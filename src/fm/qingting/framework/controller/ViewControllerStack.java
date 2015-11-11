package fm.qingting.framework.controller;

import fm.qingting.framework.view.IView;
import java.util.ArrayList;
import java.util.List;

public class ViewControllerStack
{
  private List<ViewController> controllerStack = new ArrayList();

  public void clear()
  {
    this.controllerStack.clear();
  }

  public List<ViewController> getAllControllers()
  {
    return this.controllerStack.subList(0, this.controllerStack.size());
  }

  public List<ViewController> getAllHiddenControllers()
  {
    return this.controllerStack.subList(0, -1 + this.controllerStack.size());
  }

  public int getCount()
  {
    return this.controllerStack.size();
  }

  public ViewController getLastViewController()
  {
    if (this.controllerStack.size() == 0)
      return null;
    return (ViewController)this.controllerStack.get(-1 + this.controllerStack.size());
  }

  public ViewController getRootViewController()
  {
    if (this.controllerStack.size() == 0)
      return null;
    return (ViewController)this.controllerStack.get(0);
  }

  public ViewController getViewController(int paramInt)
  {
    if (paramInt < 0)
      return null;
    if (paramInt >= getCount())
      return getLastViewController();
    return (ViewController)this.controllerStack.get(paramInt);
  }

  public List<ViewController> popToRootViewController()
  {
    ArrayList localArrayList = new ArrayList();
    if (this.controllerStack.size() <= 1)
      return localArrayList;
    int i = this.controllerStack.size();
    for (int j = 1; ; j++)
    {
      if (j >= i)
      {
        ViewController localViewController2 = (ViewController)this.controllerStack.get(0);
        localViewController2.getView().setActivate(true);
        this.controllerStack = new ArrayList();
        this.controllerStack.add(localViewController2);
        return localArrayList;
      }
      ViewController localViewController1 = (ViewController)this.controllerStack.get(j);
      localViewController1.getView().setActivate(false);
      localArrayList.add(localViewController1);
    }
  }

  public ViewController popViewController()
  {
    ViewController localViewController;
    if (this.controllerStack.size() == 0)
      localViewController = null;
    do
    {
      return localViewController;
      localViewController = (ViewController)this.controllerStack.remove(-1 + this.controllerStack.size());
      localViewController.getView().setActivate(false);
    }
    while (this.controllerStack.size() <= 0);
    ((ViewController)this.controllerStack.get(-1 + this.controllerStack.size())).getView().setActivate(true);
    return localViewController;
  }

  public void pushViewController(ViewController paramViewController)
  {
    if (paramViewController == null);
    IView localIView;
    do
    {
      return;
      if (this.controllerStack.size() > 0)
        ((ViewController)this.controllerStack.get(-1 + this.controllerStack.size())).getView().setActivate(false);
      this.controllerStack.add(paramViewController);
      localIView = paramViewController.getView();
    }
    while (localIView == null);
    localIView.setActivate(true);
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.framework.controller.ViewControllerStack
 * JD-Core Version:    0.6.2
 */