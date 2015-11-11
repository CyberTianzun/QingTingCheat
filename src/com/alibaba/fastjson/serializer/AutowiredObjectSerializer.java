package com.alibaba.fastjson.serializer;

import java.lang.reflect.Type;
import java.util.Set;

public abstract interface AutowiredObjectSerializer extends ObjectSerializer
{
  public abstract Set<Type> getAutowiredFor();
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.alibaba.fastjson.serializer.AutowiredObjectSerializer
 * JD-Core Version:    0.6.2
 */