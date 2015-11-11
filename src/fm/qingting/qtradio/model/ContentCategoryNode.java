package fm.qingting.qtradio.model;

import fm.qingting.framework.data.DataManager;
import fm.qingting.framework.data.IResultToken;
import fm.qingting.framework.data.Result;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ContentCategoryNode extends Node
{
  private transient boolean hasRestored = false;
  public LiveNode mLiveNode = new LiveNode();
  private List<CategoryNode> mLstCategoryNodes = new ArrayList();
  public VirtualNode mVirtualNode = new VirtualNode();

  public ContentCategoryNode()
  {
    this.nodeName = "contentcategory";
  }

  public List<CategoryNode> getAllCategoryNodes()
  {
    return this.mLstCategoryNodes;
  }

  public boolean restoreChildFromDB()
  {
    if (this.hasRestored)
      return false;
    this.hasRestored = true;
    Result localResult = DataManager.getInstance().getData("getdb_content_category", null, null).getResult();
    if (localResult.getSuccess());
    for (List localList = (List)localResult.getData(); ; localList = null)
    {
      int i = 0;
      if (localList != null);
      while (true)
      {
        if (i < localList.size())
        {
          if (((Node)localList.get(i)).nodeName.equalsIgnoreCase("live"))
          {
            this.mLiveNode.id = ((LiveNode)localList.get(i)).id;
            this.mLiveNode.restoreChildFromDB();
            this.mLiveNode.connectRadioNode();
          }
        }
        else
          return true;
        if (((Node)localList.get(i)).nodeName.equalsIgnoreCase("virtual"))
        {
          this.mVirtualNode.id = ((VirtualNode)localList.get(i)).id;
          this.mVirtualNode.restoreChildFromDB();
        }
        i++;
      }
    }
  }

  public boolean saveChildToDB()
  {
    ArrayList localArrayList = new ArrayList();
    localArrayList.add(this.mLiveNode);
    localArrayList.add(this.mVirtualNode);
    HashMap localHashMap = new HashMap();
    localHashMap.put("nodes", localArrayList);
    DataManager.getInstance().getData("insertdb_content_category", null, localHashMap);
    for (int i = 0; i < localArrayList.size(); i++)
      ((Node)localArrayList.get(i)).saveChildToDB();
    return true;
  }

  public void updateVirtualCatNodes(List<CategoryNode> paramList)
  {
    this.mLstCategoryNodes.clear();
    if (this.mLiveNode.getRadioCategoryNode() != null)
      this.mLstCategoryNodes.add(this.mLiveNode.getRadioCategoryNode());
    for (int i = 0; i < paramList.size(); i++)
      this.mLstCategoryNodes.add((CategoryNode)paramList.get(i));
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.qtradio.model.ContentCategoryNode
 * JD-Core Version:    0.6.2
 */