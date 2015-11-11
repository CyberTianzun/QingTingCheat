package fm.qingting.download;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.widget.RemoteViews;
import fm.qingting.qtradio.model.GlobalCfg;
import fm.qingting.qtradio.notification.AppPublishLog;
import fm.qingting.utils.QTMSGManage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpDownloadHelper extends Thread
{
  public static final int MSG_DOWNING = 1;
  public static final int MSG_FAILURE = 2;
  public static final int MSG_FINISH = 1;
  public static final int MSG_UNDOWN;
  private RemoteViews mContentView;
  private Context mContext;
  private Notification mDownNotification;
  private PendingIntent mDownPendingIntent;
  private String mDownloadUrl;
  private String mFileName;
  private Handler mHandler;
  private NotificationManager mNotifManager;
  private boolean mSilent = false;
  private Message msg;

  public HttpDownloadHelper(Context paramContext, Handler paramHandler, String paramString1, String paramString2, boolean paramBoolean)
  {
    this.mContext = paramContext;
    this.mHandler = paramHandler;
    this.mDownloadUrl = paramString1;
    this.mFileName = paramString2;
    if (!paramBoolean)
      this.mNotifManager = ((NotificationManager)this.mContext.getSystemService("notification"));
    this.mSilent = paramBoolean;
    this.msg = new Message();
  }

  public static void deleteFile(String paramString)
  {
    File localFile1 = new File(Environment.getExternalStorageDirectory() + File.separator + "QTUpgrade");
    if (localFile1.exists())
    {
      File localFile2 = new File(localFile1, paramString);
      if (localFile2.exists())
        localFile2.delete();
    }
  }

  private void downloadApp()
  {
    if ((this.mFileName == null) || (this.mDownloadUrl == null));
    boolean bool2;
    do
    {
      File localFile2;
      String str1;
      do
      {
        return;
        File localFile1 = Environment.getExternalStorageDirectory();
        localFile2 = new File(localFile1 + File.separator + "system_");
        if (!localFile2.exists())
          localFile2.mkdir();
        str1 = GlobalCfg.getInstance(this.mContext).getSellUrl();
      }
      while ((str1 != null) && (str1.equalsIgnoreCase(this.mDownloadUrl)));
      File localFile3 = new File(localFile2, this.mFileName);
      boolean bool1 = localFile3.exists();
      bool2 = false;
      if (!bool1)
        bool2 = downloadFile(this.mDownloadUrl, localFile3);
    }
    while (!bool2);
    GlobalCfg.getInstance(this.mContext).setSellUrl(this.mDownloadUrl);
    QTMSGManage.getInstance().sendStatistcsMessage("sellApps", this.mDownloadUrl);
    String str2 = GlobalCfg.getInstance(this.mContext).getSellAppsPackage();
    AppPublishLog.sendDownloadAppLog(this.mContext, str2);
  }

  private void downloadQT()
  {
    if ((this.mFileName == null) || (this.mDownloadUrl == null))
      return;
    Message localMessage = new Message();
    localMessage.what = 1;
    this.mHandler.sendMessage(localMessage);
    File localFile1 = Environment.getExternalStorageDirectory();
    File localFile2 = new File(localFile1 + File.separator + "QTUpgrade");
    if (!localFile2.exists())
      localFile2.mkdir();
    this.mDownNotification = new Notification(17301633, "蜻蜓升级版", System.currentTimeMillis());
    this.mDownNotification.flags = 2;
    this.mDownNotification.flags = 16;
    this.mContentView = new RemoteViews(this.mContext.getPackageName(), 2130903043);
    this.mDownPendingIntent = PendingIntent.getActivity(this.mContext, 0, new Intent(), 0);
    File localFile3 = new File(localFile2, this.mFileName);
    if (localFile3.exists());
    for (boolean bool = true; bool; bool = downloadFile(this.mDownloadUrl, localFile3))
    {
      this.msg.what = 1;
      Notification localNotification2 = new Notification(17301634, "最新蜻蜓下载成功", System.currentTimeMillis());
      localNotification2.flags = 2;
      localNotification2.flags = 16;
      Intent localIntent = new Intent("android.intent.action.VIEW");
      localIntent.setFlags(268435456);
      localIntent.setDataAndType(Uri.fromFile(localFile3), "application/vnd.android.package-archive");
      this.mContext.startActivity(localIntent);
      PendingIntent localPendingIntent2 = PendingIntent.getActivity(this.mContext, 0, localIntent, 0);
      localNotification2.setLatestEventInfo(this.mContext, "蜻蜓fm下载成功", null, localPendingIntent2);
      this.mNotifManager.notify(0, localNotification2);
      return;
    }
    this.msg.what = 2;
    Notification localNotification1 = new Notification(17301634, "蜻蜓fm下载失败", System.currentTimeMillis());
    localNotification1.flags = 16;
    PendingIntent localPendingIntent1 = PendingIntent.getActivity(this.mContext, 0, new Intent(), 0);
    localNotification1.setLatestEventInfo(this.mContext, "蜻蜓fm下载失败", null, localPendingIntent1);
    this.mNotifManager.notify(0, localNotification1);
  }

  private void log(String paramString)
  {
  }

  public boolean downloadFile(String paramString, File paramFile)
  {
    int i = 0;
    try
    {
      HttpURLConnection localHttpURLConnection = (HttpURLConnection)new URL(paramString).openConnection();
      if (localHttpURLConnection == null)
        return false;
      localHttpURLConnection.setReadTimeout(10000);
      localHttpURLConnection.setRequestMethod("GET");
      localHttpURLConnection.setDoInput(true);
      localHttpURLConnection.connect();
      if (localHttpURLConnection.getResponseCode() == 200)
      {
        int j = localHttpURLConnection.getContentLength();
        InputStream localInputStream = localHttpURLConnection.getInputStream();
        FileOutputStream localFileOutputStream = new FileOutputStream(paramFile);
        byte[] arrayOfByte = new byte[1024];
        int k = -1;
        while (true)
        {
          int m = localInputStream.read(arrayOfByte);
          if (m != -1)
          {
            int n = i + m;
            int i1 = (int)(100.0D * n / j);
            localFileOutputStream.write(arrayOfByte, 0, m);
            try
            {
              if (!this.mSilent)
              {
                if (n != j)
                  break label176;
                this.mNotifManager.cancel(2131230740);
              }
              while (true)
              {
                i = n;
                break;
                label176: if (k != i1)
                {
                  this.mContentView.setTextViewText(2131230739, i1 + "%");
                  this.mContentView.setProgressBar(2131230740, 100, i1, false);
                  this.mDownNotification.contentView = this.mContentView;
                  this.mDownNotification.contentIntent = this.mDownPendingIntent;
                  this.mNotifManager.notify(0, this.mDownNotification);
                  k = i1;
                }
              }
            }
            finally
            {
            }
          }
        }
        localFileOutputStream.flush();
        localFileOutputStream.close();
        localInputStream.close();
        return true;
      }
      return false;
    }
    catch (Exception localException)
    {
    }
    return false;
  }

  public void run()
  {
    try
    {
      Context localContext = this.mContext;
      if (localContext == null)
        return;
      if (Environment.getExternalStorageState().equals("mounted"))
      {
        if (this.mSilent)
        {
          downloadApp();
          return;
        }
        downloadQT();
      }
      while (true)
      {
        return;
        this.msg.what = 2;
      }
    }
    catch (Exception localException)
    {
      this.msg.what = 2;
      return;
    }
    finally
    {
      if (this.mHandler != null)
        this.mHandler.sendMessage(this.msg);
    }
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.download.HttpDownloadHelper
 * JD-Core Version:    0.6.2
 */