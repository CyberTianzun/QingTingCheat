package com.taobao.munion.base.volley.a;

import com.taobao.munion.base.volley.l;
import com.taobao.munion.base.volley.n.a;
import com.taobao.munion.base.volley.n.b;
import com.taobao.munion.base.volley.s;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class k<T>
  implements n.a, n.b<T>, Future<T>
{
  private l<?> a;
  private boolean b = false;
  private T c;
  private s d;

  public static <E> k<E> a()
  {
    return new k();
  }

  private T a(Long paramLong)
    throws InterruptedException, ExecutionException, TimeoutException
  {
    try
    {
      if (this.d != null)
        throw new ExecutionException(this.d);
    }
    finally
    {
    }
    if (this.b);
    for (Object localObject2 = this.c; ; localObject2 = this.c)
    {
      return localObject2;
      if (paramLong == null)
        wait(0L);
      while (this.d != null)
      {
        throw new ExecutionException(this.d);
        if (paramLong.longValue() > 0L)
          wait(paramLong.longValue());
      }
      if (!this.b)
        throw new TimeoutException();
    }
  }

  public void a(l<?> paraml)
  {
    this.a = paraml;
  }

  public void a(s params)
  {
    try
    {
      this.d = params;
      notifyAll();
      return;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }

  public void a(T paramT)
  {
    try
    {
      this.b = true;
      this.c = paramT;
      notifyAll();
      return;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }

  public boolean cancel(boolean paramBoolean)
  {
    try
    {
      l locall = this.a;
      boolean bool1 = false;
      if (locall == null);
      while (true)
      {
        return bool1;
        boolean bool2 = isDone();
        bool1 = false;
        if (!bool2)
        {
          this.a.i();
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
      Object localObject = a(null);
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
    return a(Long.valueOf(TimeUnit.MILLISECONDS.convert(paramLong, paramTimeUnit)));
  }

  public boolean isCancelled()
  {
    if (this.a == null)
      return false;
    return this.a.j();
  }

  public boolean isDone()
  {
    try
    {
      if ((!this.b) && (this.d == null))
      {
        boolean bool2 = isCancelled();
        if (!bool2);
      }
      else
      {
        bool1 = true;
        return bool1;
      }
      boolean bool1 = false;
    }
    finally
    {
    }
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.taobao.munion.base.volley.a.k
 * JD-Core Version:    0.6.2
 */