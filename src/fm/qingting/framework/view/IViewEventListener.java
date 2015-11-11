package fm.qingting.framework.view;

public abstract interface IViewEventListener
{
  public abstract void viewDidClosed(IView paramIView);

  public abstract void viewDidOpened(IView paramIView);

  public abstract void viewWillClose(IView paramIView);

  public abstract void viewWillOpen(IView paramIView);
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.framework.view.IViewEventListener
 * JD-Core Version:    0.6.2
 */