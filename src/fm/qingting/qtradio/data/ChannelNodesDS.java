package fm.qingting.qtradio.data;

import android.database.sqlite.SQLiteDatabase;
import com.google.gson.Gson;
import fm.qingting.framework.data.DataCommand;
import fm.qingting.framework.data.DataToken;
import fm.qingting.framework.data.IDataParser;
import fm.qingting.framework.data.IDataRecvHandler;
import fm.qingting.framework.data.IDataSource;
import fm.qingting.framework.data.IDataToken;
import fm.qingting.framework.data.Result;
import fm.qingting.qtradio.model.ChannelNode;
import java.util.List;
import java.util.Map;

public class ChannelNodesDS
  implements IDataSource
{
  private static ChannelNodesDS instance;

  // ERROR //
  private List<fm.qingting.qtradio.model.Node> acquireChannelNodes(DataCommand paramDataCommand)
  {
    // Byte code:
    //   0: aconst_null
    //   1: astore_2
    //   2: aload_1
    //   3: invokevirtual 22	fm/qingting/framework/data/DataCommand:getParam	()Ljava/util/Map;
    //   6: ldc 24
    //   8: invokeinterface 30 2 0
    //   13: checkcast 32	java/lang/String
    //   16: astore 4
    //   18: aload 4
    //   20: ifnull +184 -> 204
    //   23: aload 4
    //   25: ldc 34
    //   27: invokevirtual 38	java/lang/String:equalsIgnoreCase	(Ljava/lang/String;)Z
    //   30: ifeq +6 -> 36
    //   33: goto +171 -> 204
    //   36: new 40	java/lang/StringBuilder
    //   39: dup
    //   40: invokespecial 41	java/lang/StringBuilder:<init>	()V
    //   43: ldc 43
    //   45: invokevirtual 47	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   48: aload 4
    //   50: invokevirtual 47	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   53: ldc 49
    //   55: invokevirtual 47	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   58: invokevirtual 53	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   61: astore 5
    //   63: new 55	java/util/ArrayList
    //   66: dup
    //   67: invokespecial 56	java/util/ArrayList:<init>	()V
    //   70: astore 6
    //   72: invokestatic 62	fm/qingting/qtradio/data/DBManager:getInstance	()Lfm/qingting/qtradio/data/DBManager;
    //   75: ldc 64
    //   77: invokevirtual 68	fm/qingting/qtradio/data/DBManager:getReadableDB	(Ljava/lang/String;)Landroid/database/sqlite/SQLiteDatabase;
    //   80: aload 5
    //   82: aconst_null
    //   83: invokevirtual 74	android/database/sqlite/SQLiteDatabase:rawQuery	(Ljava/lang/String;[Ljava/lang/String;)Landroid/database/Cursor;
    //   86: astore 8
    //   88: new 76	com/google/gson/Gson
    //   91: dup
    //   92: invokespecial 77	com/google/gson/Gson:<init>	()V
    //   95: astore 9
    //   97: aconst_null
    //   98: astore 10
    //   100: aload 8
    //   102: invokeinterface 83 1 0
    //   107: ifeq +79 -> 186
    //   110: aload 8
    //   112: aload 8
    //   114: ldc 85
    //   116: invokeinterface 89 2 0
    //   121: invokeinterface 93 2 0
    //   126: astore 11
    //   128: aload 9
    //   130: aload 11
    //   132: ldc 95
    //   134: invokevirtual 99	com/google/gson/Gson:fromJson	(Ljava/lang/String;Ljava/lang/Class;)Ljava/lang/Object;
    //   137: checkcast 95	fm/qingting/qtradio/model/ChannelNode
    //   140: astore 13
    //   142: aload 10
    //   144: ifnull +22 -> 166
    //   147: aload 13
    //   149: ifnull +17 -> 166
    //   152: aload 13
    //   154: aload 10
    //   156: putfield 105	fm/qingting/qtradio/model/Node:prevSibling	Lfm/qingting/qtradio/model/Node;
    //   159: aload 10
    //   161: aload 13
    //   163: putfield 108	fm/qingting/qtradio/model/Node:nextSibling	Lfm/qingting/qtradio/model/Node;
    //   166: aload 6
    //   168: aload 13
    //   170: invokeinterface 114 2 0
    //   175: pop
    //   176: aload 13
    //   178: astore 10
    //   180: aload 13
    //   182: astore_2
    //   183: goto -83 -> 100
    //   186: aload 8
    //   188: invokeinterface 117 1 0
    //   193: aload 6
    //   195: areturn
    //   196: astore_3
    //   197: aconst_null
    //   198: areturn
    //   199: astore 7
    //   201: aload 6
    //   203: areturn
    //   204: aconst_null
    //   205: areturn
    //   206: astore 12
    //   208: aload_2
    //   209: astore 13
    //   211: goto -69 -> 142
    //
    // Exception table:
    //   from	to	target	type
    //   2	18	196	java/lang/Exception
    //   23	33	196	java/lang/Exception
    //   36	72	196	java/lang/Exception
    //   72	97	199	java/lang/Exception
    //   100	128	199	java/lang/Exception
    //   152	166	199	java/lang/Exception
    //   166	176	199	java/lang/Exception
    //   186	193	199	java/lang/Exception
    //   128	142	206	java/lang/Exception
  }

  private boolean deleteChannelNodes(DataCommand paramDataCommand)
  {
    int i = ((Integer)paramDataCommand.getParam().get("catid")).intValue();
    try
    {
      String str = "delete from channelNodes" + " where catid = '" + i + "'";
      DBManager.getInstance().getWritableDB("channelNodesv6").execSQL(str);
      return true;
    }
    catch (Exception localException)
    {
    }
    return false;
  }

  private DataToken doAcquireCommand(DataCommand paramDataCommand)
  {
    DataToken localDataToken = new DataToken();
    localDataToken.setDataInfo(paramDataCommand);
    localDataToken.setData(new Result(true, acquireChannelNodes(paramDataCommand)));
    return localDataToken;
  }

  private DataToken doDeleteCommand(DataCommand paramDataCommand)
  {
    DataToken localDataToken = new DataToken();
    localDataToken.setDataInfo(paramDataCommand);
    localDataToken.setData(new Result(true, Boolean.valueOf(deleteChannelNodes(paramDataCommand))));
    return localDataToken;
  }

  private DataToken doGetCommand(DataCommand paramDataCommand)
  {
    DataToken localDataToken = new DataToken();
    localDataToken.setDataInfo(paramDataCommand);
    localDataToken.setData(new Result(true, getChannelNodes(paramDataCommand)));
    return localDataToken;
  }

  private DataToken doInsertCommand(DataCommand paramDataCommand)
  {
    DataToken localDataToken = new DataToken();
    localDataToken.setDataInfo(paramDataCommand);
    localDataToken.setData(new Result(true, Boolean.valueOf(insertChannelNodes(paramDataCommand))));
    return localDataToken;
  }

  private DataToken doUpdateChannelCommand(DataCommand paramDataCommand)
  {
    DataToken localDataToken = new DataToken();
    localDataToken.setDataInfo(paramDataCommand);
    localDataToken.setData(new Result(true, Boolean.valueOf(updateChannelNode(paramDataCommand))));
    return localDataToken;
  }

  private DataToken doUpdateCommand(DataCommand paramDataCommand)
  {
    DataToken localDataToken = new DataToken();
    localDataToken.setDataInfo(paramDataCommand);
    localDataToken.setData(new Result(true, Boolean.valueOf(updateChannelNodes(paramDataCommand))));
    return localDataToken;
  }

  // ERROR //
  private ChannelNode getChannelNodes(DataCommand paramDataCommand)
  {
    // Byte code:
    //   0: aload_1
    //   1: invokevirtual 22	fm/qingting/framework/data/DataCommand:getParam	()Ljava/util/Map;
    //   4: astore_3
    //   5: aload_3
    //   6: ldc 188
    //   8: invokeinterface 30 2 0
    //   13: checkcast 123	java/lang/Integer
    //   16: invokevirtual 127	java/lang/Integer:intValue	()I
    //   19: istore 4
    //   21: aload_3
    //   22: ldc 190
    //   24: invokeinterface 30 2 0
    //   29: checkcast 123	java/lang/Integer
    //   32: invokevirtual 127	java/lang/Integer:intValue	()I
    //   35: istore 5
    //   37: new 40	java/lang/StringBuilder
    //   40: dup
    //   41: invokespecial 41	java/lang/StringBuilder:<init>	()V
    //   44: ldc 192
    //   46: invokevirtual 47	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   49: iload 4
    //   51: invokevirtual 134	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
    //   54: ldc 194
    //   56: invokevirtual 47	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   59: iload 5
    //   61: invokevirtual 134	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
    //   64: ldc 49
    //   66: invokevirtual 47	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   69: invokevirtual 53	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   72: astore 6
    //   74: invokestatic 62	fm/qingting/qtradio/data/DBManager:getInstance	()Lfm/qingting/qtradio/data/DBManager;
    //   77: ldc 64
    //   79: invokevirtual 68	fm/qingting/qtradio/data/DBManager:getReadableDB	(Ljava/lang/String;)Landroid/database/sqlite/SQLiteDatabase;
    //   82: aload 6
    //   84: aconst_null
    //   85: invokevirtual 74	android/database/sqlite/SQLiteDatabase:rawQuery	(Ljava/lang/String;[Ljava/lang/String;)Landroid/database/Cursor;
    //   88: astore 7
    //   90: new 76	com/google/gson/Gson
    //   93: dup
    //   94: invokespecial 77	com/google/gson/Gson:<init>	()V
    //   97: astore 8
    //   99: aload 7
    //   101: invokeinterface 83 1 0
    //   106: ifeq +53 -> 159
    //   109: aload 7
    //   111: aload 7
    //   113: ldc 85
    //   115: invokeinterface 89 2 0
    //   120: invokeinterface 93 2 0
    //   125: astore 10
    //   127: aload 8
    //   129: aload 10
    //   131: ldc 95
    //   133: invokevirtual 99	com/google/gson/Gson:fromJson	(Ljava/lang/String;Ljava/lang/Class;)Ljava/lang/Object;
    //   136: checkcast 95	fm/qingting/qtradio/model/ChannelNode
    //   139: astore 9
    //   141: aload 7
    //   143: invokeinterface 117 1 0
    //   148: aload 9
    //   150: areturn
    //   151: astore_2
    //   152: aconst_null
    //   153: areturn
    //   154: astore 11
    //   156: goto -57 -> 99
    //   159: aconst_null
    //   160: astore 9
    //   162: goto -21 -> 141
    //
    // Exception table:
    //   from	to	target	type
    //   0	99	151	java/lang/Exception
    //   99	127	151	java/lang/Exception
    //   141	148	151	java/lang/Exception
    //   127	141	154	java/lang/Exception
  }

  public static ChannelNodesDS getInstance()
  {
    if (instance == null)
      instance = new ChannelNodesDS();
    return instance;
  }

  private boolean insertChannelNodes(DataCommand paramDataCommand)
  {
    List localList = (List)paramDataCommand.getParam().get("nodes");
    if ((localList == null) || (localList.size() == 0))
      return false;
    SQLiteDatabase localSQLiteDatabase = DBManager.getInstance().getWritableDB("channelNodesv6");
    Gson localGson = new Gson();
    try
    {
      localSQLiteDatabase.beginTransaction();
      for (int i = 0; i < localList.size(); i++)
      {
        ChannelNode localChannelNode = (ChannelNode)localList.get(i);
        String str = localGson.toJson(localChannelNode);
        Object[] arrayOfObject = new Object[5];
        arrayOfObject[0] = Integer.valueOf(localChannelNode.categoryId);
        arrayOfObject[1] = Integer.valueOf(localChannelNode.channelType);
        arrayOfObject[2] = Integer.valueOf(localChannelNode.channelId);
        arrayOfObject[3] = str;
        arrayOfObject[4] = null;
        localSQLiteDatabase.execSQL("insert into channelNodes(catid,type,channelid,channelNode, key) values(?, ?, ?, ?, ?)", arrayOfObject);
      }
      localSQLiteDatabase.setTransactionSuccessful();
      localSQLiteDatabase.endTransaction();
      return true;
    }
    catch (Exception localException)
    {
      while (true)
        localException.printStackTrace();
    }
  }

  private boolean updateChannelNode(DataCommand paramDataCommand)
  {
    Map localMap = paramDataCommand.getParam();
    ChannelNode localChannelNode = (ChannelNode)localMap.get("node");
    int i = ((Integer)localMap.get("channelid")).intValue();
    if ((localChannelNode == null) || (i == 0))
      return false;
    String str1 = "delete from channelNodes" + " where channelid = '" + i + "'";
    SQLiteDatabase localSQLiteDatabase = DBManager.getInstance().getWritableDB("channelNodesv6");
    try
    {
      localSQLiteDatabase.beginTransaction();
      localSQLiteDatabase.execSQL(str1);
      String str2 = new Gson().toJson(localChannelNode);
      Object[] arrayOfObject = new Object[5];
      arrayOfObject[0] = Integer.valueOf(localChannelNode.categoryId);
      arrayOfObject[1] = Integer.valueOf(localChannelNode.channelType);
      arrayOfObject[2] = Integer.valueOf(localChannelNode.channelId);
      arrayOfObject[3] = str2;
      arrayOfObject[4] = null;
      localSQLiteDatabase.execSQL("insert into channelNodes(catid,type,channelid,channelNode,key) values(?, ?, ?, ?, ?)", arrayOfObject);
      localSQLiteDatabase.setTransactionSuccessful();
      localSQLiteDatabase.endTransaction();
      return true;
    }
    catch (Exception localException)
    {
      while (true)
        localException.printStackTrace();
    }
  }

  private boolean updateChannelNodes(DataCommand paramDataCommand)
  {
    Map localMap = paramDataCommand.getParam();
    List localList = (List)localMap.get("nodes");
    String str1 = (String)localMap.get("key");
    if ((localList == null) || (localList.size() == 0) || (str1 == null))
      return false;
    String str2 = "delete from channelNodes" + " where key = '" + str1 + "'";
    SQLiteDatabase localSQLiteDatabase = DBManager.getInstance().getWritableDB("channelNodesv6");
    try
    {
      localSQLiteDatabase.beginTransaction();
      localSQLiteDatabase.execSQL(str2);
      Gson localGson = new Gson();
      for (int i = 0; i < localList.size(); i++)
      {
        ChannelNode localChannelNode = (ChannelNode)localList.get(i);
        String str3 = localGson.toJson(localChannelNode);
        Object[] arrayOfObject = new Object[5];
        arrayOfObject[0] = Integer.valueOf(localChannelNode.categoryId);
        arrayOfObject[1] = Integer.valueOf(localChannelNode.channelType);
        arrayOfObject[2] = Integer.valueOf(localChannelNode.channelId);
        arrayOfObject[3] = str3;
        arrayOfObject[4] = str1;
        localSQLiteDatabase.execSQL("insert into channelNodes(catid,type,channelid,channelNode,key) values(?, ?, ?, ?, ?)", arrayOfObject);
      }
      localSQLiteDatabase.setTransactionSuccessful();
      localSQLiteDatabase.endTransaction();
      return true;
    }
    catch (Exception localException)
    {
      while (true)
        localException.printStackTrace();
    }
  }

  public void addParser(IDataParser paramIDataParser)
  {
  }

  public String dataSourceName()
  {
    return "ChannelNodesDS";
  }

  public IDataToken doCommand(DataCommand paramDataCommand, IDataRecvHandler paramIDataRecvHandler)
  {
    String str = paramDataCommand.getCurrentCommand();
    if (str.equalsIgnoreCase("INSERTDB_CHANNEL_NODE"))
      return doInsertCommand(paramDataCommand);
    if (str.equalsIgnoreCase("GETDB_CHANNEL_NODE"))
      return doAcquireCommand(paramDataCommand);
    if (str.equalsIgnoreCase("DELETEDB_CHANNEL_NODE"))
      return doDeleteCommand(paramDataCommand);
    if (str.equalsIgnoreCase("UPDATEDB_CHANNEL_NODE"))
      return doUpdateCommand(paramDataCommand);
    if (str.equalsIgnoreCase("UPDATEDB_A_CHANNEL_NODE"))
      return doUpdateChannelCommand(paramDataCommand);
    if (str.equalsIgnoreCase("GETDB_CHANNEL_INFO"))
      return doGetCommand(paramDataCommand);
    return null;
  }

  public boolean isSynchronous(String paramString, Map<String, Object> paramMap)
  {
    return true;
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.qtradio.data.ChannelNodesDS
 * JD-Core Version:    0.6.2
 */