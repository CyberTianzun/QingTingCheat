package master.flame.danmaku.controller;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.SystemClock;
import android.util.DisplayMetrics;
import java.util.LinkedList;
import master.flame.danmaku.danmaku.model.AbsDisplayer;
import master.flame.danmaku.danmaku.model.BaseDanmaku;
import master.flame.danmaku.danmaku.model.DanmakuTimer;
import master.flame.danmaku.danmaku.model.GlobalFlagValues;
import master.flame.danmaku.danmaku.model.IDisplayer;
import master.flame.danmaku.danmaku.model.android.AndroidDisplayer;
import master.flame.danmaku.danmaku.model.android.DanmakuGlobalConfig;
import master.flame.danmaku.danmaku.parser.BaseDanmakuParser;
import master.flame.danmaku.danmaku.parser.DanmakuFactory;
import master.flame.danmaku.danmaku.renderer.IRenderer.RenderingState;
import master.flame.danmaku.danmaku.util.AndroidUtils;
import tv.cjump.jni.DeviceUtils;

public class DrawHandler extends Handler
{
  private static final int CLEAR_DANMAKUS_ON_SCREEN = 13;
  private static final int HIDE_DANMAKUS = 9;
  private static final long INDEFINITE_TIME = 10000000L;
  private static final int MAX_RECORD_SIZE = 500;
  private static final int NOTIFY_DISP_SIZE_CHANGED = 10;
  private static final int NOTIFY_RENDERING = 11;
  private static final int PAUSE = 7;
  public static final int PREPARE = 5;
  private static final int QUIT = 6;
  public static final int RESUME = 3;
  public static final int SEEK_POS = 4;
  private static final int SHOW_DANMAKUS = 8;
  public static final int START = 1;
  public static final int UPDATE = 2;
  private static final int UPDATE_WHEN_PAUSED = 12;
  public IDrawTask drawTask;
  private Callback mCallback;
  private long mCordonTime;
  private long mCordonTime2;
  private IDanmakuViewController mDanmakuView;
  private boolean mDanmakusVisible;
  private AbsDisplayer<Canvas> mDisp;
  private LinkedList<Long> mDrawTimes;
  private long mFrameUpdateRate;
  private boolean mIdleSleep;
  private boolean mInSeekingAction;
  private boolean mInSyncAction;
  private boolean mInWaitingState;
  private long mLastDeltaTime;
  private BaseDanmakuParser mParser;
  private boolean mReady;
  private long mRemainingTime;
  private final IRenderer.RenderingState mRenderingState;
  private int mSkipFrames;
  private UpdateThread mThread;
  private long mThresholdTime;
  private long mTimeBase;
  private final boolean mUpdateInNewThread;
  private long pausedPosition = 0L;
  private boolean quitFlag;
  private DanmakuTimer timer;

  public DrawHandler(Looper paramLooper, IDanmakuViewController paramIDanmakuViewController, boolean paramBoolean)
  {
    super(paramLooper);
    this.quitFlag = bool1;
    this.timer = new DanmakuTimer();
    this.mDanmakusVisible = bool1;
    this.mRenderingState = new IRenderer.RenderingState();
    this.mDrawTimes = new LinkedList();
    this.mCordonTime = 30L;
    this.mCordonTime2 = 60L;
    this.mFrameUpdateRate = 16L;
    boolean bool2;
    if (Runtime.getRuntime().availableProcessors() > 3)
    {
      bool2 = bool1;
      this.mUpdateInNewThread = bool2;
      if (DeviceUtils.isProblemBoxDevice())
        break label137;
      label105: this.mIdleSleep = bool1;
      bindView(paramIDanmakuViewController);
      if (!paramBoolean)
        break label143;
      showDanmakus(null);
    }
    while (true)
    {
      this.mDanmakusVisible = paramBoolean;
      return;
      bool2 = false;
      break;
      label137: bool1 = false;
      break label105;
      label143: hideDanmakus(false);
    }
  }

  private void bindView(IDanmakuViewController paramIDanmakuViewController)
  {
    this.mDanmakuView = paramIDanmakuViewController;
  }

