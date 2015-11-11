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
import fm.qingting.qtradio.model.Node;
import java.util.List;
import java.util.Map;

public class PullMsgConfigDS
  implements IDataSource
{
  private static PullMsgConfigDS instance;

  // ERROR //
  private List<Node> acquirePullConfig(DataCommand paramDataCommand)
  {
    // Byte code:
    //   0: new 18	java/util/ArrayList
    //   3: dup
    //   4: invokespecial 19	java/util/ArrayList:<init>	()V
    //   7: astore_2
    //   8: invokestatic 25	fm/qingting/qtradio/data/DBManager:getInstance	()Lfm/qingting/qtradio/data/DBManager;
    //   11: ldc 27
    //   13: invokevirtual 31	fm/qingting/qtradio/data/DBManager:getReadableDB	(Ljava/lang/String;)Landroid/database/sqlite/SQLiteDatabase;
    //   16: ldc 33
    //   18: aconst_null
    //   19: invokevirtual 39	android/database/sqlite/SQLiteDatabase:rawQuery	(Ljava/lang/String;[Ljava/lang/String;)Landroid/database/Cursor;
    //   22: astore 4
    //   24: new 41	com/google/gson/Gson
    //   27: dup
    //   28: invokespecial 42	com/google/gson/Gson:<init>	()V
    //   31: astore 5
    //   33: aconst_null
    //   34: astore 6
    //   36: aload 4
    //   38: invokeinterface 48 1 0
    //   43: ifeq +151 -> 194
    //   46: aload 4
    //   48: aload 4
    //   50: ldc 50
    //   52: invokeinterface 54 2 0
    //   57: invokeinterface 58 2 0
    //   62: astore 7
    //   64: aload 4
    //   66: aload 4
    //   68: ldc 60
    //   70: invokeinterface 54 2 0
    //   75: invokeinterface 58 2 0
    //   80: astore 8
    //   82: aload 7
    //   84: ldc 62
    //   86: invokevirtual 68	java/lang/String:equalsIgnoreCase	(Ljava/lang/String;)Z
    //   89: ifeq +41 -> 130
    //   92: aload 5
    //   94: aload 8
    //   96: ldc 70
    //   98: invokevirtual 74	com/google/gson/Gson:fromJson	(Ljava/lang/String;Ljava/lang/Class;)Ljava/lang/Object;
    //   101: checkcast 76	fm/qingting/qtradio/model/Node
    //   104: astore 11
    //   106: aload 11
    //   108: astore 6
    //   110: aload 6
    //   112: ifnull -76 -> 36
    //   115: aload_2
    //   116: aload 6
    //   118: invokeinterface 82 2 0
    //   123: pop
    //   124: goto -88 -> 36
    //   127: astore_3
    //   128: aload_2
    //   129: areturn
    //   130: aload 7
    //   132: ldc 84
    //   134: invokevirtual 68	java/lang/String:equalsIgnoreCase	(Ljava/lang/String;)Z
    //   137: ifeq +20 -> 157
    //   140: aload 5
    //   142: aload 8
    //   144: ldc 86
    //   146: invokevirtual 74	com/google/gson/Gson:fromJson	(Ljava/lang/String;Ljava/lang/Class;)Ljava/lang/Object;
    //   149: checkcast 76	fm/qingting/qtradio/model/Node
    //   152: astore 11
    //   154: goto -48 -> 106
    //   157: aload 7
    //   159: ldc 88
    //   161: invokevirtual 68	java/lang/String:equalsIgnoreCase	(Ljava/lang/String;)Z
    //   164: ifeq +43 -> 207
    //   167: aload 5
    //   169: aload 8
    //   171: ldc 90
    //   173: invokevirtual 74	com/google/gson/Gson:fromJson	(Ljava/lang/String;Ljava/lang/Class;)Ljava/lang/Object;
    //   176: checkcast 76	fm/qingting/qtradio/model/Node
    //   179: astore 11
    //   181: goto -75 -> 106
    //   184: astore 9
    //   186: aload 9
    //   188: invokevirtual 93	java/lang/Exception:printStackTrace	()V
    //   191: goto -81 -> 110
    //   194: aload 4
    //   196: invokeinterface 96 1 0
    //   201: aload_2
    //   202: areturn
    //   203: astore 12
    //   205: aconst_null
    //   206: areturn
    //   207: aload 6
    //   209: astore 11
    //   211: goto -105 -> 106
    //
    // Exception table:
    //   from	to	target	type
    //   8	33	127	java/lang/Exception
    //   36	82	127	java/lang/Exception
    //   115	124	127	java/lang/Exception
    //   186	191	127	java/lang/Exception
    //   194	201	127	java/lang/Exception
    //   82	106	184	java/lang/Exception
    //   130	154	184	java/lang/Exception
    //   157	181	184	java/lang/Exception
    //   0	8	203	java/lang/Exception
  }

  private boolean deletePullConfig(DataCommand paramDataCommand)
  {
    try
    {
      DBManager.getInstance().getWritableDB("pullMsgConfig").execSQL("delete from pullMsgConfig");
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
    localDataToken.setData(new Result(true, acquirePullConfig(paramDataCommand)));
    return localDataToken;
  }

  private DataToken doDeleteCommand(DataCommand paramDataCommand)
  {
    DataToken localDataToken = new DataToken();
    localDataToken.setDataInfo(paramDataCommand);
    localDataToken.setData(new Result(true, Boolean.valueOf(deletePullConfig(paramDataCommand))));
    return localDataToken;
  }

  private DataToken doInsertCommand(DataCommand paramDataCommand)
  {
    DataToken localDataToken = new DataToken();
    localDataToken.setDataInfo(paramDataCommand);
    localDataToken.setData(new Result(true, Boolean.valueOf(insertPullConfig(paramDataCommand))));
    return localDataToken;
  }

  private DataToken doUpdateCommand(DataCommand paramDataCommand)
  {
    DataToken localDataToken = new DataToken();
    localDataToken.setDataInfo(paramDataCommand);
    localDataToken.setData(new Result(true, Boolean.valueOf(updatePullConfig(paramDataCommand))));
    return localDataToken;
  }

  public static PullMsgConfigDS getInstance()
  {
    if (instance == null)
      instance = new PullMsgConfigDS();
    return instance;
  }

  private boolean insertPullConfig(DataCommand paramDataCommand)
  {
    List localList = (List)paramDataCommand.getParam().get("nodes");
    if ((localList == null) || (localList.size() == 0))
      return false;
    try
    {
      SQLiteDatabase localSQLiteDatabase = DBManager.getInstance().getWritableDB("pullMsgConfig");
      localSQLiteDatabase.beginTransaction();
      Gson localGson = new Gson();
      for (int i = 0; i < localList.size(); i++)
      {
        Node localNode = (Node)localList.get(i);
        String str = localGson.toJson(localNode);
        Object[] arrayOfObject = new Object[3];
        arrayOfObject[0] = String.valueOf(i);
        arrayOfObject[1] = localNode.nodeName;
        arrayOfObject[2] = str;
        localSQLiteDatabase.execSQL("insert into pullMsgConfig(id,nodeName,pullNode) values(?,?,?)", arrayOfObject);
      }
      localSQLiteDatabase.setTransactionSuccessful();
      localSQLiteDatabase.endTransaction();
      return true;
    }
    catch (Exception localException)
    {
    }
    return false;
  }

  private boolean updatePullConfig(DataCommand paramDataCommand)
  {
    List localList = (List)paramDataCommand.getParam().get("nodes");
    if (localList == null)
      return false;
    try
    {
      SQLiteDatabase localSQLiteDatabase = DBManager.getInstance().getWritableDB("pullMsgConfig");
      localSQLiteDatabase.beginTransaction();
      localSQLiteDatabase.execSQL("delete from pullMsgConfig");
      Gson localGson = new Gson();
      for (int i = 0; i < localList.size(); i++)
      {
        Node localNode = (Node)localList.get(i);
        String str = localGson.toJson(localNode);
        Object[] arrayOfObject = new Object[3];
        arrayOfObject[0] = String.valueOf(i);
        arrayOfObject[1] = localNode.nodeName;
        arrayOfObject[2] = str;
        localSQLiteDatabase.execSQL("insert into pullMsgConfig(id,nodeName,pullNode) values(?,?,?)", arrayOfObject);
      }
      localSQLiteDatabase.setTransactionSuccessful();
      localSQLiteDatabase.endTransaction();
      return true;
    }
    catch (Exception localException)
    {
    }
    return false;
  }

  public void addParser(IDataParser paramIDataParser)
  {
  }

  public String dataSourceName()
  {
    return "PullMsgConfigDS";
  }

  public IDataToken doCommand(DataCommand paramDataCommand, IDataRecvHandler paramIDataRecvHandler)
  {
    String str = paramDataCommand.getCurrentCommand();
    if (str.equalsIgnoreCase("insertdb_pull_config"))
      return doInsertCommand(paramDataCommand);
    if (str.equalsIgnoreCase("getdb_pull_config"))
      return doAcquireCommand(paramDataCommand);
    if (str.equalsIgnoreCase("deletedb_pull_config"))
      return doDeleteCommand(paramDataCommand);
    if (str.equalsIgnoreCase("updatedb_pull_config"))
      return doUpdateCommand(paramDataCommand);
    return null;
  }

  public boolean isSynchronous(String paramString, Map<String, Object> paramMap)
  {
    return true;
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.qtradio.data.PullMsgConfigDS
 * JD-Core Version:    0.6.2
 */