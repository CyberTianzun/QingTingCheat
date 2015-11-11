package fm.qingting.qtradio.manager;

import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import java.io.FileOutputStream;
import java.lang.reflect.Array;

public class BlurBitmap
{
  private int _height = -1;
  private Bitmap _image;
  private int _width = -1;
  private boolean alpha = false;
  private int[] currentPixels;
  private Rect mRect;
  private int[] originalPixels;

  public BlurBitmap(Bitmap paramBitmap)
  {
    this._width = paramBitmap.getWidth();
    this._height = paramBitmap.getHeight();
    this._image = paramBitmap;
    this.mRect = new Rect(0, 0, paramBitmap.getWidth(), paramBitmap.getHeight());
    this.originalPixels = new int[this._width * this._height];
    this._image.getPixels(this.originalPixels, 0, this._width, 0, 0, this._width, this._height);
  }

  public BlurBitmap(Bitmap paramBitmap, Rect paramRect)
  {
    this._width = paramRect.width();
    this._height = paramRect.height();
    this._image = paramBitmap;
    this.originalPixels = new int[this._width * this._height];
    this.mRect = paramRect;
    this._image.getPixels(this.originalPixels, 0, this._width, paramRect.left, paramRect.top, this._width, this._height);
  }

  public Bitmap getImage()
  {
    return this._image;
  }

