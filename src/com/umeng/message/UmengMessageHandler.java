package com.umeng.message;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build.VERSION;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.support.v4.app.NotificationCompat.BigTextStyle;
import android.support.v4.app.NotificationCompat.Builder;
import android.text.TextUtils;
import com.umeng.common.message.Log;
import com.umeng.common.message.c;
import com.umeng.message.entity.UMessage;
import com.umeng.message.proguard.A;
import java.io.File;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import org.json.JSONObject;

public class UmengMessageHandler
  implements UHandler
{
  private static int a = 0;
  private static final String b = UmengMessageHandler.class.getName();
  private static Date c;
  private static final String d = "umeng_push_notification_default_large_icon";
  private static final String e = "umeng_push_notification_default_small_icon";
  private static final String f = "umeng_push_notification_default_sound";

  private void a(Context paramContext)
  {
    try
    {
      PowerManager localPowerManager = (PowerManager)paramContext.getSystemService("power");
      if (Build.VERSION.SDK_INT >= 7);
      for (boolean bool = new UmengMessageHandler.1(this, localPowerManager).a(); ; bool = false)
      {
        Log.c(b, "screen on................................." + bool);
        if (bool)
          break;
        localPowerManager.newWakeLock(805306374, "MyLock").acquire(10000L);
        return;
        Log.c(b, "android os version < 7, skip checking screen on status");
      }
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
  }

  private void a(Context paramContext, Notification paramNotification)
  {
    try
    {
      NotificationManager localNotificationManager = (NotificationManager)paramContext.getSystemService("notification");
      int i = 20100401;
      try
      {
        if (!PushAgent.getInstance(paramContext).getMergeNotificaiton())
        {
          int j = new Random().nextInt();
          i = j;
        }
        while (true)
        {
          localNotificationManager.notify(i, paramNotification);
          return;
          localNotificationManager.cancel(i);
        }
      }
      catch (Exception localException2)
      {
        while (true)
          localException2.printStackTrace();
      }
    }
    catch (Exception localException1)
    {
      localException1.printStackTrace();
    }
  }

  private boolean a(Context paramContext, NotificationCompat.Builder paramBuilder, UMessage paramUMessage)
  {
    int i = getSmallIconId(paramContext, paramUMessage);
    Bitmap localBitmap = getLargeIcon(paramContext, paramUMessage);
    if (i < 0)
      return false;
    paramBuilder.setSmallIcon(i);
    if (localBitmap != null)
      paramBuilder.setLargeIcon(localBitmap);
    return true;
  }

  public void dealWithCustomMessage(Context paramContext, UMessage paramUMessage)
  {
  }

  public void dealWithNotificationMessage(Context paramContext, UMessage paramUMessage)
  {
    Log.c(b, "notify: " + paramUMessage.getRaw().toString());
    if ((paramUMessage.hasResourceFromInternet()) && (!MessageSharedPrefs.getInstance(paramContext).b(paramUMessage.msg_id)) && (startDownloadResourceService(paramContext, paramUMessage)));
    NotificationCompat.Builder localBuilder;
    do
    {
      int j;
      do
      {
        return;
        MessageSharedPrefs.getInstance(paramContext).d(paramUMessage.msg_id);
        localNotification = getNotification(paramContext, paramUMessage);
        if ((localNotification == null) || (localNotification.icon != 0))
          break;
        j = getSmallIconId(paramContext, paramUMessage);
      }
      while (j <= 0);
      localNotification.icon = j;
      if (localNotification != null)
        break;
      localBuilder = new NotificationCompat.Builder(paramContext);
    }
    while (!a(paramContext, localBuilder, paramUMessage));
    localBuilder.setContentTitle(paramUMessage.title).setContentText(paramUMessage.text).setTicker(paramUMessage.ticker).setAutoCancel(true);
    NotificationCompat.BigTextStyle localBigTextStyle = new NotificationCompat.BigTextStyle();
    localBigTextStyle.setBigContentTitle(paramUMessage.title);
    localBigTextStyle.bigText(paramUMessage.text);
    localBuilder.setStyle(localBigTextStyle);
    Notification localNotification = localBuilder.build();
    PendingIntent localPendingIntent = getClickPendingIntent(paramContext, paramUMessage);
    localNotification.deleteIntent = getDismissPendingIntent(paramContext, paramUMessage);
    localNotification.contentIntent = localPendingIntent;
    int i = getNotificationDefaults(paramContext, paramUMessage);
    if ((i & 0x1) != 0)
    {
      Uri localUri = getSound(paramContext, paramUMessage);
      if (localUri != null)
        localNotification.sound = getSound(paramContext, paramUMessage);
      if (localUri != null)
        i ^= 1;
    }
    localNotification.defaults = i;
    a(paramContext, localNotification);
  }

  public PendingIntent getClickPendingIntent(Context paramContext, UMessage paramUMessage)
  {
    Intent localIntent = new Intent();
    localIntent.setClass(paramContext, NotificationProxyBroadcastReceiver.class);
    localIntent.putExtra("MSG", paramUMessage.getRaw().toString());
    localIntent.putExtra("ACTION", 10);
    return PendingIntent.getBroadcast(paramContext, (int)System.currentTimeMillis(), localIntent, 268435456);
  }

  public PendingIntent getDismissPendingIntent(Context paramContext, UMessage paramUMessage)
  {
    Intent localIntent = new Intent();
    localIntent.setClass(paramContext, NotificationProxyBroadcastReceiver.class);
    localIntent.putExtra("MSG", paramUMessage.getRaw().toString());
    localIntent.putExtra("ACTION", 11);
    return PendingIntent.getBroadcast(paramContext, (int)(1L + System.currentTimeMillis()), localIntent, 268435456);
  }

  public Bitmap getLargeIcon(Context paramContext, UMessage paramUMessage)
  {
    while (true)
    {
      try
      {
        if (!paramUMessage.isLargeIconFromInternet())
          break label170;
        localBitmap1 = BitmapFactory.decodeFile(UmengDownloadResourceService.getMessageResourceFolder(paramContext, paramUMessage) + paramUMessage.img.hashCode());
        if (localBitmap1 == null)
        {
          int k = -1;
          if (!TextUtils.isEmpty(paramUMessage.largeIcon))
            k = c.a(paramContext).c(paramUMessage.largeIcon);
          if (k < 0)
            k = c.a(paramContext).c("umeng_push_notification_default_large_icon");
          if (k > 0)
          {
            localBitmap2 = BitmapFactory.decodeResource(paramContext.getResources(), k);
            if (localBitmap2 == null)
              break;
            if (Build.VERSION.SDK_INT >= 11)
            {
              j = (int)paramContext.getResources().getDimension(17104902);
              return Bitmap.createScaledBitmap(localBitmap2, j, j, true);
            }
            int i = A.a(a);
            int j = i;
            continue;
          }
        }
      }
      catch (Exception localException)
      {
        localException.printStackTrace();
        return null;
      }
      Bitmap localBitmap2 = localBitmap1;
      continue;
      label170: Bitmap localBitmap1 = null;
    }
    return null;
  }

  public Notification getNotification(Context paramContext, UMessage paramUMessage)
  {
    return null;
  }

  public int getNotificationDefaults(Context paramContext, UMessage paramUMessage)
  {
    boolean bool1 = isInNoDisturbTime(paramContext);
    int i = 0;
    if (bool1);
    do
    {
      boolean bool3;
      do
      {
        return i;
        if (c == null)
          break;
        bool3 = Calendar.getInstance().getTimeInMillis() - c.getTime() < 60000L;
        i = 0;
      }
      while (bool3);
      boolean bool2 = paramUMessage.play_vibrate;
      i = 0;
      if (bool2)
        i = 2;
      if (paramUMessage.play_lights)
        i |= 4;
      if (paramUMessage.play_sound)
        i |= 1;
      c = Calendar.getInstance().getTime();
    }
    while (!paramUMessage.screen_on);
    a(paramContext);
    return i;
  }

  // ERROR //
  public int getSmallIconId(Context paramContext, UMessage paramUMessage)
  {
    // Byte code:
    //   0: iconst_m1
    //   1: istore_3
    //   2: aload_2
    //   3: getfield 414	com/umeng/message/entity/UMessage:icon	Ljava/lang/String;
    //   6: invokestatic 339	android/text/TextUtils:isEmpty	(Ljava/lang/CharSequence;)Z
    //   9: ifne +15 -> 24
    //   12: aload_1
    //   13: invokestatic 344	com/umeng/common/message/c:a	(Landroid/content/Context;)Lcom/umeng/common/message/c;
    //   16: aload_2
    //   17: getfield 414	com/umeng/message/entity/UMessage:icon	Ljava/lang/String;
    //   20: invokevirtual 347	com/umeng/common/message/c:c	(Ljava/lang/String;)I
    //   23: istore_3
    //   24: iload_3
    //   25: ifge +13 -> 38
    //   28: aload_1
    //   29: invokestatic 344	com/umeng/common/message/c:a	(Landroid/content/Context;)Lcom/umeng/common/message/c;
    //   32: ldc 19
    //   34: invokevirtual 347	com/umeng/common/message/c:c	(Ljava/lang/String;)I
    //   37: istore_3
    //   38: iload_3
    //   39: ifge +71 -> 110
    //   42: getstatic 34	com/umeng/message/UmengMessageHandler:b	Ljava/lang/String;
    //   45: ldc_w 416
    //   48: invokestatic 83	com/umeng/common/message/Log:c	(Ljava/lang/String;Ljava/lang/String;)V
    //   51: aload_1
    //   52: invokevirtual 420	android/content/Context:getPackageManager	()Landroid/content/pm/PackageManager;
    //   55: aload_1
    //   56: invokevirtual 423	android/content/Context:getPackageName	()Ljava/lang/String;
    //   59: iconst_0
    //   60: invokevirtual 429	android/content/pm/PackageManager:getPackageInfo	(Ljava/lang/String;I)Landroid/content/pm/PackageInfo;
    //   63: getfield 435	android/content/pm/PackageInfo:applicationInfo	Landroid/content/pm/ApplicationInfo;
    //   66: getfield 438	android/content/pm/ApplicationInfo:icon	I
    //   69: istore 5
    //   71: iload 5
    //   73: ifge +12 -> 85
    //   76: getstatic 34	com/umeng/message/UmengMessageHandler:b	Ljava/lang/String;
    //   79: ldc_w 440
    //   82: invokestatic 442	com/umeng/common/message/Log:b	(Ljava/lang/String;Ljava/lang/String;)V
    //   85: iload 5
    //   87: ireturn
    //   88: astore 4
    //   90: iload_3
    //   91: istore 5
    //   93: aload 4
    //   95: astore 6
    //   97: aload 6
    //   99: invokevirtual 103	java/lang/Exception:printStackTrace	()V
    //   102: iload 5
    //   104: ireturn
    //   105: astore 6
    //   107: goto -10 -> 97
    //   110: iload_3
    //   111: istore 5
    //   113: goto -42 -> 71
    //
    // Exception table:
    //   from	to	target	type
    //   2	24	88	java/lang/Exception
    //   28	38	88	java/lang/Exception
    //   42	71	88	java/lang/Exception
    //   76	85	105	java/lang/Exception
  }

  public Uri getSound(Context paramContext, UMessage paramUMessage)
  {
    while (true)
    {
      try
      {
        if (paramUMessage.isSoundFromInternet())
        {
          str = UmengDownloadResourceService.getMessageResourceFolder(paramContext, paramUMessage) + paramUMessage.sound.hashCode();
          if (!new File(str).exists())
            str = null;
          if (str == null)
          {
            int i = -1;
            if (!TextUtils.isEmpty(paramUMessage.sound))
              i = c.a(paramContext).h(paramUMessage.sound);
            if (i < 0)
              i = c.a(paramContext).h("umeng_push_notification_default_sound");
            if (i > 0)
              str = "android.resource://" + paramContext.getPackageName() + "/" + i;
          }
          Object localObject = null;
          if (str != null)
          {
            Uri localUri = Uri.parse(str);
            localObject = localUri;
          }
          return localObject;
        }
      }
      catch (Throwable localThrowable)
      {
        return null;
      }
      String str = null;
    }
  }

  public void handleMessage(Context paramContext, UMessage paramUMessage)
  {
    if ("notification".equals(paramUMessage.display_type))
      dealWithNotificationMessage(paramContext, paramUMessage);
    while (!"custom".equals(paramUMessage.display_type))
      return;
    dealWithCustomMessage(paramContext, paramUMessage);
  }

  public boolean isInNoDisturbTime(Context paramContext)
  {
    int i = Calendar.getInstance().get(11);
    int j = Calendar.getInstance().get(12);
    int k;
    int m;
    label78: int n;
    if (j + i * 60 >= 60 * PushAgent.getInstance(paramContext).getNoDisturbStartHour() + PushAgent.getInstance(paramContext).getNoDisturbStartMinute())
    {
      k = 1;
      if (j + i * 60 > 60 * PushAgent.getInstance(paramContext).getNoDisturbEndHour() + PushAgent.getInstance(paramContext).getNoDisturbEndMinute())
        break label143;
      m = 1;
      if (60 * PushAgent.getInstance(paramContext).getNoDisturbEndHour() + PushAgent.getInstance(paramContext).getNoDisturbEndMinute() <= 60 * PushAgent.getInstance(paramContext).getNoDisturbStartHour() + PushAgent.getInstance(paramContext).getNoDisturbStartMinute())
        break label149;
      n = 1;
      label120: if (n == 0)
        break label157;
      if ((k == 0) || (m == 0))
        break label155;
    }
    label143: label149: label155: label157: 
    while ((k != 0) || (m != 0))
    {
      return true;
      k = 0;
      break;
      m = 0;
      break label78;
      n = 0;
      break label120;
      return false;
    }
    return false;
  }

  public boolean startDownloadResourceService(Context paramContext, UMessage paramUMessage)
  {
    try
    {
      Intent localIntent = new Intent(paramContext, UmengDownloadResourceService.class);
      localIntent.putExtra("body", paramUMessage.getRaw().toString());
      paramContext.startService(localIntent);
      return true;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return false;
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.umeng.message.UmengMessageHandler
 * JD-Core Version:    0.6.2
 */