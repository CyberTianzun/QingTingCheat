package fm.qingting.utils;

import android.widget.Toast;
import fm.qingting.qtradio.alarm.ClockManager;
import fm.qingting.qtradio.alarm.ClockManager.IClockListener;
import fm.qingting.qtradio.fm.IMediaEventListener;
import fm.qingting.qtradio.fm.PlayStatus;
import fm.qingting.qtradio.fm.PlayerAgent;
import fm.qingting.qtradio.model.Clock;
import fm.qingting.qtradio.model.InfoManager;
import fm.qingting.qtradio.model.Node;
import fm.qingting.qtradio.model.PlayedMetaData;
import fm.qingting.qtradio.model.PlayedMetaInfo;
import fm.qingting.qtradio.model.ProgramNode;
import fm.qingting.qtradio.model.RootNode;
import fm.qingting.qtradio.model.RootNode.IInfoUpdateEventListener;
import fm.qingting.qtradio.model.RootNode.IPlayInfoEventListener;
import fm.qingting.qtradio.model.RootNode.PlayMode;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class PlayProcessSyncUtil
  implements IMediaEventListener, ClockManager.IClockListener, RootNode.IInfoUpdateEventListener, RootNode.IPlayInfoEventListener
{
  private static final int EVENT_MANUAL_SEEK = 2;
  private static final int EVENT_MAX_CHANGE = 1;
  private static final int EVENT_PAUSE = 3;
  private static final int EVENT_PROGRESS_CHANGE = 0;
  private static final int EVENT_RESUME = 4;
  private static PlayProcessSyncUtil instance;
  private int mCurrentPlayTime;
  private int mLastPlayTime;
  private List<OnPlayProcessListener> mListerners;
  private boolean mPlaying = false;
  private int mTotalLength;

  private PlayProcessSyncUtil()
  {
    ClockManager.getInstance().addListener(this);
    PlayerAgent.getInstance().addMediaEventListener(this);
    InfoManager.getInstance().root().registerInfoUpdateListener(this, 1);
    InfoManager.getInstance().root().registerSubscribeEventListener(this, 1);
  }

  private void dispatchProgressEvent(int paramInt)
  {
    if ((this.mListerners != null) && (this.mListerners.size() > 0))
    {
      int i = 0;
      if (i < this.mListerners.size())
      {
        switch (paramInt)
        {
        default:
        case 0:
        case 2:
        case 1:
        case 3:
        case 4:
        }
        while (true)
        {
          i++;
          break;
          ((OnPlayProcessListener)this.mListerners.get(i)).onProcessChanged();
          continue;
          ((OnPlayProcessListener)this.mListerners.get(i)).onManualSeek();
          continue;
          ((OnPlayProcessListener)this.mListerners.get(i)).onProcessMaxChanged();
          continue;
          ((OnPlayProcessListener)this.mListerners.get(i)).onProgressPause();
          continue;
          ((OnPlayProcessListener)this.mListerners.get(i)).onProgressResume();
        }
      }
    }
  }

  public static PlayProcessSyncUtil getInstance()
  {
    if (instance == null)
      instance = new PlayProcessSyncUtil();
    return instance;
  }

  private void incCurrentPlayTime()
  {
    this.mCurrentPlayTime = (1 + this.mCurrentPlayTime);
    if (this.mCurrentPlayTime > this.mTotalLength)
      this.mCurrentPlayTime = this.mTotalLength;
    judgeIfChanged();
  }

  private void judgeIfChanged()
  {
    if (this.mLastPlayTime != this.mCurrentPlayTime)
    {
      this.mLastPlayTime = this.mCurrentPlayTime;
      dispatchProgressEvent(0);
    }
  }

  public void addListener(OnPlayProcessListener paramOnPlayProcessListener)
  {
    if (this.mListerners == null)
      this.mListerners = new ArrayList();
    this.mListerners.add(paramOnPlayProcessListener);
  }

  public void attempSeek(float paramFloat)
  {
    float f1 = 1.0F;
    if ((InfoManager.getInstance().root().currentPlayMode() == RootNode.PlayMode.PLAY_END_ADVERTISEMENT) || (InfoManager.getInstance().root().currentPlayMode() == RootNode.PlayMode.PLAY_FRONT_ADVERTISEMENT));
    Node localNode;
    do
    {
      do
        return;
      while (InfoManager.getInstance().root().currentPlayMode() == RootNode.PlayMode.NORMALPLAY);
      localNode = InfoManager.getInstance().root().getCurrentPlayingNode();
    }
    while ((localNode == null) || (!localNode.nodeName.equalsIgnoreCase("program")));
    ProgramNode localProgramNode = (ProgramNode)localNode;
    int i = localProgramNode.getCurrPlayStatus();
    float f2;
    if (i == 1)
    {
      int j = localProgramNode.startTime();
      long l = localProgramNode.getDuration();
      Calendar localCalendar = Calendar.getInstance();
      int k = localCalendar.get(11);
      int m = localCalendar.get(12);
      f2 = (localCalendar.get(13) + (k * 3600 + m * 60) - j) / (float)l;
      if (f2 <= f1);
    }
    while (true)
      if (paramFloat >= f1)
      {
        setCurrentPlayTime(f1);
        dispatchProgressEvent(2);
        PlayerAgent.getInstance().play(localProgramNode.parent);
        return;
        if (f2 < 0.0F)
          f1 = 0.0F;
      }
      else
      {
        setCurrentPlayTime(paramFloat);
        dispatchProgressEvent(2);
        PlayerAgent.getInstance().seek(paramFloat);
        return;
        if (i != 3)
          break;
        setCurrentPlayTime(paramFloat);
        dispatchProgressEvent(2);
        PlayerAgent.getInstance().seek(paramFloat);
        return;
        f1 = f2;
      }
  }

  public void backwardThirtySeconds()
  {
    float f1 = 1.0F;
    if (this.mTotalLength == 0)
      return;
    float f2 = (-30 + this.mCurrentPlayTime) / this.mTotalLength;
    if (f2 > f1);
    while (true)
    {
      boolean bool = f1 < 0.0F;
      float f3 = 0.0F;
      if (bool);
      while (true)
      {
        attempSeek(f3);
        return;
        f3 = f1;
      }
      f1 = f2;
    }
  }

  public void forwardThirtySeconds()
  {
    float f1 = 1.0F;
    if (this.mTotalLength == 0)
      return;
    float f2 = (30 + this.mCurrentPlayTime) / this.mTotalLength;
    if (f2 > f1);
    while (true)
    {
      boolean bool = f1 < 0.0F;
      float f3 = 0.0F;
      if (bool);
      while (true)
      {
        attempSeek(f3);
        return;
        f3 = f1;
      }
      f1 = f2;
    }
  }

  public float getCurrentPlayRatio()
  {
    if (this.mTotalLength == 0)
      return 0.0F;
    return this.mCurrentPlayTime / this.mTotalLength;
  }

  public int getCurrentPlayTime()
  {
    return this.mCurrentPlayTime;
  }

  public int getTotalLength()
  {
    return this.mTotalLength;
  }

  public void init()
  {
  }

  public void onClockTime(int paramInt)
  {
    if ((this.mPlaying) || (InfoManager.getInstance().root().currentPlayMode() == RootNode.PlayMode.NORMALPLAY))
      incCurrentPlayTime();
  }

  public void onInfoUpdated(int paramInt)
  {
    Node localNode;
    if (paramInt == 1)
    {
      localNode = InfoManager.getInstance().root().getCurrentPlayingNode();
      if (localNode != null)
        if (!localNode.nodeName.equalsIgnoreCase("program"))
          break label83;
    }
    label83: for (int i = ((ProgramNode)localNode).id; ; i = 0)
    {
      PlayedMetaData localPlayedMetaData = PlayedMetaInfo.getInstance().getPlayedMeta(i);
      if (localPlayedMetaData != null)
      {
        setCurrentPlayTime(localPlayedMetaData.position);
        dispatchProgressEvent(2);
        Toast.makeText(InfoManager.getInstance().getContext(), "从上次停止的地方开始播放", 1).show();
      }
      return;
    }
  }

  public void onPlayInfoUpdated(int paramInt)
  {
    Node localNode;
    if (paramInt == 1)
    {
      localNode = InfoManager.getInstance().root().getCurrentPlayingNode();
      if (localNode != null)
        break label31;
      setTotalLength(100);
      setCurrentPlayTime(0);
    }
    label31: ProgramNode localProgramNode1;
    do
    {
      do
        return;
      while (!localNode.nodeName.equalsIgnoreCase("program"));
      localProgramNode1 = (ProgramNode)localNode;
      if (localProgramNode1.channelType == 1)
      {
        setTotalLength(localProgramNode1.getDuration());
        setCurrentPlayTime(0);
        return;
      }
    }
    while (InfoManager.getInstance().root().getCurrentPlayingChannelNode() == null);
    ProgramNode localProgramNode2 = (ProgramNode)localNode;
    setTotalLength(localProgramNode1.getDuration());
    if ((localProgramNode2 != null) && (localProgramNode2.id == ((ProgramNode)localNode).id) && (localProgramNode2.getCurrPlayStatus() == 1))
    {
      int i = localProgramNode1.startTime();
      long l = localProgramNode1.getDuration();
      Calendar localCalendar = Calendar.getInstance();
      int j = localCalendar.get(11);
      int k = localCalendar.get(12);
      float f = (localCalendar.get(13) + (j * 3600 + k * 60) - i) / (float)l;
      if (f > 1.0F)
        f = 1.0F;
      while (true)
      {
        setCurrentPlayTime(f);
        return;
        if (f < 0.0F)
          f = 0.0F;
      }
    }
    setCurrentPlayTime(0);
  }

  public void onPlayStatusUpdated(PlayStatus paramPlayStatus)
  {
    int i = paramPlayStatus.state;
    if ((i == 0) || (i == 8192) || (i == 4098) || (i == 4101))
    {
      this.mPlaying = false;
      dispatchProgressEvent(3);
      return;
    }
    this.mPlaying = true;
    dispatchProgressEvent(4);
  }

  public void onTime(Clock paramClock)
  {
  }

  public void onTimeStart(Clock paramClock)
  {
  }

  public void onTimeStop(Clock paramClock)
  {
  }

  public void onTimerRemoved()
  {
  }

  public void removeListener(OnPlayProcessListener paramOnPlayProcessListener)
  {
    if (this.mListerners == null)
      return;
    this.mListerners.remove(paramOnPlayProcessListener);
  }

  public void setCurrentPlayTime(float paramFloat)
  {
    this.mCurrentPlayTime = ((int)(paramFloat * this.mTotalLength));
    if (this.mCurrentPlayTime > this.mTotalLength)
      this.mCurrentPlayTime = this.mTotalLength;
    judgeIfChanged();
  }

  public void setCurrentPlayTime(int paramInt)
  {
    this.mCurrentPlayTime = paramInt;
    if (this.mCurrentPlayTime > this.mTotalLength)
      this.mCurrentPlayTime = this.mTotalLength;
    judgeIfChanged();
  }

  public void setTotalLength(int paramInt)
  {
    this.mTotalLength = paramInt;
    dispatchProgressEvent(1);
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.utils.PlayProcessSyncUtil
 * JD-Core Version:    0.6.2
 */