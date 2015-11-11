package fm.qingting.download;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.StatFs;
import fm.qingting.download.qtradiodownload.IDownLoadEventListener;
import fm.qingting.download.qtradiodownload.network.filedownload.FileDownloadListener;
import fm.qingting.download.qtradiodownload.network.filedownload.FileDownloadManager;
import fm.qingting.download.qtradiodownload.network.filedownload.model.DownloadTask;
import fm.qingting.download.qtradiodownload.persistent.DownloadTaskDB;
import fm.qingting.download.qtradiodownload.service.QT_RadioDownloadService;
import fm.qingting.download.qtradiodownload.service.QT_RadioDownloadService.DownloadBinder;
import fm.qingting.qtradio.model.Downloadobject;
import fm.qingting.qtradio.model.GlobalCfg;
import java.io.File;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

@Deprecated
public class QTRadioDownloadAgent
{
  private static final int MESSAGE_DOWNLOAD_COMPLETE = 2;
  private static final int MESSAGE_DOWNLOAD_FAILED = 3;
  private static final int MESSAGE_DOWNLOAD_PROCESSING = 1;
  private static final int MESSAGE_DOWNLOAD_START = 0;
  private static final String TAG = "QTRadioDownloadAgent";
  private static QTRadioDownloadAgent instance;
  private String directory;
  private FileDownloadManager downloadMgr = null;
  private FileDownloadListener fdListener = new FileDownloadListener()
  {
    public void onDownloadFailed(String paramAnonymousString)
    {
      Message localMessage = new Message();
      localMessage.what = 3;
      localMessage.obj = paramAnonymousString;
      QTRadioDownloadAgent.this.handler.sendMessage(localMessage);
    }

    public void onDownloadProcessing(String paramAnonymousString, int paramAnonymousInt)
    {
      Message localMessage = new Message();
      localMessage.what = 1;
      localMessage.obj = paramAnonymousString;
      localMessage.arg1 = paramAnonymousInt;
      QTRadioDownloadAgent.this.handler.sendMessage(localMessage);
    }

    public void onDownloadTransferred(String paramAnonymousString)
    {
      Message localMessage = new Message();
      localMessage.what = 2;
      localMessage.obj = paramAnonymousString;
      QTRadioDownloadAgent.this.handler.sendMessage(localMessage);
    }
  };
  private Handler handler = new Handler()
  {
    public void handleMessage(Message paramAnonymousMessage)
    {
      super.handleMessage(paramAnonymousMessage);
      switch (paramAnonymousMessage.what)
      {
      default:
        return;
      case 0:
        DownloadTask localDownloadTask3 = (DownloadTask)paramAnonymousMessage.obj;
        QTRadioDownloadAgent.this.dispatchonDownloadProcessingEvent(localDownloadTask3.getUUId(), 0);
        return;
      case 1:
        String str3 = (String)paramAnonymousMessage.obj;
        int i = paramAnonymousMessage.arg1;
        QTRadioDownloadAgent.this.dispatchonDownloadProcessingEvent(str3, i);
        return;
      case 2:
        String str2 = (String)paramAnonymousMessage.obj;
        DownloadTask localDownloadTask2 = QTRadioDownloadAgent.this.downloadMgr.findTaksByUUID(str2);
        if (localDownloadTask2 != null)
          QTRadioDownloadAgent.this.downloadMgr.deleteResouseDownload(localDownloadTask2);
        DownloadTaskDB.getInstance().deleteDownloadTask(str2);
        QTRadioDownloadAgent.this.dispatchonDownloadSuccessEvent(str2);
        return;
      case 3:
      }
      String str1 = (String)paramAnonymousMessage.obj;
      DownloadTask localDownloadTask1 = QTRadioDownloadAgent.this.downloadMgr.findTaksByUUID(str1);
      if (localDownloadTask1 != null)
        DownloadTaskDB.getInstance().updateDownloadTask(localDownloadTask1);
      QTRadioDownloadAgent.this.dispatchononDownloadFailedEvent(str1);
    }
  };
  public HashSet<WeakReference<IDownLoadEventListener>> listeners = new HashSet();
  private Context mContext;
  private List<OnDownloadPathChangeListener> mDownloadPathChangeListener;
  private ServiceConnection serviceConn = new ServiceConnection()
  {
    public void onServiceConnected(ComponentName paramAnonymousComponentName, IBinder paramAnonymousIBinder)
    {
      QTRadioDownloadAgent.access$002(QTRadioDownloadAgent.this, ((QT_RadioDownloadService.DownloadBinder)paramAnonymousIBinder).getDownloadMgr());
      QTRadioDownloadAgent.this.downloadMgr.addListener(QTRadioDownloadAgent.this.fdListener);
    }

    public void onServiceDisconnected(ComponentName paramAnonymousComponentName)
    {
      if (QTRadioDownloadAgent.this.downloadMgr != null)
        QTRadioDownloadAgent.this.downloadMgr.removeListener(QTRadioDownloadAgent.this.fdListener);
    }
  };

