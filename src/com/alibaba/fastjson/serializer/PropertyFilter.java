package com.alibaba.fastjson.serializer;

public abstract interface PropertyFilter extends SerializeFilter
{
  public abstract boolean apply(Object paramObject1, String paramString, Object paramObject2);
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.alibaba.fastjson.serializer.PropertyFilter
 * JD-Core Version:    0.6.2
 */