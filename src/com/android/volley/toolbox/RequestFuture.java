package com.android.volley.toolbox;

import com.android.volley.Request;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class RequestFuture<T>
  implements Future<T>, Response.Listener<T>, Response.ErrorListener
{
  private VolleyError mException;
  private Request<?> mRequest;
  private T mResult;
  private boolean mResultReceived = false;

  private T doGet(Long paramLong)
    throws InterruptedException, ExecutionException, TimeoutException
  {
    try
    {
      if (this.mException != null)
        throw new ExecutionException(this.mException);
    }
    finally
    {
    }
    if (this.mResultReceived);
    for (Object localObject2 = this.mResult; ; localObject2 = this.mResult)
    {
      return localObject2;
      if (paramLong == null)
        wait(0L);
      while (this.mException != null)
      {
        throw new ExecutionException(this.mException);
        if (paramLong.longValue() > 0L)
          wait(paramLong.longValue());
      }
      if (!this.mResultReceived)
        throw new TimeoutException();
    }
  }

  public static <E> RequestFuture<E> newFuture()
  {
    return new RequestFuture();
  }

  public boolean cancel(boolean paramBoolean)
  {
    try
    {
      Request localRequest = this.mRequest;
      boolean bool1 = false;
      if (localRequest == null);
      while (true)
      {
        return bool1;
        boolean bool2 = isDone();
        bool1 = false;
        if (!bool2)
        {
          this.mRequest.cancel();
          bool1 = true;
        }
      }
    }
    finally
    {
    }
  }

  public T get()
    throws InterruptedException, ExecutionException
  {
    try
    {
      Object localObject = doGet(null);
      return localObject;
    }
    catch (TimeoutException localTimeoutException)
    {
      throw new AssertionError(localTimeoutException);
    }
  }

  public T get(long paramLong, TimeUnit paramTimeUnit)
    throws InterruptedException, ExecutionException, TimeoutException
  {
    return doGet(Long.valueOf(TimeUnit.MILLISECONDS.convert(paramLong, paramTimeUnit)));
  }

  public boolean isCancelled()
  {
    if (this.mRequest == null)
      return false;
    return this.mRequest.isCanceled();
  }

  public boolean isDone()
  {
    try
    {
      if ((!this.mResultReceived) && (this.mException == null))
      {
        boolean bool2 = isCancelled();
        if (!bool2)
        {
          bool1 = false;
          return bool1;
        }
      }
      boolean bool1 = true;
    }
    finally
    {
    }
  }

  public void onErrorResponse(VolleyError paramVolleyError)
  {
    try
    {
      this.mException = paramVolleyError;
      notifyAll();
      return;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }

  public void onResponse(T paramT)
  {
    try
    {
      this.mResultReceived = true;
      this.mResult = paramT;
      notifyAll();
      return;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }

  public void setRequest(Request<?> paramRequest)
  {
    this.mRequest = paramRequest;
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.android.volley.toolbox.RequestFuture
 * JD-Core Version:    0.6.2
 */