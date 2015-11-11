package master.flame.danmaku.danmaku.model.objectpool;

import java.io.PrintStream;

class FinitePool<T extends Poolable<T>>
  implements Pool<T>
{
  private final boolean mInfinite;
  private final int mLimit;
  private final PoolableManager<T> mManager;
  private int mPoolCount;
  private T mRoot;

  FinitePool(PoolableManager<T> paramPoolableManager)
  {
    this.mManager = paramPoolableManager;
    this.mLimit = 0;
    this.mInfinite = true;
  }

  FinitePool(PoolableManager<T> paramPoolableManager, int paramInt)
  {
    if (paramInt <= 0)
      throw new IllegalArgumentException("The pool limit must be > 0");
    this.mManager = paramPoolableManager;
    this.mLimit = paramInt;
    this.mInfinite = false;
  }

  public T acquire()
  {
    Poolable localPoolable2;
    if (this.mRoot != null)
    {
      localPoolable2 = this.mRoot;
      this.mRoot = ((Poolable)localPoolable2.getNextPoolable());
      this.mPoolCount = (-1 + this.mPoolCount);
    }
    for (Poolable localPoolable1 = localPoolable2; ; localPoolable1 = this.mManager.newInstance())
    {
      if (localPoolable1 != null)
      {
        localPoolable1.setNextPoolable(null);
        localPoolable1.setPooled(false);
        this.mManager.onAcquired(localPoolable1);
      }
      return localPoolable1;
    }
  }

  public void release(T paramT)
  {
    if (!paramT.isPooled())
    {
      if ((this.mInfinite) || (this.mPoolCount < this.mLimit))
      {
        this.mPoolCount = (1 + this.mPoolCount);
        paramT.setNextPoolable(this.mRoot);
        paramT.setPooled(true);
        this.mRoot = paramT;
      }
      this.mManager.onReleased(paramT);
      return;
    }
    System.out.print("[FinitePool] Element is already in pool: " + paramT);
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     master.flame.danmaku.danmaku.model.objectpool.FinitePool
 * JD-Core Version:    0.6.2
 */