package org.apache.commons.httpclient.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.Locale;
import java.util.TimeZone;

public class DateUtil
{
  private static final Collection DEFAULT_PATTERNS = Arrays.asList(new String[] { "EEE MMM d HH:mm:ss yyyy", "EEEE, dd-MMM-yy HH:mm:ss zzz", "EEE, dd MMM yyyy HH:mm:ss zzz" });
  private static final Date DEFAULT_TWO_DIGIT_YEAR_START = localCalendar.getTime();
  private static final TimeZone GMT = TimeZone.getTimeZone("GMT");
  public static final String PATTERN_ASCTIME = "EEE MMM d HH:mm:ss yyyy";
  public static final String PATTERN_RFC1036 = "EEEE, dd-MMM-yy HH:mm:ss zzz";
  public static final String PATTERN_RFC1123 = "EEE, dd MMM yyyy HH:mm:ss zzz";

  static
  {
    Calendar localCalendar = Calendar.getInstance();
    localCalendar.set(2000, 0, 1, 0, 0);
  }

  public static String formatDate(Date paramDate)
  {
    return formatDate(paramDate, "EEE, dd MMM yyyy HH:mm:ss zzz");
  }

  public static String formatDate(Date paramDate, String paramString)
  {
    if (paramDate == null)
      throw new IllegalArgumentException("date is null");
    if (paramString == null)
      throw new IllegalArgumentException("pattern is null");
    SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat(paramString, Locale.US);
    localSimpleDateFormat.setTimeZone(GMT);
    return localSimpleDateFormat.format(paramDate);
  }

  public static Date parseDate(String paramString)
    throws DateParseException
  {
    return parseDate(paramString, null, null);
  }

  public static Date parseDate(String paramString, Collection paramCollection)
    throws DateParseException
  {
    return parseDate(paramString, paramCollection, null);
  }

  public static Date parseDate(String paramString, Collection paramCollection, Date paramDate)
    throws DateParseException
  {
    if (paramString == null)
      throw new IllegalArgumentException("dateValue is null");
    if (paramCollection == null)
      paramCollection = DEFAULT_PATTERNS;
    if (paramDate == null)
      paramDate = DEFAULT_TWO_DIGIT_YEAR_START;
    if ((paramString.length() > 1) && (paramString.startsWith("'")) && (paramString.endsWith("'")))
      paramString = paramString.substring(1, -1 + paramString.length());
    SimpleDateFormat localSimpleDateFormat = null;
    Iterator localIterator = paramCollection.iterator();
    while (true)
    {
      if (!localIterator.hasNext())
        throw new DateParseException("Unable to parse the date " + paramString);
      String str = (String)localIterator.next();
      if (localSimpleDateFormat == null)
      {
        localSimpleDateFormat = new SimpleDateFormat(str, Locale.US);
        localSimpleDateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
        localSimpleDateFormat.set2DigitYearStart(paramDate);
      }
      try
      {
        while (true)
        {
          Date localDate = localSimpleDateFormat.parse(paramString);
          return localDate;
          localSimpleDateFormat.applyPattern(str);
        }
      }
      catch (ParseException localParseException)
      {
      }
    }
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     org.apache.commons.httpclient.util.DateUtil
 * JD-Core Version:    0.6.2
 */