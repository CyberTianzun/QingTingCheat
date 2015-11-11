package fm.qingting.async.http.libcore;

import java.io.BufferedWriter;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class DiskLruCache
  implements Closeable
{
  static final long ANY_SEQUENCE_NUMBER = -1L;
  private static final String CLEAN = "CLEAN";
  private static final String DIRTY = "DIRTY";
  static final String JOURNAL_FILE = "journal";
  static final String JOURNAL_FILE_BKP = "journal.bkp";
  static final String JOURNAL_FILE_TMP = "journal.tmp";
  static final Pattern LEGAL_KEY_PATTERN = Pattern.compile("[a-z0-9_-]{1,64}");
  static final String MAGIC = "libcore.io.DiskLruCache";
  private static final OutputStream NULL_OUTPUT_STREAM = new OutputStream()
  {
    public void write(int paramAnonymousInt)
      throws IOException
    {
    }
  };
  private static final String READ = "READ";
  private static final String REMOVE = "REMOVE";
  static final String VERSION_1 = "1";
  private final int appVersion;
  private final Callable<Void> cleanupCallable = new Callable()
  {
    public Void call()
      throws Exception
    {
      synchronized (DiskLruCache.this)
      {
        if (DiskLruCache.this.journalWriter == null)
          return null;
        DiskLruCache.this.trimToSize();
        if (DiskLruCache.this.journalRebuildRequired())
        {
          DiskLruCache.this.rebuildJournal();
          DiskLruCache.access$402(DiskLruCache.this, 0);
        }
        return null;
      }
    }
  };
  private final File directory;
  final ThreadPoolExecutor executorService = new ThreadPoolExecutor(0, 1, 60L, TimeUnit.SECONDS, new LinkedBlockingQueue());
  private final File journalFile;
  private final File journalFileBkp;
  private final File journalFileTmp;
  private Writer journalWriter;
  private final LinkedHashMap<String, Entry> lruEntries = new LinkedHashMap(0, 0.75F, true);
  private long maxSize;
  private long nextSequenceNumber = 0L;
  private int redundantOpCount;
  private long size = 0L;
  private final int valueCount;

  private DiskLruCache(File paramFile, int paramInt1, int paramInt2, long paramLong)
  {
    this.directory = paramFile;
    this.appVersion = paramInt1;
    this.journalFile = new File(paramFile, "journal");
    this.journalFileTmp = new File(paramFile, "journal.tmp");
    this.journalFileBkp = new File(paramFile, "journal.bkp");
    this.valueCount = paramInt2;
    this.maxSize = paramLong;
  }

  private void checkNotClosed()
  {
    if (this.journalWriter == null)
      throw new IllegalStateException("cache is closed");
  }

  private void completeEdit(Editor paramEditor, boolean paramBoolean)
    throws IOException
  {
    Entry localEntry;
    try
    {
      localEntry = paramEditor.entry;
      if (localEntry.currentEditor != paramEditor)
        throw new IllegalStateException();
    }
    finally
    {
    }
    int i = 0;
    if (paramBoolean)
    {
      boolean bool = localEntry.readable;
      i = 0;
      if (!bool)
        for (int j = 0; ; j++)
        {
          int k = this.valueCount;
          i = 0;
          if (j >= k)
            break;
          if (paramEditor.written[j] == 0)
          {
            paramEditor.abort();
            throw new IllegalStateException("Newly created entry didn't create value for index " + j);
          }
          if (!localEntry.getDirtyFile(j).exists())
          {
            paramEditor.abort();
            return;
          }
        }
    }
    while (true)
    {
      if (i < this.valueCount)
      {
        File localFile1 = localEntry.getDirtyFile(i);
        if (paramBoolean)
        {
          if (localFile1.exists())
          {
            File localFile2 = localEntry.getCleanFile(i);
            localFile1.renameTo(localFile2);
            long l2 = localEntry.lengths[i];
            long l3 = localFile2.length();
            localEntry.lengths[i] = l3;
            this.size = (l3 + (this.size - l2));
          }
        }
        else
          deleteIfExists(localFile1);
      }
      else
      {
        this.redundantOpCount = (1 + this.redundantOpCount);
        Entry.access$702(localEntry, null);
        if ((paramBoolean | localEntry.readable))
        {
          Entry.access$602(localEntry, true);
          this.journalWriter.write("CLEAN " + localEntry.key + localEntry.getLengths() + '\n');
          if (paramBoolean)
          {
            long l1 = this.nextSequenceNumber;
            this.nextSequenceNumber = (1L + l1);
            Entry.access$1202(localEntry, l1);
          }
        }
        while (true)
        {
          if ((this.size <= this.maxSize) && (!journalRebuildRequired()))
            break label426;
          this.executorService.submit(this.cleanupCallable);
          break;
          this.lruEntries.remove(localEntry.key);
          this.journalWriter.write("REMOVE " + localEntry.key + '\n');
        }
        label426: break;
      }
      i++;
    }
  }

  private static void deleteIfExists(File paramFile)
    throws IOException
  {
    if ((paramFile.exists()) && (!paramFile.delete()))
      throw new IOException();
  }

  private Editor edit(String paramString, long paramLong)
    throws IOException
  {
    while (true)
    {
      Entry localEntry1;
      Editor localEditor2;
      Entry localEntry2;
      try
      {
        checkNotClosed();
        validateKey(paramString);
        localEntry1 = (Entry)this.lruEntries.get(paramString);
        if (paramLong != -1L)
          if (localEntry1 != null)
          {
            long l = localEntry1.sequenceNumber;
            if (l == paramLong);
          }
          else
          {
            localEditor2 = null;
            return localEditor2;
          }
        if (localEntry1 == null)
        {
          Entry localEntry3 = new Entry(paramString, null);
          this.lruEntries.put(paramString, localEntry3);
          localEntry2 = localEntry3;
          localEditor2 = new Editor(localEntry2, null);
          Entry.access$702(localEntry2, localEditor2);
          this.journalWriter.write("DIRTY " + paramString + '\n');
          this.journalWriter.flush();
          continue;
        }
      }
      finally
      {
      }
      Editor localEditor1 = localEntry1.currentEditor;
      if (localEditor1 != null)
        localEditor2 = null;
      else
        localEntry2 = localEntry1;
    }
  }

  private static String inputStreamToString(InputStream paramInputStream)
    throws IOException
  {
    return Streams.readFully(new InputStreamReader(paramInputStream, Charsets.UTF_8));
  }

  private boolean journalRebuildRequired()
  {
    return (this.redundantOpCount >= 2000) && (this.redundantOpCount >= this.lruEntries.size());
  }

  public static DiskLruCache open(File paramFile, int paramInt1, int paramInt2, long paramLong)
    throws IOException
  {
    if (paramLong <= 0L)
      throw new IllegalArgumentException("maxSize <= 0");
    if (paramInt2 <= 0)
      throw new IllegalArgumentException("valueCount <= 0");
    File localFile1 = new File(paramFile, "journal.bkp");
    File localFile2;
    if (localFile1.exists())
    {
      localFile2 = new File(paramFile, "journal");
      if (!localFile2.exists())
        break label150;
      localFile1.delete();
    }
    while (true)
    {
      DiskLruCache localDiskLruCache1 = new DiskLruCache(paramFile, paramInt1, paramInt2, paramLong);
      if (localDiskLruCache1.journalFile.exists())
        try
        {
          localDiskLruCache1.readJournal();
          localDiskLruCache1.processJournal();
          localDiskLruCache1.journalWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(localDiskLruCache1.journalFile, true), Charsets.US_ASCII));
          return localDiskLruCache1;
          label150: renameTo(localFile1, localFile2, false);
        }
        catch (IOException localIOException)
        {
          System.out.println("DiskLruCache " + paramFile + " is corrupt: " + localIOException.getMessage() + ", removing");
          localDiskLruCache1.delete();
        }
    }
    paramFile.mkdirs();
    DiskLruCache localDiskLruCache2 = new DiskLruCache(paramFile, paramInt1, paramInt2, paramLong);
    localDiskLruCache2.rebuildJournal();
    return localDiskLruCache2;
  }

  private void processJournal()
    throws IOException
  {
    deleteIfExists(this.journalFileTmp);
    Iterator localIterator = this.lruEntries.values().iterator();
    while (localIterator.hasNext())
    {
      Entry localEntry = (Entry)localIterator.next();
      if (localEntry.currentEditor == null)
      {
        for (int j = 0; j < this.valueCount; j++)
          this.size += localEntry.lengths[j];
      }
      else
      {
        Entry.access$702(localEntry, null);
        for (int i = 0; i < this.valueCount; i++)
        {
          deleteIfExists(localEntry.getCleanFile(i));
          deleteIfExists(localEntry.getDirtyFile(i));
        }
        localIterator.remove();
      }
    }
  }

  // ERROR //
  private void readJournal()
    throws IOException
  {
    // Byte code:
    //   0: new 439	fm/qingting/async/http/libcore/StrictLineReader
    //   3: dup
    //   4: new 441	java/io/FileInputStream
    //   7: dup
    //   8: aload_0
    //   9: getfield 129	fm/qingting/async/http/libcore/DiskLruCache:journalFile	Ljava/io/File;
    //   12: invokespecial 443	java/io/FileInputStream:<init>	(Ljava/io/File;)V
    //   15: getstatic 377	fm/qingting/async/http/libcore/Charsets:US_ASCII	Ljava/nio/charset/Charset;
    //   18: invokespecial 444	fm/qingting/async/http/libcore/StrictLineReader:<init>	(Ljava/io/InputStream;Ljava/nio/charset/Charset;)V
    //   21: astore_1
    //   22: aload_1
    //   23: invokevirtual 447	fm/qingting/async/http/libcore/StrictLineReader:readLine	()Ljava/lang/String;
    //   26: astore_3
    //   27: aload_1
    //   28: invokevirtual 447	fm/qingting/async/http/libcore/StrictLineReader:readLine	()Ljava/lang/String;
    //   31: astore 4
    //   33: aload_1
    //   34: invokevirtual 447	fm/qingting/async/http/libcore/StrictLineReader:readLine	()Ljava/lang/String;
    //   37: astore 5
    //   39: aload_1
    //   40: invokevirtual 447	fm/qingting/async/http/libcore/StrictLineReader:readLine	()Ljava/lang/String;
    //   43: astore 6
    //   45: aload_1
    //   46: invokevirtual 447	fm/qingting/async/http/libcore/StrictLineReader:readLine	()Ljava/lang/String;
    //   49: astore 7
    //   51: ldc 29
    //   53: aload_3
    //   54: invokevirtual 453	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   57: ifeq +54 -> 111
    //   60: ldc 38
    //   62: aload 4
    //   64: invokevirtual 453	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   67: ifeq +44 -> 111
    //   70: aload_0
    //   71: getfield 122	fm/qingting/async/http/libcore/DiskLruCache:appVersion	I
    //   74: invokestatic 458	java/lang/Integer:toString	(I)Ljava/lang/String;
    //   77: aload 5
    //   79: invokevirtual 453	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   82: ifeq +29 -> 111
    //   85: aload_0
    //   86: getfield 135	fm/qingting/async/http/libcore/DiskLruCache:valueCount	I
    //   89: invokestatic 458	java/lang/Integer:toString	(I)Ljava/lang/String;
    //   92: aload 6
    //   94: invokevirtual 453	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   97: ifeq +14 -> 111
    //   100: ldc_w 460
    //   103: aload 7
    //   105: invokevirtual 453	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   108: ifne +77 -> 185
    //   111: new 144	java/io/IOException
    //   114: dup
    //   115: new 218	java/lang/StringBuilder
    //   118: dup
    //   119: invokespecial 219	java/lang/StringBuilder:<init>	()V
    //   122: ldc_w 462
    //   125: invokevirtual 225	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   128: aload_3
    //   129: invokevirtual 225	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   132: ldc_w 464
    //   135: invokevirtual 225	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   138: aload 4
    //   140: invokevirtual 225	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   143: ldc_w 464
    //   146: invokevirtual 225	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   149: aload 6
    //   151: invokevirtual 225	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   154: ldc_w 464
    //   157: invokevirtual 225	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   160: aload 7
    //   162: invokevirtual 225	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   165: ldc_w 466
    //   168: invokevirtual 225	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   171: invokevirtual 232	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   174: invokespecial 467	java/io/IOException:<init>	(Ljava/lang/String;)V
    //   177: athrow
    //   178: astore_2
    //   179: aload_1
    //   180: invokestatic 473	fm/qingting/async/http/libcore/IoUtils:closeQuietly	(Ljava/io/Closeable;)V
    //   183: aload_2
    //   184: athrow
    //   185: iconst_0
    //   186: istore 8
    //   188: aload_0
    //   189: aload_1
    //   190: invokevirtual 447	fm/qingting/async/http/libcore/StrictLineReader:readLine	()Ljava/lang/String;
    //   193: invokespecial 476	fm/qingting/async/http/libcore/DiskLruCache:readJournalLine	(Ljava/lang/String;)V
    //   196: iinc 8 1
    //   199: goto -11 -> 188
    //   202: astore 9
    //   204: aload_0
    //   205: iload 8
    //   207: aload_0
    //   208: getfield 91	fm/qingting/async/http/libcore/DiskLruCache:lruEntries	Ljava/util/LinkedHashMap;
    //   211: invokevirtual 348	java/util/LinkedHashMap:size	()I
    //   214: isub
    //   215: putfield 184	fm/qingting/async/http/libcore/DiskLruCache:redundantOpCount	I
    //   218: aload_1
    //   219: invokestatic 473	fm/qingting/async/http/libcore/IoUtils:closeQuietly	(Ljava/io/Closeable;)V
    //   222: return
    //
    // Exception table:
    //   from	to	target	type
    //   22	111	178	finally
    //   111	178	178	finally
    //   188	196	178	finally
    //   204	218	178	finally
    //   188	196	202	java/io/EOFException
  }

  private void readJournalLine(String paramString)
    throws IOException
  {
    int i = paramString.indexOf(' ');
    if (i == -1)
      throw new IOException("unexpected journal line: " + paramString);
    int j = i + 1;
    int k = paramString.indexOf(' ', j);
    String str2;
    if (k == -1)
    {
      str2 = paramString.substring(j);
      if ((i != "REMOVE".length()) || (!paramString.startsWith("REMOVE")))
        break label304;
      this.lruEntries.remove(str2);
    }
    label304: for (String str1 = paramString.substring(j, k); ; str1 = str2)
    {
      Entry localEntry = (Entry)this.lruEntries.get(str1);
      if (localEntry == null)
      {
        localEntry = new Entry(str1, null);
        this.lruEntries.put(str1, localEntry);
      }
      if ((k != -1) && (i == "CLEAN".length()) && (paramString.startsWith("CLEAN")))
      {
        String[] arrayOfString = paramString.substring(k + 1).split(" ");
        Entry.access$602(localEntry, true);
        Entry.access$702(localEntry, null);
        localEntry.setLengths(arrayOfString);
        return;
      }
      if ((k == -1) && (i == "DIRTY".length()) && (paramString.startsWith("DIRTY")))
      {
        Entry.access$702(localEntry, new Editor(localEntry, null));
        return;
      }
      if ((k == -1) && (i == "READ".length()) && (paramString.startsWith("READ")))
        break;
      throw new IOException("unexpected journal line: " + paramString);
    }
  }

  private void rebuildJournal()
    throws IOException
  {
    BufferedWriter localBufferedWriter;
    while (true)
    {
      Entry localEntry;
      try
      {
        if (this.journalWriter != null)
          this.journalWriter.close();
        localBufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(this.journalFileTmp), Charsets.US_ASCII));
        try
        {
          localBufferedWriter.write("libcore.io.DiskLruCache");
          localBufferedWriter.write("\n");
          localBufferedWriter.write("1");
          localBufferedWriter.write("\n");
          localBufferedWriter.write(Integer.toString(this.appVersion));
          localBufferedWriter.write("\n");
          localBufferedWriter.write(Integer.toString(this.valueCount));
          localBufferedWriter.write("\n");
          localBufferedWriter.write("\n");
          Iterator localIterator = this.lruEntries.values().iterator();
          if (!localIterator.hasNext())
            break;
          localEntry = (Entry)localIterator.next();
          if (localEntry.currentEditor != null)
          {
            localBufferedWriter.write("DIRTY " + localEntry.key + '\n');
            continue;
          }
        }
        finally
        {
          localBufferedWriter.close();
        }
      }
      finally
      {
      }
      localBufferedWriter.write("CLEAN " + localEntry.key + localEntry.getLengths() + '\n');
    }
    localBufferedWriter.close();
    if (this.journalFile.exists())
      renameTo(this.journalFile, this.journalFileBkp, true);
    renameTo(this.journalFileTmp, this.journalFile, false);
    this.journalFileBkp.delete();
    this.journalWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(this.journalFile, true), Charsets.US_ASCII));
  }

  private static void renameTo(File paramFile1, File paramFile2, boolean paramBoolean)
    throws IOException
  {
    if (paramBoolean)
      deleteIfExists(paramFile2);
    if (!paramFile1.renameTo(paramFile2))
      throw new IOException();
  }

  private void trimToSize()
    throws IOException
  {
    while (this.size > this.maxSize)
      remove((String)((Map.Entry)this.lruEntries.entrySet().iterator().next()).getKey());
  }

  private void validateKey(String paramString)
  {
    if (!LEGAL_KEY_PATTERN.matcher(paramString).matches())
      throw new IllegalArgumentException("keys must match regex [a-z0-9_-]{1,64}: \"" + paramString + "\"");
  }

  public void close()
    throws IOException
  {
    while (true)
    {
      try
      {
        Writer localWriter = this.journalWriter;
        if (localWriter == null)
          return;
        Iterator localIterator = new ArrayList(this.lruEntries.values()).iterator();
        if (localIterator.hasNext())
        {
          Entry localEntry = (Entry)localIterator.next();
          if (localEntry.currentEditor == null)
            continue;
          localEntry.currentEditor.abort();
          continue;
        }
      }
      finally
      {
      }
      trimToSize();
      this.journalWriter.close();
      this.journalWriter = null;
    }
  }

  public void delete()
    throws IOException
  {
    close();
    IoUtils.deleteContents(this.directory);
  }

  public Editor edit(String paramString)
    throws IOException
  {
    return edit(paramString, -1L);
  }

  public void flush()
    throws IOException
  {
    try
    {
      checkNotClosed();
      trimToSize();
      this.journalWriter.flush();
      return;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }

  public Snapshot get(String paramString)
    throws IOException
  {
    while (true)
    {
      try
      {
        checkNotClosed();
        validateKey(paramString);
        Entry localEntry = (Entry)this.lruEntries.get(paramString);
        if (localEntry == null)
        {
          localSnapshot = null;
          return localSnapshot;
        }
        if (!localEntry.readable)
        {
          localSnapshot = null;
          continue;
        }
        InputStream[] arrayOfInputStream = new InputStream[this.valueCount];
        int i = 0;
        int j;
        try
        {
          if (i >= this.valueCount)
            continue;
          arrayOfInputStream[i] = new FileInputStream(localEntry.getCleanFile(i));
          i++;
          continue;
        }
        catch (FileNotFoundException localFileNotFoundException)
        {
          j = 0;
          if (j >= this.valueCount)
            break label221;
        }
        if (arrayOfInputStream[j] != null)
        {
          IoUtils.closeQuietly(arrayOfInputStream[j]);
          j++;
          continue;
          this.redundantOpCount = (1 + this.redundantOpCount);
          this.journalWriter.append("READ " + paramString + '\n');
          if (journalRebuildRequired())
            this.executorService.submit(this.cleanupCallable);
          localSnapshot = new Snapshot(paramString, localEntry.sequenceNumber, arrayOfInputStream, localEntry.lengths, null);
          continue;
        }
      }
      finally
      {
      }
      label221: Snapshot localSnapshot = null;
    }
  }

  public File getDirectory()
  {
    return this.directory;
  }

  public long getMaxSize()
  {
    return this.maxSize;
  }

  public boolean isClosed()
  {
    return this.journalWriter == null;
  }

  public boolean remove(String paramString)
    throws IOException
  {
    while (true)
    {
      try
      {
        checkNotClosed();
        validateKey(paramString);
        Entry localEntry = (Entry)this.lruEntries.get(paramString);
        int i;
        if (localEntry != null)
        {
          Editor localEditor = localEntry.currentEditor;
          i = 0;
          if (localEditor == null);
        }
        else
        {
          bool = false;
          return bool;
          this.size -= localEntry.lengths[i];
          localEntry.lengths[i] = 0L;
          i++;
        }
        if (i < this.valueCount)
        {
          File localFile = localEntry.getCleanFile(i);
          if (localFile.delete())
            continue;
          throw new IOException("failed to delete " + localFile);
        }
      }
      finally
      {
      }
      this.redundantOpCount = (1 + this.redundantOpCount);
      this.journalWriter.append("REMOVE " + paramString + '\n');
      this.lruEntries.remove(paramString);
      if (journalRebuildRequired())
        this.executorService.submit(this.cleanupCallable);
      boolean bool = true;
    }
  }

  public void setMaxSize(long paramLong)
  {
    try
    {
      this.maxSize = paramLong;
      this.executorService.submit(this.cleanupCallable);
      return;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }

  public long size()
  {
    try
    {
      long l = this.size;
      return l;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }

  public final class Editor
  {
    private boolean committed;
    private final DiskLruCache.Entry entry;
    private boolean hasErrors;
    private final boolean[] written;

    private Editor(DiskLruCache.Entry arg2)
    {
      DiskLruCache.Entry localEntry;
      this.entry = localEntry;
      if (DiskLruCache.Entry.access$600(localEntry));
      for (boolean[] arrayOfBoolean = null; ; arrayOfBoolean = new boolean[DiskLruCache.this.valueCount])
      {
        this.written = arrayOfBoolean;
        return;
      }
    }

    public void abort()
      throws IOException
    {
      DiskLruCache.this.completeEdit(this, false);
    }

    public void abortUnlessCommitted()
    {
      if (!this.committed);
      try
      {
        abort();
        return;
      }
      catch (IOException localIOException)
      {
      }
    }

    public void commit()
      throws IOException
    {
      if (this.hasErrors)
      {
        DiskLruCache.this.completeEdit(this, false);
        DiskLruCache.this.remove(DiskLruCache.Entry.access$1100(this.entry));
      }
      while (true)
      {
        this.committed = true;
        return;
        DiskLruCache.this.completeEdit(this, true);
      }
    }

    public String getString(int paramInt)
      throws IOException
    {
      InputStream localInputStream = newInputStream(paramInt);
      if (localInputStream != null)
        return DiskLruCache.inputStreamToString(localInputStream);
      return null;
    }

    public InputStream newInputStream(int paramInt)
      throws IOException
    {
      synchronized (DiskLruCache.this)
      {
        if (DiskLruCache.Entry.access$700(this.entry) != this)
          throw new IllegalStateException();
      }
      if (!DiskLruCache.Entry.access$600(this.entry))
        return null;
      try
      {
        FileInputStream localFileInputStream = new FileInputStream(this.entry.getCleanFile(paramInt));
        return localFileInputStream;
      }
      catch (FileNotFoundException localFileNotFoundException)
      {
      }
      return null;
    }

    public OutputStream newOutputStream(int paramInt)
      throws IOException
    {
      synchronized (DiskLruCache.this)
      {
        if (DiskLruCache.Entry.access$700(this.entry) != this)
          throw new IllegalStateException();
      }
      if (!DiskLruCache.Entry.access$600(this.entry))
        this.written[paramInt] = true;
      File localFile = this.entry.getDirtyFile(paramInt);
      try
      {
        FileOutputStream localFileOutputStream1 = new FileOutputStream(localFile);
        localObject2 = localFileOutputStream1;
        FaultHidingOutputStream localFaultHidingOutputStream = new FaultHidingOutputStream((OutputStream)localObject2, null);
        return localFaultHidingOutputStream;
      }
      catch (FileNotFoundException localFileNotFoundException1)
      {
        while (true)
        {
          Object localObject2;
          DiskLruCache.this.directory.mkdirs();
          try
          {
            FileOutputStream localFileOutputStream2 = new FileOutputStream(localFile);
            localObject2 = localFileOutputStream2;
          }
          catch (FileNotFoundException localFileNotFoundException2)
          {
            OutputStream localOutputStream = DiskLruCache.NULL_OUTPUT_STREAM;
            return localOutputStream;
          }
        }
      }
    }

    // ERROR //
    public void set(int paramInt, String paramString)
      throws IOException
    {
      // Byte code:
      //   0: new 127	java/io/OutputStreamWriter
      //   3: dup
      //   4: aload_0
      //   5: iload_1
      //   6: invokevirtual 129	fm/qingting/async/http/libcore/DiskLruCache$Editor:newOutputStream	(I)Ljava/io/OutputStream;
      //   9: getstatic 135	fm/qingting/async/http/libcore/Charsets:UTF_8	Ljava/nio/charset/Charset;
      //   12: invokespecial 138	java/io/OutputStreamWriter:<init>	(Ljava/io/OutputStream;Ljava/nio/charset/Charset;)V
      //   15: astore_3
      //   16: aload_3
      //   17: aload_2
      //   18: invokevirtual 144	java/io/Writer:write	(Ljava/lang/String;)V
      //   21: aload_3
      //   22: invokestatic 150	fm/qingting/async/http/libcore/IoUtils:closeQuietly	(Ljava/io/Closeable;)V
      //   25: return
      //   26: astore 4
      //   28: aconst_null
      //   29: astore_3
      //   30: aload_3
      //   31: invokestatic 150	fm/qingting/async/http/libcore/IoUtils:closeQuietly	(Ljava/io/Closeable;)V
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

    private class FaultHidingOutputStream extends FilterOutputStream
    {
      private FaultHidingOutputStream(OutputStream arg2)
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
          DiskLruCache.Editor.access$2302(DiskLruCache.Editor.this, true);
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
          DiskLruCache.Editor.access$2302(DiskLruCache.Editor.this, true);
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
          DiskLruCache.Editor.access$2302(DiskLruCache.Editor.this, true);
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
          DiskLruCache.Editor.access$2302(DiskLruCache.Editor.this, true);
        }
      }
    }
  }

  private final class Entry
  {
    private DiskLruCache.Editor currentEditor;
    private final String key;
    private final long[] lengths;
    private boolean readable;
    private long sequenceNumber;

    private Entry(String arg2)
    {
      Object localObject;
      this.key = localObject;
      this.lengths = new long[DiskLruCache.this.valueCount];
    }

    private IOException invalidLengths(String[] paramArrayOfString)
      throws IOException
    {
      throw new IOException("unexpected journal line: " + Arrays.toString(paramArrayOfString));
    }

    private void setLengths(String[] paramArrayOfString)
      throws IOException
    {
      if (paramArrayOfString.length != DiskLruCache.this.valueCount)
        throw invalidLengths(paramArrayOfString);
      int i = 0;
      try
      {
        while (i < paramArrayOfString.length)
        {
          this.lengths[i] = Long.parseLong(paramArrayOfString[i]);
          i++;
        }
      }
      catch (NumberFormatException localNumberFormatException)
      {
        throw invalidLengths(paramArrayOfString);
      }
    }

    public File getCleanFile(int paramInt)
    {
      return new File(DiskLruCache.this.directory, this.key + "." + paramInt);
    }

    public File getDirtyFile(int paramInt)
    {
      return new File(DiskLruCache.this.directory, this.key + "." + paramInt + ".tmp");
    }

    public String getLengths()
      throws IOException
    {
      StringBuilder localStringBuilder = new StringBuilder();
      for (long l : this.lengths)
        localStringBuilder.append(' ').append(l);
      return localStringBuilder.toString();
    }
  }

  public final class Snapshot
    implements Closeable
  {
    private final InputStream[] ins;
    private final String key;
    private final long[] lengths;
    private final long sequenceNumber;

    private Snapshot(String paramLong, long arg3, InputStream[] paramArrayOfLong, long[] arg6)
    {
      this.key = paramLong;
      this.sequenceNumber = ???;
      this.ins = paramArrayOfLong;
      Object localObject;
      this.lengths = localObject;
    }

    public void close()
    {
      InputStream[] arrayOfInputStream = this.ins;
      int i = arrayOfInputStream.length;
      for (int j = 0; j < i; j++)
        IoUtils.closeQuietly(arrayOfInputStream[j]);
    }

    public DiskLruCache.Editor edit()
      throws IOException
    {
      return DiskLruCache.this.edit(this.key, this.sequenceNumber);
    }

    public InputStream getInputStream(int paramInt)
    {
      return this.ins[paramInt];
    }

    public long getLength(int paramInt)
    {
      return this.lengths[paramInt];
    }

    public String getString(int paramInt)
      throws IOException
    {
      return DiskLruCache.inputStreamToString(getInputStream(paramInt));
    }
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.async.http.libcore.DiskLruCache
 * JD-Core Version:    0.6.2
 */