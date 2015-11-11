package com.tencent.weibo.sdk.android.api.util;

public class HypyUtil
{
  private static int BEGIN = 45217;
  private static int END = 63486;
  private static char[] chartable = { 21834, -32083, 25830, 25645, -30978, 21457, 22134, 21704, 21704, 20987, 21888, 22403, 22920, 25343, 21734, 21866, 26399, 28982, 25746, 22604, 22604, 22604, 25366, 26132, 21387, 21277 };
  private static char[] initialtable = { 97, 98, 99, 100, 101, 102, 103, 104, 104, 106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 116, 116, 119, 120, 121, 122 };
  private static int[] table = new int[27];

  static
  {
    for (int i = 0; ; i++)
    {
      if (i >= 26)
      {
        table[26] = END;
        return;
      }
      table[i] = gbValue(chartable[i]);
    }
  }

  private static char Char2Initial(char paramChar)
  {
    if ((paramChar >= 'a') && (paramChar <= 'z'))
      paramChar = (char)(65 + (paramChar - 'a'));
    int i;
    do
    {
      do
        return paramChar;
      while ((paramChar >= 'A') && (paramChar <= 'Z'));
      i = gbValue(paramChar);
    }
    while ((i < BEGIN) || (i > END));
    for (int j = 0; ; j++)
    {
      if (j >= 26);
      while ((i >= table[j]) && (i < table[(j + 1)]))
      {
        if (i == END)
          j = 25;
        return initialtable[j];
      }
    }
  }

  public static String cn2py(String paramString)
  {
    Object localObject = "";
    int i = paramString.length();
    int j = 0;
    while (true)
    {
      if (j >= i)
        return localObject;
      try
      {
        String str = localObject + Char2Initial(paramString.charAt(j));
        localObject = str;
        j++;
      }
      catch (Exception localException)
      {
      }
    }
    return "";
  }

  private static int gbValue(char paramChar)
  {
    String str = new String() + paramChar;
    try
    {
      byte[] arrayOfByte = str.getBytes("GB2312");
      if (arrayOfByte.length < 2)
        return 0;
      int i = 0xFF00 & arrayOfByte[0] << 8;
      int j = arrayOfByte[1];
      return i + (j & 0xFF);
    }
    catch (Exception localException)
    {
    }
    return 0;
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.tencent.weibo.sdk.android.api.util.HypyUtil
 * JD-Core Version:    0.6.2
 */