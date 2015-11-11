package fm.qingting.qtradio.model;

import fm.qingting.qtradio.helper.ChannelHelper;
import fm.qingting.qtradio.helper.ProgramHelper;
import fm.qingting.utils.TimeUtil;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

public class ProgramNode extends Node
{
  public static final int LIVE_PROGRAM = 1;
  public static final int PAST_PROGRAM = 3;
  public static final int RESERVE_PROGRAM = 2;
  private long _createTime = 0L;
  private long _updateTime = 0L;
  private long absoluteEndTime = -1L;
  private long absoluteStartTime = -1L;
  public boolean available = true;
  private int broadcastEndTime = -1;
  private int broadcastStartTime = -1;
  public int channelId;
  private String channelName;
  public int channelRatingStar = -1;
  public int channelType;
  public String createTime;
  public int dayOfWeek;
  public Download downloadInfo;
  public double duration;
  public String endTime;
  public int groupId = 0;
  public int id;
  public boolean isDownloadProgram = false;
  public List<String> lstAudioPath;
  public List<Integer> lstBitrate;
  public List<BroadcasterNode> lstBroadcaster;
  private int mAvailableUrlIndex = -1;
  private String mHighBitrateSource;
  public boolean mLiveInVirtual = false;
  private String mLowBitrateSource;
  private int mSetting = -1;
  public String mShareSourceUrl;
  public transient Map<Integer, Integer> mapLinkInfo;
  public int resId;
  public int sequence;
  public String startTime;
  public String title;
  public int uniqueId;
  public String updateTime;

  public ProgramNode()
  {
    this.nodeName = "program";
  }

  private long absoluteBaseTime()
  {
    long l1 = 60L * (System.currentTimeMillis() / 1000L / 60L);
    int i = Calendar.getInstance().get(7);
    long l2 = l1 - TimeUtil.absoluteTimeToRelative(l1);
    if (i != this.dayOfWeek)
    {
      if (i == 7)
      {
        if (this.dayOfWeek != 1)
          break label88;
        l2 += 86400L;
      }
    }
    else
      return l2;
    if ((i == 1) && (this.dayOfWeek == 7))
      return l2 - 86400L;
    label88: if (i < this.dayOfWeek)
      return l2 + 3600 * (24 * (this.dayOfWeek - i));
    return l2 - 3600 * (24 * (i - this.dayOfWeek));
  }

  private String buildTimeParam(long paramLong)
  {
    String str1 = TimeUtil.getYear(paramLong);
    String str2 = TimeUtil.getMonth(paramLong);
    String str3 = TimeUtil.getDayofMonth(paramLong);
    String str4 = "" + str1;
    String str5 = str4 + "M";
    String str6 = str5 + str2;
    String str7 = str6 + "D";
    String str8 = str7 + str3;
    String str9 = str8 + "h";
    String str10 = str9 + TimeUtil.getHour(paramLong);
    String str11 = str10 + "m";
    String str12 = str11 + TimeUtil.getMinute(paramLong);
    String str13 = str12 + "s";
    return str13 + "0";
  }

