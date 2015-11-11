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
import fm.qingting.qtradio.model.PingInfoV6;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class MediaCenterDS
  implements IDataSource
{
  private static MediaCenterDS instance;

  // ERROR //
  private Map<String, List<PingInfoV6>> acquireDataCenters(DataCommand paramDataCommand)
  {
    // Byte code:
    //   0: new 18	java/util/HashMap
    //   3: dup
    //   4: invokespecial 19	java/util/HashMap:<init>	()V
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
    //   40: ifeq +129 -> 169
    //   43: aload 4
    //   45: aload 4
    //   47: ldc 50
    //   49: invokeinterface 54 2 0
    //   54: invokeinterface 58 2 0
    //   59: astore 6
    //   61: aload 4
    //   63: aload 4
    //   65: ldc 60
    //   67: invokeinterface 54 2 0
    //   72: invokeinterface 58 2 0
    //   77: astore 7
    //   79: aload 5
    //   81: aload 7
    //   83: ldc 62
    //   85: invokevirtual 66	com/google/gson/Gson:fromJson	(Ljava/lang/String;Ljava/lang/Class;)Ljava/lang/Object;
    //   88: checkcast 62	fm/qingting/qtradio/model/PingInfoV6
    //   91: astore 9
    //   93: aload 9
    //   95: ifnull -62 -> 33
    //   98: aload 6
    //   100: ifnull -67 -> 33
    //   103: aload_2
    //   104: aload 6
    //   106: invokeinterface 72 2 0
    //   111: ifnull +25 -> 136
    //   114: aload_2
    //   115: aload 6
    //   117: invokeinterface 72 2 0
    //   122: checkcast 74	java/util/List
    //   125: aload 9
    //   127: invokeinterface 78 2 0
    //   132: pop
    //   133: goto -100 -> 33
    //   136: new 80	java/util/ArrayList
    //   139: dup
    //   140: invokespecial 81	java/util/ArrayList:<init>	()V
    //   143: astore 10
    //   145: aload 10
    //   147: aload 9
    //   149: invokeinterface 78 2 0
    //   154: pop
    //   155: aload_2
    //   156: aload 6
    //   158: aload 10
    //   160: invokeinterface 85 3 0
    //   165: pop
    //   166: goto -133 -> 33
    //   169: aload 4
    //   171: invokeinterface 88 1 0
    //   176: aload_2
    //   177: areturn
    //   178: astore_3
    //   179: aconst_null
    //   180: areturn
    //   181: astore 8
    //   183: goto -150 -> 33
    //
    // Exception table:
    //   from	to	target	type
    //   8	33	178	java/lang/Exception
    //   33	79	178	java/lang/Exception
    //   169	176	178	java/lang/Exception
    //   79	93	181	java/lang/Exception
    //   103	133	181	java/lang/Exception
    //   136	166	181	java/lang/Exception
  }

  private boolean deleteDataCenters(DataCommand paramDataCommand)
  {
    try
    {
      SQLiteDatabase localSQLiteDatabase = DBManager.getInstance().getWritableDB("mediaCenterDS");
      localSQLiteDatabase.beginTransaction();
      localSQLiteDatabase.execSQL("delete from mediaCenterDS");
      localSQLiteDatabase.setTransactionSuccessful();
      localSQLiteDatabase.endTransaction();
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
    localDataToken.setData(new Result(true, acquireDataCenters(paramDataCommand)));
    return localDataToken;
  }

  private DataToken doDeleteCommand(DataCommand paramDataCommand)
  {
    DataToken localDataToken = new DataToken();
    localDataToken.setDataInfo(paramDataCommand);
    localDataToken.setData(new Result(true, Boolean.valueOf(deleteDataCenters(paramDataCommand))));
    return localDataToken;
  }

  private DataToken doInsertCommand(DataCommand paramDataCommand)
  {
    DataToken localDataToken = new DataToken();
    localDataToken.setDataInfo(paramDataCommand);
    localDataToken.setData(new Result(true, Boolean.valueOf(false)));
    return localDataToken;
  }

  private DataToken doUpdateCommand(DataCommand paramDataCommand)
  {
    DataToken localDataToken = new DataToken();
    localDataToken.setDataInfo(paramDataCommand);
    localDataToken.setData(new Result(true, Boolean.valueOf(updateDataCenters(paramDataCommand))));
    return localDataToken;
  }

  public static MediaCenterDS getInstance()
  {
    if (instance == null)
      instance = new MediaCenterDS();
    return instance;
  }

  private boolean updateDataCenters(DataCommand paramDataCommand)
  {
    try
    {
      HashMap localHashMap = (HashMap)paramDataCommand.getParam().get("mediacenter");
      if ((localHashMap != null) && (localHashMap.size() != 0))
      {
        SQLiteDatabase localSQLiteDatabase = DBManager.getInstance().getWritableDB("mediaCenterDS");
        localSQLiteDatabase.beginTransaction();
        localSQLiteDatabase.execSQL("delete from mediaCenterDS");
        Iterator localIterator = localHashMap.entrySet().iterator();
        while (localIterator.hasNext())
        {
          Map.Entry localEntry = (Map.Entry)localIterator.next();
          List localList = (List)localEntry.getValue();
          String str = (String)localEntry.getKey();
          if ((localList != null) && (str != null))
          {
            Gson localGson = new Gson();
            for (int i = 0; i < localList.size(); i++)
              localSQLiteDatabase.execSQL("insert into mediaCenterDS(type,pinginfo) values(?,?)", new Object[] { str, localGson.toJson((PingInfoV6)localList.get(i)) });
          }
        }
        localSQLiteDatabase.setTransactionSuccessful();
        localSQLiteDatabase.endTransaction();
        return true;
      }
    }
    catch (Exception localException)
    {
      return false;
    }
    return false;
  }

  public void addParser(IDataParser paramIDataParser)
  {
  }

  public String dataSourceName()
  {
    return "mediaCenterDS";
  }

  public IDataToken doCommand(DataCommand paramDataCommand, IDataRecvHandler paramIDataRecvHandler)
  {
    String str = paramDataCommand.getCurrentCommand();
    if (str.equalsIgnoreCase("GETDB_MEDIA_CENTER"))
      return doAcquireCommand(paramDataCommand);
    if (str.equalsIgnoreCase("DELETEDB_MEDIA_CENTER"))
      return doDeleteCommand(paramDataCommand);
    if (str.equalsIgnoreCase("UPDATEDB_MEDIA_CENTER"))
      return doUpdateCommand(paramDataCommand);
    return null;
  }

  public boolean isSynchronous(String paramString, Map<String, Object> paramMap)
  {
    return true;
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.qtradio.data.MediaCenterDS
 * JD-Core Version:    0.6.2
 */