package fm.qingting.qtradio.im;

import fm.qingting.framework.data.IResultRecvHandler;
import fm.qingting.qtradio.room.SnsInfo;
import fm.qingting.qtradio.room.UserInfo;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

public class BaseUserInfoPool
{
  public static final String INVALIDATEAVATAR = "invalidateAvatar";
  private static HashMap<String, AvatarAndGender> sBaseInfos = new HashMap();
  private static HashSet<OnBaseUserinfoPutListener> sListeners;
  private static HashSet<String> sLoadedIds = new HashSet();

  public static void addListener(OnBaseUserinfoPutListener paramOnBaseUserinfoPutListener)
  {
    if (sListeners == null)
      sListeners = new HashSet();
    sListeners.add(paramOnBaseUserinfoPutListener);
  }

  private static void dispatchAvatarPutEvent(String paramString, AvatarAndGender paramAvatarAndGender)
  {
    if (sListeners == null);
    while (true)
    {
      return;
      Iterator localIterator = sListeners.iterator();
      while (localIterator.hasNext())
        ((OnBaseUserinfoPutListener)localIterator.next()).onBaseInfoPut(paramString, paramAvatarAndGender);
    }
  }

  public static String getAvatar(String paramString)
  {
    if (paramString == null)
      return null;
    AvatarAndGender localAvatarAndGender = (AvatarAndGender)sBaseInfos.get(paramString);
    if (localAvatarAndGender != null)
      return localAvatarAndGender.getAvatar();
    return null;
  }

  public static String getGender(String paramString)
  {
    if (paramString == null)
      return null;
    AvatarAndGender localAvatarAndGender = (AvatarAndGender)sBaseInfos.get(paramString);
    if (localAvatarAndGender != null)
      return localAvatarAndGender.getGender();
    return null;
  }

  private static void loadBaseInfo(String paramString, IResultRecvHandler paramIResultRecvHandler)
  {
    if (paramString == null);
    while ((sLoadedIds.contains(paramString)) || (sBaseInfos.containsKey(paramString)))
      return;
    sLoadedIds.add(paramString);
    IMDataLoadWrapper.loadBaseUserInfo(paramString, paramIResultRecvHandler);
  }

  public static void loadBaseInfo(String paramString1, String paramString2, IResultRecvHandler paramIResultRecvHandler)
  {
    if (paramString1 == null);
    while (true)
    {
      return;
      if (paramString2 == null)
        break;
      List localList = IMAgent.getInstance().getGroupMembers(paramString2);
      if ((localList == null) || (localList.size() == 0))
      {
        loadBaseInfo(paramString1, paramIResultRecvHandler);
        return;
      }
      for (int i = 0; i < localList.size(); i++)
      {
        UserInfo localUserInfo = (UserInfo)localList.get(i);
        if (paramString1.equalsIgnoreCase(localUserInfo.userKey))
        {
          putBaseInfo(paramString1, localUserInfo.snsInfo.sns_avatar, localUserInfo.snsInfo.sns_gender);
          return;
        }
      }
    }
    loadBaseInfo(paramString1, paramIResultRecvHandler);
  }

  public static void putBaseInfo(String paramString, AvatarAndGender paramAvatarAndGender)
  {
    if ((paramString == null) || (paramAvatarAndGender == null))
      return;
    if (!sBaseInfos.containsKey(paramString));
    for (int i = 1; ; i = 0)
    {
      sBaseInfos.put(paramString, paramAvatarAndGender);
      sLoadedIds.add(paramString);
      if (i == 0)
        break;
      dispatchAvatarPutEvent(paramString, paramAvatarAndGender);
      return;
    }
  }

  public static void putBaseInfo(String paramString1, String paramString2, String paramString3)
  {
    putBaseInfo(paramString1, new AvatarAndGender(paramString2, paramString3));
  }

  public static void removeListener(OnBaseUserinfoPutListener paramOnBaseUserinfoPutListener)
  {
    if (sListeners == null)
      return;
    sListeners.remove(paramOnBaseUserinfoPutListener);
  }

  public static class AvatarAndGender
  {
    String mAvatar;
    String mGender;

    public AvatarAndGender(String paramString1, String paramString2)
    {
      this.mAvatar = paramString1;
      this.mGender = paramString2;
    }

    public String getAvatar()
    {
      return this.mAvatar;
    }

    public String getGender()
    {
      return this.mGender;
    }
  }

  public static abstract interface OnBaseUserinfoPutListener
  {
    public abstract void onBaseInfoPut(String paramString, BaseUserInfoPool.AvatarAndGender paramAvatarAndGender);
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.qtradio.im.BaseUserInfoPool
 * JD-Core Version:    0.6.2
 */