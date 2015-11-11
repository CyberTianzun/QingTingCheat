package com.lmax.disruptor;

public abstract interface EventFactory<T>
{
  public abstract T newInstance();
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.lmax.disruptor.EventFactory
 * JD-Core Version:    0.6.2
 */