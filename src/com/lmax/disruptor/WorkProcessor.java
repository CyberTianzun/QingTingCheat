package com.lmax.disruptor;

import java.util.concurrent.atomic.AtomicBoolean;

public final class WorkProcessor<T>
  implements EventProcessor
{
  private final ExceptionHandler exceptionHandler;
  private final RingBuffer<T> ringBuffer;
  private final AtomicBoolean running = new AtomicBoolean(false);
  private final Sequence sequence = new Sequence(-1L);
  private final SequenceBarrier sequenceBarrier;
  private final WorkHandler<T> workHandler;
  private final Sequence workSequence;

  public WorkProcessor(RingBuffer<T> paramRingBuffer, SequenceBarrier paramSequenceBarrier, WorkHandler<T> paramWorkHandler, ExceptionHandler paramExceptionHandler, Sequence paramSequence)
  {
    this.ringBuffer = paramRingBuffer;
    this.sequenceBarrier = paramSequenceBarrier;
    this.workHandler = paramWorkHandler;
    this.exceptionHandler = paramExceptionHandler;
    this.workSequence = paramSequence;
  }

  private void notifyShutdown()
  {
    if ((this.workHandler instanceof LifecycleAware));
    try
    {
      ((LifecycleAware)this.workHandler).onShutdown();
      return;
    }
    catch (Throwable localThrowable)
    {
      this.exceptionHandler.handleOnShutdownException(localThrowable);
    }
  }

  private void notifyStart()
  {
    if ((this.workHandler instanceof LifecycleAware));
    try
    {
      ((LifecycleAware)this.workHandler).onStart();
      return;
    }
    catch (Throwable localThrowable)
    {
      this.exceptionHandler.handleOnStartException(localThrowable);
    }
  }

  public Sequence getSequence()
  {
    return this.sequence;
  }

  public void halt()
  {
    this.running.set(false);
    this.sequenceBarrier.alert();
  }

  public void run()
  {
    if (!this.running.compareAndSet(false, true))
      throw new IllegalStateException("Thread is already running");
    this.sequenceBarrier.clearAlert();
    notifyStart();
    int i = 1;
    long l = this.sequence.get();
    Object localObject = null;
    while (true)
    {
      if (i != 0)
        i = 0;
      try
      {
        l = this.workSequence.incrementAndGet();
        this.sequence.set(l - 1L);
        this.sequenceBarrier.waitFor(l);
        localObject = this.ringBuffer.get(l);
        this.workHandler.onEvent(localObject);
        i = 1;
      }
      catch (AlertException localAlertException)
      {
        if (!this.running.get())
        {
          notifyShutdown();
          this.running.set(false);
          return;
        }
      }
      catch (Throwable localThrowable)
      {
        this.exceptionHandler.handleEventException(localThrowable, l, localObject);
        i = 1;
      }
    }
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.lmax.disruptor.WorkProcessor
 * JD-Core Version:    0.6.2
 */