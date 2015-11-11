package fm.qingting.qtradio.view.popviews;

import android.content.Context;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View.MeasureSpec;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ListView;
import fm.qingting.framework.adapter.CustomizedAdapter;
import fm.qingting.framework.adapter.IAdapterIViewFactory;
import fm.qingting.framework.adapter.ItemParam;
import fm.qingting.framework.event.IEventHandler;
import fm.qingting.framework.utils.BitmapResourceCache;
import fm.qingting.framework.view.IView;
import fm.qingting.framework.view.ViewGroupViewImpl;
import fm.qingting.framework.view.ViewLayout;
import fm.qingting.qtradio.manager.SkinManager;
import fm.qingting.qtradio.model.ChannelNode;
import fm.qingting.qtradio.model.InfoManager;
import fm.qingting.qtradio.model.InfoManager.DataExceptionStatus;
import fm.qingting.qtradio.model.InfoManager.ISubscribeEventListener;
import fm.qingting.qtradio.model.Node;
import fm.qingting.qtradio.model.ProgramNode;
import fm.qingting.qtradio.model.RootNode;
import fm.qingting.qtradio.model.RootNode.IPlayInfoEventListener;
import fm.qingting.utils.ListUtils;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

public class SchedulePopView extends ViewGroupViewImpl
  implements IEventHandler, InfoManager.ISubscribeEventListener, RootNode.IPlayInfoEventListener
{
  private CustomizedAdapter adapter = new CustomizedAdapter(new ArrayList(), this.factory);
  private IAdapterIViewFactory factory = new IAdapterIViewFactory()
  {
    public IView createView(int paramAnonymousInt)
    {
      return new SchedulePopItemView(SchedulePopView.this.getContext(), this.val$hash);
    }
  };
  private final ViewLayout listLayout = this.standardLayout.createChildLT(480, 300, 0, 200, ViewLayout.SCALE_FLAG_SLTCW);
  private ListView mListView;
  private HashSet<Integer> mLoadedPosition = new HashSet();
  private ChannelNode mNode;
  private int mSchedulePopViewBottom = 0;
  private SchedulePopHeaderView mTitleView;
  private final ViewLayout standardLayout = ViewLayout.createViewLayoutWithBoundsLT(480, 800, 480, 800, 0, 0, ViewLayout.FILL);
  private final ViewLayout titleLayout = this.standardLayout.createChildLT(480, 68, 0, 200, ViewLayout.SCALE_FLAG_SLTCW);

  public SchedulePopView(Context paramContext)
  {
    super(paramContext);
    this.adapter.setEventHandler(this);
    this.mListView = new ListView(paramContext);
    this.mListView.setBackgroundColor(SkinManager.getBackgroundColor());
    this.mListView.setOnScrollListener(new AbsListView.OnScrollListener()
    {
      public void onScroll(AbsListView paramAnonymousAbsListView, int paramAnonymousInt1, int paramAnonymousInt2, int paramAnonymousInt3)
      {
        if ((paramAnonymousInt3 - 10 > 0) && (paramAnonymousInt1 + paramAnonymousInt2 > paramAnonymousInt3 - 10))
          SchedulePopView.this.loadMore(paramAnonymousInt3);
      }

      public void onScrollStateChanged(AbsListView paramAnonymousAbsListView, int paramAnonymousInt)
      {
      }
    });
    this.mListView.setVerticalScrollBarEnabled(false);
    this.mListView.setVerticalFadingEdgeEnabled(false);
    this.mListView.setCacheColorHint(0);
    this.mListView.setDivider(null);
    this.mListView.setAdapter(this.adapter);
    addView(this.mListView);
    this.mTitleView = new SchedulePopHeaderView(paramContext);
    this.mTitleView.setEventHandler(this);
    addView(this.mTitleView);
    InfoManager.getInstance().root().registerSubscribeEventListener(this, 1);
  }

  private int getToday()
  {
    int i = Calendar.getInstance().get(7);
    if (i == 0)
      i = 7;
    int j = i - 1;
    if (j < 1)
      (j + 7);
    int k = i + 1;
    if (k > 7)
      (k - 7);
    return i;
  }

  private void loadMore(int paramInt)
  {
    if (this.mNode == null);
    while ((this.mLoadedPosition.contains(Integer.valueOf(paramInt))) || (!this.mNode.nodeName.equalsIgnoreCase("channel")))
      return;
    this.mLoadedPosition.add(Integer.valueOf(paramInt));
    InfoManager.getInstance().loadProgramsScheduleNode(this.mNode, this);
  }

  private void refreshByDayofweek(int paramInt)
  {
    List localList;
    if (this.mNode != null)
    {
      localList = this.mNode.getLstProgramNode(paramInt);
      if (localList != null);
    }
    else
    {
      return;
    }
    Node localNode = InfoManager.getInstance().root().getCurrentPlayingNode();
    int j;
    int i;
    if ((localNode != null) && (localNode.nodeName.equalsIgnoreCase("program")))
    {
      int k = ((ProgramNode)localNode).id;
      Iterator localIterator = localList.iterator();
      j = 0;
      if (localIterator.hasNext())
        if (((ProgramNode)localIterator.next()).id == k)
          i = 1;
    }
    while (true)
    {
      this.adapter.setData(ListUtils.convertToObjectList(localList));
      this.mTitleView.update("setData", Integer.valueOf(paramInt));
      if ((i != 0) && (j > 0))
      {
        this.mListView.setSelectionFromTop(j, this.titleLayout.height);
        return;
        j++;
        break;
      }
      this.mListView.setSelection(0);
      return;
      i = 0;
      continue;
      i = 0;
      j = 0;
    }
  }

  public void close(boolean paramBoolean)
  {
    if ((this.mNode instanceof ChannelNode))
      InfoManager.getInstance().alignTime(this.mNode.channelId);
    InfoManager.getInstance().root().unRegisterSubscribeEventListener(1, this);
    BitmapResourceCache.getInstance().clearResourceCacheOfOne(this, 0);
    super.close(paramBoolean);
  }

  public boolean dispatchTouchEvent(MotionEvent paramMotionEvent)
  {
    if (paramMotionEvent.getAction() == 0)
    {
      int i = this.mSchedulePopViewBottom - this.mListView.getMeasuredHeight() - this.titleLayout.height;
      if (paramMotionEvent.getY() < i)
      {
        dispatchActionEvent("cancelPop", null);
        return true;
      }
    }
    return super.dispatchTouchEvent(paramMotionEvent);
  }

  public void onEvent(Object paramObject1, String paramString, Object paramObject2)
  {
    if (paramString.equalsIgnoreCase("itemCallback"))
      if (paramObject2 != null)
      {
        str = ((ItemParam)paramObject2).type;
        if ((str != null) && (str.equalsIgnoreCase("refresh")))
          dispatchActionEvent("cancelPop", null);
      }
    while (!paramString.equalsIgnoreCase("selectSchedule"))
    {
      String str;
      return;
    }
    refreshByDayofweek(((Integer)paramObject2).intValue());
  }

  protected void onLayout(boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    this.mSchedulePopViewBottom = paramInt4;
    try
    {
      this.mListView.layout(this.listLayout.leftMargin, paramInt4 - this.mListView.getMeasuredHeight(), this.standardLayout.width - this.listLayout.leftMargin, paramInt4);
      this.mTitleView.layout(this.titleLayout.leftMargin, paramInt4 - this.mListView.getMeasuredHeight() - this.titleLayout.height, this.standardLayout.width - this.titleLayout.leftMargin, paramInt4 - this.mListView.getMeasuredHeight());
      Log.e("onLayout1:", "" + paramInt4);
      return;
    }
    catch (Exception localException)
    {
      while (true)
        localException.printStackTrace();
    }
  }

  protected void onMeasure(int paramInt1, int paramInt2)
  {
    int i = View.MeasureSpec.getSize(paramInt1);
    int j = View.MeasureSpec.getSize(paramInt2);
    this.standardLayout.scaleToBounds(i, j);
    this.listLayout.scaleToBounds(this.standardLayout);
    this.titleLayout.scaleToBounds(this.standardLayout);
    this.titleLayout.measureView(this.mTitleView);
    this.mTitleView.setPadding(this.titleLayout.width / 20, 0, 0, 0);
    this.mListView.measure(this.listLayout.getWidthMeasureSpec(), View.MeasureSpec.makeMeasureSpec(2 * this.standardLayout.height / 3, -2147483648));
    setMeasuredDimension(i, j);
  }

  public void onNotification(String paramString)
  {
  }

  public void onPlayInfoUpdated(int paramInt)
  {
    if ((paramInt == 1) && (this.adapter != null))
      this.adapter.notifyDataSetChanged();
  }

  public void onRecvDataException(String paramString, InfoManager.DataExceptionStatus paramDataExceptionStatus)
  {
  }

  public void update(String paramString, Object paramObject)
  {
    Node localNode;
    List localList;
    if (paramString.equalsIgnoreCase("setBubbleData"))
    {
      this.mLoadedPosition.clear();
      this.mNode = ((ChannelNode)paramObject);
      if (this.mNode.channelType != 1)
        break label196;
      localNode = InfoManager.getInstance().root().getCurrentPlayingNode();
      if ((localNode != null) && (localNode.nodeName.equalsIgnoreCase("program")))
      {
        localList = this.mNode.getAllLstProgramNode();
        if (localList != null)
          break label77;
      }
    }
    return;
    label77: int i = ((ProgramNode)localNode).id;
    Iterator localIterator = localList.iterator();
    int j = 0;
    if (localIterator.hasNext())
      if (((ProgramNode)localIterator.next()).id != i);
    for (int k = 1; ; k = 0)
    {
      this.adapter.setData(ListUtils.convertToObjectList(localList));
      this.mTitleView.update("setData", Integer.valueOf(0));
      if ((k != 0) && (j > 0))
      {
        this.mListView.setSelectionFromTop(j, this.titleLayout.height);
        return;
        j++;
        break;
      }
      this.mListView.setSelection(0);
      return;
      label196: refreshByDayofweek(getToday());
      return;
    }
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.qtradio.view.popviews.SchedulePopView
 * JD-Core Version:    0.6.2
 */