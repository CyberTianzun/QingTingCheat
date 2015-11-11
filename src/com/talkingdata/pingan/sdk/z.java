package com.talkingdata.pingan.sdk;

class z
  implements Runnable
{
  z(aj paramaj, String paramString)
  {
  }

  public void run()
  {
    try
    {
      t localt = aj.a(this.b, this.a);
      if (!this.b.g)
        this.b.a = localt;
      synchronized (this.b)
      {
        this.b.notifyAll();
        Thread.sleep(3000L);
        if (this.b.a.b())
          aj.a(this.b, this.b.a);
        return;
      }
    }
    catch (Throwable localThrowable)
    {
    }
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.talkingdata.pingan.sdk.z
 * JD-Core Version:    0.6.2
 */