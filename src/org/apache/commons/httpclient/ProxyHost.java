package org.apache.commons.httpclient;

import org.apache.commons.httpclient.protocol.Protocol;

public class ProxyHost extends HttpHost
{
  public ProxyHost(String paramString)
  {
    this(paramString, -1);
  }

  public ProxyHost(String paramString, int paramInt)
  {
    super(paramString, paramInt, Protocol.getProtocol("http"));
  }

  public ProxyHost(ProxyHost paramProxyHost)
  {
    super(paramProxyHost);
  }

  public Object clone()
  {
    return new ProxyHost(this);
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     org.apache.commons.httpclient.ProxyHost
 * JD-Core Version:    0.6.2
 */