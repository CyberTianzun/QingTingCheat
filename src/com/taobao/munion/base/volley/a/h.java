package com.taobao.munion.base.volley.a;

import com.taobao.munion.base.volley.a;
import com.taobao.munion.base.volley.l;
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

public class h
  implements g
{
  private static final String a = "Content-Type";
  private final a b;
  private final SSLSocketFactory c;

  public h()
  {
    this(null);
  }

  public h(a parama)
  {
    this(parama, null);
  }

  public h(a parama, SSLSocketFactory paramSSLSocketFactory)
  {
    this.b = parama;
    this.c = paramSSLSocketFactory;
  }

  private HttpURLConnection a(URL paramURL, l<?> paraml)
    throws IOException
  {
    HttpURLConnection localHttpURLConnection = a(paramURL);
    int i = paraml.v();
    localHttpURLConnection.setConnectTimeout(i);
    localHttpURLConnection.setReadTimeout(i);
    localHttpURLConnection.setUseCaches(false);
    localHttpURLConnection.setDoInput(true);
    if (("https".equals(paramURL.getProtocol())) && (this.c != null))
      ((HttpsURLConnection)localHttpURLConnection).setSSLSocketFactory(this.c);
    return localHttpURLConnection;
  }

  private static HttpEntity a(HttpURLConnection paramHttpURLConnection)
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

  static void a(HttpURLConnection paramHttpURLConnection, l<?> paraml)
    throws IOException, a
  {
    switch (paraml.b())
    {
    default:
      throw new IllegalStateException("Unknown method type.");
    case -1:
      byte[] arrayOfByte = paraml.o();
      if (arrayOfByte != null)
      {
        paramHttpURLConnection.setDoOutput(true);
        paramHttpURLConnection.setRequestMethod("POST");
        paramHttpURLConnection.addRequestProperty("Content-Type", paraml.n());
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
      b(paramHttpURLConnection, paraml);
      return;
    case 2:
      paramHttpURLConnection.setRequestMethod("PUT");
      b(paramHttpURLConnection, paraml);
      return;
    case 4:
      paramHttpURLConnection.setRequestMethod("HEAD");
      return;
    case 5:
      paramHttpURLConnection.setRequestMethod("OPTIONS");
      return;
    case 6:
      paramHttpURLConnection.setRequestMethod("TRACE");
      return;
    case 7:
    }
    b(paramHttpURLConnection, paraml);
    paramHttpURLConnection.setRequestMethod("PATCH");
  }

  private static void b(HttpURLConnection paramHttpURLConnection, l<?> paraml)
    throws IOException, a
  {
    byte[] arrayOfByte = paraml.s();
    if (arrayOfByte != null)
    {
      paramHttpURLConnection.setDoOutput(true);
      paramHttpURLConnection.addRequestProperty("Content-Type", paraml.r());
      DataOutputStream localDataOutputStream = new DataOutputStream(paramHttpURLConnection.getOutputStream());
      localDataOutputStream.write(arrayOfByte);
      localDataOutputStream.close();
    }
  }

  protected HttpURLConnection a(URL paramURL)
    throws IOException
  {
    return (HttpURLConnection)paramURL.openConnection();
  }

  public HttpResponse a(l<?> paraml, Map<String, String> paramMap)
    throws IOException, a
  {
    String str1 = paraml.f();
    HashMap localHashMap = new HashMap();
    localHashMap.putAll(paramMap);
    localHashMap.putAll(paraml.k());
    String str2;
    if (this.b != null)
    {
      str2 = this.b.a(str1);
      if (str2 == null)
        throw new IOException("URL blocked by rewriter: " + str1);
    }
    else
    {
      str2 = str1;
    }
    HttpURLConnection localHttpURLConnection = a(new URL(str2), paraml);
    Iterator localIterator1 = localHashMap.keySet().iterator();
    while (localIterator1.hasNext())
    {
      String str3 = (String)localIterator1.next();
      localHttpURLConnection.addRequestProperty(str3, (String)localHashMap.get(str3));
    }
    a(localHttpURLConnection, paraml);
    ProtocolVersion localProtocolVersion = new ProtocolVersion("HTTP", 1, 1);
    if (localHttpURLConnection.getResponseCode() == -1)
      throw new IOException("Could not retrieve response code from HttpUrlConnection.");
    BasicHttpResponse localBasicHttpResponse = new BasicHttpResponse(new BasicStatusLine(localProtocolVersion, localHttpURLConnection.getResponseCode(), localHttpURLConnection.getResponseMessage()));
    localBasicHttpResponse.setEntity(a(localHttpURLConnection));
    Iterator localIterator2 = localHttpURLConnection.getHeaderFields().entrySet().iterator();
    while (localIterator2.hasNext())
    {
      Map.Entry localEntry = (Map.Entry)localIterator2.next();
      if (localEntry.getKey() != null)
        localBasicHttpResponse.addHeader(new BasicHeader((String)localEntry.getKey(), (String)((List)localEntry.getValue()).get(0)));
    }
    return localBasicHttpResponse;
  }

  public static abstract interface a
  {
    public abstract String a(String paramString);
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.taobao.munion.base.volley.a.h
 * JD-Core Version:    0.6.2
 */