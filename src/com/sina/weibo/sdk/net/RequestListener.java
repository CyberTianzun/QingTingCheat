package com.sina.weibo.sdk.net;

import com.sina.weibo.sdk.exception.WeiboException;

public abstract interface RequestListener
{
  public abstract void onComplete(String paramString);

  public abstract void onWeiboException(WeiboException paramWeiboException);
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.sina.weibo.sdk.net.RequestListener
 * JD-Core Version:    0.6.2
 */