package org.android.agoo.client;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.text.TextUtils;
import com.umeng.message.proguard.P;
import com.umeng.message.proguard.Q;
import com.umeng.message.proguard.aM;
import com.umeng.message.proguard.aQ;
import org.android.agoo.net.mtop.IMtopSynClient;
import org.android.agoo.net.mtop.MtopRequest;
import org.android.agoo.net.mtop.MtopSyncClientV3;

class MessageService
{
  private static final String a = "MessageService";
  private static final String b = "message_db";
  private static final String c = "message";
  private static final int d = 1;
  private static final String e = "id";
  private static final String f = "type";
  private static final String g = "message";
  private static final String h = "notify";
  private static final String i = "report";
  private static final String j = "interval";
  private static final String k = "target_time";
  private static final String l = "create_time";
  private static final String m = "state";
  private static final String n = "body_code";
  private static final int o = 1;
  private static final int p;
  private static volatile MessageService r = null;
  private volatile SQLiteOpenHelper q = null;
  private Context s;

  private MessageService(Context paramContext)
  {
    this.s = paramContext;
    this.q = new MessageDBHelper(paramContext);
  }

  private long a(long paramLong, int paramInt)
  {
    return paramLong + aQ.a(1000 * (paramInt * 60));
  }

  // ERROR //
  private void a(String paramString1, String paramString2, String paramString3, int paramInt1, long paramLong, int paramInt2, int paramInt3)
  {
    // Byte code:
    //   0: ldc 8
    //   2: new 80	java/lang/StringBuilder
    //   5: dup
    //   6: invokespecial 81	java/lang/StringBuilder:<init>	()V
    //   9: ldc 83
    //   11: invokevirtual 87	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   14: aload_1
    //   15: invokevirtual 87	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   18: ldc 89
    //   20: invokevirtual 87	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   23: invokevirtual 93	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   26: invokestatic 98	com/umeng/message/proguard/Q:c	(Ljava/lang/String;Ljava/lang/String;)V
    //   29: aconst_null
    //   30: astore 9
    //   32: aload_2
    //   33: invokestatic 104	android/text/TextUtils:isEmpty	(Ljava/lang/CharSequence;)Z
    //   36: ifeq +130 -> 166
    //   39: ldc 106
    //   41: astore_2
    //   42: iconst_m1
    //   43: istore 16
    //   45: aload_3
    //   46: invokestatic 104	android/text/TextUtils:isEmpty	(Ljava/lang/CharSequence;)Z
    //   49: ifeq +6 -> 55
    //   52: ldc 106
    //   54: astore_3
    //   55: aload_0
    //   56: getfield 63	org/android/agoo/client/MessageService:q	Landroid/database/sqlite/SQLiteOpenHelper;
    //   59: invokevirtual 112	android/database/sqlite/SQLiteOpenHelper:getWritableDatabase	()Landroid/database/sqlite/SQLiteDatabase;
    //   62: astore 17
    //   64: aload 17
    //   66: astore 9
    //   68: bipush 9
    //   70: anewarray 4	java/lang/Object
    //   73: astore 19
    //   75: aload 19
    //   77: iconst_0
    //   78: aload_1
    //   79: aastore
    //   80: aload 19
    //   82: iconst_1
    //   83: iload 4
    //   85: invokestatic 118	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
    //   88: aastore
    //   89: aload 19
    //   91: iconst_2
    //   92: iload 16
    //   94: invokestatic 118	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
    //   97: aastore
    //   98: aload 19
    //   100: iconst_3
    //   101: iconst_0
    //   102: invokestatic 118	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
    //   105: aastore
    //   106: aload 19
    //   108: iconst_4
    //   109: lload 5
    //   111: invokestatic 123	java/lang/Long:valueOf	(J)Ljava/lang/Long;
    //   114: aastore
    //   115: aload 19
    //   117: iconst_5
    //   118: iload 7
    //   120: invokestatic 118	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
    //   123: aastore
    //   124: aload 19
    //   126: bipush 6
    //   128: aload_3
    //   129: aastore
    //   130: aload 19
    //   132: bipush 7
    //   134: aload_2
    //   135: aastore
    //   136: aload 19
    //   138: bipush 8
    //   140: iload 8
    //   142: invokestatic 118	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
    //   145: aastore
    //   146: aload 9
    //   148: ldc 125
    //   150: aload 19
    //   152: invokevirtual 131	android/database/sqlite/SQLiteDatabase:execSQL	(Ljava/lang/String;[Ljava/lang/Object;)V
    //   155: aload 9
    //   157: ifnull +8 -> 165
    //   160: aload 9
    //   162: invokevirtual 134	android/database/sqlite/SQLiteDatabase:close	()V
    //   165: return
    //   166: aload_2
    //   167: invokevirtual 140	java/lang/String:hashCode	()I
    //   170: istore 21
    //   172: iload 21
    //   174: istore 16
    //   176: goto -131 -> 45
    //   179: astore 14
    //   181: aload 9
    //   183: ifnull -18 -> 165
    //   186: aload 9
    //   188: invokevirtual 134	android/database/sqlite/SQLiteDatabase:close	()V
    //   191: return
    //   192: astore 15
    //   194: return
    //   195: astore 10
    //   197: aconst_null
    //   198: astore 11
    //   200: aload 10
    //   202: astore 12
    //   204: aload 11
    //   206: ifnull +8 -> 214
    //   209: aload 11
    //   211: invokevirtual 134	android/database/sqlite/SQLiteDatabase:close	()V
    //   214: aload 12
    //   216: athrow
    //   217: astore 20
    //   219: return
    //   220: astore 13
    //   222: goto -8 -> 214
    //   225: astore 18
    //   227: aload 9
    //   229: astore 11
    //   231: aload 18
    //   233: astore 12
    //   235: goto -31 -> 204
    //
    // Exception table:
    //   from	to	target	type
    //   32	39	179	java/lang/Throwable
    //   45	52	179	java/lang/Throwable
    //   55	64	179	java/lang/Throwable
    //   68	155	179	java/lang/Throwable
    //   166	172	179	java/lang/Throwable
    //   186	191	192	java/lang/Throwable
    //   32	39	195	finally
    //   45	52	195	finally
    //   55	64	195	finally
    //   166	172	195	finally
    //   160	165	217	java/lang/Throwable
    //   209	214	220	java/lang/Throwable
    //   68	155	225	finally
  }

