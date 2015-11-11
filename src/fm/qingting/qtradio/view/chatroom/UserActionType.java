package fm.qingting.qtradio.view.chatroom;

import java.util.ArrayList;
import java.util.List;

public enum UserActionType
{
  static
  {
    PROFILE = new UserActionType("PROFILE", 1);
    HISTORY = new UserActionType("HISTORY", 2);
    FOLLOW = new UserActionType("FOLLOW", 3);
    CANCEL = new UserActionType("CANCEL", 4);
    UserActionType[] arrayOfUserActionType = new UserActionType[5];
    arrayOfUserActionType[0] = SAYTOIT;
    arrayOfUserActionType[1] = PROFILE;
    arrayOfUserActionType[2] = HISTORY;
    arrayOfUserActionType[3] = FOLLOW;
    arrayOfUserActionType[4] = CANCEL;
  }

  public static List<UserActionType> getDjActionTypes()
  {
    ArrayList localArrayList = new ArrayList();
    localArrayList.add(SAYTOIT);
    localArrayList.add(FOLLOW);
    localArrayList.add(CANCEL);
    return localArrayList;
  }

  public static List<UserActionType> getSimpleUserActionTypes()
  {
    ArrayList localArrayList = new ArrayList();
    localArrayList.add(SAYTOIT);
    localArrayList.add(HISTORY);
    localArrayList.add(CANCEL);
    return localArrayList;
  }

  public static List<UserActionType> getUserActionTypes()
  {
    ArrayList localArrayList = new ArrayList();
    localArrayList.add(SAYTOIT);
    localArrayList.add(PROFILE);
    localArrayList.add(HISTORY);
    localArrayList.add(CANCEL);
    return localArrayList;
  }

  public String getName()
  {
    switch (1.$SwitchMap$fm$qingting$qtradio$view$chatroom$UserActionType[ordinal()])
    {
    case 1:
    default:
      return "对Ta说";
    case 2:
      return "查看资料";
    case 3:
      return "消息记录";
    case 4:
      return "取消";
    case 5:
    }
    return "关注Ta";
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.qtradio.view.chatroom.UserActionType
 * JD-Core Version:    0.6.2
 */