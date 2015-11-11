package fm.qingting.qtradio.controller.personalcenter;

import android.content.Context;
import fm.qingting.framework.controller.ViewController;
import fm.qingting.framework.model.INavigationBarListener;
import fm.qingting.framework.model.NavigationBarItem;
import fm.qingting.qtradio.controller.ControllerManager;
import fm.qingting.qtradio.view.navigation.NavigationBarTopView;
import fm.qingting.qtradio.view.personalcenter.clock.daysetting.AlarmDaySettingView;

public class AlarmDaySettingController extends ViewController
  implements INavigationBarListener
{
  private NavigationBarTopView barTopView;
  private AlarmDaySettingView mainView;
  private boolean repeat = true;

  public AlarmDaySettingController(Context paramContext)
  {
    super(paramContext);
    this.controllerName = "daysetting";
    this.mainView = new AlarmDaySettingView(paramContext);
    attachView(this.mainView);
    this.barTopView = new NavigationBarTopView(paramContext);
    this.barTopView.setLeftItem(0);
    this.barTopView.setTitleItem(new NavigationBarItem("重复"));
    this.barTopView.setRightItem(2);
    this.barTopView.setBarListener(this);
    setNavigationBar(this.barTopView);
  }

  private void confirmDay(int paramInt)
  {
    dispatchEvent("day", Integer.valueOf(paramInt));
    ControllerManager.getInstance().popLastController();
  }

  public void config(String paramString, Object paramObject)
  {
    if (paramString.equalsIgnoreCase("day"))
      this.mainView.update(paramString, paramObject);
    boolean bool;
    do
    {
      do
        return;
      while (!paramString.equalsIgnoreCase("isRepeat"));
      this.mainView.update(paramString, paramObject);
      bool = ((Boolean)paramObject).booleanValue();
    }
    while (bool == this.repeat);
    this.repeat = bool;
    NavigationBarTopView localNavigationBarTopView = this.barTopView;
    if (this.repeat);
    for (String str = "重复"; ; str = "不重复")
    {
      localNavigationBarTopView.setTitleItem(new NavigationBarItem(str));
      return;
    }
  }

  public void onItemClick(int paramInt)
  {
    switch (paramInt)
    {
    default:
    case 2:
    case 3:
    }
    Object localObject;
    do
    {
      return;
      ControllerManager.getInstance().popLastController();
      return;
      localObject = this.mainView.getValue("day", null);
    }
    while (localObject == null);
    confirmDay(((Integer)localObject).intValue());
  }

  protected void onViewEvent(Object paramObject1, String paramString, Object paramObject2)
  {
    NavigationBarTopView localNavigationBarTopView;
    if (paramString.equalsIgnoreCase("chooseRepeat"))
    {
      boolean bool = ((Boolean)paramObject2).booleanValue();
      if (bool != this.repeat)
      {
        this.repeat = bool;
        localNavigationBarTopView = this.barTopView;
        if (!this.repeat)
          break label65;
      }
    }
    label65: for (String str = "重复"; ; str = "不重复")
    {
      localNavigationBarTopView.setTitleItem(new NavigationBarItem(str));
      return;
    }
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.qtradio.controller.personalcenter.AlarmDaySettingController
 * JD-Core Version:    0.6.2
 */