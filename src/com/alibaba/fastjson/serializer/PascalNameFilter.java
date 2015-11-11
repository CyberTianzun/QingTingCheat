package com.alibaba.fastjson.serializer;

public class PascalNameFilter
  implements NameFilter
{
  public String process(Object paramObject1, String paramString, Object paramObject2)
  {
    if ((paramString == null) || (paramString.length() == 0))
      return paramString;
    char c = Character.toUpperCase(paramString.charAt(0));
    return c + paramString.substring(1);
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.alibaba.fastjson.serializer.PascalNameFilter
 * JD-Core Version:    0.6.2
 */