package master.flame.danmaku.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;
import master.flame.danmaku.danmaku.model.BaseDanmaku;
import master.flame.danmaku.danmaku.model.DanmakuTimer;
import master.flame.danmaku.danmaku.model.IDanmakuIterator;
import master.flame.danmaku.danmaku.model.IDanmakus;
import master.flame.danmaku.danmaku.model.android.Danmakus;

public class DanmakuFilters
{
  public static final String TAG_DUPLICATE_FILTER = "1017_Filter";
  public static final String TAG_ELAPSED_TIME_FILTER = "1012_Filter";
  public static final String TAG_GUEST_FILTER = "1016_Filter";
  public static final String TAG_QUANTITY_DANMAKU_FILTER = "1011_Filter";
  public static final String TAG_TEXT_COLOR_DANMAKU_FILTER = "1013_Filter";
  public static final String TAG_TYPE_DANMAKU_FILTER = "1010_Filter";
  public static final String TAG_USER_HASH_FILTER = "1015_Filter";
  public static final String TAG_USER_ID_FILTER = "1014_Filter";
  private static final Map<String, IDanmakuFilter<?>> filters = Collections.synchronizedSortedMap(new TreeMap());
  private static DanmakuFilters instance = null;
  public final Exception filterException = new Exception("not suuport this filter tag");
  IDanmakuFilter<?>[] mFilterArray = new IDanmakuFilter[0];

  public static DanmakuFilters getDefault()
  {
    if (instance == null)
      instance = new DanmakuFilters();
    return instance;
  }

  private void throwFilterException()
  {
    try
    {
      throw this.filterException;
    }
    catch (Exception localException)
    {
    }
  }

  public void clear()
  {
    for (IDanmakuFilter localIDanmakuFilter : this.mFilterArray)
      if (localIDanmakuFilter != null)
        localIDanmakuFilter.clear();
  }

  public boolean filter(BaseDanmaku paramBaseDanmaku, int paramInt1, int paramInt2, DanmakuTimer paramDanmakuTimer, boolean paramBoolean)
  {
    for (IDanmakuFilter localIDanmakuFilter : this.mFilterArray)
      if ((localIDanmakuFilter != null) && (localIDanmakuFilter.filter(paramBaseDanmaku, paramInt1, paramInt2, paramDanmakuTimer, paramBoolean)))
        return true;
    return false;
  }

  public IDanmakuFilter<?> get(String paramString)
  {
    IDanmakuFilter localIDanmakuFilter = (IDanmakuFilter)filters.get(paramString);
    if (localIDanmakuFilter == null)
      localIDanmakuFilter = registerFilter(paramString);
    return localIDanmakuFilter;
  }

  public IDanmakuFilter<?> registerFilter(String paramString)
  {
    if (paramString == null)
    {
      throwFilterException();
      return null;
    }
    IDanmakuFilter localIDanmakuFilter = (IDanmakuFilter)filters.get(paramString);
    Object localObject;
    if (localIDanmakuFilter == null)
      if ("1010_Filter".equals(paramString))
        localObject = new TypeDanmakuFilter();
    while (true)
      if (localObject == null)
      {
        throwFilterException();
        return null;
        if ("1011_Filter".equals(paramString))
          localObject = new QuantityDanmakuFilter();
        else if ("1012_Filter".equals(paramString))
          localObject = new ElapsedTimeFilter();
        else if ("1013_Filter".equals(paramString))
          localObject = new TextColorFilter();
        else if ("1014_Filter".equals(paramString))
          localObject = new UserIdFilter();
        else if ("1015_Filter".equals(paramString))
          localObject = new UserHashFilter();
        else if ("1016_Filter".equals(paramString))
          localObject = new GuestFilter();
        else if ("1017_Filter".equals(paramString))
          localObject = new DuplicateMergingFilter();
      }
      else
      {
        ((IDanmakuFilter)localObject).setData(null);
        filters.put(paramString, localObject);
        this.mFilterArray = ((IDanmakuFilter[])filters.values().toArray(this.mFilterArray));
        return localObject;
        localObject = localIDanmakuFilter;
      }
  }

