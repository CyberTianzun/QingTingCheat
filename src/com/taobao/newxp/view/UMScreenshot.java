package com.taobao.newxp.view;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

public class UMScreenshot extends ImageView
{
  public UMScreenshot(Context paramContext)
  {
    super(paramContext);
  }

  public void setScreenshotDrawable(Drawable paramDrawable)
  {
    setScaleType(ImageView.ScaleType.FIT_XY);
    setImageDrawable(paramDrawable);
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.taobao.newxp.view.UMScreenshot
 * JD-Core Version:    0.6.2
 */