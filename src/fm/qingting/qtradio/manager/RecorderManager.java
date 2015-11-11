package fm.qingting.qtradio.manager;

import android.annotation.TargetApi;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaRecorder;
import android.media.MediaRecorder.OnInfoListener;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import fm.qingting.download.QTRadioDownloadAgent;
import fm.qingting.framework.data.RequestTrait;
import fm.qingting.framework.data.ServerConfig;
import fm.qingting.framework.manager.EventDispacthManager;
import fm.qingting.qtradio.fm.PlayerAgent;
import fm.qingting.qtradio.model.InfoManager;
import fm.qingting.qtradio.room.SnsInfo;
import fm.qingting.qtradio.room.UserInfo;
import fm.qingting.qtradio.social.CloudCenter;
import fm.qingting.qtradio.social.UserProfile;
import java.io.File;
import java.io.IOException;

public class RecorderManager
{
  private static final String CACHE_PATH = Environment.getExternalStorageDirectory().getAbsolutePath() + "/QTRadioUploadCache";
  private static final String LOG_TAG = "RecorderMgr";
  private static final int MAX_AMP_VALUE = 32767;
  private static RecorderManager instance = null;
  private final Runnable mApmRunnable = new Runnable()
  {
    public void run()
    {
      if ((RecorderManager.this.state == 0) && (RecorderManager.this.mRecorder != null))
      {
        RecorderManager.this.onAmpChanged(RecorderManager.this.mRecorder.getMaxAmplitude());
        RecorderManager.this.mHandler.postDelayed(this, 100L);
      }
    }
  };
  private long mCachedFileDurationMSec = 0L;
  private final Handler mHandler = new Handler();
  private long mMaxDurationSec = 0L;
  private MediaPlayer mPlayer = null;
  private RecorderHandler mRecordHandler = null;
  private MediaRecorder mRecorder = null;
  private final Runnable mSecRunnable = new Runnable()
  {
    public void run()
    {
      if (RecorderManager.this.state == 0)
        RecorderManager.this.onRecordingSecond();
      while (RecorderManager.this.state != 1)
        return;
      RecorderManager.this.onReplaySecond();
    }
  };
  private long mStartTime = 0L;
  private String mTagID = "";
  private String mTitle = null;
  private final Handler mUploadHandler = new Handler()
  {
    public void handleMessage(Message paramAnonymousMessage)
    {
      super.handleMessage(paramAnonymousMessage);
      int i = paramAnonymousMessage.getData().getInt("status");
      RecorderManager.this.cleanup();
      if (i >= 0)
      {
        RecorderManager.this.deleteCachedFile();
        EventDispacthManager localEventDispacthManager2 = EventDispacthManager.getInstance();
        Object[] arrayOfObject3 = new Object[1];
        arrayOfObject3[0] = RecorderManager.this.mTitle;
        localEventDispacthManager2.dispatchAction("refreshUploadView", String.format("上传\"%s\"成功！", arrayOfObject3));
      }
      while (true)
      {
        Object[] arrayOfObject2 = new Object[1];
        arrayOfObject2[0] = Integer.valueOf(i);
        Log.d("RecorderMgr", String.format("upload result: %d", arrayOfObject2));
        return;
        RecorderManager.this.renameCachedFile();
        EventDispacthManager localEventDispacthManager1 = EventDispacthManager.getInstance();
        Object[] arrayOfObject1 = new Object[1];
        arrayOfObject1[0] = RecorderManager.this.mTitle;
        localEventDispacthManager1.dispatchAction("refreshUploadView", String.format("上传\"%s\"失败！请稍后重试。", arrayOfObject1));
      }
    }
  };
  private final Runnable mUploadRunnable = new Runnable()
  {
    public void run()
    {
      int i = -1;
      try
      {
        int j = RecorderManager.this.doUploadCachedFile();
        i = j;
        Message localMessage = new Message();
        Bundle localBundle = new Bundle();
        localBundle.putInt("status", i);
        localMessage.setData(localBundle);
        RecorderManager.this.mUploadHandler.sendMessage(localMessage);
        return;
      }
      catch (Exception localException)
      {
        while (true)
          localException.printStackTrace();
      }
    }
  };
  private String mUploadUrl = "";
  private String mUserId = null;
  private String mUserName = null;
  private int state = -1;

