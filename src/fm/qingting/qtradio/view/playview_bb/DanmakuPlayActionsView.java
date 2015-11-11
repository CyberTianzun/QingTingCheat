package fm.qingting.qtradio.view.playview_bb;

import android.content.Context;
import android.view.View.MeasureSpec;
import fm.qingting.framework.manager.EventDispacthManager;
import fm.qingting.framework.utils.BitmapResourceCache;
import fm.qingting.framework.view.ButtonViewElement;
import fm.qingting.framework.view.QtView;
import fm.qingting.framework.view.ViewElement;
import fm.qingting.framework.view.ViewElement.OnElementClickListener;
import fm.qingting.framework.view.ViewLayout;
import fm.qingting.qtradio.alarm.ClockManager;
import fm.qingting.qtradio.alarm.ClockManager.IClockListener;
import fm.qingting.qtradio.controller.ControllerManager;
import fm.qingting.qtradio.model.ChannelNode;
import fm.qingting.qtradio.model.Clock;
import fm.qingting.qtradio.model.CollectionNode;
import fm.qingting.qtradio.model.InfoManager;
import fm.qingting.qtradio.model.Node;
import fm.qingting.qtradio.model.PersonalCenterNode;
import fm.qingting.qtradio.model.ProgramNode;
import fm.qingting.qtradio.model.RootNode;
import fm.qingting.qtradio.model.RootNode.IInfoUpdateEventListener;
import fm.qingting.utils.QTMSGManage;
import fm.qingting.utils.ScreenType;
import java.util.Locale;

