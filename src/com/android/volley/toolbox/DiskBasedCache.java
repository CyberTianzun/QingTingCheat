package com.android.volley.toolbox;

import android.os.SystemClock;
import com.android.volley.Cache;
import com.android.volley.Cache.Entry;
import com.android.volley.VolleyLog;
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

public class DiskBasedCache
  implements Cache
{
  private static final int CACHE_MAGIC = 538051844;
  private static final int DEFAULT_DISK_USAGE_BYTES = 5242880;
  private static final float HYSTERESIS_FACTOR = 0.9F;
  private final Map<String, CacheHeader> mEntries = new LinkedHashMap(16, 0.75F, true);
  private final int mMaxCacheSizeInBytes;
  private final File mRootDirectory;
  private long mTotalSize = 0L;

  public DiskBasedCache(File paramFile)
  {
    this(paramFile, 5242880);
  }

  public DiskBasedCache(File paramFile, int paramInt)
  {
    this.mRootDirectory = paramFile;
    this.mMaxCacheSizeInBytes = paramInt;
  }

  private String getFilenameForKey(String paramString)
  {
    int i = paramString.length() / 2;
    return String.valueOf(paramString.substring(0, i).hashCode()) + String.valueOf(paramString.substring(i).hashCode());
  }

  private void pruneIfNeeded(int paramInt)
  {
    if (this.mTotalSize + paramInt < this.mMaxCacheSizeInBytes)
      return;
    if (VolleyLog.DEBUG)
      VolleyLog.v("Pruning old cache entries.", new Object[0]);
    long l1 = this.mTotalSize;
    int i = 0;
    long l2 = SystemClock.elapsedRealtime();
    Iterator localIterator = this.mEntries.entrySet().iterator();
    label61: label71: CacheHeader localCacheHeader;
    if (!localIterator.hasNext())
    {
      if (VolleyLog.DEBUG)
      {
        Object[] arrayOfObject2 = new Object[3];
        arrayOfObject2[0] = Integer.valueOf(i);
        arrayOfObject2[1] = Long.valueOf(this.mTotalSize - l1);
        arrayOfObject2[2] = Long.valueOf(SystemClock.elapsedRealtime() - l2);
        VolleyLog.v("pruned %d files, %d bytes, %d ms", arrayOfObject2);
      }
    }
    else
    {
      localCacheHeader = (CacheHeader)((Map.Entry)localIterator.next()).getValue();
      if (!getFileForKey(localCacheHeader.key).delete())
        break label208;
      this.mTotalSize -= localCacheHeader.size;
    }
    while (true)
    {
      localIterator.remove();
      i++;
      if ((float)(this.mTotalSize + paramInt) >= 0.9F * this.mMaxCacheSizeInBytes)
        break label61;
      break label71;
      break;
      label208: Object[] arrayOfObject1 = new Object[2];
      arrayOfObject1[0] = localCacheHeader.key;
      arrayOfObject1[1] = getFilenameForKey(localCacheHeader.key);
      VolleyLog.d("Could not delete cache entry for key=%s, filename=%s", arrayOfObject1);
    }
  }

  private void putEntry(String paramString, CacheHeader paramCacheHeader)
  {
    if (!this.mEntries.containsKey(paramString));
    CacheHeader localCacheHeader;
    for (this.mTotalSize += paramCacheHeader.size; ; this.mTotalSize += paramCacheHeader.size - localCacheHeader.size)
    {
      this.mEntries.put(paramString, paramCacheHeader);
      return;
      localCacheHeader = (CacheHeader)this.mEntries.get(paramString);
    }
  }

  private static int read(InputStream paramInputStream)
    throws IOException
  {
    int i = paramInputStream.read();
    if (i == -1)
      throw new EOFException();
    return i;
  }

  static int readInt(InputStream paramInputStream)
    throws IOException
  {
    return 0x0 | read(paramInputStream) << 0 | read(paramInputStream) << 8 | read(paramInputStream) << 16 | read(paramInputStream) << 24;
  }

  static long readLong(InputStream paramInputStream)
    throws IOException
  {
    return 0L | (0xFF & read(paramInputStream)) << 0 | (0xFF & read(paramInputStream)) << 8 | (0xFF & read(paramInputStream)) << 16 | (0xFF & read(paramInputStream)) << 24 | (0xFF & read(paramInputStream)) << 32 | (0xFF & read(paramInputStream)) << 40 | (0xFF & read(paramInputStream)) << 48 | (0xFF & read(paramInputStream)) << 56;
  }

  static String readString(InputStream paramInputStream)
    throws IOException
  {
    return new String(streamToBytes(paramInputStream, (int)readLong(paramInputStream)), "UTF-8");
  }

  static Map<String, String> readStringStringMap(InputStream paramInputStream)
    throws IOException
  {
    int i = readInt(paramInputStream);
    Object localObject;
    if (i == 0)
      localObject = Collections.emptyMap();
    for (int j = 0; ; j++)
    {
      if (j >= i)
      {
        return localObject;
        localObject = new HashMap(i);
        break;
      }
      ((Map)localObject).put(readString(paramInputStream).intern(), readString(paramInputStream).intern());
    }
  }

  private void removeEntry(String paramString)
  {
    CacheHeader localCacheHeader = (CacheHeader)this.mEntries.get(paramString);
    if (localCacheHeader != null)
    {
      this.mTotalSize -= localCacheHeader.size;
      this.mEntries.remove(paramString);
    }
  }

  private static byte[] streamToBytes(InputStream paramInputStream, int paramInt)
    throws IOException
  {
    byte[] arrayOfByte;
    while (true)
    {
      int i;
      int j;
      try
      {
        arrayOfByte = new byte[paramInt];
        i = 0;
        if (i < paramInt)
        {
          j = paramInputStream.read(arrayOfByte, i, paramInt - i);
          if (j != -1);
        }
        else
        {
          if (i == paramInt)
            break;
          throw new IOException("Expected " + paramInt + " bytes, read " + i + " bytes");
        }
      }
      catch (OutOfMemoryError localOutOfMemoryError)
      {
        throw new IOException();
      }
      i += j;
    }
    return arrayOfByte;
  }

  static void writeInt(OutputStream paramOutputStream, int paramInt)
    throws IOException
  {
    paramOutputStream.write(0xFF & paramInt >> 0);
    paramOutputStream.write(0xFF & paramInt >> 8);
    paramOutputStream.write(0xFF & paramInt >> 16);
    paramOutputStream.write(0xFF & paramInt >> 24);
  }

  static void writeLong(OutputStream paramOutputStream, long paramLong)
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

  static void writeString(OutputStream paramOutputStream, String paramString)
    throws IOException
  {
    byte[] arrayOfByte = paramString.getBytes("UTF-8");
    writeLong(paramOutputStream, arrayOfByte.length);
    paramOutputStream.write(arrayOfByte, 0, arrayOfByte.length);
  }

  static void writeStringStringMap(Map<String, String> paramMap, OutputStream paramOutputStream)
    throws IOException
  {
    if (paramMap != null)
    {
      writeInt(paramOutputStream, paramMap.size());
      Iterator localIterator = paramMap.entrySet().iterator();
      while (true)
      {
        if (!localIterator.hasNext())
          return;
        Map.Entry localEntry = (Map.Entry)localIterator.next();
        writeString(paramOutputStream, (String)localEntry.getKey());
        writeString(paramOutputStream, (String)localEntry.getValue());
      }
    }
    writeInt(paramOutputStream, 0);
  }

  public void clear()
  {
    while (true)
    {
      int i;
      int j;
      try
      {
        File[] arrayOfFile = this.mRootDirectory.listFiles();
        if (arrayOfFile != null)
        {
          i = arrayOfFile.length;
          j = 0;
        }
        else
        {
          this.mEntries.clear();
          this.mTotalSize = 0L;
          VolleyLog.d("Cache cleared.", new Object[0]);
          return;
          arrayOfFile[j].delete();
          j++;
        }
      }
      finally
      {
      }
      if (j < i);
    }
  }

  // ERROR //
  public Cache.Entry get(String paramString)
  {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: getfield 38	com/android/volley/toolbox/DiskBasedCache:mEntries	Ljava/util/Map;
    //   6: aload_1
    //   7: invokeinterface 182 2 0
    //   12: checkcast 142	com/android/volley/toolbox/DiskBasedCache$CacheHeader
    //   15: astore_3
    //   16: aconst_null
    //   17: astore 4
    //   19: aload_3
    //   20: ifnonnull +8 -> 28
    //   23: aload_0
    //   24: monitorexit
    //   25: aload 4
    //   27: areturn
    //   28: aload_0
    //   29: aload_1
    //   30: invokevirtual 150	com/android/volley/toolbox/DiskBasedCache:getFileForKey	(Ljava/lang/String;)Ljava/io/File;
    //   33: astore 5
    //   35: aconst_null
    //   36: astore 6
    //   38: new 294	com/android/volley/toolbox/DiskBasedCache$CountingInputStream
    //   41: dup
    //   42: new 296	java/io/FileInputStream
    //   45: dup
    //   46: aload 5
    //   48: invokespecial 298	java/io/FileInputStream:<init>	(Ljava/io/File;)V
    //   51: aconst_null
    //   52: invokespecial 301	com/android/volley/toolbox/DiskBasedCache$CountingInputStream:<init>	(Ljava/io/InputStream;Lcom/android/volley/toolbox/DiskBasedCache$CountingInputStream;)V
    //   55: astore 7
    //   57: aload 7
    //   59: invokestatic 305	com/android/volley/toolbox/DiskBasedCache$CacheHeader:readHeader	(Ljava/io/InputStream;)Lcom/android/volley/toolbox/DiskBasedCache$CacheHeader;
    //   62: pop
    //   63: aload_3
    //   64: aload 7
    //   66: aload 5
    //   68: invokevirtual 307	java/io/File:length	()J
    //   71: aload 7
    //   73: invokestatic 311	com/android/volley/toolbox/DiskBasedCache$CountingInputStream:access$1	(Lcom/android/volley/toolbox/DiskBasedCache$CountingInputStream;)I
    //   76: i2l
    //   77: lsub
    //   78: l2i
    //   79: invokestatic 208	com/android/volley/toolbox/DiskBasedCache:streamToBytes	(Ljava/io/InputStream;I)[B
    //   82: invokevirtual 315	com/android/volley/toolbox/DiskBasedCache$CacheHeader:toCacheEntry	([B)Lcom/android/volley/Cache$Entry;
    //   85: astore 14
    //   87: aload 7
    //   89: ifnull +8 -> 97
    //   92: aload 7
    //   94: invokevirtual 318	com/android/volley/toolbox/DiskBasedCache$CountingInputStream:close	()V
    //   97: aload 14
    //   99: astore 4
    //   101: goto -78 -> 23
    //   104: astore 15
    //   106: aconst_null
    //   107: astore 4
    //   109: goto -86 -> 23
    //   112: astore 8
    //   114: iconst_2
    //   115: anewarray 4	java/lang/Object
    //   118: astore 11
    //   120: aload 11
    //   122: iconst_0
    //   123: aload 5
    //   125: invokevirtual 321	java/io/File:getAbsolutePath	()Ljava/lang/String;
    //   128: aastore
    //   129: aload 11
    //   131: iconst_1
    //   132: aload 8
    //   134: invokevirtual 322	java/io/IOException:toString	()Ljava/lang/String;
    //   137: aastore
    //   138: ldc_w 324
    //   141: aload 11
    //   143: invokestatic 168	com/android/volley/VolleyLog:d	(Ljava/lang/String;[Ljava/lang/Object;)V
    //   146: aload_0
    //   147: aload_1
    //   148: invokevirtual 326	com/android/volley/toolbox/DiskBasedCache:remove	(Ljava/lang/String;)V
    //   151: aconst_null
    //   152: astore 4
    //   154: aload 6
    //   156: ifnull -133 -> 23
    //   159: aload 6
    //   161: invokevirtual 318	com/android/volley/toolbox/DiskBasedCache$CountingInputStream:close	()V
    //   164: aconst_null
    //   165: astore 4
    //   167: goto -144 -> 23
    //   170: astore 12
    //   172: aconst_null
    //   173: astore 4
    //   175: goto -152 -> 23
    //   178: astore 9
    //   180: aload 6
    //   182: ifnull +8 -> 190
    //   185: aload 6
    //   187: invokevirtual 318	com/android/volley/toolbox/DiskBasedCache$CountingInputStream:close	()V
    //   190: aload 9
    //   192: athrow
    //   193: astore_2
    //   194: aload_0
    //   195: monitorexit
    //   196: aload_2
    //   197: athrow
    //   198: astore 10
    //   200: aconst_null
    //   201: astore 4
    //   203: goto -180 -> 23
    //   206: astore 9
    //   208: aload 7
    //   210: astore 6
    //   212: goto -32 -> 180
    //   215: astore 8
    //   217: aload 7
    //   219: astore 6
    //   221: goto -107 -> 114
    //
    // Exception table:
    //   from	to	target	type
    //   92	97	104	java/io/IOException
    //   38	57	112	java/io/IOException
    //   159	164	170	java/io/IOException
    //   38	57	178	finally
    //   114	151	178	finally
    //   2	16	193	finally
    //   28	35	193	finally
    //   92	97	193	finally
    //   159	164	193	finally
    //   185	190	193	finally
    //   190	193	193	finally
    //   185	190	198	java/io/IOException
    //   57	87	206	finally
    //   57	87	215	java/io/IOException
  }

  public File getFileForKey(String paramString)
  {
    return new File(this.mRootDirectory, getFilenameForKey(paramString));
  }

  // ERROR //
  public void initialize()
  {
    // Byte code:
    //   0: iconst_0
    //   1: istore_1
    //   2: aload_0
    //   3: monitorenter
    //   4: aload_0
    //   5: getfield 42	com/android/volley/toolbox/DiskBasedCache:mRootDirectory	Ljava/io/File;
    //   8: invokevirtual 333	java/io/File:exists	()Z
    //   11: ifne +41 -> 52
    //   14: aload_0
    //   15: getfield 42	com/android/volley/toolbox/DiskBasedCache:mRootDirectory	Ljava/io/File;
    //   18: invokevirtual 336	java/io/File:mkdirs	()Z
    //   21: ifne +28 -> 49
    //   24: iconst_1
    //   25: anewarray 4	java/lang/Object
    //   28: astore 16
    //   30: aload 16
    //   32: iconst_0
    //   33: aload_0
    //   34: getfield 42	com/android/volley/toolbox/DiskBasedCache:mRootDirectory	Ljava/io/File;
    //   37: invokevirtual 321	java/io/File:getAbsolutePath	()Ljava/lang/String;
    //   40: aastore
    //   41: ldc_w 338
    //   44: aload 16
    //   46: invokestatic 341	com/android/volley/VolleyLog:e	(Ljava/lang/String;[Ljava/lang/Object;)V
    //   49: aload_0
    //   50: monitorexit
    //   51: return
    //   52: aload_0
    //   53: getfield 42	com/android/volley/toolbox/DiskBasedCache:mRootDirectory	Ljava/io/File;
    //   56: invokevirtual 287	java/io/File:listFiles	()[Ljava/io/File;
    //   59: astore_3
    //   60: aload_3
    //   61: ifnull -12 -> 49
    //   64: aload_3
    //   65: arraylength
    //   66: istore 4
    //   68: iload_1
    //   69: iload 4
    //   71: if_icmpge -22 -> 49
    //   74: aload_3
    //   75: iload_1
    //   76: aaload
    //   77: astore 5
    //   79: aconst_null
    //   80: astore 6
    //   82: new 296	java/io/FileInputStream
    //   85: dup
    //   86: aload 5
    //   88: invokespecial 298	java/io/FileInputStream:<init>	(Ljava/io/File;)V
    //   91: astore 7
    //   93: aload 7
    //   95: invokestatic 305	com/android/volley/toolbox/DiskBasedCache$CacheHeader:readHeader	(Ljava/io/InputStream;)Lcom/android/volley/toolbox/DiskBasedCache$CacheHeader;
    //   98: astore 13
    //   100: aload 13
    //   102: aload 5
    //   104: invokevirtual 307	java/io/File:length	()J
    //   107: putfield 158	com/android/volley/toolbox/DiskBasedCache$CacheHeader:size	J
    //   110: aload_0
    //   111: aload 13
    //   113: getfield 146	com/android/volley/toolbox/DiskBasedCache$CacheHeader:key	Ljava/lang/String;
    //   116: aload 13
    //   118: invokespecial 343	com/android/volley/toolbox/DiskBasedCache:putEntry	(Ljava/lang/String;Lcom/android/volley/toolbox/DiskBasedCache$CacheHeader;)V
    //   121: aload 7
    //   123: ifnull +93 -> 216
    //   126: aload 7
    //   128: invokevirtual 344	java/io/FileInputStream:close	()V
    //   131: iinc 1 1
    //   134: goto -66 -> 68
    //   137: astore 15
    //   139: aload 5
    //   141: ifnull +9 -> 150
    //   144: aload 5
    //   146: invokevirtual 155	java/io/File:delete	()Z
    //   149: pop
    //   150: aload 6
    //   152: ifnull -21 -> 131
    //   155: aload 6
    //   157: invokevirtual 344	java/io/FileInputStream:close	()V
    //   160: goto -29 -> 131
    //   163: astore 9
    //   165: goto -34 -> 131
    //   168: astore 10
    //   170: aload 6
    //   172: ifnull +8 -> 180
    //   175: aload 6
    //   177: invokevirtual 344	java/io/FileInputStream:close	()V
    //   180: aload 10
    //   182: athrow
    //   183: astore_2
    //   184: aload_0
    //   185: monitorexit
    //   186: aload_2
    //   187: athrow
    //   188: astore 14
    //   190: goto -59 -> 131
    //   193: astore 11
    //   195: goto -15 -> 180
    //   198: astore 10
    //   200: aload 7
    //   202: astore 6
    //   204: goto -34 -> 170
    //   207: astore 8
    //   209: aload 7
    //   211: astore 6
    //   213: goto -74 -> 139
    //   216: goto -85 -> 131
    //
    // Exception table:
    //   from	to	target	type
    //   82	93	137	java/io/IOException
    //   155	160	163	java/io/IOException
    //   82	93	168	finally
    //   144	150	168	finally
    //   4	49	183	finally
    //   52	60	183	finally
    //   64	68	183	finally
    //   74	79	183	finally
    //   126	131	183	finally
    //   155	160	183	finally
    //   175	180	183	finally
    //   180	183	183	finally
    //   126	131	188	java/io/IOException
    //   175	180	193	java/io/IOException
    //   93	121	198	finally
    //   93	121	207	java/io/IOException
  }

  public void invalidate(String paramString, boolean paramBoolean)
  {
    try
    {
      Cache.Entry localEntry = get(paramString);
      if (localEntry != null)
      {
        localEntry.softTtl = 0L;
        if (paramBoolean)
          localEntry.ttl = 0L;
        put(paramString, localEntry);
      }
      return;
    }
    finally
    {
    }
  }

  public void put(String paramString, Cache.Entry paramEntry)
  {
    try
    {
      pruneIfNeeded(paramEntry.data.length);
      File localFile = getFileForKey(paramString);
      try
      {
        FileOutputStream localFileOutputStream = new FileOutputStream(localFile);
        CacheHeader localCacheHeader = new CacheHeader(paramString, paramEntry);
        localCacheHeader.writeHeader(localFileOutputStream);
        localFileOutputStream.write(paramEntry.data);
        localFileOutputStream.close();
        putEntry(paramString, localCacheHeader);
        return;
      }
      catch (IOException localIOException)
      {
        while (true)
          if (!localFile.delete())
          {
            Object[] arrayOfObject = new Object[1];
            arrayOfObject[0] = localFile.getAbsolutePath();
            VolleyLog.d("Could not clean up file %s", arrayOfObject);
          }
      }
    }
    finally
    {
    }
  }

  public void remove(String paramString)
  {
    try
    {
      boolean bool = getFileForKey(paramString).delete();
      removeEntry(paramString);
      if (!bool)
      {
        Object[] arrayOfObject = new Object[2];
        arrayOfObject[0] = paramString;
        arrayOfObject[1] = getFilenameForKey(paramString);
        VolleyLog.d("Could not delete cache entry for key=%s, filename=%s", arrayOfObject);
      }
      return;
    }
    finally
    {
    }
  }

  static class CacheHeader
  {
    public String etag;
    public String key;
    public Map<String, String> responseHeaders;
    public long serverDate;
    public long size;
    public long softTtl;
    public long ttl;

    private CacheHeader()
    {
    }

    public CacheHeader(String paramString, Cache.Entry paramEntry)
    {
      this.key = paramString;
      this.size = paramEntry.data.length;
      this.etag = paramEntry.etag;
      this.serverDate = paramEntry.serverDate;
      this.ttl = paramEntry.ttl;
      this.softTtl = paramEntry.softTtl;
      this.responseHeaders = paramEntry.responseHeaders;
    }

    public static CacheHeader readHeader(InputStream paramInputStream)
      throws IOException
    {
      CacheHeader localCacheHeader = new CacheHeader();
      if (DiskBasedCache.readInt(paramInputStream) != 538051844)
        throw new IOException();
      localCacheHeader.key = DiskBasedCache.readString(paramInputStream);
      localCacheHeader.etag = DiskBasedCache.readString(paramInputStream);
      if (localCacheHeader.etag.equals(""))
        localCacheHeader.etag = null;
      localCacheHeader.serverDate = DiskBasedCache.readLong(paramInputStream);
      localCacheHeader.ttl = DiskBasedCache.readLong(paramInputStream);
      localCacheHeader.softTtl = DiskBasedCache.readLong(paramInputStream);
      localCacheHeader.responseHeaders = DiskBasedCache.readStringStringMap(paramInputStream);
      return localCacheHeader;
    }

    public Cache.Entry toCacheEntry(byte[] paramArrayOfByte)
    {
      Cache.Entry localEntry = new Cache.Entry();
      localEntry.data = paramArrayOfByte;
      localEntry.etag = this.etag;
      localEntry.serverDate = this.serverDate;
      localEntry.ttl = this.ttl;
      localEntry.softTtl = this.softTtl;
      localEntry.responseHeaders = this.responseHeaders;
      return localEntry;
    }

    public boolean writeHeader(OutputStream paramOutputStream)
    {
      try
      {
        DiskBasedCache.writeInt(paramOutputStream, 538051844);
        DiskBasedCache.writeString(paramOutputStream, this.key);
        if (this.etag == null);
        for (String str = ""; ; str = this.etag)
        {
          DiskBasedCache.writeString(paramOutputStream, str);
          DiskBasedCache.writeLong(paramOutputStream, this.serverDate);
          DiskBasedCache.writeLong(paramOutputStream, this.ttl);
          DiskBasedCache.writeLong(paramOutputStream, this.softTtl);
          DiskBasedCache.writeStringStringMap(this.responseHeaders, paramOutputStream);
          paramOutputStream.flush();
          return true;
        }
      }
      catch (IOException localIOException)
      {
        Object[] arrayOfObject = new Object[1];
        arrayOfObject[0] = localIOException.toString();
        VolleyLog.d("%s", arrayOfObject);
      }
      return false;
    }
  }

  private static class CountingInputStream extends FilterInputStream
  {
    private int bytesRead = 0;

    private CountingInputStream(InputStream paramInputStream)
    {
      super();
    }

    public int read()
      throws IOException
    {
      int i = super.read();
      if (i != -1)
        this.bytesRead = (1 + this.bytesRead);
      return i;
    }

    public int read(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
      throws IOException
    {
      int i = super.read(paramArrayOfByte, paramInt1, paramInt2);
      if (i != -1)
        this.bytesRead = (i + this.bytesRead);
      return i;
    }
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.android.volley.toolbox.DiskBasedCache
 * JD-Core Version:    0.6.2
 */