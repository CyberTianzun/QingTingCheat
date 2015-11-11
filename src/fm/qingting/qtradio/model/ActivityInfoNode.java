package fm.qingting.qtradio.model;

import java.util.List;

public class ActivityInfoNode extends Node
{
  public String id = "area";
  private List<Node> mLstActivityNodes;
  public String name = "专区";

  public ActivityInfoNode()
  {
    this.nodeName = "activityinfo";
  }

  public List<Node> getActivityList()
  {
    return this.mLstActivityNodes;
  }

  public void onNodeUpdated(Object paramObject, String paramString)
  {
    if ((paramObject == null) || (paramString == null));
    List localList;
    do
    {
      do
        return;
      while (!paramString.equalsIgnoreCase("AACTL"));
      localList = (List)paramObject;
    }
    while ((localList.size() == 0) && (this.mLstActivityNodes != null));
    this.mLstActivityNodes = localList;
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.qtradio.model.ActivityInfoNode
 * JD-Core Version:    0.6.2
 */