package fm.qingting.framework.utils;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.MemoryInfo;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.provider.Settings.Secure;
import android.provider.Settings.SettingNotFoundException;
import android.provider.Settings.System;
import android.telephony.TelephonyManager;
import android.util.Log;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.List;
import java.util.UUID;

public class SystemInfo
{
  private static SystemInfo instance;
  private Activity activity;

  public static String getCpuName()
  {
    try
    {
      String[] arrayOfString = new BufferedReader(new FileReader("/proc/cpuinfo")).readLine().split(":\\s+", 2);
      for (int i = 0; ; i++)
        if (i >= arrayOfString.length)
        {
          String str = arrayOfString[1];
          return str;
        }
    }
    catch (FileNotFoundException localFileNotFoundException)
    {
      localFileNotFoundException.printStackTrace();
      return null;
    }
    catch (IOException localIOException)
    {
      while (true)
        localIOException.printStackTrace();
    }
  }

  public static String getCurCpuFreq()
  {
    try
    {
      String str = new BufferedReader(new FileReader("/sys/devices/system/cpu/cpu0/cpufreq/scaling_cur_freq")).readLine().trim();
      return str;
    }
    catch (FileNotFoundException localFileNotFoundException)
    {
      localFileNotFoundException.printStackTrace();
      return "N/A";
    }
    catch (IOException localIOException)
    {
      localIOException.printStackTrace();
    }
    return "N/A";
  }

  public static SystemInfo getInstance()
  {
    if (instance == null)
      instance = new SystemInfo();
    return instance;
  }

  public static String getMinCpuFreq()
  {
    Object localObject = "";
    try
    {
      InputStream localInputStream = new ProcessBuilder(new String[] { "/system/bin/cat", "/sys/devices/system/cpu/cpu0/cpufreq/cpuinfo_min_freq" }).start().getInputStream();
      byte[] arrayOfByte = new byte[24];
      while (true)
      {
        if (localInputStream.read(arrayOfByte) == -1)
        {
          localInputStream.close();
          return ((String)localObject).trim();
        }
        String str = localObject + new String(arrayOfByte);
        localObject = str;
      }
    }
    catch (IOException localIOException)
    {
      while (true)
      {
        localIOException.printStackTrace();
        localObject = "N/A";
      }
    }
  }

  public static String getUUID(Context paramContext)
  {
    TelephonyManager localTelephonyManager = (TelephonyManager)paramContext.getSystemService("phone");
    String str1;
    String str2;
    label30: String str3;
    if (localTelephonyManager.getDeviceId() == null)
    {
      str1 = "unknown";
      if (localTelephonyManager.getSimSerialNumber() != null)
        break label89;
      str2 = "unknown";
      str3 = Settings.Secure.getString(paramContext.getContentResolver(), "android_id");
      if (str3 != null)
        break label97;
    }
    label89: label97: for (String str4 = "unknown"; ; str4 = str3)
    {
      return new UUID(str4.hashCode(), str1.hashCode() << 32 | str2.hashCode()).toString();
      str1 = localTelephonyManager.getDeviceId();
      break;
      str2 = localTelephonyManager.getSimSerialNumber();
      break label30;
    }
  }

  public String getActivityManager()
  {
    StringBuffer localStringBuffer = new StringBuffer("/n======/nActivityManager/n");
    List localList = ((ActivityManager)this.activity.getSystemService("activity")).getRunningAppProcesses();
    for (int i = 0; ; i++)
    {
      if (i >= localList.size())
        return localStringBuffer.toString();
      localStringBuffer.append(((ActivityManager.RunningAppProcessInfo)localList.get(i)).toString() + "/n");
    }
  }

  public long getAvailMem(Context paramContext)
  {
    ActivityManager localActivityManager = (ActivityManager)paramContext.getSystemService("activity");
    ActivityManager.MemoryInfo localMemoryInfo = new ActivityManager.MemoryInfo();
    localActivityManager.getMemoryInfo(localMemoryInfo);
    return localMemoryInfo.availMem / 1048576L;
  }

  public String getCpu()
  {
    String str = "";
    try
    {
      InputStream localInputStream = new ProcessBuilder(new String[] { "/system/bin/cat", "/proc/cpuinfo" }).start().getInputStream();
      byte[] arrayOfByte = new byte[1024];
      if (localInputStream.read(arrayOfByte) != -1)
      {
        System.out.println(new String(arrayOfByte));
        str = str + new String(arrayOfByte);
        return str.toString();
      }
      localInputStream.close();
      return str.toString();
    }
    catch (IOException localIOException)
    {
      while (true)
        localIOException.printStackTrace();
    }
  }

