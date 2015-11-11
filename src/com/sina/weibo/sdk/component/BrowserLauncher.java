package com.sina.weibo.sdk.component;

 enum BrowserLauncher
{
  static
  {
    COMMON = new BrowserLauncher("COMMON", 3);
    BrowserLauncher[] arrayOfBrowserLauncher = new BrowserLauncher[4];
    arrayOfBrowserLauncher[0] = AUTH;
    arrayOfBrowserLauncher[1] = SHARE;
    arrayOfBrowserLauncher[2] = WIDGET;
    arrayOfBrowserLauncher[3] = COMMON;
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.sina.weibo.sdk.component.BrowserLauncher
 * JD-Core Version:    0.6.2
 */