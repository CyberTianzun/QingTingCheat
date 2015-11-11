package cn.com.iresearch.mapptracker.fm.util;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningTaskInfo;
import android.content.ComponentName;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.ActivityInfo;
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
import android.os.Bundle;
import android.provider.Settings.Secure;
import android.telephony.CellLocation;
import android.telephony.TelephonyManager;
import android.telephony.cdma.CdmaCellLocation;
import android.telephony.gsm.GsmCellLocation;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;
import cn.com.iresearch.mapptracker.fm.IRMonitor;
import cn.com.iresearch.mapptracker.fm.dao.MATMessage;
import cn.com.iresearch.mapptracker.fm.dao.b;
import java.io.File;
import java.io.PrintStream;
import java.net.URLDecoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TimeZone;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.params.HttpClientParams;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

public final class f
{
  private static SharedPreferences a;
  private static SharedPreferences.Editor b;

  public static MATMessage a(Context paramContext, String paramString1, String paramString2)
  {
    String str1 = a.a(paramString1, "UTF-8", paramString1.length());
    MATMessage localMATMessage1 = new MATMessage();
    try
    {
      String str2 = e.a(paramContext, str1);
      System.out.println(str2);
      String str3 = IRMonitor.a;
      MATMessage localMATMessage2 = a(paramString2, "p=" + str3 + "&etype=" + "2" + "&msg=" + str2);
      return localMATMessage2;
    }
    catch (Throwable localThrowable)
    {
      localThrowable.printStackTrace();
    }
    return localMATMessage1;
  }

  private static MATMessage a(String paramString1, String paramString2)
  {
    MATMessage localMATMessage = new MATMessage();
    String str = null;
    try
    {
      StringEntity localStringEntity = new StringEntity(paramString2, "UTF-8");
      DefaultHttpClient localDefaultHttpClient = new DefaultHttpClient();
      HttpClientParams.setCookiePolicy(localDefaultHttpClient.getParams(), "compatibility");
      localDefaultHttpClient.getParams().setParameter("http.protocol.single-cookie-header", Boolean.valueOf(true));
      localDefaultHttpClient.getParams().setParameter("http.connection.timeout", Integer.valueOf(10000));
      boolean bool = IRMonitor.c;
      str = null;
      if (bool)
        Log.e("MAT_SESSION", "send start---------- ");
      HttpClientParams.setCookiePolicy(localDefaultHttpClient.getParams(), "compatibility");
      HttpPost localHttpPost = new HttpPost(paramString1);
      localStringEntity.setContentType("application/x-www-form-urlencoded");
      localHttpPost.setEntity(localStringEntity);
      HttpResponse localHttpResponse = localDefaultHttpClient.execute(localHttpPost);
      int i = localHttpResponse.getStatusLine().getStatusCode();
      str = URLDecoder.decode(EntityUtils.toString(localHttpResponse.getEntity())) + " status:" + i + " url:" + paramString1;
      switch (i)
      {
      default:
        localMATMessage.setFlag(false);
        localMATMessage.setMsg(str);
      case 200:
      }
      while (true)
      {
        if (IRMonitor.c)
          Log.e("MAT_SESSION", "send end---------- ");
        return localMATMessage;
        localMATMessage.setFlag(true);
        localMATMessage.setMsg(str);
      }
    }
    catch (Exception localException)
    {
      while (true)
      {
        localMATMessage.setFlag(false);
        localMATMessage.setMsg(str);
      }
    }
  }

