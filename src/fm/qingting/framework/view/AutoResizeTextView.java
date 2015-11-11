package fm.qingting.framework.view;

import android.content.Context;
import android.graphics.Canvas;
import android.text.Layout.Alignment;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.widget.TextView;

public class AutoResizeTextView extends TextView
{
  public static final float MIN_TEXT_SIZE = 20.0F;
  private static final String mEllipsis = "...";
  private static final Canvas sTextResizeCanvas = new Canvas();
  private boolean mAddEllipsis = true;
  private float mMaxTextSize = 0.0F;
  private float mMinTextSize = 20.0F;
  private boolean mNeedsResize = false;
  private float mSpacingAdd = 0.0F;
  private float mSpacingMult = 1.0F;
  private OnTextResizeListener mTextResizeListener;
  private float mTextSize = getTextSize();

  public AutoResizeTextView(Context paramContext)
  {
    this(paramContext, null);
  }

  public AutoResizeTextView(Context paramContext, AttributeSet paramAttributeSet)
  {
    this(paramContext, paramAttributeSet, 0);
  }

  public AutoResizeTextView(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
  }

  private int getTextHeight(CharSequence paramCharSequence, TextPaint paramTextPaint, int paramInt, float paramFloat)
  {
    paramTextPaint.setTextSize(paramFloat);
    StaticLayout localStaticLayout = new StaticLayout(paramCharSequence, paramTextPaint, paramInt, Layout.Alignment.ALIGN_NORMAL, this.mSpacingMult, this.mSpacingAdd, true);
    localStaticLayout.draw(sTextResizeCanvas);
    return localStaticLayout.getHeight();
  }

  public boolean getAddEllipsis()
  {
    return this.mAddEllipsis;
  }

  public float getMaxTextSize()
  {
    return this.mMaxTextSize;
  }

  public float getMinTextSize()
  {
    return this.mMinTextSize;
  }

  protected void onLayout(boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    if ((paramBoolean) || (this.mNeedsResize))
      resizeText(paramInt3 - paramInt1 - getCompoundPaddingLeft() - getCompoundPaddingRight(), paramInt4 - paramInt2 - getCompoundPaddingBottom() - getCompoundPaddingTop());
    super.onLayout(paramBoolean, paramInt1, paramInt2, paramInt3, paramInt4);
  }

  protected void onSizeChanged(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    if ((paramInt1 != paramInt3) || (paramInt2 != paramInt4))
      this.mNeedsResize = true;
  }

  protected void onTextChanged(CharSequence paramCharSequence, int paramInt1, int paramInt2, int paramInt3)
  {
    this.mNeedsResize = true;
    resetTextSize();
  }

  public void resetTextSize()
  {
    super.setTextSize(0, this.mTextSize);
    this.mMaxTextSize = this.mTextSize;
  }

  public void resizeText()
  {
    int i = getHeight() - getPaddingBottom() - getPaddingTop();
    resizeText(getWidth() - getPaddingLeft() - getPaddingRight(), i);
  }

  public void resizeText(int paramInt1, int paramInt2)
  {
    CharSequence localCharSequence = getText();
    if ((localCharSequence == null) || (localCharSequence.length() == 0) || (paramInt2 <= 0) || (paramInt1 <= 0) || (this.mTextSize == 0.0F))
      return;
    TextPaint localTextPaint = getPaint();
    float f1 = localTextPaint.getTextSize();
    float f2;
    if (this.mMaxTextSize > 0.0F)
      f2 = Math.min(this.mTextSize, this.mMaxTextSize);
    StaticLayout localStaticLayout;
    int j;
    for (int i = getTextHeight(localCharSequence, localTextPaint, paramInt1, f2); ; i = getTextHeight(localCharSequence, localTextPaint, paramInt1, f2))
    {
      if ((i <= paramInt2) || (f2 <= this.mMinTextSize))
      {
        if ((this.mAddEllipsis) && (f2 == this.mMinTextSize) && (i > paramInt2))
        {
          localStaticLayout = new StaticLayout(localCharSequence, localTextPaint, paramInt1, Layout.Alignment.ALIGN_NORMAL, this.mSpacingMult, this.mSpacingAdd, false);
          localStaticLayout.draw(sTextResizeCanvas);
          if (localStaticLayout.getLineCount() > 0)
          {
            j = -1 + localStaticLayout.getLineForVertical(paramInt2);
            if (j >= 0)
              break label267;
            setText("");
          }
        }
        localTextPaint.setTextSize(f2);
        setLineSpacing(this.mSpacingAdd, this.mSpacingMult);
        if (this.mTextResizeListener != null)
          this.mTextResizeListener.onTextResize(this, f1, f2);
        this.mNeedsResize = false;
        return;
        f2 = this.mTextSize;
        break;
      }
      f2 = Math.max(f2 - 2.0F, this.mMinTextSize);
    }
    label267: int k = localStaticLayout.getLineStart(j);
    int m = localStaticLayout.getLineEnd(j);
    float f3 = localStaticLayout.getLineWidth(j);
    float f4 = localTextPaint.measureText("...");
    while (true)
    {
      if (paramInt1 >= f3 + f4)
      {
        setText(localCharSequence.subSequence(0, m) + "...");
        break;
      }
      m--;
      f3 = localTextPaint.measureText(localCharSequence.subSequence(k, m + 1).toString());
    }
  }

  public void setAddEllipsis(boolean paramBoolean)
  {
    this.mAddEllipsis = paramBoolean;
  }

  public void setLineSpacing(float paramFloat1, float paramFloat2)
  {
    super.setLineSpacing(paramFloat1, paramFloat2);
    this.mSpacingMult = paramFloat2;
    this.mSpacingAdd = paramFloat1;
  }

  public void setMaxTextSize(float paramFloat)
  {
    this.mMaxTextSize = paramFloat;
    requestLayout();
    invalidate();
  }

  public void setMinTextSize(float paramFloat)
  {
    this.mMinTextSize = paramFloat;
    requestLayout();
    invalidate();
  }

  public void setOnResizeListener(OnTextResizeListener paramOnTextResizeListener)
  {
    this.mTextResizeListener = paramOnTextResizeListener;
  }

  public void setTextSize(float paramFloat)
  {
    super.setTextSize(paramFloat);
    this.mTextSize = getTextSize();
  }

  public void setTextSize(int paramInt, float paramFloat)
  {
    super.setTextSize(paramInt, paramFloat);
    this.mTextSize = getTextSize();
  }

  public static abstract interface OnTextResizeListener
  {
    public abstract void onTextResize(TextView paramTextView, float paramFloat1, float paramFloat2);
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.framework.view.AutoResizeTextView
 * JD-Core Version:    0.6.2
 */