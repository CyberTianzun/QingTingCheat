package fm.qingting.async.future;

public abstract interface DependentCancellable extends Cancellable
{
  public abstract DependentCancellable setParent(Cancellable paramCancellable);
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.async.future.DependentCancellable
 * JD-Core Version:    0.6.2
 */