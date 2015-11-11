package org.apache.commons.httpclient;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.httpclient.util.EncodingUtil;
import org.apache.commons.httpclient.util.ExceptionUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ChunkedInputStream extends InputStream
{
  private static final Log LOG;
  static Class class$org$apache$commons$httpclient$ChunkedInputStream;
  private boolean bof = true;
  private int chunkSize;
  private boolean closed = false;
  private boolean eof = false;
  private InputStream in;
  private HttpMethod method = null;
  private int pos;

  static
  {
    Class localClass;
    if (class$org$apache$commons$httpclient$ChunkedInputStream == null)
    {
      localClass = class$("org.apache.commons.httpclient.ChunkedInputStream");
      class$org$apache$commons$httpclient$ChunkedInputStream = localClass;
    }
    while (true)
    {
      LOG = LogFactory.getLog(localClass);
      return;
      localClass = class$org$apache$commons$httpclient$ChunkedInputStream;
    }
  }

  public ChunkedInputStream(InputStream paramInputStream)
    throws IOException
  {
    this(paramInputStream, null);
  }

  public ChunkedInputStream(InputStream paramInputStream, HttpMethod paramHttpMethod)
    throws IOException
  {
    if (paramInputStream == null)
      throw new IllegalArgumentException("InputStream parameter may not be null");
    this.in = paramInputStream;
    this.method = paramHttpMethod;
    this.pos = 0;
  }

  static Class class$(String paramString)
  {
    try
    {
      Class localClass = Class.forName(paramString);
      return localClass;
    }
    catch (ClassNotFoundException localClassNotFoundException)
    {
      throw new NoClassDefFoundError(localClassNotFoundException.getMessage());
    }
  }

  static void exhaustInputStream(InputStream paramInputStream)
    throws IOException
  {
    byte[] arrayOfByte = new byte[1024];
    while (paramInputStream.read(arrayOfByte) >= 0);
  }

  private static int getChunkSizeFromInputStream(InputStream paramInputStream)
    throws IOException
  {
    ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream();
    int i = 0;
    String str2;
    while (true)
    {
      String str1;
      if (i == -1)
      {
        str1 = EncodingUtil.getAsciiString(localByteArrayOutputStream.toByteArray());
        int k = str1.indexOf(';');
        if (k <= 0)
          break label240;
        str2 = str1.substring(0, k).trim();
      }
      try
      {
        while (true)
        {
          int m = Integer.parseInt(str2.trim(), 16);
          return m;
          int j = paramInputStream.read();
          if (j == -1)
            throw new IOException("chunked stream ended unexpectedly");
          switch (i)
          {
          default:
            throw new RuntimeException("assertion failed");
          case 0:
            switch (j)
            {
            default:
            case 13:
            case 34:
            }
            while (true)
            {
              localByteArrayOutputStream.write(j);
              break;
              i = 1;
              break;
              i = 2;
            }
          case 1:
            if (j == 10)
            {
              i = -1;
              break;
            }
            throw new IOException("Protocol violation: Unexpected single newline character in chunk size");
          case 2:
            switch (j)
            {
            default:
            case 92:
            case 34:
            }
            while (true)
            {
              localByteArrayOutputStream.write(j);
              break;
              localByteArrayOutputStream.write(paramInputStream.read());
              break;
              i = 0;
            }
            label240: str2 = str1.trim();
          }
        }
      }
      catch (NumberFormatException localNumberFormatException)
      {
      }
    }
    throw new IOException("Bad chunk size: " + str2);
  }

  private void nextChunk()
    throws IOException
  {
    if (!this.bof)
      readCRLF();
    this.chunkSize = getChunkSizeFromInputStream(this.in);
    this.bof = false;
    this.pos = 0;
    if (this.chunkSize == 0)
    {
      this.eof = true;
      parseTrailerHeaders();
    }
  }

  private void parseTrailerHeaders()
    throws IOException
  {
    String str = "US-ASCII";
    while (true)
    {
      Header[] arrayOfHeader;
      int i;
      try
      {
        if (this.method != null)
          str = this.method.getParams().getHttpElementCharset();
        arrayOfHeader = HttpParser.parseHeaders(this.in, str);
        if (this.method != null)
        {
          i = 0;
          if (i < arrayOfHeader.length);
        }
        else
        {
          return;
        }
      }
      catch (HttpException localHttpException)
      {
        LOG.error("Error parsing trailer headers", localHttpException);
        IOException localIOException = new IOException(localHttpException.getMessage());
        ExceptionUtil.initCause(localIOException, localHttpException);
        throw localIOException;
      }
      this.method.addResponseFooter(arrayOfHeader[i]);
      i++;
    }
  }

  private void readCRLF()
    throws IOException
  {
    int i = this.in.read();
    int j = this.in.read();
    if ((i != 13) || (j != 10))
      throw new IOException("CRLF expected at end of chunk: " + i + "/" + j);
  }

  public void close()
    throws IOException
  {
    if (!this.closed);
    try
    {
      if (!this.eof)
        exhaustInputStream(this);
      return;
    }
    finally
    {
      this.eof = true;
      this.closed = true;
    }
  }

  public int read()
    throws IOException
  {
    if (this.closed)
      throw new IOException("Attempted read from closed stream.");
    if (this.eof);
    do
    {
      return -1;
      if (this.pos < this.chunkSize)
        break;
      nextChunk();
    }
    while (this.eof);
    this.pos = (1 + this.pos);
    return this.in.read();
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
    if (this.eof);
    do
    {
      return -1;
      if (this.pos < this.chunkSize)
        break;
      nextChunk();
    }
    while (this.eof);
    int i = Math.min(paramInt2, this.chunkSize - this.pos);
    int j = this.in.read(paramArrayOfByte, paramInt1, i);
    this.pos = (j + this.pos);
    return j;
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     org.apache.commons.httpclient.ChunkedInputStream
 * JD-Core Version:    0.6.2
 */