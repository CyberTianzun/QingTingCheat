package fm.qingting.qtradio.view.pinnedsection;

import android.content.Context;
import android.content.res.Resources;
import android.database.DataSetObserver;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.GradientDrawable.Orientation;
import android.os.Parcelable;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewConfiguration;
import android.widget.AbsListView;
import android.widget.AbsListView.LayoutParams;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.HeaderViewListAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SectionIndexer;

public class PinnedSectionListView extends ListView
{
  private final DataSetObserver mDataSetObserver = new DataSetObserver()
  {
    public void onChanged()
    {
      PinnedSectionListView.this.recreatePinnedShadow();
    }

    public void onInvalidated()
    {
      PinnedSectionListView.this.recreatePinnedShadow();
    }
  };
  AbsListView.OnScrollListener mDelegateOnScrollListener;
  private MotionEvent mDownEvent;
  private final AbsListView.OnScrollListener mOnScrollListener = new AbsListView.OnScrollListener()
  {
    public void onScroll(AbsListView paramAnonymousAbsListView, int paramAnonymousInt1, int paramAnonymousInt2, int paramAnonymousInt3)
    {
      if (PinnedSectionListView.this.mDelegateOnScrollListener != null)
        PinnedSectionListView.this.mDelegateOnScrollListener.onScroll(paramAnonymousAbsListView, paramAnonymousInt1, paramAnonymousInt2, paramAnonymousInt3);
      ListAdapter localListAdapter = PinnedSectionListView.this.getAdapter();
      if ((localListAdapter == null) || (paramAnonymousInt2 == 0))
        return;
      if (PinnedSectionListView.isItemViewTypePinned(localListAdapter, localListAdapter.getItemViewType(paramAnonymousInt1)))
      {
        if (PinnedSectionListView.this.getChildAt(0).getTop() == PinnedSectionListView.this.getPaddingTop())
        {
          PinnedSectionListView.this.destroyPinnedShadow();
          return;
        }
        PinnedSectionListView.this.ensureShadowForPosition(paramAnonymousInt1, paramAnonymousInt1, paramAnonymousInt2);
        return;
      }
      int i = PinnedSectionListView.this.findCurrentSectionPosition(paramAnonymousInt1);
      if (i > -1)
      {
        PinnedSectionListView.this.ensureShadowForPosition(i, paramAnonymousInt1, paramAnonymousInt2);
        return;
      }
      PinnedSectionListView.this.destroyPinnedShadow();
    }

    public void onScrollStateChanged(AbsListView paramAnonymousAbsListView, int paramAnonymousInt)
    {
      if (PinnedSectionListView.this.mDelegateOnScrollListener != null)
        PinnedSectionListView.this.mDelegateOnScrollListener.onScrollStateChanged(paramAnonymousAbsListView, paramAnonymousInt);
    }
  };
  PinnedSection mPinnedSection;
  PinnedSection mRecycleSection;
  private int mSectionsDistanceY;
  private GradientDrawable mShadowDrawable;
  private int mShadowHeight;
  private final PointF mTouchPoint = new PointF();
  private final Rect mTouchRect = new Rect();
  private int mTouchSlop;
  private View mTouchTarget;
  int mTranslateY;

  public PinnedSectionListView(Context paramContext)
  {
    super(paramContext);
    initView();
  }

  private void clearTouchTarget()
  {
    this.mTouchTarget = null;
    if (this.mDownEvent != null)
    {
      this.mDownEvent.recycle();
      this.mDownEvent = null;
    }
  }

  private void initView()
  {
    setOnScrollListener(this.mOnScrollListener);
    this.mTouchSlop = ViewConfiguration.get(getContext()).getScaledTouchSlop();
    initShadow(true);
  }

  public static boolean isItemViewTypePinned(ListAdapter paramListAdapter, int paramInt)
  {
    if ((paramListAdapter instanceof HeaderViewListAdapter));
    for (ListAdapter localListAdapter = ((HeaderViewListAdapter)paramListAdapter).getWrappedAdapter(); ; localListAdapter = paramListAdapter)
      return ((PinnedSectionListAdapter)localListAdapter).isItemViewTypePinned(paramInt);
  }

