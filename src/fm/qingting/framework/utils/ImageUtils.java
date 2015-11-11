package fm.qingting.framework.utils;

import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader.TileMode;
import android.graphics.drawable.Drawable;
import java.io.ByteArrayOutputStream;

public class ImageUtils
{
  public static byte[] bitmap2Bytes(Bitmap paramBitmap)
  {
    ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream();
    paramBitmap.compress(Bitmap.CompressFormat.PNG, 100, localByteArrayOutputStream);
    return localByteArrayOutputStream.toByteArray();
  }

  public static Bitmap bytes2Bimap(byte[] paramArrayOfByte)
  {
    if (paramArrayOfByte.length != 0)
      return BitmapFactory.decodeByteArray(paramArrayOfByte, 0, paramArrayOfByte.length);
    return null;
  }

  public static Bitmap createReflectionImageWithOrigin(Bitmap paramBitmap, float paramFloat, int paramInt1, int paramInt2, int paramInt3)
  {
    return createReflectionImageWithOrigin(paramBitmap, paramFloat, paramInt1, paramInt2, paramInt3, Bitmap.Config.ARGB_8888);
  }

  public static Bitmap createReflectionImageWithOrigin(Bitmap paramBitmap, float paramFloat, int paramInt1, int paramInt2, int paramInt3, Bitmap.Config paramConfig)
  {
    int i = paramBitmap.getWidth();
    int j = paramBitmap.getHeight();
    Matrix localMatrix = new Matrix();
    localMatrix.preScale(1.0F, -1.0F);
    int k = (int)(paramFloat * j);
    Bitmap localBitmap1 = Bitmap.createBitmap(paramBitmap, 0, j - k, i, k, localMatrix, false);
    Bitmap localBitmap2 = Bitmap.createBitmap(i, paramInt3 + (j + k), paramConfig);
    Canvas localCanvas = new Canvas(localBitmap2);
    localCanvas.drawBitmap(paramBitmap, 0.0F, 0.0F, null);
    Paint localPaint1 = new Paint();
    localPaint1.setColor(0);
    localCanvas.drawRect(0.0F, j, i, j + paramInt3, localPaint1);
    localCanvas.drawBitmap(localBitmap1, 0.0F, j + paramInt3, null);
    Paint localPaint2 = new Paint();
    if (paramInt1 < 0)
      paramInt1 = 0;
    if (paramInt1 > 255)
      paramInt1 = 255;
    if (paramInt2 < 0)
      paramInt2 = 0;
    if (paramInt2 > 255)
      paramInt2 = 255;
    localPaint2.setShader(new LinearGradient(0.0F, paramInt3 + paramBitmap.getHeight(), 0.0F, localBitmap2.getHeight(), 16777215 + (paramInt1 << 24), 16777215 + (paramInt2 << 24), Shader.TileMode.CLAMP));
    localPaint2.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
    localCanvas.drawRect(0.0F, j, i, paramInt3 + localBitmap2.getHeight(), localPaint2);
    return localBitmap2;
  }

  public static Bitmap drawableToBitmap(Drawable paramDrawable)
  {
    int i = paramDrawable.getIntrinsicWidth();
    int j = paramDrawable.getIntrinsicHeight();
    if (paramDrawable.getOpacity() != -1);
    for (Bitmap.Config localConfig = Bitmap.Config.ARGB_8888; ; localConfig = Bitmap.Config.RGB_565)
    {
      Bitmap localBitmap = Bitmap.createBitmap(i, j, localConfig);
      Canvas localCanvas = new Canvas(localBitmap);
      paramDrawable.setBounds(0, 0, i, j);
      paramDrawable.draw(localCanvas);
      return localBitmap;
    }
  }

  public static Bitmap getRoundedCornerBitmap(Bitmap paramBitmap, float paramFloat)
  {
    Bitmap localBitmap = Bitmap.createBitmap(paramBitmap.getWidth(), paramBitmap.getHeight(), Bitmap.Config.ARGB_8888);
    Canvas localCanvas = new Canvas(localBitmap);
    Paint localPaint = new Paint();
    Rect localRect = new Rect(0, 0, paramBitmap.getWidth(), paramBitmap.getHeight());
    RectF localRectF = new RectF(localRect);
    localPaint.setAntiAlias(true);
    localCanvas.drawARGB(0, 0, 0, 0);
    localPaint.setColor(-12434878);
    localCanvas.drawRoundRect(localRectF, paramFloat, paramFloat, localPaint);
    localPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
    localCanvas.drawBitmap(paramBitmap, localRect, localRect, localPaint);
    return localBitmap;
  }

  public static byte[] ninePatchChunkBuider(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6)
  {
    int i = 3;
    int j = 3;
    if (paramInt3 == 0)
      i--;
    if (paramInt4 == 0)
      i--;
    if (paramInt3 + paramInt4 == paramInt1)
      i = 0;
    if (paramInt5 == 0)
      j--;
    if (paramInt6 == 0)
      j--;
    if (paramInt5 + paramInt6 == paramInt2)
      j = 0;
    int k = i * j;
    byte[] arrayOfByte = new byte[48 + k * 4];
    arrayOfByte[0] = 1;
    arrayOfByte[1] = 2;
    arrayOfByte[2] = 2;
    arrayOfByte[3] = ((byte)k);
    putInt(arrayOfByte, 12, paramInt3);
    putInt(arrayOfByte, 16, paramInt4);
    putInt(arrayOfByte, 20, paramInt5);
    putInt(arrayOfByte, 24, paramInt6);
    putInt(arrayOfByte, 28, 0);
    putInt(arrayOfByte, 32, paramInt3);
    int m = 32 + 4;
    putInt(arrayOfByte, m, paramInt1 - paramInt4);
    int n = m + 4;
    putInt(arrayOfByte, n, paramInt5);
    int i1 = n + 4;
    putInt(arrayOfByte, i1, paramInt2 - paramInt6);
    int i2 = i1 + 4;
    for (int i3 = 0; ; i3++)
    {
      if (i3 >= k)
        return arrayOfByte;
      putInt(arrayOfByte, i2, 1);
      i2 += 4;
    }
  }

  public static void putInt(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
  {
    paramArrayOfByte[paramInt1] = ((byte)(paramInt2 & 0xFF));
    int i = paramInt2 >> 8;
    paramArrayOfByte[(paramInt1 + 1)] = ((byte)(i & 0xFF));
    int j = i >> 8;
    paramArrayOfByte[(paramInt1 + 2)] = ((byte)(j & 0xFF));
    int k = j >> 8;
    paramArrayOfByte[(paramInt1 + 3)] = ((byte)(k & 0xFF));
  }

  public static Bitmap zoomBitmap(Bitmap paramBitmap, int paramInt1, int paramInt2)
  {
    int i = paramBitmap.getWidth();
    int j = paramBitmap.getHeight();
    Matrix localMatrix = new Matrix();
    localMatrix.postScale(paramInt1 / i, paramInt2 / j);
    return Bitmap.createBitmap(paramBitmap, 0, 0, i, j, localMatrix, true);
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.framework.utils.ImageUtils
 * JD-Core Version:    0.6.2
 */