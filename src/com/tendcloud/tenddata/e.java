package com.tendcloud.tenddata;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;
import android.util.Log;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

final class e
{
  private static final int a = 5;
  private static final String b = "10";
  private static final String c = "TDtcagent.db";
  private static SQLiteDatabase d;
  private static int e;

  // ERROR //
  static long a(long paramLong)
  {
    // Byte code:
    //   0: aconst_null
    //   1: astore_2
    //   2: getstatic 26	com/tendcloud/tenddata/e:d	Landroid/database/sqlite/SQLiteDatabase;
    //   5: astore 6
    //   7: getstatic 32	com/tendcloud/tenddata/e$b:h	[Ljava/lang/String;
    //   10: astore 7
    //   12: iconst_1
    //   13: anewarray 34	java/lang/String
    //   16: astore 8
    //   18: aload 8
    //   20: iconst_0
    //   21: lload_0
    //   22: invokestatic 38	java/lang/String:valueOf	(J)Ljava/lang/String;
    //   25: aastore
    //   26: aload 6
    //   28: ldc 40
    //   30: aload 7
    //   32: ldc 42
    //   34: aload 8
    //   36: aconst_null
    //   37: aconst_null
    //   38: ldc 44
    //   40: invokevirtual 50	android/database/sqlite/SQLiteDatabase:query	(Ljava/lang/String;[Ljava/lang/String;Ljava/lang/String;[Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Landroid/database/Cursor;
    //   43: astore 9
    //   45: aload 9
    //   47: invokeinterface 56 1 0
    //   52: ifeq +39 -> 91
    //   55: aload 9
    //   57: invokeinterface 59 1 0
    //   62: ifne +29 -> 91
    //   65: aload 9
    //   67: bipush 6
    //   69: invokeinterface 63 2 0
    //   74: lstore 11
    //   76: aload 9
    //   78: ifnull +10 -> 88
    //   81: aload 9
    //   83: invokeinterface 66 1 0
    //   88: lload 11
    //   90: lreturn
    //   91: aload 9
    //   93: ifnull +10 -> 103
    //   96: aload 9
    //   98: invokeinterface 66 1 0
    //   103: lconst_0
    //   104: lreturn
    //   105: astore 4
    //   107: aconst_null
    //   108: astore 5
    //   110: aload 5
    //   112: ifnull -9 -> 103
    //   115: aload 5
    //   117: invokeinterface 66 1 0
    //   122: goto -19 -> 103
    //   125: astore_3
    //   126: aload_2
    //   127: ifnull +9 -> 136
    //   130: aload_2
    //   131: invokeinterface 66 1 0
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
    ContentValues localContentValues = new ContentValues();
    localContentValues.put("error_time", Long.valueOf(paramLong));
    try
    {
      ah localah = new ah();
      StringBuffer localStringBuffer = new StringBuffer("");
      long l = a(paramString, localah, localStringBuffer);
      if (0L == l)
      {
        localContentValues.put("message", paramString.getBytes("UTF-8"));
        localContentValues.put("repeat", Integer.valueOf(1));
        localContentValues.put("shorthashcode", localStringBuffer.toString());
        return d.insert("error_report", null, localContentValues);
      }
      localContentValues.put("repeat", Integer.valueOf(1 + localah.b));
      SQLiteDatabase localSQLiteDatabase = d;
      String[] arrayOfString = new String[1];
      arrayOfString[0] = String.valueOf(l);
      int i = localSQLiteDatabase.update("error_report", localContentValues, "_id=?", arrayOfString);
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
      ContentValues localContentValues = new ContentValues();
      localContentValues.put("session_id", paramString);
      localContentValues.put("start_time", Long.valueOf(paramLong1));
      localContentValues.put("duration", Integer.valueOf(0));
      localContentValues.put("is_launch", Integer.valueOf(0));
      localContentValues.put("interval", Long.valueOf(paramLong2));
      localContentValues.put("is_connected", Integer.valueOf(paramInt));
      d.insert("session", null, localContentValues);
      label85: return 0L;
    }
    catch (Exception localException)
    {
      break label85;
    }
  }