  public static b a(Context paramContext, String paramString)
  {
    SharedPreferences.Editor localEditor = paramContext.getSharedPreferences("MATSharedPreferences", 0).edit();
    MATMessage localMATMessage = new MATMessage();
    DefaultHttpClient localDefaultHttpClient = new DefaultHttpClient();
    localDefaultHttpClient.getParams().setParameter("http.connection.timeout", Integer.valueOf(5000));
    try
    {
      HttpResponse localHttpResponse = localDefaultHttpClient.execute(new HttpGet(paramString));
      int i = localHttpResponse.getStatusLine().getStatusCode();
      String str1 = URLDecoder.decode(EntityUtils.toString(localHttpResponse.getEntity()));
      if (200 == i)
      {
        localMATMessage.setFlag(true);
        localMATMessage.setMsg(str1);
        if (!"".equals(localMATMessage))
        {
          JSONObject localJSONObject = new JSONObject(localMATMessage.msg);
          b localb = new b();
          localb.a("http://" + localJSONObject.getString("sip") + "/rec/se?_iwt_t=i&sv=2");
          localb.b("http://" + localJSONObject.getString("sip") + "/rec/cl?_iwt_t=i&sv=2");
          localb.c("http://" + localJSONObject.getString("cip") + "/cfg/appkey-");
          long l = Integer.parseInt(localJSONObject.getString("exp"), 10);
          localb.a(System.currentTimeMillis() / 1000L + l * 60L);
          localb.a(Integer.parseInt(localJSONObject.getString("itl"), 10));
          localb.b(Integer.parseInt(localJSONObject.getString("sm"), 10));
          localb.c(Integer.parseInt(localJSONObject.getString("lc"), 10));
          if (localJSONObject.isNull("pd"));
          for (String str2 = ""; ; str2 = localJSONObject.getString("pd"))
          {
            localb.d(str2);
            localEditor.putString("SendDataUrl", localb.a());
            localEditor.putString("SendClientUrl", localb.b());
            localEditor.putLong("ConfigExpireTime", localb.c());
            localEditor.putString("ConfigUrl", localb.d());
            localEditor.putInt("LimitInterval", localb.e());
            localEditor.putInt("sendMode", localb.f());
            localEditor.putInt("LimitCount", localb.g());
            localEditor.putString("Pd", localb.h());
            localEditor.commit();
            return localb;
          }
        }
      }
      else
      {
        String str3 = "配置获取失败,status: " + i;
        if (IRMonitor.c)
          Log.e("MAT_SESSION", str3);
        localEditor.putLong("ConfigExpireTime", System.currentTimeMillis() / 1000L);
      }
      return null;
    }
    catch (Exception localException)
    {
      while (true)
        localException.printStackTrace();
    }
  }

  @SuppressLint({"SimpleDateFormat"})
  public static String a()
  {
    try
    {
      Date localDate = new Date(System.currentTimeMillis());
      String str = new SimpleDateFormat("yyyyMMdd").format(localDate);
      return str;
    }
    catch (Exception localException)
    {
    }
    return "";
  }

  public static String a(Context paramContext, int paramInt)
  {
    try
    {
      ActivityManager localActivityManager = (ActivityManager)paramContext.getSystemService("activity");
      if (d(paramContext, "android.permission.GET_TASKS"))
        return ((ActivityManager.RunningTaskInfo)localActivityManager.getRunningTasks(3).get(paramInt)).topActivity.getClassName();
      if (IRMonitor.c)
        Log.e("lost permission", "android.permission.GET_TASKS");
      return "";
    }
    catch (SecurityException localSecurityException)
    {
    }
    return "";
  }

  public static String a(Map<String, String> paramMap)
  {
    JSONObject localJSONObject = new JSONObject();
    if (paramMap == null)
      return localJSONObject.toString();
    Iterator localIterator = paramMap.entrySet().iterator();
    while (true)
    {
      if (!localIterator.hasNext())
        return localJSONObject.toString();
      String str = (String)((Map.Entry)localIterator.next()).getKey();
      try
      {
        localJSONObject.put(str, paramMap.get(str));
      }
      catch (JSONException localJSONException)
      {
        localJSONException.printStackTrace();
      }
    }
  }

  public static boolean a(Context paramContext)
  {
    while (true)
    {
      int i;
      try
      {
        if (d(paramContext, "android.permission.ACCESS_WIFI_STATE"))
        {
          ConnectivityManager localConnectivityManager = (ConnectivityManager)paramContext.getSystemService("connectivity");
          if (localConnectivityManager != null)
          {
            NetworkInfo[] arrayOfNetworkInfo = localConnectivityManager.getAllNetworkInfo();
            if (arrayOfNetworkInfo != null)
            {
              i = 0;
              if (i < arrayOfNetworkInfo.length)
              {
                if ((!arrayOfNetworkInfo[i].getTypeName().equals("WIFI")) || (!arrayOfNetworkInfo[i].isConnected()))
                  break label108;
                return true;
              }
            }
          }
        }
        else if (IRMonitor.c)
        {
          Log.e("lost permission", "lost--->android.permission.ACCESS_WIFI_STATE");
        }
      }
      catch (Exception localException)
      {
        localException.printStackTrace();
        return false;
      }
      return false;
      label108: i++;
    }
  }

