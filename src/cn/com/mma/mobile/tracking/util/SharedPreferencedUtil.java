package cn.com.mma.mobile.tracking.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class SharedPreferencedUtil
{
  public static final String SP_CONFIG_KEY_FILE = "trackingConfig";
  public static final String SP_NAME_CONFIG = "cn.com.mma.mobile.tracking.sdkconfig";
  public static final String SP_NAME_FAILED = "cn.com.mma.mobile.tracking.falied";
  public static final String SP_NAME_NORMAL = "cn.com.mma.mobile.tracking.normal";
  public static final String SP_NAME_OTHER = "cn.com.mma.mobile.tracking.other";
  public static final String SP_OTHER_KEY_ANDROID_ID = "android_id";
  public static final String SP_OTHER_KEY_UPDATE_TIME = "updateTime";

  public static void clearAllDataInSP(Context paramContext, String paramString)
  {
    paramContext.getSharedPreferences(paramString, 0).edit().clear().commit();
  }

  public static long getLong(Context paramContext, String paramString1, String paramString2)
  {
    return paramContext.getSharedPreferences(paramString1, 0).getLong(paramString2, 0L);
  }

  public static SharedPreferences getSharedPreferences(Context paramContext, String paramString)
  {
    return paramContext.getSharedPreferences(paramString, 0);
  }

  public static String getString(Context paramContext, String paramString1, String paramString2)
  {
    return paramContext.getSharedPreferences(paramString1, 0).getString(paramString2, "");
  }

  public static void putLong(Context paramContext, String paramString1, String paramString2, long paramLong)
  {
    SharedPreferences.Editor localEditor = paramContext.getSharedPreferences(paramString1, 0).edit();
    localEditor.putLong(paramString2, paramLong);
    localEditor.commit();
  }

  public static void putString(Context paramContext, String paramString1, String paramString2, String paramString3)
  {
    SharedPreferences.Editor localEditor = paramContext.getSharedPreferences(paramString1, 0).edit();
    localEditor.putString(paramString2, paramString3);
    localEditor.commit();
  }

  public static boolean removeFromSharedPreferences(Context paramContext, String paramString1, String paramString2)
  {
    return paramContext.getSharedPreferences(paramString1, 0).edit().remove(paramString2).commit();
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     cn.com.mma.mobile.tracking.util.SharedPreferencedUtil
 * JD-Core Version:    0.6.2
 */