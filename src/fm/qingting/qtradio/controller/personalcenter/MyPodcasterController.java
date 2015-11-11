package fm.qingting.qtradio.controller.personalcenter;

import android.content.Context;
import android.text.TextUtils;
import fm.qingting.framework.controller.ViewController;
import fm.qingting.framework.model.INavigationBarListener;
import fm.qingting.framework.model.NavigationBarItem;
import fm.qingting.qtradio.controller.ControllerManager;
import fm.qingting.qtradio.helper.PodcasterHelper;
import fm.qingting.qtradio.model.InfoManager;
import fm.qingting.qtradio.room.SnsInfo;
import fm.qingting.qtradio.room.UserInfo;
import fm.qingting.qtradio.social.CloudCenter;
import fm.qingting.qtradio.social.UserProfile;
import fm.qingting.qtradio.view.navigation.NavigationBarTopView;
import fm.qingting.qtradio.view.personalcenter.mypodcaster.MyPodcasterView;
import java.util.List;

public class MyPodcasterController extends ViewController
  implements INavigationBarListener
{
  public static final String NAME = "mypodcaster";
  private NavigationBarTopView barTopView;
  private boolean inManageMode = false;
  private MyPodcasterView mainView;

  public MyPodcasterController(Context paramContext)
  {
    super(paramContext);
    this.controllerName = "mypodcaster";
    this.mainView = new MyPodcasterView(paramContext);
    attachView(this.mainView);
    this.barTopView = new NavigationBarTopView(paramContext);
    this.barTopView.setLeftItem(0);
    this.barTopView.setTitleItem(new NavigationBarItem("我的主播"));
    this.barTopView.setRightItem("编辑");
    this.barTopView.setBarListener(this);
    setNavigationBar(this.barTopView);
  }

  public void config(String paramString, Object paramObject)
  {
    boolean bool1 = CloudCenter.getInstance().isLogin();
    List localList = null;
    if (bool1)
    {
      UserProfile localUserProfile = InfoManager.getInstance().getUserProfile();
      UserInfo localUserInfo = localUserProfile.getUserInfo();
      localList = null;
      if (localUserInfo != null)
      {
        boolean bool2 = TextUtils.isEmpty(localUserProfile.getUserInfo().snsInfo.sns_id);
        localList = null;
        if (!bool2)
          localList = PodcasterHelper.getInstance().getAllMyPodcaster(localUserProfile.getUserInfo().snsInfo.sns_id);
      }
    }
    if ((localList == null) || (localList.size() == 0))
      this.barTopView.setRightItemVisibility(4);
    while (true)
    {
      this.mainView.update("setData", localList);
      return;
      this.barTopView.setRightItemVisibility(0);
    }
  }

  public void controllerDidPopped()
  {
    this.mainView.close(false);
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
    default:
      return;
    case 2:
      ControllerManager.getInstance().popLastController();
      return;
    case 3:
    }
    MyPodcasterView localMyPodcasterView = this.mainView;
    String str1;
    String str2;
    if (this.inManageMode)
    {
      str1 = "hideManage";
      localMyPodcasterView.update(str1, null);
      NavigationBarTopView localNavigationBarTopView = this.barTopView;
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
      this.mainView.update("hideManage", null);
      this.barTopView.setRightItem("编辑");
      this.barTopView.setRightItemVisibility(4);
    }
    while (!paramString.equalsIgnoreCase("notEmpty"))
      return;
    this.barTopView.setRightItemVisibility(0);
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.qtradio.controller.personalcenter.MyPodcasterController
 * JD-Core Version:    0.6.2
 */