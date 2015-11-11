package fm.qingting.qtradio.view.personalcenter.mycollection;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import fm.qingting.framework.adapter.CustomizedAdapter.ViewEventHandler;
import fm.qingting.framework.adapter.ItemParam;
import fm.qingting.framework.event.IEventHandler;
import fm.qingting.framework.utils.BitmapResourceCache;
import fm.qingting.framework.view.ListViewImpl;
import fm.qingting.qtradio.model.ChannelNode;
import fm.qingting.qtradio.model.MutiCheckManageableAdapter;
import fm.qingting.qtradio.view.moreContentView.CustomSectionView;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MyCollectionListView extends ListViewImpl
  implements IEventHandler
{
  private MyCollectionAdapter adapter;
  private int mFirstPosition = 0;
  private int mHash;
  private int mVisibleCnt = 0;

  public MyCollectionListView(Context paramContext, int paramInt)
  {
    super(paramContext);
    this.mHash = paramInt;
    this.adapter = new MyCollectionAdapter(new ArrayList());
    this.adapter.setEventHandler(this);
    setVerticalScrollBarEnabled(false);
    setVerticalFadingEdgeEnabled(false);
    setCacheColorHint(0);
    setDivider(null);
    setHeaderDividersEnabled(false);
    setSelector(new ColorDrawable(0));
    setAdapter(this.adapter);
    setOnScrollListener(new AbsListView.OnScrollListener()
    {
      public void onScroll(AbsListView paramAnonymousAbsListView, int paramAnonymousInt1, int paramAnonymousInt2, int paramAnonymousInt3)
      {
        MyCollectionListView.access$002(MyCollectionListView.this, paramAnonymousInt1);
        MyCollectionListView.access$102(MyCollectionListView.this, paramAnonymousInt2);
      }

      public void onScrollStateChanged(AbsListView paramAnonymousAbsListView, int paramAnonymousInt)
      {
      }
    });
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
        {
          Object localObject = localList.get(i);
          if ((localObject instanceof ChannelNode))
            localArrayList.add(localObject);
        }
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
      int i = ((ItemParam)paramObject2).position;
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
        Log.d("Collectionlistview", "show manager:" + paramObject);
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
        List localList = (List)paramObject;
        this.adapter.setData(localList);
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

  private class MyCollectionAdapter extends MutiCheckManageableAdapter
  {
    private List<ChannelNode> mLiveChannelLst = new ArrayList();
    private List<ChannelNode> mVirtualChannelLst = new ArrayList();

    public MyCollectionAdapter()
    {
      super(null);
      fillLists(localList);
    }

    private void fillLists(List<Object> paramList)
    {
      this.mLiveChannelLst.clear();
      this.mVirtualChannelLst.clear();
      if (paramList != null)
      {
        Iterator localIterator = paramList.iterator();
        while (localIterator.hasNext())
        {
          Object localObject = localIterator.next();
          if ((localObject instanceof ChannelNode))
          {
            ChannelNode localChannelNode = (ChannelNode)localObject;
            if (localChannelNode.channelType == 0)
              this.mLiveChannelLst.add(localChannelNode);
            else if (localChannelNode.channelType == 1)
              this.mVirtualChannelLst.add(localChannelNode);
          }
        }
      }
    }

    private View newView(int paramInt)
    {
      if (paramInt == 0)
        return new MyCollectionItemView2(MyCollectionListView.this.getContext(), MyCollectionListView.this.mHash);
      if (paramInt == 1)
        return new GroupTitleItemView(MyCollectionListView.this.getContext());
      if (paramInt == 2)
        return new CustomSectionView(MyCollectionListView.this.getContext());
      return null;
    }

    public int getCount()
    {
      int i = this.mLiveChannelLst.size();
      int j = 0;
      if (i > 0)
        j = 0 + (1 + this.mLiveChannelLst.size());
      if (this.mVirtualChannelLst.size() > 0)
        j += 1 + this.mVirtualChannelLst.size();
      if ((this.mLiveChannelLst.size() > 0) && (this.mVirtualChannelLst.size() > 0))
        j++;
      return j;
    }

    public List<Object> getData()
    {
      ArrayList localArrayList = new ArrayList();
      if (this.mLiveChannelLst.size() > 0)
      {
        localArrayList.add("");
        localArrayList.addAll(this.mLiveChannelLst);
      }
      if (this.mVirtualChannelLst.size() > 0)
      {
        if (this.mLiveChannelLst.size() > 0)
          localArrayList.add("");
        localArrayList.add("");
        localArrayList.addAll(this.mVirtualChannelLst);
      }
      return localArrayList;
    }

    public Object getItem(int paramInt)
    {
      int i = this.mLiveChannelLst.size();
      int j = 0;
      if (i > 0)
      {
        j = 2 + this.mLiveChannelLst.size();
        if (paramInt == 0)
          return "电台";
        if (paramInt <= this.mLiveChannelLst.size())
          return this.mLiveChannelLst.get(paramInt - 1);
        if (paramInt == j - 1)
          return "分割线";
      }
      int k = paramInt - j;
      if (k == 0)
        return "专辑";
      if (k <= this.mVirtualChannelLst.size())
        return this.mVirtualChannelLst.get(k - 1);
      return null;
    }

    public int getItemViewType(int paramInt)
    {
      int i;
      if (this.mLiveChannelLst.size() > 0)
      {
        i = 2 + this.mLiveChannelLst.size();
        if (paramInt != 0);
      }
      int j;
      do
      {
        return 1;
        if (paramInt <= this.mLiveChannelLst.size())
          return 0;
        if (paramInt == i - 1)
        {
          return 2;
          i = 0;
        }
        j = paramInt - i;
      }
      while (j == 0);
      if (j <= this.mVirtualChannelLst.size())
        return 0;
      return 0;
    }

    public View getView(int paramInt, View paramView, ViewGroup paramViewGroup)
    {
      int i = getItemViewType(paramInt);
      Object localObject = getItem(paramInt);
      if (paramView == null);
      for (View localView = newView(i); ; localView = paramView)
      {
        MyCollectionItemView2 localMyCollectionItemView2;
        if (localView != null)
        {
          if (i != 0)
            break label116;
          localMyCollectionItemView2 = (MyCollectionItemView2)localView;
          localMyCollectionItemView2.update("content", localObject);
          localMyCollectionItemView2.update("checkstate", Boolean.valueOf(isChecked(paramInt)));
          if (!this.showDeleteButton)
            break label105;
          localMyCollectionItemView2.update("showManage", Integer.valueOf(this.mCheckOffset));
          localMyCollectionItemView2.setEventHandler(new CustomizedAdapter.ViewEventHandler(this, paramInt));
        }
        label105: label116: 
        do
        {
          return localView;
          localMyCollectionItemView2.update("hideManage", null);
          break;
          if (i == 1)
          {
            ((GroupTitleItemView)localView).setTitle((String)localObject);
            return localView;
          }
        }
        while (i != 2);
        ((CustomSectionView)localView);
        return localView;
      }
    }

    public int getViewTypeCount()
    {
      return 3;
    }

    public void setData(List<Object> paramList)
    {
      fillLists(paramList);
      super.setData(paramList);
    }
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.qtradio.view.personalcenter.mycollection.MyCollectionListView
 * JD-Core Version:    0.6.2
 */