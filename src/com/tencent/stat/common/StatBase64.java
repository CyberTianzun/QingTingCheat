package com.tencent.stat.common;

public class StatBase64
{
  static
  {
    if (!StatBase64.class.desiredAssertionStatus());
    for (boolean bool = true; ; bool = false)
    {
      $assertionsDisabled = bool;
      return;
    }
  }

  public static byte[] decode(byte[] paramArrayOfByte, int paramInt)
  {
    return decode(paramArrayOfByte, 0, paramArrayOfByte.length, paramInt);
  }

  public static byte[] decode(byte[] paramArrayOfByte, int paramInt1, int paramInt2, int paramInt3)
  {
    Decoder localDecoder = new Decoder(paramInt3, new byte[paramInt2 * 3 / 4]);
    if (!localDecoder.process(paramArrayOfByte, paramInt1, paramInt2, true))
      throw new IllegalArgumentException("bad base-64");
    if (localDecoder.op == localDecoder.output.length)
      return localDecoder.output;
    byte[] arrayOfByte = new byte[localDecoder.op];
    System.arraycopy(localDecoder.output, 0, arrayOfByte, 0, localDecoder.op);
    return arrayOfByte;
  }

  public static byte[] encode(byte[] paramArrayOfByte, int paramInt)
  {
    return encode(paramArrayOfByte, 0, paramArrayOfByte.length, paramInt);
  }

