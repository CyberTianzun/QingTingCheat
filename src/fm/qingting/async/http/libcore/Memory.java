package fm.qingting.async.http.libcore;

import java.nio.ByteOrder;

public final class Memory
{
  public static native void memmove(Object paramObject1, int paramInt1, Object paramObject2, int paramInt2, long paramLong);

  public static native byte peekByte(int paramInt);

  public static native void peekByteArray(int paramInt1, byte[] paramArrayOfByte, int paramInt2, int paramInt3);

  public static native void peekCharArray(int paramInt1, char[] paramArrayOfChar, int paramInt2, int paramInt3, boolean paramBoolean);

  public static native void peekDoubleArray(int paramInt1, double[] paramArrayOfDouble, int paramInt2, int paramInt3, boolean paramBoolean);

  public static native void peekFloatArray(int paramInt1, float[] paramArrayOfFloat, int paramInt2, int paramInt3, boolean paramBoolean);

  public static native int peekInt(int paramInt, boolean paramBoolean);

  public static int peekInt(byte[] paramArrayOfByte, int paramInt, ByteOrder paramByteOrder)
  {
    if (paramByteOrder == ByteOrder.BIG_ENDIAN)
    {
      int i1 = paramInt + 1;
      int i2 = (0xFF & paramArrayOfByte[paramInt]) << 24;
      int i3 = i1 + 1;
      int i4 = i2 | (0xFF & paramArrayOfByte[i1]) << 16;
      int i5 = i3 + 1;
      return i4 | (0xFF & paramArrayOfByte[i3]) << 8 | (0xFF & paramArrayOfByte[i5]) << 0;
    }
    int i = paramInt + 1;
    int j = (0xFF & paramArrayOfByte[paramInt]) << 0;
    int k = i + 1;
    int m = j | (0xFF & paramArrayOfByte[i]) << 8;
    int n = k + 1;
    return m | (0xFF & paramArrayOfByte[k]) << 16 | (0xFF & paramArrayOfByte[n]) << 24;
  }

  public static native void peekIntArray(int paramInt1, int[] paramArrayOfInt, int paramInt2, int paramInt3, boolean paramBoolean);

  public static native long peekLong(int paramInt, boolean paramBoolean);

  public static long peekLong(byte[] paramArrayOfByte, int paramInt, ByteOrder paramByteOrder)
  {
    if (paramByteOrder == ByteOrder.BIG_ENDIAN)
    {
      int i9 = paramInt + 1;
      int i10 = (0xFF & paramArrayOfByte[paramInt]) << 24;
      int i11 = i9 + 1;
      int i12 = i10 | (0xFF & paramArrayOfByte[i9]) << 16;
      int i13 = i11 + 1;
      int i14 = i12 | (0xFF & paramArrayOfByte[i11]) << 8;
      int i15 = i13 + 1;
      int i16 = i14 | (0xFF & paramArrayOfByte[i13]) << 0;
      int i17 = i15 + 1;
      int i18 = (0xFF & paramArrayOfByte[i15]) << 24;
      int i19 = i17 + 1;
      int i20 = i18 | (0xFF & paramArrayOfByte[i17]) << 16;
      int i21 = i19 + 1;
      int i22 = i20 | (0xFF & paramArrayOfByte[i19]) << 8 | (0xFF & paramArrayOfByte[i21]) << 0;
      return i16 << 32 | 0xFFFFFFFF & i22;
    }
    int i = paramInt + 1;
    int j = (0xFF & paramArrayOfByte[paramInt]) << 0;
    int k = i + 1;
    int m = j | (0xFF & paramArrayOfByte[i]) << 8;
    int n = k + 1;
    int i1 = m | (0xFF & paramArrayOfByte[k]) << 16;
    int i2 = n + 1;
    int i3 = i1 | (0xFF & paramArrayOfByte[n]) << 24;
    int i4 = i2 + 1;
    int i5 = (0xFF & paramArrayOfByte[i2]) << 0;
    int i6 = i4 + 1;
    int i7 = i5 | (0xFF & paramArrayOfByte[i4]) << 8;
    int i8 = i6 + 1;
    return (i7 | (0xFF & paramArrayOfByte[i6]) << 16 | (0xFF & paramArrayOfByte[i8]) << 24) << 32 | 0xFFFFFFFF & i3;
  }

  public static native void peekLongArray(int paramInt1, long[] paramArrayOfLong, int paramInt2, int paramInt3, boolean paramBoolean);

  public static native short peekShort(int paramInt, boolean paramBoolean);

  public static short peekShort(byte[] paramArrayOfByte, int paramInt, ByteOrder paramByteOrder)
  {
    if (paramByteOrder == ByteOrder.BIG_ENDIAN)
      return (short)(paramArrayOfByte[paramInt] << 8 | 0xFF & paramArrayOfByte[(paramInt + 1)]);
    return (short)(paramArrayOfByte[(paramInt + 1)] << 8 | 0xFF & paramArrayOfByte[paramInt]);
  }

  public static native void peekShortArray(int paramInt1, short[] paramArrayOfShort, int paramInt2, int paramInt3, boolean paramBoolean);

  public static native void pokeByte(int paramInt, byte paramByte);

  public static native void pokeByteArray(int paramInt1, byte[] paramArrayOfByte, int paramInt2, int paramInt3);

  public static native void pokeCharArray(int paramInt1, char[] paramArrayOfChar, int paramInt2, int paramInt3, boolean paramBoolean);

