package fm.qingting.qtradio.controller;

import android.content.Context;
import fm.qingting.framework.controller.ViewController;
import fm.qingting.framework.model.INavigationBarListener;
import fm.qingting.framework.model.NavigationBarItem;
import fm.qingting.qtradio.helper.ChannelHelper;
import fm.qingting.qtradio.model.Attribute;
import fm.qingting.qtradio.model.CategoryNode;
import fm.qingting.qtradio.model.ChannelNode;
import fm.qingting.qtradio.model.InfoManager.DataExceptionStatus;
import fm.qingting.qtradio.model.InfoManager.ISubscribeEventListener;
import fm.qingting.qtradio.model.Node;
import fm.qingting.qtradio.model.RadioNode;
import fm.qingting.qtradio.view.channelcategoryview.TraChannelView;
import fm.qingting.qtradio.view.navigation.NavigationBarTopView;
import java.util.List;

public class ChannelsListController extends ViewController
  implements INavigationBarListener, InfoManager.ISubscribeEventListener
{
  private NavigationBarTopView barTempView;
  private TraChannelView channelsView;
  private Node mNode;

  public ChannelsListController(Context paramContext)
  {
    super(paramContext);
    this.controllerName = "channellist";
    this.channelsView = new TraChannelView(paramContext);
    attachView(this.channelsView);
    this.barTempView = new NavigationBarTopView(getContext());
    this.barTempView.setLeftItem(0);
    this.barTempView.setBarListener(this);
    setNavigationBar(this.barTempView);
  }

  private void setData(List<ChannelNode> paramList)
  {
    this.channelsView.update("setData", paramList);
  }

  private void setNavigationBar()
  {
    if (this.mNode == null)
      return;
    String str;
    if (this.mNode.nodeName.equalsIgnoreCase("category"))
    {
      str = ((CategoryNode)this.mNode).name;
      if ((str != null) && (!str.endsWith("台")))
        str = str + "台";
    }
    while (true)
    {
      this.barTempView.setTitleItem(new NavigationBarItem(str));
      return;
      if (this.mNode.nodeName.equalsIgnoreCase("attribute"))
      {
        str = ((Attribute)this.mNode).name;
      }
      else
      {
        boolean bool = this.mNode.nodeName.equalsIgnoreCase("radio");
        str = null;
        if (bool)
          str = ((RadioNode)this.mNode).mTitle;
      }
    }
  }

  public void config(String paramString, Object paramObject)
  {
    if (paramString.equalsIgnoreCase("setData"))
    {
      this.mNode = ((Node)paramObject);
      setNavigationBar();
      if (this.mNode != null)
        break label29;
    }
    label29: List localList1;
    do
    {
      do
      {
        return;
        if (this.mNode.nodeName.equalsIgnoreCase("attribute"))
        {
          List localList3 = ((Attribute)this.mNode).getLstChannels();
          if (localList3 == null)
          {
            ChannelHelper.getInstance().loadListLiveChannelNodes(((Attribute)this.mNode).getCatid(), String.valueOf(((Attribute)this.mNode).id), this);
            return;
          }
          setData(localList3);
          return;
        }
        if (this.mNode.nodeName.equalsIgnoreCase("category"))
        {
          List localList2 = ((CategoryNode)this.mNode).getLstChannels();
          if (localList2 == null)
          {
            ChannelHelper.getInstance().loadListLiveChannelNodes(((CategoryNode)this.mNode).categoryId, ((CategoryNode)this.mNode).mAttributesPath, this);
            return;
          }
          setData(localList2);
          return;
        }
      }
      while (!this.mNode.nodeName.equalsIgnoreCase("radio"));
      localList1 = ((RadioNode)this.mNode).mLstChannelNodes;
    }
    while (localList1 == null);
    this.channelsView.update("setData", localList1);
  }

  public void controllerDidPopped()
  {
    this.channelsView.close(false);
    this.barTempView.close(false);
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
    ControllerManager.getInstance().popLastController();
  }

  public void onNotification(String paramString)
  {
    if (paramString.equalsIgnoreCase("RECV_LIVE_CHANNELS_BYATTR"))
    {
      if (!this.mNode.nodeName.equalsIgnoreCase("attribute"))
        break label45;
      List localList2 = ((Attribute)this.mNode).getLstChannels();
      if (localList2 != null)
        setData(localList2);
    }
    label45: List localList1;
    do
    {
      do
        return;
      while (!this.mNode.nodeName.equalsIgnoreCase("category"));
      localList1 = ((CategoryNode)this.mNode).getLstChannels();
    }
    while (localList1 == null);
    setData(localList1);
  }

  public void onRecvDataException(String paramString, InfoManager.DataExceptionStatus paramDataExceptionStatus)
  {
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.qtradio.controller.ChannelsListController
 * JD-Core Version:    0.6.2
 */