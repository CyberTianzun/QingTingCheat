package com.sina.weibo.sdk.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.Signature;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.text.TextUtils;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.URLDecoder;
import java.util.Locale;
import java.util.UUID;

public class Utility
{
  private static final String DEFAULT_CHARSET = "UTF-8";

  public static Bundle decodeUrl(String paramString)
  {
    int i = 0;
    Bundle localBundle = new Bundle();
    String[] arrayOfString1;
    int j;
    if (paramString != null)
    {
      arrayOfString1 = paramString.split("&");
      j = arrayOfString1.length;
    }
    while (true)
    {
      if (i >= j)
        return localBundle;
      String[] arrayOfString2 = arrayOfString1[i].split("=");
      try
      {
        localBundle.putString(URLDecoder.decode(arrayOfString2[0], "UTF-8"), URLDecoder.decode(arrayOfString2[1], "UTF-8"));
        i++;
      }
      catch (UnsupportedEncodingException localUnsupportedEncodingException)
      {
        while (true)
          localUnsupportedEncodingException.printStackTrace();
      }
    }
  }

  public static String generateGUID()
  {
    return UUID.randomUUID().toString().replace("-", "");
  }

  public static String generateUA(Context paramContext)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append(Build.MANUFACTURER).append("-").append(Build.MODEL);
    localStringBuilder.append("_");
    localStringBuilder.append(Build.VERSION.RELEASE);
    localStringBuilder.append("_");
    localStringBuilder.append("weibosdk");
    localStringBuilder.append("_");
    localStringBuilder.append("0030105000");
    localStringBuilder.append("_android");
    return localStringBuilder.toString();
  }

  public static String getAid(Context paramContext, String paramString)
  {
    AidTask localAidTask = AidTask.getInstance(paramContext);
    String str = localAidTask.loadAidFromCache();
    if (!TextUtils.isEmpty(str))
      return str;
    localAidTask.aidTaskInit(paramString);
    return "";
  }

  public static String getSign(Context paramContext, String paramString)
  {
    while (true)
    {
      PackageInfo localPackageInfo;
      int i;
      try
      {
        localPackageInfo = paramContext.getPackageManager().getPackageInfo(paramString, 64);
        i = 0;
        if (i >= localPackageInfo.signatures.length)
          return null;
      }
      catch (PackageManager.NameNotFoundException localNameNotFoundException)
      {
        return null;
      }
      byte[] arrayOfByte = localPackageInfo.signatures[i].toByteArray();
      if (arrayOfByte != null)
        return MD5.hexdigest(arrayOfByte);
      i++;
    }
  }

  public static boolean isChineseLocale(Context paramContext)
  {
    try
    {
      Locale localLocale = paramContext.getResources().getConfiguration().locale;
      if ((!Locale.CHINA.equals(localLocale)) && (!Locale.CHINESE.equals(localLocale)) && (!Locale.SIMPLIFIED_CHINESE.equals(localLocale)))
      {
        boolean bool = Locale.TAIWAN.equals(localLocale);
        if (!bool);
      }
      else
      {
        return true;
      }
    }
    catch (Exception localException)
    {
      return true;
    }
    return false;
  }

  public static Bundle parseUri(String paramString)
  {
    try
    {
      Bundle localBundle = decodeUrl(new URI(paramString).getQuery());
      return localBundle;
    }
    catch (Exception localException)
    {
    }
    return new Bundle();
  }

  public static Bundle parseUrl(String paramString)
  {
    try
    {
      URL localURL = new URL(paramString);
      Bundle localBundle = decodeUrl(localURL.getQuery());
      localBundle.putAll(decodeUrl(localURL.getRef()));
      return localBundle;
    }
    catch (MalformedURLException localMalformedURLException)
    {
    }
    return new Bundle();
  }

  public static String safeString(String paramString)
  {
    if (TextUtils.isEmpty(paramString))
      paramString = "";
    return paramString;
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.sina.weibo.sdk.utils.Utility
 * JD-Core Version:    0.6.2
 */