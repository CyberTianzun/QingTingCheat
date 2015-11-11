package com.alibaba.fastjson.util;

import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CoderResult;

public class UTF8Decoder extends CharsetDecoder
{
  private static final Charset charset = Charset.forName("UTF-8");

  public UTF8Decoder()
  {
    super(charset, 1.0F, 1.0F);
  }

  private CoderResult decodeArrayLoop(ByteBuffer paramByteBuffer, CharBuffer paramCharBuffer)
  {
    byte[] arrayOfByte = paramByteBuffer.array();
    int i = paramByteBuffer.arrayOffset() + paramByteBuffer.position();
    int j = paramByteBuffer.arrayOffset() + paramByteBuffer.limit();
    char[] arrayOfChar = paramCharBuffer.array();
    int k = paramCharBuffer.arrayOffset() + paramCharBuffer.position();
    int m = paramCharBuffer.arrayOffset() + paramCharBuffer.limit();
    int n = k + Math.min(j - i, m - k);
    int i1 = k;
    int i2 = i;
    int i3;
    int i5;
    int i4;
    while ((i1 < n) && (arrayOfByte[i2] >= 0))
    {
      int i17 = i1 + 1;
      int i18 = i2 + 1;
      arrayOfChar[i1] = ((char)arrayOfByte[i2]);
      i1 = i17;
      i2 = i18;
      continue;
      int i16 = i3 + 1;
      arrayOfChar[i3] = ((char)i5);
      i4++;
      i3 = i16;
    }
    while (true)
    {
      if (i4 < j)
      {
        i5 = arrayOfByte[i4];
        if (i5 >= 0)
        {
          if (i3 < m)
            break;
          return xflow(paramByteBuffer, i4, j, paramCharBuffer, i3, 1);
        }
        if (i5 >> 5 == -2)
        {
          if ((j - i4 < 2) || (i3 >= m))
            return xflow(paramByteBuffer, i4, j, paramCharBuffer, i3, 2);
          int i14 = arrayOfByte[(i4 + 1)];
          if (isMalformed2(i5, i14))
            return malformed(paramByteBuffer, i4, paramCharBuffer, i3, 2);
          int i15 = i3 + 1;
          arrayOfChar[i3] = ((char)(0xF80 ^ (i14 ^ i5 << 6)));
          i4 += 2;
          i3 = i15;
          continue;
        }
        if (i5 >> 4 == -2)
        {
          if ((j - i4 < 3) || (i3 >= m))
            return xflow(paramByteBuffer, i4, j, paramCharBuffer, i3, 3);
          int i11 = arrayOfByte[(i4 + 1)];
          int i12 = arrayOfByte[(i4 + 2)];
          if (isMalformed3(i5, i11, i12))
            return malformed(paramByteBuffer, i4, paramCharBuffer, i3, 3);
          int i13 = i3 + 1;
          arrayOfChar[i3] = ((char)(0x1F80 ^ (i12 ^ (i5 << 12 ^ i11 << 6))));
          i4 += 3;
          i3 = i13;
          continue;
        }
        if (i5 >> 3 == -2)
        {
          if ((j - i4 < 4) || (m - i3 < 2))
            return xflow(paramByteBuffer, i4, j, paramCharBuffer, i3, 4);
          int i6 = arrayOfByte[(i4 + 1)];
          int i7 = arrayOfByte[(i4 + 2)];
          int i8 = arrayOfByte[(i4 + 3)];
          int i9 = (i5 & 0x7) << 18 | (i6 & 0x3F) << 12 | (i7 & 0x3F) << 6 | i8 & 0x3F;
          if ((isMalformed4(i6, i7, i8)) || (!Surrogate.neededFor(i9)))
            return malformed(paramByteBuffer, i4, paramCharBuffer, i3, 4);
          int i10 = i3 + 1;
          arrayOfChar[i3] = Surrogate.high(i9);
          i3 = i10 + 1;
          arrayOfChar[i10] = Surrogate.low(i9);
          i4 += 4;
          continue;
        }
        return malformed(paramByteBuffer, i4, paramCharBuffer, i3, 1);
      }
      return xflow(paramByteBuffer, i4, j, paramCharBuffer, i3, 0);
      i3 = i1;
      i4 = i2;
    }
  }

  private static final boolean isMalformed2(int paramInt1, int paramInt2)
  {
    return ((paramInt1 & 0x1E) == 0) || ((paramInt2 & 0xC0) != 128);
  }

  private static boolean isMalformed3(int paramInt1, int paramInt2, int paramInt3)
  {
    return ((paramInt1 == -32) && ((paramInt2 & 0xE0) == 128)) || ((paramInt2 & 0xC0) != 128) || ((paramInt3 & 0xC0) != 128);
  }