  public static native void pokeDoubleArray(int paramInt1, double[] paramArrayOfDouble, int paramInt2, int paramInt3, boolean paramBoolean);

  public static native void pokeFloatArray(int paramInt1, float[] paramArrayOfFloat, int paramInt2, int paramInt3, boolean paramBoolean);

  public static native void pokeInt(int paramInt1, int paramInt2, boolean paramBoolean);

  public static void pokeInt(byte[] paramArrayOfByte, int paramInt1, int paramInt2, ByteOrder paramByteOrder)
  {
    if (paramByteOrder == ByteOrder.BIG_ENDIAN)
    {
      int m = paramInt1 + 1;
      paramArrayOfByte[paramInt1] = ((byte)(0xFF & paramInt2 >> 24));
      int n = m + 1;
      paramArrayOfByte[m] = ((byte)(0xFF & paramInt2 >> 16));
      int i1 = n + 1;
      paramArrayOfByte[n] = ((byte)(0xFF & paramInt2 >> 8));
      paramArrayOfByte[i1] = ((byte)(0xFF & paramInt2 >> 0));
      return;
    }
    int i = paramInt1 + 1;
    paramArrayOfByte[paramInt1] = ((byte)(0xFF & paramInt2 >> 0));
    int j = i + 1;
    paramArrayOfByte[i] = ((byte)(0xFF & paramInt2 >> 8));
    int k = j + 1;
    paramArrayOfByte[j] = ((byte)(0xFF & paramInt2 >> 16));
    paramArrayOfByte[k] = ((byte)(0xFF & paramInt2 >> 24));
  }

  public static native void pokeIntArray(int paramInt1, int[] paramArrayOfInt, int paramInt2, int paramInt3, boolean paramBoolean);

  public static native void pokeLong(int paramInt, long paramLong, boolean paramBoolean);

  public static void pokeLong(byte[] paramArrayOfByte, int paramInt, long paramLong, ByteOrder paramByteOrder)
  {
    if (paramByteOrder == ByteOrder.BIG_ENDIAN)
    {
      int i5 = (int)(paramLong >> 32);
      int i6 = paramInt + 1;
      paramArrayOfByte[paramInt] = ((byte)(0xFF & i5 >> 24));
      int i7 = i6 + 1;
      paramArrayOfByte[i6] = ((byte)(0xFF & i5 >> 16));
      int i8 = i7 + 1;
      paramArrayOfByte[i7] = ((byte)(0xFF & i5 >> 8));
      int i9 = i8 + 1;
      paramArrayOfByte[i8] = ((byte)(0xFF & i5 >> 0));
      int i10 = (int)paramLong;
      int i11 = i9 + 1;
      paramArrayOfByte[i9] = ((byte)(0xFF & i10 >> 24));
      int i12 = i11 + 1;
      paramArrayOfByte[i11] = ((byte)(0xFF & i10 >> 16));
      int i13 = i12 + 1;
      paramArrayOfByte[i12] = ((byte)(0xFF & i10 >> 8));
      paramArrayOfByte[i13] = ((byte)(0xFF & i10 >> 0));
      return;
    }
    int i = (int)paramLong;
    int j = paramInt + 1;
    paramArrayOfByte[paramInt] = ((byte)(0xFF & i >> 0));
    int k = j + 1;
    paramArrayOfByte[j] = ((byte)(0xFF & i >> 8));
    int m = k + 1;
    paramArrayOfByte[k] = ((byte)(0xFF & i >> 16));
    int n = m + 1;
    paramArrayOfByte[m] = ((byte)(0xFF & i >> 24));
    int i1 = (int)(paramLong >> 32);
    int i2 = n + 1;
    paramArrayOfByte[n] = ((byte)(0xFF & i1 >> 0));
    int i3 = i2 + 1;
    paramArrayOfByte[i2] = ((byte)(0xFF & i1 >> 8));
    int i4 = i3 + 1;
    paramArrayOfByte[i3] = ((byte)(0xFF & i1 >> 16));
    paramArrayOfByte[i4] = ((byte)(0xFF & i1 >> 24));
  }

  public static native void pokeLongArray(int paramInt1, long[] paramArrayOfLong, int paramInt2, int paramInt3, boolean paramBoolean);

  public static native void pokeShort(int paramInt, short paramShort, boolean paramBoolean);

  public static void pokeShort(byte[] paramArrayOfByte, int paramInt, short paramShort, ByteOrder paramByteOrder)
  {
    if (paramByteOrder == ByteOrder.BIG_ENDIAN)
    {
      int j = paramInt + 1;
      paramArrayOfByte[paramInt] = ((byte)(0xFF & paramShort >> 8));
      paramArrayOfByte[j] = ((byte)(0xFF & paramShort >> 0));
      return;
    }
    int i = paramInt + 1;
    paramArrayOfByte[paramInt] = ((byte)(0xFF & paramShort >> 0));
    paramArrayOfByte[i] = ((byte)(0xFF & paramShort >> 8));
  }

  public static native void pokeShortArray(int paramInt1, short[] paramArrayOfShort, int paramInt2, int paramInt3, boolean paramBoolean);

  public static native void unsafeBulkGet(Object paramObject, int paramInt1, int paramInt2, byte[] paramArrayOfByte, int paramInt3, int paramInt4, boolean paramBoolean);

  public static native void unsafeBulkPut(byte[] paramArrayOfByte, int paramInt1, int paramInt2, Object paramObject, int paramInt3, int paramInt4, boolean paramBoolean);
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.async.http.libcore.Memory
 * JD-Core Version:    0.6.2
 */