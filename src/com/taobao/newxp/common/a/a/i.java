package com.taobao.newxp.common.a.a;

import android.content.Context;
import android.os.Bundle;
import android.util.Base64;
import com.taobao.munion.base.anticheat.b;
import com.taobao.newxp.common.a.a;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class i
{
  public String a;
  public String b;
  public String c;
  private int d;
  private int e;
  private int f;
  private int g;
  private long h = 0L;
  private long i = 0L;
  private long j = 0L;
  private String k;
  private g l;

  private i(Bundle paramBundle)
  {
    d locald = a.a().b();
    if (locald != null)
    {
      this.c = locald.c;
      this.d = locald.f;
      this.e = locald.g;
      this.k = locald.d;
    }
  }

  private int a()
  {
    long l1 = 0L;
    String str = this.c;
    int m = 0;
    if (str != null)
    {
      int n = this.c.length();
      m = 0;
      if (n > 0)
      {
        g localg = this.l;
        m = 0;
        if (localg != null)
        {
          if (this.l.i > l1)
            l1 = this.l.i;
          if (this.l.a > 0);
          for (int i1 = this.l.a; ; i1 = 0)
          {
            long l2 = (l1 + this.h) % 9L;
            if (l2 >= 100L)
              l2 = 100L;
            for (int i2 = 0; i2 < l2; i2++)
            {
              int i3 = i2 * i1 % this.c.length();
              if (this.c.length() > i3)
                m += this.c.charAt(i3);
            }
          }
        }
      }
    }
    return m;
  }

  public static i a(Bundle paramBundle)
  {
    return new i(paramBundle);
  }

  private String b(Context paramContext)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append(-1);
    localStringBuilder.append(",");
    localStringBuilder.append(this.d);
    localStringBuilder.append(",");
    localStringBuilder.append(this.e);
    localStringBuilder.append(",");
    localStringBuilder.append(this.l.a);
    localStringBuilder.append(",");
    localStringBuilder.append(-1);
    localStringBuilder.append(",");
    localStringBuilder.append(this.l.i);
    localStringBuilder.append(",");
    localStringBuilder.append(this.f);
    localStringBuilder.append(",");
    localStringBuilder.append(this.g);
    localStringBuilder.append(",");
    localStringBuilder.append(b.g(paramContext));
    localStringBuilder.append(",");
    localStringBuilder.append(b.h(paramContext));
    localStringBuilder.append(",");
    return localStringBuilder.toString();
  }

  public String a(Context paramContext)
    throws UnsupportedEncodingException
  {
    j localj = (j)a.a().b(3);
    this.f = localj.a();
    this.g = localj.c();
    this.l = localj.d();
    this.h = localj.e();
    this.i = localj.f();
    this.j = (this.i - this.h);
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("v=");
    localStringBuilder.append("1.0");
    localStringBuilder.append("&");
    localStringBuilder.append("s=");
    localStringBuilder.append(a());
    localStringBuilder.append("&");
    localStringBuilder.append("n=");
    localStringBuilder.append(this.k);
    localStringBuilder.append("&");
    localStringBuilder.append("i=");
    localStringBuilder.append(b.c(paramContext));
    localStringBuilder.append("&");
    localStringBuilder.append("u=");
    localStringBuilder.append(this.c);
    localStringBuilder.append("&");
    localStringBuilder.append("et=");
    localStringBuilder.append(this.h);
    localStringBuilder.append("&");
    localStringBuilder.append("t=");
    localStringBuilder.append(this.j);
    localStringBuilder.append("&");
    localStringBuilder.append("m=");
    localStringBuilder.append(b(paramContext));
    return URLEncoder.encode(new String(Base64.encode(localStringBuilder.toString().getBytes(), 0), "UTF-8"), "UTF-8");
  }

  private static class a
  {
    static final String a = "v=";
    static final String b = "s=";
    static final String c = "n=";
    static final String d = "i=";
    static final String e = "u=";
    static final String f = "et=";
    static final String g = "t=";
    static final String h = "m=";
    static final String i = "&";
    static final String j = ",";
  }

  private static class b
  {
    static final String a = "1.0";
    static final int b = -1;
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.taobao.newxp.common.a.a.i
 * JD-Core Version:    0.6.2
 */