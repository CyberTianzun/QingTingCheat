package org.apache.commons.httpclient;

import java.io.IOException;
import java.io.InputStream;

public class ContentLengthInputStream extends InputStream
{
  private boolean closed = false;
  private long contentLength;
  private long pos = 0L;
  private InputStream wrappedStream = null;

  public ContentLengthInputStream(InputStream paramInputStream, int paramInt)
  {
    this(paramInputStream, paramInt);
  }

  public ContentLengthInputStream(InputStream paramInputStream, long paramLong)
  {
    this.wrappedStream = paramInputStream;
    this.contentLength = paramLong;
  }

  public void close()
    throws IOException
  {
    if (!this.closed);
    try
    {
      ChunkedInputStream.exhaustInputStream(this);
      return;
    }
    finally
    {
      this.closed = true;
    }
  }

  public int read()
    throws IOException
  {
    if (this.closed)
      throw new IOException("Attempted read from closed stream.");
    if (this.pos >= this.contentLength)
      return -1;
    this.pos = (1L + this.pos);
    return this.wrappedStream.read();
  }

  public int read(byte[] paramArrayOfByte)
    throws IOException
  {
    return read(paramArrayOfByte, 0, paramArrayOfByte.length);
  }

  public int read(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
    throws IOException
  {
    if (this.closed)
      throw new IOException("Attempted read from closed stream.");
    if (this.pos >= this.contentLength)
      return -1;
    if (this.pos + paramInt2 > this.contentLength)
      paramInt2 = (int)(this.contentLength - this.pos);
    int i = this.wrappedStream.read(paramArrayOfByte, paramInt1, paramInt2);
    this.pos += i;
    return i;
  }

  public long skip(long paramLong)
    throws IOException
  {
    long l1 = Math.min(paramLong, this.contentLength - this.pos);
    long l2 = this.wrappedStream.skip(l1);
    if (l2 > 0L)
      this.pos = (l2 + this.pos);
    return l2;
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     org.apache.commons.httpclient.ContentLengthInputStream
 * JD-Core Version:    0.6.2
 */