package fm.qingting.framework.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.DrawFilter;
import android.graphics.PaintFlagsDrawFilter;
import android.view.MotionEvent;

public class QtView extends ViewImpl
{
  private static final int ARRAY_CAPACITY_INCREMENT = 4;
  private static final int ARRAY_INITIAL_CAPACITY = 4;
  private final DrawFilter filter = new PaintFlagsDrawFilter(0, 67);
  private ViewElement[] mChildren = new ViewElement[4];
  private int mChildrenCount = 0;
  protected boolean mIgnoreTouchEvent = false;
  private boolean mInTouchMode = false;
  private int mLastTouchChildIndex = -1;

  public QtView(Context paramContext)
  {
    super(paramContext);
  }

  private void drawChildrens(Canvas paramCanvas)
  {
    if ((this.mChildren == null) || (this.mChildren.length == 0) || (this.mChildrenCount == 0))
      return;
    int i = 0;
    label25: ViewElement localViewElement;
    if (i < this.mChildrenCount)
    {
      localViewElement = this.mChildren[i];
      if (localViewElement.getVisiblity() != 4)
        break label54;
    }
    while (true)
    {
      i++;
      break label25;
      break;
      label54: localViewElement.draw(paramCanvas);
    }
  }

  private int handleChildrenTouchEvent(MotionEvent paramMotionEvent)
  {
    if ((this.mChildren == null) || (this.mChildren.length == 0) || (this.mChildrenCount == 0))
      return -1;
    int i = -1 + this.mChildrenCount;
    if (i < 0)
      return -1;
    ViewElement localViewElement = this.mChildren[i];
    if (localViewElement.getVisiblity() == 4);
    while (!localViewElement.handleTouchEvent(paramMotionEvent))
    {
      i--;
      break;
    }
    return i;
  }

  private int indexOfChild(ViewElement paramViewElement)
  {
    int i = this.mChildrenCount;
    ViewElement[] arrayOfViewElement = this.mChildren;
    for (int j = 0; ; j++)
    {
      if (j >= i)
        j = -1;
      while (arrayOfViewElement[j] == paramViewElement)
        return j;
    }
  }

  private void removeFromArray(int paramInt)
  {
    ViewElement[] arrayOfViewElement = this.mChildren;
    int i = this.mChildrenCount;
    if (paramInt == i - 1)
    {
      int k = -1 + this.mChildrenCount;
      this.mChildrenCount = k;
      arrayOfViewElement[k] = null;
    }
    while (true)
    {
      invalidate();
      return;
      if ((paramInt < 0) || (paramInt >= i))
        break;
      System.arraycopy(arrayOfViewElement, paramInt + 1, arrayOfViewElement, paramInt, -1 + (i - paramInt));
      int j = -1 + this.mChildrenCount;
      this.mChildrenCount = j;
      arrayOfViewElement[j] = null;
    }
    throw new IndexOutOfBoundsException();
  }

  private void removeFromArray(int paramInt1, int paramInt2)
  {
    ViewElement[] arrayOfViewElement = this.mChildren;
    int i = this.mChildrenCount;
    int j = Math.max(0, paramInt1);
    int k = Math.min(i, j + paramInt2);
    if (j == k)
      return;
    int n;
    if (k == i)
    {
      n = j;
      if (n < k);
    }
    while (true)
    {
      this.mChildrenCount -= k - j;
      invalidate();
      return;
      arrayOfViewElement[n] = null;
      n++;
      break;
      System.arraycopy(arrayOfViewElement, k, arrayOfViewElement, j, i - k);
      for (int m = i - (k - j); m < i; m++)
        arrayOfViewElement[m] = null;
    }
  }

  public void addElement(ViewElement paramViewElement)
  {
    ViewElement[] arrayOfViewElement = this.mChildren;
    int i = this.mChildrenCount;
    int j = arrayOfViewElement.length;
    if (j == i)
    {
      this.mChildren = new ViewElement[j + 4];
      System.arraycopy(arrayOfViewElement, 0, this.mChildren, 0, j);
      arrayOfViewElement = this.mChildren;
    }
    paramViewElement.setParent(this);
    int k = this.mChildrenCount;
    this.mChildrenCount = (k + 1);
    arrayOfViewElement[k] = paramViewElement;
  }

  public void addElement(ViewElement paramViewElement, int paramInt)
  {
    paramViewElement.setOwenerId(paramInt);
    addElement(paramViewElement);
  }

  public ViewElement getChildAt(int paramInt)
  {
    if ((paramInt < 0) || (paramInt >= this.mChildrenCount))
      return null;
    return this.mChildren[paramInt];
  }

  public int getChildCount()
  {
    return this.mChildrenCount;
  }

  protected void ignoreSelfTouchEvent()
  {
    this.mIgnoreTouchEvent = true;
  }

  protected void onDraw(Canvas paramCanvas)
  {
    paramCanvas.setDrawFilter(this.filter);
    paramCanvas.save();
    drawChildrens(paramCanvas);
    paramCanvas.restore();
  }

  public boolean onTouchEvent(MotionEvent paramMotionEvent)
  {
    boolean bool = true;
    if (this.mIgnoreTouchEvent)
      bool = super.onTouchEvent(paramMotionEvent);
    int i;
    do
    {
      do
        return bool;
      while ((paramMotionEvent.getAction() != 0) && (!this.mInTouchMode));
      switch (paramMotionEvent.getAction())
      {
      default:
        return bool;
      case 0:
        this.mLastTouchChildIndex = handleChildrenTouchEvent(paramMotionEvent);
        this.mInTouchMode = bool;
        return bool;
      case 2:
        i = handleChildrenTouchEvent(paramMotionEvent);
      case 1:
      case 3:
      }
    }
    while (this.mLastTouchChildIndex == i);
    this.mInTouchMode = false;
    return bool;
    handleChildrenTouchEvent(paramMotionEvent);
    return bool;
  }

  public void removeAllElements()
  {
    int i = this.mChildrenCount;
    if (i <= 0)
      return;
    ViewElement[] arrayOfViewElement = this.mChildren;
    this.mChildrenCount = 0;
    for (int j = i - 1; ; j--)
    {
      if (j < 0)
      {
        invalidate();
        return;
      }
      arrayOfViewElement[j] = null;
    }
  }

  public void removeElement(ViewElement paramViewElement)
  {
    int i = indexOfChild(paramViewElement);
    if (i >= 0)
      removeFromArray(i);
  }

  public void removeElements(int paramInt1, int paramInt2)
  {
    removeFromArray(paramInt1, paramInt2);
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.framework.view.QtView
 * JD-Core Version:    0.6.2
 */