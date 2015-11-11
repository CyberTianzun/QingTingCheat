package com.tencent.open;

import android.net.Uri;
import android.util.Log;
import android.webkit.WebView;
import java.io.UnsupportedEncodingException;
import java.lang.ref.WeakReference;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class a
{
  HashMap<String, b> a = new HashMap();

  public void a(b paramb, String paramString)
  {
    this.a.put(paramString, paramb);
  }

  public void a(String paramString1, String paramString2, List<String> paramList, a parama)
  {
    Log.d("TDialog", "getResult---objName = " + paramString1 + " methodName = " + paramString2);
    int i = paramList.size();
    int j = 0;
    while (true)
      if (j < i)
        try
        {
          paramList.set(j, URLDecoder.decode((String)paramList.get(j), "UTF-8"));
          j++;
        }
        catch (UnsupportedEncodingException localUnsupportedEncodingException)
        {
          while (true)
            localUnsupportedEncodingException.printStackTrace();
        }
    b localb = (b)this.a.get(paramString1);
    if (localb != null)
    {
      Log.d("TDialog", "call----");
      localb.call(paramString2, paramList, parama);
    }
    do
    {
      return;
      Log.d("TDialog", "not call----objName NOT FIND");
    }
    while (parama == null);
    parama.a();
  }

  public boolean a(WebView paramWebView, String paramString)
  {
    Log.d("Dialog", "canHandleUrl---url = " + paramString);
    if (paramString == null);
    ArrayList localArrayList;
    do
    {
      do
        return false;
      while (!Uri.parse(paramString).getScheme().equals("jsbridge"));
      localArrayList = new ArrayList(Arrays.asList((paramString + "/#").split("/")));
    }
    while (localArrayList.size() < 6);
    String str1 = (String)localArrayList.get(2);
    String str2 = (String)localArrayList.get(3);
    List localList = localArrayList.subList(4, -1 + localArrayList.size());
    a locala = new a(paramWebView, 4L, paramString);
    paramWebView.getUrl();
    a(str1, str2, localList, locala);
    return true;
  }

  public static class a
  {
    WeakReference<WebView> a;
    long b;
    String c;

    public a(WebView paramWebView, long paramLong, String paramString)
    {
      this.a = new WeakReference(paramWebView);
      this.b = paramLong;
      this.c = paramString;
    }

    public void a()
    {
      WebView localWebView = (WebView)this.a.get();
      if (localWebView == null)
        return;
      localWebView.loadUrl("javascript:window.JsBridge&&JsBridge.callback(" + this.b + ",{'r':1,'result':'no such method'})");
    }

    public void a(Object paramObject)
    {
      WebView localWebView = (WebView)this.a.get();
      if (localWebView == null)
        return;
      String str1 = "'undefined'";
      if ((paramObject instanceof String))
      {
        String str2 = ((String)paramObject).replace("\\", "\\\\").replace("'", "\\'");
        str1 = "'" + str2 + "'";
      }
      while (true)
      {
        localWebView.loadUrl("javascript:window.JsBridge&&JsBridge.callback(" + this.b + ",{'r':0,'result':" + str1 + "});");
        return;
        if (((paramObject instanceof Number)) || ((paramObject instanceof Long)) || ((paramObject instanceof Integer)) || ((paramObject instanceof Double)) || ((paramObject instanceof Float)))
          str1 = paramObject.toString();
        else if ((paramObject instanceof Boolean))
          str1 = paramObject.toString();
      }
    }

    public void a(String paramString)
    {
      WebView localWebView = (WebView)this.a.get();
      if (localWebView != null)
        localWebView.loadUrl("javascript:" + paramString);
    }
  }

  public static class b
  {
    public void call(String paramString, List<String> paramList, a.a parama)
    {
      Method[] arrayOfMethod = getClass().getDeclaredMethods();
      int i = arrayOfMethod.length;
      int j = 0;
      while (true)
      {
        Object localObject1 = null;
        if (j < i)
        {
          Method localMethod = arrayOfMethod[j];
          if ((localMethod.getName().equals(paramString)) && (localMethod.getParameterTypes().length == paramList.size()))
            localObject1 = localMethod;
        }
        else if (localObject1 == null);
        try
        {
          Object localObject2;
          switch (paramList.size())
          {
          default:
            Object[] arrayOfObject6 = new Object[6];
            arrayOfObject6[0] = paramList.get(0);
            arrayOfObject6[1] = paramList.get(1);
            arrayOfObject6[2] = paramList.get(2);
            arrayOfObject6[3] = paramList.get(3);
            arrayOfObject6[4] = paramList.get(4);
            arrayOfObject6[5] = paramList.get(5);
            localObject2 = localObject1.invoke(this, arrayOfObject6);
            label199: if (localObject1.getReturnType() == Void.class)
            {
              if (parama != null)
                parama.a(null);
              if (parama != null)
                parama.a();
            }
            break;
          case 0:
          case 1:
          case 2:
          case 3:
          case 4:
          case 5:
          }
          while ((parama == null) || (!customCallback()))
          {
            return;
            j++;
            break;
            localObject2 = localObject1.invoke(this, new Object[0]);
            break label199;
            Object[] arrayOfObject5 = new Object[1];
            arrayOfObject5[0] = paramList.get(0);
            localObject2 = localObject1.invoke(this, arrayOfObject5);
            break label199;
            Object[] arrayOfObject4 = new Object[2];
            arrayOfObject4[0] = paramList.get(0);
            arrayOfObject4[1] = paramList.get(1);
            localObject2 = localObject1.invoke(this, arrayOfObject4);
            break label199;
            Object[] arrayOfObject3 = new Object[3];
            arrayOfObject3[0] = paramList.get(0);
            arrayOfObject3[1] = paramList.get(1);
            arrayOfObject3[2] = paramList.get(2);
            localObject2 = localObject1.invoke(this, arrayOfObject3);
            break label199;
            Object[] arrayOfObject2 = new Object[4];
            arrayOfObject2[0] = paramList.get(0);
            arrayOfObject2[1] = paramList.get(1);
            arrayOfObject2[2] = paramList.get(2);
            arrayOfObject2[3] = paramList.get(3);
            localObject2 = localObject1.invoke(this, arrayOfObject2);
            break label199;
            Object[] arrayOfObject1 = new Object[5];
            arrayOfObject1[0] = paramList.get(0);
            arrayOfObject1[1] = paramList.get(1);
            arrayOfObject1[2] = paramList.get(2);
            arrayOfObject1[3] = paramList.get(3);
            arrayOfObject1[4] = paramList.get(4);
            localObject2 = localObject1.invoke(this, arrayOfObject1);
            break label199;
          }
          parama.a(localObject2.toString());
          return;
        }
        catch (IllegalAccessException localIllegalAccessException)
        {
          while (true)
            if (parama != null)
              parama.a();
        }
        catch (InvocationTargetException localInvocationTargetException)
        {
          while (true)
            if (parama != null)
              parama.a();
        }
        catch (Exception localException)
        {
          while (true)
            if (parama != null)
              parama.a();
        }
      }
    }

    public boolean customCallback()
    {
      return false;
    }
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.tencent.open.a
 * JD-Core Version:    0.6.2
 */