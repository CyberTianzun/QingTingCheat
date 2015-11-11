package fm.qingting.qtradio.controller.im;

import android.content.Context;
import fm.qingting.framework.controller.ViewController;
import fm.qingting.framework.model.INavigationBarListener;
import fm.qingting.framework.model.NavigationBarItem;
import fm.qingting.qtradio.controller.ControllerManager;
import fm.qingting.qtradio.model.InfoManager;
import fm.qingting.qtradio.model.RootNode;
import fm.qingting.qtradio.model.RootNode.IInfoUpdateEventListener;
import fm.qingting.qtradio.social.UserProfile;
import fm.qingting.qtradio.view.im.ImContactsSpecificView;
import fm.qingting.qtradio.view.navigation.NavigationBarTopView;
import fm.qingting.utils.QTMSGManage;
import java.util.List;
import java.util.Locale;

public class ImContactSpecificController extends ViewController
  implements INavigationBarListener, RootNode.IInfoUpdateEventListener
{
  private static final String FOLLOWER_MODEL = "粉丝(%d)";
  private static final String FOLLOWING_MODEL = "关注(%d)";
  private NavigationBarTopView mBarTopView;
  private boolean mFollowing = false;
  private ImContactsSpecificView mainView;

  public ImContactSpecificController(Context paramContext)
  {
    super(paramContext);
    this.controllerName = "contactspecific";
    this.mainView = new ImContactsSpecificView(paramContext);
    attachView(this.mainView);
    this.mBarTopView = new NavigationBarTopView(paramContext);
    this.mBarTopView.setTitleItem(new NavigationBarItem("关注"));
    this.mBarTopView.setLeftItem(0);
    setNavigationBar(this.mBarTopView);
    this.mBarTopView.setBarListener(this);
    InfoManager.getInstance().root().registerInfoUpdateListener(this, 5);
    InfoManager.getInstance().root().registerInfoUpdateListener(this, 4);
  }

  public void config(String paramString, Object paramObject)
  {
    List localList2;
    if (paramString.equalsIgnoreCase("setData"))
    {
      this.mFollowing = ((Boolean)paramObject).booleanValue();
      if (!this.mFollowing)
        break label173;
      localList2 = InfoManager.getInstance().getUserProfile().getFollowings();
      if (localList2 != null)
        break label108;
      NavigationBarTopView localNavigationBarTopView4 = this.mBarTopView;
      Locale localLocale4 = Locale.CHINESE;
      Object[] arrayOfObject4 = new Object[1];
      arrayOfObject4[0] = Integer.valueOf(0);
      localNavigationBarTopView4.setTitleItem(new NavigationBarItem(String.format(localLocale4, "关注(%d)", arrayOfObject4)));
      InfoManager.getInstance().getUserProfile().loadFollowings(this);
    }
    while (true)
    {
      QTMSGManage.getInstance().sendStatistcsMessage("imfollowinglist");
      return;
      label108: NavigationBarTopView localNavigationBarTopView3 = this.mBarTopView;
      Locale localLocale3 = Locale.CHINESE;
      Object[] arrayOfObject3 = new Object[1];
      arrayOfObject3[0] = Integer.valueOf(localList2.size());
      localNavigationBarTopView3.setTitleItem(new NavigationBarItem(String.format(localLocale3, "关注(%d)", arrayOfObject3)));
      this.mainView.update(paramString, localList2);
    }
    label173: List localList1 = InfoManager.getInstance().getUserProfile().getFollowers();
    if (localList1 == null)
    {
      NavigationBarTopView localNavigationBarTopView2 = this.mBarTopView;
      Locale localLocale2 = Locale.CHINESE;
      Object[] arrayOfObject2 = new Object[1];
      arrayOfObject2[0] = Integer.valueOf(0);
      localNavigationBarTopView2.setTitleItem(new NavigationBarItem(String.format(localLocale2, "粉丝(%d)", arrayOfObject2)));
      InfoManager.getInstance().getUserProfile().loadFollowers(this);
    }
    while (true)
    {
      QTMSGManage.getInstance().sendStatistcsMessage("imfollowerlist");
      return;
      NavigationBarTopView localNavigationBarTopView1 = this.mBarTopView;
      Locale localLocale1 = Locale.CHINESE;
      Object[] arrayOfObject1 = new Object[1];
      arrayOfObject1[0] = Integer.valueOf(localList1.size());
      localNavigationBarTopView1.setTitleItem(new NavigationBarItem(String.format(localLocale1, "粉丝(%d)", arrayOfObject1)));
      this.mainView.update(paramString, localList1);
    }
  }

  public void controllerDidPopped()
  {
    InfoManager.getInstance().root().unRegisterInfoUpdateListener(5, this);
    InfoManager.getInstance().root().unRegisterInfoUpdateListener(4, this);
    this.mainView.close(false);
    super.controllerDidPopped();
  }

  public void onInfoUpdated(int paramInt)
  {
    if ((paramInt == 5) && (this.mFollowing))
    {
      localList2 = InfoManager.getInstance().getUserProfile().getFollowings();
      if (localList2 == null)
      {
        localNavigationBarTopView4 = this.mBarTopView;
        localLocale4 = Locale.CHINESE;
        arrayOfObject4 = new Object[1];
        arrayOfObject4[0] = Integer.valueOf(0);
        localNavigationBarTopView4.setTitleItem(new NavigationBarItem(String.format(localLocale4, "关注(%d)", arrayOfObject4)));
        this.mainView.update("setData", localList2);
      }
    }
    while ((paramInt != 4) || (this.mFollowing))
      while (true)
      {
        List localList2;
        NavigationBarTopView localNavigationBarTopView4;
        Locale localLocale4;
        Object[] arrayOfObject4;
        return;
        NavigationBarTopView localNavigationBarTopView3 = this.mBarTopView;
        Locale localLocale3 = Locale.CHINESE;
        Object[] arrayOfObject3 = new Object[1];
        arrayOfObject3[0] = Integer.valueOf(localList2.size());
        localNavigationBarTopView3.setTitleItem(new NavigationBarItem(String.format(localLocale3, "关注(%d)", arrayOfObject3)));
      }
    List localList1 = InfoManager.getInstance().getUserProfile().getFollowers();
    if (localList1 == null)
    {
      NavigationBarTopView localNavigationBarTopView2 = this.mBarTopView;
      Locale localLocale2 = Locale.CHINESE;
      Object[] arrayOfObject2 = new Object[1];
      arrayOfObject2[0] = Integer.valueOf(0);
      localNavigationBarTopView2.setTitleItem(new NavigationBarItem(String.format(localLocale2, "粉丝(%d)", arrayOfObject2)));
    }
    while (true)
    {
      this.mainView.update("setData", localList1);
      return;
      NavigationBarTopView localNavigationBarTopView1 = this.mBarTopView;
      Locale localLocale1 = Locale.CHINESE;
      Object[] arrayOfObject1 = new Object[1];
      arrayOfObject1[0] = Integer.valueOf(localList1.size());
      localNavigationBarTopView1.setTitleItem(new NavigationBarItem(String.format(localLocale1, "粉丝(%d)", arrayOfObject1)));
    }
  }

  public void onItemClick(int paramInt)
  {
    if (paramInt == 2)
      ControllerManager.getInstance().popLastController();
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.qtradio.controller.im.ImContactSpecificController
 * JD-Core Version:    0.6.2
 */