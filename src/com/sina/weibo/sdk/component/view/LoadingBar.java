package com.sina.weibo.sdk.component.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Handler;
import android.util.AttributeSet;
import android.widget.TextView;

public class LoadingBar extends TextView
{
  private static final int MAX_PROGRESS = 100;
  private Handler mHander;
  private Paint mPaint;
  private int mProgress;
  private int mProgressColor;
  private Runnable mRunnable = new Runnable()
  {
    public void run()
    {
      LoadingBar localLoadingBar = LoadingBar.this;
      localLoadingBar.mProgress = (1 + localLoadingBar.mProgress);
      LoadingBar.this.drawProgress(LoadingBar.this.mProgress);
    }
  };

  public LoadingBar(Context paramContext)
  {
    super(paramContext);
    init(paramContext);
  }

  public LoadingBar(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    init(paramContext);
  }

  public LoadingBar(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
    init(paramContext);
  }

  private Rect getRect()
  {
    int i = getLeft();
    int j = getTop();
    int k = getLeft() + (getRight() - getLeft()) * this.mProgress / 100;
    int m = getBottom();
    return new Rect(0, 0, k - i, m - j);
  }

  private void init(Context paramContext)
  {
    this.mHander = new Handler();
    this.mPaint = new Paint();
    initSkin();
  }

  public void drawProgress(int paramInt)
  {
    if (paramInt < 7)
      this.mHander.postDelayed(this.mRunnable, 70L);
    while (true)
    {
      invalidate();
      return;
      this.mHander.removeCallbacks(this.mRunnable);
      this.mProgress = paramInt;
    }
  }

  public void initSkin()
  {
    this.mProgressColor = -11693826;
  }

  protected void onDraw(Canvas paramCanvas)
  {
    super.onDraw(paramCanvas);
    this.mPaint.setColor(this.mProgressColor);
    paramCanvas.drawRect(getRect(), this.mPaint);
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.sina.weibo.sdk.component.view.LoadingBar
 * JD-Core Version:    0.6.2
 */