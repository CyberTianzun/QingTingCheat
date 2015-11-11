package com.tencent.a.b;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.util.zip.DeflaterOutputStream;
import java.util.zip.InflaterInputStream;

public final class j
{
  public static String a(String paramString)
  {
    try
    {
      MessageDigest localMessageDigest = MessageDigest.getInstance("MD5");
      localMessageDigest.update(paramString.getBytes());
      byte[] arrayOfByte = localMessageDigest.digest();
      StringBuilder localStringBuilder = new StringBuilder();
      int i = arrayOfByte.length;
      for (int j = 0; j < i; j++)
        localStringBuilder.append(Integer.toHexString(0xFF & arrayOfByte[j])).append("");
      String str = localStringBuilder.toString();
      return str;
    }
    catch (Exception localException)
    {
    }
    return paramString;
  }

  public static byte[] a(byte[] paramArrayOfByte)
  {
    if (paramArrayOfByte == null)
      return null;
    ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream();
    DeflaterOutputStream localDeflaterOutputStream = new DeflaterOutputStream(localByteArrayOutputStream);
    try
    {
      localDeflaterOutputStream.write(paramArrayOfByte, 0, paramArrayOfByte.length);
      localDeflaterOutputStream.finish();
      localDeflaterOutputStream.flush();
      localDeflaterOutputStream.close();
      return localByteArrayOutputStream.toByteArray();
    }
    catch (Exception localException)
    {
    }
    return null;
  }

  public static byte[] b(byte[] paramArrayOfByte)
  {
    int i = 0;
    if (paramArrayOfByte == null)
      return null;
    ByteArrayInputStream localByteArrayInputStream = new ByteArrayInputStream(paramArrayOfByte);
    InflaterInputStream localInflaterInputStream = new InflaterInputStream(localByteArrayInputStream);
    Object localObject1 = new byte[0];
    byte[] arrayOfByte = new byte[1024];
    while (true)
    {
      try
      {
        int j = localInflaterInputStream.read(arrayOfByte);
        if (j <= 0)
          break label117;
        i += j;
        localObject2 = new byte[i];
        System.arraycopy(localObject1, 0, localObject2, 0, localObject1.length);
        System.arraycopy(arrayOfByte, 0, localObject2, localObject1.length, j);
        if (j <= 0)
          try
          {
            localByteArrayInputStream.close();
            localInflaterInputStream.close();
            return localObject2;
          }
          catch (IOException localIOException)
          {
            return null;
          }
      }
      catch (Exception localException)
      {
        return null;
      }
      localObject1 = localObject2;
      continue;
      label117: Object localObject2 = localObject1;
    }
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.tencent.a.b.j
 * JD-Core Version:    0.6.2
 */