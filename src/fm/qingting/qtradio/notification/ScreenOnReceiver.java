package fm.qingting.qtradio.notification;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import fm.qingting.qtradio.NotificationService;

public class ScreenOnReceiver extends BroadcastReceiver
{
  public void onReceive(Context paramContext, Intent paramIntent)
  {
    if (paramIntent == null);
    while (!paramIntent.getAction().equalsIgnoreCase("android.net.conn.CONNECTIVITY_CHANGE"))
      return;
    Intent localIntent = new Intent(paramContext, NotificationService.class);
    localIntent.setAction("fm.qingting.connectivity.change");
    paramContext.startService(localIntent);
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.qtradio.notification.ScreenOnReceiver
 * JD-Core Version:    0.6.2
 */