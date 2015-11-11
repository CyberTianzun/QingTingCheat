package com.taobao.munion.base.download;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.app.Notification;
import android.app.Notification.Builder;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.NetworkInfo.State;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Environment;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.text.TextUtils;
import android.util.SparseArray;
import com.taobao.munion.base.Log;
import com.taobao.munion.base.volley.m;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;
import java.util.Set;

class f
{
  protected static final String a = "Unknown";
  static final int b = 0;
  static final int c = 1;
  static final int d = 1;
  static final int e = 2;
  private static final String f = f.class.getName();
  private static final String h = "2G/3G";
  private static final String i = "Wi-Fi";
  private m g;
  private SparseArray<c> j;
  private Map<c.a, Messenger> k;
  private i l;

  public f(SparseArray<c> paramSparseArray, Map<c.a, Messenger> paramMap, i parami)
  {
    this.j = paramSparseArray;
    this.k = paramMap;
    this.l = parami;
  }

  private d a(String paramString1, String paramString2)
  {
    if (!TextUtils.isEmpty(paramString1))
      try
      {
        d locald = (d)Class.forName(paramString1).getConstructor(new Class[] { String.class }).newInstance(new Object[] { paramString2 });
        return locald;
      }
      catch (Exception localException)
      {
        Log.e(localException, "", new Object[0]);
        return null;
      }
    return new d(paramString2);
  }

  public static File a(String paramString, Context paramContext, boolean[] paramArrayOfBoolean)
    throws IOException
  {
    if (b())
    {
      String str3 = Environment.getExternalStorageDirectory().getCanonicalPath();
      File localFile2 = new File(str3 + "/download/.taobao" + paramString);
      localFile2.mkdirs();
      if (localFile2.exists())
      {
        paramArrayOfBoolean[0] = true;
        return localFile2;
      }
    }
    String str1 = paramContext.getCacheDir().getAbsolutePath();
    new File(str1).mkdir();
    a(str1, 505, -1, -1);
    String str2 = str1 + "/tbdownload";
    new File(str2).mkdir();
    a(str2, 505, -1, -1);
    File localFile1 = new File(str2);
    paramArrayOfBoolean[0] = false;
    return localFile1;
  }

