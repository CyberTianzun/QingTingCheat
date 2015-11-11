package fm.qingting.download.qtradiodownload.network.http.conn.impl;

import fm.qingting.download.qtradiodownload.network.http.conn.HttpConnectionListener;

public abstract interface DefaultHttpConnectionListener extends HttpConnectionListener
{
  public abstract void onResponse(byte[] paramArrayOfByte);
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.download.qtradiodownload.network.http.conn.impl.DefaultHttpConnectionListener
 * JD-Core Version:    0.6.2
 */