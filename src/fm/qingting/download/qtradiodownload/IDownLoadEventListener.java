package fm.qingting.download.qtradiodownload;

public abstract interface IDownLoadEventListener
{
  public abstract void onDownloadFailed(String paramString);

  public abstract void onDownloadProcessing(String paramString, int paramInt);

  public abstract void onDownloadSuccess(String paramString);
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.download.qtradiodownload.IDownLoadEventListener
 * JD-Core Version:    0.6.2
 */