public class DanmakuPlayActionsView extends QtView
  implements ViewElement.OnElementClickListener, RootNode.IInfoUpdateEventListener, ClockManager.IClockListener
{
  private final String MODEL_TIMER = "%02d'%02d";
  private final ViewLayout itemLayout = this.standardLayout.createChildLT(60, 60, 0, 0, ViewLayout.SCALE_FLAG_SLTCW);
  private ButtonViewElement[] mElements;
  private final ViewLayout standardLayout = ViewLayout.createViewLayoutWithBoundsLT(720, 110, 720, 110, 0, 0, ViewLayout.FILL);

  public DanmakuPlayActionsView(Context paramContext)
  {
    super(paramContext);
    int i = hashCode();
    int j = 2 * ScreenType.getCustomExtraBound();
    this.mElements = new ButtonViewElement[4];
    for (int k = 0; k < this.mElements.length; k++)
    {
      ButtonViewElement localButtonViewElement = new ButtonViewElement(paramContext);
      localButtonViewElement.setOnElementClickListener(this);
      this.mElements[k] = localButtonViewElement;
      addElement(localButtonViewElement, i);
      localButtonViewElement.expandHotPot(j);
    }
    if (isFaved())
      this.mElements[0].setBackground(2130837530, 2130837530);
    while (true)
    {
      this.mElements[1].setBackground(2130837531, 2130837531);
      this.mElements[2].setBackground(2130837533, 2130837533);
      this.mElements[3].setBackground(2130837528, 2130837528);
      InfoManager.getInstance().root().registerInfoUpdateListener(this, 0);
      ClockManager.getInstance().addListener(this);
      return;
      this.mElements[0].setBackground(2130837529, 2130837529);
    }
  }

  private boolean isFaved()
  {
    ChannelNode localChannelNode = InfoManager.getInstance().root().getCurrentPlayingChannelNode();
    return InfoManager.getInstance().root().mPersonalCenterNode.myCollectionNode.isExisted(localChannelNode);
  }

  public void close(boolean paramBoolean)
  {
    BitmapResourceCache.getInstance().clearResourceCacheOfOne(this, 0);
    super.close(paramBoolean);
  }

  public void onClockTime(int paramInt)
  {
    ClockManager localClockManager = ClockManager.getInstance();
    if (ClockManager.getInstance().getTimerAvailable())
    {
      int i = localClockManager.getTimerLeft();
      if (i < 0)
        i = 0;
      Locale localLocale = Locale.US;
      Object[] arrayOfObject = new Object[2];
      arrayOfObject[0] = Integer.valueOf(i / 60);
      arrayOfObject[1] = Integer.valueOf(i % 60);
      String.format(localLocale, "%02d'%02d", arrayOfObject);
    }
  }

  public void onElementClick(ViewElement paramViewElement)
  {
    int j;
    if (paramViewElement == this.mElements[3])
    {
      j = InfoManager.getInstance().getEnableNewCheckin();
      if (j == 2)
      {
        QTMSGManage.getInstance().sendStatistcsMessage("PlayView", "checkinABTestNew");
        QTMSGManage.getInstance().sendStatistcsMessage("checkinABTestNew");
        ControllerManager.getInstance().redirectToLocalWebView("http://qtime.qingting.fm/check?from=1", "签到", true);
      }
    }
    do
    {
      Node localNode;
      do
      {
        return;
        if (j == 1)
        {
          QTMSGManage.getInstance().sendStatistcsMessage("PlayView", "checkinABTestOld");
          QTMSGManage.getInstance().sendStatistcsMessage("checkinABTestOld");
          dispatchActionEvent("checkin", null);
          return;
        }
        QTMSGManage.getInstance().sendStatistcsMessage("PlayView", "checkin");
        dispatchActionEvent("checkin", null);
        return;
        if (paramViewElement == this.mElements[0])
        {
          QTMSGManage.getInstance().sendStatistcsMessage("PlayView", "collection");
          ChannelNode localChannelNode = InfoManager.getInstance().root().getCurrentPlayingChannelNode();
          CollectionNode localCollectionNode = InfoManager.getInstance().root().mPersonalCenterNode.myCollectionNode;
          if (localCollectionNode.isExisted(localChannelNode))
          {
            localCollectionNode.deleteFavNode(localChannelNode);
            return;
          }
          localCollectionNode.addFavNode(localChannelNode);
          return;
        }
        if (paramViewElement != this.mElements[1])
          break;
        EventDispacthManager.getInstance().dispatchAction("shareChoose", InfoManager.getInstance().root().getCurrentPlayingNode());
        QTMSGManage.getInstance().sendStatistcsMessage("PlayView", "share");
        localNode = InfoManager.getInstance().root().getCurrentPlayingNode();
      }
      while ((localNode == null) || (!localNode.nodeName.equalsIgnoreCase("program")));
      int i = ((ProgramNode)localNode).id;
      QTMSGManage.getInstance().sendStatistcsMessage("danmaku_share", "" + i);
      return;
    }
    while (paramViewElement != this.mElements[2]);
    ControllerManager.getInstance().openTimerSettingController();
  }

  public void onInfoUpdated(int paramInt)
  {
    if (paramInt == 0)
    {
      ChannelNode localChannelNode = InfoManager.getInstance().root().getCurrentPlayingChannelNode();
      if (InfoManager.getInstance().root().mPersonalCenterNode.myCollectionNode.isExisted(localChannelNode))
        this.mElements[0].setBackground(2130837530, 2130837530);
    }
    else
    {
      return;
    }
    this.mElements[0].setBackground(2130837529, 2130837529);
  }

  protected void onMeasure(int paramInt1, int paramInt2)
  {
    this.standardLayout.scaleToBounds(View.MeasureSpec.getSize(paramInt1), View.MeasureSpec.getSize(paramInt2));
    this.itemLayout.scaleToBounds(this.standardLayout);
    int i;
    int j;
    label51: int k;
    if (this.mElements[0].getVisiblity() == 0)
    {
      i = 1;
      if (i == 0)
        break label176;
      j = this.mElements.length;
      k = (this.standardLayout.width - j * this.itemLayout.width) / (j + 1);
      if (i == 0)
        break label188;
    }
    label176: label188: for (int m = 0; ; m = 1)
    {
      int n = (this.standardLayout.height - this.itemLayout.height) / 2;
      int i1 = k;
      while (m < this.mElements.length)
      {
        this.mElements[m].measure(i1, n, i1 + this.itemLayout.width, n + this.itemLayout.height);
        i1 += k + this.itemLayout.width;
        m++;
      }
      i = 0;
      break;
      j = -1 + this.mElements.length;
      break label51;
    }
    setMeasuredDimension(this.standardLayout.width, this.standardLayout.height);
  }

  public void onTime(Clock paramClock)
  {
  }

  public void onTimeStart(Clock paramClock)
  {
    if (paramClock.type == 2)
    {
      ClockManager localClockManager = ClockManager.getInstance();
      if (ClockManager.getInstance().getTimerAvailable())
      {
        int i = localClockManager.getTimerLeft();
        if (i < 0)
          i = 0;
        Locale localLocale = Locale.US;
        Object[] arrayOfObject = new Object[2];
        arrayOfObject[0] = Integer.valueOf(i / 60);
        arrayOfObject[1] = Integer.valueOf(i % 60);
        String.format(localLocale, "%02d'%02d", arrayOfObject);
      }
    }
  }

  public void onTimeStop(Clock paramClock)
  {
  }

  public void onTimerRemoved()
  {
  }

  public void update(String paramString, Object paramObject)
  {
    if (paramString.equalsIgnoreCase("showFav"))
      if (isFaved())
      {
        this.mElements[0].setBackground(2130837530, 2130837530);
        int j = this.mElements[0].getVisiblity();
        this.mElements[0].setVisible(0);
        if (j == 4)
          requestLayout();
      }
    int i;
    do
    {
      do
      {
        return;
        this.mElements[0].setBackground(2130837529, 2130837529);
        break;
      }
      while (!paramString.equalsIgnoreCase("hideFav"));
      i = this.mElements[0].getVisiblity();
      this.mElements[0].setVisible(4);
    }
    while (i != 0);
    requestLayout();
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.qtradio.view.playview_bb.DanmakuPlayActionsView
 * JD-Core Version:    0.6.2
 */