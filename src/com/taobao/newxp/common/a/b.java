package com.taobao.newxp.common.a;

import android.content.Context;
import android.location.Location;
import android.os.Build.VERSION;
import android.text.TextUtils;
import com.taobao.newxp.common.AlimmContext;
import com.taobao.newxp.common.ExchangeConstants;
import com.taobao.newxp.common.a.a.e;
import com.taobao.newxp.common.a.a.f;
import com.taobao.newxp.common.a.a.h;
import com.taobao.newxp.common.a.a.o;
import com.taobao.newxp.common.a.a.p;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;

public class b
{
  private static final long a = 10000L;
  private static final String b = "http://gm.mmstat.com/%s?gokey=";
  private static final String c = "af.2.1";
  private static final String d = "wapebs.3.1";
  private static final String e = "wapebs.3.2";
  private static final String f = "wapebs.4.3";
  private static final String g = "http://gm.mmstat.com/%s?";
  private static final String h = b.class.getName();
  private static b m = new b();
  private int i = 0;
  private Timer j;
  private Context k;
  private ExecutorService l = Executors.newSingleThreadExecutor();

  public static b a()
  {
    return m;
  }

  private void a(String paramString)
  {
    String str = null;
    try
    {
      str = String.format("http://gm.mmstat.com/%s?gokey=", new Object[] { "wapebs.3.2" }) + e.a(this.k, paramString).a();
      android.util.Log.i("statistics", "MMSTATA data: " + str);
      if ((str != null) && (str.trim().length() > 0))
      {
        a locala = new a(str);
        this.l.execute(locala);
      }
      return;
    }
    catch (Exception localException)
    {
      while (true)
        localException.printStackTrace();
    }
  }

  private void b(String paramString)
  {
    String str = null;
    try
    {
      str = String.format("http://gm.mmstat.com/%s?gokey=", new Object[] { "wapebs.3.1" }) + p.a(paramString).a();
      android.util.Log.i("statistics", "MMSTATA data: " + str);
      if ((str != null) && (str.trim().length() > 0))
      {
        a locala = new a(str);
        this.l.execute(locala);
      }
      return;
    }
    catch (Exception localException)
    {
      while (true)
        localException.printStackTrace();
    }
  }

  private void c()
  {
    g();
    e();
  }

  private void d()
  {
    f();
    g();
  }

  private void e()
  {
    TimerTask local1 = new TimerTask()
    {
      public void run()
      {
        b.a(b.this, 3);
        b.a(b.this);
      }
    };
    this.j = new Timer();
    this.j.schedule(local1, 10000L);
  }

  private void f()
  {
    if (this.j != null)
    {
      this.j.cancel();
      this.j = null;
    }
  }

  private void g()
  {
    String str = null;
    try
    {
      str = String.format("http://gm.mmstat.com/%s?gokey=", new Object[] { "af.2.1" }) + h.a(null).a(this.k, this.i);
      android.util.Log.i("statistics", "Pingback data: " + str);
      if ((str != null) && (str.trim().length() > 0))
      {
        a locala = new a(str);
        this.l.execute(locala);
      }
      return;
    }
    catch (Exception localException)
    {
      while (true)
        localException.printStackTrace();
    }
  }

