package com.taobao.munion.base.volley.a;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class j extends ByteArrayOutputStream
{
  private static final int a = 256;
  private final b b;

  public j(b paramb)
  {
    this(paramb, 256);
  }

  public j(b paramb, int paramInt)
  {
    this.b = paramb;
    this.buf = this.b.a(Math.max(paramInt, 256));
  }

  private void a(int paramInt)
  {
    if (paramInt + this.count <= this.buf.length)
      return;
    byte[] arrayOfByte = this.b.a(2 * (paramInt + this.count));
    System.arraycopy(this.buf, 0, arrayOfByte, 0, this.count);
    this.b.a(this.buf);
    this.buf = arrayOfByte;
  }

  public void close()
    throws IOException
  {
    this.b.a(this.buf);
    this.buf = null;
    super.close();
  }

  public void finalize()
  {
    this.b.a(this.buf);
  }

  public void write(int paramInt)
  {
    try
    {
      a(1);
      super.write(paramInt);
      return;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }

  public void write(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
  {
    try
    {
      a(paramInt2);
      super.write(paramArrayOfByte, paramInt1, paramInt2);
      return;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.taobao.munion.base.volley.a.j
 * JD-Core Version:    0.6.2
 */