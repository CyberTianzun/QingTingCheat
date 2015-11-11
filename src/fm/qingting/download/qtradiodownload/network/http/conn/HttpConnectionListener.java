package fm.qingting.download.qtradiodownload.network.http.conn;

public abstract interface HttpConnectionListener
{
  public abstract void onConnected();

  public abstract void onConnecting();

  public abstract void onError(int paramInt, String paramString);
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.download.qtradiodownload.network.http.conn.HttpConnectionListener
 * JD-Core Version:    0.6.2
 */