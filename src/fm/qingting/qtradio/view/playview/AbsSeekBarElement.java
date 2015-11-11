package fm.qingting.qtradio.view.playview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.support.v4.view.ViewConfigurationCompat;
import android.view.MotionEvent;
import android.view.ViewConfiguration;
import fm.qingting.framework.view.ViewElement;

public abstract class AbsSeekBarElement extends ViewElement
{
  private float mDownTouchX;
  private int mDragSlop;
  private boolean mInTouchMode;
  private boolean mIsDragging;
  private float mLastMotionX;
  private float mLastMotionY;
  protected int mMaxLength;
  private int mOnTouchThumbOffset = 0;
  private int mPaddingLeft = 0;
  private int mPaddingRight = 0;
  protected int mProgress;
  private OnSeekBarChangeListener mSeekBarChangeListener;
  protected float mSeekFactor = 0.0F;
  protected final Rect mThumbRect = new Rect();
  private int mTouchSlop = 10;

  public AbsSeekBarElement(Context paramContext)
  {
    super(paramContext);
    this.mDragSlop = ViewConfigurationCompat.getScaledPagingTouchSlop(ViewConfiguration.get(paramContext));
  }

  private void dispatchClickEvent()
  {
    if (this.mSeekBarChangeListener != null)
      this.mSeekBarChangeListener.onThumbClick(this);
  }

  private void dispatchSeekEvent()
  {
    if (this.mSeekFactor < 0.0F)
      this.mSeekFactor = 0.0F;
    while (true)
    {
      if (this.mSeekBarChangeListener != null)
        this.mSeekBarChangeListener.onSeek(this, this.mSeekFactor);
      return;
      if (this.mSeekFactor > 1.0F)
        this.mSeekFactor = 1.0F;
    }
  }

  private boolean downMotionInDragArea()
  {
    return (this.mLastMotionX > this.mThumbRect.left - this.mTouchSlop) && (this.mLastMotionX < this.mThumbRect.right + this.mTouchSlop) && (this.mLastMotionY > this.mThumbRect.top - this.mTouchSlop) && (this.mLastMotionY < this.mThumbRect.bottom + this.mTouchSlop);
  }

  private void notifyProgressChange(boolean paramBoolean)
  {
    if (this.mSeekBarChangeListener != null)
      this.mSeekBarChangeListener.onProgressChanged(this, this.mProgress, paramBoolean);
  }

  private void startTrackingTouch()
  {
    if (this.mSeekBarChangeListener != null)
      this.mSeekBarChangeListener.onStartTrackingTouch(this);
  }

  private void stopTrackingTouch()
  {
    if (this.mSeekBarChangeListener != null)
      this.mSeekBarChangeListener.onStopTrackingTouch(this);
  }

  private void trackTouchEvent(MotionEvent paramMotionEvent)
  {
    int i = getWidth();
    int j = i - this.mPaddingLeft - this.mPaddingRight;
    int k = (int)paramMotionEvent.getX() + this.mOnTouchThumbOffset;
    float f1;
    if (k < this.mPaddingLeft)
    {
      k = 0;
      f1 = 0.0F;
    }
    while (true)
    {
      this.mThumbRect.offsetTo(k - this.mThumbRect.width() / 2, this.mThumbRect.top);
      float f2 = 0.0F + f1 * getMax();
      this.mSeekFactor = f1;
      setProgress((int)f2, true);
      return;
      if (k > i - this.mPaddingRight)
      {
        f1 = 1.0F;
        k = i - this.mPaddingRight;
      }
      else
      {
        f1 = (k - this.mPaddingLeft) / j;
      }
    }
  }

  protected abstract void drawProgressBg(Canvas paramCanvas);

  protected abstract void drawThumb(Canvas paramCanvas);

  public int getMax()
  {
    return this.mMaxLength;
  }

  public int getProgress()
  {
    return this.mProgress;
  }

