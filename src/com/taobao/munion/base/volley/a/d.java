package com.taobao.munion.base.volley.a;

import android.os.SystemClock;
import com.taobao.munion.base.Log;
import com.taobao.munion.base.volley.b;
import com.taobao.munion.base.volley.b.a;
import java.io.EOFException;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class d
  implements b
{
  private static final int e = 5242880;
  private static final float f = 0.9F;
  private static final int g = 538051844;
  private final Map<String, a> a = new LinkedHashMap(16, 0.75F, true);
  private long b = 0L;
  private final File c;
  private final int d;

  public d(File paramFile)
  {
    this(paramFile, 5242880);
  }

  public d(File paramFile, int paramInt)
  {
    this.c = paramFile;
    this.d = paramInt;
  }

  static int a(InputStream paramInputStream)
    throws IOException
  {
    return 0x0 | e(paramInputStream) << 0 | e(paramInputStream) << 8 | e(paramInputStream) << 16 | e(paramInputStream) << 24;
  }

  private void a(int paramInt)
  {
    if (this.b + paramInt < this.d);
    label258: 
    while (true)
    {
      return;
      if (Log.DEBUG)
        Log.v("Pruning old cache entries.", new Object[0]);
      long l1 = this.b;
      long l2 = SystemClock.elapsedRealtime();
      Iterator localIterator = this.a.entrySet().iterator();
      int i = 0;
      a locala;
      label120: int j;
      if (localIterator.hasNext())
      {
        locala = (a)((Map.Entry)localIterator.next()).getValue();
        if (c(locala.b).delete())
        {
          this.b -= locala.a;
          localIterator.remove();
          j = i + 1;
          if ((float)(this.b + paramInt) >= 0.9F * this.d)
            break label246;
        }
      }
      while (true)
      {
        if (!Log.DEBUG)
          break label258;
        Object[] arrayOfObject1 = new Object[3];
        arrayOfObject1[0] = Integer.valueOf(j);
        arrayOfObject1[1] = Long.valueOf(this.b - l1);
        arrayOfObject1[2] = Long.valueOf(SystemClock.elapsedRealtime() - l2);
        Log.v("pruned %d files, %d bytes, %d ms", arrayOfObject1);
        return;
        Object[] arrayOfObject2 = new Object[2];
        arrayOfObject2[0] = locala.b;
        arrayOfObject2[1] = d(locala.b);
        Log.d("Could not delete cache entry for key=%s, filename=%s", arrayOfObject2);
        break label120;
        label246: i = j;
        break;
        j = i;
      }
    }
  }

  static void a(OutputStream paramOutputStream, int paramInt)
    throws IOException
  {
    paramOutputStream.write(0xFF & paramInt >> 0);
    paramOutputStream.write(0xFF & paramInt >> 8);
    paramOutputStream.write(0xFF & paramInt >> 16);
    paramOutputStream.write(0xFF & paramInt >> 24);
  }

  static void a(OutputStream paramOutputStream, long paramLong)
    throws IOException
  {
    paramOutputStream.write((byte)(int)(paramLong >>> 0));
    paramOutputStream.write((byte)(int)(paramLong >>> 8));
    paramOutputStream.write((byte)(int)(paramLong >>> 16));
    paramOutputStream.write((byte)(int)(paramLong >>> 24));
    paramOutputStream.write((byte)(int)(paramLong >>> 32));
    paramOutputStream.write((byte)(int)(paramLong >>> 40));
    paramOutputStream.write((byte)(int)(paramLong >>> 48));
    paramOutputStream.write((byte)(int)(paramLong >>> 56));
  }

  static void a(OutputStream paramOutputStream, String paramString)
    throws IOException
  {
    byte[] arrayOfByte = paramString.getBytes("UTF-8");
    a(paramOutputStream, arrayOfByte.length);
    paramOutputStream.write(arrayOfByte, 0, arrayOfByte.length);
  }

  private void a(String paramString, a parama)
  {
    if (!this.a.containsKey(paramString));
    a locala;
    for (this.b += parama.a; ; this.b += parama.a - locala.a)
    {
      this.a.put(paramString, parama);
      return;
      locala = (a)this.a.get(paramString);
    }
  }

  static void a(Map<String, String> paramMap, OutputStream paramOutputStream)
    throws IOException
  {
    if (paramMap != null)
    {
      a(paramOutputStream, paramMap.size());
      Iterator localIterator = paramMap.entrySet().iterator();
      while (localIterator.hasNext())
      {
        Map.Entry localEntry = (Map.Entry)localIterator.next();
        a(paramOutputStream, (String)localEntry.getKey());
        a(paramOutputStream, (String)localEntry.getValue());
      }
    }
    a(paramOutputStream, 0);
  }

  private static byte[] a(InputStream paramInputStream, int paramInt)
    throws IOException
  {
    byte[] arrayOfByte = new byte[paramInt];
    int i = 0;
    while (i < paramInt)
    {
      int j = paramInputStream.read(arrayOfByte, i, paramInt - i);
      if (j == -1)
        break;
      i += j;
    }
    if (i != paramInt)
      throw new IOException("Expected " + paramInt + " bytes, read " + i + " bytes");
    return arrayOfByte;
  }

  static long b(InputStream paramInputStream)
    throws IOException
  {
    return 0L | (0xFF & e(paramInputStream)) << 0 | (0xFF & e(paramInputStream)) << 8 | (0xFF & e(paramInputStream)) << 16 | (0xFF & e(paramInputStream)) << 24 | (0xFF & e(paramInputStream)) << 32 | (0xFF & e(paramInputStream)) << 40 | (0xFF & e(paramInputStream)) << 48 | (0xFF & e(paramInputStream)) << 56;
  }

  static String c(InputStream paramInputStream)
    throws IOException
  {
    return new String(a(paramInputStream, (int)b(paramInputStream)), "UTF-8");
  }

  private String d(String paramString)
  {
    int i = paramString.length() / 2;
    String str = String.valueOf(paramString.substring(0, i).hashCode());
    return str + String.valueOf(paramString.substring(i).hashCode());
  }

  static Map<String, String> d(InputStream paramInputStream)
    throws IOException
  {
    int i = a(paramInputStream);
    if (i == 0);
    for (Object localObject = Collections.emptyMap(); ; localObject = new HashMap(i))
      for (int j = 0; j < i; j++)
        ((Map)localObject).put(c(paramInputStream).intern(), c(paramInputStream).intern());
    return localObject;
  }

  private static int e(InputStream paramInputStream)
    throws IOException
  {
    int i = paramInputStream.read();
    if (i == -1)
      throw new EOFException();
    return i;
  }

  private void e(String paramString)
  {
    a locala = (a)this.a.get(paramString);
    if (locala != null)
    {
      this.b -= locala.a;
      this.a.remove(paramString);
    }
  }

  // ERROR //
  public b.a a(String paramString)
  {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: getfield 38	com/taobao/munion/base/volley/a/d:a	Ljava/util/Map;
    //   6: aload_1
    //   7: invokeinterface 167 2 0
    //   12: checkcast 97	com/taobao/munion/base/volley/a/d$a
    //   15: astore_3
    //   16: aload_3
    //   17: ifnonnull +11 -> 28
    //   20: aconst_null
    //   21: astore 9
    //   23: aload_0
    //   24: monitorexit
    //   25: aload 9
    //   27: areturn
    //   28: aload_0
    //   29: aload_1
    //   30: invokevirtual 103	com/taobao/munion/base/volley/a/d:c	(Ljava/lang/String;)Ljava/io/File;
    //   33: astore 4
    //   35: new 263	com/taobao/munion/base/volley/a/d$b
    //   38: dup
    //   39: new 265	java/io/FileInputStream
    //   42: dup
    //   43: aload 4
    //   45: invokespecial 267	java/io/FileInputStream:<init>	(Ljava/io/File;)V
    //   48: aconst_null
    //   49: invokespecial 270	com/taobao/munion/base/volley/a/d$b:<init>	(Ljava/io/InputStream;Lcom/taobao/munion/base/volley/a/d$1;)V
    //   52: astore 5
    //   54: aload 5
    //   56: invokestatic 273	com/taobao/munion/base/volley/a/d$a:a	(Ljava/io/InputStream;)Lcom/taobao/munion/base/volley/a/d$a;
    //   59: pop
    //   60: aload_3
    //   61: aload 5
    //   63: aload 4
    //   65: invokevirtual 275	java/io/File:length	()J
    //   68: aload 5
    //   70: invokestatic 278	com/taobao/munion/base/volley/a/d$b:a	(Lcom/taobao/munion/base/volley/a/d$b;)I
    //   73: i2l
    //   74: lsub
    //   75: l2i
    //   76: invokestatic 217	com/taobao/munion/base/volley/a/d:a	(Ljava/io/InputStream;I)[B
    //   79: invokevirtual 281	com/taobao/munion/base/volley/a/d$a:a	([B)Lcom/taobao/munion/base/volley/b$a;
    //   82: astore 13
    //   84: aload 13
    //   86: astore 9
    //   88: aload 5
    //   90: ifnull -67 -> 23
    //   93: aload 5
    //   95: invokevirtual 284	com/taobao/munion/base/volley/a/d$b:close	()V
    //   98: goto -75 -> 23
    //   101: astore 14
    //   103: aconst_null
    //   104: astore 9
    //   106: goto -83 -> 23
    //   109: astore 6
    //   111: aconst_null
    //   112: astore 5
    //   114: iconst_2
    //   115: anewarray 4	java/lang/Object
    //   118: astore 10
    //   120: aload 10
    //   122: iconst_0
    //   123: aload 4
    //   125: invokevirtual 287	java/io/File:getAbsolutePath	()Ljava/lang/String;
    //   128: aastore
    //   129: aload 10
    //   131: iconst_1
    //   132: aload 6
    //   134: invokevirtual 288	java/io/IOException:toString	()Ljava/lang/String;
    //   137: aastore
    //   138: ldc_w 290
    //   141: aload 10
    //   143: invokestatic 133	com/taobao/munion/base/Log:d	(Ljava/lang/String;[Ljava/lang/Object;)V
    //   146: aload_0
    //   147: aload_1
    //   148: invokevirtual 292	com/taobao/munion/base/volley/a/d:b	(Ljava/lang/String;)V
    //   151: aload 5
    //   153: ifnull +8 -> 161
    //   156: aload 5
    //   158: invokevirtual 284	com/taobao/munion/base/volley/a/d$b:close	()V
    //   161: aconst_null
    //   162: astore 9
    //   164: goto -141 -> 23
    //   167: astore 11
    //   169: aconst_null
    //   170: astore 9
    //   172: goto -149 -> 23
    //   175: astore 7
    //   177: aconst_null
    //   178: astore 5
    //   180: aload 5
    //   182: ifnull +8 -> 190
    //   185: aload 5
    //   187: invokevirtual 284	com/taobao/munion/base/volley/a/d$b:close	()V
    //   190: aload 7
    //   192: athrow
    //   193: astore_2
    //   194: aload_0
    //   195: monitorexit
    //   196: aload_2
    //   197: athrow
    //   198: astore 8
    //   200: aconst_null
    //   201: astore 9
    //   203: goto -180 -> 23
    //   206: astore 7
    //   208: goto -28 -> 180
    //   211: astore 6
    //   213: goto -99 -> 114
    //
    // Exception table:
    //   from	to	target	type
    //   93	98	101	java/io/IOException
    //   35	54	109	java/io/IOException
    //   156	161	167	java/io/IOException
    //   35	54	175	finally
    //   2	16	193	finally
    //   28	35	193	finally
    //   93	98	193	finally
    //   156	161	193	finally
    //   185	190	193	finally
    //   190	193	193	finally
    //   185	190	198	java/io/IOException
    //   54	84	206	finally
    //   114	151	206	finally
    //   54	84	211	java/io/IOException
  }

  // ERROR //
  public void a()
  {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: getfield 42	com/taobao/munion/base/volley/a/d:c	Ljava/io/File;
    //   6: invokevirtual 295	java/io/File:exists	()Z
    //   9: ifne +41 -> 50
    //   12: aload_0
    //   13: getfield 42	com/taobao/munion/base/volley/a/d:c	Ljava/io/File;
    //   16: invokevirtual 298	java/io/File:mkdirs	()Z
    //   19: ifne +28 -> 47
    //   22: iconst_1
    //   23: anewarray 4	java/lang/Object
    //   26: astore 17
    //   28: aload 17
    //   30: iconst_0
    //   31: aload_0
    //   32: getfield 42	com/taobao/munion/base/volley/a/d:c	Ljava/io/File;
    //   35: invokevirtual 287	java/io/File:getAbsolutePath	()Ljava/lang/String;
    //   38: aastore
    //   39: ldc_w 300
    //   42: aload 17
    //   44: invokestatic 302	com/taobao/munion/base/Log:e	(Ljava/lang/String;[Ljava/lang/Object;)V
    //   47: aload_0
    //   48: monitorexit
    //   49: return
    //   50: aload_0
    //   51: getfield 42	com/taobao/munion/base/volley/a/d:c	Ljava/io/File;
    //   54: invokevirtual 306	java/io/File:listFiles	()[Ljava/io/File;
    //   57: astore_2
    //   58: aload_2
    //   59: ifnull -12 -> 47
    //   62: aload_2
    //   63: arraylength
    //   64: istore_3
    //   65: iconst_0
    //   66: istore 4
    //   68: iload 4
    //   70: iload_3
    //   71: if_icmpge -24 -> 47
    //   74: aload_2
    //   75: iload 4
    //   77: aaload
    //   78: astore 5
    //   80: aconst_null
    //   81: astore 6
    //   83: new 265	java/io/FileInputStream
    //   86: dup
    //   87: aload 5
    //   89: invokespecial 267	java/io/FileInputStream:<init>	(Ljava/io/File;)V
    //   92: astore 7
    //   94: aload 7
    //   96: invokestatic 273	com/taobao/munion/base/volley/a/d$a:a	(Ljava/io/InputStream;)Lcom/taobao/munion/base/volley/a/d$a;
    //   99: astore 14
    //   101: aload 14
    //   103: aload 5
    //   105: invokevirtual 275	java/io/File:length	()J
    //   108: putfield 110	com/taobao/munion/base/volley/a/d$a:a	J
    //   111: aload_0
    //   112: aload 14
    //   114: getfield 100	com/taobao/munion/base/volley/a/d$a:b	Ljava/lang/String;
    //   117: aload 14
    //   119: invokespecial 308	com/taobao/munion/base/volley/a/d:a	(Ljava/lang/String;Lcom/taobao/munion/base/volley/a/d$a;)V
    //   122: aload 7
    //   124: ifnull +8 -> 132
    //   127: aload 7
    //   129: invokevirtual 309	java/io/FileInputStream:close	()V
    //   132: iinc 4 1
    //   135: goto -67 -> 68
    //   138: astore 16
    //   140: aconst_null
    //   141: astore 7
    //   143: aload 5
    //   145: ifnull +9 -> 154
    //   148: aload 5
    //   150: invokevirtual 108	java/io/File:delete	()Z
    //   153: pop
    //   154: aload 7
    //   156: ifnull -24 -> 132
    //   159: aload 7
    //   161: invokevirtual 309	java/io/FileInputStream:close	()V
    //   164: goto -32 -> 132
    //   167: astore 9
    //   169: goto -37 -> 132
    //   172: astore 11
    //   174: aload 6
    //   176: ifnull +8 -> 184
    //   179: aload 6
    //   181: invokevirtual 309	java/io/FileInputStream:close	()V
    //   184: aload 11
    //   186: athrow
    //   187: astore_1
    //   188: aload_0
    //   189: monitorexit
    //   190: aload_1
    //   191: athrow
    //   192: astore 15
    //   194: goto -62 -> 132
    //   197: astore 12
    //   199: goto -15 -> 184
    //   202: astore 10
    //   204: aload 7
    //   206: astore 6
    //   208: aload 10
    //   210: astore 11
    //   212: goto -38 -> 174
    //   215: astore 8
    //   217: goto -74 -> 143
    //
    // Exception table:
    //   from	to	target	type
    //   83	94	138	java/io/IOException
    //   159	164	167	java/io/IOException
    //   83	94	172	finally
    //   2	47	187	finally
    //   50	58	187	finally
    //   62	65	187	finally
    //   74	80	187	finally
    //   127	132	187	finally
    //   159	164	187	finally
    //   179	184	187	finally
    //   184	187	187	finally
    //   127	132	192	java/io/IOException
    //   179	184	197	java/io/IOException
    //   94	122	202	finally
    //   148	154	202	finally
    //   94	122	215	java/io/IOException
  }

  public void a(String paramString, b.a parama)
  {
    try
    {
      a(parama.a.length);
      File localFile = c(paramString);
      try
      {
        FileOutputStream localFileOutputStream = new FileOutputStream(localFile);
        a locala = new a(paramString, parama);
        locala.a(localFileOutputStream);
        localFileOutputStream.write(parama.a);
        localFileOutputStream.close();
        a(paramString, locala);
        return;
      }
      catch (IOException localIOException)
      {
        while (true)
          if (!localFile.delete())
          {
            Object[] arrayOfObject = new Object[1];
            arrayOfObject[0] = localFile.getAbsolutePath();
            Log.d("Could not clean up file %s", arrayOfObject);
          }
      }
    }
    finally
    {
    }
  }

  public void a(String paramString, boolean paramBoolean)
  {
    try
    {
      b.a locala = a(paramString);
      if (locala != null)
      {
        locala.e = 0L;
        if (paramBoolean)
          locala.d = 0L;
        a(paramString, locala);
      }
      return;
    }
    finally
    {
    }
  }

  public void b()
  {
    int i = 0;
    try
    {
      File[] arrayOfFile = this.c.listFiles();
      if (arrayOfFile != null)
      {
        int j = arrayOfFile.length;
        while (i < j)
        {
          arrayOfFile[i].delete();
          i++;
        }
      }
      this.a.clear();
      this.b = 0L;
      Log.d("Cache cleared.", new Object[0]);
      return;
    }
    finally
    {
    }
  }

  public void b(String paramString)
  {
    try
    {
      boolean bool = c(paramString).delete();
      e(paramString);
      if (!bool)
      {
        Object[] arrayOfObject = new Object[2];
        arrayOfObject[0] = paramString;
        arrayOfObject[1] = d(paramString);
        Log.d("Could not delete cache entry for key=%s, filename=%s", arrayOfObject);
      }
      return;
    }
    finally
    {
    }
  }

  public File c(String paramString)
  {
    return new File(this.c, d(paramString));
  }

  static class a
  {
    public long a;
    public String b;
    public String c;
    public long d;
    public long e;
    public long f;
    public Map<String, String> g;

    private a()
    {
    }

    public a(String paramString, b.a parama)
    {
      this.b = paramString;
      this.a = parama.a.length;
      this.c = parama.b;
      this.d = parama.c;
      this.e = parama.d;
      this.f = parama.e;
      this.g = parama.f;
    }

    public static a a(InputStream paramInputStream)
      throws IOException
    {
      a locala = new a();
      if (d.a(paramInputStream) != 538051844)
        throw new IOException();
      locala.b = d.c(paramInputStream);
      locala.c = d.c(paramInputStream);
      if (locala.c.equals(""))
        locala.c = null;
      locala.d = d.b(paramInputStream);
      locala.e = d.b(paramInputStream);
      locala.f = d.b(paramInputStream);
      locala.g = d.d(paramInputStream);
      return locala;
    }

    public b.a a(byte[] paramArrayOfByte)
    {
      b.a locala = new b.a();
      locala.a = paramArrayOfByte;
      locala.b = this.c;
      locala.c = this.d;
      locala.d = this.e;
      locala.e = this.f;
      locala.f = this.g;
      return locala;
    }

    public boolean a(OutputStream paramOutputStream)
    {
      try
      {
        d.a(paramOutputStream, 538051844);
        d.a(paramOutputStream, this.b);
        if (this.c == null);
        for (String str = ""; ; str = this.c)
        {
          d.a(paramOutputStream, str);
          d.a(paramOutputStream, this.d);
          d.a(paramOutputStream, this.e);
          d.a(paramOutputStream, this.f);
          d.a(this.g, paramOutputStream);
          paramOutputStream.flush();
          return true;
        }
      }
      catch (IOException localIOException)
      {
        Object[] arrayOfObject = new Object[1];
        arrayOfObject[0] = localIOException.toString();
        Log.d("%s", arrayOfObject);
      }
      return false;
    }
  }

  private static class b extends FilterInputStream
  {
    private int a = 0;

    private b(InputStream paramInputStream)
    {
      super();
    }

    public int read()
      throws IOException
    {
      int i = super.read();
      if (i != -1)
        this.a = (1 + this.a);
      return i;
    }

    public int read(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
      throws IOException
    {
      int i = super.read(paramArrayOfByte, paramInt1, paramInt2);
      if (i != -1)
        this.a = (i + this.a);
      return i;
    }
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.taobao.munion.base.volley.a.d
 * JD-Core Version:    0.6.2
 */