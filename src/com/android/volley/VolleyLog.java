package com.android.volley;

import android.os.SystemClock;
import android.util.Log;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

public class VolleyLog
{
  public static boolean DEBUG = Log.isLoggable(TAG, 2);
  public static String TAG = "Volley";

  private static String buildMessage(String paramString, Object[] paramArrayOfObject)
  {
    String str1;
    StackTraceElement[] arrayOfStackTraceElement;
    String str2;
    if (paramArrayOfObject == null)
    {
      str1 = paramString;
      arrayOfStackTraceElement = new Throwable().fillInStackTrace().getStackTrace();
      str2 = "<unknown>";
    }
    label178: for (int i = 2; ; i++)
    {
      if (i >= arrayOfStackTraceElement.length);
      while (true)
      {
        Locale localLocale = Locale.US;
        Object[] arrayOfObject = new Object[3];
        arrayOfObject[0] = Long.valueOf(Thread.currentThread().getId());
        arrayOfObject[1] = str2;
        arrayOfObject[2] = str1;
        return String.format(localLocale, "[%d] %s: %s", arrayOfObject);
        str1 = String.format(Locale.US, paramString, paramArrayOfObject);
        break;
        if (arrayOfStackTraceElement[i].getClass().equals(VolleyLog.class))
          break label178;
        String str3 = arrayOfStackTraceElement[i].getClassName();
        String str4 = str3.substring(1 + str3.lastIndexOf('.'));
        str2 = str4.substring(1 + str4.lastIndexOf('$')) + "." + arrayOfStackTraceElement[i].getMethodName();
      }
    }
  }

  public static void d(String paramString, Object[] paramArrayOfObject)
  {
    Log.d(TAG, buildMessage(paramString, paramArrayOfObject));
  }

  public static void e(String paramString, Object[] paramArrayOfObject)
  {
    Log.e(TAG, buildMessage(paramString, paramArrayOfObject));
  }

  public static void e(Throwable paramThrowable, String paramString, Object[] paramArrayOfObject)
  {
    Log.e(TAG, buildMessage(paramString, paramArrayOfObject), paramThrowable);
  }

  public static void setTag(String paramString)
  {
    d("Changing log tag to %s", new Object[] { paramString });
    TAG = paramString;
    DEBUG = Log.isLoggable(TAG, 2);
  }

  public static void v(String paramString, Object[] paramArrayOfObject)
  {
    if (DEBUG)
      Log.v(TAG, buildMessage(paramString, paramArrayOfObject));
  }

  public static void w(Throwable paramThrowable, String paramString, Object[] paramArrayOfObject)
  {
    Log.w(TAG, buildMessage(paramString, paramArrayOfObject), paramThrowable);
  }

  public static void wtf(String paramString, Object[] paramArrayOfObject)
  {
    Log.wtf(TAG, buildMessage(paramString, paramArrayOfObject));
  }

  public static void wtf(Throwable paramThrowable, String paramString, Object[] paramArrayOfObject)
  {
    Log.wtf(TAG, buildMessage(paramString, paramArrayOfObject), paramThrowable);
  }

  static class MarkerLog
  {
    public static final boolean ENABLED = VolleyLog.DEBUG;
    private static final long MIN_DURATION_FOR_LOGGING_MS;
    private boolean mFinished = false;
    private final List<Marker> mMarkers = new ArrayList();

    private long getTotalDuration()
    {
      if (this.mMarkers.size() == 0)
        return 0L;
      long l = ((Marker)this.mMarkers.get(0)).time;
      return ((Marker)this.mMarkers.get(-1 + this.mMarkers.size())).time - l;
    }

    public void add(String paramString, long paramLong)
    {
      try
      {
        if (this.mFinished)
          throw new IllegalStateException("Marker added to finished log");
      }
      finally
      {
      }
      this.mMarkers.add(new Marker(paramString, paramLong, SystemClock.elapsedRealtime()));
    }

    protected void finalize()
      throws Throwable
    {
      if (!this.mFinished)
      {
        finish("Request on the loose");
        VolleyLog.e("Marker log finalized without finish() - uncaught exit point for request", new Object[0]);
      }
    }

    public void finish(String paramString)
    {
      try
      {
        this.mFinished = true;
        long l1 = getTotalDuration();
        if (l1 <= 0L);
        while (true)
        {
          return;
          long l2 = ((Marker)this.mMarkers.get(0)).time;
          Object[] arrayOfObject1 = new Object[2];
          arrayOfObject1[0] = Long.valueOf(l1);
          arrayOfObject1[1] = paramString;
          VolleyLog.d("(%-4d ms) %s", arrayOfObject1);
          Iterator localIterator = this.mMarkers.iterator();
          while (localIterator.hasNext())
          {
            Marker localMarker = (Marker)localIterator.next();
            long l3 = localMarker.time;
            Object[] arrayOfObject2 = new Object[3];
            arrayOfObject2[0] = Long.valueOf(l3 - l2);
            arrayOfObject2[1] = Long.valueOf(localMarker.thread);
            arrayOfObject2[2] = localMarker.name;
            VolleyLog.d("(+%-4d) [%2d] %s", arrayOfObject2);
            l2 = l3;
          }
        }
      }
      finally
      {
      }
    }

    private static class Marker
    {
      public final String name;
      public final long thread;
      public final long time;

      public Marker(String paramString, long paramLong1, long paramLong2)
      {
        this.name = paramString;
        this.thread = paramLong1;
        this.time = paramLong2;
      }
    }
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.android.volley.VolleyLog
 * JD-Core Version:    0.6.2
 */