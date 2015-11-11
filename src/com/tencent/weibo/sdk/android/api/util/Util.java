package com.tencent.weibo.sdk.android.api.util;

import android.app.Activity;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.NetworkInfo.State;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.util.Log;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Properties;

public class Util
{
  public static void clearSharePersistent(Context paramContext)
  {
    SharePersistent localSharePersistent = SharePersistent.getInstance();
    localSharePersistent.clear(paramContext, "ACCESS_TOKEN");
    localSharePersistent.clear(paramContext, "EXPIRES_IN");
    localSharePersistent.clear(paramContext, "OPEN_ID");
    localSharePersistent.clear(paramContext, "OPEN_KEY");
    localSharePersistent.clear(paramContext, "REFRESH_TOKEN");
    localSharePersistent.clear(paramContext, "NAME");
    localSharePersistent.clear(paramContext, "NICK");
    localSharePersistent.clear(paramContext, "CLIENT_ID");
  }

  public static void clearSharePersistent(Context paramContext, String paramString)
  {
    SharePersistent.getInstance().clear(paramContext, paramString);
  }

  public static Properties getConfig()
  {
    Properties localProperties = new Properties();
    localProperties.setProperty("APP_KEY", "801439222");
    localProperties.setProperty("APP_KEY_SEC", "9a0504d18beda2583aa2ddfb2046d4f9");
    localProperties.setProperty("REDIRECT_URI", "http://tencent.callback.qingting.fm");
    return localProperties;
  }

  public static String getLocalIPAddress(Context paramContext)
  {
    int i = ((WifiManager)paramContext.getSystemService("wifi")).getConnectionInfo().getIpAddress();
    return (i & 0xFF) + "." + (0xFF & i >> 8) + "." + (0xFF & i >> 16) + "." + (0xFF & i >> 24);
  }

  public static Location getLocation(Context paramContext)
  {
    try
    {
      LocationManager localLocationManager = (LocationManager)paramContext.getSystemService("location");
      Criteria localCriteria = new Criteria();
      localCriteria.setAccuracy(2);
      localCriteria.setAltitudeRequired(false);
      localCriteria.setBearingRequired(false);
      localCriteria.setCostAllowed(true);
      localCriteria.setPowerRequirement(3);
      localCriteria.setSpeedRequired(false);
      String str = localLocationManager.getBestProvider(localCriteria, true);
      Log.d("Location", "currentProvider: " + str);
      Location localLocation = localLocationManager.getLastKnownLocation(str);
      return localLocation;
    }
    catch (Exception localException)
    {
    }
    return null;
  }

  public static String getSharePersistent(Context paramContext, String paramString)
  {
    return SharePersistent.getInstance().get(paramContext, paramString);
  }

  public static Long getSharePersistentLong(Context paramContext, String paramString)
  {
    return Long.valueOf(SharePersistent.getInstance().getLong(paramContext, paramString));
  }

  private String intToIp(int paramInt)
  {
    return (paramInt & 0xFF) + "." + (0xFF & paramInt >> 8) + "." + (0xFF & paramInt >> 16) + "." + (0xFF & paramInt >> 24);
  }

  public static boolean isNetworkAvailable(Activity paramActivity)
  {
    ConnectivityManager localConnectivityManager = (ConnectivityManager)paramActivity.getApplicationContext().getSystemService("connectivity");
    if (localConnectivityManager == null);
    while (true)
    {
      return false;
      NetworkInfo[] arrayOfNetworkInfo = localConnectivityManager.getAllNetworkInfo();
      if (arrayOfNetworkInfo != null)
        for (int i = 0; i < arrayOfNetworkInfo.length; i++)
          if (arrayOfNetworkInfo[i].getState() == NetworkInfo.State.CONNECTED)
            return true;
    }
  }

  public static Drawable loadImageFromUrl(String paramString)
  {
    try
    {
      URLConnection localURLConnection = new URL(paramString).openConnection();
      localURLConnection.connect();
      InputStream localInputStream = localURLConnection.getInputStream();
      BitmapFactory.Options localOptions = new BitmapFactory.Options();
      localOptions.inJustDecodeBounds = false;
      localOptions.inSampleSize = 2;
      BitmapDrawable localBitmapDrawable = new BitmapDrawable(BitmapFactory.decodeStream(localInputStream, null, localOptions));
      return localBitmapDrawable;
    }
    catch (Exception localException)
    {
    }
    return null;
  }

  public static void saveSharePersistent(Context paramContext, String paramString, long paramLong)
  {
    SharePersistent.getInstance().put(paramContext, paramString, paramLong);
  }

  public static void saveSharePersistent(Context paramContext, String paramString1, String paramString2)
  {
    SharePersistent.getInstance().put(paramContext, paramString1, paramString2);
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.tencent.weibo.sdk.android.api.util.Util
 * JD-Core Version:    0.6.2
 */