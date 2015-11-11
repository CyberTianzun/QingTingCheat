package com.taobao.munion.view.webview.windvane;

import android.webkit.JsPromptResult;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

public class l extends WebChromeClient
{
  WindVaneWebView b;

  public l(WindVaneWebView paramWindVaneWebView)
  {
    this.b = paramWindVaneWebView;
  }

  public boolean onJsPrompt(WebView paramWebView, String paramString1, String paramString2, String paramString3, JsPromptResult paramJsPromptResult)
  {
    d locald = this.b.getJsBridge();
    if ((locald != null) && (paramString3 != null) && (locald.a(paramString3)))
    {
      locald.c(paramString2);
      paramJsPromptResult.confirm("");
      return true;
    }
    return false;
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.taobao.munion.view.webview.windvane.l
 * JD-Core Version:    0.6.2
 */