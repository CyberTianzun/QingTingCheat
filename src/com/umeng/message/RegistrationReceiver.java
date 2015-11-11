package com.umeng.message;

import android.content.Context;
import org.android.agoo.client.BaseBroadcastReceiver;

public class RegistrationReceiver extends BaseBroadcastReceiver
{
  protected String a(Context paramContext)
  {
    return PushAgent.getInstance(paramContext).getPushIntentServiceClass();
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.umeng.message.RegistrationReceiver
 * JD-Core Version:    0.6.2
 */