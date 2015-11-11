package com.taobao.newxp.view.common.gif;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.util.Log;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class d extends Thread
{
  private static final int O = 4096;
  private static final int U = 15;
  public static final int a = 0;
  public static final int b = 1;
  public static final int c = 2;
  public static final int d = -1;
  private int A;
  private int B;
  private int C;
  private int D;
  private int E;
  private Bitmap F;
  private Bitmap G;
  private byte[] H = new byte[256];
  private int I = 0;
  private int J = 0;
  private int K = 0;
  private boolean L = false;
  private int M = 0;
  private int N;
  private short[] P;
  private byte[] Q;
  private byte[] R;
  private byte[] S;
  private int T;
  private Queue<f> V = new ArrayBlockingQueue(15);
  private final ReentrantLock W = new ReentrantLock();
  private final Condition X = this.W.newCondition();
  private final Condition Y = this.W.newCondition();
  private int Z = 0;
  private boolean aa = false;
  private ArrayList<f> ab = new ArrayList(this.T);
  private int ac = 0;
  private boolean ad = false;
  private b ae = null;
  private byte[] af = null;
  private boolean ag = false;
  private int ah = 0;
  private Resources ai = null;
  private int aj = 0;
  private String ak = null;
  private int[] al = null;
  private int[] am = new int[256];
  public boolean e = false;
  public int f;
  public int g;
  private InputStream h;
  private InputStream i;
  private volatile int j;
  private boolean k;
  private int l;
  private int m = 1;
  private int[] n;
  private int[] o;
  private int[] p;
  private int q;
  private int r;
  private int s;
  private int t;
  private boolean u;
  private boolean v;
  private int w;
  private int x;
  private int y;
  private int z;

  public d(b paramb)
  {
    this.ae = paramb;
  }

  public d(b paramb, boolean paramBoolean)
  {
    this.ae = paramb;
    this.ag = paramBoolean;
  }

  private void A()
  {
    do
    {
      u();
      if (this.H[0] == 1)
        this.m = (0xFF & this.H[1] | (0xFF & this.H[2]) << 8);
    }
    while ((this.I > 0) && (!r()));
  }

  private int B()
  {
    return t() | t() << 8;
  }

  private void C()
  {
    this.K = this.J;
    this.B = this.x;
    this.C = this.y;
    this.D = this.z;
    this.E = this.A;
    this.G = this.F;
    this.s = this.r;
    this.J = 0;
    this.L = false;
    this.M = 0;
    this.o = null;
  }

  private void D()
  {
    do
      u();
    while ((this.I > 0) && (!r()));
  }

  private int[] a(int paramInt)
  {
    int i1 = 0;
    int i2 = paramInt * 3;
    byte[] arrayOfByte = new byte[i2];
    while (true)
    {
      int i4;
      try
      {
        int i11 = this.h.read(arrayOfByte);
        i3 = i11;
        if (i3 < i2)
        {
          this.j = 1;
          return this.am;
        }
      }
      catch (Exception localException)
      {
        localException.printStackTrace();
        int i3 = 0;
        continue;
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
        int[] arrayOfInt = this.am;
        int i10 = i1 + 1;
        arrayOfInt[i1] = (i9 | (0xFF000000 | i6 << 16 | i8 << 8));
        i1 = i10;
      }
    }
  }

  private void j()
  {
    this.h = new ByteArrayInputStream(this.af);
  }

  private void k()
  {
    this.h = this.ai.openRawResource(this.aj);
  }

  private void l()
  {
    try
    {
      this.h = new FileInputStream(this.ak);
      return;
    }
    catch (Exception localException)
    {
      Log.e("open failed", localException.toString());
    }
  }

  private void m()
  {
    if (this.h != null);
    try
    {
      this.h.close();
      label14: this.h = null;
      this.af = null;
      this.j = 0;
      if (this.ab != null)
      {
        this.ab.clear();
        this.ab = null;
      }
      if (this.V != null)
      {
        this.V.clear();
        this.V = null;
      }
      return;
    }
    catch (Exception localException)
    {
      break label14;
    }
  }

  private void n()
  {
    while (true)
    {
      int i2;
      int i11;
      int i13;
      int i14;
      label339: int i15;
      try
      {
        if (this.al == null)
          this.al = new int[this.f * this.g];
        if (this.K > 0)
        {
          if (this.K == 3)
          {
            if (-2 + this.T > 0)
              this.G = null;
          }
          else
          {
            if (this.G == null)
              continue;
            this.G.getPixels(this.al, 0, this.f, 0, 0, this.f, this.g);
            if (this.K != 2)
              continue;
            if (this.L)
              break label463;
            i18 = this.s;
            break label466;
            if (i19 >= this.E)
              continue;
            int i20 = (i19 + this.C) * this.f + this.B;
            int i21 = i20 + this.D;
            if (i20 >= i21)
              continue;
            this.al[i20] = i18;
            i20++;
            continue;
          }
          this.G = null;
          continue;
        }
      }
      catch (OutOfMemoryError localOutOfMemoryError)
      {
        localOutOfMemoryError.printStackTrace();
        return;
        i19++;
        continue;
        i1 = 8;
        i2 = 0;
        i3 = 0;
        i4 = 1;
        if (i2 < this.A)
        {
          if (!this.v)
            break label444;
          if (i3 < this.A)
            break label472;
          i4++;
        }
        switch (i4)
        {
        default:
          int i8 = i3 + this.y;
          if (i8 >= this.g)
            break label524;
          int i9 = i8 * this.f;
          int i10 = i9 + this.x;
          i11 = i10 + this.z;
          if (i9 + this.f < i11)
          {
            i12 = i9 + this.f;
            i13 = i2 * this.z;
            i14 = i10;
            if (i14 >= i12)
              break label524;
            byte[] arrayOfByte = this.S;
            i15 = i13 + 1;
            int i16 = 0xFF & arrayOfByte[i13];
            int i17 = this.p[i16];
            if (i17 == 0)
              break label490;
            this.al[i14] = i17;
            break label490;
            this.F = Bitmap.createBitmap(this.al, this.f, this.g, Bitmap.Config.RGB_565);
            return;
          }
          break;
        case 2:
        case 3:
        case 4:
        }
      }
      catch (StackOverflowError localStackOverflowError)
      {
        localStackOverflowError.printStackTrace();
        return;
      }
      catch (Exception localException)
      {
        Log.e("GifView decode setpixel", localException.toString());
        return;
      }
      int i12 = i11;
      continue;
      label444: int i5 = i3;
      int i6 = i1;
      int i7 = i4;
      int i3 = i2;
      continue;
      label463: int i18 = 0;
      label466: int i19 = 0;
      continue;
      while (true)
      {
        label472: i5 = i3 + i1;
        i6 = i1;
        i7 = i4;
        break;
        label490: i14++;
        i13 = i15;
        break label339;
        i3 = 4;
        continue;
        i3 = 2;
        i1 = 4;
        continue;
        i3 = 1;
        i1 = 2;
      }
      label524: i2++;
      i3 = i5;
      int i1 = i6;
      int i4 = i7;
    }
  }

  private int o()
  {
    s();
    if (this.h != null)
    {
      x();
      if (!r())
      {
        v();
        if ((!this.aa) && (this.T < 0))
        {
          this.j = 1;
          if (this.ae != null)
            this.ae.parseReturn(4);
        }
      }
    }
    while (true)
    {
      try
      {
        if (this.h != null)
          this.h.close();
        this.h = null;
        p();
        return this.j;
      }
      catch (Exception localException)
      {
        localException.printStackTrace();
        continue;
      }
      this.j = 2;
      if (this.ae != null)
        this.ae.parseReturn(4);
    }
  }

  private void p()
  {
    if ((!this.ag) || (this.ad))
      label14: return;
    if (this.T <= 15);
    while (true)
    {
      try
      {
        this.W.lockInterruptibly();
        this.ad = true;
        this.j = -1;
        if (this.ae != null)
          this.ae.parseReturn(2);
        this.X.signal();
        this.W.unlock();
        switch (this.ah)
        {
        default:
          this.aa = true;
          if (this.e)
            break label14;
          o();
          return;
        case 1:
        case 2:
        case 3:
        }
      }
      catch (Exception localException)
      {
        this.W.unlock();
        continue;
      }
      finally
      {
        this.W.unlock();
      }
      if (this.ab != null)
      {
        this.ab.clear();
        continue;
        k();
        continue;
        l();
        continue;
        j();
      }
    }
  }

  private void q()
  {
    int i1 = this.z * this.A;
    if ((this.S == null) || (this.S.length < i1))
      this.S = new byte[i1];
    if (this.P == null)
      this.P = new short[4096];
    if (this.Q == null)
      this.Q = new byte[4096];
    if (this.R == null)
      this.R = new byte[4097];
    int i2 = t();
    int i3 = 1 << i2;
    int i4 = i3 + 1;
    int i5 = i3 + 2;
    int i6 = i2 + 1;
    int i7 = -1 + (1 << i6);
    for (int i8 = 0; i8 < i3; i8++)
    {
      this.P[i8] = 0;
      this.Q[i8] = ((byte)i8);
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
      i14 = u();
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
          this.S[i18] = 0;
        i17 = 0;
        i12 += ((0xFF & this.H[i17]) << i15);
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
        byte[] arrayOfByte5 = this.R;
        int i36 = i10 + 1;
        arrayOfByte5[i10] = this.Q[i30];
        i10 = i36;
        i16 = i30;
        i11 = i30;
        break;
      }
      if (i30 != i5)
        break label650;
      byte[] arrayOfByte4 = this.R;
      i31 = i10 + 1;
      arrayOfByte4[i10] = ((byte)i11);
      i32 = i16;
      while (i32 > i3)
      {
        byte[] arrayOfByte3 = this.R;
        int i35 = i31 + 1;
        arrayOfByte3[i31] = this.Q[i32];
        i32 = this.P[i32];
        i31 = i35;
      }
      i33 = 0xFF & this.Q[i32];
    }
    while (i5 >= 4096);
    byte[] arrayOfByte2 = this.R;
    int i34 = i31 + 1;
    arrayOfByte2[i31] = ((byte)i33);
    this.P[i5] = ((short)i16);
    this.Q[i5] = ((byte)i33);
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
      byte[] arrayOfByte1 = this.S;
      int i28 = i9 + 1;
      arrayOfByte1[i9] = this.R[i27];
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

  private boolean r()
  {
    return this.j != 0;
  }

  private void s()
  {
    this.j = 0;
    if (!this.aa)
      this.T = 0;
    this.n = null;
    this.o = null;
    this.Z = 0;
  }

  private int t()
  {
    try
    {
      int i1 = this.h.read();
      return i1;
    }
    catch (Exception localException)
    {
      this.j = 1;
    }
    return 0;
  }

  private int u()
  {
    this.I = t();
    int i1 = this.I;
    int i2 = 0;
    if (i1 > 0);
    try
    {
      while (true)
      {
        int i3;
        if (i2 < this.I)
        {
          i3 = this.h.read(this.H, i2, this.I - i2);
          if (i3 != -1);
        }
        else
        {
          if (i2 < this.I)
            this.j = 1;
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

  private void v()
  {
    int i1 = 0;
    while ((i1 == 0) && (!r()) && (!this.e))
      switch (t())
      {
      case 0:
      default:
        this.j = 1;
        break;
      case 44:
        y();
        break;
      case 33:
        switch (t())
        {
        default:
          D();
          break;
        case 249:
          w();
          break;
        case 255:
          u();
          String str = "";
          for (int i2 = 0; i2 < 11; i2++)
            str = str + (char)this.H[i2];
          if (str.equals("NETSCAPE2.0"))
            A();
          else
            D();
          break;
        }
        break;
      case 59:
        i1 = 1;
      }
  }

  private void w()
  {
    int i1 = 1;
    t();
    int i2 = t();
    this.J = ((i2 & 0x1C) >> 2);
    if (this.J == 0)
      this.J = i1;
    if ((i2 & 0x1) != 0);
    while (true)
    {
      this.L = i1;
      this.M = (10 * B());
      if (this.M == 0)
        this.M = 100;
      this.N = t();
      t();
      return;
      i1 = 0;
    }
  }

  private void x()
  {
    String str = "";
    for (int i1 = 0; i1 < 6; i1++)
      str = str + (char)t();
    if (!str.startsWith("GIF"))
      this.j = 1;
    do
    {
      return;
      z();
    }
    while ((!this.k) || (r()));
    this.n = a(this.l);
    this.r = this.n[this.q];
  }

  private void y()
  {
    this.x = B();
    this.y = B();
    this.z = B();
    this.A = B();
    int i1 = t();
    boolean bool1;
    boolean bool2;
    label61: label103: int i2;
    if ((i1 & 0x80) != 0)
    {
      bool1 = true;
      this.u = bool1;
      if ((i1 & 0x40) == 0)
        break label219;
      bool2 = true;
      this.v = bool2;
      this.w = (2 << (i1 & 0x7));
      if (!this.u)
        break label224;
      this.o = a(this.w);
      this.p = this.o;
      boolean bool3 = this.L;
      i2 = 0;
      if (bool3)
      {
        int[] arrayOfInt = this.p;
        i2 = 0;
        if (arrayOfInt != null)
        {
          int i3 = this.p.length;
          i2 = 0;
          if (i3 > 0)
          {
            int i4 = this.p.length;
            int i5 = this.N;
            i2 = 0;
            if (i4 > i5)
            {
              int i6 = this.p[this.N];
              this.p[this.N] = 0;
              i2 = i6;
            }
          }
        }
      }
      if (this.p == null)
        this.j = 1;
      if (!r())
        break label251;
    }
    label219: label224: label251: 
    do
    {
      return;
      bool1 = false;
      break;
      bool2 = false;
      break label61;
      this.p = this.n;
      if (this.q != this.N)
        break label103;
      this.r = 0;
      break label103;
      q();
      D();
    }
    while (r());
    if (!this.aa)
      this.T = (1 + this.T);
    n();
    try
    {
      this.W.lockInterruptibly();
    }
    catch (Exception localException)
    {
      try
      {
        while ((this.V != null) && (this.V.size() >= 15))
          this.Y.await();
      }
      catch (InterruptedException localInterruptedException)
      {
        this.Y.signal();
        this.W.unlock();
        if (this.L)
          this.p[this.N] = i2;
        C();
        return;
        if (this.V != null)
        {
          f localf = new f(this.F, this.M);
          this.V.add(localf);
          if (!this.aa)
            this.ab.add(localf);
          this.X.signal();
          if ((!this.aa) && (this.Z >= 0))
          {
            this.Z = (1 + this.Z);
            if (this.Z < 15)
              break label498;
            this.ae.parseReturn(3);
            this.Z = -1;
          }
        }
        while (true)
        {
          this.W.unlock();
          break;
          localException = localException;
          localException.printStackTrace();
          break;
          label498: if (this.Z == 1)
            this.ae.parseReturn(1);
        }
      }
      finally
      {
        this.W.unlock();
      }
    }
  }

  private void z()
  {
    this.f = B();
    this.g = B();
    int i1 = t();
    if ((i1 & 0x80) != 0);
    for (boolean bool = true; ; bool = false)
    {
      this.k = bool;
      this.l = (2 << (i1 & 0x7));
      this.q = t();
      this.t = t();
      return;
    }
  }

  public void a()
  {
    this.ag = true;
  }

  public void a(Resources paramResources, int paramInt)
  {
    this.ai = paramResources;
    this.aj = paramInt;
    k();
    this.ah = 1;
  }

  public void a(String paramString)
  {
    this.ak = paramString;
    this.ah = 2;
    l();
  }

  public void a(byte[] paramArrayOfByte)
  {
    this.af = paramArrayOfByte;
    j();
    this.ah = 3;
  }

  public InputStream b()
  {
    return this.i;
  }

  public int c()
  {
    return this.j;
  }

  public int d()
  {
    if ((!this.aa) && (this.j != -1))
      return -1;
    return this.T;
  }

  public void destroy()
  {
    this.e = true;
    m();
    this.ae = null;
  }

  public Bitmap e()
  {
    return g();
  }

  public int f()
  {
    return this.m;
  }

  public Bitmap g()
  {
    f localf = h();
    if (localf == null)
      return null;
    return localf.a;
  }

  public f h()
  {
    if ((this.ad) && (this.V.size() == 0))
      try
      {
        if (this.ac >= this.T)
        {
          this.ac = 0;
          this.ae.loopEnd();
        }
        f localf2 = (f)this.ab.get(this.ac);
        this.ac = (1 + this.ac);
        return localf2;
      }
      catch (Exception localException3)
      {
        return null;
      }
    try
    {
      this.W.lockInterruptibly();
      try
      {
        while ((!this.ad) && (this.V.size() == 0))
          this.X.await();
      }
      catch (Exception localException2)
      {
        this.X.signal();
        return null;
      }
      f localf1 = (f)this.V.poll();
      this.Y.signal();
      this.ac = (1 + this.ac);
      if ((this.aa) && (this.ac >= this.T))
      {
        this.ae.loopEnd();
        this.ac = 0;
      }
      return localf1;
    }
    catch (Exception localException1)
    {
      return null;
    }
    finally
    {
      this.W.unlock();
    }
  }

  public f i()
  {
    return h();
  }

  public void run()
  {
    try
    {
      o();
      return;
    }
    catch (Exception localException)
    {
      Log.e("GifView decode run", localException.toString());
      localException.printStackTrace();
    }
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.taobao.newxp.view.common.gif.d
 * JD-Core Version:    0.6.2
 */