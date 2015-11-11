package fm.qingting.qtradio.model;

import android.content.Context;
import android.widget.FrameLayout;
import fm.qingting.framework.controller.ViewController;
import fm.qingting.framework.view.INavigationSetting;
import fm.qingting.framework.view.INavigationSetting.Mode;
import fm.qingting.framework.view.IView;
import fm.qingting.framework.view.IViewModel;
import fm.qingting.qtradio.view.viewmodel.NavigationBarModel;

public class NavigationSettingController
  implements INavigationSetting
{
  public IViewModel getLayoutView(Context paramContext, INavigationSetting.Mode paramMode)
  {
    return new NavigationBarModel(paramContext, paramMode);
  }

  public void navigationWillPopped(ViewController paramViewController, FrameLayout paramFrameLayout, IView paramIView)
  {
  }

  public void navigationWillPushed(ViewController paramViewController, FrameLayout paramFrameLayout, IView paramIView)
  {
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.qtradio.model.NavigationSettingController
 * JD-Core Version:    0.6.2
 */