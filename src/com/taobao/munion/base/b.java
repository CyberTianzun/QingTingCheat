package com.taobao.munion.base;

import android.os.AsyncTask;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class b
{
  private static boolean a = false;
  private static AtomicInteger b = new AtomicInteger(0);
  private static Map<Integer, AsyncTask<?, ?, ?>> c = new ConcurrentHashMap();

  public static int a(AsyncTask<?, ?, ?> paramAsyncTask)
  {
    int i = b.getAndIncrement();
    c.put(Integer.valueOf(i), paramAsyncTask);
    return i;
  }

  public static void a(int paramInt)
  {
    c.remove(Integer.valueOf(paramInt));
  }

  public static void a(boolean paramBoolean)
  {
    a = paramBoolean;
  }

  public static boolean a()
  {
    return a;
  }

  public static boolean a(long paramLong, TimeUnit paramTimeUnit)
  {
    while (true)
    {
      try
      {
        Set localSet = c.entrySet();
        if (!localSet.isEmpty())
        {
          int i = localSet.size();
          if (i <= 0)
            break label177;
          long l = paramLong / i;
          Iterator localIterator = localSet.iterator();
          j = 0;
          if (localIterator.hasNext())
          {
            AsyncTask localAsyncTask = (AsyncTask)((Map.Entry)localIterator.next()).getValue();
            try
            {
              localAsyncTask.get(l, paramTimeUnit);
              k = j + 1;
              j = k;
            }
            catch (Exception localException)
            {
              if (!localAsyncTask.cancel(true))
                break label170;
            }
            k = j + 1;
            continue;
          }
          c.clear();
          if (j == i)
          {
            bool = true;
            return bool;
          }
          bool = false;
          continue;
        }
        boolean bool = true;
        continue;
      }
      finally
      {
      }
      label170: int k = j;
      continue;
      label177: int j = 0;
    }
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.taobao.munion.base.b
 * JD-Core Version:    0.6.2
 */