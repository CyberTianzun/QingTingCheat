package com.taobao.newxp.common.c;

import android.content.Context;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;

public class e
  implements d
{
  private static final String m = e.class.getName();
  private static String n = "%s,%s,%d,%d,%d,%d,%d,%d,%d,%d,%d,%d,%s,%d,%d";
  private static int o = 0;
  private static long p = 0L;
  private static long q = -9999L;
  String a = "1";
  String b = "-1";
  int c;
  int d;
  int e;
  int f;
  int g;
  int h;
  long i;
  long j;
  long k;
  long l;
  private String r;
  private String s;

  private String a(String paramString, long paramLong1, long paramLong2, int paramInt)
  {
    int i1 = 0;
    if (TextUtils.isEmpty(paramString))
      return "" + 0;
    if (paramLong2 > 0L)
      if (paramInt <= 0)
        break label91;
    int i2;
    while (true)
    {
      i2 = 0;
      while (i1 < (paramLong2 + paramLong1) % 9L)
      {
        i2 += paramString.charAt(i1 * paramInt % paramString.length());
        i1++;
      }
      paramLong2 = 0L;
      break;
      label91: paramInt = 0;
    }
    return "" + i2;
  }

  public String a()
  {
    String str1 = n;
    Object[] arrayOfObject = new Object[15];
    arrayOfObject[0] = this.a;
    arrayOfObject[1] = a(this.b, this.i, this.j, o);
    arrayOfObject[2] = Integer.valueOf(this.c);
    arrayOfObject[3] = Integer.valueOf(this.d);
    arrayOfObject[4] = Integer.valueOf(this.e);
    arrayOfObject[5] = Integer.valueOf(this.f);
    arrayOfObject[6] = Long.valueOf(this.i);
    arrayOfObject[7] = Integer.valueOf(this.g);
    arrayOfObject[8] = Integer.valueOf(this.h);
    arrayOfObject[9] = Long.valueOf(this.j);
    arrayOfObject[10] = Long.valueOf(this.k);
    arrayOfObject[11] = Long.valueOf(this.l);
    arrayOfObject[12] = this.b;
    arrayOfObject[13] = Long.valueOf(q);
    arrayOfObject[14] = Integer.valueOf(o);
    String str2 = String.format(str1, arrayOfObject);
    Log.d(m, "Generate Paramater A " + str2);
    return str2;
  }

  public void a(Context paramContext)
  {
    DisplayMetrics localDisplayMetrics = new DisplayMetrics();
    ((WindowManager)paramContext.getSystemService("window")).getDefaultDisplay().getMetrics(localDisplayMetrics);
    this.c = localDisplayMetrics.widthPixels;
    this.d = localDisplayMetrics.heightPixels;
  }

  public void a(a parama)
  {
    long l1;
    if ((parama instanceof f))
    {
      l1 = System.currentTimeMillis();
      if (p != 0L)
        break label47;
    }
    label47: for (long l2 = -9999L; ; l2 = l1 - p)
    {
      q = l2;
      p = l1;
      o = 1 + o;
      parama.a(this);
      return;
    }
  }

  public void a(String paramString)
  {
    this.s = paramString;
  }

  public String b()
  {
    return this.s;
  }

  public void b(String paramString)
  {
    this.r = paramString;
  }

  public String c()
  {
    return this.r;
  }

  public void c(String paramString)
  {
    if (!TextUtils.isEmpty(paramString))
    {
      this.b = paramString;
      return;
    }
    this.b = "-1";
  }

  public void d()
  {
    this.k = (System.currentTimeMillis() / 1000L);
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.taobao.newxp.common.c.e
 * JD-Core Version:    0.6.2
 */