package com.tencent.utils;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class AsynLoadImg
{
  private static String d;
  Activity a;
  private String b;
  private AsynLoadImgBack c;
  private long e;
  private Handler f;
  private Runnable g = new Runnable()
  {
    public void run()
    {
      Log.v("AsynLoadImg", "saveFileRunnable:");
      String str1 = Util.encrypt(AsynLoadImg.b(AsynLoadImg.this));
      String str2 = "share_qq_" + str1 + ".jpg";
      String str3 = AsynLoadImg.a() + str2;
      File localFile = new File(str3);
      Message localMessage = AsynLoadImg.c(AsynLoadImg.this).obtainMessage();
      if (localFile.exists())
      {
        localMessage.arg1 = 0;
        localMessage.obj = str3;
        Log.v("AsynLoadImg", "file exists: time:" + (System.currentTimeMillis() - AsynLoadImg.d(AsynLoadImg.this)));
        AsynLoadImg.c(AsynLoadImg.this).sendMessage(localMessage);
        return;
      }
      Bitmap localBitmap = AsynLoadImg.getbitmap(AsynLoadImg.b(AsynLoadImg.this));
      boolean bool;
      if (localBitmap != null)
      {
        bool = AsynLoadImg.this.saveFile(localBitmap, str2);
        label188: if (!bool)
          break label258;
        localMessage.arg1 = 0;
        localMessage.obj = str3;
      }
      while (true)
      {
        Log.v("AsynLoadImg", "file not exists: download time:" + (System.currentTimeMillis() - AsynLoadImg.d(AsynLoadImg.this)));
        break;
        Log.v("AsynLoadImg", "saveFileRunnable:get bmp fail---");
        bool = false;
        break label188;
        label258: localMessage.arg1 = 1;
      }
    }
  };

  public AsynLoadImg(Activity paramActivity)
  {
    this.a = paramActivity;
    this.f = new Handler(paramActivity.getMainLooper())
    {
      public void handleMessage(Message paramAnonymousMessage)
      {
        Log.v("AsynLoadImg", "handleMessage:" + paramAnonymousMessage.arg1);
        if (paramAnonymousMessage.arg1 == 0)
        {
          AsynLoadImg.a(AsynLoadImg.this).saved(paramAnonymousMessage.arg1, (String)paramAnonymousMessage.obj);
          return;
        }
        AsynLoadImg.a(AsynLoadImg.this).saved(paramAnonymousMessage.arg1, null);
      }
    };
  }

  public static Bitmap getbitmap(String paramString)
  {
    Log.v("AsynLoadImg", "getbitmap:" + paramString);
    try
    {
      HttpURLConnection localHttpURLConnection = (HttpURLConnection)new URL(paramString).openConnection();
      localHttpURLConnection.setDoInput(true);
      localHttpURLConnection.connect();
      InputStream localInputStream = localHttpURLConnection.getInputStream();
      Bitmap localBitmap = BitmapFactory.decodeStream(localInputStream);
      localInputStream.close();
      Log.v("AsynLoadImg", "image download finished." + paramString);
      return localBitmap;
    }
    catch (IOException localIOException)
    {
      localIOException.printStackTrace();
      Log.v("AsynLoadImg", "getbitmap bmp fail---");
    }
    return null;
  }

  public void save(String paramString, AsynLoadImgBack paramAsynLoadImgBack)
  {
    Log.v("AsynLoadImg", "--save---");
    if ((paramString == null) || (paramString.equals("")))
    {
      paramAsynLoadImgBack.saved(1, null);
      return;
    }
    if (!Util.hasSDCard())
    {
      paramAsynLoadImgBack.saved(2, null);
      return;
    }
    d = Environment.getExternalStorageDirectory() + "/tmp/";
    this.e = System.currentTimeMillis();
    this.b = paramString;
    this.c = paramAsynLoadImgBack;
    new Thread(this.g).start();
  }

  public boolean saveFile(Bitmap paramBitmap, String paramString)
  {
    String str1 = d;
    try
    {
      File localFile = new File(str1);
      if (!localFile.exists())
        localFile.mkdir();
      String str2 = str1 + paramString;
      Log.v("AsynLoadImg", "saveFile:" + paramString);
      BufferedOutputStream localBufferedOutputStream = new BufferedOutputStream(new FileOutputStream(new File(str2)));
      paramBitmap.compress(Bitmap.CompressFormat.JPEG, 80, localBufferedOutputStream);
      localBufferedOutputStream.flush();
      localBufferedOutputStream.close();
      return true;
    }
    catch (IOException localIOException)
    {
      localIOException.printStackTrace();
      Log.v("AsynLoadImg", "saveFile bmp fail---");
    }
    return false;
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.tencent.utils.AsynLoadImg
 * JD-Core Version:    0.6.2
 */