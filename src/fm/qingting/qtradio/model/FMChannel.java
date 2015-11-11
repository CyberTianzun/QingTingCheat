package fm.qingting.qtradio.model;

public class FMChannel
{
  public String channelID;
  public String channelName;
  public String city;
  public String freq;

  public FMChannel()
  {
  }

  public FMChannel(String paramString1, String paramString2, String paramString3, String paramString4)
  {
    this.city = paramString1;
    this.channelName = paramString2;
    this.channelID = paramString3;
    this.freq = paramString4;
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.qtradio.model.FMChannel
 * JD-Core Version:    0.6.2
 */