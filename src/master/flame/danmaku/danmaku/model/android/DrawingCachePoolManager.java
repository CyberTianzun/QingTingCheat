package master.flame.danmaku.danmaku.model.android;

import master.flame.danmaku.danmaku.model.objectpool.PoolableManager;

public class DrawingCachePoolManager
  implements PoolableManager<DrawingCache>
{
  public DrawingCache newInstance()
  {
    return null;
  }

  public void onAcquired(DrawingCache paramDrawingCache)
  {
  }

  public void onReleased(DrawingCache paramDrawingCache)
  {
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     master.flame.danmaku.danmaku.model.android.DrawingCachePoolManager
 * JD-Core Version:    0.6.2
 */