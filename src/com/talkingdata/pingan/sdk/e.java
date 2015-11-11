package com.talkingdata.pingan.sdk;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

final class e
{
  protected static final String a = "pingan";
  private static final int b = 5;
  private static final String c = "10";
  private static final String d = "pingantcagent.db";
  private static SQLiteDatabase e;
  private static int f;

  // ERROR //
  static long a(long paramLong)
  {
    // Byte code:
    //   0: aconst_null
    //   1: astore_2
    //   2: getstatic 29	com/talkingdata/pingan/sdk/e:e	Landroid/database/sqlite/SQLiteDatabase;
    //   5: astore 6
    //   7: getstatic 35	com/talkingdata/pingan/sdk/e$b:h	[Ljava/lang/String;
    //   10: astore 7
    //   12: iconst_1
    //   13: anewarray 37	java/lang/String
    //   16: astore 8
    //   18: aload 8
    //   20: iconst_0
    //   21: lload_0
    //   22: invokestatic 41	java/lang/String:valueOf	(J)Ljava/lang/String;
    //   25: aastore
    //   26: aload 6
    //   28: ldc 43
    //   30: aload 7
    //   32: ldc 45
    //   34: aload 8
    //   36: aconst_null
    //   37: aconst_null
    //   38: ldc 47
    //   40: invokevirtual 53	android/database/sqlite/SQLiteDatabase:query	(Ljava/lang/String;[Ljava/lang/String;Ljava/lang/String;[Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Landroid/database/Cursor;
    //   43: astore 9
    //   45: aload 9
    //   47: invokeinterface 59 1 0
    //   52: ifeq +39 -> 91
    //   55: aload 9
    //   57: invokeinterface 62 1 0
    //   62: ifne +29 -> 91
    //   65: aload 9
    //   67: bipush 6
    //   69: invokeinterface 66 2 0
    //   74: lstore 11
    //   76: aload 9
    //   78: ifnull +10 -> 88
    //   81: aload 9
    //   83: invokeinterface 69 1 0
    //   88: lload 11
    //   90: lreturn
    //   91: aload 9
    //   93: ifnull +10 -> 103
    //   96: aload 9
    //   98: invokeinterface 69 1 0
    //   103: lconst_0
    //   104: lreturn
    //   105: astore 4
    //   107: aconst_null
    //   108: astore 5
    //   110: aload 5
    //   112: ifnull -9 -> 103
    //   115: aload 5
    //   117: invokeinterface 69 1 0
    //   122: goto -19 -> 103
    //   125: astore_3
    //   126: aload_2
    //   127: ifnull +9 -> 136
    //   130: aload_2
    //   131: invokeinterface 69 1 0
    //   136: aload_3
    //   137: athrow
    //   138: astore_3
    //   139: aload 9
    //   141: astore_2
    //   142: goto -16 -> 126
    //   145: astore 10
    //   147: aload 9
    //   149: astore 5
    //   151: goto -41 -> 110
    //
    // Exception table:
    //   from	to	target	type
    //   2	45	105	java/lang/Exception
    //   2	45	125	finally
    //   45	76	138	finally
    //   45	76	145	java/lang/Exception
  }

  static long a(long paramLong, String paramString)
  {
    String[] arrayOfString1 = new String[3];
    arrayOfString1[0] = "Save Error";
    arrayOfString1[1] = ("errorTime:" + paramLong);
    arrayOfString1[2] = ("data:" + paramString);
    q.a(arrayOfString1);
    ContentValues localContentValues = new ContentValues();
    localContentValues.put("error_time", Long.valueOf(paramLong));
    try
    {
      al localal = new al();
      StringBuffer localStringBuffer = new StringBuffer("");
      long l = a(paramString, localal, localStringBuffer);
      if (0L == l)
      {
        localContentValues.put("message", paramString.getBytes("UTF-8"));
        localContentValues.put("repeat", Integer.valueOf(1));
        localContentValues.put("shorthashcode", localStringBuffer.toString());
        return e.insert("error_report", null, localContentValues);
      }
      localContentValues.put("repeat", Integer.valueOf(1 + localal.b));
      SQLiteDatabase localSQLiteDatabase = e;
      String[] arrayOfString2 = new String[1];
      arrayOfString2[0] = String.valueOf(l);
      int i = localSQLiteDatabase.update("error_report", localContentValues, "_id=?", arrayOfString2);
      return i;
    }
    catch (UnsupportedEncodingException localUnsupportedEncodingException)
    {
      localUnsupportedEncodingException.printStackTrace();
    }
    return 0L;
  }

  static long a(String paramString, long paramLong1, long paramLong2, int paramInt)
  {
    try
    {
      String[] arrayOfString = new String[3];
      arrayOfString[0] = "Save Session";
      arrayOfString[1] = ("sessionId:" + paramString);
      arrayOfString[2] = ("startTime:" + paramLong1);
      q.a(arrayOfString);
      ContentValues localContentValues = new ContentValues();
      localContentValues.put("session_id", paramString);
      localContentValues.put("start_time", Long.valueOf(paramLong1));
      localContentValues.put("duration", Integer.valueOf(0));
      localContentValues.put("is_launch", Integer.valueOf(0));
      localContentValues.put("interval", Long.valueOf(paramLong2));
      localContentValues.put("is_connected", Integer.valueOf(paramInt));
      e.insert("session", null, localContentValues);
      label148: return 0L;
    }
    catch (Exception localException)
    {
      break label148;
    }
  }

