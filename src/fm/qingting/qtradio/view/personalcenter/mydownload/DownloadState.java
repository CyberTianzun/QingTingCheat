package fm.qingting.qtradio.view.personalcenter.mydownload;

public enum DownloadState
{
  static
  {
    downloading = new DownloadState("downloading", 2);
    pausing = new DownloadState("pausing", 3);
    error = new DownloadState("error", 4);
    completed = new DownloadState("completed", 5);
    none = new DownloadState("none", 6);
    DownloadState[] arrayOfDownloadState = new DownloadState[7];
    arrayOfDownloadState[0] = notstarted;
    arrayOfDownloadState[1] = waiting;
    arrayOfDownloadState[2] = downloading;
    arrayOfDownloadState[3] = pausing;
    arrayOfDownloadState[4] = error;
    arrayOfDownloadState[5] = completed;
    arrayOfDownloadState[6] = none;
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.qtradio.view.personalcenter.mydownload.DownloadState
 * JD-Core Version:    0.6.2
 */