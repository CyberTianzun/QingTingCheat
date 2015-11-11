package fm.qingting.track.bean;

public class SelectCategory extends UserAction
{
  public String catName;
  public int id;

  public SelectCategory(int paramInt, String paramString)
  {
    super(5, "in_category");
    this.id = paramInt;
    this.catName = paramString;
  }

  public String toString()
  {
    return addQuotes(this.name) + "," + addQuotes(new StringBuilder().append(this.catName).append("---").append(this.id).toString());
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.track.bean.SelectCategory
 * JD-Core Version:    0.6.2
 */