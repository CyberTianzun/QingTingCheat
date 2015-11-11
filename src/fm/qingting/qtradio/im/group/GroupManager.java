package fm.qingting.qtradio.im.group;

import android.util.Log;

public class GroupManager
{
  private static GroupManager _instance;

  public static GroupManager getInstance()
  {
    if (_instance == null)
      _instance = new GroupManager();
    return _instance;
  }

  private void log(String paramString)
  {
    Log.e("groupmanager", paramString);
  }

  public void addGroup(String paramString1, String paramString2)
  {
  }

  public void createGroup(String paramString1, String paramString2)
  {
  }

  public void exitGroup(String paramString1, String paramString2)
  {
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.qtradio.im.group.GroupManager
 * JD-Core Version:    0.6.2
 */