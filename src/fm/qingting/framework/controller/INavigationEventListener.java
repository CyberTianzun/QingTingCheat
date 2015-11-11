package fm.qingting.framework.controller;

import java.util.List;

public abstract interface INavigationEventListener
{
  public abstract void onPopControllers(List<ViewController> paramList, boolean paramBoolean);

  public abstract void onPushController(ViewController paramViewController, boolean paramBoolean);
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.framework.controller.INavigationEventListener
 * JD-Core Version:    0.6.2
 */