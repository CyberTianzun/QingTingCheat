package com.talkingdata.pingan.sdk;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.telephony.CellLocation;
import android.telephony.TelephonyManager;
import android.telephony.cdma.CdmaCellLocation;
import android.telephony.gsm.GsmCellLocation;
import android.text.TextUtils;
import android.util.Log;
import java.io.Closeable;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TimeZone;
import java.util.UUID;

public final class PAAgent
{
  private static final int A = 5000;
  private static final int B = 0;
  private static final int C = 1;
  private static final int D = 2;
  public static final boolean DEBUG = false;
  private static final int E = 3;
  private static final int F = 4;
  private static final int G = 5;
  private static volatile boolean H = false;
  private static String I;
  private static boolean J = false;
  private static Context K;
  private static String L;
  public static boolean LOG_ON = false;
  private static String M;
  private static long N = 0L;
  private static boolean O = false;
  private static boolean P = false;
  protected static final int a = 300000;
  protected static final int b = 6;
  protected static final int c = 7;
  protected static final int d = 8;
  static Long[][] e;
  static boolean f = false;
  static final String g = "pinganLog";
  static boolean h = false;
  static boolean i = false;
  private static final String j = "+V1.0.16";
  private static final String k = "Android+pingan+V1.0.16";
  private static final String l = "pinganpref.profile.key";
  private static final String m = "pinganpref.session.key";
  private static final String n = "pinganpref.lastactivity.key";
  private static final String o = "pinganpref.start.key";
  private static final String p = "pinganpref.init.key";
  private static final String q = "pinganpref.actstart.key";
  private static final String r = "pinganpref.end.key";
  private static final String s = "pinganpref.apps_send_time.key";
  private static final String t = "pinganpref.ip";
  private static final String u = "pingan_APP_ID";
  private static final String v = "pingan_CHANNEL_ID";
  private static final String w = "pref_longtime";
  private static final String x = "pref_shorttime";
  private static final long y = 30000L;
  private static final int z;

  static
  {
    H = false;
    I = "TalkingData";
    J = false;
    O = false;
    P = false;
    o();
  }

  private static ak a(Context paramContext)
  {
    ak localak = new ak();
    if ((aa.a(paramContext, "android.permission.ACCESS_COARSE_LOCATION")) || (aa.a(paramContext, "android.permission.ACCESS_FINE_LOCATION")))
      try
      {
        CellLocation localCellLocation = ((TelephonyManager)paramContext.getSystemService("phone")).getCellLocation();
        if ((localCellLocation instanceof GsmCellLocation))
        {
          GsmCellLocation localGsmCellLocation = (GsmCellLocation)localCellLocation;
          if (localGsmCellLocation != null)
          {
            localak.d = localGsmCellLocation.getCid();
            localak.e = localGsmCellLocation.getLac();
            localak.c = "gsm";
            if (Build.VERSION.SDK_INT >= 9)
            {
              localak.c += localGsmCellLocation.getPsc();
              return localak;
            }
          }
        }
        else if ((localCellLocation instanceof CdmaCellLocation))
        {
          CdmaCellLocation localCdmaCellLocation = (CdmaCellLocation)localCellLocation;
          if (localCdmaCellLocation != null)
          {
            localak.d = localCdmaCellLocation.getBaseStationId();
            localak.e = localCdmaCellLocation.getNetworkId();
            localak.c = ("cdma:" + localCdmaCellLocation.getSystemId() + ':' + localCdmaCellLocation.getBaseStationLatitude() + ':' + localCdmaCellLocation.getBaseStationLongitude());
            return localak;
          }
        }
      }
      catch (Exception localException)
      {
      }
    return localak;
  }

  private static String a(Bundle paramBundle, String paramString)
  {
    Iterator localIterator = paramBundle.keySet().iterator();
    while (localIterator.hasNext())
      if (((String)localIterator.next()).equalsIgnoreCase(paramString))
        return String.valueOf(paramBundle.get(paramString));
    return "";
  }