  private IDrawTask createDrawTask(boolean paramBoolean1, DanmakuTimer paramDanmakuTimer, Context paramContext, int paramInt1, int paramInt2, boolean paramBoolean2, IDrawTask.TaskListener paramTaskListener)
  {
    this.mDisp = new AndroidDisplayer();
    this.mDisp.setContext(paramContext);
    this.mDisp.setSize(paramInt1, paramInt2);
    DisplayMetrics localDisplayMetrics = paramContext.getResources().getDisplayMetrics();
    this.mDisp.setDensities(localDisplayMetrics.density, localDisplayMetrics.densityDpi, localDisplayMetrics.scaledDensity);
    this.mDisp.resetSlopPixel(DanmakuGlobalConfig.DEFAULT.scaleTextSize);
    this.mDisp.setHardwareAccelerated(paramBoolean2);
    obtainMessage(10, Boolean.valueOf(false)).sendToTarget();
    if (paramBoolean1);
    for (Object localObject = new CacheManagingDrawTask(paramDanmakuTimer, paramContext, this.mDisp, paramTaskListener, 1048576 * AndroidUtils.getMemoryClass(paramContext) / 3); ; localObject = new DrawTask(paramDanmakuTimer, paramContext, this.mDisp, paramTaskListener))
    {
      ((IDrawTask)localObject).setParser(this.mParser);
      ((IDrawTask)localObject).prepare();
      return localObject;
    }
  }

  private long getAverageRenderingTime()
  {
    try
    {
      int i = this.mDrawTimes.size();
      if (i <= 0);
      for (long l = 0L; ; l = (((Long)this.mDrawTimes.getLast()).longValue() - ((Long)this.mDrawTimes.getFirst()).longValue()) / i)
        return l;
    }
    finally
    {
    }
  }

  private void initRenderingConfigs()
  {
    this.mCordonTime = Math.max(33L, ()(2.5F * (float)16L));
    this.mCordonTime2 = (2L * this.mCordonTime);
    this.mFrameUpdateRate = Math.max(16L, 15L * (16L / 15L));
    this.mLastDeltaTime = this.mFrameUpdateRate;
    this.mThresholdTime = (3L + this.mFrameUpdateRate);
  }

