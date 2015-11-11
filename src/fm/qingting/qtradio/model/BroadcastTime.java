package fm.qingting.qtradio.model;

import java.util.Calendar;
import java.util.TimeZone;

public class BroadcastTime
{
  public int day;
  public int endtime;
  public int starttime;

  public boolean isCurrentTime()
  {
    Calendar localCalendar = Calendar.getInstance();
    localCalendar.setTimeZone(TimeZone.getTimeZone("GMT+08:00"));
    if (this.day != localCalendar.get(7));
    int i;
    do
    {
      return false;
      i = 3600 * localCalendar.get(11) + 60 * localCalendar.get(12);
    }
    while ((i < this.starttime) || (i >= this.endtime));
    return true;
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.qtradio.model.BroadcastTime
 * JD-Core Version:    0.6.2
 */