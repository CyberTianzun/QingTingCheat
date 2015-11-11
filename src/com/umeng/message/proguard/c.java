package com.umeng.message.proguard;

public class c
{
  public static byte[] a(int paramInt)
  {
    byte[] arrayOfByte = new byte[4];
    arrayOfByte[3] = ((byte)(paramInt % 256));
    int i = paramInt >> 8;
    arrayOfByte[2] = ((byte)(i % 256));
    int j = i >> 8;
    arrayOfByte[1] = ((byte)(j % 256));
    arrayOfByte[0] = ((byte)((j >> 8) % 256));
    return arrayOfByte;
  }

  public static byte[] a(byte[] paramArrayOfByte, int paramInt)
  {
    if (paramArrayOfByte.length == 4)
    {
      paramArrayOfByte[3] = ((byte)(paramInt % 256));
      int i = paramInt >> 8;
      paramArrayOfByte[2] = ((byte)(i % 256));
      int j = i >> 8;
      paramArrayOfByte[1] = ((byte)(j % 256));
      paramArrayOfByte[0] = ((byte)((j >> 8) % 256));
      return paramArrayOfByte;
    }
    return null;
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.umeng.message.proguard.c
 * JD-Core Version:    0.6.2
 */