package fm.qingting.utils;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.content.Context;
import android.util.Log;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;

public class ProcessDetect
{
  public static int getProcessId(String paramString, Context paramContext)
  {
    if ((paramString == null) || (paramContext == null))
      return -1;
    try
    {
      List localList = ((ActivityManager)paramContext.getSystemService("activity")).getRunningAppProcesses();
      for (int i = 0; i < localList.size(); i++)
      {
        ActivityManager.RunningAppProcessInfo localRunningAppProcessInfo = (ActivityManager.RunningAppProcessInfo)localList.get(i);
        if (localRunningAppProcessInfo.processName.equalsIgnoreCase(paramString))
        {
          int j = localRunningAppProcessInfo.pid;
          return j;
        }
      }
    }
    catch (Exception localException)
    {
    }
    return -1;
  }

  private static void log(String paramString)
  {
    Log.e("processdetect", paramString);
  }

  public static boolean processExists(String paramString1, String paramString2)
  {
    if (paramString1 == null);
    while (true)
    {
      return false;
      try
      {
        BufferedReader localBufferedReader = new BufferedReader(new InputStreamReader(Runtime.getRuntime().exec("top -n 1").getInputStream()));
        boolean bool;
        do
        {
          String str;
          do
          {
            str = localBufferedReader.readLine();
            if (str == null)
              break;
          }
          while (!str.contains(paramString1));
          if (paramString2 == null)
            break label69;
          bool = str.contains(paramString2);
        }
        while (bool);
        label69: return true;
      }
      catch (Exception localException)
      {
        localException.printStackTrace();
      }
    }
    return false;
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.utils.ProcessDetect
 * JD-Core Version:    0.6.2
 */