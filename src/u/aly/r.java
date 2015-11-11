package u.aly;

import android.content.Context;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Build.VERSION;
import com.umeng.analytics.AnalyticsConfig;
import com.umeng.analytics.a;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.InputStreamEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

public class r
{
  public static final int a = 1;
  public static final int b = 2;
  public static final int c = 3;
  private final int d = 1;
  private String e;
  private String f = "10.0.0.172";
  private int g = 80;
  private Context h;
  private w i;
  private f j;

  public r(Context paramContext)
  {
    this.h = paramContext;
    this.j = h.b(paramContext);
    this.e = a(paramContext);
  }

  private String a(Context paramContext)
  {
    StringBuffer localStringBuffer1 = new StringBuffer();
    localStringBuffer1.append("Android");
    localStringBuffer1.append("/");
    localStringBuffer1.append("5.2.4");
    localStringBuffer1.append(" ");
    try
    {
      StringBuffer localStringBuffer2 = new StringBuffer();
      localStringBuffer2.append(bi.v(paramContext));
      localStringBuffer2.append("/");
      localStringBuffer2.append(bi.d(paramContext));
      localStringBuffer2.append(" ");
      localStringBuffer2.append(Build.MODEL);
      localStringBuffer2.append("/");
      localStringBuffer2.append(Build.VERSION.RELEASE);
      localStringBuffer2.append(" ");
      localStringBuffer2.append(bv.a(AnalyticsConfig.getAppkey(paramContext)));
      localStringBuffer1.append(URLEncoder.encode(localStringBuffer2.toString(), "UTF-8"));
      return localStringBuffer1.toString();
    }
    catch (Exception localException)
    {
      while (true)
        localException.printStackTrace();
    }
  }

  private boolean a()
  {
    if (this.h.getPackageManager().checkPermission("android.permission.ACCESS_NETWORK_STATE", this.h.getPackageName()) != 0)
      return false;
    try
    {
      NetworkInfo localNetworkInfo = ((ConnectivityManager)this.h.getSystemService("connectivity")).getActiveNetworkInfo();
      if ((localNetworkInfo != null) && (localNetworkInfo.getType() != 1))
      {
        String str = localNetworkInfo.getExtraInfo();
        if (str != null)
          if ((!str.equals("cmwap")) && (!str.equals("3gwap")))
          {
            boolean bool = str.equals("uniwap");
            if (!bool);
          }
          else
          {
            return true;
          }
      }
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return false;
  }

  private byte[] a(byte[] paramArrayOfByte, String paramString)
  {
    HttpPost localHttpPost = new HttpPost(paramString);
    BasicHttpParams localBasicHttpParams = new BasicHttpParams();
    HttpConnectionParams.setConnectionTimeout(localBasicHttpParams, 10000);
    HttpConnectionParams.setSoTimeout(localBasicHttpParams, 30000);
    DefaultHttpClient localDefaultHttpClient = new DefaultHttpClient(localBasicHttpParams);
    localHttpPost.addHeader("X-Umeng-Sdk", this.e);
    localHttpPost.addHeader("Msg-Type", "envelope");
    try
    {
      if (a())
      {
        HttpHost localHttpHost = new HttpHost(this.f, this.g);
        localDefaultHttpClient.getParams().setParameter("http.route.default-proxy", localHttpHost);
      }
      localHttpPost.setEntity(new InputStreamEntity(new ByteArrayInputStream(paramArrayOfByte), paramArrayOfByte.length));
      if (this.i != null)
        this.i.e();
      HttpResponse localHttpResponse = localDefaultHttpClient.execute(localHttpPost);
      if (this.i != null)
        this.i.f();
      int k = localHttpResponse.getStatusLine().getStatusCode();
      bj.a("MobclickAgent", "status code : " + k);
      if (localHttpResponse.getStatusLine().getStatusCode() == 200)
      {
        bj.a("MobclickAgent", "Sent message to " + paramString);
        HttpEntity localHttpEntity = localHttpResponse.getEntity();
        if (localHttpEntity != null)
        {
          InputStream localInputStream = localHttpEntity.getContent();
          try
          {
            byte[] arrayOfByte = bv.b(localInputStream);
            return arrayOfByte;
          }
          finally
          {
            bv.c(localInputStream);
          }
        }
      }
    }
    catch (ClientProtocolException localClientProtocolException)
    {
      bj.b("MobclickAgent", "ClientProtocolException,Failed to send message.", localClientProtocolException);
      return null;
      return null;
      return null;
    }
    catch (IOException localIOException)
    {
      bj.b("MobclickAgent", "IOException,Failed to send message.", localIOException);
    }
    return null;
  }

  private int b(byte[] paramArrayOfByte)
  {
    bb localbb = new bb();
    cc localcc = new cc(new cr.a());
    try
    {
      localcc.a(localbb, paramArrayOfByte);
      if (localbb.a == 1)
      {
        this.j.b(localbb.j());
        this.j.c();
      }
      bj.a("MobclickAgent", "send log:" + localbb.f());
      if (localbb.a == 1)
        return 2;
    }
    catch (Exception localException)
    {
      while (true)
        localException.printStackTrace();
    }
    return 3;
  }

  public int a(byte[] paramArrayOfByte)
  {
    byte[] arrayOfByte = null;
    for (int k = 0; ; k++)
    {
      if (k < a.f.length)
      {
        arrayOfByte = a(paramArrayOfByte, a.f[k]);
        if (arrayOfByte == null)
          break label47;
        if (this.i != null)
          this.i.c();
      }
      if (arrayOfByte != null)
        break;
      return 1;
      label47: if (this.i != null)
        this.i.d();
    }
    return b(arrayOfByte);
  }

  public void a(w paramw)
  {
    this.i = paramw;
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     u.aly.r
 * JD-Core Version:    0.6.2
 */