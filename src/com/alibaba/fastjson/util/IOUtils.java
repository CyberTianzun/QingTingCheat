package com.alibaba.fastjson.util;

import com.alibaba.fastjson.JSONException;
import java.io.Closeable;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CoderResult;

public class IOUtils
{
  static final char[] DigitOnes = { 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57 };
  static final char[] DigitTens;
  static final char[] digits = { 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 97, 98, 99, 100, 101, 102, 103, 104, 105, 106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122 };
  static final int[] sizeTable = { 9, 99, 999, 9999, 99999, 999999, 9999999, 99999999, 999999999, 2147483647 };

  static
  {
    DigitTens = new char[] { 48, 48, 48, 48, 48, 48, 48, 48, 48, 48, 49, 49, 49, 49, 49, 49, 49, 49, 49, 49, 50, 50, 50, 50, 50, 50, 50, 50, 50, 50, 51, 51, 51, 51, 51, 51, 51, 51, 51, 51, 52, 52, 52, 52, 52, 52, 52, 52, 52, 52, 53, 53, 53, 53, 53, 53, 53, 53, 53, 53, 54, 54, 54, 54, 54, 54, 54, 54, 54, 54, 55, 55, 55, 55, 55, 55, 55, 55, 55, 55, 56, 56, 56, 56, 56, 56, 56, 56, 56, 56, 57, 57, 57, 57, 57, 57, 57, 57, 57, 57 };
  }

  public static void close(Closeable paramCloseable)
  {
    if (paramCloseable != null);
    try
    {
      paramCloseable.close();
      return;
    }
    catch (Exception localException)
    {
    }
  }

  public static void decode(CharsetDecoder paramCharsetDecoder, ByteBuffer paramByteBuffer, CharBuffer paramCharBuffer)
  {
    try
    {
      CoderResult localCoderResult1 = paramCharsetDecoder.decode(paramByteBuffer, paramCharBuffer, true);
      if (!localCoderResult1.isUnderflow())
        localCoderResult1.throwException();
      CoderResult localCoderResult2 = paramCharsetDecoder.flush(paramCharBuffer);
      if (!localCoderResult2.isUnderflow())
        localCoderResult2.throwException();
      return;
    }
    catch (CharacterCodingException localCharacterCodingException)
    {
      throw new JSONException(localCharacterCodingException.getMessage(), localCharacterCodingException);
    }
  }

  public static void getChars(byte paramByte, int paramInt, char[] paramArrayOfChar)
  {
    int i = paramByte;
    int j = paramInt;
    int k = 0;
    if (i < 0)
    {
      k = 45;
      i = -i;
    }
    do
    {
      int m = 52429 * i >>> 19;
      int n = i - ((m << 3) + (m << 1));
      j--;
      paramArrayOfChar[j] = digits[n];
      i = m;
    }
    while (i != 0);
    if (k != 0)
      paramArrayOfChar[(j - 1)] = k;
  }

  public static void getChars(int paramInt1, int paramInt2, char[] paramArrayOfChar)
  {
    int i = paramInt2;
    int j = 0;
    if (paramInt1 < 0)
    {
      j = 45;
      paramInt1 = -paramInt1;
    }
    while (paramInt1 >= 65536)
    {
      int n = paramInt1 / 100;
      int i1 = paramInt1 - ((n << 6) + (n << 5) + (n << 2));
      paramInt1 = n;
      int i2 = i - 1;
      paramArrayOfChar[i2] = DigitOnes[i1];
      i = i2 - 1;
      paramArrayOfChar[i] = DigitTens[i1];
    }
    do
    {
      int k = 52429 * paramInt1 >>> 19;
      int m = paramInt1 - ((k << 3) + (k << 1));
      i--;
      paramArrayOfChar[i] = digits[m];
      paramInt1 = k;
    }
    while (paramInt1 != 0);
    if (j != 0)
      paramArrayOfChar[(i - 1)] = j;
  }

  public static void getChars(long paramLong, int paramInt, char[] paramArrayOfChar)
  {
    int i = paramInt;
    boolean bool = paramLong < 0L;
    int j = 0;
    if (bool)
    {
      j = 45;
      paramLong = -paramLong;
    }
    while (paramLong > 2147483647L)
    {
      long l = paramLong / 100L;
      int i4 = (int)(paramLong - ((l << 6) + (l << 5) + (l << 2)));
      paramLong = l;
      int i5 = i - 1;
      paramArrayOfChar[i5] = DigitOnes[i4];
      i = i5 - 1;
      paramArrayOfChar[i] = DigitTens[i4];
    }
    int k = (int)paramLong;
    while (k >= 65536)
    {
      int i1 = k / 100;
      int i2 = k - ((i1 << 6) + (i1 << 5) + (i1 << 2));
      k = i1;
      int i3 = i - 1;
      paramArrayOfChar[i3] = DigitOnes[i2];
      i = i3 - 1;
      paramArrayOfChar[i] = DigitTens[i2];
    }
    do
    {
      int m = 52429 * k >>> 19;
      int n = k - ((m << 3) + (m << 1));
      i--;
      paramArrayOfChar[i] = digits[n];
      k = m;
    }
    while (k != 0);
    if (j != 0)
      paramArrayOfChar[(i - 1)] = j;
  }

  public static int stringSize(int paramInt)
  {
    for (int i = 0; ; i++)
      if (paramInt <= sizeTable[i])
        return i + 1;
  }

  public static int stringSize(long paramLong)
  {
    long l = 10L;
    for (int i = 1; i < 19; i++)
    {
      if (paramLong < l)
        return i;
      l *= 10L;
    }
    return 19;
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.alibaba.fastjson.util.IOUtils
 * JD-Core Version:    0.6.2
 */