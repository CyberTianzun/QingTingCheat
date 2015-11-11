package com.lmax.disruptor;

import java.util.concurrent.atomic.AtomicLongArray;

public final class MultiThreadedClaimStrategy extends AbstractMultithreadedClaimStrategy
  implements ClaimStrategy
{
  private static final int RETRIES = 1000;
  private final int pendingMask;
  private final AtomicLongArray pendingPublication;

  public MultiThreadedClaimStrategy(int paramInt)
  {
    this(paramInt, 1024);
  }

  public MultiThreadedClaimStrategy(int paramInt1, int paramInt2)
  {
    super(paramInt1);
    if (Integer.bitCount(paramInt2) != 1)
      throw new IllegalArgumentException("pendingBufferSize must be a power of 2, was: " + paramInt2);
    this.pendingPublication = new AtomicLongArray(paramInt2);
    this.pendingMask = (paramInt2 - 1);
  }

  public void serialisePublishing(long paramLong, Sequence paramSequence, int paramInt)
  {
    int i = 1000;
    while (paramLong - paramSequence.get() > this.pendingPublication.length())
    {
      i--;
      if (i == 0)
      {
        Thread.yield();
        i = 1000;
      }
    }
    long l1 = paramLong - paramInt;
    for (long l2 = l1 + 1L; l2 < paramLong; l2 += 1L)
      this.pendingPublication.lazySet((int)l2 & this.pendingMask, l2);
    this.pendingPublication.set((int)paramLong & this.pendingMask, paramLong);
    long l3 = paramSequence.get();
    if (l3 >= paramLong);
    long l5;
    do
    {
      return;
      while (!paramSequence.compareAndSet(l4, l5))
      {
        l4 = Math.max(l1, l3);
        l5 = l4 + 1L;
      }
      long l4 = l5;
      l5 += 1L;
    }
    while (this.pendingPublication.get((int)l5 & this.pendingMask) == l5);
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.lmax.disruptor.MultiThreadedClaimStrategy
 * JD-Core Version:    0.6.2
 */