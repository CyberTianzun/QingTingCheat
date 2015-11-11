package com.taobao.munion.base.caches;

public class h
{
  private static h a;

  public static h a()
  {
    try
    {
      if (a == null)
        a = new h();
      h localh = a;
      return localh;
    }
    finally
    {
    }
  }

  public g a(String paramString1, String paramString2, int paramInt, boolean paramBoolean)
  {
    if ((paramString2 == null) || (paramInt < 10))
      return null;
    Object localObject;
    String str;
    if ((paramBoolean) && (r.a()))
    {
      localObject = k.a(b.b, paramString1, paramString2, true);
      str = k.a(b.b, paramString1, paramString2);
    }
    while (true)
    {
      g localg = new g((String)localObject, str, paramInt, paramBoolean);
      localg.d();
      return localg;
      str = k.a(b.b, paramString1, paramString2);
      localObject = str;
      paramBoolean = false;
    }
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.taobao.munion.base.caches.h
 * JD-Core Version:    0.6.2
 */