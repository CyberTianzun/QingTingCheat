package fm.qingting.async.http;

import fm.qingting.async.ByteBufferList;
import fm.qingting.async.DataEmitter;
import fm.qingting.async.DataEmitterReader;
import fm.qingting.async.callback.DataCallback;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.List;

abstract class HybiParser
{
  private static final int BYTE = 255;
  private static final int FIN = 128;
  private static final List<Integer> FRAGMENTED_OPCODES = Arrays.asList(arrayOfInteger2);
  private static final int LENGTH = 127;
  private static final int MASK = 128;
  private static final int MODE_BINARY = 2;
  private static final int MODE_TEXT = 1;
  private static final int OPCODE = 15;
  private static final List<Integer> OPCODES;
  private static final int OP_BINARY = 2;
  private static final int OP_CLOSE = 8;
  private static final int OP_CONTINUATION = 0;
  private static final int OP_PING = 9;
  private static final int OP_PONG = 10;
  private static final int OP_TEXT = 1;
  private static final int RSV1 = 64;
  private static final int RSV2 = 32;
  private static final int RSV3 = 16;
  private static final String TAG = "HybiParser";
  private ByteArrayOutputStream mBuffer = new ByteArrayOutputStream();
  private boolean mClosed = false;
  private boolean mFinal;
  private int mLength;
  private int mLengthSize;
  private byte[] mMask = new byte[0];
  private boolean mMasked;
  private boolean mMasking = true;
  private int mMode;
  private int mOpcode;
  private byte[] mPayload = new byte[0];
  private DataEmitterReader mReader = new DataEmitterReader();
  private int mStage;
  DataCallback mStage0 = new DataCallback()
  {
    public void onDataAvailable(DataEmitter paramAnonymousDataEmitter, ByteBufferList paramAnonymousByteBufferList)
    {
      try
      {
        HybiParser.this.parseOpcode(paramAnonymousByteBufferList.get());
        HybiParser.this.parse();
        return;
      }
      catch (HybiParser.ProtocolError localProtocolError)
      {
        while (true)
        {
          HybiParser.this.report(localProtocolError);
          localProtocolError.printStackTrace();
        }
      }
    }
  };
  DataCallback mStage1 = new DataCallback()
  {
    public void onDataAvailable(DataEmitter paramAnonymousDataEmitter, ByteBufferList paramAnonymousByteBufferList)
    {
      HybiParser.this.parseLength(paramAnonymousByteBufferList.get());
      HybiParser.this.parse();
    }
  };
  DataCallback mStage2 = new DataCallback()
  {
    public void onDataAvailable(DataEmitter paramAnonymousDataEmitter, ByteBufferList paramAnonymousByteBufferList)
    {
      byte[] arrayOfByte = new byte[HybiParser.this.mLengthSize];
      paramAnonymousByteBufferList.get(arrayOfByte);
      try
      {
        HybiParser.this.parseExtendedLength(arrayOfByte);
        HybiParser.this.parse();
        return;
      }
      catch (HybiParser.ProtocolError localProtocolError)
      {
        while (true)
        {
          HybiParser.this.report(localProtocolError);
          localProtocolError.printStackTrace();
        }
      }
    }
  };
  DataCallback mStage3 = new DataCallback()
  {
    public void onDataAvailable(DataEmitter paramAnonymousDataEmitter, ByteBufferList paramAnonymousByteBufferList)
    {
      HybiParser.access$402(HybiParser.this, new byte[4]);
      paramAnonymousByteBufferList.get(HybiParser.this.mMask);
      HybiParser.access$502(HybiParser.this, 4);
      HybiParser.this.parse();
    }
  };
  DataCallback mStage4 = new DataCallback()
  {
    public void onDataAvailable(DataEmitter paramAnonymousDataEmitter, ByteBufferList paramAnonymousByteBufferList)
    {
      HybiParser.access$602(HybiParser.this, new byte[HybiParser.this.mLength]);
      paramAnonymousByteBufferList.get(HybiParser.this.mPayload);
      try
      {
        HybiParser.this.emitFrame();
        HybiParser.access$502(HybiParser.this, 0);
        HybiParser.this.parse();
        return;
      }
      catch (IOException localIOException)
      {
        while (true)
        {
          HybiParser.this.report(localIOException);
          localIOException.printStackTrace();
        }
      }
    }
  };

