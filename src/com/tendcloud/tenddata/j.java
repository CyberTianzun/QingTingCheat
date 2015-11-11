package com.tendcloud.tenddata;

import android.app.Activity;
import android.content.Context;
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

public final class j
  implements ao
{
  private static final long A = 30000L;
  private static final int B = 0;
  private static final int C = 5000;
  private static final int D = 0;
  private static final int E = 1;
  private static final int F = 2;
  private static final int G = 3;
  private static final int H = 4;
  private static final int I = 5;
  private static volatile boolean J = false;
  private static String K = "TalkingData";
  private static Context L;
  private static String M;
  private static String N;
  private static long O = 0L;
  private static boolean P = false;
  private static boolean Q = false;
  protected static final int a = 300000;
  protected static final int b = 6;
  protected static final int c = 7;
  protected static final int d = 8;
  static Long[][] e;
  static boolean f = false;
  static h g;
  static v h;
  static boolean i = false;
  static boolean j = false;
  private static final String k = "+V1.1.0";
  private static final String l = "Android+TD+V1.1.0";
  private static final String m = "TDpref.profile.key";
  private static final String n = "TDpref.session.key";
  private static final String o = "TDpref.lastactivity.key";
  private static final String p = "TDpref.start.key";
  private static final String q = "TDpref.init.key";
  private static final String r = "TDpref.actstart.key";
  private static final String s = "TDpref.end.key";
  private static final String t = "TDpref.apps_send_time.key";
  private static final String u = "TDpref.ip";
  private static final String v = "TDpref.last.sdk.check";
  private static final String w = "TD_APP_ID";
  private static final String x = "TD_CHANNEL_ID";
  private static final String y = "pref_longtime";
  private static final String z = "pref_shorttime";

  static
  {
    t();
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

  public static void a(int paramInt, long paramLong)
  {
    Handler localHandler = d.a();
    Message localMessage = Message.obtain(localHandler, paramInt);
    localHandler.removeMessages(paramInt);
    localHandler.sendMessageDelayed(localMessage, paramLong);
  }

  static void a(long paramLong)
  {
    aa.a(L, "pref_longtime", "TDpref.start.key", paramLong);
  }

  private static void a(long paramLong, String paramString)
  {
    long l1 = 0L;
    f(N);
    N = UUID.randomUUID().toString();
    long l2 = p();
    long l3 = paramLong - l2;
    if (l1 == l2)
      if ((L == null) || (!b.b(L)))
        break label88;
    label88: for (int i1 = 1; ; i1 = -1)
    {
      b(N);
      a(paramLong);
      e.a(N, paramLong, l1, i1);
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
      c(paramString1);
      O = e.a(N, paramString1, paramLong, 0, paramString2, SystemClock.elapsedRealtime());
      d.a().removeMessages(7);
    }
    a(6, 0L);
  }

  private void a(Activity paramActivity, String paramString)
  {
    if (Q)
      return;
    Q = false;
    a(paramActivity, paramString, 1);
  }

  private void a(Context paramContext, String paramString, int paramInt)
  {
    x.a(new al(this, paramInt, paramString, paramContext));
  }

  static void a(Message paramMessage)
  {
    int i1 = 1;
    try
    {
      if (paramMessage.what != 8)
      {
        i = true;
        e.a(h());
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
          break label232;
        d.c();
        i = false;
        if (i1 != 0)
          break label232;
        a(8, 300000L);
        return;
        if ((i) || (j))
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
        i2 = i1;
        i1 = 0;
        continue;
        i2 = i1;
        i1 = 0;
        continue;
        i2 = i1;
        continue;
        i1 = 0;
        i2 = 0;
      }
      label232: return;
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

  static void b(long paramLong)
  {
    aa.a(L, "pref_longtime", "TDpref.init.key", paramLong);
  }

  private void b(Activity paramActivity, String paramString)
  {
    if ((0x80 & paramActivity.getChangingConfigurations()) == 128)
    {
      Q = true;
      return;
    }
    a(paramActivity, paramString, 3);
  }

  private static void b(Message paramMessage)
  {
    long l1 = System.currentTimeMillis();
    long l2 = m();
    long l3 = p();
    long l4;
    if (l3 > l2)
    {
      l4 = l3;
      if (paramMessage != null)
        break label55;
    }
    label55: for (String str = null; ; str = (String)paramMessage.obj)
    {
      if (l1 - l4 <= 30000L)
        break label67;
      a(l1, str);
      return;
      l4 = l2;
      break;
    }
    label67: a(l1, str, l());
  }

  static void b(String paramString)
  {
    aa.a(L, "pref_longtime", "TDpref.session.key", paramString);
  }

  private static void b(Throwable paramThrowable, boolean paramBoolean)
  {
    if (!J)
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
    locala.e = x.b(localStringBuilder.toString());
    if (paramBoolean)
    {
      Message localMessage = Message.obtain(d.a(), 5, locala);
      d.a().sendMessage(localMessage);
      return;
    }
    e.a(h());
    e.a(locala.c, locala.d);
    d(locala.c);
    e.b();
  }

  static void b(boolean paramBoolean)
  {
    Context localContext = L;
    if (paramBoolean);
    for (long l1 = 1L; ; l1 = 0L)
    {
      aa.a(localContext, "pref_longtime", "TDpref.profile.key", l1);
      return;
    }
  }

  private static af c(Context paramContext)
  {
    af localaf = new af();
    if ((x.a(paramContext, "android.permission.ACCESS_COARSE_LOCATION")) || (x.a(paramContext, "android.permission.ACCESS_FINE_LOCATION")))
      try
      {
        CellLocation localCellLocation = ((TelephonyManager)paramContext.getSystemService("phone")).getCellLocation();
        if ((localCellLocation instanceof GsmCellLocation))
        {
          GsmCellLocation localGsmCellLocation = (GsmCellLocation)localCellLocation;
          if (localGsmCellLocation != null)
          {
            localaf.d = localGsmCellLocation.getCid();
            localaf.e = localGsmCellLocation.getLac();
            localaf.c = "gsm";
            if (Build.VERSION.SDK_INT >= 9)
            {
              localaf.c += localGsmCellLocation.getPsc();
              return localaf;
            }
          }
        }
        else if ((localCellLocation instanceof CdmaCellLocation))
        {
          CdmaCellLocation localCdmaCellLocation = (CdmaCellLocation)localCellLocation;
          if (localCdmaCellLocation != null)
          {
            localaf.d = localCdmaCellLocation.getBaseStationId();
            localaf.e = localCdmaCellLocation.getNetworkId();
            localaf.c = ("cdma:" + localCdmaCellLocation.getSystemId() + ':' + localCdmaCellLocation.getBaseStationLatitude() + ':' + localCdmaCellLocation.getBaseStationLongitude());
            return localaf;
          }
        }
      }
      catch (Exception localException)
      {
      }
    return localaf;
  }

  static void c(long paramLong)
  {
    aa.a(L, "pref_shorttime", "TDpref.actstart.key", paramLong);
  }

  private static void c(Message paramMessage)
  {
    long l1 = System.currentTimeMillis();
    if (O != -1L)
      e.a(O, SystemClock.elapsedRealtime());
    d(l1);
    d.a().removeMessages(8);
    a(7, 5000L);
  }

  static void c(String paramString)
  {
    aa.a(L, "pref_shorttime", "TDpref.lastactivity.key", paramString);
  }

  static void d(long paramLong)
  {
    aa.a(L, "pref_shorttime", "TDpref.end.key", paramLong);
  }

  private static void d(Message paramMessage)
  {
    if (TextUtils.isEmpty(N))
      return;
    a locala = (a)paramMessage.obj;
    e.a(N, locala.a, locala.b, locala.f, locala.g);
  }

  static void d(String paramString)
  {
    aa.a(L, "pref_longtime", "TDpref.ip", paramString);
  }

  static void f()
  {
    w();
    e = (Long[][])null;
  }

  private static void f(String paramString)
  {
    long l1 = m();
    long l2 = p();
    if (!TextUtils.isEmpty(paramString))
    {
      long l3 = l2 - l1;
      if (l3 < 500L)
        l3 = -1000L;
      e.a(paramString, (int)l3 / 1000);
    }
  }

  public static int g()
  {
    long l1 = aa.b(L, "pref_longtime", "TDpref.apps_send_time.key", 0L);
    Calendar localCalendar = Calendar.getInstance();
    int i1 = 100 * localCalendar.get(6) + localCalendar.get(11);
    if (Math.abs(l1 / 100L - i1 / 100) >= 7L)
      return 2;
    if (l1 != i1)
      return 1;
    return 0;
  }

  protected static Context h()
  {
    return L;
  }

  public static String i()
  {
    return M;
  }

  static boolean j()
  {
    return aa.b(L, "pref_longtime", "TDpref.profile.key", 1L) != 0L;
  }

  static String k()
  {
    return aa.b(L, "pref_longtime", "TDpref.session.key", null);
  }

  static String l()
  {
    return aa.b(L, "pref_shorttime", "TDpref.lastactivity.key", "");
  }

  static long m()
  {
    return aa.b(L, "pref_longtime", "TDpref.start.key", 0L);
  }

  static long n()
  {
    return aa.b(L, "pref_longtime", "TDpref.init.key", 0L);
  }

  static long o()
  {
    return aa.b(L, "pref_shorttime", "TDpref.actstart.key", 0L);
  }

  static long p()
  {
    return aa.b(L, "pref_shorttime", "TDpref.end.key", 0L);
  }

  static String q()
  {
    return aa.b(L, "pref_longtime", "TDpref.ip", null);
  }

  static h r()
  {
    if (g != null)
      return g;
    if (L == null)
      return null;
    g = new h();
    g.a = L.getPackageName();
    g.b = i.d(L);
    g.c = String.valueOf(i.c(L));
    g.d = n();
    g.e = "Android+TD+V1.1.0";
    g.f = K;
    g.h = i.e(L);
    g.i = i.f(L);
    return g;
  }

  static v s()
  {
    if (h == null)
    {
      if (L == null)
        return null;
      h = new v();
      h.s = a.i(L);
      h.a = k.c();
      h.b = String.valueOf(k.d());
      h.d = Build.CPU_ABI;
      h.e = k.a(L);
      h.f = k.g();
      h.g = k.b(L);
      h.h = k.f();
      h.i = (TimeZone.getDefault().getRawOffset() / 1000 / 60 / 60);
      h.j = ("Android+" + Build.VERSION.RELEASE);
    }
    x();
    return h;
  }

  public static void t()
  {
    Thread.setDefaultUncaughtExceptionHandler(new b());
  }

  private static void w()
  {
    Calendar localCalendar = Calendar.getInstance();
    int i1 = 100 * localCalendar.get(6) + localCalendar.get(11);
    aa.a(L, "pref_longtime", "TDpref.apps_send_time.key", i1);
  }

  private static void x()
  {
    List localList = ae.a(L);
    Object localObject1 = null;
    Iterator localIterator = localList.iterator();
    Object localObject2;
    if (localIterator.hasNext())
    {
      localObject2 = (Location)localIterator.next();
      if ((localObject1 != null) && (((Location)localObject2).getTime() <= localObject1.getTime()))
        break label226;
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
      h.c = localf;
      v localv = h;
      if (b.c(L));
      for (int i1 = 0; ; i1 = 1)
      {
        localv.k = i1;
        h.l = b.d(L);
        h.o = b.e(L);
        h.n = b.f(L);
        h.p = ae.b(L);
        h.t = b.i(L);
        af localaf = c(L);
        h.u = localaf.c;
        h.v = localaf.d;
        h.w = localaf.e;
        return;
      }
      label226: localObject2 = localObject1;
    }
  }

  public String a()
  {
    return "+V1.1.0";
  }

  public void a(Activity paramActivity)
  {
    if (P)
      return;
    a(paramActivity, paramActivity.getLocalClassName());
  }

  public void a(Activity paramActivity, String paramString1, String paramString2)
  {
    if (TextUtils.isEmpty(paramString1))
      if (!TCAgent.LOG_ON);
    do
    {
      Log.e("TDLog", "APP ID not allow empty. Please check it.");
      do
        return;
      while (P);
      if (J)
        break;
      a(paramActivity, paramString1, paramString2);
      if (J)
        break;
    }
    while (!TCAgent.LOG_ON);
    Log.e("TDLog", "SDK initialize failed.");
    return;
    a(paramActivity, paramActivity.getLocalClassName());
  }

  public void a(Context paramContext)
  {
    while (true)
    {
      String str2;
      try
      {
        if (!J)
        {
          L = paramContext.getApplicationContext();
          try
          {
            Bundle localBundle = L.getPackageManager().getApplicationInfo(L.getPackageName(), 128).metaData;
            String str1 = a(localBundle, "TD_APP_ID");
            str2 = a(localBundle, "TD_CHANNEL_ID");
            if (TextUtils.isEmpty(str1))
            {
              if (TCAgent.LOG_ON)
                Log.e("TDLog", "TD_APP_ID not found in AndroidManifest.xml!");
              return;
            }
            if (!TCAgent.LOG_ON)
              break label217;
            Log.i("TDLog", "TD_APP_ID in AndroidManifest.xml is:" + str1 + ".");
            Log.i("TDLog", "TD_CHANNEL_ID in AndroidManifest.xml is:" + str2 + ".");
            break label217;
            a(paramContext, str1, str2);
            continue;
          }
          catch (Throwable localThrowable)
          {
            if (!TCAgent.LOG_ON)
              continue;
            Log.e("TDLog", "Failed to load meta-data", localThrowable);
            continue;
          }
        }
      }
      finally
      {
      }
      Handler localHandler = d.a();
      localHandler.sendMessage(Message.obtain(localHandler, 0));
      continue;
      label217: if (str2 == null)
        str2 = "TalkingData";
    }
  }

  public void a(Context paramContext, String paramString)
  {
    if (paramContext != null)
      L = paramContext;
  }

  // ERROR //
  public void a(Context paramContext, String paramString1, String paramString2)
  {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: getstatic 106	com/tendcloud/tenddata/j:J	Z
    //   5: ifne +94 -> 99
    //   8: aload_1
    //   9: invokevirtual 727	android/content/Context:getApplicationContext	()Landroid/content/Context;
    //   12: putstatic 220	com/tendcloud/tenddata/j:L	Landroid/content/Context;
    //   15: getstatic 710	com/tendcloud/tenddata/TCAgent:LOG_ON	Z
    //   18: ifeq +51 -> 69
    //   21: ldc_w 712
    //   24: new 165	java/lang/StringBuilder
    //   27: dup
    //   28: invokespecial 166	java/lang/StringBuilder:<init>	()V
    //   31: ldc_w 763
    //   34: invokevirtual 176	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   37: aload_2
    //   38: invokevirtual 176	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   41: ldc_w 765
    //   44: invokevirtual 176	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   47: aload_3
    //   48: invokevirtual 176	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   51: ldc_w 767
    //   54: invokevirtual 176	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   57: ldc 60
    //   59: invokevirtual 176	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   62: invokevirtual 188	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   65: invokestatic 753	android/util/Log:i	(Ljava/lang/String;Ljava/lang/String;)I
    //   68: pop
    //   69: aload_1
    //   70: ldc_w 769
    //   73: invokestatic 403	com/tendcloud/tenddata/x:a	(Landroid/content/Context;Ljava/lang/String;)Z
    //   76: ifne +26 -> 102
    //   79: getstatic 710	com/tendcloud/tenddata/TCAgent:LOG_ON	Z
    //   82: ifeq +13 -> 95
    //   85: ldc_w 712
    //   88: ldc_w 771
    //   91: invokestatic 719	android/util/Log:e	(Ljava/lang/String;Ljava/lang/String;)I
    //   94: pop
    //   95: iconst_1
    //   96: putstatic 106	com/tendcloud/tenddata/j:J	Z
    //   99: aload_0
    //   100: monitorexit
    //   101: return
    //   102: aload_2
    //   103: putstatic 528	com/tendcloud/tenddata/j:M	Ljava/lang/String;
    //   106: aload_3
    //   107: putstatic 110	com/tendcloud/tenddata/j:K	Ljava/lang/String;
    //   110: getstatic 439	android/os/Build$VERSION:SDK_INT	I
    //   113: istore 5
    //   115: iload 5
    //   117: bipush 14
    //   119: if_icmplt +77 -> 196
    //   122: aload_1
    //   123: instanceof 347
    //   126: ifeq +92 -> 218
    //   129: aload_1
    //   130: checkcast 347	android/app/Activity
    //   133: invokevirtual 775	android/app/Activity:getApplication	()Landroid/app/Application;
    //   136: astore 8
    //   138: aload 8
    //   140: ifnull +56 -> 196
    //   143: ldc_w 777
    //   146: invokestatic 781	java/lang/Class:forName	(Ljava/lang/String;)Ljava/lang/Class;
    //   149: astore 9
    //   151: aload 8
    //   153: invokevirtual 372	java/lang/Object:getClass	()Ljava/lang/Class;
    //   156: ldc_w 783
    //   159: iconst_1
    //   160: anewarray 374	java/lang/Class
    //   163: dup
    //   164: iconst_0
    //   165: aload 9
    //   167: aastore
    //   168: invokevirtual 787	java/lang/Class:getMethod	(Ljava/lang/String;[Ljava/lang/Class;)Ljava/lang/reflect/Method;
    //   171: aload 8
    //   173: iconst_1
    //   174: anewarray 4	java/lang/Object
    //   177: dup
    //   178: iconst_0
    //   179: new 789	com/tendcloud/tenddata/l
    //   182: dup
    //   183: aload_0
    //   184: invokespecial 792	com/tendcloud/tenddata/l:<init>	(Lcom/tendcloud/tenddata/j;)V
    //   187: aastore
    //   188: invokevirtual 798	java/lang/reflect/Method:invoke	(Ljava/lang/Object;[Ljava/lang/Object;)Ljava/lang/Object;
    //   191: pop
    //   192: iconst_1
    //   193: putstatic 112	com/tendcloud/tenddata/j:P	Z
    //   196: new 800	com/tendcloud/tenddata/am
    //   199: dup
    //   200: aload_0
    //   201: aload_1
    //   202: invokespecial 803	com/tendcloud/tenddata/am:<init>	(Lcom/tendcloud/tenddata/j;Landroid/content/Context;)V
    //   205: invokestatic 288	com/tendcloud/tenddata/x:a	(Ljava/lang/Runnable;)V
    //   208: goto -113 -> 95
    //   211: astore 4
    //   213: aload_0
    //   214: monitorexit
    //   215: aload 4
    //   217: athrow
    //   218: aload_1
    //   219: instanceof 805
    //   222: istore 7
    //   224: aconst_null
    //   225: astore 8
    //   227: iload 7
    //   229: ifeq -91 -> 138
    //   232: aload_1
    //   233: checkcast 805	android/app/Application
    //   236: astore 8
    //   238: goto -100 -> 138
    //   241: astore 6
    //   243: aload 6
    //   245: invokevirtual 808	java/lang/Exception:printStackTrace	()V
    //   248: goto -52 -> 196
    //
    // Exception table:
    //   from	to	target	type
    //   2	69	211	finally
    //   69	95	211	finally
    //   95	99	211	finally
    //   102	115	211	finally
    //   122	138	211	finally
    //   143	196	211	finally
    //   196	208	211	finally
    //   218	224	211	finally
    //   232	238	211	finally
    //   243	248	211	finally
    //   122	138	241	java/lang/Exception
    //   143	196	241	java/lang/Exception
    //   218	224	241	java/lang/Exception
    //   232	238	241	java/lang/Exception
  }

  public void a(Context paramContext, String paramString1, String paramString2, Map paramMap)
  {
    x.a(new ak(this, paramString1, paramString2, paramMap));
  }

  public void a(Context paramContext, Throwable paramThrowable)
  {
    if (paramThrowable == null)
      return;
    x.a(new aj(this, paramThrowable));
  }

  public void a(String paramString)
  {
    aa.a(L, "pref_longtime", "TDpref.last.sdk.check", System.currentTimeMillis());
  }

  public String b(Context paramContext)
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

  public void b(Activity paramActivity)
  {
    if (P)
      return;
    b(paramActivity, paramActivity.getLocalClassName());
  }

  public void b(Context paramContext, String paramString)
  {
    a(paramContext, paramString, 1);
  }

  public boolean b()
  {
    long l1 = aa.b(L, "pref_longtime", "TDpref.last.sdk.check", System.currentTimeMillis());
    long l2 = Math.abs((System.currentTimeMillis() - l1) / 86400000L);
    return (l2 > 7L) || (Math.random() * l2 > 2.0D);
  }

  public String c()
  {
    return "https://u.talkingdata.net/ota/a/TD/android/ver";
  }

  public void c(Context paramContext, String paramString)
  {
    a(paramContext, paramString, 3);
  }

  public void c(boolean paramBoolean)
  {
    TCAgent.d = paramBoolean;
  }

  public String d()
  {
    return "https://u.talkingdata.net/ota/a/TD/android/sdk.zip";
  }

  public void e()
  {
  }

  private static final class a
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
      if (TCAgent.d)
      {
        j.a(paramThrowable, false);
        Log.w("TDLog", "UncaughtException in Thread " + paramThread.getName(), paramThrowable);
      }
      if (this.a != null)
        this.a.uncaughtException(paramThread, paramThrowable);
    }
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.tendcloud.tenddata.j
 * JD-Core Version:    0.6.2
 */