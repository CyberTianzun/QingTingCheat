package com.umeng.fb.util;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Build.VERSION;
import java.io.File;
import java.io.FileInputStream;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import org.json.JSONObject;

public class Helper
{
  public static final String LINE_SEPARATOR = System.getProperty("line.separator");
  private static final String TAG = Helper.class.getName();

  public static String MD5(String paramString)
  {
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
      for (int i = 0; i < arrayOfByte2.length; i++)
      {
        Object[] arrayOfObject = new Object[1];
        arrayOfObject[0] = Byte.valueOf(arrayOfByte2[i]);
        localStringBuffer.append(String.format("%02X", arrayOfObject));
      }
      String str = localStringBuffer.toString();
      return str;
    }
    catch (Exception localException)
    {
    }
    return paramString.replaceAll("[^[a-z][A-Z][0-9][.][_]]", "");
  }

  public static String generateFeedbackId(Context paramContext)
  {
    String str = String.valueOf(System.currentTimeMillis());
    return "FB[" + DeviceConfig.getAppkey(paramContext) + "_" + DeviceConfig.getDeviceIdUmengMD5(paramContext) + "]" + str + String.valueOf((int)(1000.0D + 9000.0D * Math.random()));
  }

  public static String generateReplyID()
  {
    String str = String.valueOf(System.currentTimeMillis());
    return "RP" + str + String.valueOf((int)(1000.0D + 9000.0D * Math.random()));
  }

  public static String getDateTime()
  {
    return getTimeString(new Date());
  }

  public static String getFileMD5(File paramFile)
  {
    byte[] arrayOfByte = new byte[1024];
    try
    {
      if (!paramFile.isFile())
        return "";
      MessageDigest localMessageDigest = MessageDigest.getInstance("MD5");
      FileInputStream localFileInputStream = new FileInputStream(paramFile);
      try
      {
        while (true)
        {
          int i = localFileInputStream.read(arrayOfByte, 0, 1024);
          if (i == -1)
            break;
          localMessageDigest.update(arrayOfByte, 0, i);
        }
      }
      catch (Exception localException1)
      {
      }
      label62: localException1.printStackTrace();
      return null;
      localFileInputStream.close();
      return String.format("%1$032x", new Object[] { new BigInteger(1, localMessageDigest.digest()) });
    }
    catch (Exception localException2)
    {
      break label62;
    }
  }

  public static String getFileSizeDescription(Context paramContext, long paramLong)
  {
    if (paramLong < 1000L)
      return (int)paramLong + "B";
    if (paramLong < 1000000L)
      return Math.round((float)paramLong / 1000.0D) + "K";
    if (paramLong < 1000000000L)
    {
      DecimalFormat localDecimalFormat1 = new DecimalFormat("#0.0");
      return localDecimalFormat1.format((float)paramLong / 1000000.0D) + "M";
    }
    DecimalFormat localDecimalFormat2 = new DecimalFormat("#0.00");
    return localDecimalFormat2.format((float)paramLong / 1000000000.0D) + "G";
  }

  public static String getFileSizeDescription(String paramString)
  {
    while (true)
    {
      long l;
      String str;
      try
      {
        l = Long.valueOf(paramString).longValue();
        if (l < 1024L)
        {
          str = (int)l + "B";
          return str;
        }
      }
      catch (NumberFormatException localNumberFormatException)
      {
        return paramString;
      }
      if (l < 1048576L)
      {
        DecimalFormat localDecimalFormat1 = new DecimalFormat("#0.00");
        str = localDecimalFormat1.format((float)l / 1024.0D) + "K";
      }
      else if (l < 1073741824L)
      {
        DecimalFormat localDecimalFormat2 = new DecimalFormat("#0.00");
        str = localDecimalFormat2.format((float)l / 1048576.0D) + "M";
      }
      else
      {
        DecimalFormat localDecimalFormat3 = new DecimalFormat("#0.00");
        str = localDecimalFormat3.format((float)l / 1073741824.0D) + "G";
      }
    }
  }

  public static JSONObject getMessageHeader(Context paramContext)
  {
    JSONObject localJSONObject = new JSONObject();
    try
    {
      localJSONObject.put("device_id", DeviceConfig.getDeviceId(paramContext));
      localJSONObject.put("idmd5", DeviceConfig.getDeviceIdUmengMD5(paramContext));
      localJSONObject.put("device_model", Build.MODEL);
      localJSONObject.put("appkey", DeviceConfig.getAppkey(paramContext));
      localJSONObject.put("channel", DeviceConfig.getChannel(paramContext));
      localJSONObject.put("app_version", DeviceConfig.getAppVersionName(paramContext));
      localJSONObject.put("version_code", DeviceConfig.getAppVersionCode(paramContext));
      localJSONObject.put("sdk_type", "Android");
      localJSONObject.put("sdk_version", "4.3.2.20140520");
      localJSONObject.put("os", "Android");
      localJSONObject.put("os_version", Build.VERSION.RELEASE);
      localJSONObject.put("country", DeviceConfig.getLocaleInfo(paramContext)[0]);
      localJSONObject.put("language", DeviceConfig.getLocaleInfo(paramContext)[1]);
      localJSONObject.put("timezone", DeviceConfig.getTimeZone(paramContext));
      localJSONObject.put("resolution", DeviceConfig.getResolution(paramContext));
      localJSONObject.put("access", DeviceConfig.getNetworkAccessMode(paramContext)[0]);
      localJSONObject.put("access_subtype", DeviceConfig.getNetworkAccessMode(paramContext)[1]);
      localJSONObject.put("carrier", DeviceConfig.getNetworkOperatorName(paramContext));
      localJSONObject.put("cpu", DeviceConfig.getCPU());
      localJSONObject.put("package", DeviceConfig.getPackageName(paramContext));
      return localJSONObject;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return localJSONObject;
  }

  public static String getTimeString(Date paramDate)
  {
    if (paramDate == null)
      return "";
    return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US).format(paramDate);
  }

  public static String getUmengMD5(String paramString)
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
    catch (NoSuchAlgorithmException localNoSuchAlgorithmException)
    {
      Log.i(TAG, "getMD5 error", localNoSuchAlgorithmException);
    }
    return "";
  }

  public static boolean isAbsoluteUrl(String paramString)
  {
    if (isEmpty(paramString));
    String str;
    do
    {
      return false;
      str = paramString.trim().toLowerCase();
    }
    while ((!str.startsWith("http://")) && (!str.startsWith("https://")));
    return true;
  }

  public static boolean isEmpty(String paramString)
  {
    return (paramString == null) || (paramString.length() == 0);
  }

  public static void openApp(Context paramContext, String paramString)
  {
    paramContext.startActivity(paramContext.getPackageManager().getLaunchIntentForPackage(paramString));
  }

  public static boolean openUrlSchema(Context paramContext, String paramString)
  {
    try
    {
      paramContext.startActivity(new Intent("android.intent.action.VIEW", Uri.parse(paramString)));
      return true;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return false;
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.umeng.fb.util.Helper
 * JD-Core Version:    0.6.2
 */