package rui.lin.spectra;

import android.media.AudioTrack;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.os.SystemClock;
import fm.qingting.utils.CPUInfo;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Spectra
{
  protected static final int AUDIO_TRACK_BUFFER_SIZE_COEFFICIENT = 2;
  protected static final int BUFFER_ENGINE_BUFFER_FULL_SLEEP_TIME = 300;
  protected static final int BUFFER_ENGINE_PAUSED_STATE_EOF_SLEEP_TIME = 100;
  protected static final int BUFFER_ENGINE_STOPPING_STATE_SLEEP_TIME = 100;
  protected static final int EVENT_LISTENERS_CAPACITY = 5;
  protected static final int LIVE_STREAM_CRITCAL_DURATION = 5;
  protected static final int LIVE_STREAM_LOADED_STATE_TIME = 15000;
  protected static final long LOW_CPU_COST_RETRY_INTERVAL = 1500L;
  protected static final long LOW_CPU_COST_RETRY_TRIGGER = 20000L;
  protected static final long OPEN_STREAMS_TIMEOUT_FOR_LOAD = 3500L;
  protected static final long OPEN_STREAMS_TIMEOUT_FOR_PLAY = 45000L;
  protected static final int PACKET_BUFFER_LENGTH = 500;
  protected static final int PLAYBACK_ENGINE_PAUSED_STATE_SLEEP_TIME = 100;
  protected static final long PLAY_LOW_CPU_COST_RETRY_INTERVAL = 500L;
  protected static final long READ_PACKET_TIMEOUT = 10000000L;
  protected static int sLogLevel;
  protected static String sLogTag;
  protected static Logger sLogger;
  protected AudioTrack mAudioTrack;
  protected int mBitRate;
  protected BufferEngine mBufferEngine;
  protected int mChannels;
  protected String mCompressionFmt;
  protected String mContainerFmt;
  protected int mDuration;
  protected SpectraEventDispatcher mEventDispatcher;
  protected HandlerThread mEventDispatcherThread;
  protected List<SpectraEventListener> mEventListeners;
  private boolean mLowCPUCostRetry = false;
  protected byte[] mMetadata;
  protected int mMetadataLength;
  protected PlaybackEngine mPlaybackEngine;
  protected int mPosition;
  protected String mSampleFmt;
  protected int mSampleRate;
  protected String mSelectedUrl;
  protected byte[] mSpectraCtx;
  private State mState = State.UNINITIALIZED;
  private Lock mStateLock = new ReentrantLock();
  protected StateResetTask mStateResetTask;
  protected Timer mStateResetTimer;
  protected ArrayList<String> mUrls;
  protected byte[] mWaveform;
  protected int mWaveformLength;
  private boolean shouldOpt = true;

  static
  {
    int i = 1;
    sLogLevel = 4;
    sLogTag = "Spectra";
    if ((0x1 & CPUInfo.FPU()) != 0);
    while (true)
    {
      int j = 5;
      try
      {
        int k = Integer.parseInt(CPUInfo.CPUArch().substring(0, 1));
        j = k;
        System.loadLibrary("opencore");
        System.loadLibrary("rtmp");
        if ((j >= 6) && (i != 0))
        {
          System.loadLibrary("v6vfp");
          System.loadLibrary("spectra6vfp");
          clinit();
          sLogger = new Logger(sLogLevel, sLogTag);
          return;
          i = 0;
        }
      }
      catch (Exception localException)
      {
        while (true)
        {
          localException.printStackTrace();
          continue;
          System.loadLibrary("v5te");
          System.loadLibrary("spectra5te");
        }
      }
    }
  }

  public Spectra()
  {
    if (init(500) == 0)
    {
      this.mStateResetTimer = new Timer(true);
      this.mStateResetTask = new StateResetTask();
      int i = 2 * AudioTrack.getMinBufferSize(44100, 12, 2);
      this.mAudioTrack = new AudioTrack(3, 44100, 12, 2, i, 1);
      this.mEventListeners = Collections.synchronizedList(new ArrayList(5));
      this.mEventDispatcherThread = new HandlerThread("SpectraEventDispatcherThread");
      this.mEventDispatcherThread.start();
      this.mEventDispatcher = new SpectraEventDispatcher(this.mEventDispatcherThread.getLooper());
      this.mState = State.STOPPED;
      Logger localLogger = sLogger;
      Object[] arrayOfObject = new Object[1];
      arrayOfObject[0] = Integer.valueOf(i);
      localLogger.info("Spectra initialized (with AudioTrack buffer size defaulted to %d bytes), switch state to STOPPED", arrayOfObject);
      this.mEventDispatcher.sendEmptyMessage(SpectraEvent.STOPPED.ordinal());
      return;
    }
    deinit();
    this.mState = State.UNINITIALIZED;
    sLogger.info("native init() failed, keep state UNINITIALIZED", new Object[0]);
  }

  private static native int clinit();

  private native int closeStream();

  private native int decodeFrame();

  private native int deinit();

  private native boolean forceIntEnabled();

  private native int forceInterruption(boolean paramBoolean);

  private native int init(int paramInt);

  private native int openStream(String paramString, long paramLong);

  private native int readPacket();

  private native int seekStream(int paramInt);

  public boolean addEventListener(SpectraEventListener paramSpectraEventListener)
  {
    if (this.mEventListeners != null)
    {
      this.mEventListeners.add(paramSpectraEventListener);
      return true;
    }
    return false;
  }

  protected void customizeAudioTrack()
  {
    int i = 4000;
    int j = 12;
    int k = 2;
    if (this.mSampleRate < i)
    {
      sLogger.warning("sample rate unsuppported: " + i, new Object[0]);
      switch (this.mChannels)
      {
      default:
        sLogger.warning("channel config unsupported: " + this.mChannels, new Object[0]);
      case 2:
        label105: if (this.mSampleFmt == null)
          sLogger.warning("sample format null", new Object[0]);
        break;
      case 1:
      }
    }
    while (true)
    {
      if ((i == this.mAudioTrack.getSampleRate()) && (j == this.mAudioTrack.getChannelConfiguration()) && (k == this.mAudioTrack.getAudioFormat()))
        break label391;
      this.mAudioTrack.release();
      int m = 2 * AudioTrack.getMinBufferSize(i, j, k);
      this.mAudioTrack = new AudioTrack(3, i, j, k, m, 1);
      sLogger.info("customize AudioTrack with SR=" + this.mSampleRate + " CH=" + this.mChannels + " FMT=" + this.mSampleFmt + " BUFF=" + m, new Object[0]);
      return;
      if (this.mSampleRate > 48000)
      {
        i = 48000;
        sLogger.warning("sample rate unsuppported: " + i, new Object[0]);
        break;
      }
      i = this.mSampleRate;
      break;
      j = 4;
      break label105;
      if (!this.mSampleFmt.equals("s16"))
        if (this.mSampleFmt.equals("u8"))
          k = 3;
        else
          sLogger.warning("sample format unsupported: " + this.mSampleFmt, new Object[0]);
    }
    label391: sLogger.info("no need to customize AudioTrack", new Object[0]);
  }

  protected void finalize()
    throws Throwable
  {
    stop();
    deinit();
    this.mAudioTrack.release();
    super.finalize();
  }

  public void interrupt(boolean paramBoolean)
  {
    forceInterruption(paramBoolean);
  }

  public boolean interrupt()
  {
    return forceIntEnabled();
  }

  public boolean isLiveStream()
  {
    return this.mDuration < 5;
  }

  public boolean isShouldOpt()
  {
    return this.shouldOpt;
  }

  public boolean load(List<String> paramList)
  {
    boolean bool = false;
    this.mStateLock.lock();
    while (true)
    {
      try
      {
        sLogger.info("#------> load(%s)", new Object[] { paramList });
        this.mEventDispatcher.sendEmptyMessage(SpectraEvent.DO_LOAD.ordinal());
        switch (1.$SwitchMap$rui$lin$spectra$Spectra$State[this.mState.ordinal()])
        {
        default:
          sLogger.warning("load(%s) called in wrong state, ignore", new Object[] { paramList });
          this.mEventDispatcher.sendEmptyMessage(this.mState.ordinal());
          return bool;
        case 1:
          if (isLiveStream())
          {
            this.mStateResetTask.shouldCancel = true;
            this.mStateResetTask.cancel();
            this.mStateResetTimer.purge();
            this.mStateResetTask = new StateResetTask();
            sLogger.info("previous StateResetTask cancelled", new Object[0]);
          }
          this.mBufferEngine.shouldStop = true;
        case 5:
        }
        try
        {
          this.mBufferEngine.join();
          this.mBufferEngine = null;
          closeStream();
          this.mUrls = new ArrayList(paramList);
          this.mSelectedUrl = null;
          if (!openStreams(this.mUrls, 3500L))
            break label356;
          customizeAudioTrack();
          this.mBufferEngine = new BufferEngine();
          if (isLiveStream())
          {
            this.mStateResetTimer.schedule(this.mStateResetTask, 15000L);
            sLogger.info("live stream opened, switch state to LOADED and start the state reset timer", new Object[0]);
            this.mState = State.LOADED;
            this.mBufferEngine.start();
            this.mEventDispatcher.sendEmptyMessage(SpectraEvent.LOADED.ordinal());
            bool = true;
          }
        }
        catch (InterruptedException localInterruptedException)
        {
          localInterruptedException.printStackTrace();
          continue;
        }
      }
      finally
      {
        this.mStateLock.unlock();
      }
      sLogger.info("on-demand stream opened, switch state to LOADED", new Object[0]);
      continue;
      label356: this.mState = State.STOPPED;
      sLogger.warning("openStreams failed, reset state to STOPPED", new Object[0]);
      this.mEventDispatcher.sendEmptyMessage(SpectraEvent.STOPPED.ordinal());
      bool = false;
    }
  }

  protected boolean openStreams(List<String> paramList, long paramLong)
  {
    if ((paramList == null) || (paramList.size() == 0))
      return false;
    Long localLong = Long.valueOf(SystemClock.elapsedRealtime());
    Iterator localIterator = paramList.iterator();
    do
    {
      if (!localIterator.hasNext())
        break;
      String str = (String)localIterator.next();
      if (openStream(str, 1000L * paramLong) == 0)
      {
        sLogger.info("openStream(%s) succeeded", new Object[] { str });
        this.mSelectedUrl = str;
        return true;
      }
      closeStream();
      this.mEventDispatcher.sendEmptyMessage(SpectraEvent.OPEN_STREAM_FAILED.ordinal());
    }
    while (SystemClock.elapsedRealtime() <= paramLong + localLong.longValue());
    return false;
  }

  public boolean pause()
  {
    boolean bool = true;
    this.mStateLock.lock();
    while (true)
    {
      try
      {
        sLogger.info("#------> pause()", new Object[0]);
        this.mEventDispatcher.sendEmptyMessage(SpectraEvent.DO_PAUSE.ordinal());
        switch (1.$SwitchMap$rui$lin$spectra$Spectra$State[this.mState.ordinal()])
        {
        case 3:
        default:
          sLogger.warning("pause() called in wrong state, ignore", new Object[0]);
          this.mEventDispatcher.sendEmptyMessage(this.mState.ordinal());
          bool = false;
          return bool;
        case 2:
        case 4:
        }
        if (isLiveStream())
          break label212;
        this.mState = State.PAUSED;
        this.mAudioTrack.pause();
        this.mPlaybackEngine.shouldWait = true;
        if (!this.mPlaybackEngine.isWaiting)
        {
          Thread.yield();
          continue;
        }
      }
      finally
      {
        this.mStateLock.unlock();
      }
      this.mPlaybackEngine.shouldWait = false;
      sLogger.info("switch state to PAUSED", new Object[0]);
      this.mEventDispatcher.sendEmptyMessage(SpectraEvent.PAUSED.ordinal());
      continue;
      label212: sLogger.warning("live audio, cannot pause", new Object[0]);
      bool = false;
    }
  }

  public boolean play()
  {
    boolean bool = true;
    this.mStateLock.lock();
    while (true)
    {
      try
      {
        sLogger.info("#------> play()", new Object[0]);
        this.mEventDispatcher.sendEmptyMessage(SpectraEvent.DO_PLAY.ordinal());
        switch (1.$SwitchMap$rui$lin$spectra$Spectra$State[this.mState.ordinal()])
        {
        default:
          sLogger.warning("play() called in wrong state, ignore", new Object[0]);
          this.mEventDispatcher.sendEmptyMessage(this.mState.ordinal());
          bool = false;
          return bool;
        case 1:
          if (isLiveStream())
          {
            this.mStateResetTask.shouldCancel = true;
            this.mStateResetTask.cancel();
            this.mStateResetTimer.purge();
            this.mStateResetTask = new StateResetTask();
            sLogger.info("previous StateResetTask cancelled", new Object[0]);
          }
          this.mPlaybackEngine = new PlaybackEngine();
          this.mState = State.PLAYING;
          this.mPlaybackEngine.start();
          this.mAudioTrack.play();
          sLogger.info("playback started, switch state to PLAYING", new Object[0]);
          this.mEventDispatcher.sendEmptyMessage(SpectraEvent.PLAYING.ordinal());
          continue;
        case 5:
        }
      }
      finally
      {
        this.mStateLock.unlock();
      }
      if ((this.mUrls != null) && (this.mUrls.size() > 0))
      {
        if (openStreams(this.mUrls, 45000L))
        {
          customizeAudioTrack();
          this.mBufferEngine = new BufferEngine();
          this.mPlaybackEngine = new PlaybackEngine();
          this.mState = State.PLAYING;
          this.mBufferEngine.start();
          this.mPlaybackEngine.start();
          this.mAudioTrack.play();
          sLogger.info("playback started, switch state to PLAYING", new Object[0]);
          this.mEventDispatcher.sendEmptyMessage(SpectraEvent.PLAYING.ordinal());
        }
        else
        {
          this.mState = State.STOPPED;
          sLogger.warning("openStreams failed, reset state to STOPPED", new Object[0]);
          this.mEventDispatcher.sendEmptyMessage(SpectraEvent.STOPPED.ordinal());
          bool = false;
        }
      }
      else
      {
        this.mState = State.STOPPED;
        sLogger.warning("mUrls not set, reset state to STOPPED", new Object[0]);
        this.mEventDispatcher.sendEmptyMessage(SpectraEvent.STOPPED.ordinal());
        bool = false;
      }
    }
  }

  public int queryBitRate()
  {
    return this.mBitRate;
  }

  public int queryChannels()
  {
    return this.mChannels;
  }

  public String queryCompressionFormat()
  {
    if (this.mCompressionFmt != null)
      return this.mCompressionFmt;
    return "unknown";
  }

  public String queryContainerFormat()
  {
    if (this.mContainerFmt != null)
      return this.mContainerFmt;
    return "unknown";
  }

  public int queryDuration()
  {
    return this.mDuration;
  }

  public byte[] queryMetadata()
  {
    try
    {
      if ((this.mMetadata != null) && (this.mMetadata.length > 0) && (this.mMetadataLength > 0))
      {
        byte[] arrayOfByte = new byte[this.mMetadataLength];
        System.arraycopy(this.mMetadata, 0, arrayOfByte, 0, this.mMetadataLength);
        return arrayOfByte;
      }
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return null;
  }

  public int queryPosition()
  {
    return this.mPosition;
  }

  public String querySampleFormat()
  {
    if (this.mSampleFmt != null)
      return this.mSampleFmt;
    return "unknown";
  }

  public int querySampleRate()
  {
    return this.mSampleRate;
  }

  public String querySelectedUrl()
  {
    if (this.mSelectedUrl != null)
      return this.mSelectedUrl;
    return "none";
  }

  public State queryState()
  {
    return this.mState;
  }

  public ArrayList<String> queryUrls()
  {
    return this.mUrls;
  }

  public boolean removeEventListener(SpectraEventListener paramSpectraEventListener)
  {
    if (this.mEventListeners != null)
      return this.mEventListeners.remove(paramSpectraEventListener);
    return true;
  }

  public boolean resume()
  {
    boolean bool = false;
    this.mStateLock.lock();
    try
    {
      sLogger.info("#------> resume()", new Object[0]);
      this.mEventDispatcher.sendEmptyMessage(SpectraEvent.DO_RESUME.ordinal());
      switch (1.$SwitchMap$rui$lin$spectra$Spectra$State[this.mState.ordinal()])
      {
      default:
        sLogger.warning("resume() called in wrong state, ignore", new Object[0]);
        this.mEventDispatcher.sendEmptyMessage(this.mState.ordinal());
      case 3:
      }
      while (true)
      {
        return bool;
        this.mState = State.PLAYING;
        this.mAudioTrack.play();
        sLogger.info("playback resumed, switch state to PLAYING", new Object[0]);
        this.mEventDispatcher.sendEmptyMessage(SpectraEvent.PLAYING.ordinal());
        bool = true;
      }
    }
    finally
    {
      this.mStateLock.unlock();
    }
  }

  public boolean seek(int paramInt)
  {
    boolean bool = false;
    this.mStateLock.lock();
    while (true)
    {
      try
      {
        sLogger.info("#------> seek(" + paramInt + ")", new Object[0]);
        this.mEventDispatcher.sendEmptyMessage(SpectraEvent.DO_SEEK.ordinal());
        switch (1.$SwitchMap$rui$lin$spectra$Spectra$State[this.mState.ordinal()])
        {
        default:
          sLogger.warning("seek(" + paramInt + ") called in wrong state, ignore", new Object[0]);
          this.mEventDispatcher.sendEmptyMessage(this.mState.ordinal());
          return bool;
        case 1:
          if (isLiveStream())
            break label335;
          this.mBufferEngine.shouldWait = true;
          if (!this.mBufferEngine.isWaiting)
          {
            Thread.yield();
            continue;
          }
          break;
        case 2:
        case 4:
        case 3:
        }
      }
      finally
      {
        this.mStateLock.unlock();
      }
      if (seekStream(paramInt) == 0)
      {
        sLogger.info("seeking to " + paramInt + "s succeeded", new Object[0]);
        this.mEventDispatcher.sendEmptyMessage(SpectraEvent.SEEK_SUCCEEDED.ordinal());
      }
      for (bool = true; ; bool = false)
      {
        this.mBufferEngine.shouldWait = false;
        break;
        sLogger.warning("seeking to " + paramInt + "s failed", new Object[0]);
        this.mEventDispatcher.sendEmptyMessage(SpectraEvent.SEEK_FAILED.ordinal());
      }
      label335: sLogger.warning("seek() called for live stream, ignore", new Object[0]);
      bool = false;
      continue;
      if (!isLiveStream())
      {
        this.mAudioTrack.pause();
        this.mBufferEngine.shouldWait = true;
        this.mPlaybackEngine.shouldWait = true;
        this.mAudioTrack.flush();
        while ((!this.mBufferEngine.isWaiting) || (!this.mPlaybackEngine.isWaiting))
          Thread.yield();
        if (seekStream(paramInt) == 0)
        {
          sLogger.info("seeking to " + paramInt + "s succeeded", new Object[0]);
          this.mEventDispatcher.sendEmptyMessage(SpectraEvent.SEEK_SUCCEEDED.ordinal());
        }
        for (bool = true; ; bool = false)
        {
          this.mPlaybackEngine.shouldWait = false;
          this.mBufferEngine.shouldWait = false;
          this.mAudioTrack.play();
          this.mState = State.PLAYING;
          sLogger.info("switch state to PLAYING", new Object[0]);
          this.mEventDispatcher.sendEmptyMessage(SpectraEvent.PLAYING.ordinal());
          break;
          sLogger.warning("seeking to " + paramInt + "s failed", new Object[0]);
          this.mEventDispatcher.sendEmptyMessage(SpectraEvent.SEEK_FAILED.ordinal());
        }
      }
      sLogger.warning("seek() called for live stream, ignore", new Object[0]);
      bool = false;
    }
    this.mBufferEngine.shouldWait = true;
    this.mPlaybackEngine.shouldWait = true;
    this.mAudioTrack.flush();
    while ((!this.mBufferEngine.isWaiting) || (!this.mPlaybackEngine.isWaiting))
      Thread.yield();
    if (seekStream(paramInt) == 0)
    {
      sLogger.info("seeking to " + paramInt + "s succeeded", new Object[0]);
      this.mEventDispatcher.sendEmptyMessage(SpectraEvent.SEEK_SUCCEEDED.ordinal());
    }
    for (bool = true; ; bool = false)
    {
      this.mPlaybackEngine.shouldWait = false;
      this.mBufferEngine.shouldWait = false;
      break;
      sLogger.warning("seeking to " + paramInt + "s failed", new Object[0]);
      this.mEventDispatcher.sendEmptyMessage(SpectraEvent.SEEK_FAILED.ordinal());
    }
  }

  public void setShouldOpt(boolean paramBoolean)
  {
    this.shouldOpt = paramBoolean;
  }

  // ERROR //
  public boolean stop()
  {
    // Byte code:
    //   0: iconst_1
    //   1: istore_1
    //   2: aload_0
    //   3: getfield 164	rui/lin/spectra/Spectra:mStateLock	Ljava/util/concurrent/locks/Lock;
    //   6: invokeinterface 389 1 0
    //   11: getstatic 143	rui/lin/spectra/Spectra:sLogger	Lrui/lin/spectra/Logger;
    //   14: ldc_w 644
    //   17: iconst_0
    //   18: anewarray 4	java/lang/Object
    //   21: invokevirtual 246	rui/lin/spectra/Logger:info	(Ljava/lang/String;[Ljava/lang/Object;)V
    //   24: aload_0
    //   25: getfield 233	rui/lin/spectra/Spectra:mEventDispatcher	Lrui/lin/spectra/Spectra$SpectraEventDispatcher;
    //   28: getstatic 647	rui/lin/spectra/Spectra$SpectraEvent:DO_STOP	Lrui/lin/spectra/Spectra$SpectraEvent;
    //   31: invokevirtual 254	rui/lin/spectra/Spectra$SpectraEvent:ordinal	()I
    //   34: invokevirtual 258	rui/lin/spectra/Spectra$SpectraEventDispatcher:sendEmptyMessage	(I)Z
    //   37: pop
    //   38: getstatic 400	rui/lin/spectra/Spectra$1:$SwitchMap$rui$lin$spectra$Spectra$State	[I
    //   41: aload_0
    //   42: getfield 159	rui/lin/spectra/Spectra:mState	Lrui/lin/spectra/Spectra$State;
    //   45: invokevirtual 401	rui/lin/spectra/Spectra$State:ordinal	()I
    //   48: iaload
    //   49: tableswitch	default:+31 -> 80, 1:+183->232, 2:+72->121, 3:+72->121, 4:+72->121
    //   81: nop
    //   82: d2l
    //   83: ldc_w 649
    //   86: iconst_0
    //   87: anewarray 4	java/lang/Object
    //   90: invokevirtual 323	rui/lin/spectra/Logger:warning	(Ljava/lang/String;[Ljava/lang/Object;)V
    //   93: aload_0
    //   94: getfield 233	rui/lin/spectra/Spectra:mEventDispatcher	Lrui/lin/spectra/Spectra$SpectraEventDispatcher;
    //   97: aload_0
    //   98: getfield 159	rui/lin/spectra/Spectra:mState	Lrui/lin/spectra/Spectra$State;
    //   101: invokevirtual 401	rui/lin/spectra/Spectra$State:ordinal	()I
    //   104: invokevirtual 258	rui/lin/spectra/Spectra$SpectraEventDispatcher:sendEmptyMessage	(I)Z
    //   107: pop
    //   108: iconst_0
    //   109: istore_1
    //   110: aload_0
    //   111: getfield 164	rui/lin/spectra/Spectra:mStateLock	Ljava/util/concurrent/locks/Lock;
    //   114: invokeinterface 406 1 0
    //   119: iload_1
    //   120: ireturn
    //   121: aload_0
    //   122: getfield 198	rui/lin/spectra/Spectra:mAudioTrack	Landroid/media/AudioTrack;
    //   125: invokevirtual 651	android/media/AudioTrack:stop	()V
    //   128: aload_0
    //   129: getfield 421	rui/lin/spectra/Spectra:mBufferEngine	Lrui/lin/spectra/Spectra$BufferEngine;
    //   132: iconst_1
    //   133: putfield 426	rui/lin/spectra/Spectra$BufferEngine:shouldStop	Z
    //   136: aload_0
    //   137: getfield 516	rui/lin/spectra/Spectra:mPlaybackEngine	Lrui/lin/spectra/Spectra$PlaybackEngine;
    //   140: iconst_1
    //   141: putfield 652	rui/lin/spectra/Spectra$PlaybackEngine:shouldStop	Z
    //   144: aload_0
    //   145: getfield 421	rui/lin/spectra/Spectra:mBufferEngine	Lrui/lin/spectra/Spectra$BufferEngine;
    //   148: invokevirtual 429	rui/lin/spectra/Spectra$BufferEngine:join	()V
    //   151: aload_0
    //   152: getfield 516	rui/lin/spectra/Spectra:mPlaybackEngine	Lrui/lin/spectra/Spectra$PlaybackEngine;
    //   155: invokevirtual 653	rui/lin/spectra/Spectra$PlaybackEngine:join	()V
    //   158: aload_0
    //   159: aconst_null
    //   160: putfield 421	rui/lin/spectra/Spectra:mBufferEngine	Lrui/lin/spectra/Spectra$BufferEngine;
    //   163: aload_0
    //   164: aconst_null
    //   165: putfield 516	rui/lin/spectra/Spectra:mPlaybackEngine	Lrui/lin/spectra/Spectra$PlaybackEngine;
    //   168: aload_0
    //   169: invokespecial 286	rui/lin/spectra/Spectra:closeStream	()I
    //   172: pop
    //   173: aload_0
    //   174: getstatic 236	rui/lin/spectra/Spectra$State:STOPPED	Lrui/lin/spectra/Spectra$State;
    //   177: putfield 159	rui/lin/spectra/Spectra:mState	Lrui/lin/spectra/Spectra$State;
    //   180: getstatic 143	rui/lin/spectra/Spectra:sLogger	Lrui/lin/spectra/Logger;
    //   183: ldc_w 655
    //   186: iconst_0
    //   187: anewarray 4	java/lang/Object
    //   190: invokevirtual 246	rui/lin/spectra/Logger:info	(Ljava/lang/String;[Ljava/lang/Object;)V
    //   193: aload_0
    //   194: getfield 233	rui/lin/spectra/Spectra:mEventDispatcher	Lrui/lin/spectra/Spectra$SpectraEventDispatcher;
    //   197: getstatic 251	rui/lin/spectra/Spectra$SpectraEvent:STOPPED	Lrui/lin/spectra/Spectra$SpectraEvent;
    //   200: invokevirtual 254	rui/lin/spectra/Spectra$SpectraEvent:ordinal	()I
    //   203: invokevirtual 258	rui/lin/spectra/Spectra$SpectraEventDispatcher:sendEmptyMessage	(I)Z
    //   206: pop
    //   207: goto -97 -> 110
    //   210: astore_2
    //   211: aload_0
    //   212: getfield 164	rui/lin/spectra/Spectra:mStateLock	Ljava/util/concurrent/locks/Lock;
    //   215: invokeinterface 406 1 0
    //   220: aload_2
    //   221: athrow
    //   222: astore 9
    //   224: aload 9
    //   226: invokevirtual 458	java/lang/InterruptedException:printStackTrace	()V
    //   229: goto -71 -> 158
    //   232: aload_0
    //   233: invokevirtual 408	rui/lin/spectra/Spectra:isLiveStream	()Z
    //   236: ifeq +52 -> 288
    //   239: aload_0
    //   240: getfield 186	rui/lin/spectra/Spectra:mStateResetTask	Lrui/lin/spectra/Spectra$StateResetTask;
    //   243: iconst_1
    //   244: putfield 411	rui/lin/spectra/Spectra$StateResetTask:shouldCancel	Z
    //   247: aload_0
    //   248: getfield 186	rui/lin/spectra/Spectra:mStateResetTask	Lrui/lin/spectra/Spectra$StateResetTask;
    //   251: invokevirtual 414	rui/lin/spectra/Spectra$StateResetTask:cancel	()Z
    //   254: pop
    //   255: aload_0
    //   256: getfield 179	rui/lin/spectra/Spectra:mStateResetTimer	Ljava/util/Timer;
    //   259: invokevirtual 417	java/util/Timer:purge	()I
    //   262: pop
    //   263: aload_0
    //   264: new 181	rui/lin/spectra/Spectra$StateResetTask
    //   267: dup
    //   268: aload_0
    //   269: invokespecial 184	rui/lin/spectra/Spectra$StateResetTask:<init>	(Lrui/lin/spectra/Spectra;)V
    //   272: putfield 186	rui/lin/spectra/Spectra:mStateResetTask	Lrui/lin/spectra/Spectra$StateResetTask;
    //   275: getstatic 143	rui/lin/spectra/Spectra:sLogger	Lrui/lin/spectra/Logger;
    //   278: ldc_w 419
    //   281: iconst_0
    //   282: anewarray 4	java/lang/Object
    //   285: invokevirtual 246	rui/lin/spectra/Logger:info	(Ljava/lang/String;[Ljava/lang/Object;)V
    //   288: aload_0
    //   289: getfield 421	rui/lin/spectra/Spectra:mBufferEngine	Lrui/lin/spectra/Spectra$BufferEngine;
    //   292: iconst_1
    //   293: putfield 426	rui/lin/spectra/Spectra$BufferEngine:shouldStop	Z
    //   296: aload_0
    //   297: getfield 421	rui/lin/spectra/Spectra:mBufferEngine	Lrui/lin/spectra/Spectra$BufferEngine;
    //   300: invokevirtual 429	rui/lin/spectra/Spectra$BufferEngine:join	()V
    //   303: aload_0
    //   304: aconst_null
    //   305: putfield 421	rui/lin/spectra/Spectra:mBufferEngine	Lrui/lin/spectra/Spectra$BufferEngine;
    //   308: aload_0
    //   309: invokespecial 286	rui/lin/spectra/Spectra:closeStream	()I
    //   312: pop
    //   313: aload_0
    //   314: getstatic 236	rui/lin/spectra/Spectra$State:STOPPED	Lrui/lin/spectra/Spectra$State;
    //   317: putfield 159	rui/lin/spectra/Spectra:mState	Lrui/lin/spectra/Spectra$State;
    //   320: getstatic 143	rui/lin/spectra/Spectra:sLogger	Lrui/lin/spectra/Logger;
    //   323: ldc_w 655
    //   326: iconst_0
    //   327: anewarray 4	java/lang/Object
    //   330: invokevirtual 246	rui/lin/spectra/Logger:info	(Ljava/lang/String;[Ljava/lang/Object;)V
    //   333: aload_0
    //   334: getfield 233	rui/lin/spectra/Spectra:mEventDispatcher	Lrui/lin/spectra/Spectra$SpectraEventDispatcher;
    //   337: getstatic 251	rui/lin/spectra/Spectra$SpectraEvent:STOPPED	Lrui/lin/spectra/Spectra$SpectraEvent;
    //   340: invokevirtual 254	rui/lin/spectra/Spectra$SpectraEvent:ordinal	()I
    //   343: invokevirtual 258	rui/lin/spectra/Spectra$SpectraEventDispatcher:sendEmptyMessage	(I)Z
    //   346: pop
    //   347: goto -237 -> 110
    //   350: astore 4
    //   352: aload 4
    //   354: invokevirtual 458	java/lang/InterruptedException:printStackTrace	()V
    //   357: goto -54 -> 303
    //
    // Exception table:
    //   from	to	target	type
    //   11	80	210	finally
    //   80	108	210	finally
    //   121	144	210	finally
    //   144	158	210	finally
    //   158	207	210	finally
    //   224	229	210	finally
    //   232	288	210	finally
    //   288	296	210	finally
    //   296	303	210	finally
    //   303	347	210	finally
    //   352	357	210	finally
    //   144	158	222	java/lang/InterruptedException
    //   296	303	350	java/lang/InterruptedException
  }

  protected class BufferEngine extends Thread
  {
    public boolean isWaiting = false;
    private long pre_reading_success_time = 0L;
    public boolean shouldStop = false;
    public boolean shouldWait = false;

    public BufferEngine()
    {
      setPriority(5);
    }

    public void run()
    {
      while (true)
      {
        if (!this.shouldStop)
          if (this.shouldWait)
          {
            this.isWaiting = true;
            Spectra.sLogger.info("BufferEngine is waiting", new Object[0]);
            while (this.shouldWait)
              yield();
            this.isWaiting = false;
            Spectra.sLogger.info("BufferEngine continues execution", new Object[0]);
          }
        switch (Spectra.1.$SwitchMap$rui$lin$spectra$Spectra$State[Spectra.this.mState.ordinal()])
        {
        default:
          Spectra.sLogger.warning("BufferEngine running in wrong state: " + Spectra.this.mState + ", stop BufferEngine", new Object[0]);
          Spectra.sLogger.info("BufferEngine stopped", new Object[0]);
          return;
        case 1:
        case 2:
        case 3:
          int i = Spectra.this.readPacket();
          if (i == 0)
          {
            this.pre_reading_success_time = SystemClock.elapsedRealtime();
            if (Spectra.this.mLowCPUCostRetry)
            {
              Spectra.access$202(Spectra.this, false);
              Spectra.sLogger.info("exit low-CPU-cost-retry state", new Object[0]);
            }
            yield();
            continue;
          }
          if (i == 2001)
          {
            Logger localLogger4 = Spectra.sLogger;
            Object[] arrayOfObject4 = new Object[1];
            arrayOfObject4[0] = Integer.valueOf(300);
            localLogger4.debug("buffer full, sleep(%d)", arrayOfObject4);
            try
            {
              sleep(300L);
            }
            catch (InterruptedException localInterruptedException4)
            {
              localInterruptedException4.printStackTrace();
            }
            continue;
          }
          if ((i == -541478725) && (!Spectra.this.isLiveStream()))
          {
            if (Spectra.this.mStateLock.tryLock())
            {
              if (Spectra.this.mState == Spectra.State.PLAYING)
              {
                Spectra.access$002(Spectra.this, Spectra.State.STOPPING);
                Spectra.this.mStateLock.unlock();
                Spectra.sLogger.info("true EOF reached in PLAYING state, switch state to STOPPING", new Object[0]);
                Spectra.this.mEventDispatcher.sendEmptyMessage(Spectra.SpectraEvent.STOPPING.ordinal());
                continue;
              }
              Spectra.this.mStateLock.unlock();
              Logger localLogger3 = Spectra.sLogger;
              String str = "true EOF reached in " + Spectra.this.mState + "state, ignore EOF and sleep(%d)";
              Object[] arrayOfObject3 = new Object[1];
              arrayOfObject3[0] = Integer.valueOf(100);
              localLogger3.warning(str, arrayOfObject3);
              try
              {
                sleep(100L);
              }
              catch (InterruptedException localInterruptedException3)
              {
                localInterruptedException3.printStackTrace();
              }
              continue;
            }
            Spectra.sLogger.warning("true EOF is reached but tryLock failed (state is switching). Ignore EOF and yield", new Object[0]);
            yield();
            continue;
          }
          if ((!Spectra.this.mLowCPUCostRetry) && (SystemClock.elapsedRealtime() - this.pre_reading_success_time > 20000L))
          {
            Spectra.access$202(Spectra.this, true);
            Spectra.sLogger.info("enter low-CPU-cost-retry state", new Object[0]);
          }
          Logger localLogger2 = Spectra.sLogger;
          Object[] arrayOfObject2 = new Object[1];
          arrayOfObject2[0] = Integer.valueOf(i);
          localLogger2.warning("readPacket error #%d, yield", arrayOfObject2);
          Spectra.this.mEventDispatcher.sendEmptyMessage(Spectra.SpectraEvent.READ_PACKET_ERROR.ordinal());
          if (Spectra.this.mLowCPUCostRetry)
          {
            try
            {
              sleep(1500L);
            }
            catch (InterruptedException localInterruptedException2)
            {
              localInterruptedException2.printStackTrace();
            }
            continue;
          }
          yield();
          break;
        case 4:
        }
        Logger localLogger1 = Spectra.sLogger;
        Object[] arrayOfObject1 = new Object[1];
        arrayOfObject1[0] = Integer.valueOf(100);
        localLogger1.debug("BufferEngine running in STOPPING state, sleep(%d)", arrayOfObject1);
        try
        {
          sleep(100L);
        }
        catch (InterruptedException localInterruptedException1)
        {
          localInterruptedException1.printStackTrace();
        }
      }
    }
  }

  protected class PlaybackEngine extends Thread
  {
    public boolean isWaiting = false;
    public boolean shouldStop = false;
    public boolean shouldWait = false;

    public PlaybackEngine()
    {
      setPriority(10);
    }

    public void run()
    {
      try
      {
        while (true)
        {
          if (!this.shouldStop)
            if (this.shouldWait)
            {
              this.isWaiting = true;
              Spectra.sLogger.info("PlaybackEngine is waiting", new Object[0]);
              while (this.shouldWait)
                yield();
              Spectra.sLogger.info("PlaybackEngine continues execution", new Object[0]);
              this.isWaiting = false;
            }
          switch (Spectra.1.$SwitchMap$rui$lin$spectra$Spectra$State[Spectra.this.mState.ordinal()])
          {
          default:
            Spectra.sLogger.warning("PlaybackEngine running in wrong state: " + Spectra.this.mState + ", stop PlaybackEngine", new Object[0]);
          case 2:
          case 4:
            int i;
            while (true)
            {
              Spectra.sLogger.info("PlaybackEngine stopped", new Object[0]);
              return;
              i = Spectra.this.decodeFrame();
              if (i == 0)
              {
                Spectra.this.mAudioTrack.write(Spectra.this.mWaveform, 0, Spectra.this.mWaveformLength);
                Logger localLogger2 = Spectra.sLogger;
                Object[] arrayOfObject2 = new Object[1];
                arrayOfObject2[0] = Integer.valueOf(Spectra.this.mWaveformLength);
                localLogger2.debug("feed %d bytes to mAudioTrack", arrayOfObject2);
                Spectra.this.mEventDispatcher.sendEmptyMessage(Spectra.SpectraEvent.PLAYING.ordinal());
                break;
              }
              if (i == 1)
              {
                Logger localLogger3 = Spectra.sLogger;
                Object[] arrayOfObject3 = new Object[1];
                arrayOfObject3[0] = Integer.valueOf(Spectra.this.mMetadataLength);
                localLogger3.info("%d bytes ID3v2 tag stored in mMetadata", arrayOfObject3);
                Spectra.this.mEventDispatcher.sendEmptyMessage(Spectra.SpectraEvent.ID3V2.ordinal());
                break;
              }
              if ((i != 2000) || (Spectra.this.mState != Spectra.State.STOPPING))
                break label571;
              boolean bool2 = Spectra.this.mStateLock.tryLock();
              if (!bool2)
                break label553;
              try
              {
                if (Spectra.this.mState == Spectra.State.STOPPING)
                {
                  Spectra.this.mEventDispatcher.sendEmptyMessage(Spectra.SpectraEvent.END_OF_STREAM.ordinal());
                  Spectra.sLogger.info("packet buffer empty in STOPPING state, switch state to STOPPED and stop PlaybackEngine", new Object[0]);
                  Spectra.this.mAudioTrack.stop();
                  Spectra.this.mBufferEngine.shouldStop = true;
                  try
                  {
                    Spectra.this.mBufferEngine.join();
                    Spectra.this.mBufferEngine = null;
                    Spectra.this.mPlaybackEngine = null;
                    Spectra.this.closeStream();
                    Spectra.access$002(Spectra.this, Spectra.State.STOPPED);
                    Spectra.sLogger.info("state reset to STOPPED", new Object[0]);
                    Spectra.this.mEventDispatcher.sendEmptyMessage(Spectra.SpectraEvent.STOPPED.ordinal());
                  }
                  catch (InterruptedException localInterruptedException4)
                  {
                    while (true)
                      localInterruptedException4.printStackTrace();
                  }
                }
              }
              finally
              {
                Spectra.this.mStateLock.unlock();
              }
            }
            Spectra.sLogger.warning("tryLock succeeded but state is no longer STOPPING. Ignore", new Object[0]);
            Spectra.this.mStateLock.unlock();
            continue;
            label553: Spectra.sLogger.warning("packet buffer empty happened in STOPPING state, but tryLock failed (state is switching). Yield", new Object[0]);
            yield();
            continue;
            label571: if (i == 2000)
            {
              if (Spectra.this.shouldOpt)
              {
                Spectra.sLogger.debug("packet buffer empty, yield execution to another thread", new Object[0]);
                Spectra.this.mEventDispatcher.sendEmptyMessage(Spectra.SpectraEvent.BUFFER_EMPTY.ordinal());
                try
                {
                  Logger localLogger4 = Spectra.sLogger;
                  Object[] arrayOfObject4 = new Object[1];
                  arrayOfObject4[0] = Long.valueOf(500L);
                  localLogger4.error("packet buffer empty,sleep %d", arrayOfObject4);
                  sleep(500L);
                }
                catch (InterruptedException localInterruptedException3)
                {
                  localInterruptedException3.printStackTrace();
                }
                continue;
              }
              Spectra.sLogger.debug("packet buffer empty, yield execution to another thread", new Object[0]);
              Spectra.this.mEventDispatcher.sendEmptyMessage(Spectra.SpectraEvent.BUFFER_EMPTY.ordinal());
              boolean bool1 = Spectra.this.mLowCPUCostRetry;
              if (bool1)
              {
                try
                {
                  sleep(1500L);
                }
                catch (InterruptedException localInterruptedException2)
                {
                  localInterruptedException2.printStackTrace();
                }
                continue;
              }
              yield();
              continue;
            }
            Spectra.sLogger.warning("decode frame error #" + i, new Object[0]);
            Spectra.this.mEventDispatcher.sendEmptyMessage(Spectra.SpectraEvent.DECODE_FRAME_ERROR.ordinal());
            break;
          case 3:
          }
          Logger localLogger1 = Spectra.sLogger;
          Object[] arrayOfObject1 = new Object[1];
          arrayOfObject1[0] = Integer.valueOf(100);
          localLogger1.debug("PlaybackEngine running in PAUSED state, sleep(%d)", arrayOfObject1);
          try
          {
            sleep(100L);
          }
          catch (InterruptedException localInterruptedException1)
          {
            localInterruptedException1.printStackTrace();
          }
        }
      }
      catch (Exception localException)
      {
      }
    }
  }

  public static enum SpectraEvent
  {
    static
    {
      STOPPED = new SpectraEvent("STOPPED", 1);
      LOADED = new SpectraEvent("LOADED", 2);
      PLAYING = new SpectraEvent("PLAYING", 3);
      PAUSED = new SpectraEvent("PAUSED", 4);
      STOPPING = new SpectraEvent("STOPPING", 5);
      DO_LOAD = new SpectraEvent("DO_LOAD", 6);
      DO_PLAY = new SpectraEvent("DO_PLAY", 7);
      DO_STOP = new SpectraEvent("DO_STOP", 8);
      DO_PAUSE = new SpectraEvent("DO_PAUSE", 9);
      DO_RESUME = new SpectraEvent("DO_RESUME", 10);
      DO_SEEK = new SpectraEvent("DO_SEEK", 11);
      SEEK_SUCCEEDED = new SpectraEvent("SEEK_SUCCEEDED", 12);
      SEEK_FAILED = new SpectraEvent("SEEK_FAILED", 13);
      BUFFER_EMPTY = new SpectraEvent("BUFFER_EMPTY", 14);
      OPEN_STREAM_FAILED = new SpectraEvent("OPEN_STREAM_FAILED", 15);
      READ_PACKET_ERROR = new SpectraEvent("READ_PACKET_ERROR", 16);
      DECODE_FRAME_ERROR = new SpectraEvent("DECODE_FRAME_ERROR", 17);
      END_OF_STREAM = new SpectraEvent("END_OF_STREAM", 18);
      ID3V2 = new SpectraEvent("ID3V2", 19);
      SpectraEvent[] arrayOfSpectraEvent = new SpectraEvent[20];
      arrayOfSpectraEvent[0] = UNINITIALIZED;
      arrayOfSpectraEvent[1] = STOPPED;
      arrayOfSpectraEvent[2] = LOADED;
      arrayOfSpectraEvent[3] = PLAYING;
      arrayOfSpectraEvent[4] = PAUSED;
      arrayOfSpectraEvent[5] = STOPPING;
      arrayOfSpectraEvent[6] = DO_LOAD;
      arrayOfSpectraEvent[7] = DO_PLAY;
      arrayOfSpectraEvent[8] = DO_STOP;
      arrayOfSpectraEvent[9] = DO_PAUSE;
      arrayOfSpectraEvent[10] = DO_RESUME;
      arrayOfSpectraEvent[11] = DO_SEEK;
      arrayOfSpectraEvent[12] = SEEK_SUCCEEDED;
      arrayOfSpectraEvent[13] = SEEK_FAILED;
      arrayOfSpectraEvent[14] = BUFFER_EMPTY;
      arrayOfSpectraEvent[15] = OPEN_STREAM_FAILED;
      arrayOfSpectraEvent[16] = READ_PACKET_ERROR;
      arrayOfSpectraEvent[17] = DECODE_FRAME_ERROR;
      arrayOfSpectraEvent[18] = END_OF_STREAM;
      arrayOfSpectraEvent[19] = ID3V2;
    }
  }

  class SpectraEventDispatcher extends Handler
  {
    private Spectra.SpectraEvent pre_event = null;

    public SpectraEventDispatcher(Looper arg2)
    {
      super();
    }

    public void handleMessage(Message paramMessage)
    {
      Spectra.SpectraEvent localSpectraEvent = Spectra.SpectraEvent.values()[paramMessage.what];
      if (localSpectraEvent != this.pre_event)
      {
        Spectra.sLogger.debug(Spectra.this + " is dispatching event " + localSpectraEvent, new Object[0]);
        Iterator localIterator = Spectra.this.mEventListeners.iterator();
        while (localIterator.hasNext())
        {
          Spectra.SpectraEventListener localSpectraEventListener = (Spectra.SpectraEventListener)localIterator.next();
          try
          {
            localSpectraEventListener.onSpectraEvent(Spectra.this, localSpectraEvent);
          }
          catch (Exception localException)
          {
            localException.printStackTrace();
          }
        }
        this.pre_event = localSpectraEvent;
      }
    }
  }

  public static abstract interface SpectraEventListener
  {
    public abstract void onSpectraEvent(Spectra paramSpectra, Spectra.SpectraEvent paramSpectraEvent);
  }

  public static enum State
  {
    static
    {
      STOPPED = new State("STOPPED", 1);
      LOADED = new State("LOADED", 2);
      PLAYING = new State("PLAYING", 3);
      PAUSED = new State("PAUSED", 4);
      STOPPING = new State("STOPPING", 5);
      State[] arrayOfState = new State[6];
      arrayOfState[0] = UNINITIALIZED;
      arrayOfState[1] = STOPPED;
      arrayOfState[2] = LOADED;
      arrayOfState[3] = PLAYING;
      arrayOfState[4] = PAUSED;
      arrayOfState[5] = STOPPING;
    }
  }

  protected class StateResetTask extends TimerTask
  {
    boolean shouldCancel = false;

    protected StateResetTask()
    {
    }

    public void run()
    {
      Spectra.this.mStateLock.lock();
      try
      {
        if ((!this.shouldCancel) && (Spectra.this.mState == Spectra.State.LOADED))
          Spectra.this.mBufferEngine.shouldStop = true;
        try
        {
          Spectra.this.mBufferEngine.join();
          Spectra.this.mBufferEngine = null;
          Spectra.this.closeStream();
          Spectra.this.mStateResetTask = new StateResetTask(Spectra.this);
          Spectra.access$002(Spectra.this, Spectra.State.STOPPED);
          Spectra.sLogger.info("time elapsed, state reset to STOPPED", new Object[0]);
          Spectra.this.mEventDispatcher.sendEmptyMessage(Spectra.SpectraEvent.STOPPED.ordinal());
          return;
        }
        catch (InterruptedException localInterruptedException)
        {
          while (true)
            localInterruptedException.printStackTrace();
        }
      }
      finally
      {
        Spectra.this.mStateLock.unlock();
      }
    }
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     rui.lin.spectra.Spectra
 * JD-Core Version:    0.6.2
 */