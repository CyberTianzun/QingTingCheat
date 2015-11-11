package com.taobao.munion.base.caches;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class g
{
  public static final int a = 1;
  public static final int b = 2;
  public static final int c = 3;
  public static final int d = 4;
  private static String e = "FileCache";
  private static final String f = "wv_web_info.dat";
  private String g;
  private String h;
  private boolean i;
  private boolean j;
  private Map<String, i> k = Collections.synchronizedMap(new LinkedHashMap());
  private RandomAccessFile l;
  private FileChannel m;
  private boolean n = true;
  private int o = 100;
  private FileLock p;

  public g(String paramString1, String paramString2, int paramInt, boolean paramBoolean)
  {
    this.g = paramString1;
    this.h = paramString2;
    this.o = paramInt;
    this.i = paramBoolean;
    this.j = false;
  }

  private void a(int paramInt)
  {
    if (this.k.size() > paramInt)
      f();
  }

  private void f()
  {
    ArrayList localArrayList = new ArrayList();
    Set localSet = this.k.entrySet();
    int i1 = this.k.size();
    Iterator localIterator1 = localSet.iterator();
    for (int i2 = i1; localIterator1.hasNext(); i2--)
    {
      Map.Entry localEntry = (Map.Entry)localIterator1.next();
      if (i2 < this.o)
        break;
      i locali = (i)localEntry.getValue();
      if (locali != null)
        localArrayList.add(locali);
    }
    Iterator localIterator2 = localArrayList.iterator();
    while (localIterator2.hasNext())
      c(((i)localIterator2.next()).b());
  }

  private boolean g()
  {
    int i1 = 0;
    System.currentTimeMillis();
    try
    {
      ByteBuffer localByteBuffer2 = ByteBuffer.allocate((int)this.m.size());
      this.m.read(localByteBuffer2);
      byte[] arrayOfByte2 = localByteBuffer2.array();
      arrayOfByte1 = arrayOfByte2;
      System.currentTimeMillis();
      ByteArrayOutputStream localByteArrayOutputStream;
      if (arrayOfByte1 != null)
      {
        int i2 = 128;
        localByteArrayOutputStream = new ByteArrayOutputStream();
        int i3 = 0;
        if (i2 < arrayOfByte1.length)
        {
          if (arrayOfByte1[i2] == 10)
          {
            i locali = j.a(arrayOfByte1, i1, i2 - i1);
            if (locali == null)
              break label184;
            String str = locali.b();
            if (this.k.containsKey(str))
              break label178;
            locali.b(localByteArrayOutputStream.size());
            this.k.put(str, locali);
            localByteArrayOutputStream.write(arrayOfByte1, i1, 1 + (i2 - i1));
          }
          while (true)
          {
            i1 = i2 + 1;
            i2 += 128;
            i2++;
            break;
            label178: i3 = 1;
            continue;
            label184: i3 = 1;
          }
        }
        System.currentTimeMillis();
        if (i3 == 0);
      }
      try
      {
        this.m.truncate(0L);
        this.m.position(0L);
        ByteBuffer localByteBuffer1 = ByteBuffer.wrap(localByteArrayOutputStream.toByteArray());
        localByteBuffer1.position(0);
        this.m.write(localByteBuffer1);
        try
        {
          label244: localByteArrayOutputStream.close();
          return true;
        }
        catch (IOException localIOException2)
        {
          localIOException2.printStackTrace();
          return true;
        }
        return false;
      }
      catch (IOException localIOException3)
      {
        break label244;
      }
    }
    catch (IOException localIOException1)
    {
      while (true)
        byte[] arrayOfByte1 = null;
    }
  }

  public i a(String paramString)
  {
    i locali;
    if (!this.j)
      locali = null;
    do
    {
      return locali;
      locali = (i)this.k.get(paramString);
      if (locali == null)
        return null;
    }
    while (new File(this.g, paramString).exists());
    j.a(3, locali, this.m);
    this.k.remove(paramString);
    return null;
  }

  public String a()
  {
    return this.g;
  }

  public void a(i parami)
  {
    String str;
    if ((this.j) && (parami != null))
    {
      str = parami.b();
      if (str != null)
        break label21;
    }
    label21: i locali1;
    do
    {
      return;
      locali1 = (i)this.k.get(str);
    }
    while (locali1 == null);
    parami.b(locali1.e());
    i locali2 = j.a(2, parami, this.m);
    this.k.put(str, locali2);
  }

  public boolean a(i parami, ByteBuffer paramByteBuffer)
  {
    String str;
    if (parami != null)
    {
      str = parami.b();
      if (str != null)
        break label15;
    }
    while (true)
    {
      return false;
      label15: if (!this.j)
        continue;
      File localFile = new File(this.g, str);
      try
      {
        boolean bool3 = f.a(localFile, paramByteBuffer);
        bool1 = bool3;
        if (!bool1)
          continue;
      }
      catch (p localp1)
      {
        try
        {
          boolean bool1;
          i locali1 = (i)this.k.get(str);
          if (locali1 != null)
          {
            parami.b(locali1.e());
            i locali2 = j.a(2, parami, this.m);
            this.k.put(str, locali2);
          }
          while (true)
          {
            while (true)
            {
              Iterator localIterator2;
              return true;
              localp1 = localp1;
              if (this.n)
              {
                e();
                try
                {
                  boolean bool2 = f.a(localFile, paramByteBuffer);
                  bool1 = bool2;
                }
                catch (p localp2)
                {
                  localp2.printStackTrace();
                }
              }
            }
            bool1 = false;
            break;
            i locali3 = j.a(4, parami, this.m);
            this.k.put(str, locali3);
          }
        }
        finally
        {
          if (this.k.size() > this.o)
          {
            Iterator localIterator1 = this.k.entrySet().iterator();
            if (localIterator1.hasNext())
              c(((i)((Map.Entry)localIterator1.next()).getValue()).b());
          }
        }
      }
    }
  }

  public boolean b()
  {
    return this.i;
  }

  public byte[] b(String paramString)
  {
    if (!this.j)
      return null;
    i locali1 = (i)this.k.get(paramString);
    if (locali1 == null)
      return null;
    this.k.remove(paramString);
    i locali2 = j.a(1, locali1, this.m);
    this.k.put(paramString, locali2);
    return f.a(new File(this.g, paramString));
  }

  public int c()
  {
    if (this.j)
      return this.k.size();
    return 0;
  }

  public boolean c(String paramString)
  {
    boolean bool1 = this.j;
    boolean bool2 = false;
    File localFile;
    if (bool1)
    {
      bool2 = false;
      if (paramString != null)
      {
        System.currentTimeMillis();
        localFile = new File(this.g, paramString);
        if (!localFile.isFile())
          break label112;
      }
    }
    label112: for (boolean bool3 = localFile.delete(); ; bool3 = false)
    {
      if ((bool3) || (!localFile.exists()))
      {
        i locali = (i)this.k.get(paramString);
        if (locali != null)
        {
          j.a(3, locali, this.m);
          this.k.remove(paramString);
          bool2 = true;
          return bool2;
        }
      }
      return bool3;
    }
  }

  // ERROR //
  public boolean d()
  {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: getfield 67	com/taobao/munion/base/caches/g:j	Z
    //   6: ifne +210 -> 216
    //   9: new 200	java/io/File
    //   12: dup
    //   13: aload_0
    //   14: getfield 63	com/taobao/munion/base/caches/g:h	Ljava/lang/String;
    //   17: ldc 18
    //   19: invokespecial 203	java/io/File:<init>	(Ljava/lang/String;Ljava/lang/String;)V
    //   22: astore_2
    //   23: aload_2
    //   24: invokevirtual 206	java/io/File:exists	()Z
    //   27: ifne +23 -> 50
    //   30: new 200	java/io/File
    //   33: dup
    //   34: aload_0
    //   35: getfield 63	com/taobao/munion/base/caches/g:h	Ljava/lang/String;
    //   38: invokespecial 241	java/io/File:<init>	(Ljava/lang/String;)V
    //   41: invokevirtual 244	java/io/File:mkdirs	()Z
    //   44: pop
    //   45: aload_2
    //   46: invokevirtual 247	java/io/File:createNewFile	()Z
    //   49: pop
    //   50: new 200	java/io/File
    //   53: dup
    //   54: aload_0
    //   55: getfield 61	com/taobao/munion/base/caches/g:g	Ljava/lang/String;
    //   58: invokespecial 241	java/io/File:<init>	(Ljava/lang/String;)V
    //   61: invokevirtual 244	java/io/File:mkdirs	()Z
    //   64: pop
    //   65: aload_0
    //   66: new 249	java/io/RandomAccessFile
    //   69: dup
    //   70: aload_2
    //   71: invokevirtual 252	java/io/File:getAbsolutePath	()Ljava/lang/String;
    //   74: ldc 254
    //   76: invokespecial 255	java/io/RandomAccessFile:<init>	(Ljava/lang/String;Ljava/lang/String;)V
    //   79: putfield 257	com/taobao/munion/base/caches/g:l	Ljava/io/RandomAccessFile;
    //   82: aload_0
    //   83: getfield 127	com/taobao/munion/base/caches/g:m	Ljava/nio/channels/FileChannel;
    //   86: ifnonnull +14 -> 100
    //   89: aload_0
    //   90: aload_0
    //   91: getfield 257	com/taobao/munion/base/caches/g:l	Ljava/io/RandomAccessFile;
    //   94: invokevirtual 261	java/io/RandomAccessFile:getChannel	()Ljava/nio/channels/FileChannel;
    //   97: putfield 127	com/taobao/munion/base/caches/g:m	Ljava/nio/channels/FileChannel;
    //   100: aload_0
    //   101: aload_0
    //   102: getfield 127	com/taobao/munion/base/caches/g:m	Ljava/nio/channels/FileChannel;
    //   105: invokevirtual 265	java/nio/channels/FileChannel:tryLock	()Ljava/nio/channels/FileLock;
    //   108: putfield 267	com/taobao/munion/base/caches/g:p	Ljava/nio/channels/FileLock;
    //   111: aload_0
    //   112: getfield 267	com/taobao/munion/base/caches/g:p	Ljava/nio/channels/FileLock;
    //   115: astore 6
    //   117: iconst_0
    //   118: istore 5
    //   120: aload 6
    //   122: ifnonnull +8 -> 130
    //   125: aload_0
    //   126: monitorexit
    //   127: iload 5
    //   129: ireturn
    //   130: invokestatic 125	java/lang/System:currentTimeMillis	()J
    //   133: pop2
    //   134: aload_0
    //   135: invokespecial 269	com/taobao/munion/base/caches/g:g	()Z
    //   138: ifne +48 -> 186
    //   141: aload_0
    //   142: getfield 267	com/taobao/munion/base/caches/g:p	Ljava/nio/channels/FileLock;
    //   145: astore 10
    //   147: iconst_0
    //   148: istore 5
    //   150: aload 10
    //   152: ifnull -27 -> 125
    //   155: aload_0
    //   156: getfield 267	com/taobao/munion/base/caches/g:p	Ljava/nio/channels/FileLock;
    //   159: invokevirtual 274	java/nio/channels/FileLock:release	()V
    //   162: iconst_0
    //   163: istore 5
    //   165: goto -40 -> 125
    //   168: astore 11
    //   170: aload 11
    //   172: invokevirtual 193	java/io/IOException:printStackTrace	()V
    //   175: iconst_0
    //   176: istore 5
    //   178: goto -53 -> 125
    //   181: astore_1
    //   182: aload_0
    //   183: monitorexit
    //   184: aload_1
    //   185: athrow
    //   186: aload_0
    //   187: iconst_1
    //   188: putfield 67	com/taobao/munion/base/caches/g:j	Z
    //   191: aload_0
    //   192: aload_0
    //   193: getfield 59	com/taobao/munion/base/caches/g:o	I
    //   196: invokespecial 276	com/taobao/munion/base/caches/g:a	(I)V
    //   199: aload_0
    //   200: getfield 55	com/taobao/munion/base/caches/g:k	Ljava/util/Map;
    //   203: invokeinterface 74 1 0
    //   208: ifne +8 -> 216
    //   211: aload_0
    //   212: invokevirtual 225	com/taobao/munion/base/caches/g:e	()Z
    //   215: pop
    //   216: iconst_1
    //   217: istore 5
    //   219: goto -94 -> 125
    //   222: astore 13
    //   224: iconst_0
    //   225: istore 5
    //   227: goto -102 -> 125
    //   230: astore 4
    //   232: iconst_0
    //   233: istore 5
    //   235: goto -110 -> 125
    //
    // Exception table:
    //   from	to	target	type
    //   155	162	168	java/io/IOException
    //   2	45	181	finally
    //   45	50	181	finally
    //   50	65	181	finally
    //   65	100	181	finally
    //   100	117	181	finally
    //   130	147	181	finally
    //   155	162	181	finally
    //   170	175	181	finally
    //   186	216	181	finally
    //   45	50	222	java/io/IOException
    //   65	100	230	java/lang/Exception
    //   100	117	230	java/lang/Exception
  }

  public boolean e()
  {
    boolean bool1 = this.j;
    boolean bool2 = false;
    if (bool1)
    {
      String[] arrayOfString = new File(this.g).list();
      bool2 = false;
      if (arrayOfString != null)
      {
        int i1 = arrayOfString.length;
        bool2 = true;
        int i2 = 0;
        while (i2 < i1)
        {
          boolean bool3 = bool2 & c(arrayOfString[i2]);
          i2++;
          bool2 = bool3;
        }
      }
    }
    return bool2;
  }

  protected void finalize()
    throws Throwable
  {
    if (this.p != null)
      this.p.release();
    if (this.l != null);
    try
    {
      this.l.close();
      if (this.m == null);
    }
    catch (Exception localException2)
    {
      try
      {
        this.m.close();
        super.finalize();
        return;
        localException2 = localException2;
        localException2.printStackTrace();
      }
      catch (Exception localException1)
      {
        while (true)
          localException1.printStackTrace();
      }
    }
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.taobao.munion.base.caches.g
 * JD-Core Version:    0.6.2
 */