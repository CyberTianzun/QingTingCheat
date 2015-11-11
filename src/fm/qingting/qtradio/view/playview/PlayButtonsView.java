package fm.qingting.qtradio.view.playview;

import android.content.Context;
import android.view.View.MeasureSpec;
import android.widget.Toast;
import fm.qingting.framework.utils.BitmapResourceCache;
import fm.qingting.framework.view.ButtonViewElement;
import fm.qingting.framework.view.QtView;
import fm.qingting.framework.view.ViewElement;
import fm.qingting.framework.view.ViewElement.OnElementClickListener;
import fm.qingting.framework.view.ViewLayout;
import fm.qingting.qtradio.controller.ControllerManager;
import fm.qingting.qtradio.fm.IMediaEventListener;
import fm.qingting.qtradio.fm.PlayStatus;
import fm.qingting.qtradio.fm.PlayerAgent;
import fm.qingting.qtradio.fmdriver.FMManager;
import fm.qingting.qtradio.helper.ChannelHelper;
import fm.qingting.qtradio.model.ChannelNode;
import fm.qingting.qtradio.model.DownLoadInfoNode;
import fm.qingting.qtradio.model.DownLoadInfoNode.IDownloadInfoEventListener;
import fm.qingting.qtradio.model.InfoManager;
import fm.qingting.qtradio.model.Node;
import fm.qingting.qtradio.model.ProgramNode;
import fm.qingting.qtradio.model.RadioChannelNode;
import fm.qingting.qtradio.model.RootNode;
import fm.qingting.qtradio.model.RootNode.PlayMode;
import fm.qingting.utils.QTMSGManage;
import fm.qingting.utils.ScreenType;
import java.util.List;

