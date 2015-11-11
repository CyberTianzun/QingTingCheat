package fm.qingting.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import java.io.FileOutputStream;

public class Appuserinfo
{
  private static String filename = "user-new.dat";

  public static void newUserAdded(Context paramContext)
  {
    try
    {
      int i = paramContext.getPackageManager().getPackageInfo(paramContext.getPackageName(), 0).versionCode;
      FileOutputStream localFileOutputStream = paramContext.openFileOutput(filename, 0);
      localFileOutputStream.write(String.valueOf(i).getBytes());
      localFileOutputStream.close();
      return;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.utils.Appuserinfo
 * JD-Core Version:    0.6.2
 */