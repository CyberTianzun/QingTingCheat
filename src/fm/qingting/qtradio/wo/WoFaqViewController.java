package fm.qingting.qtradio.wo;

import android.content.Context;
import fm.qingting.framework.controller.ViewController;
import fm.qingting.framework.event.IEventHandler;
import fm.qingting.framework.model.INavigationBarListener;
import fm.qingting.framework.model.NavigationBarItem;
import fm.qingting.qtradio.controller.ControllerManager;
import fm.qingting.qtradio.view.navigation.NavigationBarTopView;

public class WoFaqViewController extends ViewController
  implements INavigationBarListener, IEventHandler
{
  private WoFaqView faqView = null;

  public WoFaqViewController(Context paramContext)
  {
    super(paramContext);
    this.controllerName = "wofaq";
    this.faqView = new WoFaqView(paramContext);
    attachView(this.faqView);
    NavigationBarTopView localNavigationBarTopView = new NavigationBarTopView(paramContext);
    localNavigationBarTopView.setLeftItem(0);
    localNavigationBarTopView.setTitleItem(new NavigationBarItem("蜻蜓流量包"));
    localNavigationBarTopView.setBarListener(this);
    setNavigationBar(localNavigationBarTopView);
  }

  public void onEvent(Object paramObject1, String paramString, Object paramObject2)
  {
  }

  public void onItemClick(int paramInt)
  {
    if (paramInt == 2)
      ControllerManager.getInstance().popLastController();
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.qtradio.wo.WoFaqViewController
 * JD-Core Version:    0.6.2
 */