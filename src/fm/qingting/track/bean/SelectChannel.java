package fm.qingting.track.bean;

public class SelectChannel extends UserAction
{
  public String channelName;
  public int id;

  public SelectChannel(int paramInt, String paramString)
  {
    super(2, "select_channel");
    this.id = paramInt;
    this.channelName = paramString;
  }

  public String toString()
  {
    return addQuotes(this.name) + "," + addQuotes(new StringBuilder().append(this.channelName).append("---").append(this.id).toString());
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.track.bean.SelectChannel
 * JD-Core Version:    0.6.2
 */