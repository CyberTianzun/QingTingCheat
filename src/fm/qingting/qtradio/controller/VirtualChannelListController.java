package fm.qingting.qtradio.controller;

import android.content.Context;
import fm.qingting.framework.controller.ViewController;
import fm.qingting.framework.model.INavigationBarListener;
import fm.qingting.framework.model.NavigationBarItem;
import fm.qingting.qtradio.model.Attribute;
import fm.qingting.qtradio.view.navigation.NavigationBarCustomView;
import fm.qingting.qtradio.view.virtualchannellist.VirtualChannelListByAttrView;

public class VirtualChannelListController extends ViewController
  implements INavigationBarListener
{
  public static final String NAME = "virtualchannellist";
  private NavigationBarCustomView mBarCustomView;
  private VirtualChannelListByAttrView mainView;

  public VirtualChannelListController(Context paramContext)
  {
    super(paramContext);
    this.mBarCustomView = new NavigationBarCustomView(paramContext);
    this.mBarCustomView.addLeftItem(0);
    this.mBarCustomView.addRightItem(1);
    this.mBarCustomView.setBarListener(this);
    setNavigationBar(this.mBarCustomView);
    this.mainView = new VirtualChannelListByAttrView(paramContext);
    attachView(this.mainView);
  }

  public void config(String paramString, Object paramObject)
  {
    if (paramString.equalsIgnoreCase("setData"))
    {
      localAttribute = (Attribute)paramObject;
      this.mBarCustomView.setTitleItem(new NavigationBarItem(localAttribute.name));
      this.mainView.update(paramString, paramObject);
    }
    while (!paramString.equalsIgnoreCase("setNode"))
    {
      Attribute localAttribute;
      return;
    }
    this.mainView.update(paramString, paramObject);
  }

  public void controllerDidPopped()
  {
    this.mainView.close(false);
    super.controllerDidPopped();
  }

  public boolean hasMiniPlayer()
  {
    return true;
  }

  public void onItemClick(int paramInt)
  {
    if (paramInt == 2)
      ControllerManager.getInstance().popLastController();
    while (paramInt != 3)
      return;
    ControllerManager.getInstance().redirectToSearchView(false);
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.qtradio.controller.VirtualChannelListController
 * JD-Core Version:    0.6.2
 */