package com.sina.weibo.sdk.net;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.text.TextUtils;
import com.sina.weibo.sdk.WbAppInstallActivator;
import com.sina.weibo.sdk.exception.WeiboException;
import com.sina.weibo.sdk.utils.LogUtil;
import com.sina.weibo.sdk.utils.NotificationHelper;
import java.io.File;

public class DownloadService extends IntentService
{
  private static final String APK_SAVE_DIR = WbAppInstallActivator.WB_APK_FILE_DIR;
  public static final String EXTRA_DOWNLOAD_URL = "download_url";
  public static final String EXTRA_NOTIFICATION_CONTENT = "notification_content";
  private static final String TAG = DownloadService.class.getCanonicalName();

  public DownloadService()
  {
    super(TAG);
  }

  private static String generateSaveFileName(String paramString)
  {
    String str = "";
    int i = paramString.lastIndexOf("/");
    if (i != -1)
      str = paramString.substring(i + 1, paramString.length());
    return str;
  }

  public IBinder onBind(Intent paramIntent)
  {
    return null;
  }

  public void onCreate()
  {
    super.onCreate();
  }

  protected void onHandleIntent(Intent paramIntent)
  {
    Bundle localBundle = paramIntent.getExtras();
    if (localBundle == null)
      stopSelf();
    while (true)
    {
      return;
      String str1 = localBundle.getString("download_url");
      String str2 = localBundle.getString("notification_content");
      LogUtil.e(TAG, "onHandleIntent downLoadUrl:" + str1 + "!!!!!");
      if (TextUtils.isEmpty(str1))
      {
        LogUtil.e(TAG, "downloadurl is null");
        stopSelf();
        return;
      }
      Object localObject = "";
      try
      {
        str3 = HttpManager.openRedirectUrl4LocationUri(getApplicationContext(), str1, "GET", new WeiboParameters(""));
        str4 = generateSaveFileName(str3);
        if ((TextUtils.isEmpty(str4)) || (!str4.endsWith(".apk")))
        {
          LogUtil.e(TAG, "redirectDownloadUrl is illeagle");
          stopSelf();
          return;
        }
      }
      catch (WeiboException localWeiboException)
      {
        String str3;
        String str4;
        localWeiboException.printStackTrace();
        while (true)
        {
          if (TextUtils.isEmpty((CharSequence)localObject))
            break label216;
          if (!new File((String)localObject).exists())
            break;
          LogUtil.e(TAG, "download successed!");
          NotificationHelper.showNotification(getApplicationContext(), str2, (String)localObject);
          return;
          String str5 = APK_SAVE_DIR;
          String str6 = HttpManager.downloadFile(getApplicationContext(), str3, str5, str4);
          localObject = str6;
        }
        label216: LogUtil.e(TAG, "download failed!");
      }
    }
  }

  public void onStart(Intent paramIntent, int paramInt)
  {
    super.onStart(paramIntent, paramInt);
    if (paramIntent == null);
  }

  public int onStartCommand(Intent paramIntent, int paramInt1, int paramInt2)
  {
    return super.onStartCommand(paramIntent, paramInt1, paramInt2);
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.sina.weibo.sdk.net.DownloadService
 * JD-Core Version:    0.6.2
 */