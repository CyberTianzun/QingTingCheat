package fm.qingting.qtradio.view.popviews;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import fm.qingting.framework.utils.BitmapResourceCache;
import fm.qingting.framework.view.AbsCheckBoxElement;

class CheckBoxElementInternal extends AbsCheckBoxElement
{
  private final Paint mPaint = new Paint();

  public CheckBoxElementInternal(Context paramContext)
  {
    super(paramContext);
  }

  protected void drawCheckState(Canvas paramCanvas)
  {
    BitmapResourceCache localBitmapResourceCache = BitmapResourceCache.getInstance();
    Resources localResources = getContext().getResources();
    int i = this.mOwnerId;
    if (isChecked());
    for (int j = 2130837710; ; j = 2130837814)
    {
      paramCanvas.drawBitmap(localBitmapResourceCache.getResourceCacheByParent(localResources, i, j), null, getBound(), this.mPaint);
      return;
    }
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.qtradio.view.popviews.CheckBoxElementInternal
 * JD-Core Version:    0.6.2
 */