  public static List<String> b(Context paramContext, String paramString)
  {
    PackageManager localPackageManager = paramContext.getPackageManager();
    ArrayList localArrayList = new ArrayList();
    int i;
    int j;
    do
      try
      {
        ActivityInfo[] arrayOfActivityInfo = localPackageManager.getPackageInfo(paramString, 1).activities;
        i = arrayOfActivityInfo.length;
        j = 0;
        continue;
        localArrayList.add(arrayOfActivityInfo[j].name);
        j++;
      }
      catch (PackageManager.NameNotFoundException localNameNotFoundException)
      {
        localNameNotFoundException.printStackTrace();
        return localArrayList;
      }
    while (j < i);
    return localArrayList;
  }

  public static boolean b()
  {
    boolean bool1 = true;
    int i;
    int j;
    do
      try
      {
        String[] arrayOfString = { "/system/bin/", "/system/xbin/", "/data/local/xbin/", "/data/local/bin/", "/system/sd/xbin/" };
        i = arrayOfString.length;
        j = 0;
        continue;
        boolean bool2 = new File(arrayOfString[j] + "su").exists();
        if (bool2)
          break label108;
        j++;
      }
      catch (Exception localException)
      {
        localException.printStackTrace();
        break;
      }
    while (j < i);
    bool1 = false;
    label108: return bool1;
  }

  public static boolean b(Context paramContext)
  {
    if (d(paramContext, "android.permission.INTERNET"))
    {
      NetworkInfo localNetworkInfo = ((ConnectivityManager)paramContext.getSystemService("connectivity")).getActiveNetworkInfo();
      if ((localNetworkInfo != null) && (localNetworkInfo.isAvailable()))
        return true;
      if (IRMonitor.c)
        Log.e("error", "Network error");
      return false;
    }
    if (IRMonitor.c)
      Log.e(" lost  permission", "lost----> android.permission.INTERNET");
    return false;
  }

