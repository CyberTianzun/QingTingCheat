package com.sina.weibo.sdk.component;

import android.webkit.WebViewClient;

abstract class WeiboWebViewClient extends WebViewClient
{
  protected BrowserRequestCallBack mCallBack;

  public void setBrowserRequestCallBack(BrowserRequestCallBack paramBrowserRequestCallBack)
  {
    this.mCallBack = paramBrowserRequestCallBack;
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.sina.weibo.sdk.component.WeiboWebViewClient
 * JD-Core Version:    0.6.2
 */