package fm.qingting.qtradio.social;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import fm.qingting.framework.data.IResultRecvHandler;
import fm.qingting.framework.data.IResultToken;
import fm.qingting.framework.data.Result;
import fm.qingting.qtradio.im.IMAgent;
import fm.qingting.qtradio.im.IMContacts;
import fm.qingting.qtradio.im.IMDataLoadWrapper;
import fm.qingting.qtradio.im.info.GroupInfo;
import fm.qingting.qtradio.model.InfoManager;
import fm.qingting.qtradio.model.Node;
import fm.qingting.qtradio.model.RootNode;
import fm.qingting.qtradio.model.RootNode.IInfoUpdateEventListener;
import fm.qingting.qtradio.model.SharedCfg;
import fm.qingting.qtradio.room.SnsInfo;
import fm.qingting.qtradio.room.UserInfo;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserProfile
  implements IResultRecvHandler
{
  public List<Node> lstFavNodes;
  private List<UserInfo> lstFollowers;
  private List<UserInfo> lstFollowings;
  private List<GroupInfo> lstGroups;
  private int mFollowerCnt;
  private int mFollowingCnt;
  private UserInfo mUserInfo;
  private String mUserKey = "";

  private void addGroup(GroupInfo paramGroupInfo)
  {
    if (paramGroupInfo == null)
      return;
    if (this.lstGroups == null)
      this.lstGroups = new ArrayList();
    for (int i = 0; ; i++)
    {
      if (i >= this.lstGroups.size())
        break label70;
      if (((GroupInfo)this.lstGroups.get(i)).groupId.equalsIgnoreCase(paramGroupInfo.groupId))
        break;
    }
    label70: this.lstGroups.add(paramGroupInfo);
  }

  private void log(String paramString)
  {
  }

  private UserInfo parseUserInfo(JSONObject paramJSONObject)
  {
    if (paramJSONObject != null)
    {
      UserInfo localUserInfo = new UserInfo();
      localUserInfo.userKey = paramJSONObject.getString("userId");
      localUserInfo.snsInfo.sns_avatar = paramJSONObject.getString("avatar");
      localUserInfo.snsInfo.sns_name = paramJSONObject.getString("userName");
      localUserInfo.snsInfo.signature = paramJSONObject.getString("signature");
      localUserInfo.snsInfo.age = paramJSONObject.getIntValue("age");
      localUserInfo.snsInfo.sns_gender = paramJSONObject.getString("gender");
      String str1 = paramJSONObject.getString("is_blocked");
      if ((str1 != null) && (!str1.equalsIgnoreCase("")))
        if (Integer.valueOf(str1).intValue() != 0)
          break label159;
      label159: for (localUserInfo.isBlocked = false; ; localUserInfo.isBlocked = true)
      {
        String str2 = paramJSONObject.getString("level");
        if ((str2 != null) && (!str2.equalsIgnoreCase("")))
          localUserInfo.level = Integer.valueOf(str2).intValue();
        return localUserInfo;
      }
    }
    return null;
  }

  public void clearAll()
  {
    if (this.lstFollowers != null)
      this.lstFollowers.clear();
    if (this.lstFollowings != null)
      this.lstFollowings.clear();
    if (this.lstGroups != null)
      this.lstGroups.clear();
    this.mFollowerCnt = 0;
    this.mFollowingCnt = 0;
  }

  public void followGroup(String paramString)
  {
    if ((paramString != null) && (this.mUserKey != null) && (!this.mUserKey.equalsIgnoreCase("")))
      IMDataLoadWrapper.addGroup(this.mUserKey, paramString, this);
    GroupInfo localGroupInfo = IMAgent.getInstance().getGroupInfo(paramString);
    if (localGroupInfo == null)
      IMAgent.getInstance().loadGroupInfo(paramString, null);
    while (true)
    {
      if ((this.lstGroups != null) && (localGroupInfo != null))
        this.lstGroups.add(localGroupInfo);
      return;
      IMContacts.getInstance().addWatchContacts(localGroupInfo);
    }
  }

  public void followUser(UserInfo paramUserInfo)
  {
    if (paramUserInfo == null);
    do
    {
      return;
      if ((paramUserInfo.userKey != null) && (this.mUserKey != null) && (!this.mUserKey.equalsIgnoreCase("")))
        IMDataLoadWrapper.followUser(this.mUserKey, paramUserInfo.userKey, this);
      IMContacts.getInstance().addWatchContacts(paramUserInfo);
    }
    while ((this.lstFollowings == null) || (paramUserInfo == null));
    this.lstFollowings.add(paramUserInfo);
  }

  public int getContactsCnt()
  {
    List localList = this.lstFollowers;
    int i = 0;
    if (localList != null)
      i = 0 + this.lstFollowers.size();
    if (this.lstFollowings != null)
      i += this.lstFollowings.size();
    return i;
  }

  public int getFollowerCnt()
  {
    if (this.lstFollowers == null)
      return 0;
    return this.lstFollowers.size();
  }

  public List<UserInfo> getFollowers()
  {
    return this.lstFollowers;
  }

  public int getFollowingCnt()
  {
    if (this.lstFollowings == null)
      return 0;
    return this.lstFollowings.size();
  }

  public List<UserInfo> getFollowings()
  {
    return this.lstFollowings;
  }

  public int getGroupCnt()
  {
    if (this.lstGroups == null)
      return 0;
    return this.lstGroups.size();
  }

  public List<GroupInfo> getGroups()
  {
    return this.lstGroups;
  }

  public UserInfo getUserInfo()
  {
    return this.mUserInfo;
  }

  public String getUserKey()
  {
    return this.mUserKey;
  }

  public void init()
  {
    List localList1 = IMContacts.getInstance().getWatchedGroupContacts();
    if ((localList1 != null) && (localList1.size() > 0))
    {
      if (this.lstGroups == null)
        this.lstGroups = new ArrayList();
      for (int j = 0; j < localList1.size(); j++)
        this.lstGroups.add(localList1.get(j));
    }
    List localList2 = IMContacts.getInstance().getWatchedUserContacts();
    if ((localList2 != null) && (localList2.size() > 0))
    {
      List localList3 = this.lstFollowings;
      int i = 0;
      if (localList3 == null)
        this.lstFollowings = new ArrayList();
      while (i < localList2.size())
      {
        this.lstFollowings.add(localList2.get(i));
        IMAgent.getInstance().addBaseUserInfo(((UserInfo)localList2.get(i)).userKey, ((UserInfo)localList2.get(i)).snsInfo.sns_avatar, ((UserInfo)localList2.get(i)).snsInfo.sns_gender);
        i++;
      }
    }
  }

  public boolean isPhoneOwner()
  {
    if (this.mUserKey == null);
    String str;
    do
    {
      return false;
      str = InfoManager.getInstance().getUserProfile().getUserKey();
    }
    while ((str == null) || (!str.equalsIgnoreCase(this.mUserKey)));
    return true;
  }

  public void loadFollowers(RootNode.IInfoUpdateEventListener paramIInfoUpdateEventListener)
  {
    if (this.mUserKey != null)
      IMDataLoadWrapper.loadFollowerList(this.mUserKey, this);
    if (paramIInfoUpdateEventListener != null)
      InfoManager.getInstance().root().registerInfoUpdateListener(paramIInfoUpdateEventListener, 4);
  }

  public void loadFollowings(RootNode.IInfoUpdateEventListener paramIInfoUpdateEventListener)
  {
    if (this.mUserKey != null)
      IMDataLoadWrapper.loadFollowingList(this.mUserKey, this);
    if (paramIInfoUpdateEventListener != null)
      InfoManager.getInstance().root().registerInfoUpdateListener(paramIInfoUpdateEventListener, 5);
  }

  public void loadUserInfo(String paramString, RootNode.IInfoUpdateEventListener paramIInfoUpdateEventListener)
  {
    IMDataLoadWrapper.loadUserInfo(paramString, this);
    if (paramIInfoUpdateEventListener != null)
      InfoManager.getInstance().root().registerInfoUpdateListener(paramIInfoUpdateEventListener, 3);
  }

  public void onRecvResult(Result paramResult, Object paramObject1, IResultToken paramIResultToken, Object paramObject2)
  {
    int i = 0;
    String str1 = paramIResultToken.getType();
    if (paramResult.getSuccess());
    while (true)
    {
      int j;
      try
      {
        int k;
        if (str1.equalsIgnoreCase("get_user_followers"))
        {
          String str9 = (String)((HashMap)paramObject2).get("user");
          if ((str9 != null) && (this.mUserKey != null))
          {
            if (!str9.equalsIgnoreCase(this.mUserKey))
              return;
            if (this.lstFollowers == null)
            {
              this.lstFollowers = new ArrayList();
              JSONArray localJSONArray3 = ((JSONObject)paramResult.getData()).getJSONArray("data");
              k = 0;
              if (localJSONArray3 == null)
                continue;
              if (k < localJSONArray3.size())
              {
                UserInfo localUserInfo2 = parseUserInfo(localJSONArray3.getJSONObject(k));
                if (localUserInfo2 == null)
                  continue;
                this.lstFollowers.add(localUserInfo2);
                continue;
              }
            }
            else
            {
              this.lstFollowers.clear();
              continue;
            }
            this.mFollowerCnt = this.lstFollowers.size();
            InfoManager.getInstance().root().setInfoUpdate(4);
          }
        }
        else if (str1.equalsIgnoreCase("get_user_followings"))
        {
          String str8 = (String)((HashMap)paramObject2).get("user");
          if ((str8 != null) && (this.mUserKey != null) && (str8.equalsIgnoreCase(this.mUserKey)))
          {
            if (this.lstFollowings == null)
            {
              this.lstFollowings = new ArrayList();
              JSONArray localJSONArray2 = ((JSONObject)paramResult.getData()).getJSONArray("data");
              if (localJSONArray2 == null)
                continue;
              boolean bool2 = isPhoneOwner();
              if (i < localJSONArray2.size())
              {
                UserInfo localUserInfo1 = parseUserInfo(localJSONArray2.getJSONObject(i));
                if (localUserInfo1 == null)
                  break label893;
                this.lstFollowings.add(localUserInfo1);
                if (!bool2)
                  break label893;
                IMContacts.getInstance().addWatchContacts(localUserInfo1);
                break label893;
              }
            }
            else
            {
              this.lstFollowings.clear();
              continue;
            }
            this.mFollowingCnt = this.lstFollowings.size();
            InfoManager.getInstance().root().setInfoUpdate(5);
          }
        }
        else if (str1.equalsIgnoreCase("get_user_info"))
        {
          String str2 = (String)((HashMap)paramObject2).get("user");
          if ((str2 != null) && (this.mUserKey != null) && (str2.equalsIgnoreCase(this.mUserKey)))
          {
            JSONObject localJSONObject1 = ((JSONObject)paramResult.getData()).getJSONObject("data");
            if ((localJSONObject1 != null) && (this.mUserKey != null))
            {
              if (this.mUserInfo == null)
                this.mUserInfo = new UserInfo();
              this.mUserInfo.userKey = this.mUserKey;
              String str3 = localJSONObject1.getString("avatar");
              if ((str3 != null) && (!str3.equalsIgnoreCase("")))
                this.mUserInfo.snsInfo.sns_avatar = str3;
              String str4 = localJSONObject1.getString("userName");
              if ((str4 != null) && (!str4.equalsIgnoreCase("")))
                this.mUserInfo.snsInfo.sns_name = str4;
              String str5 = localJSONObject1.getString("signature");
              if ((str5 != null) && (!str5.equalsIgnoreCase("")))
                this.mUserInfo.snsInfo.signature = str5;
              String str6 = localJSONObject1.getString("is_blocked");
              boolean bool1;
              if ((str6 != null) && (!str6.equalsIgnoreCase("")))
              {
                if (Integer.valueOf(str6).intValue() == 0)
                  this.mUserInfo.isBlocked = false;
              }
              else
              {
                String str7 = localJSONObject1.getString("level");
                if ((str7 != null) && (!str7.equalsIgnoreCase("")))
                  this.mUserInfo.level = Integer.valueOf(str7).intValue();
                this.mFollowerCnt = localJSONObject1.getIntValue("num_of_follower");
                this.mFollowingCnt = localJSONObject1.getIntValue("num_of_following");
                this.mUserInfo.snsInfo.sns_gender = localJSONObject1.getString("gender");
                JSONArray localJSONArray1 = localJSONObject1.getJSONArray("groups");
                if (localJSONArray1 == null)
                  continue;
                if (this.lstGroups == null)
                  this.lstGroups = new ArrayList();
                bool1 = isPhoneOwner();
                j = 0;
                if (j >= localJSONArray1.size())
                  continue;
                GroupInfo localGroupInfo = new GroupInfo();
                JSONObject localJSONObject2 = localJSONArray1.getJSONObject(j);
                localGroupInfo.groupId = localJSONObject2.getString("id");
                localGroupInfo.groupName = localJSONObject2.getString("groupName");
                localGroupInfo.groupDesc = localJSONObject2.getString("description");
                addGroup(localGroupInfo);
                if (!bool1)
                  break label899;
                IMContacts.getInstance().addWatchContacts(localGroupInfo);
                break label899;
              }
              this.mUserInfo.isBlocked = true;
              continue;
              if (bool1)
                IMContacts.getInstance().updateGroupInfo();
              InfoManager.getInstance().root().setInfoUpdate(3);
            }
          }
        }
        return;
        k++;
        continue;
      }
      catch (Exception localException)
      {
        return;
      }
      label893: i++;
      continue;
      label899: j++;
    }
  }

  public void setUserInfo(UserInfo paramUserInfo)
  {
    this.mUserInfo = paramUserInfo;
    if (paramUserInfo != null)
      this.mUserKey = paramUserInfo.userKey;
  }

  public void setUserKey(String paramString)
  {
    this.mUserKey = paramString;
  }

  public void setUserKey(String paramString, int paramInt)
  {
    this.mUserKey = paramString;
    if (paramInt == 0)
      SharedCfg.getInstance().setWeiboSocialUserKey(paramString);
    while (paramInt != 1)
      return;
    SharedCfg.getInstance().setTencentSocialUserKey(paramString);
  }

  public void unfollowGroup(String paramString)
  {
    if ((paramString != null) && (this.mUserKey != null) && (!this.mUserKey.equalsIgnoreCase("")))
      IMDataLoadWrapper.exitGroup(this.mUserKey, paramString, this);
    IMContacts.getInstance().unWatchGroupContacts(paramString);
    if (this.lstGroups != null);
    for (int i = 0; ; i++)
      if (i < this.lstGroups.size())
      {
        if (((GroupInfo)this.lstGroups.get(i)).groupId.equalsIgnoreCase(paramString))
          this.lstGroups.remove(i);
      }
      else
      {
        IMAgent.getInstance().leaveGroup(paramString);
        return;
      }
  }

  public void unfollowUser(String paramString)
  {
    if ((paramString != null) && (this.mUserKey != null) && (!this.mUserKey.equalsIgnoreCase("")))
      IMDataLoadWrapper.unFollowUser(this.mUserKey, paramString, this);
    IMContacts.getInstance().unWatchUserContacts(paramString);
    if ((this.lstFollowings != null) && (paramString != null));
    for (int i = 0; ; i++)
      if (i < this.lstFollowings.size())
      {
        if (((UserInfo)this.lstFollowings.get(i)).userKey.equalsIgnoreCase(paramString))
          this.lstFollowings.remove(i);
      }
      else
        return;
  }

  public void updateUserInfo()
  {
    IMDataLoadWrapper.updateUserInfo(this, this);
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.qtradio.social.UserProfile
 * JD-Core Version:    0.6.2
 */