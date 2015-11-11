package org.android.agoo.helper;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.provider.Settings.Secure;
import android.telephony.TelephonyManager;
import com.umeng.message.proguard.P;
import java.lang.reflect.Method;
import java.util.Random;

public class PhoneHelper
{
  public static final String IMEI = "imei";
  public static final String IMSI = "imsi";
  public static final String MACADDRESS = "mac_address";

  private static String a()
  {
    StringBuffer localStringBuffer1 = new StringBuffer();
    long l1 = System.currentTimeMillis();
    String str = Long.toString(l1);
    localStringBuffer1.append(str.substring(-5 + str.length()));
    StringBuffer localStringBuffer2 = new StringBuffer();
    localStringBuffer2.append(Build.MODEL.replaceAll(" ", ""));
    while (localStringBuffer2.length() < 6)
      localStringBuffer2.append('0');
    localStringBuffer1.append(localStringBuffer2.substring(0, 6));
    Random localRandom = new Random(l1);
    for (long l2 = 0L; l2 < 4096L; l2 = localRandom.nextLong());
    localStringBuffer1.append(Long.toHexString(l2).substring(0, 4));
    return localStringBuffer1.toString();
  }

  public static String getAndroidId(Context paramContext)
  {
    return Settings.Secure.getString(paramContext.getContentResolver(), "android_id");
  }

  public static String getImei(Context paramContext)
  {
    SharedPreferences localSharedPreferences = P.f(paramContext);
    String str1 = localSharedPreferences.getString("imei", null);
    if ((str1 == null) || (str1.length() == 0))
    {
      String str2 = ((TelephonyManager)paramContext.getSystemService("phone")).getDeviceId();
      if ((str2 == null) || (str2.length() == 0))
        str2 = a();
      for (str1 = str2.replaceAll(" ", "").trim(); str1.length() < 15; str1 = "0" + str1);
      SharedPreferences.Editor localEditor = localSharedPreferences.edit();
      localEditor.putString("imei", str1);
      localEditor.commit();
    }
    return str1.trim();
  }

  public static String getImsi(Context paramContext)
  {
    SharedPreferences localSharedPreferences = P.f(paramContext);
    String str1 = localSharedPreferences.getString("imsi", null);
    if ((str1 == null) || (str1.length() == 0))
    {
      String str2 = ((TelephonyManager)paramContext.getSystemService("phone")).getSubscriberId();
      if ((str2 == null) || (str2.length() == 0))
        str2 = a();
      for (str1 = str2.replaceAll(" ", "").trim(); str1.length() < 15; str1 = "0" + str1);
      SharedPreferences.Editor localEditor = localSharedPreferences.edit();
      localEditor.putString("imsi", str1);
      localEditor.commit();
    }
    return str1;
  }

  public static String getLocalMacAddress(Context paramContext)
  {
    String str = ((WifiManager)paramContext.getSystemService("wifi")).getConnectionInfo().getMacAddress();
    if ((str == null) || ("".equals(str)))
      return P.f(paramContext).getString("mac_address", "");
    SharedPreferences.Editor localEditor = P.f(paramContext).edit();
    localEditor.putString("mac_address", str);
    localEditor.commit();
    return str;
  }

  public static String getOriginalImei(Context paramContext)
  {
    String str = ((TelephonyManager)paramContext.getSystemService("phone")).getDeviceId();
    if (str != null)
      str = str.trim();
    return str;
  }

  public static String getOriginalImsi(Context paramContext)
  {
    String str = ((TelephonyManager)paramContext.getSystemService("phone")).getSubscriberId();
    if (str != null)
      str = str.trim();
    return str;
  }

  public static String getSerialNum()
  {
    try
    {
      Class localClass = Class.forName("android.os.SystemProperties");
      String str = (String)localClass.getMethod("get", new Class[] { String.class, String.class }).invoke(localClass, new Object[] { "ro.serialno", "unknown" });
      return str;
    }
    catch (Throwable localThrowable)
    {
    }
    return null;
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     org.android.agoo.helper.PhoneHelper
 * JD-Core Version:    0.6.2
 */