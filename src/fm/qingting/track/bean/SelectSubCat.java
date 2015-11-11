package fm.qingting.track.bean;

public class SelectSubCat extends UserAction
{
  public String catName;
  public String id;

  public SelectSubCat(String paramString1, String paramString2)
  {
    super(4, "in_sub_category");
    this.id = paramString1;
    this.catName = paramString2;
  }

  public String toString()
  {
    return addQuotes(this.name) + "," + addQuotes(new StringBuilder().append(this.catName).append("---").append(this.id).toString());
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.track.bean.SelectSubCat
 * JD-Core Version:    0.6.2
 */