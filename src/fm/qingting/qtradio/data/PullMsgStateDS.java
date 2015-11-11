package fm.qingting.qtradio.data;

import android.database.sqlite.SQLiteDatabase;
import fm.qingting.framework.data.DataCommand;
import fm.qingting.framework.data.DataToken;
import fm.qingting.framework.data.IDataParser;
import fm.qingting.framework.data.IDataRecvHandler;
import fm.qingting.framework.data.IDataSource;
import fm.qingting.framework.data.IDataToken;
import fm.qingting.framework.data.Result;
import java.util.List;
import java.util.Map;

public class PullMsgStateDS
  implements IDataSource
{
  private static PullMsgStateDS instance;

  // ERROR //
  private List<String> acquirePullMsgState(DataCommand paramDataCommand)
  {
    // Byte code:
    //   0: aload_1
    //   1: invokevirtual 22	fm/qingting/framework/data/DataCommand:getParam	()Ljava/util/Map;
    //   4: pop
    //   5: new 24	java/util/ArrayList
    //   8: dup
    //   9: invokespecial 25	java/util/ArrayList:<init>	()V
    //   12: astore_3
    //   13: invokestatic 31	fm/qingting/qtradio/data/DBManager:getInstance	()Lfm/qingting/qtradio/data/DBManager;
    //   16: ldc 33
    //   18: invokevirtual 37	fm/qingting/qtradio/data/DBManager:getReadableDB	(Ljava/lang/String;)Landroid/database/sqlite/SQLiteDatabase;
    //   21: ldc 39
    //   23: aconst_null
    //   24: invokevirtual 45	android/database/sqlite/SQLiteDatabase:rawQuery	(Ljava/lang/String;[Ljava/lang/String;)Landroid/database/Cursor;
    //   27: astore 5
    //   29: aload 5
    //   31: invokeinterface 51 1 0
    //   36: ifeq +38 -> 74
    //   39: aload 5
    //   41: aload 5
    //   43: ldc 53
    //   45: invokeinterface 57 2 0
    //   50: invokeinterface 61 2 0
    //   55: astore 6
    //   57: aload 6
    //   59: ifnull -30 -> 29
    //   62: aload_3
    //   63: aload 6
    //   65: invokeinterface 67 2 0
    //   70: pop
    //   71: goto -42 -> 29
    //   74: aload 5
    //   76: invokeinterface 70 1 0
    //   81: aload_3
    //   82: areturn
    //   83: astore 8
    //   85: aconst_null
    //   86: areturn
    //   87: astore 4
    //   89: aload_3
    //   90: areturn
    //
    // Exception table:
    //   from	to	target	type
    //   5	13	83	java/lang/Exception
    //   13	29	87	java/lang/Exception
    //   29	57	87	java/lang/Exception
    //   62	71	87	java/lang/Exception
    //   74	81	87	java/lang/Exception
  }

  private boolean deletePullMsgState(DataCommand paramDataCommand)
  {
    try
    {
      DBManager.getInstance().getWritableDB("pullMsgState").execSQL("delete from pullMsgState");
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
    localDataToken.setData(new Result(true, acquirePullMsgState(paramDataCommand)));
    return localDataToken;
  }

  private DataToken doDeleteCommand(DataCommand paramDataCommand)
  {
    DataToken localDataToken = new DataToken();
    localDataToken.setDataInfo(paramDataCommand);
    localDataToken.setData(new Result(true, Boolean.valueOf(deletePullMsgState(paramDataCommand))));
    return localDataToken;
  }

  private DataToken doInsertCommand(DataCommand paramDataCommand)
  {
    DataToken localDataToken = new DataToken();
    localDataToken.setDataInfo(paramDataCommand);
    localDataToken.setData(new Result(true, Boolean.valueOf(insertPullMsgState(paramDataCommand))));
    return localDataToken;
  }

  private DataToken doUpdateCommand(DataCommand paramDataCommand)
  {
    DataToken localDataToken = new DataToken();
    localDataToken.setDataInfo(paramDataCommand);
    localDataToken.setData(new Result(true, Boolean.valueOf(updatePullMsgState(paramDataCommand))));
    return localDataToken;
  }

  public static PullMsgStateDS getInstance()
  {
    if (instance == null)
      instance = new PullMsgStateDS();
    return instance;
  }

  private boolean insertPullMsgState(DataCommand paramDataCommand)
  {
    String str = (String)paramDataCommand.getParam().get("id");
    if (str == null)
      return false;
    try
    {
      SQLiteDatabase localSQLiteDatabase = DBManager.getInstance().getWritableDB("pullMsgState");
      localSQLiteDatabase.beginTransaction();
      Object[] arrayOfObject = new Object[2];
      arrayOfObject[0] = str;
      arrayOfObject[1] = Integer.valueOf(1);
      localSQLiteDatabase.execSQL("insert into pullMsgState(id,state) values(?,?)", arrayOfObject);
      localSQLiteDatabase.setTransactionSuccessful();
      localSQLiteDatabase.endTransaction();
      return true;
    }
    catch (Exception localException)
    {
    }
    return false;
  }

  private boolean updatePullMsgState(DataCommand paramDataCommand)
  {
    List localList = (List)paramDataCommand.getParam().get("ids");
    if (localList == null)
      return false;
    try
    {
      SQLiteDatabase localSQLiteDatabase = DBManager.getInstance().getWritableDB("pullMsgState");
      localSQLiteDatabase.beginTransaction();
      localSQLiteDatabase.execSQL("delete from pullMsgState");
      for (int i = 0; i < localList.size(); i++)
      {
        String str = (String)localList.get(i);
        Object[] arrayOfObject = new Object[2];
        arrayOfObject[0] = str;
        arrayOfObject[1] = Integer.valueOf(1);
        localSQLiteDatabase.execSQL("insert into pullMsgState(id,state) values(?,?)", arrayOfObject);
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
    return "PullMsgStateDS";
  }

  public IDataToken doCommand(DataCommand paramDataCommand, IDataRecvHandler paramIDataRecvHandler)
  {
    String str = paramDataCommand.getCurrentCommand();
    if (str.equalsIgnoreCase("insertdb_pullmsgstate"))
      return doInsertCommand(paramDataCommand);
    if (str.equalsIgnoreCase("getdb_pullmsgstate"))
      return doAcquireCommand(paramDataCommand);
    if (str.equalsIgnoreCase("deletedb_pullmsgstate"))
      return doDeleteCommand(paramDataCommand);
    if (str.equalsIgnoreCase("updatedb_pullmsgstate"))
      return doUpdateCommand(paramDataCommand);
    return null;
  }

  public boolean isSynchronous(String paramString, Map<String, Object> paramMap)
  {
    return true;
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.qtradio.data.PullMsgStateDS
 * JD-Core Version:    0.6.2
 */