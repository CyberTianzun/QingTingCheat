package fm.qingting.qtradio.pushmessage;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.umeng.message.UmengBaseIntentService;

public class UmengPushIntentService extends UmengBaseIntentService
{
  private static final String TAG = "UmengPushIntentService";
  private String mAlias;
  private String mMessage;
  private String mRegId;
  private String mTopic;

  private void log(String paramString)
  {
  }

  protected void onMessage(Context paramContext, Intent paramIntent)
  {
    super.onMessage(paramContext, paramIntent);
    if ((paramIntent == null) || (paramContext == null));
    while (true)
    {
      return;
      try
      {
        String str = paramIntent.getStringExtra("body");
        if (str != null)
        {
          log(str);
          JSONObject localJSONObject = ((JSONObject)JSON.parse(str)).getJSONObject("body").getJSONObject("custom");
          this.mTopic = localJSONObject.getString("topic");
          this.mAlias = localJSONObject.getString("alias");
          this.mMessage = localJSONObject.getString("msg");
          Intent localIntent = new Intent();
          localIntent.setAction("fm.qingting.qtradio.GEXIN_MESSAGE");
          localIntent.putExtra("alias", this.mAlias);
          localIntent.putExtra("msg", this.mMessage);
          localIntent.putExtra("topic", this.mTopic);
          localIntent.putExtra("reg", this.mRegId);
          localIntent.putExtra("type", String.valueOf(0));
          paramContext.sendBroadcast(localIntent);
          return;
        }
      }
      catch (Exception localException)
      {
        Log.e("UmengPushIntentService", localException.getMessage());
      }
    }
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.qtradio.pushmessage.UmengPushIntentService
 * JD-Core Version:    0.6.2
 */