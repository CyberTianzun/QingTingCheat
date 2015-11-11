package fm.qingting.async.future;

public abstract interface FutureCallback<T>
{
  public abstract void onCompleted(Exception paramException, T paramT);
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.async.future.FutureCallback
 * JD-Core Version:    0.6.2
 */