  private long getAbsoluteBroadcastTime(long paramLong)
  {
    return paramLong + absoluteBaseTime();
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

  private void setShareSourceUrl()
  {
    int i = 64;
    if ((this.lstBitrate != null) && (this.lstBitrate.size() > 0))
      i = ((Integer)this.lstBitrate.get(-1 + this.lstBitrate.size())).intValue();
    if (this.channelType == 0)
      if (getCurrPlayStatus() == 1)
        this.mShareSourceUrl = MediaCenter.getInstance().getShareUrl("radiohls", String.valueOf(this.resId), i);
    while (this.channelType != 1)
    {
      return;
      this.mShareSourceUrl = MediaCenter.getInstance().getShareReplayUrl(String.valueOf(this.resId), i, buildTimeParam(getAbsoluteStartTime()), buildTimeParam(getAbsoluteEndTime()));
      return;
    }
    String str = "";
    if ((this.lstAudioPath != null) && (this.lstAudioPath.size() > 0))
      str = (String)this.lstAudioPath.get(-1 + this.lstAudioPath.size());
    this.mShareSourceUrl = MediaCenter.getInstance().getShareUrl("virutalchannel", str, 24);
  }

  public void clearDownloadState()
  {
    this.mAvailableUrlIndex = -1;
  }

  public int endTime()
  {
    if ((this.broadcastEndTime == -1) && (this.endTime != null));
    try
    {
      String[] arrayOfString = Pattern.compile("\\D+").split(this.endTime);
      if (arrayOfString.length >= 2)
      {
        int i = Integer.parseInt(arrayOfString[0]);
        int j = Integer.parseInt(arrayOfString[1]);
        this.broadcastEndTime = (i * 3600 + j * 60);
      }
      label64: if ((this.broadcastEndTime < startTime()) && (this.endTime != null))
        this.broadcastEndTime = (86400 + this.broadcastEndTime);
      return this.broadcastEndTime;
    }
    catch (Exception localException)
    {
      break label64;
    }
  }

  public long getAbsoluteEndTime()
  {
    if (this.absoluteEndTime >= 0L)
      return this.absoluteEndTime;
    if (endTime() == -1)
    {
      this.broadcastEndTime = getDuration();
      this.absoluteEndTime = this.broadcastEndTime;
      if (this.channelType == 1)
        return this.broadcastEndTime;
    }
    return getAbsoluteBroadcastTime(this.broadcastEndTime);
  }

  public long getAbsoluteStartTime()
  {
    if (this.absoluteStartTime >= 0L)
      return this.absoluteStartTime;
    int i = startTime();
    if (i == -1)
    {
      this.startTime = "00:00";
      this.broadcastStartTime = 0;
    }
    for (this.absoluteStartTime = 0L; ; this.absoluteStartTime = getAbsoluteBroadcastTime(i))
      return this.absoluteStartTime;
  }

  public String getBroadCasterNames()
  {
    if (this.lstBroadcaster == null)
      return "";
    Iterator localIterator = this.lstBroadcaster.iterator();
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
    if (this.lstBroadcaster == null)
      return "";
    Iterator localIterator = this.lstBroadcaster.iterator();
    StringBuilder localStringBuilder = new StringBuilder();
    while (localIterator.hasNext())
    {
      BroadcasterNode localBroadcasterNode = (BroadcasterNode)localIterator.next();
      if ((localBroadcasterNode.weiboName != null) && (!localBroadcasterNode.weiboName.equalsIgnoreCase("")) && (localBroadcasterNode.weiboName.equalsIgnoreCase("未知")))
      {
        if (localStringBuilder.length() > 0)
          localStringBuilder.append(" ");
        localStringBuilder.append("@");
        localStringBuilder.append(localBroadcasterNode.weiboName);
      }
      else
      {
        if (localStringBuilder.length() > 0)
          localStringBuilder.append(" ");
        localStringBuilder.append(localBroadcasterNode.nick);
      }
    }
    return localStringBuilder.toString();
  }

  public String getBroadCasterWeiboNames()
  {
    if (this.lstBroadcaster == null)
      return "";
    Iterator localIterator = this.lstBroadcaster.iterator();
    StringBuilder localStringBuilder = new StringBuilder();
    while (localIterator.hasNext())
    {
      BroadcasterNode localBroadcasterNode = (BroadcasterNode)localIterator.next();
      if ((localBroadcasterNode.weiboName != null) && (!localBroadcasterNode.weiboName.equalsIgnoreCase("")))
      {
        if (localStringBuilder.length() > 0)
          localStringBuilder.append(" ");
        localStringBuilder.append("@");
        localStringBuilder.append(localBroadcasterNode.weiboName);
      }
    }
    return localStringBuilder.toString();
  }

  public int getCategoryId()
  {
    if (this.isDownloadProgram)
      return DownLoadInfoNode.mDownloadId;
    ChannelNode localChannelNode = ChannelHelper.getInstance().getChannel(this.channelId, this.channelType);
    if (localChannelNode != null)
      return localChannelNode.categoryId;
    return -1;
  }

  public String getChannelName()
  {
    if ((this.channelName == null) || (this.channelName.equalsIgnoreCase("")))
    {
      ChannelNode localChannelNode = ChannelHelper.getInstance().getChannel(this.channelId, this.channelType);
      if (localChannelNode != null)
        this.channelName = localChannelNode.title;
    }
    return this.channelName;
  }

  public long getCreateTime()
  {
    if (this.createTime == null)
      return 0L;
    if (this._createTime > 0L)
      return this._createTime;
    this._createTime = TimeUtil.dateToMS(this.createTime);
    return this._createTime;
  }

  public int getCurrPlayStatus()
  {
    int i = 3;
    long l1 = System.currentTimeMillis() / 1000L;
    long l2 = getAbsoluteStartTime();
    long l3 = getAbsoluteEndTime();
    if (this.channelType == 0)
    {
      if ((l2 > l1) || (l3 <= l1))
        break label47;
      i = 1;
    }
    label47: 
    do
    {
      return i;
      if (l2 > l1)
        return 2;
    }
    while (l3 >= l1);
    return i;
  }

  public String getDownLoadUrlPath()
  {
    if (this.downloadInfo == null)
      this.downloadInfo = new Download();
    if ((this.downloadInfo != null) && (this.downloadInfo.downloadPath != null) && (!this.downloadInfo.downloadPath.equalsIgnoreCase("")))
      return this.downloadInfo.downloadPath;
    if ((this.channelType == 1) && (this.lstAudioPath != null) && (this.lstAudioPath.size() > 0));
    for (this.downloadInfo.downloadPath = MediaCenter.getInstance().getVirtualProgramDownloadPath("virutalchannel", (String)this.lstAudioPath.get(0), 24); ; this.downloadInfo.downloadPath = MediaCenter.getInstance().getReplayDownloadPath("radiodownload", String.valueOf(this.resId), 24, buildTimeParam(getAbsoluteStartTime()), buildTimeParam(getAbsoluteEndTime())))
      return this.downloadInfo.downloadPath;
  }

  public int getDuration()
  {
    if (this.duration > 0.0D)
      return (int)this.duration;
    this.duration = (endTime() - startTime());
    return (int)this.duration;
  }

  public String getHighBitrateSource()
  {
    if ((this.mHighBitrateSource != null) && (!this.mHighBitrateSource.equalsIgnoreCase("")))
      return this.mHighBitrateSource;
    if ((this.lstBitrate != null) && (this.lstBitrate.size() > 0));
    for (int i = ((Integer)this.lstBitrate.get(-1 + this.lstBitrate.size())).intValue(); ; i = 24)
    {
      if (this.channelType == 0)
        if (getCurrPlayStatus() == 1)
          this.mHighBitrateSource = MediaCenter.getInstance().getPlayUrls("radiohls", String.valueOf(this.resId), i, this.channelId);
      while (true)
      {
        return this.mHighBitrateSource;
        this.mHighBitrateSource = MediaCenter.getInstance().getReplayUrls(String.valueOf(this.resId), i, buildTimeParam(getAbsoluteStartTime()), buildTimeParam(getAbsoluteEndTime()));
        continue;
        if (this.channelType == 1)
        {
          String str = "";
          if ((this.lstAudioPath != null) && (this.lstAudioPath.size() > 0))
            str = (String)this.lstAudioPath.get(-1 + this.lstAudioPath.size());
          this.mHighBitrateSource = MediaCenter.getInstance().getPlayUrls("virutalchannel", str, 24, this.channelId);
        }
      }
    }
  }

  public String getLowBitrateSource()
  {
    if ((this.mLowBitrateSource != null) && (!this.mLowBitrateSource.equalsIgnoreCase("")))
      return this.mLowBitrateSource;
    if ((this.lstBitrate != null) && (this.lstBitrate.size() > 0));
    for (int i = ((Integer)this.lstBitrate.get(0)).intValue(); ; i = 24)
    {
      if (this.channelType == 0)
        if (getCurrPlayStatus() == 1)
          this.mLowBitrateSource = MediaCenter.getInstance().getPlayUrls("radiohls", String.valueOf(this.resId), i, this.channelId);
      while (true)
      {
        return this.mLowBitrateSource;
        this.mLowBitrateSource = MediaCenter.getInstance().getReplayUrls(String.valueOf(this.resId), i, buildTimeParam(getAbsoluteStartTime()), buildTimeParam(getAbsoluteEndTime()));
        continue;
        if (this.channelType == 1)
        {
          String str = "";
          if ((this.lstAudioPath != null) && (this.lstAudioPath.size() > 0))
            str = (String)this.lstAudioPath.get(0);
          this.mLowBitrateSource = MediaCenter.getInstance().getPlayUrls("virutalchannel", str, 24, this.channelId);
        }
      }
    }
  }

  public String getNextDownLoadUrl()
  {
    if (this.downloadInfo == null)
      return null;
    int i = this.downloadInfo.type;
    List localList;
    if (i == 0)
      localList = MediaCenter.getInstance().getPingInfo("radiodownload");
    while (true)
    {
      this.mAvailableUrlIndex = (1 + this.mAvailableUrlIndex);
      if (localList == null)
        break;
      int j = this.mAvailableUrlIndex;
      if (j >= localList.size())
        break;
      String str = "http://" + ((PingInfoV6)localList.get(j)).getDomainIP();
      return str + this.downloadInfo.downloadPath;
      localList = null;
      if (i == 1)
        localList = MediaCenter.getInstance().getPingInfo("virutalchannel");
    }
    return "";
  }

  public ProgramNode getNextSibling()
  {
    if (this.nextSibling != null)
      return (ProgramNode)this.nextSibling;
    if (this.isDownloadProgram)
    {
      ChannelNode localChannelNode = InfoManager.getInstance().root().getCurrentPlayingChannelNode();
      if (localChannelNode != null)
      {
        ProgramNode localProgramNode2 = localChannelNode.getProgramNodeByProgramId(this.id);
        if (localProgramNode2 != null)
          return (ProgramNode)localProgramNode2.nextSibling;
      }
    }
    else
    {
      ProgramScheduleList localProgramScheduleList = ProgramHelper.getInstance().getProgramSchedule(this.channelId, this.channelType, true);
      if (localProgramScheduleList != null)
      {
        ProgramNode localProgramNode1 = localProgramScheduleList.getProgramNode(this.id);
        if (localProgramNode1 != null)
          return (ProgramNode)localProgramNode1.nextSibling;
      }
    }
    return null;
  }

  public ProgramNode getPrevSibling()
  {
    if (this.prevSibling != null)
      return (ProgramNode)this.prevSibling;
    if (this.isDownloadProgram)
    {
      ChannelNode localChannelNode = InfoManager.getInstance().root().getCurrentPlayingChannelNode();
      if (localChannelNode != null)
      {
        ProgramNode localProgramNode2 = localChannelNode.getProgramNodeByProgramId(this.id);
        if (localProgramNode2 != null)
          return (ProgramNode)localProgramNode2.prevSibling;
      }
    }
    else
    {
      ProgramScheduleList localProgramScheduleList = ProgramHelper.getInstance().getProgramSchedule(this.channelId, this.channelType, true);
      if (localProgramScheduleList != null)
      {
        ProgramNode localProgramNode1 = localProgramScheduleList.getProgramNode(this.id);
        if (localProgramNode1 != null)
          return (ProgramNode)localProgramNode1.prevSibling;
      }
    }
    return null;
  }

  public String getSharedSourcePath()
  {
    String str;
    if ((this.mShareSourceUrl == null) || (this.mShareSourceUrl.equalsIgnoreCase("")))
      str = null;
    label110: 
    while (true)
    {
      return str;
      int i = 0;
      int j = 0;
      str = "";
      while (true)
      {
        if (j >= this.mShareSourceUrl.length())
          break label110;
        if ((this.mShareSourceUrl.charAt(j) == '/') && (i < 3))
          i++;
        if (this.mShareSourceUrl.charAt(j) == '?')
          break;
        if (i == 3)
          str = str + this.mShareSourceUrl.charAt(j);
        j++;
      }
    }
  }

  public String getSharedSourceUrl()
  {
    if ((this.mShareSourceUrl == null) || (this.mShareSourceUrl.equalsIgnoreCase("")))
      setShareSourceUrl();
    return this.mShareSourceUrl;
  }

  public String getSourceUrl()
  {
    if (this.mSetting == -1)
      this.mSetting = InfoManager.getInstance().getAudioQualitySetting();
    if (this.mSetting == InfoManager.AUDIO_QUALITY_MODE.HIGH_QUALITY.ordinal())
      return getHighBitrateSource();
    return getLowBitrateSource();
  }

  public long getUpdateTime()
  {
    if (this.updateTime == null)
      return 0L;
    if (this._updateTime > 0L)
      return this._updateTime;
    this._updateTime = TimeUtil.dateToMS(this.updateTime);
    return this._updateTime;
  }

  public boolean isDownloadProgram()
  {
    return this.isDownloadProgram;
  }

  public boolean isLiveProgram()
  {
    return this.channelType == 0;
  }

  public boolean isMusicChannel()
  {
    return (this.channelType == 1) && (getCategoryId() == 523);
  }

  public boolean isNovelProgram()
  {
    return false;
  }

  public void setAbsoluteEndTime(long paramLong)
  {
    this.absoluteEndTime = paramLong;
  }

  public void setAbsoluteStartTime(long paramLong)
  {
    this.absoluteStartTime = paramLong;
  }

  public void setChannelName(String paramString)
  {
    this.channelName = paramString;
  }

  public void setCreateTime(long paramLong)
  {
    this._createTime = paramLong;
  }

  public void setSharedSourceUrl(String paramString)
  {
    this.mShareSourceUrl = paramString;
  }

  public void setSourceUrls(String paramString)
  {
    if ((this.mLowBitrateSource != null) && (!this.mLowBitrateSource.equalsIgnoreCase("")))
    {
      this.mLowBitrateSource += ";;";
      this.mLowBitrateSource += paramString;
    }
    for (this.mLowBitrateSource += ";;"; (this.mHighBitrateSource != null) && (!this.mHighBitrateSource.equalsIgnoreCase("")); this.mLowBitrateSource = paramString)
    {
      this.mHighBitrateSource += ";;";
      this.mHighBitrateSource += paramString;
      this.mHighBitrateSource += ";;";
      return;
    }
    this.mHighBitrateSource = paramString;
  }

  public void setUpdateTime(long paramLong)
  {
    this._updateTime = paramLong;
  }

  public int startTime()
  {
    if ((-1 == this.broadcastStartTime) && (this.startTime != null));
    try
    {
      String[] arrayOfString = Pattern.compile("\\D+").split(this.startTime);
      if (arrayOfString.length >= 2)
      {
        int i = Integer.parseInt(arrayOfString[0]);
        int j = Integer.parseInt(arrayOfString[1]);
        this.broadcastStartTime = (i * 3600 + j * 60);
      }
      label64: return this.broadcastStartTime;
    }
    catch (Exception localException)
    {
      break label64;
    }
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.qtradio.model.ProgramNode
 * JD-Core Version:    0.6.2
 */