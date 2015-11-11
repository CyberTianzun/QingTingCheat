package com.umeng.message.proguard;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

public class x
{
  public static int a;

  public static String a(byte[] paramArrayOfByte, String paramString)
    throws UnsupportedEncodingException, DataFormatException
  {
    if ((paramArrayOfByte == null) || (paramArrayOfByte.length == 0))
      return null;
    Inflater localInflater = new Inflater();
    byte[] arrayOfByte = new byte[100];
    localInflater.setInput(paramArrayOfByte, 0, paramArrayOfByte.length);
    StringBuilder localStringBuilder = new StringBuilder();
    while (!localInflater.needsInput())
      localStringBuilder.append(new String(arrayOfByte, 0, localInflater.inflate(arrayOfByte), paramString));
    localInflater.end();
    return localStringBuilder.toString();
  }

  public static byte[] a(String paramString1, String paramString2)
    throws IOException
  {
    if (y.d(paramString1))
      return null;
    Deflater localDeflater = new Deflater();
    localDeflater.setInput(paramString1.getBytes(paramString2));
    localDeflater.finish();
    byte[] arrayOfByte = new byte[8192];
    a = 0;
    try
    {
      localByteArrayOutputStream = new ByteArrayOutputStream();
      try
      {
        while (!localDeflater.finished())
        {
          int i = localDeflater.deflate(arrayOfByte);
          a = i + a;
          localByteArrayOutputStream.write(arrayOfByte, 0, i);
        }
      }
      finally
      {
      }
      if (localByteArrayOutputStream != null)
        localByteArrayOutputStream.close();
      throw localObject1;
      localDeflater.end();
      if (localByteArrayOutputStream != null)
        localByteArrayOutputStream.close();
      return localByteArrayOutputStream.toByteArray();
    }
    finally
    {
      while (true)
      {
        Object localObject2 = localObject3;
        ByteArrayOutputStream localByteArrayOutputStream = null;
      }
    }
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.umeng.message.proguard.x
 * JD-Core Version:    0.6.2
 */