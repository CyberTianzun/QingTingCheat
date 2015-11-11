package master.flame.danmaku.controller;

import android.content.Context;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import master.flame.danmaku.danmaku.model.AbsDisplayer;
import master.flame.danmaku.danmaku.model.BaseDanmaku;
import master.flame.danmaku.danmaku.model.DanmakuTimer;
import master.flame.danmaku.danmaku.model.IDanmakuIterator;
import master.flame.danmaku.danmaku.model.IDanmakus;
import master.flame.danmaku.danmaku.model.IDrawingCache;
import master.flame.danmaku.danmaku.model.android.DanmakuGlobalConfig;
import master.flame.danmaku.danmaku.model.android.DanmakuGlobalConfig.DanmakuConfigTag;
import master.flame.danmaku.danmaku.model.android.Danmakus;
import master.flame.danmaku.danmaku.model.android.DrawingCache;
import master.flame.danmaku.danmaku.model.android.DrawingCachePoolManager;
import master.flame.danmaku.danmaku.model.objectpool.Pool;
import master.flame.danmaku.danmaku.model.objectpool.Pools;
import master.flame.danmaku.danmaku.parser.DanmakuFactory;
import master.flame.danmaku.danmaku.renderer.IRenderer;
import master.flame.danmaku.danmaku.renderer.IRenderer.RenderingState;
import tv.cjump.jni.NativeBitmapFactory;

public class CacheManagingDrawTask extends DrawTask
{
  private static final int MAX_CACHE_SCREEN_SIZE = 3;
  private CacheManager mCacheManager;
  private DanmakuTimer mCacheTimer;
  private final Object mDrawingNotify = new Object();
  private int mMaxCacheSize = 2;

  static
  {
    if (!CacheManagingDrawTask.class.desiredAssertionStatus());
    for (boolean bool = true; ; bool = false)
    {
      $assertionsDisabled = bool;
      return;
    }
  }

  public CacheManagingDrawTask(DanmakuTimer paramDanmakuTimer, Context paramContext, AbsDisplayer<?> paramAbsDisplayer, IDrawTask.TaskListener paramTaskListener, int paramInt)
  {
    super(paramDanmakuTimer, paramContext, paramAbsDisplayer, paramTaskListener);
    NativeBitmapFactory.loadLibs();
    this.mMaxCacheSize = paramInt;
    if (NativeBitmapFactory.isInNativeAlloc())
      this.mMaxCacheSize = (paramInt * 3);
    this.mCacheManager = new CacheManager(paramInt, 3);
  }

  public void addDanmaku(BaseDanmaku paramBaseDanmaku)
  {
    if (this.mCacheManager == null)
      return;
    this.mCacheManager.addDanmaku(paramBaseDanmaku);
  }

  public IRenderer.RenderingState draw(AbsDisplayer<?> paramAbsDisplayer)
  {
    IRenderer.RenderingState localRenderingState;
    synchronized (this.danmakuList)
    {
      localRenderingState = super.draw(paramAbsDisplayer);
    }
    synchronized (this.mDrawingNotify)
    {
      this.mDrawingNotify.notify();
      if ((localRenderingState != null) && (this.mCacheManager != null) && (localRenderingState.incrementCount < -20))
      {
        this.mCacheManager.requestClearTimeout();
        this.mCacheManager.requestBuild(-DanmakuFactory.MAX_DANMAKU_DURATION);
      }
      return localRenderingState;
      localObject1 = finally;
      throw localObject1;
    }
  }

  protected void initTimer(DanmakuTimer paramDanmakuTimer)
  {
    this.mTimer = paramDanmakuTimer;
    this.mCacheTimer = new DanmakuTimer();
    this.mCacheTimer.update(paramDanmakuTimer.currMillisecond);
  }

  public boolean onDanmakuConfigChanged(DanmakuGlobalConfig paramDanmakuGlobalConfig, DanmakuGlobalConfig.DanmakuConfigTag paramDanmakuConfigTag, Object[] paramArrayOfObject)
  {
    if (super.handleOnDanmakuConfigChanged(paramDanmakuGlobalConfig, paramDanmakuConfigTag, paramArrayOfObject));
    while (true)
    {
      if ((this.mTaskListener != null) && (this.mCacheManager != null))
        this.mCacheManager.post(new Runnable()
        {
          public void run()
          {
            CacheManagingDrawTask.this.mTaskListener.onDanmakuConfigChanged();
          }
        });
      return true;
      if (DanmakuGlobalConfig.DanmakuConfigTag.SCROLL_SPEED_FACTOR.equals(paramDanmakuConfigTag))
      {
        this.mDisp.resetSlopPixel(DanmakuGlobalConfig.DEFAULT.scaleTextSize);
        requestClear();
      }
      else if (paramDanmakuConfigTag.isVisibilityRelatedTag())
      {
        if ((paramArrayOfObject != null) && (paramArrayOfObject.length > 0) && (paramArrayOfObject[0] != null) && ((!(paramArrayOfObject[0] instanceof Boolean)) || (((Boolean)paramArrayOfObject[0]).booleanValue())) && (this.mCacheManager != null))
          this.mCacheManager.requestBuild(0L);
        requestClear();
      }
      else if ((DanmakuGlobalConfig.DanmakuConfigTag.TRANSPARENCY.equals(paramDanmakuConfigTag)) || (DanmakuGlobalConfig.DanmakuConfigTag.SCALE_TEXTSIZE.equals(paramDanmakuConfigTag)) || (DanmakuGlobalConfig.DanmakuConfigTag.DANMAKU_STYLE.equals(paramDanmakuConfigTag)))
      {
        if (DanmakuGlobalConfig.DanmakuConfigTag.SCALE_TEXTSIZE.equals(paramDanmakuConfigTag))
          this.mDisp.resetSlopPixel(DanmakuGlobalConfig.DEFAULT.scaleTextSize);
        if (this.mCacheManager != null)
        {
          this.mCacheManager.requestClearAll();
          this.mCacheManager.requestBuild(-DanmakuFactory.MAX_DANMAKU_DURATION);
        }
      }
      else if (this.mCacheManager != null)
      {
        this.mCacheManager.requestClearUnused();
        this.mCacheManager.requestBuild(0L);
      }
    }
  }