  private void a(String paramString1, String paramString2, String paramString3, long paramLong, int paramInt1, int paramInt2)
  {
    if (paramLong < System.currentTimeMillis())
    {
      Q.c("MessageService", "sendAtTime messageId[" + paramString1 + "] targetTime[" + aM.a(paramLong) + "] <=currentTime[" + aM.a(System.currentTimeMillis()) + "]");
      return;
    }
    long l1 = a(paramLong, paramInt1);
    Q.c("MessageService", "sendAtTime message---->[" + paramString1 + "]serverTime--->[" + aM.a(paramLong) + "," + paramInt1 + " min]targetTime---->[" + aM.a(l1) + "]");
    Bundle localBundle = new Bundle();
    localBundle.putString("body", paramString2);
    localBundle.putString("id", paramString1);
    localBundle.putString("type", paramString3);
    localBundle.putBoolean("local", true);
    localBundle.putString("notify", "" + paramInt2);
    AlarmManager localAlarmManager = (AlarmManager)this.s.getSystemService("alarm");
    Intent localIntent = new Intent();
    localIntent.setAction("org.agoo.android.intent.action.RECEIVE");
    localIntent.setPackage(this.s.getPackageName());
    localIntent.putExtras(localBundle);
    localAlarmManager.set(1, l1, PendingIntent.getBroadcast(this.s, paramString1.hashCode(), localIntent, 134217728));
  }

  public static MessageService getSingleton(Context paramContext)
  {
    try
    {
      if (r == null)
        r = new MessageService(paramContext);
      MessageService localMessageService = r;
      return localMessageService;
    }
    finally
    {
    }
  }

  public void addMessage(String paramString1, String paramString2, String paramString3, int paramInt)
  {
    a(paramString1, paramString2, paramString3, 1, -1L, -1, paramInt);
  }

  public void deleteExpireTimeMessage()
  {
    deleteExpireTimeMessage(30);
  }

