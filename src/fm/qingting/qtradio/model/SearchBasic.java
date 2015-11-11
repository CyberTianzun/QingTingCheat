package fm.qingting.qtradio.model;

public class SearchBasic
{
  private String categoryType;
  private String catid;
  private String cid;
  public int count = 0;
  private String id;
  private String name;
  private String rank;
  public String searchType = "basic";
  public double totalScore = 0.0D;
  private String type;
  private String typeName;

  public SearchBasic()
  {
  }

  public SearchBasic(SearchBasic paramSearchBasic)
  {
    this.id = paramSearchBasic.id;
    this.cid = paramSearchBasic.cid;
    this.name = paramSearchBasic.name;
    this.type = paramSearchBasic.type;
    this.typeName = paramSearchBasic.typeName;
    this.rank = paramSearchBasic.rank;
    this.catid = paramSearchBasic.catid;
    this.categoryType = paramSearchBasic.categoryType;
  }

  public SearchBasic(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6, String paramString7)
  {
    this.id = paramString1;
    this.cid = paramString2;
    this.name = paramString3;
    this.type = paramString4;
    this.typeName = paramString5;
    this.rank = paramString6;
    this.catid = "";
    this.categoryType = paramString7;
  }

  public SearchBasic(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6, String paramString7, String paramString8)
  {
    this.id = paramString1;
    this.cid = paramString2;
    this.name = paramString3;
    this.type = paramString4;
    this.typeName = paramString5;
    this.rank = paramString6;
    this.catid = paramString8;
    this.categoryType = paramString7;
  }

  public String getCategoryType()
  {
    return this.categoryType;
  }

  public String getCatid()
  {
    return this.catid;
  }

  public String getCid()
  {
    return this.cid;
  }

  public String getId()
  {
    return this.id;
  }

  public String getName()
  {
    return this.name;
  }

  public String getRank()
  {
    return this.rank;
  }

  public String getType()
  {
    return this.type;
  }

  public String getTypeName()
  {
    return this.typeName;
  }

  public void setCategoryType(String paramString)
  {
    this.categoryType = paramString;
  }

  public void setCatid(String paramString)
  {
    this.catid = paramString;
  }

  public void setCid(String paramString)
  {
    this.cid = paramString;
  }

  public void setId(String paramString)
  {
    this.id = paramString;
  }

  public void setName(String paramString)
  {
    this.name = paramString;
  }

  public void setRank(String paramString)
  {
    this.rank = paramString;
  }

  public void setType(String paramString)
  {
    this.type = paramString;
  }

  public void setTypeName(String paramString)
  {
    this.typeName = paramString;
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.qtradio.model.SearchBasic
 * JD-Core Version:    0.6.2
 */