package org.apache.commons.httpclient;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InterruptedIOException;
import java.util.Collection;
import org.apache.commons.httpclient.auth.AuthState;
import org.apache.commons.httpclient.cookie.CookiePolicy;
import org.apache.commons.httpclient.cookie.CookieSpec;
import org.apache.commons.httpclient.cookie.MalformedCookieException;
import org.apache.commons.httpclient.params.DefaultHttpParams;
import org.apache.commons.httpclient.params.HttpConnectionParams;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.httpclient.protocol.Protocol;
import org.apache.commons.httpclient.util.EncodingUtil;
import org.apache.commons.httpclient.util.ExceptionUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public abstract class HttpMethodBase
  implements HttpMethod
{
  private static final int DEFAULT_INITIAL_BUFFER_SIZE = 4096;
  private static final Log LOG;
  private static final int RESPONSE_WAIT_TIME_MS = 3000;
  static Class class$org$apache$commons$httpclient$HttpMethodBase;
  private transient boolean aborted = false;
  private boolean connectionCloseForced = false;
  private CookieSpec cookiespec = null;
  private boolean doAuthentication = true;
  private HttpVersion effectiveVersion = null;
  private boolean followRedirects = false;
  private AuthState hostAuthState = new AuthState();
  private HttpHost httphost = null;
  private MethodRetryHandler methodRetryHandler;
  private HttpMethodParams params = new HttpMethodParams();
  private String path = null;
  private AuthState proxyAuthState = new AuthState();
  private String queryString = null;
  private int recoverableExceptionCount = 0;
  private HeaderGroup requestHeaders = new HeaderGroup();
  private boolean requestSent = false;
  private byte[] responseBody = null;
  private HttpConnection responseConnection = null;
  private HeaderGroup responseHeaders = new HeaderGroup();
  private InputStream responseStream = null;
  private HeaderGroup responseTrailerHeaders = new HeaderGroup();
  private StatusLine statusLine = null;
  private boolean used = false;

  static
  {
    Class localClass;
    if (class$org$apache$commons$httpclient$HttpMethodBase == null)
    {
      localClass = class$("org.apache.commons.httpclient.HttpMethodBase");
      class$org$apache$commons$httpclient$HttpMethodBase = localClass;
    }
    while (true)
    {
      LOG = LogFactory.getLog(localClass);
      return;
      localClass = class$org$apache$commons$httpclient$HttpMethodBase;
    }
  }

  public HttpMethodBase()
  {
  }

  public HttpMethodBase(String paramString)
    throws IllegalArgumentException, IllegalStateException
  {
    if (paramString != null);
    while (true)
    {
      try
      {
        if (!paramString.equals(""))
        {
          setURI(new URI(paramString, true));
          return;
        }
      }
      catch (URIException localURIException)
      {
        throw new IllegalArgumentException("Invalid uri '" + paramString + "': " + localURIException.getMessage());
      }
      paramString = "/";
    }
  }

  private static boolean canResponseHaveBody(int paramInt)
  {
    LOG.trace("enter HttpMethodBase.canResponseHaveBody(int)");
    boolean bool = true;
    if (((paramInt >= 100) && (paramInt <= 199)) || (paramInt == 204) || (paramInt == 304))
      bool = false;
    return bool;
  }

  private void checkExecuteConditions(HttpState paramHttpState, HttpConnection paramHttpConnection)
    throws HttpException
  {
    if (paramHttpState == null)
      throw new IllegalArgumentException("HttpState parameter may not be null");
    if (paramHttpConnection == null)
      throw new IllegalArgumentException("HttpConnection parameter may not be null");
    if (this.aborted)
      throw new IllegalStateException("Method has been aborted");
    if (!validate())
      throw new ProtocolException("HttpMethodBase object not valid");
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

  private void ensureConnectionRelease()
  {
    if (this.responseConnection != null)
    {
      this.responseConnection.releaseConnection();
      this.responseConnection = null;
    }
  }

  protected static String generateRequestLine(HttpConnection paramHttpConnection, String paramString1, String paramString2, String paramString3, String paramString4)
  {
    LOG.trace("enter HttpMethodBase.generateRequestLine(HttpConnection, String, String, String, String)");
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append(paramString1);
    localStringBuffer.append(" ");
    if (!paramHttpConnection.isTransparent())
    {
      Protocol localProtocol = paramHttpConnection.getProtocol();
      localStringBuffer.append(localProtocol.getScheme().toLowerCase());
      localStringBuffer.append("://");
      localStringBuffer.append(paramHttpConnection.getHost());
      if ((paramHttpConnection.getPort() != -1) && (paramHttpConnection.getPort() != localProtocol.getDefaultPort()))
      {
        localStringBuffer.append(":");
        localStringBuffer.append(paramHttpConnection.getPort());
      }
    }
    if (paramString2 == null)
      localStringBuffer.append("/");
    while (true)
    {
      if (paramString3 != null)
      {
        if (paramString3.indexOf("?") != 0)
          localStringBuffer.append("?");
        localStringBuffer.append(paramString3);
      }
      localStringBuffer.append(" ");
      localStringBuffer.append(paramString4);
      localStringBuffer.append("\r\n");
      return localStringBuffer.toString();
      if ((!paramHttpConnection.isTransparent()) && (!paramString2.startsWith("/")))
        localStringBuffer.append("/");
      localStringBuffer.append(paramString2);
    }
  }

  private CookieSpec getCookieSpec(HttpState paramHttpState)
  {
    int i;
    if (this.cookiespec == null)
    {
      i = paramHttpState.getCookiePolicy();
      if (i != -1)
        break label58;
    }
    label58: for (this.cookiespec = CookiePolicy.getCookieSpec(this.params.getCookiePolicy()); ; this.cookiespec = CookiePolicy.getSpecByPolicy(i))
    {
      this.cookiespec.setValidDateFormats((Collection)this.params.getParameter("http.dateparser.patterns"));
      return this.cookiespec;
    }
  }

  private String getRequestLine(HttpConnection paramHttpConnection)
  {
    return generateRequestLine(paramHttpConnection, getName(), getPath(), getQueryString(), this.effectiveVersion.toString());
  }

  private InputStream readResponseBody(HttpConnection paramHttpConnection)
    throws HttpException, IOException
  {
    LOG.trace("enter HttpMethodBase.readResponseBody(HttpConnection)");
    this.responseBody = null;
    Object localObject1 = paramHttpConnection.getResponseInputStream();
    if (Wire.CONTENT_WIRE.enabled())
      localObject1 = new WireLogInputStream((InputStream)localObject1, Wire.CONTENT_WIRE);
    boolean bool = canResponseHaveBody(this.statusLine.getStatusCode());
    Header localHeader1 = this.responseHeaders.getFirstHeader("Transfer-Encoding");
    Object localObject2;
    if (localHeader1 != null)
    {
      String str2 = localHeader1.getValue();
      if ((!"chunked".equalsIgnoreCase(str2)) && (!"identity".equalsIgnoreCase(str2)) && (LOG.isWarnEnabled()))
        LOG.warn("Unsupported transfer encoding: " + str2);
      HeaderElement[] arrayOfHeaderElement = localHeader1.getElements();
      int i = arrayOfHeaderElement.length;
      if ((i > 0) && ("chunked".equalsIgnoreCase(arrayOfHeaderElement[(i - 1)].getName())))
        if (paramHttpConnection.isResponseAvailable(paramHttpConnection.getParams().getSoTimeout()))
        {
          localObject2 = new ChunkedInputStream((InputStream)localObject1, this);
          if (bool)
            break label403;
        }
    }
    label403: for (Object localObject3 = null; ; localObject3 = localObject2)
    {
      if (localObject3 != null)
      {
        return new AutoCloseInputStream((InputStream)localObject3, new HttpMethodBase.1(this));
        if (getParams().isParameterTrue("http.protocol.strict-transfer-encoding"))
          throw new ProtocolException("Chunk-encoded body declared but not sent");
        LOG.warn("Chunk-encoded body missing");
        localObject2 = null;
        break;
        LOG.info("Response content is not chunk-encoded");
        setConnectionCloseForced(true);
        localObject2 = localObject1;
        break;
        long l = getResponseContentLength();
        if (l == -1L)
        {
          if ((bool) && (this.effectiveVersion.greaterEquals(HttpVersion.HTTP_1_1)))
          {
            Header localHeader2 = this.responseHeaders.getFirstHeader("Connection");
            String str1 = null;
            if (localHeader2 != null)
              str1 = localHeader2.getValue();
            if (!"close".equalsIgnoreCase(str1))
            {
              LOG.info("Response content length is not known");
              setConnectionCloseForced(true);
            }
          }
          localObject2 = localObject1;
          break;
        }
        localObject2 = new ContentLengthInputStream((InputStream)localObject1, l);
        break;
      }
      return localObject3;
    }
  }

  private boolean responseAvailable()
  {
    return (this.responseBody != null) || (this.responseStream != null);
  }

  public void abort()
  {
    if (this.aborted);
    HttpConnection localHttpConnection;
    do
    {
      return;
      this.aborted = true;
      localHttpConnection = this.responseConnection;
    }
    while (localHttpConnection == null);
    localHttpConnection.close();
  }

  protected void addCookieRequestHeader(HttpState paramHttpState, HttpConnection paramHttpConnection)
    throws IOException, HttpException
  {
    LOG.trace("enter HttpMethodBase.addCookieRequestHeader(HttpState, HttpConnection)");
    Header[] arrayOfHeader = getRequestHeaderGroup().getHeaders("Cookie");
    int i = 0;
    CookieSpec localCookieSpec;
    Cookie[] arrayOfCookie;
    if (i >= arrayOfHeader.length)
    {
      localCookieSpec = getCookieSpec(paramHttpState);
      String str1 = this.params.getVirtualHost();
      if (str1 == null)
        str1 = paramHttpConnection.getHost();
      arrayOfCookie = localCookieSpec.match(str1, paramHttpConnection.getPort(), getPath(), paramHttpConnection.isSecure(), paramHttpState.getCookies());
      if ((arrayOfCookie != null) && (arrayOfCookie.length > 0))
      {
        if (!getParams().isParameterTrue("http.protocol.single-cookie-header"))
          break label171;
        String str3 = localCookieSpec.formatCookies(arrayOfCookie);
        getRequestHeaderGroup().addHeader(new Header("Cookie", str3, true));
      }
    }
    while (true)
    {
      return;
      Header localHeader = arrayOfHeader[i];
      if (localHeader.isAutogenerated())
        getRequestHeaderGroup().removeHeader(localHeader);
      i++;
      break;
      label171: for (int j = 0; j < arrayOfCookie.length; j++)
      {
        String str2 = localCookieSpec.formatCookie(arrayOfCookie[j]);
        getRequestHeaderGroup().addHeader(new Header("Cookie", str2, true));
      }
    }
  }

  protected void addHostRequestHeader(HttpState paramHttpState, HttpConnection paramHttpConnection)
    throws IOException, HttpException
  {
    LOG.trace("enter HttpMethodBase.addHostRequestHeader(HttpState, HttpConnection)");
    String str = this.params.getVirtualHost();
    if (str != null)
      LOG.debug("Using virtual host name: " + str);
    while (true)
    {
      int i = paramHttpConnection.getPort();
      if (LOG.isDebugEnabled())
        LOG.debug("Adding Host request header");
      if (paramHttpConnection.getProtocol().getDefaultPort() != i)
        str = str + ":" + i;
      setRequestHeader("Host", str);
      return;
      str = paramHttpConnection.getHost();
    }
  }

  protected void addProxyConnectionHeader(HttpState paramHttpState, HttpConnection paramHttpConnection)
    throws IOException, HttpException
  {
    LOG.trace("enter HttpMethodBase.addProxyConnectionHeader(HttpState, HttpConnection)");
    if ((!paramHttpConnection.isTransparent()) && (getRequestHeader("Proxy-Connection") == null))
      addRequestHeader("Proxy-Connection", "Keep-Alive");
  }

  public void addRequestHeader(String paramString1, String paramString2)
  {
    addRequestHeader(new Header(paramString1, paramString2));
  }

  public void addRequestHeader(Header paramHeader)
  {
    LOG.trace("HttpMethodBase.addRequestHeader(Header)");
    if (paramHeader == null)
    {
      LOG.debug("null header value ignored");
      return;
    }
    getRequestHeaderGroup().addHeader(paramHeader);
  }

  protected void addRequestHeaders(HttpState paramHttpState, HttpConnection paramHttpConnection)
    throws IOException, HttpException
  {
    LOG.trace("enter HttpMethodBase.addRequestHeaders(HttpState, HttpConnection)");
    addUserAgentRequestHeader(paramHttpState, paramHttpConnection);
    addHostRequestHeader(paramHttpState, paramHttpConnection);
    addCookieRequestHeader(paramHttpState, paramHttpConnection);
    addProxyConnectionHeader(paramHttpState, paramHttpConnection);
  }

  public void addResponseFooter(Header paramHeader)
  {
    getResponseTrailerHeaderGroup().addHeader(paramHeader);
  }

  protected void addUserAgentRequestHeader(HttpState paramHttpState, HttpConnection paramHttpConnection)
    throws IOException, HttpException
  {
    LOG.trace("enter HttpMethodBase.addUserAgentRequestHeaders(HttpState, HttpConnection)");
    if (getRequestHeader("User-Agent") == null)
    {
      String str = (String)getParams().getParameter("http.useragent");
      if (str == null)
        str = "Jakarta Commons-HttpClient";
      setRequestHeader("User-Agent", str);
    }
  }

  protected void checkNotUsed()
    throws IllegalStateException
  {
    if (this.used)
      throw new IllegalStateException("Already used.");
  }

  protected void checkUsed()
    throws IllegalStateException
  {
    if (!this.used)
      throw new IllegalStateException("Not Used.");
  }

  public int execute(HttpState paramHttpState, HttpConnection paramHttpConnection)
    throws HttpException, IOException
  {
    LOG.trace("enter HttpMethodBase.execute(HttpState, HttpConnection)");
    this.responseConnection = paramHttpConnection;
    checkExecuteConditions(paramHttpState, paramHttpConnection);
    this.statusLine = null;
    this.connectionCloseForced = false;
    paramHttpConnection.setLastResponseInputStream(null);
    if (this.effectiveVersion == null)
      this.effectiveVersion = this.params.getVersion();
    writeRequest(paramHttpState, paramHttpConnection);
    this.requestSent = true;
    readResponse(paramHttpState, paramHttpConnection);
    this.used = true;
    return this.statusLine.getStatusCode();
  }

  void fakeResponse(StatusLine paramStatusLine, HeaderGroup paramHeaderGroup, InputStream paramInputStream)
  {
    this.used = true;
    this.statusLine = paramStatusLine;
    this.responseHeaders = paramHeaderGroup;
    this.responseBody = null;
    this.responseStream = paramInputStream;
  }

  public String getAuthenticationRealm()
  {
    return this.hostAuthState.getRealm();
  }

  protected String getContentCharSet(Header paramHeader)
  {
    LOG.trace("enter getContentCharSet( Header contentheader )");
    String str = null;
    if (paramHeader != null)
    {
      HeaderElement[] arrayOfHeaderElement = paramHeader.getElements();
      int i = arrayOfHeaderElement.length;
      str = null;
      if (i == 1)
      {
        NameValuePair localNameValuePair = arrayOfHeaderElement[0].getParameterByName("charset");
        str = null;
        if (localNameValuePair != null)
          str = localNameValuePair.getValue();
      }
    }
    if (str == null)
    {
      str = getParams().getContentCharset();
      if (LOG.isDebugEnabled())
        LOG.debug("Default charset used: " + str);
    }
    return str;
  }

  public boolean getDoAuthentication()
  {
    return this.doAuthentication;
  }

  public HttpVersion getEffectiveVersion()
  {
    return this.effectiveVersion;
  }

  public boolean getFollowRedirects()
  {
    return this.followRedirects;
  }

  public AuthState getHostAuthState()
  {
    return this.hostAuthState;
  }

  public HostConfiguration getHostConfiguration()
  {
    HostConfiguration localHostConfiguration = new HostConfiguration();
    localHostConfiguration.setHost(this.httphost);
    return localHostConfiguration;
  }

  public MethodRetryHandler getMethodRetryHandler()
  {
    return this.methodRetryHandler;
  }

  public abstract String getName();

  public HttpMethodParams getParams()
  {
    return this.params;
  }

  public String getPath()
  {
    if ((this.path == null) || (this.path.equals("")))
      return "/";
    return this.path;
  }

  public AuthState getProxyAuthState()
  {
    return this.proxyAuthState;
  }

  public String getProxyAuthenticationRealm()
  {
    return this.proxyAuthState.getRealm();
  }

  public String getQueryString()
  {
    return this.queryString;
  }

  public int getRecoverableExceptionCount()
  {
    return this.recoverableExceptionCount;
  }

  public String getRequestCharSet()
  {
    return getContentCharSet(getRequestHeader("Content-Type"));
  }

  public Header getRequestHeader(String paramString)
  {
    if (paramString == null)
      return null;
    return getRequestHeaderGroup().getCondensedHeader(paramString);
  }

  protected HeaderGroup getRequestHeaderGroup()
  {
    return this.requestHeaders;
  }

  public Header[] getRequestHeaders()
  {
    return getRequestHeaderGroup().getAllHeaders();
  }

  public Header[] getRequestHeaders(String paramString)
  {
    return getRequestHeaderGroup().getHeaders(paramString);
  }

  public byte[] getResponseBody()
    throws IOException
  {
    InputStream localInputStream;
    int j;
    ByteArrayOutputStream localByteArrayOutputStream;
    byte[] arrayOfByte;
    if (this.responseBody == null)
    {
      localInputStream = getResponseBodyAsStream();
      if (localInputStream != null)
      {
        long l = getResponseContentLength();
        if (l > 2147483647L)
          throw new IOException("Content too large to be buffered: " + l + " bytes");
        int i = getParams().getIntParameter("http.method.response.buffer.warnlimit", 1048576);
        if ((l == -1L) || (l > i))
          LOG.warn("Going to buffer response body of large or unknown size. Using getResponseBodyAsStream instead is recommended.");
        LOG.debug("Buffering response body");
        if (l <= 0L)
          break label181;
        j = (int)l;
        localByteArrayOutputStream = new ByteArrayOutputStream(j);
        arrayOfByte = new byte[4096];
      }
    }
    while (true)
    {
      int k = localInputStream.read(arrayOfByte);
      if (k <= 0)
      {
        localByteArrayOutputStream.close();
        setResponseStream(null);
        this.responseBody = localByteArrayOutputStream.toByteArray();
        return this.responseBody;
        label181: j = 4096;
        break;
      }
      localByteArrayOutputStream.write(arrayOfByte, 0, k);
    }
  }

  public InputStream getResponseBodyAsStream()
    throws IOException
  {
    if (this.responseStream != null)
      return this.responseStream;
    if (this.responseBody != null)
    {
      ByteArrayInputStream localByteArrayInputStream = new ByteArrayInputStream(this.responseBody);
      LOG.debug("re-creating response stream from byte array");
      return localByteArrayInputStream;
    }
    return null;
  }

  public String getResponseBodyAsString()
    throws IOException
  {
    boolean bool = responseAvailable();
    byte[] arrayOfByte = null;
    if (bool)
      arrayOfByte = getResponseBody();
    if (arrayOfByte != null)
      return EncodingUtil.getString(arrayOfByte, getResponseCharSet());
    return null;
  }

  public String getResponseCharSet()
  {
    return getContentCharSet(getResponseHeader("Content-Type"));
  }

  public long getResponseContentLength()
  {
    Header[] arrayOfHeader = getResponseHeaderGroup().getHeaders("Content-Length");
    if (arrayOfHeader.length == 0);
    while (true)
    {
      return -1L;
      if (arrayOfHeader.length > 1)
        LOG.warn("Multiple content-length headers detected");
      int i = -1 + arrayOfHeader.length;
      while (i >= 0)
      {
        Header localHeader = arrayOfHeader[i];
        try
        {
          long l = Long.parseLong(localHeader.getValue());
          return l;
        }
        catch (NumberFormatException localNumberFormatException)
        {
          if (LOG.isWarnEnabled())
            LOG.warn("Invalid content-length value: " + localNumberFormatException.getMessage());
          i--;
        }
      }
    }
  }

  public Header getResponseFooter(String paramString)
  {
    if (paramString == null)
      return null;
    return getResponseTrailerHeaderGroup().getCondensedHeader(paramString);
  }

  public Header[] getResponseFooters()
  {
    return getResponseTrailerHeaderGroup().getAllHeaders();
  }

  public Header getResponseHeader(String paramString)
  {
    if (paramString == null)
      return null;
    return getResponseHeaderGroup().getCondensedHeader(paramString);
  }

  protected HeaderGroup getResponseHeaderGroup()
  {
    return this.responseHeaders;
  }

  public Header[] getResponseHeaders()
  {
    return getResponseHeaderGroup().getAllHeaders();
  }

  public Header[] getResponseHeaders(String paramString)
  {
    return getResponseHeaderGroup().getHeaders(paramString);
  }

  protected InputStream getResponseStream()
  {
    return this.responseStream;
  }

  protected HeaderGroup getResponseTrailerHeaderGroup()
  {
    return this.responseTrailerHeaders;
  }

  public int getStatusCode()
  {
    return this.statusLine.getStatusCode();
  }

  public StatusLine getStatusLine()
  {
    return this.statusLine;
  }

  public String getStatusText()
  {
    return this.statusLine.getReasonPhrase();
  }

  public URI getURI()
    throws URIException
  {
    StringBuffer localStringBuffer = new StringBuffer();
    if (this.httphost != null)
    {
      localStringBuffer.append(this.httphost.getProtocol().getScheme());
      localStringBuffer.append("://");
      localStringBuffer.append(this.httphost.getHostName());
      int i = this.httphost.getPort();
      if ((i != -1) && (i != this.httphost.getProtocol().getDefaultPort()))
      {
        localStringBuffer.append(":");
        localStringBuffer.append(i);
      }
    }
    localStringBuffer.append(this.path);
    if (this.queryString != null)
    {
      localStringBuffer.append('?');
      localStringBuffer.append(this.queryString);
    }
    return new URI(localStringBuffer.toString(), true);
  }

  public boolean hasBeenUsed()
  {
    return this.used;
  }

  public boolean isAborted()
  {
    return this.aborted;
  }

  protected boolean isConnectionCloseForced()
  {
    return this.connectionCloseForced;
  }

  public boolean isHttp11()
  {
    return this.params.getVersion().equals(HttpVersion.HTTP_1_1);
  }

  public boolean isRequestSent()
  {
    return this.requestSent;
  }

  public boolean isStrictMode()
  {
    return false;
  }

  protected void processResponseBody(HttpState paramHttpState, HttpConnection paramHttpConnection)
  {
  }

  protected void processResponseHeaders(HttpState paramHttpState, HttpConnection paramHttpConnection)
  {
    LOG.trace("enter HttpMethodBase.processResponseHeaders(HttpState, HttpConnection)");
    Header[] arrayOfHeader = getResponseHeaderGroup().getHeaders("set-cookie2");
    if (arrayOfHeader.length == 0)
      arrayOfHeader = getResponseHeaderGroup().getHeaders("set-cookie");
    CookieSpec localCookieSpec = getCookieSpec(paramHttpState);
    String str1 = this.params.getVirtualHost();
    if (str1 == null)
      str1 = paramHttpConnection.getHost();
    int i = 0;
    int j = arrayOfHeader.length;
    if (i >= j)
      return;
    Header localHeader = arrayOfHeader[i];
    while (true)
    {
      int k;
      Cookie localCookie;
      try
      {
        Cookie[] arrayOfCookie2 = localCookieSpec.parse(str1, paramHttpConnection.getPort(), getPath(), paramHttpConnection.isSecure(), localHeader);
        arrayOfCookie1 = arrayOfCookie2;
        if (arrayOfCookie1 != null)
        {
          k = 0;
          int m = arrayOfCookie1.length;
          if (k < m);
        }
        else
        {
          i++;
        }
      }
      catch (MalformedCookieException localMalformedCookieException1)
      {
        boolean bool1 = LOG.isWarnEnabled();
        Cookie[] arrayOfCookie1 = null;
        if (!bool1)
          continue;
        LOG.warn("Invalid cookie header: \"" + localHeader.getValue() + "\". " + localMalformedCookieException1.getMessage());
        arrayOfCookie1 = null;
        continue;
        localCookie = arrayOfCookie1[k];
      }
      try
      {
        int n = paramHttpConnection.getPort();
        String str2 = getPath();
        boolean bool2 = paramHttpConnection.isSecure();
        localCookieSpec.validate(str1, n, str2, bool2, localCookie);
        paramHttpState.addCookie(localCookie);
        if (LOG.isDebugEnabled())
          LOG.debug("Cookie accepted: \"" + localCookieSpec.formatCookie(localCookie) + "\"");
        k++;
      }
      catch (MalformedCookieException localMalformedCookieException2)
      {
        while (true)
          if (LOG.isWarnEnabled())
            LOG.warn("Cookie rejected: \"" + localCookieSpec.formatCookie(localCookie) + "\". " + localMalformedCookieException2.getMessage());
      }
    }
  }

  protected void processStatusLine(HttpState paramHttpState, HttpConnection paramHttpConnection)
  {
  }

  protected void readResponse(HttpState paramHttpState, HttpConnection paramHttpConnection)
    throws IOException, HttpException
  {
    LOG.trace("enter HttpMethodBase.readResponse(HttpState, HttpConnection)");
    while (true)
    {
      if (this.statusLine != null)
      {
        readResponseBody(paramHttpState, paramHttpConnection);
        processResponseBody(paramHttpState, paramHttpConnection);
        return;
      }
      readStatusLine(paramHttpState, paramHttpConnection);
      processStatusLine(paramHttpState, paramHttpConnection);
      readResponseHeaders(paramHttpState, paramHttpConnection);
      processResponseHeaders(paramHttpState, paramHttpConnection);
      int i = this.statusLine.getStatusCode();
      if ((i >= 100) && (i < 200))
      {
        if (LOG.isInfoEnabled())
          LOG.info("Discarding unexpected response: " + this.statusLine.toString());
        this.statusLine = null;
      }
    }
  }

  protected void readResponseBody(HttpState paramHttpState, HttpConnection paramHttpConnection)
    throws IOException, HttpException
  {
    LOG.trace("enter HttpMethodBase.readResponseBody(HttpState, HttpConnection)");
    InputStream localInputStream = readResponseBody(paramHttpConnection);
    if (localInputStream == null)
    {
      responseBodyConsumed();
      return;
    }
    paramHttpConnection.setLastResponseInputStream(localInputStream);
    setResponseStream(localInputStream);
  }

  protected void readResponseHeaders(HttpState paramHttpState, HttpConnection paramHttpConnection)
    throws IOException, HttpException
  {
    LOG.trace("enter HttpMethodBase.readResponseHeaders(HttpState,HttpConnection)");
    getResponseHeaderGroup().clear();
    Header[] arrayOfHeader = HttpParser.parseHeaders(paramHttpConnection.getResponseInputStream(), getParams().getHttpElementCharset());
    if (Wire.HEADER_WIRE.enabled());
    for (int i = 0; ; i++)
    {
      if (i >= arrayOfHeader.length)
      {
        getResponseHeaderGroup().setHeaders(arrayOfHeader);
        return;
      }
      Wire.HEADER_WIRE.input(arrayOfHeader[i].toExternalForm());
    }
  }

  protected void readStatusLine(HttpState paramHttpState, HttpConnection paramHttpConnection)
    throws IOException, HttpException
  {
    LOG.trace("enter HttpMethodBase.readStatusLine(HttpState, HttpConnection)");
    int i = getParams().getIntParameter("http.protocol.status-line-garbage-limit", 2147483647);
    String str2;
    for (int j = 0; ; j++)
    {
      String str1 = paramHttpConnection.readLine(getParams().getHttpElementCharset());
      if ((str1 == null) && (j == 0))
        throw new NoHttpResponseException("The server " + paramHttpConnection.getHost() + " failed to respond");
      if (Wire.HEADER_WIRE.enabled())
        Wire.HEADER_WIRE.input(str1 + "\r\n");
      if ((str1 != null) && (StatusLine.startsWithHTTP(str1)))
      {
        this.statusLine = new StatusLine(str1);
        str2 = this.statusLine.getHttpVersion();
        if ((!getParams().isParameterFalse("http.protocol.unambiguous-statusline")) || (!str2.equals("HTTP")))
          break;
        getParams().setVersion(HttpVersion.HTTP_1_0);
        if (LOG.isWarnEnabled())
          LOG.warn("Ambiguous status line (HTTP protocol version missing):" + this.statusLine.toString());
        return;
      }
      if ((str1 == null) || (j >= i))
        throw new ProtocolException("The server " + paramHttpConnection.getHost() + " failed to respond with a valid HTTP response");
    }
    this.effectiveVersion = HttpVersion.parse(str2);
  }

  public void recycle()
  {
    LOG.trace("enter HttpMethodBase.recycle()");
    releaseConnection();
    this.path = null;
    this.followRedirects = false;
    this.doAuthentication = true;
    this.queryString = null;
    getRequestHeaderGroup().clear();
    getResponseHeaderGroup().clear();
    getResponseTrailerHeaderGroup().clear();
    this.statusLine = null;
    this.effectiveVersion = null;
    this.aborted = false;
    this.used = false;
    this.params = new HttpMethodParams();
    this.responseBody = null;
    this.recoverableExceptionCount = 0;
    this.connectionCloseForced = false;
    this.hostAuthState.invalidate();
    this.proxyAuthState.invalidate();
    this.cookiespec = null;
    this.requestSent = false;
  }

  // ERROR //
  public void releaseConnection()
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 89	org/apache/commons/httpclient/HttpMethodBase:responseStream	Ljava/io/InputStream;
    //   4: astore_2
    //   5: aload_2
    //   6: ifnull +10 -> 16
    //   9: aload_0
    //   10: getfield 89	org/apache/commons/httpclient/HttpMethodBase:responseStream	Ljava/io/InputStream;
    //   13: invokevirtual 917	java/io/InputStream:close	()V
    //   16: aload_0
    //   17: invokespecial 919	org/apache/commons/httpclient/HttpMethodBase:ensureConnectionRelease	()V
    //   20: return
    //   21: astore_1
    //   22: aload_0
    //   23: invokespecial 919	org/apache/commons/httpclient/HttpMethodBase:ensureConnectionRelease	()V
    //   26: aload_1
    //   27: athrow
    //   28: astore_3
    //   29: goto -13 -> 16
    //
    // Exception table:
    //   from	to	target	type
    //   0	5	21	finally
    //   9	16	21	finally
    //   9	16	28	java/io/IOException
  }

  public void removeRequestHeader(String paramString)
  {
    Header[] arrayOfHeader = getRequestHeaderGroup().getHeaders(paramString);
    for (int i = 0; ; i++)
    {
      if (i >= arrayOfHeader.length)
        return;
      getRequestHeaderGroup().removeHeader(arrayOfHeader[i]);
    }
  }

  public void removeRequestHeader(Header paramHeader)
  {
    if (paramHeader == null)
      return;
    getRequestHeaderGroup().removeHeader(paramHeader);
  }

  protected void responseBodyConsumed()
  {
    this.responseStream = null;
    if (this.responseConnection != null)
    {
      this.responseConnection.setLastResponseInputStream(null);
      if (!shouldCloseConnection(this.responseConnection))
        break label48;
      this.responseConnection.close();
    }
    while (true)
    {
      this.connectionCloseForced = false;
      ensureConnectionRelease();
      return;
      try
      {
        label48: if (this.responseConnection.isResponseAvailable())
        {
          if (getParams().isParameterTrue("http.protocol.warn-extra-input"))
            LOG.warn("Extra response data detected - closing connection");
          this.responseConnection.close();
        }
      }
      catch (IOException localIOException)
      {
        LOG.warn(localIOException.getMessage());
        this.responseConnection.close();
      }
    }
  }

  protected void setConnectionCloseForced(boolean paramBoolean)
  {
    if (LOG.isDebugEnabled())
      LOG.debug("Force-close connection: " + paramBoolean);
    this.connectionCloseForced = paramBoolean;
  }

  public void setDoAuthentication(boolean paramBoolean)
  {
    this.doAuthentication = paramBoolean;
  }

  public void setFollowRedirects(boolean paramBoolean)
  {
    this.followRedirects = paramBoolean;
  }

  public void setHostConfiguration(HostConfiguration paramHostConfiguration)
  {
    if (paramHostConfiguration != null)
    {
      this.httphost = new HttpHost(paramHostConfiguration.getHost(), paramHostConfiguration.getPort(), paramHostConfiguration.getProtocol());
      return;
    }
    this.httphost = null;
  }

  public void setHttp11(boolean paramBoolean)
  {
    if (paramBoolean)
    {
      this.params.setVersion(HttpVersion.HTTP_1_1);
      return;
    }
    this.params.setVersion(HttpVersion.HTTP_1_0);
  }

  public void setMethodRetryHandler(MethodRetryHandler paramMethodRetryHandler)
  {
    this.methodRetryHandler = paramMethodRetryHandler;
  }

  public void setParams(HttpMethodParams paramHttpMethodParams)
  {
    if (paramHttpMethodParams == null)
      throw new IllegalArgumentException("Parameters may not be null");
    this.params = paramHttpMethodParams;
  }

  public void setPath(String paramString)
  {
    this.path = paramString;
  }

  public void setQueryString(String paramString)
  {
    this.queryString = paramString;
  }

  public void setQueryString(NameValuePair[] paramArrayOfNameValuePair)
  {
    LOG.trace("enter HttpMethodBase.setQueryString(NameValuePair[])");
    this.queryString = EncodingUtil.formUrlEncode(paramArrayOfNameValuePair, "UTF-8");
  }

  public void setRequestHeader(String paramString1, String paramString2)
  {
    setRequestHeader(new Header(paramString1, paramString2));
  }

  public void setRequestHeader(Header paramHeader)
  {
    Header[] arrayOfHeader = getRequestHeaderGroup().getHeaders(paramHeader.getName());
    for (int i = 0; ; i++)
    {
      if (i >= arrayOfHeader.length)
      {
        getRequestHeaderGroup().addHeader(paramHeader);
        return;
      }
      getRequestHeaderGroup().removeHeader(arrayOfHeader[i]);
    }
  }

  protected void setResponseStream(InputStream paramInputStream)
  {
    this.responseStream = paramInputStream;
  }

  public void setStrictMode(boolean paramBoolean)
  {
    if (paramBoolean)
    {
      this.params.makeStrict();
      return;
    }
    this.params.makeLenient();
  }

  public void setURI(URI paramURI)
    throws URIException
  {
    if (paramURI.isAbsoluteURI())
      this.httphost = new HttpHost(paramURI);
    if (paramURI.getPath() == null);
    for (String str = "/"; ; str = paramURI.getEscapedPath())
    {
      setPath(str);
      setQueryString(paramURI.getEscapedQuery());
      return;
    }
  }

  protected boolean shouldCloseConnection(HttpConnection paramHttpConnection)
  {
    if (isConnectionCloseForced())
      LOG.debug("Should force-close connection.");
    Header localHeader;
    do
    {
      return true;
      boolean bool = paramHttpConnection.isTransparent();
      localHeader = null;
      if (!bool)
        localHeader = this.responseHeaders.getFirstHeader("proxy-connection");
      if (localHeader == null)
        localHeader = this.responseHeaders.getFirstHeader("connection");
      if (localHeader == null)
        localHeader = this.requestHeaders.getFirstHeader("connection");
      if (localHeader == null)
        break label232;
      if (!localHeader.getValue().equalsIgnoreCase("close"))
        break;
    }
    while (!LOG.isDebugEnabled());
    LOG.debug("Should close connection in response to directive: " + localHeader.getValue());
    return true;
    if (localHeader.getValue().equalsIgnoreCase("keep-alive"))
    {
      if (LOG.isDebugEnabled())
        LOG.debug("Should NOT close connection in response to directive: " + localHeader.getValue());
      return false;
    }
    if (LOG.isDebugEnabled())
      LOG.debug("Unknown directive: " + localHeader.toExternalForm());
    label232: LOG.debug("Resorting to protocol version default close connection policy");
    if (this.effectiveVersion.greaterEquals(HttpVersion.HTTP_1_1))
      if (LOG.isDebugEnabled())
        LOG.debug("Should NOT close connection, using " + this.effectiveVersion.toString());
    while (true)
    {
      return this.effectiveVersion.lessEquals(HttpVersion.HTTP_1_0);
      if (LOG.isDebugEnabled())
        LOG.debug("Should close connection, using " + this.effectiveVersion.toString());
    }
  }

  public boolean validate()
  {
    return true;
  }

  protected void writeRequest(HttpState paramHttpState, HttpConnection paramHttpConnection)
    throws IOException, HttpException
  {
    LOG.trace("enter HttpMethodBase.writeRequest(HttpState, HttpConnection)");
    writeRequestLine(paramHttpState, paramHttpConnection);
    writeRequestHeaders(paramHttpState, paramHttpConnection);
    paramHttpConnection.writeLine();
    if (Wire.HEADER_WIRE.enabled())
      Wire.HEADER_WIRE.output("\r\n");
    HttpVersion localHttpVersion = getParams().getVersion();
    Header localHeader = getRequestHeader("Expect");
    String str = null;
    if (localHeader != null)
      str = localHeader.getValue();
    int i;
    if ((str != null) && (str.compareToIgnoreCase("100-continue") == 0))
    {
      if (!localHttpVersion.greaterEquals(HttpVersion.HTTP_1_1))
        break label238;
      paramHttpConnection.flushRequestOutputStream();
      i = paramHttpConnection.getParams().getSoTimeout();
    }
    while (true)
    {
      try
      {
        paramHttpConnection.setSocketTimeout(3000);
        readStatusLine(paramHttpState, paramHttpConnection);
        processStatusLine(paramHttpState, paramHttpConnection);
        readResponseHeaders(paramHttpState, paramHttpConnection);
        processResponseHeaders(paramHttpState, paramHttpConnection);
        if (this.statusLine.getStatusCode() != 100)
          break;
        this.statusLine = null;
        LOG.debug("OK to continue received");
        paramHttpConnection.setSocketTimeout(i);
        writeRequestBody(paramHttpState, paramHttpConnection);
        paramHttpConnection.flushRequestOutputStream();
        return;
      }
      catch (InterruptedIOException localInterruptedIOException)
      {
        if (!ExceptionUtil.isSocketTimeoutException(localInterruptedIOException))
          throw localInterruptedIOException;
      }
      finally
      {
        paramHttpConnection.setSocketTimeout(i);
      }
      removeRequestHeader("Expect");
      LOG.info("100 (continue) read timeout. Resume sending the request");
      continue;
      label238: removeRequestHeader("Expect");
      LOG.info("'Expect: 100-continue' handshake is only supported by HTTP/1.1 or higher");
    }
    paramHttpConnection.setSocketTimeout(i);
  }

  protected boolean writeRequestBody(HttpState paramHttpState, HttpConnection paramHttpConnection)
    throws IOException, HttpException
  {
    return true;
  }

  protected void writeRequestHeaders(HttpState paramHttpState, HttpConnection paramHttpConnection)
    throws IOException, HttpException
  {
    LOG.trace("enter HttpMethodBase.writeRequestHeaders(HttpState,HttpConnection)");
    addRequestHeaders(paramHttpState, paramHttpConnection);
    String str1 = getParams().getHttpElementCharset();
    Header[] arrayOfHeader = getRequestHeaders();
    for (int i = 0; ; i++)
    {
      if (i >= arrayOfHeader.length)
        return;
      String str2 = arrayOfHeader[i].toExternalForm();
      if (Wire.HEADER_WIRE.enabled())
        Wire.HEADER_WIRE.output(str2);
      paramHttpConnection.print(str2, str1);
    }
  }

  protected void writeRequestLine(HttpState paramHttpState, HttpConnection paramHttpConnection)
    throws IOException, HttpException
  {
    LOG.trace("enter HttpMethodBase.writeRequestLine(HttpState, HttpConnection)");
    String str = getRequestLine(paramHttpConnection);
    if (Wire.HEADER_WIRE.enabled())
      Wire.HEADER_WIRE.output(str);
    paramHttpConnection.print(str, getParams().getHttpElementCharset());
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     org.apache.commons.httpclient.HttpMethodBase
 * JD-Core Version:    0.6.2
 */