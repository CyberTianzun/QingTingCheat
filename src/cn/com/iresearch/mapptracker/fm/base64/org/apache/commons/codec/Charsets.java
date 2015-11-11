package cn.com.iresearch.mapptracker.fm.base64.org.apache.commons.codec;

import java.nio.charset.Charset;

public class Charsets
{
  public static final Charset ISO_8859_1 = Charset.forName("ISO-8859-1");
  public static final Charset US_ASCII = Charset.forName("US-ASCII");
  public static final Charset UTF_16 = Charset.forName("UTF-16");
  public static final Charset UTF_16BE = Charset.forName("UTF-16BE");
  public static final Charset UTF_16LE = Charset.forName("UTF-16LE");
  public static final Charset UTF_8 = Charset.forName("UTF-8");

  public static Charset toCharset(String paramString)
  {
    if (paramString == null)
      return Charset.defaultCharset();
    return Charset.forName(paramString);
  }

  public static Charset toCharset(Charset paramCharset)
  {
    if (paramCharset == null)
      paramCharset = Charset.defaultCharset();
    return paramCharset;
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     cn.com.iresearch.mapptracker.fm.base64.org.apache.commons.codec.Charsets
 * JD-Core Version:    0.6.2
 */