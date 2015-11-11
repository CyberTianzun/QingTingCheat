package com.tencent.a.b;

import android.net.Proxy;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public final class q
{
  private static int a = 0;
  private static boolean b;

  // ERROR //
  private static n a(HttpURLConnection paramHttpURLConnection, boolean paramBoolean)
    throws IOException
  {
    // Byte code:
    //   0: iconst_1
    //   1: istore_2
    //   2: iconst_0
    //   3: istore_3
    //   4: aconst_null
    //   5: astore 4
    //   7: new 17	com/tencent/a/b/n
    //   10: dup
    //   11: invokespecial 20	com/tencent/a/b/n:<init>	()V
    //   14: astore 5
    //   16: aload_0
    //   17: invokevirtual 26	java/net/HttpURLConnection:getContentType	()Ljava/lang/String;
    //   20: astore 8
    //   22: ldc 28
    //   24: astore 9
    //   26: aload 8
    //   28: ifnull +23 -> 51
    //   31: aload 8
    //   33: ldc 30
    //   35: invokevirtual 36	java/lang/String:split	(Ljava/lang/String;)[Ljava/lang/String;
    //   38: astore 10
    //   40: aload 10
    //   42: arraylength
    //   43: istore 11
    //   45: iconst_0
    //   46: istore 12
    //   48: goto +238 -> 286
    //   51: aload 5
    //   53: aload 9
    //   55: putfield 39	com/tencent/a/b/n:b	Ljava/lang/String;
    //   58: iload_1
    //   59: ifeq +33 -> 92
    //   62: aload 8
    //   64: ifnull +192 -> 256
    //   67: aload 8
    //   69: ldc 41
    //   71: invokevirtual 45	java/lang/String:contains	(Ljava/lang/CharSequence;)Z
    //   74: ifeq +182 -> 256
    //   77: aconst_null
    //   78: astore 4
    //   80: iload_2
    //   81: ifeq +11 -> 92
    //   84: aload_0
    //   85: invokevirtual 48	java/net/HttpURLConnection:disconnect	()V
    //   88: aload_0
    //   89: invokevirtual 51	java/net/HttpURLConnection:connect	()V
    //   92: aload_0
    //   93: invokevirtual 55	java/net/HttpURLConnection:getInputStream	()Ljava/io/InputStream;
    //   96: astore 4
    //   98: aload 4
    //   100: ifnull +88 -> 188
    //   103: aload 5
    //   105: iconst_0
    //   106: newarray byte
    //   108: putfield 58	com/tencent/a/b/n:a	[B
    //   111: sipush 1024
    //   114: newarray byte
    //   116: astore 13
    //   118: aload 4
    //   120: aload 13
    //   122: invokevirtual 64	java/io/InputStream:read	([B)I
    //   125: istore 14
    //   127: iload 14
    //   129: ifle +54 -> 183
    //   132: iload_3
    //   133: iload 14
    //   135: iadd
    //   136: istore_3
    //   137: iload_3
    //   138: newarray byte
    //   140: astore 16
    //   142: aload 5
    //   144: getfield 58	com/tencent/a/b/n:a	[B
    //   147: iconst_0
    //   148: aload 16
    //   150: iconst_0
    //   151: aload 5
    //   153: getfield 58	com/tencent/a/b/n:a	[B
    //   156: arraylength
    //   157: invokestatic 70	java/lang/System:arraycopy	(Ljava/lang/Object;ILjava/lang/Object;II)V
    //   160: aload 13
    //   162: iconst_0
    //   163: aload 16
    //   165: aload 5
    //   167: getfield 58	com/tencent/a/b/n:a	[B
    //   170: arraylength
    //   171: iload 14
    //   173: invokestatic 70	java/lang/System:arraycopy	(Ljava/lang/Object;ILjava/lang/Object;II)V
    //   176: aload 5
    //   178: aload 16
    //   180: putfield 58	com/tencent/a/b/n:a	[B
    //   183: iload 14
    //   185: ifgt -67 -> 118
    //   188: aload 4
    //   190: ifnull +8 -> 198
    //   193: aload 4
    //   195: invokevirtual 73	java/io/InputStream:close	()V
    //   198: aload 5
    //   200: areturn
    //   201: aload 10
    //   203: iload 12
    //   205: aaload
    //   206: astore 17
    //   208: aload 17
    //   210: ldc 75
    //   212: invokevirtual 45	java/lang/String:contains	(Ljava/lang/CharSequence;)Z
    //   215: ifeq +35 -> 250
    //   218: aload 17
    //   220: ldc 77
    //   222: invokevirtual 36	java/lang/String:split	(Ljava/lang/String;)[Ljava/lang/String;
    //   225: astore 18
    //   227: aload 18
    //   229: arraylength
    //   230: iload_2
    //   231: if_icmple -180 -> 51
    //   234: aload 18
    //   236: iconst_1
    //   237: aaload
    //   238: invokevirtual 80	java/lang/String:trim	()Ljava/lang/String;
    //   241: astore 19
    //   243: aload 19
    //   245: astore 9
    //   247: goto -196 -> 51
    //   250: iinc 12 1
    //   253: goto +33 -> 286
    //   256: iconst_0
    //   257: istore_2
    //   258: goto -181 -> 77
    //   261: astore 6
    //   263: aload 4
    //   265: ifnull +8 -> 273
    //   268: aload 4
    //   270: invokevirtual 73	java/io/InputStream:close	()V
    //   273: aload 6
    //   275: athrow
    //   276: astore 15
    //   278: aload 5
    //   280: areturn
    //   281: astore 7
    //   283: goto -10 -> 273
    //   286: iload 12
    //   288: iload 11
    //   290: if_icmplt -89 -> 201
    //   293: goto -242 -> 51
    //
    // Exception table:
    //   from	to	target	type
    //   7	22	261	finally
    //   31	45	261	finally
    //   51	58	261	finally
    //   67	77	261	finally
    //   84	92	261	finally
    //   92	98	261	finally
    //   103	118	261	finally
    //   118	127	261	finally
    //   137	183	261	finally
    //   201	243	261	finally
    //   193	198	276	java/io/IOException
    //   268	273	281	java/io/IOException
  }

  // ERROR //
  public static n a(boolean paramBoolean1, String paramString1, String paramString2, String paramString3, byte[] paramArrayOfByte, boolean paramBoolean2, boolean paramBoolean3)
    throws java.lang.Exception
  {
    // Byte code:
    //   0: iconst_1
    //   1: istore 7
    //   3: aconst_null
    //   4: astore 8
    //   6: invokestatic 91	com/tencent/a/b/l:d	()Z
    //   9: ifne +11 -> 20
    //   12: new 93	com/tencent/a/b/r
    //   15: dup
    //   16: invokespecial 94	com/tencent/a/b/r:<init>	()V
    //   19: athrow
    //   20: aload_1
    //   21: iload 6
    //   23: invokestatic 97	com/tencent/a/b/q:a	(Ljava/lang/String;Z)Ljava/net/HttpURLConnection;
    //   26: astore 14
    //   28: aload 14
    //   30: astore 12
    //   32: aconst_null
    //   33: invokestatic 102	com/tencent/a/b/b:a	(Ljava/lang/String;)Z
    //   36: istore 15
    //   38: aconst_null
    //   39: astore 8
    //   41: iload 15
    //   43: ifeq +250 -> 293
    //   46: aload 12
    //   48: invokevirtual 106	java/net/HttpURLConnection:getURL	()Ljava/net/URL;
    //   51: invokevirtual 111	java/net/URL:getHost	()Ljava/lang/String;
    //   54: invokestatic 102	com/tencent/a/b/b:a	(Ljava/lang/String;)Z
    //   57: ifeq +3 -> 60
    //   60: iload_0
    //   61: ifeq +285 -> 346
    //   64: aload 12
    //   66: ldc 113
    //   68: invokevirtual 117	java/net/HttpURLConnection:setRequestMethod	(Ljava/lang/String;)V
    //   71: aload 12
    //   73: invokestatic 122	com/tencent/a/b/k:a	()I
    //   76: invokevirtual 126	java/net/HttpURLConnection:setConnectTimeout	(I)V
    //   79: aload 12
    //   81: invokestatic 128	com/tencent/a/b/k:b	()I
    //   84: invokevirtual 131	java/net/HttpURLConnection:setReadTimeout	(I)V
    //   87: aload 12
    //   89: ldc 133
    //   91: aload_2
    //   92: invokevirtual 137	java/net/HttpURLConnection:setRequestProperty	(Ljava/lang/String;Ljava/lang/String;)V
    //   95: aload 12
    //   97: iconst_1
    //   98: invokevirtual 141	java/net/HttpURLConnection:setDoInput	(Z)V
    //   101: iload_0
    //   102: ifeq +6 -> 108
    //   105: iconst_0
    //   106: istore 7
    //   108: aload 12
    //   110: iload 7
    //   112: invokevirtual 144	java/net/HttpURLConnection:setDoOutput	(Z)V
    //   115: aload 12
    //   117: iconst_0
    //   118: invokevirtual 147	java/net/HttpURLConnection:setUseCaches	(Z)V
    //   121: iload 5
    //   123: ifeq +12 -> 135
    //   126: aload 12
    //   128: ldc 149
    //   130: ldc 151
    //   132: invokevirtual 137	java/net/HttpURLConnection:setRequestProperty	(Ljava/lang/String;Ljava/lang/String;)V
    //   135: aload 12
    //   137: invokestatic 154	com/tencent/a/b/k:a	(Ljava/net/HttpURLConnection;)V
    //   140: aload 12
    //   142: invokevirtual 51	java/net/HttpURLConnection:connect	()V
    //   145: invokestatic 157	com/tencent/a/b/k:c	()V
    //   148: iload_0
    //   149: ifne +55 -> 204
    //   152: aconst_null
    //   153: astore 8
    //   155: aload 4
    //   157: ifnull +47 -> 204
    //   160: aload 4
    //   162: arraylength
    //   163: istore 16
    //   165: aconst_null
    //   166: astore 8
    //   168: iload 16
    //   170: ifeq +34 -> 204
    //   173: new 159	java/io/DataOutputStream
    //   176: dup
    //   177: aload 12
    //   179: invokevirtual 163	java/net/HttpURLConnection:getOutputStream	()Ljava/io/OutputStream;
    //   182: invokespecial 166	java/io/DataOutputStream:<init>	(Ljava/io/OutputStream;)V
    //   185: astore 17
    //   187: aload 17
    //   189: aload 4
    //   191: invokevirtual 170	java/io/DataOutputStream:write	([B)V
    //   194: aload 17
    //   196: invokevirtual 173	java/io/DataOutputStream:flush	()V
    //   199: aload 17
    //   201: invokevirtual 174	java/io/DataOutputStream:close	()V
    //   204: aload 12
    //   206: invokevirtual 177	java/net/HttpURLConnection:getResponseCode	()I
    //   209: istore 18
    //   211: iload 18
    //   213: sipush 200
    //   216: if_icmpeq +11 -> 227
    //   219: iload 18
    //   221: sipush 206
    //   224: if_icmpne +146 -> 370
    //   227: invokestatic 179	com/tencent/a/b/k:d	()V
    //   230: aload 12
    //   232: iload_0
    //   233: invokestatic 181	com/tencent/a/b/q:a	(Ljava/net/HttpURLConnection;Z)Lcom/tencent/a/b/n;
    //   236: astore 19
    //   238: iconst_0
    //   239: istore 20
    //   241: aconst_null
    //   242: astore 8
    //   244: aload 19
    //   246: ifnull +29 -> 275
    //   249: aload 19
    //   251: getfield 58	com/tencent/a/b/n:a	[B
    //   254: astore 21
    //   256: iconst_0
    //   257: istore 20
    //   259: aconst_null
    //   260: astore 8
    //   262: aload 21
    //   264: ifnull +11 -> 275
    //   267: aload 19
    //   269: getfield 58	com/tencent/a/b/n:a	[B
    //   272: arraylength
    //   273: istore 20
    //   275: iload 20
    //   277: invokestatic 183	com/tencent/a/b/k:a	(I)V
    //   280: aload 12
    //   282: ifnull +8 -> 290
    //   285: aload 12
    //   287: invokevirtual 48	java/net/HttpURLConnection:disconnect	()V
    //   290: aload 19
    //   292: areturn
    //   293: aload 12
    //   295: ldc 185
    //   297: aconst_null
    //   298: invokevirtual 137	java/net/HttpURLConnection:setRequestProperty	(Ljava/lang/String;Ljava/lang/String;)V
    //   301: goto -241 -> 60
    //   304: astore 9
    //   306: aload 12
    //   308: astore 10
    //   310: iconst_1
    //   311: invokestatic 187	com/tencent/a/b/k:a	(Z)V
    //   314: aload 9
    //   316: athrow
    //   317: astore 11
    //   319: aload 10
    //   321: astore 12
    //   323: aload 8
    //   325: ifnull +8 -> 333
    //   328: aload 8
    //   330: invokevirtual 174	java/io/DataOutputStream:close	()V
    //   333: aload 12
    //   335: ifnull +8 -> 343
    //   338: aload 12
    //   340: invokevirtual 48	java/net/HttpURLConnection:disconnect	()V
    //   343: aload 11
    //   345: athrow
    //   346: aload 12
    //   348: ldc 189
    //   350: invokevirtual 117	java/net/HttpURLConnection:setRequestMethod	(Ljava/lang/String;)V
    //   353: goto -282 -> 71
    //   356: astore 13
    //   358: iconst_0
    //   359: invokestatic 187	com/tencent/a/b/k:a	(Z)V
    //   362: aload 13
    //   364: athrow
    //   365: astore 11
    //   367: goto -44 -> 323
    //   370: iload 18
    //   372: sipush 202
    //   375: if_icmpeq +75 -> 450
    //   378: iload 18
    //   380: sipush 201
    //   383: if_icmpeq +67 -> 450
    //   386: iload 18
    //   388: sipush 204
    //   391: if_icmpeq +59 -> 450
    //   394: iload 18
    //   396: sipush 205
    //   399: if_icmpeq +51 -> 450
    //   402: iload 18
    //   404: sipush 304
    //   407: if_icmpeq +43 -> 450
    //   410: iload 18
    //   412: sipush 305
    //   415: if_icmpeq +35 -> 450
    //   418: iload 18
    //   420: sipush 408
    //   423: if_icmpeq +27 -> 450
    //   426: iload 18
    //   428: sipush 502
    //   431: if_icmpeq +19 -> 450
    //   434: iload 18
    //   436: sipush 504
    //   439: if_icmpeq +11 -> 450
    //   442: iload 18
    //   444: sipush 503
    //   447: if_icmpne +13 -> 460
    //   450: new 15	java/io/IOException
    //   453: dup
    //   454: ldc 191
    //   456: invokespecial 193	java/io/IOException:<init>	(Ljava/lang/String;)V
    //   459: athrow
    //   460: new 85	com/tencent/a/b/p
    //   463: dup
    //   464: new 195	java/lang/StringBuilder
    //   467: dup
    //   468: ldc 197
    //   470: invokespecial 198	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   473: iload 18
    //   475: invokevirtual 202	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
    //   478: invokevirtual 205	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   481: invokespecial 206	com/tencent/a/b/p:<init>	(Ljava/lang/String;)V
    //   484: athrow
    //   485: astore 11
    //   487: aconst_null
    //   488: astore 8
    //   490: aconst_null
    //   491: astore 12
    //   493: goto -170 -> 323
    //   496: astore 11
    //   498: aload 17
    //   500: astore 8
    //   502: goto -179 -> 323
    //   505: astore 13
    //   507: aconst_null
    //   508: astore 8
    //   510: aconst_null
    //   511: astore 12
    //   513: goto -155 -> 358
    //   516: astore 13
    //   518: aload 17
    //   520: astore 8
    //   522: goto -164 -> 358
    //   525: astore 9
    //   527: aconst_null
    //   528: astore 8
    //   530: aconst_null
    //   531: astore 10
    //   533: goto -223 -> 310
    //   536: astore 9
    //   538: aload 17
    //   540: astore 8
    //   542: aload 12
    //   544: astore 10
    //   546: goto -236 -> 310
    //
    // Exception table:
    //   from	to	target	type
    //   32	38	304	com/tencent/a/b/p
    //   46	60	304	com/tencent/a/b/p
    //   64	71	304	com/tencent/a/b/p
    //   71	101	304	com/tencent/a/b/p
    //   108	121	304	com/tencent/a/b/p
    //   126	135	304	com/tencent/a/b/p
    //   135	148	304	com/tencent/a/b/p
    //   160	165	304	com/tencent/a/b/p
    //   173	187	304	com/tencent/a/b/p
    //   204	211	304	com/tencent/a/b/p
    //   227	238	304	com/tencent/a/b/p
    //   249	256	304	com/tencent/a/b/p
    //   267	275	304	com/tencent/a/b/p
    //   275	280	304	com/tencent/a/b/p
    //   293	301	304	com/tencent/a/b/p
    //   346	353	304	com/tencent/a/b/p
    //   450	460	304	com/tencent/a/b/p
    //   460	485	304	com/tencent/a/b/p
    //   310	317	317	finally
    //   32	38	356	java/lang/Exception
    //   46	60	356	java/lang/Exception
    //   64	71	356	java/lang/Exception
    //   71	101	356	java/lang/Exception
    //   108	121	356	java/lang/Exception
    //   126	135	356	java/lang/Exception
    //   135	148	356	java/lang/Exception
    //   160	165	356	java/lang/Exception
    //   173	187	356	java/lang/Exception
    //   204	211	356	java/lang/Exception
    //   227	238	356	java/lang/Exception
    //   249	256	356	java/lang/Exception
    //   267	275	356	java/lang/Exception
    //   275	280	356	java/lang/Exception
    //   293	301	356	java/lang/Exception
    //   346	353	356	java/lang/Exception
    //   450	460	356	java/lang/Exception
    //   460	485	356	java/lang/Exception
    //   32	38	365	finally
    //   46	60	365	finally
    //   64	71	365	finally
    //   71	101	365	finally
    //   108	121	365	finally
    //   126	135	365	finally
    //   135	148	365	finally
    //   160	165	365	finally
    //   173	187	365	finally
    //   204	211	365	finally
    //   227	238	365	finally
    //   249	256	365	finally
    //   267	275	365	finally
    //   275	280	365	finally
    //   293	301	365	finally
    //   346	353	365	finally
    //   358	365	365	finally
    //   450	460	365	finally
    //   460	485	365	finally
    //   20	28	485	finally
    //   187	204	496	finally
    //   20	28	505	java/lang/Exception
    //   187	204	516	java/lang/Exception
    //   20	28	525	com/tencent/a/b/p
    //   187	204	536	com/tencent/a/b/p
  }

  // ERROR //
  private static HttpURLConnection a(String paramString, boolean paramBoolean)
  {
    // Byte code:
    //   0: aconst_null
    //   1: astore_2
    //   2: new 108	java/net/URL
    //   5: dup
    //   6: aload_0
    //   7: invokespecial 209	java/net/URL:<init>	(Ljava/lang/String;)V
    //   10: astore_3
    //   11: invokestatic 211	com/tencent/a/b/l:c	()Z
    //   14: ifeq +32 -> 46
    //   17: iconst_0
    //   18: istore 5
    //   20: iload 5
    //   22: ifne +58 -> 80
    //   25: aload_3
    //   26: invokevirtual 215	java/net/URL:openConnection	()Ljava/net/URLConnection;
    //   29: checkcast 22	java/net/HttpURLConnection
    //   32: astore 26
    //   34: aload 26
    //   36: areturn
    //   37: astore 27
    //   39: aload 27
    //   41: invokevirtual 218	java/net/MalformedURLException:printStackTrace	()V
    //   44: aconst_null
    //   45: areturn
    //   46: invokestatic 223	com/tencent/a/b/m:a	()Lcom/tencent/a/b/m;
    //   49: pop
    //   50: invokestatic 228	android/net/Proxy:getDefaultHost	()Ljava/lang/String;
    //   53: invokestatic 102	com/tencent/a/b/b:a	(Ljava/lang/String;)Z
    //   56: ifeq +9 -> 65
    //   59: iconst_0
    //   60: istore 5
    //   62: goto -42 -> 20
    //   65: iconst_1
    //   66: istore 5
    //   68: goto -48 -> 20
    //   71: astore 25
    //   73: aload 25
    //   75: invokevirtual 229	java/io/IOException:printStackTrace	()V
    //   78: aconst_null
    //   79: areturn
    //   80: getstatic 12	com/tencent/a/b/q:a	I
    //   83: ifne +165 -> 248
    //   86: getstatic 231	com/tencent/a/b/q:b	Z
    //   89: ifne +159 -> 248
    //   92: iconst_1
    //   93: putstatic 231	com/tencent/a/b/q:b	Z
    //   96: new 108	java/net/URL
    //   99: dup
    //   100: ldc 233
    //   102: invokespecial 209	java/net/URL:<init>	(Ljava/lang/String;)V
    //   105: astore 12
    //   107: invokestatic 228	android/net/Proxy:getDefaultHost	()Ljava/lang/String;
    //   110: astore 13
    //   112: invokestatic 236	android/net/Proxy:getDefaultPort	()I
    //   115: istore 14
    //   117: iload 14
    //   119: iconst_m1
    //   120: if_icmpne +7 -> 127
    //   123: bipush 80
    //   125: istore 14
    //   127: new 238	java/net/InetSocketAddress
    //   130: dup
    //   131: aload 13
    //   133: iload 14
    //   135: invokespecial 241	java/net/InetSocketAddress:<init>	(Ljava/lang/String;I)V
    //   138: astore 15
    //   140: new 243	java/net/Proxy
    //   143: dup
    //   144: getstatic 249	java/net/Proxy$Type:HTTP	Ljava/net/Proxy$Type;
    //   147: aload 15
    //   149: invokespecial 252	java/net/Proxy:<init>	(Ljava/net/Proxy$Type;Ljava/net/SocketAddress;)V
    //   152: astore 16
    //   154: aload 12
    //   156: aload 16
    //   158: invokevirtual 255	java/net/URL:openConnection	(Ljava/net/Proxy;)Ljava/net/URLConnection;
    //   161: checkcast 22	java/net/HttpURLConnection
    //   164: astore 20
    //   166: aload 20
    //   168: ldc 113
    //   170: invokevirtual 117	java/net/HttpURLConnection:setRequestMethod	(Ljava/lang/String;)V
    //   173: aload 20
    //   175: sipush 15000
    //   178: invokevirtual 126	java/net/HttpURLConnection:setConnectTimeout	(I)V
    //   181: aload 20
    //   183: ldc_w 256
    //   186: invokevirtual 131	java/net/HttpURLConnection:setReadTimeout	(I)V
    //   189: aload 20
    //   191: ldc 133
    //   193: ldc_w 258
    //   196: invokevirtual 137	java/net/HttpURLConnection:setRequestProperty	(Ljava/lang/String;Ljava/lang/String;)V
    //   199: aload 20
    //   201: iconst_1
    //   202: invokevirtual 141	java/net/HttpURLConnection:setDoInput	(Z)V
    //   205: aload 20
    //   207: iconst_0
    //   208: invokevirtual 144	java/net/HttpURLConnection:setDoOutput	(Z)V
    //   211: aload 20
    //   213: iconst_0
    //   214: invokevirtual 147	java/net/HttpURLConnection:setUseCaches	(Z)V
    //   217: aload 20
    //   219: invokestatic 261	com/tencent/a/b/q:a	(Ljava/net/HttpURLConnection;)Z
    //   222: istore 23
    //   224: aload 20
    //   226: invokevirtual 51	java/net/HttpURLConnection:connect	()V
    //   229: iload 23
    //   231: ifeq +103 -> 334
    //   234: iconst_1
    //   235: invokestatic 262	com/tencent/a/b/q:a	(I)V
    //   238: aload 20
    //   240: ifnull +8 -> 248
    //   243: aload 20
    //   245: invokevirtual 48	java/net/HttpURLConnection:disconnect	()V
    //   248: getstatic 12	com/tencent/a/b/q:a	I
    //   251: tableswitch	default:+17 -> 268, 2:+135->386
    //   269: nop
    //   270: <illegal opcode>
    //   271: astore 8
    //   273: invokestatic 236	android/net/Proxy:getDefaultPort	()I
    //   276: istore 9
    //   278: iload 9
    //   280: iconst_m1
    //   281: if_icmpne +7 -> 288
    //   284: bipush 80
    //   286: istore 9
    //   288: new 238	java/net/InetSocketAddress
    //   291: dup
    //   292: aload 8
    //   294: iload 9
    //   296: invokespecial 241	java/net/InetSocketAddress:<init>	(Ljava/lang/String;I)V
    //   299: astore 10
    //   301: aload_3
    //   302: new 243	java/net/Proxy
    //   305: dup
    //   306: getstatic 249	java/net/Proxy$Type:HTTP	Ljava/net/Proxy$Type;
    //   309: aload 10
    //   311: invokespecial 252	java/net/Proxy:<init>	(Ljava/net/Proxy$Type;Ljava/net/SocketAddress;)V
    //   314: invokevirtual 255	java/net/URL:openConnection	(Ljava/net/Proxy;)Ljava/net/URLConnection;
    //   317: checkcast 22	java/net/HttpURLConnection
    //   320: astore 11
    //   322: aload 11
    //   324: areturn
    //   325: astore 24
    //   327: iconst_0
    //   328: putstatic 231	com/tencent/a/b/q:b	Z
    //   331: goto -83 -> 248
    //   334: iconst_2
    //   335: invokestatic 262	com/tencent/a/b/q:a	(I)V
    //   338: goto -100 -> 238
    //   341: astore 22
    //   343: aload 20
    //   345: astore 18
    //   347: aload 22
    //   349: astore 17
    //   351: aload 17
    //   353: invokevirtual 263	java/lang/Exception:printStackTrace	()V
    //   356: iconst_2
    //   357: invokestatic 262	com/tencent/a/b/q:a	(I)V
    //   360: aload 18
    //   362: ifnull -114 -> 248
    //   365: aload 18
    //   367: invokevirtual 48	java/net/HttpURLConnection:disconnect	()V
    //   370: goto -122 -> 248
    //   373: astore 19
    //   375: aload_2
    //   376: ifnull +7 -> 383
    //   379: aload_2
    //   380: invokevirtual 48	java/net/HttpURLConnection:disconnect	()V
    //   383: aload 19
    //   385: athrow
    //   386: aload_3
    //   387: aload_0
    //   388: invokestatic 266	com/tencent/a/b/q:a	(Ljava/net/URL;Ljava/lang/String;)Ljava/net/HttpURLConnection;
    //   391: astore 7
    //   393: aload 7
    //   395: areturn
    //   396: astore 6
    //   398: aload 6
    //   400: invokevirtual 229	java/io/IOException:printStackTrace	()V
    //   403: aconst_null
    //   404: areturn
    //   405: astore 21
    //   407: aload 20
    //   409: astore_2
    //   410: aload 21
    //   412: astore 19
    //   414: goto -39 -> 375
    //   417: astore 19
    //   419: aload 18
    //   421: astore_2
    //   422: goto -47 -> 375
    //   425: astore 17
    //   427: aconst_null
    //   428: astore 18
    //   430: goto -79 -> 351
    //
    // Exception table:
    //   from	to	target	type
    //   2	11	37	java/net/MalformedURLException
    //   25	34	71	java/io/IOException
    //   96	107	325	java/net/MalformedURLException
    //   166	229	341	java/lang/Exception
    //   234	238	341	java/lang/Exception
    //   334	338	341	java/lang/Exception
    //   154	166	373	finally
    //   248	268	396	java/io/IOException
    //   268	278	396	java/io/IOException
    //   288	322	396	java/io/IOException
    //   386	393	396	java/io/IOException
    //   166	229	405	finally
    //   234	238	405	finally
    //   334	338	405	finally
    //   351	360	417	finally
    //   154	166	425	java/lang/Exception
  }

  private static HttpURLConnection a(URL paramURL, String paramString)
    throws IOException
  {
    int i = 80;
    String str1 = Proxy.getDefaultHost();
    int j = Proxy.getDefaultPort();
    if (j == -1)
      j = i;
    String str2 = paramURL.getHost();
    int k = paramURL.getPort();
    if (k == -1);
    while (true)
    {
      String str3;
      if (paramString.indexOf(str2 + ":" + i) != -1)
        str3 = paramString.replaceFirst(str2 + ":" + i, str1 + ":" + j);
      try
      {
        while (true)
        {
          URL localURL = new URL(str3);
          HttpURLConnection localHttpURLConnection = (HttpURLConnection)localURL.openConnection();
          localHttpURLConnection.setRequestProperty("X-Online-Host", str2 + ":" + i);
          return localHttpURLConnection;
          str3 = paramString.replaceFirst(str2, str1 + ":" + j);
        }
      }
      catch (MalformedURLException localMalformedURLException)
      {
        return null;
      }
      i = k;
    }
  }

  private static void a(int paramInt)
  {
    if (a == paramInt)
      return;
    a = paramInt;
  }

  private static boolean a(HttpURLConnection paramHttpURLConnection)
    throws IOException
  {
    InputStream localInputStream = null;
    try
    {
      localInputStream = paramHttpURLConnection.getInputStream();
      boolean bool1 = paramHttpURLConnection.getContentType().equals("text/html");
      boolean bool3;
      if (!bool1)
      {
        if (localInputStream != null)
          localInputStream.close();
        bool3 = false;
        return bool3;
      }
      ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream();
      while (true)
      {
        if (localInputStream.available() <= 0)
        {
          boolean bool2 = new String(localByteArrayOutputStream.toByteArray()).trim().equals("1");
          bool3 = bool2;
          return bool3;
        }
        localByteArrayOutputStream.write(localInputStream.read());
      }
    }
    finally
    {
      if (localInputStream != null)
        localInputStream.close();
    }
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.tencent.a.b.q
 * JD-Core Version:    0.6.2
 */