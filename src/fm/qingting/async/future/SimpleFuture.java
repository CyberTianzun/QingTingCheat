package fm.qingting.async.future;

import fm.qingting.async.AsyncServer.AsyncSemaphore;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class SimpleFuture<T> extends SimpleCancellable
  implements DependentFuture<T>
{
  FutureCallback<T> callback;
  Exception exception;
  T result;
  AsyncServer.AsyncSemaphore waiter;

  private T getResult()
    throws ExecutionException
  {
    if (this.exception != null)
      throw new ExecutionException(this.exception);
    return this.result;
  }

  private void handleCallbackUnlocked(FutureCallback<T> paramFutureCallback)
  {
    if (paramFutureCallback != null)
      paramFutureCallback.onCompleted(this.exception, this.result);
  }

  private FutureCallback<T> handleCompleteLocked()
  {
    FutureCallback localFutureCallback = this.callback;
    this.callback = null;
    return localFutureCallback;
  }

  public boolean cancel()
  {
    if (!super.cancel())
      return false;
    try
    {
      this.exception = new CancellationException();
      releaseWaiterLocked();
      return true;
    }
    finally
    {
    }
  }

  public boolean cancel(boolean paramBoolean)
  {
    return cancel();
  }

  AsyncServer.AsyncSemaphore ensureWaiterLocked()
  {
    if (this.waiter == null)
      this.waiter = new AsyncServer.AsyncSemaphore();
    return this.waiter;
  }

  public T get()
    throws InterruptedException, ExecutionException
  {
    try
    {
      if ((isCancelled()) || (isDone()))
      {
        Object localObject2 = getResult();
        return localObject2;
      }
      AsyncServer.AsyncSemaphore localAsyncSemaphore = ensureWaiterLocked();
      localAsyncSemaphore.acquire();
      return getResult();
    }
    finally
    {
    }
  }

  public T get(long paramLong, TimeUnit paramTimeUnit)
    throws InterruptedException, ExecutionException, TimeoutException
  {
    try
    {
      if ((isCancelled()) || (isDone()))
      {
        Object localObject2 = getResult();
        return localObject2;
      }
      AsyncServer.AsyncSemaphore localAsyncSemaphore = ensureWaiterLocked();
      if (!localAsyncSemaphore.tryAcquire(paramLong, paramTimeUnit))
        throw new TimeoutException();
    }
    finally
    {
    }
    return getResult();
  }

  public FutureCallback<T> getCompletionCallback()
  {
    return new FutureCallback()
    {
      public void onCompleted(Exception paramAnonymousException, T paramAnonymousT)
      {
        SimpleFuture.this.setComplete(paramAnonymousException, paramAnonymousT);
      }
    };
  }

  void releaseWaiterLocked()
  {
    if (this.waiter != null)
    {
      this.waiter.release();
      this.waiter = null;
    }
  }

  public SimpleFuture<T> reset()
  {
    super.reset();
    this.result = null;
    this.exception = null;
    this.waiter = null;
    this.callback = null;
    return this;
  }

  public SimpleFuture<T> setCallback(FutureCallback<T> paramFutureCallback)
  {
    try
    {
      this.callback = paramFutureCallback;
      if (isDone());
      for (FutureCallback localFutureCallback = handleCompleteLocked(); ; localFutureCallback = null)
      {
        handleCallbackUnlocked(localFutureCallback);
        return this;
      }
    }
    finally
    {
    }
  }

  public boolean setComplete()
  {
    return setComplete((Object)null);
  }

  public boolean setComplete(Exception paramException)
  {
    try
    {
      if (!super.setComplete())
        return false;
      this.exception = paramException;
      releaseWaiterLocked();
      FutureCallback localFutureCallback = handleCompleteLocked();
      handleCallbackUnlocked(localFutureCallback);
      return true;
    }
    finally
    {
    }
  }

  public boolean setComplete(Exception paramException, T paramT)
  {
    if (paramException != null)
      return setComplete(paramException);
    return setComplete(paramT);
  }

  public boolean setComplete(T paramT)
  {
    try
    {
      if (!super.setComplete())
        return false;
      this.result = paramT;
      releaseWaiterLocked();
      FutureCallback localFutureCallback = handleCompleteLocked();
      handleCallbackUnlocked(localFutureCallback);
      return true;
    }
    finally
    {
    }
  }

  public SimpleFuture<T> setParent(Cancellable paramCancellable)
  {
    super.setParent(paramCancellable);
    return this;
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.async.future.SimpleFuture
 * JD-Core Version:    0.6.2
 */