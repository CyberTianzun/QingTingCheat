package fm.qingting.qtradio.view.navigation.items;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import fm.qingting.framework.utils.BitmapResourceCache;
import fm.qingting.framework.view.AbsCheckBoxElement;

class SingleCheckBoxElement extends AbsCheckBoxElement
{
  private final Paint mPaint = new Paint();

  public SingleCheckBoxElement(Context paramContext)
  {
    super(paramContext);
  }

  protected void drawCheckState(Canvas paramCanvas)
  {
    Rect localRect = getBound();
    if ((this.mTranslationX != 0) || (this.mTranslationY != 0))
      localRect.offset(this.mTranslationX, this.mTranslationY);
    BitmapResourceCache localBitmapResourceCache = BitmapResourceCache.getInstance();
    Resources localResources = getContext().getResources();
    int i = this.mOwnerId;
    if (isChecked());
    for (int j = 2130837864; ; j = 2130837863)
    {
      paramCanvas.drawBitmap(localBitmapResourceCache.getResourceCacheByParent(localResources, i, j), null, localRect, this.mPaint);
      if ((this.mTranslationX != 0) || (this.mTranslationY != 0))
        localRect.offset(-this.mTranslationX, -this.mTranslationY);
      return;
    }
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.qtradio.view.navigation.items.SingleCheckBoxElement
 * JD-Core Version:    0.6.2
 */