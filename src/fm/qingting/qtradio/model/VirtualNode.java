package fm.qingting.qtradio.model;

import android.os.Handler;
import android.os.Message;
import fm.qingting.framework.data.DataManager;
import fm.qingting.framework.data.IResultToken;
import fm.qingting.framework.data.Result;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class VirtualNode extends Node
{
  private final int FAKE_RADIO = 9999;
  private final int FAKE_RECOMMEND = 0;
  private boolean hasAddLocalCategory = false;
  private boolean hasRestored = false;
  public int id = 0;
  private CategoryNode mLocalCategoryNode;
  private List<CategoryNode> mLocalCategoryNodes;
  private List<CategoryNode> mLstCategoryNodes;
  private List<CategoryNode> mLstCategoryNodesToDB;
  public String name = "所有内容";
  public String type = "virtual";

  public VirtualNode()
  {
    this.nodeName = "virtual";
    init();
  }

  private void init()
  {
  }

  public void addLocalCategory()
  {
    int i = 0;
    String str = InfoManager.getInstance().getCurrentRegion();
    if ((str != null) && (this.mLocalCategoryNodes != null) && (!this.hasAddLocalCategory));
    for (int j = 0; ; j++)
      if (j < this.mLocalCategoryNodes.size())
      {
        if (str.contains(((CategoryNode)this.mLocalCategoryNodes.get(j)).name))
          this.mLocalCategoryNode = ((CategoryNode)this.mLocalCategoryNodes.get(j));
      }
      else
      {
        while (i < this.mLstCategoryNodes.size())
        {
          if (this.mLocalCategoryNode.categoryId == ((CategoryNode)this.mLstCategoryNodes.get(i)).categoryId)
          {
            this.hasAddLocalCategory = true;
            return;
          }
          i++;
        }
        this.mLstCategoryNodes.add(2, this.mLocalCategoryNode);
        if (this.mLstCategoryNodesToDB != null)
          updateVirtualCategory(this.mLstCategoryNodesToDB);
        InfoManager.getInstance().dispatchSubscribeEvent("RECV_LOCAL_CATEGORY");
        this.hasAddLocalCategory = true;
        return;
      }
  }

  public int getCatIdBySecId(int paramInt)
  {
    getLstCategoryNodes();
    if (this.mLstCategoryNodes == null);
    while (true)
    {
      return 0;
      for (int i = 0; i < this.mLstCategoryNodes.size(); i++)
        if (((CategoryNode)this.mLstCategoryNodes.get(i)).sectionId == paramInt)
          return ((CategoryNode)this.mLstCategoryNodes.get(i)).categoryId;
    }
  }

  public CategoryNode getCategoryNode(int paramInt)
  {
    getLstCategoryNodes();
    if (this.mLstCategoryNodes == null)
      return null;
    for (int i = 0; i < this.mLstCategoryNodes.size(); i++)
      if (((CategoryNode)this.mLstCategoryNodes.get(i)).sectionId == paramInt)
        return (CategoryNode)this.mLstCategoryNodes.get(i);
    return null;
  }

  public CategoryNode getLocalCategoryNode()
  {
    return this.mLocalCategoryNode;
  }

  public List<CategoryNode> getLstCategoryNodes()
  {
    if (this.mLstCategoryNodes == null)
      restoreChildFromDB();
    if ((this.mLstCategoryNodes != null) && (this.mLstCategoryNodes.size() > 0) && (((CategoryNode)this.mLstCategoryNodes.get(0)).sectionId != 0))
    {
      CategoryNode localCategoryNode1 = new CategoryNode();
      localCategoryNode1.sectionId = 9999;
      localCategoryNode1.name = "电台";
      this.mLstCategoryNodes.add(0, localCategoryNode1);
      CategoryNode localCategoryNode2 = new CategoryNode();
      localCategoryNode2.sectionId = 0;
      localCategoryNode2.name = "精选";
      this.mLstCategoryNodes.add(0, localCategoryNode2);
    }
    return this.mLstCategoryNodes;
  }

  public int getSecIdByCatId(int paramInt)
  {
    getLstCategoryNodes();
    if (this.mLstCategoryNodes == null);
    while (true)
    {
      return 0;
      for (int i = 0; i < this.mLstCategoryNodes.size(); i++)
        if (((CategoryNode)this.mLstCategoryNodes.get(i)).categoryId == paramInt)
          return ((CategoryNode)this.mLstCategoryNodes.get(i)).sectionId;
    }
  }

  public void onNodeUpdated(Object paramObject, String paramString)
  {
    List localList = (List)paramObject;
    if ((localList == null) || (localList.size() == 0));
    while (!paramString.equalsIgnoreCase("AVCAL"))
      return;
    if (this.mLstCategoryNodes == null)
    {
      this.mLstCategoryNodes = localList;
      Iterator localIterator = this.mLstCategoryNodes.iterator();
      while (localIterator.hasNext())
        ((Node)localIterator.next()).parent = this;
    }
    this.mLstCategoryNodes = localList;
    for (int i = 0; i < this.mLstCategoryNodes.size(); i++)
      ((CategoryNode)this.mLstCategoryNodes.get(i)).parent = this;
    InfoManager.getInstance().getDataStoreHandler().sendEmptyMessage(8);
    InfoManager.getInstance().root().mContentCategory.updateVirtualCatNodes(this.mLstCategoryNodes);
  }

  public boolean restoreChildFromDB()
  {
    int i = 0;
    if (this.hasRestored);
    List localList;
    do
    {
      return false;
      this.hasRestored = true;
      HashMap localHashMap = new HashMap();
      localHashMap.put("parentid", Integer.valueOf(this.id));
      Result localResult = DataManager.getInstance().getData("getdb_category_node", null, localHashMap).getResult();
      boolean bool = localResult.getSuccess();
      localList = null;
      if (bool)
        localList = (List)localResult.getData();
    }
    while ((localList == null) || (localList.size() <= 0));
    this.mLstCategoryNodes = localList;
    while (i < this.mLstCategoryNodes.size())
    {
      ((CategoryNode)this.mLstCategoryNodes.get(i)).parent = this;
      i++;
    }
    return true;
  }

  public void setLstLocalCategory(List<CategoryNode> paramList)
  {
    this.mLocalCategoryNodes = paramList;
    addLocalCategory();
  }

  public void updateVirtualCategory(List<CategoryNode> paramList)
  {
    if ((paramList == null) || (paramList.size() == 0))
      return;
    this.mLstCategoryNodesToDB = paramList;
    if (this.mLocalCategoryNode != null)
      this.mLstCategoryNodesToDB.add(0, this.mLocalCategoryNode);
    Message localMessage = new Message();
    localMessage.what = 1;
    localMessage.obj = this;
    InfoManager.getInstance().getDataStoreHandler().sendMessage(localMessage);
  }

  public void updateVirtualCategoryToDB()
  {
    if ((this.mLstCategoryNodesToDB != null) && (this.mLstCategoryNodesToDB.size() > 0))
    {
      HashMap localHashMap = new HashMap();
      localHashMap.put("nodes", this.mLstCategoryNodesToDB);
      localHashMap.put("parentid", Integer.valueOf(this.id));
      DataManager.getInstance().getData("updatedb_category_node", null, localHashMap);
    }
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.qtradio.model.VirtualNode
 * JD-Core Version:    0.6.2
 */