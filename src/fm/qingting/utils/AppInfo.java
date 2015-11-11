package fm.qingting.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import fm.qingting.qtradio.model.GlobalCfg;

public class AppInfo
{
  public static final String OldVersionNumber = "OldVersionNumber";

  public static String getChannelName(Context paramContext)
  {
    try
    {
      String str = paramContext.getString(2131492868);
      return str;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return "";
  }

  public static String getCurrentInternalVersion(Context paramContext)
  {
    try
    {
      String str = paramContext.getString(2131492875);
      return str;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return "";
  }

  public static int getCurrentVersionCode(Context paramContext)
  {
    int i = 10000;
    try
    {
      PackageInfo localPackageInfo = paramContext.getPackageManager().getPackageInfo(paramContext.getPackageName(), 0);
      if (localPackageInfo != null)
        i = localPackageInfo.versionCode;
      return i;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return i;
  }

  public static String getFirstInstalledVersionName(Context paramContext)
  {
    String str = GlobalCfg.getInstance(paramContext).getValueFromDB("OldVersionNumber");
    if (str == null)
    {
      recordVersion(paramContext);
      str = getCurrentInternalVersion(paramContext);
    }
    return str;
  }

  public static void recordVersion(Context paramContext)
  {
    if (GlobalCfg.getInstance(paramContext).getValueFromDB("OldVersionNumber") == null)
    {
      String str = getCurrentInternalVersion(paramContext);
      GlobalCfg.getInstance(paramContext).setValueToDB("OldVersionNumber", "String", str);
    }
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.utils.AppInfo
 * JD-Core Version:    0.6.2
 */