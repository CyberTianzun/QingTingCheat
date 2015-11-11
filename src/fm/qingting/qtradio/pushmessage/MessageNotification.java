package fm.qingting.qtradio.pushmessage;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.Notification.Builder;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat.Builder;
import android.widget.RemoteViews;
import fm.qingting.qtradio.QTRadioActivity;
import fm.qingting.qtradio.im.message.IMMessage;
import fm.qingting.qtradio.manager.QtApiLevelManager;
import fm.qingting.qtradio.model.ActivityNode;
import fm.qingting.qtradio.notification.Notifier;
import java.util.Random;

public class MessageNotification
{
  public static int GLOBAL_MESSAGE = 0;
  private static final String NOTIFICATION_ID = "11";
  private static final Random random = new Random(System.currentTimeMillis());
  public int mCategoryId;
  public int mChannleId;
  public String mContent;
  public int mContentType;
  private Context mContext;
  public String mMsgType;
  public int mProgramId;
  public String mTag;
  public String mTaskId;
  public String mTitle;

  public MessageNotification(Context paramContext)
  {
    this.mContext = paramContext;
  }

  public static void clearNotification(Context paramContext)
  {
    ((NotificationManager)paramContext.getSystemService("notification")).cancelAll();
  }

  @TargetApi(16)
  public static void sendActivityNotification(ActivityNode paramActivityNode, String paramString1, String paramString2, String paramString3, int paramInt, Context paramContext)
  {
    if ((paramActivityNode == null) || (paramString1 == null) || (paramContext == null))
      return;
    NotificationManager localNotificationManager = (NotificationManager)paramContext.getSystemService("notification");
    Intent localIntent = new Intent(paramContext, QTRadioActivity.class);
    localIntent.putExtra("notify_type", paramString1);
    localIntent.putExtra("NOTIFICATION_TITLE", paramActivityNode.infoTitle);
    localIntent.putExtra("NOTIFICATION_MESSAGE", paramActivityNode.desc);
    localIntent.putExtra("ACTIVITY_CONTENTURL", paramActivityNode.contentUrl);
    localIntent.putExtra("ACTIVITY_INFOURL", paramActivityNode.infoUrl);
    localIntent.putExtra("ACTIVITY_TITLEICON", paramActivityNode.titleIconUrl);
    localIntent.putExtra("push_task_id", paramString2);
    localIntent.putExtra("push_tag_id", paramString3);
    localIntent.putExtra("contentType", paramInt);
    PendingIntent localPendingIntent = PendingIntent.getActivity(paramContext, random.nextInt(), localIntent, 134217728);
    NotificationCompat.Builder localBuilder = new NotificationCompat.Builder(paramContext);
    localBuilder.setSmallIcon(Notifier.getNotificationIcon());
    localBuilder.setTicker(paramActivityNode.desc);
    localBuilder.setWhen(System.currentTimeMillis());
    localBuilder.setAutoCancel(true);
    localBuilder.setDefaults(1);
    localBuilder.setContentTitle(paramActivityNode.infoTitle);
    localBuilder.setContentText(paramActivityNode.desc);
    localBuilder.setContentIntent(localPendingIntent);
    Notification localNotification = localBuilder.build();
    localNotificationManager.cancelAll();
    localNotificationManager.notify(random.nextInt(), localNotification);
  }

  public static void sendIMNotification(IMMessage paramIMMessage, Context paramContext, String paramString)
  {
    if ((paramIMMessage == null) || (paramContext == null))
      return;
    NotificationManager localNotificationManager = (NotificationManager)paramContext.getSystemService("notification");
    Intent localIntent = new Intent(paramContext, QTRadioActivity.class);
    localIntent.putExtra("notify_type", "im");
    localIntent.putExtra("NOTIFICATION_TITLE", paramString);
    localIntent.putExtra("NOTIFICATION_MESSAGE", paramIMMessage.mMessage);
    localIntent.putExtra("chatType", paramIMMessage.chatType);
    localIntent.putExtra("fromUserId", paramIMMessage.mFromID);
    localIntent.putExtra("fromName", paramIMMessage.mFromName);
    localIntent.putExtra("groupId", paramIMMessage.mFromGroupId);
    localIntent.putExtra("msg", paramIMMessage.mMessage);
    localIntent.putExtra("sendTime", paramIMMessage.publish);
    localIntent.putExtra("fromGender", paramIMMessage.mGender);
    PendingIntent localPendingIntent = PendingIntent.getActivity(paramContext, random.nextInt(), localIntent, 134217728);
    NotificationCompat.Builder localBuilder = new NotificationCompat.Builder(paramContext);
    localBuilder.setSmallIcon(Notifier.getNotificationIcon());
    localBuilder.setTicker(paramString);
    localBuilder.setWhen(System.currentTimeMillis());
    localBuilder.setAutoCancel(true);
    localBuilder.setDefaults(1);
    localBuilder.setContentTitle(paramString);
    localBuilder.setContentText(paramIMMessage.mMessage);
    localBuilder.setContentIntent(localPendingIntent);
    Notification localNotification = localBuilder.build();
    localNotificationManager.cancelAll();
    localNotificationManager.notify(random.nextInt(), localNotification);
  }

