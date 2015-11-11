package fm.qingting.qtradio.model;

import android.text.TextUtils;
import fm.qingting.qtradio.helper.ProgramHelper;
import fm.qingting.qtradio.room.UserInfo;
import fm.qingting.utils.ScreenType;
import fm.qingting.utils.TimeUtil;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class ChannelNode extends Node
{
  public static final int LIVE_CHANNEL = 0;
  public static final int VIRTUAL_CHANNEL = 1;
  public int audienceCnt;
  public boolean autoPlay;
  public int categoryId;
  public int channelId;
  public int channelType;
  public String desc;
  public float freq;
  public int groupId;
  private String largeThumb;
  public String latest_program;
  public List<BroadcasterNode> lstAuthors;
  public List<BroadcasterNode> lstBroadcaster;
  public transient List<UserInfo> lstPodcasters;
  public transient int mLoadedProgramId;
  public transient int mLoadedProgramSize = 0;
  public transient List<Integer> mLoadingSize;
  public transient List<Integer> mLoadingStart;
  private transient ProgramScheduleList mProgramScheduleList;
  public String mSourceUrl;
  private transient long mUpdateTime = 0L;
  public long mViewTime = 0L;
  public transient Map<Integer, Integer> mapLinkInfo;
  private String mediumThumb;
  public int programCnt;
  public int ratingStar = -1;
  public boolean recordEnable;
  public int resId;
  public String thumb;
  public String title;
  public String update_time;
  public transient long viewTime = 0L;

  public ChannelNode()
  {
    this.nodeName = "channel";
  }

  private int getProperProgramNodeBySequence(int paramInt, List<ProgramNode> paramList)
  {
    if ((paramList == null) || (paramInt < 0))
    {
      i = -1;
      return i;
    }
    for (int i = 0; ; i++)
    {
      if (i >= paramList.size())
        break label47;
      if (paramInt < ((ProgramNode)paramList.get(i)).sequence)
        break;
    }
    label47: return -1;
  }

  private static boolean isEmpty(String paramString)
  {
    return TextUtils.isEmpty(paramString);
  }

  public boolean addDownloadProgramNode(ProgramNode paramProgramNode)
  {
    if (paramProgramNode == null);
    while (!isDownloadChannel())
      return false;
    if (this.mProgramScheduleList == null)
      this.mProgramScheduleList = new ProgramScheduleList(1);
    return this.mProgramScheduleList.addProgramNode(paramProgramNode);
  }

  public void addLoadingSize(int paramInt)
  {
    if (this.mLoadingSize == null)
      this.mLoadingSize = new ArrayList();
    this.mLoadingSize.add(Integer.valueOf(paramInt));
  }

  public void addLoadingStart(int paramInt)
  {
    if (this.mLoadingStart == null)
      this.mLoadingStart = new ArrayList();
    this.mLoadingStart.add(Integer.valueOf(paramInt));
  }

  public void addProgramNode(ProgramNode paramProgramNode, boolean paramBoolean)
  {
    if (paramProgramNode == null);
    do
    {
      do
        return;
      while (isDownloadChannel());
      if (this.mProgramScheduleList == null)
      {
        this.mProgramScheduleList = ProgramHelper.getInstance().getProgramSchedule(this.channelId, this.channelType, true);
        if (this.mProgramScheduleList == null)
          this.mProgramScheduleList = new ProgramScheduleList(1);
      }
      this.mProgramScheduleList.addProgramNode(paramProgramNode);
    }
    while (!paramBoolean);
    ProgramHelper.getInstance().udpateToDB(this.mProgramScheduleList);
  }

  public ChannelNode clone()
  {
    ChannelNode localChannelNode = new ChannelNode();
    localChannelNode.channelId = this.channelId;
    localChannelNode.categoryId = this.categoryId;
    localChannelNode.title = this.title;
    localChannelNode.desc = this.desc;
    localChannelNode.groupId = this.groupId;
    localChannelNode.thumb = this.thumb;
    localChannelNode.mediumThumb = this.mediumThumb;
    localChannelNode.largeThumb = this.largeThumb;
    localChannelNode.autoPlay = this.autoPlay;
    localChannelNode.recordEnable = this.recordEnable;
    localChannelNode.channelType = this.channelType;
    localChannelNode.resId = this.resId;
    localChannelNode.audienceCnt = this.audienceCnt;
    localChannelNode.mapLinkInfo = this.mapLinkInfo;
    localChannelNode.lstBroadcaster = this.lstBroadcaster;
    localChannelNode.lstAuthors = this.lstAuthors;
    localChannelNode.lstPodcasters = this.lstPodcasters;
    localChannelNode.mProgramScheduleList = this.mProgramScheduleList;
    localChannelNode.mLoadingSize = this.mLoadingSize;
    localChannelNode.mLoadingStart = this.mLoadingStart;
    localChannelNode.mSourceUrl = this.mSourceUrl;
    localChannelNode.freq = this.freq;
    localChannelNode.latest_program = this.latest_program;
    localChannelNode.update_time = this.update_time;
    localChannelNode.mViewTime = this.mViewTime;
    localChannelNode.programCnt = this.programCnt;
    localChannelNode.ratingStar = this.ratingStar;
    return localChannelNode;
  }

  public void delProgramNode(int paramInt)
  {
    if (this.channelType == 0)
      return;
    if (this.mProgramScheduleList == null)
      this.mProgramScheduleList = new ProgramScheduleList(1);
    this.mProgramScheduleList.delProgramNode(paramInt);
  }

  public List<ProgramNode> getAllLstProgramNode()
  {
    if (isLiveChannel());
    do
    {
      return null;
      if ((this.mProgramScheduleList == null) && (!isDownloadChannel()))
      {
        this.mProgramScheduleList = ProgramHelper.getInstance().getProgramSchedule(this.channelId, this.channelType, false);
        if (this.mProgramScheduleList == null)
          InfoManager.getInstance().loadProgramsScheduleNode(this, null);
      }
    }
    while (this.mProgramScheduleList == null);
    return this.mProgramScheduleList.getLstProgramNode(0);
  }

  public String getApproximativeThumb()
  {
    return getApproximativeThumb(0, 0, false);
  }

  public String getApproximativeThumb(int paramInt1, int paramInt2)
  {
    return getApproximativeThumb(paramInt1, paramInt2, true);
  }

  public String getApproximativeThumb(int paramInt1, int paramInt2, boolean paramBoolean)
  {
    int i = Math.max(paramInt1, paramInt2);
    int j;
    int k;
    if (paramBoolean)
    {
      j = ScreenType.sImageBoundMedium;
      if (!paramBoolean)
        break label55;
      k = ScreenType.sImageBoundSmall;
    }
    while (true)
      if (i >= j)
      {
        if (!isEmpty(this.largeThumb))
        {
          return this.largeThumb;
          j = 600;
          break;
          label55: k = 300;
          continue;
        }
        if (!isEmpty(this.mediumThumb))
          return this.mediumThumb;
        return this.thumb;
      }
    if (i > k)
    {
      if (!isEmpty(this.mediumThumb))
        return this.mediumThumb;
      return this.thumb;
    }
    return this.thumb;
  }

  public String getAuthorNames()
  {
    if (this.lstAuthors == null)
      return "";
    Iterator localIterator = this.lstAuthors.iterator();
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

  public List<ProgramNode> getCurrLstProgramNodes(int paramInt1, List<ProgramNode> paramList, int paramInt2)
  {
    if (paramList == null)
      paramList = null;
    do
    {
      return paramList;
      if (paramInt1 < 0)
        break;
      List localList = getAllLstProgramNode();
      if (localList != null)
      {
        int i = 0;
        int j = paramInt1;
        if (i < localList.size())
        {
          ProgramNode localProgramNode = (ProgramNode)localList.get(i);
          if (j <= localProgramNode.sequence)
          {
            paramList.add(localProgramNode);
            j = localProgramNode.sequence;
          }
          while (true)
          {
            i++;
            break;
            if (localProgramNode.sequence == 0)
            {
              paramList.add(localProgramNode);
            }
            else if (paramInt1 <= localProgramNode.sequence)
            {
              int k = getProperProgramNodeBySequence(localProgramNode.sequence, paramList);
              if (k != -1)
                paramList.add(k, localProgramNode);
              else if (j + paramInt2 >= localProgramNode.sequence)
                paramList.add(localProgramNode);
            }
          }
        }
      }
    }
    while (paramList.size() > 0);
    return null;
  }

  public int getIndexProgramNodes(int paramInt)
  {
    List localList = getAllLstProgramNode();
    if (localList != null)
      for (int i = 0; i < localList.size(); i++)
        if (((ProgramNode)localList.get(i)).sequence == paramInt)
          return i;
    return -1;
  }

  public int getLoadedProgramSize()
  {
    if (this.channelType == 0)
      return this.mLoadedProgramSize;
    restoreProgramFromDB();
    if ((this.mProgramScheduleList != null) && (getAllLstProgramNode() != null))
      this.mLoadedProgramSize = getAllLstProgramNode().size();
    return this.mLoadedProgramSize;
  }

  public List<ProgramNode> getLstProgramNode(int paramInt)
  {
    if ((this.mProgramScheduleList == null) && (!isDownloadChannel()))
    {
      this.mProgramScheduleList = ProgramHelper.getInstance().getProgramSchedule(this.channelId, this.channelType, false);
      if (this.mProgramScheduleList == null)
        InfoManager.getInstance().loadProgramsScheduleNode(this, null);
    }
    ProgramScheduleList localProgramScheduleList = this.mProgramScheduleList;
    List localList = null;
    if (localProgramScheduleList != null)
      localList = this.mProgramScheduleList.getLstProgramNode(paramInt);
    return localList;
  }

  public ProgramNode getNextProgramNode(int paramInt)
  {
    if (!isLiveChannel())
    {
      List localList = getAllLstProgramNode();
      for (int i = 0; i < localList.size(); i++)
        if (((ProgramNode)localList.get(i)).uniqueId == paramInt)
        {
          if (i + 1 >= localList.size())
            break;
          return (ProgramNode)localList.get(i + 1);
        }
    }
    return null;
  }

  public ProgramNode getProgramNode(int paramInt)
  {
    if ((this.mProgramScheduleList == null) && (!isDownloadChannel()))
      this.mProgramScheduleList = ProgramHelper.getInstance().getProgramSchedule(this.channelId, this.channelType, true);
    if (this.mProgramScheduleList != null)
      return this.mProgramScheduleList.getProgramNode(paramInt);
    return null;
  }

  public ProgramNode getProgramNodeByProgramId(int paramInt)
  {
    if (!isLiveChannel())
    {
      List localList = getAllLstProgramNode();
      for (int i = 0; i < localList.size(); i++)
        if (((ProgramNode)localList.get(i)).uniqueId == paramInt)
          return (ProgramNode)localList.get(i);
    }
    return null;
  }

  public ProgramNode getProgramNodeByTime(long paramLong)
  {
    long l = paramLong / 1000L;
    if ((this.mProgramScheduleList == null) && (isLiveChannel()))
      this.mProgramScheduleList = ProgramHelper.getInstance().getProgramSchedule(this.channelId, this.channelType, true);
    if (this.mProgramScheduleList != null)
      return this.mProgramScheduleList.getProgramNodeByTime(l);
    return null;
  }

  public String getSourceUrl()
  {
    if (!isLiveChannel())
      return null;
    if ((this.mSourceUrl == null) || (this.mSourceUrl.equalsIgnoreCase("")))
      this.mSourceUrl = MediaCenter.getInstance().getPlayUrls("radiohls", String.valueOf(this.resId), 24, this.channelId);
    return this.mSourceUrl;
  }

  public long getUpdateTime()
  {
    if (this.update_time == null)
      return 0L;
    if (this.mUpdateTime > 0L)
      return this.mUpdateTime;
    this.mUpdateTime = TimeUtil.dateToMS(this.update_time);
    return this.mUpdateTime;
  }

  public List<ProgramNode> getYTLiveProgramNodes()
  {
    boolean bool = isLiveChannel();
    Object localObject = null;
    if (!bool);
    ProgramScheduleList localProgramScheduleList;
    do
    {
      return localObject;
      if (this.mProgramScheduleList == null)
      {
        this.mProgramScheduleList = ProgramHelper.getInstance().getProgramSchedule(this.channelId, this.channelType, false);
        if (this.mProgramScheduleList == null)
        {
          InfoManager.getInstance().loadProgramsScheduleNode(this, null);
          return null;
        }
      }
      localProgramScheduleList = this.mProgramScheduleList;
      localObject = null;
    }
    while (localProgramScheduleList == null);
    int i = TimeUtil.getDayofWeek(System.currentTimeMillis() / 1000L);
    List localList = this.mProgramScheduleList.getLstProgramNode(i);
    if (i == 1);
    for (int j = 7; ; j = i - 1)
    {
      localObject = this.mProgramScheduleList.getLstProgramNode(j);
      if ((localList == null) || (localObject == null))
        break;
      ((List)localObject).addAll(localList);
      return localObject;
    }
  }

  public boolean hasEmptyProgramSchedule()
  {
    if ((this.mProgramScheduleList == null) && (!isDownloadChannel()))
      this.mProgramScheduleList = ProgramHelper.getInstance().getProgramSchedule(this.channelId, this.channelType, false);
    if (this.mProgramScheduleList != null)
      return this.mProgramScheduleList.mLstProgramsScheduleNodes.size() <= 0;
    return true;
  }

  public boolean hasLoadingSize(int paramInt)
  {
    if (this.mLoadingSize == null);
    while (true)
    {
      return false;
      for (int i = 0; i < this.mLoadingSize.size(); i++)
        if (((Integer)this.mLoadingSize.get(i)).intValue() == paramInt)
          return true;
    }
  }

  public boolean hasLoadingStart(int paramInt)
  {
    if (this.mLoadingStart == null);
    while (true)
    {
      return false;
      for (int i = 0; i < this.mLoadingStart.size(); i++)
        if (((Integer)this.mLoadingStart.get(i)).intValue() == paramInt)
          return true;
    }
  }

  public boolean isDownloadChannel()
  {
    int i = this.categoryId;
    return i == Integer.valueOf(DownLoadInfoNode.mDownloadId).intValue();
  }

  public boolean isLiveChannel()
  {
    return this.channelType == 0;
  }

  public boolean isMusicChannel()
  {
    return (this.channelType == 1) && (this.categoryId == 523);
  }

  public boolean isNovelChannel()
  {
    return (this.channelType == 1) && (this.categoryId == 521);
  }

  public boolean noThumb()
  {
    return (this.thumb == null) && (this.mediumThumb == null) && (this.largeThumb == null);
  }

  public void onNodeUpdated(Object paramObject, String paramString)
  {
    if (paramObject == null);
    ProgramNode localProgramNode;
    do
    {
      do
        return;
      while (!paramString.equalsIgnoreCase("AVPI"));
      localProgramNode = (ProgramNode)paramObject;
    }
    while (localProgramNode.uniqueId != this.mLoadedProgramId);
    localProgramNode.channelId = this.channelId;
    addProgramNode(localProgramNode, true);
  }

  public List<ProgramNode> reloadAllLstProgramNode()
  {
    if (isLiveChannel());
    while (true)
    {
      return null;
      ProgramScheduleList localProgramScheduleList = ProgramHelper.getInstance().getProgramSchedule(this.channelId, this.channelType, false);
      if (localProgramScheduleList == null)
        InfoManager.getInstance().loadProgramsScheduleNode(this, null);
      while (this.mProgramScheduleList != null)
      {
        return this.mProgramScheduleList.getLstProgramNode(0);
        this.mProgramScheduleList = localProgramScheduleList;
      }
    }
  }

  public boolean restoreProgramFromDB()
  {
    if ((this.mProgramScheduleList == null) && (!isDownloadChannel()))
      this.mProgramScheduleList = ProgramHelper.getInstance().getProgramSchedule(this.channelId, this.channelType, true);
    return true;
  }

  public void setLargeThumb(String paramString)
  {
    this.largeThumb = paramString;
  }

  public void setLoadedProgramId(int paramInt)
  {
    this.mLoadedProgramId = paramInt;
  }

  public void setMediumThumb(String paramString)
  {
    this.mediumThumb = paramString;
  }

  public void setProgramScheduleList(ProgramScheduleList paramProgramScheduleList)
  {
    this.mProgramScheduleList = paramProgramScheduleList;
  }

  public void setSmallThumb(String paramString)
  {
    this.thumb = paramString;
  }

  public void updateAllInfo(ChannelNode paramChannelNode)
  {
    if (paramChannelNode == null)
      return;
    this.channelId = paramChannelNode.channelId;
    this.categoryId = paramChannelNode.categoryId;
    this.title = paramChannelNode.title;
    this.desc = paramChannelNode.desc;
    this.groupId = paramChannelNode.groupId;
    this.thumb = paramChannelNode.thumb;
    this.mediumThumb = paramChannelNode.mediumThumb;
    this.largeThumb = paramChannelNode.largeThumb;
    this.autoPlay = paramChannelNode.autoPlay;
    this.recordEnable = paramChannelNode.recordEnable;
    this.channelType = paramChannelNode.channelType;
    this.resId = paramChannelNode.resId;
    this.audienceCnt = paramChannelNode.audienceCnt;
    this.mapLinkInfo = paramChannelNode.mapLinkInfo;
    this.latest_program = paramChannelNode.latest_program;
    this.update_time = paramChannelNode.update_time;
    this.mViewTime = paramChannelNode.mViewTime;
    this.mUpdateTime = paramChannelNode.mUpdateTime;
    this.lstAuthors = paramChannelNode.lstAuthors;
    this.lstBroadcaster = paramChannelNode.lstBroadcaster;
    this.lstPodcasters = paramChannelNode.lstPodcasters;
    this.ratingStar = paramChannelNode.ratingStar;
    this.programCnt = paramChannelNode.programCnt;
  }

  public void updatePartialInfo(ChannelNode paramChannelNode)
  {
    if (paramChannelNode == null)
      return;
    this.channelId = paramChannelNode.channelId;
    this.categoryId = paramChannelNode.categoryId;
    this.title = paramChannelNode.title;
    this.desc = paramChannelNode.desc;
    this.groupId = paramChannelNode.groupId;
    this.thumb = paramChannelNode.thumb;
    this.mediumThumb = paramChannelNode.mediumThumb;
    this.largeThumb = paramChannelNode.largeThumb;
    this.autoPlay = paramChannelNode.autoPlay;
    this.recordEnable = paramChannelNode.recordEnable;
    this.channelType = paramChannelNode.channelType;
    this.resId = paramChannelNode.resId;
    this.audienceCnt = paramChannelNode.audienceCnt;
    this.mapLinkInfo = paramChannelNode.mapLinkInfo;
    this.latest_program = paramChannelNode.latest_program;
    this.update_time = paramChannelNode.update_time;
    this.mViewTime = paramChannelNode.mViewTime;
    this.mUpdateTime = paramChannelNode.mUpdateTime;
    this.lstAuthors = paramChannelNode.lstAuthors;
    this.lstBroadcaster = paramChannelNode.lstBroadcaster;
    this.lstPodcasters = paramChannelNode.lstPodcasters;
    this.ratingStar = paramChannelNode.ratingStar;
    this.programCnt = paramChannelNode.programCnt;
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.qtradio.model.ChannelNode
 * JD-Core Version:    0.6.2
 */