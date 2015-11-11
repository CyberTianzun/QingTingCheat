package com.tencent.a.b;

import android.content.Context;
import android.telephony.NeighboringCellInfo;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import java.lang.reflect.Method;
import java.util.LinkedList;
import java.util.List;

public final class d
{
  private Context a = null;
  private TelephonyManager b = null;
  private a c = null;
  private c d = null;
  private b e = null;
  private boolean f = false;
  private List<NeighboringCellInfo> g = new LinkedList();
  private byte[] h = new byte[0];
  private byte[] i = new byte[0];
  private boolean j = false;

  private int a(int paramInt)
  {
    String str = this.b.getNetworkOperator();
    if ((str != null) && (str.length() >= 3));
    int k;
    while (true)
    {
      try
      {
        int m = Integer.valueOf(str.substring(0, 3)).intValue();
        k = m;
        if ((paramInt != 2) || (k != -1))
          break;
        return 0;
      }
      catch (Exception localException)
      {
      }
      k = -1;
    }
    return k;
  }

  public final void a()
  {
    synchronized (this.h)
    {
      if (!this.f)
        return;
      if (this.b != null)
      {
        a locala = this.c;
        if (locala == null);
      }
    }
    try
    {
      this.b.listen(this.c, 0);
      this.f = false;
      return;
      localObject = finally;
      throw localObject;
    }
    catch (Exception localException)
    {
      while (true)
        this.f = false;
    }
  }

  public final boolean a(Context paramContext, c paramc)
  {
    synchronized (this.h)
    {
      if (this.f)
        return true;
      if ((paramContext == null) || (paramc == null))
        return false;
      this.a = paramContext;
      this.d = paramc;
      try
      {
        this.b = ((TelephonyManager)this.a.getSystemService("phone"));
        TelephonyManager localTelephonyManager = this.b;
        if (localTelephonyManager == null)
          return false;
        int k = this.b.getPhoneType();
        this.c = new a(a(k), k);
        a locala = this.c;
        if (locala == null)
          return false;
        this.b.listen(this.c, 18);
        this.f = true;
        return this.f;
      }
      catch (Exception localException)
      {
        return false;
      }
    }
  }

  public final List<NeighboringCellInfo> b()
  {
    synchronized (this.i)
    {
      List localList = this.g;
      LinkedList localLinkedList = null;
      if (localList != null)
      {
        localLinkedList = new LinkedList();
        localLinkedList.addAll(this.g);
      }
      return localLinkedList;
    }
  }

  public final class a extends PhoneStateListener
  {
    private int a = 0;
    private int b = 0;
    private int c = 0;
    private int d = 0;
    private int e = 0;
    private int f = -1;
    private int g = 2147483647;
    private int h = 2147483647;
    private Method i = null;
    private Method j = null;
    private Method k = null;
    private Method l = null;
    private Method m = null;

    public a(int paramInt1, int arg3)
    {
      this.b = paramInt1;
      int i1;
      this.a = i1;
    }