public class PlayButtonsView extends QtView
  implements ViewElement.OnElementClickListener, IMediaEventListener, DownLoadInfoNode.IDownloadInfoEventListener
{
  private final ViewLayout downloadLayout = this.standardLayout.createChildLT(50, 50, 628, 66, ViewLayout.SCALE_FLAG_SLTCW);
  private ButtonViewElement mDownloadElement;
  private ButtonViewElement mNextElement;
  private ProgramNode mNode;
  private PlayButtonElement mPlayElement;
  private ButtonViewElement mPreElement;
  private ButtonViewElement mScheduleElement;
  private final ViewLayout nextLayout = this.standardLayout.createChildLT(60, 60, 483, 60, ViewLayout.SCALE_FLAG_SLTCW);
  private final ViewLayout playLayout = this.standardLayout.createChildLT(60, 60, 330, 61, ViewLayout.SCALE_FLAG_SLTCW);
  private final ViewLayout preLayout = this.standardLayout.createChildLT(60, 60, 177, 61, ViewLayout.SCALE_FLAG_SLTCW);
  private final ViewLayout scheduleLayout = this.standardLayout.createChildLT(50, 50, 42, 66, ViewLayout.SCALE_FLAG_SLTCW);
  private final ViewLayout standardLayout = ViewLayout.createViewLayoutWithBoundsLT(720, 220, 720, 220, 0, 0, ViewLayout.FILL);

  public PlayButtonsView(Context paramContext)
  {
    super(paramContext);
    int i = hashCode();
    int j = 2 * ScreenType.getCustomExtraBound();
    this.mPlayElement = new PlayButtonElement(paramContext);
    addElement(this.mPlayElement, i);
    this.mPlayElement.expandHotPot(j);
    this.mPlayElement.setOnElementClickListener(this);
    this.mPreElement = new ButtonViewElement(paramContext);
    this.mPreElement.setBackground(2130837781, 2130837779, 2130837780);
    addElement(this.mPreElement, i);
    this.mPreElement.setOnElementClickListener(this);
    this.mPreElement.expandHotPot(j);
    this.mNextElement = new ButtonViewElement(paramContext);
    this.mNextElement.setBackground(2130837774, 2130837772, 2130837773);
    addElement(this.mNextElement, i);
    this.mNextElement.setOnElementClickListener(this);
    this.mNextElement.expandHotPot(j);
    this.mScheduleElement = new ButtonViewElement(paramContext);
    this.mScheduleElement.setBackground(2130837783, 2130837782);
    addElement(this.mScheduleElement, i);
    this.mScheduleElement.setOnElementClickListener(this);
    this.mScheduleElement.expandHotPot(j);
    this.mDownloadElement = new ButtonViewElement(paramContext);
    this.mDownloadElement.setBackground(2130837763, 2130837761);
    addElement(this.mDownloadElement, i);
    this.mDownloadElement.setOnElementClickListener(this);
    this.mDownloadElement.expandHotPot(j);
    PlayerAgent.getInstance().addMediaEventListener(this);
    int k = PlayerAgent.getInstance().getCurrentPlayStatus();
    if (InfoManager.getInstance().root().currentPlayMode() == RootNode.PlayMode.FMPLAY)
      if (InfoManager.getInstance().root().isOpenFm())
        this.mPlayElement.setState(AbsPlayButtonElement.State.PAUSE);
    while (true)
    {
      InfoManager.getInstance().root().mDownLoadInfoNode.registerListener(this);
      return;
      this.mPlayElement.setState(AbsPlayButtonElement.State.PLAY);
      continue;
      if ((k == 30583) || (k == 0) || (k == 1))
        this.mPlayElement.setState(AbsPlayButtonElement.State.PLAY);
      else if (k == 4096)
        this.mPlayElement.setState(AbsPlayButtonElement.State.PAUSE);
      else if (k == 8192)
        this.mPlayElement.setState(AbsPlayButtonElement.State.ERROR);
      else if ((k == 4101) || (k == 4098))
        this.mPlayElement.setState(AbsPlayButtonElement.State.BUFFER);
    }
  }

  private void download()
  {
    QTMSGManage.getInstance().sendStatistcsMessage("downloadclick", "playview");
    ChannelNode localChannelNode1 = InfoManager.getInstance().root().getCurrentPlayingChannelNode();
    if (localChannelNode1 != null)
    {
      if ((!localChannelNode1.isMusicChannel()) || (InfoManager.getInstance().allowDownloadMusic(localChannelNode1.channelId)))
        break label58;
      Toast.makeText(getContext(), "该节目暂时无法下载", 0).show();
    }
    label58: 
    do
    {
      return;
      if (localChannelNode1.isDownloadChannel())
      {
        ChannelNode localChannelNode2 = ChannelHelper.getInstance().getChannel(localChannelNode1.channelId, 1);
        if ((localChannelNode2 != null) && (!localChannelNode2.hasEmptyProgramSchedule()))
        {
          ControllerManager.getInstance().redirectToBatchDownloadView(localChannelNode2, false, true);
          return;
        }
      }
    }
    while (localChannelNode1.hasEmptyProgramSchedule());
    ControllerManager.getInstance().redirectToBatchDownloadView(localChannelNode1, false, true);
  }

  private void openSchedule()
  {
    dispatchActionEvent("showSchedule", null);
  }

  private void playNext()
  {
    Node localNode1 = InfoManager.getInstance().root().getCurrentPlayingNode();
    if ((localNode1 != null) && (localNode1.nextSibling != null))
      if ((localNode1.nextSibling.nodeName.equalsIgnoreCase("program")) && (((ProgramNode)localNode1.nextSibling).getCurrPlayStatus() != 2));
    Node localNode2;
    do
    {
      ChannelNode localChannelNode;
      do
      {
        do
        {
          return;
          PlayerAgent.getInstance().play(localNode1.nextSibling);
          return;
        }
        while (localNode1 == null);
        localChannelNode = InfoManager.getInstance().root().getCurrentPlayingChannelNode();
      }
      while ((localChannelNode == null) || (localChannelNode.hasEmptyProgramSchedule()) || (localChannelNode.getAllLstProgramNode() == null) || (!localNode1.nodeName.equalsIgnoreCase("program")));
      ProgramNode localProgramNode = localChannelNode.getProgramNodeByProgramId(((ProgramNode)localNode1).id);
      if ((localProgramNode != null) && (localProgramNode.nextSibling != null))
      {
        PlayerAgent.getInstance().play(localProgramNode.nextSibling);
        return;
      }
      localNode2 = (Node)localChannelNode.getAllLstProgramNode().get(0);
    }
    while (localNode2 == null);
    PlayerAgent.getInstance().play(localNode2);
  }

  private void playOrStop()
  {
    if (InfoManager.getInstance().root().currentPlayMode() == RootNode.PlayMode.FMPLAY)
      if (InfoManager.getInstance().root().isOpenFm())
      {
        PlayerAgent.getInstance().dispatchPlayStateInFM(0);
        FMManager.getInstance().turnOff();
        InfoManager.getInstance().root().tuneFM(false);
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
        return;
      }
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

  private void playPrev()
  {
    Node localNode = InfoManager.getInstance().root().getCurrentPlayingNode();
    if ((localNode != null) && (localNode.prevSibling != null) && (localNode.prevSibling.nodeName.equalsIgnoreCase("program")) && (((ProgramNode)localNode.prevSibling).getCurrPlayStatus() != 2))
      PlayerAgent.getInstance().play(localNode.prevSibling);
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

  public void close(boolean paramBoolean)
  {
    BitmapResourceCache.getInstance().clearResourceCacheOfOne(this, 0);
    InfoManager.getInstance().root().mDownLoadInfoNode.unregisterListener(this);
    super.close(paramBoolean);
  }

  public void onDownLoadInfoUpdated(int paramInt, Node paramNode)
  {
    if (((paramInt != 4) && (paramInt != 1)) || (this.mNode == null))
      return;
    if (this.mNode.isDownloadProgram())
      this.mDownloadElement.setBackground(2130837764, 2130837764);
    while (true)
    {
      invalidate();
      return;
      if (InfoManager.getInstance().root().mDownLoadInfoNode.hasDownLoad(this.mNode) == 3)
        this.mDownloadElement.setBackground(2130837764, 2130837764);
      else
        this.mDownloadElement.setBackground(2130837763, 2130837761);
    }
  }

  public void onElementClick(ViewElement paramViewElement)
  {
    if (paramViewElement == this.mPlayElement)
      if (this.mPlayElement.getState() == AbsPlayButtonElement.State.BUFFER)
      {
        stop();
        QTMSGManage.getInstance().sendStatistcsMessage("PlayView", "play");
      }
    do
    {
      return;
      playOrStop();
      break;
      if (paramViewElement == this.mPreElement)
      {
        playPrev();
        QTMSGManage.getInstance().sendStatistcsMessage("PlayView", "playprev");
        return;
      }
      if (paramViewElement == this.mNextElement)
      {
        playNext();
        QTMSGManage.getInstance().sendStatistcsMessage("PlayView", "playnext");
        return;
      }
      if (paramViewElement == this.mDownloadElement)
      {
        download();
        QTMSGManage.getInstance().sendStatistcsMessage("PlayView", "download");
        return;
      }
    }
    while (paramViewElement != this.mScheduleElement);
    openSchedule();
    QTMSGManage.getInstance().sendStatistcsMessage("PlayView", "openschedule");
  }

  protected void onMeasure(int paramInt1, int paramInt2)
  {
    this.standardLayout.scaleToBounds(View.MeasureSpec.getSize(paramInt1), View.MeasureSpec.getSize(paramInt2));
    this.playLayout.scaleToBounds(this.standardLayout);
    this.preLayout.scaleToBounds(this.standardLayout);
    this.nextLayout.scaleToBounds(this.standardLayout);
    this.scheduleLayout.scaleToBounds(this.standardLayout);
    this.downloadLayout.scaleToBounds(this.standardLayout);
    this.mPlayElement.measure(this.playLayout);
    this.mPreElement.measure(this.preLayout);
    this.mNextElement.measure(this.nextLayout);
    this.mScheduleElement.measure(this.scheduleLayout);
    this.mDownloadElement.measure(this.downloadLayout);
    setMeasuredDimension(this.standardLayout.width, this.standardLayout.height);
  }

  public void onPlayStatusUpdated(PlayStatus paramPlayStatus)
  {
    int i = paramPlayStatus.state;
    if ((i == 0) || (i == 1))
    {
      this.mPlayElement.setState(AbsPlayButtonElement.State.PLAY);
      return;
    }
    if (i == 8192)
    {
      this.mPlayElement.setState(AbsPlayButtonElement.State.ERROR);
      return;
    }
    if ((i == 4098) || (i == 4101))
    {
      this.mPlayElement.setState(AbsPlayButtonElement.State.BUFFER);
      return;
    }
    this.mPlayElement.setState(AbsPlayButtonElement.State.PAUSE);
  }

  public void update(String paramString, Object paramObject)
  {
    boolean bool1;
    if (paramString.equalsIgnoreCase("setNode"))
    {
      this.mNode = ((ProgramNode)paramObject);
      if (!this.mNode.isDownloadProgram())
        break label214;
      this.mDownloadElement.setBackground(2130837764, 2130837764);
      ChannelNode localChannelNode3 = ChannelHelper.getInstance().getChannel(this.mNode.channelId, 1);
      if ((localChannelNode3 != null) && (localChannelNode3.hasEmptyProgramSchedule()))
        InfoManager.getInstance().loadProgramsScheduleNode(localChannelNode3, null);
      if (this.mNode.nextSibling == null)
        break label306;
      if ((!this.mNode.nextSibling.nodeName.equalsIgnoreCase("program")) || (((ProgramNode)this.mNode.nextSibling).getCurrPlayStatus() == 2))
        break label458;
      bool1 = true;
    }
    while (true)
    {
      label128: boolean bool2;
      if (this.mNode.prevSibling != null)
      {
        boolean bool4 = this.mNode.prevSibling.nodeName.equalsIgnoreCase("program");
        bool2 = false;
        if (bool4)
        {
          int i = ((ProgramNode)this.mNode.prevSibling).getCurrPlayStatus();
          bool2 = false;
          if (i == 3)
            bool2 = true;
        }
      }
      while (true)
      {
        this.mNextElement.setEnable(bool1);
        this.mPreElement.setEnable(bool2);
        invalidate();
        return;
        label214: if (InfoManager.getInstance().root().mDownLoadInfoNode.hasDownLoad(this.mNode) == 3)
        {
          this.mDownloadElement.setBackground(2130837764, 2130837764);
          break;
        }
        if ((this.mNode.isMusicChannel()) && (!InfoManager.getInstance().allowDownloadMusic(this.mNode.channelId)))
        {
          this.mDownloadElement.setBackground(2130837762, 2130837762);
          break;
        }
        this.mDownloadElement.setBackground(2130837763, 2130837761);
        break;
        label306: ChannelNode localChannelNode1 = InfoManager.getInstance().root().getCurrentPlayingChannelNode();
        if ((localChannelNode1 == null) || (localChannelNode1.hasEmptyProgramSchedule()) || (localChannelNode1.getAllLstProgramNode() == null))
          break label458;
        ProgramNode localProgramNode2 = localChannelNode1.getProgramNodeByProgramId(this.mNode.id);
        if ((localProgramNode2 == null) || (localProgramNode2.nextSibling == null))
          break label458;
        bool1 = true;
        break label128;
        ChannelNode localChannelNode2 = InfoManager.getInstance().root().getCurrentPlayingChannelNode();
        bool2 = false;
        if (localChannelNode2 != null)
        {
          boolean bool3 = localChannelNode2.hasEmptyProgramSchedule();
          bool2 = false;
          if (!bool3)
          {
            List localList = localChannelNode2.getAllLstProgramNode();
            bool2 = false;
            if (localList != null)
            {
              ProgramNode localProgramNode1 = localChannelNode2.getProgramNodeByProgramId(this.mNode.id);
              bool2 = false;
              if (localProgramNode1 != null)
              {
                Node localNode = localProgramNode1.prevSibling;
                bool2 = false;
                if (localNode != null)
                  bool2 = true;
              }
            }
          }
        }
      }
      label458: bool1 = false;
    }
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.qtradio.view.playview.PlayButtonsView
 * JD-Core Version:    0.6.2
 */