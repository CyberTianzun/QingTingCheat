package com.alibaba.fastjson.parser.deserializer;

import java.lang.reflect.Type;
import java.util.Set;

public abstract interface AutowiredObjectDeserializer extends ObjectDeserializer
{
  public abstract Set<Type> getAutowiredFor();
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.alibaba.fastjson.parser.deserializer.AutowiredObjectDeserializer
 * JD-Core Version:    0.6.2
 */