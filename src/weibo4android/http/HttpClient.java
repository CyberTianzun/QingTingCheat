package weibo4android.http;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.Authenticator;
import java.net.Authenticator.RequestorType;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.PasswordAuthentication;
import java.net.Proxy;
import java.net.Proxy.Type;
import java.net.URL;
import java.net.URLEncoder;
import java.security.AccessControlException;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import weibo4android.Configuration;
import weibo4android.Weibo;
import weibo4android.WeiboException;

public class HttpClient
  implements Serializable
{
  private static final int BAD_GATEWAY = 502;
  private static final int BAD_REQUEST = 400;
  private static final boolean DEBUG = false;
  private static final int FORBIDDEN = 403;
  private static final int INTERNAL_SERVER_ERROR = 500;
  private static final int NOT_ACCEPTABLE = 406;
  private static final int NOT_AUTHORIZED = 401;
  private static final int NOT_FOUND = 404;
  private static final int NOT_MODIFIED = 304;
  private static final int OK = 200;
  private static final int SERVICE_UNAVAILABLE = 503;
  private static boolean isJDK14orEarlier = false;
  private static final long serialVersionUID = 808018030183407996L;
  private String accessTokenURL = Configuration.getScheme() + "api.t.sina.com.cn/oauth/access_token";
  private String authenticationURL = Configuration.getScheme() + "api.t.sina.com.cn/oauth/authenticate";
  private String authorizationURL = Configuration.getScheme() + "api.t.sina.com.cn/oauth/authorize";
  private int connectionTimeout = Configuration.getConnectionTimeout();
  private OAuth oauth = null;
  private OAuthToken oauthToken = null;
  private String password = Configuration.getPassword();
  private String proxyAuthPassword = Configuration.getProxyPassword();
  private String proxyAuthUser = Configuration.getProxyUser();
  private String proxyHost = Configuration.getProxyHost();
  private int proxyPort = Configuration.getProxyPort();
  private int readTimeout = Configuration.getReadTimeout();
  private Map<String, String> requestHeaders = new HashMap();
  private String requestTokenURL = Configuration.getScheme() + "api.t.sina.com.cn/oauth/request_token";
  private int retryCount = Configuration.getRetryCount();
  private int retryIntervalMillis = 1000 * Configuration.getRetryIntervalSecs();
  private String token = null;
  private String userId = Configuration.getUser();

  static
  {
    try
    {
      String str = System.getProperty("java.specification.version");
      if (str != null)
      {
        boolean bool1 = 1.5D < Double.parseDouble(str);
        boolean bool2 = false;
        if (bool1)
          bool2 = true;
        isJDK14orEarlier = bool2;
      }
      return;
    }
    catch (AccessControlException localAccessControlException)
    {
      isJDK14orEarlier = true;
    }
  }

  public HttpClient()
  {
    setUserAgent(null);
    setOAuthConsumer(null, null);
    setRequestHeader("Accept-Encoding", "gzip");
  }

  public static String encodeParameters(PostParameter[] paramArrayOfPostParameter)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    int i = 0;
    while (true)
    {
      if (i < paramArrayOfPostParameter.length)
        if (i != 0)
          localStringBuffer.append("&");
      try
      {
        localStringBuffer.append(URLEncoder.encode(paramArrayOfPostParameter[i].name, "UTF-8")).append("=").append(URLEncoder.encode(paramArrayOfPostParameter[i].value, "UTF-8"));
        label62: i++;
        continue;
        return localStringBuffer.toString();
      }
      catch (UnsupportedEncodingException localUnsupportedEncodingException)
      {
        break label62;
      }
    }
  }

  private static String getCause(int paramInt)
  {
    String str = null;
    switch (paramInt)
    {
    default:
      str = "";
    case 304:
    case 400:
    case 401:
    case 403:
    case 404:
    case 406:
    case 500:
    case 502:
    case 503:
    }
    while (true)
    {
      return paramInt + ":" + str;
      str = "The request was invalid.  An accompanying error message will explain why. This is the status code will be returned during rate limiting.";
      continue;
      str = "Authentication credentials were missing or incorrect.";
      continue;
      str = "The request is understood, but it has been refused.  An accompanying error message will explain why.";
      continue;
      str = "The URI requested is invalid or the resource requested, such as a user, does not exists.";
      continue;
      str = "Returned by the Search API when an invalid format is specified in the request.";
      continue;
      str = "Something is broken.  Please post to the group so the Weibo team can investigate.";
      continue;
      str = "Weibo is down or being upgraded.";
      continue;
      str = "Service Unavailable: The Weibo servers are up, but overloaded with requests. Try again later. The search and trend methods use this to indicate when you are being rate limited.";
    }
  }

  private HttpURLConnection getConnection(String paramString)
    throws IOException
  {
    Proxy localProxy;
    if ((this.proxyHost != null) && (!this.proxyHost.equals("")))
    {
      if ((this.proxyAuthUser != null) && (!this.proxyAuthUser.equals("")))
      {
        log("Proxy AuthUser: " + this.proxyAuthUser);
        log("Proxy AuthPassword: " + this.proxyAuthPassword);
        Authenticator.setDefault(new Authenticator()
        {
          protected PasswordAuthentication getPasswordAuthentication()
          {
            if (getRequestorType().equals(Authenticator.RequestorType.PROXY))
              return new PasswordAuthentication(HttpClient.this.proxyAuthUser, HttpClient.this.proxyAuthPassword.toCharArray());
            return null;
          }
        });
      }
      localProxy = new Proxy(Proxy.Type.HTTP, InetSocketAddress.createUnresolved(this.proxyHost, this.proxyPort));
      if (DEBUG)
        log("Opening proxied connection(" + this.proxyHost + ":" + this.proxyPort + ")");
    }
    for (HttpURLConnection localHttpURLConnection = (HttpURLConnection)new URL(paramString).openConnection(localProxy); ; localHttpURLConnection = (HttpURLConnection)new URL(paramString).openConnection())
    {
      if ((this.connectionTimeout > 0) && (!isJDK14orEarlier))
        localHttpURLConnection.setConnectTimeout(this.connectionTimeout);
      if ((this.readTimeout > 0) && (!isJDK14orEarlier))
        localHttpURLConnection.setReadTimeout(this.readTimeout);
      return localHttpURLConnection;
    }
  }

  private static void log(String paramString)
  {
    if (DEBUG)
      System.out.println("[" + new Date() + "]" + paramString);
  }

  private static void log(String paramString1, String paramString2)
  {
    if (DEBUG)
      log(paramString1 + paramString2);
  }

  private void setHeaders(String paramString1, PostParameter[] paramArrayOfPostParameter, HttpURLConnection paramHttpURLConnection, boolean paramBoolean, String paramString2)
  {
    log("Request: ");
    log(paramString2 + " ", paramString1);
    Iterator localIterator;
    if (paramBoolean)
    {
      if ((this.oauth != null) || (this.oauth != null))
      {
        String str2 = this.oauth.generateAuthorizationHeader(paramString2, paramString1, paramArrayOfPostParameter, this.oauthToken);
        paramHttpURLConnection.addRequestProperty("Authorization", str2);
        log("Authorization: " + str2);
      }
    }
    else
      localIterator = this.requestHeaders.keySet().iterator();
    while (localIterator.hasNext())
    {
      String str1 = (String)localIterator.next();
      paramHttpURLConnection.addRequestProperty(str1, (String)this.requestHeaders.get(str1));
      log(str1 + ": " + (String)this.requestHeaders.get(str1));
      continue;
      throw new IllegalStateException("Neither user ID/password combination nor OAuth consumer key/secret combination supplied");
    }
  }

  public Response delete(String paramString, boolean paramBoolean)
    throws WeiboException
  {
    return httpRequest(paramString, null, paramBoolean, "DELETE");
  }

  public boolean equals(Object paramObject)
  {
    if (this == paramObject);
    HttpClient localHttpClient;
    do
    {
      do
      {
        return true;
        if (paramObject == null)
          return false;
        if (getClass() != paramObject.getClass())
          return false;
        localHttpClient = (HttpClient)paramObject;
        if (this.accessTokenURL == null)
        {
          if (localHttpClient.accessTokenURL != null)
            return false;
        }
        else if (!this.accessTokenURL.equals(localHttpClient.accessTokenURL))
          return false;
        if (this.authenticationURL == null)
        {
          if (localHttpClient.authenticationURL != null)
            return false;
        }
        else if (!this.authenticationURL.equals(localHttpClient.authenticationURL))
          return false;
        if (this.authorizationURL == null)
        {
          if (localHttpClient.authorizationURL != null)
            return false;
        }
        else if (!this.authorizationURL.equals(localHttpClient.authorizationURL))
          return false;
        if (this.connectionTimeout != localHttpClient.connectionTimeout)
          return false;
        if (this.oauth == null)
        {
          if (localHttpClient.oauth != null)
            return false;
        }
        else if (!this.oauth.equals(localHttpClient.oauth))
          return false;
        if (this.oauthToken == null)
        {
          if (localHttpClient.oauthToken != null)
            return false;
        }
        else if (!this.oauthToken.equals(localHttpClient.oauthToken))
          return false;
        if (this.proxyAuthPassword == null)
        {
          if (localHttpClient.proxyAuthPassword != null)
            return false;
        }
        else if (!this.proxyAuthPassword.equals(localHttpClient.proxyAuthPassword))
          return false;
        if (this.proxyAuthUser == null)
        {
          if (localHttpClient.proxyAuthUser != null)
            return false;
        }
        else if (!this.proxyAuthUser.equals(localHttpClient.proxyAuthUser))
          return false;
        if (this.proxyHost == null)
        {
          if (localHttpClient.proxyHost != null)
            return false;
        }
        else if (!this.proxyHost.equals(localHttpClient.proxyHost))
          return false;
        if (this.proxyPort != localHttpClient.proxyPort)
          return false;
        if (this.readTimeout != localHttpClient.readTimeout)
          return false;
        if (this.requestHeaders == null)
        {
          if (localHttpClient.requestHeaders != null)
            return false;
        }
        else if (!this.requestHeaders.equals(localHttpClient.requestHeaders))
          return false;
        if (this.requestTokenURL == null)
        {
          if (localHttpClient.requestTokenURL != null)
            return false;
        }
        else if (!this.requestTokenURL.equals(localHttpClient.requestTokenURL))
          return false;
        if (this.retryCount != localHttpClient.retryCount)
          return false;
        if (this.retryIntervalMillis != localHttpClient.retryIntervalMillis)
          return false;
        if (this.token != null)
          break;
      }
      while (localHttpClient.token == null);
      return false;
    }
    while (this.token.equals(localHttpClient.token));
    return false;
  }

  public Response get(String paramString)
    throws WeiboException
  {
    return httpRequest(paramString, null, false);
  }

  public Response get(String paramString, boolean paramBoolean)
    throws WeiboException
  {
    return httpRequest(paramString, null, paramBoolean);
  }

  public String getAccessTokenURL()
  {
    return this.accessTokenURL;
  }

  public String getAuthenticationRL()
  {
    return this.authenticationURL;
  }

  public String getAuthenticationURL()
  {
    return this.authenticationURL;
  }

  public String getAuthorizationURL()
  {
    return this.authorizationURL;
  }

  public int getConnectionTimeout()
  {
    return this.connectionTimeout;
  }

  public AccessToken getOAuthAccessToken(String paramString1, String paramString2)
    throws WeiboException
  {
    try
    {
      this.oauthToken = new OAuthToken(paramString1, paramString2)
      {
      };
      this.oauthToken = new AccessToken(httpRequest(this.accessTokenURL, new PostParameter[0], true));
      return (AccessToken)this.oauthToken;
    }
    catch (WeiboException localWeiboException)
    {
      throw new WeiboException("The user has not given access to the account.", localWeiboException, localWeiboException.getStatusCode());
    }
  }

  public AccessToken getOAuthAccessToken(String paramString1, String paramString2, String paramString3)
    throws WeiboException
  {
    try
    {
      this.oauthToken = new OAuthToken(paramString1, paramString2)
      {
      };
      String str = this.accessTokenURL;
      PostParameter[] arrayOfPostParameter = new PostParameter[1];
      arrayOfPostParameter[0] = new PostParameter("oauth_verifier", paramString3);
      this.oauthToken = new AccessToken(httpRequest(str, arrayOfPostParameter, true));
      return (AccessToken)this.oauthToken;
    }
    catch (WeiboException localWeiboException)
    {
      throw new WeiboException("The user has not given access to the account.", localWeiboException, localWeiboException.getStatusCode());
    }
  }

  public AccessToken getOAuthAccessToken(RequestToken paramRequestToken)
    throws WeiboException
  {
    try
    {
      this.oauthToken = paramRequestToken;
      this.oauthToken = new AccessToken(httpRequest(this.accessTokenURL, new PostParameter[0], true));
      return (AccessToken)this.oauthToken;
    }
    catch (WeiboException localWeiboException)
    {
      throw new WeiboException("The user has not given access to the account.", localWeiboException, localWeiboException.getStatusCode());
    }
  }

  public AccessToken getOAuthAccessToken(RequestToken paramRequestToken, String paramString)
    throws WeiboException
  {
    try
    {
      this.oauthToken = paramRequestToken;
      String str = this.accessTokenURL;
      PostParameter[] arrayOfPostParameter = new PostParameter[1];
      arrayOfPostParameter[0] = new PostParameter("oauth_verifier", paramString);
      this.oauthToken = new AccessToken(httpRequest(str, arrayOfPostParameter, true));
      return (AccessToken)this.oauthToken;
    }
    catch (WeiboException localWeiboException)
    {
      throw new WeiboException("The user has not given access to the account.", localWeiboException, localWeiboException.getStatusCode());
    }
  }

  public RequestToken getOAuthRequestToken()
    throws WeiboException
  {
    this.oauthToken = new RequestToken(httpRequest(this.requestTokenURL, null, true), this);
    return (RequestToken)this.oauthToken;
  }

  public RequestToken getOauthRequestToken(String paramString)
    throws WeiboException
  {
    String str = this.requestTokenURL;
    PostParameter[] arrayOfPostParameter = new PostParameter[1];
    arrayOfPostParameter[0] = new PostParameter("oauth_callback", paramString);
    this.oauthToken = new RequestToken(httpRequest(str, arrayOfPostParameter, true), this);
    return (RequestToken)this.oauthToken;
  }

  public String getPassword()
  {
    return this.password;
  }

  public String getProxyAuthPassword()
  {
    return this.proxyAuthPassword;
  }

  public String getProxyAuthUser()
  {
    return this.proxyAuthUser;
  }

  public String getProxyHost()
  {
    return this.proxyHost;
  }

  public int getProxyPort()
  {
    return this.proxyPort;
  }

  public int getReadTimeout()
  {
    return this.readTimeout;
  }

  public String getRequestHeader(String paramString)
  {
    return (String)this.requestHeaders.get(paramString);
  }

  public String getRequestTokenURL()
  {
    return this.requestTokenURL;
  }

  public String getUserAgent()
  {
    return getRequestHeader("User-Agent");
  }

  public String getUserId()
  {
    return this.userId;
  }

  public AccessToken getXAuthAccessToken(String paramString1, String paramString2, String paramString3)
    throws WeiboException
  {
    String str = this.accessTokenURL;
    PostParameter[] arrayOfPostParameter = new PostParameter[3];
    arrayOfPostParameter[0] = new PostParameter("x_auth_username", paramString1);
    arrayOfPostParameter[1] = new PostParameter("x_auth_password", paramString2);
    arrayOfPostParameter[2] = new PostParameter("x_auth_mode", paramString3);
    this.oauthToken = new AccessToken(httpRequest(str, arrayOfPostParameter, true));
    return (AccessToken)this.oauthToken;
  }

  public int hashCode()
  {
    int i;
    int k;
    label26: int n;
    label44: int i2;
    label72: int i4;
    label92: int i6;
    label112: int i8;
    label132: int i10;
    label152: int i12;
    label188: int i14;
    label208: int i15;
    int i16;
    if (this.accessTokenURL == null)
    {
      i = 0;
      int j = 31 * (i + 31);
      if (this.authenticationURL != null)
        break label265;
      k = 0;
      int m = 31 * (k + j);
      if (this.authorizationURL != null)
        break label276;
      n = 0;
      int i1 = 31 * (31 * (n + m) + this.connectionTimeout);
      if (this.oauth != null)
        break label288;
      i2 = 0;
      int i3 = 31 * (i2 + i1);
      if (this.oauthToken != null)
        break label300;
      i4 = 0;
      int i5 = 31 * (i4 + i3);
      if (this.proxyAuthPassword != null)
        break label312;
      i6 = 0;
      int i7 = 31 * (i6 + i5);
      if (this.proxyAuthUser != null)
        break label324;
      i8 = 0;
      int i9 = 31 * (i8 + i7);
      if (this.proxyHost != null)
        break label336;
      i10 = 0;
      int i11 = 31 * (31 * (31 * (i10 + i9) + this.proxyPort) + this.readTimeout);
      if (this.requestHeaders != null)
        break label348;
      i12 = 0;
      int i13 = 31 * (i12 + i11);
      if (this.requestTokenURL != null)
        break label362;
      i14 = 0;
      i15 = 31 * (31 * (31 * (i14 + i13) + this.retryCount) + this.retryIntervalMillis);
      String str = this.token;
      i16 = 0;
      if (str != null)
        break label374;
    }
    while (true)
    {
      return i15 + i16;
      i = this.accessTokenURL.hashCode();
      break;
      label265: k = this.authenticationURL.hashCode();
      break label26;
      label276: n = this.authorizationURL.hashCode();
      break label44;
      label288: i2 = this.oauth.hashCode();
      break label72;
      label300: i4 = this.oauthToken.hashCode();
      break label92;
      label312: i6 = this.proxyAuthPassword.hashCode();
      break label112;
      label324: i8 = this.proxyAuthUser.hashCode();
      break label132;
      label336: i10 = this.proxyHost.hashCode();
      break label152;
      label348: i12 = this.requestHeaders.hashCode();
      break label188;
      label362: i14 = this.requestTokenURL.hashCode();
      break label208;
      label374: i16 = this.token.hashCode();
    }
  }

  protected Response httpRequest(String paramString, PostParameter[] paramArrayOfPostParameter, boolean paramBoolean)
    throws WeiboException
  {
    String str = "GET";
    if (paramArrayOfPostParameter != null)
    {
      PostParameter[] arrayOfPostParameter = new PostParameter[1 + paramArrayOfPostParameter.length];
      for (int i = 0; i < paramArrayOfPostParameter.length; i++)
        arrayOfPostParameter[i] = paramArrayOfPostParameter[i];
      arrayOfPostParameter[paramArrayOfPostParameter.length] = new PostParameter("source", Weibo.CONSUMER_KEY);
      str = "POST";
      paramArrayOfPostParameter = arrayOfPostParameter;
    }
    return httpRequest(paramString, paramArrayOfPostParameter, paramBoolean, str);
  }

  // ERROR //
  public Response httpRequest(String paramString1, PostParameter[] paramArrayOfPostParameter, boolean paramBoolean, String paramString2)
    throws WeiboException
  {
    // Byte code:
    //   0: iconst_1
    //   1: aload_0
    //   2: getfield 97	weibo4android/http/HttpClient:retryCount	I
    //   5: iadd
    //   6: istore 5
    //   8: aconst_null
    //   9: astore 6
    //   11: iconst_0
    //   12: istore 7
    //   14: iload 7
    //   16: iload 5
    //   18: if_icmpge +623 -> 641
    //   21: aload_0
    //   22: aload_1
    //   23: invokespecial 506	weibo4android/http/HttpClient:getConnection	(Ljava/lang/String;)Ljava/net/HttpURLConnection;
    //   26: astore 17
    //   28: aload 17
    //   30: iconst_1
    //   31: invokevirtual 510	java/net/HttpURLConnection:setDoInput	(Z)V
    //   34: aload_0
    //   35: aload_1
    //   36: aload_2
    //   37: aload 17
    //   39: iload_3
    //   40: aload 4
    //   42: invokespecial 512	weibo4android/http/HttpClient:setHeaders	(Ljava/lang/String;[Lweibo4android/http/PostParameter;Ljava/net/HttpURLConnection;ZLjava/lang/String;)V
    //   45: aload_2
    //   46: ifnonnull +14 -> 60
    //   49: ldc_w 500
    //   52: aload 4
    //   54: invokevirtual 267	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   57: ifeq +318 -> 375
    //   60: aload 17
    //   62: ldc_w 500
    //   65: invokevirtual 515	java/net/HttpURLConnection:setRequestMethod	(Ljava/lang/String;)V
    //   68: aload 17
    //   70: ldc_w 517
    //   73: ldc_w 519
    //   76: invokevirtual 522	java/net/HttpURLConnection:setRequestProperty	(Ljava/lang/String;Ljava/lang/String;)V
    //   79: aload 17
    //   81: iconst_1
    //   82: invokevirtual 525	java/net/HttpURLConnection:setDoOutput	(Z)V
    //   85: ldc 236
    //   87: astore 18
    //   89: aload_2
    //   90: ifnull +9 -> 99
    //   93: aload_2
    //   94: invokestatic 527	weibo4android/http/HttpClient:encodeParameters	([Lweibo4android/http/PostParameter;)Ljava/lang/String;
    //   97: astore 18
    //   99: ldc_w 529
    //   102: aload 18
    //   104: invokestatic 353	weibo4android/http/HttpClient:log	(Ljava/lang/String;Ljava/lang/String;)V
    //   107: aload 18
    //   109: ldc 220
    //   111: invokevirtual 533	java/lang/String:getBytes	(Ljava/lang/String;)[B
    //   114: astore 19
    //   116: aload 17
    //   118: ldc_w 535
    //   121: aload 19
    //   123: arraylength
    //   124: invokestatic 539	java/lang/Integer:toString	(I)Ljava/lang/String;
    //   127: invokevirtual 522	java/net/HttpURLConnection:setRequestProperty	(Ljava/lang/String;Ljava/lang/String;)V
    //   130: aload 17
    //   132: invokevirtual 543	java/net/HttpURLConnection:getOutputStream	()Ljava/io/OutputStream;
    //   135: astore 20
    //   137: aload 20
    //   139: astore 10
    //   141: aload 10
    //   143: aload 19
    //   145: invokevirtual 549	java/io/OutputStream:write	([B)V
    //   148: aload 10
    //   150: invokevirtual 552	java/io/OutputStream:flush	()V
    //   153: aload 10
    //   155: invokevirtual 555	java/io/OutputStream:close	()V
    //   158: aload 10
    //   160: astore 21
    //   162: new 557	weibo4android/http/Response
    //   165: dup
    //   166: aload 17
    //   168: invokespecial 560	weibo4android/http/Response:<init>	(Ljava/net/HttpURLConnection;)V
    //   171: astore 14
    //   173: aload 17
    //   175: invokevirtual 563	java/net/HttpURLConnection:getResponseCode	()I
    //   178: istore 22
    //   180: iload 22
    //   182: istore 13
    //   184: getstatic 70	weibo4android/http/HttpClient:DEBUG	Z
    //   187: ifeq +235 -> 422
    //   190: ldc_w 565
    //   193: invokestatic 272	weibo4android/http/HttpClient:log	(Ljava/lang/String;)V
    //   196: aload 17
    //   198: invokevirtual 569	java/net/HttpURLConnection:getHeaderFields	()Ljava/util/Map;
    //   201: astore 25
    //   203: aload 25
    //   205: invokeinterface 372 1 0
    //   210: invokeinterface 378 1 0
    //   215: astore 26
    //   217: aload 26
    //   219: invokeinterface 383 1 0
    //   224: ifeq +198 -> 422
    //   227: aload 26
    //   229: invokeinterface 387 1 0
    //   234: checkcast 263	java/lang/String
    //   237: astore 27
    //   239: aload 25
    //   241: aload 27
    //   243: invokeinterface 391 2 0
    //   248: checkcast 571	java/util/List
    //   251: invokeinterface 572 1 0
    //   256: astore 28
    //   258: aload 28
    //   260: invokeinterface 383 1 0
    //   265: ifeq -48 -> 217
    //   268: aload 28
    //   270: invokeinterface 387 1 0
    //   275: checkcast 263	java/lang/String
    //   278: astore 29
    //   280: aload 27
    //   282: ifnull +132 -> 414
    //   285: new 152	java/lang/StringBuilder
    //   288: dup
    //   289: invokespecial 153	java/lang/StringBuilder:<init>	()V
    //   292: aload 27
    //   294: invokevirtual 160	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   297: ldc_w 393
    //   300: invokevirtual 160	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   303: aload 29
    //   305: invokevirtual 160	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   308: invokevirtual 165	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   311: invokestatic 272	weibo4android/http/HttpClient:log	(Ljava/lang/String;)V
    //   314: goto -56 -> 258
    //   317: astore 8
    //   319: aload 21
    //   321: astore 10
    //   323: aload 14
    //   325: astore 6
    //   327: iload 13
    //   329: istore 9
    //   331: aload 10
    //   333: invokevirtual 555	java/io/OutputStream:close	()V
    //   336: aload 8
    //   338: athrow
    //   339: astore 12
    //   341: iload 9
    //   343: istore 13
    //   345: aload 6
    //   347: astore 14
    //   349: iload 7
    //   351: aload_0
    //   352: getfield 97	weibo4android/http/HttpClient:retryCount	I
    //   355: if_icmpne +289 -> 644
    //   358: new 402	weibo4android/WeiboException
    //   361: dup
    //   362: aload 12
    //   364: invokevirtual 575	java/io/IOException:getMessage	()Ljava/lang/String;
    //   367: aload 12
    //   369: iload 13
    //   371: invokespecial 445	weibo4android/WeiboException:<init>	(Ljava/lang/String;Ljava/lang/Exception;I)V
    //   374: athrow
    //   375: ldc_w 404
    //   378: aload 4
    //   380: invokevirtual 267	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   383: ifeq +17 -> 400
    //   386: aload 17
    //   388: ldc_w 404
    //   391: invokevirtual 515	java/net/HttpURLConnection:setRequestMethod	(Ljava/lang/String;)V
    //   394: aconst_null
    //   395: astore 21
    //   397: goto -235 -> 162
    //   400: aload 17
    //   402: ldc_w 491
    //   405: invokevirtual 515	java/net/HttpURLConnection:setRequestMethod	(Ljava/lang/String;)V
    //   408: aconst_null
    //   409: astore 21
    //   411: goto -249 -> 162
    //   414: aload 29
    //   416: invokestatic 272	weibo4android/http/HttpClient:log	(Ljava/lang/String;)V
    //   419: goto -161 -> 258
    //   422: iload 13
    //   424: sipush 200
    //   427: if_icmpeq +62 -> 489
    //   430: iload 13
    //   432: sipush 500
    //   435: if_icmplt +12 -> 447
    //   438: iload 7
    //   440: aload_0
    //   441: getfield 97	weibo4android/http/HttpClient:retryCount	I
    //   444: if_icmpne +53 -> 497
    //   447: new 402	weibo4android/WeiboException
    //   450: dup
    //   451: new 152	java/lang/StringBuilder
    //   454: dup
    //   455: invokespecial 153	java/lang/StringBuilder:<init>	()V
    //   458: iload 13
    //   460: invokestatic 577	weibo4android/http/HttpClient:getCause	(I)Ljava/lang/String;
    //   463: invokevirtual 160	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   466: ldc_w 579
    //   469: invokevirtual 160	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   472: aload 14
    //   474: invokevirtual 582	weibo4android/http/Response:asString	()Ljava/lang/String;
    //   477: invokevirtual 160	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   480: invokevirtual 165	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   483: iload 13
    //   485: invokespecial 585	weibo4android/WeiboException:<init>	(Ljava/lang/String;I)V
    //   488: athrow
    //   489: aload 21
    //   491: invokevirtual 555	java/io/OutputStream:close	()V
    //   494: aload 14
    //   496: areturn
    //   497: aload 21
    //   499: invokevirtual 555	java/io/OutputStream:close	()V
    //   502: aload 14
    //   504: astore 6
    //   506: getstatic 70	weibo4android/http/HttpClient:DEBUG	Z
    //   509: ifeq +14 -> 523
    //   512: aload 6
    //   514: ifnull +9 -> 523
    //   517: aload 6
    //   519: invokevirtual 582	weibo4android/http/Response:asString	()Ljava/lang/String;
    //   522: pop
    //   523: new 152	java/lang/StringBuilder
    //   526: dup
    //   527: invokespecial 153	java/lang/StringBuilder:<init>	()V
    //   530: ldc_w 587
    //   533: invokevirtual 160	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   536: aload_0
    //   537: getfield 102	weibo4android/http/HttpClient:retryIntervalMillis	I
    //   540: invokevirtual 239	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
    //   543: ldc_w 589
    //   546: invokevirtual 160	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   549: invokevirtual 165	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   552: invokestatic 272	weibo4android/http/HttpClient:log	(Ljava/lang/String;)V
    //   555: aload_0
    //   556: getfield 102	weibo4android/http/HttpClient:retryIntervalMillis	I
    //   559: i2l
    //   560: invokestatic 595	java/lang/Thread:sleep	(J)V
    //   563: iinc 7 1
    //   566: goto -552 -> 14
    //   569: astore 23
    //   571: goto -77 -> 494
    //   574: astore 24
    //   576: goto -74 -> 502
    //   579: astore 11
    //   581: goto -245 -> 336
    //   584: astore 15
    //   586: goto -23 -> 563
    //   589: astore 12
    //   591: goto -242 -> 349
    //   594: astore 8
    //   596: iconst_m1
    //   597: istore 9
    //   599: aconst_null
    //   600: astore 10
    //   602: goto -271 -> 331
    //   605: astore 8
    //   607: iconst_m1
    //   608: istore 9
    //   610: goto -279 -> 331
    //   613: astore 8
    //   615: aload 21
    //   617: astore 10
    //   619: iconst_m1
    //   620: istore 9
    //   622: goto -291 -> 331
    //   625: astore 8
    //   627: aload 21
    //   629: astore 10
    //   631: aload 14
    //   633: astore 6
    //   635: iconst_m1
    //   636: istore 9
    //   638: goto -307 -> 331
    //   641: aload 6
    //   643: areturn
    //   644: aload 14
    //   646: astore 6
    //   648: goto -142 -> 506
    //
    // Exception table:
    //   from	to	target	type
    //   184	217	317	finally
    //   217	258	317	finally
    //   258	280	317	finally
    //   285	314	317	finally
    //   414	419	317	finally
    //   438	447	317	finally
    //   447	489	317	finally
    //   331	336	339	java/io/IOException
    //   336	339	339	java/io/IOException
    //   489	494	569	java/lang/Exception
    //   497	502	574	java/lang/Exception
    //   331	336	579	java/lang/Exception
    //   506	512	584	java/lang/InterruptedException
    //   517	523	584	java/lang/InterruptedException
    //   523	563	584	java/lang/InterruptedException
    //   489	494	589	java/io/IOException
    //   497	502	589	java/io/IOException
    //   21	45	594	finally
    //   49	60	594	finally
    //   60	85	594	finally
    //   93	99	594	finally
    //   99	137	594	finally
    //   375	394	594	finally
    //   400	408	594	finally
    //   141	158	605	finally
    //   162	173	613	finally
    //   173	180	625	finally
  }

  public boolean isAuthenticationEnabled()
  {
    return this.oauth != null;
  }

  public Response multPartURL(String paramString1, String paramString2, PostParameter[] paramArrayOfPostParameter, File paramFile, boolean paramBoolean)
    throws WeiboException
  {
    return null;
  }

  public Response multPartURL(String paramString, PostParameter[] paramArrayOfPostParameter, ImageItem paramImageItem, boolean paramBoolean)
    throws WeiboException
  {
    return null;
  }

  public Response post(String paramString)
    throws WeiboException
  {
    return httpRequest(paramString, new PostParameter[0], false);
  }

  public Response post(String paramString1, String paramString2, String paramString3, boolean paramBoolean)
    throws WeiboException
  {
    PostParameter[] arrayOfPostParameter = new PostParameter[1];
    arrayOfPostParameter[0] = new PostParameter(paramString2, paramString3);
    return post(paramString1, arrayOfPostParameter, paramBoolean);
  }

  public Response post(String paramString, boolean paramBoolean)
    throws WeiboException
  {
    return httpRequest(paramString, new PostParameter[0], paramBoolean);
  }

  public Response post(String paramString, PostParameter[] paramArrayOfPostParameter)
    throws WeiboException
  {
    return httpRequest(paramString, paramArrayOfPostParameter, false);
  }

  public Response post(String paramString, PostParameter[] paramArrayOfPostParameter, boolean paramBoolean)
    throws WeiboException
  {
    PostParameter[] arrayOfPostParameter = new PostParameter[1 + paramArrayOfPostParameter.length];
    for (int i = 0; i < paramArrayOfPostParameter.length; i++)
      arrayOfPostParameter[i] = paramArrayOfPostParameter[i];
    arrayOfPostParameter[paramArrayOfPostParameter.length] = new PostParameter("source", Weibo.CONSUMER_KEY);
    return httpRequest(paramString, arrayOfPostParameter, paramBoolean);
  }

  public void setAccessTokenURL(String paramString)
  {
    this.accessTokenURL = paramString;
  }

  public void setAuthenticationURL(String paramString)
  {
    this.authenticationURL = paramString;
  }

  public void setAuthorizationURL(String paramString)
  {
    this.authorizationURL = paramString;
  }

  public void setConnectionTimeout(int paramInt)
  {
    this.connectionTimeout = Configuration.getConnectionTimeout(paramInt);
  }

  public void setOAuthAccessToken(AccessToken paramAccessToken)
  {
    this.oauthToken = paramAccessToken;
  }

  public void setOAuthConsumer(String paramString1, String paramString2)
  {
    String str1 = Configuration.getOAuthConsumerKey(paramString1);
    String str2 = Configuration.getOAuthConsumerSecret(paramString2);
    if ((str1 != null) && (str2 != null) && (str1.length() != 0) && (str2.length() != 0))
      this.oauth = new OAuth(str1, str2);
  }

  public void setProxyAuthPassword(String paramString)
  {
    this.proxyAuthPassword = Configuration.getProxyPassword(paramString);
  }

  public void setProxyAuthUser(String paramString)
  {
    this.proxyAuthUser = Configuration.getProxyUser(paramString);
  }

  public void setProxyHost(String paramString)
  {
    this.proxyHost = Configuration.getProxyHost(paramString);
  }

  public void setProxyPort(int paramInt)
  {
    this.proxyPort = Configuration.getProxyPort(paramInt);
  }

  public void setReadTimeout(int paramInt)
  {
    this.readTimeout = Configuration.getReadTimeout(paramInt);
  }

  public void setRequestHeader(String paramString1, String paramString2)
  {
    this.requestHeaders.put(paramString1, paramString2);
  }

  public void setRequestTokenURL(String paramString)
  {
    this.requestTokenURL = paramString;
  }

  public void setRetryCount(int paramInt)
  {
    if (paramInt >= 0)
    {
      this.retryCount = Configuration.getRetryCount(paramInt);
      return;
    }
    throw new IllegalArgumentException("RetryCount cannot be negative.");
  }

  public void setRetryIntervalSecs(int paramInt)
  {
    if (paramInt >= 0)
    {
      this.retryIntervalMillis = (1000 * Configuration.getRetryIntervalSecs(paramInt));
      return;
    }
    throw new IllegalArgumentException("RetryInterval cannot be negative.");
  }

  public RequestToken setToken(String paramString1, String paramString2)
  {
    this.token = paramString1;
    this.oauthToken = new RequestToken(paramString1, paramString2);
    return (RequestToken)this.oauthToken;
  }

  public void setUserAgent(String paramString)
  {
    setRequestHeader("User-Agent", Configuration.getUserAgent(paramString));
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     weibo4android.http.HttpClient
 * JD-Core Version:    0.6.2
 */