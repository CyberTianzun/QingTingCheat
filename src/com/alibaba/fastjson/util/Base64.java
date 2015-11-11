package com.alibaba.fastjson.util;

import java.util.Arrays;

public class Base64
{
  public static final char[] CA = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/".toCharArray();
  public static final int[] IA = new int[256];

  static
  {
    Arrays.fill(IA, -1);
    int i = 0;
    int j = CA.length;
    while (i < j)
    {
      IA[CA[i]] = i;
      i++;
    }
    IA[61] = 0;
  }

  public static final byte[] decodeFast(String paramString)
  {
    int i = paramString.length();
    if (i == 0)
      return new byte[0];
    int j = 0;
    int k = i - 1;
    while ((j < k) && (IA[(0xFF & paramString.charAt(j))] < 0))
      j++;
    while ((k > 0) && (IA[(0xFF & paramString.charAt(k))] < 0))
      k--;
    int m;
    int i24;
    label128: int i1;
    label134: int i2;
    byte[] arrayOfByte;
    int i5;
    int i6;
    label172: int i18;
    int i22;
    int i23;
    if (paramString.charAt(k) == '=')
      if (paramString.charAt(k - 1) == '=')
      {
        m = 2;
        int n = 1 + (k - j);
        if (i <= 76)
          break label386;
        if (paramString.charAt(76) != '\r')
          break label380;
        i24 = n / 78;
        i1 = i24 << 1;
        i2 = (6 * (n - i1) >> 3) - m;
        arrayOfByte = new byte[i2];
        int i3 = 0;
        int i4 = 3 * (i2 / 3);
        i5 = 0;
        i6 = j;
        if (i5 >= i4)
          break label392;
        int[] arrayOfInt2 = IA;
        int i12 = i6 + 1;
        int i13 = arrayOfInt2[paramString.charAt(i6)] << 18;
        int[] arrayOfInt3 = IA;
        int i14 = i12 + 1;
        int i15 = i13 | arrayOfInt3[paramString.charAt(i12)] << 12;
        int[] arrayOfInt4 = IA;
        int i16 = i14 + 1;
        int i17 = i15 | arrayOfInt4[paramString.charAt(i14)] << 6;
        int[] arrayOfInt5 = IA;
        i18 = i16 + 1;
        int i19 = i17 | arrayOfInt5[paramString.charAt(i16)];
        int i20 = i5 + 1;
        arrayOfByte[i5] = ((byte)(i19 >> 16));
        int i21 = i20 + 1;
        arrayOfByte[i20] = ((byte)(i19 >> 8));
        i22 = i21 + 1;
        arrayOfByte[i21] = ((byte)i19);
        if (i1 <= 0)
          break label505;
        i3++;
        if (i3 != 19)
          break label505;
        i23 = i18 + 2;
        i3 = 0;
      }
    while (true)
    {
      i5 = i22;
      i6 = i23;
      break label172;
      m = 1;
      break;
      m = 0;
      break;
      label380: i24 = 0;
      break label128;
      label386: i1 = 0;
      break label134;
      label392: if (i5 < i2)
      {
        int i7 = 0;
        int i8 = 0;
        while (i6 <= k - m)
        {
          int[] arrayOfInt1 = IA;
          int i11 = i6 + 1;
          i7 |= arrayOfInt1[paramString.charAt(i6)] << 18 - i8 * 6;
          i8++;
          i6 = i11;
        }
        int i9 = 16;
        while (i5 < i2)
        {
          int i10 = i5 + 1;
          arrayOfByte[i5] = ((byte)(i7 >> i9));
          i9 -= 8;
          i5 = i10;
        }
      }
      return arrayOfByte;
      label505: i23 = i18;
    }
  }

