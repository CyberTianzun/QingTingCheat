package fm.qingting.qtradio.model;

import android.os.Handler;
import android.os.Message;
import fm.qingting.framework.data.DataManager;
import fm.qingting.framework.data.IResultToken;
import fm.qingting.framework.data.Result;
import fm.qingting.qtradio.helper.ChannelHelper;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CategoryNode extends Node
{
  public static final int LIVE_CHANNEL = 5;
  public static final int MUSIC = 523;
  public static final int NEWS = 545;
  public static final int NOVEL = 521;
  public static final int SPECIAL_TOPIC = 2733;
  public int categoryId;
  public int hasChild;
  public transient boolean hasRestore = false;
  public transient boolean hasUpdated = false;
  public String mAttributesPath;
  public transient List<Attributes> mLstAttributes;
  public transient List<Attributes> mLstAttributesToDB;
  public transient List<ChannelNode> mLstChannelNodes;
  public String name;
  public int parentId;
  public int sectionId;
  public int type;

  public CategoryNode()
  {
    this.nodeName = "category";
  }

  public void addChannelNode(ChannelNode paramChannelNode)
  {
    if (paramChannelNode == null)
      return;
    if (this.mLstChannelNodes == null)
      this.mLstChannelNodes = new ArrayList();
    paramChannelNode.parent = this;
    this.mLstChannelNodes.add(paramChannelNode);
  }

  public void forbidUseDB()
  {
    this.type = 2;
  }

  public List<Attributes> getLstAttributes(boolean paramBoolean)
  {
    if ((!InfoManager.getInstance().hasConnectedNetwork()) || (paramBoolean))
      restoreAttributesFromDB();
    return this.mLstAttributes;
  }

  public List<ChannelNode> getLstChannels()
  {
    if (((this.mLstChannelNodes == null) || (this.mLstChannelNodes.size() == 0)) && (!isDownloadCateory()))
      this.mLstChannelNodes = ChannelHelper.getInstance().getLstChannelsByAttrPath(this.categoryId, this.mAttributesPath);
    return this.mLstChannelNodes;
  }

  public List<ChannelNode> getLstLiveChannels(boolean paramBoolean)
  {
    if (((this.mLstChannelNodes == null) || (this.mLstChannelNodes.size() == 0)) && (isLiveCategory()))
      this.mLstChannelNodes = ChannelHelper.getInstance().getLstLiveChannelsByAttrPath(this.categoryId, this.mAttributesPath, paramBoolean);
    return this.mLstChannelNodes;
  }

  public Attribute getSubCategoryNodeByRegion(String paramString)
  {
    if (paramString == null)
      return null;
    if (this.mLstAttributes == null)
      restoreAttributesFromDB();
    int i = 0;
    if (i < this.mLstAttributes.size())
      if (!((Attributes)this.mLstAttributes.get(i)).name.contains("地区"));
    for (Attributes localAttributes = (Attributes)this.mLstAttributes.get(i); ; localAttributes = null)
    {
      if (localAttributes != null)
      {
        List localList = localAttributes.mLstAttribute;
        int j = 0;
        if (localList != null)
          while (true)
          {
            if (j >= localAttributes.mLstAttribute.size())
              break label154;
            if (((Attribute)localAttributes.mLstAttribute.get(j)).name.equalsIgnoreCase(paramString))
            {
              return (Attribute)localAttributes.mLstAttribute.get(j);
              i++;
              break;
            }
            j++;
          }
      }
      label154: return null;
    }
  }

  public boolean isDownloadCateory()
  {
    return this.type == 2;
  }

  public boolean isLiveCategory()
  {
    return (this.type == 0) || (this.type == 3) || (this.type == 4);
  }

  public boolean isLiveContentCategory()
  {
    return this.type == 3;
  }

  public boolean isLiveRegionCategory()
  {
    return this.type == 4;
  }

  public boolean isNews()
  {
    return this.categoryId == 545;
  }

  public boolean isNovel()
  {
    return this.categoryId == 521;
  }

  public boolean isRegionCategory()
  {
    return this.name.startsWith("省");
  }

  public boolean isSpecialTopic()
  {
    return this.categoryId == 2733;
  }

  public boolean isVirtualCategory()
  {
    return this.type == 1;
  }

  public void onNodeUpdated(Object paramObject, Map<String, String> paramMap, String paramString)
  {
    if ((paramObject == null) || (paramMap == null));
    String str;
    List localList;
    do
    {
      do
      {
        do
          return;
        while (!paramString.equalsIgnoreCase("ADD_CATEGORY_ATTR"));
        str = (String)paramMap.get("id");
      }
      while (str == null);
      localList = (List)paramObject;
    }
    while ((localList.size() <= 0) || (Integer.valueOf(str).intValue() != this.categoryId));
    this.mLstAttributes = localList;
    for (int i = 0; i < this.mLstAttributes.size(); i++)
      for (int j = 0; j < ((Attributes)this.mLstAttributes.get(i)).mLstAttribute.size(); j++)
        ((Attribute)((Attributes)this.mLstAttributes.get(i)).mLstAttribute.get(j)).parent = this;
    this.mLstAttributesToDB = localList;
    Message localMessage = new Message();
    localMessage.what = 3;
    localMessage.obj = this;
    InfoManager.getInstance().getDataStoreHandler().sendMessage(localMessage);
  }

  public void partialUpdate(CategoryNode paramCategoryNode)
  {
    if ((paramCategoryNode == null) || (paramCategoryNode.categoryId == this.categoryId))
      return;
    this.categoryId = paramCategoryNode.categoryId;
    this.parentId = paramCategoryNode.parentId;
    this.name = paramCategoryNode.name;
    this.type = paramCategoryNode.type;
    this.hasChild = paramCategoryNode.hasChild;
    this.sectionId = paramCategoryNode.sectionId;
    this.mLstAttributes = paramCategoryNode.mLstAttributes;
    this.mAttributesPath = paramCategoryNode.mAttributesPath;
  }

  public boolean restoreAttributesFromDB()
  {
    boolean bool2;
    if (this.hasRestore)
      bool2 = true;
    String str;
    do
    {
      return bool2;
      this.hasRestore = true;
      if (!isLiveCategory())
        break;
      str = this.mAttributesPath;
      bool2 = false;
    }
    while (str != null);
    HashMap localHashMap = new HashMap();
    localHashMap.put("catid", Integer.valueOf(this.categoryId));
    Result localResult = DataManager.getInstance().getData("GETDB_CATEGORY_ATTRIBUTES", null, localHashMap).getResult();
    boolean bool1 = localResult.getSuccess();
    List localList = null;
    if (bool1)
      localList = (List)localResult.getData();
    if ((localList != null) && (localList.size() > 0))
    {
      this.mLstAttributes = localList;
      for (int i = 0; i < this.mLstAttributes.size(); i++)
        for (int j = 0; j < ((Attributes)this.mLstAttributes.get(i)).mLstAttribute.size(); j++)
          ((Attribute)((Attributes)this.mLstAttributes.get(i)).mLstAttribute.get(j)).parent = this;
    }
    return true;
  }

  public void setLstChannelNodes(List<ChannelNode> paramList)
  {
    if (paramList == null);
    while (true)
    {
      return;
      this.mLstChannelNodes = paramList;
      for (int i = 0; i < this.mLstChannelNodes.size(); i++)
        ((ChannelNode)this.mLstChannelNodes.get(i)).parent = this;
    }
  }

  public boolean updateAttributesToDB()
  {
    if ((this.mLstAttributesToDB == null) || (this.mLstAttributesToDB.size() == 0))
      return false;
    this.hasUpdated = true;
    HashMap localHashMap = new HashMap();
    localHashMap.put("attrs", this.mLstAttributesToDB);
    localHashMap.put("catid", Integer.valueOf(this.categoryId));
    DataManager.getInstance().getData("UPDATEDB_CATEGORY_ATTRIBUTES", null, localHashMap);
    return true;
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.qtradio.model.CategoryNode
 * JD-Core Version:    0.6.2
 */