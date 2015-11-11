package com.taobao.munion.base.caches;

import java.io.ByteArrayOutputStream;

public class l
{
  private static String a = "0123456789ABCDEF";

  private static byte a(byte paramByte1, byte paramByte2)
  {
    return (byte)((byte)(Byte.decode("0x" + new String(new byte[] { paramByte1 })).byteValue() << 4) | Byte.decode("0x" + new String(new byte[] { paramByte2 })).byteValue());
  }

  private static byte a(char paramChar)
  {
    return (byte)"0123456789ABCDEF".indexOf(paramChar);
  }

  public static String a(String paramString)
  {
    String str1 = "";
    for (int i = 0; i < paramString.length(); i++)
    {
      String str2 = Integer.toHexString(paramString.charAt(i));
      str1 = str1 + str2;
    }
    return str1;
  }

  public static String a(byte[] paramArrayOfByte)
  {
    StringBuilder localStringBuilder = new StringBuilder("");
    if ((paramArrayOfByte == null) || (paramArrayOfByte.length <= 0))
      return null;
    int i = paramArrayOfByte.length;
    for (int j = 0; j < i; j++)
    {
      String str = Integer.toHexString(0xFF & paramArrayOfByte[j]);
      if (str.length() < 2)
        localStringBuilder.append(0);
      localStringBuilder.append(str);
    }
    return localStringBuilder.toString();
  }

  public static String b(String paramString)
  {
    byte[] arrayOfByte = paramString.getBytes();
    StringBuilder localStringBuilder = new StringBuilder(2 * arrayOfByte.length);
    int i = arrayOfByte.length;
    for (int j = 0; j < i; j++)
    {
      int k = arrayOfByte[j];
      localStringBuilder.append(a.charAt((k & 0xF0) >> 4));
      localStringBuilder.append(a.charAt((k & 0xF) >> 0));
    }
    return localStringBuilder.toString();
  }

  public static String c(String paramString)
  {
    ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream(paramString.length() / 2);
    for (int i = 0; i < paramString.length(); i += 2)
      localByteArrayOutputStream.write(a.indexOf(paramString.charAt(i)) << 4 | a.indexOf(paramString.charAt(i + 1)));
    return new String(localByteArrayOutputStream.toByteArray());
  }

  public static byte[] d(String paramString)
  {
    byte[] arrayOfByte1 = new byte[6];
    byte[] arrayOfByte2 = paramString.getBytes();
    for (int i = 0; i < 6; i++)
      arrayOfByte1[i] = a(arrayOfByte2[(i * 2)], arrayOfByte2[(1 + i * 2)]);
    return arrayOfByte1;
  }

  public static byte[] e(String paramString)
  {
    byte[] arrayOfByte;
    if ((paramString == null) || (paramString.equals("")))
      arrayOfByte = null;
    while (true)
    {
      return arrayOfByte;
      String str = paramString.toUpperCase();
      int i = str.length() / 2;
      char[] arrayOfChar = str.toCharArray();
      arrayOfByte = new byte[i];
      for (int j = 0; j < i; j++)
      {
        int k = j * 2;
        arrayOfByte[j] = ((byte)(a(arrayOfChar[k]) << 4 | a(arrayOfChar[(k + 1)])));
      }
    }
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.taobao.munion.base.caches.l
 * JD-Core Version:    0.6.2
 */