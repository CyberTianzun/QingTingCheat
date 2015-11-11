package com.umeng.message;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import com.umeng.message.entity.UMessage;
import org.json.JSONObject;

public class UmengIntentService extends UmengBaseIntentService
{
  private static final String a = UmengIntentService.class.getName();

  protected void onMessage(Context paramContext, Intent paramIntent)
  {
    super.onMessage(paramContext, paramIntent);
    try
    {
      UMessage localUMessage = new UMessage(new JSONObject(paramIntent.getStringExtra("body")));
      UHandler localUHandler = PushAgent.getInstance(getApplicationContext()).getMessageHandler();
      if (localUHandler != null)
        localUHandler.handleMessage(getApplicationContext(), localUMessage);
      return;
    }
    catch (Exception localException)
    {
      Log.e(a, localException.getMessage());
    }
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.umeng.message.UmengIntentService
 * JD-Core Version:    0.6.2
 */