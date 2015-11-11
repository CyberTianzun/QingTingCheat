package fm.qingting.qtradio.model;

import fm.qingting.qtradio.im.info.GroupInfo;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Pattern;

public class ProgramNodeV5 extends Node
{
  public static final int LIVE_PROGRAM = 1;
  public static final int PAST_PROGRAM = 3;
  public static final int RESERVE_PROGRAM = 2;
  private long absoluteEndTime = -1L;
  private long absoluteStartTime = -1L;
  public boolean available = true;
  public String backgroundPic;
  public int belongCatId = 0;
  public int belongChannelId;
  public String belongChannelLetter;
  public String belongChannelName;
  public int belongProgramId;
  public int broadcastDuration = 0;
  public String broadcastEndTime = "23:59";
  public String broadcastTime = "";
  public int categoryId;
  public int channelType = 0;
  public int dayOfWeek;
  public String desc;
  public int dimensionId;
  public Download downloadInfo;
  private transient int endTime = -1;
  public String from;
  public int listenerCnt;
  private List<BroadcastDetail> lstBroadcastDetail;
  public List<String> lstOriginal;
  public List<String> lstPlayUrl = new ArrayList();
  private int mAvailableUrlIndex = -1;
  public String mDownLoadPath;
  public transient GroupInfo mGroupInfo;
  public String mHttpUrl;
  public List<Integer> mLstBitrate;
  public List<String> mLstBitratesPath;
  public List<BroadcasterNode> mLstBroadcasters = new ArrayList();
  private List<String> mLstSourceUrls;
  public List<Integer> mLstTransBitrate;
  private String mOriginalUrls;
  public String mParentCover;
  public String mParentOutline;
  public String mReplay;
  private transient String mReplayHighUrl;
  private transient String mReplayLowUrl;
  private String mReplayQueryString;
  public int mSequence;
  private int mSetting = -1;
  private String mSharedPageUrls;
  private String mSharedUrls;
  private String mSourceUrls;
  public String mTranscode;
  private transient String mTranscodeHightUrl;
  private transient String mTranscodeLowUrl;
  public transient Map<Integer, Integer> mapLinkInfo;
  private transient String nextEndTime = "23:59";
  private transient String nextStartTime = "";
  public String parentDisplayName;
  public String parentName;
  private transient int playNextDW = 0;
  private transient int playNextEndTime = -1;
  private transient int playNextStartTime = -1;
  public String programId;
  public String programType = "program";
  public long publishTime;
  public String resourceId = "";
  public String speicalProgram = "live";
  public int startDayOfWeek;
  private transient int startTime = -1;
  public String title;
  public String topic;
  public int uniqueId;
  private long updateTime;
  public int virtualChannelId;
  public String weiboId;
  public String weiboNick;
  public int weight;

  public ProgramNodeV5()
  {
    this.nodeName = "program";
  }

  private long absoluteBaseTime()
  {
    long l1 = 60L * (System.currentTimeMillis() / 1000L / 60L);
    int i = getDayOfWeek(l1);
    long l2 = l1 - getRelativeTime(l1 * 1000L);
    if (i != this.dayOfWeek)
    {
      if (i < this.startDayOfWeek)
        i += 7;
      return l2 - 3600 * (24 * (i - this.dayOfWeek));
    }
    return l2;
  }

  private String buildTimeParam(long paramLong)
  {
    String str1 = getYear(paramLong);
    String str2 = getMonth(paramLong);
    String str3 = getDayofMonth(paramLong);
    String str4 = "" + str1;
    String str5 = str4 + "M";
    String str6 = str5 + str2;
    String str7 = str6 + "D";
    String str8 = str7 + str3;
    String str9 = str8 + "h";
    String str10 = str9 + getHour(paramLong);
    String str11 = str10 + "m";
    String str12 = str11 + getMinute(paramLong);
    String str13 = str12 + "s";
    return str13 + "0";
  }

  private long getAbsoluteBroadcastTime(long paramLong)
  {
    return paramLong + absoluteBaseTime();
  }

