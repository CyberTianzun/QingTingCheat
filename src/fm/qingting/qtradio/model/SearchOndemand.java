package fm.qingting.qtradio.model;

public class SearchOndemand extends SearchBasic
{
  private String broadcaster = "";
  private String latest_program_title = "";
  private String source = "";
  private String strCount = "";

  public SearchOndemand()
  {
  }

  public SearchOndemand(SearchOndemand paramSearchOndemand)
  {
    super(paramSearchOndemand);
    this.strCount = paramSearchOndemand.strCount;
    this.latest_program_title = paramSearchOndemand.latest_program_title;
    this.source = paramSearchOndemand.source;
    this.broadcaster = paramSearchOndemand.broadcaster;
  }

  public SearchOndemand(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6, String paramString7, String paramString8)
  {
    super(paramString1, paramString2, paramString3, paramString4, paramString5, paramString6, paramString7, paramString8);
  }

  public String getBroadcaster()
  {
    return this.broadcaster;
  }

  public String getCount()
  {
    return this.strCount;
  }

  public String getLastProgramTitle()
  {
    return this.latest_program_title;
  }

  public String getSource()
  {
    return this.source;
  }

  public void setBroadcaster(String paramString)
  {
    this.broadcaster = paramString;
  }

  public void setCount(String paramString)
  {
    this.strCount = this.strCount;
  }

  public void setLastProgramTitle(String paramString)
  {
    this.latest_program_title = paramString;
  }

  public void setSource(String paramString)
  {
    this.source = paramString;
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.qtradio.model.SearchOndemand
 * JD-Core Version:    0.6.2
 */