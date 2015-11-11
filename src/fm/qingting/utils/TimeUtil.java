package fm.qingting.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.regex.Pattern;

public class TimeUtil
{
  private static final String JUST = "刚刚更新";
  private static final String MODEL_HOUR = "%d小时前";
  private static final String MODEL_MINUTE = "%d分钟前";
  private static final long ONEHOUR = 3600000L;
  private static final long TENMINUTE = 600000L;
  private static final long THREEHOUR = 10800000L;
  private static final String TODAY = "今天";

  public static int absoluteTimeToRelative(long paramLong)
  {
    long l = 1000L * paramLong;
    Calendar localCalendar = Calendar.getInstance();
    localCalendar.clear();
    localCalendar.setTimeInMillis(l);
    int i = localCalendar.get(11);
    int j = localCalendar.get(12);
    return i * 3600 + j * 60;
  }

  public static long dateToMS(String paramString)
  {
    if (paramString != null)
      try
      {
        if (paramString.length() < 2)
          return 0L;
        long l = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US).parse(paramString).getTime();
        return l;
      }
      catch (Exception localException)
      {
      }
    return 0L;
  }

  public static long dateToMs(String paramString1, String paramString2)
  {
    try
    {
      long l = new SimpleDateFormat(paramString2, Locale.US).parse(paramString1).getTime();
      return l;
    }
    catch (Exception localException)
    {
    }
    return 0L;
  }

  public static String getDayofMonth(long paramLong)
  {
    Calendar localCalendar = Calendar.getInstance();
    localCalendar.setTimeInMillis(1000L * paramLong);
    return String.valueOf(localCalendar.get(5));
  }

  public static int getDayofWeek(long paramLong)
  {
    Calendar localCalendar = Calendar.getInstance();
    localCalendar.setTimeInMillis(1000L * paramLong);
    return localCalendar.get(7);
  }

  public static int getDayofYear(long paramLong)
  {
    Calendar localCalendar = Calendar.getInstance();
    localCalendar.setTimeInMillis(1000L * paramLong);
    return localCalendar.get(6);
  }

  public static String getHour(long paramLong)
  {
    Calendar localCalendar = Calendar.getInstance();
    localCalendar.setTimeInMillis(1000L * paramLong);
    Object[] arrayOfObject = new Object[1];
    arrayOfObject[0] = Integer.valueOf(localCalendar.get(11));
    return String.format("%02d", arrayOfObject);
  }

  public static int getHourOfDay(long paramLong)
  {
    Calendar localCalendar = Calendar.getInstance();
    localCalendar.setTimeInMillis(1000L * paramLong);
    return localCalendar.get(11);
  }

  public static String getMinute(long paramLong)
  {
    Calendar localCalendar = Calendar.getInstance();
    localCalendar.setTimeInMillis(1000L * paramLong);
    Object[] arrayOfObject = new Object[1];
    arrayOfObject[0] = Integer.valueOf(localCalendar.get(12));
    return String.format("%02d", arrayOfObject);
  }

  public static String getMonth(long paramLong)
  {
    Calendar localCalendar = Calendar.getInstance();
    localCalendar.setTimeInMillis(1000L * paramLong);
    return String.valueOf(1 + localCalendar.get(2));
  }

  public static String getReadableTime(long paramLong)
  {
    if (paramLong == 0L)
      return "刚刚更新";
    long l1 = System.currentTimeMillis();
    Calendar localCalendar = Calendar.getInstance();
    int i = localCalendar.get(1);
    int j = localCalendar.get(6);
    localCalendar.setTimeInMillis(paramLong);
    int k = localCalendar.get(1);
    int m = localCalendar.get(6);
    if ((i == k) && (j == m));
    for (int n = 1; ; n = 0)
    {
      if (n != 0)
      {
        long l2 = l1 - paramLong;
        if (l2 < 600000L)
          return "刚刚更新";
        if (l2 < 3600000L)
        {
          Locale localLocale2 = Locale.CHINESE;
          Object[] arrayOfObject2 = new Object[1];
          arrayOfObject2[0] = Integer.valueOf((int)(l2 / 1000L / 60L));
          return String.format(localLocale2, "%d分钟前", arrayOfObject2);
        }
        if (l2 < 10800000L)
        {
          Locale localLocale1 = Locale.CHINESE;
          Object[] arrayOfObject1 = new Object[1];
          arrayOfObject1[0] = Integer.valueOf((int)(l2 / 1000L / 60L / 60L));
          return String.format(localLocale1, "%d小时前", arrayOfObject1);
        }
        return "今天";
      }
      return msToDate5(paramLong);
    }
  }

  public static String getYear(long paramLong)
  {
    Calendar localCalendar = Calendar.getInstance();
    localCalendar.setTimeInMillis(1000L * paramLong);
    return new SimpleDateFormat("yy", Locale.CHINESE).format(localCalendar.getTime());
  }

  public static boolean isSundayOrSaturday()
  {
    int i = Calendar.getInstance().get(7);
    return (i == 7) || (i == 1);
  }

  public static String mSecToTime1(long paramLong)
  {
    int i = (int)Math.ceil((float)paramLong / 1000.0F);
    int j = i / 3600;
    int k = i / 60 % 60;
    int m = i % 60;
    if (j == 0)
    {
      if (m == 0)
      {
        Locale localLocale4 = Locale.CHINA;
        Object[] arrayOfObject4 = new Object[1];
        arrayOfObject4[0] = Integer.valueOf(k);
        return String.format(localLocale4, "%d分", arrayOfObject4);
      }
      if (k == 0)
      {
        Locale localLocale3 = Locale.CHINA;
        Object[] arrayOfObject3 = new Object[1];
        arrayOfObject3[0] = Integer.valueOf(m);
        return String.format(localLocale3, "%d秒", arrayOfObject3);
      }
      Locale localLocale2 = Locale.CHINA;
      Object[] arrayOfObject2 = new Object[2];
      arrayOfObject2[0] = Integer.valueOf(k);
      arrayOfObject2[1] = Integer.valueOf(m);
      return String.format(localLocale2, "%d分%d秒", arrayOfObject2);
    }
    Locale localLocale1 = Locale.CHINA;
    Object[] arrayOfObject1 = new Object[2];
    arrayOfObject1[0] = Integer.valueOf(j);
    arrayOfObject1[1] = Integer.valueOf(k);
    return String.format(localLocale1, "%d小时%d分", arrayOfObject1);
  }

  public static String mSecToTime2(long paramLong)
  {
    int i = (int)Math.ceil((float)paramLong / 1000.0F);
    int j = i / 3600;
    int k = i / 60 % 60;
    int m = i % 60;
    if (j == 0)
    {
      Locale localLocale2 = Locale.CHINA;
      Object[] arrayOfObject2 = new Object[2];
      arrayOfObject2[0] = Integer.valueOf(k);
      arrayOfObject2[1] = Integer.valueOf(m);
      return String.format(localLocale2, "%02d:%02d", arrayOfObject2);
    }
    Locale localLocale1 = Locale.CHINA;
    Object[] arrayOfObject1 = new Object[3];
    arrayOfObject1[0] = Integer.valueOf(j);
    arrayOfObject1[1] = Integer.valueOf(k);
    arrayOfObject1[2] = Integer.valueOf(m);
    return String.format(localLocale1, "%02d:%02d:%02d", arrayOfObject1);
  }

  public static String msToDate(long paramLong)
  {
    java.sql.Date localDate = new java.sql.Date(paramLong);
    return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US).format(localDate);
  }

  public static String msToDate10(long paramLong)
  {
    java.sql.Date localDate = new java.sql.Date(paramLong);
    return new SimpleDateFormat("yyMMdd_HHmmss", Locale.US).format(localDate);
  }

  public static String msToDate2(long paramLong)
  {
    java.sql.Date localDate = new java.sql.Date(paramLong);
    return new SimpleDateFormat("yyyy-MM-dd", Locale.US).format(localDate);
  }

  public static String msToDate3(long paramLong)
  {
    java.sql.Date localDate = new java.sql.Date(paramLong);
    return new SimpleDateFormat("HH:mm", Locale.US).format(localDate);
  }

  public static String msToDate4(long paramLong)
  {
    java.sql.Date localDate = new java.sql.Date(paramLong);
    return new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.US).format(localDate);
  }

  public static String msToDate5(long paramLong)
  {
    java.sql.Date localDate = new java.sql.Date(paramLong);
    return new SimpleDateFormat("MM月dd日", Locale.CHINA).format(localDate);
  }

  public static String msToDate6(long paramLong)
  {
    java.sql.Date localDate = new java.sql.Date(paramLong);
    return new SimpleDateFormat("yyyyMMddHHmmss", Locale.US).format(localDate);
  }

  public static String msToDate7(long paramLong)
  {
    java.sql.Date localDate = new java.sql.Date(paramLong);
    return new SimpleDateFormat("yyyyMMdd", Locale.US).format(localDate);
  }

  public static String msToDate8(long paramLong)
  {
    java.sql.Date localDate = new java.sql.Date(paramLong);
    return new SimpleDateFormat("HHmmss", Locale.US).format(localDate);
  }

  public static String msToDate9(long paramLong)
  {
    java.sql.Date localDate = new java.sql.Date(paramLong);
    return new SimpleDateFormat("yyyy.MM.dd", Locale.US).format(localDate);
  }

  public static int relativeTimeToSec(String paramString)
  {
    if (paramString == null);
    while (true)
    {
      return 0;
      try
      {
        String[] arrayOfString = Pattern.compile("\\D+").split(paramString);
        if (arrayOfString.length == 2)
        {
          int i = Integer.parseInt(arrayOfString[0]);
          int j = Integer.parseInt(arrayOfString[1]);
          return i * 3600 + j * 60;
        }
      }
      catch (Exception localException)
      {
      }
    }
    return 0;
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.utils.TimeUtil
 * JD-Core Version:    0.6.2
 */