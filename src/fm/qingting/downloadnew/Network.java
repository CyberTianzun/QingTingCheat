package fm.qingting.downloadnew;

abstract interface Network
{
  public abstract void performDownload(DownloadTask paramDownloadTask)
    throws DownloadError;

  public abstract void setUrlRewriter(UrlRewriter paramUrlRewriter);
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.downloadnew.Network
 * JD-Core Version:    0.6.2
 */