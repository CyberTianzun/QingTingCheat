package com.lmax.disruptor;

import com.lmax.disruptor.util.Util;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public final class BlockingWaitStrategy
  implements WaitStrategy
{
  private final Lock lock = new ReentrantLock();
  private volatile int numWaiters = 0;
  private final Condition processorNotifyCondition = this.lock.newCondition();

  public void signalAllWhenBlocking()
  {
    if (this.numWaiters != 0)
      this.lock.lock();
    try
    {
      this.processorNotifyCondition.signalAll();
      return;
    }
    finally
    {
      this.lock.unlock();
    }
  }

  public long waitFor(long paramLong, Sequence paramSequence, Sequence[] paramArrayOfSequence, SequenceBarrier paramSequenceBarrier)
    throws AlertException, InterruptedException
  {
    long l = paramSequence.get();
    if (l < paramLong)
    {
      this.lock.lock();
      try
      {
        this.numWaiters = (1 + this.numWaiters);
        while (true)
        {
          l = paramSequence.get();
          if (l >= paramLong)
            break;
          paramSequenceBarrier.checkAlert();
          this.processorNotifyCondition.await(1L, TimeUnit.MILLISECONDS);
        }
      }
      finally
      {
        this.numWaiters = (-1 + this.numWaiters);
        this.lock.unlock();
      }
      this.numWaiters = (-1 + this.numWaiters);
      this.lock.unlock();
    }
    if (paramArrayOfSequence.length != 0)
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
    long l = paramSequence.get();
    if (l < paramLong1)
      this.lock.lock();
    try
    {
      this.numWaiters = (1 + this.numWaiters);
      boolean bool;
      do
      {
        l = paramSequence.get();
        if (l >= paramLong1)
          break;
        paramSequenceBarrier.checkAlert();
        bool = this.processorNotifyCondition.await(paramLong2, paramTimeUnit);
      }
      while (bool);
      this.numWaiters = (-1 + this.numWaiters);
      this.lock.unlock();
      if (paramArrayOfSequence.length != 0)
        while (true)
        {
          l = Util.getMinimumSequence(paramArrayOfSequence);
          if (l >= paramLong1)
            break;
          paramSequenceBarrier.checkAlert();
        }
    }
    finally
    {
      this.numWaiters = (-1 + this.numWaiters);
      this.lock.unlock();
    }
    return l;
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.lmax.disruptor.BlockingWaitStrategy
 * JD-Core Version:    0.6.2
 */