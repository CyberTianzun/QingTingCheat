package fm.qingting.framework.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.text.Layout.Alignment;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.TextUtils.TruncateAt;

public class TextViewElement extends ViewElement
{
  private Layout.Alignment mAlignment = Layout.Alignment.ALIGN_NORMAL;
  private float mExactIndentation = 0.0F;
  private int mIndentation = 0;
  private int mLastColor;
  private QtLayout mLayout = new QtLayout();
  private int mMaxLineLimit = 20;
  private final TextPaint mPaint = new TextPaint();
  private final Rect mRect = new Rect();
  private String mText;
  private final Rect mTextBound = new Rect();
  private VerticalAlignment mVerticalAlignment = VerticalAlignment.BOTTOM;

  public TextViewElement(Context paramContext)
  {
    super(paramContext);
    this.mMeasureSpec = 0;
  }

  private void drawText(Canvas paramCanvas)
  {
    if (this.mText == null)
      return;
    if (this.mMaxLineLimit == 1)
    {
      String str = TextUtils.ellipsize(this.mText, this.mPaint, this.mRect.width(), TextUtils.TruncateAt.END).toString();
      this.mPaint.getTextBounds(str, 0, str.length(), this.mTextBound);
      float f = this.mRect.left;
      switch ($SWITCH_TABLE$android$text$Layout$Alignment()[this.mAlignment.ordinal()])
      {
      case 2:
      default:
      case 1:
      case 3:
      }
      while (true)
      {
        paramCanvas.drawText(str, f + this.mTranslationX, this.mTranslationY + this.mRect.centerY() - this.mTextBound.centerY(), this.mPaint);
        return;
        f = this.mRect.centerX() - this.mTextBound.width() / 2;
        continue;
        f = this.mRect.right - this.mTextBound.width();
      }
    }
    int i = paramCanvas.save();
    paramCanvas.translate(this.mTranslationX + this.mRect.left, this.mTranslationY + this.mRect.top);
    this.mLayout.draw(paramCanvas, this.mPaint, this.mAlignment, this.mVerticalAlignment, this.mRect.width(), this.mRect.height(), this.mIndentation, this.mExactIndentation);
    paramCanvas.restoreToCount(i);
  }

  public int getHeight()
  {
    if (this.mMaxLineLimit == 1)
      return this.mRect.height();
    return this.mLayout.getHeight(this.mPaint, this.mAlignment, this.mVerticalAlignment, this.mRect.width(), this.mRect.height(), this.mIndentation, this.mExactIndentation);
  }

  public int getLineBase(int paramInt)
  {
    if (this.mMaxLineLimit == 1)
      return this.mTranslationY + this.mRect.centerY() - this.mTextBound.centerY();
    return this.mTranslationY + this.mLayout.getLineBase(paramInt);
  }

  public int getLineCnt()
  {
    if (this.mMaxLineLimit == 1)
      return 1;
    return this.mLayout.getLineCnt(this.mPaint, this.mAlignment, this.mVerticalAlignment, this.mRect.width(), this.mRect.height(), this.mIndentation, this.mExactIndentation);
  }

  public int getMaxtLine()
  {
    return this.mMaxLineLimit;
  }

  public String getText()
  {
    return this.mText;
  }

  public int getWidth()
  {
    if (this.mMaxLineLimit == 1)
    {
      if (this.mText == null)
        return 0;
      String str = TextUtils.ellipsize(this.mText, this.mPaint, this.mRect.width(), TextUtils.TruncateAt.END).toString();
      this.mPaint.getTextBounds(str, 0, str.length(), this.mTextBound);
      return this.mTextBound.width();
    }
    return this.mLayout.getWidth(this.mPaint, this.mAlignment, this.mVerticalAlignment, this.mRect.width(), this.mRect.height(), this.mIndentation, this.mExactIndentation);
  }

  protected void onDrawElement(Canvas paramCanvas)
  {
    paramCanvas.save();
    drawText(paramCanvas);
    paramCanvas.restore();
  }

  protected void onMeasureElement(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    this.mRect.set(paramInt1, paramInt2, paramInt3, paramInt4);
  }

  public void setAlignment(Layout.Alignment paramAlignment)
  {
    this.mAlignment = paramAlignment;
  }

  public void setAlpha(int paramInt)
  {
    this.mPaint.setAlpha(paramInt);
  }

  public void setColor(int paramInt)
  {
    if (this.mLastColor != paramInt)
    {
      this.mPaint.setColor(paramInt);
      this.mLastColor = paramInt;
      invalidateElement(this.mRect);
    }
  }

  public void setExactFirstIndentation(float paramFloat)
  {
    this.mExactIndentation = paramFloat;
  }

  public void setFirstIndentation(int paramInt)
  {
    this.mIndentation = paramInt;
  }

  public void setMaxLineLimit(int paramInt)
  {
    this.mMaxLineLimit = paramInt;
    this.mLayout.setMaxLine(paramInt);
  }

  public void setText(String paramString)
  {
    setText(paramString, true);
  }

  public void setText(String paramString, boolean paramBoolean)
  {
    if (TextUtils.equals(this.mText, paramString))
      if (paramBoolean)
        invalidateElement(this.mRect);
    do
    {
      return;
      this.mText = paramString;
      this.mLayout.setText(this.mText);
    }
    while (!paramBoolean);
    invalidateElement(this.mRect);
  }

  public void setTextSize(float paramFloat)
  {
    this.mPaint.setTextSize(paramFloat);
  }

  public void setTypeFace(Typeface paramTypeface)
  {
    this.mPaint.setTypeface(paramTypeface);
  }

  public void setVerticalAlignment(VerticalAlignment paramVerticalAlignment)
  {
    this.mVerticalAlignment = paramVerticalAlignment;
  }

  public static enum VerticalAlignment
  {
    static
    {
      BOTTOM = new VerticalAlignment("BOTTOM", 1);
      TOP = new VerticalAlignment("TOP", 2);
      VerticalAlignment[] arrayOfVerticalAlignment = new VerticalAlignment[3];
      arrayOfVerticalAlignment[0] = CENTER;
      arrayOfVerticalAlignment[1] = BOTTOM;
      arrayOfVerticalAlignment[2] = TOP;
    }
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.framework.view.TextViewElement
 * JD-Core Version:    0.6.2
 */