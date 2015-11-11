package com.umeng.fb;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.support.v4.app.NotificationCompat.Builder;
import com.umeng.fb.model.Conversation;
import com.umeng.fb.model.Conversation.SyncListener;
import com.umeng.fb.model.DevReply;
import com.umeng.fb.model.Reply;
import com.umeng.fb.model.Store;
import com.umeng.fb.model.UserInfo;
import com.umeng.fb.res.DrawableMapper;
import com.umeng.fb.res.StringMapper;
import com.umeng.fb.util.Log;
import java.util.List;
import java.util.Locale;

public class FeedbackAgent
{
  private static final String TAG = FeedbackAgent.class.getName();
  private Context mContext;
  private Store store;

  public FeedbackAgent(Context paramContext)
  {
    this.mContext = paramContext;
    this.store = Store.getInstance(this.mContext);
  }

  private void showReplyNotification(String paramString)
  {
    NotificationManager localNotificationManager = (NotificationManager)this.mContext.getSystemService("notification");
    String str = this.mContext.getString(StringMapper.umeng_fb_notification_ticker_text(this.mContext));
    Intent localIntent = new Intent(this.mContext, ConversationActivity.class);
    localIntent.setFlags(131072);
    PendingIntent localPendingIntent = PendingIntent.getActivity(this.mContext, 0, localIntent, 0);
    localNotificationManager.notify(0, new NotificationCompat.Builder(this.mContext).setSmallIcon(DrawableMapper.umeng_fb_statusbar_icon(this.mContext)).setContentTitle(str).setTicker(str).setContentText(paramString).setAutoCancel(true).setContentIntent(localPendingIntent).build());
  }

  public List<String> getAllConversationIds()
  {
    return this.store.getAllConversationIds();
  }

  public Conversation getConversationById(String paramString)
  {
    return this.store.getConversationById(paramString);
  }

  public Conversation getDefaultConversation()
  {
    List localList = getAllConversationIds();
    if ((localList == null) || (localList.size() < 1))
    {
      Log.d(TAG, "getDefaultConversation: No conversation saved locally. Create a new one.");
      return new Conversation(this.mContext);
    }
    Log.d(TAG, "getDefaultConversation: There are " + localList.size() + " saved locally, use the first one by default.");
    return getConversationById((String)localList.get(0));
  }

  public UserInfo getUserInfo()
  {
    return this.store.getUserInfo();
  }

  public long getUserInfoLastUpdateAt()
  {
    return this.store.getUserInfoLastUpdateAt();
  }

  public void setDebug(boolean paramBoolean)
  {
    Log.LOG = paramBoolean;
  }

  public void setUserInfo(UserInfo paramUserInfo)
  {
    this.store.saveUserInfo(paramUserInfo);
  }

  public void startFeedbackActivity()
  {
    try
    {
      Intent localIntent = new Intent();
      localIntent.setClass(this.mContext, ConversationActivity.class);
      this.mContext.startActivity(localIntent);
      return;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
  }

  public void sync()
  {
    Conversation.SyncListener local1 = new Conversation.SyncListener()
    {
      public void onReceiveDevReply(List<DevReply> paramAnonymousList)
      {
        if ((paramAnonymousList == null) || (paramAnonymousList.size() < 1))
          return;
        String str3;
        Locale localLocale2;
        Object[] arrayOfObject2;
        if (paramAnonymousList.size() == 1)
        {
          str3 = FeedbackAgent.this.mContext.getResources().getString(StringMapper.umeng_fb_notification_content_formatter_single_msg(FeedbackAgent.this.mContext));
          localLocale2 = Locale.US;
          arrayOfObject2 = new Object[1];
          arrayOfObject2[0] = ((DevReply)paramAnonymousList.get(0)).getContent();
        }
        String str1;
        Locale localLocale1;
        Object[] arrayOfObject1;
        for (String str2 = String.format(localLocale2, str3, arrayOfObject2); ; str2 = String.format(localLocale1, str1, arrayOfObject1))
        {
          try
          {
            FeedbackAgent.this.showReplyNotification(str2);
            return;
          }
          catch (Exception localException)
          {
            localException.printStackTrace();
            return;
          }
          str1 = FeedbackAgent.this.mContext.getResources().getString(StringMapper.umeng_fb_notification_content_formatter_multiple_msg(FeedbackAgent.this.mContext));
          localLocale1 = Locale.US;
          arrayOfObject1 = new Object[1];
          arrayOfObject1[0] = Integer.valueOf(paramAnonymousList.size());
        }
      }

      public void onSendUserReply(List<Reply> paramAnonymousList)
      {
      }
    };
    getDefaultConversation().sync(local1);
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.umeng.fb.FeedbackAgent
 * JD-Core Version:    0.6.2
 */