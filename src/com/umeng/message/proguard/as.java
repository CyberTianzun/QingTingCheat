package com.umeng.message.proguard;

import android.content.Context;
import java.io.IOException;
import java.net.URI;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.BufferedHttpEntity;
import org.apache.http.impl.client.AbstractHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;

public class as extends ai
{
  private static final String a = "SyncHttp.client";

  public a get(Context paramContext, String paramString, aq paramaq)
    throws Exception
  {
    return get(paramContext, null, paramString, paramaq);
  }

  public a get(Context paramContext, HttpHost paramHttpHost, String paramString, aq paramaq)
    throws Exception
  {
    HttpGet localHttpGet;
    try
    {
      localHttpGet = new HttpGet(a(paramString, paramaq));
      if (!ag.a(paramContext))
      {
        Q.c("SyncHttp.client", "network connection error[" + localHttpGet.getURI().toString() + "]");
        throw new RuntimeException("network connection error[" + localHttpGet.getURI().toString() + "]");
      }
    }
    catch (IOException localIOException)
    {
      Q.e("SyncHttp.client", "request url error:[" + paramString + "]", localIOException);
      throw localIOException;
    }
    DefaultHttpClient localDefaultHttpClient = getHttpClient();
    if (paramHttpHost != null)
      localDefaultHttpClient.getParams().setParameter("http.route.default-proxy", paramHttpHost);
    HttpResponse localHttpResponse = localDefaultHttpClient.execute(localHttpGet);
    a locala = new a();
    StatusLine localStatusLine = localHttpResponse.getStatusLine();
    locala.a = localStatusLine.getStatusCode();
    if (localStatusLine.getStatusCode() >= 300)
    {
      Q.c("SyncHttp.client", "request url [" + localHttpGet.getURI().toString() + "]  result code:[" + localStatusLine.getStatusCode() + "]");
      return locala;
    }
    HttpEntity localHttpEntity = localHttpResponse.getEntity();
    String str = null;
    if (localHttpEntity != null)
    {
      str = EntityUtils.toString(new BufferedHttpEntity(localHttpEntity), "UTF-8");
      locala.b = str;
    }
    Q.c("SyncHttp.client", "request url:[" + localHttpGet.getURI().toString() + "] : result code [" + localStatusLine.getStatusCode() + "]:[" + str + "]");
    return locala;
  }

  public static class a
  {
    public int a;
    public String b;
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.umeng.message.proguard.as
 * JD-Core Version:    0.6.2
 */