package fm.qingting.qtradio.notification;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import fm.qingting.qtradio.NotificationService;

public class QTAlarmReceiver extends BroadcastReceiver
{
  public void onReceive(Context paramContext, Intent paramIntent)
  {
    if ((paramIntent == null) || (paramContext == null));
    do
    {
      do
        return;
      while (paramIntent.getAction() == null);
      if (paramIntent.getAction().equalsIgnoreCase("fm.qingting.alarmintent"))
      {
        Intent localIntent1 = new Intent(paramContext, NotificationService.class);
        localIntent1.setAction("fm.qingting.alarmintent");
        paramContext.startService(localIntent1);
        return;
      }
      if (paramIntent.getAction().equalsIgnoreCase("fm.qingting.reserveintent"))
      {
        Intent localIntent2 = new Intent(paramContext, NotificationService.class);
        localIntent2.setAction("fm.qingting.reserveintent");
        paramContext.startService(localIntent2);
        return;
      }
      if (paramIntent.getAction().equalsIgnoreCase("fm.qingting.notifyintent"))
      {
        Intent localIntent3 = new Intent(paramContext, NotificationService.class);
        localIntent3.setAction("fm.qingting.notifyintent");
        paramContext.startService(localIntent3);
        return;
      }
      if (paramIntent.getAction().equalsIgnoreCase("fm.qingting.protectintent"))
      {
        Intent localIntent4 = new Intent(paramContext, NotificationService.class);
        localIntent4.setAction("fm.qingting.protectintent");
        paramContext.startService(localIntent4);
        return;
      }
      if (paramIntent.getAction().equalsIgnoreCase("fm.qingting.killintent"))
      {
        Intent localIntent5 = new Intent(paramContext, NotificationService.class);
        localIntent5.setAction("fm.qingting.killintent");
        paramContext.startService(localIntent5);
        return;
      }
      if (paramIntent.getAction().equalsIgnoreCase("fm.qingting.start"))
      {
        Log.e("qtalarm", "recv start");
        Intent localIntent7 = new Intent(paramContext, NotificationService.class);
        localIntent7.setAction("fm.qingting.start");
        paramContext.startService(localIntent7);
        return;
      }
    }
    while (!paramIntent.getAction().equalsIgnoreCase("fm.qingting.quit"));
    Intent localIntent6 = new Intent(paramContext, NotificationService.class);
    localIntent6.setAction("fm.qingting.quit");
    paramContext.startService(localIntent6);
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.qtradio.notification.QTAlarmReceiver
 * JD-Core Version:    0.6.2
 */