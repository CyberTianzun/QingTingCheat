package com.sina.weibo.sdk.utils;

public final class Base64
{
  private static char[] alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/=".toCharArray();
  private static byte[] codes = new byte[256];

  static
  {
    int i = 0;
    int j;
    label28: int k;
    if (i >= 256)
    {
      j = 65;
      if (j <= 90)
        break label81;
      k = 97;
      label37: if (k <= 122)
        break label97;
    }
    for (int m = 48; ; m++)
    {
      if (m > 57)
      {
        codes[43] = 62;
        codes[47] = 63;
        return;
        codes[i] = -1;
        i++;
        break;
        label81: codes[j] = ((byte)(j - 65));
        j++;
        break label28;
        label97: codes[k] = ((byte)(-97 + (k + 26)));
        k++;
        break label37;
      }
      codes[m] = ((byte)(-48 + (m + 52)));
    }
  }

  public static byte[] decode(byte[] paramArrayOfByte)
  {
    int i = 3 * ((3 + paramArrayOfByte.length) / 4);
    if ((paramArrayOfByte.length > 0) && (paramArrayOfByte[(-1 + paramArrayOfByte.length)] == 61))
      i--;
    if ((paramArrayOfByte.length > 1) && (paramArrayOfByte[(-2 + paramArrayOfByte.length)] == 61))
      i--;
    byte[] arrayOfByte = new byte[i];
    int j = 0;
    int k = 0;
    int m = 0;
    for (int n = 0; ; n++)
    {
      if (n >= paramArrayOfByte.length)
      {
        if (m == arrayOfByte.length)
          break;
        throw new RuntimeException("miscalculated data length!");
      }
      int i1 = codes[(0xFF & paramArrayOfByte[n])];
      if (i1 >= 0)
      {
        int i2 = k << 6;
        j += 6;
        k = i2 | i1;
        if (j >= 8)
        {
          j -= 8;
          int i3 = m + 1;
          arrayOfByte[m] = ((byte)(0xFF & k >> j));
          m = i3;
        }
      }
    }
    return arrayOfByte;
  }

  public static char[] encode(byte[] paramArrayOfByte)
  {
    char[] arrayOfChar1 = new char[4 * ((2 + paramArrayOfByte.length) / 3)];
    int i = 0;
    int j = 0;
    if (i >= paramArrayOfByte.length)
      return arrayOfChar1;
    int k = (0xFF & paramArrayOfByte[i]) << 8;
    int m = i + 1;
    int n = paramArrayOfByte.length;
    int i1 = 0;
    if (m < n)
    {
      k |= 0xFF & paramArrayOfByte[(i + 1)];
      i1 = 1;
    }
    int i2 = k << 8;
    int i3 = i + 2;
    int i4 = paramArrayOfByte.length;
    int i5 = 0;
    if (i3 < i4)
    {
      i2 |= 0xFF & paramArrayOfByte[(i + 2)];
      i5 = 1;
    }
    int i6 = j + 3;
    char[] arrayOfChar2 = alphabet;
    int i7;
    label136: int i8;
    int i9;
    char[] arrayOfChar3;
    if (i5 != 0)
    {
      i7 = i2 & 0x3F;
      arrayOfChar1[i6] = arrayOfChar2[i7];
      i8 = i2 >> 6;
      i9 = j + 2;
      arrayOfChar3 = alphabet;
      if (i1 == 0)
        break label241;
    }
    label241: for (int i10 = i8 & 0x3F; ; i10 = 64)
    {
      arrayOfChar1[i9] = arrayOfChar3[i10];
      int i11 = i8 >> 6;
      arrayOfChar1[(j + 1)] = alphabet[(i11 & 0x3F)];
      int i12 = i11 >> 6;
      arrayOfChar1[(j + 0)] = alphabet[(i12 & 0x3F)];
      i += 3;
      j += 4;
      break;
      i7 = 64;
      break label136;
    }
  }

  public static byte[] encodebyte(byte[] paramArrayOfByte)
  {
    byte[] arrayOfByte = new byte[4 * ((2 + paramArrayOfByte.length) / 3)];
    int i = 0;
    int j = 0;
    if (i >= paramArrayOfByte.length)
      return arrayOfByte;
    int k = (0xFF & paramArrayOfByte[i]) << 8;
    int m = i + 1;
    int n = paramArrayOfByte.length;
    int i1 = 0;
    if (m < n)
    {
      k |= 0xFF & paramArrayOfByte[(i + 1)];
      i1 = 1;
    }
    int i2 = k << 8;
    int i3 = i + 2;
    int i4 = paramArrayOfByte.length;
    int i5 = 0;
    if (i3 < i4)
    {
      i2 |= 0xFF & paramArrayOfByte[(i + 2)];
      i5 = 1;
    }
    int i6 = j + 3;
    char[] arrayOfChar1 = alphabet;
    int i7;
    label136: int i8;
    int i9;
    char[] arrayOfChar2;
    if (i5 != 0)
    {
      i7 = i2 & 0x3F;
      arrayOfByte[i6] = ((byte)arrayOfChar1[i7]);
      i8 = i2 >> 6;
      i9 = j + 2;
      arrayOfChar2 = alphabet;
      if (i1 == 0)
        break label245;
    }
    label245: for (int i10 = i8 & 0x3F; ; i10 = 64)
    {
      arrayOfByte[i9] = ((byte)arrayOfChar2[i10]);
      int i11 = i8 >> 6;
      arrayOfByte[(j + 1)] = ((byte)alphabet[(i11 & 0x3F)]);
      int i12 = i11 >> 6;
      arrayOfByte[(j + 0)] = ((byte)alphabet[(i12 & 0x3F)]);
      i += 3;
      j += 4;
      break;
      i7 = 64;
      break label136;
    }
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.sina.weibo.sdk.utils.Base64
 * JD-Core Version:    0.6.2
 */