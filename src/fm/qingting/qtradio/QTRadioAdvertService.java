package fm.qingting.qtradio;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.app.KeyguardManager;
import android.app.Notification;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Resources;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.Process;
import android.provider.Settings.Secure;
import android.telephony.TelephonyManager;
import fm.qingting.framework.data.DataManager;
import fm.qingting.framework.data.IResultRecvHandler;
import fm.qingting.framework.data.IResultToken;
import fm.qingting.framework.data.NetDS;
import fm.qingting.framework.data.Result;
import fm.qingting.framework.data.ServerConfig;
import fm.qingting.qtradio.manager.advertisement.AdvertisementManage;
import fm.qingting.qtradio.manager.advertisement.IADEventListener;
import fm.qingting.qtradio.manager.advertisement.UMengLogger;
import fm.qingting.qtradio.manager.advertisement.qtsensor.QTShakeDetectorManage;
import fm.qingting.qtradio.manager.advertisement.qtsensor.QTShakeDetectorManage.OnShakeListener;
import fm.qingting.qtradio.model.advertisement.QTAdvertisementInfo;
import fm.qingting.qtradio.parser.NetParser;
import java.io.InputStream;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

public class QTRadioAdvertService extends Service
  implements IADEventListener, QTShakeDetectorManage.OnShakeListener, IResultRecvHandler
{
  private final Handler adHandler = new Handler();
  private Runnable adRunnable = new Runnable()
  {
    public void run()
    {
      QTRadioAdvertService.this.delayTaskCommit();
      QTRadioAdvertService.this.radiocastMessage("qt_action_canceladbubble");
    }
  };
  private IResultToken adcouponRT;
  private IResultToken adinfolistRT;
  private CommandReceiver cmdReceiver;
  private String mDeDeviceId = null;

  private void avoidKilled()
  {
    startForeground(1234, new Notification());
  }

  private void delayTaskCommit()
  {
    QTShakeDetectorManage.getInstance().stop();
    this.adHandler.removeCallbacks(this.adRunnable);
  }

  private void exitProcess()
  {
    Process.killProcess(Process.myPid());
  }

  private String getUniqueId()
  {
    if (this.mDeDeviceId == null);
    try
    {
      TelephonyManager localTelephonyManager = (TelephonyManager)getBaseContext().getSystemService("phone");
      String str1 = "" + localTelephonyManager.getDeviceId();
      String str2 = "" + localTelephonyManager.getSimSerialNumber();
      this.mDeDeviceId = new UUID(("" + Settings.Secure.getString(getContentResolver(), "android_id")).hashCode(), str1.hashCode() << 32 | str2.hashCode()).toString();
      label127: if (this.mDeDeviceId == null)
        this.mDeDeviceId = ("UnknowUser_" + Build.MANUFACTURER + "_" + Build.MODEL);
      return this.mDeDeviceId;
    }
    catch (Exception localException)
    {
      break label127;
    }
  }

  private void radiocastMessage(String paramString)
  {
    Intent localIntent = new Intent();
    localIntent.putExtra("qtactionkey", paramString);
    localIntent.setAction("fm.qingting.radio.qt_service_toui");
    sendBroadcast(localIntent);
  }

  private void radiocastMessage(String paramString, QTAdvertisementInfo paramQTAdvertisementInfo)
  {
    Intent localIntent = new Intent();
    localIntent.putExtra("qtactionkey", paramString);
    localIntent.putExtra("qtactiondata", paramQTAdvertisementInfo);
    localIntent.setAction("fm.qingting.radio.qt_service_toui");
    sendBroadcast(localIntent);
  }

  private void registerCommandReceiver()
  {
    this.cmdReceiver = new CommandReceiver(null);
    IntentFilter localIntentFilter = new IntentFilter();
    localIntentFilter.addAction("fm.qingting.radio.qt_ui_toservice");
    registerReceiver(this.cmdReceiver, localIntentFilter);
  }

  private void unregisterCommandReceiver()
  {
    if (this.cmdReceiver != null);
    try
    {
      unregisterReceiver(this.cmdReceiver);
      return;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      return;
    }
    finally
    {
      this.cmdReceiver = null;
    }
  }

  public void getADInfoList()
  {
  }

  public void getCoupon(String paramString)
  {
    if (paramString == null);
  }

  public boolean isAppOnForeground()
  {
    try
    {
      ActivityManager localActivityManager = (ActivityManager)getApplicationContext().getSystemService("activity");
      String str = getApplicationContext().getPackageName();
      List localList = localActivityManager.getRunningAppProcesses();
      if (localList == null)
        return false;
      Iterator localIterator = localList.iterator();
      while (localIterator.hasNext())
      {
        ActivityManager.RunningAppProcessInfo localRunningAppProcessInfo = (ActivityManager.RunningAppProcessInfo)localIterator.next();
        if (localRunningAppProcessInfo.processName.equals(str))
        {
          int i = localRunningAppProcessInfo.importance;
          if (i == 100)
            return true;
        }
      }
    }
    catch (Exception localException)
    {
    }
    return false;
  }

  public boolean isScreenLocked()
  {
    try
    {
      boolean bool = ((KeyguardManager)getSystemService("keyguard")).inKeyguardRestrictedInputMode();
      return !bool;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return true;
  }

  public void onAdvertiseFoundEvent(String paramString)
  {
    QTShakeDetectorManage.getInstance().start();
    this.adHandler.removeCallbacks(this.adRunnable);
    this.adHandler.postDelayed(this.adRunnable, 60000L);
    QTAdvertisementInfo localQTAdvertisementInfo = AdvertisementManage.getInstance().getCurrentAdvertisementInfo();
    if (localQTAdvertisementInfo != null)
      radiocastMessage("qt_action_showadbubble", localQTAdvertisementInfo);
    if (!isAppOnForeground())
      UMengLogger.sendmessage(this, "adPlay", AdvertisementManage.getInstance().currentADKey, 0);
  }

  public IBinder onBind(Intent paramIntent)
  {
    return null;
  }

  public void onCreate()
  {
    avoidKilled();
  }

  public void onDestroy()
  {
    unregisterCommandReceiver();
    QTShakeDetectorManage.getInstance().stop();
    QTShakeDetectorManage.getInstance().removeListener(this);
    AdvertisementManage.getInstance().removeListener(this);
    AdvertisementManage.getInstance().unregisterADReceiver(this);
    exitProcess();
  }

  public void onRecvResult(Result paramResult, Object paramObject1, IResultToken paramIResultToken, Object paramObject2)
  {
  }

  public void onShake()
  {
    delayTaskCommit();
    radiocastMessage("qt_action_canceladbubble");
    getCoupon(AdvertisementManage.getInstance().currentADKey);
  }

  public int onStartCommand(Intent paramIntent, int paramInt1, int paramInt2)
  {
    InputStream localInputStream = getResources().openRawResource(2131165187);
    ServerConfig.getInstance().setServerConfig(localInputStream);
    AdvertisementManage.getInstance().addListener(this);
    AdvertisementManage.getInstance().registerADReceiver(this);
    DataManager.getInstance().addDataSource(NetDS.getInstance());
    NetDS.getInstance().addParser(new NetParser());
    QTShakeDetectorManage.getInstance().stop();
    QTShakeDetectorManage.getInstance().initSensor(this);
    QTShakeDetectorManage.getInstance().addListener(this);
    registerCommandReceiver();
    getADInfoList();
    return super.onStartCommand(paramIntent, paramInt1, paramInt2);
  }

  private class CommandReceiver extends BroadcastReceiver
  {
    private CommandReceiver()
    {
    }

    public void onReceive(Context paramContext, Intent paramIntent)
    {
      String str1 = paramIntent.getAction();
      if (str1 == null);
      String str2;
      do
      {
        do
          return;
        while (!str1.equalsIgnoreCase("fm.qingting.radio.qt_ui_toservice"));
        str2 = paramIntent.getStringExtra("qtactionkey");
      }
      while ((str2 == null) || (!str2.equalsIgnoreCase("qt_action_stopservice")));
      QTRadioAdvertService.this.delayTaskCommit();
    }
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.qtradio.QTRadioAdvertService
 * JD-Core Version:    0.6.2
 */