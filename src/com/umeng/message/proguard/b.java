package com.umeng.message.proguard;

import java.io.UnsupportedEncodingException;

public class b
{
  public static final int a = 0;
  public static final int b = 1;
  public static final int c = 2;
  public static final int d = 4;
  public static final int e = 8;
  public static final int f = 16;

  static
  {
    if (!b.class.desiredAssertionStatus());
    for (boolean bool = true; ; bool = false)
    {
      g = bool;
      return;
    }
  }

  public static byte[] a(String paramString, int paramInt)
  {
    return a(paramString.getBytes(), paramInt);
  }

  public static byte[] a(byte[] paramArrayOfByte, int paramInt)
  {
    return a(paramArrayOfByte, 0, paramArrayOfByte.length, paramInt);
  }

  public static byte[] a(byte[] paramArrayOfByte, int paramInt1, int paramInt2, int paramInt3)
  {
    b localb = new b(paramInt3, new byte[paramInt2 * 3 / 4]);
    if (!localb.a(paramArrayOfByte, paramInt1, paramInt2, true))
      throw new IllegalArgumentException("bad base-64");
    if (localb.b == localb.a.length)
      return localb.a;
    byte[] arrayOfByte = new byte[localb.b];
    System.arraycopy(localb.a, 0, arrayOfByte, 0, localb.b);
    return arrayOfByte;
  }

  public static String b(byte[] paramArrayOfByte, int paramInt)
  {
    try
    {
      String str = new String(c(paramArrayOfByte, paramInt), "US-ASCII");
      return str;
    }
    catch (UnsupportedEncodingException localUnsupportedEncodingException)
    {
      throw new AssertionError(localUnsupportedEncodingException);
    }
  }

  public static String b(byte[] paramArrayOfByte, int paramInt1, int paramInt2, int paramInt3)
  {
    try
    {
      String str = new String(c(paramArrayOfByte, paramInt1, paramInt2, paramInt3), "US-ASCII");
      return str;
    }
    catch (UnsupportedEncodingException localUnsupportedEncodingException)
    {
      throw new AssertionError(localUnsupportedEncodingException);
    }
  }

  public static byte[] c(byte[] paramArrayOfByte, int paramInt)
  {
    return c(paramArrayOfByte, 0, paramArrayOfByte.length, paramInt);
  }

  public static byte[] c(byte[] paramArrayOfByte, int paramInt1, int paramInt2, int paramInt3)
  {
    c localc = new c(paramInt3, null);
    int i = 4 * (paramInt2 / 3);
    int j;
    if (localc.e)
    {
      if (paramInt2 % 3 > 0)
        i += 4;
      if ((localc.f) && (paramInt2 > 0))
      {
        j = 1 + (paramInt2 - 1) / 57;
        if (!localc.g)
          break label167;
      }
    }
    label167: for (int k = 2; ; k = 1)
    {
      i += k * j;
      localc.a = new byte[i];
      localc.a(paramArrayOfByte, paramInt1, paramInt2, true);
      if ((g) || (localc.b == i))
        break label173;
      throw new AssertionError();
      switch (paramInt2 % 3)
      {
      case 0:
      default:
        break;
      case 1:
        i += 2;
        break;
      case 2:
        i += 3;
        break;
      }
    }
    label173: return localc.a;
  }

  static abstract class a
  {
    public byte[] a;
    public int b;

    public abstract int a(int paramInt);

    public abstract boolean a(byte[] paramArrayOfByte, int paramInt1, int paramInt2, boolean paramBoolean);
  }

  static class b extends b.a
  {
    private static final int[] c = { -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 62, -1, -1, -1, 63, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, -1, -1, -1, -2, -1, -1, -1, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, -1, -1, -1, -1, -1, -1, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1 };
    private static final int[] d = { -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 62, -1, -1, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, -1, -1, -1, -2, -1, -1, -1, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, -1, -1, -1, -1, 63, -1, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1 };
    private static final int e = -1;
    private static final int f = -2;
    private int g;
    private int h;
    private final int[] i;

    public b(int paramInt, byte[] paramArrayOfByte)
    {
      this.a = paramArrayOfByte;
      if ((paramInt & 0x8) == 0);
      for (int[] arrayOfInt = c; ; arrayOfInt = d)
      {
        this.i = arrayOfInt;
        this.g = 0;
        this.h = 0;
        return;
      }
    }

    public int a(int paramInt)
    {
      return 10 + paramInt * 3 / 4;
    }