  // ERROR //
  static long a(String paramString, ah paramah, StringBuffer paramStringBuffer)
  {
    // Byte code:
    //   0: getstatic 26	com/tendcloud/tenddata/e:d	Landroid/database/sqlite/SQLiteDatabase;
    //   3: ldc 128
    //   5: getstatic 161	com/tendcloud/tenddata/e$c:f	[Ljava/lang/String;
    //   8: aconst_null
    //   9: aconst_null
    //   10: aconst_null
    //   11: aconst_null
    //   12: ldc 44
    //   14: invokevirtual 50	android/database/sqlite/SQLiteDatabase:query	(Ljava/lang/String;[Ljava/lang/String;Ljava/lang/String;[Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Landroid/database/Cursor;
    //   17: astore 7
    //   19: aload 7
    //   21: astore 6
    //   23: aload_0
    //   24: ldc 163
    //   26: invokevirtual 167	java/lang/String:split	(Ljava/lang/String;)[Ljava/lang/String;
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
    //   52: invokeinterface 66 1 0
    //   57: lload 17
    //   59: lreturn
    //   60: new 169	java/lang/StringBuilder
    //   63: dup
    //   64: invokespecial 170	java/lang/StringBuilder:<init>	()V
    //   67: aload 8
    //   69: iconst_0
    //   70: aaload
    //   71: invokevirtual 174	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   74: ldc 163
    //   76: invokevirtual 174	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   79: aload 8
    //   81: iconst_1
    //   82: aaload
    //   83: invokevirtual 174	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   86: ldc 163
    //   88: invokevirtual 174	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   91: aload 8
    //   93: iconst_2
    //   94: aaload
    //   95: invokevirtual 174	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   98: invokevirtual 175	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   101: astore 10
    //   103: aload_2
    //   104: aload 10
    //   106: invokestatic 180	com/tendcloud/tenddata/x:b	(Ljava/lang/String;)Ljava/lang/String;
    //   109: invokevirtual 183	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
    //   112: pop
    //   113: aload 6
    //   115: invokeinterface 56 1 0
    //   120: ifeq +205 -> 325
    //   123: aload 6
    //   125: invokeinterface 59 1 0
    //   130: ifne +195 -> 325
    //   133: aload_1
    //   134: aload 6
    //   136: iconst_1
    //   137: invokeinterface 63 2 0
    //   142: putfield 186	com/tendcloud/tenddata/ah:a	J
    //   145: aload_1
    //   146: aload 6
    //   148: iconst_2
    //   149: invokeinterface 190 2 0
    //   154: putfield 193	com/tendcloud/tenddata/ah:d	[B
    //   157: aload_1
    //   158: aload 6
    //   160: iconst_3
    //   161: invokeinterface 197 2 0
    //   166: putfield 134	com/tendcloud/tenddata/ah:b	I
    //   169: new 34	java/lang/String
    //   172: dup
    //   173: aload_1
    //   174: getfield 193	com/tendcloud/tenddata/ah:d	[B
    //   177: ldc 100
    //   179: invokespecial 200	java/lang/String:<init>	([BLjava/lang/String;)V
    //   182: astore 12
    //   184: aload 12
    //   186: invokevirtual 204	java/lang/String:length	()I
    //   189: aload 10
    //   191: invokevirtual 204	java/lang/String:length	()I
    //   194: if_icmplt -71 -> 123
    //   197: aload 12
    //   199: ldc 163
    //   201: invokevirtual 167	java/lang/String:split	(Ljava/lang/String;)[Ljava/lang/String;
    //   204: astore 13
    //   206: aload 13
    //   208: arraylength
    //   209: iconst_3
    //   210: if_icmplt -87 -> 123
    //   213: new 169	java/lang/StringBuilder
    //   216: dup
    //   217: invokespecial 170	java/lang/StringBuilder:<init>	()V
    //   220: aload 13
    //   222: iconst_0
    //   223: aaload
    //   224: invokevirtual 174	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   227: ldc 163
    //   229: invokevirtual 174	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   232: aload 13
    //   234: iconst_1
    //   235: aaload
    //   236: invokevirtual 174	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   239: ldc 163
    //   241: invokevirtual 174	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   244: aload 13
    //   246: iconst_2
    //   247: aaload
    //   248: invokevirtual 174	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   251: invokevirtual 175	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   254: aload 10
    //   256: invokevirtual 208	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   259: ifeq +32 -> 291
    //   262: aload 6
    //   264: iconst_0
    //   265: invokeinterface 63 2 0
    //   270: lstore 15
    //   272: lload 15
    //   274: lstore 17
    //   276: aload 6
    //   278: ifnull -221 -> 57
    //   281: aload 6
    //   283: invokeinterface 66 1 0
    //   288: lload 17
    //   290: lreturn
    //   291: aload 6
    //   293: invokeinterface 211 1 0
    //   298: pop
    //   299: goto -176 -> 123
    //   302: astore_3
    //   303: aload 6
    //   305: astore 4
    //   307: aload_3
    //   308: invokevirtual 212	java/lang/Exception:printStackTrace	()V
    //   311: aload 4
    //   313: ifnull +10 -> 323
    //   316: aload 4
    //   318: invokeinterface 66 1 0
    //   323: lconst_0
    //   324: lreturn
    //   325: aload 6
    //   327: ifnull -4 -> 323
    //   330: aload 6
    //   332: invokeinterface 66 1 0
    //   337: goto -14 -> 323
    //   340: astore 5
    //   342: aconst_null
    //   343: astore 6
    //   345: aload 6
    //   347: ifnull +10 -> 357
    //   350: aload 6
    //   352: invokeinterface 66 1 0
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
      if (TCAgent.LOG_ON)
        Log.i("TDLog", "track Page:" + paramString2);
      ContentValues localContentValues = new ContentValues();
      localContentValues.put("session_id", paramString1);
      localContentValues.put("name", paramString2);
      localContentValues.put("start_time", Long.valueOf(paramLong1));
      localContentValues.put("duration", Integer.valueOf(paramInt));
      localContentValues.put("refer", paramString3);
      localContentValues.put("realtime", Long.valueOf(paramLong2));
      long l = d.insert("activity", null, localContentValues);
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
    //   1: invokeinterface 243 1 0
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
    //   24: iflt +163 -> 187
    //   27: new 169	java/lang/StringBuilder
    //   30: dup
    //   31: invokespecial 170	java/lang/StringBuilder:<init>	()V
    //   34: astore 5
    //   36: aload 5
    //   38: ldc 245
    //   40: invokevirtual 174	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   43: pop
    //   44: getstatic 26	com/tendcloud/tenddata/e:d	Landroid/database/sqlite/SQLiteDatabase;
    //   47: astore 10
    //   49: aload 5
    //   51: invokevirtual 175	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   54: astore 11
    //   56: iconst_1
    //   57: anewarray 34	java/lang/String
    //   60: astore 12
    //   62: aload 12
    //   64: iconst_0
    //   65: aload_0
    //   66: iload_2
    //   67: invokeinterface 249 2 0
    //   72: checkcast 251	com/tendcloud/tenddata/c
    //   75: getfield 253	com/tendcloud/tenddata/c:a	Ljava/lang/String;
    //   78: aastore
    //   79: aload 10
    //   81: aload 11
    //   83: aload 12
    //   85: invokevirtual 257	android/database/sqlite/SQLiteDatabase:rawQuery	(Ljava/lang/String;[Ljava/lang/String;)Landroid/database/Cursor;
    //   88: astore 13
    //   90: aload 13
    //   92: invokeinterface 56 1 0
    //   97: ifeq +47 -> 144
    //   100: aload 13
    //   102: iconst_0
    //   103: invokeinterface 63 2 0
    //   108: lstore 14
    //   110: lload 14
    //   112: lstore 16
    //   114: lload 16
    //   116: lconst_0
    //   117: lcmp
    //   118: ifeq +23 -> 141
    //   121: aload 13
    //   123: ifnull -109 -> 14
    //   126: aload 13
    //   128: invokeinterface 66 1 0
    //   133: lload 16
    //   135: lreturn
    //   136: astore 18
    //   138: lload 16
    //   140: lreturn
    //   141: lload 16
    //   143: lstore_3
    //   144: aload 13
    //   146: ifnull +43 -> 189
    //   149: aload 13
    //   151: invokeinterface 66 1 0
    //   156: goto +33 -> 189
    //   159: aload 9
    //   161: ifnull +10 -> 171
    //   164: aload 9
    //   166: invokeinterface 66 1 0
    //   171: aload 8
    //   173: athrow
    //   174: astore 6
    //   176: lload_3
    //   177: lreturn
    //   178: astore 8
    //   180: aload 13
    //   182: astore 9
    //   184: goto -25 -> 159
    //   187: lload_3
    //   188: lreturn
    //   189: iinc 2 255
    //   192: goto -169 -> 23
    //   195: astore 8
    //   197: aconst_null
    //   198: astore 9
    //   200: goto -41 -> 159
    //
    // Exception table:
    //   from	to	target	type
    //   126	133	136	java/lang/Exception
    //   27	44	174	java/lang/Exception
    //   149	156	174	java/lang/Exception
    //   164	171	174	java/lang/Exception
    //   171	174	174	java/lang/Exception
    //   90	110	178	finally
    //   44	90	195	finally
  }

