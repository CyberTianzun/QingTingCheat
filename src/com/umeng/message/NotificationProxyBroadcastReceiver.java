package com.umeng.message;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.umeng.common.message.Log;
import com.umeng.message.entity.UMessage;
import org.json.JSONException;
import org.json.JSONObject;

public class NotificationProxyBroadcastReceiver extends BroadcastReceiver
{
  public static final int EXTRA_ACTION_CLICK = 10;
  public static final int EXTRA_ACTION_DISMISS = 11;
  public static final int EXTRA_ACTION_NOT_EXIST = -1;
  public static final String EXTRA_KEY_ACTION = "ACTION";
  public static final String EXTRA_KEY_MSG = "MSG";
  private static final String a = NotificationProxyBroadcastReceiver.class.getName();
  private UHandler b;

  public void onReceive(Context paramContext, Intent paramIntent)
  {
    String str1 = paramIntent.getStringExtra("MSG");
    try
    {
      int i = paramIntent.getIntExtra("ACTION", -1);
      String str2 = a;
      Object[] arrayOfObject = new Object[2];
      arrayOfObject[0] = str1;
      arrayOfObject[1] = Integer.valueOf(i);
      Log.c(str2, String.format("onReceive[msg=%s, action=%d]", arrayOfObject));
      localUMessage = new UMessage(new JSONObject(str1));
      switch (i)
      {
      case 11:
        Log.c(a, "dismiss notification");
        UTrack.getInstance(paramContext).trackMsgDismissed(localUMessage);
        return;
      case 10:
      }
    }
    catch (JSONException localJSONException)
    {
      UMessage localUMessage;
      localJSONException.printStackTrace();
      return;
      Log.c(a, "click notification");
      UTrack.getInstance(paramContext).trackMsgClick(localUMessage);
      this.b = PushAgent.getInstance(paramContext).getNotificationClickHandler();
      if (this.b != null)
      {
        this.b.handleMessage(paramContext, localUMessage);
        return;
      }
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.umeng.message.NotificationProxyBroadcastReceiver
 * JD-Core Version:    0.6.2
 */