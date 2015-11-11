package fm.qingting.qtradio.manager;

import android.content.Context;
import android.os.Handler;
import java.lang.ref.WeakReference;

public class StatisticsManager
{
  private static final int CHANNEL_PROGRAM_PLAYING_RECORD_TIME = 60;
  private static final int NO_START_TIME = -1;
  private static final int RADIO_PROGRAM_PLAYING_RECORD_TIME = 300;
  private static StatisticsManager instance = null;
  private WeakReference<Context> contextRef = null;
  private boolean counting = false;
  private boolean enableValidCounting = false;
  private boolean fmAvailable = false;
  private final Handler handler = new Handler();
  private int lastChannelProgramStartTime = -1;
  private int lastFMStateStartTime = -1;
  private String lastPlayingChannel = "nothing";
  private String lastPlayingChannelProgram = null;
  private String lastPlayingRadioProgram = null;
  private int lastRadioProgramStartTime = -1;
  private long lastTimePoint = 0L;
  private boolean needRecordFMState = true;
  private boolean needRecordPlayingChannelProgram = false;
  private boolean needRecordPlayingRadioProgram = false;
  private long playStartTimePoint = 0L;
  private int playValidCount = 0;
  private String playingChannel = "Error";
  private String region = null;

  private boolean contextAvailable()
  {
    if (this.contextRef == null);
    while (this.contextRef.get() == null)
      return false;
    return true;
  }

  private Context getContext()
  {
    if (contextAvailable())
      return (Context)this.contextRef.get();
    return null;
  }

  public static StatisticsManager getInstance()
  {
    if (instance == null)
      instance = new StatisticsManager();
    return instance;
  }

  private void updateRecordPlayingChannelProgramStatus()
  {
  }

  private void updateRecordPlayingRadioProgramStatus()
  {
  }

  public void recordRegion(String paramString)
  {
    this.region = paramString;
  }

  public void recordSamsungFMAvailable(boolean paramBoolean)
  {
  }

  public void reset()
  {
    this.region = null;
    this.fmAvailable = false;
    this.lastChannelProgramStartTime = -1;
    this.lastRadioProgramStartTime = -1;
    this.lastFMStateStartTime = -1;
    this.needRecordPlayingChannelProgram = false;
    this.needRecordPlayingRadioProgram = false;
    this.needRecordFMState = true;
    this.lastPlayingChannelProgram = null;
    this.lastPlayingRadioProgram = null;
    this.contextRef = null;
  }

  public void setContext(Context paramContext)
  {
    this.contextRef = new WeakReference(paramContext);
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.qtradio.manager.StatisticsManager
 * JD-Core Version:    0.6.2
 */