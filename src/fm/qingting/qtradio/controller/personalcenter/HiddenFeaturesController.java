package fm.qingting.qtradio.controller.personalcenter;

import android.content.Context;
import fm.qingting.framework.controller.ViewController;
import fm.qingting.framework.model.INavigationBarListener;
import fm.qingting.framework.model.NavigationBarItem;
import fm.qingting.qtradio.controller.ControllerManager;
import fm.qingting.qtradio.view.navigation.NavigationBarTopView;
import fm.qingting.qtradio.view.personalcenter.hiddenfeatures.HiddenFeaturesView;

public class HiddenFeaturesController extends ViewController
  implements INavigationBarListener
{
  private HiddenFeaturesView mFeaturesView;

  public HiddenFeaturesController(Context paramContext)
  {
    super(paramContext);
    this.controllerName = "hiddenfeatures";
    NavigationBarTopView localNavigationBarTopView = new NavigationBarTopView(paramContext);
    localNavigationBarTopView.setLeftItem(0);
    localNavigationBarTopView.setTitleItem(new NavigationBarItem("Hidden Features"));
    setNavigationBar(localNavigationBarTopView);
    localNavigationBarTopView.setBarListener(this);
    this.mFeaturesView = new HiddenFeaturesView(paramContext);
    attachView(this.mFeaturesView);
  }

  public void config(String paramString, Object paramObject)
  {
    if (paramString.equalsIgnoreCase("setData"))
      this.mFeaturesView.update(paramString, paramObject);
  }

  public void controllerDidPopped()
  {
    this.mFeaturesView.close(false);
    super.controllerDidPopped();
  }

  public void onItemClick(int paramInt)
  {
    if (paramInt == 2)
      ControllerManager.getInstance().popLastController();
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.qtradio.controller.personalcenter.HiddenFeaturesController
 * JD-Core Version:    0.6.2
 */