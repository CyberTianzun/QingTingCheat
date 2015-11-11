package com.viewpagerindicator;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewConfigurationCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.BaseSavedState;
import android.view.View.MeasureSpec;
import android.view.ViewConfiguration;
import fm.qingting.qtradio.R.styleable;

public class CirclePageIndicator extends View
  implements PageIndicator
{
  private static final int INVALID_POINTER = -1;
  private int mActivePointerId = -1;
  private boolean mCentered;
  private int mCurrentPage;
  private boolean mIsDragging;
  private float mLastMotionX = -1.0F;
  private ViewPager.OnPageChangeListener mListener;
  private int mOrientation;
  private float mPageOffset;
  private final Paint mPaintFill = new Paint(1);
  private final Paint mPaintPageFill = new Paint(1);
  private final Paint mPaintStroke = new Paint(1);
  private float mRadius;
  private int mScrollState;
  private boolean mSnap;
  private int mSnapPage;
  private int mTouchSlop;
  private ViewPager mViewPager;

  public CirclePageIndicator(Context paramContext)
  {
    this(paramContext, null);
  }

  public CirclePageIndicator(Context paramContext, AttributeSet paramAttributeSet)
  {
    this(paramContext, paramAttributeSet, 0);
  }

  public CirclePageIndicator(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
    if (isInEditMode())
      return;
    Resources localResources = getResources();
    int i = localResources.getColor(2131296283);
    int j = localResources.getColor(2131296282);
    int k = localResources.getInteger(2131427334);
    int m = localResources.getColor(2131296284);
    float f1 = localResources.getDimension(2131361814);
    float f2 = localResources.getDimension(2131361813);
    boolean bool1 = localResources.getBoolean(2131623936);
    boolean bool2 = localResources.getBoolean(2131623937);
    TypedArray localTypedArray = paramContext.obtainStyledAttributes(paramAttributeSet, R.styleable.CirclePageIndicator, paramInt, 0);
    this.mCentered = localTypedArray.getBoolean(2, bool1);
    this.mOrientation = localTypedArray.getInt(0, k);
    this.mPaintPageFill.setStyle(Paint.Style.FILL);
    this.mPaintPageFill.setColor(localTypedArray.getColor(5, i));
    this.mPaintStroke.setStyle(Paint.Style.STROKE);
    this.mPaintStroke.setColor(localTypedArray.getColor(8, m));
    this.mPaintStroke.setStrokeWidth(localTypedArray.getDimension(3, f1));
    this.mPaintFill.setStyle(Paint.Style.FILL);
    this.mPaintFill.setColor(localTypedArray.getColor(4, j));
    this.mRadius = localTypedArray.getDimension(6, f2);
    this.mSnap = localTypedArray.getBoolean(7, bool2);
    Drawable localDrawable = localTypedArray.getDrawable(1);
    if (localDrawable != null)
      setBackgroundDrawable(localDrawable);
    localTypedArray.recycle();
    this.mTouchSlop = ViewConfigurationCompat.getScaledPagingTouchSlop(ViewConfiguration.get(paramContext));
  }

  private int measureLong(int paramInt)
  {
    int i = View.MeasureSpec.getMode(paramInt);
    int j = View.MeasureSpec.getSize(paramInt);
    int k;
    if ((i == 1073741824) || (this.mViewPager == null))
      k = j;
    do
    {
      return k;
      int m = this.mViewPager.getAdapter().getCount();
      k = (int)(1.0F + (getPaddingLeft() + getPaddingRight() + m * 2 * this.mRadius + (m - 1) * this.mRadius));
    }
    while (i != -2147483648);
    return Math.min(k, j);
  }

  private int measureShort(int paramInt)
  {
    int i = View.MeasureSpec.getMode(paramInt);
    int j = View.MeasureSpec.getSize(paramInt);
    if (i == 1073741824)
      return j;
    int k = (int)(1.0F + (2.0F * this.mRadius + getPaddingTop() + getPaddingBottom()));
    if (i == -2147483648)
      return Math.min(k, j);
    return k;
  }

  public int getFillColor()
  {
    return this.mPaintFill.getColor();
  }

  public int getOrientation()
  {
    return this.mOrientation;
  }

  public int getPageColor()
  {
    return this.mPaintPageFill.getColor();
  }

  public float getRadius()
  {
    return this.mRadius;
  }

  public int getStrokeColor()
  {
    return this.mPaintStroke.getColor();
  }

  public float getStrokeWidth()
  {
    return this.mPaintStroke.getStrokeWidth();
  }

  public boolean isCentered()
  {
    return this.mCentered;
  }

  public boolean isSnap()
  {
    return this.mSnap;
  }

  public void notifyDataSetChanged()
  {
    invalidate();
  }

  protected void onDraw(Canvas paramCanvas)
  {
    super.onDraw(paramCanvas);
    if (this.mViewPager == null);
    int i;
    do
    {
      return;
      i = this.mViewPager.getAdapter().getCount();
    }
    while (i == 0);
    if (this.mCurrentPage >= i)
    {
      setCurrentItem(i - 1);
      return;
    }
    int j;
    int k;
    int m;
    int n;
    float f1;
    float f2;
    float f3;
    float f4;
    int i1;
    label168: float f8;
    float f9;
    if (this.mOrientation == 0)
    {
      j = getWidth();
      k = getPaddingLeft();
      m = getPaddingRight();
      n = getPaddingTop();
      f1 = 3.0F * this.mRadius;
      f2 = n + this.mRadius;
      f3 = k + this.mRadius;
      if (this.mCentered)
        f3 += (j - k - m) / 2.0F - f1 * i / 2.0F;
      f4 = this.mRadius;
      if (this.mPaintStroke.getStrokeWidth() > 0.0F)
        f4 -= this.mPaintStroke.getStrokeWidth() / 2.0F;
      i1 = 0;
      if (i1 >= i)
        break label289;
      f8 = f3 + f1 * i1;
      if (this.mOrientation != 0)
        break label282;
      f9 = f8;
      f8 = f2;
    }
    while (true)
    {
      if (this.mPaintPageFill.getAlpha() > 0)
        paramCanvas.drawCircle(f9, f8, f4, this.mPaintPageFill);
      if (f4 != this.mRadius)
        paramCanvas.drawCircle(f9, f8, this.mRadius, this.mPaintStroke);
      i1++;
      break label168;
      j = getHeight();
      k = getPaddingTop();
      m = getPaddingBottom();
      n = getPaddingLeft();
      break;
      label282: f9 = f2;
    }
    label289: int i2;
    float f5;
    float f7;
    if (this.mSnap)
    {
      i2 = this.mSnapPage;
      f5 = f1 * i2;
      if (!this.mSnap)
        f5 += f1 * this.mPageOffset;
      if (this.mOrientation != 0)
        break label369;
      f7 = f3 + f5;
    }
    while (true)
    {
      paramCanvas.drawCircle(f7, f2, this.mRadius, this.mPaintFill);
      return;
      i2 = this.mCurrentPage;
      break;
      label369: float f6 = f3 + f5;
      f7 = f2;
      f2 = f6;
    }
  }

  protected void onMeasure(int paramInt1, int paramInt2)
  {
    if (this.mOrientation == 0)
    {
      setMeasuredDimension(measureLong(paramInt1), measureShort(paramInt2));
      return;
    }
    setMeasuredDimension(measureShort(paramInt1), measureLong(paramInt2));
  }

  public void onPageScrollStateChanged(int paramInt)
  {
    this.mScrollState = paramInt;
    if (this.mListener != null)
      this.mListener.onPageScrollStateChanged(paramInt);
  }

  public void onPageScrolled(int paramInt1, float paramFloat, int paramInt2)
  {
    this.mCurrentPage = paramInt1;
    this.mPageOffset = paramFloat;
    invalidate();
    if (this.mListener != null)
      this.mListener.onPageScrolled(paramInt1, paramFloat, paramInt2);
  }

  public void onPageSelected(int paramInt)
  {
    if ((this.mSnap) || (this.mScrollState == 0))
    {
      this.mCurrentPage = paramInt;
      this.mSnapPage = paramInt;
      invalidate();
    }
    if (this.mListener != null)
      this.mListener.onPageSelected(paramInt);
  }

  public void onRestoreInstanceState(Parcelable paramParcelable)
  {
    SavedState localSavedState = (SavedState)paramParcelable;
    super.onRestoreInstanceState(localSavedState.getSuperState());
    this.mCurrentPage = localSavedState.currentPage;
    this.mSnapPage = localSavedState.currentPage;
    requestLayout();
  }

  public Parcelable onSaveInstanceState()
  {
    SavedState localSavedState = new SavedState(super.onSaveInstanceState());
    localSavedState.currentPage = this.mCurrentPage;
    return localSavedState;
  }

  public boolean onTouchEvent(MotionEvent paramMotionEvent)
  {
    if (super.onTouchEvent(paramMotionEvent));
    label323: 
    do
    {
      int i;
      do
      {
        int n;
        float f1;
        float f2;
        do
        {
          float f4;
          do
          {
            float f3;
            do
            {
              return true;
              if ((this.mViewPager == null) || (this.mViewPager.getAdapter().getCount() == 0))
                return false;
              i = 0xFF & paramMotionEvent.getAction();
              switch (i)
              {
              case 4:
              default:
                return true;
              case 0:
                this.mActivePointerId = MotionEventCompat.getPointerId(paramMotionEvent, 0);
                this.mLastMotionX = paramMotionEvent.getX();
                return true;
              case 2:
                f3 = MotionEventCompat.getX(paramMotionEvent, MotionEventCompat.findPointerIndex(paramMotionEvent, this.mActivePointerId));
                f4 = f3 - this.mLastMotionX;
                if ((!this.mIsDragging) && (Math.abs(f4) > this.mTouchSlop))
                  this.mIsDragging = true;
                break;
              case 1:
              case 3:
              case 5:
              case 6:
              }
            }
            while (!this.mIsDragging);
            this.mLastMotionX = f3;
          }
          while ((!this.mViewPager.isFakeDragging()) && (!this.mViewPager.beginFakeDrag()));
          this.mViewPager.fakeDragBy(f4);
          return true;
          if (this.mIsDragging)
            break label323;
          n = this.mViewPager.getAdapter().getCount();
          int i1 = getWidth();
          f1 = i1 / 2.0F;
          f2 = i1 / 6.0F;
          if ((this.mCurrentPage <= 0) || (paramMotionEvent.getX() >= f1 - f2))
            break;
        }
        while (i == 3);
        this.mViewPager.setCurrentItem(-1 + this.mCurrentPage);
        return true;
        if ((this.mCurrentPage >= n - 1) || (paramMotionEvent.getX() <= f2 + f1))
          break;
      }
      while (i == 3);
      this.mViewPager.setCurrentItem(1 + this.mCurrentPage);
      return true;
      this.mIsDragging = false;
      this.mActivePointerId = -1;
    }
    while (!this.mViewPager.isFakeDragging());
    this.mViewPager.endFakeDrag();
    return true;
    int m = MotionEventCompat.getActionIndex(paramMotionEvent);
    this.mLastMotionX = MotionEventCompat.getX(paramMotionEvent, m);
    this.mActivePointerId = MotionEventCompat.getPointerId(paramMotionEvent, m);
    return true;
    int j = MotionEventCompat.getActionIndex(paramMotionEvent);
    if (MotionEventCompat.getPointerId(paramMotionEvent, j) == this.mActivePointerId)
    {
      int k = 0;
      if (j == 0)
        k = 1;
      this.mActivePointerId = MotionEventCompat.getPointerId(paramMotionEvent, k);
    }
    this.mLastMotionX = MotionEventCompat.getX(paramMotionEvent, MotionEventCompat.findPointerIndex(paramMotionEvent, this.mActivePointerId));
    return true;
  }

  public void setCentered(boolean paramBoolean)
  {
    this.mCentered = paramBoolean;
    invalidate();
  }

  public void setCurrentItem(int paramInt)
  {
    if (this.mViewPager == null)
      throw new IllegalStateException("ViewPager has not been bound.");
    this.mViewPager.setCurrentItem(paramInt);
    this.mCurrentPage = paramInt;
    invalidate();
  }

  public void setFillColor(int paramInt)
  {
    this.mPaintFill.setColor(paramInt);
    invalidate();
  }

  public void setOnPageChangeListener(ViewPager.OnPageChangeListener paramOnPageChangeListener)
  {
    this.mListener = paramOnPageChangeListener;
  }

  public void setOrientation(int paramInt)
  {
    switch (paramInt)
    {
    default:
      throw new IllegalArgumentException("Orientation must be either HORIZONTAL or VERTICAL.");
    case 0:
    case 1:
    }
    this.mOrientation = paramInt;
    requestLayout();
  }

  public void setPageColor(int paramInt)
  {
    this.mPaintPageFill.setColor(paramInt);
    invalidate();
  }

  public void setRadius(float paramFloat)
  {
    this.mRadius = paramFloat;
    invalidate();
  }

  public void setSnap(boolean paramBoolean)
  {
    this.mSnap = paramBoolean;
    invalidate();
  }

  public void setStrokeColor(int paramInt)
  {
    this.mPaintStroke.setColor(paramInt);
    invalidate();
  }

  public void setStrokeWidth(float paramFloat)
  {
    this.mPaintStroke.setStrokeWidth(paramFloat);
    invalidate();
  }

  public void setViewPager(ViewPager paramViewPager)
  {
    if (this.mViewPager == paramViewPager)
      return;
    if (this.mViewPager != null)
      this.mViewPager.setOnPageChangeListener(null);
    if (paramViewPager.getAdapter() == null)
      throw new IllegalStateException("ViewPager does not have adapter instance.");
    this.mViewPager = paramViewPager;
    this.mViewPager.setOnPageChangeListener(this);
    invalidate();
  }

  public void setViewPager(ViewPager paramViewPager, int paramInt)
  {
    setViewPager(paramViewPager);
    setCurrentItem(paramInt);
  }

  static class SavedState extends View.BaseSavedState
  {
    public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.Creator()
    {
      public CirclePageIndicator.SavedState createFromParcel(Parcel paramAnonymousParcel)
      {
        return new CirclePageIndicator.SavedState(paramAnonymousParcel, null);
      }

      public CirclePageIndicator.SavedState[] newArray(int paramAnonymousInt)
      {
        return new CirclePageIndicator.SavedState[paramAnonymousInt];
      }
    };
    int currentPage;

    private SavedState(Parcel paramParcel)
    {
      super();
      this.currentPage = paramParcel.readInt();
    }

    public SavedState(Parcelable paramParcelable)
    {
      super();
    }

    public void writeToParcel(Parcel paramParcel, int paramInt)
    {
      super.writeToParcel(paramParcel, paramInt);
      paramParcel.writeInt(this.currentPage);
    }
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.viewpagerindicator.CirclePageIndicator
 * JD-Core Version:    0.6.2
 */