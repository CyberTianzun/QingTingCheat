package fm.qingting.qtradio.im;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningTaskInfo;
import android.content.ComponentName;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.PowerManager;
import android.text.TextUtils;
import android.widget.Toast;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import fm.qingting.framework.data.DataManager;
import fm.qingting.framework.data.IResultRecvHandler;
import fm.qingting.framework.data.IResultToken;
import fm.qingting.framework.data.Result;
import fm.qingting.qtradio.QTRadioActivity;
import fm.qingting.qtradio.controller.ControllerManager;
import fm.qingting.qtradio.data.IMDatabaseDS;
import fm.qingting.qtradio.im.info.GroupInfo;
import fm.qingting.qtradio.im.message.IMMessage;
import fm.qingting.qtradio.log.LogModule;
import fm.qingting.qtradio.logger.QTLogger;
import fm.qingting.qtradio.manager.INETEventListener;
import fm.qingting.qtradio.manager.NetWorkManage;
import fm.qingting.qtradio.model.GlobalCfg;
import fm.qingting.qtradio.model.InfoManager;
import fm.qingting.qtradio.model.RootNode;
import fm.qingting.qtradio.model.RootNode.IInfoUpdateEventListener;
import fm.qingting.qtradio.model.SharedCfg;
import fm.qingting.qtradio.room.SnsInfo;
import fm.qingting.qtradio.room.UserInfo;
import fm.qingting.qtradio.room.WeiboChat;
import fm.qingting.qtradio.social.CloudCenter;
import fm.qingting.qtradio.social.UserProfile;
import fm.qingting.utils.ImageUtil;
import fm.qingting.utils.QTMSGManage;
import fm.qingting.utils.RangeRandom;
import fm.qingting.websocket.WebSocketClient;
import fm.qingting.websocket.WebSocketClient.Listener;
import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import org.apache.http.message.BasicNameValuePair;

