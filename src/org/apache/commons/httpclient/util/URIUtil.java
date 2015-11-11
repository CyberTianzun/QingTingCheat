package org.apache.commons.httpclient.util;

import java.util.BitSet;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.net.URLCodec;
import org.apache.commons.httpclient.URI;
import org.apache.commons.httpclient.URIException;

public class URIUtil
{
  protected static final BitSet empty = new BitSet(1);

  public static String decode(String paramString)
    throws URIException
  {
    try
    {
      String str = EncodingUtil.getString(URLCodec.decodeUrl(EncodingUtil.getAsciiBytes(paramString)), URI.getDefaultProtocolCharset());
      return str;
    }
    catch (DecoderException localDecoderException)
    {
      throw new URIException(localDecoderException.getMessage());
    }
  }

  public static String decode(String paramString1, String paramString2)
    throws URIException
  {
    return Coder.decode(paramString1.toCharArray(), paramString2);
  }

  public static String encode(String paramString, BitSet paramBitSet)
    throws URIException
  {
    return encode(paramString, paramBitSet, URI.getDefaultProtocolCharset());
  }

  public static String encode(String paramString1, BitSet paramBitSet, String paramString2)
    throws URIException
  {
    return EncodingUtil.getAsciiString(URLCodec.encodeUrl(paramBitSet, EncodingUtil.getBytes(paramString1, paramString2)));
  }

  public static String encodeAll(String paramString)
    throws URIException
  {
    return encodeAll(paramString, URI.getDefaultProtocolCharset());
  }

  public static String encodeAll(String paramString1, String paramString2)
    throws URIException
  {
    return encode(paramString1, empty, paramString2);
  }

  public static String encodePath(String paramString)
    throws URIException
  {
    return encodePath(paramString, URI.getDefaultProtocolCharset());
  }

  public static String encodePath(String paramString1, String paramString2)
    throws URIException
  {
    return encode(paramString1, URI.allowed_abs_path, paramString2);
  }

  public static String encodePathQuery(String paramString)
    throws URIException
  {
    return encodePathQuery(paramString, URI.getDefaultProtocolCharset());
  }

  public static String encodePathQuery(String paramString1, String paramString2)
    throws URIException
  {
    int i = paramString1.indexOf('?');
    if (i < 0)
      return encode(paramString1, URI.allowed_abs_path, paramString2);
    return encode(paramString1.substring(0, i), URI.allowed_abs_path, paramString2) + '?' + encode(paramString1.substring(i + 1), URI.allowed_query, paramString2);
  }

  public static String encodeQuery(String paramString)
    throws URIException
  {
    return encodeQuery(paramString, URI.getDefaultProtocolCharset());
  }

  public static String encodeQuery(String paramString1, String paramString2)
    throws URIException
  {
    return encode(paramString1, URI.allowed_query, paramString2);
  }

  public static String encodeWithinAuthority(String paramString)
    throws URIException
  {
    return encodeWithinAuthority(paramString, URI.getDefaultProtocolCharset());
  }

  public static String encodeWithinAuthority(String paramString1, String paramString2)
    throws URIException
  {
    return encode(paramString1, URI.allowed_within_authority, paramString2);
  }

  public static String encodeWithinPath(String paramString)
    throws URIException
  {
    return encodeWithinPath(paramString, URI.getDefaultProtocolCharset());
  }

  public static String encodeWithinPath(String paramString1, String paramString2)
    throws URIException
  {
    return encode(paramString1, URI.allowed_within_path, paramString2);
  }

  public static String encodeWithinQuery(String paramString)
    throws URIException
  {
    return encodeWithinQuery(paramString, URI.getDefaultProtocolCharset());
  }

  public static String encodeWithinQuery(String paramString1, String paramString2)
    throws URIException
  {
    return encode(paramString1, URI.allowed_within_query, paramString2);
  }

  public static String getFromPath(String paramString)
  {
    if (paramString == null)
    {
      paramString = null;
      return paramString;
    }
    int i = paramString.indexOf("//");
    int j = 0;
    if (i >= 0)
    {
      int m = paramString.lastIndexOf("/", i - 1);
      j = 0;
      if (m < 0)
        break label58;
    }
    int k;
    while (true)
    {
      k = paramString.indexOf("/", j);
      if (k >= 0)
        break label65;
      if (i < 0)
        break;
      return "/";
      label58: j = i + 2;
    }
    label65: return paramString.substring(k);
  }

  public static String getName(String paramString)
  {
    String str;
    if ((paramString == null) || (paramString.length() == 0))
      str = paramString;
    int i;
    int j;
    do
    {
      return str;
      str = getPath(paramString);
      i = str.lastIndexOf("/");
      j = str.length();
    }
    while (i < 0);
    return str.substring(i + 1, j);
  }

