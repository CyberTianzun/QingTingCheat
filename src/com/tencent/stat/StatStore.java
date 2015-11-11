package com.tencent.stat;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import com.tencent.stat.common.StatCommonHelper;
import com.tencent.stat.common.StatLogger;
import com.tencent.stat.common.User;
import com.tencent.stat.event.Event;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class StatStore
{
  private static StatStore instance = null;
  private static StatLogger logger = StatCommonHelper.getLogger();
  Handler handler = null;
  private StatStoreHelper helper;
  private HashMap<String, String> kvMap = new HashMap();
  volatile int numStoredEvents = 0;
  User user = null;

  private StatStore(Context paramContext)
  {
    try
    {
      HandlerThread localHandlerThread = new HandlerThread("StatStore");
      localHandlerThread.start();
      logger.w("Launch store thread:" + localHandlerThread);
      this.handler = new Handler(localHandlerThread.getLooper());
      Context localContext = paramContext.getApplicationContext();
      this.helper = new StatStoreHelper(localContext);
      this.helper.getWritableDatabase();
      this.helper.getReadableDatabase();
      getUser(localContext);
      loadConfig();
      loadKeyValues();
      this.handler.post(new Runnable()
      {
        public void run()
        {
          StatStore.this.loadUnsentEventCount();
        }
      });
      return;
    }
    catch (Throwable localThrowable)
    {
      logger.e(localThrowable);
    }
  }

  private void directDeleteEvents(List<StoredEvent> paramList)
  {
    logger.i("Delete " + paramList.size() + " sent events in thread:" + Thread.currentThread());
    try
    {
      this.helper.getWritableDatabase().beginTransaction();
      Iterator localIterator = paramList.iterator();
      while (localIterator.hasNext())
      {
        StoredEvent localStoredEvent = (StoredEvent)localIterator.next();
        int i = this.numStoredEvents;
        SQLiteDatabase localSQLiteDatabase = this.helper.getWritableDatabase();
        String[] arrayOfString = new String[1];
        arrayOfString[0] = Long.toString(localStoredEvent.id);
        this.numStoredEvents = (i - localSQLiteDatabase.delete("events", "event_id = ?", arrayOfString));
      }
    }
    catch (SQLiteException localSQLiteException2)
    {
      localSQLiteException2 = localSQLiteException2;
      logger.e(localSQLiteException2);
      try
      {
        this.helper.getWritableDatabase().endTransaction();
        return;
        this.helper.getWritableDatabase().setTransactionSuccessful();
        this.numStoredEvents = ((int)DatabaseUtils.queryNumEntries(this.helper.getReadableDatabase(), "events"));
        try
        {
          this.helper.getWritableDatabase().endTransaction();
          return;
        }
        catch (SQLiteException localSQLiteException4)
        {
          logger.e(localSQLiteException4);
          return;
        }
      }
      catch (SQLiteException localSQLiteException3)
      {
        logger.e(localSQLiteException3);
        return;
      }
    }
    finally
    {
    }
    try
    {
      this.helper.getWritableDatabase().endTransaction();
      throw localObject;
    }
    catch (SQLiteException localSQLiteException1)
    {
      while (true)
        logger.e(localSQLiteException1);
    }
  }

  // ERROR //
  private void directUpdateEvents(List<StoredEvent> paramList, int paramInt)
  {
    // Byte code:
    //   0: getstatic 29	com/tencent/stat/StatStore:logger	Lcom/tencent/stat/common/StatLogger;
    //   3: new 60	java/lang/StringBuilder
    //   6: dup
    //   7: invokespecial 61	java/lang/StringBuilder:<init>	()V
    //   10: ldc 234
    //   12: invokevirtual 67	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   15: aload_1
    //   16: invokeinterface 163 1 0
    //   21: invokevirtual 166	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
    //   24: ldc 236
    //   26: invokevirtual 67	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   29: iload_2
    //   30: invokevirtual 166	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
    //   33: ldc 238
    //   35: invokevirtual 67	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   38: invokestatic 174	java/lang/Thread:currentThread	()Ljava/lang/Thread;
    //   41: invokevirtual 70	java/lang/StringBuilder:append	(Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   44: invokevirtual 74	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   47: invokevirtual 177	com/tencent/stat/common/StatLogger:i	(Ljava/lang/Object;)V
    //   50: new 240	android/content/ContentValues
    //   53: dup
    //   54: invokespecial 241	android/content/ContentValues:<init>	()V
    //   57: astore_3
    //   58: aload_3
    //   59: ldc 243
    //   61: iload_2
    //   62: invokestatic 248	java/lang/Integer:toString	(I)Ljava/lang/String;
    //   65: invokevirtual 252	android/content/ContentValues:put	(Ljava/lang/String;Ljava/lang/String;)V
    //   68: aload_0
    //   69: getfield 101	com/tencent/stat/StatStore:helper	Lcom/tencent/stat/StatStore$StatStoreHelper;
    //   72: invokevirtual 105	com/tencent/stat/StatStore$StatStoreHelper:getWritableDatabase	()Landroid/database/sqlite/SQLiteDatabase;
    //   75: invokevirtual 182	android/database/sqlite/SQLiteDatabase:beginTransaction	()V
    //   78: aload_1
    //   79: invokeinterface 186 1 0
    //   84: astore 8
    //   86: aload 8
    //   88: invokeinterface 192 1 0
    //   93: ifeq +256 -> 349
    //   96: aload 8
    //   98: invokeinterface 196 1 0
    //   103: checkcast 198	com/tencent/stat/StatStore$StoredEvent
    //   106: astore 10
    //   108: iconst_1
    //   109: aload 10
    //   111: getfield 255	com/tencent/stat/StatStore$StoredEvent:send_count	I
    //   114: iadd
    //   115: invokestatic 260	com/tencent/stat/StatConfig:getMaxSendRetryCount	()I
    //   118: if_icmple +79 -> 197
    //   121: aload_0
    //   122: getfield 41	com/tencent/stat/StatStore:numStoredEvents	I
    //   125: istore 14
    //   127: aload_0
    //   128: getfield 101	com/tencent/stat/StatStore:helper	Lcom/tencent/stat/StatStore$StatStoreHelper;
    //   131: invokevirtual 105	com/tencent/stat/StatStore$StatStoreHelper:getWritableDatabase	()Landroid/database/sqlite/SQLiteDatabase;
    //   134: astore 15
    //   136: iconst_1
    //   137: anewarray 200	java/lang/String
    //   140: astore 16
    //   142: aload 16
    //   144: iconst_0
    //   145: aload 10
    //   147: getfield 204	com/tencent/stat/StatStore$StoredEvent:id	J
    //   150: invokestatic 209	java/lang/Long:toString	(J)Ljava/lang/String;
    //   153: aastore
    //   154: aload_0
    //   155: iload 14
    //   157: aload 15
    //   159: ldc 211
    //   161: ldc_w 262
    //   164: aload 16
    //   166: invokevirtual 217	android/database/sqlite/SQLiteDatabase:delete	(Ljava/lang/String;Ljava/lang/String;[Ljava/lang/String;)I
    //   169: isub
    //   170: putfield 41	com/tencent/stat/StatStore:numStoredEvents	I
    //   173: goto -87 -> 86
    //   176: astore 6
    //   178: getstatic 29	com/tencent/stat/StatStore:logger	Lcom/tencent/stat/common/StatLogger;
    //   181: aload 6
    //   183: invokevirtual 220	com/tencent/stat/common/StatLogger:e	(Ljava/lang/Exception;)V
    //   186: aload_0
    //   187: getfield 101	com/tencent/stat/StatStore:helper	Lcom/tencent/stat/StatStore$StatStoreHelper;
    //   190: invokevirtual 105	com/tencent/stat/StatStore$StatStoreHelper:getWritableDatabase	()Landroid/database/sqlite/SQLiteDatabase;
    //   193: invokevirtual 223	android/database/sqlite/SQLiteDatabase:endTransaction	()V
    //   196: return
    //   197: aload_3
    //   198: ldc_w 263
    //   201: iconst_1
    //   202: aload 10
    //   204: getfield 255	com/tencent/stat/StatStore$StoredEvent:send_count	I
    //   207: iadd
    //   208: invokestatic 267	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
    //   211: invokevirtual 270	android/content/ContentValues:put	(Ljava/lang/String;Ljava/lang/Integer;)V
    //   214: getstatic 29	com/tencent/stat/StatStore:logger	Lcom/tencent/stat/common/StatLogger;
    //   217: new 60	java/lang/StringBuilder
    //   220: dup
    //   221: invokespecial 61	java/lang/StringBuilder:<init>	()V
    //   224: ldc_w 272
    //   227: invokevirtual 67	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   230: aload 10
    //   232: getfield 204	com/tencent/stat/StatStore$StoredEvent:id	J
    //   235: invokevirtual 275	java/lang/StringBuilder:append	(J)Ljava/lang/StringBuilder;
    //   238: ldc_w 277
    //   241: invokevirtual 67	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   244: aload_3
    //   245: invokevirtual 70	java/lang/StringBuilder:append	(Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   248: invokevirtual 74	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   251: invokevirtual 177	com/tencent/stat/common/StatLogger:i	(Ljava/lang/Object;)V
    //   254: aload_0
    //   255: getfield 101	com/tencent/stat/StatStore:helper	Lcom/tencent/stat/StatStore$StatStoreHelper;
    //   258: invokevirtual 105	com/tencent/stat/StatStore$StatStoreHelper:getWritableDatabase	()Landroid/database/sqlite/SQLiteDatabase;
    //   261: astore 11
    //   263: iconst_1
    //   264: anewarray 200	java/lang/String
    //   267: astore 12
    //   269: aload 12
    //   271: iconst_0
    //   272: aload 10
    //   274: getfield 204	com/tencent/stat/StatStore$StoredEvent:id	J
    //   277: invokestatic 209	java/lang/Long:toString	(J)Ljava/lang/String;
    //   280: aastore
    //   281: aload 11
    //   283: ldc 211
    //   285: aload_3
    //   286: ldc_w 262
    //   289: aload 12
    //   291: invokevirtual 281	android/database/sqlite/SQLiteDatabase:update	(Ljava/lang/String;Landroid/content/ContentValues;Ljava/lang/String;[Ljava/lang/String;)I
    //   294: istore 13
    //   296: iload 13
    //   298: ifgt -212 -> 86
    //   301: getstatic 29	com/tencent/stat/StatStore:logger	Lcom/tencent/stat/common/StatLogger;
    //   304: new 60	java/lang/StringBuilder
    //   307: dup
    //   308: invokespecial 61	java/lang/StringBuilder:<init>	()V
    //   311: ldc_w 283
    //   314: invokevirtual 67	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   317: iload 13
    //   319: invokestatic 248	java/lang/Integer:toString	(I)Ljava/lang/String;
    //   322: invokevirtual 67	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   325: invokevirtual 74	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   328: invokevirtual 130	com/tencent/stat/common/StatLogger:e	(Ljava/lang/Object;)V
    //   331: goto -245 -> 86
    //   334: astore 4
    //   336: aload_0
    //   337: getfield 101	com/tencent/stat/StatStore:helper	Lcom/tencent/stat/StatStore$StatStoreHelper;
    //   340: invokevirtual 105	com/tencent/stat/StatStore$StatStoreHelper:getWritableDatabase	()Landroid/database/sqlite/SQLiteDatabase;
    //   343: invokevirtual 223	android/database/sqlite/SQLiteDatabase:endTransaction	()V
    //   346: aload 4
    //   348: athrow
    //   349: aload_0
    //   350: getfield 101	com/tencent/stat/StatStore:helper	Lcom/tencent/stat/StatStore$StatStoreHelper;
    //   353: invokevirtual 105	com/tencent/stat/StatStore$StatStoreHelper:getWritableDatabase	()Landroid/database/sqlite/SQLiteDatabase;
    //   356: invokevirtual 226	android/database/sqlite/SQLiteDatabase:setTransactionSuccessful	()V
    //   359: aload_0
    //   360: aload_0
    //   361: getfield 101	com/tencent/stat/StatStore:helper	Lcom/tencent/stat/StatStore$StatStoreHelper;
    //   364: invokevirtual 108	com/tencent/stat/StatStore$StatStoreHelper:getReadableDatabase	()Landroid/database/sqlite/SQLiteDatabase;
    //   367: ldc 211
    //   369: invokestatic 232	android/database/DatabaseUtils:queryNumEntries	(Landroid/database/sqlite/SQLiteDatabase;Ljava/lang/String;)J
    //   372: l2i
    //   373: putfield 41	com/tencent/stat/StatStore:numStoredEvents	I
    //   376: aload_0
    //   377: getfield 101	com/tencent/stat/StatStore:helper	Lcom/tencent/stat/StatStore$StatStoreHelper;
    //   380: invokevirtual 105	com/tencent/stat/StatStore$StatStoreHelper:getWritableDatabase	()Landroid/database/sqlite/SQLiteDatabase;
    //   383: invokevirtual 223	android/database/sqlite/SQLiteDatabase:endTransaction	()V
    //   386: return
    //   387: astore 9
    //   389: getstatic 29	com/tencent/stat/StatStore:logger	Lcom/tencent/stat/common/StatLogger;
    //   392: aload 9
    //   394: invokevirtual 220	com/tencent/stat/common/StatLogger:e	(Ljava/lang/Exception;)V
    //   397: return
    //   398: astore 7
    //   400: getstatic 29	com/tencent/stat/StatStore:logger	Lcom/tencent/stat/common/StatLogger;
    //   403: aload 7
    //   405: invokevirtual 220	com/tencent/stat/common/StatLogger:e	(Ljava/lang/Exception;)V
    //   408: return
    //   409: astore 5
    //   411: getstatic 29	com/tencent/stat/StatStore:logger	Lcom/tencent/stat/common/StatLogger;
    //   414: aload 5
    //   416: invokevirtual 220	com/tencent/stat/common/StatLogger:e	(Ljava/lang/Exception;)V
    //   419: goto -73 -> 346
    //
    // Exception table:
    //   from	to	target	type
    //   50	86	176	android/database/sqlite/SQLiteException
    //   86	173	176	android/database/sqlite/SQLiteException
    //   197	296	176	android/database/sqlite/SQLiteException
    //   301	331	176	android/database/sqlite/SQLiteException
    //   349	376	176	android/database/sqlite/SQLiteException
    //   50	86	334	finally
    //   86	173	334	finally
    //   178	186	334	finally
    //   197	296	334	finally
    //   301	331	334	finally
    //   349	376	334	finally
    //   376	386	387	android/database/sqlite/SQLiteException
    //   186	196	398	android/database/sqlite/SQLiteException
    //   336	346	409	android/database/sqlite/SQLiteException
  }

  public static StatStore getInstance()
  {
    return instance;
  }

  public static StatStore getInstance(Context paramContext)
  {
    if (instance == null)
      instance = new StatStore(paramContext);
    return instance;
  }

  // ERROR //
  private void loadKeyValues()
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 101	com/tencent/stat/StatStore:helper	Lcom/tencent/stat/StatStore$StatStoreHelper;
    //   4: invokevirtual 108	com/tencent/stat/StatStore$StatStoreHelper:getReadableDatabase	()Landroid/database/sqlite/SQLiteDatabase;
    //   7: ldc_w 289
    //   10: aconst_null
    //   11: aconst_null
    //   12: aconst_null
    //   13: aconst_null
    //   14: aconst_null
    //   15: aconst_null
    //   16: invokevirtual 293	android/database/sqlite/SQLiteDatabase:query	(Ljava/lang/String;[Ljava/lang/String;Ljava/lang/String;[Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Landroid/database/Cursor;
    //   19: astore 4
    //   21: aload 4
    //   23: astore_2
    //   24: aload_2
    //   25: invokeinterface 298 1 0
    //   30: ifeq +47 -> 77
    //   33: aload_0
    //   34: getfield 48	com/tencent/stat/StatStore:kvMap	Ljava/util/HashMap;
    //   37: aload_2
    //   38: iconst_0
    //   39: invokeinterface 301 2 0
    //   44: aload_2
    //   45: iconst_1
    //   46: invokeinterface 301 2 0
    //   51: invokevirtual 304	java/util/HashMap:put	(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   54: pop
    //   55: goto -31 -> 24
    //   58: astore_1
    //   59: getstatic 29	com/tencent/stat/StatStore:logger	Lcom/tencent/stat/common/StatLogger;
    //   62: aload_1
    //   63: invokevirtual 220	com/tencent/stat/common/StatLogger:e	(Ljava/lang/Exception;)V
    //   66: aload_2
    //   67: ifnull +9 -> 76
    //   70: aload_2
    //   71: invokeinterface 307 1 0
    //   76: return
    //   77: aload_2
    //   78: ifnull -2 -> 76
    //   81: aload_2
    //   82: invokeinterface 307 1 0
    //   87: return
    //   88: astore_3
    //   89: aconst_null
    //   90: astore_2
    //   91: aload_2
    //   92: ifnull +9 -> 101
    //   95: aload_2
    //   96: invokeinterface 307 1 0
    //   101: aload_3
    //   102: athrow
    //   103: astore_3
    //   104: goto -13 -> 91
    //   107: astore_1
    //   108: aconst_null
    //   109: astore_2
    //   110: goto -51 -> 59
    //
    // Exception table:
    //   from	to	target	type
    //   24	55	58	android/database/sqlite/SQLiteException
    //   0	21	88	finally
    //   24	55	103	finally
    //   59	66	103	finally
    //   0	21	107	android/database/sqlite/SQLiteException
  }

  private void loadUnsentEventCount()
  {
    ContentValues localContentValues = new ContentValues();
    localContentValues.put("status", Integer.valueOf(1));
    SQLiteDatabase localSQLiteDatabase = this.helper.getWritableDatabase();
    String[] arrayOfString = new String[1];
    arrayOfString[0] = Long.toString(2L);
    localSQLiteDatabase.update("events", localContentValues, "status=?", arrayOfString);
    this.numStoredEvents = ((int)DatabaseUtils.queryNumEntries(this.helper.getReadableDatabase(), "events"));
    logger.i("Total " + this.numStoredEvents + " unsent events.");
  }

  // ERROR //
  private void peekEvents(List<StoredEvent> paramList, int paramInt)
  {
    // Byte code:
    //   0: aconst_null
    //   1: astore_3
    //   2: aload_0
    //   3: getfield 101	com/tencent/stat/StatStore:helper	Lcom/tencent/stat/StatStore$StatStoreHelper;
    //   6: invokevirtual 108	com/tencent/stat/StatStore$StatStoreHelper:getReadableDatabase	()Landroid/database/sqlite/SQLiteDatabase;
    //   9: astore 7
    //   11: iconst_1
    //   12: anewarray 200	java/lang/String
    //   15: astore 8
    //   17: aload 8
    //   19: iconst_0
    //   20: iconst_1
    //   21: invokestatic 248	java/lang/Integer:toString	(I)Ljava/lang/String;
    //   24: aastore
    //   25: aload 7
    //   27: ldc 211
    //   29: aconst_null
    //   30: ldc_w 311
    //   33: aload 8
    //   35: aconst_null
    //   36: aconst_null
    //   37: ldc_w 317
    //   40: iload_2
    //   41: invokestatic 248	java/lang/Integer:toString	(I)Ljava/lang/String;
    //   44: invokevirtual 320	android/database/sqlite/SQLiteDatabase:query	(Ljava/lang/String;[Ljava/lang/String;Ljava/lang/String;[Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Landroid/database/Cursor;
    //   47: astore 9
    //   49: aload 9
    //   51: invokeinterface 298 1 0
    //   56: ifeq +82 -> 138
    //   59: aload_1
    //   60: new 198	com/tencent/stat/StatStore$StoredEvent
    //   63: dup
    //   64: aload 9
    //   66: iconst_0
    //   67: invokeinterface 324 2 0
    //   72: aload 9
    //   74: iconst_1
    //   75: invokeinterface 301 2 0
    //   80: invokestatic 328	com/tencent/stat/common/StatCommonHelper:decode	(Ljava/lang/String;)Ljava/lang/String;
    //   83: aload 9
    //   85: iconst_2
    //   86: invokeinterface 332 2 0
    //   91: aload 9
    //   93: iconst_3
    //   94: invokeinterface 332 2 0
    //   99: invokespecial 335	com/tencent/stat/StatStore$StoredEvent:<init>	(JLjava/lang/String;II)V
    //   102: invokeinterface 339 2 0
    //   107: pop
    //   108: goto -59 -> 49
    //   111: astore 4
    //   113: aload 9
    //   115: astore 5
    //   117: getstatic 29	com/tencent/stat/StatStore:logger	Lcom/tencent/stat/common/StatLogger;
    //   120: aload 4
    //   122: invokevirtual 220	com/tencent/stat/common/StatLogger:e	(Ljava/lang/Exception;)V
    //   125: aload 5
    //   127: ifnull +10 -> 137
    //   130: aload 5
    //   132: invokeinterface 307 1 0
    //   137: return
    //   138: aload 9
    //   140: ifnull -3 -> 137
    //   143: aload 9
    //   145: invokeinterface 307 1 0
    //   150: return
    //   151: astore 6
    //   153: aload_3
    //   154: ifnull +9 -> 163
    //   157: aload_3
    //   158: invokeinterface 307 1 0
    //   163: aload 6
    //   165: athrow
    //   166: astore 6
    //   168: aload 9
    //   170: astore_3
    //   171: goto -18 -> 153
    //   174: astore 6
    //   176: aload 5
    //   178: astore_3
    //   179: goto -26 -> 153
    //   182: astore 4
    //   184: aconst_null
    //   185: astore 5
    //   187: goto -70 -> 117
    //
    // Exception table:
    //   from	to	target	type
    //   49	108	111	android/database/sqlite/SQLiteException
    //   2	49	151	finally
    //   49	108	166	finally
    //   117	125	174	finally
    //   2	49	182	android/database/sqlite/SQLiteException
  }

  void deleteEvents(final List<StoredEvent> paramList)
  {
    try
    {
      if (Thread.currentThread().getId() == this.handler.getLooper().getThread().getId())
      {
        directDeleteEvents(paramList);
        return;
      }
      this.handler.post(new Runnable()
      {
        public void run()
        {
          StatStore.this.directDeleteEvents(paramList);
        }
      });
      return;
    }
    catch (SQLiteException localSQLiteException)
    {
      logger.e(localSQLiteException);
    }
  }

  void directStoreEvent(Event paramEvent, StatDispatchCallback paramStatDispatchCallback)
  {
    if (StatConfig.getMaxStoreEventCount() <= 0);
    do
    {
      return;
      if (this.numStoredEvents > StatConfig.getMaxStoreEventCount())
      {
        logger.warn("Too many events stored in db.");
        this.numStoredEvents -= this.helper.getWritableDatabase().delete("events", "event_id in (select event_id from events where timestamp in (select min(timestamp) from events) limit 1)", null);
      }
      ContentValues localContentValues = new ContentValues();
      String str = StatCommonHelper.encode(paramEvent.toJsonString());
      localContentValues.put("content", str);
      localContentValues.put("send_count", "0");
      localContentValues.put("status", Integer.toString(1));
      localContentValues.put("timestamp", Long.valueOf(paramEvent.getTimestamp()));
      if (this.helper.getWritableDatabase().insert("events", null, localContentValues) == -1L)
      {
        logger.error("Failed to store event:" + str);
        return;
      }
      this.numStoredEvents = (1 + this.numStoredEvents);
    }
    while (paramStatDispatchCallback == null);
    paramStatDispatchCallback.onDispatchSuccess();
  }

  public int getNumStoredEvents()
  {
    return this.numStoredEvents;
  }

  // ERROR //
  public User getUser(Context paramContext)
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 43	com/tencent/stat/StatStore:user	Lcom/tencent/stat/common/User;
    //   4: ifnull +8 -> 12
    //   7: aload_0
    //   8: getfield 43	com/tencent/stat/StatStore:user	Lcom/tencent/stat/common/User;
    //   11: areturn
    //   12: aload_0
    //   13: getfield 101	com/tencent/stat/StatStore:helper	Lcom/tencent/stat/StatStore$StatStoreHelper;
    //   16: invokevirtual 108	com/tencent/stat/StatStore$StatStoreHelper:getReadableDatabase	()Landroid/database/sqlite/SQLiteDatabase;
    //   19: ldc_w 407
    //   22: aconst_null
    //   23: aconst_null
    //   24: aconst_null
    //   25: aconst_null
    //   26: aconst_null
    //   27: aconst_null
    //   28: aconst_null
    //   29: invokevirtual 320	android/database/sqlite/SQLiteDatabase:query	(Ljava/lang/String;[Ljava/lang/String;Ljava/lang/String;[Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Landroid/database/Cursor;
    //   32: astore 6
    //   34: aload 6
    //   36: astore_3
    //   37: aload_3
    //   38: invokeinterface 298 1 0
    //   43: istore 7
    //   45: iconst_0
    //   46: istore 8
    //   48: iload 7
    //   50: ifeq +358 -> 408
    //   53: aload_3
    //   54: iconst_0
    //   55: invokeinterface 301 2 0
    //   60: astore 9
    //   62: aload 9
    //   64: invokestatic 328	com/tencent/stat/common/StatCommonHelper:decode	(Ljava/lang/String;)Ljava/lang/String;
    //   67: astore 10
    //   69: aload_3
    //   70: iconst_1
    //   71: invokeinterface 332 2 0
    //   76: istore 11
    //   78: aload_3
    //   79: iconst_2
    //   80: invokeinterface 301 2 0
    //   85: astore 12
    //   87: aload_3
    //   88: iconst_3
    //   89: invokeinterface 324 2 0
    //   94: lstore 13
    //   96: invokestatic 412	java/lang/System:currentTimeMillis	()J
    //   99: ldc2_w 413
    //   102: ldiv
    //   103: lstore 15
    //   105: iload 11
    //   107: iconst_1
    //   108: if_icmpeq +661 -> 769
    //   111: lload 13
    //   113: ldc2_w 413
    //   116: lmul
    //   117: invokestatic 417	com/tencent/stat/common/StatCommonHelper:getDateFormat	(J)Ljava/lang/String;
    //   120: ldc2_w 413
    //   123: lload 15
    //   125: lmul
    //   126: invokestatic 417	com/tencent/stat/common/StatCommonHelper:getDateFormat	(J)Ljava/lang/String;
    //   129: invokevirtual 420	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   132: ifne +637 -> 769
    //   135: iconst_1
    //   136: istore 17
    //   138: aload 12
    //   140: aload_1
    //   141: invokestatic 424	com/tencent/stat/common/StatCommonHelper:getAppVersion	(Landroid/content/Context;)Ljava/lang/String;
    //   144: invokevirtual 420	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   147: ifne +615 -> 762
    //   150: iload 17
    //   152: iconst_2
    //   153: ior
    //   154: istore 18
    //   156: aload 10
    //   158: ldc_w 426
    //   161: invokevirtual 430	java/lang/String:split	(Ljava/lang/String;)[Ljava/lang/String;
    //   164: astore 19
    //   166: aload 19
    //   168: ifnull +418 -> 586
    //   171: aload 19
    //   173: arraylength
    //   174: ifle +412 -> 586
    //   177: aload 19
    //   179: iconst_0
    //   180: aaload
    //   181: astore 39
    //   183: aload 39
    //   185: ifnull +13 -> 198
    //   188: aload 39
    //   190: invokevirtual 433	java/lang/String:length	()I
    //   193: bipush 11
    //   195: if_icmpge +553 -> 748
    //   198: aload_1
    //   199: invokestatic 436	com/tencent/stat/common/StatCommonHelper:getDeviceID	(Landroid/content/Context;)Ljava/lang/String;
    //   202: astore 40
    //   204: aload 40
    //   206: ifnull +532 -> 738
    //   209: aload 40
    //   211: invokevirtual 433	java/lang/String:length	()I
    //   214: bipush 10
    //   216: if_icmple +522 -> 738
    //   219: iconst_1
    //   220: istore 23
    //   222: goto +554 -> 776
    //   225: aload 19
    //   227: ifnull +379 -> 606
    //   230: aload 19
    //   232: arraylength
    //   233: iconst_2
    //   234: if_icmplt +372 -> 606
    //   237: aload 19
    //   239: iconst_1
    //   240: aaload
    //   241: astore 24
    //   243: new 60	java/lang/StringBuilder
    //   246: dup
    //   247: invokespecial 61	java/lang/StringBuilder:<init>	()V
    //   250: aload 21
    //   252: invokevirtual 67	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   255: ldc_w 426
    //   258: invokevirtual 67	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   261: aload 24
    //   263: invokevirtual 67	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   266: invokevirtual 74	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   269: astore 22
    //   271: aload_0
    //   272: new 438	com/tencent/stat/common/User
    //   275: dup
    //   276: aload 21
    //   278: aload 24
    //   280: iload 18
    //   282: invokespecial 441	com/tencent/stat/common/User:<init>	(Ljava/lang/String;Ljava/lang/String;I)V
    //   285: putfield 43	com/tencent/stat/StatStore:user	Lcom/tencent/stat/common/User;
    //   288: new 240	android/content/ContentValues
    //   291: dup
    //   292: invokespecial 241	android/content/ContentValues:<init>	()V
    //   295: astore 26
    //   297: aload 26
    //   299: ldc_w 443
    //   302: aload 22
    //   304: invokestatic 374	com/tencent/stat/common/StatCommonHelper:encode	(Ljava/lang/String;)Ljava/lang/String;
    //   307: invokevirtual 252	android/content/ContentValues:put	(Ljava/lang/String;Ljava/lang/String;)V
    //   310: aload 26
    //   312: ldc_w 445
    //   315: iload 18
    //   317: invokestatic 267	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
    //   320: invokevirtual 270	android/content/ContentValues:put	(Ljava/lang/String;Ljava/lang/Integer;)V
    //   323: aload 26
    //   325: ldc_w 447
    //   328: aload_1
    //   329: invokestatic 424	com/tencent/stat/common/StatCommonHelper:getAppVersion	(Landroid/content/Context;)Ljava/lang/String;
    //   332: invokevirtual 252	android/content/ContentValues:put	(Ljava/lang/String;Ljava/lang/String;)V
    //   335: aload 26
    //   337: ldc_w 449
    //   340: lload 15
    //   342: invokestatic 386	java/lang/Long:valueOf	(J)Ljava/lang/Long;
    //   345: invokevirtual 389	android/content/ContentValues:put	(Ljava/lang/String;Ljava/lang/Long;)V
    //   348: iload 23
    //   350: ifeq +31 -> 381
    //   353: aload_0
    //   354: getfield 101	com/tencent/stat/StatStore:helper	Lcom/tencent/stat/StatStore$StatStoreHelper;
    //   357: invokevirtual 105	com/tencent/stat/StatStore$StatStoreHelper:getWritableDatabase	()Landroid/database/sqlite/SQLiteDatabase;
    //   360: ldc_w 407
    //   363: aload 26
    //   365: ldc_w 451
    //   368: iconst_1
    //   369: anewarray 200	java/lang/String
    //   372: dup
    //   373: iconst_0
    //   374: aload 9
    //   376: aastore
    //   377: invokevirtual 281	android/database/sqlite/SQLiteDatabase:update	(Ljava/lang/String;Landroid/content/ContentValues;Ljava/lang/String;[Ljava/lang/String;)I
    //   380: pop
    //   381: iload 18
    //   383: iload 11
    //   385: if_icmpeq +347 -> 732
    //   388: aload_0
    //   389: getfield 101	com/tencent/stat/StatStore:helper	Lcom/tencent/stat/StatStore$StatStoreHelper;
    //   392: invokevirtual 105	com/tencent/stat/StatStore$StatStoreHelper:getWritableDatabase	()Landroid/database/sqlite/SQLiteDatabase;
    //   395: ldc_w 407
    //   398: aconst_null
    //   399: aload 26
    //   401: invokevirtual 454	android/database/sqlite/SQLiteDatabase:replace	(Ljava/lang/String;Ljava/lang/String;Landroid/content/ContentValues;)J
    //   404: pop2
    //   405: iconst_1
    //   406: istore 8
    //   408: iload 8
    //   410: ifne +161 -> 571
    //   413: aload_1
    //   414: invokestatic 457	com/tencent/stat/common/StatCommonHelper:getUserID	(Landroid/content/Context;)Ljava/lang/String;
    //   417: astore 30
    //   419: aload_1
    //   420: invokestatic 460	com/tencent/stat/common/StatCommonHelper:getMacId	(Landroid/content/Context;)Ljava/lang/String;
    //   423: astore 31
    //   425: aload 31
    //   427: ifnull +298 -> 725
    //   430: aload 31
    //   432: invokevirtual 433	java/lang/String:length	()I
    //   435: ifle +290 -> 725
    //   438: new 60	java/lang/StringBuilder
    //   441: dup
    //   442: invokespecial 61	java/lang/StringBuilder:<init>	()V
    //   445: aload 30
    //   447: invokevirtual 67	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   450: ldc_w 426
    //   453: invokevirtual 67	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   456: aload 31
    //   458: invokevirtual 67	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   461: invokevirtual 74	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   464: astore 32
    //   466: invokestatic 412	java/lang/System:currentTimeMillis	()J
    //   469: ldc2_w 413
    //   472: ldiv
    //   473: lstore 33
    //   475: aload_1
    //   476: invokestatic 424	com/tencent/stat/common/StatCommonHelper:getAppVersion	(Landroid/content/Context;)Ljava/lang/String;
    //   479: astore 35
    //   481: new 240	android/content/ContentValues
    //   484: dup
    //   485: invokespecial 241	android/content/ContentValues:<init>	()V
    //   488: astore 36
    //   490: aload 36
    //   492: ldc_w 443
    //   495: aload 32
    //   497: invokestatic 374	com/tencent/stat/common/StatCommonHelper:encode	(Ljava/lang/String;)Ljava/lang/String;
    //   500: invokevirtual 252	android/content/ContentValues:put	(Ljava/lang/String;Ljava/lang/String;)V
    //   503: aload 36
    //   505: ldc_w 445
    //   508: iconst_0
    //   509: invokestatic 267	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
    //   512: invokevirtual 270	android/content/ContentValues:put	(Ljava/lang/String;Ljava/lang/Integer;)V
    //   515: aload 36
    //   517: ldc_w 447
    //   520: aload 35
    //   522: invokevirtual 252	android/content/ContentValues:put	(Ljava/lang/String;Ljava/lang/String;)V
    //   525: aload 36
    //   527: ldc_w 449
    //   530: lload 33
    //   532: invokestatic 386	java/lang/Long:valueOf	(J)Ljava/lang/Long;
    //   535: invokevirtual 389	android/content/ContentValues:put	(Ljava/lang/String;Ljava/lang/Long;)V
    //   538: aload_0
    //   539: getfield 101	com/tencent/stat/StatStore:helper	Lcom/tencent/stat/StatStore$StatStoreHelper;
    //   542: invokevirtual 105	com/tencent/stat/StatStore$StatStoreHelper:getWritableDatabase	()Landroid/database/sqlite/SQLiteDatabase;
    //   545: ldc_w 407
    //   548: aconst_null
    //   549: aload 36
    //   551: invokevirtual 393	android/database/sqlite/SQLiteDatabase:insert	(Ljava/lang/String;Ljava/lang/String;Landroid/content/ContentValues;)J
    //   554: pop2
    //   555: aload_0
    //   556: new 438	com/tencent/stat/common/User
    //   559: dup
    //   560: aload 30
    //   562: aload 31
    //   564: iconst_0
    //   565: invokespecial 441	com/tencent/stat/common/User:<init>	(Ljava/lang/String;Ljava/lang/String;I)V
    //   568: putfield 43	com/tencent/stat/StatStore:user	Lcom/tencent/stat/common/User;
    //   571: aload_3
    //   572: ifnull +9 -> 581
    //   575: aload_3
    //   576: invokeinterface 307 1 0
    //   581: aload_0
    //   582: getfield 43	com/tencent/stat/StatStore:user	Lcom/tencent/stat/common/User;
    //   585: areturn
    //   586: aload_1
    //   587: invokestatic 457	com/tencent/stat/common/StatCommonHelper:getUserID	(Landroid/content/Context;)Ljava/lang/String;
    //   590: astore 20
    //   592: aload 20
    //   594: astore 21
    //   596: aload 20
    //   598: astore 22
    //   600: iconst_1
    //   601: istore 23
    //   603: goto -378 -> 225
    //   606: aload_1
    //   607: invokestatic 460	com/tencent/stat/common/StatCommonHelper:getMacId	(Landroid/content/Context;)Ljava/lang/String;
    //   610: astore 24
    //   612: aload 24
    //   614: ifnull -343 -> 271
    //   617: aload 24
    //   619: invokevirtual 433	java/lang/String:length	()I
    //   622: ifle -351 -> 271
    //   625: new 60	java/lang/StringBuilder
    //   628: dup
    //   629: invokespecial 61	java/lang/StringBuilder:<init>	()V
    //   632: aload 21
    //   634: invokevirtual 67	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   637: ldc_w 426
    //   640: invokevirtual 67	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   643: aload 24
    //   645: invokevirtual 67	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   648: invokevirtual 74	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   651: astore 25
    //   653: aload 25
    //   655: astore 22
    //   657: iconst_1
    //   658: istore 23
    //   660: goto -389 -> 271
    //   663: astore 4
    //   665: aconst_null
    //   666: astore 5
    //   668: getstatic 29	com/tencent/stat/StatStore:logger	Lcom/tencent/stat/common/StatLogger;
    //   671: aload 4
    //   673: invokevirtual 130	com/tencent/stat/common/StatLogger:e	(Ljava/lang/Object;)V
    //   676: aload 5
    //   678: ifnull -97 -> 581
    //   681: aload 5
    //   683: invokeinterface 307 1 0
    //   688: goto -107 -> 581
    //   691: astore_2
    //   692: aconst_null
    //   693: astore_3
    //   694: aload_3
    //   695: ifnull +9 -> 704
    //   698: aload_3
    //   699: invokeinterface 307 1 0
    //   704: aload_2
    //   705: athrow
    //   706: astore_2
    //   707: goto -13 -> 694
    //   710: astore_2
    //   711: aload 5
    //   713: astore_3
    //   714: goto -20 -> 694
    //   717: astore 4
    //   719: aload_3
    //   720: astore 5
    //   722: goto -54 -> 668
    //   725: aload 30
    //   727: astore 32
    //   729: goto -263 -> 466
    //   732: iconst_1
    //   733: istore 8
    //   735: goto -327 -> 408
    //   738: aload 39
    //   740: astore 40
    //   742: iconst_0
    //   743: istore 23
    //   745: goto +31 -> 776
    //   748: aload 10
    //   750: astore 22
    //   752: aload 39
    //   754: astore 21
    //   756: iconst_0
    //   757: istore 23
    //   759: goto -534 -> 225
    //   762: iload 17
    //   764: istore 18
    //   766: goto -610 -> 156
    //   769: iload 11
    //   771: istore 17
    //   773: goto -635 -> 138
    //   776: aload 10
    //   778: astore 22
    //   780: aload 40
    //   782: astore 21
    //   784: goto -559 -> 225
    //
    // Exception table:
    //   from	to	target	type
    //   12	34	663	java/lang/Throwable
    //   12	34	691	finally
    //   37	45	706	finally
    //   53	105	706	finally
    //   111	135	706	finally
    //   138	150	706	finally
    //   156	166	706	finally
    //   171	183	706	finally
    //   188	198	706	finally
    //   198	204	706	finally
    //   209	219	706	finally
    //   230	271	706	finally
    //   271	348	706	finally
    //   353	381	706	finally
    //   388	405	706	finally
    //   413	425	706	finally
    //   430	466	706	finally
    //   466	571	706	finally
    //   586	592	706	finally
    //   606	612	706	finally
    //   617	653	706	finally
    //   668	676	710	finally
    //   37	45	717	java/lang/Throwable
    //   53	105	717	java/lang/Throwable
    //   111	135	717	java/lang/Throwable
    //   138	150	717	java/lang/Throwable
    //   156	166	717	java/lang/Throwable
    //   171	183	717	java/lang/Throwable
    //   188	198	717	java/lang/Throwable
    //   198	204	717	java/lang/Throwable
    //   209	219	717	java/lang/Throwable
    //   230	271	717	java/lang/Throwable
    //   271	348	717	java/lang/Throwable
    //   353	381	717	java/lang/Throwable
    //   388	405	717	java/lang/Throwable
    //   413	425	717	java/lang/Throwable
    //   430	466	717	java/lang/Throwable
    //   466	571	717	java/lang/Throwable
    //   586	592	717	java/lang/Throwable
    //   606	612	717	java/lang/Throwable
    //   617	653	717	java/lang/Throwable
  }

  void loadConfig()
  {
    this.handler.post(new Runnable()
    {
      // ERROR //
      public void run()
      {
        // Byte code:
        //   0: aload_0
        //   1: getfield 17	com/tencent/stat/StatStore$6:this$0	Lcom/tencent/stat/StatStore;
        //   4: invokestatic 26	com/tencent/stat/StatStore:access$300	(Lcom/tencent/stat/StatStore;)Lcom/tencent/stat/StatStore$StatStoreHelper;
        //   7: invokevirtual 32	com/tencent/stat/StatStore$StatStoreHelper:getReadableDatabase	()Landroid/database/sqlite/SQLiteDatabase;
        //   10: ldc 34
        //   12: aconst_null
        //   13: aconst_null
        //   14: aconst_null
        //   15: aconst_null
        //   16: aconst_null
        //   17: aconst_null
        //   18: invokevirtual 40	android/database/sqlite/SQLiteDatabase:query	(Ljava/lang/String;[Ljava/lang/String;Ljava/lang/String;[Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Landroid/database/Cursor;
        //   21: astore 4
        //   23: aload 4
        //   25: astore_2
        //   26: aload_2
        //   27: invokeinterface 46 1 0
        //   32: ifeq +112 -> 144
        //   35: aload_2
        //   36: iconst_0
        //   37: invokeinterface 50 2 0
        //   42: istore 5
        //   44: aload_2
        //   45: iconst_1
        //   46: invokeinterface 54 2 0
        //   51: astore 6
        //   53: aload_2
        //   54: iconst_2
        //   55: invokeinterface 54 2 0
        //   60: astore 7
        //   62: aload_2
        //   63: iconst_3
        //   64: invokeinterface 50 2 0
        //   69: istore 8
        //   71: new 56	com/tencent/stat/StatConfig$OnlineConfig
        //   74: dup
        //   75: iload 5
        //   77: invokespecial 59	com/tencent/stat/StatConfig$OnlineConfig:<init>	(I)V
        //   80: astore 9
        //   82: aload 9
        //   84: iload 5
        //   86: putfield 63	com/tencent/stat/StatConfig$OnlineConfig:type	I
        //   89: aload 9
        //   91: new 65	org/json/JSONObject
        //   94: dup
        //   95: aload 6
        //   97: invokespecial 68	org/json/JSONObject:<init>	(Ljava/lang/String;)V
        //   100: putfield 72	com/tencent/stat/StatConfig$OnlineConfig:props	Lorg/json/JSONObject;
        //   103: aload 9
        //   105: aload 7
        //   107: putfield 76	com/tencent/stat/StatConfig$OnlineConfig:md5sum	Ljava/lang/String;
        //   110: aload 9
        //   112: iload 8
        //   114: putfield 79	com/tencent/stat/StatConfig$OnlineConfig:version	I
        //   117: aload 9
        //   119: invokestatic 85	com/tencent/stat/StatConfig:setConfig	(Lcom/tencent/stat/StatConfig$OnlineConfig;)V
        //   122: goto -96 -> 26
        //   125: astore_1
        //   126: invokestatic 89	com/tencent/stat/StatStore:access$400	()Lcom/tencent/stat/common/StatLogger;
        //   129: aload_1
        //   130: invokevirtual 95	com/tencent/stat/common/StatLogger:e	(Ljava/lang/Exception;)V
        //   133: aload_2
        //   134: ifnull +9 -> 143
        //   137: aload_2
        //   138: invokeinterface 98 1 0
        //   143: return
        //   144: aload_2
        //   145: ifnull -2 -> 143
        //   148: aload_2
        //   149: invokeinterface 98 1 0
        //   154: return
        //   155: astore_3
        //   156: aconst_null
        //   157: astore_2
        //   158: aload_2
        //   159: ifnull +9 -> 168
        //   162: aload_2
        //   163: invokeinterface 98 1 0
        //   168: aload_3
        //   169: athrow
        //   170: astore_3
        //   171: goto -13 -> 158
        //   174: astore_1
        //   175: aconst_null
        //   176: astore_2
        //   177: goto -51 -> 126
        //
        // Exception table:
        //   from	to	target	type
        //   26	122	125	java/lang/Exception
        //   0	23	155	finally
        //   26	122	170	finally
        //   126	133	170	finally
        //   0	23	174	java/lang/Exception
      }
    });
  }

  void loadEvents(final int paramInt)
  {
    this.handler.post(new Runnable()
    {
      public void run()
      {
        if (StatStore.this.numStoredEvents == 0)
          return;
        StatStore.logger.i("Load " + Integer.toString(StatStore.this.numStoredEvents) + " unsent events");
        ArrayList localArrayList1 = new ArrayList();
        final ArrayList localArrayList2 = new ArrayList();
        int i = paramInt;
        if ((i == -1) || (i > StatConfig.getMaxLoadEventCount()));
        for (final int j = StatConfig.getMaxLoadEventCount(); ; j = i)
        {
          StatStore localStatStore = StatStore.this;
          localStatStore.numStoredEvents -= j;
          StatStore.this.peekEvents(localArrayList2, j);
          StatStore.logger.i("Peek " + Integer.toString(localArrayList2.size()) + " unsent events.");
          if (localArrayList2.isEmpty())
            break;
          StatStore.this.directUpdateEvents(localArrayList2, 2);
          Iterator localIterator = localArrayList2.iterator();
          while (localIterator.hasNext())
            localArrayList1.add(((StatStore.StoredEvent)localIterator.next()).content);
          StatDispatcher.getInstance().send(localArrayList1, new StatDispatchCallback()
          {
            public void onDispatchFailure()
            {
              StatStore localStatStore = StatStore.this;
              localStatStore.numStoredEvents += j;
              StatStore.this.updateEvents(localArrayList2, 1);
            }

            public void onDispatchSuccess()
            {
              StatStore.this.deleteEvents(localArrayList2);
              if (StatStore.7.this.val$maxNumber == -1);
              for (int i = StatStore.7.this.val$maxNumber; ; i = StatStore.7.this.val$maxNumber - localArrayList2.size())
              {
                if ((i == -1) || (i > 0))
                  StatStore.this.loadEvents(i);
                return;
              }
            }
          });
          return;
        }
      }
    });
  }

  void storeCfg(final StatConfig.OnlineConfig paramOnlineConfig)
  {
    if (paramOnlineConfig == null)
      return;
    try
    {
      this.handler.post(new Runnable()
      {
        // ERROR //
        public void run()
        {
          // Byte code:
          //   0: aload_0
          //   1: getfield 21	com/tencent/stat/StatStore$5:val$cfg	Lcom/tencent/stat/StatConfig$OnlineConfig;
          //   4: invokevirtual 33	com/tencent/stat/StatConfig$OnlineConfig:toJsonString	()Ljava/lang/String;
          //   7: astore_1
          //   8: aload_1
          //   9: invokestatic 39	com/tencent/stat/common/StatCommonHelper:md5sum	(Ljava/lang/String;)Ljava/lang/String;
          //   12: astore_2
          //   13: aload_2
          //   14: aload_0
          //   15: getfield 21	com/tencent/stat/StatStore$5:val$cfg	Lcom/tencent/stat/StatConfig$OnlineConfig;
          //   18: getfield 42	com/tencent/stat/StatConfig$OnlineConfig:md5sum	Ljava/lang/String;
          //   21: invokevirtual 48	java/lang/String:equals	(Ljava/lang/Object;)Z
          //   24: ifne +223 -> 247
          //   27: new 50	android/content/ContentValues
          //   30: dup
          //   31: invokespecial 51	android/content/ContentValues:<init>	()V
          //   34: astore_3
          //   35: aload_3
          //   36: ldc 53
          //   38: aload_0
          //   39: getfield 21	com/tencent/stat/StatStore$5:val$cfg	Lcom/tencent/stat/StatConfig$OnlineConfig;
          //   42: getfield 57	com/tencent/stat/StatConfig$OnlineConfig:props	Lorg/json/JSONObject;
          //   45: invokevirtual 62	org/json/JSONObject:toString	()Ljava/lang/String;
          //   48: invokevirtual 66	android/content/ContentValues:put	(Ljava/lang/String;Ljava/lang/String;)V
          //   51: aload_3
          //   52: ldc 67
          //   54: aload_2
          //   55: invokevirtual 66	android/content/ContentValues:put	(Ljava/lang/String;Ljava/lang/String;)V
          //   58: aload_0
          //   59: getfield 21	com/tencent/stat/StatStore$5:val$cfg	Lcom/tencent/stat/StatConfig$OnlineConfig;
          //   62: aload_2
          //   63: putfield 42	com/tencent/stat/StatConfig$OnlineConfig:md5sum	Ljava/lang/String;
          //   66: aload_3
          //   67: ldc 69
          //   69: aload_0
          //   70: getfield 21	com/tencent/stat/StatStore$5:val$cfg	Lcom/tencent/stat/StatConfig$OnlineConfig;
          //   73: getfield 72	com/tencent/stat/StatConfig$OnlineConfig:version	I
          //   76: invokestatic 78	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
          //   79: invokevirtual 81	android/content/ContentValues:put	(Ljava/lang/String;Ljava/lang/Integer;)V
          //   82: aload_0
          //   83: getfield 19	com/tencent/stat/StatStore$5:this$0	Lcom/tencent/stat/StatStore;
          //   86: invokestatic 85	com/tencent/stat/StatStore:access$300	(Lcom/tencent/stat/StatStore;)Lcom/tencent/stat/StatStore$StatStoreHelper;
          //   89: invokevirtual 91	com/tencent/stat/StatStore$StatStoreHelper:getReadableDatabase	()Landroid/database/sqlite/SQLiteDatabase;
          //   92: ldc 93
          //   94: aconst_null
          //   95: aconst_null
          //   96: aconst_null
          //   97: aconst_null
          //   98: aconst_null
          //   99: aconst_null
          //   100: invokevirtual 99	android/database/sqlite/SQLiteDatabase:query	(Ljava/lang/String;[Ljava/lang/String;Ljava/lang/String;[Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Landroid/database/Cursor;
          //   103: astore 12
          //   105: aload 12
          //   107: astore 5
          //   109: aload 5
          //   111: invokeinterface 105 1 0
          //   116: ifeq +263 -> 379
          //   119: aload 5
          //   121: iconst_0
          //   122: invokeinterface 109 2 0
          //   127: istore 13
          //   129: aload_0
          //   130: getfield 21	com/tencent/stat/StatStore$5:val$cfg	Lcom/tencent/stat/StatConfig$OnlineConfig;
          //   133: getfield 112	com/tencent/stat/StatConfig$OnlineConfig:type	I
          //   136: istore 14
          //   138: iload 13
          //   140: iload 14
          //   142: if_icmpne -33 -> 109
          //   145: iconst_1
          //   146: istore 7
          //   148: aload 5
          //   150: ifnull +10 -> 160
          //   153: aload 5
          //   155: invokeinterface 115 1 0
          //   160: iconst_1
          //   161: iload 7
          //   163: if_icmpne +136 -> 299
          //   166: aload_0
          //   167: getfield 19	com/tencent/stat/StatStore$5:this$0	Lcom/tencent/stat/StatStore;
          //   170: invokestatic 85	com/tencent/stat/StatStore:access$300	(Lcom/tencent/stat/StatStore;)Lcom/tencent/stat/StatStore$StatStoreHelper;
          //   173: invokevirtual 118	com/tencent/stat/StatStore$StatStoreHelper:getWritableDatabase	()Landroid/database/sqlite/SQLiteDatabase;
          //   176: astore 10
          //   178: iconst_1
          //   179: anewarray 44	java/lang/String
          //   182: astore 11
          //   184: aload 11
          //   186: iconst_0
          //   187: aload_0
          //   188: getfield 21	com/tencent/stat/StatStore$5:val$cfg	Lcom/tencent/stat/StatConfig$OnlineConfig;
          //   191: getfield 112	com/tencent/stat/StatConfig$OnlineConfig:type	I
          //   194: invokestatic 121	java/lang/Integer:toString	(I)Ljava/lang/String;
          //   197: aastore
          //   198: aload 10
          //   200: ldc 93
          //   202: aload_3
          //   203: ldc 123
          //   205: aload 11
          //   207: invokevirtual 127	android/database/sqlite/SQLiteDatabase:update	(Ljava/lang/String;Landroid/content/ContentValues;Ljava/lang/String;[Ljava/lang/String;)I
          //   210: i2l
          //   211: lstore 8
          //   213: lload 8
          //   215: ldc2_w 128
          //   218: lcmp
          //   219: ifne +118 -> 337
          //   222: invokestatic 133	com/tencent/stat/StatStore:access$400	()Lcom/tencent/stat/common/StatLogger;
          //   225: new 135	java/lang/StringBuilder
          //   228: dup
          //   229: invokespecial 136	java/lang/StringBuilder:<init>	()V
          //   232: ldc 138
          //   234: invokevirtual 142	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
          //   237: aload_1
          //   238: invokevirtual 142	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
          //   241: invokevirtual 143	java/lang/StringBuilder:toString	()Ljava/lang/String;
          //   244: invokevirtual 149	com/tencent/stat/common/StatLogger:error	(Ljava/lang/Object;)V
          //   247: return
          //   248: astore 6
          //   250: aconst_null
          //   251: astore 5
          //   253: invokestatic 133	com/tencent/stat/StatStore:access$400	()Lcom/tencent/stat/common/StatLogger;
          //   256: aload 6
          //   258: invokevirtual 153	com/tencent/stat/common/StatLogger:e	(Ljava/lang/Exception;)V
          //   261: aload 5
          //   263: ifnull +110 -> 373
          //   266: aload 5
          //   268: invokeinterface 115 1 0
          //   273: iconst_0
          //   274: istore 7
          //   276: goto -116 -> 160
          //   279: astore 4
          //   281: aconst_null
          //   282: astore 5
          //   284: aload 5
          //   286: ifnull +10 -> 296
          //   289: aload 5
          //   291: invokeinterface 115 1 0
          //   296: aload 4
          //   298: athrow
          //   299: aload_3
          //   300: ldc 154
          //   302: aload_0
          //   303: getfield 21	com/tencent/stat/StatStore$5:val$cfg	Lcom/tencent/stat/StatConfig$OnlineConfig;
          //   306: getfield 112	com/tencent/stat/StatConfig$OnlineConfig:type	I
          //   309: invokestatic 78	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
          //   312: invokevirtual 81	android/content/ContentValues:put	(Ljava/lang/String;Ljava/lang/Integer;)V
          //   315: aload_0
          //   316: getfield 19	com/tencent/stat/StatStore$5:this$0	Lcom/tencent/stat/StatStore;
          //   319: invokestatic 85	com/tencent/stat/StatStore:access$300	(Lcom/tencent/stat/StatStore;)Lcom/tencent/stat/StatStore$StatStoreHelper;
          //   322: invokevirtual 118	com/tencent/stat/StatStore$StatStoreHelper:getWritableDatabase	()Landroid/database/sqlite/SQLiteDatabase;
          //   325: ldc 93
          //   327: aconst_null
          //   328: aload_3
          //   329: invokevirtual 158	android/database/sqlite/SQLiteDatabase:insert	(Ljava/lang/String;Ljava/lang/String;Landroid/content/ContentValues;)J
          //   332: lstore 8
          //   334: goto -121 -> 213
          //   337: invokestatic 133	com/tencent/stat/StatStore:access$400	()Lcom/tencent/stat/common/StatLogger;
          //   340: new 135	java/lang/StringBuilder
          //   343: dup
          //   344: invokespecial 136	java/lang/StringBuilder:<init>	()V
          //   347: ldc 160
          //   349: invokevirtual 142	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
          //   352: aload_1
          //   353: invokevirtual 142	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
          //   356: invokevirtual 143	java/lang/StringBuilder:toString	()Ljava/lang/String;
          //   359: invokevirtual 163	com/tencent/stat/common/StatLogger:i	(Ljava/lang/Object;)V
          //   362: return
          //   363: astore 4
          //   365: goto -81 -> 284
          //   368: astore 6
          //   370: goto -117 -> 253
          //   373: iconst_0
          //   374: istore 7
          //   376: goto -216 -> 160
          //   379: iconst_0
          //   380: istore 7
          //   382: goto -234 -> 148
          //
          // Exception table:
          //   from	to	target	type
          //   82	105	248	java/lang/Exception
          //   82	105	279	finally
          //   109	138	363	finally
          //   253	261	363	finally
          //   109	138	368	java/lang/Exception
        }
      });
      return;
    }
    catch (Exception localException)
    {
      logger.e(localException);
    }
  }

  void storeEvent(final Event paramEvent, final StatDispatchCallback paramStatDispatchCallback)
  {
    if (!StatConfig.isEnableStatService())
      return;
    try
    {
      if (Thread.currentThread().getId() == this.handler.getLooper().getThread().getId())
      {
        directStoreEvent(paramEvent, paramStatDispatchCallback);
        return;
      }
    }
    catch (SQLiteException localSQLiteException)
    {
      logger.e(localSQLiteException);
      return;
    }
    this.handler.post(new Runnable()
    {
      public void run()
      {
        StatStore.this.directStoreEvent(paramEvent, paramStatDispatchCallback);
      }
    });
  }

  void updateEvents(final List<StoredEvent> paramList, final int paramInt)
  {
    try
    {
      if (Thread.currentThread().getId() == this.handler.getLooper().getThread().getId())
      {
        directUpdateEvents(paramList, paramInt);
        return;
      }
      this.handler.post(new Runnable()
      {
        public void run()
        {
          StatStore.this.directUpdateEvents(paramList, paramInt);
        }
      });
      return;
    }
    catch (SQLiteException localSQLiteException)
    {
      logger.e(localSQLiteException);
    }
  }

  static class StatStoreHelper extends SQLiteOpenHelper
  {
    private static String DATABASE_NAME = "tencent_analysis.db";
    private static int DATABASE_VERSION = 3;

    public StatStoreHelper(Context paramContext)
    {
      super(DATABASE_NAME, null, DATABASE_VERSION);
    }

    private void upgradeEventsToVer3(SQLiteDatabase paramSQLiteDatabase)
    {
      Cursor localCursor = paramSQLiteDatabase.query("events", null, null, null, null, null, null);
      ArrayList localArrayList = new ArrayList();
      while (localCursor.moveToNext())
        localArrayList.add(new StatStore.StoredEvent(localCursor.getLong(0), localCursor.getString(1), localCursor.getInt(2), localCursor.getInt(3)));
      ContentValues localContentValues = new ContentValues();
      Iterator localIterator = localArrayList.iterator();
      while (localIterator.hasNext())
      {
        StatStore.StoredEvent localStoredEvent = (StatStore.StoredEvent)localIterator.next();
        localContentValues.put("content", StatCommonHelper.encode(localStoredEvent.content));
        String[] arrayOfString = new String[1];
        arrayOfString[0] = Long.toString(localStoredEvent.id);
        paramSQLiteDatabase.update("events", localContentValues, "event_id=?", arrayOfString);
      }
    }

    private void upgradeUserToVer3(SQLiteDatabase paramSQLiteDatabase)
    {
      Cursor localCursor = paramSQLiteDatabase.query("user", null, null, null, null, null, null);
      ContentValues localContentValues = new ContentValues();
      boolean bool = localCursor.moveToNext();
      String str = null;
      if (bool)
      {
        str = localCursor.getString(0);
        localCursor.getInt(1);
        localCursor.getString(2);
        localCursor.getLong(3);
        localContentValues.put("uid", StatCommonHelper.encode(str));
      }
      if (str != null)
        paramSQLiteDatabase.update("user", localContentValues, "uid=?", new String[] { str });
    }

    public void onCreate(SQLiteDatabase paramSQLiteDatabase)
    {
      paramSQLiteDatabase.execSQL("create table if not exists events(event_id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, content TEXT, status INTEGER, send_count INTEGER, timestamp LONG)");
      paramSQLiteDatabase.execSQL("create table if not exists user(uid TEXT PRIMARY KEY, user_type INTEGER, app_ver TEXT, ts INTEGER)");
      paramSQLiteDatabase.execSQL("create table if not exists config(type INTEGER PRIMARY KEY NOT NULL, content TEXT, md5sum TEXT, version INTEGER)");
      paramSQLiteDatabase.execSQL("create table if not exists keyvalues(key TEXT PRIMARY KEY NOT NULL, value TEXT)");
    }

    public void onUpgrade(SQLiteDatabase paramSQLiteDatabase, int paramInt1, int paramInt2)
    {
      StatStore.logger.debug("upgrade DB from oldVersion " + paramInt1 + " to newVersion " + paramInt2);
      if (paramInt1 == 1)
      {
        paramSQLiteDatabase.execSQL("create table if not exists keyvalues(key TEXT PRIMARY KEY NOT NULL, value TEXT)");
        upgradeUserToVer3(paramSQLiteDatabase);
        upgradeEventsToVer3(paramSQLiteDatabase);
      }
      if (paramInt1 == 2)
      {
        upgradeUserToVer3(paramSQLiteDatabase);
        upgradeEventsToVer3(paramSQLiteDatabase);
      }
    }
  }

  static class StoredEvent
  {
    String content;
    long id;
    int send_count;
    int status;

    public StoredEvent(long paramLong, String paramString, int paramInt1, int paramInt2)
    {
      this.id = paramLong;
      this.content = paramString;
      this.status = paramInt1;
      this.send_count = paramInt2;
    }
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.tencent.stat.StatStore
 * JD-Core Version:    0.6.2
 */