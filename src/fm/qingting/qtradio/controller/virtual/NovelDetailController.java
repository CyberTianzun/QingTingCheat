package fm.qingting.qtradio.controller.virtual;

import android.content.Context;
import fm.qingting.framework.controller.ViewController;
import fm.qingting.framework.event.IEventHandler;
import fm.qingting.framework.manager.EventDispacthManager;
import fm.qingting.framework.model.INavigationBarListener;
import fm.qingting.framework.model.NavigationBarItem;
import fm.qingting.framework.view.IView;
import fm.qingting.qtradio.controller.ControllerManager;
import fm.qingting.qtradio.model.ChannelNode;
import fm.qingting.qtradio.model.InfoManager;
import fm.qingting.qtradio.model.InfoManager.DataExceptionStatus;
import fm.qingting.qtradio.model.InfoManager.ISubscribeEventListener;
import fm.qingting.qtradio.model.Node;
import fm.qingting.qtradio.view.navigation.NavigationBarTopView;
import fm.qingting.qtradio.view.virtualchannels.ChannelDetailViewNew;
import fm.qingting.utils.QTMSGManage;
import java.util.List;

public class NovelDetailController extends ViewController
  implements INavigationBarListener, IEventHandler, InfoManager.ISubscribeEventListener
{
  private NavigationBarTopView barTopView;
  private ChannelNode mNode;
  private IView mainView;

  public NovelDetailController(Context paramContext)
  {
    super(paramContext);
    this.controllerName = "noveldetail";
    this.mainView = new ChannelDetailViewNew(paramContext);
    attachView(this.mainView);
    this.barTopView = new NavigationBarTopView(paramContext);
    this.barTopView.setLeftItem(0);
    this.barTopView.setBarListener(this);
    setNavigationBar(this.barTopView);
  }

  public void config(String paramString, Object paramObject)
  {
    int i;
    float f;
    if (paramString.equalsIgnoreCase("setData"))
    {
      this.mNode = ((ChannelNode)paramObject);
      this.barTopView.setTitleItem(new NavigationBarItem(this.mNode.title));
      i = this.mNode.audienceCnt;
      if (i > 0)
      {
        if (i <= 10000)
          break label144;
        f = 1000 * (i / 1000) / 10000.0F;
      }
    }
    label144: for (String str = "听众:" + String.valueOf(f) + "万"; ; str = "听众:" + String.valueOf(i))
    {
      this.barTopView.setRightItem(str);
      this.mainView.update(paramString, paramObject);
      if (!this.mNode.hasEmptyProgramSchedule())
        break;
      InfoManager.getInstance().loadProgramsScheduleNode(this.mNode, this);
      return;
    }
    List localList = this.mNode.getAllLstProgramNode();
    if ((localList != null) && (localList.size() > 0))
    {
      this.mainView.update("setProgramList", localList);
      return;
    }
    InfoManager.getInstance().loadProgramsScheduleNode(this.mNode, this);
  }

  public void controllerDidPopped()
  {
    this.mainView.close(false);
    super.controllerDidPopped();
  }

  public void controllerDidPushed()
  {
    this.mainView.update("expand", null);
    super.controllerDidPushed();
  }

  public boolean hasMiniPlayer()
  {
    return true;
  }

  public void onEvent(Object paramObject1, String paramString, Object paramObject2)
  {
    if (paramString.equalsIgnoreCase("click"))
    {
      EventDispacthManager.getInstance().dispatchAction("showFastChoose", null);
      QTMSGManage.getInstance().sendStatistcsMessage("clickchanneltitle");
    }
  }

  public void onItemClick(int paramInt)
  {
    switch (paramInt)
    {
    default:
      return;
    case 2:
    }
    ControllerManager.getInstance().popLastController();
  }

  public void onNotification(String paramString)
  {
    if ((!paramString.equalsIgnoreCase("RPS")) || (this.mNode == null) || (this.mNode.hasEmptyProgramSchedule()));
    List localList;
    do
    {
      return;
      localList = this.mNode.getAllLstProgramNode();
    }
    while ((localList == null) || (localList.size() <= 0));
    this.mainView.update("setProgramList", localList);
  }

  public void onRecvDataException(String paramString, InfoManager.DataExceptionStatus paramDataExceptionStatus)
  {
  }

  protected void onViewEvent(Object paramObject1, String paramString, Object paramObject2)
  {
    if (paramString.equalsIgnoreCase("itemSelect"))
    {
      i = ((Integer)paramObject2).intValue();
      if ((this.mNode != null) && (!this.mNode.hasEmptyProgramSchedule()))
      {
        localList = this.mNode.getAllLstProgramNode();
        if ((localList != null) && (i > -1) && (i < localList.size()))
          ControllerManager.getInstance().redirectToPlayViewByNode((Node)localList.get(i), true);
      }
    }
    while ((!paramString.equalsIgnoreCase("download")) || (this.mNode == null) || (!this.mNode.nodeName.equalsIgnoreCase("channel")))
    {
      int i;
      List localList;
      return;
    }
    ControllerManager.getInstance().redirectToBatchDownloadView(this.mNode, false, true);
    QTMSGManage.getInstance().sendStatistcsMessage("openbatchdownload", "novel");
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.qtradio.controller.virtual.NovelDetailController
 * JD-Core Version:    0.6.2
 */