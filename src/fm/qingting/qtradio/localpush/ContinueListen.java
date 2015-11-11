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
import fm.qingting.qtradio.model.DataLoadWrapper;
import fm.qingting.qtradio.model.GlobalCfg;
import fm.qingting.qtradio.model.PlayedMetaData;
import fm.qingting.qtradio.model.ProgramNode;
import fm.qingting.qtradio.pushmessage.MessageNotification;
import fm.qingting.utils.ProcessDetect;
import fm.qingting.utils.TimeUtil;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ContinueListen
{
  private static final int CheckPlayMeta = 0;
  private static final int PickPlayMeta = 1;
  private static ContinueListen instance;
  public static final String mMsgType = "continueListen";
  private static HandlerThread t = new HandlerThread("ContinueListen_Thread");
  private int mCategoryId = 0;
  private int mChannelId = 0;
  private Context mContext;
  private List<PlayedMetaData> mLstPlayedMetaData;
  private long mSendNotificationTime = 0L;
  private IResultRecvHandler resultRecver = new IResultRecvHandler()
  {
    public void onRecvResult(Result paramAnonymousResult, Object paramAnonymousObject1, IResultToken paramAnonymousIResultToken, Object paramAnonymousObject2)
    {
      String str = paramAnonymousIResultToken.getType();
      if (paramAnonymousResult.getSuccess())
      {
        if (str.equalsIgnoreCase("GET_VIRTUAL_PROGRAM_INFO"))
        {
          ProgramNode localProgramNode = (ProgramNode)paramAnonymousResult.getData();
          if (localProgramNode != null)
          {
            ContinueListen.this.sendMessageNotification(localProgramNode);
            ContinueListen.access$102(ContinueListen.this, System.currentTimeMillis());
            GlobalCfg.getInstance(ContinueListen.this.mContext).setContinueListenTime(ContinueListen.this.mSendNotificationTime);
            Message localMessage2 = new Message();
            localMessage2.what = 0;
            ContinueListen.this.updateHandler.sendMessageDelayed(localMessage2, 3600000L);
          }
        }
        return;
      }
      Message localMessage1 = new Message();
      localMessage1.what = 0;
      ContinueListen.this.updateHandler.sendMessageDelayed(localMessage1, 3600000L);
    }
  };
  private UpdateHandler updateHandler = new UpdateHandler(t.getLooper());

  static
  {
    t.start();
  }

  public static ContinueListen getInstance()
  {
    if (instance == null)
      instance = new ContinueListen();
    return instance;
  }

  private void getPlayMetaFromDB()
  {
    if (this.mLstPlayedMetaData == null)
    {
      Result localResult = DataManager.getInstance().getData("getdb_playedmeta", null, null).getResult();
      boolean bool = localResult.getSuccess();
      List localList = null;
      if (bool)
        localList = (List)localResult.getData();
      this.mLstPlayedMetaData = localList;
    }
  }

  private boolean hasPlayMeta()
  {
    return (this.mLstPlayedMetaData != null) && (this.mLstPlayedMetaData.size() != 0);
  }

  private boolean needCheckPlayMeta()
  {
    if (!hasPlayMeta());
    int i;
    do
    {
      do
        return false;
      while ((ProcessDetect.processExists(this.mContext.getPackageName() + ":local", null)) || (TimeUtil.getDayofYear(this.mSendNotificationTime / 1000L) == TimeUtil.getDayofYear(System.currentTimeMillis() / 1000L)));
      i = TimeUtil.getHourOfDay(System.currentTimeMillis() / 1000L);
    }
    while ((i < 18) || (i > 23));
    return true;
  }

  private boolean pickPlayedMeta()
  {
    if ((this.mLstPlayedMetaData == null) || (this.mLstPlayedMetaData.size() == 0))
      return false;
    long l1 = System.currentTimeMillis() / 1000L;
    int i = -1;
    long l2 = -9223372036854775807L;
    for (int j = 0; j < this.mLstPlayedMetaData.size(); j++)
    {
      int k = ((PlayedMetaData)this.mLstPlayedMetaData.get(j)).sendTime;
      long l3 = ((PlayedMetaData)this.mLstPlayedMetaData.get(j)).playedTime;
      if ((Math.abs(k) == 0) && (l3 > l1 - 604800L) && (l3 < l1 - 64800L) && (((PlayedMetaData)this.mLstPlayedMetaData.get(j)).position > 0.2D * ((PlayedMetaData)this.mLstPlayedMetaData.get(j)).duration) && (((PlayedMetaData)this.mLstPlayedMetaData.get(j)).position < 0.9D * ((PlayedMetaData)this.mLstPlayedMetaData.get(j)).duration) && (l3 > l2))
      {
        this.mCategoryId = ((PlayedMetaData)this.mLstPlayedMetaData.get(j)).categoryId;
        this.mChannelId = ((PlayedMetaData)this.mLstPlayedMetaData.get(j)).channelId;
        l2 = l3;
        i = j;
      }
    }
    if (i != -1)
    {
      ((PlayedMetaData)this.mLstPlayedMetaData.get(i)).sendTime = (-1 * (int)(System.currentTimeMillis() / 1000L));
      updateToDB((PlayedMetaData)this.mLstPlayedMetaData.get(i));
      DataLoadWrapper.loadVProgramInfo(((PlayedMetaData)this.mLstPlayedMetaData.get(i)).programId, this.resultRecver);
      return true;
    }
    return false;
  }

  private void sendMessageNotification(ProgramNode paramProgramNode)
  {
    if (paramProgramNode != null)
    {
      MessageNotification localMessageNotification = new MessageNotification(this.mContext);
      localMessageNotification.mCategoryId = this.mCategoryId;
      localMessageNotification.mChannleId = this.mChannelId;
      localMessageNotification.mProgramId = paramProgramNode.id;
      localMessageNotification.mTitle = "继续收听";
      localMessageNotification.mContent = paramProgramNode.title;
      localMessageNotification.mMsgType = "continueListen";
      localMessageNotification.mContentType = 1;
      MessageNotification.sendSimpleNotification(localMessageNotification);
    }
  }

  private void updateToDB(PlayedMetaData paramPlayedMetaData)
  {
    if (paramPlayedMetaData == null)
      return;
    HashMap localHashMap = new HashMap();
    localHashMap.put("playedMeta", paramPlayedMetaData);
    DataManager.getInstance().getData("updatedb_playedmeta", null, localHashMap);
  }

  public void init(Context paramContext)
  {
    this.mContext = paramContext;
    getPlayMetaFromDB();
    this.mSendNotificationTime = GlobalCfg.getInstance(this.mContext).getContinueListenTime();
  }

  public void start()
  {
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
          if (ContinueListen.this.needCheckPlayMeta())
          {
            Message localMessage2 = new Message();
            localMessage2.what = 1;
            ContinueListen.this.updateHandler.sendMessageDelayed(localMessage2, 1000L);
            return;
          }
          Message localMessage3 = new Message();
          localMessage3.what = 0;
          ContinueListen.this.updateHandler.sendMessageDelayed(localMessage3, 3600000L);
          return;
        case 1:
        }
      }
      while (ContinueListen.this.pickPlayedMeta());
      Message localMessage1 = new Message();
      localMessage1.what = 0;
      ContinueListen.this.updateHandler.sendMessageDelayed(localMessage1, 3600000L);
    }
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.qtradio.localpush.ContinueListen
 * JD-Core Version:    0.6.2
 */