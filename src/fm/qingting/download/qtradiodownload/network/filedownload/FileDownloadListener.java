package fm.qingting.download.qtradiodownload.network.filedownload;

public abstract interface FileDownloadListener
{
  public abstract void onDownloadFailed(String paramString);

  public abstract void onDownloadProcessing(String paramString, int paramInt);

  public abstract void onDownloadTransferred(String paramString);
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.download.qtradiodownload.network.filedownload.FileDownloadListener
 * JD-Core Version:    0.6.2
 */