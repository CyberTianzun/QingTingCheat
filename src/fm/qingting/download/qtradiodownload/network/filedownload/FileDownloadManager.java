package fm.qingting.download.qtradiodownload.network.filedownload;

import fm.qingting.download.qtradiodownload.network.filedownload.model.DownloadTask;
import fm.qingting.download.qtradiodownload.network.filedownload.model.Part;
import fm.qingting.download.qtradiodownload.network.http.conn.HttpConnection;
import fm.qingting.download.qtradiodownload.network.http.conn.impl.HttpConnectionManager;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;

public class FileDownloadManager
{
  private static final int INTERVAL = 1000;
  private static final String TAG = "FileDownloadManager";
  private FileDownloadHttpConnectionListener connListener = new FileDownloadHttpConnectionListener()
  {
    public void onConnected()
    {
    }

    public void onConnecting()
    {
    }

    public void onError(int paramAnonymousInt, String paramAnonymousString)
    {
    }

    public void onTransError(String paramAnonymousString)
    {
      DownloadTask localDownloadTask = FileDownloadManager.this.findTaksByUUID(paramAnonymousString);
      if ((localDownloadTask != null) && (localDownloadTask.isTaskFailed()))
        FileDownloadManager.this.notifyTransError(paramAnonymousString);
    }

    public void onTransProgress(String paramAnonymousString, int paramAnonymousInt)
    {
      FileDownloadManager.this.notifyProgressing(paramAnonymousString, paramAnonymousInt);
    }

    public void onTransferred(String paramAnonymousString)
    {
      DownloadTask localDownloadTask = FileDownloadManager.this.findTaksByUUID(paramAnonymousString);
      if ((localDownloadTask != null) && (localDownloadTask.updateTaskFinished()))
        FileDownloadManager.this.notifyTransferred(paramAnonymousString);
    }
  };
  private HttpConnectionManager connMgr = null;
  private boolean isTimerStarted;
  private Vector<FileDownloadListener> listeners = null;
  private List<DownloadTask> taskes = null;
  private Timer timer = new Timer("FileDownloadTimer");

  public FileDownloadManager()
  {
    this(null);
    if (this.connMgr == null)
      this.connMgr = new HttpConnectionManager();
  }

  public FileDownloadManager(HttpConnectionManager paramHttpConnectionManager)
  {
    this.connMgr = paramHttpConnectionManager;
  }

  private boolean caculateSpeeds(int paramInt)
  {
    int i = 0;
    boolean bool = false;
    if (i < this.taskes.size())
    {
      DownloadTask localDownloadTask = (DownloadTask)this.taskes.get(i);
      if ((localDownloadTask.getState() < 0) || (localDownloadTask.getState() > 2))
      {
        localDownloadTask.setSpeed(0);
        localDownloadTask.setPreLength(localDownloadTask.getCurLength());
      }
      while (true)
      {
        i++;
        break;
        int j = localDownloadTask.getCurLength();
        int k = (j - localDownloadTask.getPreLength()) / 1024 / paramInt;
        localDownloadTask.setPreLength(j);
        localDownloadTask.setSpeed(k);
        bool = true;
      }
    }
    return bool;
  }