  public static final byte[] decodeFast(String paramString, int paramInt1, int paramInt2)
  {
    if (paramInt2 == 0)
      return new byte[0];
    int i = paramInt1;
    int j = -1 + (paramInt1 + paramInt2);
    while ((i < j) && (IA[paramString.charAt(i)] < 0))
      i++;
    while ((j > 0) && (IA[paramString.charAt(j)] < 0))
      j--;
    int k;
    int i23;
    label124: int n;
    label130: int i1;
    byte[] arrayOfByte;
    int i4;
    int i5;
    label168: int i17;
    int i21;
    int i22;
    if (paramString.charAt(j) == '=')
      if (paramString.charAt(j - 1) == '=')
      {
        k = 2;
        int m = 1 + (j - i);
        if (paramInt2 <= 76)
          break label382;
        if (paramString.charAt(76) != '\r')
          break label376;
        i23 = m / 78;
        n = i23 << 1;
        i1 = (6 * (m - n) >> 3) - k;
        arrayOfByte = new byte[i1];
        int i2 = 0;
        int i3 = 3 * (i1 / 3);
        i4 = 0;
        i5 = i;
        if (i4 >= i3)
          break label388;
        int[] arrayOfInt2 = IA;
        int i11 = i5 + 1;
        int i12 = arrayOfInt2[paramString.charAt(i5)] << 18;
        int[] arrayOfInt3 = IA;
        int i13 = i11 + 1;
        int i14 = i12 | arrayOfInt3[paramString.charAt(i11)] << 12;
        int[] arrayOfInt4 = IA;
        int i15 = i13 + 1;
        int i16 = i14 | arrayOfInt4[paramString.charAt(i13)] << 6;
        int[] arrayOfInt5 = IA;
        i17 = i15 + 1;
        int i18 = i16 | arrayOfInt5[paramString.charAt(i15)];
        int i19 = i4 + 1;
        arrayOfByte[i4] = ((byte)(i18 >> 16));
        int i20 = i19 + 1;
        arrayOfByte[i19] = ((byte)(i18 >> 8));
        i21 = i20 + 1;
        arrayOfByte[i20] = ((byte)i18);
        if (n <= 0)
          break label502;
        i2++;
        if (i2 != 19)
          break label502;
        i22 = i17 + 2;
        i2 = 0;
      }
    while (true)
    {
      i4 = i21;
      i5 = i22;
      break label168;
      k = 1;
      break;
      k = 0;
      break;
      label376: i23 = 0;
      break label124;
      label382: n = 0;
      break label130;
      label388: if (i4 < i1)
      {
        int i6 = 0;
        int i7 = 0;
        while (i5 <= j - k)
        {
          int[] arrayOfInt1 = IA;
          int i10 = i5 + 1;
          i6 |= arrayOfInt1[paramString.charAt(i5)] << 18 - i7 * 6;
          i7++;
          i5 = i10;
        }
        int i8 = 16;
        while (i4 < i1)
        {
          int i9 = i4 + 1;
          arrayOfByte[i4] = ((byte)(i6 >> i8));
          i8 -= 8;
          i4 = i9;
        }
      }
      return arrayOfByte;
      label502: i22 = i17;
    }
  }

  public static final byte[] decodeFast(char[] paramArrayOfChar, int paramInt1, int paramInt2)
  {
    if (paramInt2 == 0)
      return new byte[0];
    int i = paramInt1;
    int j = -1 + (paramInt1 + paramInt2);
    while ((i < j) && (IA[paramArrayOfChar[i]] < 0))
      i++;
    while ((j > 0) && (IA[paramArrayOfChar[j]] < 0))
      j--;
    int k;
    int i23;
    label114: int n;
    label120: int i1;
    byte[] arrayOfByte;
    int i4;
    int i5;
    label158: int i17;
    int i21;
    int i22;
    if (paramArrayOfChar[j] == '=')
      if (paramArrayOfChar[(j - 1)] == '=')
      {
        k = 2;
        int m = 1 + (j - i);
        if (paramInt2 <= 76)
          break label364;
        if (paramArrayOfChar[76] != '\r')
          break label358;
        i23 = m / 78;
        n = i23 << 1;
        i1 = (6 * (m - n) >> 3) - k;
        arrayOfByte = new byte[i1];
        int i2 = 0;
        int i3 = 3 * (i1 / 3);
        i4 = 0;
        i5 = i;
        if (i4 >= i3)
          break label370;
        int[] arrayOfInt2 = IA;
        int i11 = i5 + 1;
        int i12 = arrayOfInt2[paramArrayOfChar[i5]] << 18;
        int[] arrayOfInt3 = IA;
        int i13 = i11 + 1;
        int i14 = i12 | arrayOfInt3[paramArrayOfChar[i11]] << 12;
        int[] arrayOfInt4 = IA;
        int i15 = i13 + 1;
        int i16 = i14 | arrayOfInt4[paramArrayOfChar[i13]] << 6;
        int[] arrayOfInt5 = IA;
        i17 = i15 + 1;
        int i18 = i16 | arrayOfInt5[paramArrayOfChar[i15]];
        int i19 = i4 + 1;
        arrayOfByte[i4] = ((byte)(i18 >> 16));
        int i20 = i19 + 1;
        arrayOfByte[i19] = ((byte)(i18 >> 8));
        i21 = i20 + 1;
        arrayOfByte[i20] = ((byte)i18);
        if (n <= 0)
          break label482;
        i2++;
        if (i2 != 19)
          break label482;
        i22 = i17 + 2;
        i2 = 0;
      }
    while (true)
    {
      i4 = i21;
      i5 = i22;
      break label158;
      k = 1;
      break;
      k = 0;
      break;
      label358: i23 = 0;
      break label114;
      label364: n = 0;
      break label120;
      label370: if (i4 < i1)
      {
        int i6 = 0;
        int i7 = 0;
        while (i5 <= j - k)
        {
          int[] arrayOfInt1 = IA;
          int i10 = i5 + 1;
          i6 |= arrayOfInt1[paramArrayOfChar[i5]] << 18 - i7 * 6;
          i7++;
          i5 = i10;
        }
        int i8 = 16;
        while (i4 < i1)
        {
          int i9 = i4 + 1;
          arrayOfByte[i4] = ((byte)(i6 >> i8));
          i8 -= 8;
          i4 = i9;
        }
      }
      return arrayOfByte;
      label482: i22 = i17;
    }
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.alibaba.fastjson.util.Base64
 * JD-Core Version:    0.6.2
 */