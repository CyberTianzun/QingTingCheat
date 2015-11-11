package fm.qingting.qtradio.social;

import fm.qingting.framework.data.DataManager;
import fm.qingting.framework.data.IResultRecvHandler;
import fm.qingting.framework.data.IResultToken;
import fm.qingting.framework.data.Result;
import fm.qingting.qtradio.im.IMAgent;
import fm.qingting.qtradio.im.IMContacts;
import fm.qingting.qtradio.model.ChannelNode;
import fm.qingting.qtradio.model.CollectionNode;
import fm.qingting.qtradio.model.GlobalCfg;
import fm.qingting.qtradio.model.InfoManager;
import fm.qingting.qtradio.model.PersonalCenterNode;
import fm.qingting.qtradio.model.RootNode;
import fm.qingting.qtradio.model.RootNode.IInfoUpdateEventListener;
import fm.qingting.qtradio.room.RoomDataCenter;
import fm.qingting.qtradio.room.SnsInfo;
import fm.qingting.qtradio.room.TencentChat;
import fm.qingting.qtradio.room.UserInfo;
import fm.qingting.qtradio.room.WeiboChat;
import fm.qingting.qtradio.tencentAgent.TencentAgent;
import fm.qingting.qtradio.weiboAgent.WeiboAgent;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONObject;

