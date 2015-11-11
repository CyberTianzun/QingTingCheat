package fm.qingting.qtradio.notification;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import fm.qingting.qtradio.NotificationService;

public class ConnectivityReceiver extends BroadcastReceiver
{
  private NotificationService notificationService;

  public ConnectivityReceiver(NotificationService paramNotificationService)
  {
    this.notificationService = paramNotificationService;
  }

  public void onReceive(Context paramContext, Intent paramIntent)
  {
    NetworkInfo localNetworkInfo = ((ConnectivityManager)paramContext.getSystemService("connectivity")).getActiveNetworkInfo();
    if (localNetworkInfo != null)
    {
      Log.d("NetworkState", "Network Type  = " + localNetworkInfo.getTypeName());
      Log.d("NetworkState", "Network State = " + localNetworkInfo.getState());
      if (localNetworkInfo.isConnected());
      return;
    }
    Log.e("NetworkState", "Network unavailable");
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.qtradio.notification.ConnectivityReceiver
 * JD-Core Version:    0.6.2
 */