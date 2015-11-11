package org.apache.commons.httpclient.cookie;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.StringTokenizer;
import org.apache.commons.httpclient.Cookie;
import org.apache.commons.httpclient.HeaderElement;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.logging.Log;

public class NetscapeDraftSpec extends CookieSpecBase
{
  private static boolean isSpecialDomain(String paramString)
  {
    String str = paramString.toUpperCase();
    return (str.endsWith(".COM")) || (str.endsWith(".EDU")) || (str.endsWith(".NET")) || (str.endsWith(".GOV")) || (str.endsWith(".MIL")) || (str.endsWith(".ORG")) || (str.endsWith(".INT"));
  }

  public boolean domainMatch(String paramString1, String paramString2)
  {
    return paramString1.endsWith(paramString2);
  }

  public Cookie[] parse(String paramString1, int paramInt, String paramString2, boolean paramBoolean, String paramString3)
    throws MalformedCookieException
  {
    CookieSpecBase.LOG.trace("enter NetscapeDraftSpec.parse(String, port, path, boolean, Header)");
    if (paramString1 == null)
      throw new IllegalArgumentException("Host of origin may not be null");
    if (paramString1.trim().equals(""))
      throw new IllegalArgumentException("Host of origin may not be blank");
    if (paramInt < 0)
      throw new IllegalArgumentException("Invalid port: " + paramInt);
    if (paramString2 == null)
      throw new IllegalArgumentException("Path of origin may not be null.");
    if (paramString3 == null)
      throw new IllegalArgumentException("Header may not be null.");
    if (paramString2.trim().equals(""))
      paramString2 = "/";
    String str1 = paramString1.toLowerCase();
    String str2 = paramString2;
    int i = str2.lastIndexOf("/");
    if (i >= 0)
    {
      if (i == 0)
        i = 1;
      str2 = str2.substring(0, i);
    }
    HeaderElement localHeaderElement = new HeaderElement(paramString3.toCharArray());
    Cookie localCookie = new Cookie(str1, localHeaderElement.getName(), localHeaderElement.getValue(), str2, null, false);
    NameValuePair[] arrayOfNameValuePair = localHeaderElement.getParameters();
    if (arrayOfNameValuePair != null);
    for (int j = 0; ; j++)
    {
      if (j >= arrayOfNameValuePair.length)
        return new Cookie[] { localCookie };
      parseAttribute(arrayOfNameValuePair[j], localCookie);
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
    if (str1.equals("expires"))
    {
      if (str2 == null)
        throw new MalformedCookieException("Missing value for expires attribute");
      try
      {
        paramCookie.setExpiryDate(new SimpleDateFormat("EEE, dd-MMM-yyyy HH:mm:ss z", Locale.US).parse(str2));
        return;
      }
      catch (ParseException localParseException)
      {
        throw new MalformedCookieException("Invalid expires attribute: " + localParseException.getMessage());
      }
    }
    super.parseAttribute(paramNameValuePair, paramCookie);
  }

  public void validate(String paramString1, int paramInt, String paramString2, boolean paramBoolean, Cookie paramCookie)
    throws MalformedCookieException
  {
    CookieSpecBase.LOG.trace("enterNetscapeDraftCookieProcessor RCF2109CookieProcessor.validate(Cookie)");
    super.validate(paramString1, paramInt, paramString2, paramBoolean, paramCookie);
    if (paramString1.indexOf(".") >= 0)
    {
      int i = new StringTokenizer(paramCookie.getDomain(), ".").countTokens();
      if (isSpecialDomain(paramCookie.getDomain()))
      {
        if (i < 2)
          throw new MalformedCookieException("Domain attribute \"" + paramCookie.getDomain() + "\" violates the Netscape cookie specification for " + "special domains");
      }
      else if (i < 3)
        throw new MalformedCookieException("Domain attribute \"" + paramCookie.getDomain() + "\" violates the Netscape cookie specification");
    }
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     org.apache.commons.httpclient.cookie.NetscapeDraftSpec
 * JD-Core Version:    0.6.2
 */