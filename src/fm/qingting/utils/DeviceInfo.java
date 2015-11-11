package fm.qingting.utils;

import android.content.Context;
import android.os.Build;
import android.os.Build.VERSION;
import android.provider.Settings.Secure;
import android.telephony.TelephonyManager;
import java.security.MessageDigest;
import java.util.UUID;

public class DeviceInfo
{
  private static int change = 0;
  private static String mAndroidDeviceId = null;
  private static String mDeviceIMEI = null;
  private static String mDeviceId = null;

  public static final String getAndroidDeviceId(Context paramContext)
  {
    if ((mAndroidDeviceId == null) && (paramContext != null))
      try
      {
        mAndroidDeviceId = ((TelephonyManager)paramContext.getSystemService("phone")).getDeviceId();
        String str = mAndroidDeviceId;
        return str;
      }
      catch (Exception localException)
      {
      }
    if (mAndroidDeviceId == null)
      mAndroidDeviceId = "UNKNOWN";
    return mAndroidDeviceId;
  }

  public static String getAndroidOsVersion()
  {
    return Build.VERSION.RELEASE;
  }

  public static int getChangeCnt()
  {
    return change;
  }

  public static String getChangeUniqueId(Context paramContext)
  {
    if (paramContext != null);
    while (true)
    {
      String str1;
      String str3;
      try
      {
        TelephonyManager localTelephonyManager = (TelephonyManager)paramContext.getSystemService("phone");
        str1 = "" + localTelephonyManager.getDeviceId();
        if (change <= 0)
          break label252;
        str2 = str1 + String.valueOf(3 * change);
        str3 = "" + localTelephonyManager.getSimSerialNumber();
        if (change <= 0)
          break label245;
        str4 = str3 + String.valueOf(5 * change);
        String str5 = "" + Settings.Secure.getString(paramContext.getContentResolver(), "android_id");
        if (change > 0)
          str5 = str5 + String.valueOf(2 * change);
        UUID localUUID = new UUID(str5.hashCode(), str2.hashCode() << 32 | str4.hashCode());
        change = 1 + change;
        String str6 = localUUID.toString();
        return str6;
      }
      catch (Exception localException)
      {
      }
      return mDeviceId;
      label245: String str4 = str3;
      continue;
      label252: String str2 = str1;
    }
  }

  public static String getDeviceIMEI(Context paramContext)
  {
    if ((mDeviceIMEI == null) && (paramContext != null));
    try
    {
      TelephonyManager localTelephonyManager = (TelephonyManager)paramContext.getSystemService("phone");
      mDeviceIMEI = "" + localTelephonyManager.getDeviceId();
      label45: if ((mDeviceIMEI == null) || (mDeviceIMEI.equalsIgnoreCase("null")))
        mDeviceIMEI = "UnknowIMEI";
      return mDeviceIMEI;
    }
    catch (Exception localException)
    {
      break label45;
    }
  }

  public static String getDeviceName()
  {
    return (Build.BRAND + "_br_" + "FM__" + Build.MANUFACTURER + "_" + Build.MODEL).replace(",", "");
  }

  public static String getUniqueId(Context paramContext)
  {
    if ((mDeviceId == null) && (paramContext != null));
    try
    {
      TelephonyManager localTelephonyManager = (TelephonyManager)paramContext.getSystemService("phone");
      String str1 = "" + localTelephonyManager.getDeviceId();
      String str2 = "" + localTelephonyManager.getSimSerialNumber();
      mDeviceId = new UUID(("" + Settings.Secure.getString(paramContext.getContentResolver(), "android_id")).hashCode(), str1.hashCode() << 32 | str2.hashCode()).toString();
      label126: if (mDeviceId == null)
        mDeviceId = "UnknowUser_";
      return mDeviceId;
    }
    catch (Exception localException)
    {
      break label126;
    }
  }

  public static String md5(String paramString)
  {
    try
    {
      MessageDigest localMessageDigest = MessageDigest.getInstance("MD5");
      localMessageDigest.update(paramString.getBytes());
      byte[] arrayOfByte = localMessageDigest.digest();
      StringBuffer localStringBuffer = new StringBuffer();
      for (int i = 0; i < arrayOfByte.length; i++)
        localStringBuffer.append(Integer.toHexString(0xFF & arrayOfByte[i]));
      String str = localStringBuffer.toString();
      return str;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return paramString;
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.utils.DeviceInfo
 * JD-Core Version:    0.6.2
 */