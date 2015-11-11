package fm.qingting.qtradio.helper;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Handler;
import com.tendcloud.tenddata.TCAgent;
import com.umeng.analytics.MobclickAgent;
import fm.qingting.download.HttpDownloadHelper;
import fm.qingting.framework.manager.EventDispacthManager;
import fm.qingting.qtradio.model.InfoManager;
import fm.qingting.qtradio.model.SharedCfg;
import java.util.ArrayList;
import java.util.Iterator;

public class OnlineUpdateHelper
{
  private static final String APK_ANZHI_DOWNLOAD_URL = "http://m.anzhi.com/redirect.php?do=dlapk&puid=1099";
  private static final String APK_ANZHI_NAME = "Anzhi.qpk";
  private static final String APK_QT_DOWNLOAD_URL = "http://qingting.fm/app/download";
  private static final String APK_QT_NAME = "QTRadioUpgrade.apk";
  private static final String PACKAGE_ANZHI = "cn.goapk.market";
  private static final String QT_FROM_ANZHI_URI = "anzhimarket://details?id=fm.qingting.qtradio&flag=1";
  private static final String UMENG_PARAM_LATEST_VERSION = "latestVersion";
  private static final String UMENG_PARAM_ONLINE_UPDATE_DOWNLOAD_URL = "onlineUpdateDownloadUrl";
  private static final String UMENT_PARAM_MESSAGE = "updateMessage";
  private static final long UPGRADE_TIME_STAMP = 604800000L;
  private static OnlineUpdateHelper mInstance;
  private String mChannelName = this.mContext.getResources().getString(2131492868);
  private Context mContext = InfoManager.getInstance().getContext();
  private String mDownloadUrl;
  private boolean mHasUpdate = false;
  private String mLatestVersion = "";
  private ArrayList<OnUpdateListener> mListeners;
  private String mMessage;
  private boolean mNeedQuickDownload;

  private OnlineUpdateHelper()
  {
    checkUpdate();
  }

  private void checkUpdate()
  {
    this.mMessage = MobclickAgent.getConfigParams(this.mContext, "updateMessage");
    this.mLatestVersion = MobclickAgent.getConfigParams(this.mContext, "latestVersion");
    this.mDownloadUrl = MobclickAgent.getConfigParams(this.mContext, "onlineUpdateDownloadUrl");
    String str1 = SharedCfg.getInstance().getLatestVersion();
    String str2 = this.mContext.getResources().getString(2131492874);
    long l = SharedCfg.getInstance().getUpgradeTime();
    if (this.mLatestVersion == null)
      this.mLatestVersion = "";
    if ((this.mDownloadUrl == null) || (this.mDownloadUrl.equalsIgnoreCase("")))
      this.mDownloadUrl = "http://qingting.fm/app/download";
    if (str1 == null)
      str1 = "";
    if (str2.compareToIgnoreCase(this.mLatestVersion) < 0)
      this.mHasUpdate = true;
    if (this.mHasUpdate)
    {
      if (str1.compareTo(this.mLatestVersion) < 0)
      {
        SharedCfg.getInstance().setLatestVersion(this.mLatestVersion);
        HttpDownloadHelper.deleteFile("QTRadioUpgrade.apk");
        HttpDownloadHelper.deleteFile("Anzhi.qpk");
      }
      if ((l < System.currentTimeMillis()) || (str1.compareToIgnoreCase(this.mLatestVersion) < 0))
      {
        SharedCfg.getInstance().setUpgradeTime(604800000L + System.currentTimeMillis());
        if ((InfoManager.getInstance().hasWifi()) && (!SharedCfg.getInstance().isNewUser()))
          new Handler().postDelayed(new Runnable()
          {
            public void run()
            {
              OnlineUpdateHelper.this.showUpgradeAlert();
            }
          }
          , 1000L);
      }
    }
  }

  private void download(String paramString1, String paramString2)
  {
    Handler localHandler = new Handler();
    new HttpDownloadHelper(this.mContext, localHandler, paramString1, paramString2, false).start();
  }

  public static OnlineUpdateHelper getInstance()
  {
    if (mInstance == null)
      mInstance = new OnlineUpdateHelper();
    return mInstance;
  }

  private boolean isAppInstalled(String paramString)
  {
    PackageManager localPackageManager = this.mContext.getPackageManager();
    try
    {
      localPackageManager.getPackageInfo(paramString, 1);
      return true;
    }
    catch (PackageManager.NameNotFoundException localNameNotFoundException)
    {
    }
    return false;
  }

  public void addListener(OnUpdateListener paramOnUpdateListener)
  {
    if (paramOnUpdateListener == null)
      return;
    if (this.mListeners == null)
      this.mListeners = new ArrayList();
    this.mListeners.add(paramOnUpdateListener);
  }

  public void dispatchUpdate()
  {
    if (this.mListeners == null);
    while (true)
    {
      return;
      try
      {
        Iterator localIterator = this.mListeners.iterator();
        while (localIterator.hasNext())
          ((OnUpdateListener)localIterator.next()).onUpdate();
      }
      catch (Exception localException)
      {
      }
    }
  }

  public void download()
  {
    download(this.mDownloadUrl, "QTRadioUpgrade.apk");
  }

  public String getLatestVersion()
  {
    return this.mLatestVersion;
  }

  public boolean hasUpdate()
  {
    return this.mHasUpdate;
  }

  public boolean needQuickDownload()
  {
    return this.mNeedQuickDownload;
  }

  public void quickDownload()
  {
    if (isAppInstalled("cn.goapk.market"))
    {
      Intent localIntent = new Intent();
      localIntent.addFlags(268435456);
      localIntent.setData(Uri.parse("anzhimarket://details?id=fm.qingting.qtradio&flag=1"));
      this.mContext.startActivity(localIntent);
      return;
    }
    download("http://m.anzhi.com/redirect.php?do=dlapk&puid=1099", "Anzhi.qpk");
  }

  public void sendEventMessage(String paramString)
  {
    MobclickAgent.onEvent(InfoManager.getInstance().getContext(), paramString);
    TCAgent.onEvent(InfoManager.getInstance().getContext(), paramString);
  }

  public void setNeedQuickDownload(boolean paramBoolean)
  {
    this.mNeedQuickDownload = paramBoolean;
  }

  public void showUpgradeAlert()
  {
    if (!this.mHasUpdate)
      return;
    EventDispacthManager.getInstance().dispatchAction("onlineUpgrade", this.mMessage);
    sendEventMessage("updateDialog");
  }

  public class EventType
  {
    public static final String UPDATE_DIALOG = "updateDialog";
    public static final String UPDATE_DOWNLOAD = "updateDownload";
    public static final String UPDATE_LIGHT_DOWNLOAD = "updateLightDownload";
    public static final String UPDATE_WAIT = "updateWait";

    public EventType()
    {
    }
  }

  public static abstract interface OnUpdateListener
  {
    public abstract void onUpdate();
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.qtradio.helper.OnlineUpdateHelper
 * JD-Core Version:    0.6.2
 */