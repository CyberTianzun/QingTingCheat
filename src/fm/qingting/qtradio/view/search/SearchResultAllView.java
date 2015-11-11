package fm.qingting.qtradio.view.search;

import android.content.Context;
import android.view.View.MeasureSpec;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ListView;
import fm.qingting.framework.view.IView;
import fm.qingting.framework.view.ViewGroupViewImpl;
import fm.qingting.framework.view.ViewLayout;
import fm.qingting.qtradio.model.InfoManager;
import fm.qingting.qtradio.model.RootNode;
import fm.qingting.qtradio.search.SearchNode;
import fm.qingting.qtradio.view.moreContentView.CustomSectionView;
import fm.qingting.qtradio.view.personalcenter.mydownload.EmptyTipUtil;
import fm.qingting.qtradio.view.personalcenter.mydownload.EmptyTipsView;
import fm.qingting.utils.InputMethodUtil.InputStateDelegate;
import java.util.ArrayList;
import java.util.List;

public class SearchResultAllView extends ViewGroupViewImpl
{
  private SearchSectionAdapter mAdapter = new SearchSectionAdapter(null, new ISearchSectionFactory()
  {
    public IView createView(int paramAnonymousInt)
    {
      switch (paramAnonymousInt)
      {
      default:
        return null;
      case 0:
        return new CustomSectionView(SearchResultAllView.this.getContext());
      case 1:
        return new SearchItemShowView(SearchResultAllView.this.getContext(), this.val$hash);
      case 2:
        return new SearchItemPodcasterView(SearchResultAllView.this.getContext(), this.val$hash);
      case 3:
        return new SearchItemChannelView(SearchResultAllView.this.getContext(), this.val$hash);
      case 4:
        return new SearchItemEpisodeView(SearchResultAllView.this.getContext(), this.val$hash);
      case 5:
        return new SearchTagView(SearchResultAllView.this.getContext(), this.val$hash);
      case 6:
      }
      return new SearchViewMoreView(SearchResultAllView.this.getContext());
    }
  });
  private InputMethodUtil.InputStateDelegate mDelegate;
  private EmptyTipsView mEmptyView;
  private ListView mListView;
  private ViewLayout standardLayout = ViewLayout.createViewLayoutWithBoundsLT(720, 1200, 720, 1200, 0, 0, ViewLayout.FILL);

  public SearchResultAllView(Context paramContext)
  {
    super(paramContext);
    this.mListView = new ListView(paramContext);
    this.mListView.setAdapter(this.mAdapter);
    this.mListView.setDivider(null);
    this.mListView.setSelector(17170445);
    addView(this.mListView);
    this.mEmptyView = new EmptyTipsView(paramContext, 13);
    addView(this.mEmptyView);
    this.mListView.setEmptyView(this.mEmptyView);
    this.mListView.setOnScrollListener(new AbsListView.OnScrollListener()
    {
      public void onScroll(AbsListView paramAnonymousAbsListView, int paramAnonymousInt1, int paramAnonymousInt2, int paramAnonymousInt3)
      {
      }

      public void onScrollStateChanged(AbsListView paramAnonymousAbsListView, int paramAnonymousInt)
      {
        if ((paramAnonymousInt == 1) && (SearchResultAllView.this.mDelegate != null))
          SearchResultAllView.this.mDelegate.closeIfNeed();
      }
    });
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
      this.mEmptyView.setTitle(EmptyTipUtil.getTitle(13));
      this.mEmptyView.setContent(EmptyTipUtil.getContent(13));
      if (!InfoManager.getInstance().root().mSearchNode.hasResult())
        this.mAdapter.setData(null);
    }
    while (!paramString.equalsIgnoreCase("loading"))
    {
      return;
      SearchNode localSearchNode = InfoManager.getInstance().root().mSearchNode;
      ArrayList localArrayList1 = new ArrayList();
      ArrayList localArrayList2 = localSearchNode.getSortedTypesByScore();
      for (int i = 0; i < localArrayList2.size(); i++)
      {
        int j = ((Integer)localArrayList2.get(i)).intValue();
        List localList = localSearchNode.getResult(j);
        if ((localList != null) && (localList.size() > 0))
        {
          localArrayList1.add(SearchSectionItem.build(0, null));
          localArrayList1.add(SearchSectionItem.build(5, SearchNode.TABNAMES[j]));
          int k = Math.min(3, localList.size());
          for (int m = 0; m < k; m++)
            localArrayList1.add(SearchSectionItem.build(j, localList.get(m)));
          if (localList.size() > 3)
            localArrayList1.add(SearchSectionItem.build(6, Integer.valueOf(j)));
        }
      }
      localArrayList1.add(SearchSectionItem.build(0, null));
      this.mAdapter.setData(localArrayList1);
      return;
    }
    this.mEmptyView.setTitle("正在搜索");
    this.mEmptyView.setContent(null);
    this.mAdapter.setData(null);
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.qtradio.view.search.SearchResultAllView
 * JD-Core Version:    0.6.2
 */