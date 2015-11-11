package fm.qingting.qtradio.room;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;
import com.umeng.analytics.MobclickAgent;
import fm.qingting.qtradio.log.LogModule;
import fm.qingting.qtradio.logger.QTLogger;
import fm.qingting.qtradio.model.ChannelNode;
import fm.qingting.qtradio.model.InfoManager;
import fm.qingting.qtradio.model.Node;
import fm.qingting.qtradio.model.OnDemandProgramNode;
import fm.qingting.qtradio.model.ProgramNode;
import fm.qingting.qtradio.model.RadioChannelNode;
import fm.qingting.qtradio.model.RecommendItemNode;
import fm.qingting.qtradio.model.RootNode;
import fm.qingting.qtradio.model.ShareBean;
import fm.qingting.qtradio.share.ProgramLocation;
import fm.qingting.qtradio.tencentAgent.TencentAgent;
import fm.qingting.social.SocialEventListener;
import fm.qingting.social.api.TencentWeiboApi;
import fm.qingting.utils.DateUtil;
import fm.qingting.utils.DeviceInfo;
import fm.qingting.utils.TimeUtil;
import fm.qingting.utils.ViewCaptureUtil;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

public class TencentChat extends Chat
{
  private static String BODY = "节目不错，点个赞/强>>";
  private static String HEAD;
  private static final String ParaCatid = "catid";
  private static final String ParaChannelid = "channelid";
  private static final String ParaDeviceid = "deviceid";
  private static final String ParaFrom = "from";
  private static final String ParaOs = "os";
  private static final String ParaPagetype = "pagetype";
  private static final String ParaPid = "pid";
  private static final String ParaSubcatid = "subcatid";
  private static final String ParaTimestamp = "timestamp";
  private static String TAIL = " (签到自@蜻蜓fm)";
  public static TencentChat _instance = null;
  private boolean loginSuccess = false;
  private final Handler mHandler = new Handler()
  {
    public void handleMessage(Message paramAnonymousMessage)
    {
      if (paramAnonymousMessage == null)
        return;
      switch (paramAnonymousMessage.what)
      {
      default:
        return;
      case 1:
        Toast.makeText(InfoManager.getInstance().getContext(), "签到成功", 0).show();
        MobclickAgent.onEvent(InfoManager.getInstance().getContext(), "Checkin", "succ");
        TencentChat.this.sendCheckInLog();
        return;
      case 2:
        Toast.makeText(InfoManager.getInstance().getContext(), "签到失败", 0).show();
        MobclickAgent.onEvent(InfoManager.getInstance().getContext(), "Checkin", "failed");
        return;
      case 3:
        Toast.makeText(InfoManager.getInstance().getContext(), "献花成功", 0).show();
        return;
      case 4:
      }
      Toast.makeText(InfoManager.getInstance().getContext(), "献花失败", 0).show();
    }
  };
  private String mRoomId = null;
  private String mSharedChannel;
  private String mSharedContentUrl;
  private String mSharedProgram;
  private String mSharedSentence;
  private Map<String, String> mapUserInfo = new HashMap();

