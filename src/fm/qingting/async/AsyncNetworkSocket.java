package fm.qingting.async;

import android.util.Log;
import fm.qingting.async.callback.CompletedCallback;
import fm.qingting.async.callback.DataCallback;
import fm.qingting.async.callback.WritableCallback;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;

public class AsyncNetworkSocket
  implements AsyncSocket
{
  boolean closeReported;
  private ChannelWrapper mChannel;
  CompletedCallback mClosedHander;
  private CompletedCallback mCompletedCallback;
  DataCallback mDataHandler;
  boolean mEndReported;
  private SelectionKey mKey;
  boolean mPaused = false;
  Exception mPendingEndException;
  private AsyncServer mServer;
  int mToAlloc = 0;
  WritableCallback mWriteableHandler;
  int maxAlloc;
  private ByteBufferList pending;
  InetSocketAddress socketAddress;

  static
  {
    if (!AsyncNetworkSocket.class.desiredAssertionStatus());
    for (boolean bool = true; ; bool = false)
    {
      $assertionsDisabled = bool;
      return;
    }
  }

  private void handleRemaining(int paramInt)
  {
    if (paramInt > 0)
    {
      assert (!this.mChannel.isChunked());
      this.mKey.interestOps(5);
      return;
    }
    this.mKey.interestOps(1);
  }

  private void spitPending()
  {
    if (this.pending != null)
    {
      Util.emitAllData(this, this.pending);
      if (this.pending.remaining() == 0)
        this.pending = null;
    }
  }

  void attach(DatagramChannel paramDatagramChannel)
    throws IOException
  {
    this.mChannel = new DatagramChannelWrapper(paramDatagramChannel);
    this.maxAlloc = 8192;
  }

  void attach(SocketChannel paramSocketChannel, InetSocketAddress paramInetSocketAddress)
    throws IOException
  {
    this.socketAddress = paramInetSocketAddress;
    this.maxAlloc = 262144;
    this.mChannel = new SocketChannelWrapper(paramSocketChannel);
  }

  public void close()
  {
    closeInternal();
    reportClose(null);
  }

  public void closeInternal()
  {
    this.mKey.cancel();
    try
    {
      this.mChannel.close();
      return;
    }
    catch (IOException localIOException)
    {
    }
  }

  public void end()
  {
    this.mChannel.shutdownOutput();
  }

  ChannelWrapper getChannel()
  {
    return this.mChannel;
  }

  public CompletedCallback getClosedCallback()
  {
    return this.mClosedHander;
  }

  public DataCallback getDataCallback()
  {
    return this.mDataHandler;
  }

  public CompletedCallback getEndCallback()
  {
    return this.mCompletedCallback;
  }

  public int getLocalPort()
  {
    return this.mChannel.getLocalPort();
  }

  public InetSocketAddress getRemoteAddress()
  {
    return this.socketAddress;
  }

  public AsyncServer getServer()
  {
    return this.mServer;
  }

  public WritableCallback getWriteableCallback()
  {
    return this.mWriteableHandler;
  }

  public boolean isChunked()
  {
    return this.mChannel.isChunked();
  }

  public boolean isOpen()
  {
    return (this.mChannel.isConnected()) && (this.mKey.isValid());
  }

  public boolean isPaused()
  {
    return this.mPaused;
  }

  public void onDataWritable()
  {
    assert (this.mWriteableHandler != null);
    this.mWriteableHandler.onWriteable();
  }

  int onReadable()
  {
    int i = 1;
    spitPending();
    boolean bool = this.mPaused;
    int j = 0;
    if (bool);
    label183: 
    do
    {
      return j;
      ByteBufferList localByteBufferList;
      while (true)
      {
        int k;
        try
        {
          ByteBuffer localByteBuffer = ByteBufferList.obtain(Math.min(Math.max(this.mToAlloc, 4096), this.maxAlloc));
          k = this.mChannel.read(localByteBuffer);
          j = 0;
          if (k < 0)
          {
            closeInternal();
            if (k <= 0)
              break label183;
            this.mToAlloc = (k * 2);
            localByteBuffer.limit(localByteBuffer.position());
            localByteBuffer.position(0);
            localByteBufferList = new ByteBufferList(new ByteBuffer[] { localByteBuffer });
            Util.emitAllData(this, localByteBufferList);
            if (localByteBuffer.remaining() == 0)
              break label183;
            if (($assertionsDisabled) || (this.pending == null))
              break;
            throw new AssertionError();
          }
        }
        catch (Exception localException)
        {
          closeInternal();
          reportEndPending(localException);
          reportClose(localException);
          return j;
        }
        j = 0 + k;
        i = 0;
      }
      this.pending = localByteBufferList;
    }
    while (i == 0);
    reportEndPending(null);
    reportClose(null);
    return j;
  }

  public void pause()
  {
    if (this.mServer.getAffinity() != Thread.currentThread())
      this.mServer.run(new Runnable()
      {
        public void run()
        {
          AsyncNetworkSocket.this.pause();
        }
      });
    while (this.mPaused)
      return;
    this.mPaused = true;
    try
    {
      this.mKey.interestOps(0xFFFFFFFE & this.mKey.interestOps());
      return;
    }
    catch (Exception localException)
    {
    }
  }

  protected void reportClose(Exception paramException)
  {
    if (this.closeReported);
    do
    {
      return;
      this.closeReported = true;
    }
    while (this.mClosedHander == null);
    this.mClosedHander.onCompleted(paramException);
    this.mClosedHander = null;
  }

  void reportEnd(Exception paramException)
  {
    if (this.mEndReported);
    do
    {
      return;
      this.mEndReported = true;
      if (this.mCompletedCallback != null)
      {
        this.mCompletedCallback.onCompleted(paramException);
        return;
      }
    }
    while (paramException == null);
    Log.e("NIO", "Unhandled exception", paramException);
  }

  void reportEndPending(Exception paramException)
  {
    if (this.pending != null)
    {
      this.mPendingEndException = paramException;
      return;
    }
    reportEnd(paramException);
  }

  public void resume()
  {
    if (this.mServer.getAffinity() != Thread.currentThread())
      this.mServer.run(new Runnable()
      {
        public void run()
        {
          AsyncNetworkSocket.this.resume();
        }
      });
    while (true)
    {
      return;
      if (!this.mPaused)
        continue;
      this.mPaused = false;
      try
      {
        this.mKey.interestOps(0x1 | this.mKey.interestOps());
        label58: spitPending();
        if (isOpen())
          continue;
        reportEndPending(this.mPendingEndException);
        return;
      }
      catch (Exception localException)
      {
        break label58;
      }
    }
  }

  public void setClosedCallback(CompletedCallback paramCompletedCallback)
  {
    this.mClosedHander = paramCompletedCallback;
  }

  public void setDataCallback(DataCallback paramDataCallback)
  {
    this.mDataHandler = paramDataCallback;
  }

  public void setEndCallback(CompletedCallback paramCompletedCallback)
  {
    this.mCompletedCallback = paramCompletedCallback;
  }

  public void setWriteableCallback(WritableCallback paramWritableCallback)
  {
    this.mWriteableHandler = paramWritableCallback;
  }

  void setup(AsyncServer paramAsyncServer, SelectionKey paramSelectionKey)
  {
    this.mServer = paramAsyncServer;
    this.mKey = paramSelectionKey;
  }

  public void write(final ByteBufferList paramByteBufferList)
  {
    if (this.mServer.getAffinity() != Thread.currentThread())
      this.mServer.run(new Runnable()
      {
        public void run()
        {
          AsyncNetworkSocket.this.write(paramByteBufferList);
        }
      });
    do
    {
      return;
      if (this.mChannel.isConnected())
        break;
    }
    while (($assertionsDisabled) || (!this.mChannel.isChunked()));
    throw new AssertionError();
    try
    {
      ByteBuffer[] arrayOfByteBuffer = paramByteBufferList.getAllArray();
      this.mChannel.write(arrayOfByteBuffer);
      paramByteBufferList.addAll(arrayOfByteBuffer);
      handleRemaining(paramByteBufferList.remaining());
      return;
    }
    catch (Exception localException)
    {
      close();
      reportEndPending(localException);
      reportClose(localException);
    }
  }

  public void write(final ByteBuffer paramByteBuffer)
  {
    if (this.mServer.getAffinity() != Thread.currentThread())
      this.mServer.run(new Runnable()
      {
        public void run()
        {
          AsyncNetworkSocket.this.write(paramByteBuffer);
        }
      });
    while (true)
    {
      return;
      try
      {
        if (!this.mChannel.isConnected())
        {
          if (($assertionsDisabled) || (!this.mChannel.isChunked()))
            continue;
          throw new AssertionError();
        }
      }
      catch (Exception localException)
      {
        close();
        reportEndPending(localException);
        reportClose(localException);
        return;
      }
    }
    this.mChannel.write(paramByteBuffer);
    handleRemaining(paramByteBuffer.remaining());
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.async.AsyncNetworkSocket
 * JD-Core Version:    0.6.2
 */