  public void prepare()
  {
    assert (this.mParser != null);
    loadDanmakus(this.mParser);
    this.mCacheManager.begin();
  }

  public void quit()
  {
    super.quit();
    reset();
    if (this.mCacheManager != null)
    {
      this.mCacheManager.end();
      this.mCacheManager = null;
    }
    NativeBitmapFactory.releaseLibs();
  }

  public void reset()
  {
    if (this.mRenderer != null)
      this.mRenderer.clear();
  }

  public void seek(long paramLong)
  {
    super.seek(paramLong);
    this.mCacheManager.seek(paramLong);
  }

  public void start()
  {
    super.start();
    NativeBitmapFactory.loadLibs();
    if (this.mCacheManager == null)
    {
      this.mCacheManager = new CacheManager(this.mMaxCacheSize, 3);
      this.mCacheManager.begin();
      return;
    }
    this.mCacheManager.resume();
  }

  public class CacheManager
  {
    public static final byte RESULT_FAILED = 1;
    public static final byte RESULT_FAILED_OVERSIZE = 2;
    public static final byte RESULT_SUCCESS = 0;
    private static final String TAG = "CacheManager";
    int danmakuAddedCount = 0;
    Pool<DrawingCache> mCachePool = Pools.finitePool(this.mCachePoolManager, 800);
    DrawingCachePoolManager mCachePoolManager = new DrawingCachePoolManager();
    Danmakus mCaches = new Danmakus(4);
    private boolean mEndFlag = false;
    private CacheHandler mHandler;
    private int mMaxSize;
    private int mRealSize = 0;
    private int mScreenSize = 3;
    public HandlerThread mThread;

    public CacheManager(int paramInt1, int arg3)
    {
      this.mMaxSize = paramInt1;
      int i;
      this.mScreenSize = i;
    }

    private void clearCachePool()
    {
      while (true)
      {
        DrawingCache localDrawingCache = (DrawingCache)this.mCachePool.acquire();
        if (localDrawingCache == null)
          break;
        localDrawingCache.destroy();
      }
    }

    private void clearTimeOutCaches()
    {
      clearTimeOutCaches(CacheManagingDrawTask.this.mTimer.currMillisecond);
    }

    private void clearTimeOutCaches(long paramLong)
    {
      IDanmakuIterator localIDanmakuIterator = this.mCaches.iterator();
      while ((localIDanmakuIterator.hasNext()) && (!this.mEndFlag))
      {
        BaseDanmaku localBaseDanmaku = localIDanmakuIterator.next();
        if (localBaseDanmaku.isTimeOut())
          synchronized (CacheManagingDrawTask.this.mDrawingNotify)
          {
            try
            {
              CacheManagingDrawTask.this.mDrawingNotify.wait(30L);
              entryRemoved(false, localBaseDanmaku, null);
              localIDanmakuIterator.remove();
            }
            catch (InterruptedException localInterruptedException)
            {
              localInterruptedException.printStackTrace();
              return;
            }
          }
      }
    }

    private void evictAll()
    {
      if (this.mCaches != null)
      {
        IDanmakuIterator localIDanmakuIterator = this.mCaches.iterator();
        while (localIDanmakuIterator.hasNext())
          entryRemoved(true, localIDanmakuIterator.next(), null);
        this.mCaches.clear();
      }
      this.mRealSize = 0;
    }

    private void evictAllNotInScreen()
    {
      evictAllNotInScreen(false);
    }

    private void evictAllNotInScreen(boolean paramBoolean)
    {
      if (this.mCaches != null)
      {
        IDanmakuIterator localIDanmakuIterator = this.mCaches.iterator();
        while (localIDanmakuIterator.hasNext())
        {
          BaseDanmaku localBaseDanmaku = localIDanmakuIterator.next();
          IDrawingCache localIDrawingCache = localBaseDanmaku.cache;
          if ((localIDrawingCache != null) && (localIDrawingCache.hasReferences()));
          for (int i = 1; ; i = 0)
          {
            if ((!paramBoolean) || (i == 0))
              break label119;
            if (localIDrawingCache.get() != null)
            {
              this.mRealSize -= localIDrawingCache.size();
              localIDrawingCache.destroy();
            }
            entryRemoved(true, localBaseDanmaku, null);
            localIDanmakuIterator.remove();
            break;
          }
          label119: if ((!localBaseDanmaku.hasDrawingCache()) || (localBaseDanmaku.isOutside()))
          {
            entryRemoved(true, localBaseDanmaku, null);
            localIDanmakuIterator.remove();
          }
        }
      }
      this.mRealSize = 0;
    }

    private BaseDanmaku findReuseableCache(BaseDanmaku paramBaseDanmaku, boolean paramBoolean)
    {
      IDanmakuIterator localIDanmakuIterator = this.mCaches.iterator();
      int i = 0;
      if (!paramBoolean)
        i = 2 * CacheManagingDrawTask.this.mDisp.getSlopPixel();
      BaseDanmaku localBaseDanmaku;
      label148: float f1;
      float f2;
      do
      {
        do
        {
          while (localIDanmakuIterator.hasNext())
          {
            localBaseDanmaku = localIDanmakuIterator.next();
            if (localBaseDanmaku.hasDrawingCache())
            {
              if ((localBaseDanmaku.paintWidth == paramBaseDanmaku.paintWidth) && (localBaseDanmaku.paintHeight == paramBaseDanmaku.paintHeight) && (localBaseDanmaku.underlineColor == paramBaseDanmaku.underlineColor) && (localBaseDanmaku.borderColor == paramBaseDanmaku.borderColor) && (localBaseDanmaku.textColor == paramBaseDanmaku.textColor) && (localBaseDanmaku.text.equals(paramBaseDanmaku.text)))
                return localBaseDanmaku;
              if (!paramBoolean)
                if (localBaseDanmaku.isTimeOut())
                  break label148;
            }
          }
          return null;
        }
        while (localBaseDanmaku.cache.hasReferences());
        f1 = localBaseDanmaku.cache.width() - paramBaseDanmaku.paintWidth;
        f2 = localBaseDanmaku.cache.height() - paramBaseDanmaku.paintHeight;
      }
      while ((f1 < 0.0F) || (f1 > i) || (f2 < 0.0F) || (f2 > i));
      return localBaseDanmaku;
    }

