package com.alibaba.fastjson.serializer;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.util.ThreadLocalCache;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;
import java.nio.charset.CoderResult;
import java.nio.charset.CodingErrorAction;

public class SerialWriterStringEncoder
{
  private final CharsetEncoder encoder;

  public SerialWriterStringEncoder(Charset paramCharset)
  {
    this(paramCharset.newEncoder().onMalformedInput(CodingErrorAction.REPLACE).onUnmappableCharacter(CodingErrorAction.REPLACE));
  }

  public SerialWriterStringEncoder(CharsetEncoder paramCharsetEncoder)
  {
    this.encoder = paramCharsetEncoder;
  }

  private static int scale(int paramInt, float paramFloat)
  {
    return (int)(paramInt * paramFloat);
  }

  public byte[] encode(char[] paramArrayOfChar, int paramInt1, int paramInt2)
  {
    if (paramInt2 == 0)
      return new byte[0];
    this.encoder.reset();
    return encode(paramArrayOfChar, paramInt1, paramInt2, ThreadLocalCache.getBytes(scale(paramInt2, this.encoder.maxBytesPerChar())));
  }

  public byte[] encode(char[] paramArrayOfChar, int paramInt1, int paramInt2, byte[] paramArrayOfByte)
  {
    ByteBuffer localByteBuffer = ByteBuffer.wrap(paramArrayOfByte);
    CharBuffer localCharBuffer = CharBuffer.wrap(paramArrayOfChar, paramInt1, paramInt2);
    try
    {
      CoderResult localCoderResult1 = this.encoder.encode(localCharBuffer, localByteBuffer, true);
      if (!localCoderResult1.isUnderflow())
        localCoderResult1.throwException();
      CoderResult localCoderResult2 = this.encoder.flush(localByteBuffer);
      if (!localCoderResult2.isUnderflow())
        localCoderResult2.throwException();
      int i = localByteBuffer.position();
      byte[] arrayOfByte = new byte[i];
      System.arraycopy(paramArrayOfByte, 0, arrayOfByte, 0, i);
      return arrayOfByte;
    }
    catch (CharacterCodingException localCharacterCodingException)
    {
      throw new JSONException(localCharacterCodingException.getMessage(), localCharacterCodingException);
    }
  }

  public CharsetEncoder getEncoder()
  {
    return this.encoder;
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.alibaba.fastjson.serializer.SerialWriterStringEncoder
 * JD-Core Version:    0.6.2
 */