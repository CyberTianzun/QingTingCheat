package com.taobao.munion.base.caches;

import android.text.TextUtils;
import java.io.UnsupportedEncodingException;

public class i
{
  public static final char a = '~';
  private static final char b = '_';
  private static final String c = "0000000000000";
  private static final String d = "utf-8";
  private static final String e = "";
  private long f;
  private long g;
  private String h;
  private String i;
  private String j;
  private String k;
  private String l;
  private long m;
  private boolean n = true;

  public int a(i parami)
  {
    if (this == parami)
      return 0;
    if (this.f > parami.f)
      return 1;
    return -1;
  }

  public long a()
  {
    return this.f;
  }

  public void a(long paramLong)
  {
    this.f = paramLong;
  }

  public void a(String paramString)
  {
    this.h = paramString;
  }

  public void a(boolean paramBoolean)
  {
    this.n = paramBoolean;
  }

  public String b()
  {
    return this.h;
  }

  public void b(long paramLong)
  {
    this.m = paramLong;
  }

  public void b(String paramString)
  {
    this.l = paramString;
  }

  public String c()
  {
    return this.l;
  }

  public void c(long paramLong)
  {
    this.g = paramLong;
  }

  public void c(String paramString)
  {
    this.i = paramString;
  }

  public String d()
  {
    return this.i;
  }

  public void d(String paramString)
  {
    this.k = paramString;
  }

  public long e()
  {
    return this.m;
  }

  public void e(String paramString)
  {
    this.j = paramString;
  }

  public byte[] f()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    if (this.f > 0L)
      localStringBuilder.append(this.f);
    while (true)
    {
      if (this.n)
      {
        localStringBuilder.append('~');
        label40: if (this.g <= 0L)
          break label235;
        localStringBuilder.append(this.g);
        label58: if (!this.n)
          break label245;
        localStringBuilder.append('~');
        label72: if (this.h != null)
          break label255;
        localStringBuilder.append("");
        label86: if (!this.n)
          break label267;
        localStringBuilder.append('~');
        label100: if (this.j != null)
          break label277;
        localStringBuilder.append("");
        label114: if (!this.n)
          break label289;
        localStringBuilder.append('~');
        label128: if (this.i != null)
          break label299;
        localStringBuilder.append("");
        label142: if (!this.n)
          break label311;
        localStringBuilder.append('~');
        label156: if (this.k != null)
          break label321;
        localStringBuilder.append("");
        label170: if (!this.n)
          break label333;
        localStringBuilder.append('~');
        label184: if (!TextUtils.isEmpty(this.l))
          break label343;
        localStringBuilder.append("utf-8");
      }
      try
      {
        while (true)
        {
          byte[] arrayOfByte = localStringBuilder.toString().getBytes("UTF-8");
          return arrayOfByte;
          localStringBuilder.append("0000000000000");
          break;
          localStringBuilder.append('_');
          break label40;
          label235: localStringBuilder.append("0000000000000");
          break label58;
          label245: localStringBuilder.append('_');
          break label72;
          label255: localStringBuilder.append(this.h);
          break label86;
          label267: localStringBuilder.append('_');
          break label100;
          label277: localStringBuilder.append(this.j);
          break label114;
          label289: localStringBuilder.append('_');
          break label128;
          label299: localStringBuilder.append(this.i);
          break label142;
          label311: localStringBuilder.append('_');
          break label156;
          label321: localStringBuilder.append(this.k);
          break label170;
          label333: localStringBuilder.append('_');
          break label184;
          label343: localStringBuilder.append(this.l);
        }
      }
      catch (UnsupportedEncodingException localUnsupportedEncodingException)
      {
        localUnsupportedEncodingException.printStackTrace();
      }
    }
    return null;
  }

  public String g()
  {
    return this.k;
  }

  public long h()
  {
    return this.g;
  }

  public String i()
  {
    return this.j;
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.taobao.munion.base.caches.i
 * JD-Core Version:    0.6.2
 */