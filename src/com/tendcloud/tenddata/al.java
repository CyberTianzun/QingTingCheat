package com.tendcloud.tenddata;

import android.content.Context;
import android.os.Handler;
import android.os.Message;

class al
  implements Runnable
{
  al(j paramj, int paramInt, String paramString, Context paramContext)
  {
  }

  public void run()
  {
    try
    {
      if (!j.u())
        this.d.a(this.c);
      if (j.v() == null)
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
 * Qualified Name:     com.tendcloud.tenddata.al
 * JD-Core Version:    0.6.2
 */