  // ERROR //
  public void deleteExpireTimeMessage(int paramInt)
  {
    // Byte code:
    //   0: aconst_null
    //   1: astore_2
    //   2: iload_1
    //   3: bipush 7
    //   5: if_icmple +91 -> 96
    //   8: aload_0
    //   9: getfield 63	org/android/agoo/client/MessageService:q	Landroid/database/sqlite/SQLiteOpenHelper;
    //   12: invokevirtual 112	android/database/sqlite/SQLiteOpenHelper:getWritableDatabase	()Landroid/database/sqlite/SQLiteDatabase;
    //   15: astore_2
    //   16: aload_2
    //   17: new 80	java/lang/StringBuilder
    //   20: dup
    //   21: invokespecial 81	java/lang/StringBuilder:<init>	()V
    //   24: ldc 240
    //   26: invokevirtual 87	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   29: iload_1
    //   30: invokevirtual 169	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
    //   33: ldc 242
    //   35: invokevirtual 87	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   38: invokevirtual 93	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   41: invokevirtual 245	android/database/sqlite/SQLiteDatabase:execSQL	(Ljava/lang/String;)V
    //   44: aload_2
    //   45: ifnull +7 -> 52
    //   48: aload_2
    //   49: invokevirtual 134	android/database/sqlite/SQLiteDatabase:close	()V
    //   52: return
    //   53: astore 5
    //   55: ldc 8
    //   57: ldc 247
    //   59: aload 5
    //   61: invokestatic 252	android/util/Log:e	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I
    //   64: pop
    //   65: aload_2
    //   66: ifnull -14 -> 52
    //   69: aload_2
    //   70: invokevirtual 134	android/database/sqlite/SQLiteDatabase:close	()V
    //   73: return
    //   74: astore 7
    //   76: return
    //   77: astore_3
    //   78: aload_2
    //   79: ifnull +7 -> 86
    //   82: aload_2
    //   83: invokevirtual 134	android/database/sqlite/SQLiteDatabase:close	()V
    //   86: aload_3
    //   87: athrow
    //   88: astore 8
    //   90: return
    //   91: astore 4
    //   93: goto -7 -> 86
    //   96: bipush 7
    //   98: istore_1
    //   99: goto -91 -> 8
    //
    // Exception table:
    //   from	to	target	type
    //   8	44	53	java/lang/Throwable
    //   69	73	74	java/lang/Throwable
    //   8	44	77	finally
    //   55	65	77	finally
    //   48	52	88	java/lang/Throwable
    //   82	86	91	java/lang/Throwable
  }

