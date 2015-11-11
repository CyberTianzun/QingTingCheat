package fm.qingting.qtradio.view.personalcenter.clock;

import android.content.Context;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import fm.qingting.framework.adapter.IAdapterIViewFactory;
import fm.qingting.framework.adapter.ItemParam;
import fm.qingting.framework.event.IEventHandler;
import fm.qingting.framework.utils.BitmapResourceCache;
import fm.qingting.framework.view.IView;
import fm.qingting.framework.view.ListViewImpl;
import fm.qingting.qtradio.model.MutiCheckManageableAdapter;
import fm.qingting.qtradio.view.personalcenter.faq.LargeButtonView;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class AlarmListView extends ListViewImpl
  implements IEventHandler
{
  private MutiCheckManageableAdapter adapter = new MutiCheckManageableAdapter(new ArrayList(), this.factory);
  private IAdapterIViewFactory factory = new IAdapterIViewFactory()
  {
    public IView createView(int paramAnonymousInt)
    {
      return new AlarmItemView(AlarmListView.this.getContext(), this.val$hash);
    }
  };
  private int mFirstPosition = 0;
  private int mVisibleCnt = 0;

  public AlarmListView(Context paramContext)
  {
    super(paramContext);
    this.adapter.setEventHandler(this);
    setVerticalScrollBarEnabled(false);
    setVerticalFadingEdgeEnabled(false);
    setCacheColorHint(0);
    setDivider(null);
    setHeaderDividersEnabled(false);
    setSelector(17170445);
    setAdapter(this.adapter);
    setOnScrollListener(new AbsListView.OnScrollListener()
    {
      public void onScroll(AbsListView paramAnonymousAbsListView, int paramAnonymousInt1, int paramAnonymousInt2, int paramAnonymousInt3)
      {
        AlarmListView.access$002(AlarmListView.this, paramAnonymousInt1);
        AlarmListView.access$102(AlarmListView.this, paramAnonymousInt2);
      }

      public void onScrollStateChanged(AbsListView paramAnonymousAbsListView, int paramAnonymousInt)
      {
      }
    });
    LargeButtonView localLargeButtonView = new LargeButtonView(paramContext, "添加闹钟");
    localLargeButtonView.setEventHandler(this);
    addFooterView(localLargeButtonView);
  }

  private void invalidateVisibleChildren()
  {
    for (int i = this.mFirstPosition; i < this.mFirstPosition + this.mVisibleCnt; i++)
    {
      View localView = getChildAt(i);
      if (localView != null)
        localView.invalidate();
    }
  }

  public void close(boolean paramBoolean)
  {
    BitmapResourceCache.getInstance().clearResourceCacheOfOne(this, 0);
    super.close(paramBoolean);
  }

  public Object getValue(String paramString, Object paramObject)
  {
    Boolean localBoolean;
    if (paramString.equalsIgnoreCase("hasCheckedIndexs"))
      localBoolean = Boolean.valueOf(this.adapter.hasCheckedIndexs());
    boolean bool;
    do
    {
      Iterator localIterator;
      List localList;
      do
      {
        do
        {
          return localBoolean;
          if (!paramString.equalsIgnoreCase("deletelist"))
            break;
          localIterator = this.adapter.getCheckList();
          localList = this.adapter.getData();
          localBoolean = null;
        }
        while (localIterator == null);
        localBoolean = null;
      }
      while (localList == null);
      ArrayList localArrayList = new ArrayList();
      while (localIterator.hasNext())
      {
        int i = ((Integer)localIterator.next()).intValue();
        if ((i >= 0) && (i < localList.size()))
          localArrayList.add(localList.get(i));
      }
      return localArrayList;
      bool = paramString.equalsIgnoreCase("allData");
      localBoolean = null;
    }
    while (!bool);
    return this.adapter.getData();
  }

  public void onEvent(Object paramObject1, String paramString, Object paramObject2)
  {
    if (paramString.equalsIgnoreCase("itemCallback"))
    {
      ItemParam localItemParam = (ItemParam)paramObject2;
      int i = localItemParam.position;
      if (localItemParam.type.equalsIgnoreCase("select"))
      {
        dispatchActionEvent("select", Integer.valueOf(i));
        return;
      }
      this.adapter.checkIndex(i);
      return;
    }
    dispatchActionEvent(paramString, paramObject2);
  }

  public void update(String paramString, Object paramObject)
  {
    if (paramString.equalsIgnoreCase("invalidateList"))
      invalidateVisibleChildren();
    do
    {
      return;
      if (paramString.equalsIgnoreCase("refreshList"))
      {
        this.adapter.notifyDataSetChanged();
        return;
      }
      if (paramString.equalsIgnoreCase("resetData"))
      {
        this.adapter.setData((List)paramObject);
        return;
      }
      if (paramString.equalsIgnoreCase("showManage"))
      {
        this.adapter.showManage(((Integer)paramObject).intValue());
        return;
      }
      if (paramString.equalsIgnoreCase("hideManage"))
      {
        this.adapter.hideManage();
        return;
      }
      if (paramString.equalsIgnoreCase("setData"))
      {
        this.adapter.setData((List)paramObject);
        return;
      }
      if (paramString.equalsIgnoreCase("changeProcessState"))
      {
        this.adapter.notifyDataSetChanged();
        return;
      }
      if (paramString.equalsIgnoreCase("resetCheckList"))
      {
        this.adapter.resetCheck();
        return;
      }
      if (paramString.equalsIgnoreCase("selectAll"))
      {
        if (((Boolean)paramObject).booleanValue())
        {
          this.adapter.checkAll();
          return;
        }
        this.adapter.resetCheck();
        return;
      }
    }
    while (!paramString.equalsIgnoreCase("delete"));
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.qtradio.view.personalcenter.clock.AlarmListView
 * JD-Core Version:    0.6.2
 */