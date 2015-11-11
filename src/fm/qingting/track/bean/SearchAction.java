package fm.qingting.track.bean;

public class SearchAction extends UserAction
{
  public String keyword;

  public SearchAction(String paramString)
  {
    super(6, "search");
    this.keyword = paramString;
  }

  public String toString()
  {
    return addQuotes(this.name) + "," + addQuotes(this.keyword);
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.track.bean.SearchAction
 * JD-Core Version:    0.6.2
 */