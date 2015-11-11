package fm.qingting.utils;

import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import java.io.ByteArrayOutputStream;

public class ImageUtil
{
  private static int calculateInSampleSize(BitmapFactory.Options paramOptions, int paramInt1, int paramInt2)
  {
    int i = paramOptions.outHeight;
    int j = paramOptions.outWidth;
    int k = 1;
    if ((i > paramInt2) || (j > paramInt1))
    {
      if (j > i)
        k = Math.round(i / paramInt2);
    }
    else
      return k;
    return Math.round(j / paramInt1);
  }

  public static byte[] compressImage(Bitmap paramBitmap)
  {
    ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream();
    paramBitmap.compress(Bitmap.CompressFormat.JPEG, 80, localByteArrayOutputStream);
    int i = 100;
    while ((localByteArrayOutputStream.toByteArray().length / 1024 > 100) && (i > 0))
    {
      i -= 10;
      localByteArrayOutputStream.reset();
      paramBitmap.compress(Bitmap.CompressFormat.JPEG, i, localByteArrayOutputStream);
    }
    return localByteArrayOutputStream.toByteArray();
  }

  public static byte[] getCompressImage(String paramString)
  {
    try
    {
      BitmapFactory.Options localOptions = new BitmapFactory.Options();
      localOptions.inJustDecodeBounds = true;
      BitmapFactory.decodeFile(paramString, localOptions);
      int i = localOptions.outHeight;
      int j = localOptions.outWidth;
      localOptions.inSampleSize = calculateInSampleSize(localOptions, 480, i * 480 / j);
      localOptions.inJustDecodeBounds = false;
      byte[] arrayOfByte = compressImage(BitmapFactory.decodeFile(paramString, localOptions));
      return arrayOfByte;
    }
    catch (Exception localException)
    {
    }
    return null;
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.utils.ImageUtil
 * JD-Core Version:    0.6.2
 */