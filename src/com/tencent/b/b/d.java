package com.tencent.b.b;

import android.os.StatFs;
import java.io.File;

public class d
{
  private File a;
  private long b;
  private long c;

  public static d b(File paramFile)
  {
    d locald = new d();
    locald.a(paramFile);
    StatFs localStatFs = new StatFs(paramFile.getAbsolutePath());
    long l1 = localStatFs.getBlockSize();
    long l2 = localStatFs.getBlockCount();
    long l3 = localStatFs.getAvailableBlocks();
    locald.a(l2 * l1);
    locald.b(l3 * l1);
    return locald;
  }

  public File a()
  {
    return this.a;
  }

  public void a(long paramLong)
  {
    this.b = paramLong;
  }

  public void a(File paramFile)
  {
    this.a = paramFile;
  }

  public long b()
  {
    return this.b;
  }

  public void b(long paramLong)
  {
    this.c = paramLong;
  }

  public long c()
  {
    return this.c;
  }

  public String toString()
  {
    Object[] arrayOfObject = new Object[3];
    arrayOfObject[0] = a().getAbsolutePath();
    arrayOfObject[1] = Long.valueOf(c());
    arrayOfObject[2] = Long.valueOf(b());
    return String.format("[%s : %d / %d]", arrayOfObject);
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.tencent.b.b.d
 * JD-Core Version:    0.6.2
 */