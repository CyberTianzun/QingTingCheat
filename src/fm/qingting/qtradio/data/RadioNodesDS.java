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

public class RadioNodesDS
  implements IDataSource
{
  private static RadioNodesDS instance;

  // ERROR //
  private List<Node> acquireRadioNodes(DataCommand paramDataCommand)
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
    //   43: ifeq +52 -> 95
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
    //   73: checkcast 66	fm/qingting/qtradio/model/Node
    //   76: astore 9
    //   78: aload 9
    //   80: ifnull +31 -> 111
    //   83: aload_2
    //   84: aload 9
    //   86: invokeinterface 72 2 0
    //   91: pop
    //   92: goto +19 -> 111
    //   95: aload 4
    //   97: invokeinterface 75 1 0
    //   102: aload_2
    //   103: areturn
    //   104: astore 11
    //   106: aconst_null
    //   107: areturn
    //   108: astore_3
    //   109: aload_2
    //   110: areturn
    //   111: aload 9
    //   113: astore 6
    //   115: goto -79 -> 36
    //   118: astore 8
    //   120: aload 6
    //   122: astore 9
    //   124: goto -46 -> 78
    //
    // Exception table:
    //   from	to	target	type
    //   0	8	104	java/lang/Exception
    //   8	33	108	java/lang/Exception
    //   36	64	108	java/lang/Exception
    //   83	92	108	java/lang/Exception
    //   95	102	108	java/lang/Exception
    //   64	78	118	java/lang/Exception
  }

  private boolean deleteRadioNodes(DataCommand paramDataCommand)
  {
    try
    {
      DBManager.getInstance().getWritableDB("radioNodes").execSQL("delete from radioNodes");
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
    localDataToken.setData(new Result(true, acquireRadioNodes(paramDataCommand)));
    return localDataToken;
  }

  private DataToken doDeleteCommand(DataCommand paramDataCommand)
  {
    DataToken localDataToken = new DataToken();
    localDataToken.setDataInfo(paramDataCommand);
    localDataToken.setData(new Result(true, Boolean.valueOf(deleteRadioNodes(paramDataCommand))));
    return localDataToken;
  }

  private DataToken doInsertCommand(DataCommand paramDataCommand)
  {
    DataToken localDataToken = new DataToken();
    localDataToken.setDataInfo(paramDataCommand);
    localDataToken.setData(new Result(true, Boolean.valueOf(insertRadioNodes(paramDataCommand))));
    return localDataToken;
  }

  public static RadioNodesDS getInstance()
  {
    if (instance == null)
      instance = new RadioNodesDS();
    return instance;
  }

  private boolean insertRadioNodes(DataCommand paramDataCommand)
  {
    List localList = (List)paramDataCommand.getParam().get("nodes");
    if ((localList == null) || (localList.size() == 0))
      return false;
    try
    {
      SQLiteDatabase localSQLiteDatabase = DBManager.getInstance().getWritableDB("radioNodes");
      localSQLiteDatabase.beginTransaction();
      Gson localGson = new Gson();
      int i = 0;
      int j = 0;
      while (i < localList.size())
      {
        Node localNode = (Node)localList.get(i);
        j++;
        String str = localGson.toJson(localNode);
        Object[] arrayOfObject = new Object[2];
        arrayOfObject[0] = String.valueOf(j);
        arrayOfObject[1] = str;
        localSQLiteDatabase.execSQL("insert into radioNodes(id,radioChannelNode) values(?, ?)", arrayOfObject);
        i++;
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
    return "RadioNodesDS";
  }

  public IDataToken doCommand(DataCommand paramDataCommand, IDataRecvHandler paramIDataRecvHandler)
  {
    String str = paramDataCommand.getCurrentCommand();
    if (str.equalsIgnoreCase("insertdb_radio_node"))
      return doInsertCommand(paramDataCommand);
    if (str.equalsIgnoreCase("getdb_radio_node"))
      return doAcquireCommand(paramDataCommand);
    if (str.equalsIgnoreCase("deletedb_radio_node"))
      return doDeleteCommand(paramDataCommand);
    return null;
  }

  public boolean isSynchronous(String paramString, Map<String, Object> paramMap)
  {
    return true;
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.qtradio.data.RadioNodesDS
 * JD-Core Version:    0.6.2
 */