package fm.qingting.qtradio.model;

import java.util.Calendar;

public class Clock
{
  public static final int ALL_CYCLE = 127;
  public static final int CLOCK_TYPE_ALARM = 1;
  public static final int CLOCK_TYPE_TIMER = 2;
  public static final int DEFAULT_TIME = 27000;
  public static final int FRIDAY = 16;
  public static final int MONDAY = 1;
  public static final int SATURDAY = 32;
  public static final int SUNDAY = 64;
  public static final int THURSDAY = 8;
  public static final int TUESDAY = 2;
  public static final int WEDNESDAY = 4;
  private static final int[] WEEKLIST = { 64, 1, 2, 4, 8, 16, 32 };
  public static final int WORK_CYCLE = 31;
  public boolean available;
  public int cycle;
  private int endTime = -1;
  private int id = -1;
  private int startTime = -1;
  private int time = 0;
  public int type;

  public Clock()
  {
  }

  public Clock(int paramInt1, int paramInt2)
  {
    this(false, 0, paramInt2, paramInt1);
  }

  public Clock(int paramInt1, int paramInt2, boolean paramBoolean)
  {
    this(paramBoolean, 0, paramInt2, paramInt1);
  }

  public Clock(Clock paramClock)
  {
    if (paramClock == null)
      return;
    this.id = paramClock.id;
    this.available = paramClock.available;
    this.cycle = paramClock.cycle;
    this.time = paramClock.time;
    this.type = paramClock.type;
    resetStartTime();
  }

  public Clock(boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3)
  {
    this.available = paramBoolean;
    this.cycle = paramInt1;
    this.time = paramInt2;
    this.type = paramInt3;
    resetStartTime();
  }

  private void resetStartTime()
  {
    this.startTime = ((int)(Calendar.getInstance().getTimeInMillis() / 1000L));
    this.endTime = -1;
  }

  public boolean checkCycle(int paramInt)
  {
    return (paramInt & this.cycle) == paramInt;
  }

  public boolean getAvailable()
  {
    return this.available;
  }

  public int getCycle()
  {
    return this.cycle;
  }

  public int getHours()
  {
    return this.time / 3600;
  }

  public int getID()
  {
    return this.id;
  }

  public int getMinutes()
  {
    return this.time % 3600 / 60;
  }

  public int getTime()
  {
    return this.time;
  }

  public int getTimeLeft()
  {
    return getTimeLeft((int)(System.currentTimeMillis() / 1000L));
  }

  public int getTimeLeft(int paramInt)
  {
    int m;
    int n;
    int i1;
    if (this.available)
    {
      if ((this.endTime >= 0) && (this.endTime > paramInt))
        return this.endTime - paramInt;
      if (this.type == 2)
      {
        this.endTime = (this.startTime + this.time);
        if (this.endTime >= paramInt)
          return this.endTime - paramInt;
      }
      else if (this.type == 1)
      {
        Calendar localCalendar = Calendar.getInstance();
        localCalendar.setTimeInMillis(1000L * paramInt);
        int i = -1 + localCalendar.get(7);
        int j = localCalendar.get(11);
        int k = localCalendar.get(12);
        m = localCalendar.get(13) + (3600 * (i * 24) + j * 3600 + k * 60);
        n = 0;
        if (n <= 7)
        {
          i1 = i + n;
          if (i1 <= 6)
            break label234;
        }
      }
    }
    label234: for (int i2 = i1 - 7; ; i2 = i1)
    {
      if (checkCycle(WEEKLIST[i2]))
      {
        int i3 = 3600 * (i1 * 24) + this.time;
        if (i3 > m)
        {
          this.endTime = (paramInt + (i3 - m));
          return this.endTime - paramInt;
        }
      }
      n++;
      break;
      return -1;
    }
  }

  public int getTotalMinutes()
  {
    return this.time / 60;
  }

  public int getType()
  {
    return this.type;
  }

  public String getWeekName(int paramInt)
  {
    switch (paramInt)
    {
    case 1:
    default:
      return "周一";
    case 2:
      return "周二";
    case 4:
      return "周三";
    case 8:
      return "周四";
    case 16:
      return "周五";
    case 32:
      return "周六";
    case 64:
    }
    return "周日";
  }

  public void setAvailable(boolean paramBoolean)
  {
    this.available = paramBoolean;
    resetStartTime();
  }

  public void setCycle(int paramInt)
  {
    this.cycle = paramInt;
    resetStartTime();
  }

  public void setCycle(int paramInt, boolean paramBoolean)
  {
    int i = paramInt ^ 0xFFFFFFFF;
    if (paramBoolean);
    for (int j = paramInt | this.cycle; ; j = i & this.cycle)
    {
      this.cycle = j;
      resetStartTime();
      return;
    }
  }

  public void setTime(int paramInt)
  {
    this.time = paramInt;
    resetStartTime();
  }

  public void setType(int paramInt)
  {
    this.type = paramInt;
    resetStartTime();
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.qtradio.model.Clock
 * JD-Core Version:    0.6.2
 */