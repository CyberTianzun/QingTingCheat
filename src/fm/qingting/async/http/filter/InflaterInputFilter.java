package fm.qingting.async.http.filter;

import fm.qingting.async.ByteBufferList;
import fm.qingting.async.DataEmitter;
import fm.qingting.async.FilteredDataEmitter;
import fm.qingting.async.Util;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.zip.Inflater;

public class InflaterInputFilter extends FilteredDataEmitter
{
  private Inflater mInflater;

  static
  {
    if (!InflaterInputFilter.class.desiredAssertionStatus());
    for (boolean bool = true; ; bool = false)
    {
      $assertionsDisabled = bool;
      return;
    }
  }

  public InflaterInputFilter()
  {
    this(new Inflater());
  }

  public InflaterInputFilter(Inflater paramInflater)
  {
    this.mInflater = paramInflater;
  }

  public void onDataAvailable(DataEmitter paramDataEmitter, ByteBufferList paramByteBufferList)
  {
    ByteBufferList localByteBufferList;
    ByteBuffer localByteBuffer1;
    while (true)
    {
      try
      {
        localByteBufferList = new ByteBufferList();
        localByteBuffer1 = ByteBufferList.obtain(2 * paramByteBufferList.remaining());
        if (paramByteBufferList.size() <= 0)
          break;
        ByteBuffer localByteBuffer2 = paramByteBufferList.remove();
        if (!localByteBuffer2.hasRemaining())
          continue;
        int i = localByteBuffer2.remaining();
        this.mInflater.setInput(localByteBuffer2.array(), localByteBuffer2.arrayOffset() + localByteBuffer2.position(), localByteBuffer2.remaining());
        localByteBuffer1.position(this.mInflater.inflate(localByteBuffer1.array(), localByteBuffer1.arrayOffset() + localByteBuffer1.position(), localByteBuffer1.remaining()) + localByteBuffer1.position());
        if (localByteBuffer1.hasRemaining())
          break label187;
        localByteBuffer1.limit(localByteBuffer1.position());
        localByteBuffer1.position(0);
        localByteBufferList.add(localByteBuffer1);
        if ((!$assertionsDisabled) && (i == 0))
          throw new AssertionError();
      }
      catch (Exception localException)
      {
        report(localException);
        return;
      }
      localByteBuffer1 = ByteBufferList.obtain(2 * localByteBuffer1.capacity());
      label187: if (!this.mInflater.needsInput())
        if (!this.mInflater.finished());
    }
    localByteBuffer1.limit(localByteBuffer1.position());
    localByteBuffer1.position(0);
    localByteBufferList.add(localByteBuffer1);
    Util.emitAllData(this, localByteBufferList);
  }

  protected void report(Exception paramException)
  {
    if ((paramException != null) && (this.mInflater.getRemaining() > 0))
      paramException = new IOException("data still remaining in inflater");
    super.report(paramException);
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.async.http.filter.InflaterInputFilter
 * JD-Core Version:    0.6.2
 */