  // ERROR //
  private void notifyRendering()
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 333	master/flame/danmaku/controller/DrawHandler:mInWaitingState	Z
    //   4: ifne +4 -> 8
    //   7: return
    //   8: aload_0
    //   9: getfield 335	master/flame/danmaku/controller/DrawHandler:drawTask	Lmaster/flame/danmaku/controller/IDrawTask;
    //   12: ifnull +12 -> 24
    //   15: aload_0
    //   16: getfield 335	master/flame/danmaku/controller/DrawHandler:drawTask	Lmaster/flame/danmaku/controller/IDrawTask;
    //   19: invokeinterface 338 1 0
    //   24: aload_0
    //   25: iconst_0
    //   26: putfield 340	master/flame/danmaku/controller/DrawHandler:mSkipFrames	I
    //   29: aload_0
    //   30: getfield 127	master/flame/danmaku/controller/DrawHandler:mUpdateInNewThread	Z
    //   33: ifeq +48 -> 81
    //   36: aload_0
    //   37: monitorenter
    //   38: aload_0
    //   39: getfield 103	master/flame/danmaku/controller/DrawHandler:mDrawTimes	Ljava/util/LinkedList;
    //   42: invokevirtual 343	java/util/LinkedList:clear	()V
    //   45: aload_0
    //   46: monitorexit
    //   47: aload_0
    //   48: getfield 335	master/flame/danmaku/controller/DrawHandler:drawTask	Lmaster/flame/danmaku/controller/IDrawTask;
    //   51: astore_3
    //   52: aload_3
    //   53: monitorenter
    //   54: aload_0
    //   55: getfield 335	master/flame/danmaku/controller/DrawHandler:drawTask	Lmaster/flame/danmaku/controller/IDrawTask;
    //   58: invokevirtual 348	java/lang/Object:notifyAll	()V
    //   61: aload_3
    //   62: monitorexit
    //   63: aload_0
    //   64: iconst_0
    //   65: putfield 333	master/flame/danmaku/controller/DrawHandler:mInWaitingState	Z
    //   68: return
    //   69: astore_2
    //   70: aload_0
    //   71: monitorexit
    //   72: aload_2
    //   73: athrow
    //   74: astore 4
    //   76: aload_3
    //   77: monitorexit
    //   78: aload 4
    //   80: athrow
    //   81: aload_0
    //   82: getfield 103	master/flame/danmaku/controller/DrawHandler:mDrawTimes	Ljava/util/LinkedList;
    //   85: invokevirtual 343	java/util/LinkedList:clear	()V
    //   88: aload_0
    //   89: iconst_2
    //   90: invokevirtual 352	master/flame/danmaku/controller/DrawHandler:removeMessages	(I)V
    //   93: aload_0
    //   94: iconst_2
    //   95: invokevirtual 356	master/flame/danmaku/controller/DrawHandler:sendEmptyMessage	(I)Z
    //   98: pop
    //   99: goto -36 -> 63
    //
    // Exception table:
    //   from	to	target	type
    //   38	47	69	finally
    //   70	72	69	finally
    //   54	63	74	finally
    //   76	78	74	finally
  }

  private void prepare(final Runnable paramRunnable)
  {
    if (this.drawTask == null)
    {
      this.drawTask = createDrawTask(this.mDanmakuView.isDanmakuDrawingCacheEnabled(), this.timer, this.mDanmakuView.getContext(), this.mDanmakuView.getWidth(), this.mDanmakuView.getHeight(), this.mDanmakuView.isHardwareAccelerated(), new IDrawTask.TaskListener()
      {
        public void onDanmakuAdd(BaseDanmaku paramAnonymousBaseDanmaku)
        {
          DrawHandler.this.obtainMessage(11).sendToTarget();
        }

        public void onDanmakuConfigChanged()
        {
          if ((DrawHandler.this.quitFlag) && (DrawHandler.this.mDanmakusVisible))
            DrawHandler.this.obtainMessage(12).sendToTarget();
        }

        public void ready()
        {
          DrawHandler.this.initRenderingConfigs();
          paramRunnable.run();
        }
      });
      return;
    }
    paramRunnable.run();
  }

  private void quitUpdateThread()
  {
    if (this.mThread != null)
      synchronized (this.drawTask)
      {
        this.drawTask.notifyAll();
        this.mThread.quit();
      }
    try
    {
      this.mThread.join();
      this.mThread = null;
      return;
      localObject = finally;
      throw localObject;
    }
    catch (InterruptedException localInterruptedException)
    {
      while (true)
        localInterruptedException.printStackTrace();
    }
  }

  private void recordRenderingTime()
  {
    try
    {
      long l = System.currentTimeMillis();
      this.mDrawTimes.addLast(Long.valueOf(l));
      if (this.mDrawTimes.size() > 500)
        this.mDrawTimes.removeFirst();
      return;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }

  private final long syncTimer(long paramLong)
  {
    long l1 = 0L;
    if ((this.mInSeekingAction) || (this.mInSyncAction))
      return l1;
    this.mInSyncAction = true;
    long l2 = paramLong - this.mTimeBase;
    if ((!this.mDanmakusVisible) || (this.mRenderingState.nothingRendered) || (this.mInWaitingState))
    {
      this.timer.update(l2);
      this.mRemainingTime = l1;
      l3 = l1;
      if (this.mCallback != null)
        this.mCallback.updateTimer(this.timer);
      this.mInSyncAction = false;
      return l3;
    }
    long l3 = l2 - this.timer.currMillisecond;
    long l4 = Math.max(this.mFrameUpdateRate, getAverageRenderingTime());
    if ((l3 > 2000L) || (this.mRenderingState.consumingTime > this.mCordonTime) || (l4 > this.mCordonTime));
    while (true)
    {
      this.mLastDeltaTime = l3;
      this.mRemainingTime = l1;
      this.timer.add(l3);
      break;
      long l5 = l4 + l3 / this.mFrameUpdateRate;
      long l6 = Math.max(this.mFrameUpdateRate, l5);
      long l7 = Math.min(this.mCordonTime, l6);
      if ((Math.abs(l7 - this.mLastDeltaTime) < 4L) && (l7 > this.mFrameUpdateRate) && (this.mLastDeltaTime > this.mFrameUpdateRate))
        l7 = this.mLastDeltaTime;
      long l8 = l3 - l7;
      l3 = l7;
      l1 = l8;
    }
  }

  private void syncTimerIfNeeded()
  {
    if (this.mInWaitingState)
      syncTimer(System.currentTimeMillis());
  }

  private void updateInCurrentThread()
  {
    if (this.quitFlag)
      return;
    long l1 = syncTimer(System.currentTimeMillis());
    if (l1 < 0L)
    {
      removeMessages(2);
      sendEmptyMessageDelayed(2, 60L - l1);
      return;
    }
    long l2 = this.mDanmakuView.drawDanmakus();
    removeMessages(2);
    if (!this.mDanmakusVisible)
    {
      waitRendering(10000000L);
      return;
    }
    if ((this.mRenderingState.nothingRendered) && (this.mIdleSleep))
    {
      long l3 = this.mRenderingState.endTime - this.timer.currMillisecond;
      if (l3 > 500L)
      {
        waitRendering(l3 - 400L);
        return;
      }
    }
    if (l2 < this.mFrameUpdateRate)
    {
      sendEmptyMessageDelayed(2, this.mFrameUpdateRate - l2);
      return;
    }
    sendEmptyMessage(2);
  }

  private void updateInNewThread()
  {
    if (this.mThread != null)
      return;
    this.mThread = new UpdateThread("DFM Update")
    {
      public void run()
      {
        long l1 = System.currentTimeMillis();
        while ((!isQuited()) && (!DrawHandler.this.quitFlag))
        {
          long l2 = System.currentTimeMillis();
          long l3 = System.currentTimeMillis() - l1;
          if (DrawHandler.this.mFrameUpdateRate - l3 > 1L)
          {
            SystemClock.sleep(1L);
          }
          else
          {
            long l4 = DrawHandler.this.syncTimer(l2);
            if (l4 < 0L)
            {
              SystemClock.sleep(60L - l4);
              l1 = l2;
            }
            else
            {
              DrawHandler.this.mDanmakuView.drawDanmakus();
              if (!DrawHandler.this.mDanmakusVisible)
                DrawHandler.this.waitRendering(10000000L);
              while (true)
              {
                l1 = l2;
                break;
                if ((DrawHandler.this.mRenderingState.nothingRendered) && (DrawHandler.this.mIdleSleep))
                {
                  long l5 = DrawHandler.this.mRenderingState.endTime - DrawHandler.this.timer.currMillisecond;
                  if (l5 > 500L)
                  {
                    DrawHandler.this.notifyRendering();
                    DrawHandler.this.waitRendering(l5 - 400L);
                  }
                }
              }
            }
          }
        }
      }
    };
    this.mThread.start();
  }

  private void waitRendering(long paramLong)
  {
    this.mRenderingState.sysTime = System.currentTimeMillis();
    this.mInWaitingState = true;
    if (this.mUpdateInNewThread)
      try
      {
        IDrawTask localIDrawTask = this.drawTask;
        if (paramLong == 10000000L);
        try
        {
          this.drawTask.wait();
          while (true)
          {
            sendEmptyMessage(11);
            return;
            this.drawTask.wait(paramLong);
          }
        }
        finally
        {
        }
      }
      catch (InterruptedException localInterruptedException)
      {
        localInterruptedException.printStackTrace();
        return;
      }
    if (paramLong == 10000000L)
    {
      removeMessages(11);
      removeMessages(2);
      return;
    }
    removeMessages(11);
    removeMessages(2);
    sendEmptyMessageDelayed(11, paramLong);
  }

  public void addDanmaku(BaseDanmaku paramBaseDanmaku)
  {
    if (this.drawTask != null)
    {
      paramBaseDanmaku.setTimer(this.timer);
      this.drawTask.addDanmaku(paramBaseDanmaku);
      obtainMessage(11).sendToTarget();
    }
  }

  public void clearDanmakusOnScreen()
  {
    obtainMessage(13).sendToTarget();
  }

  public IRenderer.RenderingState draw(Canvas paramCanvas)
  {
    if (this.drawTask == null)
      return this.mRenderingState;
    this.mDisp.setExtraData(paramCanvas);
    this.mRenderingState.set(this.drawTask.draw(this.mDisp));
    recordRenderingTime();
    return this.mRenderingState;
  }

  public long getCurrentTime()
  {
    if ((this.quitFlag) || (!this.mInWaitingState))
      return this.timer.currMillisecond - this.mRemainingTime;
    return System.currentTimeMillis() - this.mTimeBase;
  }

  public IDisplayer getDisplayer()
  {
    return this.mDisp;
  }

  public boolean getVisibility()
  {
    return this.mDanmakusVisible;
  }

  public void handleMessage(Message paramMessage)
  {
    int i = paramMessage.what;
    switch (i)
    {
    default:
    case 5:
    case 1:
    case 3:
    case 4:
    case 2:
    case 10:
    case 8:
    case 9:
    case 7:
    case 6:
    case 11:
    case 12:
    case 13:
    }
    label457: 
    do
    {
      do
      {
        do
        {
          do
          {
            Boolean localBoolean1;
            do
            {
              Boolean localBoolean2;
              do
              {
                return;
                if ((this.mParser == null) || (!this.mDanmakuView.isViewReady()))
                {
                  sendEmptyMessageDelayed(5, 100L);
                  return;
                }
                prepare(new Runnable()
                {
                  public void run()
                  {
                    DrawHandler.access$002(DrawHandler.this, true);
                    if (DrawHandler.this.mCallback != null)
                      DrawHandler.this.mCallback.prepared();
                  }
                });
                return;
                Long localLong2 = (Long)paramMessage.obj;
                if (localLong2 != null);
                for (this.pausedPosition = localLong2.longValue(); ; this.pausedPosition = 0L)
                {
                  this.quitFlag = false;
                  if (!this.mReady)
                    break;
                  this.mDrawTimes.clear();
                  this.mTimeBase = (System.currentTimeMillis() - this.pausedPosition);
                  this.timer.update(this.pausedPosition);
                  removeMessages(3);
                  sendEmptyMessage(2);
                  this.drawTask.start();
                  notifyRendering();
                  this.mInSeekingAction = false;
                  return;
                }
                sendEmptyMessageDelayed(3, 100L);
                return;
                this.quitFlag = true;
                quitUpdateThread();
                long l = ((Long)paramMessage.obj).longValue() - this.timer.currMillisecond;
                this.mTimeBase -= l;
                this.timer.update(System.currentTimeMillis() - this.mTimeBase);
                if (this.drawTask != null)
                  this.drawTask.seek(this.timer.currMillisecond);
                this.pausedPosition = this.timer.currMillisecond;
                removeMessages(3);
                sendEmptyMessage(3);
                return;
                if (this.mUpdateInNewThread)
                {
                  updateInNewThread();
                  return;
                }
                updateInCurrentThread();
                return;
                DanmakuFactory.notifyDispSizeChanged(this.mDisp);
                localBoolean2 = (Boolean)paramMessage.obj;
              }
              while ((localBoolean2 == null) || (!localBoolean2.booleanValue()));
              GlobalFlagValues.updateMeasureFlag();
              return;
              Long localLong1 = (Long)paramMessage.obj;
              if (this.drawTask != null)
              {
                if (localLong1 != null)
                  break label457;
                this.timer.update(getCurrentTime());
                this.drawTask.requestClear();
              }
              while (true)
              {
                this.mDanmakusVisible = true;
                if ((this.quitFlag) && (this.mDanmakuView != null))
                  this.mDanmakuView.drawDanmakus();
                notifyRendering();
                return;
                this.drawTask.start();
                this.drawTask.seek(localLong1.longValue());
                this.drawTask.requestClear();
                obtainMessage(1, localLong1).sendToTarget();
              }
              this.mDanmakusVisible = false;
              if (this.mDanmakuView != null)
                this.mDanmakuView.clear();
              if (this.drawTask != null)
              {
                this.drawTask.requestClear();
                this.drawTask.requestHide();
              }
              localBoolean1 = (Boolean)paramMessage.obj;
              if ((localBoolean1.booleanValue()) && (this.drawTask != null))
                this.drawTask.quit();
            }
            while (!localBoolean1.booleanValue());
            removeMessages(2);
            if (i == 6)
              removeCallbacksAndMessages(null);
            this.quitFlag = true;
            syncTimerIfNeeded();
            this.mSkipFrames = 0;
            if (this.mThread != null)
            {
              notifyRendering();
              quitUpdateThread();
            }
            this.pausedPosition = this.timer.currMillisecond;
          }
          while (i != 6);
          if (this.drawTask != null)
            this.drawTask.quit();
          if (this.mParser != null)
            this.mParser.release();
        }
        while (getLooper() == Looper.getMainLooper());
        getLooper().quit();
        return;
        notifyRendering();
        return;
      }
      while ((!this.quitFlag) || (this.mDanmakuView == null));
      this.drawTask.requestClear();
      this.mDanmakuView.drawDanmakus();
      notifyRendering();
      return;
    }
    while (this.drawTask == null);
    this.drawTask.clearDanmakusOnScreen(getCurrentTime());
  }

  public long hideDanmakus(boolean paramBoolean)
  {
    if (!this.mDanmakusVisible)
      return this.timer.currMillisecond;
    removeMessages(8);
    removeMessages(9);
    obtainMessage(9, Boolean.valueOf(paramBoolean)).sendToTarget();
    return this.timer.currMillisecond;
  }

  public boolean isPrepared()
  {
    return this.mReady;
  }

  public boolean isStop()
  {
    return this.quitFlag;
  }

  public void notifyDispSizeChanged(int paramInt1, int paramInt2)
  {
    if (this.mDisp == null);
    while ((this.mDisp.getWidth() == paramInt1) && (this.mDisp.getHeight() == paramInt2))
      return;
    this.mDisp.setSize(paramInt1, paramInt2);
    obtainMessage(10, Boolean.valueOf(true)).sendToTarget();
  }

  public void pause()
  {
    syncTimerIfNeeded();
    sendEmptyMessage(7);
  }

  public void prepare()
  {
    sendEmptyMessage(5);
  }

  public void quit()
  {
    sendEmptyMessage(6);
  }

  public void removeAllDanmakus()
  {
    if (this.drawTask != null)
      this.drawTask.removeAllDanmakus();
  }

  public void removeAllLiveDanmakus()
  {
    if (this.drawTask != null)
      this.drawTask.removeAllLiveDanmakus();
  }

  public void resume()
  {
    sendEmptyMessage(3);
  }

  public void seekTo(Long paramLong)
  {
    this.mInSeekingAction = true;
    removeMessages(2);
    removeMessages(3);
    removeMessages(4);
    obtainMessage(4, paramLong).sendToTarget();
  }

  public void setCallback(Callback paramCallback)
  {
    this.mCallback = paramCallback;
  }

  public void setParser(BaseDanmakuParser paramBaseDanmakuParser)
  {
    this.mParser = paramBaseDanmakuParser;
  }

  public void showDanmakus(Long paramLong)
  {
    if (this.mDanmakusVisible)
      return;
    removeMessages(8);
    removeMessages(9);
    obtainMessage(8, paramLong).sendToTarget();
  }

  public static abstract interface Callback
  {
    public abstract void prepared();

    public abstract void updateTimer(DanmakuTimer paramDanmakuTimer);
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     master.flame.danmaku.controller.DrawHandler
 * JD-Core Version:    0.6.2
 */