package fm.qingting.qtradio.data;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import fm.qingting.framework.data.DataCommand;
import fm.qingting.framework.data.DataToken;
import fm.qingting.framework.data.IDataParser;
import fm.qingting.framework.data.IDataRecvHandler;
import fm.qingting.framework.data.IDataSource;
import fm.qingting.framework.data.IDataToken;
import fm.qingting.framework.data.Result;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class CommonDS
  implements IDataSource
{
  private static CommonDS instance;

  private String acquireCommonRecord(DataCommand paramDataCommand)
  {
    String str1 = (String)paramDataCommand.getParam().get("key");
    if (str1 == null)
      return null;
    while (true)
    {
      try
      {
        String str2 = "select value from commonRecords where key = " + "'";
        String str3 = str2 + str1;
        String str4 = str3 + "'";
        Cursor localCursor = DBManager.getInstance().getReadableDB("commonRecords").rawQuery(str4, null);
        if (localCursor.moveToNext())
        {
          str5 = localCursor.getString(localCursor.getColumnIndex("value"));
          localCursor.close();
          return str5;
        }
      }
      catch (Exception localException)
      {
        return null;
      }
      String str5 = null;
    }
  }

  private boolean deleteCommonRecord(DataCommand paramDataCommand)
  {
    try
    {
      DBManager.getInstance().getWritableDB("commonRecords").execSQL("delete from commonRecords");
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
    localDataToken.setData(new Result(true, acquireCommonRecord(paramDataCommand)));
    return localDataToken;
  }

  private DataToken doDeleteCommand(DataCommand paramDataCommand)
  {
    DataToken localDataToken = new DataToken();
    localDataToken.setDataInfo(paramDataCommand);
    localDataToken.setData(new Result(true, Boolean.valueOf(deleteCommonRecord(paramDataCommand))));
    return localDataToken;
  }

  private DataToken doInsertCommand(DataCommand paramDataCommand)
  {
    DataToken localDataToken = new DataToken();
    localDataToken.setDataInfo(paramDataCommand);
    localDataToken.setData(new Result(true, Boolean.valueOf(insertCommonRecord(paramDataCommand))));
    return localDataToken;
  }

  private DataToken doUpdateCommand(DataCommand paramDataCommand)
  {
    DataToken localDataToken = new DataToken();
    localDataToken.setDataInfo(paramDataCommand);
    localDataToken.setData(new Result(true, Boolean.valueOf(updateCommonRecord(paramDataCommand))));
    return localDataToken;
  }

  private DataToken doUpdateGroupCommand(DataCommand paramDataCommand)
  {
    DataToken localDataToken = new DataToken();
    localDataToken.setDataInfo(paramDataCommand);
    localDataToken.setData(new Result(true, Boolean.valueOf(updateGroupCommonRecord(paramDataCommand))));
    return localDataToken;
  }

  public static CommonDS getInstance()
  {
    if (instance == null)
      instance = new CommonDS();
    return instance;
  }

  private boolean insertCommonRecord(DataCommand paramDataCommand)
  {
    Map localMap = paramDataCommand.getParam();
    String str1 = (String)localMap.get("type");
    String str2 = (String)localMap.get("value");
    String str3 = (String)localMap.get("key");
    if ((str1 == null) || (str2 == null) || (str3 == null))
      return false;
    try
    {
      DBManager.getInstance().getWritableDB("commonRecords").execSQL("insert into commonRecords(key,type,value) values(?,?,?)", new Object[] { str3, str1, str2 });
      return true;
    }
    catch (Exception localException)
    {
    }
    return false;
  }

  private boolean updateCommonRecord(DataCommand paramDataCommand)
  {
    Map localMap = paramDataCommand.getParam();
    String str1 = (String)localMap.get("type");
    String str2 = (String)localMap.get("value");
    String str3 = (String)localMap.get("key");
    if ((str1 == null) || (str2 == null) || (str3 == null))
      return false;
    try
    {
      SQLiteDatabase localSQLiteDatabase = DBManager.getInstance().getWritableDB("commonRecords");
      String str4 = "select value from commonRecords where key = " + "'";
      String str5 = str4 + str3;
      Cursor localCursor = localSQLiteDatabase.rawQuery(str5 + "'", null);
      if ((localCursor == null) || (!localCursor.moveToNext()))
      {
        insertCommonRecord(paramDataCommand);
        return true;
      }
      localCursor.close();
      String str6 = "update commonRecords set value = " + "'";
      String str7 = str6 + str2;
      String str8 = str7 + "'";
      String str9 = str8 + " where key = ";
      String str10 = str9 + "'";
      String str11 = str10 + str3;
      localSQLiteDatabase.execSQL(str11 + "'");
      return true;
    }
    catch (Exception localException)
    {
    }
    return false;
  }

  private boolean updateGroupCommonRecord(DataCommand paramDataCommand)
  {
    Map localMap = (Map)paramDataCommand.getParam().get("items");
    if ((localMap == null) || (localMap.size() == 0))
      return false;
    try
    {
      SQLiteDatabase localSQLiteDatabase = DBManager.getInstance().getWritableDB("commonRecords");
      localSQLiteDatabase.beginTransaction();
      Iterator localIterator = localMap.entrySet().iterator();
      while (localIterator.hasNext())
      {
        Map.Entry localEntry = (Map.Entry)localIterator.next();
        String str1 = (String)localEntry.getValue();
        String str2 = (String)localEntry.getKey();
        String str3 = "select value from commonRecords where key = " + "'";
        String str4 = str3 + str2;
        Cursor localCursor = localSQLiteDatabase.rawQuery(str4 + "'", null);
        if ((localCursor == null) || (!localCursor.moveToNext()))
        {
          localSQLiteDatabase.execSQL("insert into commonRecords(key,type,value) values(?,?,?)", new Object[] { str2, "", str1 });
        }
        else
        {
          localCursor.close();
          String str5 = "update commonRecords set value = " + "'";
          String str6 = str5 + str1;
          String str7 = str6 + "'";
          String str8 = str7 + "where key = ";
          String str9 = str8 + "'";
          String str10 = str9 + str2;
          localSQLiteDatabase.execSQL(str10 + "'");
        }
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
    return "CommonDS";
  }

  public IDataToken doCommand(DataCommand paramDataCommand, IDataRecvHandler paramIDataRecvHandler)
  {
    String str = paramDataCommand.getCurrentCommand();
    if (str.equalsIgnoreCase("insertdb_common_record"))
      return doInsertCommand(paramDataCommand);
    if (str.equalsIgnoreCase("getdb_common_record"))
      return doAcquireCommand(paramDataCommand);
    if (str.equalsIgnoreCase("deletedb_common_record"))
      return doDeleteCommand(paramDataCommand);
    if (str.equalsIgnoreCase("updatedb_common_record"))
      return doUpdateCommand(paramDataCommand);
    if (str.equalsIgnoreCase("updatedb_common_grouprecord"))
      return doUpdateGroupCommand(paramDataCommand);
    return null;
  }

  public boolean isSynchronous(String paramString, Map<String, Object> paramMap)
  {
    return true;
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.qtradio.data.CommonDS
 * JD-Core Version:    0.6.2
 */