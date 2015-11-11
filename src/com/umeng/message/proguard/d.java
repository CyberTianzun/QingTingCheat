package com.umeng.message.proguard;

import android.content.Context;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.NetworkInfo.State;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;

public class d
{
  public static final String a = "Wi-Fi";
  public static final String b = "00-00-00-00-00-00";

  private static String a(int paramInt)
  {
    return (paramInt & 0xFF) + "." + (0xFF & paramInt >> 8) + "." + (0xFF & paramInt >> 16) + "." + (0xFF & paramInt >> 24);
  }

  public static boolean a(Context paramContext)
  {
    ConnectivityManager localConnectivityManager = (ConnectivityManager)paramContext.getSystemService("connectivity");
    if (localConnectivityManager != null)
      try
      {
        NetworkInfo localNetworkInfo = localConnectivityManager.getActiveNetworkInfo();
        if (localNetworkInfo != null)
        {
          boolean bool = localNetworkInfo.isAvailable();
          return bool;
        }
      }
      catch (Exception localException)
      {
      }
    return false;
  }

  public static String[] b(Context paramContext)
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
      NetworkInfo localNetworkInfo1 = localConnectivityManager.getNetworkInfo(1);
      if ((localNetworkInfo1 != null) && (localNetworkInfo1.getState() == NetworkInfo.State.CONNECTED))
      {
        arrayOfString[0] = "Wi-Fi";
        return arrayOfString;
      }
      NetworkInfo localNetworkInfo2 = localConnectivityManager.getNetworkInfo(0);
      if ((localNetworkInfo2 != null) && (localNetworkInfo2.getState() == NetworkInfo.State.CONNECTED))
      {
        arrayOfString[0] = "2G/3G";
        arrayOfString[1] = localNetworkInfo2.getSubtypeName();
        return arrayOfString;
      }
    }
    catch (Exception localException)
    {
    }
    return arrayOfString;
  }

  public static String c(Context paramContext)
  {
    if (paramContext != null)
    {
      WifiInfo localWifiInfo = ((WifiManager)paramContext.getSystemService("wifi")).getConnectionInfo();
      if (localWifiInfo != null)
      {
        String str = localWifiInfo.getMacAddress();
        if (f.a(str))
          str = "00-00-00-00-00-00";
        return str;
      }
      return "00-00-00-00-00-00";
    }
    return "00-00-00-00-00-00";
  }

  public static String d(Context paramContext)
  {
    if (paramContext != null)
      try
      {
        WifiInfo localWifiInfo = ((WifiManager)paramContext.getSystemService("wifi")).getConnectionInfo();
        if (localWifiInfo != null)
        {
          String str = a(localWifiInfo.getIpAddress());
          return str;
        }
        return null;
      }
      catch (Exception localException)
      {
      }
    return null;
  }

  public static boolean e(Context paramContext)
  {
    boolean bool1 = false;
    if (paramContext != null);
    try
    {
      boolean bool2 = b(paramContext)[0].equals("Wi-Fi");
      bool1 = false;
      if (bool2)
        bool1 = true;
      return bool1;
    }
    catch (Exception localException)
    {
    }
    return false;
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.umeng.message.proguard.d
 * JD-Core Version:    0.6.2
 */