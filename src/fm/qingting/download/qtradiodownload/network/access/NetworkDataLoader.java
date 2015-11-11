package fm.qingting.download.qtradiodownload.network.access;

import fm.qingting.download.qtradiodownload.network.http.conn.HttpConnection;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor.DiscardPolicy;
import java.util.concurrent.TimeUnit;

public class NetworkDataLoader
{
  private static ThreadPoolExecutor threadPool;

  public static void excute(HttpConnection paramHttpConnection)
  {
    new Thread(paramHttpConnection).start();
  }

  private static void initialize()
  {
    try
    {
      ThreadPoolExecutor localThreadPoolExecutor = threadPool;
      if (localThreadPoolExecutor != null);
      while (true)
      {
        return;
        threadPool = new ThreadPoolExecutor(1 + Runtime.getRuntime().availableProcessors(), 1 + Runtime.getRuntime().availableProcessors(), 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue());
        threadPool.setRejectedExecutionHandler(new ThreadPoolExecutor.DiscardPolicy());
      }
    }
    finally
    {
    }
  }

  public static void poolExcute(HttpConnection paramHttpConnection)
  {
    if ((threadPool == null) || (threadPool.isShutdown()))
    {
      threadPool = null;
      initialize();
    }
    threadPool.execute(paramHttpConnection);
  }

  public static void shutdown()
  {
    if (threadPool != null)
    {
      threadPool.shutdownNow();
      threadPool = null;
    }
  }

  public static void waitTerminationAndExit()
    throws InterruptedException
  {
    if (threadPool != null)
    {
      if (!threadPool.isShutdown())
        threadPool.shutdownNow();
      threadPool.awaitTermination(3L, TimeUnit.SECONDS);
      threadPool = null;
    }
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.download.qtradiodownload.network.access.NetworkDataLoader
 * JD-Core Version:    0.6.2
 */