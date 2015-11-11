package com.taobao.munion.base.download;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import java.io.File;

public class DeltaUpdate
{
  private static boolean a = false;
  private static final String b = "bspatch";

  static
  {
    try
    {
      System.loadLibrary("bspatch");
      a = true;
      return;
    }
    catch (UnsatisfiedLinkError localUnsatisfiedLinkError)
    {
      a = false;
    }
  }

  public static int a(String paramString1, String paramString2, String paramString3)
  {
    return bspatch(paramString1, paramString2, paramString3);
  }

  public static String a(Context paramContext)
  {
    return paramContext.getApplicationInfo().sourceDir;
  }

  public static boolean a()
  {
    return a;
  }

  public static String b(Context paramContext)
  {
    String str = a(paramContext);
    if (!new File(str).exists())
      return "";
    return f.a(new File(str));
  }

  public static native int bspatch(String paramString1, String paramString2, String paramString3);
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.taobao.munion.base.download.DeltaUpdate
 * JD-Core Version:    0.6.2
 */