package fm.qingting.qtradio.jd.controller;

import android.content.Context;
import fm.qingting.framework.controller.ViewController;
import fm.qingting.framework.model.INavigationBarListener;
import fm.qingting.framework.model.NavigationBarItem;
import fm.qingting.qtradio.controller.ControllerManager;
import fm.qingting.qtradio.jd.data.CommodityInfo;
import fm.qingting.qtradio.jd.view.JDShopView;
import fm.qingting.qtradio.view.navigation.NavigationBarTopView;

public class JDShopController extends ViewController
  implements INavigationBarListener
{
  NavigationBarTopView mTopView;
  private JDShopView mView;

  public JDShopController(Context paramContext)
  {
    super(paramContext);
    this.mTopView = new NavigationBarTopView(paramContext);
    this.mTopView.setLeftItem(0);
    this.mTopView.setBarListener(this);
    setNavigationBar(this.mTopView);
    this.mView = new JDShopView(paramContext);
    attachView(this.mView);
  }

  public void config(String paramString, Object paramObject)
  {
    if (paramString.equalsIgnoreCase("setData"))
    {
      CommodityInfo localCommodityInfo = (CommodityInfo)paramObject;
      this.mTopView.setTitleItem(new NavigationBarItem(localCommodityInfo.getTitle()));
      this.mView.update("setData", localCommodityInfo.getShopUrl());
    }
  }

  public void onItemClick(int paramInt)
  {
    if (paramInt == 2)
      ControllerManager.getInstance().popLastController();
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.qtradio.jd.controller.JDShopController
 * JD-Core Version:    0.6.2
 */