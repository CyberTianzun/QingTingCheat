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
import fm.qingting.qtradio.model.ProgramNode;
import java.util.List;
import java.util.Map;

public class ProgramNodesDS
  implements IDataSource
{
  private static ProgramNodesDS instance;

  // ERROR //
  private List<Node> acquireProgramNodes(DataCommand paramDataCommand)
  {
    // Byte code:
    //   0: aconst_null
    //   1: astore_2
    //   2: aload_1
    //   3: invokevirtual 22	fm/qingting/framework/data/DataCommand:getParam	()Ljava/util/Map;
    //   6: ldc 24
    //   8: invokeinterface 30 2 0
    //   13: checkcast 32	java/lang/Integer
    //   16: astore 4
    //   18: new 34	java/lang/StringBuilder
    //   21: dup
    //   22: invokespecial 35	java/lang/StringBuilder:<init>	()V
    //   25: ldc 37
    //   27: invokevirtual 41	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   30: aload 4
    //   32: invokevirtual 44	java/lang/StringBuilder:append	(Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   35: ldc 46
    //   37: invokevirtual 41	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   40: invokevirtual 50	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   43: astore 5
    //   45: new 52	java/util/ArrayList
    //   48: dup
    //   49: invokespecial 53	java/util/ArrayList:<init>	()V
    //   52: astore 6
    //   54: invokestatic 59	fm/qingting/qtradio/data/DBManager:getInstance	()Lfm/qingting/qtradio/data/DBManager;
    //   57: ldc 61
    //   59: invokevirtual 65	fm/qingting/qtradio/data/DBManager:getReadableDB	(Ljava/lang/String;)Landroid/database/sqlite/SQLiteDatabase;
    //   62: aload 5
    //   64: aconst_null
    //   65: invokevirtual 71	android/database/sqlite/SQLiteDatabase:rawQuery	(Ljava/lang/String;[Ljava/lang/String;)Landroid/database/Cursor;
    //   68: astore 8
    //   70: new 73	com/google/gson/Gson
    //   73: dup
    //   74: invokespecial 74	com/google/gson/Gson:<init>	()V
    //   77: astore 9
    //   79: aload 8
    //   81: invokeinterface 80 1 0
    //   86: ifeq +72 -> 158
    //   89: aload 8
    //   91: aload 8
    //   93: ldc 82
    //   95: invokeinterface 86 2 0
    //   100: invokeinterface 90 2 0
    //   105: astore 10
    //   107: aload 9
    //   109: aload 10
    //   111: ldc 92
    //   113: invokevirtual 96	com/google/gson/Gson:fromJson	(Ljava/lang/String;Ljava/lang/Class;)Ljava/lang/Object;
    //   116: checkcast 98	fm/qingting/qtradio/model/Node
    //   119: astore 12
    //   121: aload_2
    //   122: ifnull +20 -> 142
    //   125: aload 12
    //   127: ifnull +15 -> 142
    //   130: aload 12
    //   132: aload_2
    //   133: putfield 102	fm/qingting/qtradio/model/Node:prevSibling	Lfm/qingting/qtradio/model/Node;
    //   136: aload_2
    //   137: aload 12
    //   139: putfield 105	fm/qingting/qtradio/model/Node:nextSibling	Lfm/qingting/qtradio/model/Node;
    //   142: aload 6
    //   144: aload 12
    //   146: invokeinterface 111 2 0
    //   151: pop
    //   152: aload 12
    //   154: astore_2
    //   155: goto -76 -> 79
    //   158: aload 8
    //   160: invokeinterface 114 1 0
    //   165: aload 6
    //   167: areturn
    //   168: astore_3
    //   169: aconst_null
    //   170: areturn
    //   171: astore 7
    //   173: aload 6
    //   175: areturn
    //   176: astore 11
    //   178: aload_2
    //   179: astore 12
    //   181: goto -29 -> 152
    //   184: astore 13
    //   186: goto -34 -> 152
    //
    // Exception table:
    //   from	to	target	type
    //   2	54	168	java/lang/Exception
    //   54	79	171	java/lang/Exception
    //   79	107	171	java/lang/Exception
    //   158	165	171	java/lang/Exception
    //   107	121	176	java/lang/Exception
    //   130	142	176	java/lang/Exception
    //   142	152	184	java/lang/Exception
  }

  private boolean deleteProgramNodes(DataCommand paramDataCommand)
  {
    int i = ((Integer)paramDataCommand.getParam().get("cid")).intValue();
    try
    {
      String str = "delete from programNodes" + " where cid = '" + i + "'";
      DBManager.getInstance().getWritableDB("programNodes").execSQL(str);
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
    localDataToken.setData(new Result(true, acquireProgramNodes(paramDataCommand)));
    return localDataToken;
  }

  private DataToken doDeleteCommand(DataCommand paramDataCommand)
  {
    DataToken localDataToken = new DataToken();
    localDataToken.setDataInfo(paramDataCommand);
    localDataToken.setData(new Result(true, Boolean.valueOf(deleteProgramNodes(paramDataCommand))));
    return localDataToken;
  }

  private DataToken doInsertCommand(DataCommand paramDataCommand)
  {
    DataToken localDataToken = new DataToken();
    localDataToken.setDataInfo(paramDataCommand);
    localDataToken.setData(new Result(true, Boolean.valueOf(insertProgramNodes(paramDataCommand))));
    return localDataToken;
  }

  private DataToken doUpdateCommand(DataCommand paramDataCommand)
  {
    DataToken localDataToken = new DataToken();
    localDataToken.setDataInfo(paramDataCommand);
    localDataToken.setData(new Result(true, Boolean.valueOf(updateProgramNodes(paramDataCommand))));
    return localDataToken;
  }

  public static ProgramNodesDS getInstance()
  {
    if (instance == null)
      instance = new ProgramNodesDS();
    return instance;
  }

  private boolean insertProgramNodes(DataCommand paramDataCommand)
  {
    int i = 0;
    Map localMap = paramDataCommand.getParam();
    List localList = (List)localMap.get("nodes");
    int j = ((Integer)localMap.get("cid")).intValue();
    int k = ((Integer)localMap.get("dw")).intValue();
    if ((localList == null) || (localList.size() == 0))
      return false;
    SQLiteDatabase localSQLiteDatabase = DBManager.getInstance().getWritableDB("programNodes");
    try
    {
      localSQLiteDatabase.beginTransaction();
      Gson localGson = new Gson();
      while (i < localList.size())
      {
        Node localNode = (Node)localList.get(i);
        String str = localGson.toJson(localNode);
        Object[] arrayOfObject = new Object[4];
        arrayOfObject[0] = Integer.valueOf(j);
        arrayOfObject[1] = Integer.valueOf(((ProgramNode)localNode).uniqueId);
        arrayOfObject[2] = Integer.valueOf(k);
        arrayOfObject[3] = str;
        localSQLiteDatabase.execSQL("insert into programNodes(cid,pid,dw,programNode) values(?, ?, ?, ?)", arrayOfObject);
        i++;
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

  private boolean updateProgramNodes(DataCommand paramDataCommand)
  {
    Map localMap = paramDataCommand.getParam();
    List localList = (List)localMap.get("nodes");
    int i = ((Integer)localMap.get("id")).intValue();
    int j = ((Integer)localMap.get("size")).intValue();
    SQLiteDatabase localSQLiteDatabase = DBManager.getInstance().getWritableDB("programNodes");
    try
    {
      localSQLiteDatabase.beginTransaction();
      localSQLiteDatabase.execSQL("delete from programNodes" + " where cid = '" + i + "'");
      Gson localGson = new Gson();
      for (int k = 0; (k < localList.size()) && (k < j); k++)
      {
        Node localNode = (Node)localList.get(k);
        String str = localGson.toJson(localNode);
        Object[] arrayOfObject = new Object[4];
        arrayOfObject[0] = Integer.valueOf(i);
        arrayOfObject[1] = Integer.valueOf(((ProgramNode)localNode).uniqueId);
        arrayOfObject[2] = Integer.valueOf(0);
        arrayOfObject[3] = str;
        localSQLiteDatabase.execSQL("insert into programNodes(cid,pid,dw,programNode) values(?, ?, ?, ?)", arrayOfObject);
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
    return "ProgramNodesDS";
  }

  public IDataToken doCommand(DataCommand paramDataCommand, IDataRecvHandler paramIDataRecvHandler)
  {
    String str = paramDataCommand.getCurrentCommand();
    if (str.equalsIgnoreCase("insertdb_program_node"))
      return doInsertCommand(paramDataCommand);
    if (str.equalsIgnoreCase("getdb_program_node"))
      return doAcquireCommand(paramDataCommand);
    if (str.equalsIgnoreCase("deletedb_program_node"))
      return doDeleteCommand(paramDataCommand);
    if (str.equalsIgnoreCase("updatedb_program_node"))
      return doUpdateCommand(paramDataCommand);
    return null;
  }

  public boolean isSynchronous(String paramString, Map<String, Object> paramMap)
  {
    return true;
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.qtradio.data.ProgramNodesDS
 * JD-Core Version:    0.6.2
 */