    private boolean push(BaseDanmaku paramBaseDanmaku, int paramInt)
    {
      while ((paramInt + this.mRealSize > this.mMaxSize) && (this.mCaches.size() > 0))
      {
        BaseDanmaku localBaseDanmaku = this.mCaches.first();
        boolean bool2 = localBaseDanmaku.isTimeOut();
        bool1 = false;
        if (!bool2)
          break label90;
        entryRemoved(false, localBaseDanmaku, paramBaseDanmaku);
        this.mCaches.removeItem(localBaseDanmaku);
      }
      this.mCaches.addItem(paramBaseDanmaku);
      this.mRealSize = (paramInt + this.mRealSize);
      boolean bool1 = true;
      label90: return bool1;
    }

    public void addDanmaku(BaseDanmaku paramBaseDanmaku)
    {
      if (this.mHandler != null)
        this.mHandler.obtainMessage(2, paramBaseDanmaku).sendToTarget();
    }

    public void begin()
    {
      if (this.mThread == null)
      {
        this.mThread = new HandlerThread("DFM Cache-Building Thread");
        this.mThread.start();
      }
      if (this.mHandler == null)
        this.mHandler = new CacheHandler(this.mThread.getLooper());
      this.mHandler.begin();
    }

    public void end()
    {
      this.mEndFlag = true;
      synchronized (CacheManagingDrawTask.this.mDrawingNotify)
      {
        CacheManagingDrawTask.this.mDrawingNotify.notifyAll();
        if (this.mHandler != null)
        {
          this.mHandler.pause();
          this.mHandler = null;
        }
        if (this.mThread == null);
      }
      try
      {
        this.mThread.join();
        this.mThread.quit();
        this.mThread = null;
        return;
        localObject2 = finally;
        throw localObject2;
      }
      catch (InterruptedException localInterruptedException)
      {
        while (true)
          localInterruptedException.printStackTrace();
      }
    }

    protected void entryRemoved(boolean paramBoolean, BaseDanmaku paramBaseDanmaku1, BaseDanmaku paramBaseDanmaku2)
    {
      if (paramBaseDanmaku1.cache != null)
      {
        if (paramBaseDanmaku1.cache.hasReferences())
        {
          paramBaseDanmaku1.cache.decreaseReference();
          paramBaseDanmaku1.cache = null;
        }
      }
      else
        return;
      this.mRealSize -= sizeOf(paramBaseDanmaku1);
      paramBaseDanmaku1.cache.destroy();
      this.mCachePool.release((DrawingCache)paramBaseDanmaku1.cache);
      paramBaseDanmaku1.cache = null;
    }

    public long getFirstCacheTime()
    {
      BaseDanmaku localBaseDanmaku;
      if ((this.mCaches != null) && (this.mCaches.size() > 0))
      {
        localBaseDanmaku = this.mCaches.first();
        if (localBaseDanmaku != null);
      }
      else
      {
        return 0L;
      }
      return localBaseDanmaku.time;
    }

    public float getPoolPercent()
    {
      if (this.mMaxSize == 0)
        return 0.0F;
      return this.mRealSize / this.mMaxSize;
    }

    public boolean isPoolFull()
    {
      return 5120 + this.mRealSize >= this.mMaxSize;
    }

    public void post(Runnable paramRunnable)
    {
      if (this.mHandler == null)
        return;
      this.mHandler.post(paramRunnable);
    }

    public void requestBuild(long paramLong)
    {
      if (this.mHandler != null)
        this.mHandler.requestBuildCacheAndDraw(paramLong);
    }

    public void requestClearAll()
    {
      if (this.mHandler == null)
        return;
      this.mHandler.removeMessages(3);
      this.mHandler.requestCancelCaching();
      this.mHandler.removeMessages(7);
      this.mHandler.sendEmptyMessage(7);
    }

    public void requestClearTimeout()
    {
      if (this.mHandler == null)
        return;
      this.mHandler.removeMessages(4);
      this.mHandler.sendEmptyMessage(4);
    }

    public void requestClearUnused()
    {
      if (this.mHandler == null)
        return;
      this.mHandler.removeMessages(9);
      this.mHandler.sendEmptyMessage(9);
    }

    public void resume()
    {
      if (this.mHandler != null)
      {
        this.mHandler.resume();
        return;
      }
      begin();
    }

    public void seek(long paramLong)
    {
      if (this.mHandler == null)
        return;
      this.mHandler.requestCancelCaching();
      this.mHandler.removeMessages(3);
      this.mHandler.obtainMessage(5, Long.valueOf(paramLong)).sendToTarget();
    }

    protected int sizeOf(BaseDanmaku paramBaseDanmaku)
    {
      if ((paramBaseDanmaku.cache != null) && (!paramBaseDanmaku.cache.hasReferences()))
        return paramBaseDanmaku.cache.size();
      return 0;
    }

