package u.aly;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;

public class cs extends cy
{
  private static final dd d = new dd("");
  private static final ct e = new ct("", (byte)0, (short)0);
  private static final byte[] f = new byte[16];
  private static final byte h = -126;
  private static final byte i = 1;
  private static final byte j = 31;
  private static final byte k = -32;
  private static final int l = 5;
  byte[] a = new byte[5];
  byte[] b = new byte[10];
  byte[] c = new byte[1];
  private bx m = new bx(15);
  private short n = 0;
  private ct o = null;
  private Boolean p = null;
  private final long q;
  private byte[] r = new byte[1];

  static
  {
    f[0] = 0;
    f[2] = 1;
    f[3] = 3;
    f[6] = 4;
    f[8] = 5;
    f[10] = 6;
    f[4] = 7;
    f[11] = 8;
    f[15] = 9;
    f[14] = 10;
    f[13] = 11;
    f[12] = 12;
  }

  public cs(dm paramdm)
  {
    this(paramdm, -1L);
  }

  public cs(dm paramdm, long paramLong)
  {
    super(paramdm);
    this.q = paramLong;
  }

  private int E()
    throws cf
  {
    int i1 = 0;
    int i2;
    if (this.g.h() >= 5)
    {
      byte[] arrayOfByte = this.g.f();
      int i5 = this.g.g();
      int i6 = 0;
      int i7 = 0;
      while (true)
      {
        int i8 = arrayOfByte[(i5 + i1)];
        i7 |= (i8 & 0x7F) << i6;
        if ((i8 & 0x80) != 128)
        {
          this.g.a(i1 + 1);
          return i7;
        }
        i6 += 7;
        i1++;
      }
      i2 += 7;
    }
    while (true)
    {
      int i4 = u();
      i3 |= (i4 & 0x7F) << i2;
      if ((i4 & 0x80) == 128)
        break;
      return i3;
      i2 = 0;
      int i3 = 0;
    }
  }

  private long F()
    throws cf
  {
    long l1 = 0L;
    int i1 = this.g.h();
    int i2 = 0;
    if (i1 >= 10)
    {
      byte[] arrayOfByte = this.g.f();
      int i4 = this.g.g();
      int i5 = 0;
      while (true)
      {
        int i6 = arrayOfByte[(i4 + i2)];
        l1 |= (i6 & 0x7F) << i5;
        if ((i6 & 0x80) != 128)
        {
          this.g.a(i2 + 1);
          return l1;
        }
        i5 += 7;
        i2++;
      }
    }
    int i3;
    do
    {
      i2 += 7;
      i3 = u();
      l1 |= (i3 & 0x7F) << i2;
    }
    while ((i3 & 0x80) == 128);
    return l1;
  }

  private long a(byte[] paramArrayOfByte)
  {
    return (0xFF & paramArrayOfByte[7]) << 56 | (0xFF & paramArrayOfByte[6]) << 48 | (0xFF & paramArrayOfByte[5]) << 40 | (0xFF & paramArrayOfByte[4]) << 32 | (0xFF & paramArrayOfByte[3]) << 24 | (0xFF & paramArrayOfByte[2]) << 16 | (0xFF & paramArrayOfByte[1]) << 8 | 0xFF & paramArrayOfByte[0];
  }

  private void a(long paramLong, byte[] paramArrayOfByte, int paramInt)
  {
    paramArrayOfByte[(paramInt + 0)] = ((byte)(int)(paramLong & 0xFF));
    paramArrayOfByte[(paramInt + 1)] = ((byte)(int)(0xFF & paramLong >> 8));
    paramArrayOfByte[(paramInt + 2)] = ((byte)(int)(0xFF & paramLong >> 16));
    paramArrayOfByte[(paramInt + 3)] = ((byte)(int)(0xFF & paramLong >> 24));
    paramArrayOfByte[(paramInt + 4)] = ((byte)(int)(0xFF & paramLong >> 32));
    paramArrayOfByte[(paramInt + 5)] = ((byte)(int)(0xFF & paramLong >> 40));
    paramArrayOfByte[(paramInt + 6)] = ((byte)(int)(0xFF & paramLong >> 48));
    paramArrayOfByte[(paramInt + 7)] = ((byte)(int)(0xFF & paramLong >> 56));
  }

