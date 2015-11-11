package com.lmax.disruptor;

public class EventPublisher<E>
{
  private final RingBuffer<E> ringBuffer;

  public EventPublisher(RingBuffer<E> paramRingBuffer)
  {
    this.ringBuffer = paramRingBuffer;
  }

  private void translateAndPublish(EventTranslator<E> paramEventTranslator, long paramLong)
  {
    try
    {
      paramEventTranslator.translateTo(this.ringBuffer.get(paramLong), paramLong);
      return;
    }
    finally
    {
      this.ringBuffer.publish(paramLong);
    }
  }

  public void publishEvent(EventTranslator<E> paramEventTranslator)
  {
    translateAndPublish(paramEventTranslator, this.ringBuffer.next());
  }

  public boolean tryPublishEvent(EventTranslator<E> paramEventTranslator, int paramInt)
  {
    try
    {
      translateAndPublish(paramEventTranslator, this.ringBuffer.tryNext(paramInt));
      return true;
    }
    catch (InsufficientCapacityException localInsufficientCapacityException)
    {
    }
    return false;
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.lmax.disruptor.EventPublisher
 * JD-Core Version:    0.6.2
 */