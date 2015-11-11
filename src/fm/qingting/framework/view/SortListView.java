package fm.qingting.framework.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewConfiguration;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.ImageView;
import fm.qingting.framework.adapter.SortAdapter;
import java.util.List;

public class SortListView extends ListViewImpl
{
  public static final String EMPTY_DATA = "empty_data";
  private Bitmap mDragBitmap;
  private DragListener mDragListener;
  private int mDragPointX;
  private int mDragPointY;
  private int mDragPos;
  private ImageView mDragView;
  private DropListener mDropListener;
  private Object mFloatingData;
  private int mHeight;
  private int mLastEmptyPos = -1;
  private int mLowerBound;
  private int mSrcDragPos;
  private Rect mTempRect = new Rect();
  private final int mTouchSlop;
  private Drawable mTrashcan;
  private int mUpperBound;
  private WindowManager mWindowManager;
  private WindowManager.LayoutParams mWindowParams;
  private int mXOffset;
  private int mYOffset;
  private final ViewLayout standardLayout = ViewLayout.createViewLayoutWithBoundsLT(480, 800, 480, 800, 0, 0, ViewLayout.FILL);

  public SortListView(Context paramContext)
  {
    super(paramContext);
    this.mTouchSlop = ViewConfiguration.get(paramContext).getScaledTouchSlop();
  }

  private void adjustScrollBounds(int paramInt)
  {
    if (paramInt >= this.mHeight / 3)
      this.mUpperBound = (this.mHeight / 3);
    if (paramInt <= 2 * this.mHeight / 3)
      this.mLowerBound = (2 * this.mHeight / 3);
  }

  private void doExpansion()
  {
    int i = this.mDragPos - getFirstVisiblePosition();
    if (this.mDragPos > this.mSrcDragPos)
      i++;
    int j = getHeaderViewsCount();
    View localView1 = getChildAt(this.mSrcDragPos - getFirstVisiblePosition());
    int k = 0;
    View localView2 = getChildAt(k);
    if (localView2 == null)
      return;
    int m;
    if ((this.mDragPos < j) && (k == j))
    {
      boolean bool = localView2.equals(localView1);
      m = 0;
      if (bool)
        m = 4;
    }
    while (true)
    {
      localView2.setVisibility(m);
      k++;
      break;
      if (localView2.equals(localView1))
      {
        if (this.mDragPos != this.mSrcDragPos)
        {
          int i3 = getPositionForView(localView2);
          int i4 = -1 + getCount();
          m = 0;
          if (i3 != i4);
        }
        else
        {
          m = 4;
        }
      }
      else
      {
        m = 0;
        if (k == i)
        {
          int n = this.mDragPos;
          m = 0;
          if (n >= j)
          {
            int i1 = this.mDragPos;
            int i2 = -1 + getCount();
            m = 0;
            if (i1 < i2)
              m = 4;
          }
        }
      }
    }
  }

  private void dragView(int paramInt1, int paramInt2)
  {
    this.mWindowParams.x = (paramInt1 - this.mDragPointX + this.mXOffset);
    this.mWindowParams.y = (paramInt2 - this.mDragPointY + this.mYOffset);
    this.mWindowManager.updateViewLayout(this.mDragView, this.mWindowParams);
    int i;
    if (this.mTrashcan != null)
    {
      i = this.mDragView.getWidth();
      if (paramInt2 > 3 * getHeight() / 4)
        this.mTrashcan.setLevel(2);
    }
    else
    {
      return;
    }
    if ((i > 0) && (paramInt1 > i / 4))
    {
      this.mTrashcan.setLevel(1);
      return;
    }
    this.mTrashcan.setLevel(0);
  }

  private int getItemForPosition(int paramInt)
  {
    int i = paramInt - this.mDragPointY;
    int j = myPointToPosition(0, i);
    if ((j < 0) && (i < 0))
      j = 0;
    return j;
  }

  private void log(String paramString)
  {
    Log.e("draganddrop", paramString);
  }

