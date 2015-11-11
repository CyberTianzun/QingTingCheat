package fm.qingting.download;

import android.os.Environment;
import java.io.File;
import java.io.IOException;

public class FileHelper
{
  private static String QADIOSAVEPATH = "QTDownloadRadio/";
  private String SDCardPath = null;

  public File createSDDir(String paramString)
  {
    File localFile = new File(this.SDCardPath + paramString);
    localFile.mkdir();
    return localFile;
  }

  public File createSDFile(String paramString)
    throws IOException
  {
    File localFile = new File(this.SDCardPath + paramString);
    localFile.createNewFile();
    return localFile;
  }

  public String getPath()
  {
    return this.SDCardPath;
  }

  public boolean isFileExist(String paramString)
  {
    return new File(this.SDCardPath + paramString).exists();
  }

  public boolean isSDCardAvailable()
  {
    return Environment.getExternalStorageState().equals("mounted");
  }

  // ERROR //
  public File writeToSDCard(String paramString1, String paramString2, java.io.InputStream paramInputStream)
  {
    // Byte code:
    //   0: aconst_null
    //   1: astore 4
    //   3: aload_0
    //   4: aload_1
    //   5: invokevirtual 82	fm/qingting/download/FileHelper:createSDDir	(Ljava/lang/String;)Ljava/io/File;
    //   8: pop
    //   9: aload_0
    //   10: new 20	java/lang/StringBuilder
    //   13: dup
    //   14: invokespecial 21	java/lang/StringBuilder:<init>	()V
    //   17: aload_1
    //   18: invokevirtual 39	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   21: aload_2
    //   22: invokevirtual 39	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   25: invokevirtual 43	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   28: invokevirtual 84	fm/qingting/download/FileHelper:createSDFile	(Ljava/lang/String;)Ljava/io/File;
    //   31: astore 13
    //   33: aload 13
    //   35: astore 7
    //   37: new 86	java/io/FileOutputStream
    //   40: dup
    //   41: aload 7
    //   43: invokespecial 89	java/io/FileOutputStream:<init>	(Ljava/io/File;)V
    //   46: astore 8
    //   48: sipush 4096
    //   51: newarray byte
    //   53: astore 14
    //   55: aload_3
    //   56: aload 14
    //   58: invokevirtual 95	java/io/InputStream:read	([B)I
    //   61: iconst_m1
    //   62: if_icmpeq +28 -> 90
    //   65: aload 8
    //   67: aload 14
    //   69: invokevirtual 101	java/io/OutputStream:write	([B)V
    //   72: goto -17 -> 55
    //   75: astore 6
    //   77: aload 6
    //   79: invokevirtual 104	java/lang/Exception:printStackTrace	()V
    //   82: aload 8
    //   84: invokevirtual 107	java/io/OutputStream:close	()V
    //   87: aload 7
    //   89: areturn
    //   90: aload 8
    //   92: invokevirtual 110	java/io/OutputStream:flush	()V
    //   95: aload 8
    //   97: invokevirtual 107	java/io/OutputStream:close	()V
    //   100: aload 7
    //   102: areturn
    //   103: astore 15
    //   105: aload 15
    //   107: invokevirtual 104	java/lang/Exception:printStackTrace	()V
    //   110: aload 7
    //   112: areturn
    //   113: astore 11
    //   115: aload 11
    //   117: invokevirtual 104	java/lang/Exception:printStackTrace	()V
    //   120: aload 7
    //   122: areturn
    //   123: astore 9
    //   125: aload 4
    //   127: invokevirtual 107	java/io/OutputStream:close	()V
    //   130: aload 9
    //   132: athrow
    //   133: astore 10
    //   135: aload 10
    //   137: invokevirtual 104	java/lang/Exception:printStackTrace	()V
    //   140: goto -10 -> 130
    //   143: astore 9
    //   145: aload 8
    //   147: astore 4
    //   149: goto -24 -> 125
    //   152: astore 5
    //   154: aload 5
    //   156: astore 6
    //   158: aconst_null
    //   159: astore 7
    //   161: aconst_null
    //   162: astore 8
    //   164: goto -87 -> 77
    //   167: astore 6
    //   169: aconst_null
    //   170: astore 8
    //   172: goto -95 -> 77
    //
    // Exception table:
    //   from	to	target	type
    //   48	55	75	java/lang/Exception
    //   55	72	75	java/lang/Exception
    //   90	95	75	java/lang/Exception
    //   95	100	103	java/lang/Exception
    //   82	87	113	java/lang/Exception
    //   3	33	123	finally
    //   37	48	123	finally
    //   125	130	133	java/lang/Exception
    //   48	55	143	finally
    //   55	72	143	finally
    //   77	82	143	finally
    //   90	95	143	finally
    //   3	33	152	java/lang/Exception
    //   37	48	167	java/lang/Exception
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.download.FileHelper
 * JD-Core Version:    0.6.2
 */