package com.taobao.munion.view.webview.windvane;

public class h
{
  public static c a(Object paramObject)
  {
    try
    {
      String str = ((a)paramObject).a.getJsBridge().b();
      if ("wv_hybird:".equals(str))
        return g.a();
      if ("mraid:".equals(str))
      {
        com.taobao.munion.view.webview.windvane.mraid.c localc = com.taobao.munion.view.webview.windvane.mraid.c.a();
        return localc;
      }
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return g.a();
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.taobao.munion.view.webview.windvane.h
 * JD-Core Version:    0.6.2
 */