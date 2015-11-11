package com.umeng.fb.util;

import android.text.TextUtils;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

public class DeflaterHelper
{
  public static int TOTAL_LEN;

  public static byte[] deflaterCompress(String paramString1, String paramString2)
    throws IOException
  {
    if (TextUtils.isEmpty(paramString1))
      return null;
    return deflaterCompress(paramString1.getBytes(paramString2));
  }

  public static byte[] deflaterCompress(byte[] paramArrayOfByte)
    throws IOException
  {
    if ((paramArrayOfByte == null) || (paramArrayOfByte.length <= 0))
      return null;
    Deflater localDeflater = new Deflater();
    localDeflater.setInput(paramArrayOfByte);
    localDeflater.finish();
    byte[] arrayOfByte = new byte[8192];
    TOTAL_LEN = 0;
    try
    {
      ByteArrayOutputStream localByteArrayOutputStream1 = new ByteArrayOutputStream();
      try
      {
        while (!localDeflater.finished())
        {
          int i = localDeflater.deflate(arrayOfByte);
          TOTAL_LEN = i + TOTAL_LEN;
          localByteArrayOutputStream1.write(arrayOfByte, 0, i);
        }
      }
      finally
      {
        localByteArrayOutputStream2 = localByteArrayOutputStream1;
      }
      if (localByteArrayOutputStream2 != null)
        localByteArrayOutputStream2.close();
      throw localObject1;
      localDeflater.end();
      if (localByteArrayOutputStream1 != null)
        localByteArrayOutputStream1.close();
      return localByteArrayOutputStream1.toByteArray();
    }
    finally
    {
      while (true)
        ByteArrayOutputStream localByteArrayOutputStream2 = null;
    }
  }

  public static String deflaterDecompress(byte[] paramArrayOfByte, String paramString)
    throws UnsupportedEncodingException, DataFormatException
  {
    byte[] arrayOfByte = deflaterDecompress(paramArrayOfByte);
    if (arrayOfByte != null)
      return new String(arrayOfByte, paramString);
    return null;
  }

  public static byte[] deflaterDecompress(byte[] paramArrayOfByte)
    throws UnsupportedEncodingException, DataFormatException
  {
    if ((paramArrayOfByte == null) || (paramArrayOfByte.length == 0))
      return null;
    Inflater localInflater = new Inflater();
    localInflater.setInput(paramArrayOfByte, 0, paramArrayOfByte.length);
    ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream();
    byte[] arrayOfByte = new byte[1024];
    int i = 0;
    while (!localInflater.needsInput())
    {
      int j = localInflater.inflate(arrayOfByte);
      localByteArrayOutputStream.write(arrayOfByte, i, j);
      i += j;
    }
    localInflater.end();
    return localByteArrayOutputStream.toByteArray();
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.umeng.fb.util.DeflaterHelper
 * JD-Core Version:    0.6.2
 */