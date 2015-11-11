package org.apache.commons.httpclient;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import org.apache.commons.httpclient.auth.AuthChallengeException;
import org.apache.commons.httpclient.auth.AuthChallengeParser;
import org.apache.commons.httpclient.auth.AuthChallengeProcessor;
import org.apache.commons.httpclient.auth.AuthScheme;
import org.apache.commons.httpclient.auth.AuthScope;
import org.apache.commons.httpclient.auth.AuthState;
import org.apache.commons.httpclient.auth.AuthenticationException;
import org.apache.commons.httpclient.auth.CredentialsNotAvailableException;
import org.apache.commons.httpclient.auth.CredentialsProvider;
import org.apache.commons.httpclient.auth.MalformedChallengeException;
import org.apache.commons.httpclient.params.DefaultHttpParams;
import org.apache.commons.httpclient.params.HttpClientParams;
import org.apache.commons.httpclient.params.HttpConnectionParams;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.httpclient.params.HttpParams;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

class HttpMethodDirector
{
  private static final Log LOG;
  public static final String PROXY_AUTH_CHALLENGE = "Proxy-Authenticate";
  public static final String PROXY_AUTH_RESP = "Proxy-Authorization";
  public static final String WWW_AUTH_CHALLENGE = "WWW-Authenticate";
  public static final String WWW_AUTH_RESP = "Authorization";
  static Class class$org$apache$commons$httpclient$HttpMethodDirector;
  private AuthChallengeProcessor authProcessor = null;
  private HttpConnection conn;
  private ConnectMethod connectMethod;
  private HttpConnectionManager connectionManager;
  private HostConfiguration hostConfiguration;
  private HttpClientParams params;
  private Set redirectLocations = null;
  private boolean releaseConnection = false;
  private HttpState state;

  static
  {
    Class localClass;
    if (class$org$apache$commons$httpclient$HttpMethodDirector == null)
    {
      localClass = class$("org.apache.commons.httpclient.HttpMethodDirector");
      class$org$apache$commons$httpclient$HttpMethodDirector = localClass;
    }
    while (true)
    {
      LOG = LogFactory.getLog(localClass);
      return;
      localClass = class$org$apache$commons$httpclient$HttpMethodDirector;
    }
  }

  public HttpMethodDirector(HttpConnectionManager paramHttpConnectionManager, HostConfiguration paramHostConfiguration, HttpClientParams paramHttpClientParams, HttpState paramHttpState)
  {
    this.connectionManager = paramHttpConnectionManager;
    this.hostConfiguration = paramHostConfiguration;
    this.params = paramHttpClientParams;
    this.state = paramHttpState;
    this.authProcessor = new AuthChallengeProcessor(this.params);
  }

  private void applyConnectionParams(HttpMethod paramHttpMethod)
    throws IOException
  {
    Object localObject = paramHttpMethod.getParams().getParameter("http.socket.timeout");
    if (localObject == null)
      localObject = this.conn.getParams().getParameter("http.socket.timeout");
    int i = 0;
    if (localObject != null)
      i = ((Integer)localObject).intValue();
    this.conn.setSocketTimeout(i);
  }

  private void authenticate(HttpMethod paramHttpMethod)
  {
    try
    {
      if ((this.conn.isProxied()) && (!this.conn.isSecure()))
        authenticateProxy(paramHttpMethod);
      authenticateHost(paramHttpMethod);
      return;
    }
    catch (AuthenticationException localAuthenticationException)
    {
      LOG.error(localAuthenticationException.getMessage(), localAuthenticationException);
    }
  }

  private void authenticateHost(HttpMethod paramHttpMethod)
    throws AuthenticationException
  {
    if (!cleanAuthHeaders(paramHttpMethod, "Authorization"));
    do
    {
      AuthScope localAuthScope;
      do
      {
        String str2;
        do
        {
          AuthState localAuthState;
          AuthScheme localAuthScheme;
          do
          {
            return;
            localAuthState = paramHttpMethod.getHostAuthState();
            localAuthScheme = localAuthState.getAuthScheme();
          }
          while ((localAuthScheme == null) || ((!localAuthState.isAuthRequested()) && (localAuthScheme.isConnectionBased())));
          String str1 = paramHttpMethod.getParams().getVirtualHost();
          if (str1 == null)
            str1 = this.conn.getHost();
          localAuthScope = new AuthScope(str1, this.conn.getPort(), localAuthScheme.getRealm(), localAuthScheme.getSchemeName());
          if (LOG.isDebugEnabled())
            LOG.debug("Authenticating with " + localAuthScope);
          Credentials localCredentials = this.state.getCredentials(localAuthScope);
          if (localCredentials == null)
            break;
          str2 = localAuthScheme.authenticate(localCredentials, paramHttpMethod);
        }
        while (str2 == null);
        paramHttpMethod.addRequestHeader(new Header("Authorization", str2, true));
        return;
      }
      while (!LOG.isWarnEnabled());
      LOG.warn("Required credentials not available for " + localAuthScope);
    }
    while (!paramHttpMethod.getHostAuthState().isPreemptive());
    LOG.warn("Preemptive authentication requested but no default credentials available");
  }

