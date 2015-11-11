package fm.qingting.framework.utils;

import android.content.Context;
import android.os.Vibrator;

public class VibratorUtil
{
  public static void Vibrate(Context paramContext, long paramLong)
  {
    try
    {
      ((Vibrator)paramContext.getSystemService("vibrator")).vibrate(paramLong);
      return;
    }
    catch (Exception localException)
    {
    }
  }

  public static void Vibrate(Context paramContext, long[] paramArrayOfLong, boolean paramBoolean)
  {
    try
    {
      Vibrator localVibrator = (Vibrator)paramContext.getSystemService("vibrator");
      if (paramBoolean);
      for (int i = 1; ; i = -1)
      {
        localVibrator.vibrate(paramArrayOfLong, i);
        return;
      }
    }
    catch (Exception localException)
    {
    }
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.framework.utils.VibratorUtil
 * JD-Core Version:    0.6.2
 */