  // ERROR //
  private int initResourceSize(DownloadTask paramDownloadTask)
  {
    // Byte code:
    //   0: new 131	fm/qingting/download/qtradiodownload/network/http/HttpRequest
    //   3: dup
    //   4: aload_1
    //   5: invokevirtual 135	fm/qingting/download/qtradiodownload/network/filedownload/model/DownloadTask:getResourceUrl	()Ljava/lang/String;
    //   8: ldc 137
    //   10: invokespecial 140	fm/qingting/download/qtradiodownload/network/http/HttpRequest:<init>	(Ljava/lang/String;Ljava/lang/String;)V
    //   13: astore_2
    //   14: aload_2
    //   15: aload_1
    //   16: invokevirtual 144	fm/qingting/download/qtradiodownload/network/filedownload/model/DownloadTask:getConnProperties	()Ljava/util/HashMap;
    //   19: invokevirtual 148	fm/qingting/download/qtradiodownload/network/http/HttpRequest:setHeaders	(Ljava/util/HashMap;)V
    //   22: aload_0
    //   23: getfield 32	fm/qingting/download/qtradiodownload/network/filedownload/FileDownloadManager:connMgr	Lfm/qingting/download/qtradiodownload/network/http/conn/impl/HttpConnectionManager;
    //   26: aload_2
    //   27: aconst_null
    //   28: invokevirtual 152	fm/qingting/download/qtradiodownload/network/http/conn/impl/HttpConnectionManager:createDefaultHttpConnection	(Lfm/qingting/download/qtradiodownload/network/http/HttpRequest;Lfm/qingting/download/qtradiodownload/network/http/conn/impl/DefaultHttpConnectionListener;)Lfm/qingting/download/qtradiodownload/network/http/conn/HttpConnection;
    //   31: astore 10
    //   33: aload 10
    //   35: ldc 154
    //   37: invokevirtual 159	fm/qingting/download/qtradiodownload/network/http/conn/HttpConnection:setName	(Ljava/lang/String;)V
    //   40: aload 10
    //   42: invokevirtual 163	fm/qingting/download/qtradiodownload/network/http/conn/HttpConnection:connect	()Ljava/net/HttpURLConnection;
    //   45: invokevirtual 168	java/net/HttpURLConnection:getContentLength	()I
    //   48: istore 11
    //   50: iload 11
    //   52: istore 4
    //   54: aload_1
    //   55: iload 4
    //   57: invokevirtual 171	fm/qingting/download/qtradiodownload/network/filedownload/model/DownloadTask:setSize	(I)V
    //   60: iload 4
    //   62: ireturn
    //   63: astore 8
    //   65: iconst_m1
    //   66: istore 4
    //   68: aload 8
    //   70: astore 9
    //   72: aload 9
    //   74: invokevirtual 174	java/net/MalformedURLException:printStackTrace	()V
    //   77: iload 4
    //   79: ireturn
    //   80: astore 6
    //   82: iconst_m1
    //   83: istore 4
    //   85: aload 6
    //   87: astore 7
    //   89: aload 7
    //   91: invokevirtual 175	java/io/IOException:printStackTrace	()V
    //   94: iload 4
    //   96: ireturn
    //   97: astore_3
    //   98: iconst_m1
    //   99: istore 4
    //   101: aload_3
    //   102: astore 5
    //   104: aload 5
    //   106: invokevirtual 176	java/lang/NullPointerException:printStackTrace	()V
    //   109: iload 4
    //   111: ireturn
    //   112: astore 5
    //   114: goto -10 -> 104
    //   117: astore 7
    //   119: goto -30 -> 89
    //   122: astore 9
    //   124: goto -52 -> 72
    //
    // Exception table:
    //   from	to	target	type
    //   0	50	63	java/net/MalformedURLException
    //   0	50	80	java/io/IOException
    //   0	50	97	java/lang/NullPointerException
    //   54	60	112	java/lang/NullPointerException
    //   54	60	117	java/io/IOException
    //   54	60	122	java/net/MalformedURLException
  }

  private void notifyProgressing(String paramString, int paramInt)
  {
    Iterator localIterator = this.listeners.iterator();
    while (localIterator.hasNext())
      ((FileDownloadListener)localIterator.next()).onDownloadProcessing(paramString, paramInt);
  }

  private void notifyTransError(String paramString)
  {
    Iterator localIterator = this.listeners.iterator();
    while (localIterator.hasNext())
      ((FileDownloadListener)localIterator.next()).onDownloadFailed(paramString);
  }

