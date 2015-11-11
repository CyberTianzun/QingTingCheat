package fm.qingting.framework.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtils
{
  public static String changeCharset(String paramString1, String paramString2)
  {
    if (paramString1 != null);
    try
    {
      String str = new String(paramString1.getBytes(), paramString2);
      paramString1 = str;
      return paramString1;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return paramString1;
  }

  public static <T extends Enum<T>> T getEnumWithString(Class<T> paramClass, String paramString)
  {
    if (paramString == null)
      return null;
    try
    {
      Enum localEnum = Enum.valueOf(paramClass, paramString.toUpperCase());
      return localEnum;
    }
    catch (IllegalArgumentException localIllegalArgumentException)
    {
    }
    return null;
  }

  public static Map<String, Object> searchBody(String paramString, String[] paramArrayOfString1, String[] paramArrayOfString2)
  {
    return searchBody(paramString, paramArrayOfString1, paramArrayOfString2, -1);
  }

  public static Map<String, Object> searchBody(String paramString, String[] paramArrayOfString1, String[] paramArrayOfString2, int paramInt)
  {
    int i = 0;
    int j;
    int k;
    int m;
    int n;
    int i1;
    if (paramInt < 0)
    {
      j = 0;
      k = 0;
      m = 0;
      n = 0;
      i1 = 0;
      if (paramArrayOfString1 == null);
    }
    for (int i3 = 0; ; i3++)
    {
      if (i3 >= paramArrayOfString1.length)
      {
        if (i1 != 0)
          break label108;
        return null;
        j = paramInt;
        k = paramInt;
        m = paramInt;
        n = paramInt;
        break;
      }
      Matcher localMatcher2 = Pattern.compile(paramArrayOfString1[i3]).matcher(paramString);
      if (localMatcher2.find(m))
      {
        i1 = 1;
        n = localMatcher2.start();
        m = localMatcher2.end();
        k = m;
      }
    }
    label108: if (paramArrayOfString2 == null)
      m = paramString.length();
    while (i == 0)
    {
      return null;
      for (int i2 = 0; i2 < paramArrayOfString2.length; i2++)
      {
        Matcher localMatcher1 = Pattern.compile(paramArrayOfString2[i2]).matcher(paramString);
        if (localMatcher1.find(m))
        {
          i = 1;
          j = localMatcher1.start();
          m = localMatcher1.end();
        }
      }
    }
    HashMap localHashMap = new HashMap();
    localHashMap.put("body", paramString.substring(n, m));
    localHashMap.put("content", paramString.substring(k, j));
    localHashMap.put("bodyFrom", Integer.valueOf(n));
    localHashMap.put("bodyTo", Integer.valueOf(m));
    localHashMap.put("contentFrom", Integer.valueOf(k));
    localHashMap.put("contentTo", Integer.valueOf(j));
    return localHashMap;
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.framework.utils.StringUtils
 * JD-Core Version:    0.6.2
 */