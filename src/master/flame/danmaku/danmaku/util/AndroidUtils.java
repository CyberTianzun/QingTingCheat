package master.flame.danmaku.danmaku.util;

import android.app.ActivityManager;
import android.content.Context;

public class AndroidUtils
{
  public static int getMemoryClass(Context paramContext)
  {
    return ((ActivityManager)paramContext.getSystemService("activity")).getMemoryClass();
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     master.flame.danmaku.danmaku.util.AndroidUtils
 * JD-Core Version:    0.6.2
 */