  private int myPointToPosition(int paramInt1, int paramInt2)
  {
    if (paramInt2 < 0)
    {
      int j = myPointToPosition(paramInt1, paramInt2 + 60);
      if (j > 0)
        return j - 1;
    }
    Rect localRect = this.mTempRect;
    for (int i = -1 + getChildCount(); ; i--)
    {
      if (i < 0)
        return -1;
      getChildAt(i).getHitRect(localRect);
      if (localRect.contains(paramInt1, paramInt2))
        return i + getFirstVisiblePosition();
    }
  }

  private void startDragging(Bitmap paramBitmap, int paramInt1, int paramInt2)
  {
    stopDragging();
    this.mWindowParams = new WindowManager.LayoutParams();
    this.mWindowParams.gravity = 51;
    this.mWindowParams.x = (paramInt1 - this.mDragPointX + this.mXOffset);
    this.mWindowParams.y = (paramInt2 - this.mDragPointY + this.mYOffset);
    this.mWindowParams.height = -2;
    this.mWindowParams.width = -2;
    this.mWindowParams.flags = 920;
    this.mWindowParams.format = -3;
    this.mWindowParams.windowAnimations = 0;
    Context localContext = getContext();
    ImageView localImageView = new ImageView(localContext);
    localImageView.setPadding(0, 0, 0, 0);
    localImageView.setImageBitmap(paramBitmap);
    this.mDragBitmap = paramBitmap;
    this.mWindowManager = ((WindowManager)localContext.getSystemService("window"));
    this.mWindowManager.addView(localImageView, this.mWindowParams);
    this.mDragView = localImageView;
  }

  private void stopDragging()
  {
    if (this.mDragView != null)
    {
      this.mDragView.setVisibility(8);
      ((WindowManager)getContext().getSystemService("window")).removeView(this.mDragView);
      this.mDragView.setImageDrawable(null);
      this.mDragView = null;
    }
    if (this.mDragBitmap != null)
    {
      this.mDragBitmap.recycle();
      this.mDragBitmap = null;
    }
    if (this.mTrashcan != null)
      this.mTrashcan.setLevel(0);
  }

  private void unExpandViews(boolean paramBoolean)
  {
    int i = 0;
    while (true)
    {
      Object localObject = getChildAt(i);
      if (localObject == null)
        if (paramBoolean)
        {
          int j = getFirstVisiblePosition();
          int k = getChildAt(0).getTop();
          setAdapter(getAdapter());
          setSelectionFromTop(j, k);
        }
      try
      {
        layoutChildren();
        View localView = getChildAt(i);
        localObject = localView;
        label62: if (localObject == null)
          return;
        ((View)localObject).setVisibility(0);
        i++;
      }
      catch (IllegalStateException localIllegalStateException)
      {
        break label62;
      }
    }
  }

  public void exchangeData(int paramInt1, int paramInt2)
  {
    Log.e("FMtest", "old:" + paramInt1 + "   new:" + paramInt2);
    if ((paramInt1 < 0) && (paramInt2 < 0));
    SortAdapter localSortAdapter;
    List localList;
    do
    {
      do
      {
        return;
        localSortAdapter = (SortAdapter)getAdapter();
      }
      while (localSortAdapter == null);
      localList = localSortAdapter.getData();
    }
    while (localList == null);
    if (paramInt1 < 0)
      if ((localList.size() > paramInt2) && (paramInt2 >= 0))
      {
        localSortAdapter.setDragPosition(paramInt2);
        localSortAdapter.setEmptyPosition(paramInt2);
      }
    while (true)
    {
      localSortAdapter.notifyDataSetChanged();
      return;
      if (paramInt2 < 0)
      {
        if ((localList.size() > paramInt1) && (paramInt1 >= 0))
        {
          localSortAdapter.setDragPosition(-1);
          localSortAdapter.setEmptyPosition(-1);
        }
      }
      else if ((localList.size() > paramInt1) && (localList.size() > paramInt2))
        localSortAdapter.setEmptyPosition(paramInt2);
    }
  }

