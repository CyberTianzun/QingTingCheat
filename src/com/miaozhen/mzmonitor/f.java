package com.miaozhen.mzmonitor;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
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
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;
import java.util.Locale;

public class f
{
  private static boolean a = false;
  private static f c = null;
  private Context b;

  private f(Context paramContext)
  {
    this.b = paramContext;
  }

  public static f a(Context paramContext)
  {
    try
    {
      if (c == null)
        c = new f(paramContext.getApplicationContext());
      f localf = c;
      return localf;
    }
    finally
    {
    }
  }

  public static String a()
  {
    return Build.MODEL;
  }

  public static String b(Context paramContext)
  {
    TelephonyManager localTelephonyManager = (TelephonyManager)paramContext.getSystemService("phone");
    if (localTelephonyManager != null)
    {
      String str = localTelephonyManager.getNetworkOperatorName();
      if ((localTelephonyManager.getNetworkOperatorName() != null) && (!localTelephonyManager.getNetworkOperatorName().equals("")))
        return str;
    }
    return null;
  }

  public static String g()
  {
    return Build.VERSION.RELEASE;
  }

  public static String n()
  {
    Locale localLocale = Locale.getDefault();
    return localLocale.getLanguage() + "_" + localLocale.getCountry();
  }

  public static String o()
  {
    try
    {
      Enumeration localEnumeration = ((NetworkInterface)NetworkInterface.getNetworkInterfaces().nextElement()).getInetAddresses();
      while (localEnumeration.hasMoreElements())
      {
        InetAddress localInetAddress = (InetAddress)localEnumeration.nextElement();
        if (!localInetAddress.isLinkLocalAddress())
        {
          String str = localInetAddress.getHostAddress();
          return str;
        }
      }
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return null;
  }

  public final String b()
  {
    return ((TelephonyManager)this.b.getSystemService("phone")).getDeviceId();
  }

  public final String c()
  {
    return Settings.Secure.getString(this.b.getContentResolver(), "android_id");
  }

  public final String d()
  {
    Object localObject;
    try
    {
      ApplicationInfo localApplicationInfo = this.b.getPackageManager().getApplicationInfo(this.b.getPackageName(), 128);
      if (localApplicationInfo != null)
      {
        if (localApplicationInfo.labelRes != 0)
          return this.b.getResources().getString(localApplicationInfo.labelRes);
        if (localApplicationInfo.nonLocalizedLabel == null)
        {
          localObject = null;
        }
        else
        {
          String str = localApplicationInfo.nonLocalizedLabel.toString();
          localObject = str;
        }
      }
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return "";
    return localObject;
  }

  public final String e()
  {
    String str = "";
    try
    {
      PackageManager localPackageManager = this.b.getPackageManager();
      if (localPackageManager != null)
      {
        PackageInfo localPackageInfo = localPackageManager.getPackageInfo(this.b.getPackageName(), 0);
        if (localPackageInfo != null)
          str = localPackageInfo.packageName;
      }
      return str;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return str;
  }

  protected final String f()
  {
    h.a(this.b);
    if (h.b())
      return h.a();
    return "";
  }

  public final String h()
  {
    WifiInfo localWifiInfo = ((WifiManager)this.b.getSystemService("wifi")).getConnectionInfo();
    if (localWifiInfo != null)
      return localWifiInfo.getMacAddress();
    return "";
  }

  public final String i()
  {
    WindowManager localWindowManager = (WindowManager)this.b.getSystemService("window");
    DisplayMetrics localDisplayMetrics = new DisplayMetrics();
    localWindowManager.getDefaultDisplay().getMetrics(localDisplayMetrics);
    return localDisplayMetrics.widthPixels + "x" + localDisplayMetrics.heightPixels;
  }

  public final String j()
  {
    try
    {
      String str2 = Settings.System.getString(this.b.getContentResolver(), "android_id");
      localObject = str2;
      return c.a.b((String)localObject);
    }
    catch (Exception localException1)
    {
      try
      {
        String str1 = Settings.System.getString(this.b.getContentResolver(), "android_id");
        Object localObject = str1;
      }
      catch (Exception localException2)
      {
      }
    }
    return null;
  }

  public final boolean k()
  {
    return ((ConnectivityManager)this.b.getSystemService("connectivity")).getNetworkInfo(1).getState() == NetworkInfo.State.CONNECTED;
  }

  public final String l()
  {
    try
    {
      NetworkInfo localNetworkInfo = ((ConnectivityManager)this.b.getSystemService("connectivity")).getActiveNetworkInfo();
      if (localNetworkInfo != null)
      {
        if (localNetworkInfo.getType() == 1)
          return "1";
      }
      else
        return "0";
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      return "0";
    }
    return "2";
  }

  public final String m()
  {
    try
    {
      NetworkInfo localNetworkInfo = ((ConnectivityManager)this.b.getSystemService("connectivity")).getActiveNetworkInfo();
      if ((localNetworkInfo != null) && (localNetworkInfo.getType() == 1))
      {
        WifiInfo localWifiInfo = ((WifiManager)this.b.getSystemService("wifi")).getConnectionInfo();
        if (localWifiInfo == null)
          return "NULL";
        String str = localWifiInfo.getSSID();
        return str;
      }
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return null;
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.miaozhen.mzmonitor.f
 * JD-Core Version:    0.6.2
 */