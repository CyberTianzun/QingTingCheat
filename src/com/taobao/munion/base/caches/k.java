package com.taobao.munion.base.caches;

import android.content.Context;
import android.os.Environment;
import android.text.TextUtils;
import java.io.File;

public class k
{
  private static final String a = "FileManager";

  public static File a(Context paramContext, String paramString)
  {
    String str = paramContext.getFilesDir().getAbsolutePath();
    if (!TextUtils.isEmpty(paramString))
      str = str + File.separator + paramString;
    File localFile = new File(str);
    if (!localFile.exists())
      localFile.mkdirs();
    return localFile;
  }

  public static String a(Context paramContext, String paramString1, String paramString2)
  {
    String str = "";
    if (paramContext.getFilesDir() != null)
    {
      StringBuilder localStringBuilder = new StringBuilder().append(paramContext.getFilesDir().getAbsolutePath());
      if (!TextUtils.isEmpty(paramString1))
        localStringBuilder.append(File.separator).append(paramString1);
      str = File.separator + paramString2;
    }
    return str;
  }

  public static String a(Context paramContext, String paramString1, String paramString2, boolean paramBoolean)
  {
    if (paramBoolean)
    {
      StringBuilder localStringBuilder = new StringBuilder().append(Environment.getExternalStorageDirectory().toString()).append(File.separator);
      if (TextUtils.isEmpty(paramString1))
        localStringBuilder.append(paramContext.getPackageName());
      while (true)
      {
        return File.separator + paramString2;
        localStringBuilder.append(paramString1);
      }
    }
    return a(paramContext, paramString1, paramString2);
  }

  // ERROR //
  public static void a(java.io.InputStream paramInputStream, File paramFile)
  {
    // Byte code:
    //   0: aload_0
    //   1: ifnull +7 -> 8
    //   4: aload_1
    //   5: ifnonnull +4 -> 9
    //   8: return
    //   9: aconst_null
    //   10: astore_2
    //   11: aload_1
    //   12: invokevirtual 77	java/io/File:createNewFile	()Z
    //   15: pop
    //   16: new 79	java/io/FileOutputStream
    //   19: dup
    //   20: aload_1
    //   21: invokespecial 82	java/io/FileOutputStream:<init>	(Ljava/io/File;)V
    //   24: astore 4
    //   26: sipush 2048
    //   29: newarray byte
    //   31: astore 14
    //   33: aload_0
    //   34: aload 14
    //   36: iconst_0
    //   37: sipush 2048
    //   40: invokevirtual 88	java/io/InputStream:read	([BII)I
    //   43: istore 15
    //   45: iload 15
    //   47: iconst_m1
    //   48: if_icmpeq +37 -> 85
    //   51: aload 4
    //   53: aload 14
    //   55: iconst_0
    //   56: iload 15
    //   58: invokevirtual 92	java/io/FileOutputStream:write	([BII)V
    //   61: goto -28 -> 33
    //   64: astore 13
    //   66: aload 4
    //   68: ifnull -60 -> 8
    //   71: aload 4
    //   73: invokevirtual 95	java/io/FileOutputStream:close	()V
    //   76: return
    //   77: astore 5
    //   79: aload 5
    //   81: invokevirtual 98	java/io/IOException:printStackTrace	()V
    //   84: return
    //   85: aload 4
    //   87: ifnull -79 -> 8
    //   90: aload 4
    //   92: invokevirtual 95	java/io/FileOutputStream:close	()V
    //   95: return
    //   96: astore 16
    //   98: aload 16
    //   100: invokevirtual 98	java/io/IOException:printStackTrace	()V
    //   103: return
    //   104: astore 8
    //   106: aconst_null
    //   107: astore 4
    //   109: aload 4
    //   111: ifnull -103 -> 8
    //   114: aload 4
    //   116: invokevirtual 95	java/io/FileOutputStream:close	()V
    //   119: return
    //   120: astore 9
    //   122: aload 9
    //   124: invokevirtual 98	java/io/IOException:printStackTrace	()V
    //   127: return
    //   128: astore 6
    //   130: aload_2
    //   131: ifnull +7 -> 138
    //   134: aload_2
    //   135: invokevirtual 95	java/io/FileOutputStream:close	()V
    //   138: aload 6
    //   140: athrow
    //   141: astore 7
    //   143: aload 7
    //   145: invokevirtual 98	java/io/IOException:printStackTrace	()V
    //   148: goto -10 -> 138
    //   151: astore 12
    //   153: aload 4
    //   155: astore_2
    //   156: aload 12
    //   158: astore 6
    //   160: goto -30 -> 130
    //   163: astore 11
    //   165: goto -56 -> 109
    //   168: astore_3
    //   169: aconst_null
    //   170: astore 4
    //   172: goto -106 -> 66
    //
    // Exception table:
    //   from	to	target	type
    //   26	33	64	java/io/FileNotFoundException
    //   33	45	64	java/io/FileNotFoundException
    //   51	61	64	java/io/FileNotFoundException
    //   71	76	77	java/io/IOException
    //   90	95	96	java/io/IOException
    //   11	26	104	java/io/IOException
    //   114	119	120	java/io/IOException
    //   11	26	128	finally
    //   134	138	141	java/io/IOException
    //   26	33	151	finally
    //   33	45	151	finally
    //   51	61	151	finally
    //   26	33	163	java/io/IOException
    //   33	45	163	java/io/IOException
    //   51	61	163	java/io/IOException
    //   11	26	168	java/io/FileNotFoundException
  }

