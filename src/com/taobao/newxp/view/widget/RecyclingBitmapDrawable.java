package com.taobao.newxp.view.widget;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;

public class RecyclingBitmapDrawable extends BitmapDrawable
{
  static final String a = "CountingBitmapDrawable";
  private int b = 0;
  private int c = 0;
  private boolean d;

  public RecyclingBitmapDrawable(Resources paramResources, Bitmap paramBitmap)
  {
    super(paramResources, paramBitmap);
  }

  private void a()
  {
    try
    {
      if ((this.b <= 0) && (this.c <= 0) && (this.d) && (b()))
        getBitmap().recycle();
      return;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }

  private boolean b()
  {
    try
    {
      Bitmap localBitmap = getBitmap();
      if (localBitmap != null)
      {
        boolean bool1 = localBitmap.isRecycled();
        if (bool1);
      }
      for (boolean bool2 = true; ; bool2 = false)
        return bool2;
    }
    finally
    {
    }
  }

  public void setIsCached(boolean paramBoolean)
  {
    if (paramBoolean);
    try
    {
      for (this.b = (1 + this.b); ; this.b = (-1 + this.b))
      {
        a();
        return;
      }
    }
    finally
    {
    }
  }

  public void setIsDisplayed(boolean paramBoolean)
  {
    if (paramBoolean);
    try
    {
      this.c = (1 + this.c);
      this.d = true;
      while (true)
      {
        a();
        return;
        this.c = (-1 + this.c);
      }
    }
    finally
    {
    }
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.taobao.newxp.view.widget.RecyclingBitmapDrawable
 * JD-Core Version:    0.6.2
 */