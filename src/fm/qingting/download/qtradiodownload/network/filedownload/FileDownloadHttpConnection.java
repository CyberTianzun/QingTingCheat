package fm.qingting.download.qtradiodownload.network.filedownload;

import fm.qingting.download.qtradiodownload.network.filedownload.model.DownloadTask;
import fm.qingting.download.qtradiodownload.network.filedownload.model.Part;
import fm.qingting.download.qtradiodownload.network.http.HttpRequest;
import fm.qingting.download.qtradiodownload.network.http.conn.HttpConnection;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;

public class FileDownloadHttpConnection extends HttpConnection
{
  public static final int MAX_BUFFER_SIZE = 4096;
  private static final String TAG = "FileDownloadHttpConnection";
  private int downloadSize = 0;
  private Part part;
  private RandomAccessFile rav = null;

  protected FileDownloadHttpConnection(Part paramPart)
  {
    this(paramPart, null);
  }

  protected FileDownloadHttpConnection(Part paramPart, FileDownloadHttpConnectionListener paramFileDownloadHttpConnectionListener)
  {
    this.part = paramPart;
    this.listener = paramFileDownloadHttpConnectionListener;
    this.request = new HttpRequest(paramPart.getResource().getResourceUrl());
    this.request.setHeaders(paramPart.getResource().getConnProperties());
    if (paramPart.getSize() == -1)
    {
      this.request.setHeader("Range", "bytes=" + (paramPart.getBegin() + paramPart.getCurLength()));
      return;
    }
    this.request.setHeader("Range", "bytes=" + (paramPart.getBegin() + paramPart.getCurLength()) + "-" + "");
  }

  private void save2File(InputStream paramInputStream, RandomAccessFile paramRandomAccessFile)
    throws IOException
  {
    this.downloadSize = 0;
    byte[] arrayOfByte = new byte[4096];
    this.part.setState(2);
    paramRandomAccessFile.seek(this.part.getBegin() + this.part.getCurLength());
    while (this.part.getResource().getState() != 3)
    {
      int i = paramInputStream.read(arrayOfByte);
      if (i != -1)
        try
        {
          paramRandomAccessFile.write(arrayOfByte, 0, i);
          this.downloadSize = (i + this.downloadSize);
          this.part.setCurLength(i + this.part.getCurLength());
          if (this.listener != null)
          {
            int j = 100 * this.part.getCurLength() / this.part.getSize();
            ((FileDownloadHttpConnectionListener)this.listener).onTransProgress(this.part.getResource().getUUId(), j);
          }
        }
        finally
        {
        }
    }
    if (this.part.getResource().getState() == 3);
  }

  public void handleConnectingFail()
  {
    handleConnectionFinished();
  }

  public void handleConnectionFinished()
  {
    try
    {
      if (this.rav != null)
      {
        this.rav.close();
        this.rav = null;
      }
      if (this.part.isPartFinished())
      {
        this.part.setState(4);
        if (this.listener != null)
          ((FileDownloadHttpConnectionListener)this.listener).onTransferred(this.part.getResource().getUUId());
        return;
      }
    }
    catch (IOException localIOException)
    {
      do
      {
        while (true)
          localIOException.printStackTrace();
        if (this.part.getResource().getState() == 3)
        {
          this.part.setState(3);
          return;
        }
        this.part.setState(5);
      }
      while (this.listener == null);
      ((FileDownloadHttpConnectionListener)this.listener).onTransError(this.part.getResource().getUUId());
    }
  }

  public void handlePart()
  {
    if (this.part.getResource().getState() != 3)
    {
      this.part.setPartFinished(true);
      if (this.listener != null)
        ((FileDownloadHttpConnectionListener)this.listener).onTransProgress(this.part.getResource().getUUId(), 100);
    }
  }

  public void handleResponse(InputStream paramInputStream)
    throws IOException
  {
    this.rav = new RandomAccessFile(new File(this.part.getResource().getFileDirectory(), this.part.getResource().getFileName()), "rws");
    save2File(paramInputStream, this.rav);
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.download.qtradiodownload.network.filedownload.FileDownloadHttpConnection
 * JD-Core Version:    0.6.2
 */