  // ERROR //
  public static void a(String paramString1, String paramString2)
  {
    // Byte code:
    //   0: aconst_null
    //   1: astore_2
    //   2: aload_0
    //   3: ifnull +7 -> 10
    //   6: aload_1
    //   7: ifnonnull +4 -> 11
    //   10: return
    //   11: aload_1
    //   12: ldc 101
    //   14: invokevirtual 107	java/lang/String:endsWith	(Ljava/lang/String;)Z
    //   17: ifne +23 -> 40
    //   20: new 33	java/lang/StringBuilder
    //   23: dup
    //   24: invokespecial 34	java/lang/StringBuilder:<init>	()V
    //   27: aload_1
    //   28: invokevirtual 38	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   31: ldc 101
    //   33: invokevirtual 38	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   36: invokevirtual 44	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   39: astore_1
    //   40: new 21	java/io/File
    //   43: dup
    //   44: aload_0
    //   45: invokespecial 47	java/io/File:<init>	(Ljava/lang/String;)V
    //   48: astore_3
    //   49: aload_3
    //   50: invokevirtual 51	java/io/File:exists	()Z
    //   53: ifeq -43 -> 10
    //   56: new 109	java/util/zip/ZipFile
    //   59: dup
    //   60: aload_3
    //   61: invokespecial 110	java/util/zip/ZipFile:<init>	(Ljava/io/File;)V
    //   64: astore 4
    //   66: aload 4
    //   68: invokevirtual 114	java/util/zip/ZipFile:entries	()Ljava/util/Enumeration;
    //   71: astore 10
    //   73: aconst_null
    //   74: astore 6
    //   76: aload 10
    //   78: invokeinterface 119 1 0
    //   83: ifeq +220 -> 303
    //   86: aload 10
    //   88: invokeinterface 123 1 0
    //   93: checkcast 125	java/util/zip/ZipEntry
    //   96: astore 13
    //   98: aload 13
    //   100: ifnonnull +30 -> 130
    //   103: aload_2
    //   104: ifnull +7 -> 111
    //   107: aload_2
    //   108: invokevirtual 126	java/io/InputStream:close	()V
    //   111: aload 6
    //   113: ifnull -103 -> 10
    //   116: aload 6
    //   118: invokevirtual 95	java/io/FileOutputStream:close	()V
    //   121: return
    //   122: astore 27
    //   124: aload 27
    //   126: invokevirtual 98	java/io/IOException:printStackTrace	()V
    //   129: return
    //   130: new 21	java/io/File
    //   133: dup
    //   134: new 33	java/lang/StringBuilder
    //   137: dup
    //   138: invokespecial 34	java/lang/StringBuilder:<init>	()V
    //   141: aload_1
    //   142: invokevirtual 38	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   145: aload 13
    //   147: invokevirtual 129	java/util/zip/ZipEntry:getName	()Ljava/lang/String;
    //   150: invokevirtual 38	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   153: invokevirtual 44	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   156: invokespecial 47	java/io/File:<init>	(Ljava/lang/String;)V
    //   159: astore 14
    //   161: aload 13
    //   163: invokevirtual 132	java/util/zip/ZipEntry:isDirectory	()Z
    //   166: ifeq +19 -> 185
    //   169: aload 14
    //   171: invokevirtual 54	java/io/File:mkdirs	()Z
    //   174: pop
    //   175: aload_2
    //   176: astore 18
    //   178: aload 6
    //   180: astore 24
    //   182: goto +251 -> 433
    //   185: aload 14
    //   187: invokevirtual 135	java/io/File:getParentFile	()Ljava/io/File;
    //   190: invokevirtual 51	java/io/File:exists	()Z
    //   193: ifne +12 -> 205
    //   196: aload 14
    //   198: invokevirtual 135	java/io/File:getParentFile	()Ljava/io/File;
    //   201: invokevirtual 54	java/io/File:mkdirs	()Z
    //   204: pop
    //   205: new 79	java/io/FileOutputStream
    //   208: dup
    //   209: aload 14
    //   211: invokespecial 82	java/io/FileOutputStream:<init>	(Ljava/io/File;)V
    //   214: astore 15
    //   216: aload 4
    //   218: aload 13
    //   220: invokevirtual 139	java/util/zip/ZipFile:getInputStream	(Ljava/util/zip/ZipEntry;)Ljava/io/InputStream;
    //   223: astore 17
    //   225: aload 17
    //   227: astore 18
    //   229: sipush 1024
    //   232: newarray byte
    //   234: astore 22
    //   236: aload 18
    //   238: aload 22
    //   240: iconst_0
    //   241: sipush 1024
    //   244: invokevirtual 88	java/io/InputStream:read	([BII)I
    //   247: istore 23
    //   249: iload 23
    //   251: ifle +178 -> 429
    //   254: aload 15
    //   256: aload 22
    //   258: iconst_0
    //   259: iload 23
    //   261: invokevirtual 92	java/io/FileOutputStream:write	([BII)V
    //   264: goto -28 -> 236
    //   267: astore 21
    //   269: aload 18
    //   271: astore_2
    //   272: aload 15
    //   274: astore 6
    //   276: aload_2
    //   277: ifnull +7 -> 284
    //   280: aload_2
    //   281: invokevirtual 126	java/io/InputStream:close	()V
    //   284: aload 6
    //   286: ifnull -276 -> 10
    //   289: aload 6
    //   291: invokevirtual 95	java/io/FileOutputStream:close	()V
    //   294: return
    //   295: astore 7
    //   297: aload 7
    //   299: invokevirtual 98	java/io/IOException:printStackTrace	()V
    //   302: return
    //   303: aload 4
    //   305: invokevirtual 140	java/util/zip/ZipFile:close	()V
    //   308: aload_2
    //   309: ifnull +7 -> 316
    //   312: aload_2
    //   313: invokevirtual 126	java/io/InputStream:close	()V
    //   316: aload 6
    //   318: ifnull -308 -> 10
    //   321: aload 6
    //   323: invokevirtual 95	java/io/FileOutputStream:close	()V
    //   326: return
    //   327: astore 12
    //   329: aload 12
    //   331: invokevirtual 98	java/io/IOException:printStackTrace	()V
    //   334: return
    //   335: astore 8
    //   337: aconst_null
    //   338: astore 6
    //   340: aload_2
    //   341: ifnull +7 -> 348
    //   344: aload_2
    //   345: invokevirtual 126	java/io/InputStream:close	()V
    //   348: aload 6
    //   350: ifnull +8 -> 358
    //   353: aload 6
    //   355: invokevirtual 95	java/io/FileOutputStream:close	()V
    //   358: aload 8
    //   360: athrow
    //   361: astore 9
    //   363: aload 9
    //   365: invokevirtual 98	java/io/IOException:printStackTrace	()V
    //   368: goto -10 -> 358
    //   371: astore 19
    //   373: aload 15
    //   375: astore 6
    //   377: aload 18
    //   379: astore 20
    //   381: aload 19
    //   383: astore 8
    //   385: aload 20
    //   387: astore_2
    //   388: goto -48 -> 340
    //   391: astore 8
    //   393: goto -53 -> 340
    //   396: astore 8
    //   398: aload 15
    //   400: astore 6
    //   402: goto -62 -> 340
    //   405: astore 5
    //   407: aconst_null
    //   408: astore_2
    //   409: aconst_null
    //   410: astore 6
    //   412: goto -136 -> 276
    //   415: astore 11
    //   417: goto -141 -> 276
    //   420: astore 16
    //   422: aload 15
    //   424: astore 6
    //   426: goto -150 -> 276
    //   429: aload 15
    //   431: astore 24
    //   433: aload 24
    //   435: astore 6
    //   437: aload 18
    //   439: astore_2
    //   440: goto -364 -> 76
    //
    // Exception table:
    //   from	to	target	type
    //   107	111	122	java/io/IOException
    //   116	121	122	java/io/IOException
    //   229	236	267	java/io/IOException
    //   236	249	267	java/io/IOException
    //   254	264	267	java/io/IOException
    //   280	284	295	java/io/IOException
    //   289	294	295	java/io/IOException
    //   312	316	327	java/io/IOException
    //   321	326	327	java/io/IOException
    //   56	73	335	finally
    //   344	348	361	java/io/IOException
    //   353	358	361	java/io/IOException
    //   229	236	371	finally
    //   236	249	371	finally
    //   254	264	371	finally
    //   76	98	391	finally
    //   130	175	391	finally
    //   185	205	391	finally
    //   205	216	391	finally
    //   303	308	391	finally
    //   216	225	396	finally
    //   56	73	405	java/io/IOException
    //   76	98	415	java/io/IOException
    //   130	175	415	java/io/IOException
    //   185	205	415	java/io/IOException
    //   205	216	415	java/io/IOException
    //   303	308	415	java/io/IOException
    //   216	225	420	java/io/IOException
  }

