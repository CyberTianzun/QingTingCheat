package com.alibaba.fastjson.parser.deserializer;

import java.lang.reflect.Type;

public abstract interface ExtraTypeProvider extends ParseProcess
{
  public abstract Type getExtraType(Object paramObject, String paramString);
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.alibaba.fastjson.parser.deserializer.ExtraTypeProvider
 * JD-Core Version:    0.6.2
 */