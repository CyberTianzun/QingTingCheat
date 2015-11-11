package fm.qingting.qtradio.im.message;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import fm.qingting.qtradio.im.group.GroupManager;
import fm.qingting.qtradio.manager.INETEventListener;
import fm.qingting.qtradio.model.GlobalCfg;
import fm.qingting.qtradio.pushmessage.MessageNotification;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class MessageSystem
  implements IMMessagePump.IRecvIMMsgListener, INETEventListener
{
  private static final int MSG_PAGE_SIZE = 20;
  private static final String MUTI_MSG_MODEL = "%d个联系人发来%d条消息";
  private static final String SINGLE_MSG_MODEL = "%s发%d条消息";
  private static MessageSystem _instance;
  private HashSet<String> mContactIds = new HashSet();
  private Context mContext;
  private QTIMReceiver mImReceiver;
  private List<String> mLstEnableGroups = new ArrayList();
  private IMMessage mRecvMessage = new IMMessage();
  private boolean mRegister = false;
  private int mUnReadMsgCnt;
  private String mUserId;
  private Map<String, String> mapMsg = new HashMap();

  public static MessageSystem getInstance()
  {
    if (_instance == null)
      _instance = new MessageSystem();
    return _instance;
  }

  private boolean handleMessage(IMMessage paramIMMessage)
  {
    if (paramIMMessage == null);
    while (!this.mRegister)
      return false;
    if (IMMessage.parseData(paramIMMessage, this.mRecvMessage))
      sendIntent(this.mRecvMessage, "recv_msg");
    return true;
  }

  private boolean hasAccount(String paramString)
  {
    if (paramString == null);
    String str;
    do
    {
      return false;
      str = GlobalCfg.getInstance(this.mContext).getAccounts();
    }
    while ((str == null) || (!str.contains(paramString)));
    return true;
  }

  private void initIMReceiver()
  {
    if (this.mContext == null)
      return;
    this.mImReceiver = new QTIMReceiver();
    IntentFilter localIntentFilter = new IntentFilter();
    localIntentFilter.addAction("recv_msg");
    localIntentFilter.setPriority(1);
    this.mContext.registerReceiver(this.mImReceiver, localIntentFilter);
  }

  private void log(String paramString)
  {
  }

  private void register()
  {
  }

  private void releaseIMReceiver()
  {
    try
    {
      if (this.mImReceiver != null)
      {
        this.mContext.unregisterReceiver(this.mImReceiver);
        this.mImReceiver = null;
      }
      return;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
  }

  private void saveEnableGroup()
  {
    Object localObject = null;
    int i = 0;
    if (i < this.mLstEnableGroups.size())
    {
      if (localObject != null);
      for (String str1 = (String)localObject + "_"; ; str1 = "")
      {
        String str2 = str1 + (String)this.mLstEnableGroups.get(i);
        i++;
        localObject = str2;
        break;
      }
    }
    if (localObject != null)
      GlobalCfg.getInstance(this.mContext).setEnableGroups((String)localObject);
  }

  private void sendEmptyListMessageIntent()
  {
    Intent localIntent = new Intent();
    localIntent.setAction("recv_list_msg");
    localIntent.putStringArrayListExtra("lstmsg", new ArrayList());
    this.mContext.sendBroadcast(localIntent);
  }

  private void sendIntent(int paramInt)
  {
    if (this.mContext == null)
      return;
    Intent localIntent = new Intent();
    localIntent.setAction("status_msg");
    localIntent.putExtra("status", paramInt);
    this.mContext.sendBroadcast(localIntent);
  }

  private void sendIntent(IMMessage paramIMMessage, String paramString)
  {
    if ((paramIMMessage == null) || (this.mContext == null) || (paramString == null))
      return;
    Intent localIntent = new Intent();
    localIntent.setAction(paramString);
    localIntent.putExtra("chatType", paramIMMessage.chatType);
    localIntent.putExtra("groupId", paramIMMessage.mFromGroupId);
    localIntent.putExtra("fromUserId", paramIMMessage.mFromID);
    localIntent.putExtra("fromName", paramIMMessage.mFromName);
    localIntent.putExtra("sendTime", paramIMMessage.publish);
    localIntent.putExtra("msg", paramIMMessage.mMessage);
    localIntent.putExtra("groupName", paramIMMessage.mGroupName);
    localIntent.putExtra("fromAvatar", paramIMMessage.mFromAvatar);
    localIntent.putExtra("toUserId", paramIMMessage.mToUserId);
    localIntent.putExtra("fromGender", paramIMMessage.mGender);
    this.mContext.sendOrderedBroadcast(localIntent, null);
  }

  private void sendIntent(List<String> paramList)
  {
    if ((paramList == null) || (this.mContext == null))
      return;
    Intent localIntent = new Intent();
    localIntent.setAction("recv_list_msg");
    localIntent.putStringArrayListExtra("lstmsg", (ArrayList)paramList);
    this.mContext.sendBroadcast(localIntent);
  }

  private void unRegister()
  {
  }

  public void addGroup(String paramString1, String paramString2)
  {
    GroupManager.getInstance().addGroup(paramString1, paramString2);
  }

  public void clearNotificationMsg()
  {
    this.mContactIds.clear();
    this.mUnReadMsgCnt = 0;
    MessageNotification.clearNotification(this.mContext);
  }

  public void clearUnReadMsg(String paramString)
  {
  }

  public void disableGroup(String paramString)
  {
    for (int i = 0; ; i++)
      if (i < this.mLstEnableGroups.size())
      {
        if (((String)this.mLstEnableGroups.get(i)).equalsIgnoreCase(paramString))
        {
          this.mLstEnableGroups.remove(i);
          saveEnableGroup();
        }
      }
      else
        return;
  }

  public void disableUserID(String paramString)
  {
  }

  public void enableGroup(String paramString)
  {
    if (paramString == null);
    while (hasEnableGroups(paramString))
      return;
    this.mLstEnableGroups.add(paramString);
    saveEnableGroup();
  }

  public void enableUserId(String paramString)
  {
  }

  public void exitGroup(String paramString1, String paramString2)
  {
    GroupManager.getInstance().exitGroup(paramString1, paramString2);
  }

  public String getUserId()
  {
    return this.mUserId;
  }

  public boolean hasEnableGroups(String paramString)
  {
    if ((paramString == null) || (this.mLstEnableGroups == null));
    while (true)
    {
      return false;
      for (int i = 0; i < this.mLstEnableGroups.size(); i++)
        if (((String)this.mLstEnableGroups.get(i)).equalsIgnoreCase(paramString))
          return true;
    }
  }

  public void init(Context paramContext)
  {
    this.mContext = paramContext;
    IMMessagePump.getInstance().init(this.mContext);
    initIMReceiver();
  }

  public void initEnableGroups()
  {
    String str = GlobalCfg.getInstance(this.mContext).getEnableGroups();
    String[] arrayOfString;
    if (str != null)
    {
      arrayOfString = str.split("_");
      if (arrayOfString != null)
        break label27;
    }
    while (true)
    {
      return;
      label27: for (int i = 0; i < arrayOfString.length; i++)
        this.mLstEnableGroups.add(arrayOfString[i]);
    }
  }

  public void loadLastMsg(String paramString)
  {
    if (paramString == null);
  }

  public void loadMoreGroupMsg(String paramString, boolean paramBoolean)
  {
    if (paramString == null);
  }

  public void loadMoreUserMsg(String paramString, boolean paramBoolean)
  {
    if (paramString == null);
  }

  public void logout()
  {
    this.mRegister = false;
    this.mUserId = "";
  }

  public void onNetChanged(String paramString)
  {
  }

  public boolean onRecvIMMsg(IMMessage paramIMMessage, String paramString)
  {
    if (paramIMMessage == null);
    return false;
  }

  public void sendGroupMsg(String paramString1, String paramString2, String paramString3)
  {
    if ((paramString1 != null) && (paramString2 == null));
  }

  public void sendUserMsg(String paramString1, String paramString2, String paramString3)
  {
    if ((paramString1 != null) && (paramString2 == null));
  }

  public void setUserId(String paramString)
  {
    if ((paramString == null) || (paramString.equalsIgnoreCase("")));
    do
    {
      return;
      if (this.mUserId == null)
        break;
    }
    while (this.mUserId.equalsIgnoreCase(paramString));
    unRegister();
    this.mUserId = paramString;
    register();
    this.mRegister = true;
  }

  public boolean start(String paramString)
  {
    IMMessagePump.getInstance().registerRecvMsg(this);
    setUserId(paramString);
    this.mUnReadMsgCnt = 0;
    this.mContactIds.clear();
    return true;
  }

  public void stop()
  {
    IMMessagePump.getInstance().release();
    releaseIMReceiver();
  }

  class QTIMReceiver extends BroadcastReceiver
  {
    QTIMReceiver()
    {
    }

    public void onReceive(Context paramContext, Intent paramIntent)
    {
      if (paramIntent == null);
      Bundle localBundle;
      do
      {
        return;
        localBundle = paramIntent.getExtras();
      }
      while ((localBundle == null) || (!paramIntent.getAction().equalsIgnoreCase("recv_msg")));
      MessageSystem.access$008(MessageSystem.this);
      int i = localBundle.getInt("chatType");
      String str1 = "";
      String str2 = localBundle.getString("msg");
      if (i == 1);
      while (true)
      {
        MessageNotification.sendImNotification(str1, str2, localBundle, MessageSystem.this.mContext, true);
        return;
        String str3 = localBundle.getString("fromUserId");
        String str4 = localBundle.getString("fromName");
        MessageSystem.this.mContactIds.add(str3);
        if (MessageSystem.this.mContactIds.size() == 1)
        {
          Locale localLocale2 = Locale.CHINESE;
          Object[] arrayOfObject2 = new Object[2];
          arrayOfObject2[0] = str4;
          arrayOfObject2[1] = Integer.valueOf(MessageSystem.this.mUnReadMsgCnt);
          str1 = String.format(localLocale2, "%s发%d条消息", arrayOfObject2);
        }
        else
        {
          Locale localLocale1 = Locale.CHINESE;
          Object[] arrayOfObject1 = new Object[2];
          arrayOfObject1[0] = Integer.valueOf(MessageSystem.this.mContactIds.size());
          arrayOfObject1[1] = Integer.valueOf(MessageSystem.this.mUnReadMsgCnt);
          str1 = String.format(localLocale1, "%d个联系人发来%d条消息", arrayOfObject1);
        }
      }
    }
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.qtradio.im.message.MessageSystem
 * JD-Core Version:    0.6.2
 */