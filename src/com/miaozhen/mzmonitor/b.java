package com.miaozhen.mzmonitor;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;
import java.util.List;

class b
{
  private static b b = null;
  private Context a;

  private b(Context paramContext)
  {
    this.a = paramContext;
  }

  public static b a(Context paramContext)
  {
    try
    {
      if (b == null)
        b = new b(paramContext.getApplicationContext());
      b localb = b;
      return localb;
    }
    finally
    {
    }
  }

  private void a(a parama)
  {
    a locala = new a(this.a, "mzmonitor", null, 6);
    SQLiteDatabase localSQLiteDatabase = locala.getWritableDatabase();
    String[] arrayOfString = new String[2];
    arrayOfString[0] = parama.e();
    arrayOfString[1] = parama.a();
    localSQLiteDatabase.delete("mzcaches", "cacheId = ? AND url = ?", arrayOfString);
    locala.close();
  }

  private int b()
  {
    int i = 0;
    try
    {
      a locala = new a(this.a, "mzmonitor", null, 6);
      Cursor localCursor = locala.getReadableDatabase().rawQuery("select count(*) from mzcaches", null);
      localCursor.moveToFirst();
      i = localCursor.getInt(0);
      localCursor.close();
      locala.close();
      return i;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return i;
  }

  private boolean b(a parama)
  {
    a locala = new a(this.a, "mzmonitor", null, 6);
    SQLiteDatabase localSQLiteDatabase = locala.getReadableDatabase();
    String[] arrayOfString = new String[2];
    arrayOfString[0] = parama.e();
    arrayOfString[1] = parama.a();
    Cursor localCursor = localSQLiteDatabase.rawQuery("SELECT * FROM mzcaches WHERE cacheId = ? and url = ?", arrayOfString);
    boolean bool1 = localCursor.moveToNext();
    boolean bool2 = false;
    if (bool1)
      bool2 = true;
    localCursor.close();
    locala.close();
    return bool2;
  }

  public final List<a> a()
  {
    ArrayList localArrayList = new ArrayList();
    try
    {
      a locala = new a(this.a, "mzmonitor", null, 6);
      Cursor localCursor = locala.getReadableDatabase().query("mzcaches", new String[] { "cacheId", "url", "timestamp", "times" }, null, null, null, null, null);
      if (localCursor.getCount() > 0);
      while (true)
      {
        if (!localCursor.moveToNext())
        {
          localCursor.close();
          locala.close();
          return localArrayList;
        }
        a locala1 = new a();
        locala1.e(localCursor.getString(localCursor.getColumnIndex("cacheId")));
        locala1.a(localCursor.getString(localCursor.getColumnIndex("url")));
        locala1.a(localCursor.getLong(localCursor.getColumnIndex("timestamp")));
        locala1.a(localCursor.getShort(localCursor.getColumnIndex("times")));
        localArrayList.add(locala1);
      }
    }
    catch (Exception localException)
    {
    }
    return null;
  }

  // ERROR //
  public final void a(a parama, boolean paramBoolean)
  {
    // Byte code:
    //   0: iconst_1
    //   1: istore_3
    //   2: aload_0
    //   3: monitorenter
    //   4: iload_2
    //   5: ifeq +19 -> 24
    //   8: aload_0
    //   9: aload_1
    //   10: invokespecial 160	com/miaozhen/mzmonitor/b:b	(Lcom/miaozhen/mzmonitor/a;)Z
    //   13: ifeq +8 -> 21
    //   16: aload_0
    //   17: aload_1
    //   18: invokespecial 162	com/miaozhen/mzmonitor/b:a	(Lcom/miaozhen/mzmonitor/a;)V
    //   21: aload_0
    //   22: monitorexit
    //   23: return
    //   24: aload_0
    //   25: aload_1
    //   26: invokespecial 160	com/miaozhen/mzmonitor/b:b	(Lcom/miaozhen/mzmonitor/a;)Z
    //   29: ifeq +168 -> 197
    //   32: aload_1
    //   33: invokevirtual 165	com/miaozhen/mzmonitor/a:h	()I
    //   36: aload_0
    //   37: getfield 18	com/miaozhen/mzmonitor/b:a	Landroid/content/Context;
    //   40: invokestatic 170	com/miaozhen/mzmonitor/j:b	(Landroid/content/Context;)I
    //   43: if_icmplt +15 -> 58
    //   46: iload_3
    //   47: ifeq +44 -> 91
    //   50: aload_0
    //   51: aload_1
    //   52: invokespecial 162	com/miaozhen/mzmonitor/b:a	(Lcom/miaozhen/mzmonitor/a;)V
    //   55: goto -34 -> 21
    //   58: aload_1
    //   59: invokevirtual 174	com/miaozhen/mzmonitor/a:g	()J
    //   62: lstore 21
    //   64: aload_0
    //   65: getfield 18	com/miaozhen/mzmonitor/b:a	Landroid/content/Context;
    //   68: invokestatic 176	com/miaozhen/mzmonitor/j:e	(Landroid/content/Context;)I
    //   71: i2l
    //   72: lstore 23
    //   74: invokestatic 180	com/miaozhen/mzmonitor/c$a:a	()J
    //   77: lload 21
    //   79: lsub
    //   80: lload 23
    //   82: lcmp
    //   83: ifgt -37 -> 46
    //   86: iconst_0
    //   87: istore_3
    //   88: goto -42 -> 46
    //   91: new 30	com/miaozhen/mzmonitor/b$a
    //   94: dup
    //   95: aload_0
    //   96: aload_0
    //   97: getfield 18	com/miaozhen/mzmonitor/b:a	Landroid/content/Context;
    //   100: ldc 32
    //   102: aconst_null
    //   103: bipush 6
    //   105: invokespecial 35	com/miaozhen/mzmonitor/b$a:<init>	(Lcom/miaozhen/mzmonitor/b;Landroid/content/Context;Ljava/lang/String;Landroid/database/sqlite/SQLiteDatabase$CursorFactory;I)V
    //   108: astore 16
    //   110: aload 16
    //   112: invokevirtual 39	com/miaozhen/mzmonitor/b$a:getWritableDatabase	()Landroid/database/sqlite/SQLiteDatabase;
    //   115: astore 17
    //   117: aload_1
    //   118: iconst_1
    //   119: aload_1
    //   120: invokevirtual 165	com/miaozhen/mzmonitor/a:h	()I
    //   123: iadd
    //   124: invokevirtual 151	com/miaozhen/mzmonitor/a:a	(I)V
    //   127: aload_1
    //   128: invokevirtual 184	com/miaozhen/mzmonitor/a:j	()Landroid/content/ContentValues;
    //   131: astore 18
    //   133: iconst_2
    //   134: anewarray 41	java/lang/String
    //   137: astore 19
    //   139: aload 19
    //   141: iconst_0
    //   142: new 43	java/lang/StringBuilder
    //   145: dup
    //   146: invokespecial 44	java/lang/StringBuilder:<init>	()V
    //   149: aload_1
    //   150: invokevirtual 50	com/miaozhen/mzmonitor/a:e	()Ljava/lang/String;
    //   153: invokevirtual 54	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   156: invokevirtual 57	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   159: aastore
    //   160: aload 19
    //   162: iconst_1
    //   163: aload_1
    //   164: invokevirtual 59	com/miaozhen/mzmonitor/a:a	()Ljava/lang/String;
    //   167: aastore
    //   168: aload 17
    //   170: ldc 61
    //   172: aload 18
    //   174: ldc 63
    //   176: aload 19
    //   178: invokevirtual 188	android/database/sqlite/SQLiteDatabase:update	(Ljava/lang/String;Landroid/content/ContentValues;Ljava/lang/String;[Ljava/lang/String;)I
    //   181: pop
    //   182: aload 16
    //   184: invokevirtual 72	com/miaozhen/mzmonitor/b$a:close	()V
    //   187: goto -166 -> 21
    //   190: astore 4
    //   192: aload_0
    //   193: monitorexit
    //   194: aload 4
    //   196: athrow
    //   197: aload_0
    //   198: invokespecial 190	com/miaozhen/mzmonitor/b:b	()I
    //   201: aload_0
    //   202: getfield 18	com/miaozhen/mzmonitor/b:a	Landroid/content/Context;
    //   205: invokestatic 192	com/miaozhen/mzmonitor/j:a	(Landroid/content/Context;)I
    //   208: if_icmplt +210 -> 418
    //   211: iload_3
    //   212: ifeq +129 -> 341
    //   215: new 30	com/miaozhen/mzmonitor/b$a
    //   218: dup
    //   219: aload_0
    //   220: aload_0
    //   221: getfield 18	com/miaozhen/mzmonitor/b:a	Landroid/content/Context;
    //   224: ldc 32
    //   226: aconst_null
    //   227: bipush 6
    //   229: invokespecial 35	com/miaozhen/mzmonitor/b$a:<init>	(Lcom/miaozhen/mzmonitor/b;Landroid/content/Context;Ljava/lang/String;Landroid/database/sqlite/SQLiteDatabase$CursorFactory;I)V
    //   232: astore 7
    //   234: aload 7
    //   236: invokevirtual 78	com/miaozhen/mzmonitor/b$a:getReadableDatabase	()Landroid/database/sqlite/SQLiteDatabase;
    //   239: astore 8
    //   241: aload 8
    //   243: ldc 194
    //   245: aconst_null
    //   246: invokevirtual 84	android/database/sqlite/SQLiteDatabase:rawQuery	(Ljava/lang/String;[Ljava/lang/String;)Landroid/database/Cursor;
    //   249: astore 9
    //   251: aload 9
    //   253: invokeinterface 104 1 0
    //   258: ifeq +71 -> 329
    //   261: iconst_2
    //   262: anewarray 41	java/lang/String
    //   265: astore 15
    //   267: aload 15
    //   269: iconst_0
    //   270: new 43	java/lang/StringBuilder
    //   273: dup
    //   274: invokespecial 44	java/lang/StringBuilder:<init>	()V
    //   277: aload 9
    //   279: aload 9
    //   281: ldc 110
    //   283: invokeinterface 128 2 0
    //   288: invokeinterface 132 2 0
    //   293: invokevirtual 54	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   296: invokevirtual 57	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   299: aastore
    //   300: aload 15
    //   302: iconst_1
    //   303: aload 9
    //   305: aload 9
    //   307: ldc 112
    //   309: invokeinterface 128 2 0
    //   314: invokeinterface 132 2 0
    //   319: aastore
    //   320: aload 8
    //   322: ldc 196
    //   324: aload 15
    //   326: invokevirtual 200	android/database/sqlite/SQLiteDatabase:execSQL	(Ljava/lang/String;[Ljava/lang/Object;)V
    //   329: aload 9
    //   331: invokeinterface 95 1 0
    //   336: aload 7
    //   338: invokevirtual 72	com/miaozhen/mzmonitor/b$a:close	()V
    //   341: new 30	com/miaozhen/mzmonitor/b$a
    //   344: dup
    //   345: aload_0
    //   346: aload_0
    //   347: getfield 18	com/miaozhen/mzmonitor/b:a	Landroid/content/Context;
    //   350: ldc 32
    //   352: aconst_null
    //   353: bipush 6
    //   355: invokespecial 35	com/miaozhen/mzmonitor/b$a:<init>	(Lcom/miaozhen/mzmonitor/b;Landroid/content/Context;Ljava/lang/String;Landroid/database/sqlite/SQLiteDatabase$CursorFactory;I)V
    //   358: astore 10
    //   360: aload 10
    //   362: invokevirtual 39	com/miaozhen/mzmonitor/b$a:getWritableDatabase	()Landroid/database/sqlite/SQLiteDatabase;
    //   365: astore 11
    //   367: aload_1
    //   368: iconst_1
    //   369: aload_1
    //   370: invokevirtual 165	com/miaozhen/mzmonitor/a:h	()I
    //   373: iadd
    //   374: invokevirtual 151	com/miaozhen/mzmonitor/a:a	(I)V
    //   377: aload 11
    //   379: ldc 61
    //   381: aconst_null
    //   382: aload_1
    //   383: invokevirtual 184	com/miaozhen/mzmonitor/a:j	()Landroid/content/ContentValues;
    //   386: invokevirtual 204	android/database/sqlite/SQLiteDatabase:insert	(Ljava/lang/String;Ljava/lang/String;Landroid/content/ContentValues;)J
    //   389: pop2
    //   390: ldc 206
    //   392: aload_1
    //   393: invokevirtual 207	com/miaozhen/mzmonitor/a:toString	()Ljava/lang/String;
    //   396: invokestatic 213	android/util/Log:d	(Ljava/lang/String;Ljava/lang/String;)I
    //   399: pop
    //   400: aload 10
    //   402: invokevirtual 72	com/miaozhen/mzmonitor/b$a:close	()V
    //   405: goto -384 -> 21
    //   408: astore 6
    //   410: aload 6
    //   412: invokevirtual 98	java/lang/Exception:printStackTrace	()V
    //   415: goto -394 -> 21
    //   418: iconst_0
    //   419: istore_3
    //   420: goto -209 -> 211
    //   423: astore 5
    //   425: goto -404 -> 21
    //
    // Exception table:
    //   from	to	target	type
    //   8	21	190	finally
    //   24	46	190	finally
    //   50	55	190	finally
    //   58	86	190	finally
    //   91	187	190	finally
    //   197	211	190	finally
    //   215	329	190	finally
    //   329	341	190	finally
    //   341	405	190	finally
    //   410	415	190	finally
    //   197	211	408	java/lang/Exception
    //   215	329	408	java/lang/Exception
    //   329	341	408	java/lang/Exception
    //   341	405	408	java/lang/Exception
    //   8	21	423	java/lang/Exception
    //   24	46	423	java/lang/Exception
    //   50	55	423	java/lang/Exception
    //   58	86	423	java/lang/Exception
    //   91	187	423	java/lang/Exception
    //   410	415	423	java/lang/Exception
  }

  final class a extends SQLiteOpenHelper
  {
    public a(Context paramString, String paramCursorFactory, SQLiteDatabase.CursorFactory paramInt, int arg5)
    {
      super(paramCursorFactory, null, 6);
    }

    public final void onCreate(SQLiteDatabase paramSQLiteDatabase)
    {
      paramSQLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS mzcaches (cacheId VARCHAR PRIMARY KEY, url VARCHAR, timestamp LONG, times INTEGER)");
    }

    public final void onUpgrade(SQLiteDatabase paramSQLiteDatabase, int paramInt1, int paramInt2)
    {
      paramSQLiteDatabase.execSQL("DROP TABLE IF EXISTS mzcaches");
      paramSQLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS mzcaches (cacheId VARCHAR PRIMARY KEY, url VARCHAR, timestamp LONG, times INTEGER)");
    }
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.miaozhen.mzmonitor.b
 * JD-Core Version:    0.6.2
 */