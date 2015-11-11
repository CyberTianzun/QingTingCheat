package fm.qingting.qtradio.view.dragdrop;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.os.Vibrator;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.RotateAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import fm.qingting.framework.view.IView;
import fm.qingting.framework.view.ViewGroupViewImpl;
import fm.qingting.qtradio.manager.SkinManager;
import java.util.ArrayList;
import java.util.List;

public class DragDropContainer3 extends ViewGroupViewImpl
  implements View.OnLongClickListener, View.OnClickListener
{
  private static final int BACKFROMINNERSPACE = 238;
  private static final int BACKFROMOUTERSPACE = 254;
  private static final int CROSS_CORNER_LB = 9;
  private static final int CROSS_EDGE_BOTTOM = 8;
  private static final int CROSS_EDGE_LEFT = 1;
  private static final int CROSS_EDGE_RIGHT = 2;
  private static final int CROSS_EDGE_TOP = 4;
  private static final int DURATION = 180;
  private static final int INBOUND = 0;
  private static final int INNERSPACE = 239;
  private static final int OUTERSPACE = 255;
  private static final int POSITION_INVALID = -1;
  private static final int RESETDURATION = 120;
  private static final boolean ROCK = false;
  private static final String TAG = "dragdrop";
  private DragDropAdapter mAdapter;
  private final Rect mBound = new Rect();
  private ArrayList<IView> mChildren;
  private int mCorrectionOffset = 0;
  private Bitmap mDragBitmap;
  private int mDragIndex = -1;
  private int mDragOffsetX;
  private int mDragOffsetY;
  private ImageView mDragView;
  private boolean mInDragMode = false;
  private ArrayList<Integer> mIndexs = new ArrayList();
  private float mInitialMotionX;
  private float mInitialMotionY;
  private int mItemHeight;
  private int mItemWidth;
  private DragDropListener mListener;
  private int mOuterSpaceBoundX;
  private int mOuterSpaceBoundY_left;
  private int mOuterSpaceBoundY_right;
  private RotateAnimation mRotateAnimation;
  private int mStartDragIndex = -1;
  private boolean mTravelingInnerSpace = false;
  private boolean mTravelingOuterSpace = false;
  private Vibrator mVibrator;
  private WindowManager mWindowManager;
  private WindowManager.LayoutParams mWindowParams;

  public DragDropContainer3(Context paramContext)
  {
    super(paramContext);
    this.mVibrator = ((Vibrator)paramContext.getSystemService("vibrator"));
    this.mRotateAnimation = new RotateAnimation(-3.0F, 3.0F, 1, 0.5F, 1, 0.5F);
    this.mRotateAnimation.setRepeatMode(2);
    this.mRotateAnimation.setRepeatCount(-1);
    this.mRotateAnimation.setDuration(80L);
    this.mRotateAnimation.setFillAfter(false);
    this.mRotateAnimation.setInterpolator(new AccelerateDecelerateInterpolator());
  }

  private void adjustBound(int paramInt1, int paramInt2)
  {
    int i = paramInt1 + getItemWidth();
    int j = paramInt2 + getItemHeight();
    this.mBound.set(paramInt1, paramInt2, i, j);
  }

  private void changeDragIndex(int paramInt1, int paramInt2)
  {
    if ((paramInt2 < 0) || (isItemFixed(paramInt2)));
    do
    {
      return;
      if (paramInt2 >= this.mChildren.size())
        paramInt2 = -1 + this.mChildren.size();
    }
    while (paramInt1 == paramInt2);
    this.mDragIndex = paramInt2;
    IView localIView = (IView)this.mChildren.remove(paramInt1);
    this.mChildren.add(paramInt2, localIView);
    Integer localInteger = (Integer)this.mIndexs.remove(paramInt1);
    this.mIndexs.add(paramInt2, localInteger);
    adjustBound(getItemLeft(paramInt2), getItemTop(paramInt2));
    layoutItems();
    doTranslateAnimations(paramInt1, paramInt2);
  }

  private void doTranslateAnimations(int paramInt1, int paramInt2)
  {
    int i = getRow(paramInt1);
    int j = getRow(paramInt2);
    int k = this.mAdapter.getColumnCount();
    if (i == j)
      translateViewsHorizonallyByRange(paramInt1, paramInt2, true);
    do
    {
      return;
      if (i > j)
      {
        if (paramInt1 % k != 0)
          translateViewsHorizonallyByRange(paramInt1, i * k, false);
        translateViewAcrossRow(getAcrossRowView(i, j), i, j, true);
        translateViewsHorizonallyByRange(-1 + k * (j + 1), paramInt2, false);
        return;
      }
      translateViewsHorizonallyByRange(paramInt1, -1 + k * (i + 1), false);
      translateViewAcrossRow(getAcrossRowView(i, j), i, j, true);
    }
    while (paramInt2 % k == 0);
    translateViewsHorizonallyByRange(j * k, paramInt2, false);
  }

  private void drag(int paramInt1, int paramInt2)
  {
    this.mWindowParams.x = paramInt1;
    this.mWindowParams.y = (paramInt2 + getCorrectionOffset());
    this.mWindowManager.updateViewLayout(this.mDragView, this.mWindowParams);
    int i = getCrossEdgeState(paramInt1 + getItemWidth() / 2, paramInt2 + getItemHeight() / 2);
    int j = this.mDragIndex;
    int k = this.mAdapter.getColumnCount();
    switch (i)
    {
    default:
    case 1:
    case 4:
    case 2:
    case 8:
    case 255:
    case 239:
    case 9:
    }
    do
    {
      do
      {
        do
        {
          do
          {
            do
            {
              do
              {
                do
                  return;
                while (j % k <= 0);
                changeDragIndex(j, j - 1);
                return;
              }
              while (j / k <= 0);
              changeDragIndex(j, j - k);
              return;
            }
            while ((j % k == k - 1) || (j == -1 + this.mChildren.size()));
            changeDragIndex(j, j + 1);
            return;
          }
          while (j / k == this.mChildren.size() / k);
          changeDragIndex(j, j + k);
          return;
        }
        while (this.mTravelingOuterSpace);
        this.mTravelingOuterSpace = true;
        changeDragIndex(j, -1 + this.mChildren.size());
        return;
      }
      while (this.mTravelingInnerSpace);
      this.mTravelingInnerSpace = true;
      return;
    }
    while (j % k <= 0);
    changeDragIndex(j, -1 + (j + k));
  }

  private void endDragging()
  {
    if (this.mDragView != null)
    {
      this.mDragView.setVisibility(8);
      if (this.mWindowManager != null)
        this.mWindowManager.removeView(this.mDragView);
      this.mDragView.setImageDrawable(null);
      this.mDragView = null;
    }
    if (this.mDragBitmap != null)
    {
      this.mDragBitmap.recycle();
      this.mDragBitmap = null;
    }
    if (this.mDragIndex != -1)
    {
      if (this.mWindowParams != null)
        resetDraggingView(this.mWindowParams.x, this.mWindowParams.y - getCorrectionOffset());
      if (this.mListener != null)
        this.mListener.onDrop(this.mStartDragIndex, this.mDragIndex);
    }
    this.mDragIndex = -1;
  }

  private int findIndex(View paramView)
  {
    ArrayList localArrayList = this.mChildren;
    int i = localArrayList.size();
    for (int j = 0; j < i; j++)
      if (paramView.equals(((IView)localArrayList.get(j)).getView()))
        return j;
    return -1;
  }

  private int findPosition(float paramFloat1, float paramFloat2)
  {
    ArrayList localArrayList = this.mChildren;
    int i = localArrayList.size();
    for (int j = 0; j < i; j++)
      if (!isItemFixed(j))
      {
        View localView = ((IView)localArrayList.get(j)).getView();
        if ((paramFloat1 > localView.getLeft()) && (paramFloat1 < localView.getRight()) && (paramFloat2 > localView.getTop()) && (paramFloat2 < localView.getBottom()))
          return j;
      }
    return -1;
  }

  private int findWhereToLand(int paramInt1, int paramInt2)
  {
    for (int i = 0; i < this.mChildren.size(); i++)
    {
      int j = getItemLeft(i);
      int k = getItemTop(i);
      int m = j + getItemWidth();
      int n = k + getItemHeight();
      if ((paramInt1 >= j) && (paramInt1 < m) && (paramInt2 >= k) && (paramInt2 < n))
        return i;
    }
    return -1;
  }

  private View getAcrossRowView(int paramInt1, int paramInt2)
  {
    int i = this.mAdapter.getColumnCount();
    if (paramInt1 < paramInt2)
      return ((IView)this.mChildren.get(-1 + (i + paramInt1 * i))).getView();
    if (paramInt1 > paramInt2)
      return ((IView)this.mChildren.get(i * paramInt1)).getView();
    return null;
  }

  private final int getCorrectionOffset()
  {
    return this.mCorrectionOffset;
  }

  private final int getCrossEdgeState(int paramInt1, int paramInt2)
  {
    int i;
    if (isInnerSpace(paramInt1, paramInt2))
      i = 239;
    int j;
    do
    {
      Rect localRect;
      boolean bool;
      do
      {
        return i;
        if (inOuterSpace(paramInt1, paramInt2))
          return 255;
        if (this.mTravelingOuterSpace)
        {
          int m = findWhereToLand(paramInt1, paramInt2);
          if (m != -1)
          {
            changeDragIndex(this.mDragIndex, m);
            this.mTravelingOuterSpace = false;
            return 254;
          }
        }
        if (this.mTravelingInnerSpace)
        {
          int k = findWhereToLand(paramInt1, paramInt2);
          if (k != -1)
          {
            changeDragIndex(this.mDragIndex, k);
            this.mTravelingInnerSpace = false;
            return 238;
          }
        }
        localRect = this.mBound;
        bool = localRect.contains(paramInt1, paramInt2);
        i = 0;
      }
      while (bool);
      if (paramInt1 >= localRect.right)
        return 2;
      if (paramInt2 < localRect.top)
        return 4;
      if ((paramInt1 < localRect.left) && (paramInt2 >= localRect.bottom))
        return 9;
      if (paramInt1 < localRect.left)
        return 1;
      j = localRect.bottom;
      i = 0;
    }
    while (paramInt2 < j);
    return 8;
  }

  private int getItemHeight()
  {
    return this.mItemHeight;
  }

  private int getItemLeft(int paramInt)
  {
    return this.mItemWidth * (paramInt % this.mAdapter.getColumnCount());
  }

  private int getItemTop(int paramInt)
  {
    return this.mItemHeight * (paramInt / this.mAdapter.getColumnCount());
  }

  private int getItemWidth()
  {
    return this.mItemWidth;
  }

  private final int getRow(int paramInt)
  {
    return paramInt / this.mAdapter.getColumnCount();
  }

  private boolean inOuterSpace(int paramInt1, int paramInt2)
  {
    return ((paramInt1 < this.mOuterSpaceBoundX) && (paramInt2 > this.mOuterSpaceBoundY_left)) || ((paramInt1 > this.mOuterSpaceBoundX) && (paramInt2 > this.mOuterSpaceBoundY_right));
  }

  private final boolean isDragging()
  {
    return this.mDragIndex != -1;
  }

  private final boolean isInDragMode()
  {
    return this.mInDragMode;
  }

  private boolean isInnerSpace(int paramInt1, int paramInt2)
  {
    return paramInt2 < 0;
  }

  private final boolean isItemFixed(int paramInt)
  {
    return this.mAdapter.isFixed(paramInt);
  }

  private void isReadyToGo(View paramView)
  {
    int i = findIndex(paramView);
    if (isItemFixed(i))
      return;
    this.mVibrator.vibrate(50L);
    if (this.mListener != null)
      this.mListener.onEnterDragMode(i);
    if (i != -1)
    {
      this.mDragIndex = i;
      this.mStartDragIndex = i;
      startDragging(paramView, getItemLeft(i), getItemTop(i));
    }
    if ((paramView instanceof DraggableItem))
      ((DraggableItem)paramView).setState(1);
    while (true)
    {
      enableDragDrop();
      return;
      paramView.setVisibility(4);
    }
  }

  private void layoutItems()
  {
    if ((this.mAdapter == null) || (this.mChildren == null))
      throw new IllegalStateException("u should call setDragDropAdapter right after constructor");
    for (int i = 0; i < this.mChildren.size(); i++)
    {
      View localView = ((IView)this.mChildren.get(i)).getView();
      int j = getItemLeft(i);
      int k = getItemTop(i);
      localView.layout(j, k, j + this.mItemWidth, k + this.mItemHeight);
      if (i == -1 + this.mChildren.size())
      {
        this.mOuterSpaceBoundX = (j + this.mItemWidth);
        this.mOuterSpaceBoundY_right = k;
        this.mOuterSpaceBoundY_left = (k + this.mItemHeight);
      }
    }
  }

  private void letsRock()
  {
  }

  private void onItemClick(View paramView)
  {
    if (this.mListener == null);
    int i;
    do
    {
      return;
      i = findIndex(paramView);
    }
    while (i == -1);
    this.mListener.onItemClick(i);
  }

  private void onTouchDown(MotionEvent paramMotionEvent)
  {
    this.mInitialMotionX = paramMotionEvent.getX();
    this.mInitialMotionY = paramMotionEvent.getY();
    if (!isInDragMode());
    int i;
    do
    {
      return;
      i = findPosition(this.mInitialMotionX, this.mInitialMotionY);
    }
    while (i == -1);
    this.mDragIndex = i;
    this.mStartDragIndex = i;
    View localView = ((IView)this.mChildren.get(i)).getView();
    localView.clearAnimation();
    startDragging(localView, getItemLeft(i), getItemTop(i));
    if ((localView instanceof DraggableItem))
    {
      ((DraggableItem)localView).setState(1);
      return;
    }
    localView.setVisibility(4);
  }

  private void onTouchMove(MotionEvent paramMotionEvent)
  {
    if (!isDragging())
      return;
    drag((int)(paramMotionEvent.getX() - this.mDragOffsetX), (int)(paramMotionEvent.getY() - this.mDragOffsetY));
  }

  private void onTouchUp(MotionEvent paramMotionEvent)
  {
    endDragging();
  }

  private void resetDraggingView(int paramInt1, int paramInt2)
  {
    if (!isDragging())
      return;
    View localView = ((IView)this.mChildren.get(this.mDragIndex)).getView();
    if ((localView instanceof DraggableItem))
      ((DraggableItem)localView).setState(0);
    while (paramInt2 < 0)
    {
      localView.startAnimation(this.mRotateAnimation);
      return;
      localView.setVisibility(0);
    }
    int i = getItemLeft(this.mDragIndex);
    int j = getItemTop(this.mDragIndex);
    TranslateAnimation localTranslateAnimation = new TranslateAnimation(paramInt1 - i, 0.0F, paramInt2 - j, 0.0F);
    localTranslateAnimation.setDuration(120L);
    localTranslateAnimation.setInterpolator(new AccelerateDecelerateInterpolator());
    localTranslateAnimation.setAnimationListener(new Animation.AnimationListener()
    {
      public void onAnimationEnd(Animation paramAnonymousAnimation)
      {
        DragDropContainer3.this.letsRock();
      }

      public void onAnimationRepeat(Animation paramAnonymousAnimation)
      {
      }

      public void onAnimationStart(Animation paramAnonymousAnimation)
      {
      }
    });
    localView.clearAnimation();
    localView.startAnimation(localTranslateAnimation);
  }

  private void startDragging(View paramView, int paramInt1, int paramInt2)
  {
    if (this.mWindowParams == null)
    {
      this.mWindowParams = new WindowManager.LayoutParams();
      this.mWindowParams.gravity = 51;
      this.mWindowParams.height = -2;
      this.mWindowParams.width = -2;
      this.mWindowParams.flags = 664;
      this.mWindowParams.format = -3;
      this.mWindowParams.windowAnimations = 0;
    }
    this.mWindowParams.x = paramInt1;
    this.mWindowParams.y = (paramInt2 + getCorrectionOffset());
    adjustBound(paramInt1, paramInt2);
    this.mDragOffsetX = ((int)(this.mInitialMotionX - paramInt1));
    this.mDragOffsetY = ((int)(this.mInitialMotionY - paramInt2));
    ImageView localImageView = new ImageView(getContext());
    localImageView.setBackgroundColor(SkinManager.getCardColor());
    localImageView.setPadding(0, 0, 0, 0);
    paramView.setDrawingCacheEnabled(true);
    Bitmap localBitmap = Bitmap.createBitmap(paramView.getDrawingCache());
    localImageView.setImageBitmap(localBitmap);
    this.mDragBitmap = localBitmap;
    paramView.destroyDrawingCache();
    if (this.mWindowManager == null)
      this.mWindowManager = ((WindowManager)getContext().getSystemService("window"));
    this.mWindowManager.addView(localImageView, this.mWindowParams);
    this.mDragView = localImageView;
    if (this.mListener != null)
      this.mListener.onDragStart(this.mDragIndex);
  }

  private void stopRocking()
  {
    for (int i = 0; i < this.mChildren.size(); i++)
      if ((i != this.mDragIndex) && (!isItemFixed(i)))
        ((IView)this.mChildren.get(i)).getView().clearAnimation();
  }

  private void translateViewAcrossRow(View paramView, int paramInt1, int paramInt2, boolean paramBoolean)
  {
    if (paramInt1 == paramInt2)
      return;
    int i = this.mAdapter.getColumnCount();
    int j = (i - 1) * getWidth() / i;
    int k = (paramInt2 - paramInt1) * getItemHeight();
    if (paramInt1 > paramInt2);
    for (float f = j; ; f = -j)
    {
      TranslateAnimation localTranslateAnimation = new TranslateAnimation(f, 0.0F, k, 0.0F);
      localTranslateAnimation.setDuration(180L);
      localTranslateAnimation.setInterpolator(new AccelerateDecelerateInterpolator());
      if (paramBoolean)
        localTranslateAnimation.setAnimationListener(new Animation.AnimationListener()
        {
          public void onAnimationEnd(Animation paramAnonymousAnimation)
          {
            DragDropContainer3.this.letsRock();
          }

          public void onAnimationRepeat(Animation paramAnonymousAnimation)
          {
          }

          public void onAnimationStart(Animation paramAnonymousAnimation)
          {
          }
        });
      paramView.clearAnimation();
      paramView.startAnimation(localTranslateAnimation);
      return;
    }
  }

  private void translateViewsHorizonallyByRange(int paramInt1, int paramInt2, boolean paramBoolean)
  {
    if (paramInt1 == paramInt2)
      return;
    float f;
    label19: TranslateAnimation localTranslateAnimation;
    int i;
    if (paramInt1 > paramInt2)
    {
      f = -getItemWidth();
      localTranslateAnimation = new TranslateAnimation(f, 0.0F, 0.0F, 0.0F);
      localTranslateAnimation.setDuration(180L);
      localTranslateAnimation.setInterpolator(new AccelerateDecelerateInterpolator());
      if (paramInt1 <= paramInt2)
        break label141;
      i = paramInt2 + 1;
      paramInt2 = paramInt1 + 1;
    }
    while (true)
    {
      if (paramBoolean)
        localTranslateAnimation.setAnimationListener(new Animation.AnimationListener()
        {
          public void onAnimationEnd(Animation paramAnonymousAnimation)
          {
            DragDropContainer3.this.letsRock();
          }

          public void onAnimationRepeat(Animation paramAnonymousAnimation)
          {
          }

          public void onAnimationStart(Animation paramAnonymousAnimation)
          {
          }
        });
      for (int j = i; j < paramInt2; j++)
      {
        View localView = ((IView)this.mChildren.get(j)).getView();
        localView.clearAnimation();
        localView.startAnimation(localTranslateAnimation);
      }
      break;
      f = getItemWidth();
      break label19;
      label141: i = paramInt1;
    }
  }

  public void disableDragDrop()
  {
    this.mInDragMode = false;
    stopRocking();
  }

  public void enableDragDrop()
  {
    this.mInDragMode = true;
    letsRock();
  }

  public void ensureWindowRemoved()
  {
    endDragging();
  }

  public DragDropAdapter getAdapter()
  {
    return this.mAdapter;
  }

  public ArrayList<Integer> getIndexsList()
  {
    return this.mIndexs;
  }

  public List<IView> getItems()
  {
    return this.mChildren;
  }

  public int getSelectedIndex()
  {
    if (this.mChildren == null)
    {
      i = -1;
      return i;
    }
    for (int i = 0; ; i++)
    {
      if (i >= this.mChildren.size())
        break label52;
      if (((IView)this.mChildren.get(i)).getView().isSelected())
        break;
    }
    label52: return -1;
  }

  final void log(String paramString)
  {
    Log.e("dragdrop", paramString);
  }

  public void onClick(View paramView)
  {
    if (!isInDragMode())
      onItemClick(paramView);
  }

  public boolean onInterceptTouchEvent(MotionEvent paramMotionEvent)
  {
    int i = paramMotionEvent.getAction();
    if ((i != 1) && (isInDragMode()))
      return true;
    switch (i & 0xFF)
    {
    case 2:
    default:
    case 0:
    case 1:
    }
    while (true)
    {
      return super.onInterceptTouchEvent(paramMotionEvent);
      onTouchDown(paramMotionEvent);
      continue;
      onTouchUp(paramMotionEvent);
    }
  }

  protected void onLayout(boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    layoutItems();
  }

  public boolean onLongClick(View paramView)
  {
    if (!this.mInDragMode)
      isReadyToGo(paramView);
    return true;
  }

  protected void onMeasure(int paramInt1, int paramInt2)
  {
    if ((this.mAdapter == null) || (this.mChildren == null))
      throw new IllegalStateException("u should call setDragDropAdapter right after constructor");
    int i = this.mAdapter.getColumnCount();
    int j = View.MeasureSpec.getSize(paramInt1) / i;
    this.mItemWidth = j;
    int k = View.MeasureSpec.makeMeasureSpec(j, 1073741824);
    int m = this.mChildren.size();
    for (int n = 0; n < m; n++)
    {
      ((IView)this.mChildren.get(n)).getView().measure(k, paramInt2);
      if (n == 0)
        this.mItemHeight = ((IView)this.mChildren.get(n)).getView().getMeasuredHeight();
    }
    int i1 = this.mItemHeight;
    int i2 = m / i;
    if (m % i == 0);
    for (int i3 = 0; ; i3 = 1)
    {
      int i4 = i1 * (i3 + i2);
      setMeasuredDimension(View.MeasureSpec.getSize(paramInt1), i4);
      return;
    }
  }

  public boolean onTouchEvent(MotionEvent paramMotionEvent)
  {
    switch (0xFF & paramMotionEvent.getAction())
    {
    default:
    case 0:
    case 2:
    case 1:
    case 3:
    }
    while (true)
    {
      return true;
      onTouchDown(paramMotionEvent);
      continue;
      onTouchMove(paramMotionEvent);
      continue;
      onTouchUp(paramMotionEvent);
    }
  }

  public void setCorrectionOffset(int paramInt)
  {
    this.mCorrectionOffset = paramInt;
  }

  public void setDragDropAdapter(DragDropAdapter paramDragDropAdapter)
  {
    if (this.mAdapter != null)
      throw new IllegalStateException("adapter already set, don't set it twice");
    this.mAdapter = paramDragDropAdapter;
    int i = this.mAdapter.getCount();
    if (this.mChildren == null)
      this.mChildren = new ArrayList();
    while (true)
    {
      for (int j = 0; j < i; j++)
      {
        IView localIView = this.mAdapter.instantiateItem(j);
        if (localIView != null)
        {
          View localView = localIView.getView();
          localView.setOnLongClickListener(this);
          localView.setOnClickListener(this);
          addView(localView);
          this.mChildren.add(localIView);
        }
      }
      this.mChildren.clear();
      removeAllViews();
    }
  }

  public void setDragDropListener(DragDropListener paramDragDropListener)
  {
    this.mListener = paramDragDropListener;
  }

  public void setIndexsList(ArrayList<Integer> paramArrayList)
  {
    this.mIndexs.clear();
    for (int i = 0; i < paramArrayList.size(); i++)
      this.mIndexs.add(paramArrayList.get(i));
  }

  public boolean toggleDragDrop()
  {
    if (this.mInDragMode)
      disableDragDrop();
    while (true)
    {
      return this.mInDragMode;
      enableDragDrop();
    }
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.qtradio.view.dragdrop.DragDropContainer3
 * JD-Core Version:    0.6.2
 */