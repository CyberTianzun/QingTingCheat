package fm.qingting.qtradio.controller.im;

import android.content.Context;
import fm.qingting.framework.controller.ViewController;
import fm.qingting.framework.model.INavigationBarListener;
import fm.qingting.framework.model.NavigationBarItem;
import fm.qingting.qtradio.controller.ControllerManager;
import fm.qingting.qtradio.im.IMAgent;
import fm.qingting.qtradio.im.info.GroupInfo;
import fm.qingting.qtradio.log.LogModule;
import fm.qingting.qtradio.logger.QTLogger;
import fm.qingting.qtradio.model.InfoManager;
import fm.qingting.qtradio.model.RootNode;
import fm.qingting.qtradio.model.RootNode.IInfoUpdateEventListener;
import fm.qingting.qtradio.view.im.MyGroupsView;
import fm.qingting.qtradio.view.navigation.NavigationBarTopView;
import fm.qingting.utils.QTMSGManage;
import java.util.List;

public class ImMyGroupsController extends ViewController
  implements INavigationBarListener, RootNode.IInfoUpdateEventListener
{
  private NavigationBarTopView mBarTopView;
  private List<GroupInfo> mLstGroupInfo;
  private MyGroupsView mainView;

  public ImMyGroupsController(Context paramContext)
  {
    super(paramContext);
    this.controllerName = "mygroups";
    this.mainView = new MyGroupsView(paramContext);
    attachView(this.mainView);
    this.mBarTopView = new NavigationBarTopView(paramContext);
    this.mBarTopView.setTitleItem(new NavigationBarItem("群组"));
    this.mBarTopView.setLeftItem(0);
    setNavigationBar(this.mBarTopView);
    this.mBarTopView.setBarListener(this);
    InfoManager.getInstance().root().registerInfoUpdateListener(this, 6);
  }

  public void config(String paramString, Object paramObject)
  {
    if (paramString.equalsIgnoreCase("setData"))
    {
      this.mainView.update(paramString, paramObject);
      List localList = (List)paramObject;
      if (localList != null)
      {
        this.mLstGroupInfo = localList;
        this.mBarTopView.setTitleItem(new NavigationBarItem("群组(" + this.mLstGroupInfo.size() + ")"));
        int i = 0;
        if (i < this.mLstGroupInfo.size())
        {
          if (this.mLstGroupInfo.get(i) == null);
          while (true)
          {
            i++;
            break;
            GroupInfo localGroupInfo = IMAgent.getInstance().getGroupInfo(((GroupInfo)this.mLstGroupInfo.get(i)).groupId);
            if (localGroupInfo == null)
              IMAgent.getInstance().loadGroupInfo(((GroupInfo)this.mLstGroupInfo.get(i)).groupId, this);
            else
              ((GroupInfo)this.mLstGroupInfo.get(i)).update(localGroupInfo);
          }
        }
      }
      QTMSGManage.getInstance().sendStatistcsMessage("imgrouplist");
      String str = QTLogger.getInstance().buildEnterIMLog(3);
      if (str != null)
        LogModule.getInstance().send("IMUI", str);
    }
  }

  public void controllerDidPopped()
  {
    InfoManager.getInstance().root().unRegisterInfoUpdateListener(6, this);
    this.mainView.close(false);
    super.controllerDidPopped();
  }

  public void onInfoUpdated(int paramInt)
  {
    if ((paramInt == 6) && (this.mLstGroupInfo != null))
    {
      for (int i = 0; i < this.mLstGroupInfo.size(); i++)
      {
        GroupInfo localGroupInfo = IMAgent.getInstance().getGroupInfo(((GroupInfo)this.mLstGroupInfo.get(i)).groupId);
        if ((localGroupInfo != null) && (((GroupInfo)this.mLstGroupInfo.get(i)).lstAdmins == null))
          ((GroupInfo)this.mLstGroupInfo.get(i)).update(localGroupInfo);
      }
      this.mainView.update("setData", this.mLstGroupInfo);
    }
  }

  public void onItemClick(int paramInt)
  {
    if (paramInt == 2)
      ControllerManager.getInstance().popLastController();
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.qtradio.controller.im.ImMyGroupsController
 * JD-Core Version:    0.6.2
 */