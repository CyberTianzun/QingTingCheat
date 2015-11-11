package com.taobao.munion.base.caches;

import android.os.Build.VERSION;
import android.util.LruCache;
import java.io.IOException;
import java.io.InputStream;

public class s
{
  private static final String a = "WVMemoryCache";
  private static s b;
  private LruCache<String, w> c;

  public s()
  {
    int i = (int)(Runtime.getRuntime().maxMemory() / 10240L);
    if (Build.VERSION.SDK_INT > 11)
      this.c = new LruCache(i)
      {
        protected int a(String paramAnonymousString, w paramAnonymousw)
        {
          return (int)paramAnonymousw.d / 1024;
        }
      };
  }

  public static s a()
  {
    try
    {
      if (b == null)
        b = new s();
      s locals = b;
      return locals;
    }
    finally
    {
    }
  }

  public w a(String paramString)
  {
    if ((this.c != null) && (paramString != null))
    {
      w localw = (w)this.c.get(paramString);
      if (localw != null)
        try
        {
          localw.e.reset();
          return localw;
        }
        catch (IOException localIOException)
        {
          localIOException.printStackTrace();
          return localw;
        }
    }
    return null;
  }

  public void a(String paramString, w paramw)
  {
    if ((this.c != null) && (paramString != null) && (paramw != null))
    {
      paramw.e.mark(2147483647);
      this.c.put(paramString, paramw);
    }
  }

  public void b()
  {
    if (this.c != null)
      this.c.evictAll();
  }

  public void b(String paramString)
  {
    if ((this.c != null) && (paramString != null))
      this.c.remove(paramString);
  }

  public boolean c()
  {
    return this.c != null;
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.taobao.munion.base.caches.s
 * JD-Core Version:    0.6.2
 */