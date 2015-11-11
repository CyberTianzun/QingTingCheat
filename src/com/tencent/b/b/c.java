package com.tencent.b.b;

import android.os.Environment;

public class c
{
  public static boolean a()
  {
    String str = Environment.getExternalStorageState();
    return ("mounted".equals(str)) || ("mounted_ro".equals(str));
  }

  public static d b()
  {
    if (!a())
      return null;
    return d.b(Environment.getExternalStorageDirectory());
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.tencent.b.b.c
 * JD-Core Version:    0.6.2
 */