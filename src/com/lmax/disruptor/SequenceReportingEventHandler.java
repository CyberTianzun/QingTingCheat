package com.lmax.disruptor;

public abstract interface SequenceReportingEventHandler<T> extends EventHandler<T>
{
  public abstract void setSequenceCallback(Sequence paramSequence);
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.lmax.disruptor.SequenceReportingEventHandler
 * JD-Core Version:    0.6.2
 */