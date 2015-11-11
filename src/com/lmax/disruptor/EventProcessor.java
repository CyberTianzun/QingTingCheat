package com.lmax.disruptor;

public abstract interface EventProcessor extends Runnable
{
  public abstract Sequence getSequence();

  public abstract void halt();
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.lmax.disruptor.EventProcessor
 * JD-Core Version:    0.6.2
 */