public class CloudCenter
  implements IResultRecvHandler, RootNode.IInfoUpdateEventListener
{
  private static CloudCenter _instance;
  private int mIsLiveRoomAdmin = -1;
  private String mLiveRoomAdmin = "";
  private boolean mNeedSocialType = false;
  private String mPlayName = "";
  private UserInfo mRegisterUser;
  private int mSocialType = 0;
  private Map<String, List<IUserEventListener>> mapUserEventListeners = new HashMap();
  private Map<String, UserProfile> mapUserProfile = new HashMap();

  private CloudCenter()
  {
    init();
  }

  private void dispatchUserEvent(String paramString)
  {
    if (this.mapUserEventListeners.containsKey(paramString))
    {
      List localList = (List)this.mapUserEventListeners.get(paramString);
      int i = localList.size();
      for (int j = 0; j < i; j++)
        ((IUserEventListener)localList.get(j)).onUserNotification(paramString);
      Iterator localIterator = localList.iterator();
      for (int k = 0; (localIterator.hasNext()) && (k < i); k++)
      {
        localIterator.next();
        localIterator.remove();
      }
    }
  }

  public static CloudCenter getInstance()
  {
    if (_instance == null)
      _instance = new CloudCenter();
    return _instance;
  }

  private void handleReportUser(String paramString)
  {
    if ((paramString == null) || (!isLiveRoomAdmin()))
      return;
    UserInfo localUserInfo = new UserInfo();
    localUserInfo.userKey = paramString;
    localUserInfo.userId = paramString;
    IMAgent.getInstance().sendUserMsg("setblack", localUserInfo, 0);
  }

  private void init()
  {
    this.mSocialType = 0;
    this.mNeedSocialType = false;
    InfoManager.getInstance().root().registerInfoUpdateListener(this, 0);
  }

  private void log(String paramString)
  {
  }

  private void mergeMiniFav(List<MiniFavNode> paramList)
  {
    if (paramList == null)
      return;
    InfoManager.getInstance().root().mPersonalCenterNode.myCollectionNode.mergeWithFavNodes(paramList);
  }

  private void updateUserKey(String paramString)
  {
    if (this.mRegisterUser != null)
      this.mRegisterUser.userKey = paramString;
  }

  public void getGroupInfo(String paramString)
  {
  }

  public int getSocialType()
  {
    return this.mSocialType;
  }

  public UserProfile getUserProfile(String paramString)
  {
    if ((paramString == null) || (paramString.equalsIgnoreCase("")));
    while (!this.mapUserProfile.containsKey(paramString))
      return null;
    return (UserProfile)this.mapUserProfile.get(paramString);
  }

  public boolean hasAddFriendBySocialType()
  {
    return (0x1 & this.mSocialType) != 0;
  }

  public boolean hasAgreeAddFriendBySocialType()
  {
    return (0x2 & this.mSocialType) != 0;
  }

  public boolean hasChatMessaggBySocialType()
  {
    return (0x3 & this.mSocialType) != 0;
  }

  public boolean isLiveRoomAdmin()
  {
    if (this.mIsLiveRoomAdmin == 0)
      return false;
    if (this.mIsLiveRoomAdmin == 1)
      return true;
    if (this.mRegisterUser != null)
    {
      if (this.mLiveRoomAdmin.contains(this.mRegisterUser.userKey))
      {
        this.mIsLiveRoomAdmin = 1;
        return true;
      }
      this.mIsLiveRoomAdmin = 0;
    }
    this.mIsLiveRoomAdmin = 0;
    return false;
  }

  public boolean isLiveRoomAdmin(String paramString)
  {
    if (paramString == null);
    while (!this.mLiveRoomAdmin.contains(paramString))
      return false;
    return true;
  }

  public boolean isLogin()
  {
    return (WeiboAgent.getInstance().isLoggedIn().booleanValue()) || (TencentAgent.getInstance().isLoggedIn().booleanValue());
  }

  public void loadUserProfile(String paramString, IUserEventListener paramIUserEventListener)
  {
    if ((paramString == null) || (paramString.equalsIgnoreCase("")));
    while (true)
    {
      return;
      try
      {
        HashMap localHashMap = new HashMap();
        localHashMap.put("userkey", paramString);
        DataManager.getInstance().getData("get_social_user_data", this, localHashMap);
        if (paramIUserEventListener != null)
        {
          registerUserEventListener(paramIUserEventListener, "RUP");
          return;
        }
      }
      catch (Exception localException)
      {
        localException.printStackTrace();
      }
    }
  }

  public void login(int paramInt, OnLoginEventListerner paramOnLoginEventListerner)
  {
    if (paramInt == 2);
    try
    {
      TencentAgent.getInstance().login(paramOnLoginEventListerner);
      return;
      if (paramInt == 1)
      {
        WeiboAgent.getInstance().login(paramOnLoginEventListerner);
        return;
      }
    }
    catch (Exception localException)
    {
    }
  }

  public void logout()
  {
    TencentChat.getInstance().logout();
    WeiboChat.getInstance().logout();
    IMContacts.getInstance().clearAll();
    InfoManager.getInstance().getUserProfile().clearAll();
    GlobalCfg.getInstance(InfoManager.getInstance().getContext()).setActiveUserKey("");
    IMAgent.getInstance().logout();
  }

  public void onInfoUpdated(int paramInt)
  {
    if (paramInt == 0)
      uploadFavoriteChannelToCloud();
  }

  public void onRecvResult(Result paramResult, Object paramObject1, IResultToken paramIResultToken, Object paramObject2)
  {
    String str1 = paramIResultToken.getType();
    String str3;
    if (paramResult.getSuccess())
    {
      if (!str1.equalsIgnoreCase("create_user"))
        break label171;
      str3 = (String)paramResult.getData();
      RoomDataCenter.getInstance().setLoginUserKey(str3);
      updateUserKey(str3);
      if (!this.mRegisterUser.snsInfo.sns_site.equalsIgnoreCase("tencent"))
        break label156;
      InfoManager.getInstance().getUserProfile().setUserKey(str3, 1);
      InfoManager.getInstance().getUserProfile().init();
      InfoManager.getInstance().getUserProfile().loadUserInfo(str3, null);
      InfoManager.getInstance().getUserProfile().loadFollowers(null);
      InfoManager.getInstance().getUserProfile().loadFollowings(null);
      pullFavoriteChannelFromCloud(null);
      GlobalCfg.getInstance(InfoManager.getInstance().getContext()).setActiveUserKey(str3);
      InfoManager.getInstance().getUserProfile().updateUserInfo();
      IMAgent.getInstance().connect();
    }
    label156: label171: label238: 
    do
    {
      UserProfile localUserProfile;
      String str2;
      do
      {
        do
        {
          return;
          InfoManager.getInstance().getUserProfile().setUserKey(str3, 0);
          break;
          if (!str1.equalsIgnoreCase("get_social_user_data"))
            break label238;
          localUserProfile = (UserProfile)paramResult.getData();
        }
        while (localUserProfile == null);
        str2 = localUserProfile.getUserKey();
      }
      while ((str2 == null) || (str2.equalsIgnoreCase("")));
      this.mapUserProfile.put(str2, localUserProfile);
      dispatchUserEvent("RUP");
      return;
    }
    while (!str1.equalsIgnoreCase("pull_collection_data"));
    List localList = (List)paramResult.getData();
    if (localList != null)
    {
      mergeMiniFav(localList);
      uploadFavoriteChannelToCloud();
      return;
    }
    uploadFavoriteChannelToCloud();
  }

  public void pullFavoriteChannelFromCloud(IUserEventListener paramIUserEventListener)
  {
    if ((this.mRegisterUser == null) || (this.mRegisterUser.userKey == null) || (this.mRegisterUser.userKey.equalsIgnoreCase("")));
    while (true)
    {
      return;
      try
      {
        HashMap localHashMap = new HashMap();
        localHashMap.put("user", this.mRegisterUser.userKey);
        DataManager.getInstance().getData("pull_collection_data", this, localHashMap);
        if (paramIUserEventListener != null)
        {
          registerUserEventListener(paramIUserEventListener, "RFC");
          return;
        }
      }
      catch (Exception localException)
      {
        localException.printStackTrace();
      }
    }
  }

  public void registerUser(UserInfo paramUserInfo)
  {
    if (paramUserInfo == null)
    {
      this.mRegisterUser = null;
      return;
    }
    while (true)
    {
      HashMap localHashMap;
      try
      {
        localHashMap = new HashMap();
        if (paramUserInfo.snsInfo.sns_id == null)
          break;
        if (paramUserInfo.snsInfo.sns_site.equalsIgnoreCase("tencent"))
        {
          localHashMap.put("sns_id", paramUserInfo.snsInfo.sns_id);
          localHashMap.put("sns_type", "1");
          localHashMap.put("device_id", InfoManager.getInstance().getDeviceId());
          localHashMap.put("app", "Master");
          localHashMap.put("phone", "Android");
          DataManager.getInstance().getData("create_user", this, localHashMap);
          this.mRegisterUser = paramUserInfo;
          return;
        }
      }
      catch (Exception localException)
      {
        localException.printStackTrace();
        return;
      }
      localHashMap.put("sns_id", paramUserInfo.snsInfo.sns_id);
      localHashMap.put("sns_type", "0");
    }
  }

  public void registerUserEventListener(IUserEventListener paramIUserEventListener, String paramString)
  {
    if ((paramIUserEventListener != null) && (paramString != null))
    {
      if (this.mapUserEventListeners.containsKey(paramString))
        ((List)this.mapUserEventListeners.get(paramString)).add(paramIUserEventListener);
    }
    else
      return;
    ArrayList localArrayList = new ArrayList();
    localArrayList.add(paramIUserEventListener);
    this.mapUserEventListeners.put(paramString, localArrayList);
  }

  public void reportUser(String paramString1, String paramString2, int paramInt)
  {
    if (paramString2 == null)
      return;
    try
    {
      HashMap localHashMap1 = new HashMap();
      HashMap localHashMap2 = new HashMap();
      localHashMap2.put("informer", this.mRegisterUser.userKey);
      localHashMap2.put("blacker", paramString1);
      localHashMap2.put("reason", String.valueOf(paramInt));
      localHashMap2.put("last_message", paramString2);
      localHashMap1.put("postdata", localHashMap2);
      DataManager.getInstance().getData("report_user", this, localHashMap1);
      handleReportUser(paramString1);
      return;
    }
    catch (Exception localException)
    {
    }
  }

  public void requstGroup(String paramString)
  {
  }

  public void resetSocialType()
  {
    this.mSocialType = 0;
  }

  public void setLiveRoomAdmin(String paramString)
  {
    this.mLiveRoomAdmin = paramString;
  }

  public void setNeedSocialType(boolean paramBoolean)
  {
    this.mNeedSocialType = paramBoolean;
  }

  public void uploadFavoriteChannelToCloud()
  {
    if ((this.mRegisterUser == null) || (this.mRegisterUser.userKey == null) || (this.mRegisterUser.userKey.equalsIgnoreCase("")));
    List localList;
    do
    {
      return;
      localList = InfoManager.getInstance().root().mPersonalCenterNode.myCollectionNode.getFavouriteNodes();
    }
    while (localList == null);
    HashMap localHashMap1 = new HashMap();
    HashMap localHashMap2 = new HashMap();
    JSONArray localJSONArray = new JSONArray();
    int i = 0;
    while (true)
    {
      try
      {
        if (i < localList.size())
        {
          JSONObject localJSONObject = new JSONObject();
          localJSONObject.put("id", String.valueOf(((ChannelNode)localList.get(i)).channelId));
          localJSONObject.put("catid", String.valueOf(((ChannelNode)localList.get(i)).categoryId));
          localJSONObject.put("parentid", String.valueOf(((ChannelNode)localList.get(i)).channelId));
          localJSONObject.put("type", ((ChannelNode)localList.get(i)).channelType);
          localJSONObject.put("name", ((ChannelNode)localList.get(i)).title);
          localJSONArray.put(localJSONObject);
          i++;
          continue;
        }
        if (localList.size() == 0)
        {
          localHashMap2.put("value", "\"\"");
          localHashMap1.put("postdata", localHashMap2);
          localHashMap1.put("user", URLEncoder.encode(this.mRegisterUser.userKey, "UTF-8"));
          DataManager.getInstance().getData("set_user_data", this, localHashMap1);
          return;
        }
      }
      catch (Exception localException)
      {
        localException.printStackTrace();
        return;
      }
      localHashMap2.put("value", localJSONArray.toString());
    }
  }

  public static abstract interface IUserEventListener
  {
    public static final String RECV_ADD_FRIEND = "RAF";
    public static final String RECV_AGREE_ADD_FRIEND = "RAAF";
    public static final String RECV_CHAT_MESSAGE = "RCM";
    public static final String RECV_FAV_CHANNELS = "RFC";
    public static final String RECV_USER_PROFILE = "RUP";
    public static final String SOCIAL_LOGIN = "SL";

    public abstract void onUserNotification(String paramString);
  }

  public static abstract interface OnLoginEventListerner
  {
    public abstract void onLoginFailed(int paramInt);

    public abstract void onLoginSuccessed(int paramInt);
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.qtradio.social.CloudCenter
 * JD-Core Version:    0.6.2
 */