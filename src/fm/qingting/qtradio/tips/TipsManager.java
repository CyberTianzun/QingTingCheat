package fm.qingting.qtradio.tips;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;
import fm.qingting.qtradio.model.SharedCfg;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class TipsManager
{
  private static final String DefDataBasePath = "/mnt/sdcard/QTRadioTips/";
  private static final String TIPS_SOURCE = "file:///mnt/sdcard/QTRadioTips/1";
  private static final String TIPS_VERSION = "1.0";
  private static TipsManager _instance;
  private Context mContext;

  public static TipsManager getInstance()
  {
    if (_instance == null)
      _instance = new TipsManager();
    return _instance;
  }

  private void loadTipsData()
  {
    AssetManager localAssetManager = this.mContext.getAssets();
    try
    {
      if (!upgradeTipsDB())
        return;
      for (String str : localAssetManager.list("tips"))
      {
        InputStream localInputStream = localAssetManager.open("tips/" + str);
        ZipInputStream localZipInputStream = new ZipInputStream(localInputStream);
        new byte[4096];
        while (true)
        {
          ZipEntry localZipEntry = localZipInputStream.getNextEntry();
          if (localZipEntry == null)
            break;
          Log.e("TipManager", "tips: " + localZipEntry.getName());
          moveDataBase(localZipInputStream, localZipEntry.getName());
        }
        SharedCfg.getInstance().setTipsVersion("1.0");
        localZipInputStream.closeEntry();
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

  private boolean upgradeTipsDB()
  {
    String str;
    if (this.mContext != null)
      str = SharedCfg.getInstance().getTipsVersion();
    return (str == null) || (!str.equalsIgnoreCase("1.0"));
  }

  public String getTipsSource()
  {
    return "file:///mnt/sdcard/QTRadioTips/1";
  }

  public void loadTips(Context paramContext)
  {
    if (paramContext == null);
    while (true)
    {
      return;
      try
      {
        this.mContext = paramContext;
        loadTipsData();
      }
      finally
      {
      }
    }
  }

  // ERROR //
  public boolean moveDataBase(InputStream paramInputStream, String paramString)
  {
    // Byte code:
    //   0: aload_1
    //   1: ifnull +7 -> 8
    //   4: aload_2
    //   5: ifnonnull +5 -> 10
    //   8: iconst_0
    //   9: ireturn
    //   10: new 145	java/io/File
    //   13: dup
    //   14: ldc 8
    //   16: invokespecial 146	java/io/File:<init>	(Ljava/lang/String;)V
    //   19: astore_3
    //   20: aload_3
    //   21: invokevirtual 149	java/io/File:exists	()Z
    //   24: ifne +8 -> 32
    //   27: aload_3
    //   28: invokevirtual 152	java/io/File:mkdir	()Z
    //   31: pop
    //   32: new 52	java/lang/StringBuilder
    //   35: dup
    //   36: invokespecial 53	java/lang/StringBuilder:<init>	()V
    //   39: ldc 8
    //   41: invokevirtual 59	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   44: aload_2
    //   45: invokevirtual 59	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   48: invokevirtual 63	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   51: astore 4
    //   53: new 145	java/io/File
    //   56: dup
    //   57: aload 4
    //   59: invokespecial 146	java/io/File:<init>	(Ljava/lang/String;)V
    //   62: astore 5
    //   64: aload 5
    //   66: invokevirtual 149	java/io/File:exists	()Z
    //   69: ifeq +18 -> 87
    //   72: aload_0
    //   73: invokespecial 42	fm/qingting/qtradio/tips/TipsManager:upgradeTipsDB	()Z
    //   76: ifeq -68 -> 8
    //   79: aload 5
    //   81: invokevirtual 155	java/io/File:delete	()Z
    //   84: ifeq -76 -> 8
    //   87: new 157	java/io/FileOutputStream
    //   90: dup
    //   91: aload 4
    //   93: invokespecial 158	java/io/FileOutputStream:<init>	(Ljava/lang/String;)V
    //   96: astore 6
    //   98: sipush 4096
    //   101: newarray byte
    //   103: astore 7
    //   105: aload_1
    //   106: aload 7
    //   108: invokevirtual 159	java/io/InputStream:read	([B)I
    //   111: istore 10
    //   113: iload 10
    //   115: ifle +23 -> 138
    //   118: aload 6
    //   120: aload 7
    //   122: iconst_0
    //   123: iload 10
    //   125: invokevirtual 162	java/io/FileOutputStream:write	([BII)V
    //   128: goto -23 -> 105
    //   131: astore 8
    //   133: aload 8
    //   135: invokevirtual 165	java/io/IOException:printStackTrace	()V
    //   138: aload 6
    //   140: invokevirtual 166	java/io/FileOutputStream:close	()V
    //   143: iconst_1
    //   144: ireturn
    //   145: astore 11
    //   147: aload 11
    //   149: invokevirtual 167	java/io/FileNotFoundException:printStackTrace	()V
    //   152: iconst_0
    //   153: ireturn
    //   154: astore 9
    //   156: aload 9
    //   158: invokevirtual 165	java/io/IOException:printStackTrace	()V
    //   161: goto -18 -> 143
    //
    // Exception table:
    //   from	to	target	type
    //   105	113	131	java/io/IOException
    //   118	128	131	java/io/IOException
    //   87	98	145	java/io/FileNotFoundException
    //   138	143	154	java/io/IOException
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.qtradio.tips.TipsManager
 * JD-Core Version:    0.6.2
 */