package fm.qingting.qtradio.controller;

import android.content.Context;
import fm.qingting.framework.controller.ViewController;
import fm.qingting.framework.model.INavigationBarListener;
import fm.qingting.framework.model.NavigationBarItem;
import fm.qingting.qtradio.model.ActivityInfoNode;
import fm.qingting.qtradio.model.InfoManager;
import fm.qingting.qtradio.model.InfoManager.DataExceptionStatus;
import fm.qingting.qtradio.model.InfoManager.ISubscribeEventListener;
import fm.qingting.qtradio.model.Node;
import fm.qingting.qtradio.view.channelcategoryview.ActivitiesView;
import fm.qingting.qtradio.view.navigation.NavigationBarTopView;

public class ActivitiesController extends ViewController
  implements INavigationBarListener, InfoManager.ISubscribeEventListener
{
  private ActivitiesView activityView;
  private NavigationBarTopView barTempView;
  private Node targetNode = null;

  public ActivitiesController(Context paramContext)
  {
    super(paramContext);
    this.controllerName = "activities";
    this.activityView = new ActivitiesView(paramContext);
    attachView(this.activityView);
  }

  private void openActivity(Node paramNode)
  {
    if (this.targetNode == null)
      return;
    ControllerManager.getInstance().redirectToActivityViewByNode(paramNode);
  }

  private void openParentController()
  {
    if (this.targetNode != null)
      ControllerManager.getInstance().popLastController();
  }

  private void setData()
  {
    if (this.targetNode == null);
    while (!this.targetNode.nodeName.equalsIgnoreCase("activityinfo"))
      return;
    this.activityView.update("setData", ((ActivityInfoNode)this.targetNode).getActivityList());
  }

  private void setNavigationBar(Node paramNode)
  {
    if (paramNode == null);
    do
    {
      return;
      if (this.barTempView == null)
        this.barTempView = new NavigationBarTopView(getContext());
      this.barTempView.setLeftItem(0);
      this.barTempView.setBarListener(this);
      setNavigationBar(this.barTempView);
    }
    while (!this.targetNode.nodeName.equalsIgnoreCase("activityinfo"));
    this.barTempView.setTitleItem(new NavigationBarItem(((ActivityInfoNode)this.targetNode).name));
  }

  public void config(String paramString, Object paramObject)
  {
    if (paramString.equalsIgnoreCase("setData"))
    {
      this.targetNode = ((Node)paramObject);
      setNavigationBar(this.targetNode);
      if (this.targetNode != null)
        break label33;
    }
    label33: 
    while (!this.targetNode.nodeName.equalsIgnoreCase("activityinfo"))
      return;
    if (((ActivityInfoNode)this.targetNode).getActivityList() != null)
    {
      setData();
      return;
    }
    InfoManager.getInstance().loadActivityListNodes(this.targetNode, this);
  }

  public void controllerDidPopped()
  {
    this.activityView.close(false);
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
    case 3:
    default:
      return;
    case 2:
    }
    openParentController();
  }

  public void onNotification(String paramString)
  {
    if (paramString.equalsIgnoreCase("RACTL"))
      setData();
  }

  public void onRecvDataException(String paramString, InfoManager.DataExceptionStatus paramDataExceptionStatus)
  {
  }

  protected void onViewEvent(Object paramObject1, String paramString, Object paramObject2)
  {
    if (paramString.equalsIgnoreCase("selectActivity"))
      openActivity((Node)paramObject2);
    while (!paramString.equalsIgnoreCase("loadMore"))
      return;
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.qtradio.controller.ActivitiesController
 * JD-Core Version:    0.6.2
 */