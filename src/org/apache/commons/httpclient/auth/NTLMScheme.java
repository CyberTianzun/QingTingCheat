package org.apache.commons.httpclient.auth;

import org.apache.commons.httpclient.Credentials;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.NTCredentials;
import org.apache.commons.httpclient.UsernamePasswordCredentials;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class NTLMScheme
  implements AuthScheme
{
  private static final int FAILED = 2147483647;
  private static final int INITIATED = 1;
  private static final Log LOG;
  private static final int TYPE1_MSG_GENERATED = 2;
  private static final int TYPE2_MSG_RECEIVED = 3;
  private static final int TYPE3_MSG_GENERATED = 4;
  private static final int UNINITIATED;
  static Class class$org$apache$commons$httpclient$auth$NTLMScheme;
  private String ntlmchallenge = null;
  private int state;

  static
  {
    Class localClass;
    if (class$org$apache$commons$httpclient$auth$NTLMScheme == null)
    {
      localClass = class$("org.apache.commons.httpclient.auth.NTLMScheme");
      class$org$apache$commons$httpclient$auth$NTLMScheme = localClass;
    }
    while (true)
    {
      LOG = LogFactory.getLog(localClass);
      return;
      localClass = class$org$apache$commons$httpclient$auth$NTLMScheme;
    }
  }

  public NTLMScheme()
  {
    this.state = 0;
  }

  public NTLMScheme(String paramString)
    throws MalformedChallengeException
  {
    processChallenge(paramString);
  }

  public static String authenticate(NTCredentials paramNTCredentials, String paramString)
    throws AuthenticationException
  {
    LOG.trace("enter NTLMScheme.authenticate(NTCredentials, String)");
    if (paramNTCredentials == null)
      throw new IllegalArgumentException("Credentials may not be null");
    String str = new NTLM().getResponseFor(paramString, paramNTCredentials.getUserName(), paramNTCredentials.getPassword(), paramNTCredentials.getHost(), paramNTCredentials.getDomain());
    return "NTLM " + str;
  }

  public static String authenticate(NTCredentials paramNTCredentials, String paramString1, String paramString2)
    throws AuthenticationException
  {
    LOG.trace("enter NTLMScheme.authenticate(NTCredentials, String)");
    if (paramNTCredentials == null)
      throw new IllegalArgumentException("Credentials may not be null");
    NTLM localNTLM = new NTLM();
    localNTLM.setCredentialCharset(paramString2);
    String str = localNTLM.getResponseFor(paramString1, paramNTCredentials.getUserName(), paramNTCredentials.getPassword(), paramNTCredentials.getHost(), paramNTCredentials.getDomain());
    return "NTLM " + str;
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
    LOG.trace("enter NTLMScheme.authenticate(Credentials, String, String)");
    try
    {
      NTCredentials localNTCredentials = (NTCredentials)paramCredentials;
      return authenticate(localNTCredentials, this.ntlmchallenge);
    }
    catch (ClassCastException localClassCastException)
    {
    }
    throw new InvalidCredentialsException("Credentials cannot be used for NTLM authentication: " + paramCredentials.getClass().getName());
  }

  public String authenticate(Credentials paramCredentials, HttpMethod paramHttpMethod)
    throws AuthenticationException
  {
    LOG.trace("enter NTLMScheme.authenticate(Credentials, HttpMethod)");
    if (this.state == 0)
      throw new IllegalStateException("NTLM authentication process has not been initiated");
    while (true)
    {
      NTCredentials localNTCredentials;
      NTLM localNTLM;
      try
      {
        localNTCredentials = (NTCredentials)paramCredentials;
        localNTLM = new NTLM();
        localNTLM.setCredentialCharset(paramHttpMethod.getParams().getCredentialCharset());
        if ((this.state == 1) || (this.state == 2147483647))
        {
          str = localNTLM.getType1Message(localNTCredentials.getHost(), localNTCredentials.getDomain());
          this.state = 2;
          return "NTLM " + str;
        }
      }
      catch (ClassCastException localClassCastException)
      {
        throw new InvalidCredentialsException("Credentials cannot be used for NTLM authentication: " + paramCredentials.getClass().getName());
      }
      String str = localNTLM.getType3Message(localNTCredentials.getUserName(), localNTCredentials.getPassword(), localNTCredentials.getHost(), localNTCredentials.getDomain(), localNTLM.parseType2Message(this.ntlmchallenge));
      this.state = 4;
    }
  }

  public String getID()
  {
    return this.ntlmchallenge;
  }

  public String getParameter(String paramString)
  {
    if (paramString == null)
      throw new IllegalArgumentException("Parameter name may not be null");
    return null;
  }

  public String getRealm()
  {
    return null;
  }

  public String getSchemeName()
  {
    return "ntlm";
  }

  public boolean isComplete()
  {
    return (this.state == 4) || (this.state == 2147483647);
  }

  public boolean isConnectionBased()
  {
    return true;
  }

  public void processChallenge(String paramString)
    throws MalformedChallengeException
  {
    if (!AuthChallengeParser.extractScheme(paramString).equalsIgnoreCase(getSchemeName()))
      throw new MalformedChallengeException("Invalid NTLM challenge: " + paramString);
    int i = paramString.indexOf(' ');
    if (i != -1)
    {
      this.ntlmchallenge = paramString.substring(i, paramString.length()).trim();
      this.state = 3;
      return;
    }
    this.ntlmchallenge = "";
    if (this.state == 0)
    {
      this.state = 1;
      return;
    }
    this.state = 2147483647;
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     org.apache.commons.httpclient.auth.NTLMScheme
 * JD-Core Version:    0.6.2
 */