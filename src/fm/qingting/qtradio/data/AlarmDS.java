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
import fm.qingting.qtradio.model.AlarmInfo;
import java.util.List;
import java.util.Map;

public class AlarmDS
  implements IDataSource
{
  private static AlarmDS instance;

  // ERROR //
  private List<AlarmInfo> acquireAlarmInfos(DataCommand paramDataCommand)
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
    //   33: aload 4
    //   35: invokeinterface 48 1 0
    //   40: ifeq +128 -> 168
    //   43: aload 4
    //   45: aload 4
    //   47: ldc 50
    //   49: invokeinterface 54 2 0
    //   54: invokeinterface 58 2 0
    //   59: astore 6
    //   61: aload 5
    //   63: aload 6
    //   65: ldc 60
    //   67: invokevirtual 64	com/google/gson/Gson:fromJson	(Ljava/lang/String;Ljava/lang/Class;)Ljava/lang/Object;
    //   70: checkcast 60	fm/qingting/qtradio/model/AlarmInfo
    //   73: astore 14
    //   75: aload 14
    //   77: astore 10
    //   79: aconst_null
    //   80: astore 9
    //   82: aload 10
    //   84: ifnull +51 -> 135
    //   87: aload_2
    //   88: aload 10
    //   90: invokeinterface 70 2 0
    //   95: pop
    //   96: goto -63 -> 33
    //   99: astore_3
    //   100: aload_2
    //   101: areturn
    //   102: astore 7
    //   104: aload 5
    //   106: aload 6
    //   108: ldc 72
    //   110: invokevirtual 64	com/google/gson/Gson:fromJson	(Ljava/lang/String;Ljava/lang/Class;)Ljava/lang/Object;
    //   113: checkcast 72	fm/qingting/qtradio/model/AlarmInfoLegacy
    //   116: astore 9
    //   118: aconst_null
    //   119: astore 10
    //   121: goto -39 -> 82
    //   124: astore 8
    //   126: aconst_null
    //   127: astore 9
    //   129: aconst_null
    //   130: astore 10
    //   132: goto -50 -> 82
    //   135: aload 9
    //   137: ifnull -104 -> 33
    //   140: new 60	fm/qingting/qtradio/model/AlarmInfo
    //   143: dup
    //   144: invokespecial 73	fm/qingting/qtradio/model/AlarmInfo:<init>	()V
    //   147: astore 11
    //   149: aload 11
    //   151: aload 9
    //   153: invokevirtual 77	fm/qingting/qtradio/model/AlarmInfo:update	(Lfm/qingting/qtradio/model/AlarmInfoLegacy;)V
    //   156: aload_2
    //   157: aload 11
    //   159: invokeinterface 70 2 0
    //   164: pop
    //   165: goto -132 -> 33
    //   168: aload 4
    //   170: invokeinterface 80 1 0
    //   175: aload_2
    //   176: areturn
    //   177: astore 15
    //   179: aconst_null
    //   180: areturn
    //
    // Exception table:
    //   from	to	target	type
    //   8	33	99	java/lang/Exception
    //   33	61	99	java/lang/Exception
    //   87	96	99	java/lang/Exception
    //   140	165	99	java/lang/Exception
    //   168	175	99	java/lang/Exception
    //   61	75	102	java/lang/Exception
    //   104	118	124	java/lang/Exception
    //   0	8	177	java/lang/Exception
  }

  private boolean deleteAlarmInfos(DataCommand paramDataCommand)
  {
    try
    {
      DBManager.getInstance().getWritableDB("alarmInfos").execSQL("delete from alarmInfos");
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
    localDataToken.setData(new Result(true, acquireAlarmInfos(paramDataCommand)));
    return localDataToken;
  }

  private DataToken doDeleteCommand(DataCommand paramDataCommand)
  {
    DataToken localDataToken = new DataToken();
    localDataToken.setDataInfo(paramDataCommand);
    localDataToken.setData(new Result(true, Boolean.valueOf(deleteAlarmInfos(paramDataCommand))));
    return localDataToken;
  }

  private DataToken doInsertCommand(DataCommand paramDataCommand)
  {
    DataToken localDataToken = new DataToken();
    localDataToken.setDataInfo(paramDataCommand);
    localDataToken.setData(new Result(true, Boolean.valueOf(insertAlarmInfos(paramDataCommand))));
    return localDataToken;
  }

  public static AlarmDS getInstance()
  {
    if (instance == null)
      instance = new AlarmDS();
    return instance;
  }

  private boolean insertAlarmInfos(DataCommand paramDataCommand)
  {
    List localList = (List)paramDataCommand.getParam().get("alarmInfos");
    if ((localList == null) || (localList.size() == 0))
      return false;
    try
    {
      SQLiteDatabase localSQLiteDatabase = DBManager.getInstance().getWritableDB("alarmInfos");
      localSQLiteDatabase.beginTransaction();
      Gson localGson = new Gson();
      for (int i = 0; i < localList.size(); i++)
        localSQLiteDatabase.execSQL("insert into alarmInfos(alarmInfo) values(?)", new Object[] { localGson.toJson((AlarmInfo)localList.get(i)) });
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
    return "AlarmDS";
  }

  public IDataToken doCommand(DataCommand paramDataCommand, IDataRecvHandler paramIDataRecvHandler)
  {
    String str = paramDataCommand.getCurrentCommand();
    if (str.equalsIgnoreCase("insertdb_alarm_info"))
      return doInsertCommand(paramDataCommand);
    if (str.equalsIgnoreCase("getdb_alarm_info"))
      return doAcquireCommand(paramDataCommand);
    if (str.equalsIgnoreCase("deletedb_alarm_info"))
      return doDeleteCommand(paramDataCommand);
    return null;
  }

  public boolean isSynchronous(String paramString, Map<String, Object> paramMap)
  {
    return true;
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.qtradio.data.AlarmDS
 * JD-Core Version:    0.6.2
 */