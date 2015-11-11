package fm.qingting.qtradio.room;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;
import com.umeng.analytics.MobclickAgent;
import fm.qingting.framework.data.DataManager;
import fm.qingting.framework.data.IResultRecvHandler;
import fm.qingting.framework.data.IResultToken;
import fm.qingting.framework.data.Result;
import fm.qingting.qtradio.controller.ControllerManager;
import fm.qingting.qtradio.log.LogModule;
import fm.qingting.qtradio.logger.QTLogger;
import fm.qingting.qtradio.model.BroadcasterNode;
import fm.qingting.qtradio.model.ChannelNode;
import fm.qingting.qtradio.model.InfoManager;
import fm.qingting.qtradio.model.Node;
import fm.qingting.qtradio.model.ProgramNode;
import fm.qingting.qtradio.model.RadioChannelNode;
import fm.qingting.qtradio.model.RecommendItemNode;
import fm.qingting.qtradio.model.RootNode;
import fm.qingting.qtradio.model.ShareBean;
import fm.qingting.qtradio.model.SharedCfg;
import fm.qingting.qtradio.share.ProgramLocation;
import fm.qingting.qtradio.share.ShareUtil;
import fm.qingting.qtradio.weiboAgent.WeiboAgent;
import fm.qingting.social.SocialEventListener;
import fm.qingting.social.api.SinaWeiboApi;
import fm.qingting.social.auth.SinaWeiboAuth;
import fm.qingting.utils.DateUtil;
import fm.qingting.utils.DeviceInfo;
import fm.qingting.utils.QTMSGManage;
import fm.qingting.utils.TimeUtil;
import fm.qingting.utils.ViewCaptureUtil;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import weibo4android.Comment;
import weibo4android.User;

