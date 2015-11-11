package fm.qingting.qtradio.controller.im;

import android.content.Context;
import fm.qingting.framework.controller.ViewController;
import fm.qingting.framework.model.INavigationBarListener;
import fm.qingting.framework.model.NavigationBarItem;
import fm.qingting.qtradio.controller.ControllerManager;
import fm.qingting.qtradio.im.IMAgent;
import fm.qingting.qtradio.im.IMContacts;
import fm.qingting.qtradio.im.info.GroupInfo;
import fm.qingting.qtradio.log.LogModule;
import fm.qingting.qtradio.logger.QTLogger;
import fm.qingting.qtradio.model.InfoManager;
import fm.qingting.qtradio.model.RootNode;
import fm.qingting.qtradio.model.RootNode.IInfoUpdateEventListener;
import fm.qingting.qtradio.social.UserProfile;
import fm.qingting.qtradio.view.im.profile.GroupProfileView;
import fm.qingting.qtradio.view.navigation.NavigationBarTopView;
import fm.qingting.utils.QTMSGManage;
import java.util.List;

public class ImGroupProfileController extends ViewController
  implements INavigationBarListener, RootNode.IInfoUpdateEventListener
{
  private NavigationBarTopView mBarTopView;
  private String mGroupId;
  private GroupInfo mInfo;
  private GroupProfileView mainView;

  public ImGroupProfileController(Context paramContext)
  {
    super(paramContext);
    this.controllerName = "groupprofile";
    this.mainView = new GroupProfileView(paramContext);
    attachView(this.mainView);
    this.mBarTopView = new NavigationBarTopView(paramContext);
    this.mBarTopView.setTitleItem(new NavigationBarItem("群资料"));
    this.mBarTopView.setLeftItem(0);
    this.mBarTopView.setRightItem("设置");
    setNavigationBar(this.mBarTopView);
    this.mBarTopView.setBarListener(this);
    InfoManager.getInstance().root().registerInfoUpdateListener(this, 6);
    InfoManager.getInstance().root().registerInfoUpdateListener(this, 7);
  }

  private void setGroupInfo(GroupInfo paramGroupInfo)
  {
    if (paramGroupInfo == null)
      return;
    this.mainView.update("setData", paramGroupInfo);
    boolean bool = IMContacts.getInstance().hasWatchedGroup(paramGroupInfo.groupId);
    NavigationBarTopView localNavigationBarTopView = this.mBarTopView;
    if (bool);
    for (int i = 0; ; i = 4)
    {
      localNavigationBarTopView.setRightItemVisibility(i);
      return;
    }
  }

  public void config(String paramString, Object paramObject)
  {
    if (paramString.equalsIgnoreCase("setData"))
    {
      this.mGroupId = ((String)paramObject);
      this.mInfo = IMAgent.getInstance().getGroupInfo(this.mGroupId);
      if (this.mInfo == null)
      {
        IMAgent.getInstance().loadGroupInfo(this.mGroupId, this);
        localList = IMAgent.getInstance().getGroupMembers(this.mGroupId);
        if ((localList != null) && (localList.size() > 0))
          this.mainView.update("setUsers", localList);
        IMAgent.getInstance().loadGroupMembers(this.mGroupId, 1, 200);
        QTMSGManage.getInstance().sendStatistcsMessage("imgroupprofile");
        str = QTLogger.getInstance().buildEnterIMLog(5);
        if (str != null)
          LogModule.getInstance().send("IMUI", str);
      }
    }
    while (!paramString.equalsIgnoreCase("resetData"))
      while (true)
      {
        List localList;
        String str;
        return;
        setGroupInfo(this.mInfo);
      }
    setGroupInfo(this.mInfo);
  }

  public void controllerDidPopped()
  {
    InfoManager.getInstance().root().unRegisterInfoUpdateListener(6, this);
    InfoManager.getInstance().root().unRegisterInfoUpdateListener(7, this);
    this.mainView.close(false);
    super.controllerDidPopped();
  }

  public void onInfoUpdated(int paramInt)
  {
    if (paramInt == 6)
    {
      this.mInfo = IMAgent.getInstance().getGroupInfo(this.mGroupId);
      setGroupInfo(this.mInfo);
    }
    while (paramInt != 7)
      return;
    this.mainView.update("setUsers", IMAgent.getInstance().getGroupMembers(this.mGroupId));
  }

  public void onItemClick(int paramInt)
  {
    if (paramInt == 2)
      ControllerManager.getInstance().popLastController();
    while ((paramInt != 3) || (this.mInfo == null))
      return;
    ControllerManager.getInstance().openImGroupSettingController(this.mInfo.groupId);
  }

  protected void onViewEvent(Object paramObject1, String paramString, Object paramObject2)
  {
    if (paramString.equalsIgnoreCase("useraction"))
    {
      if (!IMContacts.getInstance().hasWatchedGroup(this.mGroupId))
        break label96;
      ViewController localViewController = ControllerManager.getInstance().getControllerUnderneath();
      if ((localViewController != null) && (localViewController.controllerName.equalsIgnoreCase("imchat")))
      {
        String str = (String)localViewController.getValue("getTalkingId", null);
        if ((str != null) && (str.equalsIgnoreCase(this.mGroupId)))
          ControllerManager.getInstance().popLastController();
      }
    }
    else
    {
      return;
    }
    ControllerManager.getInstance().openImChatController(this.mInfo);
    return;
    label96: InfoManager.getInstance().getUserProfile().followGroup(this.mGroupId);
    this.mainView.update("setJoined", null);
    this.mBarTopView.setRightItemVisibility(0);
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.qtradio.controller.im.ImGroupProfileController
 * JD-Core Version:    0.6.2
 */