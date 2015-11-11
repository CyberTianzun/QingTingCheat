package fm.qingting.qtradio.controller;

import android.content.Context;
import fm.qingting.framework.controller.ViewController;
import fm.qingting.framework.model.INavigationBarListener;
import fm.qingting.framework.model.NavigationBarItem;
import fm.qingting.qtradio.model.Attribute;
import fm.qingting.qtradio.model.CategoryNode;
import fm.qingting.qtradio.model.ContentCategoryNode;
import fm.qingting.qtradio.model.InfoManager;
import fm.qingting.qtradio.model.LiveNode;
import fm.qingting.qtradio.model.RootNode;
import fm.qingting.qtradio.view.channelcategoryview.CategoryListView;
import fm.qingting.qtradio.view.navigation.NavigationBarTopView;
import java.util.List;

public class CategoryListController extends ViewController
  implements INavigationBarListener
{
  private NavigationBarTopView barTempView;
  private CategoryNode mNode;
  private CategoryListView mainView;

  public CategoryListController(Context paramContext)
  {
    super(paramContext);
    this.controllerName = "categorylist";
    this.barTempView = new NavigationBarTopView(paramContext);
    this.barTempView.setLeftItem(0);
    this.barTempView.setBarListener(this);
    setNavigationBar(this.barTempView);
    this.mainView = new CategoryListView(paramContext);
    attachView(this.mainView);
  }

  private void setAttrs(List<Attribute> paramList)
  {
    this.mainView.update("setData", paramList);
  }

  private void setNavigationBar()
  {
    if (this.mNode == null)
      return;
    this.barTempView.setLeftItem(0);
    this.barTempView.setTitleItem(new NavigationBarItem(this.mNode.name));
  }

  public void config(String paramString, Object paramObject)
  {
    if ((!paramString.equalsIgnoreCase("setData")) || (paramObject == null));
    List localList;
    do
    {
      return;
      this.mNode = ((CategoryNode)paramObject);
      setNavigationBar();
      localList = InfoManager.getInstance().root().mContentCategory.mLiveNode.getRegionAttribute();
    }
    while (localList == null);
    setAttrs(localList);
  }

  public void controllerDidPopped()
  {
    this.mainView.close(false);
    this.barTempView.close(false);
    super.controllerDidPopped();
  }

  public boolean hasMiniPlayer()
  {
    return true;
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
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.qtradio.controller.CategoryListController
 * JD-Core Version:    0.6.2
 */