  private void notifyTransferred(String paramString)
  {
    Iterator localIterator = this.listeners.iterator();
    while (localIterator.hasNext())
      ((FileDownloadListener)localIterator.next()).onDownloadTransferred(paramString);
  }

  private void startTimer()
  {
    try
    {
      boolean bool = this.isTimerStarted;
      if (bool);
      while (true)
      {
        return;
        TimerTask local3 = new TimerTask()
        {
          public void run()
          {
            if (!FileDownloadManager.this.caculateSpeeds(1))
              FileDownloadManager.this.stopTimer();
          }
        };
        this.timer = new Timer("FileDownloadTimer");
        this.timer.scheduleAtFixedRate(local3, 0L, 1000L);
        this.isTimerStarted = true;
      }
    }
    finally
    {
    }
  }

  private void stopTimer()
  {
    try
    {
      boolean bool = this.isTimerStarted;
      if (!bool);
      while (true)
      {
        return;
        this.timer.cancel();
        this.timer.purge();
        this.isTimerStarted = false;
      }
    }
    finally
    {
    }
  }

  public void addListener(FileDownloadListener paramFileDownloadListener)
  {
    if (!this.listeners.contains(paramFileDownloadListener))
      this.listeners.add(paramFileDownloadListener);
  }

  public void deleteResouseDownload(DownloadTask paramDownloadTask)
  {
    try
    {
      if ((paramDownloadTask.getState() >= 0) && (paramDownloadTask.getState() < 3))
        pauseResouseDownload(paramDownloadTask);
      this.taskes.remove(paramDownloadTask);
      return;
    }
    finally
    {
    }
  }

  @Deprecated
  public void deleteResouseDownloadByuuid(String paramString)
  {
    try
    {
      Iterator localIterator = this.taskes.iterator();
      while (localIterator.hasNext())
      {
        DownloadTask localDownloadTask = (DownloadTask)localIterator.next();
        if ((paramString != null) && (paramString.equals(localDownloadTask.getUUId())))
        {
          if ((localDownloadTask.getState() >= 0) && (localDownloadTask.getState() < 3))
            pauseResouseDownload(localDownloadTask);
          this.taskes.remove(localDownloadTask);
        }
      }
    }
    finally
    {
    }
  }

  public DownloadTask findTaksByUUID(String paramString)
  {
    if (paramString != null);
    try
    {
      List localList = this.taskes;
      DownloadTask localDownloadTask;
      if (localList == null)
        localDownloadTask = null;
      while (true)
      {
        return localDownloadTask;
        Iterator localIterator = this.taskes.iterator();
        while (true)
          if (localIterator.hasNext())
          {
            localDownloadTask = (DownloadTask)localIterator.next();
            if (paramString != null)
            {
              boolean bool = paramString.equals(localDownloadTask.getUUId());
              if (bool)
                break;
            }
          }
        localDownloadTask = null;
      }
    }
    finally
    {
    }
  }

  public ArrayList<DownloadTask> getDownloadingResourceList()
  {
    ArrayList localArrayList;
    try
    {
      localArrayList = new ArrayList();
      Iterator localIterator = this.taskes.iterator();
      while (localIterator.hasNext())
      {
        DownloadTask localDownloadTask = (DownloadTask)localIterator.next();
        if (localDownloadTask.getState() == 2)
          localArrayList.add(localDownloadTask);
      }
    }
    finally
    {
    }
    return localArrayList;
  }

