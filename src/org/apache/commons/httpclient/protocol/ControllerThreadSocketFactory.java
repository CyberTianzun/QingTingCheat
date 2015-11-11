package org.apache.commons.httpclient.protocol;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import org.apache.commons.httpclient.ConnectTimeoutException;
import org.apache.commons.httpclient.util.TimeoutController;
import org.apache.commons.httpclient.util.TimeoutController.TimeoutException;

public final class ControllerThreadSocketFactory
{
  public static Socket createSocket(SocketTask paramSocketTask, int paramInt)
    throws IOException, UnknownHostException, ConnectTimeoutException
  {
    long l = paramInt;
    Socket localSocket;
    try
    {
      TimeoutController.execute(paramSocketTask, l);
      localSocket = paramSocketTask.getSocket();
      if (paramSocketTask.exception != null)
        throw paramSocketTask.exception;
    }
    catch (TimeoutController.TimeoutException localTimeoutException)
    {
      throw new ConnectTimeoutException("The host did not accept the connection within timeout of " + paramInt + " ms");
    }
    return localSocket;
  }

  public static Socket createSocket(ProtocolSocketFactory paramProtocolSocketFactory, String paramString, int paramInt1, InetAddress paramInetAddress, int paramInt2, int paramInt3)
    throws IOException, UnknownHostException, ConnectTimeoutException
  {
    ControllerThreadSocketFactory.1 local1 = new ControllerThreadSocketFactory.1(paramProtocolSocketFactory, paramString, paramInt1, paramInetAddress, paramInt2);
    long l = paramInt3;
    Socket localSocket;
    try
    {
      TimeoutController.execute(local1, l);
      localSocket = local1.getSocket();
      if (local1.exception != null)
        throw local1.exception;
    }
    catch (TimeoutController.TimeoutException localTimeoutException)
    {
      throw new ConnectTimeoutException("The host did not accept the connection within timeout of " + paramInt3 + " ms");
    }
    return localSocket;
  }

  public static abstract class SocketTask
    implements Runnable
  {
    private IOException exception;
    private Socket socket;

    public abstract void doit()
      throws IOException;

    protected Socket getSocket()
    {
      return this.socket;
    }

    public void run()
    {
      try
      {
        doit();
        return;
      }
      catch (IOException localIOException)
      {
        this.exception = localIOException;
      }
    }

    protected void setSocket(Socket paramSocket)
    {
      this.socket = paramSocket;
    }
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     org.apache.commons.httpclient.protocol.ControllerThreadSocketFactory
 * JD-Core Version:    0.6.2
 */