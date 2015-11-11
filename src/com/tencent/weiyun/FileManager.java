package com.tencent.weiyun;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.SystemClock;
import android.text.TextUtils;
import com.tencent.connect.auth.QQAuth;
import com.tencent.connect.auth.QQToken;
import com.tencent.connect.common.BaseApi;
import com.tencent.connect.common.BaseApi.TempRequestListener;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.UiError;
import com.tencent.utils.DataConvert;
import com.tencent.utils.HttpUtils;
import com.tencent.utils.HttpUtils.HttpStatusException;
import com.tencent.utils.HttpUtils.NetworkUnavailableException;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class FileManager extends BaseApi
{
  private static final String[] a = { "https://graph.qq.com/weiyun/get_photo_list", "https://graph.qq.com/weiyun/get_music_list", "https://graph.qq.com/weiyun/get_video_list" };
  private static final String[] b = { "https://graph.qq.com/weiyun/delete_photo", "https://graph.qq.com/weiyun/delete_music", "https://graph.qq.com/weiyun/delete_video" };

  public FileManager(Context paramContext, QQAuth paramQQAuth, QQToken paramQQToken)
  {
    super(paramContext, paramQQAuth, paramQQToken);
  }

  public FileManager(Context paramContext, QQToken paramQQToken)
  {
    super(paramContext, paramQQToken);
  }

  public void deleteFile(WeiyunFileType paramWeiyunFileType, String paramString, IUiListener paramIUiListener)
  {
    String str = b[paramWeiyunFileType.value()];
    Bundle localBundle = composeCGIParams();
    localBundle.putString("file_id", paramString);
    BaseApi.TempRequestListener localTempRequestListener = new BaseApi.TempRequestListener(this, paramIUiListener);
    HttpUtils.requestAsync(this.mToken, this.mContext, str, localBundle, "GET", localTempRequestListener);
  }

  public void downLoadFile(WeiyunFileType paramWeiyunFileType, WeiyunFile paramWeiyunFile, String paramString, IDownLoadFileCallBack paramIDownLoadFileCallBack)
  {
    new DownLoadImp(this.mContext, paramWeiyunFileType, paramWeiyunFile, paramString, paramIDownLoadFileCallBack).start();
  }

  public void downLoadThumb(WeiyunFile paramWeiyunFile, String paramString1, String paramString2, IDownLoadFileCallBack paramIDownLoadFileCallBack)
  {
    DownLoadImp localDownLoadImp = new DownLoadImp(this.mContext, WeiyunFileType.ImageFile, paramWeiyunFile, paramString1, paramIDownLoadFileCallBack);
    localDownLoadImp.setThumbSize(paramString2);
    localDownLoadImp.start();
  }

  public void getFileList(WeiyunFileType paramWeiyunFileType, IGetFileListListener paramIGetFileListListener)
  {
    String str = a[paramWeiyunFileType.value()];
    Bundle localBundle = composeCGIParams();
    localBundle.putString("offset", "0");
    localBundle.putString("number", "100");
    BaseApi.TempRequestListener localTempRequestListener = new BaseApi.TempRequestListener(this, new GetFileListListener(paramIGetFileListListener));
    HttpUtils.requestAsync(this.mToken, this.mContext, str, localBundle, "GET", localTempRequestListener);
  }

  public void uploadFile(WeiyunFileType paramWeiyunFileType, String paramString, IUploadFileCallBack paramIUploadFileCallBack)
  {
    new UploadFileImp(this.mContext, paramWeiyunFileType, paramString, paramIUploadFileCallBack).start();
  }

  private class DownLoadImp
  {
    private static final String DOWNLOAD_COOKIE_NAME = "dl_cookie_name";
    private static final String DOWNLOAD_COOKIE_VALUE = "dl_cookie_value";
    private static final String DOWNLOAD_ENCRYPT_URL = "dl_encrypt_url";
    private static final String DOWNLOAD_MUSIC_URL = "https://graph.qq.com/weiyun/download_music";
    private static final String DOWNLOAD_PIC_URL = "https://graph.qq.com/weiyun/download_photo";
    private static final int DOWNLOAD_PROGRESS = 1;
    private static final int DOWNLOAD_PROGRESS_DONE = 2;
    private static final String DOWNLOAD_SERVER_HOST = "dl_svr_host";
    private static final String DOWNLOAD_SERVER_PORT = "dl_svr_port";
    private static final String DOWNLOAD_THUMB_SIZE = "dl_thumb_size";
    private static final String DOWNLOAD_THUMB_URL = "https://graph.qq.com/weiyun/get_photo_thumb";
    private static final String DOWNLOAD_VIDEO_URL = "https://graph.qq.com/weiyun/download_video";
    private static final int GET_PERMISSON_DOWN = 0;
    private static final int MAX_ERROR_TIMES = 10;
    private IDownLoadFileCallBack mCallback;
    private Context mContext;
    private String mDownloadCookieName;
    private String mDownloadCookieValue;
    private String mDownloadEncryptUrl;
    private String mDownloadServerHost;
    private int mDownloadServerPort;
    private String mDownloadThumbSize;
    private File mFile;
    private FileManager.WeiyunFileType mFileType;
    private Handler mHandler;
    private String mSavePath;
    private String mThumbSize;
    private WeiyunFile mWeiyunFile;

    public DownLoadImp(Context paramWeiyunFileType, FileManager.WeiyunFileType paramWeiyunFile, WeiyunFile paramString, String paramIDownLoadFileCallBack, IDownLoadFileCallBack arg6)
    {
      this.mContext = paramWeiyunFileType;
      this.mFileType = paramWeiyunFile;
      this.mWeiyunFile = paramString;
      this.mSavePath = paramIDownLoadFileCallBack;
      Object localObject;
      this.mCallback = localObject;
      this.mHandler = new Handler(this.mContext.getMainLooper())
      {
        public void handleMessage(Message paramAnonymousMessage)
        {
          switch (paramAnonymousMessage.what)
          {
          default:
            FileManager.DownLoadImp.this.mCallback.onError(new UiError(paramAnonymousMessage.what, (String)paramAnonymousMessage.obj, null));
            return;
          case 0:
            JSONObject localJSONObject1 = (JSONObject)paramAnonymousMessage.obj;
            try
            {
              int j = localJSONObject1.getInt("ret");
              if (j != 0)
              {
                FileManager.DownLoadImp.this.mCallback.onError(new UiError(j, localJSONObject1.toString(), null));
                return;
              }
            }
            catch (JSONException localJSONException)
            {
              FileManager.DownLoadImp.this.mCallback.onError(new UiError(-4, localJSONException.getMessage(), null));
              return;
            }
            JSONObject localJSONObject2 = localJSONObject1.getJSONObject("data");
            FileManager.DownLoadImp.access$1602(FileManager.DownLoadImp.this, localJSONObject2.getString("dl_encrypt_url"));
            FileManager.DownLoadImp.access$1702(FileManager.DownLoadImp.this, localJSONObject2.getString("dl_cookie_name"));
            FileManager.DownLoadImp.access$1802(FileManager.DownLoadImp.this, localJSONObject2.getString("dl_cookie_value"));
            FileManager.DownLoadImp.access$1902(FileManager.DownLoadImp.this, localJSONObject2.getInt("dl_svr_port"));
            FileManager.DownLoadImp.access$2002(FileManager.DownLoadImp.this, localJSONObject2.getString("dl_svr_host"));
            if (localJSONObject2.has("dl_thumb_size"))
              FileManager.DownLoadImp.access$2102(FileManager.DownLoadImp.this, localJSONObject2.getString("dl_thumb_size"));
            FileManager.DownLoadImp.this.mCallback.onDownloadStart();
            FileManager.DownLoadImp.this.doDownload();
            return;
          case 1:
            int i = Integer.parseInt((String)paramAnonymousMessage.obj);
            FileManager.DownLoadImp.this.mCallback.onDownloadProgress(i);
            return;
          case 2:
          }
          FileManager.DownLoadImp.this.mCallback.onDownloadSuccess(FileManager.DownLoadImp.this.mSavePath);
        }
      };
    }

    private void doDownload()
    {
      new Thread()
      {
        // ERROR //
        public void run()
        {
          // Byte code:
          //   0: new 26	org/apache/http/params/BasicHttpParams
          //   3: dup
          //   4: invokespecial 27	org/apache/http/params/BasicHttpParams:<init>	()V
          //   7: astore_1
          //   8: aload_1
          //   9: sipush 15000
          //   12: invokestatic 33	org/apache/http/params/HttpConnectionParams:setConnectionTimeout	(Lorg/apache/http/params/HttpParams;I)V
          //   15: aload_1
          //   16: sipush 20000
          //   19: invokestatic 36	org/apache/http/params/HttpConnectionParams:setSoTimeout	(Lorg/apache/http/params/HttpParams;I)V
          //   22: aload_1
          //   23: getstatic 42	org/apache/http/HttpVersion:HTTP_1_1	Lorg/apache/http/HttpVersion;
          //   26: invokestatic 48	org/apache/http/params/HttpProtocolParams:setVersion	(Lorg/apache/http/params/HttpParams;Lorg/apache/http/ProtocolVersion;)V
          //   29: aload_1
          //   30: ldc 50
          //   32: invokestatic 54	org/apache/http/params/HttpProtocolParams:setContentCharset	(Lorg/apache/http/params/HttpParams;Ljava/lang/String;)V
          //   35: aload_1
          //   36: ldc 56
          //   38: invokestatic 59	org/apache/http/params/HttpProtocolParams:setUserAgent	(Lorg/apache/http/params/HttpParams;Ljava/lang/String;)V
          //   41: new 61	java/lang/StringBuilder
          //   44: dup
          //   45: invokespecial 62	java/lang/StringBuilder:<init>	()V
          //   48: ldc 64
          //   50: invokevirtual 68	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
          //   53: aload_0
          //   54: getfield 15	com/tencent/weiyun/FileManager$DownLoadImp$3:this$1	Lcom/tencent/weiyun/FileManager$DownLoadImp;
          //   57: invokestatic 72	com/tencent/weiyun/FileManager$DownLoadImp:access$2000	(Lcom/tencent/weiyun/FileManager$DownLoadImp;)Ljava/lang/String;
          //   60: invokevirtual 68	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
          //   63: ldc 74
          //   65: invokevirtual 68	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
          //   68: aload_0
          //   69: getfield 15	com/tencent/weiyun/FileManager$DownLoadImp$3:this$1	Lcom/tencent/weiyun/FileManager$DownLoadImp;
          //   72: invokestatic 78	com/tencent/weiyun/FileManager$DownLoadImp:access$1900	(Lcom/tencent/weiyun/FileManager$DownLoadImp;)I
          //   75: invokevirtual 81	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
          //   78: ldc 83
          //   80: invokevirtual 68	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
          //   83: aload_0
          //   84: getfield 15	com/tencent/weiyun/FileManager$DownLoadImp$3:this$1	Lcom/tencent/weiyun/FileManager$DownLoadImp;
          //   87: invokestatic 86	com/tencent/weiyun/FileManager$DownLoadImp:access$1600	(Lcom/tencent/weiyun/FileManager$DownLoadImp;)Ljava/lang/String;
          //   90: invokevirtual 68	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
          //   93: ldc 88
          //   95: invokevirtual 68	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
          //   98: invokevirtual 92	java/lang/StringBuilder:toString	()Ljava/lang/String;
          //   101: astore_2
          //   102: aload_0
          //   103: getfield 15	com/tencent/weiyun/FileManager$DownLoadImp$3:this$1	Lcom/tencent/weiyun/FileManager$DownLoadImp;
          //   106: invokestatic 95	com/tencent/weiyun/FileManager$DownLoadImp:access$2100	(Lcom/tencent/weiyun/FileManager$DownLoadImp;)Ljava/lang/String;
          //   109: invokestatic 101	android/text/TextUtils:isEmpty	(Ljava/lang/CharSequence;)Z
          //   112: ifne +33 -> 145
          //   115: new 61	java/lang/StringBuilder
          //   118: dup
          //   119: invokespecial 62	java/lang/StringBuilder:<init>	()V
          //   122: aload_2
          //   123: invokevirtual 68	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
          //   126: ldc 103
          //   128: invokevirtual 68	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
          //   131: aload_0
          //   132: getfield 15	com/tencent/weiyun/FileManager$DownLoadImp$3:this$1	Lcom/tencent/weiyun/FileManager$DownLoadImp;
          //   135: invokestatic 95	com/tencent/weiyun/FileManager$DownLoadImp:access$2100	(Lcom/tencent/weiyun/FileManager$DownLoadImp;)Ljava/lang/String;
          //   138: invokevirtual 68	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
          //   141: invokevirtual 92	java/lang/StringBuilder:toString	()Ljava/lang/String;
          //   144: astore_2
          //   145: new 105	org/apache/http/impl/client/DefaultHttpClient
          //   148: dup
          //   149: aload_1
          //   150: invokespecial 108	org/apache/http/impl/client/DefaultHttpClient:<init>	(Lorg/apache/http/params/HttpParams;)V
          //   153: astore_3
          //   154: aconst_null
          //   155: astore 4
          //   157: new 110	java/io/File
          //   160: dup
          //   161: aload_0
          //   162: getfield 15	com/tencent/weiyun/FileManager$DownLoadImp$3:this$1	Lcom/tencent/weiyun/FileManager$DownLoadImp;
          //   165: invokestatic 113	com/tencent/weiyun/FileManager$DownLoadImp:access$2300	(Lcom/tencent/weiyun/FileManager$DownLoadImp;)Ljava/lang/String;
          //   168: invokespecial 116	java/io/File:<init>	(Ljava/lang/String;)V
          //   171: astore 5
          //   173: new 118	java/io/FileOutputStream
          //   176: dup
          //   177: aload 5
          //   179: invokespecial 121	java/io/FileOutputStream:<init>	(Ljava/io/File;)V
          //   182: astore 6
          //   184: ldc 122
          //   186: newarray byte
          //   188: astore 7
          //   190: aload_0
          //   191: getfield 15	com/tencent/weiyun/FileManager$DownLoadImp$3:this$1	Lcom/tencent/weiyun/FileManager$DownLoadImp;
          //   194: invokestatic 125	com/tencent/weiyun/FileManager$DownLoadImp:access$2600	(Lcom/tencent/weiyun/FileManager$DownLoadImp;)Ljava/lang/String;
          //   197: invokestatic 101	android/text/TextUtils:isEmpty	(Ljava/lang/CharSequence;)Z
          //   200: ifeq +663 -> 863
          //   203: aload_0
          //   204: getfield 15	com/tencent/weiyun/FileManager$DownLoadImp$3:this$1	Lcom/tencent/weiyun/FileManager$DownLoadImp;
          //   207: invokestatic 129	com/tencent/weiyun/FileManager$DownLoadImp:access$2500	(Lcom/tencent/weiyun/FileManager$DownLoadImp;)Lcom/tencent/weiyun/WeiyunFile;
          //   210: invokevirtual 135	com/tencent/weiyun/WeiyunFile:getFileSize	()J
          //   213: ldc2_w 136
          //   216: lcmp
          //   217: ifle +353 -> 570
          //   220: ldc2_w 136
          //   223: lstore 24
          //   225: lload 24
          //   227: lconst_0
          //   228: ladd
          //   229: lstore 26
          //   231: iconst_0
          //   232: istore 28
          //   234: lconst_0
          //   235: lstore 29
          //   237: aconst_null
          //   238: astore 17
          //   240: lload 26
          //   242: lstore 31
          //   244: lload 31
          //   246: aload_0
          //   247: getfield 15	com/tencent/weiyun/FileManager$DownLoadImp$3:this$1	Lcom/tencent/weiyun/FileManager$DownLoadImp;
          //   250: invokestatic 129	com/tencent/weiyun/FileManager$DownLoadImp:access$2500	(Lcom/tencent/weiyun/FileManager$DownLoadImp;)Lcom/tencent/weiyun/WeiyunFile;
          //   253: invokevirtual 135	com/tencent/weiyun/WeiyunFile:getFileSize	()J
          //   256: lcmp
          //   257: ifgt +422 -> 679
          //   260: new 139	org/apache/http/client/methods/HttpGet
          //   263: dup
          //   264: aload_2
          //   265: invokespecial 140	org/apache/http/client/methods/HttpGet:<init>	(Ljava/lang/String;)V
          //   268: astore 33
          //   270: aload 33
          //   272: ldc 142
          //   274: ldc 144
          //   276: invokeinterface 150 3 0
          //   281: aload 33
          //   283: ldc 152
          //   285: aload_0
          //   286: getfield 15	com/tencent/weiyun/FileManager$DownLoadImp$3:this$1	Lcom/tencent/weiyun/FileManager$DownLoadImp;
          //   289: invokestatic 72	com/tencent/weiyun/FileManager$DownLoadImp:access$2000	(Lcom/tencent/weiyun/FileManager$DownLoadImp;)Ljava/lang/String;
          //   292: invokeinterface 150 3 0
          //   297: aload 33
          //   299: ldc 154
          //   301: ldc 156
          //   303: invokeinterface 150 3 0
          //   308: aload 33
          //   310: ldc 158
          //   312: new 61	java/lang/StringBuilder
          //   315: dup
          //   316: invokespecial 62	java/lang/StringBuilder:<init>	()V
          //   319: aload_0
          //   320: getfield 15	com/tencent/weiyun/FileManager$DownLoadImp$3:this$1	Lcom/tencent/weiyun/FileManager$DownLoadImp;
          //   323: invokestatic 161	com/tencent/weiyun/FileManager$DownLoadImp:access$1700	(Lcom/tencent/weiyun/FileManager$DownLoadImp;)Ljava/lang/String;
          //   326: invokevirtual 68	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
          //   329: ldc 163
          //   331: invokevirtual 68	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
          //   334: aload_0
          //   335: getfield 15	com/tencent/weiyun/FileManager$DownLoadImp$3:this$1	Lcom/tencent/weiyun/FileManager$DownLoadImp;
          //   338: invokestatic 166	com/tencent/weiyun/FileManager$DownLoadImp:access$1800	(Lcom/tencent/weiyun/FileManager$DownLoadImp;)Ljava/lang/String;
          //   341: invokevirtual 68	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
          //   344: invokevirtual 92	java/lang/StringBuilder:toString	()Ljava/lang/String;
          //   347: invokeinterface 150 3 0
          //   352: aload 33
          //   354: ldc 168
          //   356: ldc 170
          //   358: invokeinterface 150 3 0
          //   363: aload 33
          //   365: ldc 172
          //   367: new 61	java/lang/StringBuilder
          //   370: dup
          //   371: invokespecial 62	java/lang/StringBuilder:<init>	()V
          //   374: ldc 174
          //   376: invokevirtual 68	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
          //   379: lload 29
          //   381: invokevirtual 177	java/lang/StringBuilder:append	(J)Ljava/lang/StringBuilder;
          //   384: ldc 179
          //   386: invokevirtual 68	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
          //   389: lload 31
          //   391: invokevirtual 177	java/lang/StringBuilder:append	(J)Ljava/lang/StringBuilder;
          //   394: ldc 181
          //   396: invokevirtual 68	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
          //   399: invokevirtual 92	java/lang/StringBuilder:toString	()Ljava/lang/String;
          //   402: invokeinterface 150 3 0
          //   407: aload_3
          //   408: aload 33
          //   410: invokeinterface 187 2 0
          //   415: astore 42
          //   417: ldc 189
          //   419: new 61	java/lang/StringBuilder
          //   422: dup
          //   423: invokespecial 62	java/lang/StringBuilder:<init>	()V
          //   426: ldc 191
          //   428: invokevirtual 68	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
          //   431: aload 42
          //   433: invokevirtual 194	java/lang/Object:toString	()Ljava/lang/String;
          //   436: invokevirtual 68	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
          //   439: invokevirtual 92	java/lang/StringBuilder:toString	()Ljava/lang/String;
          //   442: invokestatic 200	android/util/Log:i	(Ljava/lang/String;Ljava/lang/String;)I
          //   445: pop
          //   446: aload 42
          //   448: invokeinterface 206 1 0
          //   453: invokeinterface 212 1 0
          //   458: istore 44
          //   460: iload 44
          //   462: sipush 200
          //   465: if_icmpeq +11 -> 476
          //   468: iload 44
          //   470: sipush 206
          //   473: if_icmpne +206 -> 679
          //   476: aload 42
          //   478: invokeinterface 216 1 0
          //   483: invokeinterface 222 1 0
          //   488: astore 17
          //   490: aload 17
          //   492: aload 7
          //   494: invokevirtual 228	java/io/InputStream:read	([B)I
          //   497: istore 45
          //   499: iload 45
          //   501: ifle +220 -> 721
          //   504: aload 6
          //   506: aload 7
          //   508: iconst_0
          //   509: iload 45
          //   511: invokevirtual 232	java/io/FileOutputStream:write	([BII)V
          //   514: lload 29
          //   516: iload 45
          //   518: i2l
          //   519: ladd
          //   520: lstore 29
          //   522: goto -32 -> 490
          //   525: astore 46
          //   527: aload_0
          //   528: getfield 15	com/tencent/weiyun/FileManager$DownLoadImp$3:this$1	Lcom/tencent/weiyun/FileManager$DownLoadImp;
          //   531: invokestatic 236	com/tencent/weiyun/FileManager$DownLoadImp:access$3100	(Lcom/tencent/weiyun/FileManager$DownLoadImp;)Landroid/os/Handler;
          //   534: invokevirtual 242	android/os/Handler:obtainMessage	()Landroid/os/Message;
          //   537: astore 47
          //   539: aload 47
          //   541: bipush 254
          //   543: putfield 248	android/os/Message:what	I
          //   546: aload 47
          //   548: aload 46
          //   550: invokevirtual 251	java/io/FileNotFoundException:getMessage	()Ljava/lang/String;
          //   553: putfield 255	android/os/Message:obj	Ljava/lang/Object;
          //   556: aload_0
          //   557: getfield 15	com/tencent/weiyun/FileManager$DownLoadImp$3:this$1	Lcom/tencent/weiyun/FileManager$DownLoadImp;
          //   560: invokestatic 236	com/tencent/weiyun/FileManager$DownLoadImp:access$3100	(Lcom/tencent/weiyun/FileManager$DownLoadImp;)Landroid/os/Handler;
          //   563: aload 47
          //   565: invokevirtual 259	android/os/Handler:sendMessage	(Landroid/os/Message;)Z
          //   568: pop
          //   569: return
          //   570: aload_0
          //   571: getfield 15	com/tencent/weiyun/FileManager$DownLoadImp$3:this$1	Lcom/tencent/weiyun/FileManager$DownLoadImp;
          //   574: invokestatic 129	com/tencent/weiyun/FileManager$DownLoadImp:access$2500	(Lcom/tencent/weiyun/FileManager$DownLoadImp;)Lcom/tencent/weiyun/WeiyunFile;
          //   577: invokevirtual 135	com/tencent/weiyun/WeiyunFile:getFileSize	()J
          //   580: lstore 24
          //   582: goto -357 -> 225
          //   585: astore 34
          //   587: iinc 28 1
          //   590: iload 28
          //   592: bipush 10
          //   594: if_icmple +127 -> 721
          //   597: aload 34
          //   599: invokevirtual 262	java/lang/Exception:printStackTrace	()V
          //   602: ldc 189
          //   604: new 61	java/lang/StringBuilder
          //   607: dup
          //   608: invokespecial 62	java/lang/StringBuilder:<init>	()V
          //   611: ldc_w 264
          //   614: invokevirtual 68	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
          //   617: aload 34
          //   619: invokevirtual 265	java/lang/Exception:getMessage	()Ljava/lang/String;
          //   622: invokevirtual 68	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
          //   625: ldc 181
          //   627: invokevirtual 68	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
          //   630: invokevirtual 92	java/lang/StringBuilder:toString	()Ljava/lang/String;
          //   633: invokestatic 268	android/util/Log:e	(Ljava/lang/String;Ljava/lang/String;)I
          //   636: pop
          //   637: aload_0
          //   638: getfield 15	com/tencent/weiyun/FileManager$DownLoadImp$3:this$1	Lcom/tencent/weiyun/FileManager$DownLoadImp;
          //   641: invokestatic 236	com/tencent/weiyun/FileManager$DownLoadImp:access$3100	(Lcom/tencent/weiyun/FileManager$DownLoadImp;)Landroid/os/Handler;
          //   644: invokevirtual 242	android/os/Handler:obtainMessage	()Landroid/os/Message;
          //   647: astore 40
          //   649: aload 40
          //   651: bipush 254
          //   653: putfield 248	android/os/Message:what	I
          //   656: aload 40
          //   658: aload 34
          //   660: invokevirtual 265	java/lang/Exception:getMessage	()Ljava/lang/String;
          //   663: putfield 255	android/os/Message:obj	Ljava/lang/Object;
          //   666: aload_0
          //   667: getfield 15	com/tencent/weiyun/FileManager$DownLoadImp$3:this$1	Lcom/tencent/weiyun/FileManager$DownLoadImp;
          //   670: invokestatic 236	com/tencent/weiyun/FileManager$DownLoadImp:access$3100	(Lcom/tencent/weiyun/FileManager$DownLoadImp;)Landroid/os/Handler;
          //   673: aload 40
          //   675: invokevirtual 259	android/os/Handler:sendMessage	(Landroid/os/Message;)Z
          //   678: pop
          //   679: aload 6
          //   681: invokevirtual 271	java/io/FileOutputStream:close	()V
          //   684: aload 17
          //   686: invokevirtual 272	java/io/InputStream:close	()V
          //   689: aload_0
          //   690: getfield 15	com/tencent/weiyun/FileManager$DownLoadImp$3:this$1	Lcom/tencent/weiyun/FileManager$DownLoadImp;
          //   693: invokestatic 236	com/tencent/weiyun/FileManager$DownLoadImp:access$3100	(Lcom/tencent/weiyun/FileManager$DownLoadImp;)Landroid/os/Handler;
          //   696: invokevirtual 242	android/os/Handler:obtainMessage	()Landroid/os/Message;
          //   699: astore 21
          //   701: aload 21
          //   703: iconst_2
          //   704: putfield 248	android/os/Message:what	I
          //   707: aload_0
          //   708: getfield 15	com/tencent/weiyun/FileManager$DownLoadImp$3:this$1	Lcom/tencent/weiyun/FileManager$DownLoadImp;
          //   711: invokestatic 236	com/tencent/weiyun/FileManager$DownLoadImp:access$3100	(Lcom/tencent/weiyun/FileManager$DownLoadImp;)Landroid/os/Handler;
          //   714: aload 21
          //   716: invokevirtual 259	android/os/Handler:sendMessage	(Landroid/os/Message;)Z
          //   719: pop
          //   720: return
          //   721: aload_0
          //   722: getfield 15	com/tencent/weiyun/FileManager$DownLoadImp$3:this$1	Lcom/tencent/weiyun/FileManager$DownLoadImp;
          //   725: invokestatic 129	com/tencent/weiyun/FileManager$DownLoadImp:access$2500	(Lcom/tencent/weiyun/FileManager$DownLoadImp;)Lcom/tencent/weiyun/WeiyunFile;
          //   728: invokevirtual 135	com/tencent/weiyun/WeiyunFile:getFileSize	()J
          //   731: lload 31
          //   733: lsub
          //   734: lconst_0
          //   735: lcmp
          //   736: ifle -57 -> 679
          //   739: aload_0
          //   740: getfield 15	com/tencent/weiyun/FileManager$DownLoadImp$3:this$1	Lcom/tencent/weiyun/FileManager$DownLoadImp;
          //   743: invokestatic 129	com/tencent/weiyun/FileManager$DownLoadImp:access$2500	(Lcom/tencent/weiyun/FileManager$DownLoadImp;)Lcom/tencent/weiyun/WeiyunFile;
          //   746: invokevirtual 135	com/tencent/weiyun/WeiyunFile:getFileSize	()J
          //   749: lload 29
          //   751: lsub
          //   752: ldc2_w 136
          //   755: lcmp
          //   756: ifle +89 -> 845
          //   759: ldc2_w 136
          //   762: lstore 35
          //   764: lload 35
          //   766: lload 29
          //   768: ladd
          //   769: lstore 31
          //   771: aload_0
          //   772: getfield 15	com/tencent/weiyun/FileManager$DownLoadImp$3:this$1	Lcom/tencent/weiyun/FileManager$DownLoadImp;
          //   775: invokestatic 236	com/tencent/weiyun/FileManager$DownLoadImp:access$3100	(Lcom/tencent/weiyun/FileManager$DownLoadImp;)Landroid/os/Handler;
          //   778: invokevirtual 242	android/os/Handler:obtainMessage	()Landroid/os/Message;
          //   781: astore 37
          //   783: aload 37
          //   785: iconst_1
          //   786: putfield 248	android/os/Message:what	I
          //   789: aload 37
          //   791: new 61	java/lang/StringBuilder
          //   794: dup
          //   795: invokespecial 62	java/lang/StringBuilder:<init>	()V
          //   798: ldc2_w 273
          //   801: lload 31
          //   803: lmul
          //   804: aload_0
          //   805: getfield 15	com/tencent/weiyun/FileManager$DownLoadImp$3:this$1	Lcom/tencent/weiyun/FileManager$DownLoadImp;
          //   808: invokestatic 129	com/tencent/weiyun/FileManager$DownLoadImp:access$2500	(Lcom/tencent/weiyun/FileManager$DownLoadImp;)Lcom/tencent/weiyun/WeiyunFile;
          //   811: invokevirtual 135	com/tencent/weiyun/WeiyunFile:getFileSize	()J
          //   814: ldiv
          //   815: invokevirtual 177	java/lang/StringBuilder:append	(J)Ljava/lang/StringBuilder;
          //   818: ldc 181
          //   820: invokevirtual 68	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
          //   823: invokevirtual 92	java/lang/StringBuilder:toString	()Ljava/lang/String;
          //   826: putfield 255	android/os/Message:obj	Ljava/lang/Object;
          //   829: aload_0
          //   830: getfield 15	com/tencent/weiyun/FileManager$DownLoadImp$3:this$1	Lcom/tencent/weiyun/FileManager$DownLoadImp;
          //   833: invokestatic 236	com/tencent/weiyun/FileManager$DownLoadImp:access$3100	(Lcom/tencent/weiyun/FileManager$DownLoadImp;)Landroid/os/Handler;
          //   836: aload 37
          //   838: invokevirtual 259	android/os/Handler:sendMessage	(Landroid/os/Message;)Z
          //   841: pop
          //   842: goto -598 -> 244
          //   845: aload_0
          //   846: getfield 15	com/tencent/weiyun/FileManager$DownLoadImp$3:this$1	Lcom/tencent/weiyun/FileManager$DownLoadImp;
          //   849: invokestatic 129	com/tencent/weiyun/FileManager$DownLoadImp:access$2500	(Lcom/tencent/weiyun/FileManager$DownLoadImp;)Lcom/tencent/weiyun/WeiyunFile;
          //   852: invokevirtual 135	com/tencent/weiyun/WeiyunFile:getFileSize	()J
          //   855: lload 29
          //   857: lsub
          //   858: lstore 35
          //   860: goto -96 -> 764
          //   863: new 139	org/apache/http/client/methods/HttpGet
          //   866: dup
          //   867: aload_2
          //   868: invokespecial 140	org/apache/http/client/methods/HttpGet:<init>	(Ljava/lang/String;)V
          //   871: astore 8
          //   873: aload 8
          //   875: ldc 142
          //   877: ldc 144
          //   879: invokeinterface 150 3 0
          //   884: aload 8
          //   886: ldc 152
          //   888: aload_0
          //   889: getfield 15	com/tencent/weiyun/FileManager$DownLoadImp$3:this$1	Lcom/tencent/weiyun/FileManager$DownLoadImp;
          //   892: invokestatic 72	com/tencent/weiyun/FileManager$DownLoadImp:access$2000	(Lcom/tencent/weiyun/FileManager$DownLoadImp;)Ljava/lang/String;
          //   895: invokeinterface 150 3 0
          //   900: aload 8
          //   902: ldc 154
          //   904: ldc 156
          //   906: invokeinterface 150 3 0
          //   911: aload 8
          //   913: ldc 158
          //   915: new 61	java/lang/StringBuilder
          //   918: dup
          //   919: invokespecial 62	java/lang/StringBuilder:<init>	()V
          //   922: aload_0
          //   923: getfield 15	com/tencent/weiyun/FileManager$DownLoadImp$3:this$1	Lcom/tencent/weiyun/FileManager$DownLoadImp;
          //   926: invokestatic 161	com/tencent/weiyun/FileManager$DownLoadImp:access$1700	(Lcom/tencent/weiyun/FileManager$DownLoadImp;)Ljava/lang/String;
          //   929: invokevirtual 68	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
          //   932: ldc 163
          //   934: invokevirtual 68	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
          //   937: aload_0
          //   938: getfield 15	com/tencent/weiyun/FileManager$DownLoadImp$3:this$1	Lcom/tencent/weiyun/FileManager$DownLoadImp;
          //   941: invokestatic 166	com/tencent/weiyun/FileManager$DownLoadImp:access$1800	(Lcom/tencent/weiyun/FileManager$DownLoadImp;)Ljava/lang/String;
          //   944: invokevirtual 68	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
          //   947: ldc 181
          //   949: invokevirtual 68	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
          //   952: invokevirtual 92	java/lang/StringBuilder:toString	()Ljava/lang/String;
          //   955: invokeinterface 150 3 0
          //   960: aload 8
          //   962: ldc 168
          //   964: ldc 170
          //   966: invokeinterface 150 3 0
          //   971: aload_3
          //   972: aload 8
          //   974: invokeinterface 187 2 0
          //   979: astore 14
          //   981: ldc 189
          //   983: new 61	java/lang/StringBuilder
          //   986: dup
          //   987: invokespecial 62	java/lang/StringBuilder:<init>	()V
          //   990: ldc 191
          //   992: invokevirtual 68	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
          //   995: aload 14
          //   997: invokevirtual 194	java/lang/Object:toString	()Ljava/lang/String;
          //   1000: invokevirtual 68	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
          //   1003: invokevirtual 92	java/lang/StringBuilder:toString	()Ljava/lang/String;
          //   1006: invokestatic 200	android/util/Log:i	(Ljava/lang/String;Ljava/lang/String;)I
          //   1009: pop
          //   1010: aload 14
          //   1012: invokeinterface 206 1 0
          //   1017: invokeinterface 212 1 0
          //   1022: istore 16
          //   1024: iload 16
          //   1026: sipush 200
          //   1029: if_icmpeq +14 -> 1043
          //   1032: aconst_null
          //   1033: astore 4
          //   1035: iload 16
          //   1037: sipush 206
          //   1040: if_icmpne +142 -> 1182
          //   1043: aload 14
          //   1045: invokeinterface 216 1 0
          //   1050: invokeinterface 222 1 0
          //   1055: astore 4
          //   1057: aload 4
          //   1059: aload 7
          //   1061: invokevirtual 228	java/io/InputStream:read	([B)I
          //   1064: istore 23
          //   1066: iload 23
          //   1068: ifle +114 -> 1182
          //   1071: aload 6
          //   1073: aload 7
          //   1075: iconst_0
          //   1076: iload 23
          //   1078: invokevirtual 232	java/io/FileOutputStream:write	([BII)V
          //   1081: goto -24 -> 1057
          //   1084: astore 9
          //   1086: aload 9
          //   1088: invokevirtual 262	java/lang/Exception:printStackTrace	()V
          //   1091: ldc 189
          //   1093: new 61	java/lang/StringBuilder
          //   1096: dup
          //   1097: invokespecial 62	java/lang/StringBuilder:<init>	()V
          //   1100: ldc_w 264
          //   1103: invokevirtual 68	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
          //   1106: aload 9
          //   1108: invokevirtual 265	java/lang/Exception:getMessage	()Ljava/lang/String;
          //   1111: invokevirtual 68	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
          //   1114: ldc 181
          //   1116: invokevirtual 68	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
          //   1119: invokevirtual 92	java/lang/StringBuilder:toString	()Ljava/lang/String;
          //   1122: invokestatic 268	android/util/Log:e	(Ljava/lang/String;Ljava/lang/String;)I
          //   1125: pop
          //   1126: aload_0
          //   1127: getfield 15	com/tencent/weiyun/FileManager$DownLoadImp$3:this$1	Lcom/tencent/weiyun/FileManager$DownLoadImp;
          //   1130: invokestatic 236	com/tencent/weiyun/FileManager$DownLoadImp:access$3100	(Lcom/tencent/weiyun/FileManager$DownLoadImp;)Landroid/os/Handler;
          //   1133: invokevirtual 242	android/os/Handler:obtainMessage	()Landroid/os/Message;
          //   1136: astore 11
          //   1138: aload 11
          //   1140: bipush 254
          //   1142: putfield 248	android/os/Message:what	I
          //   1145: aload 11
          //   1147: aload 9
          //   1149: invokevirtual 265	java/lang/Exception:getMessage	()Ljava/lang/String;
          //   1152: putfield 255	android/os/Message:obj	Ljava/lang/Object;
          //   1155: aload_0
          //   1156: getfield 15	com/tencent/weiyun/FileManager$DownLoadImp$3:this$1	Lcom/tencent/weiyun/FileManager$DownLoadImp;
          //   1159: invokestatic 236	com/tencent/weiyun/FileManager$DownLoadImp:access$3100	(Lcom/tencent/weiyun/FileManager$DownLoadImp;)Landroid/os/Handler;
          //   1162: aload 11
          //   1164: invokevirtual 259	android/os/Handler:sendMessage	(Landroid/os/Message;)Z
          //   1167: pop
          //   1168: aload 6
          //   1170: invokevirtual 271	java/io/FileOutputStream:close	()V
          //   1173: aload 4
          //   1175: invokevirtual 272	java/io/InputStream:close	()V
          //   1178: return
          //   1179: astore 13
          //   1181: return
          //   1182: aload 4
          //   1184: astore 17
          //   1186: goto -507 -> 679
          //   1189: astore 18
          //   1191: aload_0
          //   1192: getfield 15	com/tencent/weiyun/FileManager$DownLoadImp$3:this$1	Lcom/tencent/weiyun/FileManager$DownLoadImp;
          //   1195: invokestatic 236	com/tencent/weiyun/FileManager$DownLoadImp:access$3100	(Lcom/tencent/weiyun/FileManager$DownLoadImp;)Landroid/os/Handler;
          //   1198: invokevirtual 242	android/os/Handler:obtainMessage	()Landroid/os/Message;
          //   1201: astore 19
          //   1203: aload 19
          //   1205: bipush 254
          //   1207: putfield 248	android/os/Message:what	I
          //   1210: aload 19
          //   1212: aload 18
          //   1214: invokevirtual 275	java/io/IOException:getMessage	()Ljava/lang/String;
          //   1217: putfield 255	android/os/Message:obj	Ljava/lang/Object;
          //   1220: aload_0
          //   1221: getfield 15	com/tencent/weiyun/FileManager$DownLoadImp$3:this$1	Lcom/tencent/weiyun/FileManager$DownLoadImp;
          //   1224: invokestatic 236	com/tencent/weiyun/FileManager$DownLoadImp:access$3100	(Lcom/tencent/weiyun/FileManager$DownLoadImp;)Landroid/os/Handler;
          //   1227: aload 19
          //   1229: invokevirtual 259	android/os/Handler:sendMessage	(Landroid/os/Message;)Z
          //   1232: pop
          //   1233: return
          //
          // Exception table:
          //   from	to	target	type
          //   173	184	525	java/io/FileNotFoundException
          //   407	460	585	java/lang/Exception
          //   476	490	585	java/lang/Exception
          //   490	499	585	java/lang/Exception
          //   504	514	585	java/lang/Exception
          //   971	1024	1084	java/lang/Exception
          //   1043	1057	1084	java/lang/Exception
          //   1057	1066	1084	java/lang/Exception
          //   1071	1081	1084	java/lang/Exception
          //   1168	1178	1179	java/io/IOException
          //   679	689	1189	java/io/IOException
        }
      }
      .start();
    }

    private void getDownloadPermission()
    {
      new Thread()
      {
        public void run()
        {
          Bundle localBundle = FileManager.c(FileManager.this);
          localBundle.putString("file_id", FileManager.DownLoadImp.this.mWeiyunFile.getFileId());
          if (!TextUtils.isEmpty(FileManager.DownLoadImp.this.mThumbSize))
            localBundle.putString("thumb", FileManager.DownLoadImp.this.mThumbSize);
          try
          {
            JSONObject localJSONObject = HttpUtils.request(FileManager.d(FileManager.this), FileManager.DownLoadImp.this.mContext, FileManager.DownLoadImp.this.getDownloadUrl(FileManager.DownLoadImp.this.mFileType), localBundle, "GET");
            Message localMessage6 = FileManager.DownLoadImp.this.mHandler.obtainMessage();
            localMessage6.what = 0;
            localMessage6.obj = localJSONObject;
            FileManager.DownLoadImp.this.mHandler.sendMessage(localMessage6);
            return;
          }
          catch (MalformedURLException localMalformedURLException)
          {
            Message localMessage5 = FileManager.DownLoadImp.this.mHandler.obtainMessage();
            localMessage5.what = -3;
            localMessage5.obj = localMalformedURLException.getMessage();
            FileManager.DownLoadImp.this.mHandler.sendMessage(localMessage5);
            return;
          }
          catch (IOException localIOException)
          {
            Message localMessage4 = FileManager.DownLoadImp.this.mHandler.obtainMessage();
            localMessage4.obj = localIOException.getMessage();
            localMessage4.what = -2;
            FileManager.DownLoadImp.this.mHandler.sendMessage(localMessage4);
            return;
          }
          catch (JSONException localJSONException)
          {
            Message localMessage3 = FileManager.DownLoadImp.this.mHandler.obtainMessage();
            localMessage3.obj = localJSONException.getMessage();
            localMessage3.what = -4;
            FileManager.DownLoadImp.this.mHandler.sendMessage(localMessage3);
            return;
          }
          catch (HttpUtils.NetworkUnavailableException localNetworkUnavailableException)
          {
            Message localMessage2 = FileManager.DownLoadImp.this.mHandler.obtainMessage();
            localMessage2.obj = localNetworkUnavailableException.getMessage();
            localMessage2.what = -10;
            FileManager.DownLoadImp.this.mHandler.sendMessage(localMessage2);
            return;
          }
          catch (HttpUtils.HttpStatusException localHttpStatusException)
          {
            Message localMessage1 = FileManager.DownLoadImp.this.mHandler.obtainMessage();
            localMessage1.obj = localHttpStatusException.getMessage();
            localMessage1.what = -9;
            FileManager.DownLoadImp.this.mHandler.sendMessage(localMessage1);
          }
        }
      }
      .start();
    }

    private String getDownloadUrl(FileManager.WeiyunFileType paramWeiyunFileType)
    {
      if (paramWeiyunFileType == FileManager.WeiyunFileType.ImageFile)
      {
        if (this.mThumbSize != null)
          return "https://graph.qq.com/weiyun/get_photo_thumb";
        return "https://graph.qq.com/weiyun/download_photo";
      }
      if (paramWeiyunFileType == FileManager.WeiyunFileType.MusicFile)
        return "https://graph.qq.com/weiyun/download_music";
      if (paramWeiyunFileType == FileManager.WeiyunFileType.VideoFile)
        return "https://graph.qq.com/weiyun/download_video";
      return "https://graph.qq.com/weiyun/download_photo";
    }

    public void setThumbSize(String paramString)
    {
      this.mThumbSize = paramString;
    }

    public void start()
    {
      if ((this.mSavePath == null) || (this.mFileType == null) || (this.mWeiyunFile == null) || (this.mWeiyunFile.getFileId() == null))
      {
        Message localMessage1 = this.mHandler.obtainMessage();
        localMessage1.what = -5;
        localMessage1.obj = new String("");
        this.mHandler.sendMessage(localMessage1);
        return;
      }
      this.mFile = new File(this.mSavePath);
      if (this.mFile.exists())
      {
        Message localMessage2 = this.mHandler.obtainMessage();
        localMessage2.what = -11;
        localMessage2.obj = new String("");
        this.mHandler.sendMessage(localMessage2);
        return;
      }
      this.mCallback.onPrepareStart();
      getDownloadPermission();
    }
  }

  private class GetFileListListener
    implements IUiListener
  {
    private IGetFileListListener mListener;

    public GetFileListListener(IGetFileListListener arg2)
    {
      Object localObject;
      this.mListener = localObject;
    }

    public void onCancel()
    {
    }

    public void onComplete(Object paramObject)
    {
      JSONObject localJSONObject1 = (JSONObject)paramObject;
      try
      {
        ArrayList localArrayList = new ArrayList();
        JSONObject localJSONObject2 = localJSONObject1.getJSONObject("data");
        if (!localJSONObject2.isNull("content"))
        {
          JSONArray localJSONArray = localJSONObject2.getJSONArray("content");
          for (int i = 0; i < localJSONArray.length(); i++)
          {
            JSONObject localJSONObject3 = localJSONArray.getJSONObject(i);
            localArrayList.add(new WeiyunFile(localJSONObject3.getString("file_id"), localJSONObject3.getString("file_name"), localJSONObject3.getString("file_ctime"), localJSONObject3.getInt("file_size")));
          }
        }
        this.mListener.onComplete(localArrayList);
        return;
      }
      catch (JSONException localJSONException)
      {
        this.mListener.onError(new UiError(-4, "服务器返回数据格式有误!", localJSONObject1.toString()));
      }
    }

    public void onError(UiError paramUiError)
    {
      this.mListener.onError(paramUiError);
    }
  }

  private class UploadFileImp
  {
    private static final int GET_PERMISSON_DONE = 0;
    private static final String UPLOAD_IMAGE_URL = "https://graph.qq.com/weiyun/upload_photo";
    private static final String UPLOAD_MUSIC_URL = "https://graph.qq.com/weiyun/upload_music";
    private static final int UPLOAD_PROGRESS = 1;
    private static final int UPLOAD_PROGRESS_DONE = 2;
    private static final String UPLOAD_VIDEO_URL = "https://graph.qq.com/weiyun/upload_video";
    private final IUploadFileCallBack mCallback;
    private final Context mContext;
    private String mFileKey;
    private final String mFilePath;
    private long mFileSize;
    private final FileManager.WeiyunFileType mFileType;
    private final Handler mHandler;
    private String mHost;
    private String mMD5Hash;
    private byte[] mUKey;

    public UploadFileImp(Context paramWeiyunFileType, FileManager.WeiyunFileType paramString, String paramIUploadFileCallBack, IUploadFileCallBack arg5)
    {
      this.mContext = paramWeiyunFileType;
      this.mFileType = paramString;
      this.mFilePath = paramIUploadFileCallBack;
      Object localObject;
      this.mCallback = localObject;
      this.mHandler = new Handler(this.mContext.getMainLooper())
      {
        public void handleMessage(Message paramAnonymousMessage)
        {
          switch (paramAnonymousMessage.what)
          {
          default:
            FileManager.UploadFileImp.this.mCallback.onError(new UiError(paramAnonymousMessage.what, (String)paramAnonymousMessage.obj, null));
            return;
          case 0:
            JSONObject localJSONObject1 = (JSONObject)paramAnonymousMessage.obj;
            try
            {
              int j = localJSONObject1.getInt("ret");
              if (j != 0)
              {
                FileManager.UploadFileImp.this.mCallback.onError(new UiError(j, localJSONObject1.toString(), null));
                return;
              }
            }
            catch (Exception localException)
            {
              FileManager.UploadFileImp.this.mCallback.onError(new UiError(-4, localException.getMessage(), null));
              return;
            }
            JSONObject localJSONObject2 = localJSONObject1.getJSONObject("data");
            String str = localJSONObject2.getString("csum");
            FileManager.UploadFileImp.access$102(FileManager.UploadFileImp.this, DataConvert.string2bytes(str));
            FileManager.UploadFileImp.access$202(FileManager.UploadFileImp.this, localJSONObject2.getString("host"));
            FileManager.UploadFileImp.this.mCallback.onUploadStart();
            FileManager.UploadFileImp.this.doUpload();
            return;
          case 1:
            int i = Integer.parseInt((String)paramAnonymousMessage.obj);
            FileManager.UploadFileImp.this.mCallback.onUploadProgress(i);
            return;
          case 2:
          }
          FileManager.UploadFileImp.this.mCallback.onUploadSuccess();
        }
      };
    }

    private void doUpload()
    {
      new Thread()
      {
        // ERROR //
        public void run()
        {
          // Byte code:
          //   0: new 24	org/apache/http/params/BasicHttpParams
          //   3: dup
          //   4: invokespecial 25	org/apache/http/params/BasicHttpParams:<init>	()V
          //   7: astore_1
          //   8: aload_1
          //   9: sipush 15000
          //   12: invokestatic 31	org/apache/http/params/HttpConnectionParams:setConnectionTimeout	(Lorg/apache/http/params/HttpParams;I)V
          //   15: aload_1
          //   16: sipush 20000
          //   19: invokestatic 34	org/apache/http/params/HttpConnectionParams:setSoTimeout	(Lorg/apache/http/params/HttpParams;I)V
          //   22: aload_1
          //   23: getstatic 40	org/apache/http/HttpVersion:HTTP_1_1	Lorg/apache/http/HttpVersion;
          //   26: invokestatic 46	org/apache/http/params/HttpProtocolParams:setVersion	(Lorg/apache/http/params/HttpParams;Lorg/apache/http/ProtocolVersion;)V
          //   29: aload_1
          //   30: ldc 48
          //   32: invokestatic 52	org/apache/http/params/HttpProtocolParams:setContentCharset	(Lorg/apache/http/params/HttpParams;Ljava/lang/String;)V
          //   35: aload_1
          //   36: ldc 54
          //   38: invokestatic 57	org/apache/http/params/HttpProtocolParams:setUserAgent	(Lorg/apache/http/params/HttpParams;Ljava/lang/String;)V
          //   41: new 59	org/apache/http/impl/client/DefaultHttpClient
          //   44: dup
          //   45: aload_1
          //   46: invokespecial 62	org/apache/http/impl/client/DefaultHttpClient:<init>	(Lorg/apache/http/params/HttpParams;)V
          //   49: astore_2
          //   50: ldc 63
          //   52: newarray byte
          //   54: astore_3
          //   55: new 65	java/io/FileInputStream
          //   58: dup
          //   59: aload_0
          //   60: getfield 15	com/tencent/weiyun/FileManager$UploadFileImp$3:this$1	Lcom/tencent/weiyun/FileManager$UploadFileImp;
          //   63: invokestatic 69	com/tencent/weiyun/FileManager$UploadFileImp:access$400	(Lcom/tencent/weiyun/FileManager$UploadFileImp;)Ljava/lang/String;
          //   66: invokespecial 72	java/io/FileInputStream:<init>	(Ljava/lang/String;)V
          //   69: astore 4
          //   71: iconst_0
          //   72: istore 5
          //   74: iload 5
          //   76: i2l
          //   77: aload_0
          //   78: getfield 15	com/tencent/weiyun/FileManager$UploadFileImp$3:this$1	Lcom/tencent/weiyun/FileManager$UploadFileImp;
          //   81: invokestatic 76	com/tencent/weiyun/FileManager$UploadFileImp:access$800	(Lcom/tencent/weiyun/FileManager$UploadFileImp;)J
          //   84: lcmp
          //   85: ifge +37 -> 122
          //   88: aload 4
          //   90: aload_3
          //   91: invokevirtual 80	java/io/FileInputStream:read	([B)I
          //   94: istore 12
          //   96: aload_0
          //   97: getfield 15	com/tencent/weiyun/FileManager$UploadFileImp$3:this$1	Lcom/tencent/weiyun/FileManager$UploadFileImp;
          //   100: aload_3
          //   101: iload 12
          //   103: iload 5
          //   105: invokestatic 84	com/tencent/weiyun/FileManager$UploadFileImp:access$1400	(Lcom/tencent/weiyun/FileManager$UploadFileImp;[BII)[B
          //   108: astore 13
          //   110: iload 5
          //   112: iload 12
          //   114: iadd
          //   115: istore 5
          //   117: aload 13
          //   119: ifnonnull +95 -> 214
          //   122: aload 4
          //   124: invokevirtual 87	java/io/FileInputStream:close	()V
          //   127: return
          //   128: astore 27
          //   130: aload_0
          //   131: getfield 15	com/tencent/weiyun/FileManager$UploadFileImp$3:this$1	Lcom/tencent/weiyun/FileManager$UploadFileImp;
          //   134: invokestatic 91	com/tencent/weiyun/FileManager$UploadFileImp:access$1300	(Lcom/tencent/weiyun/FileManager$UploadFileImp;)Landroid/os/Handler;
          //   137: invokevirtual 97	android/os/Handler:obtainMessage	()Landroid/os/Message;
          //   140: astore 28
          //   142: aload 28
          //   144: bipush 254
          //   146: putfield 103	android/os/Message:what	I
          //   149: aload 28
          //   151: ldc 105
          //   153: putfield 109	android/os/Message:obj	Ljava/lang/Object;
          //   156: aload_0
          //   157: getfield 15	com/tencent/weiyun/FileManager$UploadFileImp$3:this$1	Lcom/tencent/weiyun/FileManager$UploadFileImp;
          //   160: invokestatic 91	com/tencent/weiyun/FileManager$UploadFileImp:access$1300	(Lcom/tencent/weiyun/FileManager$UploadFileImp;)Landroid/os/Handler;
          //   163: aload 28
          //   165: invokevirtual 113	android/os/Handler:sendMessage	(Landroid/os/Message;)Z
          //   168: pop
          //   169: return
          //   170: astore 9
          //   172: aload_0
          //   173: getfield 15	com/tencent/weiyun/FileManager$UploadFileImp$3:this$1	Lcom/tencent/weiyun/FileManager$UploadFileImp;
          //   176: invokestatic 91	com/tencent/weiyun/FileManager$UploadFileImp:access$1300	(Lcom/tencent/weiyun/FileManager$UploadFileImp;)Landroid/os/Handler;
          //   179: invokevirtual 97	android/os/Handler:obtainMessage	()Landroid/os/Message;
          //   182: astore 10
          //   184: aload 10
          //   186: bipush 254
          //   188: putfield 103	android/os/Message:what	I
          //   191: aload 10
          //   193: ldc 105
          //   195: putfield 109	android/os/Message:obj	Ljava/lang/Object;
          //   198: aload_0
          //   199: getfield 15	com/tencent/weiyun/FileManager$UploadFileImp$3:this$1	Lcom/tencent/weiyun/FileManager$UploadFileImp;
          //   202: invokestatic 91	com/tencent/weiyun/FileManager$UploadFileImp:access$1300	(Lcom/tencent/weiyun/FileManager$UploadFileImp;)Landroid/os/Handler;
          //   205: aload 10
          //   207: invokevirtual 113	android/os/Handler:sendMessage	(Landroid/os/Message;)Z
          //   210: pop
          //   211: goto -89 -> 122
          //   214: new 115	org/apache/http/client/methods/HttpPost
          //   217: dup
          //   218: new 117	java/lang/StringBuilder
          //   221: dup
          //   222: invokespecial 118	java/lang/StringBuilder:<init>	()V
          //   225: ldc 120
          //   227: invokevirtual 124	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
          //   230: aload_0
          //   231: getfield 15	com/tencent/weiyun/FileManager$UploadFileImp$3:this$1	Lcom/tencent/weiyun/FileManager$UploadFileImp;
          //   234: invokestatic 127	com/tencent/weiyun/FileManager$UploadFileImp:access$200	(Lcom/tencent/weiyun/FileManager$UploadFileImp;)Ljava/lang/String;
          //   237: invokevirtual 124	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
          //   240: ldc 129
          //   242: invokevirtual 124	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
          //   245: aload_0
          //   246: getfield 15	com/tencent/weiyun/FileManager$UploadFileImp$3:this$1	Lcom/tencent/weiyun/FileManager$UploadFileImp;
          //   249: invokestatic 132	com/tencent/weiyun/FileManager$UploadFileImp:access$700	(Lcom/tencent/weiyun/FileManager$UploadFileImp;)Ljava/lang/String;
          //   252: invokevirtual 124	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
          //   255: invokevirtual 136	java/lang/StringBuilder:toString	()Ljava/lang/String;
          //   258: invokespecial 137	org/apache/http/client/methods/HttpPost:<init>	(Ljava/lang/String;)V
          //   261: astore 14
          //   263: aload 14
          //   265: ldc 139
          //   267: ldc 141
          //   269: invokevirtual 145	org/apache/http/client/methods/HttpPost:addHeader	(Ljava/lang/String;Ljava/lang/String;)V
          //   272: aload 14
          //   274: ldc 147
          //   276: ldc 149
          //   278: invokevirtual 152	org/apache/http/client/methods/HttpPost:setHeader	(Ljava/lang/String;Ljava/lang/String;)V
          //   281: aload 14
          //   283: ldc 154
          //   285: ldc 156
          //   287: invokevirtual 152	org/apache/http/client/methods/HttpPost:setHeader	(Ljava/lang/String;Ljava/lang/String;)V
          //   290: aload 14
          //   292: ldc 158
          //   294: ldc 160
          //   296: invokevirtual 152	org/apache/http/client/methods/HttpPost:setHeader	(Ljava/lang/String;Ljava/lang/String;)V
          //   299: aload 14
          //   301: new 162	org/apache/http/entity/ByteArrayEntity
          //   304: dup
          //   305: aload 13
          //   307: invokespecial 165	org/apache/http/entity/ByteArrayEntity:<init>	([B)V
          //   310: invokevirtual 169	org/apache/http/client/methods/HttpPost:setEntity	(Lorg/apache/http/HttpEntity;)V
          //   313: aload_2
          //   314: aload 14
          //   316: invokeinterface 175 2 0
          //   321: invokeinterface 181 1 0
          //   326: invokeinterface 187 1 0
          //   331: istore 26
          //   333: iload 26
          //   335: istore 18
          //   337: iload 18
          //   339: sipush 200
          //   342: if_icmpne +182 -> 524
          //   345: iload 5
          //   347: i2l
          //   348: aload_0
          //   349: getfield 15	com/tencent/weiyun/FileManager$UploadFileImp$3:this$1	Lcom/tencent/weiyun/FileManager$UploadFileImp;
          //   352: invokestatic 76	com/tencent/weiyun/FileManager$UploadFileImp:access$800	(Lcom/tencent/weiyun/FileManager$UploadFileImp;)J
          //   355: lcmp
          //   356: ifge +127 -> 483
          //   359: ldc2_w 188
          //   362: iload 5
          //   364: i2l
          //   365: lmul
          //   366: aload_0
          //   367: getfield 15	com/tencent/weiyun/FileManager$UploadFileImp$3:this$1	Lcom/tencent/weiyun/FileManager$UploadFileImp;
          //   370: invokestatic 76	com/tencent/weiyun/FileManager$UploadFileImp:access$800	(Lcom/tencent/weiyun/FileManager$UploadFileImp;)J
          //   373: ldiv
          //   374: l2i
          //   375: istore 23
          //   377: aload_0
          //   378: getfield 15	com/tencent/weiyun/FileManager$UploadFileImp$3:this$1	Lcom/tencent/weiyun/FileManager$UploadFileImp;
          //   381: invokestatic 91	com/tencent/weiyun/FileManager$UploadFileImp:access$1300	(Lcom/tencent/weiyun/FileManager$UploadFileImp;)Landroid/os/Handler;
          //   384: invokevirtual 97	android/os/Handler:obtainMessage	()Landroid/os/Message;
          //   387: astore 24
          //   389: aload 24
          //   391: iconst_1
          //   392: putfield 103	android/os/Message:what	I
          //   395: aload 24
          //   397: new 117	java/lang/StringBuilder
          //   400: dup
          //   401: invokespecial 118	java/lang/StringBuilder:<init>	()V
          //   404: iload 23
          //   406: invokevirtual 192	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
          //   409: ldc 105
          //   411: invokevirtual 124	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
          //   414: invokevirtual 136	java/lang/StringBuilder:toString	()Ljava/lang/String;
          //   417: putfield 109	android/os/Message:obj	Ljava/lang/Object;
          //   420: aload_0
          //   421: getfield 15	com/tencent/weiyun/FileManager$UploadFileImp$3:this$1	Lcom/tencent/weiyun/FileManager$UploadFileImp;
          //   424: invokestatic 91	com/tencent/weiyun/FileManager$UploadFileImp:access$1300	(Lcom/tencent/weiyun/FileManager$UploadFileImp;)Landroid/os/Handler;
          //   427: aload 24
          //   429: invokevirtual 113	android/os/Handler:sendMessage	(Landroid/os/Message;)Z
          //   432: pop
          //   433: goto -359 -> 74
          //   436: astore 15
          //   438: aload_0
          //   439: getfield 15	com/tencent/weiyun/FileManager$UploadFileImp$3:this$1	Lcom/tencent/weiyun/FileManager$UploadFileImp;
          //   442: invokestatic 91	com/tencent/weiyun/FileManager$UploadFileImp:access$1300	(Lcom/tencent/weiyun/FileManager$UploadFileImp;)Landroid/os/Handler;
          //   445: invokevirtual 97	android/os/Handler:obtainMessage	()Landroid/os/Message;
          //   448: astore 16
          //   450: aload 16
          //   452: bipush 254
          //   454: putfield 103	android/os/Message:what	I
          //   457: aload 16
          //   459: ldc 105
          //   461: putfield 109	android/os/Message:obj	Ljava/lang/Object;
          //   464: aload_0
          //   465: getfield 15	com/tencent/weiyun/FileManager$UploadFileImp$3:this$1	Lcom/tencent/weiyun/FileManager$UploadFileImp;
          //   468: invokestatic 91	com/tencent/weiyun/FileManager$UploadFileImp:access$1300	(Lcom/tencent/weiyun/FileManager$UploadFileImp;)Landroid/os/Handler;
          //   471: aload 16
          //   473: invokevirtual 113	android/os/Handler:sendMessage	(Landroid/os/Message;)Z
          //   476: pop
          //   477: iconst_0
          //   478: istore 18
          //   480: goto -143 -> 337
          //   483: aload_0
          //   484: getfield 15	com/tencent/weiyun/FileManager$UploadFileImp$3:this$1	Lcom/tencent/weiyun/FileManager$UploadFileImp;
          //   487: invokestatic 91	com/tencent/weiyun/FileManager$UploadFileImp:access$1300	(Lcom/tencent/weiyun/FileManager$UploadFileImp;)Landroid/os/Handler;
          //   490: invokevirtual 97	android/os/Handler:obtainMessage	()Landroid/os/Message;
          //   493: astore 21
          //   495: aload 21
          //   497: iconst_2
          //   498: putfield 103	android/os/Message:what	I
          //   501: aload 21
          //   503: ldc 105
          //   505: putfield 109	android/os/Message:obj	Ljava/lang/Object;
          //   508: aload_0
          //   509: getfield 15	com/tencent/weiyun/FileManager$UploadFileImp$3:this$1	Lcom/tencent/weiyun/FileManager$UploadFileImp;
          //   512: invokestatic 91	com/tencent/weiyun/FileManager$UploadFileImp:access$1300	(Lcom/tencent/weiyun/FileManager$UploadFileImp;)Landroid/os/Handler;
          //   515: aload 21
          //   517: invokevirtual 113	android/os/Handler:sendMessage	(Landroid/os/Message;)Z
          //   520: pop
          //   521: goto -447 -> 74
          //   524: aload_0
          //   525: getfield 15	com/tencent/weiyun/FileManager$UploadFileImp$3:this$1	Lcom/tencent/weiyun/FileManager$UploadFileImp;
          //   528: invokestatic 91	com/tencent/weiyun/FileManager$UploadFileImp:access$1300	(Lcom/tencent/weiyun/FileManager$UploadFileImp;)Landroid/os/Handler;
          //   531: invokevirtual 97	android/os/Handler:obtainMessage	()Landroid/os/Message;
          //   534: astore 19
          //   536: aload 19
          //   538: bipush 247
          //   540: putfield 103	android/os/Message:what	I
          //   543: aload 19
          //   545: ldc 105
          //   547: putfield 109	android/os/Message:obj	Ljava/lang/Object;
          //   550: aload_0
          //   551: getfield 15	com/tencent/weiyun/FileManager$UploadFileImp$3:this$1	Lcom/tencent/weiyun/FileManager$UploadFileImp;
          //   554: invokestatic 91	com/tencent/weiyun/FileManager$UploadFileImp:access$1300	(Lcom/tencent/weiyun/FileManager$UploadFileImp;)Landroid/os/Handler;
          //   557: aload 19
          //   559: invokevirtual 113	android/os/Handler:sendMessage	(Landroid/os/Message;)Z
          //   562: pop
          //   563: goto -441 -> 122
          //   566: astore 6
          //   568: aload_0
          //   569: getfield 15	com/tencent/weiyun/FileManager$UploadFileImp$3:this$1	Lcom/tencent/weiyun/FileManager$UploadFileImp;
          //   572: invokestatic 91	com/tencent/weiyun/FileManager$UploadFileImp:access$1300	(Lcom/tencent/weiyun/FileManager$UploadFileImp;)Landroid/os/Handler;
          //   575: invokevirtual 97	android/os/Handler:obtainMessage	()Landroid/os/Message;
          //   578: astore 7
          //   580: aload 7
          //   582: bipush 254
          //   584: putfield 103	android/os/Message:what	I
          //   587: aload 7
          //   589: aload 6
          //   591: invokevirtual 195	java/io/IOException:getMessage	()Ljava/lang/String;
          //   594: putfield 109	android/os/Message:obj	Ljava/lang/Object;
          //   597: aload_0
          //   598: getfield 15	com/tencent/weiyun/FileManager$UploadFileImp$3:this$1	Lcom/tencent/weiyun/FileManager$UploadFileImp;
          //   601: invokestatic 91	com/tencent/weiyun/FileManager$UploadFileImp:access$1300	(Lcom/tencent/weiyun/FileManager$UploadFileImp;)Landroid/os/Handler;
          //   604: aload 7
          //   606: invokevirtual 113	android/os/Handler:sendMessage	(Landroid/os/Message;)Z
          //   609: pop
          //   610: return
          //
          // Exception table:
          //   from	to	target	type
          //   55	71	128	java/io/FileNotFoundException
          //   88	110	170	java/io/IOException
          //   313	333	436	java/io/IOException
          //   122	127	566	java/io/IOException
        }
      }
      .start();
    }

    private String getRequestUrl(FileManager.WeiyunFileType paramWeiyunFileType)
    {
      if (paramWeiyunFileType == FileManager.WeiyunFileType.ImageFile)
        return "https://graph.qq.com/weiyun/upload_photo";
      if (paramWeiyunFileType == FileManager.WeiyunFileType.MusicFile)
        return "https://graph.qq.com/weiyun/upload_music";
      return "https://graph.qq.com/weiyun/upload_video";
    }

    private void getUploadPermission()
    {
      new Thread()
      {
        public void run()
        {
          Uri localUri = Uri.parse(FileManager.UploadFileImp.this.mFilePath);
          String str = SystemClock.elapsedRealtime() + "__" + localUri.getLastPathSegment();
          Bundle localBundle = FileManager.a(FileManager.this);
          localBundle.putString("sha", FileManager.UploadFileImp.this.mFileKey);
          localBundle.putString("md5", FileManager.UploadFileImp.this.mMD5Hash);
          localBundle.putString("size", FileManager.UploadFileImp.this.mFileSize + "");
          localBundle.putString("name", str);
          localBundle.putString("upload_type", "control");
          try
          {
            JSONObject localJSONObject = HttpUtils.request(FileManager.b(FileManager.this), FileManager.UploadFileImp.this.mContext, FileManager.UploadFileImp.this.getRequestUrl(FileManager.UploadFileImp.this.mFileType), localBundle, "GET");
            Message localMessage6 = FileManager.UploadFileImp.this.mHandler.obtainMessage();
            localMessage6.what = 0;
            localMessage6.obj = localJSONObject;
            FileManager.UploadFileImp.this.mHandler.sendMessage(localMessage6);
            return;
          }
          catch (MalformedURLException localMalformedURLException)
          {
            Message localMessage5 = FileManager.UploadFileImp.this.mHandler.obtainMessage();
            localMessage5.what = -3;
            localMessage5.obj = localMalformedURLException.getMessage();
            FileManager.UploadFileImp.this.mHandler.sendMessage(localMessage5);
            return;
          }
          catch (IOException localIOException)
          {
            Message localMessage4 = FileManager.UploadFileImp.this.mHandler.obtainMessage();
            localMessage4.obj = localIOException.getMessage();
            localMessage4.what = -2;
            FileManager.UploadFileImp.this.mHandler.sendMessage(localMessage4);
            return;
          }
          catch (JSONException localJSONException)
          {
            Message localMessage3 = FileManager.UploadFileImp.this.mHandler.obtainMessage();
            localMessage3.obj = localJSONException.getMessage();
            localMessage3.what = -4;
            FileManager.UploadFileImp.this.mHandler.sendMessage(localMessage3);
            return;
          }
          catch (HttpUtils.NetworkUnavailableException localNetworkUnavailableException)
          {
            Message localMessage2 = FileManager.UploadFileImp.this.mHandler.obtainMessage();
            localMessage2.obj = localNetworkUnavailableException.getMessage();
            localMessage2.what = -10;
            FileManager.UploadFileImp.this.mHandler.sendMessage(localMessage2);
            return;
          }
          catch (HttpUtils.HttpStatusException localHttpStatusException)
          {
            Message localMessage1 = FileManager.UploadFileImp.this.mHandler.obtainMessage();
            localMessage1.obj = localHttpStatusException.getMessage();
            localMessage1.what = -9;
            FileManager.UploadFileImp.this.mHandler.sendMessage(localMessage1);
          }
        }
      }
      .start();
    }

    private byte[] packPostBody(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
    {
      try
      {
        MessageDigest localMessageDigest = MessageDigest.getInstance("MD5");
        localMessageDigest.update(paramArrayOfByte, 0, paramInt1);
        this.mMD5Hash = DataConvert.toHexString(localMessageDigest.digest());
        localMessageDigest.reset();
        int i = paramInt1 + 340;
        byte[] arrayOfByte = new byte[4 + (4 + (4 + (i + 4)))];
        int j = 0 + DataConvert.putInt2Bytes(-1412589450, arrayOfByte, 0);
        int k = j + DataConvert.putInt2Bytes(1000, arrayOfByte, j);
        int m = k + DataConvert.putInt2Bytes(0, arrayOfByte, k);
        int n = m + DataConvert.putInt2Bytes(i, arrayOfByte, m);
        int i1 = n + DataConvert.putShort2Bytes(304, arrayOfByte, n);
        int i2 = i1 + DataConvert.putBytes2Bytes(this.mUKey, arrayOfByte, i1);
        int i3 = i2 + DataConvert.putShort2Bytes(20, arrayOfByte, i2);
        int i4 = i3 + DataConvert.putString2Bytes(this.mFileKey, arrayOfByte, i3);
        int i5 = i4 + DataConvert.putInt2Bytes((int)this.mFileSize, arrayOfByte, i4);
        int i6 = i5 + DataConvert.putInt2Bytes(paramInt2, arrayOfByte, i5);
        int i7 = i6 + DataConvert.putInt2Bytes(paramInt1, arrayOfByte, i6);
        (i7 + DataConvert.putBytes2Bytes(paramArrayOfByte, paramInt1, arrayOfByte, i7));
        return arrayOfByte;
      }
      catch (NoSuchAlgorithmException localNoSuchAlgorithmException)
      {
        Message localMessage = this.mHandler.obtainMessage();
        localMessage.what = -2;
        localMessage.obj = localNoSuchAlgorithmException.getMessage();
        this.mHandler.sendMessage(localMessage);
      }
      return null;
    }

    // ERROR //
    public void start()
    {
      // Byte code:
      //   0: aload_0
      //   1: getfield 52	com/tencent/weiyun/FileManager$UploadFileImp:mFilePath	Ljava/lang/String;
      //   4: ifnull +20 -> 24
      //   7: new 206	java/io/File
      //   10: dup
      //   11: aload_0
      //   12: getfield 52	com/tencent/weiyun/FileManager$UploadFileImp:mFilePath	Ljava/lang/String;
      //   15: invokespecial 209	java/io/File:<init>	(Ljava/lang/String;)V
      //   18: invokevirtual 213	java/io/File:exists	()Z
      //   21: ifne +40 -> 61
      //   24: aload_0
      //   25: getfield 67	com/tencent/weiyun/FileManager$UploadFileImp:mHandler	Landroid/os/Handler;
      //   28: invokevirtual 185	android/os/Handler:obtainMessage	()Landroid/os/Message;
      //   31: astore_1
      //   32: aload_1
      //   33: bipush 251
      //   35: putfield 190	android/os/Message:what	I
      //   38: aload_1
      //   39: new 215	java/lang/String
      //   42: dup
      //   43: ldc 217
      //   45: invokespecial 218	java/lang/String:<init>	(Ljava/lang/String;)V
      //   48: putfield 198	android/os/Message:obj	Ljava/lang/Object;
      //   51: aload_0
      //   52: getfield 67	com/tencent/weiyun/FileManager$UploadFileImp:mHandler	Landroid/os/Handler;
      //   55: aload_1
      //   56: invokevirtual 202	android/os/Handler:sendMessage	(Landroid/os/Message;)Z
      //   59: pop
      //   60: return
      //   61: aload_0
      //   62: getfield 54	com/tencent/weiyun/FileManager$UploadFileImp:mCallback	Lcom/tencent/weiyun/IUploadFileCallBack;
      //   65: invokeinterface 223 1 0
      //   70: new 206	java/io/File
      //   73: dup
      //   74: aload_0
      //   75: getfield 52	com/tencent/weiyun/FileManager$UploadFileImp:mFilePath	Ljava/lang/String;
      //   78: invokespecial 209	java/io/File:<init>	(Ljava/lang/String;)V
      //   81: astore_3
      //   82: aload_0
      //   83: aload_3
      //   84: invokevirtual 227	java/io/File:length	()J
      //   87: putfield 113	com/tencent/weiyun/FileManager$UploadFileImp:mFileSize	J
      //   90: ldc 229
      //   92: invokestatic 143	java/security/MessageDigest:getInstance	(Ljava/lang/String;)Ljava/security/MessageDigest;
      //   95: astore 7
      //   97: new 231	java/io/FileInputStream
      //   100: dup
      //   101: aload_3
      //   102: invokespecial 234	java/io/FileInputStream:<init>	(Ljava/io/File;)V
      //   105: astore 8
      //   107: new 236	java/security/DigestInputStream
      //   110: dup
      //   111: aload 8
      //   113: aload 7
      //   115: invokespecial 239	java/security/DigestInputStream:<init>	(Ljava/io/InputStream;Ljava/security/MessageDigest;)V
      //   118: astore 9
      //   120: ldc 240
      //   122: newarray byte
      //   124: astore 10
      //   126: aload 9
      //   128: aload 10
      //   130: invokevirtual 244	java/security/DigestInputStream:read	([B)I
      //   133: ifgt -7 -> 126
      //   136: aload 9
      //   138: invokevirtual 248	java/security/DigestInputStream:getMessageDigest	()Ljava/security/MessageDigest;
      //   141: astore 11
      //   143: aload_0
      //   144: aload 11
      //   146: invokevirtual 151	java/security/MessageDigest:digest	()[B
      //   149: invokestatic 157	com/tencent/utils/DataConvert:toHexString	([B)Ljava/lang/String;
      //   152: putfield 106	com/tencent/weiyun/FileManager$UploadFileImp:mFileKey	Ljava/lang/String;
      //   155: aload 11
      //   157: invokevirtual 160	java/security/MessageDigest:reset	()V
      //   160: aload 8
      //   162: invokevirtual 251	java/io/FileInputStream:close	()V
      //   165: aload 9
      //   167: invokevirtual 252	java/security/DigestInputStream:close	()V
      //   170: ldc 137
      //   172: invokestatic 143	java/security/MessageDigest:getInstance	(Ljava/lang/String;)Ljava/security/MessageDigest;
      //   175: astore 15
      //   177: new 231	java/io/FileInputStream
      //   180: dup
      //   181: aload_3
      //   182: invokespecial 234	java/io/FileInputStream:<init>	(Ljava/io/File;)V
      //   185: astore 16
      //   187: new 236	java/security/DigestInputStream
      //   190: dup
      //   191: aload 16
      //   193: aload 15
      //   195: invokespecial 239	java/security/DigestInputStream:<init>	(Ljava/io/InputStream;Ljava/security/MessageDigest;)V
      //   198: astore 17
      //   200: ldc 240
      //   202: newarray byte
      //   204: astore 18
      //   206: aload 17
      //   208: aload 18
      //   210: invokevirtual 244	java/security/DigestInputStream:read	([B)I
      //   213: ifgt -7 -> 206
      //   216: aload 17
      //   218: invokevirtual 248	java/security/DigestInputStream:getMessageDigest	()Ljava/security/MessageDigest;
      //   221: astore 19
      //   223: aload_0
      //   224: aload 19
      //   226: invokevirtual 151	java/security/MessageDigest:digest	()[B
      //   229: invokestatic 157	com/tencent/utils/DataConvert:toHexString	([B)Ljava/lang/String;
      //   232: putfield 109	com/tencent/weiyun/FileManager$UploadFileImp:mMD5Hash	Ljava/lang/String;
      //   235: aload 19
      //   237: invokevirtual 160	java/security/MessageDigest:reset	()V
      //   240: aload 16
      //   242: invokevirtual 251	java/io/FileInputStream:close	()V
      //   245: aload 17
      //   247: invokevirtual 252	java/security/DigestInputStream:close	()V
      //   250: aload_0
      //   251: invokespecial 254	com/tencent/weiyun/FileManager$UploadFileImp:getUploadPermission	()V
      //   254: return
      //   255: astore 4
      //   257: aload_0
      //   258: getfield 67	com/tencent/weiyun/FileManager$UploadFileImp:mHandler	Landroid/os/Handler;
      //   261: invokevirtual 185	android/os/Handler:obtainMessage	()Landroid/os/Message;
      //   264: astore 5
      //   266: aload 5
      //   268: bipush 254
      //   270: putfield 190	android/os/Message:what	I
      //   273: aload 5
      //   275: new 215	java/lang/String
      //   278: dup
      //   279: ldc 217
      //   281: invokespecial 218	java/lang/String:<init>	(Ljava/lang/String;)V
      //   284: putfield 198	android/os/Message:obj	Ljava/lang/Object;
      //   287: aload_0
      //   288: getfield 67	com/tencent/weiyun/FileManager$UploadFileImp:mHandler	Landroid/os/Handler;
      //   291: aload 5
      //   293: invokevirtual 202	android/os/Handler:sendMessage	(Landroid/os/Message;)Z
      //   296: pop
      //   297: return
      //   298: astore 12
      //   300: aload_0
      //   301: getfield 67	com/tencent/weiyun/FileManager$UploadFileImp:mHandler	Landroid/os/Handler;
      //   304: invokevirtual 185	android/os/Handler:obtainMessage	()Landroid/os/Message;
      //   307: astore 13
      //   309: aload 13
      //   311: bipush 254
      //   313: putfield 190	android/os/Message:what	I
      //   316: aload 13
      //   318: new 215	java/lang/String
      //   321: dup
      //   322: ldc 217
      //   324: invokespecial 218	java/lang/String:<init>	(Ljava/lang/String;)V
      //   327: putfield 198	android/os/Message:obj	Ljava/lang/Object;
      //   330: aload_0
      //   331: getfield 67	com/tencent/weiyun/FileManager$UploadFileImp:mHandler	Landroid/os/Handler;
      //   334: aload 13
      //   336: invokevirtual 202	android/os/Handler:sendMessage	(Landroid/os/Message;)Z
      //   339: pop
      //   340: return
      //
      // Exception table:
      //   from	to	target	type
      //   90	126	255	java/lang/Exception
      //   126	170	255	java/lang/Exception
      //   170	206	298	java/lang/Exception
      //   206	250	298	java/lang/Exception
    }
  }

  public static enum WeiyunFileType
  {
    private final int mType;

    static
    {
      WeiyunFileType[] arrayOfWeiyunFileType = new WeiyunFileType[3];
      arrayOfWeiyunFileType[0] = ImageFile;
      arrayOfWeiyunFileType[1] = MusicFile;
      arrayOfWeiyunFileType[2] = VideoFile;
    }

    private WeiyunFileType(int paramInt)
    {
      this.mType = paramInt;
    }

    public int value()
    {
      return this.mType;
    }
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.tencent.weiyun.FileManager
 * JD-Core Version:    0.6.2
 */