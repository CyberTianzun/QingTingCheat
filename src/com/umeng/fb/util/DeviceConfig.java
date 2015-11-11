package com.umeng.fb.util;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.location.Location;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.NetworkInfo.State;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings.Secure;
import android.provider.Settings.System;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import javax.microedition.khronos.opengles.GL10;

public class DeviceConfig
{
  public static final int DEFAULT_TIMEZONE = 8;
  protected static final String LOG_TAG = DeviceConfig.class.getName();
  private static final String MOBILE_NETWORK = "2G/3G";
  protected static final String UNKNOW = "Unknown";
  private static final String WIFI = "Wi-Fi";

  public static boolean checkPermission(Context paramContext, String paramString)
  {
    return paramContext.getPackageManager().checkPermission(paramString, paramContext.getPackageName()) == 0;
  }

  public static String getAppLabel(Context paramContext)
  {
    PackageManager localPackageManager = paramContext.getPackageManager();
    try
    {
      ApplicationInfo localApplicationInfo2 = localPackageManager.getApplicationInfo(paramContext.getPackageName(), 0);
      localApplicationInfo1 = localApplicationInfo2;
      if (localApplicationInfo1 != null)
      {
        localObject = localPackageManager.getApplicationLabel(localApplicationInfo1);
        return (String)localObject;
      }
    }
    catch (PackageManager.NameNotFoundException localNameNotFoundException)
    {
      while (true)
      {
        ApplicationInfo localApplicationInfo1 = null;
        continue;
        Object localObject = "";
      }
    }
  }

  public static String getAppVersionCode(Context paramContext)
  {
    try
    {
      String str = String.valueOf(paramContext.getPackageManager().getPackageInfo(paramContext.getPackageName(), 0).versionCode);
      return str;
    }
    catch (PackageManager.NameNotFoundException localNameNotFoundException)
    {
    }
    return "Unknown";
  }

  public static String getAppVersionName(Context paramContext)
  {
    try
    {
      String str = paramContext.getPackageManager().getPackageInfo(paramContext.getPackageName(), 0).versionName;
      return str;
    }
    catch (PackageManager.NameNotFoundException localNameNotFoundException)
    {
    }
    return "Unknown";
  }

  public static String getAppkey(Context paramContext)
  {
    try
    {
      ApplicationInfo localApplicationInfo = paramContext.getPackageManager().getApplicationInfo(paramContext.getPackageName(), 128);
      if (localApplicationInfo != null)
      {
        String str = localApplicationInfo.metaData.getString("UMENG_APPKEY");
        if (str != null)
          return str.trim();
        Log.e(LOG_TAG, "Could not read UMENG_APPKEY meta-data from AndroidManifest.xml.");
      }
      return null;
    }
    catch (Exception localException)
    {
      while (true)
        Log.e(LOG_TAG, "Could not read UMENG_APPKEY meta-data from AndroidManifest.xml.", localException);
    }
  }

  public static String getApplicationLable(Context paramContext)
  {
    return paramContext.getPackageManager().getApplicationLabel(paramContext.getApplicationInfo()).toString();
  }