  private static String a(Throwable paramThrowable)
  {
    int i1 = 50;
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append(paramThrowable.toString());
    localStringBuilder.append("\r\n");
    StackTraceElement[] arrayOfStackTraceElement = paramThrowable.getStackTrace();
    if (arrayOfStackTraceElement.length > i1);
    while (true)
    {
      for (int i2 = 0; i2 < i1; i2++)
        localStringBuilder.append("\t" + arrayOfStackTraceElement[i2] + "\r\n");
      i1 = arrayOfStackTraceElement.length;
    }
    Throwable localThrowable = paramThrowable.getCause();
    if (localThrowable != null)
      a(localStringBuilder, arrayOfStackTraceElement, localThrowable, 1);
    return localStringBuilder.toString();
  }

  static void a()
  {
    s();
    e = (Long[][])null;
  }

  public static void a(int paramInt, long paramLong)
  {
    Handler localHandler = d.a();
    Message localMessage = Message.obtain(localHandler, paramInt);
    localHandler.removeMessages(paramInt);
    localHandler.sendMessageDelayed(localMessage, paramLong);
  }

  static void a(long paramLong)
  {
    c("pinganpref.start.key", paramLong);
  }

  private static void a(long paramLong, String paramString)
  {
    long l1 = 0L;
    e(M);
    M = UUID.randomUUID().toString();
    long l2 = k();
    long l3 = paramLong - l2;
    if (l1 == l2)
      if ((K == null) || (!b.b(K)))
        break label88;
    label88: for (int i1 = 1; ; i1 = -1)
    {
      a(M);
      a(paramLong);
      e.a(M, paramLong, l1, i1);
      a(paramLong, paramString, "");
      return;
      l1 = l3;
      break;
    }
  }

  private static void a(long paramLong, String paramString1, String paramString2)
  {
    if (paramString1 != null)
    {
      c(paramLong);
      b(paramString1);
      N = e.a(M, paramString1, paramLong, 0, paramString2, SystemClock.elapsedRealtime());
      d.a().removeMessages(7);
    }
    a(6, 0L);
  }

  private static void a(Activity paramActivity, String paramString, int paramInt)
  {
    ah.a(new y(paramInt, paramString, paramActivity));
  }

  static void a(Message paramMessage)
  {
    int i1 = 1;
    try
    {
      if (paramMessage.what != 8)
      {
        h = true;
        e.a(c());
        switch (paramMessage.what)
        {
        case 0:
        case 1:
        case 3:
        case 4:
        case 5:
        case 6:
        case 8:
        case 7:
        case 2:
        }
      }
      while (true)
      {
        e.b();
        if (i2 == 0)
          break label284;
        d.c();
        h = false;
        if (i1 != 0)
          break label284;
        q.a(new String[] { "Schedule next loop send." });
        a(8, 300000L);
        return;
        if ((h) || (i))
          break;
        a(8, 300000L);
        return;
        b(null);
        int i2 = i1;
        i1 = 0;
        continue;
        b(paramMessage);
        i1 = 0;
        i2 = 0;
        continue;
        c(paramMessage);
        i1 = 0;
        i2 = 0;
        continue;
        d(paramMessage);
        i1 = 0;
        i2 = 0;
        continue;
        a locala = (a)paramMessage.obj;
        e.a(locala.c, locala.d);
        d(locala.c);
        i1 = 0;
        i2 = 0;
        continue;
        q.a(new String[] { "Send data at resume" });
        i2 = i1;
        i1 = 0;
        continue;
        q.a(new String[] { "Send data at loop" });
        i2 = i1;
        i1 = 0;
        continue;
        q.a(new String[] { "Send data at pause" });
        i2 = i1;
        continue;
        i1 = 0;
        i2 = 0;
      }
      label284: return;
    }
    catch (Throwable localThrowable)
    {
    }
  }

  static void a(Closeable paramCloseable)
  {
    if (paramCloseable != null);
    try
    {
      paramCloseable.close();
      return;
    }
    catch (Throwable localThrowable)
    {
    }
  }

  static void a(String paramString)
  {
    c("pinganpref.session.key", paramString);
  }

