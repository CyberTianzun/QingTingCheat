package com.umeng.message.proguard;

import java.math.BigInteger;

public class u extends v
{
  static final byte[] a = { 13, 10 };
  private static final int m = 6;
  private static final int n = 3;
  private static final int o = 4;
  private static final byte[] p = { 65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90, 97, 98, 99, 100, 101, 102, 103, 104, 105, 106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 43, 47 };
  private static final byte[] q = { 65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90, 97, 98, 99, 100, 101, 102, 103, 104, 105, 106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 45, 95 };
  private static final byte[] r = { -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 62, -1, 62, -1, 63, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, -1, -1, -1, -1, -1, -1, -1, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, -1, -1, -1, -1, 63, -1, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51 };
  private static final int s = 63;
  private final byte[] t;
  private final byte[] u;
  private final byte[] v;
  private final int w;
  private final int x;
  private int y;

  public u()
  {
    this(0);
  }

  public u(int paramInt)
  {
    this(paramInt, a);
  }

  public u(int paramInt, byte[] paramArrayOfByte)
  {
    this(paramInt, paramArrayOfByte, false);
  }

  public u(int paramInt, byte[] paramArrayOfByte, boolean paramBoolean)
  {
  }

  public u(boolean paramBoolean)
  {
    this(76, a, paramBoolean);
  }

  public static boolean a(byte paramByte)
  {
    return (paramByte == 61) || ((paramByte >= 0) && (paramByte < r.length) && (r[paramByte] != -1));
  }

  public static boolean a(String paramString)
  {
    return b(s.f(paramString));
  }

  public static boolean a(byte[] paramArrayOfByte)
  {
    return b(paramArrayOfByte);
  }

  public static byte[] a(BigInteger paramBigInteger)
  {
    if (paramBigInteger == null)
      throw new NullPointerException("encodeInteger called with null parameter");
    return a(b(paramBigInteger), false);
  }

  public static byte[] a(byte[] paramArrayOfByte, boolean paramBoolean)
  {
    return a(paramArrayOfByte, paramBoolean, false);
  }

  public static byte[] a(byte[] paramArrayOfByte, boolean paramBoolean1, boolean paramBoolean2)
  {
    return a(paramArrayOfByte, paramBoolean1, paramBoolean2, 2147483647);
  }

  public static byte[] a(byte[] paramArrayOfByte, boolean paramBoolean1, boolean paramBoolean2, int paramInt)
  {
    if ((paramArrayOfByte == null) || (paramArrayOfByte.length == 0))
      return paramArrayOfByte;
    if (paramBoolean1);
    for (u localu = new u(paramBoolean2); ; localu = new u(0, a, paramBoolean2))
    {
      long l = localu.o(paramArrayOfByte);
      if (l <= paramInt)
        break;
      throw new IllegalArgumentException("Input array too big, the output array would be bigger (" + l + ") than the specified maximum size of " + paramInt);
    }
    return localu.l(paramArrayOfByte);
  }

  public static boolean b(byte[] paramArrayOfByte)
  {
    for (int i = 0; i < paramArrayOfByte.length; i++)
      if ((!a(paramArrayOfByte[i])) && (!c(paramArrayOfByte[i])))
        return false;
    return true;
  }

  public static byte[] b(String paramString)
  {
    return new u().c(paramString);
  }

  static byte[] b(BigInteger paramBigInteger)
  {
    int i = 7 + paramBigInteger.bitLength() >> 3 << 3;
    byte[] arrayOfByte1 = paramBigInteger.toByteArray();
    if ((paramBigInteger.bitLength() % 8 != 0) && (1 + paramBigInteger.bitLength() / 8 == i / 8))
      return arrayOfByte1;
    int j = arrayOfByte1.length;
    int k = paramBigInteger.bitLength() % 8;
    int i1 = 0;
    if (k == 0)
    {
      i1 = 1;
      j--;
    }
    int i2 = i / 8 - j;
    byte[] arrayOfByte2 = new byte[i / 8];
    System.arraycopy(arrayOfByte1, i1, arrayOfByte2, i2, j);
    return arrayOfByte2;
  }

  public static byte[] c(byte[] paramArrayOfByte)
  {
    return a(paramArrayOfByte, false);
  }

  public static String d(byte[] paramArrayOfByte)
  {
    return s.f(a(paramArrayOfByte, false));
  }

  public static byte[] e(byte[] paramArrayOfByte)
  {
    return a(paramArrayOfByte, false, true);
  }

  public static String f(byte[] paramArrayOfByte)
  {
    return s.f(a(paramArrayOfByte, false, true));
  }

  public static byte[] g(byte[] paramArrayOfByte)
  {
    return a(paramArrayOfByte, true);
  }

  public static byte[] h(byte[] paramArrayOfByte)
  {
    return new u().k(paramArrayOfByte);
  }

  public static BigInteger i(byte[] paramArrayOfByte)
  {
    return new BigInteger(1, h(paramArrayOfByte));
  }

