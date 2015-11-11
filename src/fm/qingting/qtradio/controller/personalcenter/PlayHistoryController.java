package fm.qingting.qtradio.controller.personalcenter;

import android.content.Context;
import fm.qingting.framework.controller.ViewController;
import fm.qingting.framework.model.INavigationBarListener;
import fm.qingting.framework.model.NavigationBarItem;
import fm.qingting.qtradio.controller.ControllerManager;
import fm.qingting.qtradio.model.InfoManager;
import fm.qingting.qtradio.model.PersonalCenterNode;
import fm.qingting.qtradio.model.PlayHistoryInfoNode;
import fm.qingting.qtradio.model.RootNode;
import fm.qingting.qtradio.view.navigation.NavigationBarTopView;
import fm.qingting.qtradio.view.personalcenter.playhistory.PlayHistoryView;
import java.util.List;

public class PlayHistoryController extends ViewController
  implements INavigationBarListener
{
  private NavigationBarTopView barTempView;
  private PlayHistoryView channelsView;
  private boolean inManageMode = false;

  public PlayHistoryController(Context paramContext)
  {
    super(paramContext);
    this.controllerName = "playhistory";
    this.channelsView = new PlayHistoryView(paramContext);
    attachView(this.channelsView);
    this.barTempView = new NavigationBarTopView(paramContext);
    this.barTempView.setLeftItem(0);
    this.barTempView.setRightItem("编辑");
    this.barTempView.setBarListener(this);
    setNavigationBar(this.barTempView);
  }

  private void setData()
  {
    List localList = InfoManager.getInstance().root().mPersonalCenterNode.playHistoryNode.getPlayHistoryNodes();
    int i;
    NavigationBarTopView localNavigationBarTopView;
    int j;
    if (localList.size() > 0)
    {
      i = 1;
      localNavigationBarTopView = this.barTempView;
      j = 0;
      if (i == 0)
        break label61;
    }
    while (true)
    {
      localNavigationBarTopView.setRightItemVisibility(j);
      this.channelsView.update("setData", localList);
      return;
      i = 0;
      break;
      label61: j = 4;
    }
  }

  public void config(String paramString, Object paramObject)
  {
    if (paramString.equalsIgnoreCase("setData"))
    {
      this.barTempView.setTitleItem(new NavigationBarItem(InfoManager.getInstance().root().mPersonalCenterNode.playHistoryNode.mTitle));
      setData();
    }
  }

  public void controllerDidPopped()
  {
    this.channelsView.close(false);
    super.controllerDidPopped();
  }

  public void controllerReappeared()
  {
    setData();
    super.controllerReappeared();
  }

  public void onItemClick(int paramInt)
  {
    switch (paramInt)
    {
    default:
      return;
    case 2:
      ControllerManager.getInstance().popLastController();
      return;
    case 3:
    }
    PlayHistoryView localPlayHistoryView = this.channelsView;
    String str1;
    String str2;
    if (this.inManageMode)
    {
      str1 = "hideManage";
      localPlayHistoryView.update(str1, null);
      NavigationBarTopView localNavigationBarTopView = this.barTempView;
      if (!this.inManageMode)
        break label100;
      str2 = "编辑";
      label70: localNavigationBarTopView.setRightItem(str2);
      if (this.inManageMode)
        break label107;
    }
    label100: label107: for (boolean bool = true; ; bool = false)
    {
      this.inManageMode = bool;
      return;
      str1 = "showManage";
      break;
      str2 = "取消";
      break label70;
    }
  }

  protected void onViewEvent(Object paramObject1, String paramString, Object paramObject2)
  {
    if (paramString.equalsIgnoreCase("emptynow"))
    {
      this.channelsView.update("hideManage", null);
      this.barTempView.setRightItem("编辑");
      this.barTempView.setRightItemVisibility(4);
      this.inManageMode = false;
    }
    while (!paramString.equalsIgnoreCase("hideManage"))
      return;
    this.channelsView.update("hideManage", null);
    this.barTempView.setRightItem("编辑");
    this.inManageMode = false;
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.qtradio.controller.personalcenter.PlayHistoryController
 * JD-Core Version:    0.6.2
 */