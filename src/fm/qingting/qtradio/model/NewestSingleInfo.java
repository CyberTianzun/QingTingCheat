package fm.qingting.qtradio.model;

import java.util.List;

public class NewestSingleInfo
{
  private List<SingleItem> singleList;
  private String title;

  public List<SingleItem> getSingleList()
  {
    return this.singleList;
  }

  public String getTitle()
  {
    return this.title;
  }

  public void setSingleList(List<SingleItem> paramList)
  {
    this.singleList = paramList;
  }

  public void setTitle(String paramString)
  {
    this.title = paramString;
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.qtradio.model.NewestSingleInfo
 * JD-Core Version:    0.6.2
 */