public class IMAgent
  implements IResultRecvHandler, INETEventListener
{
  private static long MAX_RecvMsg_INTERVAL = 0L;
  private static final String TAG = "IMAgent2";
  private static IMAgent instance;
  private static long mLastRecvMsgTime = 0L;
  private final int CONNECT_CHATROOM_FAILED = 2;
  private int CONNECT_INTERVAL = 20;
  private int IM_HISTORY_SIZE = 100;
  private int MSG_CNT_PER_PAGE = 10;
  private int PING_INTERVAL = 300;
  private final String PROTOCOL_HEAD = "ws://";
  private final int RECV_LIST_MSG = 1;
  private final int RECV_SINGLE_MSG = 0;
  private long SEND_NOTIFICATION_INTERVAL = 2000L;
  private boolean autoGet = false;
  private boolean autoJoin = false;
  private boolean autoLogin = false;
  private Handler connectHandler = new Handler();
  private String connectUrl;
  private boolean connected = false;
  private boolean connecting = false;
  private Runnable doConnect = new Runnable()
  {
    public void run()
    {
      IMAgent.this.connect();
    }
  };
  private long lastPingTime = 0L;
  private boolean loginSuccess = false;
  private List<String> lstHasCheckin = new ArrayList();
  private int mAddProgramId = 0;
  private int mAddTime = 0;
  private int mBarraeProgramId = 0;
  private Context mContext;
  private String mCurrentGroupId;
  private List<UserInfo> mCurrentGroupUsers;
  private Handler mDispatchHandler = new DispatchHandler(Looper.getMainLooper());
  private JSONObject mJsonMsg = new JSONObject();
  private String mLastFromId = null;
  private String mLastNetType = "";
  private List<String> mLstEnableGroups = new ArrayList();
  private List<IMMessage> mLstImageBarrage = null;
  private IMMessage mLstSendImageBarrage = null;
  private List<String> mLstTempWatchGroups = new ArrayList();
  private List<IMMessage> mLstTextBarrage = null;
  private String mPingMsg = null;
  private int mRecvMsgCnt = 0;
  private long mRecvMsgTime = 0L;
  private int mTotalUnReadMsgCnt = 0;
  private WebSocketClient mWebSocket;
  private Map<String, UserInfo> mapATUser = new HashMap();
  private Map<String, GroupInfo> mapGroupInfo = new HashMap();
  private Map<String, List<IMEventListener>> mapIMEventListeners = new HashMap();
  private Map<String, List<IMMessage>> mapMessage = new HashMap();
  private Map<String, Integer> mapUnReadMsgCnt = new HashMap();
  private int maxHistoryRecords = 30;
  private int maxOnlineUsers = 1000;
  private int recordCnt = 0;

  private IMAgent()
  {
    NetWorkManage.getInstance().addListener(this);
    this.mLastNetType = NetWorkManage.getInstance().getNetWorkType();
    initUnReadMsg();
  }

  private void _connect()
  {
    if ((this.connectUrl == null) || (this.connectUrl.equalsIgnoreCase("")))
      getImServer();
    do
    {
      do
      {
        return;
        if (!this.connecting)
          break;
      }
      while (this.mContext == null);
      return;
    }
    while ((this.connected) && (this.mWebSocket != null));
    try
    {
      this.connecting = true;
      BasicNameValuePair[] arrayOfBasicNameValuePair = new BasicNameValuePair[1];
      arrayOfBasicNameValuePair[0] = new BasicNameValuePair("Cookie", "session=abcd");
      List localList = Arrays.asList(arrayOfBasicNameValuePair);
      this.mWebSocket = new WebSocketClient(URI.create(this.connectUrl), new WebSocketClient.Listener()
      {
        public void onConnect()
        {
          IMAgent.this.handleConnect();
        }

        public void onDisconnect(int paramAnonymousInt, String paramAnonymousString)
        {
          IMAgent.this.handleConnectFailure();
        }

        public void onError(Exception paramAnonymousException)
        {
          IMAgent.this.handleConnectFailure();
        }

        public void onMessage(String paramAnonymousString)
        {
          IMAgent.this.handleMessage(paramAnonymousString);
        }

        public void onMessage(byte[] paramAnonymousArrayOfByte)
        {
        }
      }
      , localList);
      this.mWebSocket.connect();
      return;
    }
    catch (Exception localException)
    {
    }
  }

  private void addGroupInfo(GroupInfo paramGroupInfo)
  {
    if ((paramGroupInfo == null) || (paramGroupInfo.groupId == null))
      return;
    this.mapGroupInfo.put(paramGroupInfo.groupId, paramGroupInfo);
  }

  private void addMsgToDS(IMMessage paramIMMessage)
  {
    if (paramIMMessage == null)
      return;
    String str;
    if (paramIMMessage.isGroupMsg())
    {
      str = paramIMMessage.mFromGroupId;
      List localList = (List)this.mapMessage.get(str);
      if (localList == null)
        break label67;
      localList.add(paramIMMessage);
    }
    while (true)
    {
      if (!paramIMMessage.isGroupMsg())
        break label101;
      IMDatabaseDS.getInstance().appendGroupMessage(paramIMMessage, false);
      return;
      str = paramIMMessage.mFromID;
      break;
      label67: ArrayList localArrayList = new ArrayList();
      localArrayList.add(paramIMMessage);
      this.mapMessage.put(str, localArrayList);
    }
    label101: IMDatabaseDS.getInstance().appendPrivateMessage(paramIMMessage, false);
  }

  private boolean canHandle(String paramString, int paramInt)
  {
    if (paramString == null);
    do
    {
      do
      {
        do
        {
          return false;
          if (paramInt != 0)
            break;
        }
        while (isSelf(paramString));
        return true;
      }
      while (paramInt != 1);
      if (hasInTempWatchGroups(paramString) != -1)
        return true;
    }
    while (!IMContacts.getInstance().hasWatchedGroup(paramString));
    return true;
  }

  private void dispatchIMEvent(String paramString, IMMessage paramIMMessage)
  {
    if (this.mapIMEventListeners.containsKey(paramString))
    {
      List localList = (List)this.mapIMEventListeners.get(paramString);
      int i = localList.size();
      for (int j = 0; j < i; j++)
        ((IMEventListener)localList.get(j)).onIMEvent(paramString, paramIMMessage);
    }
  }

  private void dispatchIMEvent(String paramString, List<IMMessage> paramList)
  {
    if (this.mapIMEventListeners.containsKey(paramString))
    {
      List localList = (List)this.mapIMEventListeners.get(paramString);
      int i = localList.size();
      for (int j = 0; j < i; j++)
        ((IMEventListener)localList.get(j)).onIMListMsg(paramString, paramList);
    }
  }

  private byte[] getFileByPath(String paramString)
  {
    if ((paramString == null) || (paramString.equalsIgnoreCase("")))
      return null;
    try
    {
      byte[] arrayOfByte = ImageUtil.getCompressImage(paramString);
      return arrayOfByte;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return null;
  }

  private void getImServer()
  {
    UserInfo localUserInfo = InfoManager.getInstance().getUserProfile().getUserInfo();
    if ((localUserInfo != null) && (localUserInfo.userKey != null) && (!localUserInfo.userKey.equalsIgnoreCase("")))
    {
      HashMap localHashMap = new HashMap();
      localHashMap.put("from", localUserInfo.userKey);
      DataManager.getInstance().getData("GET_IM_SERVER", this, localHashMap);
    }
  }

  public static IMAgent getInstance()
  {
    if (instance == null)
      instance = new IMAgent();
    return instance;
  }

  private IMMessage getLatestGroupMessage(String paramString)
  {
    if (paramString == null)
      return null;
    List localList = (List)this.mapMessage.get(paramString);
    if (localList == null)
      return null;
    if (localList.size() == 0)
      return null;
    return (IMMessage)localList.get(-1 + localList.size());
  }

  private IMMessage getLatestUserMessage(String paramString)
  {
    if (paramString == null)
      return null;
    List localList = (List)this.mapMessage.get(paramString);
    if (localList == null)
      return null;
    if (localList.size() == 0)
      return null;
    return (IMMessage)localList.get(-1 + localList.size());
  }

  private String getMsgId()
  {
    UserInfo localUserInfo = InfoManager.getInstance().getUserProfile().getUserInfo();
    if (localUserInfo != null)
    {
      String str = localUserInfo.userKey;
      if (str != null)
      {
        long l = System.currentTimeMillis();
        return str + l;
      }
    }
    return null;
  }

  private void handleConnectFailure()
  {
    this.connected = false;
    this.connecting = false;
    this.loginSuccess = false;
    if (this.mWebSocket != null)
      this.mWebSocket.disconnect();
    if (!InfoManager.getInstance().hasConnectedNetwork())
    {
      this.mDispatchHandler.sendMessage(this.mDispatchHandler.obtainMessage(2, null));
      return;
    }
    asyncConnect();
  }

  private void handleMessage(String paramString)
  {
    if ((paramString == null) || (paramString.equalsIgnoreCase("")));
    while (true)
    {
      return;
      try
      {
        JSONObject localJSONObject = (JSONObject)JSON.parse(paramString);
        String str1 = localJSONObject.getString("event");
        if (str1 != null)
        {
          if (str1.equalsIgnoreCase("login"))
          {
            recvLogin();
            return;
          }
          if (str1.equalsIgnoreCase("peer"))
          {
            if (this.loginSuccess)
            {
              String str4 = localJSONObject.getString("from");
              if (canHandle(str4, 0))
              {
                IMMessage localIMMessage2 = new IMMessage();
                localIMMessage2.mFromID = str4;
                localIMMessage2.mFromName = localJSONObject.getString("fromName");
                localIMMessage2.mGender = localJSONObject.getString("fromGender");
                localIMMessage2.mMsgId = localJSONObject.getString("id");
                recvMsg(0, localJSONObject.getJSONArray("body"), localIMMessage2);
              }
            }
          }
          else if ((str1.equalsIgnoreCase("group")) && (this.loginSuccess))
          {
            String str2 = localJSONObject.getString("from");
            if (!isSelf(str2))
            {
              String str3 = localJSONObject.getString("to");
              if (canHandle(str3, 1))
              {
                IMMessage localIMMessage1 = new IMMessage();
                localIMMessage1.mFromID = str2;
                localIMMessage1.mFromGroupId = str3;
                localIMMessage1.mFromName = localJSONObject.getString("fromName");
                localIMMessage1.mGender = localJSONObject.getString("fromGender");
                localIMMessage1.mMsgId = localJSONObject.getString("id");
                recvMsg(1, localJSONObject.getJSONArray("body"), localIMMessage1);
                return;
              }
            }
          }
        }
      }
      catch (Exception localException)
      {
      }
    }
  }

  private int hasInTempWatchGroups(String paramString)
  {
    if (paramString == null)
    {
      i = -1;
      return i;
    }
    for (int i = 0; ; i++)
    {
      if (i >= this.mLstTempWatchGroups.size())
        break label49;
      if (((String)this.mLstTempWatchGroups.get(i)).equalsIgnoreCase(paramString))
        break;
    }
    label49: return -1;
  }

  private boolean isSelf(String paramString)
  {
    if (paramString == null);
    UserInfo localUserInfo;
    do
    {
      return false;
      localUserInfo = InfoManager.getInstance().getUserProfile().getUserInfo();
    }
    while ((localUserInfo == null) || (localUserInfo.userKey == null) || (!localUserInfo.userKey.equalsIgnoreCase(paramString)));
    return true;
  }

  private boolean isSetBlack(IMMessage paramIMMessage)
  {
    if (paramIMMessage == null);
    do
    {
      do
      {
        do
          return false;
        while (paramIMMessage.isGroupMsg());
        if (!IMUtil.isSetBlack(paramIMMessage.mMessage))
          break;
      }
      while (!CloudCenter.getInstance().isLiveRoomAdmin(paramIMMessage.mFromID));
      if (InfoManager.getInstance().getUserProfile().getUserInfo() != null)
        InfoManager.getInstance().getUserProfile().getUserInfo().setBlocked(true);
      return true;
    }
    while (!ImBlackList.inBlackList(this.mContext, paramIMMessage.mFromID));
    return true;
  }

  private boolean isUIActive()
  {
    if (this.mContext == null)
      return false;
    if (!((PowerManager)this.mContext.getSystemService("power")).isScreenOn())
      return false;
    return ((ActivityManager.RunningTaskInfo)((ActivityManager)this.mContext.getSystemService("activity")).getRunningTasks(1).get(0)).topActivity.getClassName().equals(QTRadioActivity.class.getName());
  }

  private void log(String paramString)
  {
  }

  private void login()
  {
    try
    {
      UserInfo localUserInfo = InfoManager.getInstance().getUserProfile().getUserInfo();
      if ((localUserInfo != null) && (this.mWebSocket != null))
      {
        JSONObject localJSONObject = new JSONObject();
        localJSONObject.put("event", "login");
        localJSONObject.put("from", localUserInfo.userKey);
        String str = localJSONObject.toString();
        if (str != null)
          this.mWebSocket.send(str);
      }
      return;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
  }

  private boolean needSendMsgNotification(IMMessage paramIMMessage)
  {
    if (paramIMMessage == null);
    String str;
    do
    {
      do
      {
        return false;
        if (paramIMMessage.isGroupMsg())
          return hasEnabledGroup(paramIMMessage.mFromGroupId);
        if (!IMUtil.isSetBlack(paramIMMessage.mMessage))
          break;
      }
      while (InfoManager.getInstance().getUserProfile().getUserInfo() == null);
      InfoManager.getInstance().getUserProfile().getUserInfo().setBlocked(true);
      return false;
      str = paramIMMessage.mFromID;
    }
    while (ControllerManager.getInstance().isActive(paramIMMessage.chatType, str));
    return true;
  }

  private void recvLogin()
  {
    this.loginSuccess = true;
  }

  private void recvMsg(int paramInt, JSONArray paramJSONArray, IMMessage paramIMMessage)
  {
    if ((paramJSONArray != null) && (paramIMMessage != null));
    try
    {
      if (paramJSONArray.size() == 1)
      {
        IMMessage.parseData(paramJSONArray.getJSONObject(0), paramIMMessage);
        paramIMMessage.chatType = paramInt;
        if (paramIMMessage != null)
        {
          if (isSetBlack(paramIMMessage))
            return;
          if ((paramIMMessage.mFromAvatar == null) || (paramIMMessage.mFromAvatar.equalsIgnoreCase("")))
            paramIMMessage.mFromAvatar = getUserAvatar(paramIMMessage.mFromID);
          if ((paramIMMessage.mFromGroupId != null) && (!paramIMMessage.mFromGroupId.equalsIgnoreCase("")) && (paramIMMessage.mGroupName == null) && (paramIMMessage.isGroupMsg()))
          {
            GroupInfo localGroupInfo = getGroupInfo(paramIMMessage.mFromGroupId);
            if (localGroupInfo != null)
              paramIMMessage.mGroupName = localGroupInfo.groupName;
          }
          long l = 1L + InfoManager.getInstance().getMsgSeq();
          paramIMMessage.msgSeq = l;
          InfoManager.getInstance().setMsgSeq(l);
          this.mRecvMsgCnt = (1 + this.mRecvMsgCnt);
          if (!paramIMMessage.isGroupMsg())
          {
            if (InfoManager.getInstance().getUserProfile() != null)
              paramIMMessage.mToUserId = InfoManager.getInstance().getUserProfile().getUserKey();
            IMContacts.getInstance().addRecentContacts(paramIMMessage.buildUserInfo());
            Integer localInteger3 = (Integer)this.mapUnReadMsgCnt.get(paramIMMessage.mFromID);
            if (localInteger3 == null)
              this.mapUnReadMsgCnt.put(paramIMMessage.mFromID, Integer.valueOf(1));
            while (true)
            {
              this.mTotalUnReadMsgCnt = (1 + this.mTotalUnReadMsgCnt);
              addMsgToDS(paramIMMessage);
              this.mDispatchHandler.sendMessage(this.mDispatchHandler.obtainMessage(0, paramIMMessage));
              if (!needSendMsgNotification(paramIMMessage))
                break;
              sendNotification(paramIMMessage);
              return;
              Integer localInteger4 = Integer.valueOf(1 + localInteger3.intValue());
              this.mapUnReadMsgCnt.put(paramIMMessage.mFromID, localInteger4);
            }
          }
          IMContacts.getInstance().addRecentContacts(paramIMMessage.mFromGroupId);
          Integer localInteger1 = (Integer)this.mapUnReadMsgCnt.get(paramIMMessage.mFromGroupId);
          if (localInteger1 == null)
            this.mapUnReadMsgCnt.put(paramIMMessage.mFromGroupId, Integer.valueOf(1));
          while (true)
          {
            this.mTotalUnReadMsgCnt = (1 + this.mTotalUnReadMsgCnt);
            break;
            Integer localInteger2 = Integer.valueOf(1 + localInteger1.intValue());
            this.mapUnReadMsgCnt.put(paramIMMessage.mFromGroupId, localInteger2);
          }
        }
      }
      return;
    }
    catch (Exception localException)
    {
    }
  }

  private void saveEnableGroup()
  {
    Object localObject = null;
    int i = 0;
    if (i < this.mLstEnableGroups.size())
    {
      if (localObject != null);
      for (String str1 = (String)localObject + "_"; ; str1 = "")
      {
        String str2 = str1 + (String)this.mLstEnableGroups.get(i);
        i++;
        localObject = str2;
        break;
      }
    }
    if (localObject != null)
      GlobalCfg.getInstance(this.mContext).setEnableGroups((String)localObject);
  }

  private void sendNotification(IMMessage paramIMMessage)
  {
  }

  private boolean sendUserMsgInGroup(String paramString, UserInfo paramUserInfo, GroupInfo paramGroupInfo)
  {
    if (paramString != null);
    try
    {
      if ((!paramString.equalsIgnoreCase("")) && (paramUserInfo != null) && (paramGroupInfo != null))
      {
        if (this.mWebSocket != null)
        {
          JSONObject localJSONObject = IMMessage.buildIMMessage(paramString, InfoManager.getInstance().getUserProfile().getUserInfo(), paramUserInfo);
          if (localJSONObject != null)
          {
            String str1 = getMsgId();
            if (str1 != null)
              this.mJsonMsg.put("id", str1);
            this.mJsonMsg.put("event", "peer");
            this.mJsonMsg.put("fromName", InfoManager.getInstance().getUserProfile().getUserInfo().snsInfo.sns_name);
            this.mJsonMsg.put("fromGender", InfoManager.getInstance().getUserProfile().getUserInfo().snsInfo.sns_gender);
            this.mJsonMsg.put("from", InfoManager.getInstance().getUserProfile().getUserInfo().userKey);
            this.mJsonMsg.put("to", paramUserInfo.userKey);
            this.mJsonMsg.put("group", paramGroupInfo.groupId);
            JSONArray localJSONArray = new JSONArray();
            localJSONArray.add(localJSONObject);
            this.mJsonMsg.put("body", localJSONArray);
            String str2 = this.mJsonMsg.toJSONString();
            if (str2 != null)
            {
              this.mWebSocket.send(str2);
              return true;
            }
          }
        }
        else
        {
          _connect();
          Toast.makeText(this.mContext, "未连接上直播间，发送消息失败", 1).show();
        }
      }
      else
        return false;
    }
    catch (Exception localException)
    {
    }
    return false;
  }

  public void addBarrageInfo(int paramInt1, int paramInt2)
  {
    this.mAddProgramId = paramInt1;
    this.mAddTime = paramInt2;
  }

  public void addBaseUserInfo(String paramString1, String paramString2, String paramString3)
  {
    if ((paramString1 == null) || (paramString2 == null))
      return;
    BaseUserInfoPool.putBaseInfo(paramString1, paramString2, paramString3);
  }

  public void addGroup(String paramString)
  {
    if (paramString == null)
      return;
    for (int i = 0; ; i++)
    {
      if (i >= this.mLstTempWatchGroups.size())
        break label35;
      if (hasInTempWatchGroups(paramString) != -1)
        break;
    }
    label35: this.mLstTempWatchGroups.add(paramString);
  }

  public boolean allowRecvMsg()
  {
    long l = System.currentTimeMillis();
    if ((l - mLastRecvMsgTime) / 1000L > MAX_RecvMsg_INTERVAL)
    {
      mLastRecvMsgTime = l;
      return true;
    }
    return false;
  }

  public void asyncConnect()
  {
    this.connectHandler.removeCallbacks(this.doConnect);
    long l = 1000L * RangeRandom.Random(this.CONNECT_INTERVAL);
    this.connectHandler.postDelayed(this.doConnect, l);
  }

  public void clearNotificationMsg()
  {
    this.mLastFromId = null;
    this.mRecvMsgCnt = 0;
    this.mRecvMsgTime = 0L;
  }

  public boolean clearUnreadCnt(String paramString)
  {
    if (paramString == null)
      return false;
    if (this.mapUnReadMsgCnt.get(paramString) == null)
      return false;
    int i = ((Integer)this.mapUnReadMsgCnt.get(paramString)).intValue();
    this.mTotalUnReadMsgCnt -= i;
    this.mapUnReadMsgCnt.put(paramString, Integer.valueOf(0));
    return true;
  }

  public void connect()
  {
    getImServer();
  }

  public void disableGroup(String paramString)
  {
    for (int i = 0; ; i++)
      if (i < this.mLstEnableGroups.size())
      {
        if (((String)this.mLstEnableGroups.get(i)).equalsIgnoreCase(paramString))
        {
          this.mLstEnableGroups.remove(i);
          saveEnableGroup();
        }
      }
      else
        return;
  }

  public void enableGroup(String paramString)
  {
    if (paramString == null);
    while (hasEnableGroups(paramString))
      return;
    this.mLstEnableGroups.add(paramString);
    saveEnableGroup();
  }

  public String getATTaName(String paramString)
  {
    if ((paramString != null) && (paramString.startsWith("@")))
    {
      String str = "";
      for (int i = 0; ; i++)
      {
        if ((i >= paramString.length()) || (paramString.charAt(i) == ' '))
        {
          if (str.length() <= 1)
            break;
          return str;
        }
        str = str + paramString.charAt(i);
      }
    }
    return null;
  }

  public int getAddBarrageTime()
  {
    return this.mAddTime;
  }

  public String getCheckinText()
  {
    switch ((int)RangeRandom.Random(5L))
    {
    default:
    case 0:
    case 1:
    case 2:
      String str1;
      do
      {
        String str3;
        do
        {
          return "签个到，大家好呀";
          str3 = InfoManager.getInstance().getCurrentCity();
        }
        while ((str3 == null) || (str3.equalsIgnoreCase("")));
        String str4 = "我在" + str3;
        return str4 + "签个到,大家好呀";
        return "大家好,有人吗?";
        str1 = InfoManager.getInstance().getCurrentCity();
      }
      while ((str1 == null) || (str1.equalsIgnoreCase("")));
      String str2 = "大家好,有" + str1;
      return str2 + "的吗?";
    case 3:
      return "哈喽,大家好";
    case 4:
    }
    return "各位帅哥美女好";
  }

  public GroupInfo getGroupInfo(String paramString)
  {
    if (paramString == null)
      return null;
    return (GroupInfo)this.mapGroupInfo.get(paramString);
  }

  public List<UserInfo> getGroupMembers(String paramString)
  {
    if (TextUtils.equals(paramString, this.mCurrentGroupId))
      return this.mCurrentGroupUsers;
    return null;
  }

  public List<IMMessage> getImageBarrage(int paramInt)
  {
    if (this.mBarraeProgramId == paramInt)
      return this.mLstImageBarrage;
    return null;
  }

  public IMMessage getSendImageBarrage()
  {
    return this.mLstSendImageBarrage;
  }

  public List<IMMessage> getTxtBarrage(int paramInt)
  {
    if (this.mBarraeProgramId == paramInt)
      return this.mLstTextBarrage;
    return null;
  }

  public int getUnreadCnt()
  {
    if (this.mTotalUnReadMsgCnt < 0)
      return 0;
    return this.mTotalUnReadMsgCnt;
  }

  public int getUnreadCnt(String paramString)
  {
    if (paramString == null);
    while (this.mapUnReadMsgCnt.get(paramString) == null)
      return 0;
    return ((Integer)this.mapUnReadMsgCnt.get(paramString)).intValue();
  }

  public String getUserAvatar(String paramString)
  {
    if ((paramString == null) || (paramString.length() == 0))
      return null;
    return BaseUserInfoPool.getAvatar(paramString);
  }

  public UserInfo getUserInfoByName(String paramString)
  {
    if ((paramString != null) && (!paramString.equalsIgnoreCase("")))
      return (UserInfo)this.mapATUser.get(paramString);
    return null;
  }

  public void handleConnect()
  {
    this.connected = true;
    this.connecting = false;
    if (!this.loginSuccess)
    {
      login();
      this.autoLogin = false;
    }
  }

  public boolean hasCheckIn(String paramString)
  {
    if (paramString == null);
    while (true)
    {
      return false;
      for (int i = 0; i < this.lstHasCheckin.size(); i++)
        if (((String)this.lstHasCheckin.get(i)).equalsIgnoreCase(paramString))
          return true;
    }
  }

  public boolean hasEnableGroups(String paramString)
  {
    if ((paramString == null) || (this.mLstEnableGroups == null));
    while (true)
    {
      return false;
      for (int i = 0; i < this.mLstEnableGroups.size(); i++)
        if (((String)this.mLstEnableGroups.get(i)).equalsIgnoreCase(paramString))
          return true;
    }
  }

  public boolean hasEnabledGroup(String paramString)
  {
    if ((paramString == null) || (this.mLstEnableGroups == null));
    while (true)
    {
      return false;
      for (int i = 0; i < this.mLstEnableGroups.size(); i++)
        if (((String)this.mLstEnableGroups.get(i)).equalsIgnoreCase(paramString))
          return true;
    }
  }

  public void initEnableGroups()
  {
    String str = GlobalCfg.getInstance(this.mContext).getEnableGroups();
    String[] arrayOfString;
    if (str != null)
    {
      arrayOfString = str.split("_");
      if (arrayOfString != null)
        break label28;
    }
    while (true)
    {
      return;
      label28: for (int i = 0; i < arrayOfString.length; i++)
        this.mLstEnableGroups.add(arrayOfString[i]);
    }
  }

  public void initGroup()
  {
    List localList = IMContacts.getInstance().getWatchedGroupContacts();
    if (localList != null)
      for (int i = 0; i < localList.size(); i++)
        this.mapGroupInfo.put(((GroupInfo)localList.get(i)).groupId, localList.get(i));
  }

  public void initService(Context paramContext)
  {
    this.mContext = paramContext;
  }

  public void initUnReadMsg()
  {
    String str1 = GlobalCfg.getInstance(this.mContext).getUnReadID();
    String str2 = GlobalCfg.getInstance(this.mContext).getUnReadCnt();
    String[] arrayOfString1;
    if ((str1 != null) && (str2 != null))
    {
      arrayOfString1 = str1.split("_");
      if (arrayOfString1 != null)
        break label43;
    }
    while (true)
    {
      return;
      label43: String[] arrayOfString2 = str2.split("_");
      if ((arrayOfString2 != null) && (arrayOfString2.length == arrayOfString1.length))
        for (int i = 0; i < arrayOfString1.length; i++)
          if ((arrayOfString1[i] != null) && (arrayOfString1[i].length() > 0) && (arrayOfString2[i] != null) && (arrayOfString2[i].length() > 0))
          {
            int j = Integer.valueOf(arrayOfString2[i]).intValue();
            this.mTotalUnReadMsgCnt = (j + this.mTotalUnReadMsgCnt);
            this.mapUnReadMsgCnt.put(arrayOfString1[i], Integer.valueOf(j));
          }
    }
  }

  public boolean isCheckin(int paramInt)
  {
    return paramInt == 1;
  }

  public void leaveGroup(String paramString)
  {
    if (paramString == null);
    int i;
    do
    {
      return;
      i = hasInTempWatchGroups(paramString);
    }
    while (i == -1);
    this.mLstTempWatchGroups.remove(i);
  }

  public void likeBarrage(IMMessage paramIMMessage)
  {
    if (!CloudCenter.getInstance().isLogin())
      Toast.makeText(this.mContext, "登录后才能点赞", 1).show();
    while ((paramIMMessage == null) || (paramIMMessage.mMsgId == null))
      return;
    new HashMap().put("id", paramIMMessage.mMsgId);
    DataManager.getInstance().getData("POST_LIKE_BARRAGE", this, null);
  }

  public void loadBarrage(int paramInt, RootNode.IInfoUpdateEventListener paramIInfoUpdateEventListener)
  {
    HashMap localHashMap = new HashMap();
    localHashMap.put("id", String.valueOf(paramInt));
    this.mBarraeProgramId = paramInt;
    DataManager.getInstance().getData("GET_PROGRAM_BARRAGE", this, localHashMap);
    if (paramIInfoUpdateEventListener != null)
      InfoManager.getInstance().root().registerInfoUpdateListener(paramIInfoUpdateEventListener, 8);
  }

  public void loadGroupInfo(String paramString, RootNode.IInfoUpdateEventListener paramIInfoUpdateEventListener)
  {
    if ((paramString != null) && (!paramString.equalsIgnoreCase("")))
      IMDataLoadWrapper.loadGroupInfo(paramString, this);
    if (paramIInfoUpdateEventListener != null)
      InfoManager.getInstance().root().registerInfoUpdateListener(paramIInfoUpdateEventListener, 6);
  }

  public void loadGroupMembers(String paramString, int paramInt1, int paramInt2)
  {
    this.mCurrentGroupId = paramString;
    IMDataLoadWrapper.loadGroupUserList(paramString, paramInt1, paramInt2, this);
  }

  public void loadLatestGroupMsgFromNet(String paramString1, String paramString2)
  {
    if ((paramString1 == null) || (paramString2 == null) || (paramString1.equalsIgnoreCase("")) || (paramString2.equalsIgnoreCase("")))
      return;
    HashMap localHashMap = new HashMap();
    localHashMap.put("groupId", paramString1);
    localHashMap.put("msgId", paramString2);
    DataManager.getInstance().getData("get_im_group_latest_msg", this, localHashMap);
  }

  public IMMessage loadLatestMsg(String paramString, int paramInt)
  {
    if (paramString == null)
      return null;
    List localList1 = (List)this.mapMessage.get(paramString);
    List localList3;
    if (localList1 == null)
    {
      if (paramInt == 1)
        localList3 = IMDatabaseDS.getInstance().getGroupConversation(paramString, this.MSG_CNT_PER_PAGE);
      while (localList3 != null)
      {
        Collections.reverse(localList3);
        int j = 0;
        while (true)
          if (j < localList3.size())
          {
            if ((((IMMessage)localList3.get(j)).mFromAvatar == null) || (((IMMessage)localList3.get(j)).mFromAvatar.equalsIgnoreCase("")))
              ((IMMessage)localList3.get(j)).mFromAvatar = getUserAvatar(((IMMessage)localList3.get(j)).mFromID);
            j++;
            continue;
            if (paramInt != 0)
              break label292;
            UserInfo localUserInfo = InfoManager.getInstance().getUserProfile().getUserInfo();
            if (localUserInfo == null)
              break label292;
            localList3 = IMDatabaseDS.getInstance().getPrivateConversation(localUserInfo.userKey, paramString, this.MSG_CNT_PER_PAGE);
            break;
          }
        this.mapMessage.put(paramString, localList3);
      }
    }
    for (List localList2 = localList3; ; localList2 = localList1)
    {
      if (localList2 != null)
      {
        int i = localList2.size();
        if (i > 0)
        {
          if (paramInt == 1)
            loadLatestGroupMsgFromNet(((IMMessage)localList2.get(i - 1)).mFromGroupId, ((IMMessage)localList2.get(i - 1)).mMsgId);
          while (true)
          {
            return (IMMessage)localList2.get(i - 1);
            if (paramInt != 0);
          }
        }
      }
      return null;
      label292: localList3 = localList1;
      break;
    }
  }

  public void loadLatestPeerMsgFromNet()
  {
    UserInfo localUserInfo = InfoManager.getInstance().getUserProfile().getUserInfo();
    if (localUserInfo == null)
      return;
    HashMap localHashMap = new HashMap();
    localHashMap.put("userid", localUserInfo.userKey);
    DataManager.getInstance().getData("get_im_peer_latest_msg", this, localHashMap);
  }

  public List<IMMessage> loadMoreGroupMsgFromDB(String paramString, int paramInt)
  {
    int i = 0;
    Object localObject;
    if (paramString == null)
      localObject = null;
    do
    {
      return localObject;
      if (InfoManager.getInstance().getUserProfile().getUserInfo() == null)
        return null;
      localObject = (List)this.mapMessage.get(paramString);
      if (localObject == null)
      {
        List localList2 = IMDatabaseDS.getInstance().getGroupConversation(paramString, this.MSG_CNT_PER_PAGE);
        if (localList2 == null)
          break;
        Collections.reverse(localList2);
        while (i < localList2.size())
        {
          if ((((IMMessage)localList2.get(i)).mFromAvatar == null) || (((IMMessage)localList2.get(i)).mFromAvatar.equalsIgnoreCase("")))
            ((IMMessage)localList2.get(i)).mFromAvatar = getUserAvatar(((IMMessage)localList2.get(i)).mFromID);
          i++;
        }
        this.mapMessage.put(paramString, localList2);
        return localList2;
      }
      if (((List)localObject).size() <= 0)
        break;
      if (((IMMessage)((List)localObject).get(0)).msgSeq == paramInt)
      {
        List localList1 = IMDatabaseDS.getInstance().getGroupConversationLessThan(paramString, this.MSG_CNT_PER_PAGE, paramInt);
        if ((localList1 == null) || (localList1.size() <= 0))
          break;
        Collections.reverse(localList1);
        ((List)localObject).addAll(0, localList1);
        for (int j = 0; j < ((List)localObject).size(); j++)
          if ((((IMMessage)((List)localObject).get(j)).mFromAvatar == null) || (((IMMessage)((List)localObject).get(j)).mFromAvatar.equalsIgnoreCase("")))
            ((IMMessage)((List)localObject).get(j)).mFromAvatar = getUserAvatar(((IMMessage)((List)localObject).get(j)).mFromID);
        return localList1;
      }
    }
    while (paramInt == -1);
    return null;
  }

  public List<IMMessage> loadMoreGroupMsgFromNet(String paramString1, String paramString2)
  {
    Object localObject;
    if (paramString1 == null)
      localObject = null;
    do
    {
      return localObject;
      if (paramString2 != null)
        break;
      localObject = (List)this.mapMessage.get(paramString1);
    }
    while ((localObject != null) && (((List)localObject).size() > 0));
    HashMap localHashMap = new HashMap();
    localHashMap.put("group", paramString1);
    localHashMap.put("fetchsize", String.valueOf(this.IM_HISTORY_SIZE));
    DataManager.getInstance().getData("GET_IM_HISTORY_FROM_SERVER", this, localHashMap);
    return null;
  }

  public void loadMoreGroupMsgFromNet(String paramString, int paramInt)
  {
    if (paramString == null)
      return;
    HashMap localHashMap = new HashMap();
    localHashMap.put("group", paramString);
    localHashMap.put("fetchsize", String.valueOf(paramInt));
    DataManager.getInstance().getData("GET_IM_HISTORY_FROM_SERVER", this, localHashMap);
  }

  public List<IMMessage> loadMoreUserMsgFromDB(String paramString, int paramInt)
  {
    int i = 0;
    Object localObject;
    if (paramString == null)
      localObject = null;
    do
    {
      return localObject;
      UserInfo localUserInfo = InfoManager.getInstance().getUserProfile().getUserInfo();
      if (localUserInfo == null)
        return null;
      localObject = (List)this.mapMessage.get(paramString);
      if (localObject == null)
      {
        List localList2 = IMDatabaseDS.getInstance().getPrivateConversation(localUserInfo.userKey, paramString, this.MSG_CNT_PER_PAGE);
        if (localList2 == null)
          break;
        Collections.reverse(localList2);
        while (i < localList2.size())
        {
          if ((((IMMessage)localList2.get(i)).mFromAvatar == null) || (((IMMessage)localList2.get(i)).mFromAvatar.equalsIgnoreCase("")))
            ((IMMessage)localList2.get(i)).mFromAvatar = getUserAvatar(((IMMessage)localList2.get(i)).mFromID);
          i++;
        }
        this.mapMessage.put(paramString, localList2);
        return localList2;
      }
      if (((List)localObject).size() <= 0)
        break;
      if (((IMMessage)((List)localObject).get(0)).msgSeq == paramInt)
      {
        List localList1 = IMDatabaseDS.getInstance().getPrivateConversationLessThan(localUserInfo.userKey, paramString, this.MSG_CNT_PER_PAGE, paramInt);
        if ((localList1 == null) || (localList1.size() <= 0))
          break;
        Collections.reverse(localList1);
        ((List)localObject).addAll(0, localList1);
        for (int j = 0; j < ((List)localObject).size(); j++)
          if ((((IMMessage)((List)localObject).get(j)).mFromAvatar == null) || (((IMMessage)((List)localObject).get(j)).mFromAvatar.equalsIgnoreCase("")))
            ((IMMessage)((List)localObject).get(j)).mFromAvatar = getUserAvatar(((IMMessage)((List)localObject).get(j)).mFromID);
        return localList1;
      }
    }
    while (paramInt == -1);
    return null;
  }

  public void loadMoreUserMsgFromNet(String paramString)
  {
    if (paramString == null);
    UserInfo localUserInfo;
    do
    {
      return;
      localUserInfo = InfoManager.getInstance().getUserProfile().getUserInfo();
    }
    while (localUserInfo == null);
    HashMap localHashMap = new HashMap();
    localHashMap.put("sender", paramString);
    localHashMap.put("receiver", localUserInfo.userKey);
    DataManager.getInstance().getData("get_im_peer_history_from_server", this, localHashMap);
  }

  public void logout()
  {
    this.connected = false;
    this.connecting = false;
    this.loginSuccess = false;
    if (this.mWebSocket != null)
      this.mWebSocket.disconnect();
  }

  public void onNetChanged(String paramString)
  {
    if (paramString == null);
    do
    {
      return;
      if ((paramString.equalsIgnoreCase("nonet")) || (paramString.equalsIgnoreCase(this.mLastNetType)))
      {
        this.mLastNetType = paramString;
        return;
      }
      this.mLastNetType = paramString;
    }
    while (this.connected);
    _connect();
  }

  public void onRecvResult(Result paramResult, Object paramObject1, IResultToken paramIResultToken, Object paramObject2)
  {
    String str1 = paramIResultToken.getType();
    String str6;
    List localList4;
    IMMessage localIMMessage7;
    if (paramResult.getSuccess())
    {
      if (str1.equalsIgnoreCase("get_group_info"))
      {
        GroupInfo localGroupInfo3 = (GroupInfo)paramResult.getData();
        if (localGroupInfo3 != null)
        {
          addGroupInfo(localGroupInfo3);
          InfoManager.getInstance().root().setInfoUpdate(6);
          IMContacts.getInstance().updateGroupInfo(localGroupInfo3.groupId);
        }
      }
      do
      {
        String str7;
        do
        {
          List localList6;
          do
          {
            String str8;
            List localList7;
            do
            {
              return;
              if (!str1.equalsIgnoreCase("GET_PROGRAM_BARRAGE"))
                break;
              str8 = (String)((Map)paramObject2).get("id");
              localList7 = (List)paramResult.getData();
            }
            while ((localList7 == null) || (str8 == null) || (Integer.valueOf(str8).intValue() != this.mBarraeProgramId) || (localList7.size() != 2));
            this.mLstTextBarrage = ((List)localList7.get(0));
            this.mLstImageBarrage = ((List)localList7.get(1));
            InfoManager.getInstance().root().setInfoUpdate(8);
            return;
            if (str1.equalsIgnoreCase("POST_PROGRAM_BARRAGE"))
            {
              this.mLstSendImageBarrage = ((IMMessage)paramResult.getData());
              InfoManager.getInstance().root().setInfoUpdate(9);
              Toast.makeText(this.mContext, "发送弹幕成功", 1).show();
              return;
            }
            if (!str1.equalsIgnoreCase("get_group_users"))
              break;
            localList6 = (List)paramResult.getData();
          }
          while (localList6 == null);
          this.mCurrentGroupUsers = localList6;
          for (int i2 = 0; i2 < localList6.size(); i2++)
          {
            UserInfo localUserInfo2 = (UserInfo)localList6.get(i2);
            BaseUserInfoPool.putBaseInfo(localUserInfo2.userKey, localUserInfo2.snsInfo.sns_avatar, localUserInfo2.snsInfo.sns_gender);
          }
          InfoManager.getInstance().root().setInfoUpdate(7);
          return;
          if (!str1.equalsIgnoreCase("GET_IM_SERVER"))
            break;
          str7 = (String)paramResult.getData();
        }
        while ((str7 == null) || (str7.equalsIgnoreCase("")));
        this.connectUrl = str7;
        _connect();
        return;
        if (!str1.equalsIgnoreCase("GET_IM_HISTORY_FROM_SERVER"))
          break;
        str6 = (String)((HashMap)paramObject2).get("group");
        localList4 = (List)paramResult.getData();
      }
      while ((str6 == null) || (localList4 == null) || (localList4.size() <= 0));
      localIMMessage7 = getLatestGroupMessage(str6);
      if (localIMMessage7 == null)
        break label1511;
    }
    label1511: for (long l4 = localIMMessage7.publish; ; l4 = 0L)
    {
      boolean bool = IMContacts.getInstance().hasWatchedGroup(str6);
      for (int i1 = 0; i1 < localList4.size(); i1++)
      {
        IMMessage localIMMessage8 = (IMMessage)localList4.get(i1);
        if ((localIMMessage8.isGroupMsg()) && (l4 < localIMMessage8.publish))
        {
          if (bool)
            LatestMessages.putMessage(localIMMessage8.mFromGroupId, localIMMessage8);
          long l5 = 1L + InfoManager.getInstance().getMsgSeq();
          localIMMessage8.msgSeq = l5;
          InfoManager.getInstance().setMsgSeq(l5);
          if (localIMMessage8.mGroupName == null)
          {
            GroupInfo localGroupInfo2 = (GroupInfo)this.mapGroupInfo.get(localIMMessage8.mFromGroupId);
            if (localGroupInfo2 != null)
              localIMMessage8.mGroupName = localGroupInfo2.groupName;
          }
        }
      }
      List localList5 = (List)this.mapMessage.get(str6);
      if ((localList5 == null) || (localList5.size() == 0))
      {
        this.mapMessage.put(str6, localList4);
        dispatchIMEvent("RECV_LIST_MSG", localList4);
      }
      while (true)
      {
        IMDatabaseDS.getInstance().appendListGroupMessage(localList4, false);
        return;
        localList5.addAll(localList4);
        dispatchIMEvent("RECV_LIST_MSG", localList4);
      }
      if (str1.equalsIgnoreCase("get_im_base_user_info"))
      {
        String str5 = (String)((HashMap)paramObject2).get("user");
        UserInfo localUserInfo1 = (UserInfo)paramResult.getData();
        if ((localUserInfo1 == null) || (str5 == null))
          break;
        BaseUserInfoPool.putBaseInfo(str5, localUserInfo1.snsInfo.sns_avatar, localUserInfo1.snsInfo.sns_gender);
        return;
      }
      if (str1.equalsIgnoreCase("get_im_peer_latest_msg"))
      {
        String str4 = (String)((HashMap)paramObject2).get("userid");
        List localList3 = (List)paramResult.getData();
        if ((localList3 == null) || (localList3.size() <= 0))
          break;
        for (int m = 0; m < localList3.size(); m++)
        {
          UnreadMessagesFromNet localUnreadMessagesFromNet2 = (UnreadMessagesFromNet)localList3.get(m);
          int n = localUnreadMessagesFromNet2.getCount();
          IMMessage localIMMessage5 = localUnreadMessagesFromNet2.getMessage();
          if ((n > 0) && (localIMMessage5 != null))
          {
            IMMessage localIMMessage6 = getLatestUserMessage(str4);
            if ((localIMMessage6 == null) || (localIMMessage6.publish < localIMMessage5.publish))
            {
              if ((localIMMessage5.mFromAvatar == null) || (localIMMessage5.mFromAvatar.equalsIgnoreCase("")))
                localIMMessage5.mFromAvatar = getUserAvatar(localIMMessage5.mFromID);
              localIMMessage5.mToUserId = str4;
              LatestMessages.putMessage(localIMMessage5.mFromID, localIMMessage5);
              saveUnReadMsg(localIMMessage5.mFromID, n);
              IMContacts.getInstance().addRecentUserContacts(localIMMessage5.mFromID, localIMMessage5.mFromName, localIMMessage5.mFromAvatar);
              loadMoreUserMsgFromNet(localIMMessage5.mFromID);
            }
          }
        }
        storeUnReadMsgCnt();
        return;
      }
      if (str1.equalsIgnoreCase("get_im_group_latest_msg"))
      {
        String str3 = (String)((HashMap)paramObject2).get("groupId");
        UnreadMessagesFromNet localUnreadMessagesFromNet1 = (UnreadMessagesFromNet)paramResult.getData();
        if (localUnreadMessagesFromNet1 == null)
          break;
        IMMessage localIMMessage3 = localUnreadMessagesFromNet1.getMessage();
        int j = localUnreadMessagesFromNet1.getCount();
        if ((str3 == null) || (localIMMessage3 == null) || (j <= 0))
          break;
        if (localIMMessage3.mGroupName == null)
        {
          GroupInfo localGroupInfo1 = (GroupInfo)this.mapGroupInfo.get(localIMMessage3.mFromGroupId);
          if (localGroupInfo1 != null)
            localIMMessage3.mGroupName = localGroupInfo1.groupName;
        }
        if ((localIMMessage3.mMessage != null) && (!localIMMessage3.mMessage.equalsIgnoreCase("")));
        for (int k = 1; ; k = 0)
        {
          long l3 = 0L;
          IMMessage localIMMessage4 = getLatestGroupMessage(str3);
          if (localIMMessage4 != null)
            l3 = localIMMessage4.publish;
          if (k != 0)
            LatestMessages.putMessage(str3, localIMMessage3);
          IMContacts.getInstance().addRecentContacts(str3);
          if (l3 >= localIMMessage3.publish)
            break;
          saveUnReadMsg(str3, j);
          storeUnReadMsgCnt();
          loadMoreGroupMsgFromNet(str3, j);
          return;
        }
      }
      if (!str1.equalsIgnoreCase("get_im_peer_history_from_server"))
        break;
      String str2 = (String)((HashMap)paramObject2).get("sender");
      List localList1 = (List)paramResult.getData();
      if ((str2 == null) || (localList1 == null) || (localList1.size() <= 0))
        break;
      IMMessage localIMMessage1 = getLatestUserMessage(str2);
      if (localIMMessage1 != null);
      for (long l1 = localIMMessage1.publish; ; l1 = 0L)
      {
        for (int i = 0; i < localList1.size(); i++)
        {
          IMMessage localIMMessage2 = (IMMessage)localList1.get(i);
          if (l1 < localIMMessage2.publish)
          {
            LatestMessages.putMessage(localIMMessage2.mFromID, localIMMessage2);
            if ((localIMMessage2.mFromAvatar == null) || (localIMMessage2.mFromAvatar.equalsIgnoreCase("")))
              localIMMessage2.mFromAvatar = getUserAvatar(localIMMessage2.mFromID);
            long l2 = 1L + InfoManager.getInstance().getMsgSeq();
            localIMMessage2.msgSeq = l2;
            InfoManager.getInstance().setMsgSeq(l2);
          }
        }
        List localList2 = (List)this.mapMessage.get(str2);
        if ((localList2 == null) || (localList2.size() == 0))
          this.mapMessage.put(str2, localList1);
        while (true)
        {
          IMDatabaseDS.getInstance().appendListPrivateMessage(localList1, false);
          return;
          localList2.addAll(localList1);
        }
        if (!str1.equalsIgnoreCase("POST_PROGRAM_BARRAGE"))
          break;
        Toast.makeText(this.mContext, "发送弹幕失败", 1).show();
        return;
      }
    }
  }

  public void ping()
  {
    if ((this.mWebSocket != null) && (this.loginSuccess))
      try
      {
        long l = System.currentTimeMillis() / 1000L;
        if (l - this.lastPingTime < this.PING_INTERVAL)
          return;
        this.lastPingTime = l;
        if (this.mPingMsg == null)
        {
          JSONObject localJSONObject = new JSONObject();
          localJSONObject.put("event", "ping");
          localJSONObject.put("from", InfoManager.getInstance().getUserProfile().getUserInfo().userKey);
          this.mPingMsg = localJSONObject.toJSONString();
        }
        if (this.mPingMsg != null)
        {
          this.mWebSocket.send(this.mPingMsg);
          return;
        }
      }
      catch (Exception localException)
      {
      }
  }

  public void registerIMEventListener(IMEventListener paramIMEventListener, String paramString)
  {
    List localList;
    if ((paramIMEventListener != null) && (paramString != null))
    {
      if (this.mapIMEventListeners.containsKey(paramString))
        localList = (List)this.mapIMEventListeners.get(paramString);
    }
    else
    {
      for (int i = 0; i < localList.size(); i++)
        if (localList.get(i) == paramIMEventListener)
          return;
      ((List)this.mapIMEventListeners.get(paramString)).add(paramIMEventListener);
      return;
    }
    ArrayList localArrayList = new ArrayList();
    localArrayList.add(paramIMEventListener);
    this.mapIMEventListeners.put(paramString, localArrayList);
  }

  public void saveUnReadMsg(String paramString, int paramInt)
  {
    if (paramString == null)
      return;
    Integer localInteger1 = (Integer)this.mapUnReadMsgCnt.get(paramString);
    if (localInteger1 != null);
    for (Integer localInteger2 = Integer.valueOf(paramInt + localInteger1.intValue()); ; localInteger2 = Integer.valueOf(1))
    {
      this.mTotalUnReadMsgCnt = (paramInt + this.mTotalUnReadMsgCnt);
      this.mapUnReadMsgCnt.put(paramString, localInteger2);
      return;
    }
  }

  public boolean sendBarrage(int paramInt1, String paramString1, String paramString2, int paramInt2)
  {
    if ((paramString1 == null) && (paramString2 == null))
      return false;
    if (paramString1 != null);
    try
    {
      if (InfoManager.getInstance().getMaxWordsInLiveRoom() < paramString1.length())
      {
        Toast.makeText(this.mContext, "超出字数范围,字数最长为" + InfoManager.getInstance().getMaxWordsInLiveRoom() + "个", 1).show();
        return false;
      }
      if (SharedCfg.getInstance().hitFilter(paramString1))
      {
        Toast.makeText(this.mContext, "消息中包含敏感词,敬请三思", 1).show();
        return false;
      }
      HashMap localHashMap = new HashMap();
      localHashMap.put("content", paramString1);
      localHashMap.put("timePoint", String.valueOf(paramInt2));
      UserInfo localUserInfo = InfoManager.getInstance().getUserProfile().getUserInfo();
      if (localUserInfo == null)
      {
        localHashMap.put("senderId", InfoManager.getInstance().getDeviceId());
        localHashMap.put("senderName", "");
        localHashMap.put("senderAvatar", "");
        localHashMap.put("senderGender", "f");
      }
      while (true)
      {
        localHashMap.put("image", paramString2);
        localHashMap.put("file", getFileByPath(paramString2));
        localHashMap.put("programId", String.valueOf(paramInt1));
        DataManager.getInstance().getData("POST_PROGRAM_BARRAGE", this, localHashMap);
        break;
        localHashMap.put("senderId", localUserInfo.userId);
        localHashMap.put("senderName", localUserInfo.snsInfo.sns_name);
        localHashMap.put("senderAvatar", localUserInfo.snsInfo.sns_avatar);
        localHashMap.put("senderGender", localUserInfo.snsInfo.sns_gender);
      }
    }
    catch (Exception localException)
    {
    }
    return true;
  }

  public boolean sendBarrage(String paramString1, String paramString2)
  {
    return sendBarrage(this.mAddProgramId, paramString1, paramString2, this.mAddTime);
  }

  public boolean sendFeedbackMessage(String paramString, GroupInfo paramGroupInfo)
  {
    if (paramString != null);
    try
    {
      if (!paramString.equalsIgnoreCase(""))
      {
        if (paramGroupInfo == null)
          return false;
        if (InfoManager.getInstance().getUserProfile().getUserInfo() != null)
        {
          if (InfoManager.getInstance().getUserProfile().getUserInfo().isBlocked())
          {
            Toast.makeText(this.mContext, "该账号被其它用户举报,您可以在新浪微博上@蜻蜓fm 投诉", 1).show();
            return false;
          }
          if (InfoManager.getInstance().getMaxWordsInLiveRoom() < paramString.length())
          {
            Toast.makeText(this.mContext, "超出字数范围,字数最长为" + InfoManager.getInstance().getMaxWordsInLiveRoom() + "个", 1).show();
            return false;
          }
          if (SharedCfg.getInstance().hitFilter(paramString))
          {
            Toast.makeText(this.mContext, "消息中包含敏感词,敬请三思", 1).show();
            return false;
          }
          if (this.mWebSocket != null)
          {
            JSONObject localJSONObject = IMMessage.buildIMMessage(paramString, InfoManager.getInstance().getUserProfile().getUserInfo(), paramGroupInfo);
            if (localJSONObject != null)
            {
              String str1 = getMsgId();
              if (str1 != null)
                this.mJsonMsg.put("id", str1);
              this.mJsonMsg.put("event", "group");
              this.mJsonMsg.put("fromGender", InfoManager.getInstance().getUserProfile().getUserInfo().snsInfo.sns_gender);
              this.mJsonMsg.put("from", InfoManager.getInstance().getUserProfile().getUserInfo().userKey);
              this.mJsonMsg.put("fromName", InfoManager.getInstance().getUserProfile().getUserInfo().snsInfo.sns_name);
              this.mJsonMsg.put("to", paramGroupInfo.groupId);
              JSONArray localJSONArray = new JSONArray();
              localJSONArray.add(localJSONObject);
              this.mJsonMsg.put("body", localJSONArray);
              String str2 = this.mJsonMsg.toJSONString();
              if (str2 != null)
              {
                this.mWebSocket.send(str2);
                return true;
              }
            }
          }
          else
          {
            _connect();
          }
        }
      }
      return false;
    }
    catch (Exception localException)
    {
    }
    return false;
  }

  public boolean sendGroupMsg(String paramString, GroupInfo paramGroupInfo, int paramInt)
  {
    if (paramString != null);
    try
    {
      if ((!paramString.equalsIgnoreCase("")) && (paramGroupInfo != null))
      {
        if (InfoManager.getInstance().getUserProfile().getUserInfo() == null)
          return false;
        if (InfoManager.getInstance().getUserProfile().getUserInfo().isBlocked())
        {
          Toast.makeText(this.mContext, "该账号被其它用户举报,您可以在新浪微博上@蜻蜓fm 投诉", 1).show();
          return false;
        }
        if (InfoManager.getInstance().getMaxWordsInLiveRoom() < paramString.length())
        {
          Toast.makeText(this.mContext, "超出字数范围,字数最长为" + InfoManager.getInstance().getMaxWordsInLiveRoom() + "个", 1).show();
          return false;
        }
        if (SharedCfg.getInstance().hitFilter(paramString))
        {
          Toast.makeText(this.mContext, "消息中包含敏感词,敬请三思", 1).show();
          return false;
        }
        if ((paramGroupInfo != null) && (paramGroupInfo.groupId != null) && (isCheckin(paramInt)))
        {
          if (hasCheckIn(paramGroupInfo.groupId))
            return false;
          this.lstHasCheckin.add(paramGroupInfo.groupId);
        }
        if (this.mWebSocket != null)
        {
          JSONObject localJSONObject = IMMessage.buildIMMessage(paramString, InfoManager.getInstance().getUserProfile().getUserInfo(), paramGroupInfo);
          if (localJSONObject != null)
          {
            String str1 = getMsgId();
            if (str1 != null)
              this.mJsonMsg.put("id", str1);
            this.mJsonMsg.put("event", "group");
            this.mJsonMsg.put("fromGender", InfoManager.getInstance().getUserProfile().getUserInfo().snsInfo.sns_gender);
            this.mJsonMsg.put("from", InfoManager.getInstance().getUserProfile().getUserInfo().userKey);
            this.mJsonMsg.put("fromName", InfoManager.getInstance().getUserProfile().getUserInfo().snsInfo.sns_name);
            this.mJsonMsg.put("to", paramGroupInfo.groupId);
            JSONArray localJSONArray = new JSONArray();
            localJSONArray.add(localJSONObject);
            this.mJsonMsg.put("body", localJSONArray);
            String str2 = this.mJsonMsg.toJSONString();
            if (str2 != null)
            {
              this.mWebSocket.send(str2);
              IMMessage localIMMessage = new IMMessage();
              localIMMessage.mFromID = InfoManager.getInstance().getUserProfile().getUserInfo().userKey;
              localIMMessage.mFromName = InfoManager.getInstance().getUserProfile().getUserInfo().snsInfo.sns_name;
              localIMMessage.mFromAvatar = InfoManager.getInstance().getUserProfile().getUserInfo().snsInfo.sns_avatar;
              localIMMessage.mFromGroupId = paramGroupInfo.groupId;
              localIMMessage.mMsgId = str1;
              IMMessage.parseData(localJSONObject, localIMMessage);
              long l = 1L + InfoManager.getInstance().getMsgSeq();
              localIMMessage.msgSeq = l;
              InfoManager.getInstance().setMsgSeq(l);
              LatestMessages.putMessage(localIMMessage.mFromGroupId, localIMMessage);
              List localList = (List)this.mapMessage.get(paramGroupInfo.groupId);
              if (localList != null)
                localList.add(localIMMessage);
              while (true)
              {
                IMDatabaseDS.getInstance().appendGroupMessage(localIMMessage, false);
                String str3 = QTLogger.getInstance().buildIMSendGroupLog(localIMMessage.mFromID, localIMMessage.mFromGroupId);
                if (str3 != null)
                  LogModule.getInstance().send("GroupMsg", str3);
                QTMSGManage.getInstance().sendStatistcsMessage("groupMsg", "send");
                return true;
                ArrayList localArrayList = new ArrayList();
                localArrayList.add(localIMMessage);
                this.mapMessage.put(paramGroupInfo.groupId, localArrayList);
              }
            }
          }
          String str4 = InfoManager.getInstance().getWeiboIdByGroupId(paramGroupInfo.groupId);
          if (str4 != null)
            WeiboChat.getInstance().comment(str4, paramString);
        }
        else
        {
          _connect();
          Toast.makeText(this.mContext, "未连接上直播间，发送消息失败", 1).show();
        }
      }
      else
      {
        return false;
      }
    }
    catch (Exception localException)
    {
    }
    return false;
  }

  public boolean sendUserMsg(String paramString, UserInfo paramUserInfo, int paramInt)
  {
    if (paramString != null);
    try
    {
      if ((!paramString.equalsIgnoreCase("")) && (paramUserInfo != null))
      {
        if (isSelf(paramUserInfo.userKey))
          return false;
        if (InfoManager.getInstance().getUserProfile().getUserInfo().isBlocked())
        {
          Toast.makeText(this.mContext, "该账号被其它用户举报,您可以在新浪微博上@蜻蜓fm 投诉", 1).show();
          return false;
        }
        if (InfoManager.getInstance().getMaxWordsInLiveRoom() < paramString.length())
        {
          Toast.makeText(this.mContext, "超出字数范围,字数最长为" + InfoManager.getInstance().getMaxWordsInLiveRoom() + "个", 1).show();
          return false;
        }
        if (SharedCfg.getInstance().hitFilter(paramString))
        {
          Toast.makeText(this.mContext, "消息中包含敏感词,敬请三思", 1).show();
          return false;
        }
        if (this.mWebSocket != null)
        {
          JSONObject localJSONObject = IMMessage.buildIMMessage(paramString, InfoManager.getInstance().getUserProfile().getUserInfo(), paramUserInfo);
          if (localJSONObject != null)
          {
            String str1 = getMsgId();
            if (str1 != null)
              this.mJsonMsg.put("id", str1);
            this.mJsonMsg.put("event", "peer");
            this.mJsonMsg.put("fromName", InfoManager.getInstance().getUserProfile().getUserInfo().snsInfo.sns_name);
            this.mJsonMsg.put("fromGender", InfoManager.getInstance().getUserProfile().getUserInfo().snsInfo.sns_gender);
            this.mJsonMsg.put("from", InfoManager.getInstance().getUserProfile().getUserInfo().userKey);
            this.mJsonMsg.put("to", paramUserInfo.userKey);
            JSONArray localJSONArray = new JSONArray();
            localJSONArray.add(localJSONObject);
            this.mJsonMsg.put("body", localJSONArray);
            String str2 = this.mJsonMsg.toJSONString();
            if (str2 != null)
            {
              this.mWebSocket.send(str2);
              IMMessage localIMMessage = new IMMessage();
              localIMMessage.mFromID = InfoManager.getInstance().getUserProfile().getUserInfo().userKey;
              localIMMessage.mFromName = InfoManager.getInstance().getUserProfile().getUserInfo().snsInfo.sns_name;
              localIMMessage.mFromAvatar = InfoManager.getInstance().getUserProfile().getUserInfo().snsInfo.sns_avatar;
              IMMessage.parseData(localJSONObject, localIMMessage);
              localIMMessage.mToUserId = paramUserInfo.userKey;
              localIMMessage.mMsgId = str1;
              long l = 1L + InfoManager.getInstance().getMsgSeq();
              localIMMessage.msgSeq = l;
              InfoManager.getInstance().setMsgSeq(l);
              List localList = (List)this.mapMessage.get(paramUserInfo.userKey);
              if (localList != null)
                localList.add(localIMMessage);
              while (true)
              {
                LatestMessages.putMessage(localIMMessage.mToUserId, localIMMessage);
                IMDatabaseDS.getInstance().appendPrivateMessage(localIMMessage, false);
                String str3 = QTLogger.getInstance().buildIMSendUserLog(localIMMessage.mFromID, localIMMessage.mToUserId);
                if (str3 != null)
                  LogModule.getInstance().send("UserMsg", str3);
                QTMSGManage.getInstance().sendStatistcsMessage("userMsg", "send");
                return true;
                ArrayList localArrayList = new ArrayList();
                localArrayList.add(localIMMessage);
                this.mapMessage.put(paramUserInfo.userKey, localArrayList);
              }
            }
          }
        }
        else
        {
          _connect();
          Toast.makeText(this.mContext, "未连接上直播间，发送消息失败", 1).show();
        }
      }
      else
      {
        return false;
      }
    }
    catch (Exception localException)
    {
    }
    return false;
  }

  public void setUser(String paramString, UserInfo paramUserInfo)
  {
    if ((paramString != null) && (paramUserInfo != null) && (!paramString.equalsIgnoreCase("")))
      this.mapATUser.put(paramString, paramUserInfo);
  }

  public void storeUnReadMsgCnt()
  {
    if (this.mapUnReadMsgCnt.size() > 0)
    {
      Iterator localIterator = this.mapUnReadMsgCnt.entrySet().iterator();
      Object localObject1 = "";
      Object localObject2 = "";
      int i = 0;
      while (localIterator.hasNext())
      {
        Map.Entry localEntry = (Map.Entry)localIterator.next();
        int j = ((Integer)localEntry.getValue()).intValue();
        String str1 = (String)localObject2 + (String)localEntry.getKey();
        String str2 = (String)localObject1 + String.valueOf(j);
        if (i != -1 + this.mapUnReadMsgCnt.size())
        {
          str1 = str1 + "_";
          str2 = str2 + "_";
        }
        i++;
        localObject1 = str2;
        localObject2 = str1;
      }
      GlobalCfg.getInstance(this.mContext).setUnReadCnt((String)localObject1);
      GlobalCfg.getInstance(this.mContext).setUnReadID((String)localObject2);
    }
  }

  public void unRegisterIMEventListener(String paramString, IMEventListener paramIMEventListener)
  {
    List localList;
    if ((paramIMEventListener != null) && (this.mapIMEventListeners.containsKey(paramString)))
    {
      localList = (List)this.mapIMEventListeners.get(paramString);
      if (localList == null);
    }
    for (int i = 0; ; i++)
      if (i < localList.size())
      {
        if (localList.get(i) == paramIMEventListener)
          localList.remove(i);
      }
      else
        return;
  }

  private class DispatchHandler extends Handler
  {
    public DispatchHandler(Looper arg2)
    {
      super();
    }

    public void handleMessage(Message paramMessage)
    {
      switch (paramMessage.what)
      {
      default:
      case 0:
      case 1:
      case 2:
      }
      do
      {
        return;
        IMAgent.this.dispatchIMEvent("RECV_SINGLE_MSG", (IMMessage)paramMessage.obj);
        return;
        IMAgent.this.dispatchIMEvent("RECV_LIST_MSG", (List)paramMessage.obj);
        return;
      }
      while (IMAgent.this.mContext == null);
      Toast.makeText(IMAgent.this.mContext, "遇上了点网络问题，连接直播间失败。", 1).show();
    }
  }

  public static abstract interface IMEventListener
  {
    public abstract boolean onIMEvent(String paramString, IMMessage paramIMMessage);

    public abstract boolean onIMListMsg(String paramString, List<IMMessage> paramList);
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.qtradio.im.IMAgent
 * JD-Core Version:    0.6.2
 */