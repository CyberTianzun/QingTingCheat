package fm.qingting.qtradio.share;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.tencent.weibo.sdk.android.model.ModelResult;
import com.umeng.analytics.MobclickAgent;
import fm.qingting.framework.controller.ViewController;
import fm.qingting.framework.utils.ImageLoader;
import fm.qingting.qtradio.controller.ControllerManager;
import fm.qingting.qtradio.helper.ChannelHelper;
import fm.qingting.qtradio.log.LogModule;
import fm.qingting.qtradio.logger.QTLogger;
import fm.qingting.qtradio.model.ActivityNode;
import fm.qingting.qtradio.model.BroadcasterNode;
import fm.qingting.qtradio.model.ChannelNode;
import fm.qingting.qtradio.model.InfoManager;
import fm.qingting.qtradio.model.Node;
import fm.qingting.qtradio.model.ProgramNode;
import fm.qingting.qtradio.model.RecommendItemNode;
import fm.qingting.qtradio.model.RootNode;
import fm.qingting.qtradio.model.ShareBean;
import fm.qingting.qtradio.model.SpecialTopicNode;
import fm.qingting.qtradio.room.SnsInfo;
import fm.qingting.qtradio.room.UserInfo;
import fm.qingting.qtradio.social.UserProfile;
import fm.qingting.qtradio.tencentAgent.TencentAgent;
import fm.qingting.qtradio.weiboAgent.WeiboAgent;
import fm.qingting.social.ISocialEventListener;
import fm.qingting.social.SocialEventListener;
import fm.qingting.social.api.QQApi;
import fm.qingting.social.api.QZoneApi;
import fm.qingting.social.api.SinaWeiboApi;
import fm.qingting.social.api.TencentWeiboApi;
import fm.qingting.social.api.WechatApi;
import fm.qingting.utils.DateUtil;
import fm.qingting.utils.DeviceInfo;
import fm.qingting.utils.QTMSGManage;
import fm.qingting.utils.TimeUtil;
import fm.qingting.utils.ViewCaptureUtil;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.List;

public class ShareUtil
{
  private static final String Default_Desc = "有声世界,无限精彩";
  public static final int PAGETYPE_AUDIO = 2;
  public static final int PAGETYPE_WEBPAGE = 1;
  public static final int PLATFORM_MOMENT = 1;
  public static final int PLATFORM_QQ = 3;
  public static final int PLATFORM_QZONE = 2;
  public static final int PLATFORM_SINA = 4;
  public static final int PLATFORM_TENCENT = 5;
  public static final int PLATFORM_WEIXIN = 0;
  private static final String ParaCatid = "catid";
  private static final String ParaChannelid = "channelid";
  private static final String ParaDeviceid = "deviceid";
  private static final String ParaFrom = "from";
  private static final String ParaOs = "os";
  private static final String ParaPagetype = "pagetype";
  private static final String ParaPid = "pid";
  private static final String ParaTargetType = "targettype";
  private static final String ParaTid = "tid";
  private static final String ParaTimestamp = "timestamp";
  private static final String ShareChannelTemplate = "我觉得%s不错哟";
  private static final String ShareContentCustom = "";
  private static final String ShareProgramTemplate = "我正在收听%s";
  private static final String ShareTopicTemplate = "我觉得%s不错哟";
  private static String TAIL_TENCENT = " (分享自@蜻蜓fm)";
  private static String TAIL_WEIBO;
  public static final int TARGETTYPE_ACTIVITY = 4;
  public static final int TARGETTYPE_CHANNEL = 2;
  public static final int TARGETTYPE_PROGRAM = 1;
  public static final int TARGETTYPE_TOPIC = 3;
  private static final Handler mHandler = new Handler()
  {
    public void handleMessage(Message paramAnonymousMessage)
    {
      if (paramAnonymousMessage == null);
      Context localContext;
      do
      {
        return;
        localContext = InfoManager.getInstance().getContext();
      }
      while (localContext == null);
      switch (paramAnonymousMessage.what)
      {
      default:
        return;
      case 1:
        Toast.makeText(localContext, "分享成功", 0).show();
        MobclickAgent.onEvent(localContext, "ShareResult", "succ_" + ShareUtil.mSharePlatformName);
        ShareUtil.sendShareText(ShareUtil.mSharePlatformName, ShareUtil.mShareNode);
        return;
      case 2:
        Toast.makeText(localContext, "分享失败", 0).show();
        MobclickAgent.onEvent(localContext, "ShareResult", "failed_" + ShareUtil.mSharePlatformName);
        ShareUtil.sendShareText(ShareUtil.mSharePlatformName, ShareUtil.mShareNode);
        return;
      case 3:
        Toast.makeText(localContext, "分享取消", 0).show();
        MobclickAgent.onEvent(localContext, "ShareResult", "cancel_" + ShareUtil.mSharePlatformName);
        return;
      case 4:
        Toast.makeText(localContext, "邀请成功", 0).show();
        MobclickAgent.onEvent(localContext, "ShareResult", "succ_" + ShareUtil.mSharePlatformName);
        return;
      case 5:
        Toast.makeText(localContext, "邀请失败", 0).show();
        MobclickAgent.onEvent(localContext, "ShareResult", "failed_" + ShareUtil.mSharePlatformName);
        return;
      case 6:
        Toast.makeText(localContext, "取消邀请", 0).show();
        MobclickAgent.onEvent(localContext, "ShareResult", "cancel_" + ShareUtil.mSharePlatformName);
        return;
      case 7:
      }
      Toast.makeText(localContext, "请先安装或更新微信", 0).show();
    }
  };
  private static Node mShareNode;
  private static String mSharePlatformName;

