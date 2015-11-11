package master.flame.danmaku.danmaku.model.objectpool;

public abstract interface PoolableManager<T extends Poolable<T>>
{
  public abstract T newInstance();

  public abstract void onAcquired(T paramT);

  public abstract void onReleased(T paramT);
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     master.flame.danmaku.danmaku.model.objectpool.PoolableManager
 * JD-Core Version:    0.6.2
 */