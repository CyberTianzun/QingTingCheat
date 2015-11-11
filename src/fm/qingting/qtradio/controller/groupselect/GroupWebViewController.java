package fm.qingting.qtradio.controller.groupselect;

import android.content.Context;
import fm.qingting.framework.controller.ViewController;
import fm.qingting.framework.event.IEventHandler;
import fm.qingting.framework.manager.EventDispacthManager;
import fm.qingting.framework.model.INavigationBarListener;
import fm.qingting.framework.model.NavigationBarItem;
import fm.qingting.framework.view.IView;
import fm.qingting.qtradio.controller.ControllerManager;
import fm.qingting.qtradio.fm.WebViewPlayer;
import fm.qingting.qtradio.model.ActivityNode;
import fm.qingting.qtradio.view.groupselect.GroupWebView;
import fm.qingting.qtradio.view.navigation.NavigationBarTopView;

public class GroupWebViewController extends ViewController
  implements IEventHandler, INavigationBarListener
{
  private NavigationBarTopView barTopView;
  private GroupWebView mGroupWebView;
  private ActivityNode mNode = null;

  public GroupWebViewController(Context paramContext)
  {
    super(paramContext);
  }

  public GroupWebViewController(Context paramContext, IView paramIView, ActivityNode paramActivityNode)
  {
    super(paramContext, paramIView);
    this.controllerName = "webView";
    this.barTopView = new NavigationBarTopView(paramContext);
    this.barTopView.setLeftItem(0);
    if ((paramActivityNode != null) && (paramActivityNode.hasShared))
      this.barTopView.setRightItem("分享");
    String str1 = paramActivityNode.name;
    if ((str1 != null) && (str1.length() > 10))
    {
      String str2 = str1.substring(0, 10);
      str1 = str2 + "...";
    }
    this.barTopView.setTitleItem(new NavigationBarItem(str1));
    this.barTopView.setBarListener(this);
    this.mNode = paramActivityNode;
    setNavigationBar(this.barTopView);
    this.mGroupWebView = ((GroupWebView)paramIView);
  }

  private void initData()
  {
  }

  public void config(String paramString, Object paramObject)
  {
    if (paramString.equalsIgnoreCase("setTitle"));
  }

  public void controllerDidPopped()
  {
    if (this.mGroupWebView != null)
    {
      this.mGroupWebView.release();
      this.mGroupWebView.destroy();
    }
    super.controllerDidPopped();
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
    default:
    case 2:
    case 3:
    }
    do
    {
      return;
      if (this.mGroupWebView != null)
      {
        if (this.mGroupWebView.canBack())
        {
          this.mGroupWebView.goBack();
          return;
        }
        this.mGroupWebView.destroy();
      }
      ControllerManager.getInstance().popLastController();
      return;
    }
    while ((this.mNode == null) || (!this.mNode.hasShared));
    EventDispacthManager.getInstance().dispatchAction("shareChoose", this.mNode);
    WebViewPlayer.getInstance().callback();
  }

  public void viewWillClose(IView paramIView)
  {
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.qtradio.controller.groupselect.GroupWebViewController
 * JD-Core Version:    0.6.2
 */