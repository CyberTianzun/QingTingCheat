package org.apache.commons.httpclient;

import org.apache.commons.httpclient.util.URIUtil;

public class HttpURL extends URI
{
  public static final int DEFAULT_PORT = 80;
  public static final char[] DEFAULT_SCHEME = { 104, 116, 116, 112 };
  public static final int _default_port = 80;
  public static final char[] _default_scheme = DEFAULT_SCHEME;
  static final long serialVersionUID = -7158031098595039459L;

  protected HttpURL()
  {
  }

  public HttpURL(String paramString)
    throws URIException
  {
    parseUriReference(paramString, false);
    checkValid();
  }

  public HttpURL(String paramString1, int paramInt, String paramString2)
    throws URIException
  {
    this(null, null, paramString1, paramInt, paramString2, null, null);
  }

  public HttpURL(String paramString1, int paramInt, String paramString2, String paramString3)
    throws URIException
  {
    this(null, null, paramString1, paramInt, paramString2, paramString3, null);
  }

  public HttpURL(String paramString1, String paramString2)
    throws URIException
  {
    this.protocolCharset = paramString2;
    parseUriReference(paramString1, false);
    checkValid();
  }

  public HttpURL(String paramString1, String paramString2, int paramInt, String paramString3)
    throws URIException
  {
    this(paramString1, paramString2, paramInt, paramString3, null, null);
  }

  public HttpURL(String paramString1, String paramString2, int paramInt, String paramString3, String paramString4)
    throws URIException
  {
    this(paramString1, paramString2, paramInt, paramString3, paramString4, null);
  }

  public HttpURL(String paramString1, String paramString2, int paramInt, String paramString3, String paramString4, String paramString5)
    throws URIException
  {
    StringBuffer localStringBuffer = new StringBuffer();
    if ((paramString1 != null) || (paramString2 != null) || (paramInt != -1))
    {
      this._scheme = DEFAULT_SCHEME;
      localStringBuffer.append(_default_scheme);
      localStringBuffer.append("://");
      if (paramString1 != null)
      {
        localStringBuffer.append(paramString1);
        localStringBuffer.append('@');
      }
      if (paramString2 != null)
      {
        localStringBuffer.append(URIUtil.encode(paramString2, URI.allowed_host));
        if ((paramInt != -1) || (paramInt != 80))
        {
          localStringBuffer.append(':');
          localStringBuffer.append(paramInt);
        }
      }
    }
    if (paramString3 != null)
    {
      if ((URI.scheme != null) && (!paramString3.startsWith("/")))
        throw new URIException(1, "abs_path requested");
      localStringBuffer.append(URIUtil.encode(paramString3, URI.allowed_abs_path));
    }
    if (paramString4 != null)
    {
      localStringBuffer.append('?');
      localStringBuffer.append(URIUtil.encode(paramString4, URI.allowed_query));
    }
    if (paramString5 != null)
    {
      localStringBuffer.append('#');
      localStringBuffer.append(URIUtil.encode(paramString5, URI.allowed_fragment));
    }
    parseUriReference(localStringBuffer.toString(), true);
    checkValid();
  }

  public HttpURL(String paramString1, String paramString2, String paramString3)
    throws URIException
  {
    this(paramString1, paramString2, paramString3, -1, null, null, null);
  }

  public HttpURL(String paramString1, String paramString2, String paramString3, int paramInt)
    throws URIException
  {
    this(paramString1, paramString2, paramString3, paramInt, null, null, null);
  }

  public HttpURL(String paramString1, String paramString2, String paramString3, int paramInt, String paramString4)
    throws URIException
  {
    this(paramString1, paramString2, paramString3, paramInt, paramString4, null, null);
  }

  public HttpURL(String paramString1, String paramString2, String paramString3, int paramInt, String paramString4, String paramString5)
    throws URIException
  {
    this(paramString1, paramString2, paramString3, paramInt, paramString4, paramString5, null);
  }

  public HttpURL(String paramString1, String paramString2, String paramString3, int paramInt, String paramString4, String paramString5, String paramString6)
    throws URIException
  {
    this(toUserinfo(paramString1, paramString2), paramString3, paramInt, paramString4, paramString5, paramString6);
  }

