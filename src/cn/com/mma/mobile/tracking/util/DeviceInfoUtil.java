package cn.com.mma.mobile.tracking.util;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.NetworkInfo.State;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Build.VERSION;
import android.provider.Settings.Secure;
import android.provider.Settings.System;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;
import java.math.BigInteger;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class DeviceInfoUtil
{
  private static final String CHAR_SET = "iso-8859-1";
  private static final String SHA1_ALGORITHM = "SHA-1";

  private static String SHA1(String paramString)
  {
    try
    {
      MessageDigest localMessageDigest = MessageDigest.getInstance("SHA-1");
      localMessageDigest.update(paramString.getBytes("iso-8859-1"), 0, paramString.length());
      String str = convertToHex(localMessageDigest.digest());
      return str;
    }
    catch (Exception localException)
    {
      Logger.e("ODIN Error generating generating SHA-1: " + localException);
    }
    return null;
  }

  public static String appVersion(Context paramContext)
  {
    try
    {
      String str = paramContext.getPackageManager().getPackageInfo(paramContext.getPackageName(), 0).versionName;
      return str;
    }
    catch (PackageManager.NameNotFoundException localNameNotFoundException)
    {
    }
    return "1.0";
  }

  private static String convertToHex(byte[] paramArrayOfByte)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    int i = 0;
    if (i >= paramArrayOfByte.length)
      return localStringBuffer.toString();
    int j = 0xF & paramArrayOfByte[i] >>> 4;
    int m;
    label94: for (int k = 0; ; k = m)
    {
      if ((j >= 0) && (j <= 9))
        localStringBuffer.append((char)(j + 48));
      while (true)
      {
        j = 0xF & paramArrayOfByte[i];
        m = k + 1;
        if (k < 1)
          break label94;
        i++;
        break;
        localStringBuffer.append((char)(97 + (j - 10)));
      }
    }
  }

  public static Map<String, String> fulfillTrackingInfo(Context paramContext)
  {
    HashMap localHashMap = new HashMap();
    localHashMap.put("LBS", LocationUtil.getInstance(paramContext).getLocation());
    String str1 = getMacAddress(paramContext);
    if (str1 != null)
      localHashMap.put("MAC", str1.replaceAll(":", "").toUpperCase());
    localHashMap.put("ANDROIDID", getAndroidId(paramContext));
    localHashMap.put("OSVS", getOSVersion());
    localHashMap.put("TERM", getDevice());
    if (isWifi(paramContext));
    for (String str2 = "1"; ; str2 = "0")
    {
      localHashMap.put("WIFI", str2);
      localHashMap.put("ANAME", getAppName(paramContext));
      localHashMap.put("AKEY", getPackageName(paramContext));
      localHashMap.put("OSVS", getOSVersion());
      localHashMap.put("OS", "0");
      localHashMap.put("SCWH", getResolution(paramContext));
      localHashMap.put("IMEI", getImei(paramContext));
      localHashMap.put("SDKVS", "1.2");
      return localHashMap;
    }
  }

  private static String generateAndroidId(Context paramContext)
  {
    String str1 = Settings.Secure.getString(paramContext.getContentResolver(), "android_id");
    if ((str1 == null) || (str1.equals("9774d56d682e549c")) || (str1.length() < 15))
      str1 = new BigInteger(64, new SecureRandom()).toString(16);
    String str2 = CommonUtil.md5(str1);
    SharedPreferencedUtil.putString(paramContext, "cn.com.mma.mobile.tracking.other", "android_id", str2);
    return str2;
  }

  public static String getAndroidId(Context paramContext)
  {
    String str = SharedPreferencedUtil.getString(paramContext, "cn.com.mma.mobile.tracking.other", "android_id");
    if ((str == null) || (str.equals("")) || (str.equals("null")))
      str = generateAndroidId(paramContext);
    return str;
  }

  public static String getAppName(Context paramContext)
  {
    PackageManager localPackageManager = paramContext.getPackageManager();
    try
    {
      String str = localPackageManager.getApplicationInfo(paramContext.getPackageName(), 128).loadLabel(localPackageManager).toString();
      return str;
    }
    catch (PackageManager.NameNotFoundException localNameNotFoundException)
    {
      localNameNotFoundException.printStackTrace();
    }
    return "";
  }

  public static String getCarrier(Context paramContext)
  {
    return ((TelephonyManager)paramContext.getSystemService("phone")).getNetworkOperatorName();
  }

  public static String getDevice()
  {
    return Build.MODEL;
  }

  public static String getIP(Context paramContext)
  {
    try
    {
      Enumeration localEnumeration = ((NetworkInterface)NetworkInterface.getNetworkInterfaces().nextElement()).getInetAddresses();
      InetAddress localInetAddress;
      do
      {
        if (!localEnumeration.hasMoreElements())
          return null;
        localInetAddress = (InetAddress)localEnumeration.nextElement();
      }
      while (localInetAddress.isLinkLocalAddress());
      String str = localInetAddress.getHostAddress();
      return str;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return null;
  }

  public static String getImei(Context paramContext)
  {
    return ((TelephonyManager)paramContext.getSystemService("phone")).getDeviceId();
  }

  public static String getLocale()
  {
    Locale localLocale = Locale.getDefault();
    return localLocale.getLanguage() + "_" + localLocale.getCountry();
  }

  public static String getMacAddress(Context paramContext)
  {
    String str = "";
    WifiManager localWifiManager = (WifiManager)paramContext.getSystemService("wifi");
    if (localWifiManager != null)
    {
      WifiInfo localWifiInfo = localWifiManager.getConnectionInfo();
      if (localWifiInfo != null)
        str = localWifiInfo.getMacAddress();
    }
    return str;
  }

  public static String getModel()
  {
    String str1 = Build.DEVICE;
    String str2 = Build.ID;
    String str3 = Build.DISPLAY;
    String str4 = Build.PRODUCT;
    String str5 = Build.BOARD;
    String str6 = Build.BRAND;
    String str7 = Build.MODEL;
    return str1 + "," + str2 + "," + str3 + "," + str4 + "," + str5 + "," + str6 + "," + str7;
  }

  public static String getODIN1(Context paramContext)
  {
    return SHA1(Settings.System.getString(paramContext.getContentResolver(), "android_id"));
  }

  public static String getOSVersion()
  {
    return Build.VERSION.RELEASE;
  }

  public static String getPackageName(Context paramContext)
  {
    return paramContext.getPackageName();
  }

  public static String getResolution(Context paramContext)
  {
    WindowManager localWindowManager = (WindowManager)paramContext.getSystemService("window");
    DisplayMetrics localDisplayMetrics = new DisplayMetrics();
    localWindowManager.getDefaultDisplay().getMetrics(localDisplayMetrics);
    return localDisplayMetrics.widthPixels + "x" + localDisplayMetrics.heightPixels;
  }

  public static boolean isEmulator(Context paramContext)
  {
    String str = Settings.Secure.getString(paramContext.getContentResolver(), "android_id");
    boolean bool1 = false;
    if (str != null)
    {
      boolean bool2 = str.equals("9774d56d682e549c");
      bool1 = false;
      if (bool2)
        bool1 = true;
    }
    return bool1;
  }

  public static boolean isNetworkAvailable(Context paramContext)
  {
    ConnectivityManager localConnectivityManager;
    if (paramContext != null)
    {
      localConnectivityManager = (ConnectivityManager)paramContext.getSystemService("connectivity");
      if (localConnectivityManager != null)
        break label21;
    }
    while (true)
    {
      return false;
      label21: NetworkInfo[] arrayOfNetworkInfo = localConnectivityManager.getAllNetworkInfo();
      if (arrayOfNetworkInfo != null)
        for (int i = 0; i < arrayOfNetworkInfo.length; i++)
          if (arrayOfNetworkInfo[i].getState() == NetworkInfo.State.CONNECTED)
            return true;
    }
  }

  public static boolean isWifi(Context paramContext)
  {
    return ((ConnectivityManager)paramContext.getSystemService("connectivity")).getNetworkInfo(1).getState() == NetworkInfo.State.CONNECTED;
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     cn.com.mma.mobile.tracking.util.DeviceInfoUtil
 * JD-Core Version:    0.6.2
 */