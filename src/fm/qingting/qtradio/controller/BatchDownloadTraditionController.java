package fm.qingting.qtradio.controller;

import android.content.Context;
import fm.qingting.framework.controller.ViewController;
import fm.qingting.framework.model.INavigationBarListener;
import fm.qingting.framework.model.NavigationBarItem;
import fm.qingting.qtradio.model.DownLoadInfoNode;
import fm.qingting.qtradio.model.DownLoadInfoNode.IDownloadInfoEventListener;
import fm.qingting.qtradio.model.InfoManager;
import fm.qingting.qtradio.model.Node;
import fm.qingting.qtradio.model.RootNode;
import fm.qingting.qtradio.view.navigation.NavigationBarTopView;
import fm.qingting.qtradio.view.scheduleview.BatchDownloadTraditionView;

public class BatchDownloadTraditionController extends ViewController
  implements INavigationBarListener, DownLoadInfoNode.IDownloadInfoEventListener
{
  private NavigationBarTopView barTopView;
  private BatchDownloadTraditionView mainView;

  public BatchDownloadTraditionController(Context paramContext)
  {
    super(paramContext);
    this.controllerName = "batchdownload_tradition";
    this.mainView = new BatchDownloadTraditionView(paramContext);
    attachView(this.mainView);
    this.barTopView = new NavigationBarTopView(paramContext);
    this.barTopView.setLeftItem(0);
    this.barTopView.setTitleItem(new NavigationBarItem("批量下载"));
    this.barTopView.setBarListener(this);
    setNavigationBar(this.barTopView);
    InfoManager.getInstance().root().mDownLoadInfoNode.registerListener(this);
  }

  private void setTip()
  {
    int i = InfoManager.getInstance().root().mDownLoadInfoNode.getAllDownloadCount();
    if (i > 0)
    {
      this.barTopView.setRightTip(String.valueOf(i));
      return;
    }
    this.barTopView.setRightTip(null);
  }

  public void config(String paramString, Object paramObject)
  {
    Node localNode;
    if (paramString.equalsIgnoreCase("setData"))
    {
      localNode = (Node)paramObject;
      if (localNode != null)
        break label19;
    }
    label19: 
    while (!localNode.nodeName.equalsIgnoreCase("channel"))
      return;
    this.mainView.update(paramString, localNode);
  }

  public void controllerDidPopped()
  {
    this.mainView.close(false);
    InfoManager.getInstance().root().mDownLoadInfoNode.unregisterListener(this);
    super.controllerDidPopped();
  }

  public void onDownLoadInfoUpdated(int paramInt, Node paramNode)
  {
    if ((paramInt == 1) || (paramInt == 2) || (paramInt == 4) || (paramInt == 8))
      this.mainView.update("refreshList", null);
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
    ControllerManager.getInstance().popLastController();
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.qtradio.controller.BatchDownloadTraditionController
 * JD-Core Version:    0.6.2
 */