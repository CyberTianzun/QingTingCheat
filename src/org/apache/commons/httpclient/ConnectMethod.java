package org.apache.commons.httpclient;

import java.io.IOException;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.httpclient.protocol.Protocol;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ConnectMethod extends HttpMethodBase
{
  private static final Log LOG;
  public static final String NAME = "CONNECT";
  static Class class$org$apache$commons$httpclient$ConnectMethod;

  static
  {
    Class localClass;
    if (class$org$apache$commons$httpclient$ConnectMethod == null)
    {
      localClass = class$("org.apache.commons.httpclient.ConnectMethod");
      class$org$apache$commons$httpclient$ConnectMethod = localClass;
    }
    while (true)
    {
      LOG = LogFactory.getLog(localClass);
      return;
      localClass = class$org$apache$commons$httpclient$ConnectMethod;
    }
  }

  public ConnectMethod()
  {
    LOG.trace("enter ConnectMethod()");
  }

  public ConnectMethod(HttpMethod paramHttpMethod)
  {
    LOG.trace("enter ConnectMethod(HttpMethod)");
  }

  static Class class$(String paramString)
  {
    try
    {
      Class localClass = Class.forName(paramString);
      return localClass;
    }
    catch (ClassNotFoundException localClassNotFoundException)
    {
      throw new NoClassDefFoundError(localClassNotFoundException.getMessage());
    }
  }

  protected void addCookieRequestHeader(HttpState paramHttpState, HttpConnection paramHttpConnection)
    throws IOException, HttpException
  {
  }

  protected void addRequestHeaders(HttpState paramHttpState, HttpConnection paramHttpConnection)
    throws IOException, HttpException
  {
    LOG.trace("enter ConnectMethod.addRequestHeaders(HttpState, HttpConnection)");
    addUserAgentRequestHeader(paramHttpState, paramHttpConnection);
    addHostRequestHeader(paramHttpState, paramHttpConnection);
    addProxyConnectionHeader(paramHttpState, paramHttpConnection);
  }

  public int execute(HttpState paramHttpState, HttpConnection paramHttpConnection)
    throws IOException, HttpException
  {
    LOG.trace("enter ConnectMethod.execute(HttpState, HttpConnection)");
    int i = super.execute(paramHttpState, paramHttpConnection);
    if (LOG.isDebugEnabled())
      LOG.debug("CONNECT status code " + i);
    return i;
  }

  public String getName()
  {
    return "CONNECT";
  }

  protected boolean shouldCloseConnection(HttpConnection paramHttpConnection)
  {
    if (getStatusCode() == 200)
    {
      boolean bool = paramHttpConnection.isTransparent();
      Header localHeader = null;
      if (!bool)
        localHeader = getResponseHeader("proxy-connection");
      if (localHeader == null)
        localHeader = getResponseHeader("connection");
      if ((localHeader != null) && (localHeader.getValue().equalsIgnoreCase("close")) && (LOG.isWarnEnabled()))
        LOG.warn("Invalid header encountered '" + localHeader.toExternalForm() + "' in response " + getStatusLine().toString());
      return false;
    }
    return super.shouldCloseConnection(paramHttpConnection);
  }

  protected void writeRequestLine(HttpState paramHttpState, HttpConnection paramHttpConnection)
    throws IOException, HttpException
  {
    int i = paramHttpConnection.getPort();
    if (i == -1)
      i = paramHttpConnection.getProtocol().getDefaultPort();
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append(getName());
    localStringBuffer.append(' ');
    localStringBuffer.append(paramHttpConnection.getHost());
    if (i > -1)
    {
      localStringBuffer.append(':');
      localStringBuffer.append(i);
    }
    localStringBuffer.append(" ");
    localStringBuffer.append(getEffectiveVersion());
    String str = localStringBuffer.toString();
    paramHttpConnection.printLine(str, getParams().getHttpElementCharset());
    if (Wire.HEADER_WIRE.enabled())
      Wire.HEADER_WIRE.output(str);
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     org.apache.commons.httpclient.ConnectMethod
 * JD-Core Version:    0.6.2
 */