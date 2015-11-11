package com.tencent.connect.share;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Matrix;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import com.tencent.utils.AsynLoadImgBack;
import com.tencent.utils.Util;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class a
{
  public static final int a(BitmapFactory.Options paramOptions, int paramInt1, int paramInt2)
  {
    int i = b(paramOptions, paramInt1, paramInt2);
    if (i <= 8)
    {
      j = 1;
      while (j < i)
        j <<= 1;
    }
    int j = 8 * ((i + 7) / 8);
    return j;
  }

  private static Bitmap a(Bitmap paramBitmap, int paramInt)
  {
    Matrix localMatrix = new Matrix();
    int i = paramBitmap.getWidth();
    int j = paramBitmap.getHeight();
    if (i > j);
    while (true)
    {
      float f = paramInt / i;
      localMatrix.postScale(f, f);
      return Bitmap.createBitmap(paramBitmap, 0, 0, paramBitmap.getWidth(), paramBitmap.getHeight(), localMatrix, true);
      i = j;
    }
  }

  public static final Bitmap a(String paramString, int paramInt)
  {
    Bitmap localBitmap;
    if (TextUtils.isEmpty(paramString))
      localBitmap = null;
    while (true)
    {
      return localBitmap;
      BitmapFactory.Options localOptions = new BitmapFactory.Options();
      localOptions.inJustDecodeBounds = true;
      BitmapFactory.decodeFile(paramString, localOptions);
      int i = localOptions.outWidth;
      int j = localOptions.outHeight;
      if ((localOptions.mCancel) || (localOptions.outWidth == -1) || (localOptions.outHeight == -1))
        return null;
      if (i > j);
      while (true)
      {
        localOptions.inPreferredConfig = Bitmap.Config.RGB_565;
        if (i > paramInt)
          localOptions.inSampleSize = a(localOptions, -1, paramInt * paramInt);
        localOptions.inJustDecodeBounds = false;
        localBitmap = BitmapFactory.decodeFile(paramString, localOptions);
        if (localBitmap != null)
          break;
        return null;
        i = j;
      }
      int k = localOptions.outWidth;
      int m = localOptions.outHeight;
      if (k > m);
      while (k > paramInt)
      {
        return a(localBitmap, paramInt);
        k = m;
      }
    }
  }

  protected static final String a(Bitmap paramBitmap, String paramString1, String paramString2)
  {
    File localFile1 = new File(paramString1);
    if (!localFile1.exists())
      localFile1.mkdirs();
    String str = paramString1 + paramString2;
    File localFile2 = new File(str);
    if (localFile2.exists())
      localFile2.delete();
    if (paramBitmap != null);
    try
    {
      FileOutputStream localFileOutputStream = new FileOutputStream(localFile2);
      paramBitmap.compress(Bitmap.CompressFormat.JPEG, 80, localFileOutputStream);
      localFileOutputStream.flush();
      localFileOutputStream.close();
      paramBitmap.recycle();
      return str;
    }
    catch (FileNotFoundException localFileNotFoundException)
    {
      localFileNotFoundException.printStackTrace();
      return null;
    }
    catch (IOException localIOException)
    {
      while (true)
        localIOException.printStackTrace();
    }
  }

  public static final void a(Context paramContext, String paramString, final AsynLoadImgBack paramAsynLoadImgBack)
  {
    Log.d("AsynScaleCompressImage", "scaleCompressImage");
    if (TextUtils.isEmpty(paramString))
    {
      paramAsynLoadImgBack.saved(1, null);
      return;
    }
    if (!Util.hasSDCard())
    {
      paramAsynLoadImgBack.saved(2, null);
      return;
    }
    new Thread(new Runnable()
    {
      public void run()
      {
        Bitmap localBitmap = a.a(this.a, 140);
        if (localBitmap != null)
        {
          String str1 = Environment.getExternalStorageDirectory() + "/tmp/";
          String str2 = Util.encrypt(this.a);
          String str3 = "share2qq_temp" + str2 + ".jpg";
          if (!a.a(this.a, 140, 140))
            Log.d("AsynScaleCompressImage", "not out of bound,not compress!");
          for (String str4 = this.a; str4 != null; str4 = a.a(localBitmap, str1, str3))
          {
            Message localMessage2 = this.b.obtainMessage(101);
            localMessage2.obj = str4;
            this.b.sendMessage(localMessage2);
            return;
            Log.d("AsynScaleCompressImage", "out of bound,compress!");
          }
        }
        Message localMessage1 = this.b.obtainMessage(102);
        localMessage1.arg1 = 3;
        this.b.sendMessage(localMessage1);
      }
    }).start();
  }

  public static final void a(Context paramContext, ArrayList<String> paramArrayList, final AsynLoadImgBack paramAsynLoadImgBack)
  {
    Log.d("AsynScaleCompressImage", "batchScaleCompressImage");
    if (paramArrayList == null)
    {
      paramAsynLoadImgBack.saved(1, null);
      return;
    }
    if (!Util.hasSDCard())
    {
      paramAsynLoadImgBack.saved(2, null);
      return;
    }
    new Thread(new Runnable()
    {
      public void run()
      {
        int i = 0;
        if (i < this.a.size())
        {
          String str1 = (String)this.a.get(i);
          Bitmap localBitmap;
          String str2;
          String str4;
          if ((!Util.isValidUrl(str1)) && (Util.fileExists(str1)))
          {
            localBitmap = a.a(str1, 10000);
            if (localBitmap != null)
            {
              str2 = Environment.getExternalStorageDirectory() + "/tmp/";
              String str3 = Util.encrypt(str1);
              str4 = "share2qzone_temp" + str3 + ".jpg";
              if (a.a(str1, 640, 10000))
                break label158;
              Log.d("AsynScaleCompressImage", "not out of bound,not compress!");
            }
          }
          while (true)
          {
            if (str1 != null)
              this.a.set(i, str1);
            i++;
            break;
            label158: Log.d("AsynScaleCompressImage", "out of bound, compress!");
            str1 = a.a(localBitmap, str2, str4);
          }
        }
        Message localMessage = this.b.obtainMessage(101);
        Bundle localBundle = new Bundle();
        localBundle.putStringArrayList("images", this.a);
        localMessage.setData(localBundle);
        this.b.sendMessage(localMessage);
      }
    }).start();
  }

  private static int b(BitmapFactory.Options paramOptions, int paramInt1, int paramInt2)
  {
    double d1 = paramOptions.outWidth;
    double d2 = paramOptions.outHeight;
    int i;
    int j;
    if (paramInt2 == -1)
    {
      i = 1;
      if (paramInt1 != -1)
        break label60;
      j = 128;
      label31: if (j >= i)
        break label84;
    }
    label60: label84: 
    do
    {
      return i;
      i = (int)Math.ceil(Math.sqrt(d1 * d2 / paramInt2));
      break;
      j = (int)Math.min(Math.floor(d1 / paramInt1), Math.floor(d2 / paramInt1));
      break label31;
      if ((paramInt2 == -1) && (paramInt1 == -1))
        return 1;
    }
    while (paramInt1 == -1);
    return j;
  }

  private static final boolean b(String paramString, int paramInt1, int paramInt2)
  {
    if (TextUtils.isEmpty(paramString))
      return false;
    BitmapFactory.Options localOptions = new BitmapFactory.Options();
    localOptions.inJustDecodeBounds = true;
    BitmapFactory.decodeFile(paramString, localOptions);
    int i = localOptions.outWidth;
    int j = localOptions.outHeight;
    if ((localOptions.mCancel) || (localOptions.outWidth == -1) || (localOptions.outHeight == -1))
      return false;
    int k;
    if (i > j)
    {
      k = i;
      if (i >= j)
        break label147;
    }
    while (true)
    {
      Log.d("AsynScaleCompressImage", "longSide=" + k + "shortSide=" + i);
      localOptions.inPreferredConfig = Bitmap.Config.RGB_565;
      if ((k <= paramInt2) && (i <= paramInt1))
        break label154;
      return true;
      k = j;
      break;
      label147: i = j;
    }
    label154: return false;
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.tencent.connect.share.a
 * JD-Core Version:    0.6.2
 */