package com.taobao.munion.base.caches;

import android.content.Context;
import android.text.TextUtils;
import com.taobao.munion.base.Log;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentLinkedQueue;

public class b
  implements v
{
  public static final String a = "_wvcrc=";
  public static Context b;
  public static String c = "";
  public static String d = "";
  public static String e = "";
  public static boolean f = false;
  public static boolean g = false;
  public static String h = "";
  public static String i = "";
  public static boolean j = false;
  private static final String k = "CacheManager";
  private static final String l = "wvcache";
  private static final int m = 150;
  private static final String n = "wvimage";
  private static final int o = 200;
  private static final String p = "yyz.config";
  private static b q;
  private g r;
  private g s;
  private int t;
  private boolean u = false;
  private boolean v = false;

  public static b a()
  {
    try
    {
      if (q == null)
        q = new b();
      b localb = q;
      return localb;
    }
    finally
    {
    }
  }

  private Map<String, String> a(w paramw)
  {
    HashMap localHashMap = null;
    if (paramw != null)
    {
      localHashMap = new HashMap();
      if (!TextUtils.isEmpty(paramw.g()))
        localHashMap.put("If-None-Match", paramw.g());
      if (paramw.h() > 0L)
        localHashMap.put("If-Modified-Since", e.a(paramw.h()));
    }
    return localHashMap;
  }

  private void a(i parami)
  {
    if (this.r == null)
      return;
    if (e.f(parami.d()))
    {
      this.s.a(parami);
      return;
    }
    this.r.a(parami);
  }

  public w a(String paramString1, String paramString2)
  {
    m localm = new m();
    t.a.add(localm);
    String str = u.f(paramString1);
    if (TextUtils.isEmpty(str))
      str = "text/html";
    d locald = new d(paramString1, localm);
    w localw = new w();
    localw.c(str);
    localw.b("utf-8");
    localw.e = locald;
    t.b().a(new q(paramString1, this, paramString2, localm));
    return localw;
  }

  public String a(boolean paramBoolean)
  {
    if (this.r == null)
      return null;
    if (paramBoolean)
      return this.s.a();
    return this.r.a();
  }

  public void a(Context paramContext)
  {
    a(paramContext, null);
  }

  public void a(Context paramContext, String paramString)
  {
    if (paramContext == null)
      try
      {
        throw new NullPointerException("CacheManager init error, context is null");
      }
      finally
      {
      }
    boolean bool = this.v;
    if (bool);
    while (true)
    {
      return;
      b = paramContext.getApplicationContext();
      Log.i("cache start init", new Object[0]);
      if (this.r == null)
      {
        this.r = h.a().a(paramString, "wvcache", 150, true);
        this.s = h.a().a(paramString, "wvimage", 200, true);
      }
      this.v = true;
    }
  }

  public void a(String paramString)
  {
    c = paramString;
    if (c(paramString) != null)
      a().d(paramString);
    a().a(paramString, new w(), "");
  }

  public void a(String paramString1, w paramw, String paramString2)
  {
    t.b().a(new q(paramString1, this, paramString2, null));
  }

  public void a(byte[] paramArrayOfByte, Map<String, String> paramMap, String paramString, m paramm)
  {
    if (paramMap == null)
    {
      if (paramm != null)
        paramm.b();
      return;
    }
    String str1 = (String)paramMap.get("url");
    String str2 = (String)paramMap.get("response-code");
    long l1 = c.a().c(str1);
    while (true)
    {
      int i1;
      int i2;
      int i3;
      String str3;
      try
      {
        i1 = 13 - Long.valueOf(l1).toString().length();
        if (i1 <= 0)
          break label527;
        i2 = 0;
        break label502;
        if (i3 < Math.abs(i1))
        {
          long l4 = l1 / 10L;
          i3++;
          l1 = l4;
          continue;
        }
      }
      catch (Exception localException)
      {
        str3 = com.taobao.munion.base.e.a(str1);
        if ("304".equals(str2))
        {
          w localw2 = s.a().a(str3);
          if (localw2 != null)
            localw2.a(l1);
          i locali = this.r.a(str3);
          if (locali == null)
            locali = this.s.a(str3);
          if (locali == null)
            break;
          locali.a(l1);
          a(locali);
          return;
        }
      }
      if ((!"200".equals(str2)) || (paramArrayOfByte == null) || (paramArrayOfByte.length <= 0))
        break;
      String str4 = (String)paramMap.get("content-type");
      if (TextUtils.isEmpty(str4));
      for (String str5 = (String)paramMap.get("Content-Type"); ; str5 = str4)
      {
        String str6 = (String)paramMap.get("last-modified");
        String str7 = (String)paramMap.get("etag");
        String str8 = e.c(str5);
        String str9 = e.b(str5);
        if (TextUtils.isEmpty(str9))
          str9 = "utf-8";
        long l2 = e.e(str6);
        if (l2 <= 0L)
          l2 = System.currentTimeMillis();
        w localw1 = new w();
        localw1.a(str3);
        localw1.c(str8);
        localw1.d(str7);
        localw1.b(str9);
        localw1.c(l2);
        if (l1 <= 0L)
          if (!e.g(str8))
            break label483;
        label483: for (l1 = 1800000L + System.currentTimeMillis(); ; l1 = 604800000L + System.currentTimeMillis())
        {
          localw1.a(l1);
          localw1.d = paramArrayOfByte.length;
          localw1.e = new ByteArrayInputStream(paramArrayOfByte);
          if (s.a().c())
          {
            s.a().a(str3, localw1);
            if (paramm != null)
            {
              t.b().a(paramm);
              paramm.b();
            }
          }
          a(localw1, paramArrayOfByte);
          if (paramm == null)
            break;
          t.b().a(paramm);
          paramm.b();
          return;
        }
      }
      label502: 
      while (i2 < i1)
      {
        long l3 = 10L * l1;
        i2++;
        l1 = l3;
      }
      continue;
      label527: if (i1 < 0)
        i3 = 0;
    }
  }

  public boolean a(i parami, byte[] paramArrayOfByte)
  {
    if (this.r == null);
    ByteBuffer localByteBuffer;
    String str;
    do
    {
      return false;
      localByteBuffer = ByteBuffer.wrap(paramArrayOfByte);
      if (e.f(parami.d()))
        return this.s.a(parami, localByteBuffer);
      str = com.taobao.munion.base.e.a(paramArrayOfByte);
    }
    while (str == null);
    parami.e(str);
    return this.r.a(parami, localByteBuffer);
  }

  public boolean a(String paramString1, String paramString2, long paramLong)
  {
    try
    {
      String str = com.taobao.munion.base.e.a(paramString1);
      w localw = new w();
      localw.a(str);
      localw.c("none");
      localw.d("");
      localw.b("utf-8");
      localw.c(0L);
      localw.a(System.currentTimeMillis() + 1000L * paramLong);
      byte[] arrayOfByte = paramString2.getBytes("utf-8");
      localw.d = arrayOfByte.length;
      localw.e = new ByteArrayInputStream(arrayOfByte);
      if (s.a().c())
        s.a().a(str, localw);
      a(localw, arrayOfByte);
      return true;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return false;
  }

  public File b(boolean paramBoolean)
  {
    if (this.r == null)
      return null;
    if (paramBoolean);
    for (String str = this.s.a() + File.separator + "temp"; ; str = this.r.a() + File.separator + "temp")
    {
      File localFile = new File(str);
      if (!localFile.exists())
        localFile.mkdir();
      return localFile;
    }
  }

  public void b(Context paramContext)
  {
    if (this.r == null)
      return;
    try
    {
      s.a().b();
      t.b().a(new Runnable()
      {
        public void run()
        {
          b.a(b.this).e();
          b.b(b.this).e();
        }
      });
      return;
    }
    catch (Exception localException)
    {
    }
  }

  public boolean b()
  {
    if (this.r == null)
      return true;
    return this.r.b();
  }

  public boolean b(String paramString)
  {
    return c.a().a(paramString);
  }

  public int c()
  {
    g localg = this.r;
    int i1 = 0;
    if (localg != null)
      i1 = this.r.c();
    if (this.s != null)
      i1 += this.s.c();
    return i1;
  }

  public w c(String paramString)
  {
    if (!TextUtils.isEmpty(paramString))
      paramString = paramString.replace("&mmusdkwakeup=1", "").replace("mmusdkwakeup=1", "").replace("&mmusdk_cache=1", "").replace("mmusdk_cache=1", "");
    if (this.r == null);
    while (true)
    {
      return null;
      String str1 = com.taobao.munion.base.e.a(paramString);
      w localw1 = s.a().a(str1);
      if (localw1 != null)
        return localw1;
      i locali1 = this.r.a(str1);
      String str2 = this.r.a();
      i locali2;
      String str3;
      if (locali1 == null)
      {
        i locali3 = this.s.a(str1);
        String str5 = this.s.a();
        locali2 = locali3;
        str3 = str5;
      }
      for (int i1 = 1; locali2 != null; i1 = 0)
      {
        w localw2 = w.b(locali2);
        String str4 = locali2.i();
        if ((i1 == 0) && (!TextUtils.isEmpty(str4)))
        {
          byte[] arrayOfByte = this.r.b(str1);
          if ((arrayOfByte != null) && (str4.equals(com.taobao.munion.base.e.a(arrayOfByte))))
          {
            localw2.d = arrayOfByte.length;
            localw2.e = new ByteArrayInputStream(arrayOfByte);
            return localw2;
          }
          this.r.c(str1);
          return null;
        }
        try
        {
          File localFile = new File(str3 + File.separator + str1);
          localw2.d = localFile.length();
          localw2.e = new a(localFile);
          label278: return localw2;
        }
        catch (FileNotFoundException localFileNotFoundException)
        {
          break label278;
        }
        locali2 = locali1;
        str3 = str2;
      }
    }
  }

  public void d(final String paramString)
  {
    if ((this.r == null) || (paramString == null))
      return;
    t.b().a(new Runnable()
    {
      public void run()
      {
        String str = com.taobao.munion.base.e.a(paramString);
        s.a().b(str);
        b.a(b.this).c(str);
      }
    });
  }

  public void e(String paramString)
  {
    try
    {
      List localList = new n().a(paramString);
      if ((localList != null) && (localList.size() > 0))
        for (int i1 = 0; i1 < localList.size(); i1++)
        {
          String str = ((a)localList.get(i1)).a();
          if (!TextUtils.isEmpty(str))
          {
            w localw = c(str);
            if (localw == null)
              a(str.replace("&mmusdkwakeup=1", "").replace("mmusdkwakeup=1", "").replace("&mmusdk_cache=1", "").replace("mmusdk_cache=1", ""), localw, "");
          }
        }
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
  }

  public static class a
  {
    private String a;

    public String a()
    {
      return this.a;
    }

    public void a(String paramString)
    {
      this.a = paramString;
    }
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.taobao.munion.base.caches.b
 * JD-Core Version:    0.6.2
 */