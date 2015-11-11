package cn.mmachina;

import android.content.Context;

public class JniClient
{
  public static int version = 1;

  static
  {
    System.loadLibrary("MMANDKSignature");
  }

  public static native String MDString(String paramString1, Context paramContext, String paramString2);
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     cn.mmachina.JniClient
 * JD-Core Version:    0.6.2
 */