package u.aly;

public final class dl extends dm
{
  private byte[] a;
  private int b;
  private int c;

  public dl()
  {
  }

  public dl(byte[] paramArrayOfByte)
  {
    a(paramArrayOfByte);
  }

  public dl(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
  {
    c(paramArrayOfByte, paramInt1, paramInt2);
  }

  public int a(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
    throws dn
  {
    int i = h();
    if (paramInt2 > i)
      paramInt2 = i;
    if (paramInt2 > 0)
    {
      System.arraycopy(this.a, this.b, paramArrayOfByte, paramInt1, paramInt2);
      a(paramInt2);
    }
    return paramInt2;
  }

  public void a(int paramInt)
  {
    this.b = (paramInt + this.b);
  }

  public void a(byte[] paramArrayOfByte)
  {
    c(paramArrayOfByte, 0, paramArrayOfByte.length);
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
    throw new UnsupportedOperationException("No writing allowed!");
  }

  public void c()
  {
  }

  public void c(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
  {
    this.a = paramArrayOfByte;
    this.b = paramInt1;
    this.c = (paramInt1 + paramInt2);
  }

  public void e()
  {
    this.a = null;
  }

  public byte[] f()
  {
    return this.a;
  }

  public int g()
  {
    return this.b;
  }

  public int h()
  {
    return this.c - this.b;
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     u.aly.dl
 * JD-Core Version:    0.6.2
 */