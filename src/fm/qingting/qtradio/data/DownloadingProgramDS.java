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

public class DownloadingProgramDS
  implements IDataSource
{
  private static DownloadingProgramDS instance;

  // ERROR //
  private List<Node> acquireProgramNodes(DataCommand paramDataCommand)
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
    //   43: ifeq +67 -> 110
    //   46: aload 4
    //   48: aload 4
    //   50: ldc 50
    //   52: invokeinterface 54 2 0
    //   57: invokeinterface 58 2 0
    //   62: astore 7
    //   64: aload 5
    //   66: aload 7
    //   68: ldc 60
    //   70: invokevirtual 64	com/google/gson/Gson:fromJson	(Ljava/lang/String;Ljava/lang/Class;)Ljava/lang/Object;
    //   73: checkcast 60	fm/qingting/qtradio/model/ProgramNode
    //   76: astore 10
    //   78: aload 10
    //   80: astore 6
    //   82: aload 6
    //   84: ifnull -48 -> 36
    //   87: aload 6
    //   89: checkcast 60	fm/qingting/qtradio/model/ProgramNode
    //   92: getfield 68	fm/qingting/qtradio/model/ProgramNode:downloadInfo	Lfm/qingting/qtradio/model/Download;
    //   95: ifnull -59 -> 36
    //   98: aload_2
    //   99: aload 6
    //   101: invokeinterface 74 2 0
    //   106: pop
    //   107: goto -71 -> 36
    //   110: aload 4
    //   112: invokeinterface 77 1 0
    //   117: aload_2
    //   118: areturn
    //   119: astore 11
    //   121: aconst_null
    //   122: areturn
    //   123: astore 8
    //   125: goto -43 -> 82
    //   128: astore_3
    //   129: aload_2
    //   130: areturn
    //
    // Exception table:
    //   from	to	target	type
    //   0	8	119	java/lang/Exception
    //   64	78	123	java/lang/Exception
    //   8	33	128	java/lang/Exception
    //   36	64	128	java/lang/Exception
    //   87	107	128	java/lang/Exception
    //   110	117	128	java/lang/Exception
  }

  private boolean deleteProgramNodes(DataCommand paramDataCommand)
  {
    try
    {
      DBManager.getInstance().getWritableDB("downloadingprogramNodes").execSQL("delete from downloadingNodes");
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

  public static DownloadingProgramDS getInstance()
  {
    if (instance == null)
      instance = new DownloadingProgramDS();
    return instance;
  }

  private boolean insertProgramNodes(DataCommand paramDataCommand)
  {
    Node localNode = (Node)paramDataCommand.getParam().get("node");
    if (localNode == null)
      return false;
    SQLiteDatabase localSQLiteDatabase = DBManager.getInstance().getWritableDB("downloadingprogramNodes");
    try
    {
      localSQLiteDatabase.beginTransaction();
      String str = new Gson().toJson(localNode);
      int i = ((ProgramNode)localNode).id;
      localSQLiteDatabase.execSQL("delete from downloadingNodes where id = '" + i + "'");
      Object[] arrayOfObject = new Object[2];
      arrayOfObject[0] = Integer.valueOf(i);
      arrayOfObject[1] = str;
      localSQLiteDatabase.execSQL("insert into downloadingNodes(id,programNode) values(?,?)", arrayOfObject);
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
    List localList = (List)paramDataCommand.getParam().get("nodes");
    SQLiteDatabase localSQLiteDatabase = DBManager.getInstance().getWritableDB("downloadingprogramNodes");
    Gson localGson = new Gson();
    try
    {
      localSQLiteDatabase.beginTransaction();
      localSQLiteDatabase.execSQL("delete from downloadingNodes");
      if (localList != null)
        for (int i = 0; i < localList.size(); i++)
        {
          Node localNode = (Node)localList.get(i);
          String str = localGson.toJson(localNode);
          int j = ((ProgramNode)localNode).id;
          Object[] arrayOfObject = new Object[2];
          arrayOfObject[0] = Integer.valueOf(j);
          arrayOfObject[1] = str;
          localSQLiteDatabase.execSQL("insert into downloadingNodes(id,programNode) values(?,?)", arrayOfObject);
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
    return "DownloadingProgramDS";
  }

  public IDataToken doCommand(DataCommand paramDataCommand, IDataRecvHandler paramIDataRecvHandler)
  {
    String str = paramDataCommand.getCurrentCommand();
    if (str.equalsIgnoreCase("insertdb_downloading_program_node"))
      return doInsertCommand(paramDataCommand);
    if (str.equalsIgnoreCase("getdb_downloading_program_node"))
      return doAcquireCommand(paramDataCommand);
    if (str.equalsIgnoreCase("deletedb_downloading_program_node"))
      return doDeleteCommand(paramDataCommand);
    if (str.equalsIgnoreCase("updatedb_downloading_program_node"))
      return doUpdateCommand(paramDataCommand);
    return null;
  }

  public boolean isSynchronous(String paramString, Map<String, Object> paramMap)
  {
    return true;
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.qtradio.data.DownloadingProgramDS
 * JD-Core Version:    0.6.2
 */