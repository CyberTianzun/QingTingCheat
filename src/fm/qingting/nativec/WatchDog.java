package fm.qingting.nativec;

import android.content.Context;
import android.os.Environment;
import android.util.Log;
import fm.qingting.qtradio.abtest.ABTest;
import fm.qingting.qtradio.abtest.ABTestConfig;
import fm.qingting.qtradio.log.LogModule;
import fm.qingting.qtradio.logger.QTLogger;
import fm.qingting.utils.AppInfo;
import fm.qingting.utils.DeviceInfo;
import fm.qingting.utils.ProcessDetect;

public class WatchDog
{
  private static final int EnableDebug = 0;
  private static String LogApiUrl = "http://logger.qingting.fm/logger.php";
  private static final int MonitorInterval = 900;
  private static Context _context;
  private static String _packageName;
  private static String _processName;
  private static String _serviceName;

  public static void monitor(String paramString1, String paramString2, String paramString3, Context paramContext)
  {
    _context = paramContext;
    _processName = paramString1;
    _serviceName = paramString2;
    _packageName = paramString3;
    new Thread()
    {
      public void run()
      {
        String str1;
        String str2;
        String str3;
        String str4;
        String str5;
        String str6;
        String str7;
        String str8;
        try
        {
          sleep(5000L);
          str1 = WatchDog._packageName + "/" + "ihaveadreamwatchdog";
          if (ProcessDetect.processExists(str1, null))
          {
            Log.e("WatchDog", "watch dog is running. Don't launch it twice.");
            return;
          }
        }
        catch (InterruptedException localInterruptedException1)
        {
          while (true)
            localInterruptedException1.printStackTrace();
          str2 = Environment.getExternalStorageDirectory() + "/log.txt";
          str3 = DeviceInfo.getUniqueId(WatchDog._context);
          str4 = AppInfo.getCurrentInternalVersion(WatchDog._context);
          str5 = WatchDog._context.getString(2131492869);
          str6 = DeviceInfo.getDeviceName().replace(" ", "-");
          str7 = DeviceInfo.getAndroidOsVersion();
          str8 = ABTest.buildUserTypeString(WatchDog._context, ABTestConfig.items).trim();
          if (str8.length() != 0);
        }
        for (String str9 = "NA"; ; str9 = str8)
        {
          String str10 = AppInfo.getFirstInstalledVersionName(WatchDog._context);
          Context localContext = WatchDog._context;
          String str11 = WatchDog._context.getPackageName();
          String[] arrayOfString1 = new String[6];
          arrayOfString1[0] = WatchDog._processName;
          arrayOfString1[1] = WatchDog._serviceName;
          arrayOfString1[2] = WatchDog._packageName;
          arrayOfString1[3] = WatchDog.LogApiUrl;
          arrayOfString1[4] = String.valueOf(900);
          arrayOfString1[5] = String.valueOf(0);
          String[] arrayOfString2 = new String[20];
          arrayOfString2[0] = "+8";
          arrayOfString2[1] = "Android";
          arrayOfString2[2] = str3;
          arrayOfString2[3] = "NA";
          arrayOfString2[4] = str4;
          arrayOfString2[5] = str5;
          arrayOfString2[6] = str6;
          arrayOfString2[7] = str7;
          arrayOfString2[8] = "NA";
          arrayOfString2[9] = "NA";
          arrayOfString2[10] = "NA";
          arrayOfString2[11] = "NA";
          arrayOfString2[12] = "NA";
          arrayOfString2[13] = "NA";
          arrayOfString2[14] = "NA";
          arrayOfString2[15] = str9;
          arrayOfString2[16] = "NA";
          arrayOfString2[17] = "0";
          arrayOfString2[18] = WatchDog._packageName;
          arrayOfString2[19] = str10;
          NativeLaucher.RunExecutable(localContext, str11, "libwatchdog.so", "ihaveadreamwatchdog", str2, arrayOfString1, arrayOfString2);
          try
          {
            sleep(5000L);
            if (!ProcessDetect.processExists(str1, null))
            {
              Log.e("WatchDog", "watch dog not launched. There may be some error with it.");
              String str12 = QTLogger.getInstance().buildCommonLog();
              String str13 = str12.substring(0, -1 + str12.length());
              LogModule.getInstance().send("WatchdogNotLaunched", str13);
              return;
            }
          }
          catch (InterruptedException localInterruptedException2)
          {
            localInterruptedException2.printStackTrace();
            return;
          }
          Log.e("WatchDog", "watch dog starts running now.");
          return;
        }
      }
    }
    .start();
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.nativec.WatchDog
 * JD-Core Version:    0.6.2
 */