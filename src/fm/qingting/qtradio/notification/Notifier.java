package fm.qingting.qtradio.notification;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat.Builder;
import fm.qingting.qtradio.QTRadioActivity;
import java.util.Random;

public class Notifier
{
  private Context context;
  private final boolean mNewStyle = false;
  private NotificationManager notificationManager;
  private Random random;

  public Notifier(Context paramContext)
  {
    this.context = paramContext;
    this.notificationManager = ((NotificationManager)paramContext.getSystemService("notification"));
    this.random = new Random(System.currentTimeMillis());
  }

  private PendingIntent getImmediatePlayIntent(Intent paramIntent)
  {
    return PendingIntent.getBroadcast(this.context, 0, paramIntent, 134217728);
  }

  public static int getNotificationIcon()
  {
    return 2130837871;
  }

  @TargetApi(16)
  public void notify(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6, String paramString7, int paramInt1, String paramString8, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, String paramString9, String paramString10)
  {
    Intent localIntent = new Intent(this.context, QTRadioActivity.class);
    localIntent.putExtra("NOTIFICATION_ID", paramString1);
    localIntent.putExtra("NOTIFICATION_API_KEY", paramString2);
    localIntent.putExtra("NOTIFICATION_TITLE", paramString3);
    localIntent.putExtra("NOTIFICATION_MESSAGE", paramString4);
    localIntent.putExtra("NOTIFICATION_URI", paramString5);
    localIntent.putExtra("channelname", paramString7);
    localIntent.putExtra("channelid", paramInt1);
    localIntent.putExtra("push_task_id", paramString9);
    localIntent.putExtra("push_tag_id", paramString10);
    localIntent.putExtra("categoryid", paramInt2);
    localIntent.putExtra("programid", paramInt3);
    localIntent.putExtra("parentid", paramInt4);
    localIntent.putExtra("contentType", paramInt5);
    localIntent.putExtra("alarmType", paramInt6);
    localIntent.putExtra("notify_type", paramString8);
    localIntent.setFlags(268435456);
    localIntent.setFlags(536870912);
    localIntent.setFlags(67108864);
    PendingIntent localPendingIntent = PendingIntent.getActivity(this.context, this.random.nextInt(), localIntent, 134217728);
    NotificationCompat.Builder localBuilder = new NotificationCompat.Builder(this.context);
    localBuilder.setSmallIcon(getNotificationIcon());
    localBuilder.setTicker(paramString4);
    localBuilder.setWhen(System.currentTimeMillis());
    localBuilder.setAutoCancel(true);
    localBuilder.setDefaults(1);
    localBuilder.setContentTitle(paramString3);
    localBuilder.setContentText(paramString4);
    localBuilder.setContentIntent(localPendingIntent);
    Notification localNotification = localBuilder.build();
    this.notificationManager.cancelAll();
    this.notificationManager.notify(this.random.nextInt(), localNotification);
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.qtradio.notification.Notifier
 * JD-Core Version:    0.6.2
 */