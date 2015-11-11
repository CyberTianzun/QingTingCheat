package com.lmax.disruptor;

import com.lmax.disruptor.util.Util;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

public final class SleepingWaitStrategy
  implements WaitStrategy
{
  private static final int RETRIES = 200;

  private int applyWaitMethod(SequenceBarrier paramSequenceBarrier, int paramInt)
    throws AlertException
  {
    paramSequenceBarrier.checkAlert();
    if (paramInt > 100)
      return paramInt - 1;
    if (paramInt > 0)
    {
      int i = paramInt - 1;
      Thread.yield();
      return i;
    }
    LockSupport.parkNanos(1L);
    return paramInt;
  }

  public void signalAllWhenBlocking()
  {
  }

  public long waitFor(long paramLong, Sequence paramSequence, Sequence[] paramArrayOfSequence, SequenceBarrier paramSequenceBarrier)
    throws AlertException, InterruptedException
  {
    int i = 200;
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
    int i = 200;
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
 * Qualified Name:     com.lmax.disruptor.SleepingWaitStrategy
 * JD-Core Version:    0.6.2
 */