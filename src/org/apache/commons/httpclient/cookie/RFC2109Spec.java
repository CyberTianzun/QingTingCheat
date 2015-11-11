package org.apache.commons.httpclient.cookie;

import org.apache.commons.httpclient.Cookie;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.util.ParameterFormatter;
import org.apache.commons.logging.Log;

public class RFC2109Spec extends CookieSpecBase
{
  private final ParameterFormatter formatter = new ParameterFormatter();

  public RFC2109Spec()
  {
    this.formatter.setAlwaysUseQuotes(true);
  }

  private void formatCookieAsVer(StringBuffer paramStringBuffer, Cookie paramCookie, int paramInt)
  {
    String str = paramCookie.getValue();
    if (str == null)
      str = "";
    formatParam(paramStringBuffer, new NameValuePair(paramCookie.getName(), str), paramInt);
    if ((paramCookie.getPath() != null) && (paramCookie.isPathAttributeSpecified()))
    {
      paramStringBuffer.append("; ");
      formatParam(paramStringBuffer, new NameValuePair("$Path", paramCookie.getPath()), paramInt);
    }
    if ((paramCookie.getDomain() != null) && (paramCookie.isDomainAttributeSpecified()))
    {
      paramStringBuffer.append("; ");
      formatParam(paramStringBuffer, new NameValuePair("$Domain", paramCookie.getDomain()), paramInt);
    }
  }

  private void formatParam(StringBuffer paramStringBuffer, NameValuePair paramNameValuePair, int paramInt)
  {
    if (paramInt < 1)
    {
      paramStringBuffer.append(paramNameValuePair.getName());
      paramStringBuffer.append("=");
      if (paramNameValuePair.getValue() != null)
        paramStringBuffer.append(paramNameValuePair.getValue());
      return;
    }
    this.formatter.format(paramStringBuffer, paramNameValuePair);
  }

  public boolean domainMatch(String paramString1, String paramString2)
  {
    return (paramString1.equals(paramString2)) || ((paramString2.startsWith(".")) && (paramString1.endsWith(paramString2)));
  }

  public String formatCookie(Cookie paramCookie)
  {
    CookieSpecBase.LOG.trace("enter RFC2109Spec.formatCookie(Cookie)");
    if (paramCookie == null)
      throw new IllegalArgumentException("Cookie may not be null");
    int i = paramCookie.getVersion();
    StringBuffer localStringBuffer = new StringBuffer();
    formatParam(localStringBuffer, new NameValuePair("$Version", Integer.toString(i)), i);
    localStringBuffer.append("; ");
    formatCookieAsVer(localStringBuffer, paramCookie, i);
    return localStringBuffer.toString();
  }

  public String formatCookies(Cookie[] paramArrayOfCookie)
  {
    CookieSpecBase.LOG.trace("enter RFC2109Spec.formatCookieHeader(Cookie[])");
    int i = 2147483647;
    int j = 0;
    StringBuffer localStringBuffer;
    if (j >= paramArrayOfCookie.length)
    {
      localStringBuffer = new StringBuffer();
      formatParam(localStringBuffer, new NameValuePair("$Version", Integer.toString(i)), i);
    }
    for (int k = 0; ; k++)
    {
      if (k >= paramArrayOfCookie.length)
      {
        return localStringBuffer.toString();
        Cookie localCookie = paramArrayOfCookie[j];
        if (localCookie.getVersion() < i)
          i = localCookie.getVersion();
        j++;
        break;
      }
      localStringBuffer.append("; ");
      formatCookieAsVer(localStringBuffer, paramArrayOfCookie[k], i);
    }
  }

  public void parseAttribute(NameValuePair paramNameValuePair, Cookie paramCookie)
    throws MalformedCookieException
  {
    if (paramNameValuePair == null)
      throw new IllegalArgumentException("Attribute may not be null.");
    if (paramCookie == null)
      throw new IllegalArgumentException("Cookie may not be null.");
    String str1 = paramNameValuePair.getName().toLowerCase();
    String str2 = paramNameValuePair.getValue();
    if (str1.equals("path"))
    {
      if (str2 == null)
        throw new MalformedCookieException("Missing value for path attribute");
      if (str2.trim().equals(""))
        throw new MalformedCookieException("Blank value for path attribute");
      paramCookie.setPath(str2);
      paramCookie.setPathAttributeSpecified(true);
      return;
    }
    if (str1.equals("version"))
    {
      if (str2 == null)
        throw new MalformedCookieException("Missing value for version attribute");
      try
      {
        paramCookie.setVersion(Integer.parseInt(str2));
        return;
      }
      catch (NumberFormatException localNumberFormatException)
      {
        throw new MalformedCookieException("Invalid version: " + localNumberFormatException.getMessage());
      }
    }
    super.parseAttribute(paramNameValuePair, paramCookie);
  }

  public void validate(String paramString1, int paramInt, String paramString2, boolean paramBoolean, Cookie paramCookie)
    throws MalformedCookieException
  {
    CookieSpecBase.LOG.trace("enter RFC2109Spec.validate(String, int, String, boolean, Cookie)");
    super.validate(paramString1, paramInt, paramString2, paramBoolean, paramCookie);
    if (paramCookie.getName().indexOf(' ') != -1)
      throw new MalformedCookieException("Cookie name may not contain blanks");
    if (paramCookie.getName().startsWith("$"))
      throw new MalformedCookieException("Cookie name may not start with $");
    if ((paramCookie.isDomainAttributeSpecified()) && (!paramCookie.getDomain().equals(paramString1)))
    {
      if (!paramCookie.getDomain().startsWith("."))
        throw new MalformedCookieException("Domain attribute \"" + paramCookie.getDomain() + "\" violates RFC 2109: domain must start with a dot");
      int i = paramCookie.getDomain().indexOf('.', 1);
      if ((i < 0) || (i == -1 + paramCookie.getDomain().length()))
        throw new MalformedCookieException("Domain attribute \"" + paramCookie.getDomain() + "\" violates RFC 2109: domain must contain an embedded dot");
      String str = paramString1.toLowerCase();
      if (!str.endsWith(paramCookie.getDomain()))
        throw new MalformedCookieException("Illegal domain attribute \"" + paramCookie.getDomain() + "\". Domain of origin: \"" + str + "\"");
      if (str.substring(0, str.length() - paramCookie.getDomain().length()).indexOf('.') != -1)
        throw new MalformedCookieException("Domain attribute \"" + paramCookie.getDomain() + "\" violates RFC 2109: host minus domain may not contain any dots");
    }
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     org.apache.commons.httpclient.cookie.RFC2109Spec
 * JD-Core Version:    0.6.2
 */