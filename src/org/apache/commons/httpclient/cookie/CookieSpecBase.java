package org.apache.commons.httpclient.cookie;

import java.util.Collection;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import org.apache.commons.httpclient.Cookie;
import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HeaderElement;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.util.DateParseException;
import org.apache.commons.httpclient.util.DateUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class CookieSpecBase
  implements CookieSpec
{
  protected static final Log LOG;
  static Class class$org$apache$commons$httpclient$cookie$CookieSpec;
  private Collection datepatterns = null;

  static
  {
    Class localClass;
    if (class$org$apache$commons$httpclient$cookie$CookieSpec == null)
    {
      localClass = class$("org.apache.commons.httpclient.cookie.CookieSpec");
      class$org$apache$commons$httpclient$cookie$CookieSpec = localClass;
    }
    while (true)
    {
      LOG = LogFactory.getLog(localClass);
      return;
      localClass = class$org$apache$commons$httpclient$cookie$CookieSpec;
    }
  }

  private static void addInPathOrder(List paramList, Cookie paramCookie)
  {
    for (int i = 0; ; i++)
    {
      if (i >= paramList.size());
      while (paramCookie.compare(paramCookie, (Cookie)paramList.get(i)) > 0)
      {
        paramList.add(i, paramCookie);
        return;
      }
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

  public boolean domainMatch(String paramString1, String paramString2)
  {
    if (paramString1.equals(paramString2));
    do
    {
      return true;
      if (!paramString2.startsWith("."))
        paramString2 = "." + paramString2;
    }
    while ((paramString1.endsWith(paramString2)) || (paramString1.equals(paramString2.substring(1))));
    return false;
  }

  public String formatCookie(Cookie paramCookie)
  {
    LOG.trace("enter CookieSpecBase.formatCookie(Cookie)");
    if (paramCookie == null)
      throw new IllegalArgumentException("Cookie may not be null");
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append(paramCookie.getName());
    localStringBuffer.append("=");
    String str = paramCookie.getValue();
    if (str != null)
      localStringBuffer.append(str);
    return localStringBuffer.toString();
  }

  public Header formatCookieHeader(Cookie paramCookie)
  {
    LOG.trace("enter CookieSpecBase.formatCookieHeader(Cookie)");
    return new Header("Cookie", formatCookie(paramCookie));
  }

  public Header formatCookieHeader(Cookie[] paramArrayOfCookie)
  {
    LOG.trace("enter CookieSpecBase.formatCookieHeader(Cookie[])");
    return new Header("Cookie", formatCookies(paramArrayOfCookie));
  }

  public String formatCookies(Cookie[] paramArrayOfCookie)
    throws IllegalArgumentException
  {
    LOG.trace("enter CookieSpecBase.formatCookies(Cookie[])");
    if (paramArrayOfCookie == null)
      throw new IllegalArgumentException("Cookie array may not be null");
    if (paramArrayOfCookie.length == 0)
      throw new IllegalArgumentException("Cookie array may not be empty");
    StringBuffer localStringBuffer = new StringBuffer();
    for (int i = 0; ; i++)
    {
      if (i >= paramArrayOfCookie.length)
        return localStringBuffer.toString();
      if (i > 0)
        localStringBuffer.append("; ");
      localStringBuffer.append(formatCookie(paramArrayOfCookie[i]));
    }
  }

  public Collection getValidDateFormats()
  {
    return this.datepatterns;
  }

  public boolean match(String paramString1, int paramInt, String paramString2, boolean paramBoolean, Cookie paramCookie)
  {
    boolean bool = true;
    LOG.trace("enter CookieSpecBase.match(String, int, String, boolean, Cookie");
    if (paramString1 == null)
      throw new IllegalArgumentException("Host of origin may not be null");
    if (paramString1.trim().equals(""))
      throw new IllegalArgumentException("Host of origin may not be blank");
    if (paramInt < 0)
      throw new IllegalArgumentException("Invalid port: " + paramInt);
    if (paramString2 == null)
      throw new IllegalArgumentException("Path of origin may not be null.");
    if (paramCookie == null)
      throw new IllegalArgumentException("Cookie may not be null");
    if (paramString2.trim().equals(""))
      paramString2 = "/";
    String str = paramString1.toLowerCase();
    if (paramCookie.getDomain() == null)
    {
      LOG.warn("Invalid cookie state: domain not specified");
      return false;
    }
    if (paramCookie.getPath() == null)
    {
      LOG.warn("Invalid cookie state: path not specified");
      return false;
    }
    if (((paramCookie.getExpiryDate() == null) || (paramCookie.getExpiryDate().after(new Date()))) && (domainMatch(str, paramCookie.getDomain())) && (pathMatch(paramString2, paramCookie.getPath())))
      if (paramCookie.getSecure())
        if (!paramBoolean)
          break label246;
    while (true)
    {
      return bool;
      paramBoolean = bool;
      break;
      label246: bool = false;
    }
  }

  public Cookie[] match(String paramString1, int paramInt, String paramString2, boolean paramBoolean, Cookie[] paramArrayOfCookie)
  {
    LOG.trace("enter CookieSpecBase.match(String, int, String, boolean, Cookie[])");
    if (paramArrayOfCookie == null)
      return null;
    LinkedList localLinkedList = new LinkedList();
    for (int i = 0; ; i++)
    {
      if (i >= paramArrayOfCookie.length)
        return (Cookie[])localLinkedList.toArray(new Cookie[localLinkedList.size()]);
      if (match(paramString1, paramInt, paramString2, paramBoolean, paramArrayOfCookie[i]))
        addInPathOrder(localLinkedList, paramArrayOfCookie[i]);
    }
  }

  public Cookie[] parse(String paramString1, int paramInt, String paramString2, boolean paramBoolean, String paramString3)
    throws MalformedCookieException
  {
    LOG.trace("enter CookieSpecBase.parse(String, port, path, boolean, Header)");
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
    int j = paramString3.toLowerCase().indexOf("expires=");
    int k = 0;
    int i2;
    int i3;
    if (j != -1)
    {
      i2 = j + "expires=".length();
      i3 = paramString3.indexOf(";", i2);
      if (i3 == -1)
        i3 = paramString3.length();
    }
    try
    {
      DateUtil.parseDate(paramString3.substring(i2, i3), this.datepatterns);
      k = 1;
      HeaderElement[] arrayOfHeaderElement;
      if (k != 0)
      {
        arrayOfHeaderElement = new HeaderElement[1];
        arrayOfHeaderElement[0] = new HeaderElement(paramString3.toCharArray());
      }
      Cookie[] arrayOfCookie;
      int m;
      while (true)
      {
        arrayOfCookie = new Cookie[arrayOfHeaderElement.length];
        m = 0;
        if (m < arrayOfHeaderElement.length)
          break;
        return arrayOfCookie;
        arrayOfHeaderElement = HeaderElement.parseElements(paramString3.toCharArray());
      }
      HeaderElement localHeaderElement = arrayOfHeaderElement[m];
      while (true)
      {
        Cookie localCookie;
        NameValuePair[] arrayOfNameValuePair;
        int n;
        try
        {
          localCookie = new Cookie(str1, localHeaderElement.getName(), localHeaderElement.getValue(), str2, null, false);
          arrayOfNameValuePair = localHeaderElement.getParameters();
          if (arrayOfNameValuePair != null)
          {
            n = 0;
            int i1 = arrayOfNameValuePair.length;
            if (n < i1);
          }
          else
          {
            arrayOfCookie[m] = localCookie;
            m++;
          }
        }
        catch (IllegalArgumentException localIllegalArgumentException)
        {
          throw new MalformedCookieException(localIllegalArgumentException.getMessage());
        }
        parseAttribute(arrayOfNameValuePair[n], localCookie);
        n++;
      }
    }
    catch (DateParseException localDateParseException)
    {
      while (true)
        k = 0;
    }
  }

  public Cookie[] parse(String paramString1, int paramInt, String paramString2, boolean paramBoolean, Header paramHeader)
    throws MalformedCookieException
  {
    LOG.trace("enter CookieSpecBase.parse(String, port, path, boolean, String)");
    if (paramHeader == null)
      throw new IllegalArgumentException("Header may not be null.");
    return parse(paramString1, paramInt, paramString2, paramBoolean, paramHeader.getValue());
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
      if ((str2 == null) || (str2.trim().equals("")))
        str2 = "/";
      paramCookie.setPath(str2);
      paramCookie.setPathAttributeSpecified(true);
    }
    do
    {
      return;
      if (str1.equals("domain"))
      {
        if (str2 == null)
          throw new MalformedCookieException("Missing value for domain attribute");
        if (str2.trim().equals(""))
          throw new MalformedCookieException("Blank value for domain attribute");
        paramCookie.setDomain(str2);
        paramCookie.setDomainAttributeSpecified(true);
        return;
      }
      if (str1.equals("max-age"))
      {
        if (str2 == null)
          throw new MalformedCookieException("Missing value for max-age attribute");
        try
        {
          int i = Integer.parseInt(str2);
          paramCookie.setExpiryDate(new Date(System.currentTimeMillis() + 1000L * i));
          return;
        }
        catch (NumberFormatException localNumberFormatException)
        {
          throw new MalformedCookieException("Invalid max-age attribute: " + localNumberFormatException.getMessage());
        }
      }
      if (str1.equals("secure"))
      {
        paramCookie.setSecure(true);
        return;
      }
      if (str1.equals("comment"))
      {
        paramCookie.setComment(str2);
        return;
      }
      if (str1.equals("expires"))
      {
        if (str2 == null)
          throw new MalformedCookieException("Missing value for expires attribute");
        try
        {
          paramCookie.setExpiryDate(DateUtil.parseDate(str2, this.datepatterns));
          return;
        }
        catch (DateParseException localDateParseException)
        {
          LOG.debug("Error parsing cookie date", localDateParseException);
          throw new MalformedCookieException("Unable to parse expiration date parameter: " + str2);
        }
      }
    }
    while (!LOG.isDebugEnabled());
    LOG.debug("Unrecognized cookie attribute: " + paramNameValuePair.toString());
  }

  public boolean pathMatch(String paramString1, String paramString2)
  {
    boolean bool = paramString1.startsWith(paramString2);
    if ((bool) && (paramString1.length() != paramString2.length()) && (!paramString2.endsWith("/")))
    {
      if (paramString1.charAt(paramString2.length()) == CookieSpec.PATH_DELIM_CHAR)
        bool = true;
    }
    else
      return bool;
    return false;
  }

  public void setValidDateFormats(Collection paramCollection)
  {
    this.datepatterns = paramCollection;
  }

  public void validate(String paramString1, int paramInt, String paramString2, boolean paramBoolean, Cookie paramCookie)
    throws MalformedCookieException
  {
    LOG.trace("enter CookieSpecBase.validate(String, port, path, boolean, Cookie)");
    if (paramString1 == null)
      throw new IllegalArgumentException("Host of origin may not be null");
    if (paramString1.trim().equals(""))
      throw new IllegalArgumentException("Host of origin may not be blank");
    if (paramInt < 0)
      throw new IllegalArgumentException("Invalid port: " + paramInt);
    if (paramString2 == null)
      throw new IllegalArgumentException("Path of origin may not be null.");
    if (paramString2.trim().equals(""))
      paramString2 = "/";
    String str1 = paramString1.toLowerCase();
    if (paramCookie.getVersion() < 0)
      throw new MalformedCookieException("Illegal version number " + paramCookie.getValue());
    if (str1.indexOf(".") >= 0)
    {
      if (!str1.endsWith(paramCookie.getDomain()))
      {
        String str2 = paramCookie.getDomain();
        if (str2.startsWith("."))
          str2 = str2.substring(1, str2.length());
        if (!str1.equals(str2))
          throw new MalformedCookieException("Illegal domain attribute \"" + paramCookie.getDomain() + "\". Domain of origin: \"" + str1 + "\"");
      }
    }
    else if (!str1.equals(paramCookie.getDomain()))
      throw new MalformedCookieException("Illegal domain attribute \"" + paramCookie.getDomain() + "\". Domain of origin: \"" + str1 + "\"");
    if (!paramString2.startsWith(paramCookie.getPath()))
      throw new MalformedCookieException("Illegal path attribute \"" + paramCookie.getPath() + "\". Path of origin: \"" + paramString2 + "\"");
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     org.apache.commons.httpclient.cookie.CookieSpecBase
 * JD-Core Version:    0.6.2
 */