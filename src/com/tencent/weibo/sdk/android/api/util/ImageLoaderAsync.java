package com.tencent.weibo.sdk.android.api.util;

import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;

public class ImageLoaderAsync
{
  public Drawable loadImage(final String paramString, final callBackImage paramcallBackImage)
  {
    new Thread()
    {
      public void run()
      {
        Drawable localDrawable = Util.loadImageFromUrl(paramString);
        this.val$handler.sendMessage(this.val$handler.obtainMessage(0, localDrawable));
      }
    }
    .start();
    return null;
  }

  public static abstract interface callBackImage
  {
    public abstract void callback(Drawable paramDrawable, String paramString);
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.tencent.weibo.sdk.android.api.util.ImageLoaderAsync
 * JD-Core Version:    0.6.2
 */