  private String getBitrateUrlsByMode(int paramInt)
  {
    if (paramInt == InfoManager.AUDIO_QUALITY_MODE.SMART.ordinal())
    {
      if (InfoManager.getInstance().hasMobileNetwork())
        return getFirstBitrateUrl();
      return getLastBitrateUrl();
    }
    if (paramInt == InfoManager.AUDIO_QUALITY_MODE.LOW_QUALITY.ordinal())
      return getFirstBitrateUrl();
    if (paramInt == InfoManager.AUDIO_QUALITY_MODE.HIGH_QUALITY.ordinal())
      return getLastBitrateUrl();
    return getTranscodeUrls(InfoManager.AUDIO_QUALITY_MODE.LOW_QUALITY.ordinal());
  }

  private String getBitrateUrlsByRate(int paramInt)
  {
    if ((this.mLstBitrate != null) && (this.mLstBitratesPath != null))
      for (int i = 0; i < this.mLstBitrate.size(); i++)
        if (((Integer)this.mLstBitrate.get(i)).intValue() == paramInt)
          return getUrlsByPath((String)this.mLstBitratesPath.get(i));
    return null;
  }

  private String getBroadcastEndTimeByDW(int paramInt)
  {
    if ((this.lstBroadcastDetail != null) && (this.lstBroadcastDetail.size() > 0))
      for (int i = 0; i < this.lstBroadcastDetail.size(); i++)
        if (((BroadcastDetail)this.lstBroadcastDetail.get(i)).dayofweek == paramInt)
          return ((BroadcastDetail)this.lstBroadcastDetail.get(i)).endTime;
    return null;
  }

  private List<BroadcasterNode> getBroadcastNodeByDW(int paramInt)
  {
    return null;
  }

  private String getBroadcastTimeByDW(int paramInt)
  {
    if ((this.lstBroadcastDetail != null) && (this.lstBroadcastDetail.size() > 0))
      for (int i = 0; i < this.lstBroadcastDetail.size(); i++)
        if (((BroadcastDetail)this.lstBroadcastDetail.get(i)).dayofweek == paramInt)
          return ((BroadcastDetail)this.lstBroadcastDetail.get(i)).startTime;
    return null;
  }

  private int getDayOfWeek(long paramLong)
  {
    Calendar localCalendar = Calendar.getInstance();
    localCalendar.setTimeInMillis(1000L * paramLong);
    return localCalendar.get(7);
  }

  private String getDayofMonth(long paramLong)
  {
    Calendar localCalendar = Calendar.getInstance();
    localCalendar.setTimeInMillis(1000L * paramLong);
    return String.valueOf(localCalendar.get(5));
  }

  private String getFirstBitrateUrl()
  {
    if ((this.mLstBitratesPath != null) && (this.mLstBitratesPath.size() > 0))
      return getUrlsByPath((String)this.mLstBitratesPath.get(0));
    return getTranscodeUrls(InfoManager.AUDIO_QUALITY_MODE.LOW_QUALITY.ordinal());
  }

  private String getFirstTransBitrate()
  {
    if ((this.mLstTransBitrate != null) && (this.mLstTransBitrate.size() > 0))
      return "&bitrate=" + String.valueOf(this.mLstTransBitrate.get(0));
    return "";
  }

  private String getHour(long paramLong)
  {
    Calendar localCalendar = Calendar.getInstance();
    localCalendar.setTimeInMillis(1000L * paramLong);
    Object[] arrayOfObject = new Object[1];
    arrayOfObject[0] = Integer.valueOf(localCalendar.get(11));
    return String.format("%02d", arrayOfObject);
  }

  private String getLastBitrateUrl()
  {
    if ((this.mLstBitratesPath != null) && (this.mLstBitratesPath.size() > 0))
      return getUrlsByPath((String)this.mLstBitratesPath.get(-1 + this.mLstBitratesPath.size()));
    return getTranscodeUrls(InfoManager.AUDIO_QUALITY_MODE.HIGH_QUALITY.ordinal());
  }

