package fm.qingting.download.qtradiodownload.core;

import fm.qingting.download.qtradiodownload.network.filedownload.FileDownloadManager;
import fm.qingting.download.qtradiodownload.persistent.DownloadTaskDB;
import java.util.List;

public class Core
{
  private static Core instance;
  private FileDownloadManager downloadMgr;
  private NetworkManager networkMgr;

  private Core()
  {
    NetworkManager.createInstance(this);
    this.downloadMgr = new FileDownloadManager();
  }

  public static void createInstance()
  {
    if (instance == null)
      instance = new Core();
  }

  public static Core getInstance()
  {
    return instance;
  }

  public static void terminate()
  {
    if (instance != null)
      instance.stopCore();
    instance = null;
  }

  public FileDownloadManager getFileDownloadManager()
  {
    return this.downloadMgr;
  }

  public NetworkManager getNetWorkManager()
  {
    return this.networkMgr;
  }

  public void startCore()
  {
    this.networkMgr = NetworkManager.getInstance();
    this.networkMgr.start();
    this.downloadMgr.getResourceList().addAll(DownloadTaskDB.getInstance().queryAllDownloadTask());
  }

  public void stopCore()
  {
    this.networkMgr.stop();
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.download.qtradiodownload.core.Core
 * JD-Core Version:    0.6.2
 */