  public float getMaxCpuFreq()
  {
    Object localObject = "";
    try
    {
      InputStream localInputStream = new ProcessBuilder(new String[] { "/system/bin/cat", "/sys/devices/system/cpu/cpu0/cpufreq/cpuinfo_max_freq" }).start().getInputStream();
      arrayOfByte = new byte[24];
      if (localInputStream.read(arrayOfByte) == -1)
        localInputStream.close();
    }
    catch (IOException localIOException)
    {
      try
      {
        while (true)
        {
          byte[] arrayOfByte;
          float f = Float.parseFloat((String)localObject);
          return f;
          String str = localObject + new String(arrayOfByte);
          localObject = str;
          continue;
          localIOException = localIOException;
          localIOException.printStackTrace();
          localObject = "N/A";
        }
      }
      catch (NumberFormatException localNumberFormatException)
      {
      }
    }
    return 0.0F;
  }

  public String getNetWorkIP()
  {
    CMDExecute localCMDExecute = new CMDExecute();
    try
    {
      String str = localCMDExecute.run(new String[] { "/system/bin/netcfg" }, "/system/bin/");
      return str;
    }
    catch (IOException localIOException)
    {
    }
    return "/n======/n";
  }

  public float getOldBrightness()
  {
    try
    {
      int i = Settings.System.getInt(this.activity.getContentResolver(), "screen_brightness");
      return i;
    }
    catch (Settings.SettingNotFoundException localSettingNotFoundException)
    {
    }
    return 255.0F;
  }

  public String getTelephonyManager(Context paramContext)
  {
    TelephonyManager localTelephonyManager = (TelephonyManager)paramContext.getSystemService("phone");
    return (new StringBuilder(String.valueOf(new StringBuilder(String.valueOf(new StringBuilder(String.valueOf(new StringBuilder(String.valueOf(new StringBuilder(String.valueOf("/n======/nTelephonyManager/n")).append("getDataState() = ").append(localTelephonyManager.getDataState()).append("/n").toString())).append("getDeviceId() = ").append(localTelephonyManager.getDeviceId()).append("/n").toString())).append("getLine1Number() = ").append(localTelephonyManager.getLine1Number()).append("/n").toString())).append("getSimSerialNumber() = ").append(localTelephonyManager.getSimSerialNumber()).append("/n").toString())).append("getSubscriberId() = ").append(localTelephonyManager.getSubscriberId()).append("/n").toString() + "isNetworkRoaming() = " + localTelephonyManager.isNetworkRoaming() + "/n").toString();
  }

  public void getTotalMemory()
  {
    try
    {
      BufferedReader localBufferedReader = new BufferedReader(new FileReader("/proc/meminfo"), 8192);
      while (true)
      {
        String str = localBufferedReader.readLine();
        if (str == null)
          return;
        Log.e("test.java", "---" + str);
      }
    }
    catch (IOException localIOException)
    {
    }
  }

  public String getWifiInfo()
  {
    WifiManager localWifiManager = (WifiManager)this.activity.getSystemService("wifi");
    StringBuilder localStringBuilder = new StringBuilder("/nState:");
    if (localWifiManager.getWifiState() != 3)
      return "/n======/nWifiState:/nWIFI_STATE_UNKNOWN/n";
    return "/n======/nWifiState:/nWIFI_STATE_KNOWN/n".toString();
  }

  public void init(Activity paramActivity)
  {
    this.activity = paramActivity;
  }

  public boolean isConnected()
  {
    ConnectivityManager localConnectivityManager = (ConnectivityManager)this.activity.getSystemService("connectivity");
    TelephonyManager localTelephonyManager = (TelephonyManager)this.activity.getSystemService("phone");
    NetworkInfo localNetworkInfo = localConnectivityManager.getActiveNetworkInfo();
    if ((localNetworkInfo == null) || (!localConnectivityManager.getBackgroundDataSetting()));
    int i;
    int j;
    do
    {
      return false;
      i = localNetworkInfo.getType();
      j = localNetworkInfo.getSubtype();
      if (i == 1)
        return localNetworkInfo.isConnected();
    }
    while ((i != 0) || (j != 3) || (localTelephonyManager.isNetworkRoaming()));
    return localNetworkInfo.isConnected();
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.framework.utils.SystemInfo
 * JD-Core Version:    0.6.2
 */