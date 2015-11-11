package com.umeng.fb.util;

public class Log
{
  public static boolean LOG = false;

  public static void d(String paramString1, String paramString2)
  {
    if (LOG)
      android.util.Log.d(paramString1, paramString2);
  }

  public static void d(String paramString1, String paramString2, Exception paramException)
  {
    if (LOG)
      android.util.Log.d(paramString1, paramException.toString() + ":  [" + paramString2 + "]");
  }

  public static void e(String paramString1, String paramString2)
  {
    if (LOG)
      android.util.Log.e(paramString1, paramString2);
  }

  public static void e(String paramString1, String paramString2, Exception paramException)
  {
    if (LOG)
    {
      android.util.Log.e(paramString1, paramException.toString() + ":  [" + paramString2 + "]");
      for (StackTraceElement localStackTraceElement : paramException.getStackTrace())
        android.util.Log.e(paramString1, "        at\t " + localStackTraceElement.toString());
    }
  }

  public static void i(String paramString1, String paramString2)
  {
    if (LOG)
      android.util.Log.i(paramString1, paramString2);
  }

  public static void i(String paramString1, String paramString2, Exception paramException)
  {
    if (LOG)
      android.util.Log.i(paramString1, paramException.toString() + ":  [" + paramString2 + "]");
  }

  public static void v(String paramString1, String paramString2)
  {
    if (LOG)
      android.util.Log.v(paramString1, paramString2);
  }

  public static void v(String paramString1, String paramString2, Exception paramException)
  {
    if (LOG)
      android.util.Log.v(paramString1, paramException.toString() + ":  [" + paramString2 + "]");
  }

  public static void w(String paramString1, String paramString2)
  {
    if (LOG)
      android.util.Log.w(paramString1, paramString2);
  }

  public static void w(String paramString1, String paramString2, Exception paramException)
  {
    if (LOG)
    {
      android.util.Log.w(paramString1, paramException.toString() + ":  [" + paramString2 + "]");
      for (StackTraceElement localStackTraceElement : paramException.getStackTrace())
        android.util.Log.w(paramString1, "        at\t " + localStackTraceElement.toString());
    }
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.umeng.fb.util.Log
 * JD-Core Version:    0.6.2
 */