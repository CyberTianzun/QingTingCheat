package fm.qingting.qtradio.view.playview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Rect;
import android.graphics.RectF;
import fm.qingting.framework.view.ViewElement;
import fm.qingting.qtradio.manager.SkinManager;
import fm.qingting.qtradio.model.InfoManager;

public class LabelViewElement extends ViewElement
{
  private final Paint mBgPaint = new Paint();
  private String mId;
  private final Paint mLabelPaint = new Paint();
  private final Rect mTextBound = new Rect();
  private final RectF mTipRectF = new RectF();

  public LabelViewElement(Context paramContext)
  {
    super(paramContext);
    this.mBgPaint.setColor(SkinManager.getTextColorHighlight());
    this.mBgPaint.setStyle(Paint.Style.FILL);
    this.mLabelPaint.setColor(-1);
    this.mLabelPaint.setTextSize(SkinManager.getInstance().getTinyTextSize());
  }

  private void drawTip(Canvas paramCanvas)
  {
    if (this.mId == null);
    int i;
    do
    {
      return;
      i = InfoManager.getInstance().getWsqNew(this.mId);
    }
    while (i <= 0);
    if (i >= 100);
    int j;
    int k;
    int m;
    for (String str = "99+"; ; str = String.valueOf(i))
    {
      this.mLabelPaint.getTextBounds(str, 0, str.length(), this.mTextBound);
      j = this.mTextBound.right + this.mTextBound.left;
      k = getUpperLimit();
      m = getHeight();
      if (j > k)
        break;
      float f1 = getLeftMargin() + m / 2;
      float f2 = getTopMargin() + m / 2;
      paramCanvas.drawCircle(f1, f2, m / 2, this.mBgPaint);
      paramCanvas.drawText(str, f1 - (this.mTextBound.right + this.mTextBound.left) / 2, f2 - (this.mTextBound.top + this.mTextBound.bottom) / 2, this.mLabelPaint);
      return;
    }
    this.mTipRectF.set(getLeftMargin(), getTopMargin(), j + (m + getLeftMargin()) - k, getBottomMargin());
    paramCanvas.drawRoundRect(this.mTipRectF, m / 2, m / 2, this.mBgPaint);
    paramCanvas.drawText(str, this.mTipRectF.centerX() - (this.mTextBound.right + this.mTextBound.left) / 2, this.mTipRectF.centerY() - (this.mTextBound.top + this.mTextBound.bottom) / 2, this.mLabelPaint);
  }

  private int getUpperLimit()
  {
    return 2 * getHeight() / 3;
  }

  protected void onDrawElement(Canvas paramCanvas)
  {
    drawTip(paramCanvas);
  }

  protected void onMeasureElement(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
  }

  public void setId(String paramString)
  {
    if (paramString == null)
    {
      setVisible(4);
      return;
    }
    this.mId = paramString;
    setVisible(0);
    invalidateElement();
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.qtradio.view.playview.LabelViewElement
 * JD-Core Version:    0.6.2
 */