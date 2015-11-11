package com.lmax.disruptor;

import java.util.concurrent.atomic.AtomicBoolean;

public final class BatchEventProcessor<T>
  implements EventProcessor
{
  private final EventHandler<T> eventHandler;
  private ExceptionHandler exceptionHandler = new FatalExceptionHandler();
  private final RingBuffer<T> ringBuffer;
  private final AtomicBoolean running = new AtomicBoolean(false);
  private final Sequence sequence = new Sequence(-1L);
  private final SequenceBarrier sequenceBarrier;

  public BatchEventProcessor(RingBuffer<T> paramRingBuffer, SequenceBarrier paramSequenceBarrier, EventHandler<T> paramEventHandler)
  {
    this.ringBuffer = paramRingBuffer;
    this.sequenceBarrier = paramSequenceBarrier;
    this.eventHandler = paramEventHandler;
    if ((paramEventHandler instanceof SequenceReportingEventHandler))
      ((SequenceReportingEventHandler)paramEventHandler).setSequenceCallback(this.sequence);
  }

  private void notifyShutdown()
  {
    if ((this.eventHandler instanceof LifecycleAware));
    try
    {
      ((LifecycleAware)this.eventHandler).onShutdown();
      return;
    }
    catch (Throwable localThrowable)
    {
      this.exceptionHandler.handleOnShutdownException(localThrowable);
    }
  }

  private void notifyStart()
  {
    if ((this.eventHandler instanceof LifecycleAware));
    try
    {
      ((LifecycleAware)this.eventHandler).onStart();
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
    Object localObject = null;
    long l1 = 1L + this.sequence.get();
    while (true)
    {
      try
      {
        long l2 = this.sequenceBarrier.waitFor(l1);
        if (l1 <= l2)
        {
          localObject = this.ringBuffer.get(l1);
          EventHandler localEventHandler = this.eventHandler;
          if (l1 != l2)
            break label177;
          bool = true;
          localEventHandler.onEvent(localObject, l1, bool);
          l1 += 1L;
          continue;
        }
        this.sequence.set(l1 - 1L);
        continue;
      }
      catch (AlertException localAlertException)
      {
        if (this.running.get())
          continue;
        notifyShutdown();
        this.running.set(false);
        return;
      }
      catch (Throwable localThrowable)
      {
        this.exceptionHandler.handleEventException(localThrowable, l1, localObject);
        this.sequence.set(l1);
        l1 += 1L;
      }
      continue;
      label177: boolean bool = false;
    }
  }

  public void setExceptionHandler(ExceptionHandler paramExceptionHandler)
  {
    if (paramExceptionHandler == null)
      throw new NullPointerException();
    this.exceptionHandler = paramExceptionHandler;
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.lmax.disruptor.BatchEventProcessor
 * JD-Core Version:    0.6.2
 */