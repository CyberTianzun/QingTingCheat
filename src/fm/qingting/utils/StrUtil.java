package fm.qingting.utils;

public class StrUtil
{
  public static String trimAll(String paramString)
  {
    if (paramString == null)
      return paramString;
    StringBuffer localStringBuffer = new StringBuffer();
    String str = paramString.trim();
    for (int i = 0; i < str.length(); i++)
    {
      char c = str.charAt(i);
      if ((c != '\n') && (c != '\r') && (c != '\t'))
        localStringBuffer.append(c);
    }
    return localStringBuffer.toString();
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.utils.StrUtil
 * JD-Core Version:    0.6.2
 */