  // ERROR //
  static long a(String paramString, al paramal, StringBuffer paramStringBuffer)
  {
    // Byte code:
    //   0: getstatic 29	com/talkingdata/pingan/sdk/e:e	Landroid/database/sqlite/SQLiteDatabase;
    //   3: ldc 153
    //   5: getstatic 191	com/talkingdata/pingan/sdk/e$c:f	[Ljava/lang/String;
    //   8: aconst_null
    //   9: aconst_null
    //   10: aconst_null
    //   11: aconst_null
    //   12: ldc 47
    //   14: invokevirtual 53	android/database/sqlite/SQLiteDatabase:query	(Ljava/lang/String;[Ljava/lang/String;Ljava/lang/String;[Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Landroid/database/Cursor;
    //   17: astore 7
    //   19: aload 7
    //   21: astore 6
    //   23: aload_0
    //   24: ldc 193
    //   26: invokevirtual 197	java/lang/String:split	(Ljava/lang/String;)[Ljava/lang/String;
    //   29: astore 8
    //   31: aload 8
    //   33: arraylength
    //   34: istore 9
    //   36: iload 9
    //   38: iconst_3
    //   39: if_icmpge +21 -> 60
    //   42: lconst_0
    //   43: lstore 17
    //   45: aload 6
    //   47: ifnull +10 -> 57
    //   50: aload 6
    //   52: invokeinterface 69 1 0
    //   57: lload 17
    //   59: lreturn
    //   60: new 76	java/lang/StringBuilder
    //   63: dup
    //   64: invokespecial 77	java/lang/StringBuilder:<init>	()V
    //   67: aload 8
    //   69: iconst_0
    //   70: aaload
    //   71: invokevirtual 83	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   74: ldc 193
    //   76: invokevirtual 83	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   79: aload 8
    //   81: iconst_1
    //   82: aaload
    //   83: invokevirtual 83	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   86: ldc 193
    //   88: invokevirtual 83	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   91: aload 8
    //   93: iconst_2
    //   94: aaload
    //   95: invokevirtual 83	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   98: invokevirtual 90	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   101: astore 10
    //   103: aload_2
    //   104: aload 10
    //   106: invokestatic 202	com/talkingdata/pingan/sdk/aa:b	(Ljava/lang/String;)Ljava/lang/String;
    //   109: invokevirtual 205	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
    //   112: pop
    //   113: aload 6
    //   115: invokeinterface 59 1 0
    //   120: ifeq +205 -> 325
    //   123: aload 6
    //   125: invokeinterface 62 1 0
    //   130: ifne +195 -> 325
    //   133: aload_1
    //   134: aload 6
    //   136: iconst_1
    //   137: invokeinterface 66 2 0
    //   142: putfield 208	com/talkingdata/pingan/sdk/al:a	J
    //   145: aload_1
    //   146: aload 6
    //   148: iconst_2
    //   149: invokeinterface 212 2 0
    //   154: putfield 215	com/talkingdata/pingan/sdk/al:d	[B
    //   157: aload_1
    //   158: aload 6
    //   160: iconst_3
    //   161: invokeinterface 219 2 0
    //   166: putfield 159	com/talkingdata/pingan/sdk/al:b	I
    //   169: new 37	java/lang/String
    //   172: dup
    //   173: aload_1
    //   174: getfield 215	com/talkingdata/pingan/sdk/al:d	[B
    //   177: ldc 128
    //   179: invokespecial 222	java/lang/String:<init>	([BLjava/lang/String;)V
    //   182: astore 12
    //   184: aload 12
    //   186: invokevirtual 226	java/lang/String:length	()I
    //   189: aload 10
    //   191: invokevirtual 226	java/lang/String:length	()I
    //   194: if_icmplt -71 -> 123
    //   197: aload 12
    //   199: ldc 193
    //   201: invokevirtual 197	java/lang/String:split	(Ljava/lang/String;)[Ljava/lang/String;
    //   204: astore 13
    //   206: aload 13
    //   208: arraylength
    //   209: iconst_3
    //   210: if_icmplt -87 -> 123
    //   213: new 76	java/lang/StringBuilder
    //   216: dup
    //   217: invokespecial 77	java/lang/StringBuilder:<init>	()V
    //   220: aload 13
    //   222: iconst_0
    //   223: aaload
    //   224: invokevirtual 83	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   227: ldc 193
    //   229: invokevirtual 83	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   232: aload 13
    //   234: iconst_1
    //   235: aaload
    //   236: invokevirtual 83	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   239: ldc 193
    //   241: invokevirtual 83	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   244: aload 13
    //   246: iconst_2
    //   247: aaload
    //   248: invokevirtual 83	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   251: invokevirtual 90	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   254: aload 10
    //   256: invokevirtual 230	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   259: ifeq +32 -> 291
    //   262: aload 6
    //   264: iconst_0
    //   265: invokeinterface 66 2 0
    //   270: lstore 15
    //   272: lload 15
    //   274: lstore 17
    //   276: aload 6
    //   278: ifnull -221 -> 57
    //   281: aload 6
    //   283: invokeinterface 69 1 0
    //   288: lload 17
    //   290: lreturn
    //   291: aload 6
    //   293: invokeinterface 233 1 0
    //   298: pop
    //   299: goto -176 -> 123
    //   302: astore_3
    //   303: aload 6
    //   305: astore 4
    //   307: aload_3
    //   308: invokevirtual 234	java/lang/Exception:printStackTrace	()V
    //   311: aload 4
    //   313: ifnull +10 -> 323
    //   316: aload 4
    //   318: invokeinterface 69 1 0
    //   323: lconst_0
    //   324: lreturn
    //   325: aload 6
    //   327: ifnull -4 -> 323
    //   330: aload 6
    //   332: invokeinterface 69 1 0
    //   337: goto -14 -> 323
    //   340: astore 5
    //   342: aconst_null
    //   343: astore 6
    //   345: aload 6
    //   347: ifnull +10 -> 357
    //   350: aload 6
    //   352: invokeinterface 69 1 0
    //   357: aload 5
    //   359: athrow
    //   360: astore 5
    //   362: goto -17 -> 345
    //   365: astore 5
    //   367: aload 4
    //   369: astore 6
    //   371: goto -26 -> 345
    //   374: astore_3
    //   375: aconst_null
    //   376: astore 4
    //   378: goto -71 -> 307
    //
    // Exception table:
    //   from	to	target	type
    //   23	36	302	java/lang/Exception
    //   60	123	302	java/lang/Exception
    //   123	272	302	java/lang/Exception
    //   291	299	302	java/lang/Exception
    //   0	19	340	finally
    //   23	36	360	finally
    //   60	123	360	finally
    //   123	272	360	finally
    //   291	299	360	finally
    //   307	311	365	finally
    //   0	19	374	java/lang/Exception
  }

  static long a(String paramString1, String paramString2, long paramLong1, int paramInt, String paramString3, long paramLong2)
  {
    try
    {
      String[] arrayOfString1 = new String[6];
      arrayOfString1[0] = "Save Activity";
      arrayOfString1[1] = ("sessionId:" + paramString1);
      arrayOfString1[2] = ("name:" + paramString2);
      arrayOfString1[3] = ("start:" + paramLong1);
      arrayOfString1[4] = ("duration:" + paramInt);
      arrayOfString1[5] = ("refer:" + paramString3);
      q.a(arrayOfString1);
      ContentValues localContentValues = new ContentValues();
      localContentValues.put("session_id", paramString1);
      localContentValues.put("name", paramString2);
      localContentValues.put("start_time", Long.valueOf(paramLong1));
      localContentValues.put("duration", Integer.valueOf(paramInt));
      localContentValues.put("refer", paramString3);
      localContentValues.put("realtime", Long.valueOf(paramLong2));
      long l = e.insert("activity", null, localContentValues);
      String[] arrayOfString2 = new String[2];
      arrayOfString2[0] = "ActEvent";
      arrayOfString2[1] = ("Save activity: " + l + " sessionId:" + paramString1 + " name:" + paramString2);
      q.a(arrayOfString2);
      return l;
    }
    catch (Exception localException)
    {
    }
    return -1L;
  }