  // ERROR //
  public static boolean a(File paramFile1, File paramFile2)
  {
    // Byte code:
    //   0: aconst_null
    //   1: astore_2
    //   2: aload_0
    //   3: invokevirtual 51	java/io/File:exists	()Z
    //   6: istore 12
    //   8: iload 12
    //   10: ifne +44 -> 54
    //   13: iconst_0
    //   14: ifeq +7 -> 21
    //   17: aconst_null
    //   18: invokevirtual 146	java/io/FileInputStream:close	()V
    //   21: iconst_0
    //   22: istore 9
    //   24: iconst_0
    //   25: ifeq +7 -> 32
    //   28: aconst_null
    //   29: invokevirtual 95	java/io/FileOutputStream:close	()V
    //   32: iload 9
    //   34: ireturn
    //   35: astore 20
    //   37: aload 20
    //   39: invokevirtual 98	java/io/IOException:printStackTrace	()V
    //   42: goto -21 -> 21
    //   45: astore 19
    //   47: aload 19
    //   49: invokevirtual 98	java/io/IOException:printStackTrace	()V
    //   52: iconst_0
    //   53: ireturn
    //   54: aload_1
    //   55: invokevirtual 51	java/io/File:exists	()Z
    //   58: ifne +8 -> 66
    //   61: aload_1
    //   62: invokevirtual 77	java/io/File:createNewFile	()Z
    //   65: pop
    //   66: new 145	java/io/FileInputStream
    //   69: dup
    //   70: aload_0
    //   71: invokespecial 147	java/io/FileInputStream:<init>	(Ljava/io/File;)V
    //   74: astore 6
    //   76: new 79	java/io/FileOutputStream
    //   79: dup
    //   80: aload_1
    //   81: invokespecial 82	java/io/FileOutputStream:<init>	(Ljava/io/File;)V
    //   84: astore 13
    //   86: sipush 2048
    //   89: newarray byte
    //   91: astore 14
    //   93: aload 6
    //   95: aload 14
    //   97: invokevirtual 150	java/io/FileInputStream:read	([B)I
    //   100: istore 15
    //   102: iload 15
    //   104: iconst_m1
    //   105: if_icmpeq +60 -> 165
    //   108: aload 13
    //   110: aload 14
    //   112: iconst_0
    //   113: iload 15
    //   115: invokevirtual 92	java/io/FileOutputStream:write	([BII)V
    //   118: goto -25 -> 93
    //   121: astore_3
    //   122: aload 13
    //   124: astore_2
    //   125: aload 6
    //   127: astore 4
    //   129: aload_3
    //   130: invokevirtual 151	java/lang/Exception:printStackTrace	()V
    //   133: aload 4
    //   135: ifnull +8 -> 143
    //   138: aload 4
    //   140: invokevirtual 146	java/io/FileInputStream:close	()V
    //   143: iconst_0
    //   144: istore 9
    //   146: aload_2
    //   147: ifnull -115 -> 32
    //   150: aload_2
    //   151: invokevirtual 95	java/io/FileOutputStream:close	()V
    //   154: iconst_0
    //   155: ireturn
    //   156: astore 10
    //   158: aload 10
    //   160: invokevirtual 98	java/io/IOException:printStackTrace	()V
    //   163: iconst_0
    //   164: ireturn
    //   165: aload 13
    //   167: invokevirtual 154	java/io/FileOutputStream:flush	()V
    //   170: iconst_1
    //   171: istore 9
    //   173: aload 6
    //   175: ifnull +8 -> 183
    //   178: aload 6
    //   180: invokevirtual 146	java/io/FileInputStream:close	()V
    //   183: aload 13
    //   185: ifnull -153 -> 32
    //   188: aload 13
    //   190: invokevirtual 95	java/io/FileOutputStream:close	()V
    //   193: iload 9
    //   195: ireturn
    //   196: astore 16
    //   198: aload 16
    //   200: invokevirtual 98	java/io/IOException:printStackTrace	()V
    //   203: iload 9
    //   205: ireturn
    //   206: astore 17
    //   208: aload 17
    //   210: invokevirtual 98	java/io/IOException:printStackTrace	()V
    //   213: goto -30 -> 183
    //   216: astore 11
    //   218: aload 11
    //   220: invokevirtual 98	java/io/IOException:printStackTrace	()V
    //   223: goto -80 -> 143
    //   226: astore 5
    //   228: aconst_null
    //   229: astore 6
    //   231: aload 6
    //   233: ifnull +8 -> 241
    //   236: aload 6
    //   238: invokevirtual 146	java/io/FileInputStream:close	()V
    //   241: aload_2
    //   242: ifnull +7 -> 249
    //   245: aload_2
    //   246: invokevirtual 95	java/io/FileOutputStream:close	()V
    //   249: aload 5
    //   251: athrow
    //   252: astore 8
    //   254: aload 8
    //   256: invokevirtual 98	java/io/IOException:printStackTrace	()V
    //   259: goto -18 -> 241
    //   262: astore 7
    //   264: aload 7
    //   266: invokevirtual 98	java/io/IOException:printStackTrace	()V
    //   269: goto -20 -> 249
    //   272: astore 5
    //   274: aconst_null
    //   275: astore_2
    //   276: goto -45 -> 231
    //   279: astore 5
    //   281: aload 13
    //   283: astore_2
    //   284: goto -53 -> 231
    //   287: astore 5
    //   289: aload 4
    //   291: astore 6
    //   293: goto -62 -> 231
    //   296: astore_3
    //   297: aconst_null
    //   298: astore_2
    //   299: aconst_null
    //   300: astore 4
    //   302: goto -173 -> 129
    //   305: astore_3
    //   306: aload 6
    //   308: astore 4
    //   310: aconst_null
    //   311: astore_2
    //   312: goto -183 -> 129
    //
    // Exception table:
    //   from	to	target	type
    //   17	21	35	java/io/IOException
    //   28	32	45	java/io/IOException
    //   86	93	121	java/lang/Exception
    //   93	102	121	java/lang/Exception
    //   108	118	121	java/lang/Exception
    //   165	170	121	java/lang/Exception
    //   150	154	156	java/io/IOException
    //   188	193	196	java/io/IOException
    //   178	183	206	java/io/IOException
    //   138	143	216	java/io/IOException
    //   2	8	226	finally
    //   54	66	226	finally
    //   66	76	226	finally
    //   236	241	252	java/io/IOException
    //   245	249	262	java/io/IOException
    //   76	86	272	finally
    //   86	93	279	finally
    //   93	102	279	finally
    //   108	118	279	finally
    //   165	170	279	finally
    //   129	133	287	finally
    //   2	8	296	java/lang/Exception
    //   54	66	296	java/lang/Exception
    //   66	76	296	java/lang/Exception
    //   76	86	305	java/lang/Exception
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.taobao.munion.base.caches.k
 * JD-Core Version:    0.6.2
 */