  private void authenticateProxy(HttpMethod paramHttpMethod)
    throws AuthenticationException
  {
    if (!cleanAuthHeaders(paramHttpMethod, "Proxy-Authorization"));
    do
    {
      AuthScope localAuthScope;
      do
      {
        String str;
        do
        {
          AuthState localAuthState;
          AuthScheme localAuthScheme;
          do
          {
            return;
            localAuthState = paramHttpMethod.getProxyAuthState();
            localAuthScheme = localAuthState.getAuthScheme();
          }
          while ((localAuthScheme == null) || ((!localAuthState.isAuthRequested()) && (localAuthScheme.isConnectionBased())));
          localAuthScope = new AuthScope(this.conn.getProxyHost(), this.conn.getProxyPort(), localAuthScheme.getRealm(), localAuthScheme.getSchemeName());
          if (LOG.isDebugEnabled())
            LOG.debug("Authenticating with " + localAuthScope);
          Credentials localCredentials = this.state.getProxyCredentials(localAuthScope);
          if (localCredentials == null)
            break;
          str = localAuthScheme.authenticate(localCredentials, paramHttpMethod);
        }
        while (str == null);
        paramHttpMethod.addRequestHeader(new Header("Proxy-Authorization", str, true));
        return;
      }
      while (!LOG.isWarnEnabled());
      LOG.warn("Required proxy credentials not available for " + localAuthScope);
    }
    while (!paramHttpMethod.getProxyAuthState().isPreemptive());
    LOG.warn("Preemptive authentication requested but no default proxy credentials available");
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

  private boolean cleanAuthHeaders(HttpMethod paramHttpMethod, String paramString)
  {
    Header[] arrayOfHeader = paramHttpMethod.getRequestHeaders(paramString);
    boolean bool = true;
    int i = 0;
    if (i >= arrayOfHeader.length)
      return bool;
    Header localHeader = arrayOfHeader[i];
    if (localHeader.isAutogenerated())
      paramHttpMethod.removeRequestHeader(localHeader);
    while (true)
    {
      i++;
      break;
      bool = false;
    }
  }

  private boolean executeConnect()
    throws IOException, HttpException
  {
    this.connectMethod = new ConnectMethod();
    this.connectMethod.getParams().setDefaults(this.hostConfiguration.getParams());
    while (true)
    {
      if (!this.conn.isOpen())
        this.conn.open();
      if ((this.params.isAuthenticationPreemptive()) || (this.state.isAuthenticationPreemptive()))
      {
        LOG.debug("Preemptively sending default basic credentials");
        this.connectMethod.getProxyAuthState().setPreemptive();
        this.connectMethod.getProxyAuthState().setAuthAttempted(true);
      }
      try
      {
        authenticateProxy(this.connectMethod);
        applyConnectionParams(this.connectMethod);
        this.connectMethod.execute(this.state, this.conn);
        int i = this.connectMethod.getStatusCode();
        AuthState localAuthState = this.connectMethod.getProxyAuthState();
        if (i == 407)
        {
          bool1 = true;
          localAuthState.setAuthRequested(bool1);
          boolean bool2 = localAuthState.isAuthRequested();
          int j = 0;
          if (bool2)
          {
            boolean bool3 = processAuthenticationResponse(this.connectMethod);
            j = 0;
            if (bool3)
              j = 1;
          }
          if (j != 0)
            break label255;
          if ((i < 200) || (i >= 300))
            break;
          this.conn.tunnelCreated();
          this.connectMethod = null;
          return true;
        }
      }
      catch (AuthenticationException localAuthenticationException)
      {
        while (true)
        {
          LOG.error(localAuthenticationException.getMessage(), localAuthenticationException);
          continue;
          boolean bool1 = false;
        }
      }
      label255: if (this.connectMethod.getResponseBodyAsStream() != null)
        this.connectMethod.getResponseBodyAsStream().close();
    }
    return false;
  }

  private void executeWithRetry(HttpMethod paramHttpMethod)
    throws IOException, HttpException
  {
    int i = 0;
    while (true)
    {
      i++;
      try
      {
        if (LOG.isTraceEnabled())
          LOG.trace("Attempt number " + i + " to process request");
        if (this.conn.getParams().isStaleCheckingEnabled())
          this.conn.closeIfStale();
        if (!this.conn.isOpen())
        {
          this.conn.open();
          if ((this.conn.isProxied()) && (this.conn.isSecure()) && (!(paramHttpMethod instanceof ConnectMethod)) && (!executeConnect()))
            return;
        }
        applyConnectionParams(paramHttpMethod);
        paramHttpMethod.execute(this.state, this.conn);
        return;
      }
      catch (HttpException localHttpException)
      {
        try
        {
          throw localHttpException;
        }
        catch (IOException localIOException2)
        {
          if (this.conn.isOpen())
          {
            LOG.debug("Closing the connection.");
            this.conn.close();
          }
          this.releaseConnection = true;
          throw localIOException2;
        }
      }
      catch (IOException localIOException1)
      {
        LOG.debug("Closing the connection.");
        this.conn.close();
        if ((paramHttpMethod instanceof HttpMethodBase))
        {
          MethodRetryHandler localMethodRetryHandler = ((HttpMethodBase)paramHttpMethod).getMethodRetryHandler();
          if ((localMethodRetryHandler != null) && (!localMethodRetryHandler.retryMethod(paramHttpMethod, this.conn, new HttpRecoverableException(localIOException1.getMessage()), i, paramHttpMethod.isRequestSent())))
          {
            LOG.debug("Method retry handler returned false. Automatic recovery will not be attempted");
            throw localIOException1;
          }
        }
      }
      catch (RuntimeException localRuntimeException)
      {
        if (this.conn.isOpen)
        {
          LOG.debug("Closing the connection.");
          this.conn.close();
        }
        this.releaseConnection = true;
        throw localRuntimeException;
      }
      Object localObject = (HttpMethodRetryHandler)paramHttpMethod.getParams().getParameter("http.method.retry-handler");
      if (localObject == null)
        localObject = new DefaultHttpMethodRetryHandler();
      if (!((HttpMethodRetryHandler)localObject).retryMethod(paramHttpMethod, localIOException1, i))
      {
        LOG.debug("Method retry handler returned false. Automatic recovery will not be attempted");
        throw localIOException1;
      }
      if (LOG.isInfoEnabled())
        LOG.info("I/O exception (" + localIOException1.getClass().getName() + ") caught when processing request: " + localIOException1.getMessage());
      if (LOG.isDebugEnabled())
        LOG.debug(localIOException1.getMessage(), localIOException1);
      LOG.info("Retrying request");
    }
  }

  private void fakeResponse(HttpMethod paramHttpMethod)
    throws IOException, HttpException
  {
    LOG.debug("CONNECT failed, fake the response for the original method");
    if ((paramHttpMethod instanceof HttpMethodBase))
    {
      ((HttpMethodBase)paramHttpMethod).fakeResponse(this.connectMethod.getStatusLine(), this.connectMethod.getResponseHeaderGroup(), this.connectMethod.getResponseBodyAsStream());
      paramHttpMethod.getProxyAuthState().setAuthScheme(this.connectMethod.getProxyAuthState().getAuthScheme());
      this.connectMethod = null;
      return;
    }
    this.releaseConnection = true;
    LOG.warn("Unable to fake response on method as it is not derived from HttpMethodBase.");
  }

  private boolean isAuthenticationNeeded(HttpMethod paramHttpMethod)
  {
    AuthState localAuthState1 = paramHttpMethod.getHostAuthState();
    boolean bool1;
    AuthState localAuthState2;
    if (paramHttpMethod.getStatusCode() == 401)
    {
      bool1 = true;
      localAuthState1.setAuthRequested(bool1);
      localAuthState2 = paramHttpMethod.getProxyAuthState();
      if (paramHttpMethod.getStatusCode() != 407)
        break label118;
    }
    label118: for (boolean bool2 = true; ; bool2 = false)
    {
      localAuthState2.setAuthRequested(bool2);
      boolean bool3;
      if (!paramHttpMethod.getHostAuthState().isAuthRequested())
      {
        boolean bool4 = paramHttpMethod.getProxyAuthState().isAuthRequested();
        bool3 = false;
        if (!bool4);
      }
      else
      {
        LOG.debug("Authorization required");
        if (!paramHttpMethod.getDoAuthentication())
          break label124;
        bool3 = true;
      }
      return bool3;
      bool1 = false;
      break;
    }
    label124: LOG.info("Authentication requested but doAuthentication is disabled");
    return false;
  }

  private boolean isRedirectNeeded(HttpMethod paramHttpMethod)
  {
    switch (paramHttpMethod.getStatusCode())
    {
    case 304:
    case 305:
    case 306:
    default:
      return false;
    case 301:
    case 302:
    case 303:
    case 307:
    }
    LOG.debug("Redirect required");
    if (paramHttpMethod.getFollowRedirects())
      return true;
    LOG.info("Redirect requested but followRedirects is disabled");
    return false;
  }

  private boolean processAuthenticationResponse(HttpMethod paramHttpMethod)
  {
    LOG.trace("enter HttpMethodBase.processAuthenticationResponse(HttpState, HttpConnection)");
    try
    {
      switch (paramHttpMethod.getStatusCode())
      {
      case 401:
        return processWWWAuthChallenge(paramHttpMethod);
      case 407:
        boolean bool = processProxyAuthChallenge(paramHttpMethod);
        return bool;
      }
    }
    catch (Exception localException)
    {
      if (LOG.isErrorEnabled())
        LOG.error(localException.getMessage(), localException);
    }
    return false;
  }

  private boolean processProxyAuthChallenge(HttpMethod paramHttpMethod)
    throws MalformedChallengeException, AuthenticationException
  {
    AuthState localAuthState = paramHttpMethod.getProxyAuthState();
    Map localMap = AuthChallengeParser.parseChallenges(paramHttpMethod.getResponseHeaders("Proxy-Authenticate"));
    if (localMap.isEmpty())
      LOG.debug("Proxy authentication challenge(s) not found");
    AuthScope localAuthScope;
    label255: 
    do
    {
      AuthScheme localAuthScheme1;
      while (true)
      {
        return false;
        try
        {
          AuthScheme localAuthScheme2 = this.authProcessor.processChallenge(localAuthState, localMap);
          localAuthScheme1 = localAuthScheme2;
          if (localAuthScheme1 != null)
          {
            localAuthScope = new AuthScope(this.conn.getProxyHost(), this.conn.getProxyPort(), localAuthScheme1.getRealm(), localAuthScheme1.getSchemeName());
            if (LOG.isDebugEnabled())
              LOG.debug("Proxy authentication scope: " + localAuthScope);
            if ((!localAuthState.isAuthAttempted()) || (!localAuthScheme1.isComplete()))
              break label255;
            if (promptForProxyCredentials(localAuthScheme1, paramHttpMethod.getParams(), localAuthScope) == null)
            {
              if (!LOG.isInfoEnabled())
                continue;
              LOG.info("Failure authenticating with " + localAuthScope);
              return false;
            }
          }
        }
        catch (AuthChallengeException localAuthChallengeException)
        {
          while (true)
          {
            boolean bool = LOG.isWarnEnabled();
            localAuthScheme1 = null;
            if (bool)
            {
              LOG.warn(localAuthChallengeException.getMessage());
              localAuthScheme1 = null;
            }
          }
        }
      }
      return true;
      localAuthState.setAuthAttempted(true);
      Credentials localCredentials = this.state.getProxyCredentials(localAuthScope);
      if (localCredentials == null)
        localCredentials = promptForProxyCredentials(localAuthScheme1, paramHttpMethod.getParams(), localAuthScope);
      if (localCredentials != null)
        break;
    }
    while (!LOG.isInfoEnabled());
    LOG.info("No credentials available for " + localAuthScope);
    return false;
    return true;
  }

  // ERROR //
  private boolean processRedirectResponse(HttpMethod paramHttpMethod)
    throws RedirectException
  {
    // Byte code:
    //   0: aload_1
    //   1: ldc_w 525
    //   4: invokeinterface 529 2 0
    //   9: astore_2
    //   10: aload_2
    //   11: ifnonnull +44 -> 55
    //   14: getstatic 57	org/apache/commons/httpclient/HttpMethodDirector:LOG	Lorg/apache/commons/logging/Log;
    //   17: new 192	java/lang/StringBuffer
    //   20: dup
    //   21: invokespecial 193	java/lang/StringBuffer:<init>	()V
    //   24: ldc_w 531
    //   27: invokevirtual 199	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
    //   30: aload_1
    //   31: invokeinterface 450 1 0
    //   36: invokevirtual 358	java/lang/StringBuffer:append	(I)Ljava/lang/StringBuffer;
    //   39: ldc_w 533
    //   42: invokevirtual 199	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
    //   45: invokevirtual 205	java/lang/StringBuffer:toString	()Ljava/lang/String;
    //   48: invokeinterface 535 2 0
    //   53: iconst_0
    //   54: ireturn
    //   55: aload_2
    //   56: invokevirtual 540	org/apache/commons/httpclient/NameValuePair:getValue	()Ljava/lang/String;
    //   59: astore_3
    //   60: getstatic 57	org/apache/commons/httpclient/HttpMethodDirector:LOG	Lorg/apache/commons/logging/Log;
    //   63: invokeinterface 190 1 0
    //   68: ifeq +37 -> 105
    //   71: getstatic 57	org/apache/commons/httpclient/HttpMethodDirector:LOG	Lorg/apache/commons/logging/Log;
    //   74: new 192	java/lang/StringBuffer
    //   77: dup
    //   78: invokespecial 193	java/lang/StringBuffer:<init>	()V
    //   81: ldc_w 542
    //   84: invokevirtual 199	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
    //   87: aload_3
    //   88: invokevirtual 199	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
    //   91: ldc_w 544
    //   94: invokevirtual 199	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
    //   97: invokevirtual 205	java/lang/StringBuffer:toString	()Ljava/lang/String;
    //   100: invokeinterface 209 2 0
    //   105: new 546	org/apache/commons/httpclient/URI
    //   108: dup
    //   109: aload_0
    //   110: getfield 100	org/apache/commons/httpclient/HttpMethodDirector:conn	Lorg/apache/commons/httpclient/HttpConnection;
    //   113: invokevirtual 550	org/apache/commons/httpclient/HttpConnection:getProtocol	()Lorg/apache/commons/httpclient/protocol/Protocol;
    //   116: invokevirtual 555	org/apache/commons/httpclient/protocol/Protocol:getScheme	()Ljava/lang/String;
    //   119: aconst_null
    //   120: aload_0
    //   121: getfield 100	org/apache/commons/httpclient/HttpMethodDirector:conn	Lorg/apache/commons/httpclient/HttpConnection;
    //   124: invokevirtual 173	org/apache/commons/httpclient/HttpConnection:getHost	()Ljava/lang/String;
    //   127: aload_0
    //   128: getfield 100	org/apache/commons/httpclient/HttpMethodDirector:conn	Lorg/apache/commons/httpclient/HttpConnection;
    //   131: invokevirtual 178	org/apache/commons/httpclient/HttpConnection:getPort	()I
    //   134: aload_1
    //   135: invokeinterface 558 1 0
    //   140: invokespecial 561	org/apache/commons/httpclient/URI:<init>	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;ILjava/lang/String;)V
    //   143: astore 4
    //   145: new 546	org/apache/commons/httpclient/URI
    //   148: dup
    //   149: aload_3
    //   150: iconst_1
    //   151: invokespecial 564	org/apache/commons/httpclient/URI:<init>	(Ljava/lang/String;Z)V
    //   154: astore 5
    //   156: aload 5
    //   158: invokevirtual 567	org/apache/commons/httpclient/URI:isRelativeURI	()Z
    //   161: ifeq +199 -> 360
    //   164: aload_0
    //   165: getfield 73	org/apache/commons/httpclient/HttpMethodDirector:params	Lorg/apache/commons/httpclient/params/HttpClientParams;
    //   168: ldc_w 569
    //   171: invokevirtual 573	org/apache/commons/httpclient/params/DefaultHttpParams:isParameterTrue	(Ljava/lang/String;)Z
    //   174: ifeq +39 -> 213
    //   177: getstatic 57	org/apache/commons/httpclient/HttpMethodDirector:LOG	Lorg/apache/commons/logging/Log;
    //   180: new 192	java/lang/StringBuffer
    //   183: dup
    //   184: invokespecial 193	java/lang/StringBuffer:<init>	()V
    //   187: ldc_w 575
    //   190: invokevirtual 199	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
    //   193: aload_3
    //   194: invokevirtual 199	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
    //   197: ldc_w 577
    //   200: invokevirtual 199	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
    //   203: invokevirtual 205	java/lang/StringBuffer:toString	()Ljava/lang/String;
    //   206: invokeinterface 235 2 0
    //   211: iconst_0
    //   212: ireturn
    //   213: getstatic 57	org/apache/commons/httpclient/HttpMethodDirector:LOG	Lorg/apache/commons/logging/Log;
    //   216: ldc_w 579
    //   219: invokeinterface 209 2 0
    //   224: new 546	org/apache/commons/httpclient/URI
    //   227: dup
    //   228: aload 4
    //   230: aload 5
    //   232: invokespecial 582	org/apache/commons/httpclient/URI:<init>	(Lorg/apache/commons/httpclient/URI;Lorg/apache/commons/httpclient/URI;)V
    //   235: astore 7
    //   237: aload_1
    //   238: aload 7
    //   240: invokeinterface 586 2 0
    //   245: aload_0
    //   246: getfield 71	org/apache/commons/httpclient/HttpMethodDirector:hostConfiguration	Lorg/apache/commons/httpclient/HostConfiguration;
    //   249: aload 7
    //   251: invokevirtual 589	org/apache/commons/httpclient/HostConfiguration:setHost	(Lorg/apache/commons/httpclient/URI;)V
    //   254: aload_0
    //   255: getfield 73	org/apache/commons/httpclient/HttpMethodDirector:params	Lorg/apache/commons/httpclient/params/HttpClientParams;
    //   258: ldc_w 591
    //   261: invokevirtual 594	org/apache/commons/httpclient/params/DefaultHttpParams:isParameterFalse	(Ljava/lang/String;)Z
    //   264: ifeq +158 -> 422
    //   267: aload_0
    //   268: getfield 67	org/apache/commons/httpclient/HttpMethodDirector:redirectLocations	Ljava/util/Set;
    //   271: ifnonnull +14 -> 285
    //   274: aload_0
    //   275: new 596	java/util/HashSet
    //   278: dup
    //   279: invokespecial 597	java/util/HashSet:<init>	()V
    //   282: putfield 67	org/apache/commons/httpclient/HttpMethodDirector:redirectLocations	Ljava/util/Set;
    //   285: aload_0
    //   286: getfield 67	org/apache/commons/httpclient/HttpMethodDirector:redirectLocations	Ljava/util/Set;
    //   289: aload 4
    //   291: invokeinterface 603 2 0
    //   296: pop
    //   297: aload 7
    //   299: invokevirtual 606	org/apache/commons/httpclient/URI:hasQuery	()Z
    //   302: ifeq +9 -> 311
    //   305: aload 7
    //   307: aconst_null
    //   308: invokevirtual 609	org/apache/commons/httpclient/URI:setQuery	(Ljava/lang/String;)V
    //   311: aload_0
    //   312: getfield 67	org/apache/commons/httpclient/HttpMethodDirector:redirectLocations	Ljava/util/Set;
    //   315: aload 7
    //   317: invokeinterface 612 2 0
    //   322: ifeq +100 -> 422
    //   325: new 614	org/apache/commons/httpclient/CircularRedirectException
    //   328: dup
    //   329: new 192	java/lang/StringBuffer
    //   332: dup
    //   333: invokespecial 193	java/lang/StringBuffer:<init>	()V
    //   336: ldc_w 616
    //   339: invokevirtual 199	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
    //   342: aload 7
    //   344: invokevirtual 202	java/lang/StringBuffer:append	(Ljava/lang/Object;)Ljava/lang/StringBuffer;
    //   347: ldc_w 544
    //   350: invokevirtual 199	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
    //   353: invokevirtual 205	java/lang/StringBuffer:toString	()Ljava/lang/String;
    //   356: invokespecial 617	org/apache/commons/httpclient/CircularRedirectException:<init>	(Ljava/lang/String;)V
    //   359: athrow
    //   360: aload_1
    //   361: invokeinterface 90 1 0
    //   366: aload_0
    //   367: getfield 73	org/apache/commons/httpclient/HttpMethodDirector:params	Lorg/apache/commons/httpclient/params/HttpClientParams;
    //   370: invokevirtual 297	org/apache/commons/httpclient/params/DefaultHttpParams:setDefaults	(Lorg/apache/commons/httpclient/params/HttpParams;)V
    //   373: aload 5
    //   375: astore 7
    //   377: goto -140 -> 237
    //   380: astore 11
    //   382: getstatic 57	org/apache/commons/httpclient/HttpMethodDirector:LOG	Lorg/apache/commons/logging/Log;
    //   385: new 192	java/lang/StringBuffer
    //   388: dup
    //   389: invokespecial 193	java/lang/StringBuffer:<init>	()V
    //   392: ldc_w 619
    //   395: invokevirtual 199	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
    //   398: aload_3
    //   399: invokevirtual 199	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
    //   402: ldc_w 621
    //   405: invokevirtual 199	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
    //   408: invokevirtual 205	java/lang/StringBuffer:toString	()Ljava/lang/String;
    //   411: invokeinterface 235 2 0
    //   416: iconst_0
    //   417: ireturn
    //   418: astore 10
    //   420: iconst_0
    //   421: ireturn
    //   422: getstatic 57	org/apache/commons/httpclient/HttpMethodDirector:LOG	Lorg/apache/commons/logging/Log;
    //   425: invokeinterface 190 1 0
    //   430: ifeq +49 -> 479
    //   433: getstatic 57	org/apache/commons/httpclient/HttpMethodDirector:LOG	Lorg/apache/commons/logging/Log;
    //   436: new 192	java/lang/StringBuffer
    //   439: dup
    //   440: invokespecial 193	java/lang/StringBuffer:<init>	()V
    //   443: ldc_w 623
    //   446: invokevirtual 199	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
    //   449: aload 4
    //   451: invokevirtual 626	org/apache/commons/httpclient/URI:getEscapedURI	()Ljava/lang/String;
    //   454: invokevirtual 199	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
    //   457: ldc_w 628
    //   460: invokevirtual 199	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
    //   463: aload 7
    //   465: invokevirtual 626	org/apache/commons/httpclient/URI:getEscapedURI	()Ljava/lang/String;
    //   468: invokevirtual 199	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
    //   471: invokevirtual 205	java/lang/StringBuffer:toString	()Ljava/lang/String;
    //   474: invokeinterface 209 2 0
    //   479: aload_1
    //   480: invokeinterface 151 1 0
    //   485: invokevirtual 631	org/apache/commons/httpclient/auth/AuthState:invalidate	()V
    //   488: iconst_1
    //   489: ireturn
    //   490: astore 8
    //   492: goto -110 -> 382
    //   495: astore 6
    //   497: goto -115 -> 382
    //
    // Exception table:
    //   from	to	target	type
    //   105	145	380	org/apache/commons/httpclient/URIException
    //   297	311	418	org/apache/commons/httpclient/URIException
    //   145	156	490	org/apache/commons/httpclient/URIException
    //   237	254	490	org/apache/commons/httpclient/URIException
    //   156	211	495	org/apache/commons/httpclient/URIException
    //   213	237	495	org/apache/commons/httpclient/URIException
    //   360	373	495	org/apache/commons/httpclient/URIException
  }

  private boolean processWWWAuthChallenge(HttpMethod paramHttpMethod)
    throws MalformedChallengeException, AuthenticationException
  {
    AuthState localAuthState = paramHttpMethod.getHostAuthState();
    Map localMap = AuthChallengeParser.parseChallenges(paramHttpMethod.getResponseHeaders("WWW-Authenticate"));
    if (localMap.isEmpty())
      LOG.debug("Authentication challenge(s) not found");
    AuthScope localAuthScope;
    label275: 
    do
    {
      AuthScheme localAuthScheme1;
      while (true)
      {
        return false;
        try
        {
          AuthScheme localAuthScheme2 = this.authProcessor.processChallenge(localAuthState, localMap);
          localAuthScheme1 = localAuthScheme2;
          if (localAuthScheme1 != null)
          {
            String str = paramHttpMethod.getParams().getVirtualHost();
            if (str == null)
              str = this.conn.getHost();
            localAuthScope = new AuthScope(str, this.conn.getPort(), localAuthScheme1.getRealm(), localAuthScheme1.getSchemeName());
            if (LOG.isDebugEnabled())
              LOG.debug("Authentication scope: " + localAuthScope);
            if ((!localAuthState.isAuthAttempted()) || (!localAuthScheme1.isComplete()))
              break label275;
            if (promptForCredentials(localAuthScheme1, paramHttpMethod.getParams(), localAuthScope) == null)
            {
              if (!LOG.isInfoEnabled())
                continue;
              LOG.info("Failure authenticating with " + localAuthScope);
              return false;
            }
          }
        }
        catch (AuthChallengeException localAuthChallengeException)
        {
          while (true)
          {
            boolean bool = LOG.isWarnEnabled();
            localAuthScheme1 = null;
            if (bool)
            {
              LOG.warn(localAuthChallengeException.getMessage());
              localAuthScheme1 = null;
            }
          }
        }
      }
      return true;
      localAuthState.setAuthAttempted(true);
      Credentials localCredentials = this.state.getCredentials(localAuthScope);
      if (localCredentials == null)
        localCredentials = promptForCredentials(localAuthScheme1, paramHttpMethod.getParams(), localAuthScope);
      if (localCredentials != null)
        break;
    }
    while (!LOG.isInfoEnabled());
    LOG.info("No credentials available for " + localAuthScope);
    return false;
    return true;
  }

  private Credentials promptForCredentials(AuthScheme paramAuthScheme, HttpParams paramHttpParams, AuthScope paramAuthScope)
  {
    LOG.debug("Credentials required");
    CredentialsProvider localCredentialsProvider = (CredentialsProvider)paramHttpParams.getParameter("http.authentication.credential-provider");
    if (localCredentialsProvider != null)
      try
      {
        Credentials localCredentials2 = localCredentialsProvider.getCredentials(paramAuthScheme, paramAuthScope.getHost(), paramAuthScope.getPort(), false);
        localCredentials1 = localCredentials2;
        if (localCredentials1 != null)
        {
          this.state.setCredentials(paramAuthScope, localCredentials1);
          if (LOG.isDebugEnabled())
            LOG.debug(paramAuthScope + " new credentials given");
        }
        return localCredentials1;
      }
      catch (CredentialsNotAvailableException localCredentialsNotAvailableException)
      {
        while (true)
        {
          LOG.warn(localCredentialsNotAvailableException.getMessage());
          Credentials localCredentials1 = null;
        }
      }
    LOG.debug("Credentials provider not available");
    return null;
  }

  private Credentials promptForProxyCredentials(AuthScheme paramAuthScheme, HttpParams paramHttpParams, AuthScope paramAuthScope)
  {
    LOG.debug("Proxy credentials required");
    CredentialsProvider localCredentialsProvider = (CredentialsProvider)paramHttpParams.getParameter("http.authentication.credential-provider");
    if (localCredentialsProvider != null)
      try
      {
        Credentials localCredentials2 = localCredentialsProvider.getCredentials(paramAuthScheme, paramAuthScope.getHost(), paramAuthScope.getPort(), true);
        localCredentials1 = localCredentials2;
        if (localCredentials1 != null)
        {
          this.state.setProxyCredentials(paramAuthScope, localCredentials1);
          if (LOG.isDebugEnabled())
            LOG.debug(paramAuthScope + " new credentials given");
        }
        return localCredentials1;
      }
      catch (CredentialsNotAvailableException localCredentialsNotAvailableException)
      {
        while (true)
        {
          LOG.warn(localCredentialsNotAvailableException.getMessage());
          Credentials localCredentials1 = null;
        }
      }
    LOG.debug("Proxy credentials provider not available");
    return null;
  }

  public void executeMethod(HttpMethod paramHttpMethod)
    throws IOException, HttpException
  {
    if (paramHttpMethod == null)
      throw new IllegalArgumentException("Method may not be null");
    this.hostConfiguration.getParams().setDefaults(this.params);
    paramHttpMethod.getParams().setDefaults(this.hostConfiguration.getParams());
    Collection localCollection = (Collection)this.hostConfiguration.getParams().getParameter("http.default-headers");
    Iterator localIterator;
    if (localCollection != null)
    {
      localIterator = localCollection.iterator();
      if (localIterator.hasNext())
        break label338;
    }
    while (true)
    {
      int i;
      int j;
      label338: int k;
      try
      {
        i = this.params.getIntParameter("http.protocol.max-redirects", 100);
        j = 0;
        if ((this.conn != null) && (!this.hostConfiguration.hostEquals(this.conn)))
        {
          this.conn.setLocked(false);
          this.conn.releaseConnection();
          this.conn = null;
        }
        if (this.conn == null)
        {
          this.conn = this.connectionManager.getConnectionWithTimeout(this.hostConfiguration, this.params.getConnectionManagerTimeout());
          this.conn.setLocked(true);
          if ((this.params.isAuthenticationPreemptive()) || (this.state.isAuthenticationPreemptive()))
          {
            LOG.debug("Preemptively sending default basic credentials");
            paramHttpMethod.getHostAuthState().setPreemptive();
            paramHttpMethod.getHostAuthState().setAuthAttempted(true);
            if ((this.conn.isProxied()) && (!this.conn.isSecure()))
            {
              paramHttpMethod.getProxyAuthState().setPreemptive();
              paramHttpMethod.getProxyAuthState().setAuthAttempted(true);
            }
          }
        }
        authenticate(paramHttpMethod);
        executeWithRetry(paramHttpMethod);
        if (this.connectMethod != null)
        {
          fakeResponse(paramHttpMethod);
          return;
          paramHttpMethod.addRequestHeader((Header)localIterator.next());
          break;
        }
        boolean bool1 = isRedirectNeeded(paramHttpMethod);
        k = 0;
        if (!bool1)
          break label545;
        boolean bool2 = processRedirectResponse(paramHttpMethod);
        k = 0;
        if (!bool2)
          break label545;
        k = 1;
        j++;
        if (j >= i)
        {
          LOG.error("Narrowly avoided an infinite loop in execute");
          throw new RedirectException("Maximum redirects (" + i + ") exceeded");
        }
      }
      finally
      {
        if (this.conn != null)
          this.conn.setLocked(false);
        if (((this.releaseConnection) || (paramHttpMethod.getResponseBodyAsStream() == null)) && (this.conn != null))
          this.conn.releaseConnection();
      }
      if (LOG.isDebugEnabled())
        LOG.debug("Execute redirect " + j + " of " + i);
      label545: if ((isAuthenticationNeeded(paramHttpMethod)) && (processAuthenticationResponse(paramHttpMethod)))
      {
        LOG.debug("Retry authentication");
        k = 1;
      }
      if (k != 0)
        if (paramHttpMethod.getResponseBodyAsStream() != null)
          paramHttpMethod.getResponseBodyAsStream().close();
    }
  }

  public HttpConnectionManager getConnectionManager()
  {
    return this.connectionManager;
  }

  public HostConfiguration getHostConfiguration()
  {
    return this.hostConfiguration;
  }

  public HttpParams getParams()
  {
    return this.params;
  }

  public HttpState getState()
  {
    return this.state;
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     org.apache.commons.httpclient.HttpMethodDirector
 * JD-Core Version:    0.6.2
 */