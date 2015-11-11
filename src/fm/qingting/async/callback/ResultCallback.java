package fm.qingting.async.callback;

public abstract interface ResultCallback<S, T>
{
  public abstract void onCompleted(Exception paramException, S paramS, T paramT);
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.async.callback.ResultCallback
 * JD-Core Version:    0.6.2
 */