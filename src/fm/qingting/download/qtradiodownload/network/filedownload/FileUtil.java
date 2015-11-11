package fm.qingting.download.qtradiodownload.network.filedownload;

import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.LinkedList;

public class FileUtil
{
  public static String FormetFileSize(long paramLong)
  {
    DecimalFormat localDecimalFormat = new DecimalFormat("#.00");
    if (paramLong < 1024L)
      return localDecimalFormat.format(paramLong) + "B";
    if (paramLong < 1048576L)
      return localDecimalFormat.format(paramLong / 1024.0D) + "K";
    if (paramLong < 1073741824L)
      return localDecimalFormat.format(paramLong / 1048576.0D) + "M";
    if (paramLong < 1099511627776L)
      return localDecimalFormat.format(paramLong / 1073741824.0D) + "G";
    return localDecimalFormat.format(paramLong / 1099511627776.0D) + "T";
  }

  public static boolean createFile(String paramString, boolean paramBoolean)
    throws IOException
  {
    File localFile1 = new File(paramString);
    boolean bool1 = localFile1.exists();
    boolean bool2 = false;
    if (!bool1);
    try
    {
      boolean bool4 = localFile1.createNewFile();
      bool2 = bool4;
      return bool2;
    }
    catch (IOException localIOException1)
    {
      if (!paramBoolean)
        throw localIOException1;
      File localFile2 = localFile1.getParentFile();
      if (!localFile2.exists())
        localFile2.mkdirs();
      try
      {
        boolean bool3 = localFile1.createNewFile();
        return bool3;
      }
      catch (IOException localIOException2)
      {
        throw localIOException2;
      }
    }
  }

  public static long getFileContains(File paramFile)
  {
    if ((paramFile == null) || (!paramFile.exists()) || (!paramFile.isDirectory()))
      return 0L;
    return paramFile.list().length;
  }

  public static long getFileSize(File paramFile)
  {
    long l1 = 0L;
    if ((paramFile == null) || (!paramFile.exists()));
    while (true)
    {
      return l1;
      if (!paramFile.isDirectory())
        break;
      File[] arrayOfFile = paramFile.listFiles();
      if ((arrayOfFile != null) && (arrayOfFile.length > 0))
      {
        int i = 0;
        int j = arrayOfFile.length;
        while (i < j)
        {
          long l2 = l1 + getFileSize(arrayOfFile[i]);
          i++;
          l1 = l2;
        }
      }
    }
    return l1 + paramFile.length();
  }

  public static long getFileSize2(File paramFile)
  {
    long l1 = 0L;
    long l2;
    if ((paramFile == null) || (!paramFile.exists()))
      l2 = l1;
    label114: 
    while (true)
    {
      return l2;
      LinkedList localLinkedList = new LinkedList();
      localLinkedList.add(paramFile);
      File localFile = (File)localLinkedList.poll();
      if (!localFile.isDirectory());
      for (l2 = l1 + localFile.length(); ; l2 = l1)
      {
        if (localLinkedList.isEmpty())
          break label114;
        l1 = l2;
        break;
        File[] arrayOfFile = localFile.listFiles();
        int i = 0;
        int j = arrayOfFile.length;
        while (i < j)
        {
          localLinkedList.add(arrayOfFile[i]);
          i++;
        }
      }
    }
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.download.qtradiodownload.network.filedownload.FileUtil
 * JD-Core Version:    0.6.2
 */