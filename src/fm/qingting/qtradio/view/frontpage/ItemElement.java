package fm.qingting.qtradio.view.frontpage;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import fm.qingting.framework.utils.BitmapResourceCache;
import fm.qingting.framework.view.ViewElement;
import fm.qingting.framework.view.ViewLayout;
import fm.qingting.qtradio.manager.SkinManager;

class ItemElement extends ViewElement
{
  private static final int NORMAL = 0;
  private static final int SELECTED = 1;
  private final ViewLayout iconLayout = this.itemLayout.createChildLT(48, 48, 51, 16, ViewLayout.SCALE_FLAG_SLTCW);
  private final ViewLayout itemLayout = ViewLayout.createViewLayoutWithBoundsLT(150, 114, 150, 114, 0, 0, ViewLayout.FILL);
  private int[] mIconResources;
  private String mName;
  private final Paint mNameHighlightPaint = new Paint();
  private final Paint mNameNormalPaint = new Paint();
  private final Paint mPaint = new Paint();
  private final Paint mPressedPaint = new Paint();
  private final Rect mRect = new Rect();
  private final Rect mTextBound = new Rect();
  private final ViewLayout nameLayout = this.itemLayout.createChildLT(150, 30, 0, 70, ViewLayout.SCALE_FLAG_SLTCW);

  public ItemElement(Context paramContext)
  {
    super(paramContext);
    this.mNameNormalPaint.setColor(-1);
    this.mNameHighlightPaint.setColor(-855638017);
    this.mPressedPaint.setAlpha(204);
  }

  protected void onDrawElement(Canvas paramCanvas)
  {
    int i = getLeftMargin();
    int j = getTopMargin();
    this.mRect.offset(i, j);
    if ((isPressed()) || (isSelected()));
    for (int k = 1; ; k = 0)
    {
      Bitmap localBitmap = BitmapResourceCache.getInstance().getResourceCacheByParent(getContext().getResources(), this.mOwnerId, this.mIconResources[k]);
      Rect localRect = this.mRect;
      Paint localPaint1;
      String str;
      float f1;
      float f2;
      if (isPressed())
      {
        localPaint1 = this.mPressedPaint;
        paramCanvas.drawBitmap(localBitmap, null, localRect, localPaint1);
        this.mRect.offset(-i, -j);
        this.mNameNormalPaint.getTextBounds(this.mName, 0, this.mName.length(), this.mTextBound);
        str = this.mName;
        f1 = i + (this.nameLayout.width - this.mTextBound.left - this.mTextBound.right) / 2;
        f2 = j + this.nameLayout.topMargin + (this.nameLayout.height - this.mTextBound.top - this.mTextBound.bottom) / 2;
        if (!isPressed())
          break label235;
      }
      label235: for (Paint localPaint2 = this.mNameHighlightPaint; ; localPaint2 = this.mNameNormalPaint)
      {
        paramCanvas.drawText(str, f1, f2, localPaint2);
        return;
        localPaint1 = this.mPaint;
        break;
      }
    }
  }

  protected void onMeasureElement(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    this.itemLayout.scaleToBounds(paramInt3 - paramInt1, paramInt4 - paramInt2);
    this.iconLayout.scaleToBounds(this.itemLayout);
    this.nameLayout.scaleToBounds(this.itemLayout);
    this.mRect.set(this.iconLayout.leftMargin, this.iconLayout.topMargin, this.iconLayout.getRight(), this.iconLayout.getBottom());
    this.mNameNormalPaint.setTextSize(SkinManager.getInstance().getTinyTextSize());
    this.mNameHighlightPaint.setTextSize(SkinManager.getInstance().getTinyTextSize());
  }

  public void setAction(String paramString, int[] paramArrayOfInt)
  {
    this.mIconResources = paramArrayOfInt;
    this.mName = paramString;
    invalidateElement();
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.qtradio.view.frontpage.ItemElement
 * JD-Core Version:    0.6.2
 */