  private void a(ct paramct, byte paramByte)
    throws cf
  {
    if (paramByte == -1)
      paramByte = e(paramct.b);
    if ((paramct.c > this.n) && (paramct.c - this.n <= 15))
      d(paramByte | paramct.c - this.n << 4);
    while (true)
    {
      this.n = paramct.c;
      return;
      b(paramByte);
      a(paramct.c);
    }
  }

  private void a(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
    throws cf
  {
    b(paramInt2);
    this.g.b(paramArrayOfByte, paramInt1, paramInt2);
  }

  private void b(byte paramByte)
    throws cf
  {
    this.r[0] = paramByte;
    this.g.b(this.r);
  }

  private void b(int paramInt)
    throws cf
  {
    int i2;
    for (int i1 = 0; ; i1 = i2)
    {
      if ((paramInt & 0xFFFFFF80) == 0)
      {
        byte[] arrayOfByte2 = this.a;
        int i3 = i1 + 1;
        arrayOfByte2[i1] = ((byte)paramInt);
        this.g.b(this.a, 0, i3);
        return;
      }
      byte[] arrayOfByte1 = this.a;
      i2 = i1 + 1;
      arrayOfByte1[i1] = ((byte)(0x80 | paramInt & 0x7F));
      paramInt >>>= 7;
    }
  }

  private void b(long paramLong)
    throws cf
  {
    int i2;
    for (int i1 = 0; ; i1 = i2)
    {
      if ((0xFFFFFF80 & paramLong) == 0L)
      {
        byte[] arrayOfByte2 = this.b;
        int i3 = i1 + 1;
        arrayOfByte2[i1] = ((byte)(int)paramLong);
        this.g.b(this.b, 0, i3);
        return;
      }
      byte[] arrayOfByte1 = this.b;
      i2 = i1 + 1;
      arrayOfByte1[i1] = ((byte)(int)(0x80 | 0x7F & paramLong));
      paramLong >>>= 7;
    }
  }

  private int c(int paramInt)
  {
    return paramInt << 1 ^ paramInt >> 31;
  }

  private long c(long paramLong)
  {
    return paramLong << 1 ^ paramLong >> 63;
  }

  private boolean c(byte paramByte)
  {
    int i1 = paramByte & 0xF;
    return (i1 == 1) || (i1 == 2);
  }

  private byte d(byte paramByte)
    throws cz
  {
    switch ((byte)(paramByte & 0xF))
    {
    default:
      throw new cz("don't know what type: " + (byte)(paramByte & 0xF));
    case 0:
      return 0;
    case 1:
    case 2:
      return 2;
    case 3:
      return 3;
    case 4:
      return 6;
    case 5:
      return 8;
    case 6:
      return 10;
    case 7:
      return 4;
    case 8:
      return 11;
    case 9:
      return 15;
    case 10:
      return 14;
    case 11:
      return 13;
    case 12:
    }
    return 12;
  }

  private long d(long paramLong)
  {
    return paramLong >>> 1 ^ -(1L & paramLong);
  }

  private void d(int paramInt)
    throws cf
  {
    b((byte)paramInt);
  }

  private byte e(byte paramByte)
  {
    return f[paramByte];
  }

  private byte[] e(int paramInt)
    throws cf
  {
    if (paramInt == 0)
      return new byte[0];
    byte[] arrayOfByte = new byte[paramInt];
    this.g.d(arrayOfByte, 0, paramInt);
    return arrayOfByte;
  }

  private void f(int paramInt)
    throws cz
  {
    if (paramInt < 0)
      throw new cz("Negative length: " + paramInt);
    if ((this.q != -1L) && (paramInt > this.q))
      throw new cz("Length exceeded max allowed: " + paramInt);
  }

  private int g(int paramInt)
  {
    return paramInt >>> 1 ^ -(paramInt & 0x1);
  }

  public ByteBuffer A()
    throws cf
  {
    int i1 = E();
    f(i1);
    if (i1 == 0)
      return ByteBuffer.wrap(new byte[0]);
    byte[] arrayOfByte = new byte[i1];
    this.g.d(arrayOfByte, 0, i1);
    return ByteBuffer.wrap(arrayOfByte);
  }

  public void B()
  {
    this.m.c();
    this.n = 0;
  }

  public void a()
    throws cf
  {
  }

  public void a(byte paramByte)
    throws cf
  {
    b(paramByte);
  }

  protected void a(byte paramByte, int paramInt)
    throws cf
  {
    if (paramInt <= 14)
    {
      d(paramInt << 4 | e(paramByte));
      return;
    }
    d(0xF0 | e(paramByte));
    b(paramInt);
  }

  public void a(double paramDouble)
    throws cf
  {
    byte[] arrayOfByte = { 0, 0, 0, 0, 0, 0, 0, 0 };
    a(Double.doubleToLongBits(paramDouble), arrayOfByte, 0);
    this.g.b(arrayOfByte);
  }

  public void a(int paramInt)
    throws cf
  {
    b(c(paramInt));
  }

  public void a(long paramLong)
    throws cf
  {
    b(c(paramLong));
  }

  public void a(String paramString)
    throws cf
  {
    try
    {
      byte[] arrayOfByte = paramString.getBytes("UTF-8");
      a(arrayOfByte, 0, arrayOfByte.length);
      return;
    }
    catch (UnsupportedEncodingException localUnsupportedEncodingException)
    {
    }
    throw new cf("UTF-8 not supported!");
  }

  public void a(ByteBuffer paramByteBuffer)
    throws cf
  {
    int i1 = paramByteBuffer.limit() - paramByteBuffer.position();
    a(paramByteBuffer.array(), paramByteBuffer.position() + paramByteBuffer.arrayOffset(), i1);
  }

  public void a(ct paramct)
    throws cf
  {
    if (paramct.b == 2)
    {
      this.o = paramct;
      return;
    }
    a(paramct, (byte)-1);
  }

  public void a(cu paramcu)
    throws cf
  {
    a(paramcu.a, paramcu.b);
  }

  public void a(cv paramcv)
    throws cf
  {
    if (paramcv.c == 0)
    {
      d(0);
      return;
    }
    b(paramcv.c);
    d(e(paramcv.a) << 4 | e(paramcv.b));
  }

  public void a(cw paramcw)
    throws cf
  {
    b((byte)-126);
    d(0x1 | 0xFFFFFFE0 & paramcw.b << 5);
    b(paramcw.c);
    a(paramcw.a);
  }

  public void a(dc paramdc)
    throws cf
  {
    a(paramdc.a, paramdc.b);
  }

  public void a(dd paramdd)
    throws cf
  {
    this.m.a(this.n);
    this.n = 0;
  }

  public void a(short paramShort)
    throws cf
  {
    b(c(paramShort));
  }

  public void a(boolean paramBoolean)
    throws cf
  {
    byte b1 = 1;
    if (this.o != null)
    {
      ct localct = this.o;
      if (paramBoolean);
      while (true)
      {
        a(localct, b1);
        this.o = null;
        return;
        b1 = 2;
      }
    }
    if (paramBoolean);
    while (true)
    {
      b(b1);
      return;
      b1 = 2;
    }
  }

  public void b()
    throws cf
  {
    this.n = this.m.a();
  }

  public void c()
    throws cf
  {
  }

  public void d()
    throws cf
  {
    b((byte)0);
  }

  public void e()
    throws cf
  {
  }

  public void f()
    throws cf
  {
  }

  public void g()
    throws cf
  {
  }

  public cw h()
    throws cf
  {
    int i1 = u();
    if (i1 != -126)
      throw new cz("Expected protocol id " + Integer.toHexString(-126) + " but got " + Integer.toHexString(i1));
    int i2 = u();
    int i3 = (byte)(i2 & 0x1F);
    if (i3 != 1)
      throw new cz("Expected version 1 but got " + i3);
    byte b1 = (byte)(0x3 & i2 >> 5);
    int i4 = E();
    return new cw(z(), b1, i4);
  }

  public void i()
    throws cf
  {
  }

  public dd j()
    throws cf
  {
    this.m.a(this.n);
    this.n = 0;
    return d;
  }

  public void k()
    throws cf
  {
    this.n = this.m.a();
  }

  public ct l()
    throws cf
  {
    int i1 = u();
    if (i1 == 0)
      return e;
    int i2 = (short)((i1 & 0xF0) >> 4);
    short s;
    ct localct;
    if (i2 == 0)
    {
      s = v();
      localct = new ct("", d((byte)(i1 & 0xF)), s);
      if (c(i1))
        if ((byte)(i1 & 0xF) != 1)
          break label103;
    }
    label103: for (Boolean localBoolean = Boolean.TRUE; ; localBoolean = Boolean.FALSE)
    {
      this.p = localBoolean;
      this.n = localct.c;
      return localct;
      s = (short)(i2 + this.n);
      break;
    }
  }

  public void m()
    throws cf
  {
  }

  public cv n()
    throws cf
  {
    int i1 = E();
    if (i1 == 0);
    for (int i2 = 0; ; i2 = u())
      return new cv(d((byte)(i2 >> 4)), d((byte)(i2 & 0xF)), i1);
  }

  public void o()
    throws cf
  {
  }

  public cu p()
    throws cf
  {
    byte b1 = u();
    int i1 = 0xF & b1 >> 4;
    if (i1 == 15)
      i1 = E();
    return new cu(d(b1), i1);
  }

  public void q()
    throws cf
  {
  }

  public dc r()
    throws cf
  {
    return new dc(p());
  }

  public void s()
    throws cf
  {
  }

  public boolean t()
    throws cf
  {
    int i1 = 1;
    if (this.p != null)
    {
      i1 = this.p.booleanValue();
      this.p = null;
    }
    while (u() == i1)
      return i1;
    return false;
  }

  public byte u()
    throws cf
  {
    if (this.g.h() > 0)
    {
      byte b1 = this.g.f()[this.g.g()];
      this.g.a(1);
      return b1;
    }
    this.g.d(this.c, 0, 1);
    return this.c[0];
  }

  public short v()
    throws cf
  {
    return (short)g(E());
  }

  public int w()
    throws cf
  {
    return g(E());
  }

  public long x()
    throws cf
  {
    return d(F());
  }

  public double y()
    throws cf
  {
    byte[] arrayOfByte = new byte[8];
    this.g.d(arrayOfByte, 0, 8);
    return Double.longBitsToDouble(a(arrayOfByte));
  }

  public String z()
    throws cf
  {
    int i1 = E();
    f(i1);
    if (i1 == 0)
      return "";
    try
    {
      if (this.g.h() >= i1)
      {
        String str1 = new String(this.g.f(), this.g.g(), i1, "UTF-8");
        this.g.a(i1);
        return str1;
      }
    }
    catch (UnsupportedEncodingException localUnsupportedEncodingException)
    {
      throw new cf("UTF-8 not supported!");
    }
    String str2 = new String(e(i1), "UTF-8");
    return str2;
  }

  public static class a
    implements da
  {
    private final long a;

    public a()
    {
      this.a = -1L;
    }

    public a(int paramInt)
    {
      this.a = paramInt;
    }

    public cy a(dm paramdm)
    {
      return new cs(paramdm, this.a);
    }
  }

  private static class b
  {
    public static final byte a = 1;
    public static final byte b = 2;
    public static final byte c = 3;
    public static final byte d = 4;
    public static final byte e = 5;
    public static final byte f = 6;
    public static final byte g = 7;
    public static final byte h = 8;
    public static final byte i = 9;
    public static final byte j = 10;
    public static final byte k = 11;
    public static final byte l = 12;
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     u.aly.cs
 * JD-Core Version:    0.6.2
 */