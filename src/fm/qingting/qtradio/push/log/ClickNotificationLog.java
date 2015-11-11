package fm.qingting.qtradio.push.log;

import android.content.Context;
import fm.qingting.qtradio.log.LogModule;
import fm.qingting.qtradio.logger.QTLogger;

public class ClickNotificationLog
{
  public static final String LogType = "ClickedNotification";

  public static void sendClickNotification(int paramInt1, int paramInt2, String paramString1, int paramInt3, String paramString2, String paramString3, String paramString4, Context paramContext)
  {
    if (paramContext == null);
    String str1;
    do
    {
      return;
      QTLogger.getInstance().setContext(paramContext);
      str1 = QTLogger.getInstance().buildCommonLog();
    }
    while (str1 == null);
    String str2 = str1 + "\"" + String.valueOf(paramInt1) + "\"";
    String str3 = str2 + ",\"\"";
    String str4 = str3 + ",\"" + String.valueOf(paramInt2) + "\"";
    String str5 = str4 + ",\"" + paramString1 + "\"";
    String str6 = str5 + ",\"" + String.valueOf(paramInt3) + "\"";
    String str7 = str6 + ",\"" + paramString2 + "\"";
    String str8 = str7 + ",\"" + paramString3 + "\"";
    String str9 = str8 + ",\"0\"";
    LogModule.getInstance().send("ClickedNotification", str9);
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.qtradio.push.log.ClickNotificationLog
 * JD-Core Version:    0.6.2
 */