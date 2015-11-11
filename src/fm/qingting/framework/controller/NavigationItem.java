package fm.qingting.framework.controller;

class NavigationItem
{
  public ISwitchAnimation popAnimation;
  public ViewController viewController;

  public NavigationItem(ViewController paramViewController, ISwitchAnimation paramISwitchAnimation)
  {
    this.viewController = paramViewController;
    this.popAnimation = paramISwitchAnimation;
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.framework.controller.NavigationItem
 * JD-Core Version:    0.6.2
 */