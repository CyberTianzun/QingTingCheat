package fm.qingting.qtradio.data;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import fm.qingting.framework.data.DataCommand;
import fm.qingting.framework.data.DataToken;
import fm.qingting.framework.data.IDataParser;
import fm.qingting.framework.data.IDataRecvHandler;
import fm.qingting.framework.data.IDataSource;
import fm.qingting.framework.data.IDataToken;
import fm.qingting.framework.data.ListData;
import fm.qingting.framework.data.Result;
import fm.qingting.qtradio.model.ActivityItem;
import fm.qingting.qtradio.model.NotifyMessage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class NotifyDS
  implements IDataSource
{
  private static NotifyDS instance;

  // ERROR //
  private void changeNotifyState(String paramString)
  {
    // Byte code:
    //   0: new 22	java/util/ArrayList
    //   3: dup
    //   4: invokespecial 23	java/util/ArrayList:<init>	()V
    //   7: astore_2
    //   8: invokestatic 29	fm/qingting/qtradio/data/DBManager:getInstance	()Lfm/qingting/qtradio/data/DBManager;
    //   11: ldc 31
    //   13: invokevirtual 35	fm/qingting/qtradio/data/DBManager:getWritableDB	(Ljava/lang/String;)Landroid/database/sqlite/SQLiteDatabase;
    //   16: astore_3
    //   17: aload_3
    //   18: ldc 37
    //   20: aconst_null
    //   21: invokevirtual 43	android/database/sqlite/SQLiteDatabase:rawQuery	(Ljava/lang/String;[Ljava/lang/String;)Landroid/database/Cursor;
    //   24: astore 4
    //   26: iconst_0
    //   27: istore 5
    //   29: aload_2
    //   30: astore 6
    //   32: aload 4
    //   34: invokeinterface 49 1 0
    //   39: ifeq +240 -> 279
    //   42: new 51	java/io/ByteArrayInputStream
    //   45: dup
    //   46: aload 4
    //   48: aload 4
    //   50: ldc 53
    //   52: invokeinterface 57 2 0
    //   57: invokeinterface 61 2 0
    //   62: invokespecial 64	java/io/ByteArrayInputStream:<init>	([B)V
    //   65: astore 7
    //   67: new 66	java/io/ObjectInputStream
    //   70: dup
    //   71: aload 7
    //   73: invokespecial 69	java/io/ObjectInputStream:<init>	(Ljava/io/InputStream;)V
    //   76: astore 8
    //   78: aload 8
    //   80: invokevirtual 73	java/io/ObjectInputStream:readObject	()Ljava/lang/Object;
    //   83: checkcast 22	java/util/ArrayList
    //   86: astore 17
    //   88: aload 8
    //   90: invokevirtual 76	java/io/ObjectInputStream:close	()V
    //   93: aload 7
    //   95: invokevirtual 77	java/io/ByteArrayInputStream:close	()V
    //   98: aload 17
    //   100: invokevirtual 81	java/util/ArrayList:size	()I
    //   103: ifle +81 -> 184
    //   106: aload 17
    //   108: invokevirtual 85	java/util/ArrayList:iterator	()Ljava/util/Iterator;
    //   111: astore 22
    //   113: iload 5
    //   115: istore 23
    //   117: aload 22
    //   119: invokeinterface 90 1 0
    //   124: ifeq +350 -> 474
    //   127: aload 22
    //   129: invokeinterface 93 1 0
    //   134: checkcast 95	fm/qingting/qtradio/model/NotifyMessage
    //   137: astore 27
    //   139: aload_1
    //   140: aload 27
    //   142: getfield 99	fm/qingting/qtradio/model/NotifyMessage:content	Ljava/lang/String;
    //   145: invokevirtual 105	java/lang/String:equalsIgnoreCase	(Ljava/lang/String;)Z
    //   148: ifeq +59 -> 207
    //   151: aload 27
    //   153: getfield 109	fm/qingting/qtradio/model/NotifyMessage:state	Lfm/qingting/qtradio/model/NotifyMessage$STATE;
    //   156: getstatic 114	fm/qingting/qtradio/model/NotifyMessage$STATE:FRESH	Lfm/qingting/qtradio/model/NotifyMessage$STATE;
    //   159: if_acmpne +48 -> 207
    //   162: aload 27
    //   164: getstatic 117	fm/qingting/qtradio/model/NotifyMessage$STATE:READ	Lfm/qingting/qtradio/model/NotifyMessage$STATE;
    //   167: putfield 109	fm/qingting/qtradio/model/NotifyMessage:state	Lfm/qingting/qtradio/model/NotifyMessage$STATE;
    //   170: aload 17
    //   172: iload 23
    //   174: aload 27
    //   176: invokevirtual 121	java/util/ArrayList:set	(ILjava/lang/Object;)Ljava/lang/Object;
    //   179: pop
    //   180: iload 23
    //   182: istore 5
    //   184: iload 5
    //   186: istore 21
    //   188: aload 17
    //   190: astore 11
    //   192: iload 21
    //   194: istore 10
    //   196: aload 11
    //   198: astore 6
    //   200: iload 10
    //   202: istore 5
    //   204: goto -172 -> 32
    //   207: iinc 23 1
    //   210: goto -93 -> 117
    //   213: astore 15
    //   215: iload 5
    //   217: istore 10
    //   219: aload 6
    //   221: astore 11
    //   223: aload 15
    //   225: astore 16
    //   227: aload 16
    //   229: invokevirtual 124	java/io/StreamCorruptedException:printStackTrace	()V
    //   232: goto -36 -> 196
    //   235: astore 13
    //   237: iload 5
    //   239: istore 10
    //   241: aload 6
    //   243: astore 11
    //   245: aload 13
    //   247: astore 14
    //   249: aload 14
    //   251: invokevirtual 125	java/io/IOException:printStackTrace	()V
    //   254: goto -58 -> 196
    //   257: astore 9
    //   259: iload 5
    //   261: istore 10
    //   263: aload 6
    //   265: astore 11
    //   267: aload 9
    //   269: astore 12
    //   271: aload 12
    //   273: invokevirtual 126	java/lang/ClassNotFoundException:printStackTrace	()V
    //   276: goto -80 -> 196
    //   279: aload_3
    //   280: ldc 128
    //   282: iconst_1
    //   283: anewarray 4	java/lang/Object
    //   286: dup
    //   287: iconst_0
    //   288: ldc 130
    //   290: aastore
    //   291: invokevirtual 134	android/database/sqlite/SQLiteDatabase:execSQL	(Ljava/lang/String;[Ljava/lang/Object;)V
    //   294: new 136	java/io/ByteArrayOutputStream
    //   297: dup
    //   298: invokespecial 137	java/io/ByteArrayOutputStream:<init>	()V
    //   301: astore 29
    //   303: new 139	java/io/ObjectOutputStream
    //   306: dup
    //   307: aload 29
    //   309: invokespecial 142	java/io/ObjectOutputStream:<init>	(Ljava/io/OutputStream;)V
    //   312: astore 30
    //   314: aload 30
    //   316: aload 6
    //   318: invokevirtual 146	java/io/ObjectOutputStream:writeObject	(Ljava/lang/Object;)V
    //   321: aload 30
    //   323: invokevirtual 147	java/io/ObjectOutputStream:close	()V
    //   326: aload 29
    //   328: invokevirtual 148	java/io/ByteArrayOutputStream:close	()V
    //   331: aload_3
    //   332: ldc 150
    //   334: iconst_2
    //   335: anewarray 4	java/lang/Object
    //   338: dup
    //   339: iconst_0
    //   340: ldc 130
    //   342: aastore
    //   343: dup
    //   344: iconst_1
    //   345: aload 29
    //   347: invokevirtual 154	java/io/ByteArrayOutputStream:toByteArray	()[B
    //   350: aastore
    //   351: invokevirtual 134	android/database/sqlite/SQLiteDatabase:execSQL	(Ljava/lang/String;[Ljava/lang/Object;)V
    //   354: aload 4
    //   356: invokeinterface 155 1 0
    //   361: return
    //   362: astore 31
    //   364: aload 31
    //   366: invokevirtual 125	java/io/IOException:printStackTrace	()V
    //   369: goto -38 -> 331
    //   372: astore 12
    //   374: iload 5
    //   376: istore 20
    //   378: aload 17
    //   380: astore 11
    //   382: iload 20
    //   384: istore 10
    //   386: goto -115 -> 271
    //   389: astore 26
    //   391: aload 17
    //   393: astore 11
    //   395: iload 23
    //   397: istore 10
    //   399: aload 26
    //   401: astore 12
    //   403: goto -132 -> 271
    //   406: astore 14
    //   408: iload 5
    //   410: istore 19
    //   412: aload 17
    //   414: astore 11
    //   416: iload 19
    //   418: istore 10
    //   420: goto -171 -> 249
    //   423: astore 25
    //   425: aload 17
    //   427: astore 11
    //   429: iload 23
    //   431: istore 10
    //   433: aload 25
    //   435: astore 14
    //   437: goto -188 -> 249
    //   440: astore 16
    //   442: iload 5
    //   444: istore 18
    //   446: aload 17
    //   448: astore 11
    //   450: iload 18
    //   452: istore 10
    //   454: goto -227 -> 227
    //   457: astore 24
    //   459: aload 17
    //   461: astore 11
    //   463: iload 23
    //   465: istore 10
    //   467: aload 24
    //   469: astore 16
    //   471: goto -244 -> 227
    //   474: iload 23
    //   476: istore 5
    //   478: goto -294 -> 184
    //
    // Exception table:
    //   from	to	target	type
    //   67	88	213	java/io/StreamCorruptedException
    //   67	88	235	java/io/IOException
    //   67	88	257	java/lang/ClassNotFoundException
    //   303	331	362	java/io/IOException
    //   88	113	372	java/lang/ClassNotFoundException
    //   117	180	389	java/lang/ClassNotFoundException
    //   88	113	406	java/io/IOException
    //   117	180	423	java/io/IOException
    //   88	113	440	java/io/StreamCorruptedException
    //   117	180	457	java/io/StreamCorruptedException
  }

  private DataToken doChangeNotifyCommand(DataCommand paramDataCommand)
  {
    DataToken localDataToken = new DataToken();
    localDataToken.setDataInfo(paramDataCommand);
    changeNotifyState((String)paramDataCommand.getParam().get("content"));
    localDataToken.setData(new Result(true, null));
    return localDataToken;
  }

  private DataToken doGetActivitiesCommand(DataCommand paramDataCommand)
  {
    DataToken localDataToken = new DataToken();
    localDataToken.setDataInfo(paramDataCommand);
    ListData localListData = getActivities();
    if (localListData == null)
    {
      localDataToken.setData(new Result(false, null));
      return localDataToken;
    }
    localDataToken.setData(new Result(true, localListData, null, null));
    return localDataToken;
  }

  private DataToken doGetNotifyCommand(DataCommand paramDataCommand)
  {
    DataToken localDataToken = new DataToken();
    localDataToken.setDataInfo(paramDataCommand);
    ListData localListData = getNotify();
    if (localListData == null)
    {
      localDataToken.setData(new Result(false, null));
      return localDataToken;
    }
    localDataToken.setData(new Result(true, localListData, null, null));
    return localDataToken;
  }

  private DataToken doUpdateActivitiesCommand(DataCommand paramDataCommand)
  {
    DataToken localDataToken = new DataToken();
    localDataToken.setDataInfo(paramDataCommand);
    updateActivities((List)paramDataCommand.getParam().get("activities"));
    localDataToken.setData(new Result(true, null));
    return localDataToken;
  }

  private DataToken doUpdateNotifyCommand(DataCommand paramDataCommand)
  {
    DataToken localDataToken = new DataToken();
    localDataToken.setDataInfo(paramDataCommand);
    updateNotify("NotifyList", (List)paramDataCommand.getParam().get("notifylist"));
    localDataToken.setData(new Result(true, null));
    return localDataToken;
  }

  // ERROR //
  private ListData getActivities()
  {
    // Byte code:
    //   0: new 22	java/util/ArrayList
    //   3: dup
    //   4: invokespecial 23	java/util/ArrayList:<init>	()V
    //   7: astore_1
    //   8: invokestatic 29	fm/qingting/qtradio/data/DBManager:getInstance	()Lfm/qingting/qtradio/data/DBManager;
    //   11: ldc 216
    //   13: invokevirtual 219	fm/qingting/qtradio/data/DBManager:getReadableDB	(Ljava/lang/String;)Landroid/database/sqlite/SQLiteDatabase;
    //   16: ldc 221
    //   18: aconst_null
    //   19: invokevirtual 43	android/database/sqlite/SQLiteDatabase:rawQuery	(Ljava/lang/String;[Ljava/lang/String;)Landroid/database/Cursor;
    //   22: astore_2
    //   23: aload_2
    //   24: invokeinterface 49 1 0
    //   29: ifeq +117 -> 146
    //   32: new 51	java/io/ByteArrayInputStream
    //   35: dup
    //   36: aload_2
    //   37: aload_2
    //   38: ldc 223
    //   40: invokeinterface 57 2 0
    //   45: invokeinterface 61 2 0
    //   50: invokespecial 64	java/io/ByteArrayInputStream:<init>	([B)V
    //   53: astore_3
    //   54: new 66	java/io/ObjectInputStream
    //   57: dup
    //   58: aload_3
    //   59: invokespecial 69	java/io/ObjectInputStream:<init>	(Ljava/io/InputStream;)V
    //   62: astore 4
    //   64: aload 4
    //   66: invokevirtual 73	java/io/ObjectInputStream:readObject	()Ljava/lang/Object;
    //   69: checkcast 225	fm/qingting/qtradio/model/ActivityItem
    //   72: astore 7
    //   74: aload 4
    //   76: invokevirtual 76	java/io/ObjectInputStream:close	()V
    //   79: aload_3
    //   80: invokevirtual 77	java/io/ByteArrayInputStream:close	()V
    //   83: aload_1
    //   84: aload 7
    //   86: invokeinterface 229 2 0
    //   91: pop
    //   92: goto -69 -> 23
    //   95: astore 11
    //   97: aload 11
    //   99: astore 12
    //   101: aconst_null
    //   102: astore 7
    //   104: aload 12
    //   106: invokevirtual 124	java/io/StreamCorruptedException:printStackTrace	()V
    //   109: goto -26 -> 83
    //   112: astore 9
    //   114: aload 9
    //   116: astore 10
    //   118: aconst_null
    //   119: astore 7
    //   121: aload 10
    //   123: invokevirtual 125	java/io/IOException:printStackTrace	()V
    //   126: goto -43 -> 83
    //   129: astore 5
    //   131: aload 5
    //   133: astore 6
    //   135: aconst_null
    //   136: astore 7
    //   138: aload 6
    //   140: invokevirtual 126	java/lang/ClassNotFoundException:printStackTrace	()V
    //   143: goto -60 -> 83
    //   146: aload_2
    //   147: invokeinterface 155 1 0
    //   152: aload_1
    //   153: invokeinterface 230 1 0
    //   158: ifle +13 -> 171
    //   161: new 232	fm/qingting/framework/data/ListData
    //   164: dup
    //   165: aload_1
    //   166: aconst_null
    //   167: invokespecial 235	fm/qingting/framework/data/ListData:<init>	(Ljava/util/List;Lfm/qingting/framework/data/Navigation;)V
    //   170: areturn
    //   171: aconst_null
    //   172: areturn
    //   173: astore 6
    //   175: goto -37 -> 138
    //   178: astore 10
    //   180: goto -59 -> 121
    //   183: astore 12
    //   185: goto -81 -> 104
    //
    // Exception table:
    //   from	to	target	type
    //   54	74	95	java/io/StreamCorruptedException
    //   54	74	112	java/io/IOException
    //   54	74	129	java/lang/ClassNotFoundException
    //   74	83	173	java/lang/ClassNotFoundException
    //   74	83	178	java/io/IOException
    //   74	83	183	java/io/StreamCorruptedException
  }

  public static NotifyDS getInstance()
  {
    if (instance == null)
      instance = new NotifyDS();
    return instance;
  }

  // ERROR //
  private ListData getNotify()
  {
    // Byte code:
    //   0: new 22	java/util/ArrayList
    //   3: dup
    //   4: invokespecial 23	java/util/ArrayList:<init>	()V
    //   7: astore_1
    //   8: invokestatic 29	fm/qingting/qtradio/data/DBManager:getInstance	()Lfm/qingting/qtradio/data/DBManager;
    //   11: ldc 31
    //   13: invokevirtual 219	fm/qingting/qtradio/data/DBManager:getReadableDB	(Ljava/lang/String;)Landroid/database/sqlite/SQLiteDatabase;
    //   16: ldc 37
    //   18: aconst_null
    //   19: invokevirtual 43	android/database/sqlite/SQLiteDatabase:rawQuery	(Ljava/lang/String;[Ljava/lang/String;)Landroid/database/Cursor;
    //   22: astore_2
    //   23: aload_1
    //   24: astore_3
    //   25: aload_2
    //   26: invokeinterface 49 1 0
    //   31: ifeq +114 -> 145
    //   34: new 51	java/io/ByteArrayInputStream
    //   37: dup
    //   38: aload_2
    //   39: aload_2
    //   40: ldc 53
    //   42: invokeinterface 57 2 0
    //   47: invokeinterface 61 2 0
    //   52: invokespecial 64	java/io/ByteArrayInputStream:<init>	([B)V
    //   55: astore 4
    //   57: new 66	java/io/ObjectInputStream
    //   60: dup
    //   61: aload 4
    //   63: invokespecial 69	java/io/ObjectInputStream:<init>	(Ljava/io/InputStream;)V
    //   66: astore 5
    //   68: aload 5
    //   70: invokevirtual 73	java/io/ObjectInputStream:readObject	()Ljava/lang/Object;
    //   73: checkcast 22	java/util/ArrayList
    //   76: astore 7
    //   78: aload 5
    //   80: invokevirtual 76	java/io/ObjectInputStream:close	()V
    //   83: aload 4
    //   85: invokevirtual 77	java/io/ByteArrayInputStream:close	()V
    //   88: aload 7
    //   90: astore_3
    //   91: goto -66 -> 25
    //   94: astore 11
    //   96: aload_3
    //   97: astore 7
    //   99: aload 11
    //   101: astore 12
    //   103: aload 12
    //   105: invokevirtual 124	java/io/StreamCorruptedException:printStackTrace	()V
    //   108: goto -20 -> 88
    //   111: astore 9
    //   113: aload_3
    //   114: astore 7
    //   116: aload 9
    //   118: astore 10
    //   120: aload 10
    //   122: invokevirtual 125	java/io/IOException:printStackTrace	()V
    //   125: goto -37 -> 88
    //   128: astore 6
    //   130: aload_3
    //   131: astore 7
    //   133: aload 6
    //   135: astore 8
    //   137: aload 8
    //   139: invokevirtual 126	java/lang/ClassNotFoundException:printStackTrace	()V
    //   142: goto -54 -> 88
    //   145: aload_2
    //   146: invokeinterface 155 1 0
    //   151: aload_3
    //   152: invokevirtual 81	java/util/ArrayList:size	()I
    //   155: ifle +13 -> 168
    //   158: new 232	fm/qingting/framework/data/ListData
    //   161: dup
    //   162: aload_3
    //   163: aconst_null
    //   164: invokespecial 235	fm/qingting/framework/data/ListData:<init>	(Ljava/util/List;Lfm/qingting/framework/data/Navigation;)V
    //   167: areturn
    //   168: aconst_null
    //   169: areturn
    //   170: astore 8
    //   172: goto -35 -> 137
    //   175: astore 10
    //   177: goto -57 -> 120
    //   180: astore 12
    //   182: goto -79 -> 103
    //
    // Exception table:
    //   from	to	target	type
    //   57	78	94	java/io/StreamCorruptedException
    //   57	78	111	java/io/IOException
    //   57	78	128	java/lang/ClassNotFoundException
    //   78	88	170	java/lang/ClassNotFoundException
    //   78	88	175	java/io/IOException
    //   78	88	180	java/io/StreamCorruptedException
  }

  private void updateActivities(List<ActivityItem> paramList)
  {
    SQLiteDatabase localSQLiteDatabase = DBManager.getInstance().getWritableDB("activity");
    localSQLiteDatabase.execSQL("delete from activitylist");
    Iterator localIterator = paramList.iterator();
    while (true)
      if (localIterator.hasNext())
      {
        ActivityItem localActivityItem = (ActivityItem)localIterator.next();
        ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream();
        try
        {
          ObjectOutputStream localObjectOutputStream = new ObjectOutputStream(localByteArrayOutputStream);
          localObjectOutputStream.writeObject(localActivityItem);
          localObjectOutputStream.close();
          localByteArrayOutputStream.close();
          byte[] arrayOfByte = localByteArrayOutputStream.toByteArray();
          Object[] arrayOfObject = new Object[2];
          arrayOfObject[0] = localActivityItem.title;
          arrayOfObject[1] = arrayOfByte;
          localSQLiteDatabase.execSQL("insert into activitylist(title,item)values(?,?)", arrayOfObject);
        }
        catch (IOException localIOException)
        {
          while (true)
            localIOException.printStackTrace();
        }
      }
  }

  private void updateNotify(String paramString, List<NotifyMessage> paramList)
  {
    SQLiteDatabase localSQLiteDatabase = DBManager.getInstance().getWritableDB("notification");
    Cursor localCursor = localSQLiteDatabase.rawQuery("select listname from notification", null);
    do
      if (!localCursor.moveToNext())
        break;
    while (!paramString.equalsIgnoreCase(localCursor.getString(localCursor.getColumnIndex("listname"))));
    for (int i = 1; ; i = 0)
    {
      if (i != 0)
        localSQLiteDatabase.execSQL("delete from notification where listname =?", new Object[] { paramString });
      ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream();
      try
      {
        ObjectOutputStream localObjectOutputStream = new ObjectOutputStream(localByteArrayOutputStream);
        localObjectOutputStream.writeObject(paramList);
        localObjectOutputStream.close();
        localByteArrayOutputStream.close();
        localSQLiteDatabase.execSQL("insert into notification(listname,message)values(?,?)", new Object[] { paramString, localByteArrayOutputStream.toByteArray() });
        localCursor.close();
        return;
      }
      catch (IOException localIOException)
      {
        while (true)
          localIOException.printStackTrace();
      }
    }
  }

  public void addParser(IDataParser paramIDataParser)
  {
  }

  public String dataSourceName()
  {
    return "Notify";
  }

  public IDataToken doCommand(DataCommand paramDataCommand, IDataRecvHandler paramIDataRecvHandler)
  {
    String str = paramDataCommand.getCurrentCommand();
    if (str.equalsIgnoreCase("getNotify"))
      return doGetNotifyCommand(paramDataCommand);
    if (str.equalsIgnoreCase("updateNotify"))
      return doUpdateNotifyCommand(paramDataCommand);
    if (str.equalsIgnoreCase("changeNotifyState"))
      return doChangeNotifyCommand(paramDataCommand);
    if (str.equalsIgnoreCase("getActivities"))
      return doGetActivitiesCommand(paramDataCommand);
    if (str.equalsIgnoreCase("updateActivities"))
      return doUpdateActivitiesCommand(paramDataCommand);
    return null;
  }

  public boolean isSynchronous(String paramString, Map<String, Object> paramMap)
  {
    return true;
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.qtradio.data.NotifyDS
 * JD-Core Version:    0.6.2
 */