package fm.qingting.qtradio.view.popviews;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.RectF;
import android.view.MotionEvent;
import fm.qingting.framework.view.ButtonViewElement;
import fm.qingting.framework.view.ViewElement;
import fm.qingting.framework.view.ViewElement.OnElementClickListener;
import fm.qingting.qtradio.manager.SkinManager;
import java.util.List;

public class ButtonRowElement extends ViewElement
  implements ViewElement.OnElementClickListener
{
  private ButtonViewElement[] mButtons;
  private Paint mHighlightPaint = new Paint();
  private RectF mHighlightRectF = new RectF();
  private boolean mInTouchMode = false;
  private int mLastTouchChildIndex = -1;
  private int mLineWidth = 1;
  private OnButtonRowClickListener mListener;
  private float mRoundCornerRadius = 0.0F;

  public ButtonRowElement(Context paramContext)
  {
    super(paramContext);
    this.mHighlightPaint.setColor(SkinManager.getItemHighlightMaskColor());
    this.mHighlightPaint.setStyle(Paint.Style.FILL);
  }

  private void drawButtons(Canvas paramCanvas)
  {
    if ((this.mButtons == null) || (this.mButtons.length == 0));
    while (true)
    {
      return;
      for (int i = 0; i < this.mButtons.length; i++)
      {
        ButtonViewElement localButtonViewElement = this.mButtons[i];
        localButtonViewElement.setTranslationX(getTranslationX());
        localButtonViewElement.setTranslationY(getTranslationY());
        localButtonViewElement.draw(paramCanvas);
      }
    }
  }

  private void drawHighlight(Canvas paramCanvas, int paramInt1, int paramInt2, int paramInt3)
  {
    int i = paramCanvas.save();
    paramCanvas.clipRect(paramInt2, getTopMargin(), paramInt3, getBottomMargin());
    if (this.mButtons.length == 1)
    {
      this.mHighlightRectF.set(paramInt2, getTopMargin() - this.mRoundCornerRadius, paramInt3, getBottomMargin());
      paramCanvas.drawRoundRect(this.mHighlightRectF, this.mRoundCornerRadius, this.mRoundCornerRadius, this.mHighlightPaint);
    }
    while (true)
    {
      paramCanvas.restoreToCount(i);
      return;
      if (paramInt1 == 0)
      {
        this.mHighlightRectF.set(paramInt2, getTopMargin() - this.mRoundCornerRadius, paramInt3 + this.mRoundCornerRadius, getBottomMargin());
        paramCanvas.drawRoundRect(this.mHighlightRectF, this.mRoundCornerRadius, this.mRoundCornerRadius, this.mHighlightPaint);
      }
      else if (paramInt1 == -1 + this.mButtons.length)
      {
        this.mHighlightRectF.set(paramInt2 - this.mRoundCornerRadius, getTopMargin() - this.mRoundCornerRadius, paramInt3, getBottomMargin());
        paramCanvas.drawRoundRect(this.mHighlightRectF, this.mRoundCornerRadius, this.mRoundCornerRadius, this.mHighlightPaint);
      }
      else
      {
        this.mHighlightRectF.set(paramInt2, getTopMargin(), paramInt3, getBottomMargin());
        paramCanvas.drawRect(this.mHighlightRectF, this.mHighlightPaint);
      }
    }
  }

  private void drawLinesAndHighlight(Canvas paramCanvas)
  {
    if (this.mButtons == null);
    while (true)
    {
      return;
      for (int i = 0; i < this.mButtons.length; i++)
      {
        ButtonViewElement localButtonViewElement = this.mButtons[i];
        if (localButtonViewElement.isHighlighted())
          drawHighlight(paramCanvas, i, localButtonViewElement.getLeftMargin(), localButtonViewElement.getRightMargin());
        if (i > 0)
          SkinManager.getInstance().drawVerticalLine(paramCanvas, getTopMargin(), getBottomMargin(), localButtonViewElement.getLeftMargin(), this.mLineWidth);
      }
    }
  }

  private void drawTopLine(Canvas paramCanvas)
  {
    if (this.mLineWidth == 0)
      return;
    SkinManager.getInstance().drawHorizontalLine(paramCanvas, getLeftMargin(), getRightMargin(), getTopMargin(), this.mLineWidth);
  }

  private int handleChildrenTouchEvent(MotionEvent paramMotionEvent)
  {
    if ((this.mButtons == null) || (this.mButtons.length == 0))
    {
      i = -1;
      return i;
    }
    for (int i = 0; ; i++)
    {
      if (i >= this.mButtons.length)
        break label49;
      if (this.mButtons[i].handleTouchEvent(paramMotionEvent))
        break;
    }
    label49: return -1;
  }

  private void measureButtons(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    if (this.mButtons == null);
    while (true)
    {
      return;
      int i = (paramInt3 - paramInt1) / this.mButtons.length;
      for (int j = 0; j < this.mButtons.length; j++)
      {
        this.mButtons[j].measure(paramInt1, paramInt2, paramInt1 + i, paramInt4);
        this.mButtons[j].setTextSize(SkinManager.getInstance().getNormalTextSize());
        paramInt1 += i;
      }
    }
  }

  public boolean handleTouchEvent(MotionEvent paramMotionEvent)
  {
    if ((paramMotionEvent.getAction() != 0) && (!this.mInTouchMode));
    int i;
    do
    {
      return true;
      switch (paramMotionEvent.getAction())
      {
      default:
        return true;
      case 0:
        this.mLastTouchChildIndex = handleChildrenTouchEvent(paramMotionEvent);
        this.mInTouchMode = true;
        return true;
      case 2:
        i = handleChildrenTouchEvent(paramMotionEvent);
      case 1:
      case 3:
      }
    }
    while (this.mLastTouchChildIndex == i);
    this.mInTouchMode = false;
    return true;
    handleChildrenTouchEvent(paramMotionEvent);
    return true;
  }

  protected void onDrawElement(Canvas paramCanvas)
  {
    drawLinesAndHighlight(paramCanvas);
    drawButtons(paramCanvas);
    drawTopLine(paramCanvas);
  }

  public void onElementClick(ViewElement paramViewElement)
  {
    if ((this.mButtons == null) || (this.mListener == null));
    while (true)
    {
      return;
      for (int i = 0; i < this.mButtons.length; i++)
        if (this.mButtons[i] == paramViewElement)
          this.mListener.OnRowClick(paramViewElement, i);
    }
  }

  protected void onMeasureElement(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    measureButtons(paramInt1, paramInt2, paramInt3, paramInt4);
  }

  public void setButtons(List<String> paramList)
  {
    int i = 0;
    if (this.mButtons != null)
      this.mButtons = null;
    for (int j = 1; ; j = 0)
    {
      this.mButtons = new ButtonViewElement[paramList.size()];
      while (i < paramList.size())
      {
        ButtonViewElement localButtonViewElement = new ButtonViewElement(getContext());
        localButtonViewElement.setText((String)paramList.get(i));
        localButtonViewElement.setTextColor(SkinManager.getTextColorHighlight(), SkinManager.getTextColorHighlight());
        localButtonViewElement.setOnElementClickListener(this);
        localButtonViewElement.setBelonging(this);
        this.mButtons[i] = localButtonViewElement;
        i++;
      }
      if (j != 0)
        measureButtons(getLeftMargin() - this.mTranslationX, getTopMargin() - this.mTranslationY, getRightMargin() - this.mTranslationX, getBottomMargin() - this.mTranslationY);
      return;
    }
  }

  public void setOnRowClickListenrer(OnButtonRowClickListener paramOnButtonRowClickListener)
  {
    this.mListener = paramOnButtonRowClickListener;
  }

  public void setOtherParams(float paramFloat, int paramInt)
  {
    this.mRoundCornerRadius = paramFloat;
    this.mLineWidth = paramInt;
  }

  public static abstract interface OnButtonRowClickListener
  {
    public abstract void OnRowClick(ViewElement paramViewElement, int paramInt);
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.qtradio.view.popviews.ButtonRowElement
 * JD-Core Version:    0.6.2
 */