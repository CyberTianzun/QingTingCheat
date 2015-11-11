package com.sina.weibo.sdk.exception;

public class WeiboException extends RuntimeException
{
  private static final long serialVersionUID = 475022994858770424L;

  public WeiboException()
  {
  }

  public WeiboException(String paramString)
  {
    super(paramString);
  }

  public WeiboException(String paramString, Throwable paramThrowable)
  {
    super(paramString, paramThrowable);
  }

  public WeiboException(Throwable paramThrowable)
  {
    super(paramThrowable);
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.sina.weibo.sdk.exception.WeiboException
 * JD-Core Version:    0.6.2
 */