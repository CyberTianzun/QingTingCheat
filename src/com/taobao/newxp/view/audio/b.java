package com.taobao.newxp.view.audio;

import android.os.Environment;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;

public class b
{
  private String a = "";
  private int b = 4096;

  public b()
  {
    if (Environment.getExternalStorageState().equals("mounted"))
      this.a = (Environment.getExternalStorageDirectory() + "/");
  }

  public File a(String paramString)
    throws IOException
  {
    File localFile = new File(this.a + paramString);
    localFile.createNewFile();
    return localFile;
  }

  // ERROR //
  public File a(String paramString1, String paramString2, java.io.InputStream paramInputStream)
  {
    // Byte code:
    //   0: aconst_null
    //   1: astore 4
    //   3: aload_0
    //   4: aload_1
    //   5: invokevirtual 68	com/taobao/newxp/view/audio/b:b	(Ljava/lang/String;)Ljava/io/File;
    //   8: pop
    //   9: aload_0
    //   10: new 34	java/lang/StringBuilder
    //   13: dup
    //   14: invokespecial 35	java/lang/StringBuilder:<init>	()V
    //   17: aload_1
    //   18: invokevirtual 48	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   21: aload_2
    //   22: invokevirtual 48	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   25: invokevirtual 51	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   28: invokevirtual 70	com/taobao/newxp/view/audio/b:a	(Ljava/lang/String;)Ljava/io/File;
    //   31: astore 13
    //   33: aload 13
    //   35: astore 7
    //   37: new 72	java/io/FileOutputStream
    //   40: dup
    //   41: aload 7
    //   43: invokespecial 75	java/io/FileOutputStream:<init>	(Ljava/io/File;)V
    //   46: astore 8
    //   48: aload_0
    //   49: getfield 18	com/taobao/newxp/view/audio/b:b	I
    //   52: newarray byte
    //   54: astore 14
    //   56: aload_3
    //   57: aload 14
    //   59: invokevirtual 81	java/io/InputStream:read	([B)I
    //   62: istore 15
    //   64: iload 15
    //   66: ifle +31 -> 97
    //   69: aload 8
    //   71: aload 14
    //   73: iconst_0
    //   74: iload 15
    //   76: invokevirtual 87	java/io/OutputStream:write	([BII)V
    //   79: goto -23 -> 56
    //   82: astore 6
    //   84: aload 6
    //   86: invokevirtual 90	java/lang/Exception:printStackTrace	()V
    //   89: aload 8
    //   91: invokevirtual 93	java/io/OutputStream:close	()V
    //   94: aload 7
    //   96: areturn
    //   97: aload 8
    //   99: invokevirtual 96	java/io/OutputStream:flush	()V
    //   102: aload 8
    //   104: invokevirtual 93	java/io/OutputStream:close	()V
    //   107: aload 7
    //   109: areturn
    //   110: astore 16
    //   112: aload 16
    //   114: invokevirtual 97	java/io/IOException:printStackTrace	()V
    //   117: aload 7
    //   119: areturn
    //   120: astore 11
    //   122: aload 11
    //   124: invokevirtual 97	java/io/IOException:printStackTrace	()V
    //   127: aload 7
    //   129: areturn
    //   130: astore 9
    //   132: aload 4
    //   134: invokevirtual 93	java/io/OutputStream:close	()V
    //   137: aload 9
    //   139: athrow
    //   140: astore 10
    //   142: aload 10
    //   144: invokevirtual 97	java/io/IOException:printStackTrace	()V
    //   147: goto -10 -> 137
    //   150: astore 9
    //   152: aload 8
    //   154: astore 4
    //   156: goto -24 -> 132
    //   159: astore 5
    //   161: aload 5
    //   163: astore 6
    //   165: aconst_null
    //   166: astore 7
    //   168: aconst_null
    //   169: astore 8
    //   171: goto -87 -> 84
    //   174: astore 6
    //   176: aconst_null
    //   177: astore 8
    //   179: goto -95 -> 84
    //
    // Exception table:
    //   from	to	target	type
    //   48	56	82	java/lang/Exception
    //   56	64	82	java/lang/Exception
    //   69	79	82	java/lang/Exception
    //   97	102	82	java/lang/Exception
    //   102	107	110	java/io/IOException
    //   89	94	120	java/io/IOException
    //   3	33	130	finally
    //   37	48	130	finally
    //   132	137	140	java/io/IOException
    //   48	56	150	finally
    //   56	64	150	finally
    //   69	79	150	finally
    //   84	89	150	finally
    //   97	102	150	finally
    //   3	33	159	java/lang/Exception
    //   37	48	174	java/lang/Exception
  }

  public String a()
  {
    return this.a;
  }

  public File b(String paramString)
  {
    File localFile = new File(this.a + paramString);
    localFile.mkdir();
    return localFile;
  }

  public boolean c(String paramString)
  {
    File localFile = new File(this.a + paramString);
    System.out.println(this.a + paramString);
    return localFile.exists();
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.taobao.newxp.view.audio.b
 * JD-Core Version:    0.6.2
 */