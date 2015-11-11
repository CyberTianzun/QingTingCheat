package com.taobao.newxp.imagecache.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory.Options;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Environment;
import android.os.StatFs;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.util.LruCache;
import android.util.Log;
import com.taobao.newxp.view.widget.RecyclingBitmapDrawable;
import java.io.File;
import java.io.IOException;
import java.lang.ref.SoftReference;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashSet;
import java.util.Iterator;

public class ImageCache
{
  static ImageCache a;
  private static final String b = "ImageCache";
  private static final int c = 5120;
  private static final int d = 10485760;
  private static final Bitmap.CompressFormat e = Bitmap.CompressFormat.PNG;
  private static final int f = 70;
  private static final int g = 0;
  private static final boolean h = true;
  private static final boolean i = true;
  private static final boolean j;
  private a k;
  private LruCache<String, BitmapDrawable> l;
  private a m;
  private final Object n = new Object();
  private boolean o = true;
  private HashSet<SoftReference<Bitmap>> p;

  private ImageCache(a parama)
  {
    b(parama);
  }

  public static int a(BitmapDrawable paramBitmapDrawable)
  {
    Bitmap localBitmap = paramBitmapDrawable.getBitmap();
    if (e.e())
      return localBitmap.getByteCount();
    return localBitmap.getRowBytes() * localBitmap.getHeight();
  }

  public static long a(File paramFile)
  {
    if (e.c())
      return paramFile.getUsableSpace();
    StatFs localStatFs = new StatFs(paramFile.getPath());
    return localStatFs.getBlockSize() * localStatFs.getAvailableBlocks();
  }

  private static RetainFragment a(FragmentManager paramFragmentManager)
  {
    RetainFragment localRetainFragment = (RetainFragment)paramFragmentManager.findFragmentByTag("ImageCache");
    if (localRetainFragment == null)
    {
      localRetainFragment = new RetainFragment();
      paramFragmentManager.beginTransaction().add(localRetainFragment, "ImageCache").commitAllowingStateLoss();
    }
    return localRetainFragment;
  }

  public static ImageCache a(FragmentManager paramFragmentManager, a parama)
  {
    RetainFragment localRetainFragment = a(paramFragmentManager);
    ImageCache localImageCache = (ImageCache)localRetainFragment.a();
    if (localImageCache == null)
    {
      localImageCache = new ImageCache(parama);
      localRetainFragment.a(localImageCache);
    }
    return localImageCache;
  }

  public static ImageCache a(a parama)
  {
    if (a == null)
      a = new ImageCache(parama);
    return a;
  }

  public static File a(Context paramContext)
  {
    if (e.b())
      return paramContext.getExternalCacheDir();
    String str = "/Android/data/" + paramContext.getPackageName() + "/cache/";
    return new File(Environment.getExternalStorageDirectory().getPath() + str);
  }

  public static File a(Context paramContext, String paramString)
  {
    try
    {
      if (("mounted".equals(Environment.getExternalStorageState())) || (!e()));
      String str;
      for (Object localObject = a(paramContext).getPath(); ; localObject = str)
      {
        return new File((String)localObject + File.separator + paramString);
        str = paramContext.getCacheDir().getPath();
      }
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return new File(paramContext.getCacheDir().getPath() + File.separator + paramString);
  }

  private static String a(byte[] paramArrayOfByte)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    for (int i1 = 0; i1 < paramArrayOfByte.length; i1++)
    {
      String str = Integer.toHexString(0xFF & paramArrayOfByte[i1]);
      if (str.length() == 1)
        localStringBuilder.append('0');
      localStringBuilder.append(str);
    }
    return localStringBuilder.toString();
  }

  private static boolean a(Bitmap paramBitmap, BitmapFactory.Options paramOptions)
  {
    int i1 = paramOptions.outWidth / paramOptions.inSampleSize;
    int i2 = paramOptions.outHeight / paramOptions.inSampleSize;
    return (paramBitmap.getWidth() == i1) && (paramBitmap.getHeight() == i2);
  }

