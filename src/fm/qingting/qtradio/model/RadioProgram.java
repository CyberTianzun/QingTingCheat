package fm.qingting.qtradio.model;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.Locale;
import java.util.TimeZone;

public class RadioProgram extends ProgramSet
{
  public String author = "";
  public ArrayList<Broadcaster> broadcasters = new ArrayList();
  public String dataCenter = null;
  public boolean empty = false;
  public int endTime;
  public int nextStartTime;
  public int onDemandCatid = 0;
  public int onDemandCid = 0;
  public String pic;
  public String resource_id = null;
  public int startTime = 0;
  public int timeRawOffset = TimeZone.getTimeZone("Shanghai").getRawOffset();
  public int updatetime = 0;
  public String wid;
  public String wnick;

  public RadioProgram()
  {
  }

  public RadioProgram(RadioProgram paramRadioProgram)
  {
    this.startTime = paramRadioProgram.startTime;
    this.endTime = paramRadioProgram.endTime;
    this.name = paramRadioProgram.name;
    this.programId = paramRadioProgram.programId;
    this.source = paramRadioProgram.source;
    this.duration = paramRadioProgram.duration;
    this.timeRawOffset = paramRadioProgram.timeRawOffset;
    this.broadcasters = new ArrayList(paramRadioProgram.broadcasters);
    this.wid = paramRadioProgram.wid;
    this.wnick = paramRadioProgram.wnick;
    this.pic = paramRadioProgram.pic;
    this.resource_id = paramRadioProgram.resource_id;
    this.dataCenter = paramRadioProgram.dataCenter;
    this.nextStartTime = paramRadioProgram.nextStartTime;
    this.onDemandCid = paramRadioProgram.onDemandCid;
    this.onDemandCatid = paramRadioProgram.onDemandCatid;
    this.index = paramRadioProgram.index;
  }

  private String buildTimeParam(int paramInt)
  {
    Calendar.getInstance();
    String str1 = getYear(paramInt);
    String str2 = getMonth(paramInt);
    String str3 = getDayofMonth(paramInt);
    String str4 = "" + str1;
    String str5 = str4 + "M";
    String str6 = str5 + str2;
    String str7 = str6 + "D";
    String str8 = str7 + str3;
    String str9 = str8 + "h";
    String str10 = str9 + getHour(paramInt);
    String str11 = str10 + "m";
    String str12 = str11 + getMinute(paramInt);
    String str13 = str12 + "s";
    return str13 + getSecond(paramInt);
  }

  private String getDayofMonth(long paramLong)
  {
    Calendar localCalendar = Calendar.getInstance();
    localCalendar.setTimeInMillis(1000L * paramLong);
    return String.valueOf(localCalendar.get(5));
  }

  private String getHour(long paramLong)
  {
    Calendar localCalendar = Calendar.getInstance();
    localCalendar.setTimeInMillis(1000L * paramLong);
    Object[] arrayOfObject = new Object[1];
    arrayOfObject[0] = Integer.valueOf(localCalendar.get(11));
    return String.format("%02d", arrayOfObject);
  }

  private String getMinute(long paramLong)
  {
    Calendar localCalendar = Calendar.getInstance();
    localCalendar.setTimeInMillis(1000L * paramLong);
    Object[] arrayOfObject = new Object[1];
    arrayOfObject[0] = Integer.valueOf(localCalendar.get(12));
    return String.format("%02d", arrayOfObject);
  }

  private String getMonth(long paramLong)
  {
    Calendar localCalendar = Calendar.getInstance();
    localCalendar.setTimeInMillis(1000L * paramLong);
    return String.valueOf(1 + localCalendar.get(2));
  }

  private String getSecond(long paramLong)
  {
    Calendar localCalendar = Calendar.getInstance();
    localCalendar.setTimeInMillis(1000L * paramLong);
    Object[] arrayOfObject = new Object[1];
    arrayOfObject[0] = Integer.valueOf(localCalendar.get(13));
    return String.format("%02d", arrayOfObject);
  }

  private String getTime(long paramLong)
  {
    Calendar localCalendar = Calendar.getInstance();
    localCalendar.setTimeInMillis(1000L * paramLong);
    Object[] arrayOfObject = new Object[2];
    arrayOfObject[0] = Integer.valueOf(localCalendar.get(11));
    arrayOfObject[1] = Integer.valueOf(localCalendar.get(12));
    return String.format("%02d:%02d", arrayOfObject);
  }

  private String getYear(long paramLong)
  {
    Calendar localCalendar = Calendar.getInstance();
    localCalendar.setTimeInMillis(1000L * paramLong);
    return new SimpleDateFormat("yy", Locale.CHINESE).format(localCalendar.getTime());
  }

  public void addBroadCasterName(Broadcaster paramBroadcaster)
  {
    this.broadcasters.add(paramBroadcaster);
  }

