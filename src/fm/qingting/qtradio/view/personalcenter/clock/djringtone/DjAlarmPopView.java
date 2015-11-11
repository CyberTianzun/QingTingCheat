package fm.qingting.qtradio.view.personalcenter.clock.djringtone;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.DrawFilter;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View.MeasureSpec;
import fm.qingting.framework.manager.EventDispacthManager;
import fm.qingting.framework.utils.BitmapResourceCache;
import fm.qingting.framework.view.ViewImpl;
import fm.qingting.framework.view.ViewLayout;
import fm.qingting.qtradio.manager.SkinManager;

public class DjAlarmPopView extends ViewImpl
{
  private final Paint bgPaint = new Paint();
  private final ViewLayout buttonLayout = ViewLayout.createViewLayoutWithBoundsLT(184, 100, 480, 800, 0, 80, ViewLayout.LT | ViewLayout.SLT | ViewLayout.CW);
  private final Handler delayHandler = new Handler();
  private Runnable delayRunnable = new Runnable()
  {
    public void run()
    {
      DjAlarmPopView.this.dispatchActionEvent("cancelPop", null);
    }
  };
  private boolean failed = false;
  private final DrawFilter filter = new PaintFlagsDrawFilter(0, 67);
  private final ViewLayout largeBgLayout = ViewLayout.createViewLayoutWithBoundsLT(400, 200, 480, 800, 0, 200, ViewLayout.LT | ViewLayout.SLT | ViewLayout.CW);
  private boolean leftSelected = false;
  private final RectF mBgRectF = new RectF();
  private final RectF mButtonRectF = new RectF();
  private final Paint mHighlightButtonPaint = new Paint();
  private final Paint mNormalButtonPaint = new Paint();
  private boolean rightSelected = false;
  private final ViewLayout smallBgLayout = ViewLayout.createViewLayoutWithBoundsLT(400, 120, 480, 800, 0, 200, ViewLayout.LT | ViewLayout.SLT | ViewLayout.CW);
  private final ViewLayout standardLayout = ViewLayout.createViewLayoutWithBoundsLT(480, 800, 480, 800, 0, 0, ViewLayout.FILL);
  private Rect textBound = new Rect();
  private final ViewLayout tipLayout = ViewLayout.createViewLayoutWithBoundsLT(400, 40, 480, 800, 10, 40, ViewLayout.LT | ViewLayout.SLT | ViewLayout.CW);
  private final Paint tipPaint = new Paint();
  private String tipText = "正在下载铃声";

  public DjAlarmPopView(Context paramContext)
  {
    super(paramContext);
    this.bgPaint.setColor(SkinManager.getPopBgColor());
    this.tipPaint.setColor(SkinManager.getTextColorNormal());
    this.mHighlightButtonPaint.setColor(SkinManager.getPopButtonHighlightColor());
    this.mHighlightButtonPaint.setStyle(Paint.Style.FILL);
    this.mNormalButtonPaint.setColor(SkinManager.getPopButtonNormalColor());
    this.mNormalButtonPaint.setStyle(Paint.Style.FILL);
  }

  private void drawLargeBg(Canvas paramCanvas)
  {
    int i = (this.standardLayout.width - this.largeBgLayout.width) / 2;
    int j = (this.standardLayout.width + this.largeBgLayout.width) / 2;
    int k = (this.standardLayout.width - this.largeBgLayout.width) / 2 + (this.largeBgLayout.width / 2 - this.buttonLayout.width) / 2;
    int m = this.largeBgLayout.topMargin + this.buttonLayout.topMargin;
    (this.largeBgLayout.topMargin + this.largeBgLayout.height);
    int n = this.largeBgLayout.topMargin + this.buttonLayout.topMargin + this.buttonLayout.height + this.tipLayout.topMargin / 4;
    this.mBgRectF.set(i, this.largeBgLayout.topMargin, j, n);
    paramCanvas.drawRoundRect(this.mBgRectF, this.tipLayout.leftMargin, this.tipLayout.leftMargin, this.bgPaint);
    this.tipPaint.getTextBounds(this.tipText, 0, this.tipText.length(), this.textBound);
    paramCanvas.drawText(this.tipText, (this.standardLayout.width - this.textBound.width()) / 2, this.largeBgLayout.topMargin + this.tipLayout.topMargin / 3 + this.tipLayout.height, this.tipPaint);
    this.mButtonRectF.set(k, m, k + this.buttonLayout.width, m + this.buttonLayout.height);
    RectF localRectF1 = this.mButtonRectF;
    float f1 = this.tipLayout.leftMargin;
    float f2 = this.tipLayout.leftMargin;
    Paint localPaint1;
    int i1;
    RectF localRectF2;
    float f3;
    float f4;
    if (this.leftSelected)
    {
      localPaint1 = this.mHighlightButtonPaint;
      paramCanvas.drawRoundRect(localRectF1, f1, f2, localPaint1);
      this.tipPaint.getTextBounds("取消", 0, "取消".length(), this.textBound);
      paramCanvas.drawText("取消", k + this.buttonLayout.width / 2 - this.textBound.width() / 2, m + (this.buttonLayout.height - this.textBound.top - this.textBound.bottom) / 2, this.tipPaint);
      i1 = k + this.largeBgLayout.width / 2;
      this.mButtonRectF.set(i1, m, i1 + this.buttonLayout.width, m + this.buttonLayout.height);
      localRectF2 = this.mButtonRectF;
      f3 = this.tipLayout.leftMargin;
      f4 = this.tipLayout.leftMargin;
      if (!this.rightSelected)
        break label630;
    }
    label630: for (Paint localPaint2 = this.mHighlightButtonPaint; ; localPaint2 = this.mNormalButtonPaint)
    {
      paramCanvas.drawRoundRect(localRectF2, f3, f4, localPaint2);
      this.tipPaint.getTextBounds("重试", 0, "重试".length(), this.textBound);
      paramCanvas.drawText("重试", i1 + this.buttonLayout.width / 2 - this.textBound.width() / 2, m + (this.buttonLayout.height - this.textBound.top - this.textBound.bottom) / 2, this.tipPaint);
      return;
      localPaint1 = this.mNormalButtonPaint;
      break;
    }
  }

