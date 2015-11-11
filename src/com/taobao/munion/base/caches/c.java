package com.taobao.munion.base.caches;

import android.text.TextUtils;
import java.util.Map;

public class c
{
  private static c a;
  private Map<String, String> b;

  public static c a()
  {
    if (a == null);
    try
    {
      if (a == null)
        a = new c();
      return a;
    }
    finally
    {
    }
  }

  public void a(Map<String, String> paramMap)
  {
    this.b = paramMap;
  }

  public boolean a(String paramString)
  {
    long l;
    if ((this.b != null) && (this.b.containsKey(paramString)))
      l = c(paramString);
    while (true)
    {
      try
      {
        int i = 13 - Long.valueOf(l).toString().length();
        int j;
        int k;
        if (i > 0)
        {
          j = 0;
          break label111;
          if (k < Math.abs(i))
          {
            l /= 10L;
            k++;
            continue;
          }
        }
      }
      catch (Exception localException)
      {
      }
      label111: 
      do
        while (true)
        {
          w localw = b.a().c(paramString);
          if ((localw != null) && (l > localw.a()))
            b.a().d(paramString);
          return true;
          return false;
          while (j < i)
          {
            l *= 10L;
            j++;
          }
        }
      while (i >= 0);
      k = 0;
    }
  }

  public String b(String paramString)
  {
    try
    {
      if ((this.b != null) && (this.b.size() > 0))
      {
        String str = (String)this.b.get(paramString);
        return str;
      }
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return null;
  }

  public long c(String paramString)
  {
    if ((this.b == null) || (this.b.size() <= 0))
      return 0L;
    try
    {
      String str = (String)this.b.get(paramString);
      if (!TextUtils.isEmpty(str))
      {
        long l = Long.parseLong(str);
        return l;
      }
    }
    catch (Exception localException)
    {
      return 0L;
    }
    return 0L;
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.taobao.munion.base.caches.c
 * JD-Core Version:    0.6.2
 */