  private boolean isPinnedViewTouched(View paramView, float paramFloat1, float paramFloat2)
  {
    paramView.getHitRect(this.mTouchRect);
    Rect localRect1 = this.mTouchRect;
    localRect1.top += this.mTranslateY;
    Rect localRect2 = this.mTouchRect;
    localRect2.bottom += this.mTranslateY + getPaddingTop();
    Rect localRect3 = this.mTouchRect;
    localRect3.left += getPaddingLeft();
    Rect localRect4 = this.mTouchRect;
    localRect4.right -= getPaddingRight();
    return this.mTouchRect.contains((int)paramFloat1, (int)paramFloat2);
  }

  private boolean performPinnedItemClick()
  {
    if (this.mPinnedSection == null)
      return false;
    AdapterView.OnItemClickListener localOnItemClickListener = getOnItemClickListener();
    if (localOnItemClickListener != null)
    {
      View localView = this.mPinnedSection.view;
      playSoundEffect(0);
      if (localView != null)
        localView.sendAccessibilityEvent(1);
      localOnItemClickListener.onItemClick(this, localView, this.mPinnedSection.position, this.mPinnedSection.id);
      return true;
    }
    return false;
  }

  void createPinnedShadow(int paramInt)
  {
    PinnedSection localPinnedSection1 = this.mRecycleSection;
    this.mRecycleSection = null;
    if (localPinnedSection1 == null);
    for (PinnedSection localPinnedSection2 = new PinnedSection(); ; localPinnedSection2 = localPinnedSection1)
    {
      View localView = getAdapter().getView(paramInt, localPinnedSection2.view, this);
      AbsListView.LayoutParams localLayoutParams = (AbsListView.LayoutParams)localView.getLayoutParams();
      if (localLayoutParams == null)
        localLayoutParams = new AbsListView.LayoutParams(-1, -2);
      int i = View.MeasureSpec.getMode(localLayoutParams.height);
      int j = View.MeasureSpec.getSize(localLayoutParams.height);
      if (i == 0);
      for (int k = 1073741824; ; k = i)
      {
        int m = getHeight() - getListPaddingTop() - getListPaddingBottom();
        if (j > m);
        while (true)
        {
          localView.measure(View.MeasureSpec.makeMeasureSpec(getWidth() - getListPaddingLeft() - getListPaddingRight(), 1073741824), View.MeasureSpec.makeMeasureSpec(m, k));
          localView.layout(0, 0, localView.getMeasuredWidth(), localView.getMeasuredHeight());
          this.mTranslateY = 0;
          localPinnedSection2.view = localView;
          localPinnedSection2.position = paramInt;
          localPinnedSection2.id = getAdapter().getItemId(paramInt);
          this.mPinnedSection = localPinnedSection2;
          return;
          m = j;
        }
      }
    }
  }

  void destroyPinnedShadow()
  {
    if (this.mPinnedSection != null)
    {
      this.mRecycleSection = this.mPinnedSection;
      this.mPinnedSection = null;
    }
  }

  protected void dispatchDraw(Canvas paramCanvas)
  {
    super.dispatchDraw(paramCanvas);
    int i;
    int j;
    View localView;
    int k;
    if (this.mPinnedSection != null)
    {
      i = getListPaddingLeft();
      j = getListPaddingTop();
      localView = this.mPinnedSection.view;
      paramCanvas.save();
      k = localView.getHeight();
      if (this.mShadowDrawable != null)
        break label187;
    }
    label187: for (int m = 0; ; m = Math.min(this.mShadowHeight, this.mSectionsDistanceY))
    {
      int n = m + k;
      paramCanvas.clipRect(i, j, i + localView.getWidth(), n + j);
      paramCanvas.translate(i, j + this.mTranslateY);
      drawChild(paramCanvas, this.mPinnedSection.view, getDrawingTime());
      if ((this.mShadowDrawable != null) && (this.mSectionsDistanceY > 0))
      {
        this.mShadowDrawable.setBounds(this.mPinnedSection.view.getLeft(), this.mPinnedSection.view.getBottom(), this.mPinnedSection.view.getRight(), this.mPinnedSection.view.getBottom() + this.mShadowHeight);
        this.mShadowDrawable.draw(paramCanvas);
      }
      paramCanvas.restore();
      return;
    }
  }

