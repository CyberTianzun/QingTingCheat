package org.apache.commons.httpclient;

import java.io.IOException;
import java.security.Provider;
import java.security.Security;
import org.apache.commons.httpclient.params.DefaultHttpParams;
import org.apache.commons.httpclient.params.HttpClientParams;
import org.apache.commons.httpclient.params.HttpConnectionParams;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class HttpClient
{
  private static final Log LOG;
  static Class class$org$apache$commons$httpclient$HttpClient;
  private HostConfiguration hostConfiguration = new HostConfiguration();
  private HttpConnectionManager httpConnectionManager;
  private HttpClientParams params = null;
  private HttpState state = new HttpState();

  static
  {
    Class localClass;
    if (class$org$apache$commons$httpclient$HttpClient == null)
    {
      localClass = class$("org.apache.commons.httpclient.HttpClient");
      class$org$apache$commons$httpclient$HttpClient = localClass;
    }
    while (true)
    {
      LOG = LogFactory.getLog(localClass);
      if (LOG.isDebugEnabled());
      try
      {
        LOG.debug("Java version: " + System.getProperty("java.version"));
        LOG.debug("Java vendor: " + System.getProperty("java.vendor"));
        LOG.debug("Java class path: " + System.getProperty("java.class.path"));
        LOG.debug("Operating system name: " + System.getProperty("os.name"));
        LOG.debug("Operating system architecture: " + System.getProperty("os.arch"));
        LOG.debug("Operating system version: " + System.getProperty("os.version"));
        Provider[] arrayOfProvider = Security.getProviders();
        for (int i = 0; ; i++)
        {
          int j = arrayOfProvider.length;
          if (i >= j)
          {
            return;
            localClass = class$org$apache$commons$httpclient$HttpClient;
            break;
          }
          Provider localProvider = arrayOfProvider[i];
          LOG.debug(localProvider.getName() + " " + localProvider.getVersion() + ": " + localProvider.getInfo());
        }
      }
      catch (SecurityException localSecurityException)
      {
      }
    }
  }

  public HttpClient()
  {
    this(new HttpClientParams());
  }

  public HttpClient(HttpConnectionManager paramHttpConnectionManager)
  {
    this(new HttpClientParams(), paramHttpConnectionManager);
  }

  public HttpClient(HttpClientParams paramHttpClientParams)
  {
    if (paramHttpClientParams == null)
      throw new IllegalArgumentException("Params may not be null");
    this.params = paramHttpClientParams;
    this.httpConnectionManager = null;
    Class localClass = paramHttpClientParams.getConnectionManagerClass();
    if (localClass != null);
    try
    {
      this.httpConnectionManager = ((HttpConnectionManager)localClass.newInstance());
      if (this.httpConnectionManager == null)
        this.httpConnectionManager = new SimpleHttpConnectionManager();
      if (this.httpConnectionManager != null)
        this.httpConnectionManager.getParams().setDefaults(this.params);
      return;
    }
    catch (Exception localException)
    {
      while (true)
        LOG.warn("Error instantiating connection manager class, defaulting to SimpleHttpConnectionManager", localException);
    }
  }

  public HttpClient(HttpClientParams paramHttpClientParams, HttpConnectionManager paramHttpConnectionManager)
  {
    if (paramHttpConnectionManager == null)
      throw new IllegalArgumentException("httpConnectionManager cannot be null");
    if (paramHttpClientParams == null)
      throw new IllegalArgumentException("Params may not be null");
    this.params = paramHttpClientParams;
    this.httpConnectionManager = paramHttpConnectionManager;
    if (this.httpConnectionManager != null)
      this.httpConnectionManager.getParams().setDefaults(this.params);
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

  public int executeMethod(HostConfiguration paramHostConfiguration, HttpMethod paramHttpMethod)
    throws IOException, HttpException
  {
    LOG.trace("enter HttpClient.executeMethod(HostConfiguration,HttpMethod)");
    return executeMethod(paramHostConfiguration, paramHttpMethod, null);
  }

  public int executeMethod(HostConfiguration paramHostConfiguration, HttpMethod paramHttpMethod, HttpState paramHttpState)
    throws IOException, HttpException
  {
    LOG.trace("enter HttpClient.executeMethod(HostConfiguration,HttpMethod,HttpState)");
    if (paramHttpMethod == null)
      throw new IllegalArgumentException("HttpMethod parameter may not be null");
    HostConfiguration localHostConfiguration1 = getHostConfiguration();
    if (paramHostConfiguration == null)
      paramHostConfiguration = localHostConfiguration1;
    URI localURI = paramHttpMethod.getURI();
    if ((paramHostConfiguration == localHostConfiguration1) || (localURI.isAbsoluteURI()))
    {
      HostConfiguration localHostConfiguration2 = new HostConfiguration(paramHostConfiguration);
      if (localURI.isAbsoluteURI())
        localHostConfiguration2.setHost(localURI);
      paramHostConfiguration = localHostConfiguration2;
    }
    HttpConnectionManager localHttpConnectionManager = getHttpConnectionManager();
    HttpClientParams localHttpClientParams = this.params;
    if (paramHttpState == null)
      paramHttpState = getState();
    new HttpMethodDirector(localHttpConnectionManager, paramHostConfiguration, localHttpClientParams, paramHttpState).executeMethod(paramHttpMethod);
    return paramHttpMethod.getStatusCode();
  }

  public int executeMethod(HttpMethod paramHttpMethod)
    throws IOException, HttpException
  {
    LOG.trace("enter HttpClient.executeMethod(HttpMethod)");
    return executeMethod(null, paramHttpMethod, null);
  }

  public String getHost()
  {
    return this.hostConfiguration.getHost();
  }

  public HostConfiguration getHostConfiguration()
  {
    try
    {
      HostConfiguration localHostConfiguration = this.hostConfiguration;
      return localHostConfiguration;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }

  public HttpConnectionManager getHttpConnectionManager()
  {
    try
    {
      HttpConnectionManager localHttpConnectionManager = this.httpConnectionManager;
      return localHttpConnectionManager;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }

  public HttpClientParams getParams()
  {
    return this.params;
  }

  public int getPort()
  {
    return this.hostConfiguration.getPort();
  }

  public HttpState getState()
  {
    try
    {
      HttpState localHttpState = this.state;
      return localHttpState;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }

  public boolean isStrictMode()
  {
    return false;
  }

  public void setConnectionTimeout(int paramInt)
  {
    try
    {
      this.httpConnectionManager.getParams().setConnectionTimeout(paramInt);
      return;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }

  public void setHostConfiguration(HostConfiguration paramHostConfiguration)
  {
    try
    {
      this.hostConfiguration = paramHostConfiguration;
      return;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }

  public void setHttpConnectionFactoryTimeout(long paramLong)
  {
    try
    {
      this.params.setConnectionManagerTimeout(paramLong);
      return;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }

  public void setHttpConnectionManager(HttpConnectionManager paramHttpConnectionManager)
  {
    try
    {
      this.httpConnectionManager = paramHttpConnectionManager;
      if (this.httpConnectionManager != null)
        this.httpConnectionManager.getParams().setDefaults(this.params);
      return;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }

  public void setParams(HttpClientParams paramHttpClientParams)
  {
    if (paramHttpClientParams == null)
      throw new IllegalArgumentException("Parameters may not be null");
    this.params = paramHttpClientParams;
  }

  public void setState(HttpState paramHttpState)
  {
    try
    {
      this.state = paramHttpState;
      return;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }

  public void setStrictMode(boolean paramBoolean)
  {
    if (paramBoolean);
    try
    {
      this.params.makeStrict();
      while (true)
      {
        return;
        this.params.makeLenient();
      }
    }
    finally
    {
    }
  }

  public void setTimeout(int paramInt)
  {
    try
    {
      this.params.setSoTimeout(paramInt);
      return;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     org.apache.commons.httpclient.HttpClient
 * JD-Core Version:    0.6.2
 */