package com.umeng.analytics.social;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.provider.Settings.Secure;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

public abstract class f
{
  private static Map<String, String> a;

  protected static String a(Context paramContext)
  {
    String str1 = e.d;
    if (!TextUtils.isEmpty(str1))
    {
      b.b("MobclickAgent", "use usefully appkey from constant field.");
      return str1;
    }
    try
    {
      ApplicationInfo localApplicationInfo = paramContext.getPackageManager().getApplicationInfo(paramContext.getPackageName(), 128);
      if (localApplicationInfo != null)
      {
        String str2 = localApplicationInfo.metaData.getString("UMENG_APPKEY");
        if (str2 != null)
          return str2.trim();
        b.b("MobclickAgent", "Could not read UMENG_APPKEY meta-data from AndroidManifest.xml.");
      }
      return null;
    }
    catch (Exception localException)
    {
      while (true)
        b.b("MobclickAgent", "Could not read UMENG_APPKEY meta-data from AndroidManifest.xml.", localException);
    }
  }

  private static String a(List<NameValuePair> paramList)
  {
    try
    {
      ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream();
      new UrlEncodedFormEntity(paramList, "UTF-8").writeTo(localByteArrayOutputStream);
      String str = localByteArrayOutputStream.toString();
      return str;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return null;
  }

  private static List<NameValuePair> a(UMPlatformData[] paramArrayOfUMPlatformData)
  {
    StringBuilder localStringBuilder1 = new StringBuilder();
    StringBuilder localStringBuilder2 = new StringBuilder();
    StringBuilder localStringBuilder3 = new StringBuilder();
    int i = paramArrayOfUMPlatformData.length;
    for (int j = 0; j < i; j++)
    {
      UMPlatformData localUMPlatformData = paramArrayOfUMPlatformData[j];
      localStringBuilder1.append(localUMPlatformData.getMeida().toString());
      localStringBuilder1.append(',');
      localStringBuilder2.append(localUMPlatformData.getUsid());
      localStringBuilder2.append(',');
      localStringBuilder3.append(localUMPlatformData.getWeiboId());
      localStringBuilder3.append(',');
    }
    if (paramArrayOfUMPlatformData.length > 0)
    {
      localStringBuilder1.deleteCharAt(-1 + localStringBuilder1.length());
      localStringBuilder2.deleteCharAt(-1 + localStringBuilder2.length());
      localStringBuilder3.deleteCharAt(-1 + localStringBuilder3.length());
    }
    ArrayList localArrayList = new ArrayList();
    localArrayList.add(new BasicNameValuePair("platform", localStringBuilder1.toString()));
    localArrayList.add(new BasicNameValuePair("usid", localStringBuilder2.toString()));
    if (localStringBuilder3.length() > 0)
      localArrayList.add(new BasicNameValuePair("weiboid", localStringBuilder3.toString()));
    return localArrayList;
  }

  private static boolean a(Context paramContext, String paramString)
  {
    return paramContext.getPackageManager().checkPermission(paramString, paramContext.getPackageName()) == 0;
  }

  protected static String[] a(Context paramContext, String paramString, UMPlatformData[] paramArrayOfUMPlatformData)
    throws a
  {
    if ((paramArrayOfUMPlatformData == null) || (paramArrayOfUMPlatformData.length == 0))
      throw new a("platform data is null");
    String str1 = a(paramContext);
    if (TextUtils.isEmpty(str1))
      throw new a("can`t get appkey.");
    ArrayList localArrayList = new ArrayList();
    String str2 = "http://log.umsns.com/share/api/" + str1 + "/";
    if ((a == null) || (a.isEmpty()))
      a = c(paramContext);
    if ((a != null) && (!a.isEmpty()))
    {
      Iterator localIterator = a.entrySet().iterator();
      while (localIterator.hasNext())
      {
        Map.Entry localEntry = (Map.Entry)localIterator.next();
        localArrayList.add(new BasicNameValuePair((String)localEntry.getKey(), (String)localEntry.getValue()));
      }
    }
    localArrayList.add(new BasicNameValuePair("date", String.valueOf(System.currentTimeMillis())));
    localArrayList.add(new BasicNameValuePair("channel", e.e));
    if (!TextUtils.isEmpty(paramString))
      localArrayList.add(new BasicNameValuePair("topic", paramString));
    localArrayList.addAll(a(paramArrayOfUMPlatformData));
    String str3 = b(paramArrayOfUMPlatformData);
    if (str3 == null)
      str3 = "null";
    String str4 = str2 + "?" + a(localArrayList);
    b.c("MobclickAgent", "URL:" + str4);
    b.c("MobclickAgent", "BODY:" + str3);
    return new String[] { str4, str3 };
  }

  private static String b(UMPlatformData[] paramArrayOfUMPlatformData)
  {
    JSONObject localJSONObject1 = new JSONObject();
    int i = paramArrayOfUMPlatformData.length;
    label132: label175: for (int j = 0; ; j++)
      if (j < i)
      {
        UMPlatformData localUMPlatformData = paramArrayOfUMPlatformData[j];
        UMPlatformData.GENDER localGENDER = localUMPlatformData.getGender();
        String str1 = localUMPlatformData.getName();
        if (localGENDER == null);
        try
        {
          if (TextUtils.isEmpty(str1))
            continue;
          JSONObject localJSONObject2 = new JSONObject();
          String str2;
          if (localGENDER == null)
          {
            str2 = "";
            localJSONObject2.put("gender", str2);
            if (str1 != null)
              break label132;
          }
          String str3;
          for (Object localObject = ""; ; localObject = str3)
          {
            localJSONObject2.put("name", localObject);
            localJSONObject1.put(localUMPlatformData.getMeida().toString(), localJSONObject2);
            break label175;
            str2 = String.valueOf(localGENDER.value);
            break;
            str3 = String.valueOf(str1);
          }
        }
        catch (Exception localException)
        {
          throw new a("build body exception", localException);
        }
      }
      else
      {
        if (localJSONObject1.length() == 0)
          return null;
        return localJSONObject1.toString();
      }
  }

  public static Map<String, String> b(Context paramContext)
  {
    HashMap localHashMap = new HashMap();
    TelephonyManager localTelephonyManager = (TelephonyManager)paramContext.getSystemService("phone");
    if (localTelephonyManager == null)
      b.e("MobclickAgent", "No IMEI.");
    try
    {
      if (a(paramContext, "android.permission.READ_PHONE_STATE"))
      {
        String str4 = localTelephonyManager.getDeviceId();
        str1 = str4;
        String str2 = d(paramContext);
        String str3 = Settings.Secure.getString(paramContext.getContentResolver(), "android_id");
        if (!TextUtils.isEmpty(str2))
          localHashMap.put("mac", str2);
        if (!TextUtils.isEmpty(str1))
          localHashMap.put("imei", str1);
        if (!TextUtils.isEmpty(str3))
          localHashMap.put("android_id", str3);
        return localHashMap;
      }
    }
    catch (Exception localException)
    {
      while (true)
      {
        b.e("MobclickAgent", "No IMEI.", localException);
        String str1 = null;
      }
    }
  }

  private static Map<String, String> c(Context paramContext)
    throws a
  {
    HashMap localHashMap = new HashMap();
    Map localMap = b(paramContext);
    if ((localMap != null) && (!localMap.isEmpty()))
    {
      StringBuilder localStringBuilder1 = new StringBuilder();
      StringBuilder localStringBuilder2 = new StringBuilder();
      Iterator localIterator = localMap.entrySet().iterator();
      while (localIterator.hasNext())
      {
        Map.Entry localEntry = (Map.Entry)localIterator.next();
        if (!TextUtils.isEmpty((CharSequence)localEntry.getValue()))
        {
          localStringBuilder2.append((String)localEntry.getKey()).append(",");
          localStringBuilder1.append((String)localEntry.getValue()).append(",");
        }
      }
      if (localStringBuilder1.length() > 0)
      {
        localStringBuilder1.deleteCharAt(-1 + localStringBuilder1.length());
        localHashMap.put("deviceid", localStringBuilder1.toString());
      }
      if (localStringBuilder2.length() > 0)
      {
        localStringBuilder2.deleteCharAt(-1 + localStringBuilder2.length());
        localHashMap.put("idtype", localStringBuilder2.toString());
      }
      return localHashMap;
    }
    throw new a("can`t get device id.");
  }

  private static String d(Context paramContext)
  {
    try
    {
      WifiManager localWifiManager = (WifiManager)paramContext.getSystemService("wifi");
      if (a(paramContext, "android.permission.ACCESS_WIFI_STATE"))
        return localWifiManager.getConnectionInfo().getMacAddress();
      b.e("MobclickAgent", "Could not get mac address.[no permission android.permission.ACCESS_WIFI_STATE");
      return "";
    }
    catch (Exception localException)
    {
      while (true)
        b.e("MobclickAgent", "Could not get mac address." + localException.toString());
    }
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.umeng.analytics.social.f
 * JD-Core Version:    0.6.2
 */