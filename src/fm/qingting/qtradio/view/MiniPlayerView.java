package fm.qingting.qtradio.view;

import android.content.Context;
import android.view.View.MeasureSpec;
import fm.qingting.framework.manager.EventDispacthManager;
import fm.qingting.framework.manager.EventDispacthManager.IActionEventHandler;
import fm.qingting.framework.utils.BitmapResourceCache;
import fm.qingting.framework.view.ButtonViewElement;
import fm.qingting.framework.view.ImageViewElement;
import fm.qingting.framework.view.QtView;
import fm.qingting.framework.view.TextViewElement;
import fm.qingting.framework.view.ViewElement;
import fm.qingting.framework.view.ViewElement.OnElementClickListener;
import fm.qingting.framework.view.ViewLayout;
import fm.qingting.qtradio.controller.ControllerManager;
import fm.qingting.qtradio.fm.IMediaEventListener;
import fm.qingting.qtradio.fm.PlayStatus;
import fm.qingting.qtradio.fm.PlayerAgent;
import fm.qingting.qtradio.fmdriver.FMManager;
import fm.qingting.qtradio.manager.SkinManager;
import fm.qingting.qtradio.model.ChannelNode;
import fm.qingting.qtradio.model.InfoManager;
import fm.qingting.qtradio.model.InfoManager.DataExceptionStatus;
import fm.qingting.qtradio.model.InfoManager.ISubscribeEventListener;
import fm.qingting.qtradio.model.Node;
import fm.qingting.qtradio.model.PersonalCenterNode;
import fm.qingting.qtradio.model.PlayHistoryInfoNode;
import fm.qingting.qtradio.model.ProgramNode;
import fm.qingting.qtradio.model.RadioChannelNode;
import fm.qingting.qtradio.model.RootNode;
import fm.qingting.qtradio.model.RootNode.IPlayInfoEventListener;
import fm.qingting.qtradio.model.RootNode.PlayMode;
import fm.qingting.qtradio.view.playview.AbsPlayButtonElement.State;
import fm.qingting.qtradio.wo.WoApiRequest;
import fm.qingting.qtradio.wo.WoApiRequest.OnOpenListener;
import fm.qingting.utils.OnPlayProcessListener;
import fm.qingting.utils.PlayProcessSyncUtil;
import fm.qingting.utils.QTMSGManage;
import java.util.List;