    public class CacheHandler extends Handler
    {
      public static final int ADD_DANMAKKU = 2;
      public static final int BUILD_CACHES = 3;
      public static final int CLEAR_ALL_CACHES = 7;
      public static final int CLEAR_OUTSIDE_CACHES = 8;
      public static final int CLEAR_OUTSIDE_CACHES_AND_RESET = 9;
      public static final int CLEAR_TIMEOUT_CACHES = 4;
      public static final int DISPATCH_ACTIONS = 16;
      private static final int PREPARE = 1;
      public static final int QUIT = 6;
      public static final int SEEK = 5;
      private boolean mCancelFlag;
      private boolean mPause;
      private boolean mSeekedFlag;

      public CacheHandler(Looper arg2)
      {
        super();
      }

      // ERROR //
      private byte buildCache(BaseDanmaku paramBaseDanmaku)
      {
        // Byte code:
        //   0: aconst_null
        //   1: astore_2
        //   2: aload_1
        //   3: invokevirtual 50	master/flame/danmaku/danmaku/model/BaseDanmaku:isMeasured	()Z
        //   6: ifne +17 -> 23
        //   9: aload_1
        //   10: aload_0
        //   11: getfield 35	master/flame/danmaku/controller/CacheManagingDrawTask$CacheManager$CacheHandler:this$1	Lmaster/flame/danmaku/controller/CacheManagingDrawTask$CacheManager;
        //   14: getfield 56	master/flame/danmaku/controller/CacheManagingDrawTask$CacheManager:this$0	Lmaster/flame/danmaku/controller/CacheManagingDrawTask;
        //   17: getfield 62	master/flame/danmaku/controller/CacheManagingDrawTask:mDisp	Lmaster/flame/danmaku/danmaku/model/AbsDisplayer;
        //   20: invokevirtual 66	master/flame/danmaku/danmaku/model/BaseDanmaku:measure	(Lmaster/flame/danmaku/danmaku/model/IDisplayer;)V
        //   23: aload_0
        //   24: getfield 35	master/flame/danmaku/controller/CacheManagingDrawTask$CacheManager$CacheHandler:this$1	Lmaster/flame/danmaku/controller/CacheManagingDrawTask$CacheManager;
        //   27: aload_1
        //   28: iconst_1
        //   29: invokestatic 70	master/flame/danmaku/controller/CacheManagingDrawTask$CacheManager:access$900	(Lmaster/flame/danmaku/controller/CacheManagingDrawTask$CacheManager;Lmaster/flame/danmaku/danmaku/model/BaseDanmaku;Z)Lmaster/flame/danmaku/danmaku/model/BaseDanmaku;
        //   32: astore 6
        //   34: aconst_null
        //   35: astore_2
        //   36: aload 6
        //   38: ifnull +16 -> 54
        //   41: aload 6
        //   43: getfield 74	master/flame/danmaku/danmaku/model/BaseDanmaku:cache	Lmaster/flame/danmaku/danmaku/model/IDrawingCache;
        //   46: checkcast 76	master/flame/danmaku/danmaku/model/android/DrawingCache
        //   49: astore 7
        //   51: aload 7
        //   53: astore_2
        //   54: aload_2
        //   55: ifnull +37 -> 92
        //   58: aload_2
        //   59: invokevirtual 80	master/flame/danmaku/danmaku/model/android/DrawingCache:increaseReference	()V
        //   62: aload_1
        //   63: aload_2
        //   64: putfield 74	master/flame/danmaku/danmaku/model/BaseDanmaku:cache	Lmaster/flame/danmaku/danmaku/model/IDrawingCache;
        //   67: aload_0
        //   68: getfield 35	master/flame/danmaku/controller/CacheManagingDrawTask$CacheManager$CacheHandler:this$1	Lmaster/flame/danmaku/controller/CacheManagingDrawTask$CacheManager;
        //   71: getfield 56	master/flame/danmaku/controller/CacheManagingDrawTask$CacheManager:this$0	Lmaster/flame/danmaku/controller/CacheManagingDrawTask;
        //   74: invokestatic 84	master/flame/danmaku/controller/CacheManagingDrawTask:access$1000	(Lmaster/flame/danmaku/controller/CacheManagingDrawTask;)Lmaster/flame/danmaku/controller/CacheManagingDrawTask$CacheManager;
        //   77: aload_1
        //   78: aload_0
        //   79: getfield 35	master/flame/danmaku/controller/CacheManagingDrawTask$CacheManager$CacheHandler:this$1	Lmaster/flame/danmaku/controller/CacheManagingDrawTask$CacheManager;
        //   82: aload_1
        //   83: invokevirtual 88	master/flame/danmaku/controller/CacheManagingDrawTask$CacheManager:sizeOf	(Lmaster/flame/danmaku/danmaku/model/BaseDanmaku;)I
        //   86: invokestatic 92	master/flame/danmaku/controller/CacheManagingDrawTask$CacheManager:access$1100	(Lmaster/flame/danmaku/controller/CacheManagingDrawTask$CacheManager;Lmaster/flame/danmaku/danmaku/model/BaseDanmaku;I)Z
        //   89: pop
        //   90: iconst_0
        //   91: ireturn
        //   92: aload_0
        //   93: getfield 35	master/flame/danmaku/controller/CacheManagingDrawTask$CacheManager$CacheHandler:this$1	Lmaster/flame/danmaku/controller/CacheManagingDrawTask$CacheManager;
        //   96: aload_1
        //   97: iconst_0
        //   98: invokestatic 70	master/flame/danmaku/controller/CacheManagingDrawTask$CacheManager:access$900	(Lmaster/flame/danmaku/controller/CacheManagingDrawTask$CacheManager;Lmaster/flame/danmaku/danmaku/model/BaseDanmaku;Z)Lmaster/flame/danmaku/danmaku/model/BaseDanmaku;
        //   101: astore 9
        //   103: aload 9
        //   105: ifnull +12 -> 117
        //   108: aload 9
        //   110: getfield 74	master/flame/danmaku/danmaku/model/BaseDanmaku:cache	Lmaster/flame/danmaku/danmaku/model/IDrawingCache;
        //   113: checkcast 76	master/flame/danmaku/danmaku/model/android/DrawingCache
        //   116: astore_2
        //   117: aload_2
        //   118: ifnull +54 -> 172
        //   121: aload 9
        //   123: aconst_null
        //   124: putfield 74	master/flame/danmaku/danmaku/model/BaseDanmaku:cache	Lmaster/flame/danmaku/danmaku/model/IDrawingCache;
        //   127: aload_1
        //   128: aload_0
        //   129: getfield 35	master/flame/danmaku/controller/CacheManagingDrawTask$CacheManager$CacheHandler:this$1	Lmaster/flame/danmaku/controller/CacheManagingDrawTask$CacheManager;
        //   132: getfield 56	master/flame/danmaku/controller/CacheManagingDrawTask$CacheManager:this$0	Lmaster/flame/danmaku/controller/CacheManagingDrawTask;
        //   135: getfield 62	master/flame/danmaku/controller/CacheManagingDrawTask:mDisp	Lmaster/flame/danmaku/danmaku/model/AbsDisplayer;
        //   138: aload_2
        //   139: invokestatic 98	master/flame/danmaku/danmaku/util/DanmakuUtils:buildDanmakuDrawingCache	(Lmaster/flame/danmaku/danmaku/model/BaseDanmaku;Lmaster/flame/danmaku/danmaku/model/IDisplayer;Lmaster/flame/danmaku/danmaku/model/android/DrawingCache;)Lmaster/flame/danmaku/danmaku/model/android/DrawingCache;
        //   142: astore 10
        //   144: aload 10
        //   146: astore 4
        //   148: aload_1
        //   149: aload 4
        //   151: putfield 74	master/flame/danmaku/danmaku/model/BaseDanmaku:cache	Lmaster/flame/danmaku/danmaku/model/IDrawingCache;
        //   154: aload_0
        //   155: getfield 35	master/flame/danmaku/controller/CacheManagingDrawTask$CacheManager$CacheHandler:this$1	Lmaster/flame/danmaku/controller/CacheManagingDrawTask$CacheManager;
        //   158: getfield 56	master/flame/danmaku/controller/CacheManagingDrawTask$CacheManager:this$0	Lmaster/flame/danmaku/controller/CacheManagingDrawTask;
        //   161: invokestatic 84	master/flame/danmaku/controller/CacheManagingDrawTask:access$1000	(Lmaster/flame/danmaku/controller/CacheManagingDrawTask;)Lmaster/flame/danmaku/controller/CacheManagingDrawTask$CacheManager;
        //   164: aload_1
        //   165: iconst_0
        //   166: invokestatic 92	master/flame/danmaku/controller/CacheManagingDrawTask$CacheManager:access$1100	(Lmaster/flame/danmaku/controller/CacheManagingDrawTask$CacheManager;Lmaster/flame/danmaku/danmaku/model/BaseDanmaku;I)Z
        //   169: pop
        //   170: iconst_0
        //   171: ireturn
        //   172: aload_1
        //   173: getfield 102	master/flame/danmaku/danmaku/model/BaseDanmaku:paintWidth	F
        //   176: f2i
        //   177: aload_1
        //   178: getfield 105	master/flame/danmaku/danmaku/model/BaseDanmaku:paintHeight	F
        //   181: f2i
        //   182: invokestatic 109	master/flame/danmaku/danmaku/util/DanmakuUtils:getCacheSize	(II)I
        //   185: aload_0
        //   186: getfield 35	master/flame/danmaku/controller/CacheManagingDrawTask$CacheManager$CacheHandler:this$1	Lmaster/flame/danmaku/controller/CacheManagingDrawTask$CacheManager;
        //   189: invokestatic 113	master/flame/danmaku/controller/CacheManagingDrawTask$CacheManager:access$1200	(Lmaster/flame/danmaku/controller/CacheManagingDrawTask$CacheManager;)I
        //   192: iadd
        //   193: aload_0
        //   194: getfield 35	master/flame/danmaku/controller/CacheManagingDrawTask$CacheManager$CacheHandler:this$1	Lmaster/flame/danmaku/controller/CacheManagingDrawTask$CacheManager;
        //   197: invokestatic 116	master/flame/danmaku/controller/CacheManagingDrawTask$CacheManager:access$1300	(Lmaster/flame/danmaku/controller/CacheManagingDrawTask$CacheManager;)I
        //   200: if_icmple +5 -> 205
        //   203: iconst_1
        //   204: ireturn
        //   205: aload_0
        //   206: getfield 35	master/flame/danmaku/controller/CacheManagingDrawTask$CacheManager$CacheHandler:this$1	Lmaster/flame/danmaku/controller/CacheManagingDrawTask$CacheManager;
        //   209: getfield 120	master/flame/danmaku/controller/CacheManagingDrawTask$CacheManager:mCachePool	Lmaster/flame/danmaku/danmaku/model/objectpool/Pool;
        //   212: invokeinterface 126 1 0
        //   217: checkcast 76	master/flame/danmaku/danmaku/model/android/DrawingCache
        //   220: astore 4
        //   222: aload_0
        //   223: getfield 35	master/flame/danmaku/controller/CacheManagingDrawTask$CacheManager$CacheHandler:this$1	Lmaster/flame/danmaku/controller/CacheManagingDrawTask$CacheManager;
        //   226: getfield 56	master/flame/danmaku/controller/CacheManagingDrawTask$CacheManager:this$0	Lmaster/flame/danmaku/controller/CacheManagingDrawTask;
        //   229: getfield 130	master/flame/danmaku/controller/CacheManagingDrawTask:danmakuList	Lmaster/flame/danmaku/danmaku/model/IDanmakus;
        //   232: astore 14
        //   234: aload 14
        //   236: monitorenter
        //   237: aload_1
        //   238: aload_0
        //   239: getfield 35	master/flame/danmaku/controller/CacheManagingDrawTask$CacheManager$CacheHandler:this$1	Lmaster/flame/danmaku/controller/CacheManagingDrawTask$CacheManager;
        //   242: getfield 56	master/flame/danmaku/controller/CacheManagingDrawTask$CacheManager:this$0	Lmaster/flame/danmaku/controller/CacheManagingDrawTask;
        //   245: getfield 62	master/flame/danmaku/controller/CacheManagingDrawTask:mDisp	Lmaster/flame/danmaku/danmaku/model/AbsDisplayer;
        //   248: aload 4
        //   250: invokestatic 98	master/flame/danmaku/danmaku/util/DanmakuUtils:buildDanmakuDrawingCache	(Lmaster/flame/danmaku/danmaku/model/BaseDanmaku;Lmaster/flame/danmaku/danmaku/model/IDisplayer;Lmaster/flame/danmaku/danmaku/model/android/DrawingCache;)Lmaster/flame/danmaku/danmaku/model/android/DrawingCache;
        //   253: astore 20
        //   255: aload_1
        //   256: aload 20
        //   258: putfield 74	master/flame/danmaku/danmaku/model/BaseDanmaku:cache	Lmaster/flame/danmaku/danmaku/model/IDrawingCache;
        //   261: aload_0
        //   262: getfield 35	master/flame/danmaku/controller/CacheManagingDrawTask$CacheManager$CacheHandler:this$1	Lmaster/flame/danmaku/controller/CacheManagingDrawTask$CacheManager;
        //   265: getfield 56	master/flame/danmaku/controller/CacheManagingDrawTask$CacheManager:this$0	Lmaster/flame/danmaku/controller/CacheManagingDrawTask;
        //   268: invokestatic 84	master/flame/danmaku/controller/CacheManagingDrawTask:access$1000	(Lmaster/flame/danmaku/controller/CacheManagingDrawTask;)Lmaster/flame/danmaku/controller/CacheManagingDrawTask$CacheManager;
        //   271: aload_1
        //   272: aload_0
        //   273: getfield 35	master/flame/danmaku/controller/CacheManagingDrawTask$CacheManager$CacheHandler:this$1	Lmaster/flame/danmaku/controller/CacheManagingDrawTask$CacheManager;
        //   276: aload_1
        //   277: invokevirtual 88	master/flame/danmaku/controller/CacheManagingDrawTask$CacheManager:sizeOf	(Lmaster/flame/danmaku/danmaku/model/BaseDanmaku;)I
        //   280: invokestatic 92	master/flame/danmaku/controller/CacheManagingDrawTask$CacheManager:access$1100	(Lmaster/flame/danmaku/controller/CacheManagingDrawTask$CacheManager;Lmaster/flame/danmaku/danmaku/model/BaseDanmaku;I)Z
        //   283: istore 21
        //   285: iload 21
        //   287: ifne +116 -> 403
        //   290: aload_0
        //   291: aload_1
        //   292: aload 20
        //   294: invokespecial 134	master/flame/danmaku/controller/CacheManagingDrawTask$CacheManager$CacheHandler:releaseDanmakuCache	(Lmaster/flame/danmaku/danmaku/model/BaseDanmaku;Lmaster/flame/danmaku/danmaku/model/android/DrawingCache;)V
        //   297: goto +106 -> 403
        //   300: aload 14
        //   302: monitorexit
        //   303: iload 22
        //   305: ireturn
        //   306: astore 17
        //   308: aload 20
        //   310: astore 16
        //   312: aload 14
        //   314: monitorexit
        //   315: aload 17
        //   317: athrow
        //   318: astore 19
        //   320: aload 16
        //   322: astore 4
        //   324: aload_0
        //   325: aload_1
        //   326: aload 4
        //   328: invokespecial 134	master/flame/danmaku/controller/CacheManagingDrawTask$CacheManager$CacheHandler:releaseDanmakuCache	(Lmaster/flame/danmaku/danmaku/model/BaseDanmaku;Lmaster/flame/danmaku/danmaku/model/android/DrawingCache;)V
        //   331: iconst_1
        //   332: ireturn
        //   333: iconst_1
        //   334: istore 22
        //   336: goto -36 -> 300
        //   339: astore 5
        //   341: aload_0
        //   342: aload_1
        //   343: aload_2
        //   344: invokespecial 134	master/flame/danmaku/controller/CacheManagingDrawTask$CacheManager$CacheHandler:releaseDanmakuCache	(Lmaster/flame/danmaku/danmaku/model/BaseDanmaku;Lmaster/flame/danmaku/danmaku/model/android/DrawingCache;)V
        //   347: iconst_1
        //   348: ireturn
        //   349: astore 12
        //   351: aload 4
        //   353: astore_2
        //   354: goto -13 -> 341
        //   357: astore 18
        //   359: aload 16
        //   361: astore_2
        //   362: goto -21 -> 341
        //   365: astore_3
        //   366: aconst_null
        //   367: astore 4
        //   369: goto -45 -> 324
        //   372: astore 8
        //   374: aload_2
        //   375: astore 4
        //   377: goto -53 -> 324
        //   380: astore 11
        //   382: goto -58 -> 324
        //   385: astore 15
        //   387: aload 4
        //   389: astore 16
        //   391: aload 15
        //   393: astore 17
        //   395: goto -83 -> 312
        //   398: astore 17
        //   400: goto -88 -> 312
        //   403: iload 21
        //   405: ifeq -72 -> 333
        //   408: iconst_0
        //   409: istore 22
        //   411: goto -111 -> 300
        //
        // Exception table:
        //   from	to	target	type
        //   255	285	306	finally
        //   290	297	306	finally
        //   300	303	306	finally
        //   315	318	318	java/lang/OutOfMemoryError
        //   23	34	339	java/lang/Exception
        //   41	51	339	java/lang/Exception
        //   58	90	339	java/lang/Exception
        //   92	103	339	java/lang/Exception
        //   108	117	339	java/lang/Exception
        //   121	144	339	java/lang/Exception
        //   172	203	339	java/lang/Exception
        //   205	222	339	java/lang/Exception
        //   148	170	349	java/lang/Exception
        //   222	237	349	java/lang/Exception
        //   315	318	357	java/lang/Exception
        //   23	34	365	java/lang/OutOfMemoryError
        //   41	51	365	java/lang/OutOfMemoryError
        //   58	90	372	java/lang/OutOfMemoryError
        //   92	103	372	java/lang/OutOfMemoryError
        //   108	117	372	java/lang/OutOfMemoryError
        //   121	144	372	java/lang/OutOfMemoryError
        //   172	203	372	java/lang/OutOfMemoryError
        //   205	222	372	java/lang/OutOfMemoryError
        //   148	170	380	java/lang/OutOfMemoryError
        //   222	237	380	java/lang/OutOfMemoryError
        //   237	255	385	finally
        //   312	315	398	finally
      }

