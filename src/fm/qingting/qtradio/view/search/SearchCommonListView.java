package fm.qingting.qtradio.view.search;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.View.MeasureSpec;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ListView;
import fm.qingting.framework.adapter.CustomizedAdapter;
import fm.qingting.framework.adapter.IAdapterIViewFactory;
import fm.qingting.framework.utils.BitmapResourceCache;
import fm.qingting.framework.view.IView;
import fm.qingting.framework.view.ViewGroupViewImpl;
import fm.qingting.framework.view.ViewLayout;
import fm.qingting.qtradio.model.InfoManager;
import fm.qingting.qtradio.model.RootNode;
import fm.qingting.qtradio.search.SearchItemNode;
import fm.qingting.qtradio.search.SearchNode;
import fm.qingting.qtradio.view.personalcenter.mydownload.EmptyTipUtil;
import fm.qingting.qtradio.view.personalcenter.mydownload.EmptyTipsView;
import fm.qingting.utils.InputMethodUtil.InputStateDelegate;
import fm.qingting.utils.ListUtils;
import java.util.ArrayList;
import java.util.List;

public class SearchCommonListView extends ViewGroupViewImpl
{
  private CustomizedAdapter mAdapter;
  private InputMethodUtil.InputStateDelegate mDelegate;
  private EmptyTipsView mEmptyView;
  private IAdapterIViewFactory mFactory;
  private ListView mListView;
  private int mType;
  private final ViewLayout standardLayout = ViewLayout.createViewLayoutWithBoundsLT(720, 1200, 720, 1200, 0, 0, ViewLayout.FILL);

  public SearchCommonListView(Context paramContext, int paramInt)
  {
    super(paramContext);
    this.mType = paramInt;
    this.mFactory = new IAdapterIViewFactory()
    {
      public IView createView(int paramAnonymousInt)
      {
        switch (SearchCommonListView.this.mType)
        {
        default:
          return null;
        case 2:
          return new SearchItemPodcasterView(SearchCommonListView.this.getContext(), this.val$hash);
        case 3:
          return new SearchItemChannelView(SearchCommonListView.this.getContext(), this.val$hash);
        case 4:
          return new SearchItemEpisodeView(SearchCommonListView.this.getContext(), this.val$hash);
        case 1:
        }
        return new SearchItemShowView(SearchCommonListView.this.getContext(), this.val$hash);
      }
    };
    this.mAdapter = new CustomizedAdapter(new ArrayList(), this.mFactory);
    this.mEmptyView = new EmptyTipsView(paramContext, 8);
    addView(this.mEmptyView);
    this.mListView = new ListView(paramContext);
    this.mListView.setEmptyView(this.mEmptyView);
    this.mListView.setCacheColorHint(0);
    this.mListView.setSelector(new ColorDrawable(0));
    this.mListView.setDivider(null);
    this.mListView.setAdapter(this.mAdapter);
    addView(this.mListView);
    this.mListView.setOnScrollListener(new AbsListView.OnScrollListener()
    {
      public void onScroll(AbsListView paramAnonymousAbsListView, int paramAnonymousInt1, int paramAnonymousInt2, int paramAnonymousInt3)
      {
      }

      public void onScrollStateChanged(AbsListView paramAnonymousAbsListView, int paramAnonymousInt)
      {
        if ((paramAnonymousInt == 1) && (SearchCommonListView.this.mDelegate != null))
          SearchCommonListView.this.mDelegate.closeIfNeed();
      }
    });
  }

  public void close(boolean paramBoolean)
  {
    BitmapResourceCache.getInstance().clearResourceCacheOfOne(this, 0);
    this.mEmptyView.close(paramBoolean);
    super.close(paramBoolean);
  }

  public List<SearchItemNode> getResultList()
  {
    switch (this.mType)
    {
    default:
      return InfoManager.getInstance().root().mSearchNode.getResult(4);
    case 0:
      return InfoManager.getInstance().root().mSearchNode.getResult(0);
    case 3:
      return InfoManager.getInstance().root().mSearchNode.getResult(3);
    case 1:
      return InfoManager.getInstance().root().mSearchNode.getResult(1);
    case 2:
    }
    return InfoManager.getInstance().root().mSearchNode.getResult(2);
  }

  protected void onLayout(boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    this.standardLayout.layoutView(this.mListView);
    this.standardLayout.layoutView(this.mEmptyView);
  }

  protected void onMeasure(int paramInt1, int paramInt2)
  {
    this.standardLayout.scaleToBounds(View.MeasureSpec.getSize(paramInt1), View.MeasureSpec.getSize(paramInt2));
    this.standardLayout.measureView(this.mListView);
    this.standardLayout.measureView(this.mEmptyView);
    setMeasuredDimension(this.standardLayout.width, this.standardLayout.height);
  }

  protected void setInputStateDelegate(InputMethodUtil.InputStateDelegate paramInputStateDelegate)
  {
    this.mDelegate = paramInputStateDelegate;
  }

  public void update(String paramString, Object paramObject)
  {
    if (paramString.equalsIgnoreCase("setData"))
    {
      this.mEmptyView.setTitle(EmptyTipUtil.getTitle(8));
      this.mEmptyView.setContent(EmptyTipUtil.getContent(8));
      localList = getResultList();
      this.mAdapter.setData(ListUtils.convertToObjectList(localList));
    }
    while (!paramString.equalsIgnoreCase("loading"))
    {
      List localList;
      return;
    }
    this.mEmptyView.setTitle("正在搜索");
    this.mEmptyView.setContent(null);
    this.mAdapter.setData(null);
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.qtradio.view.search.SearchCommonListView
 * JD-Core Version:    0.6.2
 */