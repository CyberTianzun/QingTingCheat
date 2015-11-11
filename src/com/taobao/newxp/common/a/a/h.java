package com.taobao.newxp.common.a.a;

import android.content.Context;
import android.os.Bundle;
import com.taobao.newxp.common.AlimmContext;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class h
{
  private static long e;
  private static int f = 1;
  public String a;
  public String b;
  public String c;
  public String d;
  private int g;
  private int h;
  private int i;
  private float j;
  private int k;
  private int l;
  private g m;

  private h(Bundle paramBundle)
  {
    d locald = com.taobao.newxp.common.a.a.a().b();
    if (locald != null)
    {
      this.c = locald.a;
      this.d = locald.b;
      this.g = locald.e;
      this.h = locald.f;
      this.i = locald.g;
      this.j = locald.h;
    }
  }

  private int a(long paramLong)
  {
    long l1 = 0L;
    String str = this.d;
    int n = 0;
    if (str != null)
    {
      int i1 = this.d.length();
      n = 0;
      if (i1 > 0)
      {
        g localg = this.m;
        n = 0;
        if (localg != null)
        {
          if (this.m.i > l1)
            l1 = this.m.i;
          if (this.m.a > 0);
          int i3;
          for (int i2 = this.m.a; ; i2 = 0)
          {
            long l2 = (l1 + paramLong) % 9L;
            i3 = 0;
            while (n < l2)
            {
              int i4 = n * i2 % this.d.length();
              if (this.d.length() > i4)
                i3 += this.d.charAt(i4);
              n++;
            }
          }
          n = i3;
        }
      }
    }
    return n;
  }

  public static h a(Bundle paramBundle)
  {
    return new h(paramBundle);
  }

  private String a()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append(this.m.a);
    localStringBuilder.append(",");
    localStringBuilder.append(this.k);
    localStringBuilder.append(",");
    localStringBuilder.append(this.k + this.m.b);
    localStringBuilder.append(",");
    localStringBuilder.append(this.l);
    localStringBuilder.append(",");
    localStringBuilder.append(this.m.i);
    return localStringBuilder.toString();
  }

  private String a(int paramInt)
  {
    switch (paramInt)
    {
    default:
      return "";
    case 1:
      return "e";
    case 2:
      return "c";
    case 3:
    }
    return "t";
  }

  private String a(Context paramContext)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append(this.g);
    localStringBuilder.append(",");
    localStringBuilder.append(this.j);
    localStringBuilder.append(",");
    localStringBuilder.append(this.h);
    localStringBuilder.append(",");
    localStringBuilder.append(this.i);
    localStringBuilder.append(",");
    if (AlimmContext.getAliContext().getAppUtils().l());
    for (String str = "0"; ; str = "1")
    {
      localStringBuilder.append(str);
      return localStringBuilder.toString();
    }
  }

  public String a(Context paramContext, int paramInt)
    throws UnsupportedEncodingException
  {
    long l1 = System.currentTimeMillis() / 1000L;
    k localk = (k)com.taobao.newxp.common.a.a.a().b(1);
    this.m = localk.a();
    this.k = localk.d();
    this.l = localk.c();
    if (paramInt == 1)
      e = System.currentTimeMillis() / 1000L;
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("v=");
    localStringBuilder.append("1.1");
    localStringBuilder.append("&");
    localStringBuilder.append("pt=");
    localStringBuilder.append(e);
    localStringBuilder.append("&");
    localStringBuilder.append("t=");
    localStringBuilder.append(l1);
    localStringBuilder.append("&");
    localStringBuilder.append("i=");
    localStringBuilder.append(this.d);
    localStringBuilder.append("|");
    localStringBuilder.append(this.c);
    localStringBuilder.append("&");
    localStringBuilder.append("h=");
    localStringBuilder.append(f);
    localStringBuilder.append("&");
    localStringBuilder.append("a=");
    localStringBuilder.append(a(paramInt));
    localStringBuilder.append("&");
    if (paramInt != 1)
    {
      localStringBuilder.append("s=");
      localStringBuilder.append(a(l1));
      localStringBuilder.append("&");
      localStringBuilder.append("d=");
      localStringBuilder.append(a(paramContext));
      localStringBuilder.append("&");
      localStringBuilder.append("m=");
      localStringBuilder.append(a());
      localStringBuilder.append("&");
    }
    return URLEncoder.encode(localStringBuilder.toString().substring(0, -1 + localStringBuilder.length()), "UTF-8");
  }

  private static class a
  {
    static final String a = "v=";
    static final String b = "pt=";
    static final String c = "t=";
    static final String d = "s=";
    static final String e = "i=";
    static final String f = "h=";
    static final String g = "a=";
    static final String h = "d=";
    static final String i = "m=";
    static final String j = "&";
    static final String k = ",";
    static final String l = "|";
  }

  private static class b
  {
    static final String a = "1.1";
    static final int b = 0;
    static final int c = 1;
    static final int d = 2;
    static final String e = "0";
    static final String f = "1";
    static final String g = "e";
    static final String h = "c";
    static final String i = "t";
    static final int j = -1;
    static final int k = -1;
    static final String l = "";
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.taobao.newxp.common.a.a.h
 * JD-Core Version:    0.6.2
 */