  // ERROR //
  static long a(List paramList)
  {
    // Byte code:
    //   0: aload_0
    //   1: invokeinterface 270 1 0
    //   6: istore_1
    //   7: iload_1
    //   8: ifne +9 -> 17
    //   11: lconst_0
    //   12: lstore 16
    //   14: lload 16
    //   16: lreturn
    //   17: iload_1
    //   18: iconst_1
    //   19: isub
    //   20: istore_2
    //   21: lconst_0
    //   22: lstore_3
    //   23: iload_2
    //   24: iflt +164 -> 188
    //   27: new 76	java/lang/StringBuilder
    //   30: dup
    //   31: invokespecial 77	java/lang/StringBuilder:<init>	()V
    //   34: astore 5
    //   36: aload 5
    //   38: ldc_w 272
    //   41: invokevirtual 83	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   44: pop
    //   45: getstatic 29	com/talkingdata/pingan/sdk/e:e	Landroid/database/sqlite/SQLiteDatabase;
    //   48: astore 10
    //   50: aload 5
    //   52: invokevirtual 90	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   55: astore 11
    //   57: iconst_1
    //   58: anewarray 37	java/lang/String
    //   61: astore 12
    //   63: aload 12
    //   65: iconst_0
    //   66: aload_0
    //   67: iload_2
    //   68: invokeinterface 276 2 0
    //   73: checkcast 278	com/talkingdata/pingan/sdk/c
    //   76: getfield 280	com/talkingdata/pingan/sdk/c:a	Ljava/lang/String;
    //   79: aastore
    //   80: aload 10
    //   82: aload 11
    //   84: aload 12
    //   86: invokevirtual 284	android/database/sqlite/SQLiteDatabase:rawQuery	(Ljava/lang/String;[Ljava/lang/String;)Landroid/database/Cursor;
    //   89: astore 13
    //   91: aload 13
    //   93: invokeinterface 59 1 0
    //   98: ifeq +47 -> 145
    //   101: aload 13
    //   103: iconst_0
    //   104: invokeinterface 66 2 0
    //   109: lstore 14
    //   111: lload 14
    //   113: lstore 16
    //   115: lload 16
    //   117: lconst_0
    //   118: lcmp
    //   119: ifeq +23 -> 142
    //   122: aload 13
    //   124: ifnull -110 -> 14
    //   127: aload 13
    //   129: invokeinterface 69 1 0
    //   134: lload 16
    //   136: lreturn
    //   137: astore 18
    //   139: lload 16
    //   141: lreturn
    //   142: lload 16
    //   144: lstore_3
    //   145: aload 13
    //   147: ifnull +43 -> 190
    //   150: aload 13
    //   152: invokeinterface 69 1 0
    //   157: goto +33 -> 190
    //   160: aload 9
    //   162: ifnull +10 -> 172
    //   165: aload 9
    //   167: invokeinterface 69 1 0
    //   172: aload 8
    //   174: athrow
    //   175: astore 6
    //   177: lload_3
    //   178: lreturn
    //   179: astore 8
    //   181: aload 13
    //   183: astore 9
    //   185: goto -25 -> 160
    //   188: lload_3
    //   189: lreturn
    //   190: iinc 2 255
    //   193: goto -170 -> 23
    //   196: astore 8
    //   198: aconst_null
    //   199: astore 9
    //   201: goto -41 -> 160
    //
    // Exception table:
    //   from	to	target	type
    //   127	134	137	java/lang/Exception
    //   27	45	175	java/lang/Exception
    //   150	157	175	java/lang/Exception
    //   165	172	175	java/lang/Exception
    //   172	175	175	java/lang/Exception
    //   91	111	179	finally
    //   45	91	196	finally
  }

  // ERROR //
  static List a(String paramString, long paramLong)
  {
    // Byte code:
    //   0: aconst_null
    //   1: astore_3
    //   2: new 287	java/util/ArrayList
    //   5: dup
    //   6: invokespecial 288	java/util/ArrayList:<init>	()V
    //   9: astore 4
    //   11: getstatic 29	com/talkingdata/pingan/sdk/e:e	Landroid/database/sqlite/SQLiteDatabase;
    //   14: ldc 43
    //   16: getstatic 35	com/talkingdata/pingan/sdk/e$b:h	[Ljava/lang/String;
    //   19: ldc_w 290
    //   22: iconst_1
    //   23: anewarray 37	java/lang/String
    //   26: dup
    //   27: iconst_0
    //   28: aload_0
    //   29: aastore
    //   30: aconst_null
    //   31: aconst_null
    //   32: ldc 47
    //   34: invokevirtual 53	android/database/sqlite/SQLiteDatabase:query	(Ljava/lang/String;[Ljava/lang/String;Ljava/lang/String;[Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Landroid/database/Cursor;
    //   37: astore 8
    //   39: aload 8
    //   41: astore 6
    //   43: aload 6
    //   45: invokeinterface 59 1 0
    //   50: ifeq +112 -> 162
    //   53: aload 6
    //   55: invokeinterface 62 1 0
    //   60: ifne +102 -> 162
    //   63: new 292	com/talkingdata/pingan/sdk/am
    //   66: dup
    //   67: invokespecial 293	com/talkingdata/pingan/sdk/am:<init>	()V
    //   70: astore 11
    //   72: aload 11
    //   74: aload 6
    //   76: iconst_1
    //   77: invokeinterface 297 2 0
    //   82: putfield 298	com/talkingdata/pingan/sdk/am:a	Ljava/lang/String;
    //   85: aload 11
    //   87: aload 6
    //   89: iconst_2
    //   90: invokeinterface 66 2 0
    //   95: putfield 300	com/talkingdata/pingan/sdk/am:b	J
    //   98: aload 11
    //   100: aload 6
    //   102: iconst_3
    //   103: invokeinterface 219 2 0
    //   108: putfield 302	com/talkingdata/pingan/sdk/am:c	I
    //   111: aload 11
    //   113: aload 6
    //   115: iconst_5
    //   116: invokeinterface 297 2 0
    //   121: putfield 304	com/talkingdata/pingan/sdk/am:d	Ljava/lang/String;
    //   124: aload 4
    //   126: aload 11
    //   128: invokeinterface 307 2 0
    //   133: pop
    //   134: aload 6
    //   136: invokeinterface 233 1 0
    //   141: pop
    //   142: goto -89 -> 53
    //   145: astore 10
    //   147: aload 6
    //   149: ifnull +10 -> 159
    //   152: aload 6
    //   154: invokeinterface 69 1 0
    //   159: aload 4
    //   161: areturn
    //   162: aload 6
    //   164: ifnull -5 -> 159
    //   167: aload 6
    //   169: invokeinterface 69 1 0
    //   174: aload 4
    //   176: areturn
    //   177: astore 7
    //   179: aload_3
    //   180: ifnull +9 -> 189
    //   183: aload_3
    //   184: invokeinterface 69 1 0
    //   189: aload 7
    //   191: athrow
    //   192: astore 9
    //   194: aload 6
    //   196: astore_3
    //   197: aload 9
    //   199: astore 7
    //   201: goto -22 -> 179
    //   204: astore 5
    //   206: aconst_null
    //   207: astore 6
    //   209: goto -62 -> 147
    //
    // Exception table:
    //   from	to	target	type
    //   43	53	145	java/lang/Exception
    //   53	142	145	java/lang/Exception
    //   11	39	177	finally
    //   43	53	192	finally
    //   53	142	192	finally
    //   11	39	204	java/lang/Exception
  }

