package com.tencent.weibo.sdk.android.api.util;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class BackGroudSeletor
{
  static int[] EMPTY_STATE_SET = new int[0];
  static int[] ENABLED_STATE_SET;
  static int[] PRESSED_ENABLED_STATE_SET = { 16842910, 16842919 };
  private static String pix = "";

  static
  {
    ENABLED_STATE_SET = new int[] { 16842910 };
  }

  public static StateListDrawable createBgByImageIds(String[] paramArrayOfString, Context paramContext)
  {
    StateListDrawable localStateListDrawable = new StateListDrawable();
    Drawable localDrawable1 = getdrawble(paramArrayOfString[0], paramContext);
    Drawable localDrawable2 = getdrawble(paramArrayOfString[1], paramContext);
    localStateListDrawable.addState(PRESSED_ENABLED_STATE_SET, localDrawable2);
    localStateListDrawable.addState(ENABLED_STATE_SET, localDrawable1);
    localStateListDrawable.addState(EMPTY_STATE_SET, localDrawable1);
    return localStateListDrawable;
  }

  public static String getPix()
  {
    return pix;
  }

  public static Drawable getdrawble(String paramString, Context paramContext)
  {
    Bitmap localBitmap = null;
    try
    {
      File localFile = new File(paramString + pix + ".png");
      String str = paramString + pix + ".png";
      boolean bool = localFile.isFile();
      localBitmap = null;
      if (!bool)
        str = paramString + "480x800" + ".png";
      localBitmap = BitmapFactory.decodeStream(paramContext.getAssets().open(str));
      BitmapDrawable localBitmapDrawable = new BitmapDrawable(localBitmap);
      return localBitmapDrawable;
    }
    catch (IOException localIOException)
    {
      if (localBitmap != null)
        localBitmap.recycle();
      localIOException.printStackTrace();
    }
    return null;
  }

  public static void setPix(String paramString)
  {
    pix = paramString;
  }

  public static InputStream zipPic(InputStream paramInputStream)
  {
    return null;
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.tencent.weibo.sdk.android.api.util.BackGroudSeletor
 * JD-Core Version:    0.6.2
 */