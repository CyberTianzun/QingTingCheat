package org.apache.commons.httpclient;

import java.io.IOException;

public abstract interface HttpMethodRetryHandler
{
  public abstract boolean retryMethod(HttpMethod paramHttpMethod, IOException paramIOException, int paramInt);
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     org.apache.commons.httpclient.HttpMethodRetryHandler
 * JD-Core Version:    0.6.2
 */