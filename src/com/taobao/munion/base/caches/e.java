package com.taobao.munion.base.caches;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.text.TextUtils;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class e
{
  private static SimpleDateFormat a = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss 'GMT'", Locale.ENGLISH);

  public static int a(String paramString)
  {
    if (TextUtils.isEmpty(paramString))
      return 0;
    try
    {
      int i = Integer.parseInt(paramString);
      return i;
    }
    catch (NumberFormatException localNumberFormatException)
    {
    }
    return 0;
  }

  public static int a(String paramString1, String paramString2)
  {
    if (paramString1 == null)
      paramString1 = "0";
    if (paramString2 == null)
      paramString2 = "0";
    String[] arrayOfString1 = paramString1.split("\\.");
    String[] arrayOfString2 = paramString2.split("\\.");
    int i = arrayOfString1.length;
    int j = arrayOfString2.length;
    int k;
    if (i > j)
      k = i;
    for (int m = 0; ; m++)
    {
      if (m >= k)
        break label153;
      if ((m < i) && (m < j))
      {
        int i1 = a(arrayOfString1[m]);
        int i2 = a(arrayOfString2[m]);
        if (i1 == i2)
          continue;
        return i1 - i2;
        k = j;
        break;
      }
      if (i > j);
      for (int n = a(arrayOfString1[m]); n != 0; n = -1 * a(arrayOfString2[m]))
        return n;
    }
    label153: return 0;
  }

  public static String a(long paramLong)
  {
    return a.format(new Date(paramLong));
  }

  public static boolean a(Context paramContext, String paramString)
  {
    if ((paramContext == null) || (TextUtils.isEmpty(paramString)));
    while (true)
    {
      return false;
      try
      {
        PackageManager localPackageManager = paramContext.getPackageManager();
        if (localPackageManager != null)
        {
          PackageInfo localPackageInfo = localPackageManager.getPackageInfo(paramString, 0);
          if (localPackageInfo != null)
            return true;
        }
      }
      catch (PackageManager.NameNotFoundException localNameNotFoundException)
      {
        localNameNotFoundException.printStackTrace();
      }
    }
    return false;
  }

  public static String b(String paramString)
  {
    if (!TextUtils.isEmpty(paramString))
    {
      int i = paramString.indexOf("charset");
      if ((i != -1) && (paramString.indexOf("=", i) != -1))
      {
        String str = paramString.substring(1 + paramString.indexOf("=", i));
        int j = str.indexOf(";");
        if (j != -1)
          str = str.substring(0, j).trim();
        return str.trim();
      }
    }
    return "";
  }

  public static String c(String paramString)
  {
    if (!TextUtils.isEmpty(paramString))
    {
      int i = paramString.indexOf(";");
      if (i == -1)
        return paramString.trim();
      return paramString.substring(0, i).trim();
    }
    return "";
  }

  public static long d(String paramString)
  {
    if ((!TextUtils.isEmpty(paramString)) && (paramString.indexOf("max-age=") != -1))
    {
      StringBuilder localStringBuilder = new StringBuilder();
      String str = paramString.substring(8);
      for (int i = 0; (i < str.length()) && (Character.isDigit(str.charAt(i))); i++)
        localStringBuilder.append(str.charAt(i));
      try
      {
        long l = Long.parseLong(localStringBuilder.toString());
        return l * 1000L;
      }
      catch (NumberFormatException localNumberFormatException)
      {
        localNumberFormatException.printStackTrace();
      }
    }
    return 0L;
  }

  public static long e(String paramString)
  {
    if (!TextUtils.isEmpty(paramString))
      try
      {
        long l = a.parse(paramString.trim()).getTime();
        return l;
      }
      catch (ParseException localParseException)
      {
        localParseException.printStackTrace();
      }
    return 0L;
  }

  public static boolean f(String paramString)
  {
    return (!TextUtils.isEmpty(paramString)) && (paramString.toLowerCase().startsWith("image"));
  }

  public static boolean g(String paramString)
  {
    return (!TextUtils.isEmpty(paramString)) && (paramString.equalsIgnoreCase("text/html"));
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.taobao.munion.base.caches.e
 * JD-Core Version:    0.6.2
 */