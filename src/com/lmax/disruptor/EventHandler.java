package com.lmax.disruptor;

public abstract interface EventHandler<T>
{
  public abstract void onEvent(T paramT, long paramLong, boolean paramBoolean)
    throws Exception;
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.lmax.disruptor.EventHandler
 * JD-Core Version:    0.6.2
 */