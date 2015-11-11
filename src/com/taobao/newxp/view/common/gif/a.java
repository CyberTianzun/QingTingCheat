package com.taobao.newxp.view.common.gif;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import java.io.InputStream;
import java.util.Vector;

public class a
{
  protected Bitmap A;
  protected Bitmap B;
  protected byte[] C = new byte[256];
  protected int D = 0;
  protected int E = 0;
  protected int F = 0;
  protected boolean G = false;
  protected int H = 0;
  protected int I;
  protected short[] J;
  protected byte[] K;
  protected byte[] L;
  protected byte[] M;
  protected Vector<a> N;
  protected int O;
  private final int P = 0;
  private final int Q = 1;
  private final int R = 2;
  private final int S = 3;
  protected final int a = 4096;
  protected InputStream b;
  protected int c;
  protected int d;
  protected int e;
  protected boolean f;
  protected int g;
  protected int h = 1;
  protected int[] i;
  protected int[] j;
  protected int[] k;
  protected int l;
  protected int m;
  protected int n;
  protected int o;
  protected boolean p;
  protected boolean q;
  protected int r;
  protected int s;
  protected int t;
  protected int u;
  protected int v;
  protected int w;
  protected int x;
  protected int y;
  protected int z;

  public int a()
  {
    return this.O;
  }

  public int a(int paramInt)
  {
    this.H = -1;
    if ((paramInt >= 0) && (paramInt < this.O))
      this.H = ((a)this.N.elementAt(paramInt)).b;
    return this.H;
  }

  public int a(InputStream paramInputStream)
  {
    g();
    if (paramInputStream != null)
    {
      this.b = paramInputStream;
      l();
      if (!f())
      {
        j();
        if (this.O < 0)
          this.c = 1;
      }
    }
    while (true)
    {
      if (paramInputStream != null);
      try
      {
        paramInputStream.close();
        label48: return this.c;
        this.c = 2;
      }
      catch (Exception localException)
      {
        break label48;
      }
    }
  }

  public Bitmap b()
  {
    return b(0);
  }

  public Bitmap b(int paramInt)
  {
    if (this.O <= 0)
      return null;
    int i1 = paramInt % this.O;
    return ((a)this.N.elementAt(i1)).a;
  }

  public int c()
  {
    return this.h;
  }

  protected int[] c(int paramInt)
  {
    int i1 = 0;
    int i2 = paramInt * 3;
    int[] arrayOfInt = null;
    byte[] arrayOfByte = new byte[i2];
    while (true)
    {
      int i4;
      try
      {
        int i11 = this.b.read(arrayOfByte);
        i3 = i11;
        if (i3 < i2)
        {
          this.c = 1;
          return arrayOfInt;
        }
      }
      catch (Exception localException)
      {
        localException.printStackTrace();
        int i3 = 0;
        continue;
        arrayOfInt = new int[256];
        i4 = 0;
      }
      while (i1 < paramInt)
      {
        int i5 = i4 + 1;
        int i6 = 0xFF & arrayOfByte[i4];
        int i7 = i5 + 1;
        int i8 = 0xFF & arrayOfByte[i5];
        i4 = i7 + 1;
        int i9 = 0xFF & arrayOfByte[i7];
        int i10 = i1 + 1;
        arrayOfInt[i1] = (i9 | (0xFF000000 | i6 << 16 | i8 << 8));
        i1 = i10;
      }
    }
  }

  protected void d()
  {
    int i1 = 0;
    int[] arrayOfInt = new int[this.d * this.e];
    if (this.F > 0)
      if (this.F == 3)
      {
        int i21 = -2 + this.O;
        if (i21 > 0)
          this.B = b(i21 - 1);
      }
      else
      {
        if (this.B == null)
          break label179;
        this.B.getPixels(arrayOfInt, 0, this.d, 0, 0, this.d, this.e);
        if (this.F != 2)
          break label179;
        if (this.G)
          break label454;
      }
    label179: label448: label454: for (int i17 = this.n; ; i17 = 0)
    {
      for (int i18 = 0; ; i18++)
      {
        if (i18 >= this.z)
          break label179;
        int i19 = (i18 + this.x) * this.d + this.w;
        int i20 = i19 + this.y;
        while (true)
          if (i19 < i20)
          {
            arrayOfInt[i19] = i17;
            i19++;
            continue;
            this.B = null;
            break;
          }
      }
      int i2 = 8;
      int i3 = 1;
      int i4 = 0;
      int i16;
      if (i1 < this.v)
      {
        if (!this.q)
          break label448;
        if (i4 >= this.v)
          i3++;
        switch (i3)
        {
        default:
          int i15 = i4 + i2;
          i16 = i4;
          i4 = i15;
        case 2:
        case 3:
        case 4:
        }
      }
      for (int i5 = i16; ; i5 = i1)
      {
        int i6 = i5 + this.t;
        if (i6 < this.e)
        {
          int i7 = i6 * this.d;
          int i8 = i7 + this.s;
          int i9 = i8 + this.u;
          if (i7 + this.d < i9)
            i9 = i7 + this.d;
          int i10 = i1 * this.u;
          int i11 = i8;
          while (true)
            if (i11 < i9)
            {
              byte[] arrayOfByte = this.M;
              int i12 = i10 + 1;
              int i13 = 0xFF & arrayOfByte[i10];
              int i14 = this.k[i13];
              if (i14 != 0)
                arrayOfInt[i11] = i14;
              i11++;
              i10 = i12;
              continue;
              i4 = 4;
              break;
              i4 = 2;
              i2 = 4;
              break;
              i4 = 1;
              i2 = 2;
              break;
            }
        }
        i1++;
        break;
        this.A = Bitmap.createBitmap(arrayOfInt, this.d, this.e, Bitmap.Config.ARGB_4444);
        return;
      }
    }
  }

