package fm.qingting.qtradio.view.navigation.items;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Rect;
import android.graphics.RectF;
import android.view.MotionEvent;
import android.view.View.MeasureSpec;
import fm.qingting.framework.event.IEventHandler;
import fm.qingting.framework.view.QtListItemView;
import fm.qingting.framework.view.ViewLayout;
import fm.qingting.qtradio.manager.SkinManager;

public class TopAutoScaleView extends QtListItemView
{
  private IEventHandler eventHandler;
  private ViewLayout frameLayout = this.standardLayout.createChildLT(2, 6, 0, 0, ViewLayout.SCALE_FLAG_SLTCW);
  private Paint mBgPaint = new Paint();
  private Paint mFramePaint = new Paint();
  private RectF mFrameRectF = new RectF();
  private Paint mHighlightPaint = new Paint();
  private boolean mInTouchMode = false;
  private int mItemType;
  private Paint mNormalPaint = new Paint();
  private boolean mSelected = false;
  private Rect mTextBound = new Rect();
  private String mTip;
  private Paint mTipBgPaint = new Paint();
  private Paint mTipPaint = new Paint();
  private String mTitle = " ";
  private ViewLayout standardLayout = ViewLayout.createViewLayoutWithBoundsLT(106, 44, 468, 60, 0, 0, ViewLayout.LT | ViewLayout.SLT | ViewLayout.CW);
  private final ViewLayout tipLayout = this.standardLayout.createChildLT(30, 30, 10, 2, ViewLayout.SCALE_FLAG_SLTCW);

  public TopAutoScaleView(Context paramContext)
  {
    super(paramContext);
    this.mFramePaint.setColor(-11711155);
    this.mFramePaint.setStyle(Paint.Style.STROKE);
    this.mBgPaint.setColor(SkinManager.getTextColorHighlight());
    this.mBgPaint.setStyle(Paint.Style.FILL);
    this.mNormalPaint.setColor(-1);
    this.mHighlightPaint.setColor(-1);
    this.mTipBgPaint.setColor(SkinManager.getDownloadTipBgColor());
    this.mTipPaint.setColor(SkinManager.getBackgroundColor());
    setItemSelectedEnable();
  }

  private void drawButton(Canvas paramCanvas)
  {
    RectF localRectF = this.mFrameRectF;
    float f1 = this.frameLayout.height;
    float f2 = this.frameLayout.height;
    if ((isItemPressed()) && (this.mSelected));
    for (Paint localPaint1 = this.mBgPaint; ; localPaint1 = this.mFramePaint)
    {
      paramCanvas.drawRoundRect(localRectF, f1, f2, localPaint1);
      if ((this.mTitle != null) && (!this.mTitle.equalsIgnoreCase("")))
        break;
      return;
    }
    this.mNormalPaint.getTextBounds(this.mTitle, 0, this.mTitle.length(), this.mTextBound);
    String str = this.mTitle;
    float f3 = (this.standardLayout.width - this.mTextBound.width()) / 2;
    float f4 = this.mFrameRectF.centerY() - (this.mTextBound.top + this.mTextBound.bottom) / 2;
    if ((isItemPressed()) && (this.mSelected));
    for (Paint localPaint2 = this.mHighlightPaint; ; localPaint2 = this.mNormalPaint)
    {
      paramCanvas.drawText(str, f3, f4, localPaint2);
      if (this.mTip == null)
        break;
      float f5 = this.standardLayout.width - this.tipLayout.width / 2;
      float f6 = this.tipLayout.topMargin + this.tipLayout.height / 2;
      paramCanvas.drawCircle(f5, f6, this.tipLayout.width / 2, this.mTipBgPaint);
      this.mTipPaint.getTextBounds(this.mTip, 0, this.mTip.length(), this.mTextBound);
      paramCanvas.drawText(this.mTip, f5 - (this.mTextBound.right + this.mTextBound.left) / 2, f6 - (this.mTextBound.top + this.mTextBound.bottom) / 2, this.mTipPaint);
      return;
    }
  }

  private boolean pointInView(float paramFloat1, float paramFloat2)
  {
    return (paramFloat1 > 0.0F) && (paramFloat1 < this.standardLayout.width) && (paramFloat2 > this.mFrameRectF.top) && (paramFloat2 < this.mFrameRectF.bottom);
  }

  protected void onDraw(Canvas paramCanvas)
  {
    paramCanvas.save();
    paramCanvas.setDrawFilter(SkinManager.getInstance().getDrawFilter());
    drawButton(paramCanvas);
    paramCanvas.restore();
  }

  protected void onMeasure(int paramInt1, int paramInt2)
  {
    int i = View.MeasureSpec.getSize(paramInt1);
    int j = View.MeasureSpec.getSize(paramInt2);
    this.standardLayout.scaleToBounds(i, j);
    this.frameLayout.scaleToBounds(this.standardLayout);
    this.tipLayout.scaleToBounds(this.standardLayout);
    this.mFrameRectF.set(this.frameLayout.width, (j - this.standardLayout.height) / 2, this.standardLayout.width - this.frameLayout.width, (j + this.standardLayout.height) / 2);
    this.mFramePaint.setStrokeWidth(this.frameLayout.width);
    this.mNormalPaint.setTextSize(SkinManager.getInstance().getMiddleTextSize());
    this.mHighlightPaint.setTextSize(SkinManager.getInstance().getMiddleTextSize());
    this.mTipPaint.setTextSize(0.65F * this.tipLayout.height);
    setMeasuredDimension(this.standardLayout.width, j);
  }

  public boolean onTouchEvent(MotionEvent paramMotionEvent)
  {
    if ((paramMotionEvent.getAction() != 0) && (!this.mInTouchMode));
    do
    {
      do
      {
        do
        {
          do
          {
            return true;
            switch (paramMotionEvent.getAction())
            {
            default:
              return true;
            case 0:
              this.mInTouchMode = true;
              this.mSelected = true;
              invalidate();
              return true;
            case 2:
            case 1:
            case 3:
            }
          }
          while (pointInView(paramMotionEvent.getX(), paramMotionEvent.getY()));
          this.mInTouchMode = false;
          this.mSelected = false;
        }
        while (!isItemPressed());
        invalidate();
        return true;
        this.mInTouchMode = false;
        if (this.eventHandler != null)
          this.eventHandler.onEvent(this, "click", Integer.valueOf(this.mItemType));
      }
      while (!isItemPressed());
      invalidate();
      return true;
      this.mInTouchMode = false;
      this.mSelected = false;
    }
    while (!isItemPressed());
    invalidate();
    return true;
  }

  public void setEventHandler(IEventHandler paramIEventHandler)
  {
    this.eventHandler = paramIEventHandler;
  }

  public void setItemType(int paramInt)
  {
    this.mItemType = paramInt;
  }

  public void setTip(String paramString)
  {
    this.mTip = paramString;
    invalidate();
  }

  public void setTitle(String paramString)
  {
    this.mTitle = paramString;
    invalidate();
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.qtradio.view.navigation.items.TopAutoScaleView
 * JD-Core Version:    0.6.2
 */