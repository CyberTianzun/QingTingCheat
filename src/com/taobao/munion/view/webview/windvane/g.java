package com.taobao.munion.view.webview.windvane;

import android.text.TextUtils;

public class g
  implements c
{
  private static g a = new g();

  public static g a()
  {
    return a;
  }

  public void a(Object paramObject, String paramString)
  {
    a locala;
    String str;
    if ((paramObject instanceof a))
    {
      locala = (a)paramObject;
      if (!TextUtils.isEmpty(paramString))
        break label59;
      Object[] arrayOfObject2 = new Object[1];
      arrayOfObject2[0] = locala.g;
      str = String.format("javascript:window.WindVane.onSuccess(%s,'');", arrayOfObject2);
    }
    while (true)
    {
      if (locala.a != null);
      try
      {
        locala.a.loadUrl(str);
        return;
        label59: Object[] arrayOfObject1 = new Object[2];
        arrayOfObject1[0] = locala.g;
        arrayOfObject1[1] = paramString;
        str = String.format("javascript:window.WindVane.onSuccess(%s,'%s');", arrayOfObject1);
      }
      catch (Exception localException)
      {
        localException.printStackTrace();
      }
    }
  }

  public void a(Object paramObject, String paramString1, String paramString2)
  {
    a locala;
    String str;
    if ((paramObject instanceof a))
    {
      locala = (a)paramObject;
      if (!TextUtils.isEmpty(paramString2))
        break label54;
      str = String.format("javascript:window.WindVane.fireEvent('%s', '');", new Object[] { paramString1 });
    }
    while (true)
    {
      if (locala.a != null);
      try
      {
        locala.a.loadUrl(str);
        return;
        label54: str = String.format("javascript:window.WindVane.fireEvent('%s','%s');", new Object[] { paramString1, paramString2 });
      }
      catch (Exception localException)
      {
        localException.printStackTrace();
      }
    }
  }

  public void b(Object paramObject, String paramString)
  {
    a locala;
    String str;
    if ((paramObject instanceof a))
    {
      locala = (a)paramObject;
      if (!TextUtils.isEmpty(paramString))
        break label59;
      Object[] arrayOfObject2 = new Object[1];
      arrayOfObject2[0] = locala.g;
      str = String.format("javascript:window.WindVane.onFailure(%s,'');", arrayOfObject2);
    }
    while (true)
    {
      if (locala.a != null);
      try
      {
        locala.a.loadUrl(str);
        return;
        label59: Object[] arrayOfObject1 = new Object[2];
        arrayOfObject1[0] = locala.g;
        arrayOfObject1[1] = paramString;
        str = String.format("javascript:window.WindVane.onFailure(%s,'%s');", arrayOfObject1);
      }
      catch (Exception localException)
      {
        localException.printStackTrace();
      }
    }
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.taobao.munion.view.webview.windvane.g
 * JD-Core Version:    0.6.2
 */