  private void b(a parama)
  {
    this.m = parama;
    if (this.m.f)
    {
      if (e.d())
        this.p = new HashSet();
      this.l = new LruCache(this.m.a)
      {
        protected int a(String paramAnonymousString, BitmapDrawable paramAnonymousBitmapDrawable)
        {
          int i = ImageCache.a(paramAnonymousBitmapDrawable) / 1024;
          if (i == 0)
            i = 1;
          return i;
        }

        protected void a(boolean paramAnonymousBoolean, String paramAnonymousString, BitmapDrawable paramAnonymousBitmapDrawable1, BitmapDrawable paramAnonymousBitmapDrawable2)
        {
          if (RecyclingBitmapDrawable.class.isInstance(paramAnonymousBitmapDrawable1))
            ((RecyclingBitmapDrawable)paramAnonymousBitmapDrawable1).setIsCached(false);
          while (!e.d())
            return;
          ImageCache.a(ImageCache.this).add(new SoftReference(paramAnonymousBitmapDrawable1.getBitmap()));
        }
      };
    }
    if (parama.h)
      a();
  }

  public static String c(String paramString)
  {
    try
    {
      MessageDigest localMessageDigest = MessageDigest.getInstance("MD5");
      localMessageDigest.update(paramString.getBytes());
      String str = a(localMessageDigest.digest());
      return str;
    }
    catch (NoSuchAlgorithmException localNoSuchAlgorithmException)
    {
    }
    return String.valueOf(paramString.hashCode());
  }

  public static boolean e()
  {
    if (e.c())
      return Environment.isExternalStorageRemovable();
    return true;
  }

  protected Bitmap a(BitmapFactory.Options paramOptions)
  {
    if ((this.p != null) && (!this.p.isEmpty()))
    {
      Iterator localIterator = this.p.iterator();
      while (localIterator.hasNext())
      {
        Bitmap localBitmap = (Bitmap)((SoftReference)localIterator.next()).get();
        if ((localBitmap != null) && (localBitmap.isMutable()))
        {
          if (a(localBitmap, paramOptions))
          {
            localIterator.remove();
            return localBitmap;
          }
        }
        else
          localIterator.remove();
      }
    }
    return null;
  }

  public BitmapDrawable a(String paramString)
  {
    LruCache localLruCache = this.l;
    BitmapDrawable localBitmapDrawable = null;
    if (localLruCache != null)
      localBitmapDrawable = (BitmapDrawable)this.l.get(paramString);
    return localBitmapDrawable;
  }

  public void a()
  {
    synchronized (this.n)
    {
      File localFile;
      if ((this.k == null) || (this.k.d()))
      {
        localFile = this.m.c;
        if ((this.m.g) && (localFile != null))
        {
          if (!localFile.exists())
            localFile.mkdirs();
          long l1 = a(localFile);
          int i1 = this.m.b;
          if (l1 <= i1);
        }
      }
      try
      {
        this.k = a.a(localFile, 1, 1, this.m.b);
        this.o = false;
        this.n.notifyAll();
        return;
      }
      catch (IOException localIOException)
      {
        while (true)
        {
          this.m.c = null;
          Log.e("ImageCache", "initDiskCache - " + localIOException);
        }
      }
    }
  }