  // ERROR //
  private static Map a(byte[] paramArrayOfByte)
  {
    // Byte code:
    //   0: aload_0
    //   1: ifnull +8 -> 9
    //   4: aload_0
    //   5: arraylength
    //   6: ifne +5 -> 11
    //   9: aconst_null
    //   10: areturn
    //   11: new 310	java/util/HashMap
    //   14: dup
    //   15: invokespecial 311	java/util/HashMap:<init>	()V
    //   18: astore_1
    //   19: new 313	java/io/ByteArrayInputStream
    //   22: dup
    //   23: aload_0
    //   24: invokespecial 316	java/io/ByteArrayInputStream:<init>	([B)V
    //   27: astore_2
    //   28: new 318	java/io/DataInputStream
    //   31: dup
    //   32: aload_2
    //   33: invokespecial 321	java/io/DataInputStream:<init>	(Ljava/io/InputStream;)V
    //   36: astore_3
    //   37: aload_3
    //   38: invokevirtual 324	java/io/DataInputStream:readInt	()I
    //   41: istore 8
    //   43: iconst_0
    //   44: istore 9
    //   46: iload 9
    //   48: iload 8
    //   50: if_icmpge +78 -> 128
    //   53: aload_3
    //   54: invokevirtual 327	java/io/DataInputStream:readUTF	()Ljava/lang/String;
    //   57: astore 10
    //   59: aload_3
    //   60: invokevirtual 324	java/io/DataInputStream:readInt	()I
    //   63: istore 11
    //   65: iload 11
    //   67: bipush 66
    //   69: if_icmpne +29 -> 98
    //   72: aload_3
    //   73: invokevirtual 331	java/io/DataInputStream:readDouble	()D
    //   76: invokestatic 336	java/lang/Double:valueOf	(D)Ljava/lang/Double;
    //   79: astore 12
    //   81: aload_1
    //   82: aload 10
    //   84: aload 12
    //   86: invokeinterface 341 3 0
    //   91: pop
    //   92: iinc 9 1
    //   95: goto -49 -> 46
    //   98: iload 11
    //   100: bipush 88
    //   102: if_icmpne +16 -> 118
    //   105: aload_3
    //   106: invokevirtual 327	java/io/DataInputStream:readUTF	()Ljava/lang/String;
    //   109: astore 14
    //   111: aload 14
    //   113: astore 12
    //   115: goto -34 -> 81
    //   118: aload_2
    //   119: invokestatic 346	com/talkingdata/pingan/sdk/PAAgent:a	(Ljava/io/Closeable;)V
    //   122: aload_3
    //   123: invokestatic 346	com/talkingdata/pingan/sdk/PAAgent:a	(Ljava/io/Closeable;)V
    //   126: aconst_null
    //   127: areturn
    //   128: aload_2
    //   129: invokestatic 346	com/talkingdata/pingan/sdk/PAAgent:a	(Ljava/io/Closeable;)V
    //   132: aload_3
    //   133: invokestatic 346	com/talkingdata/pingan/sdk/PAAgent:a	(Ljava/io/Closeable;)V
    //   136: aload_1
    //   137: areturn
    //   138: astore 18
    //   140: aconst_null
    //   141: astore 5
    //   143: aconst_null
    //   144: astore 6
    //   146: aload 6
    //   148: invokestatic 346	com/talkingdata/pingan/sdk/PAAgent:a	(Ljava/io/Closeable;)V
    //   151: aload 5
    //   153: invokestatic 346	com/talkingdata/pingan/sdk/PAAgent:a	(Ljava/io/Closeable;)V
    //   156: aconst_null
    //   157: areturn
    //   158: astore 17
    //   160: aconst_null
    //   161: astore_3
    //   162: aconst_null
    //   163: astore_2
    //   164: aload 17
    //   166: astore 7
    //   168: aload_2
    //   169: invokestatic 346	com/talkingdata/pingan/sdk/PAAgent:a	(Ljava/io/Closeable;)V
    //   172: aload_3
    //   173: invokestatic 346	com/talkingdata/pingan/sdk/PAAgent:a	(Ljava/io/Closeable;)V
    //   176: aload 7
    //   178: athrow
    //   179: astore 16
    //   181: aload 16
    //   183: astore 7
    //   185: aconst_null
    //   186: astore_3
    //   187: goto -19 -> 168
    //   190: astore 7
    //   192: goto -24 -> 168
    //   195: astore 15
    //   197: aload_2
    //   198: astore 6
    //   200: aconst_null
    //   201: astore 5
    //   203: goto -57 -> 146
    //   206: astore 4
    //   208: aload_3
    //   209: astore 5
    //   211: aload_2
    //   212: astore 6
    //   214: goto -68 -> 146
    //
    // Exception table:
    //   from	to	target	type
    //   11	28	138	java/lang/Exception
    //   11	28	158	finally
    //   28	37	179	finally
    //   37	43	190	finally
    //   53	65	190	finally
    //   72	81	190	finally
    //   81	92	190	finally
    //   105	111	190	finally
    //   28	37	195	java/lang/Exception
    //   37	43	206	java/lang/Exception
    //   53	65	206	java/lang/Exception
    //   72	81	206	java/lang/Exception
    //   81	92	206	java/lang/Exception
    //   105	111	206	java/lang/Exception
  }

  static void a()
  {
    e.setVersion(5);
    a.a(e);
    b.a(e);
    d.a(e);
    c.a(e);
  }

  static void a(long paramLong1, long paramLong2)
  {
    long l = (paramLong2 - a(paramLong1)) / 1000L;
    String[] arrayOfString1 = new String[3];
    arrayOfString1[0] = "Update Activity Duration";
    arrayOfString1[1] = ("id:" + paramLong1);
    arrayOfString1[2] = ("duration:" + l);
    q.a(arrayOfString1);
    ContentValues localContentValues = new ContentValues();
    localContentValues.put("duration", Long.valueOf(l));
    try
    {
      SQLiteDatabase localSQLiteDatabase = e;
      String[] arrayOfString2 = new String[1];
      arrayOfString2[0] = String.valueOf(paramLong1);
      localSQLiteDatabase.update("activity", localContentValues, "_id=?", arrayOfString2);
      return;
    }
    catch (Exception localException)
    {
    }
  }

  static void a(Context paramContext)
  {
    while (true)
    {
      try
      {
        if (e == null)
        {
          File localFile = new File(paramContext.getFilesDir(), "pingantcagent.db");
          boolean bool = localFile.exists();
          if (!localFile.getParentFile().exists())
            localFile.getParentFile().mkdirs();
          e = SQLiteDatabase.openOrCreateDatabase(localFile, null);
          e.setLockingEnabled(true);
          e.setMaximumSize(1000000L);
          f = 1;
          if (!bool)
          {
            a();
            return;
          }
          if (5 <= e.getVersion())
            continue;
          e.execSQL("DROP TABLE IF EXISTS error_report");
          e.execSQL("DROP TABLE IF EXISTS app_event");
          e.execSQL("DROP TABLE IF EXISTS session");
          e.execSQL("DROP TABLE IF EXISTS activity");
          a();
          continue;
        }
      }
      finally
      {
      }
      f = 1 + f;
    }
  }

  static void a(String paramString)
  {
    ContentValues localContentValues = new ContentValues();
    localContentValues.put("is_launch", Integer.valueOf(1));
    e.update("session", localContentValues, "session_id=?", new String[] { paramString });
  }

  static void a(String paramString, int paramInt)
  {
    String[] arrayOfString = new String[3];
    arrayOfString[0] = "Update Session Duration";
    arrayOfString[1] = ("sessionId:" + paramString);
    arrayOfString[2] = ("duration:" + paramInt);
    q.a(arrayOfString);
    ContentValues localContentValues = new ContentValues();
    localContentValues.put("duration", Integer.valueOf(paramInt));
    try
    {
      e.update("session", localContentValues, "session_id=?", new String[] { paramString });
      return;
    }
    catch (Exception localException)
    {
    }
  }

  static boolean a(String paramString1, String paramString2, String paramString3, long paramLong, Map paramMap)
  {
    try
    {
      String[] arrayOfString = new String[4];
      arrayOfString[0] = "Save App Event";
      arrayOfString[1] = ("sessionId:" + paramString1);
      arrayOfString[2] = ("event:" + paramString2);
      arrayOfString[3] = ("label:" + paramString3);
      q.a(arrayOfString);
      ContentValues localContentValues = new ContentValues();
      localContentValues.put("event_id", paramString2);
      localContentValues.put("event_label", paramString3);
      localContentValues.put("session_id", paramString1);
      localContentValues.put("occurtime", Long.valueOf(paramLong));
      localContentValues.put("paramap", a(paramMap));
      long l = e.insert("app_event", null, localContentValues);
      return l != -1L;
    }
    catch (Exception localException)
    {
    }
    return false;
  }