  private static void a(String paramString, long paramLong)
  {
    K.getSharedPreferences("pref_shorttime", 0).edit().putLong(paramString, paramLong).commit();
  }

  private static void a(String paramString1, String paramString2)
  {
    K.getSharedPreferences("pref_shorttime", 0).edit().putString(paramString1, paramString2).commit();
  }

  private static void a(StringBuilder paramStringBuilder, StackTraceElement[] paramArrayOfStackTraceElement, Throwable paramThrowable, int paramInt)
  {
    int i1 = 50;
    StackTraceElement[] arrayOfStackTraceElement = paramThrowable.getStackTrace();
    int i2 = -1 + arrayOfStackTraceElement.length;
    int i3 = -1 + paramArrayOfStackTraceElement.length;
    int i4 = i2;
    int i5 = i3;
    while ((i4 >= 0) && (i5 >= 0) && (arrayOfStackTraceElement[i4].equals(paramArrayOfStackTraceElement[i5])))
    {
      int i7 = i4 - 1;
      i5--;
      i4 = i7;
    }
    if (i4 > i1);
    while (true)
    {
      paramStringBuilder.append("Caused by : " + paramThrowable + "\r\n");
      for (int i6 = 0; i6 <= i1; i6++)
        paramStringBuilder.append("\t" + arrayOfStackTraceElement[i6] + "\r\n");
      i1 = i4;
    }
    if (paramInt >= 5);
    while (paramThrowable.getCause() == null)
      return;
    a(paramStringBuilder, arrayOfStackTraceElement, paramThrowable, paramInt + 1);
  }

  public static void a(boolean paramBoolean)
  {
    f = paramBoolean;
  }

  public static int b()
  {
    long l1 = d("pinganpref.apps_send_time.key", 0L);
    Calendar localCalendar = Calendar.getInstance();
    int i1 = 100 * localCalendar.get(6) + localCalendar.get(11);
    if (Math.abs(l1 / 100L - i1 / 100) >= 7L)
      return 2;
    if (l1 != i1)
      return 1;
    return 0;
  }

  private static long b(String paramString, long paramLong)
  {
    return K.getSharedPreferences("pref_shorttime", 0).getLong(paramString, paramLong);
  }

  private static String b(String paramString1, String paramString2)
  {
    return K.getSharedPreferences("pref_shorttime", 0).getString(paramString1, paramString2);
  }

  static void b(long paramLong)
  {
    c("pinganpref.init.key", paramLong);
  }

  private static void b(Message paramMessage)
  {
    q.a(new String[] { "api on resume" });
    long l1 = System.currentTimeMillis();
    long l2 = h();
    long l3 = k();
    long l4;
    if (l3 > l2)
    {
      l4 = l3;
      if (paramMessage != null)
        break label81;
    }
    label81: for (String str = null; ; str = (String)paramMessage.obj)
    {
      if (l1 - l4 <= 30000L)
        break label93;
      q.a(new String[] { "new launch..." });
      a(l1, str);
      return;
      l4 = l2;
      break;
    }
    label93: q.a(new String[] { "session continue..." });
    a(l1, str, g());
  }

  static void b(String paramString)
  {
    a("pinganpref.lastactivity.key", paramString);
  }

  static void b(boolean paramBoolean)
  {
    if (paramBoolean);
    for (long l1 = 1L; ; l1 = 0L)
    {
      c("pinganpref.profile.key", l1);
      return;
    }
  }

  protected static Context c()
  {
    return K;
  }

  static void c(long paramLong)
  {
    a("pinganpref.actstart.key", paramLong);
  }

  private static void c(Message paramMessage)
  {
    q.a(new String[] { "api on pause" });
    long l1 = System.currentTimeMillis();
    if (N != -1L)
      e.a(N, SystemClock.elapsedRealtime());
    d(l1);
    d.a().removeMessages(8);
    a(7, 5000L);
  }

  static void c(String paramString)
  {
    c("pinganpref.ip", paramString);
  }

  private static void c(String paramString, long paramLong)
  {
    K.getSharedPreferences("pref_longtime", 0).edit().putLong(paramString, paramLong).commit();
  }

