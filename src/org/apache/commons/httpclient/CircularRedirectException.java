package org.apache.commons.httpclient;

public class CircularRedirectException extends RedirectException
{
  public CircularRedirectException()
  {
  }

  public CircularRedirectException(String paramString)
  {
    super(paramString);
  }

  public CircularRedirectException(String paramString, Throwable paramThrowable)
  {
    super(paramString, paramThrowable);
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     org.apache.commons.httpclient.CircularRedirectException
 * JD-Core Version:    0.6.2
 */