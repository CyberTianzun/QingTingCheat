package fm.qingting.qtradio.view.playview;

import android.content.Context;
import android.view.View.MeasureSpec;
import fm.qingting.framework.manager.EventDispacthManager;
import fm.qingting.framework.utils.BitmapResourceCache;
import fm.qingting.framework.view.QtView;
import fm.qingting.framework.view.ViewElement;
import fm.qingting.framework.view.ViewElement.OnElementClickListener;
import fm.qingting.framework.view.ViewLayout;
import fm.qingting.qtradio.alarm.ClockManager;
import fm.qingting.qtradio.alarm.ClockManager.IClockListener;
import fm.qingting.qtradio.controller.ControllerManager;
import fm.qingting.qtradio.manager.SkinManager;
import fm.qingting.qtradio.model.ChannelNode;
import fm.qingting.qtradio.model.Clock;
import fm.qingting.qtradio.model.CollectionNode;
import fm.qingting.qtradio.model.InfoManager;
import fm.qingting.qtradio.model.PersonalCenterNode;
import fm.qingting.qtradio.model.RootNode;
import fm.qingting.qtradio.model.RootNode.IInfoUpdateEventListener;
import fm.qingting.utils.QTMSGManage;
import java.util.Locale;

public class PlayActionsView extends QtView
  implements ViewElement.OnElementClickListener, RootNode.IInfoUpdateEventListener, ClockManager.IClockListener
{
  private final String MODEL_TIMER = "%02d'%02d";
  private final ViewLayout itemLayout = this.standardLayout.createChildLT(150, 100, 0, 0, ViewLayout.SCALE_FLAG_SLTCW);
  private CustomActionButtonElement[] mElements;
  private String mall = null;
  private final ViewLayout standardLayout = ViewLayout.createViewLayoutWithBoundsLT(720, 110, 720, 110, 0, 0, ViewLayout.FILL);

  public PlayActionsView(Context paramContext)
  {
    super(paramContext);
    int i = hashCode();
    this.mElements = new CustomActionButtonElement[5];
    for (int j = 0; j < this.mElements.length; j++)
    {
      CustomActionButtonElement localCustomActionButtonElement = new CustomActionButtonElement(paramContext);
      localCustomActionButtonElement.setOnElementClickListener(this);
      this.mElements[j] = localCustomActionButtonElement;
      addElement(localCustomActionButtonElement, i);
    }
    if (isFaved())
      this.mElements[0].setAction("已收藏", 2130837767, 2130837768);
    while (true)
    {
      this.mElements[1].setAction("定时", 2130837787, 2130837786);
      this.mElements[2].setAction("分享", 2130837785, 2130837784);
      this.mElements[3].setAction("签到", 2130837760, 2130837759);
      this.mElements[4].setAction("店铺", 2130837771, 2130837771);
      this.mElements[4].setVisible(4);
      InfoManager.getInstance().root().registerInfoUpdateListener(this, 0);
      ClockManager.getInstance().addListener(this);
      return;
      this.mElements[0].setAction("收藏", 2130837767, 2130837766);
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
      String str = String.format(localLocale, "%02d'%02d", arrayOfObject);
      this.mElements[1].setAction(str);
    }
  }

  public void onElementClick(ViewElement paramViewElement)
  {
    if (paramViewElement == this.mElements[2])
    {
      EventDispacthManager.getInstance().dispatchAction("shareChoose", InfoManager.getInstance().root().getCurrentPlayingNode());
      QTMSGManage.getInstance().sendStatistcsMessage("PlayView", "share");
    }
    do
    {
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
      if (paramViewElement == this.mElements[3])
      {
        int i = InfoManager.getInstance().getEnableNewCheckin();
        if (i == 2)
        {
          QTMSGManage.getInstance().sendStatistcsMessage("PlayView", "checkinABTestNew");
          QTMSGManage.getInstance().sendStatistcsMessage("checkinABTestNew");
          ControllerManager.getInstance().redirectToLocalWebView("http://qtime.qingting.fm/check?from=1", "签到", true);
          return;
        }
        if (i == 1)
        {
          QTMSGManage.getInstance().sendStatistcsMessage("PlayView", "checkinABTestOld");
          QTMSGManage.getInstance().sendStatistcsMessage("checkinABTestOld");
          dispatchActionEvent("checkin", null);
          return;
        }
        QTMSGManage.getInstance().sendStatistcsMessage("PlayView", "checkin");
        dispatchActionEvent("checkin", null);
        return;
      }
      if (paramViewElement == this.mElements[1])
      {
        ControllerManager.getInstance().openTimerSettingController();
        return;
      }
    }
    while (paramViewElement != this.mElements[4]);
    QTMSGManage.getInstance().sendStatistcsMessage("mallActionClick");
    ControllerManager.getInstance().redirectToLocalWebView(this.mall, "店铺", false);
  }

  public void onInfoUpdated(int paramInt)
  {
    if (paramInt == 0)
    {
      ChannelNode localChannelNode = InfoManager.getInstance().root().getCurrentPlayingChannelNode();
      if (InfoManager.getInstance().root().mPersonalCenterNode.myCollectionNode.isExisted(localChannelNode))
        this.mElements[0].setAction("已收藏", 2130837767, 2130837768);
    }
    else
    {
      return;
    }
    this.mElements[0].setAction("收藏", 2130837767, 2130837766);
  }

  protected void onMeasure(int paramInt1, int paramInt2)
  {
    int i = 1;
    int j = 0;
    this.standardLayout.scaleToBounds(View.MeasureSpec.getSize(paramInt1), View.MeasureSpec.getSize(paramInt2));
    this.itemLayout.scaleToBounds(this.standardLayout);
    int k;
    label58: int m;
    if (this.mElements[0].getVisiblity() == 0)
    {
      k = i;
      if (this.mElements[4].getVisiblity() != 0)
        break label198;
      m = this.mElements.length;
      if (k != 0)
        break label222;
    }
    label198: label222: for (int n = m - 1; ; n = m)
    {
      if (i == 0)
        n--;
      int i1 = (this.standardLayout.width - n * this.itemLayout.width) / (n + 1);
      int i2 = i1;
      while (true)
        if (j < this.mElements.length)
        {
          if (this.mElements[j].getVisiblity() == 0)
          {
            this.mElements[j].measure(i2, this.itemLayout.topMargin, i2 + this.itemLayout.width, this.itemLayout.getBottom());
            i2 += i1 + this.itemLayout.width;
          }
          j++;
          continue;
          k = 0;
          break;
          i = 0;
          break label58;
        }
      setMeasuredDimension(this.standardLayout.width, this.standardLayout.height);
      return;
    }
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
        String str = String.format(localLocale, "%02d'%02d", arrayOfObject);
        this.mElements[1].setAction(str);
        this.mElements[1].setNameColor(-1);
      }
    }
  }

  public void onTimeStop(Clock paramClock)
  {
  }

  public void onTimerRemoved()
  {
    this.mElements[1].setAction("定时");
    this.mElements[1].setNameColor(SkinManager.getNewPlaySubColor());
  }

  public void update(String paramString, Object paramObject)
  {
    if (paramString.equalsIgnoreCase("showFav"))
      if (isFaved())
      {
        this.mElements[0].setAction("已收藏", 2130837767, 2130837768);
        int j = this.mElements[0].getVisiblity();
        this.mElements[0].setVisible(0);
        if (j == 4)
          requestLayout();
      }
    label125: 
    do
    {
      do
      {
        int i;
        do
        {
          return;
          this.mElements[0].setAction("收藏", 2130837767, 2130837766);
          break;
          if (!paramString.equalsIgnoreCase("hideFav"))
            break label125;
          i = this.mElements[0].getVisiblity();
          this.mElements[0].setVisible(4);
        }
        while (i != 0);
        requestLayout();
        return;
        if (!paramString.equalsIgnoreCase("showMall"))
          break label172;
      }
      while (this.mElements[4].getVisiblity() != 4);
      this.mall = ((String)paramObject);
      this.mElements[4].setVisible(0);
      requestLayout();
      return;
    }
    while ((!paramString.equalsIgnoreCase("hideMall")) || (this.mElements[4].getVisiblity() != 0));
    label172: this.mElements[4].setVisible(4);
    requestLayout();
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.qtradio.view.playview.PlayActionsView
 * JD-Core Version:    0.6.2
 */