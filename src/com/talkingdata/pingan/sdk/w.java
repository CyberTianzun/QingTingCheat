package com.talkingdata.pingan.sdk;

final class w
  implements Runnable
{
  w(Throwable paramThrowable)
  {
  }

  public void run()
  {
    try
    {
      if (!PAAgent.p())
        return;
      if (PAAgent.q() != null)
      {
        PAAgent.a(this.a, true);
        return;
      }
    }
    catch (Throwable localThrowable)
    {
    }
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.talkingdata.pingan.sdk.w
 * JD-Core Version:    0.6.2
 */