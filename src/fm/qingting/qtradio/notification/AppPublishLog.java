package fm.qingting.qtradio.notification;

import android.content.Context;
import fm.qingting.qtradio.log.LogModule;
import fm.qingting.utils.DeviceInfo;

public class AppPublishLog
{
  public static final String DownloadApp = "DownloadApp";
  public static final String InstallApp = "InstallApp";
  public static final String WakeupApp = "WakeupApp";

  private static void log(String paramString)
  {
  }

  public static void sendDownloadAppLog(Context paramContext, String paramString)
  {
    if ((paramContext == null) || (paramString == null) || (paramString.equalsIgnoreCase("")))
      return;
    String str1 = "" + "\"";
    String str2 = str1 + DeviceInfo.getUniqueId(paramContext);
    String str3 = str2 + "\"";
    String str4 = str3 + ",";
    String str5 = str4 + "\"";
    String str6 = str5 + paramString;
    String str7 = str6 + "\"";
    String str8 = str7 + "\n";
    LogModule.getInstance().send("DownloadApp", str8);
  }

  public static void sendInstallAppLog(Context paramContext, String paramString)
  {
    if ((paramContext == null) || (paramString == null) || (paramString.equalsIgnoreCase("")))
      return;
    String str1 = "" + "\"";
    String str2 = str1 + DeviceInfo.getUniqueId(paramContext);
    String str3 = str2 + "\"";
    String str4 = str3 + ",";
    String str5 = str4 + "\"";
    String str6 = str5 + paramString;
    String str7 = str6 + "\"";
    String str8 = str7 + "\n";
    LogModule.getInstance().send("InstallApp", str8);
  }

  public static void sendWakeupAppLog(Context paramContext, String paramString)
  {
    if ((paramContext == null) || (paramString == null) || (paramString.equalsIgnoreCase("")))
      return;
    String str1 = "" + "\"";
    String str2 = str1 + DeviceInfo.getUniqueId(paramContext);
    String str3 = str2 + "\"";
    String str4 = str3 + ",";
    String str5 = str4 + "\"";
    String str6 = str5 + paramString;
    String str7 = str6 + "\"";
    String str8 = str7 + "\n";
    LogModule.getInstance().send("WakeupApp", str8);
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.qtradio.notification.AppPublishLog
 * JD-Core Version:    0.6.2
 */