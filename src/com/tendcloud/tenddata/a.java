package com.tendcloud.tenddata;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build.VERSION;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.provider.Settings.Secure;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class a
{
  static TelephonyManager a;
  static String b;
  private static final String c = "pref.deviceid.key";
  private static final String d = "00:00:00:00:00:00";
  private static final Pattern e = Pattern.compile("^([0-9A-F]{2}:){5}([0-9A-F]{2})$");
  private static final String f = ".tcookieid";
  private static String g = null;

  private static String a()
  {
    String str = null;
    File[] arrayOfFile1 = new File("/").listFiles();
    int i = arrayOfFile1.length;
    label156: for (int j = 0; ; j++)
    {
      File localFile1;
      if (j < i)
      {
        localFile1 = arrayOfFile1[j];
        if ((!localFile1.isDirectory()) || ("/sdcard".equals(localFile1.getAbsolutePath())))
          continue;
        if (localFile1.canWrite())
        {
          str = a(new File(localFile1, ".tcookieid"));
          if (x.a(str));
        }
      }
      else
      {
        return str;
      }
      if (localFile1.listFiles() != null)
      {
        File[] arrayOfFile2 = localFile1.listFiles();
        int k = arrayOfFile2.length;
        for (int m = 0; ; m++)
        {
          if (m >= k)
            break label156;
          File localFile2 = arrayOfFile2[m];
          if (localFile2.isDirectory())
          {
            str = a(new File(localFile2, ".tcookieid"));
            if (!x.a(str))
              break;
          }
        }
      }
    }
  }

  private static String a(Context paramContext, boolean paramBoolean)
  {
    if ("mounted".equals(Environment.getExternalStorageState()))
    {
      File localFile = Environment.getExternalStorageDirectory();
      if (paramBoolean);
      for (String str1 = ".tcookieid"; ; str1 = ".tcookieid" + n(paramContext))
      {
        String str2 = a(new File(localFile, str1));
        if (x.a(str2))
          str2 = a(new File(Environment.getExternalStorageDirectory(), ".tid" + n(paramContext)));
        return str2;
      }
    }
    return "";
  }

  private static String a(File paramFile)
  {
    try
    {
      if ((paramFile.exists()) && (paramFile.canRead()))
      {
        FileInputStream localFileInputStream = new FileInputStream(paramFile);
        byte[] arrayOfByte = new byte[''];
        int i = localFileInputStream.read(arrayOfByte);
        localFileInputStream.close();
        String str = new String(arrayOfByte, 0, i);
        return str;
      }
    }
    catch (Exception localException)
    {
    }
    return null;
  }

  public static void a(Context paramContext)
  {
    a = (TelephonyManager)paramContext.getSystemService("phone");
  }

  private static void a(Context paramContext, String paramString)
  {
    for (File localFile1 : new File("/").listFiles())
      if ((localFile1.isDirectory()) && (!"/sdcard".equals(localFile1.getAbsolutePath())))
      {
        if ((localFile1.canWrite()) && (!new File(localFile1, ".tcookieid" + n(paramContext)).exists()))
          a(new File(localFile1, ".tcookieid"), paramString);
        if (localFile1.listFiles() != null)
          for (File localFile2 : localFile1.listFiles())
            if ((localFile2.isDirectory()) && (localFile2.canWrite()) && (!new File(localFile2, ".tcookieid" + n(paramContext)).exists()))
              a(new File(localFile2, ".tcookieid"), paramString);
      }
  }

  private static void a(Context paramContext, String paramString, boolean paramBoolean)
  {
    File localFile = Environment.getExternalStorageDirectory();
    if (paramBoolean);
    for (String str = ".tcookieid"; ; str = ".tcookieid" + n(paramContext))
    {
      a(new File(localFile, str), paramString);
      return;
    }
  }

  private static void a(File paramFile, String paramString)
  {
    try
    {
      FileOutputStream localFileOutputStream = new FileOutputStream(paramFile);
      localFileOutputStream.write(paramString.getBytes());
      localFileOutputStream.close();
      if (Build.VERSION.SDK_INT < 9)
      {
        Runtime.getRuntime().exec("chmod 444 " + paramFile.getAbsolutePath());
        return;
      }
      Class localClass = paramFile.getClass();
      Class[] arrayOfClass = new Class[2];
      arrayOfClass[0] = Boolean.TYPE;
      arrayOfClass[1] = Boolean.TYPE;
      Method localMethod = localClass.getMethod("setReadable", arrayOfClass);
      Object[] arrayOfObject = new Object[2];
      arrayOfObject[0] = Boolean.valueOf(true);
      arrayOfObject[1] = Boolean.valueOf(false);
      localMethod.invoke(paramFile, arrayOfObject);
      return;
    }
    catch (Exception localException)
    {
    }
  }

  public static String b(Context paramContext)
  {
    try
    {
      if (b == null)
        b = j(paramContext);
      String str = b;
      return str;
    }
    finally
    {
    }
  }

  private static void b(Context paramContext, String paramString)
  {
    SharedPreferences localSharedPreferences = paramContext.getSharedPreferences("tdid", 0);
    if (localSharedPreferences != null)
    {
      SharedPreferences.Editor localEditor = localSharedPreferences.edit();
      localEditor.putString("pref.deviceid.key", paramString);
      localEditor.commit();
    }
  }

  private static boolean b()
  {
    while (true)
    {
      try
      {
        if (Build.VERSION.SDK_INT < 9)
          break label44;
        boolean bool2 = ((Boolean)Environment.class.getMethod("isExternalStorageRemovable", null).invoke(null, null)).booleanValue();
        bool1 = bool2;
        if (!bool1)
          return true;
      }
      catch (Exception localException)
      {
        bool1 = true;
        continue;
      }
      return false;
      label44: boolean bool1 = true;
    }
  }

  public static String c(Context paramContext)
  {
    return Settings.Secure.getString(paramContext.getContentResolver(), "android_id");
  }

  public static String d(Context paramContext)
  {
    if (x.a(paramContext, "android.permission.READ_PHONE_STATE"))
    {
      if (a == null)
        a(paramContext);
      return a.getDeviceId();
    }
    return "";
  }

  public static String e(Context paramContext)
  {
    if (x.a(paramContext, "android.permission.READ_PHONE_STATE"))
    {
      if (a == null)
        a(paramContext);
      return a.getSimSerialNumber();
    }
    return "";
  }

  public static String f(Context paramContext)
  {
    if (x.a(paramContext, "android.permission.READ_PHONE_STATE"))
    {
      if (a == null)
        a(paramContext);
      return a.getSubscriberId();
    }
    return "";
  }

  public static String g(Context paramContext)
  {
    if (x.a(paramContext, "android.permission.ACCESS_WIFI_STATE"))
    {
      WifiManager localWifiManager = (WifiManager)paramContext.getSystemService("wifi");
      if (localWifiManager.isWifiEnabled())
      {
        WifiInfo localWifiInfo = localWifiManager.getConnectionInfo();
        if (localWifiInfo != null)
        {
          String str = localWifiInfo.getMacAddress().toUpperCase().trim();
          if (("00:00:00:00:00:00".equals(str)) || (!e.matcher(str).matches()))
            str = "";
          return str;
        }
      }
    }
    return "";
  }

  public static final String h(Context paramContext)
  {
    try
    {
      Object localObject = Class.forName("com.google.android.gms.ads.identifier.AdvertisingIdClient").getMethod("getAdvertisingIdInfo", new Class[] { Context.class }).invoke(null, new Object[] { paramContext });
      String str = (String)Class.forName("com.google.android.gms.ads.identifier.AdvertisingIdClient.Info").getMethod("getId", null).invoke(localObject, null);
      return str;
    }
    catch (Exception localException)
    {
    }
    return null;
  }

  public static final String i(Context paramContext)
  {
    String str1 = g(paramContext);
    if (!TextUtils.isEmpty(str1))
      str1 = String.valueOf(Long.parseLong(str1.replaceAll(":", ""), 16));
    String str2 = c(paramContext);
    String str3 = d(paramContext);
    String str4 = f(paramContext);
    String str5 = e(paramContext);
    String str6 = b(paramContext);
    String str7 = h(paramContext);
    return 2 + "|" + str1 + "|" + str2 + "|" + str3 + "|" + str4 + "|" + str5 + "|" + str6 + "|" + str7;
  }

  private static String j(Context paramContext)
  {
    String str1 = k(paramContext);
    String str2 = a();
    boolean bool = b();
    String str3 = a(paramContext, bool);
    String[] arrayOfString = { str1, str2, str3 };
    int i = arrayOfString.length;
    int j = 0;
    String str4;
    if (j < i)
    {
      str4 = arrayOfString[j];
      if (x.a(str4));
    }
    while (true)
    {
      if (x.a(str4))
        str4 = l(paramContext);
      if (x.a(str1))
        b(paramContext, str4);
      if (x.a(str3))
        a(paramContext, str4, bool);
      if (x.a(str2))
        a(paramContext, str4);
      return str4;
      j++;
      break;
      str4 = null;
    }
  }

  private static String k(Context paramContext)
  {
    String str = aa.b(paramContext, "tdid", "pref.deviceid.key", null);
    if (x.a(str))
      str = PreferenceManager.getDefaultSharedPreferences(paramContext).getString("pref.deviceid.key", null);
    return str;
  }

  private static String l(Context paramContext)
  {
    String str = m(paramContext);
    return "3" + x.b(str);
  }

  private static String m(Context paramContext)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append(d(paramContext)).append('-').append(g(paramContext)).append('-').append(c(paramContext));
    return localStringBuilder.toString();
  }

  private static String n(Context paramContext)
  {
    if (g == null)
    {
      List localList = ((SensorManager)paramContext.getSystemService("sensor")).getSensorList(-1);
      Sensor[] arrayOfSensor = new Sensor[64];
      Iterator localIterator = localList.iterator();
      while (localIterator.hasNext())
      {
        Sensor localSensor = (Sensor)localIterator.next();
        if ((localSensor.getType() < arrayOfSensor.length) && (localSensor.getType() >= 0))
          arrayOfSensor[localSensor.getType()] = localSensor;
      }
      StringBuffer localStringBuffer = new StringBuffer();
      for (int i = 0; i < arrayOfSensor.length; i++)
        if (arrayOfSensor[i] != null)
          localStringBuffer.append(i).append('.').append(arrayOfSensor[i].getVendor()).append('-').append(arrayOfSensor[i].getName()).append('-').append(arrayOfSensor[i].getVersion()).append('\n');
      g = String.valueOf(localStringBuffer.toString().hashCode());
    }
    return g;
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.tendcloud.tenddata.a
 * JD-Core Version:    0.6.2
 */