  public boolean onInterceptTouchEvent(MotionEvent paramMotionEvent)
  {
    switch (paramMotionEvent.getAction())
    {
    default:
    case 0:
    }
    int i;
    int j;
    int k;
    do
    {
      return true;
      i = (int)paramMotionEvent.getX();
      j = (int)paramMotionEvent.getY();
      k = pointToPosition(i, j);
    }
    while ((k == -1) || (k < getHeaderViewsCount()));
    IView localIView = (IView)getChildAt(k - getFirstVisiblePosition());
    this.mDragPointX = (i - localIView.getView().getLeft());
    this.mDragPointY = (j - localIView.getView().getTop());
    this.mXOffset = ((int)paramMotionEvent.getRawX() - i);
    this.mYOffset = ((int)paramMotionEvent.getRawY() - j);
    if (i < 565)
    {
      localIView.getView().setDrawingCacheEnabled(true);
      startDragging(Bitmap.createBitmap(localIView.getView().getDrawingCache()), i, j);
      this.mDragPos = k;
      this.mSrcDragPos = this.mDragPos;
      this.mHeight = getHeight();
      int m = this.mTouchSlop;
      this.mUpperBound = Math.min(j - m, this.mHeight / 3);
      this.mLowerBound = Math.max(j + m, 2 * this.mHeight / 3);
      exchangeData(-1, this.mDragPos);
      this.mLastEmptyPos = k;
      return false;
    }
    stopDragging();
    return true;
  }

  protected void onMeasure(int paramInt1, int paramInt2)
  {
    this.standardLayout.scaleToBounds(View.MeasureSpec.getSize(paramInt1), View.MeasureSpec.getSize(paramInt2));
    super.onMeasure(paramInt1, paramInt2);
  }

  public boolean onTouchEvent(MotionEvent paramMotionEvent)
  {
    if (this.mDragView != null)
    {
      int i = paramMotionEvent.getAction();
      switch (i)
      {
      default:
      case 1:
      case 3:
      case 0:
      case 2:
      }
      int k;
      int m;
      do
      {
        while (true)
        {
          return true;
          Rect localRect = this.mTempRect;
          this.mDragView.getDrawingRect(localRect);
          stopDragging();
          if ((this.mDropListener != null) && (this.mDragPos >= 0) && (this.mDragPos < getCount()))
            this.mDropListener.drop(this.mSrcDragPos, this.mDragPos);
          exchangeData(this.mLastEmptyPos, -1);
          this.mLastEmptyPos = -1;
        }
        int j = (int)paramMotionEvent.getX();
        k = (int)paramMotionEvent.getY();
        dragView(j, k);
        m = getItemForPosition(k);
      }
      while (m < 0);
      if ((i == 0) || (m != this.mLastEmptyPos))
      {
        if (this.mDragListener != null)
          this.mDragListener.drag(this.mDragPos, m);
        this.mDragPos = m;
        exchangeData(this.mLastEmptyPos, m);
        this.mLastEmptyPos = m;
      }
      adjustScrollBounds(k);
      int i1;
      if (k > this.mLowerBound)
        if (getLastVisiblePosition() < -1 + getCount())
          if (k > (this.mHeight + this.mLowerBound) / 2)
            i1 = 16;
      label262: label349: label354: 
      while (true)
      {
        if (i1 != 0)
        {
          smoothScrollBy(i1, 30);
          break;
          i1 = 4;
          continue;
          i1 = 1;
          continue;
          int n = this.mUpperBound;
          i1 = 0;
          if (k >= n)
            continue;
          if (k >= this.mUpperBound / 2)
            break label349;
        }
        for (i1 = -16; ; i1 = -4)
        {
          if ((getFirstVisiblePosition() != 0) || (getChildAt(0).getTop() < getPaddingTop()))
            break label354;
          i1 = 0;
          break label262;
          break;
        }
      }
    }
    return super.onTouchEvent(paramMotionEvent);
  }

  public void setDragListener(DragListener paramDragListener)
  {
    this.mDragListener = paramDragListener;
  }

  public void setDropListener(DropListener paramDropListener)
  {
    this.mDropListener = paramDropListener;
  }

  public static abstract interface DragListener
  {
    public abstract void drag(int paramInt1, int paramInt2);
  }

  public static abstract interface DropListener
  {
    public abstract void drop(int paramInt1, int paramInt2);
  }

  public static abstract interface RemoveListener
  {
    public abstract void remove(int paramInt);
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.framework.view.SortListView
 * JD-Core Version:    0.6.2
 */