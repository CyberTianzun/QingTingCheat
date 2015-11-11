package com.taobao.munion.base;

import android.os.SystemClock;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Log
{
  public static boolean DEBUG = true;
  public static String TAG = "Munion";

  private static String a(String paramString, Object[] paramArrayOfObject)
  {
    StackTraceElement[] arrayOfStackTraceElement;
    int i;
    label25: String str4;
    if ((paramArrayOfObject == null) || (paramArrayOfObject.length == 0))
    {
      arrayOfStackTraceElement = new Throwable().fillInStackTrace().getStackTrace();
      i = 2;
      if (i >= arrayOfStackTraceElement.length)
        break label169;
      if (arrayOfStackTraceElement[i].getClass().equals(Log.class))
        break label163;
      String str2 = arrayOfStackTraceElement[i].getClassName();
      String str3 = str2.substring(1 + str2.lastIndexOf('.'));
      str4 = str3.substring(1 + str3.lastIndexOf('$'));
    }
    label163: label169: for (String str1 = str4 + "." + arrayOfStackTraceElement[i].getMethodName(); ; str1 = "<unknown>")
    {
      Object[] arrayOfObject = new Object[3];
      arrayOfObject[0] = Long.valueOf(Thread.currentThread().getId());
      arrayOfObject[1] = str1;
      arrayOfObject[2] = paramString;
      return String.format("[%d] %s: %s", arrayOfObject);
      paramString = String.format(paramString, paramArrayOfObject);
      break;
      i++;
      break label25;
    }
  }

  public static void d(String paramString, Object[] paramArrayOfObject)
  {
    if (DEBUG)
      android.util.Log.d(TAG, a(paramString, paramArrayOfObject));
  }

  public static void e(String paramString, Object[] paramArrayOfObject)
  {
    android.util.Log.e(TAG, a(paramString, paramArrayOfObject));
  }

  public static void e(Throwable paramThrowable, String paramString, Object[] paramArrayOfObject)
  {
    android.util.Log.e(TAG, a(paramString, paramArrayOfObject), paramThrowable);
  }

  public static void i(String paramString, Object[] paramArrayOfObject)
  {
    if (DEBUG)
      android.util.Log.i(TAG, a(paramString, paramArrayOfObject));
  }

  public static void setTag(String paramString)
  {
    d("Changing log tag to %s", new Object[] { paramString });
    TAG = paramString;
  }

  public static void v(String paramString, Object[] paramArrayOfObject)
  {
    if (DEBUG)
      android.util.Log.v(TAG, a(paramString, paramArrayOfObject));
  }

  public static void w(String paramString, Object[] paramArrayOfObject)
  {
    android.util.Log.w(TAG, a(paramString, paramArrayOfObject));
  }

  public static void w(Throwable paramThrowable, String paramString, Object[] paramArrayOfObject)
  {
    android.util.Log.w(TAG, a(paramString, paramArrayOfObject), paramThrowable);
  }

  public static void wtf(String paramString, Object[] paramArrayOfObject)
  {
    android.util.Log.wtf(TAG, a(paramString, paramArrayOfObject));
  }

  public static void wtf(Throwable paramThrowable, String paramString, Object[] paramArrayOfObject)
  {
    android.util.Log.wtf(TAG, a(paramString, paramArrayOfObject), paramThrowable);
  }

  public static class a
  {
    public static final boolean a = Log.DEBUG;
    private static final long b;
    private final List<a> c = new ArrayList();
    private boolean d = false;

    private long a()
    {
      if (this.c.size() == 0)
        return 0L;
      long l = ((a)this.c.get(0)).c;
      return ((a)this.c.get(-1 + this.c.size())).c - l;
    }

    public void a(String paramString)
    {
      try
      {
        this.d = true;
        long l1 = a();
        if (l1 <= 0L);
        while (true)
        {
          return;
          long l2 = ((a)this.c.get(0)).c;
          Object[] arrayOfObject1 = new Object[2];
          arrayOfObject1[0] = Long.valueOf(l1);
          arrayOfObject1[1] = paramString;
          Log.d("(%-4d ms) %s", arrayOfObject1);
          Iterator localIterator = this.c.iterator();
          long l4;
          for (long l3 = l2; localIterator.hasNext(); l3 = l4)
          {
            a locala = (a)localIterator.next();
            l4 = locala.c;
            Object[] arrayOfObject2 = new Object[3];
            arrayOfObject2[0] = Long.valueOf(l4 - l3);
            arrayOfObject2[1] = Long.valueOf(locala.b);
            arrayOfObject2[2] = locala.a;
            Log.d("(+%-4d) [%2d] %s", arrayOfObject2);
          }
        }
      }
      finally
      {
      }
    }

    public void a(String paramString, long paramLong)
    {
      try
      {
        boolean bool = this.d;
        if (bool);
        while (true)
        {
          return;
          this.c.add(new a(paramString, paramLong, SystemClock.elapsedRealtime()));
        }
      }
      finally
      {
      }
    }

    protected void finalize()
      throws Throwable
    {
      if (!this.d)
      {
        a("Request on the loose");
        Log.e("Marker log finalized without finish() - uncaught exit point for request", new Object[0]);
      }
    }

    private static class a
    {
      public final String a;
      public final long b;
      public final long c;

      public a(String paramString, long paramLong1, long paramLong2)
      {
        this.a = paramString;
        this.b = paramLong1;
        this.c = paramLong2;
      }
    }
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.taobao.munion.base.Log
 * JD-Core Version:    0.6.2
 */