  public static void sendImNotification(String paramString1, String paramString2, Bundle paramBundle, Context paramContext, boolean paramBoolean)
  {
    if ((paramString1 == null) || (paramString2 == null) || (paramBundle == null) || (paramString2 == null))
      return;
    NotificationManager localNotificationManager = (NotificationManager)paramContext.getSystemService("notification");
    Intent localIntent = new Intent(paramContext, QTRadioActivity.class);
    localIntent.putExtra("notify_type", "im");
    localIntent.putExtras(paramBundle);
    PendingIntent localPendingIntent = PendingIntent.getActivity(paramContext, random.nextInt(), localIntent, 134217728);
    NotificationCompat.Builder localBuilder = new NotificationCompat.Builder(paramContext);
    localBuilder.setSmallIcon(Notifier.getNotificationIcon());
    localBuilder.setTicker(paramString1);
    localBuilder.setWhen(System.currentTimeMillis());
    localBuilder.setAutoCancel(true);
    if (paramBoolean)
      localBuilder.setDefaults(5);
    localBuilder.setContentTitle(paramString1);
    localBuilder.setContentText(paramString2);
    localBuilder.setContentIntent(localPendingIntent);
    Notification localNotification = localBuilder.build();
    localNotificationManager.cancelAll();
    localNotificationManager.notify(random.nextInt(), localNotification);
  }

  @TargetApi(16)
  public static void sendNotification(MessageNotification paramMessageNotification, String paramString)
  {
    if ((paramMessageNotification == null) || (paramMessageNotification.mContext == null))
      return;
    NotificationManager localNotificationManager = (NotificationManager)paramMessageNotification.mContext.getSystemService("notification");
    Intent localIntent1 = new Intent(paramMessageNotification.mContext, QTRadioActivity.class);
    localIntent1.putExtra("categoryid", paramMessageNotification.mCategoryId);
    localIntent1.putExtra("parentid", 0);
    localIntent1.putExtra("channelid", paramMessageNotification.mChannleId);
    localIntent1.putExtra("programid", paramMessageNotification.mProgramId);
    localIntent1.putExtra("channelname", paramMessageNotification.mTitle);
    localIntent1.putExtra("notify_type", paramString);
    localIntent1.putExtra("live_topic", paramMessageNotification.mContent);
    localIntent1.putExtra("NOTIFICATION_MESSAGE", paramMessageNotification.mContent);
    localIntent1.putExtra("push_task_id", paramMessageNotification.mTaskId);
    localIntent1.putExtra("push_tag_id", paramMessageNotification.mTag);
    localIntent1.putExtra("contentType", paramMessageNotification.mContentType);
    PendingIntent localPendingIntent = PendingIntent.getActivity(paramMessageNotification.mContext, random.nextInt(), localIntent1, 134217728);
    Intent localIntent2;
    Notification localNotification;
    if (QtApiLevelManager.isApiLevelSupported(16))
    {
      localIntent2 = new Intent("fm.qingting.qtradio.INSTANT_PLAY");
      if (paramMessageNotification.mContentType == 5)
      {
        localIntent2.putExtra("setplaychannelnode", paramMessageNotification.mChannleId);
        localIntent2.putExtras(localIntent1);
        Notification.Builder localBuilder = new Notification.Builder(paramMessageNotification.mContext);
        localBuilder.setSmallIcon(Notifier.getNotificationIcon());
        localBuilder.setTicker(paramMessageNotification.mContent);
        localBuilder.setWhen(System.currentTimeMillis());
        localBuilder.setAutoCancel(true);
        localBuilder.setDefaults(1);
        localNotification = localBuilder.build();
        RemoteViews localRemoteViews = new RemoteViews(paramMessageNotification.mContext.getPackageName(), 2130903045);
        localRemoteViews.setOnClickPendingIntent(2131230749, PendingIntent.getBroadcast(paramMessageNotification.mContext, 0, localIntent2, 134217728));
        localRemoteViews.setTextViewText(2131230751, paramMessageNotification.mTitle);
        localRemoteViews.setTextViewText(2131230752, paramMessageNotification.mContent);
        localNotification.contentIntent = localPendingIntent;
        localNotification.contentView = localRemoteViews;
      }
    }
    while (true)
    {
      localNotificationManager.cancelAll();
      localNotificationManager.notify(random.nextInt(), localNotification);
      return;
      if (paramMessageNotification.mContentType != 1)
        break;
      localIntent2.putExtra("setplaynode", paramMessageNotification.mProgramId);
      break;
      NotificationCompat.Builder localBuilder1 = new NotificationCompat.Builder(paramMessageNotification.mContext);
      localBuilder1.setSmallIcon(Notifier.getNotificationIcon());
      localBuilder1.setTicker(paramMessageNotification.mContent);
      localBuilder1.setWhen(System.currentTimeMillis());
      localBuilder1.setAutoCancel(true);
      localBuilder1.setDefaults(1);
      localBuilder1.setContentTitle(paramMessageNotification.mTitle);
      localBuilder1.setContentText(paramMessageNotification.mContent);
      localBuilder1.setContentIntent(localPendingIntent);
      localNotification = localBuilder1.build();
    }
  }

  public static void sendSimpleNotification(MessageNotification paramMessageNotification)
  {
    if ((paramMessageNotification == null) || (paramMessageNotification.mContext == null))
      return;
    new Notifier(paramMessageNotification.mContext).notify("11", "", paramMessageNotification.mTitle, paramMessageNotification.mContent, "", "", "", paramMessageNotification.mChannleId, paramMessageNotification.mMsgType, paramMessageNotification.mCategoryId, paramMessageNotification.mProgramId, 0, paramMessageNotification.mContentType, 0, paramMessageNotification.mTaskId, paramMessageNotification.mTag);
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.qtradio.pushmessage.MessageNotification
 * JD-Core Version:    0.6.2
 */