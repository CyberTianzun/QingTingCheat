package fm.qingting.qtradio.view.playview_bb;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import fm.qingting.framework.utils.BitmapResourceCache;
import fm.qingting.framework.view.ViewLayout;
import fm.qingting.qtradio.manager.SkinManager;
import fm.qingting.qtradio.view.playview.AbsSeekBarElement;

public class DanmakuSeekBarElement extends AbsSeekBarElement
{
  private final Paint mPaint = new Paint();
  private final ViewLayout progressBgLayout = this.standardLayout.createChildLT(720, 10, 0, 36, ViewLayout.SCALE_FLAG_SLTCW);
  private final ViewLayout standardLayout = ViewLayout.createViewLayoutWithBoundsLT(720, 70, 720, 70, 0, 0, ViewLayout.FILL);
  private final ViewLayout thumbLayout = this.standardLayout.createChildLT(42, 42, 0, 20, ViewLayout.SCALE_FLAG_SLTCW);

  public DanmakuSeekBarElement(Context paramContext)
  {
    super(paramContext);
  }

  private void resolveShift()
  {
    int i = getMax();
    float f1;
    float f2;
    if (i <= 0)
    {
      f1 = 0.0F;
      boolean bool = f1 < 0.0F;
      f2 = 0.0F;
      if (!bool)
        break label72;
    }
    while (true)
    {
      int j = (int)(f2 * getWidth());
      this.mThumbRect.offsetTo(j - this.mThumbRect.width() / 2, this.mThumbRect.top);
      return;
      f1 = this.mProgress / i;
      break;
      label72: if (f1 > 1.0F)
        f2 = 1.0F;
      else
        f2 = f1;
    }
  }

  protected void drawProgressBg(Canvas paramCanvas)
  {
    int i = this.mThumbRect.centerX();
    int j = paramCanvas.save();
    paramCanvas.clipRect(i, this.progressBgLayout.topMargin, this.progressBgLayout.getRight(), this.progressBgLayout.getBottom());
    paramCanvas.drawColor(-1776412);
    paramCanvas.restoreToCount(j);
    int k = paramCanvas.save();
    paramCanvas.clipRect(this.progressBgLayout.leftMargin, this.progressBgLayout.topMargin, i, this.progressBgLayout.getBottom());
    paramCanvas.drawColor(SkinManager.getTextColorHighlight());
    paramCanvas.restoreToCount(k);
  }

  protected void drawThumb(Canvas paramCanvas)
  {
    paramCanvas.drawBitmap(BitmapResourceCache.getInstance().getResourceCacheByParent(getContext().getResources(), this.mOwnerId, 2130837523), null, this.mThumbRect, this.mPaint);
  }

  protected void onMeasureElement(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    this.standardLayout.scaleToBounds(paramInt3 - paramInt1, paramInt4 - paramInt2);
    this.thumbLayout.scaleToBounds(this.standardLayout);
    this.progressBgLayout.scaleToBounds(this.standardLayout);
    this.mThumbRect.set(this.thumbLayout.leftMargin - this.thumbLayout.width / 2, this.thumbLayout.topMargin, this.thumbLayout.getRight() - this.thumbLayout.width / 2, this.thumbLayout.getBottom());
    resolveShift();
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.qtradio.view.playview_bb.DanmakuSeekBarElement
 * JD-Core Version:    0.6.2
 */