  // ERROR //
  static List a(String paramString, long paramLong)
  {
    // Byte code:
    //   0: aconst_null
    //   1: astore_3
    //   2: new 260	java/util/ArrayList
    //   5: dup
    //   6: invokespecial 261	java/util/ArrayList:<init>	()V
    //   9: astore 4
    //   11: getstatic 26	com/tendcloud/tenddata/e:d	Landroid/database/sqlite/SQLiteDatabase;
    //   14: ldc 40
    //   16: getstatic 32	com/tendcloud/tenddata/e$b:h	[Ljava/lang/String;
    //   19: ldc_w 263
    //   22: iconst_1
    //   23: anewarray 34	java/lang/String
    //   26: dup
    //   27: iconst_0
    //   28: aload_0
    //   29: aastore
    //   30: aconst_null
    //   31: aconst_null
    //   32: ldc 44
    //   34: invokevirtual 50	android/database/sqlite/SQLiteDatabase:query	(Ljava/lang/String;[Ljava/lang/String;Ljava/lang/String;[Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Landroid/database/Cursor;
    //   37: astore 8
    //   39: aload 8
    //   41: astore 6
    //   43: aload 6
    //   45: invokeinterface 56 1 0
    //   50: ifeq +112 -> 162
    //   53: aload 6
    //   55: invokeinterface 59 1 0
    //   60: ifne +102 -> 162
    //   63: new 265	com/tendcloud/tenddata/ai
    //   66: dup
    //   67: invokespecial 266	com/tendcloud/tenddata/ai:<init>	()V
    //   70: astore 11
    //   72: aload 11
    //   74: aload 6
    //   76: iconst_1
    //   77: invokeinterface 270 2 0
    //   82: putfield 271	com/tendcloud/tenddata/ai:a	Ljava/lang/String;
    //   85: aload 11
    //   87: aload 6
    //   89: iconst_2
    //   90: invokeinterface 63 2 0
    //   95: putfield 273	com/tendcloud/tenddata/ai:b	J
    //   98: aload 11
    //   100: aload 6
    //   102: iconst_3
    //   103: invokeinterface 197 2 0
    //   108: putfield 275	com/tendcloud/tenddata/ai:c	I
    //   111: aload 11
    //   113: aload 6
    //   115: iconst_5
    //   116: invokeinterface 270 2 0
    //   121: putfield 277	com/tendcloud/tenddata/ai:d	Ljava/lang/String;
    //   124: aload 4
    //   126: aload 11
    //   128: invokeinterface 280 2 0
    //   133: pop
    //   134: aload 6
    //   136: invokeinterface 211 1 0
    //   141: pop
    //   142: goto -89 -> 53
    //   145: astore 10
    //   147: aload 6
    //   149: ifnull +10 -> 159
    //   152: aload 6
    //   154: invokeinterface 66 1 0
    //   159: aload 4
    //   161: areturn
    //   162: aload 6
    //   164: ifnull -5 -> 159
    //   167: aload 6
    //   169: invokeinterface 66 1 0
    //   174: aload 4
    //   176: areturn
    //   177: astore 7
    //   179: aload_3
    //   180: ifnull +9 -> 189
    //   183: aload_3
    //   184: invokeinterface 66 1 0
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
    //   11: new 283	java/util/HashMap
    //   14: dup
    //   15: invokespecial 284	java/util/HashMap:<init>	()V
    //   18: astore_1
    //   19: new 286	java/io/ByteArrayInputStream
    //   22: dup
    //   23: aload_0
    //   24: invokespecial 289	java/io/ByteArrayInputStream:<init>	([B)V
    //   27: astore_2
    //   28: new 291	java/io/DataInputStream
    //   31: dup
    //   32: aload_2
    //   33: invokespecial 294	java/io/DataInputStream:<init>	(Ljava/io/InputStream;)V
    //   36: astore_3
    //   37: aload_3
    //   38: invokevirtual 297	java/io/DataInputStream:readInt	()I
    //   41: istore 8
    //   43: iconst_0
    //   44: istore 9
    //   46: iload 9
    //   48: iload 8
    //   50: if_icmpge +78 -> 128
    //   53: aload_3
    //   54: invokevirtual 300	java/io/DataInputStream:readUTF	()Ljava/lang/String;
    //   57: astore 10
    //   59: aload_3
    //   60: invokevirtual 297	java/io/DataInputStream:readInt	()I
    //   63: istore 11
    //   65: iload 11
    //   67: bipush 66
    //   69: if_icmpne +29 -> 98
    //   72: aload_3
    //   73: invokevirtual 304	java/io/DataInputStream:readDouble	()D
    //   76: invokestatic 309	java/lang/Double:valueOf	(D)Ljava/lang/Double;
    //   79: astore 12
    //   81: aload_1
    //   82: aload 10
    //   84: aload 12
    //   86: invokeinterface 314 3 0
    //   91: pop
    //   92: iinc 9 1
    //   95: goto -49 -> 46
    //   98: iload 11
    //   100: bipush 88
    //   102: if_icmpne +16 -> 118
    //   105: aload_3
    //   106: invokevirtual 300	java/io/DataInputStream:readUTF	()Ljava/lang/String;
    //   109: astore 14
    //   111: aload 14
    //   113: astore 12
    //   115: goto -34 -> 81
    //   118: aload_2
    //   119: invokestatic 319	com/tendcloud/tenddata/j:a	(Ljava/io/Closeable;)V
    //   122: aload_3
    //   123: invokestatic 319	com/tendcloud/tenddata/j:a	(Ljava/io/Closeable;)V
    //   126: aconst_null
    //   127: areturn
    //   128: aload_2
    //   129: invokestatic 319	com/tendcloud/tenddata/j:a	(Ljava/io/Closeable;)V
    //   132: aload_3
    //   133: invokestatic 319	com/tendcloud/tenddata/j:a	(Ljava/io/Closeable;)V
    //   136: aload_1
    //   137: areturn
    //   138: astore 18
    //   140: aconst_null
    //   141: astore 5
    //   143: aconst_null
    //   144: astore 6
    //   146: aload 6
    //   148: invokestatic 319	com/tendcloud/tenddata/j:a	(Ljava/io/Closeable;)V
    //   151: aload 5
    //   153: invokestatic 319	com/tendcloud/tenddata/j:a	(Ljava/io/Closeable;)V
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
    //   169: invokestatic 319	com/tendcloud/tenddata/j:a	(Ljava/io/Closeable;)V
    //   172: aload_3
    //   173: invokestatic 319	com/tendcloud/tenddata/j:a	(Ljava/io/Closeable;)V
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
    d.setVersion(5);
    a.a(d);
    b.a(d);
    d.a(d);
    c.a(d);
  }