public class MiniPlayerView extends QtView
  implements IMediaEventListener, EventDispacthManager.IActionEventHandler, RootNode.IPlayInfoEventListener, OnPlayProcessListener, ViewElement.OnElementClickListener, WoApiRequest.OnOpenListener, InfoManager.ISubscribeEventListener
{
  private final ViewLayout channelTitleLayout = this.standardLayout.createChildLT(400, 45, 112, 55, ViewLayout.SCALE_FLAG_SLTCW);
  private final ViewLayout historyLayout = this.standardLayout.createChildLT(110, 110, 605, 0, ViewLayout.SCALE_FLAG_SLTCW);
  private ButtonViewElement mBg;
  private TextViewElement mChannelTitleTe;
  private boolean mHasTop;
  private ImageViewElement mHistoryElement;
  private ButtonViewElement mMenuElement;
  private PlayButtonElement mPlayElement;
  private ProcessBarElement mProcessBarElement;
  private TextViewElement mProgramTitleTe;
  private ImageViewElement mStateElement;
  private ImageViewElement mTrangleElement;
  private final ViewLayout menuLayout = this.standardLayout.createChildLT(110, 110, 0, 0, ViewLayout.SCALE_FLAG_SLTCW);
  private final ViewLayout playLayout = this.standardLayout.createChildLT(110, 110, 500, 0, ViewLayout.SCALE_FLAG_SLTCW);
  private final ViewLayout processLayout = this.standardLayout.createChildLT(720, 5, 0, 0, ViewLayout.SCALE_FLAG_SLTCW);
  private final ViewLayout programTitleLayout = this.standardLayout.createChildLT(400, 45, 112, 15, ViewLayout.SCALE_FLAG_SLTCW);
  private final ViewLayout standardLayout = ViewLayout.createViewLayoutWithBoundsLT(720, 110, 720, 110, 0, 0, ViewLayout.FILL);
  private final ViewLayout stateLayout = this.standardLayout.createChildLT(50, 26, 112, 20, ViewLayout.SCALE_FLAG_SLTCW);
  private final ViewLayout trangleLayout = this.standardLayout.createChildLT(20, 10, 652, 0, ViewLayout.SCALE_FLAG_SLTCW);

  public MiniPlayerView(Context paramContext)
  {
    super(paramContext);
    int i = hashCode();
    setBackgroundColor(SkinManager.getMiniplayerBgColor());
    this.mBg = new ButtonViewElement(paramContext);
    this.mBg.setBackgroundColor(1275068416, 0);
    addElement(this.mBg);
    this.mBg.setOnElementClickListener(this);
    this.mProcessBarElement = new ProcessBarElement(paramContext);
    addElement(this.mProcessBarElement);
    this.mMenuElement = new ButtonViewElement(paramContext);
    this.mMenuElement.setBackground(2130837836, 2130837834);
    addElement(this.mMenuElement, i);
    this.mMenuElement.setOnElementClickListener(this);
    this.mPlayElement = new PlayButtonElement(paramContext);
    addElement(this.mPlayElement, i);
    this.mPlayElement.setOnElementClickListener(this);
    this.mHistoryElement = new ImageViewElement(paramContext);
    this.mHistoryElement.setImageRes(2130837842);
    addElement(this.mHistoryElement, i);
    this.mHistoryElement.setOnElementClickListener(this);
    this.mTrangleElement = new ImageViewElement(paramContext);
    this.mTrangleElement.setImageRes(2130837845);
    addElement(this.mTrangleElement, i);
    List localList = InfoManager.getInstance().root().mPersonalCenterNode.playHistoryNode.getPlayHistoryNodes();
    if ((localList != null) && (localList.size() > 1) && (ControllerManager.getInstance().getLastViewController() == null) && (InfoManager.getInstance().getTopHistory()))
    {
      this.mTrangleElement.setVisible(0);
      this.mHasTop = true;
      this.mStateElement = new ImageViewElement(paramContext);
      this.mStateElement.setImageRes(2130837844);
      this.mProgramTitleTe = new TextViewElement(paramContext);
      this.mProgramTitleTe.setMaxLineLimit(1);
      this.mProgramTitleTe.setColor(-1);
      addElement(this.mProgramTitleTe);
      this.mChannelTitleTe = new TextViewElement(paramContext);
      this.mChannelTitleTe.setMaxLineLimit(1);
      this.mChannelTitleTe.setColor(-1);
      addElement(this.mChannelTitleTe);
      PlayerAgent.getInstance().addMediaEventListener(this);
      InfoManager.getInstance().root().registerSubscribeEventListener(this, 1);
      InfoManager.getInstance().registerSubscribeEventListener(this, "RPS");
      PlayProcessSyncUtil.getInstance().addListener(this);
      refreshPlayingInfo();
      changeViewStateByPlayState(getCurrentPlayStatus());
      if (!WoApiRequest.hasOpen())
        break label650;
      this.mStateElement.setImageRes(2130838003);
    }
    while (true)
    {
      EventDispacthManager.getInstance().addListener(this);
      return;
      this.mTrangleElement.setVisible(4);
      this.mHasTop = false;
      break;
      label650: WoApiRequest.addListener(this);
    }
  }

  private void changeViewStateByPlayState(int paramInt)
  {
    if ((paramInt == 0) || (paramInt == 1))
    {
      this.mPlayElement.setState(AbsPlayButtonElement.State.PLAY);
      return;
    }
    if (paramInt == 8192)
    {
      this.mPlayElement.setState(AbsPlayButtonElement.State.ERROR);
      return;
    }
    if ((paramInt == 4098) || (paramInt == 4101))
    {
      this.mPlayElement.setState(AbsPlayButtonElement.State.BUFFER);
      return;
    }
    this.mPlayElement.setState(AbsPlayButtonElement.State.PAUSE);
  }

  private int getCurrentPlayStatus()
  {
    int i = PlayerAgent.getInstance().getCurrentPlayStatus();
    if (InfoManager.getInstance().root().currentPlayMode() == RootNode.PlayMode.FMPLAY)
      if (!InfoManager.getInstance().root().isOpenFm());
    do
    {
      return 4096;
      return 0;
      if ((i == 30583) || (i == 0) || (i == 1))
        return 0;
    }
    while (i == 4096);
    return i;
  }

  private void playNext()
  {
    Node localNode1 = InfoManager.getInstance().root().getCurrentPlayingNode();
    if ((localNode1 != null) && (localNode1.nextSibling != null))
      if (localNode1.nextSibling.nodeName.equalsIgnoreCase("program"))
      {
        if (((ProgramNode)localNode1.nextSibling).getCurrPlayStatus() == 2)
          return;
        PlayerAgent.getInstance().play(localNode1.nextSibling);
      }
    while (true)
    {
      QTMSGManage.getInstance().sendStatistcsMessage("miniplayerclick", "next");
      return;
      if (localNode1 != null)
      {
        ChannelNode localChannelNode = InfoManager.getInstance().root().getCurrentPlayingChannelNode();
        if ((localChannelNode != null) && (!localChannelNode.hasEmptyProgramSchedule()) && (localChannelNode.getAllLstProgramNode() != null) && (localNode1.nodeName.equalsIgnoreCase("program")))
        {
          ProgramNode localProgramNode = localChannelNode.getProgramNodeByProgramId(((ProgramNode)localNode1).id);
          if ((localProgramNode != null) && (localProgramNode.nextSibling != null))
          {
            PlayerAgent.getInstance().play(localProgramNode.nextSibling);
          }
          else
          {
            Node localNode2 = (Node)localChannelNode.getAllLstProgramNode().get(0);
            if (localNode2 != null)
              PlayerAgent.getInstance().play(localNode2);
          }
        }
      }
    }
  }

  private void playOrStop()
  {
    if (InfoManager.getInstance().root().currentPlayMode() == RootNode.PlayMode.FMPLAY)
      if (InfoManager.getInstance().root().isOpenFm())
      {
        PlayerAgent.getInstance().dispatchPlayStateInFM(0);
        FMManager.getInstance().turnOff();
        InfoManager.getInstance().root().tuneFM(false);
        QTMSGManage.getInstance().sendStatistcsMessage("miniplayerclick", "stop");
      }
    Node localNode1;
    do
    {
      Node localNode4;
      do
      {
        Node localNode3;
        do
        {
          do
          {
            return;
            QTMSGManage.getInstance().sendStatistcsMessage("miniplayerclick", "play");
            localNode3 = InfoManager.getInstance().root().getCurrentPlayingNode();
          }
          while (localNode3 == null);
          if (localNode3.nodeName.equalsIgnoreCase("radiochannel"))
          {
            PlayerAgent.getInstance().startFM((RadioChannelNode)localNode3);
            return;
          }
        }
        while (!localNode3.nodeName.equalsIgnoreCase("program"));
        localNode4 = localNode3.parent;
      }
      while ((localNode4 == null) || (!localNode4.nodeName.equalsIgnoreCase("radiochannel")));
      PlayerAgent.getInstance().startFM((RadioChannelNode)localNode4);
      return;
      if (PlayerAgent.getInstance().isPlaying())
      {
        PlayerAgent.getInstance().stop();
        QTMSGManage.getInstance().sendStatistcsMessage("miniplayerclick", "stop");
        return;
      }
      QTMSGManage.getInstance().sendStatistcsMessage("miniplayerclick", "play");
      localNode1 = InfoManager.getInstance().root().getCurrentPlayingNode();
    }
    while (localNode1 == null);
    if (InfoManager.getInstance().root().currentPlayMode() == RootNode.PlayMode.FMPLAY)
    {
      int i;
      if (localNode1.nodeName.equalsIgnoreCase("radiochannel"))
        i = Integer.valueOf(((RadioChannelNode)localNode1).freq).intValue();
      while (true)
      {
        if (i != 0)
          FMManager.getInstance().tune(i);
        PlayerAgent.getInstance().dispatchPlayStateInFM(4096);
        InfoManager.getInstance().root().setPlayingNode(localNode1);
        InfoManager.getInstance().root().tuneFM(true);
        return;
        boolean bool1 = localNode1.nodeName.equalsIgnoreCase("program");
        i = 0;
        if (bool1)
        {
          Node localNode2 = localNode1.parent;
          i = 0;
          if (localNode2 != null)
          {
            boolean bool2 = localNode1.parent.nodeName.equalsIgnoreCase("radiochannel");
            i = 0;
            if (bool2)
              i = Integer.valueOf(((RadioChannelNode)localNode1.parent).freq).intValue();
          }
        }
      }
    }
    PlayerAgent.getInstance().play(localNode1);
  }

  private void refreshPlayingInfo()
  {
    ChannelNode localChannelNode1 = InfoManager.getInstance().root().getCurrentPlayingChannelNode();
    Node localNode = InfoManager.getInstance().root().getCurrentPlayingNode();
    String str1 = "节目加载中";
    Object localObject1 = "节目加载中";
    if (localChannelNode1 != null)
      str1 = localChannelNode1.title;
    String str2;
    if (localNode == null)
    {
      if (ControllerManager.getInstance().playedLastChannel())
      {
        localObject1 = "节目加载中";
        str2 = str1;
        this.mProgramTitleTe.setText((String)localObject1);
        this.mChannelTitleTe.setText(str2);
      }
      return;
    }
    ProgramNode localProgramNode2;
    if (localNode.nodeName.equalsIgnoreCase("channel"))
    {
      str1 = ((ChannelNode)localNode).title;
      if (!((ChannelNode)localNode).isLiveChannel())
        break label273;
      localProgramNode2 = ((ChannelNode)localNode).getProgramNodeByTime(System.currentTimeMillis());
      if (localProgramNode2 == null)
        break label279;
    }
    label273: label279: for (Object localObject2 = localProgramNode2.title; ; localObject2 = localObject1)
    {
      localObject1 = localObject2;
      str2 = str1;
      break;
      if (localNode.nodeName.equalsIgnoreCase("program"))
      {
        ChannelNode localChannelNode2 = InfoManager.getInstance().root().getCurrentPlayingChannelNode();
        localObject1 = ((ProgramNode)localNode).title;
        if (localNode.nextSibling != null)
        {
          if ((!localNode.nextSibling.nodeName.equalsIgnoreCase("program")) || (((ProgramNode)localNode.nextSibling).getCurrPlayStatus() == 2))
            break label273;
          str2 = str1;
          break;
        }
        if ((localChannelNode2 != null) && (!localChannelNode2.hasEmptyProgramSchedule()) && (localChannelNode2.getAllLstProgramNode() != null))
        {
          ProgramNode localProgramNode1 = localChannelNode2.getProgramNodeByProgramId(((ProgramNode)localNode).id);
          if ((localProgramNode1 != null) && (localProgramNode1.nextSibling != null))
          {
            str2 = str1;
            break;
          }
        }
      }
      str2 = str1;
      break;
    }
  }

  private void showSchedule()
  {
    EventDispacthManager.getInstance().dispatchAction("showSchedule", InfoManager.getInstance().root().getCurrentPlayingNode());
    QTMSGManage.getInstance().sendStatistcsMessage("miniplayerclick", "menu");
  }

  private void stop()
  {
    if ((InfoManager.getInstance().root().currentPlayMode() == RootNode.PlayMode.ALARMPLAY) || (InfoManager.getInstance().root().currentPlayMode() == RootNode.PlayMode.ALARM_PLAY_ONLINE))
    {
      PlayerAgent.getInstance().stop();
      return;
    }
    PlayerAgent.getInstance().stop();
  }

  private void toPlayView()
  {
    EventDispacthManager.getInstance().dispatchAction("toPlayView", null);
    QTMSGManage.getInstance().sendStatistcsMessage("miniplayerclick", "other");
  }

  public void destroy()
  {
    PlayProcessSyncUtil.getInstance().removeListener(this);
    InfoManager.getInstance().root().unRegisterSubscribeEventListener(1, this);
    PlayerAgent.getInstance().removeMediaEventListener(this);
    BitmapResourceCache.getInstance().clearResourceCacheOfOne(this, 0);
  }

  public void onAction(String paramString, Object paramObject)
  {
    if (paramString.equalsIgnoreCase("hideMiniplayerTrangle"))
      this.mTrangleElement.setVisible(4);
  }

  public void onElementClick(ViewElement paramViewElement)
  {
    this.mTrangleElement.setVisible(4);
    if (paramViewElement == this.mPlayElement)
    {
      EventDispacthManager.getInstance().dispatchAction("cancelPop", null);
      if (this.mPlayElement.getState() == AbsPlayButtonElement.State.BUFFER)
      {
        stop();
        QTMSGManage.getInstance().sendStatistcsMessage("PlayView", "play");
      }
    }
    while (true)
    {
      if (this.mHasTop)
      {
        this.mHasTop = false;
        QTMSGManage.getInstance().sendStatistcsMessage("miniplayerclick", "minitopplayhistory");
      }
      return;
      playOrStop();
      break;
      if (paramViewElement == this.mHistoryElement)
      {
        EventDispacthManager.getInstance().dispatchAction("cancelPop", null);
        ControllerManager.getInstance().openPlayHistoryController();
        QTMSGManage.getInstance().sendStatistcsMessage("miniplayerclick", "playhistory");
      }
      else if (paramViewElement == this.mMenuElement)
      {
        showSchedule();
      }
      else if (paramViewElement == this.mBg)
      {
        toPlayView();
      }
    }
  }

  public void onManualSeek()
  {
  }

  protected void onMeasure(int paramInt1, int paramInt2)
  {
    this.standardLayout.scaleToBounds(View.MeasureSpec.getSize(paramInt1), View.MeasureSpec.getSize(paramInt2));
    this.processLayout.scaleToBounds(this.standardLayout);
    this.stateLayout.scaleToBounds(this.standardLayout);
    this.menuLayout.scaleToBounds(this.standardLayout);
    this.channelTitleLayout.scaleToBounds(this.standardLayout);
    this.playLayout.scaleToBounds(this.standardLayout);
    this.historyLayout.scaleToBounds(this.standardLayout);
    this.programTitleLayout.scaleToBounds(this.standardLayout);
    this.trangleLayout.scaleToBounds(this.standardLayout);
    this.mProcessBarElement.measure(0, this.standardLayout.height - this.processLayout.height, this.standardLayout.width, this.standardLayout.height);
    this.mBg.measure(0, 0, this.standardLayout.width, this.standardLayout.height - this.processLayout.height);
    this.mMenuElement.measure(this.menuLayout);
    this.mStateElement.measure(this.stateLayout);
    this.mChannelTitleTe.measure(this.channelTitleLayout);
    this.mPlayElement.measure(this.playLayout);
    this.mHistoryElement.measure(this.historyLayout);
    this.mProgramTitleTe.measure(this.programTitleLayout);
    this.mTrangleElement.measure(this.trangleLayout);
    this.mProgramTitleTe.setTextSize(5.0F + SkinManager.getInstance().getSubTextSize());
    this.mChannelTitleTe.setTextSize(SkinManager.getInstance().getSubTextSize());
    setMeasuredDimension(this.standardLayout.width, this.standardLayout.height);
  }

  public void onNotification(String paramString)
  {
    if ((paramString != null) && (paramString.equalsIgnoreCase("RPS")))
      refreshPlayingInfo();
  }

  public void onOpen()
  {
    this.mStateElement.setImageRes(2130838003);
  }

  public void onPlayInfoUpdated(int paramInt)
  {
    if (paramInt == 1)
      refreshPlayingInfo();
  }

  public void onPlayStatusUpdated(PlayStatus paramPlayStatus)
  {
    if (paramPlayStatus.state == 16384)
      return;
    refreshPlayingInfo();
    changeViewStateByPlayState(paramPlayStatus.state);
  }

  public void onProcessChanged()
  {
    this.mProcessBarElement.setProcess(PlayProcessSyncUtil.getInstance().getCurrentPlayRatio());
  }

  public void onProcessMaxChanged()
  {
  }

  public void onProgressPause()
  {
  }

  public void onProgressResume()
  {
  }

  public void onRecvDataException(String paramString, InfoManager.DataExceptionStatus paramDataExceptionStatus)
  {
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.qtradio.view.MiniPlayerView
 * JD-Core Version:    0.6.2
 */