  // ERROR //
  public void a(String paramString, BitmapDrawable paramBitmapDrawable)
  {
    // Byte code:
    //   0: aload_1
    //   1: ifnull +7 -> 8
    //   4: aload_2
    //   5: ifnonnull +4 -> 9
    //   8: return
    //   9: aload_0
    //   10: getfield 252	com/taobao/newxp/imagecache/utils/ImageCache:l	Landroid/support/v4/util/LruCache;
    //   13: ifnull +31 -> 44
    //   16: ldc_w 366
    //   19: aload_2
    //   20: invokevirtual 371	java/lang/Class:isInstance	(Ljava/lang/Object;)Z
    //   23: ifeq +11 -> 34
    //   26: aload_2
    //   27: checkcast 366	com/taobao/newxp/view/widget/RecyclingBitmapDrawable
    //   30: iconst_1
    //   31: invokevirtual 375	com/taobao/newxp/view/widget/RecyclingBitmapDrawable:setIsCached	(Z)V
    //   34: aload_0
    //   35: getfield 252	com/taobao/newxp/imagecache/utils/ImageCache:l	Landroid/support/v4/util/LruCache;
    //   38: aload_1
    //   39: aload_2
    //   40: invokevirtual 379	android/support/v4/util/LruCache:put	(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   43: pop
    //   44: aload_0
    //   45: getfield 54	com/taobao/newxp/imagecache/utils/ImageCache:n	Ljava/lang/Object;
    //   48: astore_3
    //   49: aload_3
    //   50: monitorenter
    //   51: aload_0
    //   52: getfield 329	com/taobao/newxp/imagecache/utils/ImageCache:k	Lcom/taobao/newxp/imagecache/utils/a;
    //   55: ifnull +100 -> 155
    //   58: aload_1
    //   59: invokestatic 381	com/taobao/newxp/imagecache/utils/ImageCache:c	(Ljava/lang/String;)Ljava/lang/String;
    //   62: astore 5
    //   64: aload_0
    //   65: getfield 329	com/taobao/newxp/imagecache/utils/ImageCache:k	Lcom/taobao/newxp/imagecache/utils/a;
    //   68: aload 5
    //   70: invokevirtual 384	com/taobao/newxp/imagecache/utils/a:a	(Ljava/lang/String;)Lcom/taobao/newxp/imagecache/utils/a$c;
    //   73: astore 18
    //   75: aload 18
    //   77: ifnonnull +88 -> 165
    //   80: aload_0
    //   81: getfield 329	com/taobao/newxp/imagecache/utils/ImageCache:k	Lcom/taobao/newxp/imagecache/utils/a;
    //   84: aload 5
    //   86: invokevirtual 387	com/taobao/newxp/imagecache/utils/a:b	(Ljava/lang/String;)Lcom/taobao/newxp/imagecache/utils/a$a;
    //   89: astore 19
    //   91: aconst_null
    //   92: astore 20
    //   94: aload 19
    //   96: ifnull +49 -> 145
    //   99: aload 19
    //   101: iconst_0
    //   102: invokevirtual 392	com/taobao/newxp/imagecache/utils/a$a:c	(I)Ljava/io/OutputStream;
    //   105: astore 21
    //   107: aload 21
    //   109: astore 20
    //   111: aload_2
    //   112: invokevirtual 65	android/graphics/drawable/BitmapDrawable:getBitmap	()Landroid/graphics/Bitmap;
    //   115: aload_0
    //   116: getfield 234	com/taobao/newxp/imagecache/utils/ImageCache:m	Lcom/taobao/newxp/imagecache/utils/ImageCache$a;
    //   119: getfield 394	com/taobao/newxp/imagecache/utils/ImageCache$a:d	Landroid/graphics/Bitmap$CompressFormat;
    //   122: aload_0
    //   123: getfield 234	com/taobao/newxp/imagecache/utils/ImageCache:m	Lcom/taobao/newxp/imagecache/utils/ImageCache$a;
    //   126: getfield 396	com/taobao/newxp/imagecache/utils/ImageCache$a:e	I
    //   129: aload 20
    //   131: invokevirtual 400	android/graphics/Bitmap:compress	(Landroid/graphics/Bitmap$CompressFormat;ILjava/io/OutputStream;)Z
    //   134: pop
    //   135: aload 19
    //   137: invokevirtual 401	com/taobao/newxp/imagecache/utils/a$a:a	()V
    //   140: aload 20
    //   142: invokevirtual 406	java/io/OutputStream:close	()V
    //   145: aload 20
    //   147: ifnull +8 -> 155
    //   150: aload 20
    //   152: invokevirtual 406	java/io/OutputStream:close	()V
    //   155: aload_3
    //   156: monitorexit
    //   157: return
    //   158: astore 4
    //   160: aload_3
    //   161: monitorexit
    //   162: aload 4
    //   164: athrow
    //   165: aload 18
    //   167: iconst_0
    //   168: invokevirtual 411	com/taobao/newxp/imagecache/utils/a$c:a	(I)Ljava/io/InputStream;
    //   171: invokevirtual 414	java/io/InputStream:close	()V
    //   174: aconst_null
    //   175: astore 20
    //   177: goto -32 -> 145
    //   180: astore 14
    //   182: aconst_null
    //   183: astore 7
    //   185: aload 14
    //   187: astore 15
    //   189: ldc 10
    //   191: new 154	java/lang/StringBuilder
    //   194: dup
    //   195: invokespecial 155	java/lang/StringBuilder:<init>	()V
    //   198: ldc_w 416
    //   201: invokevirtual 161	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   204: aload 15
    //   206: invokevirtual 358	java/lang/StringBuilder:append	(Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   209: invokevirtual 169	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   212: invokestatic 363	android/util/Log:e	(Ljava/lang/String;Ljava/lang/String;)I
    //   215: pop
    //   216: aload 7
    //   218: ifnull -63 -> 155
    //   221: aload 7
    //   223: invokevirtual 406	java/io/OutputStream:close	()V
    //   226: goto -71 -> 155
    //   229: astore 17
    //   231: goto -76 -> 155
    //   234: astore 10
    //   236: aconst_null
    //   237: astore 7
    //   239: aload 10
    //   241: astore 11
    //   243: ldc 10
    //   245: new 154	java/lang/StringBuilder
    //   248: dup
    //   249: invokespecial 155	java/lang/StringBuilder:<init>	()V
    //   252: ldc_w 416
    //   255: invokevirtual 161	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   258: aload 11
    //   260: invokevirtual 358	java/lang/StringBuilder:append	(Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   263: invokevirtual 169	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   266: invokestatic 363	android/util/Log:e	(Ljava/lang/String;Ljava/lang/String;)I
    //   269: pop
    //   270: aload 7
    //   272: ifnull -117 -> 155
    //   275: aload 7
    //   277: invokevirtual 406	java/io/OutputStream:close	()V
    //   280: goto -125 -> 155
    //   283: astore 13
    //   285: goto -130 -> 155
    //   288: astore 6
    //   290: aconst_null
    //   291: astore 7
    //   293: aload 6
    //   295: astore 8
    //   297: aload 7
    //   299: ifnull +8 -> 307
    //   302: aload 7
    //   304: invokevirtual 406	java/io/OutputStream:close	()V
    //   307: aload 8
    //   309: athrow
    //   310: astore 26
    //   312: goto -157 -> 155
    //   315: astore 9
    //   317: goto -10 -> 307
    //   320: astore 24
    //   322: aload 20
    //   324: astore 7
    //   326: aload 24
    //   328: astore 8
    //   330: goto -33 -> 297
    //   333: astore 8
    //   335: goto -38 -> 297
    //   338: astore 23
    //   340: aload 20
    //   342: astore 7
    //   344: aload 23
    //   346: astore 11
    //   348: goto -105 -> 243
    //   351: astore 22
    //   353: aload 20
    //   355: astore 7
    //   357: aload 22
    //   359: astore 15
    //   361: goto -172 -> 189
    //
    // Exception table:
    //   from	to	target	type
    //   51	64	158	finally
    //   150	155	158	finally
    //   155	157	158	finally
    //   160	162	158	finally
    //   221	226	158	finally
    //   275	280	158	finally
    //   302	307	158	finally
    //   307	310	158	finally
    //   64	75	180	java/io/IOException
    //   80	91	180	java/io/IOException
    //   99	107	180	java/io/IOException
    //   165	174	180	java/io/IOException
    //   221	226	229	java/io/IOException
    //   64	75	234	java/lang/Exception
    //   80	91	234	java/lang/Exception
    //   99	107	234	java/lang/Exception
    //   165	174	234	java/lang/Exception
    //   275	280	283	java/io/IOException
    //   64	75	288	finally
    //   80	91	288	finally
    //   99	107	288	finally
    //   165	174	288	finally
    //   150	155	310	java/io/IOException
    //   302	307	315	java/io/IOException
    //   111	145	320	finally
    //   189	216	333	finally
    //   243	270	333	finally
    //   111	145	338	java/lang/Exception
    //   111	145	351	java/io/IOException
  }

