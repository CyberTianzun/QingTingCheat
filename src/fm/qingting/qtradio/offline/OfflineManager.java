package fm.qingting.qtradio.offline;

import android.content.Context;
import android.content.res.AssetManager;
import fm.qingting.qtradio.model.SharedCfg;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class OfflineManager
{
  private static final String DefDataBasePath = "/data/data/fm.qingting.qtradio/databases/";

  public static void loadOfflineData(Context paramContext)
  {
    int i = 0;
    if (paramContext == null);
    while (true)
    {
      return;
      int k;
      while (true)
      {
        InputStream localInputStream;
        ZipInputStream localZipInputStream;
        try
        {
          while (true)
          {
            AssetManager localAssetManager = paramContext.getAssets();
            try
            {
              if (!upgradeOfflineDB(paramContext))
                break;
              log("loadOfflineData");
              String[] arrayOfString = localAssetManager.list("offline");
              File localFile = new File("/data/data/fm.qingting.qtradio/databases/");
              if (!localFile.exists())
                localFile.mkdir();
              int j = arrayOfString.length;
              k = 0;
              if (i >= j)
                break label232;
              String str = arrayOfString[i];
              localInputStream = localAssetManager.open("offline/" + str);
              localZipInputStream = new ZipInputStream(localInputStream);
              while (true)
              {
                ZipEntry localZipEntry = localZipInputStream.getNextEntry();
                if (localZipEntry == null)
                  break;
                log("ze: " + localZipEntry.getName());
                moveDataBase(localZipInputStream, localZipEntry.getName(), localFile);
              }
            }
            catch (Exception localException)
            {
              log("exception:" + localException);
            }
          }
          break;
        }
        finally
        {
        }
        k = 1;
        localInputStream.close();
        localZipInputStream.closeEntry();
        localZipInputStream.close();
        i++;
      }
      label232: if (k != 0)
        SharedCfg.getInstance().setOfflineDBVersion(SharedCfg.getInstance().getNewOfflienDBVersion());
    }
  }

  private static void log(String paramString)
  {
  }

  // ERROR //
  private static boolean moveDataBase(InputStream paramInputStream, String paramString, File paramFile)
  {
    // Byte code:
    //   0: aload_0
    //   1: ifnull +11 -> 12
    //   4: aload_1
    //   5: ifnull +7 -> 12
    //   8: aload_2
    //   9: ifnonnull +5 -> 14
    //   12: iconst_0
    //   13: ireturn
    //   14: new 52	java/lang/StringBuilder
    //   17: dup
    //   18: invokespecial 53	java/lang/StringBuilder:<init>	()V
    //   21: ldc 8
    //   23: invokevirtual 59	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   26: aload_1
    //   27: invokevirtual 59	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   30: invokevirtual 63	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   33: astore_3
    //   34: new 41	java/io/File
    //   37: dup
    //   38: aload_3
    //   39: invokespecial 43	java/io/File:<init>	(Ljava/lang/String;)V
    //   42: invokevirtual 47	java/io/File:exists	()Z
    //   45: ifeq +25 -> 70
    //   48: new 52	java/lang/StringBuilder
    //   51: dup
    //   52: invokespecial 53	java/lang/StringBuilder:<init>	()V
    //   55: aload_3
    //   56: invokevirtual 59	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   59: ldc 119
    //   61: invokevirtual 59	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   64: invokevirtual 63	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   67: invokestatic 31	fm/qingting/qtradio/offline/OfflineManager:log	(Ljava/lang/String;)V
    //   70: new 121	java/io/FileOutputStream
    //   73: dup
    //   74: aload_3
    //   75: invokespecial 122	java/io/FileOutputStream:<init>	(Ljava/lang/String;)V
    //   78: astore 4
    //   80: sipush 4096
    //   83: newarray byte
    //   85: astore 5
    //   87: aload_0
    //   88: aload 5
    //   90: invokevirtual 126	java/io/InputStream:read	([B)I
    //   93: istore 8
    //   95: iload 8
    //   97: ifle +23 -> 120
    //   100: aload 4
    //   102: aload 5
    //   104: iconst_0
    //   105: iload 8
    //   107: invokevirtual 130	java/io/FileOutputStream:write	([BII)V
    //   110: goto -23 -> 87
    //   113: astore 6
    //   115: aload 6
    //   117: invokevirtual 133	java/io/IOException:printStackTrace	()V
    //   120: aload 4
    //   122: invokevirtual 134	java/io/FileOutputStream:close	()V
    //   125: iconst_1
    //   126: ireturn
    //   127: astore 9
    //   129: aload 9
    //   131: invokevirtual 135	java/io/FileNotFoundException:printStackTrace	()V
    //   134: iconst_0
    //   135: ireturn
    //   136: astore 7
    //   138: aload 7
    //   140: invokevirtual 133	java/io/IOException:printStackTrace	()V
    //   143: goto -18 -> 125
    //
    // Exception table:
    //   from	to	target	type
    //   87	95	113	java/io/IOException
    //   100	110	113	java/io/IOException
    //   70	80	127	java/io/FileNotFoundException
    //   120	125	136	java/io/IOException
  }

  private void showData(String paramString)
  {
    if (paramString == null);
    while (true)
    {
      return;
      try
      {
        FileInputStream localFileInputStream = new FileInputStream(paramString);
        byte[] arrayOfByte = new byte[1024];
        while (true)
        {
          int i = localFileInputStream.read(arrayOfByte);
          if (i <= 0)
            break;
          new String(arrayOfByte, 0, i);
        }
      }
      catch (Exception localException)
      {
      }
    }
  }

  private static boolean upgradeOfflineDB(Context paramContext)
  {
    String str1;
    String str2;
    if (paramContext != null)
    {
      str1 = SharedCfg.getInstance().getOfflineDBVersion();
      str2 = SharedCfg.getInstance().getNewOfflienDBVersion();
    }
    return (str1 == null) || (str2 == null) || (!str1.equalsIgnoreCase(str2));
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.qtradio.offline.OfflineManager
 * JD-Core Version:    0.6.2
 */