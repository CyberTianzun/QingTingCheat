package fm.qingting.qtradio.controller.personalcenter;

import android.content.Context;
import fm.qingting.framework.controller.ViewController;
import fm.qingting.framework.model.INavigationBarListener;
import fm.qingting.framework.model.NavigationBarItem;
import fm.qingting.qtradio.controller.ControllerManager;
import fm.qingting.qtradio.view.navigation.NavigationBarTopView;
import fm.qingting.qtradio.view.settingviews.PushMessageSettingView;

public class PushMessageSettingController extends ViewController
  implements INavigationBarListener
{
  private PushMessageSettingView mainView;

  public PushMessageSettingController(Context paramContext)
  {
    super(paramContext);
    this.controllerName = "pushmessageSetting";
    this.mainView = new PushMessageSettingView(paramContext);
    attachView(this.mainView);
    NavigationBarTopView localNavigationBarTopView = new NavigationBarTopView(paramContext);
    localNavigationBarTopView.setLeftItem(0);
    localNavigationBarTopView.setTitleItem(new NavigationBarItem("消息推送"));
    localNavigationBarTopView.setBarListener(this);
    setNavigationBar(localNavigationBarTopView);
  }

  public void config(String paramString, Object paramObject)
  {
    if (paramString.equalsIgnoreCase("setData"))
      this.mainView.update(paramString, paramObject);
  }

  public void controllerDidPopped()
  {
    this.mainView.close(false);
    super.controllerDidPopped();
  }

  public void onItemClick(int paramInt)
  {
    if (paramInt == 2)
      ControllerManager.getInstance().popLastController();
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.qtradio.controller.personalcenter.PushMessageSettingController
 * JD-Core Version:    0.6.2
 */