package fm.qingting.qtradio.helper;

import android.os.Handler;
import android.os.Message;
import android.util.SparseArray;
import fm.qingting.framework.data.DataManager;
import fm.qingting.framework.data.IResultToken;
import fm.qingting.framework.data.Result;
import fm.qingting.qtradio.model.Attribute;
import fm.qingting.qtradio.model.ChannelNode;
import fm.qingting.qtradio.model.DownLoadInfoNode;
import fm.qingting.qtradio.model.InfoManager;
import fm.qingting.qtradio.model.InfoManager.ISubscribeEventListener;
import fm.qingting.qtradio.model.Node;
import fm.qingting.qtradio.model.ProgramNode;
import fm.qingting.qtradio.model.RootNode;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChannelHelper extends Node
{
  private static ChannelHelper _instance = null;
  private final int ERROR_CHANNELS = 1;
  private final int LIVE_CHANNELS_PAGE_SIZE = 1000;
  private final int VIRTUAL_CHANNELS_PAGE_SIZE = 30;
  private SparseArray<IDataChangeObserver> mObservers;
  public Map<String, List<ChannelNode>> mapChannelNodes = new HashMap();
  public Map<String, Integer> mapChannelPages = new HashMap();
  private Map<Integer, ChannelNode> mapLiveChannels = new HashMap();
  private Map<String, Integer> mapTotalChannelNodes = new HashMap();
  public Map<String, Boolean> mapUpdateChannels = new HashMap();
  private Map<Integer, ChannelNode> mapVirtualChannels = new HashMap();

  private ChannelHelper()
  {
    this.nodeName = "channelhelper";
  }

  private void addChannels(List<ChannelNode> paramList, int paramInt1, String paramString, int paramInt2)
  {
    if (paramList == null);
    do
    {
      return;
      if (paramInt1 == 1)
      {
        int i1 = 0;
        if (i1 < paramList.size())
        {
          ChannelNode localChannelNode3 = (ChannelNode)this.mapVirtualChannels.get(Integer.valueOf(((ChannelNode)paramList.get(i1)).channelId));
          if (localChannelNode3 != null)
            localChannelNode3.updatePartialInfo((ChannelNode)paramList.get(i1));
          while (true)
          {
            i1++;
            break;
            this.mapVirtualChannels.put(Integer.valueOf(((ChannelNode)paramList.get(i1)).channelId), paramList.get(i1));
          }
        }
      }
      else
      {
        int i = 0;
        if (i < paramList.size())
        {
          ChannelNode localChannelNode2 = (ChannelNode)this.mapLiveChannels.get(Integer.valueOf(((ChannelNode)paramList.get(i)).channelId));
          if (localChannelNode2 != null)
            localChannelNode2.updatePartialInfo((ChannelNode)paramList.get(i));
          while (true)
          {
            i++;
            break;
            this.mapLiveChannels.put(Integer.valueOf(((ChannelNode)paramList.get(i)).channelId), paramList.get(i));
          }
        }
      }
    }
    while (paramString == null);
    if (this.mapChannelNodes.get(paramString) == null)
    {
      this.mapChannelNodes.put(paramString, paramList);
      return;
    }
    List localList = (List)this.mapChannelNodes.get(paramString);
    int j = localList.size();
    if ((paramInt2 == j) && (j > 0))
    {
      ChannelNode localChannelNode1 = (ChannelNode)localList.get(-1 + localList.size());
      localChannelNode1.nextSibling = ((Node)paramList.get(0));
      ((ChannelNode)paramList.get(0)).prevSibling = localChannelNode1;
      localList.addAll(paramList);
      return;
    }
    int k = 0;
    label360: int m;
    if (k < paramList.size())
    {
      m = 0;
      label374: if (m >= localList.size())
        break label512;
      if (((ChannelNode)localList.get(m)).channelId != ((ChannelNode)paramList.get(k)).channelId)
        break label487;
      ((ChannelNode)localList.get(m)).updatePartialInfo((ChannelNode)paramList.get(k));
    }
    label512: for (int n = 1; ; n = 0)
    {
      if (n == 0)
      {
        if (k >= localList.size())
          break label493;
        localList.add(k, paramList.get(k));
      }
      while (true)
      {
        k++;
        break label360;
        break;
        label487: m++;
        break label374;
        label493: localList.add(paramList.get(k));
      }
    }
  }

  private boolean allowReadCache(String paramString)
  {
    if (!InfoManager.getInstance().hasConnectedNetwork());
    while (this.mapUpdateChannels.get(paramString) != null)
      return true;
    this.mapUpdateChannels.put(paramString, Boolean.valueOf(true));
    return false;
  }

  private String buildAttrs(List<Attribute> paramList)
  {
    String str;
    if ((paramList == null) || (paramList.size() == 0))
      str = null;
    while (true)
    {
      return str;
      str = "";
      for (int i = 0; i < paramList.size(); i++)
      {
        str = str + ((Attribute)paramList.get(i)).id;
        if (i < -1 + paramList.size())
          str = str + "/";
      }
    }
  }

  private String buildKey(int paramInt, String paramString)
  {
    String str1 = String.valueOf(paramInt);
    if ((paramString != null) && (!paramString.equalsIgnoreCase("")) && (!paramString.equalsIgnoreCase("0")))
    {
      String str2 = str1 + "/";
      str1 = str2 + paramString;
    }
    return str1;
  }

  private String buildKey(int paramInt, List<Attribute> paramList)
  {
    String str1 = String.valueOf(paramInt);
    if (paramList != null)
    {
      Collections.sort(paramList, new AttributeComparator());
      localObject = str1;
      int i = 0;
      while (i < paramList.size())
      {
        String str2 = (String)localObject + "/";
        String str3 = str2 + ((Attribute)paramList.get(i)).id;
        i++;
        localObject = str3;
      }
    }
    Object localObject = str1;
    return localObject;
  }

  private void dispatch2Observer(ChannelNode paramChannelNode)
  {
    if (this.mObservers == null);
    IDataChangeObserver localIDataChangeObserver;
    do
    {
      return;
      localIDataChangeObserver = (IDataChangeObserver)this.mObservers.get(paramChannelNode.channelId);
    }
    while (localIDataChangeObserver == null);
    localIDataChangeObserver.onChannelNodeInfoUpdate(paramChannelNode);
  }

  private ChannelNode getChannelFromDB(int paramInt1, int paramInt2)
  {
    HashMap localHashMap = new HashMap();
    localHashMap.put("channelid", Integer.valueOf(paramInt1));
    localHashMap.put("type", Integer.valueOf(paramInt2));
    Result localResult = DataManager.getInstance().getData("GETDB_CHANNEL_INFO", null, localHashMap).getResult();
    boolean bool = localResult.getSuccess();
    ChannelNode localChannelNode = null;
    if (bool)
      localChannelNode = (ChannelNode)localResult.getData();
    return localChannelNode;
  }

  public static ChannelHelper getInstance()
  {
    if (_instance == null)
      _instance = new ChannelHelper();
    return _instance;
  }

  private List<ChannelNode> getLstChannelsFromDB(String paramString)
  {
    if ((paramString == null) || (paramString.equalsIgnoreCase("")));
    Result localResult;
    do
    {
      return null;
      HashMap localHashMap = new HashMap();
      localHashMap.put("key", paramString);
      localResult = DataManager.getInstance().getData("GETDB_CHANNEL_NODE", null, localHashMap).getResult();
    }
    while (!localResult.getSuccess());
    return (List)localResult.getData();
  }

  private int getTotalChannelNodes(String paramString)
  {
    if ((paramString != null) && (this.mapTotalChannelNodes.get(paramString) != null))
      return ((Integer)this.mapTotalChannelNodes.get(paramString)).intValue();
    return 0;
  }

  private void incPageByKey(String paramString)
  {
    if ((paramString == null) || (paramString.equalsIgnoreCase("")))
      return;
    int i = 1 + getPageByKey(paramString);
    this.mapChannelPages.put(paramString, Integer.valueOf(i));
  }

  private void updateChannel(ChannelNode paramChannelNode)
  {
    if (paramChannelNode == null)
      return;
    HashMap localHashMap = new HashMap();
    localHashMap.put("node", paramChannelNode);
    localHashMap.put("channelid", Integer.valueOf(paramChannelNode.channelId));
    DataManager.getInstance().getData("UPDATEDB_A_CHANNEL_NODE", null, localHashMap);
  }

  public void addObserver(int paramInt, IDataChangeObserver paramIDataChangeObserver)
  {
    if (this.mObservers == null)
      this.mObservers = new SparseArray();
    this.mObservers.put(paramInt, paramIDataChangeObserver);
  }

  public ChannelNode getChannel(int paramInt1, int paramInt2)
  {
    ChannelNode localChannelNode;
    if (paramInt2 == 0)
    {
      localChannelNode = (ChannelNode)this.mapLiveChannels.get(Integer.valueOf(paramInt1));
      if (localChannelNode == null)
        break label53;
    }
    while (true)
    {
      return localChannelNode;
      if (paramInt2 == 1)
      {
        localChannelNode = (ChannelNode)this.mapVirtualChannels.get(Integer.valueOf(paramInt1));
        if (localChannelNode != null);
      }
      else
      {
        label53: localChannelNode = getChannelFromDB(paramInt1, paramInt2);
        if (localChannelNode != null)
          if (paramInt2 == 1)
            this.mapVirtualChannels.put(Integer.valueOf(paramInt1), localChannelNode);
        while (paramInt2 == 1)
        {
          InfoManager.getInstance().loadVirtualChannelNode(paramInt1, this);
          return localChannelNode;
          if (paramInt2 == 0)
          {
            this.mapLiveChannels.put(Integer.valueOf(paramInt1), localChannelNode);
            continue;
            if (paramInt2 == 0)
              InfoManager.getInstance()._loadLiveChannelNode(paramInt1, this);
          }
        }
      }
    }
  }

  public ChannelNode getChannel(ProgramNode paramProgramNode)
  {
    ChannelNode localChannelNode;
    if (paramProgramNode == null)
      localChannelNode = null;
    while (true)
    {
      return localChannelNode;
      if (paramProgramNode.isDownloadProgram())
        return InfoManager.getInstance().root().mDownLoadInfoNode.getChannelNode(paramProgramNode.channelId);
      if ((paramProgramNode.channelType == 1) || (paramProgramNode.mLiveInVirtual));
      for (localChannelNode = (ChannelNode)this.mapVirtualChannels.get(Integer.valueOf(paramProgramNode.channelId)); localChannelNode == null; localChannelNode = (ChannelNode)this.mapLiveChannels.get(Integer.valueOf(paramProgramNode.channelId)))
      {
        if (!paramProgramNode.mLiveInVirtual)
          break label111;
        return getChannelFromDB(paramProgramNode.channelId, 1);
      }
    }
    label111: return getChannelFromDB(paramProgramNode.channelId, paramProgramNode.channelType);
  }

  public ChannelNode getFakeChannel(int paramInt1, int paramInt2, String paramString, int paramInt3)
  {
    if (paramInt3 == 0)
      return getFakeLiveChannel(paramInt1, paramInt2, paramString);
    return getFakeVirtualChannel(paramInt1, paramInt2, paramString);
  }

  public ChannelNode getFakeLiveChannel(int paramInt1, int paramInt2, String paramString)
  {
    if (this.mapLiveChannels.get(Integer.valueOf(paramInt1)) != null)
      return (ChannelNode)this.mapLiveChannels.get(Integer.valueOf(paramInt1));
    ChannelNode localChannelNode = new ChannelNode();
    localChannelNode.channelId = paramInt1;
    localChannelNode.title = paramString;
    localChannelNode.channelType = 0;
    if (localChannelNode.title == null)
      localChannelNode.title = "蜻蜓fm";
    localChannelNode.categoryId = paramInt2;
    this.mapLiveChannels.put(Integer.valueOf(paramInt1), localChannelNode);
    InfoManager.getInstance()._loadLiveChannelNode(paramInt1, this);
    return localChannelNode;
  }

  public ChannelNode getFakeVirtualChannel(int paramInt1, int paramInt2, String paramString)
  {
    if (this.mapVirtualChannels.get(Integer.valueOf(paramInt1)) != null)
      return (ChannelNode)this.mapVirtualChannels.get(Integer.valueOf(paramInt1));
    if (paramInt2 == DownLoadInfoNode.mDownloadId)
    {
      ChannelNode localChannelNode2 = InfoManager.getInstance().root().mDownLoadInfoNode.getChannelNode(paramInt1);
      if (localChannelNode2 != null)
      {
        this.mapVirtualChannels.put(Integer.valueOf(paramInt1), localChannelNode2);
        return localChannelNode2;
      }
    }
    ChannelNode localChannelNode1 = new ChannelNode();
    localChannelNode1.channelId = paramInt1;
    localChannelNode1.title = paramString;
    localChannelNode1.channelType = 1;
    if (localChannelNode1.title == null)
      localChannelNode1.title = "蜻蜓fm";
    localChannelNode1.categoryId = paramInt2;
    this.mapVirtualChannels.put(Integer.valueOf(paramInt1), localChannelNode1);
    InfoManager.getInstance().loadVirtualChannelNode(paramInt1, this);
    return localChannelNode1;
  }

  public List<ChannelNode> getLstChannelsByAttr(int paramInt, List<Attribute> paramList)
  {
    return getLstChannelsByKey(buildKey(paramInt, paramList));
  }

  public List<ChannelNode> getLstChannelsByAttrPath(int paramInt, String paramString)
  {
    return getLstChannelsByKey(buildKey(paramInt, paramString));
  }

  public List<ChannelNode> getLstChannelsByKey(String paramString)
  {
    List localList1 = null;
    if (paramString != null)
    {
      if (this.mapChannelNodes.get(paramString) == null)
        break label35;
      localList1 = (List)this.mapChannelNodes.get(paramString);
    }
    label35: List localList2;
    int i;
    do
    {
      do
      {
        boolean bool;
        do
        {
          return localList1;
          bool = allowReadCache(paramString);
          localList1 = null;
        }
        while (!bool);
        localList2 = getLstChannelsFromDB(paramString);
        localList1 = null;
      }
      while (localList2 == null);
      i = localList2.size();
      localList1 = null;
    }
    while (i == 0);
    this.mapChannelNodes.put(paramString, localList2);
    return localList2;
  }

  public List<ChannelNode> getLstChannelsByKey(String paramString, boolean paramBoolean)
  {
    List localList1 = null;
    if (paramString != null)
    {
      if (this.mapChannelNodes.get(paramString) == null)
        break label35;
      localList1 = (List)this.mapChannelNodes.get(paramString);
    }
    label35: List localList2;
    int i;
    do
    {
      do
      {
        do
        {
          return localList1;
          localList1 = null;
        }
        while (!paramBoolean);
        localList2 = getLstChannelsFromDB(paramString);
        localList1 = null;
      }
      while (localList2 == null);
      i = localList2.size();
      localList1 = null;
    }
    while (i == 0);
    this.mapChannelNodes.put(paramString, localList2);
    return localList2;
  }

  public List<ChannelNode> getLstLiveChannelsByAttrPath(int paramInt, String paramString, boolean paramBoolean)
  {
    String str = buildKey(paramInt, paramString);
    List localList = getLstChannelsByKey(str);
    if ((localList == null) || (localList.size() == 0))
    {
      loadListLiveChannelNodes(paramInt, paramString, null);
      localList = getLstChannelsByKey(str, paramBoolean);
    }
    return localList;
  }

  public ChannelNode getNextChannel(int paramInt1, int paramInt2)
  {
    ChannelNode localChannelNode = getChannel(paramInt1, paramInt2);
    if (localChannelNode != null)
      return (ChannelNode)localChannelNode.nextSibling;
    return null;
  }

  public int getPageByAttr(int paramInt, List<Attribute> paramList)
  {
    if ((paramList == null) || (paramList.size() == 0))
      return 0;
    return getPageByKey(buildKey(paramInt, paramList));
  }

  public int getPageByAttrPath(int paramInt, String paramString)
  {
    return getPageByKey(buildKey(paramInt, paramString));
  }

  public int getPageByKey(String paramString)
  {
    if (paramString != null)
    {
      if (this.mapChannelPages.get(paramString) != null)
        return ((Integer)this.mapChannelPages.get(paramString)).intValue();
      List localList = getLstChannelsByKey(paramString);
      if (localList != null)
      {
        this.mapChannelPages.put(paramString, Integer.valueOf(localList.size() / 30));
        return localList.size() / 30;
      }
    }
    return 0;
  }

  public ChannelNode getPrevChannel(int paramInt1, int paramInt2)
  {
    ChannelNode localChannelNode = getChannel(paramInt1, paramInt2);
    if (localChannelNode != null)
      return (ChannelNode)localChannelNode.prevSibling;
    return null;
  }

  public void init()
  {
    InfoManager.getInstance().registerNodeEventListener(this, "ADD_CATEGORY_ALL_CHANNELS");
    InfoManager.getInstance().registerNodeEventListener(this, "ADD_VIRTUAL_CHANNELS_BYATTR");
    InfoManager.getInstance().registerNodeEventListener(this, "ADD_LIVE_CHANNELS_BYATTR");
    InfoManager.getInstance().registerNodeEventListener(this, "ADD_SPECIAL_TOPIC_CHANNELS");
  }

  public boolean isFinished(int paramInt, String paramString)
  {
    String str = buildKey(paramInt, paramString);
    if (str != null)
    {
      List localList = getLstChannelsByKey(str);
      int i = getTotalChannelNodes(str);
      if ((localList != null) && (i > 0) && (1 + localList.size() > i))
        return true;
    }
    return false;
  }

  public boolean isFinished(int paramInt, List<Attribute> paramList)
  {
    String str = buildKey(paramInt, paramList);
    if (str != null)
    {
      List localList = getLstChannelsByKey(str);
      int i = getTotalChannelNodes(str);
      if ((localList != null) && (i > 0) && (1 + localList.size() > i))
        return true;
    }
    return false;
  }

  public void loadListLiveChannelNodes(int paramInt, String paramString, InfoManager.ISubscribeEventListener paramISubscribeEventListener)
  {
    InfoManager.getInstance().loadListLiveChannelsByAttr(paramInt, paramString, 1, 1000, paramISubscribeEventListener);
  }

  public void loadListVirtualChannelNodesByAttr(int paramInt, List<Attribute> paramList, InfoManager.ISubscribeEventListener paramISubscribeEventListener)
  {
    String str = buildKey(paramInt, paramList);
    int i = getPageByKey(str);
    int j = getTotalChannelNodes(str);
    if ((j > 0) && (i * 30 >= j))
      return;
    int k = i + 1;
    incPageByKey(str);
    InfoManager.getInstance().loadListVirtualChannelsByAttr(paramInt, buildAttrs(paramList), k, 30, false, paramISubscribeEventListener);
  }

  public void loadListVirtualChannelNodesById(int paramInt, String paramString, InfoManager.ISubscribeEventListener paramISubscribeEventListener)
  {
    String str = buildKey(paramInt, paramString);
    int i = getPageByKey(str);
    int j = getTotalChannelNodes(str);
    if ((j > 0) && (i * 30 >= j))
      return;
    int k = i + 1;
    incPageByKey(str);
    InfoManager.getInstance().loadListVirtualChannelsByAttr(paramInt, paramString, k, 30, false, paramISubscribeEventListener);
  }

  public void onNodeUpdated(Object paramObject, String paramString)
  {
    if (paramObject == null);
    Node localNode1;
    do
    {
      do
      {
        Node localNode2;
        do
        {
          return;
          if (!paramString.equalsIgnoreCase("ADD_VIRTUAL_CHANNEL_INFO"))
            break;
          localNode2 = (Node)paramObject;
        }
        while ((localNode2 == null) || (!localNode2.nodeName.equalsIgnoreCase("channel")));
        ChannelNode localChannelNode2 = (ChannelNode)this.mapVirtualChannels.get(Integer.valueOf(((ChannelNode)localNode2).channelId));
        if (localChannelNode2 != null)
          localChannelNode2.updatePartialInfo((ChannelNode)localNode2);
        while (true)
        {
          dispatch2Observer((ChannelNode)localNode2);
          updateChannel((ChannelNode)localNode2);
          return;
          this.mapVirtualChannels.put(Integer.valueOf(((ChannelNode)localNode2).channelId), (ChannelNode)localNode2);
        }
      }
      while (!paramString.equalsIgnoreCase("ADD_LIVE_CHANNEL_INFO"));
      localNode1 = (Node)paramObject;
    }
    while ((localNode1 == null) || (!localNode1.nodeName.equalsIgnoreCase("channel")));
    ChannelNode localChannelNode1 = (ChannelNode)this.mapLiveChannels.get(Integer.valueOf(((ChannelNode)localNode1).channelId));
    if (localChannelNode1 != null)
      localChannelNode1.updatePartialInfo((ChannelNode)localNode1);
    while (true)
    {
      dispatch2Observer((ChannelNode)localNode1);
      updateChannel((ChannelNode)localNode1);
      return;
      this.mapLiveChannels.put(Integer.valueOf(((ChannelNode)localNode1).channelId), (ChannelNode)localNode1);
    }
  }

  public void onNodeUpdated(Object paramObject, Map<String, String> paramMap, String paramString)
  {
    if (paramString.equalsIgnoreCase("ADD_CATEGORY_ALL_CHANNELS"))
    {
      List localList4 = (List)paramObject;
      if ((localList4.size() > 0) && (paramMap != null))
      {
        String str9 = (String)paramMap.get("page");
        String str10 = (String)paramMap.get("pagesize");
        int j = (-1 + Integer.valueOf(str9).intValue()) * Integer.valueOf(str10).intValue();
        ChannelNode localChannelNode2 = (ChannelNode)localList4.get(0);
        String str11 = buildKey(localChannelNode2.categoryId, "");
        String str12 = (String)paramMap.get("code");
        String str13 = (String)paramMap.get("message");
        if ((str12 != null) && (str13 != null) && (str12.equalsIgnoreCase("total")))
          this.mapTotalChannelNodes.put(str11, Integer.valueOf(str13));
        addChannels(localList4, localChannelNode2.channelType, str11, j);
        Message localMessage2 = new Message();
        localMessage2.what = 9;
        localMessage2.obj = str11;
        InfoManager.getInstance().getDataStoreHandler().sendMessage(localMessage2);
      }
    }
    List localList1;
    do
    {
      do
      {
        List localList2;
        do
        {
          List localList3;
          do
          {
            return;
            if (!paramString.equalsIgnoreCase("ADD_LIVE_CHANNELS_BYATTR"))
              break;
            localList3 = (List)paramObject;
          }
          while ((localList3.size() <= 0) || (paramMap == null));
          String str7 = (String)paramMap.get("attr");
          String str8 = buildKey(((ChannelNode)localList3.get(0)).categoryId, str7);
          addChannels(localList3, 0, str8, 0);
          Message localMessage1 = new Message();
          localMessage1.what = 9;
          localMessage1.obj = str8;
          InfoManager.getInstance().getDataStoreHandler().sendMessage(localMessage1);
          return;
          if (!paramString.equalsIgnoreCase("ADD_VIRTUAL_CHANNELS_BYATTR"))
            break;
          localList2 = (List)paramObject;
        }
        while ((localList2.size() <= 0) || (paramMap == null));
        String str1 = (String)paramMap.get("page");
        String str2 = (String)paramMap.get("pagesize");
        String str3 = (String)paramMap.get("attr");
        int i = (-1 + Integer.valueOf(str1).intValue()) * Integer.valueOf(str2).intValue();
        ChannelNode localChannelNode1 = (ChannelNode)localList2.get(0);
        String str4 = buildKey(localChannelNode1.categoryId, str3);
        String str5 = (String)paramMap.get("code");
        String str6 = (String)paramMap.get("message");
        if ((str5 != null) && (str6 != null) && (str5.equalsIgnoreCase("total")))
          this.mapTotalChannelNodes.put(str4, Integer.valueOf(str6));
        addChannels(localList2, localChannelNode1.channelType, str4, i);
        return;
      }
      while (!paramString.equalsIgnoreCase("ADD_SPECIAL_TOPIC_CHANNELS"));
      localList1 = (List)paramObject;
    }
    while ((localList1.size() <= 0) || (paramMap == null));
    addChannels(localList1, 1, String.valueOf(1000001 + Integer.valueOf((String)paramMap.get("id")).intValue()), 0);
  }

  public void reloadListVirtualChannelNodesByAttr(int paramInt, List<Attribute> paramList, InfoManager.ISubscribeEventListener paramISubscribeEventListener)
  {
    String str = buildKey(paramInt, paramList);
    if (1 + getPageByKey(str) == 1)
      incPageByKey(str);
    InfoManager.getInstance().loadListVirtualChannelsByAttr(paramInt, buildAttrs(paramList), 1, 30, true, paramISubscribeEventListener);
  }

  public void reloadListVirtualChannelNodesById(int paramInt, String paramString, InfoManager.ISubscribeEventListener paramISubscribeEventListener)
  {
    String str = buildKey(paramInt, paramString);
    if (1 + getPageByKey(str) == 1)
      incPageByKey(str);
    InfoManager.getInstance().loadListVirtualChannelsByAttr(paramInt, paramString, 1, 30, true, paramISubscribeEventListener);
  }

  public void removeObserver(int paramInt)
  {
    if (this.mObservers == null)
      return;
    this.mObservers.remove(paramInt);
  }

  public void setChannel(ChannelNode paramChannelNode, boolean paramBoolean)
  {
    if (paramChannelNode == null);
    do
    {
      do
        return;
      while (paramChannelNode.isDownloadChannel());
      if (paramChannelNode.channelType != 1)
        break;
      this.mapVirtualChannels.put(Integer.valueOf(paramChannelNode.channelId), paramChannelNode);
    }
    while (!paramBoolean);
    InfoManager.getInstance().loadVirtualChannelNode(paramChannelNode.channelId, this);
    return;
    this.mapLiveChannels.put(Integer.valueOf(paramChannelNode.channelId), paramChannelNode);
  }

  public void setChannelsByAttr(List<ChannelNode> paramList, int paramInt1, String paramString, int paramInt2)
  {
    if ((paramList == null) || (paramString == null))
      return;
    String str = paramInt1 + "/" + paramString;
    if (paramInt2 == 1)
    {
      this.mapChannelNodes.put(str, paramList);
      return;
    }
    Object localObject = (List)this.mapChannelNodes.get(str);
    if (localObject != null)
      ((List)localObject).addAll(paramList);
    while (true)
    {
      this.mapChannelNodes.put(str, localObject);
      return;
      localObject = paramList;
    }
  }

  public void updateChannel(String paramString)
  {
    if (paramString == null);
    List localList;
    do
    {
      return;
      localList = getLstChannelsByKey(paramString);
    }
    while ((localList == null) || (localList.size() == 0));
    HashMap localHashMap = new HashMap();
    localHashMap.put("nodes", localList);
    localHashMap.put("key", paramString);
    DataManager.getInstance().getData("UPDATEDB_CHANNEL_NODE", null, localHashMap);
  }

  class AttributeComparator
    implements Comparator<Attribute>
  {
    AttributeComparator()
    {
    }

    public int compare(Attribute paramAttribute1, Attribute paramAttribute2)
    {
      if (paramAttribute1.id > paramAttribute2.id)
        return 1;
      if (paramAttribute1.id < paramAttribute2.id)
        return -1;
      return 0;
    }
  }

  public static abstract interface IDataChangeObserver
  {
    public abstract void onChannelNodeInfoUpdate(ChannelNode paramChannelNode);
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.qtradio.helper.ChannelHelper
 * JD-Core Version:    0.6.2
 */