  // ERROR //
  public void handleMessageAtTime(String paramString1, String paramString2, String paramString3, String paramString4, int paramInt)
  {
    // Byte code:
    //   0: aload 4
    //   2: ldc_w 256
    //   5: invokevirtual 260	java/lang/String:split	(Ljava/lang/String;)[Ljava/lang/String;
    //   8: astore 6
    //   10: aload 6
    //   12: arraylength
    //   13: iconst_2
    //   14: if_icmpne +215 -> 229
    //   17: aload 6
    //   19: iconst_0
    //   20: aaload
    //   21: invokestatic 264	java/lang/Long:parseLong	(Ljava/lang/String;)J
    //   24: lstore 13
    //   26: lload 13
    //   28: lstore 11
    //   30: aload 6
    //   32: iconst_1
    //   33: aaload
    //   34: invokestatic 268	java/lang/Integer:parseInt	(Ljava/lang/String;)I
    //   37: istore 16
    //   39: iload 16
    //   41: istore 7
    //   43: lload 11
    //   45: lstore 8
    //   47: lload 8
    //   49: ldc2_w 231
    //   52: lcmp
    //   53: ifeq +18 -> 71
    //   56: iload 7
    //   58: iconst_m1
    //   59: if_icmpeq +12 -> 71
    //   62: lload 8
    //   64: invokestatic 147	java/lang/System:currentTimeMillis	()J
    //   67: lcmp
    //   68: ifge +70 -> 138
    //   71: ldc 8
    //   73: new 80	java/lang/StringBuilder
    //   76: dup
    //   77: invokespecial 81	java/lang/StringBuilder:<init>	()V
    //   80: ldc_w 270
    //   83: invokevirtual 87	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   86: aload_1
    //   87: invokevirtual 87	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   90: ldc_w 272
    //   93: invokevirtual 87	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   96: lload 8
    //   98: invokestatic 156	com/umeng/message/proguard/aM:a	(J)Ljava/lang/String;
    //   101: invokevirtual 87	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   104: ldc 158
    //   106: invokevirtual 87	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   109: invokestatic 147	java/lang/System:currentTimeMillis	()J
    //   112: invokestatic 156	com/umeng/message/proguard/aM:a	(J)Ljava/lang/String;
    //   115: invokevirtual 87	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   118: ldc 89
    //   120: invokevirtual 87	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   123: invokevirtual 93	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   126: invokestatic 274	com/umeng/message/proguard/Q:e	(Ljava/lang/String;Ljava/lang/String;)V
    //   129: aload_0
    //   130: aload_1
    //   131: aload_2
    //   132: aload_3
    //   133: iload 5
    //   135: invokevirtual 276	org/android/agoo/client/MessageService:addMessage	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;I)V
    //   138: lload 8
    //   140: invokestatic 147	java/lang/System:currentTimeMillis	()J
    //   143: lcmp
    //   144: iflt +30 -> 174
    //   147: aload_0
    //   148: aload_1
    //   149: aload_2
    //   150: aload_3
    //   151: iconst_0
    //   152: lload 8
    //   154: iload 7
    //   156: iload 5
    //   158: invokespecial 234	org/android/agoo/client/MessageService:a	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;IJII)V
    //   161: aload_0
    //   162: aload_1
    //   163: aload_2
    //   164: aload_3
    //   165: lload 8
    //   167: iload 7
    //   169: iload 5
    //   171: invokespecial 278	org/android/agoo/client/MessageService:a	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;JII)V
    //   174: return
    //   175: astore 10
    //   177: ldc2_w 231
    //   180: lstore 11
    //   182: ldc 8
    //   184: new 80	java/lang/StringBuilder
    //   187: dup
    //   188: invokespecial 81	java/lang/StringBuilder:<init>	()V
    //   191: ldc_w 280
    //   194: invokevirtual 87	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   197: aload 4
    //   199: invokevirtual 87	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   202: ldc_w 282
    //   205: invokevirtual 87	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   208: invokevirtual 93	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   211: invokestatic 274	com/umeng/message/proguard/Q:e	(Ljava/lang/String;Ljava/lang/String;)V
    //   214: iconst_m1
    //   215: istore 7
    //   217: lload 11
    //   219: lstore 8
    //   221: goto -174 -> 47
    //   224: astore 15
    //   226: goto -44 -> 182
    //   229: iconst_m1
    //   230: istore 7
    //   232: ldc2_w 231
    //   235: lstore 8
    //   237: goto -190 -> 47
    //
    // Exception table:
    //   from	to	target	type
    //   17	26	175	java/lang/Throwable
    //   30	39	224	java/lang/Throwable
  }