  static
  {
    HEAD = "记录蜻蜓陪伴我走过的每一天，此刻我在听";
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

  private String getCheckInLabel()
  {
    return "\"" + TimeUtil.msToDate9(System.currentTimeMillis()) + "签到\"";
  }

  private String getCheckInText(Node paramNode)
  {
    if (paramNode != null)
    {
      this.mSharedContentUrl = "http://qingting.fm";
      this.mSharedChannel = "";
      this.mSharedProgram = "";
      this.mSharedSentence = "";
      ProgramLocation localProgramLocation = new ProgramLocation();
      if ((paramNode instanceof ChannelNode))
      {
        ChannelNode localChannelNode2 = (ChannelNode)paramNode;
        localProgramLocation.catId = localChannelNode2.categoryId;
        localProgramLocation.channelId = localChannelNode2.channelId;
        if (localChannelNode2.channelType == 0)
          this.mSharedContentUrl = ("http://qingting.fm/channels/" + localChannelNode2.channelId);
        this.mSharedChannel = localChannelNode2.title;
        this.mSharedSentence = (getShareTitle(localChannelNode2.title, null) + " " + wrapPageUrl(this.mSharedContentUrl, localProgramLocation) + TAIL);
      }
      ProgramNode localProgramNode;
      if ((paramNode instanceof ProgramNode))
      {
        localProgramNode = (ProgramNode)paramNode;
        localProgramLocation.catId = localProgramNode.getCategoryId();
        localProgramLocation.pId = localProgramNode.id;
        if (!(localProgramNode.parent instanceof ChannelNode))
          break label419;
        ChannelNode localChannelNode1 = (ChannelNode)localProgramNode.parent;
        localProgramLocation.channelId = localChannelNode1.channelId;
        if (localChannelNode1.channelType != 0)
          break label377;
        this.mSharedContentUrl = ("http://qingting.fm/channels/" + localChannelNode1.channelId + "/programs/" + localProgramNode.id + "/date/" + TimeUtil.msToDate6(1000L * localProgramNode.getAbsoluteStartTime()));
        this.mSharedChannel = localChannelNode1.title;
      }
      while (true)
      {
        this.mSharedProgram = localProgramNode.title;
        this.mSharedSentence = (getCheckInLabel() + HEAD + getShareTitle(this.mSharedChannel, this.mSharedProgram) + " " + BODY + wrapPageUrl(this.mSharedContentUrl, localProgramLocation) + TAIL);
        return this.mSharedSentence;
        label377: this.mSharedContentUrl = ("http://qingting.fm/vchannels/" + localProgramNode.channelId + "/programs/" + localProgramNode.id);
        break;
        label419: if ((localProgramNode.parent instanceof RecommendItemNode))
        {
          this.mSharedContentUrl = ("http://qingting.fm/vchannels/" + localProgramNode.channelId + "/programs/" + localProgramNode.id);
        }
        else if ((localProgramNode.parent instanceof RadioChannelNode))
        {
          RadioChannelNode localRadioChannelNode = (RadioChannelNode)localProgramNode.parent;
          localProgramLocation.channelId = localRadioChannelNode.channelId;
          this.mSharedContentUrl = ("http://qingting.fm/channels/" + localRadioChannelNode.channelId + " ");
          this.mSharedChannel = localRadioChannelNode.channelName;
        }
        else if (localProgramNode.channelType == 1)
        {
          localProgramLocation.channelId = localProgramNode.channelId;
          this.mSharedContentUrl = ("http://qingting.fm/vchannels/" + localProgramNode.channelId + "/programs/" + localProgramNode.id);
        }
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

  public static TencentChat getInstance()
  {
    if (_instance == null)
      _instance = new TencentChat();
    return _instance;
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

  private String getSiteString(Node paramNode, String paramString)
  {
    if (paramNode != null)
    {
      if (paramNode.nodeName.equalsIgnoreCase("program"))
      {
        if (paramString != null)
        {
          Object[] arrayOfObject2 = new Object[2];
          arrayOfObject2[0] = paramString;
          arrayOfObject2[1] = InfoManager.getInstance().getContext().getString(2131492874);
          return String.format("http://qingting.fm/%s?os=android&from=weibo&version=%s&action=flower", arrayOfObject2);
        }
        Object[] arrayOfObject1 = new Object[2];
        arrayOfObject1[0] = paramString;
        arrayOfObject1[1] = InfoManager.getInstance().getContext().getString(2131492874);
        return String.format("http://qingting.fm/%s?os=android&from=weibo&version=%s&action=flower", arrayOfObject1);
      }
      if (paramNode.nodeName.equalsIgnoreCase("ondemandprogram"))
      {
        String str1 = ((OnDemandProgramNode)paramNode).programId;
        String str2 = ((OnDemandProgramNode)paramNode).albumId;
        if ((str1 == null) || (str2 == null))
          break label154;
        return String.format("http://qingting.fm/aod/%s/episodes/%s", new Object[] { str2, str1 });
      }
    }
    return null;
    label154: return null;
  }

  private void sendCheckInLog()
  {
    Node localNode = InfoManager.getInstance().root().getCurrentPlayingNode();
    ChannelNode localChannelNode = InfoManager.getInstance().root().getCurrentPlayingChannelNode();
    ShareBean localShareBean = new ShareBean();
    localShareBean.platform = "tencent";
    if ((localNode != null) && (localChannelNode != null))
      if (localNode.nodeName.equalsIgnoreCase("program"))
      {
        localShareBean.channelType = ((ProgramNode)localNode).channelType;
        localShareBean.programId = ((ProgramNode)localNode).id;
        localShareBean.categoryId = localChannelNode.categoryId;
        localShareBean.channelId = localChannelNode.channelId;
        localShareBean.snsId = TencentAgent.getInstance().getUserSnsId();
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
      localShareBean.snsId = TencentAgent.getInstance().getUserSnsId();
      localShareBean.time = (System.currentTimeMillis() / 1000L);
      str1 = QTLogger.getInstance().buildPublishLog(localShareBean);
    }
    while (str1 == null);
    LogModule.getInstance().send("CheckIn", str1);
  }

  private void sendLiveRoomLog(int paramInt)
  {
  }

  private static String wrapPageUrl(String paramString, ProgramLocation paramProgramLocation)
  {
    return composeTrackUrl(paramString, 1, 4, paramProgramLocation);
  }

  public void checkIn(int paramInt, String paramString)
  {
    if (!TencentWeiboApi.isSessionValid(getContext()).booleanValue());
    ChannelNode localChannelNode;
    String str1;
    do
    {
      do
      {
        return;
        localChannelNode = InfoManager.getInstance().root().getCurrentPlayingChannelNode();
      }
      while (localChannelNode == null);
      str1 = getCheckInText(localChannelNode);
    }
    while ((str1 == null) || (str1.equalsIgnoreCase("")));
    String str2 = localChannelNode.getApproximativeThumb();
    if ((str2 == null) || (str2.equalsIgnoreCase("")))
    {
      String str3 = getCheckinImagePath();
      if ((str3 != null) && (!str3.equalsIgnoreCase("")));
      str2 = null;
    }
    SocialEventListener local2 = new SocialEventListener()
    {
      public void onComplete(Object paramAnonymousObject1, Object paramAnonymousObject2)
      {
        Message localMessage = Message.obtain(TencentChat.this.mHandler, 1, null);
        TencentChat.this.mHandler.sendMessage(localMessage);
      }

      public void onException(Object paramAnonymousObject)
      {
        Message localMessage = Message.obtain(TencentChat.this.mHandler, 2, null);
        TencentChat.this.mHandler.sendMessage(localMessage);
      }
    };
    TencentWeiboApi.shareImage(getContext(), str1, str2, local2);
    sendLiveRoomLog(1);
  }

  public void comment(String paramString1, String paramString2)
  {
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
      while ((str1 == null) || (str1.equalsIgnoreCase("")) || (!TencentAgent.getInstance().isSessionValid().booleanValue()));
      str2 = getCheckInText(InfoManager.getInstance().root().getCurrentPlayingNode());
    }
    while ((str2 == null) || (str2.equalsIgnoreCase("")));
    String str3 = "节目好精彩, 我向" + str1 + " 献了一朵花[鲜花]! " + str2;
    TencentWeiboApi.shareText(getContext(), str3, new SocialEventListener()
    {
      public void onComplete(Object paramAnonymousObject1, Object paramAnonymousObject2)
      {
        Message localMessage = Message.obtain(TencentChat.this.mHandler, 3, null);
        TencentChat.this.mHandler.sendMessage(localMessage);
      }

      public void onException(Object paramAnonymousObject)
      {
        Message localMessage = Message.obtain(TencentChat.this.mHandler, 4, null);
        TencentChat.this.mHandler.sendMessage(localMessage);
      }
    });
    sendLiveRoomLog(2);
  }

  public UserInfo getUserInfo()
  {
    return TencentAgent.getInstance().getUserInfo();
  }

  public void join(String paramString1, String paramString2)
  {
    if ((paramString2 == null) || (paramString1 == null));
    while (TencentAgent.getInstance().isSessionValid().booleanValue())
      return;
  }

  public boolean login()
  {
    if ((this.loginSuccess) && (TencentAgent.getInstance().isSessionValid().booleanValue()))
      return true;
    if ((getUserInfo() != null) && (TencentAgent.getInstance().isSessionValid().booleanValue()))
    {
      QTChat.getInstance().login(getInstance().getUserInfo());
      this.loginSuccess = true;
      return true;
    }
    return false;
  }

  public void logout()
  {
    TencentAgent.getInstance().logout();
    this.loginSuccess = false;
    InfoManager.getInstance().setUserInfo(null);
  }

  public void reply(String paramString1, String paramString2, String paramString3)
  {
  }

  public void send(String paramString)
  {
    if ((paramString == null) || (paramString.equalsIgnoreCase("")))
      return;
    TencentAgent.getInstance().addTencentWeiboWithoutPic(paramString);
    sendLiveRoomLog(3);
  }

  public void send(String paramString1, UserInfo paramUserInfo, String paramString2)
  {
    if ((paramString1 == null) || (paramString1.equalsIgnoreCase("")) || (paramString2 == null) || (paramString2.equalsIgnoreCase("")) || (paramUserInfo == null))
      return;
    String str1 = paramString1 + " //";
    String str2 = str1 + paramUserInfo.snsInfo.sns_name;
    String str3 = str2 + ": ";
    String str4 = str3 + paramString2;
    TencentAgent.getInstance().addTencentWeiboWithoutPic(str4);
    sendLiveRoomLog(3);
  }

  public void share(Node paramNode, String paramString)
  {
  }

  public void speakTo(UserInfo paramUserInfo, String paramString)
  {
    if ((paramUserInfo == null) || (paramString == null));
    String str1;
    do
    {
      do
        return;
      while (!TencentAgent.getInstance().isSessionValid().booleanValue());
      str1 = paramUserInfo.snsInfo.sns_id;
    }
    while (!this.mapUserInfo.containsKey(str1));
    String str2 = (String)this.mapUserInfo.get(str1);
    Object[] arrayOfObject = new Object[3];
    arrayOfObject[0] = str2;
    arrayOfObject[1] = paramString;
    arrayOfObject[2] = InfoManager.getInstance().getContext().getString(2131492874);
    String str3 = String.format("@%s , %s (发自@蜻蜓FM http://qingting.fm/?os=android&from=weibo&version=%s&action=speakTo )", arrayOfObject);
    TencentAgent.getInstance().addTencentWeiboWithoutPic(str3);
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.qtradio.room.TencentChat
 * JD-Core Version:    0.6.2
 */