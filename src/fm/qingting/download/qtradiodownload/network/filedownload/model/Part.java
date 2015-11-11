package fm.qingting.download.qtradiodownload.network.filedownload.model;

public class Part
{
  private static final int MAX_ATTEMPTS = 3;
  private int attempts = 0;
  private int begin;
  private int curLength;
  private boolean isFinished = false;
  private boolean lock = false;
  private DownloadTask resource = null;
  private int size;
  private int state;

  public Part(DownloadTask paramDownloadTask, int paramInt1, int paramInt2, int paramInt3)
  {
    this.resource = paramDownloadTask;
    this.begin = paramInt1;
    this.size = paramInt2;
    this.curLength = paramInt3;
  }

  public int getBegin()
  {
    return this.begin;
  }

  public int getCurLength()
  {
    return this.curLength;
  }

  public DownloadTask getResource()
  {
    return this.resource;
  }

  public int getSize()
  {
    return this.size;
  }

  public int getState()
  {
    return this.state;
  }

  public void increaseAttempts()
  {
    this.attempts = (1 + this.attempts);
  }

  public boolean isLock()
  {
    return this.lock;
  }

  public boolean isOverAttempts()
  {
    return this.attempts >= 3;
  }

  public boolean isPartFinished()
  {
    return this.isFinished;
  }

  public void setCurLength(int paramInt)
  {
    this.curLength = paramInt;
  }

  public void setLock(boolean paramBoolean)
  {
    this.lock = paramBoolean;
  }

  public void setPartFinished(boolean paramBoolean)
  {
    this.isFinished = paramBoolean;
  }

  public void setState(int paramInt)
  {
    this.state = paramInt;
    this.resource.setState(paramInt);
  }

  public String toString()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("Part[").append("size:" + this.size + "|").append("(" + this.begin + "-" + (-1 + (this.begin + this.size)) + ")").append("]");
    return localStringBuilder.toString();
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.download.qtradiodownload.network.filedownload.model.Part
 * JD-Core Version:    0.6.2
 */