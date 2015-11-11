package fm.qingting.async.future;

public abstract interface Cancellable
{
  public abstract boolean cancel();

  public abstract boolean isCancelled();

  public abstract boolean isDone();
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.async.future.Cancellable
 * JD-Core Version:    0.6.2
 */