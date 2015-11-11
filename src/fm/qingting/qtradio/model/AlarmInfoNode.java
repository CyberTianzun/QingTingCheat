package fm.qingting.qtradio.model;

import android.os.Handler;
import android.os.Message;
import fm.qingting.framework.data.DataManager;
import fm.qingting.framework.data.IResultToken;
import fm.qingting.framework.data.Result;
import fm.qingting.qtradio.alarm.ClockManager;
import fm.qingting.qtradio.alarm.ClockManager.IClockListener;
import fm.qingting.qtradio.controller.ControllerManager;
import fm.qingting.qtradio.fm.PlayerAgent;
import fm.qingting.qtradio.notification.Notifier;
import fm.qingting.utils.LifeTime;
import fm.qingting.utils.QTMSGManage;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class AlarmInfoNode extends Node
  implements ClockManager.IClockListener
{
  private static final String[] NEXTWEEKS = { "", "下周日", "下周一", "下周二", "下周三", "下周四", "下周五", "下周六" };
  private static final int TIME_THRESHOLD = 2;
  private static final String[] WEEKS = { "", "周日", "周一", "周二", "周三", "周四", "周五", "周六" };
  private boolean hasRestored = false;
  private final Handler mHandler = new Handler()
  {
    public void handleMessage(Message paramAnonymousMessage)
    {
      if (paramAnonymousMessage == null)
        return;
      switch (paramAnonymousMessage.what)
      {
      default:
        return;
      case 1:
      }
      int i = ((Integer)paramAnonymousMessage.obj).intValue();
      AlarmInfoNode.this.chooseAlarm(i);
    }
  };
  public List<AlarmInfo> mLstAlarms;
  private boolean needToWriteToDB = false;

  public AlarmInfoNode()
  {
    this.nodeName = "alarminfo";
  }

  private void chooseAlarm(long paramLong)
  {
    if ((this.mLstAlarms == null) || (this.mLstAlarms.size() == 0));
    label308: 
    while (true)
    {
      return;
      Calendar localCalendar = Calendar.getInstance();
      int i = localCalendar.get(7);
      int j = (int)Math.pow(2.0D, i);
      localCalendar.setTimeInMillis(1000L * paramLong);
      int k = localCalendar.get(11);
      int m = localCalendar.get(12);
      int n = localCalendar.get(13) + (60 * (k * 60) + m * 60);
      for (int i1 = 0; ; i1++)
      {
        if (i1 >= this.mLstAlarms.size())
          break label308;
        AlarmInfo localAlarmInfo = (AlarmInfo)this.mLstAlarms.get(i1);
        if (localAlarmInfo.isAvailable)
        {
          if ((j & localAlarmInfo.dayOfWeek) != 0)
          {
            if (!hitAlarm(localAlarmInfo, n))
              continue;
            sendAlarmNotification(localAlarmInfo);
            localAlarmInfo.hasShouted = true;
            if (localAlarmInfo.repeat)
              break;
            localAlarmInfo.isAvailable = false;
            localAlarmInfo.hasShouted = false;
            return;
          }
          if ((localAlarmInfo.dayOfWeek == 0) && (isWorkDayToday(i)))
          {
            if (!hitAlarm(localAlarmInfo, n))
              continue;
            sendAlarmNotification(localAlarmInfo);
            localAlarmInfo.hasShouted = true;
            if (localAlarmInfo.repeat)
              break;
            localAlarmInfo.isAvailable = false;
            localAlarmInfo.hasShouted = false;
            return;
          }
          if ((!localAlarmInfo.repeat) && (hitAlarm(localAlarmInfo, n)))
          {
            sendAlarmNotification(localAlarmInfo);
            localAlarmInfo.hasShouted = true;
            if (localAlarmInfo.repeat)
              break;
            localAlarmInfo.isAvailable = false;
            localAlarmInfo.hasShouted = false;
            return;
          }
        }
      }
    }
  }

  private void deleteFromDB()
  {
    DataManager.getInstance().getData("deletedb_alarm_info", null, null);
  }

  private int getDayOfWeek()
  {
    return Calendar.getInstance().get(7);
  }

  private int getDayOfWeek(long paramLong)
  {
    Calendar localCalendar = Calendar.getInstance();
    localCalendar.setTimeInMillis(paramLong);
    return localCalendar.get(7);
  }

  private int getHourOfDay()
  {
    return Calendar.getInstance().get(11);
  }

  private int getRelativeTime(long paramLong)
  {
    Calendar localCalendar = Calendar.getInstance();
    localCalendar.setTimeInMillis(paramLong);
    int i = localCalendar.get(11);
    int j = localCalendar.get(12);
    return localCalendar.get(13) + (60 * (i * 60) + j * 60);
  }

  private boolean hitAlarm(AlarmInfo paramAlarmInfo, long paramLong)
  {
    if (paramAlarmInfo == null);
    do
    {
      return false;
      if ((paramAlarmInfo.alarmTime < paramLong) && (paramLong < 2L + paramAlarmInfo.alarmTime) && (!paramAlarmInfo.hasShouted))
        return true;
    }
    while ((paramLong <= 2L + paramAlarmInfo.alarmTime) || (!paramAlarmInfo.hasShouted));
    paramAlarmInfo.hasShouted = false;
    return false;
  }

  private int isExisted(AlarmInfo paramAlarmInfo)
  {
    if (paramAlarmInfo == null)
    {
      i = -1;
      return i;
    }
    if ((this.mLstAlarms == null) || (this.mLstAlarms.size() == 0))
      return -1;
    for (int i = 0; ; i++)
    {
      if (i >= this.mLstAlarms.size())
        break label97;
      if ((((AlarmInfo)this.mLstAlarms.get(i)).alarmTime == paramAlarmInfo.alarmTime) && (((AlarmInfo)this.mLstAlarms.get(i)).dayOfWeek == paramAlarmInfo.dayOfWeek))
        break;
    }
    label97: return -1;
  }

  private boolean isWorkDayToday(int paramInt)
  {
    return (paramInt >= 2) && (paramInt <= 6);
  }

  private boolean needToWriteToDB()
  {
    return this.needToWriteToDB;
  }

  private void sendAlarmNotification(AlarmInfo paramAlarmInfo)
  {
    String str1;
    if (paramAlarmInfo != null)
    {
      int i = getHourOfDay();
      QTMSGManage.getInstance().sendStatistcsMessage("ClockActive", String.valueOf(i));
      QTMSGManage.getInstance().sendStatistcsMessage("StartActivityByClock", String.valueOf(i));
      if (paramAlarmInfo.channelName == null)
        break label229;
      String str2 = "蜻蜓闹钟提醒：" + paramAlarmInfo.channelName;
      str1 = str2 + "开始广播啦";
      new Notifier(ControllerManager.getInstance().getContext()).notify("11", "", "闹钟", str1, null, "", paramAlarmInfo.channelName, paramAlarmInfo.channelId, "alarm", paramAlarmInfo.categoryId, Integer.valueOf(paramAlarmInfo.ringToneId).intValue(), 0, 0, paramAlarmInfo.alarmType, null, null);
      if ((paramAlarmInfo.ringToneId == null) || (InfoManager.getInstance().isNetworkAvailable()))
        break label254;
      InfoManager.getInstance().root().setPlayMode(RootNode.PlayMode.ALARMPLAY);
      if (InfoManager.getInstance().root().mRingToneInfoNode.getRingNodeById(paramAlarmInfo.ringToneId) != null)
      {
        InfoManager.getInstance().root().mRingToneInfoNode.setAvaliableRingId(paramAlarmInfo.ringToneId);
        PlayerAgent.getInstance().playRingTone(InfoManager.getInstance().root().mRingToneInfoNode.getRingNodeById(paramAlarmInfo.ringToneId));
      }
    }
    label229: label254: 
    while ((!InfoManager.getInstance().isNetworkAvailable()) || (PlayerAgent.getInstance().getCurrentPlayStatus() == 4096))
    {
      return;
      str1 = "蜻蜓闹钟提醒：" + "您有一个闹钟到点了";
      break;
    }
    ControllerManager.getInstance().openPlayViewForAlarm(paramAlarmInfo.categoryId, paramAlarmInfo.channelId, paramAlarmInfo.programId, paramAlarmInfo.alarmType);
  }

  public void WriteToDB()
  {
    if (!needToWriteToDB());
    do
    {
      return;
      this.needToWriteToDB = false;
      deleteFromDB();
    }
    while ((this.mLstAlarms == null) || (this.mLstAlarms.size() == 0));
    HashMap localHashMap = new HashMap();
    localHashMap.put("alarmInfos", this.mLstAlarms);
    DataManager.getInstance().getData("insertdb_alarm_info", null, localHashMap);
  }

  public void addDefaultAlarm(AlarmInfo paramAlarmInfo)
  {
    if (paramAlarmInfo == null);
    do
    {
      return;
      if (this.mLstAlarms == null)
        this.mLstAlarms = new ArrayList();
    }
    while (this.mLstAlarms.size() != 0);
    this.mLstAlarms.add(paramAlarmInfo);
    this.needToWriteToDB = true;
  }

  public String getNearestAlarmInfo()
  {
    List localList = this.mLstAlarms;
    if ((localList == null) || (localList.size() == 0))
      return null;
    long l1 = 9223372036854775807L;
    int i = 0;
    if (i < localList.size())
    {
      long l2 = ((AlarmInfo)localList.get(i)).getNextShoutTime();
      if (l2 == 9223372036854775807L);
      while (true)
      {
        i++;
        break;
        long l3 = System.currentTimeMillis() + l2 * 1000L;
        if (l1 > l3)
          l1 = l3;
      }
    }
    if (l1 == 9223372036854775807L)
      return null;
    StringBuilder localStringBuilder = new StringBuilder();
    Calendar localCalendar = Calendar.getInstance();
    int j = localCalendar.get(7);
    int k = localCalendar.get(11);
    int m = localCalendar.get(12);
    localCalendar.setTimeInMillis(l1);
    int n = localCalendar.get(7);
    int i1 = localCalendar.get(11);
    int i2 = localCalendar.get(12);
    if ((n - j == 1) || ((j == 7) && (n == 1)))
    {
      localStringBuilder.append("明天 ");
      Locale localLocale1 = Locale.US;
      Object[] arrayOfObject1 = new Object[2];
      arrayOfObject1[0] = Integer.valueOf(i1);
      arrayOfObject1[1] = Integer.valueOf(i2);
      localStringBuilder.append(String.format(localLocale1, "%02d:%02d", arrayOfObject1));
    }
    while (true)
    {
      return localStringBuilder.toString();
      if (n == j)
      {
        if (k < i1)
        {
          localStringBuilder.append("今天 ");
          Locale localLocale7 = Locale.US;
          Object[] arrayOfObject7 = new Object[2];
          arrayOfObject7[0] = Integer.valueOf(i1);
          arrayOfObject7[1] = Integer.valueOf(i2);
          localStringBuilder.append(String.format(localLocale7, "%02d:%02d", arrayOfObject7));
        }
        else if (k == i1)
        {
          if (m < i2)
          {
            localStringBuilder.append("今天 ");
            Locale localLocale6 = Locale.US;
            Object[] arrayOfObject6 = new Object[2];
            arrayOfObject6[0] = Integer.valueOf(i1);
            arrayOfObject6[1] = Integer.valueOf(i2);
            localStringBuilder.append(String.format(localLocale6, "%02d:%02d", arrayOfObject6));
          }
          else
          {
            localStringBuilder.append(NEXTWEEKS[n]);
            localStringBuilder.append(" ");
            Locale localLocale5 = Locale.US;
            Object[] arrayOfObject5 = new Object[2];
            arrayOfObject5[0] = Integer.valueOf(i1);
            arrayOfObject5[1] = Integer.valueOf(i2);
            localStringBuilder.append(String.format(localLocale5, "%02d:%02d", arrayOfObject5));
          }
        }
        else
        {
          localStringBuilder.append(NEXTWEEKS[n]);
          localStringBuilder.append(" ");
          Locale localLocale4 = Locale.US;
          Object[] arrayOfObject4 = new Object[2];
          arrayOfObject4[0] = Integer.valueOf(i1);
          arrayOfObject4[1] = Integer.valueOf(i2);
          localStringBuilder.append(String.format(localLocale4, "%02d:%02d", arrayOfObject4));
        }
      }
      else if (n < j)
      {
        localStringBuilder.append(NEXTWEEKS[n]);
        localStringBuilder.append(" ");
        Locale localLocale3 = Locale.US;
        Object[] arrayOfObject3 = new Object[2];
        arrayOfObject3[0] = Integer.valueOf(i1);
        arrayOfObject3[1] = Integer.valueOf(i2);
        localStringBuilder.append(String.format(localLocale3, "%02d:%02d", arrayOfObject3));
      }
      else
      {
        localStringBuilder.append(WEEKS[n]);
        localStringBuilder.append(" ");
        Locale localLocale2 = Locale.US;
        Object[] arrayOfObject2 = new Object[2];
        arrayOfObject2[0] = Integer.valueOf(i1);
        arrayOfObject2[1] = Integer.valueOf(i2);
        localStringBuilder.append(String.format(localLocale2, "%02d:%02d", arrayOfObject2));
      }
    }
  }

  public void init()
  {
    if (!LifeTime.isFirstLaunchAfterInstall)
      restoreFromDB();
    ClockManager.getInstance().addListener(this);
  }

  public boolean isEmpty()
  {
    if (this.mLstAlarms == null);
    while (this.mLstAlarms.size() == 0)
      return true;
    return false;
  }

  public void onClockTime(int paramInt)
  {
    if (this.mHandler != null)
    {
      Message localMessage = Message.obtain(this.mHandler, 1, Integer.valueOf(paramInt));
      this.mHandler.sendMessage(localMessage);
    }
  }

  public void onTime(Clock paramClock)
  {
  }

  public void onTimeStart(Clock paramClock)
  {
  }

  public void onTimeStop(Clock paramClock)
  {
  }

  public void onTimerRemoved()
  {
  }

  public void removeAlarm(int paramInt)
  {
    if (paramInt >= this.mLstAlarms.size())
      return;
    AlarmInfo localAlarmInfo = (AlarmInfo)this.mLstAlarms.get(paramInt);
    int i = (int)(localAlarmInfo.alarmTime / 3600L);
    if (localAlarmInfo.repeat);
    for (String str = "repeat"; ; str = "not")
    {
      this.mLstAlarms.remove(paramInt);
      this.needToWriteToDB = true;
      QTMSGManage.getInstance().sendStatistcsMessage("ClockRemove", String.valueOf(i));
      QTMSGManage.getInstance().sendStatistcsMessage("alarm_remove", str);
      return;
    }
  }

  public void removeAlarm(AlarmInfo paramAlarmInfo)
  {
    if (paramAlarmInfo == null)
      return;
    int i = isExisted(paramAlarmInfo);
    if (i != -1)
    {
      this.mLstAlarms.remove(i);
      this.needToWriteToDB = true;
    }
    int j = (int)(paramAlarmInfo.alarmTime / 3600L);
    QTMSGManage.getInstance().sendStatistcsMessage("ClockRemove", String.valueOf(j));
  }

  public boolean restoreFromDB()
  {
    if (this.hasRestored);
    List localList;
    do
    {
      return true;
      this.hasRestored = true;
      localList = (List)DataManager.getInstance().getData("getdb_alarm_info", null, null).getResult().getData();
    }
    while ((localList == null) || (localList.size() == 0));
    this.mLstAlarms = localList;
    return true;
  }

  public void updateAlarm(AlarmInfo paramAlarmInfo)
  {
    if (paramAlarmInfo == null)
      return;
    if (!paramAlarmInfo.repeat)
    {
      long l = paramAlarmInfo.getNextShoutTime();
      ((int)Math.pow(2.0D, getDayOfWeek(System.currentTimeMillis() + l * 1000L)));
    }
    int i = isExisted(paramAlarmInfo);
    if (i == -1)
    {
      AlarmInfo localAlarmInfo = new AlarmInfo();
      localAlarmInfo.update(paramAlarmInfo);
      localAlarmInfo.hasShouted = false;
      if ((localAlarmInfo.channelId == 0) || (localAlarmInfo.categoryId == 0))
      {
        localAlarmInfo.categoryId = 54;
        localAlarmInfo.channelId = 386;
        localAlarmInfo.channelName = "CNR中国之声";
      }
      this.mLstAlarms.add(localAlarmInfo);
    }
    while (true)
    {
      this.needToWriteToDB = true;
      if (!paramAlarmInfo.isAvailable)
        break;
      int j = (int)(paramAlarmInfo.alarmTime / 3600L);
      String str = String.valueOf(j);
      QTMSGManage.getInstance().sendStatistcsMessage("ClockSettingNew", str);
      return;
      ((AlarmInfo)this.mLstAlarms.get(i)).update(paramAlarmInfo);
      ((AlarmInfo)this.mLstAlarms.get(i)).hasShouted = false;
    }
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.qtradio.model.AlarmInfoNode
 * JD-Core Version:    0.6.2
 */