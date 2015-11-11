package com.umeng.message;

import org.json.JSONException;
import org.json.JSONObject;

class UTrack$3
  implements Runnable
{
  UTrack$3(UTrack paramUTrack)
  {
  }

  public void run()
  {
    try
    {
      JSONObject localJSONObject1 = UTrack.b(this.a);
      JSONObject localJSONObject2 = UTrack.a(this.a, localJSONObject1, MsgConstant.LAUNCH_ENDPOINT);
      if ("ok".equalsIgnoreCase(localJSONObject2.optString("success")))
      {
        MessageSharedPrefs.getInstance(UTrack.a(this.a)).setAppLaunchLogSentAt(System.currentTimeMillis());
        int i = localJSONObject2.optInt("launch_policy", -1);
        int j = localJSONObject2.optInt("tag_policy", -1);
        if (i > 0)
          MessageSharedPrefs.getInstance(UTrack.a(this.a)).setAppLaunchLogSendPolicy(i);
        if (j > 0)
          MessageSharedPrefs.getInstance(UTrack.a(this.a)).setTagSendPolicy(j);
      }
      return;
    }
    catch (JSONException localJSONException)
    {
      localJSONException.printStackTrace();
      return;
    }
    finally
    {
      UTrack.b(false);
    }
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.umeng.message.UTrack.3
 * JD-Core Version:    0.6.2
 */