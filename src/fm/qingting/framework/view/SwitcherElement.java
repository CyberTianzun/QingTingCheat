package fm.qingting.framework.view;

import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Build.VERSION;
import android.view.MotionEvent;
import fm.qingting.framework.utils.BitmapResourceCache;

public class SwitcherElement extends ViewElement
{
  private Object mAnimator;
  private final Rect mBgRect = new Rect();
  private boolean mHasMoved = false;
  private float mIconOffset = 0.0F;
  private final Rect mIconRect = new Rect();
  private int mIconRes;
  private boolean mInDragMode = false;
  private boolean mInTouchMode = false;
  private boolean mIsApiLevelSupport;
  private boolean mIsOn = false;
  private float mLastMotionX = 0.0F;
  private float mLastMotionY = 0.0F;
  private int mOffBgRes;
  private int mOnBgRes;
  private final Paint mPaint = new Paint();
  private OnSwitchChangeListener mSwitchListener;
  private float mSwitchTouchDownOffset = this.mIconOffset;
  private float mTouchDownX = this.mLastMotionX;

  public SwitcherElement(Context paramContext)
  {
    super(paramContext);
    int i = Build.VERSION.SDK_INT;
    boolean bool = false;
    if (i >= 11)
      bool = true;
    this.mIsApiLevelSupport = bool;
    init();
  }

  private void drawSwitcher(Canvas paramCanvas)
  {
    if ((this.mTranslationX != 0) || (this.mTranslationY != 0))
      this.mBgRect.offset(this.mTranslationX, this.mTranslationY);
    BitmapResourceCache localBitmapResourceCache = BitmapResourceCache.getInstance();
    Resources localResources = getContext().getResources();
    int i = this.mOwnerId;
    if (this.mIsOn);
    for (int j = this.mOnBgRes; ; j = this.mOffBgRes)
    {
      paramCanvas.drawBitmap(localBitmapResourceCache.getResourceCacheByParent(localResources, i, j), null, this.mBgRect, this.mPaint);
      if ((this.mTranslationX != 0) || (this.mTranslationY != 0))
        this.mBgRect.offset(-this.mTranslationX, -this.mTranslationY);
      Bitmap localBitmap = BitmapResourceCache.getInstance().getResourceCacheByParent(getContext().getResources(), this.mOwnerId, this.mIconRes);
      int k = (int)(this.mIconOffset * getMaxIconOffset());
      if ((this.mTranslationX != 0) || (this.mTranslationY != 0))
        this.mIconRect.offset(this.mTranslationX, this.mTranslationY);
      this.mIconRect.offset(k, 0);
      paramCanvas.drawBitmap(localBitmap, null, this.mIconRect, this.mPaint);
      this.mIconRect.offset(-k, 0);
      if ((this.mTranslationX != 0) || (this.mTranslationY != 0))
        this.mIconRect.offset(-this.mTranslationX, -this.mTranslationY);
      return;
    }
  }

  private int getMaxIconOffset()
  {
    return this.mBgRect.width() - this.mIconRect.width();
  }

  private void handleSwitchAction()
  {
    this.mInTouchMode = false;
    boolean bool1 = this.mIsOn;
    boolean bool2 = false;
    if (bool1);
    while (true)
    {
      this.mIsOn = bool2;
      if (this.mSwitchListener != null)
        this.mSwitchListener.onChanged(this.mIsOn);
      return;
      bool2 = true;
    }
  }

  @TargetApi(11)
  private void init()
  {
    if (this.mIsApiLevelSupport)
    {
      this.mAnimator = new ValueAnimator();
      ((ValueAnimator)this.mAnimator).setDuration(200L);
      ((ValueAnimator)this.mAnimator).addUpdateListener(new ValueAnimator.AnimatorUpdateListener()
      {
        public void onAnimationUpdate(ValueAnimator paramAnonymousValueAnimator)
        {
          SwitcherElement.this.setPosition(((Float)paramAnonymousValueAnimator.getAnimatedValue()).floatValue());
        }
      });
    }
  }

