package master.flame.danmaku.danmaku.model.objectpool;

public abstract interface Pool<T extends Poolable<T>>
{
  public abstract T acquire();

  public abstract void release(T paramT);
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     master.flame.danmaku.danmaku.model.objectpool.Pool
 * JD-Core Version:    0.6.2
 */