  public HttpURL(String paramString1, String paramString2, String paramString3, String paramString4)
    throws URIException
  {
    this(null, null, paramString1, -1, paramString2, paramString3, paramString4);
  }

  public HttpURL(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5)
    throws URIException
  {
    this(paramString1, paramString2, -1, paramString3, paramString4, paramString5);
  }

  public HttpURL(HttpURL paramHttpURL, String paramString)
    throws URIException
  {
    this(paramHttpURL, new HttpURL(paramString));
  }

  public HttpURL(HttpURL paramHttpURL1, HttpURL paramHttpURL2)
    throws URIException
  {
    super(paramHttpURL1, paramHttpURL2);
    checkValid();
  }

  public HttpURL(char[] paramArrayOfChar)
    throws URIException, NullPointerException
  {
    parseUriReference(new String(paramArrayOfChar), true);
    checkValid();
  }

  public HttpURL(char[] paramArrayOfChar, String paramString)
    throws URIException, NullPointerException
  {
    this.protocolCharset = paramString;
    parseUriReference(new String(paramArrayOfChar), true);
    checkValid();
  }

  protected static String toUserinfo(String paramString1, String paramString2)
    throws URIException
  {
    if (paramString1 == null)
      return null;
    StringBuffer localStringBuffer = new StringBuffer(20);
    localStringBuffer.append(URIUtil.encode(paramString1, URI.allowed_within_userinfo));
    if (paramString2 == null)
      return localStringBuffer.toString();
    localStringBuffer.append(':');
    localStringBuffer.append(URIUtil.encode(paramString2, URI.allowed_within_userinfo));
    return localStringBuffer.toString();
  }

  protected void checkValid()
    throws URIException
  {
    if ((!equals(this._scheme, DEFAULT_SCHEME)) && (this._scheme != null))
      throw new URIException(1, "wrong class use");
  }

  public String getEscapedPassword()
  {
    char[] arrayOfChar = getRawPassword();
    if (arrayOfChar == null)
      return null;
    return new String(arrayOfChar);
  }

  public String getEscapedUser()
  {
    char[] arrayOfChar = getRawUser();
    if (arrayOfChar == null)
      return null;
    return new String(arrayOfChar);
  }

  public String getPassword()
    throws URIException
  {
    char[] arrayOfChar = getRawPassword();
    if (arrayOfChar == null)
      return null;
    return URI.decode(arrayOfChar, getProtocolCharset());
  }

  public int getPort()
  {
    if (this._port == -1)
      return 80;
    return this._port;
  }

  public char[] getRawAboveHierPath()
    throws URIException
  {
    char[] arrayOfChar = getRawCurrentHierPath();
    if ((arrayOfChar == null) || (arrayOfChar.length == 0))
      return URI.rootPath;
    return getRawCurrentHierPath(arrayOfChar);
  }

  public char[] getRawCurrentHierPath()
    throws URIException
  {
    if ((this._path == null) || (this._path.length == 0))
      return URI.rootPath;
    return super.getRawCurrentHierPath(this._path);
  }

  public char[] getRawPassword()
  {
    int i = indexFirstOf(this._userinfo, ':');
    if (i == -1)
      return null;
    int j = -1 + (this._userinfo.length - i);
    char[] arrayOfChar = new char[j];
    System.arraycopy(this._userinfo, i + 1, arrayOfChar, 0, j);
    return arrayOfChar;
  }

  public char[] getRawPath()
  {
    char[] arrayOfChar = super.getRawPath();
    if ((arrayOfChar == null) || (arrayOfChar.length == 0))
      arrayOfChar = URI.rootPath;
    return arrayOfChar;
  }

  public char[] getRawScheme()
  {
    if (this._scheme == null)
      return null;
    return DEFAULT_SCHEME;
  }

  public char[] getRawUser()
  {
    if ((this._userinfo == null) || (this._userinfo.length == 0))
      return null;
    int i = indexFirstOf(this._userinfo, ':');
    if (i == -1)
      return this._userinfo;
    char[] arrayOfChar = new char[i];
    System.arraycopy(this._userinfo, 0, arrayOfChar, 0, i);
    return arrayOfChar;
  }

