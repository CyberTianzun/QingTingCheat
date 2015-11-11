package fm.qingting.download.qtradiodownload.core;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import fm.qingting.download.qtradiodownload.network.access.NetworkAccess;
import fm.qingting.download.qtradiodownload.platform.AndroidFactory;

public class NetworkManager
{
  private static final String TAG = "AccessManager";
  private static NetworkManager instance;
  private ConnectivityManager connectivityMgr;
  private Core core;
  private NetworkAccess curAccess;
  private BroadcastReceiver networkStateListener = new BroadcastReceiver()
  {
    public void onReceive(Context paramAnonymousContext, final Intent paramAnonymousIntent)
    {
      new Thread()
      {
        public void run()
        {
          NetworkManager.this.connectionEvent(paramAnonymousIntent.getAction());
        }
      }
      .start();
    }
  };

  private NetworkManager(Core paramCore)
  {
    this.core = paramCore;
    this.connectivityMgr = ((ConnectivityManager)AndroidFactory.getApplicationContext().getSystemService("connectivity"));
  }

  private void connectionEvent(String paramString)
  {
    try
    {
      if (paramString.equals("android.net.conn.CONNECTIVITY_CHANGE"))
      {
        NetworkInfo localNetworkInfo = this.connectivityMgr.getActiveNetworkInfo();
        if (localNetworkInfo != null);
      }
      return;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }

  public static void createInstance(Core paramCore)
  {
    try
    {
      if (instance == null)
        instance = new NetworkManager(paramCore);
      return;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }

  public static NetworkManager getInstance()
  {
    return instance;
  }

  public void start()
  {
    try
    {
      IntentFilter localIntentFilter = new IntentFilter();
      localIntentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
      AndroidFactory.getApplicationContext().registerReceiver(this.networkStateListener, localIntentFilter);
      return;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }

  public void stop()
  {
    try
    {
      AndroidFactory.getApplicationContext().unregisterReceiver(this.networkStateListener);
      return;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.download.qtradiodownload.core.NetworkManager
 * JD-Core Version:    0.6.2
 */