package com.taobao.munion.base.caches;

import java.io.File;
import java.nio.ByteBuffer;

public class f
{
  // ERROR //
  public static boolean a(File paramFile, ByteBuffer paramByteBuffer)
    throws p
  {
    // Byte code:
    //   0: aconst_null
    //   1: astore_2
    //   2: aload_0
    //   3: invokevirtual 22	java/io/File:exists	()Z
    //   6: ifne +16 -> 22
    //   9: aload_0
    //   10: invokevirtual 26	java/io/File:getParentFile	()Ljava/io/File;
    //   13: invokevirtual 29	java/io/File:mkdirs	()Z
    //   16: pop
    //   17: aload_0
    //   18: invokevirtual 32	java/io/File:createNewFile	()Z
    //   21: pop
    //   22: new 34	java/io/FileOutputStream
    //   25: dup
    //   26: aload_0
    //   27: invokespecial 37	java/io/FileOutputStream:<init>	(Ljava/io/File;)V
    //   30: astore 4
    //   32: aload 4
    //   34: invokevirtual 41	java/io/FileOutputStream:getChannel	()Ljava/nio/channels/FileChannel;
    //   37: astore_2
    //   38: aload_1
    //   39: iconst_0
    //   40: invokevirtual 47	java/nio/ByteBuffer:position	(I)Ljava/nio/Buffer;
    //   43: pop
    //   44: aload_2
    //   45: aload_1
    //   46: invokevirtual 53	java/nio/channels/FileChannel:write	(Ljava/nio/ByteBuffer;)I
    //   49: pop
    //   50: aload_2
    //   51: iconst_1
    //   52: invokevirtual 57	java/nio/channels/FileChannel:force	(Z)V
    //   55: aload 4
    //   57: ifnull +8 -> 65
    //   60: aload 4
    //   62: invokevirtual 60	java/io/FileOutputStream:close	()V
    //   65: aload_2
    //   66: ifnull +7 -> 73
    //   69: aload_2
    //   70: invokevirtual 61	java/nio/channels/FileChannel:close	()V
    //   73: iconst_1
    //   74: ireturn
    //   75: astore 15
    //   77: aload 15
    //   79: invokevirtual 64	java/io/IOException:printStackTrace	()V
    //   82: goto -17 -> 65
    //   85: astore 14
    //   87: aload 14
    //   89: invokevirtual 64	java/io/IOException:printStackTrace	()V
    //   92: iconst_1
    //   93: ireturn
    //   94: astore 7
    //   96: aconst_null
    //   97: astore 4
    //   99: aload 7
    //   101: invokevirtual 68	java/lang/Exception:getMessage	()Ljava/lang/String;
    //   104: astore 8
    //   106: aload 8
    //   108: ifnull +44 -> 152
    //   111: aload 8
    //   113: ldc 70
    //   115: invokevirtual 76	java/lang/String:contains	(Ljava/lang/CharSequence;)Z
    //   118: ifeq +34 -> 152
    //   121: new 12	com/taobao/munion/base/caches/p
    //   124: dup
    //   125: ldc 78
    //   127: invokespecial 81	com/taobao/munion/base/caches/p:<init>	(Ljava/lang/String;)V
    //   130: athrow
    //   131: astore_3
    //   132: aload 4
    //   134: ifnull +8 -> 142
    //   137: aload 4
    //   139: invokevirtual 60	java/io/FileOutputStream:close	()V
    //   142: aload_2
    //   143: ifnull +7 -> 150
    //   146: aload_2
    //   147: invokevirtual 61	java/nio/channels/FileChannel:close	()V
    //   150: aload_3
    //   151: athrow
    //   152: aload_0
    //   153: ifnull +8 -> 161
    //   156: aload_0
    //   157: invokevirtual 84	java/io/File:delete	()Z
    //   160: pop
    //   161: aload 7
    //   163: invokevirtual 85	java/lang/Exception:printStackTrace	()V
    //   166: aload 4
    //   168: ifnull +8 -> 176
    //   171: aload 4
    //   173: invokevirtual 60	java/io/FileOutputStream:close	()V
    //   176: aload_2
    //   177: ifnull +7 -> 184
    //   180: aload_2
    //   181: invokevirtual 61	java/nio/channels/FileChannel:close	()V
    //   184: iconst_0
    //   185: ireturn
    //   186: astore 10
    //   188: aload 10
    //   190: invokevirtual 64	java/io/IOException:printStackTrace	()V
    //   193: goto -17 -> 176
    //   196: astore 9
    //   198: aload 9
    //   200: invokevirtual 64	java/io/IOException:printStackTrace	()V
    //   203: goto -19 -> 184
    //   206: astore 6
    //   208: aload 6
    //   210: invokevirtual 64	java/io/IOException:printStackTrace	()V
    //   213: goto -71 -> 142
    //   216: astore 5
    //   218: aload 5
    //   220: invokevirtual 64	java/io/IOException:printStackTrace	()V
    //   223: goto -73 -> 150
    //   226: astore_3
    //   227: aconst_null
    //   228: astore_2
    //   229: aconst_null
    //   230: astore 4
    //   232: goto -100 -> 132
    //   235: astore 7
    //   237: goto -138 -> 99
    //
    // Exception table:
    //   from	to	target	type
    //   60	65	75	java/io/IOException
    //   69	73	85	java/io/IOException
    //   2	22	94	java/lang/Exception
    //   22	32	94	java/lang/Exception
    //   32	55	131	finally
    //   99	106	131	finally
    //   111	131	131	finally
    //   156	161	131	finally
    //   161	166	131	finally
    //   171	176	186	java/io/IOException
    //   180	184	196	java/io/IOException
    //   137	142	206	java/io/IOException
    //   146	150	216	java/io/IOException
    //   2	22	226	finally
    //   22	32	226	finally
    //   32	55	235	java/lang/Exception
  }

