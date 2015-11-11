package org.apache.commons.httpclient.auth;

import org.apache.commons.httpclient.util.LangUtils;

public class AuthScope
{
  public static final AuthScope ANY = new AuthScope(ANY_HOST, -1, ANY_REALM, ANY_SCHEME);
  public static final String ANY_HOST = null;
  public static final int ANY_PORT = -1;
  public static final String ANY_REALM = null;
  public static final String ANY_SCHEME = null;
  private String host = null;
  private int port = -1;
  private String realm = null;
  private String scheme = null;

  public AuthScope(String paramString, int paramInt)
  {
    this(paramString, paramInt, ANY_REALM, ANY_SCHEME);
  }

  public AuthScope(String paramString1, int paramInt, String paramString2)
  {
    this(paramString1, paramInt, paramString2, ANY_SCHEME);
  }

  public AuthScope(String paramString1, int paramInt, String paramString2, String paramString3)
  {
    String str1;
    if (paramString1 == null)
    {
      str1 = ANY_HOST;
      this.host = str1;
      if (paramInt < 0)
        paramInt = -1;
      this.port = paramInt;
      if (paramString2 == null)
        paramString2 = ANY_REALM;
      this.realm = paramString2;
      if (paramString3 != null)
        break label89;
    }
    label89: for (String str2 = ANY_SCHEME; ; str2 = paramString3.toUpperCase())
    {
      this.scheme = str2;
      return;
      str1 = paramString1.toLowerCase();
      break;
    }
  }

  public AuthScope(AuthScope paramAuthScope)
  {
    if (paramAuthScope == null)
      throw new IllegalArgumentException("Scope may not be null");
    this.host = paramAuthScope.getHost();
    this.port = paramAuthScope.getPort();
    this.realm = paramAuthScope.getRealm();
    this.scheme = paramAuthScope.getScheme();
  }

  private static boolean paramsEqual(int paramInt1, int paramInt2)
  {
    return paramInt1 == paramInt2;
  }

  private static boolean paramsEqual(String paramString1, String paramString2)
  {
    if (paramString1 == null)
      return paramString1 == paramString2;
    return paramString1.equals(paramString2);
  }

  public boolean equals(Object paramObject)
  {
    boolean bool = true;
    if (paramObject == null)
      bool = false;
    AuthScope localAuthScope;
    do
    {
      do
        return bool;
      while (paramObject == this);
      if (!(paramObject instanceof AuthScope))
        return super.equals(paramObject);
      localAuthScope = (AuthScope)paramObject;
    }
    while ((paramsEqual(this.host, localAuthScope.host)) && (paramsEqual(this.port, localAuthScope.port)) && (paramsEqual(this.realm, localAuthScope.realm)) && (paramsEqual(this.scheme, localAuthScope.scheme)));
    return false;
  }

  public String getHost()
  {
    return this.host;
  }

  public int getPort()
  {
    return this.port;
  }

  public String getRealm()
  {
    return this.realm;
  }

  public String getScheme()
  {
    return this.scheme;
  }

  public int hashCode()
  {
    return LangUtils.hashCode(LangUtils.hashCode(LangUtils.hashCode(LangUtils.hashCode(17, this.host), this.port), this.realm), this.scheme);
  }

  public int match(AuthScope paramAuthScope)
  {
    int i;
    if (paramsEqual(this.scheme, paramAuthScope.scheme))
    {
      i = 0 + 1;
      if (!paramsEqual(this.realm, paramAuthScope.realm))
        break label113;
      i += 2;
      label36: if (!paramsEqual(this.port, paramAuthScope.port))
        break label135;
      i += 4;
      label53: if (!paramsEqual(this.host, paramAuthScope.host))
        break label153;
      i += 8;
    }
    label113: 
    while ((this.host == ANY_HOST) || (paramAuthScope.host == ANY_HOST))
    {
      return i;
      String str1 = this.scheme;
      String str2 = ANY_SCHEME;
      i = 0;
      if (str1 == str2)
        break;
      String str3 = paramAuthScope.scheme;
      String str4 = ANY_SCHEME;
      i = 0;
      if (str3 == str4)
        break;
      return -1;
      if ((this.realm == ANY_REALM) || (paramAuthScope.realm == ANY_REALM))
        break label36;
      return -1;
      if ((this.port == -1) || (paramAuthScope.port == -1))
        break label53;
      return -1;
    }
    label135: label153: return -1;
  }

  public String toString()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    if (this.scheme != null)
    {
      localStringBuffer.append(this.scheme.toUpperCase());
      localStringBuffer.append(' ');
    }
    if (this.realm != null)
    {
      localStringBuffer.append('\'');
      localStringBuffer.append(this.realm);
      localStringBuffer.append('\'');
    }
    while (true)
    {
      if (this.host != null)
      {
        localStringBuffer.append('@');
        localStringBuffer.append(this.host);
        if (this.port >= 0)
        {
          localStringBuffer.append(':');
          localStringBuffer.append(this.port);
        }
      }
      return localStringBuffer.toString();
      localStringBuffer.append("<any realm>");
    }
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     org.apache.commons.httpclient.auth.AuthScope
 * JD-Core Version:    0.6.2
 */