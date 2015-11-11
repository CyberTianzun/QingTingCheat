package com.tencent.stat;

abstract interface StatDispatchCallback
{
  public abstract void onDispatchFailure();

  public abstract void onDispatchSuccess();
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.tencent.stat.StatDispatchCallback
 * JD-Core Version:    0.6.2
 */