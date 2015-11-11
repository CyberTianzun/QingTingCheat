package org.apache.commons.httpclient;

import org.apache.commons.httpclient.params.HttpConnectionManagerParams;

public abstract interface HttpConnectionManager
{
  public abstract void closeIdleConnections(long paramLong);

  public abstract HttpConnection getConnection(HostConfiguration paramHostConfiguration);

  public abstract HttpConnection getConnection(HostConfiguration paramHostConfiguration, long paramLong)
    throws HttpException;

  public abstract HttpConnection getConnectionWithTimeout(HostConfiguration paramHostConfiguration, long paramLong)
    throws ConnectionPoolTimeoutException;

  public abstract HttpConnectionManagerParams getParams();

  public abstract void releaseConnection(HttpConnection paramHttpConnection);

  public abstract void setParams(HttpConnectionManagerParams paramHttpConnectionManagerParams);
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     org.apache.commons.httpclient.HttpConnectionManager
 * JD-Core Version:    0.6.2
 */