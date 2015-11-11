package fm.qingting.framework.utils;

import android.app.ActivityManager;
import android.content.Context;
import android.os.Debug;

public class MemManager
{
  private static double freeMemForApp = -1.0D;
  private static double heapMemLimit;
  private static double startUpMem;

  public static double getFreeMemForApp(Context paramContext)
  {
    if (freeMemForApp < 0.0D)
    {
      heapMemLimit = 1024 * ((ActivityManager)paramContext.getSystemService("activity")).getMemoryClass();
      log("dalvik.vm.heapsize:" + heapMemLimit);
      Runtime localRuntime = Runtime.getRuntime();
      heapMemLimit = localRuntime.maxMemory() / 1024L;
      log("Max heap:" + heapMemLimit + ";Current heap:" + localRuntime.totalMemory() / 1024L + ";Free heap:" + localRuntime.freeMemory() / 1024L + ";Allocated heap:" + (localRuntime.totalMemory() / 1024L - localRuntime.freeMemory() / 1024L));
      log("Native heap size:" + Debug.getNativeHeapSize() / 1024L + ";Native allocated heap size:" + Debug.getNativeHeapAllocatedSize() / 1024L + ";Native free heap size:" + Debug.getNativeHeapFreeSize() / 1024L);
      startUpMem = (localRuntime.totalMemory() - localRuntime.freeMemory() + Debug.getNativeHeapAllocatedSize()) / 1024L;
      log("Start up memory:" + startUpMem);
      log("Free Mem Now:" + (heapMemLimit - startUpMem));
      freeMemForApp = heapMemLimit - startUpMem;
      return freeMemForApp;
    }
    return freeMemForApp;
  }

  public static void log(String paramString)
  {
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.framework.utils.MemManager
 * JD-Core Version:    0.6.2
 */