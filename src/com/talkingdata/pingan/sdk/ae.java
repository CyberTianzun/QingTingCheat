package com.talkingdata.pingan.sdk;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

final class ae extends Handler
{
  ae(Looper paramLooper)
  {
    super(paramLooper);
  }

  public void handleMessage(Message paramMessage)
  {
    PAAgent.a(paramMessage);
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.talkingdata.pingan.sdk.ae
 * JD-Core Version:    0.6.2
 */