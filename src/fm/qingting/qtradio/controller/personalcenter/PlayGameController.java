package fm.qingting.qtradio.controller.personalcenter;

import android.content.Context;
import fm.qingting.framework.controller.ViewController;
import fm.qingting.framework.model.INavigationBarListener;
import fm.qingting.framework.model.NavigationBarItem;
import fm.qingting.qtradio.controller.ControllerManager;
import fm.qingting.qtradio.model.InfoManager;
import fm.qingting.qtradio.view.navigation.NavigationBarTopView;
import fm.qingting.qtradio.view.personalcenter.playgame.PlayGameView;
import java.util.List;

public class PlayGameController extends ViewController
  implements INavigationBarListener
{
  private NavigationBarTopView barTempView;
  private PlayGameView gamesView;

  public PlayGameController(Context paramContext)
  {
    super(paramContext);
    this.controllerName = "playgame";
    this.gamesView = new PlayGameView(paramContext);
    attachView(this.gamesView);
    this.barTempView = new NavigationBarTopView(paramContext);
    this.barTempView.setLeftItem(0);
    this.barTempView.setBarListener(this);
    setNavigationBar(this.barTempView);
  }

  private void setData()
  {
    List localList = InfoManager.getInstance().getLstGameBean();
    int i;
    NavigationBarTopView localNavigationBarTopView;
    int j;
    if (localList.size() > 0)
    {
      i = 1;
      localNavigationBarTopView = this.barTempView;
      j = 0;
      if (i == 0)
        break label52;
    }
    while (true)
    {
      localNavigationBarTopView.setRightItemVisibility(j);
      this.gamesView.update("setData", localList);
      return;
      i = 0;
      break;
      label52: j = 4;
    }
  }

  public void config(String paramString, Object paramObject)
  {
    if (paramString.equalsIgnoreCase("setData"))
    {
      this.barTempView.setTitleItem(new NavigationBarItem("蜻蜓游乐场"));
      setData();
    }
  }

  public void controllerDidPopped()
  {
    this.gamesView.close(false);
    super.controllerDidPopped();
  }

  public void onItemClick(int paramInt)
  {
    switch (paramInt)
    {
    case 3:
    default:
      return;
    case 2:
    }
    ControllerManager.getInstance().popLastController();
  }

  protected void onViewEvent(Object paramObject1, String paramString, Object paramObject2)
  {
    if (paramString.equalsIgnoreCase("emptynow"));
    while (!paramString.equalsIgnoreCase("hideManage"))
      return;
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.qtradio.controller.personalcenter.PlayGameController
 * JD-Core Version:    0.6.2
 */