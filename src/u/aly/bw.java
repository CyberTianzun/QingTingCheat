package u.aly;

public class bw
{
  public static final byte a(byte paramByte, int paramInt, boolean paramBoolean)
  {
    return (byte)a(paramByte, paramInt, paramBoolean);
  }

  public static final int a(int paramInt1, int paramInt2, boolean paramBoolean)
  {
    if (paramBoolean)
      return paramInt1 | 1 << paramInt2;
    return b(paramInt1, paramInt2);
  }

  public static final int a(byte[] paramArrayOfByte)
  {
    return a(paramArrayOfByte, 0);
  }

  public static final int a(byte[] paramArrayOfByte, int paramInt)
  {
    return (0xFF & paramArrayOfByte[paramInt]) << 24 | (0xFF & paramArrayOfByte[(paramInt + 1)]) << 16 | (0xFF & paramArrayOfByte[(paramInt + 2)]) << 8 | 0xFF & paramArrayOfByte[(paramInt + 3)];
  }

  public static final long a(long paramLong, int paramInt, boolean paramBoolean)
  {
    if (paramBoolean)
      return paramLong | 1L << paramInt;
    return b(paramLong, paramInt);
  }

  public static final short a(short paramShort, int paramInt, boolean paramBoolean)
  {
    return (short)a(paramShort, paramInt, paramBoolean);
  }

  public static final void a(int paramInt, byte[] paramArrayOfByte)
  {
    a(paramInt, paramArrayOfByte, 0);
  }

  public static final void a(int paramInt1, byte[] paramArrayOfByte, int paramInt2)
  {
    paramArrayOfByte[paramInt2] = ((byte)(0xFF & paramInt1 >> 24));
    paramArrayOfByte[(paramInt2 + 1)] = ((byte)(0xFF & paramInt1 >> 16));
    paramArrayOfByte[(paramInt2 + 2)] = ((byte)(0xFF & paramInt1 >> 8));
    paramArrayOfByte[(paramInt2 + 3)] = ((byte)(paramInt1 & 0xFF));
  }

  public static final boolean a(byte paramByte, int paramInt)
  {
    return a(paramByte, paramInt);
  }

  public static final boolean a(int paramInt1, int paramInt2)
  {
    return (paramInt1 & 1 << paramInt2) != 0;
  }

  public static final boolean a(long paramLong, int paramInt)
  {
    return (paramLong & 1L << paramInt) != 0L;
  }

  public static final boolean a(short paramShort, int paramInt)
  {
    return a(paramShort, paramInt);
  }

  public static final byte b(byte paramByte, int paramInt)
  {
    return (byte)b(paramByte, paramInt);
  }

  public static final int b(int paramInt1, int paramInt2)
  {
    return paramInt1 & (0xFFFFFFFF ^ 1 << paramInt2);
  }

  public static final long b(long paramLong, int paramInt)
  {
    return paramLong & (0xFFFFFFFF ^ 1L << paramInt);
  }

  public static final short b(short paramShort, int paramInt)
  {
    return (short)b(paramShort, paramInt);
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     u.aly.bw
 * JD-Core Version:    0.6.2
 */