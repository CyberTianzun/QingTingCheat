package fm.qingting.track;

public class PlayLogBean
{
  private String content;
  private long id;

  public PlayLogBean(long paramLong, String paramString)
  {
    this.id = paramLong;
    this.content = paramString;
  }

  public String getContent()
  {
    return this.content;
  }

  public long getId()
  {
    return this.id;
  }

  public void setContent(String paramString)
  {
    this.content = paramString;
  }

  public void setId(int paramInt)
  {
    this.id = paramInt;
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.track.PlayLogBean
 * JD-Core Version:    0.6.2
 */