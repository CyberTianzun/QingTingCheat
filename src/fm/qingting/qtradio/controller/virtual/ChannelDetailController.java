package fm.qingting.qtradio.controller.virtual;

import android.content.Context;
import fm.qingting.framework.controller.ViewController;
import fm.qingting.framework.model.INavigationBarListener;
import fm.qingting.framework.model.NavigationBarItem;
import fm.qingting.framework.view.INavigationSetting.Mode;
import fm.qingting.qtradio.controller.ControllerManager;
import fm.qingting.qtradio.manager.RecorderManager;
import fm.qingting.qtradio.model.ChannelNode;
import fm.qingting.qtradio.model.InfoManager;
import fm.qingting.qtradio.model.Node;
import fm.qingting.qtradio.model.RootNode;
import fm.qingting.qtradio.view.navigation.NavigationBarTopView;
import fm.qingting.qtradio.view.virtualchannels.ChannelDetailView;
import fm.qingting.utils.QTMSGManage;

public class ChannelDetailController extends ViewController
  implements INavigationBarListener
{
  public static final String NAME = "channeldetail";
  private NavigationBarTopView barTopView;
  private ChannelNode mNode;
  private ChannelDetailView mainView;

  public ChannelDetailController(Context paramContext)
  {
    super(paramContext);
    this.controllerName = "channeldetail";
    this.mainView = new ChannelDetailView(paramContext);
    attachView(this.mainView);
    setNavigationBarMode(INavigationSetting.Mode.OVERLAY);
    this.barTopView = new NavigationBarTopView(paramContext);
    this.barTopView.setLeftItem(0);
    this.barTopView.setRecordItem("录音");
    this.barTopView.setRightItemVisibility(4);
    this.barTopView.setBarListener(this);
    setNavigationBar(this.barTopView);
  }

  private void setNavi()
  {
    if (this.mNode == null)
      return;
    this.barTopView.setTitleItem(new NavigationBarItem(this.mNode.title));
  }

  public void config(String paramString, Object paramObject)
  {
    if (paramString.equalsIgnoreCase("setData"))
    {
      this.mNode = ((ChannelNode)paramObject);
      this.mainView.update(paramString, paramObject);
      setNavi();
      if (this.mNode.channelId == 66106)
        QTMSGManage.getInstance().sendStatistcsMessage("opencampusblabla");
      if (this.mNode.recordEnable)
        config("refreshUploadView", null);
    }
    do
    {
      int n;
      do
      {
        return;
        n = this.mNode.audienceCnt;
      }
      while (n <= 0);
      float f;
      if (n > 10000)
        f = 1000 * (n / 1000) / 10000.0F;
      for (String str = "听众:" + String.valueOf(f) + "万"; ; str = "听众:" + String.valueOf(n))
      {
        this.barTopView.setRightItem(str);
        return;
      }
      if (paramString.equalsIgnoreCase("refresh"))
      {
        this.mainView.update(paramString, paramObject);
        return;
      }
      if (paramString.equalsIgnoreCase("refreshUploadView"))
      {
        if ((this.mNode.recordEnable) && (!RecorderManager.getInstance().isUploading()))
          this.barTopView.setRightItemVisibility(0);
        while (true)
        {
          this.mainView.update("refreshUploadView", Integer.valueOf(this.mNode.channelId));
          return;
          this.barTopView.setRightItemVisibility(4);
        }
      }
      if (!paramString.equalsIgnoreCase("syncdata"))
        break;
    }
    while (this.mNode == null);
    Node localNode1 = InfoManager.getInstance().root().getCurrentPlayingNode();
    if (localNode1 != null)
    {
      if (!localNode1.nodeName.equalsIgnoreCase("program"))
        break label419;
      Node localNode2 = localNode1.parent;
      if ((localNode2 != null) && (localNode2.nodeName.equalsIgnoreCase("channel")))
      {
        int k = ((ChannelNode)localNode2).categoryId;
        int m = ((ChannelNode)localNode2).channelType;
        if ((k == this.mNode.categoryId) && (m == this.mNode.channelType) && (((ChannelNode)localNode2).channelId != this.mNode.channelId))
        {
          this.mNode = ((ChannelNode)localNode2);
          setNavi();
          this.mainView.update("setData", this.mNode);
        }
      }
    }
    while (true)
    {
      setNavi();
      return;
      label419: if (localNode1.nodeName.equalsIgnoreCase("channel"))
      {
        int i = ((ChannelNode)localNode1).categoryId;
        int j = ((ChannelNode)localNode1).channelType;
        if ((i == this.mNode.categoryId) && (j == this.mNode.channelType) && (((ChannelNode)localNode1).channelId != this.mNode.channelId))
        {
          this.mNode = ((ChannelNode)localNode1);
          setNavi();
          this.mainView.update("setData", this.mNode);
        }
      }
    }
    this.mainView.update(paramString, paramObject);
  }

  public void controllerDidPopped()
  {
    InfoManager.getInstance().alignTime(this.mNode.channelId);
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
    case 2:
    case 3:
    }
    do
    {
      return;
      ControllerManager.getInstance().popLastController();
      return;
    }
    while ((this.mNode == null) || (!this.mNode.recordEnable));
    ControllerManager.getInstance().openUploadVoiceController(this.mNode);
  }

  protected void onViewEvent(Object paramObject1, String paramString, Object paramObject2)
  {
    if (paramString.equalsIgnoreCase("resetNavi"))
      setNavi();
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.qtradio.controller.virtual.ChannelDetailController
 * JD-Core Version:    0.6.2
 */