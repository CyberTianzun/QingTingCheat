package fm.qingting.qtradio.room;

import fm.qingting.qtradio.model.ChannelNode;
import fm.qingting.qtradio.model.InfoManager;
import fm.qingting.qtradio.model.ProgramNode;
import java.util.List;

public class UserInfo
{
  public long fansNumber;
  public boolean isBlocked = false;
  public boolean isPodcaster = false;
  public long lastestUpdateTime;
  public int level = 0;
  private transient List<ChannelNode> mLstChannelNodes;
  private transient List<ProgramNode> mLstProgramNodes;
  public int podcasterId;
  public String podcasterName;
  public SnsInfo snsInfo = new SnsInfo();
  public String userId = InfoManager.getInstance().getDeviceId();
  public String userKey = "";
  public String userType = "normal";

  public List<ChannelNode> getChannelNodes()
  {
    return this.mLstChannelNodes;
  }

  public int getLevel()
  {
    return this.level;
  }

  public List<ProgramNode> getProgramNodes()
  {
    return this.mLstProgramNodes;
  }

  public String getUid()
  {
    return this.snsInfo.sns_site + this.snsInfo.sns_id;
  }

  public boolean isBlocked()
  {
    return this.isBlocked;
  }

  public void setBlocked(boolean paramBoolean)
  {
    this.isBlocked = paramBoolean;
  }

  public void setChannelNodes(List<ChannelNode> paramList)
  {
    this.mLstChannelNodes = paramList;
  }

  public void setProgramNodes(List<ProgramNode> paramList)
  {
    this.mLstProgramNodes = paramList;
  }

  public void swapUserInfo(UserInfo paramUserInfo)
  {
    if (paramUserInfo == null)
      return;
    String str1 = this.userId;
    this.userId = paramUserInfo.userId;
    paramUserInfo.userId = str1;
    String str2 = this.userKey;
    this.userKey = paramUserInfo.userKey;
    paramUserInfo.userKey = str2;
    int i = this.snsInfo.age;
    this.snsInfo.age = paramUserInfo.snsInfo.age;
    paramUserInfo.snsInfo.age = i;
    String str3 = this.snsInfo.signature;
    this.snsInfo.signature = paramUserInfo.snsInfo.signature;
    paramUserInfo.snsInfo.signature = str3;
    String str4 = this.snsInfo.sns_account;
    this.snsInfo.sns_account = paramUserInfo.snsInfo.sns_account;
    paramUserInfo.snsInfo.sns_account = str4;
    String str5 = this.snsInfo.sns_avatar;
    this.snsInfo.sns_avatar = paramUserInfo.snsInfo.sns_avatar;
    paramUserInfo.snsInfo.sns_avatar = str5;
    String str6 = this.snsInfo.sns_gender;
    this.snsInfo.sns_gender = paramUserInfo.snsInfo.sns_gender;
    paramUserInfo.snsInfo.sns_gender = str6;
    String str7 = this.snsInfo.sns_id;
    this.snsInfo.sns_id = paramUserInfo.snsInfo.sns_id;
    paramUserInfo.snsInfo.sns_id = str7;
    String str8 = this.snsInfo.sns_name;
    this.snsInfo.sns_name = paramUserInfo.snsInfo.sns_name;
    paramUserInfo.snsInfo.sns_name = str8;
    String str9 = this.snsInfo.sns_site;
    this.snsInfo.sns_site = paramUserInfo.snsInfo.sns_site;
    paramUserInfo.snsInfo.sns_site = str9;
    String str10 = this.snsInfo.source;
    this.snsInfo.source = paramUserInfo.snsInfo.source;
    paramUserInfo.snsInfo.source = str10;
    boolean bool1 = this.isBlocked;
    this.isBlocked = paramUserInfo.isBlocked;
    paramUserInfo.isBlocked = bool1;
    int j = this.level;
    this.level = paramUserInfo.level;
    paramUserInfo.level = j;
    boolean bool2 = this.isPodcaster;
    this.isPodcaster = paramUserInfo.isPodcaster;
    paramUserInfo.isPodcaster = bool2;
    int k = this.podcasterId;
    this.podcasterId = paramUserInfo.podcasterId;
    paramUserInfo.podcasterId = k;
  }

  public void updateUserInfo(UserInfo paramUserInfo)
  {
    if (paramUserInfo == null)
      return;
    this.userId = paramUserInfo.userId;
    this.userKey = paramUserInfo.userKey;
    this.podcasterName = paramUserInfo.podcasterName;
    this.fansNumber = paramUserInfo.fansNumber;
    this.snsInfo.age = paramUserInfo.snsInfo.age;
    this.snsInfo.signature = paramUserInfo.snsInfo.signature;
    this.snsInfo.sns_account = paramUserInfo.snsInfo.sns_account;
    this.snsInfo.sns_avatar = paramUserInfo.snsInfo.sns_avatar;
    this.snsInfo.sns_gender = paramUserInfo.snsInfo.sns_gender;
    this.snsInfo.sns_id = paramUserInfo.snsInfo.sns_id;
    this.snsInfo.sns_name = paramUserInfo.snsInfo.sns_name;
    this.snsInfo.sns_site = paramUserInfo.snsInfo.sns_site;
    this.snsInfo.source = paramUserInfo.snsInfo.source;
    this.snsInfo.desc = paramUserInfo.snsInfo.desc;
    this.isBlocked = paramUserInfo.isBlocked;
    this.level = paramUserInfo.level;
    this.isPodcaster = paramUserInfo.isPodcaster;
    this.podcasterId = paramUserInfo.podcasterId;
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.qtradio.room.UserInfo
 * JD-Core Version:    0.6.2
 */