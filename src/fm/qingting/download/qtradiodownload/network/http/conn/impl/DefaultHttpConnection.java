package fm.qingting.download.qtradiodownload.network.http.conn.impl;

import fm.qingting.download.qtradiodownload.network.http.HttpRequest;
import fm.qingting.download.qtradiodownload.network.http.conn.HttpConnection;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class DefaultHttpConnection extends HttpConnection
{
  private static final String TAG = "DefaultHttpConnection";

  protected DefaultHttpConnection(HttpRequest paramHttpRequest)
  {
    this(paramHttpRequest, null);
  }

  protected DefaultHttpConnection(HttpRequest paramHttpRequest, DefaultHttpConnectionListener paramDefaultHttpConnectionListener)
  {
    this.request = paramHttpRequest;
    this.listener = paramDefaultHttpConnectionListener;
  }

  protected static byte[] readStream(InputStream paramInputStream)
    throws IOException
  {
    byte[] arrayOfByte1 = new byte[4096];
    ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream();
    while (true)
    {
      int i = paramInputStream.read(arrayOfByte1);
      if (i == -1)
        break;
      localByteArrayOutputStream.write(arrayOfByte1, 0, i);
    }
    byte[] arrayOfByte2 = localByteArrayOutputStream.toByteArray();
    localByteArrayOutputStream.close();
    return arrayOfByte2;
  }

  public void handleConnectingFail()
  {
  }

  public void handleConnectionFinished()
  {
  }

  public void handlePart()
  {
  }

  public void handleResponse(InputStream paramInputStream)
    throws IOException
  {
    byte[] arrayOfByte = readStream(paramInputStream);
    if (this.listener != null)
      ((DefaultHttpConnectionListener)this.listener).onResponse(arrayOfByte);
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.download.qtradiodownload.network.http.conn.impl.DefaultHttpConnection
 * JD-Core Version:    0.6.2
 */