    // ERROR //
    public final void onCellLocationChanged(android.telephony.CellLocation paramCellLocation)
    {
      // Byte code:
      //   0: aload_0
      //   1: iconst_m1
      //   2: putfield 40	com/tencent/a/b/d$a:f	I
      //   5: aload_0
      //   6: iconst_m1
      //   7: putfield 38	com/tencent/a/b/d$a:e	I
      //   10: aload_0
      //   11: iconst_m1
      //   12: putfield 36	com/tencent/a/b/d$a:d	I
      //   15: aload_0
      //   16: iconst_m1
      //   17: putfield 34	com/tencent/a/b/d$a:c	I
      //   20: aload_1
      //   21: ifnull +31 -> 52
      //   24: aload_0
      //   25: getfield 30	com/tencent/a/b/d$a:a	I
      //   28: tableswitch	default:+24 -> 52, 1:+105->133, 2:+260->288
      //   53: getfield 25	com/tencent/a/b/d$a:n	Lcom/tencent/a/b/d;
      //   56: new 61	com/tencent/a/b/d$b
      //   59: dup
      //   60: aload_0
      //   61: getfield 25	com/tencent/a/b/d$a:n	Lcom/tencent/a/b/d;
      //   64: aload_0
      //   65: getfield 30	com/tencent/a/b/d$a:a	I
      //   68: aload_0
      //   69: getfield 32	com/tencent/a/b/d$a:b	I
      //   72: aload_0
      //   73: getfield 34	com/tencent/a/b/d$a:c	I
      //   76: aload_0
      //   77: getfield 36	com/tencent/a/b/d$a:d	I
      //   80: aload_0
      //   81: getfield 38	com/tencent/a/b/d$a:e	I
      //   84: aload_0
      //   85: getfield 40	com/tencent/a/b/d$a:f	I
      //   88: aload_0
      //   89: getfield 43	com/tencent/a/b/d$a:g	I
      //   92: aload_0
      //   93: getfield 45	com/tencent/a/b/d$a:h	I
      //   96: invokespecial 64	com/tencent/a/b/d$b:<init>	(Lcom/tencent/a/b/d;IIIIIIII)V
      //   99: invokestatic 69	com/tencent/a/b/d:a	(Lcom/tencent/a/b/d;Lcom/tencent/a/b/d$b;)Lcom/tencent/a/b/d$b;
      //   102: pop
      //   103: aload_0
      //   104: getfield 25	com/tencent/a/b/d$a:n	Lcom/tencent/a/b/d;
      //   107: invokestatic 72	com/tencent/a/b/d:a	(Lcom/tencent/a/b/d;)Lcom/tencent/a/b/d$c;
      //   110: ifnull +22 -> 132
      //   113: aload_0
      //   114: getfield 25	com/tencent/a/b/d$a:n	Lcom/tencent/a/b/d;
      //   117: invokestatic 72	com/tencent/a/b/d:a	(Lcom/tencent/a/b/d;)Lcom/tencent/a/b/d$c;
      //   120: aload_0
      //   121: getfield 25	com/tencent/a/b/d$a:n	Lcom/tencent/a/b/d;
      //   124: invokestatic 75	com/tencent/a/b/d:b	(Lcom/tencent/a/b/d;)Lcom/tencent/a/b/d$b;
      //   127: invokeinterface 80 2 0
      //   132: return
      //   133: aload_1
      //   134: checkcast 82	android/telephony/gsm/GsmCellLocation
      //   137: astore 5
      //   139: aload 5
      //   141: invokevirtual 86	android/telephony/gsm/GsmCellLocation:getLac	()I
      //   144: ifgt +30 -> 174
      //   147: aload 5
      //   149: invokevirtual 89	android/telephony/gsm/GsmCellLocation:getCid	()I
      //   152: ifgt +22 -> 174
      //   155: aload_0
      //   156: getfield 25	com/tencent/a/b/d$a:n	Lcom/tencent/a/b/d;
      //   159: invokestatic 92	com/tencent/a/b/d:d	(Lcom/tencent/a/b/d;)Landroid/telephony/TelephonyManager;
      //   162: invokevirtual 98	android/telephony/TelephonyManager:getCellLocation	()Landroid/telephony/CellLocation;
      //   165: checkcast 82	android/telephony/gsm/GsmCellLocation
      //   168: astore 10
      //   170: aload 10
      //   172: astore 5
      //   174: iconst_1
      //   175: istore 6
      //   177: iload 6
      //   179: ifeq -127 -> 52
      //   182: aload 5
      //   184: ifnull -132 -> 52
      //   187: aload_0
      //   188: getfield 25	com/tencent/a/b/d$a:n	Lcom/tencent/a/b/d;
      //   191: invokestatic 92	com/tencent/a/b/d:d	(Lcom/tencent/a/b/d;)Landroid/telephony/TelephonyManager;
      //   194: invokevirtual 102	android/telephony/TelephonyManager:getNetworkOperator	()Ljava/lang/String;
      //   197: astore 7
      //   199: aload 7
      //   201: ifnull +28 -> 229
      //   204: aload 7
      //   206: invokevirtual 107	java/lang/String:length	()I
      //   209: iconst_3
      //   210: if_icmple +19 -> 229
      //   213: aload_0
      //   214: aload 7
      //   216: iconst_3
      //   217: invokevirtual 111	java/lang/String:substring	(I)Ljava/lang/String;
      //   220: invokestatic 117	java/lang/Integer:valueOf	(Ljava/lang/String;)Ljava/lang/Integer;
      //   223: invokevirtual 120	java/lang/Integer:intValue	()I
      //   226: putfield 34	com/tencent/a/b/d$a:c	I
      //   229: aload_0
      //   230: aload 5
      //   232: invokevirtual 86	android/telephony/gsm/GsmCellLocation:getLac	()I
      //   235: putfield 36	com/tencent/a/b/d$a:d	I
      //   238: aload_0
      //   239: aload 5
      //   241: invokevirtual 89	android/telephony/gsm/GsmCellLocation:getCid	()I
      //   244: putfield 38	com/tencent/a/b/d$a:e	I
      //   247: aload_0
      //   248: getfield 25	com/tencent/a/b/d$a:n	Lcom/tencent/a/b/d;
      //   251: invokestatic 123	com/tencent/a/b/d:c	(Lcom/tencent/a/b/d;)V
      //   254: goto -202 -> 52
      //   257: astore 4
      //   259: aconst_null
      //   260: astore 5
      //   262: iconst_0
      //   263: istore 6
      //   265: goto -88 -> 177
      //   268: astore 8
      //   270: aload_0
      //   271: iconst_m1
      //   272: putfield 38	com/tencent/a/b/d$a:e	I
      //   275: aload_0
      //   276: iconst_m1
      //   277: putfield 36	com/tencent/a/b/d$a:d	I
      //   280: aload_0
      //   281: iconst_m1
      //   282: putfield 34	com/tencent/a/b/d$a:c	I
      //   285: goto -38 -> 247
      //   288: aload_1
      //   289: ifnull -237 -> 52
      //   292: aload_0
      //   293: getfield 47	com/tencent/a/b/d$a:i	Ljava/lang/reflect/Method;
      //   296: ifnonnull +93 -> 389
      //   299: aload_0
      //   300: ldc 125
      //   302: invokestatic 131	java/lang/Class:forName	(Ljava/lang/String;)Ljava/lang/Class;
      //   305: ldc 133
      //   307: iconst_0
      //   308: anewarray 127	java/lang/Class
      //   311: invokevirtual 137	java/lang/Class:getMethod	(Ljava/lang/String;[Ljava/lang/Class;)Ljava/lang/reflect/Method;
      //   314: putfield 47	com/tencent/a/b/d$a:i	Ljava/lang/reflect/Method;
      //   317: aload_0
      //   318: ldc 125
      //   320: invokestatic 131	java/lang/Class:forName	(Ljava/lang/String;)Ljava/lang/Class;
      //   323: ldc 139
      //   325: iconst_0
      //   326: anewarray 127	java/lang/Class
      //   329: invokevirtual 137	java/lang/Class:getMethod	(Ljava/lang/String;[Ljava/lang/Class;)Ljava/lang/reflect/Method;
      //   332: putfield 49	com/tencent/a/b/d$a:j	Ljava/lang/reflect/Method;
      //   335: aload_0
      //   336: ldc 125
      //   338: invokestatic 131	java/lang/Class:forName	(Ljava/lang/String;)Ljava/lang/Class;
      //   341: ldc 141
      //   343: iconst_0
      //   344: anewarray 127	java/lang/Class
      //   347: invokevirtual 137	java/lang/Class:getMethod	(Ljava/lang/String;[Ljava/lang/Class;)Ljava/lang/reflect/Method;
      //   350: putfield 51	com/tencent/a/b/d$a:k	Ljava/lang/reflect/Method;
      //   353: aload_0
      //   354: ldc 125
      //   356: invokestatic 131	java/lang/Class:forName	(Ljava/lang/String;)Ljava/lang/Class;
      //   359: ldc 143
      //   361: iconst_0
      //   362: anewarray 127	java/lang/Class
      //   365: invokevirtual 137	java/lang/Class:getMethod	(Ljava/lang/String;[Ljava/lang/Class;)Ljava/lang/reflect/Method;
      //   368: putfield 53	com/tencent/a/b/d$a:l	Ljava/lang/reflect/Method;
      //   371: aload_0
      //   372: ldc 125
      //   374: invokestatic 131	java/lang/Class:forName	(Ljava/lang/String;)Ljava/lang/Class;
      //   377: ldc 145
      //   379: iconst_0
      //   380: anewarray 127	java/lang/Class
      //   383: invokevirtual 137	java/lang/Class:getMethod	(Ljava/lang/String;[Ljava/lang/Class;)Ljava/lang/reflect/Method;
      //   386: putfield 55	com/tencent/a/b/d$a:m	Ljava/lang/reflect/Method;
      //   389: aload_0
      //   390: aload_0
      //   391: getfield 49	com/tencent/a/b/d$a:j	Ljava/lang/reflect/Method;
      //   394: aload_1
      //   395: iconst_0
      //   396: anewarray 147	java/lang/Object
      //   399: invokevirtual 153	java/lang/reflect/Method:invoke	(Ljava/lang/Object;[Ljava/lang/Object;)Ljava/lang/Object;
      //   402: checkcast 113	java/lang/Integer
      //   405: invokevirtual 120	java/lang/Integer:intValue	()I
      //   408: putfield 34	com/tencent/a/b/d$a:c	I
      //   411: aload_0
      //   412: aload_0
      //   413: getfield 51	com/tencent/a/b/d$a:k	Ljava/lang/reflect/Method;
      //   416: aload_1
      //   417: iconst_0
      //   418: anewarray 147	java/lang/Object
      //   421: invokevirtual 153	java/lang/reflect/Method:invoke	(Ljava/lang/Object;[Ljava/lang/Object;)Ljava/lang/Object;
      //   424: checkcast 113	java/lang/Integer
      //   427: invokevirtual 120	java/lang/Integer:intValue	()I
      //   430: putfield 36	com/tencent/a/b/d$a:d	I
      //   433: aload_0
      //   434: aload_0
      //   435: getfield 47	com/tencent/a/b/d$a:i	Ljava/lang/reflect/Method;
      //   438: aload_1
      //   439: iconst_0
      //   440: anewarray 147	java/lang/Object
      //   443: invokevirtual 153	java/lang/reflect/Method:invoke	(Ljava/lang/Object;[Ljava/lang/Object;)Ljava/lang/Object;
      //   446: checkcast 113	java/lang/Integer
      //   449: invokevirtual 120	java/lang/Integer:intValue	()I
      //   452: putfield 38	com/tencent/a/b/d$a:e	I
      //   455: aload_0
      //   456: aload_0
      //   457: getfield 53	com/tencent/a/b/d$a:l	Ljava/lang/reflect/Method;
      //   460: aload_1
      //   461: iconst_0
      //   462: anewarray 147	java/lang/Object
      //   465: invokevirtual 153	java/lang/reflect/Method:invoke	(Ljava/lang/Object;[Ljava/lang/Object;)Ljava/lang/Object;
      //   468: checkcast 113	java/lang/Integer
      //   471: invokevirtual 120	java/lang/Integer:intValue	()I
      //   474: putfield 43	com/tencent/a/b/d$a:g	I
      //   477: aload_0
      //   478: aload_0
      //   479: getfield 55	com/tencent/a/b/d$a:m	Ljava/lang/reflect/Method;
      //   482: aload_1
      //   483: iconst_0
      //   484: anewarray 147	java/lang/Object
      //   487: invokevirtual 153	java/lang/reflect/Method:invoke	(Ljava/lang/Object;[Ljava/lang/Object;)Ljava/lang/Object;
      //   490: checkcast 113	java/lang/Integer
      //   493: invokevirtual 120	java/lang/Integer:intValue	()I
      //   496: putfield 45	com/tencent/a/b/d$a:h	I
      //   499: goto -447 -> 52
      //   502: astore_3
      //   503: aload_0
      //   504: iconst_m1
      //   505: putfield 38	com/tencent/a/b/d$a:e	I
      //   508: aload_0
      //   509: iconst_m1
      //   510: putfield 36	com/tencent/a/b/d$a:d	I
      //   513: aload_0
      //   514: iconst_m1
      //   515: putfield 34	com/tencent/a/b/d$a:c	I
      //   518: aload_0
      //   519: ldc 41
      //   521: putfield 43	com/tencent/a/b/d$a:g	I
      //   524: aload_0
      //   525: ldc 41
      //   527: putfield 45	com/tencent/a/b/d$a:h	I
      //   530: goto -478 -> 52
      //   533: astore 9
      //   535: goto -273 -> 262
      //
      // Exception table:
      //   from	to	target	type
      //   133	139	257	java/lang/Exception
      //   204	229	268	java/lang/Exception
      //   229	247	268	java/lang/Exception
      //   292	389	502	java/lang/Exception
      //   389	499	502	java/lang/Exception
      //   139	170	533	java/lang/Exception
    }

