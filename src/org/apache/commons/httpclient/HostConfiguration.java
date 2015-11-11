package org.apache.commons.httpclient;

import java.net.InetAddress;
import org.apache.commons.httpclient.params.DefaultHttpParams;
import org.apache.commons.httpclient.params.HostParams;
import org.apache.commons.httpclient.protocol.Protocol;
import org.apache.commons.httpclient.util.LangUtils;

public class HostConfiguration
  implements Cloneable
{
  public static final HostConfiguration ANY_HOST_CONFIGURATION = new HostConfiguration();
  private HttpHost host = null;
  private InetAddress localAddress = null;
  private HostParams params = new HostParams();
  private ProxyHost proxyHost = null;

  public HostConfiguration()
  {
  }

  public HostConfiguration(HostConfiguration paramHostConfiguration)
  {
    while (true)
    {
      try
      {
        if (paramHostConfiguration.host != null)
        {
          this.host = ((HttpHost)paramHostConfiguration.host.clone());
          if (paramHostConfiguration.proxyHost != null)
          {
            this.proxyHost = ((ProxyHost)paramHostConfiguration.proxyHost.clone());
            this.localAddress = paramHostConfiguration.getLocalAddress();
            this.params = ((HostParams)paramHostConfiguration.getParams().clone());
          }
        }
        else
        {
          this.host = null;
          continue;
        }
      }
      catch (CloneNotSupportedException localCloneNotSupportedException)
      {
        throw new IllegalArgumentException("Host configuration could not be cloned");
      }
      finally
      {
      }
      this.proxyHost = null;
    }
  }

  public Object clone()
  {
    return new HostConfiguration(this);
  }

  public boolean equals(Object paramObject)
  {
    boolean bool1 = true;
    try
    {
      boolean bool2 = paramObject instanceof HostConfiguration;
      if (bool2)
        if (paramObject != this);
      while (true)
      {
        return bool1;
        HostConfiguration localHostConfiguration = (HostConfiguration)paramObject;
        if ((LangUtils.equals(this.host, localHostConfiguration.host)) && (LangUtils.equals(this.proxyHost, localHostConfiguration.proxyHost)))
        {
          boolean bool3 = LangUtils.equals(this.localAddress, localHostConfiguration.localAddress);
          if (bool3);
        }
        else
        {
          bool1 = false;
          continue;
          bool1 = false;
        }
      }
    }
    finally
    {
    }
  }

  public String getHost()
  {
    try
    {
      if (this.host != null)
      {
        String str2 = this.host.getHostName();
        str1 = str2;
        return str1;
      }
      String str1 = null;
    }
    finally
    {
    }
  }

  public String getHostURL()
  {
    try
    {
      if (this.host == null)
        throw new IllegalStateException("Host must be set to create a host URL");
    }
    finally
    {
    }
    String str = this.host.toURI();
    return str;
  }

  public InetAddress getLocalAddress()
  {
    try
    {
      InetAddress localInetAddress = this.localAddress;
      return localInetAddress;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }

  public HostParams getParams()
  {
    return this.params;
  }

  public int getPort()
  {
    try
    {
      if (this.host != null)
      {
        int j = this.host.getPort();
        i = j;
        return i;
      }
      int i = -1;
    }
    finally
    {
    }
  }

  public Protocol getProtocol()
  {
    try
    {
      if (this.host != null)
      {
        Protocol localProtocol2 = this.host.getProtocol();
        localProtocol1 = localProtocol2;
        return localProtocol1;
      }
      Protocol localProtocol1 = null;
    }
    finally
    {
    }
  }

  public String getProxyHost()
  {
    try
    {
      if (this.proxyHost != null)
      {
        String str2 = this.proxyHost.getHostName();
        str1 = str2;
        return str1;
      }
      String str1 = null;
    }
    finally
    {
    }
  }

  public int getProxyPort()
  {
    try
    {
      if (this.proxyHost != null)
      {
        int j = this.proxyHost.getPort();
        i = j;
        return i;
      }
      int i = -1;
    }
    finally
    {
    }
  }

  public String getVirtualHost()
  {
    try
    {
      String str = this.params.getVirtualHost();
      return str;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }

  public int hashCode()
  {
    try
    {
      int i = LangUtils.hashCode(LangUtils.hashCode(LangUtils.hashCode(17, this.host), this.proxyHost), this.localAddress);
      return i;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }

  public boolean hostEquals(HttpConnection paramHttpConnection)
  {
    if (paramHttpConnection == null)
      try
      {
        throw new IllegalArgumentException("Connection may not be null");
      }
      finally
      {
      }
    HttpHost localHttpHost = this.host;
    boolean bool1 = false;
    if (localHttpHost != null)
    {
      boolean bool2 = this.host.getHostName().equalsIgnoreCase(paramHttpConnection.getHost());
      bool1 = false;
      if (bool2)
        break label61;
    }
    while (true)
    {
      return bool1;
      label61: int i = this.host.getPort();
      int j = paramHttpConnection.getPort();
      bool1 = false;
      if (i == j)
      {
        boolean bool3 = this.host.getProtocol().equals(paramHttpConnection.getProtocol());
        bool1 = false;
        if (bool3)
          if (this.localAddress != null)
          {
            boolean bool4 = this.localAddress.equals(paramHttpConnection.getLocalAddress());
            bool1 = false;
            if (bool4)
              break label154;
          }
          else
          {
            InetAddress localInetAddress = paramHttpConnection.getLocalAddress();
            if (localInetAddress != null)
              bool1 = false;
            else
              label154: bool1 = true;
          }
      }
    }
  }

  public boolean isHostSet()
  {
    try
    {
      HttpHost localHttpHost = this.host;
      if (localHttpHost != null)
      {
        bool = true;
        return bool;
      }
      boolean bool = false;
    }
    finally
    {
    }
  }

  public boolean isProxySet()
  {
    try
    {
      ProxyHost localProxyHost = this.proxyHost;
      if (localProxyHost != null)
      {
        bool = true;
        return bool;
      }
      boolean bool = false;
    }
    finally
    {
    }
  }

  public boolean proxyEquals(HttpConnection paramHttpConnection)
  {
    boolean bool = true;
    if (paramHttpConnection == null)
      try
      {
        throw new IllegalArgumentException("Connection may not be null");
      }
      finally
      {
      }
    if (this.proxyHost != null)
      if (this.proxyHost.getHostName().equalsIgnoreCase(paramHttpConnection.getProxyHost()))
      {
        int i = this.proxyHost.getPort();
        int j = paramHttpConnection.getProxyPort();
        if (i != j);
      }
    while (true)
    {
      return bool;
      bool = false;
      continue;
      String str = paramHttpConnection.getProxyHost();
      if (str != null)
        bool = false;
    }
  }

  public void setHost(String paramString)
  {
    try
    {
      Protocol localProtocol = Protocol.getProtocol("http");
      setHost(paramString, localProtocol.getDefaultPort(), localProtocol);
      return;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }

  public void setHost(String paramString, int paramInt)
  {
    try
    {
      setHost(paramString, paramInt, Protocol.getProtocol("http"));
      return;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }

  public void setHost(String paramString1, int paramInt, String paramString2)
  {
    try
    {
      this.host = new HttpHost(paramString1, paramInt, Protocol.getProtocol(paramString2));
      return;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }

  public void setHost(String paramString, int paramInt, Protocol paramProtocol)
  {
    if (paramString == null)
      try
      {
        throw new IllegalArgumentException("host must not be null");
      }
      finally
      {
      }
    if (paramProtocol == null)
      throw new IllegalArgumentException("protocol must not be null");
    this.host = new HttpHost(paramString, paramInt, paramProtocol);
  }

  public void setHost(String paramString1, String paramString2, int paramInt, Protocol paramProtocol)
  {
    try
    {
      setHost(paramString1, paramInt, paramProtocol);
      this.params.setVirtualHost(paramString2);
      return;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }

  public void setHost(HttpHost paramHttpHost)
  {
    try
    {
      this.host = paramHttpHost;
      return;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }

  public void setHost(URI paramURI)
  {
    try
    {
      setHost(paramURI.getHost(), paramURI.getPort(), paramURI.getScheme());
      return;
    }
    catch (URIException localURIException)
    {
      throw new IllegalArgumentException(localURIException.toString());
    }
    finally
    {
    }
  }

  public void setLocalAddress(InetAddress paramInetAddress)
  {
    try
    {
      this.localAddress = paramInetAddress;
      return;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }

  public void setParams(HostParams paramHostParams)
  {
    if (paramHostParams == null)
      throw new IllegalArgumentException("Parameters may not be null");
    this.params = paramHostParams;
  }

  public void setProxy(String paramString, int paramInt)
  {
    try
    {
      this.proxyHost = new ProxyHost(paramString, paramInt);
      return;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }

  public void setProxyHost(ProxyHost paramProxyHost)
  {
    try
    {
      this.proxyHost = paramProxyHost;
      return;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }

  public String toString()
  {
    try
    {
      StringBuffer localStringBuffer = new StringBuffer(50);
      localStringBuffer.append("HostConfiguration[");
      HttpHost localHttpHost = this.host;
      int i = 0;
      if (localHttpHost != null)
      {
        i = 1;
        localStringBuffer.append("host=").append(this.host);
      }
      if (this.proxyHost != null)
      {
        if (i != 0)
        {
          localStringBuffer.append(", ");
          localStringBuffer.append("proxyHost=").append(this.proxyHost);
        }
      }
      else if (this.localAddress != null)
      {
        if (i == 0)
          break label166;
        localStringBuffer.append(", ");
        label102: localStringBuffer.append("localAddress=").append(this.localAddress);
        if (i == 0)
          break label172;
        localStringBuffer.append(", ");
      }
      label166: label172: 
      while (true)
      {
        localStringBuffer.append("params=").append(this.params);
        localStringBuffer.append("]");
        String str = localStringBuffer.toString();
        return str;
        i = 1;
        break;
        i = 1;
        break label102;
      }
    }
    finally
    {
    }
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     org.apache.commons.httpclient.HostConfiguration
 * JD-Core Version:    0.6.2
 */