  // ERROR //
  private int doUploadCachedFile()
  {
    // Byte code:
    //   0: aconst_null
    //   1: astore_1
    //   2: new 161	java/io/FileInputStream
    //   5: dup
    //   6: aload_0
    //   7: invokevirtual 164	fm/qingting/qtradio/manager/RecorderManager:getCachedFile	()Ljava/io/File;
    //   10: invokespecial 167	java/io/FileInputStream:<init>	(Ljava/io/File;)V
    //   13: astore_2
    //   14: new 169	java/net/URL
    //   17: dup
    //   18: aload_0
    //   19: getfield 101	fm/qingting/qtradio/manager/RecorderManager:mUploadUrl	Ljava/lang/String;
    //   22: invokespecial 172	java/net/URL:<init>	(Ljava/lang/String;)V
    //   25: invokevirtual 176	java/net/URL:openConnection	()Ljava/net/URLConnection;
    //   28: checkcast 178	java/net/HttpURLConnection
    //   31: astore 13
    //   33: aload 13
    //   35: iconst_1
    //   36: invokevirtual 182	java/net/HttpURLConnection:setDoInput	(Z)V
    //   39: aload 13
    //   41: iconst_1
    //   42: invokevirtual 185	java/net/HttpURLConnection:setDoOutput	(Z)V
    //   45: aload 13
    //   47: iconst_0
    //   48: invokevirtual 188	java/net/HttpURLConnection:setUseCaches	(Z)V
    //   51: aload 13
    //   53: ldc 190
    //   55: invokevirtual 193	java/net/HttpURLConnection:setRequestMethod	(Ljava/lang/String;)V
    //   58: aload 13
    //   60: ldc 195
    //   62: ldc 197
    //   64: invokevirtual 201	java/net/HttpURLConnection:setRequestProperty	(Ljava/lang/String;Ljava/lang/String;)V
    //   67: aload 13
    //   69: ldc 203
    //   71: new 43	java/lang/StringBuilder
    //   74: dup
    //   75: invokespecial 46	java/lang/StringBuilder:<init>	()V
    //   78: ldc 205
    //   80: invokevirtual 62	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   83: ldc 207
    //   85: invokevirtual 62	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   88: invokevirtual 67	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   91: invokevirtual 201	java/net/HttpURLConnection:setRequestProperty	(Ljava/lang/String;Ljava/lang/String;)V
    //   94: new 209	java/io/DataOutputStream
    //   97: dup
    //   98: aload 13
    //   100: invokevirtual 213	java/net/HttpURLConnection:getOutputStream	()Ljava/io/OutputStream;
    //   103: invokespecial 216	java/io/DataOutputStream:<init>	(Ljava/io/OutputStream;)V
    //   106: astore 16
    //   108: aload 16
    //   110: new 43	java/lang/StringBuilder
    //   113: dup
    //   114: invokespecial 46	java/lang/StringBuilder:<init>	()V
    //   117: ldc 218
    //   119: invokevirtual 62	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   122: ldc 207
    //   124: invokevirtual 62	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   127: ldc 220
    //   129: invokevirtual 62	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   132: invokevirtual 67	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   135: invokevirtual 223	java/io/DataOutputStream:writeBytes	(Ljava/lang/String;)V
    //   138: aload 16
    //   140: new 43	java/lang/StringBuilder
    //   143: dup
    //   144: invokespecial 46	java/lang/StringBuilder:<init>	()V
    //   147: ldc 225
    //   149: invokevirtual 62	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   152: ldc 220
    //   154: invokevirtual 62	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   157: invokevirtual 67	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   160: invokevirtual 223	java/io/DataOutputStream:writeBytes	(Ljava/lang/String;)V
    //   163: aload 16
    //   165: ldc 220
    //   167: invokevirtual 223	java/io/DataOutputStream:writeBytes	(Ljava/lang/String;)V
    //   170: aload 16
    //   172: aload_0
    //   173: getfield 80	fm/qingting/qtradio/manager/RecorderManager:mUserName	Ljava/lang/String;
    //   176: ldc 227
    //   178: invokevirtual 233	java/lang/String:getBytes	(Ljava/lang/String;)[B
    //   181: invokevirtual 237	java/io/DataOutputStream:write	([B)V
    //   184: aload 16
    //   186: ldc 220
    //   188: invokevirtual 223	java/io/DataOutputStream:writeBytes	(Ljava/lang/String;)V
    //   191: aload 16
    //   193: new 43	java/lang/StringBuilder
    //   196: dup
    //   197: invokespecial 46	java/lang/StringBuilder:<init>	()V
    //   200: ldc 218
    //   202: invokevirtual 62	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   205: ldc 207
    //   207: invokevirtual 62	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   210: ldc 220
    //   212: invokevirtual 62	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   215: invokevirtual 67	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   218: invokevirtual 223	java/io/DataOutputStream:writeBytes	(Ljava/lang/String;)V
    //   221: aload 16
    //   223: new 43	java/lang/StringBuilder
    //   226: dup
    //   227: invokespecial 46	java/lang/StringBuilder:<init>	()V
    //   230: ldc 239
    //   232: invokevirtual 62	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   235: ldc 220
    //   237: invokevirtual 62	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   240: invokevirtual 67	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   243: invokevirtual 223	java/io/DataOutputStream:writeBytes	(Ljava/lang/String;)V
    //   246: aload 16
    //   248: ldc 220
    //   250: invokevirtual 223	java/io/DataOutputStream:writeBytes	(Ljava/lang/String;)V
    //   253: aload 16
    //   255: aload_0
    //   256: getfield 82	fm/qingting/qtradio/manager/RecorderManager:mUserId	Ljava/lang/String;
    //   259: invokevirtual 223	java/io/DataOutputStream:writeBytes	(Ljava/lang/String;)V
    //   262: aload 16
    //   264: ldc 220
    //   266: invokevirtual 223	java/io/DataOutputStream:writeBytes	(Ljava/lang/String;)V
    //   269: aload 16
    //   271: new 43	java/lang/StringBuilder
    //   274: dup
    //   275: invokespecial 46	java/lang/StringBuilder:<init>	()V
    //   278: ldc 218
    //   280: invokevirtual 62	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   283: ldc 207
    //   285: invokevirtual 62	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   288: ldc 220
    //   290: invokevirtual 62	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   293: invokevirtual 67	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   296: invokevirtual 223	java/io/DataOutputStream:writeBytes	(Ljava/lang/String;)V
    //   299: aload 16
    //   301: new 43	java/lang/StringBuilder
    //   304: dup
    //   305: invokespecial 46	java/lang/StringBuilder:<init>	()V
    //   308: ldc 241
    //   310: invokevirtual 62	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   313: ldc 220
    //   315: invokevirtual 62	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   318: invokevirtual 67	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   321: invokevirtual 223	java/io/DataOutputStream:writeBytes	(Ljava/lang/String;)V
    //   324: aload 16
    //   326: ldc 220
    //   328: invokevirtual 223	java/io/DataOutputStream:writeBytes	(Ljava/lang/String;)V
    //   331: aload 16
    //   333: aload_0
    //   334: getfield 76	fm/qingting/qtradio/manager/RecorderManager:mTagID	Ljava/lang/String;
    //   337: invokevirtual 223	java/io/DataOutputStream:writeBytes	(Ljava/lang/String;)V
    //   340: aload 16
    //   342: ldc 220
    //   344: invokevirtual 223	java/io/DataOutputStream:writeBytes	(Ljava/lang/String;)V
    //   347: aload 16
    //   349: new 43	java/lang/StringBuilder
    //   352: dup
    //   353: invokespecial 46	java/lang/StringBuilder:<init>	()V
    //   356: ldc 218
    //   358: invokevirtual 62	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   361: ldc 207
    //   363: invokevirtual 62	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   366: ldc 220
    //   368: invokevirtual 62	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   371: invokevirtual 67	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   374: invokevirtual 223	java/io/DataOutputStream:writeBytes	(Ljava/lang/String;)V
    //   377: aload 16
    //   379: new 43	java/lang/StringBuilder
    //   382: dup
    //   383: invokespecial 46	java/lang/StringBuilder:<init>	()V
    //   386: ldc 243
    //   388: invokevirtual 62	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   391: ldc 220
    //   393: invokevirtual 62	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   396: invokevirtual 67	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   399: invokevirtual 223	java/io/DataOutputStream:writeBytes	(Ljava/lang/String;)V
    //   402: aload 16
    //   404: ldc 220
    //   406: invokevirtual 223	java/io/DataOutputStream:writeBytes	(Ljava/lang/String;)V
    //   409: aload 16
    //   411: aload_0
    //   412: getfield 78	fm/qingting/qtradio/manager/RecorderManager:mTitle	Ljava/lang/String;
    //   415: ldc 227
    //   417: invokevirtual 233	java/lang/String:getBytes	(Ljava/lang/String;)[B
    //   420: invokevirtual 237	java/io/DataOutputStream:write	([B)V
    //   423: aload 16
    //   425: ldc 220
    //   427: invokevirtual 223	java/io/DataOutputStream:writeBytes	(Ljava/lang/String;)V
    //   430: aload 16
    //   432: new 43	java/lang/StringBuilder
    //   435: dup
    //   436: invokespecial 46	java/lang/StringBuilder:<init>	()V
    //   439: ldc 218
    //   441: invokevirtual 62	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   444: ldc 207
    //   446: invokevirtual 62	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   449: ldc 220
    //   451: invokevirtual 62	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   454: invokevirtual 67	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   457: invokevirtual 223	java/io/DataOutputStream:writeBytes	(Ljava/lang/String;)V
    //   460: aload 16
    //   462: new 43	java/lang/StringBuilder
    //   465: dup
    //   466: invokespecial 46	java/lang/StringBuilder:<init>	()V
    //   469: ldc 245
    //   471: invokevirtual 62	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   474: aload_0
    //   475: invokespecial 248	fm/qingting/qtradio/manager/RecorderManager:getCachedFilePath	()Ljava/lang/String;
    //   478: invokevirtual 62	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   481: ldc 250
    //   483: invokevirtual 62	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   486: ldc 220
    //   488: invokevirtual 62	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   491: invokevirtual 67	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   494: invokevirtual 223	java/io/DataOutputStream:writeBytes	(Ljava/lang/String;)V
    //   497: aload 16
    //   499: ldc 220
    //   501: invokevirtual 223	java/io/DataOutputStream:writeBytes	(Ljava/lang/String;)V
    //   504: aload_2
    //   505: invokevirtual 253	java/io/FileInputStream:available	()I
    //   508: ldc 254
    //   510: invokestatic 260	java/lang/Math:min	(II)I
    //   513: istore 17
    //   515: iload 17
    //   517: newarray byte
    //   519: astore 18
    //   521: aload_2
    //   522: aload 18
    //   524: iconst_0
    //   525: iload 17
    //   527: invokevirtual 264	java/io/FileInputStream:read	([BII)I
    //   530: istore 19
    //   532: iload 19
    //   534: ifle +38 -> 572
    //   537: aload 16
    //   539: aload 18
    //   541: iconst_0
    //   542: iload 17
    //   544: invokevirtual 267	java/io/DataOutputStream:write	([BII)V
    //   547: aload_2
    //   548: invokevirtual 253	java/io/FileInputStream:available	()I
    //   551: ldc 254
    //   553: invokestatic 260	java/lang/Math:min	(II)I
    //   556: istore 17
    //   558: aload_2
    //   559: aload 18
    //   561: iconst_0
    //   562: iload 17
    //   564: invokevirtual 264	java/io/FileInputStream:read	([BII)I
    //   567: istore 19
    //   569: goto -37 -> 532
    //   572: aload 16
    //   574: ldc 220
    //   576: invokevirtual 223	java/io/DataOutputStream:writeBytes	(Ljava/lang/String;)V
    //   579: aload 16
    //   581: new 43	java/lang/StringBuilder
    //   584: dup
    //   585: invokespecial 46	java/lang/StringBuilder:<init>	()V
    //   588: ldc 218
    //   590: invokevirtual 62	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   593: ldc 207
    //   595: invokevirtual 62	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   598: ldc 218
    //   600: invokevirtual 62	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   603: ldc 220
    //   605: invokevirtual 62	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   608: invokevirtual 67	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   611: invokevirtual 223	java/io/DataOutputStream:writeBytes	(Ljava/lang/String;)V
    //   614: aload_2
    //   615: invokevirtual 270	java/io/FileInputStream:close	()V
    //   618: aload 16
    //   620: invokevirtual 273	java/io/DataOutputStream:flush	()V
    //   623: aload 16
    //   625: invokevirtual 274	java/io/DataOutputStream:close	()V
    //   628: aload 13
    //   630: astore_1
    //   631: new 276	java/io/DataInputStream
    //   634: dup
    //   635: aload_1
    //   636: invokevirtual 280	java/net/HttpURLConnection:getInputStream	()Ljava/io/InputStream;
    //   639: invokespecial 283	java/io/DataInputStream:<init>	(Ljava/io/InputStream;)V
    //   642: astore 4
    //   644: bipush 254
    //   646: istore 5
    //   648: aload 4
    //   650: invokevirtual 286	java/io/DataInputStream:readLine	()Ljava/lang/String;
    //   653: astore 9
    //   655: aload 9
    //   657: ifnull +44 -> 701
    //   660: aload 9
    //   662: invokestatic 292	com/alibaba/fastjson/JSON:parse	(Ljava/lang/String;)Ljava/lang/Object;
    //   665: checkcast 294	com/alibaba/fastjson/JSONObject
    //   668: ldc_w 296
    //   671: invokevirtual 300	com/alibaba/fastjson/JSONObject:getIntValue	(Ljava/lang/String;)I
    //   674: istore 10
    //   676: iload 10
    //   678: istore 5
    //   680: goto -32 -> 648
    //   683: astore 12
    //   685: aload 12
    //   687: invokevirtual 303	java/net/MalformedURLException:printStackTrace	()V
    //   690: goto -59 -> 631
    //   693: astore_3
    //   694: aload_3
    //   695: invokevirtual 304	java/io/IOException:printStackTrace	()V
    //   698: goto -67 -> 631
    //   701: aload 4
    //   703: invokevirtual 305	java/io/DataInputStream:close	()V
    //   706: iload 5
    //   708: ireturn
    //   709: astore 11
    //   711: aload 11
    //   713: astore 8
    //   715: bipush 254
    //   717: istore 7
    //   719: aload 8
    //   721: invokevirtual 306	java/lang/Exception:printStackTrace	()V
    //   724: iload 7
    //   726: ireturn
    //   727: astore 6
    //   729: iload 5
    //   731: istore 7
    //   733: aload 6
    //   735: astore 8
    //   737: goto -18 -> 719
    //   740: astore 15
    //   742: aload 13
    //   744: astore_1
    //   745: aload 15
    //   747: astore_3
    //   748: goto -54 -> 694
    //   751: astore 14
    //   753: aload 13
    //   755: astore_1
    //   756: aload 14
    //   758: astore 12
    //   760: goto -75 -> 685
    //
    // Exception table:
    //   from	to	target	type
    //   2	33	683	java/net/MalformedURLException
    //   2	33	693	java/io/IOException
    //   631	644	709	java/lang/Exception
    //   648	655	727	java/lang/Exception
    //   660	676	727	java/lang/Exception
    //   701	706	727	java/lang/Exception
    //   33	532	740	java/io/IOException
    //   537	569	740	java/io/IOException
    //   572	628	740	java/io/IOException
    //   33	532	751	java/net/MalformedURLException
    //   537	569	751	java/net/MalformedURLException
    //   572	628	751	java/net/MalformedURLException
  }

