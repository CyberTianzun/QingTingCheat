package com.talkingdata.pingan.sdk;

import android.app.Activity;
import android.os.Handler;
import android.os.Message;

final class y
  implements Runnable
{
  y(int paramInt, String paramString, Activity paramActivity)
  {
  }

  public void run()
  {
    try
    {
      String[] arrayOfString = new String[3];
      arrayOfString[0] = "onPage:";
      arrayOfString[1] = String.valueOf(this.a);
      arrayOfString[2] = this.b;
      q.a(arrayOfString);
      if (!PAAgent.p())
        PAAgent.init(this.c);
      if (PAAgent.q() == null)
        return;
      Message localMessage = Message.obtain(d.a(), this.a, this.b);
      d.a().sendMessage(localMessage);
      return;
    }
    catch (Throwable localThrowable)
    {
    }
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.talkingdata.pingan.sdk.y
 * JD-Core Version:    0.6.2
 */