package com.umeng.message.proguard;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import org.android.agoo.net.mtop.MtopHttpChunked;

class ae
  implements Runnable
{
  ae(aa.a parama, Intent paramIntent, Context paramContext)
  {
  }

  private void a(long paramLong)
  {
    Q.c("MessagePush", "connect[" + aa.d(this.c.a) + "]heart[" + paramLong + " ms]timeout--->[reconnect:" + aa.m(this.c.a) + "ms]");
    U.a(this.b, paramLong, aa.a.c(this.c));
    long l = aa.j(this.c.a);
    if (l != -1L)
    {
      aa.d(this.c.a, l, "heart_connect_network_wap");
      return;
    }
    aa.d(this.c.a, aa.m(this.c.a), "heart_connect");
  }

  public void run()
  {
    if (TextUtils.equals("agoo_action_heart", this.a.getAction()))
    {
      ay localay = aa.a(this.c.a).readyState();
      long l = System.currentTimeMillis() - aa.h(this.c.a);
      if ((aa.a.a(this.c)) && (localay == ay.b) && (aa.h(this.c.a) > -1L) && (l > aa.a.b(this.c)))
        a(l);
    }
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.umeng.message.proguard.ae
 * JD-Core Version:    0.6.2
 */