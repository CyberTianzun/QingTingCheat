package com.taobao.munion.view.webview;

import android.webkit.WebView;
import android.webkit.WebViewClient;

public class b extends WebViewClient
{
  private a a;

  public void a(a parama)
  {
    this.a = parama;
  }

  public boolean shouldOverrideUrlLoading(WebView paramWebView, String paramString)
  {
    if ((this.a != null) && (this.a.a()))
      return true;
    return super.shouldOverrideUrlLoading(paramWebView, paramString);
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.taobao.munion.view.webview.b
 * JD-Core Version:    0.6.2
 */