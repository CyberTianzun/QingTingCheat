package fm.qingting.qtradio.manager;

import android.os.Build.VERSION;

public class QtApiLevelManager
{
  public static boolean isApiLevelSupported(int paramInt)
  {
    return Build.VERSION.SDK_INT >= paramInt;
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.qtradio.manager.QtApiLevelManager
 * JD-Core Version:    0.6.2
 */