package fm.qingting.qtradio.controller;

import android.content.Context;
import fm.qingting.framework.controller.ViewController;
import fm.qingting.framework.model.INavigationBarListener;
import fm.qingting.framework.model.NavigationBarItem;
import fm.qingting.framework.view.INavigationSetting.Mode;
import fm.qingting.qtradio.view.navigation.NavigationBarTopView;
import fm.qingting.qtradio.view.podcaster.PodcasterInfoView;

public class PodcasterInfoController extends ViewController
  implements INavigationBarListener
{
  public static final String NAME = "podcasterinfo";
  private PodcasterInfoView mInfoView;
  private NavigationBarTopView mTopView;

  public PodcasterInfoController(Context paramContext)
  {
    super(paramContext);
    this.controllerName = "podcasterinfo";
    this.mTopView = new NavigationBarTopView(paramContext);
    this.mTopView.setBackgroundResource(0);
    setNavigationBar(this.mTopView);
    this.mTopView.setLeftItem(0);
    this.mTopView.setBarListener(this);
    setNavigationBarMode(INavigationSetting.Mode.OVERLAY);
    this.mInfoView = new PodcasterInfoView(paramContext);
    attachView(this.mInfoView);
  }

  public void config(String paramString, Object paramObject)
  {
    if (paramString.equalsIgnoreCase("setData"))
      this.mInfoView.update(paramString, paramObject);
  }

  public void controllerDidPopped()
  {
    this.mInfoView.update("setlastestprogramid", null);
  }

  public boolean hasMiniPlayer()
  {
    return true;
  }

  public void onItemClick(int paramInt)
  {
    if (paramInt == 2)
      ControllerManager.getInstance().popLastController();
  }

  protected void onViewEvent(Object paramObject1, String paramString, Object paramObject2)
  {
    if (paramString.equalsIgnoreCase("showTitle"))
    {
      str = (String)paramObject2;
      this.mTopView.setTitleItem(new NavigationBarItem(str));
    }
    while (!paramString.equalsIgnoreCase("hideTitle"))
    {
      String str;
      return;
    }
    this.mTopView.setTitleItem(new NavigationBarItem(null));
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.qtradio.controller.PodcasterInfoController
 * JD-Core Version:    0.6.2
 */