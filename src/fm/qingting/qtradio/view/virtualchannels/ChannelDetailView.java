package fm.qingting.qtradio.view.virtualchannels;

import android.annotation.TargetApi;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.MeasureSpec;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnPullEventListener;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshBase.State;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import fm.qingting.framework.adapter.CustomizedAdapter;
import fm.qingting.framework.adapter.IAdapterIViewFactory;
import fm.qingting.framework.event.IEventHandler;
import fm.qingting.framework.utils.BitmapResourceCache;
import fm.qingting.framework.view.IView;
import fm.qingting.framework.view.ViewGroupViewImpl;
import fm.qingting.framework.view.ViewLayout;
import fm.qingting.qtradio.controller.ControllerManager;
import fm.qingting.qtradio.fm.PlayerAgent;
import fm.qingting.qtradio.helper.ChannelHelper;
import fm.qingting.qtradio.helper.ChannelHelper.IDataChangeObserver;
import fm.qingting.qtradio.log.LogModule;
import fm.qingting.qtradio.logger.QTLogger;
import fm.qingting.qtradio.manager.QtApiLevelManager;
import fm.qingting.qtradio.manager.RecorderManager;
import fm.qingting.qtradio.manager.SkinManager;
import fm.qingting.qtradio.model.ChannelNode;
import fm.qingting.qtradio.model.DownLoadInfoNode;
import fm.qingting.qtradio.model.DownLoadInfoNode.IDownloadInfoEventListener;
import fm.qingting.qtradio.model.InfoManager;
import fm.qingting.qtradio.model.InfoManager.DataExceptionStatus;
import fm.qingting.qtradio.model.InfoManager.ISubscribeEventListener;
import fm.qingting.qtradio.model.Node;
import fm.qingting.qtradio.model.PersonalCenterNode;
import fm.qingting.qtradio.model.PlayHistoryInfoNode;
import fm.qingting.qtradio.model.PlayHistoryNode;
import fm.qingting.qtradio.model.PlayedMetaData;
import fm.qingting.qtradio.model.PlayedMetaInfo;
import fm.qingting.qtradio.model.ProgramNode;
import fm.qingting.qtradio.model.RootNode;
import fm.qingting.qtradio.model.RootNode.IPlayInfoEventListener;
import fm.qingting.qtradio.view.LoadMoreFootView;
import fm.qingting.qtradio.view.MiniPlayerView;
import fm.qingting.utils.DateUtil;
import fm.qingting.utils.ListUtils;
import fm.qingting.utils.QTMSGManage;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ChannelDetailView extends ViewGroupViewImpl
  implements IEventHandler, RootNode.IPlayInfoEventListener, InfoManager.ISubscribeEventListener, DownLoadInfoNode.IDownloadInfoEventListener, ChannelHelper.IDataChangeObserver
{
  private static List<Integer> userCancelChannels = new ArrayList();
  private final ViewLayout coverLayout = this.standardLayout.createChildLT(720, 530, 0, 0, ViewLayout.SCALE_FLAG_SLTCW);
  private ChannelDetailTagView listTitleView;
  private CustomizedAdapter mAdapter;
  private ChannelNode mChannelNode;
  private ChannelDetailCoverView mCoverView;
  private IAdapterIViewFactory mFactory;
  private boolean mFirstTime = true;
  private LoadMoreFootView mFooterView;
  private RotateLoadingLayout mHeaderLayout;
  private int mLastOffset = 0;
  private LinearLayout mLinearLayout;
  private PullToRefreshListView mListView;
  private MiniPlayerView mMiniView;
  private PlayedMetaData mRecentIncompleteProgram;
  private PlayHistoryNode mRecentNode;
  private final ViewLayout miniLayout = this.standardLayout.createChildLT(720, 110, 0, 0, ViewLayout.SCALE_FLAG_SLTCW);
  private final ViewLayout naviLayout = this.standardLayout.createChildLT(720, 114, 0, 0, ViewLayout.SCALE_FLAG_SLTCW);
  private final ViewLayout operateLayout = this.standardLayout.createChildLT(720, 101, 0, 1, ViewLayout.SCALE_FLAG_SLTCW);
  private final ViewLayout standardLayout = ViewLayout.createViewLayoutWithBoundsLT(720, 1200, 720, 1200, 0, 0, ViewLayout.FILL);
  private ChannelDetailTagView uploadTitleView;
  private UploadVoiceItemView uploadVoiceItemView;

  public ChannelDetailView(Context paramContext)
  {
    super(paramContext);
    setBackgroundColor(SkinManager.getBackgroundColor_item());
    final int i = hashCode();
    this.uploadTitleView = new ChannelDetailTagView(paramContext);
    this.uploadTitleView.setTagName("待上传录音");
    this.listTitleView = new ChannelDetailTagView(paramContext);
    this.listTitleView.setTagName("节目列表");
    this.uploadVoiceItemView = new UploadVoiceItemView(paramContext, i);
    this.listTitleView.setEventHandler(this);
    this.mLinearLayout = ((LinearLayout)LayoutInflater.from(paramContext).inflate(2130903058, null));
    this.mListView = ((PullToRefreshListView)this.mLinearLayout.findViewById(2131230731));
    this.mHeaderLayout = new RotateLoadingLayout(paramContext);
    this.mListView.addListHeaderView(this.mHeaderLayout);
    this.mListView.addListHeaderView(this.listTitleView);
    this.mFooterView = new LoadMoreFootView(paramContext);
    this.mFooterView.setBackgroundColor(SkinManager.getBackgroundColor_item());
    this.mListView.addListFooterView(this.mFooterView);
    this.mListView.setSelector(17170445);
    addView(this.mLinearLayout);
    this.mListView.setOnScrollListener(new AbsListView.OnScrollListener()
    {
      public void onScroll(AbsListView paramAnonymousAbsListView, int paramAnonymousInt1, int paramAnonymousInt2, int paramAnonymousInt3)
      {
        if ((paramAnonymousInt1 == 0) || (paramAnonymousInt1 == 1))
        {
          View localView1 = paramAnonymousAbsListView.getChildAt(0);
          if (localView1 != null)
          {
            int i = localView1.getTop();
            int j = ChannelDetailView.this.getMaxTranslationY();
            if ((i <= 0) && (i >= j))
              ChannelDetailView.this.moveHead(i);
          }
          if (paramAnonymousInt2 != paramAnonymousInt3)
            break label119;
          ChannelDetailView.this.hideFooterView();
        }
        label119: 
        while ((ChannelDetailView.this.mFooterView.isAll()) || (ChannelDetailView.this.mFooterView.isLoading()) || (paramAnonymousInt1 + paramAnonymousInt2 < paramAnonymousInt3))
        {
          return;
          View localView2 = paramAnonymousAbsListView.getChildAt(0);
          if (localView2 == null)
            break;
          int k = localView2.getTop();
          int m = ChannelDetailView.this.getMaxTranslationY();
          if (k < m)
            break;
          ChannelDetailView.this.moveHead(m);
          break;
        }
        ChannelDetailView.this.mFooterView.showLoad();
        ChannelDetailView.this.loadMore(paramAnonymousInt3 - 1);
      }

      public void onScrollStateChanged(AbsListView paramAnonymousAbsListView, int paramAnonymousInt)
      {
      }
    });
    this.mFactory = new IAdapterIViewFactory()
    {
      public IView createView(int paramAnonymousInt)
      {
        return new ChannelDetailItemView(ChannelDetailView.this.getContext(), i);
      }
    };
    this.mAdapter = new CustomizedAdapter(new ArrayList(), this.mFactory);
    this.mListView.setAdapter(this.mAdapter);
    this.mListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener()
    {
      public void onRefresh(PullToRefreshBase<ListView> paramAnonymousPullToRefreshBase)
      {
        InfoManager.getInstance().reloadVirtualProgramsSchedule(ChannelDetailView.this.mChannelNode, ChannelDetailView.this);
      }
    });
    this.mListView.setOnPullEventListener(new PullToRefreshBase.OnPullEventListener()
    {
      public void onPullEvent(PullToRefreshBase<ListView> paramAnonymousPullToRefreshBase, PullToRefreshBase.State paramAnonymousState, PullToRefreshBase.Mode paramAnonymousMode)
      {
        switch (ChannelDetailView.5.$SwitchMap$com$handmark$pulltorefresh$library$PullToRefreshBase$State[paramAnonymousState.ordinal()])
        {
        default:
          return;
        case 1:
          ChannelDetailView.this.mHeaderLayout.reset();
          return;
        case 2:
          ChannelDetailView.this.mHeaderLayout.pullToRefresh();
          return;
        case 3:
          ChannelDetailView.this.mHeaderLayout.releaseToRefresh();
          return;
        case 4:
        case 5:
        }
        ChannelDetailView.this.mHeaderLayout.refreshing();
      }
    });
    this.mCoverView = new ChannelDetailCoverView(paramContext);
    addView(this.mCoverView);
    this.mMiniView = new MiniPlayerView(paramContext);
    addView(this.mMiniView);
    InfoManager.getInstance().root().registerSubscribeEventListener(this, 1);
    InfoManager.getInstance().root().mDownLoadInfoNode.registerListener(this);
  }

  private int getMaxTranslationY()
  {
    return this.naviLayout.height + this.operateLayout.height + this.operateLayout.getTop() - this.coverLayout.height;
  }

  private void handleAutoPlay(ChannelNode paramChannelNode)
  {
    if (paramChannelNode == null);
    List localList;
    do
    {
      Node localNode2;
      do
      {
        do
          return;
        while ((!paramChannelNode.autoPlay) || (paramChannelNode.hasEmptyProgramSchedule()));
        Node localNode1 = InfoManager.getInstance().root().getCurrentPlayingNode();
        localNode2 = null;
        if (localNode1 != null)
        {
          boolean bool = localNode1.nodeName.equalsIgnoreCase("program");
          localNode2 = null;
          if (bool)
            localNode2 = localNode1.parent;
        }
      }
      while ((localNode2 != null) && (localNode2.nodeName.equalsIgnoreCase("channel")) && (((ChannelNode)localNode2).channelId == paramChannelNode.channelId));
      localList = paramChannelNode.getAllLstProgramNode();
    }
    while ((localList == null) || (localList.size() <= 0) || (PlayerAgent.getInstance().isPlaying()));
    PlayerAgent.getInstance().play((Node)localList.get(0));
  }

  private void hideFooterView()
  {
    this.mFooterView.hideLoad();
  }

  private void initData()
  {
    if (this.mChannelNode == null)
      return;
    if (this.mListView != null)
      this.mListView.setSelection(0);
    if (this.mChannelNode.hasEmptyProgramSchedule())
    {
      this.mListView.setRefreshing();
      return;
    }
    setProgramList(this.mChannelNode.getAllLstProgramNode());
  }

  private void loadMore(int paramInt)
  {
    InfoManager.getInstance().loadProgramsScheduleNode(this.mChannelNode, this);
  }

  @TargetApi(11)
  private void moveHead(int paramInt)
  {
    if (this.mLastOffset == paramInt)
      return;
    this.mLastOffset = paramInt;
    if (QtApiLevelManager.isApiLevelSupported(11))
    {
      this.mCoverView.setTranslationY(paramInt);
      return;
    }
    this.mCoverView.layout(0, paramInt, this.standardLayout.width, paramInt + this.coverLayout.height);
  }

  private void sendProgramsShowLog(List<ProgramNode> paramList, ChannelNode paramChannelNode)
  {
    if ((paramList == null) || (paramChannelNode == null));
    String str;
    do
    {
      do
        return;
      while (paramChannelNode.channelType == 0);
      str = QTLogger.getInstance().buildProgramsShowLog(paramList, paramChannelNode.categoryId, paramChannelNode.categoryId, paramChannelNode.channelId, InfoManager.getInstance().getProgramPageSize(), paramChannelNode.isNovelChannel());
    }
    while (str == null);
    LogModule.getInstance().send("ProgramsShowV6", str);
  }

  private boolean setProgramList(List<ProgramNode> paramList)
  {
    if (paramList == null)
      return false;
    List localList = PlayedMetaInfo.getInstance().getPlayedMetadata();
    int i;
    if ((this.mChannelNode != null) && (userCancelChannels.contains(Integer.valueOf(this.mChannelNode.channelId))))
    {
      i = 1;
      if (ControllerManager.getInstance().getChannelSource() != 1)
        break label190;
    }
    label190: for (int j = 1; ; j = 0)
    {
      this.mRecentIncompleteProgram = null;
      if ((i != 0) || (j != 0) || (localList == null) || (localList.size() <= 0))
        break label196;
      Iterator localIterator2 = localList.iterator();
      while (localIterator2.hasNext())
      {
        PlayedMetaData localPlayedMetaData = (PlayedMetaData)localIterator2.next();
        if ((this.mChannelNode.channelId == localPlayedMetaData.channelId) && (localPlayedMetaData.position > 5) && (localPlayedMetaData.position < -5 + localPlayedMetaData.duration) && ((this.mRecentIncompleteProgram == null) || (localPlayedMetaData.playedTime > this.mRecentIncompleteProgram.playedTime)))
          this.mRecentIncompleteProgram = localPlayedMetaData;
      }
      i = 0;
      break;
    }
    label196: this.mRecentNode = null;
    if (this.mRecentIncompleteProgram != null)
    {
      Iterator localIterator1 = InfoManager.getInstance().root().mPersonalCenterNode.playHistoryNode.getPlayHistoryNodes().iterator();
      while (localIterator1.hasNext())
      {
        PlayHistoryNode localPlayHistoryNode = (PlayHistoryNode)localIterator1.next();
        if (((ProgramNode)localPlayHistoryNode.playNode).id == this.mRecentIncompleteProgram.programId)
          this.mRecentNode = localPlayHistoryNode;
      }
    }
    ArrayList localArrayList = new ArrayList();
    int k;
    if (paramList.size() > 0)
    {
      sendProgramsShowLog(paramList, this.mChannelNode);
      Node localNode = InfoManager.getInstance().root().getCurrentPlayingNode();
      if ((localNode != null) && (localNode.nodeName.equalsIgnoreCase("program")))
      {
        k = 0;
        if (k < paramList.size())
          if (((ProgramNode)localNode).id == ((ProgramNode)paramList.get(k)).id)
          {
            if ((this.mRecentNode != null) && (((ProgramNode)localNode).id == ((ProgramNode)this.mRecentNode.playNode).id))
              this.mRecentNode = null;
            if ((localNode.prevSibling == null) && (localNode.nextSibling == null))
            {
              localNode.prevSibling = ((ProgramNode)paramList.get(k)).prevSibling;
              localNode.nextSibling = ((ProgramNode)paramList.get(k)).nextSibling;
            }
            label468: handleAutoPlay(this.mChannelNode);
          }
      }
    }
    while (true)
    {
      this.mListView.onRefreshComplete();
      this.mFooterView.hideLoad();
      if (this.mRecentNode != null)
      {
        QTMSGManage.getInstance().sendStatistcsMessage("resumerecent_display");
        localArrayList.add(this.mRecentNode);
      }
      localArrayList.addAll(paramList);
      this.mAdapter.setData(ListUtils.convertToObjectList(localArrayList));
      if ((this.mFirstTime) && (k != -1) && (this.mListView != null))
      {
        this.mFirstTime = false;
        this.mListView.setSelection(k);
      }
      return true;
      k++;
      break;
      k = -1;
      break label468;
      k = -1;
    }
  }

  public void close(boolean paramBoolean)
  {
    this.mMiniView.destroy();
    this.mCoverView.close(paramBoolean);
    InfoManager.getInstance().root().unRegisterSubscribeEventListener(1, this);
    BitmapResourceCache.getInstance().clearResourceCacheOfOne(this, 0);
    InfoManager.getInstance().root().mDownLoadInfoNode.unregisterListener(this);
    super.close(paramBoolean);
  }

  public Object getValue(String paramString, Object paramObject)
  {
    if ((paramString.equalsIgnoreCase("list")) && (this.mAdapter != null))
      return this.mAdapter.getData();
    return super.getValue(paramString, paramObject);
  }

  public void onChannelNodeInfoUpdate(ChannelNode paramChannelNode)
  {
    if ((this.mChannelNode != null) && (this.mChannelNode.channelId == paramChannelNode.channelId))
    {
      this.mChannelNode.updateAllInfo(paramChannelNode);
      this.mCoverView.update("setData", this.mChannelNode);
      dispatchActionEvent("resetNavi", null);
    }
  }

  public void onDownLoadInfoUpdated(int paramInt, Node paramNode)
  {
    if ((paramInt == 8) || (paramInt == 1) || (paramInt == 4))
    {
      if ((this.mChannelNode == null) || (paramNode == null) || (paramNode.parent == null) || (!paramNode.parent.nodeName.equalsIgnoreCase("channel")))
        break label78;
      if (this.mChannelNode.channelId == ((ChannelNode)paramNode.parent).channelId)
        this.mAdapter.notifyDataSetChanged();
    }
    label78: 
    while ((this.mChannelNode == null) || (paramNode == null) || (!paramNode.nodeName.equalsIgnoreCase("program")) || (this.mChannelNode.channelId != ((ProgramNode)paramNode).channelId))
      return;
    this.mAdapter.notifyDataSetChanged();
  }

  public void onEvent(Object paramObject1, String paramString, Object paramObject2)
  {
    dispatchActionEvent(paramString, paramObject2);
  }

  protected void onLayout(boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    this.mLinearLayout.layout(0, this.operateLayout.height + this.naviLayout.height, this.standardLayout.width, this.standardLayout.height - this.miniLayout.height);
    this.coverLayout.layoutView(this.mCoverView);
    this.mMiniView.layout(0, this.standardLayout.height - this.miniLayout.height, this.standardLayout.width, this.standardLayout.height);
  }

  protected void onMeasure(int paramInt1, int paramInt2)
  {
    int i = View.MeasureSpec.getSize(paramInt1);
    int j = View.MeasureSpec.getSize(paramInt2);
    this.standardLayout.scaleToBounds(i, j);
    this.miniLayout.scaleToBounds(this.standardLayout);
    this.coverLayout.scaleToBounds(this.standardLayout);
    this.operateLayout.scaleToBounds(this.standardLayout);
    this.naviLayout.scaleToBounds(this.standardLayout);
    this.miniLayout.measureView(this.mMiniView);
    this.coverLayout.measureView(this.mCoverView);
    this.mLinearLayout.measure(this.standardLayout.getWidthMeasureSpec(), View.MeasureSpec.makeMeasureSpec(this.standardLayout.height - this.miniLayout.height - this.operateLayout.height - this.naviLayout.height, 1073741824));
    setMeasuredDimension(i, j);
  }

  public void onNotification(String paramString)
  {
    if (paramString.equalsIgnoreCase("RPS"))
      if (!setProgramList(this.mChannelNode.getAllLstProgramNode()))
        InfoManager.getInstance().registerSubscribeEventListener(this, "RPS");
    while (!paramString.equalsIgnoreCase("RECV_RELOAD_PROGRAMS_SCHEDULE"))
      return;
    setProgramList(this.mChannelNode.reloadAllLstProgramNode());
    this.mListView.onRefreshComplete();
  }

  public void onPlayInfoUpdated(int paramInt)
  {
    if ((paramInt == 1) && (this.mAdapter != null))
      this.mAdapter.notifyDataSetChanged();
  }

  public void onRecvDataException(String paramString, InfoManager.DataExceptionStatus paramDataExceptionStatus)
  {
    Log.e("qtradio", paramString + paramDataExceptionStatus);
  }

  public void update(String paramString, Object paramObject)
  {
    ChannelNode localChannelNode;
    if (paramString.equalsIgnoreCase("setData"))
    {
      localChannelNode = (ChannelNode)paramObject;
      if (this.mChannelNode != localChannelNode);
    }
    do
    {
      do
      {
        return;
        this.mChannelNode = localChannelNode;
        ChannelHelper.getInstance().addObserver(this.mChannelNode.channelId, this);
        InfoManager.getInstance().updateViewTime(String.valueOf(this.mChannelNode.channelId), DateUtil.getCurrentSeconds());
        this.mCoverView.update("setData", paramObject);
        initData();
        return;
        if (!paramString.equalsIgnoreCase("refreshUploadView"))
          break;
      }
      while (!QtApiLevelManager.isApiLevelSupported(19));
      this.mListView.removeListHeaderView(this.uploadTitleView);
      this.mListView.removeListHeaderView(this.uploadVoiceItemView);
      this.mListView.removeListHeaderView(this.listTitleView);
      File localFile = new File(RecorderManager.getCachedFilePath((String)paramObject));
      if ((localFile == null) || (!localFile.exists()) || (RecorderManager.getInstance().isUploading()))
      {
        this.mListView.addListHeaderView(this.listTitleView);
        return;
      }
      this.uploadVoiceItemView.update("setTagId", paramObject);
      this.mListView.addListHeaderView(this.uploadTitleView);
      this.mListView.addListHeaderView(this.uploadVoiceItemView);
      this.mListView.addListHeaderView(this.listTitleView);
      return;
      if (paramString.equalsIgnoreCase("closerecentplay"))
      {
        if (this.mChannelNode != null)
          userCancelChannels.add(Integer.valueOf(this.mChannelNode.channelId));
        initData();
        return;
      }
    }
    while (!paramString.equalsIgnoreCase("setpodcasterinfo"));
    this.mCoverView.update(paramString, paramObject);
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.qtradio.view.virtualchannels.ChannelDetailView
 * JD-Core Version:    0.6.2
 */