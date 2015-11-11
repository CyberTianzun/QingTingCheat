package com.tencent.open.a;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Build.VERSION;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;

public class a
{
  static String a = null;
  private static String b = null;

  public static String a(Context paramContext)
  {
    if ((a != null) && (a.length() > 0))
      return a;
    if (paramContext == null)
      return "";
    try
    {
      String str = ((TelephonyManager)paramContext.getSystemService("phone")).getDeviceId();
      return str;
    }
    catch (Exception localException)
    {
    }
    return "";
  }

  public static String b(Context paramContext)
  {
    try
    {
      if (b == null)
      {
        WindowManager localWindowManager = (WindowManager)paramContext.getSystemService("window");
        DisplayMetrics localDisplayMetrics = new DisplayMetrics();
        localWindowManager.getDefaultDisplay().getMetrics(localDisplayMetrics);
        StringBuilder localStringBuilder = new StringBuilder();
        localStringBuilder.append("imei=").append(a(paramContext)).append('&');
        localStringBuilder.append("model=").append(Build.MODEL).append('&');
        localStringBuilder.append("os=").append(Build.VERSION.RELEASE).append('&');
        localStringBuilder.append("apilevel=").append(Build.VERSION.SDK_INT).append('&');
        localStringBuilder.append("display=").append(localDisplayMetrics.widthPixels).append('*').append(localDisplayMetrics.heightPixels).append('&');
        localStringBuilder.append("manu=").append(Build.MANUFACTURER).append("&");
        b = localStringBuilder.toString();
      }
      String str = b;
      return str;
    }
    catch (Exception localException)
    {
    }
    return null;
  }

  public static String c(Context paramContext)
  {
    ConnectivityManager localConnectivityManager = (ConnectivityManager)paramContext.getSystemService("connectivity");
    if (localConnectivityManager == null)
      return "MOBILE";
    NetworkInfo localNetworkInfo = localConnectivityManager.getActiveNetworkInfo();
    if (localNetworkInfo != null)
      return localNetworkInfo.getTypeName();
    return "MOBILE";
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.tencent.open.a.a
 * JD-Core Version:    0.6.2
 */