package fm.qingting.download.qtradiodownload.network.http.conn.impl;

import fm.qingting.download.qtradiodownload.network.http.HttpRequest;
import fm.qingting.download.qtradiodownload.network.http.conn.HttpConnection;

public class HttpConnectionManager
{
  public HttpConnection createDefaultHttpConnection(HttpRequest paramHttpRequest, DefaultHttpConnectionListener paramDefaultHttpConnectionListener)
  {
    return new DefaultHttpConnection(paramHttpRequest, paramDefaultHttpConnectionListener);
  }

  public void startConnection(HttpConnection paramHttpConnection)
  {
    new Thread(paramHttpConnection, paramHttpConnection.getName()).start();
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.download.qtradiodownload.network.http.conn.impl.HttpConnectionManager
 * JD-Core Version:    0.6.2
 */