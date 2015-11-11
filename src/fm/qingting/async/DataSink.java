package fm.qingting.async;

import fm.qingting.async.callback.CompletedCallback;
import fm.qingting.async.callback.WritableCallback;
import java.nio.ByteBuffer;

public abstract interface DataSink
{
  public abstract void close();

  public abstract void end();

  public abstract CompletedCallback getClosedCallback();

  public abstract AsyncServer getServer();

  public abstract WritableCallback getWriteableCallback();

  public abstract boolean isOpen();

  public abstract void setClosedCallback(CompletedCallback paramCompletedCallback);

  public abstract void setWriteableCallback(WritableCallback paramWritableCallback);

  public abstract void write(ByteBufferList paramByteBufferList);

  public abstract void write(ByteBuffer paramByteBuffer);
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.async.DataSink
 * JD-Core Version:    0.6.2
 */