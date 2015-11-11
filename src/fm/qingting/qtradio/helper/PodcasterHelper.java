package fm.qingting.qtradio.helper;

import android.text.TextUtils;
import android.util.Pair;
import fm.qingting.framework.data.DataManager;
import fm.qingting.framework.data.IResultToken;
import fm.qingting.framework.data.Result;
import fm.qingting.qtradio.model.ChannelNode;
import fm.qingting.qtradio.model.InfoManager;
import fm.qingting.qtradio.model.Node;
import fm.qingting.qtradio.model.ProgramNode;
import fm.qingting.qtradio.room.UserInfo;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class PodcasterHelper extends Node
{
  private static PodcasterHelper _instance = null;
  private Map<Integer, UserInfo> mapPodcasters = new HashMap();

  private PodcasterHelper()
  {
    this.nodeName = "podcasterhelper";
  }

  private void addPodcaster(UserInfo paramUserInfo)
  {
    if (paramUserInfo == null)
      return;
    UserInfo localUserInfo = (UserInfo)this.mapPodcasters.get(Integer.valueOf(paramUserInfo.podcasterId));
    if (localUserInfo == null)
      this.mapPodcasters.put(Integer.valueOf(paramUserInfo.podcasterId), paramUserInfo);
    while (true)
    {
      HashMap localHashMap = new HashMap();
      localHashMap.put("userInfo", paramUserInfo);
      DataManager.getInstance().getData("UPDATEDB_PODCASTER_INFO", null, localHashMap);
      return;
      localUserInfo.updateUserInfo(paramUserInfo);
    }
  }

  private void addPodcasterChannels(int paramInt, List<ChannelNode> paramList)
  {
    UserInfo localUserInfo1 = (UserInfo)this.mapPodcasters.get(Integer.valueOf(paramInt));
    UserInfo localUserInfo2;
    if (localUserInfo1 == null)
    {
      localUserInfo2 = new UserInfo();
      localUserInfo2.podcasterId = paramInt;
    }
    for (UserInfo localUserInfo3 = localUserInfo2; ; localUserInfo3 = localUserInfo1)
    {
      localUserInfo3.setChannelNodes(paramList);
      for (int i = 0; i < paramList.size(); i++)
        if (((ChannelNode)paramList.get(i)).lstPodcasters == null)
        {
          ((ChannelNode)paramList.get(i)).lstPodcasters = new ArrayList();
          ((ChannelNode)paramList.get(i)).lstPodcasters.add(localUserInfo3);
        }
      this.mapPodcasters.put(Integer.valueOf(paramInt), localUserInfo3);
      return;
    }
  }

  private void addPodcasterPrograms(int paramInt, List<ProgramNode> paramList)
  {
    UserInfo localUserInfo = (UserInfo)this.mapPodcasters.get(Integer.valueOf(paramInt));
    if (localUserInfo == null)
    {
      localUserInfo = new UserInfo();
      localUserInfo.podcasterId = paramInt;
    }
    localUserInfo.setProgramNodes(paramList);
  }

  public static PodcasterHelper getInstance()
  {
    try
    {
      if (_instance == null)
      {
        _instance = new PodcasterHelper();
        _instance.init();
      }
      PodcasterHelper localPodcasterHelper = _instance;
      return localPodcasterHelper;
    }
    finally
    {
    }
  }

  private UserInfo getPodcasterFromDB(int paramInt)
  {
    HashMap localHashMap = new HashMap();
    localHashMap.put("pid", Integer.valueOf(paramInt));
    Result localResult = DataManager.getInstance().getData("GETDB_PODCASTER_INFO", null, localHashMap).getResult();
    boolean bool = localResult.getSuccess();
    UserInfo localUserInfo = null;
    if (bool)
      localUserInfo = (UserInfo)localResult.getData();
    return localUserInfo;
  }

  private void updateLastestProgramId(UserInfo paramUserInfo)
  {
    UserInfo localUserInfo = (UserInfo)this.mapPodcasters.get(Integer.valueOf(paramUserInfo.podcasterId));
    if (localUserInfo == null)
    {
      this.mapPodcasters.put(Integer.valueOf(paramUserInfo.podcasterId), paramUserInfo);
      return;
    }
    localUserInfo.lastestUpdateTime = paramUserInfo.lastestUpdateTime;
  }

  public boolean addMyPodcaster(int paramInt, String paramString, long paramLong)
  {
    HashMap localHashMap = new HashMap();
    localHashMap.put("pid", Integer.valueOf(paramInt));
    localHashMap.put("ukey", paramString);
    localHashMap.put("uptime", Long.valueOf(paramLong));
    return DataManager.getInstance().getData("INSERTDB_PODCASTER_FOLLOW_INFO", null, localHashMap).getResult().getSuccess();
  }

  public List<Integer> getAllMyPodcaster(String paramString)
  {
    HashMap localHashMap = new HashMap();
    localHashMap.put("ukey", paramString);
    Result localResult = DataManager.getInstance().getData("GETDB_ALL_PODCASTER_FOLLOW_INFO", null, localHashMap).getResult();
    boolean bool = localResult.getSuccess();
    Object localObject = null;
    if (bool)
    {
      List localList = (List)localResult.getData();
      ArrayList localArrayList = new ArrayList();
      Iterator localIterator = localList.iterator();
      while (localIterator.hasNext())
      {
        Pair localPair = (Pair)localIterator.next();
        int i = ((Integer)localPair.first).intValue();
        long l = ((Long)localPair.second).longValue();
        UserInfo localUserInfo = new UserInfo();
        localUserInfo.podcasterId = i;
        localUserInfo.lastestUpdateTime = l;
        updateLastestProgramId(localUserInfo);
        localArrayList.add(Integer.valueOf(i));
      }
      localObject = localArrayList;
    }
    return localObject;
  }

  public UserInfo getPodcaster(int paramInt)
  {
    UserInfo localUserInfo1 = (UserInfo)this.mapPodcasters.get(Integer.valueOf(paramInt));
    if ((localUserInfo1 == null) || (TextUtils.isEmpty(localUserInfo1.podcasterName)))
    {
      UserInfo localUserInfo2 = getPodcasterFromDB(paramInt);
      if (localUserInfo1 != null)
        localUserInfo1.updateUserInfo(localUserInfo2);
    }
    if (localUserInfo1 == null)
    {
      localUserInfo1 = new UserInfo();
      localUserInfo1.podcasterId = paramInt;
      localUserInfo1.podcasterName = "加载中";
      localUserInfo1.isPodcaster = true;
      InfoManager.getInstance().loadPodcasterBaseInfo(localUserInfo1.podcasterId, null);
    }
    this.mapPodcasters.put(Integer.valueOf(localUserInfo1.podcasterId), localUserInfo1);
    return localUserInfo1;
  }

  public void init()
  {
    InfoManager.getInstance().registerNodeEventListener(this, "ADD_PODCASTER_BASE");
    InfoManager.getInstance().registerNodeEventListener(this, "ADD_PODCASTER_CHANNELS");
    InfoManager.getInstance().registerNodeEventListener(this, "ADD_PODCASTER_DETAIL");
    InfoManager.getInstance().registerNodeEventListener(this, "ADD_PODCASTER_LATEST");
  }

  public boolean isMyPodcaster(int paramInt, String paramString)
  {
    HashMap localHashMap = new HashMap();
    localHashMap.put("pid", Integer.valueOf(paramInt));
    localHashMap.put("ukey", paramString);
    Result localResult = DataManager.getInstance().getData("GETDB_PODCASTER_FOLLOW_INFO", null, localHashMap).getResult();
    if (localResult.getSuccess())
      return ((Boolean)localResult.getData()).booleanValue();
    return false;
  }

  public void onNodeUpdated(Object paramObject, Map<String, String> paramMap, String paramString)
  {
    if (paramString.equalsIgnoreCase("ADD_PODCASTER_BASE"))
      addPodcaster((UserInfo)paramObject);
    String str1;
    do
    {
      do
      {
        String str2;
        do
        {
          do
          {
            return;
            if (!paramString.equalsIgnoreCase("ADD_PODCASTER_CHANNELS"))
              break;
          }
          while (paramMap == null);
          str2 = (String)paramMap.get("id");
        }
        while ((str2 == null) || (str2.equalsIgnoreCase("")));
        addPodcasterChannels(Integer.valueOf(str2).intValue(), (List)paramObject);
        return;
      }
      while ((paramString.equalsIgnoreCase("ADD_PODCASTER_DETAIL")) || (!paramString.equalsIgnoreCase("ADD_PODCASTER_LATEST")) || (paramMap == null));
      str1 = (String)paramMap.get("id");
    }
    while ((str1 == null) || (str1.equalsIgnoreCase("")));
    addPodcasterPrograms(Integer.valueOf(str1).intValue(), (List)paramObject);
  }

  public boolean removeMyPodcaster(int paramInt, String paramString)
  {
    HashMap localHashMap = new HashMap();
    localHashMap.put("pid", Integer.valueOf(paramInt));
    localHashMap.put("ukey", paramString);
    Result localResult = DataManager.getInstance().getData("DELETEDB_PODCASTER_FOLLOW_INFO", null, localHashMap).getResult();
    if (localResult.getSuccess())
      return ((Boolean)localResult.getData()).booleanValue();
    return false;
  }

  public boolean updateMyPodcasterLastestProgramId(int paramInt, String paramString, long paramLong)
  {
    HashMap localHashMap = new HashMap();
    localHashMap.put("pid", Integer.valueOf(paramInt));
    localHashMap.put("ukey", paramString);
    localHashMap.put("uptime", Long.valueOf(paramLong));
    return DataManager.getInstance().getData("UPDATEDB_PODCASTER_LATEST_PROGRAME", null, localHashMap).getResult().getSuccess();
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.qtradio.helper.PodcasterHelper
 * JD-Core Version:    0.6.2
 */