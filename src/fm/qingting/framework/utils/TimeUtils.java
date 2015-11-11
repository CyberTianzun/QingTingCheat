package fm.qingting.framework.utils;

import java.text.DateFormat;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TimeUtils
{
  public static String dateToString(Date paramDate)
  {
    return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(paramDate);
  }

  public static String dateToString(Date paramDate, int paramInt)
  {
    return new SimpleDateFormat("yyyy-MM-dd KK:mm:ss a").format(paramDate);
  }

  public static String dateToStringI(long paramLong, DateFormat paramDateFormat)
  {
    Calendar localCalendar = Calendar.getInstance();
    localCalendar.setTimeInMillis(paramLong);
    return paramDateFormat.format(localCalendar.getTime());
  }

  public static String getDay()
  {
    return new SimpleDateFormat("d").format(new Date());
  }

  public static String getHour()
  {
    return new SimpleDateFormat("H").format(new Date());
  }

  public static String getMonth()
  {
    return new SimpleDateFormat("M").format(new Date());
  }

  public static String getTimeLeft(long paramLong)
  {
    int i = (int)((Calendar.getInstance().getTimeInMillis() - paramLong) / 1000L);
    if (i < 0)
      return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(paramLong));
    if (i < 60)
    {
      Object[] arrayOfObject6 = new Object[1];
      arrayOfObject6[0] = Integer.valueOf(i);
      return String.format("%d 秒前", arrayOfObject6);
    }
    int j = 60 * 60;
    if (i < j)
    {
      int i5 = i / 60;
      Object[] arrayOfObject5 = new Object[1];
      arrayOfObject5[0] = Integer.valueOf(i5);
      return String.format("%d 分钟前", arrayOfObject5);
    }
    int k = j * 24;
    if (i < k)
    {
      int i4 = i / 3600;
      Object[] arrayOfObject4 = new Object[1];
      arrayOfObject4[0] = Integer.valueOf(i4);
      return String.format("%d 小时前", arrayOfObject4);
    }
    int m = k * 7;
    if (i < m)
    {
      int i3 = i / 86400;
      Object[] arrayOfObject3 = new Object[1];
      arrayOfObject3[0] = Integer.valueOf(i3);
      return String.format("%d 天前", arrayOfObject3);
    }
    int n = m * 4;
    if (i < n)
    {
      int i2 = i / 604800;
      Object[] arrayOfObject2 = new Object[1];
      arrayOfObject2[0] = Integer.valueOf(i2);
      return String.format("%d 周前", arrayOfObject2);
    }
    if (i < n * 13)
    {
      int i1 = i / 2419200;
      Object[] arrayOfObject1 = new Object[1];
      arrayOfObject1[0] = Integer.valueOf(i1);
      return String.format("%d 个月之前", arrayOfObject1);
    }
    return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(paramLong));
  }

  public static String getWeek()
  {
    return new SimpleDateFormat("E").format(new Date());
  }

  public static String getYYYY_MM_DD()
  {
    return dateToString(new Date()).substring(0, 10);
  }

  public static String getYYYY_MM_DD(String paramString)
  {
    return paramString.substring(0, 10);
  }

  public static String getYear()
  {
    return new SimpleDateFormat("yyyy").format(new Date());
  }

  public static String now()
  {
    return dateToString(new Date());
  }

  public static String now(int paramInt)
  {
    return dateToString(new Date(), paramInt);
  }

  public static Date stringToDate(String paramString)
  {
    int i = paramString.indexOf("AD");
    String str = paramString.trim();
    SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat("yyyy.MM.dd G 'at' hh:mm:ss z");
    if (i > -1)
    {
      str = str.substring(0, i) + "公元" + str.substring(i + "AD".length());
      localSimpleDateFormat = new SimpleDateFormat("yyyy.MM.dd G 'at' hh:mm:ss z");
    }
    if ((str.indexOf("-") > -1) && (str.indexOf(" ") < 0))
      localSimpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmssZ");
    while (true)
    {
      return localSimpleDateFormat.parse(str, new ParsePosition(0));
      if ((str.indexOf("/") > -1) && (str.indexOf(" ") > -1))
        localSimpleDateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
      else if ((str.indexOf("-") > -1) && (str.indexOf(" ") > -1))
        localSimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
      else if (((str.indexOf("/") > -1) && (str.indexOf("am") > -1)) || (str.indexOf("pm") > -1))
        localSimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd KK:mm:ss a");
      else if (((str.indexOf("-") > -1) && (str.indexOf("am") > -1)) || (str.indexOf("pm") > -1))
        localSimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd KK:mm:ss a");
    }
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.framework.utils.TimeUtils
 * JD-Core Version:    0.6.2
 */