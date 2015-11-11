package fm.qingting.download.qtradiodownload.service;

import android.app.Notification;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import fm.qingting.download.qtradiodownload.core.Core;
import fm.qingting.download.qtradiodownload.network.filedownload.FileDownloadManager;
import fm.qingting.download.qtradiodownload.persistent.DownloadTaskDB;
import fm.qingting.download.qtradiodownload.platform.AndroidFactory;

public class QT_RadioDownloadService extends Service
{
  private static final String TAG = "QT_RadioDownloadService";

  private void avoidKilled()
  {
    startForeground(0, new Notification());
  }

  private void startCore()
  {
    try
    {
      Core localCore = Core.getInstance();
      if (localCore != null);
      while (true)
      {
        return;
        DownloadTaskDB.createInstance(getApplicationContext());
        Core.createInstance();
        Core.getInstance().startCore();
      }
    }
    finally
    {
    }
  }

  private void stopCore()
  {
    try
    {
      Core localCore = Core.getInstance();
      if (localCore == null);
      while (true)
      {
        return;
        Core.terminate();
      }
    }
    finally
    {
    }
  }

  public IBinder onBind(Intent paramIntent)
  {
    return new DownloadBinder();
  }

  public void onCreate()
  {
    super.onCreate();
    avoidKilled();
    AndroidFactory.setApplicationContext(getApplication());
    startCore();
  }

  public void onDestroy()
  {
    stopCore();
    super.onDestroy();
  }

  public class DownloadBinder extends Binder
  {
    public DownloadBinder()
    {
    }

    public FileDownloadManager getDownloadMgr()
    {
      return Core.getInstance().getFileDownloadManager();
    }
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.download.qtradiodownload.service.QT_RadioDownloadService
 * JD-Core Version:    0.6.2
 */