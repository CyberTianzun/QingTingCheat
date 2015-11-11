package fm.qingting.qtradio.view.moreContentView;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Shader.TileMode;
import android.widget.ImageView;
import fm.qingting.framework.utils.ImageLoader;
import fm.qingting.framework.utils.ImageLoaderHandler;
import fm.qingting.framework.view.ViewElement;

class RoundAvatarElement extends ViewElement
  implements ImageLoaderHandler
{
  private int mCachedBitmapId;
  private boolean mError = false;
  private final Rect mImageRect = new Rect();
  private final Paint mPaint = new Paint();
  private String mUrl;

  public RoundAvatarElement(Context paramContext)
  {
    super(paramContext);
  }

  private void drawRoundBitmap(Canvas paramCanvas, Bitmap paramBitmap)
  {
    int i = paramBitmap.hashCode();
    if (this.mCachedBitmapId != i)
    {
      BitmapShader localBitmapShader1 = new BitmapShader(paramBitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
      this.mPaint.setShader(localBitmapShader1);
      this.mCachedBitmapId = i;
    }
    int j;
    int k;
    int m;
    while (true)
    {
      j = paramBitmap.getWidth();
      k = paramBitmap.getHeight();
      m = Math.min(j, k);
      if (m != 0)
        break;
      return;
      if (this.mPaint.getShader() == null)
      {
        BitmapShader localBitmapShader2 = new BitmapShader(paramBitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        this.mPaint.setShader(localBitmapShader2);
        this.mCachedBitmapId = i;
      }
    }
    float f1 = m / 2.0F;
    float f2 = this.mImageRect.width() / m;
    float f3 = (f1 + j / 2.0F) / 2.0F;
    float f4 = (f1 + k / 2.0F) / 2.0F;
    int n = paramCanvas.save();
    paramCanvas.scale(f2, f2, this.mImageRect.centerX() + this.mTranslationX, this.mImageRect.centerY() + this.mTranslationY);
    paramCanvas.translate(this.mImageRect.centerX() + this.mTranslationX - f3, this.mImageRect.centerY() + this.mTranslationY - f4);
    paramCanvas.drawCircle(f3, f4, f1, this.mPaint);
    paramCanvas.restoreToCount(n);
  }

  public void loadImageFinish(boolean paramBoolean, String paramString, Bitmap paramBitmap, int paramInt1, int paramInt2)
  {
    if ((paramBoolean) && (this.mUrl != null) && (this.mUrl.equalsIgnoreCase(paramString)))
    {
      this.mError = false;
      invalidateElement(this.mImageRect);
    }
    while (paramBoolean)
      return;
    this.mError = true;
    invalidateElement(this.mImageRect);
  }

  protected void onDrawElement(Canvas paramCanvas)
  {
    paramCanvas.save();
    if ((this.mUrl == null) || (this.mUrl.equalsIgnoreCase("")))
      paramCanvas.drawColor(0);
    while (true)
    {
      paramCanvas.restore();
      return;
      Bitmap localBitmap = ImageLoader.getInstance(getContext()).getImage(this.mUrl, this.mImageRect.width(), this.mImageRect.height());
      if (localBitmap == null)
      {
        paramCanvas.drawColor(0);
        if (!this.mError)
          ImageLoader.getInstance(getContext()).loadImage(this.mUrl, null, this, this.mImageRect.width(), this.mImageRect.height(), this);
      }
      else
      {
        drawRoundBitmap(paramCanvas, localBitmap);
      }
    }
  }

  protected void onMeasureElement(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    this.mImageRect.set(paramInt1, paramInt2, paramInt3, paramInt4);
  }

  public void setImageUrl(String paramString)
  {
    this.mUrl = paramString;
    this.mError = false;
    invalidateAll();
  }

  public void updateImageViewFinish(boolean paramBoolean, ImageView paramImageView, String paramString, Bitmap paramBitmap)
  {
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.qtradio.view.moreContentView.RoundAvatarElement
 * JD-Core Version:    0.6.2
 */