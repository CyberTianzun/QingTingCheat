package master.flame.danmaku.danmaku.model.android;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import java.lang.reflect.Array;
import tv.cjump.jni.NativeBitmapFactory;

public class DrawingCacheHolder
{
  public Bitmap bitmap;
  public Bitmap[][] bitmapArray;
  public Canvas canvas;
  public boolean drawn;
  public Object extra;
  public int height;
  private int mDensity;
  public int width;

  public DrawingCacheHolder()
  {
  }

  public DrawingCacheHolder(int paramInt1, int paramInt2)
  {
    buildCache(paramInt1, paramInt2, 0, true);
  }

  public DrawingCacheHolder(int paramInt1, int paramInt2, int paramInt3)
  {
    this.mDensity = paramInt3;
    buildCache(paramInt1, paramInt2, paramInt3, true);
  }

  private void eraseBitmap(Bitmap paramBitmap)
  {
    if ((paramBitmap != null) && (!paramBitmap.isRecycled()))
      paramBitmap.eraseColor(0);
  }

  private void eraseBitmapArray()
  {
    if (this.bitmapArray != null)
      for (int i = 0; i < this.bitmapArray.length; i++)
        for (int j = 0; j < this.bitmapArray[i].length; j++)
          eraseBitmap(this.bitmapArray[i][j]);
  }

  private void recycleBitmapArray()
  {
    if (this.bitmapArray != null)
    {
      for (int i = 0; i < this.bitmapArray.length; i++)
        for (int j = 0; j < this.bitmapArray[i].length; j++)
          if (this.bitmapArray[i][j] != null)
          {
            this.bitmapArray[i][j].recycle();
            this.bitmapArray[i][j] = null;
          }
      this.bitmapArray = ((Bitmap[][])null);
    }
  }

  public void buildCache(int paramInt1, int paramInt2, int paramInt3, boolean paramBoolean)
  {
    int i = 1;
    if (paramBoolean)
      if ((paramInt1 != this.width) || (paramInt2 != this.height));
    while ((i != 0) && (this.bitmap != null) && (!this.bitmap.isRecycled()))
    {
      this.canvas.setBitmap(null);
      this.bitmap.eraseColor(0);
      this.canvas.setBitmap(this.bitmap);
      recycleBitmapArray();
      return;
      i = 0;
      continue;
      if ((paramInt1 > this.width) || (paramInt2 > this.height))
        i = 0;
    }
    if (this.bitmap != null)
      recycle();
    this.width = paramInt1;
    this.height = paramInt2;
    this.bitmap = NativeBitmapFactory.createBitmap(paramInt1, paramInt2, Bitmap.Config.ARGB_8888);
    if (paramInt3 > 0)
    {
      this.mDensity = paramInt3;
      this.bitmap.setDensity(paramInt3);
    }
    if (this.canvas == null)
    {
      this.canvas = new Canvas(this.bitmap);
      this.canvas.setDensity(paramInt3);
      return;
    }
    this.canvas.setBitmap(this.bitmap);
  }

  public final boolean draw(Canvas paramCanvas, float paramFloat1, float paramFloat2, Paint paramPaint)
  {
    label105: boolean bool1;
    if (this.bitmapArray != null)
    {
      for (int i = 0; i < this.bitmapArray.length; i++)
      {
        int j = 0;
        if (j < this.bitmapArray[i].length)
        {
          Bitmap localBitmap2 = this.bitmapArray[i][j];
          float f1;
          if ((localBitmap2 != null) && (!localBitmap2.isRecycled()))
          {
            f1 = paramFloat1 + j * localBitmap2.getWidth();
            if ((f1 <= paramCanvas.getWidth()) && (f1 + localBitmap2.getWidth() >= 0.0F))
              break label105;
          }
          while (true)
          {
            j++;
            break;
            float f2 = paramFloat2 + i * localBitmap2.getHeight();
            if ((f2 <= paramCanvas.getHeight()) && (f2 + localBitmap2.getHeight() >= 0.0F))
              paramCanvas.drawBitmap(localBitmap2, f1, f2, paramPaint);
          }
        }
      }
      bool1 = true;
    }
    boolean bool2;
    do
    {
      Bitmap localBitmap1;
      do
      {
        return bool1;
        localBitmap1 = this.bitmap;
        bool1 = false;
      }
      while (localBitmap1 == null);
      bool2 = this.bitmap.isRecycled();
      bool1 = false;
    }
    while (bool2);
    paramCanvas.drawBitmap(this.bitmap, paramFloat1, paramFloat2, paramPaint);
    return true;
  }

  public void erase()
  {
    eraseBitmap(this.bitmap);
    eraseBitmapArray();
  }

  public void recycle()
  {
    this.height = 0;
    this.width = 0;
    if (this.bitmap != null)
    {
      this.bitmap.recycle();
      this.bitmap = null;
    }
    recycleBitmapArray();
    this.extra = null;
  }

  @SuppressLint({"NewApi"})
  public void splitWith(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    recycleBitmapArray();
    if ((this.width <= 0) || (this.height <= 0) || (this.bitmap == null) || (this.bitmap.isRecycled()));
    while ((this.width <= paramInt3) && (this.height <= paramInt4))
      return;
    int i = Math.min(paramInt3, paramInt1);
    int j = Math.min(paramInt4, paramInt2);
    int k = this.width / i;
    int m;
    int n;
    int i2;
    label119: int i3;
    int i4;
    int i5;
    Bitmap[][] arrayOfBitmap;
    Rect localRect1;
    Rect localRect2;
    if (this.width % i == 0)
    {
      m = 0;
      n = k + m;
      int i1 = this.height / j;
      if (this.height % j != 0)
        break label370;
      i2 = 0;
      i3 = i1 + i2;
      i4 = this.width / n;
      i5 = this.height / i3;
      arrayOfBitmap = (Bitmap[][])Array.newInstance(Bitmap.class, new int[] { i3, n });
      if (this.canvas == null)
      {
        this.canvas = new Canvas();
        if (this.mDensity > 0)
          this.canvas.setDensity(this.mDensity);
      }
      localRect1 = new Rect();
      localRect2 = new Rect();
    }
    for (int i6 = 0; ; i6++)
    {
      if (i6 >= i3)
        break label382;
      int i7 = 0;
      while (true)
        if (i7 < n)
        {
          Bitmap[] arrayOfBitmap1 = arrayOfBitmap[i6];
          Bitmap localBitmap = NativeBitmapFactory.createBitmap(i4, i5, Bitmap.Config.ARGB_8888);
          arrayOfBitmap1[i7] = localBitmap;
          if (this.mDensity > 0)
            localBitmap.setDensity(this.mDensity);
          this.canvas.setBitmap(localBitmap);
          int i8 = i7 * i4;
          int i9 = i6 * i5;
          localRect1.set(i8, i9, i8 + i4, i9 + i5);
          localRect2.set(0, 0, localBitmap.getWidth(), localBitmap.getHeight());
          this.canvas.drawBitmap(this.bitmap, localRect1, localRect2, null);
          i7++;
          continue;
          m = 1;
          break;
          label370: i2 = 1;
          break label119;
        }
    }
    label382: this.canvas.setBitmap(this.bitmap);
    this.bitmapArray = arrayOfBitmap;
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     master.flame.danmaku.danmaku.model.android.DrawingCacheHolder
 * JD-Core Version:    0.6.2
 */