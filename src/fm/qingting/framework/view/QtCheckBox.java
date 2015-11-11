package fm.qingting.framework.view;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.DrawFilter;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.Rect;
import android.text.Layout.Alignment;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.TextUtils.TruncateAt;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.View.OnClickListener;
import fm.qingting.framework.utils.BitmapResourceCache;

public class QtCheckBox extends ViewImpl
  implements View.OnClickListener
{
  private final DrawFilter filter = new PaintFlagsDrawFilter(0, 67);
  private Layout.Alignment mAlignment = Layout.Alignment.ALIGN_CENTER;
  private boolean mBroadcasting;
  private boolean mChecked;
  private TextUtils.TruncateAt mEllipsizeWhere = TextUtils.TruncateAt.END;
  private boolean mEnabled = true;
  private int mHighlightRes = 0;
  private int mNormalRes = 0;
  private OnQtCheckedChangeListener mOnQtCheckedChangeListener;
  private TextPaint mPaint = new TextPaint();
  private String mText;
  private float mTextWidthScale = 1.0F;
  private final ViewLayout standardLayout = ViewLayout.createViewLayoutWithBoundsLT(480, 800, 480, 800, 0, 0, ViewLayout.FILL);

  public QtCheckBox(Context paramContext)
  {
    super(paramContext);
    setOnClickListener(this);
  }

  private void drawBg(Canvas paramCanvas)
  {
    BitmapResourceCache localBitmapResourceCache1 = BitmapResourceCache.getInstance();
    Resources localResources1 = getResources();
    int i;
    Bitmap localBitmap;
    BitmapResourceCache localBitmapResourceCache2;
    Resources localResources2;
    if (this.mChecked)
    {
      i = this.mHighlightRes;
      localBitmap = localBitmapResourceCache1.getResourceCache(localResources1, this, i);
      if (localBitmap == null)
      {
        localBitmapResourceCache2 = BitmapResourceCache.getInstance();
        localResources2 = getResources();
        if (!this.mChecked)
          break label119;
      }
    }
    label119: for (int j = this.mNormalRes; ; j = this.mHighlightRes)
    {
      localBitmap = localBitmapResourceCache2.getResourceCache(localResources2, this, j);
      if (localBitmap != null)
        paramCanvas.drawBitmap(localBitmap, null, new Rect(0, 0, this.standardLayout.width, this.standardLayout.height), null);
      return;
      i = this.mNormalRes;
      break;
    }
  }

  private void drawTitle(Canvas paramCanvas)
  {
    if ((this.mText == null) || (this.mText.equalsIgnoreCase("")))
      return;
    String str = TextUtils.ellipsize(this.mText, this.mPaint, this.standardLayout.width * this.mTextWidthScale, this.mEllipsizeWhere).toString();
    Rect localRect = new Rect();
    this.mPaint.getTextBounds(str, 0, str.length(), localRect);
    float f1 = (this.standardLayout.height - localRect.bottom - localRect.top) / 2.0F;
    int i = $SWITCH_TABLE$android$text$Layout$Alignment()[this.mAlignment.ordinal()];
    float f2 = 0.0F;
    switch (i)
    {
    default:
    case 1:
    case 2:
    case 3:
    }
    while (true)
    {
      paramCanvas.drawText(str, f2, f1, this.mPaint);
      return;
      f2 = (this.standardLayout.width - localRect.width()) / 2.0F;
      continue;
      f2 = (this.standardLayout.width - this.standardLayout.width * this.mTextWidthScale) / 2.0F;
      continue;
      f2 = (this.standardLayout.width + this.standardLayout.width * this.mTextWidthScale) / 2.0F - localRect.width();
    }
  }

  private void reSize()
  {
  }

  public void close(boolean paramBoolean)
  {
    BitmapResourceCache.getInstance().clearResourceCacheOfOne(this, 0);
    super.close(paramBoolean);
  }

  public boolean isChecked()
  {
    return this.mChecked;
  }

  public void onClick(View paramView)
  {
  }

  protected void onDraw(Canvas paramCanvas)
  {
    paramCanvas.setDrawFilter(this.filter);
    paramCanvas.save();
    drawBg(paramCanvas);
    drawTitle(paramCanvas);
    paramCanvas.restore();
  }

  protected void onMeasure(int paramInt1, int paramInt2)
  {
    this.standardLayout.scaleToBounds(View.MeasureSpec.getSize(paramInt1), View.MeasureSpec.getSize(paramInt2));
    reSize();
    setMeasuredDimension(this.standardLayout.width, this.standardLayout.height);
  }

  public boolean performClick()
  {
    toggle();
    return super.performClick();
  }

  public void setAlignment(Layout.Alignment paramAlignment)
  {
    this.mAlignment = paramAlignment;
  }

  public void setCheckStateResources(int paramInt1, int paramInt2)
  {
    this.mNormalRes = paramInt1;
    this.mHighlightRes = paramInt2;
  }

  public void setChecked(boolean paramBoolean)
  {
    if (!this.mEnabled);
    do
    {
      do
        return;
      while (this.mChecked == paramBoolean);
      this.mChecked = paramBoolean;
      invalidate();
    }
    while (this.mBroadcasting);
    this.mBroadcasting = true;
    if (this.mOnQtCheckedChangeListener != null)
      this.mOnQtCheckedChangeListener.onCheckedChanged(this, this.mChecked);
    this.mBroadcasting = false;
  }

  public void setCheckedWithoutBroadcast(boolean paramBoolean)
  {
    if (!this.mEnabled);
    while (this.mChecked == paramBoolean)
      return;
    this.mChecked = paramBoolean;
    invalidate();
  }

  public void setEllipsizeWhere(TextUtils.TruncateAt paramTruncateAt)
  {
    this.mEllipsizeWhere = paramTruncateAt;
  }

  public void setEnable(boolean paramBoolean)
  {
    if ((this.mEnabled != paramBoolean) && (!paramBoolean))
    {
      this.mChecked = false;
      invalidate();
    }
    this.mEnabled = paramBoolean;
  }

  public void setOnQtCheckedChangeListener(OnQtCheckedChangeListener paramOnQtCheckedChangeListener)
  {
    this.mOnQtCheckedChangeListener = paramOnQtCheckedChangeListener;
  }

  public void setText(String paramString)
  {
    this.mText = paramString;
    invalidate();
  }

  public void setTextColor(int paramInt)
  {
    this.mPaint.setColor(paramInt);
  }

  public void setTextSize(float paramFloat)
  {
    this.mPaint.setTextSize(paramFloat);
    invalidate();
  }

  public void setTextWidthScale(float paramFloat)
  {
    this.mTextWidthScale = paramFloat;
  }

  public void toggle()
  {
    if (this.mChecked);
    for (boolean bool = false; ; bool = true)
    {
      setChecked(bool);
      return;
    }
  }

  public static abstract interface OnQtCheckedChangeListener
  {
    public abstract void onCheckedChanged(QtCheckBox paramQtCheckBox, boolean paramBoolean);
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.framework.view.QtCheckBox
 * JD-Core Version:    0.6.2
 */