  // ERROR //
  public static String getCPU()
  {
    // Byte code:
    //   0: new 128	java/io/FileReader
    //   3: dup
    //   4: ldc 130
    //   6: invokespecial 133	java/io/FileReader:<init>	(Ljava/lang/String;)V
    //   9: astore_0
    //   10: aconst_null
    //   11: astore_1
    //   12: aload_0
    //   13: ifnull +28 -> 41
    //   16: new 135	java/io/BufferedReader
    //   19: dup
    //   20: aload_0
    //   21: sipush 1024
    //   24: invokespecial 138	java/io/BufferedReader:<init>	(Ljava/io/Reader;I)V
    //   27: astore_2
    //   28: aload_2
    //   29: invokevirtual 141	java/io/BufferedReader:readLine	()Ljava/lang/String;
    //   32: astore_1
    //   33: aload_2
    //   34: invokevirtual 144	java/io/BufferedReader:close	()V
    //   37: aload_0
    //   38: invokevirtual 145	java/io/FileReader:close	()V
    //   41: aload_1
    //   42: ifnull +16 -> 58
    //   45: aload_1
    //   46: iconst_1
    //   47: aload_1
    //   48: bipush 58
    //   50: invokevirtual 149	java/lang/String:indexOf	(I)I
    //   53: iadd
    //   54: invokevirtual 152	java/lang/String:substring	(I)Ljava/lang/String;
    //   57: astore_1
    //   58: aload_1
    //   59: invokevirtual 101	java/lang/String:trim	()Ljava/lang/String;
    //   62: areturn
    //   63: astore_3
    //   64: getstatic 28	com/umeng/fb/util/DeviceConfig:LOG_TAG	Ljava/lang/String;
    //   67: ldc 154
    //   69: aload_3
    //   70: invokestatic 112	com/umeng/fb/util/Log:e	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Exception;)V
    //   73: goto -32 -> 41
    //   76: astore 4
    //   78: getstatic 28	com/umeng/fb/util/DeviceConfig:LOG_TAG	Ljava/lang/String;
    //   81: ldc 156
    //   83: aload 4
    //   85: invokestatic 112	com/umeng/fb/util/Log:e	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Exception;)V
    //   88: goto -47 -> 41
    //   91: astore 4
    //   93: aconst_null
    //   94: astore_1
    //   95: goto -17 -> 78
    //   98: astore 4
    //   100: goto -22 -> 78
    //   103: astore_3
    //   104: goto -40 -> 64
    //
    // Exception table:
    //   from	to	target	type
    //   16	28	63	java/io/IOException
    //   16	28	76	java/io/FileNotFoundException
    //   64	73	76	java/io/FileNotFoundException
    //   0	10	91	java/io/FileNotFoundException
    //   28	41	98	java/io/FileNotFoundException
    //   28	41	103	java/io/IOException
  }

  public static String getChannel(Context paramContext)
  {
    try
    {
      ApplicationInfo localApplicationInfo = paramContext.getPackageManager().getApplicationInfo(paramContext.getPackageName(), 128);
      if ((localApplicationInfo != null) && (localApplicationInfo.metaData != null))
      {
        Object localObject = localApplicationInfo.metaData.get("UMENG_CHANNEL");
        if (localObject != null)
        {
          String str = localObject.toString();
          if (str != null)
            return str;
          Log.i(LOG_TAG, "Could not read UMENG_CHANNEL meta-data from AndroidManifest.xml.");
          return "Unknown";
        }
      }
    }
    catch (Exception localException)
    {
      Log.i(LOG_TAG, "Could not read UMENG_CHANNEL meta-data from AndroidManifest.xml.");
      localException.printStackTrace();
    }
    return "Unknown";
  }

  public static String getDeviceId(Context paramContext)
  {
    TelephonyManager localTelephonyManager = (TelephonyManager)paramContext.getSystemService("phone");
    if (localTelephonyManager == null)
      Log.w(LOG_TAG, "No IMEI.");
    Object localObject = "";
    try
    {
      if (checkPermission(paramContext, "android.permission.READ_PHONE_STATE"))
      {
        String str2 = localTelephonyManager.getDeviceId();
        localObject = str2;
      }
      if (TextUtils.isEmpty((CharSequence)localObject))
      {
        Log.w(LOG_TAG, "No IMEI.");
        localObject = getMac(paramContext);
        if (TextUtils.isEmpty((CharSequence)localObject))
        {
          Log.w(LOG_TAG, "Failed to take mac as IMEI. Try to use Secure.ANDROID_ID instead.");
          String str1 = Settings.Secure.getString(paramContext.getContentResolver(), "android_id");
          Log.i(LOG_TAG, "getDeviceId: Secure.ANDROID_ID: " + str1);
          return str1;
        }
      }
    }
    catch (Exception localException)
    {
      while (true)
        Log.w(LOG_TAG, "No IMEI.", localException);
    }
    return localObject;
  }

  public static String getDeviceIdUmengMD5(Context paramContext)
  {
    return Helper.getUmengMD5(getDeviceId(paramContext));
  }

