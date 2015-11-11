package fm.qingting.qtradio.notification;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.telephony.TelephonyManager;
import fm.qingting.framework.data.DataManager;
import fm.qingting.framework.data.IResultToken;
import fm.qingting.framework.data.Result;
import fm.qingting.qtradio.NotificationService;
import fm.qingting.qtradio.model.Node;
import fm.qingting.qtradio.model.PullMsgConfig;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ConnectThread extends Thread
{
  private static final int LATEST_INTERVAL = 300;
  private Context context;
  private int duration = 1800;
  private boolean mDontPull = false;
  private SharedPreferences.Editor mEditor;
  private String mHasSendMsgIds;
  List<receivedMessage> messageList;
  private NotifyManager notifyManager;
  private SharedPreferences sharedPrefs;

  ConnectThread(NotificationService paramNotificationService)
  {
    this.context = paramNotificationService;
    this.notifyManager = new NotifyManager(paramNotificationService);
    this.sharedPrefs = this.context.getSharedPreferences("client_preferences", 0);
    this.mEditor = this.sharedPrefs.edit();
    this.messageList = new ArrayList();
    this.mHasSendMsgIds = this.sharedPrefs.getString("key_sentids", "init");
    if (this.mHasSendMsgIds.equalsIgnoreCase("init"))
    {
      List localList = (List)DataManager.getInstance().getData("getdb_pullmsgstate", null, null).getResult().getData();
      if ((localList != null) && (localList.size() > 0))
        moveData(localList);
    }
    else
    {
      return;
    }
    this.mHasSendMsgIds = ",";
    this.mEditor.putString("key_sentids", this.mHasSendMsgIds);
    this.mEditor.commit();
  }

  private void _sendMessage(receivedMessage paramreceivedMessage)
  {
    if (paramreceivedMessage == null)
      return;
    new Notifier(this.context).notify("11", "", paramreceivedMessage.title, paramreceivedMessage.content, "", String.valueOf(paramreceivedMessage.startTime), paramreceivedMessage.channelname, Integer.valueOf(paramreceivedMessage.channelid).intValue(), "pullmsg", Integer.valueOf(paramreceivedMessage.catId).intValue(), Integer.valueOf(paramreceivedMessage.programId).intValue(), Integer.valueOf(paramreceivedMessage.parentid).intValue(), paramreceivedMessage.msgType, 0, null, null);
  }

  private String bulidParamString()
  {
    String str1 = "?deviceid=" + this.sharedPrefs.getString("DEVICE_ID", "");
    String str2 = str1 + "&country=中国";
    String str3 = PullMsgConfig.getInstance().getPullRegion();
    if ((str3 == null) || (str3.equalsIgnoreCase("")))
      str3 = "上海市";
    String str4 = str2 + "&province=" + str3;
    String str5 = PullMsgConfig.getInstance().getPullCity();
    if ((str5 == null) || (str5.equalsIgnoreCase("")))
      str5 = "上海";
    String str6 = str4 + "&city=" + str5;
    String str7 = str6 + "&phonetype=";
    String str8 = str7 + PullMsgConfig.getInstance().getPhoneType();
    return str8 + "&isp=" + getTypeOfNetwork(this.context);
  }

  private boolean hasSendMsg(String paramString)
  {
    if ((this.mHasSendMsgIds == null) || (paramString == null))
      return false;
    return isIdSent(paramString);
  }

  private void insertSentId(String paramString)
  {
    if (this.mHasSendMsgIds == null)
      this.mHasSendMsgIds = ("," + paramString + ",");
    while (true)
    {
      this.mEditor.putString("key_sentids", this.mHasSendMsgIds);
      this.mEditor.commit();
      return;
      if (!this.mHasSendMsgIds.contains("," + paramString + ","))
        this.mHasSendMsgIds = (this.mHasSendMsgIds + paramString + ",");
    }
  }

  private boolean isIdSent(String paramString)
  {
    if (this.mHasSendMsgIds == null)
      return false;
    return this.mHasSendMsgIds.contains("," + paramString + ",");
  }

  private void moveData(List<String> paramList)
  {
    Object localObject = ",";
    int i = 0;
    while (i < paramList.size())
    {
      String str = (String)localObject + (String)paramList.get(i) + ",";
      i++;
      localObject = str;
    }
    this.mHasSendMsgIds = ((String)localObject);
    this.mEditor.putString("key_sentids", this.mHasSendMsgIds);
    this.mEditor.commit();
  }

  private List<receivedMessage> parsePullMsg(String paramString)
  {
    return null;
  }

  private receivedMessage pickLatestMessage()
  {
    if ((this.messageList == null) || (this.messageList.size() == 0))
      return null;
    long l = 9223372036854775807L;
    int i = -1;
    for (int j = 0; j < this.messageList.size(); j++)
      if ((l > ((receivedMessage)this.messageList.get(j)).startTime) && (!hasSendMsg(String.valueOf(((receivedMessage)this.messageList.get(j)).id))))
      {
        l = ((receivedMessage)this.messageList.get(j)).startTime;
        i = j;
      }
    if (i != -1)
      return (receivedMessage)this.messageList.get(i);
    return null;
  }

  private void pullMessage()
  {
    String str = this.notifyManager.getNotify("http://api.qingting.fm/api/newpush/getMessagev2" + bulidParamString(), null, null);
    if (str != null);
    try
    {
      if (!str.equalsIgnoreCase(""))
      {
        List localList = parsePullMsg(str);
        if (localList != null)
          saveMessages(localList);
      }
      return;
    }
    catch (Exception localException)
    {
    }
  }

  private void saveMessages(List<receivedMessage> paramList)
  {
    this.messageList = paramList;
  }

  private boolean sendMessage()
  {
    receivedMessage localreceivedMessage = pickLatestMessage();
    if (localreceivedMessage == null)
      this.mDontPull = false;
    long l;
    do
    {
      return false;
      l = System.currentTimeMillis() / 1000L;
      if ((localreceivedMessage.startTime > l) || (hasSendMsg(String.valueOf(localreceivedMessage.id))))
        break;
      this.mDontPull = false;
      insertSentId(String.valueOf(localreceivedMessage.id));
    }
    while ((localreceivedMessage.startTime != localreceivedMessage.endTime) && (l > localreceivedMessage.endTime));
    _sendMessage(localreceivedMessage);
    return true;
  }

  private long waiting()
  {
    return 1000L * this.duration;
  }

  private void writeToDB(List<Node> paramList)
  {
    if ((paramList == null) || (paramList.size() == 0))
      return;
    HashMap localHashMap = new HashMap();
    localHashMap.put("nodes", paramList);
    DataManager.getInstance().getData("updatedb_pull_node", null, localHashMap);
  }

  public String getTypeOfNetwork(Context paramContext)
  {
    int i;
    label47: label51: 
    do
    {
      try
      {
        i = ((TelephonyManager)paramContext.getSystemService("phone")).getNetworkType();
        if ((i != 6) && (i != 4))
        {
          if (i != 7)
            break label51;
          break label47;
          return "4";
        }
      }
      catch (Exception localException)
      {
        localException.printStackTrace();
        return "others";
      }
      return "3";
      if (i == 2)
        return "1";
    }
    while ((i != 1) && (i != 3) && (i != 8));
    return "2";
  }

  public void run()
  {
    try
    {
      while (!isInterrupted())
      {
        if (!this.mDontPull)
          pullMessage();
        sendMessage();
        Thread.sleep(waiting());
      }
    }
    catch (InterruptedException localInterruptedException)
    {
    }
  }

  public void saveMessage(receivedMessage paramreceivedMessage)
  {
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.qtradio.notification.ConnectThread
 * JD-Core Version:    0.6.2
 */