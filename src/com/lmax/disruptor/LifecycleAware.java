package com.lmax.disruptor;

public abstract interface LifecycleAware
{
  public abstract void onShutdown();

  public abstract void onStart();
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.lmax.disruptor.LifecycleAware
 * JD-Core Version:    0.6.2
 */