package fm.qingting.framework.manager;

import java.util.ArrayList;
import java.util.List;

public enum EventDispacthManager
{
  private List<IActionEventHandler> listeners = null;

  static
  {
    EventDispacthManager[] arrayOfEventDispacthManager = new EventDispacthManager[1];
    arrayOfEventDispacthManager[0] = INSTANCE;
  }

  public static EventDispacthManager getInstance()
  {
    return INSTANCE;
  }

  public void addListener(IActionEventHandler paramIActionEventHandler)
  {
    if (this.listeners == null)
      this.listeners = new ArrayList();
    this.listeners.add(paramIActionEventHandler);
  }

  public void dispatchAction(String paramString, Object paramObject)
  {
    if (this.listeners == null);
    while (true)
    {
      return;
      for (int i = 0; i < this.listeners.size(); i++)
        ((IActionEventHandler)this.listeners.get(i)).onAction(paramString, paramObject);
    }
  }

  public void removeAll()
  {
    if (this.listeners != null)
    {
      this.listeners.clear();
      this.listeners = null;
    }
  }

  public void removeListener(IActionEventHandler paramIActionEventHandler)
  {
    if (this.listeners == null)
      return;
    this.listeners.remove(paramIActionEventHandler);
  }

  public static abstract interface IActionEventHandler
  {
    public abstract void onAction(String paramString, Object paramObject);
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.framework.manager.EventDispacthManager
 * JD-Core Version:    0.6.2
 */