  public static String getPath(String paramString)
  {
    if (paramString == null)
    {
      paramString = null;
      return paramString;
    }
    int i = paramString.indexOf("//");
    int j = 0;
    if (i >= 0)
    {
      int n = paramString.lastIndexOf("/", i - 1);
      j = 0;
      if (n < 0)
        break label113;
    }
    int k;
    int m;
    while (true)
    {
      k = paramString.indexOf("/", j);
      m = paramString.length();
      if (paramString.indexOf('?', k) != -1)
        m = paramString.indexOf('?', k);
      if ((paramString.lastIndexOf("#") > k) && (paramString.lastIndexOf("#") < m))
        m = paramString.lastIndexOf("#");
      if (k >= 0)
        break label120;
      if (i < 0)
        break;
      return "/";
      label113: j = i + 2;
    }
    label120: return paramString.substring(k, m);
  }

  public static String getPathQuery(String paramString)
  {
    if (paramString == null)
    {
      paramString = null;
      return paramString;
    }
    int i = paramString.indexOf("//");
    int j = 0;
    if (i >= 0)
    {
      int n = paramString.lastIndexOf("/", i - 1);
      j = 0;
      if (n < 0)
        break label82;
    }
    int k;
    int m;
    while (true)
    {
      k = paramString.indexOf("/", j);
      m = paramString.length();
      if (paramString.lastIndexOf("#") > k)
        m = paramString.lastIndexOf("#");
      if (k >= 0)
        break label89;
      if (i < 0)
        break;
      return "/";
      label82: j = i + 2;
    }
    label89: return paramString.substring(k, m);
  }

  public static String getQuery(String paramString)
  {
    if ((paramString == null) || (paramString.length() == 0));
    int i;
    int j;
    int m;
    int n;
    do
    {
      return null;
      i = paramString.indexOf("//");
      j = 0;
      if (i >= 0)
      {
        int i2 = paramString.lastIndexOf("/", i - 1);
        j = 0;
        if (i2 < 0)
          break;
      }
      int k = paramString.indexOf("/", j);
      m = paramString.length();
      n = paramString.indexOf("?", k);
    }
    while (n < 0);
    int i1 = n + 1;
    if (paramString.lastIndexOf("#") > i1)
      m = paramString.lastIndexOf("#");
    if ((i1 < 0) || (i1 == m));
    for (String str = null; ; str = paramString.substring(i1, m))
    {
      return str;
      j = i + 2;
      break;
    }
  }

  protected static class Coder extends URI
  {
    public static String decode(char[] paramArrayOfChar, String paramString)
      throws URIException
    {
      return URI.decode(paramArrayOfChar, paramString);
    }

    public static char[] encode(String paramString1, BitSet paramBitSet, String paramString2)
      throws URIException
    {
      return URI.encode(paramString1, paramBitSet, paramString2);
    }

    public static String replace(String paramString, char paramChar1, char paramChar2)
    {
      StringBuffer localStringBuffer = new StringBuffer(paramString.length());
      int i = 0;
      int j = paramString.indexOf(paramChar1);
      if (j >= 0)
      {
        localStringBuffer.append(paramString.substring(0, j));
        localStringBuffer.append(paramChar2);
      }
      while (true)
      {
        i = j;
        if (j >= 0)
          break;
        return localStringBuffer.toString();
        localStringBuffer.append(paramString.substring(i));
      }
    }

    public static String replace(String paramString, char[] paramArrayOfChar1, char[] paramArrayOfChar2)
    {
      for (int i = paramArrayOfChar1.length; ; i--)
      {
        if (i <= 0)
          return paramString.toString();
        paramString = replace(paramString, paramArrayOfChar1[i], paramArrayOfChar2[i]);
      }
    }

    public static boolean verifyEscaped(char[] paramArrayOfChar)
    {
      for (int i = 0; ; i++)
      {
        boolean bool;
        if (i >= paramArrayOfChar.length)
          bool = true;
        int n;
        do
        {
          int k;
          int m;
          do
          {
            int j;
            do
            {
              return bool;
              j = paramArrayOfChar[i];
              bool = false;
            }
            while (j > 128);
            if (j != 37)
              break;
            k = i + 1;
            m = Character.digit(paramArrayOfChar[k], 16);
            bool = false;
          }
          while (m == -1);
          i = k + 1;
          n = Character.digit(paramArrayOfChar[i], 16);
          bool = false;
        }
        while (n == -1);
      }
    }
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     org.apache.commons.httpclient.util.URIUtil
 * JD-Core Version:    0.6.2
 */