package fm.qingting.async.http.filter;

import fm.qingting.async.ByteBufferList;
import fm.qingting.async.DataEmitter;
import fm.qingting.async.NullDataCallback;
import fm.qingting.async.PushParser;
import fm.qingting.async.TapCallback;
import fm.qingting.async.callback.DataCallback;
import fm.qingting.async.http.libcore.Memory;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.zip.CRC32;
import java.util.zip.Inflater;

public class GZIPInputFilter extends InflaterInputFilter
{
  private static final int FCOMMENT = 16;
  private static final int FEXTRA = 4;
  private static final int FHCRC = 2;
  private static final int FNAME = 8;
  protected CRC32 crc = new CRC32();
  boolean mNeedsHeader = true;

  public GZIPInputFilter()
  {
    super(new Inflater(true));
  }

  public static int unsignedToBytes(byte paramByte)
  {
    return paramByte & 0xFF;
  }

  public void onDataAvailable(final DataEmitter paramDataEmitter, ByteBufferList paramByteBufferList)
  {
    if (this.mNeedsHeader)
    {
      final PushParser localPushParser = new PushParser(paramDataEmitter);
      localPushParser.readBuffer(10).tap(new TapCallback()
      {
        int flags;
        boolean hcrc;

        private void next()
        {
          PushParser localPushParser = new PushParser(paramDataEmitter);
          DataCallback local2 = new DataCallback()
          {
            public void onDataAvailable(DataEmitter paramAnonymous2DataEmitter, ByteBufferList paramAnonymous2ByteBufferList)
            {
              if (GZIPInputFilter.1.this.hcrc)
                while (paramAnonymous2ByteBufferList.size() > 0)
                {
                  ByteBuffer localByteBuffer = paramAnonymous2ByteBufferList.remove();
                  GZIPInputFilter.this.crc.update(localByteBuffer.array(), localByteBuffer.arrayOffset() + localByteBuffer.position(), localByteBuffer.remaining());
                }
            }
          };
          if ((0x8 & this.flags) != 0)
            localPushParser.until((byte)0, local2);
          if ((0x10 & this.flags) != 0)
            localPushParser.until((byte)0, local2);
          if (this.hcrc)
            localPushParser.readBuffer(2);
          while (true)
          {
            localPushParser.tap(new TapCallback()
            {
              public void tap(byte[] paramAnonymous2ArrayOfByte)
              {
                if (paramAnonymous2ArrayOfByte != null)
                {
                  int i = Memory.peekShort(paramAnonymous2ArrayOfByte, 0, ByteOrder.LITTLE_ENDIAN);
                  if ((short)(int)GZIPInputFilter.this.crc.getValue() != i)
                  {
                    GZIPInputFilter.this.report(new IOException("CRC mismatch"));
                    return;
                  }
                  GZIPInputFilter.this.crc.reset();
                }
                GZIPInputFilter.this.mNeedsHeader = false;
                GZIPInputFilter.this.setDataEmitter(GZIPInputFilter.1.this.val$emitter);
              }
            });
            return;
            localPushParser.noop();
          }
        }

        public void tap(byte[] paramAnonymousArrayOfByte)
        {
          boolean bool = true;
          short s = Memory.peekShort(paramAnonymousArrayOfByte, 0, ByteOrder.LITTLE_ENDIAN);
          if (s != -29921)
          {
            GZIPInputFilter localGZIPInputFilter = GZIPInputFilter.this;
            Object[] arrayOfObject = new Object[bool];
            arrayOfObject[0] = Short.valueOf(s);
            localGZIPInputFilter.report(new IOException(String.format("unknown format (magic number %x)", arrayOfObject)));
            paramDataEmitter.setDataCallback(new NullDataCallback());
            return;
          }
          this.flags = paramAnonymousArrayOfByte[3];
          if ((0x2 & this.flags) != 0);
          while (true)
          {
            this.hcrc = bool;
            if (this.hcrc)
              GZIPInputFilter.this.crc.update(paramAnonymousArrayOfByte, 0, paramAnonymousArrayOfByte.length);
            if ((0x4 & this.flags) != 0)
              localPushParser.readBuffer(2).tap(new TapCallback()
              {
                public void tap(byte[] paramAnonymous2ArrayOfByte)
                {
                  if (GZIPInputFilter.1.this.hcrc)
                    GZIPInputFilter.this.crc.update(paramAnonymous2ArrayOfByte, 0, 2);
                  int i = 0xFFFF & Memory.peekShort(paramAnonymous2ArrayOfByte, 0, ByteOrder.LITTLE_ENDIAN);
                  GZIPInputFilter.1.this.val$parser.readBuffer(i).tap(new TapCallback()
                  {
                    public void tap(byte[] paramAnonymous3ArrayOfByte)
                    {
                      if (GZIPInputFilter.1.this.hcrc)
                        GZIPInputFilter.this.crc.update(paramAnonymous3ArrayOfByte, 0, paramAnonymous3ArrayOfByte.length);
                      GZIPInputFilter.1.this.next();
                    }
                  });
                }
              });
            next();
            return;
            bool = false;
          }
        }
      });
      return;
    }
    super.onDataAvailable(paramDataEmitter, paramByteBufferList);
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.async.http.filter.GZIPInputFilter
 * JD-Core Version:    0.6.2
 */