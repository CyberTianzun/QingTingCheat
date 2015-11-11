package com.taobao.newxp.imagecache.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build.VERSION;
import android.util.Log;
import android.widget.Toast;
import java.io.File;
import java.io.IOException;

public class b extends c
{
  private static final String e = "ImageFetcher";
  private static final int f = 10485760;
  private static final String g = "http";
  private static final int h = 8192;
  private static final int m;
  private a i;
  private File j;
  private boolean k = true;
  private final Object l = new Object();

  public b(Context paramContext, int paramInt)
  {
    super(paramContext, paramInt);
    a(paramContext);
  }

  public b(Context paramContext, int paramInt1, int paramInt2)
  {
    super(paramContext, paramInt1, paramInt2);
    a(paramContext);
  }

  // ERROR //
  private Bitmap a(String paramString)
  {
    // Byte code:
    //   0: aload_1
    //   1: invokestatic 58	com/taobao/newxp/imagecache/utils/ImageCache:c	(Ljava/lang/String;)Ljava/lang/String;
    //   4: astore_2
    //   5: aload_0
    //   6: getfield 38	com/taobao/newxp/imagecache/utils/b:l	Ljava/lang/Object;
    //   9: astore_3
    //   10: aload_3
    //   11: monitorenter
    //   12: aload_0
    //   13: getfield 31	com/taobao/newxp/imagecache/utils/b:k	Z
    //   16: istore 5
    //   18: iload 5
    //   20: ifeq +18 -> 38
    //   23: aload_0
    //   24: getfield 38	com/taobao/newxp/imagecache/utils/b:l	Ljava/lang/Object;
    //   27: invokevirtual 61	java/lang/Object:wait	()V
    //   30: goto -18 -> 12
    //   33: astore 27
    //   35: goto -23 -> 12
    //   38: aload_0
    //   39: getfield 63	com/taobao/newxp/imagecache/utils/b:i	Lcom/taobao/newxp/imagecache/utils/a;
    //   42: astore 6
    //   44: aload 6
    //   46: ifnull +379 -> 425
    //   49: aload_0
    //   50: getfield 63	com/taobao/newxp/imagecache/utils/b:i	Lcom/taobao/newxp/imagecache/utils/a;
    //   53: aload_2
    //   54: invokevirtual 68	com/taobao/newxp/imagecache/utils/a:a	(Ljava/lang/String;)Lcom/taobao/newxp/imagecache/utils/a$c;
    //   57: astore 19
    //   59: aload 19
    //   61: ifnonnull +47 -> 108
    //   64: aload_0
    //   65: getfield 63	com/taobao/newxp/imagecache/utils/b:i	Lcom/taobao/newxp/imagecache/utils/a;
    //   68: aload_2
    //   69: invokevirtual 72	com/taobao/newxp/imagecache/utils/a:b	(Ljava/lang/String;)Lcom/taobao/newxp/imagecache/utils/a$a;
    //   72: astore 20
    //   74: aload 20
    //   76: ifnull +22 -> 98
    //   79: aload_0
    //   80: aload_1
    //   81: aload 20
    //   83: iconst_0
    //   84: invokevirtual 77	com/taobao/newxp/imagecache/utils/a$a:c	(I)Ljava/io/OutputStream;
    //   87: invokevirtual 80	com/taobao/newxp/imagecache/utils/b:a	(Ljava/lang/String;Ljava/io/OutputStream;)Z
    //   90: ifeq +106 -> 196
    //   93: aload 20
    //   95: invokevirtual 82	com/taobao/newxp/imagecache/utils/a$a:a	()V
    //   98: aload_0
    //   99: getfield 63	com/taobao/newxp/imagecache/utils/b:i	Lcom/taobao/newxp/imagecache/utils/a;
    //   102: aload_2
    //   103: invokevirtual 68	com/taobao/newxp/imagecache/utils/a:a	(Ljava/lang/String;)Lcom/taobao/newxp/imagecache/utils/a$c;
    //   106: astore 19
    //   108: aload 19
    //   110: ifnull +306 -> 416
    //   113: aload 19
    //   115: iconst_0
    //   116: invokevirtual 87	com/taobao/newxp/imagecache/utils/a$c:a	(I)Ljava/io/InputStream;
    //   119: checkcast 89	java/io/FileInputStream
    //   122: astore 21
    //   124: aload 21
    //   126: invokevirtual 93	java/io/FileInputStream:getFD	()Ljava/io/FileDescriptor;
    //   129: astore 25
    //   131: aload 25
    //   133: astore 7
    //   135: aload 21
    //   137: astore 8
    //   139: aload 7
    //   141: ifnonnull +13 -> 154
    //   144: aload 8
    //   146: ifnull +8 -> 154
    //   149: aload 8
    //   151: invokevirtual 96	java/io/FileInputStream:close	()V
    //   154: aload_3
    //   155: monitorexit
    //   156: aconst_null
    //   157: astore 9
    //   159: aload 7
    //   161: ifnull +22 -> 183
    //   164: aload 7
    //   166: aload_0
    //   167: getfield 98	com/taobao/newxp/imagecache/utils/b:a	I
    //   170: aload_0
    //   171: getfield 100	com/taobao/newxp/imagecache/utils/b:b	I
    //   174: aload_0
    //   175: invokevirtual 103	com/taobao/newxp/imagecache/utils/b:f	()Lcom/taobao/newxp/imagecache/utils/ImageCache;
    //   178: invokestatic 106	com/taobao/newxp/imagecache/utils/b:a	(Ljava/io/FileDescriptor;IILcom/taobao/newxp/imagecache/utils/ImageCache;)Landroid/graphics/Bitmap;
    //   181: astore 9
    //   183: aload 8
    //   185: ifnull +8 -> 193
    //   188: aload 8
    //   190: invokevirtual 96	java/io/FileInputStream:close	()V
    //   193: aload 9
    //   195: areturn
    //   196: aload 20
    //   198: invokevirtual 108	com/taobao/newxp/imagecache/utils/a$a:b	()V
    //   201: goto -103 -> 98
    //   204: astore 16
    //   206: aconst_null
    //   207: astore 8
    //   209: ldc 8
    //   211: new 110	java/lang/StringBuilder
    //   214: dup
    //   215: invokespecial 111	java/lang/StringBuilder:<init>	()V
    //   218: ldc 113
    //   220: invokevirtual 117	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   223: aload 16
    //   225: invokevirtual 120	java/lang/StringBuilder:append	(Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   228: invokevirtual 124	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   231: invokestatic 129	android/util/Log:e	(Ljava/lang/String;Ljava/lang/String;)I
    //   234: pop
    //   235: iconst_0
    //   236: ifne +174 -> 410
    //   239: aload 8
    //   241: ifnull +169 -> 410
    //   244: aload 8
    //   246: invokevirtual 96	java/io/FileInputStream:close	()V
    //   249: aconst_null
    //   250: astore 7
    //   252: goto -98 -> 154
    //   255: astore 18
    //   257: aconst_null
    //   258: astore 7
    //   260: goto -106 -> 154
    //   263: astore 13
    //   265: aconst_null
    //   266: astore 8
    //   268: ldc 8
    //   270: new 110	java/lang/StringBuilder
    //   273: dup
    //   274: invokespecial 111	java/lang/StringBuilder:<init>	()V
    //   277: ldc 113
    //   279: invokevirtual 117	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   282: aload 13
    //   284: invokevirtual 120	java/lang/StringBuilder:append	(Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   287: invokevirtual 124	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   290: invokestatic 129	android/util/Log:e	(Ljava/lang/String;Ljava/lang/String;)I
    //   293: pop
    //   294: iconst_0
    //   295: ifne +115 -> 410
    //   298: aload 8
    //   300: ifnull +110 -> 410
    //   303: aload 8
    //   305: invokevirtual 96	java/io/FileInputStream:close	()V
    //   308: aconst_null
    //   309: astore 7
    //   311: goto -157 -> 154
    //   314: astore 15
    //   316: aconst_null
    //   317: astore 7
    //   319: goto -165 -> 154
    //   322: astore 11
    //   324: aconst_null
    //   325: astore 8
    //   327: iconst_0
    //   328: ifne +13 -> 341
    //   331: aload 8
    //   333: ifnull +8 -> 341
    //   336: aload 8
    //   338: invokevirtual 96	java/io/FileInputStream:close	()V
    //   341: aload 11
    //   343: athrow
    //   344: astore 4
    //   346: aload_3
    //   347: monitorexit
    //   348: aload 4
    //   350: athrow
    //   351: astore 26
    //   353: goto -199 -> 154
    //   356: astore 12
    //   358: goto -17 -> 341
    //   361: astore 10
    //   363: aload 9
    //   365: areturn
    //   366: astore 24
    //   368: aload 21
    //   370: astore 8
    //   372: aload 24
    //   374: astore 11
    //   376: goto -49 -> 327
    //   379: astore 11
    //   381: goto -54 -> 327
    //   384: astore 23
    //   386: aload 21
    //   388: astore 8
    //   390: aload 23
    //   392: astore 13
    //   394: goto -126 -> 268
    //   397: astore 22
    //   399: aload 21
    //   401: astore 8
    //   403: aload 22
    //   405: astore 16
    //   407: goto -198 -> 209
    //   410: aconst_null
    //   411: astore 7
    //   413: goto -259 -> 154
    //   416: aconst_null
    //   417: astore 7
    //   419: aconst_null
    //   420: astore 8
    //   422: goto -283 -> 139
    //   425: aconst_null
    //   426: astore 7
    //   428: aconst_null
    //   429: astore 8
    //   431: goto -277 -> 154
    //
    // Exception table:
    //   from	to	target	type
    //   23	30	33	java/lang/InterruptedException
    //   49	59	204	java/io/IOException
    //   64	74	204	java/io/IOException
    //   79	98	204	java/io/IOException
    //   98	108	204	java/io/IOException
    //   113	124	204	java/io/IOException
    //   196	201	204	java/io/IOException
    //   244	249	255	java/io/IOException
    //   49	59	263	java/lang/IllegalStateException
    //   64	74	263	java/lang/IllegalStateException
    //   79	98	263	java/lang/IllegalStateException
    //   98	108	263	java/lang/IllegalStateException
    //   113	124	263	java/lang/IllegalStateException
    //   196	201	263	java/lang/IllegalStateException
    //   303	308	314	java/io/IOException
    //   49	59	322	finally
    //   64	74	322	finally
    //   79	98	322	finally
    //   98	108	322	finally
    //   113	124	322	finally
    //   196	201	322	finally
    //   12	18	344	finally
    //   23	30	344	finally
    //   38	44	344	finally
    //   149	154	344	finally
    //   154	156	344	finally
    //   244	249	344	finally
    //   303	308	344	finally
    //   336	341	344	finally
    //   341	344	344	finally
    //   346	348	344	finally
    //   149	154	351	java/io/IOException
    //   336	341	356	java/io/IOException
    //   188	193	361	java/io/IOException
    //   124	131	366	finally
    //   209	235	379	finally
    //   268	294	379	finally
    //   124	131	384	java/lang/IllegalStateException
    //   124	131	397	java/io/IOException
  }

