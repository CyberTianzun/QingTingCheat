package fm.qingting.qtradio.view.personalcenter.clock;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.DrawFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.text.TextPaint;
import android.view.MotionEvent;
import android.view.View.MeasureSpec;
import android.view.ViewConfiguration;
import android.view.animation.LinearInterpolator;
import fm.qingting.framework.tween.FrameTween;
import fm.qingting.framework.tween.FrameTween.SyncType;
import fm.qingting.framework.tween.IMotionHandler;
import fm.qingting.framework.tween.MotionController;
import fm.qingting.framework.tween.TweenProperty;
import fm.qingting.framework.tween.easing.Quad.EaseOut;
import fm.qingting.framework.view.ViewImpl;
import fm.qingting.framework.view.ViewLayout;
import fm.qingting.qtradio.fm.PlayerAgent;
import fm.qingting.qtradio.manager.QtApiLevelManager;
import fm.qingting.qtradio.manager.SkinManager;
import fm.qingting.qtradio.model.InfoManager;
import fm.qingting.qtradio.model.Node;
import fm.qingting.qtradio.model.RootNode;
import fm.qingting.qtradio.model.RootNode.FromType;
import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class TimePickView extends ViewImpl
  implements IMotionHandler
{
  private final int ARC = 10;
  private int arc = 10;
  private final ViewLayout channelLayout = ViewLayout.createViewLayoutWithBoundsLT(90, 67, 360, 496, 210, 0, ViewLayout.LT | ViewLayout.SLT | ViewLayout.CW);
  private DrawFilter filter = SkinManager.getInstance().getDrawFilter();
  private final ViewLayout glassLayout = ViewLayout.createViewLayoutWithBoundsLT(360, 105, 360, 496, 0, 0, ViewLayout.LT | ViewLayout.SLT | ViewLayout.CW);
  private final ViewLayout itemLayout = ViewLayout.createViewLayoutWithBoundsLT(360, 105, 360, 496, 0, 0, ViewLayout.LT | ViewLayout.SLT | ViewLayout.CW);
  private final ViewLayout lineLayout = this.standardLayout.createChildLT(360, 1, 0, 0, ViewLayout.SCALE_FLAG_SLTCW);
  private AnimationState mAnimationState;
  private Object mAnimator;
  private float mCenterIndex = 0.0F;
  private int mCenterY = 0;
  float[] mDstR = new float[8];
  private RectF mDstRect = new RectF();
  private Paint mGlassPaint = new Paint();
  private Rect mGlassRect = new Rect();
  private boolean mInTouchMode = false;
  private float mInitPosition = 0.0F;
  private float mLastMotionY = 0.0F;
  private long mLastMovementTime = 0L;
  private float mLastScrollPosition = 0.0F;
  private float mLastTwoMoveEventDistance = 0.0F;
  private long mLastTwoMoveEventInterval = 0L;
  private Paint mLinePaint = new Paint();
  private Matrix mMatrix = new Matrix();
  private float mMinimumFlingVelocity;
  private TextPaint mNormalTextPaint = new TextPaint();
  private Paint mPaint = new Paint();
  private float mScrollPosition = 0.0F;
  private int mSelectedIndex = 0;
  private Paint mSelectedTextPaint = new Paint();
  float[] mSrcR = new float[8];
  private Rect mSrcRect = new Rect();
  private TimeType mTimeType = TimeType.Minute;
  private float mTouchedIndex = 0.0F;
  private Paint mUnitPaint = new Paint();
  private long mUpEventMoveEventInterval = 0L;
  private MotionController motionController;
  private final ViewLayout standardLayout = ViewLayout.createViewLayoutWithBoundsLT(360, 496, 360, 496, 0, 0, ViewLayout.FILL);
  private Rect textBound = new Rect();
  private Map<Integer, SoftReference<Bitmap>> textCaches = new HashMap();

  public TimePickView(Context paramContext)
  {
    super(paramContext);
    this.mGlassPaint.setColor(-13287874);
    this.mUnitPaint.setColor(SkinManager.getTextColorHighlight());
    this.mLinePaint.setColor(654311423);
    this.mNormalTextPaint.setColor(SkinManager.getTextColorSubInfo());
    this.mNormalTextPaint.setAntiAlias(true);
    ColorMatrixColorFilter localColorMatrixColorFilter = new ColorMatrixColorFilter(new float[] { 0.0F, 0.0F, 0.0F, 0.0F, 245.0F, 0.0F, 0.0F, 0.0F, 0.0F, 53.0F, 0.0F, 0.0F, 0.0F, 0.0F, 45.0F, 0.0F, 0.0F, 0.0F, 1.0F, 0.0F });
    this.mSelectedTextPaint.setColorFilter(localColorMatrixColorFilter);
    init();
    this.mMinimumFlingVelocity = ViewConfiguration.get(paramContext).getScaledMinimumFlingVelocity();
  }

  @TargetApi(11)
  private void cancelAnimation()
  {
    if (QtApiLevelManager.isApiLevelSupported(11))
    {
      ((ValueAnimator)this.mAnimator).cancel();
      return;
    }
    FrameTween.cancel(this);
  }

  private void clearAllTextCaches()
  {
    if (this.textCaches == null)
      return;
    Iterator localIterator = this.textCaches.keySet().iterator();
    while (localIterator.hasNext())
    {
      int i = ((Integer)localIterator.next()).intValue();
      Bitmap localBitmap = (Bitmap)((SoftReference)this.textCaches.get(Integer.valueOf(i))).get();
      if ((localBitmap != null) && (!localBitmap.isRecycled()))
        localBitmap.recycle();
    }
    this.textCaches.clear();
  }

  private void drawChannels(Canvas paramCanvas)
  {
    int i = paramCanvas.save();
    int j = Math.round(this.mCenterIndex);
    float f1 = this.mCenterY - (this.mCenterIndex - j) * this.itemLayout.height;
    int k = 0;
    float f2 = f1;
    for (int m = j; f2 > 0.0F; m--)
    {
      float f5 = (float)(1.0D - 2.0D * Math.pow((f2 - this.mCenterY) / this.standardLayout.height, 2.0D));
      (f2 - f5 * this.itemLayout.height / 2.0F);
      if (k == 0)
      {
        (f2 + f5 * this.itemLayout.height / 2.0F);
        k = 1;
      }
      f2 -= drawItemBitmapBottom(paramCanvas, m, f5, f2);
    }
    int n = j + 1;
    float f3 = this.mCenterY - (this.mCenterIndex - j) * this.itemLayout.height + this.itemLayout.height;
    while (f3 < this.standardLayout.height)
    {
      float f4 = (float)(1.0D - 2.0D * Math.pow((f3 - this.mCenterY) / this.standardLayout.height, 2.0D));
      (f3 + f4 * this.itemLayout.height / 2.0F);
      f3 += drawItemBitmapTop(paramCanvas, n, f4, f3);
      n++;
    }
    paramCanvas.restoreToCount(i);
  }

  private void drawGlass(Canvas paramCanvas)
  {
    SkinManager.getInstance().drawHorizontalLine(paramCanvas, 0, this.standardLayout.width, this.mGlassRect.top - this.lineLayout.height, this.lineLayout.height);
    SkinManager.getInstance().drawHorizontalLine(paramCanvas, 0, this.standardLayout.width, this.mGlassRect.bottom, this.lineLayout.height);
    String str = this.mTimeType.getName();
    this.mUnitPaint.getTextBounds(str, 0, str.length(), this.textBound);
    if (this.mTimeType == TimeType.Hour);
    for (int i = this.channelLayout.leftMargin + this.channelLayout.width; ; i = this.standardLayout.width - this.channelLayout.leftMargin)
    {
      paramCanvas.drawText(str, i, this.mCenterY + this.textBound.height() / 2, this.mUnitPaint);
      return;
    }
  }

  private float drawItemBitmapBottom(Canvas paramCanvas, int paramInt, float paramFloat1, float paramFloat2)
  {
    float f1 = paramFloat1 * this.itemLayout.height;
    Bitmap localBitmap = getTextCache(paramInt);
    if (localBitmap == null)
      return f1;
    float f2 = paramFloat1 * localBitmap.getHeight();
    float f3 = this.mCenterY - this.glassLayout.height / 2.0F;
    float f4 = this.mCenterY + this.glassLayout.height / 2.0F;
    if ((paramFloat2 + f2 / 2.0F > f4) && (paramFloat2 - f2 / 2.0F < f4))
    {
      drawReflectionTextBottom(paramCanvas, localBitmap, f4 - paramFloat2 + f2 / 2.0F, f2, paramFloat2);
      return f1;
    }
    if ((paramFloat2 + f2 / 2.0F > f3) && (paramFloat2 - f2 / 2.0F < f3))
    {
      drawReflectionTextTop(paramCanvas, localBitmap, f3 - paramFloat2 + f2 / 2.0F, f2, paramFloat2);
      return f1;
    }
    if ((paramFloat2 - f2 / 2.0F > f3) && (paramFloat2 + f2 / 2.0F < f4))
    {
      drawSelectedText(paramCanvas, localBitmap, paramFloat1 * this.itemLayout.height, paramFloat2);
      return f1;
    }
    int i = getLeftMargin();
    float[] arrayOfFloat1 = this.mSrcR;
    float[] arrayOfFloat2 = new float[8];
    arrayOfFloat2[0] = 0.0F;
    arrayOfFloat2[1] = 0.0F;
    arrayOfFloat2[2] = localBitmap.getWidth();
    arrayOfFloat2[3] = 0.0F;
    arrayOfFloat2[4] = localBitmap.getWidth();
    arrayOfFloat2[5] = localBitmap.getHeight();
    arrayOfFloat2[6] = 0.0F;
    arrayOfFloat2[7] = localBitmap.getHeight();
    setArrayValue(arrayOfFloat1, arrayOfFloat2);
    float[] arrayOfFloat3 = this.mDstR;
    float[] arrayOfFloat4 = new float[8];
    arrayOfFloat4[0] = (i + 5 * this.arc * (1.0F - paramFloat1));
    arrayOfFloat4[1] = (paramFloat2 - f2 / 2.0F);
    arrayOfFloat4[2] = (i + localBitmap.getWidth() - 5 * this.arc * (1.0F - paramFloat1));
    arrayOfFloat4[3] = (paramFloat2 - f2 / 2.0F);
    arrayOfFloat4[4] = (i + localBitmap.getWidth());
    arrayOfFloat4[5] = (paramFloat2 + f2 / 2.0F);
    arrayOfFloat4[6] = i;
    arrayOfFloat4[7] = (paramFloat2 + paramFloat1 * localBitmap.getHeight() / 2.0F);
    setArrayValue(arrayOfFloat3, arrayOfFloat4);
    this.mMatrix.setPolyToPoly(this.mSrcR, 0, this.mDstR, 0, this.mSrcR.length >> 1);
    try
    {
      paramCanvas.drawBitmap(localBitmap, this.mMatrix, this.mPaint);
      return f1;
    }
    catch (OutOfMemoryError localOutOfMemoryError)
    {
      while (true)
        localOutOfMemoryError.printStackTrace();
    }
  }

  private float drawItemBitmapTop(Canvas paramCanvas, int paramInt, float paramFloat1, float paramFloat2)
  {
    float f1 = paramFloat1 * this.itemLayout.height;
    Bitmap localBitmap = getTextCache(paramInt);
    if (localBitmap == null)
      return f1;
    float f2 = paramFloat1 * localBitmap.getHeight();
    float f3 = this.mCenterY + this.glassLayout.height / 2.0F;
    if ((paramFloat2 + f2 / 2.0F > f3) && (paramFloat2 - f2 / 2.0F < f3))
    {
      drawReflectionTextBottom(paramCanvas, localBitmap, f3 - paramFloat2 + f2 / 2.0F, f2, paramFloat2);
      return f1;
    }
    int i = getLeftMargin();
    float[] arrayOfFloat1 = this.mSrcR;
    float[] arrayOfFloat2 = new float[8];
    arrayOfFloat2[0] = 0.0F;
    arrayOfFloat2[1] = 0.0F;
    arrayOfFloat2[2] = localBitmap.getWidth();
    arrayOfFloat2[3] = 0.0F;
    arrayOfFloat2[4] = localBitmap.getWidth();
    arrayOfFloat2[5] = localBitmap.getHeight();
    arrayOfFloat2[6] = 0.0F;
    arrayOfFloat2[7] = localBitmap.getHeight();
    setArrayValue(arrayOfFloat1, arrayOfFloat2);
    float[] arrayOfFloat3 = this.mDstR;
    float[] arrayOfFloat4 = new float[8];
    arrayOfFloat4[0] = i;
    arrayOfFloat4[1] = (paramFloat2 - f2 / 2.0F);
    arrayOfFloat4[2] = (i + localBitmap.getWidth());
    arrayOfFloat4[3] = (paramFloat2 - f2 / 2.0F);
    arrayOfFloat4[4] = (i + localBitmap.getWidth() - 5 * this.arc * (1.0F - paramFloat1));
    arrayOfFloat4[5] = (paramFloat2 + f2 / 2.0F);
    arrayOfFloat4[6] = (i + 5 * this.arc * (1.0F - paramFloat1));
    arrayOfFloat4[7] = (paramFloat2 + f2 / 2.0F);
    setArrayValue(arrayOfFloat3, arrayOfFloat4);
    this.mMatrix.setPolyToPoly(this.mSrcR, 0, this.mDstR, 0, this.mSrcR.length >> 1);
    try
    {
      paramCanvas.drawBitmap(localBitmap, this.mMatrix, this.mPaint);
      return f1;
    }
    catch (OutOfMemoryError localOutOfMemoryError)
    {
      while (true)
        localOutOfMemoryError.printStackTrace();
    }
  }

  private float drawReflectionTextBottom(Canvas paramCanvas, Bitmap paramBitmap, float paramFloat1, float paramFloat2, float paramFloat3)
  {
    int i = getLeftMargin();
    this.mSrcRect.set(0, 0, paramBitmap.getWidth(), (int)(paramFloat1 * paramBitmap.getHeight() / paramFloat2));
    this.mDstRect.set(i, paramFloat3 - paramFloat2 / 2.0F, i + paramBitmap.getWidth(), paramFloat1 + (paramFloat3 - paramFloat2 / 2.0F));
    paramCanvas.drawBitmap(paramBitmap, this.mSrcRect, this.mDstRect, this.mSelectedTextPaint);
    this.mSrcRect.set(0, (int)(paramFloat1 * paramBitmap.getHeight() / paramFloat2), paramBitmap.getWidth(), paramBitmap.getHeight());
    this.mDstRect.set(i, paramFloat1 + (paramFloat3 - paramFloat2 / 2.0F), i + paramBitmap.getWidth(), paramFloat3 + paramFloat2 / 2.0F);
    paramCanvas.drawBitmap(paramBitmap, this.mSrcRect, this.mDstRect, this.mPaint);
    return paramFloat2;
  }

  private float drawReflectionTextTop(Canvas paramCanvas, Bitmap paramBitmap, float paramFloat1, float paramFloat2, float paramFloat3)
  {
    int i = getLeftMargin();
    this.mSrcRect.set(0, 0, paramBitmap.getWidth(), (int)(paramFloat1 * paramBitmap.getHeight() / paramFloat2));
    this.mDstRect.set(i, paramFloat3 - paramFloat2 / 2.0F, i + paramBitmap.getWidth(), paramFloat1 + (paramFloat3 - paramFloat2 / 2.0F));
    paramCanvas.drawBitmap(paramBitmap, this.mSrcRect, this.mDstRect, this.mPaint);
    this.mSrcRect.set(0, (int)(paramFloat1 * paramBitmap.getHeight() / paramFloat2), paramBitmap.getWidth(), paramBitmap.getHeight());
    this.mDstRect.set(i, paramFloat1 + (paramFloat3 - paramFloat2 / 2.0F), i + paramBitmap.getWidth(), paramFloat3 + paramFloat2 / 2.0F);
    paramCanvas.drawBitmap(paramBitmap, this.mSrcRect, this.mDstRect, this.mSelectedTextPaint);
    return paramFloat2;
  }

  private float drawSelectedText(Canvas paramCanvas, Bitmap paramBitmap, float paramFloat1, float paramFloat2)
  {
    int i = getLeftMargin();
    this.mSrcRect.set(0, 0, paramBitmap.getWidth(), paramBitmap.getHeight());
    this.mDstRect.set(i, paramFloat2 - paramBitmap.getHeight() / 2.0F, i + paramBitmap.getWidth(), paramFloat2 + paramBitmap.getHeight() / 2.0F);
    paramCanvas.drawBitmap(paramBitmap, this.mSrcRect, this.mDstRect, this.mSelectedTextPaint);
    return paramFloat1;
  }

  private void generateRect()
  {
    this.mGlassRect = new Rect(0, this.mCenterY - this.glassLayout.height / 2, this.glassLayout.width, this.mCenterY + this.glassLayout.height / 2);
  }

  private int getAnimationDuration(float paramFloat)
  {
    return (int)(200.0D * Math.sqrt(paramFloat));
  }

  private float getIndex()
  {
    this.mCenterIndex %= this.mTimeType.getTotal();
    if (this.mCenterIndex < 0.0F)
      this.mCenterIndex += this.mTimeType.getTotal();
    return this.mCenterIndex;
  }

  private int getLeftMargin()
  {
    if (this.mTimeType == TimeType.Hour)
      return this.channelLayout.leftMargin;
    return this.standardLayout.width - this.channelLayout.leftMargin - this.channelLayout.width;
  }

  private Bitmap getTextCache(int paramInt)
  {
    int i = paramInt % this.mTimeType.getTotal();
    if (i < 0);
    for (int j = i + this.mTimeType.getTotal(); ; j = i)
    {
      int k = this.channelLayout.width;
      if ((this.textCaches.get(Integer.valueOf(j)) != null) && (((SoftReference)this.textCaches.get(Integer.valueOf(j))).get() != null))
        return (Bitmap)((SoftReference)this.textCaches.get(Integer.valueOf(j))).get();
      Object[] arrayOfObject = new Object[1];
      arrayOfObject[0] = Integer.valueOf(j);
      String str = String.format("%02d", arrayOfObject);
      this.mNormalTextPaint.getTextBounds(str, 0, str.length(), this.textBound);
      if ((this.textBound.width() <= 0) || (this.textBound.height() <= 0))
        return null;
      try
      {
        Bitmap localBitmap = Bitmap.createBitmap(k, this.channelLayout.height, Bitmap.Config.ARGB_4444);
        new Canvas(localBitmap).drawText(str, (k - this.textBound.width()) / 2.0F, (this.channelLayout.height - this.textBound.top - this.textBound.bottom) / 2.0F, this.mNormalTextPaint);
        this.textCaches.put(Integer.valueOf(j), new SoftReference(localBitmap));
        return localBitmap;
      }
      catch (OutOfMemoryError localOutOfMemoryError)
      {
        return null;
      }
    }
  }

  @TargetApi(11)
  private void init()
  {
    if (QtApiLevelManager.isApiLevelSupported(11))
    {
      this.mAnimator = new ValueAnimator();
      ((ValueAnimator)this.mAnimator).setInterpolator(new LinearInterpolator());
      ((ValueAnimator)this.mAnimator).addUpdateListener(new ValueAnimator.AnimatorUpdateListener()
      {
        public void onAnimationUpdate(ValueAnimator paramAnonymousValueAnimator)
        {
          TimePickView.this.setIndex(((Float)paramAnonymousValueAnimator.getAnimatedValue()).floatValue());
        }
      });
      ((ValueAnimator)this.mAnimator).addListener(new Animator.AnimatorListener()
      {
        public void onAnimationCancel(Animator paramAnonymousAnimator)
        {
          TimePickView.access$102(TimePickView.this, TimePickView.AnimationState.STOP);
        }

        public void onAnimationEnd(Animator paramAnonymousAnimator)
        {
          TimePickView.access$102(TimePickView.this, TimePickView.AnimationState.STOP);
        }

        public void onAnimationRepeat(Animator paramAnonymousAnimator)
        {
        }

        public void onAnimationStart(Animator paramAnonymousAnimator)
        {
        }
      });
      return;
    }
    this.motionController = new MotionController(this);
  }

  private void setArrayValue(float[] paramArrayOfFloat1, float[] paramArrayOfFloat2)
  {
    if ((paramArrayOfFloat2 == null) || (paramArrayOfFloat2.length < paramArrayOfFloat1.length));
    while (true)
    {
      return;
      for (int i = 0; i < paramArrayOfFloat1.length; i++)
        paramArrayOfFloat1[i] = paramArrayOfFloat2[i];
    }
  }

  private void setIndex(float paramFloat)
  {
    this.mScrollPosition = (this.mLastScrollPosition + (this.mTouchedIndex - paramFloat) * this.itemLayout.height);
    int i = this.mTimeType.getTotal();
    this.mCenterIndex = (paramFloat % i);
    if (this.mCenterIndex < 0.0F)
      this.mCenterIndex += i;
    invalidate();
  }

  @TargetApi(11)
  private void startAnimationTo(float paramFloat, boolean paramBoolean)
  {
    this.mAnimationState = AnimationState.RUNNING;
    if (QtApiLevelManager.isApiLevelSupported(11))
    {
      int i = getAnimationDuration(Math.abs(this.mCenterIndex - paramFloat));
      ValueAnimator localValueAnimator = (ValueAnimator)this.mAnimator;
      float[] arrayOfFloat = new float[2];
      arrayOfFloat[0] = this.mCenterIndex;
      arrayOfFloat[1] = paramFloat;
      localValueAnimator.setFloatValues(arrayOfFloat);
      ((ValueAnimator)this.mAnimator).setDuration(i);
      ((ValueAnimator)this.mAnimator).start();
      return;
    }
    ArrayList localArrayList = new ArrayList();
    localArrayList.add(new TweenProperty("position", this.mCenterIndex, paramFloat, 10.0F, new Quad.EaseOut()));
    FrameTween.to(this.motionController, this.motionController, localArrayList, FrameTween.SyncType.ASYNC);
  }

  public Object getValue(String paramString, Object paramObject)
  {
    if (paramString.equalsIgnoreCase("selectedIndex"))
    {
      int i = this.mSelectedIndex;
      int j = this.mTimeType.getTotal();
      int k = i % j;
      if (k < 0)
        k += j;
      return Integer.valueOf(k);
    }
    return null;
  }

  protected void onDraw(Canvas paramCanvas)
  {
    paramCanvas.setDrawFilter(this.filter);
    paramCanvas.save();
    drawGlass(paramCanvas);
    drawChannels(paramCanvas);
    paramCanvas.restore();
  }

  protected void onMeasure(int paramInt1, int paramInt2)
  {
    int i = View.MeasureSpec.getSize(paramInt1);
    int j = View.MeasureSpec.getSize(paramInt2);
    this.standardLayout.scaleToBounds(i, j);
    this.glassLayout.scaleToBounds(this.standardLayout);
    this.itemLayout.scaleToBounds(this.standardLayout);
    this.channelLayout.scaleToBounds(this.standardLayout);
    this.lineLayout.scaleToBounds(this.standardLayout);
    this.mNormalTextPaint.setTextSize(0.7F * this.channelLayout.height);
    this.mUnitPaint.setTextSize(0.4F * this.channelLayout.height);
    this.mCenterY = (this.standardLayout.height / 2);
    this.mLinePaint.setStrokeWidth(this.lineLayout.height);
    generateRect();
    setMeasuredDimension(this.standardLayout.width, this.standardLayout.height);
  }

  public void onMotionCancel(MotionController paramMotionController)
  {
    this.mAnimationState = AnimationState.STOP;
  }

  public void onMotionComplete(MotionController paramMotionController)
  {
    this.mAnimationState = AnimationState.STOP;
  }

  public void onMotionProgress(MotionController paramMotionController, float paramFloat1, float paramFloat2)
  {
    setIndex(paramFloat1);
  }

  public void onMotionStart(MotionController paramMotionController)
  {
  }

  public void onTargetChange(MotionController paramMotionController, float paramFloat)
  {
  }

  public boolean onTouchEvent(MotionEvent paramMotionEvent)
  {
    if ((paramMotionEvent.getAction() != 0) && (!this.mInTouchMode));
    do
    {
      return true;
      switch (paramMotionEvent.getAction())
      {
      default:
        return true;
      case 0:
        if (this.mAnimationState == AnimationState.RUNNING)
          cancelAnimation();
        this.mInTouchMode = true;
        this.mLastScrollPosition = this.mScrollPosition;
        this.mTouchedIndex = this.mCenterIndex;
        this.mInitPosition = paramMotionEvent.getY();
        this.mLastMovementTime = paramMotionEvent.getEventTime();
        this.mLastMotionY = paramMotionEvent.getY();
        return true;
      case 2:
      case 1:
      case 3:
      }
    }
    while (!this.mInTouchMode);
    paramMotionEvent.getX();
    paramMotionEvent.getY();
    long l = paramMotionEvent.getEventTime();
    this.mLastTwoMoveEventInterval = (l - this.mLastMovementTime);
    this.mLastMovementTime = l;
    float f6 = paramMotionEvent.getY();
    this.mLastTwoMoveEventDistance = (f6 - this.mLastMotionY);
    this.mLastMotionY = f6;
    float f7 = this.mTouchedIndex - (paramMotionEvent.getY() - this.mInitPosition) / this.itemLayout.height;
    setIndex(f7);
    this.mSelectedIndex = Math.round(f7);
    return true;
    this.mUpEventMoveEventInterval = (paramMotionEvent.getEventTime() - this.mLastMovementTime);
    if ((this.mUpEventMoveEventInterval < ViewConfiguration.getTapTimeout()) && (this.mLastTwoMoveEventInterval > 0L))
    {
      float f3 = 1000.0F * this.mLastTwoMoveEventDistance / (float)this.mLastTwoMoveEventInterval;
      if (Math.abs(f3) > this.mMinimumFlingVelocity)
      {
        int i = (int)Math.abs(f3 / this.standardLayout.height);
        if (f3 > 0.0F)
        {
          float f5 = Math.round(getIndex() - i);
          this.mSelectedIndex = ((int)f5);
          Node localNode2 = (Node)getValue("selectedChannel", null);
          if (localNode2 != null)
            PlayerAgent.getInstance().setSource(localNode2);
          InfoManager.getInstance().root().setFromType(RootNode.FromType.WHEEL);
          startAnimationTo(f5, true);
        }
        while (true)
        {
          this.mInTouchMode = false;
          return true;
          if (f3 < 0.0F)
          {
            float f4 = Math.round(getIndex() + i);
            this.mSelectedIndex = ((int)f4);
            Node localNode1 = (Node)getValue("selectedChannel", null);
            if (localNode1 != null)
              PlayerAgent.getInstance().setSource(localNode1);
            InfoManager.getInstance().root().setFromType(RootNode.FromType.WHEEL);
            startAnimationTo(f4, true);
          }
        }
      }
    }
    float f1 = getIndex();
    if (f1 == Math.round(f1))
    {
      this.mAnimationState = AnimationState.STOP;
      return true;
    }
    float f2 = Math.round(f1);
    this.mSelectedIndex = ((int)f2);
    startAnimationTo(f2, true);
    this.mInTouchMode = false;
    return true;
    invalidate();
    return true;
  }

  public void update(String paramString, Object paramObject)
  {
    if (paramString.equalsIgnoreCase("setTimeType"))
    {
      this.mTimeType = ((TimeType)paramObject);
      switch (3.$SwitchMap$fm$qingting$qtradio$view$personalcenter$clock$TimePickView$TimeType[this.mTimeType.ordinal()])
      {
      default:
      case 1:
      case 2:
      }
    }
    while (!paramString.equalsIgnoreCase("setTime"))
    {
      return;
      this.mCenterIndex = 7.0F;
      return;
      this.mCenterIndex = 0.0F;
      return;
    }
    this.mSelectedIndex = ((Integer)paramObject).intValue();
    this.mCenterIndex = this.mSelectedIndex;
    invalidate();
  }

  private static enum AnimationState
  {
    static
    {
      AnimationState[] arrayOfAnimationState = new AnimationState[2];
      arrayOfAnimationState[0] = RUNNING;
      arrayOfAnimationState[1] = STOP;
    }
  }

  public static enum TimeType
  {
    static
    {
      TimeType[] arrayOfTimeType = new TimeType[2];
      arrayOfTimeType[0] = Hour;
      arrayOfTimeType[1] = Minute;
    }

    public String getName()
    {
      String str = "时";
      if (this == Minute)
        str = "分";
      return str;
    }

    public int getTotal()
    {
      int i = 24;
      if (this == Minute)
        i = 60;
      return i;
    }
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.qtradio.view.personalcenter.clock.TimePickView
 * JD-Core Version:    0.6.2
 */