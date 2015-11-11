package fm.qingting.qtradio.data;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.google.gson.Gson;
import fm.qingting.framework.data.DataCommand;
import fm.qingting.framework.data.DataToken;
import fm.qingting.framework.data.IDataParser;
import fm.qingting.framework.data.IDataRecvHandler;
import fm.qingting.framework.data.IDataSource;
import fm.qingting.framework.data.IDataToken;
import fm.qingting.framework.data.Result;
import fm.qingting.qtradio.pushcontent.LiveChannelInfoBean;
import java.util.Map;

public class GenericDS
  implements IDataSource
{
  private static GenericDS instance;

  private Object acquireGenericObj(DataCommand paramDataCommand)
  {
    Map localMap = paramDataCommand.getParam();
    String str1 = (String)localMap.get("type");
    String str2 = (String)localMap.get("id");
    if ((str1 == null) || (str2 == null))
      return null;
    try
    {
      String str3 = "select value from genericObjs where type = '" + str1 + "'" + " and id=" + "'" + str2 + "'";
      SQLiteDatabase localSQLiteDatabase = DBManager.getInstance().getReadableDB("genericObjs");
      Cursor localCursor = localSQLiteDatabase.rawQuery(str3, null);
      Gson localGson = new Gson();
      String str4;
      if (localCursor.moveToNext())
      {
        str4 = localCursor.getString(localCursor.getColumnIndex("value"));
        boolean bool = str1.equalsIgnoreCase("livechannelinfobean");
        if (!bool);
      }
      while (true)
      {
        try
        {
          localLiveChannelInfoBean = (LiveChannelInfoBean)localGson.fromJson(str4, LiveChannelInfoBean.class);
          localCursor.close();
          localSQLiteDatabase.close();
          return localLiveChannelInfoBean;
        }
        catch (Exception localException2)
        {
          localException2.printStackTrace();
        }
        break;
        LiveChannelInfoBean localLiveChannelInfoBean = null;
      }
    }
    catch (Exception localException1)
    {
    }
    return null;
  }

  private boolean deleteGenericObj(DataCommand paramDataCommand)
  {
    try
    {
      SQLiteDatabase localSQLiteDatabase = DBManager.getInstance().getWritableDB("genericObjs");
      localSQLiteDatabase.execSQL("delete from genericObjs");
      localSQLiteDatabase.close();
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
    localDataToken.setData(new Result(true, acquireGenericObj(paramDataCommand)));
    return localDataToken;
  }

  private DataToken doDeleteCommand(DataCommand paramDataCommand)
  {
    DataToken localDataToken = new DataToken();
    localDataToken.setDataInfo(paramDataCommand);
    localDataToken.setData(new Result(true, Boolean.valueOf(deleteGenericObj(paramDataCommand))));
    return localDataToken;
  }

  private DataToken doUpdateCommand(DataCommand paramDataCommand)
  {
    DataToken localDataToken = new DataToken();
    localDataToken.setDataInfo(paramDataCommand);
    localDataToken.setData(new Result(true, Boolean.valueOf(updateGenericObj(paramDataCommand))));
    return localDataToken;
  }

  public static GenericDS getInstance()
  {
    if (instance == null)
      instance = new GenericDS();
    return instance;
  }

  private boolean updateGenericObj(DataCommand paramDataCommand)
  {
    Map localMap = paramDataCommand.getParam();
    String str1 = (String)localMap.get("type");
    String str2 = (String)localMap.get("id");
    if ((str1 == null) || (str2 == null))
      return false;
    LiveChannelInfoBean localLiveChannelInfoBean;
    if (str1.equalsIgnoreCase("livechannelinfobean"))
    {
      localLiveChannelInfoBean = (LiveChannelInfoBean)localMap.get("value");
      if (localLiveChannelInfoBean == null);
    }
    for (String str3 = new Gson().toJson(localLiveChannelInfoBean); ; str3 = null)
    {
      if (str3 == null)
        return false;
      try
      {
        SQLiteDatabase localSQLiteDatabase = DBManager.getInstance().getWritableDB("genericObjs");
        String str4 = "delete from genericObjs where type = '" + str1 + "'" + " and id=" + "'" + str2 + "'";
        localSQLiteDatabase.beginTransaction();
        localSQLiteDatabase.execSQL(str4);
        localSQLiteDatabase.execSQL("insert into genericObjs(id,type,value) values(?,?,?)", new Object[] { str2, str1, str3 });
        localSQLiteDatabase.close();
        return true;
      }
      catch (Exception localException)
      {
        return false;
      }
    }
  }

  public void addParser(IDataParser paramIDataParser)
  {
  }

  public String dataSourceName()
  {
    return "GenericDS";
  }

  public IDataToken doCommand(DataCommand paramDataCommand, IDataRecvHandler paramIDataRecvHandler)
  {
    String str = paramDataCommand.getCurrentCommand();
    if (str.equalsIgnoreCase("getdb_generic_obj"))
      return doAcquireCommand(paramDataCommand);
    if (str.equalsIgnoreCase("deletedb_generic_obj"))
      return doDeleteCommand(paramDataCommand);
    if (str.equalsIgnoreCase("updatedb_generic_obj"))
      return doUpdateCommand(paramDataCommand);
    return null;
  }

  public boolean isSynchronous(String paramString, Map<String, Object> paramMap)
  {
    return true;
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.qtradio.data.GenericDS
 * JD-Core Version:    0.6.2
 */