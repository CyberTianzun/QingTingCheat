package com.taobao.munion.base;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.MemoryInfo;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.ResolveInfo;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.location.Location;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.NetworkInfo.State;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings.Secure;
import android.provider.Settings.SettingNotFoundException;
import android.provider.Settings.System;
import android.text.TextUtils;
import android.util.TypedValue;
import java.util.List;
import java.util.Locale;

public class DefaultAppUtils
  implements a
{
  private a a;
  private String b;
  private String c;
  private String d;
  private String e;
  private String f;
  private String g;
  private String h;
  private String i;
  private String j;
  private String k;
  private String l;
  private String m;
  private String n;
  private String o;
  private float p;
  private int q;
  private int r;
  private String s;
  private String t;
  private Context u;
  private boolean v = false;
  private boolean w = false;
  private boolean x = false;
  private boolean y = false;
  private String z = null;

  public static Intent a(Context paramContext)
  {
    PackageManager localPackageManager = paramContext.getPackageManager();
    Intent localIntent = new Intent("android.intent.action.MAIN");
    localIntent.addCategory("android.intent.category.LAUNCHER");
    localIntent.setPackage(paramContext.getPackageName());
    List localList = localPackageManager.queryIntentActivities(localIntent, 0);
    if ((localList != null) && (localList.size() > 0))
    {
      ResolveInfo localResolveInfo = (ResolveInfo)localList.get(0);
      localIntent.setFlags(67108864);
      localIntent.setComponent(new ComponentName(localResolveInfo.activityInfo.applicationInfo.packageName, localResolveInfo.activityInfo.name));
      return localIntent;
    }
    return null;
  }

  public static boolean a(Activity paramActivity)
  {
    Intent localIntent = a(paramActivity);
    if (localIntent != null)
    {
      paramActivity.startActivity(localIntent);
      return true;
    }
    return false;
  }

  private String g(String paramString)
  {
    if (TextUtils.isEmpty(paramString))
      return this.a.b;
    return paramString.replaceAll(" ", "");
  }

  public int A()
  {
    Intent localIntent = this.u.getApplicationContext().registerReceiver(null, new IntentFilter("android.intent.action.BATTERY_CHANGED"));
    int i1 = localIntent.getIntExtra("level", 0);
    int i2 = localIntent.getIntExtra("scale", 100);
    return i1 * 100 / i2;
  }

  public String B()
  {
    return this.i;
  }

  public String C()
  {
    return this.g;
  }

  public String[] D()
  {
    String[] arrayOfString = new String[2];
    arrayOfString[0] = this.a.b;
    arrayOfString[1] = this.a.b;
    if (this.u != null)
    {
      if (!e("android.permission.ACCESS_NETWORK_STATE"))
        return arrayOfString;
      ConnectivityManager localConnectivityManager = (ConnectivityManager)this.u.getSystemService("connectivity");
      if (localConnectivityManager == null)
        return arrayOfString;
      if (localConnectivityManager.getNetworkInfo(1).getState() == NetworkInfo.State.CONNECTED)
      {
        arrayOfString[0] = this.a.d;
        return arrayOfString;
      }
      NetworkInfo localNetworkInfo = localConnectivityManager.getNetworkInfo(0);
      if (localNetworkInfo.getState() == NetworkInfo.State.CONNECTED)
      {
        arrayOfString[0] = this.a.c;
        arrayOfString[1] = localNetworkInfo.getSubtypeName();
        return arrayOfString;
      }
    }
    return arrayOfString;
  }

  public String[] E()
  {
    String[] arrayOfString = new String[2];
    arrayOfString[0] = this.a.b;
    arrayOfString[1] = this.a.b;
    if (this.u != null)
    {
      if (!e("android.permission.ACCESS_NETWORK_STATE"))
        return arrayOfString;
      ConnectivityManager localConnectivityManager = (ConnectivityManager)this.u.getSystemService("connectivity");
      if (localConnectivityManager == null)
        return arrayOfString;
      if (localConnectivityManager.getNetworkInfo(1).getState() == NetworkInfo.State.CONNECTED)
      {
        arrayOfString[0] = "wifi";
        return arrayOfString;
      }
      NetworkInfo localNetworkInfo = localConnectivityManager.getNetworkInfo(0);
      if (localNetworkInfo.getState() == NetworkInfo.State.CONNECTED)
      {
        arrayOfString[0] = this.a.c;
        arrayOfString[1] = localNetworkInfo.getSubtypeName();
        return arrayOfString;
      }
    }
    return arrayOfString;
  }

  public Location F()
  {
    try
    {
      LocationManager localLocationManager = (LocationManager)this.u.getSystemService("location");
      if (e("android.permission.ACCESS_FINE_LOCATION"))
      {
        Location localLocation2 = localLocationManager.getLastKnownLocation("gps");
        if (localLocation2 != null)
        {
          Log.d("get location from gps:" + localLocation2.getLatitude() + "," + localLocation2.getLongitude(), new Object[0]);
          return localLocation2;
        }
      }
      if (e("android.permission.ACCESS_COARSE_LOCATION"))
      {
        Location localLocation1 = localLocationManager.getLastKnownLocation("network");
        if (localLocation1 != null)
        {
          Log.d("get location from network:" + localLocation1.getLatitude() + "," + localLocation1.getLongitude(), new Object[0]);
          return localLocation1;
        }
      }
    }
    catch (Exception localException)
    {
      Log.e(localException, "", new Object[0]);
    }
    while (true)
    {
      return null;
      Log.i("Could not get loction from GPS or Cell-id, lack ACCESS_COARSE_LOCATION or ACCESS_COARSE_LOCATION permission?", new Object[0]);
    }
  }

  public String G()
  {
    return this.s;
  }

  public String H()
  {
    return this.t;
  }

  public float a(float paramFloat)
  {
    return TypedValue.applyDimension(1, paramFloat, this.u.getResources().getDisplayMetrics());
  }

  protected void a(Context paramContext, Class<?>[] paramArrayOfClass)
  {
    int i1 = paramArrayOfClass.length;
    for (int i2 = 0; i2 < i1; i2++)
    {
      Class<?> localClass = paramArrayOfClass[i2];
      if (c(localClass) == null)
        Log.w("No activity element declared for [" + localClass.getName() + "].  Please ensure you have included this in your AndroidManifest.xml", new Object[0]);
    }
  }

  void a(boolean paramBoolean)
  {
    this.y = paramBoolean;
  }

  public boolean a()
  {
    return false;
  }

  public boolean a(Intent paramIntent)
  {
    return this.u.getPackageManager().queryIntentActivities(paramIntent, 65536).size() > 0;
  }

  public boolean a(Class<?> paramClass)
  {
    return a(new Intent(this.u, paramClass));
  }

  public boolean a(String paramString)
  {
    try
    {
      boolean bool = a(Class.forName(paramString));
      return bool;
    }
    catch (ClassNotFoundException localClassNotFoundException)
    {
    }
    return false;
  }

  public int b(Context paramContext)
  {
    return paramContext.getApplicationInfo().icon;
  }

  public boolean b()
  {
    return false;
  }

  public boolean b(Class<?> paramClass)
  {
    return this.u.getPackageManager().queryIntentServices(new Intent(this.u, paramClass), 65536).size() > 0;
  }

  public boolean b(String paramString)
  {
    return a(new Intent(paramString));
  }

  public ActivityInfo c(Class<?> paramClass)
  {
    try
    {
      ActivityInfo localActivityInfo = this.u.getPackageManager().getActivityInfo(new ComponentName(this.u, paramClass), 0);
      return localActivityInfo;
    }
    catch (PackageManager.NameNotFoundException localNameNotFoundException)
    {
      Log.e(localNameNotFoundException, "Failed to locate info for activity [" + paramClass.getName() + "]", new Object[0]);
    }
    return null;
  }

  public boolean c()
  {
    try
    {
      NetworkInfo localNetworkInfo = ((ConnectivityManager)this.u.getSystemService("connectivity")).getActiveNetworkInfo();
      if (localNetworkInfo != null)
      {
        boolean bool = localNetworkInfo.isConnectedOrConnecting();
        return bool;
      }
    }
    catch (Exception localException)
    {
      return false;
    }
    return false;
  }

  public boolean c(String paramString)
  {
    if (this.u != null)
    {
      PackageManager localPackageManager = this.u.getPackageManager();
      try
      {
        localPackageManager.getPackageInfo(paramString, 1);
        return true;
      }
      catch (PackageManager.NameNotFoundException localNameNotFoundException)
      {
        return false;
      }
    }
    return false;
  }

  public String d(String paramString)
  {
    if ((paramString != null) && (paramString.equalsIgnoreCase("amazon")))
      return "amz";
    return null;
  }

  public boolean d()
  {
    return Environment.getExternalStorageState().equals("mounted");
  }

  public boolean d(Class<?> paramClass)
  {
    return this.u.getPackageManager().queryBroadcastReceivers(new Intent(this.u, paramClass), 65536).size() > 0;
  }

  public boolean e()
  {
    Context localContext = this.u;
    boolean bool = false;
    if (localContext != null)
      bool = this.u.getResources().getConfiguration().locale.toString().equals(Locale.CHINA.toString());
    return bool;
  }

  public boolean e(String paramString)
  {
    return this.u.getPackageManager().checkPermission(paramString, this.u.getPackageName()) == 0;
  }

  public String f()
  {
    return this.c;
  }

  public String f(String paramString)
  {
    if (this.u != null)
      try
      {
        ApplicationInfo localApplicationInfo = this.u.getPackageManager().getApplicationInfo(this.u.getPackageName(), 128);
        if ((localApplicationInfo != null) && (localApplicationInfo.metaData != null))
        {
          Object localObject = localApplicationInfo.metaData.get(paramString);
          if (localObject != null)
          {
            String str = localObject.toString();
            return str;
          }
        }
      }
      catch (Exception localException)
      {
        Log.e("Could not read meta-data from AndroidManifest.xml.", new Object[0]);
      }
    return null;
  }

  public String g()
  {
    return this.b;
  }

  public String h()
  {
    return this.e;
  }

  public String i()
  {
    return this.f;
  }

  public void init(Context paramContext)
  {
    init(paramContext, new a());
  }

  // ERROR //
  public void init(Context paramContext, a parama)
  {
    // Byte code:
    //   0: aload_0
    //   1: aload_1
    //   2: invokevirtual 168	android/content/Context:getApplicationContext	()Landroid/content/Context;
    //   5: putfield 164	com/taobao/munion/base/DefaultAppUtils:u	Landroid/content/Context;
    //   8: aload_0
    //   9: aload_2
    //   10: putfield 147	com/taobao/munion/base/DefaultAppUtils:a	Lcom/taobao/munion/base/DefaultAppUtils$a;
    //   13: aload_0
    //   14: aload_0
    //   15: aload_1
    //   16: invokevirtual 76	android/content/Context:getPackageName	()Ljava/lang/String;
    //   19: invokespecial 453	com/taobao/munion/base/DefaultAppUtils:g	(Ljava/lang/String;)Ljava/lang/String;
    //   22: putfield 439	com/taobao/munion/base/DefaultAppUtils:b	Ljava/lang/String;
    //   25: aload_1
    //   26: invokevirtual 59	android/content/Context:getPackageManager	()Landroid/content/pm/PackageManager;
    //   29: astore 21
    //   31: aload_0
    //   32: aload 21
    //   34: aload 21
    //   36: aload_0
    //   37: getfield 439	com/taobao/munion/base/DefaultAppUtils:b	Ljava/lang/String;
    //   40: iconst_0
    //   41: invokevirtual 425	android/content/pm/PackageManager:getApplicationInfo	(Ljava/lang/String;I)Landroid/content/pm/ApplicationInfo;
    //   44: invokevirtual 457	android/content/pm/PackageManager:getApplicationLabel	(Landroid/content/pm/ApplicationInfo;)Ljava/lang/CharSequence;
    //   47: invokeinterface 460 1 0
    //   52: putfield 422	com/taobao/munion/base/DefaultAppUtils:c	Ljava/lang/String;
    //   55: aload_0
    //   56: getfield 422	com/taobao/munion/base/DefaultAppUtils:c	Ljava/lang/String;
    //   59: invokestatic 145	android/text/TextUtils:isEmpty	(Ljava/lang/CharSequence;)Z
    //   62: ifeq +11 -> 73
    //   65: aload_0
    //   66: aload_0
    //   67: getfield 439	com/taobao/munion/base/DefaultAppUtils:b	Ljava/lang/String;
    //   70: putfield 422	com/taobao/munion/base/DefaultAppUtils:c	Ljava/lang/String;
    //   73: aload_1
    //   74: invokevirtual 59	android/content/Context:getPackageManager	()Landroid/content/pm/PackageManager;
    //   77: aload_1
    //   78: invokevirtual 76	android/content/Context:getPackageName	()Ljava/lang/String;
    //   81: iconst_0
    //   82: invokevirtual 380	android/content/pm/PackageManager:getPackageInfo	(Ljava/lang/String;I)Landroid/content/pm/PackageInfo;
    //   85: astore 20
    //   87: aload_0
    //   88: aload_0
    //   89: aload 20
    //   91: getfield 465	android/content/pm/PackageInfo:versionName	Ljava/lang/String;
    //   94: invokespecial 453	com/taobao/munion/base/DefaultAppUtils:g	(Ljava/lang/String;)Ljava/lang/String;
    //   97: putfield 441	com/taobao/munion/base/DefaultAppUtils:e	Ljava/lang/String;
    //   100: aload_0
    //   101: aload_0
    //   102: aload 20
    //   104: getfield 468	android/content/pm/PackageInfo:versionCode	I
    //   107: invokestatic 472	java/lang/String:valueOf	(I)Ljava/lang/String;
    //   110: invokespecial 453	com/taobao/munion/base/DefaultAppUtils:g	(Ljava/lang/String;)Ljava/lang/String;
    //   113: putfield 443	com/taobao/munion/base/DefaultAppUtils:f	Ljava/lang/String;
    //   116: aload_0
    //   117: ldc_w 474
    //   120: invokevirtual 198	com/taobao/munion/base/DefaultAppUtils:e	(Ljava/lang/String;)Z
    //   123: ifeq +63 -> 186
    //   126: aload_1
    //   127: ldc_w 476
    //   130: invokevirtual 204	android/content/Context:getSystemService	(Ljava/lang/String;)Ljava/lang/Object;
    //   133: checkcast 478	android/telephony/TelephonyManager
    //   136: astore 19
    //   138: aload_0
    //   139: aload 19
    //   141: invokevirtual 481	android/telephony/TelephonyManager:getNetworkCountryIso	()Ljava/lang/String;
    //   144: putfield 188	com/taobao/munion/base/DefaultAppUtils:i	Ljava/lang/String;
    //   147: aload_0
    //   148: aload_0
    //   149: aload 19
    //   151: invokevirtual 484	android/telephony/TelephonyManager:getDeviceId	()Ljava/lang/String;
    //   154: invokespecial 453	com/taobao/munion/base/DefaultAppUtils:g	(Ljava/lang/String;)Ljava/lang/String;
    //   157: putfield 486	com/taobao/munion/base/DefaultAppUtils:l	Ljava/lang/String;
    //   160: aload_0
    //   161: aload_0
    //   162: aload 19
    //   164: invokevirtual 489	android/telephony/TelephonyManager:getSubscriberId	()Ljava/lang/String;
    //   167: invokespecial 453	com/taobao/munion/base/DefaultAppUtils:g	(Ljava/lang/String;)Ljava/lang/String;
    //   170: putfield 491	com/taobao/munion/base/DefaultAppUtils:m	Ljava/lang/String;
    //   173: aload_0
    //   174: aload_0
    //   175: aload 19
    //   177: invokevirtual 494	android/telephony/TelephonyManager:getNetworkOperatorName	()Ljava/lang/String;
    //   180: invokespecial 453	com/taobao/munion/base/DefaultAppUtils:g	(Ljava/lang/String;)Ljava/lang/String;
    //   183: putfield 294	com/taobao/munion/base/DefaultAppUtils:s	Ljava/lang/String;
    //   186: aload_0
    //   187: getfield 188	com/taobao/munion/base/DefaultAppUtils:i	Ljava/lang/String;
    //   190: invokestatic 145	android/text/TextUtils:isEmpty	(Ljava/lang/CharSequence;)Z
    //   193: ifeq +17 -> 210
    //   196: aload_0
    //   197: aload_0
    //   198: invokestatic 498	java/util/Locale:getDefault	()Ljava/util/Locale;
    //   201: invokevirtual 501	java/util/Locale:getCountry	()Ljava/lang/String;
    //   204: invokespecial 453	com/taobao/munion/base/DefaultAppUtils:g	(Ljava/lang/String;)Ljava/lang/String;
    //   207: putfield 188	com/taobao/munion/base/DefaultAppUtils:i	Ljava/lang/String;
    //   210: aload_0
    //   211: ldc_w 503
    //   214: invokevirtual 198	com/taobao/munion/base/DefaultAppUtils:e	(Ljava/lang/String;)Z
    //   217: ifeq +26 -> 243
    //   220: aload_0
    //   221: aload_0
    //   222: aload_1
    //   223: ldc 232
    //   225: invokevirtual 204	android/content/Context:getSystemService	(Ljava/lang/String;)Ljava/lang/Object;
    //   228: checkcast 505	android/net/wifi/WifiManager
    //   231: invokevirtual 509	android/net/wifi/WifiManager:getConnectionInfo	()Landroid/net/wifi/WifiInfo;
    //   234: invokevirtual 514	android/net/wifi/WifiInfo:getMacAddress	()Ljava/lang/String;
    //   237: invokespecial 453	com/taobao/munion/base/DefaultAppUtils:g	(Ljava/lang/String;)Ljava/lang/String;
    //   240: putfield 516	com/taobao/munion/base/DefaultAppUtils:n	Ljava/lang/String;
    //   243: new 518	android/util/DisplayMetrics
    //   246: dup
    //   247: invokespecial 519	android/util/DisplayMetrics:<init>	()V
    //   250: astore 7
    //   252: aload_1
    //   253: ldc_w 521
    //   256: invokevirtual 204	android/content/Context:getSystemService	(Ljava/lang/String;)Ljava/lang/Object;
    //   259: checkcast 523	android/view/WindowManager
    //   262: checkcast 523	android/view/WindowManager
    //   265: invokeinterface 527 1 0
    //   270: aload 7
    //   272: invokevirtual 533	android/view/Display:getMetrics	(Landroid/util/DisplayMetrics;)V
    //   275: aload_0
    //   276: aload 7
    //   278: getfield 536	android/util/DisplayMetrics:widthPixels	I
    //   281: putfield 538	com/taobao/munion/base/DefaultAppUtils:q	I
    //   284: aload_0
    //   285: aload 7
    //   287: getfield 541	android/util/DisplayMetrics:heightPixels	I
    //   290: putfield 543	com/taobao/munion/base/DefaultAppUtils:r	I
    //   293: aload_0
    //   294: new 249	java/lang/StringBuilder
    //   297: dup
    //   298: invokespecial 250	java/lang/StringBuilder:<init>	()V
    //   301: aload_0
    //   302: getfield 543	com/taobao/munion/base/DefaultAppUtils:r	I
    //   305: invokestatic 472	java/lang/String:valueOf	(I)Ljava/lang/String;
    //   308: invokevirtual 256	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   311: aload_0
    //   312: getfield 147	com/taobao/munion/base/DefaultAppUtils:a	Lcom/taobao/munion/base/DefaultAppUtils$a;
    //   315: getfield 545	com/taobao/munion/base/DefaultAppUtils$a:a	Ljava/lang/String;
    //   318: invokevirtual 256	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   321: aload_0
    //   322: getfield 538	com/taobao/munion/base/DefaultAppUtils:q	I
    //   325: invokestatic 472	java/lang/String:valueOf	(I)Ljava/lang/String;
    //   328: invokevirtual 256	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   331: invokevirtual 273	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   334: putfield 547	com/taobao/munion/base/DefaultAppUtils:o	Ljava/lang/String;
    //   337: aload_0
    //   338: aload 7
    //   340: getfield 550	android/util/DisplayMetrics:density	F
    //   343: putfield 552	com/taobao/munion/base/DefaultAppUtils:p	F
    //   346: aload_1
    //   347: invokevirtual 302	android/content/Context:getResources	()Landroid/content/res/Resources;
    //   350: invokevirtual 405	android/content/res/Resources:getConfiguration	()Landroid/content/res/Configuration;
    //   353: astore 17
    //   355: aload 17
    //   357: ifnull +249 -> 606
    //   360: aload 17
    //   362: getfield 411	android/content/res/Configuration:locale	Ljava/util/Locale;
    //   365: ifnull +241 -> 606
    //   368: aload_0
    //   369: aload_0
    //   370: aload 17
    //   372: getfield 411	android/content/res/Configuration:locale	Ljava/util/Locale;
    //   375: invokevirtual 555	java/util/Locale:getDisplayName	()Ljava/lang/String;
    //   378: invokespecial 453	com/taobao/munion/base/DefaultAppUtils:g	(Ljava/lang/String;)Ljava/lang/String;
    //   381: putfield 557	com/taobao/munion/base/DefaultAppUtils:j	Ljava/lang/String;
    //   384: aload_0
    //   385: aload_0
    //   386: aload 17
    //   388: getfield 411	android/content/res/Configuration:locale	Ljava/util/Locale;
    //   391: invokevirtual 414	java/util/Locale:toString	()Ljava/lang/String;
    //   394: invokespecial 453	com/taobao/munion/base/DefaultAppUtils:g	(Ljava/lang/String;)Ljava/lang/String;
    //   397: putfield 559	com/taobao/munion/base/DefaultAppUtils:h	Ljava/lang/String;
    //   400: aload_0
    //   401: aload_0
    //   402: aload 17
    //   404: getfield 411	android/content/res/Configuration:locale	Ljava/util/Locale;
    //   407: invokestatic 565	java/util/Calendar:getInstance	(Ljava/util/Locale;)Ljava/util/Calendar;
    //   410: invokevirtual 569	java/util/Calendar:getTimeZone	()Ljava/util/TimeZone;
    //   413: invokevirtual 574	java/util/TimeZone:getRawOffset	()I
    //   416: ldc_w 575
    //   419: idiv
    //   420: invokestatic 472	java/lang/String:valueOf	(I)Ljava/lang/String;
    //   423: invokespecial 453	com/taobao/munion/base/DefaultAppUtils:g	(Ljava/lang/String;)Ljava/lang/String;
    //   426: putfield 577	com/taobao/munion/base/DefaultAppUtils:k	Ljava/lang/String;
    //   429: new 579	java/io/FileReader
    //   432: dup
    //   433: ldc_w 581
    //   436: invokespecial 582	java/io/FileReader:<init>	(Ljava/lang/String;)V
    //   439: astore 10
    //   441: aload 10
    //   443: ifnull +71 -> 514
    //   446: new 584	java/io/BufferedReader
    //   449: dup
    //   450: aload 10
    //   452: sipush 1024
    //   455: invokespecial 587	java/io/BufferedReader:<init>	(Ljava/io/Reader;I)V
    //   458: astore 12
    //   460: aload 12
    //   462: invokevirtual 590	java/io/BufferedReader:readLine	()Ljava/lang/String;
    //   465: astore 16
    //   467: aload 16
    //   469: ifnull +25 -> 494
    //   472: aload_0
    //   473: aload_0
    //   474: aload 16
    //   476: iconst_1
    //   477: aload 16
    //   479: bipush 58
    //   481: invokevirtual 594	java/lang/String:indexOf	(I)I
    //   484: iadd
    //   485: invokevirtual 597	java/lang/String:substring	(I)Ljava/lang/String;
    //   488: invokespecial 453	com/taobao/munion/base/DefaultAppUtils:g	(Ljava/lang/String;)Ljava/lang/String;
    //   491: putfield 297	com/taobao/munion/base/DefaultAppUtils:t	Ljava/lang/String;
    //   494: aload 12
    //   496: ifnull +8 -> 504
    //   499: aload 12
    //   501: invokevirtual 600	java/io/BufferedReader:close	()V
    //   504: aload 10
    //   506: ifnull +8 -> 514
    //   509: aload 10
    //   511: invokevirtual 601	java/io/FileReader:close	()V
    //   514: aload_0
    //   515: aload_1
    //   516: invokestatic 606	com/taobao/munion/base/utdid/a:a	(Landroid/content/Context;)Ljava/lang/String;
    //   519: putfield 191	com/taobao/munion/base/DefaultAppUtils:g	Ljava/lang/String;
    //   522: return
    //   523: astore_3
    //   524: aload_3
    //   525: ldc_w 608
    //   528: iconst_0
    //   529: anewarray 4	java/lang/Object
    //   532: invokestatic 287	com/taobao/munion/base/Log:e	(Ljava/lang/Throwable;Ljava/lang/String;[Ljava/lang/Object;)V
    //   535: goto -480 -> 55
    //   538: astore 4
    //   540: aload 4
    //   542: ldc_w 610
    //   545: iconst_0
    //   546: anewarray 4	java/lang/Object
    //   549: invokestatic 287	com/taobao/munion/base/Log:e	(Ljava/lang/Throwable;Ljava/lang/String;[Ljava/lang/Object;)V
    //   552: goto -436 -> 116
    //   555: astore 5
    //   557: aload 5
    //   559: ldc_w 612
    //   562: iconst_0
    //   563: anewarray 4	java/lang/Object
    //   566: invokestatic 287	com/taobao/munion/base/Log:e	(Ljava/lang/Throwable;Ljava/lang/String;[Ljava/lang/Object;)V
    //   569: goto -383 -> 186
    //   572: astore 6
    //   574: aload 6
    //   576: ldc_w 614
    //   579: iconst_0
    //   580: anewarray 4	java/lang/Object
    //   583: invokestatic 287	com/taobao/munion/base/Log:e	(Ljava/lang/Throwable;Ljava/lang/String;[Ljava/lang/Object;)V
    //   586: goto -343 -> 243
    //   589: astore 8
    //   591: aload 8
    //   593: ldc_w 616
    //   596: iconst_0
    //   597: anewarray 4	java/lang/Object
    //   600: invokestatic 287	com/taobao/munion/base/Log:e	(Ljava/lang/Throwable;Ljava/lang/String;[Ljava/lang/Object;)V
    //   603: goto -257 -> 346
    //   606: aload_0
    //   607: getfield 147	com/taobao/munion/base/DefaultAppUtils:a	Lcom/taobao/munion/base/DefaultAppUtils$a;
    //   610: getfield 151	com/taobao/munion/base/DefaultAppUtils$a:b	Ljava/lang/String;
    //   613: astore 18
    //   615: aload_0
    //   616: aload 18
    //   618: putfield 559	com/taobao/munion/base/DefaultAppUtils:h	Ljava/lang/String;
    //   621: aload_0
    //   622: aload 18
    //   624: putfield 557	com/taobao/munion/base/DefaultAppUtils:j	Ljava/lang/String;
    //   627: goto -198 -> 429
    //   630: astore 9
    //   632: aload 9
    //   634: ldc_w 618
    //   637: iconst_0
    //   638: anewarray 4	java/lang/Object
    //   641: invokestatic 287	com/taobao/munion/base/Log:e	(Ljava/lang/Throwable;Ljava/lang/String;[Ljava/lang/Object;)V
    //   644: goto -215 -> 429
    //   647: astore 13
    //   649: aconst_null
    //   650: astore 12
    //   652: ldc_w 620
    //   655: iconst_1
    //   656: anewarray 4	java/lang/Object
    //   659: dup
    //   660: iconst_0
    //   661: aload 13
    //   663: aastore
    //   664: invokestatic 438	com/taobao/munion/base/Log:e	(Ljava/lang/String;[Ljava/lang/Object;)V
    //   667: aload 12
    //   669: ifnull +8 -> 677
    //   672: aload 12
    //   674: invokevirtual 600	java/io/BufferedReader:close	()V
    //   677: aload 10
    //   679: ifnull -165 -> 514
    //   682: aload 10
    //   684: invokevirtual 601	java/io/FileReader:close	()V
    //   687: goto -173 -> 514
    //   690: astore 15
    //   692: ldc_w 622
    //   695: iconst_1
    //   696: anewarray 4	java/lang/Object
    //   699: dup
    //   700: iconst_0
    //   701: aload 15
    //   703: aastore
    //   704: invokestatic 438	com/taobao/munion/base/Log:e	(Ljava/lang/String;[Ljava/lang/Object;)V
    //   707: goto -193 -> 514
    //   710: astore 14
    //   712: aconst_null
    //   713: astore 12
    //   715: aload 12
    //   717: ifnull +8 -> 725
    //   720: aload 12
    //   722: invokevirtual 600	java/io/BufferedReader:close	()V
    //   725: aload 10
    //   727: ifnull +8 -> 735
    //   730: aload 10
    //   732: invokevirtual 601	java/io/FileReader:close	()V
    //   735: aload 14
    //   737: athrow
    //   738: astore 11
    //   740: aload 11
    //   742: ldc_w 624
    //   745: iconst_0
    //   746: anewarray 4	java/lang/Object
    //   749: invokestatic 287	com/taobao/munion/base/Log:e	(Ljava/lang/Throwable;Ljava/lang/String;[Ljava/lang/Object;)V
    //   752: return
    //   753: astore 14
    //   755: goto -40 -> 715
    //   758: astore 13
    //   760: goto -108 -> 652
    //
    // Exception table:
    //   from	to	target	type
    //   25	55	523	java/lang/Exception
    //   73	116	538	java/lang/Exception
    //   116	186	555	java/lang/Exception
    //   210	243	572	java/lang/Exception
    //   243	346	589	java/lang/Exception
    //   346	355	630	java/lang/Exception
    //   360	429	630	java/lang/Exception
    //   606	627	630	java/lang/Exception
    //   446	460	647	java/io/IOException
    //   429	441	690	java/lang/Exception
    //   499	504	690	java/lang/Exception
    //   509	514	690	java/lang/Exception
    //   672	677	690	java/lang/Exception
    //   682	687	690	java/lang/Exception
    //   720	725	690	java/lang/Exception
    //   730	735	690	java/lang/Exception
    //   735	738	690	java/lang/Exception
    //   446	460	710	finally
    //   514	522	738	java/lang/Exception
    //   460	467	753	finally
    //   472	494	753	finally
    //   652	667	753	finally
    //   460	467	758	java/io/IOException
    //   472	494	758	java/io/IOException
  }

  public String j()
  {
    return this.d;
  }

  public void k()
  {
  }

  public boolean l()
  {
    return (this.u != null) && (this.u.getResources().getConfiguration().orientation == 1);
  }

  public String m()
  {
    return this.h;
  }

  public String n()
  {
    return this.j;
  }

  public String o()
  {
    return this.k;
  }

  public String p()
  {
    return this.n;
  }

  public String q()
  {
    return this.l;
  }

  public String r()
  {
    if ((!TextUtils.isEmpty(this.l)) && (!this.a.b.equals(this.l)))
      return this.l;
    if ((!TextUtils.isEmpty(this.n)) && (!this.a.b.equals(this.n)))
      return this.n;
    return Settings.Secure.getString(this.u.getContentResolver(), "android_id");
  }

  public String s()
  {
    return this.m;
  }

  public String t()
  {
    return this.o;
  }

  public float u()
  {
    return this.p;
  }

  public int v()
  {
    Context localContext = this.u;
    int i1 = 0;
    if (localContext != null);
    try
    {
      int i2 = Settings.System.getInt(this.u.getContentResolver(), "screen_brightness");
      i1 = i2;
      return i1;
    }
    catch (Settings.SettingNotFoundException localSettingNotFoundException)
    {
      Log.w("Get screen bright exception,info:" + localSettingNotFoundException.toString(), new Object[0]);
    }
    return 0;
  }

  // ERROR //
  public int w()
  {
    // Byte code:
    //   0: aconst_null
    //   1: astore_1
    //   2: iconst_m1
    //   3: istore_2
    //   4: new 579	java/io/FileReader
    //   7: dup
    //   8: ldc_w 656
    //   11: invokespecial 582	java/io/FileReader:<init>	(Ljava/lang/String;)V
    //   14: astore_3
    //   15: new 584	java/io/BufferedReader
    //   18: dup
    //   19: aload_3
    //   20: sipush 1024
    //   23: invokespecial 587	java/io/BufferedReader:<init>	(Ljava/io/Reader;I)V
    //   26: astore 4
    //   28: aload 4
    //   30: invokevirtual 590	java/io/BufferedReader:readLine	()Ljava/lang/String;
    //   33: astore 14
    //   35: aload 14
    //   37: ifnull +35 -> 72
    //   40: aload 14
    //   42: invokevirtual 659	java/lang/String:trim	()Ljava/lang/String;
    //   45: invokevirtual 662	java/lang/String:length	()I
    //   48: ifle +24 -> 72
    //   51: aload 14
    //   53: ldc_w 664
    //   56: invokevirtual 668	java/lang/String:split	(Ljava/lang/String;)[Ljava/lang/String;
    //   59: iconst_1
    //   60: aaload
    //   61: invokestatic 673	java/lang/Integer:valueOf	(Ljava/lang/String;)Ljava/lang/Integer;
    //   64: invokevirtual 676	java/lang/Integer:intValue	()I
    //   67: sipush 1024
    //   70: idiv
    //   71: istore_2
    //   72: aload 4
    //   74: ifnull +8 -> 82
    //   77: aload 4
    //   79: invokevirtual 600	java/io/BufferedReader:close	()V
    //   82: aload_3
    //   83: ifnull +7 -> 90
    //   86: aload_3
    //   87: invokevirtual 601	java/io/FileReader:close	()V
    //   90: iload_2
    //   91: ireturn
    //   92: astore 16
    //   94: new 249	java/lang/StringBuilder
    //   97: dup
    //   98: invokespecial 250	java/lang/StringBuilder:<init>	()V
    //   101: ldc_w 678
    //   104: invokevirtual 256	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   107: aload 16
    //   109: invokevirtual 679	java/io/IOException:toString	()Ljava/lang/String;
    //   112: invokevirtual 256	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   115: invokevirtual 273	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   118: iconst_0
    //   119: anewarray 4	java/lang/Object
    //   122: invokestatic 329	com/taobao/munion/base/Log:w	(Ljava/lang/String;[Ljava/lang/Object;)V
    //   125: goto -43 -> 82
    //   128: astore 15
    //   130: new 249	java/lang/StringBuilder
    //   133: dup
    //   134: invokespecial 250	java/lang/StringBuilder:<init>	()V
    //   137: ldc_w 681
    //   140: invokevirtual 256	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   143: aload 15
    //   145: invokevirtual 679	java/io/IOException:toString	()Ljava/lang/String;
    //   148: invokevirtual 256	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   151: invokevirtual 273	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   154: iconst_0
    //   155: anewarray 4	java/lang/Object
    //   158: invokestatic 329	com/taobao/munion/base/Log:w	(Ljava/lang/String;[Ljava/lang/Object;)V
    //   161: iload_2
    //   162: ireturn
    //   163: astore 5
    //   165: aconst_null
    //   166: astore 4
    //   168: new 249	java/lang/StringBuilder
    //   171: dup
    //   172: invokespecial 250	java/lang/StringBuilder:<init>	()V
    //   175: ldc_w 683
    //   178: invokevirtual 256	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   181: aload 5
    //   183: invokevirtual 679	java/io/IOException:toString	()Ljava/lang/String;
    //   186: invokevirtual 256	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   189: invokevirtual 273	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   192: iconst_0
    //   193: anewarray 4	java/lang/Object
    //   196: invokestatic 329	com/taobao/munion/base/Log:w	(Ljava/lang/String;[Ljava/lang/Object;)V
    //   199: aload 4
    //   201: ifnull +8 -> 209
    //   204: aload 4
    //   206: invokevirtual 600	java/io/BufferedReader:close	()V
    //   209: aload_1
    //   210: ifnull -120 -> 90
    //   213: aload_1
    //   214: invokevirtual 601	java/io/FileReader:close	()V
    //   217: iload_2
    //   218: ireturn
    //   219: astore 9
    //   221: new 249	java/lang/StringBuilder
    //   224: dup
    //   225: invokespecial 250	java/lang/StringBuilder:<init>	()V
    //   228: ldc_w 681
    //   231: invokevirtual 256	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   234: aload 9
    //   236: invokevirtual 679	java/io/IOException:toString	()Ljava/lang/String;
    //   239: invokevirtual 256	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   242: invokevirtual 273	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   245: iconst_0
    //   246: anewarray 4	java/lang/Object
    //   249: invokestatic 329	com/taobao/munion/base/Log:w	(Ljava/lang/String;[Ljava/lang/Object;)V
    //   252: iload_2
    //   253: ireturn
    //   254: astore 10
    //   256: new 249	java/lang/StringBuilder
    //   259: dup
    //   260: invokespecial 250	java/lang/StringBuilder:<init>	()V
    //   263: ldc_w 678
    //   266: invokevirtual 256	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   269: aload 10
    //   271: invokevirtual 679	java/io/IOException:toString	()Ljava/lang/String;
    //   274: invokevirtual 256	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   277: invokevirtual 273	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   280: iconst_0
    //   281: anewarray 4	java/lang/Object
    //   284: invokestatic 329	com/taobao/munion/base/Log:w	(Ljava/lang/String;[Ljava/lang/Object;)V
    //   287: goto -78 -> 209
    //   290: astore 11
    //   292: aconst_null
    //   293: astore 4
    //   295: aconst_null
    //   296: astore_3
    //   297: new 249	java/lang/StringBuilder
    //   300: dup
    //   301: invokespecial 250	java/lang/StringBuilder:<init>	()V
    //   304: ldc_w 683
    //   307: invokevirtual 256	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   310: aload 11
    //   312: invokevirtual 684	java/lang/Exception:toString	()Ljava/lang/String;
    //   315: invokevirtual 256	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   318: invokevirtual 273	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   321: iconst_0
    //   322: anewarray 4	java/lang/Object
    //   325: invokestatic 329	com/taobao/munion/base/Log:w	(Ljava/lang/String;[Ljava/lang/Object;)V
    //   328: aload 4
    //   330: ifnull +8 -> 338
    //   333: aload 4
    //   335: invokevirtual 600	java/io/BufferedReader:close	()V
    //   338: aload_3
    //   339: ifnull -249 -> 90
    //   342: aload_3
    //   343: invokevirtual 601	java/io/FileReader:close	()V
    //   346: iload_2
    //   347: ireturn
    //   348: astore 12
    //   350: new 249	java/lang/StringBuilder
    //   353: dup
    //   354: invokespecial 250	java/lang/StringBuilder:<init>	()V
    //   357: ldc_w 681
    //   360: invokevirtual 256	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   363: aload 12
    //   365: invokevirtual 679	java/io/IOException:toString	()Ljava/lang/String;
    //   368: invokevirtual 256	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   371: invokevirtual 273	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   374: iconst_0
    //   375: anewarray 4	java/lang/Object
    //   378: invokestatic 329	com/taobao/munion/base/Log:w	(Ljava/lang/String;[Ljava/lang/Object;)V
    //   381: iload_2
    //   382: ireturn
    //   383: astore 13
    //   385: new 249	java/lang/StringBuilder
    //   388: dup
    //   389: invokespecial 250	java/lang/StringBuilder:<init>	()V
    //   392: ldc_w 678
    //   395: invokevirtual 256	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   398: aload 13
    //   400: invokevirtual 679	java/io/IOException:toString	()Ljava/lang/String;
    //   403: invokevirtual 256	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   406: invokevirtual 273	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   409: iconst_0
    //   410: anewarray 4	java/lang/Object
    //   413: invokestatic 329	com/taobao/munion/base/Log:w	(Ljava/lang/String;[Ljava/lang/Object;)V
    //   416: goto -78 -> 338
    //   419: astore 6
    //   421: aconst_null
    //   422: astore 4
    //   424: aconst_null
    //   425: astore_3
    //   426: aload 4
    //   428: ifnull +8 -> 436
    //   431: aload 4
    //   433: invokevirtual 600	java/io/BufferedReader:close	()V
    //   436: aload_3
    //   437: ifnull +7 -> 444
    //   440: aload_3
    //   441: invokevirtual 601	java/io/FileReader:close	()V
    //   444: aload 6
    //   446: athrow
    //   447: astore 8
    //   449: new 249	java/lang/StringBuilder
    //   452: dup
    //   453: invokespecial 250	java/lang/StringBuilder:<init>	()V
    //   456: ldc_w 678
    //   459: invokevirtual 256	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   462: aload 8
    //   464: invokevirtual 679	java/io/IOException:toString	()Ljava/lang/String;
    //   467: invokevirtual 256	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   470: invokevirtual 273	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   473: iconst_0
    //   474: anewarray 4	java/lang/Object
    //   477: invokestatic 329	com/taobao/munion/base/Log:w	(Ljava/lang/String;[Ljava/lang/Object;)V
    //   480: goto -44 -> 436
    //   483: astore 7
    //   485: new 249	java/lang/StringBuilder
    //   488: dup
    //   489: invokespecial 250	java/lang/StringBuilder:<init>	()V
    //   492: ldc_w 681
    //   495: invokevirtual 256	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   498: aload 7
    //   500: invokevirtual 679	java/io/IOException:toString	()Ljava/lang/String;
    //   503: invokevirtual 256	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   506: invokevirtual 273	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   509: iconst_0
    //   510: anewarray 4	java/lang/Object
    //   513: invokestatic 329	com/taobao/munion/base/Log:w	(Ljava/lang/String;[Ljava/lang/Object;)V
    //   516: goto -72 -> 444
    //   519: astore 6
    //   521: aconst_null
    //   522: astore 4
    //   524: goto -98 -> 426
    //   527: astore 6
    //   529: goto -103 -> 426
    //   532: astore 6
    //   534: aload_1
    //   535: astore_3
    //   536: goto -110 -> 426
    //   539: astore 11
    //   541: aconst_null
    //   542: astore 4
    //   544: goto -247 -> 297
    //   547: astore 11
    //   549: goto -252 -> 297
    //   552: astore 5
    //   554: aload_3
    //   555: astore_1
    //   556: aconst_null
    //   557: astore 4
    //   559: goto -391 -> 168
    //   562: astore 5
    //   564: aload_3
    //   565: astore_1
    //   566: goto -398 -> 168
    //
    // Exception table:
    //   from	to	target	type
    //   77	82	92	java/io/IOException
    //   86	90	128	java/io/IOException
    //   4	15	163	java/io/IOException
    //   213	217	219	java/io/IOException
    //   204	209	254	java/io/IOException
    //   4	15	290	java/lang/Exception
    //   342	346	348	java/io/IOException
    //   333	338	383	java/io/IOException
    //   4	15	419	finally
    //   431	436	447	java/io/IOException
    //   440	444	483	java/io/IOException
    //   15	28	519	finally
    //   28	35	527	finally
    //   40	72	527	finally
    //   297	328	527	finally
    //   168	199	532	finally
    //   15	28	539	java/lang/Exception
    //   28	35	547	java/lang/Exception
    //   40	72	547	java/lang/Exception
    //   15	28	552	java/io/IOException
    //   28	35	562	java/io/IOException
    //   40	72	562	java/io/IOException
  }

  public int x()
  {
    int i1 = -1;
    if (this.u != null)
    {
      ActivityManager localActivityManager = (ActivityManager)this.u.getSystemService("activity");
      ActivityManager.MemoryInfo localMemoryInfo = new ActivityManager.MemoryInfo();
      localActivityManager.getMemoryInfo(localMemoryInfo);
      i1 = new Long(localMemoryInfo.availMem / 1048576L).intValue();
    }
    return i1;
  }

  public int y()
  {
    return 0;
  }

  public int z()
  {
    return 0;
  }

  public static class a
  {
    public String a = "x";
    public String b = "unknown";
    public String c = "cell";
    public String d = "wifi";
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.taobao.munion.base.DefaultAppUtils
 * JD-Core Version:    0.6.2
 */