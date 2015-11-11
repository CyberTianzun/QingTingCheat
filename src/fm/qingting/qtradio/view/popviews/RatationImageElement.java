package fm.qingting.qtradio.view.popviews;

import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Handler;
import android.view.animation.LinearInterpolator;
import fm.qingting.framework.utils.BitmapResourceCache;
import fm.qingting.framework.view.ViewElement;
import fm.qingting.qtradio.manager.QtApiLevelManager;

class RatationImageElement extends ViewElement
{
  private static final int TIME_STAMP = 150;
  private ValueAnimator mAnimator;
  private int mCenterX;
  private int mCenterY;
  private int mDegree;
  private Handler mHandler = new Handler();
  private int mRadiusX;
  private int mRadiusY;
  private Rect mRect = new Rect();
  private Runnable mRunnable = new Runnable()
  {
    public void run()
    {
      int i = RatationImageElement.this.mDegree;
      RatationImageElement.access$002(RatationImageElement.this, (360 + (-10 + RatationImageElement.this.mDegree)) % 360);
      RatationImageElement.this.startAnimation(i, RatationImageElement.this.mDegree);
      RatationImageElement.this.mHandler.postDelayed(RatationImageElement.this.mRunnable, 150L);
    }
  };
  private Bitmap mThumbBitmap;
  private int mThumbCenterX;
  private int mThumbCenterY;
  private int mThumbRadius;

  public RatationImageElement(Context paramContext)
  {
    super(paramContext);
    this.mMeasureSpec = 0;
    if (QtApiLevelManager.isApiLevelSupported(11))
    {
      this.mAnimator = new ValueAnimator();
      this.mAnimator.setDuration(150L);
      this.mAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener()
      {
        public void onAnimationUpdate(ValueAnimator paramAnonymousValueAnimator)
        {
          RatationImageElement.access$002(RatationImageElement.this, ((Float)paramAnonymousValueAnimator.getAnimatedValue()).intValue());
          RatationImageElement.this.invalidateAll();
        }
      });
      this.mAnimator.setInterpolator(new LinearInterpolator());
    }
    startRunnable();
  }

  private void drawImage(Canvas paramCanvas)
  {
    if (this.mThumbBitmap == null)
      return;
    this.mThumbCenterX = ((int)(this.mCenterX + this.mRadiusX * Math.cos(3.141592653589793D * this.mDegree / 180.0D)));
    this.mThumbCenterY = ((int)(this.mCenterY - this.mRadiusY * Math.sin(3.141592653589793D * this.mDegree / 180.0D)));
    this.mRect.set(this.mThumbCenterX - this.mThumbRadius, this.mThumbCenterY - this.mThumbRadius, this.mThumbCenterX + this.mThumbRadius, this.mThumbCenterY + this.mThumbRadius);
    paramCanvas.drawBitmap(this.mThumbBitmap, null, this.mRect, new Paint());
  }

  private void startAnimation(float paramFloat1, float paramFloat2)
  {
    if (paramFloat1 < paramFloat2)
    {
      paramFloat1 = 360.0F;
      paramFloat2 = 350.0F;
    }
    if (this.mAnimator != null)
    {
      this.mAnimator.setFloatValues(new float[] { paramFloat1, paramFloat2 });
      this.mAnimator.start();
      return;
    }
    this.mDegree = ((int)paramFloat2);
    invalidateAll();
  }

  protected void onDrawElement(Canvas paramCanvas)
  {
    paramCanvas.save();
    drawImage(paramCanvas);
    paramCanvas.restore();
  }

  protected void onMeasureElement(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    this.mCenterX = ((paramInt1 + paramInt3) / 2);
    this.mCenterY = ((paramInt2 + paramInt4) / 2);
    this.mRadiusX = ((paramInt3 - paramInt1) / 2);
    this.mRadiusY = ((paramInt4 - paramInt2) / 2);
  }

  public void setThumbRadius(int paramInt)
  {
    this.mThumbRadius = paramInt;
  }

  public void setThumbResource(int paramInt)
  {
    this.mThumbBitmap = BitmapResourceCache.getInstance().getResourceCacheByParent(getContext().getResources(), this.mOwnerId, paramInt);
  }

  public int setVisible(int paramInt)
  {
    if (paramInt != super.getVisiblity())
    {
      super.setVisible(paramInt);
      if (paramInt == 0)
        startRunnable();
    }
    else
    {
      return paramInt;
    }
    stopRunnable();
    return paramInt;
  }

  public void startRunnable()
  {
    this.mDegree = 80;
    this.mHandler.removeCallbacks(this.mRunnable);
    this.mHandler.post(this.mRunnable);
  }

  public void stopRunnable()
  {
    this.mHandler.removeCallbacks(this.mRunnable);
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.qtradio.view.popviews.RatationImageElement
 * JD-Core Version:    0.6.2
 */