  // ERROR //
  public Bitmap b(String paramString)
  {
    // Byte code:
    //   0: aload_1
    //   1: invokestatic 381	com/taobao/newxp/imagecache/utils/ImageCache:c	(Ljava/lang/String;)Ljava/lang/String;
    //   4: astore_2
    //   5: aload_0
    //   6: getfield 54	com/taobao/newxp/imagecache/utils/ImageCache:n	Ljava/lang/Object;
    //   9: astore_3
    //   10: aload_3
    //   11: monitorenter
    //   12: aload_0
    //   13: getfield 56	com/taobao/newxp/imagecache/utils/ImageCache:o	Z
    //   16: istore 5
    //   18: iload 5
    //   20: ifeq +18 -> 38
    //   23: aload_0
    //   24: getfield 54	com/taobao/newxp/imagecache/utils/ImageCache:n	Ljava/lang/Object;
    //   27: invokevirtual 422	java/lang/Object:wait	()V
    //   30: goto -18 -> 12
    //   33: astore 18
    //   35: goto -23 -> 12
    //   38: aload_0
    //   39: getfield 329	com/taobao/newxp/imagecache/utils/ImageCache:k	Lcom/taobao/newxp/imagecache/utils/a;
    //   42: astore 6
    //   44: aconst_null
    //   45: astore 7
    //   47: aload 6
    //   49: ifnull +72 -> 121
    //   52: aload_0
    //   53: getfield 329	com/taobao/newxp/imagecache/utils/ImageCache:k	Lcom/taobao/newxp/imagecache/utils/a;
    //   56: aload_2
    //   57: invokevirtual 384	com/taobao/newxp/imagecache/utils/a:a	(Ljava/lang/String;)Lcom/taobao/newxp/imagecache/utils/a$c;
    //   60: astore 14
    //   62: aload 14
    //   64: ifnull +166 -> 230
    //   67: aload 14
    //   69: iconst_0
    //   70: invokevirtual 411	com/taobao/newxp/imagecache/utils/a$c:a	(I)Ljava/io/InputStream;
    //   73: astore 15
    //   75: aload 15
    //   77: astore 9
    //   79: aconst_null
    //   80: astore 7
    //   82: aload 9
    //   84: ifnull +27 -> 111
    //   87: aload 9
    //   89: checkcast 424	java/io/FileInputStream
    //   92: invokevirtual 428	java/io/FileInputStream:getFD	()Ljava/io/FileDescriptor;
    //   95: ldc_w 429
    //   98: ldc_w 429
    //   101: aload_0
    //   102: invokestatic 434	com/taobao/newxp/imagecache/utils/c:a	(Ljava/io/FileDescriptor;IILcom/taobao/newxp/imagecache/utils/ImageCache;)Landroid/graphics/Bitmap;
    //   105: astore 17
    //   107: aload 17
    //   109: astore 7
    //   111: aload 9
    //   113: ifnull +8 -> 121
    //   116: aload 9
    //   118: invokevirtual 414	java/io/InputStream:close	()V
    //   121: aload_3
    //   122: monitorexit
    //   123: aload 7
    //   125: areturn
    //   126: astore 11
    //   128: aconst_null
    //   129: astore 9
    //   131: ldc 10
    //   133: new 154	java/lang/StringBuilder
    //   136: dup
    //   137: invokespecial 155	java/lang/StringBuilder:<init>	()V
    //   140: ldc_w 436
    //   143: invokevirtual 161	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   146: aload 11
    //   148: invokevirtual 358	java/lang/StringBuilder:append	(Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   151: invokevirtual 169	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   154: invokestatic 363	android/util/Log:e	(Ljava/lang/String;Ljava/lang/String;)I
    //   157: pop
    //   158: aconst_null
    //   159: astore 7
    //   161: aload 9
    //   163: ifnull -42 -> 121
    //   166: aload 9
    //   168: invokevirtual 414	java/io/InputStream:close	()V
    //   171: aconst_null
    //   172: astore 7
    //   174: goto -53 -> 121
    //   177: astore 13
    //   179: aconst_null
    //   180: astore 7
    //   182: goto -61 -> 121
    //   185: astore 8
    //   187: aconst_null
    //   188: astore 9
    //   190: aload 9
    //   192: ifnull +8 -> 200
    //   195: aload 9
    //   197: invokevirtual 414	java/io/InputStream:close	()V
    //   200: aload 8
    //   202: athrow
    //   203: astore 4
    //   205: aload_3
    //   206: monitorexit
    //   207: aload 4
    //   209: athrow
    //   210: astore 16
    //   212: goto -91 -> 121
    //   215: astore 10
    //   217: goto -17 -> 200
    //   220: astore 8
    //   222: goto -32 -> 190
    //   225: astore 11
    //   227: goto -96 -> 131
    //   230: aconst_null
    //   231: astore 9
    //   233: aconst_null
    //   234: astore 7
    //   236: goto -125 -> 111
    //
    // Exception table:
    //   from	to	target	type
    //   23	30	33	java/lang/InterruptedException
    //   52	62	126	java/io/IOException
    //   67	75	126	java/io/IOException
    //   166	171	177	java/io/IOException
    //   52	62	185	finally
    //   67	75	185	finally
    //   12	18	203	finally
    //   23	30	203	finally
    //   38	44	203	finally
    //   116	121	203	finally
    //   121	123	203	finally
    //   166	171	203	finally
    //   195	200	203	finally
    //   200	203	203	finally
    //   205	207	203	finally
    //   116	121	210	java/io/IOException
    //   195	200	215	java/io/IOException
    //   87	107	220	finally
    //   131	158	220	finally
    //   87	107	225	java/io/IOException
  }

