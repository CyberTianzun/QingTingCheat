package org.apache.commons.httpclient;

import java.io.UnsupportedEncodingException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class HttpConstants
{
  public static final String DEFAULT_CONTENT_CHARSET = "ISO-8859-1";
  public static final String HTTP_ELEMENT_CHARSET = "US-ASCII";
  private static final Log LOG;
  static Class class$org$apache$commons$httpclient$HttpConstants;

  static
  {
    Class localClass;
    if (class$org$apache$commons$httpclient$HttpConstants == null)
    {
      localClass = class$("org.apache.commons.httpclient.HttpConstants");
      class$org$apache$commons$httpclient$HttpConstants = localClass;
    }
    while (true)
    {
      LOG = LogFactory.getLog(localClass);
      return;
      localClass = class$org$apache$commons$httpclient$HttpConstants;
    }
  }

  static Class class$(String paramString)
  {
    try
    {
      Class localClass = Class.forName(paramString);
      return localClass;
    }
    catch (ClassNotFoundException localClassNotFoundException)
    {
      throw new NoClassDefFoundError(localClassNotFoundException.getMessage());
    }
  }

  public static byte[] getAsciiBytes(String paramString)
  {
    if (paramString == null)
      throw new IllegalArgumentException("Parameter may not be null");
    try
    {
      byte[] arrayOfByte = paramString.getBytes("US-ASCII");
      return arrayOfByte;
    }
    catch (UnsupportedEncodingException localUnsupportedEncodingException)
    {
    }
    throw new RuntimeException("HttpClient requires ASCII support");
  }

  public static String getAsciiString(byte[] paramArrayOfByte)
  {
    return getAsciiString(paramArrayOfByte, 0, paramArrayOfByte.length);
  }

  public static String getAsciiString(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
  {
    if (paramArrayOfByte == null)
      throw new IllegalArgumentException("Parameter may not be null");
    try
    {
      String str = new String(paramArrayOfByte, paramInt1, paramInt2, "US-ASCII");
      return str;
    }
    catch (UnsupportedEncodingException localUnsupportedEncodingException)
    {
    }
    throw new RuntimeException("HttpClient requires ASCII support");
  }

  public static byte[] getBytes(String paramString)
  {
    if (paramString == null)
      throw new IllegalArgumentException("Parameter may not be null");
    try
    {
      byte[] arrayOfByte = paramString.getBytes("US-ASCII");
      return arrayOfByte;
    }
    catch (UnsupportedEncodingException localUnsupportedEncodingException)
    {
      if (LOG.isWarnEnabled())
        LOG.warn("Unsupported encoding: US-ASCII. System default encoding used");
    }
    return paramString.getBytes();
  }

  public static byte[] getContentBytes(String paramString)
  {
    return getContentBytes(paramString, null);
  }

  public static byte[] getContentBytes(String paramString1, String paramString2)
  {
    if (paramString1 == null)
      throw new IllegalArgumentException("Parameter may not be null");
    if ((paramString2 == null) || (paramString2.equals("")))
      paramString2 = "ISO-8859-1";
    try
    {
      byte[] arrayOfByte2 = paramString1.getBytes(paramString2);
      return arrayOfByte2;
    }
    catch (UnsupportedEncodingException localUnsupportedEncodingException1)
    {
      if (LOG.isWarnEnabled())
        LOG.warn("Unsupported encoding: " + paramString2 + ". HTTP default encoding used");
      try
      {
        byte[] arrayOfByte1 = paramString1.getBytes("ISO-8859-1");
        return arrayOfByte1;
      }
      catch (UnsupportedEncodingException localUnsupportedEncodingException2)
      {
        if (LOG.isWarnEnabled())
          LOG.warn("Unsupported encoding: ISO-8859-1. System encoding used");
      }
    }
    return paramString1.getBytes();
  }

  public static String getContentString(byte[] paramArrayOfByte)
  {
    return getContentString(paramArrayOfByte, null);
  }

  public static String getContentString(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
  {
    return getContentString(paramArrayOfByte, paramInt1, paramInt2, null);
  }

  public static String getContentString(byte[] paramArrayOfByte, int paramInt1, int paramInt2, String paramString)
  {
    if (paramArrayOfByte == null)
      throw new IllegalArgumentException("Parameter may not be null");
    if ((paramString == null) || (paramString.equals("")))
      paramString = "ISO-8859-1";
    try
    {
      String str1 = new String(paramArrayOfByte, paramInt1, paramInt2, paramString);
      return str1;
    }
    catch (UnsupportedEncodingException localUnsupportedEncodingException1)
    {
      if (LOG.isWarnEnabled())
        LOG.warn("Unsupported encoding: " + paramString + ". Default HTTP encoding used");
      try
      {
        String str2 = new String(paramArrayOfByte, paramInt1, paramInt2, "ISO-8859-1");
        return str2;
      }
      catch (UnsupportedEncodingException localUnsupportedEncodingException2)
      {
        if (LOG.isWarnEnabled())
          LOG.warn("Unsupported encoding: ISO-8859-1. System encoding used");
      }
    }
    return new String(paramArrayOfByte, paramInt1, paramInt2);
  }

  public static String getContentString(byte[] paramArrayOfByte, String paramString)
  {
    return getContentString(paramArrayOfByte, 0, paramArrayOfByte.length, paramString);
  }

  public static String getString(byte[] paramArrayOfByte)
  {
    return getString(paramArrayOfByte, 0, paramArrayOfByte.length);
  }

  public static String getString(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
  {
    if (paramArrayOfByte == null)
      throw new IllegalArgumentException("Parameter may not be null");
    try
    {
      String str = new String(paramArrayOfByte, paramInt1, paramInt2, "US-ASCII");
      return str;
    }
    catch (UnsupportedEncodingException localUnsupportedEncodingException)
    {
      if (LOG.isWarnEnabled())
        LOG.warn("Unsupported encoding: US-ASCII. System default encoding used");
    }
    return new String(paramArrayOfByte, paramInt1, paramInt2);
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     org.apache.commons.httpclient.HttpConstants
 * JD-Core Version:    0.6.2
 */