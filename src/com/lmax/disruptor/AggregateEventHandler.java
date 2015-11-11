package com.lmax.disruptor;

public final class AggregateEventHandler<T>
  implements EventHandler<T>, LifecycleAware
{
  private final EventHandler<T>[] eventHandlers;

  public AggregateEventHandler(EventHandler<T>[] paramArrayOfEventHandler)
  {
    this.eventHandlers = paramArrayOfEventHandler;
  }

  public void onEvent(T paramT, long paramLong, boolean paramBoolean)
    throws Exception
  {
    EventHandler[] arrayOfEventHandler = this.eventHandlers;
    int i = arrayOfEventHandler.length;
    for (int j = 0; j < i; j++)
      arrayOfEventHandler[j].onEvent(paramT, paramLong, paramBoolean);
  }

  public void onShutdown()
  {
    for (EventHandler localEventHandler : this.eventHandlers)
      if ((localEventHandler instanceof LifecycleAware))
        ((LifecycleAware)localEventHandler).onShutdown();
  }

  public void onStart()
  {
    for (EventHandler localEventHandler : this.eventHandlers)
      if ((localEventHandler instanceof LifecycleAware))
        ((LifecycleAware)localEventHandler).onStart();
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.lmax.disruptor.AggregateEventHandler
 * JD-Core Version:    0.6.2
 */