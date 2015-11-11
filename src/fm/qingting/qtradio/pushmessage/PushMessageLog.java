package fm.qingting.qtradio.pushmessage;

import android.content.Context;
import android.os.Bundle;
import fm.qingting.qtradio.log.LogModule;
import fm.qingting.qtradio.logger.QTLogger;

public class PushMessageLog
{
  public static final String ClickPushMsg = "ClickGeTuiPushMsg";
  public static final String RecvPushMsg = "RecvGeTuiPushMsg";
  public static final String SendPushMsg = "SendGeTuiPushMsg";
  public static final String StartPushServiceMsg = "StartPushServiceMsg";

  private static void log(String paramString)
  {
  }

  public static void sendGetuiMsgFromServiceLog(Context paramContext, boolean paramBoolean1, boolean paramBoolean2)
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
    String str2 = str1 + "\"" + paramBoolean1 + "\"";
    String str3 = str2 + ",\"" + paramBoolean2 + "\"";
    LogModule.getInstance().send("RecvGeTuiMsgFromService", str3);
  }

  public static void sendGetuiMsgLog(Context paramContext, String paramString1, String paramString2)
  {
    if ((paramContext == null) || ((paramString1 == null) && (paramString2 == null)));
    String str1;
    do
    {
      return;
      if (paramString1 == null)
        paramString1 = "";
      if (paramString2 == null)
        paramString2 = "";
      QTLogger.getInstance().setContext(paramContext);
      str1 = QTLogger.getInstance().buildCommonLog();
    }
    while (str1 == null);
    String str2 = str1 + "\"" + paramString1 + "\"";
    String str3 = str2 + ",\"" + paramString2 + "\"";
    LogModule.getInstance().send("RecvGeTuiMsg", str3);
  }

  public static void sendPushLog(Context paramContext, Bundle paramBundle, String paramString)
  {
    int i = paramBundle.getInt("channelid");
    int j = paramBundle.getInt("categoryid");
    int k = paramBundle.getInt("programid");
    String str = paramBundle.getString("NOTIFICATION_MESSAGE");
    sendPushLog(paramContext, paramBundle.getString("push_task_id"), paramBundle.getString("push_tag_id"), Integer.valueOf(j).intValue(), 0, Integer.valueOf(i).intValue(), Integer.valueOf(k).intValue(), str, paramString);
  }

  public static void sendPushLog(Context paramContext, MessageNotification paramMessageNotification, String paramString)
  {
    sendPushLog(paramContext, paramMessageNotification.mTaskId, paramMessageNotification.mTag, paramMessageNotification.mCategoryId, 0, paramMessageNotification.mChannleId, paramMessageNotification.mProgramId, paramMessageNotification.mContent, paramString);
  }

  public static void sendPushLog(Context paramContext, String paramString1, String paramString2)
  {
    if ((paramContext == null) || (paramString1 == null) || (paramString2 == null));
    String str1;
    do
    {
      return;
      QTLogger.getInstance().setContext(paramContext);
      str1 = QTLogger.getInstance().buildCommonLog();
    }
    while (str1 == null);
    String str2 = str1 + "\"" + paramString1 + "\"";
    String str3 = str2 + ",\"" + paramString2 + "\"";
    LogModule.getInstance().send("PushCID", str3);
  }

  public static void sendPushLog(Context paramContext, String paramString1, String paramString2, int paramInt1, int paramInt2, int paramInt3, int paramInt4, String paramString3, String paramString4)
  {
    if ((paramContext == null) || (paramString4 == null) || (paramString1 == null) || (paramString2 == null));
    String str1;
    do
    {
      return;
      QTLogger.getInstance().setContext(paramContext);
      str1 = QTLogger.getInstance().buildCommonLog();
    }
    while (str1 == null);
    String str2 = str1 + "\"" + paramString1 + "\"";
    String str3 = str2 + ",\"" + paramString2 + "\"";
    String str4 = str3 + ",\"" + paramInt1 + "\"";
    String str5 = str4 + ",\"" + paramInt2 + "\"";
    String str6 = str5 + ",\"" + paramInt3 + "\"";
    String str7 = str6 + ",\"" + paramInt4 + "\"";
    if (paramString3 == null)
      paramString3 = "";
    String str8 = str7 + ",\"" + paramString3 + "\"";
    LogModule.getInstance().send(paramString4, str8);
  }

  public static void sendPushOutOfDateLog(Context paramContext, long paramLong)
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
    String str2 = str1 + "\"" + String.valueOf(paramLong) + "\"";
    String str3 = str2 + ",\"" + String.valueOf(System.currentTimeMillis() / 1000L) + "\"";
    LogModule.getInstance().send("PushOutOfDate", str3);
  }

  public static void sendPushUserLog(Context paramContext, String paramString)
  {
    if ((paramContext == null) || (paramString == null));
    String str1;
    do
    {
      return;
      QTLogger.getInstance().setContext(paramContext);
      str1 = QTLogger.getInstance().buildCommonLog();
    }
    while (str1 == null);
    String str2 = str1 + "\"" + paramString + "\"";
    LogModule.getInstance().send("PushUser", str2);
  }

  public static void sendXiaoMsgLog(Context paramContext, String paramString1, String paramString2)
  {
    if ((paramContext == null) || ((paramString1 == null) && (paramString2 == null)));
    String str1;
    do
    {
      return;
      if (paramString1 == null)
        paramString1 = "";
      if (paramString2 == null)
        paramString2 = "";
      QTLogger.getInstance().setContext(paramContext);
      str1 = QTLogger.getInstance().buildCommonLog();
    }
    while (str1 == null);
    String str2 = str1 + "\"" + paramString1 + "\"";
    String str3 = str2 + ",\"" + paramString2 + "\"";
    LogModule.getInstance().send("RecvXiaoMiMsg", str3);
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.qtradio.pushmessage.PushMessageLog
 * JD-Core Version:    0.6.2
 */