  static void a(long paramLong1, long paramLong2)
  {
    long l = (paramLong2 - a(paramLong1)) / 1000L;
    ContentValues localContentValues = new ContentValues();
    localContentValues.put("duration", Long.valueOf(l));
    try
    {
      SQLiteDatabase localSQLiteDatabase = d;
      String[] arrayOfString = new String[1];
      arrayOfString[0] = String.valueOf(paramLong1);
      localSQLiteDatabase.update("activity", localContentValues, "_id=?", arrayOfString);
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
        if (d == null)
        {
          File localFile = new File(paramContext.getFilesDir(), "TDtcagent.db");
          boolean bool = localFile.exists();
          if (!localFile.getParentFile().exists())
            localFile.getParentFile().mkdirs();
          d = SQLiteDatabase.openOrCreateDatabase(localFile, null);
          d.setLockingEnabled(true);
          d.setMaximumSize(1000000L);
          e = 1;
          if (!bool)
          {
            a();
            return;
          }
          if (5 <= d.getVersion())
            continue;
          d.execSQL("DROP TABLE IF EXISTS error_report");
          d.execSQL("DROP TABLE IF EXISTS app_event");
          d.execSQL("DROP TABLE IF EXISTS session");
          d.execSQL("DROP TABLE IF EXISTS activity");
          a();
          continue;
        }
      }
      finally
      {
      }
      e = 1 + e;
    }
  }

  static void a(String paramString)
  {
    ContentValues localContentValues = new ContentValues();
    localContentValues.put("is_launch", Integer.valueOf(1));
    d.update("session", localContentValues, "session_id=?", new String[] { paramString });
  }

  static void a(String paramString, int paramInt)
  {
    ContentValues localContentValues = new ContentValues();
    localContentValues.put("duration", Integer.valueOf(paramInt));
    try
    {
      d.update("session", localContentValues, "session_id=?", new String[] { paramString });
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
      if (TCAgent.LOG_ON)
        Log.i("TDLog", "event:" + paramString2 + "label:" + paramString3);
      ContentValues localContentValues = new ContentValues();
      localContentValues.put("event_id", paramString2);
      localContentValues.put("event_label", paramString3);
      localContentValues.put("session_id", paramString1);
      localContentValues.put("occurtime", Long.valueOf(paramLong));
      localContentValues.put("paramap", a(paramMap));
      long l = d.insert("app_event", null, localContentValues);
      boolean bool1 = l < -1L;
      boolean bool2 = false;
      if (bool1)
        bool2 = true;
      return bool2;
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
    //   5: invokeinterface 412 1 0
    //   10: ifne +5 -> 15
    //   13: aconst_null
    //   14: areturn
    //   15: aload_0
    //   16: invokeinterface 412 1 0
    //   21: istore_1
    //   22: iload_1
    //   23: bipush 10
    //   25: if_icmple +6 -> 31
    //   28: bipush 10
    //   30: istore_1
    //   31: new 414	java/io/ByteArrayOutputStream
    //   34: dup
    //   35: invokespecial 415	java/io/ByteArrayOutputStream:<init>	()V
    //   38: astore_2
    //   39: new 417	java/io/DataOutputStream
    //   42: dup
    //   43: aload_2
    //   44: invokespecial 420	java/io/DataOutputStream:<init>	(Ljava/io/OutputStream;)V
    //   47: astore_3
    //   48: aload_3
    //   49: iload_1
    //   50: invokevirtual 423	java/io/DataOutputStream:writeInt	(I)V
    //   53: aload_0
    //   54: invokeinterface 427 1 0
    //   59: invokeinterface 433 1 0
    //   64: astore 8
    //   66: iconst_0
    //   67: istore 9
    //   69: aload 8
    //   71: invokeinterface 438 1 0
    //   76: ifeq +67 -> 143
    //   79: aload 8
    //   81: invokeinterface 442 1 0
    //   86: checkcast 444	java/util/Map$Entry
    //   89: astore 11
    //   91: aload_3
    //   92: aload 11
    //   94: invokeinterface 447 1 0
    //   99: checkcast 34	java/lang/String
    //   102: invokevirtual 450	java/io/DataOutputStream:writeUTF	(Ljava/lang/String;)V
    //   105: aload 11
    //   107: invokeinterface 453 1 0
    //   112: astore 12
    //   114: aload 12
    //   116: instanceof 455
    //   119: ifeq +41 -> 160
    //   122: aload_3
    //   123: bipush 66
    //   125: invokevirtual 423	java/io/DataOutputStream:writeInt	(I)V
    //   128: aload_3
    //   129: aload 12
    //   131: checkcast 455	java/lang/Number
    //   134: invokevirtual 458	java/lang/Number:doubleValue	()D
    //   137: invokevirtual 462	java/io/DataOutputStream:writeDouble	(D)V
    //   140: goto +116 -> 256
    //   143: aload_2
    //   144: invokevirtual 466	java/io/ByteArrayOutputStream:toByteArray	()[B
    //   147: astore 10
    //   149: aload_2
    //   150: invokestatic 319	com/tendcloud/tenddata/j:a	(Ljava/io/Closeable;)V
    //   153: aload_3
    //   154: invokestatic 319	com/tendcloud/tenddata/j:a	(Ljava/io/Closeable;)V
    //   157: aload 10
    //   159: areturn
    //   160: aload_3
    //   161: bipush 88
    //   163: invokevirtual 423	java/io/DataOutputStream:writeInt	(I)V
    //   166: aload_3
    //   167: aload 12
    //   169: invokevirtual 467	java/lang/Object:toString	()Ljava/lang/String;
    //   172: invokevirtual 450	java/io/DataOutputStream:writeUTF	(Ljava/lang/String;)V
    //   175: goto +81 -> 256
    //   178: astore 5
    //   180: aload_3
    //   181: astore 6
    //   183: aload_2
    //   184: astore 7
    //   186: aload 7
    //   188: invokestatic 319	com/tendcloud/tenddata/j:a	(Ljava/io/Closeable;)V
    //   191: aload 6
    //   193: invokestatic 319	com/tendcloud/tenddata/j:a	(Ljava/io/Closeable;)V
    //   196: aconst_null
    //   197: areturn
    //   198: iload 13
    //   200: istore 9
    //   202: goto -133 -> 69
    //   205: astore 4
    //   207: aconst_null
    //   208: astore_3
    //   209: aconst_null
    //   210: astore_2
    //   211: aload_2
    //   212: invokestatic 319	com/tendcloud/tenddata/j:a	(Ljava/io/Closeable;)V
    //   215: aload_3
    //   216: invokestatic 319	com/tendcloud/tenddata/j:a	(Ljava/io/Closeable;)V
    //   219: aload 4
    //   221: athrow
    //   222: astore 4
    //   224: aconst_null
    //   225: astore_3
    //   226: goto -15 -> 211
    //   229: astore 4
    //   231: goto -20 -> 211
    //   234: astore 15
    //   236: aconst_null
    //   237: astore 6
    //   239: aconst_null
    //   240: astore 7
    //   242: goto -56 -> 186
    //   245: astore 14
    //   247: aload_2
    //   248: astore 7
    //   250: aconst_null
    //   251: astore 6
    //   253: goto -67 -> 186
    //   256: iload 9
    //   258: iconst_1
    //   259: iadd
    //   260: istore 13
    //   262: iload 13
    //   264: bipush 10
    //   266: if_icmpne -68 -> 198
    //   269: goto -126 -> 143
    //
    // Exception table:
    //   from	to	target	type
    //   48	66	178	java/lang/Exception
    //   69	140	178	java/lang/Exception
    //   143	149	178	java/lang/Exception
    //   160	175	178	java/lang/Exception
    //   31	39	205	finally
    //   39	48	222	finally
    //   48	66	229	finally
    //   69	140	229	finally
    //   143	149	229	finally
    //   160	175	229	finally
    //   31	39	234	java/lang/Exception
    //   39	48	245	java/lang/Exception
  }

  // ERROR //
  static long b(List paramList)
  {
    // Byte code:
    //   0: aload_0
    //   1: invokeinterface 243 1 0
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
    //   25: new 169	java/lang/StringBuilder
    //   28: dup
    //   29: invokespecial 170	java/lang/StringBuilder:<init>	()V
    //   32: astore_3
    //   33: aload_3
    //   34: ldc_w 469
    //   37: invokevirtual 174	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   40: pop
    //   41: getstatic 26	com/tendcloud/tenddata/e:d	Landroid/database/sqlite/SQLiteDatabase;
    //   44: astore 8
    //   46: aload_3
    //   47: invokevirtual 175	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   50: astore 9
    //   52: iconst_1
    //   53: anewarray 34	java/lang/String
    //   56: astore 10
    //   58: aload 10
    //   60: iconst_0
    //   61: aload_0
    //   62: iload_2
    //   63: invokeinterface 249 2 0
    //   68: checkcast 251	com/tendcloud/tenddata/c
    //   71: getfield 253	com/tendcloud/tenddata/c:a	Ljava/lang/String;
    //   74: aastore
    //   75: aload 8
    //   77: aload 9
    //   79: aload 10
    //   81: invokevirtual 257	android/database/sqlite/SQLiteDatabase:rawQuery	(Ljava/lang/String;[Ljava/lang/String;)Landroid/database/Cursor;
    //   84: astore 11
    //   86: aload 11
    //   88: invokeinterface 56 1 0
    //   93: ifeq +48 -> 141
    //   96: aload 11
    //   98: iconst_0
    //   99: invokeinterface 63 2 0
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
    //   124: invokeinterface 66 1 0
    //   129: lload 14
    //   131: lreturn
    //   132: astore 4
    //   134: aload 4
    //   136: invokevirtual 212	java/lang/Exception:printStackTrace	()V
    //   139: lconst_0
    //   140: lreturn
    //   141: aload 11
    //   143: ifnull +37 -> 180
    //   146: aload 11
    //   148: invokeinterface 66 1 0
    //   153: goto +27 -> 180
    //   156: aload 7
    //   158: ifnull +10 -> 168
    //   161: aload 7
    //   163: invokeinterface 66 1 0
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
    //   2: new 260	java/util/ArrayList
    //   5: dup
    //   6: invokespecial 261	java/util/ArrayList:<init>	()V
    //   9: astore 4
    //   11: new 169	java/lang/StringBuilder
    //   14: dup
    //   15: invokespecial 170	java/lang/StringBuilder:<init>	()V
    //   18: astore 5
    //   20: aload 5
    //   22: ldc_w 471
    //   25: invokevirtual 174	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   28: pop
    //   29: getstatic 26	com/tendcloud/tenddata/e:d	Landroid/database/sqlite/SQLiteDatabase;
    //   32: aload 5
    //   34: invokevirtual 175	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   37: iconst_1
    //   38: anewarray 34	java/lang/String
    //   41: dup
    //   42: iconst_0
    //   43: aload_0
    //   44: aastore
    //   45: invokevirtual 257	android/database/sqlite/SQLiteDatabase:rawQuery	(Ljava/lang/String;[Ljava/lang/String;)Landroid/database/Cursor;
    //   48: astore 11
    //   50: aload 11
    //   52: astore_3
    //   53: aload_3
    //   54: invokeinterface 56 1 0
    //   59: ifeq +125 -> 184
    //   62: aload_3
    //   63: invokeinterface 59 1 0
    //   68: ifne +116 -> 184
    //   71: new 473	com/tendcloud/tenddata/y
    //   74: dup
    //   75: invokespecial 474	com/tendcloud/tenddata/y:<init>	()V
    //   78: astore 13
    //   80: aload 13
    //   82: aload_3
    //   83: iconst_0
    //   84: invokeinterface 197 2 0
    //   89: putfield 475	com/tendcloud/tenddata/y:c	I
    //   92: aload 13
    //   94: aload_3
    //   95: iconst_1
    //   96: invokeinterface 63 2 0
    //   101: putfield 477	com/tendcloud/tenddata/y:d	J
    //   104: aload 13
    //   106: aload_3
    //   107: iconst_2
    //   108: invokeinterface 270 2 0
    //   113: putfield 478	com/tendcloud/tenddata/y:a	Ljava/lang/String;
    //   116: aload 13
    //   118: aload_3
    //   119: iconst_3
    //   120: invokeinterface 270 2 0
    //   125: putfield 480	com/tendcloud/tenddata/y:b	Ljava/lang/String;
    //   128: aload 13
    //   130: aconst_null
    //   131: putfield 483	com/tendcloud/tenddata/y:e	Ljava/util/Map;
    //   134: aload 13
    //   136: aload_3
    //   137: iconst_4
    //   138: invokeinterface 190 2 0
    //   143: invokestatic 485	com/tendcloud/tenddata/e:a	([B)Ljava/util/Map;
    //   146: putfield 483	com/tendcloud/tenddata/y:e	Ljava/util/Map;
    //   149: aload 4
    //   151: aload 13
    //   153: invokeinterface 280 2 0
    //   158: pop
    //   159: aload_3
    //   160: invokeinterface 211 1 0
    //   165: pop
    //   166: goto -104 -> 62
    //   169: astore 9
    //   171: aload_3
    //   172: ifnull +9 -> 181
    //   175: aload_3
    //   176: invokeinterface 66 1 0
    //   181: aload 4
    //   183: areturn
    //   184: aload_3
    //   185: ifnull -4 -> 181
    //   188: aload_3
    //   189: invokeinterface 66 1 0
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
    //   213: invokeinterface 66 1 0
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
      e = -1 + e;
      e = Math.max(0, e);
      if ((e == 0) && (d != null))
      {
        d.close();
        d = null;
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
    SQLiteDatabase localSQLiteDatabase = d;
    String[] arrayOfString = new String[1];
    arrayOfString[0] = String.valueOf(paramLong);
    localSQLiteDatabase.delete("activity", "_id<=? AND duration != 0 ", arrayOfString);
  }

  static void b(String paramString)
  {
    d.delete("session", "session_id=?", new String[] { paramString });
  }

  static long c()
  {
    return DatabaseUtils.queryNumEntries(d, "session");
  }

  static void c(long paramLong)
  {
    String[] arrayOfString1 = new String[2];
    arrayOfString1[0] = "Delete App Event Less Than Id";
    arrayOfString1[1] = ("id:" + paramLong);
    r.a(arrayOfString1);
    SQLiteDatabase localSQLiteDatabase = d;
    String[] arrayOfString2 = new String[1];
    arrayOfString2[0] = String.valueOf(paramLong);
    localSQLiteDatabase.delete("app_event", "_id<=? ", arrayOfString2);
  }

  static void c(String paramString)
  {
    d.delete("activity", "session_id=? ", new String[] { paramString });
  }

  // ERROR //
  static List d()
  {
    // Byte code:
    //   0: new 260	java/util/ArrayList
    //   3: dup
    //   4: invokespecial 261	java/util/ArrayList:<init>	()V
    //   7: astore_0
    //   8: getstatic 26	com/tendcloud/tenddata/e:d	Landroid/database/sqlite/SQLiteDatabase;
    //   11: ldc 156
    //   13: getstatic 524	com/tendcloud/tenddata/e$a:h	[Ljava/lang/String;
    //   16: aconst_null
    //   17: aconst_null
    //   18: aconst_null
    //   19: aconst_null
    //   20: ldc 44
    //   22: ldc 11
    //   24: invokevirtual 527	android/database/sqlite/SQLiteDatabase:query	(Ljava/lang/String;[Ljava/lang/String;Ljava/lang/String;[Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Landroid/database/Cursor;
    //   27: astore 5
    //   29: aload 5
    //   31: astore_2
    //   32: aload_2
    //   33: invokeinterface 56 1 0
    //   38: ifeq +217 -> 255
    //   41: aload_2
    //   42: invokeinterface 59 1 0
    //   47: ifne +208 -> 255
    //   50: new 251	com/tendcloud/tenddata/c
    //   53: dup
    //   54: invokespecial 528	com/tendcloud/tenddata/c:<init>	()V
    //   57: astore 8
    //   59: aload 8
    //   61: aload_2
    //   62: iconst_1
    //   63: invokeinterface 270 2 0
    //   68: putfield 253	com/tendcloud/tenddata/c:a	Ljava/lang/String;
    //   71: aload 8
    //   73: aload_2
    //   74: iconst_2
    //   75: invokeinterface 63 2 0
    //   80: putfield 529	com/tendcloud/tenddata/c:b	J
    //   83: aload 8
    //   85: aload_2
    //   86: iconst_3
    //   87: invokeinterface 197 2 0
    //   92: putfield 532	com/tendcloud/tenddata/c:g	I
    //   95: aload_2
    //   96: iconst_4
    //   97: invokeinterface 197 2 0
    //   102: ifne +104 -> 206
    //   105: aload 8
    //   107: iconst_1
    //   108: putfield 533	com/tendcloud/tenddata/c:c	I
    //   111: iconst_1
    //   112: aload 8
    //   114: getfield 533	com/tendcloud/tenddata/c:c	I
    //   117: if_icmpne +43 -> 160
    //   120: aload 8
    //   122: aload_2
    //   123: iconst_5
    //   124: invokeinterface 197 2 0
    //   129: putfield 536	com/tendcloud/tenddata/c:j	I
    //   132: aload 8
    //   134: getfield 536	com/tendcloud/tenddata/c:j	I
    //   137: ifge +9 -> 146
    //   140: aload 8
    //   142: iconst_0
    //   143: putfield 536	com/tendcloud/tenddata/c:j	I
    //   146: aload 8
    //   148: aload 8
    //   150: getfield 536	com/tendcloud/tenddata/c:j	I
    //   153: sipush 1000
    //   156: idiv
    //   157: putfield 532	com/tendcloud/tenddata/c:g	I
    //   160: aload 8
    //   162: aload_2
    //   163: bipush 6
    //   165: invokeinterface 197 2 0
    //   170: putfield 539	com/tendcloud/tenddata/c:k	I
    //   173: aload_0
    //   174: aload 8
    //   176: invokeinterface 280 2 0
    //   181: pop
    //   182: aload_2
    //   183: invokeinterface 211 1 0
    //   188: pop
    //   189: goto -148 -> 41
    //   192: astore 7
    //   194: aload_2
    //   195: ifnull +9 -> 204
    //   198: aload_2
    //   199: invokeinterface 66 1 0
    //   204: aload_0
    //   205: areturn
    //   206: aload 8
    //   208: getfield 532	com/tendcloud/tenddata/c:g	I
    //   211: ifeq +38 -> 249
    //   214: iconst_3
    //   215: istore 9
    //   217: aload 8
    //   219: iload 9
    //   221: putfield 533	com/tendcloud/tenddata/c:c	I
    //   224: goto -113 -> 111
    //   227: astore 6
    //   229: aload_2
    //   230: astore 4
    //   232: aload 6
    //   234: astore_3
    //   235: aload 4
    //   237: ifnull +10 -> 247
    //   240: aload 4
    //   242: invokeinterface 66 1 0
    //   247: aload_3
    //   248: athrow
    //   249: iconst_2
    //   250: istore 9
    //   252: goto -35 -> 217
    //   255: aload_2
    //   256: ifnull -52 -> 204
    //   259: aload_2
    //   260: invokeinterface 66 1 0
    //   265: aload_0
    //   266: areturn
    //   267: astore_3
    //   268: aconst_null
    //   269: astore 4
    //   271: goto -36 -> 235
    //   274: astore_1
    //   275: aconst_null
    //   276: astore_2
    //   277: goto -83 -> 194
    //
    // Exception table:
    //   from	to	target	type
    //   32	41	192	java/lang/Exception
    //   41	111	192	java/lang/Exception
    //   111	146	192	java/lang/Exception
    //   146	160	192	java/lang/Exception
    //   160	189	192	java/lang/Exception
    //   206	214	192	java/lang/Exception
    //   217	224	192	java/lang/Exception
    //   32	41	227	finally
    //   41	111	227	finally
    //   111	146	227	finally
    //   146	160	227	finally
    //   160	189	227	finally
    //   206	214	227	finally
    //   217	224	227	finally
    //   8	29	267	finally
    //   8	29	274	java/lang/Exception
  }

  static void d(long paramLong)
  {
    SQLiteDatabase localSQLiteDatabase = d;
    String[] arrayOfString = new String[1];
    arrayOfString[0] = String.valueOf(paramLong);
    localSQLiteDatabase.delete("error_report", "_id<=?", arrayOfString);
  }

  static void d(String paramString)
  {
    d.delete("app_event", "session_id=? ", new String[] { paramString });
  }

  // ERROR //
  static long e(String paramString)
  {
    // Byte code:
    //   0: aconst_null
    //   1: astore_1
    //   2: new 169	java/lang/StringBuilder
    //   5: dup
    //   6: invokespecial 170	java/lang/StringBuilder:<init>	()V
    //   9: astore_2
    //   10: aload_2
    //   11: ldc_w 544
    //   14: invokevirtual 174	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   17: pop
    //   18: aload_2
    //   19: aload_0
    //   20: invokevirtual 174	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   23: pop
    //   24: getstatic 26	com/tendcloud/tenddata/e:d	Landroid/database/sqlite/SQLiteDatabase;
    //   27: aload_2
    //   28: invokevirtual 175	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   31: aconst_null
    //   32: invokevirtual 257	android/database/sqlite/SQLiteDatabase:rawQuery	(Ljava/lang/String;[Ljava/lang/String;)Landroid/database/Cursor;
    //   35: astore 9
    //   37: aload 9
    //   39: astore 4
    //   41: aload 4
    //   43: invokeinterface 56 1 0
    //   48: ifeq +38 -> 86
    //   51: aload 4
    //   53: invokeinterface 59 1 0
    //   58: ifne +28 -> 86
    //   61: aload 4
    //   63: iconst_0
    //   64: invokeinterface 63 2 0
    //   69: lstore 11
    //   71: aload 4
    //   73: ifnull +10 -> 83
    //   76: aload 4
    //   78: invokeinterface 66 1 0
    //   83: lload 11
    //   85: lreturn
    //   86: aload 4
    //   88: ifnull +10 -> 98
    //   91: aload 4
    //   93: invokeinterface 66 1 0
    //   98: lconst_0
    //   99: lreturn
    //   100: astore 6
    //   102: aload_1
    //   103: ifnull -5 -> 98
    //   106: aload_1
    //   107: invokeinterface 66 1 0
    //   112: goto -14 -> 98
    //   115: astore_3
    //   116: aconst_null
    //   117: astore 4
    //   119: aload_3
    //   120: astore 5
    //   122: aload 4
    //   124: ifnull +10 -> 134
    //   127: aload 4
    //   129: invokeinterface 66 1 0
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
      SQLiteDatabase localSQLiteDatabase = d;
      String str1 = localStringBuilder.toString();
      String[] arrayOfString = new String[1];
      arrayOfString[0] = String.valueOf(paramLong);
      localCursor = localSQLiteDatabase.rawQuery(str1, arrayOfString);
      if (localCursor.moveToFirst())
        if (j.h() != null)
        {
          str2 = String.valueOf(i.c(j.h()));
          while (!localCursor.isAfterLast())
          {
            z localz = new z();
            localz.a = 3;
            ah localah = new ah();
            localah.a = localCursor.getLong(0);
            localah.d = localCursor.getBlob(1);
            localah.b = localCursor.getInt(2);
            localah.e = localCursor.getString(3);
            localah.c = str2;
            localz.d = localah;
            localArrayList.add(localz);
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
 * Qualified Name:     com.tendcloud.tenddata.e
 * JD-Core Version:    0.6.2
 */