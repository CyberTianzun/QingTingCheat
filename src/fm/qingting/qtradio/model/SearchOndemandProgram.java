package fm.qingting.qtradio.model;

public class SearchOndemandProgram extends SearchBasic
{
  private String cname;
  private String pid;

  public SearchOndemandProgram()
  {
  }

  public SearchOndemandProgram(SearchOndemandProgram paramSearchOndemandProgram)
  {
    super(paramSearchOndemandProgram);
    this.cname = paramSearchOndemandProgram.cname;
    this.pid = paramSearchOndemandProgram.pid;
  }

  public SearchOndemandProgram(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6, String paramString7, String paramString8, String paramString9, String paramString10)
  {
    super(paramString1, paramString2, paramString3, paramString4, paramString5, paramString6, paramString7, paramString10);
    this.cname = paramString8;
    this.pid = paramString9;
  }

  public String getCName()
  {
    return this.cname;
  }

  public String getPid()
  {
    return this.pid;
  }

  public void setCName(String paramString)
  {
    this.cname = paramString;
  }

  public void setPid(String paramString)
  {
    this.pid = paramString;
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.qtradio.model.SearchOndemandProgram
 * JD-Core Version:    0.6.2
 */