  private boolean connected()
  {
    return this.downloadMgr != null;
  }

  public static QTRadioDownloadAgent getInstance()
  {
    if (instance == null)
      instance = new QTRadioDownloadAgent();
    return instance;
  }

  public void addListener(IDownLoadEventListener paramIDownLoadEventListener)
  {
    Iterator localIterator = this.listeners.iterator();
    while (localIterator.hasNext())
      if (((WeakReference)localIterator.next()).get() == paramIDownLoadEventListener)
        return;
    this.listeners.add(new WeakReference(paramIDownLoadEventListener));
  }

  public void addPathChangeListener(OnDownloadPathChangeListener paramOnDownloadPathChangeListener)
  {
    if (this.mDownloadPathChangeListener == null)
      this.mDownloadPathChangeListener = new ArrayList();
    this.mDownloadPathChangeListener.add(paramOnDownloadPathChangeListener);
  }

  public void changeDownloadPath(String paramString)
  {
    this.directory = paramString;
    if (this.mDownloadPathChangeListener != null)
      for (int i = 0; i < this.mDownloadPathChangeListener.size(); i++)
        ((OnDownloadPathChangeListener)this.mDownloadPathChangeListener.get(i)).onPathChanged(paramString);
    GlobalCfg.getInstance(null).changeDownloadPath(paramString);
  }

  public boolean checkServiceConnectedState()
  {
    if (!connected())
    {
      startService();
      return false;
    }
    return true;
  }

  public boolean createTask(Downloadobject paramDownloadobject)
  {
    if ((!checkServiceConnectedState()) || (paramDownloadobject == null))
      return false;
    DownloadTask localDownloadTask1 = this.downloadMgr.findTaksByUUID(paramDownloadobject.uuid);
    if (localDownloadTask1 == null)
    {
      DownloadTask localDownloadTask2 = new DownloadTask(paramDownloadobject.uuid, paramDownloadobject.downloadurl, this.directory, paramDownloadobject.programName, 1);
      localDownloadTask2.setSize(paramDownloadobject.size);
      DownloadTaskDB.getInstance().insertDownloadTask(localDownloadTask2);
      this.downloadMgr.startResourceDownload(localDownloadTask2);
      Message localMessage = new Message();
      localMessage.what = 0;
      localMessage.obj = localDownloadTask2;
      this.handler.sendMessage(localMessage);
      return true;
    }
    this.downloadMgr.resumeResourceDownload(localDownloadTask1);
    return true;
  }

  public boolean deleteDownLoadFile(String paramString)
  {
    if (paramString == null)
      return false;
    return new File(this.directory + File.separator, paramString).delete();
  }

  public boolean deleteTask(String paramString, boolean paramBoolean)
  {
    if ((!checkServiceConnectedState()) || (paramString == null));
    DownloadTask localDownloadTask;
    do
    {
      do
      {
        return false;
        localDownloadTask = this.downloadMgr.findTaksByUUID(paramString);
      }
      while (localDownloadTask == null);
      this.downloadMgr.deleteResouseDownload(localDownloadTask);
      DownloadTaskDB.getInstance().deleteDownloadTask(paramString);
      if (!paramBoolean)
        break;
    }
    while (localDownloadTask == null);
    if (new File(localDownloadTask.getFileDirectory() + File.separator, paramString).delete());
    return true;
  }

  public void dispatchonDownloadProcessingEvent(String paramString, int paramInt)
  {
    removeUnavailableListener();
    Iterator localIterator = new HashSet(this.listeners).iterator();
    while (localIterator.hasNext())
    {
      IDownLoadEventListener localIDownLoadEventListener = (IDownLoadEventListener)((WeakReference)localIterator.next()).get();
      if (localIDownLoadEventListener != null)
        localIDownLoadEventListener.onDownloadProcessing(paramString, paramInt);
    }
  }

  public void dispatchonDownloadSuccessEvent(String paramString)
  {
    removeUnavailableListener();
    Iterator localIterator = new HashSet(this.listeners).iterator();
    while (localIterator.hasNext())
    {
      IDownLoadEventListener localIDownLoadEventListener = (IDownLoadEventListener)((WeakReference)localIterator.next()).get();
      if (localIDownLoadEventListener != null)
        localIDownLoadEventListener.onDownloadSuccess(paramString);
    }
  }

