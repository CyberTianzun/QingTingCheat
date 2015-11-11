package fm.qingting.qtradio.model;

import java.util.Calendar;
import java.util.TimeZone;

public class SearchBroadcastTime extends BroadcastTime
{
  public int hour;
  public int minuter;
  public String timeText;
  public String weekString;

  public SearchBroadcastTime()
  {
  }

  public SearchBroadcastTime(SearchBroadcastTime paramSearchBroadcastTime)
  {
    this.day = paramSearchBroadcastTime.day;
    this.weekString = paramSearchBroadcastTime.weekString;
    this.timeText = paramSearchBroadcastTime.timeText;
    this.hour = paramSearchBroadcastTime.hour;
    this.minuter = paramSearchBroadcastTime.minuter;
  }

  private void setDay(String paramString)
  {
    if (paramString.equalsIgnoreCase("1"))
    {
      this.weekString = "周日";
      this.day = 1;
    }
    do
    {
      return;
      if (paramString.equalsIgnoreCase("2"))
      {
        this.weekString = "周一";
        this.day = 2;
        return;
      }
      if (paramString.equalsIgnoreCase("3"))
      {
        this.weekString = "周二";
        this.day = 3;
        return;
      }
      if (paramString.equalsIgnoreCase("4"))
      {
        this.weekString = "周三";
        this.day = 4;
        return;
      }
      if (paramString.equalsIgnoreCase("5"))
      {
        this.weekString = "周四";
        this.day = 5;
        return;
      }
      if (paramString.equalsIgnoreCase("6"))
      {
        this.weekString = "周五";
        this.day = 6;
        return;
      }
    }
    while (!paramString.equalsIgnoreCase("7"));
    this.weekString = "周六";
    this.day = 7;
  }

  private void setTimeText(String paramString)
  {
    this.timeText = paramString;
    String[] arrayOfString = paramString.split(":");
    try
    {
      this.hour = Integer.valueOf(arrayOfString[0]).intValue();
      this.minuter = Integer.valueOf(arrayOfString[1]).intValue();
      return;
    }
    catch (NumberFormatException localNumberFormatException)
    {
      localNumberFormatException.printStackTrace();
    }
  }

  public void Clone(SearchBroadcastTime paramSearchBroadcastTime)
  {
    this.day = paramSearchBroadcastTime.day;
    this.weekString = paramSearchBroadcastTime.weekString;
    this.timeText = paramSearchBroadcastTime.timeText;
    this.hour = paramSearchBroadcastTime.hour;
    this.minuter = paramSearchBroadcastTime.minuter;
  }

  public String getText()
  {
    if ((this.weekString != null) && (this.timeText != null))
      return this.weekString + " " + this.timeText;
    return "";
  }

  public boolean isNextProgram()
  {
    Calendar localCalendar = Calendar.getInstance();
    localCalendar.setTimeZone(TimeZone.getTimeZone("GMT+08:00"));
    if ((this.day != -1) && (this.day < localCalendar.get(7)));
    while (((this.hour != -1) && (this.hour < localCalendar.get(11))) || ((this.minuter != -1) && (this.minuter < localCalendar.get(12))))
      return false;
    return true;
  }

  public void setBroadTimeText(String paramString)
  {
    String[] arrayOfString = paramString.split("-");
    if (arrayOfString.length != 2)
    {
      this.day = -1;
      this.hour = -1;
      this.minuter = -1;
      return;
    }
    setDay(arrayOfString[0]);
    setTimeText(arrayOfString[1]);
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.qtradio.model.SearchBroadcastTime
 * JD-Core Version:    0.6.2
 */