    public final void onSignalStrengthChanged(int paramInt)
    {
      if (this.a == 1)
        d.c(d.this);
      if (Math.abs(paramInt - (113 + this.f) / 2) > 3)
      {
        if (this.f != -1)
          break label52;
        this.f = (-113 + (paramInt << 1));
      }
      label52: 
      do
      {
        return;
        this.f = (-113 + (paramInt << 1));
        d.a(d.this, new d.b(d.this, this.a, this.b, this.c, this.d, this.e, this.f, this.g, this.h));
      }
      while (d.a(d.this) == null);
      d.a(d.this).a(d.b(d.this));
    }
  }

  public final class b
    implements Cloneable
  {
    public int a = 0;
    public int b = 0;
    public int c = 0;
    public int d = 0;
    public int e = 0;
    public int f = 0;
    public int g = 2147483647;
    public int h = 2147483647;

    public b(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7, int arg9)
    {
      this.a = paramInt1;
      this.b = paramInt2;
      this.c = paramInt3;
      this.d = paramInt4;
      this.e = paramInt5;
      this.f = paramInt6;
      this.g = paramInt7;
      int i;
      this.h = i;
    }

    public final Object clone()
    {
      try
      {
        b localb = (b)super.clone();
        return localb;
      }
      catch (Exception localException)
      {
      }
      return null;
    }
  }

  public static abstract interface c
  {
    public abstract void a(d.b paramb);
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.tencent.a.b.d
 * JD-Core Version:    0.6.2
 */