  public void release()
  {
    clear();
    filters.clear();
    this.mFilterArray = new IDanmakuFilter[0];
  }

  public void reset()
  {
    for (IDanmakuFilter localIDanmakuFilter : this.mFilterArray)
      if (localIDanmakuFilter != null)
        localIDanmakuFilter.reset();
  }

  public void unregisterFilter(String paramString)
  {
    IDanmakuFilter localIDanmakuFilter = (IDanmakuFilter)filters.remove(paramString);
    if (localIDanmakuFilter != null)
    {
      localIDanmakuFilter.clear();
      this.mFilterArray = ((IDanmakuFilter[])filters.values().toArray(this.mFilterArray));
    }
  }

  public static abstract class BaseDanmakuFilter<T>
    implements DanmakuFilters.IDanmakuFilter<T>
  {
    public void clear()
    {
    }
  }

  public static class DuplicateMergingFilter extends DanmakuFilters.BaseDanmakuFilter<Void>
  {
    protected final IDanmakus blockedDanmakus = new Danmakus(4);
    protected final LinkedHashMap<String, BaseDanmaku> currentDanmakus = new LinkedHashMap();
    private final IDanmakus passedDanmakus = new Danmakus(4);

    private void removeTimeoutDanmakus(LinkedHashMap<String, BaseDanmaku> paramLinkedHashMap, int paramInt)
    {
      Iterator localIterator = paramLinkedHashMap.entrySet().iterator();
      long l = System.currentTimeMillis();
      while (true)
      {
        if (localIterator.hasNext());
        try
        {
          if (((BaseDanmaku)((Map.Entry)localIterator.next()).getValue()).isTimeOut())
          {
            localIterator.remove();
            if (System.currentTimeMillis() - l <= paramInt);
          }
          else;
        }
        catch (Exception localException)
        {
        }
      }
    }

    private final void removeTimeoutDanmakus(IDanmakus paramIDanmakus, long paramLong)
    {
      IDanmakuIterator localIDanmakuIterator = paramIDanmakus.iterator();
      long l = System.currentTimeMillis();
      while (true)
      {
        if (localIDanmakuIterator.hasNext());
        try
        {
          if (localIDanmakuIterator.next().isTimeOut())
          {
            localIDanmakuIterator.remove();
            if (System.currentTimeMillis() - l <= paramLong);
          }
          else;
        }
        catch (Exception localException)
        {
        }
      }
    }

    public void clear()
    {
      reset();
    }

    public boolean filter(BaseDanmaku paramBaseDanmaku, int paramInt1, int paramInt2, DanmakuTimer paramDanmakuTimer, boolean paramBoolean)
    {
      boolean bool1 = true;
      while (true)
      {
        try
        {
          removeTimeoutDanmakus(this.blockedDanmakus, 2L);
          removeTimeoutDanmakus(this.passedDanmakus, 2L);
          removeTimeoutDanmakus(this.currentDanmakus, 3);
          if (this.blockedDanmakus.contains(paramBaseDanmaku))
          {
            boolean bool2 = paramBaseDanmaku.isOutside();
            if (!bool2)
              return bool1;
          }
          if (this.passedDanmakus.contains(paramBaseDanmaku))
          {
            bool1 = false;
            continue;
          }
          if (this.currentDanmakus.containsKey(paramBaseDanmaku.text))
          {
            this.currentDanmakus.put(paramBaseDanmaku.text, paramBaseDanmaku);
            this.blockedDanmakus.removeItem(paramBaseDanmaku);
            this.blockedDanmakus.addItem(paramBaseDanmaku);
            continue;
          }
        }
        finally
        {
        }
        this.currentDanmakus.put(paramBaseDanmaku.text, paramBaseDanmaku);
        this.passedDanmakus.addItem(paramBaseDanmaku);
        bool1 = false;
      }
    }

    public void reset()
    {
      try
      {
        this.passedDanmakus.clear();
        this.blockedDanmakus.clear();
        this.currentDanmakus.clear();
        return;
      }
      finally
      {
        localObject = finally;
        throw localObject;
      }
    }

    public void setData(Void paramVoid)
    {
    }
  }