  // ERROR //
  public boolean hasMessageDuplicate(String paramString, int paramInt)
  {
    // Byte code:
    //   0: aconst_null
    //   1: astore_3
    //   2: aload_0
    //   3: getfield 63	org/android/agoo/client/MessageService:q	Landroid/database/sqlite/SQLiteOpenHelper;
    //   6: invokevirtual 112	android/database/sqlite/SQLiteOpenHelper:getWritableDatabase	()Landroid/database/sqlite/SQLiteDatabase;
    //   9: astore 10
    //   11: aload 10
    //   13: astore 5
    //   15: aload 5
    //   17: ldc_w 286
    //   20: iconst_1
    //   21: anewarray 136	java/lang/String
    //   24: dup
    //   25: iconst_0
    //   26: aload_1
    //   27: aastore
    //   28: invokevirtual 290	android/database/sqlite/SQLiteDatabase:rawQuery	(Ljava/lang/String;[Ljava/lang/String;)Landroid/database/Cursor;
    //   31: astore_3
    //   32: iconst_0
    //   33: istore 8
    //   35: aload_3
    //   36: ifnull +39 -> 75
    //   39: aload_3
    //   40: invokeinterface 296 1 0
    //   45: istore 12
    //   47: iconst_0
    //   48: istore 8
    //   50: iload 12
    //   52: ifeq +23 -> 75
    //   55: aload_3
    //   56: iconst_0
    //   57: invokeinterface 300 2 0
    //   62: istore 13
    //   64: iconst_0
    //   65: istore 8
    //   67: iload 13
    //   69: ifle +6 -> 75
    //   72: iconst_1
    //   73: istore 8
    //   75: aload_3
    //   76: ifnull +9 -> 85
    //   79: aload_3
    //   80: invokeinterface 301 1 0
    //   85: aload 5
    //   87: ifnull +8 -> 95
    //   90: aload 5
    //   92: invokevirtual 134	android/database/sqlite/SQLiteDatabase:close	()V
    //   95: iload 8
    //   97: ireturn
    //   98: astore 7
    //   100: aconst_null
    //   101: astore 5
    //   103: aload_3
    //   104: ifnull +9 -> 113
    //   107: aload_3
    //   108: invokeinterface 301 1 0
    //   113: iconst_0
    //   114: istore 8
    //   116: aload 5
    //   118: ifnull -23 -> 95
    //   121: aload 5
    //   123: invokevirtual 134	android/database/sqlite/SQLiteDatabase:close	()V
    //   126: iconst_0
    //   127: ireturn
    //   128: astore 9
    //   130: iconst_0
    //   131: ireturn
    //   132: astore 4
    //   134: aconst_null
    //   135: astore 5
    //   137: aload_3
    //   138: ifnull +9 -> 147
    //   141: aload_3
    //   142: invokeinterface 301 1 0
    //   147: aload 5
    //   149: ifnull +8 -> 157
    //   152: aload 5
    //   154: invokevirtual 134	android/database/sqlite/SQLiteDatabase:close	()V
    //   157: aload 4
    //   159: athrow
    //   160: astore 6
    //   162: goto -5 -> 157
    //   165: astore 4
    //   167: goto -30 -> 137
    //   170: astore 11
    //   172: goto -69 -> 103
    //   175: astore 14
    //   177: iload 8
    //   179: ireturn
    //
    // Exception table:
    //   from	to	target	type
    //   2	11	98	java/lang/Throwable
    //   107	113	128	java/lang/Throwable
    //   121	126	128	java/lang/Throwable
    //   2	11	132	finally
    //   141	147	160	java/lang/Throwable
    //   152	157	160	java/lang/Throwable
    //   15	32	165	finally
    //   39	47	165	finally
    //   55	64	165	finally
    //   15	32	170	java/lang/Throwable
    //   39	47	170	java/lang/Throwable
    //   55	64	170	java/lang/Throwable
    //   79	85	175	java/lang/Throwable
    //   90	95	175	java/lang/Throwable
  }

  // ERROR //
  public void notice(String paramString)
  {
    // Byte code:
    //   0: aconst_null
    //   1: astore_2
    //   2: aload_0
    //   3: getfield 63	org/android/agoo/client/MessageService:q	Landroid/database/sqlite/SQLiteOpenHelper;
    //   6: invokevirtual 112	android/database/sqlite/SQLiteOpenHelper:getWritableDatabase	()Landroid/database/sqlite/SQLiteDatabase;
    //   9: astore_2
    //   10: aload_2
    //   11: ldc_w 304
    //   14: iconst_1
    //   15: anewarray 4	java/lang/Object
    //   18: dup
    //   19: iconst_0
    //   20: aload_1
    //   21: aastore
    //   22: invokevirtual 131	android/database/sqlite/SQLiteDatabase:execSQL	(Ljava/lang/String;[Ljava/lang/Object;)V
    //   25: aload_2
    //   26: ifnull +7 -> 33
    //   29: aload_2
    //   30: invokevirtual 134	android/database/sqlite/SQLiteDatabase:close	()V
    //   33: return
    //   34: astore 5
    //   36: ldc 8
    //   38: ldc 247
    //   40: aload 5
    //   42: invokestatic 252	android/util/Log:e	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I
    //   45: pop
    //   46: aload_2
    //   47: ifnull -14 -> 33
    //   50: aload_2
    //   51: invokevirtual 134	android/database/sqlite/SQLiteDatabase:close	()V
    //   54: return
    //   55: astore 7
    //   57: return
    //   58: astore_3
    //   59: aload_2
    //   60: ifnull +7 -> 67
    //   63: aload_2
    //   64: invokevirtual 134	android/database/sqlite/SQLiteDatabase:close	()V
    //   67: aload_3
    //   68: athrow
    //   69: astore 8
    //   71: return
    //   72: astore 4
    //   74: goto -7 -> 67
    //
    // Exception table:
    //   from	to	target	type
    //   2	25	34	java/lang/Throwable
    //   50	54	55	java/lang/Throwable
    //   2	25	58	finally
    //   36	46	58	finally
    //   29	33	69	java/lang/Throwable
    //   63	67	72	java/lang/Throwable
  }

