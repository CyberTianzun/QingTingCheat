package com.lmax.disruptor;

public final class RingBuffer<T> extends Sequencer
{
  private final Object[] entries;
  private final int indexMask;

  public RingBuffer(EventFactory<T> paramEventFactory, int paramInt)
  {
    this(paramEventFactory, new MultiThreadedClaimStrategy(paramInt), new BlockingWaitStrategy());
  }

  public RingBuffer(EventFactory<T> paramEventFactory, ClaimStrategy paramClaimStrategy, WaitStrategy paramWaitStrategy)
  {
    super(paramClaimStrategy, paramWaitStrategy);
    if (Integer.bitCount(paramClaimStrategy.getBufferSize()) != 1)
      throw new IllegalArgumentException("bufferSize must be a power of 2");
    this.indexMask = (-1 + paramClaimStrategy.getBufferSize());
    this.entries = new Object[paramClaimStrategy.getBufferSize()];
    fill(paramEventFactory);
  }

  private void fill(EventFactory<T> paramEventFactory)
  {
    for (int i = 0; i < this.entries.length; i++)
      this.entries[i] = paramEventFactory.newInstance();
  }

  public T get(long paramLong)
  {
    return this.entries[((int)paramLong & this.indexMask)];
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.lmax.disruptor.RingBuffer
 * JD-Core Version:    0.6.2
 */