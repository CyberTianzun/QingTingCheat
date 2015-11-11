package fm.qingting.qtradio.abtestlog;

import android.content.Context;
import android.util.Log;

public class AutoReserveLog
{
  public static final String ReserveNotification = "SendReserve";
  public static final String clickReserve = "ClickReserve";
  public static final String inApp = "inApp";
  public static final String launchApp = "launchApp";

  private static void log(String paramString)
  {
    Log.e("autoreserve", paramString);
  }

  public static void sendClickReserve(Context paramContext, String paramString1, String paramString2, String paramString3, String paramString4, String paramString5)
  {
    if ((paramString1 != null) && (paramString2 != null) && (paramString3 != null) && (paramString4 == null));
  }

  public static void sendReserveNotification(Context paramContext, String paramString1, String paramString2, String paramString3, String paramString4, String paramString5)
  {
    if ((paramString1 != null) && (paramString2 != null) && (paramString3 != null) && (paramString4 == null));
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.qtradio.abtestlog.AutoReserveLog
 * JD-Core Version:    0.6.2
 */