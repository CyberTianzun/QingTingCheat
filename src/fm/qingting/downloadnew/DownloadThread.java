package fm.qingting.downloadnew;

import android.content.Context;
import android.util.Log;
import java.util.concurrent.BlockingQueue;

class DownloadThread extends Thread
{
  static int count = 0;
  private final String TAG = "DownloadThread" + count;
  private DownloadTask mCurTask;
  private EventDispatcher mDispatcher;
  private Network mNetwork;
  private volatile boolean mQuit = false;
  private final BlockingQueue<DownloadTask> mTaskQueue;

  DownloadThread(Context paramContext, BlockingQueue<DownloadTask> paramBlockingQueue)
  {
    super("download thread" + count);
    count = 1 + count;
    this.mTaskQueue = paramBlockingQueue;
    this.mDispatcher = new EventDispatcher();
    this.mNetwork = new QTDownloadNetwork(paramContext, this.mDispatcher, this.TAG);
  }

  public void addDownloadListener(DownloadListener paramDownloadListener)
  {
    this.mDispatcher.addListener(paramDownloadListener);
  }

  public DownloadTask getCurrentTask()
  {
    return this.mCurTask;
  }

  public void quit()
  {
    Log.d(this.TAG, "下载线程被要求退出");
    this.mQuit = true;
    interrupt();
  }

  public void removeDownloadListener(DownloadListener paramDownloadListener)
  {
    this.mDispatcher.removeListener(paramDownloadListener);
  }

  // ERROR //
  public void run()
  {
    // Byte code:
    //   0: bipush 10
    //   2: invokestatic 102	android/os/Process:setThreadPriority	(I)V
    //   5: invokestatic 108	android/os/SystemClock:elapsedRealtime	()J
    //   8: pop2
    //   9: aload_0
    //   10: aconst_null
    //   11: putfield 74	fm/qingting/downloadnew/DownloadThread:mCurTask	Lfm/qingting/downloadnew/DownloadTask;
    //   14: aload_0
    //   15: aload_0
    //   16: getfield 53	fm/qingting/downloadnew/DownloadThread:mTaskQueue	Ljava/util/concurrent/BlockingQueue;
    //   19: invokeinterface 114 1 0
    //   24: checkcast 116	fm/qingting/downloadnew/DownloadTask
    //   27: putfield 74	fm/qingting/downloadnew/DownloadThread:mCurTask	Lfm/qingting/downloadnew/DownloadTask;
    //   30: aload_0
    //   31: getfield 74	fm/qingting/downloadnew/DownloadThread:mCurTask	Lfm/qingting/downloadnew/DownloadTask;
    //   34: getfield 120	fm/qingting/downloadnew/DownloadTask:mState	Lfm/qingting/downloadnew/DownloadState;
    //   37: getstatic 125	fm/qingting/downloadnew/DownloadState:READY	Lfm/qingting/downloadnew/DownloadState;
    //   40: if_acmpne +107 -> 147
    //   43: aload_0
    //   44: getfield 51	fm/qingting/downloadnew/DownloadThread:TAG	Ljava/lang/String;
    //   47: ldc 127
    //   49: invokestatic 83	android/util/Log:d	(Ljava/lang/String;Ljava/lang/String;)I
    //   52: pop
    //   53: aload_0
    //   54: getfield 51	fm/qingting/downloadnew/DownloadThread:TAG	Ljava/lang/String;
    //   57: new 27	java/lang/StringBuilder
    //   60: dup
    //   61: invokespecial 29	java/lang/StringBuilder:<init>	()V
    //   64: ldc 129
    //   66: invokevirtual 35	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   69: aload_0
    //   70: getfield 74	fm/qingting/downloadnew/DownloadThread:mCurTask	Lfm/qingting/downloadnew/DownloadTask;
    //   73: getfield 132	fm/qingting/downloadnew/DownloadTask:mTaskId	Ljava/lang/String;
    //   76: invokevirtual 35	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   79: invokevirtual 42	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   82: invokestatic 135	android/util/Log:i	(Ljava/lang/String;Ljava/lang/String;)I
    //   85: pop
    //   86: aload_0
    //   87: getfield 58	fm/qingting/downloadnew/DownloadThread:mDispatcher	Lfm/qingting/downloadnew/EventDispatcher;
    //   90: aload_0
    //   91: getfield 74	fm/qingting/downloadnew/DownloadThread:mCurTask	Lfm/qingting/downloadnew/DownloadTask;
    //   94: invokevirtual 139	fm/qingting/downloadnew/EventDispatcher:setCurrentTask	(Lfm/qingting/downloadnew/DownloadTask;)V
    //   97: aload_0
    //   98: getfield 65	fm/qingting/downloadnew/DownloadThread:mNetwork	Lfm/qingting/downloadnew/Network;
    //   101: aload_0
    //   102: getfield 74	fm/qingting/downloadnew/DownloadThread:mCurTask	Lfm/qingting/downloadnew/DownloadTask;
    //   105: invokeinterface 144 2 0
    //   110: aload_0
    //   111: getfield 47	fm/qingting/downloadnew/DownloadThread:mQuit	Z
    //   114: ifeq -109 -> 5
    //   117: aload_0
    //   118: getfield 51	fm/qingting/downloadnew/DownloadThread:TAG	Ljava/lang/String;
    //   121: ldc 146
    //   123: invokestatic 83	android/util/Log:d	(Ljava/lang/String;Ljava/lang/String;)I
    //   126: pop
    //   127: return
    //   128: astore_3
    //   129: aload_0
    //   130: getfield 47	fm/qingting/downloadnew/DownloadThread:mQuit	Z
    //   133: ifeq -128 -> 5
    //   136: aload_0
    //   137: getfield 51	fm/qingting/downloadnew/DownloadThread:TAG	Ljava/lang/String;
    //   140: ldc 148
    //   142: invokestatic 83	android/util/Log:d	(Ljava/lang/String;Ljava/lang/String;)I
    //   145: pop
    //   146: return
    //   147: aload_0
    //   148: getfield 51	fm/qingting/downloadnew/DownloadThread:TAG	Ljava/lang/String;
    //   151: new 27	java/lang/StringBuilder
    //   154: dup
    //   155: invokespecial 29	java/lang/StringBuilder:<init>	()V
    //   158: ldc 150
    //   160: invokevirtual 35	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   163: aload_0
    //   164: getfield 74	fm/qingting/downloadnew/DownloadThread:mCurTask	Lfm/qingting/downloadnew/DownloadTask;
    //   167: getfield 132	fm/qingting/downloadnew/DownloadTask:mTaskId	Ljava/lang/String;
    //   170: invokevirtual 35	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   173: ldc 152
    //   175: invokevirtual 35	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   178: invokevirtual 42	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   181: invokestatic 155	android/util/Log:e	(Ljava/lang/String;Ljava/lang/String;)I
    //   184: pop
    //   185: goto -180 -> 5
    //   188: astore 8
    //   190: goto -80 -> 110
    //
    // Exception table:
    //   from	to	target	type
    //   14	30	128	java/lang/InterruptedException
    //   97	110	188	fm/qingting/downloadnew/DownloadError
  }

  public void setUrlRewriter(UrlRewriter paramUrlRewriter)
  {
    this.mNetwork.setUrlRewriter(paramUrlRewriter);
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.downloadnew.DownloadThread
 * JD-Core Version:    0.6.2
 */