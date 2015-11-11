package fm.qingting.qtradio.pushmessage;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import fm.qingting.qtradio.NotificationService;

public class UFOReceiver extends BroadcastReceiver
{
  private void log(String paramString)
  {
    if (paramString != null);
  }

  public void onReceive(Context paramContext, Intent paramIntent)
  {
    if ((paramIntent == null) || (paramContext == null));
    while ((paramIntent.getAction() == null) || (!paramIntent.getAction().equalsIgnoreCase("fm.qingting.qtradio.GEXIN_MESSAGE")))
      return;
    Intent localIntent = new Intent(paramContext, NotificationService.class);
    localIntent.setAction("fm.qingting.qtradio.GEXIN_MESSAGE_BAK");
    Bundle localBundle = paramIntent.getExtras();
    if (localBundle != null)
    {
      String str1 = localBundle.getString("msg");
      localIntent.putExtra("msg", str1);
      log(str1);
      String str2 = localBundle.getString("alias");
      localIntent.putExtra("alias", str2);
      log(str2);
      String str3 = localBundle.getString("topic");
      log(str3);
      localIntent.putExtra("topic", str3);
      String str4 = localBundle.getString("reg");
      log(str4);
      localIntent.putExtra("reg", str4);
      String str5 = localBundle.getString("type");
      log(str5);
      localIntent.putExtra("type", String.valueOf(str5));
    }
    paramContext.startService(localIntent);
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.qtradio.pushmessage.UFOReceiver
 * JD-Core Version:    0.6.2
 */