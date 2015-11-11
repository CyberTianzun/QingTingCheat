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
import fm.qingting.qtradio.model.PlayedMetaData;
import java.util.Map;

public class PlayedMetaDS
  implements IDataSource
{
  private static PlayedMetaDS instance;

  // ERROR //
  private java.util.List<PlayedMetaData> acquirePlayedMeta(DataCommand paramDataCommand)
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
    //   43: ifeq +66 -> 109
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
    //   73: checkcast 60	fm/qingting/qtradio/model/PlayedMetaData
    //   76: astore 9
    //   78: aload 9
    //   80: ifnull +45 -> 125
    //   83: aload_2
    //   84: aload 9
    //   86: invokeinterface 70 2 0
    //   91: pop
    //   92: goto +33 -> 125
    //   95: astore 8
    //   97: aload 8
    //   99: invokevirtual 73	java/lang/Exception:printStackTrace	()V
    //   102: aload 6
    //   104: astore 9
    //   106: goto -28 -> 78
    //   109: aload 4
    //   111: invokeinterface 76 1 0
    //   116: aload_2
    //   117: areturn
    //   118: astore 11
    //   120: aconst_null
    //   121: areturn
    //   122: astore_3
    //   123: aload_2
    //   124: areturn
    //   125: aload 9
    //   127: astore 6
    //   129: goto -93 -> 36
    //
    // Exception table:
    //   from	to	target	type
    //   64	78	95	java/lang/Exception
    //   0	8	118	java/lang/Exception
    //   8	33	122	java/lang/Exception
    //   36	64	122	java/lang/Exception
    //   83	92	122	java/lang/Exception
    //   97	102	122	java/lang/Exception
    //   109	116	122	java/lang/Exception
  }

  private boolean deletePlayedMeta(DataCommand paramDataCommand)
  {
    PlayedMetaData localPlayedMetaData = (PlayedMetaData)paramDataCommand.getParam().get("playedMeta");
    if (localPlayedMetaData == null)
      return false;
    try
    {
      String str = "delete from playedMetaData" + " where id = '" + localPlayedMetaData.uniqueId + "'";
      DBManager.getInstance().getWritableDB("playedMeta").execSQL(str);
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
    localDataToken.setData(new Result(true, acquirePlayedMeta(paramDataCommand)));
    return localDataToken;
  }

  private DataToken doDeleteCommand(DataCommand paramDataCommand)
  {
    DataToken localDataToken = new DataToken();
    localDataToken.setDataInfo(paramDataCommand);
    localDataToken.setData(new Result(true, Boolean.valueOf(deletePlayedMeta(paramDataCommand))));
    return localDataToken;
  }

  private DataToken doInsertCommand(DataCommand paramDataCommand)
  {
    DataToken localDataToken = new DataToken();
    localDataToken.setDataInfo(paramDataCommand);
    localDataToken.setData(new Result(true, Boolean.valueOf(insertPlayedMeta(paramDataCommand))));
    return localDataToken;
  }

  private DataToken doUpdateCommand(DataCommand paramDataCommand)
  {
    DataToken localDataToken = new DataToken();
    localDataToken.setDataInfo(paramDataCommand);
    localDataToken.setData(new Result(true, Boolean.valueOf(updatePlayedMeta(paramDataCommand))));
    return localDataToken;
  }

  public static PlayedMetaDS getInstance()
  {
    if (instance == null)
      instance = new PlayedMetaDS();
    return instance;
  }

  private boolean insertPlayedMeta(DataCommand paramDataCommand)
  {
    PlayedMetaData localPlayedMetaData = (PlayedMetaData)paramDataCommand.getParam().get("playedMeta");
    if (localPlayedMetaData == null)
      return false;
    try
    {
      SQLiteDatabase localSQLiteDatabase = DBManager.getInstance().getWritableDB("playedMeta");
      localSQLiteDatabase.beginTransaction();
      String str = new Gson().toJson(localPlayedMetaData);
      Object[] arrayOfObject = new Object[2];
      arrayOfObject[0] = Integer.valueOf(localPlayedMetaData.uniqueId);
      arrayOfObject[1] = str;
      localSQLiteDatabase.execSQL("insert into playedMetaData(id,playedMetaData) values(?,?)", arrayOfObject);
      localSQLiteDatabase.setTransactionSuccessful();
      localSQLiteDatabase.endTransaction();
      return true;
    }
    catch (Exception localException)
    {
    }
    return false;
  }

  private boolean updatePlayedMeta(DataCommand paramDataCommand)
  {
    PlayedMetaData localPlayedMetaData = (PlayedMetaData)paramDataCommand.getParam().get("playedMeta");
    if (localPlayedMetaData == null)
      return false;
    try
    {
      String str1 = "delete from playedMetaData" + " where id = '" + localPlayedMetaData.uniqueId + "'";
      SQLiteDatabase localSQLiteDatabase = DBManager.getInstance().getWritableDB("playedMeta");
      localSQLiteDatabase.beginTransaction();
      localSQLiteDatabase.execSQL(str1);
      String str2 = new Gson().toJson(localPlayedMetaData);
      Object[] arrayOfObject = new Object[2];
      arrayOfObject[0] = Integer.valueOf(localPlayedMetaData.uniqueId);
      arrayOfObject[1] = str2;
      localSQLiteDatabase.execSQL("insert into playedMetaData(id,playedMetaData) values(?,?)", arrayOfObject);
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
    return "PlayedMetaDS";
  }

  public IDataToken doCommand(DataCommand paramDataCommand, IDataRecvHandler paramIDataRecvHandler)
  {
    String str = paramDataCommand.getCurrentCommand();
    if (str.equalsIgnoreCase("insertdb_playedmeta"))
      return doInsertCommand(paramDataCommand);
    if (str.equalsIgnoreCase("getdb_playedmeta"))
      return doAcquireCommand(paramDataCommand);
    if (str.equalsIgnoreCase("deletedb_playedmeta"))
      return doDeleteCommand(paramDataCommand);
    if (str.equalsIgnoreCase("updatedb_playedmeta"))
      return doUpdateCommand(paramDataCommand);
    return null;
  }

  public boolean isSynchronous(String paramString, Map<String, Object> paramMap)
  {
    return true;
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.qtradio.data.PlayedMetaDS
 * JD-Core Version:    0.6.2
 */