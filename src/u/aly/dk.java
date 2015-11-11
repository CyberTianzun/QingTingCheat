package u.aly;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class dk extends dm
{
  protected InputStream a = null;
  protected OutputStream b = null;

  protected dk()
  {
  }

  public dk(InputStream paramInputStream)
  {
    this.a = paramInputStream;
  }

  public dk(InputStream paramInputStream, OutputStream paramOutputStream)
  {
    this.a = paramInputStream;
    this.b = paramOutputStream;
  }

  public dk(OutputStream paramOutputStream)
  {
    this.b = paramOutputStream;
  }

  public int a(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
    throws dn
  {
    if (this.a == null)
      throw new dn(1, "Cannot read from null inputStream");
    int i;
    try
    {
      i = this.a.read(paramArrayOfByte, paramInt1, paramInt2);
      if (i < 0)
        throw new dn(4);
    }
    catch (IOException localIOException)
    {
      throw new dn(0, localIOException);
    }
    return i;
  }

  public boolean a()
  {
    return true;
  }

  public void b()
    throws dn
  {
  }

  public void b(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
    throws dn
  {
    if (this.b == null)
      throw new dn(1, "Cannot write to null outputStream");
    try
    {
      this.b.write(paramArrayOfByte, paramInt1, paramInt2);
      return;
    }
    catch (IOException localIOException)
    {
      throw new dn(0, localIOException);
    }
  }

  public void c()
  {
    if (this.a != null);
    try
    {
      this.a.close();
      this.a = null;
      if (this.b == null);
    }
    catch (IOException localIOException2)
    {
      try
      {
        this.b.close();
        this.b = null;
        return;
        localIOException2 = localIOException2;
        localIOException2.printStackTrace();
      }
      catch (IOException localIOException1)
      {
        while (true)
          localIOException1.printStackTrace();
      }
    }
  }

  public void d()
    throws dn
  {
    if (this.b == null)
      throw new dn(1, "Cannot flush null outputStream");
    try
    {
      this.b.flush();
      return;
    }
    catch (IOException localIOException)
    {
      throw new dn(0, localIOException);
    }
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     u.aly.dk
 * JD-Core Version:    0.6.2
 */