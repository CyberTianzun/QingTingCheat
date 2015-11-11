package u.aly;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import com.umeng.analytics.AnalyticsConfig;
import java.util.Arrays;
import java.util.List;

public class v
{
  private static final String a = "session_start_time";
  private static final String b = "session_end_time";
  private static final String c = "session_id";
  private static final String f = "activities";
  private final String d = "a_start_time";
  private final String e = "a_end_time";

  private String a(Context paramContext, SharedPreferences paramSharedPreferences)
  {
    k localk = k.a(paramContext);
    String str = b(paramContext);
    af localaf = a(paramContext);
    SharedPreferences.Editor localEditor = paramSharedPreferences.edit();
    localEditor.putString("session_id", str);
    localEditor.putLong("session_start_time", System.currentTimeMillis());
    localEditor.putLong("session_end_time", 0L);
    localEditor.commit();
    if (localaf != null)
    {
      localk.a(localaf);
      return str;
    }
    localk.a((af)null);
    return str;
  }

  private void a(SharedPreferences paramSharedPreferences)
  {
    SharedPreferences.Editor localEditor = paramSharedPreferences.edit();
    localEditor.remove("session_start_time");
    localEditor.remove("session_end_time");
    localEditor.remove("session_id");
    localEditor.remove("a_start_time");
    localEditor.remove("a_end_time");
    localEditor.putString("activities", "");
    localEditor.commit();
  }

  private boolean b(SharedPreferences paramSharedPreferences)
  {
    long l1 = paramSharedPreferences.getLong("a_start_time", 0L);
    long l2 = paramSharedPreferences.getLong("a_end_time", 0L);
    long l3 = System.currentTimeMillis();
    if ((l1 != 0L) && (l3 - l1 < AnalyticsConfig.kContinueSessionMillis))
      bj.b("MobclickAgent", "onResume called before onPause");
    while (l3 - l2 <= AnalyticsConfig.kContinueSessionMillis)
      return false;
    return true;
  }

  public af a(Context paramContext)
  {
    SharedPreferences localSharedPreferences = u.a(paramContext);
    String str = localSharedPreferences.getString("session_id", null);
    if (str == null)
      return null;
    long l1 = localSharedPreferences.getLong("session_start_time", 0L);
    long l2 = localSharedPreferences.getLong("session_end_time", 0L);
    long l3 = 0L;
    if (l2 != 0L)
    {
      l3 = l2 - l1;
      if (Math.abs(l3) > 86400000L)
        l3 = 0L;
    }
    af localaf = new af();
    localaf.a(str);
    localaf.a(l1);
    localaf.b(l2);
    localaf.c(l3);
    double[] arrayOfDouble = AnalyticsConfig.getLocation();
    aw localaw;
    if (arrayOfDouble != null)
    {
      localaw = new aw(arrayOfDouble[0], arrayOfDouble[1], System.currentTimeMillis());
      if (!localaf.y())
        break label216;
      localaf.a(localaw);
    }
    while (true)
    {
      be localbe = y.a(paramContext);
      if (localbe != null)
        localaf.a(localbe);
      List localList = z.a(localSharedPreferences);
      if ((localList != null) && (localList.size() > 0))
        localaf.a(localList);
      a(localSharedPreferences);
      return localaf;
      label216: localaf.b(Arrays.asList(new aw[] { localaw }));
    }
  }

  public String b(Context paramContext)
  {
    String str1 = bi.f(paramContext);
    String str2 = AnalyticsConfig.getAppkey(paramContext);
    long l = System.currentTimeMillis();
    if (str2 == null)
      throw new RuntimeException("Appkey is null or empty, Please check AndroidManifest.xml");
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append(l).append(str2).append(str1);
    return bv.a(localStringBuilder.toString());
  }

  public void c(Context paramContext)
  {
    SharedPreferences localSharedPreferences = u.a(paramContext);
    if (localSharedPreferences == null)
      return;
    if (b(localSharedPreferences))
    {
      String str2 = a(paramContext, localSharedPreferences);
      bj.a("MobclickAgent", "Start new session: " + str2);
    }
    while (true)
    {
      SharedPreferences.Editor localEditor = localSharedPreferences.edit();
      localEditor.putLong("a_start_time", System.currentTimeMillis());
      localEditor.putLong("a_end_time", 0L);
      localEditor.commit();
      return;
      String str1 = localSharedPreferences.getString("session_id", null);
      bj.a("MobclickAgent", "Extend current session: " + str1);
    }
  }

  public void d(Context paramContext)
  {
    SharedPreferences localSharedPreferences = u.a(paramContext);
    if (localSharedPreferences == null)
      return;
    if ((localSharedPreferences.getLong("a_start_time", 0L) == 0L) && (AnalyticsConfig.ACTIVITY_DURATION_OPEN))
    {
      bj.b("MobclickAgent", "onPause called before onResume");
      return;
    }
    long l = System.currentTimeMillis();
    SharedPreferences.Editor localEditor = localSharedPreferences.edit();
    localEditor.putLong("a_start_time", 0L);
    localEditor.putLong("a_end_time", l);
    localEditor.putLong("session_end_time", l);
    localEditor.commit();
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     u.aly.v
 * JD-Core Version:    0.6.2
 */