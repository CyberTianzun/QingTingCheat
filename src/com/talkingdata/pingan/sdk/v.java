package com.talkingdata.pingan.sdk;

import android.os.Handler;
import android.os.Message;
import java.util.Map;

final class v
  implements Runnable
{
  v(String paramString1, String paramString2, Map paramMap)
  {
  }

  public void run()
  {
    try
    {
      if (!PAAgent.p())
        return;
      PAAgent.a locala = new PAAgent.a();
      locala.a = this.a;
      locala.b = this.b;
      locala.g = this.c;
      Handler localHandler = d.a();
      localHandler.sendMessage(Message.obtain(localHandler, 4, locala));
      return;
    }
    catch (Throwable localThrowable)
    {
    }
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.talkingdata.pingan.sdk.v
 * JD-Core Version:    0.6.2
 */