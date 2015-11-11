package master.flame.danmaku.danmaku.model.objectpool;

public abstract interface Poolable<T>
{
  public abstract T getNextPoolable();

  public abstract boolean isPooled();

  public abstract void setNextPoolable(T paramT);

  public abstract void setPooled(boolean paramBoolean);
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     master.flame.danmaku.danmaku.model.objectpool.Poolable
 * JD-Core Version:    0.6.2
 */