package com.alibaba.fastjson.parser.deserializer;

public abstract interface ExtraProcessor extends ParseProcess
{
  public abstract void processExtra(Object paramObject1, String paramString, Object paramObject2);
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.alibaba.fastjson.parser.deserializer.ExtraProcessor
 * JD-Core Version:    0.6.2
 */