package u.aly;

import android.content.Context;
import com.umeng.analytics.AnalyticsConfig;
import com.umeng.analytics.ReportPolicy.a;
import com.umeng.analytics.ReportPolicy.b;
import com.umeng.analytics.ReportPolicy.c;
import com.umeng.analytics.ReportPolicy.d;
import com.umeng.analytics.ReportPolicy.e;
import com.umeng.analytics.ReportPolicy.f;
import com.umeng.analytics.f;

public final class j
  implements com.umeng.analytics.onlineconfig.c, o
{
  private q a = null;
  private r b = null;
  private ReportPolicy.e c = null;
  private f d = null;
  private w e = null;
  private d f = null;
  private int g = 10;
  private Context h;

  public j(Context paramContext)
  {
    this.h = paramContext;
    this.a = new q(paramContext);
    this.f = h.a(paramContext);
    this.e = new w(paramContext);
    this.b = new r(paramContext);
    this.b.a(this.e);
    this.d = f.a(paramContext);
    int[] arrayOfInt = AnalyticsConfig.getReportPolicy(this.h);
    a(arrayOfInt[0], arrayOfInt[1]);
  }

  private bf a(byte[] paramArrayOfByte)
  {
    if (paramArrayOfByte == null)
      return null;
    try
    {
      bf localbf = new bf();
      new cc().a(localbf, paramArrayOfByte);
      return localbf;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return null;
  }

  private void a(int paramInt1, int paramInt2)
  {
    switch (paramInt1)
    {
    case 2:
    case 3:
    default:
      this.c = new ReportPolicy.a();
    case 1:
    case 6:
    case 4:
    case 0:
    case 5:
    case 7:
    }
    while (true)
    {
      bj.c("MobclickAgent", "report policy:" + paramInt1 + " interval:" + paramInt2);
      return;
      this.c = new ReportPolicy.a();
      continue;
      this.c = new ReportPolicy.b(this.e, paramInt2);
      continue;
      this.c = new ReportPolicy.d(this.e);
      continue;
      this.c = new ReportPolicy.e();
      continue;
      this.c = new ReportPolicy.f(this.h);
      continue;
      this.c = new ReportPolicy.c(this.a, paramInt2);
    }
  }

  private boolean a(boolean paramBoolean)
  {
    boolean bool = true;
    if (!bi.l(this.h))
    {
      if (bj.a)
        bj.c("MobclickAgent", "network is unavailable");
      bool = false;
    }
    while ((this.e.b()) || ((bj.a) && (bi.w(this.h))))
      return bool;
    return this.c.a(paramBoolean);
  }

  private byte[] a(bf parambf)
  {
    try
    {
      byte[] arrayOfByte = new ci().a(parambf);
      return arrayOfByte;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return null;
  }

  private boolean d()
  {
    return this.a.b() > this.g;
  }

  private void e()
  {
    try
    {
      if (this.e.b())
        this.a.a(new ah(this.e.j()));
      f();
      return;
    }
    catch (Throwable localThrowable)
    {
      while (((localThrowable instanceof OutOfMemoryError)) && (localThrowable == null));
      localThrowable.printStackTrace();
    }
  }

  private void f()
  {
    f localf = f.a(this.h);
    boolean bool = localf.f();
    byte[] arrayOfByte2;
    if (bool)
      arrayOfByte2 = localf.d();
    switch (this.b.a(arrayOfByte2))
    {
    default:
    case 2:
    case 3:
      do
      {
        do
        {
          return;
          this.f.a();
          byte[] arrayOfByte1 = c();
          if (arrayOfByte1 == null)
          {
            bj.e("MobclickAgent", "message is null");
            return;
          }
          arrayOfByte2 = c.a(this.h, AnalyticsConfig.getAppkey(this.h), arrayOfByte1).c();
          localf.c();
          break;
          if (this.e.i())
            this.e.h();
          this.f.d();
          this.e.g();
        }
        while (!bool);
        localf.e();
        return;
        this.e.g();
      }
      while (!bool);
      localf.e();
      return;
    case 1:
    }
    if (!bool)
      localf.b(arrayOfByte2);
    bj.b("MobclickAgent", "connection error");
  }

  public void a()
  {
    if (bi.l(this.h))
      e();
    while (!bj.a)
      return;
    bj.c("MobclickAgent", "network is unavailable");
  }

  public void a(int paramInt, long paramLong)
  {
    AnalyticsConfig.setReportPolicy(paramInt, (int)paramLong);
    a(paramInt, (int)paramLong);
  }

  public void a(p paramp)
  {
    if (paramp != null)
      this.a.a(paramp);
    if (a(paramp instanceof bd))
      e();
    while (!d())
      return;
    b();
  }

  public void b()
  {
    if (this.a.b() > 0);
    try
    {
      byte[] arrayOfByte = c();
      if (arrayOfByte != null)
        this.d.a(arrayOfByte);
      return;
    }
    catch (Throwable localThrowable)
    {
      do
        if ((localThrowable instanceof OutOfMemoryError))
          this.d.c();
      while (localThrowable == null);
      localThrowable.printStackTrace();
    }
  }

  public void b(p paramp)
  {
    this.a.a(paramp);
  }

  // ERROR //
  protected byte[] c()
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 45	u/aly/j:h	Landroid/content/Context;
    //   4: invokestatic 211	com/umeng/analytics/AnalyticsConfig:getAppkey	(Landroid/content/Context;)Ljava/lang/String;
    //   7: invokestatic 260	android/text/TextUtils:isEmpty	(Ljava/lang/CharSequence;)Z
    //   10: ifeq +13 -> 23
    //   13: ldc 97
    //   15: ldc_w 262
    //   18: invokestatic 237	u/aly/bj:b	(Ljava/lang/String;Ljava/lang/String;)V
    //   21: aconst_null
    //   22: areturn
    //   23: aload_0
    //   24: getfield 45	u/aly/j:h	Landroid/content/Context;
    //   27: invokestatic 68	com/umeng/analytics/f:a	(Landroid/content/Context;)Lcom/umeng/analytics/f;
    //   30: invokevirtual 264	com/umeng/analytics/f:b	()[B
    //   33: astore_2
    //   34: aload_2
    //   35: ifnonnull +21 -> 56
    //   38: aconst_null
    //   39: astore_3
    //   40: aload_3
    //   41: ifnonnull +24 -> 65
    //   44: aload_0
    //   45: getfield 31	u/aly/j:a	Lu/aly/q;
    //   48: invokevirtual 170	u/aly/q:b	()I
    //   51: ifne +14 -> 65
    //   54: aconst_null
    //   55: areturn
    //   56: aload_0
    //   57: aload_2
    //   58: invokespecial 266	u/aly/j:a	([B)Lu/aly/bf;
    //   61: astore_3
    //   62: goto -22 -> 40
    //   65: aload_3
    //   66: ifnonnull +176 -> 242
    //   69: new 82	u/aly/bf
    //   72: dup
    //   73: invokespecial 83	u/aly/bf:<init>	()V
    //   76: astore 4
    //   78: aload_0
    //   79: getfield 31	u/aly/j:a	Lu/aly/q;
    //   82: aload 4
    //   84: invokevirtual 269	u/aly/q:a	(Lu/aly/bf;)V
    //   87: getstatic 150	u/aly/bj:a	Z
    //   90: ifeq +71 -> 161
    //   93: aload 4
    //   95: invokevirtual 272	u/aly/bf:B	()Z
    //   98: ifeq +63 -> 161
    //   101: iconst_0
    //   102: istore 9
    //   104: aload 4
    //   106: invokevirtual 276	u/aly/bf:z	()Ljava/util/List;
    //   109: invokeinterface 282 1 0
    //   114: astore 10
    //   116: aload 10
    //   118: invokeinterface 287 1 0
    //   123: ifeq +25 -> 148
    //   126: aload 10
    //   128: invokeinterface 291 1 0
    //   133: checkcast 247	u/aly/bd
    //   136: invokevirtual 294	u/aly/bd:p	()I
    //   139: ifle +96 -> 235
    //   142: iconst_1
    //   143: istore 11
    //   145: goto +106 -> 251
    //   148: iload 9
    //   150: ifne +11 -> 161
    //   153: ldc 97
    //   155: ldc_w 296
    //   158: invokestatic 207	u/aly/bj:e	(Ljava/lang/String;Ljava/lang/String;)V
    //   161: aload_0
    //   162: aload 4
    //   164: invokespecial 298	u/aly/j:a	(Lu/aly/bf;)[B
    //   167: astore 7
    //   169: aload 7
    //   171: astore 6
    //   173: getstatic 150	u/aly/bj:a	Z
    //   176: ifeq +72 -> 248
    //   179: ldc 97
    //   181: aload 4
    //   183: invokevirtual 299	u/aly/bf:toString	()Ljava/lang/String;
    //   186: invokestatic 120	u/aly/bj:c	(Ljava/lang/String;Ljava/lang/String;)V
    //   189: aload 6
    //   191: areturn
    //   192: astore 8
    //   194: ldc 97
    //   196: ldc_w 301
    //   199: invokestatic 237	u/aly/bj:b	(Ljava/lang/String;Ljava/lang/String;)V
    //   202: aload 6
    //   204: areturn
    //   205: astore_1
    //   206: ldc 97
    //   208: ldc_w 303
    //   211: aload_1
    //   212: invokestatic 306	u/aly/bj:b	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Exception;)V
    //   215: aload_0
    //   216: getfield 45	u/aly/j:h	Landroid/content/Context;
    //   219: invokestatic 68	com/umeng/analytics/f:a	(Landroid/content/Context;)Lcom/umeng/analytics/f;
    //   222: invokevirtual 219	com/umeng/analytics/f:c	()V
    //   225: aconst_null
    //   226: areturn
    //   227: astore 5
    //   229: aconst_null
    //   230: astore 6
    //   232: goto -38 -> 194
    //   235: iload 9
    //   237: istore 11
    //   239: goto +12 -> 251
    //   242: aload_3
    //   243: astore 4
    //   245: goto -167 -> 78
    //   248: aload 6
    //   250: areturn
    //   251: iload 11
    //   253: istore 9
    //   255: goto -139 -> 116
    //
    // Exception table:
    //   from	to	target	type
    //   173	189	192	java/lang/Exception
    //   0	21	205	java/lang/Exception
    //   23	34	205	java/lang/Exception
    //   44	54	205	java/lang/Exception
    //   56	62	205	java/lang/Exception
    //   69	78	205	java/lang/Exception
    //   78	101	205	java/lang/Exception
    //   104	116	205	java/lang/Exception
    //   116	142	205	java/lang/Exception
    //   153	161	205	java/lang/Exception
    //   194	202	205	java/lang/Exception
    //   161	169	227	java/lang/Exception
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     u.aly.j
 * JD-Core Version:    0.6.2
 */