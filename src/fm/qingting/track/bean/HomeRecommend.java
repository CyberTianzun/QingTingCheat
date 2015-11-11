package fm.qingting.track.bean;

public class HomeRecommend extends UserAction
{
  public int position;

  public HomeRecommend(int paramInt)
  {
    super(6, "home_recommend");
    this.position = paramInt;
  }

  public String toString()
  {
    return addQuotes(this.name) + "," + addQuotes(this.position);
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.track.bean.HomeRecommend
 * JD-Core Version:    0.6.2
 */