  @TargetApi(11)
  private boolean isDoingAnimation()
  {
    if (this.mIsApiLevelSupport)
      return ((ValueAnimator)this.mAnimator).isRunning();
    return false;
  }

  private void setPosition(float paramFloat)
  {
    this.mIconOffset = paramFloat;
    invalidateElement(this.mBgRect);
  }

  @TargetApi(11)
  private void startAnimation(float paramFloat)
  {
    if (this.mIsApiLevelSupport)
    {
      ValueAnimator localValueAnimator = (ValueAnimator)this.mAnimator;
      float[] arrayOfFloat = new float[2];
      arrayOfFloat[0] = this.mIconOffset;
      arrayOfFloat[1] = paramFloat;
      localValueAnimator.setFloatValues(arrayOfFloat);
      ((ValueAnimator)this.mAnimator).start();
      return;
    }
    this.mIconOffset = paramFloat;
    invalidateElement(this.mBgRect);
  }

  public boolean handleTouchEvent(MotionEvent paramMotionEvent)
  {
    boolean bool1 = true;
    if ((paramMotionEvent.getAction() != 0) && (!this.mInTouchMode))
      return false;
    this.mLastMotionX = (paramMotionEvent.getX() - this.mTranslationX);
    this.mLastMotionY = (paramMotionEvent.getY() - this.mTranslationY);
    if ((this.mInTouchMode) && (!this.mBgRect.contains((int)this.mLastMotionX, (int)this.mLastMotionY)))
    {
      if (this.mInDragMode)
      {
        if (!this.mHasMoved)
          if (this.mSwitchTouchDownOffset < 0.5F)
          {
            startAnimation(bool1);
            handleSwitchAction();
          }
        while (true)
        {
          this.mInTouchMode = false;
          return false;
          bool1 = false;
          break;
          if (this.mIconOffset < 0.5F)
          {
            startAnimation(0.0F);
            if (this.mSwitchTouchDownOffset > 0.5F)
              handleSwitchAction();
          }
          else
          {
            startAnimation(1.0F);
            if (this.mSwitchTouchDownOffset < 0.5F)
              handleSwitchAction();
          }
        }
      }
      if (this.mIconOffset < 0.5F);
      while (true)
      {
        startAnimation(bool1);
        handleSwitchAction();
        break;
        bool1 = false;
      }
    }
    switch (paramMotionEvent.getAction())
    {
    default:
    case 0:
    case 1:
    case 3:
    case 2:
    }
    while (true)
    {
      return bool1;
      if (!this.mBgRect.contains((int)this.mLastMotionX, (int)this.mLastMotionY))
      {
        this.mInTouchMode = false;
        return false;
      }
      this.mInTouchMode = bool1;
      this.mLastMotionX = paramMotionEvent.getX();
      this.mLastMotionY = paramMotionEvent.getY();
      this.mInTouchMode = bool1;
      this.mHasMoved = false;
      if (isDoingAnimation())
      {
        this.mInTouchMode = false;
        return false;
      }
      this.mInTouchMode = bool1;
      if ((this.mIconOffset < 0.5F) && (this.mLastMotionX < this.mBgRect.left + this.mIconRect.width()))
      {
        this.mInDragMode = bool1;
        this.mTouchDownX = this.mLastMotionX;
        this.mSwitchTouchDownOffset = this.mIconOffset;
      }
      else if ((this.mIconOffset > 0.5F) && (this.mLastMotionX > this.mBgRect.right - this.mIconRect.width()))
      {
        this.mTouchDownX = this.mLastMotionX;
        this.mInDragMode = bool1;
        this.mSwitchTouchDownOffset = this.mIconOffset;
      }
      else
      {
        this.mInDragMode = false;
        continue;
        if (this.mInDragMode)
        {
          if (!this.mHasMoved)
          {
            boolean bool5 = this.mSwitchTouchDownOffset < 0.5F;
            int m = 0;
            if (bool5)
              m = bool1;
            startAnimation(m);
            handleSwitchAction();
          }
          else if (this.mIconOffset < 0.5F)
          {
            startAnimation(0.0F);
            if (this.mSwitchTouchDownOffset > 0.5F)
              handleSwitchAction();
          }
          else
          {
            startAnimation(1.0F);
            if (this.mSwitchTouchDownOffset < 0.5F)
              handleSwitchAction();
          }
        }
        else
        {
          boolean bool4 = this.mIconOffset < 0.5F;
          int k = 0;
          if (bool4)
            k = bool1;
          startAnimation(k);
          handleSwitchAction();
          continue;
          if (this.mInDragMode)
          {
            if (!this.mHasMoved)
            {
              boolean bool3 = this.mSwitchTouchDownOffset < 0.5F;
              int j = 0;
              if (bool3)
                j = bool1;
              startAnimation(j);
              handleSwitchAction();
            }
            else if (this.mIconOffset < 0.5F)
            {
              startAnimation(0.0F);
              if (this.mSwitchTouchDownOffset > 0.5F)
                handleSwitchAction();
            }
            else
            {
              startAnimation(1.0F);
              if (this.mSwitchTouchDownOffset < 0.5F)
                handleSwitchAction();
            }
          }
          else
          {
            boolean bool2 = this.mIconOffset < 0.5F;
            int i = 0;
            if (bool2)
              i = bool1;
            startAnimation(i);
            handleSwitchAction();
            continue;
            this.mLastMotionX = paramMotionEvent.getX();
            this.mLastMotionY = paramMotionEvent.getY();
            if (this.mInDragMode)
            {
              float f = (this.mLastMotionX - this.mTouchDownX) / getMaxIconOffset();
              this.mIconOffset = (f + this.mSwitchTouchDownOffset);
              if ((!this.mHasMoved) && (f > 0.1F))
                this.mHasMoved = bool1;
              if (this.mIconOffset < 0.0F)
              {
                this.mIconOffset = 0.0F;
                invalidateElement(this.mBgRect);
              }
              else if (this.mIconOffset > 1.0F)
              {
                this.mIconOffset = 1.0F;
                invalidateElement(this.mBgRect);
              }
              else
              {
                invalidateElement(this.mBgRect);
              }
            }
          }
        }
      }
    }
  }

