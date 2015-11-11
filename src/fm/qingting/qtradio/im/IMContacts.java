package fm.qingting.qtradio.im;

import fm.qingting.framework.data.DataManager;
import fm.qingting.framework.data.IResultToken;
import fm.qingting.framework.data.Result;
import fm.qingting.qtradio.im.info.GroupInfo;
import fm.qingting.qtradio.log.LogModule;
import fm.qingting.qtradio.logger.QTLogger;
import fm.qingting.qtradio.model.InfoManager;
import fm.qingting.qtradio.room.UserInfo;
import fm.qingting.qtradio.social.UserProfile;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class IMContacts
{
  private static IMContacts _instance;
  private int RECENT_CONTACTS_SIZE = 20;
  private List<GroupInfo> mLstRecentGroupInfo;
  private List<UserInfo> mLstRecentUserInfo;
  private List<GroupInfo> mLstWatchedGroupInfo;
  private List<UserInfo> mLstWatchedUserInfo;
  private boolean needToWriteRGToDB = false;
  private boolean needToWriteRUToDB = false;
  private boolean needToWriteWGToDB = false;
  private boolean needToWriteWUToDB = false;

  public static IMContacts getInstance()
  {
    if (_instance == null)
      _instance = new IMContacts();
    return _instance;
  }

  public void addRecentContacts(GroupInfo paramGroupInfo)
  {
    if (paramGroupInfo == null)
      return;
    if (this.mLstRecentGroupInfo == null)
      getRecentGroupContacts();
    for (int i = 0; ; i++)
    {
      if (i >= this.mLstRecentGroupInfo.size())
        break label64;
      if (paramGroupInfo.groupId.equalsIgnoreCase(((GroupInfo)this.mLstRecentGroupInfo.get(i)).groupId))
        break;
    }
    label64: if (this.mLstRecentGroupInfo.size() >= this.RECENT_CONTACTS_SIZE)
      this.mLstRecentGroupInfo.remove(-1 + this.RECENT_CONTACTS_SIZE);
    this.mLstRecentGroupInfo.add(0, paramGroupInfo);
    this.needToWriteRGToDB = true;
  }

  public void addRecentContacts(UserInfo paramUserInfo)
  {
    if (paramUserInfo == null)
      return;
    if (this.mLstRecentUserInfo == null)
      getRecentUserContacts();
    for (int i = 0; ; i++)
    {
      if (i >= this.mLstRecentUserInfo.size())
        break label64;
      if (paramUserInfo.userKey.equalsIgnoreCase(((UserInfo)this.mLstRecentUserInfo.get(i)).userKey))
        break;
    }
    label64: if (this.mLstRecentUserInfo.size() >= this.RECENT_CONTACTS_SIZE)
      this.mLstRecentUserInfo.remove(-1 + this.RECENT_CONTACTS_SIZE);
    this.mLstRecentUserInfo.add(0, paramUserInfo);
    this.needToWriteRUToDB = true;
  }

  public void addRecentContacts(String paramString)
  {
    if (paramString == null);
    label61: GroupInfo localGroupInfo;
    do
    {
      return;
      if (this.mLstRecentGroupInfo == null)
        getRecentGroupContacts();
      for (int i = 0; ; i++)
      {
        if (i >= this.mLstRecentGroupInfo.size())
          break label61;
        if (paramString.equalsIgnoreCase(((GroupInfo)this.mLstRecentGroupInfo.get(i)).groupId))
          break;
      }
      if (this.mLstRecentGroupInfo.size() >= this.RECENT_CONTACTS_SIZE)
        this.mLstRecentGroupInfo.remove(-1 + this.RECENT_CONTACTS_SIZE);
      localGroupInfo = IMAgent.getInstance().getGroupInfo(paramString);
    }
    while (localGroupInfo == null);
    this.mLstRecentGroupInfo.add(0, localGroupInfo);
    this.needToWriteRGToDB = true;
  }

  public void addRecentGroupContacts(String paramString1, String paramString2, String paramString3)
  {
    if (hasRecentGroupContacts(paramString1))
      return;
    GroupInfo localGroupInfo = new GroupInfo();
    localGroupInfo.groupId = paramString1;
    localGroupInfo.groupName = paramString2;
    this.mLstRecentGroupInfo.add(localGroupInfo);
    this.needToWriteRGToDB = true;
  }

  public void addRecentUserContacts(String paramString1, String paramString2, String paramString3)
  {
    if (hasRecentUserContacts(paramString1))
      return;
    UserInfo localUserInfo = new UserInfo();
    localUserInfo.userKey = paramString1;
    localUserInfo.snsInfo.sns_name = paramString2;
    localUserInfo.snsInfo.sns_avatar = paramString3;
    this.mLstRecentUserInfo.add(localUserInfo);
    this.needToWriteRUToDB = true;
  }

  public void addWatchContacts(GroupInfo paramGroupInfo)
  {
    if (paramGroupInfo == null)
      return;
    if (this.mLstWatchedGroupInfo == null)
      getWatchedGroupContacts();
    for (int i = 0; ; i++)
    {
      if (i >= this.mLstWatchedGroupInfo.size())
        break label64;
      if (paramGroupInfo.groupId.equalsIgnoreCase(((GroupInfo)this.mLstWatchedGroupInfo.get(i)).groupId))
        break;
    }
    label64: String str1 = InfoManager.getInstance().getUserProfile().getUserKey();
    if (str1 != null)
    {
      String str2 = QTLogger.getInstance().buildIMGroupRelationLog(str1, paramGroupInfo.groupId, 1);
      if (str2 != null)
        LogModule.getInstance().send("GroupRelation", str2);
    }
    this.mLstWatchedGroupInfo.add(0, paramGroupInfo);
    this.needToWriteWGToDB = true;
  }

  public void addWatchContacts(UserInfo paramUserInfo)
  {
    if (paramUserInfo == null)
      return;
    if (this.mLstWatchedUserInfo == null)
      getWatchedUserContacts();
    for (int i = 0; ; i++)
    {
      if (i >= this.mLstWatchedUserInfo.size())
        break label64;
      if (paramUserInfo.userKey.equalsIgnoreCase(((UserInfo)this.mLstWatchedUserInfo.get(i)).userKey))
        break;
    }
    label64: String str1 = InfoManager.getInstance().getUserProfile().getUserKey();
    if (str1 != null)
    {
      String str2 = QTLogger.getInstance().buildIMUserRelationLog(str1, paramUserInfo.userKey, 1);
      if (str2 != null)
        LogModule.getInstance().send("UserRelation", str2);
    }
    this.mLstWatchedUserInfo.add(0, paramUserInfo);
    this.needToWriteWUToDB = true;
  }

  public void clearAll()
  {
    if (this.mLstWatchedUserInfo != null)
      this.mLstWatchedUserInfo.clear();
    if (this.mLstWatchedGroupInfo != null)
      this.mLstWatchedGroupInfo.clear();
    if (this.mLstRecentGroupInfo != null)
      this.mLstRecentGroupInfo.clear();
    if (this.mLstRecentUserInfo != null)
      this.mLstRecentUserInfo.clear();
    DataManager.getInstance().getData("deletedb_im_contacts", null, null);
  }

  public List<GroupInfo> getRecentGroupContacts()
  {
    if (this.mLstRecentGroupInfo == null)
    {
      HashMap localHashMap = new HashMap();
      localHashMap.put("type", "rg");
      Result localResult = DataManager.getInstance().getData("getdb_im_group_contacts", null, localHashMap).getResult();
      if (localResult.getSuccess())
        this.mLstRecentGroupInfo = ((List)localResult.getData());
    }
    return this.mLstRecentGroupInfo;
  }

  public List<UserInfo> getRecentUserContacts()
  {
    if (this.mLstRecentUserInfo == null)
    {
      HashMap localHashMap = new HashMap();
      localHashMap.put("type", "ru");
      Result localResult = DataManager.getInstance().getData("getdb_im_user_contacts", null, localHashMap).getResult();
      if (localResult.getSuccess())
        this.mLstRecentUserInfo = ((List)localResult.getData());
    }
    return this.mLstRecentUserInfo;
  }

  public GroupInfo getWatchedGroup(String paramString)
  {
    if (paramString == null)
      return null;
    if (this.mLstWatchedGroupInfo == null)
      getWatchedGroupContacts();
    for (int i = 0; i < this.mLstWatchedGroupInfo.size(); i++)
      if (((GroupInfo)this.mLstWatchedGroupInfo.get(i)).groupId.equalsIgnoreCase(paramString))
        return (GroupInfo)this.mLstWatchedGroupInfo.get(i);
    return null;
  }

  public List<GroupInfo> getWatchedGroupContacts()
  {
    if (this.mLstWatchedGroupInfo == null)
    {
      HashMap localHashMap = new HashMap();
      localHashMap.put("type", "wg");
      Result localResult = DataManager.getInstance().getData("getdb_im_group_contacts", null, localHashMap).getResult();
      if (localResult.getSuccess())
        this.mLstWatchedGroupInfo = ((List)localResult.getData());
    }
    return this.mLstWatchedGroupInfo;
  }

  public List<UserInfo> getWatchedUserContacts()
  {
    if (this.mLstWatchedUserInfo == null)
    {
      HashMap localHashMap = new HashMap();
      localHashMap.put("type", "wu");
      Result localResult = DataManager.getInstance().getData("getdb_im_user_contacts", null, localHashMap).getResult();
      if (localResult.getSuccess())
        this.mLstWatchedUserInfo = ((List)localResult.getData());
    }
    return this.mLstWatchedUserInfo;
  }

  public boolean hasRecentGroupContacts(String paramString)
  {
    if (paramString == null);
    while (true)
    {
      return false;
      if (this.mLstRecentGroupInfo == null)
        getRecentGroupContacts();
      for (int i = 0; i < this.mLstRecentGroupInfo.size(); i++)
        if (paramString.equalsIgnoreCase(((GroupInfo)this.mLstRecentGroupInfo.get(i)).groupId))
          return true;
    }
  }

  public boolean hasRecentUserContacts(String paramString)
  {
    if (paramString == null);
    while (true)
    {
      return false;
      if (this.mLstRecentUserInfo == null)
        getRecentUserContacts();
      for (int i = 0; i < this.mLstRecentUserInfo.size(); i++)
        if (paramString.equalsIgnoreCase(((UserInfo)this.mLstRecentUserInfo.get(i)).userKey))
          return true;
    }
  }

  public boolean hasWatchedGroup(String paramString)
  {
    if (paramString == null);
    while (true)
    {
      return false;
      if (this.mLstWatchedGroupInfo == null)
        getWatchedGroupContacts();
      for (int i = 0; i < this.mLstWatchedGroupInfo.size(); i++)
        if (((GroupInfo)this.mLstWatchedGroupInfo.get(i)).groupId.equalsIgnoreCase(paramString))
          return true;
    }
  }

  public boolean hasWatchedUser(String paramString)
  {
    if (paramString == null);
    while (true)
    {
      return false;
      if (this.mLstWatchedUserInfo == null)
        getWatchedUserContacts();
      String str = InfoManager.getInstance().getUserProfile().getUserKey();
      if ((str != null) && (str.equalsIgnoreCase(paramString)))
        return true;
      for (int i = 0; i < this.mLstWatchedUserInfo.size(); i++)
        if (((UserInfo)this.mLstWatchedUserInfo.get(i)).userKey.equalsIgnoreCase(paramString))
          return true;
    }
  }

  public void init()
  {
  }

  public boolean needToWriteRGToDB()
  {
    return this.needToWriteRGToDB;
  }

  public boolean needToWriteRUToDB()
  {
    return this.needToWriteRUToDB;
  }

  public boolean needToWriteWGToDB()
  {
    return this.needToWriteWGToDB;
  }

  public boolean needToWriteWUToDB()
  {
    return this.needToWriteWUToDB;
  }

  public void removeRecentGroupContact(String paramString)
  {
    if (paramString == null)
      return;
    if (this.mLstRecentGroupInfo == null)
      getRecentGroupContacts();
    for (int i = 0; ; i++)
      if (i < this.mLstRecentGroupInfo.size())
      {
        if (paramString.equalsIgnoreCase(((GroupInfo)this.mLstRecentGroupInfo.get(i)).groupId))
          this.mLstRecentGroupInfo.remove(i);
      }
      else
      {
        this.needToWriteRGToDB = true;
        return;
      }
  }

  public void removeRecentUserContact(String paramString)
  {
    if (paramString == null)
      return;
    if (this.mLstRecentUserInfo == null)
      getRecentUserContacts();
    for (int i = 0; ; i++)
      if (i < this.mLstRecentUserInfo.size())
      {
        if (paramString.equalsIgnoreCase(((UserInfo)this.mLstRecentUserInfo.get(i)).userKey))
          this.mLstRecentUserInfo.remove(i);
      }
      else
      {
        this.needToWriteRUToDB = true;
        return;
      }
  }

  public void unWatchGroupContacts(String paramString)
  {
    if (paramString == null);
    while (true)
    {
      return;
      for (int i = 0; i < this.mLstWatchedGroupInfo.size(); i++)
        if (((GroupInfo)this.mLstWatchedGroupInfo.get(i)).groupId.equalsIgnoreCase(paramString))
        {
          this.mLstWatchedGroupInfo.remove(i);
          String str1 = InfoManager.getInstance().getUserProfile().getUserKey();
          if (str1 != null)
          {
            String str2 = QTLogger.getInstance().buildIMGroupRelationLog(str1, paramString, 0);
            if (str2 != null)
              LogModule.getInstance().send("GroupRelation", str2);
          }
          this.needToWriteWGToDB = true;
          return;
        }
    }
  }

  public void unWatchUserContacts(String paramString)
  {
    if (paramString == null);
    while (true)
    {
      return;
      for (int i = 0; i < this.mLstWatchedUserInfo.size(); i++)
        if (((UserInfo)this.mLstWatchedUserInfo.get(i)).userKey.equalsIgnoreCase(paramString))
        {
          this.mLstWatchedUserInfo.remove(i);
          String str1 = InfoManager.getInstance().getUserProfile().getUserKey();
          if (str1 != null)
          {
            String str2 = QTLogger.getInstance().buildIMUserRelationLog(str1, paramString, 0);
            if (str2 != null)
              LogModule.getInstance().send("UserRelation", str2);
          }
          this.needToWriteWUToDB = true;
          return;
        }
    }
  }

  public void updateGroupInfo()
  {
    getWatchedGroupContacts();
    if (this.mLstWatchedGroupInfo != null)
      for (int j = 0; j < this.mLstWatchedGroupInfo.size(); j++)
        IMAgent.getInstance().loadGroupInfo(((GroupInfo)this.mLstWatchedGroupInfo.get(j)).groupId, null);
    getRecentGroupContacts();
    List localList = this.mLstRecentGroupInfo;
    int i = 0;
    if (localList != null)
      while (i < this.mLstRecentGroupInfo.size())
      {
        IMAgent.getInstance().loadGroupInfo(((GroupInfo)this.mLstRecentGroupInfo.get(i)).groupId, null);
        i++;
      }
  }

  public void updateGroupInfo(String paramString)
  {
    if (paramString == null);
    while (true)
    {
      return;
      GroupInfo localGroupInfo = IMAgent.getInstance().getGroupInfo(paramString);
      if (localGroupInfo != null)
        for (int i = 0; i < this.mLstWatchedGroupInfo.size(); i++)
          if (((GroupInfo)this.mLstWatchedGroupInfo.get(i)).groupId.equalsIgnoreCase(paramString))
          {
            ((GroupInfo)this.mLstWatchedGroupInfo.get(i)).update(localGroupInfo);
            this.needToWriteWGToDB = true;
            return;
          }
    }
  }

  public void writeToDB()
  {
    if (this.needToWriteRGToDB)
    {
      this.needToWriteRGToDB = false;
      HashMap localHashMap4 = new HashMap();
      localHashMap4.put("rg", this.mLstRecentGroupInfo);
      localHashMap4.put("type", "rg");
      DataManager.getInstance().getData("updatedb_im_contacts", null, localHashMap4);
    }
    if (this.needToWriteRUToDB)
    {
      this.needToWriteRUToDB = false;
      HashMap localHashMap3 = new HashMap();
      localHashMap3.put("ru", this.mLstRecentUserInfo);
      localHashMap3.put("type", "ru");
      DataManager.getInstance().getData("updatedb_im_contacts", null, localHashMap3);
    }
    if (this.needToWriteWGToDB)
    {
      this.needToWriteWGToDB = false;
      HashMap localHashMap2 = new HashMap();
      localHashMap2.put("wg", this.mLstWatchedGroupInfo);
      localHashMap2.put("type", "wg");
      DataManager.getInstance().getData("updatedb_im_contacts", null, localHashMap2);
    }
    if (this.needToWriteWUToDB)
    {
      this.needToWriteWUToDB = false;
      HashMap localHashMap1 = new HashMap();
      localHashMap1.put("wu", this.mLstWatchedUserInfo);
      localHashMap1.put("type", "wu");
      DataManager.getInstance().getData("updatedb_im_contacts", null, localHashMap1);
    }
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.qtradio.im.IMContacts
 * JD-Core Version:    0.6.2
 */