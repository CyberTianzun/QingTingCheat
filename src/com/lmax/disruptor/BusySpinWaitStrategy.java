package com.lmax.disruptor;

import com.lmax.disruptor.util.Util;
import java.util.concurrent.TimeUnit;

public final class BusySpinWaitStrategy
  implements WaitStrategy
{
  public void signalAllWhenBlocking()
  {
  }

  public long waitFor(long paramLong, Sequence paramSequence, Sequence[] paramArrayOfSequence, SequenceBarrier paramSequenceBarrier)
    throws AlertException, InterruptedException
  {
    long l;
    if (paramArrayOfSequence.length == 0)
      while (true)
      {
        l = paramSequence.get();
        if (l >= paramLong)
          break;
        paramSequenceBarrier.checkAlert();
      }
    while (true)
    {
      l = Util.getMinimumSequence(paramArrayOfSequence);
      if (l >= paramLong)
        break;
      paramSequenceBarrier.checkAlert();
    }
    return l;
  }

  public long waitFor(long paramLong1, Sequence paramSequence, Sequence[] paramArrayOfSequence, SequenceBarrier paramSequenceBarrier, long paramLong2, TimeUnit paramTimeUnit)
    throws AlertException, InterruptedException
  {
    long l1 = paramTimeUnit.toMillis(paramLong2);
    long l2 = System.currentTimeMillis();
    long l3;
    if (paramArrayOfSequence.length == 0)
    {
      do
      {
        l3 = paramSequence.get();
        if (l3 >= paramLong1)
          break;
        paramSequenceBarrier.checkAlert();
      }
      while (System.currentTimeMillis() - l2 <= l1);
      return l3;
    }
    do
    {
      l3 = Util.getMinimumSequence(paramArrayOfSequence);
      if (l3 >= paramLong1)
        break;
      paramSequenceBarrier.checkAlert();
    }
    while (System.currentTimeMillis() - l2 <= l1);
    return l3;
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.lmax.disruptor.BusySpinWaitStrategy
 * JD-Core Version:    0.6.2
 */