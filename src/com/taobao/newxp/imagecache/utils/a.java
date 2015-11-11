package com.taobao.newxp.imagecache.utils;

import java.io.BufferedWriter;
import java.io.Closeable;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.reflect.Array;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public final class a
  implements Closeable
{
  static final String a = "journal";
  static final String b = "journal.tmp";
  static final String c = "libcore.io.DiskLruCache";
  static final String d = "1";
  static final long e = -1L;
  private static final String f = "CLEAN";
  private static final String g = "DIRTY";
  private static final String h = "REMOVE";
  private static final String i = "READ";
  private static final Charset j = Charset.forName("UTF-8");
  private static final int k = 8192;
  private final File l;
  private final File m;
  private final File n;
  private final int o;
  private final long p;
  private final int q;
  private long r = 0L;
  private Writer s;
  private final LinkedHashMap<String, b> t = new LinkedHashMap(0, 0.75F, true);
  private int u;
  private long v = 0L;
  private final ExecutorService w = new ThreadPoolExecutor(0, 1, 60L, TimeUnit.SECONDS, new LinkedBlockingQueue());
  private final Callable<Void> x = new Callable()
  {
    public Void a()
      throws Exception
    {
      synchronized (a.this)
      {
        if (a.a(a.this) == null)
          return null;
        a.b(a.this);
        if (a.c(a.this))
        {
          a.d(a.this);
          a.a(a.this, 0);
        }
        return null;
      }
    }
  };

  private a(File paramFile, int paramInt1, int paramInt2, long paramLong)
  {
    this.l = paramFile;
    this.o = paramInt1;
    this.m = new File(paramFile, "journal");
    this.n = new File(paramFile, "journal.tmp");
    this.q = paramInt2;
    this.p = paramLong;
  }

  private a a(String paramString, long paramLong)
    throws IOException
  {
    while (true)
    {
      b localb1;
      a locala2;
      b localb2;
      try
      {
        l();
        e(paramString);
        localb1 = (b)this.t.get(paramString);
        if (paramLong != -1L)
          if (localb1 != null)
          {
            long l1 = b.e(localb1);
            if (l1 == paramLong);
          }
          else
          {
            locala2 = null;
            return locala2;
          }
        if (localb1 == null)
        {
          b localb3 = new b(paramString, null);
          this.t.put(paramString, localb3);
          localb2 = localb3;
          locala2 = new a(localb2, null);
          b.a(localb2, locala2);
          this.s.write("DIRTY " + paramString + '\n');
          this.s.flush();
          continue;
        }
      }
      finally
      {
      }
      a locala1 = b.a(localb1);
      if (locala1 != null)
        locala2 = null;
      else
        localb2 = localb1;
    }
  }

  public static a a(File paramFile, int paramInt1, int paramInt2, long paramLong)
    throws IOException
  {
    if (paramLong <= 0L)
      throw new IllegalArgumentException("maxSize <= 0");
    if (paramInt2 <= 0)
      throw new IllegalArgumentException("valueCount <= 0");
    a locala1 = new a(paramFile, paramInt1, paramInt2, paramLong);
    if (locala1.m.exists())
      try
      {
        locala1.h();
        locala1.i();
        locala1.s = new BufferedWriter(new FileWriter(locala1.m, true), 8192);
        return locala1;
      }
      catch (IOException localIOException)
      {
        locala1.f();
      }
    paramFile.mkdirs();
    a locala2 = new a(paramFile, paramInt1, paramInt2, paramLong);
    locala2.j();
    return locala2;
  }

  public static String a(InputStream paramInputStream)
    throws IOException
  {
    StringBuilder localStringBuilder = new StringBuilder(80);
    while (true)
    {
      int i1 = paramInputStream.read();
      if (i1 == -1)
        throw new EOFException();
      if (i1 == 10)
      {
        int i2 = localStringBuilder.length();
        if ((i2 > 0) && (localStringBuilder.charAt(i2 - 1) == '\r'))
          localStringBuilder.setLength(i2 - 1);
        return localStringBuilder.toString();
      }
      localStringBuilder.append((char)i1);
    }
  }

  public static String a(Reader paramReader)
    throws IOException
  {
    StringWriter localStringWriter;
    try
    {
      localStringWriter = new StringWriter();
      char[] arrayOfChar = new char[1024];
      while (true)
      {
        int i1 = paramReader.read(arrayOfChar);
        if (i1 == -1)
          break;
        localStringWriter.write(arrayOfChar, 0, i1);
      }
    }
    finally
    {
      paramReader.close();
    }
    String str = localStringWriter.toString();
    paramReader.close();
    return str;
  }

  private void a(a parama, boolean paramBoolean)
    throws IOException
  {
    b localb;
    try
    {
      localb = a.a(parama);
      if (b.a(localb) != parama)
        throw new IllegalStateException();
    }
    finally
    {
    }
    int i1 = 0;
    int i2;
    if (paramBoolean)
    {
      boolean bool = b.d(localb);
      i1 = 0;
      if (!bool)
      {
        i2 = 0;
        int i3 = this.q;
        i1 = 0;
        if (i2 < i3)
        {
          if (localb.b(i2).exists())
            break label408;
          parama.b();
          throw new IllegalStateException("edit didn't create file " + i2);
        }
      }
    }
    while (true)
    {
      if (i1 < this.q)
      {
        File localFile1 = localb.b(i1);
        if (paramBoolean)
        {
          if (localFile1.exists())
          {
            File localFile2 = localb.a(i1);
            localFile1.renameTo(localFile2);
            long l2 = b.b(localb)[i1];
            long l3 = localFile2.length();
            b.b(localb)[i1] = l3;
            this.r = (l3 + (this.r - l2));
          }
        }
        else
          b(localFile1);
      }
      else
      {
        this.u = (1 + this.u);
        b.a(localb, null);
        if ((paramBoolean | b.d(localb)))
        {
          b.a(localb, true);
          this.s.write("CLEAN " + b.c(localb) + localb.a() + '\n');
          if (paramBoolean)
          {
            long l1 = this.v;
            this.v = (1L + l1);
            b.a(localb, l1);
          }
        }
        while (true)
        {
          if ((this.r > this.p) || (k()))
            this.w.submit(this.x);
          return;
          this.t.remove(b.c(localb));
          this.s.write("REMOVE " + b.c(localb) + '\n');
        }
        label408: i2++;
        break;
      }
      i1++;
    }
  }

  public static void a(Closeable paramCloseable)
  {
    if (paramCloseable != null);
    try
    {
      paramCloseable.close();
      return;
    }
    catch (RuntimeException localRuntimeException)
    {
      throw localRuntimeException;
    }
    catch (Exception localException)
    {
    }
  }

  public static void a(File paramFile)
    throws IOException
  {
    File[] arrayOfFile = paramFile.listFiles();
    if (arrayOfFile == null)
      throw new IllegalArgumentException("not a directory: " + paramFile);
    int i1 = arrayOfFile.length;
    for (int i2 = 0; i2 < i1; i2++)
    {
      File localFile = arrayOfFile[i2];
      if (localFile.isDirectory())
        a(localFile);
      if (!localFile.delete())
        throw new IOException("failed to delete file: " + localFile);
    }
  }

  private static <T> T[] a(T[] paramArrayOfT, int paramInt1, int paramInt2)
  {
    int i1 = paramArrayOfT.length;
    if (paramInt1 > paramInt2)
      throw new IllegalArgumentException();
    if ((paramInt1 < 0) || (paramInt1 > i1))
      throw new ArrayIndexOutOfBoundsException();
    int i2 = paramInt2 - paramInt1;
    int i3 = Math.min(i2, i1 - paramInt1);
    Object[] arrayOfObject = (Object[])Array.newInstance(paramArrayOfT.getClass().getComponentType(), i2);
    System.arraycopy(paramArrayOfT, paramInt1, arrayOfObject, 0, i3);
    return arrayOfObject;
  }

  private static void b(File paramFile)
    throws IOException
  {
    if ((paramFile.exists()) && (!paramFile.delete()))
      throw new IOException();
  }

  private static String c(InputStream paramInputStream)
    throws IOException
  {
    return a(new InputStreamReader(paramInputStream, j));
  }

  private void d(String paramString)
    throws IOException
  {
    String[] arrayOfString = paramString.split(" ");
    if (arrayOfString.length < 2)
      throw new IOException("unexpected journal line: " + paramString);
    String str = arrayOfString[1];
    if ((arrayOfString[0].equals("REMOVE")) && (arrayOfString.length == 2))
    {
      this.t.remove(str);
      return;
    }
    b localb1 = (b)this.t.get(str);
    b localb2;
    if (localb1 == null)
    {
      localb2 = new b(str, null);
      this.t.put(str, localb2);
    }
    for (b localb3 = localb2; ; localb3 = localb1)
    {
      if ((arrayOfString[0].equals("CLEAN")) && (arrayOfString.length == 2 + this.q))
      {
        b.a(localb3, true);
        b.a(localb3, null);
        b.a(localb3, (String[])a(arrayOfString, 2, arrayOfString.length));
        return;
      }
      if ((arrayOfString[0].equals("DIRTY")) && (arrayOfString.length == 2))
      {
        b.a(localb3, new a(localb3, null));
        return;
      }
      if ((arrayOfString[0].equals("READ")) && (arrayOfString.length == 2))
        break;
      throw new IOException("unexpected journal line: " + paramString);
    }
  }

  private void e(String paramString)
  {
    if ((paramString.contains(" ")) || (paramString.contains("\n")) || (paramString.contains("\r")))
      throw new IllegalArgumentException("keys must not contain spaces or newlines: \"" + paramString + "\"");
  }

  // ERROR //
  private void h()
    throws IOException
  {
    // Byte code:
    //   0: new 449	java/io/BufferedInputStream
    //   3: dup
    //   4: new 451	java/io/FileInputStream
    //   7: dup
    //   8: aload_0
    //   9: getfield 124	com/taobao/newxp/imagecache/utils/a:m	Ljava/io/File;
    //   12: invokespecial 453	java/io/FileInputStream:<init>	(Ljava/io/File;)V
    //   15: sipush 8192
    //   18: invokespecial 456	java/io/BufferedInputStream:<init>	(Ljava/io/InputStream;I)V
    //   21: astore_1
    //   22: aload_1
    //   23: invokestatic 458	com/taobao/newxp/imagecache/utils/a:a	(Ljava/io/InputStream;)Ljava/lang/String;
    //   26: astore_3
    //   27: aload_1
    //   28: invokestatic 458	com/taobao/newxp/imagecache/utils/a:a	(Ljava/io/InputStream;)Ljava/lang/String;
    //   31: astore 4
    //   33: aload_1
    //   34: invokestatic 458	com/taobao/newxp/imagecache/utils/a:a	(Ljava/io/InputStream;)Ljava/lang/String;
    //   37: astore 5
    //   39: aload_1
    //   40: invokestatic 458	com/taobao/newxp/imagecache/utils/a:a	(Ljava/io/InputStream;)Ljava/lang/String;
    //   43: astore 6
    //   45: aload_1
    //   46: invokestatic 458	com/taobao/newxp/imagecache/utils/a:a	(Ljava/io/InputStream;)Ljava/lang/String;
    //   49: astore 7
    //   51: ldc 16
    //   53: aload_3
    //   54: invokevirtual 425	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   57: ifeq +54 -> 111
    //   60: ldc 19
    //   62: aload 4
    //   64: invokevirtual 425	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   67: ifeq +44 -> 111
    //   70: aload_0
    //   71: getfield 117	com/taobao/newxp/imagecache/utils/a:o	I
    //   74: invokestatic 463	java/lang/Integer:toString	(I)Ljava/lang/String;
    //   77: aload 5
    //   79: invokevirtual 425	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   82: ifeq +29 -> 111
    //   85: aload_0
    //   86: getfield 128	com/taobao/newxp/imagecache/utils/a:q	I
    //   89: invokestatic 463	java/lang/Integer:toString	(I)Ljava/lang/String;
    //   92: aload 6
    //   94: invokevirtual 425	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   97: ifeq +14 -> 111
    //   100: ldc_w 465
    //   103: aload 7
    //   105: invokevirtual 425	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   108: ifne +77 -> 185
    //   111: new 136	java/io/IOException
    //   114: dup
    //   115: new 172	java/lang/StringBuilder
    //   118: dup
    //   119: invokespecial 173	java/lang/StringBuilder:<init>	()V
    //   122: ldc_w 467
    //   125: invokevirtual 179	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   128: aload_3
    //   129: invokevirtual 179	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   132: ldc_w 469
    //   135: invokevirtual 179	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   138: aload 4
    //   140: invokevirtual 179	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   143: ldc_w 469
    //   146: invokevirtual 179	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   149: aload 6
    //   151: invokevirtual 179	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   154: ldc_w 469
    //   157: invokevirtual 179	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   160: aload 7
    //   162: invokevirtual 179	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   165: ldc_w 471
    //   168: invokevirtual 179	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   171: invokevirtual 186	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   174: invokespecial 364	java/io/IOException:<init>	(Ljava/lang/String;)V
    //   177: athrow
    //   178: astore_2
    //   179: aload_1
    //   180: invokestatic 473	com/taobao/newxp/imagecache/utils/a:a	(Ljava/io/Closeable;)V
    //   183: aload_2
    //   184: athrow
    //   185: aload_0
    //   186: aload_1
    //   187: invokestatic 458	com/taobao/newxp/imagecache/utils/a:a	(Ljava/io/InputStream;)Ljava/lang/String;
    //   190: invokespecial 475	com/taobao/newxp/imagecache/utils/a:d	(Ljava/lang/String;)V
    //   193: goto -8 -> 185
    //   196: astore 8
    //   198: aload_1
    //   199: invokestatic 473	com/taobao/newxp/imagecache/utils/a:a	(Ljava/io/Closeable;)V
    //   202: return
    //
    // Exception table:
    //   from	to	target	type
    //   22	111	178	finally
    //   111	178	178	finally
    //   185	193	178	finally
    //   185	193	196	java/io/EOFException
  }

  private void i()
    throws IOException
  {
    b(this.n);
    Iterator localIterator = this.t.values().iterator();
    while (localIterator.hasNext())
    {
      b localb = (b)localIterator.next();
      if (b.a(localb) == null)
      {
        for (int i2 = 0; i2 < this.q; i2++)
          this.r += b.b(localb)[i2];
      }
      else
      {
        b.a(localb, null);
        for (int i1 = 0; i1 < this.q; i1++)
        {
          b(localb.a(i1));
          b(localb.b(i1));
        }
        localIterator.remove();
      }
    }
  }

  private void j()
    throws IOException
  {
    BufferedWriter localBufferedWriter;
    while (true)
    {
      b localb;
      try
      {
        if (this.s != null)
          this.s.close();
        localBufferedWriter = new BufferedWriter(new FileWriter(this.n), 8192);
        localBufferedWriter.write("libcore.io.DiskLruCache");
        localBufferedWriter.write("\n");
        localBufferedWriter.write("1");
        localBufferedWriter.write("\n");
        localBufferedWriter.write(Integer.toString(this.o));
        localBufferedWriter.write("\n");
        localBufferedWriter.write(Integer.toString(this.q));
        localBufferedWriter.write("\n");
        localBufferedWriter.write("\n");
        Iterator localIterator = this.t.values().iterator();
        if (!localIterator.hasNext())
          break;
        localb = (b)localIterator.next();
        if (b.a(localb) != null)
        {
          localBufferedWriter.write("DIRTY " + b.c(localb) + '\n');
          continue;
        }
      }
      finally
      {
      }
      localBufferedWriter.write("CLEAN " + b.c(localb) + localb.a() + '\n');
    }
    localBufferedWriter.close();
    this.n.renameTo(this.m);
    this.s = new BufferedWriter(new FileWriter(this.m, true), 8192);
  }

  private boolean k()
  {
    return (this.u >= 2000) && (this.u >= this.t.size());
  }

  private void l()
  {
    if (this.s == null)
      throw new IllegalStateException("cache is closed");
  }

  private void m()
    throws IOException
  {
    while (this.r > this.p)
      c((String)((Map.Entry)this.t.entrySet().iterator().next()).getKey());
  }

  public c a(String paramString)
    throws IOException
  {
    try
    {
      l();
      e(paramString);
      b localb = (b)this.t.get(paramString);
      c localc;
      if (localb == null)
        localc = null;
      while (true)
      {
        return localc;
        if (!b.d(localb))
        {
          localc = null;
        }
        else
        {
          InputStream[] arrayOfInputStream = new InputStream[this.q];
          int i1 = 0;
          try
          {
            while (i1 < this.q)
            {
              arrayOfInputStream[i1] = new FileInputStream(localb.a(i1));
              i1++;
            }
          }
          catch (FileNotFoundException localFileNotFoundException)
          {
            localc = null;
          }
          continue;
          this.u = (1 + this.u);
          this.s.append("READ " + paramString + '\n');
          if (k())
            this.w.submit(this.x);
          localc = new c(paramString, b.e(localb), arrayOfInputStream, null);
        }
      }
    }
    finally
    {
    }
  }

  public File a()
  {
    return this.l;
  }

  public long b()
  {
    return this.p;
  }

  public a b(String paramString)
    throws IOException
  {
    return a(paramString, -1L);
  }

  public long c()
  {
    try
    {
      long l1 = this.r;
      return l1;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }

  public boolean c(String paramString)
    throws IOException
  {
    while (true)
    {
      try
      {
        l();
        e(paramString);
        b localb = (b)this.t.get(paramString);
        int i1;
        if (localb != null)
        {
          a locala = b.a(localb);
          i1 = 0;
          if (locala == null);
        }
        else
        {
          bool = false;
          return bool;
          this.r -= b.b(localb)[i1];
          b.b(localb)[i1] = 0L;
          i1++;
        }
        if (i1 < this.q)
        {
          File localFile = localb.a(i1);
          if (localFile.delete())
            continue;
          throw new IOException("failed to delete " + localFile);
        }
      }
      finally
      {
      }
      this.u = (1 + this.u);
      this.s.append("REMOVE " + paramString + '\n');
      this.t.remove(paramString);
      if (k())
        this.w.submit(this.x);
      boolean bool = true;
    }
  }

  public void close()
    throws IOException
  {
    while (true)
    {
      try
      {
        Writer localWriter = this.s;
        if (localWriter == null)
          return;
        Iterator localIterator = new ArrayList(this.t.values()).iterator();
        if (localIterator.hasNext())
        {
          b localb = (b)localIterator.next();
          if (b.a(localb) == null)
            continue;
          b.a(localb).b();
          continue;
        }
      }
      finally
      {
      }
      m();
      this.s.close();
      this.s = null;
    }
  }

  public boolean d()
  {
    return this.s == null;
  }

  public void e()
    throws IOException
  {
    try
    {
      l();
      m();
      this.s.flush();
      return;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }

  public void f()
    throws IOException
  {
    close();
    a(this.l);
  }

  public final class a
  {
    private final a.b b;
    private boolean c;

    private a(a.b arg2)
    {
      Object localObject;
      this.b = localObject;
    }

    public InputStream a(int paramInt)
      throws IOException
    {
      synchronized (a.this)
      {
        if (a.b.a(this.b) != this)
          throw new IllegalStateException();
      }
      if (!a.b.d(this.b))
        return null;
      FileInputStream localFileInputStream = new FileInputStream(this.b.a(paramInt));
      return localFileInputStream;
    }

    public void a()
      throws IOException
    {
      if (this.c)
      {
        a.a(a.this, this, false);
        a.this.c(a.b.c(this.b));
        return;
      }
      a.a(a.this, this, true);
    }

    // ERROR //
    public void a(int paramInt, String paramString)
      throws IOException
    {
      // Byte code:
      //   0: new 63	java/io/OutputStreamWriter
      //   3: dup
      //   4: aload_0
      //   5: iload_1
      //   6: invokevirtual 66	com/taobao/newxp/imagecache/utils/a$a:c	(I)Ljava/io/OutputStream;
      //   9: invokestatic 70	com/taobao/newxp/imagecache/utils/a:g	()Ljava/nio/charset/Charset;
      //   12: invokespecial 73	java/io/OutputStreamWriter:<init>	(Ljava/io/OutputStream;Ljava/nio/charset/Charset;)V
      //   15: astore_3
      //   16: aload_3
      //   17: aload_2
      //   18: invokevirtual 79	java/io/Writer:write	(Ljava/lang/String;)V
      //   21: aload_3
      //   22: invokestatic 82	com/taobao/newxp/imagecache/utils/a:a	(Ljava/io/Closeable;)V
      //   25: return
      //   26: astore 4
      //   28: aconst_null
      //   29: astore_3
      //   30: aload_3
      //   31: invokestatic 82	com/taobao/newxp/imagecache/utils/a:a	(Ljava/io/Closeable;)V
      //   34: aload 4
      //   36: athrow
      //   37: astore 4
      //   39: goto -9 -> 30
      //
      // Exception table:
      //   from	to	target	type
      //   0	16	26	finally
      //   16	21	37	finally
    }

    public String b(int paramInt)
      throws IOException
    {
      InputStream localInputStream = a(paramInt);
      if (localInputStream != null)
        return a.b(localInputStream);
      return null;
    }

    public void b()
      throws IOException
    {
      a.a(a.this, this, false);
    }

    public OutputStream c(int paramInt)
      throws IOException
    {
      synchronized (a.this)
      {
        if (a.b.a(this.b) != this)
          throw new IllegalStateException();
      }
      a locala1 = new a(new FileOutputStream(this.b.b(paramInt)), null);
      return locala1;
    }

    private class a extends FilterOutputStream
    {
      private a(OutputStream arg2)
      {
        super();
      }

      public void close()
      {
        try
        {
          this.out.close();
          return;
        }
        catch (IOException localIOException)
        {
          a.a.a(a.a.this, true);
        }
      }

      public void flush()
      {
        try
        {
          this.out.flush();
          return;
        }
        catch (IOException localIOException)
        {
          a.a.a(a.a.this, true);
        }
      }

      public void write(int paramInt)
      {
        try
        {
          this.out.write(paramInt);
          return;
        }
        catch (IOException localIOException)
        {
          a.a.a(a.a.this, true);
        }
      }

      public void write(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
      {
        try
        {
          this.out.write(paramArrayOfByte, paramInt1, paramInt2);
          return;
        }
        catch (IOException localIOException)
        {
          a.a.a(a.a.this, true);
        }
      }
    }
  }

  private final class b
  {
    private final String b;
    private final long[] c;
    private boolean d;
    private a.a e;
    private long f;

    private b(String arg2)
    {
      Object localObject;
      this.b = localObject;
      this.c = new long[a.e(a.this)];
    }

    private void a(String[] paramArrayOfString)
      throws IOException
    {
      if (paramArrayOfString.length != a.e(a.this))
        throw b(paramArrayOfString);
      int i = 0;
      try
      {
        while (i < paramArrayOfString.length)
        {
          this.c[i] = Long.parseLong(paramArrayOfString[i]);
          i++;
        }
      }
      catch (NumberFormatException localNumberFormatException)
      {
        throw b(paramArrayOfString);
      }
    }

    private IOException b(String[] paramArrayOfString)
      throws IOException
    {
      throw new IOException("unexpected journal line: " + Arrays.toString(paramArrayOfString));
    }

    public File a(int paramInt)
    {
      return new File(a.f(a.this), this.b + "." + paramInt);
    }

    public String a()
      throws IOException
    {
      StringBuilder localStringBuilder = new StringBuilder();
      for (long l : this.c)
        localStringBuilder.append(' ').append(l);
      return localStringBuilder.toString();
    }

    public File b(int paramInt)
    {
      return new File(a.f(a.this), this.b + "." + paramInt + ".tmp");
    }
  }

  public final class c
    implements Closeable
  {
    private final String b;
    private final long c;
    private final InputStream[] d;

    private c(String paramLong, long arg3, InputStream[] arg5)
    {
      this.b = paramLong;
      this.c = ???;
      Object localObject;
      this.d = localObject;
    }

    public a.a a()
      throws IOException
    {
      return a.a(a.this, this.b, this.c);
    }

    public InputStream a(int paramInt)
    {
      return this.d[paramInt];
    }

    public String b(int paramInt)
      throws IOException
    {
      return a.b(a(paramInt));
    }

    public void close()
    {
      InputStream[] arrayOfInputStream = this.d;
      int i = arrayOfInputStream.length;
      for (int j = 0; j < i; j++)
        a.a(arrayOfInputStream[j]);
    }
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.taobao.newxp.imagecache.utils.a
 * JD-Core Version:    0.6.2
 */