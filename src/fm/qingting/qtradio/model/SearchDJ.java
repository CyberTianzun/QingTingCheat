package fm.qingting.qtradio.model;

public class SearchDJ extends SearchBasic
{
  private String DJname;
  private BroadcasterNode broadcaster = new BroadcasterNode();
  private String cname;
  private String pname;

  public SearchDJ()
  {
  }

  public SearchDJ(SearchDJ paramSearchDJ)
  {
    super(paramSearchDJ);
    this.DJname = paramSearchDJ.DJname;
    this.pname = paramSearchDJ.pname;
  }

  public SearchDJ(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6, String paramString7, String paramString8, String paramString9, String paramString10, BroadcasterNode paramBroadcasterNode)
  {
    super(paramString1, paramString2, paramString3, paramString4, paramString5, paramString6, paramString7, paramString8);
    this.DJname = paramString9;
    this.pname = paramString10;
    this.cname = paramString9;
    this.broadcaster.update(paramBroadcasterNode);
  }

  public BroadcasterNode getBroadcaster()
  {
    return this.broadcaster;
  }

  public String getCName()
  {
    return this.cname;
  }

  public String getDJName()
  {
    return this.DJname;
  }

  public String getPName()
  {
    return this.pname;
  }

  public void setBroadcaster(BroadcasterNode paramBroadcasterNode)
  {
    this.broadcaster.update(paramBroadcasterNode);
  }

  public void setCName(String paramString)
  {
    this.cname = paramString;
  }

  public void setDJame(String paramString)
  {
    this.DJname = paramString;
  }

  public void setPName(String paramString)
  {
    this.pname = paramString;
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.qtradio.model.SearchDJ
 * JD-Core Version:    0.6.2
 */