package u.aly;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;

public class cc
{
  private final cy a;
  private final dl b = new dl();

  public cc()
  {
    this(new cs.a());
  }

  public cc(da paramda)
  {
    this.a = paramda.a(this.b);
  }

  private Object a(byte paramByte, byte[] paramArrayOfByte, cg paramcg, cg[] paramArrayOfcg)
    throws cf
  {
    try
    {
      ct localct = j(paramArrayOfByte, paramcg, paramArrayOfcg);
      if (localct != null)
        switch (paramByte)
        {
        default:
        case 2:
        case 3:
        case 4:
        case 6:
        case 8:
        case 10:
        case 11:
        case 100:
        }
      do
      {
        do
        {
          do
          {
            do
            {
              do
              {
                do
                {
                  do
                  {
                    do
                      return null;
                    while (localct.b != 2);
                    Boolean localBoolean = Boolean.valueOf(this.a.t());
                    return localBoolean;
                  }
                  while (localct.b != 3);
                  Byte localByte = Byte.valueOf(this.a.u());
                  return localByte;
                }
                while (localct.b != 4);
                Double localDouble = Double.valueOf(this.a.y());
                return localDouble;
              }
              while (localct.b != 6);
              Short localShort = Short.valueOf(this.a.v());
              return localShort;
            }
            while (localct.b != 8);
            Integer localInteger = Integer.valueOf(this.a.w());
            return localInteger;
          }
          while (localct.b != 10);
          Long localLong = Long.valueOf(this.a.x());
          return localLong;
        }
        while (localct.b != 11);
        String str = this.a.z();
        return str;
      }
      while (localct.b != 11);
      ByteBuffer localByteBuffer = this.a.A();
      return localByteBuffer;
    }
    catch (Exception localException)
    {
      throw new cf(localException);
    }
    finally
    {
      this.b.e();
      this.a.B();
    }
  }

  private ct j(byte[] paramArrayOfByte, cg paramcg, cg[] paramArrayOfcg)
    throws cf
  {
    int i = 0;
    this.b.a(paramArrayOfByte);
    cg[] arrayOfcg = new cg[1 + paramArrayOfcg.length];
    arrayOfcg[0] = paramcg;
    for (int j = 0; j < paramArrayOfcg.length; j++)
      arrayOfcg[(j + 1)] = paramArrayOfcg[j];
    this.a.j();
    ct localct = null;
    while (i < arrayOfcg.length)
    {
      localct = this.a.l();
      if ((localct.b == 0) || (localct.c > arrayOfcg[i].a()))
        return null;
      if (localct.c != arrayOfcg[i].a())
      {
        db.a(this.a, localct.b);
        this.a.m();
      }
      else
      {
        i++;
        if (i < arrayOfcg.length)
          this.a.j();
      }
    }
    return localct;
  }

  public Boolean a(byte[] paramArrayOfByte, cg paramcg, cg[] paramArrayOfcg)
    throws cf
  {
    return (Boolean)a((byte)2, paramArrayOfByte, paramcg, paramArrayOfcg);
  }

  public void a(bz parambz, String paramString)
    throws cf
  {
    a(parambz, paramString.getBytes());
  }

  public void a(bz parambz, String paramString1, String paramString2)
    throws cf
  {
    try
    {
      a(parambz, paramString1.getBytes(paramString2));
      return;
    }
    catch (UnsupportedEncodingException localUnsupportedEncodingException)
    {
      throw new cf("JVM DOES NOT SUPPORT ENCODING: " + paramString2);
    }
    finally
    {
      this.a.B();
    }
  }

  public void a(bz parambz, byte[] paramArrayOfByte)
    throws cf
  {
    try
    {
      this.b.a(paramArrayOfByte);
      parambz.a(this.a);
      return;
    }
    finally
    {
      this.b.e();
      this.a.B();
    }
  }

  public void a(bz parambz, byte[] paramArrayOfByte, cg paramcg, cg[] paramArrayOfcg)
    throws cf
  {
    try
    {
      if (j(paramArrayOfByte, paramcg, paramArrayOfcg) != null)
        parambz.a(this.a);
      return;
    }
    catch (Exception localException)
    {
      throw new cf(localException);
    }
    finally
    {
      this.b.e();
      this.a.B();
    }
  }

  public Byte b(byte[] paramArrayOfByte, cg paramcg, cg[] paramArrayOfcg)
    throws cf
  {
    return (Byte)a((byte)3, paramArrayOfByte, paramcg, paramArrayOfcg);
  }

  public Double c(byte[] paramArrayOfByte, cg paramcg, cg[] paramArrayOfcg)
    throws cf
  {
    return (Double)a((byte)4, paramArrayOfByte, paramcg, paramArrayOfcg);
  }

  public Short d(byte[] paramArrayOfByte, cg paramcg, cg[] paramArrayOfcg)
    throws cf
  {
    return (Short)a((byte)6, paramArrayOfByte, paramcg, paramArrayOfcg);
  }

  public Integer e(byte[] paramArrayOfByte, cg paramcg, cg[] paramArrayOfcg)
    throws cf
  {
    return (Integer)a((byte)8, paramArrayOfByte, paramcg, paramArrayOfcg);
  }

  public Long f(byte[] paramArrayOfByte, cg paramcg, cg[] paramArrayOfcg)
    throws cf
  {
    return (Long)a((byte)10, paramArrayOfByte, paramcg, paramArrayOfcg);
  }

  public String g(byte[] paramArrayOfByte, cg paramcg, cg[] paramArrayOfcg)
    throws cf
  {
    return (String)a((byte)11, paramArrayOfByte, paramcg, paramArrayOfcg);
  }

  public ByteBuffer h(byte[] paramArrayOfByte, cg paramcg, cg[] paramArrayOfcg)
    throws cf
  {
    return (ByteBuffer)a((byte)100, paramArrayOfByte, paramcg, paramArrayOfcg);
  }

  public Short i(byte[] paramArrayOfByte, cg paramcg, cg[] paramArrayOfcg)
    throws cf
  {
    try
    {
      if (j(paramArrayOfByte, paramcg, paramArrayOfcg) != null)
      {
        this.a.j();
        Short localShort = Short.valueOf(this.a.l().c);
        return localShort;
      }
      return null;
    }
    catch (Exception localException)
    {
      throw new cf(localException);
    }
    finally
    {
      this.b.e();
      this.a.B();
    }
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     u.aly.cc
 * JD-Core Version:    0.6.2
 */