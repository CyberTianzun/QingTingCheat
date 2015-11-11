package cn.com.iresearch.mapptracker.fm.base64.org.apache.commons.codec.binary;

import cn.com.iresearch.mapptracker.fm.base64.org.apache.commons.codec.BinaryDecoder;
import cn.com.iresearch.mapptracker.fm.base64.org.apache.commons.codec.BinaryEncoder;
import cn.com.iresearch.mapptracker.fm.base64.org.apache.commons.codec.DecoderException;
import cn.com.iresearch.mapptracker.fm.base64.org.apache.commons.codec.EncoderException;

public abstract class BaseNCodec
  implements BinaryDecoder, BinaryEncoder
{
  private static final int DEFAULT_BUFFER_RESIZE_FACTOR = 2;
  private static final int DEFAULT_BUFFER_SIZE = 8192;
  static final int EOF = -1;
  protected static final int MASK_8BITS = 255;
  public static final int MIME_CHUNK_SIZE = 76;
  protected static final byte PAD_DEFAULT = 61;
  public static final int PEM_CHUNK_SIZE = 64;
  protected final byte PAD = 61;
  private final int chunkSeparatorLength;
  private final int encodedBlockSize;
  protected final int lineLength;
  private final int unencodedBlockSize;

  protected BaseNCodec(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    this.unencodedBlockSize = paramInt1;
    this.encodedBlockSize = paramInt2;
    if ((paramInt3 > 0) && (paramInt4 > 0));
    for (int i = 1; ; i = 0)
    {
      int j = 0;
      if (i != 0)
        j = paramInt2 * (paramInt3 / paramInt2);
      this.lineLength = j;
      this.chunkSeparatorLength = paramInt4;
      return;
    }
  }

  protected static boolean isWhiteSpace(byte paramByte)
  {
    switch (paramByte)
    {
    default:
      return false;
    case 9:
    case 10:
    case 13:
    case 32:
    }
    return true;
  }

  private byte[] resizeBuffer(BaseNCodec.Context paramContext)
  {
    if (paramContext.buffer == null)
    {
      paramContext.buffer = new byte[getDefaultBufferSize()];
      paramContext.pos = 0;
      paramContext.readPos = 0;
    }
    while (true)
    {
      return paramContext.buffer;
      byte[] arrayOfByte = new byte[paramContext.buffer.length << 1];
      System.arraycopy(paramContext.buffer, 0, arrayOfByte, 0, paramContext.buffer.length);
      paramContext.buffer = arrayOfByte;
    }
  }

  int available(BaseNCodec.Context paramContext)
  {
    if (paramContext.buffer != null)
      return paramContext.pos - paramContext.readPos;
    return 0;
  }

  protected boolean containsAlphabetOrPad(byte[] paramArrayOfByte)
  {
    if (paramArrayOfByte == null);
    while (true)
    {
      return false;
      int i = paramArrayOfByte.length;
      for (int j = 0; j < i; j++)
      {
        byte b = paramArrayOfByte[j];
        if ((61 == b) || (isInAlphabet(b)))
          return true;
      }
    }
  }

  public Object decode(Object paramObject)
  {
    if ((paramObject instanceof byte[]))
      return decode((byte[])paramObject);
    if ((paramObject instanceof String))
      return decode((String)paramObject);
    throw new DecoderException("Parameter supplied to Base-N decode is not a byte[] or a String");
  }

  abstract void decode(byte[] paramArrayOfByte, int paramInt1, int paramInt2, BaseNCodec.Context paramContext);

  public byte[] decode(String paramString)
  {
    return decode(StringUtils.getBytesUtf8(paramString));
  }

  public byte[] decode(byte[] paramArrayOfByte)
  {
    if ((paramArrayOfByte == null) || (paramArrayOfByte.length == 0))
      return paramArrayOfByte;
    BaseNCodec.Context localContext = new BaseNCodec.Context();
    decode(paramArrayOfByte, 0, paramArrayOfByte.length, localContext);
    decode(paramArrayOfByte, 0, -1, localContext);
    byte[] arrayOfByte = new byte[localContext.pos];
    readResults(arrayOfByte, 0, arrayOfByte.length, localContext);
    return arrayOfByte;
  }

  public Object encode(Object paramObject)
  {
    if (!(paramObject instanceof byte[]))
      throw new EncoderException("Parameter supplied to Base-N encode is not a byte[]");
    return encode((byte[])paramObject);
  }

  abstract void encode(byte[] paramArrayOfByte, int paramInt1, int paramInt2, BaseNCodec.Context paramContext);

  public byte[] encode(byte[] paramArrayOfByte)
  {
    if ((paramArrayOfByte == null) || (paramArrayOfByte.length == 0))
      return paramArrayOfByte;
    BaseNCodec.Context localContext = new BaseNCodec.Context();
    encode(paramArrayOfByte, 0, paramArrayOfByte.length, localContext);
    encode(paramArrayOfByte, 0, -1, localContext);
    byte[] arrayOfByte = new byte[localContext.pos - localContext.readPos];
    readResults(arrayOfByte, 0, arrayOfByte.length, localContext);
    return arrayOfByte;
  }

  public String encodeAsString(byte[] paramArrayOfByte)
  {
    return StringUtils.newStringUtf8(encode(paramArrayOfByte));
  }

  public String encodeToString(byte[] paramArrayOfByte)
  {
    return StringUtils.newStringUtf8(encode(paramArrayOfByte));
  }

  protected byte[] ensureBufferSize(int paramInt, BaseNCodec.Context paramContext)
  {
    if ((paramContext.buffer == null) || (paramContext.buffer.length < paramInt + paramContext.pos))
      return resizeBuffer(paramContext);
    return paramContext.buffer;
  }

  protected int getDefaultBufferSize()
  {
    return 8192;
  }

  public long getEncodedLength(byte[] paramArrayOfByte)
  {
    long l = (-1 + (paramArrayOfByte.length + this.unencodedBlockSize)) / this.unencodedBlockSize * this.encodedBlockSize;
    if (this.lineLength > 0)
      l += (l + this.lineLength - 1L) / this.lineLength * this.chunkSeparatorLength;
    return l;
  }

  boolean hasData(BaseNCodec.Context paramContext)
  {
    return paramContext.buffer != null;
  }

  protected abstract boolean isInAlphabet(byte paramByte);

  public boolean isInAlphabet(String paramString)
  {
    return isInAlphabet(StringUtils.getBytesUtf8(paramString), true);
  }

  public boolean isInAlphabet(byte[] paramArrayOfByte, boolean paramBoolean)
  {
    for (int i = 0; ; i++)
    {
      boolean bool1;
      if (i >= paramArrayOfByte.length)
        bool1 = true;
      boolean bool2;
      do
      {
        do
        {
          return bool1;
          if (isInAlphabet(paramArrayOfByte[i]))
            break;
          bool1 = false;
        }
        while (!paramBoolean);
        if (paramArrayOfByte[i] == 61)
          break;
        bool2 = isWhiteSpace(paramArrayOfByte[i]);
        bool1 = false;
      }
      while (!bool2);
    }
  }

  int readResults(byte[] paramArrayOfByte, int paramInt1, int paramInt2, BaseNCodec.Context paramContext)
  {
    if (paramContext.buffer != null)
    {
      int i = Math.min(available(paramContext), paramInt2);
      System.arraycopy(paramContext.buffer, paramContext.readPos, paramArrayOfByte, paramInt1, i);
      paramContext.readPos = (i + paramContext.readPos);
      if (paramContext.readPos >= paramContext.pos)
        paramContext.buffer = null;
      return i;
    }
    if (paramContext.eof)
      return -1;
    return 0;
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     cn.com.iresearch.mapptracker.fm.base64.org.apache.commons.codec.binary.BaseNCodec
 * JD-Core Version:    0.6.2
 */