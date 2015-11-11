package master.flame.danmaku.danmaku.model;

public abstract class AbsDisplayer<T>
  implements IDisplayer
{
  public abstract T getExtraData();

  public boolean isHardwareAccelerated()
  {
    return false;
  }

  public abstract void setExtraData(T paramT);
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     master.flame.danmaku.danmaku.model.AbsDisplayer
 * JD-Core Version:    0.6.2
 */