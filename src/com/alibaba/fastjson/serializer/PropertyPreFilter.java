package com.alibaba.fastjson.serializer;

public abstract interface PropertyPreFilter extends SerializeFilter
{
  public abstract boolean apply(JSONSerializer paramJSONSerializer, Object paramObject, String paramString);
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.alibaba.fastjson.serializer.PropertyPreFilter
 * JD-Core Version:    0.6.2
 */