  private static final boolean isMalformed4(int paramInt1, int paramInt2, int paramInt3)
  {
    return ((paramInt1 & 0xC0) != 128) || ((paramInt2 & 0xC0) != 128) || ((paramInt3 & 0xC0) != 128);
  }

  private static boolean isNotContinuation(int paramInt)
  {
    return (paramInt & 0xC0) != 128;
  }

  private static CoderResult lookupN(ByteBuffer paramByteBuffer, int paramInt)
  {
    for (int i = 1; i < paramInt; i++)
      if (isNotContinuation(paramByteBuffer.get()))
        return CoderResult.malformedForLength(i);
    return CoderResult.malformedForLength(paramInt);
  }

  private static CoderResult malformed(ByteBuffer paramByteBuffer, int paramInt1, CharBuffer paramCharBuffer, int paramInt2, int paramInt3)
  {
    paramByteBuffer.position(paramInt1 - paramByteBuffer.arrayOffset());
    CoderResult localCoderResult = malformedN(paramByteBuffer, paramInt3);
    updatePositions(paramByteBuffer, paramInt1, paramCharBuffer, paramInt2);
    return localCoderResult;
  }

  public static CoderResult malformedN(ByteBuffer paramByteBuffer, int paramInt)
  {
    int i = 2;
    switch (paramInt)
    {
    default:
      throw new IllegalStateException();
    case 1:
      int i1 = paramByteBuffer.get();
      if (i1 >> 2 == -2)
      {
        if (paramByteBuffer.remaining() < 4)
          return CoderResult.UNDERFLOW;
        return lookupN(paramByteBuffer, 5);
      }
      if (i1 >> 1 == -2)
      {
        if (paramByteBuffer.remaining() < 5)
          return CoderResult.UNDERFLOW;
        return lookupN(paramByteBuffer, 6);
      }
      return CoderResult.malformedForLength(1);
    case 2:
      return CoderResult.malformedForLength(1);
    case 3:
      int m = paramByteBuffer.get();
      int n = paramByteBuffer.get();
      if (((m == -32) && ((n & 0xE0) == 128)) || (isNotContinuation(n)))
        i = 1;
      return CoderResult.malformedForLength(i);
    case 4:
    }
    int j = 0xFF & paramByteBuffer.get();
    int k = 0xFF & paramByteBuffer.get();
    if ((j > 244) || ((j == 240) && ((k < 144) || (k > 191))) || ((j == 244) && ((k & 0xF0) != 128)) || (isNotContinuation(k)))
      return CoderResult.malformedForLength(1);
    if (isNotContinuation(paramByteBuffer.get()))
      return CoderResult.malformedForLength(i);
    return CoderResult.malformedForLength(3);
  }

  static final void updatePositions(Buffer paramBuffer1, int paramInt1, Buffer paramBuffer2, int paramInt2)
  {
    paramBuffer1.position(paramInt1);
    paramBuffer2.position(paramInt2);
  }

  private static CoderResult xflow(Buffer paramBuffer1, int paramInt1, int paramInt2, Buffer paramBuffer2, int paramInt3, int paramInt4)
  {
    updatePositions(paramBuffer1, paramInt1, paramBuffer2, paramInt3);
    if ((paramInt4 == 0) || (paramInt2 - paramInt1 < paramInt4))
      return CoderResult.UNDERFLOW;
    return CoderResult.OVERFLOW;
  }

  protected CoderResult decodeLoop(ByteBuffer paramByteBuffer, CharBuffer paramCharBuffer)
  {
    return decodeArrayLoop(paramByteBuffer, paramCharBuffer);
  }

  private static class Surrogate
  {
    public static final int UCS4_MAX = 1114111;
    public static final int UCS4_MIN = 65536;

    static
    {
      if (!UTF8Decoder.class.desiredAssertionStatus());
      for (boolean bool = true; ; bool = false)
      {
        $assertionsDisabled = bool;
        return;
      }
    }

    public static char high(int paramInt)
    {
      assert (neededFor(paramInt));
      return (char)(0xD800 | 0x3FF & paramInt - 65536 >> 10);
    }

    public static char low(int paramInt)
    {
      assert (neededFor(paramInt));
      return (char)(0xDC00 | 0x3FF & paramInt - 65536);
    }

    public static boolean neededFor(int paramInt)
    {
      return (paramInt >= 65536) && (paramInt <= 1114111);
    }
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.alibaba.fastjson.util.UTF8Decoder
 * JD-Core Version:    0.6.2
 */