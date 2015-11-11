package fm.qingting.qtradio.view.settingviews;

import android.os.Environment;
import android.text.TextUtils;
import fm.qingting.download.QTRadioDownloadAgent;
import fm.qingting.qtradio.fm.PlayCacheAgent;
import fm.qingting.qtradio.helper.OnlineUpdateHelper;
import fm.qingting.qtradio.view.scheduleview.SizeInfo;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class SettingConfig
{
  public static List<String> getAllExterSdcardPath()
  {
    ArrayList localArrayList = new ArrayList();
    String str1 = Environment.getExternalStorageDirectory().getPath();
    try
    {
      BufferedReader localBufferedReader = new BufferedReader(new InputStreamReader(Runtime.getRuntime().exec("mount").getInputStream()));
      while (true)
      {
        String str2 = localBufferedReader.readLine();
        if (str2 == null)
          break;
        if ((!str2.contains("secure")) && (!str2.contains("asec")) && (!str2.contains("media")) && (!str2.contains("system")) && (!str2.contains("cache")) && (!str2.contains("sys")) && (!str2.contains("data")) && (!str2.contains("tmpfs")) && (!str2.contains("shell")) && (!str2.contains("root")) && (!str2.contains("acct")) && (!str2.contains("proc")) && (!str2.contains("misc")) && (!str2.contains("obb")) && ((str2.contains("fat")) || (str2.contains("fuse")) || (str2.contains("ntfs"))))
        {
          String[] arrayOfString = str2.split(" ");
          if ((arrayOfString != null) && (arrayOfString.length > 1))
          {
            String str3 = arrayOfString[1];
            if ((str3 != null) && (!localArrayList.contains(str3)) && (str3.toLowerCase(Locale.US).contains("sd")))
              localArrayList.add(arrayOfString[1]);
          }
        }
      }
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      if (!localArrayList.contains(str1))
        localArrayList.add(str1);
    }
    return localArrayList;
  }

  private static String getDownloadDirSettingSubInfo()
  {
    String str1 = QTRadioDownloadAgent.getInstance().getDownLoadPath();
    if (TextUtils.equals(str1, Environment.getExternalStorageDirectory().getPath() + File.separator + "QTDownloadRadio"));
    for (String str2 = "存储卡1"; ; str2 = "存储卡2")
    {
      String str3 = SizeInfo.getFileSize(QTRadioDownloadAgent.getInstance().getAvailableExternalMemorySize(str1));
      String str4 = SizeInfo.getFileSize(QTRadioDownloadAgent.getInstance().getTotalExternalMemorySize(str1));
      return "当前位置:" + str2 + "," + str3 + "可用," + "共" + str4;
    }
  }

  public static List<Object> getSettingList()
  {
    ArrayList localArrayList = new ArrayList();
    localArrayList.add(new SettingItem("清除缓存", SettingItem.SettingType.select, "delcache", "已缓存" + PlayCacheAgent.getInstance().getCacheSize() / 1048576L + "MB"));
    localArrayList.add(new SettingItem("打开应用后自动播放", SettingItem.SettingType.switcher, "autoplay"));
    localArrayList.add(new SettingItem("音质设置", SettingItem.SettingType.select, "audioquality"));
    if (needShowDownloadDirSetting())
      localArrayList.add(new SettingItem("选择下载位置", SettingItem.SettingType.select, "selectdir", getDownloadDirSettingSubInfo()));
    localArrayList.add(new SettingItem("消息推送", SettingItem.SettingType.select, "pushmessage"));
    localArrayList.add(new SettingItem("常见问题", SettingItem.SettingType.select, "faq"));
    localArrayList.add(new SettingItem("意见反馈", SettingItem.SettingType.select, "help"));
    if (OnlineUpdateHelper.getInstance().hasUpdate());
    for (String str = OnlineUpdateHelper.getInstance().getLatestVersion(); ; str = "已是最新")
    {
      localArrayList.add(new SettingItem("检查更新", SettingItem.SettingType.select, "checkupgrade", str));
      localArrayList.add(new SettingItem("关于蜻蜓fm", SettingItem.SettingType.select, "aboutus"));
      localArrayList.add(new SettingItem("主播入驻", SettingItem.SettingType.select, "podcasterenroll"));
      return localArrayList;
    }
  }

  public static boolean needShowDownloadDirSetting()
  {
    return getAllExterSdcardPath().size() > 1;
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.qtradio.view.settingviews.SettingConfig
 * JD-Core Version:    0.6.2
 */