  public boolean equals(Object paramObject)
  {
    boolean bool1 = true;
    boolean bool2;
    if (this == paramObject)
      bool2 = bool1;
    RadioProgram localRadioProgram;
    int i;
    label46: int j;
    label57: 
    do
    {
      boolean bool3;
      do
      {
        do
        {
          return bool2;
          bool2 = false;
        }
        while (paramObject == null);
        bool3 = paramObject instanceof RadioProgram;
        bool2 = false;
      }
      while (!bool3);
      localRadioProgram = (RadioProgram)paramObject;
      if (this.wid != null)
        break;
      i = bool1;
      if (localRadioProgram.wid != null)
        break label320;
      j = bool1;
      bool2 = false;
    }
    while (i != j);
    int k;
    if (this.wnick == null)
    {
      k = bool1;
      label76: if (localRadioProgram.wnick != null)
        break label332;
    }
    while (true)
    {
      bool2 = false;
      if (k != bool1)
        break;
      if (this.wid != null)
      {
        boolean bool8 = this.wid.equalsIgnoreCase(localRadioProgram.wid);
        bool2 = false;
        if (!bool8)
          break;
      }
      if (this.wnick != null)
      {
        boolean bool7 = this.wnick.equalsIgnoreCase(localRadioProgram.wnick);
        bool2 = false;
        if (!bool7)
          break;
      }
      int m = this.startTime;
      int n = localRadioProgram.startTime;
      bool2 = false;
      if (m != n)
        break;
      int i1 = this.endTime;
      int i2 = localRadioProgram.endTime;
      bool2 = false;
      if (i1 != i2)
        break;
      int i3 = this.timeRawOffset;
      int i4 = localRadioProgram.timeRawOffset;
      bool2 = false;
      if (i3 != i4)
        break;
      boolean bool4 = this.empty;
      boolean bool5 = localRadioProgram.empty;
      bool2 = false;
      if (bool4 != bool5)
        break;
      if (this.pic != null)
      {
        boolean bool6 = this.pic.equalsIgnoreCase(localRadioProgram.pic);
        bool2 = false;
        if (!bool6)
          break;
      }
      int i5 = this.nextStartTime;
      int i6 = localRadioProgram.nextStartTime;
      bool2 = false;
      if (i5 != i6)
        break;
      int i7 = this.onDemandCid;
      int i8 = localRadioProgram.onDemandCid;
      bool2 = false;
      if (i7 != i8)
        break;
      return super.equals(paramObject);
      i = 0;
      break label46;
      label320: j = 0;
      break label57;
      k = 0;
      break label76;
      label332: bool1 = false;
    }
  }

  public String getBroadCasterNames()
  {
    if (this.broadcasters == null)
      return "";
    Iterator localIterator = this.broadcasters.iterator();
    StringBuilder localStringBuilder = new StringBuilder();
    while (localIterator.hasNext())
    {
      Broadcaster localBroadcaster = (Broadcaster)localIterator.next();
      if ((localBroadcaster.nick != null) && (!localBroadcaster.nick.equalsIgnoreCase("")))
      {
        if (localStringBuilder.length() > 0)
          localStringBuilder.append(" ");
        localStringBuilder.append(localBroadcaster.nick);
      }
    }
    return localStringBuilder.toString();
  }

  public String getBroadCasterNamesForAt()
  {
    if (this.broadcasters == null)
      return "";
    Iterator localIterator = this.broadcasters.iterator();
    StringBuilder localStringBuilder = new StringBuilder();
    while (localIterator.hasNext())
    {
      Broadcaster localBroadcaster = (Broadcaster)localIterator.next();
      if ((localBroadcaster.isVip) && (localBroadcaster.vname != null) && (!localBroadcaster.vname.equalsIgnoreCase("")))
      {
        if (localStringBuilder.length() > 0)
          localStringBuilder.append(" ");
        localStringBuilder.append("@");
        localStringBuilder.append(localBroadcaster.vname);
      }
    }
    return localStringBuilder.toString();
  }

  public long getDuration()
  {
    return this.endTime - this.startTime;
  }

  public boolean quickEquals(Object paramObject)
  {
    if (this == paramObject);
    RadioProgram localRadioProgram;
    do
    {
      return true;
      if (paramObject == null)
        return false;
      if (!(paramObject instanceof RadioProgram))
        return false;
      localRadioProgram = (RadioProgram)paramObject;
      if (this.startTime != localRadioProgram.startTime)
        return false;
      if (this.endTime != localRadioProgram.endTime)
        return false;
      if (this.nextStartTime != localRadioProgram.nextStartTime)
        return false;
      if (this.onDemandCid != localRadioProgram.onDemandCid)
        return false;
      if ((this.name != null) && (localRadioProgram.name != null) && (!this.name.equalsIgnoreCase(localRadioProgram.name)))
        return false;
      if ((this.source != null) && (localRadioProgram.source != null) && (!this.source.equalsIgnoreCase(localRadioProgram.source)))
        return false;
    }
    while ((this.programId == null) || (localRadioProgram.programId == null) || (this.programId.equalsIgnoreCase(localRadioProgram.programId)));
    return false;
  }

  public String replaySource()
  {
    String str1 = "";
    if ((this.dataCenter != null) && (this.resource_id != null))
    {
      String str2 = str1 + this.dataCenter;
      String str3 = str2 + "/cache?id=";
      String str4 = str3 + this.resource_id;
      String str5 = str4 + "&start=";
      String str6 = str5 + buildTimeParam(this.startTime);
      String str7 = str6 + "&end=";
      str1 = str7 + buildTimeParam(this.endTime);
    }
    return str1;
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.qtradio.model.RadioProgram
 * JD-Core Version:    0.6.2
 */