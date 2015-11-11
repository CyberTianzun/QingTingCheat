package fm.qingting.qtradio.controller.im;

import android.content.Context;
import android.text.TextUtils;
import fm.qingting.framework.controller.ViewController;
import fm.qingting.framework.model.INavigationBarListener;
import fm.qingting.framework.model.NavigationBarItem;
import fm.qingting.qtradio.controller.ControllerManager;
import fm.qingting.qtradio.im.IMContacts;
import fm.qingting.qtradio.im.UserProfileManager;
import fm.qingting.qtradio.log.LogModule;
import fm.qingting.qtradio.logger.QTLogger;
import fm.qingting.qtradio.model.InfoManager;
import fm.qingting.qtradio.model.RootNode;
import fm.qingting.qtradio.model.RootNode.IInfoUpdateEventListener;
import fm.qingting.qtradio.social.UserProfile;
import fm.qingting.qtradio.view.im.profile.UserProfileView;
import fm.qingting.qtradio.view.navigation.NavigationBarTopView;
import fm.qingting.utils.QTMSGManage;

public class ImUserProfileController extends ViewController
  implements INavigationBarListener, RootNode.IInfoUpdateEventListener
{
  private NavigationBarTopView mBarTopView;
  private boolean mIsMe = false;
  private String mUserKey;
  private UserProfileView mainView;

  public ImUserProfileController(Context paramContext)
  {
    super(paramContext);
    this.controllerName = "userprofile";
    this.mainView = new UserProfileView(paramContext);
    attachView(this.mainView);
    this.mBarTopView = new NavigationBarTopView(paramContext);
    this.mBarTopView.setTitleItem(new NavigationBarItem("个人资料"));
    this.mBarTopView.setLeftItem(0);
    this.mBarTopView.setRightItem("设置");
    setNavigationBar(this.mBarTopView);
    this.mBarTopView.setBarListener(this);
    InfoManager.getInstance().root().registerInfoUpdateListener(this, 3);
  }

  private void setData()
  {
    this.mainView.update("setData", UserProfileManager.getInstance().getUserProfile(this.mUserKey));
    boolean bool;
    NavigationBarTopView localNavigationBarTopView;
    int i;
    if (this.mIsMe)
    {
      bool = false;
      localNavigationBarTopView = this.mBarTopView;
      i = 0;
      if (!bool)
        break label59;
    }
    while (true)
    {
      localNavigationBarTopView.setRightItemVisibility(i);
      return;
      bool = IMContacts.getInstance().hasWatchedUser(this.mUserKey);
      break;
      label59: i = 4;
    }
  }

  public void config(String paramString, Object paramObject)
  {
    if (paramString.equalsIgnoreCase("setData"))
    {
      this.mUserKey = ((String)paramObject);
      this.mIsMe = TextUtils.equals(InfoManager.getInstance().getUserProfile().getUserKey(), this.mUserKey);
      localUserProfile = UserProfileManager.getInstance().getUserProfile(this.mUserKey);
      if (this.mIsMe)
      {
        this.mBarTopView.setRightItemVisibility(4);
        if (localUserProfile != null)
          break label122;
        UserProfileManager.getInstance().loadUserInfo(this.mUserKey, this);
        QTMSGManage.getInstance().sendStatistcsMessage("imuserprofile");
        str = QTLogger.getInstance().buildEnterIMLog(4);
        if (str != null)
          LogModule.getInstance().send("IMUI", str);
      }
    }
    label122: 
    while (!paramString.equalsIgnoreCase("resetData"))
      while (true)
      {
        UserProfile localUserProfile;
        String str;
        return;
        this.mBarTopView.setRightItemVisibility(0);
        continue;
        setData();
      }
    setData();
  }

  public void controllerDidPopped()
  {
    InfoManager.getInstance().root().unRegisterInfoUpdateListener(3, this);
    this.mainView.close(false);
    super.controllerDidPopped();
  }

  public void onInfoUpdated(int paramInt)
  {
    if (paramInt == 3)
      setData();
  }

  public void onItemClick(int paramInt)
  {
    if (paramInt == 2)
      ControllerManager.getInstance().popLastController();
    while (paramInt != 3)
      return;
    ControllerManager.getInstance().openImUserSettingController(this.mUserKey);
  }

  protected void onViewEvent(Object paramObject1, String paramString, Object paramObject2)
  {
    if (paramString.equalsIgnoreCase("followuser"))
    {
      UserProfile localUserProfile2 = UserProfileManager.getInstance().getUserProfile(this.mUserKey);
      if (localUserProfile2 != null)
        InfoManager.getInstance().getUserProfile().followUser(localUserProfile2.getUserInfo());
    }
    UserProfile localUserProfile1;
    do
    {
      do
        return;
      while (!paramString.equalsIgnoreCase("sendMessage"));
      localUserProfile1 = UserProfileManager.getInstance().getUserProfile(this.mUserKey);
    }
    while (localUserProfile1 == null);
    ViewController localViewController = ControllerManager.getInstance().getControllerUnderneath();
    if ((localViewController != null) && (localViewController.controllerName.equalsIgnoreCase("imchat")))
    {
      String str = (String)localViewController.getValue("getTalkingId", null);
      if ((str != null) && (str.equalsIgnoreCase(localUserProfile1.getUserKey())))
      {
        ControllerManager.getInstance().popLastController();
        return;
      }
    }
    ControllerManager.getInstance().openImChatController(localUserProfile1.getUserInfo());
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.qtradio.controller.im.ImUserProfileController
 * JD-Core Version:    0.6.2
 */