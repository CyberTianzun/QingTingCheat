package org.apache.commons.httpclient.protocol;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import org.apache.commons.httpclient.ConnectTimeoutException;
import org.apache.commons.httpclient.params.HttpConnectionParams;

public class DefaultProtocolSocketFactory
  implements ProtocolSocketFactory
{
  static Class class$org$apache$commons$httpclient$protocol$DefaultProtocolSocketFactory;
  private static final DefaultProtocolSocketFactory factory = new DefaultProtocolSocketFactory();

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

  static DefaultProtocolSocketFactory getSocketFactory()
  {
    return factory;
  }

  public Socket createSocket(String paramString, int paramInt)
    throws IOException, UnknownHostException
  {
    return new Socket(paramString, paramInt);
  }

  public Socket createSocket(String paramString, int paramInt1, InetAddress paramInetAddress, int paramInt2)
    throws IOException, UnknownHostException
  {
    return new Socket(paramString, paramInt1, paramInetAddress, paramInt2);
  }

  public Socket createSocket(String paramString, int paramInt1, InetAddress paramInetAddress, int paramInt2, HttpConnectionParams paramHttpConnectionParams)
    throws IOException, UnknownHostException, ConnectTimeoutException
  {
    if (paramHttpConnectionParams == null)
      throw new IllegalArgumentException("Parameters may not be null");
    int i = paramHttpConnectionParams.getConnectionTimeout();
    Socket localSocket;
    if (i == 0)
      localSocket = createSocket(paramString, paramInt1, paramInetAddress, paramInt2);
    do
    {
      return localSocket;
      localSocket = ReflectionSocketFactory.createSocket("javax.net.SocketFactory", paramString, paramInt1, paramInetAddress, paramInt2, i);
    }
    while (localSocket != null);
    return ControllerThreadSocketFactory.createSocket(this, paramString, paramInt1, paramInetAddress, paramInt2, i);
  }

  public boolean equals(Object paramObject)
  {
    if (paramObject != null)
    {
      Class localClass1 = paramObject.getClass();
      Class localClass2;
      if (class$org$apache$commons$httpclient$protocol$DefaultProtocolSocketFactory == null)
      {
        localClass2 = class$("org.apache.commons.httpclient.protocol.DefaultProtocolSocketFactory");
        class$org$apache$commons$httpclient$protocol$DefaultProtocolSocketFactory = localClass2;
      }
      while (localClass1.equals(localClass2))
      {
        return true;
        localClass2 = class$org$apache$commons$httpclient$protocol$DefaultProtocolSocketFactory;
      }
    }
    return false;
  }

  public int hashCode()
  {
    Class localClass;
    if (class$org$apache$commons$httpclient$protocol$DefaultProtocolSocketFactory == null)
    {
      localClass = class$("org.apache.commons.httpclient.protocol.DefaultProtocolSocketFactory");
      class$org$apache$commons$httpclient$protocol$DefaultProtocolSocketFactory = localClass;
    }
    while (true)
    {
      return localClass.hashCode();
      localClass = class$org$apache$commons$httpclient$protocol$DefaultProtocolSocketFactory;
    }
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     org.apache.commons.httpclient.protocol.DefaultProtocolSocketFactory
 * JD-Core Version:    0.6.2
 */