package com.lmax.disruptor;

import com.lmax.disruptor.util.Util;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicBoolean;

public final class WorkerPool<T>
{
  private final RingBuffer<T> ringBuffer;
  private final AtomicBoolean started = new AtomicBoolean(false);
  private final WorkProcessor<?>[] workProcessors;
  private final Sequence workSequence = new Sequence(-1L);

  public WorkerPool(EventFactory<T> paramEventFactory, ClaimStrategy paramClaimStrategy, WaitStrategy paramWaitStrategy, ExceptionHandler paramExceptionHandler, WorkHandler<T>[] paramArrayOfWorkHandler)
  {
    this.ringBuffer = new RingBuffer(paramEventFactory, paramClaimStrategy, paramWaitStrategy);
    SequenceBarrier localSequenceBarrier = this.ringBuffer.newBarrier(new Sequence[0]);
    int i = paramArrayOfWorkHandler.length;
    this.workProcessors = new WorkProcessor[i];
    for (int j = 0; j < i; j++)
      this.workProcessors[j] = new WorkProcessor(this.ringBuffer, localSequenceBarrier, paramArrayOfWorkHandler[j], paramExceptionHandler, this.workSequence);
    this.ringBuffer.setGatingSequences(getWorkerSequences());
  }

  public WorkerPool(RingBuffer<T> paramRingBuffer, SequenceBarrier paramSequenceBarrier, ExceptionHandler paramExceptionHandler, WorkHandler<T>[] paramArrayOfWorkHandler)
  {
    this.ringBuffer = paramRingBuffer;
    int i = paramArrayOfWorkHandler.length;
    this.workProcessors = new WorkProcessor[i];
    for (int j = 0; j < i; j++)
      this.workProcessors[j] = new WorkProcessor(paramRingBuffer, paramSequenceBarrier, paramArrayOfWorkHandler[j], paramExceptionHandler, this.workSequence);
  }

  public void drainAndHalt()
  {
    Sequence[] arrayOfSequence = getWorkerSequences();
    while (this.ringBuffer.getCursor() > Util.getMinimumSequence(arrayOfSequence))
      Thread.yield();
    WorkProcessor[] arrayOfWorkProcessor = this.workProcessors;
    int i = arrayOfWorkProcessor.length;
    for (int j = 0; j < i; j++)
      arrayOfWorkProcessor[j].halt();
    this.started.set(false);
  }

  public Sequence[] getWorkerSequences()
  {
    Sequence[] arrayOfSequence = new Sequence[this.workProcessors.length];
    int i = 0;
    int j = this.workProcessors.length;
    while (i < j)
    {
      arrayOfSequence[i] = this.workProcessors[i].getSequence();
      i++;
    }
    return arrayOfSequence;
  }

  public void halt()
  {
    WorkProcessor[] arrayOfWorkProcessor = this.workProcessors;
    int i = arrayOfWorkProcessor.length;
    for (int j = 0; j < i; j++)
      arrayOfWorkProcessor[j].halt();
    this.started.set(false);
  }

  public RingBuffer<T> start(Executor paramExecutor)
  {
    if (!this.started.compareAndSet(false, true))
      throw new IllegalStateException("WorkerPool has already been started and cannot be restarted until halted.");
    long l = this.ringBuffer.getCursor();
    this.workSequence.set(l);
    for (WorkProcessor localWorkProcessor : this.workProcessors)
    {
      localWorkProcessor.getSequence().set(l);
      paramExecutor.execute(localWorkProcessor);
    }
    return this.ringBuffer;
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.lmax.disruptor.WorkerPool
 * JD-Core Version:    0.6.2
 */