  // ERROR //
  private static byte[] a(Map paramMap)
  {
    // Byte code:
    //   0: aload_0
    //   1: ifnull +12 -> 13
    //   4: aload_0
    //   5: invokeinterface 447 1 0
    //   10: ifne +5 -> 15
    //   13: aconst_null
    //   14: areturn
    //   15: aload_0
    //   16: invokeinterface 447 1 0
    //   21: istore_1
    //   22: iload_1
    //   23: bipush 10
    //   25: if_icmple +6 -> 31
    //   28: bipush 10
    //   30: istore_1
    //   31: new 449	java/io/ByteArrayOutputStream
    //   34: dup
    //   35: invokespecial 450	java/io/ByteArrayOutputStream:<init>	()V
    //   38: astore_2
    //   39: new 452	java/io/DataOutputStream
    //   42: dup
    //   43: aload_2
    //   44: invokespecial 455	java/io/DataOutputStream:<init>	(Ljava/io/OutputStream;)V
    //   47: astore_3
    //   48: aload_3
    //   49: iload_1
    //   50: invokevirtual 458	java/io/DataOutputStream:writeInt	(I)V
    //   53: aload_0
    //   54: invokeinterface 462 1 0
    //   59: invokeinterface 468 1 0
    //   64: astore 8
    //   66: iconst_0
    //   67: istore 9
    //   69: aload 8
    //   71: invokeinterface 473 1 0
    //   76: ifeq +67 -> 143
    //   79: aload 8
    //   81: invokeinterface 477 1 0
    //   86: checkcast 479	java/util/Map$Entry
    //   89: astore 11
    //   91: aload_3
    //   92: aload 11
    //   94: invokeinterface 482 1 0
    //   99: checkcast 37	java/lang/String
    //   102: invokevirtual 485	java/io/DataOutputStream:writeUTF	(Ljava/lang/String;)V
    //   105: aload 11
    //   107: invokeinterface 488 1 0
    //   112: astore 12
    //   114: aload 12
    //   116: instanceof 490
    //   119: ifeq +41 -> 160
    //   122: aload_3
    //   123: bipush 66
    //   125: invokevirtual 458	java/io/DataOutputStream:writeInt	(I)V
    //   128: aload_3
    //   129: aload 12
    //   131: checkcast 490	java/lang/Number
    //   134: invokevirtual 493	java/lang/Number:doubleValue	()D
    //   137: invokevirtual 497	java/io/DataOutputStream:writeDouble	(D)V
    //   140: goto +134 -> 274
    //   143: aload_2
    //   144: invokevirtual 501	java/io/ByteArrayOutputStream:toByteArray	()[B
    //   147: astore 10
    //   149: aload_2
    //   150: invokestatic 346	com/talkingdata/pingan/sdk/PAAgent:a	(Ljava/io/Closeable;)V
    //   153: aload_3
    //   154: invokestatic 346	com/talkingdata/pingan/sdk/PAAgent:a	(Ljava/io/Closeable;)V
    //   157: aload 10
    //   159: areturn
    //   160: aload 12
    //   162: instanceof 37
    //   165: ifeq +41 -> 206
    //   168: aload_3
    //   169: bipush 88
    //   171: invokevirtual 458	java/io/DataOutputStream:writeInt	(I)V
    //   174: aload_3
    //   175: aload 12
    //   177: checkcast 37	java/lang/String
    //   180: invokevirtual 485	java/io/DataOutputStream:writeUTF	(Ljava/lang/String;)V
    //   183: goto +91 -> 274
    //   186: astore 5
    //   188: aload_3
    //   189: astore 6
    //   191: aload_2
    //   192: astore 7
    //   194: aload 7
    //   196: invokestatic 346	com/talkingdata/pingan/sdk/PAAgent:a	(Ljava/io/Closeable;)V
    //   199: aload 6
    //   201: invokestatic 346	com/talkingdata/pingan/sdk/PAAgent:a	(Ljava/io/Closeable;)V
    //   204: aconst_null
    //   205: areturn
    //   206: aload_2
    //   207: invokestatic 346	com/talkingdata/pingan/sdk/PAAgent:a	(Ljava/io/Closeable;)V
    //   210: aload_3
    //   211: invokestatic 346	com/talkingdata/pingan/sdk/PAAgent:a	(Ljava/io/Closeable;)V
    //   214: aconst_null
    //   215: areturn
    //   216: iload 13
    //   218: istore 9
    //   220: goto -151 -> 69
    //   223: astore 4
    //   225: aconst_null
    //   226: astore_3
    //   227: aconst_null
    //   228: astore_2
    //   229: aload_2
    //   230: invokestatic 346	com/talkingdata/pingan/sdk/PAAgent:a	(Ljava/io/Closeable;)V
    //   233: aload_3
    //   234: invokestatic 346	com/talkingdata/pingan/sdk/PAAgent:a	(Ljava/io/Closeable;)V
    //   237: aload 4
    //   239: athrow
    //   240: astore 4
    //   242: aconst_null
    //   243: astore_3
    //   244: goto -15 -> 229
    //   247: astore 4
    //   249: goto -20 -> 229
    //   252: astore 15
    //   254: aconst_null
    //   255: astore 6
    //   257: aconst_null
    //   258: astore 7
    //   260: goto -66 -> 194
    //   263: astore 14
    //   265: aload_2
    //   266: astore 7
    //   268: aconst_null
    //   269: astore 6
    //   271: goto -77 -> 194
    //   274: iload 9
    //   276: iconst_1
    //   277: iadd
    //   278: istore 13
    //   280: iload 13
    //   282: bipush 10
    //   284: if_icmpne -68 -> 216
    //   287: goto -144 -> 143
    //
    // Exception table:
    //   from	to	target	type
    //   48	66	186	java/lang/Exception
    //   69	140	186	java/lang/Exception
    //   143	149	186	java/lang/Exception
    //   160	183	186	java/lang/Exception
    //   31	39	223	finally
    //   39	48	240	finally
    //   48	66	247	finally
    //   69	140	247	finally
    //   143	149	247	finally
    //   160	183	247	finally
    //   31	39	252	java/lang/Exception
    //   39	48	263	java/lang/Exception
  }

  // ERROR //
  static long b(List paramList)
  {
    // Byte code:
    //   0: aload_0
    //   1: invokeinterface 270 1 0
    //   6: istore_1
    //   7: iload_1
    //   8: ifne +9 -> 17
    //   11: lconst_0
    //   12: lstore 14
    //   14: lload 14
    //   16: lreturn
    //   17: iload_1
    //   18: iconst_1
    //   19: isub
    //   20: istore_2
    //   21: iload_2
    //   22: iflt +117 -> 139
    //   25: new 76	java/lang/StringBuilder
    //   28: dup
    //   29: invokespecial 77	java/lang/StringBuilder:<init>	()V
    //   32: astore_3
    //   33: aload_3
    //   34: ldc_w 503
    //   37: invokevirtual 83	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   40: pop
    //   41: getstatic 29	com/talkingdata/pingan/sdk/e:e	Landroid/database/sqlite/SQLiteDatabase;
    //   44: astore 8
    //   46: aload_3
    //   47: invokevirtual 90	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   50: astore 9
    //   52: iconst_1
    //   53: anewarray 37	java/lang/String
    //   56: astore 10
    //   58: aload 10
    //   60: iconst_0
    //   61: aload_0
    //   62: iload_2
    //   63: invokeinterface 276 2 0
    //   68: checkcast 278	com/talkingdata/pingan/sdk/c
    //   71: getfield 280	com/talkingdata/pingan/sdk/c:a	Ljava/lang/String;
    //   74: aastore
    //   75: aload 8
    //   77: aload 9
    //   79: aload 10
    //   81: invokevirtual 284	android/database/sqlite/SQLiteDatabase:rawQuery	(Ljava/lang/String;[Ljava/lang/String;)Landroid/database/Cursor;
    //   84: astore 11
    //   86: aload 11
    //   88: invokeinterface 59 1 0
    //   93: ifeq +48 -> 141
    //   96: aload 11
    //   98: iconst_0
    //   99: invokeinterface 66 2 0
    //   104: lstore 12
    //   106: lload 12
    //   108: lstore 14
    //   110: lload 14
    //   112: lconst_0
    //   113: lcmp
    //   114: ifeq +27 -> 141
    //   117: aload 11
    //   119: ifnull -105 -> 14
    //   122: aload 11
    //   124: invokeinterface 69 1 0
    //   129: lload 14
    //   131: lreturn
    //   132: astore 4
    //   134: aload 4
    //   136: invokevirtual 234	java/lang/Exception:printStackTrace	()V
    //   139: lconst_0
    //   140: lreturn
    //   141: aload 11
    //   143: ifnull +37 -> 180
    //   146: aload 11
    //   148: invokeinterface 69 1 0
    //   153: goto +27 -> 180
    //   156: aload 7
    //   158: ifnull +10 -> 168
    //   161: aload 7
    //   163: invokeinterface 69 1 0
    //   168: aload 6
    //   170: athrow
    //   171: astore 6
    //   173: aload 11
    //   175: astore 7
    //   177: goto -21 -> 156
    //   180: iinc 2 255
    //   183: goto -162 -> 21
    //   186: astore 6
    //   188: aconst_null
    //   189: astore 7
    //   191: goto -35 -> 156
    //
    // Exception table:
    //   from	to	target	type
    //   25	41	132	java/lang/Exception
    //   122	129	132	java/lang/Exception
    //   146	153	132	java/lang/Exception
    //   161	168	132	java/lang/Exception
    //   168	171	132	java/lang/Exception
    //   86	106	171	finally
    //   41	86	186	finally
  }

