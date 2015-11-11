package com.umeng.message.proguard;

public abstract class v
{
  private static final int a = 2;
  public static final int b = 76;
  public static final int c = 64;
  protected static final int d = 255;
  protected static final byte e = 61;
  private static final int m = 8192;
  protected final byte f = 61;
  protected final int g;
  protected byte[] h;
  protected int i;
  protected boolean j;
  protected int k;
  protected int l;
  private final int n;
  private final int o;
  private final int p;
  private int q;

  protected v(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    this.n = paramInt1;
    this.o = paramInt2;
    if ((paramInt3 > 0) && (paramInt4 > 0));
    for (int i1 = paramInt2 * (paramInt3 / paramInt2); ; i1 = 0)
    {
      this.g = i1;
      this.p = paramInt4;
      return;
    }
  }

  private void a()
  {
    if (this.h == null)
    {
      this.h = new byte[d()];
      this.i = 0;
      this.q = 0;
      return;
    }
    byte[] arrayOfByte = new byte[2 * this.h.length];
    System.arraycopy(this.h, 0, arrayOfByte, 0, this.h.length);
    this.h = arrayOfByte;
  }

  protected static boolean c(byte paramByte)
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

  private void e()
  {
    this.h = null;
    this.i = 0;
    this.q = 0;
    this.k = 0;
    this.l = 0;
    this.j = false;
  }

  public Object a(Object paramObject)
    throws Exception
  {
    if (!(paramObject instanceof byte[]))
      throw new Exception("Parameter supplied to Base-N encode is not a byte[]");
    return l((byte[])paramObject);
  }

  protected void a(int paramInt)
  {
    if ((this.h == null) || (this.h.length < paramInt + this.i))
      a();
  }

  abstract void a(byte[] paramArrayOfByte, int paramInt1, int paramInt2);

  public Object b(Object paramObject)
    throws Exception
  {
    if ((paramObject instanceof byte[]))
      return k((byte[])paramObject);
    if ((paramObject instanceof String))
      return c((String)paramObject);
    throw new Exception("Parameter supplied to Base-N decode is not a byte[] or a String");
  }

  abstract void b(byte[] paramArrayOfByte, int paramInt1, int paramInt2);

  boolean b()
  {
    return this.h != null;
  }

  protected abstract boolean b(byte paramByte);

  public boolean b(byte[] paramArrayOfByte, boolean paramBoolean)
  {
    for (int i1 = 0; i1 < paramArrayOfByte.length; i1++)
      if ((!b(paramArrayOfByte[i1])) && ((!paramBoolean) || ((paramArrayOfByte[i1] != 61) && (!c(paramArrayOfByte[i1])))))
        return false;
    return true;
  }

  int c()
  {
    if (this.h != null)
      return this.i - this.q;
    return 0;
  }

  int c(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
  {
    if (this.h != null)
    {
      int i1 = Math.min(c(), paramInt2);
      System.arraycopy(this.h, this.q, paramArrayOfByte, paramInt1, i1);
      this.q = (i1 + this.q);
      if (this.q >= this.i)
        this.h = null;
      return i1;
    }
    if (this.j)
      return -1;
    return 0;
  }

  public byte[] c(String paramString)
  {
    return k(s.f(paramString));
  }

  protected int d()
  {
    return 8192;
  }

  public boolean d(String paramString)
  {
    return b(s.f(paramString), true);
  }

  public String j(byte[] paramArrayOfByte)
  {
    return s.f(l(paramArrayOfByte));
  }

  public byte[] k(byte[] paramArrayOfByte)
  {
    e();
    if ((paramArrayOfByte == null) || (paramArrayOfByte.length == 0))
      return paramArrayOfByte;
    b(paramArrayOfByte, 0, paramArrayOfByte.length);
    b(paramArrayOfByte, 0, -1);
    byte[] arrayOfByte = new byte[this.i];
    c(arrayOfByte, 0, arrayOfByte.length);
    return arrayOfByte;
  }

  public byte[] l(byte[] paramArrayOfByte)
  {
    e();
    if ((paramArrayOfByte == null) || (paramArrayOfByte.length == 0))
      return paramArrayOfByte;
    a(paramArrayOfByte, 0, paramArrayOfByte.length);
    a(paramArrayOfByte, 0, -1);
    byte[] arrayOfByte = new byte[this.i - this.q];
    c(arrayOfByte, 0, arrayOfByte.length);
    return arrayOfByte;
  }

  public String m(byte[] paramArrayOfByte)
  {
    return s.f(l(paramArrayOfByte));
  }

  protected boolean n(byte[] paramArrayOfByte)
  {
    if (paramArrayOfByte == null);
    while (true)
    {
      return false;
      for (int i1 = 0; i1 < paramArrayOfByte.length; i1++)
        if ((61 == paramArrayOfByte[i1]) || (b(paramArrayOfByte[i1])))
          return true;
    }
  }

  public long o(byte[] paramArrayOfByte)
  {
    long l1 = (-1 + (paramArrayOfByte.length + this.n)) / this.n * this.o;
    if (this.g > 0)
      l1 += (l1 + this.g - 1L) / this.g * this.p;
    return l1;
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.umeng.message.proguard.v
 * JD-Core Version:    0.6.2
 */