  public static String getDisplayResolution(Context paramContext)
  {
    try
    {
      DisplayMetrics localDisplayMetrics = new DisplayMetrics();
      ((WindowManager)paramContext.getSystemService("window")).getDefaultDisplay().getMetrics(localDisplayMetrics);
      int i = localDisplayMetrics.widthPixels;
      int j = localDisplayMetrics.heightPixels;
      String str = String.valueOf(j) + "*" + String.valueOf(i);
      return str;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return "Unknown";
  }

  public static String[] getGPU(GL10 paramGL10)
  {
    try
    {
      String[] arrayOfString = new String[2];
      String str1 = paramGL10.glGetString(7936);
      String str2 = paramGL10.glGetString(7937);
      arrayOfString[0] = str1;
      arrayOfString[1] = str2;
      return arrayOfString;
    }
    catch (Exception localException)
    {
      Log.e(LOG_TAG, "Could not read gpu infor:", localException);
    }
    return new String[0];
  }

  public static int getIntervalSeconds(Date paramDate1, Date paramDate2)
  {
    if (paramDate1.after(paramDate2))
    {
      Date localDate = paramDate1;
      paramDate1 = paramDate2;
      paramDate2 = localDate;
    }
    long l = paramDate1.getTime();
    return (int)((paramDate2.getTime() - l) / 1000L);
  }

  private static Locale getLocale(Context paramContext)
  {
    try
    {
      Configuration localConfiguration = new Configuration();
      Settings.System.getConfiguration(paramContext.getContentResolver(), localConfiguration);
      localLocale = null;
      if (localConfiguration != null)
        localLocale = localConfiguration.locale;
      if (localLocale == null)
        localLocale = Locale.getDefault();
      return localLocale;
    }
    catch (Exception localException)
    {
      while (true)
      {
        Log.e(LOG_TAG, "fail to read user config locale");
        Locale localLocale = null;
      }
    }
  }

  public static String[] getLocaleInfo(Context paramContext)
  {
    String[] arrayOfString = new String[2];
    try
    {
      Locale localLocale = getLocale(paramContext);
      if (localLocale != null)
      {
        arrayOfString[0] = localLocale.getCountry();
        arrayOfString[1] = localLocale.getLanguage();
      }
      if (TextUtils.isEmpty(arrayOfString[0]))
        arrayOfString[0] = "Unknown";
      if (TextUtils.isEmpty(arrayOfString[1]))
        arrayOfString[1] = "Unknown";
      return arrayOfString;
    }
    catch (Exception localException)
    {
      Log.e(LOG_TAG, "error in getLocaleInfo", localException);
    }
    return arrayOfString;
  }

  public static Location getLocation(Context paramContext)
  {
    return null;
  }

  public static String getMac(Context paramContext)
  {
    try
    {
      WifiManager localWifiManager = (WifiManager)paramContext.getSystemService("wifi");
      if (checkPermission(paramContext, "android.permission.ACCESS_WIFI_STATE"))
        return localWifiManager.getConnectionInfo().getMacAddress();
      Log.w(LOG_TAG, "Could not get mac address.[no permission android.permission.ACCESS_WIFI_STATE");
      return "";
    }
    catch (Exception localException)
    {
      while (true)
        Log.w(LOG_TAG, "Could not get mac address." + localException.toString());
    }
  }

  public static String[] getNetworkAccessMode(Context paramContext)
  {
    String[] arrayOfString = { "Unknown", "Unknown" };
    ConnectivityManager localConnectivityManager;
    try
    {
      if (paramContext.getPackageManager().checkPermission("android.permission.ACCESS_NETWORK_STATE", paramContext.getPackageName()) != 0)
      {
        arrayOfString[0] = "Unknown";
        return arrayOfString;
      }
      localConnectivityManager = (ConnectivityManager)paramContext.getSystemService("connectivity");
      if (localConnectivityManager == null)
      {
        arrayOfString[0] = "Unknown";
        return arrayOfString;
      }
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
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
    }
    return arrayOfString;
  }

  public static String getNetworkOperatorName(Context paramContext)
  {
    try
    {
      TelephonyManager localTelephonyManager = (TelephonyManager)paramContext.getSystemService("phone");
      if (localTelephonyManager == null)
        return "Unknown";
      String str = localTelephonyManager.getNetworkOperatorName();
      return str;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return "Unknown";
  }

  public static String getOperator(Context paramContext)
  {
    try
    {
      String str = ((TelephonyManager)paramContext.getSystemService("phone")).getNetworkOperatorName();
      return str;
    }
    catch (Exception localException)
    {
      Log.i(LOG_TAG, "read carrier fail", localException);
    }
    return "Unknown";
  }

  public static String getPackageName(Context paramContext)
  {
    return paramContext.getPackageName();
  }

  public static String getResolution(Context paramContext)
  {
    while (true)
    {
      int i;
      int j;
      try
      {
        DisplayMetrics localDisplayMetrics = new DisplayMetrics();
        ((WindowManager)paramContext.getSystemService("window")).getDefaultDisplay().getMetrics(localDisplayMetrics);
        i = -1;
        j = -1;
        if ((0x2000 & paramContext.getApplicationInfo().flags) == 0)
        {
          i = reflectMetrics(localDisplayMetrics, "noncompatWidthPixels");
          j = reflectMetrics(localDisplayMetrics, "noncompatHeightPixels");
          break label136;
          i = localDisplayMetrics.widthPixels;
          j = localDisplayMetrics.heightPixels;
          StringBuffer localStringBuffer = new StringBuffer();
          localStringBuffer.append(i);
          localStringBuffer.append("*");
          localStringBuffer.append(j);
          String str = localStringBuffer.toString();
          return str;
        }
      }
      catch (Exception localException)
      {
        Log.e(LOG_TAG, "read resolution fail", localException);
        return "Unknown";
      }
      label136: if (i != -1)
        if (j != -1);
    }
  }

  public static String getTimeString(Date paramDate)
  {
    return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(paramDate);
  }

  public static int getTimeZone(Context paramContext)
  {
    try
    {
      Calendar localCalendar = Calendar.getInstance(getLocale(paramContext));
      if (localCalendar != null)
      {
        int i = localCalendar.getTimeZone().getRawOffset() / 3600000;
        return i;
      }
    }
    catch (Exception localException)
    {
      Log.i(LOG_TAG, "error in getTimeZone", localException);
    }
    return 8;
  }

  public static String getToday()
  {
    Date localDate = new Date();
    return new SimpleDateFormat("yyyy-MM-dd").format(localDate);
  }

  public static boolean isAppInstalled(String paramString, Context paramContext)
  {
    PackageManager localPackageManager = paramContext.getPackageManager();
    try
    {
      localPackageManager.getPackageInfo(paramString, 1);
      return true;
    }
    catch (PackageManager.NameNotFoundException localNameNotFoundException)
    {
    }
    return false;
  }

  public static boolean isChinese(Context paramContext)
  {
    return paramContext.getResources().getConfiguration().locale.toString().equals(Locale.CHINA.toString());
  }

  public static boolean isDebug(Context paramContext)
  {
    try
    {
      int i = paramContext.getApplicationInfo().flags;
      int j = i & 0x2;
      boolean bool = false;
      if (j != 0)
        bool = true;
      return bool;
    }
    catch (Exception localException)
    {
    }
    return false;
  }

  public static boolean isOnline(Context paramContext)
  {
    try
    {
      NetworkInfo localNetworkInfo = ((ConnectivityManager)paramContext.getSystemService("connectivity")).getActiveNetworkInfo();
      if (localNetworkInfo != null)
      {
        boolean bool = localNetworkInfo.isConnectedOrConnecting();
        return bool;
      }
      return false;
    }
    catch (Exception localException)
    {
    }
    return true;
  }

  public static boolean isScreenPortrait(Context paramContext)
  {
    return paramContext.getResources().getConfiguration().orientation == 1;
  }

  public static boolean isSdCardWrittenable()
  {
    return Environment.getExternalStorageState().equals("mounted");
  }

  public static boolean isWiFiAvailable(Context paramContext)
  {
    return "Wi-Fi".equals(getNetworkAccessMode(paramContext)[0]);
  }

  private static int reflectMetrics(Object paramObject, String paramString)
  {
    try
    {
      Field localField = DisplayMetrics.class.getDeclaredField(paramString);
      localField.setAccessible(true);
      int i = localField.getInt(paramObject);
      return i;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return -1;
  }

  public static Date toTime(String paramString)
  {
    try
    {
      Date localDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(paramString);
      return localDate;
    }
    catch (Exception localException)
    {
    }
    return null;
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.umeng.fb.util.DeviceConfig
 * JD-Core Version:    0.6.2
 */