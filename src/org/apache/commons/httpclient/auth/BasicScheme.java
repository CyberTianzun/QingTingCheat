package org.apache.commons.httpclient.auth;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.httpclient.Credentials;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.UsernamePasswordCredentials;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.httpclient.util.EncodingUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class BasicScheme extends RFC2617Scheme
{
  private static final Log LOG;
  static Class class$org$apache$commons$httpclient$auth$BasicScheme;
  private boolean complete;

  static
  {
    Class localClass;
    if (class$org$apache$commons$httpclient$auth$BasicScheme == null)
    {
      localClass = class$("org.apache.commons.httpclient.auth.BasicScheme");
      class$org$apache$commons$httpclient$auth$BasicScheme = localClass;
    }
    while (true)
    {
      LOG = LogFactory.getLog(localClass);
      return;
      localClass = class$org$apache$commons$httpclient$auth$BasicScheme;
    }
  }

  public BasicScheme()
  {
    this.complete = false;
  }

  public BasicScheme(String paramString)
    throws MalformedChallengeException
  {
    super(paramString);
    this.complete = true;
  }

  public static String authenticate(UsernamePasswordCredentials paramUsernamePasswordCredentials)
  {
    return authenticate(paramUsernamePasswordCredentials, "ISO-8859-1");
  }

  public static String authenticate(UsernamePasswordCredentials paramUsernamePasswordCredentials, String paramString)
  {
    LOG.trace("enter BasicScheme.authenticate(UsernamePasswordCredentials, String)");
    if (paramUsernamePasswordCredentials == null)
      throw new IllegalArgumentException("Credentials may not be null");
    if ((paramString == null) || (paramString.length() == 0))
      throw new IllegalArgumentException("charset may not be null or empty");
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append(paramUsernamePasswordCredentials.getUserName());
    localStringBuffer.append(":");
    localStringBuffer.append(paramUsernamePasswordCredentials.getPassword());
    return "Basic " + EncodingUtil.getAsciiString(Base64.encodeBase64(EncodingUtil.getBytes(localStringBuffer.toString(), paramString)));
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

  public String authenticate(Credentials paramCredentials, String paramString1, String paramString2)
    throws AuthenticationException
  {
    LOG.trace("enter BasicScheme.authenticate(Credentials, String, String)");
    try
    {
      UsernamePasswordCredentials localUsernamePasswordCredentials = (UsernamePasswordCredentials)paramCredentials;
      return authenticate(localUsernamePasswordCredentials);
    }
    catch (ClassCastException localClassCastException)
    {
    }
    throw new InvalidCredentialsException("Credentials cannot be used for basic authentication: " + paramCredentials.getClass().getName());
  }

  public String authenticate(Credentials paramCredentials, HttpMethod paramHttpMethod)
    throws AuthenticationException
  {
    LOG.trace("enter BasicScheme.authenticate(Credentials, HttpMethod)");
    if (paramHttpMethod == null)
      throw new IllegalArgumentException("Method may not be null");
    try
    {
      UsernamePasswordCredentials localUsernamePasswordCredentials = (UsernamePasswordCredentials)paramCredentials;
      return authenticate(localUsernamePasswordCredentials, paramHttpMethod.getParams().getCredentialCharset());
    }
    catch (ClassCastException localClassCastException)
    {
    }
    throw new InvalidCredentialsException("Credentials cannot be used for basic authentication: " + paramCredentials.getClass().getName());
  }

  public String getSchemeName()
  {
    return "basic";
  }

  public boolean isComplete()
  {
    return this.complete;
  }

  public boolean isConnectionBased()
  {
    return false;
  }

  public void processChallenge(String paramString)
    throws MalformedChallengeException
  {
    super.processChallenge(paramString);
    this.complete = true;
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     org.apache.commons.httpclient.auth.BasicScheme
 * JD-Core Version:    0.6.2
 */