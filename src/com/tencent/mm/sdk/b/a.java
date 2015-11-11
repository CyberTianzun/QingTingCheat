package com.tencent.mm.sdk.b;

import android.os.Build;
import android.os.Build.VERSION;
import android.os.Looper;
import android.os.Process;

public final class a
{
  public static d h;
  private static a i;
  private static a j;
  private static final String k = localStringBuilder.toString();
  private static int level = 6;

  static
  {
    b localb = new b();
    i = localb;
    j = localb;
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("VERSION.RELEASE:[" + Build.VERSION.RELEASE);
    localStringBuilder.append("] VERSION.CODENAME:[" + Build.VERSION.CODENAME);
    localStringBuilder.append("] VERSION.INCREMENTAL:[" + Build.VERSION.INCREMENTAL);
    localStringBuilder.append("] BOARD:[" + Build.BOARD);
    localStringBuilder.append("] DEVICE:[" + Build.DEVICE);
    localStringBuilder.append("] DISPLAY:[" + Build.DISPLAY);
    localStringBuilder.append("] FINGERPRINT:[" + Build.FINGERPRINT);
    localStringBuilder.append("] HOST:[" + Build.HOST);
    localStringBuilder.append("] MANUFACTURER:[" + Build.MANUFACTURER);
    localStringBuilder.append("] MODEL:[" + Build.MODEL);
    localStringBuilder.append("] PRODUCT:[" + Build.PRODUCT);
    localStringBuilder.append("] TAGS:[" + Build.TAGS);
    localStringBuilder.append("] TYPE:[" + Build.TYPE);
    localStringBuilder.append("] USER:[" + Build.USER + "]");
  }

  public static void a(String paramString1, String paramString2)
  {
    a(paramString1, paramString2, null);
  }

  public static void a(String paramString1, String paramString2, Object[] paramArrayOfObject)
  {
    if ((j != null) && (j.b() <= 4))
      if (paramArrayOfObject != null)
        break label74;
    label74: for (String str1 = paramString2; ; str1 = String.format(paramString2, paramArrayOfObject))
    {
      if (str1 == null)
        str1 = "";
      String str2 = b(paramString1);
      a locala = j;
      Process.myPid();
      Thread.currentThread().getId();
      Looper.getMainLooper().getThread().getId();
      locala.f(str2, str1);
      return;
    }
  }

  private static String b(String paramString)
  {
    if (h != null)
      paramString = h.b(paramString);
    return paramString;
  }

  public static void b(String paramString1, String paramString2)
  {
    if ((j != null) && (j.b() <= 2))
    {
      if (paramString2 == null)
        paramString2 = "";
      String str = b(paramString1);
      a locala = j;
      Process.myPid();
      Thread.currentThread().getId();
      Looper.getMainLooper().getThread().getId();
      locala.d(str, paramString2);
    }
  }

  public static void c(String paramString1, String paramString2)
  {
    if ((j != null) && (j.b() <= 1))
    {
      if (paramString2 == null)
        paramString2 = "";
      String str = b(paramString1);
      a locala = j;
      Process.myPid();
      Thread.currentThread().getId();
      Looper.getMainLooper().getThread().getId();
      locala.e(str, paramString2);
    }
  }

  public static abstract interface a
  {
    public abstract int b();

    public abstract void d(String paramString1, String paramString2);

    public abstract void e(String paramString1, String paramString2);

    public abstract void f(String paramString1, String paramString2);
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.tencent.mm.sdk.b.a
 * JD-Core Version:    0.6.2
 */