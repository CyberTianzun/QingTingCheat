package u.aly;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.Configuration;
import android.content.res.Resources;
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
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import javax.microedition.khronos.opengles.GL10;

public class bi
{
  protected static final String a = bi.class.getName();
  public static final String b = "";
  public static final String c = "2G/3G";
  public static final String d = "Wi-Fi";
  public static final int e = 8;

  private static int a(Object paramObject, String paramString)
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

  public static int a(Date paramDate1, Date paramDate2)
  {
    if (paramDate1.after(paramDate2));
    while (true)
    {
      long l = paramDate2.getTime();
      return (int)((paramDate1.getTime() - l) / 1000L);
      Date localDate = paramDate2;
      paramDate2 = paramDate1;
      paramDate1 = localDate;
    }
  }

  public static String a()
  {
    String str = null;
    try
    {
      FileReader localFileReader = new FileReader("/proc/cpuinfo");
      str = null;
      if (localFileReader != null);
      try
      {
        BufferedReader localBufferedReader = new BufferedReader(localFileReader, 1024);
        str = localBufferedReader.readLine();
        localBufferedReader.close();
        localFileReader.close();
        if (str != null)
          return str.substring(1 + str.indexOf(':')).trim();
      }
      catch (IOException localIOException)
      {
        while (true)
          bj.b(a, "Could not read from file /proc/cpuinfo", localIOException);
      }
    }
    catch (FileNotFoundException localFileNotFoundException)
    {
      while (true)
        bj.b(a, "Could not open file /proc/cpuinfo", localFileNotFoundException);
    }
    return "";
  }