  static
  {
    TAIL_WEIBO = " (分享自@蜻蜓FM)";
  }

  private static String composeTrackUrl(String paramString, int paramInt1, int paramInt2, int paramInt3, ProgramLocation paramProgramLocation, Context paramContext)
  {
    if (paramString.startsWith("http"))
      paramString = paramString.substring(paramString.indexOf("/", 8));
    StringBuilder localStringBuilder = new StringBuilder().append("http://share.qingting.fm").append(paramString);
    if (paramString.indexOf("?") == -1);
    for (String str1 = "?"; ; str1 = "&")
    {
      String str2 = str1 + "pagetype" + "=" + paramInt1 + "&" + "targettype" + "=" + paramInt2 + "&" + "timestamp" + "=" + DateUtil.getCurrentSeconds() + "&" + "from" + "=" + paramInt3 + "&" + "os" + "=1" + "&" + "deviceid" + "=" + DeviceInfo.getUniqueId(paramContext) + "&av=6";
      if (paramProgramLocation != null)
      {
        if (paramProgramLocation.catId != 0)
          str2 = str2 + "&catid=" + paramProgramLocation.catId;
        if (paramProgramLocation.channelId != 0)
          str2 = str2 + "&channelid=" + paramProgramLocation.channelId;
        if (paramProgramLocation.pId != 0)
          str2 = str2 + "&pid=" + paramProgramLocation.pId;
        if (paramProgramLocation.tId != 0)
          str2 = str2 + "&tid=" + paramProgramLocation.tId;
      }
      return str2;
    }
  }

