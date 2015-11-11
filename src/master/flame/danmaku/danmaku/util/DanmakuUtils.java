package master.flame.danmaku.danmaku.util;

import master.flame.danmaku.danmaku.model.BaseDanmaku;
import master.flame.danmaku.danmaku.model.IDisplayer;
import master.flame.danmaku.danmaku.model.android.AndroidDisplayer;
import master.flame.danmaku.danmaku.model.android.DrawingCache;
import master.flame.danmaku.danmaku.model.android.DrawingCacheHolder;

public class DanmakuUtils
{
  public static DrawingCache buildDanmakuDrawingCache(BaseDanmaku paramBaseDanmaku, IDisplayer paramIDisplayer, DrawingCache paramDrawingCache)
  {
    if (paramDrawingCache == null)
      paramDrawingCache = new DrawingCache();
    int i = (int)Math.ceil(paramBaseDanmaku.paintWidth);
    int j = (int)Math.ceil(paramBaseDanmaku.paintHeight);
    if (paramBaseDanmaku.bgColor != 0)
    {
      i += 48;
      j += 24;
      if (paramBaseDanmaku.drawableLeftResid != 0)
        i += 64;
    }
    paramDrawingCache.build(i, j, paramIDisplayer.getDensityDpi(), false);
    DrawingCacheHolder localDrawingCacheHolder = paramDrawingCache.get();
    if (localDrawingCacheHolder != null)
    {
      AndroidDisplayer.drawDanmaku(paramIDisplayer.getContext(), paramBaseDanmaku, localDrawingCacheHolder.canvas, 0.0F, 0.0F, false);
      if (paramIDisplayer.isHardwareAccelerated())
        localDrawingCacheHolder.splitWith(paramIDisplayer.getWidth(), paramIDisplayer.getHeight(), paramIDisplayer.getMaximumCacheWidth(), paramIDisplayer.getMaximumCacheHeight());
    }
    return paramDrawingCache;
  }

  private static boolean checkHit(int paramInt1, int paramInt2, float[] paramArrayOfFloat1, float[] paramArrayOfFloat2)
  {
    int i = 1;
    if (paramInt1 != paramInt2);
    do
    {
      return false;
      if (paramInt1 == i)
      {
        if (paramArrayOfFloat2[0] < paramArrayOfFloat1[2]);
        while (true)
        {
          return i;
          i = 0;
        }
      }
    }
    while (paramInt1 != 6);
    if (paramArrayOfFloat2[2] > paramArrayOfFloat1[0]);
    while (true)
    {
      return i;
      i = 0;
    }
  }

  private static boolean checkHitAtTime(IDisplayer paramIDisplayer, BaseDanmaku paramBaseDanmaku1, BaseDanmaku paramBaseDanmaku2, long paramLong)
  {
    float[] arrayOfFloat1 = paramBaseDanmaku1.getRectAtTime(paramIDisplayer, paramLong);
    float[] arrayOfFloat2 = paramBaseDanmaku2.getRectAtTime(paramIDisplayer, paramLong);
    if ((arrayOfFloat1 == null) || (arrayOfFloat2 == null))
      return false;
    return checkHit(paramBaseDanmaku1.getType(), paramBaseDanmaku2.getType(), arrayOfFloat1, arrayOfFloat2);
  }

  public static final int compare(BaseDanmaku paramBaseDanmaku1, BaseDanmaku paramBaseDanmaku2)
  {
    int i = -1;
    if (paramBaseDanmaku1 == paramBaseDanmaku2)
      i = 0;
    int i1;
    do
    {
      int n;
      do
      {
        int k;
        do
        {
          int j;
          do
          {
            long l;
            do
            {
              do
                return i;
              while (paramBaseDanmaku1 == null);
              if (paramBaseDanmaku2 == null)
                return 1;
              l = paramBaseDanmaku1.time - paramBaseDanmaku2.time;
              if (l > 0L)
                return 1;
            }
            while (l < 0L);
            j = paramBaseDanmaku1.index - paramBaseDanmaku2.index;
            if (j > 0)
              return 1;
          }
          while (j < 0);
          k = paramBaseDanmaku1.getType() - paramBaseDanmaku2.getType();
          if (k > 0)
            return 1;
        }
        while ((k < 0) || (paramBaseDanmaku1.text == null));
        if (paramBaseDanmaku2.text == null)
          return 1;
        int m = paramBaseDanmaku1.text.compareTo(paramBaseDanmaku2.text);
        if (m != 0)
          return m;
        n = paramBaseDanmaku1.textColor - paramBaseDanmaku2.textColor;
        if (n == 0)
          break;
      }
      while (n < 0);
      return 1;
      i1 = paramBaseDanmaku1.index - paramBaseDanmaku2.index;
      if (i1 == 0)
        break;
    }
    while (i1 < 0);
    return 1;
    return paramBaseDanmaku1.hashCode() - paramBaseDanmaku1.hashCode();
  }

  public static int getCacheSize(int paramInt1, int paramInt2)
  {
    return 4 * (paramInt1 * paramInt2);
  }

  public static final boolean isDuplicate(BaseDanmaku paramBaseDanmaku1, BaseDanmaku paramBaseDanmaku2)
  {
    if (paramBaseDanmaku1 == paramBaseDanmaku2);
    do
    {
      return false;
      if (paramBaseDanmaku1.text == paramBaseDanmaku2.text)
        return true;
    }
    while ((paramBaseDanmaku1.text == null) || (!paramBaseDanmaku1.text.equals(paramBaseDanmaku2.text)));
    return true;
  }

  public static final boolean isOverSize(IDisplayer paramIDisplayer, BaseDanmaku paramBaseDanmaku)
  {
    return (paramIDisplayer.isHardwareAccelerated()) && ((paramBaseDanmaku.paintWidth > paramIDisplayer.getMaximumCacheWidth()) || (paramBaseDanmaku.paintHeight > paramIDisplayer.getMaximumCacheHeight()));
  }

  public static boolean willHitInDuration(IDisplayer paramIDisplayer, BaseDanmaku paramBaseDanmaku1, BaseDanmaku paramBaseDanmaku2, long paramLong1, long paramLong2)
  {
    int i = paramBaseDanmaku1.getType();
    if (i != paramBaseDanmaku2.getType());
    do
    {
      long l;
      do
      {
        do
          return false;
        while (paramBaseDanmaku1.isOutside());
        l = paramBaseDanmaku2.time - paramBaseDanmaku1.time;
        if (l < 0L)
          return true;
      }
      while ((Math.abs(l) >= paramLong1) || (paramBaseDanmaku1.isTimeOut()) || (paramBaseDanmaku2.isTimeOut()));
      if ((i == 5) || (i == 4))
        return true;
    }
    while ((!checkHitAtTime(paramIDisplayer, paramBaseDanmaku1, paramBaseDanmaku2, paramLong2)) && (!checkHitAtTime(paramIDisplayer, paramBaseDanmaku1, paramBaseDanmaku2, paramBaseDanmaku1.time + paramBaseDanmaku1.getDuration())));
    return true;
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     master.flame.danmaku.danmaku.util.DanmakuUtils
 * JD-Core Version:    0.6.2
 */