package com.taobao.newxp.common.c;

import android.util.Log;

public abstract class a
{
  protected static final String a = a.class.getName();

  public static <T extends a> T a(Class<T> paramClass)
  {
    try
    {
      a locala = (a)paramClass.newInstance();
      return locala;
    }
    catch (InstantiationException localInstantiationException)
    {
      Log.e(a, "", localInstantiationException);
      return null;
    }
    catch (IllegalAccessException localIllegalAccessException)
    {
      while (true)
        Log.e(a, "", localIllegalAccessException);
    }
  }

  protected abstract void a(e parame);

  protected abstract boolean a();
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.taobao.newxp.common.c.a
 * JD-Core Version:    0.6.2
 */