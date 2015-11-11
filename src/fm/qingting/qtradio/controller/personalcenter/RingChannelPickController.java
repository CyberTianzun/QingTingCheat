package fm.qingting.qtradio.controller.personalcenter;

import android.content.Context;
import fm.qingting.framework.controller.ViewController;
import fm.qingting.framework.model.INavigationBarListener;
import fm.qingting.framework.model.NavigationBarItem;
import fm.qingting.qtradio.controller.ControllerManager;
import fm.qingting.qtradio.model.CollectionNode;
import fm.qingting.qtradio.model.InfoManager;
import fm.qingting.qtradio.model.PersonalCenterNode;
import fm.qingting.qtradio.model.RootNode;
import fm.qingting.qtradio.view.navigation.NavigationBarTopView;
import fm.qingting.qtradio.view.personalcenter.clock.ringtonesetting.RingPickListView;

public class RingChannelPickController extends ViewController
  implements INavigationBarListener
{
  private RingPickListView mainView;

  public RingChannelPickController(Context paramContext)
  {
    super(paramContext);
    this.controllerName = "alarmchannelpick";
    this.mainView = new RingPickListView(paramContext);
    attachView(this.mainView);
    NavigationBarTopView localNavigationBarTopView = new NavigationBarTopView(paramContext);
    localNavigationBarTopView.setLeftItem(0);
    localNavigationBarTopView.setTitleItem(new NavigationBarItem("播放电台"));
    localNavigationBarTopView.setBarListener(this);
    setNavigationBar(localNavigationBarTopView);
  }

  public void config(String paramString, Object paramObject)
  {
    if (paramString.equalsIgnoreCase("setData"))
      this.mainView.update(paramString, InfoManager.getInstance().root().mPersonalCenterNode.myCollectionNode.getFavouriteNodes());
    do
    {
      return;
      if (paramString.equalsIgnoreCase("setRingtone"))
      {
        this.mainView.update(paramString, paramObject);
        return;
      }
    }
    while (!paramString.equalsIgnoreCase("setRingChannel"));
    this.mainView.update(paramString, paramObject);
  }

  public void onItemClick(int paramInt)
  {
    switch (paramInt)
    {
    default:
      return;
    case 2:
    }
    ControllerManager.getInstance().popLastController();
  }

  protected void onViewEvent(Object paramObject1, String paramString, Object paramObject2)
  {
    if (paramString.equalsIgnoreCase("select"))
    {
      dispatchEvent(paramString, paramObject2);
      ControllerManager.getInstance().popLastController();
    }
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.qtradio.controller.personalcenter.RingChannelPickController
 * JD-Core Version:    0.6.2
 */