  public void b()
  {
    if (this.l != null)
      this.l.evictAll();
    synchronized (this.n)
    {
      this.o = true;
      if (this.k != null)
      {
        boolean bool = this.k.d();
        if (bool);
      }
      try
      {
        this.k.f();
        this.k = null;
        a();
        return;
      }
      catch (IOException localIOException)
      {
        while (true)
          Log.e("ImageCache", "clearCache - " + localIOException);
      }
    }
  }

  public void c()
  {
    synchronized (this.n)
    {
      a locala = this.k;
      if (locala != null);
      try
      {
        this.k.e();
        return;
      }
      catch (IOException localIOException)
      {
        while (true)
          Log.e("ImageCache", "flush - " + localIOException);
      }
    }
  }

  public void d()
  {
    synchronized (this.n)
    {
      a locala = this.k;
      if (locala != null);
      try
      {
        if (!this.k.d())
        {
          this.k.close();
          this.k = null;
        }
        return;
      }
      catch (IOException localIOException)
      {
        while (true)
          Log.e("ImageCache", "close - " + localIOException);
      }
    }
  }

  public static class RetainFragment extends Fragment
  {
    private Object a;

    public Object a()
    {
      return this.a;
    }

    public void a(Object paramObject)
    {
      this.a = paramObject;
    }

    public void onCreate(Bundle paramBundle)
    {
      super.onCreate(paramBundle);
      setRetainInstance(true);
    }
  }

  public static class a
  {
    public int a = 5120;
    public int b = 10485760;
    public File c;
    public Bitmap.CompressFormat d = ImageCache.f();
    public int e = 70;
    public boolean f = true;
    public boolean g = true;
    public boolean h = false;

    public a(Context paramContext, String paramString)
    {
      this.c = ImageCache.a(paramContext, paramString);
    }

    public void a(float paramFloat)
    {
      if ((paramFloat < 0.05F) || (paramFloat > 0.8F))
        throw new IllegalArgumentException("setMemCacheSizePercent - percent must be between 0.05 and 0.8 (inclusive)");
      this.a = Math.round(paramFloat * (float)Runtime.getRuntime().maxMemory() / 1024.0F);
    }
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.taobao.newxp.imagecache.utils.ImageCache
 * JD-Core Version:    0.6.2
 */