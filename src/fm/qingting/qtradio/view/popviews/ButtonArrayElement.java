package fm.qingting.qtradio.view.popviews;

import android.content.Context;
import android.graphics.Canvas;
import android.view.MotionEvent;
import fm.qingting.framework.view.ButtonViewElement;
import fm.qingting.framework.view.ViewElement;
import fm.qingting.framework.view.ViewElement.OnElementClickListener;
import fm.qingting.qtradio.manager.SkinManager;
import java.util.List;

public class ButtonArrayElement extends ViewElement
  implements ViewElement.OnElementClickListener
{
  private ButtonViewElement[] mButtons;
  private boolean mInTouchMode = false;
  private List<ButtonItem> mItems;
  private int mLastTouchChildIndex = -1;
  private int mLineWidth = 1;
  private OnButtonArrayClickListener mListener;

  public ButtonArrayElement(Context paramContext)
  {
    super(paramContext);
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
    paramCanvas.clipRect(getLeftMargin(), paramInt2, getRightMargin(), paramInt3);
    paramCanvas.drawColor(SkinManager.getItemHighlightMaskColor());
    paramCanvas.restoreToCount(i);
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
          drawHighlight(paramCanvas, i, localButtonViewElement.getTopMargin(), localButtonViewElement.getBottomMargin());
        SkinManager.getInstance().drawHorizontalLine(paramCanvas, getLeftMargin(), getRightMargin(), localButtonViewElement.getTopMargin(), this.mLineWidth);
      }
    }
  }

  private void drawTopLine(Canvas paramCanvas)
  {
    if (this.mLineWidth == 0);
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
      int i = (paramInt4 - paramInt2) / this.mButtons.length;
      for (int j = 0; j < this.mButtons.length; j++)
      {
        this.mButtons[j].measure(paramInt1, paramInt2, paramInt3, paramInt2 + i);
        this.mButtons[j].setTextSize(SkinManager.getInstance().getNormalTextSize());
        paramInt2 += i;
      }
    }
  }

  public int getCount()
  {
    if (this.mButtons == null)
      return 0;
    return this.mButtons.length;
  }

  public ButtonItem getItem(int paramInt)
  {
    if (this.mItems == null);
    while ((paramInt < 0) || (paramInt >= this.mItems.size()))
      return null;
    return (ButtonItem)this.mItems.get(paramInt);
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
          this.mListener.OnArrayClick(paramViewElement, i);
    }
  }

  protected void onMeasureElement(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    measureButtons(paramInt1, paramInt2, paramInt3, paramInt4);
  }

  public void setButtons(List<ButtonItem> paramList)
  {
    int i = 0;
    if (this.mButtons != null)
      this.mButtons = null;
    for (int j = 1; ; j = 0)
    {
      this.mItems = paramList;
      this.mButtons = new ButtonViewElement[paramList.size()];
      if (i < paramList.size())
      {
        ButtonViewElement localButtonViewElement = new ButtonViewElement(getContext());
        ButtonItem localButtonItem = (ButtonItem)paramList.get(i);
        localButtonViewElement.setText(localButtonItem.getName());
        if (localButtonItem.getType() == 0);
        for (int k = -11908534; ; k = SkinManager.getTextColorHighlight())
        {
          localButtonViewElement.setTextColor(k, k);
          localButtonViewElement.setOnElementClickListener(this);
          localButtonViewElement.setBelonging(this);
          this.mButtons[i] = localButtonViewElement;
          i++;
          break;
        }
      }
      if (j != 0)
        measureButtons(getLeftMargin() - this.mTranslationX, getTopMargin() - this.mTranslationY, getRightMargin() - this.mTranslationX, getBottomMargin() - this.mTranslationY);
      return;
    }
  }

  public void setOnArrayClickListenrer(OnButtonArrayClickListener paramOnButtonArrayClickListener)
  {
    this.mListener = paramOnButtonArrayClickListener;
  }

  public void setOtherParams(int paramInt)
  {
    this.mLineWidth = paramInt;
  }

  public static abstract interface OnButtonArrayClickListener
  {
    public abstract void OnArrayClick(ViewElement paramViewElement, int paramInt);
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.qtradio.view.popviews.ButtonArrayElement
 * JD-Core Version:    0.6.2
 */