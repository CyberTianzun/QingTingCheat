package com.umeng.message.proguard;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import org.apache.http.HttpHost;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;

public class ah extends DefaultHttpClient
{
  private static final String a = ah.class.getSimpleName();
  private static final int b = 30000;
  private static final int c = 8192;
  private String d;
  private int e;
  private boolean f;
  private RuntimeException g = new IllegalStateException("ProxyHttpClient created and never closed");

  public ah(Context paramContext, ag paramag)
  {
    this(paramContext, null, paramag);
  }

  public ah(Context paramContext, String paramString, ag paramag)
  {
    if (paramag == null)
      paramag = new ag(paramContext);
    this.f = paramag.a();
    this.d = paramag.d();
    this.e = paramag.e();
    if (this.f)
    {
      HttpHost localHttpHost = new HttpHost(this.d, this.e);
      getParams().setParameter("http.route.default-proxy", localHttpHost);
    }
    HttpConnectionParams.setConnectionTimeout(getParams(), 30000);
    HttpConnectionParams.setSoTimeout(getParams(), 30000);
    HttpConnectionParams.setSocketBufferSize(getParams(), 8192);
    if (!TextUtils.isEmpty(paramString))
      HttpProtocolParams.setUserAgent(getParams(), paramString);
  }

  public void a()
  {
    if (this.g != null)
    {
      getConnectionManager().shutdown();
      this.g = null;
    }
  }

  public boolean b()
  {
    return this.f;
  }

  protected HttpParams createHttpParams()
  {
    HttpParams localHttpParams = super.createHttpParams();
    HttpProtocolParams.setUseExpectContinue(localHttpParams, false);
    return localHttpParams;
  }

  protected void finalize()
    throws Throwable
  {
    super.finalize();
    if (this.g != null)
      Log.e(a, "Leak found", this.g);
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.umeng.message.proguard.ah
 * JD-Core Version:    0.6.2
 */