package fm.qingting.qtradio.view.virtualchannels;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewConfiguration;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.ImageView;
import android.widget.ListView;
import fm.qingting.framework.view.IView;
import fm.qingting.framework.view.ViewLayout;

public class DragAndDropListView extends ListView
{
  private final ViewLayout itemLayout = ViewLayout.createViewLayoutWithBoundsLT(480, 90, 480, 800, 0, 0, ViewLayout.LT | ViewLayout.SLT | ViewLayout.CW);
  private Bitmap mDragBitmap;
  private DragListener mDragListener;
  private int mDragPointX;
  private int mDragPointY;
  private int mDragPos;
  private ImageView mDragView;
  private DropListener mDropListener;
  private int mHeight;
  private int mItemHeightExpanded;
  private int mItemHeightHalf;
  private int mItemHeightNormal;
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

  public DragAndDropListView(Context paramContext)
  {
    super(paramContext);
    this.mTouchSlop = ViewConfiguration.get(paramContext).getScaledTouchSlop();
    getResources();
    this.mItemHeightNormal = 100;
    this.mItemHeightHalf = (this.mItemHeightNormal / 2);
    this.mItemHeightExpanded = 200;
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
    int m = this.mItemHeightNormal;
    int n;
    int i1;
    if ((this.mDragPos < j) && (k == j))
      if (localView2.equals(localView1))
      {
        n = m;
        i1 = 4;
      }
    while (true)
    {
      ViewGroup.LayoutParams localLayoutParams = localView2.getLayoutParams();
      localLayoutParams.height = n;
      localView2.setLayoutParams(localLayoutParams);
      localView2.setVisibility(i1);
      k++;
      break;
      n = this.mItemHeightExpanded;
      i1 = 0;
      continue;
      if (localView2.equals(localView1))
      {
        if ((this.mDragPos == this.mSrcDragPos) || (getPositionForView(localView2) == -1 + getCount()))
        {
          n = m;
          i1 = 4;
        }
        else
        {
          n = 1;
          i1 = 0;
        }
      }
      else if ((k == i) && (this.mDragPos >= j) && (this.mDragPos < -1 + getCount()))
      {
        n = this.mItemHeightExpanded;
        i1 = 0;
      }
      else
      {
        n = m;
        i1 = 0;
      }
    }
  }

  private void dragView(int paramInt1, int paramInt2)
  {
    this.mWindowParams.x = 0;
    this.mWindowParams.y = (paramInt2 - this.mDragPointY + this.mYOffset);
    this.mWindowManager.updateViewLayout(this.mDragView, this.mWindowParams);
    log("dragView:" + paramInt1 + "-" + paramInt2);
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
    int i = paramInt - this.mDragPointY - this.mItemHeightHalf;
    int j = myPointToPosition(0, i);
    if (j >= 0)
      if (j <= this.mSrcDragPos)
        j++;
    while (i >= 0)
      return j;
    return 0;
  }

  private void log(String paramString)
  {
    Log.e("draganddrop", paramString);
  }

  private int myPointToPosition(int paramInt1, int paramInt2)
  {
    if (paramInt2 < 0)
    {
      int j = myPointToPosition(paramInt1, paramInt2 + this.mItemHeightNormal);
      if (j > 0)
        return j - 1;
    }
    Rect localRect = this.mTempRect;
    for (int i = -1 + getChildCount(); i >= 0; i--)
    {
      getChildAt(i).getHitRect(localRect);
      if (localRect.contains(paramInt1, paramInt2))
        return i + getFirstVisiblePosition();
    }
    return -1;
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
        ViewGroup.LayoutParams localLayoutParams = ((View)localObject).getLayoutParams();
        localLayoutParams.height = this.mItemHeightNormal;
        ((View)localObject).setLayoutParams(localLayoutParams);
        ((View)localObject).setVisibility(0);
        i++;
      }
      catch (IllegalStateException localIllegalStateException)
      {
        break label62;
      }
    }
  }

  public boolean onInterceptTouchEvent(MotionEvent paramMotionEvent)
  {
    if ((this.mDragListener != null) || (this.mDropListener != null))
      switch (paramMotionEvent.getAction())
      {
      default:
      case 0:
      }
    while (true)
    {
      return true;
      int i = (int)paramMotionEvent.getX();
      int j = (int)paramMotionEvent.getY();
      int k = pointToPosition(i, j);
      if ((k != -1) && (k >= getHeaderViewsCount()))
      {
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
          this.mLowerBound = Math.max(m + j, 2 * this.mHeight / 3);
          return false;
        }
        stopDragging();
      }
    }
  }

  protected void onMeasure(int paramInt1, int paramInt2)
  {
    this.standardLayout.scaleToBounds(View.MeasureSpec.getSize(paramInt1), View.MeasureSpec.getSize(paramInt2));
    this.itemLayout.scaleToBounds(this.standardLayout);
    this.mItemHeightNormal = this.itemLayout.height;
    this.mItemHeightHalf = (this.mItemHeightNormal / 2);
    this.mItemHeightExpanded = (2 * this.mItemHeightNormal);
    super.onMeasure(paramInt1, paramInt2);
  }

  public boolean onTouchEvent(MotionEvent paramMotionEvent)
  {
    int i;
    if (((this.mDragListener != null) || (this.mDropListener != null)) && (this.mDragView != null))
    {
      i = paramMotionEvent.getAction();
      switch (i)
      {
      default:
      case 1:
      case 3:
      case 0:
      case 2:
      }
    }
    label338: label350: 
    while (true)
    {
      return true;
      Rect localRect = this.mTempRect;
      this.mDragView.getDrawingRect(localRect);
      stopDragging();
      if ((this.mDropListener != null) && (this.mDragPos >= 0) && (this.mDragPos < getCount()))
        this.mDropListener.drop(this.mSrcDragPos, this.mDragPos);
      unExpandViews(false);
      continue;
      int j = (int)paramMotionEvent.getX();
      int k = (int)paramMotionEvent.getY();
      dragView(j, k);
      int m = getItemForPosition(k);
      if (m >= 0)
      {
        if ((i == 0) || (m != this.mDragPos))
        {
          if (this.mDragListener != null)
            this.mDragListener.drag(this.mDragPos, m);
          this.mDragPos = m;
          doExpansion();
        }
        adjustScrollBounds(k);
        int n;
        if (k > this.mLowerBound)
          if (getLastVisiblePosition() < -1 + getCount())
            if (k > (this.mHeight + this.mLowerBound) / 2)
              n = 16;
        while (true)
        {
          if (n == 0)
            break label350;
          smoothScrollBy(n, 30);
          break;
          n = 4;
          continue;
          n = 1;
          continue;
          if (k < this.mUpperBound)
          {
            if (k < this.mUpperBound / 2);
            for (n = -16; ; n = -4)
            {
              if ((getFirstVisiblePosition() != 0) || (getChildAt(0).getTop() < getPaddingTop()))
                break label338;
              n = 0;
              break;
            }
            continue;
            return super.onTouchEvent(paramMotionEvent);
          }
          else
          {
            n = 0;
          }
        }
      }
    }
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
 * Qualified Name:     fm.qingting.qtradio.view.virtualchannels.DragAndDropListView
 * JD-Core Version:    0.6.2
 */