package fm.qingting.framework.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory.Options;
import android.os.AsyncTask;
import android.widget.ImageView;
import fm.qingting.framework.data.DataManager;
import fm.qingting.framework.data.IResultToken;
import fm.qingting.framework.data.Result;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class OldVersionImageLoader
{
  private static OldVersionImageLoader instance = null;
  private String DefDataBasePath = "/data/data/fm.qingting.qtradio/pics/";
  private HashMap<String, HashMap<String, ImageCache>> globalImageCache = new HashMap();
  private HashMap<ImageView, ImageLoaderHandler> imageViewHandlers = new HashMap();
  private HashMap<ImageView, ImageCache> imagesOrder = new HashMap();
  private HashMap<ImageCache, HashSet<ImageLoaderHandler>> loadingOrder = new HashMap();
  private HashMap<SoftReference<Object>, HashSet<ImageCache>> localImageCache = new HashMap();
  private ReferenceQueue<Object> ownerReferenceQueue = new ReferenceQueue();

  private void addCache(ImageCache paramImageCache, Object paramObject)
  {
    Iterator localIterator = this.localImageCache.keySet().iterator();
    boolean bool = localIterator.hasNext();
    Object localObject = null;
    label29: HashSet localHashSet;
    label69: HashMap localHashMap;
    if (!bool)
    {
      if (localObject != null)
        break label178;
      SoftReference localSoftReference2 = new SoftReference(paramObject, this.ownerReferenceQueue);
      localHashSet = new HashSet();
      this.localImageCache.put(localSoftReference2, localHashSet);
      if (!localHashSet.contains(paramImageCache))
      {
        localHashSet.add(paramImageCache);
        paramImageCache.retain();
      }
      if (this.globalImageCache.containsKey(paramImageCache.url))
        break label195;
      localHashMap = new HashMap();
      this.globalImageCache.put(paramImageCache.url, localHashMap);
    }
    while (true)
    {
      if (!localHashMap.containsKey(paramImageCache.tag))
        localHashMap.put(paramImageCache.tag, paramImageCache);
      return;
      SoftReference localSoftReference1 = (SoftReference)localIterator.next();
      if (localSoftReference1.get() != paramObject)
        break;
      localObject = localSoftReference1;
      break label29;
      label178: localHashSet = (HashSet)this.localImageCache.get(localObject);
      break label69;
      label195: localHashMap = (HashMap)this.globalImageCache.get(paramImageCache.url);
    }
  }

  private void clearCacheMap(Set<ImageCache> paramSet)
  {
    if (paramSet == null);
    while (true)
    {
      return;
      Iterator localIterator = paramSet.iterator();
      while (localIterator.hasNext())
      {
        ImageCache localImageCache1 = (ImageCache)localIterator.next();
        if (localImageCache1.release() == 0)
        {
          Map localMap = (Map)this.globalImageCache.get(localImageCache1.url);
          localMap.remove(localImageCache1.tag);
          if (!localMap.keySet().iterator().hasNext())
            this.globalImageCache.remove(localImageCache1.url);
        }
      }
    }
  }

  private void clearReleasedOwnerCache()
  {
    while (true)
    {
      SoftReference localSoftReference = (SoftReference)this.ownerReferenceQueue.poll();
      if (localSoftReference == null)
        return;
      releaseLocalCache(localSoftReference);
    }
  }

  private static int computeInitialSampleSize(BitmapFactory.Options paramOptions, int paramInt1, int paramInt2)
  {
    double d1 = paramOptions.outWidth;
    double d2 = paramOptions.outHeight;
    int i;
    int j;
    if (paramInt2 == -1)
    {
      i = 1;
      if (paramInt1 != -1)
        break label60;
      j = 128;
      label31: if (j >= i)
        break label84;
    }
    label60: label84: 
    do
    {
      return i;
      i = (int)Math.ceil(Math.sqrt(d1 * d2 / paramInt2));
      break;
      j = (int)Math.min(Math.floor(d1 / paramInt1), Math.floor(d2 / paramInt1));
      break label31;
      if ((paramInt2 == -1) && (paramInt1 == -1))
        return 1;
    }
    while (paramInt1 == -1);
    return j;
  }

  public static int computeSampleSize(BitmapFactory.Options paramOptions, int paramInt1, int paramInt2)
  {
    int i = computeInitialSampleSize(paramOptions, paramInt1, paramInt2);
    if (i <= 8)
    {
      int j = 1;
      while (true)
      {
        if (j >= i)
          return j;
        j <<= 1;
      }
    }
    return 8 * ((i + 7) / 8);
  }

  private ImageCache getCache(String paramString1, String paramString2)
  {
    if (!this.globalImageCache.containsKey(paramString1));
    Map localMap;
    do
    {
      return null;
      localMap = (Map)this.globalImageCache.get(paramString1);
    }
    while (!localMap.containsKey(paramString2));
    return (ImageCache)localMap.get(paramString2);
  }

  public static OldVersionImageLoader getInstance()
  {
    try
    {
      if (instance == null)
        instance = new OldVersionImageLoader();
      OldVersionImageLoader localOldVersionImageLoader = instance;
      return localOldVersionImageLoader;
    }
    finally
    {
    }
  }

  private String getTag(int paramInt1, int paramInt2)
  {
    if ((paramInt1 == 0) && (paramInt2 == 0))
      return "";
    Object[] arrayOfObject = new Object[2];
    arrayOfObject[0] = Integer.valueOf(paramInt1);
    arrayOfObject[1] = Integer.valueOf(paramInt2);
    return String.format("%1$-4d_%1$-4d", arrayOfObject);
  }

  private void loadCompleteHandler(ImageCache paramImageCache, Bitmap paramBitmap)
  {
    updateCache(paramImageCache, paramBitmap);
  }

  private void loadFailedHandler(ImageCache paramImageCache)
  {
    updateCache(paramImageCache, null);
  }

  private void releaseLocalCache(SoftReference<Object> paramSoftReference)
  {
    if (paramSoftReference == null);
    HashSet localHashSet;
    do
    {
      return;
      localHashSet = (HashSet)this.localImageCache.remove(paramSoftReference);
    }
    while (localHashSet == null);
    clearCacheMap(localHashSet);
    localHashSet.clear();
  }

  private void updateCache(ImageCache paramImageCache, Bitmap paramBitmap)
  {
    if (paramBitmap != null)
      paramImageCache.image = paramBitmap;
    Iterator localIterator1 = this.imagesOrder.keySet().iterator();
    HashSet localHashSet;
    ImageView localImageView;
    ImageLoaderHandler localImageLoaderHandler1;
    do
    {
      do
      {
        if (!localIterator1.hasNext())
        {
          localHashSet = (HashSet)this.loadingOrder.remove(paramImageCache);
          if (localHashSet != null)
            break;
          return;
        }
        localImageView = (ImageView)localIterator1.next();
      }
      while ((ImageCache)this.imagesOrder.get(localImageView) != paramImageCache);
      localIterator1.remove();
      if (paramBitmap != null)
        localImageView.setImageBitmap(paramBitmap);
      localImageLoaderHandler1 = (ImageLoaderHandler)this.imageViewHandlers.remove(localImageView);
    }
    while (localImageLoaderHandler1 == null);
    if (paramBitmap != null);
    for (boolean bool1 = true; ; bool1 = false)
    {
      localImageLoaderHandler1.updateImageViewFinish(bool1, localImageView, paramImageCache.url, paramImageCache.image);
      break;
    }
    Iterator localIterator2 = localHashSet.iterator();
    ImageLoaderHandler localImageLoaderHandler2;
    do
    {
      if (!localIterator2.hasNext())
      {
        localHashSet.clear();
        return;
      }
      localImageLoaderHandler2 = (ImageLoaderHandler)localIterator2.next();
    }
    while (localImageLoaderHandler2 == null);
    if (paramBitmap != null);
    for (boolean bool2 = true; ; bool2 = false)
    {
      localImageLoaderHandler2.loadImageFinish(bool2, paramImageCache.url, paramImageCache.image, paramImageCache.width, paramImageCache.height);
      break;
    }
  }

  public Bitmap getImage(String paramString, int paramInt1, int paramInt2)
  {
    ImageCache localImageCache1 = getCache(paramString, getTag(paramInt1, paramInt2));
    if ((localImageCache1 != null) && (localImageCache1.available()))
      return localImageCache1.image;
    return null;
  }

  public void loadImage(String paramString, ImageView paramImageView, Object paramObject)
  {
    loadImage(paramString, paramImageView, paramObject, 0, 0, null);
  }

  public void loadImage(String paramString, ImageView paramImageView, Object paramObject, int paramInt1, int paramInt2)
  {
    loadImage(paramString, paramImageView, paramObject, paramInt1, paramInt2, null);
  }

  public void loadImage(String paramString, ImageView paramImageView, Object paramObject, int paramInt1, int paramInt2, ImageLoaderHandler paramImageLoaderHandler)
  {
    ImageCache localImageCache1 = getCache(paramString, getTag(paramInt1, paramInt2));
    if (localImageCache1 == null)
      localImageCache1 = new ImageCache(null, paramString, 0, getTag(paramInt1, paramInt2), paramInt1, paramInt2);
    addCache(localImageCache1, paramObject);
    if (paramImageView != null)
    {
      this.imagesOrder.remove(paramImageView);
      this.imageViewHandlers.remove(paramImageView);
    }
    if (localImageCache1.available())
    {
      if (paramImageView != null)
        paramImageView.setImageBitmap(localImageCache1.image);
      if (paramImageLoaderHandler != null)
      {
        paramImageLoaderHandler.loadImageFinish(true, paramString, localImageCache1.image, localImageCache1.width, localImageCache1.height);
        if (paramImageView != null)
          paramImageLoaderHandler.updateImageViewFinish(true, paramImageView, paramString, localImageCache1.image);
      }
    }
    while (true)
    {
      clearReleasedOwnerCache();
      return;
      if (paramImageView != null)
      {
        this.imagesOrder.put(paramImageView, localImageCache1);
        if (paramImageLoaderHandler != null)
          this.imageViewHandlers.put(paramImageView, paramImageLoaderHandler);
      }
      if (!this.loadingOrder.containsKey(localImageCache1))
      {
        new LoadTask().execute(new ImageCache[] { localImageCache1 });
        this.loadingOrder.put(localImageCache1, new HashSet());
      }
      if (paramImageLoaderHandler != null)
        ((HashSet)this.loadingOrder.get(localImageCache1)).add(paramImageLoaderHandler);
    }
  }

  public void loadImage(String paramString, ImageView paramImageView, Object paramObject, ImageLoaderHandler paramImageLoaderHandler)
  {
    loadImage(paramString, paramImageView, paramObject, 0, 0, paramImageLoaderHandler);
  }

  public void releaseAllCache()
  {
    this.localImageCache.clear();
    this.globalImageCache.clear();
  }

  public void releaseCache(Object paramObject)
  {
    if (paramObject == null)
      return;
    Iterator localIterator = this.localImageCache.keySet().iterator();
    boolean bool = localIterator.hasNext();
    Object localObject = null;
    if (!bool);
    while (true)
    {
      releaseLocalCache((SoftReference)localObject);
      return;
      SoftReference localSoftReference = (SoftReference)localIterator.next();
      if (localSoftReference.get() != paramObject)
        break;
      localObject = localSoftReference;
    }
  }

  public void reset()
  {
    stopAllLoading();
    releaseAllCache();
    this.loadingOrder.clear();
    this.imagesOrder.clear();
    this.imageViewHandlers.clear();
    this.ownerReferenceQueue = new ReferenceQueue();
  }

  public void setDefDataPath(String paramString)
  {
    if ((paramString == null) || (paramString.equalsIgnoreCase("")))
      return;
    this.DefDataBasePath = paramString;
  }

  public void stopAllLoading()
  {
    Iterator localIterator = new ArrayList(this.imagesOrder.values()).iterator();
    while (true)
    {
      if (!localIterator.hasNext())
        return;
      loadFailedHandler((ImageCache)localIterator.next());
    }
  }

  private class ImageCache
  {
    public int height;
    public Bitmap image;
    public int retainCount;
    public String tag;
    public String url;
    public int width;

    public ImageCache(Bitmap paramString1, String paramInt1, int paramString2, String paramInt2, int paramInt3, int arg7)
    {
      this.image = paramString1;
      this.url = paramInt1;
      this.retainCount = paramString2;
      this.tag = paramInt2;
      this.width = paramInt3;
      int i;
      this.height = i;
    }

    public boolean available()
    {
      return this.image != null;
    }

    public int release()
    {
      this.retainCount = (-1 + this.retainCount);
      return this.retainCount;
    }

    public int retain()
    {
      this.retainCount = (1 + this.retainCount);
      return this.retainCount;
    }
  }

  public class LoadTask extends AsyncTask<OldVersionImageLoader.ImageCache, Void, Bitmap>
  {
    private OldVersionImageLoader.ImageCache cache;

    public LoadTask()
    {
    }

    private Bitmap getImageByUrl(String paramString)
    {
      Bitmap localBitmap;
      if (paramString == null)
        localBitmap = null;
      do
      {
        return localBitmap;
        localBitmap = getImageByUrlFromDir(paramString);
      }
      while (localBitmap != null);
      return getImageByUrlFromNet(paramString);
    }

    // ERROR //
    private Bitmap getImageByUrlFromDir(String paramString)
    {
      // Byte code:
      //   0: aload_1
      //   1: ifnull +12 -> 13
      //   4: aload_1
      //   5: ldc 30
      //   7: invokevirtual 36	java/lang/String:equalsIgnoreCase	(Ljava/lang/String;)Z
      //   10: ifeq +5 -> 15
      //   13: aconst_null
      //   14: areturn
      //   15: aload_1
      //   16: ldc 38
      //   18: invokevirtual 42	java/lang/String:contains	(Ljava/lang/CharSequence;)Z
      //   21: ifeq -8 -> 13
      //   24: aload_0
      //   25: aload_1
      //   26: invokespecial 46	fm/qingting/framework/utils/OldVersionImageLoader$LoadTask:getImageID	(Ljava/lang/String;)Ljava/lang/String;
      //   29: astore_2
      //   30: aload_2
      //   31: ifnull -18 -> 13
      //   34: new 48	java/lang/StringBuilder
      //   37: dup
      //   38: aload_0
      //   39: getfield 13	fm/qingting/framework/utils/OldVersionImageLoader$LoadTask:this$0	Lfm/qingting/framework/utils/OldVersionImageLoader;
      //   42: invokestatic 54	fm/qingting/framework/utils/OldVersionImageLoader:access$0	(Lfm/qingting/framework/utils/OldVersionImageLoader;)Ljava/lang/String;
      //   45: invokestatic 58	java/lang/String:valueOf	(Ljava/lang/Object;)Ljava/lang/String;
      //   48: invokespecial 61	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
      //   51: aload_2
      //   52: invokevirtual 65	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   55: invokevirtual 69	java/lang/StringBuilder:toString	()Ljava/lang/String;
      //   58: astore_3
      //   59: new 71	java/io/File
      //   62: dup
      //   63: aload_3
      //   64: invokespecial 72	java/io/File:<init>	(Ljava/lang/String;)V
      //   67: astore 4
      //   69: aload 4
      //   71: invokevirtual 76	java/io/File:exists	()Z
      //   74: istore 6
      //   76: iload 6
      //   78: ifeq -65 -> 13
      //   81: new 78	android/graphics/BitmapFactory$Options
      //   84: dup
      //   85: invokespecial 79	android/graphics/BitmapFactory$Options:<init>	()V
      //   88: astore 7
      //   90: aload 7
      //   92: getstatic 85	android/graphics/Bitmap$Config:RGB_565	Landroid/graphics/Bitmap$Config;
      //   95: putfield 88	android/graphics/BitmapFactory$Options:inPreferredConfig	Landroid/graphics/Bitmap$Config;
      //   98: aload 7
      //   100: iconst_1
      //   101: putfield 92	android/graphics/BitmapFactory$Options:inJustDecodeBounds	Z
      //   104: aload_0
      //   105: getfield 94	fm/qingting/framework/utils/OldVersionImageLoader$LoadTask:cache	Lfm/qingting/framework/utils/OldVersionImageLoader$ImageCache;
      //   108: getfield 100	fm/qingting/framework/utils/OldVersionImageLoader$ImageCache:width	I
      //   111: ifeq +13 -> 124
      //   114: aload_0
      //   115: getfield 94	fm/qingting/framework/utils/OldVersionImageLoader$LoadTask:cache	Lfm/qingting/framework/utils/OldVersionImageLoader$ImageCache;
      //   118: getfield 103	fm/qingting/framework/utils/OldVersionImageLoader$ImageCache:height	I
      //   121: ifne +185 -> 306
      //   124: aload 7
      //   126: getfield 106	android/graphics/BitmapFactory$Options:outHeight	I
      //   129: istore 8
      //   131: iconst_0
      //   132: istore 9
      //   134: iload 8
      //   136: sipush 1024
      //   139: if_icmple +8 -> 147
      //   142: sipush 1024
      //   145: istore 9
      //   147: aload 7
      //   149: getfield 109	android/graphics/BitmapFactory$Options:outWidth	I
      //   152: istore 10
      //   154: iconst_0
      //   155: istore 11
      //   157: iload 10
      //   159: sipush 1024
      //   162: if_icmple +8 -> 170
      //   165: sipush 1024
      //   168: istore 11
      //   170: iload 9
      //   172: ifne +40 -> 212
      //   175: iload 11
      //   177: ifne +35 -> 212
      //   180: aload 7
      //   182: iconst_1
      //   183: putfield 112	android/graphics/BitmapFactory$Options:inSampleSize	I
      //   186: aload 7
      //   188: iconst_0
      //   189: putfield 92	android/graphics/BitmapFactory$Options:inJustDecodeBounds	Z
      //   192: aload_3
      //   193: aload 7
      //   195: invokestatic 118	android/graphics/BitmapFactory:decodeFile	(Ljava/lang/String;Landroid/graphics/BitmapFactory$Options;)Landroid/graphics/Bitmap;
      //   198: astore 14
      //   200: aload 14
      //   202: areturn
      //   203: astore 5
      //   205: aload 5
      //   207: invokevirtual 121	java/lang/Exception:printStackTrace	()V
      //   210: aconst_null
      //   211: areturn
      //   212: iload 9
      //   214: ifle +41 -> 255
      //   217: iload 11
      //   219: ifne +36 -> 255
      //   222: aload 7
      //   224: iconst_m1
      //   225: iload 9
      //   227: aload 7
      //   229: getfield 106	android/graphics/BitmapFactory$Options:outHeight	I
      //   232: imul
      //   233: invokestatic 125	fm/qingting/framework/utils/OldVersionImageLoader:computeSampleSize	(Landroid/graphics/BitmapFactory$Options;II)I
      //   236: istore 15
      //   238: aload 7
      //   240: iload 15
      //   242: putfield 112	android/graphics/BitmapFactory$Options:inSampleSize	I
      //   245: aload 7
      //   247: iload 15
      //   249: putfield 112	android/graphics/BitmapFactory$Options:inSampleSize	I
      //   252: goto -66 -> 186
      //   255: iload 11
      //   257: ifle +30 -> 287
      //   260: iload 9
      //   262: ifne +25 -> 287
      //   265: aload 7
      //   267: aload 7
      //   269: iconst_m1
      //   270: iload 11
      //   272: aload 7
      //   274: getfield 109	android/graphics/BitmapFactory$Options:outWidth	I
      //   277: imul
      //   278: invokestatic 125	fm/qingting/framework/utils/OldVersionImageLoader:computeSampleSize	(Landroid/graphics/BitmapFactory$Options;II)I
      //   281: putfield 112	android/graphics/BitmapFactory$Options:inSampleSize	I
      //   284: goto -98 -> 186
      //   287: aload 7
      //   289: aload 7
      //   291: iconst_m1
      //   292: iload 9
      //   294: iload 11
      //   296: imul
      //   297: invokestatic 125	fm/qingting/framework/utils/OldVersionImageLoader:computeSampleSize	(Landroid/graphics/BitmapFactory$Options;II)I
      //   300: putfield 112	android/graphics/BitmapFactory$Options:inSampleSize	I
      //   303: goto -117 -> 186
      //   306: aload 7
      //   308: aload 7
      //   310: iconst_m1
      //   311: aload_0
      //   312: getfield 94	fm/qingting/framework/utils/OldVersionImageLoader$LoadTask:cache	Lfm/qingting/framework/utils/OldVersionImageLoader$ImageCache;
      //   315: getfield 100	fm/qingting/framework/utils/OldVersionImageLoader$ImageCache:width	I
      //   318: aload_0
      //   319: getfield 94	fm/qingting/framework/utils/OldVersionImageLoader$LoadTask:cache	Lfm/qingting/framework/utils/OldVersionImageLoader$ImageCache;
      //   322: getfield 103	fm/qingting/framework/utils/OldVersionImageLoader$ImageCache:height	I
      //   325: imul
      //   326: invokestatic 125	fm/qingting/framework/utils/OldVersionImageLoader:computeSampleSize	(Landroid/graphics/BitmapFactory$Options;II)I
      //   329: putfield 112	android/graphics/BitmapFactory$Options:inSampleSize	I
      //   332: goto -146 -> 186
      //   335: astore 13
      //   337: aload 13
      //   339: invokevirtual 121	java/lang/Exception:printStackTrace	()V
      //   342: aconst_null
      //   343: areturn
      //   344: astore 12
      //   346: aconst_null
      //   347: areturn
      //
      // Exception table:
      //   from	to	target	type
      //   69	76	203	java/lang/Exception
      //   192	200	335	java/lang/Exception
      //   192	200	344	java/lang/OutOfMemoryError
    }

    // ERROR //
    private Bitmap getImageByUrlFromNet(String paramString)
    {
      // Byte code:
      //   0: aload_1
      //   1: ifnonnull +5 -> 6
      //   4: aconst_null
      //   5: areturn
      //   6: new 129	fm/qingting/framework/utils/HTTPStream
      //   9: dup
      //   10: invokespecial 130	fm/qingting/framework/utils/HTTPStream:<init>	()V
      //   13: aload_1
      //   14: invokevirtual 134	fm/qingting/framework/utils/HTTPStream:getStream	(Ljava/lang/String;)Ljava/io/InputStream;
      //   17: astore_2
      //   18: aload_2
      //   19: ifnull -15 -> 4
      //   22: aload_0
      //   23: aload_2
      //   24: invokevirtual 138	fm/qingting/framework/utils/OldVersionImageLoader$LoadTask:InputStreamToByte	(Ljava/io/InputStream;)[B
      //   27: astore 15
      //   29: aload 15
      //   31: astore 5
      //   33: new 78	android/graphics/BitmapFactory$Options
      //   36: dup
      //   37: invokespecial 79	android/graphics/BitmapFactory$Options:<init>	()V
      //   40: astore 6
      //   42: aload 6
      //   44: getstatic 85	android/graphics/Bitmap$Config:RGB_565	Landroid/graphics/Bitmap$Config;
      //   47: putfield 88	android/graphics/BitmapFactory$Options:inPreferredConfig	Landroid/graphics/Bitmap$Config;
      //   50: aload 6
      //   52: iconst_1
      //   53: putfield 92	android/graphics/BitmapFactory$Options:inJustDecodeBounds	Z
      //   56: aload_0
      //   57: getfield 94	fm/qingting/framework/utils/OldVersionImageLoader$LoadTask:cache	Lfm/qingting/framework/utils/OldVersionImageLoader$ImageCache;
      //   60: getfield 100	fm/qingting/framework/utils/OldVersionImageLoader$ImageCache:width	I
      //   63: ifeq +13 -> 76
      //   66: aload_0
      //   67: getfield 94	fm/qingting/framework/utils/OldVersionImageLoader$LoadTask:cache	Lfm/qingting/framework/utils/OldVersionImageLoader$ImageCache;
      //   70: getfield 103	fm/qingting/framework/utils/OldVersionImageLoader$ImageCache:height	I
      //   73: ifne +197 -> 270
      //   76: aload 6
      //   78: getfield 106	android/graphics/BitmapFactory$Options:outHeight	I
      //   81: istore 7
      //   83: iconst_0
      //   84: istore 8
      //   86: iload 7
      //   88: sipush 1024
      //   91: if_icmple +8 -> 99
      //   94: sipush 1024
      //   97: istore 8
      //   99: aload 6
      //   101: getfield 109	android/graphics/BitmapFactory$Options:outWidth	I
      //   104: istore 9
      //   106: iconst_0
      //   107: istore 10
      //   109: iload 9
      //   111: sipush 1024
      //   114: if_icmple +8 -> 122
      //   117: sipush 1024
      //   120: istore 10
      //   122: iload 8
      //   124: ifne +52 -> 176
      //   127: iload 10
      //   129: ifne +47 -> 176
      //   132: aload 6
      //   134: iconst_1
      //   135: putfield 112	android/graphics/BitmapFactory$Options:inSampleSize	I
      //   138: aload 6
      //   140: iconst_0
      //   141: putfield 92	android/graphics/BitmapFactory$Options:inJustDecodeBounds	Z
      //   144: aload 5
      //   146: iconst_0
      //   147: aload 5
      //   149: arraylength
      //   150: aload 6
      //   152: invokestatic 142	android/graphics/BitmapFactory:decodeByteArray	([BIILandroid/graphics/BitmapFactory$Options;)Landroid/graphics/Bitmap;
      //   155: astore 13
      //   157: aload 13
      //   159: areturn
      //   160: astore 4
      //   162: aload 4
      //   164: invokevirtual 143	java/io/IOException:printStackTrace	()V
      //   167: aconst_null
      //   168: astore 5
      //   170: goto -137 -> 33
      //   173: astore_3
      //   174: aconst_null
      //   175: areturn
      //   176: iload 8
      //   178: ifle +41 -> 219
      //   181: iload 10
      //   183: ifne +36 -> 219
      //   186: aload 6
      //   188: iconst_m1
      //   189: iload 8
      //   191: aload 6
      //   193: getfield 106	android/graphics/BitmapFactory$Options:outHeight	I
      //   196: imul
      //   197: invokestatic 125	fm/qingting/framework/utils/OldVersionImageLoader:computeSampleSize	(Landroid/graphics/BitmapFactory$Options;II)I
      //   200: istore 14
      //   202: aload 6
      //   204: iload 14
      //   206: putfield 112	android/graphics/BitmapFactory$Options:inSampleSize	I
      //   209: aload 6
      //   211: iload 14
      //   213: putfield 112	android/graphics/BitmapFactory$Options:inSampleSize	I
      //   216: goto -78 -> 138
      //   219: iload 10
      //   221: ifle +30 -> 251
      //   224: iload 8
      //   226: ifne +25 -> 251
      //   229: aload 6
      //   231: aload 6
      //   233: iconst_m1
      //   234: iload 10
      //   236: aload 6
      //   238: getfield 109	android/graphics/BitmapFactory$Options:outWidth	I
      //   241: imul
      //   242: invokestatic 125	fm/qingting/framework/utils/OldVersionImageLoader:computeSampleSize	(Landroid/graphics/BitmapFactory$Options;II)I
      //   245: putfield 112	android/graphics/BitmapFactory$Options:inSampleSize	I
      //   248: goto -110 -> 138
      //   251: aload 6
      //   253: aload 6
      //   255: iconst_m1
      //   256: iload 8
      //   258: iload 10
      //   260: imul
      //   261: invokestatic 125	fm/qingting/framework/utils/OldVersionImageLoader:computeSampleSize	(Landroid/graphics/BitmapFactory$Options;II)I
      //   264: putfield 112	android/graphics/BitmapFactory$Options:inSampleSize	I
      //   267: goto -129 -> 138
      //   270: aload 6
      //   272: aload 6
      //   274: iconst_m1
      //   275: aload_0
      //   276: getfield 94	fm/qingting/framework/utils/OldVersionImageLoader$LoadTask:cache	Lfm/qingting/framework/utils/OldVersionImageLoader$ImageCache;
      //   279: getfield 100	fm/qingting/framework/utils/OldVersionImageLoader$ImageCache:width	I
      //   282: aload_0
      //   283: getfield 94	fm/qingting/framework/utils/OldVersionImageLoader$LoadTask:cache	Lfm/qingting/framework/utils/OldVersionImageLoader$ImageCache;
      //   286: getfield 103	fm/qingting/framework/utils/OldVersionImageLoader$ImageCache:height	I
      //   289: imul
      //   290: invokestatic 125	fm/qingting/framework/utils/OldVersionImageLoader:computeSampleSize	(Landroid/graphics/BitmapFactory$Options;II)I
      //   293: putfield 112	android/graphics/BitmapFactory$Options:inSampleSize	I
      //   296: goto -158 -> 138
      //   299: astore 12
      //   301: aconst_null
      //   302: areturn
      //   303: astore 11
      //   305: aconst_null
      //   306: areturn
      //
      // Exception table:
      //   from	to	target	type
      //   22	29	160	java/io/IOException
      //   22	29	173	java/lang/OutOfMemoryError
      //   144	157	299	java/lang/OutOfMemoryError
      //   144	157	303	java/lang/Exception
    }

    private byte[] getImageFromDBByUrl(String paramString)
    {
      if (paramString == null);
      Result localResult;
      do
      {
        return null;
        HashMap localHashMap = new HashMap();
        localHashMap.put("url", paramString);
        localResult = DataManager.getInstance().getData("getdb_image_info", null, localHashMap).getResult();
      }
      while (!localResult.getSuccess());
      return (byte[])localResult.getData();
    }

    private String getImageID(String paramString)
    {
      if (paramString == null);
      String[] arrayOfString;
      int i;
      do
      {
        do
        {
          return null;
          arrayOfString = paramString.split("/");
        }
        while (arrayOfString == null);
        i = -1 + arrayOfString.length;
      }
      while (i < 0);
      return arrayOfString[i];
    }

    // ERROR //
    private void saveImageByUrlToDir(String paramString, Bitmap paramBitmap)
    {
      // Byte code:
      //   0: aload_1
      //   1: ifnull +7 -> 8
      //   4: aload_2
      //   5: ifnonnull +4 -> 9
      //   8: return
      //   9: aload_1
      //   10: ldc 38
      //   12: invokevirtual 42	java/lang/String:contains	(Ljava/lang/CharSequence;)Z
      //   15: ifeq -7 -> 8
      //   18: aload_0
      //   19: aload_1
      //   20: invokespecial 46	fm/qingting/framework/utils/OldVersionImageLoader$LoadTask:getImageID	(Ljava/lang/String;)Ljava/lang/String;
      //   23: astore_3
      //   24: aload_3
      //   25: ifnull -17 -> 8
      //   28: new 71	java/io/File
      //   31: dup
      //   32: aload_0
      //   33: getfield 13	fm/qingting/framework/utils/OldVersionImageLoader$LoadTask:this$0	Lfm/qingting/framework/utils/OldVersionImageLoader;
      //   36: invokestatic 54	fm/qingting/framework/utils/OldVersionImageLoader:access$0	(Lfm/qingting/framework/utils/OldVersionImageLoader;)Ljava/lang/String;
      //   39: invokespecial 72	java/io/File:<init>	(Ljava/lang/String;)V
      //   42: astore 4
      //   44: aload 4
      //   46: invokevirtual 76	java/io/File:exists	()Z
      //   49: ifne +9 -> 58
      //   52: aload 4
      //   54: invokevirtual 195	java/io/File:mkdir	()Z
      //   57: pop
      //   58: new 71	java/io/File
      //   61: dup
      //   62: new 48	java/lang/StringBuilder
      //   65: dup
      //   66: aload_0
      //   67: getfield 13	fm/qingting/framework/utils/OldVersionImageLoader$LoadTask:this$0	Lfm/qingting/framework/utils/OldVersionImageLoader;
      //   70: invokestatic 54	fm/qingting/framework/utils/OldVersionImageLoader:access$0	(Lfm/qingting/framework/utils/OldVersionImageLoader;)Ljava/lang/String;
      //   73: invokestatic 58	java/lang/String:valueOf	(Ljava/lang/Object;)Ljava/lang/String;
      //   76: invokespecial 61	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
      //   79: aload_3
      //   80: invokevirtual 65	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   83: invokevirtual 69	java/lang/StringBuilder:toString	()Ljava/lang/String;
      //   86: invokespecial 72	java/io/File:<init>	(Ljava/lang/String;)V
      //   89: astore 5
      //   91: aload 5
      //   93: invokevirtual 198	java/io/File:createNewFile	()Z
      //   96: istore 7
      //   98: iload 7
      //   100: ifeq -92 -> 8
      //   103: new 200	java/io/FileOutputStream
      //   106: dup
      //   107: aload 5
      //   109: invokespecial 203	java/io/FileOutputStream:<init>	(Ljava/io/File;)V
      //   112: astore 8
      //   114: aload_2
      //   115: getstatic 209	android/graphics/Bitmap$CompressFormat:PNG	Landroid/graphics/Bitmap$CompressFormat;
      //   118: bipush 100
      //   120: aload 8
      //   122: invokevirtual 215	android/graphics/Bitmap:compress	(Landroid/graphics/Bitmap$CompressFormat;ILjava/io/OutputStream;)Z
      //   125: pop
      //   126: aload 8
      //   128: invokevirtual 218	java/io/FileOutputStream:flush	()V
      //   131: aload 8
      //   133: invokevirtual 221	java/io/FileOutputStream:close	()V
      //   136: return
      //   137: astore 11
      //   139: aload 11
      //   141: invokevirtual 143	java/io/IOException:printStackTrace	()V
      //   144: return
      //   145: astore 6
      //   147: aload 6
      //   149: invokevirtual 121	java/lang/Exception:printStackTrace	()V
      //   152: return
      //   153: astore 12
      //   155: aload 12
      //   157: invokevirtual 121	java/lang/Exception:printStackTrace	()V
      //   160: return
      //   161: astore 10
      //   163: aload 10
      //   165: invokevirtual 143	java/io/IOException:printStackTrace	()V
      //   168: goto -37 -> 131
      //
      // Exception table:
      //   from	to	target	type
      //   131	136	137	java/io/IOException
      //   91	98	145	java/lang/Exception
      //   103	114	153	java/lang/Exception
      //   126	131	161	java/io/IOException
    }

    public byte[] InputStreamToByte(InputStream paramInputStream)
      throws IOException
    {
      ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream();
      while (true)
      {
        int i = paramInputStream.read();
        if (i == -1)
        {
          byte[] arrayOfByte = localByteArrayOutputStream.toByteArray();
          localByteArrayOutputStream.close();
          return arrayOfByte;
        }
        try
        {
          localByteArrayOutputStream.write(i);
        }
        catch (OutOfMemoryError localOutOfMemoryError)
        {
          System.gc();
        }
      }
    }

    protected Bitmap doInBackground(OldVersionImageLoader.ImageCache[] paramArrayOfImageCache)
    {
      Bitmap localBitmap1 = null;
      Bitmap localBitmap2;
      try
      {
        this.cache = paramArrayOfImageCache[0];
        String str = this.cache.url;
        localBitmap1 = null;
        localBitmap2 = null;
        if (str != null)
        {
          if (this.cache.url.equalsIgnoreCase(""))
            return null;
          localBitmap1 = getImageByUrl(this.cache.url);
          localBitmap2 = null;
          if (localBitmap1 != null)
          {
            saveImageByUrlToDir(this.cache.url, localBitmap1);
            return localBitmap1;
          }
        }
      }
      catch (Exception localException)
      {
        localBitmap2 = localBitmap1;
      }
      return localBitmap2;
    }

    protected void onPostExecute(Bitmap paramBitmap)
    {
      if (paramBitmap != null)
      {
        OldVersionImageLoader.this.loadCompleteHandler(this.cache, paramBitmap);
        return;
      }
      if (this.cache.url == null);
      while (true)
      {
        OldVersionImageLoader.this.loadFailedHandler(this.cache);
        return;
      }
    }
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.framework.utils.OldVersionImageLoader
 * JD-Core Version:    0.6.2
 */