      private long dispatchAction()
      {
        float f = CacheManagingDrawTask.CacheManager.this.getPoolPercent();
        BaseDanmaku localBaseDanmaku = CacheManagingDrawTask.CacheManager.this.mCaches.first();
        long l1;
        long l2;
        if (localBaseDanmaku != null)
        {
          l1 = localBaseDanmaku.time - CacheManagingDrawTask.this.mTimer.currMillisecond;
          l2 = 2L * DanmakuFactory.MAX_DANMAKU_DURATION;
          if ((f >= 0.6F) || (l1 <= DanmakuFactory.MAX_DANMAKU_DURATION))
            break label111;
          CacheManagingDrawTask.this.mCacheTimer.update(CacheManagingDrawTask.this.mTimer.currMillisecond);
          removeMessages(3);
          sendEmptyMessage(3);
        }
        label111: long l3;
        do
        {
          do
          {
            return 0L;
            l1 = 0L;
            break;
            if ((f > 0.4F) && (l1 < -l2))
            {
              removeMessages(4);
              sendEmptyMessage(4);
              return 0L;
            }
          }
          while (f >= 0.9F);
          l3 = CacheManagingDrawTask.this.mCacheTimer.currMillisecond - CacheManagingDrawTask.this.mTimer.currMillisecond;
          if (l3 < 0L)
          {
            CacheManagingDrawTask.this.mCacheTimer.update(CacheManagingDrawTask.this.mTimer.currMillisecond);
            sendEmptyMessage(8);
            sendEmptyMessage(3);
            return 0L;
          }
        }
        while (l3 > l2);
        removeMessages(3);
        sendEmptyMessage(3);
        return 0L;
      }