  public List<DownloadTask> getResourceList()
  {
    try
    {
      List localList = this.taskes;
      return localList;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }

  public void pauseResouseDownload(DownloadTask paramDownloadTask)
  {
    try
    {
      paramDownloadTask.setState(3);
      return;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }

  public void removeAllListeners()
  {
    this.listeners.clear();
  }

  public void removeListener(FileDownloadListener paramFileDownloadListener)
  {
    this.listeners.remove(paramFileDownloadListener);
  }

  public void resumeResourceDownload(DownloadTask paramDownloadTask)
  {
    if ((paramDownloadTask.getState() < 0) || (paramDownloadTask.getState() == 4))
    {
      paramDownloadTask.setTaskFinished(false);
      startResourceDownload(paramDownloadTask);
    }
    while ((paramDownloadTask.getState() == 1) && (paramDownloadTask.getState() == 2))
      return;
    if (new File(paramDownloadTask.getFileDirectory() + File.separator + paramDownloadTask.getFileName()).exists())
    {
      paramDownloadTask.setState(2);
      for (int i = 0; i < paramDownloadTask.getParts().size(); i++)
      {
        Part localPart = (Part)paramDownloadTask.getParts().get(i);
        if (localPart.getState() != 4)
        {
          FileDownloadHttpConnection localFileDownloadHttpConnection = new FileDownloadHttpConnection(localPart, this.connListener);
          localFileDownloadHttpConnection.setName(paramDownloadTask.getFileName() + "-part-" + i);
          this.connMgr.startConnection(localFileDownloadHttpConnection);
        }
      }
    }
    paramDownloadTask.setTaskFinished(false);
    startResourceDownload(paramDownloadTask);
    return;
    startTimer();
  }

  public void startResourceDownload(final DownloadTask paramDownloadTask)
  {
    try
    {
      if (!this.taskes.contains(paramDownloadTask))
        this.taskes.add(paramDownloadTask);
      notifyProgressing(paramDownloadTask.getUUId(), 0);
      new Thread(new Runnable()
      {
        public void run()
        {
          paramDownloadTask.setState(0);
          int i;
          int j;
          int k;
          try
          {
            FileUtil.createFile(paramDownloadTask.getFileDirectory() + File.separator + paramDownloadTask.getFileName(), true);
            i = paramDownloadTask.getSize();
            if (i == -1)
            {
              paramDownloadTask.setState(1);
              i = FileDownloadManager.this.initResourceSize(paramDownloadTask);
            }
            paramDownloadTask.setState(2);
            paramDownloadTask.getParts().clear();
            if (i == -1)
            {
              Part localPart1 = new Part(paramDownloadTask, 0, -1, 0);
              paramDownloadTask.getParts().add(localPart1);
              FileDownloadHttpConnection localFileDownloadHttpConnection1 = new FileDownloadHttpConnection(localPart1, null);
              localFileDownloadHttpConnection1.setName(paramDownloadTask.getFileName() + "-part");
              FileDownloadManager.this.connMgr.startConnection(localFileDownloadHttpConnection1);
              return;
            }
          }
          catch (IOException localIOException)
          {
            while (true)
              localIOException.printStackTrace();
            if (paramDownloadTask.getThreadSize() < 1)
              paramDownloadTask.setThreadSize(3);
            j = i / paramDownloadTask.getThreadSize();
            if (i - j * paramDownloadTask.getThreadSize() > 0)
              j++;
            k = 0;
          }
          label249: if (k < paramDownloadTask.getThreadSize())
            if (k != -1 + paramDownloadTask.getThreadSize())
              break label399;
          label399: for (int m = i - j * k; ; m = j)
          {
            Part localPart2 = new Part(paramDownloadTask, k * j, m, 0);
            paramDownloadTask.getParts().add(localPart2);
            if (!localPart2.isLock())
            {
              FileDownloadHttpConnection localFileDownloadHttpConnection2 = new FileDownloadHttpConnection(localPart2, FileDownloadManager.this.connListener);
              localFileDownloadHttpConnection2.setName(paramDownloadTask.getFileName() + "-part-" + k);
              FileDownloadManager.this.connMgr.startConnection(localFileDownloadHttpConnection2);
            }
            k++;
            break label249;
            break;
          }
        }
      }).start();
      startTimer();
      return;
    }
    finally
    {
    }
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.download.qtradiodownload.network.filedownload.FileDownloadManager
 * JD-Core Version:    0.6.2
 */