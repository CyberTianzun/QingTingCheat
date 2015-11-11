package fm.qingting.async.http.libcore;

final class HeaderParser
{
  public static void parseCacheControl(String paramString, CacheControlHandler paramCacheControlHandler)
  {
    int i = 0;
    while (i < paramString.length())
    {
      int j = skipUntil(paramString, i, "=,");
      String str1 = paramString.substring(i, j).trim();
      if ((j == paramString.length()) || (paramString.charAt(j) == ','))
      {
        i = j + 1;
        paramCacheControlHandler.handle(str1, null);
      }
      else
      {
        int k = skipWhitespace(paramString, j + 1);
        String str3;
        if ((k < paramString.length()) && (paramString.charAt(k) == '"'))
        {
          int n = k + 1;
          int i1 = skipUntil(paramString, n, "\"");
          str3 = paramString.substring(n, i1);
          i = i1 + 1;
        }
        String str2;
        for (Object localObject = str3; ; localObject = str2)
        {
          paramCacheControlHandler.handle(str1, (String)localObject);
          break;
          int m = skipUntil(paramString, k, ",");
          str2 = paramString.substring(k, m).trim();
          i = m;
        }
      }
    }
  }

  public static int parseSeconds(String paramString)
  {
    try
    {
      long l = Long.parseLong(paramString);
      if (l > 2147483647L)
        return 2147483647;
      if (l < 0L)
        return 0;
      return (int)l;
    }
    catch (NumberFormatException localNumberFormatException)
    {
    }
    return -1;
  }

  private static int skipUntil(String paramString1, int paramInt, String paramString2)
  {
    while (true)
    {
      if ((paramInt >= paramString1.length()) || (paramString2.indexOf(paramString1.charAt(paramInt)) != -1))
        return paramInt;
      paramInt++;
    }
  }

  private static int skipWhitespace(String paramString, int paramInt)
  {
    while (true)
    {
      if (paramInt < paramString.length())
      {
        int i = paramString.charAt(paramInt);
        if ((i == 32) || (i == 9));
      }
      else
      {
        return paramInt;
      }
      paramInt++;
    }
  }

  public static abstract interface CacheControlHandler
  {
    public abstract void handle(String paramString1, String paramString2);
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.async.http.libcore.HeaderParser
 * JD-Core Version:    0.6.2
 */