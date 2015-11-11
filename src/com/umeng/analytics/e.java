package com.umeng.analytics;

public abstract class e
  implements Runnable
{
  public abstract void a();

  public void run()
  {
    try
    {
      a();
      return;
    }
    catch (Throwable localThrowable)
    {
      while (localThrowable == null);
      localThrowable.printStackTrace();
    }
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.umeng.analytics.e
 * JD-Core Version:    0.6.2
 */