package fm.qingting.qtradio.view.search;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
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
import fm.qingting.qtradio.model.InfoManager.DataExceptionStatus;
import fm.qingting.qtradio.model.InfoManager.ISubscribeEventListener;
import fm.qingting.qtradio.model.PlayingProgramInfoNode;
import fm.qingting.qtradio.model.RootNode;
import fm.qingting.qtradio.search.SearchItemNode;
import fm.qingting.qtradio.search.SearchNode;
import fm.qingting.qtradio.view.personalcenter.mydownload.EmptyTipUtil;
import fm.qingting.qtradio.view.personalcenter.mydownload.EmptyTipsView;
import fm.qingting.utils.InputMethodUtil.InputStateDelegate;
import fm.qingting.utils.ListUtils;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class SearchChannelListView extends ViewGroupViewImpl
  implements InfoManager.ISubscribeEventListener
{
  private CustomizedAdapter mAdapter = new CustomizedAdapter(new ArrayList(), this.mFactory);
  private InputMethodUtil.InputStateDelegate mDelegate;
  private EmptyTipsView mEmptyView;
  private IAdapterIViewFactory mFactory = new IAdapterIViewFactory()
  {
    public IView createView(int paramAnonymousInt)
    {
      return new SearchItemChannelView(SearchChannelListView.this.getContext(), this.val$hash);
    }
  };
  private ListView mListView;
  private final ViewLayout standardLayout = ViewLayout.createViewLayoutWithBoundsLT(720, 1200, 720, 1200, 0, 0, ViewLayout.FILL);

  public SearchChannelListView(Context paramContext)
  {
    super(paramContext);
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
        if ((paramAnonymousInt == 1) && (SearchChannelListView.this.mDelegate != null))
          SearchChannelListView.this.mDelegate.closeIfNeed();
      }
    });
  }

  private void invalidateCurrentPlayingProgram()
  {
    int i = this.mListView.getChildCount();
    for (int j = 0; j < i; j++)
    {
      View localView = this.mListView.getChildAt(j);
      if ((localView != null) && ((localView instanceof IView)))
        ((IView)localView).update("ip", null);
    }
  }

  private void loadPlayingProgram(List<SearchItemNode> paramList)
  {
    if (paramList == null)
      return;
    Object localObject1 = "";
    Iterator localIterator = paramList.iterator();
    label15: int i;
    Object localObject2;
    if (localIterator.hasNext())
    {
      i = ((SearchItemNode)localIterator.next()).channelId;
      if (InfoManager.getInstance().root().mPlayingProgramInfo.isExist(i))
        break label138;
      if (((String)localObject1).equalsIgnoreCase(""))
        localObject2 = (String)localObject1 + i;
    }
    while (true)
    {
      localObject1 = localObject2;
      break label15;
      localObject2 = (String)localObject1 + "," + i;
      continue;
      if (((String)localObject1).equalsIgnoreCase(""))
        break;
      InfoManager.getInstance().loadCurrentPlayingPrograms((String)localObject1, this);
      return;
      label138: localObject2 = localObject1;
    }
  }

  public void close(boolean paramBoolean)
  {
    BitmapResourceCache.getInstance().clearResourceCacheOfOne(this, 0);
    this.mEmptyView.close(paramBoolean);
    super.close(paramBoolean);
  }

  public List<SearchItemNode> getResultList()
  {
    return InfoManager.getInstance().root().mSearchNode.getResult(3);
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

  public void onNotification(String paramString)
  {
    if (paramString.equalsIgnoreCase("RCPPL"))
      invalidateCurrentPlayingProgram();
  }

  public void onRecvDataException(String paramString, InfoManager.DataExceptionStatus paramDataExceptionStatus)
  {
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
      loadPlayingProgram(localList);
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
 * Qualified Name:     fm.qingting.qtradio.view.search.SearchChannelListView
 * JD-Core Version:    0.6.2
 */