  public static boolean a(String paramString)
  {
    if (paramString == null)
      return false;
    return new File(paramString).exists();
  }

  public static boolean a(String paramString, ByteBuffer paramByteBuffer)
    throws p
  {
    if (paramString == null)
      return false;
    return a(new File(paramString), paramByteBuffer);
  }

  // ERROR //
  public static byte[] a(File paramFile)
  {
    // Byte code:
    //   0: new 93	java/io/FileInputStream
    //   3: dup
    //   4: aload_0
    //   5: invokespecial 94	java/io/FileInputStream:<init>	(Ljava/io/File;)V
    //   8: astore_1
    //   9: aload_1
    //   10: invokevirtual 95	java/io/FileInputStream:getChannel	()Ljava/nio/channels/FileChannel;
    //   13: astore 11
    //   15: aload 11
    //   17: astore_3
    //   18: aload_3
    //   19: invokevirtual 99	java/nio/channels/FileChannel:size	()J
    //   22: l2i
    //   23: invokestatic 103	java/nio/ByteBuffer:allocate	(I)Ljava/nio/ByteBuffer;
    //   26: astore 12
    //   28: aload_3
    //   29: aload 12
    //   31: invokevirtual 106	java/nio/channels/FileChannel:read	(Ljava/nio/ByteBuffer;)I
    //   34: pop
    //   35: aload 12
    //   37: invokevirtual 110	java/nio/ByteBuffer:array	()[B
    //   40: astore 14
    //   42: aload 14
    //   44: astore 7
    //   46: aload_1
    //   47: ifnull +7 -> 54
    //   50: aload_1
    //   51: invokevirtual 111	java/io/FileInputStream:close	()V
    //   54: aload_3
    //   55: ifnull +7 -> 62
    //   58: aload_3
    //   59: invokevirtual 61	java/nio/channels/FileChannel:close	()V
    //   62: aload 7
    //   64: areturn
    //   65: astore 16
    //   67: aload 16
    //   69: invokevirtual 64	java/io/IOException:printStackTrace	()V
    //   72: goto -18 -> 54
    //   75: astore 15
    //   77: aload 15
    //   79: invokevirtual 64	java/io/IOException:printStackTrace	()V
    //   82: aload 7
    //   84: areturn
    //   85: astore_2
    //   86: aconst_null
    //   87: astore_3
    //   88: aconst_null
    //   89: astore_1
    //   90: aload_2
    //   91: invokevirtual 85	java/lang/Exception:printStackTrace	()V
    //   94: aload_1
    //   95: ifnull +7 -> 102
    //   98: aload_1
    //   99: invokevirtual 111	java/io/FileInputStream:close	()V
    //   102: aconst_null
    //   103: astore 7
    //   105: aload_3
    //   106: ifnull -44 -> 62
    //   109: aload_3
    //   110: invokevirtual 61	java/nio/channels/FileChannel:close	()V
    //   113: aconst_null
    //   114: areturn
    //   115: astore 8
    //   117: aload 8
    //   119: invokevirtual 64	java/io/IOException:printStackTrace	()V
    //   122: aconst_null
    //   123: areturn
    //   124: astore 9
    //   126: aload 9
    //   128: invokevirtual 64	java/io/IOException:printStackTrace	()V
    //   131: goto -29 -> 102
    //   134: astore 17
    //   136: aconst_null
    //   137: astore_3
    //   138: aconst_null
    //   139: astore_1
    //   140: aload 17
    //   142: astore 4
    //   144: aload_1
    //   145: ifnull +7 -> 152
    //   148: aload_1
    //   149: invokevirtual 111	java/io/FileInputStream:close	()V
    //   152: aload_3
    //   153: ifnull +7 -> 160
    //   156: aload_3
    //   157: invokevirtual 61	java/nio/channels/FileChannel:close	()V
    //   160: aload 4
    //   162: athrow
    //   163: astore 6
    //   165: aload 6
    //   167: invokevirtual 64	java/io/IOException:printStackTrace	()V
    //   170: goto -18 -> 152
    //   173: astore 5
    //   175: aload 5
    //   177: invokevirtual 64	java/io/IOException:printStackTrace	()V
    //   180: goto -20 -> 160
    //   183: astore 10
    //   185: aload 10
    //   187: astore 4
    //   189: aconst_null
    //   190: astore_3
    //   191: goto -47 -> 144
    //   194: astore 4
    //   196: goto -52 -> 144
    //   199: astore_2
    //   200: aconst_null
    //   201: astore_3
    //   202: goto -112 -> 90
    //   205: astore_2
    //   206: goto -116 -> 90
    //
    // Exception table:
    //   from	to	target	type
    //   50	54	65	java/io/IOException
    //   58	62	75	java/io/IOException
    //   0	9	85	java/lang/Exception
    //   109	113	115	java/io/IOException
    //   98	102	124	java/io/IOException
    //   0	9	134	finally
    //   148	152	163	java/io/IOException
    //   156	160	173	java/io/IOException
    //   9	15	183	finally
    //   18	42	194	finally
    //   90	94	194	finally
    //   9	15	199	java/lang/Exception
    //   18	42	205	java/lang/Exception
  }

  public static boolean b(File paramFile)
  {
    boolean bool1 = false;
    if (paramFile != null)
    {
      boolean bool2 = paramFile.exists();
      bool1 = false;
      if (bool2)
        if (paramFile.isDirectory())
        {
          File[] arrayOfFile = paramFile.listFiles();
          int i = arrayOfFile.length;
          int j = 0;
          if (j < i)
          {
            File localFile = arrayOfFile[j];
            if (localFile.isDirectory())
              b(localFile);
            while (true)
            {
              j++;
              break;
              try
              {
                localFile.delete();
              }
              catch (Exception localException2)
              {
                localException2.printStackTrace();
              }
            }
          }
        }
    }
    try
    {
      boolean bool3 = paramFile.delete();
      bool1 = bool3;
      return bool1;
    }
    catch (Exception localException1)
    {
      localException1.printStackTrace();
    }
    return false;
  }

  public static byte[] b(String paramString)
  {
    if (paramString == null)
      return null;
    return a(new File(paramString));
  }

  public static boolean c(String paramString)
  {
    if (paramString == null)
      return false;
    return b(new File(paramString));
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.taobao.munion.base.caches.f
 * JD-Core Version:    0.6.2
 */