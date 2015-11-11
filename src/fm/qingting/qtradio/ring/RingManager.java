package fm.qingting.qtradio.ring;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;
import fm.qingting.qtradio.model.SharedCfg;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class RingManager
{
  public static void loadRings(Context paramContext)
  {
    try
    {
      loadRingsData(paramContext);
      return;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }

  private static void loadRingsData(Context paramContext)
  {
    AssetManager localAssetManager = paramContext.getAssets();
    try
    {
      if (!upgradeRingsDB())
        return;
      for (String str : localAssetManager.list("rings"))
      {
        InputStream localInputStream = localAssetManager.open("rings/" + str);
        ZipInputStream localZipInputStream = new ZipInputStream(localInputStream);
        while (true)
        {
          ZipEntry localZipEntry = localZipInputStream.getNextEntry();
          if (localZipEntry == null)
            break;
          Log.e("RingManager", "ring: " + localZipEntry.getName());
          moveDataBase(localZipInputStream, localZipEntry.getName());
        }
        SharedCfg.getInstance().setLocalRingsVersion(SharedCfg.getInstance().getNewRingsVersion());
        localInputStream.close();
        localZipInputStream.closeEntry();
        localZipInputStream.close();
      }
      return;
    }
    catch (Exception localException)
    {
    }
  }

  // ERROR //
  private static boolean moveDataBase(InputStream paramInputStream, String paramString)
  {
    // Byte code:
    //   0: aload_0
    //   1: ifnull +7 -> 8
    //   4: aload_1
    //   5: ifnonnull +5 -> 10
    //   8: iconst_0
    //   9: ireturn
    //   10: invokestatic 109	fm/qingting/download/QTRadioDownloadAgent:getInstance	()Lfm/qingting/download/QTRadioDownloadAgent;
    //   13: invokevirtual 112	fm/qingting/download/QTRadioDownloadAgent:getDownLoadPath	()Ljava/lang/String;
    //   16: astore_2
    //   17: new 114	java/io/File
    //   20: dup
    //   21: aload_2
    //   22: invokespecial 116	java/io/File:<init>	(Ljava/lang/String;)V
    //   25: astore_3
    //   26: aload_3
    //   27: invokevirtual 119	java/io/File:exists	()Z
    //   30: ifne +8 -> 38
    //   33: aload_3
    //   34: invokevirtual 122	java/io/File:mkdir	()Z
    //   37: pop
    //   38: new 35	java/lang/StringBuilder
    //   41: dup
    //   42: invokespecial 36	java/lang/StringBuilder:<init>	()V
    //   45: aload_2
    //   46: invokevirtual 42	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   49: aload_1
    //   50: invokevirtual 42	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   53: invokevirtual 46	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   56: astore 4
    //   58: new 114	java/io/File
    //   61: dup
    //   62: aload 4
    //   64: invokespecial 116	java/io/File:<init>	(Ljava/lang/String;)V
    //   67: astore 5
    //   69: aload 5
    //   71: invokevirtual 119	java/io/File:exists	()Z
    //   74: ifeq +17 -> 91
    //   77: invokestatic 25	fm/qingting/qtradio/ring/RingManager:upgradeRingsDB	()Z
    //   80: ifeq -72 -> 8
    //   83: aload 5
    //   85: invokevirtual 125	java/io/File:delete	()Z
    //   88: ifeq -80 -> 8
    //   91: new 127	java/io/FileOutputStream
    //   94: dup
    //   95: aload 4
    //   97: invokespecial 128	java/io/FileOutputStream:<init>	(Ljava/lang/String;)V
    //   100: astore 6
    //   102: sipush 4096
    //   105: newarray byte
    //   107: astore 7
    //   109: aload_0
    //   110: aload 7
    //   112: invokevirtual 132	java/io/InputStream:read	([B)I
    //   115: istore 10
    //   117: iload 10
    //   119: ifle +23 -> 142
    //   122: aload 6
    //   124: aload 7
    //   126: iconst_0
    //   127: iload 10
    //   129: invokevirtual 136	java/io/FileOutputStream:write	([BII)V
    //   132: goto -23 -> 109
    //   135: astore 8
    //   137: aload 8
    //   139: invokevirtual 139	java/io/IOException:printStackTrace	()V
    //   142: aload 6
    //   144: invokevirtual 140	java/io/FileOutputStream:close	()V
    //   147: iconst_1
    //   148: ireturn
    //   149: astore 11
    //   151: aload 11
    //   153: invokevirtual 141	java/io/FileNotFoundException:printStackTrace	()V
    //   156: iconst_0
    //   157: ireturn
    //   158: astore 9
    //   160: aload 9
    //   162: invokevirtual 139	java/io/IOException:printStackTrace	()V
    //   165: goto -18 -> 147
    //
    // Exception table:
    //   from	to	target	type
    //   109	117	135	java/io/IOException
    //   122	132	135	java/io/IOException
    //   91	102	149	java/io/FileNotFoundException
    //   142	147	158	java/io/IOException
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

  private static boolean upgradeRingsDB()
  {
    String str1 = SharedCfg.getInstance().getLocalRingsVersion();
    String str2 = SharedCfg.getInstance().getNewRingsVersion();
    return (str1 == null) || (str2 == null) || (!str1.equalsIgnoreCase(str2));
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.qtradio.ring.RingManager
 * JD-Core Version:    0.6.2
 */