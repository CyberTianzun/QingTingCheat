package u.aly;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.ApplicationInfo;
import java.lang.reflect.Method;

public class y
{
  private static final String a = "uptr";
  private static final String b = "dntr";

  public static be a(Context paramContext)
  {
    try
    {
      be localbe = new be();
      long[] arrayOfLong = b(paramContext);
      if ((arrayOfLong[0] > 0L) && (arrayOfLong[1] > 0L))
      {
        SharedPreferences localSharedPreferences = u.a(paramContext);
        long l1 = localSharedPreferences.getLong("uptr", -1L);
        long l2 = localSharedPreferences.getLong("dntr", -1L);
        localSharedPreferences.edit().putLong("uptr", arrayOfLong[1]).putLong("dntr", arrayOfLong[0]).commit();
        if ((l1 <= 0L) || (l2 <= 0L))
          break label185;
        arrayOfLong[0] -= l2;
        arrayOfLong[1] -= l1;
        if ((arrayOfLong[0] <= 0L) || (arrayOfLong[1] <= 0L))
          break label187;
        localbe.c((int)arrayOfLong[0]);
        localbe.a((int)arrayOfLong[1]);
        return localbe;
      }
    }
    catch (Exception localException)
    {
      bj.e("MobclickAgent", "sdk less than 2.2 has get no traffic");
      return null;
    }
    return null;
    label185: return null;
    label187: return null;
  }

  private static long[] b(Context paramContext)
    throws Exception
  {
    Class localClass = Class.forName("android.net.TrafficStats");
    Class[] arrayOfClass1 = new Class[1];
    arrayOfClass1[0] = Integer.TYPE;
    Method localMethod1 = localClass.getMethod("getUidRxBytes", arrayOfClass1);
    Class[] arrayOfClass2 = new Class[1];
    arrayOfClass2[0] = Integer.TYPE;
    Method localMethod2 = localClass.getMethod("getUidTxBytes", arrayOfClass2);
    int i = paramContext.getApplicationInfo().uid;
    if (i == -1)
      return null;
    long[] arrayOfLong = new long[2];
    Object[] arrayOfObject1 = new Object[1];
    arrayOfObject1[0] = Integer.valueOf(i);
    arrayOfLong[0] = ((Long)localMethod1.invoke(null, arrayOfObject1)).longValue();
    Object[] arrayOfObject2 = new Object[1];
    arrayOfObject2[0] = Integer.valueOf(i);
    arrayOfLong[1] = ((Long)localMethod2.invoke(null, arrayOfObject2)).longValue();
    return arrayOfLong;
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     u.aly.y
 * JD-Core Version:    0.6.2
 */