package com.taobao.munion.base.caches;

import android.os.Environment;
import android.os.StatFs;
import java.io.File;

public class r
{
  public static final int a = -1;

  public static boolean a()
  {
    String str = Environment.getExternalStorageState();
    return (str != null) && (str.equals("mounted"));
  }

  public static long b()
  {
    StatFs localStatFs = new StatFs(Environment.getDataDirectory().getPath());
    return localStatFs.getBlockSize() * localStatFs.getAvailableBlocks();
  }

  public static long c()
  {
    if (a())
    {
      StatFs localStatFs = new StatFs(Environment.getExternalStorageDirectory().getPath());
      return localStatFs.getBlockSize() * localStatFs.getAvailableBlocks();
    }
    return -1L;
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.taobao.munion.base.caches.r
 * JD-Core Version:    0.6.2
 */