  public void process(int paramInt)
  {
    if (paramInt < 1)
      paramInt = 1;
    this.currentPixels = ((int[])this.originalPixels.clone());
    int i = -1 + this._width;
    int j = -1 + this._height;
    int k = this._width * this._height;
    int m = 1 + (paramInt + paramInt);
    int[] arrayOfInt1 = new int[k];
    int[] arrayOfInt2 = new int[k];
    int[] arrayOfInt3 = new int[k];
    int[] arrayOfInt4 = new int[Math.max(this._width, this._height)];
    int n = m + 1 >> 1;
    int i1 = n * n;
    int[] arrayOfInt5 = new int[i1 * 256];
    for (int i2 = 0; i2 < i1 * 256; i2++)
      arrayOfInt5[i2] = (i2 / i1);
    int i3 = 0;
    int[] arrayOfInt6 = { m, 3 };
    int[][] arrayOfInt = (int[][])Array.newInstance(Integer.TYPE, arrayOfInt6);
    int i4 = paramInt + 1;
    int i5 = 0;
    int i6 = 0;
    while (i5 < this._height)
    {
      int i47 = 0;
      int i48 = -paramInt;
      int i49 = 0;
      int i50 = 0;
      int i51 = 0;
      int i52 = 0;
      int i53 = i48;
      int i54 = 0;
      int i55 = 0;
      int i56 = 0;
      int i57 = 0;
      if (i53 <= paramInt)
      {
        int i73 = this.currentPixels[(i6 + Math.min(i, Math.max(i53, 0)))];
        int[] arrayOfInt12 = arrayOfInt[(i53 + paramInt)];
        arrayOfInt12[0] = ((0xFF0000 & i73) >> 16);
        arrayOfInt12[1] = ((0xFF00 & i73) >> 8);
        arrayOfInt12[2] = (i73 & 0xFF);
        int i74 = i4 - Math.abs(i53);
        i56 += i74 * arrayOfInt12[0];
        i55 += i74 * arrayOfInt12[1];
        i54 += i74 * arrayOfInt12[2];
        if (i53 > 0)
        {
          i49 += arrayOfInt12[0];
          i57 += arrayOfInt12[1];
          i47 += arrayOfInt12[2];
        }
        while (true)
        {
          i53++;
          break;
          i52 += arrayOfInt12[0];
          i51 += arrayOfInt12[1];
          i50 += arrayOfInt12[2];
        }
      }
      int i58 = i56;
      int i59 = i55;
      int i60 = i54;
      int i61 = 0;
      int i62 = paramInt;
      if (i61 < this._width)
      {
        if (!this.alpha)
          if (Color.alpha(this.originalPixels[(i61 + i5 * this._width)]) == 255)
            break label768;
        label768: for (boolean bool = true; ; bool = false)
        {
          this.alpha = bool;
          arrayOfInt1[i6] = arrayOfInt5[i58];
          arrayOfInt2[i6] = arrayOfInt5[i59];
          arrayOfInt3[i6] = arrayOfInt5[i60];
          int i63 = i58 - i52;
          int i64 = i59 - i51;
          int i65 = i60 - i50;
          int[] arrayOfInt10 = arrayOfInt[((m + (i62 - paramInt)) % m)];
          int i66 = i52 - arrayOfInt10[0];
          int i67 = i51 - arrayOfInt10[1];
          int i68 = i50 - arrayOfInt10[2];
          if (i5 == 0)
            arrayOfInt4[i61] = Math.min(1 + (i61 + paramInt), i);
          int i69 = this.currentPixels[(i3 + arrayOfInt4[i61])];
          arrayOfInt10[0] = ((0xFF0000 & i69) >> 16);
          arrayOfInt10[1] = ((0xFF00 & i69) >> 8);
          arrayOfInt10[2] = (i69 & 0xFF);
          int i70 = i49 + arrayOfInt10[0];
          int i71 = i57 + arrayOfInt10[1];
          int i72 = i47 + arrayOfInt10[2];
          i58 = i63 + i70;
          i59 = i64 + i71;
          i60 = i65 + i72;
          i62 = (i62 + 1) % m;
          int[] arrayOfInt11 = arrayOfInt[(i62 % m)];
          i52 = i66 + arrayOfInt11[0];
          i51 = i67 + arrayOfInt11[1];
          i50 = i68 + arrayOfInt11[2];
          i49 = i70 - arrayOfInt11[0];
          i57 = i71 - arrayOfInt11[1];
          i47 = i72 - arrayOfInt11[2];
          i6++;
          i61++;
          break;
        }
      }
      i3 += this._width;
      i5++;
    }
    for (int i7 = 0; i7 < this._width; i7++)
    {
      int i8 = 0;
      int i9 = -paramInt * this._width;
      int i10 = -paramInt;
      int i11 = 0;
      int i12 = 0;
      int i13 = 0;
      int i14 = 0;
      int i15 = i10;
      int i16 = 0;
      int i17 = 0;
      int i18 = 0;
      int i19 = 0;
      if (i15 <= paramInt)
      {
        int i42 = i7 + Math.max(0, i9);
        int[] arrayOfInt9 = arrayOfInt[(i15 + paramInt)];
        arrayOfInt9[0] = arrayOfInt1[i42];
        arrayOfInt9[1] = arrayOfInt2[i42];
        arrayOfInt9[2] = arrayOfInt3[i42];
        int i43 = i4 - Math.abs(i15);
        int i44 = i18 + i43 * arrayOfInt1[i42];
        int i45 = i17 + i43 * arrayOfInt2[i42];
        int i46 = i16 + i43 * arrayOfInt3[i42];
        if (i15 > 0)
        {
          i11 += arrayOfInt9[0];
          i19 += arrayOfInt9[1];
          i8 += arrayOfInt9[2];
        }
        while (true)
        {
          if (i15 < j)
            i9 += this._width;
          i15++;
          i16 = i46;
          i17 = i45;
          i18 = i44;
          break;
          i14 += arrayOfInt9[0];
          i13 += arrayOfInt9[1];
          i12 += arrayOfInt9[2];
        }
      }
      int i20 = i17;
      int i21 = i18;
      int i22 = 0;
      int i23 = i16;
      int i24 = i7;
      int i25 = i8;
      int i26 = i19;
      int i27 = i11;
      int i28 = i12;
      int i29 = i13;
      int i30 = i14;
      int i31 = paramInt;
      if (i22 < this._height)
      {
        if (this.alpha)
          this.currentPixels[i24] = (0xFF000000 & this.currentPixels[i24] | arrayOfInt5[i21] << 16 | arrayOfInt5[i20] << 8 | arrayOfInt5[i23]);
        while (true)
        {
          int i32 = i21 - i30;
          int i33 = i20 - i29;
          int i34 = i23 - i28;
          int[] arrayOfInt7 = arrayOfInt[((m + (i31 - paramInt)) % m)];
          int i35 = i30 - arrayOfInt7[0];
          int i36 = i29 - arrayOfInt7[1];
          int i37 = i28 - arrayOfInt7[2];
          if (i7 == 0)
            arrayOfInt4[i22] = (Math.min(i22 + i4, j) * this._width);
          int i38 = i7 + arrayOfInt4[i22];
          arrayOfInt7[0] = arrayOfInt1[i38];
          arrayOfInt7[1] = arrayOfInt2[i38];
          arrayOfInt7[2] = arrayOfInt3[i38];
          int i39 = i27 + arrayOfInt7[0];
          int i40 = i26 + arrayOfInt7[1];
          int i41 = i25 + arrayOfInt7[2];
          i21 = i32 + i39;
          i20 = i33 + i40;
          i23 = i34 + i41;
          i31 = (i31 + 1) % m;
          int[] arrayOfInt8 = arrayOfInt[i31];
          i30 = i35 + arrayOfInt8[0];
          i29 = i36 + arrayOfInt8[1];
          i28 = i37 + arrayOfInt8[2];
          i27 = i39 - arrayOfInt8[0];
          i26 = i40 - arrayOfInt8[1];
          i25 = i41 - arrayOfInt8[2];
          i24 += this._width;
          i22++;
          break;
          this.currentPixels[i24] = (0xFF000000 | arrayOfInt5[i21] << 16 | arrayOfInt5[i20] << 8 | arrayOfInt5[i23]);
        }
      }
    }
  }

