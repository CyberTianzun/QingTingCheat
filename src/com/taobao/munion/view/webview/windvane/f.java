package com.taobao.munion.view.webview.windvane;

import android.content.Context;
import android.text.TextUtils;
import com.taobao.munion.view.webview.windvane.mraid.AlimamaMraid;
import java.util.HashMap;

public class f
{
  private static HashMap<String, String> a = new HashMap();
  private Context b;
  private WindVaneWebView c;

  public f(Context paramContext, WindVaneWebView paramWindVaneWebView)
  {
    this.b = paramContext;
    this.c = paramWindVaneWebView;
    a();
  }

  private Object a(String paramString, WindVaneWebView paramWindVaneWebView, Context paramContext)
  {
    String str = (String)a.get(paramString);
    if (!TextUtils.isEmpty(str))
      try
      {
        Class localClass = Class.forName(str);
        if ((localClass != null) && (j.class.isAssignableFrom(localClass)))
        {
          j localj = (j)localClass.newInstance();
          localj.initialize(paramContext, paramWindVaneWebView);
          return localj;
        }
      }
      catch (Exception localException)
      {
        localException.printStackTrace();
      }
    return null;
  }

  public Object a(String paramString)
  {
    if (a == null)
      a = new HashMap();
    return a(paramString, this.c, this.b);
  }

  public void a()
  {
    try
    {
      a(Class.forName("com.taobao.newxp.view.handler.waketaobao.AlimamaWakeup"));
      a(AlimamaMraid.class);
    }
    catch (Exception localException1)
    {
      try
      {
        a(Class.forName("com.taobao.newxp.view.handler.Mmusdk"));
        return;
        localException1 = localException1;
        localException1.printStackTrace();
      }
      catch (Exception localException2)
      {
        localException2.printStackTrace();
      }
    }
  }

  public void a(Class paramClass)
  {
    if (a == null)
      a = new HashMap();
    a.put(paramClass.getSimpleName(), paramClass.getName());
  }

  public void b(String paramString)
  {
    if (a == null)
      a = new HashMap();
    a.remove(paramString);
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.taobao.munion.view.webview.windvane.f
 * JD-Core Version:    0.6.2
 */