package com.lmax.disruptor;

public final class BatchDescriptor
{
  private long end = -1L;
  private final int size;

  BatchDescriptor(int paramInt)
  {
    this.size = paramInt;
  }

  public long getEnd()
  {
    return this.end;
  }

  public int getSize()
  {
    return this.size;
  }

  public long getStart()
  {
    return this.end - (this.size - 1L);
  }

  void setEnd(long paramLong)
  {
    this.end = paramLong;
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.lmax.disruptor.BatchDescriptor
 * JD-Core Version:    0.6.2
 */