  public static class ElapsedTimeFilter extends DanmakuFilters.BaseDanmakuFilter<Object>
  {
    protected final IDanmakus danmakus = new Danmakus();
    long mMaxTime = 20L;

    public void clear()
    {
      reset();
    }

    public boolean filter(BaseDanmaku paramBaseDanmaku, int paramInt1, int paramInt2, DanmakuTimer paramDanmakuTimer, boolean paramBoolean)
    {
      boolean bool1 = true;
      while (true)
      {
        try
        {
          if ((this.danmakus.last() != null) && (this.danmakus.last().isTimeOut()))
            this.danmakus.clear();
          boolean bool2 = this.danmakus.contains(paramBaseDanmaku);
          if (bool2)
            return bool1;
          if ((paramDanmakuTimer == null) || (!paramBaseDanmaku.isOutside()))
            break label122;
          if (System.currentTimeMillis() - paramDanmakuTimer.currMillisecond >= this.mMaxTime)
          {
            this.danmakus.addItem(paramBaseDanmaku);
            continue;
          }
        }
        finally
        {
        }
        bool1 = false;
        continue;
        label122: bool1 = false;
      }
    }

    public void reset()
    {
      try
      {
        this.danmakus.clear();
        return;
      }
      finally
      {
        localObject = finally;
        throw localObject;
      }
    }

    public void setData(Object paramObject)
    {
      reset();
    }
  }

  public static class GuestFilter extends DanmakuFilters.BaseDanmakuFilter<Boolean>
  {
    private Boolean mBlock = Boolean.valueOf(false);

    public boolean filter(BaseDanmaku paramBaseDanmaku, int paramInt1, int paramInt2, DanmakuTimer paramDanmakuTimer, boolean paramBoolean)
    {
      if (!this.mBlock.booleanValue())
        return false;
      return paramBaseDanmaku.isGuest;
    }

    public void reset()
    {
      this.mBlock = Boolean.valueOf(false);
    }

    public void setData(Boolean paramBoolean)
    {
      this.mBlock = paramBoolean;
    }
  }

  public static abstract interface IDanmakuFilter<T>
  {
    public abstract void clear();

    public abstract boolean filter(BaseDanmaku paramBaseDanmaku, int paramInt1, int paramInt2, DanmakuTimer paramDanmakuTimer, boolean paramBoolean);

    public abstract void reset();

    public abstract void setData(T paramT);
  }

  public static class QuantityDanmakuFilter extends DanmakuFilters.BaseDanmakuFilter<Integer>
  {
    protected final IDanmakus danmakus = new Danmakus();
    protected BaseDanmaku mLastSkipped = null;
    protected int mMaximumSize = -1;

    public void clear()
    {
      reset();
    }

    public boolean filter(BaseDanmaku paramBaseDanmaku, int paramInt1, int paramInt2, DanmakuTimer paramDanmakuTimer, boolean paramBoolean)
    {
      int i = 1;
      while (true)
      {
        try
        {
          BaseDanmaku localBaseDanmaku = this.danmakus.last();
          if ((localBaseDanmaku != null) && (localBaseDanmaku.isTimeOut()))
            this.danmakus.clear();
          if (this.mMaximumSize > 0)
          {
            int j = paramBaseDanmaku.getType();
            if (j == i);
          }
          else
          {
            i = 0;
            return i;
          }
          if (this.danmakus.contains(paramBaseDanmaku))
            continue;
          if ((paramInt2 < this.mMaximumSize) || (paramBaseDanmaku.isShown()) || ((this.mLastSkipped != null) && (paramBaseDanmaku.time - this.mLastSkipped.time > 500L)))
          {
            this.mLastSkipped = paramBaseDanmaku;
            i = 0;
            continue;
          }
          if ((paramInt1 > this.mMaximumSize) && (!paramBaseDanmaku.isTimeOut()))
          {
            this.danmakus.addItem(paramBaseDanmaku);
            continue;
          }
        }
        finally
        {
        }
        this.mLastSkipped = paramBaseDanmaku;
        i = 0;
      }
    }

    public void reset()
    {
      try
      {
        this.danmakus.clear();
        return;
      }
      finally
      {
        localObject = finally;
        throw localObject;
      }
    }

