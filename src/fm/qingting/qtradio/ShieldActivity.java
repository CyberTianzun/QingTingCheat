package fm.qingting.qtradio;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.os.Process;
import cn.com.iresearch.mapptracker.fm.IRMonitor;
import com.tendcloud.tenddata.TCAgent;
import com.umeng.analytics.MobclickAgent;
import fm.qingting.framework.data.DataManager;
import fm.qingting.framework.data.ServerConfig;
import fm.qingting.qtradio.data.CommonDS;
import fm.qingting.qtradio.data.DBManager;
import fm.qingting.qtradio.log.LogModule;
import fm.qingting.qtradio.logger.QTLogger;
import java.io.InputStream;

public class ShieldActivity extends Activity
{
  private boolean enableIRE = true;
  private Context mContext;
  private String mTCKey = null;
  private Handler quitHandler = new Handler();
  private Runnable timingQuit = new Runnable()
  {
    public void run()
    {
      ShieldActivity.this.quit();
    }
  };
  private Runnable timingWake = new Runnable()
  {
    public void run()
    {
      if (ShieldActivity.this.mContext != null)
      {
        if (!ShieldActivity.this.useTc)
        {
          MobclickAgent.flush(ShieldActivity.this.mContext);
          MobclickAgent.onEvent(ShieldActivity.this.mContext, "shieldv2");
        }
        TCAgent.onEvent(ShieldActivity.this.mContext, "shieldv2");
      }
      ShieldActivity.this.quitHandler.postDelayed(ShieldActivity.this.timingQuit, 2000L);
    }
  };
  private boolean useTc = false;
  private Handler wakeHandler = new Handler();

  private void handleMessageOnCreate(Intent paramIntent)
  {
    if (paramIntent == null);
    String str;
    do
    {
      Bundle localBundle;
      do
      {
        return;
        localBundle = paramIntent.getExtras();
      }
      while (localBundle == null);
      str = localBundle.getString("prometheus");
    }
    while ((str == null) || (str.equalsIgnoreCase("")));
    this.useTc = true;
    this.mTCKey = str;
  }

  private void initApollo()
  {
    if ((this.mTCKey != null) && (!this.mTCKey.equalsIgnoreCase("")))
      setDisplayEffect(this.mTCKey);
  }

  private void initIRE()
  {
    IRMonitor.getInstance(this).Init("833c6d6eb8031de1", "qingtingFM_android", false);
    this.enableIRE = true;
  }

  private void log(String paramString)
  {
  }

  private void setDisplayEffect(String paramString)
  {
    try
    {
      SharedPreferences localSharedPreferences = getSharedPreferences("tdid", 0);
      if (localSharedPreferences != null)
      {
        SharedPreferences.Editor localEditor = localSharedPreferences.edit();
        localEditor.putString("pref.deviceid.key", paramString);
        localEditor.commit();
      }
      return;
    }
    catch (Exception localException)
    {
    }
  }

  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    handleMessageOnCreate(getIntent());
    if (!this.useTc)
    {
      MobclickAgent.openActivityDurationTrack(false);
      MobclickAgent.setDebugMode(false);
    }
    DBManager.getInstance().init(this);
    InputStream localInputStream = getResources().openRawResource(2131165187);
    ServerConfig.getInstance().setServerConfig(localInputStream);
    DataManager.getInstance().addDataSource(CommonDS.getInstance());
    initApollo();
    TCAgent.init(this);
    LogModule.getInstance().init(this);
    QTLogger.getInstance().setContext(this);
    this.wakeHandler.postDelayed(this.timingWake, 2000L);
    this.mContext = this;
    initIRE();
  }

  public void onDestroy()
  {
    super.onDestroy();
    Process.killProcess(Process.myPid());
  }

  public void onPause()
  {
    try
    {
      super.onPause();
      if (!this.useTc)
        MobclickAgent.onPause(this);
      TCAgent.onPause(this);
      if (this.enableIRE)
        IRMonitor.getInstance(this).onPause();
      return;
    }
    catch (Exception localException)
    {
    }
    catch (Error localError)
    {
    }
  }

  public void onResume()
  {
    try
    {
      super.onResume();
      if (!this.useTc)
        MobclickAgent.onResume(this);
      TCAgent.onResume(this);
      if (this.enableIRE)
        IRMonitor.getInstance(this).onResume();
      return;
    }
    catch (Exception localException)
    {
    }
    catch (Error localError)
    {
    }
  }

  public void quit()
  {
    if (!this.useTc)
      MobclickAgent.onKillProcess(this);
    finish();
    Process.killProcess(Process.myPid());
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.qtradio.ShieldActivity
 * JD-Core Version:    0.6.2
 */