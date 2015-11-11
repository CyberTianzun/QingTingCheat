package fm.qingting.utils;

import android.annotation.SuppressLint;
import fm.qingting.framework.manager.EventDispacthManager;
import fm.qingting.qtradio.manager.CollectionRemindManager;
import fm.qingting.qtradio.model.ChannelNode;
import fm.qingting.qtradio.model.InfoManager;
import fm.qingting.qtradio.model.RootNode;
import java.util.HashMap;
import java.util.Map;

public class PlaybackMonitor
  implements OnPlayProcessListener
{
  private static PlaybackMonitor instance;
  private final int CONTINUOUS_PLAY_TO_COLLECT = 600;
  private final int THRESHOLD = 3;

  @SuppressLint({"UseSparseArrays"})
  private Map<Integer, Integer> mAccumulateTimeByChannel = new HashMap();

  @SuppressLint({"UseSparseArrays"})
  private Map<Integer, Integer> mContinuousPlayTime = new HashMap();
  private boolean mEducationFavNotFired = true;
  private int mLastChannelId = 0;
  private int mLastPosition;
  private long mLastTimestamp;
  private int mTotalAccumulateTime;

  private PlaybackMonitor()
  {
    PlayProcessSyncUtil.getInstance().addListener(this);
  }

  public static PlaybackMonitor getInstance()
  {
    if (instance == null)
      instance = new PlaybackMonitor();
    return instance;
  }

  public int getAccumulateTime()
  {
    return 0;
  }

  public int getChannelAccumulateTime(int paramInt)
  {
    if (this.mAccumulateTimeByChannel.containsKey(Integer.valueOf(paramInt)))
      return ((Integer)this.mAccumulateTimeByChannel.get(Integer.valueOf(paramInt))).intValue();
    return 0;
  }

  public int getChannelContinuousPlayTime(int paramInt)
  {
    if (this.mContinuousPlayTime.containsKey(Integer.valueOf(paramInt)))
      return ((Integer)this.mContinuousPlayTime.get(Integer.valueOf(paramInt))).intValue();
    return 0;
  }

  public int getCurrentChannelAccumulateTime()
  {
    if (this.mLastChannelId != 0)
      return getChannelAccumulateTime(this.mLastChannelId);
    return 0;
  }

  public int getCurrentChannelContinuousPlayTime()
  {
    return getChannelContinuousPlayTime(this.mLastChannelId);
  }

  public int getTotalAccumulateTime()
  {
    return this.mTotalAccumulateTime;
  }

  public void onManualSeek()
  {
  }

  public void onProcessChanged()
  {
    int i = PlayProcessSyncUtil.getInstance().getCurrentPlayTime();
    long l = System.currentTimeMillis() / 1000L;
    ChannelNode localChannelNode = InfoManager.getInstance().root().getCurrentPlayingChannelNode();
    int j;
    if (localChannelNode.channelId == this.mLastChannelId)
    {
      if (i > this.mLastPosition)
      {
        j = i - this.mLastPosition;
        (l - this.mLastTimestamp);
        if (j <= 3);
      }
      else
      {
        this.mLastPosition = i;
        this.mLastTimestamp = l;
        return;
      }
      this.mTotalAccumulateTime = (j + this.mTotalAccumulateTime);
      if (!this.mAccumulateTimeByChannel.containsKey(Integer.valueOf(localChannelNode.channelId)))
      {
        this.mAccumulateTimeByChannel.put(Integer.valueOf(localChannelNode.channelId), Integer.valueOf(j));
        label132: if (!this.mContinuousPlayTime.containsKey(Integer.valueOf(localChannelNode.channelId)))
          break label324;
      }
    }
    label324: for (int k = j + ((Integer)this.mContinuousPlayTime.get(Integer.valueOf(localChannelNode.channelId))).intValue(); ; k = j)
    {
      this.mContinuousPlayTime.put(Integer.valueOf(localChannelNode.channelId), Integer.valueOf(k));
      if ((!this.mEducationFavNotFired) || (k <= 600))
        break;
      this.mEducationFavNotFired = false;
      CollectionRemindManager.setSource(2);
      EventDispacthManager.getInstance().dispatchAction("showEducationFav", localChannelNode);
      break;
      this.mAccumulateTimeByChannel.put(Integer.valueOf(localChannelNode.channelId), Integer.valueOf(j + ((Integer)this.mAccumulateTimeByChannel.get(Integer.valueOf(localChannelNode.channelId))).intValue()));
      break label132;
      this.mLastChannelId = localChannelNode.channelId;
      this.mContinuousPlayTime.put(Integer.valueOf(localChannelNode.channelId), Integer.valueOf(0));
      break;
    }
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
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.utils.PlaybackMonitor
 * JD-Core Version:    0.6.2
 */