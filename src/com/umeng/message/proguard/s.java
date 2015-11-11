package com.umeng.message.proguard;

import java.io.UnsupportedEncodingException;

public class s
{
  private static IllegalStateException a(String paramString, UnsupportedEncodingException paramUnsupportedEncodingException)
  {
    return new IllegalStateException(paramString + ": " + paramUnsupportedEncodingException);
  }

  public static String a(byte[] paramArrayOfByte)
  {
    return a(paramArrayOfByte, "ISO-8859-1");
  }

  public static String a(byte[] paramArrayOfByte, String paramString)
  {
    if (paramArrayOfByte == null)
      return null;
    try
    {
      String str = new String(paramArrayOfByte, paramString);
      return str;
    }
    catch (UnsupportedEncodingException localUnsupportedEncodingException)
    {
      throw a(paramString, localUnsupportedEncodingException);
    }
  }

  public static byte[] a(String paramString)
  {
    return a(paramString, "ISO-8859-1");
  }

  public static byte[] a(String paramString1, String paramString2)
  {
    if (paramString1 == null)
      return null;
    try
    {
      byte[] arrayOfByte = paramString1.getBytes(paramString2);
      return arrayOfByte;
    }
    catch (UnsupportedEncodingException localUnsupportedEncodingException)
    {
      throw a(paramString2, localUnsupportedEncodingException);
    }
  }

  public static String b(byte[] paramArrayOfByte)
  {
    return a(paramArrayOfByte, "US-ASCII");
  }

  public static byte[] b(String paramString)
  {
    return a(paramString, "US-ASCII");
  }

  public static String c(byte[] paramArrayOfByte)
  {
    return a(paramArrayOfByte, "UTF-16");
  }

  public static byte[] c(String paramString)
  {
    return a(paramString, "UTF-16");
  }

  public static String d(byte[] paramArrayOfByte)
  {
    return a(paramArrayOfByte, "UTF-16BE");
  }

  public static byte[] d(String paramString)
  {
    return a(paramString, "UTF-16BE");
  }

  public static String e(byte[] paramArrayOfByte)
  {
    return a(paramArrayOfByte, "UTF-16LE");
  }

  public static byte[] e(String paramString)
  {
    return a(paramString, "UTF-16LE");
  }

  public static String f(byte[] paramArrayOfByte)
  {
    return a(paramArrayOfByte, "UTF-8");
  }

  public static byte[] f(String paramString)
  {
    return a(paramString, "UTF-8");
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.umeng.message.proguard.s
 * JD-Core Version:    0.6.2
 */