      private long prepareCaches(boolean paramBoolean)
      {
        long l1 = CacheManagingDrawTask.this.mCacheTimer.currMillisecond;
        long l2 = l1 + 3L * (DanmakuFactory.MAX_DANMAKU_DURATION * CacheManagingDrawTask.CacheManager.this.mScreenSize);
        if (l2 < CacheManagingDrawTask.this.mTimer.currMillisecond)
          return 0L;
        long l3 = System.currentTimeMillis();
        IDanmakus localIDanmakus = CacheManagingDrawTask.this.danmakuList.subnew(l1, l2);
        if ((localIDanmakus == null) || (localIDanmakus.isEmpty()))
        {
          CacheManagingDrawTask.this.mCacheTimer.update(l2);
          return 0L;
        }
        BaseDanmaku localBaseDanmaku1 = localIDanmakus.first();
        BaseDanmaku localBaseDanmaku2 = localIDanmakus.last();
        long l4 = Math.min(100L, 30L + 10L * (localBaseDanmaku1.time - CacheManagingDrawTask.this.mTimer.currMillisecond) / DanmakuFactory.MAX_DANMAKU_DURATION);
        if (paramBoolean);
        for (long l5 = 0L; ; l5 = l4)
        {
          IDanmakuIterator localIDanmakuIterator = localIDanmakus.iterator();
          int i = 0;
          int j = localIDanmakus.size();
          int k = 0;
          int m = 0;
          BaseDanmaku localBaseDanmaku3 = null;
          while (true)
          {
            if ((this.mPause) || (this.mCancelFlag) || (!localIDanmakuIterator.hasNext()));
            label231: int n;
            label263: 
            do
            {
              long l6 = System.currentTimeMillis() - l3;
              if (localBaseDanmaku3 == null)
                break;
              CacheManagingDrawTask.this.mCacheTimer.update(localBaseDanmaku3.time);
              return l6;
              localBaseDanmaku3 = localIDanmakuIterator.next();
              n = m + 1;
            }
            while (localBaseDanmaku2.time < CacheManagingDrawTask.this.mTimer.currMillisecond);
            if (localBaseDanmaku3.hasDrawingCache())
            {
              m = n;
            }
            else if (!paramBoolean)
            {
              if (!localBaseDanmaku3.isTimeOut())
                if (!localBaseDanmaku3.isOutside())
                  m = n;
            }
            else if (DanmakuFilters.getDefault().filter(localBaseDanmaku3, i, j, null, true))
            {
              m = n;
            }
            else
            {
              if (localBaseDanmaku3.getType() == 1)
              {
                i1 = (int)((localBaseDanmaku3.time - l1) / DanmakuFactory.MAX_DANMAKU_DURATION);
                if (k == i1)
                  i++;
              }
              for (int i1 = k; ; i1 = k)
              {
                while (true)
                {
                  if (!paramBoolean);
                  try
                  {
                    synchronized (CacheManagingDrawTask.this.mDrawingNotify)
                    {
                      CacheManagingDrawTask.this.mDrawingNotify.wait(l5);
                      if ((buildCache(localBaseDanmaku3) == 1) || ((!paramBoolean) && (System.currentTimeMillis() - l3 >= 3800L * CacheManagingDrawTask.CacheManager.this.mScreenSize)))
                        break label231;
                      k = i1;
                      m = n;
                      break;
                      i = 0;
                    }
                  }
                  catch (InterruptedException localInterruptedException)
                  {
                    localInterruptedException.printStackTrace();
                  }
                }
                break label231;
                CacheManagingDrawTask.this.mCacheTimer.update(l2);
                break label263;
              }
              m = n;
            }
          }
        }
      }

