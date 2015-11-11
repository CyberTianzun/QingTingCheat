package com.lmax.disruptor;

public abstract interface EventTranslator<T>
{
  public abstract void translateTo(T paramT, long paramLong);
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.lmax.disruptor.EventTranslator
 * JD-Core Version:    0.6.2
 */