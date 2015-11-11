package com.umeng.message.proguard;

public class f
{
  public static String a(Object paramObject)
  {
    if (paramObject != null)
    {
      if ((paramObject instanceof String))
        return ((String)paramObject).toString();
      if ((paramObject instanceof Integer))
        return "" + ((Integer)paramObject).intValue();
      if ((paramObject instanceof Long))
        return "" + ((Long)paramObject).longValue();
      if ((paramObject instanceof Double))
        return "" + ((Double)paramObject).doubleValue();
      if ((paramObject instanceof Float))
        return "" + ((Float)paramObject).floatValue();
      if ((paramObject instanceof Short))
        return "" + ((Short)paramObject).shortValue();
      if ((paramObject instanceof Byte))
        return "" + ((Byte)paramObject).byteValue();
      if ((paramObject instanceof Boolean))
        return ((Boolean)paramObject).toString();
      if ((paramObject instanceof Character))
        return ((Character)paramObject).toString();
      return paramObject.toString();
    }
    return "";
  }

  public static boolean a(String paramString)
  {
    return (paramString == null) || (paramString.length() <= 0);
  }

  public static int b(String paramString)
  {
    int i = paramString.length();
    int j = 0;
    if (i > 0)
    {
      char[] arrayOfChar = paramString.toCharArray();
      int k = 0;
      while (j < arrayOfChar.length)
      {
        k = k * 31 + arrayOfChar[j];
        j++;
      }
      j = k;
    }
    return j;
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.umeng.message.proguard.f
 * JD-Core Version:    0.6.2
 */