  private String getCachedFilePath()
  {
    return getCachedFilePath(this.mTagID);
  }

  public static String getCachedFilePath(String paramString)
  {
    File localFile = new File(CACHE_PATH + "/" + paramString);
    if ((localFile.exists()) && (localFile.isDirectory()))
    {
      File[] arrayOfFile = localFile.listFiles();
      if (arrayOfFile.length > 0)
        return arrayOfFile[0].getAbsolutePath();
    }
    return "";
  }

  public static RecorderManager getInstance()
  {
    if (instance == null)
      instance = new RecorderManager();
    return instance;
  }

  private String newCachedFilePath()
  {
    String str = CACHE_PATH + "/" + this.mTagID;
    new File(str).mkdirs();
    return str + "/" + this.mTitle + ".m4a";
  }

  private void onAmpChanged(int paramInt)
  {
    float f1;
    float f2;
    if (this.mRecordHandler != null)
    {
      f1 = (0.0F + paramInt) / 32767.0F;
      boolean bool = f1 < 0.0F;
      f2 = 0.0F;
      if (!bool)
        break label39;
    }
    while (true)
    {
      this.mRecordHandler.onMonitorAmpChanged(f2);
      return;
      label39: if (f1 > 1.0F)
        f2 = 1.0F;
      else
        f2 = f1;
    }
  }

