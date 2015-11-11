package com.lmax.disruptor;

import com.lmax.disruptor.util.Util;
import java.util.concurrent.TimeUnit;

public final class YieldingWaitStrategy
  implements WaitStrategy
{
  private static final int SPIN_TRIES = 100;

  private int applyWaitMethod(SequenceBarrier paramSequenceBarrier, int paramInt)
    throws AlertException
  {
    paramSequenceBarrier.checkAlert();
    if (paramInt == 0)
    {
      Thread.yield();
      return paramInt;
    }
    return paramInt - 1;
  }

  public void signalAllWhenBlocking()
  {
  }

  public long waitFor(long paramLong, Sequence paramSequence, Sequence[] paramArrayOfSequence, SequenceBarrier paramSequenceBarrier)
    throws AlertException, InterruptedException
  {
    int i = 100;
    long l;
    if (paramArrayOfSequence.length == 0)
      while (true)
      {
        l = paramSequence.get();
        if (l >= paramLong)
          break;
        i = applyWaitMethod(paramSequenceBarrier, i);
      }
    while (true)
    {
      l = Util.getMinimumSequence(paramArrayOfSequence);
      if (l >= paramLong)
        break;
      i = applyWaitMethod(paramSequenceBarrier, i);
    }
    return l;
  }

  public long waitFor(long paramLong1, Sequence paramSequence, Sequence[] paramArrayOfSequence, SequenceBarrier paramSequenceBarrier, long paramLong2, TimeUnit paramTimeUnit)
    throws AlertException, InterruptedException
  {
    long l1 = paramTimeUnit.toMillis(paramLong2);
    long l2 = System.currentTimeMillis();
    int i = 100;
    long l3;
    if (paramArrayOfSequence.length == 0)
    {
      do
      {
        l3 = paramSequence.get();
        if (l3 >= paramLong1)
          break;
        i = applyWaitMethod(paramSequenceBarrier, i);
      }
      while (System.currentTimeMillis() - l2 <= l1);
      return l3;
    }
    do
    {
      l3 = Util.getMinimumSequence(paramArrayOfSequence);
      if (l3 >= paramLong1)
        break;
      i = applyWaitMethod(paramSequenceBarrier, i);
    }
    while (System.currentTimeMillis() - l2 <= l1);
    return l3;
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.lmax.disruptor.YieldingWaitStrategy
 * JD-Core Version:    0.6.2
 */