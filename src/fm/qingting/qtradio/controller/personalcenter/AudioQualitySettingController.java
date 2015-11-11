package fm.qingting.qtradio.controller.personalcenter;

import android.content.Context;
import fm.qingting.framework.controller.ViewController;
import fm.qingting.framework.model.INavigationBarListener;
import fm.qingting.framework.model.NavigationBarItem;
import fm.qingting.qtradio.controller.ControllerManager;
import fm.qingting.qtradio.view.navigation.NavigationBarTopView;
import fm.qingting.qtradio.view.settingviews.AudioQualitySettingView;

public class AudioQualitySettingController extends ViewController
  implements INavigationBarListener
{
  private AudioQualitySettingView mainView;

  public AudioQualitySettingController(Context paramContext)
  {
    super(paramContext);
    this.controllerName = "audioSetting";
    this.mainView = new AudioQualitySettingView(paramContext);
    attachView(this.mainView);
    NavigationBarTopView localNavigationBarTopView = new NavigationBarTopView(paramContext);
    localNavigationBarTopView.setLeftItem(0);
    localNavigationBarTopView.setTitleItem(new NavigationBarItem("音质设置"));
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
 * Qualified Name:     fm.qingting.qtradio.controller.personalcenter.AudioQualitySettingController
 * JD-Core Version:    0.6.2
 */