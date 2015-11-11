package fm.qingting.framework.utils;

import android.graphics.Bitmap;

class BitmapCache
{
  public Bitmap cache;
  public int id;
  public int retain = 0;

  public BitmapCache(Bitmap paramBitmap, int paramInt)
  {
    this.cache = paramBitmap;
    this.id = paramInt;
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.framework.utils.BitmapCache
 * JD-Core Version:    0.6.2
 */