  void a(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
  {
    if (this.j);
    while (true)
    {
      return;
      if (paramInt2 < 0)
      {
        this.j = true;
        if ((this.l != 0) || (this.g != 0))
        {
          a(this.x);
          int i5 = this.i;
          switch (this.l)
          {
          default:
          case 1:
          case 2:
          }
          while (true)
          {
            this.k += this.i - i5;
            if ((this.g <= 0) || (this.k <= 0))
              break;
            System.arraycopy(this.v, 0, this.h, this.i, this.v.length);
            this.i += this.v.length;
            return;
            byte[] arrayOfByte9 = this.h;
            int i10 = this.i;
            this.i = (i10 + 1);
            arrayOfByte9[i10] = this.t[(0x3F & this.y >> 2)];
            byte[] arrayOfByte10 = this.h;
            int i11 = this.i;
            this.i = (i11 + 1);
            arrayOfByte10[i11] = this.t[(0x3F & this.y << 4)];
            if (this.t == p)
            {
              byte[] arrayOfByte11 = this.h;
              int i12 = this.i;
              this.i = (i12 + 1);
              arrayOfByte11[i12] = 61;
              byte[] arrayOfByte12 = this.h;
              int i13 = this.i;
              this.i = (i13 + 1);
              arrayOfByte12[i13] = 61;
              continue;
              byte[] arrayOfByte5 = this.h;
              int i6 = this.i;
              this.i = (i6 + 1);
              arrayOfByte5[i6] = this.t[(0x3F & this.y >> 10)];
              byte[] arrayOfByte6 = this.h;
              int i7 = this.i;
              this.i = (i7 + 1);
              arrayOfByte6[i7] = this.t[(0x3F & this.y >> 4)];
              byte[] arrayOfByte7 = this.h;
              int i8 = this.i;
              this.i = (i8 + 1);
              arrayOfByte7[i8] = this.t[(0x3F & this.y << 2)];
              if (this.t == p)
              {
                byte[] arrayOfByte8 = this.h;
                int i9 = this.i;
                this.i = (i9 + 1);
                arrayOfByte8[i9] = 61;
              }
            }
          }
        }
      }
      else
      {
        int i = 0;
        while (i < paramInt2)
        {
          a(this.x);
          this.l = ((1 + this.l) % 3);
          int j = paramInt1 + 1;
          int k = paramArrayOfByte[paramInt1];
          if (k < 0)
            k += 256;
          this.y = (k + (this.y << 8));
          if (this.l == 0)
          {
            byte[] arrayOfByte1 = this.h;
            int i1 = this.i;
            this.i = (i1 + 1);
            arrayOfByte1[i1] = this.t[(0x3F & this.y >> 18)];
            byte[] arrayOfByte2 = this.h;
            int i2 = this.i;
            this.i = (i2 + 1);
            arrayOfByte2[i2] = this.t[(0x3F & this.y >> 12)];
            byte[] arrayOfByte3 = this.h;
            int i3 = this.i;
            this.i = (i3 + 1);
            arrayOfByte3[i3] = this.t[(0x3F & this.y >> 6)];
            byte[] arrayOfByte4 = this.h;
            int i4 = this.i;
            this.i = (i4 + 1);
            arrayOfByte4[i4] = this.t[(0x3F & this.y)];
            this.k = (4 + this.k);
            if ((this.g > 0) && (this.g <= this.k))
            {
              System.arraycopy(this.v, 0, this.h, this.i, this.v.length);
              this.i += this.v.length;
              this.k = 0;
            }
          }
          i++;
          paramInt1 = j;
        }
      }
    }
  }

  public boolean a()
  {
    return this.t == q;
  }

  void b(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
  {
    if (this.j)
      return;
    if (paramInt2 < 0)
      this.j = true;
    int i = 0;
    while (true)
    {
      int i2;
      int i3;
      if (i < paramInt2)
      {
        a(this.w);
        i2 = paramInt1 + 1;
        i3 = paramArrayOfByte[paramInt1];
        if (i3 == 61)
          this.j = true;
      }
      else
      {
        if ((!this.j) || (this.l == 0))
          break;
        a(this.w);
      }
      switch (this.l)
      {
      default:
        return;
      case 2:
        this.y >>= 4;
        byte[] arrayOfByte3 = this.h;
        int i1 = this.i;
        this.i = (i1 + 1);
        arrayOfByte3[i1] = ((byte)(0xFF & this.y));
        return;
        if ((i3 >= 0) && (i3 < r.length))
        {
          int i4 = r[i3];
          if (i4 >= 0)
          {
            this.l = ((1 + this.l) % 4);
            this.y = (i4 + (this.y << 6));
            if (this.l == 0)
            {
              byte[] arrayOfByte4 = this.h;
              int i5 = this.i;
              this.i = (i5 + 1);
              arrayOfByte4[i5] = ((byte)(0xFF & this.y >> 16));
              byte[] arrayOfByte5 = this.h;
              int i6 = this.i;
              this.i = (i6 + 1);
              arrayOfByte5[i6] = ((byte)(0xFF & this.y >> 8));
              byte[] arrayOfByte6 = this.h;
              int i7 = this.i;
              this.i = (i7 + 1);
              arrayOfByte6[i7] = ((byte)(0xFF & this.y));
            }
          }
        }
        i++;
        paramInt1 = i2;
      case 3:
      }
    }
    this.y >>= 2;
    byte[] arrayOfByte1 = this.h;
    int j = this.i;
    this.i = (j + 1);
    arrayOfByte1[j] = ((byte)(0xFF & this.y >> 8));
    byte[] arrayOfByte2 = this.h;
    int k = this.i;
    this.i = (k + 1);
    arrayOfByte2[k] = ((byte)(0xFF & this.y));
  }

  protected boolean b(byte paramByte)
  {
    return (paramByte >= 0) && (paramByte < this.u.length) && (this.u[paramByte] != -1);
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.umeng.message.proguard.u
 * JD-Core Version:    0.6.2
 */