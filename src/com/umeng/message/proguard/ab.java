package com.umeng.message.proguard;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import org.android.agoo.net.mtop.MtopHttpChunked;

class ab extends BroadcastReceiver
{
  private volatile int b = 0;

  ab(aa paramaa)
  {
  }

  public void onReceive(Context paramContext, Intent paramIntent)
  {
    String str = paramIntent.getAction();
    if (TextUtils.equals(str, "android.intent.action.SCREEN_ON"))
    {
      Q.c("MessagePush", "screen_on");
      ay localay = aa.a(this.a).readyState();
      if ((localay != ay.b) && (localay != ay.a))
        aa.a(this.a, aa.b(this.a), "screen_on_connect");
    }
    do
    {
      do
        return;
      while (!TextUtils.equals(str, "android.net.conn.CONNECTIVITY_CHANGE"));
      this.b = (1 + this.b);
    }
    while (this.b <= 1);
    Q.c("MessagePush", "network_change");
    aa.a(this.a, aa.c(this.a), "network_change_connect");
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.umeng.message.proguard.ab
 * JD-Core Version:    0.6.2
 */