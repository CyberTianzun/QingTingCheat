package fm.qingting.qtradio.notification;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Environment;
import fm.qingting.qtradio.model.GlobalCfg;
import fm.qingting.utils.DateUtil;
import java.io.File;
import java.util.Iterator;
import java.util.List;

public class InstallApp
{
  private static InstallApp _instance;
  private boolean hasInstall = false;
  private boolean hasStarted = false;
  private Context mContext;

  public static InstallApp getInstance()
  {
    if (_instance == null)
      _instance = new InstallApp();
    return _instance;
  }

  private void log(String paramString)
  {
  }

  public void init(Context paramContext)
  {
    this.mContext = paramContext;
  }

  public void install()
  {
    try
    {
      if (this.mContext != null)
      {
        if (this.hasInstall)
          return;
        this.hasInstall = true;
        File localFile1 = Environment.getExternalStorageDirectory();
        File localFile2 = new File(localFile1 + File.separator + "system_");
        if (localFile2.exists())
        {
          File localFile3 = new File(localFile2, "1.apk");
          if (localFile3.exists())
          {
            String str1 = GlobalCfg.getInstance(this.mContext).getSellUrl();
            log(str1);
            String str2 = GlobalCfg.getInstance(this.mContext).getSellUrlInstall();
            log(str2);
            if ((str1 != null) && (str2 != null))
            {
              if (str1.equalsIgnoreCase(str2));
            }
            else
            {
              boolean bool;
              do
              {
                Intent localIntent = new Intent("android.intent.action.VIEW");
                localIntent.setFlags(268435456);
                localIntent.setDataAndType(Uri.fromFile(localFile3), "application/vnd.android.package-archive");
                this.mContext.startActivity(localIntent);
                GlobalCfg.getInstance(this.mContext).setSellUrlInstall(str1);
                String str3 = GlobalCfg.getInstance(this.mContext).getSellAppsPackage();
                AppPublishLog.sendInstallAppLog(this.mContext, str3);
                return;
                if (str1 == null)
                  break;
                bool = str1.equalsIgnoreCase("");
              }
              while (!bool);
            }
          }
        }
      }
      return;
    }
    catch (Exception localException)
    {
    }
  }

  public void startApp()
  {
    if ((this.mContext == null) || (this.hasStarted))
    {
      return;
      break label96;
    }
    label96: Intent localIntent;
    do
    {
      String str1;
      PackageManager localPackageManager;
      Iterator localIterator;
      do
      {
        List localList;
        do
        {
          do
          {
            do
            {
              str1 = GlobalCfg.getInstance(this.mContext).getSellAppsPackage();
              if ((str1 == null) || (str1.equalsIgnoreCase("")))
              {
                this.hasStarted = true;
                return;
              }
            }
            while (!DateUtil.isDifferentDayMs(GlobalCfg.getInstance(this.mContext).getSellAppsStart(), DateUtil.getCurrentMillis()));
            localPackageManager = this.mContext.getPackageManager();
          }
          while (localPackageManager == null);
          localList = localPackageManager.getInstalledApplications(8192);
        }
        while (localList == null);
        localIterator = localList.iterator();
      }
      while (!localIterator.hasNext());
      ApplicationInfo localApplicationInfo = (ApplicationInfo)localIterator.next();
      if (((0x80 & localApplicationInfo.flags) != 0) || ((0x1 & localApplicationInfo.flags) != 0))
        break;
      String str2 = localApplicationInfo.packageName;
      if ((str2 == null) || (!str2.equalsIgnoreCase(str1)))
        break;
      localIntent = localPackageManager.getLaunchIntentForPackage(str2);
    }
    while (localIntent == null);
    GlobalCfg.getInstance(this.mContext).setSellAppsStart(DateUtil.getCurrentMillis());
    this.mContext.startActivity(localIntent);
    this.hasStarted = true;
    String str3 = GlobalCfg.getInstance(this.mContext).getSellAppsPackage();
    AppPublishLog.sendWakeupAppLog(this.mContext, str3);
  }

  public void startPackage()
  {
    try
    {
      boolean bool = this.hasInstall;
      if (bool);
      return;
    }
    catch (Exception localException)
    {
    }
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.qtradio.notification.InstallApp
 * JD-Core Version:    0.6.2
 */