  private void onRecordingSecond()
  {
    if (this.mRecorder != null)
    {
      long l = (int)Math.ceil((System.currentTimeMillis() - this.mStartTime) / 1000L);
      if (this.mRecordHandler != null)
        this.mRecordHandler.onRecordingSecond(Long.valueOf(l));
      this.mHandler.postDelayed(this.mSecRunnable, 1000L);
    }
  }

  private void onReplaySecond()
  {
    if (this.mPlayer != null)
    {
      long l = this.mPlayer.getCurrentPosition();
      if (this.mRecordHandler != null)
        this.mRecordHandler.onReplaySecond(Long.valueOf(l));
      this.mHandler.postDelayed(this.mSecRunnable, 200L);
    }
  }

  public void cleanup()
  {
    this.mHandler.removeCallbacks(this.mSecRunnable);
    this.state = -1;
    this.mCachedFileDurationMSec = 0L;
    if (this.mRecorder != null)
    {
      this.mRecorder.reset();
      this.mRecorder.release();
      this.mRecorder = null;
    }
    if (this.mPlayer != null)
    {
      this.mPlayer.reset();
      this.mPlayer.release();
      this.mPlayer = null;
    }
  }

  public boolean deleteCachedFile()
  {
    return getCachedFile().delete();
  }

