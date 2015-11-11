package com.umeng.message.proguard;

import android.content.Context;
import java.lang.ref.WeakReference;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HttpContext;

public class al extends ai
{
  protected static final String a = "AsyncHttp.client";
  private final ThreadPoolExecutor b = (ThreadPoolExecutor)Executors.newFixedThreadPool(5);
  private final Map<Context, List<WeakReference<Future<?>>>> c = new WeakHashMap();

  private HttpEntity a(aq paramaq)
  {
    HttpEntity localHttpEntity = null;
    if (paramaq != null)
      localHttpEntity = paramaq.a();
    return localHttpEntity;
  }

  private void a(Context paramContext, DefaultHttpClient paramDefaultHttpClient, HttpContext paramHttpContext, HttpHost paramHttpHost, HttpUriRequest paramHttpUriRequest, String paramString, an paraman)
  {
    if (paramString != null)
      paramHttpUriRequest.addHeader("Content-Type", paramString);
    Future localFuture = this.b.submit(new am(paramContext, paramDefaultHttpClient, paramHttpContext, paramHttpHost, paramHttpUriRequest, paraman));
    if (paramContext != null)
    {
      Object localObject = (List)this.c.get(paramContext);
      if (localObject == null)
      {
        localObject = new LinkedList();
        this.c.put(paramContext, localObject);
      }
      ((List)localObject).add(new WeakReference(localFuture));
    }
  }

  public void cancelRequests(Context paramContext, boolean paramBoolean)
  {
    List localList = (List)this.c.get(paramContext);
    if (localList != null)
    {
      Iterator localIterator = localList.iterator();
      while (localIterator.hasNext())
      {
        Future localFuture = (Future)((WeakReference)localIterator.next()).get();
        if (localFuture != null)
          localFuture.cancel(paramBoolean);
      }
    }
    this.c.remove(paramContext);
  }

  public void get(Context paramContext, String paramString, an paraman)
  {
    get(paramContext, null, paramString, null, null, paraman);
  }

  public void get(Context paramContext, String paramString, aq paramaq, an paraman)
  {
    get(paramContext, null, paramString, null, paramaq, paraman);
  }

  public void get(Context paramContext, String paramString, HttpHost paramHttpHost, an paraman)
  {
    get(paramContext, paramHttpHost, paramString, null, null, paraman);
  }

  public void get(Context paramContext, HttpHost paramHttpHost, String paramString, aq paramaq, an paraman)
  {
    get(paramContext, paramHttpHost, paramString, null, paramaq, paraman);
  }

  public void get(Context paramContext, HttpHost paramHttpHost, String paramString, Header[] paramArrayOfHeader, aq paramaq, an paraman)
  {
    HttpGet localHttpGet = new HttpGet(a(paramString, paramaq));
    if (paramArrayOfHeader != null)
      localHttpGet.setHeaders(paramArrayOfHeader);
    a(paramContext, getHttpClient(), getHttpContext(), paramHttpHost, localHttpGet, null, paraman);
  }

  public void post(Context paramContext, String paramString1, aq paramaq, String paramString2, an paraman)
  {
    post(paramContext, paramString1, null, paramaq, paramString2, paraman);
  }

  public void post(Context paramContext, String paramString1, Header[] paramArrayOfHeader, aq paramaq, String paramString2, an paraman)
  {
    HttpPost localHttpPost = new HttpPost(paramString1);
    if (paramaq != null)
      localHttpPost.setEntity(a(paramaq));
    if (paramArrayOfHeader != null)
      localHttpPost.setHeaders(paramArrayOfHeader);
    a(paramContext, getHttpClient(), getHttpContext(), null, localHttpPost, paramString2, paraman);
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.umeng.message.proguard.al
 * JD-Core Version:    0.6.2
 */