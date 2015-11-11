package fm.qingting.qtradio.view;

import android.content.Context;
import android.os.Handler;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import fm.qingting.framework.view.ListViewImpl;
import fm.qingting.utils.RecommendStatisticsUtil;

public class LoadMoreListView extends ListViewImpl
{
  private final int VALID_TIME = 1000;
  private OnCrossTopListener mCrossListener;
  private int mCrossScope = 0;
  private Handler mDelayHandler = new Handler();
  private Runnable mDelayRunnable = new Runnable()
  {
    public void run()
    {
      LoadMoreListView.this.addValidData();
    }
  };
  private boolean mEnableStatistics = false;
  private int mFirstItem = -1;
  private LoadMoreFootView mFooterView;
  private int mHeaderCount = -1;
  private boolean mIsLoadingMore;
  private int mLastItem = 0;
  private int mLastRangeEnd = -1;
  private int mLastRangeStart = -1;
  private onLoadMoreListener mLoadMoreListener;
  private int mPreloadOffset = 0;
  private int mRecommendCategoryId;
  private OnScrollStateListener mStateListener;
  private int mTotalCnt = 0;
  private int mVisibleItemCnt = 0;

  public LoadMoreListView(Context paramContext)
  {
    super(paramContext);
    this.mFooterView = new LoadMoreFootView(paramContext);
    addFooterView(this.mFooterView);
    setOnScrollListener(new AbsListView.OnScrollListener()
    {
      public void onScroll(AbsListView paramAnonymousAbsListView, int paramAnonymousInt1, int paramAnonymousInt2, int paramAnonymousInt3)
      {
        if (LoadMoreListView.this.mCrossListener != null)
        {
          if (paramAnonymousInt1 != 0)
            break label146;
          View localView2 = paramAnonymousAbsListView.getChildAt(0);
          if (localView2 != null)
          {
            int i = localView2.getTop();
            if ((i <= 0) && (i >= -LoadMoreListView.this.mCrossScope))
              LoadMoreListView.this.mCrossListener.onCrossTop(i);
          }
        }
        if (LoadMoreListView.this.mEnableStatistics)
        {
          LoadMoreListView.access$502(LoadMoreListView.this, paramAnonymousInt3);
          LoadMoreListView.access$602(LoadMoreListView.this, paramAnonymousInt2);
          if ((LoadMoreListView.this.mFirstItem != paramAnonymousInt1) || (LoadMoreListView.this.mLastItem != paramAnonymousInt1 + LoadMoreListView.this.mVisibleItemCnt))
            LoadMoreListView.this.startDelayTimer(paramAnonymousInt1);
        }
        if (paramAnonymousInt2 == paramAnonymousInt3)
          LoadMoreListView.this.hideFooterView();
        label146: 
        while ((LoadMoreListView.this.mIsLoadingMore) || (paramAnonymousInt1 + paramAnonymousInt2 < paramAnonymousInt3 - LoadMoreListView.this.mPreloadOffset))
        {
          return;
          View localView1 = paramAnonymousAbsListView.getChildAt(0);
          if ((localView1 == null) || (localView1.getTop() < -LoadMoreListView.this.mCrossScope))
            break;
          LoadMoreListView.this.mCrossListener.onCrossTop(-LoadMoreListView.this.mCrossScope);
          break;
        }
        LoadMoreListView.this.dispatchLoadMoreEvent(paramAnonymousInt3 - 1);
      }

      public void onScrollStateChanged(AbsListView paramAnonymousAbsListView, int paramAnonymousInt)
      {
        if (LoadMoreListView.this.mStateListener == null)
          return;
        switch (paramAnonymousInt)
        {
        default:
          return;
        case 0:
          LoadMoreListView.this.mStateListener.onStateChange(1);
          return;
        case 1:
        }
        LoadMoreListView.this.mStateListener.onStateChange(0);
      }
    });
  }

  private void addValidData()
  {
    if (this.mHeaderCount < 0)
      this.mHeaderCount = getHeaderViewsCount();
    int i;
    int j;
    label48: int k;
    if (this.mFirstItem == 0)
    {
      i = this.mFirstItem;
      if (this.mFirstItem + this.mVisibleItemCnt != this.mTotalCnt)
        break label118;
      j = this.mTotalCnt;
      k = i;
      label50: if (k >= j)
        break label139;
      if ((k < this.mLastRangeStart) || (k > this.mLastRangeEnd))
        break label133;
    }
    label133: for (int m = 1; ; m = 0)
    {
      if ((m == 0) && (this.mEnableStatistics))
        RecommendStatisticsUtil.INSTANCE.addValidData(k - this.mHeaderCount, this.mRecommendCategoryId);
      k++;
      break label50;
      i = 1 + this.mFirstItem;
      break;
      label118: j = -1 + (this.mFirstItem + this.mVisibleItemCnt);
      break label48;
    }
    label139: this.mLastRangeStart = i;
    this.mLastRangeEnd = (j - 1);
  }

  private void dispatchLoadMoreEvent(int paramInt)
  {
    if (this.mLoadMoreListener != null)
    {
      this.mIsLoadingMore = true;
      showLoadState();
      this.mLoadMoreListener.onLoadMore(paramInt);
    }
  }

  private void showLoadState()
  {
    this.mIsLoadingMore = true;
    this.mFooterView.showLoad();
  }

  private void startDelayTimer(int paramInt)
  {
    this.mFirstItem = paramInt;
    this.mLastItem = (this.mFirstItem + this.mVisibleItemCnt);
    this.mDelayHandler.removeCallbacks(this.mDelayRunnable);
    this.mDelayHandler.postDelayed(this.mDelayRunnable, 1000L);
  }

  private void stopDelayTimer()
  {
    this.mDelayHandler.removeCallbacks(this.mDelayRunnable);
  }

  public void cancelLoadState()
  {
    this.mIsLoadingMore = false;
    hideFooterView();
  }

  public void enableStatistics(int paramInt)
  {
    this.mEnableStatistics = true;
    this.mRecommendCategoryId = paramInt;
  }

  public void hideFooterView()
  {
    this.mFooterView.hideLoad();
  }

  public void setCrossScope(int paramInt)
  {
    this.mCrossScope = paramInt;
  }

  public void setOnCrossTopListener(OnCrossTopListener paramOnCrossTopListener)
  {
    this.mCrossListener = paramOnCrossTopListener;
  }

  public void setOnLoadMoreListener(onLoadMoreListener paramonLoadMoreListener)
  {
    this.mLoadMoreListener = paramonLoadMoreListener;
  }

  public void setOnStateChangeListener(OnScrollStateListener paramOnScrollStateListener)
  {
    this.mStateListener = paramOnScrollStateListener;
  }

  public void setPreloadOffset(int paramInt)
  {
    this.mPreloadOffset = paramInt;
  }

  public static abstract interface OnCrossTopListener
  {
    public abstract void onCrossTop(int paramInt);
  }

  public static abstract interface OnScrollStateListener
  {
    public static final int START = 0;
    public static final int STOP = 1;

    public abstract void onStateChange(int paramInt);
  }

  public static abstract interface onLoadMoreListener
  {
    public abstract void onLoadMore(int paramInt);
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.qtradio.view.LoadMoreListView
 * JD-Core Version:    0.6.2
 */