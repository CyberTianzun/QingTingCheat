package fm.qingting.qtradio.controller.groupselect;

import android.content.Context;
import fm.qingting.framework.controller.ViewController;
import fm.qingting.framework.event.IEventHandler;
import fm.qingting.framework.model.INavigationBarListener;
import fm.qingting.framework.model.NavigationBarItem;
import fm.qingting.framework.view.IView;
import fm.qingting.qtradio.controller.ControllerManager;
import fm.qingting.qtradio.view.navigation.NavigationBarTopView;
import fm.qingting.qtradio.view.navigation.items.NavigationEditableView;

public class GroupWebViewWorkFlowController extends ViewController
  implements IEventHandler, INavigationBarListener
{
  private NavigationBarTopView barTopView;
  private NavigationEditableView titleView;

  public GroupWebViewWorkFlowController(Context paramContext)
  {
    super(paramContext);
  }

  public GroupWebViewWorkFlowController(Context paramContext, IView paramIView)
  {
    super(paramContext, paramIView);
    this.controllerName = "workflow";
    this.titleView = new NavigationEditableView(paramContext);
    this.barTopView = new NavigationBarTopView(paramContext);
    this.barTopView.setLeftItem(0);
    this.barTopView.setTitleItem(new NavigationBarItem(this.titleView));
    this.barTopView.setBarListener(this);
    setNavigationBar(this.barTopView);
  }

  public void config(String paramString, Object paramObject)
  {
    if (paramString.equalsIgnoreCase("setTitle"))
    {
      String str = (String)paramObject;
      this.titleView.update("set", str);
    }
  }

  public void onDestroy()
  {
    super.onDestroy();
  }

  public void onEvent(Object paramObject1, String paramString, Object paramObject2)
  {
  }

  public void onItemClick(int paramInt)
  {
    switch (paramInt)
    {
    case 3:
    default:
      return;
    case 2:
    }
    ControllerManager.getInstance().popLastController();
  }

  public void viewWillClose(IView paramIView)
  {
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.qtradio.controller.groupselect.GroupWebViewWorkFlowController
 * JD-Core Version:    0.6.2
 */