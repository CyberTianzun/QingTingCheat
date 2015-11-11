package com.taobao.munion.view.webview.windvane;

import android.content.Context;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import android.webkit.WebView;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class i
  implements Handler.Callback, d
{
  protected Pattern a;
  protected String b;
  protected final int c = 1;
  protected Context d;
  protected WindVaneWebView e;
  protected Handler f;

  public i(Context paramContext)
  {
    this.d = paramContext;
    this.f = new Handler(Looper.getMainLooper(), this);
  }

  public WebView a()
  {
    return this.e;
  }

  protected void a(int paramInt, a parama)
  {
    Message localMessage = Message.obtain();
    localMessage.what = paramInt;
    localMessage.obj = parama;
    this.f.sendMessage(localMessage);
  }

  public void a(WindVaneWebView paramWindVaneWebView)
  {
    this.e = paramWindVaneWebView;
  }

  protected void a(a parama)
  {
    Object localObject;
    if (parama.a == null)
    {
      localObject = null;
      if (localObject != null)
        break label29;
    }
    while (true)
    {
      return;
      localObject = parama.a.getJsObject(parama.d);
      break;
      try
      {
        label29: b.f localf = b.a(localObject.getClass().getName()).b(parama.e, new Class[] { Object.class, String.class });
        localf.a();
        if ((localObject != null) && ((localObject instanceof j)))
        {
          parama.b = localObject;
          parama.c = localf;
          parama.b = localObject;
          a(1, parama);
          return;
        }
      }
      catch (b.b.a locala)
      {
        locala.printStackTrace();
      }
    }
  }

  public void a(Pattern paramPattern)
  {
    this.a = paramPattern;
  }

  public boolean a(String paramString)
  {
    if (k.a(paramString))
    {
      a(k.b(paramString));
      d(paramString);
      return true;
    }
    return false;
  }

  public a b(String paramString)
  {
    if (paramString == null);
    Matcher localMatcher;
    a locala;
    int i;
    do
    {
      do
      {
        return null;
        localMatcher = this.a.matcher(paramString);
      }
      while (!localMatcher.matches());
      locala = new a();
      i = localMatcher.groupCount();
      if (i >= 5)
        locala.f = localMatcher.group(5);
    }
    while (i < 3);
    locala.d = localMatcher.group(1);
    locala.g = localMatcher.group(2);
    locala.e = localMatcher.group(3);
    return locala;
  }

  public String b()
  {
    return this.b;
  }

  public void c(String paramString)
  {
    if (TextUtils.isEmpty(paramString));
    a locala;
    do
    {
      return;
      locala = b(paramString);
    }
    while (locala == null);
    locala.a = this.e;
    a(locala);
  }

  public void d(String paramString)
  {
    this.b = paramString;
  }

  public boolean handleMessage(Message paramMessage)
  {
    a locala = (a)paramMessage.obj;
    if (locala == null)
      return false;
    try
    {
      switch (paramMessage.what)
      {
      case 1:
        Object localObject = locala.b;
        b.f localf = locala.c;
        Object[] arrayOfObject = new Object[2];
        arrayOfObject[0] = locala;
        if (TextUtils.isEmpty(locala.f));
        for (String str = "{}"; ; str = locala.f)
        {
          arrayOfObject[1] = str;
          localf.a(localObject, arrayOfObject);
          return true;
        }
      }
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return false;
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.taobao.munion.view.webview.windvane.i
 * JD-Core Version:    0.6.2
 */