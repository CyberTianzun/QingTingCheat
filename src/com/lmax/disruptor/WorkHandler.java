package com.lmax.disruptor;

public abstract interface WorkHandler<T>
{
  public abstract void onEvent(T paramT)
    throws Exception;
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.lmax.disruptor.WorkHandler
 * JD-Core Version:    0.6.2
 */