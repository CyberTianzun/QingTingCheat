package com.tencent.b.a;

import com.tencent.b.c.a;
import java.io.File;
import java.io.FileFilter;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;

public class b
{
  private static SimpleDateFormat a = com.tencent.b.c.b.a("yyyy-MM-dd");
  private static FileFilter b = new FileFilter()
  {
    public boolean accept(File paramAnonymousFile)
    {
      if (!paramAnonymousFile.isDirectory());
      while (b.a(paramAnonymousFile) <= 0L)
        return false;
      return true;
    }
  };
  private String c = "Tracer.File";
  private int d = 2147483647;
  private int e = 2147483647;
  private int f = 4096;
  private long g = 10000L;
  private File h;
  private int i = 10;
  private String j = ".log";
  private long k = 9223372036854775807L;
  private FileFilter l = new FileFilter()
  {
    public boolean accept(File paramAnonymousFile)
    {
      if (!paramAnonymousFile.getName().endsWith(b.this.j()));
      while (b.d(paramAnonymousFile) == -1)
        return false;
      return true;
    }
  };
  private Comparator<? super File> m = new Comparator()
  {
    public int a(File paramAnonymousFile1, File paramAnonymousFile2)
    {
      return b.d(paramAnonymousFile1) - b.d(paramAnonymousFile2);
    }
  };

  public b(File paramFile, int paramInt1, int paramInt2, int paramInt3, String paramString1, long paramLong1, int paramInt4, String paramString2, long paramLong2)
  {
    c(paramFile);
    b(paramInt1);
    a(paramInt2);
    c(paramInt3);
    a(paramString1);
    b(paramLong1);
    d(paramInt4);
    b(paramString2);
    c(paramLong2);
  }

  public static long a(File paramFile)
  {
    try
    {
      long l1 = a.parse(paramFile.getName()).getTime();
      return l1;
    }
    catch (Exception localException)
    {
    }
    return -1L;
  }

  private File d(long paramLong)
  {
    return e(a(paramLong));
  }

  private File e(File paramFile)
  {
    File[] arrayOfFile = b(paramFile);
    File localFile;
    if ((arrayOfFile == null) || (arrayOfFile.length == 0))
      localFile = new File(paramFile, "1" + j());
    while (true)
    {
      return localFile;
      a(arrayOfFile);
      localFile = arrayOfFile[(-1 + arrayOfFile.length)];
      int n = arrayOfFile.length - e();
      if ((int)localFile.length() > d())
      {
        int i2 = 1 + f(localFile);
        localFile = new File(paramFile, i2 + j());
        n++;
      }
      for (int i1 = 0; i1 < n; i1++)
        arrayOfFile[i1].delete();
    }
  }

  private static int f(File paramFile)
  {
    try
    {
      String str = paramFile.getName();
      int n = Integer.parseInt(str.substring(0, str.indexOf('.')));
      return n;
    }
    catch (Exception localException)
    {
    }
    return -1;
  }

  public File a()
  {
    return d(System.currentTimeMillis());
  }

  public File a(long paramLong)
  {
    File localFile = new File(h(), a.format(Long.valueOf(paramLong)));
    localFile.mkdirs();
    return localFile;
  }

  public void a(int paramInt)
  {
    this.d = paramInt;
  }

  public void a(String paramString)
  {
    this.c = paramString;
  }

  public File[] a(File[] paramArrayOfFile)
  {
    Arrays.sort(paramArrayOfFile, this.m);
    return paramArrayOfFile;
  }

  public void b()
  {
    if (h() == null);
    while (true)
    {
      return;
      File[] arrayOfFile = h().listFiles(b);
      if (arrayOfFile != null)
      {
        int n = arrayOfFile.length;
        for (int i1 = 0; i1 < n; i1++)
        {
          File localFile = arrayOfFile[i1];
          long l1 = a(localFile);
          if (System.currentTimeMillis() - l1 > k())
            a.a(localFile);
        }
      }
    }
  }

  public void b(int paramInt)
  {
    this.e = paramInt;
  }

  public void b(long paramLong)
  {
    this.g = paramLong;
  }

  public void b(String paramString)
  {
    this.j = paramString;
  }

  public File[] b(File paramFile)
  {
    return paramFile.listFiles(this.l);
  }

  public String c()
  {
    return this.c;
  }

  public void c(int paramInt)
  {
    this.f = paramInt;
  }

  public void c(long paramLong)
  {
    this.k = paramLong;
  }

  public void c(File paramFile)
  {
    this.h = paramFile;
  }

  public int d()
  {
    return this.d;
  }

  public void d(int paramInt)
  {
    this.i = paramInt;
  }

  public int e()
  {
    return this.e;
  }

  public int f()
  {
    return this.f;
  }

  public long g()
  {
    return this.g;
  }

  public File h()
  {
    return this.h;
  }

  public int i()
  {
    return this.i;
  }

  public String j()
  {
    return this.j;
  }

  public long k()
  {
    return this.k;
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.tencent.b.a.b
 * JD-Core Version:    0.6.2
 */