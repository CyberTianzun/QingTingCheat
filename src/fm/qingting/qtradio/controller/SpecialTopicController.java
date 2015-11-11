package fm.qingting.qtradio.controller;

import android.content.Context;
import fm.qingting.framework.controller.ViewController;
import fm.qingting.framework.manager.EventDispacthManager;
import fm.qingting.framework.model.INavigationBarListener;
import fm.qingting.framework.model.NavigationBarItem;
import fm.qingting.qtradio.model.InfoManager;
import fm.qingting.qtradio.model.InfoManager.DataExceptionStatus;
import fm.qingting.qtradio.model.InfoManager.ISubscribeEventListener;
import fm.qingting.qtradio.model.SpecialTopicNode;
import fm.qingting.qtradio.social.CloudCenter;
import fm.qingting.qtradio.view.navigation.NavigationBarCustomView;
import fm.qingting.qtradio.view.virtualchannellist.SpecialTopicView;
import fm.qingting.utils.QTMSGManage;

public class SpecialTopicController extends ViewController
  implements INavigationBarListener, InfoManager.ISubscribeEventListener
{
  public static final String NAME = "specialtopic";
  private NavigationBarCustomView mBarCustomView;
  private SpecialTopicNode mNode;
  private int mTopicId = 0;
  private SpecialTopicView mainView;

  public SpecialTopicController(Context paramContext)
  {
    super(paramContext);
    this.mBarCustomView = new NavigationBarCustomView(paramContext);
    this.mBarCustomView.addLeftItem(0);
    this.mBarCustomView.addRightItem(4);
    this.mBarCustomView.setBarListener(this);
    setNavigationBar(this.mBarCustomView);
    this.mainView = new SpecialTopicView(paramContext);
    attachView(this.mainView);
  }

  public void config(String paramString, Object paramObject)
  {
    if (paramString.equalsIgnoreCase("setData"))
    {
      localSpecialTopicNode = (SpecialTopicNode)paramObject;
      this.mNode = localSpecialTopicNode;
      this.mBarCustomView.setTitleItem(new NavigationBarItem(localSpecialTopicNode.title));
      this.mainView.update(paramString, paramObject);
    }
    while (!paramString.equalsIgnoreCase("setid"))
    {
      SpecialTopicNode localSpecialTopicNode;
      return;
    }
    this.mTopicId = ((Integer)paramObject).intValue();
    this.mNode = new SpecialTopicNode();
    this.mNode.id = (1000001 + this.mTopicId);
    InfoManager.getInstance().loadSpecialTopicNode(this.mNode, this);
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
    if (paramInt == 2)
      ControllerManager.getInstance().popLastController();
    while (paramInt != 3)
      return;
    if (CloudCenter.getInstance().isLogin())
      QTMSGManage.getInstance().sendStatistcsMessage("sharespecialtopic", "sharechoose");
    while (true)
    {
      EventDispacthManager.getInstance().dispatchAction("shareChoose", this.mNode);
      return;
      QTMSGManage.getInstance().sendStatistcsMessage("sharespecialtopic", "unlogin");
    }
  }

  public void onNotification(String paramString)
  {
    if ((paramString.equalsIgnoreCase("RECV_SPECIAL_TOPIC_CHANNELS")) && (this.mNode.title != null) && (!this.mNode.title.equalsIgnoreCase("")))
    {
      this.mBarCustomView.setTitleItem(new NavigationBarItem(this.mNode.title));
      this.mainView.update("setData", this.mNode);
    }
  }

  public void onRecvDataException(String paramString, InfoManager.DataExceptionStatus paramDataExceptionStatus)
  {
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.qtradio.controller.SpecialTopicController
 * JD-Core Version:    0.6.2
 */