package master.flame.danmaku.danmaku.model;

public abstract interface IDanmakuIterator
{
  public abstract boolean hasNext();

  public abstract BaseDanmaku next();

  public abstract void remove();

  public abstract void reset();
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     master.flame.danmaku.danmaku.model.IDanmakuIterator
 * JD-Core Version:    0.6.2
 */