  private static void c(String paramString1, String paramString2)
  {
    K.getSharedPreferences("pref_longtime", 0).edit().putString(paramString1, paramString2).commit();
  }

  private static long d(String paramString, long paramLong)
  {
    return K.getSharedPreferences("pref_longtime", 0).getLong(paramString, paramLong);
  }

  public static String d()
  {
    return L;
  }

  private static String d(String paramString1, String paramString2)
  {
    return K.getSharedPreferences("pref_longtime", 0).getString(paramString1, paramString2);
  }

  static void d(long paramLong)
  {
    a("pinganpref.end.key", paramLong);
  }

  private static void d(Message paramMessage)
  {
    if (TextUtils.isEmpty(M))
    {
      q.a(new String[] { "Not Found Session Id" });
      return;
    }
    a locala = (a)paramMessage.obj;
    e.a(M, locala.a, locala.b, locala.f, locala.g);
  }

  private static void e(String paramString)
  {
    long l1 = h();
    long l2 = k();
    if (!TextUtils.isEmpty(paramString))
    {
      long l3 = l2 - l1;
      if (l3 < 500L)
        l3 = -1000L;
      e.a(paramString, (int)l3 / 1000);
    }
  }

  static boolean e()
  {
    long l1 = d("pinganpref.profile.key", 1L);
    String[] arrayOfString = new String[1];
    arrayOfString[0] = ("need Post Init:" + l1);
    q.a(arrayOfString);
    return l1 != 0L;
  }

  static String f()
  {
    return d("pinganpref.session.key", null);
  }

  static String g()
  {
    return b("pinganpref.lastactivity.key", "");
  }