  protected void e()
  {
    int i1 = this.u * this.v;
    if ((this.M == null) || (this.M.length < i1))
      this.M = new byte[i1];
    if (this.J == null)
      this.J = new short[4096];
    if (this.K == null)
      this.K = new byte[4096];
    if (this.L == null)
      this.L = new byte[4097];
    int i2 = h();
    int i3 = 1 << i2;
    int i4 = i3 + 1;
    int i5 = i3 + 2;
    int i6 = i2 + 1;
    int i7 = -1 + (1 << i6);
    for (int i8 = 0; i8 < i3; i8++)
    {
      this.J[i8] = 0;
      this.K[i8] = ((byte)i8);
    }
    int i9 = 0;
    int i10 = 0;
    int i11 = 0;
    int i12 = 0;
    int i13 = 0;
    int i14 = 0;
    int i15 = 0;
    int i16 = -1;
    int i17 = 0;
    if (i13 < i1)
    {
      if (i10 != 0)
        break label661;
      if (i15 >= i6)
        break label265;
      if (i14 != 0)
        break label234;
      i14 = i();
      if (i14 > 0)
        break label231;
    }
    label231: label234: int i30;
    label265: int i31;
    int i32;
    label406: int i33;
    do
    {
      do
      {
        for (int i18 = i9; i18 < i1; i18++)
          this.M[i18] = 0;
        i17 = 0;
        i12 += ((0xFF & this.C[i17]) << i15);
        i15 += 8;
        i17++;
        i14--;
        break;
        i30 = i12 & i7;
        i12 >>= i6;
        i15 -= i6;
      }
      while ((i30 > i5) || (i30 == i4));
      if (i30 == i3)
      {
        i6 = i2 + 1;
        i7 = -1 + (1 << i6);
        i5 = i3 + 2;
        i16 = -1;
        break;
      }
      if (i16 == -1)
      {
        byte[] arrayOfByte5 = this.L;
        int i36 = i10 + 1;
        arrayOfByte5[i10] = this.K[i30];
        i10 = i36;
        i16 = i30;
        i11 = i30;
        break;
      }
      if (i30 != i5)
        break label650;
      byte[] arrayOfByte4 = this.L;
      i31 = i10 + 1;
      arrayOfByte4[i10] = ((byte)i11);
      i32 = i16;
      while (i32 > i3)
      {
        byte[] arrayOfByte3 = this.L;
        int i35 = i31 + 1;
        arrayOfByte3[i31] = this.K[i32];
        i32 = this.J[i32];
        i31 = i35;
      }
      i33 = 0xFF & this.K[i32];
    }
    while (i5 >= 4096);
    byte[] arrayOfByte2 = this.L;
    int i34 = i31 + 1;
    arrayOfByte2[i31] = ((byte)i33);
    this.J[i5] = ((short)i16);
    this.K[i5] = ((byte)i33);
    int i26 = i5 + 1;
    if (((i26 & i7) == 0) && (i26 < 4096))
    {
      i6++;
      i7 += i26;
    }
    int i24 = i12;
    int i25 = i30;
    int i19 = i7;
    int i20 = i33;
    int i21 = i15;
    int i22 = i6;
    int i23 = i34;
    while (true)
    {
      int i27 = i23 - 1;
      byte[] arrayOfByte1 = this.M;
      int i28 = i9 + 1;
      arrayOfByte1[i9] = this.L[i27];
      i13++;
      i9 = i28;
      i6 = i22;
      i15 = i21;
      i11 = i20;
      i7 = i19;
      int i29 = i25;
      i12 = i24;
      i10 = i27;
      i5 = i26;
      i16 = i29;
      break;
      return;
      label650: i31 = i10;
      i32 = i30;
      break label406;
      label661: i19 = i7;
      i20 = i11;
      i21 = i15;
      i22 = i6;
      i23 = i10;
      i24 = i12;
      i25 = i16;
      i26 = i5;
    }
  }

  protected boolean f()
  {
    return this.c != 0;
  }

  protected void g()
  {
    this.c = 0;
    this.O = 0;
    this.N = new Vector();
    this.i = null;
    this.j = null;
  }

