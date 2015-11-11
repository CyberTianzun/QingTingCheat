package com.taobao.newxp.view.audio;

import android.os.AsyncTask;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class c
{
  private URL a = null;
  private b b;

  private InputStream a(String paramString)
  {
    try
    {
      this.a = new URL(paramString);
      InputStream localInputStream = ((HttpURLConnection)this.a.openConnection()).getInputStream();
      return localInputStream;
    }
    catch (MalformedURLException localMalformedURLException)
    {
      localMalformedURLException.printStackTrace();
      return null;
    }
    catch (IOException localIOException)
    {
      localIOException.printStackTrace();
    }
    return null;
  }

  private String b(String paramString1, String paramString2)
  {
    InputStream localInputStream = null;
    try
    {
      b localb = new b();
      str = paramString1.substring(1 + paramString1.lastIndexOf("/"));
      System.out.println(str);
      boolean bool = localb.c(paramString2 + str);
      if (bool)
        if (0 == 0);
      do
      {
        do
        {
          try
          {
            null.close();
            return str;
          }
          catch (IOException localIOException5)
          {
            localIOException5.printStackTrace();
            return str;
          }
          localInputStream = a(paramString1);
          if (localb.a(paramString2, str, localInputStream) != null)
            break;
          str = "error";
        }
        while (localInputStream == null);
        try
        {
          localInputStream.close();
          return str;
        }
        catch (IOException localIOException3)
        {
          localIOException3.printStackTrace();
          return str;
        }
      }
      while (localInputStream == null);
      try
      {
        localInputStream.close();
        return str;
      }
      catch (IOException localIOException4)
      {
        localIOException4.printStackTrace();
        return str;
      }
    }
    catch (Exception localException)
    {
      String str;
      do
      {
        localException.printStackTrace();
        str = "error";
      }
      while (localInputStream == null);
      try
      {
        localInputStream.close();
        return str;
      }
      catch (IOException localIOException2)
      {
        localIOException2.printStackTrace();
        return str;
      }
    }
    finally
    {
      if (localInputStream == null);
    }
    try
    {
      localInputStream.close();
      throw localObject;
    }
    catch (IOException localIOException1)
    {
      while (true)
        localIOException1.printStackTrace();
    }
  }

  public b a()
  {
    return this.b;
  }

  public void a(b paramb)
  {
    this.b = paramb;
  }

  public void a(String paramString1, String paramString2)
  {
    new a(paramString2).execute(new String[] { paramString1 });
  }

  class a extends AsyncTask<String, Integer, String>
  {
    private String b;

    public a(String arg2)
    {
      Object localObject;
      this.b = localObject;
    }

    protected String a(String[] paramArrayOfString)
    {
      return c.a(c.this, paramArrayOfString[0], this.b);
    }

    protected void a(String paramString)
    {
      if (c.a(c.this) != null)
        c.a(c.this).a(c.c.a, paramString);
    }
  }

  public static abstract interface b
  {
    public abstract void a(c.c paramc, String paramString);
  }

  public static enum c
  {
    static
    {
      c[] arrayOfc = new c[2];
      arrayOfc[0] = a;
      arrayOfc[1] = b;
    }
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.taobao.newxp.view.audio.c
 * JD-Core Version:    0.6.2
 */