  // ERROR //
  static List b(String paramString, long paramLong)
  {
    // Byte code:
    //   0: aconst_null
    //   1: astore_3
    //   2: new 287	java/util/ArrayList
    //   5: dup
    //   6: invokespecial 288	java/util/ArrayList:<init>	()V
    //   9: astore 4
    //   11: new 76	java/lang/StringBuilder
    //   14: dup
    //   15: invokespecial 77	java/lang/StringBuilder:<init>	()V
    //   18: astore 5
    //   20: aload 5
    //   22: ldc_w 505
    //   25: invokevirtual 83	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   28: pop
    //   29: getstatic 29	com/talkingdata/pingan/sdk/e:e	Landroid/database/sqlite/SQLiteDatabase;
    //   32: aload 5
    //   34: invokevirtual 90	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   37: iconst_1
    //   38: anewarray 37	java/lang/String
    //   41: dup
    //   42: iconst_0
    //   43: aload_0
    //   44: aastore
    //   45: invokevirtual 284	android/database/sqlite/SQLiteDatabase:rawQuery	(Ljava/lang/String;[Ljava/lang/String;)Landroid/database/Cursor;
    //   48: astore 11
    //   50: aload 11
    //   52: astore_3
    //   53: aload_3
    //   54: invokeinterface 59 1 0
    //   59: ifeq +125 -> 184
    //   62: aload_3
    //   63: invokeinterface 62 1 0
    //   68: ifne +116 -> 184
    //   71: new 507	com/talkingdata/pingan/sdk/ab
    //   74: dup
    //   75: invokespecial 508	com/talkingdata/pingan/sdk/ab:<init>	()V
    //   78: astore 13
    //   80: aload 13
    //   82: aload_3
    //   83: iconst_0
    //   84: invokeinterface 219 2 0
    //   89: putfield 509	com/talkingdata/pingan/sdk/ab:c	I
    //   92: aload 13
    //   94: aload_3
    //   95: iconst_1
    //   96: invokeinterface 66 2 0
    //   101: putfield 511	com/talkingdata/pingan/sdk/ab:d	J
    //   104: aload 13
    //   106: aload_3
    //   107: iconst_2
    //   108: invokeinterface 297 2 0
    //   113: putfield 512	com/talkingdata/pingan/sdk/ab:a	Ljava/lang/String;
    //   116: aload 13
    //   118: aload_3
    //   119: iconst_3
    //   120: invokeinterface 297 2 0
    //   125: putfield 514	com/talkingdata/pingan/sdk/ab:b	Ljava/lang/String;
    //   128: aload 13
    //   130: aconst_null
    //   131: putfield 517	com/talkingdata/pingan/sdk/ab:e	Ljava/util/Map;
    //   134: aload 13
    //   136: aload_3
    //   137: iconst_4
    //   138: invokeinterface 212 2 0
    //   143: invokestatic 519	com/talkingdata/pingan/sdk/e:a	([B)Ljava/util/Map;
    //   146: putfield 517	com/talkingdata/pingan/sdk/ab:e	Ljava/util/Map;
    //   149: aload 4
    //   151: aload 13
    //   153: invokeinterface 307 2 0
    //   158: pop
    //   159: aload_3
    //   160: invokeinterface 233 1 0
    //   165: pop
    //   166: goto -104 -> 62
    //   169: astore 9
    //   171: aload_3
    //   172: ifnull +9 -> 181
    //   175: aload_3
    //   176: invokeinterface 69 1 0
    //   181: aload 4
    //   183: areturn
    //   184: aload_3
    //   185: ifnull -4 -> 181
    //   188: aload_3
    //   189: invokeinterface 69 1 0
    //   194: aload 4
    //   196: areturn
    //   197: astore 6
    //   199: aconst_null
    //   200: astore 7
    //   202: aload 6
    //   204: astore 8
    //   206: aload 7
    //   208: ifnull +10 -> 218
    //   211: aload 7
    //   213: invokeinterface 69 1 0
    //   218: aload 8
    //   220: athrow
    //   221: astore 12
    //   223: aload_3
    //   224: astore 7
    //   226: aload 12
    //   228: astore 8
    //   230: goto -24 -> 206
    //
    // Exception table:
    //   from	to	target	type
    //   11	50	169	java/lang/Exception
    //   53	62	169	java/lang/Exception
    //   62	166	169	java/lang/Exception
    //   11	50	197	finally
    //   53	62	221	finally
    //   62	166	221	finally
  }

