package org.apache.commons.httpclient.auth;

import java.util.HashMap;
import java.util.Map;
import org.apache.commons.httpclient.Credentials;
import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpConnection;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.HttpState;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.UsernamePasswordCredentials;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public final class HttpAuthenticator
{
  private static final Log LOG;
  public static final String PROXY_AUTH = "Proxy-Authenticate";
  public static final String PROXY_AUTH_RESP = "Proxy-Authorization";
  public static final String WWW_AUTH = "WWW-Authenticate";
  public static final String WWW_AUTH_RESP = "Authorization";
  static Class class$org$apache$commons$httpclient$auth$HttpAuthenticator;

  static
  {
    Class localClass;
    if (class$org$apache$commons$httpclient$auth$HttpAuthenticator == null)
    {
      localClass = class$("org.apache.commons.httpclient.auth.HttpAuthenticator");
      class$org$apache$commons$httpclient$auth$HttpAuthenticator = localClass;
    }
    while (true)
    {
      LOG = LogFactory.getLog(localClass);
      return;
      localClass = class$org$apache$commons$httpclient$auth$HttpAuthenticator;
    }
  }

  public static boolean authenticate(AuthScheme paramAuthScheme, HttpMethod paramHttpMethod, HttpConnection paramHttpConnection, HttpState paramHttpState)
    throws AuthenticationException
  {
    LOG.trace("enter HttpAuthenticator.authenticate(AuthScheme, HttpMethod, HttpConnection, HttpState)");
    return doAuthenticate(paramAuthScheme, paramHttpMethod, paramHttpConnection, paramHttpState, false);
  }

  public static boolean authenticateDefault(HttpMethod paramHttpMethod, HttpConnection paramHttpConnection, HttpState paramHttpState)
    throws AuthenticationException
  {
    LOG.trace("enter HttpAuthenticator.authenticateDefault(HttpMethod, HttpConnection, HttpState)");
    return doAuthenticateDefault(paramHttpMethod, paramHttpConnection, paramHttpState, false);
  }

  public static boolean authenticateProxy(AuthScheme paramAuthScheme, HttpMethod paramHttpMethod, HttpConnection paramHttpConnection, HttpState paramHttpState)
    throws AuthenticationException
  {
    LOG.trace("enter HttpAuthenticator.authenticateProxy(AuthScheme, HttpMethod, HttpState)");
    return doAuthenticate(paramAuthScheme, paramHttpMethod, paramHttpConnection, paramHttpState, true);
  }

  public static boolean authenticateProxyDefault(HttpMethod paramHttpMethod, HttpConnection paramHttpConnection, HttpState paramHttpState)
    throws AuthenticationException
  {
    LOG.trace("enter HttpAuthenticator.authenticateProxyDefault(HttpMethod, HttpState)");
    return doAuthenticateDefault(paramHttpMethod, paramHttpConnection, paramHttpState, true);
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

  private static boolean doAuthenticate(AuthScheme paramAuthScheme, HttpMethod paramHttpMethod, HttpConnection paramHttpConnection, HttpState paramHttpState, boolean paramBoolean)
    throws AuthenticationException
  {
    if (paramAuthScheme == null)
      throw new IllegalArgumentException("Authentication scheme may not be null");
    if (paramHttpMethod == null)
      throw new IllegalArgumentException("HTTP method may not be null");
    if (paramHttpState == null)
      throw new IllegalArgumentException("HTTP state may not be null");
    String str1 = null;
    String str2;
    StringBuffer localStringBuffer1;
    label109: Credentials localCredentials;
    label153: StringBuffer localStringBuffer2;
    if (paramHttpConnection != null)
    {
      if (paramBoolean)
        str1 = paramHttpConnection.getProxyHost();
    }
    else
    {
      str2 = paramAuthScheme.getRealm();
      if (LOG.isDebugEnabled())
      {
        localStringBuffer1 = new StringBuffer();
        localStringBuffer1.append("Using credentials for ");
        if (str2 != null)
          break label242;
        localStringBuffer1.append("default");
        localStringBuffer1.append(" authentication realm at ");
        localStringBuffer1.append(str1);
        LOG.debug(localStringBuffer1.toString());
      }
      if (!paramBoolean)
        break label269;
      localCredentials = paramHttpState.getProxyCredentials(str2, str1);
      if (localCredentials != null)
        break label309;
      localStringBuffer2 = new StringBuffer();
      localStringBuffer2.append("No credentials available for the ");
      if (str2 != null)
        break label282;
      localStringBuffer2.append("default");
    }
    while (true)
    {
      localStringBuffer2.append(" authentication realm at ");
      localStringBuffer2.append(str1);
      throw new CredentialsNotAvailableException(localStringBuffer2.toString());
      str1 = paramHttpMethod.getParams().getVirtualHost();
      if (str1 != null)
        break;
      str1 = paramHttpConnection.getHost();
      break;
      label242: localStringBuffer1.append('\'');
      localStringBuffer1.append(str2);
      localStringBuffer1.append('\'');
      break label109;
      label269: localCredentials = paramHttpState.getCredentials(str2, str1);
      break label153;
      label282: localStringBuffer2.append('\'');
      localStringBuffer2.append(str2);
      localStringBuffer2.append('\'');
    }
    label309: String str3 = paramAuthScheme.authenticate(localCredentials, paramHttpMethod);
    if (str3 != null)
    {
      if (paramBoolean);
      for (String str4 = "Proxy-Authorization"; ; str4 = "Authorization")
      {
        paramHttpMethod.addRequestHeader(new Header(str4, str3, true));
        return true;
      }
    }
    return false;
  }

  private static boolean doAuthenticateDefault(HttpMethod paramHttpMethod, HttpConnection paramHttpConnection, HttpState paramHttpState, boolean paramBoolean)
    throws AuthenticationException
  {
    if (paramHttpMethod == null)
      throw new IllegalArgumentException("HTTP method may not be null");
    if (paramHttpState == null)
      throw new IllegalArgumentException("HTTP state may not be null");
    String str1 = null;
    Credentials localCredentials;
    if (paramHttpConnection != null)
    {
      if (paramBoolean)
        str1 = paramHttpConnection.getProxyHost();
    }
    else
    {
      if (!paramBoolean)
        break label74;
      localCredentials = paramHttpState.getProxyCredentials(null, str1);
      label58: if (localCredentials != null)
        break label86;
    }
    label74: label86: String str2;
    do
    {
      return false;
      str1 = paramHttpConnection.getHost();
      break;
      localCredentials = paramHttpState.getCredentials(null, str1);
      break label58;
      if (!(localCredentials instanceof UsernamePasswordCredentials))
        throw new InvalidCredentialsException("Credentials cannot be used for basic authentication: " + localCredentials.toString());
      str2 = BasicScheme.authenticate((UsernamePasswordCredentials)localCredentials, paramHttpMethod.getParams().getCredentialCharset());
    }
    while (str2 == null);
    if (paramBoolean);
    for (String str3 = "Proxy-Authorization"; ; str3 = "Authorization")
    {
      paramHttpMethod.addRequestHeader(new Header(str3, str2, true));
      return true;
    }
  }

  public static AuthScheme selectAuthScheme(Header[] paramArrayOfHeader)
    throws MalformedChallengeException
  {
    LOG.trace("enter HttpAuthenticator.selectAuthScheme(Header[])");
    if (paramArrayOfHeader == null)
      throw new IllegalArgumentException("Array of challenges may not be null");
    if (paramArrayOfHeader.length == 0)
      throw new IllegalArgumentException("Array of challenges may not be empty");
    HashMap localHashMap = new HashMap(paramArrayOfHeader.length);
    for (int i = 0; ; i++)
    {
      if (i >= paramArrayOfHeader.length)
      {
        String str2 = (String)localHashMap.get("ntlm");
        if (str2 == null)
          break;
        return new NTLMScheme(str2);
      }
      String str1 = paramArrayOfHeader[i].getValue();
      localHashMap.put(AuthChallengeParser.extractScheme(str1), str1);
    }
    String str3 = (String)localHashMap.get("digest");
    if (str3 != null)
      return new DigestScheme(str3);
    String str4 = (String)localHashMap.get("basic");
    if (str4 != null)
      return new BasicScheme(str4);
    throw new UnsupportedOperationException("Authentication scheme(s) not supported: " + localHashMap.toString());
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     org.apache.commons.httpclient.auth.HttpAuthenticator
 * JD-Core Version:    0.6.2
 */