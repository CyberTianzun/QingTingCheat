package fm.qingting.qtradio.localpush;

import android.content.Context;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import fm.qingting.framework.data.DataManager;
import fm.qingting.framework.data.IResultRecvHandler;
import fm.qingting.framework.data.IResultToken;
import fm.qingting.framework.data.Result;
import fm.qingting.framework.utils.MobileState;
import fm.qingting.qtradio.log.LogModule;
import fm.qingting.qtradio.logger.QTLogger;
import fm.qingting.qtradio.model.ChannelNode;
import fm.qingting.qtradio.model.DataLoadWrapper;
import fm.qingting.qtradio.model.GlobalCfg;
import fm.qingting.qtradio.model.ProgramNode;
import fm.qingting.qtradio.model.ProgramScheduleList;
import fm.qingting.qtradio.pushmessage.MessageNotification;
import fm.qingting.utils.ProcessDetect;
import fm.qingting.utils.TimeUtil;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChannelUpdate
{
  private static final int CheckChannel = 1;
  private static final int CheckUpdate = 0;
  private static final int MAX_CHANNEL_UPDATE = 3;
  private static final int SendMessage = 2;
  private static ChannelUpdate instance;
  public static final String mMsgType = "channelUpdate";
  private static HandlerThread t = new HandlerThread("Channelupdate_Thread");
  private Context mContext;
  private List<ChannelNode> mLstChannelNodes = new ArrayList();
  private List<Integer> mLstChannelUpdateIDs;
  private List<ChannelNode> mLstFavouriteNodes = null;
  private List<ProgramNode> mLstProgramNodes = new ArrayList();
  private long mSendNotificationTime = 0L;
  private IResultRecvHandler resultRecver = new IResultRecvHandler()
  {
    public void onRecvResult(Result paramAnonymousResult, Object paramAnonymousObject1, IResultToken paramAnonymousIResultToken, Object paramAnonymousObject2)
    {
      String str1 = paramAnonymousIResultToken.getType();
      if (paramAnonymousResult.getSuccess())
      {
        if (str1.equalsIgnoreCase("GET_VIRTUAL_CHANNEL_INFO"))
        {
          ChannelNode localChannelNode = (ChannelNode)paramAnonymousResult.getData();
          if (!ChannelUpdate.this.handleChannelUpdate(localChannelNode))
          {
            Message localMessage3 = new Message();
            localMessage3.what = 1;
            ChannelUpdate.this.updateHandler.sendMessageDelayed(localMessage3, 3600000L);
          }
        }
        int i;
        ProgramScheduleList localProgramScheduleList;
        do
        {
          String str2;
          do
          {
            do
            {
              return;
              Message localMessage4 = new Message();
              localMessage4.what = 0;
              ChannelUpdate.this.updateHandler.sendMessageDelayed(localMessage4, 3600000L);
              return;
            }
            while (!str1.equalsIgnoreCase("GET_VIRTUAL_PROGRAM_SCHEDULE"));
            str2 = (String)((Map)paramAnonymousObject2).get("id");
          }
          while (str2 == null);
          i = Integer.valueOf(str2).intValue();
          localProgramScheduleList = (ProgramScheduleList)paramAnonymousResult.getData();
        }
        while ((localProgramScheduleList == null) || (localProgramScheduleList.getLstProgramNode(0) == null) || (localProgramScheduleList.getLstProgramNode(0).size() <= 0));
        for (int j = 0; j < localProgramScheduleList.getLstProgramNode(0).size(); j++)
          ((ProgramNode)localProgramScheduleList.getLstProgramNode(0).get(j)).channelId = i;
        localProgramScheduleList.updateToDB();
        ChannelUpdate.this.mLstProgramNodes.add(localProgramScheduleList.getLstProgramNode(0).get(0));
        Message localMessage2 = new Message();
        localMessage2.what = 2;
        if (ChannelUpdate.this.mLstChannelUpdateIDs.size() == 0)
        {
          ChannelUpdate.this.updateHandler.sendMessageDelayed(localMessage2, 1000L);
          return;
        }
        ChannelUpdate.this.updateHandler.sendMessageDelayed(localMessage2, 3600000L);
        return;
      }
      Message localMessage1 = new Message();
      localMessage1.what = 0;
      ChannelUpdate.this.updateHandler.sendMessageDelayed(localMessage1, 3600000L);
    }
  };
  private long sendNotification = 0L;
  private UpdateHandler updateHandler = new UpdateHandler(t.getLooper());

  static
  {
    t.start();
  }

  private boolean checkChannel()
  {
    if ((this.mLstFavouriteNodes == null) || (this.mLstFavouriteNodes.size() == 0));
    while (this.mLstChannelNodes.size() >= 3)
      return false;
    int i = 0;
    boolean bool = false;
    if ((i < this.mLstFavouriteNodes.size()) && (i < 10))
      for (int j = 0; ; j++)
        if ((j >= this.mLstChannelNodes.size()) || (((ChannelNode)this.mLstChannelNodes.get(j)).channelId == ((ChannelNode)this.mLstFavouriteNodes.get(i)).channelId))
        {
          if ((j == this.mLstChannelNodes.size()) && (((ChannelNode)this.mLstFavouriteNodes.get(i)).channelType == 1))
          {
            DataLoadWrapper.loadVChannelInfo(((ChannelNode)this.mLstFavouriteNodes.get(i)).channelId, this.resultRecver);
            bool = true;
          }
          i++;
          break;
        }
    return bool;
  }

  private ChannelNode getChannelNode(int paramInt)
  {
    for (int i = 0; i < this.mLstFavouriteNodes.size(); i++)
      if (paramInt == ((ChannelNode)this.mLstFavouriteNodes.get(i)).channelId)
        return (ChannelNode)this.mLstFavouriteNodes.get(i);
    return null;
  }

  private void getFavNodesFromDB()
  {
    if (this.mLstFavouriteNodes == null)
      this.mLstFavouriteNodes = ((List)DataManager.getInstance().getData("get_favourite_channels", null, null).getResult().getData());
  }

  public static ChannelUpdate getInstance()
  {
    try
    {
      if (instance == null)
        instance = new ChannelUpdate();
      ChannelUpdate localChannelUpdate = instance;
      return localChannelUpdate;
    }
    finally
    {
    }
  }

  private boolean handleChannelUpdate(ChannelNode paramChannelNode)
  {
    if (paramChannelNode != null)
    {
      ChannelNode localChannelNode = getChannelNode(paramChannelNode.channelId);
      if ((localChannelNode != null) && (localChannelNode.getUpdateTime() != 0L) && (paramChannelNode.getUpdateTime() != 0L) && (localChannelNode.getUpdateTime() != paramChannelNode.getUpdateTime()))
      {
        localChannelNode.updatePartialInfo(paramChannelNode);
        updateFavDB(paramChannelNode);
        this.mLstChannelNodes.add(paramChannelNode);
        DataLoadWrapper.loadVirtualProgramsScheduleNode(paramChannelNode.channelId, 1, this.resultRecver);
        return true;
      }
    }
    return false;
  }

  private boolean hasFavNodes()
  {
    if ((this.mLstFavouriteNodes == null) || (this.mLstFavouriteNodes.size() == 0));
    while (true)
    {
      return false;
      for (int i = 0; i < this.mLstFavouriteNodes.size(); i++)
        if (((ChannelNode)this.mLstFavouriteNodes.get(i)).channelType == 1)
          return true;
    }
  }

  private boolean hasWifi()
  {
    return 1 == MobileState.getNetWorkType(this.mContext);
  }

  private boolean needCheckUpdate()
  {
    if (!hasFavNodes());
    int i;
    do
    {
      do
        return false;
      while ((ProcessDetect.processExists(this.mContext.getPackageName() + ":local", null)) || (TimeUtil.getDayofYear(this.mSendNotificationTime / 1000L) == TimeUtil.getDayofYear(System.currentTimeMillis() / 1000L)) || (this.mLstChannelUpdateIDs.size() >= 3));
      i = TimeUtil.getHourOfDay(System.currentTimeMillis() / 1000L);
    }
    while ((i < 18) || (i > 23));
    return true;
  }

  private boolean sendMessageNotification()
  {
    int i = TimeUtil.getHourOfDay(System.currentTimeMillis() / 1000L);
    if ((i < 18) || (i > 23));
    label498: 
    while (true)
    {
      return false;
      if (System.currentTimeMillis() - this.sendNotification < 1800000L)
        return true;
      this.sendNotification = System.currentTimeMillis();
      if ((this.mLstProgramNodes.size() > 0) && (this.mLstChannelUpdateIDs.size() < 3) && (this.mLstChannelNodes.size() > 0))
        for (int j = 0; ; j++)
        {
          if (j >= this.mLstProgramNodes.size())
            break label498;
          int k = ((ProgramNode)this.mLstProgramNodes.get(j)).channelId;
          int m = 0;
          label121: if ((m >= this.mLstChannelUpdateIDs.size()) || (((ProgramNode)this.mLstProgramNodes.get(j)).channelId == ((Integer)this.mLstChannelUpdateIDs.get(m)).intValue()))
          {
            if (m != this.mLstChannelUpdateIDs.size());
          }
          else
            for (int n = 0; ; n++)
              if ((n >= this.mLstChannelNodes.size()) || (((ChannelNode)this.mLstChannelNodes.get(n)).channelId == k))
              {
                if (n == this.mLstChannelNodes.size())
                  break;
                this.mLstChannelUpdateIDs.add(Integer.valueOf(k));
                MessageNotification localMessageNotification = new MessageNotification(this.mContext);
                localMessageNotification.mCategoryId = ((ChannelNode)this.mLstChannelNodes.get(n)).categoryId;
                localMessageNotification.mChannleId = ((ProgramNode)this.mLstProgramNodes.get(j)).channelId;
                localMessageNotification.mProgramId = ((ProgramNode)this.mLstProgramNodes.get(j)).uniqueId;
                localMessageNotification.mTitle = ("<" + ((ChannelNode)this.mLstChannelNodes.get(n)).title + ">" + "有更新啦");
                localMessageNotification.mContent = ((ProgramNode)this.mLstProgramNodes.get(j)).title;
                localMessageNotification.mMsgType = "channelUpdate";
                localMessageNotification.mContentType = 1;
                MessageNotification.sendSimpleNotification(localMessageNotification);
                sendPushedOneItemLog((ProgramNode)this.mLstProgramNodes.get(j), localMessageNotification.mCategoryId, ((ChannelNode)this.mLstChannelNodes.get(n)).title);
                this.mSendNotificationTime = System.currentTimeMillis();
                GlobalCfg.getInstance(this.mContext).setChannelUpdateTime(this.mSendNotificationTime);
                return true;
                m++;
                break label121;
              }
        }
    }
  }

  private void sendPushedOneItemLog(ProgramNode paramProgramNode, int paramInt, String paramString)
  {
    if ((this.mContext == null) || (paramProgramNode == null));
    String str1;
    do
    {
      return;
      QTLogger.getInstance().setContext(this.mContext);
      str1 = QTLogger.getInstance().buildCommonLog();
    }
    while (str1 == null);
    String str2 = str1 + "\"" + String.valueOf(paramInt) + "\"";
    String str3 = str2 + ",\"\"";
    String str4 = str3 + ",\"" + String.valueOf(paramProgramNode.channelId) + "\"";
    String str5 = str4 + ",\"" + paramString + "\"";
    String str6 = str5 + ",\"" + String.valueOf(paramProgramNode.id) + "\"";
    String str7 = str6 + ",\"" + paramProgramNode.title + "\"";
    LogModule.getInstance().send("PushedOneItem", str7);
  }

  private void updateFavDB(ChannelNode paramChannelNode)
  {
    if (paramChannelNode == null)
      return;
    HashMap localHashMap = new HashMap();
    localHashMap.put("channel", paramChannelNode);
    DataManager.getInstance().getData("update_favourite_channels", null, localHashMap);
  }

  public int getFavNodesNum()
  {
    if (this.mLstFavouriteNodes == null)
      return 0;
    int i = 0;
    int j = 0;
    while (i < this.mLstFavouriteNodes.size())
    {
      if (!((ChannelNode)this.mLstFavouriteNodes.get(i)).isLiveChannel())
        j++;
      i++;
    }
    return j;
  }

  public void init(Context paramContext)
  {
    this.mContext = paramContext;
    getFavNodesFromDB();
    this.mSendNotificationTime = GlobalCfg.getInstance(this.mContext).getChannelUpdateTime();
    this.mLstChannelUpdateIDs = new ArrayList();
  }

  public void start()
  {
    if (!hasWifi())
      return;
    Message localMessage = new Message();
    localMessage.what = 0;
    this.updateHandler.sendMessageDelayed(localMessage, 3600000L);
  }

  public class UpdateHandler extends Handler
  {
    public UpdateHandler(Looper arg2)
    {
      super();
    }

    public void handleMessage(Message paramMessage)
    {
      if (paramMessage == null);
      do
      {
        return;
        switch (paramMessage.what)
        {
        default:
          return;
        case 0:
          if (ChannelUpdate.this.needCheckUpdate())
          {
            Message localMessage4 = new Message();
            localMessage4.what = 1;
            ChannelUpdate.this.updateHandler.sendMessageDelayed(localMessage4, 1000L);
            return;
          }
          Message localMessage5 = new Message();
          localMessage5.what = 0;
          ChannelUpdate.this.updateHandler.sendMessageDelayed(localMessage5, 3600000L);
          return;
        case 1:
        case 2:
        }
      }
      while (ChannelUpdate.this.checkChannel());
      Message localMessage3 = new Message();
      localMessage3.what = 0;
      ChannelUpdate.this.updateHandler.sendMessageDelayed(localMessage3, 3600000L);
      return;
      if (ChannelUpdate.this.sendMessageNotification())
      {
        Message localMessage1 = new Message();
        localMessage1.what = 2;
        ChannelUpdate.this.updateHandler.sendMessageDelayed(localMessage1, 3600000L);
        return;
      }
      Message localMessage2 = new Message();
      localMessage2.what = 0;
      ChannelUpdate.this.updateHandler.sendMessageDelayed(localMessage2, 3600000L);
    }
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.qtradio.localpush.ChannelUpdate
 * JD-Core Version:    0.6.2
 */