  public Bitmap returnBlurredImage(int paramInt)
  {
    Bitmap localBitmap = Bitmap.createBitmap(this._width, this._height, Bitmap.Config.RGB_565);
    Canvas localCanvas = new Canvas(localBitmap);
    localCanvas.drawBitmap(this._image, this.mRect, new Rect(0, 0, this._width, this._height), new Paint());
    localBitmap.setPixels(this.currentPixels, 0, this._width, 0, 0, this._width, this._height);
    if (paramInt != 0)
      localCanvas.drawColor(paramInt);
    return localBitmap;
  }

  public Bitmap returnBlurredImage(Bitmap paramBitmap)
  {
    Bitmap localBitmap = Bitmap.createBitmap(this._width, this._height, Bitmap.Config.RGB_565);
    Canvas localCanvas = new Canvas(localBitmap);
    Rect localRect = new Rect(0, 0, this._width, this._height);
    localCanvas.drawBitmap(this._image, this.mRect, localRect, new Paint());
    localBitmap.setPixels(this.currentPixels, 0, this._width, 0, 0, this._width, this._height);
    if (paramBitmap != null)
      localCanvas.drawBitmap(paramBitmap, null, localRect, new Paint());
    return localBitmap;
  }

  public Bitmap returnBlurredImage(Bitmap paramBitmap, int paramInt)
  {
    Bitmap localBitmap = Bitmap.createBitmap(this._width, paramInt + this._height, Bitmap.Config.ARGB_8888);
    Canvas localCanvas = new Canvas(localBitmap);
    localCanvas.drawBitmap(this._image, this.mRect, new Rect(0, paramInt, this._width, paramInt + this._height), new Paint());
    localBitmap.setPixels(this.currentPixels, 0, this._width, 0, paramInt, this._width, this._height);
    if (paramBitmap != null)
      localCanvas.drawBitmap(paramBitmap, null, new Rect(0, 0, this._width, paramInt + this._height), new Paint());
    return localBitmap;
  }

  public Bitmap returnBlurredImageInPlay(int paramInt)
  {
    Bitmap localBitmap = Bitmap.createBitmap(this._width, this._height, Bitmap.Config.ARGB_8888);
    Canvas localCanvas = new Canvas(localBitmap);
    localCanvas.drawBitmap(this._image, this.mRect, new Rect(0, 0, this._width, this._height), new Paint());
    localBitmap.setPixels(this.currentPixels, 0, this._width, 0, 0, this._width, this._height);
    if (paramInt != 0)
      localCanvas.drawColor(paramInt);
    return localBitmap;
  }

  public void saveIntoFile(String paramString)
  {
    Bitmap localBitmap = Bitmap.createBitmap(this._image.getWidth(), this._image.getHeight(), Bitmap.Config.RGB_565);
    new Canvas(localBitmap).drawBitmap(this._image, 0.0F, 0.0F, new Paint());
    localBitmap.setPixels(this.currentPixels, 0, this._width, 0, 0, this._width, this._height);
    try
    {
      FileOutputStream localFileOutputStream = new FileOutputStream(paramString);
      localBitmap.compress(Bitmap.CompressFormat.PNG, 90, localFileOutputStream);
      return;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.qtradio.manager.BlurBitmap
 * JD-Core Version:    0.6.2
 */