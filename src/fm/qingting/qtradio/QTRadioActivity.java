package fm.qingting.qtradio;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.Intent.ShortcutIconResource;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.ResolveInfo;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.media.AudioManager;
import android.media.session.MediaSession;
import android.media.session.MediaSession.Callback;
import android.media.session.PlaybackState;
import android.media.session.PlaybackState.Builder;
import android.net.Uri;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.os.Process;
import android.os.SystemClock;
import android.provider.MediaStore.Images.Media;
import android.provider.Settings.System;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.KeyEvent;
import android.view.Window;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.Toast;
import cn.com.iresearch.mapptracker.fm.IRMonitor;
import cn.com.mma.mobile.tracking.api.Countly;
import com.google.android.apps.analytics.GoogleAnalyticsTracker;
import com.ixintui.pushsdk.PushSdkApi;
import com.pingan.pinganwifi.AppGlobal;
import com.tendcloud.tenddata.TCAgent;
import com.umeng.analytics.MobclickAgent;
import com.umeng.message.PushAgent;
import fm.qingting.download.HttpDownloadHelper;
import fm.qingting.download.QTRadioDownloadAgent;
import fm.qingting.framework.controller.StatisticsFMManage;
import fm.qingting.framework.data.DataManager;
import fm.qingting.framework.data.IResultRecvHandler;
import fm.qingting.framework.data.IResultToken;
import fm.qingting.framework.data.NetDS;
import fm.qingting.framework.data.Result;
import fm.qingting.framework.data.ServerConfig;
import fm.qingting.framework.event.IEventHandler;
import fm.qingting.framework.manager.EventDispacthManager;
import fm.qingting.framework.net.HTTPConnection;
import fm.qingting.framework.utils.MobileState;
import fm.qingting.framework.utils.SystemInfo;
import fm.qingting.qtradio.abtest.ABTest;
import fm.qingting.qtradio.abtest.ABTestConfig;
import fm.qingting.qtradio.abtest.ABTestItem;
import fm.qingting.qtradio.controller.ControllerManager;
import fm.qingting.qtradio.data.AlarmDS;
import fm.qingting.qtradio.data.AllFavCategoryDS;
import fm.qingting.qtradio.data.ApiSign;
import fm.qingting.qtradio.data.AttributesDS;
import fm.qingting.qtradio.data.BillboardNodeDS;
import fm.qingting.qtradio.data.CategoryNodeDS;
import fm.qingting.qtradio.data.ChannelNodesDS;
import fm.qingting.qtradio.data.CommonDS;
import fm.qingting.qtradio.data.DBManager;
import fm.qingting.qtradio.data.DownloadingProgramDS;
import fm.qingting.qtradio.data.FavouriteChannelDS;
import fm.qingting.qtradio.data.FreqChannelsDS;
import fm.qingting.qtradio.data.IMContactsDS;
import fm.qingting.qtradio.data.IMDatabaseDS;
import fm.qingting.qtradio.data.IMUserInfoDS;
import fm.qingting.qtradio.data.MediaCenterDS;
import fm.qingting.qtradio.data.MyPodcasterDS;
import fm.qingting.qtradio.data.NotifyDS;
import fm.qingting.qtradio.data.PlayHistoryDS;
import fm.qingting.qtradio.data.PlayListDS;
import fm.qingting.qtradio.data.PlayedMetaDS;
import fm.qingting.qtradio.data.PodcasterDS;
import fm.qingting.qtradio.data.PreDownloadingProgramDS;
import fm.qingting.qtradio.data.ProfileDS;
import fm.qingting.qtradio.data.ProgramNodesDS;
import fm.qingting.qtradio.data.PullNodeDS;
import fm.qingting.qtradio.data.RadioNodesDS;
import fm.qingting.qtradio.data.RecommendCategoryNodeDS;
import fm.qingting.qtradio.data.ReserveProgramDS;
import fm.qingting.qtradio.data.SearchDS;
import fm.qingting.qtradio.data.SocialUserProfileDS;
import fm.qingting.qtradio.data.UserInfoDS;
import fm.qingting.qtradio.data.WeiboProfile;
import fm.qingting.qtradio.doubleclick.DoubleClick;
import fm.qingting.qtradio.fm.PlayerAgent;
import fm.qingting.qtradio.fm.SystemPlayer;
import fm.qingting.qtradio.fm.TaobaoAgent;
import fm.qingting.qtradio.fm.WebViewPlayer;
import fm.qingting.qtradio.fmdriver.FMManager;
import fm.qingting.qtradio.fmdriver.FMcontrol;
import fm.qingting.qtradio.headset.HeadSet;
import fm.qingting.qtradio.headset.MediaButtonReceiver;
import fm.qingting.qtradio.helper.ChannelHelper;
import fm.qingting.qtradio.helper.OnlineUpdateHelper;
import fm.qingting.qtradio.helper.ProgramHelper;
import fm.qingting.qtradio.im.IMAgent;
import fm.qingting.qtradio.im.IMContacts;
import fm.qingting.qtradio.im.info.GroupInfo;
import fm.qingting.qtradio.im.message.IMMessage;
import fm.qingting.qtradio.log.LogModule;
import fm.qingting.qtradio.logger.QTLogger;
import fm.qingting.qtradio.manager.NetWorkManage;
import fm.qingting.qtradio.manager.QtApiLevelManager;
import fm.qingting.qtradio.manager.SkinManager;
import fm.qingting.qtradio.manager.advertisement.AdvertisementManage;
import fm.qingting.qtradio.mobAgent.mobAgentOption;
import fm.qingting.qtradio.model.ActivityNode;
import fm.qingting.qtradio.model.AdvertisementInfoNode;
import fm.qingting.qtradio.model.AdvertisementItemNode;
import fm.qingting.qtradio.model.AlarmInfo;
import fm.qingting.qtradio.model.AlarmInfoNode;
import fm.qingting.qtradio.model.BillboardNode;
import fm.qingting.qtradio.model.CategoryNode;
import fm.qingting.qtradio.model.ChannelNode;
import fm.qingting.qtradio.model.CollectionNode;
import fm.qingting.qtradio.model.ContentCategoryNode;
import fm.qingting.qtradio.model.DownLoadInfoNode;
import fm.qingting.qtradio.model.GlobalCfg;
import fm.qingting.qtradio.model.InfoManager;
import fm.qingting.qtradio.model.InfoManager.DataExceptionStatus;
import fm.qingting.qtradio.model.InfoManager.ISubscribeEventListener;
import fm.qingting.qtradio.model.LiveNode;
import fm.qingting.qtradio.model.Node;
import fm.qingting.qtradio.model.PersonalCenterNode;
import fm.qingting.qtradio.model.PlayHistoryInfoNode;
import fm.qingting.qtradio.model.PlayedMetaInfo;
import fm.qingting.qtradio.model.ProgramNode;
import fm.qingting.qtradio.model.QTLocation;
import fm.qingting.qtradio.model.RadioNode;
import fm.qingting.qtradio.model.ReserveInfoNode;
import fm.qingting.qtradio.model.RingToneInfoNode;
import fm.qingting.qtradio.model.RingToneNode;
import fm.qingting.qtradio.model.RootNode;
import fm.qingting.qtradio.model.RootNode.FromType;
import fm.qingting.qtradio.model.RootNode.PlayMode;
import fm.qingting.qtradio.model.ShareInfoNode;
import fm.qingting.qtradio.model.SharedCfg;
import fm.qingting.qtradio.model.UpgradeInfo;
import fm.qingting.qtradio.model.advertisement.QTAdvertisementInfo;
import fm.qingting.qtradio.notification.QTAlarmReceiver;
import fm.qingting.qtradio.offline.OfflineManager;
import fm.qingting.qtradio.parser.NetParser;
import fm.qingting.qtradio.playlist.PlayListManager;
import fm.qingting.qtradio.push.bean.PushType;
import fm.qingting.qtradio.push.config.PushConfig;
import fm.qingting.qtradio.push.log.ClickNotificationLog;
import fm.qingting.qtradio.push.log.NDPushLog;
import fm.qingting.qtradio.pushcontent.PushLiveConfig;
import fm.qingting.qtradio.pushcontent.PushLiveLog;
import fm.qingting.qtradio.pushmessage.PushMessageLog;
import fm.qingting.qtradio.pushmessage.UmengPushIntentService;
import fm.qingting.qtradio.remotecontrol.RemoteControl;
import fm.qingting.qtradio.retain.RetainLog;
import fm.qingting.qtradio.ring.RingManager;
import fm.qingting.qtradio.room.UserInfo;
import fm.qingting.qtradio.room.WeiboChat;
import fm.qingting.qtradio.sensor.SpeedSensor;
import fm.qingting.qtradio.social.CloudCenter;
import fm.qingting.qtradio.tencentAgent.TencentAgent;
import fm.qingting.qtradio.view.LaunchView;
import fm.qingting.qtradio.view.MainView;
import fm.qingting.qtradio.view.UserGuideView;
import fm.qingting.qtradio.view.popviews.AlertParam;
import fm.qingting.qtradio.view.popviews.AlertParam.Builder;
import fm.qingting.qtradio.view.popviews.AlertParam.OnButtonClickListener;
import fm.qingting.qtradio.view.popviews.CategoryResortPopView.CategoryResortInfo;
import fm.qingting.qtradio.weiboAgent.WeiboAgent;
import fm.qingting.qtradio.weixin.WeixinAgent;
import fm.qingting.qtradio.wo.WoApiRequest;
import fm.qingting.qtradio.wo.WoNetEventListener;
import fm.qingting.utils.AppInfo;
import fm.qingting.utils.CPUInfo;
import fm.qingting.utils.DateUtil;
import fm.qingting.utils.DeviceInfo;
import fm.qingting.utils.LifeTime;
import fm.qingting.utils.PlayProcessSyncUtil;
import fm.qingting.utils.QTMSGManage;
import fm.qingting.utils.RecommendStatisticsUtil;
import fm.qingting.utils.ScreenType;
import fm.qingting.utils.ThirdAdv;
import fm.qingting.utils.ThirdTracker;
import fm.qingting.utils.Zeus;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class QTRadioActivity extends Activity
  implements IEventHandler, IResultRecvHandler, InfoManager.ISubscribeEventListener
{
  public static final int CROP_IMAGE_CAPTURE = 79;
  public static final int CROP_IMAGE_PICK = 83;
  private static int Maxfreq = 0;
  private static int Minfreq = 0;
  public static final int SELECT_PIC_BY_CAPTURE = 73;
  public static final int SELECT_PIC_BY_PICK_PHOTO = 71;
  private int MaxVolume = 15;
  private int MinVolume = 0;
  private long curScanTime = 0L;
  private long curnetplaytime = 0L;
  private int currentVolume = -1;
  private long gpsLocateStartTime;
  private String gpsLocation = null;
  private boolean hasLocal = false;
  private boolean hasOpenSpeaker = false;
  private Handler headSetPluggedHandler = new Handler()
  {
    public void handleMessage(Message paramAnonymousMessage)
    {
      if (paramAnonymousMessage.what == 1);
    }
  };
  private boolean ignoreBootStrapResult = false;
  private boolean inited = false;
  private long ipLocateStartTime;
  private String ipLocation = null;
  private AudioManager mAudioManager;
  private boolean mCarPlay = false;
  private Context mContext;
  private boolean mDisplayAD = false;
  private LaunchView mLaunchView;
  private MediaSession mMediaSession;
  private boolean mNotifySwitchState = false;
  private PushAgent mPushAgent;
  private boolean mShowAdvertisement = false;
  private UpgradeInfo mUpgradeInfo;
  private String mUpgradeName = "QTRadioUpgrade.apk";
  private MainView mainView;
  private MediaButtonReceiver mediaButtonReceiver = new MediaButtonReceiver();
  private boolean playedLastChannel = true;
  private QTLocation positionLocation = null;
  private long preScanTime = 0L;
  private long prenetplaytime = 0L;
  private final String quitAction = "fm.qingting.quit";
  private boolean readyToStart = false;
  private ServiceCommandReceiver serviceCommandReceiver;
  private final String startAction = "fm.qingting.start";
  private boolean started = false;
  private Runnable timingWake = new Runnable()
  {
    public void run()
    {
      QTRadioActivity.access$802(QTRadioActivity.this, true);
      QTRadioActivity.this.startMain();
    }
  };
  private GoogleAnalyticsTracker tracker;
  private Handler wakeHandler = new Handler();

  private void InitWeiboProfile()
  {
    WeiboProfile.getInstance().setAppKey("790020947");
    WeiboProfile.getInstance().setAppSecret("7ebd878125b90a8a87f25ddf1483960f");
    WeiboProfile.getInstance().setRedirectUrl("http://weibo.callback.qingting.fm");
    WeiboProfile localWeiboProfile = WeiboProfile.getInstance();
    Object[] arrayOfObject = new Object[1];
    arrayOfObject[0] = getString(2131492874);
    localWeiboProfile.setUniad(String.format("(发自@蜻蜓FM http://qingting.fm/?os=android&from=weibo&version=%s&action=", arrayOfObject));
  }

  private void ShowDialogReportToUser()
  {
    DialogInterface.OnClickListener local5 = new DialogInterface.OnClickListener()
    {
      public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt)
      {
        switch (paramAnonymousInt)
        {
        default:
          return;
        case -1:
        }
        QTRadioActivity.this.quit();
      }
    };
    AlertDialog localAlertDialog = new AlertDialog.Builder(this).create();
    localAlertDialog.setButton("退出", local5);
    localAlertDialog.setButton2("继续", local5);
    localAlertDialog.setMessage("抱歉，蜻蜓暂时不能正常工作，您可以加入蜻蜓官方客服QQ群(171440910)，我们会有专业工程师第一时间为您解决此问题");
    localAlertDialog.show();
  }

  private void acquireABTest()
  {
    InfoManager.getInstance().setStatusPage(true);
    String str1 = ABTest.getInstance().getOption(ABTestConfig.frontCollection.OptionName);
    InfoManager localInfoManager = InfoManager.getInstance();
    if ((str1 != null) && (str1.equalsIgnoreCase(ABTestConfig.frontCollection.OptionA)));
    for (boolean bool = true; ; bool = false)
    {
      localInfoManager.setEnableFrontCollection(bool);
      if (SharedCfg.getInstance().getDefaultCollection())
        InfoManager.getInstance().setEnableFrontCollection(true);
      String str2 = ABTest.getInstance().getOption(ABTestConfig.checkin.OptionName);
      if (str2 != null)
      {
        if (!str2.equalsIgnoreCase(ABTestConfig.checkin.OptionA))
          break;
        InfoManager.getInstance().setEnableNewCheckin(1);
      }
      return;
    }
    InfoManager.getInstance().setEnableNewCheckin(2);
  }

  private void acquireAdvertisementParam()
  {
    try
    {
      String str = MobclickAgent.getConfigParams(this, "showAdvertisement");
      if ((str != null) && (!str.equalsIgnoreCase("")))
        InfoManager.getInstance().setAdvertisementRadio(Integer.valueOf(str).intValue());
      return;
    }
    catch (Exception localException)
    {
    }
  }

  private void acquireUmengConfig()
  {
    try
    {
      String str1 = MobclickAgent.getConfigParams(this, "CollectionRemindTime");
      if ((str1 != null) && (!str1.equalsIgnoreCase("")))
        GlobalCfg.getInstance(this).setValueToDB("CollectionRemindTime", "integer", str1);
      String str2 = MobclickAgent.getConfigParams(this, "allowBubbleChannels");
      SharedCfg.getInstance().setBubbleChannelList(str2);
      String str3 = AppInfo.getChannelName(this);
      SharedCfg.getInstance().setMutiRate(true);
      SharedCfg.getInstance().setSaveBattery(true);
      String str4 = MobclickAgent.getConfigParams(this, "filterStatement");
      if ((str4 != null) && (!str4.equalsIgnoreCase("")))
        SharedCfg.getInstance().setFilterLiveRoom(str4);
      String str5 = MobclickAgent.getConfigParams(this, "isNeedDisplayAD");
      label230: String str15;
      if (str5 != null)
      {
        if (str5.equalsIgnoreCase("1"))
          this.mDisplayAD = true;
      }
      else
      {
        String str6 = MobclickAgent.getConfigParams(this, "MaxWordsInLiveRoom");
        if ((str6 != null) && (!str6.equalsIgnoreCase("")))
          InfoManager.getInstance().setMaxWordsInLiveRoom(Integer.valueOf(str6).intValue());
        InfoManager.getInstance().setAutoSeek(true);
        InfoManager.getInstance().setEnableAutoSeek(true);
        String str7 = MobclickAgent.getConfigParams(this, "useFloatWindow");
        if (str7 != null)
        {
          if ((!str7.contains(str3)) && (!str7.equalsIgnoreCase("all")))
            break label2703;
          GlobalCfg.getInstance(this).setFloatWindow(true);
        }
        InfoManager.getInstance().setShowRecommendApp(false);
        PushConfig.pullUmengConfig(this);
        PushLiveConfig.pullUmengConfig(this);
        String str8 = MobclickAgent.getConfigParams(this, "autoReserveMinDuration");
        if ((str8 != null) && (!str8.equalsIgnoreCase("")))
          InfoManager.getInstance().setAutoReserveMinDuration(Integer.valueOf(str8).intValue());
        String str9 = MobclickAgent.getConfigParams(this, "PushLiveSwitch");
        if (str9 != null)
        {
          if (!str9.equalsIgnoreCase("true"))
            break label2714;
          GlobalCfg.getInstance(this).setPushLiveSwitch(true);
        }
        label317: InfoManager.getInstance().setUsePlayCache(true);
        String str10 = MobclickAgent.getConfigParams(this, "enterChatRoom");
        if (str10 != null)
          InfoManager.getInstance().setChatroom(str10);
        InfoManager.getInstance().setEnableSetDNS(false);
        String str11 = MobclickAgent.getConfigParams(this, "apple");
        if (str11 != null)
        {
          if ((!str11.contains(str3)) && (!str11.contains("all")))
            break label2725;
          InfoManager.getInstance().apple(true);
          String str12 = MobclickAgent.getConfigParams(this, "appleImage");
          if (str12 != null)
            InfoManager.getInstance().setAppImage(str12);
          String str13 = MobclickAgent.getConfigParams(this, "appleDesc");
          if (str13 != null)
            InfoManager.getInstance().setAppDesc(str13);
          String str14 = MobclickAgent.getConfigParams(this, "appleUrl");
          if (str14 != null)
            InfoManager.getInstance().setAppleUrl(str14);
        }
        label461: str15 = MobclickAgent.getConfigParams(this, "shareTag");
        if (str15 != null)
        {
          if (!str15.equalsIgnoreCase("#"))
            break label2735;
          InfoManager.getInstance().setShareTag("");
        }
        label495: String str16 = MobclickAgent.getConfigParams(this, "enableGroup");
        if (str16 != null)
          InfoManager.getInstance().setEnableSocial(str16);
        String str17 = MobclickAgent.getConfigParams(this, "addGroupSlogan");
        if (str17 != null)
          InfoManager.getInstance().setAddGroupSlogan(str17);
        String str18 = MobclickAgent.getConfigParams(this, "linkDuration");
        if ((str18 != null) && (!str18.equalsIgnoreCase("")))
          InfoManager.getInstance().setLinkDuration(Integer.valueOf(str18).intValue());
        String str19 = MobclickAgent.getConfigParams(this, "LiveRoomBlacklist");
        if (str19 != null)
          InfoManager.getInstance().setLiveRoomBlack(str19);
        String str20 = MobclickAgent.getConfigParams(this, "ZeusV2");
        if (str20 != null)
          Zeus.getInstance().setZeusUrl(str20);
        String str21 = MobclickAgent.getConfigParams(this, "ZeusV2Percent");
        if (str21 != null)
          Zeus.getInstance().setZeusPercent(str21);
        String str22 = MobclickAgent.getConfigParams(this, "TrackerRegions");
        if (str22 != null)
          ThirdTracker.getInstance().setTrackerRegions(str22);
        String str23 = MobclickAgent.getConfigParams(this, "ADTracker");
        if (str23 != null)
          ThirdTracker.getInstance().setADUrl(str23);
        String str24 = MobclickAgent.getConfigParams(this, "ADTrackerPercent");
        if (str24 != null)
          ThirdTracker.getInstance().setADPercent(str24);
        String str25 = MobclickAgent.getConfigParams(this, "MZTracker");
        if (str25 != null)
          ThirdTracker.getInstance().setMZUrl(str25);
        String str26 = MobclickAgent.getConfigParams(this, "MZTrackerPercent");
        if (str26 != null)
          ThirdTracker.getInstance().setMZPercent(str26);
        String str27 = MobclickAgent.getConfigParams(this, "Athena");
        if ((str27 != null) && (!str27.equalsIgnoreCase("")))
          InfoManager.getInstance().setAthenaLife(str27);
        String str28 = MobclickAgent.getConfigParams(this, "Achilles");
        if ((str28 != null) && (!str28.equalsIgnoreCase("")) && ((str28.contains(str3)) || (str28.equalsIgnoreCase("all"))))
        {
          InfoManager.getInstance().setAchilles(true);
          String str81 = MobclickAgent.getConfigParams(this, "AchillesUrl");
          if (str81 != null)
            InfoManager.getInstance().setAchillesUrl(str81);
          String str82 = MobclickAgent.getConfigParams(this, "AchillesPercent");
          if (str82 != null)
            InfoManager.getInstance().setAchillesPercent(str82);
        }
        String str29 = MobclickAgent.getConfigParams(this, "ApolloStartTime");
        if ((str29 != null) && (!str29.equalsIgnoreCase("")))
          SharedCfg.getInstance().setApolloStartTime(Integer.valueOf(str29).intValue());
        String str30 = MobclickAgent.getConfigParams(this, "ApolloDuration");
        if ((str30 != null) && (!str30.equalsIgnoreCase("")))
          SharedCfg.getInstance().setApolloDuration(Integer.valueOf(str30).intValue());
        String str31 = MobclickAgent.getConfigParams(this, "sellApps");
        if ((str31 != null) && ((str31.contains(str3)) || (str31.equalsIgnoreCase("all"))))
        {
          String str32 = MobclickAgent.getConfigParams(this, "sellAppVersion");
          if ((str32 != null) && (!str32.equalsIgnoreCase("")) && (Integer.valueOf(str32).intValue() > AppInfo.getCurrentVersionCode(this)))
          {
            InfoManager.getInstance().setSellApps(true);
            String str79 = MobclickAgent.getConfigParams(this, "sellAppsInfo");
            if (str79 != null)
              InfoManager.getInstance().setSellAppsInfo(str79);
            String str80 = MobclickAgent.getConfigParams(this, "sellAppsPackage");
            if ((str80 != null) && (!str80.equalsIgnoreCase("")))
              InfoManager.getInstance().setSellAppsPackage(str80);
          }
        }
        String str33 = MobclickAgent.getConfigParams(this, "enableAudioAdv");
        if (str33 != null)
        {
          if ((!str33.contains(str3)) && (!str33.equalsIgnoreCase("all")))
            break label2746;
          InfoManager.getInstance().setAudioAdv(true);
        }
        label1141: String str34 = MobclickAgent.getConfigParams(this, "taobaoAdv1");
        if (str34 != null)
        {
          if ((!str34.contains(str3)) && (!str34.equalsIgnoreCase("all")))
            break label2756;
          InfoManager.getInstance().setTaobaoAudioAdv(true);
        }
        label1183: String str35 = MobclickAgent.getConfigParams(this, "taobaoAdvId");
        if ((str35 != null) && (!str35.equalsIgnoreCase("")))
          TaobaoAgent.getInstance().setADId(str35);
        String str36 = MobclickAgent.getConfigParams(this, "StudentABTest");
        if ((str36 != null) && (str36.equalsIgnoreCase("0")))
          SharedCfg.getInstance().setChooseStudent(0);
        String str37 = MobclickAgent.getConfigParams(this, "doubleClickUrls");
        if (str37 != null)
        {
          DoubleClick.getInstance().setZeusUrl(str37);
          String str38 = MobclickAgent.getConfigParams(this, "doubleClickPercent");
          if (str38 != null)
            DoubleClick.getInstance().setZeusPercent(str38);
        }
        String str39 = MobclickAgent.getConfigParams(this, "doubleClickPattern");
        if (str39 != null)
        {
          DoubleClick.getInstance().setPattern(str39);
          String str40 = MobclickAgent.getConfigParams(this, "doubleClickConfig");
          if (str40 != null)
          {
            DoubleClick.getInstance().setConfigs(str40);
            String str41 = MobclickAgent.getConfigParams(this, "doubleClickConfigPercent");
            if (str41 != null)
              DoubleClick.getInstance().setPercents(str41);
          }
        }
        String str42 = MobclickAgent.getConfigParams(this, "ForceLogin");
        if (str42 != null)
        {
          if ((!str42.contains(str3)) && (!str42.equalsIgnoreCase("all")))
            break label2766;
          InfoManager.getInstance().setForceLogin(true);
        }
        label1400: String str43 = MobclickAgent.getConfigParams(this, "chinaUnicomFlow");
        if (str43 != null)
        {
          if ((!str43.contains(str3)) && (!str43.contains("all")))
            break label2776;
          InfoManager.getInstance().setEnableWoQt(true);
        }
        label1442: String str44 = MobclickAgent.getConfigParams(this, "chinaUnicomZone");
        if (str44 != null)
        {
          if ((!str44.contains(str3)) && (!str44.contains("all")))
            break label2786;
          InfoManager.getInstance().setChinaUnicomZone(true);
        }
        label1484: String str45 = MobclickAgent.getConfigParams(this, "quickDownloadChannel");
        if (str45 != null)
        {
          if ((!str45.contains(str3)) && (!str45.contains("all")))
            break label2796;
          OnlineUpdateHelper.getInstance().setNeedQuickDownload(true);
        }
        label1526: String str46 = MobclickAgent.getConfigParams(this, "pingan");
        if (str46 != null)
        {
          if ((!str46.contains(str3)) && (!str46.contains("all")))
            break label2806;
          InfoManager.getInstance().setEnablePingan(true);
        }
        label1568: String str47 = MobclickAgent.getConfigParams(this, "ug_category_recommend");
        if (str47 != null)
          InfoManager.getInstance().setUserguideRecommend(str47);
        String str48 = MobclickAgent.getConfigParams(this, "IREChange");
        if ((str48 != null) && (!str48.equalsIgnoreCase("")))
          SharedCfg.getInstance().setIREChange(Integer.valueOf(str48).intValue());
        String str49 = MobclickAgent.getConfigParams(this, "taobaoChange");
        if ((str49 != null) && (!str49.equalsIgnoreCase("")))
          SharedCfg.getInstance().setTaoBaoChange(Integer.valueOf(str49).intValue());
        String str50 = MobclickAgent.getConfigParams(this, "AdvLoc");
        if (str50 != null)
        {
          if ((!str50.contains(str3)) && (!str50.contains("all")))
            break label2816;
          InfoManager.getInstance().setADVLoc(true);
        }
        label1710: String str51 = MobclickAgent.getConfigParams(this, "allowMusicDownload");
        if ((str51 != null) && (!str51.equalsIgnoreCase("")))
          InfoManager.getInstance().setAllowDownloadMusic(str51);
        String str52 = MobclickAgent.getConfigParams(this, "barrage");
        if ((str52 != null) && (!str52.equalsIgnoreCase("")))
          InfoManager.getInstance().setBarrage(str52);
        String str53 = MobclickAgent.getConfigParams(this, "defaultCollectionKey");
        String str54 = MobclickAgent.getConfigParams(this, "defaultCollectionValue");
        InfoManager.getInstance().setDefaultCollectionChannelId(str53, str54);
        String str55 = MobclickAgent.getConfigParams(this, "defaultSTKey");
        String str56 = MobclickAgent.getConfigParams(this, "defaultSTValue");
        InfoManager.getInstance().setDefaultSpecialTopic(str55, str56);
        String str57 = MobclickAgent.getConfigParams(this, "advFromAirWave2");
        if ((str57 != null) && ((str57.contains(str3)) || (str57.equalsIgnoreCase("all"))))
          InfoManager.getInstance().enableAirWave(true);
        String str58 = MobclickAgent.getConfigParams(this, "advFromAirWaveCity");
        if (str58 != null)
        {
          String str59 = InfoManager.getInstance().getCurrentCity();
          if (((str59 != null) && (!str58.contains(str59))) || (str58.equalsIgnoreCase("#")))
            InfoManager.getInstance().enableAirWaveCity(true);
        }
        String str60 = MobclickAgent.getConfigParams(this, "advFromAirWaveShow");
        if ((str60 != null) && (!str60.equalsIgnoreCase("#")) && (!str60.equalsIgnoreCase("")))
          InfoManager.getInstance().setAirWaveShow(Integer.valueOf(str60).intValue());
        String str61 = MobclickAgent.getConfigParams(this, "advFromAirWaveClick");
        if ((str61 != null) && (!str61.equalsIgnoreCase("#")) && (!str61.equalsIgnoreCase("")))
          InfoManager.getInstance().setAirWaveClick(Integer.valueOf(str61).intValue());
        String str62 = MobclickAgent.getConfigParams(this, "JDADChannel");
        if (str62 != null)
        {
          if ((!str62.contains(str3)) && (!str62.equalsIgnoreCase("all")))
            break label2826;
          InfoManager.getInstance().sethasJdAd(true);
        }
        label2071: String str63 = MobclickAgent.getConfigParams(this, "JDADPosition");
        if (str63 != null)
          InfoManager.getInstance().setJdAdPosition(str63);
        String str64 = MobclickAgent.getConfigParams(this, "topPlayHistory");
        if (str64 != null)
        {
          if ((!str64.contains(str3)) && (!str64.equalsIgnoreCase("all")))
            break label2836;
          InfoManager.getInstance().setTopHistory(true);
        }
        label2135: String str65 = MobclickAgent.getConfigParams(this, "advJDCity");
        if (str65 != null)
        {
          String str66 = InfoManager.getInstance().getCurrentCity();
          if (((str66 != null) && (!str65.contains(str66))) || (str65.equalsIgnoreCase("#")))
            InfoManager.getInstance().enableJDCity(true);
        }
        String str67 = MobclickAgent.getConfigParams(this, "advJDShow");
        if ((str67 != null) && (!str67.equalsIgnoreCase("#")) && (!str67.equalsIgnoreCase("")))
          InfoManager.getInstance().setJDShow(Integer.valueOf(str67).intValue());
        String str68 = MobclickAgent.getConfigParams(this, "advJDClick");
        if ((str68 != null) && (!str68.equalsIgnoreCase("#")) && (!str68.equalsIgnoreCase("")))
          InfoManager.getInstance().setJDClick(Integer.valueOf(str68).intValue());
        String str69 = MobclickAgent.getConfigParams(this, "h7");
        if ((str69 != null) && ((str69.contains(str3)) || (str69.equalsIgnoreCase("all"))))
          InfoManager.getInstance().setEnableH5(true);
        String str70 = MobclickAgent.getConfigParams(this, "topPlayHistory");
        if (str70 != null)
        {
          if ((!str70.contains(str3)) && (!str70.equalsIgnoreCase("all")))
            break label2846;
          InfoManager.getInstance().setTopHistory(true);
        }
      }
      while (true)
      {
        String str71 = MobclickAgent.getConfigParams(this, "advJDCity");
        if (str71 != null)
        {
          String str72 = InfoManager.getInstance().getCurrentCity();
          if (((str72 != null) && (!str71.contains(str72))) || (str71.equalsIgnoreCase("#")))
            InfoManager.getInstance().enableJDCity(true);
        }
        String str73 = MobclickAgent.getConfigParams(this, "advJDShow");
        if ((str73 != null) && (!str73.equalsIgnoreCase("#")) && (!str73.equalsIgnoreCase("")))
          InfoManager.getInstance().setJDShow(Integer.valueOf(str73).intValue());
        String str74 = MobclickAgent.getConfigParams(this, "advJDClick");
        if ((str74 != null) && (!str74.equalsIgnoreCase("#")) && (!str74.equalsIgnoreCase("")))
          InfoManager.getInstance().setJDClick(Integer.valueOf(str74).intValue());
        String str75 = MobclickAgent.getConfigParams(this, "h6");
        if ((str75 != null) && ((str75.contains(str3)) || (str75.equalsIgnoreCase("all"))))
          InfoManager.getInstance().setEnableH5(true);
        String str76 = MobclickAgent.getConfigParams(this, "advJDSeed");
        if ((str76 != null) && (!str76.equalsIgnoreCase("")))
          ThirdTracker.getInstance().setJDSeed(Integer.valueOf(str76).intValue());
        String str77 = MobclickAgent.getConfigParams(this, "programabtest");
        if ((str77 != null) && ((str77.contains(str3)) || (str77.equalsIgnoreCase("all"))))
          InfoManager.getInstance().setEnableProgramABTest(true);
        String str78 = MobclickAgent.getConfigParams(this, "game");
        if ((str78 == null) || ((!str78.contains(str3)) && (!str78.equalsIgnoreCase("all"))))
          break label2856;
        InfoManager.getInstance().setEnablGame(true);
        return;
        this.mDisplayAD = false;
        break;
        label2703: GlobalCfg.getInstance(this).setFloatWindow(false);
        break label230;
        label2714: GlobalCfg.getInstance(this).setPushLiveSwitch(false);
        break label317;
        label2725: InfoManager.getInstance().apple(false);
        break label461;
        label2735: InfoManager.getInstance().setShareTag(str15);
        break label495;
        label2746: InfoManager.getInstance().setAudioAdv(false);
        break label1141;
        label2756: InfoManager.getInstance().setTaobaoAudioAdv(false);
        break label1183;
        label2766: InfoManager.getInstance().setForceLogin(false);
        break label1400;
        label2776: InfoManager.getInstance().setEnableWoQt(false);
        break label1442;
        label2786: InfoManager.getInstance().setChinaUnicomZone(false);
        break label1484;
        label2796: OnlineUpdateHelper.getInstance().setNeedQuickDownload(false);
        break label1526;
        label2806: InfoManager.getInstance().setEnablePingan(false);
        break label1568;
        label2816: InfoManager.getInstance().setADVLoc(false);
        break label1710;
        label2826: InfoManager.getInstance().sethasJdAd(false);
        break label2071;
        label2836: InfoManager.getInstance().setTopHistory(false);
        break label2135;
        label2846: InfoManager.getInstance().setTopHistory(false);
      }
      label2856: return;
    }
    catch (Exception localException)
    {
    }
  }

  private boolean addShortCut(Context paramContext, String paramString)
  {
    if (SharedCfg.getInstance().hasAddedShortcut())
      return false;
    if (hasShortcut())
      return false;
    String str1 = "unknown";
    PackageManager localPackageManager = paramContext.getPackageManager();
    Intent localIntent1 = new Intent("android.intent.action.MAIN", null);
    localIntent1.addCategory("android.intent.category.LAUNCHER");
    Iterator localIterator = localPackageManager.queryIntentActivities(localIntent1, 1).iterator();
    String str2;
    int i;
    while (localIterator.hasNext())
    {
      ResolveInfo localResolveInfo = (ResolveInfo)localIterator.next();
      if (TextUtils.equals(localResolveInfo.activityInfo.packageName, paramString))
      {
        str1 = localResolveInfo.loadLabel(localPackageManager).toString();
        int j = localResolveInfo.activityInfo.applicationInfo.icon;
        str2 = localResolveInfo.activityInfo.name;
        i = j;
      }
    }
    while (true)
    {
      if (TextUtils.isEmpty(str2))
        return false;
      Intent localIntent2 = new Intent("com.android.launcher.action.INSTALL_SHORTCUT");
      localIntent2.putExtra("android.intent.extra.shortcut.NAME", str1);
      localIntent2.putExtra("duplicate", false);
      ComponentName localComponentName = new ComponentName(paramString, str2);
      localIntent2.putExtra("android.intent.extra.shortcut.INTENT", new Intent("android.intent.action.MAIN").setComponent(localComponentName));
      Object localObject;
      if (TextUtils.equals(paramString, paramContext.getPackageName()))
        localObject = paramContext;
      while (true)
      {
        if (localObject != null)
          localIntent2.putExtra("android.intent.extra.shortcut.ICON_RESOURCE", Intent.ShortcutIconResource.fromContext((Context)localObject, i));
        paramContext.sendBroadcast(localIntent2);
        SharedCfg.getInstance().setShortcutAdded();
        return true;
        try
        {
          Context localContext = paramContext.createPackageContext(paramString, 3);
          localObject = localContext;
        }
        catch (PackageManager.NameNotFoundException localNameNotFoundException)
        {
          localNameNotFoundException.printStackTrace();
          localObject = null;
        }
      }
      i = -1;
      str2 = null;
    }
  }

  private void cancelSystemAlarm()
  {
    try
    {
      Intent localIntent1 = new Intent("fm.qingting.alarmintent");
      localIntent1.setClass(this, QTAlarmReceiver.class);
      PendingIntent localPendingIntent = PendingIntent.getBroadcast(this, 0, localIntent1, 134217728);
      AlarmManager localAlarmManager = (AlarmManager)getSystemService("alarm");
      localAlarmManager.cancel(localPendingIntent);
      Intent localIntent2 = new Intent("fm.qingting.reserveintent");
      localIntent1.setClass(this, QTAlarmReceiver.class);
      localAlarmManager.cancel(PendingIntent.getBroadcast(this, 0, localIntent2, 134217728));
      Intent localIntent3 = new Intent("fm.qingting.notifyintent");
      localIntent1.setClass(this, QTAlarmReceiver.class);
      localAlarmManager.cancel(PendingIntent.getBroadcast(this, 0, localIntent3, 134217728));
      return;
    }
    catch (Exception localException)
    {
    }
  }

  private void change(String paramString)
  {
    Class localClass = paramString.getClass();
    try
    {
      Field localField1 = localClass.getDeclaredField("value");
      Field localField2 = localClass.getDeclaredField("count");
      localField2.setAccessible(true);
      localField1.setAccessible(true);
      int i = SharedCfg.getInstance().getIREChange();
      if (i == 0)
        return;
      String str = paramString + "CM";
      int j = SharedCfg.getInstance().getBootstrapCnt() % i;
      if (j != 0)
      {
        for (int k = 0; k < j; k++)
          str = str + String.valueOf(k);
        char[] arrayOfChar = str.toCharArray();
        localField2.set(paramString, Integer.valueOf(arrayOfChar.length));
        localField1.set(paramString, arrayOfChar);
        return;
      }
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
  }

  private void checkHasReserveTask()
  {
    long l1 = InfoManager.getInstance().root().mPersonalCenterNode.reserveNode.getReadyToInvokeReserveTask();
    if (l1 < 9223372036854775807L)
      try
      {
        AlarmManager localAlarmManager = (AlarmManager)getSystemService("alarm");
        PendingIntent localPendingIntent = PendingIntent.getBroadcast(this, 0, new Intent("fm.qingting.reserveintent"), 134217728);
        long l2 = l1 * 1000L - 300000L;
        if (l2 < System.currentTimeMillis())
        {
          localAlarmManager.setRepeating(1, 10000L + System.currentTimeMillis(), 60000L, localPendingIntent);
          return;
        }
        localAlarmManager.setRepeating(0, l2, 60000L, localPendingIntent);
        return;
      }
      catch (Exception localException)
      {
      }
  }

  private void checkIfFloatSwitchIsOn()
  {
    if ((GlobalCfg.getInstance(this.mContext).getFloatWindow()) && (GlobalCfg.getInstance(this.mContext).getFloatState()))
      LogModule.getInstance().send("FloatSwitchOn", QTLogger.getInstance().buildCommonLog());
  }

  private void checkIfPushable()
  {
    int i = 1;
    List localList = InfoManager.getInstance().root().mPersonalCenterNode.myCollectionNode.getFavouriteNodes();
    if ((localList != null) && (localList.size() > 0))
      QTMSGManage.getInstance().sendStatistcsMessage("haveFavorChannel", "" + localList.size());
    for (int j = i; ; j = 0)
    {
      if (InfoManager.getInstance().getPushSwitch() == i)
      {
        QTMSGManage.getInstance().sendStatistcsMessage("PushSwitchIsOn", "true");
        QTLogger.getInstance().sendOther("PushSwitchIsOn", "1");
      }
      while (true)
      {
        if ((j != 0) && (i != 0))
          QTMSGManage.getInstance().sendStatistcsMessage("UserIsPushable", "true");
        return;
        i = 0;
      }
    }
  }

  private void checkLocation()
  {
  }

  private void checkSystem()
  {
    if (this.mLaunchView != null)
    {
      this.mLaunchView.close(false);
      this.mLaunchView = null;
    }
    if ((InfoManager.getInstance().getStatusPage()) && (!SharedCfg.getInstance().getGuideShowed()))
    {
      UserGuideView localUserGuideView = new UserGuideView(this.mContext);
      localUserGuideView.setEventHandler(this);
      setContentView(localUserGuideView);
    }
    do
    {
      return;
      showMainView();
    }
    while ((handleMessageOnCreate(getIntent())) || (!InfoManager.getInstance().hasConnectedNetwork()) || (InfoManager.getInstance().getDefaultSpecialTopic() == 0));
    ControllerManager.getInstance().openSpecialTopicController(InfoManager.getInstance().getDefaultSpecialTopic());
  }

  private void checkUpgrade()
  {
    try
    {
      OnlineUpdateHelper.getInstance();
      String str = DeviceInfo.getUniqueId(this);
      HashMap localHashMap = new HashMap();
      localHashMap.put("deviceid", str);
      localHashMap.put("version", AppInfo.getCurrentInternalVersion(this));
      localHashMap.put("cpu", String.valueOf(()SystemInfo.getInstance().getMaxCpuFreq() / 1000L));
      localHashMap.put("mem", String.valueOf(SystemInfo.getInstance().getAvailMem(this)));
      localHashMap.put("network", String.valueOf(MobileState.getNetWorkType(this)));
      localHashMap.put("sysver", DeviceInfo.getAndroidOsVersion());
      localHashMap.put("channel", AppInfo.getChannelName(this).trim().replaceAll(" ", ""));
      DataManager.getInstance().getData("upgrade_online", this, localHashMap);
      return;
    }
    catch (Exception localException)
    {
    }
  }

  private String convetToPercentage(int paramInt)
  {
    NumberFormat localNumberFormat = NumberFormat.getInstance();
    localNumberFormat.setMaximumFractionDigits(0);
    return localNumberFormat.format(100.0F * ((paramInt - Minfreq) / (Maxfreq - Minfreq))) + "%";
  }

  private void cropImageUri(Uri paramUri1, Uri paramUri2, int paramInt1, int paramInt2, int paramInt3)
  {
    Intent localIntent = new Intent("com.android.camera.action.CROP");
    localIntent.setDataAndType(paramUri1, "image/*");
    localIntent.putExtra("crop", "true");
    localIntent.putExtra("aspectX", 720);
    localIntent.putExtra("aspectY", 574);
    localIntent.putExtra("outputX", paramInt1);
    localIntent.putExtra("outputY", paramInt2);
    localIntent.putExtra("scale", true);
    localIntent.putExtra("output", paramUri2);
    localIntent.putExtra("return-data", false);
    localIntent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
    localIntent.putExtra("noFaceDetection", true);
    startActivityForResult(localIntent, paramInt3);
  }

  private void dealPushMsg(Bundle paramBundle)
  {
    this.playedLastChannel = false;
    int i = InfoManager.getInstance().getNetWorkType();
    QTMSGManage.getInstance().sendStatistcsMessage("ContentUpdatePushClicked", String.valueOf(i));
    String str = paramBundle.getString("notify_type");
    if ((str != null) && (str.equalsIgnoreCase("push_live_channel")))
      PushLiveLog.sendClickNotification(this, paramBundle.getString("live_topic"), "launchApp");
    while (true)
    {
      switch (i)
      {
      case 1:
      }
      return;
      if (str.equalsIgnoreCase(PushType.ContentUpdate.name()))
        NDPushLog.sendNDClickLog(paramBundle, 1, PushType.ContentUpdate, this);
      else if (str.equalsIgnoreCase(PushType.Download.name()))
        NDPushLog.sendNDClickLog(paramBundle, 1, PushType.Download, this);
      else if (str.equalsIgnoreCase(PushType.Novel.name()))
        NDPushLog.sendNDClickLog(paramBundle, 1, PushType.Novel, this);
      else if (str.equalsIgnoreCase(PushType.ResumeProgram.name()))
        NDPushLog.sendNDClickLog(paramBundle, 1, PushType.ResumeProgram, this);
    }
  }

  private void doCropPhoto(int paramInt)
  {
    Uri localUri;
    if (paramInt == 79)
    {
      localUri = Uri.parse("file:///sdcard/qt_danmaku_crop_capture.jpg");
      if (localUri != null)
        break label36;
    }
    while (true)
    {
      return;
      localUri = null;
      if (paramInt != 83)
        break;
      localUri = Uri.parse("file:///sdcard/qt_danmaku_crop_capture.jpg");
      break;
      try
      {
        label36: Bitmap localBitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), localUri);
        if (localBitmap != null)
        {
          HashMap localHashMap = new HashMap();
          localHashMap.put("image", localBitmap);
          localHashMap.put("uri", localUri.getPath());
          ControllerManager.getInstance().openDanmakuSendController(localHashMap);
          return;
        }
      }
      catch (IOException localIOException)
      {
      }
    }
  }

  private void doMagic()
  {
    if (SharedCfg.getInstance().isApollo())
      setDisplayEffect();
    do
    {
      int i;
      do
      {
        return;
        if (SharedCfg.getInstance().isNewUser())
        {
          SharedCfg.getInstance().setApollo(true);
          GlobalCfg.getInstance(this).setApollo(true);
          setDisplayEffect();
          return;
        }
        i = SharedCfg.getInstance().getApolloDuration();
      }
      while (i == 0);
      int j = SharedCfg.getInstance().getApolloStartTime();
      long l1 = getTodaySec() + Long.valueOf(j).longValue();
      long l2 = l1 + Integer.valueOf(i).intValue();
      long l3 = System.currentTimeMillis() / 1000L;
      if ((l1 <= l3) && (l3 < l2))
      {
        SharedCfg.getInstance().setApollo(true);
        GlobalCfg.getInstance(this).setApollo(true);
      }
    }
    while (!SharedCfg.getInstance().isApollo());
    setDisplayEffect();
  }

  private void doPhoto(int paramInt, Intent paramIntent)
  {
    if (paramIntent == null);
    Uri localUri;
    Bitmap localBitmap;
    do
    {
      return;
      Bundle localBundle = paramIntent.getExtras();
      localUri = paramIntent.getData();
      localBitmap = null;
      if (localBundle != null)
        localBitmap = (Bitmap)localBundle.get("data");
    }
    while (localBitmap != null);
    try
    {
      MediaStore.Images.Media.getBitmap(getContentResolver(), localUri);
      return;
    }
    catch (IOException localIOException)
    {
    }
  }

  private void enableGPU()
  {
    try
    {
      getWindow().setFlags(16777216, 16777216);
      return;
    }
    catch (Exception localException)
    {
    }
  }

  private int getAlarmDayOfWeek(long paramLong)
  {
    Calendar localCalendar = Calendar.getInstance();
    localCalendar.setTimeInMillis(paramLong);
    return localCalendar.get(7);
  }

  private long getTodaySec()
  {
    long l = System.currentTimeMillis();
    Calendar localCalendar = Calendar.getInstance();
    localCalendar.setTimeInMillis(l);
    int i = localCalendar.get(11);
    int j = localCalendar.get(12);
    int k = localCalendar.get(13);
    return l / 1000L - i * 3600 - j * 60 - k;
  }

  private int getVolume()
  {
    int i = this.mAudioManager.getStreamVolume(3);
    if ((FMManager.getInstance().isAvailable()) && (FMManager.getInstance().getName().equalsIgnoreCase("MiuiFM")));
    try
    {
      int j = this.mAudioManager.getStreamVolume(10);
      i = j;
      return i;
    }
    catch (Exception localException)
    {
      localException = localException;
      localException.printStackTrace();
      return i;
    }
    finally
    {
    }
    return i;
  }

  private void handleFloatIntent(int paramInt)
  {
    if (this.mainView != null)
      this.mainView.onEvent(this, "cancelPop", null);
    switch (paramInt)
    {
    default:
      return;
    case 32:
      ControllerManager.getInstance().openMyDownloadController("float");
      return;
    case 16:
      ControllerManager.getInstance().redirectToMyCollectionView();
      return;
    case 48:
      ControllerManager.getInstance().openPlayHistoryController();
      return;
    case 64:
      EventDispacthManager.getInstance().dispatchAction("showSettingView", null);
      return;
    case 80:
      this.playedLastChannel = false;
      handleListenNews();
      return;
    case 96:
      this.playedLastChannel = false;
      handleListenMusic();
      return;
    case 112:
    }
    this.playedLastChannel = false;
    handleListenWhatever();
  }

  private void handleIMMessage(IMMessage paramIMMessage)
  {
    if (!CloudCenter.getInstance().isLogin());
    while (paramIMMessage == null)
      return;
    IMAgent.getInstance().clearNotificationMsg();
    if ((paramIMMessage.isGroupMsg()) && (paramIMMessage.mFromGroupId != null))
    {
      localList2 = IMContacts.getInstance().getRecentGroupContacts();
      if (localList2 != null)
        for (j = 0; j < localList2.size(); j++)
          if (((GroupInfo)localList2.get(j)).groupId.equalsIgnoreCase(paramIMMessage.mFromGroupId))
          {
            ControllerManager.getInstance().openImChatController(localList2.get(j));
            return;
          }
      ControllerManager.getInstance().openImChatController(paramIMMessage.buildGroupInfo());
    }
    while (paramIMMessage.isGroupMsg())
    {
      List localList2;
      int j;
      ControllerManager.getInstance().openImConversationsController();
      return;
    }
    List localList1 = IMContacts.getInstance().getRecentUserContacts();
    if (localList1 != null)
      for (int i = 0; i < localList1.size(); i++)
        if (((UserInfo)localList1.get(i)).userKey.equalsIgnoreCase(paramIMMessage.mFromID))
        {
          ControllerManager.getInstance().openImChatController(localList1.get(i));
          return;
        }
    ControllerManager.getInstance().openImChatController(paramIMMessage.buildUserInfo());
  }

  private void handleListenMusic()
  {
  }

  private void handleListenNews()
  {
    ControllerManager.getInstance().redirectToPlayViewById(54, 386, 0, 0, 0, null, null);
  }

  private void handleListenWhatever()
  {
    boolean bool = InfoManager.getInstance().root().mBillboardNode.restoreChannelFromDB();
    List localList = null;
    if (bool)
      localList = InfoManager.getInstance().root().mBillboardNode.getLstBillboardChannel();
    if (localList == null)
      handleListenMusic();
    Node localNode;
    do
    {
      int i;
      do
      {
        return;
        i = localList.size();
      }
      while (i <= 0);
      localNode = (Node)localList.get((int)(10.0D * Math.random()) % i);
    }
    while (localNode == null);
    ControllerManager.getInstance().openControllerByBillboardItemNode(localNode);
  }

  private boolean handleMessageNew(Intent paramIntent)
  {
    if (paramIntent == null)
      return false;
    try
    {
      Bundle localBundle1 = paramIntent.getExtras();
      if (localBundle1 == null)
        return false;
      String str1 = localBundle1.getString("notify_type");
      if (str1 == null)
      {
        Bundle localBundle2 = localBundle1.getBundle("fm.qingting.qtradio.float_jump");
        if (localBundle2 != null)
        {
          int i = localBundle2.getInt("floatjumptype");
          if (i != 0)
            handleFloatIntent(i);
        }
      }
      else
      {
        PushMessageLog.sendPushLog(this, localBundle1, "ClickGeTuiPushMsg");
        if (str1.equalsIgnoreCase("localpush_alarm"))
        {
          ControllerManager.getInstance().openAlarmControllerListOrAdd();
          QTMSGManage.getInstance().sendStatistcsMessage("LocalPushMsg", "alarm");
          return true;
        }
        if (str1.equalsIgnoreCase("localpush_timing"))
        {
          ControllerManager.getInstance().redirectPlayViewTimer();
          QTMSGManage.getInstance().sendStatistcsMessage("LocalPushMsg", "timing");
          return true;
        }
        if (str1.equalsIgnoreCase("localpush_replay"))
        {
          redirectToReplayView();
          QTMSGManage.getInstance().sendStatistcsMessage("LocalPushMsg", "replay");
          return true;
        }
        if (str1.equalsIgnoreCase("localpush_liveroom"))
        {
          redirectToLiveRoom();
          QTMSGManage.getInstance().sendStatistcsMessage("LocalPushMsg", "liveroom");
          return true;
        }
        if (str1.equalsIgnoreCase("local_umeng_reply"))
        {
          ControllerManager.getInstance().openFeedBackController();
          QTMSGManage.getInstance().sendStatistcsMessage("LocalPushMsg", "umengreply");
          return true;
        }
        if (PushType.isPush(str1))
        {
          dealPushMsg(localBundle1);
          return true;
        }
        if (str1.equalsIgnoreCase("push_live_channel"))
          PushLiveLog.sendClickNotification(this, localBundle1.getString("live_topic"), "inApp");
        String str2;
        int j;
        int k;
        int m;
        int n;
        int i1;
        String str3;
        while (!str1.equalsIgnoreCase("im"))
        {
          str2 = localBundle1.getString("channelname");
          j = localBundle1.getInt("channelid");
          k = localBundle1.getInt("categoryid");
          m = localBundle1.getInt("programid");
          n = localBundle1.getInt("alarmType");
          i1 = localBundle1.getInt("contentType");
          localBundle1.getString("NOTIFICATION_TITLE");
          str3 = localBundle1.getString("program_name");
          if (j != 0)
            break;
          return false;
        }
        IMMessage localIMMessage = new IMMessage();
        localIMMessage.chatType = localBundle1.getInt("chatType");
        localIMMessage.mFromID = localBundle1.getString("fromUserId");
        localIMMessage.mFromName = localBundle1.getString("fromName");
        localIMMessage.mFromGroupId = localBundle1.getString("groupId");
        localIMMessage.mMessage = localBundle1.getString("msg");
        localIMMessage.publish = localBundle1.getLong("sendTime");
        localIMMessage.mGroupName = localBundle1.getString("groupName");
        localIMMessage.mFromAvatar = localBundle1.getString("fromAvatar");
        localIMMessage.mToUserId = localBundle1.getString("toUserId");
        localIMMessage.mGender = localBundle1.getString("fromGender");
        handleIMMessage(localIMMessage);
        return true;
        this.playedLastChannel = false;
        if (str1.equalsIgnoreCase("reserve"))
        {
          InfoManager.getInstance().root().setFromType(RootNode.FromType.RESERVE);
          PlayerAgent.getInstance().addPlaySource(8);
          if (n == 1)
          {
            ControllerManager.getInstance().redirectToPlayViewById(Integer.valueOf(k).intValue(), Integer.valueOf(j).intValue(), m, n, 0, null, null);
            break label1178;
          }
          ControllerManager.getInstance().redirectToPlayViewById(Integer.valueOf(k).intValue(), Integer.valueOf(j).intValue(), 0, 0, 0, null, null);
          break label1178;
        }
        if (str1.equalsIgnoreCase("alarm"))
        {
          if (this.mAudioManager.getStreamVolume(3) < 10)
            this.mAudioManager.setStreamVolume(3, 10, 3);
          PlayerAgent.getInstance().addPlaySource(12);
          InfoManager.getInstance().root().setPlayMode(RootNode.PlayMode.ALARMPLAY);
          if (m == 0)
            if (!InfoManager.getInstance().isNetworkAvailable())
            {
              InfoManager.getInstance().root().mRingToneInfoNode.setAvaliableRingId("0");
              PlayerAgent.getInstance().playRingTone(InfoManager.getInstance().root().mRingToneInfoNode.getRingNodeById("0"));
            }
          while (true)
          {
            InfoManager.getInstance().root().setFromType(RootNode.FromType.NOTIFICATION);
            InfoManager.getInstance().root().mRingToneInfoNode.setRingCatId(Integer.valueOf(k).intValue());
            InfoManager.getInstance().root().mRingToneInfoNode.setRingChannelId(Integer.valueOf(j).intValue());
            return true;
            InfoManager.getInstance().root().setFromType(RootNode.FromType.NOTIFICATION);
            ControllerManager.getInstance().openPlayViewForAlarm(k, j, m, Integer.valueOf(n).intValue());
            return true;
            InfoManager.getInstance().root().mRingToneInfoNode.setAvaliableRingId(String.valueOf(m));
            PlayerAgent.getInstance().playRingTone(InfoManager.getInstance().root().mRingToneInfoNode.getRingNodeById(String.valueOf(m)));
          }
        }
        if (str1.equalsIgnoreCase("pullmsg"))
        {
          if ((Integer.valueOf(i1).intValue() == 1) || (Integer.valueOf(i1).intValue() == 3) || (Integer.valueOf(i1).intValue() == 2))
          {
            List localList = InfoManager.getInstance().root().getPullNodes();
            if ((localList != null) && (localList.size() > 0))
            {
              Node localNode = (Node)localList.get(0);
              if ((localNode != null) && (localNode.nodeName.equalsIgnoreCase("program")))
              {
                ControllerManager.getInstance().setChannelSource(0);
                ControllerManager.getInstance().openChannelDetailController((ProgramNode)localNode, true);
              }
            }
            while (true)
            {
              InfoManager.getInstance().root().delPullNodes();
              break;
              ControllerManager.getInstance().redirectToPlayViewById(k, j, m, 1, 0, null, null);
              continue;
              ControllerManager.getInstance().redirectToPlayViewById(k, j, m, 1, 0, null, null);
            }
          }
          if (Integer.valueOf(i1).intValue() != 5)
            break label1183;
          ControllerManager.getInstance().redirectToPlayViewById(k, j, m, 0, 0, null, null);
          break label1183;
        }
        if (str1.equalsIgnoreCase("channelUpdate"))
        {
          ControllerManager.getInstance().setChannelSource(0);
          ControllerManager.getInstance().openChannelDetailController(k, j, m, 1, null, true);
          ClickNotificationLog.sendClickNotification(k, j, str2, m, str3, String.valueOf(2), String.valueOf(InfoManager.getInstance().getNetWorkType()), this);
          break label1178;
        }
        if (str1.equalsIgnoreCase("continueListen"))
        {
          ControllerManager.getInstance().setChannelSource(0);
          ControllerManager.getInstance().openChannelDetailController(k, j, m, 1, null, true);
          break label1178;
        }
        InfoManager.getInstance().root().setFromType(RootNode.FromType.NOTIFICATION);
        ControllerManager.getInstance().redirectToPlayViewById(k, j, m, 0, 0, null, null);
        break label1178;
      }
      return false;
      label1178: return true;
    }
    catch (Exception localException)
    {
      return false;
    }
    label1183: return true;
  }

  private boolean handleMessageOnCreate(Intent paramIntent)
  {
    if (paramIntent == null)
      return false;
    if ((paramIntent.getAction() != null) && ((paramIntent.getAction().equalsIgnoreCase("fm.qingting.qtradio.CAR_PLAY")) || (paramIntent.getAction().equalsIgnoreCase("fm.qingting.qtradio.CAR_PLAY_NEXT")) || (paramIntent.getAction().equalsIgnoreCase("fm.qingting.qtradio.CAR_PLAY_PRE"))))
    {
      MobclickAgent.onEvent(this, "fujia", "startActivity");
      this.mCarPlay = true;
      return true;
    }
    Bundle localBundle1 = paramIntent.getExtras();
    if (localBundle1 == null)
      return false;
    String str1 = localBundle1.getString("notify_type");
    if (str1 == null)
    {
      Bundle localBundle2 = localBundle1.getBundle("fm.qingting.qtradio.float_jump");
      if (localBundle2 != null)
      {
        int i2 = localBundle2.getInt("floatjumptype");
        if (i2 != 0)
          handleFloatIntent(i2);
      }
      return false;
    }
    if (str1.equalsIgnoreCase("shield"))
    {
      MobclickAgent.onEvent(this, "shield");
      quit();
      return false;
    }
    PushMessageLog.sendPushLog(this, localBundle1, "ClickGeTuiPushMsg");
    setIntent(null);
    if (str1.equalsIgnoreCase("localpush_alarm"))
    {
      ControllerManager.getInstance().openAlarmControllerListOrAdd();
      QTMSGManage.getInstance().sendStatistcsMessage("LocalPushMsg", "alarm");
      return true;
    }
    if (str1.equalsIgnoreCase("localpush_timing"))
    {
      ControllerManager.getInstance().redirectPlayViewTimer();
      QTMSGManage.getInstance().sendStatistcsMessage("LocalPushMsg", "timing");
      return true;
    }
    if (str1.equalsIgnoreCase("localpush_replay"))
    {
      redirectToReplayView();
      QTMSGManage.getInstance().sendStatistcsMessage("LocalPushMsg", "replay");
      return true;
    }
    if (str1.equalsIgnoreCase("localpush_liveroom"))
    {
      redirectToLiveRoom();
      QTMSGManage.getInstance().sendStatistcsMessage("LocalPushMsg", "liveroom");
      return true;
    }
    if (str1.equalsIgnoreCase("local_umeng_reply"))
    {
      ControllerManager.getInstance().openFeedBackController();
      QTMSGManage.getInstance().sendStatistcsMessage("LocalPushMsg", "umengreply");
      return true;
    }
    if (PushType.isPush(str1))
    {
      dealPushMsg(localBundle1);
      return true;
    }
    if (str1.equalsIgnoreCase("push_live_channel"))
      PushLiveLog.sendClickNotification(this, localBundle1.getString("live_topic"), "launchApp");
    String str2;
    String str3;
    int i;
    int j;
    int k;
    int m;
    int n;
    do
    {
      str2 = localBundle1.getString("program_name");
      str3 = localBundle1.getString("channelname");
      i = localBundle1.getInt("channelid");
      j = localBundle1.getInt("categoryid");
      k = localBundle1.getInt("programid");
      m = localBundle1.getInt("alarmType");
      n = localBundle1.getInt("contentType");
      localBundle1.getString("NOTIFICATION_TITLE");
      if (i != 0)
        break;
      return false;
      if (str1.equalsIgnoreCase("push_activity"))
      {
        ActivityNode localActivityNode = new ActivityNode();
        localActivityNode.contentUrl = localBundle1.getString("ACTIVITY_CONTENTURL");
        localActivityNode.titleIconUrl = localBundle1.getString("ACTIVITY_TITLEICON");
        localActivityNode.infoUrl = localBundle1.getString("ACTIVITY_INFOURL");
        ControllerManager.getInstance().redirectToActivityViewByNode(localActivityNode);
        return true;
      }
    }
    while (!str1.equalsIgnoreCase("im"));
    this.playedLastChannel = false;
    IMMessage localIMMessage = new IMMessage();
    localIMMessage.chatType = localBundle1.getInt("chatType");
    localIMMessage.mFromID = localBundle1.getString("fromUserId");
    localIMMessage.mFromName = localBundle1.getString("fromName");
    localIMMessage.mFromGroupId = localBundle1.getString("groupId");
    localIMMessage.mMessage = localBundle1.getString("msg");
    localIMMessage.publish = localBundle1.getLong("sendTime");
    localIMMessage.mGroupName = localBundle1.getString("groupName");
    localIMMessage.mFromAvatar = localBundle1.getString("fromAvatar");
    localIMMessage.mToUserId = localBundle1.getString("toUserId");
    localIMMessage.mGender = localBundle1.getString("fromGender");
    handleIMMessage(localIMMessage);
    return true;
    this.playedLastChannel = false;
    if (str1.equalsIgnoreCase("reserve"))
    {
      InfoManager.getInstance().root().setFromType(RootNode.FromType.RESERVE);
      PlayerAgent.getInstance().addPlaySource(8);
      if (m == 1)
        ControllerManager.getInstance().redirectToPlayViewById(Integer.valueOf(j).intValue(), Integer.valueOf(i).intValue(), k, m, 0, null, null);
      while (true)
      {
        return true;
        ControllerManager.getInstance().redirectToPlayViewById(Integer.valueOf(j).intValue(), Integer.valueOf(i).intValue(), 0, 0, 0, null, null);
      }
    }
    if (str1.equalsIgnoreCase("alarm"))
    {
      if (isAlarmFailed())
      {
        this.playedLastChannel = true;
        return false;
      }
      PlayerAgent.getInstance().addPlaySource(12);
      String str4;
      if (!InfoManager.getInstance().isNetworkAvailable())
      {
        str4 = "" + "offline_";
        int i1 = Calendar.getInstance().get(11);
        String str5 = str4 + String.valueOf(i1);
        QTMSGManage.getInstance().sendStatistcsMessage("StartActivityByClock", str5);
        if (str3 != null)
          QTMSGManage.getInstance().sendStatistcsMessage("ClockChannel", str3);
        if (this.mAudioManager.getStreamVolume(3) < 7)
          this.mAudioManager.setStreamVolume(3, 10, 3);
        InfoManager.getInstance().root().setPlayMode(RootNode.PlayMode.ALARMPLAY);
        if (k != 0)
          break label1154;
        InfoManager.getInstance().root().mRingToneInfoNode.setAvaliableRingId("0");
        RingToneNode localRingToneNode2 = InfoManager.getInstance().root().mRingToneInfoNode.getRingNodeById("0");
        if (localRingToneNode2 != null)
        {
          SystemPlayer.getInstance().setSource(localRingToneNode2.getListenOnLineUrl());
          SystemPlayer.getInstance().play();
        }
        ControllerManager.getInstance().openPlayViewForAlarm(Integer.valueOf(j).intValue(), Integer.valueOf(i).intValue(), k, Integer.valueOf(m).intValue());
      }
      while (true)
      {
        InfoManager.getInstance().root().setFromType(RootNode.FromType.NOTIFICATION);
        InfoManager.getInstance().root().mRingToneInfoNode.setRingCatId(Integer.valueOf(j).intValue());
        InfoManager.getInstance().root().mRingToneInfoNode.setRingProgramId(k);
        InfoManager.getInstance().root().mRingToneInfoNode.setRingChannelId(Integer.valueOf(i).intValue());
        InfoManager.getInstance().root().mRingToneInfoNode.setRingChannelType(Integer.valueOf(m).intValue());
        return true;
        if (MobileState.getNetWorkType(this) == 1)
        {
          str4 = "" + "wifi_";
          break;
        }
        str4 = "" + "mobile_";
        break;
        label1154: InfoManager.getInstance().root().mRingToneInfoNode.setRingProgramId(k);
        RingToneNode localRingToneNode1 = InfoManager.getInstance().root().mRingToneInfoNode.getRingNodeById("0");
        if (localRingToneNode1 != null)
        {
          SystemPlayer.getInstance().setSource(localRingToneNode1.getListenOnLineUrl());
          SystemPlayer.getInstance().play();
        }
        ControllerManager.getInstance().openPlayViewForAlarm(Integer.valueOf(j).intValue(), Integer.valueOf(i).intValue(), k, Integer.valueOf(m).intValue());
      }
    }
    if (str1.equalsIgnoreCase("localrecommend"))
    {
      if (str3 != null)
        MobclickAgent.onEvent(this, "StartActivityByLocalRecommend2", str3);
      InfoManager.getInstance().root().setFromType(RootNode.FromType.NOTIFICATION);
      ControllerManager.getInstance().redirectToPlayViewById(Integer.valueOf(j).intValue(), Integer.valueOf(i).intValue(), 0, 0, 0, null, null);
      return true;
    }
    if (str1.equalsIgnoreCase("pullmsg"))
    {
      if ((Integer.valueOf(n).intValue() == 1) || (Integer.valueOf(n).intValue() == 3) || (Integer.valueOf(n).intValue() == 2))
      {
        List localList = InfoManager.getInstance().root().getPullNodes();
        if ((localList != null) && (localList.size() > 0))
        {
          Node localNode = (Node)localList.get(0);
          if ((localNode != null) && (localNode.nodeName.equalsIgnoreCase("program")))
          {
            ControllerManager.getInstance().setChannelSource(0);
            ControllerManager.getInstance().openChannelDetailController((ProgramNode)localNode, true);
            InfoManager.getInstance().root().delPullNodes();
          }
        }
      }
      while (true)
      {
        return true;
        ControllerManager.getInstance().redirectToPlayViewById(Integer.valueOf(j).intValue(), Integer.valueOf(i).intValue(), Integer.valueOf(k).intValue(), 1, 0, null, null);
        break;
        ControllerManager.getInstance().redirectToPlayViewById(Integer.valueOf(j).intValue(), Integer.valueOf(i).intValue(), Integer.valueOf(k).intValue(), 1, 0, null, null);
        continue;
        if (Integer.valueOf(n).intValue() == 5)
          ControllerManager.getInstance().redirectToPlayViewById(Integer.valueOf(j).intValue(), Integer.valueOf(i).intValue(), Integer.valueOf(k).intValue(), 0, 0, null, null);
      }
    }
    if (str1.equalsIgnoreCase("channelUpdate"))
    {
      ControllerManager.getInstance().setChannelSource(0);
      ControllerManager.getInstance().openChannelDetailController(j, i, k, 1, null, true);
      ClickNotificationLog.sendClickNotification(j, i, str3, k, str2, String.valueOf(1), String.valueOf(InfoManager.getInstance().getNetWorkType()), this);
    }
    while (true)
    {
      InfoManager.getInstance().root().setFromType(RootNode.FromType.NOTIFICATION);
      ControllerManager.getInstance().redirectToPlayViewById(Integer.valueOf(j).intValue(), Integer.valueOf(i).intValue(), Integer.valueOf(k).intValue(), Integer.valueOf(m).intValue(), 0, null, null);
      break;
      if (str1.equalsIgnoreCase("continueListen"))
      {
        ControllerManager.getInstance().setChannelSource(0);
        ControllerManager.getInstance().openChannelDetailController(j, i, k, 1, null, true);
      }
    }
  }

  private void initAlertView()
  {
  }

  private void initClientID()
  {
    loadClientID();
  }

  private void initConfig()
  {
    InfoManager.getInstance().setContext(this.mContext);
    String str = DeviceInfo.getUniqueId(this.mContext);
    InfoManager.getInstance().setDeviceId(str);
    enableGPU();
    SharedCfg.getInstance().init(this);
    GlobalCfg.getInstance(this).setUseCache(true);
    DBManager.getInstance().init(this);
    NetWorkManage.getInstance().init(this);
    NetWorkManage.getInstance().register();
    this.tracker = GoogleAnalyticsTracker.getInstance();
    this.tracker.startNewSession("UA-30488419-1", 600, this);
    MobclickAgent.updateOnlineConfig(this);
    MobclickAgent.setSessionContinueMillis(300000L);
    if (SharedCfg.getInstance().isNewUser())
      SharedCfg.getInstance().setBootstrapMax(10);
    doMagic();
    TCAgent.init(this);
    TCAgent.LOG_ON = false;
    QTMSGManage.getInstance().initContext(this);
    SystemInfo.getInstance().init(this);
    float f = SystemInfo.getInstance().getMaxCpuFreq() / 1000.0F;
    if (f < 600.0F)
      getWindow().setFormat(4);
    while (true)
    {
      ScreenType.setScreenInfo(this.mContext);
      SkinManager.getInstance().setConfig();
      mobAgentOption.getInstance().init(this);
      return;
      if (f < 900.0F)
        getWindow().setFormat(4);
      else
        getWindow().setFormat(1);
    }
  }

  private void initDataSources()
  {
    DataManager.getInstance().addDataSource(NetDS.getInstance());
    NetDS.getInstance().addParser(new NetParser());
    DataManager.getInstance().addDataSource(MediaCenterDS.getInstance());
    DataManager.getInstance().addDataSource(AttributesDS.getInstance());
    DataManager.getInstance().addDataSource(NotifyDS.getInstance());
    DataManager.getInstance().addDataSource(ProfileDS.getInstance());
    DataManager.getInstance().addDataSourceProxy(ApiSign.getInstance());
    DataManager.getInstance().addDataSource(SearchDS.getInstance());
    DataManager.getInstance().addDataSource(FavouriteChannelDS.getInstance());
    DataManager.getInstance().addDataSource(CategoryNodeDS.getInstance());
    DataManager.getInstance().addDataSource(PlayHistoryDS.getInstance());
    DataManager.getInstance().addDataSource(ChannelNodesDS.getInstance());
    DataManager.getInstance().addDataSource(RadioNodesDS.getInstance());
    DataManager.getInstance().addDataSource(AlarmDS.getInstance());
    DataManager.getInstance().addDataSource(CommonDS.getInstance());
    DataManager.getInstance().addDataSource(SocialUserProfileDS.getInstance());
    DataManager.getInstance().addDataSource(UserInfoDS.getInstance());
    DataManager.getInstance().addDataSource(ProgramNodesDS.getInstance());
    DataManager.getInstance().addDataSource(PlayedMetaDS.getInstance());
    DataManager.getInstance().addDataSource(PlayListDS.getInstance());
    DataManager.getInstance().addDataSource(BillboardNodeDS.getInstance());
    DataManager.getInstance().addDataSource(DownloadingProgramDS.getInstance());
    DataManager.getInstance().addDataSource(PreDownloadingProgramDS.getInstance());
    DataManager.getInstance().addDataSource(AllFavCategoryDS.getInstance());
    DataManager.getInstance().addDataSource(RecommendCategoryNodeDS.getInstance());
    DataManager.getInstance().addDataSource(FreqChannelsDS.getInstance());
    DataManager.getInstance().addDataSource(ReserveProgramDS.getInstance());
    DataManager.getInstance().addDataSource(PullNodeDS.getInstance());
    DataManager.getInstance().addDataSource(IMContactsDS.getInstance());
    DataManager.getInstance().addDataSource(IMUserInfoDS.getInstance());
    DataManager.getInstance().addDataSource(IMDatabaseDS.getInstance());
    DataManager.getInstance().addDataSource(PodcasterDS.getInstance());
    DataManager.getInstance().addDataSource(MyPodcasterDS.getInstance());
  }

  private void initIRE()
  {
    try
    {
      change(Build.DISPLAY);
      IRMonitor.getInstance(this).Init("833c6d6eb8031de1", "qingtingFM_android", false);
      return;
    }
    catch (Exception localException)
    {
    }
  }

  private void initOthers()
  {
    WeiboAgent.getInstance().setContext(this);
    WeiboAgent.getInstance().init();
    WeiboChat.getInstance().init();
    TencentAgent.getInstance().init(this, "100387802");
    SharedCfg.getInstance().setVertion(this);
    SharedCfg.getInstance().setAppStartCount();
    SharedCfg.getInstance().setAppLocalCount();
    StatisticsFMManage.getInstance(this).setVertion(this);
    SharedCfg.getInstance().setlocalNotice("yes");
    SharedCfg.getInstance().setFMPlayIndex("");
    if (!InfoManager.getInstance().enableGenerateDB())
      OfflineManager.loadOfflineData(this);
    RingManager.loadRings(this);
  }

  // ERROR //
  private void initServiceAndView()
  {
    // Byte code:
    //   0: aload_0
    //   1: invokespecial 2314	fm/qingting/qtradio/QTRadioActivity:registerServiceCommandReceiver	()V
    //   4: ldc2_w 2315
    //   7: lstore_1
    //   8: aload_0
    //   9: new 836	android/content/Intent
    //   12: dup
    //   13: aload_0
    //   14: ldc_w 2318
    //   17: invokespecial 2321	android/content/Intent:<init>	(Landroid/content/Context;Ljava/lang/Class;)V
    //   20: invokevirtual 2325	fm/qingting/qtradio/QTRadioActivity:startService	(Landroid/content/Intent;)Landroid/content/ComponentName;
    //   23: pop
    //   24: lload_1
    //   25: lconst_1
    //   26: lsub
    //   27: lstore_1
    //   28: invokestatic 1789	fm/qingting/qtradio/fm/PlayerAgent:getInstance	()Lfm/qingting/qtradio/fm/PlayerAgent;
    //   31: aload_0
    //   32: invokevirtual 2326	fm/qingting/qtradio/fm/PlayerAgent:init	(Landroid/content/Context;)V
    //   35: invokestatic 1789	fm/qingting/qtradio/fm/PlayerAgent:getInstance	()Lfm/qingting/qtradio/fm/PlayerAgent;
    //   38: aload_0
    //   39: invokevirtual 2327	fm/qingting/qtradio/fm/PlayerAgent:setEventHandler	(Lfm/qingting/framework/event/IEventHandler;)V
    //   42: lload_1
    //   43: lconst_1
    //   44: lsub
    //   45: lstore 5
    //   47: lload 5
    //   49: lconst_1
    //   50: lsub
    //   51: lstore_1
    //   52: lload_1
    //   53: lconst_0
    //   54: lcmp
    //   55: ifgt -47 -> 8
    //   58: lload_1
    //   59: lconst_0
    //   60: lcmp
    //   61: ifne +7 -> 68
    //   64: aload_0
    //   65: invokespecial 2329	fm/qingting/qtradio/QTRadioActivity:ShowDialogReportToUser	()V
    //   68: invokestatic 2334	fm/qingting/download/QTRadioDownloadAgent:getInstance	()Lfm/qingting/download/QTRadioDownloadAgent;
    //   71: aload_0
    //   72: invokevirtual 2337	fm/qingting/download/QTRadioDownloadAgent:initDownload	(Landroid/content/Context;)V
    //   75: invokestatic 2334	fm/qingting/download/QTRadioDownloadAgent:getInstance	()Lfm/qingting/download/QTRadioDownloadAgent;
    //   78: invokevirtual 2339	fm/qingting/download/QTRadioDownloadAgent:startService	()V
    //   81: invokestatic 1558	fm/qingting/qtradio/im/IMAgent:getInstance	()Lfm/qingting/qtradio/im/IMAgent;
    //   84: aload_0
    //   85: invokevirtual 2342	fm/qingting/qtradio/im/IMAgent:initService	(Landroid/content/Context;)V
    //   88: aload_0
    //   89: aload_0
    //   90: ldc_w 2344
    //   93: invokevirtual 984	fm/qingting/qtradio/QTRadioActivity:getSystemService	(Ljava/lang/String;)Ljava/lang/Object;
    //   96: checkcast 1487	android/media/AudioManager
    //   99: putfield 1485	fm/qingting/qtradio/QTRadioActivity:mAudioManager	Landroid/media/AudioManager;
    //   102: aload_0
    //   103: iconst_3
    //   104: invokevirtual 2347	fm/qingting/qtradio/QTRadioActivity:setVolumeControlStream	(I)V
    //   107: aload_0
    //   108: invokespecial 2350	fm/qingting/qtradio/QTRadioActivity:registerMediaButton	()V
    //   111: aload_0
    //   112: invokestatic 2355	fm/qingting/qtradio/headset/HeadSet:getInstance	(Landroid/content/Context;)Lfm/qingting/qtradio/headset/HeadSet;
    //   115: aload_0
    //   116: invokevirtual 2358	fm/qingting/qtradio/headset/HeadSet:registerReceiver	(Landroid/content/Context;)V
    //   119: invokestatic 2363	fm/qingting/qtradio/remotecontrol/RemoteControl:getInstance	()Lfm/qingting/qtradio/remotecontrol/RemoteControl;
    //   122: aload_0
    //   123: invokevirtual 2366	fm/qingting/qtradio/remotecontrol/RemoteControl:registerRemoteControl	(Landroid/content/Context;)V
    //   126: aload_0
    //   127: invokespecial 2368	fm/qingting/qtradio/QTRadioActivity:acquireUmengConfig	()V
    //   130: aload_0
    //   131: new 1508	fm/qingting/qtradio/view/MainView
    //   134: dup
    //   135: aload_0
    //   136: invokespecial 2369	fm/qingting/qtradio/view/MainView:<init>	(Landroid/content/Context;)V
    //   139: putfield 252	fm/qingting/qtradio/QTRadioActivity:mainView	Lfm/qingting/qtradio/view/MainView;
    //   142: aload_0
    //   143: getfield 252	fm/qingting/qtradio/QTRadioActivity:mainView	Lfm/qingting/qtradio/view/MainView;
    //   146: aload_0
    //   147: invokevirtual 2370	fm/qingting/qtradio/view/MainView:setEventHandler	(Lfm/qingting/framework/event/IEventHandler;)V
    //   150: aload_0
    //   151: getfield 2000	fm/qingting/qtradio/QTRadioActivity:tracker	Lcom/google/android/apps/analytics/GoogleAnalyticsTracker;
    //   154: ldc_w 2372
    //   157: invokevirtual 2375	com/google/android/apps/analytics/GoogleAnalyticsTracker:trackPageView	(Ljava/lang/String;)V
    //   160: aload_0
    //   161: invokespecial 2377	fm/qingting/qtradio/QTRadioActivity:initClientID	()V
    //   164: invokestatic 1495	fm/qingting/qtradio/fmdriver/FMManager:getInstance	()Lfm/qingting/qtradio/fmdriver/FMManager;
    //   167: invokevirtual 1498	fm/qingting/qtradio/fmdriver/FMManager:isAvailable	()Z
    //   170: ifeq +207 -> 377
    //   173: invokestatic 1105	fm/qingting/qtradio/logger/QTLogger:getInstance	()Lfm/qingting/qtradio/logger/QTLogger;
    //   176: ldc_w 414
    //   179: invokevirtual 2380	fm/qingting/qtradio/logger/QTLogger:setFMAvailable	(Ljava/lang/String;)V
    //   182: aload_0
    //   183: invokespecial 2383	fm/qingting/qtradio/QTRadioActivity:setWifiPolicy	()V
    //   186: invokestatic 298	fm/qingting/qtradio/model/InfoManager:getInstance	()Lfm/qingting/qtradio/model/InfoManager;
    //   189: invokevirtual 2386	fm/qingting/qtradio/model/InfoManager:startLocate	()V
    //   192: invokestatic 298	fm/qingting/qtradio/model/InfoManager:getInstance	()Lfm/qingting/qtradio/model/InfoManager;
    //   195: invokevirtual 2389	fm/qingting/qtradio/model/InfoManager:loadADLoc	()V
    //   198: aload_0
    //   199: invokespecial 2391	fm/qingting/qtradio/QTRadioActivity:cancelSystemAlarm	()V
    //   202: aload_0
    //   203: invokestatic 382	fm/qingting/qtradio/model/GlobalCfg:getInstance	(Landroid/content/Context;)Lfm/qingting/qtradio/model/GlobalCfg;
    //   206: invokestatic 1475	java/util/Calendar:getInstance	()Ljava/util/Calendar;
    //   209: bipush 6
    //   211: invokevirtual 1482	java/util/Calendar:get	(I)I
    //   214: invokevirtual 2394	fm/qingting/qtradio/model/GlobalCfg:setOpenDay	(I)V
    //   217: aload_0
    //   218: invokestatic 298	fm/qingting/qtradio/model/InfoManager:getInstance	()Lfm/qingting/qtradio/model/InfoManager;
    //   221: invokevirtual 1141	fm/qingting/qtradio/model/InfoManager:getPushSwitch	()Z
    //   224: putfield 115	fm/qingting/qtradio/QTRadioActivity:mNotifySwitchState	Z
    //   227: aload_0
    //   228: getfield 126	fm/qingting/qtradio/QTRadioActivity:wakeHandler	Landroid/os/Handler;
    //   231: astore 8
    //   233: aload_0
    //   234: getfield 133	fm/qingting/qtradio/QTRadioActivity:timingWake	Ljava/lang/Runnable;
    //   237: astore 9
    //   239: getstatic 2399	fm/qingting/utils/LifeTime:isFirstLaunchAfterInstall	Z
    //   242: ifeq +147 -> 389
    //   245: ldc2_w 2400
    //   248: lstore 11
    //   250: aload 8
    //   252: aload 9
    //   254: lload 11
    //   256: invokevirtual 2405	android/os/Handler:postDelayed	(Ljava/lang/Runnable;J)Z
    //   259: pop
    //   260: return
    //   261: astore 15
    //   263: aload_0
    //   264: ldc_w 2407
    //   267: aload_0
    //   268: invokestatic 1203	fm/qingting/utils/DeviceInfo:getUniqueId	(Landroid/content/Context;)Ljava/lang/String;
    //   271: invokestatic 1887	com/umeng/analytics/MobclickAgent:onEvent	(Landroid/content/Context;Ljava/lang/String;Ljava/lang/String;)V
    //   274: aload 15
    //   276: invokevirtual 2408	java/lang/SecurityException:printStackTrace	()V
    //   279: aload_0
    //   280: ldc_w 2407
    //   283: invokestatic 2411	com/umeng/analytics/MobclickAgent:reportError	(Landroid/content/Context;Ljava/lang/String;)V
    //   286: goto -258 -> 28
    //   289: astore_3
    //   290: aload_0
    //   291: ldc_w 2407
    //   294: aload_0
    //   295: invokestatic 1203	fm/qingting/utils/DeviceInfo:getUniqueId	(Landroid/content/Context;)Ljava/lang/String;
    //   298: invokestatic 1887	com/umeng/analytics/MobclickAgent:onEvent	(Landroid/content/Context;Ljava/lang/String;Ljava/lang/String;)V
    //   301: aload_3
    //   302: invokevirtual 1044	java/lang/Exception:printStackTrace	()V
    //   305: aload_0
    //   306: ldc_w 2407
    //   309: invokestatic 2411	com/umeng/analytics/MobclickAgent:reportError	(Landroid/content/Context;Ljava/lang/String;)V
    //   312: goto -284 -> 28
    //   315: astore 14
    //   317: aload_0
    //   318: ldc_w 2413
    //   321: aload_0
    //   322: invokestatic 1203	fm/qingting/utils/DeviceInfo:getUniqueId	(Landroid/content/Context;)Ljava/lang/String;
    //   325: invokestatic 1887	com/umeng/analytics/MobclickAgent:onEvent	(Landroid/content/Context;Ljava/lang/String;Ljava/lang/String;)V
    //   328: aload 14
    //   330: invokevirtual 2408	java/lang/SecurityException:printStackTrace	()V
    //   333: aload_0
    //   334: ldc_w 2413
    //   337: invokestatic 2411	com/umeng/analytics/MobclickAgent:reportError	(Landroid/content/Context;Ljava/lang/String;)V
    //   340: lload_1
    //   341: lstore 5
    //   343: goto -296 -> 47
    //   346: astore 4
    //   348: aload_0
    //   349: ldc_w 2413
    //   352: aload_0
    //   353: invokestatic 1203	fm/qingting/utils/DeviceInfo:getUniqueId	(Landroid/content/Context;)Ljava/lang/String;
    //   356: invokestatic 1887	com/umeng/analytics/MobclickAgent:onEvent	(Landroid/content/Context;Ljava/lang/String;Ljava/lang/String;)V
    //   359: aload 4
    //   361: invokevirtual 1044	java/lang/Exception:printStackTrace	()V
    //   364: aload_0
    //   365: ldc_w 2413
    //   368: invokestatic 2411	com/umeng/analytics/MobclickAgent:reportError	(Landroid/content/Context;Ljava/lang/String;)V
    //   371: lload_1
    //   372: lstore 5
    //   374: goto -327 -> 47
    //   377: invokestatic 1105	fm/qingting/qtradio/logger/QTLogger:getInstance	()Lfm/qingting/qtradio/logger/QTLogger;
    //   380: ldc_w 636
    //   383: invokevirtual 2380	fm/qingting/qtradio/logger/QTLogger:setFMAvailable	(Ljava/lang/String;)V
    //   386: goto -204 -> 182
    //   389: aload_0
    //   390: getfield 108	fm/qingting/qtradio/QTRadioActivity:mShowAdvertisement	Z
    //   393: ifeq +16 -> 409
    //   396: sipush 2500
    //   399: istore 10
    //   401: iload 10
    //   403: i2l
    //   404: lstore 11
    //   406: goto -156 -> 250
    //   409: sipush 1500
    //   412: istore 10
    //   414: goto -13 -> 401
    //   417: astore 7
    //   419: goto -331 -> 88
    //
    // Exception table:
    //   from	to	target	type
    //   8	24	261	java/lang/SecurityException
    //   8	24	289	java/lang/Exception
    //   28	42	315	java/lang/SecurityException
    //   28	42	346	java/lang/Exception
    //   68	88	417	java/lang/Exception
  }

  private boolean isAlarmFailed()
  {
    return (!GlobalCfg.getInstance(this.mContext).getAlarmShouted()) && (GlobalCfg.getInstance(this.mContext).getAlarmAbsoluteTime() < System.currentTimeMillis());
  }

  private boolean isinitAlertView()
  {
    return true;
  }

  private void loadClientID()
  {
    String str = DeviceInfo.getUniqueId(this);
    HashMap localHashMap = new HashMap();
    localHashMap.put("deviceid", str);
    DataManager.getInstance().getData("bootstrap", this, localHashMap);
  }

  private void loadSystemInfo()
  {
    String str = CPUInfo.CPUArch();
    int i = CPUInfo.FPU();
    int j = CPUInfo.CPUMaxFreq();
    HashMap localHashMap1 = new HashMap();
    localHashMap1.put("arch", str);
    localHashMap1.put("fpu", String.valueOf(i));
    if (j <= 820000)
    {
      localHashMap1.put("freq", "below 820MHz");
      switch (str.charAt(0))
      {
      default:
        MobclickAgent.onEvent(this, "ArchUnknown", localHashMap1);
      case '5':
      case '6':
      case '7':
      }
    }
    while (true)
    {
      HashMap localHashMap2 = new HashMap();
      Display localDisplay = getWindowManager().getDefaultDisplay();
      localHashMap2.put("height_x_width", String.valueOf(localDisplay.getHeight()) + " x " + String.valueOf(localDisplay.getWidth()));
      DisplayMetrics localDisplayMetrics = new DisplayMetrics();
      localDisplay.getMetrics(localDisplayMetrics);
      localHashMap2.put("diagonal_size", String.valueOf(Math.sqrt(Math.pow(localDisplayMetrics.widthPixels / localDisplayMetrics.xdpi, 2.0D) + Math.pow(localDisplayMetrics.heightPixels / localDisplayMetrics.ydpi, 2.0D))));
      localHashMap2.put("ydpi_x_xdpi", String.valueOf(localDisplayMetrics.ydpi) + " x " + String.valueOf(localDisplayMetrics.xdpi));
      MobclickAgent.onEvent(this, "DisplayInfo", localHashMap2);
      return;
      if (j <= 1020000)
      {
        localHashMap1.put("freq", "between 820MHz and 1.02GHz");
        break;
      }
      if (j > 1020000)
      {
        localHashMap1.put("freq", "above 1.02GHz");
        break;
      }
      localHashMap1.put("freq", "unknown");
      break;
      MobclickAgent.onEvent(this, "ArchARMv5", localHashMap1);
      continue;
      MobclickAgent.onEvent(this, "ArchARMv6", localHashMap1);
      continue;
      MobclickAgent.onEvent(this, "ArchARMv7", localHashMap1);
    }
  }

  private void playAtBack()
  {
    this.mainView.update("cancelBubble", null);
    moveTaskToBack(true);
  }

  private void playLastChannel()
  {
    int i = 1;
    boolean bool = InfoManager.getInstance().getAutoPlayAfterStart();
    int i1;
    int n;
    int i2;
    int m;
    if ((this.mCarPlay) || (bool))
    {
      int j = i;
      if (this.mCarPlay)
        this.playedLastChannel = i;
      if (this.playedLastChannel)
      {
        String str1 = SharedCfg.getInstance().getLastPlayInfo();
        if (str1 == null)
          break label731;
        String[] arrayOfString = str1.split("_");
        if (arrayOfString == null)
          break label731;
        i1 = Integer.valueOf(arrayOfString[0]).intValue();
        n = Integer.valueOf(arrayOfString[i]).intValue();
        i2 = Integer.valueOf(arrayOfString[2]).intValue();
        m = Integer.valueOf(arrayOfString[3]).intValue();
      }
    }
    while (true)
    {
      if (n == 0)
      {
        if (InfoManager.getInstance().getDefaultCollectionChannelId() == 0)
          break label717;
        ChannelNode localChannelNode2 = ChannelHelper.getInstance().getChannel(InfoManager.getInstance().getDefaultCollectionChannelId(), i);
        if (localChannelNode2 == null)
          break label717;
        int i8 = localChannelNode2.channelId;
        i1 = localChannelNode2.categoryId;
        n = i8;
      }
      for (m = i; ; m = 0)
      {
        String str2 = PlayerAgent.getInstance().getSource();
        int i3;
        int i4;
        if ((str2 != null) && (!str2.equalsIgnoreCase("")))
        {
          i3 = PlayerAgent.getInstance().queryPosition();
          PlayerAgent.getInstance().queryIsLiveStream();
          if (i3 > 5)
          {
            PlayerAgent.getInstance().recoverSource(str2);
            PlayerAgent.getInstance().recoverRecvPlay(i);
            PlayerAgent.getInstance().setCurrPlayState(4096);
            PlayerAgent.getInstance().dispatchPlayStateInFM(4096);
          }
          i2 = PlayerAgent.getInstance().getProgramPlayInfo(4);
          i4 = i;
        }
        while (true)
        {
          label262: if (i4 != 0)
            if (this.mainView != null)
              this.mainView.update("removeShare", null);
          while (true)
          {
            ChannelNode localChannelNode1;
            if (i1 == DownLoadInfoNode.mDownloadId)
            {
              localChannelNode1 = InfoManager.getInstance().root().mDownLoadInfoNode.getChannelNode(Integer.valueOf(n).intValue());
              label325: if (localChannelNode1 == null)
                localChannelNode1 = ChannelHelper.getInstance().getChannel(386, 0);
              if (localChannelNode1 != null)
              {
                InfoManager.getInstance().root().setPlayingChannelNode(localChannelNode1);
                if (localChannelNode1.channelType != 0)
                  break label453;
                InfoManager.getInstance().root().setWillPlayNode(localChannelNode1);
                label377: if (i4 != 0)
                  break label653;
                if (i != 0)
                  PlayerAgent.getInstance().play(InfoManager.getInstance().root().getCurrentPlayingNode());
                PlayerAgent.getInstance().addPlaySource(13);
              }
            }
            int k;
            label453: label614: int i6;
            label653: 
            do
            {
              do
              {
                return;
                k = 0;
                break;
                if (m != 0)
                  break label708;
                i2 = 0;
                i3 = 0;
                i5 = 0;
                break label262;
                localChannelNode1 = ChannelHelper.getInstance().getChannel(Integer.valueOf(n).intValue(), m);
                break label325;
                Node localNode = InfoManager.getInstance().root().mPersonalCenterNode.playHistoryNode.getPlayNode(Integer.valueOf(n).intValue(), i2);
                if (localNode != null)
                {
                  InfoManager.getInstance().root().setWillPlayNode(localNode);
                  break label377;
                }
                List localList = PlayListManager.getInstance().getPlayList();
                if ((localList != null) && (localList.size() > 0))
                {
                  if (i2 != 0)
                  {
                    for (int i7 = 0; ; i7++)
                    {
                      if (i7 >= localList.size())
                        break label614;
                      if ((((Node)localList.get(i7)).nodeName.equalsIgnoreCase("program")) && (((ProgramNode)localList.get(i7)).id == i2))
                      {
                        InfoManager.getInstance().root().setWillPlayNode((Node)localList.get(i7));
                        break;
                      }
                    }
                    break label377;
                  }
                  InfoManager.getInstance().root().setWillPlayNode((Node)localList.get(0));
                  break label377;
                }
                InfoManager.getInstance().root().setWillPlayNode(localChannelNode1);
                break label377;
                PlayerAgent.getInstance().addPlaySource(0);
              }
              while (i3 <= 5);
              i6 = PlayerAgent.getInstance().queryDuration();
            }
            while (i6 <= 0);
            PlayerAgent.getInstance().setLiveStream(false);
            PlayProcessSyncUtil.getInstance().setTotalLength(i6);
            PlayProcessSyncUtil.getInstance().setCurrentPlayTime(i3);
            return;
            i = k;
          }
          label708: i3 = 0;
          int i5 = 0;
        }
        label717: n = 386;
        i1 = 5;
      }
      label731: m = 0;
      n = 0;
      i1 = 0;
      i2 = 0;
    }
  }

  private void quit()
  {
    SharedCfg.getInstance().setNewUser(false);
    if ((this.mNotifySwitchState) && (!InfoManager.getInstance().getPushSwitch()))
      QTMSGManage.getInstance().sendStatistcsMessage("turnOffNotify");
    if (this.mShowAdvertisement)
    {
      AdvertisementItemNode localAdvertisementItemNode = InfoManager.getInstance().root().mAdvertisementInfoNode.getSplashAdvertisement();
      if (localAdvertisementItemNode != null)
      {
        localAdvertisementItemNode.getImage();
        localAdvertisementItemNode.onShow();
      }
    }
    InfoManager.getInstance().clearAllChannelUpdates();
    addShortCut(this, "fm.qingting.qtradio");
    RecommendStatisticsUtil.INSTANCE.sendLog();
    setSystemAlarm();
    checkHasReserveTask();
    try
    {
      stopService(new Intent(this, QTRadioService.class));
      stopService(new Intent(this, NotificationService.class));
      if ((!InfoManager.getInstance().root().mShareInfoNode.hasUpdate()) && (InfoManager.getInstance().hasWifi()))
        SharedCfg.getInstance().setRecommendShareUpdate(false);
      WebViewPlayer.getInstance().release();
      IMAgent.getInstance().storeUnReadMsgCnt();
    }
    catch (Exception localException4)
    {
      try
      {
        while (true)
        {
          stopService(new Intent(this, QTRadioAdvertService.class));
          label189: stopQuitTimer();
          this.playedLastChannel = true;
          try
          {
            InfoManager.getInstance().root().mPersonalCenterNode.alarmInfoNode.WriteToDB();
            InfoManager.getInstance().root().mPersonalCenterNode.reserveNode.WriteToDB();
            InfoManager.getInstance().startNotificationInQuit();
            InfoManager.getInstance().sendAvailAlarmCnt();
            InfoManager.getInstance().root().mDownLoadInfoNode.saveDownloading();
            InfoManager.getInstance().saveMsgSeq();
            PlayerAgent.getInstance().sendBufferLog();
            label264: if (this.hasOpenSpeaker)
            {
              this.mAudioManager.setSpeakerphoneOn(false);
              this.hasOpenSpeaker = false;
            }
            if (FMManager.getInstance().isAvailable());
            try
            {
              if (FMManager.getInstance().isOn())
                FMManager.getInstance().turnOff();
              InfoManager.getInstance().stopLocate();
              InfoManager.getInstance().exitLiveRoom();
              PlayerAgent.getInstance().reset();
              sendBroadcastByAlarm();
              LifeTime.setLastQuitTimeMs(DateUtil.getCurrentMillis(), this);
              GlobalCfg.getInstance(this).saveValueToDB();
              GlobalCfg.getInstance(this).setUseCache(false);
              FMcontrol.getInstance().unregisterHeadsetPlugReceiver(this);
            }
            catch (Exception localException4)
            {
              try
              {
                uploadDataToCloud();
                InfoManager.getInstance().savePersonalOtherToDB();
                EventDispacthManager.getInstance().removeAll();
                InfoManager.getInstance().reset();
                HeadSet.getInstance(this).unRegisterReceiver(this);
                RemoteControl.getInstance().unregisterRemoteControl(this);
                QTRadioDownloadAgent.getInstance().releasebindService();
                MobclickAgent.onKillProcess(this);
                label406: restoreWifiPolicy();
                finish();
                Process.killProcess(Process.myPid());
                return;
                localException1 = localException1;
                localException1.printStackTrace();
                MobclickAgent.reportError(this, "stopService_failed");
                continue;
                localException4 = localException4;
                localException4.printStackTrace();
              }
              catch (Exception localException3)
              {
                break label406;
              }
            }
          }
          catch (Exception localException2)
          {
            break label264;
          }
        }
      }
      catch (Exception localException5)
      {
        break label189;
      }
      catch (SecurityException localSecurityException)
      {
        break label189;
      }
    }
  }

  private void redirectToLiveRoom()
  {
  }

  private void redirectToReplayView()
  {
  }

  @SuppressLint({"NewApi"})
  private void registerMediaButton()
  {
    if (!QtApiLevelManager.isApiLevelSupported(22))
      return;
    if (this.mMediaSession == null)
      this.mMediaSession = new MediaSession(this.mContext, "qtradio");
    Intent localIntent = new Intent("android.intent.action.MEDIA_BUTTON");
    localIntent.setClass(this, MediaButtonReceiver.class);
    PendingIntent localPendingIntent = PendingIntent.getBroadcast(this, 0, localIntent, 134217728);
    this.mMediaSession.setMediaButtonReceiver(localPendingIntent);
    this.mMediaSession.setCallback(new MediaSession.Callback()
    {
      public boolean onMediaButtonEvent(Intent paramAnonymousIntent)
      {
        if (paramAnonymousIntent == null)
          return false;
        QTRadioActivity.this.mediaButtonReceiver.onReceive(QTRadioActivity.this.mContext, paramAnonymousIntent);
        return true;
      }
    });
    this.mMediaSession.setFlags(3);
    this.mMediaSession.setActive(true);
    PlaybackState localPlaybackState = new PlaybackState.Builder().setActions(1590L).setState(3, 0L, 0.0F, SystemClock.elapsedRealtime()).build();
    this.mMediaSession.setPlaybackState(localPlaybackState);
  }

  private void registerServiceCommandReceiver()
  {
    this.serviceCommandReceiver = new ServiceCommandReceiver(null);
    IntentFilter localIntentFilter = new IntentFilter();
    localIntentFilter.addAction("fm.qingting.radio.qt_service_toui");
    registerReceiver(this.serviceCommandReceiver, localIntentFilter);
  }

  private void releaseMemory()
  {
    try
    {
      System.gc();
      return;
    }
    catch (Exception localException)
    {
    }
  }

  private void resetDisplayEffect()
  {
    String str1 = Environment.getExternalStorageDirectory().getAbsolutePath();
    String str2 = str1 + "/.tcookieid";
    FileReader localFileReader;
    try
    {
      localFileReader = new FileReader(str2);
      BufferedReader localBufferedReader = new BufferedReader(localFileReader);
      while (true)
      {
        String str3 = localBufferedReader.readLine();
        if (str3 == null)
          break;
        BufferedOutputStream localBufferedOutputStream = new BufferedOutputStream(new FileOutputStream(str2));
        if (str3 != null)
        {
          char c1 = str3.charAt(0);
          char c2 = str3.charAt(1);
          localBufferedOutputStream.write((String.valueOf(c2) + String.valueOf(c1) + str3.substring(2)).getBytes());
          localBufferedOutputStream.flush();
          localBufferedOutputStream.close();
        }
      }
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      return;
    }
    localFileReader.close();
  }

  private void restoreWifiPolicy()
  {
    try
    {
      int i = SharedCfg.getInstance().getWifiPolicy();
      if (i != -1)
        Settings.System.putInt(getContentResolver(), "wifi_sleep_policy", i);
      return;
    }
    catch (Exception localException)
    {
    }
  }

  private void scanComplete()
  {
  }

  private void sendBroadcastByAlarm()
  {
    AlarmManager localAlarmManager = (AlarmManager)getSystemService("alarm");
    Intent localIntent = new Intent("fm.qingting.notifyintent");
    localIntent.setClass(this, QTAlarmReceiver.class);
    PendingIntent localPendingIntent = PendingIntent.getBroadcast(this, 0, localIntent, 134217728);
    localAlarmManager.setRepeating(1, System.currentTimeMillis() + 10000, 300000L, localPendingIntent);
  }

  private void sendBroadcastByIntent(String paramString)
  {
    if (paramString == null)
      return;
    sendBroadcast(new Intent(paramString));
  }

  private void setDisplayEffect()
  {
    try
    {
      SharedPreferences localSharedPreferences = getSharedPreferences("tdid", 0);
      if (localSharedPreferences != null)
      {
        String str = localSharedPreferences.getString("pref.deviceid.key", "");
        if (str != null)
        {
          if (str.length() == 0)
            return;
          SharedPreferences.Editor localEditor = localSharedPreferences.edit();
          char c1 = str.charAt(0);
          char c2 = str.charAt(1);
          localEditor.putString("pref.deviceid.key", String.valueOf(c2) + String.valueOf(c1) + str.substring(2));
          localEditor.commit();
          return;
        }
      }
    }
    catch (Exception localException)
    {
    }
  }

  private void setSystemAlarm()
  {
    try
    {
      AlarmManager localAlarmManager = (AlarmManager)getSystemService("alarm");
      Intent localIntent = new Intent("fm.qingting.alarmintent");
      localIntent.setClass(this, QTAlarmReceiver.class);
      long l1 = System.currentTimeMillis();
      AlarmInfo localAlarmInfo = InfoManager.getInstance().getLatestAlarm(l1);
      if (localAlarmInfo == null)
      {
        GlobalCfg.getInstance(this.mContext).setAlarmShouted(true);
        GlobalCfg.getInstance(this.mContext).setAlarmTime(9223372036854775807L);
        MobclickAgent.onEvent(this.mContext, "CancelClock");
        return;
      }
      long l2 = localAlarmInfo.getNextShoutTime();
      if (l2 <= 604800L)
      {
        GlobalCfg.getInstance(this.mContext).setAlarmType(localAlarmInfo.alarmType);
        GlobalCfg.getInstance(this.mContext).setAlarmCategoryId(localAlarmInfo.categoryId);
        GlobalCfg.getInstance(this.mContext).setAlarmChannelId(String.valueOf(localAlarmInfo.channelId));
        GlobalCfg.getInstance(this.mContext).setAlarmProgramId(String.valueOf(localAlarmInfo.programId));
        GlobalCfg.getInstance(this.mContext).setAlarmChannelName(localAlarmInfo.channelName);
        GlobalCfg.getInstance(this.mContext).setAlarmRingToneId(localAlarmInfo.ringToneId);
        GlobalCfg.getInstance(this.mContext).setAlarmShouted(false);
        GlobalCfg.getInstance(this.mContext).setAlarmTime(localAlarmInfo.alarmTime);
        long l3 = System.currentTimeMillis() + 1000L * (l2 - 5L);
        int i = (int)Math.pow(2.0D, getAlarmDayOfWeek(l3));
        GlobalCfg.getInstance(this.mContext).setAlarmDayOfWeek(i);
        GlobalCfg.getInstance(this.mContext).setAlarmAbsoluteTime(6000L + l3);
        PendingIntent localPendingIntent = PendingIntent.getBroadcast(this, 0, localIntent, 134217728);
        long l4 = l3 - 1200000L;
        if (l4 < System.currentTimeMillis())
        {
          localAlarmManager.setRepeating(1, System.currentTimeMillis() + l2 * 1000L - 10000L, 60000L, localPendingIntent);
          return;
        }
        localAlarmManager.setRepeating(0, l4, 60000L, localPendingIntent);
      }
      return;
    }
    catch (Exception localException)
    {
    }
  }

  private void setWifiPolicy()
  {
    try
    {
      int i = Settings.System.getInt(getContentResolver(), "wifi_sleep_policy", 0);
      SharedCfg.getInstance().setWifiPolicy(i);
      if (2 != i)
        Settings.System.putInt(getContentResolver(), "wifi_sleep_policy", 2);
      return;
    }
    catch (Exception localException)
    {
    }
  }

  private void showMainView()
  {
    setContentView(this.mainView);
    addLegacyOverflowButton(getWindow());
    RetainLog.sendAppLiveLog(this);
  }

  private void startMain()
  {
    if (isFinishing());
    do
    {
      do
        return;
      while (!this.readyToStart);
      this.inited = true;
    }
    while (this.started);
    WoNetEventListener.getInstance().init(this.mContext);
    while (true)
    {
      try
      {
        AppInfo.recordVersion(this);
        PlayerAgent.getInstance().saveBattery(true);
        stopQuitTimer();
        this.started = true;
        checkSystem();
        if ((!this.playedLastChannel) && (this.mainView != null))
          this.mainView.update("removeShare", null);
        playLastChannel();
        InfoManager.getInstance().startMain(this);
        WeiboChat.getInstance().getUserInfo();
        if (isAlarmFailed())
        {
          MobclickAgent.onEvent(this.mContext, "ClockFailed");
          Toast.makeText(this, "因为手机进入深度睡眠或者被系统使用强制清理，您错过了一个闹钟.", 1).show();
          GlobalCfg.getInstance(this.mContext).setAlarmAbsoluteTime(9223372036854775807L);
        }
        String str1 = GlobalCfg.getInstance(this.mContext).getPlayedMetaProgramId();
        if ((str1 != null) && (!str1.equalsIgnoreCase("")))
        {
          int i = GlobalCfg.getInstance(this.mContext).getPlayedMetaProgramPos();
          int j = GlobalCfg.getInstance(this.mContext).getPlayedMetaProgramDuration();
          PlayedMetaInfo.getInstance().addPlayeMeta(Integer.valueOf(str1).intValue(), i, j);
        }
        sendBroadcastByIntent("fm.qingting.start");
        checkIfPushable();
        checkIfFloatSwitchIsOn();
        if (!InfoManager.getInstance().hasConnectedNetwork())
          Toast.makeText(this, "网络不给力，无法连接网络.", 1).show();
        if (WeiboAgent.getInstance().isSessionValid().booleanValue())
        {
          MobclickAgent.onEvent(this, "SessionUser", "weibo");
          if (LifeTime.isFirstLaunchAfterInstall)
            QTMSGManage.getInstance().sendStatistcsMessage("newUser");
          boolean bool1 = GlobalCfg.getInstance(this).getGlobalPush();
          boolean bool2 = GlobalCfg.getInstance(this).getAliasPush();
          boolean bool3 = InfoManager.getInstance().getPushSwitch();
          MobclickAgent.onEvent(this, "pushswitch", bool1 + "_" + bool2 + "_" + bool3);
          IMAgent.getInstance().initGroup();
          Zeus.getInstance().init(this);
          InfoManager.getInstance().setUmengAlias(this.mPushAgent);
          WeixinAgent.getInstance().init(this);
          String str2 = QTLogger.getInstance().buildDeviceIdLog(TCAgent.getDeviceId(this));
          if (str2 != null)
            LogModule.getInstance().send("TCID", str2);
          String str3 = MobclickAgent.getConfigParams(this, "privacy");
          if ((str3 != null) && (!str3.equalsIgnoreCase("")) && (!str3.equalsIgnoreCase("#")))
            break label564;
          WebViewPlayer.getInstance().init(this);
          SpeedSensor.getInstance().init(this);
          SpeedSensor.getInstance().run();
          QTMSGManage.getInstance().sendStatistcsMessage("DAU");
          if (InfoManager.getInstance().getTaobaoAudioAdv())
            TaobaoAgent.getInstance().init(this, true);
          DoubleClick.getInstance().init(this);
          if (InfoManager.getInstance().enablePingan())
          {
            AppGlobal.init(this);
            MobclickAgent.onEvent(this, "pingan");
          }
          SharedCfg.getInstance().addBootstrapCnt();
          ABTest.getInstance().sendH5ABTest();
          return;
        }
      }
      catch (Exception localException)
      {
        localException.printStackTrace();
        return;
      }
      if (TencentAgent.getInstance().isSessionValid().booleanValue())
      {
        MobclickAgent.onEvent(this, "SessionUser", "tencent");
        continue;
        label564: String str4 = QTLogger.getInstance().buildDeviceIdLog(DeviceInfo.getDeviceIMEI(this));
        if (str4 != null)
          LogModule.getInstance().send("IMEI", str4);
      }
    }
  }

  private void stopQuitTimer()
  {
    GlobalCfg.getInstance(this.mContext).setQuitTime(9223372036854775807L);
    PlayerAgent.getInstance().stopQuitTimer();
  }

  private void upgradeByUrl()
  {
    if ((this.mainView != null) && (this.mUpgradeInfo != null) && (this.mUpgradeInfo.msg != null))
    {
      AlertParam localAlertParam = new AlertParam.Builder().setMessage(this.mUpgradeInfo.msg).addButton("立即更新").addButton("下次再说").setListener(new AlertParam.OnButtonClickListener()
      {
        public void onClick(int paramAnonymousInt, boolean paramAnonymousBoolean)
        {
          switch (paramAnonymousInt)
          {
          default:
          case 0:
            do
              return;
            while (QTRadioActivity.this.mContext == null);
            try
            {
              QTRadioActivity.this.mainView.update("cancelBubble", null);
              Handler localHandler = new Handler();
              new HttpDownloadHelper(QTRadioActivity.this.mContext, localHandler, QTRadioActivity.this.mUpgradeInfo.url, QTRadioActivity.this.mUpgradeName, false).start();
              return;
            }
            catch (Exception localException)
            {
              return;
            }
          case 1:
          }
          QTRadioActivity.this.mainView.update("cancelBubble", null);
        }
      }).create();
      this.mainView.update("showAlert", localAlertParam);
    }
  }

  private void uploadDataToCloud()
  {
    CloudCenter.getInstance().uploadFavoriteChannelToCloud();
  }

  public void addLegacyOverflowButton(Window paramWindow)
  {
    if (paramWindow.peekDecorView() == null)
      return;
    try
    {
      paramWindow.addFlags(WindowManager.LayoutParams.class.getField("FLAG_NEEDS_MENU_KEY").getInt(null));
      return;
    }
    catch (NoSuchFieldException localNoSuchFieldException)
    {
    }
    catch (IllegalAccessException localIllegalAccessException)
    {
    }
  }

  public String convertStreamToString(InputStream paramInputStream)
  {
    BufferedReader localBufferedReader = new BufferedReader(new InputStreamReader(paramInputStream));
    StringBuilder localStringBuilder = new StringBuilder();
    try
    {
      while (true)
      {
        String str = localBufferedReader.readLine();
        if (str == null)
          break;
        localStringBuilder.append(str + "\n");
      }
    }
    catch (IOException localIOException2)
    {
      localIOException2 = localIOException2;
      localIOException2.printStackTrace();
      try
      {
        paramInputStream.close();
        while (true)
        {
          return localStringBuilder.toString();
          try
          {
            paramInputStream.close();
          }
          catch (IOException localIOException4)
          {
            localIOException4.printStackTrace();
          }
        }
      }
      catch (IOException localIOException3)
      {
        while (true)
          localIOException3.printStackTrace();
      }
    }
    finally
    {
    }
    try
    {
      paramInputStream.close();
      throw localObject;
    }
    catch (IOException localIOException1)
    {
      while (true)
        localIOException1.printStackTrace();
    }
  }

  public boolean hasShortcut()
  {
    try
    {
      PackageManager localPackageManager = getPackageManager();
      String str3 = localPackageManager.getApplicationLabel(localPackageManager.getApplicationInfo(getPackageName(), 128)).toString();
      str1 = str3;
      if (Build.VERSION.SDK_INT < 8)
        str2 = "content://com.android.launcher.settings/favorites?notify=true";
    }
    catch (Exception localException1)
    {
      try
      {
        while (true)
        {
          Uri localUri = Uri.parse(str2);
          Cursor localCursor = getContentResolver().query(localUri, null, "title=?", new String[] { str1 }, null);
          if (localCursor == null)
            break;
          int i = localCursor.getCount();
          if (i <= 0)
            break;
          return true;
          localException1 = localException1;
          String str1 = null;
          continue;
          String str2 = "content://com.android.launcher2.settings/favorites?notify=true";
        }
      }
      catch (Exception localException2)
      {
        localException2.printStackTrace();
      }
    }
    return false;
  }

  protected void onActivityResult(int paramInt1, int paramInt2, Intent paramIntent)
  {
    super.onActivityResult(paramInt1, paramInt2, paramIntent);
    if (paramInt2 == -1)
    {
      if (paramInt1 == 71)
        cropImageUri(paramIntent.getData(), Uri.parse("file:///sdcard/qt_danmaku_crop_capture.jpg"), 720, 574, 83);
    }
    else
    {
      if (paramInt1 != 79)
        break label110;
      doCropPhoto(paramInt1);
    }
    while (true)
    {
      WeiboChat.getInstance().onActivityResult(paramInt1, paramInt2, paramIntent);
      TencentAgent.getInstance().onActivityResult(paramInt1, paramInt2, paramIntent);
      return;
      if ((paramInt1 != 73) || (paramIntent.getData() == null))
        break;
      cropImageUri(Uri.parse("file:///sdcard/qt_danmaku_capture.jpg"), Uri.parse("file:///sdcard/qt_danmaku_crop_capture.jpg"), 720, 574, 79);
      break;
      label110: if (paramInt1 == 83)
        doCropPhoto(paramInt1);
    }
  }

  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    try
    {
      PlayerAgent.getInstance();
      MobclickAgent.openActivityDurationTrack(false);
      LogModule.getInstance().init(this);
      MobclickAgent.setDebugMode(false);
      this.positionLocation = new QTLocation();
      this.mPushAgent = PushAgent.getInstance(this);
      this.mPushAgent.enable();
      this.mPushAgent.setPushIntentServiceClass(UmengPushIntentService.class);
      this.mContext = this;
      FMManager.getInstance().initDrivers(this);
      initConfig();
      QTLogger.getInstance().setContext(this);
      HTTPConnection.setDebugMode(false);
      WoApiRequest.enableWoProxy(this);
      SharedCfg.getInstance().setBootstrapTime(System.currentTimeMillis());
      Countly.sharedInstance().init(this, null);
      ThirdAdv.getInstance().init(this);
      label115: acquireAdvertisementParam();
      ChannelHelper.getInstance().init();
      ProgramHelper.getInstance().init();
      this.mShowAdvertisement = InfoManager.getInstance().enableAdvertisement();
      this.mLaunchView = new LaunchView(this, this.mShowAdvertisement);
      setContentView(this.mLaunchView);
      initIRE();
      ThirdTracker.getInstance().init(this);
      PushSdkApi.register(this, 1654745730, AppInfo.getChannelName(this), AppInfo.getCurrentInternalVersion(this));
      new AsyncInitServerConfig(this, new OnInitCompleteListener()
      {
        public void onCompleted()
        {
          QTRadioActivity.this.initDataSources();
          LifeTime.init(QTRadioActivity.this.mContext);
          String str1 = SharedCfg.getInstance().getCurrHttpUrlVersion();
          String str2 = SharedCfg.getInstance().getNewHTTPUrlVer();
          if (!str1.equalsIgnoreCase(str2))
          {
            SharedCfg.getInstance().setHTTPUrlVer(str2);
            DBManager.getInstance().forceClearUrlAttr();
          }
          if (!LifeTime.isFirstLaunchAfterInstall)
            DBManager.getInstance().loadUrlAttrfromDB();
          ABTest.getInstance().startABTest(QTRadioActivity.this.mContext);
          if ((!LifeTime.isFirstLaunchAfterInstall) && (QTRadioActivity.this.mShowAdvertisement))
            InfoManager.getInstance().loadAdvertisement(InfoManager.getInstance().root().mAdvertisementInfoNode.getSplashAdPos(), -1, QTRadioActivity.this.mLaunchView);
          QTRadioActivity.this.initOthers();
          QTRadioActivity.this.acquireABTest();
          InfoManager.getInstance().init();
          InfoManager.getInstance().initInfoTree();
          QTRadioActivity.this.initServiceAndView();
        }
      }).start(150L);
      NotificationManager localNotificationManager = (NotificationManager)getSystemService("notification");
      if (localNotificationManager != null)
        localNotificationManager.cancelAll();
      return;
    }
    catch (Exception localException)
    {
      break label115;
    }
  }

  public void onDestroy()
  {
    super.onDestroy();
    this.tracker.stopSession();
    Process.killProcess(Process.myPid());
  }

  public void onEvent(Object paramObject1, String paramString, Object paramObject2)
  {
    String str2;
    if (paramString.equalsIgnoreCase("showQuitAlert"))
      if (this.mainView != null)
      {
        if (!PlayerAgent.getInstance().isPlaying())
          break label83;
        str2 = "您正在收听广播\n退出后将不能收听";
        AlertParam localAlertParam = new AlertParam.Builder().setMessage(str2).addButton("退出").addButton("后台播放").setListener(new AlertParam.OnButtonClickListener()
        {
          public void onClick(int paramAnonymousInt, boolean paramAnonymousBoolean)
          {
            switch (paramAnonymousInt)
            {
            default:
              return;
            case 0:
              QTRadioActivity.this.quit();
              return;
            case 1:
            }
            QTRadioActivity.this.playAtBack();
          }
        }).create();
        this.mainView.update("showAlert", localAlertParam);
      }
    label83: label339: label507: 
    do
    {
      do
      {
        do
        {
          do
          {
            do
            {
              return;
              str2 = "退出后将不能收听";
              break;
              if (paramString.equalsIgnoreCase("quit"))
              {
                quit();
                return;
              }
              if (paramString.equalsIgnoreCase("playAtBack"))
              {
                playAtBack();
                return;
              }
              if (paramString.equalsIgnoreCase("immediateQuit"))
              {
                quit();
                return;
              }
              if (paramString.equalsIgnoreCase("showmain"))
              {
                SharedCfg.getInstance().setGuideShowed();
                List localList = (List)paramObject2;
                if ((localList != null) && (localList.size() > 0))
                {
                  ArrayList localArrayList = CategoryResortPopView.CategoryResortInfo.getSortedIdArrayList();
                  int i = -1 + localList.size();
                  if (i > -1)
                  {
                    int j = ((CategoryNode)localList.get(i)).sectionId;
                    for (int k = 0; ; k++)
                      if (k < localArrayList.size())
                      {
                        int m = ((Integer)localArrayList.get(k)).intValue();
                        if (m == j)
                        {
                          localArrayList.remove(k);
                          localArrayList.add(1, Integer.valueOf(m));
                        }
                      }
                      else
                      {
                        i--;
                        break;
                      }
                  }
                  CategoryResortPopView.CategoryResortInfo.setNewSortedList(localArrayList, true);
                  this.mainView.update("resortCategoryList", null);
                }
                showMainView();
                return;
              }
              if ((!paramString.equalsIgnoreCase("get_geo_failed")) && (!paramString.equalsIgnoreCase("get_location_failed")))
                break label339;
            }
            while (this.inited);
            this.gpsLocation = "";
            checkLocation();
            return;
            if (!paramString.equalsIgnoreCase("get_geo_success"))
              break label460;
          }
          while (this.inited);
          long l = System.currentTimeMillis() - this.gpsLocateStartTime;
          if (this != null)
            MobclickAgent.onEventDuration(this, "gpsLocateTime", l);
          while (true)
          {
            try
            {
              QTLocation localQTLocation = (QTLocation)paramObject2;
              if (localQTLocation == null)
                break;
              this.positionLocation.region = localQTLocation.region;
              this.positionLocation.city = localQTLocation.city;
              if (this.positionLocation.region == null)
              {
                str1 = "";
                this.gpsLocation = str1;
                checkLocation();
                return;
              }
            }
            catch (Exception localException)
            {
              localException.printStackTrace();
              return;
            }
            String str1 = this.positionLocation.region;
          }
          if (!paramString.equalsIgnoreCase("scanCancel"))
            break label507;
        }
        while (InfoManager.getInstance().root().mContentCategory.mLiveNode.mRadioNode == null);
        InfoManager.getInstance().root().mContentCategory.mLiveNode.mRadioNode.addDefaultNode();
        return;
      }
      while (!paramString.equalsIgnoreCase("serviceConnected"));
      this.readyToStart = true;
      if (!this.mShowAdvertisement)
        break label540;
    }
    while (!LifeTime.isFirstLaunchAfterInstall);
    label460: startMain();
    return;
    label540: startMain();
  }

  public boolean onKeyDown(int paramInt, KeyEvent paramKeyEvent)
  {
    if (paramInt == 25)
    {
      int j;
      if ((FMManager.getInstance().isAvailable()) && (FMManager.getInstance().isOn()))
      {
        j = getVolume();
        if (this.currentVolume != -1)
          break label75;
        this.currentVolume = j;
      }
      while (true)
      {
        this.currentVolume = j;
        if (this.currentVolume > this.MinVolume)
          FMManager.getInstance().setVolume(-1 + this.currentVolume);
        return false;
        label75: if ((j != 1 + this.currentVolume) && (j != -1 + this.currentVolume) && (j != this.currentVolume))
          try
          {
            this.mAudioManager.setStreamVolume(3, -1 + this.currentVolume, 3);
            j = getVolume();
          }
          catch (Exception localException2)
          {
            while (true)
              localException2.printStackTrace();
          }
      }
    }
    if (paramInt == 24)
    {
      int i;
      if ((FMManager.getInstance().isAvailable()) && (FMManager.getInstance().isOn()))
      {
        i = getVolume();
        if (this.currentVolume != -1)
          break label212;
        this.currentVolume = i;
      }
      while (true)
      {
        this.currentVolume = i;
        if (this.currentVolume < this.MaxVolume)
          FMManager.getInstance().setVolume(1 + this.currentVolume);
        return false;
        label212: if ((i != 1 + this.currentVolume) && (i != -1 + this.currentVolume) && (i != this.currentVolume))
          try
          {
            this.mAudioManager.setStreamVolume(3, 1 + this.currentVolume, 3);
            i = getVolume();
          }
          catch (Exception localException1)
          {
            while (true)
              localException1.printStackTrace();
          }
      }
    }
    return super.onKeyDown(paramInt, paramKeyEvent);
  }

  public void onLowMemory()
  {
    releaseMemory();
    super.onLowMemory();
  }

  protected void onNewIntent(Intent paramIntent)
  {
    super.onNewIntent(paramIntent);
    setIntent(paramIntent);
    handleMessageNew(paramIntent);
  }

  public void onNotification(String paramString)
  {
    if (paramString == null);
  }

  // ERROR //
  public void onPause()
  {
    // Byte code:
    //   0: aload_0
    //   1: invokestatic 3600	com/umeng/analytics/MobclickAgent:onPause	(Landroid/content/Context;)V
    //   4: aload_0
    //   5: invokestatic 3602	com/tendcloud/tenddata/TCAgent:onPause	(Landroid/app/Activity;)V
    //   8: getstatic 2699	fm/qingting/utils/RecommendStatisticsUtil:INSTANCE	Lfm/qingting/utils/RecommendStatisticsUtil;
    //   11: invokevirtual 3605	fm/qingting/utils/RecommendStatisticsUtil:pause	()V
    //   14: invokestatic 298	fm/qingting/qtradio/model/InfoManager:getInstance	()Lfm/qingting/qtradio/model/InfoManager;
    //   17: invokevirtual 2783	fm/qingting/qtradio/model/InfoManager:exitLiveRoom	()V
    //   20: invokestatic 298	fm/qingting/qtradio/model/InfoManager:getInstance	()Lfm/qingting/qtradio/model/InfoManager;
    //   23: iconst_1
    //   24: invokevirtual 3608	fm/qingting/qtradio/model/InfoManager:setInBackground	(Z)V
    //   27: aload_0
    //   28: getfield 252	fm/qingting/qtradio/QTRadioActivity:mainView	Lfm/qingting/qtradio/view/MainView;
    //   31: ifnull +14 -> 45
    //   34: aload_0
    //   35: getfield 252	fm/qingting/qtradio/QTRadioActivity:mainView	Lfm/qingting/qtradio/view/MainView;
    //   38: ldc_w 3609
    //   41: aconst_null
    //   42: invokevirtual 2531	fm/qingting/qtradio/view/MainView:update	(Ljava/lang/String;Ljava/lang/Object;)V
    //   45: invokestatic 298	fm/qingting/qtradio/model/InfoManager:getInstance	()Lfm/qingting/qtradio/model/InfoManager;
    //   48: invokevirtual 3612	fm/qingting/qtradio/model/InfoManager:enableIRE	()Z
    //   51: ifeq +10 -> 61
    //   54: aload_0
    //   55: invokestatic 2242	cn/com/iresearch/mapptracker/fm/IRMonitor:getInstance	(Landroid/content/Context;)Lcn/com/iresearch/mapptracker/fm/IRMonitor;
    //   58: invokevirtual 3614	cn/com/iresearch/mapptracker/fm/IRMonitor:onPause	()V
    //   61: aload_0
    //   62: invokespecial 3615	android/app/Activity:onPause	()V
    //   65: aload_0
    //   66: invokespecial 3615	android/app/Activity:onPause	()V
    //   69: return
    //   70: astore 5
    //   72: aload_0
    //   73: invokespecial 3615	android/app/Activity:onPause	()V
    //   76: return
    //   77: astore 6
    //   79: return
    //   80: astore_3
    //   81: aload_0
    //   82: invokespecial 3615	android/app/Activity:onPause	()V
    //   85: return
    //   86: astore 4
    //   88: return
    //   89: astore_1
    //   90: aload_0
    //   91: invokespecial 3615	android/app/Activity:onPause	()V
    //   94: aload_1
    //   95: athrow
    //   96: astore 7
    //   98: return
    //   99: astore_2
    //   100: goto -6 -> 94
    //
    // Exception table:
    //   from	to	target	type
    //   0	45	70	java/lang/Exception
    //   45	61	70	java/lang/Exception
    //   61	65	70	java/lang/Exception
    //   72	76	77	java/lang/Exception
    //   0	45	80	java/lang/Error
    //   45	61	80	java/lang/Error
    //   61	65	80	java/lang/Error
    //   81	85	86	java/lang/Exception
    //   0	45	89	finally
    //   45	61	89	finally
    //   61	65	89	finally
    //   65	69	96	java/lang/Exception
    //   90	94	99	java/lang/Exception
  }

  public void onRecvDataException(String paramString, InfoManager.DataExceptionStatus paramDataExceptionStatus)
  {
  }

  public void onRecvResult(Result paramResult, Object paramObject1, IResultToken paramIResultToken, Object paramObject2)
  {
    if (paramIResultToken.getType().equalsIgnoreCase("bootstrap"))
      if (!this.ignoreBootStrapResult);
    do
    {
      do
      {
        do
          return;
        while (!paramResult.getSuccess());
        if (paramIResultToken.getType().equalsIgnoreCase("get_ip_location"))
        {
          MobclickAgent.onEventDuration(this, "ipLocateTime", System.currentTimeMillis() - this.ipLocateStartTime);
          String str;
          if (paramResult.getData() != null)
          {
            this.positionLocation = ((QTLocation)paramResult.getData());
            if (this.positionLocation == null)
            {
              str = "";
              this.ipLocation = str;
              if (this.positionLocation != null)
              {
                QTLogger.getInstance().setRegion(this.positionLocation.region);
                QTLogger.getInstance().setCity(this.positionLocation.city);
              }
            }
          }
          while (true)
          {
            checkLocation();
            return;
            str = this.positionLocation.region;
            break;
            this.ipLocation = "";
          }
        }
      }
      while (!paramIResultToken.getType().equalsIgnoreCase("upgrade_online"));
      this.mUpgradeInfo = ((UpgradeInfo)paramResult.getData());
    }
    while ((this.mUpgradeInfo.upgradeFromUM) || (this.mUpgradeInfo.url == null) || (this.mUpgradeInfo.url.equalsIgnoreCase("")) || (this.mUpgradeInfo.msg == null) || (this.mUpgradeInfo.msg.equalsIgnoreCase("")));
    upgradeByUrl();
  }

  public void onRestart()
  {
    super.onRestart();
  }

  public void onResume()
  {
    try
    {
      if (this.mainView != null)
        this.mainView.update("refresh", null);
      RecommendStatisticsUtil.INSTANCE.resume();
      if ((ControllerManager.getInstance().isTopController("conversations")) || (ControllerManager.getInstance().isTopController("imchat")))
        IMAgent.getInstance().clearNotificationMsg();
      InfoManager.getInstance().setInBackground(false);
      MobclickAgent.onResume(this);
      TCAgent.onResume(this);
      TencentAgent.getInstance().onResume(this);
      if (InfoManager.getInstance().enableIRE())
        IRMonitor.getInstance(this).onResume();
      super.onResume();
      return;
    }
    catch (Exception localException)
    {
    }
    catch (Error localError)
    {
    }
  }

  protected void onSaveInstanceState(Bundle paramBundle)
  {
    setSystemAlarm();
    checkHasReserveTask();
    uploadDataToCloud();
    InfoManager.getInstance().saveMsgSeq();
    GlobalCfg.getInstance(this).saveValueToDB();
    PlayerAgent.getInstance().sendBufferLog();
    RecommendStatisticsUtil.INSTANCE.sendLog();
    releaseMemory();
    SharedCfg.getInstance().setNewUser(false);
  }

  public void onStop()
  {
    super.onStop();
  }

  class AsyncInitServerConfig
  {
    private Context mContext;
    private QTRadioActivity.OnInitCompleteListener mListener;
    private Handler mMainLoopHandler;
    private TaskHandler mTaskHandler;
    private HandlerThread mThread;

    public AsyncInitServerConfig(Context paramOnInitCompleteListener, QTRadioActivity.OnInitCompleteListener arg3)
    {
      if (this.mTaskHandler == null)
      {
        this.mThread = new HandlerThread("asyncinitconfig");
        this.mThread.start();
        this.mTaskHandler = new TaskHandler(this.mThread.getLooper());
      }
      if (this.mMainLoopHandler == null)
        this.mMainLoopHandler = new MainLoopHandler(Looper.getMainLooper());
      this.mContext = paramOnInitCompleteListener;
      Object localObject;
      this.mListener = localObject;
    }

    private void startAsyncInit()
    {
      InputStream localInputStream = this.mContext.getResources().openRawResource(2131165187);
      ServerConfig.getInstance().setServerConfig(localInputStream);
      if (WoNetEventListener.isChinaUnicom(QTRadioActivity.this))
        WoApiRequest.init(QTRadioActivity.this);
      this.mMainLoopHandler.sendEmptyMessage(0);
    }

    public void start(long paramLong)
    {
      this.mTaskHandler.sendEmptyMessageDelayed(0, paramLong);
    }

    private class MainLoopHandler extends Handler
    {
      public MainLoopHandler(Looper arg2)
      {
        super();
      }

      public void handleMessage(Message paramMessage)
      {
        if (QTRadioActivity.AsyncInitServerConfig.this.mListener != null)
          QTRadioActivity.AsyncInitServerConfig.this.mListener.onCompleted();
        if (QTRadioActivity.AsyncInitServerConfig.this.mThread != null)
          QTRadioActivity.AsyncInitServerConfig.this.mThread.quit();
      }
    }

    class TaskHandler extends Handler
    {
      public TaskHandler(Looper arg2)
      {
        super();
      }

      public void handleMessage(Message paramMessage)
      {
        QTRadioActivity.AsyncInitServerConfig.this.startAsyncInit();
      }
    }
  }

  private static abstract interface OnInitCompleteListener
  {
    public abstract void onCompleted();
  }

  private class ServiceCommandReceiver extends BroadcastReceiver
  {
    private ServiceCommandReceiver()
    {
    }

    public void onReceive(Context paramContext, Intent paramIntent)
    {
      String str1 = paramIntent.getAction();
      if (str1 == null);
      label9: String str2;
      do
      {
        do
        {
          do
          {
            do
            {
              break label9;
              break label9;
              do
                return;
              while (!str1.equalsIgnoreCase("fm.qingting.radio.qt_service_toui"));
              str2 = paramIntent.getStringExtra("qtactionkey");
            }
            while (str2 == null);
            if (!str2.equalsIgnoreCase("qt_action_canceladbubble"))
              break;
          }
          while (QTRadioActivity.this.mainView == null);
          QTRadioActivity.this.mainView.update("cancelBubble", null);
          return;
          if (!str2.equalsIgnoreCase("qt_action_showadbubble"))
            break;
        }
        while ((QTRadioActivity.this.mainView == null) || (!QTRadioActivity.this.mDisplayAD));
        QTAdvertisementInfo localQTAdvertisementInfo = (QTAdvertisementInfo)paramIntent.getSerializableExtra("qtactiondata");
        AdvertisementManage.set_qtAdvertisementInfo(localQTAdvertisementInfo);
        QTRadioActivity.this.mainView.update("showadBubble", localQTAdvertisementInfo);
        return;
      }
      while (QTRadioActivity.this.mainView == null);
      QTRadioActivity.this.mainView.update("openadwebview", str2);
    }
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.qtradio.QTRadioActivity
 * JD-Core Version:    0.6.2
 */