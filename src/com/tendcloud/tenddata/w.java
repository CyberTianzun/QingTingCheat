package com.tendcloud.tenddata;

class w
  implements Runnable
{
  w(ag paramag, String paramString)
  {
  }

  public void run()
  {
    try
    {
      u localu = ag.a(this.b, this.a);
      if (!this.b.l)
        this.b.a = localu;
      synchronized (this.b)
      {
        this.b.notifyAll();
        Thread.sleep(3000L);
        if (this.b.a.b())
          ag.a(this.b, this.b.a);
        return;
      }
    }
    catch (Throwable localThrowable)
    {
    }
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.tendcloud.tenddata.w
 * JD-Core Version:    0.6.2
 */