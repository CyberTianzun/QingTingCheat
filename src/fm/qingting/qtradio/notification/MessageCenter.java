package fm.qingting.qtradio.notification;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import fm.qingting.framework.data.DataManager;
import fm.qingting.framework.data.IResultToken;
import fm.qingting.framework.data.Result;
import fm.qingting.qtradio.NotificationService;
import fm.qingting.qtradio.model.AlarmInfo;
import fm.qingting.qtradio.model.GlobalCfg;
import fm.qingting.qtradio.model.Node;
import fm.qingting.qtradio.model.ProgramNode;
import fm.qingting.qtradio.model.ReserveNode;
import fm.qingting.utils.MobileState;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MessageCenter
{
  private static final int ALARM_DELAY_THRESHOLD = 3600;
  private static final int ALARM_TIME_THRESHOLD = 6;
  private static final int FIVEDAY = 432000;
  private static final String KEY_LOCALNOTIFICATION_INDEX = "key_localnotification_index";
  private static final String KEY_PUSHLOCALNOTIFICATION = "key_pushlocalnotification";
  private static final int MAX_NOTIFICATION_INDEX = 4;
  private static final int ONEDAY = 86400;
  private static final int ONEWEEK = 604800;
  private static final int RECOMMEND_TIME_INTERVAL = 600;
  private static final int RECOMMEND_TIME_THRESHOLD = 5400;
  private static final int TESTDAY = 60;
  private static final int TIME_THRESHOLD = 15;
  private static final int TWODAYS = 172800;
  private long appFirstStartTime = 0L;
  private Context context;
  private int getProgramTopicTime = 0;
  private boolean hasAlarmInfo = true;
  private boolean hasReserveMsg = true;
  private List<AlarmInfo> lstAlarmInfo = new ArrayList();
  private List<Node> lstReservePrograms = new ArrayList();
  private SharedPreferences sharedPrefs;
  private long startActivityTime = 0L;

  public MessageCenter(NotificationService paramNotificationService)
  {
    if (paramNotificationService != null)
    {
      this.startActivityTime = (1000L * GlobalCfg.getInstance(paramNotificationService).getActivityStartTime());
      this.appFirstStartTime = (1000L * GlobalCfg.getInstance(paramNotificationService).getAppFirstStartTime());
    }
    this.context = paramNotificationService;
    this.sharedPrefs = this.context.getSharedPreferences("client_preferences", 0);
    init();
  }

  private boolean acquireProperDuration()
  {
    int i = Calendar.getInstance().get(11);
    return (i >= 7) && (i <= 23);
  }

  private boolean acquireProperNet()
  {
    if (this.context == null);
    while (MobileState.getNetWorkType(this.context) != 1)
      return false;
    return true;
  }

  private boolean acquireProperNotification()
  {
    return getLocalNotificationIndex() < 4;
  }

  private boolean acquireProperTime()
  {
    long l1 = getBootStrapTime();
    long l2 = System.currentTimeMillis();
    long l3 = (l2 - l1) / 1000L;
    int i = getLocalNotificationIndex();
    if ((i == 0) && (this.appFirstStartTime > 0L))
      if ((l2 - this.appFirstStartTime) / 1000L <= 86400L);
    do
    {
      return true;
      return false;
      if ((i < 1) || (l1 <= 0L))
        break;
    }
    while (l3 > 604800L);
    return false;
    return false;
  }

  private void getAlarmInfoFromDB()
  {
    if ((this.lstAlarmInfo != null) && (this.lstAlarmInfo.size() != 0));
    do
    {
      do
        return;
      while (!this.hasAlarmInfo);
      Result localResult = DataManager.getInstance().getData("getdb_alarm_info", null, null).getResult();
      if (localResult.getSuccess())
        this.lstAlarmInfo = ((List)localResult.getData());
    }
    while (this.lstAlarmInfo.size() != 0);
    this.hasAlarmInfo = false;
  }

  private long getBootStrapTime()
  {
    if (this.context != null)
      return this.startActivityTime;
    return System.currentTimeMillis();
  }

  private int getDayOfWeek()
  {
    return Calendar.getInstance().get(7);
  }

  private int getLocalNotificationIndex()
  {
    SharedPreferences localSharedPreferences = this.sharedPrefs;
    int i = 0;
    if (localSharedPreferences != null)
      i = this.sharedPrefs.getInt("key_localnotification_index", 0);
    return i;
  }

  private localMessage getMessageByIndex(int paramInt)
  {
    switch (paramInt)
    {
    case 1:
    case 2:
    case 3:
    case 4:
    }
    return null;
  }

  private int getRelativeTime(long paramLong)
  {
    Calendar localCalendar = Calendar.getInstance();
    localCalendar.setTimeInMillis(paramLong);
    int i = localCalendar.get(11);
    int j = localCalendar.get(12);
    return localCalendar.get(13) + (60 * (i * 60) + j * 60);
  }

  private void getReserveProgramFromDB()
  {
    if ((this.lstReservePrograms != null) && (this.lstReservePrograms.size() != 0));
    do
    {
      do
        return;
      while (!this.hasReserveMsg);
      Result localResult = DataManager.getInstance().getData("get_reserve_program", null, null).getResult();
      if (localResult.getSuccess())
        this.lstReservePrograms = ((List)localResult.getData());
    }
    while (this.lstReservePrograms.size() != 0);
    this.hasReserveMsg = false;
  }

  private localMessage handleAlarm()
  {
    long l1 = System.currentTimeMillis();
    int i = (int)Math.pow(2.0D, getDayOfWeek());
    int j = getRelativeTime(l1);
    int k = GlobalCfg.getInstance(getContext()).getAlarmDayOfWeek();
    if (GlobalCfg.getInstance(getContext()).getAlarmShouted());
    String str1;
    String str2;
    String str3;
    String str4;
    long l2;
    int m;
    String str6;
    do
    {
      do
      {
        return null;
        str1 = GlobalCfg.getInstance(getContext()).getAlarmChannelName();
        str2 = GlobalCfg.getInstance(getContext()).getAlarmChannelId();
        str3 = GlobalCfg.getInstance(getContext()).getAlarmCategoryId();
        str4 = GlobalCfg.getInstance(getContext()).getAlarmRingToneId();
        l2 = GlobalCfg.getInstance(getContext()).getAlarmTime();
        String str5 = GlobalCfg.getInstance(getContext()).getAlarmType();
        m = 0;
        if (str5 != null)
        {
          boolean bool = str5.equalsIgnoreCase("");
          m = 0;
          if (!bool)
            m = Integer.valueOf(str5).intValue();
        }
        str6 = GlobalCfg.getInstance(getContext()).getAlarmProgramId();
      }
      while ((str2 == null) || (str2.equalsIgnoreCase("")) || (str3 == null) || (str3.equalsIgnoreCase("")) || (str4 == null) || (str4.equalsIgnoreCase("")) || (k != i));
      if ((j < l2) && (l2 < j + 6))
        return new localMessage(str1, "蜻蜓闹钟提醒：到时间啦。", str1, Integer.valueOf(str2).intValue(), Integer.valueOf(str6).intValue(), String.valueOf(l2), "alarm", Integer.valueOf(str4).intValue(), m, Integer.valueOf(str3).intValue());
    }
    while ((l2 >= j) || (j >= 3600L + l2));
    return new localMessage(str1, "蜻蜓闹钟提醒：到时间啦。", str1, Integer.valueOf(str2).intValue(), Integer.valueOf(str6).intValue(), String.valueOf(l2), "alarm", Integer.valueOf(str4).intValue(), m, Integer.valueOf(str3).intValue());
  }

  private localMessage handleLocalNotification()
  {
    boolean bool1 = acquireProperNotification();
    localMessage locallocalMessage = null;
    if (bool1)
    {
      boolean bool2 = acquireProperDuration();
      locallocalMessage = null;
      if (bool2)
      {
        boolean bool3 = acquireProperNet();
        locallocalMessage = null;
        if (bool3)
        {
          boolean bool4 = acquireProperTime();
          locallocalMessage = null;
          if (bool4)
          {
            int i = 1 + getLocalNotificationIndex();
            setLocalNotificationIndex(i);
            locallocalMessage = getMessageByIndex(i);
          }
        }
      }
    }
    return locallocalMessage;
  }

  private localMessage handleRecommend()
  {
    GlobalCfg.getInstance(getContext()).getLocalRecommend();
    return null;
  }

  private localMessage handleReply()
  {
    return null;
  }

  private void init()
  {
    getReserveProgramFromDB();
    getAlarmInfoFromDB();
  }

  public static String msToDate(long paramLong)
  {
    Date localDate = new Date(paramLong);
    return new SimpleDateFormat("yyyy-MM-dd").format(localDate);
  }

  private void setLocalNotificationIndex(int paramInt)
  {
    if (this.sharedPrefs != null)
    {
      SharedPreferences.Editor localEditor = this.sharedPrefs.edit();
      localEditor.putInt("key_localnotification_index", paramInt);
      localEditor.commit();
    }
  }

  public Context getContext()
  {
    return this.context;
  }

  public localMessage getMessage(String paramString, Map<String, Object> paramMap)
  {
    long l1 = System.currentTimeMillis() / 1000L;
    Object localObject;
    int j;
    localMessage locallocalMessage;
    if (paramString.equalsIgnoreCase("reserve"))
    {
      List localList = this.lstReservePrograms;
      localObject = null;
      if (localList != null)
      {
        int i = this.lstReservePrograms.size();
        localObject = null;
        if (i != 0)
        {
          j = 0;
          if (j >= this.lstReservePrograms.size())
            break label632;
          if ((this.lstReservePrograms.get(j) == null) || (!((Node)this.lstReservePrograms.get(j)).nodeName.equalsIgnoreCase("reserve")))
            break label371;
          ProgramNode localProgramNode = (ProgramNode)((ReserveNode)this.lstReservePrograms.get(j)).reserveNode;
          long l2 = ((ReserveNode)this.lstReservePrograms.get(j)).reserveTime;
          boolean bool2 = localProgramNode.mLiveInVirtual;
          int k = 0;
          if (bool2)
            k = 1;
          if ((l2 < l1) || (l2 >= 15L + l1))
            break label371;
          if ((((ReserveNode)this.lstReservePrograms.get(j)).channelId == 0) || (((ReserveNode)this.lstReservePrograms.get(j)).uniqueId == 0))
            break label626;
          locallocalMessage = new localMessage("节目智能提醒", "<<" + localProgramNode.title + ">>马上就要开始啦，点击即可收听哦^_^", ((ReserveNode)this.lstReservePrograms.get(j)).programName, ((ReserveNode)this.lstReservePrograms.get(j)).channelId, ((ReserveNode)this.lstReservePrograms.get(j)).uniqueId, String.valueOf(l2), "reserve", Integer.valueOf(((ReserveNode)this.lstReservePrograms.get(j)).uniqueId).intValue(), k, 0);
          label352: this.lstReservePrograms.remove(j);
        }
      }
    }
    while (true)
    {
      localObject = locallocalMessage;
      label371: 
      do
      {
        boolean bool1;
        do
        {
          do
          {
            do
            {
              do
              {
                return localObject;
                j++;
                break;
                if (!paramString.equalsIgnoreCase("alarm"))
                  break label432;
                localObject = handleAlarm();
              }
              while (localObject == null);
              GlobalCfg.getInstance(getContext()).setAlarmShouted(true);
              GlobalCfg.getInstance(this.context).saveValueToDB();
              GlobalCfg.getInstance(this.context).clearCache();
              return localObject;
              if (!paramString.equalsIgnoreCase("reply"))
                break label488;
              localObject = handleReply();
            }
            while (localObject == null);
            GlobalCfg.getInstance(getContext()).setRecvReply("");
            GlobalCfg.getInstance(this.context).saveValueToDB();
            GlobalCfg.getInstance(this.context).clearCache();
            return localObject;
            if (!paramString.equalsIgnoreCase("localrecommend"))
              break label560;
            localObject = handleRecommend();
          }
          while (localObject == null);
          GlobalCfg.getInstance(getContext()).setInterestShouted(true);
          GlobalCfg.getInstance(getContext()).setInterestNotify(System.currentTimeMillis() / 1000L);
          GlobalCfg.getInstance(this.context).saveValueToDB();
          GlobalCfg.getInstance(this.context).clearCache();
          return localObject;
          bool1 = paramString.equalsIgnoreCase("localNotification");
          localObject = null;
        }
        while (!bool1);
        localObject = handleLocalNotification();
      }
      while (localObject == null);
      label432: label488: GlobalCfg.getInstance(getContext()).setLocalNotify(((localMessage)localObject).page);
      label560: GlobalCfg.getInstance(this.context).saveValueToDB();
      GlobalCfg.getInstance(this.context).clearCache();
      return localObject;
      label626: locallocalMessage = null;
      break label352;
      label632: locallocalMessage = null;
    }
  }

  public boolean hasAlarmInfo()
  {
    return this.hasAlarmInfo;
  }

  public boolean hasReserveMsg()
  {
    return this.hasReserveMsg;
  }

  public boolean parseProgramTopics(String paramString1, String paramString2)
  {
    boolean bool1 = false;
    if (paramString1 != null)
    {
      bool1 = false;
      if (paramString2 == null);
    }
    try
    {
      JSONArray localJSONArray = new JSONObject(paramString1).getJSONArray("data");
      for (int i = 0; ; i++)
      {
        int j = localJSONArray.length();
        bool1 = false;
        if (i < j)
        {
          JSONObject localJSONObject = localJSONArray.getJSONObject(i);
          if (localJSONObject.getString("type").equalsIgnoreCase("program"))
          {
            String str = localJSONObject.getJSONObject("textinfo").getString("program");
            if ((str != null) && (!str.equalsIgnoreCase("")))
            {
              boolean bool2 = str.equalsIgnoreCase(paramString2);
              if (bool2)
                bool1 = true;
            }
          }
        }
        else
        {
          return bool1;
        }
      }
    }
    catch (JSONException localJSONException)
    {
      localJSONException.printStackTrace();
    }
    return false;
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.qtradio.notification.MessageCenter
 * JD-Core Version:    0.6.2
 */