  private void a(Context paramContext)
  {
    b(paramContext);
    this.j = ImageCache.a(paramContext, "http");
  }

  private void b(Context paramContext)
  {
    NetworkInfo localNetworkInfo = ((ConnectivityManager)paramContext.getSystemService("connectivity")).getActiveNetworkInfo();
    if ((localNetworkInfo == null) || (!localNetworkInfo.isConnectedOrConnecting()))
    {
      Toast.makeText(paramContext, "网络连接失败", 1).show();
      Log.e("ImageFetcher", "checkConnection - no connection found");
    }
  }

  public static void e()
  {
    if (Build.VERSION.SDK_INT < 8)
      System.setProperty("http.keepAlive", "false");
  }

  private void j()
  {
    if (!this.j.exists())
      this.j.mkdirs();
    synchronized (this.l)
    {
      long l1 = ImageCache.a(this.j);
      if (l1 > 10485760L);
      try
      {
        this.i = a.a(this.j, 1, 1, 10485760L);
        this.k = false;
        this.l.notifyAll();
        return;
      }
      catch (IOException localIOException)
      {
        while (true)
          this.i = null;
      }
    }
  }

  protected Bitmap a(Object paramObject)
  {
    return a(String.valueOf(paramObject));
  }

  protected void a()
  {
    super.a();
    j();
  }

