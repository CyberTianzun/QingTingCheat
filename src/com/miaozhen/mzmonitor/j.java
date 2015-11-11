package com.miaozhen.mzmonitor;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public final class j
{
  static long a;
  static int b = 0;
  private static Thread c;

  public static int a(Context paramContext)
  {
    return paramContext.getSharedPreferences("mzSdkProfilePrefs", 0).getInt("mzMaxLogItems", 100);
  }

  @Deprecated
  public static void a(Context paramContext, String paramString)
  {
    SharedPreferences.Editor localEditor = paramContext.getSharedPreferences("mzSdkProfilePrefs", 0).edit();
    localEditor.putString("mzProfileURI", paramString);
    localEditor.commit();
  }

  static void a(Context paramContext, String paramString1, String paramString2)
  {
    if (paramString1 != null)
    {
      SharedPreferences.Editor localEditor = paramContext.getSharedPreferences("mzSdkProfilePrefs", 0).edit();
      localEditor.putString("mzLatestLocation", paramString1);
      localEditor.putString("latestLocation", paramString2);
      localEditor.putLong("mzLocationUpdateTimestamp", c.a.a());
      localEditor.commit();
    }
  }

  public static int b(Context paramContext)
  {
    return paramContext.getSharedPreferences("mzSdkProfilePrefs", 0).getInt("mzMaxLogRetryTime", 20);
  }

  public static int c(Context paramContext)
  {
    return paramContext.getSharedPreferences("mzSdkProfilePrefs", 0).getInt("mzProfileVersion", 1);
  }

  public static String d(Context paramContext)
  {
    return paramContext.getSharedPreferences("mzSdkProfilePrefs", 0).getString("mzSignVersion", "1.1");
  }

  public static int e(Context paramContext)
  {
    return paramContext.getSharedPreferences("mzSdkProfilePrefs", 0).getInt("mzLogExpiresIn", 604800);
  }

  public static int f(Context paramContext)
  {
    return paramContext.getSharedPreferences("mzSdkProfilePrefs", 0).getInt("mzLocationServiceTimeout", 15);
  }

  public static String g(Context paramContext)
  {
    return paramContext.getSharedPreferences("mzSdkProfilePrefs", 0).getString("mzLatestLocation", "[UNKNOWN]");
  }

  static String h(Context paramContext)
  {
    return paramContext.getSharedPreferences("mzSdkProfilePrefs", 0).getString("latestLocation", "0x0");
  }

  public static boolean i(Context paramContext)
  {
    SharedPreferences localSharedPreferences = paramContext.getSharedPreferences("mzSdkProfilePrefs", 0);
    boolean bool2;
    if (localSharedPreferences.getString("mzConfigFile", null) == null)
      bool2 = true;
    boolean bool1;
    do
    {
      return bool2;
      long l1 = c.a.a();
      long l2 = localSharedPreferences.getLong("mzProfileUpdateTimestamp", l1);
      int i = localSharedPreferences.getInt("mzProfileExpiresIn", 86400);
      bool1 = l1 - l2 < i;
      bool2 = false;
    }
    while (bool1);
    return true;
  }

  public static boolean j(Context paramContext)
  {
    SharedPreferences localSharedPreferences = paramContext.getSharedPreferences("mzSdkProfilePrefs", 0);
    long l1 = c.a.a();
    long l2 = localSharedPreferences.getLong("mzLocationUpdateTimestamp", l1);
    boolean bool2;
    if (l2 == l1)
      bool2 = true;
    boolean bool1;
    do
    {
      return bool2;
      int i = localSharedPreferences.getInt("mzLocationExpiresIn", 300);
      if (l1 - l2 >= i)
        return true;
      bool1 = localSharedPreferences.getString("mzLatestLocation", "[UNKNOWN]").equals("[UNKNOWN]");
      bool2 = false;
    }
    while (!bool1);
    return true;
  }

  static String k(Context paramContext)
  {
    String str = paramContext.getSharedPreferences("mzSdkProfilePrefs", 0).getString("mzConfigFile", null);
    if (str == null)
      str = e.a;
    return str;
  }

  static void l(Context paramContext)
  {
    if ((c != null) && (c.isAlive()))
      return;
    Thread local1 = new Thread()
    {
      // ERROR //
      public final void run()
      {
        // Byte code:
        //   0: invokestatic 25	com/miaozhen/mzmonitor/j:a	()Ljava/lang/Thread;
        //   3: astore_1
        //   4: aload_1
        //   5: monitorenter
        //   6: aconst_null
        //   7: astore_2
        //   8: aconst_null
        //   9: astore_3
        //   10: aload_0
        //   11: getfield 14	com/miaozhen/mzmonitor/j$1:a	Landroid/content/Context;
        //   14: ldc 27
        //   16: iconst_0
        //   17: invokevirtual 33	android/content/Context:getSharedPreferences	(Ljava/lang/String;I)Landroid/content/SharedPreferences;
        //   20: astore 10
        //   22: new 35	java/net/URL
        //   25: dup
        //   26: aload 10
        //   28: ldc 37
        //   30: ldc 39
        //   32: invokeinterface 45 3 0
        //   37: invokespecial 48	java/net/URL:<init>	(Ljava/lang/String;)V
        //   40: invokevirtual 52	java/net/URL:openConnection	()Ljava/net/URLConnection;
        //   43: checkcast 54	java/net/HttpURLConnection
        //   46: astore 11
        //   48: aload 11
        //   50: sipush 5000
        //   53: invokevirtual 58	java/net/HttpURLConnection:setConnectTimeout	(I)V
        //   56: aload 11
        //   58: iconst_0
        //   59: invokevirtual 62	java/net/HttpURLConnection:setUseCaches	(Z)V
        //   62: aload 11
        //   64: ldc 64
        //   66: invokevirtual 67	java/net/HttpURLConnection:setRequestMethod	(Ljava/lang/String;)V
        //   69: aload 11
        //   71: invokevirtual 70	java/net/HttpURLConnection:connect	()V
        //   74: aload 11
        //   76: invokevirtual 74	java/net/HttpURLConnection:getResponseCode	()I
        //   79: istore 12
        //   81: aconst_null
        //   82: astore_3
        //   83: aconst_null
        //   84: astore_2
        //   85: iload 12
        //   87: ifle +111 -> 198
        //   90: aload 11
        //   92: invokevirtual 74	java/net/HttpURLConnection:getResponseCode	()I
        //   95: istore 13
        //   97: aconst_null
        //   98: astore_3
        //   99: aconst_null
        //   100: astore_2
        //   101: iload 13
        //   103: sipush 400
        //   106: if_icmpge +92 -> 198
        //   109: aload 11
        //   111: invokevirtual 78	java/net/HttpURLConnection:getInputStream	()Ljava/io/InputStream;
        //   114: astore_2
        //   115: new 80	java/io/BufferedReader
        //   118: dup
        //   119: new 82	java/io/InputStreamReader
        //   122: dup
        //   123: aload_2
        //   124: invokespecial 85	java/io/InputStreamReader:<init>	(Ljava/io/InputStream;)V
        //   127: invokespecial 88	java/io/BufferedReader:<init>	(Ljava/io/Reader;)V
        //   130: astore 14
        //   132: new 90	java/lang/StringBuffer
        //   135: dup
        //   136: invokespecial 91	java/lang/StringBuffer:<init>	()V
        //   139: astore 15
        //   141: aload 14
        //   143: invokevirtual 95	java/io/BufferedReader:readLine	()Ljava/lang/String;
        //   146: astore 16
        //   148: aload 16
        //   150: ifnonnull +67 -> 217
        //   153: aload 10
        //   155: invokeinterface 99 1 0
        //   160: ldc 101
        //   162: aload 15
        //   164: invokevirtual 104	java/lang/StringBuffer:toString	()Ljava/lang/String;
        //   167: invokeinterface 110 3 0
        //   172: invokeinterface 114 1 0
        //   177: pop
        //   178: aload_0
        //   179: getfield 14	com/miaozhen/mzmonitor/j$1:a	Landroid/content/Context;
        //   182: invokestatic 117	com/miaozhen/mzmonitor/j:m	(Landroid/content/Context;)V
        //   185: aload_0
        //   186: getfield 14	com/miaozhen/mzmonitor/j$1:a	Landroid/content/Context;
        //   189: invokestatic 122	com/miaozhen/mzmonitor/c:a	(Landroid/content/Context;)Lcom/miaozhen/mzmonitor/c;
        //   192: invokevirtual 125	com/miaozhen/mzmonitor/c:b	()V
        //   195: aload 14
        //   197: astore_3
        //   198: aload_2
        //   199: ifnull +7 -> 206
        //   202: aload_2
        //   203: invokevirtual 130	java/io/InputStream:close	()V
        //   206: aload_3
        //   207: ifnull +7 -> 214
        //   210: aload_3
        //   211: invokevirtual 131	java/io/BufferedReader:close	()V
        //   214: aload_1
        //   215: monitorexit
        //   216: return
        //   217: aload 15
        //   219: aload 16
        //   221: invokevirtual 135	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
        //   224: pop
        //   225: goto -84 -> 141
        //   228: astore 4
        //   230: aload 14
        //   232: astore_3
        //   233: ldc 137
        //   235: new 139	java/lang/StringBuilder
        //   238: dup
        //   239: ldc 141
        //   241: invokespecial 142	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
        //   244: aload 4
        //   246: invokevirtual 145	java/lang/StringBuilder:append	(Ljava/lang/Object;)Ljava/lang/StringBuilder;
        //   249: invokevirtual 146	java/lang/StringBuilder:toString	()Ljava/lang/String;
        //   252: invokestatic 152	android/util/Log:d	(Ljava/lang/String;Ljava/lang/String;)I
        //   255: pop
        //   256: aload_2
        //   257: ifnull +7 -> 264
        //   260: aload_2
        //   261: invokevirtual 130	java/io/InputStream:close	()V
        //   264: aload_3
        //   265: ifnull -51 -> 214
        //   268: aload_3
        //   269: invokevirtual 131	java/io/BufferedReader:close	()V
        //   272: goto -58 -> 214
        //   275: astore 9
        //   277: aload 9
        //   279: invokevirtual 155	java/io/IOException:printStackTrace	()V
        //   282: goto -68 -> 214
        //   285: astore 7
        //   287: aload_1
        //   288: monitorexit
        //   289: aload 7
        //   291: athrow
        //   292: astore 5
        //   294: aload_2
        //   295: ifnull +7 -> 302
        //   298: aload_2
        //   299: invokevirtual 130	java/io/InputStream:close	()V
        //   302: aload_3
        //   303: ifnull +7 -> 310
        //   306: aload_3
        //   307: invokevirtual 131	java/io/BufferedReader:close	()V
        //   310: aload 5
        //   312: athrow
        //   313: astore 6
        //   315: aload 6
        //   317: invokevirtual 155	java/io/IOException:printStackTrace	()V
        //   320: goto -10 -> 310
        //   323: astore 18
        //   325: aload 18
        //   327: invokevirtual 155	java/io/IOException:printStackTrace	()V
        //   330: goto -116 -> 214
        //   333: astore 5
        //   335: aload 14
        //   337: astore_3
        //   338: goto -44 -> 294
        //   341: astore 4
        //   343: aconst_null
        //   344: astore_3
        //   345: goto -112 -> 233
        //
        // Exception table:
        //   from	to	target	type
        //   132	141	228	java/lang/Exception
        //   141	148	228	java/lang/Exception
        //   153	195	228	java/lang/Exception
        //   217	225	228	java/lang/Exception
        //   260	264	275	java/io/IOException
        //   268	272	275	java/io/IOException
        //   202	206	285	finally
        //   210	214	285	finally
        //   214	216	285	finally
        //   260	264	285	finally
        //   268	272	285	finally
        //   277	282	285	finally
        //   298	302	285	finally
        //   306	310	285	finally
        //   310	313	285	finally
        //   315	320	285	finally
        //   325	330	285	finally
        //   10	81	292	finally
        //   90	97	292	finally
        //   109	132	292	finally
        //   233	256	292	finally
        //   298	302	313	java/io/IOException
        //   306	310	313	java/io/IOException
        //   202	206	323	java/io/IOException
        //   210	214	323	java/io/IOException
        //   132	141	333	finally
        //   141	148	333	finally
        //   153	195	333	finally
        //   217	225	333	finally
        //   10	81	341	java/lang/Exception
        //   90	97	341	java/lang/Exception
        //   109	132	341	java/lang/Exception
      }
    };
    c = local1;
    local1.start();
  }

  // ERROR //
  static void m(Context paramContext)
  {
    // Byte code:
    //   0: aconst_null
    //   1: astore_1
    //   2: aload_0
    //   3: ldc 17
    //   5: iconst_0
    //   6: invokevirtual 23	android/content/Context:getSharedPreferences	(Ljava/lang/String;I)Landroid/content/SharedPreferences;
    //   9: astore 7
    //   11: new 145	java/io/ByteArrayInputStream
    //   14: dup
    //   15: aload 7
    //   17: ldc 99
    //   19: aconst_null
    //   20: invokeinterface 82 3 0
    //   25: ldc 147
    //   27: invokevirtual 151	java/lang/String:getBytes	(Ljava/lang/String;)[B
    //   30: invokespecial 154	java/io/ByteArrayInputStream:<init>	([B)V
    //   33: astore 8
    //   35: invokestatic 160	android/util/Xml:newPullParser	()Lorg/xmlpull/v1/XmlPullParser;
    //   38: astore 9
    //   40: aload 9
    //   42: aload 8
    //   44: ldc 147
    //   46: invokeinterface 166 3 0
    //   51: aload 9
    //   53: invokeinterface 170 1 0
    //   58: istore 10
    //   60: iconst_0
    //   61: istore 11
    //   63: aconst_null
    //   64: astore 12
    //   66: iconst_0
    //   67: istore 13
    //   69: goto +551 -> 620
    //   72: aload 7
    //   74: invokeinterface 40 1 0
    //   79: astore 14
    //   81: aload 12
    //   83: ldc 172
    //   85: invokeinterface 177 2 0
    //   90: ifeq +28 -> 118
    //   93: aload 14
    //   95: ldc 72
    //   97: aload 12
    //   99: ldc 172
    //   101: invokeinterface 181 2 0
    //   106: checkcast 113	java/lang/String
    //   109: invokestatic 187	java/lang/Integer:parseInt	(Ljava/lang/String;)I
    //   112: invokeinterface 191 3 0
    //   117: pop
    //   118: aload 12
    //   120: ldc 193
    //   122: invokeinterface 177 2 0
    //   127: ifeq +25 -> 152
    //   130: aload 14
    //   132: ldc 76
    //   134: aload 12
    //   136: ldc 193
    //   138: invokeinterface 181 2 0
    //   143: checkcast 113	java/lang/String
    //   146: invokeinterface 48 3 0
    //   151: pop
    //   152: aload 12
    //   154: ldc 195
    //   156: invokeinterface 177 2 0
    //   161: ifeq +28 -> 189
    //   164: aload 14
    //   166: ldc 107
    //   168: aload 12
    //   170: ldc 195
    //   172: invokeinterface 181 2 0
    //   177: checkcast 113	java/lang/String
    //   180: invokestatic 187	java/lang/Integer:parseInt	(Ljava/lang/String;)I
    //   183: invokeinterface 191 3 0
    //   188: pop
    //   189: aload 12
    //   191: ldc 197
    //   193: invokeinterface 177 2 0
    //   198: ifeq +28 -> 226
    //   201: aload 14
    //   203: ldc 85
    //   205: aload 12
    //   207: ldc 197
    //   209: invokeinterface 181 2 0
    //   214: checkcast 113	java/lang/String
    //   217: invokestatic 187	java/lang/Integer:parseInt	(Ljava/lang/String;)I
    //   220: invokeinterface 191 3 0
    //   225: pop
    //   226: aload 12
    //   228: ldc 199
    //   230: invokeinterface 177 2 0
    //   235: ifeq +28 -> 263
    //   238: aload 14
    //   240: ldc 25
    //   242: aload 12
    //   244: ldc 199
    //   246: invokeinterface 181 2 0
    //   251: checkcast 113	java/lang/String
    //   254: invokestatic 187	java/lang/Integer:parseInt	(Ljava/lang/String;)I
    //   257: invokeinterface 191 3 0
    //   262: pop
    //   263: aload 12
    //   265: ldc 201
    //   267: invokeinterface 177 2 0
    //   272: ifeq +28 -> 300
    //   275: aload 14
    //   277: ldc 70
    //   279: aload 12
    //   281: ldc 201
    //   283: invokeinterface 181 2 0
    //   288: checkcast 113	java/lang/String
    //   291: invokestatic 187	java/lang/Integer:parseInt	(Ljava/lang/String;)I
    //   294: invokeinterface 191 3 0
    //   299: pop
    //   300: aload 12
    //   302: ldc 203
    //   304: invokeinterface 177 2 0
    //   309: ifeq +28 -> 337
    //   312: aload 14
    //   314: ldc 111
    //   316: aload 12
    //   318: ldc 203
    //   320: invokeinterface 181 2 0
    //   325: checkcast 113	java/lang/String
    //   328: invokestatic 187	java/lang/Integer:parseInt	(Ljava/lang/String;)I
    //   331: invokeinterface 191 3 0
    //   336: pop
    //   337: aload 12
    //   339: ldc 205
    //   341: invokeinterface 177 2 0
    //   346: ifeq +28 -> 374
    //   349: aload 14
    //   351: ldc 89
    //   353: aload 12
    //   355: ldc 205
    //   357: invokeinterface 181 2 0
    //   362: checkcast 113	java/lang/String
    //   365: invokestatic 187	java/lang/Integer:parseInt	(Ljava/lang/String;)I
    //   368: invokeinterface 191 3 0
    //   373: pop
    //   374: aload 14
    //   376: ldc 101
    //   378: invokestatic 64	com/miaozhen/mzmonitor/c$a:a	()J
    //   381: invokeinterface 68 4 0
    //   386: pop
    //   387: aload 14
    //   389: invokeinterface 52 1 0
    //   394: pop
    //   395: aload 8
    //   397: invokevirtual 210	java/io/InputStream:close	()V
    //   400: return
    //   401: iload 11
    //   403: ifne -331 -> 72
    //   406: iload 10
    //   408: tableswitch	default:+32 -> 440, 0:+44->452, 1:+32->440, 2:+56->464, 3:+103->511
    //   441: lconst_0
    //   442: invokeinterface 213 1 0
    //   447: istore 10
    //   449: goto +171 -> 620
    //   452: new 215	java/util/HashMap
    //   455: dup
    //   456: invokespecial 217	java/util/HashMap:<init>	()V
    //   459: astore 12
    //   461: goto -21 -> 440
    //   464: aload 9
    //   466: invokeinterface 221 1 0
    //   471: astore 27
    //   473: iload 13
    //   475: ifeq +20 -> 495
    //   478: aload 12
    //   480: aload 27
    //   482: aload 9
    //   484: invokeinterface 224 1 0
    //   489: invokeinterface 228 3 0
    //   494: pop
    //   495: aload 27
    //   497: ldc 230
    //   499: invokevirtual 117	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   502: ifeq -62 -> 440
    //   505: iconst_1
    //   506: istore 13
    //   508: goto -68 -> 440
    //   511: aload 9
    //   513: invokeinterface 221 1 0
    //   518: ldc 230
    //   520: invokevirtual 117	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   523: istore 26
    //   525: iload 26
    //   527: ifeq -87 -> 440
    //   530: iconst_1
    //   531: istore 11
    //   533: goto -93 -> 440
    //   536: astore 4
    //   538: ldc 232
    //   540: new 234	java/lang/StringBuilder
    //   543: dup
    //   544: ldc 236
    //   546: invokespecial 239	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   549: aload 4
    //   551: invokevirtual 243	java/lang/StringBuilder:append	(Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   554: invokevirtual 246	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   557: invokestatic 251	android/util/Log:d	(Ljava/lang/String;Ljava/lang/String;)I
    //   560: pop
    //   561: aload_1
    //   562: ifnull -162 -> 400
    //   565: aload_1
    //   566: invokevirtual 210	java/io/InputStream:close	()V
    //   569: return
    //   570: astore 6
    //   572: aload 6
    //   574: invokevirtual 254	java/io/IOException:printStackTrace	()V
    //   577: return
    //   578: astore_2
    //   579: aload_1
    //   580: ifnull +7 -> 587
    //   583: aload_1
    //   584: invokevirtual 210	java/io/InputStream:close	()V
    //   587: aload_2
    //   588: athrow
    //   589: astore_3
    //   590: aload_3
    //   591: invokevirtual 254	java/io/IOException:printStackTrace	()V
    //   594: goto -7 -> 587
    //   597: astore 17
    //   599: aload 17
    //   601: invokevirtual 254	java/io/IOException:printStackTrace	()V
    //   604: return
    //   605: astore_2
    //   606: aload 8
    //   608: astore_1
    //   609: goto -30 -> 579
    //   612: astore 4
    //   614: aload 8
    //   616: astore_1
    //   617: goto -79 -> 538
    //   620: iload 10
    //   622: iconst_1
    //   623: if_icmpne -222 -> 401
    //   626: goto -554 -> 72
    //
    // Exception table:
    //   from	to	target	type
    //   2	35	536	java/lang/Exception
    //   565	569	570	java/io/IOException
    //   2	35	578	finally
    //   538	561	578	finally
    //   583	587	589	java/io/IOException
    //   395	400	597	java/io/IOException
    //   35	60	605	finally
    //   72	118	605	finally
    //   118	152	605	finally
    //   152	189	605	finally
    //   189	226	605	finally
    //   226	263	605	finally
    //   263	300	605	finally
    //   300	337	605	finally
    //   337	374	605	finally
    //   374	395	605	finally
    //   440	449	605	finally
    //   452	461	605	finally
    //   464	473	605	finally
    //   478	495	605	finally
    //   495	505	605	finally
    //   511	525	605	finally
    //   35	60	612	java/lang/Exception
    //   72	118	612	java/lang/Exception
    //   118	152	612	java/lang/Exception
    //   152	189	612	java/lang/Exception
    //   189	226	612	java/lang/Exception
    //   226	263	612	java/lang/Exception
    //   263	300	612	java/lang/Exception
    //   300	337	612	java/lang/Exception
    //   337	374	612	java/lang/Exception
    //   374	395	612	java/lang/Exception
    //   440	449	612	java/lang/Exception
    //   452	461	612	java/lang/Exception
    //   464	473	612	java/lang/Exception
    //   478	495	612	java/lang/Exception
    //   495	505	612	java/lang/Exception
    //   511	525	612	java/lang/Exception
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.miaozhen.mzmonitor.j
 * JD-Core Version:    0.6.2
 */