  private String getLastTransBitrate()
  {
    if ((this.mLstTransBitrate != null) && (this.mLstTransBitrate.size() > 0))
      return "&bitrate=" + String.valueOf(this.mLstTransBitrate.get(-1 + this.mLstTransBitrate.size()));
    return "";
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

  private int getNearestDW()
  {
    int i = 0;
    int j = Calendar.getInstance().get(7);
    if (j == 7);
    for (int k = 0; ; k = j)
    {
      int i2;
      int i3;
      int m;
      if ((this.lstBroadcastDetail != null) && (this.lstBroadcastDetail.size() > 0))
      {
        int i1 = ((BroadcastDetail)this.lstBroadcastDetail.get(0)).dayofweek;
        for (i2 = 10; i < this.lstBroadcastDetail.size(); i2 = i3)
        {
          if ((((BroadcastDetail)this.lstBroadcastDetail.get(i)).dayofweek <= k) || (((BroadcastDetail)this.lstBroadcastDetail.get(i)).dayofweek >= i2))
            break label172;
          i3 = ((BroadcastDetail)this.lstBroadcastDetail.get(i)).dayofweek;
          i++;
        }
        m = i1;
      }
      for (int n = i2; ; n = 10)
      {
        if (n == 10)
        {
          if (m < 10)
            return m;
          return -1;
        }
        return n;
        label172: i3 = i2;
        break;
        m = 10;
      }
    }
  }

  private String getOriginalUrls()
  {
    if (!InfoManager.getInstance().enableOriginalSource())
      return "";
    if ((this.mOriginalUrls == null) && (this.lstOriginal != null))
    {
      Object localObject = "";
      int i = 0;
      while (i < this.lstOriginal.size())
      {
        String str1 = (String)localObject + (String)this.lstOriginal.get(i);
        String str2 = str1 + ";;";
        i++;
        localObject = str2;
      }
      this.mOriginalUrls = ((String)localObject);
    }
    return this.mOriginalUrls;
  }

  private int getRelativeSecTime(long paramLong)
  {
    Calendar localCalendar = Calendar.getInstance();
    localCalendar.setTimeInMillis(paramLong);
    int i = localCalendar.get(11);
    int j = localCalendar.get(12);
    return localCalendar.get(13) + (60 * (i * 60) + j * 60);
  }

  private long getRelativeTime(long paramLong)
  {
    Calendar localCalendar = Calendar.getInstance();
    localCalendar.setTimeInMillis(paramLong);
    int i = localCalendar.get(11);
    int j = localCalendar.get(12);
    return 60 * (i * 60) + j * 60;
  }

  private String getReplayUrl(String paramString, long paramLong1, long paramLong2)
  {
    if (paramString == null)
      return null;
    String str1 = "" + this.mReplay;
    String str2 = str1 + "&start=";
    String str3 = str2 + buildTimeParam(paramLong1);
    String str4 = str3 + "&end=";
    String str5 = str4 + buildTimeParam(paramLong2);
    String str6 = str5 + "&deviceid=";
    String str7 = str6 + InfoManager.getInstance().getDeviceId();
    if (InfoManager.getInstance().getMutiRate())
    {
      if (this.mSetting == -1)
        this.mSetting = InfoManager.getInstance().getAudioQualitySetting();
      if (!hasHighQuality(this.mSetting))
        break label260;
    }
    label260: for (str7 = str7 + getLastTransBitrate(); ; str7 = str7 + getFirstTransBitrate())
      return str7 + ";;";
  }

  private String getReplayUrlByDataCenter(String paramString, int paramInt)
  {
    if (paramString == null)
      return null;
    String str1 = "" + this.mReplay;
    String str2 = str1 + "&start=";
    String str3;
    String str4;
    String str5;
    if ((this.speicalProgram.equalsIgnoreCase("specific")) && (this.absoluteStartTime != 0L))
    {
      str3 = str2 + buildTimeParam(this.absoluteStartTime);
      str4 = str3 + "&end=";
      if ((!this.speicalProgram.equalsIgnoreCase("specific")) || (this.absoluteEndTime == 0L))
        break label360;
      str5 = str4 + buildTimeParam(this.absoluteEndTime);
      label174: this.mReplayQueryString = str5;
      String str6 = str5 + "&deviceid=";
      str7 = str6 + InfoManager.getInstance().getDeviceId();
      if (InfoManager.getInstance().getUseIpMedia())
        str7 = str7 + "&useip=1";
      if (InfoManager.getInstance().getMutiRate())
        if (!hasHighQuality(paramInt))
          break label396;
    }
    label396: for (String str7 = str7 + getLastTransBitrate(); ; str7 = str7 + getFirstTransBitrate())
    {
      return str7 + ";;";
      str3 = str2 + buildTimeParam(getAbsoluteBroadcastTime(startTime()));
      break;
      label360: str5 = str4 + buildTimeParam(getAbsoluteBroadcastTime(endTime()));
      break label174;
    }
  }

  private int getTime(String paramString)
  {
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
    return -1;
  }

  private String getTranscodeUrls(int paramInt)
  {
    return null;
  }

  private String getUrlsByPath(String paramString)
  {
    return null;
  }

  private String getYear(long paramLong)
  {
    Calendar localCalendar = Calendar.getInstance();
    localCalendar.setTimeInMillis(1000L * paramLong);
    return new SimpleDateFormat("yy", Locale.CHINESE).format(localCalendar.getTime());
  }

  private boolean hasHighQuality(int paramInt)
  {
    if (!InfoManager.getInstance().getMutiRate());
    do
    {
      return false;
      if (paramInt == InfoManager.AUDIO_QUALITY_MODE.HIGH_QUALITY.ordinal())
        return true;
    }
    while ((paramInt == InfoManager.AUDIO_QUALITY_MODE.LOW_QUALITY.ordinal()) || (!InfoManager.getInstance().hasWifi()));
    return true;
  }

  private String msToTimeFormat(long paramLong)
  {
    Date localDate = new Date(paramLong);
    return new SimpleDateFormat("HH:mm").format(localDate);
  }

  public void autoSetAbsoluteEndTime(long paramLong)
  {
    if (this.speicalProgram.equalsIgnoreCase("specific"))
      this.absoluteEndTime = paramLong;
    if (this.endTime == -1)
      this.endTime = ((int)getRelativeTime(paramLong * 1000L));
    if ((this.broadcastEndTime == null) || (this.broadcastEndTime.equalsIgnoreCase("23:59")))
      this.broadcastEndTime = msToTimeFormat(paramLong * 1000L);
  }

  public void autoSetAbsoluteTime(long paramLong)
  {
    if (this.startTime == -1)
      this.startTime = ((int)getRelativeTime(paramLong * 1000L));
    if ((this.broadcastTime == null) || (this.broadcastTime.equalsIgnoreCase("")))
      this.broadcastTime = msToTimeFormat(paramLong * 1000L);
    int i = getDuration();
    if (i > 0)
      autoSetAbsoluteEndTime(paramLong + i);
  }

  public boolean calcNextPlayTime()
  {
    this.playNextDW = getNearestDW();
    if (this.playNextDW == -1)
      return false;
    String str1 = getBroadcastTimeByDW(this.playNextDW);
    if (str1 != null)
    {
      this.nextStartTime = str1;
      this.playNextStartTime = getTime(this.nextStartTime);
    }
    String str2 = getBroadcastEndTimeByDW(this.playNextDW);
    if (str2 != null)
    {
      this.nextEndTime = str2;
      this.playNextEndTime = getTime(this.nextEndTime);
      if (this.playNextEndTime < this.playNextStartTime)
        this.playNextEndTime = (86400 + this.playNextEndTime);
    }
    return true;
  }

  public void clearDownloadState()
  {
    this.mAvailableUrlIndex = -1;
  }

  public int endTime()
  {
    if (-1 == this.endTime);
    try
    {
      String[] arrayOfString = Pattern.compile("\\D+").split(this.broadcastEndTime);
      if (arrayOfString.length == 2)
      {
        int i = Integer.parseInt(arrayOfString[0]);
        int j = Integer.parseInt(arrayOfString[1]);
        this.endTime = (i * 3600 + j * 60);
      }
      label58: if (this.endTime < startTime())
        this.endTime = (86400 + this.endTime);
      return this.endTime;
    }
    catch (Exception localException)
    {
      break label58;
    }
  }

  public long getAbsoluteEndTime()
  {
    if ((this.speicalProgram.equalsIgnoreCase("specific")) && (this.absoluteEndTime != 0L))
      return this.absoluteEndTime;
    return getAbsoluteBroadcastTime(endTime());
  }

  public long getAbsoluteStartTime()
  {
    if ((this.speicalProgram.equalsIgnoreCase("specific")) && (this.absoluteStartTime != 0L))
      return this.absoluteStartTime;
    int i = startTime();
    if (i == -1)
    {
      this.broadcastTime = "00:00";
      this.broadcastEndTime = "23:59";
      return 0L;
    }
    return getAbsoluteBroadcastTime(i);
  }

  public String getBroadCasterNames()
  {
    if (this.mLstBroadcasters == null)
      return "";
    Iterator localIterator = this.mLstBroadcasters.iterator();
    StringBuilder localStringBuilder = new StringBuilder();
    while (localIterator.hasNext())
    {
      BroadcasterNode localBroadcasterNode = (BroadcasterNode)localIterator.next();
      if (localStringBuilder.length() > 0)
        localStringBuilder.append(" ");
      localStringBuilder.append(localBroadcasterNode.nick);
    }
    return localStringBuilder.toString();
  }

  public String getBroadCasterNamesForAt()
  {
    return null;
  }

  public String getBroadCasterWeiboNames()
  {
    return null;
  }

  public String getCurrDownloadUrl()
  {
    return null;
  }

  public int getCurrPlayStatus()
  {
    int i = 2;
    long l1 = System.currentTimeMillis() / 1000L;
    long l2 = getAbsoluteStartTime();
    long l3 = getAbsoluteEndTime();
    if (this.programType.equalsIgnoreCase("program"))
    {
      if ((l2 <= l1) && (l3 > l1))
        i = 1;
      while ((l2 > l1) || (l3 >= l1))
        return i;
      return 3;
    }
    return 3;
  }

  public String getDownLoadUrlPath()
  {
    String str1 = "" + this.mDownLoadPath;
    String str2;
    if (this.programType.equalsIgnoreCase("program"))
    {
      str2 = str1 + "&start=";
      if ((!this.speicalProgram.equalsIgnoreCase("specific")) || (this.absoluteStartTime == 0L))
        break label177;
    }
    String str4;
    label177: for (String str3 = str2 + buildTimeParam(this.absoluteStartTime); ; str3 = str2 + buildTimeParam(getAbsoluteBroadcastTime(startTime())))
    {
      str4 = str3 + "&end=";
      if ((!this.speicalProgram.equalsIgnoreCase("specific")) || (this.absoluteEndTime == 0L))
        break;
      str1 = str4 + buildTimeParam(this.absoluteEndTime);
      return str1;
    }
    return str4 + buildTimeParam(5L + getAbsoluteBroadcastTime(endTime()));
  }

  public String getDownloadUrlForVirtualProgram()
  {
    return null;
  }

  public int getDuration()
  {
    if (this.broadcastDuration <= 0)
    {
      this.broadcastDuration = (endTime() - startTime());
      if (this.broadcastDuration <= 0)
        this.broadcastDuration = (86400 + this.broadcastDuration);
    }
    return this.broadcastDuration;
  }

  public long getNextAbsolutePlayTime()
  {
    int i = 7;
    int m;
    if ((this.playNextDW > 0) && (this.playNextStartTime > 0))
    {
      int j = this.playNextDW;
      int k = Calendar.getInstance().get(i);
      m = (j + 7 - k) % 7;
      if (m != 0)
        break label70;
    }
    while (true)
    {
      return getAbsoluteBroadcastTime(this.playNextStartTime) + 3600 * (i * 24);
      return -1L;
      label70: i = m;
    }
  }

  public String getNextDownLoadUrl()
  {
    return null;
  }

  public String getReplayQueryString()
  {
    return this.mReplayQueryString;
  }

  public String getReplayUrlByEndTime(long paramLong)
  {
    return null;
  }

  public String getReplayUrls()
  {
    return null;
  }

  public String getReplayUrlsDirectly()
  {
    if (this.mReplayLowUrl != null)
      return this.mReplayLowUrl;
    if (this.mReplayHighUrl != null)
      return this.mReplayHighUrl;
    return null;
  }

  public String getSharedPageUrl()
  {
    return this.mSharedPageUrls;
  }

  public String getSharedSourceUrl()
  {
    if (getCurrPlayStatus() == 3)
    {
      if (this.mSetting == -1)
        this.mSetting = InfoManager.getInstance().getAudioQualitySetting();
      if (this.mSharedUrls == null)
        getTranscodeUrls(this.mSetting);
    }
    return this.mSharedUrls;
  }

  public String getSourceUrls()
  {
    int i = InfoManager.getInstance().getAudioQualitySetting();
    int j = this.mSetting;
    int k = 0;
    if (i != j)
    {
      this.mSetting = i;
      k = 1;
    }
    if ((this.mSourceUrls != null) && (this.programType.equalsIgnoreCase("DownloadProgram")))
      return this.mSourceUrls;
    if ((this.mSourceUrls == null) || (k != 0))
    {
      this.mSourceUrls = getTranscodeUrls(this.mSetting);
      this.mSourceUrls = getBitrateUrlsByMode(i);
      if (this.mSourceUrls == null)
        break label142;
      String str2 = getOriginalUrls();
      if ((str2 != null) && (!str2.equalsIgnoreCase("")))
        this.mSourceUrls += str2;
    }
    while (true)
    {
      return this.mSourceUrls;
      label142: String str1 = getOriginalUrls();
      if (str1 != null)
        this.mSourceUrls = str1;
    }
  }

  public String getSourceUrlsDirectly()
  {
    return this.mSourceUrls;
  }

  public long getUpdateTime()
  {
    return this.updateTime;
  }

  public int getWeight()
  {
    return this.weight;
  }

  public boolean isDownloadProgram()
  {
    return (this.programType != null) && (this.programType.equalsIgnoreCase("DownloadProgram"));
  }

  public void recomputeBroadcastDetail()
  {
    int i = Calendar.getInstance().get(7);
    if (this.channelType == 1)
    {
      this.startDayOfWeek = i;
      this.dayOfWeek = this.startDayOfWeek;
    }
    String str1 = getBroadcastTimeByDW(i);
    if (str1 != null)
    {
      this.broadcastTime = str1;
      this.startTime = getTime(this.broadcastTime);
    }
    String str2 = getBroadcastEndTimeByDW(i);
    if (str2 != null)
    {
      this.broadcastEndTime = str2;
      this.endTime = getTime(this.broadcastEndTime);
      if (this.endTime < this.startTime)
        this.endTime = (86400 + this.endTime);
    }
    this.mLstBroadcasters = getBroadcastNodeByDW(i);
  }

  public void setAbsoluteStartTime(long paramLong)
  {
    if (this.speicalProgram.equalsIgnoreCase("specific"))
      this.absoluteStartTime = paramLong;
    this.startTime = ((int)getRelativeTime(paramLong * 1000L));
    this.broadcastTime = msToTimeFormat(paramLong * 1000L);
  }

  public void setBroadcastDetail(List<BroadcastDetail> paramList)
  {
    this.lstBroadcastDetail = paramList;
    recomputeBroadcastDetail();
  }

  public void setBroadcastEndTimeByDuration(int paramInt)
  {
    if (paramInt < 0)
      return;
    int i = paramInt + startTime();
    int j = i / 3600;
    int k = i - j * 3600;
    if (j == 0);
    for (this.broadcastEndTime = "00"; k == 0; this.broadcastEndTime = String.valueOf(j))
    {
      this.broadcastEndTime += ":";
      this.broadcastEndTime += "00";
      return;
    }
    this.broadcastEndTime += ":";
    this.broadcastEndTime += String.valueOf(k);
  }

  public void setPlayTimeDetail(List<BroadcastDetail> paramList)
  {
    this.lstBroadcastDetail = paramList;
  }

  public void setSharedPageUrl(String paramString)
  {
    if ((paramString == null) || (paramString.equalsIgnoreCase("")))
      return;
    this.mSharedPageUrls = paramString;
  }

  public void setSharedSourceUrl(String paramString)
  {
    if ((paramString == null) || (paramString.equalsIgnoreCase("")))
      return;
    this.mSharedUrls = paramString;
  }

  public void setSourceUrls(String paramString)
  {
    if (paramString == null)
      return;
    this.mSourceUrls = paramString;
  }

  public void setUpdateTime(long paramLong)
  {
    this.updateTime = paramLong;
  }

  public int startTime()
  {
    if (-1 == this.startTime);
    try
    {
      String[] arrayOfString = Pattern.compile("\\D+").split(this.broadcastTime);
      if (arrayOfString.length == 2)
      {
        int i = Integer.parseInt(arrayOfString[0]);
        int j = Integer.parseInt(arrayOfString[1]);
        this.startTime = (i * 3600 + j * 60);
      }
      label58: return this.startTime;
    }
    catch (Exception localException)
    {
      break label58;
    }
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.qtradio.model.ProgramNodeV5
 * JD-Core Version:    0.6.2
 */