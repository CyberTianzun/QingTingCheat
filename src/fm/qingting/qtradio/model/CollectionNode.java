package fm.qingting.qtradio.model;

import fm.qingting.framework.data.DataManager;
import fm.qingting.framework.data.IResultToken;
import fm.qingting.framework.data.Result;
import fm.qingting.framework.manager.EventDispacthManager;
import fm.qingting.qtradio.helper.ChannelHelper;
import fm.qingting.qtradio.pushcontent.TimeBean;
import fm.qingting.qtradio.social.MiniFavNode;
import fm.qingting.utils.DateUtil;
import fm.qingting.utils.LifeTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CollectionNode extends Node
{
  private int MAX_UPDATE_CHANNEL_CNT = 5;
  private Map<String, TimeBean> mChannelsTiming;
  private int mHasUpdateDB = 0;
  private List<ChannelNode> mLstFavouriteNodes = null;
  public String mTitle = "已收藏电台";
  private boolean needToWriteToDB = false;

  public CollectionNode()
  {
    this.nodeName = "collection";
  }

  private void addNode(Node paramNode)
  {
    if (paramNode == null);
    while (!paramNode.nodeName.equalsIgnoreCase("channel"))
      return;
    ChannelNode localChannelNode = (ChannelNode)paramNode;
    this.needToWriteToDB = true;
    if (this.mLstFavouriteNodes == null)
    {
      this.mLstFavouriteNodes = new ArrayList();
      localChannelNode.parent = this;
      this.mLstFavouriteNodes.add(localChannelNode);
      return;
    }
    for (int i = 0; ; i++)
      if (i < this.mLstFavouriteNodes.size())
      {
        if (((ChannelNode)this.mLstFavouriteNodes.get(i)).channelId == localChannelNode.channelId)
          this.mLstFavouriteNodes.remove(i);
      }
      else
      {
        localChannelNode.parent = this;
        this.mLstFavouriteNodes.add(0, localChannelNode);
        return;
      }
  }

  private void deleteAllCollection()
  {
    DataManager.getInstance().getData("delete_favourite_channels", null, null);
  }

  private void updateNode(Node paramNode)
  {
    if (paramNode == null)
      break label4;
    while (true)
    {
      label4: return;
      if (paramNode.nodeName.equalsIgnoreCase("channel"))
      {
        ChannelNode localChannelNode = (ChannelNode)paramNode;
        if (this.mLstFavouriteNodes == null)
          break;
        for (int i = 0; i < this.mLstFavouriteNodes.size(); i++)
          if (((ChannelNode)this.mLstFavouriteNodes.get(i)).channelId == localChannelNode.channelId)
          {
            ((ChannelNode)this.mLstFavouriteNodes.get(i)).updatePartialInfo(localChannelNode);
            return;
          }
      }
    }
  }

  public void WriteToDB()
  {
    InfoManager.getInstance().saveChannelsTime();
    if (!needToWriteToDB())
      return;
    this.mHasUpdateDB = ((int)(System.currentTimeMillis() / 1000L));
    updateFavouriteChannels();
    deleteAllCollection();
    this.needToWriteToDB = false;
    HashMap localHashMap = new HashMap();
    localHashMap.put("channels", this.mLstFavouriteNodes);
    localHashMap.put("total", Integer.valueOf(this.mLstFavouriteNodes.size()));
    DataManager.getInstance().getData("insert_favourite_channels", null, localHashMap);
    InfoManager.getInstance().root().setInfoUpdate(0);
  }

  public void addFavNode(Node paramNode)
  {
    addFavNode(paramNode, true);
  }

  public void addFavNode(Node paramNode, boolean paramBoolean)
  {
    if (paramNode == null);
    ChannelNode localChannelNode2;
    do
    {
      do
        return;
      while ((isExisted(paramNode)) || (!paramNode.nodeName.equalsIgnoreCase("channel")));
      ChannelNode localChannelNode1 = (ChannelNode)paramNode;
      InfoManager.getInstance().insertTime(String.valueOf(localChannelNode1.channelId), DateUtil.getCurrentSeconds());
      localChannelNode2 = ((ChannelNode)paramNode).clone();
      localChannelNode2.parent = this;
      this.mLstFavouriteNodes.add(0, localChannelNode2);
      this.needToWriteToDB = true;
      InfoManager.getInstance().root().setInfoUpdate(0);
    }
    while (!paramBoolean);
    EventDispacthManager.getInstance().dispatchAction("showToast", "已添加" + localChannelNode2.title + "到收藏中");
  }

  public void deleteFavNode(Node paramNode)
  {
    if (paramNode == null)
      break label4;
    while (true)
    {
      label4: return;
      if (this.mLstFavouriteNodes != null)
      {
        this.needToWriteToDB = true;
        if (!paramNode.nodeName.equalsIgnoreCase("channel"))
          break;
        int i = ((ChannelNode)paramNode).channelId;
        for (int j = -1 + this.mLstFavouriteNodes.size(); j >= 0; j--)
          if ((((ChannelNode)this.mLstFavouriteNodes.get(j)).nodeName.equalsIgnoreCase("channel")) && (((ChannelNode)this.mLstFavouriteNodes.get(j)).channelId == i))
          {
            this.mLstFavouriteNodes.remove(j);
            InfoManager.getInstance().root().setInfoUpdate(0);
            InfoManager.getInstance().removeTime(String.valueOf(i));
            return;
          }
      }
    }
  }

  public List<ChannelNode> getFavouriteNodes()
  {
    if (this.mLstFavouriteNodes == null)
    {
      this.mLstFavouriteNodes = ((List)DataManager.getInstance().getData("get_favourite_channels", null, null).getResult().getData());
      if (this.mLstFavouriteNodes == null)
        this.mLstFavouriteNodes = new ArrayList();
      if (this.mChannelsTiming != null)
        Collections.sort(this.mLstFavouriteNodes, new CollectionNodeComparator());
      for (int i = 0; i < this.mLstFavouriteNodes.size(); i++)
        ((ChannelNode)this.mLstFavouriteNodes.get(i)).parent = this;
    }
    return this.mLstFavouriteNodes;
  }

  public boolean hasDelOldCollection()
  {
    if (InfoManager.getInstance().getContext() != null)
      return SharedCfg.getInstance().getHasDeleteOldCollection();
    return false;
  }

  public int hasUpdateDB()
  {
    return this.mHasUpdateDB;
  }

  public void init(Map<String, TimeBean> paramMap)
  {
    this.mChannelsTiming = paramMap;
    if (!LifeTime.isFirstLaunchAfterInstall)
    {
      getFavouriteNodes();
      return;
    }
    this.mLstFavouriteNodes = new ArrayList();
  }

  public boolean isEmpty()
  {
    return this.mLstFavouriteNodes.size() <= 0;
  }

  public boolean isExisted(int paramInt)
  {
    if (this.mLstFavouriteNodes != null)
      for (int i = -1 + this.mLstFavouriteNodes.size(); i >= 0; i--)
        if (((ChannelNode)this.mLstFavouriteNodes.get(i)).channelId == paramInt)
          return true;
    return false;
  }

  public boolean isExisted(Node paramNode)
  {
    if (paramNode == null);
    while (this.mLstFavouriteNodes == null)
      return false;
    if (paramNode.nodeName.equalsIgnoreCase("channel"));
    for (int i = ((ChannelNode)paramNode).channelId; ; i = 0)
    {
      for (int j = -1 + this.mLstFavouriteNodes.size(); j >= 0; j--)
        if ((((ChannelNode)this.mLstFavouriteNodes.get(j)).nodeName.equalsIgnoreCase("channel")) && (((ChannelNode)this.mLstFavouriteNodes.get(j)).channelId == i))
          return true;
      break;
    }
  }

  public void mergeWithFavNodes(List<MiniFavNode> paramList)
  {
    if (paramList == null)
      return;
    int i = 0;
    int j = 0;
    label9: ChannelNode localChannelNode1;
    ChannelNode localChannelNode2;
    if (i < paramList.size())
      if (!isExisted(((MiniFavNode)paramList.get(i)).id))
      {
        localChannelNode1 = ChannelHelper.getInstance().getChannel(((MiniFavNode)paramList.get(i)).id, ((MiniFavNode)paramList.get(i)).channelType);
        if (localChannelNode1 != null)
          break label258;
        if (((MiniFavNode)paramList.get(i)).channelType != 1)
          break label192;
        localChannelNode2 = ChannelHelper.getInstance().getFakeVirtualChannel(((MiniFavNode)paramList.get(i)).id, ((MiniFavNode)paramList.get(i)).categoryId, ((MiniFavNode)paramList.get(i)).name);
      }
    label258: 
    while (true)
    {
      localChannelNode2.parent = this;
      if (localChannelNode2 != null)
      {
        this.mLstFavouriteNodes.add(localChannelNode2);
        j = 1;
      }
      if (j != 0)
      {
        this.needToWriteToDB = true;
        InfoManager.getInstance().root().setInfoUpdate(0);
      }
      i++;
      break label9;
      break;
      label192: if (((MiniFavNode)paramList.get(i)).channelType == 0)
        localChannelNode2 = ChannelHelper.getInstance().getFakeLiveChannel(((MiniFavNode)paramList.get(i)).id, ((MiniFavNode)paramList.get(i)).categoryId, ((MiniFavNode)paramList.get(i)).name);
      else
        localChannelNode2 = localChannelNode1;
    }
  }

  public boolean needToWriteToDB()
  {
    return this.needToWriteToDB;
  }

  public void onNodeUpdated(Object paramObject, String paramString)
  {
    Node localNode = (Node)paramObject;
    if ((localNode == null) || (paramString == null));
    while ((paramString.equalsIgnoreCase("ADD_LIVE_CHANNEL_INFO")) || (!paramString.equalsIgnoreCase("ADD_VIRTUAL_CHANNEL_INFO")))
      return;
    updateNode(localNode);
  }

  public void removeOldFavoredVirtual()
  {
    getFavouriteNodes();
    for (int i = -1 + this.mLstFavouriteNodes.size(); i >= 0; i--)
    {
      ChannelNode localChannelNode = (ChannelNode)this.mLstFavouriteNodes.get(i);
      if (localChannelNode.channelType == 1)
        deleteFavNode(localChannelNode);
    }
  }

  public void updateChannelsInfo()
  {
    int i = 0;
    if (this.mLstFavouriteNodes != null)
    {
      int j = 0;
      while ((j < this.mLstFavouriteNodes.size()) && (i < this.MAX_UPDATE_CHANNEL_CNT))
      {
        int k = i + 1;
        if (((ChannelNode)this.mLstFavouriteNodes.get(j)).channelType == 1)
          InfoManager.getInstance().loadVirtualChannelNode(((ChannelNode)this.mLstFavouriteNodes.get(j)).channelId, this);
        j++;
        i = k;
      }
    }
  }

  public void updateFavouriteChannels()
  {
  }

  class CollectionNodeComparator
    implements Comparator<Node>
  {
    CollectionNodeComparator()
    {
    }

    public int compare(Node paramNode1, Node paramNode2)
    {
      if ((paramNode1 == null) || (paramNode2 == null) || (!(paramNode1 instanceof ChannelNode)) || (!(paramNode2 instanceof ChannelNode)));
      int j;
      label174: label180: label196: 
      do
      {
        TimeBean localTimeBean1;
        TimeBean localTimeBean2;
        int i;
        do
        {
          do
          {
            return 0;
            ChannelNode localChannelNode1 = (ChannelNode)paramNode1;
            localTimeBean1 = (TimeBean)CollectionNode.this.mChannelsTiming.get(Integer.valueOf(localChannelNode1.channelId));
            ChannelNode localChannelNode2 = (ChannelNode)paramNode2;
            localTimeBean2 = (TimeBean)CollectionNode.this.mChannelsTiming.get(Integer.valueOf(localChannelNode2.channelId));
          }
          while ((localTimeBean1 == null) && (localTimeBean2 == null));
          if (localTimeBean1 == null)
            return 1;
          if (localTimeBean2 == null)
            return -1;
          if (localTimeBean1.getViewTime() < localTimeBean1.getUpdateTime())
          {
            i = 1;
            if (localTimeBean2.getViewTime() >= localTimeBean2.getUpdateTime())
              break label174;
          }
          for (j = 1; ; j = 0)
          {
            if ((i == 0) || (j == 0))
              break label196;
            if (localTimeBean1.getUpdateTime() <= localTimeBean2.getUpdateTime())
              break label180;
            return -1;
            i = 0;
            break;
          }
        }
        while (localTimeBean1.getUpdateTime() == localTimeBean2.getUpdateTime());
        return 1;
        if (i != 0)
          return -1;
      }
      while (j == 0);
      return 1;
    }
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.qtradio.model.CollectionNode
 * JD-Core Version:    0.6.2
 */