package fm.qingting.framework.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.text.TextUtils;
import android.widget.ImageView;
import fm.qingting.framework.utils.BitmapResourceCache;
import fm.qingting.framework.utils.ImageLoader;
import fm.qingting.framework.utils.ImageLoaderHandler;

public class NetImageViewElement extends ViewElement
  implements ImageLoaderHandler
{
  private Bitmap mBitmap;
  private boolean mBound = false;
  private Paint mBoundPaint = new Paint();
  private CLAMPTYPE mClamp = CLAMPTYPE.FILL;
  private final Paint mCornerPaint = new Paint();
  private Path mCornerPath;
  private int mDefaultColor = 0;
  private int mDefaultImageRes = 0;
  private Rect mDstRect = new Rect();
  private boolean mError = false;
  private int mHighlightColor = 0;
  private final Rect mImageRect = new Rect();
  private boolean mLoading = false;
  private int mLoadingImageRes = 0;
  private final Paint mPaint = new Paint();
  private boolean mRoundCorner;
  private Rect mSrcRect = new Rect();
  private String mUrl;

  public NetImageViewElement(Context paramContext)
  {
    super(paramContext);
    this.mBoundPaint.setStyle(Paint.Style.STROKE);
  }

  private void adjustRect(Bitmap paramBitmap)
  {
    switch ($SWITCH_TABLE$fm$qingting$framework$view$NetImageViewElement$CLAMPTYPE()[this.mClamp.ordinal()])
    {
    default:
      return;
    case 1:
      this.mSrcRect.set(0, 0, paramBitmap.getWidth(), paramBitmap.getHeight());
      this.mDstRect.set(this.mImageRect);
      return;
    case 2:
      if (paramBitmap.getHeight() * this.mImageRect.width() > this.mImageRect.height() * paramBitmap.getWidth())
      {
        this.mSrcRect.set(0, paramBitmap.getHeight() - paramBitmap.getWidth() * this.mImageRect.height() / this.mImageRect.width(), paramBitmap.getWidth(), paramBitmap.getHeight());
        this.mDstRect.set(this.mImageRect);
        return;
      }
      this.mSrcRect.set(0, 0, paramBitmap.getWidth(), paramBitmap.getHeight());
      int m = (this.mImageRect.height() - this.mImageRect.width() * paramBitmap.getHeight() / paramBitmap.getWidth()) / 2;
      this.mDstRect.set(this.mImageRect.left, m, this.mImageRect.right, this.mImageRect.bottom - m);
      return;
    case 3:
      if (paramBitmap.getHeight() * this.mImageRect.width() > this.mImageRect.height() * paramBitmap.getWidth())
      {
        this.mSrcRect.set(0, 0, paramBitmap.getWidth(), paramBitmap.getWidth() * this.mImageRect.height() / this.mImageRect.width());
        this.mDstRect.set(this.mImageRect);
        return;
      }
      this.mSrcRect.set(0, 0, paramBitmap.getWidth(), paramBitmap.getHeight());
      int k = (this.mImageRect.height() - this.mImageRect.width() * paramBitmap.getHeight() / paramBitmap.getWidth()) / 2;
      this.mDstRect.set(this.mImageRect.left, k, this.mImageRect.right, this.mImageRect.bottom - k);
      return;
    case 4:
    }
    if (paramBitmap.getHeight() * this.mImageRect.width() > this.mImageRect.height() * paramBitmap.getWidth())
    {
      int j = (paramBitmap.getHeight() - paramBitmap.getWidth() * this.mImageRect.height() / this.mImageRect.width()) / 2;
      this.mSrcRect.set(0, j, paramBitmap.getWidth(), paramBitmap.getHeight() - j);
      this.mDstRect.set(this.mImageRect);
      return;
    }
    int i = (paramBitmap.getWidth() - paramBitmap.getHeight() * this.mImageRect.width() / this.mImageRect.height()) / 2;
    this.mSrcRect.set(-i, 0, i + paramBitmap.getWidth(), paramBitmap.getHeight());
    this.mDstRect.set(this.mImageRect);
  }

  private void drawBound(Canvas paramCanvas)
  {
    if (this.mBound)
      paramCanvas.drawRect(this.mImageRect, this.mBoundPaint);
  }

  private void drawCorner(Canvas paramCanvas)
  {
    if ((!this.mRoundCorner) || (this.mCornerPath == null))
      return;
    paramCanvas.drawPath(this.mCornerPath, this.mCornerPaint);
  }

  private void drawDefault(Canvas paramCanvas)
  {
    if (this.mDefaultColor != 0)
    {
      i = paramCanvas.save();
      paramCanvas.clipRect(this.mImageRect);
      paramCanvas.drawColor(this.mDefaultColor);
      paramCanvas.restoreToCount(i);
    }
    while (this.mDefaultImageRes == 0)
    {
      int i;
      return;
    }
    paramCanvas.drawBitmap(BitmapResourceCache.getInstance().getResourceCacheByParent(getContext().getResources(), this.mOwnerId, this.mDefaultImageRes), null, this.mImageRect, this.mPaint);
  }

  private void drawHighlight(Canvas paramCanvas)
  {
    if ((this.mHighlightColor != 0) && (isPressed()))
    {
      int i = paramCanvas.save();
      paramCanvas.clipRect(this.mImageRect);
      paramCanvas.drawColor(this.mHighlightColor);
      paramCanvas.restoreToCount(i);
    }
  }

  private void drawLoading(Canvas paramCanvas)
  {
    if (this.mLoadingImageRes != 0)
    {
      paramCanvas.drawBitmap(BitmapResourceCache.getInstance().getResourceCacheByParent(getContext().getResources(), this.mOwnerId, this.mLoadingImageRes), null, this.mImageRect, this.mPaint);
      return;
    }
    drawDefault(paramCanvas);
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
    this.mLoading = false;
    invalidateElement(this.mImageRect);
  }

  protected void onDrawElement(Canvas paramCanvas)
  {
    this.mImageRect.offset(this.mTranslationX, this.mTranslationY);
    paramCanvas.save();
    if (((this.mUrl == null) || (this.mUrl.equalsIgnoreCase(""))) && (this.mBitmap == null))
      drawDefault(paramCanvas);
    while (true)
    {
      drawHighlight(paramCanvas);
      drawCorner(paramCanvas);
      drawBound(paramCanvas);
      paramCanvas.restore();
      this.mImageRect.offset(-this.mTranslationX, -this.mTranslationY);
      return;
      Bitmap localBitmap = this.mBitmap;
      if (localBitmap == null)
        localBitmap = ImageLoader.getInstance(getContext()).getImage(this.mUrl, this.mImageRect.width(), this.mImageRect.height());
      if (localBitmap == null)
      {
        if (this.mError)
        {
          drawDefault(paramCanvas);
        }
        else
        {
          drawLoading(paramCanvas);
          this.mLoading = true;
          ImageLoader.getInstance(getContext()).loadImage(this.mUrl, null, this, this.mImageRect.width(), this.mImageRect.height(), this);
        }
      }
      else
      {
        adjustRect(localBitmap);
        paramCanvas.drawBitmap(localBitmap, this.mSrcRect, this.mDstRect, this.mPaint);
      }
    }
  }

  protected void onMeasureElement(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    this.mImageRect.set(paramInt1, paramInt2, paramInt3, paramInt4);
  }

  public void setBoundColor(int paramInt)
  {
    this.mBoundPaint.setColor(paramInt);
  }

  public void setBoundLineWidth(float paramFloat)
  {
    this.mBound = true;
    this.mBoundPaint.setStrokeWidth(paramFloat);
  }

  public void setClampType(CLAMPTYPE paramCLAMPTYPE)
  {
    this.mClamp = paramCLAMPTYPE;
  }

  public void setDefaultColor(int paramInt)
  {
    this.mDefaultColor = paramInt;
  }

  public void setDefaultImageRes(int paramInt)
  {
    this.mDefaultImageRes = paramInt;
  }

  public void setHighlightColor(int paramInt)
  {
    this.mHighlightColor = paramInt;
  }

  public void setImageBitmap(Bitmap paramBitmap)
  {
    this.mBitmap = paramBitmap;
  }

  public void setImageUrl(String paramString)
  {
    setImageUrl(paramString, true);
  }

  public void setImageUrl(String paramString, boolean paramBoolean)
  {
    if (TextUtils.equals(paramString, this.mUrl));
    do
    {
      return;
      this.mUrl = paramString;
      this.mError = false;
      this.mBitmap = null;
      this.mLoading = false;
    }
    while (!paramBoolean);
    invalidateAll();
  }

  public void setLoadingImageRes(int paramInt)
  {
    this.mLoadingImageRes = paramInt;
  }

  public void setRoundCorner(int paramInt)
  {
    if (this.mImageRect.isEmpty())
      return;
    if (this.mCornerPath == null)
      throw new IllegalStateException("must set roundcorner enable first");
    this.mCornerPath.rewind();
    int i = this.mImageRect.left;
    int j = this.mImageRect.top;
    int k = this.mImageRect.right;
    int m = this.mImageRect.bottom;
    this.mCornerPath.moveTo(i + paramInt, j);
    this.mCornerPath.lineTo(i, j);
    this.mCornerPath.lineTo(i, j + paramInt);
    this.mCornerPath.arcTo(new RectF(i, j, i + paramInt, j + paramInt), -180.0F, 90.0F);
    this.mCornerPath.moveTo(k - paramInt, j);
    this.mCornerPath.lineTo(k, j);
    this.mCornerPath.lineTo(k, j + paramInt);
    this.mCornerPath.arcTo(new RectF(k - paramInt, j, k, j + paramInt), 0.0F, -90.0F);
    this.mCornerPath.moveTo(k, m - paramInt);
    this.mCornerPath.lineTo(k, m);
    this.mCornerPath.lineTo(k - paramInt, m);
    this.mCornerPath.arcTo(new RectF(k - paramInt, m - paramInt, k, m), 90.0F, -90.0F);
    this.mCornerPath.moveTo(i + paramInt, m);
    this.mCornerPath.lineTo(i, m);
    this.mCornerPath.lineTo(i, m - paramInt);
    this.mCornerPath.arcTo(new RectF(i, m - paramInt, i + paramInt, m), 180.0F, -90.0F);
  }

  public void setRoundCorner(boolean paramBoolean)
  {
    this.mRoundCorner = paramBoolean;
    if (this.mCornerPath == null)
    {
      this.mCornerPath = new Path();
      return;
    }
    this.mCornerPath.reset();
  }

  public void setRoundCornerColor(int paramInt)
  {
    setRoundCorner(true);
    this.mCornerPaint.setColor(paramInt);
    this.mCornerPaint.setStyle(Paint.Style.FILL);
  }

  public void updateImageViewFinish(boolean paramBoolean, ImageView paramImageView, String paramString, Bitmap paramBitmap)
  {
  }

  public static enum CLAMPTYPE
  {
    static
    {
      CLIPTOP = new CLAMPTYPE("CLIPTOP", 1);
      CLIPBOTTOM = new CLAMPTYPE("CLIPBOTTOM", 2);
      CLIPBOTH = new CLAMPTYPE("CLIPBOTH", 3);
      CLAMPTYPE[] arrayOfCLAMPTYPE = new CLAMPTYPE[4];
      arrayOfCLAMPTYPE[0] = FILL;
      arrayOfCLAMPTYPE[1] = CLIPTOP;
      arrayOfCLAMPTYPE[2] = CLIPBOTTOM;
      arrayOfCLAMPTYPE[3] = CLIPBOTH;
    }
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.framework.view.NetImageViewElement
 * JD-Core Version:    0.6.2
 */