package com.alibaba.fastjson;

import java.lang.reflect.Type;
import java.util.List;

public class TypeReference<T>
{
  public static final Type LIST_STRING = new TypeReference()
  {
  }
  .getType();
  private final Type type = ((java.lang.reflect.ParameterizedType)getClass().getGenericSuperclass()).getActualTypeArguments()[0];

  public Type getType()
  {
    return this.type;
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.alibaba.fastjson.TypeReference
 * JD-Core Version:    0.6.2
 */