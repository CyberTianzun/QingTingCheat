package com.taobao.munion.base.caches;

import android.net.Uri;
import android.net.Uri.Builder;
import android.text.TextUtils;
import java.util.HashMap;
import java.util.Map;

public class u
{
  public static final String a = "url";
  public static final String b = "response-code";
  public static final String c = "content-type";
  public static final String d = "content-length";
  public static final String e = "location";
  public static final String f = "last-modified";
  public static final String g = "expires";
  public static final String h = "date";
  public static final String i = "set-cookie";
  public static final String j = "cache-control";
  public static final String k = "etag";
  public static final String l = "If-Modified-Since";
  public static final String m = "If-None-Match";
  public static final String n = "Mozilla/5.0 (Linux; U;Android munion-h5-sdk httpclient;)";
  private static Map<String, String> o = new HashMap();

  static
  {
    for (o localo : o.values())
      o.put(localo.a(), localo.b());
  }

  public static String a(String paramString1, String paramString2)
  {
    if ((paramString1 == null) || (paramString2 == null))
      return null;
    return Uri.parse(paramString1).getQueryParameter(paramString2);
  }

  public static String a(String paramString1, String paramString2, String paramString3)
  {
    if ((paramString1 == null) || (TextUtils.isEmpty(paramString2)));
    Uri localUri;
    do
    {
      return paramString1;
      localUri = Uri.parse(paramString1);
    }
    while (localUri.getQueryParameter(paramString2) != null);
    Uri.Builder localBuilder = localUri.buildUpon();
    localBuilder.appendQueryParameter(paramString2, paramString3);
    return localBuilder.toString();
  }

  public static boolean a(String paramString)
  {
    String str = e(paramString);
    return (o.a.a().equals(str)) || (o.b.a().equals(str));
  }

  public static boolean b(String paramString)
  {
    return f(paramString).startsWith("image");
  }

  public static boolean c(String paramString)
  {
    if (TextUtils.isEmpty(paramString));
    String str;
    do
    {
      return false;
      str = Uri.parse(paramString).getPath();
    }
    while ((str == null) || ((!str.endsWith("." + o.h.a())) && (!str.endsWith("." + o.i.a())) && (!TextUtils.isEmpty(str)) && (!"/".equals(str))));
    return true;
  }

  public static Map<String, String> d(String paramString)
  {
    if (TextUtils.isEmpty(paramString))
      return null;
    HashMap localHashMap = new HashMap();
    int i1 = paramString.indexOf("?");
    if (i1 != -1)
    {
      String str = paramString.substring(i1 + 1);
      if (str.contains("#"))
        str = str.substring(0, str.indexOf("#"));
      String[] arrayOfString1 = str.split("&");
      int i2 = arrayOfString1.length;
      int i3 = 0;
      if (i3 < i2)
      {
        String[] arrayOfString2 = arrayOfString1[i3].split("=");
        if (arrayOfString2.length < 2)
          localHashMap.put(arrayOfString2[0], "");
        while (true)
        {
          i3++;
          break;
          localHashMap.put(arrayOfString2[0], arrayOfString2[1]);
        }
      }
    }
    return localHashMap;
  }

  public static String e(String paramString)
  {
    if (TextUtils.isEmpty(paramString))
      return "";
    String str = Uri.parse(paramString).getPath();
    if (str != null)
    {
      int i1 = str.lastIndexOf(".");
      if (i1 != -1)
        return str.substring(i1 + 1);
    }
    return "";
  }

  public static String f(String paramString)
  {
    String str1 = e(paramString);
    String str2 = (String)o.get(str1);
    if (str2 == null)
      str2 = "";
    return str2;
  }

  public static boolean g(String paramString)
  {
    boolean bool1 = TextUtils.isEmpty(paramString);
    boolean bool2 = false;
    if (!bool1)
      if (!paramString.toLowerCase().startsWith("http://"))
      {
        boolean bool3 = paramString.toLowerCase().startsWith("https://");
        bool2 = false;
        if (!bool3);
      }
      else
      {
        bool2 = true;
      }
    return bool2;
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.taobao.munion.base.caches.u
 * JD-Core Version:    0.6.2
 */