  private static String getBroadcastors(ChannelNode paramChannelNode, ProgramNode paramProgramNode)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    if ((paramChannelNode != null) && (paramChannelNode.lstPodcasters != null))
    {
      Iterator localIterator2 = paramChannelNode.lstPodcasters.iterator();
      while (localIterator2.hasNext())
      {
        UserInfo localUserInfo = (UserInfo)localIterator2.next();
        if ((localUserInfo.snsInfo != null) && (localUserInfo.snsInfo.sns_account.trim().length() > 0) && (localUserInfo.snsInfo.sns_name.trim().length() > 0))
          localStringBuffer.append(" ,@" + localUserInfo.snsInfo.sns_name.trim());
      }
    }
    if ((paramProgramNode != null) && (paramProgramNode.lstBroadcaster != null))
    {
      Iterator localIterator1 = paramProgramNode.lstBroadcaster.iterator();
      while (localIterator1.hasNext())
      {
        BroadcasterNode localBroadcasterNode = (BroadcasterNode)localIterator1.next();
        if ((localBroadcasterNode.weiboName != null) && (localBroadcasterNode.weiboName.trim().length() > 0) && (!localBroadcasterNode.weiboName.equalsIgnoreCase("未知")))
          localStringBuffer.append(" ,@" + localBroadcasterNode.weiboName.trim());
      }
    }
    if (localStringBuffer.length() > 0)
      return " 主播：" + localStringBuffer.substring(2);
    return "";
  }

  public static int getPlatFormNum(int paramInt)
  {
    switch (paramInt)
    {
    default:
      return 0;
    case 0:
      return 1;
    case 1:
      return 2;
    case 4:
      return 3;
    case 5:
      return 4;
    case 3:
      return 5;
    case 2:
    }
    return 6;
  }

  private static Bitmap getShareBitmap(Context paramContext, String paramString)
  {
    Bitmap localBitmap = null;
    if (paramString != null)
    {
      boolean bool = paramString.equalsIgnoreCase("");
      localBitmap = null;
      if (!bool)
        localBitmap = ImageLoader.getInstance(paramContext).getImage(paramString, 200, 200);
    }
    if (localBitmap != null)
      return localBitmap;
    return BitmapFactory.decodeResource(paramContext.getResources(), 2130837798);
  }

  private static ShareInfoBean getShareInfoBean(Node paramNode, int paramInt, Context paramContext)
  {
    if (paramNode == null)
      return null;
    int i = getPlatFormNum(paramInt);
    ShareInfoBean localShareInfoBean = new ShareInfoBean();
    localShareInfoBean.path = new ProgramLocation();
    boolean bool = paramNode instanceof ChannelNode;
    int j = 0;
    ChannelNode localChannelNode3;
    if (bool)
    {
      localChannelNode3 = (ChannelNode)paramNode;
      localShareInfoBean.path.catId = localChannelNode3.categoryId;
      localShareInfoBean.path.channelId = localChannelNode3.channelId;
      localShareInfoBean.parentCover = localChannelNode3.getApproximativeThumb();
      Object[] arrayOfObject3 = new Object[1];
      arrayOfObject3[0] = getShareTitle(localChannelNode3.title, null);
      localShareInfoBean.content = String.format("我觉得%s不错哟", arrayOfObject3);
      localShareInfoBean.content += getBroadcastors(localChannelNode3, null);
      if (localChannelNode3.channelType != 0)
        break label315;
      localShareInfoBean.pageUrl = ("/channels/" + localChannelNode3.channelId);
      localShareInfoBean.playUrl = ("/live/" + localChannelNode3.resId + ".m3u8");
      localShareInfoBean.description = localChannelNode3.title;
      localShareInfoBean.title = localChannelNode3.title;
      j = 2;
    }
    ProgramNode localProgramNode;
    ChannelNode localChannelNode1;
    label315: int m;
    while ((paramNode instanceof ProgramNode))
    {
      localProgramNode = (ProgramNode)paramNode;
      localShareInfoBean.path.catId = localProgramNode.getCategoryId();
      localShareInfoBean.path.channelId = localProgramNode.channelId;
      localShareInfoBean.path.pId = localProgramNode.id;
      localChannelNode1 = ChannelHelper.getInstance().getChannel(localProgramNode);
      if (localChannelNode1 == null)
      {
        return null;
        localShareInfoBean.pageUrl = ("/vchannels/" + localChannelNode3.channelId);
        localShareInfoBean.title = localChannelNode3.title;
        localShareInfoBean.description = localChannelNode3.desc;
        j = 2;
      }
      else
      {
        Object[] arrayOfObject2 = new Object[1];
        arrayOfObject2[0] = getShareTitle(localChannelNode1.title, localProgramNode.title);
        localShareInfoBean.content = String.format("我正在收听%s", arrayOfObject2);
        localShareInfoBean.content += getBroadcastors(localChannelNode1, localProgramNode);
        log(localShareInfoBean.content);
        localShareInfoBean.parentCover = localChannelNode1.getApproximativeThumb();
        if ((localShareInfoBean.parentCover == null) && (localProgramNode.isDownloadProgram()))
        {
          ChannelNode localChannelNode2 = ChannelHelper.getInstance().getChannel(localProgramNode.channelId, 1);
          if (localChannelNode2 != null)
            localShareInfoBean.parentCover = localChannelNode2.getApproximativeThumb();
        }
        localShareInfoBean.title = localProgramNode.title;
        localShareInfoBean.description = localChannelNode1.title;
        if (localChannelNode1.channelType != 0)
          break label1008;
        localShareInfoBean.pageUrl = ("/channels/" + localChannelNode1.channelId + "/from/" + TimeUtil.msToDate6(1000L * localProgramNode.getAbsoluteStartTime()) + "/to/" + TimeUtil.msToDate6(1000L * localProgramNode.getAbsoluteEndTime()));
        if ((localChannelNode1.channelType != 0) && (!localProgramNode.mLiveInVirtual))
          break label1064;
        StringBuilder localStringBuilder = new StringBuilder().append("/cache/");
        if (localProgramNode.resId <= 0)
          break label1054;
        m = localProgramNode.resId;
        label646: localShareInfoBean.playUrl = (m + ".m3u8" + "?start=" + TimeUtil.msToDate10(1000L * localProgramNode.getAbsoluteStartTime()) + "&end=" + TimeUtil.msToDate10(1000L * localProgramNode.getAbsoluteEndTime()));
        j = 1;
      }
    }
    label712: int k;
    if ((paramNode instanceof SpecialTopicNode))
    {
      k = 3;
      SpecialTopicNode localSpecialTopicNode = (SpecialTopicNode)paramNode;
      localShareInfoBean.pageUrl = ("/topics/" + localSpecialTopicNode.getApiId());
      localShareInfoBean.title = localSpecialTopicNode.title;
      localShareInfoBean.parentCover = localSpecialTopicNode.thumb;
      Object[] arrayOfObject1 = new Object[1];
      arrayOfObject1[0] = getShareTitle(localSpecialTopicNode.title, null);
      localShareInfoBean.content = String.format("我觉得%s不错哟", arrayOfObject1);
      localShareInfoBean.description = localSpecialTopicNode.desc;
    }
    while (true)
    {
      localShareInfoBean.pageUrl = composeTrackUrl(localShareInfoBean.pageUrl, 1, k, i, localShareInfoBean.path, paramContext);
      if (localShareInfoBean.playUrl != null)
        localShareInfoBean.playUrl = composeTrackUrl(localShareInfoBean.playUrl, 2, k, i, localShareInfoBean.path, paramContext);
      localShareInfoBean.content = (localShareInfoBean.content + " " + localShareInfoBean.pageUrl + getTail(paramInt));
      if ((paramInt == 4) && (localShareInfoBean.content != null))
      {
        localShareInfoBean.content += " ";
        localShareInfoBean.content += InfoManager.getInstance().getShareTag();
      }
      if (localShareInfoBean.description == null)
        localShareInfoBean.description = "有声世界,无限精彩";
      return localShareInfoBean;
      label1008: localShareInfoBean.pageUrl = ("/vchannels/" + localProgramNode.channelId + "/programs/" + localProgramNode.id);
      break;
      label1054: m = localChannelNode1.resId;
      break label646;
      label1064: if ((localProgramNode.lstAudioPath != null) && (localProgramNode.lstAudioPath.size() > 0))
      {
        localShareInfoBean.playUrl = ("/" + (String)localProgramNode.lstAudioPath.get(-1 + localProgramNode.lstAudioPath.size()));
        j = 1;
        break label712;
      }
      String str = localProgramNode.getSharedSourcePath();
      if ((str != null) && (!str.equalsIgnoreCase("")))
      {
        localShareInfoBean.playUrl = str;
        j = 1;
        break label712;
      }
      return null;
      if ((paramNode instanceof ActivityNode))
      {
        localShareInfoBean.pageUrl = ((ActivityNode)paramNode).contentUrl;
        localShareInfoBean.title = ((ActivityNode)paramNode).name;
        localShareInfoBean.description = ((ActivityNode)paramNode).infoTitle;
        if ((localShareInfoBean.description == null) || (localShareInfoBean.description.equalsIgnoreCase("")))
          localShareInfoBean.description = localShareInfoBean.title;
        localShareInfoBean.content = (getShareTitle(((ActivityNode)paramNode).name, ((ActivityNode)paramNode).infoTitle) + " " + localShareInfoBean.pageUrl);
        localShareInfoBean.playUrl = null;
        localShareInfoBean.parentCover = ((ActivityNode)paramNode).infoUrl;
        return localShareInfoBean;
      }
      k = j;
    }
  }

  private static String getShareSlogan(Node paramNode)
  {
    if (paramNode == null)
      return "";
    if ((paramNode.parent != null) && (paramNode.parent.nodeName.equalsIgnoreCase("recommenditem")) && (((RecommendItemNode)paramNode.parent).isRecommendShare()) && (((RecommendItemNode)paramNode.parent).desc != null))
      return ((RecommendItemNode)paramNode.parent).desc;
    return "";
  }

  private static String getShareTitle(String paramString1, String paramString2)
  {
    String str = "";
    if ((paramString1 != null) && (!paramString1.equalsIgnoreCase("")))
      str = str + "【" + normalizeTitle(paramString1) + "】";
    if (paramString2 != null)
      str = str + normalizeTitle(paramString2);
    return str;
  }

  private static String getTail(int paramInt)
  {
    if (paramInt == 4)
      return TAIL_WEIBO;
    return TAIL_TENCENT;
  }

  public static void inviteByPlatform(Context paramContext, Node paramNode, int paramInt)
  {
    if (paramNode == null)
      return;
    shareToPlatform(paramContext, paramNode, paramInt, Boolean.valueOf(true));
  }

  public static void inviteByPlatformIm(Context paramContext, int paramInt, String paramString)
  {
    Node localNode = InfoManager.getInstance().root().getCurrentPlayingNode();
    if (localNode == null)
      return;
    shareToPlatform(paramContext, localNode, paramInt, Boolean.valueOf(true));
  }

  private static void log(String paramString)
  {
  }

  private static String normalizeTitle(String paramString)
  {
    return paramString.trim();
  }

  private static void sendShareText(String paramString, Node paramNode)
  {
    if ((paramNode == null) || (paramString == null));
    label288: 
    while (true)
    {
      return;
      ShareBean localShareBean = new ShareBean();
      localShareBean.platform = paramString;
      if (InfoManager.getInstance().getUserProfile().getUserInfo() != null);
      for (String str1 = InfoManager.getInstance().getUserProfile().getUserInfo().snsInfo.sns_id; ; str1 = null)
      {
        if (paramNode == null)
          break label288;
        if (paramNode.nodeName.equalsIgnoreCase("program"))
        {
          localShareBean.channelType = ((ProgramNode)paramNode).channelType;
          localShareBean.programId = ((ProgramNode)paramNode).uniqueId;
          ChannelNode localChannelNode;
          if (localShareBean.channelType == 0)
          {
            localChannelNode = InfoManager.getInstance().root().getCurrentPlayingChannelNode();
            if (localChannelNode == null)
              break;
            localShareBean.categoryId = localChannelNode.categoryId;
          }
          for (localShareBean.channelId = localChannelNode.channelId; ; localShareBean.channelId = ((ProgramNode)paramNode).channelId)
          {
            localShareBean.snsId = str1;
            localShareBean.time = (System.currentTimeMillis() / 1000L);
            String str3 = QTLogger.getInstance().buildPublishLog(localShareBean);
            if (str3 == null)
              break;
            LogModule.getInstance().send("Share", str3);
            return;
            localShareBean.categoryId = ((ProgramNode)paramNode).getCategoryId();
          }
        }
        if (!paramNode.nodeName.equalsIgnoreCase("channel"))
          break;
        localShareBean.channelType = ((ChannelNode)paramNode).channelType;
        localShareBean.categoryId = ((ChannelNode)paramNode).categoryId;
        localShareBean.channelId = ((ChannelNode)paramNode).channelId;
        localShareBean.snsId = str1;
        localShareBean.time = (System.currentTimeMillis() / 1000L);
        String str2 = QTLogger.getInstance().buildPublishLog(localShareBean);
        if (str2 == null)
          break;
        LogModule.getInstance().send("Share", str2);
        return;
      }
    }
  }

  public static void shareToPlatform(Context paramContext, Node paramNode, int paramInt)
  {
    shareToPlatform(paramContext, paramNode, paramInt, Boolean.valueOf(false));
  }

  public static void shareToPlatform(Context paramContext, Node paramNode, int paramInt, Boolean paramBoolean)
  {
    if (paramNode == null)
      return;
    ShareInfoBean localShareInfoBean;
    String str1;
    String str2;
    String str3;
    String str4;
    String str5;
    SocialEventListener local3;
    while (true)
    {
      try
      {
        localShareInfoBean = getShareInfoBean(paramNode, paramInt, paramContext);
        str1 = localShareInfoBean.pageUrl;
        str2 = localShareInfoBean.playUrl;
        str3 = localShareInfoBean.title;
        str4 = "http://qingting.fm";
        if ((localShareInfoBean.parentCover != null) && (!localShareInfoBean.parentCover.equalsIgnoreCase("")))
        {
          str5 = localShareInfoBean.parentCover;
          local3 = new SocialEventListener()
          {
            public void onCancel(Object paramAnonymousObject)
            {
              Handler localHandler;
              if ((ShareUtil.mSharePlatformName == "weibo") || (ShareUtil.mSharePlatformName == "tencent"))
              {
                localHandler = ShareUtil.mHandler;
                if (!this.val$isInvite.booleanValue())
                  break label51;
              }
              label51: for (int i = 6; ; i = 3)
              {
                Message localMessage = Message.obtain(localHandler, i, null);
                ShareUtil.mHandler.sendMessage(localMessage);
                return;
              }
            }

            public void onComplete(Object paramAnonymousObject1, Object paramAnonymousObject2)
            {
              Handler localHandler;
              if (ShareUtil.mSharePlatformName == "weibo")
              {
                WeiboAgent.getInstance().onSocialLogin(paramAnonymousObject1);
                localHandler = ShareUtil.mHandler;
                if (!this.val$isInvite.booleanValue())
                  break label69;
              }
              label69: for (int i = 4; ; i = 1)
              {
                Message localMessage = Message.obtain(localHandler, i, null);
                ShareUtil.mHandler.sendMessage(localMessage);
                return;
                if (ShareUtil.mSharePlatformName != "tencent")
                  break;
                TencentAgent.getInstance().onSocialLogin(paramAnonymousObject1);
                break;
              }
            }

            public void onException(Object paramAnonymousObject)
            {
              if (this.val$isInvite.booleanValue());
              for (int i = 5; ; i = 2)
              {
                Message localMessage = Message.obtain(ShareUtil.mHandler, i, null);
                ShareUtil.mHandler.sendMessage(localMessage);
                return;
              }
            }
          };
          mShareNode = paramNode;
          if (!mShareNode.nodeName.equalsIgnoreCase("specialtopic"))
            break label690;
          QTMSGManage.getInstance().sendStatistcsMessage("sharespecialtopic", String.valueOf(paramInt));
          break label690;
          mSharePlatformName = "wechat";
          if (str2 != null)
            break;
          WechatApi.shareWebPage(paramContext, str1, str3, localShareInfoBean.description, getShareBitmap(paramContext, str5), Boolean.valueOf(false), local3);
          return;
        }
      }
      catch (Exception localException)
      {
        log(localException.toString());
        return;
      }
      str5 = "http://s1.qingting.fm/images/qt_logo.jpg";
    }
    WechatApi.shareAudio(paramContext, str1, str2, str3, localShareInfoBean.description, getShareBitmap(paramContext, str5), Boolean.valueOf(false), local3);
    return;
    mSharePlatformName = "wechatfriend";
    if (str2 == null)
    {
      WechatApi.shareWebPage(paramContext, str1, str3, localShareInfoBean.description, getShareBitmap(paramContext, str5), Boolean.valueOf(true), local3);
      return;
    }
    WechatApi.shareAudio(paramContext, str1, str2, str3, localShareInfoBean.description, getShareBitmap(paramContext, str5), Boolean.valueOf(true), local3);
    return;
    mSharePlatformName = "qzone";
    String str15 = localShareInfoBean.description;
    String str16;
    label319: String str13;
    label407: String str6;
    String str7;
    int i;
    String str8;
    String str9;
    String str10;
    if (str1 == null)
    {
      str16 = str4;
      QZoneApi.share(paramContext, str3, str15, str16, str5, "蜻蜓FM", local3);
      return;
      mSharePlatformName = "qqfriend";
      String str14 = localShareInfoBean.description;
      if (str1 != null)
        break label736;
      QQApi.share(paramContext, str3, str14, str5, str4, str2, local3);
      return;
      str13 = localShareInfoBean.parentCover;
      if (str13 == null)
      {
        ViewController localViewController = ControllerManager.getInstance().getLastViewController();
        if (localViewController.controllerName.equalsIgnoreCase("mainplayview"))
        {
          ViewCaptureUtil.setScreenView(localViewController.getView());
          ViewCaptureUtil.captureViewPath();
          str13 = ViewCaptureUtil.getViewPath();
        }
      }
      mSharePlatformName = "weibo";
      if ((str13 == null) || (str13 == ""))
        break label743;
      if (str13.startsWith("http"))
      {
        SinaWeiboApi.shareImage(paramContext, localShareInfoBean.content, str13, "", "", local3);
        return;
      }
      SinaWeiboApi.shareLocalImage(paramContext, localShareInfoBean.content, str13, local3);
      return;
      mSharePlatformName = "tencent";
      str6 = localShareInfoBean.playUrl;
      str7 = localShareInfoBean.content;
      if ((str6 != null) && (str6.length() > 80) && (str7 != null))
        if (str7 == null)
        {
          i = -1;
          str8 = str7.substring(0, i);
          str9 = str7.substring(i);
          if (str9.indexOf(" ") <= 0)
            break label751;
          str10 = str9.substring(0, str9.indexOf(" "));
          str9 = str9.substring(str9.indexOf(" "));
        }
    }
    while (true)
    {
      while (true)
      {
        SocialEventListener local4 = new SocialEventListener()
        {
          public void onComplete(Object paramAnonymousObject1, Object paramAnonymousObject2)
          {
            ModelResult localModelResult = (ModelResult)paramAnonymousObject1;
            if (localModelResult == null);
            JSONObject localJSONObject1;
            do
            {
              do
                return;
              while (!localModelResult.isSuccess());
              localJSONObject1 = (JSONObject)JSON.parse(localModelResult.getObj().toString());
            }
            while (localJSONObject1 == null);
            JSONObject localJSONObject2 = localJSONObject1.getJSONObject("data");
            String str1 = "http://url.cn/" + localJSONObject2.getString("short_url");
            String str2 = (String)getValue("message");
            String str3 = (String)getValue("suffix");
            SocialEventListener local1 = new SocialEventListener()
            {
              public void onComplete(Object paramAnonymous2Object1, Object paramAnonymous2Object2)
              {
                ModelResult localModelResult = (ModelResult)paramAnonymous2Object1;
                if (localModelResult.isSuccess())
                {
                  JSONObject localJSONObject = ((JSONObject)JSON.parse(localModelResult.getObj().toString())).getJSONObject("data");
                  String str1 = "http://url.cn/" + localJSONObject.getString("short_url");
                  String str2 = (String)getValue("message");
                  String str3 = (String)getValue("suffix");
                  String str4 = str2 + str1 + str3;
                  String str5 = (String)getValue("playUrl");
                  TencentWeiboApi.shareMusic(ShareUtil.4.this.val$context, str4, str5, (String)getValue("title"), "蜻蜓FM", (SocialEventListener)getValue("listener"));
                }
              }
            };
            local1.setValue("message", str2);
            local1.setValue("suffix", str3);
            local1.setValue("title", getValue("title"));
            local1.setValue("playUrl", str1);
            local1.setValue("listener", getValue("listener"));
            Object localObject = (String)getValue("contentLink");
            try
            {
              String str4 = URLEncoder.encode((String)localObject, "UTF-8");
              localObject = str4;
              TencentWeiboApi.getShortLink(this.val$context, (String)localObject, local1);
              return;
            }
            catch (UnsupportedEncodingException localUnsupportedEncodingException)
            {
              while (true)
                localUnsupportedEncodingException.printStackTrace();
            }
          }
        };
        local4.setValue("message", str8);
        local4.setValue("contentLink", str10);
        local4.setValue("suffix", str9);
        local4.setValue("title", localShareInfoBean.title);
        local4.setValue("playUrl", str6);
        local4.setValue("listener", local3);
        try
        {
          String str12 = URLEncoder.encode(str6, "UTF-8");
          str11 = str12;
          TencentWeiboApi.getShortLink(paramContext, str11, local4);
          return;
          i = str7.indexOf("http://share.qingting.fm/");
        }
        catch (UnsupportedEncodingException localUnsupportedEncodingException)
        {
          while (true)
          {
            localUnsupportedEncodingException.printStackTrace();
            String str11 = str6;
          }
        }
      }
      TencentAgent.getInstance().publishTencentWeibo(localShareInfoBean);
      return;
      label690: switch (paramInt)
      {
      case 0:
      case 1:
      case 2:
      case 3:
      case 4:
      case 5:
      }
      return;
      str16 = str1;
      break;
      label736: str4 = str1;
      break label319;
      label743: str13 = "http://s1.qingting.fm/images/qt_logo.jpg";
      break label407;
      label751: str10 = str9;
    }
  }

  public static void shareToPlatform(String paramString, final int paramInt1, int paramInt2)
  {
    if ((paramString == null) || (paramString.equalsIgnoreCase("")));
    do
    {
      return;
      if (WeiboAgent.getInstance().isLoggedIn().booleanValue())
      {
        SinaWeiboApi.shareText(InfoManager.getInstance().getContext(), paramString, null, null, new SocialEventListener()
        {
          public void onComplete(Object paramAnonymousObject1, Object paramAnonymousObject2)
          {
            Message localMessage = Message.obtain(ShareUtil.mHandler, paramInt1, null);
            ShareUtil.mHandler.sendMessage(localMessage);
          }

          public void onException(Object paramAnonymousObject)
          {
            Message localMessage = Message.obtain(ShareUtil.mHandler, this.val$failCode, null);
            ShareUtil.mHandler.sendMessage(localMessage);
          }
        });
        return;
      }
    }
    while (!TencentAgent.getInstance().isLoggedIn().booleanValue());
    TencentWeiboApi.shareText(InfoManager.getInstance().getContext(), paramString, new SocialEventListener()
    {
      public void onComplete(Object paramAnonymousObject1, Object paramAnonymousObject2)
      {
        Message localMessage = Message.obtain(ShareUtil.mHandler, paramInt1, null);
        ShareUtil.mHandler.sendMessage(localMessage);
      }

      public void onException(Object paramAnonymousObject)
      {
        Message localMessage = Message.obtain(ShareUtil.mHandler, this.val$failCode, null);
        ShareUtil.mHandler.sendMessage(localMessage);
      }
    });
  }

  public static void shareToPlatform(String paramString1, String paramString2, String paramString3, String paramString4, int paramInt, SocialEventListener paramSocialEventListener)
  {
    Context localContext = InfoManager.getInstance().getContext();
    if (localContext == null)
      return;
    switch (paramInt)
    {
    case 2:
    default:
      return;
    case 0:
      Bitmap localBitmap2 = getShareBitmap(localContext, null);
      Boolean localBoolean2 = Boolean.valueOf(false);
      WechatApi.shareWebPage(localContext, paramString1, paramString2, paramString3, localBitmap2, localBoolean2, paramSocialEventListener);
      return;
    case 1:
      Bitmap localBitmap1 = getShareBitmap(localContext, null);
      Boolean localBoolean1 = Boolean.valueOf(true);
      WechatApi.shareWebPage(localContext, paramString1, paramString2, paramString3, localBitmap1, localBoolean1, paramSocialEventListener);
      return;
    case 3:
      QQApi.share(localContext, paramString2, paramString3, paramString4, paramString1, paramString1, paramSocialEventListener);
      return;
    case 4:
    }
    if ((paramString4 == null) || (paramString4.equalsIgnoreCase("")));
    for (String str1 = "http://s1.qingting.fm/images/qt_logo.jpg"; ; str1 = paramString4)
    {
      if (paramString3 == null)
        paramString3 = "";
      String str2 = paramString3 + " ";
      String str3 = str2 + paramString1;
      String str4 = str3 + " (分享自@蜻蜓fm)";
      if ((str1 == null) || (!str1.startsWith("http")))
        break;
      SinaWeiboApi.shareImage(localContext, str4, str1, "", "", paramSocialEventListener);
      return;
    }
  }

  public static String wrapPageUrl(String paramString, int paramInt1, ProgramLocation paramProgramLocation, Context paramContext, int paramInt2)
  {
    return composeTrackUrl(paramString, 1, 1, paramInt1, paramProgramLocation, paramContext);
  }

  public void shareUrlToMoments(String paramString)
  {
    if (paramString == null)
      return;
    WechatApi.shareUrlToMoments(InfoManager.getInstance().getContext(), paramString, "", "", null, new ISocialEventListener()
    {
      public void onCancel(Object paramAnonymousObject)
      {
      }

      public void onComplete(Object paramAnonymousObject1, Object paramAnonymousObject2)
      {
      }

      public void onException(Object paramAnonymousObject)
      {
      }
    });
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.qtradio.share.ShareUtil
 * JD-Core Version:    0.6.2
 */