package fm.qingting.qtradio.model;

import java.util.HashMap;
import java.util.Map;

public class LinkInfoNode extends Node
{
  private Map<Integer, RecommendItemNode> mapLinkInfo = new HashMap();
  private Map<Integer, Boolean> mapLoaded = new HashMap();

  public LinkInfoNode()
  {
    this.nodeName = "linkinfo";
  }

  public RecommendItemNode getLinkInfo(int paramInt)
  {
    return (RecommendItemNode)this.mapLinkInfo.get(Integer.valueOf(paramInt));
  }

  public boolean hasLoaded(int paramInt)
  {
    return this.mapLoaded.containsKey(Integer.valueOf(paramInt));
  }

  public boolean isExist(String paramString)
  {
    if (paramString == null)
      return false;
    return this.mapLinkInfo.containsKey(paramString);
  }

  public void onNodeUpdated(Object paramObject, String paramString)
  {
    Node localNode = (Node)paramObject;
    if ((localNode == null) || (paramString == null));
    String str;
    do
    {
      do
        return;
      while (!paramString.equalsIgnoreCase("ALI"));
      str = ((RecommendItemNode)localNode).id;
    }
    while ((str == null) || (str.equalsIgnoreCase("")));
    this.mapLinkInfo.put(Integer.valueOf(str), (RecommendItemNode)localNode);
  }

  public void setLinkInfo(int paramInt, RecommendItemNode paramRecommendItemNode)
  {
    if (paramRecommendItemNode == null)
      return;
    this.mapLinkInfo.put(Integer.valueOf(paramInt), paramRecommendItemNode);
  }

  public void setLoaded(int paramInt)
  {
    this.mapLoaded.put(Integer.valueOf(paramInt), Boolean.valueOf(true));
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.qtradio.model.LinkInfoNode
 * JD-Core Version:    0.6.2
 */