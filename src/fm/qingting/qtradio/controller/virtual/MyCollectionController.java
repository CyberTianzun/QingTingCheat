package fm.qingting.qtradio.controller.virtual;

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
import fm.qingting.qtradio.view.personalcenter.mycollection.MyCollectionView;
import java.util.List;

public class MyCollectionController extends ViewController
  implements INavigationBarListener
{
  private NavigationBarTopView barTopView;
  private boolean mManage = false;
  private MyCollectionView mainView;

  public MyCollectionController(Context paramContext)
  {
    super(paramContext);
    this.controllerName = "mycollection";
    this.mainView = new MyCollectionView(paramContext);
    attachView(this.mainView);
    this.barTopView = new NavigationBarTopView(paramContext);
    this.barTopView.setLeftItem(0);
    this.barTopView.setTitleItem(new NavigationBarItem("我的收藏"));
    this.barTopView.setRightItem("编辑");
    this.barTopView.setBarListener(this);
    setNavigationBar(this.barTopView);
  }

  private void setData()
  {
    List localList = InfoManager.getInstance().root().mPersonalCenterNode.myCollectionNode.getFavouriteNodes();
    if ((localList == null) || (localList.size() == 0))
      this.barTopView.setRightItemVisibility(4);
    while (true)
    {
      this.mainView.update("setData", localList);
      return;
      this.barTopView.setRightItemVisibility(0);
    }
  }

  public void config(String paramString, Object paramObject)
  {
    if (paramString.equalsIgnoreCase("setData"))
      setData();
    while (!paramString.equalsIgnoreCase("resetData"))
      return;
    setData();
  }

  public void controllerDidPopped()
  {
    this.mainView.close(false);
    super.controllerDidPopped();
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
    if ((this.mManage) && (InfoManager.getInstance().root().mPersonalCenterNode.myCollectionNode.getFavouriteNodes().size() == 0))
    {
      ControllerManager.getInstance().popLastController();
      return;
    }
    MyCollectionView localMyCollectionView = this.mainView;
    String str1;
    String str2;
    if (this.mManage)
    {
      str1 = "hideManage";
      localMyCollectionView.update(str1, null);
      NavigationBarTopView localNavigationBarTopView = this.barTopView;
      if (!this.mManage)
        break label137;
      str2 = "编辑";
      label107: localNavigationBarTopView.setRightItem(str2);
      if (this.mManage)
        break label144;
    }
    label137: label144: for (boolean bool = true; ; bool = false)
    {
      this.mManage = bool;
      return;
      str1 = "showManage";
      break;
      str2 = "取消";
      break label107;
    }
  }

  protected void onViewEvent(Object paramObject1, String paramString, Object paramObject2)
  {
    if (paramString.equalsIgnoreCase("emptynow"))
    {
      this.mainView.update("hideManage", null);
      this.barTopView.setRightItem("编辑");
      this.barTopView.setRightItemVisibility(4);
      this.mManage = false;
    }
    while (!paramString.equalsIgnoreCase("notEmpty"))
      return;
    this.barTopView.setRightItemVisibility(0);
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.qtradio.controller.virtual.MyCollectionController
 * JD-Core Version:    0.6.2
 */