  static void b()
  {
    try
    {
      f = -1 + f;
      f = Math.max(0, f);
      if ((f == 0) && (e != null))
      {
        e.close();
        e = null;
      }
      return;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }

  static void b(long paramLong)
  {
    String[] arrayOfString1 = new String[2];
    arrayOfString1[0] = "Delete Activity Less Than Id";
    arrayOfString1[1] = ("id:" + paramLong);
    q.a(arrayOfString1);
    SQLiteDatabase localSQLiteDatabase = e;
    String[] arrayOfString2 = new String[1];
    arrayOfString2[0] = String.valueOf(paramLong);
    localSQLiteDatabase.delete("activity", "_id<=? AND duration != 0 ", arrayOfString2);
  }

  static void b(String paramString)
  {
    String[] arrayOfString = new String[2];
    arrayOfString[0] = "Delete Session By Session Id";
    arrayOfString[1] = ("sessionId:" + paramString);
    q.a(arrayOfString);
    e.delete("session", "session_id=?", new String[] { paramString });
  }

  static long c()
  {
    return DatabaseUtils.queryNumEntries(e, "session");
  }

  static void c(long paramLong)
  {
    String[] arrayOfString1 = new String[2];
    arrayOfString1[0] = "Delete App Event Less Than Id";
    arrayOfString1[1] = ("id:" + paramLong);
    q.a(arrayOfString1);
    SQLiteDatabase localSQLiteDatabase = e;
    String[] arrayOfString2 = new String[1];
    arrayOfString2[0] = String.valueOf(paramLong);
    localSQLiteDatabase.delete("app_event", "_id<=? ", arrayOfString2);
  }

  static void c(String paramString)
  {
    String[] arrayOfString = new String[2];
    arrayOfString[0] = "Delete Activity By SessionID";
    arrayOfString[1] = ("sessionId:" + paramString);
    q.a(arrayOfString);
    e.delete("activity", "session_id=? ", new String[] { paramString });
  }

  // ERROR //
  static List d()
  {
    // Byte code:
    //   0: new 287	java/util/ArrayList
    //   3: dup
    //   4: invokespecial 288	java/util/ArrayList:<init>	()V
    //   7: astore_0
    //   8: getstatic 29	com/talkingdata/pingan/sdk/e:e	Landroid/database/sqlite/SQLiteDatabase;
    //   11: ldc 187
    //   13: getstatic 554	com/talkingdata/pingan/sdk/e$a:h	[Ljava/lang/String;
    //   16: aconst_null
    //   17: aconst_null
    //   18: aconst_null
    //   19: aconst_null
    //   20: ldc 47
    //   22: ldc 14
    //   24: invokevirtual 557	android/database/sqlite/SQLiteDatabase:query	(Ljava/lang/String;[Ljava/lang/String;Ljava/lang/String;[Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Landroid/database/Cursor;
    //   27: astore 5
    //   29: aload 5
    //   31: astore_2
    //   32: aload_2
    //   33: invokeinterface 59 1 0
    //   38: ifeq +264 -> 302
    //   41: aload_2
    //   42: invokeinterface 62 1 0
    //   47: ifne +255 -> 302
    //   50: new 278	com/talkingdata/pingan/sdk/c
    //   53: dup
    //   54: invokespecial 558	com/talkingdata/pingan/sdk/c:<init>	()V
    //   57: astore 8
    //   59: aload 8
    //   61: aload_2
    //   62: iconst_1
    //   63: invokeinterface 297 2 0
    //   68: putfield 280	com/talkingdata/pingan/sdk/c:a	Ljava/lang/String;
    //   71: aload 8
    //   73: aload_2
    //   74: iconst_2
    //   75: invokeinterface 66 2 0
    //   80: putfield 559	com/talkingdata/pingan/sdk/c:b	J
    //   83: iconst_1
    //   84: anewarray 37	java/lang/String
    //   87: astore 9
    //   89: aload 9
    //   91: iconst_0
    //   92: new 76	java/lang/StringBuilder
    //   95: dup
    //   96: invokespecial 77	java/lang/StringBuilder:<init>	()V
    //   99: aload 8
    //   101: getfield 280	com/talkingdata/pingan/sdk/c:a	Ljava/lang/String;
    //   104: invokevirtual 83	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   107: ldc_w 561
    //   110: invokevirtual 83	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   113: aload 8
    //   115: getfield 559	com/talkingdata/pingan/sdk/c:b	J
    //   118: invokevirtual 86	java/lang/StringBuilder:append	(J)Ljava/lang/StringBuilder;
    //   121: invokevirtual 90	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   124: aastore
    //   125: aload 9
    //   127: invokestatic 97	com/talkingdata/pingan/sdk/q:a	([Ljava/lang/String;)V
    //   130: aload 8
    //   132: aload_2
    //   133: iconst_3
    //   134: invokeinterface 219 2 0
    //   139: putfield 564	com/talkingdata/pingan/sdk/c:g	I
    //   142: aload_2
    //   143: iconst_4
    //   144: invokeinterface 219 2 0
    //   149: ifne +104 -> 253
    //   152: aload 8
    //   154: iconst_1
    //   155: putfield 565	com/talkingdata/pingan/sdk/c:c	I
    //   158: iconst_1
    //   159: aload 8
    //   161: getfield 565	com/talkingdata/pingan/sdk/c:c	I
    //   164: if_icmpne +43 -> 207
    //   167: aload 8
    //   169: aload_2
    //   170: iconst_5
    //   171: invokeinterface 219 2 0
    //   176: putfield 568	com/talkingdata/pingan/sdk/c:j	I
    //   179: aload 8
    //   181: getfield 568	com/talkingdata/pingan/sdk/c:j	I
    //   184: ifge +9 -> 193
    //   187: aload 8
    //   189: iconst_0
    //   190: putfield 568	com/talkingdata/pingan/sdk/c:j	I
    //   193: aload 8
    //   195: aload 8
    //   197: getfield 568	com/talkingdata/pingan/sdk/c:j	I
    //   200: sipush 1000
    //   203: idiv
    //   204: putfield 564	com/talkingdata/pingan/sdk/c:g	I
    //   207: aload 8
    //   209: aload_2
    //   210: bipush 6
    //   212: invokeinterface 219 2 0
    //   217: putfield 571	com/talkingdata/pingan/sdk/c:k	I
    //   220: aload_0
    //   221: aload 8
    //   223: invokeinterface 307 2 0
    //   228: pop
    //   229: aload_2
    //   230: invokeinterface 233 1 0
    //   235: pop
    //   236: goto -195 -> 41
    //   239: astore 7
    //   241: aload_2
    //   242: ifnull +9 -> 251
    //   245: aload_2
    //   246: invokeinterface 69 1 0
    //   251: aload_0
    //   252: areturn
    //   253: aload 8
    //   255: getfield 564	com/talkingdata/pingan/sdk/c:g	I
    //   258: ifeq +38 -> 296
    //   261: iconst_3
    //   262: istore 10
    //   264: aload 8
    //   266: iload 10
    //   268: putfield 565	com/talkingdata/pingan/sdk/c:c	I
    //   271: goto -113 -> 158
    //   274: astore 6
    //   276: aload_2
    //   277: astore 4
    //   279: aload 6
    //   281: astore_3
    //   282: aload 4
    //   284: ifnull +10 -> 294
    //   287: aload 4
    //   289: invokeinterface 69 1 0
    //   294: aload_3
    //   295: athrow
    //   296: iconst_2
    //   297: istore 10
    //   299: goto -35 -> 264
    //   302: aload_2
    //   303: ifnull -52 -> 251
    //   306: aload_2
    //   307: invokeinterface 69 1 0
    //   312: aload_0
    //   313: areturn
    //   314: astore_3
    //   315: aconst_null
    //   316: astore 4
    //   318: goto -36 -> 282
    //   321: astore_1
    //   322: aconst_null
    //   323: astore_2
    //   324: goto -83 -> 241
    //
    // Exception table:
    //   from	to	target	type
    //   32	41	239	java/lang/Exception
    //   41	158	239	java/lang/Exception
    //   158	193	239	java/lang/Exception
    //   193	207	239	java/lang/Exception
    //   207	236	239	java/lang/Exception
    //   253	261	239	java/lang/Exception
    //   264	271	239	java/lang/Exception
    //   32	41	274	finally
    //   41	158	274	finally
    //   158	193	274	finally
    //   193	207	274	finally
    //   207	236	274	finally
    //   253	261	274	finally
    //   264	271	274	finally
    //   8	29	314	finally
    //   8	29	321	java/lang/Exception
  }

  static void d(long paramLong)
  {
    String[] arrayOfString1 = new String[2];
    arrayOfString1[0] = "Delete Error Less Than Id";
    arrayOfString1[1] = ("id:" + paramLong);
    q.a(arrayOfString1);
    SQLiteDatabase localSQLiteDatabase = e;
    String[] arrayOfString2 = new String[1];
    arrayOfString2[0] = String.valueOf(paramLong);
    localSQLiteDatabase.delete("error_report", "_id<=?", arrayOfString2);
  }

  static void d(String paramString)
  {
    String[] arrayOfString = new String[2];
    arrayOfString[0] = "Delete App Event By SessionId";
    arrayOfString[1] = ("sessionId:" + paramString);
    q.a(arrayOfString);
    e.delete("app_event", "session_id=? ", new String[] { paramString });
  }

  // ERROR //
  static long e(String paramString)
  {
    // Byte code:
    //   0: aconst_null
    //   1: astore_1
    //   2: new 76	java/lang/StringBuilder
    //   5: dup
    //   6: invokespecial 77	java/lang/StringBuilder:<init>	()V
    //   9: astore_2
    //   10: aload_2
    //   11: ldc_w 580
    //   14: invokevirtual 83	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   17: pop
    //   18: aload_2
    //   19: aload_0
    //   20: invokevirtual 83	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   23: pop
    //   24: getstatic 29	com/talkingdata/pingan/sdk/e:e	Landroid/database/sqlite/SQLiteDatabase;
    //   27: aload_2
    //   28: invokevirtual 90	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   31: aconst_null
    //   32: invokevirtual 284	android/database/sqlite/SQLiteDatabase:rawQuery	(Ljava/lang/String;[Ljava/lang/String;)Landroid/database/Cursor;
    //   35: astore 9
    //   37: aload 9
    //   39: astore 4
    //   41: aload 4
    //   43: invokeinterface 59 1 0
    //   48: ifeq +38 -> 86
    //   51: aload 4
    //   53: invokeinterface 62 1 0
    //   58: ifne +28 -> 86
    //   61: aload 4
    //   63: iconst_0
    //   64: invokeinterface 66 2 0
    //   69: lstore 11
    //   71: aload 4
    //   73: ifnull +10 -> 83
    //   76: aload 4
    //   78: invokeinterface 69 1 0
    //   83: lload 11
    //   85: lreturn
    //   86: aload 4
    //   88: ifnull +10 -> 98
    //   91: aload 4
    //   93: invokeinterface 69 1 0
    //   98: lconst_0
    //   99: lreturn
    //   100: astore 6
    //   102: aload_1
    //   103: ifnull -5 -> 98
    //   106: aload_1
    //   107: invokeinterface 69 1 0
    //   112: goto -14 -> 98
    //   115: astore_3
    //   116: aconst_null
    //   117: astore 4
    //   119: aload_3
    //   120: astore 5
    //   122: aload 4
    //   124: ifnull +10 -> 134
    //   127: aload 4
    //   129: invokeinterface 69 1 0
    //   134: aload 5
    //   136: athrow
    //   137: astore 5
    //   139: goto -17 -> 122
    //   142: astore 10
    //   144: aload 4
    //   146: astore_1
    //   147: goto -45 -> 102
    //
    // Exception table:
    //   from	to	target	type
    //   2	37	100	java/lang/Exception
    //   2	37	115	finally
    //   41	71	137	finally
    //   41	71	142	java/lang/Exception
  }

  static List e(long paramLong)
  {
    ArrayList localArrayList = new ArrayList();
    Cursor localCursor = null;
    try
    {
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("SELECT error_time,message,repeat, shorthashcode from error_report where _id<=?");
      SQLiteDatabase localSQLiteDatabase = e;
      String str1 = localStringBuilder.toString();
      String[] arrayOfString = new String[1];
      arrayOfString[0] = String.valueOf(paramLong);
      localCursor = localSQLiteDatabase.rawQuery(str1, arrayOfString);
      if (localCursor.moveToFirst())
        if (PAAgent.c() != null)
        {
          str2 = String.valueOf(i.c(PAAgent.c()));
          while (!localCursor.isAfterLast())
          {
            ac localac = new ac();
            localac.a = 3;
            al localal = new al();
            localal.a = localCursor.getLong(0);
            localal.d = localCursor.getBlob(1);
            localal.b = localCursor.getInt(2);
            localal.e = localCursor.getString(3);
            localal.c = str2;
            localac.d = localal;
            localArrayList.add(localac);
            localCursor.moveToNext();
          }
        }
    }
    catch (Exception localException)
    {
      while (true)
      {
        localException.printStackTrace();
        return localArrayList;
        String str2 = "";
      }
      return localArrayList;
    }
    finally
    {
      if (localCursor != null)
        localCursor.close();
    }
  }

  static final class a
    implements BaseColumns
  {
    public static final String a = "session_id";
    public static final String b = "start_time";
    public static final String c = "duration";
    public static final String d = "is_launch";
    public static final String e = "interval";
    public static final String f = "is_connected";
    public static final String g = "session";
    public static final String[] h = { "_id", "session_id", "start_time", "duration", "is_launch", "interval", "is_connected" };

    public static final void a(SQLiteDatabase paramSQLiteDatabase)
    {
      paramSQLiteDatabase.execSQL("CREATE TABLE session (_id INTEGER PRIMARY KEY autoincrement,session_id TEXT,start_time LONG,duration INTEGER,is_launch INTEGER,interval LONG, is_connected INTEGER)");
    }

    public static final void b(SQLiteDatabase paramSQLiteDatabase)
    {
      paramSQLiteDatabase.execSQL("DROP TABLE IF EXISTS session");
    }
  }

  static final class b
    implements BaseColumns
  {
    public static final String a = "name";
    public static final String b = "start_time";
    public static final String c = "duration";
    public static final String d = "session_id";
    public static final String e = "refer";
    public static final String f = "realtime";
    public static final String g = "activity";
    public static final String[] h = { "_id", "name", "start_time", "duration", "session_id", "refer", "realtime" };

    public static final void a(SQLiteDatabase paramSQLiteDatabase)
    {
      paramSQLiteDatabase.execSQL("CREATE TABLE activity (_id INTEGER PRIMARY KEY autoincrement,name TEXT,start_time LONG,duration INTEGER,session_id TEXT,refer TEXT,realtime LONG)");
    }

    public static final void b(SQLiteDatabase paramSQLiteDatabase)
    {
      paramSQLiteDatabase.execSQL("DROP TABLE IF EXISTS activity");
    }
  }

  static final class c
    implements BaseColumns
  {
    public static final String a = "error_time";
    public static final String b = "message";
    public static final String c = "repeat";
    public static final String d = "shorthashcode";
    public static final String e = "error_report";
    public static final String[] f = { "_id", "error_time", "message", "repeat", "shorthashcode" };

    public static final void a(SQLiteDatabase paramSQLiteDatabase)
    {
      paramSQLiteDatabase.execSQL("CREATE TABLE error_report (_id INTEGER PRIMARY KEY autoincrement,error_time LONG,message BLOB,repeat INTERGER,shorthashcode TEXT)");
    }

    public static final void b(SQLiteDatabase paramSQLiteDatabase)
    {
      paramSQLiteDatabase.execSQL("DROP TABLE IF EXISTS error_report");
    }
  }

  static final class d
    implements BaseColumns
  {
    public static final String a = "event_id";
    public static final String b = "event_label";
    public static final String c = "session_id";
    public static final String d = "occurtime";
    public static final String e = "paramap";
    public static final String f = "app_event";
    public static final String[] g = { "_id", "event_id", "event_label", "session_id", "occurtime", "paramap" };

    public static final void a(SQLiteDatabase paramSQLiteDatabase)
    {
      paramSQLiteDatabase.execSQL("CREATE TABLE app_event (_id INTEGER PRIMARY KEY autoincrement,event_id TEXT,event_label TEXT,session_id TEXT,occurtime LONG,paramap BLOB)");
    }

    public static final void b(SQLiteDatabase paramSQLiteDatabase)
    {
      paramSQLiteDatabase.execSQL("DROP TABLE IF EXISTS app_event");
    }
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.talkingdata.pingan.sdk.e
 * JD-Core Version:    0.6.2
 */