      private void releaseDanmakuCache(BaseDanmaku paramBaseDanmaku, DrawingCache paramDrawingCache)
      {
        if (paramDrawingCache == null);
        for (DrawingCache localDrawingCache = (DrawingCache)paramBaseDanmaku.cache; ; localDrawingCache = paramDrawingCache)
        {
          paramBaseDanmaku.cache = null;
          if (localDrawingCache == null)
            return;
          localDrawingCache.destroy();
          CacheManagingDrawTask.CacheManager.this.mCachePool.release(localDrawingCache);
          return;
        }
      }

      public void begin()
      {
        sendEmptyMessage(1);
        sendEmptyMessageDelayed(4, DanmakuFactory.MAX_DANMAKU_DURATION);
      }

      public void handleMessage(Message paramMessage)
      {
        int i = 0;
        switch (paramMessage.what)
        {
        case 10:
        case 11:
        case 12:
        case 13:
        case 14:
        case 15:
        default:
        case 1:
        case 16:
        case 3:
        case 2:
        case 4:
        case 5:
          Long localLong;
          do
          {
            return;
            CacheManagingDrawTask.CacheManager.this.evictAllNotInScreen();
            while (i < 300)
            {
              CacheManagingDrawTask.CacheManager.this.mCachePool.release(new DrawingCache());
              i++;
            }
            long l = dispatchAction();
            if (l <= 0L)
              l = DanmakuFactory.MAX_DANMAKU_DURATION / 2L;
            sendEmptyMessageDelayed(16, l);
            return;
            removeMessages(3);
            if (((CacheManagingDrawTask.this.mTaskListener != null) && (!CacheManagingDrawTask.this.mReadyState)) || (this.mSeekedFlag));
            for (boolean bool = true; ; bool = false)
            {
              prepareCaches(bool);
              if (bool)
                this.mSeekedFlag = false;
              if ((CacheManagingDrawTask.this.mTaskListener == null) || (CacheManagingDrawTask.this.mReadyState))
                break;
              CacheManagingDrawTask.this.mTaskListener.ready();
              CacheManagingDrawTask.this.mReadyState = true;
              return;
            }
            BaseDanmaku localBaseDanmaku;
            synchronized (CacheManagingDrawTask.this.danmakuList)
            {
              localBaseDanmaku = (BaseDanmaku)paramMessage.obj;
              if (localBaseDanmaku.isTimeOut())
                return;
            }
            if (!localBaseDanmaku.hasDrawingCache())
              buildCache(localBaseDanmaku);
            if (localBaseDanmaku.isLive)
              CacheManagingDrawTask.this.mCacheTimer.update(CacheManagingDrawTask.this.mTimer.currMillisecond + DanmakuFactory.MAX_DANMAKU_DURATION * CacheManagingDrawTask.CacheManager.this.mScreenSize);
            CacheManagingDrawTask.this.addDanmaku(localBaseDanmaku);
            return;
            CacheManagingDrawTask.CacheManager.this.clearTimeOutCaches();
            return;
            localLong = (Long)paramMessage.obj;
          }
          while (localLong == null);
          CacheManagingDrawTask.this.mCacheTimer.update(localLong.longValue());
          this.mSeekedFlag = true;
          CacheManagingDrawTask.CacheManager.this.evictAllNotInScreen();
          resume();
          return;
        case 6:
          removeCallbacksAndMessages(null);
          this.mPause = true;
          CacheManagingDrawTask.CacheManager.this.evictAll();
          CacheManagingDrawTask.CacheManager.this.clearCachePool();
          getLooper().quit();
          return;
        case 7:
          CacheManagingDrawTask.CacheManager.this.evictAll();
          CacheManagingDrawTask.this.mCacheTimer.update(CacheManagingDrawTask.this.mTimer.currMillisecond - DanmakuFactory.MAX_DANMAKU_DURATION);
          this.mSeekedFlag = true;
          return;
        case 8:
          CacheManagingDrawTask.CacheManager.this.evictAllNotInScreen(true);
          CacheManagingDrawTask.this.mCacheTimer.update(CacheManagingDrawTask.this.mTimer.currMillisecond);
          return;
        case 9:
        }
        CacheManagingDrawTask.CacheManager.this.evictAllNotInScreen(true);
        CacheManagingDrawTask.this.mCacheTimer.update(CacheManagingDrawTask.this.mTimer.currMillisecond);
        CacheManagingDrawTask.this.requestClear();
      }

      public boolean isPause()
      {
        return this.mPause;
      }

      public void pause()
      {
        this.mPause = true;
        removeCallbacksAndMessages(null);
        sendEmptyMessage(6);
      }

      public void requestBuildCacheAndDraw(long paramLong)
      {
        removeMessages(3);
        this.mSeekedFlag = true;
        this.mCancelFlag = false;
        CacheManagingDrawTask.this.mCacheTimer.update(paramLong + CacheManagingDrawTask.this.mTimer.currMillisecond);
        sendEmptyMessage(3);
      }

      public void requestCancelCaching()
      {
        this.mCancelFlag = true;
      }

      public void resume()
      {
        this.mCancelFlag = false;
        this.mPause = false;
        removeMessages(16);
        sendEmptyMessage(16);
        sendEmptyMessageDelayed(4, DanmakuFactory.MAX_DANMAKU_DURATION);
      }
    }
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     master.flame.danmaku.controller.CacheManagingDrawTask
 * JD-Core Version:    0.6.2
 */