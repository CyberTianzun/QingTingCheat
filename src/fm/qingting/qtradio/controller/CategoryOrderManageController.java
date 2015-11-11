package fm.qingting.qtradio.controller;

import android.content.Context;
import fm.qingting.framework.controller.ViewController;
import fm.qingting.framework.model.NavigationBarItem;
import fm.qingting.qtradio.model.ContentCategoryNode;
import fm.qingting.qtradio.model.InfoManager;
import fm.qingting.qtradio.model.RootNode;
import fm.qingting.qtradio.model.VirtualNode;
import fm.qingting.qtradio.view.categoryorder.CategoryOrderManageView;
import fm.qingting.qtradio.view.navigation.NavigationBarTopView;
import fm.qingting.qtradio.view.popviews.CategoryResortPopView.CategoryResortInfo;
import java.util.List;

public class CategoryOrderManageController extends ViewController
{
  private CategoryOrderManageView mView;

  public CategoryOrderManageController(Context paramContext)
  {
    super(paramContext);
    NavigationBarTopView localNavigationBarTopView = new NavigationBarTopView(paramContext);
    localNavigationBarTopView.setLeftItem(0);
    localNavigationBarTopView.setRightItem("管理");
    localNavigationBarTopView.setTitleItem(new NavigationBarItem("全部分类"));
    setNavigationBar(localNavigationBarTopView);
    this.mView = new CategoryOrderManageView(paramContext, InfoManager.getInstance().root().mContentCategory.mVirtualNode.getLstCategoryNodes().subList(0, 10));
    attachView(this.mView);
  }

  public void config(String paramString, Object paramObject)
  {
    if (paramString.equalsIgnoreCase("setData"))
    {
      CategoryResortPopView.CategoryResortInfo localCategoryResortInfo = new CategoryResortPopView.CategoryResortInfo(1, InfoManager.getInstance().root().mContentCategory.mVirtualNode.getLstCategoryNodes().size());
      this.mView.update("setData", localCategoryResortInfo);
    }
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.qtradio.controller.CategoryOrderManageController
 * JD-Core Version:    0.6.2
 */