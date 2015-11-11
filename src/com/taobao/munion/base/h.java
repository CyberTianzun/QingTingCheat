package com.taobao.munion.base;

import android.content.Context;
import android.content.res.AssetManager;
import java.io.IOException;
import java.io.InputStream;

public class h
{
  private c a = new c();

  public c a()
  {
    return this.a;
  }

  public InputStream a(Context paramContext, String paramString)
    throws IOException
  {
    InputStream localInputStream = null;
    try
    {
      Log.i("Looking for " + paramString + " in asset path...", new Object[0]);
      localInputStream = paramContext.getAssets().open(paramString);
      Log.i("Found " + paramString + " in asset path", new Object[0]);
      return localInputStream;
    }
    catch (IOException localIOException)
    {
      Log.i("No file found in assets with name [" + paramString + "].", new Object[0]);
    }
    return localInputStream;
  }

  public InputStream a(String paramString)
    throws IOException
  {
    c localc = this.a;
    InputStream localInputStream = null;
    if (localc != null)
    {
      Log.i("Looking for " + paramString + " in classpath...", new Object[0]);
      localInputStream = this.a.a().getResourceAsStream(paramString);
      if (localInputStream != null)
        Log.i("Found " + paramString + " in classpath", new Object[0]);
    }
    return localInputStream;
  }

  public void a(c paramc)
  {
    this.a = paramc;
  }

  public InputStream b(Context paramContext, String paramString)
    throws IOException
  {
    InputStream localInputStream = a(paramContext, paramString);
    if (localInputStream == null)
      localInputStream = a(paramString);
    if (localInputStream == null)
      Log.i("Could not locate [" + paramString + "] in any location", new Object[0]);
    return localInputStream;
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.taobao.munion.base.h
 * JD-Core Version:    0.6.2
 */