  public static String a()
  {
    Date localDate = new Date();
    return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US).format(localDate);
  }

  public static String a(File paramFile)
  {
    byte[] arrayOfByte = new byte[1024];
    MessageDigest localMessageDigest;
    FileInputStream localFileInputStream;
    try
    {
      if (!paramFile.isFile())
        return "";
      localMessageDigest = MessageDigest.getInstance("MD5");
      localFileInputStream = new FileInputStream(paramFile);
      while (true)
      {
        int m = localFileInputStream.read(arrayOfByte, 0, 1024);
        if (m == -1)
          break;
        localMessageDigest.update(arrayOfByte, 0, m);
      }
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      return null;
    }
    localFileInputStream.close();
    return String.format("%1$032x", new Object[] { new BigInteger(1, localMessageDigest.digest()) });
  }

  public static String a(String paramString)
  {
    int m = 0;
    if (paramString == null)
      return null;
    try
    {
      byte[] arrayOfByte1 = paramString.getBytes();
      MessageDigest localMessageDigest = MessageDigest.getInstance("MD5");
      localMessageDigest.reset();
      localMessageDigest.update(arrayOfByte1);
      byte[] arrayOfByte2 = localMessageDigest.digest();
      StringBuffer localStringBuffer = new StringBuffer();
      while (m < arrayOfByte2.length)
      {
        Object[] arrayOfObject = new Object[1];
        arrayOfObject[0] = Byte.valueOf(arrayOfByte2[m]);
        localStringBuffer.append(String.format("%02X", arrayOfObject));
        m++;
      }
      String str = localStringBuffer.toString();
      return str;
    }
    catch (Exception localException)
    {
    }
    return paramString.replaceAll("[^[a-z][A-Z][0-9][.][_]]", "");
  }

  protected static boolean a(String paramString, int paramInt)
  {
    int m = 432;
    if ((paramInt & 0x1) != 0)
      m = 436;
    if ((paramInt & 0x2) != 0)
      m |= 2;
    return a(paramString, m, -1, -1);
  }

  protected static boolean a(String paramString, int paramInt1, int paramInt2, int paramInt3)
  {
    try
    {
      Class localClass = Class.forName("android.os.FileUtils");
      Class[] arrayOfClass = new Class[4];
      arrayOfClass[0] = String.class;
      arrayOfClass[1] = Integer.TYPE;
      arrayOfClass[2] = Integer.TYPE;
      arrayOfClass[3] = Integer.TYPE;
      Method localMethod = localClass.getMethod("setPermissions", arrayOfClass);
      Object[] arrayOfObject = new Object[4];
      arrayOfObject[0] = paramString;
      arrayOfObject[1] = Integer.valueOf(paramInt1);
      arrayOfObject[2] = Integer.valueOf(-1);
      arrayOfObject[3] = Integer.valueOf(-1);
      localMethod.invoke(null, arrayOfObject);
      return true;
    }
    catch (ClassNotFoundException localClassNotFoundException)
    {
      Log.e("error when set permissions:", new Object[] { localClassNotFoundException });
      return false;
    }
    catch (NoSuchMethodException localNoSuchMethodException)
    {
      while (true)
        Log.e("error when set permissions:", new Object[] { localNoSuchMethodException });
    }
    catch (IllegalArgumentException localIllegalArgumentException)
    {
      while (true)
        Log.e("error when set permissions:", new Object[] { localIllegalArgumentException });
    }
    catch (IllegalAccessException localIllegalAccessException)
    {
      while (true)
        Log.e("error when set permissions:", new Object[] { localIllegalAccessException });
    }
    catch (InvocationTargetException localInvocationTargetException)
    {
      while (true)
        Log.e("error when set permissions:", new Object[] { localInvocationTargetException });
    }
  }

  private static boolean b()
  {
    return Environment.getExternalStorageState().equals("mounted");
  }

  public static boolean b(Context paramContext)
  {
    try
    {
      int m = paramContext.getPackageManager().checkPermission("android.permission.ACCESS_NETWORK_STATE", paramContext.getPackageName());
      boolean bool = ((ConnectivityManager)paramContext.getSystemService("connectivity")).getActiveNetworkInfo().isConnectedOrConnecting();
      return (m == 0) && (bool);
    }
    catch (Exception localException)
    {
    }
    return false;
  }

  public static String c(Context paramContext)
  {
    try
    {
      String str = paramContext.getPackageManager().getApplicationLabel(paramContext.getApplicationInfo()).toString();
      return str;
    }
    catch (Exception localException)
    {
    }
    return "unknow";
  }

  public static String[] d(Context paramContext)
  {
    String[] arrayOfString = { "Unknown", "Unknown" };
    try
    {
      if (paramContext.getPackageManager().checkPermission("android.permission.ACCESS_NETWORK_STATE", paramContext.getPackageName()) != 0)
      {
        arrayOfString[0] = "Unknown";
        return arrayOfString;
      }
      ConnectivityManager localConnectivityManager = (ConnectivityManager)paramContext.getSystemService("connectivity");
      if (localConnectivityManager == null)
      {
        arrayOfString[0] = "Unknown";
        return arrayOfString;
      }
      if (localConnectivityManager.getNetworkInfo(1).getState() == NetworkInfo.State.CONNECTED)
      {
        arrayOfString[0] = "Wi-Fi";
        return arrayOfString;
      }
      NetworkInfo localNetworkInfo = localConnectivityManager.getNetworkInfo(0);
      if (localNetworkInfo.getState() == NetworkInfo.State.CONNECTED)
      {
        arrayOfString[0] = "2G/3G";
        arrayOfString[1] = localNetworkInfo.getSubtypeName();
        return arrayOfString;
      }
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return arrayOfString;
  }

  public static boolean e(Context paramContext)
  {
    return "Wi-Fi".equals(d(paramContext)[0]);
  }

  int a(c.a parama)
  {
    return Math.abs((int)((parama.c.hashCode() >> 2) + (parama.d.hashCode() >> 3) + System.currentTimeMillis()));
  }

  a a(Context paramContext, c.a parama, int paramInt1, int paramInt2)
  {
    Context localContext = paramContext.getApplicationContext();
    a locala = new a(localContext);
    PendingIntent localPendingIntent = PendingIntent.getActivity(localContext, 0, new Intent(), 134217728);
    locala.c(k.e).a(17301633).a(localPendingIntent).a(System.currentTimeMillis());
    locala.b(k.f + parama.c).a(paramInt2 + "%").a(100, paramInt2, false);
    if (parama.m)
    {
      locala.b();
      a(localContext, locala, paramInt1, 2);
    }
    locala.a(true).b(false);
    return locala;
  }

  final void a(long paramLong1, long paramLong2, long paramLong3, c.a parama)
  {
    String[] arrayOfString = parama.i;
    if (arrayOfString != null)
    {
      Log.i("sendFailedReport.", new Object[0]);
      int m = arrayOfString.length;
      for (int n = 0; n < m; n++)
      {
        String str = arrayOfString[n];
        d locald = a(parama.a, str);
        Long[] arrayOfLong = new Long[3];
        arrayOfLong[0] = Long.valueOf(paramLong1);
        arrayOfLong[1] = Long.valueOf(paramLong2);
        arrayOfLong[2] = Long.valueOf(paramLong3);
        locald.a(arrayOfLong);
        this.g.a(locald);
      }
    }
  }

  void a(Context paramContext, int paramInt)
  {
    Context localContext = paramContext.getApplicationContext();
    NotificationManager localNotificationManager = (NotificationManager)localContext.getSystemService("notification");
    c localc = (c)this.j.get(paramInt);
    localc.b.b();
    a(localContext, localc.b, paramInt, 1);
    localc.b.b(k.g + localc.e.c).a(false).b(true);
    localNotificationManager.notify(paramInt, localc.b.a());
  }

  void a(Context paramContext, a parama, int paramInt1, int paramInt2)
  {
    PendingIntent localPendingIntent1;
    PendingIntent localPendingIntent2;
    if (Build.VERSION.SDK_INT >= 16)
    {
      localPendingIntent1 = j.b(paramContext, j.a(paramInt1, "continue"));
      localPendingIntent2 = j.b(paramContext, j.a(paramInt1, "cancel"));
      switch (paramInt2)
      {
      default:
      case 1:
      case 2:
      }
    }
    while (true)
    {
      parama.a(17301560, k.c, localPendingIntent2);
      return;
      parama.a(17301540, k.a, localPendingIntent1);
      continue;
      parama.a(17301539, k.b, localPendingIntent1);
    }
  }

  public void a(m paramm)
  {
    this.g = paramm;
  }

  boolean a(Context paramContext)
  {
    List localList = ((ActivityManager)paramContext.getSystemService("activity")).getRunningAppProcesses();
    if (localList == null)
      return false;
    String str = paramContext.getPackageName();
    Iterator localIterator = localList.iterator();
    while (localIterator.hasNext())
    {
      ActivityManager.RunningAppProcessInfo localRunningAppProcessInfo = (ActivityManager.RunningAppProcessInfo)localIterator.next();
      if ((localRunningAppProcessInfo.importance == 100) && (localRunningAppProcessInfo.processName.equals(str)))
        return true;
    }
    return false;
  }

  // ERROR //
  boolean a(DownloadingService paramDownloadingService, Intent paramIntent)
  {
    // Byte code:
    //   0: aload_1
    //   1: invokevirtual 566	com/taobao/munion/base/download/DownloadingService:getApplicationContext	()Landroid/content/Context;
    //   4: astore 4
    //   6: aload_2
    //   7: invokevirtual 570	android/content/Intent:getExtras	()Landroid/os/Bundle;
    //   10: ldc_w 572
    //   13: invokevirtual 577	android/os/Bundle:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   16: astore 5
    //   18: aload 5
    //   20: ifnonnull +5 -> 25
    //   23: iconst_0
    //   24: ireturn
    //   25: aload 5
    //   27: ldc_w 579
    //   30: invokevirtual 583	java/lang/String:split	(Ljava/lang/String;)[Ljava/lang/String;
    //   33: astore 6
    //   35: aload 6
    //   37: iconst_0
    //   38: aaload
    //   39: invokestatic 587	java/lang/Integer:parseInt	(Ljava/lang/String;)I
    //   42: istore 7
    //   44: aload 6
    //   46: iconst_1
    //   47: aaload
    //   48: invokevirtual 590	java/lang/String:trim	()Ljava/lang/String;
    //   51: astore 8
    //   53: iload 7
    //   55: ifeq +281 -> 336
    //   58: aload 8
    //   60: invokestatic 62	android/text/TextUtils:isEmpty	(Ljava/lang/CharSequence;)Z
    //   63: ifne +273 -> 336
    //   66: aload_0
    //   67: getfield 49	com/taobao/munion/base/download/f:j	Landroid/util/SparseArray;
    //   70: iload 7
    //   72: invokevirtual 593	android/util/SparseArray:indexOfKey	(I)I
    //   75: iflt +261 -> 336
    //   78: aload_0
    //   79: getfield 49	com/taobao/munion/base/download/f:j	Landroid/util/SparseArray;
    //   82: iload 7
    //   84: invokevirtual 484	android/util/SparseArray:get	(I)Ljava/lang/Object;
    //   87: checkcast 486	com/taobao/munion/base/download/f$c
    //   90: astore 9
    //   92: aload 9
    //   94: getfield 596	com/taobao/munion/base/download/f$c:a	Lcom/taobao/munion/base/download/DownloadingService$b;
    //   97: astore 10
    //   99: aload 9
    //   101: getfield 599	com/taobao/munion/base/download/f$c:f	[J
    //   104: iconst_0
    //   105: laload
    //   106: lstore 11
    //   108: aload 9
    //   110: getfield 599	com/taobao/munion/base/download/f$c:f	[J
    //   113: iconst_1
    //   114: laload
    //   115: lstore 13
    //   117: aload 9
    //   119: getfield 599	com/taobao/munion/base/download/f$c:f	[J
    //   122: iconst_2
    //   123: laload
    //   124: lstore 15
    //   126: ldc_w 508
    //   129: aload 8
    //   131: invokevirtual 289	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   134: ifeq +346 -> 480
    //   137: aload 10
    //   139: ifnonnull +199 -> 338
    //   142: getstatic 43	com/taobao/munion/base/download/f:f	Ljava/lang/String;
    //   145: iconst_1
    //   146: anewarray 4	java/lang/Object
    //   149: dup
    //   150: iconst_0
    //   151: ldc_w 601
    //   154: aastore
    //   155: invokestatic 603	com/taobao/munion/base/Log:d	(Ljava/lang/String;[Ljava/lang/Object;)V
    //   158: aload 4
    //   160: invokestatic 605	com/taobao/munion/base/download/f:b	(Landroid/content/Context;)Z
    //   163: ifne +17 -> 180
    //   166: aload 4
    //   168: getstatic 606	com/taobao/munion/base/download/k:d	Ljava/lang/String;
    //   171: iconst_1
    //   172: invokestatic 612	android/widget/Toast:makeText	(Landroid/content/Context;Ljava/lang/CharSequence;I)Landroid/widget/Toast;
    //   175: invokevirtual 615	android/widget/Toast:show	()V
    //   178: iconst_0
    //   179: ireturn
    //   180: aload_1
    //   181: invokevirtual 619	java/lang/Object:getClass	()Ljava/lang/Class;
    //   184: pop
    //   185: new 621	com/taobao/munion/base/download/DownloadingService$b
    //   188: dup
    //   189: aload_1
    //   190: aload 4
    //   192: aload 9
    //   194: getfield 494	com/taobao/munion/base/download/f$c:e	Lcom/taobao/munion/base/download/c$a;
    //   197: iload 7
    //   199: aload 9
    //   201: getfield 623	com/taobao/munion/base/download/f$c:d	I
    //   204: aload_1
    //   205: getfield 627	com/taobao/munion/base/download/DownloadingService:r	Lcom/taobao/munion/base/download/DownloadingService$a;
    //   208: invokespecial 630	com/taobao/munion/base/download/DownloadingService$b:<init>	(Lcom/taobao/munion/base/download/DownloadingService;Landroid/content/Context;Lcom/taobao/munion/base/download/c$a;IILcom/taobao/munion/base/download/DownloadingService$a;)V
    //   211: astore 18
    //   213: aload 9
    //   215: aload 18
    //   217: putfield 596	com/taobao/munion/base/download/f$c:a	Lcom/taobao/munion/base/download/DownloadingService$b;
    //   220: aload 18
    //   222: invokevirtual 633	com/taobao/munion/base/download/DownloadingService$b:start	()V
    //   225: aload_0
    //   226: lload 11
    //   228: lload 13
    //   230: lload 15
    //   232: aload 9
    //   234: getfield 494	com/taobao/munion/base/download/f$c:e	Lcom/taobao/munion/base/download/c$a;
    //   237: invokevirtual 635	com/taobao/munion/base/download/f:c	(JJJLcom/taobao/munion/base/download/c$a;)V
    //   240: invokestatic 641	android/os/Message:obtain	()Landroid/os/Message;
    //   243: astore 19
    //   245: aload 19
    //   247: iconst_2
    //   248: putfield 644	android/os/Message:what	I
    //   251: aload 19
    //   253: bipush 7
    //   255: putfield 647	android/os/Message:arg1	I
    //   258: aload 19
    //   260: iload 7
    //   262: putfield 650	android/os/Message:arg2	I
    //   265: aload_0
    //   266: getfield 51	com/taobao/munion/base/download/f:k	Ljava/util/Map;
    //   269: aload 9
    //   271: getfield 494	com/taobao/munion/base/download/f$c:e	Lcom/taobao/munion/base/download/c$a;
    //   274: invokeinterface 655 2 0
    //   279: ifnull +25 -> 304
    //   282: aload_0
    //   283: getfield 51	com/taobao/munion/base/download/f:k	Ljava/util/Map;
    //   286: aload 9
    //   288: getfield 494	com/taobao/munion/base/download/f$c:e	Lcom/taobao/munion/base/download/c$a;
    //   291: invokeinterface 655 2 0
    //   296: checkcast 657	android/os/Messenger
    //   299: aload 19
    //   301: invokevirtual 661	android/os/Messenger:send	(Landroid/os/Message;)V
    //   304: iconst_1
    //   305: ireturn
    //   306: astore 20
    //   308: getstatic 43	com/taobao/munion/base/download/f:f	Ljava/lang/String;
    //   311: iconst_2
    //   312: anewarray 4	java/lang/Object
    //   315: dup
    //   316: iconst_0
    //   317: ldc 82
    //   319: aastore
    //   320: dup
    //   321: iconst_1
    //   322: aload 20
    //   324: aastore
    //   325: invokestatic 280	com/taobao/munion/base/Log:e	(Ljava/lang/String;[Ljava/lang/Object;)V
    //   328: goto -24 -> 304
    //   331: astore_3
    //   332: aload_3
    //   333: invokevirtual 192	java/lang/Exception:printStackTrace	()V
    //   336: iconst_0
    //   337: ireturn
    //   338: getstatic 43	com/taobao/munion/base/download/f:f	Ljava/lang/String;
    //   341: iconst_1
    //   342: anewarray 4	java/lang/Object
    //   345: dup
    //   346: iconst_0
    //   347: ldc_w 601
    //   350: aastore
    //   351: invokestatic 603	com/taobao/munion/base/Log:d	(Ljava/lang/String;[Ljava/lang/Object;)V
    //   354: aload 10
    //   356: iconst_1
    //   357: invokevirtual 664	com/taobao/munion/base/download/DownloadingService$b:a	(I)V
    //   360: aload 9
    //   362: aconst_null
    //   363: putfield 596	com/taobao/munion/base/download/f$c:a	Lcom/taobao/munion/base/download/DownloadingService$b;
    //   366: aload_0
    //   367: aload 4
    //   369: iload 7
    //   371: invokevirtual 666	com/taobao/munion/base/download/f:a	(Landroid/content/Context;I)V
    //   374: aload_0
    //   375: lload 11
    //   377: lload 13
    //   379: lload 15
    //   381: aload 9
    //   383: getfield 494	com/taobao/munion/base/download/f$c:e	Lcom/taobao/munion/base/download/c$a;
    //   386: invokevirtual 668	com/taobao/munion/base/download/f:b	(JJJLcom/taobao/munion/base/download/c$a;)V
    //   389: invokestatic 641	android/os/Message:obtain	()Landroid/os/Message;
    //   392: astore 21
    //   394: aload 21
    //   396: iconst_2
    //   397: putfield 644	android/os/Message:what	I
    //   400: aload 21
    //   402: bipush 6
    //   404: putfield 647	android/os/Message:arg1	I
    //   407: aload 21
    //   409: iload 7
    //   411: putfield 650	android/os/Message:arg2	I
    //   414: aload_0
    //   415: getfield 51	com/taobao/munion/base/download/f:k	Ljava/util/Map;
    //   418: aload 9
    //   420: getfield 494	com/taobao/munion/base/download/f$c:e	Lcom/taobao/munion/base/download/c$a;
    //   423: invokeinterface 655 2 0
    //   428: ifnull +25 -> 453
    //   431: aload_0
    //   432: getfield 51	com/taobao/munion/base/download/f:k	Ljava/util/Map;
    //   435: aload 9
    //   437: getfield 494	com/taobao/munion/base/download/f$c:e	Lcom/taobao/munion/base/download/c$a;
    //   440: invokeinterface 655 2 0
    //   445: checkcast 657	android/os/Messenger
    //   448: aload 21
    //   450: invokevirtual 661	android/os/Messenger:send	(Landroid/os/Message;)V
    //   453: iconst_1
    //   454: ireturn
    //   455: astore 22
    //   457: getstatic 43	com/taobao/munion/base/download/f:f	Ljava/lang/String;
    //   460: iconst_2
    //   461: anewarray 4	java/lang/Object
    //   464: dup
    //   465: iconst_0
    //   466: ldc 82
    //   468: aastore
    //   469: dup
    //   470: iconst_1
    //   471: aload 22
    //   473: aastore
    //   474: invokestatic 280	com/taobao/munion/base/Log:e	(Ljava/lang/String;[Ljava/lang/Object;)V
    //   477: goto -24 -> 453
    //   480: ldc_w 518
    //   483: aload 8
    //   485: invokevirtual 289	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   488: ifeq -152 -> 336
    //   491: getstatic 43	com/taobao/munion/base/download/f:f	Ljava/lang/String;
    //   494: iconst_1
    //   495: anewarray 4	java/lang/Object
    //   498: dup
    //   499: iconst_0
    //   500: ldc_w 670
    //   503: aastore
    //   504: invokestatic 603	com/taobao/munion/base/Log:d	(Ljava/lang/String;[Ljava/lang/Object;)V
    //   507: aload 10
    //   509: ifnull +82 -> 591
    //   512: aload 10
    //   514: iconst_2
    //   515: invokevirtual 664	com/taobao/munion/base/download/DownloadingService$b:a	(I)V
    //   518: invokestatic 641	android/os/Message:obtain	()Landroid/os/Message;
    //   521: astore 29
    //   523: aload 29
    //   525: iconst_5
    //   526: putfield 644	android/os/Message:what	I
    //   529: aload 29
    //   531: iconst_5
    //   532: putfield 647	android/os/Message:arg1	I
    //   535: aload 29
    //   537: iload 7
    //   539: putfield 650	android/os/Message:arg2	I
    //   542: aload_0
    //   543: getfield 51	com/taobao/munion/base/download/f:k	Ljava/util/Map;
    //   546: aload 9
    //   548: getfield 494	com/taobao/munion/base/download/f$c:e	Lcom/taobao/munion/base/download/c$a;
    //   551: invokeinterface 655 2 0
    //   556: ifnull +25 -> 581
    //   559: aload_0
    //   560: getfield 51	com/taobao/munion/base/download/f:k	Ljava/util/Map;
    //   563: aload 9
    //   565: getfield 494	com/taobao/munion/base/download/f$c:e	Lcom/taobao/munion/base/download/c$a;
    //   568: invokeinterface 655 2 0
    //   573: checkcast 657	android/os/Messenger
    //   576: aload 29
    //   578: invokevirtual 661	android/os/Messenger:send	(Landroid/os/Message;)V
    //   581: aload_0
    //   582: aload 4
    //   584: iload 7
    //   586: invokevirtual 672	com/taobao/munion/base/download/f:b	(Landroid/content/Context;I)V
    //   589: iconst_1
    //   590: ireturn
    //   591: aload_0
    //   592: lload 11
    //   594: lload 13
    //   596: lload 15
    //   598: aload 9
    //   600: getfield 494	com/taobao/munion/base/download/f$c:e	Lcom/taobao/munion/base/download/c$a;
    //   603: invokevirtual 674	com/taobao/munion/base/download/f:d	(JJJLcom/taobao/munion/base/download/c$a;)V
    //   606: goto -88 -> 518
    //   609: astore 26
    //   611: invokestatic 641	android/os/Message:obtain	()Landroid/os/Message;
    //   614: astore 27
    //   616: aload 27
    //   618: iconst_5
    //   619: putfield 644	android/os/Message:what	I
    //   622: aload 27
    //   624: iconst_5
    //   625: putfield 647	android/os/Message:arg1	I
    //   628: aload 27
    //   630: iload 7
    //   632: putfield 650	android/os/Message:arg2	I
    //   635: aload_0
    //   636: getfield 51	com/taobao/munion/base/download/f:k	Ljava/util/Map;
    //   639: aload 9
    //   641: getfield 494	com/taobao/munion/base/download/f$c:e	Lcom/taobao/munion/base/download/c$a;
    //   644: invokeinterface 655 2 0
    //   649: ifnull +25 -> 674
    //   652: aload_0
    //   653: getfield 51	com/taobao/munion/base/download/f:k	Ljava/util/Map;
    //   656: aload 9
    //   658: getfield 494	com/taobao/munion/base/download/f$c:e	Lcom/taobao/munion/base/download/c$a;
    //   661: invokeinterface 655 2 0
    //   666: checkcast 657	android/os/Messenger
    //   669: aload 27
    //   671: invokevirtual 661	android/os/Messenger:send	(Landroid/os/Message;)V
    //   674: aload_0
    //   675: aload 4
    //   677: iload 7
    //   679: invokevirtual 672	com/taobao/munion/base/download/f:b	(Landroid/content/Context;I)V
    //   682: goto -93 -> 589
    //   685: astore 28
    //   687: aload_0
    //   688: aload 4
    //   690: iload 7
    //   692: invokevirtual 672	com/taobao/munion/base/download/f:b	(Landroid/content/Context;I)V
    //   695: goto -106 -> 589
    //   698: astore 30
    //   700: aload_0
    //   701: aload 4
    //   703: iload 7
    //   705: invokevirtual 672	com/taobao/munion/base/download/f:b	(Landroid/content/Context;I)V
    //   708: goto -119 -> 589
    //   711: astore 23
    //   713: invokestatic 641	android/os/Message:obtain	()Landroid/os/Message;
    //   716: astore 24
    //   718: aload 24
    //   720: iconst_5
    //   721: putfield 644	android/os/Message:what	I
    //   724: aload 24
    //   726: iconst_5
    //   727: putfield 647	android/os/Message:arg1	I
    //   730: aload 24
    //   732: iload 7
    //   734: putfield 650	android/os/Message:arg2	I
    //   737: aload_0
    //   738: getfield 51	com/taobao/munion/base/download/f:k	Ljava/util/Map;
    //   741: aload 9
    //   743: getfield 494	com/taobao/munion/base/download/f$c:e	Lcom/taobao/munion/base/download/c$a;
    //   746: invokeinterface 655 2 0
    //   751: ifnull +25 -> 776
    //   754: aload_0
    //   755: getfield 51	com/taobao/munion/base/download/f:k	Ljava/util/Map;
    //   758: aload 9
    //   760: getfield 494	com/taobao/munion/base/download/f$c:e	Lcom/taobao/munion/base/download/c$a;
    //   763: invokeinterface 655 2 0
    //   768: checkcast 657	android/os/Messenger
    //   771: aload 24
    //   773: invokevirtual 661	android/os/Messenger:send	(Landroid/os/Message;)V
    //   776: aload_0
    //   777: aload 4
    //   779: iload 7
    //   781: invokevirtual 672	com/taobao/munion/base/download/f:b	(Landroid/content/Context;I)V
    //   784: aload 23
    //   786: athrow
    //   787: astore 25
    //   789: aload_0
    //   790: aload 4
    //   792: iload 7
    //   794: invokevirtual 672	com/taobao/munion/base/download/f:b	(Landroid/content/Context;I)V
    //   797: goto -13 -> 784
    //
    // Exception table:
    //   from	to	target	type
    //   265	304	306	android/os/RemoteException
    //   0	18	331	java/lang/Exception
    //   25	53	331	java/lang/Exception
    //   58	137	331	java/lang/Exception
    //   142	178	331	java/lang/Exception
    //   180	265	331	java/lang/Exception
    //   265	304	331	java/lang/Exception
    //   308	328	331	java/lang/Exception
    //   338	414	331	java/lang/Exception
    //   414	453	331	java/lang/Exception
    //   457	477	331	java/lang/Exception
    //   480	507	331	java/lang/Exception
    //   518	542	331	java/lang/Exception
    //   542	581	331	java/lang/Exception
    //   581	589	331	java/lang/Exception
    //   611	635	331	java/lang/Exception
    //   635	674	331	java/lang/Exception
    //   674	682	331	java/lang/Exception
    //   687	695	331	java/lang/Exception
    //   700	708	331	java/lang/Exception
    //   713	737	331	java/lang/Exception
    //   737	776	331	java/lang/Exception
    //   776	784	331	java/lang/Exception
    //   784	787	331	java/lang/Exception
    //   789	797	331	java/lang/Exception
    //   414	453	455	android/os/RemoteException
    //   512	518	609	java/lang/Exception
    //   591	606	609	java/lang/Exception
    //   635	674	685	android/os/RemoteException
    //   674	682	685	android/os/RemoteException
    //   542	581	698	android/os/RemoteException
    //   581	589	698	android/os/RemoteException
    //   512	518	711	finally
    //   591	606	711	finally
    //   737	776	787	android/os/RemoteException
    //   776	784	787	android/os/RemoteException
  }

  boolean a(c.a parama, boolean paramBoolean, Messenger paramMessenger)
  {
    if (paramBoolean)
    {
      int m = new Random().nextInt(1000);
      if (this.k != null)
      {
        Iterator localIterator2 = this.k.keySet().iterator();
        while (localIterator2.hasNext())
        {
          c.a locala2 = (c.a)localIterator2.next();
          String str2 = f;
          Object[] arrayOfObject2 = new Object[1];
          arrayOfObject2[0] = ("_" + m + " downling  " + locala2.c + "   " + locala2.d);
          Log.d(str2, arrayOfObject2);
        }
      }
      String str1 = f;
      Object[] arrayOfObject1 = new Object[1];
      arrayOfObject1[0] = ("_" + m + "downling  null");
      Log.d(str1, arrayOfObject1);
    }
    if (this.k == null)
      return false;
    Iterator localIterator1 = this.k.keySet().iterator();
    while (localIterator1.hasNext())
    {
      c.a locala1 = (c.a)localIterator1.next();
      if ((parama.f != null) && (parama.f.equals(locala1.f)))
      {
        this.k.put(locala1, paramMessenger);
        return true;
      }
      if (locala1.d.equals(parama.d))
      {
        this.k.put(locala1, paramMessenger);
        return true;
      }
    }
    return false;
  }

  int b(c.a parama)
  {
    for (int m = 0; m < this.j.size(); m++)
    {
      int n = this.j.keyAt(m);
      if ((parama.f != null) && (parama.f.equals(((c)this.j.get(n)).e.f)))
        return ((c)this.j.get(n)).c;
      if (((c)this.j.get(n)).e.d.equals(parama.d))
        return ((c)this.j.get(n)).c;
    }
    return -1;
  }

  final void b(long paramLong1, long paramLong2, long paramLong3, c.a parama)
  {
    String[] arrayOfString = parama.k;
    if (arrayOfString != null)
    {
      Log.i("sendPauseReport.", new Object[0]);
      int m = arrayOfString.length;
      for (int n = 0; n < m; n++)
      {
        String str = arrayOfString[n];
        d locald = a(parama.a, str);
        Long[] arrayOfLong = new Long[3];
        arrayOfLong[0] = Long.valueOf(paramLong1);
        arrayOfLong[1] = Long.valueOf(paramLong2);
        arrayOfLong[2] = Long.valueOf(paramLong3);
        locald.a(arrayOfLong);
        this.g.a(locald);
      }
    }
  }

  void b(Context paramContext, int paramInt)
  {
    NotificationManager localNotificationManager = (NotificationManager)paramContext.getApplicationContext().getSystemService("notification");
    c localc = (c)this.j.get(paramInt);
    if (localc != null)
    {
      String str = f;
      Object[] arrayOfObject = new Object[1];
      arrayOfObject[0] = ("download service clear cache " + localc.e.c);
      Log.d(str, arrayOfObject);
      if (localc.a != null)
        localc.a.a(2);
      localNotificationManager.cancel(localc.c);
      if (this.k.containsKey(localc.e))
        this.k.remove(localc.e);
      localc.b(this.j);
      this.l.b(paramInt);
    }
  }

  final void c(long paramLong1, long paramLong2, long paramLong3, c.a parama)
  {
    String[] arrayOfString = parama.h;
    if (arrayOfString != null)
    {
      Log.i("sendGoOnReport.", new Object[0]);
      int m = arrayOfString.length;
      for (int n = 0; n < m; n++)
      {
        String str = arrayOfString[n];
        d locald = a(parama.a, str);
        Long[] arrayOfLong = new Long[3];
        arrayOfLong[0] = Long.valueOf(paramLong1);
        arrayOfLong[1] = Long.valueOf(paramLong2);
        arrayOfLong[2] = Long.valueOf(paramLong3);
        locald.a(arrayOfLong);
        this.g.a(locald);
      }
    }
  }

  final void c(c.a parama)
  {
    int m = 0;
    String[] arrayOfString = parama.j;
    if (arrayOfString != null)
    {
      Log.i("sendStartReport.", new Object[0]);
      int n = arrayOfString.length;
      while (m < n)
      {
        String str = arrayOfString[m];
        d locald = a(parama.a, str);
        this.g.a(locald);
        m++;
      }
    }
  }

  final void d(long paramLong1, long paramLong2, long paramLong3, c.a parama)
  {
    String[] arrayOfString = parama.l;
    if (arrayOfString != null)
    {
      Log.i("sendCancelReport.", new Object[0]);
      int m = arrayOfString.length;
      for (int n = 0; n < m; n++)
      {
        String str = arrayOfString[n];
        d locald = a(parama.a, str);
        Long[] arrayOfLong = new Long[3];
        arrayOfLong[0] = Long.valueOf(paramLong1);
        arrayOfLong[1] = Long.valueOf(paramLong2);
        arrayOfLong[2] = Long.valueOf(paramLong3);
        locald.a(arrayOfLong);
        this.g.a(locald);
      }
    }
  }

  final void d(c.a parama)
  {
    int m = 0;
    String[] arrayOfString = parama.g;
    if (arrayOfString != null)
    {
      Log.i("sendSuccessReport.", new Object[0]);
      int n = arrayOfString.length;
      while (m < n)
      {
        String str = arrayOfString[m];
        d locald = a(parama.a, str);
        this.g.a(locald);
        m++;
      }
    }
  }

  static class a extends h
  {
    String a;
    String b;
    String c;

    public a(Context paramContext)
    {
      super();
    }

    public Notification a()
    {
      if (Build.VERSION.SDK_INT >= 16)
        return this.f.build();
      this.e.setLatestEventInfo(this.d, this.a, this.c, this.g);
      return this.e;
    }

    public a a(int paramInt1, int paramInt2, boolean paramBoolean)
    {
      if (Build.VERSION.SDK_INT >= 16)
      {
        this.f.setProgress(paramInt1, paramInt2, paramBoolean);
        return this;
      }
      this.c = (paramInt2 + "%");
      return this;
    }

    public a a(CharSequence paramCharSequence)
    {
      if (Build.VERSION.SDK_INT >= 16)
      {
        this.f.setContentText(paramCharSequence);
        return this;
      }
      this.b = paramCharSequence.toString();
      return this;
    }

    public void a(int paramInt, String paramString, PendingIntent paramPendingIntent)
    {
      if (Build.VERSION.SDK_INT >= 16)
        this.f.addAction(paramInt, paramString, paramPendingIntent);
    }

    public a b(CharSequence paramCharSequence)
    {
      if (Build.VERSION.SDK_INT >= 16)
      {
        this.f.setContentTitle(paramCharSequence);
        return this;
      }
      this.a = paramCharSequence.toString();
      return this;
    }
  }

  static class b
  {
    public static final int a = 448;
    public static final int b = 256;
    public static final int c = 128;
    public static final int d = 64;
    public static final int e = 56;
    public static final int f = 32;
    public static final int g = 16;
    public static final int h = 8;
    public static final int i = 7;
    public static final int j = 4;
    public static final int k = 2;
    public static final int l = 1;
  }

  static class c
  {
    DownloadingService.b a;
    f.a b;
    int c;
    int d;
    c.a e;
    long[] f = new long[3];

    public c(c.a parama, int paramInt)
    {
      this.c = paramInt;
      this.e = parama;
    }

    public void a(SparseArray<c> paramSparseArray)
    {
      paramSparseArray.put(this.c, this);
    }

    public void b(SparseArray<c> paramSparseArray)
    {
      if (paramSparseArray.indexOfKey(this.c) >= 0)
        paramSparseArray.remove(this.c);
    }
  }

  class d extends AsyncTask<String, Void, Integer>
  {
    public int a;
    public String b;
    private c.a d;
    private Context e;
    private NotificationManager f;

    public d(Context paramInt, int parama, c.a paramString, String arg5)
    {
      this.e = paramInt.getApplicationContext();
      this.f = ((NotificationManager)this.e.getSystemService("notification"));
      this.a = parama;
      this.d = paramString;
      Object localObject;
      this.b = localObject;
    }

    protected Integer a(String[] paramArrayOfString)
    {
      return Integer.valueOf(1);
    }

    protected void a(Integer paramInteger)
    {
      if (paramInteger.intValue() == 1)
      {
        Notification localNotification = new Notification(17301634, k.h, System.currentTimeMillis());
        Intent localIntent = new Intent("android.intent.action.VIEW");
        localIntent.addFlags(268435456);
        localIntent.setDataAndType(Uri.fromFile(new File(this.b)), "application/vnd.android.package-archive");
        PendingIntent localPendingIntent = PendingIntent.getActivity(this.e, 0, localIntent, 134217728);
        localNotification.setLatestEventInfo(this.e, f.c(this.e), k.h, localPendingIntent);
        localNotification.flags = 16;
        this.f.notify(1 + this.a, localNotification);
        if (f.this.a(this.e))
        {
          this.f.cancel(1 + this.a);
          this.e.startActivity(localIntent);
        }
        Bundle localBundle1 = new Bundle();
        localBundle1.putString("filename", this.b);
        Message localMessage1 = Message.obtain();
        localMessage1.what = 5;
        localMessage1.arg1 = 1;
        localMessage1.arg2 = this.a;
        localMessage1.setData(localBundle1);
        try
        {
          if (f.a(f.this).get(this.d) != null)
            ((Messenger)f.a(f.this).get(this.d)).send(localMessage1);
          f.this.b(this.e, this.a);
          return;
        }
        catch (RemoteException localRemoteException1)
        {
          f.this.b(this.e, this.a);
          return;
        }
      }
      this.f.cancel(1 + this.a);
      Bundle localBundle2 = new Bundle();
      localBundle2.putString("filename", this.b);
      Message localMessage2 = Message.obtain();
      localMessage2.what = 5;
      localMessage2.arg1 = 3;
      localMessage2.arg2 = this.a;
      localMessage2.setData(localBundle2);
      try
      {
        if (f.a(f.this).get(this.d) != null)
          ((Messenger)f.a(f.this).get(this.d)).send(localMessage2);
        f.this.b(this.e, this.a);
        return;
      }
      catch (RemoteException localRemoteException2)
      {
        f.this.b(this.e, this.a);
      }
    }

    protected void onPreExecute()
    {
      super.onPreExecute();
    }
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.taobao.munion.base.download.f
 * JD-Core Version:    0.6.2
 */