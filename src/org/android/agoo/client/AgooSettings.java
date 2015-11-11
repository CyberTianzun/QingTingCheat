package org.android.agoo.client;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.text.TextUtils;
import com.umeng.message.proguard.P;
import com.umeng.message.proguard.Q;
import java.util.Calendar;

public class AgooSettings
{
  private static final String a = "42.120.111.1";
  private static final String b = "110.75.40.7";
  private static final String c = "110.75.120.15";
  private static final String d = "agoo_mode";
  private static final String e = "agoo_security_mode";
  private static final long f = 20131220L;
  private static final String g = "AGOO_MTOP_DOMAIN";
  private static final String h = "AGOO_APOLL_DOMAIN";
  private static final String i = "agoo_start_time";
  private static final String j = "agoo_end_time";

  public static long getAgooReleaseTime()
  {
    return 20131220L;
  }

  public static String getApollUrl(Context paramContext)
  {
    return String.format("http://%s/activeip/", new Object[] { P.g(paramContext).getString("AGOO_APOLL_DOMAIN", "upoll.umengcloud.com") });
  }

  public static Mode getMode(Context paramContext)
  {
    try
    {
      String str = P.g(paramContext).getString("AGOO_MTOP_DOMAIN", "utop.umengcloud.com");
      Mode localMode;
      if (TextUtils.indexOf(str, "waptest") != -1)
        localMode = Mode.TEST;
      while (true)
      {
        return localMode;
        if (TextUtils.indexOf(str, "wapa") != -1)
          localMode = Mode.PREVIEW;
        else
          localMode = Mode.RELEASE;
      }
    }
    finally
    {
    }
  }

  public static String getPullUrl(Context paramContext)
  {
    return String.format("http://%s/rest/api3.do", new Object[] { P.g(paramContext).getString("AGOO_MTOP_DOMAIN", "utop.umengcloud.com") });
  }

  public static long getTargetTime(Context paramContext)
  {
    return getTargetTime(paramContext, System.currentTimeMillis());
  }

  public static long getTargetTime(Context paramContext, long paramLong)
  {
    if (paramContext == null);
    int k;
    int m;
    do
    {
      return -1L;
      SharedPreferences localSharedPreferences = P.g(paramContext);
      k = localSharedPreferences.getInt("agoo_start_time", -1);
      m = localSharedPreferences.getInt("agoo_end_time", -1);
    }
    while ((k == -1) || (m == -1));
    Calendar localCalendar = Calendar.getInstance();
    localCalendar.setTimeInMillis(paramLong);
    int n = localCalendar.get(11);
    int i1 = localCalendar.get(12);
    int i2 = localCalendar.get(13) + (60 * (n * 60) + i1 * 60);
    if (i2 < k)
      localCalendar.add(13, k - i2);
    while (true)
    {
      return localCalendar.getTimeInMillis();
      if (i2 <= m)
        break;
      localCalendar.add(13, 86400 + (k - i2));
    }
  }

  public static boolean isAgooSoSecurityMode(Context paramContext)
  {
    return P.g(paramContext).getBoolean("agoo_security_mode", false);
  }

  public static boolean isAgooTestMode(Context paramContext)
  {
    return getMode(paramContext) != Mode.RELEASE;
  }

  public static void setAgooMode(Context paramContext, Mode paramMode)
  {
  }

  public static void setAgooSecurityMode(Context paramContext, boolean paramBoolean)
  {
    if (paramContext != null)
    {
      SharedPreferences.Editor localEditor = P.g(paramContext).edit();
      localEditor.putBoolean("agoo_security_mode", paramBoolean);
      localEditor.commit();
    }
  }

  public static void setAvailableTime(Context paramContext, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    if (paramContext != null)
    {
      int k = 0 + (60 * (paramInt1 * 60) + paramInt2 * 60);
      int m = 0 + (60 * (paramInt3 * 60) + paramInt4 * 60);
      SharedPreferences.Editor localEditor = P.g(paramContext).edit();
      localEditor.putInt("agoo_start_time", k);
      localEditor.putInt("agoo_end_time", m);
      localEditor.commit();
    }
  }

  public static void setDebugMode(boolean paramBoolean)
  {
    Q.b(paramBoolean);
  }

  public static void setDomain(Context paramContext, String paramString1, String paramString2)
  {
    if (paramContext != null)
    {
      SharedPreferences.Editor localEditor = P.g(paramContext).edit();
      localEditor.putString("AGOO_MTOP_DOMAIN", paramString1);
      localEditor.putString("AGOO_APOLL_DOMAIN", paramString2);
      localEditor.commit();
    }
  }

  public static void setLog(Context paramContext, boolean paramBoolean1, boolean paramBoolean2)
  {
    P.a(paramContext, paramBoolean1, paramBoolean2);
    Q.a(paramContext);
  }

  public static void setLog2File(boolean paramBoolean)
  {
    Q.a(paramBoolean);
  }

  public static void setUTVersion(Context paramContext, String paramString)
  {
    P.a(paramContext, paramString);
  }

  public static abstract enum Mode
  {
    private int a;

    static
    {
      PREVIEW = new AgooSettings.Mode.2("PREVIEW", 1, 0);
      RELEASE = new AgooSettings.Mode.3("RELEASE", 2, 1);
      Mode[] arrayOfMode = new Mode[3];
      arrayOfMode[0] = TEST;
      arrayOfMode[1] = PREVIEW;
      arrayOfMode[2] = RELEASE;
    }

    private Mode(int paramInt)
    {
      this.a = paramInt;
    }

    public abstract String getPushApollIp();

    public abstract int getPushApollPort();

    public int getValue()
    {
      return this.a;
    }
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     org.android.agoo.client.AgooSettings
 * JD-Core Version:    0.6.2
 */