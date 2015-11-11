package com.umeng.message;

import android.content.Context;
import android.content.Intent;
import android.os.Process;
import com.umeng.common.message.Log;
import com.umeng.message.entity.UMessage;
import org.android.agoo.client.BaseIntentService;
import org.json.JSONObject;

public abstract class UmengBaseIntentService extends BaseIntentService
{
  private static final String a = UmengBaseIntentService.class.getName();

  protected final Class<?> getAgooService()
  {
    return UmengService.class.getClass();
  }

  protected void onError(Context paramContext, String paramString)
  {
    Log.c(a, "onError()[" + paramString + "]");
  }

  protected void onMessage(Context paramContext, Intent paramIntent)
  {
    if (Process.getElapsedCpuTime() < 3000L)
    {
      Log.a(a, "App is launched by push message");
      PushAgent.setAppLaunchByMessage();
    }
    String str = paramIntent.getStringExtra("body");
    Log.c(a, "onMessage():[" + str + "]");
    try
    {
      UMessage localUMessage = new UMessage(new JSONObject(str));
      UTrack.getInstance(getApplicationContext()).a(localUMessage);
      return;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
  }

  protected void onRegistered(Context paramContext, String paramString)
  {
    Log.c(a, "onRegistered()[" + paramString + "]");
    try
    {
      UTrack.getInstance(getApplicationContext()).trackRegister();
    }
    catch (Exception localException1)
    {
      try
      {
        while (true)
        {
          IUmengRegisterCallback localIUmengRegisterCallback = PushAgent.getInstance(paramContext).getRegisterCallback();
          if (localIUmengRegisterCallback != null)
            localIUmengRegisterCallback.onRegistered(paramString);
          return;
          localException1 = localException1;
          localException1.printStackTrace();
        }
      }
      catch (Exception localException2)
      {
        localException2.printStackTrace();
      }
    }
  }

  protected void onUnregistered(Context paramContext, String paramString)
  {
    Log.c(a, "onUnregistered()[" + paramString + "]");
    try
    {
      IUmengUnregisterCallback localIUmengUnregisterCallback = PushAgent.getInstance(paramContext).getUnregisterCallback();
      if (localIUmengUnregisterCallback != null)
        localIUmengUnregisterCallback.onUnregistered(paramString);
      return;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
  }

  protected boolean shouldProcessMessage(Context paramContext, Intent paramIntent)
  {
    return PushAgent.getInstance(paramContext).isEnabled();
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.umeng.message.UmengBaseIntentService
 * JD-Core Version:    0.6.2
 */