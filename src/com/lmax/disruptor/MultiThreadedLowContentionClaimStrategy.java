package com.lmax.disruptor;

public final class MultiThreadedLowContentionClaimStrategy extends AbstractMultithreadedClaimStrategy
{
  public MultiThreadedLowContentionClaimStrategy(int paramInt)
  {
    super(paramInt);
  }

  public void serialisePublishing(long paramLong, Sequence paramSequence, int paramInt)
  {
    long l = paramLong - paramInt;
    while (l != paramSequence.get());
    paramSequence.set(paramLong);
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.lmax.disruptor.MultiThreadedLowContentionClaimStrategy
 * JD-Core Version:    0.6.2
 */