package org.apache.commons.httpclient.params;

import java.util.ArrayList;
import java.util.Arrays;
import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpVersion;

public class DefaultHttpParamsFactory
  implements HttpParamsFactory
{
  static Class class$org$apache$commons$httpclient$SimpleHttpConnectionManager;
  private HttpParams httpParams;

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

  protected HttpParams createParams()
  {
    HttpClientParams localHttpClientParams = new HttpClientParams(null);
    localHttpClientParams.setParameter("http.useragent", "Jakarta Commons-HttpClient/3.0.1");
    localHttpClientParams.setVersion(HttpVersion.HTTP_1_1);
    Class localClass;
    if (class$org$apache$commons$httpclient$SimpleHttpConnectionManager == null)
    {
      localClass = class$("org.apache.commons.httpclient.SimpleHttpConnectionManager");
      class$org$apache$commons$httpclient$SimpleHttpConnectionManager = localClass;
    }
    while (true)
    {
      localHttpClientParams.setConnectionManagerClass(localClass);
      localHttpClientParams.setCookiePolicy("rfc2109");
      localHttpClientParams.setHttpElementCharset("US-ASCII");
      localHttpClientParams.setContentCharset("ISO-8859-1");
      localHttpClientParams.setParameter("http.method.retry-handler", new DefaultHttpMethodRetryHandler());
      ArrayList localArrayList = new ArrayList();
      localArrayList.addAll(Arrays.asList(new String[] { "EEE, dd MMM yyyy HH:mm:ss zzz", "EEEE, dd-MMM-yy HH:mm:ss zzz", "EEE MMM d HH:mm:ss yyyy", "EEE, dd-MMM-yyyy HH:mm:ss z", "EEE, dd-MMM-yyyy HH-mm-ss z", "EEE, dd MMM yy HH:mm:ss z", "EEE dd-MMM-yyyy HH:mm:ss z", "EEE dd MMM yyyy HH:mm:ss z", "EEE dd-MMM-yyyy HH-mm-ss z", "EEE dd-MMM-yy HH:mm:ss z", "EEE dd MMM yy HH:mm:ss z", "EEE,dd-MMM-yy HH:mm:ss z", "EEE,dd-MMM-yyyy HH:mm:ss z", "EEE, dd-MM-yyyy HH:mm:ss z" }));
      localHttpClientParams.setParameter("http.dateparser.patterns", localArrayList);
      try
      {
        String str7 = System.getProperty("httpclient.useragent");
        str1 = str7;
        if (str1 != null)
          localHttpClientParams.setParameter("http.useragent", str1);
        try
        {
          String str6 = System.getProperty("httpclient.authentication.preemptive");
          str2 = str6;
          String str5;
          if (str2 != null)
          {
            str5 = str2.trim().toLowerCase();
            if (!str5.equals("true"))
              break label292;
            localHttpClientParams.setParameter("http.authentication.preemptive", Boolean.TRUE);
          }
          try
          {
            label251: String str4 = System.getProperty("apache.commons.httpclient.cookiespec");
            str3 = str4;
            if (str3 != null)
            {
              if (!"COMPATIBILITY".equalsIgnoreCase(str3))
                break label314;
              localHttpClientParams.setCookiePolicy("compatibility");
            }
            label292: label314: 
            do
            {
              return localHttpClientParams;
              localClass = class$org$apache$commons$httpclient$SimpleHttpConnectionManager;
              break;
              if (!str5.equals("false"))
                break label251;
              localHttpClientParams.setParameter("http.authentication.preemptive", Boolean.FALSE);
              break label251;
              if ("NETSCAPE_DRAFT".equalsIgnoreCase(str3))
              {
                localHttpClientParams.setCookiePolicy("netscape");
                return localHttpClientParams;
              }
            }
            while (!"RFC2109".equalsIgnoreCase(str3));
            localHttpClientParams.setCookiePolicy("rfc2109");
            return localHttpClientParams;
          }
          catch (SecurityException localSecurityException3)
          {
            while (true)
              String str3 = null;
          }
        }
        catch (SecurityException localSecurityException2)
        {
          while (true)
            String str2 = null;
        }
      }
      catch (SecurityException localSecurityException1)
      {
        while (true)
          String str1 = null;
      }
    }
  }

  public HttpParams getDefaultParams()
  {
    try
    {
      if (this.httpParams == null)
        this.httpParams = createParams();
      HttpParams localHttpParams = this.httpParams;
      return localHttpParams;
    }
    finally
    {
    }
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     org.apache.commons.httpclient.params.DefaultHttpParamsFactory
 * JD-Core Version:    0.6.2
 */