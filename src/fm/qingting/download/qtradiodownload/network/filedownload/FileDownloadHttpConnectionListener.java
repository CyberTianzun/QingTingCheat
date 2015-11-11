package fm.qingting.download.qtradiodownload.network.filedownload;

import fm.qingting.download.qtradiodownload.network.http.conn.HttpConnectionListener;

public abstract interface FileDownloadHttpConnectionListener extends HttpConnectionListener
{
  public abstract void onTransError(String paramString);

  public abstract void onTransProgress(String paramString, int paramInt);

  public abstract void onTransferred(String paramString);
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.download.qtradiodownload.network.filedownload.FileDownloadHttpConnectionListener
 * JD-Core Version:    0.6.2
 */