  // ERROR //
  public boolean a(String paramString, java.io.OutputStream paramOutputStream)
  {
    // Byte code:
    //   0: aconst_null
    //   1: astore_3
    //   2: invokestatic 217	com/taobao/newxp/imagecache/utils/b:e	()V
    //   5: new 219	java/net/URL
    //   8: dup
    //   9: aload_1
    //   10: invokespecial 222	java/net/URL:<init>	(Ljava/lang/String;)V
    //   13: invokevirtual 226	java/net/URL:openConnection	()Ljava/net/URLConnection;
    //   16: checkcast 228	java/net/HttpURLConnection
    //   19: astore 11
    //   21: new 230	java/io/BufferedInputStream
    //   24: dup
    //   25: aload 11
    //   27: invokevirtual 234	java/net/HttpURLConnection:getInputStream	()Ljava/io/InputStream;
    //   30: sipush 8192
    //   33: invokespecial 237	java/io/BufferedInputStream:<init>	(Ljava/io/InputStream;I)V
    //   36: astore 12
    //   38: new 239	java/io/BufferedOutputStream
    //   41: dup
    //   42: aload_2
    //   43: sipush 8192
    //   46: invokespecial 242	java/io/BufferedOutputStream:<init>	(Ljava/io/OutputStream;I)V
    //   49: astore 13
    //   51: aload 12
    //   53: invokevirtual 246	java/io/BufferedInputStream:read	()I
    //   56: istore 16
    //   58: iload 16
    //   60: iconst_m1
    //   61: if_icmpeq +86 -> 147
    //   64: aload 13
    //   66: iload 16
    //   68: invokevirtual 250	java/io/BufferedOutputStream:write	(I)V
    //   71: goto -20 -> 51
    //   74: astore 15
    //   76: aload 13
    //   78: astore_3
    //   79: aload 11
    //   81: astore 6
    //   83: aload 15
    //   85: astore 4
    //   87: aload 12
    //   89: astore 5
    //   91: ldc 8
    //   93: new 110	java/lang/StringBuilder
    //   96: dup
    //   97: invokespecial 111	java/lang/StringBuilder:<init>	()V
    //   100: ldc 252
    //   102: invokevirtual 117	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   105: aload 4
    //   107: invokevirtual 120	java/lang/StringBuilder:append	(Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   110: invokevirtual 124	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   113: invokestatic 129	android/util/Log:e	(Ljava/lang/String;Ljava/lang/String;)I
    //   116: pop
    //   117: aload 6
    //   119: ifnull +8 -> 127
    //   122: aload 6
    //   124: invokevirtual 255	java/net/HttpURLConnection:disconnect	()V
    //   127: aload_3
    //   128: ifnull +7 -> 135
    //   131: aload_3
    //   132: invokevirtual 256	java/io/BufferedOutputStream:close	()V
    //   135: aload 5
    //   137: ifnull +8 -> 145
    //   140: aload 5
    //   142: invokevirtual 257	java/io/BufferedInputStream:close	()V
    //   145: iconst_0
    //   146: ireturn
    //   147: aload 11
    //   149: ifnull +8 -> 157
    //   152: aload 11
    //   154: invokevirtual 255	java/net/HttpURLConnection:disconnect	()V
    //   157: aload 13
    //   159: ifnull +8 -> 167
    //   162: aload 13
    //   164: invokevirtual 256	java/io/BufferedOutputStream:close	()V
    //   167: aload 12
    //   169: ifnull +8 -> 177
    //   172: aload 12
    //   174: invokevirtual 257	java/io/BufferedInputStream:close	()V
    //   177: iconst_1
    //   178: ireturn
    //   179: astore 7
    //   181: aconst_null
    //   182: astore 5
    //   184: aconst_null
    //   185: astore 6
    //   187: aload 6
    //   189: ifnull +8 -> 197
    //   192: aload 6
    //   194: invokevirtual 255	java/net/HttpURLConnection:disconnect	()V
    //   197: aload_3
    //   198: ifnull +7 -> 205
    //   201: aload_3
    //   202: invokevirtual 256	java/io/BufferedOutputStream:close	()V
    //   205: aload 5
    //   207: ifnull +8 -> 215
    //   210: aload 5
    //   212: invokevirtual 257	java/io/BufferedInputStream:close	()V
    //   215: aload 7
    //   217: athrow
    //   218: astore 8
    //   220: goto -5 -> 215
    //   223: astore 21
    //   225: aload 11
    //   227: astore 6
    //   229: aload 21
    //   231: astore 7
    //   233: aconst_null
    //   234: astore 5
    //   236: aconst_null
    //   237: astore_3
    //   238: goto -51 -> 187
    //   241: astore 19
    //   243: aload 12
    //   245: astore 5
    //   247: aload 11
    //   249: astore 6
    //   251: aload 19
    //   253: astore 7
    //   255: aconst_null
    //   256: astore_3
    //   257: goto -70 -> 187
    //   260: astore 14
    //   262: aload 13
    //   264: astore_3
    //   265: aload 11
    //   267: astore 6
    //   269: aload 14
    //   271: astore 7
    //   273: aload 12
    //   275: astore 5
    //   277: goto -90 -> 187
    //   280: astore 7
    //   282: goto -95 -> 187
    //   285: astore 10
    //   287: goto -142 -> 145
    //   290: astore 4
    //   292: aconst_null
    //   293: astore 5
    //   295: aconst_null
    //   296: astore 6
    //   298: aconst_null
    //   299: astore_3
    //   300: goto -209 -> 91
    //   303: astore 20
    //   305: aload 11
    //   307: astore 6
    //   309: aload 20
    //   311: astore 4
    //   313: aconst_null
    //   314: astore 5
    //   316: aconst_null
    //   317: astore_3
    //   318: goto -227 -> 91
    //   321: astore 18
    //   323: aload 12
    //   325: astore 5
    //   327: aload 11
    //   329: astore 6
    //   331: aload 18
    //   333: astore 4
    //   335: aconst_null
    //   336: astore_3
    //   337: goto -246 -> 91
    //   340: astore 17
    //   342: goto -165 -> 177
    //
    // Exception table:
    //   from	to	target	type
    //   51	58	74	java/io/IOException
    //   64	71	74	java/io/IOException
    //   5	21	179	finally
    //   201	205	218	java/io/IOException
    //   210	215	218	java/io/IOException
    //   21	38	223	finally
    //   38	51	241	finally
    //   51	58	260	finally
    //   64	71	260	finally
    //   91	117	280	finally
    //   131	135	285	java/io/IOException
    //   140	145	285	java/io/IOException
    //   5	21	290	java/io/IOException
    //   21	38	303	java/io/IOException
    //   38	51	321	java/io/IOException
    //   162	167	340	java/io/IOException
    //   172	177	340	java/io/IOException
  }

  protected void b()
  {
    super.b();
    synchronized (this.l)
    {
      if (this.i != null)
      {
        boolean bool = this.i.d();
        if (bool);
      }
      try
      {
        this.i.f();
        this.i = null;
        this.k = true;
        j();
        return;
      }
      catch (IOException localIOException)
      {
        while (true)
          Log.e("ImageFetcher", "clearCacheInternal - " + localIOException);
      }
    }
  }

  protected void c()
  {
    super.c();
    synchronized (this.l)
    {
      a locala = this.i;
      if (locala != null);
      try
      {
        this.i.e();
        return;
      }
      catch (IOException localIOException)
      {
        while (true)
          Log.e("ImageFetcher", "flush - " + localIOException);
      }
    }
  }

  protected void d()
  {
    super.d();
    synchronized (this.l)
    {
      a locala = this.i;
      if (locala != null);
      try
      {
        if (!this.i.d())
        {
          this.i.close();
          this.i = null;
        }
        return;
      }
      catch (IOException localIOException)
      {
        while (true)
          Log.e("ImageFetcher", "closeCacheInternal - " + localIOException);
      }
    }
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.taobao.newxp.imagecache.utils.b
 * JD-Core Version:    0.6.2
 */