package fm.qingting.utils;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class DateUtil
{
  public static long dateTransformBetweenTimeZone(Date paramDate, TimeZone paramTimeZone1, TimeZone paramTimeZone2)
  {
    return Long.valueOf(paramDate.getTime() - paramTimeZone1.getRawOffset() + paramTimeZone2.getRawOffset()).longValue() / 1000L;
  }

  public static int getClockTime()
  {
    Calendar localCalendar = Calendar.getInstance();
    return 0 + 60 * (60 * localCalendar.get(11)) + 60 * localCalendar.get(12) + localCalendar.get(13);
  }

  public static long getCurrentMillis()
  {
    return 28800000L + System.currentTimeMillis();
  }

  public static long getCurrentSeconds()
  {
    return dateTransformBetweenTimeZone(new Date(), TimeZone.getDefault(), TimeZone.getTimeZone("GMT+8:00"));
  }

  public static Calendar getGTM8CalendarFromUtcms(long paramLong)
  {
    Calendar localCalendar = Calendar.getInstance();
    localCalendar.setTimeZone(TimeZone.getTimeZone("GMT+00:00"));
    localCalendar.setTimeInMillis(1000L * (28800L + paramLong));
    return localCalendar;
  }

  public static int getGTM8CurrentHour()
  {
    Calendar localCalendar = Calendar.getInstance();
    localCalendar.setTimeZone(TimeZone.getTimeZone("GMT+00:00"));
    localCalendar.setTimeInMillis(28800000L + System.currentTimeMillis());
    return localCalendar.get(11);
  }

  public static String getMonthDay(long paramLong)
  {
    Calendar localCalendar = Calendar.getInstance();
    localCalendar.setTimeInMillis(1000L * paramLong);
    return 1 + localCalendar.get(2) + "月" + localCalendar.get(5) + "日";
  }

  public static boolean isDifferentDayMs(long paramLong1, long paramLong2)
  {
    int i = 1;
    Calendar localCalendar = Calendar.getInstance();
    localCalendar.setTimeZone(TimeZone.getTimeZone("GMT+00:00"));
    localCalendar.setTimeInMillis(paramLong1);
    int j = localCalendar.get(i);
    int k = localCalendar.get(2);
    int m = localCalendar.get(5);
    localCalendar.get(11);
    localCalendar.setTimeInMillis(paramLong2);
    int n = localCalendar.get(i);
    int i1 = localCalendar.get(2);
    int i2 = localCalendar.get(5);
    localCalendar.get(11);
    if ((j == n) && (k == i1) && (m == i2))
      i = 0;
    return i;
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.utils.DateUtil
 * JD-Core Version:    0.6.2
 */