    public boolean a(byte[] paramArrayOfByte, int paramInt1, int paramInt2, boolean paramBoolean)
    {
      if (this.g == 6)
        return false;
      int j = paramInt2 + paramInt1;
      int k = this.g;
      int m = this.h;
      int n = 0;
      byte[] arrayOfByte = this.a;
      int[] arrayOfInt = this.i;
      int i1 = paramInt1;
      if (i1 < j)
        if (k == 0)
        {
          while (i1 + 4 <= j)
          {
            m = arrayOfInt[(0xFF & paramArrayOfByte[i1])] << 18 | arrayOfInt[(0xFF & paramArrayOfByte[(i1 + 1)])] << 12 | arrayOfInt[(0xFF & paramArrayOfByte[(i1 + 2)])] << 6 | arrayOfInt[(0xFF & paramArrayOfByte[(i1 + 3)])];
            if (m < 0)
              break;
            arrayOfByte[(n + 2)] = ((byte)m);
            arrayOfByte[(n + 1)] = ((byte)(m >> 8));
            arrayOfByte[n] = ((byte)(m >> 16));
            n += 3;
            i1 += 4;
          }
          if (i1 < j);
        }
      for (int i2 = m; ; i2 = m)
      {
        if (!paramBoolean)
        {
          this.g = k;
          this.h = i2;
          this.b = n;
          return true;
          int i5 = i1 + 1;
          int i6 = arrayOfInt[(0xFF & paramArrayOfByte[i1])];
          switch (k)
          {
          default:
          case 0:
          case 1:
          case 2:
          case 3:
          case 4:
          case 5:
          }
          label276: label559: 
          do
          {
            do
            {
              int i7 = k;
              while (true)
              {
                k = i7;
                i1 = i5;
                break;
                if (i6 >= 0)
                {
                  i7 = k + 1;
                  m = i6;
                }
                else
                {
                  if (i6 == -1)
                    break label276;
                  this.g = 6;
                  return false;
                  if (i6 >= 0)
                  {
                    m = i6 | m << 6;
                    i7 = k + 1;
                  }
                  else
                  {
                    if (i6 == -1)
                      break label276;
                    this.g = 6;
                    return false;
                    if (i6 >= 0)
                    {
                      m = i6 | m << 6;
                      i7 = k + 1;
                    }
                    else if (i6 == -2)
                    {
                      int i8 = n + 1;
                      arrayOfByte[n] = ((byte)(m >> 4));
                      i7 = 4;
                      n = i8;
                    }
                    else
                    {
                      if (i6 == -1)
                        break label276;
                      this.g = 6;
                      return false;
                      if (i6 >= 0)
                      {
                        m = i6 | m << 6;
                        arrayOfByte[(n + 2)] = ((byte)m);
                        arrayOfByte[(n + 1)] = ((byte)(m >> 8));
                        arrayOfByte[n] = ((byte)(m >> 16));
                        n += 3;
                        i7 = 0;
                      }
                      else if (i6 == -2)
                      {
                        arrayOfByte[(n + 1)] = ((byte)(m >> 2));
                        arrayOfByte[n] = ((byte)(m >> 10));
                        n += 2;
                        i7 = 5;
                      }
                      else
                      {
                        if (i6 == -1)
                          break label276;
                        this.g = 6;
                        return false;
                        if (i6 != -2)
                          break label559;
                        i7 = k + 1;
                      }
                    }
                  }
                }
              }
            }
            while (i6 == -1);
            this.g = 6;
            return false;
          }
          while (i6 == -1);
          this.g = 6;
          return false;
        }
        switch (k)
        {
        case 0:
        default:
        case 1:
        case 2:
        case 3:
          while (true)
          {
            this.g = k;
            this.b = n;
            return true;
            this.g = 6;
            return false;
            int i4 = n + 1;
            arrayOfByte[n] = ((byte)(i2 >> 4));
            n = i4;
            continue;
            int i3 = n + 1;
            arrayOfByte[n] = ((byte)(i2 >> 10));
            n = i3 + 1;
            arrayOfByte[i3] = ((byte)(i2 >> 2));
          }
        case 4:
        }
        this.g = 6;
        return false;
      }
    }
  }

  static class c extends b.a
  {
    public static final int c = 19;
    private static final byte[] i;
    private static final byte[] j;
    int d;
    public final boolean e;
    public final boolean f;
    public final boolean g;
    private final byte[] k;
    private int l;
    private final byte[] m;

    static
    {
      if (!b.class.desiredAssertionStatus());
      for (boolean bool = true; ; bool = false)
      {
        h = bool;
        i = new byte[] { 65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90, 97, 98, 99, 100, 101, 102, 103, 104, 105, 106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 43, 47 };
        j = new byte[] { 65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90, 97, 98, 99, 100, 101, 102, 103, 104, 105, 106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 45, 95 };
        return;
      }
    }

