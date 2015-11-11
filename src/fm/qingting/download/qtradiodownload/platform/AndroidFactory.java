package fm.qingting.download.qtradiodownload.platform;

import android.content.Context;

public class AndroidFactory
{
  private static Context context = null;

  public static Context getApplicationContext()
  {
    return context;
  }

  public static void setApplicationContext(Context paramContext)
  {
    context = paramContext;
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.download.qtradiodownload.platform.AndroidFactory
 * JD-Core Version:    0.6.2
 */