  public String getScheme()
  {
    if (this._scheme == null)
      return null;
    return new String(DEFAULT_SCHEME);
  }

  public String getUser()
    throws URIException
  {
    char[] arrayOfChar = getRawUser();
    if (arrayOfChar == null)
      return null;
    return URI.decode(arrayOfChar, getProtocolCharset());
  }

  public void setEscapedPassword(String paramString)
    throws URIException
  {
    if (paramString == null);
    for (char[] arrayOfChar = null; ; arrayOfChar = paramString.toCharArray())
    {
      setRawPassword(arrayOfChar);
      return;
    }
  }

  public void setEscapedUser(String paramString)
    throws URIException, NullPointerException
  {
    setRawUser(paramString.toCharArray());
  }

  public void setEscapedUserinfo(String paramString1, String paramString2)
    throws URIException, NullPointerException
  {
    char[] arrayOfChar1 = paramString1.toCharArray();
    if (paramString2 == null);
    for (char[] arrayOfChar2 = null; ; arrayOfChar2 = paramString2.toCharArray())
    {
      setRawUserinfo(arrayOfChar1, arrayOfChar2);
      return;
    }
  }

  public void setPassword(String paramString)
    throws URIException
  {
    if (paramString == null);
    for (char[] arrayOfChar = null; ; arrayOfChar = URI.encode(paramString, URI.allowed_within_userinfo, getProtocolCharset()))
    {
      setRawPassword(arrayOfChar);
      return;
    }
  }

  public void setQuery(String paramString1, String paramString2)
    throws URIException, NullPointerException
  {
    StringBuffer localStringBuffer = new StringBuffer();
    String str = getProtocolCharset();
    localStringBuffer.append(URI.encode(paramString1, URI.allowed_within_query, str));
    localStringBuffer.append('=');
    localStringBuffer.append(URI.encode(paramString2, URI.allowed_within_query, str));
    this._query = localStringBuffer.toString().toCharArray();
    setURI();
  }

  public void setQuery(String[] paramArrayOfString1, String[] paramArrayOfString2)
    throws URIException, NullPointerException
  {
    int i = paramArrayOfString1.length;
    if (i != paramArrayOfString2.length)
      throw new URIException("wrong array size of query");
    StringBuffer localStringBuffer = new StringBuffer();
    String str = getProtocolCharset();
    for (int j = 0; ; j++)
    {
      if (j >= i)
      {
        this._query = localStringBuffer.toString().toCharArray();
        setURI();
        return;
      }
      localStringBuffer.append(URI.encode(paramArrayOfString1[j], URI.allowed_within_query, str));
      localStringBuffer.append('=');
      localStringBuffer.append(URI.encode(paramArrayOfString2[j], URI.allowed_within_query, str));
      if (j + 1 < i)
        localStringBuffer.append('&');
    }
  }

  public void setRawPassword(char[] paramArrayOfChar)
    throws URIException
  {
    if ((paramArrayOfChar != null) && (!validate(paramArrayOfChar, URI.within_userinfo)))
      throw new URIException(3, "escaped password not valid");
    if ((getRawUser() == null) || (getRawUser().length == 0))
      throw new URIException(1, "username required");
    String str1 = new String(getRawUser());
    String str2 = new String(paramArrayOfChar);
    StringBuffer localStringBuffer = new StringBuffer().append(str1);
    String str3;
    String str4;
    String str5;
    if (str2 == null)
    {
      str3 = "";
      str4 = str3;
      str5 = new String(getRawHost());
      if (this._port != -1)
        break label206;
    }
    label206: for (String str6 = str5; ; str6 = str5 + ":" + this._port)
    {
      String str7 = str4 + "@" + str6;
      this._userinfo = str4.toCharArray();
      this._authority = str7.toCharArray();
      setURI();
      return;
      str3 = ":" + str2;
      break;
    }
  }