  public static byte[] encode(byte[] paramArrayOfByte, int paramInt1, int paramInt2, int paramInt3)
  {
    Encoder localEncoder = new Encoder(paramInt3, null);
    int i = 4 * (paramInt2 / 3);
    int j;
    if (localEncoder.do_padding)
    {
      if (paramInt2 % 3 > 0)
        i += 4;
      if ((localEncoder.do_newline) && (paramInt2 > 0))
      {
        j = 1 + (paramInt2 - 1) / 57;
        if (!localEncoder.do_cr)
          break label167;
      }
    }
    label167: for (int k = 2; ; k = 1)
    {
      i += k * j;
      localEncoder.output = new byte[i];
      localEncoder.process(paramArrayOfByte, paramInt1, paramInt2, true);
      if (($assertionsDisabled) || (localEncoder.op == i))
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
    label173: return localEncoder.output;
  }

  static abstract class Coder
  {
    public int op;
    public byte[] output;
  }

  static class Decoder extends StatBase64.Coder
  {
    private static final int[] DECODE = { -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 62, -1, -1, -1, 63, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, -1, -1, -1, -2, -1, -1, -1, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, -1, -1, -1, -1, -1, -1, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1 };
    private static final int[] DECODE_WEBSAFE = { -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 62, -1, -1, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, -1, -1, -1, -2, -1, -1, -1, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, -1, -1, -1, -1, 63, -1, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1 };
    private final int[] alphabet;
    private int state;
    private int value;

    public Decoder(int paramInt, byte[] paramArrayOfByte)
    {
      this.output = paramArrayOfByte;
      if ((paramInt & 0x8) == 0);
      for (int[] arrayOfInt = DECODE; ; arrayOfInt = DECODE_WEBSAFE)
      {
        this.alphabet = arrayOfInt;
        this.state = 0;
        this.value = 0;
        return;
      }
    }

    public boolean process(byte[] paramArrayOfByte, int paramInt1, int paramInt2, boolean paramBoolean)
    {
      if (this.state == 6)
        return false;
      int i = paramInt2 + paramInt1;
      int j = this.state;
      int k = this.value;
      int m = 0;
      byte[] arrayOfByte = this.output;
      int[] arrayOfInt = this.alphabet;
      int n = paramInt1;
      if (n < i)
        if (j == 0)
        {
          while (n + 4 <= i)
          {
            k = arrayOfInt[(0xFF & paramArrayOfByte[n])] << 18 | arrayOfInt[(0xFF & paramArrayOfByte[(n + 1)])] << 12 | arrayOfInt[(0xFF & paramArrayOfByte[(n + 2)])] << 6 | arrayOfInt[(0xFF & paramArrayOfByte[(n + 3)])];
            if (k < 0)
              break;
            arrayOfByte[(m + 2)] = ((byte)k);
            arrayOfByte[(m + 1)] = ((byte)(k >> 8));
            arrayOfByte[m] = ((byte)(k >> 16));
            m += 3;
            n += 4;
          }
          if (n < i);
        }
      for (int i1 = k; ; i1 = k)
      {
        if (!paramBoolean)
        {
          this.state = j;
          this.value = i1;
          this.op = m;
          return true;
          int i4 = n + 1;
          int i5 = arrayOfInt[(0xFF & paramArrayOfByte[n])];
          switch (j)
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
              int i6 = j;
              while (true)
              {
                j = i6;
                n = i4;
                break;
                if (i5 >= 0)
                {
                  i6 = j + 1;
                  k = i5;
                }
                else
                {
                  if (i5 == -1)
                    break label276;
                  this.state = 6;
                  return false;
                  if (i5 >= 0)
                  {
                    k = i5 | k << 6;
                    i6 = j + 1;
                  }
                  else
                  {
                    if (i5 == -1)
                      break label276;
                    this.state = 6;
                    return false;
                    if (i5 >= 0)
                    {
                      k = i5 | k << 6;
                      i6 = j + 1;
                    }
                    else if (i5 == -2)
                    {
                      int i7 = m + 1;
                      arrayOfByte[m] = ((byte)(k >> 4));
                      i6 = 4;
                      m = i7;
                    }
                    else
                    {
                      if (i5 == -1)
                        break label276;
                      this.state = 6;
                      return false;
                      if (i5 >= 0)
                      {
                        k = i5 | k << 6;
                        arrayOfByte[(m + 2)] = ((byte)k);
                        arrayOfByte[(m + 1)] = ((byte)(k >> 8));
                        arrayOfByte[m] = ((byte)(k >> 16));
                        m += 3;
                        i6 = 0;
                      }
                      else if (i5 == -2)
                      {
                        arrayOfByte[(m + 1)] = ((byte)(k >> 2));
                        arrayOfByte[m] = ((byte)(k >> 10));
                        m += 2;
                        i6 = 5;
                      }
                      else
                      {
                        if (i5 == -1)
                          break label276;
                        this.state = 6;
                        return false;
                        if (i5 != -2)
                          break label559;
                        i6 = j + 1;
                      }
                    }
                  }
                }
              }
            }
            while (i5 == -1);
            this.state = 6;
            return false;
          }
          while (i5 == -1);
          this.state = 6;
          return false;
        }
        switch (j)
        {
        case 0:
        default:
        case 1:
        case 2:
        case 3:
          while (true)
          {
            this.state = j;
            this.op = m;
            return true;
            this.state = 6;
            return false;
            int i3 = m + 1;
            arrayOfByte[m] = ((byte)(i1 >> 4));
            m = i3;
            continue;
            int i2 = m + 1;
            arrayOfByte[m] = ((byte)(i1 >> 10));
            m = i2 + 1;
            arrayOfByte[i2] = ((byte)(i1 >> 2));
          }
        case 4:
        }
        this.state = 6;
        return false;
      }
    }
  }

  static class Encoder extends StatBase64.Coder
  {
    private static final byte[] ENCODE;
    private static final byte[] ENCODE_WEBSAFE;
    private final byte[] alphabet;
    private int count;
    public final boolean do_cr;
    public final boolean do_newline;
    public final boolean do_padding;
    private final byte[] tail;
    int tailLen;

    static
    {
      if (!StatBase64.class.desiredAssertionStatus());
      for (boolean bool = true; ; bool = false)
      {
        $assertionsDisabled = bool;
        ENCODE = new byte[] { 65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90, 97, 98, 99, 100, 101, 102, 103, 104, 105, 106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 43, 47 };
        ENCODE_WEBSAFE = new byte[] { 65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90, 97, 98, 99, 100, 101, 102, 103, 104, 105, 106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 45, 95 };
        return;
      }
    }

    public Encoder(int paramInt, byte[] paramArrayOfByte)
    {
      this.output = paramArrayOfByte;
      boolean bool2;
      boolean bool3;
      label35: label47: byte[] arrayOfByte;
      if ((paramInt & 0x1) == 0)
      {
        bool2 = bool1;
        this.do_padding = bool2;
        if ((paramInt & 0x2) != 0)
          break label106;
        bool3 = bool1;
        this.do_newline = bool3;
        if ((paramInt & 0x4) == 0)
          break label112;
        this.do_cr = bool1;
        if ((paramInt & 0x8) != 0)
          break label117;
        arrayOfByte = ENCODE;
        label64: this.alphabet = arrayOfByte;
        this.tail = new byte[2];
        this.tailLen = 0;
        if (!this.do_newline)
          break label125;
      }
      label106: label112: label117: label125: for (int i = 19; ; i = -1)
      {
        this.count = i;
        return;
        bool2 = false;
        break;
        bool3 = false;
        break label35;
        bool1 = false;
        break label47;
        arrayOfByte = ENCODE_WEBSAFE;
        break label64;
      }
    }

    public boolean process(byte[] paramArrayOfByte, int paramInt1, int paramInt2, boolean paramBoolean)
    {
      byte[] arrayOfByte1 = this.alphabet;
      byte[] arrayOfByte2 = this.output;
      int i = this.count;
      int j = paramInt2 + paramInt1;
      int k = -1;
      int n;
      label59: int i1;
      int i35;
      switch (this.tailLen)
      {
      default:
        n = paramInt1;
        i1 = 0;
        if (k != -1)
        {
          arrayOfByte2[0] = arrayOfByte1[(0x3F & k >> 18)];
          arrayOfByte2[1] = arrayOfByte1[(0x3F & k >> 12)];
          arrayOfByte2[2] = arrayOfByte1[(0x3F & k >> 6)];
          i1 = 4;
          arrayOfByte2[3] = arrayOfByte1[(k & 0x3F)];
          i--;
          if (i == 0)
          {
            if (!this.do_cr)
              break label1246;
            i35 = 5;
            arrayOfByte2[i1] = 13;
          }
        }
        break;
      case 0:
      case 1:
      case 2:
      }
      while (true)
      {
        int i36 = i35 + 1;
        arrayOfByte2[i35] = 10;
        int i2 = 19;
        int i3 = i36;
        while (true)
        {
          label174: int i33;
          if (n + 3 <= j)
          {
            int i32 = (0xFF & paramArrayOfByte[n]) << 16 | (0xFF & paramArrayOfByte[(n + 1)]) << 8 | 0xFF & paramArrayOfByte[(n + 2)];
            arrayOfByte2[i3] = arrayOfByte1[(0x3F & i32 >> 18)];
            arrayOfByte2[(i3 + 1)] = arrayOfByte1[(0x3F & i32 >> 12)];
            arrayOfByte2[(i3 + 2)] = arrayOfByte1[(0x3F & i32 >> 6)];
            arrayOfByte2[(i3 + 3)] = arrayOfByte1[(i32 & 0x3F)];
            n += 3;
            i1 = i3 + 4;
            i = i2 - 1;
            if (i != 0)
              break label1235;
            if (!this.do_cr)
              break label1228;
            i33 = i1 + 1;
            arrayOfByte2[i1] = 13;
          }
          while (true)
          {
            int i34 = i33 + 1;
            arrayOfByte2[i33] = 10;
            i2 = 19;
            i3 = i34;
            break label174;
            n = paramInt1;
            break label59;
            if (paramInt1 + 2 > j)
              break;
            int i37 = (0xFF & this.tail[0]) << 16;
            int i38 = paramInt1 + 1;
            int i39 = i37 | (0xFF & paramArrayOfByte[paramInt1]) << 8;
            int i40 = i38 + 1;
            k = i39 | 0xFF & paramArrayOfByte[i38];
            this.tailLen = 0;
            n = i40;
            break label59;
            if (paramInt1 + 1 > j)
              break;
            int m = (0xFF & this.tail[0]) << 16 | (0xFF & this.tail[1]) << 8;
            n = paramInt1 + 1;
            k = m | 0xFF & paramArrayOfByte[paramInt1];
            this.tailLen = 0;
            break label59;
            int i10;
            int i9;
            label770: int i13;
            label811: int i17;
            int i18;
            if (paramBoolean)
            {
              if (n - this.tailLen == j - 1)
              {
                int i25;
                int i23;
                int i24;
                if (this.tailLen > 0)
                {
                  byte[] arrayOfByte8 = this.tail;
                  i25 = 1;
                  i23 = arrayOfByte8[0];
                  i24 = n;
                }
                while (true)
                {
                  int i26 = (i23 & 0xFF) << 4;
                  this.tailLen -= i25;
                  int i27 = i3 + 1;
                  arrayOfByte2[i3] = arrayOfByte1[(0x3F & i26 >> 6)];
                  int i28 = i27 + 1;
                  arrayOfByte2[i27] = arrayOfByte1[(i26 & 0x3F)];
                  if (this.do_padding)
                  {
                    int i31 = i28 + 1;
                    arrayOfByte2[i28] = 61;
                    i28 = i31 + 1;
                    arrayOfByte2[i31] = 61;
                  }
                  if (this.do_newline)
                  {
                    if (this.do_cr)
                    {
                      int i30 = i28 + 1;
                      arrayOfByte2[i28] = 13;
                      i28 = i30;
                    }
                    int i29 = i28 + 1;
                    arrayOfByte2[i28] = 10;
                    i28 = i29;
                  }
                  n = i24;
                  i3 = i28;
                  if (($assertionsDisabled) || (this.tailLen == 0))
                    break;
                  throw new AssertionError();
                  int i22 = n + 1;
                  i23 = paramArrayOfByte[n];
                  i24 = i22;
                  i25 = 0;
                }
              }
              if (n - this.tailLen == j - 2)
                if (this.tailLen > 1)
                {
                  byte[] arrayOfByte7 = this.tail;
                  i10 = 1;
                  i9 = arrayOfByte7[0];
                  int i11 = (i9 & 0xFF) << 10;
                  if (this.tailLen <= 0)
                    break label995;
                  byte[] arrayOfByte6 = this.tail;
                  int i21 = i10 + 1;
                  i13 = arrayOfByte6[i10];
                  i10 = i21;
                  int i14 = i11 | (i13 & 0xFF) << 2;
                  this.tailLen -= i10;
                  int i15 = i3 + 1;
                  arrayOfByte2[i3] = arrayOfByte1[(0x3F & i14 >> 12)];
                  int i16 = i15 + 1;
                  arrayOfByte2[i15] = arrayOfByte1[(0x3F & i14 >> 6)];
                  i17 = i16 + 1;
                  arrayOfByte2[i16] = arrayOfByte1[(i14 & 0x3F)];
                  if (!this.do_padding)
                    break label1221;
                  i18 = i17 + 1;
                  arrayOfByte2[i17] = 61;
                }
            }
            while (true)
            {
              if (this.do_newline)
              {
                if (this.do_cr)
                {
                  int i20 = i18 + 1;
                  arrayOfByte2[i18] = 13;
                  i18 = i20;
                }
                int i19 = i18 + 1;
                arrayOfByte2[i18] = 10;
                i18 = i19;
              }
              i3 = i18;
              break;
              int i8 = n + 1;
              i9 = paramArrayOfByte[n];
              n = i8;
              i10 = 0;
              break label770;
              label995: int i12 = n + 1;
              i13 = paramArrayOfByte[n];
              n = i12;
              break label811;
              if ((!this.do_newline) || (i3 <= 0) || (i2 == 19))
                break;
              int i7;
              if (this.do_cr)
              {
                i7 = i3 + 1;
                arrayOfByte2[i3] = 13;
              }
              while (true)
              {
                i3 = i7 + 1;
                arrayOfByte2[i7] = 10;
                break;
                if ((!$assertionsDisabled) && (n != j))
                {
                  throw new AssertionError();
                  if (n != j - 1)
                    break label1142;
                  byte[] arrayOfByte5 = this.tail;
                  int i6 = this.tailLen;
                  this.tailLen = (i6 + 1);
                  arrayOfByte5[i6] = paramArrayOfByte[n];
                }
                while (true)
                {
                  this.op = i3;
                  this.count = i2;
                  return true;
                  label1142: if (n == j - 2)
                  {
                    byte[] arrayOfByte3 = this.tail;
                    int i4 = this.tailLen;
                    this.tailLen = (i4 + 1);
                    arrayOfByte3[i4] = paramArrayOfByte[n];
                    byte[] arrayOfByte4 = this.tail;
                    int i5 = this.tailLen;
                    this.tailLen = (i5 + 1);
                    arrayOfByte4[i5] = paramArrayOfByte[(n + 1)];
                  }
                }
                i7 = i3;
              }
              label1221: i18 = i17;
            }
            label1228: i33 = i1;
          }
          label1235: i2 = i;
          i3 = i1;
        }
        label1246: i35 = i1;
      }
    }
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.tencent.stat.common.StatBase64
 * JD-Core Version:    0.6.2
 */