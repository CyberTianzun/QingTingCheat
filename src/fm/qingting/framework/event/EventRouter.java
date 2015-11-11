package fm.qingting.framework.event;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public final class EventRouter
{
  private static EventRouter _instance;
  private Map<String, Set<IEventHandler>> _handlerMap = new HashMap();

  private void checkMap(String paramString)
  {
    if (!this._handlerMap.containsKey(paramString))
      this._handlerMap.put(paramString, new HashSet());
  }

  public static EventRouter getInstance()
  {
    try
    {
      if (_instance == null)
        _instance = new EventRouter();
      EventRouter localEventRouter = _instance;
      return localEventRouter;
    }
    finally
    {
    }
  }

  public void addEventListener(String paramString, IEventHandler paramIEventHandler)
  {
    if ((paramIEventHandler == null) || (paramString == null))
      throw new NullTypeHandlerException(null);
    synchronized (this._handlerMap)
    {
      checkMap(paramString);
      ((Set)this._handlerMap.get(paramString)).add(paramIEventHandler);
      return;
    }
  }

  public void dispatchEvent(AppEvent paramAppEvent)
  {
    if (this._handlerMap.get(paramAppEvent.type) == null);
    while (true)
    {
      return;
      HashSet localHashSet = new HashSet();
      synchronized (this._handlerMap)
      {
        localHashSet.addAll((Collection)this._handlerMap.get(paramAppEvent.type));
        Iterator localIterator = localHashSet.iterator();
        if (!localIterator.hasNext())
          continue;
        ((IEventHandler)localIterator.next()).onEvent(paramAppEvent.source, paramAppEvent.type, paramAppEvent.param);
      }
    }
  }

  public void removeEventListener(String paramString, IEventHandler paramIEventHandler)
  {
    synchronized (this._handlerMap)
    {
      checkMap(paramString);
      ((Set)this._handlerMap.get(paramString)).remove(paramIEventHandler);
      return;
    }
  }

  private class NullTypeHandlerException extends RuntimeException
  {
    private static final long serialVersionUID = 7009684814333539406L;

    private NullTypeHandlerException()
    {
    }

    public String getMessage()
    {
      return "type or handler is null";
    }
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.framework.event.EventRouter
 * JD-Core Version:    0.6.2
 */