package org.apache.commons.httpclient.auth;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import org.apache.commons.httpclient.params.HttpParams;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public final class AuthChallengeProcessor
{
  private static final Log LOG;
  static Class class$org$apache$commons$httpclient$auth$AuthChallengeProcessor;
  private HttpParams params = null;

  static
  {
    Class localClass;
    if (class$org$apache$commons$httpclient$auth$AuthChallengeProcessor == null)
    {
      localClass = class$("org.apache.commons.httpclient.auth.AuthChallengeProcessor");
      class$org$apache$commons$httpclient$auth$AuthChallengeProcessor = localClass;
    }
    while (true)
    {
      LOG = LogFactory.getLog(localClass);
      return;
      localClass = class$org$apache$commons$httpclient$auth$AuthChallengeProcessor;
    }
  }

  public AuthChallengeProcessor(HttpParams paramHttpParams)
  {
    if (paramHttpParams == null)
      throw new IllegalArgumentException("Parameter collection may not be null");
    this.params = paramHttpParams;
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

  public AuthScheme processChallenge(AuthState paramAuthState, Map paramMap)
    throws MalformedChallengeException, AuthenticationException
  {
    if (paramAuthState == null)
      throw new IllegalArgumentException("Authentication state may not be null");
    if (paramMap == null)
      throw new IllegalArgumentException("Challenge map may not be null");
    if ((paramAuthState.isPreemptive()) || (paramAuthState.getAuthScheme() == null))
      paramAuthState.setAuthScheme(selectAuthScheme(paramMap));
    AuthScheme localAuthScheme = paramAuthState.getAuthScheme();
    String str1 = localAuthScheme.getSchemeName();
    if (LOG.isDebugEnabled())
      LOG.debug("Using authentication scheme: " + str1);
    String str2 = (String)paramMap.get(str1.toLowerCase());
    if (str2 == null)
      throw new AuthenticationException(str1 + " authorization challenge expected, but not found");
    localAuthScheme.processChallenge(str2);
    LOG.debug("Authorization challenge processed");
    return localAuthScheme;
  }

  public AuthScheme selectAuthScheme(Map paramMap)
    throws AuthChallengeException
  {
    if (paramMap == null)
      throw new IllegalArgumentException("Challenge map may not be null");
    Object localObject1 = (Collection)this.params.getParameter("http.auth.scheme-priority");
    if ((localObject1 == null) || (((Collection)localObject1).isEmpty()))
      localObject1 = AuthPolicy.getDefaultAuthPrefs();
    if (LOG.isDebugEnabled())
      LOG.debug("Supported authentication schemes in the order of preference: " + localObject1);
    Iterator localIterator = ((Collection)localObject1).iterator();
    Object localObject2;
    while (true)
    {
      boolean bool = localIterator.hasNext();
      localObject2 = null;
      if (!bool);
      String str;
      while (true)
      {
        if (localObject2 != null)
          break label282;
        throw new AuthChallengeException("Unable to respond to any of these challenges: " + paramMap);
        str = (String)localIterator.next();
        if ((String)paramMap.get(str.toLowerCase()) != null)
        {
          if (LOG.isInfoEnabled())
            LOG.info(str + " authentication scheme selected");
          try
          {
            AuthScheme localAuthScheme = AuthPolicy.getAuthScheme(str);
            localObject2 = localAuthScheme;
          }
          catch (IllegalStateException localIllegalStateException)
          {
            throw new AuthChallengeException(localIllegalStateException.getMessage());
          }
        }
      }
      if (LOG.isDebugEnabled())
        LOG.debug("Challenge for " + str + " authentication scheme not available");
    }
    label282: return localObject2;
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     org.apache.commons.httpclient.auth.AuthChallengeProcessor
 * JD-Core Version:    0.6.2
 */