  public void dispatchononDownloadFailedEvent(String paramString)
  {
    removeUnavailableListener();
    Iterator localIterator = new HashSet(this.listeners).iterator();
    while (localIterator.hasNext())
    {
      IDownLoadEventListener localIDownLoadEventListener = (IDownLoadEventListener)((WeakReference)localIterator.next()).get();
      if (localIDownLoadEventListener != null)
        localIDownLoadEventListener.onDownloadFailed(paramString);
    }
  }

  public long getAvailableExternalMemorySize()
  {
    long l1 = 0L;
    try
    {
      if (isSDCardAvailable())
      {
        StatFs localStatFs = new StatFs(Environment.getExternalStorageDirectory().getPath());
        long l2 = localStatFs.getBlockSize();
        int i = localStatFs.getAvailableBlocks();
        l1 = l2 * i;
      }
      return l1;
    }
    catch (Exception localException)
    {
    }
    return l1;
  }

  public long getAvailableExternalMemorySize(String paramString)
  {
    try
    {
      StatFs localStatFs = new StatFs(paramString);
      long l = localStatFs.getBlockSize();
      int i = localStatFs.getAvailableBlocks();
      return l * i;
    }
    catch (Exception localException)
    {
    }
    return 0L;
  }

  public String getDownLoadPath()
  {
    if (this.directory == null)
      this.directory = GlobalCfg.getInstance(null).getDownloadPath();
    return this.directory;
  }

  public long getTotalExternalMemorySize(String paramString)
  {
    try
    {
      StatFs localStatFs = new StatFs(paramString);
      long l = localStatFs.getBlockSize();
      int i = localStatFs.getBlockCount();
      return l * i;
    }
    catch (Exception localException)
    {
    }
    return 0L;
  }

  public void initDownload(Context paramContext)
  {
    this.mContext = paramContext;
  }

  public int isDownloadAvailable()
  {
    if (this.downloadMgr == null)
      return -1;
    return this.downloadMgr.getResourceList().size();
  }

  public boolean isSDCardAvailable()
  {
    return Environment.getExternalStorageState().equals("mounted");
  }

  public boolean isTaskPaused(String paramString)
  {
    if (this.downloadMgr == null);
    DownloadTask localDownloadTask;
    do
    {
      return false;
      localDownloadTask = this.downloadMgr.findTaksByUUID(paramString);
    }
    while ((localDownloadTask == null) || (localDownloadTask.getState() != 3));
    return true;
  }

  public boolean pauseTask(String paramString)
  {
    if ((!checkServiceConnectedState()) || (paramString == null));
    DownloadTask localDownloadTask;
    do
    {
      return false;
      localDownloadTask = this.downloadMgr.findTaksByUUID(paramString);
    }
    while (localDownloadTask == null);
    this.downloadMgr.pauseResouseDownload(localDownloadTask);
    DownloadTaskDB.getInstance().updateDownloadTask(localDownloadTask);
    return true;
  }

  public void releasebindService()
  {
    if (this.downloadMgr != null)
    {
      Iterator localIterator = this.downloadMgr.getDownloadingResourceList().iterator();
      while (localIterator.hasNext())
      {
        DownloadTask localDownloadTask = (DownloadTask)localIterator.next();
        this.downloadMgr.pauseResouseDownload(localDownloadTask);
        DownloadTaskDB.getInstance().updateDownloadTask(localDownloadTask);
      }
    }
    if (this.mContext != null)
      this.mContext.unbindService(this.serviceConn);
    this.mContext = null;
  }

  public void removePathChangeListener(OnDownloadPathChangeListener paramOnDownloadPathChangeListener)
  {
    if ((this.mDownloadPathChangeListener == null) || (paramOnDownloadPathChangeListener == null))
      return;
    this.mDownloadPathChangeListener.remove(paramOnDownloadPathChangeListener);
  }

  public void removeUnavailableListener()
  {
    Iterator localIterator = this.listeners.iterator();
    while (localIterator.hasNext())
      if ((IDownLoadEventListener)((WeakReference)localIterator.next()).get() == null)
        localIterator.remove();
  }

  public boolean resumeTask(String paramString)
  {
    if ((!checkServiceConnectedState()) || (paramString == null));
    DownloadTask localDownloadTask;
    do
    {
      return false;
      localDownloadTask = this.downloadMgr.findTaksByUUID(paramString);
    }
    while (localDownloadTask == null);
    this.downloadMgr.resumeResourceDownload(localDownloadTask);
    return true;
  }

  public void startService()
  {
    if (this.mContext != null)
    {
      Intent localIntent = new Intent(this.mContext, QT_RadioDownloadService.class);
      this.mContext.bindService(localIntent, this.serviceConn, 1);
    }
  }

  public static abstract interface OnDownloadPathChangeListener
  {
    public abstract void onPathChanged(String paramString);
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.download.QTRadioDownloadAgent
 * JD-Core Version:    0.6.2
 */