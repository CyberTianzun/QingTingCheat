package org.apache.commons.httpclient.util;

import org.apache.commons.httpclient.NameValuePair;

public class ParameterFormatter
{
  private static final char[] SEPARATORS = { 40, 41, 60, 62, 64, 44, 59, 58, 92, 34, 47, 91, 93, 63, 61, 123, 125, 32, 9 };
  private static final char[] UNSAFE_CHARS = { 34, 92 };
  private boolean alwaysUseQuotes = true;

  public static void formatValue(StringBuffer paramStringBuffer, String paramString, boolean paramBoolean)
  {
    if (paramStringBuffer == null)
      throw new IllegalArgumentException("String buffer may not be null");
    if (paramString == null)
      throw new IllegalArgumentException("Value buffer may not be null");
    if (paramBoolean)
    {
      paramStringBuffer.append('"');
      for (int m = 0; ; m++)
      {
        if (m >= paramString.length())
        {
          paramStringBuffer.append('"');
          return;
        }
        char c2 = paramString.charAt(m);
        if (isUnsafeChar(c2))
          paramStringBuffer.append('\\');
        paramStringBuffer.append(c2);
      }
    }
    int i = paramStringBuffer.length();
    int j = 0;
    for (int k = 0; ; k++)
    {
      if (k >= paramString.length())
      {
        if (j == 0)
          break;
        paramStringBuffer.insert(i, '"');
        paramStringBuffer.append('"');
        return;
      }
      char c1 = paramString.charAt(k);
      if (isSeparator(c1))
        j = 1;
      if (isUnsafeChar(c1))
        paramStringBuffer.append('\\');
      paramStringBuffer.append(c1);
    }
  }

  private static boolean isOneOf(char[] paramArrayOfChar, char paramChar)
  {
    for (int i = 0; ; i++)
    {
      if (i >= paramArrayOfChar.length)
        return false;
      if (paramChar == paramArrayOfChar[i])
        return true;
    }
  }

  private static boolean isSeparator(char paramChar)
  {
    return isOneOf(SEPARATORS, paramChar);
  }

  private static boolean isUnsafeChar(char paramChar)
  {
    return isOneOf(UNSAFE_CHARS, paramChar);
  }

  public String format(NameValuePair paramNameValuePair)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    format(localStringBuffer, paramNameValuePair);
    return localStringBuffer.toString();
  }

  public void format(StringBuffer paramStringBuffer, NameValuePair paramNameValuePair)
  {
    if (paramStringBuffer == null)
      throw new IllegalArgumentException("String buffer may not be null");
    if (paramNameValuePair == null)
      throw new IllegalArgumentException("Parameter may not be null");
    paramStringBuffer.append(paramNameValuePair.getName());
    String str = paramNameValuePair.getValue();
    if (str != null)
    {
      paramStringBuffer.append("=");
      formatValue(paramStringBuffer, str, this.alwaysUseQuotes);
    }
  }

  public boolean isAlwaysUseQuotes()
  {
    return this.alwaysUseQuotes;
  }

  public void setAlwaysUseQuotes(boolean paramBoolean)
  {
    this.alwaysUseQuotes = paramBoolean;
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     org.apache.commons.httpclient.util.ParameterFormatter
 * JD-Core Version:    0.6.2
 */