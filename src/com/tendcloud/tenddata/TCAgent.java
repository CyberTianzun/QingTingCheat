package com.tendcloud.tenddata;

import android.app.Activity;
import android.content.Context;
import java.util.Map;
import java.util.TreeMap;

public class TCAgent
{
  public static boolean LOG_ON = false;
  protected static final String a = "TD";
  static final boolean b = false;
  static final String c = "TDLog";
  static boolean d = false;
  static final Map e = new TreeMap();
  private static ao f;

  private static void a(Context paramContext)
  {
    try
    {
      ag localag;
      if (f == null)
      {
        System.currentTimeMillis();
        localag = new ag();
      }
      try
      {
        if (f == null)
          f = (ao)localag.a(paramContext, "analytics", "TD", ao.class, j.class, "com.tendcloud.tenddata.ota.j");
        return;
      }
      catch (Exception localException)
      {
        while (true)
          localException.printStackTrace();
      }
    }
    finally
    {
    }
  }

  public static String getDeviceId(Context paramContext)
  {
    try
    {
      a(paramContext);
      String str = f.b(paramContext);
      return str;
    }
    catch (Throwable localThrowable)
    {
      localThrowable.printStackTrace();
    }
    return null;
  }

  public static void init(Context paramContext)
  {
    try
    {
      a(paramContext);
      f.a(paramContext);
      return;
    }
    catch (Throwable localThrowable)
    {
      localThrowable.printStackTrace();
    }
  }

  public static void init(Context paramContext, String paramString1, String paramString2)
  {
    try
    {
      a(paramContext);
      f.a(paramContext, paramString1, paramString2);
      return;
    }
    catch (Throwable localThrowable)
    {
      localThrowable.printStackTrace();
    }
  }

  public static void onError(Context paramContext, Throwable paramThrowable)
  {
    try
    {
      a(paramContext);
      f.a(paramContext, paramThrowable);
      return;
    }
    catch (Throwable localThrowable)
    {
      localThrowable.printStackTrace();
    }
  }

  public static void onEvent(Context paramContext, String paramString)
  {
    onEvent(paramContext, paramString, null);
  }

  public static void onEvent(Context paramContext, String paramString1, String paramString2)
  {
    onEvent(paramContext, paramString1, paramString2, null);
  }

  public static void onEvent(Context paramContext, String paramString1, String paramString2, Map paramMap)
  {
    while (true)
    {
      TreeMap localTreeMap;
      try
      {
        a(paramContext);
        if (e.size() > 0)
        {
          localTreeMap = new TreeMap();
          localTreeMap.putAll(e);
          if ((paramMap != null) && (paramMap.size() > 0))
            localTreeMap.putAll(paramMap);
        }
        else
        {
          f.a(paramContext, paramString1, paramString2, paramMap);
          return;
        }
      }
      catch (Throwable localThrowable)
      {
        localThrowable.printStackTrace();
        return;
      }
      paramMap = localTreeMap;
    }
  }

  public static void onPageEnd(Context paramContext, String paramString)
  {
    try
    {
      a(paramContext.getApplicationContext());
      f.c(paramContext, paramString);
      return;
    }
    catch (Throwable localThrowable)
    {
      localThrowable.printStackTrace();
    }
  }

  public static void onPageStart(Context paramContext, String paramString)
  {
    try
    {
      a(paramContext.getApplicationContext());
      f.b(paramContext, paramString);
      return;
    }
    catch (Throwable localThrowable)
    {
      localThrowable.printStackTrace();
    }
  }

  public static void onPause(Activity paramActivity)
  {
    try
    {
      a(paramActivity.getApplicationContext());
      f.b(paramActivity);
      return;
    }
    catch (Throwable localThrowable)
    {
      localThrowable.printStackTrace();
    }
  }

  public static void onResume(Activity paramActivity)
  {
    try
    {
      a(paramActivity.getApplicationContext());
      f.a(paramActivity);
      return;
    }
    catch (Throwable localThrowable)
    {
      localThrowable.printStackTrace();
    }
  }

  public static void removeGlobalKV(String paramString)
  {
    e.remove(paramString);
  }

  public static void setGlobalKV(String paramString, Object paramObject)
  {
    e.put(paramString, paramObject);
  }

  public static void setReportUncaughtExceptions(boolean paramBoolean)
  {
    try
    {
      d = paramBoolean;
      return;
    }
    catch (Throwable localThrowable)
    {
      localThrowable.printStackTrace();
    }
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.tendcloud.tenddata.TCAgent
 * JD-Core Version:    0.6.2
 */