package com.taobao.munion.base;

import com.taobao.munion.base.caches.l;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.security.MessageDigest;

public class e
{
  private static final String a = "MD5";
  private static final String b = "SHA-1";
  private static final String c = "SHA-256";

  private static String a(InputStream paramInputStream, String paramString)
    throws IOException
  {
    try
    {
      MessageDigest localMessageDigest = MessageDigest.getInstance(paramString);
      byte[] arrayOfByte = new byte[1024];
      for (int i = paramInputStream.read(arrayOfByte, 0, 1024); i > -1; i = paramInputStream.read(arrayOfByte, 0, 1024))
        localMessageDigest.update(arrayOfByte, 0, i);
      String str = l.a(localMessageDigest.digest());
      return str;
    }
    catch (GeneralSecurityException localGeneralSecurityException)
    {
      throw new IllegalStateException("Security exception", localGeneralSecurityException);
    }
  }

  public static String a(String paramString)
  {
    if (paramString == null)
      return null;
    try
    {
      String str = a(paramString.getBytes("utf-8"), "MD5");
      return str;
    }
    catch (UnsupportedEncodingException localUnsupportedEncodingException)
    {
      localUnsupportedEncodingException.printStackTrace();
    }
    return null;
  }

  public static String a(byte[] paramArrayOfByte)
  {
    if (paramArrayOfByte == null)
      return null;
    return a(paramArrayOfByte, "SHA-256");
  }

  private static String a(byte[] paramArrayOfByte, String paramString)
  {
    try
    {
      String str = l.a(MessageDigest.getInstance(paramString).digest(paramArrayOfByte));
      return str;
    }
    catch (GeneralSecurityException localGeneralSecurityException)
    {
      throw new IllegalStateException("Security exception", localGeneralSecurityException);
    }
  }

  public static String b(byte[] paramArrayOfByte)
  {
    if (paramArrayOfByte == null)
      return null;
    return a(paramArrayOfByte, "SHA-1");
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.taobao.munion.base.e
 * JD-Core Version:    0.6.2
 */