package fm.qingting.qtradio.view.virtualchannellist;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import fm.qingting.framework.adapter.CustomizedAdapter;
import fm.qingting.framework.adapter.IAdapterIViewFactory;
import fm.qingting.framework.utils.BitmapResourceCache;
import fm.qingting.framework.view.IView;
import fm.qingting.framework.view.ViewGroupViewImpl;
import fm.qingting.qtradio.helper.ChannelHelper;
import fm.qingting.qtradio.model.InfoManager.DataExceptionStatus;
import fm.qingting.qtradio.model.InfoManager.ISubscribeEventListener;
import fm.qingting.qtradio.model.RecommendItemNode;
import fm.qingting.qtradio.view.LoadMoreFootView;
import fm.qingting.utils.ListUtils;
import java.util.ArrayList;
import java.util.List;

public class VirtualChannelListView extends ViewGroupViewImpl
  implements InfoManager.ISubscribeEventListener
{
  private CustomizedAdapter mAdapter = new CustomizedAdapter(new ArrayList(), this.mFactory);
  private IAdapterIViewFactory mFactory = new IAdapterIViewFactory()
  {
    public IView createView(int paramAnonymousInt)
    {
      return new VirtualChannelItemView(VirtualChannelListView.this.getContext(), this.val$hash);
    }
  };
  private LoadMoreFootView mFooterView;
  private LinearLayout mLinearLayout;
  private PullToRefreshListView mListView;
  private RecommendItemNode mNode;

  public VirtualChannelListView(Context paramContext)
  {
    super(paramContext);
    this.mLinearLayout = ((LinearLayout)LayoutInflater.from(paramContext).inflate(2130903040, null));
    this.mListView = ((PullToRefreshListView)this.mLinearLayout.findViewById(2131230731));
    this.mListView.setVerticalScrollBarEnabled(false);
    this.mListView.setVerticalFadingEdgeEnabled(false);
    this.mListView.setSelector(17170445);
    this.mFooterView = new LoadMoreFootView(paramContext);
    this.mListView.addListFooterView(this.mFooterView);
    this.mListView.setOnScrollListener(new AbsListView.OnScrollListener()
    {
      public void onScroll(AbsListView paramAnonymousAbsListView, int paramAnonymousInt1, int paramAnonymousInt2, int paramAnonymousInt3)
      {
        if (paramAnonymousInt2 == paramAnonymousInt3)
          VirtualChannelListView.this.mFooterView.hideLoad();
        while ((VirtualChannelListView.this.mFooterView.isAll()) || (VirtualChannelListView.this.mFooterView.isLoading()) || (paramAnonymousInt1 + paramAnonymousInt2 < paramAnonymousInt3))
          return;
        VirtualChannelListView.this.mFooterView.showLoad();
        VirtualChannelListView.this.loadMore();
      }

      public void onScrollStateChanged(AbsListView paramAnonymousAbsListView, int paramAnonymousInt)
      {
      }
    });
    this.mListView.setAdapter(this.mAdapter);
    addView(this.mLinearLayout);
    this.mListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener()
    {
      public void onRefresh(PullToRefreshBase<ListView> paramAnonymousPullToRefreshBase)
      {
        if (VirtualChannelListView.this.mNode != null)
          ChannelHelper.getInstance().reloadListVirtualChannelNodesById(VirtualChannelListView.this.mNode.mCategoryId, VirtualChannelListView.this.mNode.mAttributesPath, VirtualChannelListView.this);
      }
    });
  }

  private void loadMore()
  {
    if (this.mNode == null)
      return;
    ChannelHelper.getInstance().loadListVirtualChannelNodesById(this.mNode.mCategoryId, this.mNode.mAttributesPath, this);
  }

  public void close(boolean paramBoolean)
  {
    BitmapResourceCache.getInstance().clearResourceCacheOfOne(this, 0);
    super.close(paramBoolean);
  }

  protected void onLayout(boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    this.mLinearLayout.layout(0, 0, paramInt3 - paramInt1, paramInt4 - paramInt2);
  }

  protected void onMeasure(int paramInt1, int paramInt2)
  {
    this.mLinearLayout.measure(paramInt1, paramInt2);
    super.onMeasure(paramInt1, paramInt2);
  }

  public void onNotification(String paramString)
  {
    List localList;
    LoadMoreFootView localLoadMoreFootView;
    if (paramString.equalsIgnoreCase("RECV_VIRTUAL_CHANNELS_BYATTR"))
    {
      localList = ChannelHelper.getInstance().getLstChannelsByAttrPath(this.mNode.mCategoryId, this.mNode.mAttributesPath);
      this.mListView.onRefreshComplete();
      this.mFooterView.hideLoad();
      if (ChannelHelper.getInstance().isFinished(this.mNode.mCategoryId, this.mNode.mAttributesPath))
        this.mFooterView.setAll();
      this.mAdapter.setData(ListUtils.convertToObjectList(localList));
      localLoadMoreFootView = this.mFooterView;
      if (localList != null)
        break label104;
    }
    label104: for (int i = 0; ; i = localList.size())
    {
      localLoadMoreFootView.hideFootIfItemsNotEnough(i);
      return;
    }
  }

  public void onRecvDataException(String paramString, InfoManager.DataExceptionStatus paramDataExceptionStatus)
  {
  }

  public void update(String paramString, Object paramObject)
  {
    List localList;
    if (paramString.equalsIgnoreCase("setData"))
    {
      this.mNode = ((RecommendItemNode)paramObject);
      localList = ChannelHelper.getInstance().getLstChannelsByAttrPath(this.mNode.mCategoryId, this.mNode.mAttributesPath);
      if (localList == null)
        this.mListView.setRefreshing();
    }
    else
    {
      return;
    }
    this.mAdapter.setData(ListUtils.convertToObjectList(localList));
    if (ChannelHelper.getInstance().isFinished(this.mNode.mCategoryId, this.mNode.mAttributesPath))
      this.mFooterView.setAll();
    this.mFooterView.hideFootIfItemsNotEnough(localList.size());
    this.mListView.onRefreshComplete();
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.qtradio.view.virtualchannellist.VirtualChannelListView
 * JD-Core Version:    0.6.2
 */