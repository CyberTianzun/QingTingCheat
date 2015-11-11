package fm.qingting.qtradio.view.virtualchannels;

import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.View.OnClickListener;
import fm.qingting.framework.utils.BitmapResourceCache;
import fm.qingting.framework.view.ViewImpl;
import fm.qingting.framework.view.ViewLayout;
import fm.qingting.qtradio.manager.QtApiLevelManager;
import fm.qingting.qtradio.manager.SkinManager;

public class ChannelDetailTagView extends ViewImpl
{
  private final ViewLayout arrowLayout = this.itemLayout.createChildLT(38, 20, 650, 24, ViewLayout.SCALE_FLAG_SLTCW);
  private final ViewLayout itemLayout = ViewLayout.createViewLayoutWithBoundsLT(720, 68, 720, 68, 0, 0, ViewLayout.SCALE_FLAG_SLTCW);
  private final ViewLayout labelLayout = this.itemLayout.createChildLT(5, 22, 18, 0, ViewLayout.SCALE_FLAG_SLTCW);
  private final ViewLayout lineLayout = this.itemLayout.createChildLT(720, 1, 12, 0, ViewLayout.SCALE_FLAG_SLTCW);
  private Rect mArrowRect = new Rect();
  private int mCnt = 0;
  private boolean mHasArrow = false;
  private boolean mHasBackground = true;
  private Paint mLinePaint = new Paint();
  private Paint mPaint = new Paint();
  private int mRotateDegree = 0;
  private Rect mTextBound = new Rect();
  private String mTitle;
  private Paint mTitlePaint = new Paint();
  private final ViewLayout titleLayout = this.itemLayout.createChildLT(720, 45, 30, 0, ViewLayout.SCALE_FLAG_SLTCW);

  public ChannelDetailTagView(Context paramContext)
  {
    super(paramContext);
    this.mTitlePaint.setColor(SkinManager.getTextColorSubInfo());
    this.mLinePaint.setColor(SkinManager.getDividerColor_new());
    setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        ChannelDetailTagView.this.toggleRotate();
        ChannelDetailTagView.this.dispatchActionEvent("expand", null);
      }
    });
  }

  private void drawArrow(Canvas paramCanvas)
  {
    if (!this.mHasArrow)
      return;
    Bitmap localBitmap = BitmapResourceCache.getInstance().getResourceCache(getResources(), this, 2130837709);
    int i = paramCanvas.save();
    paramCanvas.rotate(this.mRotateDegree, this.mArrowRect.centerX(), this.mArrowRect.centerY());
    paramCanvas.drawBitmap(localBitmap, null, this.mArrowRect, this.mPaint);
    paramCanvas.restoreToCount(i);
  }

  private void drawBg(Canvas paramCanvas)
  {
    if (this.mHasBackground)
    {
      int i = paramCanvas.save();
      paramCanvas.clipRect(0, 0, this.itemLayout.width, this.itemLayout.height);
      paramCanvas.drawColor(SkinManager.getBackgroundColor_item());
      paramCanvas.restoreToCount(i);
    }
  }

  private void drawLines(Canvas paramCanvas)
  {
    paramCanvas.drawLine(0.0F, 0.0F, this.itemLayout.width, 0.0F, this.mLinePaint);
    paramCanvas.drawLine(this.titleLayout.getLeft(), this.itemLayout.height - this.lineLayout.height, this.itemLayout.width, this.itemLayout.height - this.lineLayout.height, this.mLinePaint);
  }

  private void drawTitle(Canvas paramCanvas)
  {
    if (this.mTitle == null)
      return;
    if (this.mCnt > 0)
    {
      String str = this.mTitle + "(" + this.mCnt + ")";
      this.mTitlePaint.getTextBounds(str, 0, str.length(), this.mTextBound);
      paramCanvas.drawText(str, this.titleLayout.leftMargin, (this.itemLayout.height - this.mTextBound.top - this.mTextBound.bottom) / 2, this.mTitlePaint);
      return;
    }
    this.mTitlePaint.getTextBounds(this.mTitle, 0, this.mTitle.length(), this.mTextBound);
    paramCanvas.drawText(this.mTitle, this.titleLayout.leftMargin, (this.itemLayout.height - this.mTextBound.top - this.mTextBound.bottom) / 2, this.mTitlePaint);
  }

  @TargetApi(11)
  private void toggleRotate()
  {
    int i = 180;
    if (!this.mHasArrow)
      return;
    if (QtApiLevelManager.isApiLevelSupported(11))
    {
      int j;
      if ((this.mRotateDegree > i) || (this.mRotateDegree == 0))
      {
        j = i;
        i = 0;
      }
      while (true)
      {
        ValueAnimator localValueAnimator = ValueAnimator.ofInt(new int[] { i, j });
        localValueAnimator.setDuration(350L).addUpdateListener(new ValueAnimator.AnimatorUpdateListener()
        {
          public void onAnimationUpdate(ValueAnimator paramAnonymousValueAnimator)
          {
            ChannelDetailTagView.access$202(ChannelDetailTagView.this, ((Integer)paramAnonymousValueAnimator.getAnimatedValue()).intValue());
            ChannelDetailTagView.this.invalidate();
          }
        });
        localValueAnimator.start();
        return;
        j = 360;
      }
    }
    if (this.mRotateDegree == 0);
    for (this.mRotateDegree = i; ; this.mRotateDegree = 0)
    {
      invalidate();
      return;
    }
  }

  public void close(boolean paramBoolean)
  {
    BitmapResourceCache.getInstance().clearResourceCacheOfOne(this, 0);
    super.close(paramBoolean);
  }

  protected void onDraw(Canvas paramCanvas)
  {
    paramCanvas.setDrawFilter(SkinManager.getInstance().getDrawFilter());
    paramCanvas.save();
    drawBg(paramCanvas);
    drawLines(paramCanvas);
    drawTitle(paramCanvas);
    drawArrow(paramCanvas);
    paramCanvas.restore();
  }

  protected void onMeasure(int paramInt1, int paramInt2)
  {
    this.itemLayout.scaleToBounds(View.MeasureSpec.getSize(paramInt1), View.MeasureSpec.getSize(paramInt2));
    this.lineLayout.scaleToBounds(this.itemLayout);
    this.titleLayout.scaleToBounds(this.itemLayout);
    this.labelLayout.scaleToBounds(this.itemLayout);
    this.arrowLayout.scaleToBounds(this.itemLayout);
    this.mTitlePaint.setTextSize(SkinManager.getInstance().getSubTextSize());
    this.mArrowRect.set(this.arrowLayout.leftMargin, this.arrowLayout.topMargin, this.arrowLayout.getRight(), this.arrowLayout.getBottom());
    setMeasuredDimension(this.itemLayout.width, this.itemLayout.height);
  }

  public void setArrow()
  {
    this.mHasArrow = true;
  }

  public void setBackground(boolean paramBoolean)
  {
    this.mHasBackground = paramBoolean;
  }

  public void setCount(int paramInt)
  {
    this.mCnt = paramInt;
    invalidate();
  }

  public void setTagName(String paramString)
  {
    this.mTitle = paramString;
    invalidate();
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.qtradio.view.virtualchannels.ChannelDetailTagView
 * JD-Core Version:    0.6.2
 */