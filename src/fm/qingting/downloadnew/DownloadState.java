package fm.qingting.downloadnew;

public enum DownloadState
{
  private String title;

  static
  {
    READY = new DownloadState("READY", 1, "等待中");
    CONNECTING = new DownloadState("CONNECTING", 2, "连接中...");
    DOWNLOADING = new DownloadState("DOWNLOADING", 3, "正在下载...");
    SUCCESS = new DownloadState("SUCCESS", 4, "已完成。");
    ERROR = new DownloadState("ERROR", 5, "出错!");
    PAUSED = new DownloadState("PAUSED", 6, "暂停");
    DownloadState[] arrayOfDownloadState = new DownloadState[7];
    arrayOfDownloadState[0] = UNSPECIFIED;
    arrayOfDownloadState[1] = READY;
    arrayOfDownloadState[2] = CONNECTING;
    arrayOfDownloadState[3] = DOWNLOADING;
    arrayOfDownloadState[4] = SUCCESS;
    arrayOfDownloadState[5] = ERROR;
    arrayOfDownloadState[6] = PAUSED;
  }

  private DownloadState(String paramString)
  {
    this.title = paramString;
  }

  public static DownloadState valueOf(int paramInt)
  {
    if ((paramInt >= 0) && (paramInt < values().length))
      return values()[paramInt];
    throw new IllegalArgumentException();
  }

  public String toString()
  {
    return this.title;
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.downloadnew.DownloadState
 * JD-Core Version:    0.6.2
 */