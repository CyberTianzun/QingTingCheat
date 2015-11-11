package fm.qingting.qtradio.room;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import fm.qingting.qtradio.model.InfoManager;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RoomDataCenter
{
  public static RoomDataCenter _instance;
  private CustomData mAskForSong = null;
  private Handler mDispatchHandler = new DispatchHandler(Looper.getMainLooper());
  private UserInfo mEnterUser = null;
  private UserInfo mLoginUser;
  private CustomData mQualifyData;
  private String mTopic = null;
  private Map<String, List<CustomData>> mapLiveRoomData = new HashMap();
  private Map<String, List<CustomData>> mapLiveRoomSongs = new HashMap();
  private Map<String, Integer> mapLiveRoomUserCnts = new HashMap();
  private Map<String, List<UserInfo>> mapLiveRoomUsers = new HashMap();
  private Map<String, List<IRoomDataEventListener>> mapRoomDataEventListeners = new HashMap();
  private Map<String, List<IRoomStateListener>> mapRoomStateListeners = new HashMap();

  private void clearLiveRoomData()
  {
  }

  private void dispatchRoomDataEvent(String paramString)
  {
    if (this.mapRoomDataEventListeners.containsKey(paramString))
    {
      List localList = (List)this.mapRoomDataEventListeners.get(paramString);
      int i = localList.size();
      for (int j = 0; j < i; j++)
        ((IRoomDataEventListener)localList.get(j)).onRoomData(paramString);
    }
  }

  private void dispatchRoomStateEvent(String paramString)
  {
    if (this.mapRoomStateListeners.containsKey(paramString))
    {
      List localList = (List)this.mapRoomStateListeners.get(paramString);
      int i = localList.size();
      for (int j = 0; j < i; j++)
        ((IRoomStateListener)localList.get(j)).onRoomState(paramString);
    }
  }

  public static RoomDataCenter getInstance()
  {
    if (_instance == null)
      _instance = new RoomDataCenter();
    return _instance;
  }

  private List<CustomData> mergeListData(List<CustomData> paramList1, List<CustomData> paramList2)
  {
    if ((paramList1 == null) || (paramList2 == null))
      return null;
    ArrayList localArrayList = new ArrayList();
    int i = 0;
    int j = 0;
    if ((j < paramList1.size()) && (i < paramList2.size()))
    {
      CustomData localCustomData1 = (CustomData)paramList1.get(j);
      CustomData localCustomData2 = (CustomData)paramList2.get(i);
      int m;
      int n;
      if (localCustomData1.createTime > localCustomData2.createTime)
      {
        localArrayList.add(localCustomData2);
        m = i + 1;
        n = j;
      }
      while (true)
      {
        i = m;
        j = n;
        break;
        if ((localCustomData1.createTime == localCustomData2.createTime) && (localCustomData1.msgId.equalsIgnoreCase(localCustomData2.msgId)))
        {
          m = i + 1;
          n = j;
        }
        else
        {
          localArrayList.add(localCustomData1);
          int k = j + 1;
          m = i;
          n = k;
        }
      }
    }
    if (j < paramList1.size())
      while (j < paramList1.size())
      {
        localArrayList.add(paramList1.get(j));
        j++;
      }
    if (i < paramList2.size())
      while (i < paramList2.size())
      {
        localArrayList.add(paramList2.get(i));
        i++;
      }
    return localArrayList;
  }

  private void setEnterUser(UserInfo paramUserInfo)
  {
    this.mEnterUser = paramUserInfo;
  }

  public void clearRoomTopic()
  {
    this.mTopic = null;
  }

  public void enterRoom(int paramInt, String paramString)
  {
    if ((paramInt == 1) && (!this.mapLiveRoomData.containsKey(paramString)))
      clearLiveRoomData();
  }

  public CustomData getAskSongInfo(int paramInt, String paramString)
  {
    if (paramInt == 1)
      return this.mAskForSong;
    return null;
  }

  public UserInfo getEnterUser(int paramInt, String paramString)
  {
    if (paramInt == 1)
      return this.mEnterUser;
    return null;
  }

  public UserInfo getLoginUser(int paramInt)
  {
    if (paramInt == 1)
      return this.mLoginUser;
    return null;
  }

  public CustomData getQualifyData()
  {
    return this.mQualifyData;
  }

  public List<CustomData> getRoomDataByType(int paramInt, String paramString)
  {
    if ((paramInt == 1) && (this.mapLiveRoomData.containsKey(paramString)))
      return (List)this.mapLiveRoomData.get(paramString);
    return null;
  }

  public String getRoomTopic(int paramInt, String paramString)
  {
    if (paramInt == 1)
      return this.mTopic;
    return null;
  }

  public List<UserInfo> getRoomUsersByType(int paramInt, String paramString)
  {
    if (paramString == null);
    while ((paramInt != 1) || (!this.mapLiveRoomUsers.containsKey(paramString)))
      return null;
    return (List)this.mapLiveRoomUsers.get(paramString);
  }

  public int getRoomUsersCntByType(int paramInt, String paramString)
  {
    if (paramString == null);
    while (paramInt != 1)
      return 0;
    return ((Integer)this.mapLiveRoomUserCnts.get(paramString)).intValue();
  }

  public List<CustomData> getSongList(int paramInt, String paramString)
  {
    if (paramString == null);
    while ((paramInt != 1) || (!this.mapLiveRoomSongs.containsKey(paramString)))
      return null;
    return (List)this.mapLiveRoomSongs.get(paramString);
  }

  public boolean hasRoomData(int paramInt, CustomData paramCustomData, String paramString)
  {
    boolean bool1 = false;
    List localList;
    if (paramInt == 1)
    {
      boolean bool2 = this.mapLiveRoomData.containsKey(paramString);
      bool1 = false;
      if (bool2)
        localList = (List)this.mapLiveRoomData.get(paramString);
    }
    for (int i = 0; ; i++)
    {
      int j = localList.size();
      bool1 = false;
      if (i < j)
      {
        if ((((CustomData)localList.get(i)).msgId != null) && (paramCustomData.msgId != null) && (((CustomData)localList.get(i)).msgId.equalsIgnoreCase(paramCustomData.msgId)))
          bool1 = true;
      }
      else
        return bool1;
    }
  }

  public boolean isPrivateMessageButInvalid(CustomData paramCustomData)
  {
    if (paramCustomData == null)
      return false;
    if ((paramCustomData.type == 1) && (((ChatData)paramCustomData).conentType == 3))
    {
      if (((ChatData)paramCustomData).user != null)
      {
        if (((ChatData)paramCustomData).user.userId.equalsIgnoreCase(InfoManager.getInstance().getDeviceId()))
          return false;
        if ((((ChatData)paramCustomData).toUser != null) && (((ChatData)paramCustomData).toUser.userId.equalsIgnoreCase(InfoManager.getInstance().getDeviceId())))
          return false;
      }
      return true;
    }
    return false;
  }

  public boolean isWelMessageButInValid(CustomData paramCustomData)
  {
    if (paramCustomData == null)
      return false;
    if (paramCustomData.type == 1)
    {
      if ((((ChatData)paramCustomData).content != null) && (((ChatData)paramCustomData).content.contains("你好啊:)")))
      {
        if (((ChatData)paramCustomData).user != null)
        {
          if (((ChatData)paramCustomData).user.userId.equalsIgnoreCase(InfoManager.getInstance().getDeviceId()))
            return false;
          if ((((ChatData)paramCustomData).toUser != null) && (((ChatData)paramCustomData).toUser.userId.equalsIgnoreCase(InfoManager.getInstance().getDeviceId())))
            return false;
        }
        return true;
      }
      return false;
    }
    return false;
  }

  public void recvAskForSong(int paramInt, CustomData paramCustomData, String paramString)
  {
    if (paramCustomData == null);
    do
    {
      do
      {
        do
          return;
        while (paramInt != 1);
        if (paramCustomData.type != 1)
          break;
      }
      while (((ChatData)paramCustomData).conentType != 2);
      this.mAskForSong = paramCustomData;
    }
    while (this.mAskForSong == null);
    this.mDispatchHandler.sendMessage(this.mDispatchHandler.obtainMessage(DispatchMsg.RECV_LIVE_ROOM_ASKSONG.ordinal(), "RLRAS"));
  }

  public void recvOnlineUsers(int paramInt, List<UserInfo> paramList, String paramString)
  {
    if ((paramList == null) || (paramList.size() == 0));
    while (paramInt != 1)
      return;
    if ((paramList != null) && (paramList.size() > 0))
      this.mapLiveRoomUsers.put(paramString, paramList);
    this.mDispatchHandler.sendMessage(this.mDispatchHandler.obtainMessage(DispatchMsg.RECV_LIVE_ROOM_ONLINE_USERS.ordinal(), "RLROU"));
  }

  public void recvRoomData(int paramInt, CustomData paramCustomData, String paramString)
  {
    if (paramCustomData == null);
    do
    {
      do
        return;
      while (paramInt != 1);
      if (!this.mapLiveRoomData.containsKey(paramString))
        break;
    }
    while (isWelMessageButInValid(paramCustomData));
    List localList = (List)this.mapLiveRoomData.get(paramString);
    for (int i = 0; ; i++)
    {
      if (i >= localList.size())
        break label117;
      if ((((CustomData)localList.get(i)).msgId != null) && (paramCustomData.msgId != null) && (((CustomData)localList.get(i)).msgId.equalsIgnoreCase(paramCustomData.msgId)))
        break;
    }
    label117: localList.add(paramCustomData);
    this.mDispatchHandler.sendMessage(this.mDispatchHandler.obtainMessage(DispatchMsg.RECV_LIVE_ROOM_CHAT_DATA.ordinal(), "RLRCD"));
    return;
    ArrayList localArrayList = new ArrayList();
    localArrayList.add(paramCustomData);
    this.mapLiveRoomData.put(paramString, localArrayList);
    this.mDispatchHandler.sendMessage(this.mDispatchHandler.obtainMessage(DispatchMsg.RECV_LIVE_ROOM_CHAT_DATA.ordinal(), "RLRCD"));
  }

  public void recvRoomData(int paramInt, List<CustomData> paramList, String paramString)
  {
    if (paramList == null);
    while (paramInt != 1)
      return;
    if (this.mapLiveRoomData.containsKey(paramString))
      paramList = mergeListData((List)this.mapLiveRoomData.get(paramString), paramList);
    if ((paramList != null) && (paramList.size() > 0))
      this.mapLiveRoomData.put(paramString, paramList);
    this.mDispatchHandler.sendMessage(this.mDispatchHandler.obtainMessage(DispatchMsg.RECV_LIVE_ROOM_CHAT_DATA.ordinal(), "RLRCD"));
  }

  public void recvRoomEvent(int paramInt, String paramString)
  {
    if (paramString == null);
    do
    {
      do
        return;
      while (paramInt != 1);
      if (paramString.equalsIgnoreCase("RLRJ"))
      {
        this.mDispatchHandler.sendMessage(this.mDispatchHandler.obtainMessage(DispatchMsg.RECV_LIVE_ROOM_JOIN.ordinal(), "RLRJ"));
        return;
      }
    }
    while (!paramString.equalsIgnoreCase("RLRL"));
    this.mDispatchHandler.sendMessage(this.mDispatchHandler.obtainMessage(DispatchMsg.RECV_LIVE_ROOM_LOGIN.ordinal(), "RLRL"));
  }

  public void recvRoomTopic(int paramInt, CustomData paramCustomData, String paramString)
  {
    if (paramCustomData == null);
    do
    {
      do
        return;
      while (paramInt != 1);
      if (paramCustomData.type == 4)
        this.mTopic = ((TopicData)paramCustomData).topic;
    }
    while (this.mTopic == null);
    this.mDispatchHandler.sendMessage(this.mDispatchHandler.obtainMessage(DispatchMsg.RECV_LIVE_ROOM_TOPIC.ordinal(), "RLRT"));
  }

  public void recvUserEnter(int paramInt, CustomData paramCustomData, String paramString)
  {
    if (paramCustomData == null);
    do
    {
      do
        return;
      while (paramInt != 1);
      if (paramCustomData.type == 3)
        this.mEnterUser = ((EnterRoomData)paramCustomData).user;
    }
    while (this.mEnterUser == null);
    this.mDispatchHandler.sendMessage(this.mDispatchHandler.obtainMessage(DispatchMsg.RECV_LIVE_ROOM_USER_ENTER.ordinal(), "RLRUE"));
  }

  public void registerRoomDataEventListener(IRoomDataEventListener paramIRoomDataEventListener, String paramString)
  {
    List localList;
    if ((paramIRoomDataEventListener != null) && (paramString != null))
    {
      if (this.mapRoomDataEventListeners.containsKey(paramString))
        localList = (List)this.mapRoomDataEventListeners.get(paramString);
    }
    else
    {
      for (int i = 0; i < localList.size(); i++)
        if (localList.get(i) == paramIRoomDataEventListener)
          return;
      ((List)this.mapRoomDataEventListeners.get(paramString)).add(paramIRoomDataEventListener);
      return;
    }
    ArrayList localArrayList = new ArrayList();
    localArrayList.add(paramIRoomDataEventListener);
    this.mapRoomDataEventListeners.put(paramString, localArrayList);
  }

  public void registerRoomStateEventListener(IRoomStateListener paramIRoomStateListener, String paramString)
  {
    List localList;
    if ((paramIRoomStateListener != null) && (paramString != null))
    {
      if (this.mapRoomStateListeners.containsKey(paramString))
        localList = (List)this.mapRoomStateListeners.get(paramString);
    }
    else
    {
      for (int i = 0; i < localList.size(); i++)
        if (localList.get(i) == paramIRoomStateListener)
          return;
      ((List)this.mapRoomStateListeners.get(paramString)).add(paramIRoomStateListener);
      return;
    }
    ArrayList localArrayList = new ArrayList();
    localArrayList.add(paramIRoomStateListener);
    this.mapRoomStateListeners.put(paramString, localArrayList);
  }

  public void setLoginUser(UserInfo paramUserInfo)
  {
    if (paramUserInfo == null)
      return;
    this.mLoginUser = paramUserInfo;
  }

  public void setLoginUserKey(String paramString)
  {
    if (this.mLoginUser != null)
      this.mLoginUser.userKey = paramString;
  }

  public void setOnlineUsersCnt(String paramString, int paramInt)
  {
    if (paramString == null)
      return;
    this.mapLiveRoomUserCnts.put(paramString, Integer.valueOf(paramInt));
  }

  public void setQualifyData(CustomData paramCustomData)
  {
    this.mQualifyData = paramCustomData;
  }

  public void setSongList(int paramInt, CustomData paramCustomData, String paramString)
  {
    if ((paramString == null) || (paramCustomData == null))
      break label8;
    label8: 
    while (paramInt != 1)
      return;
    if (this.mapLiveRoomSongs.containsKey(paramString))
    {
      List localList = (List)this.mapLiveRoomSongs.get(paramString);
      for (int i = 0; ; i++)
      {
        if (i >= localList.size())
          break label91;
        if (((ChatData)localList.get(i)).content.equalsIgnoreCase(((ChatData)paramCustomData).content))
          break;
      }
      label91: localList.add(paramCustomData);
      return;
    }
    ArrayList localArrayList = new ArrayList();
    localArrayList.add(paramCustomData);
    this.mapLiveRoomSongs.put(paramString, localArrayList);
  }

  public void unRegisterRoomDataEventListener(String paramString, IRoomDataEventListener paramIRoomDataEventListener)
  {
    List localList;
    if ((paramIRoomDataEventListener != null) && (this.mapRoomDataEventListeners.containsKey(paramString)))
    {
      localList = (List)this.mapRoomDataEventListeners.get(paramString);
      if (localList == null);
    }
    for (int i = 0; ; i++)
      if (i < localList.size())
      {
        if (localList.get(i) == paramIRoomDataEventListener)
          localList.remove(i);
      }
      else
        return;
  }

  public void unRegisterRoomStateEventListener(String paramString, IRoomStateListener paramIRoomStateListener)
  {
    List localList;
    if ((paramIRoomStateListener != null) && (this.mapRoomStateListeners.containsKey(paramString)))
    {
      localList = (List)this.mapRoomStateListeners.get(paramString);
      if (localList == null);
    }
    for (int i = 0; ; i++)
      if (i < localList.size())
      {
        if (localList.get(i) == paramIRoomStateListener)
          localList.remove(i);
      }
      else
        return;
  }

  private class DispatchHandler extends Handler
  {
    public DispatchHandler(Looper arg2)
    {
      super();
    }

    public void handleMessage(Message paramMessage)
    {
      switch (paramMessage.what)
      {
      default:
        return;
      case 0:
        RoomDataCenter.this.dispatchRoomDataEvent("RLRCD");
        return;
      case 1:
        RoomDataCenter.this.dispatchRoomStateEvent("RLRL");
        return;
      case 2:
        RoomDataCenter.this.dispatchRoomStateEvent("RLRJ");
        return;
      case 3:
        RoomDataCenter.this.dispatchRoomDataEvent("RLROU");
        return;
      case 4:
        RoomDataCenter.this.dispatchRoomDataEvent("RLRUE");
        return;
      case 5:
        RoomDataCenter.this.dispatchRoomDataEvent("RLRT");
        return;
      case 6:
      }
      RoomDataCenter.this.dispatchRoomDataEvent("RLRAS");
    }
  }

  private static enum DispatchMsg
  {
    static
    {
      RECV_LIVE_ROOM_JOIN = new DispatchMsg("RECV_LIVE_ROOM_JOIN", 2);
      RECV_LIVE_ROOM_ONLINE_USERS = new DispatchMsg("RECV_LIVE_ROOM_ONLINE_USERS", 3);
      RECV_LIVE_ROOM_USER_ENTER = new DispatchMsg("RECV_LIVE_ROOM_USER_ENTER", 4);
      RECV_LIVE_ROOM_TOPIC = new DispatchMsg("RECV_LIVE_ROOM_TOPIC", 5);
      RECV_LIVE_ROOM_ASKSONG = new DispatchMsg("RECV_LIVE_ROOM_ASKSONG", 6);
      RECV_LIVE_ROOM_TELLSONG = new DispatchMsg("RECV_LIVE_ROOM_TELLSONG", 7);
      DispatchMsg[] arrayOfDispatchMsg = new DispatchMsg[8];
      arrayOfDispatchMsg[0] = RECV_LIVE_ROOM_CHAT_DATA;
      arrayOfDispatchMsg[1] = RECV_LIVE_ROOM_LOGIN;
      arrayOfDispatchMsg[2] = RECV_LIVE_ROOM_JOIN;
      arrayOfDispatchMsg[3] = RECV_LIVE_ROOM_ONLINE_USERS;
      arrayOfDispatchMsg[4] = RECV_LIVE_ROOM_USER_ENTER;
      arrayOfDispatchMsg[5] = RECV_LIVE_ROOM_TOPIC;
      arrayOfDispatchMsg[6] = RECV_LIVE_ROOM_ASKSONG;
      arrayOfDispatchMsg[7] = RECV_LIVE_ROOM_TELLSONG;
    }
  }

  public static abstract interface IRoomDataEventListener
  {
    public static final String RECV_LIVE_ROOM_ASKSONG = "RLRAS";
    public static final String RECV_LIVE_ROOM_CHAT_DATA = "RLRCD";
    public static final String RECV_LIVE_ROOM_ONLINE_USERS = "RLROU";
    public static final String RECV_LIVE_ROOM_TELLSONG = "RLRTS";
    public static final String RECV_LIVE_ROOM_TOPIC = "RLRT";
    public static final String RECV_LIVE_ROOM_USER_ENTER = "RLRUE";

    public abstract void onRoomData(String paramString);
  }

  public static abstract interface IRoomStateListener
  {
    public static final String RECV_LIVE_ROOM_JOIN = "RLRJ";
    public static final String RECV_LIVE_ROOM_LOGIN = "RLRL";
    public static final String RECV_LIVE_ROOM_LOGOUT = "RLRLO";

    public abstract void onRoomState(String paramString);
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.qtradio.room.RoomDataCenter
 * JD-Core Version:    0.6.2
 */