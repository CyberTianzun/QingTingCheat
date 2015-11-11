package fm.qingting.qtradio;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.app.Application;
import android.content.Context;
import java.util.List;

public class QTApplication extends Application
{
  public static Context appContext;

  private String getAppName(int paramInt)
  {
    List localList = ((ActivityManager)getSystemService("activity")).getRunningAppProcesses();
    for (int i = 0; i < localList.size(); i++)
    {
      ActivityManager.RunningAppProcessInfo localRunningAppProcessInfo = (ActivityManager.RunningAppProcessInfo)localList.get(i);
      if (localRunningAppProcessInfo.pid == paramInt)
        return localRunningAppProcessInfo.processName;
    }
    return null;
  }

  public void onCreate()
  {
    super.onCreate();
    if (appContext == null)
      appContext = this;
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.qtradio.QTApplication
 * JD-Core Version:    0.6.2
 */