public class WeiboChat extends Chat
  implements IResultRecvHandler
{
  private static final String ParaCatid = "catid";
  private static final String ParaChannelid = "channelid";
  private static final String ParaDeviceid = "deviceid";
  private static final String ParaFrom = "from";
  private static final String ParaOs = "os";
  private static final String ParaPagetype = "pagetype";
  private static final String ParaPid = "pid";
  private static final String ParaSubcatid = "subcatid";
  private static final String ParaTimestamp = "timestamp";
  private static final String TAG = "WeiboChat";
  private static String TAIL = " (签到自@蜻蜓FM)";
  public static WeiboChat _instance = null;
  private int COMMENTS_COUNT = 10;
  private boolean hasRestoredFromDB = false;
  private boolean loginSuccess = false;
  private List<String> lstFlowerUser = new ArrayList();
  private final Handler mHandler = new Handler()
  {
    public void handleMessage(Message paramAnonymousMessage)
    {
      if (paramAnonymousMessage == null)
        return;
      switch (paramAnonymousMessage.what)
      {
      case 4:
      default:
        return;
      case 1:
        Toast.makeText(InfoManager.getInstance().getContext(), "签到成功", 0).show();
        MobclickAgent.onEvent(InfoManager.getInstance().getContext(), "Checkin", "succ");
        WeiboChat.this.sendCheckinLog();
        return;
      case 2:
        Toast.makeText(InfoManager.getInstance().getContext(), "签到失败", 0).show();
        MobclickAgent.onEvent(InfoManager.getInstance().getContext(), "Checkin", "failed");
        return;
      case 3:
        Toast.makeText(InfoManager.getInstance().getContext(), "献花成功", 0).show();
        return;
      case 5:
      }
      QTMSGManage.getInstance().sendStatistcsMessage("publishComment");
    }
  };
  private String mRoomId = null;
  private String mSharedChannel;
  private String mSharedContentUrl;
  private String mSharedProgram;
  private String mSharedSentence;
  private String mUserId;
  private UserInfo mUserInfo;
  private String mWeiboId;
  private Map<String, String> mapComments = new HashMap();
  private Map<String, Boolean> mapFlowerInfo = new HashMap();
  private Map<String, String> mapRoomId = new HashMap();
  private Map<String, String> mapUserInfo = new HashMap();

  private void _sendWeibo(String paramString)
  {
  }

  private void addUser(User paramUser)
  {
    if (paramUser == null);
    String str1;
    String str2;
    do
    {
      String str3;
      String str4;
      do
      {
        return;
        if ((this.mUserInfo != null) || (this.mUserId == null))
          break;
        if (this.mUserId.equalsIgnoreCase(String.valueOf(paramUser.getId())))
        {
          this.mUserInfo = new UserInfo();
          this.mUserInfo.userId = InfoManager.getInstance().getDeviceId();
          this.mUserInfo.snsInfo.sns_avatar = paramUser.getProfileImageURL().toString();
          this.mUserInfo.snsInfo.sns_id = String.valueOf(paramUser.getId());
          this.mUserInfo.snsInfo.sns_name = paramUser.getName();
          this.mUserInfo.snsInfo.sns_site = "weibo";
          this.mUserInfo.snsInfo.sns_gender = paramUser.getGender();
          this.mUserInfo.snsInfo.signature = paramUser.getDescription();
          saveUserInfoToDB();
          return;
        }
        str3 = paramUser.getName();
        str4 = String.valueOf(paramUser.getId());
      }
      while ((str3 == null) || (str4 == null));
      this.mapUserInfo.put(str4, str3);
      return;
      str1 = paramUser.getName();
      str2 = String.valueOf(paramUser.getId());
    }
    while ((str1 == null) || (str2 == null));
    this.mapUserInfo.put(str2, str1);
  }

  private void addUserInfoLog()
  {
  }

  private boolean allowFlower(String paramString)
  {
    if (paramString == null)
      return false;
    for (int i = 0; ; i++)
    {
      if (i >= this.lstFlowerUser.size())
        break label47;
      if (((String)this.lstFlowerUser.get(i)).equalsIgnoreCase(paramString))
        break;
    }
    label47: return true;
  }

  private void commitComments(List<Comment> paramList)
  {
    if (paramList == null);
    while (paramList.size() == 0)
      return;
    String str = this.mRoomId;
    ArrayList localArrayList = new ArrayList();
    for (int i = 0; i < paramList.size(); i++)
    {
      ChatData localChatData = new ChatData();
      localChatData.content = ((Comment)paramList.get(i)).getText();
      localChatData.createTime = ((Comment)paramList.get(i)).getCreatedAt().getTime();
      localChatData.user = new UserInfo();
      localChatData.user.userId = String.valueOf(((Comment)paramList.get(i)).getUser().getId());
      localChatData.user.snsInfo.sns_site = "weibo";
      localChatData.user.snsInfo.sns_id = localChatData.user.userId;
      localChatData.user.snsInfo.sns_name = ((Comment)paramList.get(i)).getUser().getName();
      localChatData.user.snsInfo.sns_avatar = ((Comment)paramList.get(i)).getUser().getProfileImageURL().toString();
      localArrayList.add(localChatData);
    }
    Collections.sort(localArrayList, new ChatDataComparator());
    RoomDataCenter.getInstance().recvRoomData(1, localArrayList, str);
    RoomDataCenter.getInstance().recvRoomEvent(1, "RLRJ");
  }

  private static String composeTrackUrl(String paramString, int paramInt1, int paramInt2, ProgramLocation paramProgramLocation)
  {
    if (paramProgramLocation == null)
      return paramString;
    if (paramInt2 != 6);
    try
    {
      String str5 = URLEncoder.encode(paramString, "utf-8");
      paramString = str5;
      String str1 = "http://tracker.qingting.fm/share_audio_app?pagetype=" + paramInt1 + "&" + "timestamp" + "=" + DateUtil.getCurrentSeconds() + "&" + "from" + "=" + paramInt2 + "&" + "os" + "=1" + "&" + "deviceid" + "=" + DeviceInfo.getUniqueId(InfoManager.getInstance().getContext());
      String str2 = str1 + "&catid=" + paramProgramLocation.catId;
      String str3 = str2 + "&channelid=" + paramProgramLocation.channelId;
      String str4 = str3 + "&pid=" + paramProgramLocation.pId;
      return str4 + "&url=" + paramString;
    }
    catch (UnsupportedEncodingException localUnsupportedEncodingException)
    {
      while (true)
        localUnsupportedEncodingException.printStackTrace();
    }
    catch (NullPointerException localNullPointerException)
    {
      while (true)
        paramString = "undef";
    }
  }

  private void deleteUserInfo()
  {
    HashMap localHashMap = new HashMap();
    localHashMap.put("site", "weibo");
    DataManager.getInstance().getData("deletedb_user_info", null, localHashMap);
  }

  private static String getBroadcastors(ProgramNode paramProgramNode)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    int i;
    if (paramProgramNode.lstBroadcaster != null)
    {
      Iterator localIterator = paramProgramNode.lstBroadcaster.iterator();
      i = 1;
      if (localIterator.hasNext())
      {
        BroadcasterNode localBroadcasterNode = (BroadcasterNode)localIterator.next();
        if ((localBroadcasterNode.weiboName == null) || (localBroadcasterNode.weiboName.trim().length() <= 0) || (localBroadcasterNode.weiboName.equalsIgnoreCase("未知")))
          break label144;
        if (i == 0)
          localStringBuffer.append(",");
        localStringBuffer.append("@" + localBroadcasterNode.weiboName + " ");
      }
    }
    label144: for (int j = 0; ; j = i)
    {
      i = j;
      break;
      return localStringBuffer.toString();
    }
  }

  private String getCheckInLabel()
  {
    return "\"" + TimeUtil.msToDate9(System.currentTimeMillis()) + "签到\"";
  }

  private String getCheckInText(Node paramNode, boolean paramBoolean)
  {
    if (paramNode != null)
    {
      this.mSharedContentUrl = "http://qingting.fm";
      this.mSharedChannel = "";
      this.mSharedProgram = "";
      this.mSharedSentence = "";
      ProgramLocation localProgramLocation = new ProgramLocation();
      ProgramNode localProgramNode;
      if ((paramNode instanceof ChannelNode))
      {
        ChannelNode localChannelNode2 = (ChannelNode)paramNode;
        localProgramLocation.catId = localChannelNode2.categoryId;
        localProgramLocation.channelId = localChannelNode2.channelId;
        if (localChannelNode2.channelType == 0)
          this.mSharedContentUrl = ("http://qingting.fm/channels/" + localChannelNode2.channelId);
        this.mSharedChannel = localChannelNode2.title;
        if (paramBoolean)
          this.mSharedSentence = (getShareTitle(localChannelNode2.title, null) + " " + wrapPageUrl(this.mSharedContentUrl, localProgramLocation) + TAIL);
      }
      else if ((paramNode instanceof ProgramNode))
      {
        localProgramNode = (ProgramNode)paramNode;
        localProgramLocation.catId = localProgramNode.getCategoryId();
        localProgramLocation.pId = localProgramNode.id;
        if (!(localProgramNode.parent instanceof ChannelNode))
          break label509;
        ChannelNode localChannelNode1 = (ChannelNode)localProgramNode.parent;
        localProgramLocation.channelId = localChannelNode1.channelId;
        if (localChannelNode1.channelType != 0)
          break label464;
        this.mSharedContentUrl = ("http://qingting.fm/channels/" + localChannelNode1.channelId + "/programs/" + localProgramNode.id + "/date/" + TimeUtil.msToDate6(1000L * localProgramNode.getAbsoluteStartTime()));
        label305: this.mSharedChannel = localChannelNode1.title;
        label314: this.mSharedProgram = localProgramNode.title;
        if (!paramBoolean)
          break label753;
        String str = getBroadcastors(localProgramNode);
        if ((str == null) || (str.length() <= 0))
          break label698;
        this.mSharedSentence = (getShareTitle(this.mSharedChannel, this.mSharedProgram) + " 主播:" + str + " " + wrapPageUrl(this.mSharedContentUrl, localProgramLocation) + TAIL);
      }
      while (true)
      {
        return this.mSharedSentence;
        this.mSharedSentence = ("我正在  " + this.mSharedContentUrl + " 收听" + this.mSharedChannel + " (分享自@蜻蜓FM)");
        break;
        label464: this.mSharedContentUrl = ("http://qingting.fm/vchannels/" + localProgramNode.channelId + "/programs/" + localProgramNode.id);
        break label305;
        label509: if ((localProgramNode.parent instanceof RecommendItemNode))
        {
          this.mSharedContentUrl = ("http://qingting.fm/vchannels/" + localProgramNode.channelId + "/programs/" + localProgramNode.id);
          break label314;
        }
        if ((localProgramNode.parent instanceof RadioChannelNode))
        {
          RadioChannelNode localRadioChannelNode = (RadioChannelNode)localProgramNode.parent;
          localProgramLocation.channelId = localRadioChannelNode.channelId;
          this.mSharedContentUrl = ("http://qingting.fm/channels/" + localRadioChannelNode.channelId + " ");
          this.mSharedChannel = localRadioChannelNode.channelName;
          break label314;
        }
        if (localProgramNode.channelType != 1)
          break label314;
        this.mSharedContentUrl = ("http://qingting.fm/vchannels/" + localProgramNode.channelId + "/programs/" + localProgramNode.id);
        break label314;
        label698: this.mSharedSentence = (getShareTitle(this.mSharedChannel, this.mSharedProgram) + " " + wrapPageUrl(this.mSharedContentUrl, localProgramLocation) + TAIL);
        continue;
        label753: this.mSharedSentence = ("我正在  " + this.mSharedContentUrl + " 收听" + this.mSharedChannel + "里的" + this.mSharedProgram + " (分享自@蜻蜓FM)");
      }
    }
    return null;
  }

  private String getCheckinImagePath()
  {
    return ViewCaptureUtil.getViewPath();
  }

  private Context getContext()
  {
    return InfoManager.getInstance().getContext();
  }

  public static WeiboChat getInstance()
  {
    if (_instance == null)
      _instance = new WeiboChat();
    return _instance;
  }

  private String getPageUrl(ChannelNode paramChannelNode, ProgramNode paramProgramNode, String paramString)
  {
    if ((paramChannelNode == null) || (paramProgramNode == null) || (paramString == null))
      return null;
    int i = ShareUtil.getPlatFormNum(4);
    ProgramLocation localProgramLocation = new ProgramLocation();
    localProgramLocation.catId = paramProgramNode.getCategoryId();
    localProgramLocation.pId = paramProgramNode.id;
    return ShareUtil.wrapPageUrl(paramString, i, localProgramLocation, InfoManager.getInstance().getContext(), 1);
  }

  private String getParentCover(Node paramNode)
  {
    if (paramNode.nodeName.equalsIgnoreCase("program"))
    {
      if (((ProgramNode)paramNode).channelType == 1)
      {
        Node localNode = paramNode.parent;
        if ((localNode != null) && (localNode.nodeName.equalsIgnoreCase("channel")))
          return ((ChannelNode)localNode).getApproximativeThumb();
      }
    }
    else if ((paramNode.nodeName.equalsIgnoreCase("channel")) && (((ChannelNode)paramNode).channelType == 1))
      return ((ChannelNode)paramNode).getApproximativeThumb();
    return null;
  }

  private String getShareTitle(String paramString1, String paramString2)
  {
    String str = "";
    if ((paramString1 != null) && (!paramString1.equalsIgnoreCase("")))
      str = str + "【" + paramString1 + "】";
    if (paramString2 != null)
      str = str + paramString2;
    return str;
  }

  private String getSlogan()
  {
    InfoManager.getInstance().root().getCurrentPlayingChannelNode();
    InfoManager.getInstance().root().getCurrentPlayingNode();
    return "(发自@蜻蜓fm )";
  }

  private String getUserID()
  {
    if (this.mUserId == null)
      this.mUserId = WeiboAgent.getInstance().getUserId();
    return this.mUserId;
  }

  private void log(String paramString)
  {
  }

  private void saveUserInfoToDB()
  {
    if (this.mUserInfo == null);
    while (this.mUserInfo.snsInfo.sns_id.equalsIgnoreCase(""))
      return;
    HashMap localHashMap = new HashMap();
    localHashMap.put("site", "weibo");
    DataManager.getInstance().getData("deletedb_user_info", null, localHashMap);
    localHashMap.put("userInfo", this.mUserInfo);
    DataManager.getInstance().getData("insertdb_user_info", null, localHashMap);
    SharedCfg.getInstance().setWeiboGender(this.mUserInfo.snsInfo.sns_gender);
    InfoManager.getInstance().setUserInfo(this.mUserInfo);
  }

  private void sendCheckinLog()
  {
    Node localNode = InfoManager.getInstance().root().getCurrentPlayingNode();
    ChannelNode localChannelNode = InfoManager.getInstance().root().getCurrentPlayingChannelNode();
    ShareBean localShareBean = new ShareBean();
    localShareBean.platform = "weibo";
    if ((localNode != null) && (localChannelNode != null))
      if (localNode.nodeName.equalsIgnoreCase("program"))
      {
        localShareBean.channelType = ((ProgramNode)localNode).channelType;
        localShareBean.programId = ((ProgramNode)localNode).uniqueId;
        localShareBean.categoryId = localChannelNode.categoryId;
        localShareBean.channelId = localChannelNode.channelId;
        if ((this.mUserInfo != null) && (this.mUserInfo.snsInfo != null))
          localShareBean.snsId = this.mUserInfo.snsInfo.sns_id;
        localShareBean.time = (System.currentTimeMillis() / 1000L);
        String str2 = QTLogger.getInstance().buildPublishLog(localShareBean);
        if (str2 != null)
          LogModule.getInstance().send("CheckIn", str2);
      }
    String str1;
    do
    {
      do
        return;
      while (localChannelNode == null);
      localShareBean.channelType = localChannelNode.channelType;
      localShareBean.categoryId = localChannelNode.categoryId;
      localShareBean.channelId = localChannelNode.channelId;
      localShareBean.snsId = this.mUserId;
      localShareBean.time = (System.currentTimeMillis() / 1000L);
      str1 = QTLogger.getInstance().buildPublishLog(localShareBean);
    }
    while (str1 == null);
    LogModule.getInstance().send("CheckIn", str1);
  }

  private void sendLiveRoomLog(int paramInt)
  {
  }

  private void updateUserInfo(String paramString)
  {
    if (paramString == null);
    while ((paramString == null) || (this.mapUserInfo.containsKey(paramString)) || (!WeiboAgent.getInstance().isSessionValid().booleanValue()))
      return;
    new HashMap().put("uid", paramString);
  }

  private static String wrapPageUrl(String paramString, ProgramLocation paramProgramLocation)
  {
    return composeTrackUrl(paramString, 1, 3, paramProgramLocation);
  }

  public void checkIn(int paramInt, String paramString)
  {
    if (!WeiboAgent.getInstance().isSessionValid().booleanValue());
    ChannelNode localChannelNode;
    do
    {
      return;
      localChannelNode = InfoManager.getInstance().root().getCurrentPlayingChannelNode();
    }
    while (localChannelNode == null);
    String str = localChannelNode.getApproximativeThumb();
    SinaWeiboApi.checkIn(getContext(), getCheckInText(localChannelNode, true), null, str, new SocialEventListener()
    {
      public void onCancel(Object paramAnonymousObject)
      {
        Message localMessage = Message.obtain(WeiboChat.this.mHandler, 3, null);
        WeiboChat.this.mHandler.sendMessage(localMessage);
      }

      public void onComplete(Object paramAnonymousObject1, Object paramAnonymousObject2)
      {
        Message localMessage = Message.obtain(WeiboChat.this.mHandler, 1, null);
        WeiboChat.this.mHandler.sendMessage(localMessage);
      }

      public void onException(Object paramAnonymousObject)
      {
        Message localMessage = Message.obtain(WeiboChat.this.mHandler, 2, null);
        WeiboChat.this.mHandler.sendMessage(localMessage);
      }
    });
    sendLiveRoomLog(1);
  }

  public void comment(String paramString1, String paramString2)
  {
    if ((paramString2 == null) || (paramString2.equalsIgnoreCase("")));
    while ((paramString1 == null) || (paramString1.equalsIgnoreCase("")) || (!WeiboAgent.getInstance().isSessionValid().booleanValue()))
      return;
    String str1 = paramString2.replaceAll("@", " ");
    String str2 = getSlogan();
    if ((str2 != null) && (!str2.equalsIgnoreCase("")))
      str1 = str1 + str2;
    Boolean localBoolean = Boolean.valueOf(true);
    SinaWeiboApi.comment(getContext(), paramString1, str1, localBoolean, new SocialEventListener()
    {
      public void onComplete(Object paramAnonymousObject1, Object paramAnonymousObject2)
      {
      }
    });
  }

  public void exit()
  {
  }

  public void flower(UserInfo paramUserInfo)
  {
    if (paramUserInfo == null);
    String str1;
    String str2;
    do
    {
      do
      {
        return;
        str1 = paramUserInfo.snsInfo.sns_name;
      }
      while ((str1 == null) || (str1.equalsIgnoreCase("")) || (!allowFlower(paramUserInfo.snsInfo.sns_id)) || (!WeiboAgent.getInstance().isSessionValid().booleanValue()));
      str2 = getCheckInText(InfoManager.getInstance().root().getCurrentPlayingNode(), false);
    }
    while ((str2 == null) || (str2.equalsIgnoreCase("")));
    String str3 = "节目好精彩, 我向@" + str1 + " 献了一朵花[鲜花]! " + str2;
    SinaWeiboApi.shareText(getContext(), str3, "", "", new SocialEventListener()
    {
      public void onComplete(Object paramAnonymousObject1, Object paramAnonymousObject2)
      {
        Message localMessage = Message.obtain(WeiboChat.this.mHandler, 3, null);
        WeiboChat.this.mHandler.sendMessage(localMessage);
      }

      public void onException(Object paramAnonymousObject)
      {
        Message localMessage = Message.obtain(WeiboChat.this.mHandler, 4, null);
        WeiboChat.this.mHandler.sendMessage(localMessage);
      }
    });
    sendLiveRoomLog(2);
  }

  public UserInfo getUserInfo()
  {
    if (!WeiboAgent.getInstance().isSessionValid().booleanValue())
      return null;
    if ((this.mUserInfo != null) && (this.mUserInfo.snsInfo.sns_id != null) && (!this.mUserInfo.snsInfo.sns_id.equalsIgnoreCase("")))
      return this.mUserInfo;
    if (this.mUserInfo == null)
      this.mUserInfo = WeiboAgent.getInstance().getUserInfo();
    return this.mUserInfo;
  }

  public void init()
  {
    if (this.hasRestoredFromDB);
    while (!WeiboAgent.getInstance().isSessionValid().booleanValue())
      return;
    this.mUserInfo = WeiboAgent.getInstance().getUserInfo();
    this.hasRestoredFromDB = true;
  }

  public boolean login()
  {
    if ((this.loginSuccess) && (WeiboAgent.getInstance().isSessionValid().booleanValue()))
      return true;
    if ((getInstance().getUserInfo() != null) && (WeiboAgent.getInstance().isSessionValid().booleanValue()))
    {
      QTChat.getInstance().login(getInstance().getUserInfo());
      updatePlayingProgramUserInfo();
      this.loginSuccess = true;
      return true;
    }
    return false;
  }

  public void logout()
  {
    WeiboAgent.getInstance().logout();
    this.loginSuccess = false;
    this.mUserId = null;
    this.mUserInfo = null;
    deleteUserInfo();
    InfoManager.getInstance().setUserInfo(null);
  }

  public void onActivityResult(int paramInt1, int paramInt2, Intent paramIntent)
  {
    SinaWeiboAuth.onActivityResult(paramInt1, paramInt2, paramIntent);
  }

  public void onLoginSuccess()
  {
    getUserInfo();
    this.loginSuccess = true;
    ControllerManager.getInstance().dipatchEventToCurrentController("weibo_login_success");
    saveUserInfoToDB();
  }

  public void onRecvResult(Result paramResult, Object paramObject1, IResultToken paramIResultToken, Object paramObject2)
  {
  }

  public void reply(String paramString1, String paramString2, String paramString3)
  {
    if ((paramString1 != null) && (!paramString1.equalsIgnoreCase("")) && (paramString2 != null) && (!paramString2.equalsIgnoreCase("")) && (paramString3 != null) && (paramString3.equalsIgnoreCase("")));
  }

  public void send(String paramString)
  {
  }

  public void send(String paramString1, UserInfo paramUserInfo, String paramString2)
  {
  }

  public void setWeiboId(String paramString)
  {
    this.mWeiboId = paramString;
  }

  public void share(Node paramNode, String paramString)
  {
  }

  public void speakTo(UserInfo paramUserInfo, String paramString)
  {
    if ((paramUserInfo == null) || (paramString == null) || (paramString.equalsIgnoreCase("")));
    String str;
    do
    {
      do
        return;
      while (!WeiboAgent.getInstance().isSessionValid().booleanValue());
      str = paramUserInfo.snsInfo.sns_name;
    }
    while ((str == null) || (!str.equalsIgnoreCase("")));
  }

  public void updateBroadcasterWeiboName(Node paramNode)
  {
    if (paramNode == null);
  }

  public void updatePlayingProgramUserInfo()
  {
    if (!WeiboAgent.getInstance().isSessionValid().booleanValue());
    while (true)
    {
      return;
      Node localNode = InfoManager.getInstance().root().getCurrentPlayingNode();
      if ((localNode != null) && (localNode.nodeName.equalsIgnoreCase("program")))
      {
        ProgramNode localProgramNode = (ProgramNode)localNode;
        if ((localProgramNode.lstBroadcaster != null) && (localProgramNode.lstBroadcaster.size() != 0))
          for (int i = 0; i < localProgramNode.lstBroadcaster.size(); i++)
            updateUserInfo(((BroadcasterNode)localProgramNode.lstBroadcaster.get(i)).weiboId);
      }
    }
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.qtradio.room.WeiboChat
 * JD-Core Version:    0.6.2
 */