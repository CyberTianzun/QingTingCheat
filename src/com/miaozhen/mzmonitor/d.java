package com.miaozhen.mzmonitor;

final class d
{
  private static final byte[] a = { 65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90, 97, 98, 99, 100, 101, 102, 103, 104, 105, 106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 43, 47 };

  public static String a(String paramString)
  {
    if (paramString == null)
      return "";
    byte[] arrayOfByte1 = paramString.getBytes();
    int i = arrayOfByte1.length;
    byte[] arrayOfByte2 = new byte[(i + 2) / 3 << 2];
    int j = 0;
    int k = 0;
    int i2;
    if (i <= 2)
    {
      if (i == 0)
        break label297;
      i2 = k + 1;
      arrayOfByte2[k] = a[(arrayOfByte1[j] >> 2)];
      if (i <= 1)
        break label254;
      int i5 = i2 + 1;
      arrayOfByte2[i2] = a[(((0x3 & arrayOfByte1[j]) << 4) + (arrayOfByte1[(j + 1)] >> 4))];
      int i6 = i5 + 1;
      arrayOfByte2[i5] = a[((0xF & arrayOfByte1[(j + 1)]) << 2)];
      arrayOfByte2[i6] = 61;
    }
    label297: 
    while (true)
    {
      return new String(arrayOfByte2);
      int m = k + 1;
      arrayOfByte2[k] = a[(arrayOfByte1[j] >> 2)];
      int n = m + 1;
      arrayOfByte2[m] = a[(((0x3 & arrayOfByte1[j]) << 4) + (arrayOfByte1[(j + 1)] >> 4))];
      int i1 = n + 1;
      arrayOfByte2[n] = a[(((0xF & arrayOfByte1[(j + 1)]) << 2) + (arrayOfByte1[(j + 2)] >> 6))];
      k = i1 + 1;
      arrayOfByte2[i1] = a[(0x3F & arrayOfByte1[(j + 2)])];
      j += 3;
      i -= 3;
      break;
      label254: int i3 = i2 + 1;
      arrayOfByte2[i2] = a[((0x3 & arrayOfByte1[j]) << 4)];
      int i4 = i3 + 1;
      arrayOfByte2[i3] = 61;
      arrayOfByte2[i4] = 61;
    }
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.miaozhen.mzmonitor.d
 * JD-Core Version:    0.6.2
 */