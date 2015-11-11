package org.apache.commons.httpclient;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.apache.commons.httpclient.auth.AuthScope;
import org.apache.commons.httpclient.cookie.CookiePolicy;
import org.apache.commons.httpclient.cookie.CookieSpec;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class HttpState
{
  private static final Log LOG;
  public static final String PREEMPTIVE_DEFAULT = "false";
  public static final String PREEMPTIVE_PROPERTY = "httpclient.authentication.preemptive";
  static Class class$org$apache$commons$httpclient$HttpState;
  private int cookiePolicy = -1;
  private ArrayList cookies = new ArrayList();
  private HashMap credMap = new HashMap();
  private boolean preemptive = false;
  private HashMap proxyCred = new HashMap();

  static
  {
    Class localClass;
    if (class$org$apache$commons$httpclient$HttpState == null)
    {
      localClass = class$("org.apache.commons.httpclient.HttpState");
      class$org$apache$commons$httpclient$HttpState = localClass;
    }
    while (true)
    {
      LOG = LogFactory.getLog(localClass);
      return;
      localClass = class$org$apache$commons$httpclient$HttpState;
    }
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

  private static String getCookiesStringRepresentation(List paramList)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    Iterator localIterator = paramList.iterator();
    while (true)
    {
      if (!localIterator.hasNext())
        return localStringBuffer.toString();
      Cookie localCookie = (Cookie)localIterator.next();
      if (localStringBuffer.length() > 0)
        localStringBuffer.append("#");
      localStringBuffer.append(localCookie.toExternalForm());
    }
  }

  private static String getCredentialsStringRepresentation(Map paramMap)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    Iterator localIterator = paramMap.keySet().iterator();
    while (true)
    {
      if (!localIterator.hasNext())
        return localStringBuffer.toString();
      Object localObject = localIterator.next();
      Credentials localCredentials = (Credentials)paramMap.get(localObject);
      if (localStringBuffer.length() > 0)
        localStringBuffer.append(", ");
      localStringBuffer.append(localObject);
      localStringBuffer.append("#");
      localStringBuffer.append(localCredentials.toString());
    }
  }

  private static Credentials matchCredentials(HashMap paramHashMap, AuthScope paramAuthScope)
  {
    Credentials localCredentials = (Credentials)paramHashMap.get(paramAuthScope);
    int i;
    Object localObject;
    Iterator localIterator;
    if (localCredentials == null)
    {
      i = -1;
      localObject = null;
      localIterator = paramHashMap.keySet().iterator();
    }
    while (true)
    {
      if (!localIterator.hasNext())
      {
        if (localObject != null)
          localCredentials = (Credentials)paramHashMap.get(localObject);
        return localCredentials;
      }
      AuthScope localAuthScope = (AuthScope)localIterator.next();
      int j = paramAuthScope.match(localAuthScope);
      if (j > i)
      {
        i = j;
        localObject = localAuthScope;
      }
    }
  }

  public void addCookie(Cookie paramCookie)
  {
    try
    {
      LOG.trace("enter HttpState.addCookie(Cookie)");
      Iterator localIterator;
      if (paramCookie != null)
      {
        localIterator = this.cookies.iterator();
        if (localIterator.hasNext())
          break label52;
      }
      while (true)
      {
        if (!paramCookie.isExpired())
          this.cookies.add(paramCookie);
        return;
        label52: if (!paramCookie.equals((Cookie)localIterator.next()))
          break;
        localIterator.remove();
      }
    }
    finally
    {
    }
  }

  public void addCookies(Cookie[] paramArrayOfCookie)
  {
    try
    {
      LOG.trace("enter HttpState.addCookies(Cookie[])");
      if (paramArrayOfCookie != null);
      for (int i = 0; ; i++)
      {
        int j = paramArrayOfCookie.length;
        if (i >= j)
          return;
        addCookie(paramArrayOfCookie[i]);
      }
    }
    finally
    {
    }
  }

  public void clear()
  {
    clearCookies();
    clearCredentials();
    clearProxyCredentials();
  }

  public void clearCookies()
  {
    try
    {
      this.cookies.clear();
      return;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }

  public void clearCredentials()
  {
    this.credMap.clear();
  }

  public void clearProxyCredentials()
  {
    this.proxyCred.clear();
  }

  public int getCookiePolicy()
  {
    return this.cookiePolicy;
  }

  public Cookie[] getCookies()
  {
    try
    {
      LOG.trace("enter HttpState.getCookies()");
      Cookie[] arrayOfCookie = (Cookie[])this.cookies.toArray(new Cookie[this.cookies.size()]);
      return arrayOfCookie;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }

  public Cookie[] getCookies(String paramString1, int paramInt, String paramString2, boolean paramBoolean)
  {
    try
    {
      LOG.trace("enter HttpState.getCookies(String, int, String, boolean)");
      CookieSpec localCookieSpec = CookiePolicy.getDefaultSpec();
      ArrayList localArrayList = new ArrayList(this.cookies.size());
      int i = 0;
      int j = this.cookies.size();
      while (true)
      {
        if (i >= j)
        {
          Cookie[] arrayOfCookie = (Cookie[])localArrayList.toArray(new Cookie[localArrayList.size()]);
          return arrayOfCookie;
        }
        Cookie localCookie = (Cookie)this.cookies.get(i);
        if (localCookieSpec.match(paramString1, paramInt, paramString2, paramBoolean, localCookie))
          localArrayList.add(localCookie);
        i++;
      }
    }
    finally
    {
    }
  }

  public Credentials getCredentials(String paramString1, String paramString2)
  {
    try
    {
      LOG.trace("enter HttpState.getCredentials(String, String");
      Credentials localCredentials = matchCredentials(this.credMap, new AuthScope(paramString2, -1, paramString1, AuthScope.ANY_SCHEME));
      return localCredentials;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }

  public Credentials getCredentials(AuthScope paramAuthScope)
  {
    if (paramAuthScope == null)
      try
      {
        throw new IllegalArgumentException("Authentication scope may not be null");
      }
      finally
      {
      }
    LOG.trace("enter HttpState.getCredentials(AuthScope)");
    Credentials localCredentials = matchCredentials(this.credMap, paramAuthScope);
    return localCredentials;
  }

  public Credentials getProxyCredentials(String paramString1, String paramString2)
  {
    try
    {
      LOG.trace("enter HttpState.getCredentials(String, String");
      Credentials localCredentials = matchCredentials(this.proxyCred, new AuthScope(paramString2, -1, paramString1, AuthScope.ANY_SCHEME));
      return localCredentials;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }

  public Credentials getProxyCredentials(AuthScope paramAuthScope)
  {
    if (paramAuthScope == null)
      try
      {
        throw new IllegalArgumentException("Authentication scope may not be null");
      }
      finally
      {
      }
    LOG.trace("enter HttpState.getProxyCredentials(AuthScope)");
    Credentials localCredentials = matchCredentials(this.proxyCred, paramAuthScope);
    return localCredentials;
  }

  public boolean isAuthenticationPreemptive()
  {
    return this.preemptive;
  }

  public boolean purgeExpiredCookies()
  {
    try
    {
      LOG.trace("enter HttpState.purgeExpiredCookies()");
      boolean bool = purgeExpiredCookies(new Date());
      return bool;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }

  public boolean purgeExpiredCookies(Date paramDate)
  {
    try
    {
      LOG.trace("enter HttpState.purgeExpiredCookies(Date)");
      boolean bool1 = false;
      Iterator localIterator = this.cookies.iterator();
      while (true)
      {
        boolean bool2 = localIterator.hasNext();
        if (!bool2)
          return bool1;
        if (((Cookie)localIterator.next()).isExpired(paramDate))
        {
          localIterator.remove();
          bool1 = true;
        }
      }
    }
    finally
    {
    }
  }

  public void setAuthenticationPreemptive(boolean paramBoolean)
  {
    this.preemptive = paramBoolean;
  }

  public void setCookiePolicy(int paramInt)
  {
    this.cookiePolicy = paramInt;
  }

  public void setCredentials(String paramString1, String paramString2, Credentials paramCredentials)
  {
    try
    {
      LOG.trace("enter HttpState.setCredentials(String, String, Credentials)");
      this.credMap.put(new AuthScope(paramString2, -1, paramString1, AuthScope.ANY_SCHEME), paramCredentials);
      return;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }

  public void setCredentials(AuthScope paramAuthScope, Credentials paramCredentials)
  {
    if (paramAuthScope == null)
      try
      {
        throw new IllegalArgumentException("Authentication scope may not be null");
      }
      finally
      {
      }
    LOG.trace("enter HttpState.setCredentials(AuthScope, Credentials)");
    this.credMap.put(paramAuthScope, paramCredentials);
  }

  public void setProxyCredentials(String paramString1, String paramString2, Credentials paramCredentials)
  {
    try
    {
      LOG.trace("enter HttpState.setProxyCredentials(String, String, Credentials");
      this.proxyCred.put(new AuthScope(paramString2, -1, paramString1, AuthScope.ANY_SCHEME), paramCredentials);
      return;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }

  public void setProxyCredentials(AuthScope paramAuthScope, Credentials paramCredentials)
  {
    if (paramAuthScope == null)
      try
      {
        throw new IllegalArgumentException("Authentication scope may not be null");
      }
      finally
      {
      }
    LOG.trace("enter HttpState.setProxyCredentials(AuthScope, Credentials)");
    this.proxyCred.put(paramAuthScope, paramCredentials);
  }

  public String toString()
  {
    try
    {
      StringBuffer localStringBuffer = new StringBuffer();
      localStringBuffer.append("[");
      localStringBuffer.append(getCredentialsStringRepresentation(this.proxyCred));
      localStringBuffer.append(" | ");
      localStringBuffer.append(getCredentialsStringRepresentation(this.credMap));
      localStringBuffer.append(" | ");
      localStringBuffer.append(getCookiesStringRepresentation(this.cookies));
      localStringBuffer.append("]");
      String str = localStringBuffer.toString();
      return str;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     org.apache.commons.httpclient.HttpState
 * JD-Core Version:    0.6.2
 */