  public long getAudioDurationMSec(File paramFile)
  {
    long l = 0L;
    if (!paramFile.exists())
      return l;
    MediaPlayer localMediaPlayer = new MediaPlayer();
    try
    {
      localMediaPlayer.setDataSource(getCachedFilePath());
      localMediaPlayer.prepare();
      l = localMediaPlayer.getDuration();
      localMediaPlayer.reset();
      localMediaPlayer.release();
      return l;
    }
    catch (IOException localIOException)
    {
      Log.e("RecorderMgr", "player prepare() failed");
      localIOException.printStackTrace();
    }
    return l;
  }

  public long getAvaliableMemSize()
  {
    return QTRadioDownloadAgent.getInstance().getAvailableExternalMemorySize();
  }

  public File getCachedFile()
  {
    return new File(getCachedFilePath());
  }

  public long getCachedFileDurationMSec()
  {
    if (this.mCachedFileDurationMSec <= 0L)
    {
      this.mCachedFileDurationMSec = getAudioDurationMSec(getCachedFile());
      if ((this.mMaxDurationSec > 0L) && (this.mCachedFileDurationMSec > 1000L * this.mMaxDurationSec))
        this.mCachedFileDurationMSec = (1000L * this.mMaxDurationSec);
    }
    return this.mCachedFileDurationMSec;
  }

