package fm.qingting.qtradio.notification;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import fm.qingting.qtradio.NotificationService;

public class BatteryReceiver extends BroadcastReceiver
{
  public void onReceive(Context paramContext, Intent paramIntent)
  {
    if (paramIntent == null);
    do
    {
      return;
      if (paramIntent.getAction().equalsIgnoreCase("android.intent.action.BATTERY_CHANGED"))
      {
        Log.e("batteryReceiver", "recv android.intent.action.BATTERY_CHANGED");
        Intent localIntent = new Intent(paramContext, NotificationService.class);
        localIntent.setAction("fm.qingting.connectivity.change");
        paramContext.startService(localIntent);
        return;
      }
    }
    while (!paramIntent.getAction().equalsIgnoreCase("android.intent.action.USER_PRESENT"));
    Log.e("batteryReceiver", "recv android.intent.action.USER_PRESENT");
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.qtradio.notification.BatteryReceiver
 * JD-Core Version:    0.6.2
 */