package fm.qingting.qtradio.manager.advertisement;

import android.content.Context;
import com.umeng.analytics.MobclickAgent;

public class UMengLogger
{
  public static void sendmessage(Context paramContext, String paramString1, String paramString2)
  {
    try
    {
      MobclickAgent.onEvent(paramContext, paramString1, paramString2);
      return;
    }
    catch (Exception localException)
    {
    }
  }

  public static void sendmessage(Context paramContext, String paramString1, String paramString2, int paramInt)
  {
    try
    {
      MobclickAgent.onEvent(paramContext, paramString1, paramString2, paramInt);
      return;
    }
    catch (Exception localException)
    {
    }
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.qtradio.manager.advertisement.UMengLogger
 * JD-Core Version:    0.6.2
 */