  // ERROR //
  public void reloadMessageAtTime()
  {
    // Byte code:
    //   0: ldc 8
    //   2: ldc_w 307
    //   5: invokestatic 98	com/umeng/message/proguard/Q:c	(Ljava/lang/String;Ljava/lang/String;)V
    //   8: aload_0
    //   9: getfield 63	org/android/agoo/client/MessageService:q	Landroid/database/sqlite/SQLiteOpenHelper;
    //   12: invokevirtual 112	android/database/sqlite/SQLiteOpenHelper:getWritableDatabase	()Landroid/database/sqlite/SQLiteDatabase;
    //   15: astore 10
    //   17: aload 10
    //   19: astore 7
    //   21: aload 7
    //   23: ldc_w 309
    //   26: invokevirtual 245	android/database/sqlite/SQLiteDatabase:execSQL	(Ljava/lang/String;)V
    //   29: aload 7
    //   31: ldc_w 311
    //   34: iconst_1
    //   35: anewarray 136	java/lang/String
    //   38: dup
    //   39: iconst_0
    //   40: ldc_w 313
    //   43: aastore
    //   44: invokevirtual 290	android/database/sqlite/SQLiteDatabase:rawQuery	(Ljava/lang/String;[Ljava/lang/String;)Landroid/database/Cursor;
    //   47: astore 13
    //   49: aload 13
    //   51: astore 6
    //   53: aload 6
    //   55: ifnull +103 -> 158
    //   58: aload 6
    //   60: invokeinterface 316 1 0
    //   65: ifeq +93 -> 158
    //   68: aload_0
    //   69: aload 6
    //   71: iconst_0
    //   72: invokeinterface 320 2 0
    //   77: aload 6
    //   79: iconst_1
    //   80: invokeinterface 320 2 0
    //   85: aload 6
    //   87: iconst_2
    //   88: invokeinterface 320 2 0
    //   93: aload 6
    //   95: iconst_3
    //   96: invokeinterface 324 2 0
    //   101: aload 6
    //   103: iconst_4
    //   104: invokeinterface 300 2 0
    //   109: aload 6
    //   111: iconst_5
    //   112: invokeinterface 300 2 0
    //   117: invokespecial 278	org/android/agoo/client/MessageService:a	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;JII)V
    //   120: goto -62 -> 58
    //   123: astore 15
    //   125: aload 6
    //   127: astore_2
    //   128: aload 7
    //   130: astore_3
    //   131: aload_2
    //   132: ifnull +9 -> 141
    //   135: aload_2
    //   136: invokeinterface 301 1 0
    //   141: aload_3
    //   142: ifnull +7 -> 149
    //   145: aload_3
    //   146: invokevirtual 134	android/database/sqlite/SQLiteDatabase:close	()V
    //   149: ldc 8
    //   151: ldc_w 326
    //   154: invokestatic 98	com/umeng/message/proguard/Q:c	(Ljava/lang/String;Ljava/lang/String;)V
    //   157: return
    //   158: aload 6
    //   160: ifnull +10 -> 170
    //   163: aload 6
    //   165: invokeinterface 301 1 0
    //   170: aload 7
    //   172: ifnull -23 -> 149
    //   175: aload 7
    //   177: invokevirtual 134	android/database/sqlite/SQLiteDatabase:close	()V
    //   180: goto -31 -> 149
    //   183: astore 14
    //   185: goto -36 -> 149
    //   188: astore 5
    //   190: aconst_null
    //   191: astore 6
    //   193: aconst_null
    //   194: astore 7
    //   196: aload 5
    //   198: astore 8
    //   200: aload 6
    //   202: ifnull +10 -> 212
    //   205: aload 6
    //   207: invokeinterface 301 1 0
    //   212: aload 7
    //   214: ifnull +8 -> 222
    //   217: aload 7
    //   219: invokevirtual 134	android/database/sqlite/SQLiteDatabase:close	()V
    //   222: aload 8
    //   224: athrow
    //   225: astore 9
    //   227: goto -5 -> 222
    //   230: astore 12
    //   232: aload 12
    //   234: astore 8
    //   236: aconst_null
    //   237: astore 6
    //   239: goto -39 -> 200
    //   242: astore 8
    //   244: goto -44 -> 200
    //   247: astore 4
    //   249: goto -100 -> 149
    //   252: astore_1
    //   253: aconst_null
    //   254: astore_2
    //   255: aconst_null
    //   256: astore_3
    //   257: goto -126 -> 131
    //   260: astore 11
    //   262: aload 7
    //   264: astore_3
    //   265: aconst_null
    //   266: astore_2
    //   267: goto -136 -> 131
    //
    // Exception table:
    //   from	to	target	type
    //   58	120	123	java/lang/Throwable
    //   163	170	183	java/lang/Throwable
    //   175	180	183	java/lang/Throwable
    //   8	17	188	finally
    //   205	212	225	java/lang/Throwable
    //   217	222	225	java/lang/Throwable
    //   21	49	230	finally
    //   58	120	242	finally
    //   135	141	247	java/lang/Throwable
    //   145	149	247	java/lang/Throwable
    //   8	17	252	java/lang/Throwable
    //   21	49	260	java/lang/Throwable
  }

