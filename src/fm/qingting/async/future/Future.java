package fm.qingting.async.future;

public abstract interface Future<T> extends Cancellable, java.util.concurrent.Future<T>
{
  public abstract Future<T> setCallback(FutureCallback<T> paramFutureCallback);
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.async.future.Future
 * JD-Core Version:    0.6.2
 */