  public String getCachedFileName()
  {
    return this.mTitle;
  }

  public long getMaxDurationSec()
  {
    return this.mMaxDurationSec;
  }

  public String getUserName()
  {
    return this.mUserName;
  }

  public String init(String paramString, int paramInt)
  {
    this.mUploadUrl = ServerConfig.getInstance().getRequestTrait("post_recorded_voice").getCommand();
    this.mMaxDurationSec = paramInt;
    this.mCachedFileDurationMSec = 0L;
    this.mTagID = paramString;
    try
    {
      this.mUserName = InfoManager.getInstance().getUserProfile().getUserInfo().snsInfo.sns_name;
      this.mTitle = (this.mUserName + "的录音");
      if ((float)getAvaliableMemSize() < 10000000.0F)
        return "requireMem";
    }
    catch (Exception localException)
    {
      while (true)
        this.mUserName = "蜻蜓用户";
      if (!CloudCenter.getInstance().isLogin())
        return "requireLogin";
      if (NetWorkManage.getInstance().getNetWorkType().equalsIgnoreCase("noNet"))
        return "requireNet";
      deleteCachedFile();
    }
    return "";
  }

  public boolean initByFile(String paramString)
  {
    this.mTagID = paramString;
    File localFile = getCachedFile();
    if (!localFile.exists())
      return false;
    cleanup();
    this.mMaxDurationSec = 0L;
    this.mCachedFileDurationMSec = 0L;
    this.mUploadUrl = ServerConfig.getInstance().getRequestTrait("post_recorded_voice").getCommand();
    try
    {
      this.mUserName = InfoManager.getInstance().getUserProfile().getUserInfo().snsInfo.sns_name;
      this.mTitle = localFile.getName().split("\\.(?=[^\\.]+$)")[0];
      return true;
    }
    catch (Exception localException)
    {
      while (true)
        this.mUserName = "蜻蜓用户";
    }
  }