  public boolean dispatchTouchEvent(MotionEvent paramMotionEvent)
  {
    float f1 = paramMotionEvent.getX();
    float f2 = paramMotionEvent.getY();
    int i = paramMotionEvent.getAction();
    if ((i == 0) && (this.mTouchTarget == null) && (this.mPinnedSection != null) && (isPinnedViewTouched(this.mPinnedSection.view, f1, f2)))
    {
      this.mTouchTarget = this.mPinnedSection.view;
      this.mTouchPoint.x = f1;
      this.mTouchPoint.y = f2;
      this.mDownEvent = MotionEvent.obtain(paramMotionEvent);
    }
    if (this.mTouchTarget != null)
    {
      if (isPinnedViewTouched(this.mTouchTarget, f1, f2))
        this.mTouchTarget.dispatchTouchEvent(paramMotionEvent);
      if (i == 1)
      {
        super.dispatchTouchEvent(paramMotionEvent);
        performPinnedItemClick();
        clearTouchTarget();
      }
      do
      {
        return true;
        if (i == 3)
        {
          clearTouchTarget();
          return true;
        }
      }
      while ((i != 2) || (Math.abs(f2 - this.mTouchPoint.y) <= this.mTouchSlop));
      MotionEvent localMotionEvent = MotionEvent.obtain(paramMotionEvent);
      localMotionEvent.setAction(3);
      this.mTouchTarget.dispatchTouchEvent(localMotionEvent);
      localMotionEvent.recycle();
      super.dispatchTouchEvent(this.mDownEvent);
      super.dispatchTouchEvent(paramMotionEvent);
      clearTouchTarget();
      return true;
    }
    return super.dispatchTouchEvent(paramMotionEvent);
  }

  void ensureShadowForPosition(int paramInt1, int paramInt2, int paramInt3)
  {
    if (paramInt3 < 2)
      destroyPinnedShadow();
    int i;
    do
    {
      return;
      if ((this.mPinnedSection != null) && (this.mPinnedSection.position != paramInt1))
        destroyPinnedShadow();
      if (this.mPinnedSection == null)
        createPinnedShadow(paramInt1);
      i = paramInt1 + 1;
    }
    while (i >= getCount());
    int j = findFirstVisibleSectionPosition(i, paramInt3 - (i - paramInt2));
    if (j > -1)
    {
      View localView = getChildAt(j - paramInt2);
      int k = this.mPinnedSection.view.getBottom() + getPaddingTop();
      this.mSectionsDistanceY = (localView.getTop() - k);
      if (this.mSectionsDistanceY < 0)
      {
        this.mTranslateY = this.mSectionsDistanceY;
        return;
      }
      this.mTranslateY = 0;
      return;
    }
    this.mTranslateY = 0;
    this.mSectionsDistanceY = 2147483647;
  }

  int findCurrentSectionPosition(int paramInt)
  {
    ListAdapter localListAdapter = getAdapter();
    if ((localListAdapter instanceof SectionIndexer))
    {
      SectionIndexer localSectionIndexer = (SectionIndexer)localListAdapter;
      i = localSectionIndexer.getPositionForSection(localSectionIndexer.getSectionForPosition(paramInt));
      if (isItemViewTypePinned(localListAdapter, localListAdapter.getItemViewType(i)))
        return i;
    }
    for (int i = paramInt; ; i--)
    {
      if (i < 0)
        break label76;
      if (isItemViewTypePinned(localListAdapter, localListAdapter.getItemViewType(i)))
        break;
    }
    label76: return -1;
  }

