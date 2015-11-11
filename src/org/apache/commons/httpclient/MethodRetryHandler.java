package org.apache.commons.httpclient;

public abstract interface MethodRetryHandler
{
  public abstract boolean retryMethod(HttpMethod paramHttpMethod, HttpConnection paramHttpConnection, HttpRecoverableException paramHttpRecoverableException, int paramInt, boolean paramBoolean);
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     org.apache.commons.httpclient.MethodRetryHandler
 * JD-Core Version:    0.6.2
 */