  protected int h()
  {
    try
    {
      int i1 = this.b.read();
      return i1;
    }
    catch (Exception localException)
    {
      this.c = 1;
    }
    return 0;
  }

  protected int i()
  {
    this.D = h();
    int i1 = this.D;
    int i2 = 0;
    if (i1 > 0);
    try
    {
      while (true)
      {
        int i3;
        if (i2 < this.D)
        {
          i3 = this.b.read(this.C, i2, this.D - i2);
          if (i3 != -1);
        }
        else
        {
          if (i2 < this.D)
            this.c = 1;
          return i2;
        }
        i2 += i3;
      }
    }
    catch (Exception localException)
    {
      while (true)
        localException.printStackTrace();
    }
  }

  protected void j()
  {
    int i1 = 0;
    while ((i1 == 0) && (!f()))
      switch (h())
      {
      default:
        this.c = 1;
        break;
      case 44:
        if (!f())
          m();
        break;
      case 33:
        switch (h())
        {
        default:
          r();
          break;
        case 249:
          k();
          break;
        case 255:
          i();
          StringBuffer localStringBuffer = new StringBuffer();
          for (int i2 = 0; i2 < 11; i2++)
            localStringBuffer.append((char)this.C[i2]);
          if (localStringBuffer.toString().equals("NETSCAPE2.0"))
            o();
          else
            r();
          break;
        case 254:
          r();
          break;
        case 1:
          r();
        }
        break;
      case 59:
        i1 = 1;
      }
  }

  protected void k()
  {
    int i1 = 1;
    h();
    int i2 = h();
    this.E = ((i2 & 0x1C) >> 2);
    if (this.E == 0)
      this.E = i1;
    if ((i2 & 0x1) != 0);
    while (true)
    {
      this.G = i1;
      this.H = (10 * p());
      this.I = h();
      h();
      return;
      i1 = 0;
    }
  }

  protected void l()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    for (int i1 = 0; i1 < 6; i1++)
      localStringBuffer.append((char)h());
    if (!localStringBuffer.toString().startsWith("GIF"))
      this.c = 1;
    do
    {
      return;
      n();
    }
    while ((!this.f) || (f()));
    this.i = c(this.g);
    this.m = this.i[this.l];
  }

  protected void m()
  {
    boolean bool1 = true;
    while (true)
    {
      int i2;
      try
      {
        this.s = p();
        this.t = p();
        this.u = p();
        this.v = p();
        int i1 = h();
        if ((i1 & 0x80) == 0)
          break label295;
        bool2 = bool1;
        this.p = bool2;
        this.r = ((int)Math.pow(2.0D, 1 + (i1 & 0x7)));
        if ((i1 & 0x40) == 0)
          break label301;
        this.q = bool1;
        if (this.p)
        {
          this.j = c(this.r);
          this.k = this.j;
          boolean bool3 = this.G;
          i2 = 0;
          if (bool3)
          {
            i2 = this.k[this.I];
            this.k[this.I] = 0;
          }
          if (this.k == null)
            this.c = 1;
          if (!f());
        }
        else
        {
          this.k = this.i;
          if (this.l != this.I)
            continue;
          this.m = 0;
          continue;
        }
      }
      catch (OutOfMemoryError localOutOfMemoryError)
      {
        this.c = 3;
        return;
      }
      e();
      r();
      if (!f())
      {
        this.A = Bitmap.createBitmap(this.d, this.e, Bitmap.Config.ARGB_4444);
        d();
        this.O = (1 + this.O);
        this.N.addElement(new a(this.A, this.H));
        if (this.G)
          this.k[this.I] = i2;
        q();
      }
      return;
      label295: boolean bool2 = false;
      continue;
      label301: bool1 = false;
    }
  }

  protected void n()
  {
    this.d = p();
    this.e = p();
    int i1 = h();
    if ((i1 & 0x80) != 0);
    for (boolean bool = true; ; bool = false)
    {
      this.f = bool;
      this.g = (2 << (i1 & 0x7));
      this.l = h();
      this.o = h();
      return;
    }
  }

  protected void o()
  {
    do
    {
      i();
      if (this.C[0] == 1)
        this.h = (0xFF & this.C[1] | (0xFF & this.C[2]) << 8);
    }
    while ((this.D > 0) && (!f()));
  }

  protected int p()
  {
    return h() | h() << 8;
  }

  protected void q()
  {
    this.F = this.E;
    this.w = this.s;
    this.x = this.t;
    this.y = this.u;
    this.z = this.v;
    this.B = this.A;
    this.n = this.m;
    this.E = 0;
    this.G = false;
    this.H = 0;
    this.j = null;
  }

  protected void r()
  {
    do
      i();
    while ((this.D > 0) && (!f()));
  }

  private class a
  {
    public Bitmap a;
    public int b;

    public a(Bitmap paramInt, int arg3)
    {
      this.a = paramInt;
      int i;
      this.b = i;
    }
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.taobao.newxp.view.common.gif.a
 * JD-Core Version:    0.6.2
 */