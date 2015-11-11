package fm.qingting.utils;

public class UserRetention
{
  private static String eventname = "UserRetention";
  private static String filename = "user-retention.dat";

  // ERROR //
  public static void appStarted(android.content.Context paramContext)
  {
    // Byte code:
    //   0: aload_0
    //   1: invokevirtual 30	android/content/Context:fileList	()[Ljava/lang/String;
    //   4: invokestatic 36	java/util/Arrays:asList	([Ljava/lang/Object;)Ljava/util/List;
    //   7: getstatic 13	fm/qingting/utils/UserRetention:filename	Ljava/lang/String;
    //   10: invokeinterface 42 2 0
    //   15: ifne +127 -> 142
    //   18: getstatic 17	fm/qingting/utils/UserRetention:eventname	Ljava/lang/String;
    //   21: ldc 44
    //   23: invokestatic 50	android/util/Log:d	(Ljava/lang/String;Ljava/lang/String;)I
    //   26: pop
    //   27: invokestatic 56	java/util/Calendar:getInstance	()Ljava/util/Calendar;
    //   30: iconst_5
    //   31: invokevirtual 60	java/util/Calendar:get	(I)I
    //   34: invokestatic 66	java/lang/String:valueOf	(I)Ljava/lang/String;
    //   37: astore 15
    //   39: aload_0
    //   40: getstatic 13	fm/qingting/utils/UserRetention:filename	Ljava/lang/String;
    //   43: iconst_0
    //   44: invokevirtual 70	android/content/Context:openFileOutput	(Ljava/lang/String;I)Ljava/io/FileOutputStream;
    //   47: astore 17
    //   49: aload 17
    //   51: new 72	java/lang/StringBuilder
    //   54: dup
    //   55: invokespecial 73	java/lang/StringBuilder:<init>	()V
    //   58: aload 15
    //   60: invokevirtual 77	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   63: bipush 10
    //   65: invokevirtual 80	java/lang/StringBuilder:append	(C)Ljava/lang/StringBuilder;
    //   68: aload 15
    //   70: invokevirtual 77	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   73: bipush 10
    //   75: invokevirtual 80	java/lang/StringBuilder:append	(C)Ljava/lang/StringBuilder;
    //   78: invokevirtual 84	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   81: invokevirtual 88	java/lang/String:getBytes	()[B
    //   84: invokevirtual 94	java/io/FileOutputStream:write	([B)V
    //   87: aload 17
    //   89: invokevirtual 97	java/io/FileOutputStream:close	()V
    //   92: invokestatic 102	fm/qingting/utils/QTMSGManage:getInstance	()Lfm/qingting/utils/QTMSGManage;
    //   95: getstatic 17	fm/qingting/utils/UserRetention:eventname	Ljava/lang/String;
    //   98: new 72	java/lang/StringBuilder
    //   101: dup
    //   102: invokespecial 73	java/lang/StringBuilder:<init>	()V
    //   105: ldc 104
    //   107: invokevirtual 77	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   110: aload 15
    //   112: invokevirtual 77	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   115: bipush 95
    //   117: invokevirtual 80	java/lang/StringBuilder:append	(C)Ljava/lang/StringBuilder;
    //   120: aload 15
    //   122: invokevirtual 77	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   125: invokevirtual 84	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   128: invokevirtual 108	fm/qingting/utils/QTMSGManage:sendStatistcsMessage	(Ljava/lang/String;Ljava/lang/String;)V
    //   131: return
    //   132: astore 16
    //   134: aload 16
    //   136: invokevirtual 111	java/lang/Exception:printStackTrace	()V
    //   139: goto -47 -> 92
    //   142: getstatic 17	fm/qingting/utils/UserRetention:eventname	Ljava/lang/String;
    //   145: ldc 113
    //   147: invokestatic 50	android/util/Log:d	(Ljava/lang/String;Ljava/lang/String;)I
    //   150: pop
    //   151: new 115	java/io/BufferedReader
    //   154: dup
    //   155: new 117	java/io/InputStreamReader
    //   158: dup
    //   159: aload_0
    //   160: getstatic 13	fm/qingting/utils/UserRetention:filename	Ljava/lang/String;
    //   163: invokevirtual 121	android/content/Context:openFileInput	(Ljava/lang/String;)Ljava/io/FileInputStream;
    //   166: invokespecial 124	java/io/InputStreamReader:<init>	(Ljava/io/InputStream;)V
    //   169: invokespecial 127	java/io/BufferedReader:<init>	(Ljava/io/Reader;)V
    //   172: astore_2
    //   173: aload_2
    //   174: invokevirtual 130	java/io/BufferedReader:readLine	()Ljava/lang/String;
    //   177: invokevirtual 133	java/lang/String:trim	()Ljava/lang/String;
    //   180: astore 12
    //   182: aload 12
    //   184: astore 4
    //   186: aconst_null
    //   187: astore 6
    //   189: aload_2
    //   190: invokevirtual 130	java/io/BufferedReader:readLine	()Ljava/lang/String;
    //   193: astore 13
    //   195: aload 13
    //   197: ifnull +13 -> 210
    //   200: aload 13
    //   202: invokevirtual 133	java/lang/String:trim	()Ljava/lang/String;
    //   205: astore 6
    //   207: goto -18 -> 189
    //   210: aload_2
    //   211: invokevirtual 134	java/io/BufferedReader:close	()V
    //   214: invokestatic 56	java/util/Calendar:getInstance	()Ljava/util/Calendar;
    //   217: iconst_5
    //   218: invokevirtual 60	java/util/Calendar:get	(I)I
    //   221: invokestatic 66	java/lang/String:valueOf	(I)Ljava/lang/String;
    //   224: astore 7
    //   226: aload 7
    //   228: ifnull +139 -> 367
    //   231: aload 6
    //   233: ifnull +134 -> 367
    //   236: aload 6
    //   238: aload 7
    //   240: invokevirtual 137	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   243: ifne +124 -> 367
    //   246: getstatic 17	fm/qingting/utils/UserRetention:eventname	Ljava/lang/String;
    //   249: ldc 139
    //   251: invokestatic 50	android/util/Log:d	(Ljava/lang/String;Ljava/lang/String;)I
    //   254: pop
    //   255: aload_0
    //   256: getstatic 13	fm/qingting/utils/UserRetention:filename	Ljava/lang/String;
    //   259: ldc 140
    //   261: invokevirtual 70	android/content/Context:openFileOutput	(Ljava/lang/String;I)Ljava/io/FileOutputStream;
    //   264: astore 11
    //   266: aload 11
    //   268: new 72	java/lang/StringBuilder
    //   271: dup
    //   272: invokespecial 73	java/lang/StringBuilder:<init>	()V
    //   275: aload 7
    //   277: invokevirtual 77	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   280: bipush 10
    //   282: invokevirtual 80	java/lang/StringBuilder:append	(C)Ljava/lang/StringBuilder;
    //   285: invokevirtual 84	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   288: invokevirtual 88	java/lang/String:getBytes	()[B
    //   291: invokevirtual 94	java/io/FileOutputStream:write	([B)V
    //   294: aload 11
    //   296: invokevirtual 97	java/io/FileOutputStream:close	()V
    //   299: invokestatic 102	fm/qingting/utils/QTMSGManage:getInstance	()Lfm/qingting/utils/QTMSGManage;
    //   302: getstatic 17	fm/qingting/utils/UserRetention:eventname	Ljava/lang/String;
    //   305: new 72	java/lang/StringBuilder
    //   308: dup
    //   309: invokespecial 73	java/lang/StringBuilder:<init>	()V
    //   312: ldc 142
    //   314: invokevirtual 77	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   317: aload 4
    //   319: invokevirtual 77	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   322: bipush 95
    //   324: invokevirtual 80	java/lang/StringBuilder:append	(C)Ljava/lang/StringBuilder;
    //   327: aload 7
    //   329: invokevirtual 77	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   332: invokevirtual 84	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   335: invokevirtual 108	fm/qingting/utils/QTMSGManage:sendStatistcsMessage	(Ljava/lang/String;Ljava/lang/String;)V
    //   338: return
    //   339: astore_3
    //   340: aconst_null
    //   341: astore 4
    //   343: aload_3
    //   344: astore 5
    //   346: aconst_null
    //   347: astore 6
    //   349: aload 5
    //   351: invokevirtual 111	java/lang/Exception:printStackTrace	()V
    //   354: goto -140 -> 214
    //   357: astore 10
    //   359: aload 10
    //   361: invokevirtual 111	java/lang/Exception:printStackTrace	()V
    //   364: goto -65 -> 299
    //   367: getstatic 17	fm/qingting/utils/UserRetention:eventname	Ljava/lang/String;
    //   370: ldc 144
    //   372: invokestatic 50	android/util/Log:d	(Ljava/lang/String;Ljava/lang/String;)I
    //   375: pop
    //   376: return
    //   377: astore 5
    //   379: goto -30 -> 349
    //
    // Exception table:
    //   from	to	target	type
    //   39	92	132	java/lang/Exception
    //   151	182	339	java/lang/Exception
    //   255	299	357	java/lang/Exception
    //   189	195	377	java/lang/Exception
    //   200	207	377	java/lang/Exception
    //   210	214	377	java/lang/Exception
  }

  public static void setEventName(String paramString)
  {
    eventname = paramString;
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.utils.UserRetention
 * JD-Core Version:    0.6.2
 */