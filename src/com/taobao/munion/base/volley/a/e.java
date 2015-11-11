package com.taobao.munion.base.volley.a;

import com.taobao.munion.base.volley.a;
import com.taobao.munion.base.volley.l;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpHead;
import org.apache.http.client.methods.HttpOptions;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpTrace;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

public class e
  implements g
{
  private static final String b = "Content-Type";
  protected final HttpClient a;

  public e(HttpClient paramHttpClient)
  {
    this.a = paramHttpClient;
  }

  private static List<NameValuePair> a(Map<String, String> paramMap)
  {
    ArrayList localArrayList = new ArrayList(paramMap.size());
    Iterator localIterator = paramMap.keySet().iterator();
    while (localIterator.hasNext())
    {
      String str = (String)localIterator.next();
      localArrayList.add(new BasicNameValuePair(str, (String)paramMap.get(str)));
    }
    return localArrayList;
  }

  private static void a(HttpEntityEnclosingRequestBase paramHttpEntityEnclosingRequestBase, l<?> paraml)
    throws a
  {
    byte[] arrayOfByte = paraml.s();
    if (arrayOfByte != null)
      paramHttpEntityEnclosingRequestBase.setEntity(new ByteArrayEntity(arrayOfByte));
  }

  private static void a(HttpUriRequest paramHttpUriRequest, Map<String, String> paramMap)
  {
    Iterator localIterator = paramMap.keySet().iterator();
    while (localIterator.hasNext())
    {
      String str = (String)localIterator.next();
      paramHttpUriRequest.setHeader(str, (String)paramMap.get(str));
    }
  }

  static HttpUriRequest b(l<?> paraml, Map<String, String> paramMap)
    throws a
  {
    switch (paraml.b())
    {
    default:
      throw new IllegalStateException("Unknown request method.");
    case -1:
      byte[] arrayOfByte = paraml.o();
      if (arrayOfByte != null)
      {
        HttpPost localHttpPost2 = new HttpPost(paraml.f());
        localHttpPost2.addHeader("Content-Type", paraml.n());
        localHttpPost2.setEntity(new ByteArrayEntity(arrayOfByte));
        return localHttpPost2;
      }
      return new HttpGet(paraml.f());
    case 0:
      return new HttpGet(paraml.f());
    case 3:
      return new HttpDelete(paraml.f());
    case 1:
      HttpPost localHttpPost1 = new HttpPost(paraml.f());
      localHttpPost1.addHeader("Content-Type", paraml.r());
      a(localHttpPost1, paraml);
      return localHttpPost1;
    case 2:
      HttpPut localHttpPut = new HttpPut(paraml.f());
      localHttpPut.addHeader("Content-Type", paraml.r());
      a(localHttpPut, paraml);
      return localHttpPut;
    case 4:
      return new HttpHead(paraml.f());
    case 5:
      return new HttpOptions(paraml.f());
    case 6:
      return new HttpTrace(paraml.f());
    case 7:
    }
    a locala = new a(paraml.f());
    locala.addHeader("Content-Type", paraml.r());
    a(locala, paraml);
    return locala;
  }

  public HttpResponse a(l<?> paraml, Map<String, String> paramMap)
    throws IOException, a
  {
    HttpUriRequest localHttpUriRequest = b(paraml, paramMap);
    a(localHttpUriRequest, paramMap);
    a(localHttpUriRequest, paraml.k());
    b(localHttpUriRequest);
    HttpParams localHttpParams = localHttpUriRequest.getParams();
    int i = paraml.v();
    HttpConnectionParams.setConnectionTimeout(localHttpParams, 5000);
    HttpConnectionParams.setSoTimeout(localHttpParams, i);
    return a(localHttpUriRequest);
  }

  protected HttpResponse a(HttpUriRequest paramHttpUriRequest)
    throws IOException
  {
    return this.a.execute(paramHttpUriRequest);
  }

  protected void b(HttpUriRequest paramHttpUriRequest)
    throws IOException
  {
  }

  public static final class a extends HttpEntityEnclosingRequestBase
  {
    public static final String a = "PATCH";

    public a()
    {
    }

    public a(String paramString)
    {
      setURI(URI.create(paramString));
    }

    public a(URI paramURI)
    {
      setURI(paramURI);
    }

    public String getMethod()
    {
      return "PATCH";
    }
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.taobao.munion.base.volley.a.e
 * JD-Core Version:    0.6.2
 */