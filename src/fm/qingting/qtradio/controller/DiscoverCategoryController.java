package fm.qingting.qtradio.controller;

import android.content.Context;
import fm.qingting.framework.controller.ViewController;
import fm.qingting.framework.model.INavigationBarListener;
import fm.qingting.framework.model.NavigationBarItem;
import fm.qingting.qtradio.model.CategoryNode;
import fm.qingting.qtradio.view.frontpage.DiscoverCategoryView;
import fm.qingting.qtradio.view.navigation.NavigationBarCustomView;

public class DiscoverCategoryController extends ViewController
  implements INavigationBarListener
{
  private NavigationBarCustomView mBarCustomView;
  private DiscoverCategoryView mainView;

  public DiscoverCategoryController(Context paramContext, CategoryNode paramCategoryNode)
  {
    super(paramContext);
    this.mBarCustomView = new NavigationBarCustomView(paramContext);
    this.mBarCustomView.addLeftItem(0);
    this.mBarCustomView.addRightItem(1);
    this.mBarCustomView.setBarListener(this);
    setNavigationBar(this.mBarCustomView);
    this.mBarCustomView.setTitleItem(new NavigationBarItem(paramCategoryNode.name));
    this.mainView = new DiscoverCategoryView(paramContext, paramCategoryNode);
    attachView(this.mainView);
  }

  public void onItemClick(int paramInt)
  {
    if (paramInt == 2)
      ControllerManager.getInstance().popLastController();
    while (paramInt != 3)
      return;
    ControllerManager.getInstance().redirectToSearchView(false);
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.qtradio.controller.DiscoverCategoryController
 * JD-Core Version:    0.6.2
 */