  static
  {
    Integer[] arrayOfInteger1 = new Integer[6];
    arrayOfInteger1[0] = Integer.valueOf(0);
    arrayOfInteger1[1] = Integer.valueOf(1);
    arrayOfInteger1[2] = Integer.valueOf(2);
    arrayOfInteger1[3] = Integer.valueOf(8);
    arrayOfInteger1[4] = Integer.valueOf(9);
    arrayOfInteger1[5] = Integer.valueOf(10);
    OPCODES = Arrays.asList(arrayOfInteger1);
    Integer[] arrayOfInteger2 = new Integer[3];
    arrayOfInteger2[0] = Integer.valueOf(0);
    arrayOfInteger2[1] = Integer.valueOf(1);
    arrayOfInteger2[2] = Integer.valueOf(2);
  }

  public HybiParser(DataEmitter paramDataEmitter)
  {
    paramDataEmitter.setDataCallback(this.mReader);
    parse();
  }

  private static long byteArrayToLong(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
  {
    if (paramArrayOfByte.length < paramInt2)
      throw new IllegalArgumentException("length must be less than or equal to b.length");
    long l = 0L;
    for (int i = 0; i < paramInt2; i++)
    {
      int j = 8 * (paramInt2 - 1 - i);
      l += ((0xFF & paramArrayOfByte[(i + paramInt1)]) << j);
    }
    return l;
  }

  private byte[] decode(String paramString)
  {
    try
    {
      byte[] arrayOfByte = paramString.getBytes("UTF-8");
      return arrayOfByte;
    }
    catch (UnsupportedEncodingException localUnsupportedEncodingException)
    {
      throw new RuntimeException(localUnsupportedEncodingException);
    }
  }

  private void emitFrame()
    throws IOException
  {
    byte[] arrayOfByte1 = mask(this.mPayload, this.mMask, 0);
    int i = this.mOpcode;
    byte[] arrayOfByte2;
    if (i == 0)
    {
      if (this.mMode == 0)
        throw new ProtocolError("Mode was not set.");
      this.mBuffer.write(arrayOfByte1);
      if (this.mFinal)
      {
        arrayOfByte2 = this.mBuffer.toByteArray();
        if (this.mMode != 1)
          break label86;
        onMessage(encode(arrayOfByte2));
        reset();
      }
    }
    label86: 
    do
    {
      return;
      onMessage(arrayOfByte2);
      break;
      if (i == 1)
      {
        if (this.mFinal)
        {
          onMessage(encode(arrayOfByte1));
          return;
        }
        this.mMode = 1;
        this.mBuffer.write(arrayOfByte1);
        return;
      }
      if (i == 2)
      {
        if (this.mFinal)
        {
          onMessage(arrayOfByte1);
          return;
        }
        this.mMode = 2;
        this.mBuffer.write(arrayOfByte1);
        return;
      }
      if (i == 8)
      {
        int j = arrayOfByte1.length;
        int k = 0;
        if (j >= 2)
          k = 256 * arrayOfByte1[0] + arrayOfByte1[1];
        if (arrayOfByte1.length > 2);
        for (String str = encode(slice(arrayOfByte1, 2)); ; str = null)
        {
          onDisconnect(k, str);
          return;
        }
      }
      if (i == 9)
      {
        if (arrayOfByte1.length > 125)
          throw new ProtocolError("Ping payload too large");
        sendFrame(frame(arrayOfByte1, 10, -1));
        return;
      }
    }
    while (i != 10);
    encode(arrayOfByte1);
  }

  private String encode(byte[] paramArrayOfByte)
  {
    try
    {
      String str = new String(paramArrayOfByte, "UTF-8");
      return str;
    }
    catch (UnsupportedEncodingException localUnsupportedEncodingException)
    {
      throw new RuntimeException(localUnsupportedEncodingException);
    }
  }

  private byte[] frame(Object paramObject, int paramInt1, int paramInt2)
  {
    if (this.mClosed)
      return null;
    byte[] arrayOfByte1;
    int i;
    label33: int j;
    int k;
    label51: int m;
    label61: int n;
    int i1;
    label80: byte[] arrayOfByte2;
    if ((paramObject instanceof String))
    {
      arrayOfByte1 = decode((String)paramObject);
      if (paramInt2 <= 0)
        break label283;
      i = 2;
      j = i + arrayOfByte1.length;
      if (j > 125)
        break label289;
      k = 2;
      if (!this.mMasking)
        break label310;
      m = 4;
      n = k + m;
      if (!this.mMasking)
        break label316;
      i1 = 128;
      arrayOfByte2 = new byte[j + n];
      arrayOfByte2[0] = ((byte)(0xFFFFFF80 | (byte)paramInt1));
      if (j > 125)
        break label322;
      arrayOfByte2[1] = ((byte)(i1 | j));
    }
    while (true)
    {
      if (paramInt2 > 0)
      {
        arrayOfByte2[n] = ((byte)(0xFF & (int)Math.floor(paramInt2 / 256)));
        arrayOfByte2[(n + 1)] = ((byte)(paramInt2 & 0xFF));
      }
      System.arraycopy(arrayOfByte1, 0, arrayOfByte2, i + n, arrayOfByte1.length);
      if (this.mMasking)
      {
        byte[] arrayOfByte3 = new byte[4];
        arrayOfByte3[0] = ((byte)(int)Math.floor(256.0D * Math.random()));
        arrayOfByte3[1] = ((byte)(int)Math.floor(256.0D * Math.random()));
        arrayOfByte3[2] = ((byte)(int)Math.floor(256.0D * Math.random()));
        arrayOfByte3[3] = ((byte)(int)Math.floor(256.0D * Math.random()));
        System.arraycopy(arrayOfByte3, 0, arrayOfByte2, k, arrayOfByte3.length);
        mask(arrayOfByte2, arrayOfByte3, n);
      }
      return arrayOfByte2;
      arrayOfByte1 = (byte[])paramObject;
      break;
      label283: i = 0;
      break label33;
      label289: if (j <= 65535)
      {
        k = 4;
        break label51;
      }
      k = 10;
      break label51;
      label310: m = 0;
      break label61;
      label316: i1 = 0;
      break label80;
      label322: if (j <= 65535)
      {
        arrayOfByte2[1] = ((byte)(i1 | 0x7E));
        arrayOfByte2[2] = ((byte)(int)Math.floor(j / 256));
        arrayOfByte2[3] = ((byte)(j & 0xFF));
      }
      else
      {
        arrayOfByte2[1] = ((byte)(i1 | 0x7F));
        arrayOfByte2[2] = ((byte)(0xFF & (int)Math.floor(j / Math.pow(2.0D, 56.0D))));
        arrayOfByte2[3] = ((byte)(0xFF & (int)Math.floor(j / Math.pow(2.0D, 48.0D))));
        arrayOfByte2[4] = ((byte)(0xFF & (int)Math.floor(j / Math.pow(2.0D, 40.0D))));
        arrayOfByte2[5] = ((byte)(0xFF & (int)Math.floor(j / Math.pow(2.0D, 32.0D))));
        arrayOfByte2[6] = ((byte)(0xFF & (int)Math.floor(j / Math.pow(2.0D, 24.0D))));
        arrayOfByte2[7] = ((byte)(0xFF & (int)Math.floor(j / Math.pow(2.0D, 16.0D))));
        arrayOfByte2[8] = ((byte)(0xFF & (int)Math.floor(j / Math.pow(2.0D, 8.0D))));
        arrayOfByte2[9] = ((byte)(j & 0xFF));
      }
    }
  }

  private byte[] frame(String paramString, int paramInt1, int paramInt2)
  {
    return frame(paramString, paramInt1, paramInt2);
  }

  private byte[] frame(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
  {
    return frame(paramArrayOfByte, paramInt1, paramInt2);
  }

  private int getInteger(byte[] paramArrayOfByte)
    throws HybiParser.ProtocolError
  {
    long l = byteArrayToLong(paramArrayOfByte, 0, paramArrayOfByte.length);
    if ((l < 0L) || (l > 2147483647L))
      throw new ProtocolError("Bad integer: " + l);
    return (int)l;
  }

  private static byte[] mask(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2, int paramInt)
  {
    if (paramArrayOfByte2.length == 0);
    while (true)
    {
      return paramArrayOfByte1;
      for (int i = 0; i < paramArrayOfByte1.length - paramInt; i++)
        paramArrayOfByte1[(paramInt + i)] = ((byte)(paramArrayOfByte1[(paramInt + i)] ^ paramArrayOfByte2[(i % 4)]));
    }
  }

  private void parseExtendedLength(byte[] paramArrayOfByte)
    throws HybiParser.ProtocolError
  {
    this.mLength = getInteger(paramArrayOfByte);
    if (this.mMasked);
    for (int i = 3; ; i = 4)
    {
      this.mStage = i;
      return;
    }
  }

  private void parseLength(byte paramByte)
  {
    boolean bool;
    if ((paramByte & 0x80) == 128)
    {
      bool = true;
      this.mMasked = bool;
      this.mLength = (paramByte & 0x7F);
      if ((this.mLength < 0) || (this.mLength > 125))
        break label70;
      if (!this.mMasked)
        break label64;
    }
    label64: for (int j = 3; ; j = 4)
    {
      this.mStage = j;
      return;
      bool = false;
      break;
    }
    label70: if (this.mLength == 126);
    for (int i = 2; ; i = 8)
    {
      this.mLengthSize = i;
      this.mStage = 2;
      return;
    }
  }

  private void parseOpcode(byte paramByte)
    throws HybiParser.ProtocolError
  {
    int i;
    int j;
    if ((paramByte & 0x40) == 64)
    {
      i = 1;
      if ((paramByte & 0x20) != 32)
        break label63;
      j = 1;
      label22: if ((paramByte & 0x10) != 16)
        break label68;
    }
    label63: label68: for (int k = 1; ; k = 0)
    {
      if ((i == 0) && (j == 0) && (k == 0))
        break label74;
      throw new ProtocolError("RSV not zero");
      i = 0;
      break;
      j = 0;
      break label22;
    }
    label74: if ((paramByte & 0x80) == 128);
    for (boolean bool = true; ; bool = false)
    {
      this.mFinal = bool;
      this.mOpcode = (paramByte & 0xF);
      this.mMask = new byte[0];
      this.mPayload = new byte[0];
      if (OPCODES.contains(Integer.valueOf(this.mOpcode)))
        break;
      throw new ProtocolError("Bad opcode");
    }
    if ((!FRAGMENTED_OPCODES.contains(Integer.valueOf(this.mOpcode))) && (!this.mFinal))
      throw new ProtocolError("Expected non-final packet");
    this.mStage = 1;
  }

  private void reset()
  {
    this.mMode = 0;
    this.mBuffer.reset();
  }

  private byte[] slice(byte[] paramArrayOfByte, int paramInt)
  {
    byte[] arrayOfByte = new byte[paramArrayOfByte.length - paramInt];
    System.arraycopy(paramArrayOfByte, paramInt, arrayOfByte, 0, paramArrayOfByte.length - paramInt);
    return arrayOfByte;
  }

  public void close(int paramInt, String paramString)
  {
    if (this.mClosed)
      return;
    sendFrame(frame(paramString, 8, paramInt));
    this.mClosed = true;
  }

  public byte[] frame(String paramString)
  {
    return frame(paramString, 1, -1);
  }

  public byte[] frame(byte[] paramArrayOfByte)
  {
    return frame(paramArrayOfByte, 2, -1);
  }

  protected abstract void onDisconnect(int paramInt, String paramString);

  protected abstract void onMessage(String paramString);

  protected abstract void onMessage(byte[] paramArrayOfByte);

  void parse()
  {
    switch (this.mStage)
    {
    default:
      return;
    case 0:
      this.mReader.read(1, this.mStage0);
      return;
    case 1:
      this.mReader.read(1, this.mStage1);
      return;
    case 2:
      this.mReader.read(this.mLengthSize, this.mStage2);
      return;
    case 3:
      this.mReader.read(4, this.mStage3);
      return;
    case 4:
    }
    this.mReader.read(this.mLength, this.mStage4);
  }

  public void ping(String paramString)
  {
  }

  protected abstract void report(Exception paramException);

  protected abstract void sendFrame(byte[] paramArrayOfByte);

  public void setMasking(boolean paramBoolean)
  {
    this.mMasking = paramBoolean;
  }

  public static class ProtocolError extends IOException
  {
    public ProtocolError(String paramString)
    {
      super();
    }
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.async.http.HybiParser
 * JD-Core Version:    0.6.2
 */