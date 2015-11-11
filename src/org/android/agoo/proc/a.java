package org.android.agoo.proc;

import java.io.UnsupportedEncodingException;

public final class a
{
  private static final byte[] a = { 65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90, 97, 98, 99, 100, 101, 102, 103, 104, 105, 106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 43, 47 };

  public static byte[] a(byte[] paramArrayOfByte)
  {
    return a(paramArrayOfByte, paramArrayOfByte.length);
  }

  static byte[] a(byte[] paramArrayOfByte, int paramInt)
  {
    if ((paramArrayOfByte == null) || (paramInt <= 0))
      return null;
    int i = 3 * (paramInt / 4);
    if (i == 0)
      return null;
    byte[] arrayOfByte1 = new byte[i];
    int j = 0;
    int k = paramArrayOfByte[(paramInt - 1)];
    if ((k == 10) || (k == 13) || (k == 32) || (k == 9));
    while (true)
    {
      paramInt--;
      break;
      if (k != 61)
        break label83;
      j++;
    }
    label83: int m = 0;
    int n = 0;
    int i1 = 0;
    int i2 = 0;
    int i5;
    int i6;
    int i7;
    int i8;
    if (m < paramInt)
    {
      i5 = paramArrayOfByte[m];
      if ((i5 == 10) || (i5 == 13) || (i5 == 32))
        break label430;
      if (i5 == 9)
      {
        i6 = n;
        i7 = i2;
        i8 = i1;
      }
    }
    while (true)
    {
      m++;
      i1 = i8;
      i2 = i7;
      n = i6;
      break;
      int i9;
      label186: int i10;
      int i11;
      if ((i5 >= 65) && (i5 <= 90))
      {
        i9 = i5 - 65;
        i10 = n << 6 | (byte)i9;
        if (i1 % 4 != 3)
          break label423;
        int i12 = i2 + 1;
        arrayOfByte1[i2] = ((byte)(i10 >> 16));
        int i13 = i12 + 1;
        arrayOfByte1[i12] = ((byte)(i10 >> 8));
        i11 = i13 + 1;
        arrayOfByte1[i13] = ((byte)i10);
      }
      while (true)
      {
        i8 = i1 + 1;
        i7 = i11;
        i6 = i10;
        break;
        if ((i5 >= 97) && (i5 <= 122))
        {
          i9 = i5 - 71;
          break label186;
        }
        if ((i5 >= 48) && (i5 <= 57))
        {
          i9 = i5 + 4;
          break label186;
        }
        if (i5 == 43)
        {
          i9 = 62;
          break label186;
        }
        if (i5 == 47)
        {
          i9 = 63;
          break label186;
        }
        return null;
        int i4;
        if (j > 0)
        {
          int i3 = n << j * 6;
          i4 = i2 + 1;
          arrayOfByte1[i2] = ((byte)(i3 >> 16));
          if (j != 1)
            break label416;
          i2 = i4 + 1;
          arrayOfByte1[i4] = ((byte)(i3 >> 8));
        }
        while (true)
        {
          byte[] arrayOfByte2 = new byte[i2];
          System.arraycopy(arrayOfByte1, 0, arrayOfByte2, 0, i2);
          return arrayOfByte2;
          label416: i2 = i4;
        }
        label423: i11 = i2;
      }
      label430: i6 = n;
      i7 = i2;
      i8 = i1;
    }
  }

  public static String b(byte[] paramArrayOfByte)
    throws UnsupportedEncodingException
  {
    byte[] arrayOfByte = new byte[4 * (2 + paramArrayOfByte.length) / 3];
    int i = paramArrayOfByte.length - paramArrayOfByte.length % 3;
    int j = 0;
    int i8;
    for (int k = 0; j < i; k = i8)
    {
      int i5 = k + 1;
      arrayOfByte[k] = a[((0xFF & paramArrayOfByte[j]) >> 2)];
      int i6 = i5 + 1;
      arrayOfByte[i5] = a[((0x3 & paramArrayOfByte[j]) << 4 | (0xFF & paramArrayOfByte[(j + 1)]) >> 4)];
      int i7 = i6 + 1;
      arrayOfByte[i6] = a[((0xF & paramArrayOfByte[(j + 1)]) << 2 | (0xFF & paramArrayOfByte[(j + 2)]) >> 6)];
      i8 = i7 + 1;
      arrayOfByte[i7] = a[(0x3F & paramArrayOfByte[(j + 2)])];
      j += 3;
    }
    switch (paramArrayOfByte.length % 3)
    {
    default:
    case 1:
    case 2:
    }
    while (true)
    {
      return new String(arrayOfByte, 0, k, "US_ASCII");
      int i2 = k + 1;
      arrayOfByte[k] = a[((0xFF & paramArrayOfByte[i]) >> 2)];
      int i3 = i2 + 1;
      arrayOfByte[i2] = a[((0x3 & paramArrayOfByte[i]) << 4)];
      int i4 = i3 + 1;
      arrayOfByte[i3] = 61;
      k = i4 + 1;
      arrayOfByte[i4] = 61;
      continue;
      int m = k + 1;
      arrayOfByte[k] = a[((0xFF & paramArrayOfByte[i]) >> 2)];
      int n = m + 1;
      arrayOfByte[m] = a[((0x3 & paramArrayOfByte[i]) << 4 | (0xFF & paramArrayOfByte[(i + 1)]) >> 4)];
      int i1 = n + 1;
      arrayOfByte[n] = a[((0xF & paramArrayOfByte[(i + 1)]) << 2)];
      k = i1 + 1;
      arrayOfByte[i1] = 61;
    }
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     org.android.agoo.proc.a
 * JD-Core Version:    0.6.2
 */