  private void drawSmallBg(Canvas paramCanvas)
  {
    int i = (this.standardLayout.width - this.smallBgLayout.width) / 2;
    int j = (this.standardLayout.width + this.smallBgLayout.width) / 2;
    this.mBgRectF.set(i, this.smallBgLayout.topMargin, j, this.smallBgLayout.topMargin + this.smallBgLayout.height);
    paramCanvas.drawRoundRect(this.mBgRectF, this.tipLayout.leftMargin, this.tipLayout.leftMargin, this.bgPaint);
    this.tipPaint.getTextBounds(this.tipText, 0, this.tipText.length(), this.textBound);
    paramCanvas.drawText(this.tipText, (this.standardLayout.width - this.textBound.width()) / 2, this.smallBgLayout.topMargin + this.tipLayout.topMargin + this.tipLayout.height, this.tipPaint);
  }

  public void close(boolean paramBoolean)
  {
    BitmapResourceCache.getInstance().clearResourceCacheOfOne(this, 0);
    super.close(paramBoolean);
  }

  protected void onDraw(Canvas paramCanvas)
  {
    paramCanvas.save();
    paramCanvas.setDrawFilter(this.filter);
    if (this.failed)
      drawLargeBg(paramCanvas);
    while (true)
    {
      paramCanvas.restore();
      return;
      drawSmallBg(paramCanvas);
    }
  }

  protected void onMeasure(int paramInt1, int paramInt2)
  {
    this.standardLayout.scaleToBounds(View.MeasureSpec.getSize(paramInt1), View.MeasureSpec.getSize(paramInt2));
    this.smallBgLayout.scaleToBounds(this.standardLayout);
    this.largeBgLayout.scaleToBounds(this.standardLayout);
    this.tipLayout.scaleToBounds(this.standardLayout);
    this.buttonLayout.scaleToBounds(this.standardLayout);
    this.tipPaint.setTextSize(0.045F * this.standardLayout.width);
    setMeasuredDimension(this.standardLayout.width, this.standardLayout.height);
  }

  public boolean onTouchEvent(MotionEvent paramMotionEvent)
  {
    if (!this.failed)
    {
      dispatchActionEvent("cancelPop", null);
      return true;
    }
    switch (paramMotionEvent.getAction())
    {
    case 2:
    default:
      return true;
    case 0:
      float f1 = paramMotionEvent.getX();
      float f2 = paramMotionEvent.getY();
      int i;
      int j;
      if ((f1 > (this.standardLayout.width - this.largeBgLayout.width) / 2) && (f1 < (this.standardLayout.width + this.largeBgLayout.width) / 2) && (f2 > this.largeBgLayout.topMargin) && (f2 < this.largeBgLayout.topMargin + this.buttonLayout.topMargin + this.buttonLayout.height + this.tipLayout.topMargin / 4))
      {
        i = (this.standardLayout.width - this.largeBgLayout.width) / 2 + (this.largeBgLayout.width / 2 - this.buttonLayout.width) / 2;
        j = this.largeBgLayout.topMargin + this.buttonLayout.topMargin;
        if ((f1 > i) && (f1 < i + this.buttonLayout.width) && (f2 > j) && (f2 < j + this.buttonLayout.height))
          this.leftSelected = true;
      }
      while (true)
      {
        invalidate();
        return true;
        if ((f1 > i + this.largeBgLayout.width / 2) && (f1 < i + this.largeBgLayout.width / 2 + this.buttonLayout.width) && (f2 > j) && (f2 < j + this.buttonLayout.height))
        {
          this.rightSelected = true;
          continue;
          dispatchActionEvent("cancelPop", null);
        }
      }
    case 1:
      if (this.leftSelected)
        dispatchActionEvent("cancelPop", null);
      while (true)
      {
        this.leftSelected = false;
        this.rightSelected = false;
        invalidate();
        return true;
        if (this.rightSelected)
        {
          dispatchActionEvent("cancelPop", null);
          EventDispacthManager.getInstance().dispatchAction("retryDownload", null);
        }
      }
    case 3:
    }
    this.leftSelected = false;
    this.rightSelected = false;
    invalidate();
    return true;
  }

  public void update(String paramString, Object paramObject)
  {
    if (paramString.equalsIgnoreCase("setBubbleData"))
    {
      if (paramObject == null)
      {
        this.tipText = "正在下载闹铃声";
        this.failed = false;
        invalidate();
      }
    }
    else
      return;
    if (((Boolean)paramObject).booleanValue())
    {
      this.tipText = "闹铃声下载完成";
      this.failed = false;
      invalidate();
      this.delayHandler.removeCallbacks(this.delayRunnable);
      this.delayHandler.postDelayed(this.delayRunnable, 500L);
      return;
    }
    this.tipText = "闹铃声下载失败";
    this.failed = true;
    invalidate();
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.qtradio.view.personalcenter.clock.djringtone.DjAlarmPopView
 * JD-Core Version:    0.6.2
 */