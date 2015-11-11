package fm.qingting.qtradio.im;

import android.text.TextUtils;
import fm.qingting.framework.controller.ViewController;
import fm.qingting.qtradio.controller.ControllerManager;
import fm.qingting.qtradio.im.info.GroupInfo;
import fm.qingting.qtradio.im.message.IMMessage;
import fm.qingting.qtradio.room.UserInfo;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class LatestMessages
{
  private static final String AFTERNOON = "下午";
  private static final String DATEFORMAT = "%d月%d日";
  private static final String DAWN = "凌晨";
  private static final String DAYBEFOREYESTERDAY = "前天 ";
  private static final String MORNING = "早上";
  private static final String NIGHT = "晚上";
  private static final String NOON = "中午";
  private static final String TIMEFORMAT = "%s%02d:%02d";
  private static final String YESTERDAY = "昨天 ";
  private static Calendar sCalendar;
  private static int sDay;
  private static String sLatestContactId;
  private static IMMessage sLatestMessage;
  private static long sLatestPublishTime = -1L;
  private static HashMap<String, IMMessage> sMessages = new HashMap();

  public static String getLatestContactId()
  {
    return sLatestContactId;
  }

  public static IMMessage getMessage(String paramString)
  {
    return (IMMessage)sMessages.get(paramString);
  }

  public static int getSize()
  {
    return sMessages.size();
  }

  private static String getTimeInDay(int paramInt1, int paramInt2)
  {
    String str;
    if (paramInt1 < 6)
      str = "凌晨";
    while (true)
    {
      Locale localLocale = Locale.CHINESE;
      Object[] arrayOfObject = new Object[3];
      arrayOfObject[0] = str;
      arrayOfObject[1] = Integer.valueOf(paramInt1);
      arrayOfObject[2] = Integer.valueOf(paramInt2);
      return String.format(localLocale, "%s%02d:%02d", arrayOfObject);
      if (paramInt1 < 12)
        str = "早上";
      else if (paramInt1 < 13)
        str = "中午";
      else if (paramInt1 < 18)
        str = "下午";
      else
        str = "晚上";
    }
  }

  public static String getTimestampBySecond(long paramLong)
  {
    int i = 11;
    sCalendar.setTimeInMillis(1000L * paramLong);
    int j = sCalendar.get(6);
    int k = sCalendar.get(i);
    int m = sCalendar.get(12);
    if (j == sDay)
      return getTimeInDay(k, m);
    if (j == -1 + sDay)
      return "昨天 " + getTimeInDay(k, m);
    if (j == -2 + sDay)
      return "前天 " + getTimeInDay(k, m);
    int n = sCalendar.get(2);
    int i1 = sCalendar.get(5);
    if (n == 12);
    while (true)
    {
      Locale localLocale = Locale.CHINESE;
      Object[] arrayOfObject = new Object[2];
      arrayOfObject[0] = Integer.valueOf(i + 1);
      arrayOfObject[1] = Integer.valueOf(i1);
      return String.format(localLocale, "%d月%d日", arrayOfObject);
      i = n;
    }
  }

  public static int loadUnreadMsgs(boolean paramBoolean)
  {
    List localList1 = IMContacts.getInstance().getRecentGroupContacts();
    List localList2 = IMContacts.getInstance().getRecentUserContacts();
    if (localList1 != null)
      for (int m = 0; m < localList1.size(); m++)
      {
        String str2 = ((GroupInfo)localList1.get(m)).groupId;
        if (!paramBoolean)
        {
          IMMessage localIMMessage2 = IMAgent.getInstance().loadLatestMsg(str2, 1);
          if (localIMMessage2 != null)
            putMessage(str2, localIMMessage2);
        }
      }
    for (int i = 0 + localList1.size(); ; i = 0)
    {
      if (localList2 != null)
        for (int k = 0; k < localList2.size(); k++)
        {
          String str1 = ((UserInfo)localList2.get(k)).userKey;
          if (!paramBoolean)
          {
            IMMessage localIMMessage1 = IMAgent.getInstance().loadLatestMsg(str1, 0);
            if (localIMMessage1 != null)
              putMessage(str1, localIMMessage1);
          }
        }
      for (int j = i + localList2.size(); ; j = i)
      {
        if (!paramBoolean)
          IMAgent.getInstance().loadLatestPeerMsgFromNet();
        return j;
      }
    }
  }

  public static IMMessage pickLatestMessage()
  {
    return sLatestMessage;
  }

  public static void putMessage(String paramString, IMMessage paramIMMessage)
  {
    long l1 = paramIMMessage.publish;
    long l2 = System.currentTimeMillis() / 1000L;
    if (l1 > l2);
    while (true)
    {
      if (l2 > sLatestPublishTime)
      {
        sLatestPublishTime = l2;
        sLatestContactId = paramString;
        sLatestMessage = paramIMMessage;
      }
      for (int i = 1; ; i = 0)
      {
        if (sMessages.containsKey(paramString))
        {
          sMessages.put(paramString, paramIMMessage);
          if (i != 0)
            refreshViewifNeed(paramString);
          return;
        }
        sMessages.put(paramString, paramIMMessage);
        resetDataIfNeed();
        return;
      }
      l2 = l1;
    }
  }

  private static void refreshViewifNeed(String paramString)
  {
    if (!TextUtils.equals(paramString, sLatestContactId));
    for (boolean bool = true; ; bool = false)
    {
      ViewController localViewController = ControllerManager.getInstance().getLastViewController();
      if ((localViewController != null) && (localViewController.controllerName.equalsIgnoreCase("conversations")))
        localViewController.config("resetList", Boolean.valueOf(bool));
      return;
    }
  }

  public static void resetBaseTime()
  {
    if (sCalendar == null)
      sCalendar = Calendar.getInstance();
    sCalendar.setTimeInMillis(System.currentTimeMillis());
    sDay = sCalendar.get(6);
  }

  private static void resetDataIfNeed()
  {
    ViewController localViewController = ControllerManager.getInstance().getLastViewController();
    if ((localViewController != null) && (localViewController.controllerName.equalsIgnoreCase("conversations")))
      localViewController.config("resetData", null);
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.qtradio.im.LatestMessages
 * JD-Core Version:    0.6.2
 */