  public void setRawUser(char[] paramArrayOfChar)
    throws URIException
  {
    if ((paramArrayOfChar == null) || (paramArrayOfChar.length == 0))
      throw new URIException(1, "user required");
    if (!validate(paramArrayOfChar, URI.within_userinfo))
      throw new URIException(3, "escaped user not valid");
    String str1 = new String(paramArrayOfChar);
    String str2 = new String(getRawPassword());
    StringBuffer localStringBuffer = new StringBuffer().append(str1);
    String str3;
    String str4;
    String str5;
    if (str2 == null)
    {
      str3 = "";
      str4 = str3;
      str5 = new String(getRawHost());
      if (this._port != -1)
        break label198;
    }
    label198: for (String str6 = str5; ; str6 = str5 + ":" + this._port)
    {
      String str7 = str4 + "@" + str6;
      this._userinfo = str4.toCharArray();
      this._authority = str7.toCharArray();
      setURI();
      return;
      str3 = ":" + str2;
      break;
    }
  }

  public void setRawUserinfo(char[] paramArrayOfChar1, char[] paramArrayOfChar2)
    throws URIException
  {
    if ((paramArrayOfChar1 == null) || (paramArrayOfChar1.length == 0))
      throw new URIException(1, "user required");
    if ((!validate(paramArrayOfChar1, URI.within_userinfo)) || ((paramArrayOfChar2 != null) && (!validate(paramArrayOfChar2, URI.within_userinfo))))
      throw new URIException(3, "escaped userinfo not valid");
    String str1 = new String(paramArrayOfChar1);
    String str2;
    String str3;
    label97: String str4;
    String str5;
    if (paramArrayOfChar2 == null)
    {
      str2 = null;
      StringBuffer localStringBuffer = new StringBuffer().append(str1);
      if (str2 != null)
        break label197;
      str3 = "";
      str4 = str3;
      str5 = new String(getRawHost());
      if (this._port != -1)
        break label223;
    }
    label197: label223: for (String str6 = str5; ; str6 = str5 + ":" + this._port)
    {
      String str7 = str4 + "@" + str6;
      this._userinfo = str4.toCharArray();
      this._authority = str7.toCharArray();
      setURI();
      return;
      str2 = new String(paramArrayOfChar2);
      break;
      str3 = ":" + str2;
      break label97;
    }
  }

  protected void setURI()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    if (this._scheme != null)
    {
      localStringBuffer.append(this._scheme);
      localStringBuffer.append(':');
    }
    if (this._is_net_path)
    {
      localStringBuffer.append("//");
      if (this._authority != null)
      {
        if (this._userinfo == null)
          break label163;
        if (this._host != null)
        {
          localStringBuffer.append(this._host);
          if (this._port != -1)
          {
            localStringBuffer.append(':');
            localStringBuffer.append(this._port);
          }
        }
      }
    }
    if ((this._opaque != null) && (this._is_opaque_part))
      localStringBuffer.append(this._opaque);
    while (true)
    {
      if (this._query != null)
      {
        localStringBuffer.append('?');
        localStringBuffer.append(this._query);
      }
      this._uri = localStringBuffer.toString().toCharArray();
      this.hash = 0;
      return;
      label163: localStringBuffer.append(this._authority);
      break;
      if ((this._path != null) && (this._path.length != 0))
        localStringBuffer.append(this._path);
    }
  }

  public void setUser(String paramString)
    throws URIException, NullPointerException
  {
    setRawUser(URI.encode(paramString, URI.allowed_within_userinfo, getProtocolCharset()));
  }

  public void setUserinfo(String paramString1, String paramString2)
    throws URIException, NullPointerException
  {
    String str = getProtocolCharset();
    char[] arrayOfChar1 = URI.encode(paramString1, URI.within_userinfo, str);
    if (paramString2 == null);
    for (char[] arrayOfChar2 = null; ; arrayOfChar2 = URI.encode(paramString2, URI.within_userinfo, str))
    {
      setRawUserinfo(arrayOfChar1, arrayOfChar2);
      return;
    }
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     org.apache.commons.httpclient.HttpURL
 * JD-Core Version:    0.6.2
 */