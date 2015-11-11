package cn.com.iresearch.mapptracker.fm.base64.org.apache.commons.codec.binary;

import android.annotation.SuppressLint;
import cn.com.iresearch.mapptracker.fm.base64.org.apache.commons.codec.BinaryDecoder;
import cn.com.iresearch.mapptracker.fm.base64.org.apache.commons.codec.BinaryEncoder;
import cn.com.iresearch.mapptracker.fm.base64.org.apache.commons.codec.Charsets;
import cn.com.iresearch.mapptracker.fm.base64.org.apache.commons.codec.DecoderException;
import cn.com.iresearch.mapptracker.fm.base64.org.apache.commons.codec.EncoderException;
import java.nio.charset.Charset;

public class Hex
  implements BinaryDecoder, BinaryEncoder
{
  public static final Charset DEFAULT_CHARSET = Charsets.UTF_8;
  public static final String DEFAULT_CHARSET_NAME = "UTF-8";
  private static final char[] DIGITS_LOWER = { 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 97, 98, 99, 100, 101, 102 };
  private static final char[] DIGITS_UPPER = { 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 65, 66, 67, 68, 69, 70 };
  private static int EOF = -1;
  private final Charset charset;

  public Hex()
  {
    this.charset = DEFAULT_CHARSET;
  }

  public Hex(String paramString)
  {
    this(Charset.forName(paramString));
  }

  public Hex(Charset paramCharset)
  {
    this.charset = paramCharset;
  }

  public static byte[] decodeHex(char[] paramArrayOfChar)
  {
    int i = 0;
    int j = paramArrayOfChar.length;
    if ((j & 0x1) != 0)
      throw new DecoderException("Odd number of characters.");
    byte[] arrayOfByte = new byte[j >> 1];
    for (int k = 0; ; k++)
    {
      if (i >= j)
        return arrayOfByte;
      int m = toDigit(paramArrayOfChar[i], i) << 4;
      int n = i + 1;
      int i1 = m | toDigit(paramArrayOfChar[n], n);
      i = n + 1;
      arrayOfByte[k] = ((byte)i1);
    }
  }

  public static char[] encodeHex(byte[] paramArrayOfByte)
  {
    return encodeHex(paramArrayOfByte, true);
  }

  public static char[] encodeHex(byte[] paramArrayOfByte, boolean paramBoolean)
  {
    if (paramBoolean);
    for (char[] arrayOfChar = DIGITS_LOWER; ; arrayOfChar = DIGITS_UPPER)
      return encodeHex(paramArrayOfByte, arrayOfChar);
  }

  protected static char[] encodeHex(byte[] paramArrayOfByte, char[] paramArrayOfChar)
  {
    int i = 0;
    int j = paramArrayOfByte.length;
    char[] arrayOfChar = new char[j << 1];
    for (int k = 0; ; k++)
    {
      if (k >= j)
        return arrayOfChar;
      int m = i + 1;
      arrayOfChar[i] = paramArrayOfChar[((0xF0 & paramArrayOfByte[k]) >>> 4)];
      i = m + 1;
      arrayOfChar[m] = paramArrayOfChar[(0xF & paramArrayOfByte[k])];
    }
  }

  public static String encodeHexString(byte[] paramArrayOfByte)
  {
    return new String(encodeHex(paramArrayOfByte));
  }

  protected static int toDigit(char paramChar, int paramInt)
  {
    int i = Character.digit(paramChar, 16);
    if (i == -1)
      throw new DecoderException("Illegal hexadecimal character " + paramChar + " at index " + paramInt);
    return i;
  }

  public Object decode(Object paramObject)
  {
    try
    {
      if ((paramObject instanceof String));
      for (char[] arrayOfChar = ((String)paramObject).toCharArray(); ; arrayOfChar = (char[])paramObject)
        return decodeHex(arrayOfChar);
    }
    catch (ClassCastException localClassCastException)
    {
      throw new DecoderException(localClassCastException.getMessage(), localClassCastException);
    }
  }

  @SuppressLint({"NewApi"})
  public byte[] decode(byte[] paramArrayOfByte)
  {
    return decodeHex(new String(paramArrayOfByte, getCharset()).toCharArray());
  }

  @SuppressLint({"NewApi"})
  public Object encode(Object paramObject)
  {
    try
    {
      if ((paramObject instanceof String));
      for (byte[] arrayOfByte = ((String)paramObject).getBytes(getCharset()); ; arrayOfByte = (byte[])paramObject)
        return encodeHex(arrayOfByte);
    }
    catch (ClassCastException localClassCastException)
    {
      throw new EncoderException(localClassCastException.getMessage(), localClassCastException);
    }
  }

  @SuppressLint({"NewApi"})
  public byte[] encode(byte[] paramArrayOfByte)
  {
    return encodeHexString(paramArrayOfByte).getBytes(getCharset());
  }

  public Charset getCharset()
  {
    return this.charset;
  }

  public String getCharsetName()
  {
    return this.charset.name();
  }

  public String toString()
  {
    return super.toString() + "[charsetName=" + this.charset + "]";
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     cn.com.iresearch.mapptracker.fm.base64.org.apache.commons.codec.binary.Hex
 * JD-Core Version:    0.6.2
 */