    public void setData(Integer paramInteger)
    {
      reset();
      if (paramInteger == null);
      while (paramInteger.intValue() == this.mMaximumSize)
        return;
      this.mMaximumSize = paramInteger.intValue();
    }
  }

  public static class TextColorFilter extends DanmakuFilters.BaseDanmakuFilter<List<Integer>>
  {
    public List<Integer> mWhiteList = new ArrayList();

    private void addToWhiteList(Integer paramInteger)
    {
      if (!this.mWhiteList.contains(paramInteger))
        this.mWhiteList.add(paramInteger);
    }

    public boolean filter(BaseDanmaku paramBaseDanmaku, int paramInt1, int paramInt2, DanmakuTimer paramDanmakuTimer, boolean paramBoolean)
    {
      return (paramBaseDanmaku != null) && (!this.mWhiteList.contains(Integer.valueOf(paramBaseDanmaku.textColor)));
    }

    public void reset()
    {
      this.mWhiteList.clear();
    }

    public void setData(List<Integer> paramList)
    {
      reset();
      if (paramList != null)
      {
        Iterator localIterator = paramList.iterator();
        while (localIterator.hasNext())
          addToWhiteList((Integer)localIterator.next());
      }
    }
  }

  public static class TypeDanmakuFilter extends DanmakuFilters.BaseDanmakuFilter<List<Integer>>
  {
    final List<Integer> mFilterTypes = Collections.synchronizedList(new ArrayList());

    public void disableType(Integer paramInteger)
    {
      if (this.mFilterTypes.contains(paramInteger))
        this.mFilterTypes.remove(paramInteger);
    }

    public void enableType(Integer paramInteger)
    {
      if (!this.mFilterTypes.contains(paramInteger))
        this.mFilterTypes.add(paramInteger);
    }

    public boolean filter(BaseDanmaku paramBaseDanmaku, int paramInt1, int paramInt2, DanmakuTimer paramDanmakuTimer, boolean paramBoolean)
    {
      return (paramBaseDanmaku != null) && (this.mFilterTypes.contains(Integer.valueOf(paramBaseDanmaku.getType())));
    }

    public void reset()
    {
      this.mFilterTypes.clear();
    }

    public void setData(List<Integer> paramList)
    {
      reset();
      if (paramList != null)
      {
        Iterator localIterator = paramList.iterator();
        while (localIterator.hasNext())
          enableType((Integer)localIterator.next());
      }
    }
  }

  public static abstract class UserFilter<T> extends DanmakuFilters.BaseDanmakuFilter<List<T>>
  {
    public List<T> mBlackList = new ArrayList();

    private void addToBlackList(T paramT)
    {
      if (!this.mBlackList.contains(paramT))
        this.mBlackList.add(paramT);
    }

    public abstract boolean filter(BaseDanmaku paramBaseDanmaku, int paramInt1, int paramInt2, DanmakuTimer paramDanmakuTimer, boolean paramBoolean);

    public void reset()
    {
      this.mBlackList.clear();
    }

    public void setData(List<T> paramList)
    {
      reset();
      if (paramList != null)
      {
        Iterator localIterator = paramList.iterator();
        while (localIterator.hasNext())
          addToBlackList(localIterator.next());
      }
    }
  }

  public static class UserHashFilter extends DanmakuFilters.UserFilter<String>
  {
    public boolean filter(BaseDanmaku paramBaseDanmaku, int paramInt1, int paramInt2, DanmakuTimer paramDanmakuTimer, boolean paramBoolean)
    {
      return (paramBaseDanmaku != null) && (this.mBlackList.contains(paramBaseDanmaku.userHash));
    }
  }

  public static class UserIdFilter extends DanmakuFilters.UserFilter<Integer>
  {
    public boolean filter(BaseDanmaku paramBaseDanmaku, int paramInt1, int paramInt2, DanmakuTimer paramDanmakuTimer, boolean paramBoolean)
    {
      return (paramBaseDanmaku != null) && (this.mBlackList.contains(Integer.valueOf(paramBaseDanmaku.userId)));
    }
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     master.flame.danmaku.controller.DanmakuFilters
 * JD-Core Version:    0.6.2
 */