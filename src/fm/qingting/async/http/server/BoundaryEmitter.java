package fm.qingting.async.http.server;

import fm.qingting.async.ByteBufferList;
import fm.qingting.async.DataEmitter;
import fm.qingting.async.FilteredDataEmitter;
import java.nio.ByteBuffer;

public class BoundaryEmitter extends FilteredDataEmitter
{
  private byte[] boundary;
  int state = 2;

  static
  {
    if (!BoundaryEmitter.class.desiredAssertionStatus());
    for (boolean bool = true; ; bool = false)
    {
      $assertionsDisabled = bool;
      return;
    }
  }

  public String getBoundary()
  {
    if (this.boundary == null)
      return null;
    return new String(this.boundary, 4, -4 + this.boundary.length);
  }

  public String getBoundaryEnd()
  {
    assert (this.boundary != null);
    return getBoundaryStart() + "--\r\n";
  }

  public String getBoundaryStart()
  {
    assert (this.boundary != null);
    return new String(this.boundary, 2, -2 + this.boundary.length);
  }

  protected void onBoundaryEnd()
  {
  }

  protected void onBoundaryStart()
  {
  }

  public void onDataAvailable(DataEmitter paramDataEmitter, ByteBufferList paramByteBufferList)
  {
    if (this.state > 0)
    {
      paramByteBufferList.add(0, ByteBuffer.wrap(this.boundary, 0, this.state).duplicate());
      this.state = 0;
    }
    byte[] arrayOfByte = new byte[paramByteBufferList.remaining()];
    paramByteBufferList.get(arrayOfByte);
    int i = 0;
    int j = 0;
    if (i < arrayOfByte.length)
    {
      if (this.state >= 0)
        if (arrayOfByte[i] == this.boundary[this.state])
        {
          this.state = (1 + this.state);
          if (this.state == this.boundary.length)
            this.state = -1;
        }
      while (true)
      {
        i++;
        break;
        if (this.state > 0)
        {
          i -= this.state;
          this.state = 0;
          continue;
          if (this.state != -1)
            break label255;
          if (arrayOfByte[i] == 13)
          {
            this.state = -4;
            m = i - j - this.boundary.length;
            if ((j != 0) || (m != 0))
            {
              localByteBuffer3 = ByteBuffer.wrap(arrayOfByte, j, m);
              localByteBufferList3 = new ByteBufferList();
              localByteBufferList3.add(localByteBuffer3);
              super.onDataAvailable(this, localByteBufferList3);
            }
            onBoundaryStart();
          }
          else
          {
            if (arrayOfByte[i] != 45)
              break label241;
            this.state = -2;
          }
        }
      }
      label241: report(new Exception("Invalid multipart/form-data. Expected \r or -"));
    }
    label255: 
    while (j >= arrayOfByte.length)
      while (true)
      {
        int m;
        ByteBuffer localByteBuffer3;
        ByteBufferList localByteBufferList3;
        return;
        if (this.state == -2)
        {
          if (arrayOfByte[i] == 45)
            this.state = -3;
          else
            report(new Exception("Invalid multipart/form-data. Expected -"));
        }
        else if (this.state == -3)
        {
          if (arrayOfByte[i] == 13)
          {
            this.state = -4;
            ByteBuffer localByteBuffer2 = ByteBuffer.wrap(arrayOfByte, j, -2 + (i - j - this.boundary.length));
            ByteBufferList localByteBufferList2 = new ByteBufferList();
            localByteBufferList2.add(localByteBuffer2);
            super.onDataAvailable(this, localByteBufferList2);
            onBoundaryEnd();
          }
          else
          {
            report(new Exception("Invalid multipart/form-data. Expected \r"));
          }
        }
        else if (this.state == -4)
        {
          if (arrayOfByte[i] == 10)
          {
            j = i + 1;
            this.state = 0;
          }
          else
          {
            report(new Exception("Invalid multipart/form-data. Expected \n"));
          }
        }
        else
        {
          if (!$assertionsDisabled)
            throw new AssertionError();
          report(new Exception("Invalid multipart/form-data. Unknown state?"));
        }
      }
    int k = Math.max(this.state, 0);
    ByteBuffer localByteBuffer1 = ByteBuffer.wrap(arrayOfByte, j, arrayOfByte.length - j - k);
    ByteBufferList localByteBufferList1 = new ByteBufferList();
    localByteBufferList1.add(localByteBuffer1);
    super.onDataAvailable(this, localByteBufferList1);
  }

  public void setBoundary(String paramString)
  {
    this.boundary = ("\r\n--" + paramString).getBytes();
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.async.http.server.BoundaryEmitter
 * JD-Core Version:    0.6.2
 */