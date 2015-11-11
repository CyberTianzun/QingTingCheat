package weibo4android.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.BitSet;

public class URLEncodeUtils
{
  static BitSet dontNeedEncoding = new BitSet(256);

  static
  {
    for (int i = 97; i <= 122; i++)
      dontNeedEncoding.set(i);
    for (int j = 65; j <= 90; j++)
      dontNeedEncoding.set(j);
    for (int k = 48; k <= 57; k++)
      dontNeedEncoding.set(k);
    dontNeedEncoding.set(32);
    dontNeedEncoding.set(45);
    dontNeedEncoding.set(95);
    dontNeedEncoding.set(46);
    dontNeedEncoding.set(42);
    dontNeedEncoding.set(43);
    dontNeedEncoding.set(37);
  }

  public static final String decodeURL(String paramString)
  {
    try
    {
      String str = URLDecoder.decode(paramString, "utf-8");
      return str;
    }
    catch (UnsupportedEncodingException localUnsupportedEncodingException)
    {
      throw new RuntimeException(localUnsupportedEncodingException);
    }
  }

  public static final String encodeURL(String paramString)
  {
    try
    {
      String str = URLEncoder.encode(paramString, "utf-8");
      return str;
    }
    catch (UnsupportedEncodingException localUnsupportedEncodingException)
    {
      throw new RuntimeException(localUnsupportedEncodingException);
    }
  }

  public static final boolean isURLEncoded(String paramString)
  {
    boolean bool = true;
    if ((paramString == null) || ("".equals(paramString)))
      bool = false;
    int k;
    do
    {
      return bool;
      char[] arrayOfChar = paramString.toCharArray();
      int i = arrayOfChar.length;
      int j = 0;
      k = 0;
      while (j < i)
      {
        char c = arrayOfChar[j];
        if (Character.isWhitespace(c))
          return false;
        if (!dontNeedEncoding.get(c))
          return false;
        if (c == '%')
          k = bool;
        j++;
      }
    }
    while (k != 0);
    return false;
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     weibo4android.util.URLEncodeUtils
 * JD-Core Version:    0.6.2
 */