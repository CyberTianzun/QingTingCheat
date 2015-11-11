package fm.qingting.qtradio.model;

import android.graphics.Bitmap;
import android.os.Environment;
import com.umeng.fb.FeedbackAgent;
import fm.qingting.framework.data.DataManager;
import fm.qingting.framework.data.IResultToken;
import fm.qingting.framework.data.Result;
import fm.qingting.framework.manager.EventDispacthManager;
import fm.qingting.qtradio.alarm.ClockManager;
import fm.qingting.qtradio.alarm.ClockManager.IClockListener;
import fm.qingting.qtradio.controller.ControllerManager;
import fm.qingting.qtradio.doubleclick.DoubleClick;
import fm.qingting.qtradio.fm.IMediaEventListener;
import fm.qingting.qtradio.fm.PlayStatus;
import fm.qingting.qtradio.fm.PlayerAgent;
import fm.qingting.qtradio.fm.TaobaoAgent;
import fm.qingting.qtradio.helper.ChannelHelper;
import fm.qingting.qtradio.im.IMAgent;
import fm.qingting.qtradio.im.IMContacts;
import fm.qingting.qtradio.playlist.PlayListManager;
import fm.qingting.qtradio.room.GetTopicAction;
import fm.qingting.qtradio.room.Room;
import fm.qingting.qtradio.room.RoomManager;
import fm.qingting.qtradio.search.SearchNode;
import fm.qingting.qtradio.sensor.SpeedSensor;
import fm.qingting.utils.PlayProcessSyncUtil;
import fm.qingting.utils.QTMSGManage;
import fm.qingting.utils.RecommendStatisticsUtil;
import fm.qingting.utils.ThirdTracker;
import fm.qingting.utils.Zeus;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class RootNode extends Node
  implements IMediaEventListener, ClockManager.IClockListener
{
  public static final int frontPageId = 0;
  public static final int localPageId = 1;
  private int LOADED_INTERVAL = 30;
  private int fromType = FromType.UNKNOWN.ordinal();
  private boolean hasChangedChannel = false;
  private boolean hasOpenFM = false;
  private boolean hasSendDownload = false;
  private List<Object> hotPrograms = null;
  private int lastFromType = FromType.UNKNOWN.ordinal();
  private PlayMode lastPlayMode = PlayMode.UNKNOWN;
  public ActivityInfoNode mActivityInfoNode = new ActivityInfoNode();
  public AdvertisementInfoNode mAdvertisementInfoNode = new AdvertisementInfoNode();
  public BillboardNode mBillboardNode = new BillboardNode();
  public ContentCategoryNode mContentCategory = new ContentCategoryNode();
  public DownLoadInfoNode mDownLoadInfoNode = new DownLoadInfoNode();
  public GuideFavNode mGuideFavNode = new GuideFavNode();
  private Map<String, Long> mHasLoadUrls = new HashMap();
  private String mInterestCategoryId = "";
  private String mInterestChannelId = "";
  private int mInterestProgramBegin = 0;
  private long mInterestProgramDuration = 0L;
  private String mInterestProgramName = "";
  private int mLastBitmapId = 0;
  public LinkInfoNode mLinkInfoNode = new LinkInfoNode();
  public RecommendCategoryNode mLocalRecommendCategoryNode;
  public RecommendCategoryNode mNewFrontPageNode;
  public PersonalCenterNode mPersonalCenterNode = new PersonalCenterNode();
  private ChannelNode mPlayingChannelNode;
  private Node mPlayingNode;
  public PlayingProgramInfoNode mPlayingProgramInfo = new PlayingProgramInfoNode();
  public ProgramTopicInfoNode mProgramTopicInfoNode = new ProgramTopicInfoNode();
  public RecommendNode mRecommendNode = new RecommendNode();
  public RecommendPlayingInfoNode mRecommendPlayingInfo = new RecommendPlayingInfoNode();
  public RingToneInfoNode mRingToneInfoNode = new RingToneInfoNode();
  public SearchNode mSearchNode;
  public ShareInfoNode mShareInfoNode = new ShareInfoNode();
  private Map<String, List<IInfoUpdateEventListener>> mapInfoUpdateEventListeners = new HashMap();
  private Map<String, List<IPlayInfoEventListener>> mapPlayInfoEventListeners = new HashMap();
  public Map<Integer, RecommendCategoryNode> mapRecommendCategory = new HashMap();
  private PlayMode playMode = PlayMode.UNKNOWN;
  private List<Object> recommendPrograms = new ArrayList();
  private long throwLinkTime = 0L;
  private FeedbackAgent umengAgent = null;

  public RootNode()
  {
    this.nodeName = "root";
  }

  private void addLoadUrls(String paramString)
  {
    if ((paramString == null) || (paramString.equalsIgnoreCase("")))
      return;
    this.mHasLoadUrls.put(paramString, Long.valueOf(System.currentTimeMillis() / 1000L));
  }

  private void checkLinkInfo()
  {
    if ((this.mPlayingNode == null) || (this.mPlayingChannelNode == null) || (!PlayerAgent.getInstance().isPlaying()));
    Map localMap;
    int j;
    do
    {
      int i;
      do
      {
        long l;
        do
        {
          ProgramNode localProgramNode;
          do
          {
            int k;
            int m;
            do
            {
              do
              {
                do
                {
                  return;
                  localMap = this.mPlayingChannelNode.mapLinkInfo;
                }
                while (!this.mPlayingNode.nodeName.equalsIgnoreCase("program"));
                localProgramNode = (ProgramNode)this.mPlayingNode;
              }
              while (!localProgramNode.available);
              if (!localProgramNode.isDownloadProgram())
                break;
              k = localProgramNode.getDuration();
              m = PlayProcessSyncUtil.getInstance().getCurrentPlayTime();
            }
            while ((m <= 0) || (k - m >= InfoManager.getInstance().getLinkDuration() / 2) || (k - m <= 0));
            throwLink();
            return;
            if (localProgramNode.mapLinkInfo != null)
              localMap = localProgramNode.mapLinkInfo;
          }
          while (localMap == null);
          i = localProgramNode.getCurrPlayStatus();
          if (i != 1)
            break;
          l = System.currentTimeMillis() / 1000L - localProgramNode.getAbsoluteStartTime();
        }
        while (l <= 0L);
        handleLinkInfo(l, localMap);
        return;
      }
      while (i != 3);
      j = PlayProcessSyncUtil.getInstance().getCurrentPlayTime();
    }
    while (j <= 0);
    handleLinkInfo(j, localMap);
  }

  private void checkProgramNode(int paramInt)
  {
    if (((this.playMode == PlayMode.NORMALPLAY) || (this.playMode == PlayMode.FMPLAY)) && (this.mPlayingNode != null) && (this.mPlayingNode.nodeName.equalsIgnoreCase("program")))
    {
      ChannelNode localChannelNode = this.mPlayingChannelNode;
      if ((localChannelNode == null) || (((ChannelNode)localChannelNode).channelType != 1))
        break label69;
      checkVirtualProgram(paramInt);
    }
    label69: ProgramNode localProgramNode;
    do
    {
      long l1;
      long l2;
      do
      {
        do
        {
          return;
          l1 = ((ProgramNode)this.mPlayingNode).getAbsoluteStartTime();
          localProgramNode = (ProgramNode)this.mPlayingNode.nextSibling;
        }
        while (localProgramNode == null);
        l2 = localProgramNode.getAbsoluteStartTime();
      }
      while ((l2 <= l1) || (l2 >= paramInt));
      if (InfoManager.getInstance().getQuitAfterPlay())
      {
        EventDispacthManager.getInstance().dispatchAction("QTquit", null);
        return;
      }
      setPlayingNode(localProgramNode);
    }
    while (!PlayerAgent.getInstance().isPlaying());
    PlayerAgent.getInstance().play(localProgramNode);
  }

  private void checkVirtualProgram(int paramInt)
  {
    if ((this.playMode == PlayMode.NORMALPLAY) && (this.mPlayingNode != null) && (((ProgramNode)this.mPlayingNode).channelType == 0) && (((ProgramNode)this.mPlayingNode).getAbsoluteEndTime() < paramInt) && (PlayerAgent.getInstance().isPlaying()) && (((ProgramNode)this.mPlayingNode).getAbsoluteEndTime() < paramInt))
    {
      if (!InfoManager.getInstance().getQuitAfterPlay())
        break label91;
      EventDispacthManager.getInstance().dispatchAction("QTquit", null);
    }
    label91: 
    while (!PlayerAgent.getInstance().isPlaying())
      return;
    PlayerAgent.getInstance().playNext();
  }

  private void dispatchInfoUpdateEvent(int paramInt)
  {
    String str = String.valueOf(paramInt);
    if (this.mapInfoUpdateEventListeners.containsKey(str))
    {
      List localList = (List)this.mapInfoUpdateEventListeners.get(str);
      for (int i = 0; i < localList.size(); i++)
        ((IInfoUpdateEventListener)localList.get(i)).onInfoUpdated(paramInt);
    }
  }

  private void dispatchPlayInfoEvent(int paramInt)
  {
    String str = String.valueOf(paramInt);
    if (this.mapPlayInfoEventListeners.containsKey(str))
    {
      List localList = (List)this.mapPlayInfoEventListeners.get(str);
      for (int i = 0; i < localList.size(); i++)
        ((IPlayInfoEventListener)localList.get(i)).onPlayInfoUpdated(paramInt);
    }
  }

  private Node findCatNodeByChannelId(int paramInt, Node paramNode)
  {
    Node localNode = null;
    if (paramNode == null);
    do
    {
      boolean bool;
      do
      {
        return localNode;
        bool = paramNode.nodeName.equalsIgnoreCase("program");
        localNode = null;
      }
      while (bool);
      if ((paramNode.nodeName.equalsIgnoreCase("channel")) && (((ChannelNode)paramNode).channelId == paramInt))
        return paramNode.parent;
      localNode = findCatNodeByChannelId(paramInt, paramNode.getChild());
    }
    while (localNode != null);
    return findCatNodeByChannelId(paramInt, paramNode.getNextSibling());
  }

  private Node findNodeByCategoryId(int paramInt, Node paramNode)
  {
    Node localNode;
    if (paramNode.nodeName.equalsIgnoreCase("live"))
    {
      if (((LiveNode)paramNode).getLiveCategoryNodes() != null)
      {
        Iterator localIterator = ((LiveNode)paramNode).getLiveCategoryNodes().iterator();
        do
        {
          if (!localIterator.hasNext())
            break;
          localNode = (Node)localIterator.next();
        }
        while ((localNode == null) || (!(localNode instanceof CategoryNode)) || (((CategoryNode)localNode).categoryId != paramInt));
      }
    }
    else
    {
      do
      {
        return localNode;
        return null;
        if (!paramNode.nodeName.equalsIgnoreCase("contentcategory"))
          break;
        localNode = findNodeByCategoryId(paramInt, ((ContentCategoryNode)paramNode).mLiveNode);
      }
      while (localNode != null);
      return findCategoryNodeFromAnchor(paramInt, ((ContentCategoryNode)paramNode).mVirtualNode);
    }
    return null;
  }

  private Node findNodeByChannelId(int paramInt, Node paramNode)
  {
    if (paramNode == null)
      paramNode = null;
    do
    {
      return paramNode;
      if (paramNode.nodeName.equalsIgnoreCase("program"))
        return null;
    }
    while ((paramNode.nodeName.equalsIgnoreCase("channel")) && (((ChannelNode)paramNode).channelId == paramInt));
    Node localNode = findNodeByCategoryId(paramInt, paramNode.getChild());
    if (localNode == null)
      localNode = findNodeByCategoryId(paramInt, paramNode.getNextSibling());
    return localNode;
  }

  private String getCatId()
  {
    if (this.mPlayingChannelNode != null)
      return String.valueOf(this.mPlayingChannelNode.categoryId);
    return null;
  }

  private String getPlayingChannelThumb()
  {
    ChannelNode localChannelNode = getCurrentPlayingChannelNode();
    if (localChannelNode != null)
      return localChannelNode.getApproximativeThumb();
    return null;
  }

  private String getSubCatId()
  {
    return null;
  }

  private void getTopic(int paramInt)
  {
    GetTopicAction localGetTopicAction = new GetTopicAction();
    localGetTopicAction.setContentInfo(1, String.valueOf(paramInt));
    RoomManager.getInstance().getRoomByType(1).doAction(localGetTopicAction);
  }

  private void handleLinkInfo(long paramLong, Map<Integer, Integer> paramMap)
  {
    if (paramMap == null);
    label175: 
    while (true)
    {
      return;
      int i = InfoManager.getInstance().getLinkDuration();
      if (i > 0)
      {
        Iterator localIterator = paramMap.entrySet().iterator();
        while (true)
        {
          if (!localIterator.hasNext())
            break label175;
          Map.Entry localEntry = (Map.Entry)localIterator.next();
          int j = ((Integer)localEntry.getKey()).intValue();
          if ((j < paramLong) && (paramLong < j + i))
          {
            int k = ((Integer)localEntry.getValue()).intValue();
            RecommendItemNode localRecommendItemNode = this.mLinkInfoNode.getLinkInfo(k);
            if (localRecommendItemNode != null)
            {
              long l1 = localRecommendItemNode.getShowLink();
              long l2 = System.currentTimeMillis() / 1000L;
              if (l2 - l1 <= i)
                break;
              localRecommendItemNode.setShowLink(l2);
              EventDispacthManager.getInstance().dispatchAction("showLink", localRecommendItemNode);
              return;
            }
            InfoManager.getInstance().loadLinkInfo(k, null);
          }
        }
      }
    }
  }

  private void handleRecvReply(String paramString)
  {
  }

  private void preload(ProgramNode paramProgramNode, ChannelNode paramChannelNode)
  {
    if ((paramProgramNode == null) || (paramChannelNode == null) || (paramChannelNode.isDownloadChannel()));
    do
    {
      do
        return;
      while ((paramChannelNode.channelType != 1) || (paramProgramNode.channelType != 1) || (paramProgramNode.nextSibling != null));
      if (paramChannelNode.hasEmptyProgramSchedule())
      {
        InfoManager.getInstance().loadVirtualProgramsScheduleNode(paramChannelNode, paramChannelNode.channelId, paramChannelNode.isNovelChannel(), 1, 0, null);
        return;
      }
      if ((paramProgramNode.parent == null) || (!paramProgramNode.parent.nodeName.equalsIgnoreCase("recommenditem")))
        break;
    }
    while (paramChannelNode.getProgramNode(paramProgramNode.uniqueId) != null);
    paramProgramNode.nextSibling = ((Node)paramChannelNode.getAllLstProgramNode().get(0));
    ((ProgramNode)paramChannelNode.getAllLstProgramNode().get(0)).prevSibling = paramProgramNode;
    paramChannelNode.getAllLstProgramNode().add(0, paramProgramNode);
    return;
    InfoManager.getInstance().loadProgramsScheduleNode(paramChannelNode, null);
  }

  private void reloadAD()
  {
    if (this.mAdvertisementInfoNode.getLstAds() == null)
      InfoManager.getInstance().loadADPos();
  }

  private void saveToPlayList(Node paramNode)
  {
    if (paramNode == null);
    while (!paramNode.nodeName.equalsIgnoreCase("program"))
      return;
    PlayListManager.getInstance().setPlayList((ProgramNode)paramNode, ((ProgramNode)paramNode).channelId, 3, false);
  }

  private void sendUmeng()
  {
    if ((this.mPlayingNode != null) && (!this.hasSendDownload) && (this.mPlayingNode.nodeName.equalsIgnoreCase("program")) && (((ProgramNode)this.mPlayingNode).isDownloadProgram()))
    {
      QTMSGManage.getInstance().sendStatistcsMessage("ListenDownload", "1");
      this.hasSendDownload = true;
    }
  }

  private void setQuitTime()
  {
    if (!InfoManager.getInstance().getQuitAfterPlay());
    while (true)
    {
      return;
      if ((this.mPlayingNode != null) && (this.mPlayingNode.nodeName.equalsIgnoreCase("program")))
      {
        ProgramNode localProgramNode = (ProgramNode)this.mPlayingNode;
        int i = localProgramNode.getCurrPlayStatus();
        int j;
        if (i == 1)
        {
          long l2 = System.currentTimeMillis() / 1000L;
          j = (int)(localProgramNode.getAbsoluteEndTime() - l2);
        }
        while (j > 0)
        {
          ClockManager.getInstance().addTimer(new Clock(2, j, true));
          long l1 = System.currentTimeMillis() / 1000L + j;
          GlobalCfg.getInstance(InfoManager.getInstance().getContext()).setQuitTime(l1);
          return;
          if (i == 3)
          {
            int k = PlayProcessSyncUtil.getInstance().getCurrentPlayTime();
            j = localProgramNode.getDuration() - k;
          }
          else
          {
            j = 0;
          }
        }
      }
    }
  }

  private void throwLink()
  {
    int k;
    if ((this.mPlayingNode != null) && (this.mPlayingNode.nodeName.equalsIgnoreCase("program")) && (((ProgramNode)this.mPlayingNode).isDownloadProgram()))
    {
      String str = ((ProgramNode)this.mPlayingNode).title;
      if (str == null)
        break label244;
      int j = str.length();
      k = j - 4;
      if ((k > 0) && (k < j) && (str.charAt(k) >= '0') && (str.charAt(k) <= '9'))
        break label111;
      if (k != j)
        break label244;
    }
    label111: label244: for (int i = 1; ; i = 0)
    {
      if (i != 0);
      RecommendItemNode localRecommendItemNode;
      do
      {
        ChannelNode localChannelNode;
        do
        {
          return;
          k++;
          break;
          localChannelNode = ChannelHelper.getInstance().getFakeVirtualChannel(((ProgramNode)this.mPlayingNode).channelId, ((ProgramNode)this.mPlayingNode).getCategoryId(), ((ProgramNode)this.mPlayingNode).title);
        }
        while (localChannelNode == null);
        localRecommendItemNode = new RecommendItemNode();
        localRecommendItemNode.name = "点此收听更多精彩节目";
        localRecommendItemNode.desc = localChannelNode.desc;
        localRecommendItemNode.mNode = localChannelNode;
        localRecommendItemNode.id = "0";
      }
      while (System.currentTimeMillis() / 1000L - this.throwLinkTime <= InfoManager.getInstance().getLinkDuration());
      EventDispacthManager.getInstance().dispatchAction("showLink", localRecommendItemNode);
      this.throwLinkTime = (System.currentTimeMillis() / 1000L);
      return;
    }
  }

  private void updateDB()
  {
    this.mPersonalCenterNode.upateDB();
    this.mDownLoadInfoNode.syncDownloadingToDB();
    IMContacts.getInstance().writeToDB();
  }

  public void clearHasChangedChannel()
  {
    this.hasChangedChannel = false;
  }

  public PlayMode currentPlayMode()
  {
    return this.playMode;
  }

  public void delPullNodes()
  {
    DataManager.getInstance().getData("deletedb_pull_node", null, null);
  }

  public Node findCategoryNodeFromAnchor(int paramInt, Node paramNode)
  {
    if (paramNode == null)
      return null;
    return findNodeByCategoryId(paramInt, paramNode);
  }

  public Node findCategoryNodeFromAnchorByChannelId(int paramInt, Node paramNode)
  {
    if (paramNode == null)
      return null;
    return findCatNodeByChannelId(paramInt, paramNode);
  }

  public Node findNodeByCategoryId(int paramInt)
  {
    return findNodeByCategoryId(paramInt, this.mContentCategory);
  }

  public Node findNodeByChannelId(int paramInt)
  {
    if (this.mContentCategory != null)
      return findNodeByChannelId(paramInt, this.mContentCategory.mLiveNode.child);
    return null;
  }

  public ChannelNode findNodeByProgramNode(ProgramNode paramProgramNode)
  {
    if (paramProgramNode == null)
      return null;
    return ChannelHelper.getInstance().getChannel(paramProgramNode);
  }

  public int getCatIdBySecId(int paramInt)
  {
    return this.mContentCategory.mVirtualNode.getCatIdBySecId(paramInt);
  }

  public ChannelNode getCurrentPlayingChannelNode()
  {
    return this.mPlayingChannelNode;
  }

  public Node getCurrentPlayingNode()
  {
    return this.mPlayingNode;
  }

  public boolean getHasChangedChannel()
  {
    return this.hasChangedChannel;
  }

  public List<Object> getHotPrograms()
  {
    return this.hotPrograms;
  }

  public int getLastFromType()
  {
    return this.lastFromType;
  }

  public String getLocalProgramSource(ProgramNode paramProgramNode)
  {
    if (paramProgramNode == null)
      return null;
    return this.mDownLoadInfoNode.getDownloadedProgramSource(paramProgramNode);
  }

  public List<Node> getPullNodes()
  {
    Result localResult = DataManager.getInstance().getData("getdb_pull_node", null, null).getResult();
    boolean bool = localResult.getSuccess();
    List localList = null;
    if (bool)
      localList = (List)localResult.getData();
    return localList;
  }

  public RecommendCategoryNode getRecommendCategoryNode(int paramInt)
  {
    if (paramInt == 0)
      return this.mNewFrontPageNode;
    if (this.mapRecommendCategory.containsKey(Integer.valueOf(paramInt)))
      return (RecommendCategoryNode)this.mapRecommendCategory.get(Integer.valueOf(paramInt));
    return null;
  }

  public List<Object> getRecommendPrograms()
  {
    return this.recommendPrograms;
  }

  public int getSecIdByCatId(int paramInt)
  {
    return this.mContentCategory.mVirtualNode.getSecIdByCatId(paramInt);
  }

  public boolean hasLoadUrls(String paramString, boolean paramBoolean)
  {
    if ((paramString == null) || (paramString.equalsIgnoreCase("")))
      return false;
    if (!InfoManager.getInstance().hasConnectedNetwork())
      return true;
    if ((Long)this.mHasLoadUrls.get(paramString) == null)
    {
      addLoadUrls(paramString);
      return false;
    }
    if (paramBoolean)
    {
      addLoadUrls(paramString);
      return false;
    }
    return true;
  }

  public void init()
  {
    this.mRingToneInfoNode.init();
    this.mPersonalCenterNode.init();
    this.mDownLoadInfoNode.init();
    this.mSearchNode = new SearchNode();
    this.mSearchNode.parent = this;
    this.mSearchNode.init();
    PlayerAgent.getInstance().addMediaEventListener(this);
    ClockManager.getInstance().addListener(this);
    PlayedMetaInfo.getInstance().init();
    PlayProcessSyncUtil.getInstance().init();
  }

  public boolean isInstantReplay()
  {
    if (this.mPlayingNode == null)
      return false;
    return (this.mPlayingNode.nodeName.equalsIgnoreCase("program")) && (((ProgramNode)this.mPlayingNode).getCurrPlayStatus() == 1) && (currentPlayMode() == PlayMode.REPLAY);
  }

  public boolean isOpenFm()
  {
    return this.hasOpenFM;
  }

  public boolean isPlayingAd()
  {
    return (this.playMode == PlayMode.PLAY_END_ADVERTISEMENT) || (this.playMode == PlayMode.PLAY_FRONT_ADVERTISEMENT);
  }

  public boolean isProgramNodeChanged(Node paramNode1, Node paramNode2)
  {
    if ((paramNode1 == null) && (paramNode2 != null));
    do
    {
      do
      {
        do
        {
          return true;
          if ((paramNode1 != null) && (paramNode2 == null))
            return false;
        }
        while (!paramNode1.nodeName.equalsIgnoreCase(paramNode2.nodeName));
        if (!paramNode1.nodeName.equalsIgnoreCase("program"))
          break;
      }
      while (((ProgramNode)paramNode1).uniqueId != ((ProgramNode)paramNode2).uniqueId);
      return false;
      if (!paramNode1.nodeName.equalsIgnoreCase("ondemandprogram"))
        break;
    }
    while (!((OnDemandProgramNode)paramNode1).programId.equalsIgnoreCase(((OnDemandProgramNode)paramNode2).programId));
    return false;
    return false;
  }

  public boolean isSDCardAvailable()
  {
    return Environment.getExternalStorageState().equals("mounted");
  }

  public void onClockTime(int paramInt)
  {
    checkProgramNode(paramInt);
    checkLinkInfo();
    if (paramInt % 5 == 0)
    {
      updateDB();
      ThirdTracker.getInstance().trackJD();
    }
    if (paramInt % 2 == 0)
    {
      DoubleClick.getInstance().startZeus();
      DoubleClick.getInstance().startSuperZeus();
    }
    if (paramInt % 10 == 0)
    {
      Zeus.getInstance().startZeus();
      ThirdTracker.getInstance().startAM();
      ThirdTracker.getInstance().start();
      reloadAD();
    }
    long l = ThirdTracker.getInstance().getJDAdvTime();
    if ((l > 0L) && (paramInt >= l))
      ThirdTracker.getInstance().changeJD();
    if (paramInt % 20 == 0)
    {
      InfoManager.getInstance().runSellApps();
      if (InfoManager.getInstance().enableTBMagic())
        TaobaoAgent.getInstance().playAD(true);
    }
    if (paramInt % 60 == 0)
    {
      RecommendStatisticsUtil.INSTANCE.sendLog();
      if (this.mRecommendPlayingInfo.checkRecommendPlayingList(paramInt))
        InfoManager.getInstance().root().setInfoUpdate(2);
      IMAgent.getInstance().ping();
    }
  }

  public void onPlayStatusUpdated(PlayStatus paramPlayStatus)
  {
    if (paramPlayStatus.state == 2)
    {
      if ((this.mPlayingNode == null) || (this.playMode == PlayMode.ALARMPLAY) || (this.playMode == PlayMode.ALARM_PLAY_ONLINE) || (this.playMode == PlayMode.PLAY_FRONT_ADVERTISEMENT))
        break label579;
      if (!this.mPlayingNode.nodeName.equalsIgnoreCase("channel"))
        break label62;
    }
    label62: Node localNode;
    do
    {
      do
        return;
      while (!this.mPlayingNode.nodeName.equalsIgnoreCase("program"));
      if (InfoManager.getInstance().getQuitAfterPlay())
      {
        EventDispacthManager.getInstance().dispatchAction("QTquit", null);
        return;
      }
      if (((ProgramNode)this.mPlayingNode).channelType == 0)
      {
        ProgramNode localProgramNode3 = (ProgramNode)this.mPlayingNode.nextSibling;
        if (localProgramNode3 != null)
        {
          long l = System.currentTimeMillis() / 1000L;
          if (localProgramNode3.getAbsoluteEndTime() < l)
          {
            PlayerAgent.getInstance().replay(localProgramNode3);
            return;
          }
          PlayerAgent.getInstance().play(localProgramNode3);
          return;
        }
        PlayerAgent.getInstance().setCurrPlayState(0);
        return;
      }
      PlayedMetaInfo.getInstance().deletePlayedMetaById(((ProgramNode)this.mPlayingNode).uniqueId);
      if ((this.playMode != PlayMode.PLAY_END_ADVERTISEMENT) && (InfoManager.getInstance().enableAudioAdv((ProgramNode)this.mPlayingNode)))
      {
        int n = ((ProgramNode)this.mPlayingNode).getCategoryId();
        String str = InfoManager.getInstance().getEndAudioAdv(n, ((ProgramNode)this.mPlayingNode).channelId);
        if (PlayerAgent.getInstance().playSource(str))
        {
          setPlayMode(PlayMode.PLAY_END_ADVERTISEMENT);
          return;
        }
      }
      if (this.playMode == PlayMode.PLAY_END_ADVERTISEMENT)
        setPlayMode();
      localNode = this.mPlayingNode.parent;
    }
    while ((localNode != null) && (localNode.nodeName.equalsIgnoreCase("channel")));
    if ((localNode != null) && (localNode.nodeName.equalsIgnoreCase("recommenditem")))
    {
      ProgramNode localProgramNode2 = (ProgramNode)this.mPlayingNode.nextSibling;
      if (localProgramNode2 != null)
      {
        PlayerAgent.getInstance().play(localProgramNode2);
        return;
      }
    }
    ProgramNode localProgramNode1;
    Object localObject;
    if ((this.mPlayingChannelNode != null) && (this.mPlayingChannelNode.channelType == 1))
    {
      if (Integer.valueOf(this.mPlayingChannelNode.channelId).intValue() != ((ProgramNode)this.mPlayingNode).channelId)
      {
        PlayerAgent.getInstance().setCurrPlayState(0);
        PlayerAgent.getInstance().play(this.mPlayingNode);
        return;
      }
      if ((this.mPlayingChannelNode.hasEmptyProgramSchedule()) || (this.mPlayingChannelNode.getAllLstProgramNode().size() <= 0))
        break label767;
      localProgramNode1 = this.mPlayingChannelNode.getProgramNodeByProgramId(((ProgramNode)this.mPlayingNode).uniqueId);
      if ((localProgramNode1 != null) && (localProgramNode1.nextSibling != null))
        localObject = localProgramNode1.nextSibling;
    }
    while (true)
      if (localObject != null)
      {
        PlayerAgent.getInstance().play((Node)localObject);
        return;
        if (localProgramNode1 != null)
          localObject = this.mPlayingChannelNode.getNextProgramNode(((ProgramNode)this.mPlayingNode).uniqueId);
        else
          localObject = (Node)this.mPlayingChannelNode.getAllLstProgramNode().get(0);
      }
      else
      {
        PlayerAgent.getInstance().setCurrPlayState(0);
        PlayerAgent.getInstance().play(this.mPlayingNode);
        return;
        PlayerAgent.getInstance().setCurrPlayState(0);
        PlayerAgent.getInstance().play(this.mPlayingNode);
        return;
        label579: if (this.playMode == PlayMode.ALARMPLAY)
        {
          if (!InfoManager.getInstance().isNetworkAvailable())
          {
            PlayerAgent.getInstance().playRingTone(InfoManager.getInstance().root().mRingToneInfoNode.getRingNodeById(InfoManager.getInstance().root().mRingToneInfoNode.getAvailableRingId()));
            return;
          }
          int i = InfoManager.getInstance().root().mRingToneInfoNode.getRingCatId();
          int j = InfoManager.getInstance().root().mRingToneInfoNode.getRingChannelId();
          int k = InfoManager.getInstance().root().mRingToneInfoNode.getRingChannelType();
          int m = InfoManager.getInstance().root().mRingToneInfoNode.getRingProgramId();
          ControllerManager.getInstance().openPlayViewForAlarm(Integer.valueOf(i).intValue(), Integer.valueOf(j).intValue(), m, k);
          return;
        }
        if (this.playMode == PlayMode.PLAY_FRONT_ADVERTISEMENT)
        {
          setPlayMode();
          PlayerAgent.getInstance().play(this.mPlayingNode, false);
          return;
        }
        if (this.playMode == PlayMode.ALARM_PLAY_ONLINE)
        {
          PlayerAgent.getInstance().stop();
          return;
        }
        PlayerAgent.getInstance().setCurrPlayState(0);
        PlayerAgent.getInstance().dispatchPlayStateInFM(0);
        return;
        label767: localObject = null;
      }
  }

  public void onTime(Clock paramClock)
  {
  }

  public void onTimeStart(Clock paramClock)
  {
  }

  public void onTimeStop(Clock paramClock)
  {
    if (paramClock.type == 2)
      EventDispacthManager.getInstance().dispatchAction("QTquit", null);
  }

  public void onTimerRemoved()
  {
  }

  public void registerInfoUpdateListener(IInfoUpdateEventListener paramIInfoUpdateEventListener, int paramInt)
  {
    String str = String.valueOf(paramInt);
    List localList;
    if ((paramIInfoUpdateEventListener != null) && (str != null))
    {
      if (this.mapInfoUpdateEventListeners.containsKey(str))
        localList = (List)this.mapInfoUpdateEventListeners.get(str);
    }
    else
    {
      for (int i = 0; i < localList.size(); i++)
        if (localList.get(i) == paramIInfoUpdateEventListener)
          return;
      ((List)this.mapInfoUpdateEventListeners.get(str)).add(paramIInfoUpdateEventListener);
      return;
    }
    ArrayList localArrayList = new ArrayList();
    localArrayList.add(paramIInfoUpdateEventListener);
    this.mapInfoUpdateEventListeners.put(str, localArrayList);
  }

  public void registerSubscribeEventListener(IPlayInfoEventListener paramIPlayInfoEventListener, int paramInt)
  {
    String str = String.valueOf(paramInt);
    List localList;
    if ((paramIPlayInfoEventListener != null) && (str != null))
    {
      if (this.mapPlayInfoEventListeners.containsKey(str))
        localList = (List)this.mapPlayInfoEventListeners.get(str);
    }
    else
    {
      for (int i = 0; i < localList.size(); i++)
        if (localList.get(i) == paramIPlayInfoEventListener)
          return;
      ((List)this.mapPlayInfoEventListeners.get(str)).add(paramIPlayInfoEventListener);
      return;
    }
    ArrayList localArrayList = new ArrayList();
    localArrayList.add(paramIPlayInfoEventListener);
    this.mapPlayInfoEventListeners.put(str, localArrayList);
  }

  public long replayCurrNodeByTime(String paramString, long paramLong)
  {
    if ((paramString != null) && (paramLong >= 0L) && (this.mPlayingNode == null));
    return -1L;
  }

  public void restoreContentFromDB()
  {
    if (this.mContentCategory != null)
    {
      this.mContentCategory.restoreChildFromDB();
      return;
    }
    ContentCategoryNode localContentCategoryNode = new ContentCategoryNode();
    localContentCategoryNode.restoreChildFromDB();
    this.mContentCategory = localContentCategoryNode;
  }

  public void saveFavToDB()
  {
    if (this.mPersonalCenterNode == null)
      return;
    this.mPersonalCenterNode.saveFavToDB();
  }

  public void saveInterestProgram()
  {
    int i = SharedCfg.getInstance().getLocalRecommendThreshold();
    if (this.mInterestProgramDuration < i * 60);
    int m;
    int n;
    do
    {
      return;
      int j = SharedCfg.getInstance().getLocalRecommendProgramBeginMin();
      int k = SharedCfg.getInstance().getLocalRecommendProgramBeginMax();
      m = j * 3600;
      n = k * 3600;
    }
    while ((this.mInterestProgramBegin < m) || (this.mInterestProgramBegin > n) || (SharedCfg.getInstance().isLocalRecommendProgramIgnored(this.mInterestProgramName)));
    GlobalCfg.getInstance(InfoManager.getInstance().getContext()).setInterestCategoryId(this.mInterestCategoryId);
    GlobalCfg.getInstance(InfoManager.getInstance().getContext()).setInterestChannelId(this.mInterestChannelId);
    GlobalCfg.getInstance(InfoManager.getInstance().getContext()).setInterestProgramName(this.mInterestProgramName);
    GlobalCfg.getInstance(InfoManager.getInstance().getContext()).setInterestProgramDuration(this.mInterestProgramDuration);
    int i1 = SharedCfg.getInstance().getLocalRecommndProgramDelayed();
    GlobalCfg.getInstance(InfoManager.getInstance().getContext()).setInterestProgramBegin(i1 + this.mInterestProgramBegin);
    int i2 = Calendar.getInstance().get(6);
    GlobalCfg.getInstance(InfoManager.getInstance().getContext()).setInterestDayOfYear(i2);
    GlobalCfg.getInstance(InfoManager.getInstance().getContext()).setInterestShouted(false);
  }

  public void savePersonalContentToDB()
  {
    if (this.mPersonalCenterNode == null)
      return;
    this.mPersonalCenterNode.saveOtherToDB();
  }

  public void setFromType(FromType paramFromType)
  {
    if (this.lastFromType == FromType.UNKNOWN.ordinal());
    for (this.lastFromType = paramFromType.ordinal(); ; this.lastFromType = this.fromType)
    {
      this.fromType = paramFromType.ordinal();
      return;
    }
  }

  public void setHotPrograms(List<Object> paramList)
  {
    if (this.hotPrograms != null)
      this.hotPrograms.clear();
    this.hotPrograms = paramList;
  }

  public void setInfoUpdate(int paramInt)
  {
    dispatchInfoUpdateEvent(paramInt);
  }

  public void setPlayMode()
  {
    if (this.mPlayingNode == null);
    do
    {
      return;
      if (this.mPlayingNode.nodeName.equalsIgnoreCase("program"))
      {
        if (((ProgramNode)this.mPlayingNode).getCurrPlayStatus() == 3)
        {
          this.playMode = PlayMode.REPLAY;
          return;
        }
        if (this.hasOpenFM)
        {
          this.playMode = PlayMode.FMPLAY;
          return;
        }
        this.playMode = PlayMode.NORMALPLAY;
        return;
      }
      if (this.mPlayingNode.nodeName.equalsIgnoreCase("radiochannel"))
      {
        this.playMode = PlayMode.FMPLAY;
        return;
      }
    }
    while (!this.mPlayingNode.nodeName.equalsIgnoreCase("channel"));
    if (((ChannelNode)this.mPlayingNode).channelType == 0)
    {
      this.playMode = PlayMode.NORMALPLAY;
      return;
    }
    this.playMode = PlayMode.REPLAY;
  }

  public void setPlayMode(PlayMode paramPlayMode)
  {
    this.playMode = paramPlayMode;
  }

  public void setPlayingChannelNode(Node paramNode)
  {
    if ((paramNode == null) || (!paramNode.nodeName.equalsIgnoreCase("channel")));
    do
    {
      do
        return;
      while (this.mPlayingChannelNode == (ChannelNode)paramNode);
      this.mPlayingChannelNode = ((ChannelNode)paramNode);
    }
    while ((!this.mPlayingChannelNode.hasEmptyProgramSchedule()) || (this.mPlayingChannelNode.isDownloadChannel()));
    InfoManager.getInstance().loadProgramsScheduleNode(this.mPlayingChannelNode, null);
  }

  public void setPlayingChannelThumb(Bitmap paramBitmap, ChannelNode paramChannelNode)
  {
    if ((paramBitmap == null) || (paramChannelNode == null));
    while (this.mLastBitmapId == paramChannelNode.channelId)
      return;
    this.mLastBitmapId = paramChannelNode.channelId;
  }

  public void setPlayingNode(Node paramNode)
  {
    if (paramNode == null);
    do
    {
      do
      {
        return;
        if (paramNode != this.mPlayingNode)
          break;
      }
      while (!this.mPlayingNode.nodeName.equalsIgnoreCase("program"));
      PlayerAgent.getInstance().setPlayingChannel(((ProgramNode)this.mPlayingNode).getCategoryId(), ((ProgramNode)this.mPlayingNode).channelId, ((ProgramNode)this.mPlayingNode).id, ((ProgramNode)this.mPlayingNode).uniqueId, ((ProgramNode)this.mPlayingNode).channelType, getPlayingChannelThumb(), ((ProgramNode)this.mPlayingNode).getChannelName(), SpeedSensor.getInstance().getCurrSpeed());
      preload((ProgramNode)this.mPlayingNode, this.mPlayingChannelNode);
      saveToPlayList(paramNode);
    }
    while ((currentPlayMode() == PlayMode.PLAY_END_ADVERTISEMENT) || (currentPlayMode() == PlayMode.PLAY_FRONT_ADVERTISEMENT));
    setPlayMode();
    return;
    this.hasChangedChannel = false;
    boolean bool = isProgramNodeChanged(this.mPlayingNode, paramNode);
    if (bool)
    {
      if (this.mPlayingNode == null)
        break label524;
      if ((paramNode.nodeName.equalsIgnoreCase("program")) && (this.mPlayingNode.nodeName.equalsIgnoreCase("program")))
      {
        if (((ProgramNode)paramNode).channelType != ((ProgramNode)this.mPlayingNode).channelType)
          break label516;
        if ((((ProgramNode)paramNode).channelType == 1) && (((ProgramNode)paramNode).channelId != ((ProgramNode)this.mPlayingNode).channelId))
          this.hasChangedChannel = true;
      }
    }
    if (((!bool) || (!(paramNode.parent instanceof ChannelNode)) || (((ChannelNode)paramNode.parent).channelType != 0)) || (!bool))
      if ((paramNode.nextSibling == null) && (this.mPlayingNode.nextSibling != null))
        label312: if (this.mPlayingNode != null)
        {
          if (!this.mPlayingNode.nodeName.equalsIgnoreCase("program"))
            break label552;
          PlayerAgent.getInstance().setPlayingChannel(((ProgramNode)this.mPlayingNode).getCategoryId(), ((ProgramNode)this.mPlayingNode).channelId, ((ProgramNode)this.mPlayingNode).id, ((ProgramNode)this.mPlayingNode).uniqueId, ((ProgramNode)this.mPlayingNode).channelType, getPlayingChannelThumb(), ((ProgramNode)this.mPlayingNode).getChannelName(), SpeedSensor.getInstance().getCurrSpeed());
          sendUmeng();
          InfoManager.getInstance().root().mPersonalCenterNode.playHistoryNode.addPlayHistoryNode(this.mPlayingNode, getPlayingChannelThumb());
          preload((ProgramNode)this.mPlayingNode, this.mPlayingChannelNode);
          if ((currentPlayMode() != PlayMode.PLAY_END_ADVERTISEMENT) && (currentPlayMode() != PlayMode.PLAY_FRONT_ADVERTISEMENT))
            setPlayMode();
        }
    while (true)
    {
      if (bool)
        dispatchPlayInfoEvent(1);
      if (this.lastPlayMode != this.playMode)
      {
        this.lastPlayMode = this.playMode;
        dispatchPlayInfoEvent(0);
      }
      saveToPlayList(paramNode);
      return;
      label516: this.hasChangedChannel = true;
      break;
      label524: this.hasChangedChannel = true;
      break;
      this.mPlayingNode = paramNode;
      break label312;
      this.mPlayingNode = paramNode;
      setQuitTime();
      break label312;
      label552: if (this.mPlayingNode.nodeName.equalsIgnoreCase("channel"))
      {
        setPlayingChannelNode(this.mPlayingNode);
        PlayerAgent.getInstance().setPlayingChannel(((ChannelNode)this.mPlayingNode).categoryId, ((ChannelNode)this.mPlayingNode).channelId, 0, 0, ((ChannelNode)this.mPlayingNode).channelType, ((ChannelNode)this.mPlayingNode).getApproximativeThumb(), ((ChannelNode)this.mPlayingNode).title, SpeedSensor.getInstance().getCurrSpeed());
        if ((currentPlayMode() != PlayMode.PLAY_END_ADVERTISEMENT) && (currentPlayMode() != PlayMode.PLAY_FRONT_ADVERTISEMENT))
          setPlayMode();
      }
      else if (this.mPlayingNode.nodeName.equalsIgnoreCase("radiochannel"))
      {
        long l = System.currentTimeMillis();
        ProgramNode localProgramNode = ((RadioChannelNode)this.mPlayingNode).getCurrentPlayingProgramNode(l);
        if (localProgramNode != null)
          this.mPlayingNode = localProgramNode;
        if ((currentPlayMode() != PlayMode.PLAY_END_ADVERTISEMENT) && (currentPlayMode() != PlayMode.PLAY_FRONT_ADVERTISEMENT))
          setPlayMode();
      }
    }
  }

  public void setRecommendPrograms(List<Object> paramList)
  {
    if (this.recommendPrograms != null)
      this.recommendPrograms.clear();
    this.recommendPrograms = paramList;
  }

  public void setWillPlayNode(Node paramNode)
  {
    if (paramNode == null);
    do
    {
      return;
      boolean bool = isProgramNodeChanged(this.mPlayingNode, paramNode);
      this.mPlayingNode = paramNode;
      if (bool)
        dispatchPlayInfoEvent(1);
    }
    while (!this.mPlayingNode.nodeName.equalsIgnoreCase("program"));
    preload((ProgramNode)this.mPlayingNode, this.mPlayingChannelNode);
    PlayerAgent.getInstance().setPlayingChannel(((ProgramNode)this.mPlayingNode).getCategoryId(), ((ProgramNode)this.mPlayingNode).channelId, ((ProgramNode)this.mPlayingNode).id, ((ProgramNode)this.mPlayingNode).uniqueId, ((ProgramNode)this.mPlayingNode).channelType, getPlayingChannelThumb(), ((ProgramNode)this.mPlayingNode).getChannelName(), SpeedSensor.getInstance().getCurrSpeed());
    saveToPlayList(paramNode);
  }

  public void tuneFM(boolean paramBoolean)
  {
    if (paramBoolean)
    {
      PlayerAgent.getInstance().stopWithoutDispatchState();
      this.hasOpenFM = true;
      return;
    }
    this.hasOpenFM = false;
  }

  public void unRegisterInfoUpdateListener(int paramInt, IInfoUpdateEventListener paramIInfoUpdateEventListener)
  {
    String str = String.valueOf(paramInt);
    List localList;
    if ((paramIInfoUpdateEventListener != null) && (this.mapInfoUpdateEventListeners.containsKey(str)))
    {
      localList = (List)this.mapInfoUpdateEventListeners.get(str);
      if (localList == null);
    }
    for (int i = 0; ; i++)
      if (i < localList.size())
      {
        if (localList.get(i) == paramIInfoUpdateEventListener)
          localList.remove(i);
      }
      else
        return;
  }

  public void unRegisterSubscribeEventListener(int paramInt, IPlayInfoEventListener paramIPlayInfoEventListener)
  {
    String str = String.valueOf(paramInt);
    List localList;
    if ((paramIPlayInfoEventListener != null) && (this.mapPlayInfoEventListeners.containsKey(str)))
    {
      localList = (List)this.mapPlayInfoEventListeners.get(str);
      if (localList == null);
    }
    for (int i = 0; ; i++)
      if (i < localList.size())
      {
        if (localList.get(i) == paramIPlayInfoEventListener)
          localList.remove(i);
      }
      else
        return;
  }

  public static enum FromType
  {
    static
    {
      CATEGORY = new FromType("CATEGORY", 2);
      NOTIFICATION = new FromType("NOTIFICATION", 3);
      COLLECTION = new FromType("COLLECTION", 4);
      PLAYHISTORY = new FromType("PLAYHISTORY", 5);
      RESERVE = new FromType("RESERVE", 6);
      WHEEL = new FromType("WHEEL", 7);
      UNKNOWN = new FromType("UNKNOWN", 8);
      FromType[] arrayOfFromType = new FromType[9];
      arrayOfFromType[0] = RECOMMEND;
      arrayOfFromType[1] = SEARCH;
      arrayOfFromType[2] = CATEGORY;
      arrayOfFromType[3] = NOTIFICATION;
      arrayOfFromType[4] = COLLECTION;
      arrayOfFromType[5] = PLAYHISTORY;
      arrayOfFromType[6] = RESERVE;
      arrayOfFromType[7] = WHEEL;
      arrayOfFromType[8] = UNKNOWN;
    }
  }

  public static abstract interface IInfoUpdateEventListener
  {
    public static final int AUTO_SEEK = 1;
    public static final int PODCASTER_BANNER_CLOSE = 11;
    public static final int RECV_GROUP_INFO = 6;
    public static final int RECV_GROUP_USERS = 7;
    public static final int RECV_PROGRAM_BARRAGE = 8;
    public static final int RECV_USER_FOLLOWERS = 4;
    public static final int RECV_USER_FOLLOWINGS = 5;
    public static final int RECV_USER_INFO = 3;
    public static final int SEND_PROGRAM_BARRAGE = 9;
    public static final int UPDATED_FAV = 0;
    public static final int UPDATED_JD_ADV = 12;
    public static final int UPDATED_RECOMMEND_PLAYING = 2;
    public static final int UPDATE_PODCASTER_FOLLOW = 10;

    public abstract void onInfoUpdated(int paramInt);
  }

  public static abstract interface IPlayInfoEventListener
  {
    public static final int UPDATED_CURR_PLAYMODE = 0;
    public static final int UPDATED_CURR_PLAYPROGRM = 1;

    public abstract void onPlayInfoUpdated(int paramInt);
  }

  public static enum PlayMode
  {
    static
    {
      NORMALPLAY = new PlayMode("NORMALPLAY", 1);
      REPLAY = new PlayMode("REPLAY", 2);
      FMPLAY = new PlayMode("FMPLAY", 3);
      ALARMPLAY = new PlayMode("ALARMPLAY", 4);
      ALARM_PLAY_ONLINE = new PlayMode("ALARM_PLAY_ONLINE", 5);
      PLAY_END_ADVERTISEMENT = new PlayMode("PLAY_END_ADVERTISEMENT", 6);
      PLAY_FRONT_ADVERTISEMENT = new PlayMode("PLAY_FRONT_ADVERTISEMENT", 7);
      PlayMode[] arrayOfPlayMode = new PlayMode[8];
      arrayOfPlayMode[0] = UNKNOWN;
      arrayOfPlayMode[1] = NORMALPLAY;
      arrayOfPlayMode[2] = REPLAY;
      arrayOfPlayMode[3] = FMPLAY;
      arrayOfPlayMode[4] = ALARMPLAY;
      arrayOfPlayMode[5] = ALARM_PLAY_ONLINE;
      arrayOfPlayMode[6] = PLAY_END_ADVERTISEMENT;
      arrayOfPlayMode[7] = PLAY_FRONT_ADVERTISEMENT;
    }
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.qtradio.model.RootNode
 * JD-Core Version:    0.6.2
 */