  public boolean handleTouchEvent(MotionEvent paramMotionEvent)
  {
    if ((!this.mInTouchMode) && (paramMotionEvent.getAction() != 0));
    do
    {
      return true;
      this.mLastMotionX = paramMotionEvent.getX();
      this.mLastMotionY = paramMotionEvent.getY();
      switch (paramMotionEvent.getAction())
      {
      default:
        return true;
      case 0:
        int i;
        float f;
        if (!downMotionInDragArea())
        {
          this.mIsDragging = true;
          i = (int)paramMotionEvent.getX();
          if (i < this.mPaddingLeft)
          {
            f = 0.0F;
            this.mSeekFactor = f;
            dispatchSeekEvent();
            this.mOnTouchThumbOffset = 0;
          }
        }
        while (true)
        {
          this.mInTouchMode = true;
          startTrackingTouch();
          trackTouchEvent(paramMotionEvent);
          return true;
          if (i > getWidth() - this.mPaddingRight)
          {
            f = 1.0F;
            (getWidth() - this.mPaddingRight);
            break;
          }
          f = (i - this.mPaddingLeft) / getWidth() - this.mPaddingLeft - this.mPaddingRight;
          break;
          this.mOnTouchThumbOffset = ((int)(this.mThumbRect.centerX() - this.mLastMotionX));
          this.mDownTouchX = this.mLastMotionX;
        }
      case 2:
        if (this.mIsDragging)
        {
          trackTouchEvent(paramMotionEvent);
          return true;
        }
        break;
      case 1:
      case 3:
      }
    }
    while (Math.abs(this.mLastMotionX - this.mDownTouchX) <= this.mDragSlop);
    this.mIsDragging = true;
    trackTouchEvent(paramMotionEvent);
    return true;
    if (this.mIsDragging)
      dispatchSeekEvent();
    while (true)
    {
      stopTrackingTouch();
      this.mIsDragging = false;
      return true;
      dispatchClickEvent();
    }
    if (this.mIsDragging)
      dispatchSeekEvent();
    stopTrackingTouch();
    this.mIsDragging = false;
    return true;
  }

  public void initProgress(int paramInt)
  {
    this.mProgress = paramInt;
    int i = getMax();
    float f1;
    float f2;
    if (i <= 0)
    {
      f1 = 0.0F;
      boolean bool = f1 < 0.0F;
      f2 = 0.0F;
      if (!bool)
        break label83;
    }
    while (true)
    {
      int j = (int)(f2 * getWidth());
      this.mThumbRect.offsetTo(j - this.mThumbRect.width() / 2, this.mThumbRect.top);
      invalidateElement();
      return;
      f1 = this.mProgress / i;
      break;
      label83: if (f1 > 1.0F)
        f2 = 1.0F;
      else
        f2 = f1;
    }
  }

  protected void onDrawElement(Canvas paramCanvas)
  {
    drawProgressBg(paramCanvas);
    drawThumb(paramCanvas);
  }

  protected void onMeasureElement(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
  }

  public void setMax(int paramInt)
  {
    if (this.mMaxLength != paramInt)
    {
      this.mMaxLength = paramInt;
      invalidateElement();
    }
  }

  public void setProgress(int paramInt, boolean paramBoolean)
  {
    if (paramBoolean)
    {
      this.mProgress = paramInt;
      notifyProgressChange(paramBoolean);
      invalidateElement();
    }
    while (this.mIsDragging)
      return;
    this.mProgress = paramInt;
    notifyProgressChange(paramBoolean);
    int i = getMax();
    float f1;
    float f2;
    if (i <= 0)
    {
      f1 = 0.0F;
      boolean bool = f1 < 0.0F;
      f2 = 0.0F;
      if (!bool)
        break label117;
    }
    while (true)
    {
      int j = (int)(f2 * getWidth());
      this.mThumbRect.offsetTo(j - this.mThumbRect.width() / 2, this.mThumbRect.top);
      invalidateElement();
      return;
      f1 = this.mProgress / i;
      break;
      label117: if (f1 > 1.0F)
        f2 = 1.0F;
      else
        f2 = f1;
    }
  }

  public void setSeekbarChangeListener(OnSeekBarChangeListener paramOnSeekBarChangeListener)
  {
    this.mSeekBarChangeListener = paramOnSeekBarChangeListener;
  }

  public static abstract interface OnSeekBarChangeListener
  {
    public abstract void onProgressChanged(AbsSeekBarElement paramAbsSeekBarElement, int paramInt, boolean paramBoolean);

    public abstract void onSeek(AbsSeekBarElement paramAbsSeekBarElement, float paramFloat);

    public abstract void onStartTrackingTouch(AbsSeekBarElement paramAbsSeekBarElement);

    public abstract void onStopTrackingTouch(AbsSeekBarElement paramAbsSeekBarElement);

    public abstract void onThumbClick(AbsSeekBarElement paramAbsSeekBarElement);
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.qtradio.view.playview.AbsSeekBarElement
 * JD-Core Version:    0.6.2
 */