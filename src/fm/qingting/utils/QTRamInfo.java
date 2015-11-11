package fm.qingting.utils;

import android.app.ActivityManager;
import android.app.ActivityManager.MemoryInfo;
import android.content.Context;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class QTRamInfo
{
  public static int getAvailableRam(Context paramContext)
  {
    try
    {
      ActivityManager localActivityManager = (ActivityManager)paramContext.getSystemService("activity");
      ActivityManager.MemoryInfo localMemoryInfo = new ActivityManager.MemoryInfo();
      localActivityManager.getMemoryInfo(localMemoryInfo);
      long l = localMemoryInfo.availMem / 1024L / 1024L;
      return (int)l;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return -1;
  }

  public static int getTotalRam()
  {
    long l = 0L;
    try
    {
      BufferedReader localBufferedReader = new BufferedReader(new FileReader("/proc/meminfo"), 8192);
      l = 1024 * Integer.valueOf(localBufferedReader.readLine().split("\\s+")[1]).intValue();
      localBufferedReader.close();
      label49: return (int)(l / 1024L / 1024L);
    }
    catch (Exception localException)
    {
      break label49;
    }
    catch (IOException localIOException)
    {
      break label49;
    }
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.utils.QTRamInfo
 * JD-Core Version:    0.6.2
 */