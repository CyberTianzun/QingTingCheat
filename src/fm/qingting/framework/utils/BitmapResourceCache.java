package fm.qingting.framework.utils;

import android.annotation.TargetApi;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.os.Build.VERSION;
import android.util.SparseArray;
import java.util.HashSet;
import java.util.Iterator;

public enum BitmapResourceCache
{
  private SparseArray<BitmapCache> caches = new SparseArray();
  private SparseArray<HashSet<Integer>> usingResHolder = new SparseArray();

  static
  {
    BitmapResourceCache[] arrayOfBitmapResourceCache = new BitmapResourceCache[1];
    arrayOfBitmapResourceCache[0] = INSTANCE;
  }

  public static BitmapResourceCache getInstance()
  {
    return INSTANCE;
  }

  private int[] hash2array(HashSet<Integer> paramHashSet)
  {
    int[] arrayOfInt = new int[paramHashSet.size()];
    Iterator localIterator = paramHashSet.iterator();
    for (int i = 0; ; i++)
    {
      if (!localIterator.hasNext())
        return arrayOfInt;
      arrayOfInt[i] = ((Integer)localIterator.next()).intValue();
    }
  }

  private void releaseCaches(int[] paramArrayOfInt)
  {
    if (paramArrayOfInt == null)
      return;
    int i = paramArrayOfInt.length;
    int j = 0;
    label10: int k;
    BitmapCache localBitmapCache;
    if (j < i)
    {
      k = paramArrayOfInt[j];
      localBitmapCache = (BitmapCache)this.caches.get(k);
      if (localBitmapCache != null)
        break label45;
    }
    while (true)
    {
      j++;
      break label10;
      break;
      label45: localBitmapCache.retain = (-1 + localBitmapCache.retain);
      if (localBitmapCache.retain <= 0)
      {
        if ((localBitmapCache.cache != null) && (!localBitmapCache.cache.isRecycled()))
          localBitmapCache.cache.recycle();
        localBitmapCache.cache = null;
        this.caches.remove(k);
      }
    }
  }

  // ERROR //
  private void retainCaches(Resources paramResources, int[] paramArrayOfInt)
  {
    // Byte code:
    //   0: aload_2
    //   1: arraylength
    //   2: istore_3
    //   3: iconst_0
    //   4: istore 4
    //   6: iload 4
    //   8: iload_3
    //   9: if_icmplt +4 -> 13
    //   12: return
    //   13: aload_2
    //   14: iload 4
    //   16: iaload
    //   17: istore 5
    //   19: aload_0
    //   20: getfield 32	fm/qingting/framework/utils/BitmapResourceCache:caches	Landroid/util/SparseArray;
    //   23: iload 5
    //   25: invokevirtual 69	android/util/SparseArray:get	(I)Ljava/lang/Object;
    //   28: checkcast 71	fm/qingting/framework/utils/BitmapCache
    //   31: astore 6
    //   33: aload 6
    //   35: ifnonnull +62 -> 97
    //   38: aconst_null
    //   39: astore 7
    //   41: aload_1
    //   42: iload 5
    //   44: new 99	android/util/TypedValue
    //   47: dup
    //   48: invokespecial 100	android/util/TypedValue:<init>	()V
    //   51: invokevirtual 106	android/content/res/Resources:openRawResource	(ILandroid/util/TypedValue;)Ljava/io/InputStream;
    //   54: astore 7
    //   56: new 71	fm/qingting/framework/utils/BitmapCache
    //   59: dup
    //   60: aload 7
    //   62: invokestatic 112	android/graphics/BitmapFactory:decodeStream	(Ljava/io/InputStream;)Landroid/graphics/Bitmap;
    //   65: iload 5
    //   67: invokespecial 115	fm/qingting/framework/utils/BitmapCache:<init>	(Landroid/graphics/Bitmap;I)V
    //   70: astore 11
    //   72: aload_0
    //   73: getfield 32	fm/qingting/framework/utils/BitmapResourceCache:caches	Landroid/util/SparseArray;
    //   76: iload 5
    //   78: aload 11
    //   80: invokevirtual 119	android/util/SparseArray:put	(ILjava/lang/Object;)V
    //   83: aload 7
    //   85: ifnull +131 -> 216
    //   88: aload 7
    //   90: invokevirtual 124	java/io/InputStream:close	()V
    //   93: aload 11
    //   95: astore 6
    //   97: aload 6
    //   99: ifnull +15 -> 114
    //   102: aload 6
    //   104: iconst_1
    //   105: aload 6
    //   107: getfield 75	fm/qingting/framework/utils/BitmapCache:retain	I
    //   110: iadd
    //   111: putfield 75	fm/qingting/framework/utils/BitmapCache:retain	I
    //   114: iinc 4 1
    //   117: goto -111 -> 6
    //   120: astore 10
    //   122: aload 6
    //   124: astore 11
    //   126: aload 10
    //   128: invokevirtual 127	java/lang/OutOfMemoryError:printStackTrace	()V
    //   131: new 71	fm/qingting/framework/utils/BitmapCache
    //   134: dup
    //   135: iconst_1
    //   136: iconst_1
    //   137: getstatic 133	android/graphics/Bitmap$Config:RGB_565	Landroid/graphics/Bitmap$Config;
    //   140: invokestatic 137	android/graphics/Bitmap:createBitmap	(IILandroid/graphics/Bitmap$Config;)Landroid/graphics/Bitmap;
    //   143: iload 5
    //   145: invokespecial 115	fm/qingting/framework/utils/BitmapCache:<init>	(Landroid/graphics/Bitmap;I)V
    //   148: astore 6
    //   150: aload_0
    //   151: getfield 32	fm/qingting/framework/utils/BitmapResourceCache:caches	Landroid/util/SparseArray;
    //   154: iload 5
    //   156: aload 6
    //   158: invokevirtual 119	android/util/SparseArray:put	(ILjava/lang/Object;)V
    //   161: aload 7
    //   163: ifnull -66 -> 97
    //   166: aload 7
    //   168: invokevirtual 124	java/io/InputStream:close	()V
    //   171: goto -74 -> 97
    //   174: astore 13
    //   176: aload 13
    //   178: invokevirtual 138	java/io/IOException:printStackTrace	()V
    //   181: goto -84 -> 97
    //   184: astore 8
    //   186: aload 7
    //   188: ifnull +8 -> 196
    //   191: aload 7
    //   193: invokevirtual 124	java/io/InputStream:close	()V
    //   196: aload 8
    //   198: athrow
    //   199: astore 9
    //   201: aload 9
    //   203: invokevirtual 138	java/io/IOException:printStackTrace	()V
    //   206: goto -10 -> 196
    //   209: astore 14
    //   211: aload 14
    //   213: invokevirtual 138	java/io/IOException:printStackTrace	()V
    //   216: aload 11
    //   218: astore 6
    //   220: goto -123 -> 97
    //   223: astore 8
    //   225: aload 11
    //   227: pop
    //   228: goto -42 -> 186
    //   231: astore 10
    //   233: goto -107 -> 126
    //
    // Exception table:
    //   from	to	target	type
    //   41	72	120	java/lang/OutOfMemoryError
    //   166	171	174	java/io/IOException
    //   41	72	184	finally
    //   150	161	184	finally
    //   191	196	199	java/io/IOException
    //   88	93	209	java/io/IOException
    //   72	83	223	finally
    //   126	150	223	finally
    //   72	83	231	java/lang/OutOfMemoryError
  }

