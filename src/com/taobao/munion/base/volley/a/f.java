package com.taobao.munion.base.volley.a;

import com.taobao.munion.base.volley.b.a;
import com.taobao.munion.base.volley.i;
import java.util.Date;
import java.util.Map;
import org.apache.http.impl.cookie.DateParseException;
import org.apache.http.impl.cookie.DateUtils;

public class f
{
  public static long a(String paramString)
  {
    try
    {
      long l = DateUtils.parseDate(paramString).getTime();
      return l;
    }
    catch (DateParseException localDateParseException)
    {
    }
    return 0L;
  }

  public static b.a a(i parami)
  {
    long l1 = System.currentTimeMillis();
    Map localMap = parami.c;
    long l2 = 0L;
    long l3 = 0L;
    long l4 = 0L;
    String str1 = (String)localMap.get("Date");
    if (str1 != null)
      l2 = a(str1);
    String str2 = (String)localMap.get("Cache-Control");
    int i = 0;
    String[] arrayOfString;
    int k;
    if (str2 != null)
    {
      i = 1;
      arrayOfString = str2.split(",");
      k = 0;
    }
    while (true)
    {
      String str5;
      if (k < arrayOfString.length)
      {
        str5 = arrayOfString[k].trim();
        if ((str5.equals("no-cache")) || (str5.equals("no-store")))
          return null;
        if (!str5.startsWith("max-age="));
      }
      try
      {
        long l7 = Long.parseLong(str5.substring(8));
        for (l4 = l7; ; l4 = 0L)
          do
          {
            k++;
            break;
          }
          while ((!str5.equals("must-revalidate")) && (!str5.equals("proxy-revalidate")));
        int j = i;
        long l5 = l4;
        String str3 = (String)localMap.get("Expires");
        if (str3 != null)
          l3 = a(str3);
        String str4 = (String)localMap.get("ETag");
        if (j != 0);
        for (l6 = l1 + l5 * 1000L; ; l6 = l1 + (l3 - l2))
        {
          b.a locala = new b.a();
          locala.a = parami.b;
          locala.b = str4;
          locala.e = l6;
          locala.d = locala.e;
          locala.c = l2;
          locala.f = localMap;
          return locala;
          if ((l2 <= 0L) || (l3 < l2))
            break;
        }
      }
      catch (Exception localException)
      {
        while (true)
        {
          continue;
          long l6 = 0L;
        }
      }
    }
  }

  public static String a(Map<String, String> paramMap)
  {
    String str = (String)paramMap.get("Content-Type");
    if (str != null)
    {
      String[] arrayOfString1 = str.split(";");
      for (int i = 1; i < arrayOfString1.length; i++)
      {
        String[] arrayOfString2 = arrayOfString1[i].trim().split("=");
        if ((arrayOfString2.length == 2) && (arrayOfString2[0].equals("charset")))
          return arrayOfString2[1];
      }
    }
    return "ISO-8859-1";
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.taobao.munion.base.volley.a.f
 * JD-Core Version:    0.6.2
 */