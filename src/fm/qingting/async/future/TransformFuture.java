package fm.qingting.async.future;

public abstract class TransformFuture<T, F> extends SimpleFuture<T>
  implements FutureCallback<F>
{
  protected void error(Exception paramException)
  {
    setComplete(paramException);
  }

  public TransformFuture<T, F> from(Future<F> paramFuture)
  {
    setParent(paramFuture);
    paramFuture.setCallback(this);
    return this;
  }

  public void onCompleted(Exception paramException, F paramF)
  {
    if (isCancelled())
      return;
    if (paramException != null)
    {
      error(paramException);
      return;
    }
    try
    {
      transform(paramF);
      return;
    }
    catch (Exception localException)
    {
      error(localException);
    }
  }

  protected abstract void transform(F paramF)
    throws Exception;
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.async.future.TransformFuture
 * JD-Core Version:    0.6.2
 */