    public c(int paramInt, byte[] paramArrayOfByte)
    {
      this.a = paramArrayOfByte;
      boolean bool2;
      boolean bool3;
      label35: label47: byte[] arrayOfByte;
      if ((paramInt & 0x1) == 0)
      {
        bool2 = bool1;
        this.e = bool2;
        if ((paramInt & 0x2) != 0)
          break label106;
        bool3 = bool1;
        this.f = bool3;
        if ((paramInt & 0x4) == 0)
          break label112;
        this.g = bool1;
        if ((paramInt & 0x8) != 0)
          break label117;
        arrayOfByte = i;
        label64: this.m = arrayOfByte;
        this.k = new byte[2];
        this.d = 0;
        if (!this.f)
          break label125;
      }
      label106: label112: label117: label125: for (int n = 19; ; n = -1)
      {
        this.l = n;
        return;
        bool2 = false;
        break;
        bool3 = false;
        break label35;
        bool1 = false;
        break label47;
        arrayOfByte = j;
        break label64;
      }
    }

    public int a(int paramInt)
    {
      return 10 + paramInt * 8 / 5;
    }

    public boolean a(byte[] paramArrayOfByte, int paramInt1, int paramInt2, boolean paramBoolean)
    {
      byte[] arrayOfByte1 = this.m;
      byte[] arrayOfByte2 = this.a;
      int n = this.l;
      int i1 = paramInt2 + paramInt1;
      int i2 = -1;
      int i4;
      label59: int i5;
      int i39;
      switch (this.d)
      {
      default:
        i4 = paramInt1;
        i5 = 0;
        if (i2 != -1)
        {
          arrayOfByte2[0] = arrayOfByte1[(0x3F & i2 >> 18)];
          arrayOfByte2[1] = arrayOfByte1[(0x3F & i2 >> 12)];
          arrayOfByte2[2] = arrayOfByte1[(0x3F & i2 >> 6)];
          i5 = 4;
          arrayOfByte2[3] = arrayOfByte1[(i2 & 0x3F)];
          n--;
          if (n == 0)
          {
            if (!this.g)
              break label1246;
            i39 = 5;
            arrayOfByte2[i5] = 13;
          }
        }
        break;
      case 0:
      case 1:
      case 2:
      }
      while (true)
      {
        int i40 = i39 + 1;
        arrayOfByte2[i39] = 10;
        int i6 = 19;
        int i7 = i40;
        while (true)
        {
          label174: int i37;
          if (i4 + 3 <= i1)
          {
            int i36 = (0xFF & paramArrayOfByte[i4]) << 16 | (0xFF & paramArrayOfByte[(i4 + 1)]) << 8 | 0xFF & paramArrayOfByte[(i4 + 2)];
            arrayOfByte2[i7] = arrayOfByte1[(0x3F & i36 >> 18)];
            arrayOfByte2[(i7 + 1)] = arrayOfByte1[(0x3F & i36 >> 12)];
            arrayOfByte2[(i7 + 2)] = arrayOfByte1[(0x3F & i36 >> 6)];
            arrayOfByte2[(i7 + 3)] = arrayOfByte1[(i36 & 0x3F)];
            i4 += 3;
            i5 = i7 + 4;
            n = i6 - 1;
            if (n != 0)
              break label1235;
            if (!this.g)
              break label1228;
            i37 = i5 + 1;
            arrayOfByte2[i5] = 13;
          }
          while (true)
          {
            int i38 = i37 + 1;
            arrayOfByte2[i37] = 10;
            i6 = 19;
            i7 = i38;
            break label174;
            i4 = paramInt1;
            break label59;
            if (paramInt1 + 2 > i1)
              break;
            int i41 = (0xFF & this.k[0]) << 16;
            int i42 = paramInt1 + 1;
            int i43 = i41 | (0xFF & paramArrayOfByte[paramInt1]) << 8;
            int i44 = i42 + 1;
            i2 = i43 | 0xFF & paramArrayOfByte[i42];
            this.d = 0;
            i4 = i44;
            break label59;
            if (paramInt1 + 1 > i1)
              break;
            int i3 = (0xFF & this.k[0]) << 16 | (0xFF & this.k[1]) << 8;
            i4 = paramInt1 + 1;
            i2 = i3 | 0xFF & paramArrayOfByte[paramInt1];
            this.d = 0;
            break label59;
            int i14;
            int i13;
            label770: int i17;
            label811: int i21;
            int i22;
            if (paramBoolean)
            {
              if (i4 - this.d == i1 - 1)
              {
                int i29;
                int i27;
                int i28;
                if (this.d > 0)
                {
                  byte[] arrayOfByte8 = this.k;
                  i29 = 1;
                  i27 = arrayOfByte8[0];
                  i28 = i4;
                }
                while (true)
                {
                  int i30 = (i27 & 0xFF) << 4;
                  this.d -= i29;
                  int i31 = i7 + 1;
                  arrayOfByte2[i7] = arrayOfByte1[(0x3F & i30 >> 6)];
                  int i32 = i31 + 1;
                  arrayOfByte2[i31] = arrayOfByte1[(i30 & 0x3F)];
                  if (this.e)
                  {
                    int i35 = i32 + 1;
                    arrayOfByte2[i32] = 61;
                    i32 = i35 + 1;
                    arrayOfByte2[i35] = 61;
                  }
                  if (this.f)
                  {
                    if (this.g)
                    {
                      int i34 = i32 + 1;
                      arrayOfByte2[i32] = 13;
                      i32 = i34;
                    }
                    int i33 = i32 + 1;
                    arrayOfByte2[i32] = 10;
                    i32 = i33;
                  }
                  i4 = i28;
                  i7 = i32;
                  if ((h) || (this.d == 0))
                    break;
                  throw new AssertionError();
                  int i26 = i4 + 1;
                  i27 = paramArrayOfByte[i4];
                  i28 = i26;
                  i29 = 0;
                }
              }
              if (i4 - this.d == i1 - 2)
                if (this.d > 1)
                {
                  byte[] arrayOfByte7 = this.k;
                  i14 = 1;
                  i13 = arrayOfByte7[0];
                  int i15 = (i13 & 0xFF) << 10;
                  if (this.d <= 0)
                    break label995;
                  byte[] arrayOfByte6 = this.k;
                  int i25 = i14 + 1;
                  i17 = arrayOfByte6[i14];
                  i14 = i25;
                  int i18 = i15 | (i17 & 0xFF) << 2;
                  this.d -= i14;
                  int i19 = i7 + 1;
                  arrayOfByte2[i7] = arrayOfByte1[(0x3F & i18 >> 12)];
                  int i20 = i19 + 1;
                  arrayOfByte2[i19] = arrayOfByte1[(0x3F & i18 >> 6)];
                  i21 = i20 + 1;
                  arrayOfByte2[i20] = arrayOfByte1[(i18 & 0x3F)];
                  if (!this.e)
                    break label1221;
                  i22 = i21 + 1;
                  arrayOfByte2[i21] = 61;
                }
            }
            while (true)
            {
              if (this.f)
              {
                if (this.g)
                {
                  int i24 = i22 + 1;
                  arrayOfByte2[i22] = 13;
                  i22 = i24;
                }
                int i23 = i22 + 1;
                arrayOfByte2[i22] = 10;
                i22 = i23;
              }
              i7 = i22;
              break;
              int i12 = i4 + 1;
              i13 = paramArrayOfByte[i4];
              i4 = i12;
              i14 = 0;
              break label770;
              label995: int i16 = i4 + 1;
              i17 = paramArrayOfByte[i4];
              i4 = i16;
              break label811;
              if ((!this.f) || (i7 <= 0) || (i6 == 19))
                break;
              int i11;
              if (this.g)
              {
                i11 = i7 + 1;
                arrayOfByte2[i7] = 13;
              }
              while (true)
              {
                i7 = i11 + 1;
                arrayOfByte2[i11] = 10;
                break;
                if ((!h) && (i4 != i1))
                {
                  throw new AssertionError();
                  if (i4 != i1 - 1)
                    break label1142;
                  byte[] arrayOfByte5 = this.k;
                  int i10 = this.d;
                  this.d = (i10 + 1);
                  arrayOfByte5[i10] = paramArrayOfByte[i4];
                }
                while (true)
                {
                  this.b = i7;
                  this.l = i6;
                  return true;
                  label1142: if (i4 == i1 - 2)
                  {
                    byte[] arrayOfByte3 = this.k;
                    int i8 = this.d;
                    this.d = (i8 + 1);
                    arrayOfByte3[i8] = paramArrayOfByte[i4];
                    byte[] arrayOfByte4 = this.k;
                    int i9 = this.d;
                    this.d = (i9 + 1);
                    arrayOfByte4[i9] = paramArrayOfByte[(i4 + 1)];
                  }
                }
                i11 = i7;
              }
              label1221: i22 = i21;
            }
            label1228: i37 = i5;
          }
          label1235: i6 = n;
          i7 = i5;
        }
        label1246: i39 = i5;
      }
    }
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.umeng.message.proguard.b
 * JD-Core Version:    0.6.2
 */