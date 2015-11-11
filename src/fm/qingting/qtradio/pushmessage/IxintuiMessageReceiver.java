package fm.qingting.qtradio.pushmessage;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import fm.qingting.qtradio.NotificationService;
import fm.qingting.qtradio.log.LogModule;

public class IxintuiMessageReceiver extends BroadcastReceiver
{
  private String mAlias;
  private String mMessage;
  private String mRegId;
  private String mTopic;

  private void log(String paramString)
  {
  }

  public void onReceive(Context paramContext, Intent paramIntent)
  {
    if ((paramContext == null) || (paramIntent == null));
    while (true)
    {
      return;
      try
      {
        String str1 = paramIntent.getAction();
        if (str1 != null)
        {
          log("message received, action is: " + str1);
          if (str1.equals("com.ixintui.action.MESSAGE"))
          {
            String str2 = paramIntent.getStringExtra("com.ixintui.MESSAGE");
            log("message received, msg is: " + str2);
            JSONObject localJSONObject = (JSONObject)JSON.parse(str2);
            this.mTopic = localJSONObject.getString("topic");
            this.mAlias = localJSONObject.getString("alias");
            this.mMessage = localJSONObject.getString("msg");
            if ((str2 != null) && (str2.equalsIgnoreCase("{\"topic\":\"qingting:startService\"}")))
            {
              log("message received, msg is: " + str2 + " startservice");
              Intent localIntent2 = new Intent(paramContext, NotificationService.class);
              localIntent2.setAction("fm.qingting.connectivity.change");
              paramContext.startService(localIntent2);
              return;
            }
            Intent localIntent1 = new Intent();
            localIntent1.setAction("fm.qingting.qtradio.GEXIN_MESSAGE");
            localIntent1.putExtra("alias", this.mAlias);
            localIntent1.putExtra("msg", this.mMessage);
            localIntent1.putExtra("topic", this.mTopic);
            localIntent1.putExtra("reg", this.mRegId);
            localIntent1.putExtra("type", String.valueOf(0));
            LogModule.getInstance().init(paramContext);
            PushMessageLog.sendGetuiMsgLog(paramContext, ((JSONObject)JSON.parse(this.mMessage)).getString("uuid"), "ixintui");
            paramContext.sendBroadcast(localIntent1);
            return;
          }
        }
      }
      catch (Exception localException)
      {
      }
    }
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.qtradio.pushmessage.IxintuiMessageReceiver
 * JD-Core Version:    0.6.2
 */