  public static String a(Date paramDate)
  {
    return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US).format(paramDate);
  }

  public static Date a(String paramString)
  {
    try
    {
      Date localDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US).parse(paramString);
      return localDate;
    }
    catch (Exception localException)
    {
    }
    return null;
  }

  public static boolean a(Context paramContext)
  {
    return paramContext.getResources().getConfiguration().locale.toString().equals(Locale.CHINA.toString());
  }

  public static boolean a(Context paramContext, String paramString)
  {
    return paramContext.getPackageManager().checkPermission(paramString, paramContext.getPackageName()) == 0;
  }

  public static boolean a(String paramString, Context paramContext)
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

  public static String[] a(GL10 paramGL10)
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
      bj.b(a, "Could not read gpu infor:", localException);
    }
    return new String[0];
  }

  public static boolean b()
  {
    return Environment.getExternalStorageState().equals("mounted");
  }

  public static boolean b(Context paramContext)
  {
    return paramContext.getResources().getConfiguration().orientation == 1;
  }

  public static String c()
  {
    Date localDate = new Date();
    return new SimpleDateFormat("yyyy-MM-dd", Locale.US).format(localDate);
  }

  public static String c(Context paramContext)
  {
    try
    {
      String str = String.valueOf(paramContext.getPackageManager().getPackageInfo(paramContext.getPackageName(), 0).versionCode);
      return str;
    }
    catch (PackageManager.NameNotFoundException localNameNotFoundException)
    {
    }
    return "";
  }

  public static String d(Context paramContext)
  {
    try
    {
      String str = paramContext.getPackageManager().getPackageInfo(paramContext.getPackageName(), 0).versionName;
      return str;
    }
    catch (PackageManager.NameNotFoundException localNameNotFoundException)
    {
    }
    return "";
  }

  public static String e(Context paramContext)
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

  public static String f(Context paramContext)
  {
    TelephonyManager localTelephonyManager = (TelephonyManager)paramContext.getSystemService("phone");
    if (localTelephonyManager == null)
      bj.e(a, "No IMEI.");
    try
    {
      if (a(paramContext, "android.permission.READ_PHONE_STATE"))
      {
        String str2 = localTelephonyManager.getDeviceId();
        str1 = str2;
        if (TextUtils.isEmpty(str1))
        {
          bj.e(a, "No IMEI.");
          str1 = p(paramContext);
          if (TextUtils.isEmpty(str1))
          {
            bj.e(a, "Failed to take mac as IMEI. Try to use Secure.ANDROID_ID instead.");
            str1 = Settings.Secure.getString(paramContext.getContentResolver(), "android_id");
            bj.a(a, "getDeviceId: Secure.ANDROID_ID: " + str1);
          }
        }
        return str1;
      }
    }
    catch (Exception localException)
    {
      while (true)
      {
        bj.e(a, "No IMEI.", localException);
        String str1 = "";
      }
    }
  }

  public static String g(Context paramContext)
  {
    return bv.b(f(paramContext));
  }

  public static String h(Context paramContext)
  {
    try
    {
      TelephonyManager localTelephonyManager = (TelephonyManager)paramContext.getSystemService("phone");
      if (localTelephonyManager == null)
        return "";
      String str = localTelephonyManager.getNetworkOperatorName();
      return str;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return "";
  }

  public static String i(Context paramContext)
  {
    try
    {
      DisplayMetrics localDisplayMetrics = new DisplayMetrics();
      ((WindowManager)paramContext.getSystemService("window")).getDefaultDisplay().getMetrics(localDisplayMetrics);
      int i = localDisplayMetrics.widthPixels;
      String str = String.valueOf(localDisplayMetrics.heightPixels) + "*" + String.valueOf(i);
      return str;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return "";
  }

  public static String[] j(Context paramContext)
  {
    String[] arrayOfString = { "", "" };
    try
    {
      if (paramContext.getPackageManager().checkPermission("android.permission.ACCESS_NETWORK_STATE", paramContext.getPackageName()) != 0)
      {
        arrayOfString[0] = "";
        return arrayOfString;
      }
      ConnectivityManager localConnectivityManager = (ConnectivityManager)paramContext.getSystemService("connectivity");
      if (localConnectivityManager == null)
      {
        arrayOfString[0] = "";
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

  public static boolean k(Context paramContext)
  {
    return "Wi-Fi".equals(j(paramContext)[0]);
  }

  public static boolean l(Context paramContext)
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

  public static int m(Context paramContext)
  {
    try
    {
      Calendar localCalendar = Calendar.getInstance(x(paramContext));
      if (localCalendar != null)
      {
        int i = localCalendar.getTimeZone().getRawOffset() / 3600000;
        return i;
      }
    }
    catch (Exception localException)
    {
      bj.a(a, "error in getTimeZone", localException);
    }
    return 8;
  }

  public static String[] n(Context paramContext)
  {
    String[] arrayOfString = new String[2];
    try
    {
      Locale localLocale = x(paramContext);
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
      bj.b(a, "error in getLocaleInfo", localException);
    }
    return arrayOfString;
  }

  public static String o(Context paramContext)
  {
    try
    {
      ApplicationInfo localApplicationInfo = paramContext.getPackageManager().getApplicationInfo(paramContext.getPackageName(), 128);
      if (localApplicationInfo != null)
      {
        String str = localApplicationInfo.metaData.getString("UMENG_APPKEY");
        if (str != null)
          return str.trim();
        bj.b(a, "Could not read UMENG_APPKEY meta-data from AndroidManifest.xml.");
      }
      return null;
    }
    catch (Exception localException)
    {
      while (true)
        bj.b(a, "Could not read UMENG_APPKEY meta-data from AndroidManifest.xml.", localException);
    }
  }

  public static String p(Context paramContext)
  {
    try
    {
      WifiManager localWifiManager = (WifiManager)paramContext.getSystemService("wifi");
      if (a(paramContext, "android.permission.ACCESS_WIFI_STATE"))
        return localWifiManager.getConnectionInfo().getMacAddress();
      bj.e(a, "Could not get mac address.[no permission android.permission.ACCESS_WIFI_STATE");
      return "";
    }
    catch (Exception localException)
    {
      while (true)
        bj.e(a, "Could not get mac address." + localException.toString());
    }
  }

  public static String q(Context paramContext)
  {
    int[] arrayOfInt = r(paramContext);
    if (arrayOfInt != null)
    {
      StringBuffer localStringBuffer = new StringBuffer();
      localStringBuffer.append(arrayOfInt[0]);
      localStringBuffer.append("*");
      localStringBuffer.append(arrayOfInt[1]);
      return localStringBuffer.toString();
    }
    return "Unknown";
  }

  public static int[] r(Context paramContext)
  {
    while (true)
    {
      int j;
      int i;
      int n;
      int i1;
      try
      {
        DisplayMetrics localDisplayMetrics = new DisplayMetrics();
        ((WindowManager)paramContext.getSystemService("window")).getDefaultDisplay().getMetrics(localDisplayMetrics);
        if ((0x2000 & paramContext.getApplicationInfo().flags) == 0)
        {
          j = a(localDisplayMetrics, "noncompatWidthPixels");
          i = a(localDisplayMetrics, "noncompatHeightPixels");
          break label151;
          int k = localDisplayMetrics.widthPixels;
          int m = localDisplayMetrics.heightPixels;
          n = k;
          i1 = m;
          label81: int[] arrayOfInt = new int[2];
          if (n > i1)
          {
            arrayOfInt[0] = i1;
            arrayOfInt[1] = n;
            return arrayOfInt;
          }
          arrayOfInt[0] = n;
          arrayOfInt[1] = i1;
          return arrayOfInt;
        }
      }
      catch (Exception localException)
      {
        bj.b(a, "read resolution fail", localException);
        return null;
      }
      label151: 
      do
      {
        n = j;
        i1 = i;
        break label81;
        i = -1;
        j = -1;
        if (j == -1)
          break;
      }
      while (i != -1);
    }
  }

  public static String s(Context paramContext)
  {
    try
    {
      String str = ((TelephonyManager)paramContext.getSystemService("phone")).getNetworkOperatorName();
      return str;
    }
    catch (Exception localException)
    {
      bj.a(a, "read carrier fail", localException);
    }
    return "Unknown";
  }

  public static String t(Context paramContext)
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
          bj.a(a, "Could not read UMENG_CHANNEL meta-data from AndroidManifest.xml.");
          return "Unknown";
        }
      }
    }
    catch (Exception localException)
    {
      bj.a(a, "Could not read UMENG_CHANNEL meta-data from AndroidManifest.xml.");
      localException.printStackTrace();
    }
    return "Unknown";
  }

  public static String u(Context paramContext)
  {
    return paramContext.getPackageName();
  }

  public static String v(Context paramContext)
  {
    return paramContext.getPackageManager().getApplicationLabel(paramContext.getApplicationInfo()).toString();
  }

  public static boolean w(Context paramContext)
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

  private static Locale x(Context paramContext)
  {
    try
    {
      Configuration localConfiguration = new Configuration();
      localConfiguration.setToDefaults();
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
        bj.b(a, "fail to read user config locale");
        Locale localLocale = null;
      }
    }
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     u.aly.bi
 * JD-Core Version:    0.6.2
 */