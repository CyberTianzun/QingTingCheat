package org.apache.commons.httpclient.cookie;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public abstract class CookiePolicy
{
  public static final String BROWSER_COMPATIBILITY = "compatibility";
  public static final int COMPATIBILITY = 0;
  public static final String DEFAULT = "default";
  public static final String IGNORE_COOKIES = "ignoreCookies";
  protected static final Log LOG;
  public static final String NETSCAPE = "netscape";
  public static final int NETSCAPE_DRAFT = 1;
  public static final int RFC2109 = 2;
  public static final String RFC_2109 = "rfc2109";
  private static Map SPECS = Collections.synchronizedMap(new HashMap());
  static Class class$org$apache$commons$httpclient$cookie$CookiePolicy;
  static Class class$org$apache$commons$httpclient$cookie$CookieSpecBase;
  static Class class$org$apache$commons$httpclient$cookie$IgnoreCookiesSpec;
  static Class class$org$apache$commons$httpclient$cookie$NetscapeDraftSpec;
  static Class class$org$apache$commons$httpclient$cookie$RFC2109Spec;
  private static int defaultPolicy;

  static
  {
    Class localClass1;
    Class localClass2;
    label51: Class localClass3;
    label73: Class localClass4;
    label95: Class localClass5;
    label119: Class localClass6;
    if (class$org$apache$commons$httpclient$cookie$RFC2109Spec == null)
    {
      localClass1 = class$("org.apache.commons.httpclient.cookie.RFC2109Spec");
      class$org$apache$commons$httpclient$cookie$RFC2109Spec = localClass1;
      registerCookieSpec("default", localClass1);
      if (class$org$apache$commons$httpclient$cookie$RFC2109Spec != null)
        break label164;
      localClass2 = class$("org.apache.commons.httpclient.cookie.RFC2109Spec");
      class$org$apache$commons$httpclient$cookie$RFC2109Spec = localClass2;
      registerCookieSpec("rfc2109", localClass2);
      if (class$org$apache$commons$httpclient$cookie$CookieSpecBase != null)
        break label171;
      localClass3 = class$("org.apache.commons.httpclient.cookie.CookieSpecBase");
      class$org$apache$commons$httpclient$cookie$CookieSpecBase = localClass3;
      registerCookieSpec("compatibility", localClass3);
      if (class$org$apache$commons$httpclient$cookie$NetscapeDraftSpec != null)
        break label178;
      localClass4 = class$("org.apache.commons.httpclient.cookie.NetscapeDraftSpec");
      class$org$apache$commons$httpclient$cookie$NetscapeDraftSpec = localClass4;
      registerCookieSpec("netscape", localClass4);
      if (class$org$apache$commons$httpclient$cookie$IgnoreCookiesSpec != null)
        break label185;
      localClass5 = class$("org.apache.commons.httpclient.cookie.IgnoreCookiesSpec");
      class$org$apache$commons$httpclient$cookie$IgnoreCookiesSpec = localClass5;
      registerCookieSpec("ignoreCookies", localClass5);
      defaultPolicy = 2;
      if (class$org$apache$commons$httpclient$cookie$CookiePolicy != null)
        break label193;
      localClass6 = class$("org.apache.commons.httpclient.cookie.CookiePolicy");
      class$org$apache$commons$httpclient$cookie$CookiePolicy = localClass6;
    }
    while (true)
    {
      LOG = LogFactory.getLog(localClass6);
      return;
      localClass1 = class$org$apache$commons$httpclient$cookie$RFC2109Spec;
      break;
      label164: localClass2 = class$org$apache$commons$httpclient$cookie$RFC2109Spec;
      break label51;
      label171: localClass3 = class$org$apache$commons$httpclient$cookie$CookieSpecBase;
      break label73;
      label178: localClass4 = class$org$apache$commons$httpclient$cookie$NetscapeDraftSpec;
      break label95;
      label185: localClass5 = class$org$apache$commons$httpclient$cookie$IgnoreCookiesSpec;
      break label119;
      label193: localClass6 = class$org$apache$commons$httpclient$cookie$CookiePolicy;
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

  public static CookieSpec getCompatibilitySpec()
  {
    return getSpecByPolicy(0);
  }

  public static CookieSpec getCookieSpec(String paramString)
    throws IllegalStateException
  {
    if (paramString == null)
      throw new IllegalArgumentException("Id may not be null");
    Class localClass = (Class)SPECS.get(paramString.toLowerCase());
    if (localClass != null)
      try
      {
        CookieSpec localCookieSpec = (CookieSpec)localClass.newInstance();
        return localCookieSpec;
      }
      catch (Exception localException)
      {
        LOG.error("Error initializing cookie spec: " + paramString, localException);
        throw new IllegalStateException(paramString + " cookie spec implemented by " + localClass.getName() + " could not be initialized");
      }
    throw new IllegalStateException("Unsupported cookie spec " + paramString);
  }

  public static int getDefaultPolicy()
  {
    return defaultPolicy;
  }

  public static CookieSpec getDefaultSpec()
  {
    try
    {
      CookieSpec localCookieSpec = getCookieSpec("default");
      return localCookieSpec;
    }
    catch (IllegalStateException localIllegalStateException)
    {
      LOG.warn("Default cookie policy is not registered");
    }
    return new RFC2109Spec();
  }

  public static CookieSpec getSpecByPolicy(int paramInt)
  {
    switch (paramInt)
    {
    default:
      return getDefaultSpec();
    case 0:
      return new CookieSpecBase();
    case 1:
      return new NetscapeDraftSpec();
    case 2:
    }
    return new RFC2109Spec();
  }

  public static CookieSpec getSpecByVersion(int paramInt)
  {
    switch (paramInt)
    {
    default:
      return getDefaultSpec();
    case 0:
      return new NetscapeDraftSpec();
    case 1:
    }
    return new RFC2109Spec();
  }

  public static void registerCookieSpec(String paramString, Class paramClass)
  {
    if (paramString == null)
      throw new IllegalArgumentException("Id may not be null");
    if (paramClass == null)
      throw new IllegalArgumentException("Cookie spec class may not be null");
    SPECS.put(paramString.toLowerCase(), paramClass);
  }

  public static void setDefaultPolicy(int paramInt)
  {
    defaultPolicy = paramInt;
  }

  public static void unregisterCookieSpec(String paramString)
  {
    if (paramString == null)
      throw new IllegalArgumentException("Id may not be null");
    SPECS.remove(paramString.toLowerCase());
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     org.apache.commons.httpclient.cookie.CookiePolicy
 * JD-Core Version:    0.6.2
 */