  public boolean isReplaying()
  {
    if (this.mPlayer != null)
      return this.mPlayer.isPlaying();
    return false;
  }

  public boolean isUploading()
  {
    return this.state == 3;
  }

  public void pauseReplay()
  {
    try
    {
      this.mPlayer.pause();
      return;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
  }

  public boolean renameCachedFile()
  {
    return getCachedFile().renameTo(new File(CACHE_PATH + "/" + this.mTagID + "/" + this.mTitle + ".m4a"));
  }

  @TargetApi(10)
  public void startRecording(RecorderHandler paramRecorderHandler)
  {
    if (this.mMaxDurationSec <= 0L);
    while (true)
    {
      return;
      cleanup();
      this.state = 0;
      this.mRecorder = new MediaRecorder();
      this.mRecorder.setAudioSource(1);
      this.mRecorder.setOutputFormat(2);
      String str = getCachedFilePath();
      if (str.length() == 0)
        str = newCachedFilePath();
      this.mRecorder.setOutputFile(str);
      if (QtApiLevelManager.isApiLevelSupported(10))
      {
        this.mRecorder.setAudioEncoder(3);
        this.mRecorder.setAudioEncodingBitRate(64000);
        this.mRecorder.setAudioSamplingRate(44100);
        this.mRecorder.setOnInfoListener(new MediaRecorder.OnInfoListener()
        {
          public void onInfo(MediaRecorder paramAnonymousMediaRecorder, int paramAnonymousInt1, int paramAnonymousInt2)
          {
            if (paramAnonymousInt1 == 800)
              RecorderManager.this.stopRecording();
          }
        });
        this.mRecorder.setMaxDuration(1000 * (int)this.mMaxDurationSec);
        this.mRecordHandler = paramRecorderHandler;
      }
      try
      {
        this.mRecorder.prepare();
        this.mStartTime = System.currentTimeMillis();
        this.mHandler.postDelayed(this.mSecRunnable, 1000L);
        this.mHandler.postDelayed(this.mApmRunnable, 0L);
        this.mRecorder.start();
        if (this.mRecordHandler != null)
        {
          this.mRecordHandler.onRecordingStart();
          return;
        }
      }
      catch (IOException localIOException)
      {
        Log.e("RecorderMgr", "recorder prepare() failed");
        localIOException.printStackTrace();
        return;
        this.mRecorder.setAudioEncoder(1);
        this.mRecorder.setAudioEncodingBitRate(12200);
        this.mRecorder.setAudioSamplingRate(8000);
      }
      catch (Exception localException)
      {
        localException.printStackTrace();
      }
    }
  }

  public void startReplay(RecorderHandler paramRecorderHandler)
  {
    if ((this.mPlayer != null) && (this.state == 1));
    try
    {
      while (true)
      {
        this.mPlayer.start();
        return;
        cleanup();
        PlayerAgent.getInstance().stop();
        this.state = 1;
        this.mPlayer = new MediaPlayer();
        this.mPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener()
        {
          public void onCompletion(MediaPlayer paramAnonymousMediaPlayer)
          {
            RecorderManager.this.mRecordHandler.onReplayStop();
            RecorderManager.this.cleanup();
          }
        });
        this.mRecordHandler = paramRecorderHandler;
        try
        {
          this.mPlayer.setDataSource(getCachedFilePath());
          this.mPlayer.prepare();
          this.mPlayer.setVolume(1.0F, 1.0F);
          this.mHandler.postDelayed(this.mSecRunnable, 200L);
        }
        catch (IOException localIOException)
        {
          Log.e("RecorderMgr", "player prepare() failed");
          localIOException.printStackTrace();
        }
        catch (Exception localException1)
        {
          localException1.printStackTrace();
        }
      }
    }
    catch (Exception localException2)
    {
      localException2.printStackTrace();
    }
  }

