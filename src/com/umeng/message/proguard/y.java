package com.umeng.message.proguard;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import com.umeng.common.message.Log;
import java.io.File;
import java.io.FileInputStream;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

public class y
{
  public static final String a = System.getProperty("line.separator");
  private static final String b = "helper";

  public static String a()
  {
    return a(new Date());
  }

  public static String a(Context paramContext, long paramLong)
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

  public static String a(File paramFile)
  {
    byte[] arrayOfByte = new byte[1024];
    MessageDigest localMessageDigest;
    FileInputStream localFileInputStream;
    try
    {
      if (!paramFile.isFile())
        return "";
      localMessageDigest = MessageDigest.getInstance("MD5");
      localFileInputStream = new FileInputStream(paramFile);
      while (true)
      {
        int i = localFileInputStream.read(arrayOfByte, 0, 1024);
        if (i == -1)
          break;
        localMessageDigest.update(arrayOfByte, 0, i);
      }
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      return null;
    }
    localFileInputStream.close();
    return String.format("%1$032x", new Object[] { new BigInteger(1, localMessageDigest.digest()) });
  }

  public static String a(String paramString)
  {
    int i = 0;
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
      while (i < arrayOfByte2.length)
      {
        Object[] arrayOfObject = new Object[1];
        arrayOfObject[0] = Byte.valueOf(arrayOfByte2[i]);
        localStringBuffer.append(String.format("%02X", arrayOfObject));
        i++;
      }
      String str = localStringBuffer.toString();
      return str;
    }
    catch (Exception localException)
    {
    }
    return paramString.replaceAll("[^[a-z][A-Z][0-9][.][_]]", "");
  }

  public static String a(Date paramDate)
  {
    if (paramDate == null)
      return "";
    return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(paramDate);
  }

  public static void a(Context paramContext, String paramString)
  {
    paramContext.startActivity(paramContext.getPackageManager().getLaunchIntentForPackage(paramString));
  }

  public static boolean a(Context paramContext)
  {
    try
    {
      List localList = ((ActivityManager)paramContext.getSystemService("activity")).getRunningAppProcesses();
      if (localList == null)
        return false;
      String str = paramContext.getPackageName();
      Iterator localIterator = localList.iterator();
      while (localIterator.hasNext())
      {
        ActivityManager.RunningAppProcessInfo localRunningAppProcessInfo = (ActivityManager.RunningAppProcessInfo)localIterator.next();
        if (localRunningAppProcessInfo.importance == 100)
        {
          boolean bool = localRunningAppProcessInfo.processName.equals(str);
          if (bool)
            return true;
        }
      }
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return false;
  }

  public static String b(String paramString)
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
      Log.a("helper", "getMD5 error", localNoSuchAlgorithmException);
    }
    return "";
  }

  public static boolean b(Context paramContext, String paramString)
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

  public static String c(String paramString)
  {
    long l;
    try
    {
      l = Long.valueOf(paramString).longValue();
      if (l < 1024L)
        return (int)l + "B";
    }
    catch (NumberFormatException localNumberFormatException)
    {
      return paramString;
    }
    if (l < 1048576L)
    {
      DecimalFormat localDecimalFormat1 = new DecimalFormat("#0.00");
      return localDecimalFormat1.format((float)l / 1024.0D) + "K";
    }
    if (l < 1073741824L)
    {
      DecimalFormat localDecimalFormat2 = new DecimalFormat("#0.00");
      return localDecimalFormat2.format((float)l / 1048576.0D) + "M";
    }
    DecimalFormat localDecimalFormat3 = new DecimalFormat("#0.00");
    return localDecimalFormat3.format((float)l / 1073741824.0D) + "G";
  }

  public static boolean d(String paramString)
  {
    return (paramString == null) || (paramString.length() == 0);
  }

  public static boolean e(String paramString)
  {
    if (d(paramString));
    String str;
    do
    {
      return false;
      str = paramString.trim().toLowerCase(Locale.US);
    }
    while ((!str.startsWith("http://")) && (!str.startsWith("https://")));
    return true;
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.umeng.message.proguard.y
 * JD-Core Version:    0.6.2
 */