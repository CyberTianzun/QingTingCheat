package com.lmax.disruptor.util;

public class MutableLong
{
  private long value = 0L;

  public MutableLong()
  {
  }

  public MutableLong(long paramLong)
  {
    this.value = paramLong;
  }

  public long get()
  {
    return this.value;
  }

  public void set(long paramLong)
  {
    this.value = paramLong;
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.lmax.disruptor.util.MutableLong
 * JD-Core Version:    0.6.2
 */