  public void stopRecording()
  {
    try
    {
      this.mRecorder.stop();
      if (this.mRecordHandler != null)
        this.mRecordHandler.onRecordingStop();
      cleanup();
      return;
    }
    catch (Exception localException)
    {
      while (true)
        localException.printStackTrace();
    }
  }

  public void uploadCachedFile(String paramString)
  {
    this.state = 3;
    this.mUserId = InfoManager.getInstance().getUserProfile().getUserKey();
    if ((this.mUserId == null) || (this.mUserId.equalsIgnoreCase("")))
      this.mUserId = InfoManager.getInstance().getDeviceId();
    try
    {
      this.mUserName = InfoManager.getInstance().getUserProfile().getUserInfo().snsInfo.sns_name;
      if (paramString != null)
      {
        this.mTitle = paramString;
        Object[] arrayOfObject1 = new Object[5];
        arrayOfObject1[0] = getCachedFilePath();
        arrayOfObject1[1] = this.mTagID;
        arrayOfObject1[2] = this.mUserId;
        arrayOfObject1[3] = this.mUserName;
        arrayOfObject1[4] = this.mTitle;
        Log.d("RecorderMgr", String.format("NewThread: Upload %s, %s, %s, %s, %s", arrayOfObject1));
        new Thread(this.mUploadRunnable).start();
        EventDispacthManager localEventDispacthManager = EventDispacthManager.getInstance();
        Object[] arrayOfObject2 = new Object[1];
        arrayOfObject2[0] = this.mTitle;
        localEventDispacthManager.dispatchAction("refreshUploadView", String.format("开始上传\"%s\"！", arrayOfObject2));
        return;
      }
    }
    catch (Exception localException)
    {
      while (true)
      {
        this.mUserName = "蜻蜓用户";
        continue;
        paramString = this.mUserName + "的录音";
      }
    }
  }

  public static abstract interface RecorderHandler
  {
    public abstract void onMonitorAmpChanged(float paramFloat);

    public abstract void onRecordingSecond(Long paramLong);

    public abstract void onRecordingStart();

    public abstract void onRecordingStop();

    public abstract void onReplaySecond(Long paramLong);

    public abstract void onReplayStop();
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.qtradio.manager.RecorderManager
 * JD-Core Version:    0.6.2
 */