  private void h()
  {
    try
    {
      localf = (f)a.a().b(4);
      locala1 = AlimmContext.getAliContext().getAppUtils();
      if (localf != null)
      {
        if (locala1 == null)
          return;
        arrayOfString = locala1.E();
        Location localLocation = locala1.F();
        if (localLocation != null)
        {
          str1 = localLocation.getLatitude() + "," + localLocation.getLongitude();
          if (!TextUtils.isEmpty(com.taobao.munion.base.caches.b.i))
          {
            str2 = com.taobao.munion.base.caches.b.i;
            str3 = String.format("http://gm.mmstat.com/%s?", new Object[] { "wapebs.4.3" });
            Object[] arrayOfObject = new Object[5];
            arrayOfObject[0] = Long.valueOf(localf.a());
            arrayOfObject[1] = Long.valueOf(localf.c());
            arrayOfObject[2] = Long.valueOf(localf.d());
            arrayOfObject[3] = Long.valueOf(localf.e());
            arrayOfObject[4] = Long.valueOf(localf.f());
            com.taobao.munion.base.Log.i("handleClickTime @Time:%s,landingUrlLoadStartTime @Time:%s,checkoutForFirstImageLoad @Time:%s,landingUrlLoadFisishedTime @Time:%s,closeWebview @Time:%s", arrayOfObject);
            localStringBuffer2 = new StringBuffer();
          }
        }
      }
    }
    catch (Exception localException1)
    {
      while (true)
      {
        try
        {
          f localf;
          com.taobao.munion.base.a locala1;
          String[] arrayOfString;
          String str3;
          localStringBuffer2.append(str3).append("ta=" + URLEncoder.encode(new StringBuilder().append(localf.a()).append("").toString(), "utf-8")).append("&tb=" + URLEncoder.encode(new StringBuilder().append(localf.c()).append("").toString(), "utf-8")).append("&tc=" + URLEncoder.encode(new StringBuilder().append(localf.d()).append("").toString(), "utf-8")).append("&td=" + URLEncoder.encode(new StringBuilder().append(localf.e()).append("").toString(), "utf-8")).append("&te=" + URLEncoder.encode(new StringBuilder().append(localf.f()).append("").toString(), "utf-8")).append("&refpid=" + URLEncoder.encode(str2, "utf-8")).append("&ac=" + URLEncoder.encode(arrayOfString[0], "utf-8")).append("&acsub=" + URLEncoder.encode(arrayOfString[1], "utf-8")).append("&carrier=" + URLEncoder.encode(locala1.G(), "utf-8")).append("&gps=" + URLEncoder.encode(str1, "utf-8")).append("&os=" + URLEncoder.encode("android", "utf-8")).append("&osv=" + URLEncoder.encode(Build.VERSION.RELEASE, "utf-8")).append("&sdkv=" + URLEncoder.encode(ExchangeConstants.sdk_version, "utf-8")).append("&pgv=").append("&aurl=" + URLEncoder.encode(com.taobao.munion.base.caches.b.d, "utf-8")).append("&pageid=" + URLEncoder.encode(com.taobao.munion.base.caches.b.h, "utf-8"));
          localStringBuffer1 = localStringBuffer2;
          if ((localStringBuffer1.toString() == null) || (localStringBuffer1.toString().trim().length() <= 0))
            break;
          android.util.Log.i("ping back url = ", localStringBuffer1.toString());
          a locala = new a(localStringBuffer1.toString(), 4, true);
          this.l.execute(locala);
          return;
          localException1 = localException1;
          localObject = localException1;
          localStringBuffer1 = null;
          ((Exception)localObject).printStackTrace();
          continue;
        }
        catch (Exception localException2)
        {
          StringBuffer localStringBuffer2;
          StringBuffer localStringBuffer1 = localStringBuffer2;
          Object localObject = localException2;
          continue;
        }
        String str2 = "";
        continue;
        String str1 = "";
      }
    }
  }

  public void a(Context paramContext, o paramo)
  {
    if ((paramContext != null) && (paramo != null));
    while (true)
    {
      try
      {
        this.k = paramContext;
        int n = paramo.b;
        switch (n)
        {
        case 3:
        case 4:
        case 7:
        default:
          return;
        case 1:
          this.i = 1;
          c();
          continue;
        case 2:
        case 5:
        case 6:
        case 8:
        }
      }
      finally
      {
      }
      if (this.i != 3)
      {
        this.i = 2;
        d();
        continue;
        if ((paramo.c != null) && ((paramo.c instanceof String)))
        {
          b((String)paramo.c);
          continue;
          if ((paramo.c != null) && ((paramo.c instanceof String)))
          {
            a((String)paramo.c);
            continue;
            h();
          }
        }
      }
    }
  }

  private class a
    implements Runnable
  {
    private String b;
    private int c;
    private boolean d;

    public a(String arg2)
    {
      Object localObject;
      this.b = localObject;
    }

    public a(String paramInt, int paramBoolean, boolean arg4)
    {
      this.b = paramInt;
      this.c = paramBoolean;
      boolean bool;
      this.d = bool;
    }

    public void run()
    {
      try
      {
        DefaultHttpClient localDefaultHttpClient = new DefaultHttpClient();
        BasicHttpContext localBasicHttpContext = new BasicHttpContext();
        HttpGet localHttpGet = new HttpGet(this.b);
        localHttpGet.setHeader("Referer", b.b(b.this).getPackageName());
        localDefaultHttpClient.execute(localHttpGet, localBasicHttpContext);
        if (this.d)
          a.a().a(this.c);
        return;
      }
      catch (ClientProtocolException localClientProtocolException)
      {
        android.util.Log.e(b.b(), "Failed on sending user datas, error code is: " + localClientProtocolException.toString());
        return;
      }
      catch (IOException localIOException)
      {
        android.util.Log.e(b.b(), "Failed on sending user datas, error code is: " + localIOException.toString());
        return;
      }
      catch (Exception localException)
      {
        android.util.Log.e(b.b(), "Failed on sending user datas, error code is: " + localException.toString());
      }
    }
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.taobao.newxp.common.a.b
 * JD-Core Version:    0.6.2
 */