  private static String c()
  {
    try
    {
      String str = Build.VERSION.RELEASE;
      return str;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return "unKnown";
  }

  public static String c(Context paramContext)
  {
    try
    {
      if (d(paramContext, "android.permission.GET_TASKS"))
        return ((ActivityManager.RunningTaskInfo)((ActivityManager)paramContext.getSystemService("activity")).getRunningTasks(1).get(0)).topActivity.getPackageName();
      if (IRMonitor.c)
        Log.e("lost permission", "android.permission.GET_TASKS");
      return "";
    }
    catch (SecurityException localSecurityException)
    {
    }
    return "";
  }

  public static boolean c(Context paramContext, String paramString)
  {
    try
    {
      if (d(paramContext, "android.permission.GET_TASKS"))
      {
        String str = ((ActivityManager.RunningTaskInfo)((ActivityManager)paramContext.getSystemService("activity")).getRunningTasks(1).get(0)).topActivity.getPackageName();
        if ((str != null) || (!"".equals(str)))
        {
          if (!str.equals(paramString))
            break label92;
          return true;
        }
      }
      else if (IRMonitor.c)
      {
        Log.e("MAT_SESSION", "缺少：android.permission.GET_TASKS 权限");
      }
      return false;
    }
    catch (SecurityException localSecurityException)
    {
      while (true)
        localSecurityException.printStackTrace();
    }
    label92: return false;
  }

  private static String d()
  {
    return TimeZone.getDefault().getOffset(new Date().getTime()) / 1000;
  }

  public static String d(Context paramContext)
  {
    while (true)
    {
      String str;
      int i;
      try
      {
        if (d(paramContext, "android.permission.READ_PHONE_STATE"))
        {
          str = "";
          if (paramContext.getPackageManager().checkPermission("android.permission.READ_PHONE_STATE", paramContext.getPackageName()) == 0)
            break label99;
          i = 0;
          if (i != 0)
          {
            str = ((TelephonyManager)paramContext.getSystemService("phone")).getDeviceId();
            break label93;
            if (IRMonitor.c)
              Log.e("MAT_SESSION", "deviceId is null");
            return "";
          }
        }
        else
        {
          if (IRMonitor.c)
            Log.e("MAT_SESSION", "lost----->android.permission.READ_PHONE_STATE");
          return "";
        }
      }
      catch (Exception localException)
      {
        return "";
      }
      label93: if (str != null)
      {
        return str;
        label99: i = 1;
      }
    }
  }

  private static boolean d(Context paramContext, String paramString)
  {
    return paramContext.getPackageManager().checkPermission(paramString, paramContext.getPackageName()) == 0;
  }

  public static JSONObject e(Context paramContext)
  {
    JSONObject localJSONObject = new JSONObject();
    try
    {
      WindowManager localWindowManager = (WindowManager)paramContext.getSystemService("window");
      DisplayMetrics localDisplayMetrics = new DisplayMetrics();
      localWindowManager.getDefaultDisplay().getMetrics(localDisplayMetrics);
      localJSONObject.put("app_key", IRMonitor.a);
      localJSONObject.put("uid", " " + g(paramContext));
      localJSONObject.put("uidtype", Build.MANUFACTURER + " " + Build.MODEL);
      localJSONObject.put("ip", "");
      localJSONObject.put("imei", IRMonitor.b);
      localJSONObject.put("appid", c(paramContext));
      localJSONObject.put("appver", k(paramContext));
      localJSONObject.put("mac_hash", "");
      localJSONObject.put("network", j(paramContext));
      String str = l(paramContext);
      if (str.equals(""))
      {
        localJSONObject.put("carrier", "--");
        localJSONObject.put("country", "--");
      }
      while (true)
      {
        localJSONObject.put("city", localDisplayMetrics.heightPixels + "*" + localDisplayMetrics.widthPixels);
        localJSONObject.put("timezone", d());
        localJSONObject.put("os_name", "Android");
        localJSONObject.put("os_ver", c());
        localJSONObject.put("sdk_ver", "2.3.2");
        localJSONObject.put("channel", h(paramContext));
        localJSONObject.put("col1", Settings.Secure.getString(paramContext.getContentResolver(), "android_id"));
        localJSONObject.put("col2", ((TelephonyManager)paramContext.getSystemService("phone")).getDeviceId());
        localJSONObject.put("col3", ((WifiManager)paramContext.getApplicationContext().getSystemService("wifi")).getConnectionInfo().getMacAddress());
        SharedPreferences localSharedPreferences = paramContext.getSharedPreferences("MATSharedPreferences", 0);
        a = localSharedPreferences;
        b = localSharedPreferences.edit();
        localJSONObject.put("ts", a.getLong("ts", System.currentTimeMillis() / 1000L));
        b.putLong("ts", System.currentTimeMillis() / 1000L).commit();
        localJSONObject.put("dd", "3" + a().substring(1));
        localJSONObject.put("lac_cid", "0_0");
        return localJSONObject;
        localJSONObject.put("carrier", str.substring(3));
        localJSONObject.put("country", str.substring(0, 3));
      }
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return localJSONObject;
  }

  public static JSONObject f(Context paramContext)
  {
    JSONObject localJSONObject = new JSONObject();
    try
    {
      WindowManager localWindowManager = (WindowManager)paramContext.getSystemService("window");
      DisplayMetrics localDisplayMetrics = new DisplayMetrics();
      localWindowManager.getDefaultDisplay().getMetrics(localDisplayMetrics);
      localJSONObject.put("app_key", IRMonitor.a);
      localJSONObject.put("uid", " " + g(paramContext));
      localJSONObject.put("uidtype", Build.MANUFACTURER + " " + Build.MODEL);
      localJSONObject.put("ip", "");
      localJSONObject.put("imei", IRMonitor.b);
      localJSONObject.put("appid", c(paramContext));
      localJSONObject.put("appver", k(paramContext));
      localJSONObject.put("mac_hash", "");
      localJSONObject.put("network", j(paramContext));
      String str = l(paramContext);
      if (str.equals(""))
      {
        localJSONObject.put("carrier", "--");
        localJSONObject.put("country", "--");
      }
      while (true)
      {
        localJSONObject.put("city", localDisplayMetrics.heightPixels + "*" + localDisplayMetrics.widthPixels);
        localJSONObject.put("timezone", d());
        localJSONObject.put("os_name", "Android");
        localJSONObject.put("os_ver", c());
        localJSONObject.put("sdk_ver", "2.3.2");
        localJSONObject.put("channel", h(paramContext));
        localJSONObject.put("col1", Settings.Secure.getString(paramContext.getContentResolver(), "android_id"));
        localJSONObject.put("col2", ((TelephonyManager)paramContext.getSystemService("phone")).getDeviceId());
        localJSONObject.put("col3", ((WifiManager)paramContext.getApplicationContext().getSystemService("wifi")).getConnectionInfo().getMacAddress());
        SharedPreferences localSharedPreferences = paramContext.getSharedPreferences("MATSharedPreferences", 0);
        a = localSharedPreferences;
        b = localSharedPreferences.edit();
        localJSONObject.put("ts", a.getLong("ts", System.currentTimeMillis() / 1000L));
        b.putLong("ts", System.currentTimeMillis() / 1000L).commit();
        localJSONObject.put("dd", "3" + a().substring(1));
        g localg = i(paramContext);
        localJSONObject.put("lac_cid", localg.a + "_" + localg.b);
        return localJSONObject;
        localJSONObject.put("carrier", str.substring(3));
        localJSONObject.put("country", str.substring(0, 3));
      }
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return localJSONObject;
  }

  public static String g(Context paramContext)
  {
    try
    {
      String str1 = ((TelephonyManager)paramContext.getSystemService("phone")).getDeviceId();
      String str2 = "35" + Build.BOARD.length() % 10 + Build.BRAND.length() % 10 + Build.CPU_ABI.length() % 10 + Build.DEVICE.length() % 10 + Build.DISPLAY.length() % 10 + Build.HOST.length() % 10 + Build.ID.length() % 10 + Build.MANUFACTURER.length() % 10 + Build.MODEL.length() % 10 + Build.PRODUCT.length() % 10 + Build.TAGS.length() % 10 + Build.TYPE.length() % 10 + Build.USER.length() % 10;
      String str3 = Settings.Secure.getString(paramContext.getContentResolver(), "android_id");
      String str4 = ((WifiManager)paramContext.getApplicationContext().getSystemService("wifi")).getConnectionInfo().getMacAddress();
      String str5 = str1 + str2 + str3 + str4;
      try
      {
        MessageDigest localMessageDigest2 = MessageDigest.getInstance("MD5");
        localMessageDigest1 = localMessageDigest2;
        localMessageDigest1.update(str5.getBytes(), 0, str5.length());
        arrayOfByte = localMessageDigest1.digest();
        localObject = new String();
        i = 0;
        if (i >= arrayOfByte.length)
          return ((String)localObject).toUpperCase();
      }
      catch (NoSuchAlgorithmException localNoSuchAlgorithmException)
      {
        while (true)
        {
          byte[] arrayOfByte;
          int i;
          localNoSuchAlgorithmException.printStackTrace();
          MessageDigest localMessageDigest1 = null;
          continue;
          int j = 0xFF & arrayOfByte[i];
          if (j <= 15)
            localObject = localObject + "0";
          String str6 = localObject + Integer.toHexString(j);
          Object localObject = str6;
          i++;
        }
      }
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return "";
  }

  private static String h(Context paramContext)
  {
    try
    {
      ApplicationInfo localApplicationInfo = paramContext.getPackageManager().getApplicationInfo(paramContext.getPackageName(), 128);
      if (localApplicationInfo != null)
      {
        String str = localApplicationInfo.metaData.getString("ire_channel");
        if (str != null)
          return str.toString();
        if (IRMonitor.c)
          Log.e("MAT", "Could not read ire_channel meta-data from AndroidManifest.xml.");
      }
      return "";
    }
    catch (Exception localException)
    {
      while (true)
        if (IRMonitor.c)
        {
          Log.e("MAT", "Could not read ire_channel meta-data from AndroidManifest.xml.");
          localException.printStackTrace();
        }
    }
  }

  private static g i(Context paramContext)
  {
    g localg = new g((byte)0);
    try
    {
      if (d(paramContext, "android.permission.ACCESS_FINE_LOCATION"))
      {
        TelephonyManager localTelephonyManager = (TelephonyManager)paramContext.getSystemService("phone");
        CellLocation localCellLocation = localTelephonyManager.getCellLocation();
        if (localCellLocation == null)
          return localg;
        if ((localCellLocation instanceof CdmaCellLocation))
        {
          CdmaCellLocation localCdmaCellLocation = (CdmaCellLocation)localCellLocation;
          j = localCdmaCellLocation.getNetworkId();
          i = localCdmaCellLocation.getBaseStationId() / 16;
          str = localTelephonyManager.getNetworkOperator();
          if ((str != null) && (str.length() >= 3))
            break label180;
        }
        while (true)
        {
          if ((str != null) && (str.length() >= 3))
            break label202;
          localg.a = j;
          localg.b = i;
          if (str.equals(""))
            break label224;
          Integer.parseInt(str);
          break label224;
          if (!(localCellLocation instanceof GsmCellLocation))
            break label215;
          GsmCellLocation localGsmCellLocation = (GsmCellLocation)localCellLocation;
          int k = 0xFFFF & localGsmCellLocation.getCid();
          j = localGsmCellLocation.getLac();
          i = k;
          break;
          label180: Integer.parseInt(str.substring(0, 3));
        }
      }
    }
    catch (Exception localException)
    {
      while (true)
      {
        String str;
        localException.printStackTrace();
        break;
        label202: Integer.parseInt(str.substring(3));
        continue;
        label215: int i = 0;
        int j = 0;
      }
    }
    label224: return localg;
  }

  private static String j(Context paramContext)
  {
    try
    {
      if (d(paramContext, "android.permission.ACCESS_NETWORK_STATE"))
      {
        ConnectivityManager localConnectivityManager = (ConnectivityManager)paramContext.getSystemService("connectivity");
        NetworkInfo.State localState1 = localConnectivityManager.getNetworkInfo(0).getState();
        NetworkInfo.State localState2 = localConnectivityManager.getNetworkInfo(1).getState();
        if ((localState1 == NetworkInfo.State.CONNECTED) || (localState1 == NetworkInfo.State.CONNECTING))
          break label86;
        if ((localState2 == NetworkInfo.State.CONNECTED) || (localState2 == NetworkInfo.State.CONNECTING))
          return "WIFI";
      }
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return "UNKNOWN";
    label86: return "3G";
  }

  // ERROR //
  private static String k(Context paramContext)
  {
    // Byte code:
    //   0: aload_0
    //   1: invokevirtual 471	android/content/Context:getPackageManager	()Landroid/content/pm/PackageManager;
    //   4: aload_0
    //   5: invokevirtual 564	android/content/Context:getPackageName	()Ljava/lang/String;
    //   8: iconst_0
    //   9: invokevirtual 480	android/content/pm/PackageManager:getPackageInfo	(Ljava/lang/String;I)Landroid/content/pm/PackageInfo;
    //   12: getfield 893	android/content/pm/PackageInfo:versionName	Ljava/lang/String;
    //   15: astore_2
    //   16: aload_2
    //   17: ifnull +14 -> 31
    //   20: aload_2
    //   21: invokevirtual 19	java/lang/String:length	()I
    //   24: istore 5
    //   26: iload 5
    //   28: ifgt +6 -> 34
    //   31: ldc 218
    //   33: astore_2
    //   34: aload_2
    //   35: areturn
    //   36: astore_1
    //   37: ldc 218
    //   39: astore_2
    //   40: aload_1
    //   41: astore_3
    //   42: getstatic 127	cn/com/iresearch/mapptracker/fm/IRMonitor:c	Z
    //   45: ifeq -11 -> 34
    //   48: ldc_w 833
    //   51: ldc_w 895
    //   54: aload_3
    //   55: invokestatic 898	android/util/Log:e	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I
    //   58: pop
    //   59: aload_2
    //   60: areturn
    //   61: astore_3
    //   62: goto -20 -> 42
    //
    // Exception table:
    //   from	to	target	type
    //   0	16	36	java/lang/Exception
    //   20	26	61	java/lang/Exception
  }

  private static String l(Context paramContext)
  {
    try
    {
      TelephonyManager localTelephonyManager = (TelephonyManager)paramContext.getSystemService("phone");
      if (5 == localTelephonyManager.getSimState())
      {
        String str = localTelephonyManager.getSimOperator();
        return str;
      }
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return "";
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     cn.com.iresearch.mapptracker.fm.util.f
 * JD-Core Version:    0.6.2
 */