package fm.qingting.qtradio.pushmessage;

import android.content.Context;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import fm.qingting.framework.data.DataManager;
import fm.qingting.framework.data.IResultRecvHandler;
import fm.qingting.framework.data.IResultToken;
import fm.qingting.framework.data.Result;
import fm.qingting.qtradio.model.ActivityNode;
import fm.qingting.qtradio.model.DataLoadWrapper;
import fm.qingting.qtradio.model.Node;
import fm.qingting.qtradio.model.ProgramNode;
import fm.qingting.utils.AppInfo;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GlobalMessageHandler
  implements MessagePump.IRecvPushMsgListener
{
  private static GlobalMessageHandler _instance;
  private String GlobalTopic = "qingting:global";
  private ActivityNode mActivityNode;
  private int mCategoryId;
  private int mChannelId;
  private String mContent;
  private int mContentType;
  private Context mContext;
  private long mExpireTime;
  private String mMsgType = "pullmsg";
  private int mProgramId;
  private String mTaskId;
  private String mTitle;
  private IResultRecvHandler resultRecver = new IResultRecvHandler()
  {
    public void onRecvResult(Result paramAnonymousResult, Object paramAnonymousObject1, IResultToken paramAnonymousIResultToken, Object paramAnonymousObject2)
    {
      String str;
      if (paramAnonymousResult.getSuccess())
      {
        str = paramAnonymousIResultToken.getType();
        if (!str.equalsIgnoreCase("GET_VIRTUAL_PROGRAM_INFO"))
          break label56;
        ProgramNode localProgramNode = (ProgramNode)paramAnonymousResult.getData();
        if (localProgramNode != null)
        {
          GlobalMessageHandler.this.writeToDB(localProgramNode);
          GlobalMessageHandler.this.sendNotification();
        }
      }
      label56: Node localNode;
      do
      {
        do
          return;
        while (!str.equalsIgnoreCase("GET_LIVE_CHANNEL_INFO"));
        localNode = (Node)paramAnonymousResult.getData();
      }
      while ((localNode == null) || (!localNode.nodeName.equalsIgnoreCase("channel")));
      GlobalMessageHandler.this.writeToDB(localNode);
      GlobalMessageHandler.this.sendNotification();
    }
  };

  private void _writeToDB(List<Node> paramList)
  {
    if ((paramList == null) || (paramList.size() == 0))
      return;
    HashMap localHashMap = new HashMap();
    localHashMap.put("nodes", paramList);
    DataManager.getInstance().getData("updatedb_pull_node", null, localHashMap);
  }

  private boolean canHandle(String paramString)
  {
    if (paramString == null);
    while (!this.GlobalTopic.equalsIgnoreCase(paramString))
      return false;
    return true;
  }

  public static GlobalMessageHandler getInstance()
  {
    if (_instance == null)
      _instance = new GlobalMessageHandler();
    return _instance;
  }

  private boolean handleMsg(String paramString)
  {
    if ((paramString == null) || (paramString.equalsIgnoreCase("")));
    while (true)
    {
      return false;
      try
      {
        resetInfo();
        JSONObject localJSONObject = (JSONObject)JSON.parse(paramString);
        String str = localJSONObject.getString("type");
        this.mTitle = localJSONObject.getString("title");
        this.mContent = localJSONObject.getString("content");
        this.mTaskId = localJSONObject.getString("uuid");
        this.mExpireTime = localJSONObject.getLongValue("expiretime");
        if (isOutOfDate())
        {
          PushMessageLog.sendPushOutOfDateLog(this.mContext, this.mExpireTime);
          return false;
        }
        if (str.equalsIgnoreCase("virtual_program"))
        {
          this.mCategoryId = localJSONObject.getIntValue("cat_id_v6");
          this.mChannelId = localJSONObject.getIntValue("channel_id_v6");
          this.mProgramId = localJSONObject.getIntValue("program_id_v6");
          this.mContentType = 1;
          DataLoadWrapper.loadVProgramInfo(Integer.valueOf(this.mProgramId).intValue(), this.resultRecver);
        }
        while (true)
        {
          PushMessageLog.sendPushLog(this.mContext, this.mTaskId, this.GlobalTopic, this.mCategoryId, 0, this.mChannelId, this.mProgramId, this.mContent, "RecvGeTuiPushMsg");
          return false;
          if (str.equalsIgnoreCase("live_channel"))
          {
            this.mCategoryId = localJSONObject.getIntValue("cat_id_v6");
            this.mChannelId = localJSONObject.getIntValue("channel_id_v6");
            this.mProgramId = 0;
            this.mContentType = 5;
            DataLoadWrapper.loadLiveChannelNode(Integer.valueOf(this.mChannelId).intValue(), this.resultRecver);
          }
          else
          {
            if (!str.equalsIgnoreCase("activity"))
              break;
            int i = localJSONObject.getIntValue("version");
            if (AppInfo.getCurrentVersionCode(this.mContext) >= i)
              break;
            this.mActivityNode = new ActivityNode();
            this.mActivityNode.contentUrl = localJSONObject.getString("content_url");
            this.mActivityNode.titleIconUrl = localJSONObject.getString("title_icon");
            this.mActivityNode.infoUrl = localJSONObject.getString("info_url");
            this.mActivityNode.infoTitle = this.mTitle;
            this.mActivityNode.desc = this.mContent;
            this.mContentType = 4;
            sendActivityNotification();
          }
        }
      }
      catch (Exception localException)
      {
      }
    }
    return false;
  }

  private boolean isOutOfDate()
  {
    if (this.mExpireTime == 0L);
    while (this.mExpireTime >= System.currentTimeMillis() / 1000L)
      return false;
    return true;
  }

  private void resetInfo()
  {
    this.mCategoryId = 0;
    this.mChannelId = 0;
    this.mProgramId = 0;
    this.mTitle = null;
    this.mContent = null;
    this.mTaskId = null;
    this.mExpireTime = 0L;
    this.mActivityNode = null;
  }

  private void sendActivityNotification()
  {
    if (this.mActivityNode != null)
    {
      MessageNotification.sendActivityNotification(this.mActivityNode, "push_activity", this.mTaskId, this.GlobalTopic, this.mContentType, this.mContext);
      PushMessageLog.sendPushLog(this.mContext, this.mTaskId, this.GlobalTopic, this.mCategoryId, 0, this.mChannelId, this.mProgramId, this.mContent, "SendGeTuiPushMsg");
    }
  }

  private void sendNotification()
  {
    MessageNotification localMessageNotification = new MessageNotification(this.mContext);
    localMessageNotification.mCategoryId = this.mCategoryId;
    localMessageNotification.mChannleId = this.mChannelId;
    localMessageNotification.mProgramId = this.mProgramId;
    localMessageNotification.mTitle = this.mTitle;
    localMessageNotification.mContent = this.mContent;
    localMessageNotification.mMsgType = this.mMsgType;
    localMessageNotification.mContentType = this.mContentType;
    localMessageNotification.mTag = this.GlobalTopic;
    localMessageNotification.mTaskId = this.mTaskId;
    MessageNotification.sendSimpleNotification(localMessageNotification);
    PushMessageLog.sendPushLog(this.mContext, this.mTaskId, this.GlobalTopic, this.mCategoryId, 0, this.mChannelId, this.mProgramId, this.mContent, "SendGeTuiPushMsg");
  }

  private void writeToDB(Node paramNode)
  {
    if (paramNode == null)
      return;
    ArrayList localArrayList = new ArrayList();
    localArrayList.add(paramNode);
    _writeToDB(localArrayList);
  }

  public void init(Context paramContext)
  {
    MessagePump.getInstance().registerRecvMsg(this);
    this.mContext = paramContext;
  }

  public boolean onRecvPushMsg(PushMessage paramPushMessage, int paramInt)
  {
    if (paramPushMessage == null);
    while ((paramInt != 0) || (!canHandle(paramPushMessage.mTopic)))
      return false;
    return handleMsg(paramPushMessage.mMessage);
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.qtradio.pushmessage.GlobalMessageHandler
 * JD-Core Version:    0.6.2
 */