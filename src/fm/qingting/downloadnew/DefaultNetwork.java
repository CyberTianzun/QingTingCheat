package fm.qingting.downloadnew;

import android.content.Context;
import android.util.Log;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;

class DefaultNetwork
  implements Network
{
  final String TAG;
  private byte[] mBuffer;
  private Context mContext;
  protected DownloadTask mCurTask;
  private EventDispatcher mDispatcher;
  protected RandomAccessFile mRAFile;
  private UrlRewriter mRewriter;

  DefaultNetwork(Context paramContext, EventDispatcher paramEventDispatcher, String paramString)
  {
    this.mContext = paramContext;
    this.mBuffer = new byte[4096];
    this.mDispatcher = paramEventDispatcher;
    this.TAG = paramString;
  }

  private void attemptRetryTimeoutOnException(String paramString, HttpURLConnection paramHttpURLConnection, DownloadError paramDownloadError)
    throws DownloadError
  {
    RetryPolicy localRetryPolicy = this.mCurTask.mRetryPolicy;
    try
    {
      localRetryPolicy.retryTimeout(paramDownloadError);
      Log.d(this.TAG, paramString + "，进行第" + this.mCurTask.getCurRetryTime() + "次重试，超时设置为" + localRetryPolicy.getCurrentTimeout() + "ms");
      return;
    }
    catch (DownloadError localDownloadError)
    {
      finishTask(paramHttpURLConnection, DownloadState.ERROR, localDownloadError.getMessage());
      throw localDownloadError;
    }
  }

  private void attemptRetryUrlOnConnectionFail(String paramString, HttpURLConnection paramHttpURLConnection, DownloadError paramDownloadError)
    throws DownloadError
  {
    RetryPolicy localRetryPolicy = this.mCurTask.mRetryPolicy;
    try
    {
      localRetryPolicy.retryUrl(paramDownloadError);
      this.mCurTask.mRedirectUrl = null;
      Log.d(this.TAG, paramString + "，进行重试，url设置为" + localRetryPolicy.getCurrentUrl());
      return;
    }
    catch (DownloadError localDownloadError)
    {
      finishTask(paramHttpURLConnection, DownloadState.ERROR, localDownloadError.getMessage());
      throw localDownloadError;
    }
  }

  // ERROR //
  private void downloadToFile(HttpURLConnection paramHttpURLConnection)
    throws IOException
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 31	fm/qingting/downloadnew/DefaultNetwork:mDispatcher	Lfm/qingting/downloadnew/EventDispatcher;
    //   4: aload_0
    //   5: getfield 39	fm/qingting/downloadnew/DefaultNetwork:mCurTask	Lfm/qingting/downloadnew/DownloadTask;
    //   8: getfield 116	fm/qingting/downloadnew/DownloadTask:mTaskId	Ljava/lang/String;
    //   11: getstatic 119	fm/qingting/downloadnew/DownloadState:DOWNLOADING	Lfm/qingting/downloadnew/DownloadState;
    //   14: invokevirtual 125	fm/qingting/downloadnew/EventDispatcher:sendLoopEvent	(Ljava/lang/String;Lfm/qingting/downloadnew/DownloadState;)V
    //   17: getstatic 128	fm/qingting/downloadnew/DownloadState:SUCCESS	Lfm/qingting/downloadnew/DownloadState;
    //   20: astore_2
    //   21: aload_1
    //   22: invokevirtual 134	java/net/HttpURLConnection:getInputStream	()Ljava/io/InputStream;
    //   25: astore_3
    //   26: aload_0
    //   27: getfield 136	fm/qingting/downloadnew/DefaultNetwork:mRAFile	Ljava/io/RandomAccessFile;
    //   30: aload_0
    //   31: getfield 39	fm/qingting/downloadnew/DefaultNetwork:mCurTask	Lfm/qingting/downloadnew/DownloadTask;
    //   34: getfield 140	fm/qingting/downloadnew/DownloadTask:mCurSize	J
    //   37: invokevirtual 146	java/io/RandomAccessFile:seek	(J)V
    //   40: aload_3
    //   41: aload_0
    //   42: getfield 29	fm/qingting/downloadnew/DefaultNetwork:mBuffer	[B
    //   45: invokevirtual 152	java/io/InputStream:read	([B)I
    //   48: istore 5
    //   50: iload 5
    //   52: iconst_m1
    //   53: if_icmpeq +60 -> 113
    //   56: aload_0
    //   57: getfield 136	fm/qingting/downloadnew/DefaultNetwork:mRAFile	Ljava/io/RandomAccessFile;
    //   60: aload_0
    //   61: getfield 29	fm/qingting/downloadnew/DefaultNetwork:mBuffer	[B
    //   64: iconst_0
    //   65: iload 5
    //   67: invokevirtual 156	java/io/RandomAccessFile:write	([BII)V
    //   70: aload_0
    //   71: getfield 39	fm/qingting/downloadnew/DefaultNetwork:mCurTask	Lfm/qingting/downloadnew/DownloadTask;
    //   74: astore 8
    //   76: aload 8
    //   78: aload 8
    //   80: getfield 140	fm/qingting/downloadnew/DownloadTask:mCurSize	J
    //   83: iload 5
    //   85: i2l
    //   86: ladd
    //   87: putfield 140	fm/qingting/downloadnew/DownloadTask:mCurSize	J
    //   90: aload_0
    //   91: getfield 39	fm/qingting/downloadnew/DefaultNetwork:mCurTask	Lfm/qingting/downloadnew/DownloadTask;
    //   94: getfield 159	fm/qingting/downloadnew/DownloadTask:mState	Lfm/qingting/downloadnew/DownloadState;
    //   97: getstatic 162	fm/qingting/downloadnew/DownloadState:PAUSED	Lfm/qingting/downloadnew/DownloadState;
    //   100: if_acmpeq +9 -> 109
    //   103: invokestatic 168	java/lang/Thread:interrupted	()Z
    //   106: ifeq -66 -> 40
    //   109: getstatic 162	fm/qingting/downloadnew/DownloadState:PAUSED	Lfm/qingting/downloadnew/DownloadState;
    //   112: astore_2
    //   113: aload_3
    //   114: invokevirtual 171	java/io/InputStream:close	()V
    //   117: aload_2
    //   118: getstatic 128	fm/qingting/downloadnew/DownloadState:SUCCESS	Lfm/qingting/downloadnew/DownloadState;
    //   121: if_acmpne +93 -> 214
    //   124: aconst_null
    //   125: astore 6
    //   127: aload_0
    //   128: aload_1
    //   129: aload_2
    //   130: aload 6
    //   132: invokespecial 97	fm/qingting/downloadnew/DefaultNetwork:finishTask	(Ljava/net/HttpURLConnection;Lfm/qingting/downloadnew/DownloadState;Ljava/lang/Object;)V
    //   135: return
    //   136: astore 4
    //   138: aload_3
    //   139: ifnull +7 -> 146
    //   142: aload_3
    //   143: invokevirtual 171	java/io/InputStream:close	()V
    //   146: new 113	java/io/IOException
    //   149: dup
    //   150: new 53	java/lang/StringBuilder
    //   153: dup
    //   154: invokespecial 54	java/lang/StringBuilder:<init>	()V
    //   157: ldc 173
    //   159: invokevirtual 58	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   162: aload 4
    //   164: invokevirtual 174	java/io/IOException:getMessage	()Ljava/lang/String;
    //   167: invokevirtual 58	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   170: invokevirtual 78	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   173: invokespecial 177	java/io/IOException:<init>	(Ljava/lang/String;)V
    //   176: athrow
    //   177: astore 7
    //   179: aload_3
    //   180: invokevirtual 171	java/io/InputStream:close	()V
    //   183: new 113	java/io/IOException
    //   186: dup
    //   187: new 53	java/lang/StringBuilder
    //   190: dup
    //   191: invokespecial 54	java/lang/StringBuilder:<init>	()V
    //   194: ldc 179
    //   196: invokevirtual 58	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   199: aload 7
    //   201: invokevirtual 174	java/io/IOException:getMessage	()Ljava/lang/String;
    //   204: invokevirtual 58	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   207: invokevirtual 78	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   210: invokespecial 177	java/io/IOException:<init>	(Ljava/lang/String;)V
    //   213: athrow
    //   214: ldc 181
    //   216: astore 6
    //   218: goto -91 -> 127
    //
    // Exception table:
    //   from	to	target	type
    //   26	40	136	java/io/IOException
    //   56	70	177	java/io/IOException
  }

  private void finishTask(HttpURLConnection paramHttpURLConnection, DownloadState paramDownloadState, Object paramObject)
  {
    this.mDispatcher.stopLoopEvent();
    try
    {
      if (this.mRAFile != null)
        this.mRAFile.close();
      if (paramHttpURLConnection != null)
        paramHttpURLConnection.disconnect();
      if (paramDownloadState == DownloadState.SUCCESS)
      {
        this.mCurTask.mTotalSize = this.mCurTask.mCurSize;
        if (checkFile())
          this.mCurTask.mFinishTimeMS = System.currentTimeMillis();
      }
      else
      {
        this.mCurTask.mState = paramDownloadState;
        DownloadDAO.updateTask(this.mContext, this.mCurTask);
        String str = this.TAG;
        StringBuilder localStringBuilder = new StringBuilder().append("任务").append(this.mCurTask.mTaskId).append("结束，");
        if (paramDownloadState != DownloadState.SUCCESS)
          break label190;
        localObject = "下载成功";
        Log.d(str, localObject);
        this.mDispatcher.sendEvent(this.mCurTask.mTaskId, paramDownloadState, paramObject);
        return;
      }
    }
    catch (IOException localIOException)
    {
      while (true)
      {
        Log.e(this.TAG, "关闭文件时出错！");
        continue;
        paramDownloadState = DownloadState.ERROR;
        paramObject = "文件完整性检查错误";
        continue;
        label190: Object localObject = paramObject;
      }
    }
  }

  protected boolean checkFile()
  {
    return true;
  }

  protected HttpURLConnection openConnection(URL paramURL)
    throws IOException
  {
    return (HttpURLConnection)paramURL.openConnection();
  }

  // ERROR //
  public void performDownload(DownloadTask paramDownloadTask)
    throws DownloadError
  {
    // Byte code:
    //   0: aload_0
    //   1: aload_1
    //   2: putfield 39	fm/qingting/downloadnew/DefaultNetwork:mCurTask	Lfm/qingting/downloadnew/DownloadTask;
    //   5: aload_0
    //   6: aconst_null
    //   7: putfield 136	fm/qingting/downloadnew/DefaultNetwork:mRAFile	Ljava/io/RandomAccessFile;
    //   10: aload_0
    //   11: new 142	java/io/RandomAccessFile
    //   14: dup
    //   15: aload_0
    //   16: getfield 39	fm/qingting/downloadnew/DefaultNetwork:mCurTask	Lfm/qingting/downloadnew/DownloadTask;
    //   19: getfield 247	fm/qingting/downloadnew/DownloadTask:mFileName	Ljava/lang/String;
    //   22: invokestatic 253	fm/qingting/downloadnew/DownloadUtils:createFile	(Ljava/lang/String;)Ljava/io/File;
    //   25: ldc 255
    //   27: invokespecial 258	java/io/RandomAccessFile:<init>	(Ljava/io/File;Ljava/lang/String;)V
    //   30: putfield 136	fm/qingting/downloadnew/DefaultNetwork:mRAFile	Ljava/io/RandomAccessFile;
    //   33: invokestatic 263	android/os/SystemClock:elapsedRealtime	()J
    //   36: lstore 4
    //   38: iconst_0
    //   39: istore 6
    //   41: aconst_null
    //   42: astore 7
    //   44: aload_0
    //   45: getfield 39	fm/qingting/downloadnew/DefaultNetwork:mCurTask	Lfm/qingting/downloadnew/DownloadTask;
    //   48: getfield 140	fm/qingting/downloadnew/DownloadTask:mCurSize	J
    //   51: lstore 8
    //   53: aload_0
    //   54: getfield 39	fm/qingting/downloadnew/DefaultNetwork:mCurTask	Lfm/qingting/downloadnew/DownloadTask;
    //   57: invokevirtual 266	fm/qingting/downloadnew/DownloadTask:getUrl	()Ljava/lang/String;
    //   60: astore 15
    //   62: aload_0
    //   63: getfield 268	fm/qingting/downloadnew/DefaultNetwork:mRewriter	Lfm/qingting/downloadnew/UrlRewriter;
    //   66: ifnull +1003 -> 1069
    //   69: aload_0
    //   70: getfield 268	fm/qingting/downloadnew/DefaultNetwork:mRewriter	Lfm/qingting/downloadnew/UrlRewriter;
    //   73: aload 15
    //   75: invokeinterface 274 2 0
    //   80: astore 31
    //   82: aload 31
    //   84: ifnonnull +146 -> 230
    //   87: aload_0
    //   88: aconst_null
    //   89: getstatic 90	fm/qingting/downloadnew/DownloadState:ERROR	Lfm/qingting/downloadnew/DownloadState;
    //   92: ldc_w 276
    //   95: invokespecial 97	fm/qingting/downloadnew/DefaultNetwork:finishTask	(Ljava/net/HttpURLConnection;Lfm/qingting/downloadnew/DownloadState;Ljava/lang/Object;)V
    //   98: new 37	fm/qingting/downloadnew/DownloadError
    //   101: dup
    //   102: ldc_w 276
    //   105: invokespecial 277	fm/qingting/downloadnew/DownloadError:<init>	(Ljava/lang/String;)V
    //   108: athrow
    //   109: astore 14
    //   111: aload_0
    //   112: getfield 39	fm/qingting/downloadnew/DefaultNetwork:mCurTask	Lfm/qingting/downloadnew/DownloadTask;
    //   115: getfield 140	fm/qingting/downloadnew/DownloadTask:mCurSize	J
    //   118: lload 8
    //   120: lsub
    //   121: lconst_0
    //   122: lcmp
    //   123: ifne +726 -> 849
    //   126: aload_0
    //   127: ldc_w 279
    //   130: aload 7
    //   132: new 37	fm/qingting/downloadnew/DownloadError
    //   135: dup
    //   136: ldc_w 279
    //   139: invokespecial 277	fm/qingting/downloadnew/DownloadError:<init>	(Ljava/lang/String;)V
    //   142: invokespecial 281	fm/qingting/downloadnew/DefaultNetwork:attemptRetryUrlOnConnectionFail	(Ljava/lang/String;Ljava/net/HttpURLConnection;Lfm/qingting/downloadnew/DownloadError;)V
    //   145: goto -112 -> 33
    //   148: astore_3
    //   149: aload_0
    //   150: aconst_null
    //   151: getstatic 90	fm/qingting/downloadnew/DownloadState:ERROR	Lfm/qingting/downloadnew/DownloadState;
    //   154: new 53	java/lang/StringBuilder
    //   157: dup
    //   158: invokespecial 54	java/lang/StringBuilder:<init>	()V
    //   161: ldc_w 283
    //   164: invokevirtual 58	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   167: aload_3
    //   168: invokevirtual 284	java/io/FileNotFoundException:getMessage	()Ljava/lang/String;
    //   171: invokevirtual 58	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   174: invokevirtual 78	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   177: invokespecial 97	fm/qingting/downloadnew/DefaultNetwork:finishTask	(Ljava/net/HttpURLConnection;Lfm/qingting/downloadnew/DownloadState;Ljava/lang/Object;)V
    //   180: return
    //   181: astore_2
    //   182: aload_0
    //   183: aconst_null
    //   184: getstatic 90	fm/qingting/downloadnew/DownloadState:ERROR	Lfm/qingting/downloadnew/DownloadState;
    //   187: new 53	java/lang/StringBuilder
    //   190: dup
    //   191: invokespecial 54	java/lang/StringBuilder:<init>	()V
    //   194: ldc_w 286
    //   197: invokevirtual 58	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   200: aload_0
    //   201: getfield 39	fm/qingting/downloadnew/DefaultNetwork:mCurTask	Lfm/qingting/downloadnew/DownloadTask;
    //   204: getfield 247	fm/qingting/downloadnew/DownloadTask:mFileName	Ljava/lang/String;
    //   207: invokevirtual 58	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   210: ldc_w 288
    //   213: invokevirtual 58	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   216: aload_2
    //   217: invokevirtual 174	java/io/IOException:getMessage	()Ljava/lang/String;
    //   220: invokevirtual 58	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   223: invokevirtual 78	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   226: invokespecial 97	fm/qingting/downloadnew/DefaultNetwork:finishTask	(Ljava/net/HttpURLConnection;Lfm/qingting/downloadnew/DownloadState;Ljava/lang/Object;)V
    //   229: return
    //   230: aload 31
    //   232: astore 16
    //   234: aload_0
    //   235: new 233	java/net/URL
    //   238: dup
    //   239: aload 16
    //   241: invokespecial 289	java/net/URL:<init>	(Ljava/lang/String;)V
    //   244: invokevirtual 291	fm/qingting/downloadnew/DefaultNetwork:openConnection	(Ljava/net/URL;)Ljava/net/HttpURLConnection;
    //   247: astore 17
    //   249: aload 17
    //   251: astore 11
    //   253: aload 11
    //   255: ldc_w 293
    //   258: invokevirtual 296	java/net/HttpURLConnection:setRequestMethod	(Ljava/lang/String;)V
    //   261: aload_0
    //   262: getfield 39	fm/qingting/downloadnew/DefaultNetwork:mCurTask	Lfm/qingting/downloadnew/DownloadTask;
    //   265: invokevirtual 299	fm/qingting/downloadnew/DownloadTask:getTimeoutMs	()I
    //   268: istore 20
    //   270: aload 11
    //   272: iload 20
    //   274: invokevirtual 303	java/net/HttpURLConnection:setConnectTimeout	(I)V
    //   277: aload 11
    //   279: iload 20
    //   281: iconst_2
    //   282: imul
    //   283: invokevirtual 306	java/net/HttpURLConnection:setReadTimeout	(I)V
    //   286: aload 11
    //   288: iconst_0
    //   289: invokevirtual 310	java/net/HttpURLConnection:setUseCaches	(Z)V
    //   292: aload 11
    //   294: iconst_1
    //   295: invokevirtual 313	java/net/HttpURLConnection:setDoInput	(Z)V
    //   298: aload_0
    //   299: getfield 39	fm/qingting/downloadnew/DefaultNetwork:mCurTask	Lfm/qingting/downloadnew/DownloadTask;
    //   302: getfield 140	fm/qingting/downloadnew/DownloadTask:mCurSize	J
    //   305: lconst_0
    //   306: lcmp
    //   307: ifle +43 -> 350
    //   310: aload 11
    //   312: ldc_w 315
    //   315: new 53	java/lang/StringBuilder
    //   318: dup
    //   319: invokespecial 54	java/lang/StringBuilder:<init>	()V
    //   322: ldc_w 317
    //   325: invokevirtual 58	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   328: aload_0
    //   329: getfield 39	fm/qingting/downloadnew/DefaultNetwork:mCurTask	Lfm/qingting/downloadnew/DownloadTask;
    //   332: getfield 140	fm/qingting/downloadnew/DownloadTask:mCurSize	J
    //   335: invokevirtual 320	java/lang/StringBuilder:append	(J)Ljava/lang/StringBuilder;
    //   338: ldc_w 322
    //   341: invokevirtual 58	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   344: invokevirtual 78	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   347: invokevirtual 326	java/net/HttpURLConnection:addRequestProperty	(Ljava/lang/String;Ljava/lang/String;)V
    //   350: aload_0
    //   351: getfield 33	fm/qingting/downloadnew/DefaultNetwork:TAG	Ljava/lang/String;
    //   354: new 53	java/lang/StringBuilder
    //   357: dup
    //   358: invokespecial 54	java/lang/StringBuilder:<init>	()V
    //   361: ldc_w 328
    //   364: invokevirtual 58	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   367: aload 16
    //   369: invokevirtual 58	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   372: invokevirtual 78	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   375: invokestatic 84	android/util/Log:d	(Ljava/lang/String;Ljava/lang/String;)I
    //   378: pop
    //   379: aload_0
    //   380: getfield 39	fm/qingting/downloadnew/DefaultNetwork:mCurTask	Lfm/qingting/downloadnew/DownloadTask;
    //   383: getstatic 331	fm/qingting/downloadnew/DownloadState:CONNECTING	Lfm/qingting/downloadnew/DownloadState;
    //   386: putfield 159	fm/qingting/downloadnew/DownloadTask:mState	Lfm/qingting/downloadnew/DownloadState;
    //   389: aload_0
    //   390: getfield 31	fm/qingting/downloadnew/DefaultNetwork:mDispatcher	Lfm/qingting/downloadnew/EventDispatcher;
    //   393: aload_0
    //   394: getfield 39	fm/qingting/downloadnew/DefaultNetwork:mCurTask	Lfm/qingting/downloadnew/DownloadTask;
    //   397: getfield 116	fm/qingting/downloadnew/DownloadTask:mTaskId	Ljava/lang/String;
    //   400: getstatic 331	fm/qingting/downloadnew/DownloadState:CONNECTING	Lfm/qingting/downloadnew/DownloadState;
    //   403: aconst_null
    //   404: invokevirtual 222	fm/qingting/downloadnew/EventDispatcher:sendEvent	(Ljava/lang/String;Lfm/qingting/downloadnew/DownloadState;Ljava/lang/Object;)V
    //   407: aload 11
    //   409: invokevirtual 334	java/net/HttpURLConnection:getResponseCode	()I
    //   412: istore 6
    //   414: invokestatic 263	android/os/SystemClock:elapsedRealtime	()J
    //   417: lload 4
    //   419: lsub
    //   420: lstore 22
    //   422: aload_0
    //   423: getfield 33	fm/qingting/downloadnew/DefaultNetwork:TAG	Ljava/lang/String;
    //   426: new 53	java/lang/StringBuilder
    //   429: dup
    //   430: invokespecial 54	java/lang/StringBuilder:<init>	()V
    //   433: ldc_w 336
    //   436: invokevirtual 58	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   439: lload 22
    //   441: invokevirtual 320	java/lang/StringBuilder:append	(J)Ljava/lang/StringBuilder;
    //   444: ldc_w 338
    //   447: invokevirtual 58	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   450: iload 6
    //   452: invokevirtual 67	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
    //   455: invokevirtual 78	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   458: invokestatic 84	android/util/Log:d	(Ljava/lang/String;Ljava/lang/String;)I
    //   461: pop
    //   462: aload_0
    //   463: getfield 39	fm/qingting/downloadnew/DefaultNetwork:mCurTask	Lfm/qingting/downloadnew/DownloadTask;
    //   466: getfield 159	fm/qingting/downloadnew/DownloadTask:mState	Lfm/qingting/downloadnew/DownloadState;
    //   469: getstatic 162	fm/qingting/downloadnew/DownloadState:PAUSED	Lfm/qingting/downloadnew/DownloadState;
    //   472: if_acmpne +15 -> 487
    //   475: aload_0
    //   476: aload 11
    //   478: getstatic 162	fm/qingting/downloadnew/DownloadState:PAUSED	Lfm/qingting/downloadnew/DownloadState;
    //   481: ldc 181
    //   483: invokespecial 97	fm/qingting/downloadnew/DefaultNetwork:finishTask	(Ljava/net/HttpURLConnection;Lfm/qingting/downloadnew/DownloadState;Ljava/lang/Object;)V
    //   486: return
    //   487: iload 6
    //   489: iconst_m1
    //   490: if_icmpne +69 -> 559
    //   493: aload_0
    //   494: getfield 33	fm/qingting/downloadnew/DefaultNetwork:TAG	Ljava/lang/String;
    //   497: ldc_w 340
    //   500: invokestatic 84	android/util/Log:d	(Ljava/lang/String;Ljava/lang/String;)I
    //   503: pop
    //   504: new 113	java/io/IOException
    //   507: dup
    //   508: invokespecial 341	java/io/IOException:<init>	()V
    //   511: athrow
    //   512: astore 18
    //   514: aload_0
    //   515: ldc_w 343
    //   518: aload 11
    //   520: new 37	fm/qingting/downloadnew/DownloadError
    //   523: dup
    //   524: new 53	java/lang/StringBuilder
    //   527: dup
    //   528: invokespecial 54	java/lang/StringBuilder:<init>	()V
    //   531: ldc_w 345
    //   534: invokevirtual 58	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   537: aload_0
    //   538: getfield 39	fm/qingting/downloadnew/DefaultNetwork:mCurTask	Lfm/qingting/downloadnew/DownloadTask;
    //   541: invokevirtual 266	fm/qingting/downloadnew/DownloadTask:getUrl	()Ljava/lang/String;
    //   544: invokevirtual 58	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   547: invokevirtual 78	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   550: invokespecial 277	fm/qingting/downloadnew/DownloadError:<init>	(Ljava/lang/String;)V
    //   553: invokespecial 281	fm/qingting/downloadnew/DefaultNetwork:attemptRetryUrlOnConnectionFail	(Ljava/lang/String;Ljava/net/HttpURLConnection;Lfm/qingting/downloadnew/DownloadError;)V
    //   556: goto -523 -> 33
    //   559: iload 6
    //   561: sipush 200
    //   564: if_icmpne +81 -> 645
    //   567: aload_0
    //   568: getfield 33	fm/qingting/downloadnew/DefaultNetwork:TAG	Ljava/lang/String;
    //   571: ldc_w 347
    //   574: invokestatic 84	android/util/Log:d	(Ljava/lang/String;Ljava/lang/String;)I
    //   577: pop
    //   578: aload_0
    //   579: getfield 39	fm/qingting/downloadnew/DefaultNetwork:mCurTask	Lfm/qingting/downloadnew/DownloadTask;
    //   582: getstatic 119	fm/qingting/downloadnew/DownloadState:DOWNLOADING	Lfm/qingting/downloadnew/DownloadState;
    //   585: putfield 159	fm/qingting/downloadnew/DownloadTask:mState	Lfm/qingting/downloadnew/DownloadState;
    //   588: aload_0
    //   589: getfield 39	fm/qingting/downloadnew/DefaultNetwork:mCurTask	Lfm/qingting/downloadnew/DownloadTask;
    //   592: aload 11
    //   594: invokevirtual 350	java/net/HttpURLConnection:getContentLength	()I
    //   597: i2l
    //   598: putfield 191	fm/qingting/downloadnew/DownloadTask:mTotalSize	J
    //   601: aload_0
    //   602: getfield 39	fm/qingting/downloadnew/DefaultNetwork:mCurTask	Lfm/qingting/downloadnew/DownloadTask;
    //   605: lconst_0
    //   606: putfield 140	fm/qingting/downloadnew/DownloadTask:mCurSize	J
    //   609: aload_0
    //   610: aload 11
    //   612: invokespecial 352	fm/qingting/downloadnew/DefaultNetwork:downloadToFile	(Ljava/net/HttpURLConnection;)V
    //   615: return
    //   616: astore 13
    //   618: iload 6
    //   620: ifne +251 -> 871
    //   623: aload_0
    //   624: ldc_w 354
    //   627: aload 11
    //   629: new 37	fm/qingting/downloadnew/DownloadError
    //   632: dup
    //   633: ldc_w 354
    //   636: invokespecial 277	fm/qingting/downloadnew/DownloadError:<init>	(Ljava/lang/String;)V
    //   639: invokespecial 281	fm/qingting/downloadnew/DefaultNetwork:attemptRetryUrlOnConnectionFail	(Ljava/lang/String;Ljava/net/HttpURLConnection;Lfm/qingting/downloadnew/DownloadError;)V
    //   642: goto -609 -> 33
    //   645: iload 6
    //   647: sipush 206
    //   650: if_icmpne +435 -> 1085
    //   653: aload_0
    //   654: getfield 33	fm/qingting/downloadnew/DefaultNetwork:TAG	Ljava/lang/String;
    //   657: new 53	java/lang/StringBuilder
    //   660: dup
    //   661: invokespecial 54	java/lang/StringBuilder:<init>	()V
    //   664: ldc_w 356
    //   667: invokevirtual 58	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   670: aload_0
    //   671: getfield 39	fm/qingting/downloadnew/DefaultNetwork:mCurTask	Lfm/qingting/downloadnew/DownloadTask;
    //   674: getfield 140	fm/qingting/downloadnew/DownloadTask:mCurSize	J
    //   677: invokevirtual 320	java/lang/StringBuilder:append	(J)Ljava/lang/StringBuilder;
    //   680: ldc_w 358
    //   683: invokevirtual 58	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   686: invokevirtual 78	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   689: invokestatic 84	android/util/Log:d	(Ljava/lang/String;Ljava/lang/String;)I
    //   692: pop
    //   693: aload_0
    //   694: getfield 39	fm/qingting/downloadnew/DefaultNetwork:mCurTask	Lfm/qingting/downloadnew/DownloadTask;
    //   697: getstatic 119	fm/qingting/downloadnew/DownloadState:DOWNLOADING	Lfm/qingting/downloadnew/DownloadState;
    //   700: putfield 159	fm/qingting/downloadnew/DownloadTask:mState	Lfm/qingting/downloadnew/DownloadState;
    //   703: aload_0
    //   704: aload 11
    //   706: invokespecial 352	fm/qingting/downloadnew/DefaultNetwork:downloadToFile	(Ljava/net/HttpURLConnection;)V
    //   709: return
    //   710: aload 11
    //   712: invokevirtual 362	java/net/HttpURLConnection:getHeaderFields	()Ljava/util/Map;
    //   715: ldc_w 364
    //   718: invokeinterface 370 2 0
    //   723: checkcast 372	java/util/List
    //   726: astore 26
    //   728: aload 26
    //   730: ifnull +68 -> 798
    //   733: aload 26
    //   735: invokeinterface 375 1 0
    //   740: ifle +58 -> 798
    //   743: aload_0
    //   744: getfield 39	fm/qingting/downloadnew/DefaultNetwork:mCurTask	Lfm/qingting/downloadnew/DownloadTask;
    //   747: aload 26
    //   749: iconst_0
    //   750: invokeinterface 378 2 0
    //   755: checkcast 380	java/lang/String
    //   758: putfield 104	fm/qingting/downloadnew/DownloadTask:mRedirectUrl	Ljava/lang/String;
    //   761: aload_0
    //   762: getfield 33	fm/qingting/downloadnew/DefaultNetwork:TAG	Ljava/lang/String;
    //   765: new 53	java/lang/StringBuilder
    //   768: dup
    //   769: invokespecial 54	java/lang/StringBuilder:<init>	()V
    //   772: ldc_w 382
    //   775: invokevirtual 58	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   778: aload_0
    //   779: getfield 39	fm/qingting/downloadnew/DefaultNetwork:mCurTask	Lfm/qingting/downloadnew/DownloadTask;
    //   782: getfield 104	fm/qingting/downloadnew/DownloadTask:mRedirectUrl	Ljava/lang/String;
    //   785: invokevirtual 58	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   788: invokevirtual 78	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   791: invokestatic 84	android/util/Log:d	(Ljava/lang/String;Ljava/lang/String;)I
    //   794: pop
    //   795: goto -762 -> 33
    //   798: aload_0
    //   799: getfield 39	fm/qingting/downloadnew/DefaultNetwork:mCurTask	Lfm/qingting/downloadnew/DownloadTask;
    //   802: ldc_w 384
    //   805: putfield 104	fm/qingting/downloadnew/DownloadTask:mRedirectUrl	Ljava/lang/String;
    //   808: goto -47 -> 761
    //   811: iload 6
    //   813: sipush 416
    //   816: if_icmpne +25 -> 841
    //   819: aload_0
    //   820: getfield 39	fm/qingting/downloadnew/DefaultNetwork:mCurTask	Lfm/qingting/downloadnew/DownloadTask;
    //   823: lconst_0
    //   824: putfield 140	fm/qingting/downloadnew/DownloadTask:mCurSize	J
    //   827: aload_0
    //   828: getfield 33	fm/qingting/downloadnew/DefaultNetwork:TAG	Ljava/lang/String;
    //   831: ldc_w 386
    //   834: invokestatic 84	android/util/Log:d	(Ljava/lang/String;Ljava/lang/String;)I
    //   837: pop
    //   838: goto -805 -> 33
    //   841: new 113	java/io/IOException
    //   844: dup
    //   845: invokespecial 341	java/io/IOException:<init>	()V
    //   848: athrow
    //   849: aload_0
    //   850: ldc_w 279
    //   853: aload 7
    //   855: new 37	fm/qingting/downloadnew/DownloadError
    //   858: dup
    //   859: ldc_w 279
    //   862: invokespecial 277	fm/qingting/downloadnew/DownloadError:<init>	(Ljava/lang/String;)V
    //   865: invokespecial 388	fm/qingting/downloadnew/DefaultNetwork:attemptRetryTimeoutOnException	(Ljava/lang/String;Ljava/net/HttpURLConnection;Lfm/qingting/downloadnew/DownloadError;)V
    //   868: goto -835 -> 33
    //   871: iload 6
    //   873: sipush 200
    //   876: if_icmpeq +11 -> 887
    //   879: iload 6
    //   881: sipush 206
    //   884: if_icmpne +66 -> 950
    //   887: aload 13
    //   889: invokevirtual 174	java/io/IOException:getMessage	()Ljava/lang/String;
    //   892: ldc_w 390
    //   895: invokevirtual 394	java/lang/String:startsWith	(Ljava/lang/String;)Z
    //   898: ifeq +30 -> 928
    //   901: aload_0
    //   902: aload 11
    //   904: getstatic 90	fm/qingting/downloadnew/DownloadState:ERROR	Lfm/qingting/downloadnew/DownloadState;
    //   907: aload 13
    //   909: invokevirtual 174	java/io/IOException:getMessage	()Ljava/lang/String;
    //   912: invokespecial 97	fm/qingting/downloadnew/DefaultNetwork:finishTask	(Ljava/net/HttpURLConnection;Lfm/qingting/downloadnew/DownloadState;Ljava/lang/Object;)V
    //   915: new 37	fm/qingting/downloadnew/DownloadError
    //   918: dup
    //   919: aload 13
    //   921: invokevirtual 174	java/io/IOException:getMessage	()Ljava/lang/String;
    //   924: invokespecial 277	fm/qingting/downloadnew/DownloadError:<init>	(Ljava/lang/String;)V
    //   927: athrow
    //   928: aload_0
    //   929: ldc_w 396
    //   932: aload 11
    //   934: new 37	fm/qingting/downloadnew/DownloadError
    //   937: dup
    //   938: ldc_w 396
    //   941: invokespecial 277	fm/qingting/downloadnew/DownloadError:<init>	(Ljava/lang/String;)V
    //   944: invokespecial 388	fm/qingting/downloadnew/DefaultNetwork:attemptRetryTimeoutOnException	(Ljava/lang/String;Ljava/net/HttpURLConnection;Lfm/qingting/downloadnew/DownloadError;)V
    //   947: goto -914 -> 33
    //   950: iload 6
    //   952: sipush 401
    //   955: if_icmpeq +11 -> 966
    //   958: iload 6
    //   960: sipush 403
    //   963: if_icmpne +25 -> 988
    //   966: aload_0
    //   967: ldc_w 398
    //   970: aload 11
    //   972: new 37	fm/qingting/downloadnew/DownloadError
    //   975: dup
    //   976: ldc_w 400
    //   979: invokespecial 277	fm/qingting/downloadnew/DownloadError:<init>	(Ljava/lang/String;)V
    //   982: invokespecial 388	fm/qingting/downloadnew/DefaultNetwork:attemptRetryTimeoutOnException	(Ljava/lang/String;Ljava/net/HttpURLConnection;Lfm/qingting/downloadnew/DownloadError;)V
    //   985: goto -952 -> 33
    //   988: aload_0
    //   989: new 53	java/lang/StringBuilder
    //   992: dup
    //   993: invokespecial 54	java/lang/StringBuilder:<init>	()V
    //   996: ldc_w 402
    //   999: invokevirtual 58	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1002: iload 6
    //   1004: invokevirtual 67	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
    //   1007: invokevirtual 78	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   1010: aload 11
    //   1012: new 37	fm/qingting/downloadnew/DownloadError
    //   1015: dup
    //   1016: new 53	java/lang/StringBuilder
    //   1019: dup
    //   1020: invokespecial 54	java/lang/StringBuilder:<init>	()V
    //   1023: ldc_w 402
    //   1026: invokevirtual 58	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1029: iload 6
    //   1031: invokevirtual 67	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
    //   1034: invokevirtual 78	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   1037: invokespecial 277	fm/qingting/downloadnew/DownloadError:<init>	(Ljava/lang/String;)V
    //   1040: invokespecial 281	fm/qingting/downloadnew/DefaultNetwork:attemptRetryUrlOnConnectionFail	(Ljava/lang/String;Ljava/net/HttpURLConnection;Lfm/qingting/downloadnew/DownloadError;)V
    //   1043: goto -1010 -> 33
    //   1046: astore 12
    //   1048: aload 12
    //   1050: astore 13
    //   1052: aconst_null
    //   1053: astore 11
    //   1055: iconst_0
    //   1056: istore 6
    //   1058: goto -440 -> 618
    //   1061: astore 10
    //   1063: aconst_null
    //   1064: astore 11
    //   1066: goto -552 -> 514
    //   1069: aload 15
    //   1071: astore 16
    //   1073: goto -839 -> 234
    //   1076: astore 19
    //   1078: aload 11
    //   1080: astore 7
    //   1082: goto -971 -> 111
    //   1085: iload 6
    //   1087: sipush 301
    //   1090: if_icmpeq -380 -> 710
    //   1093: iload 6
    //   1095: sipush 302
    //   1098: if_icmpne -287 -> 811
    //   1101: goto -391 -> 710
    //
    // Exception table:
    //   from	to	target	type
    //   53	82	109	java/net/SocketTimeoutException
    //   87	109	109	java/net/SocketTimeoutException
    //   234	249	109	java/net/SocketTimeoutException
    //   10	33	148	java/io/FileNotFoundException
    //   10	33	181	java/io/IOException
    //   253	350	512	java/net/MalformedURLException
    //   350	486	512	java/net/MalformedURLException
    //   493	512	512	java/net/MalformedURLException
    //   567	615	512	java/net/MalformedURLException
    //   653	709	512	java/net/MalformedURLException
    //   710	728	512	java/net/MalformedURLException
    //   733	761	512	java/net/MalformedURLException
    //   761	795	512	java/net/MalformedURLException
    //   798	808	512	java/net/MalformedURLException
    //   819	838	512	java/net/MalformedURLException
    //   841	849	512	java/net/MalformedURLException
    //   253	350	616	java/io/IOException
    //   350	486	616	java/io/IOException
    //   493	512	616	java/io/IOException
    //   567	615	616	java/io/IOException
    //   653	709	616	java/io/IOException
    //   710	728	616	java/io/IOException
    //   733	761	616	java/io/IOException
    //   761	795	616	java/io/IOException
    //   798	808	616	java/io/IOException
    //   819	838	616	java/io/IOException
    //   841	849	616	java/io/IOException
    //   53	82	1046	java/io/IOException
    //   87	109	1046	java/io/IOException
    //   234	249	1046	java/io/IOException
    //   53	82	1061	java/net/MalformedURLException
    //   87	109	1061	java/net/MalformedURLException
    //   234	249	1061	java/net/MalformedURLException
    //   253	350	1076	java/net/SocketTimeoutException
    //   350	486	1076	java/net/SocketTimeoutException
    //   493	512	1076	java/net/SocketTimeoutException
    //   567	615	1076	java/net/SocketTimeoutException
    //   653	709	1076	java/net/SocketTimeoutException
    //   710	728	1076	java/net/SocketTimeoutException
    //   733	761	1076	java/net/SocketTimeoutException
    //   761	795	1076	java/net/SocketTimeoutException
    //   798	808	1076	java/net/SocketTimeoutException
    //   819	838	1076	java/net/SocketTimeoutException
    //   841	849	1076	java/net/SocketTimeoutException
  }

  public void setUrlRewriter(UrlRewriter paramUrlRewriter)
  {
    this.mRewriter = paramUrlRewriter;
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.downloadnew.DefaultNetwork
 * JD-Core Version:    0.6.2
 */