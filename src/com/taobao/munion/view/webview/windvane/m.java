package com.taobao.munion.view.webview.windvane;

import android.graphics.Bitmap;
import android.text.TextUtils;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import com.taobao.munion.base.caches.u;
import com.taobao.munion.base.caches.w;
import com.taobao.newxp.common.a.a;
import com.taobao.newxp.common.a.a.f.a;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class m extends com.taobao.munion.view.webview.b
{
  public static final int c = 0;
  public static final int d = 1;
  public static final String e = "mmusdk_cache";
  public static final String f = "1";
  public static boolean g = true;
  private int a = 0;
  protected String b = null;

  public m()
  {
  }

  public m(int paramInt)
  {
    this.a = paramInt;
  }

  // ERROR //
  private w a(String paramString1, String paramString2)
  {
    // Byte code:
    //   0: invokestatic 40	com/taobao/munion/base/caches/b:a	()Lcom/taobao/munion/base/caches/b;
    //   3: aload_1
    //   4: invokevirtual 43	com/taobao/munion/base/caches/b:c	(Ljava/lang/String;)Lcom/taobao/munion/base/caches/w;
    //   7: astore 6
    //   9: aload 6
    //   11: astore 4
    //   13: aload 4
    //   15: ifnull +11 -> 26
    //   18: aload 4
    //   20: invokevirtual 49	com/taobao/munion/base/caches/w:j	()Z
    //   23: ifeq +108 -> 131
    //   26: aload_1
    //   27: invokestatic 55	android/net/Uri:parse	(Ljava/lang/String;)Landroid/net/Uri;
    //   30: ldc 13
    //   32: invokevirtual 59	android/net/Uri:getQueryParameter	(Ljava/lang/String;)Ljava/lang/String;
    //   35: astore 7
    //   37: aload_1
    //   38: ldc 61
    //   40: ldc 63
    //   42: invokevirtual 69	java/lang/String:replace	(Ljava/lang/CharSequence;Ljava/lang/CharSequence;)Ljava/lang/String;
    //   45: ldc 71
    //   47: ldc 63
    //   49: invokevirtual 69	java/lang/String:replace	(Ljava/lang/CharSequence;Ljava/lang/CharSequence;)Ljava/lang/String;
    //   52: ldc 73
    //   54: ldc 63
    //   56: invokevirtual 69	java/lang/String:replace	(Ljava/lang/CharSequence;Ljava/lang/CharSequence;)Ljava/lang/String;
    //   59: ldc 75
    //   61: ldc 63
    //   63: invokevirtual 69	java/lang/String:replace	(Ljava/lang/CharSequence;Ljava/lang/CharSequence;)Ljava/lang/String;
    //   66: astore 8
    //   68: aload 7
    //   70: invokestatic 81	android/text/TextUtils:isEmpty	(Ljava/lang/CharSequence;)Z
    //   73: ifne +23 -> 96
    //   76: ldc 16
    //   78: aload 7
    //   80: invokevirtual 85	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   83: ifeq +13 -> 96
    //   86: invokestatic 40	com/taobao/munion/base/caches/b:a	()Lcom/taobao/munion/base/caches/b;
    //   89: aload 8
    //   91: aload_2
    //   92: invokevirtual 87	com/taobao/munion/base/caches/b:a	(Ljava/lang/String;Ljava/lang/String;)Lcom/taobao/munion/base/caches/w;
    //   95: areturn
    //   96: aload 4
    //   98: ifnull +11 -> 109
    //   101: invokestatic 40	com/taobao/munion/base/caches/b:a	()Lcom/taobao/munion/base/caches/b;
    //   104: aload 8
    //   106: invokevirtual 90	com/taobao/munion/base/caches/b:d	(Ljava/lang/String;)V
    //   109: aconst_null
    //   110: areturn
    //   111: astore_3
    //   112: aconst_null
    //   113: astore 4
    //   115: aload_3
    //   116: astore 5
    //   118: aload 5
    //   120: invokevirtual 93	java/lang/Exception:printStackTrace	()V
    //   123: aload 4
    //   125: areturn
    //   126: astore 5
    //   128: goto -10 -> 118
    //   131: aload 4
    //   133: areturn
    //
    // Exception table:
    //   from	to	target	type
    //   0	9	111	java/lang/Exception
    //   18	26	126	java/lang/Exception
    //   26	96	126	java/lang/Exception
    //   101	109	126	java/lang/Exception
  }

  public static String a(InputStream paramInputStream)
  {
    BufferedReader localBufferedReader = new BufferedReader(new InputStreamReader(paramInputStream));
    StringBuilder localStringBuilder = new StringBuilder();
    try
    {
      while (true)
      {
        String str = localBufferedReader.readLine();
        if (str == null)
          break;
        localStringBuilder.append(str + "\n");
      }
    }
    catch (IOException localIOException)
    {
      localIOException = localIOException;
      localIOException.printStackTrace();
      return localStringBuilder.toString();
    }
    finally
    {
    }
  }

  private w b(String paramString1, String paramString2)
  {
    w localw = com.taobao.munion.base.caches.b.a().c(paramString1);
    if (localw == null)
    {
      com.taobao.munion.base.caches.b.a().a(paramString1, localw, paramString2);
      localw = null;
    }
    return localw;
  }

  public void onPageStarted(WebView paramWebView, String paramString, Bitmap paramBitmap)
  {
    super.onPageStarted(paramWebView, paramString, paramBitmap);
    this.b = paramString;
  }

  public WebResourceResponse shouldInterceptRequest(WebView paramWebView, String paramString)
  {
    if (!TextUtils.isEmpty(this.b))
      this.b = this.b.replace("&mmusdkwakeup=1", "").replace("mmusdkwakeup=1", "");
    if ((!TextUtils.isEmpty(this.b)) && (this.b.equals(com.taobao.munion.base.caches.b.d)) && (u.b(paramString)) && (g))
    {
      f.a locala = new f.a(2, System.currentTimeMillis());
      a.a().a(locala);
      g = false;
    }
    if (1 == this.a)
    {
      if (com.taobao.munion.base.caches.b.g)
      {
        w localw2 = a(paramString, this.b);
        if (localw2 != null)
          return new WebResourceResponse(localw2.d(), localw2.c(), localw2.e);
      }
      return super.shouldInterceptRequest(paramWebView, paramString);
    }
    if (com.taobao.munion.base.caches.b.a().b(paramString))
    {
      w localw1 = b(paramString, this.b);
      if (localw1 != null)
        return new WebResourceResponse(localw1.d(), localw1.c(), localw1.e);
    }
    return super.shouldInterceptRequest(paramWebView, paramString);
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.taobao.munion.view.webview.windvane.m
 * JD-Core Version:    0.6.2
 */