  public boolean report(String paramString1, String paramString2, String paramString3, String paramString4)
  {
    boolean bool1 = TextUtils.isEmpty(paramString3);
    boolean bool2 = false;
    if (!bool1);
    try
    {
      int i1 = Integer.parseInt(paramString3);
      bool2 = false;
      if (i1 >= -1)
      {
        MtopRequest localMtopRequest = new MtopRequest();
        localMtopRequest.setApi("mtop.push.msg.report");
        localMtopRequest.setV("1.0");
        localMtopRequest.putParams("messageId", paramString1 + "@" + paramString4);
        localMtopRequest.putParams("mesgStatus", "4");
        if (!TextUtils.isEmpty(paramString2))
          localMtopRequest.putParams("taskId", paramString2);
        localMtopRequest.setDeviceId(BaseRegistrar.getRegistrationId(this.s));
        MtopSyncClientV3 localMtopSyncClientV3 = new MtopSyncClientV3();
        localMtopSyncClientV3.setDefaultAppkey(P.n(this.s));
        localMtopSyncClientV3.setDefaultAppSecret(P.p(this.s));
        localMtopSyncClientV3.setBaseUrl(AgooSettings.getPullUrl(this.s));
        localMtopSyncClientV3.getV3(this.s, localMtopRequest);
        bool2 = true;
      }
      return bool2;
    }
    catch (Throwable localThrowable)
    {
      Q.e("MessageService", "[" + paramString3 + "] to Integer error", localThrowable);
    }
    return false;
  }

  private static class MessageDBHelper extends SQLiteOpenHelper
  {
    public MessageDBHelper(Context paramContext)
    {
      super("message_db", null, 1);
    }

    private String a()
    {
      StringBuffer localStringBuffer = new StringBuffer();
      localStringBuffer.append("create table message");
      localStringBuffer.append("(");
      localStringBuffer.append("id text UNIQUE not null,");
      localStringBuffer.append("state integer,");
      localStringBuffer.append("body_code integer,");
      localStringBuffer.append("report long,");
      localStringBuffer.append("target_time long,");
      localStringBuffer.append("interval integer,");
      localStringBuffer.append("type text,");
      localStringBuffer.append("message text,");
      localStringBuffer.append("notify integer,");
      localStringBuffer.append("create_time date");
      localStringBuffer.append(");");
      return localStringBuffer.toString();
    }

    private String b()
    {
      return "CREATE INDEX body_code_index ON message(body_code)";
    }

    private String c()
    {
      return "CREATE INDEX id_index ON message(id)";
    }

    public void onCreate(SQLiteDatabase paramSQLiteDatabase)
    {
      if (paramSQLiteDatabase != null);
      try
      {
        paramSQLiteDatabase.execSQL(a());
        paramSQLiteDatabase.execSQL(c());
        paramSQLiteDatabase.execSQL(b());
        return;
      }
      catch (Throwable localThrowable)
      {
        Q.d("MessageService", "messagedbhelper create", localThrowable);
      }
    }

    public void onUpgrade(SQLiteDatabase paramSQLiteDatabase, int paramInt1, int paramInt2)
    {
    }
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     org.android.agoo.client.MessageService
 * JD-Core Version:    0.6.2
 */