package fm.qingting.qtradio.model;

import android.os.Handler;
import android.os.Message;
import fm.qingting.framework.data.DataManager;
import fm.qingting.framework.data.IResultToken;
import fm.qingting.framework.data.Result;
import fm.qingting.qtradio.fmdriver.FMManager;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LiveNode extends Node
{
  private transient boolean hasRestored = false;
  private transient boolean hasRestoredLiveCategory = false;
  public int id = 1;
  private transient Node mLocalAttrNode;
  private List<CategoryNode> mLstContentCategoryNodes = new ArrayList();
  private List<CategoryNode> mLstLiveCategoryNodes;
  private List<CategoryNode> mLstLiveCategoryNodesToDB;
  private List<CategoryNode> mLstRegionCategoryNodes = new ArrayList();
  private CategoryNode mRadioCategoryNode;
  public transient RadioNode mRadioNode;
  public String name = "广播电台";
  public String type = "channel";

  public LiveNode()
  {
    this.nodeName = "live";
    init();
  }

  private void buildCategory()
  {
    if ((this.mLstContentCategoryNodes.size() > 0) && (this.mLstRegionCategoryNodes.size() > 0));
    List localList;
    do
    {
      return;
      localList = getLiveCategoryNodes();
    }
    while (localList == null);
    int i = 0;
    label36: if (i < localList.size())
    {
      if (!((CategoryNode)localList.get(i)).isLiveContentCategory())
        break label85;
      this.mLstContentCategoryNodes.add(localList.get(i));
    }
    while (true)
    {
      i++;
      break label36;
      break;
      label85: if (((CategoryNode)localList.get(i)).isLiveRegionCategory())
        this.mLstRegionCategoryNodes.add(localList.get(i));
    }
  }

  private void init()
  {
    if (FMManager.getInstance().isAvailable())
      this.mRadioNode = new RadioNode();
    this.mRadioCategoryNode = new CategoryNode();
    this.mRadioCategoryNode.parentId = this.id;
    this.mRadioCategoryNode.categoryId = 5;
    this.mRadioCategoryNode.name = "直播电台";
    this.mRadioCategoryNode.type = 0;
    this.mRadioCategoryNode.hasChild = 1;
    this.mRadioCategoryNode.parent = this;
  }

  public void connectRadioNode()
  {
    if (this.mRadioNode != null)
    {
      this.mRadioNode.parent = this;
      this.mRadioNode.restoreFromDB();
      QTLocation localQTLocation = InfoManager.getInstance().getCurrentLocation();
      if ((localQTLocation != null) && (localQTLocation.city != null))
        this.mRadioNode.restoreFromDBByCity(localQTLocation.city);
    }
  }

  public CategoryNode getCategoryNode(int paramInt)
  {
    if (this.mLstRegionCategoryNodes == null)
      getRegionCategory();
    if (this.mLstRegionCategoryNodes != null)
      for (int j = 0; j < this.mLstRegionCategoryNodes.size(); j++)
        if (((CategoryNode)this.mLstRegionCategoryNodes.get(j)).categoryId == paramInt)
          return (CategoryNode)this.mLstRegionCategoryNodes.get(j);
    if (this.mLstLiveCategoryNodes == null)
      getLiveCategoryNodes();
    List localList = this.mLstLiveCategoryNodes;
    int i = 0;
    if (localList != null)
      while (i < this.mLstLiveCategoryNodes.size())
      {
        if (((CategoryNode)this.mLstLiveCategoryNodes.get(i)).categoryId == paramInt)
          return (CategoryNode)this.mLstLiveCategoryNodes.get(i);
        i++;
      }
    return null;
  }

  public List<Attribute> getContentAttribute()
  {
    List localList = getLstAttributes();
    if (localList == null)
      return null;
    for (int i = 0; i < localList.size(); i++)
      if (((Attributes)localList.get(i)).id == 91)
        return ((Attributes)localList.get(i)).mLstAttribute;
    return null;
  }

  public List<CategoryNode> getContentCategory()
  {
    if (this.mLstContentCategoryNodes.size() > 0)
      return this.mLstContentCategoryNodes;
    buildCategory();
    return this.mLstContentCategoryNodes;
  }

  public List<CategoryNode> getLiveCategoryNodes()
  {
    if (this.mLstLiveCategoryNodes == null)
      restoreLiveCategory();
    return this.mLstLiveCategoryNodes;
  }

  public Node getLocalCategoryNode()
  {
    if (this.mLocalAttrNode != null)
      return this.mLocalAttrNode;
    QTLocation localQTLocation = InfoManager.getInstance().getCurrentLocation();
    if (localQTLocation != null)
      setRegion(localQTLocation.region);
    return this.mLocalAttrNode;
  }

  public List<Attributes> getLstAttributes()
  {
    if (this.mRadioCategoryNode != null)
      return this.mRadioCategoryNode.getLstAttributes(true);
    return null;
  }

  public List<ChannelNode> getLstChannelByRegionAndContent(CategoryNode paramCategoryNode, Attribute paramAttribute)
  {
    if ((paramCategoryNode != null) && (paramAttribute == null))
      return paramCategoryNode.getLstChannels();
    if ((paramCategoryNode == null) && (paramAttribute != null))
      return paramAttribute.getLstChannels();
    if ((paramCategoryNode != null) && (paramAttribute != null))
    {
      List localList1 = paramAttribute.getLstLiveChannels(true);
      List localList2 = paramCategoryNode.getLstLiveChannels(true);
      if ((localList1 != null) && (localList2 != null))
      {
        ArrayList localArrayList = new ArrayList();
        int i = 0;
        if (i < localList1.size())
          for (int j = 0; ; j++)
            if (j < localList2.size())
            {
              if (((ChannelNode)localList1.get(i)).channelId == ((ChannelNode)localList2.get(j)).channelId)
                localArrayList.add(localList1.get(i));
            }
            else
            {
              i++;
              break;
            }
        if (localArrayList.size() > 0)
          return localArrayList;
      }
    }
    return null;
  }

  public List<ChannelNode> getLstChannelByRegionAndContent(CategoryNode paramCategoryNode1, CategoryNode paramCategoryNode2)
  {
    if ((paramCategoryNode1 != null) && (paramCategoryNode2 == null))
      return paramCategoryNode1.getLstChannels();
    if ((paramCategoryNode1 == null) && (paramCategoryNode2 != null))
      return paramCategoryNode2.getLstChannels();
    if ((paramCategoryNode1 != null) && (paramCategoryNode2 != null))
    {
      List localList1 = paramCategoryNode2.getLstLiveChannels(true);
      List localList2 = paramCategoryNode1.getLstLiveChannels(true);
      if ((localList1 != null) && (localList2 != null))
      {
        ArrayList localArrayList = new ArrayList();
        int i = 0;
        if (i < localList1.size())
          for (int j = 0; ; j++)
            if (j < localList2.size())
            {
              if (((ChannelNode)localList1.get(i)).channelId == ((ChannelNode)localList2.get(j)).channelId)
                localArrayList.add(localList1.get(i));
            }
            else
            {
              i++;
              break;
            }
        if (localArrayList.size() > 0)
          return localArrayList;
      }
    }
    return null;
  }

  public CategoryNode getRadioCategoryNode()
  {
    return this.mRadioCategoryNode;
  }

  public List<Attribute> getRegionAttribute()
  {
    List localList = getLstAttributes();
    if (localList == null)
      return null;
    for (int i = 0; i < localList.size(); i++)
      if (((Attributes)localList.get(i)).id == 20)
        return ((Attributes)localList.get(i)).mLstAttribute;
    return null;
  }

  public List<CategoryNode> getRegionCategory()
  {
    if (this.mLstRegionCategoryNodes.size() > 0)
      return this.mLstRegionCategoryNodes;
    buildCategory();
    return this.mLstRegionCategoryNodes;
  }

  public boolean isRadioCategoryNode(CategoryNode paramCategoryNode)
  {
    if (paramCategoryNode == null);
    while (paramCategoryNode.categoryId != this.mRadioCategoryNode.categoryId)
      return false;
    return true;
  }

  public boolean restoreChildFromDB()
  {
    if (this.hasRestored)
      return false;
    this.hasRestored = true;
    HashMap localHashMap = new HashMap();
    localHashMap.put("parentid", Integer.valueOf(this.id));
    Result localResult = DataManager.getInstance().getData("getdb_category_node", null, localHashMap).getResult();
    boolean bool = localResult.getSuccess();
    List localList = null;
    if (bool)
      localList = (List)localResult.getData();
    return (localList != null) && (localList.size() > 0);
  }

  public boolean restoreLiveCategory()
  {
    if (this.hasRestoredLiveCategory)
      return false;
    this.hasRestoredLiveCategory = true;
    HashMap localHashMap = new HashMap();
    localHashMap.put("parentid", Integer.valueOf(this.id));
    Result localResult = DataManager.getInstance().getData("getdb_category_node", null, localHashMap).getResult();
    boolean bool = localResult.getSuccess();
    List localList = null;
    if (bool)
      localList = (List)localResult.getData();
    if ((localList != null) && (localList.size() > 0))
    {
      this.mLstLiveCategoryNodes = localList;
      return true;
    }
    this.mLstLiveCategoryNodes = new ArrayList();
    return false;
  }

  public boolean saveChildToDB()
  {
    return false;
  }

  public void setRegion(String paramString)
  {
    if (paramString == null);
    while (true)
    {
      return;
      List localList = getRegionAttribute();
      if (localList != null)
        for (int i = 0; i < localList.size(); i++)
          if (((Attribute)localList.get(i)).name.contains(paramString))
            this.mLocalAttrNode = ((Node)localList.get(i));
    }
  }

  public void updateLiveCategory(List<CategoryNode> paramList)
  {
    if (paramList == null)
      return;
    for (int i = 0; i < paramList.size(); i++)
      ((CategoryNode)paramList.get(i)).parentId = this.id;
    if ((this.mLstLiveCategoryNodes == null) || (this.mLstLiveCategoryNodes.size() == 0))
      this.mLstLiveCategoryNodes = paramList;
    this.mLstLiveCategoryNodesToDB = paramList;
    Message localMessage = new Message();
    localMessage.what = 8;
    localMessage.obj = this;
    InfoManager.getInstance().getDataStoreHandler().sendMessage(localMessage);
  }

  public void updateLiveCategoryToDB()
  {
    if (this.mLstLiveCategoryNodesToDB != null)
    {
      HashMap localHashMap = new HashMap();
      localHashMap.put("nodes", this.mLstLiveCategoryNodesToDB);
      localHashMap.put("parentid", Integer.valueOf(this.id));
      DataManager.getInstance().getData("updatedb_category_node", null, localHashMap);
    }
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.qtradio.model.LiveNode
 * JD-Core Version:    0.6.2
 */