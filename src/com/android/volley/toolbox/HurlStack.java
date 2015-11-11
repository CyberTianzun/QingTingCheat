package com.android.volley.toolbox;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSocketFactory;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.ProtocolVersion;
import org.apache.http.entity.BasicHttpEntity;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicHttpResponse;
import org.apache.http.message.BasicStatusLine;

public class HurlStack
  implements HttpStack
{
  private static final String HEADER_CONTENT_TYPE = "Content-Type";
  private final SSLSocketFactory mSslSocketFactory;
  private final UrlRewriter mUrlRewriter;

  public HurlStack()
  {
    this(null);
  }

  public HurlStack(UrlRewriter paramUrlRewriter)
  {
    this(paramUrlRewriter, null);
  }

  public HurlStack(UrlRewriter paramUrlRewriter, SSLSocketFactory paramSSLSocketFactory)
  {
    this.mUrlRewriter = paramUrlRewriter;
    this.mSslSocketFactory = paramSSLSocketFactory;
  }

  private static void addBodyIfExists(HttpURLConnection paramHttpURLConnection, Request<?> paramRequest)
    throws IOException, AuthFailureError
  {
    byte[] arrayOfByte = paramRequest.getBody();
    if (arrayOfByte != null)
    {
      paramHttpURLConnection.setDoOutput(true);
      paramHttpURLConnection.addRequestProperty("Content-Type", paramRequest.getBodyContentType());
      DataOutputStream localDataOutputStream = new DataOutputStream(paramHttpURLConnection.getOutputStream());
      localDataOutputStream.write(arrayOfByte);
      localDataOutputStream.close();
    }
  }

  private static HttpEntity entityFromConnection(HttpURLConnection paramHttpURLConnection)
  {
    BasicHttpEntity localBasicHttpEntity = new BasicHttpEntity();
    try
    {
      InputStream localInputStream2 = paramHttpURLConnection.getInputStream();
      localInputStream1 = localInputStream2;
      localBasicHttpEntity.setContent(localInputStream1);
      localBasicHttpEntity.setContentLength(paramHttpURLConnection.getContentLength());
      localBasicHttpEntity.setContentEncoding(paramHttpURLConnection.getContentEncoding());
      localBasicHttpEntity.setContentType(paramHttpURLConnection.getContentType());
      return localBasicHttpEntity;
    }
    catch (IOException localIOException)
    {
      while (true)
        InputStream localInputStream1 = paramHttpURLConnection.getErrorStream();
    }
  }

  private HttpURLConnection openConnection(URL paramURL, Request<?> paramRequest)
    throws IOException
  {
    HttpURLConnection localHttpURLConnection = createConnection(paramURL);
    int i = paramRequest.getTimeoutMs();
    localHttpURLConnection.setConnectTimeout(i);
    localHttpURLConnection.setReadTimeout(i);
    localHttpURLConnection.setUseCaches(false);
    localHttpURLConnection.setDoInput(true);
    if (("https".equals(paramURL.getProtocol())) && (this.mSslSocketFactory != null))
      ((HttpsURLConnection)localHttpURLConnection).setSSLSocketFactory(this.mSslSocketFactory);
    return localHttpURLConnection;
  }

  static void setConnectionParametersForRequest(HttpURLConnection paramHttpURLConnection, Request<?> paramRequest)
    throws IOException, AuthFailureError
  {
    switch (paramRequest.getMethod())
    {
    default:
      throw new IllegalStateException("Unknown method type.");
    case -1:
      byte[] arrayOfByte = paramRequest.getPostBody();
      if (arrayOfByte != null)
      {
        paramHttpURLConnection.setDoOutput(true);
        paramHttpURLConnection.setRequestMethod("POST");
        paramHttpURLConnection.addRequestProperty("Content-Type", paramRequest.getPostBodyContentType());
        DataOutputStream localDataOutputStream = new DataOutputStream(paramHttpURLConnection.getOutputStream());
        localDataOutputStream.write(arrayOfByte);
        localDataOutputStream.close();
      }
      return;
    case 0:
      paramHttpURLConnection.setRequestMethod("GET");
      return;
    case 3:
      paramHttpURLConnection.setRequestMethod("DELETE");
      return;
    case 1:
      paramHttpURLConnection.setRequestMethod("POST");
      addBodyIfExists(paramHttpURLConnection, paramRequest);
      return;
    case 2:
    }
    paramHttpURLConnection.setRequestMethod("PUT");
    addBodyIfExists(paramHttpURLConnection, paramRequest);
  }

  protected HttpURLConnection createConnection(URL paramURL)
    throws IOException
  {
    return (HttpURLConnection)paramURL.openConnection();
  }

  public HttpResponse performRequest(Request<?> paramRequest, Map<String, String> paramMap)
    throws IOException, AuthFailureError
  {
    Object localObject = paramRequest.getUrl();
    HashMap localHashMap = new HashMap();
    localHashMap.putAll(paramRequest.getHeaders());
    localHashMap.putAll(paramMap);
    if (this.mUrlRewriter != null)
    {
      String str2 = this.mUrlRewriter.rewriteUrl((String)localObject);
      if (str2 == null)
        throw new IOException("URL blocked by rewriter: " + (String)localObject);
      localObject = str2;
    }
    HttpURLConnection localHttpURLConnection = openConnection(new URL((String)localObject), paramRequest);
    Iterator localIterator1 = localHashMap.keySet().iterator();
    ProtocolVersion localProtocolVersion;
    while (true)
    {
      if (!localIterator1.hasNext())
      {
        setConnectionParametersForRequest(localHttpURLConnection, paramRequest);
        localProtocolVersion = new ProtocolVersion("HTTP", 1, 1);
        if (localHttpURLConnection.getResponseCode() != -1)
          break;
        throw new IOException("Could not retrieve response code from HttpUrlConnection.");
      }
      String str1 = (String)localIterator1.next();
      localHttpURLConnection.addRequestProperty(str1, (String)localHashMap.get(str1));
    }
    BasicHttpResponse localBasicHttpResponse = new BasicHttpResponse(new BasicStatusLine(localProtocolVersion, localHttpURLConnection.getResponseCode(), localHttpURLConnection.getResponseMessage()));
    localBasicHttpResponse.setEntity(entityFromConnection(localHttpURLConnection));
    Iterator localIterator2 = localHttpURLConnection.getHeaderFields().entrySet().iterator();
    while (true)
    {
      if (!localIterator2.hasNext())
        return localBasicHttpResponse;
      Map.Entry localEntry = (Map.Entry)localIterator2.next();
      if (localEntry.getKey() != null)
        localBasicHttpResponse.addHeader(new BasicHeader((String)localEntry.getKey(), (String)((List)localEntry.getValue()).get(0)));
    }
  }

  public static abstract interface UrlRewriter
  {
    public abstract String rewriteUrl(String paramString);
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.android.volley.toolbox.HurlStack
 * JD-Core Version:    0.6.2
 */