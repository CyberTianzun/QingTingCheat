package fm.qingting.qtradio.controller;

import android.content.Context;
import fm.qingting.framework.controller.ViewController;
import fm.qingting.framework.model.INavigationBarListener;
import fm.qingting.framework.model.NavigationBarItem;
import fm.qingting.qtradio.model.AdvertisementItemNode;
import fm.qingting.qtradio.view.frontpage.discover.SectionAdView;
import fm.qingting.qtradio.view.navigation.NavigationBarTopView;

public class SectionAdController extends ViewController
  implements INavigationBarListener
{
  private NavigationBarTopView mTopView;
  private SectionAdView mView;

  public SectionAdController(Context paramContext)
  {
    super(paramContext);
    this.mTopView = new NavigationBarTopView(paramContext);
    this.mTopView.setLeftItem(0);
    this.mTopView.setBarListener(this);
    setNavigationBar(this.mTopView);
    this.mView = new SectionAdView(paramContext);
    attachView(this.mView);
  }

  public void config(String paramString, Object paramObject)
  {
    if (paramString.equalsIgnoreCase("setData"))
    {
      AdvertisementItemNode localAdvertisementItemNode = (AdvertisementItemNode)paramObject;
      this.mTopView.setTitleItem(new NavigationBarItem(localAdvertisementItemNode.getTitle()));
      this.mView.update("setData", localAdvertisementItemNode.landing);
    }
  }

  public void onItemClick(int paramInt)
  {
    if (paramInt == 2)
      ControllerManager.getInstance().popLastController();
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.qtradio.controller.SectionAdController
 * JD-Core Version:    0.6.2
 */