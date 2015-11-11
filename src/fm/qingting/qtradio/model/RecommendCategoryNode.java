package fm.qingting.qtradio.model;

import fm.qingting.framework.data.DataManager;
import fm.qingting.framework.data.IResultToken;
import fm.qingting.framework.data.Result;
import fm.qingting.qtradio.ad.AdPos;
import fm.qingting.utils.ThirdAdv;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RecommendCategoryNode extends Node
{
  private transient boolean hasInsertedBannerAdvertisement = false;
  private transient boolean hasLoadAudioAdvertisement = false;
  private transient boolean hasRestored = false;
  private transient boolean hasRestoredSucc = false;
  public transient boolean hasUpdate = false;
  private List<RecommendItemNode> lstBanner = new ArrayList();
  public List<List<RecommendItemNode>> lstRecMain = new ArrayList();
  public String name = "";
  public int sectionId = -1;

  public RecommendCategoryNode()
  {
    this.nodeName = "recommendcategory";
  }

  private void log(String paramString)
  {
  }

  public void KeepNewRecommendItem(RecommendItemNode paramRecommendItemNode, int paramInt)
  {
    if (paramInt == 1)
    {
      if (this.lstRecMain.size() > 0)
      {
        this.lstRecMain.clear();
        paramRecommendItemNode.parent = this;
        ((List)this.lstRecMain.get(0)).add(paramRecommendItemNode);
        return;
      }
      ArrayList localArrayList = new ArrayList();
      paramRecommendItemNode.parent = this;
      localArrayList.add(paramRecommendItemNode);
      this.lstRecMain.add(localArrayList);
      return;
    }
    if (this.lstBanner.size() > 0)
    {
      this.lstBanner.clear();
      paramRecommendItemNode.parent = this;
      this.lstBanner.add(paramRecommendItemNode);
      return;
    }
    paramRecommendItemNode.parent = this;
    this.lstBanner.add(paramRecommendItemNode);
  }

  public List<RecommendItemNode> getLstBanner()
  {
    insertBannerAdvertisement();
    loadAudioAdvertisement();
    int i = InfoManager.getInstance().root().getCatIdBySecId(this.sectionId);
    insertAdvFromThirdParty(ThirdAdv.getInstance().getRecommendNodes(i));
    return this.lstBanner;
  }

  public void insertAdvFromThirdParty(RecommendItemNode paramRecommendItemNode)
  {
    if (paramRecommendItemNode == null);
    while ((this.lstBanner == null) || (this.lstBanner.size() <= 0) || (this.lstBanner.get(-1 + this.lstBanner.size()) == paramRecommendItemNode))
      return;
    for (int i = 0; (i < this.lstBanner.size()) && (i >= 0); i++)
      if (this.lstBanner.get(i) == paramRecommendItemNode)
      {
        this.lstBanner.remove(i);
        i--;
      }
    this.lstBanner.add(paramRecommendItemNode);
  }

  public boolean insertBannerAdvertisement()
  {
    int j;
    int m;
    int k;
    label71: AdPos localAdPos;
    RecommendItemNode localRecommendItemNode;
    int i1;
    label152: int i2;
    label228: int n;
    if ((this.lstBanner != null) && (this.lstBanner.size() > 0) && (!this.hasInsertedBannerAdvertisement))
    {
      int i = this.lstBanner.size();
      if (isFrontpage())
      {
        j = 0;
        if (ThirdAdv.getInstance().getRecommendNodes(j) == null)
          InfoManager.getInstance().loadAdvFromThirdParty(j);
        if (j < 0)
          break label357;
        m = 0;
        k = 0;
        if (m > i)
          break label359;
        localAdPos = InfoManager.getInstance().root().mAdvertisementInfoNode.getBannerAdPos(j, m);
        if (localAdPos == null)
          break label378;
        localAdPos.parent = this;
        AdvertisementItemNode localAdvertisementItemNode = InfoManager.getInstance().root().mAdvertisementInfoNode.getAdvertisement(localAdPos.posid);
        if (localAdvertisementItemNode == null)
          break label324;
        localRecommendItemNode = localAdvertisementItemNode.convertToRecommendItem(j);
        if (localRecommendItemNode != null)
        {
          String str1 = localRecommendItemNode.getApproximativeThumb();
          i1 = 0;
          if (i1 >= this.lstBanner.size())
            break label372;
          String str2 = ((RecommendItemNode)this.lstBanner.get(i1)).getApproximativeThumb();
          if ((!((RecommendItemNode)this.lstBanner.get(i1)).isAds) || (str2 == null) || (str1 == null) || (!str2.equalsIgnoreCase(str1)))
            break label303;
          i2 = 1;
          if (i2 == 0)
          {
            if ((localAdPos.bannerPos >= this.lstBanner.size()) || (localAdPos.bannerPos < 0))
              break label309;
            this.lstBanner.add(localAdPos.bannerPos, localRecommendItemNode);
          }
        }
        label274: n = k;
      }
    }
    while (true)
    {
      m++;
      k = n;
      break label71;
      j = InfoManager.getInstance().root().getCatIdBySecId(this.sectionId);
      break;
      label303: i1++;
      break label152;
      label309: this.lstBanner.add(localRecommendItemNode);
      break label274;
      label324: InfoManager.getInstance().loadAdvertisement(localAdPos, -1, null);
      if (ThirdAdv.getInstance().getRecommendNodes(j) == null)
        InfoManager.getInstance().loadAdvFromThirdParty(j);
      n = 1;
      continue;
      label357: k = 0;
      label359: if (k == 0)
      {
        this.hasInsertedBannerAdvertisement = true;
        return true;
      }
      return false;
      label372: i2 = 0;
      break label228;
      label378: n = k;
    }
  }

  public void insertRecCategory(RecommendItemNode paramRecommendItemNode, int paramInt)
  {
    int i = 0;
    if (paramInt == 1)
    {
      paramRecommendItemNode.categoryPos = 1;
      for (int j = 0; j < this.lstRecMain.size(); j++)
        if (((RecommendItemNode)((List)this.lstRecMain.get(j)).get(0)).sectionId == paramRecommendItemNode.sectionId)
        {
          paramRecommendItemNode.parent = this;
          ((List)this.lstRecMain.get(j)).add(paramRecommendItemNode);
          return;
        }
      ArrayList localArrayList = new ArrayList();
      paramRecommendItemNode.parent = this;
      localArrayList.add(paramRecommendItemNode);
      this.lstRecMain.add(localArrayList);
      return;
    }
    paramRecommendItemNode.categoryPos = 0;
    while (i < this.lstBanner.size())
    {
      if (((RecommendItemNode)this.lstBanner.get(i)).sectionId == paramRecommendItemNode.sectionId)
      {
        paramRecommendItemNode.parent = this;
        this.lstBanner.add(paramRecommendItemNode);
        return;
      }
      i++;
    }
    paramRecommendItemNode.parent = this;
    this.lstBanner.add(paramRecommendItemNode);
  }

  public boolean isFrontpage()
  {
    return this.sectionId == 0;
  }

  public void loadAudioAdvertisement()
  {
    if ((this.hasLoadAudioAdvertisement) || (isFrontpage()));
    int j;
    do
    {
      return;
      int i = InfoManager.getInstance().root().getCatIdBySecId(this.sectionId);
      j = 0;
      if (i > 0)
      {
        AdPos localAdPos = InfoManager.getInstance().root().mAdvertisementInfoNode.getAudioAdPos(i, 4);
        j = 0;
        if (localAdPos != null)
        {
          AdvertisementItemNode localAdvertisementItemNode = InfoManager.getInstance().root().mAdvertisementInfoNode.getAdvertisement(localAdPos.posid);
          j = 0;
          if (localAdvertisementItemNode == null)
          {
            InfoManager.getInstance().loadAdvertisement(localAdPos, -1, null);
            j = 1;
          }
        }
      }
    }
    while (j != 0);
    this.hasLoadAudioAdvertisement = true;
  }

  public boolean restoreFromDB()
  {
    if (this.hasRestored)
      return this.hasRestoredSucc;
    this.hasRestored = true;
    HashMap localHashMap = new HashMap();
    localHashMap.put("id", Integer.valueOf(this.sectionId));
    Result localResult = DataManager.getInstance().getData("getdb_reccategory_node", null, localHashMap).getResult();
    boolean bool = localResult.getSuccess();
    RecommendCategoryNode localRecommendCategoryNode = null;
    if (bool)
      localRecommendCategoryNode = (RecommendCategoryNode)localResult.getData();
    if (localRecommendCategoryNode != null)
    {
      this.lstBanner = localRecommendCategoryNode.lstBanner;
      this.lstRecMain = localRecommendCategoryNode.lstRecMain;
    }
    this.hasRestoredSucc = true;
    return true;
  }

  public void updateToDB()
  {
    HashMap localHashMap = new HashMap();
    localHashMap.put("id", Integer.valueOf(this.sectionId));
    if (this.lstRecMain.size() > 0)
      localHashMap.put("main", this.lstRecMain);
    if (this.lstBanner.size() > 0)
      localHashMap.put("banner", this.lstBanner);
    DataManager.getInstance().getData("updatedb_reccategory_node", null, localHashMap);
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.qtradio.model.RecommendCategoryNode
 * JD-Core Version:    0.6.2
 */