  protected void onDrawElement(Canvas paramCanvas)
  {
    paramCanvas.save();
    drawSwitcher(paramCanvas);
    paramCanvas.restore();
  }

  protected void onMeasureElement(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    this.mBgRect.set(paramInt1, paramInt2, paramInt3, paramInt4);
  }

  public void setAlpha(int paramInt)
  {
    this.mPaint.setAlpha(paramInt);
  }

  public void setBgRes(int paramInt1, int paramInt2)
  {
    this.mOnBgRes = paramInt1;
    this.mOffBgRes = paramInt2;
  }

  public void setIconRes(int paramInt)
  {
    this.mIconRes = paramInt;
  }

  public void setIconSize(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    this.mIconRect.set(paramInt1, paramInt2, paramInt3, paramInt4);
  }

  public void setSwitchChangeListener(OnSwitchChangeListener paramOnSwitchChangeListener)
  {
    this.mSwitchListener = paramOnSwitchChangeListener;
  }

  public void switchOff(boolean paramBoolean)
  {
    if (this.mIsOn)
    {
      this.mIsOn = false;
      if (paramBoolean)
        startAnimation(0.0F);
    }
    else
    {
      return;
    }
    this.mIconOffset = 0.0F;
    invalidateElement(this.mBgRect);
  }

  public void switchOn(boolean paramBoolean)
  {
    if (!this.mIsOn)
    {
      this.mIsOn = true;
      if (paramBoolean)
        startAnimation(1.0F);
    }
    else
    {
      return;
    }
    this.mIconOffset = 1.0F;
    invalidateElement(this.mBgRect);
  }

  public static abstract interface OnSwitchChangeListener
  {
    public abstract void onChanged(boolean paramBoolean);
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.framework.view.SwitcherElement
 * JD-Core Version:    0.6.2
 */