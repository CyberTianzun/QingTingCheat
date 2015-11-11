package fm.qingting.framework.controller;

import android.content.Context;
import android.view.View;
import android.widget.FrameLayout;
import fm.qingting.framework.view.FrameLayoutViewImpl;
import fm.qingting.framework.view.INavigationSetting;
import fm.qingting.framework.view.IView;
import fm.qingting.framework.view.IViewModel;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class NavigationController extends ViewController
  implements SwitchAnimationListener
{
  public static final int OPTION_TYPE_POP = 1;
  public static final int OPTION_TYPE_PUSH = 2;
  private FrameLayoutViewImpl container;
  private LinkedList<NavigationItem> controllerStack = new LinkedList();
  private boolean isAnimation = false;
  private INavigationEventListener navigationEventListener;
  private NavigationItem navigationItem;
  private INavigationSetting navigationSetting;
  private ISwitchAnimation popAnimation;
  private ISwitchAnimation pushAnimation;

  public NavigationController(Context paramContext)
  {
    this(paramContext, new NavigationControllerContainerView(paramContext));
  }

  public NavigationController(Context paramContext, FrameLayoutViewImpl paramFrameLayoutViewImpl)
  {
    super(paramContext, paramFrameLayoutViewImpl);
    this.container = paramFrameLayoutViewImpl;
  }

  private void dispatchPopEvent(ViewController paramViewController, boolean paramBoolean)
  {
    ArrayList localArrayList = new ArrayList();
    localArrayList.add(paramViewController);
    dispatchPopEvent(localArrayList, paramBoolean);
  }

  private void dispatchPopEvent(List<ViewController> paramList, boolean paramBoolean)
  {
    if (this.navigationEventListener == null)
      return;
    this.navigationEventListener.onPopControllers(paramList, paramBoolean);
  }

  private void dispatchPushEvent(ViewController paramViewController, boolean paramBoolean)
  {
    if (this.navigationEventListener == null)
      return;
    this.navigationEventListener.onPushController(paramViewController, paramBoolean);
  }

  private void removerStackController(ViewController paramViewController)
  {
  }

  public void controllerPopEnd(ViewController paramViewController)
  {
    ViewController localViewController = ((NavigationItem)this.controllerStack.getLast()).viewController;
    if (localViewController != null)
      localViewController.controllerReappeared();
    paramViewController.controllerDidPopped();
  }

  public List<ViewController> getAllControllers()
  {
    ArrayList localArrayList = new ArrayList();
    Iterator localIterator = this.controllerStack.iterator();
    while (true)
    {
      if (!localIterator.hasNext())
        return localArrayList;
      localArrayList.add(((NavigationItem)localIterator.next()).viewController);
    }
  }

  public List<ViewController> getAllHiddenControllers()
  {
    ArrayList localArrayList = new ArrayList();
    Iterator localIterator = this.controllerStack.iterator();
    while (true)
    {
      if (!localIterator.hasNext())
        return localArrayList;
      NavigationItem localNavigationItem = (NavigationItem)localIterator.next();
      if (localIterator.hasNext())
        localArrayList.add(localNavigationItem.viewController);
    }
  }

  public int getCount()
  {
    return this.controllerStack.size();
  }

  public ViewController getLastViewController()
  {
    if (this.controllerStack.getLast() == null)
      return null;
    return ((NavigationItem)this.controllerStack.getLast()).viewController;
  }

  public ViewController getRootViewController()
  {
    return ((NavigationItem)this.controllerStack.getFirst()).viewController;
  }

  public FrameLayoutViewImpl getViewContainer()
  {
    return this.container;
  }

  public ViewController getViewController(int paramInt)
  {
    return ((NavigationItem)this.controllerStack.get(paramInt)).viewController;
  }

  public boolean isAnimating()
  {
    return this.isAnimation;
  }

  protected void onDestroy()
  {
    Iterator localIterator = getAllControllers().iterator();
    while (true)
    {
      if (!localIterator.hasNext())
      {
        this.controllerStack.clear();
        this.controllerStack = null;
        this.container.removeAllViews();
        this.container = null;
        this.navigationEventListener = null;
        if (this.popAnimation != null)
        {
          this.popAnimation.destory();
          this.popAnimation = null;
        }
        if (this.pushAnimation != null)
        {
          this.pushAnimation.destory();
          this.pushAnimation = null;
        }
        this.navigationSetting = null;
        super.onDestroy();
        return;
      }
      ((ViewController)localIterator.next()).destroy();
    }
  }

  public void popToRootViewController(boolean paramBoolean)
  {
    if (this.isAnimation);
    while (this.controllerStack.size() <= 1)
      return;
    NavigationItem localNavigationItem1 = (NavigationItem)this.controllerStack.removeLast();
    ViewController localViewController = localNavigationItem1.viewController;
    Object localObject;
    IView localIView1;
    label77: NavigationItem localNavigationItem2;
    IView localIView2;
    if (paramBoolean)
    {
      localObject = localNavigationItem1.popAnimation;
      ((ISwitchAnimation)localObject).setSwitchAnimationListener(this);
      this.popAnimation = ((ISwitchAnimation)localObject);
      localViewController.setNavigationController(null);
      if (localViewController.getShellView() != null)
        break label229;
      localIView1 = localViewController.getView();
      localIView1.setActivate(false);
      localViewController.stopActivate();
      localNavigationItem2 = (NavigationItem)this.controllerStack.getFirst();
      if (localNavigationItem2.viewController.getShellView() == null)
        break label238;
      localIView2 = localNavigationItem2.viewController.getShellView();
      label122: localIView2.setActivate(true);
      if (this.container.indexOfChild(localIView2.getView()) >= 0)
        break label251;
      this.container.addView(localIView2.getView(), this.container.getChildCount());
    }
    while (true)
    {
      this.controllerStack.clear();
      this.controllerStack.add(localNavigationItem2);
      ((ISwitchAnimation)localObject).setPopingController(localViewController);
      ((ISwitchAnimation)localObject).startAnimation(this.container, localIView2, localIView1, paramBoolean, 1);
      dispatchPopEvent(localViewController, paramBoolean);
      return;
      localObject = new PopAnimation();
      break;
      label229: localIView1 = localViewController.getShellView();
      break label77;
      label238: localIView2 = localNavigationItem2.viewController.getView();
      break label122;
      label251: this.container.bringChildToFront(localIView2.getView());
    }
  }

  public void popToRootViewControllerUsingAnimation(ISwitchAnimation paramISwitchAnimation)
  {
    if (paramISwitchAnimation == null);
    while ((this.isAnimation) || (this.controllerStack.size() <= 1))
      return;
    ViewController localViewController = ((NavigationItem)this.controllerStack.removeLast()).viewController;
    paramISwitchAnimation.setSwitchAnimationListener(this);
    this.popAnimation = paramISwitchAnimation;
    localViewController.setNavigationController(null);
    IView localIView1;
    NavigationItem localNavigationItem;
    IView localIView2;
    if (localViewController.getShellView() == null)
    {
      localIView1 = localViewController.getView();
      localIView1.setActivate(false);
      localViewController.stopActivate();
      localNavigationItem = (NavigationItem)this.controllerStack.getFirst();
      if (localNavigationItem.viewController.getShellView() == null)
        break label210;
      localIView2 = localNavigationItem.viewController.getShellView();
      label110: localIView2.setActivate(true);
      if (this.container.indexOfChild(localIView2.getView()) >= 0)
        break label223;
      this.container.addView(localIView2.getView(), this.container.getChildCount());
    }
    while (true)
    {
      this.controllerStack.clear();
      this.controllerStack.add(localNavigationItem);
      paramISwitchAnimation.setPopingController(localViewController);
      paramISwitchAnimation.startAnimation(this.container, localIView2, localIView1, true, 1);
      dispatchPopEvent(localViewController, true);
      return;
      localIView1 = localViewController.getShellView();
      break;
      label210: localIView2 = localNavigationItem.viewController.getView();
      break label110;
      label223: this.container.bringChildToFront(localIView2.getView());
    }
  }

  public void popToViewController(int paramInt, boolean paramBoolean)
  {
    if (this.isAnimation);
    while (this.controllerStack.size() <= paramInt)
      return;
    ArrayList localArrayList = new ArrayList();
    NavigationItem localNavigationItem1 = (NavigationItem)this.controllerStack.removeLast();
    ViewController localViewController = localNavigationItem1.viewController;
    localArrayList.add(localViewController);
    ISwitchAnimation localISwitchAnimation = localNavigationItem1.popAnimation;
    localISwitchAnimation.setSwitchAnimationListener(this);
    this.popAnimation = localISwitchAnimation;
    localViewController.setNavigationController(null);
    IView localIView1;
    NavigationItem localNavigationItem2;
    IView localIView2;
    if (localViewController.getShellView() == null)
    {
      localIView1 = localViewController.getView();
      localIView1.setActivate(false);
      localViewController.stopActivate();
      localNavigationItem2 = (NavigationItem)this.controllerStack.get(paramInt);
      if (localNavigationItem2.viewController.getShellView() == null)
        break label250;
      localIView2 = localNavigationItem2.viewController.getShellView();
      label144: localIView2.setActivate(true);
      if (this.container.indexOfChild(localIView2.getView()) >= 0)
        break label263;
      this.container.addView(localIView2.getView(), this.container.getChildCount());
    }
    label190: for (int i = this.controllerStack.size(); ; i--)
    {
      if (i <= paramInt + 1)
      {
        localISwitchAnimation.setPopingController(localViewController);
        localISwitchAnimation.startAnimation(this.container, localIView2, localIView1, paramBoolean, 1);
        dispatchPopEvent(localArrayList, paramBoolean);
        return;
        localIView1 = localViewController.getShellView();
        break;
        label250: localIView2 = localNavigationItem2.viewController.getView();
        break label144;
        this.container.bringChildToFront(localIView2.getView());
        break label190;
      }
      localArrayList.add(((NavigationItem)this.controllerStack.removeLast()).viewController);
    }
  }

  public void popViewController()
  {
    popViewController(true);
  }

  public void popViewController(boolean paramBoolean)
  {
    if (this.isAnimation);
    while (this.controllerStack.size() <= 1)
      return;
    NavigationItem localNavigationItem = (NavigationItem)this.controllerStack.removeLast();
    ViewController localViewController = localNavigationItem.viewController;
    ISwitchAnimation localISwitchAnimation = localNavigationItem.popAnimation;
    localISwitchAnimation.setSwitchAnimationListener(this);
    this.popAnimation = localISwitchAnimation;
    localViewController.setNavigationController(null);
    IView localIView1;
    IView localIView3;
    if (localViewController.getShellView() == null)
    {
      localIView1 = localViewController.getView();
      localIView1.setActivate(false);
      localViewController.stopActivate();
      IView localIView2 = ((NavigationItem)this.controllerStack.getLast()).viewController.getShellView();
      if (localIView2 == null)
        break label267;
      localIView3 = localIView2;
      label112: localIView3.setActivate(true);
      if (this.navigationSetting != null)
      {
        int i = -2 + this.controllerStack.size();
        if (i >= 0)
          this.navigationSetting.navigationWillPopped(((NavigationItem)this.controllerStack.get(i)).viewController, this.container, localIView3);
      }
      this.container.bringChildToFront(localIView1.getView());
      if (this.container.indexOfChild(localIView3.getView()) >= 0)
        break label288;
      this.container.addView(localIView3.getView(), this.container.getChildCount());
    }
    while (true)
    {
      localISwitchAnimation.setPopingController(localViewController);
      localISwitchAnimation.startAnimation(this.container, localIView3, localIView1, paramBoolean, 1);
      dispatchPopEvent(localViewController, paramBoolean);
      return;
      localIView1 = localViewController.getShellView();
      break;
      label267: localIView3 = ((NavigationItem)this.controllerStack.getLast()).viewController.getView();
      break label112;
      label288: this.container.bringChildToFront(localIView3.getView());
    }
  }

  public void popViewControllerShell(boolean paramBoolean)
  {
    if (this.isAnimation);
    while (this.controllerStack.size() <= 1)
      return;
    NavigationItem localNavigationItem = (NavigationItem)this.controllerStack.removeLast();
    ViewController localViewController = localNavigationItem.viewController;
    ISwitchAnimation localISwitchAnimation = localNavigationItem.popAnimation;
    IView localIView1;
    IView localIView2;
    IView localIView4;
    if (localViewController.getShellView() == null)
    {
      localIView1 = localViewController.getView();
      localISwitchAnimation.setSwitchAnimationListener(this);
      this.popAnimation = localISwitchAnimation;
      localViewController.setNavigationController(null);
      localIView2 = localIView1;
      localViewController.stopActivate();
      localViewController.controllerDidPopped();
      IView localIView3 = ((NavigationItem)this.controllerStack.getLast()).viewController.getShellView();
      if (localIView3 == null)
        break label245;
      localIView4 = localIView3;
      label112: if (this.navigationSetting != null)
      {
        int i = -2 + this.controllerStack.size();
        if (i >= 0)
          this.navigationSetting.navigationWillPopped(((NavigationItem)this.controllerStack.get(i)).viewController, this.container, localIView4);
      }
      localIView4.setActivate(true);
      if (this.container.indexOfChild(localIView4.getView()) >= 0)
        break label266;
      this.container.addView(localIView4.getView(), this.container.getChildCount());
    }
    while (true)
    {
      localISwitchAnimation.startAnimation(this.container, localIView4, localIView2, paramBoolean, 1);
      dispatchPopEvent(localViewController, paramBoolean);
      return;
      localIView1 = localViewController.getShellView();
      break;
      label245: localIView4 = ((NavigationItem)this.controllerStack.getLast()).viewController.getView();
      break label112;
      label266: this.container.bringChildToFront(localIView4.getView());
    }
  }

  public void pushViewController(ViewController paramViewController)
  {
    pushViewController(paramViewController, true);
  }

  public void pushViewController(ViewController paramViewController, boolean paramBoolean)
  {
    pushViewController(paramViewController, paramBoolean, null, null);
  }

  public void pushViewController(ViewController paramViewController, boolean paramBoolean, ISwitchAnimation paramISwitchAnimation1, ISwitchAnimation paramISwitchAnimation2)
  {
    if ((paramViewController == null) || (this.isAnimation));
    while (this.popAnimation != null)
      return;
    ViewController localViewController;
    label32: IView localIView1;
    label138: IView localIView2;
    if (this.controllerStack.size() == 0)
    {
      localViewController = null;
      if (localViewController == paramViewController)
        break label277;
      if (paramISwitchAnimation1 == null)
        paramISwitchAnimation1 = new PushAnimation();
      if (paramISwitchAnimation2 == null)
        paramISwitchAnimation2 = new PopAnimation();
      paramISwitchAnimation1.setSwitchAnimationListener(this);
      this.pushAnimation = paramISwitchAnimation1;
      IViewModel localIViewModel = this.navigationSetting.getLayoutView(getContext(), paramViewController.getNavigationBarMode());
      if ((localIViewModel != null) && (paramViewController.getShellView() == null))
        localIViewModel.LayoutView(paramViewController);
      localIView1 = null;
      if (localViewController != null)
      {
        if (localViewController.getShellView() == null)
          break label279;
        localIView1 = localViewController.getShellView();
        localIView1.setActivate(false);
        localViewController.stopActivate();
      }
      if (paramViewController.getShellView() == null)
        break label289;
      localIView2 = paramViewController.getShellView();
      label164: localIView2.setActivate(true);
      if (this.container.indexOfChild(localIView2.getView()) >= 0)
        break label298;
      this.container.addView(localIView2.getView(), this.container.getChildCount());
    }
    while (true)
    {
      paramViewController.setNavigationController(this);
      this.navigationItem = null;
      this.navigationItem = new NavigationItem(paramViewController, paramISwitchAnimation2);
      FrameLayoutViewImpl localFrameLayoutViewImpl = this.container;
      paramISwitchAnimation1.startAnimation(localFrameLayoutViewImpl, localIView2, localIView1, paramBoolean, 2);
      dispatchPushEvent(paramViewController, paramBoolean);
      return;
      localViewController = ((NavigationItem)this.controllerStack.getLast()).viewController;
      break label32;
      label277: break;
      label279: localIView1 = localViewController.getView();
      break label138;
      label289: localIView2 = paramViewController.getView();
      break label164;
      label298: this.container.bringChildToFront(localIView2.getView());
    }
  }

  public void pushViewController(ViewController paramViewController, boolean paramBoolean, ISwitchAnimation paramISwitchAnimation1, ISwitchAnimation paramISwitchAnimation2, String paramString)
  {
    if ((paramViewController == null) || (this.isAnimation));
    while (this.popAnimation != null)
      return;
    ViewController localViewController;
    label32: IView localIView1;
    label140: IView localIView2;
    if (this.controllerStack.size() == 0)
    {
      localViewController = null;
      if (localViewController == paramViewController)
        break label283;
      if (paramISwitchAnimation1 == null)
        paramISwitchAnimation1 = new PushAnimation();
      if (paramISwitchAnimation2 == null)
        paramISwitchAnimation2 = new PopAnimation(paramString);
      paramISwitchAnimation1.setSwitchAnimationListener(this);
      this.pushAnimation = paramISwitchAnimation1;
      IViewModel localIViewModel = this.navigationSetting.getLayoutView(getContext(), paramViewController.getNavigationBarMode());
      if ((localIViewModel != null) && (paramViewController.getShellView() == null))
        localIViewModel.LayoutView(paramViewController);
      localIView1 = null;
      if (localViewController != null)
      {
        if (localViewController.getShellView() == null)
          break label285;
        localIView1 = localViewController.getShellView();
        localIView1.setActivate(false);
        localViewController.stopActivate();
      }
      if (paramViewController.getShellView() == null)
        break label295;
      localIView2 = paramViewController.getShellView();
      label166: localIView2.setActivate(true);
      if (this.container.indexOfChild(localIView2.getView()) >= 0)
        break label304;
      this.container.addView(localIView2.getView(), this.container.getChildCount());
    }
    while (true)
    {
      paramViewController.setNavigationController(this);
      paramViewController.controllerDidPushed();
      this.navigationItem = null;
      this.navigationItem = new NavigationItem(paramViewController, paramISwitchAnimation2);
      FrameLayoutViewImpl localFrameLayoutViewImpl = this.container;
      paramISwitchAnimation1.startAnimation(localFrameLayoutViewImpl, localIView2, localIView1, paramBoolean, 2);
      dispatchPushEvent(paramViewController, paramBoolean);
      return;
      localViewController = ((NavigationItem)this.controllerStack.getLast()).viewController;
      break label32;
      label283: break;
      label285: localIView1 = localViewController.getView();
      break label140;
      label295: localIView2 = paramViewController.getView();
      break label166;
      label304: this.container.bringChildToFront(localIView2.getView());
    }
  }

  public ViewController removeController(int paramInt)
  {
    if (this.controllerStack.size() <= paramInt)
      return null;
    return ((NavigationItem)this.controllerStack.remove(paramInt)).viewController;
  }

  public void setNavigationEventListener(INavigationEventListener paramINavigationEventListener)
  {
    this.navigationEventListener = paramINavigationEventListener;
  }

  public void setNavigationSetting(INavigationSetting paramINavigationSetting)
  {
    this.navigationSetting = paramINavigationSetting;
  }

  public void setRootController(ViewController paramViewController, String paramString)
  {
    pushViewController(paramViewController);
  }

  public void switchEnd(ISwitchAnimation paramISwitchAnimation, final FrameLayout paramFrameLayout, IView paramIView1, final IView paramIView2, boolean paramBoolean, int paramInt)
  {
    if ((paramInt == 2) && (this.navigationItem != null))
    {
      this.navigationItem.viewController.controllerDidPushed();
      removerStackController(this.navigationItem.viewController);
      this.controllerStack.add(this.navigationItem);
    }
    if (paramInt == 2)
    {
      if (paramIView2 != null)
      {
        paramIView2.getView().clearAnimation();
        paramIView2.getView().setVisibility(8);
      }
      this.container.bringChildToFront(paramIView1.getView());
    }
    while (true)
    {
      if (this.pushAnimation != null)
      {
        this.pushAnimation.destory();
        this.pushAnimation = null;
      }
      if (this.popAnimation != null)
      {
        this.popAnimation.destory();
        this.popAnimation = null;
      }
      this.isAnimation = false;
      return;
      if ((paramIView2 != null) && (paramFrameLayout.indexOfChild(paramIView2.getView()) >= 0))
      {
        paramIView2.getView().clearAnimation();
        paramIView2.getView().setVisibility(8);
        this.container.post(new Runnable()
        {
          public void run()
          {
            paramFrameLayout.removeView(paramIView2.getView());
          }
        });
      }
    }
  }

  public void switchStart(ISwitchAnimation paramISwitchAnimation, FrameLayout paramFrameLayout, IView paramIView1, IView paramIView2, boolean paramBoolean, int paramInt)
  {
    this.isAnimation = true;
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.framework.controller.NavigationController
 * JD-Core Version:    0.6.2
 */