  int findFirstVisibleSectionPosition(int paramInt1, int paramInt2)
  {
    ListAdapter localListAdapter = getAdapter();
    for (int i = 0; i < paramInt2; i++)
    {
      int j = paramInt1 + i;
      if (isItemViewTypePinned(localListAdapter, localListAdapter.getItemViewType(j)))
        return j;
    }
    return -1;
  }

  public void initShadow(boolean paramBoolean)
  {
    if (paramBoolean)
      if (this.mShadowDrawable == null)
      {
        localOrientation = GradientDrawable.Orientation.TOP_BOTTOM;
        arrayOfInt = new int[3];
        arrayOfInt[0] = Color.parseColor("#ffa0a0a0");
        arrayOfInt[1] = Color.parseColor("#50a0a0a0");
        arrayOfInt[2] = Color.parseColor("#00a0a0a0");
        this.mShadowDrawable = new GradientDrawable(localOrientation, arrayOfInt);
        this.mShadowHeight = ((int)(8.0F * getResources().getDisplayMetrics().density));
      }
    while (this.mShadowDrawable == null)
    {
      GradientDrawable.Orientation localOrientation;
      int[] arrayOfInt;
      return;
    }
    this.mShadowDrawable = null;
    this.mShadowHeight = 0;
  }

  protected void onLayout(boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    super.onLayout(paramBoolean, paramInt1, paramInt2, paramInt3, paramInt4);
    if ((this.mPinnedSection != null) && (paramInt3 - paramInt1 - getPaddingLeft() - getPaddingRight() != this.mPinnedSection.view.getWidth()))
      recreatePinnedShadow();
  }

  public void onRestoreInstanceState(Parcelable paramParcelable)
  {
    super.onRestoreInstanceState(paramParcelable);
    post(new Runnable()
    {
      public void run()
      {
        PinnedSectionListView.this.recreatePinnedShadow();
      }
    });
  }

  void recreatePinnedShadow()
  {
    destroyPinnedShadow();
    ListAdapter localListAdapter = getAdapter();
    int i;
    int j;
    if ((localListAdapter != null) && (localListAdapter.getCount() > 0))
    {
      i = getFirstVisiblePosition();
      j = findCurrentSectionPosition(i);
      if (j != -1);
    }
    else
    {
      return;
    }
    ensureShadowForPosition(j, i, getLastVisiblePosition() - i);
  }

  public void setAdapter(ListAdapter paramListAdapter)
  {
    if (paramListAdapter != null)
    {
      if (!(paramListAdapter instanceof PinnedSectionListAdapter))
        throw new IllegalArgumentException("Does your adapter implement PinnedSectionListAdapter?");
      if (paramListAdapter.getViewTypeCount() < 2)
        throw new IllegalArgumentException("Does your adapter handle at least two types of views in getViewTypeCount() method: items and sections?");
    }
    ListAdapter localListAdapter = getAdapter();
    if (localListAdapter != null)
      localListAdapter.unregisterDataSetObserver(this.mDataSetObserver);
    if (paramListAdapter != null)
      paramListAdapter.registerDataSetObserver(this.mDataSetObserver);
    if (localListAdapter != paramListAdapter)
      destroyPinnedShadow();
    super.setAdapter(paramListAdapter);
  }

  public void setOnScrollListener(AbsListView.OnScrollListener paramOnScrollListener)
  {
    if (paramOnScrollListener == this.mOnScrollListener)
    {
      super.setOnScrollListener(paramOnScrollListener);
      return;
    }
    this.mDelegateOnScrollListener = paramOnScrollListener;
  }

  public void setShadowVisible(boolean paramBoolean)
  {
    initShadow(paramBoolean);
    if (this.mPinnedSection != null)
    {
      View localView = this.mPinnedSection.view;
      invalidate(localView.getLeft(), localView.getTop(), localView.getRight(), localView.getBottom() + this.mShadowHeight);
    }
  }

  static class PinnedSection
  {
    public long id;
    public int position;
    public View view;
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.qtradio.view.pinnedsection.PinnedSectionListView
 * JD-Core Version:    0.6.2
 */