  public static String getDeviceId(Context paramContext)
  {
    try
    {
      String str = a.b(paramContext);
      return str;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }

  static long h()
  {
    return d("pinganpref.start.key", 0L);
  }

  static long i()
  {
    return d("pinganpref.init.key", 0L);
  }

  public static void init(Context paramContext)
  {
    if (!H)
      K = paramContext.getApplicationContext();
    while (true)
    {
      String str2;
      try
      {
        Bundle localBundle = K.getPackageManager().getApplicationInfo(K.getPackageName(), 128).metaData;
        String str1 = a(localBundle, "pingan_APP_ID");
        str2 = a(localBundle, "pingan_CHANNEL_ID");
        if (TextUtils.isEmpty(str1))
        {
          if (!LOG_ON)
            break label200;
          Log.e("pinganLog", "pingan_APP_ID not found in AndroidManifest.xml!");
          return;
        }
        if (LOG_ON)
          Log.i("pinganLog", "pingan_APP_ID in AndroidManifest.xml is:" + str1 + ".");
        if (!LOG_ON)
          break label201;
        Log.i("pinganLog", "pingan_CHANNEL_ID in AndroidManifest.xml is:" + str2 + ".");
        break label201;
        init(paramContext, str1, str2);
        return;
      }
      catch (Throwable localThrowable)
      {
        if (!LOG_ON)
          break label200;
      }
      Log.e("pinganLog", "Failed to load meta-data", localThrowable);
      return;
      Handler localHandler = d.a();
      localHandler.sendMessage(Message.obtain(localHandler, 0));
      label200: return;
      label201: if (str2 == null)
        str2 = "TalkingData";
    }
  }

  // ERROR //
  public static void init(Context paramContext, String paramString1, String paramString2)
  {
    // Byte code:
    //   0: ldc 2
    //   2: monitorenter
    //   3: getstatic 105	com/talkingdata/pingan/sdk/PAAgent:H	Z
    //   6: ifne +81 -> 87
    //   9: aload_0
    //   10: invokevirtual 572	android/content/Context:getApplicationContext	()Landroid/content/Context;
    //   13: putstatic 323	com/talkingdata/pingan/sdk/PAAgent:K	Landroid/content/Context;
    //   16: getstatic 103	com/talkingdata/pingan/sdk/PAAgent:LOG_ON	Z
    //   19: ifeq +39 -> 58
    //   22: ldc 48
    //   24: new 177	java/lang/StringBuilder
    //   27: dup
    //   28: invokespecial 178	java/lang/StringBuilder:<init>	()V
    //   31: ldc_w 622
    //   34: invokevirtual 182	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   37: aload_1
    //   38: invokevirtual 182	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   41: ldc_w 624
    //   44: invokevirtual 182	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   47: aload_2
    //   48: invokevirtual 182	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   51: invokevirtual 192	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   54: invokestatic 606	android/util/Log:i	(Ljava/lang/String;Ljava/lang/String;)I
    //   57: pop
    //   58: aload_0
    //   59: ldc_w 626
    //   62: invokestatic 137	com/talkingdata/pingan/sdk/aa:a	(Landroid/content/Context;Ljava/lang/String;)Z
    //   65: ifne +26 -> 91
    //   68: getstatic 103	com/talkingdata/pingan/sdk/PAAgent:LOG_ON	Z
    //   71: ifeq +12 -> 83
    //   74: ldc 48
    //   76: ldc_w 628
    //   79: invokestatic 600	android/util/Log:e	(Ljava/lang/String;Ljava/lang/String;)I
    //   82: pop
    //   83: iconst_1
    //   84: putstatic 105	com/talkingdata/pingan/sdk/PAAgent:H	Z
    //   87: ldc 2
    //   89: monitorexit
    //   90: return
    //   91: aload_1
    //   92: putstatic 525	com/talkingdata/pingan/sdk/PAAgent:L	Ljava/lang/String;
    //   95: aload_2
    //   96: putstatic 109	com/talkingdata/pingan/sdk/PAAgent:I	Ljava/lang/String;
    //   99: iconst_1
    //   100: anewarray 239	java/lang/String
    //   103: astore 4
    //   105: aload 4
    //   107: iconst_0
    //   108: new 177	java/lang/StringBuilder
    //   111: dup
    //   112: invokespecial 178	java/lang/StringBuilder:<init>	()V
    //   115: ldc_w 630
    //   118: invokevirtual 182	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   121: getstatic 175	android/os/Build$VERSION:SDK_INT	I
    //   124: invokevirtual 188	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
    //   127: invokevirtual 192	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   130: aastore
    //   131: aload 4
    //   133: invokestatic 388	com/talkingdata/pingan/sdk/q:a	([Ljava/lang/String;)V
    //   136: getstatic 175	android/os/Build$VERSION:SDK_INT	I
    //   139: bipush 14
    //   141: if_icmplt +84 -> 225
    //   144: aload_0
    //   145: instanceof 632
    //   148: istore 5
    //   150: iload 5
    //   152: ifeq +73 -> 225
    //   155: aload_0
    //   156: checkcast 632	android/app/Activity
    //   159: invokevirtual 636	android/app/Activity:getApplication	()Landroid/app/Application;
    //   162: astore 7
    //   164: ldc_w 638
    //   167: invokevirtual 643	java/lang/Class:getCanonicalName	()Ljava/lang/String;
    //   170: invokestatic 647	java/lang/Class:forName	(Ljava/lang/String;)Ljava/lang/Class;
    //   173: astore 8
    //   175: ldc_w 649
    //   178: invokestatic 647	java/lang/Class:forName	(Ljava/lang/String;)Ljava/lang/Class;
    //   181: astore 9
    //   183: aload 7
    //   185: invokevirtual 653	java/lang/Object:getClass	()Ljava/lang/Class;
    //   188: ldc_w 655
    //   191: iconst_1
    //   192: anewarray 640	java/lang/Class
    //   195: dup
    //   196: iconst_0
    //   197: aload 9
    //   199: aastore
    //   200: invokevirtual 659	java/lang/Class:getMethod	(Ljava/lang/String;[Ljava/lang/Class;)Ljava/lang/reflect/Method;
    //   203: aload 7
    //   205: iconst_1
    //   206: anewarray 4	java/lang/Object
    //   209: dup
    //   210: iconst_0
    //   211: aload 8
    //   213: invokevirtual 662	java/lang/Class:newInstance	()Ljava/lang/Object;
    //   216: aastore
    //   217: invokevirtual 668	java/lang/reflect/Method:invoke	(Ljava/lang/Object;[Ljava/lang/Object;)Ljava/lang/Object;
    //   220: pop
    //   221: iconst_1
    //   222: putstatic 113	com/talkingdata/pingan/sdk/PAAgent:O	Z
    //   225: new 670	com/talkingdata/pingan/sdk/x
    //   228: dup
    //   229: aload_0
    //   230: invokespecial 672	com/talkingdata/pingan/sdk/x:<init>	(Landroid/content/Context;)V
    //   233: invokestatic 367	com/talkingdata/pingan/sdk/ah:a	(Ljava/lang/Runnable;)V
    //   236: goto -153 -> 83
    //   239: astore_3
    //   240: ldc 2
    //   242: monitorexit
    //   243: aload_3
    //   244: athrow
    //   245: astore 6
    //   247: aload 6
    //   249: invokevirtual 675	java/lang/Exception:printStackTrace	()V
    //   252: goto -27 -> 225
    //
    // Exception table:
    //   from	to	target	type
    //   3	58	239	finally
    //   58	83	239	finally
    //   83	87	239	finally
    //   91	150	239	finally
    //   155	225	239	finally
    //   225	236	239	finally
    //   247	252	239	finally
    //   155	225	245	java/lang/Exception
  }

  static long j()
  {
    return b("pinganpref.actstart.key", 0L);
  }

  static long k()
  {
    return b("pinganpref.end.key", 0L);
  }

  static String l()
  {
    return d("pinganpref.ip", null);
  }

  static h m()
  {
    if (K == null)
      return null;
    h localh = new h();
    localh.a = K.getPackageName();
    localh.b = i.d(K);
    localh.c = String.valueOf(i.c(K));
    localh.d = i();
    localh.e = "Android+pingan+V1.0.16";
    localh.f = I;
    localh.h = i.e(K);
    localh.i = i.f(K);
    return localh;
  }

  static u n()
  {
    Object localObject1 = null;
    if (K == null)
      return null;
    u localu = new u();
    localu.s = a.b(K);
    localu.a = j.c();
    localu.b = String.valueOf(j.d());
    List localList = ai.a(K);
    StringBuffer localStringBuffer = new StringBuffer();
    Iterator localIterator = localList.iterator();
    Object localObject2;
    if (localIterator.hasNext())
    {
      localObject2 = (Location)localIterator.next();
      localStringBuffer.append(((Location)localObject2).getLatitude()).append(',').append(((Location)localObject2).getLongitude()).append(',').append(((Location)localObject2).getAltitude()).append(',').append(((Location)localObject2).getTime()).append(',').append(((Location)localObject2).getAccuracy()).append(',').append(((Location)localObject2).getBearing()).append(',').append(((Location)localObject2).getSpeed()).append(',').append((short)((Location)localObject2).getProvider().hashCode()).append(':');
      if ((localObject1 != null) && (((Location)localObject2).getTime() <= localObject1.getTime()))
        break label456;
    }
    while (true)
    {
      localObject1 = localObject2;
      break;
      f localf = new f();
      if (localObject1 != null)
      {
        localf.b = localObject1.getLatitude();
        localf.a = localObject1.getLongitude();
      }
      localu.c = localf;
      localu.d = Build.CPU_ABI;
      localu.e = j.a(K);
      localu.f = j.g();
      localu.g = j.b(K);
      localu.h = j.f();
      localu.i = (TimeZone.getDefault().getRawOffset() / 1000 / 60 / 60);
      localu.j = ("Android+" + Build.VERSION.RELEASE);
      if (b.c(K));
      for (int i1 = 0; ; i1 = 1)
      {
        localu.k = i1;
        localu.l = b.d(K);
        localu.o = b.e(K);
        localu.n = b.f(K);
        localu.p = localStringBuffer.toString();
        localu.t = b.i(K);
        ak localak = a(K);
        localu.u = localak.c;
        localu.v = localak.d;
        localu.w = localak.e;
        return localu;
      }
      label456: localObject2 = localObject1;
    }
  }

  public static void o()
  {
    Thread.setDefaultUncaughtExceptionHandler(new b());
  }

  public static void onError(Context paramContext, Throwable paramThrowable)
  {
    if (paramThrowable == null)
      return;
    ah.a(new w(paramThrowable));
  }

  private static void onError(Throwable paramThrowable, boolean paramBoolean)
  {
    if (!H)
      return;
    a locala = new a();
    locala.c = System.currentTimeMillis();
    locala.d = a(paramThrowable);
    while (paramThrowable.getCause() != null)
      paramThrowable = paramThrowable.getCause();
    StackTraceElement[] arrayOfStackTraceElement = paramThrowable.getStackTrace();
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append(paramThrowable.getClass().getName()).append(":");
    for (int i1 = 0; (i1 < 3) && (i1 < arrayOfStackTraceElement.length); i1++)
      localStringBuilder.append(arrayOfStackTraceElement[i1].toString()).append(":");
    locala.e = aa.b(localStringBuilder.toString());
    if (paramBoolean)
    {
      Message localMessage = Message.obtain(d.a(), 5, locala);
      d.a().sendMessage(localMessage);
      return;
    }
    e.a(c());
    e.a(locala.c, locala.d);
    d(locala.c);
    e.b();
  }

  public static void onEvent(Context paramContext, String paramString)
  {
    onEvent(paramContext, paramString, "");
  }

  public static void onEvent(Context paramContext, String paramString1, String paramString2)
  {
    onEvent(paramContext, paramString1, paramString2, null);
  }

  public static void onEvent(Context paramContext, String paramString1, String paramString2, Map paramMap)
  {
    ah.a(new v(paramString1, paramString2, paramMap));
  }

  public static void onPageEnd(Activity paramActivity, String paramString)
  {
    if ((0x80 & paramActivity.getChangingConfigurations()) == 128)
    {
      P = true;
      return;
    }
    a(paramActivity, paramString, 3);
  }

  public static void onPageStart(Activity paramActivity, String paramString)
  {
    if (P)
      return;
    P = false;
    a(paramActivity, paramString, 1);
  }

  public static void onPause(Activity paramActivity)
  {
    if (O)
      return;
    onPageEnd(paramActivity, paramActivity.getLocalClassName());
  }

  public static void onResume(Activity paramActivity)
  {
    if (O)
      return;
    onPageStart(paramActivity, paramActivity.getLocalClassName());
  }

  protected static void onResume(Activity paramActivity, String paramString1, String paramString2)
  {
    if (TextUtils.isEmpty(paramString1))
      q.a(new String[] { "APP ID not allow empty. Please check it." });
    while (O)
      return;
    if (!H)
    {
      init(paramActivity, paramString1, paramString2);
      if (!H)
      {
        q.a(new String[] { "SDK not initialized. TCAgent.onResume()" });
        return;
      }
    }
    onPageStart(paramActivity, paramActivity.getLocalClassName());
  }

  private static void s()
  {
    Calendar localCalendar = Calendar.getInstance();
    c("pinganpref.apps_send_time.key", 100 * localCalendar.get(6) + localCalendar.get(11));
  }

  public static void setReportUncaughtExceptions(boolean paramBoolean)
  {
    J = paramBoolean;
  }

  private static class a
  {
    String a;
    String b;
    long c;
    String d;
    String e;
    long f = System.currentTimeMillis();
    Map g = null;
  }

  private static class b
    implements Thread.UncaughtExceptionHandler
  {
    private Thread.UncaughtExceptionHandler a = Thread.getDefaultUncaughtExceptionHandler();

    public void uncaughtException(Thread paramThread, Throwable paramThrowable)
    {
      if (PAAgent.r())
      {
        PAAgent.a(paramThrowable, false);
        Log.w("pinganLog", "UncaughtException in Thread " + paramThread.getName(), paramThrowable);
      }
      if (this.a != null)
        this.a.uncaughtException(paramThread, paramThrowable);
    }
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.talkingdata.pingan.sdk.PAAgent
 * JD-Core Version:    0.6.2
 */