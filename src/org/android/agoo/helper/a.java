package org.android.agoo.helper;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.text.TextUtils;
import com.umeng.message.proguard.Q;
import com.umeng.message.proguard.aI;
import com.umeng.message.proguard.ag;
import com.umeng.message.proguard.aq;
import com.umeng.message.proguard.as;
import com.umeng.message.proguard.as.a;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.android.agoo.client.AgooSettings;
import org.android.agoo.client.AgooSettings.Mode;
import org.apache.http.HttpHost;

public class a
{
  private static final String a = "HostClient";
  private static final String b = "AGOO_HOST";
  private static final String c = "AGOO_HOST_SIZE";
  private static final String d = "AGOO_HOST_VALUE_";
  private volatile int e = 0;
  private volatile ThreadPoolExecutor f;
  private volatile as g = null;
  private volatile Context h;
  private volatile String i;

  public a(Context paramContext, String paramString)
  {
    this.h = paramContext;
    this.i = paramString;
    this.f = ((ThreadPoolExecutor)Executors.newFixedThreadPool(2));
    this.g = new as();
  }

  private int a(Context paramContext, String[] paramArrayOfString)
  {
    int j = 0;
    SharedPreferences.Editor localEditor = paramContext.getSharedPreferences("AGOO_HOST", 4).edit();
    localEditor.clear();
    int k = paramArrayOfString.length;
    for (int m = 0; m < k; m++)
      if ((!TextUtils.isEmpty(paramArrayOfString[m])) && (a(paramArrayOfString[m])))
      {
        localEditor.putString("AGOO_HOST_VALUE_" + j, paramArrayOfString[m]);
        j++;
      }
    localEditor.putInt("AGOO_HOST_SIZE", j);
    localEditor.commit();
    return j;
  }

  private static String a(Context paramContext, int paramInt)
  {
    return paramContext.getSharedPreferences("AGOO_HOST", 4).getString("AGOO_HOST_VALUE_" + paramInt, null);
  }

  private void a(as.a parama, b paramb, AgooSettings.Mode paramMode)
  {
    if (parama == null)
    {
      paramb.a(408, paramMode.getPushApollIp());
      return;
    }
    if (200 != parama.a)
    {
      paramb.a(404, "get [" + paramMode.getPushApollIp() + "] error");
      return;
    }
    if (TextUtils.isEmpty(parama.b))
    {
      paramb.a(504, "get [" + paramMode.getPushApollIp() + "] error");
      return;
    }
    if (TextUtils.indexOf(parama.b, "<html>") != -1)
    {
      paramb.a(307, "get [" + paramMode.getPushApollIp() + "] error");
      return;
    }
    String[] arrayOfString = parama.b.split("\\|");
    if (arrayOfString.length <= 0)
    {
      paramb.a(504, "get [" + paramMode.getPushApollIp() + "] error");
      return;
    }
    if (arrayOfString.length <= 0)
    {
      paramb.a(504, "get [" + paramMode.getPushApollIp() + "] error");
      return;
    }
    if (a(this.h, arrayOfString) <= 0)
    {
      paramb.a(504, "get [" + paramMode.getPushApollIp() + "] error");
      return;
    }
    paramb.a(a(this.h, 0));
  }

  private boolean a(String paramString)
  {
    String str = "(" + "(2[0-4]\\d)|(25[0-5])" + ")|(" + "1\\d{2}" + ")|(" + "[1-9]\\d" + ")|(" + "\\d" + ")";
    return Pattern.compile("(" + str + ").(" + str + ").(" + str + ").(" + str + "):\\d*$").matcher(paramString).matches();
  }

  private void b(b paramb)
  {
    this.f.submit(new a(this.h, this.i, paramb));
  }

  public String a(Context paramContext)
  {
    String str = PhoneHelper.getImsi(paramContext);
    if (!TextUtils.isEmpty(str))
    {
      if ((str.startsWith("46000")) || (str.startsWith("46002")))
        return "china_mobile";
      if (str.startsWith("46001"))
        return "china_unicom";
      if (str.startsWith("46003"))
        return "china_telecom";
    }
    return null;
  }

  public void a(b paramb)
  {
    if (paramb == null)
      throw new NullPointerException("IHostHandler is null");
    int j = b(this.h);
    if (j <= 0)
    {
      Q.c("HostClient", "local host size <=0");
      b(paramb);
      return;
    }
    if (this.e >= j)
    {
      Q.c("HostClient", "next host >= localhost size");
      b(paramb);
      return;
    }
    String str = a(this.h, this.e);
    if (TextUtils.isEmpty(str))
    {
      Q.c("HostClient", "next host == null");
      b(paramb);
      return;
    }
    Q.c("HostClient", "next host [" + str + "]");
    paramb.a(str);
    this.e = (1 + this.e);
  }

  public int b(Context paramContext)
  {
    return paramContext.getSharedPreferences("AGOO_HOST", 4).getInt("AGOO_HOST_SIZE", 0);
  }

  class a
    implements Runnable
  {
    String a;
    a.b b;
    Context c;

    public a(Context paramString, String paramb, a.b arg4)
    {
      this.c = paramString;
      this.a = paramb;
      Object localObject;
      this.b = localObject;
    }

    public void run()
    {
      AgooSettings.Mode localMode = AgooSettings.getMode(this.c);
      try
      {
        aq localaq = new aq();
        localaq.a("id", this.a);
        localaq.a("app_version_code", "" + aI.b(this.c));
        localaq.a("agoo_version_code", "" + AgooSettings.getAgooReleaseTime());
        ag localag = new ag(this.c);
        String str1 = localag.c();
        if (!TextUtils.isEmpty(str1))
          localaq.a("agoo_network", str1);
        String str2 = localag.b();
        if (!TextUtils.isEmpty(str2))
          localaq.a("agoo_apn", str2);
        String str3 = a.this.a(this.c);
        if (!TextUtils.isEmpty(str3))
          localaq.a("agoo_operators", str3);
        String str4 = localMode.getPushApollIp();
        String str5 = AgooSettings.getApollUrl(this.c);
        int i = localMode.getPushApollPort();
        if (a.a(a.this) == null)
          a.a(a.this, new as());
        as.a locala2;
        if (!AgooSettings.isAgooTestMode(this.c))
          locala2 = a.a(a.this).get(this.c, str5, localaq);
        as.a locala1;
        for (localObject = locala2; ; localObject = locala1)
        {
          a.a(a.this, (as.a)localObject, this.b, localMode);
          return;
          Q.c("HostClient", "test host ip [ " + str4 + " ]");
          HttpHost localHttpHost = new HttpHost(str4, i);
          locala1 = a.a(a.this).get(this.c, localHttpHost, str5, localaq);
        }
      }
      catch (Throwable localThrowable)
      {
        while (true)
        {
          Q.d("HostClient", "host Throwable", localThrowable);
          Object localObject = null;
        }
      }
    }
  }

  public static abstract interface b
  {
    public abstract void a(int paramInt, String paramString);

    public abstract void a(String paramString);
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     org.android.agoo.helper.a
 * JD-Core Version:    0.6.2
 */