  @TargetApi(11)
  public void clearAllResourceCaches()
  {
    int i = 0;
    if (i >= this.caches.size())
    {
      if (Build.VERSION.SDK_INT < 11)
        this.caches.clear();
      this.usingResHolder.clear();
      return;
    }
    BitmapCache localBitmapCache = (BitmapCache)this.caches.valueAt(i);
    if (localBitmapCache == null);
    while (true)
    {
      i++;
      break;
      if ((localBitmapCache.cache != null) && (!localBitmapCache.cache.isRecycled()))
        localBitmapCache.cache.recycle();
      localBitmapCache.cache = null;
      if (Build.VERSION.SDK_INT >= 11)
        this.caches.removeAt(i);
    }
  }

  public void clearResourceCacheOfOne(Object paramObject, int paramInt)
  {
    int i = paramObject.hashCode();
    HashSet localHashSet = (HashSet)this.usingResHolder.get(i);
    this.usingResHolder.remove(i);
    if (localHashSet != null)
      releaseCaches(hash2array(localHashSet));
  }

  public Bitmap getResourceCache(Resources paramResources, Object paramObject, int paramInt)
  {
    HashSet localHashSet = (HashSet)this.usingResHolder.get(paramObject.hashCode());
    if (localHashSet == null)
    {
      localHashSet = new HashSet();
      this.usingResHolder.put(paramObject.hashCode(), localHashSet);
    }
    if (!localHashSet.contains(Integer.valueOf(paramInt)))
    {
      localHashSet.add(Integer.valueOf(paramInt));
      retainCaches(paramResources, new int[] { paramInt });
    }
    if (this.caches.get(paramInt) == null)
      retainCaches(paramResources, new int[] { paramInt });
    return ((BitmapCache)this.caches.get(paramInt)).cache;
  }

  public Bitmap getResourceCacheByParent(Resources paramResources, int paramInt1, int paramInt2)
  {
    HashSet localHashSet = (HashSet)this.usingResHolder.get(paramInt1);
    if (localHashSet == null)
    {
      localHashSet = new HashSet();
      this.usingResHolder.put(paramInt1, localHashSet);
    }
    if (!localHashSet.contains(Integer.valueOf(paramInt2)))
    {
      localHashSet.add(Integer.valueOf(paramInt2));
      retainCaches(paramResources, new int[] { paramInt2 });
    }
    if (this.caches.get(paramInt2) == null)
      retainCaches(paramResources, new int[] { paramInt2 });
    return ((BitmapCache)this.caches.get(paramInt2)).cache;
  }

  public void saveResourceCache(Object paramObject)
  {
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.framework.utils.BitmapResourceCache
 * JD-Core Version:    0.6.2
 */