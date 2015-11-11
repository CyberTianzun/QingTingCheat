package fm.qingting.download.qtradiodownload.network.filedownload.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class DownloadTask
{
  public static final int STATE_CONNECTING = 1;
  public static final int STATE_DOWNLOADING = 2;
  public static final int STATE_FAILED = 5;
  public static final int STATE_FINISHED = 4;
  public static final int STATE_PAUSED = 3;
  public static final int STATE_STARTED = 0;
  private static final String TAG = "DownloadTask";
  private long _id = 0L;
  private HashMap<String, String> connProperties = null;
  private String fileDirectory = null;
  private String fileName = null;
  private boolean isFinished = false;
  private List<Part> parts;
  private int preLength = 0;
  private String resourceUrl = null;
  private int size = -1;
  private int speed;
  private int state = -1;
  private int threadSize = 1;
  private String uuid = null;

  public DownloadTask(String paramString1, String paramString2, String paramString3, int paramInt)
  {
    this.resourceUrl = paramString1;
    this.fileDirectory = paramString2;
    this.fileName = paramString3;
    this.threadSize = paramInt;
    this.parts = new ArrayList();
  }

  public DownloadTask(String paramString1, String paramString2, String paramString3, String paramString4, int paramInt)
  {
    this.uuid = paramString1;
    this.resourceUrl = paramString2;
    this.fileDirectory = paramString3;
    this.fileName = paramString4;
    this.threadSize = paramInt;
    this.parts = new ArrayList();
  }

  public HashMap<String, String> getConnProperties()
  {
    return this.connProperties;
  }

  public int getCurLength()
  {
    Iterator localIterator = this.parts.iterator();
    int i = 0;
    while (localIterator.hasNext())
      i += ((Part)localIterator.next()).getCurLength();
    return i;
  }

  public String getFileDirectory()
  {
    return this.fileDirectory;
  }

  public String getFileName()
  {
    return this.fileName;
  }

  public long getId()
  {
    return this._id;
  }

  public List<Part> getParts()
  {
    return this.parts;
  }

  public int getPreLength()
  {
    return this.preLength;
  }

  public String getResourceUrl()
  {
    return this.resourceUrl;
  }

  public int getSize()
  {
    return this.size;
  }

  public int getSpeed()
  {
    return this.speed;
  }

  public int getState()
  {
    return this.state;
  }

  public int getThreadSize()
  {
    return this.threadSize;
  }

  public String getUUId()
  {
    return this.uuid;
  }

  public boolean isTaskFailed()
  {
    Iterator localIterator = this.parts.iterator();
    boolean bool = false;
    while (localIterator.hasNext())
    {
      Part localPart = (Part)localIterator.next();
      if (localPart.getState() == 5)
        bool = true;
      else if (localPart.getState() != 4)
        return false;
    }
    return bool;
  }

  public boolean isTaskFinished()
  {
    return this.isFinished;
  }

  public void setConnProperty(String paramString1, String paramString2)
  {
    if (this.connProperties == null)
      this.connProperties = new HashMap();
    this.connProperties.put(paramString1, paramString2);
  }

  public void setId(long paramLong)
  {
    this._id = paramLong;
  }

  public void setPreLength(int paramInt)
  {
    this.preLength = paramInt;
  }

  public void setSize(int paramInt)
  {
    this.size = paramInt;
  }

  public void setSpeed(int paramInt)
  {
    this.speed = paramInt;
  }

  public void setState(int paramInt)
  {
    switch (paramInt)
    {
    default:
      return;
    case 0:
    case 1:
    case 2:
    case 3:
      this.state = paramInt;
      return;
    case 4:
    case 5:
    }
    Iterator localIterator = this.parts.iterator();
    while (localIterator.hasNext())
      if (((Part)localIterator.next()).getState() != paramInt)
        return;
    this.state = paramInt;
  }

  public void setTaskFinished(boolean paramBoolean)
  {
    this.isFinished = paramBoolean;
  }

  public void setThreadSize(int paramInt)
  {
    this.threadSize = paramInt;
  }

  public void setUuid(String paramString)
  {
    this.uuid = paramString;
  }

  public String toString()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("DownLoadTask[").append("name:" + this.fileName + "| ").append("size:" + this.size + "| ").append("isfinished:" + this.isFinished + "| ").append("curlen:" + this.preLength + "| ").append("thread:" + this.threadSize + "| ").append("uuid:" + this.uuid + "| ").append("url:" + this.resourceUrl + "").append("]");
    return localStringBuilder.toString();
  }

  public boolean updateTaskFinished()
  {
    try
    {
      this.isFinished = true;
      Iterator localIterator = this.parts.iterator();
      while (localIterator.hasNext())
        if (!((Part)localIterator.next()).isPartFinished())
          this.isFinished = false;
      boolean bool = this.isFinished;
      return bool;
    }
    finally
    {
    }
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.download.qtradiodownload.network.filedownload.model.DownloadTask
 * JD-Core Version:    0.6.2
 */