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
import fm.qingting.qtradio.room.UserInfo;
import java.util.Map;

public class PodcasterDS
  implements IDataSource
{
  private static PodcasterDS instance;

  private UserInfo acquireUserInfo(DataCommand paramDataCommand)
  {
    try
    {
      int i = ((Integer)paramDataCommand.getParam().get("pid")).intValue();
      String str = "select * from podcastersInfo where podcasterId = '" + i + "'";
      Cursor localCursor = DBManager.getInstance().getReadableDB("podcastersInfo").rawQuery(str, null);
      Gson localGson;
      if (localCursor.moveToNext())
      {
        localGson = new Gson();
        localCursor.getColumnIndex("podcasterId");
      }
      for (UserInfo localUserInfo = (UserInfo)localGson.fromJson(localCursor.getString(localCursor.getColumnIndex("data")), UserInfo.class); ; localUserInfo = null)
      {
        localCursor.close();
        return localUserInfo;
      }
    }
    catch (Exception localException)
    {
    }
    return null;
  }

  private DataToken doAcquireCommand(DataCommand paramDataCommand)
  {
    DataToken localDataToken = new DataToken();
    localDataToken.setDataInfo(paramDataCommand);
    localDataToken.setData(new Result(true, acquireUserInfo(paramDataCommand)));
    return localDataToken;
  }

  private DataToken doUpdateCommand(DataCommand paramDataCommand)
  {
    DataToken localDataToken = new DataToken();
    localDataToken.setDataInfo(paramDataCommand);
    localDataToken.setData(new Result(true, Boolean.valueOf(updateUserInfo(paramDataCommand))));
    return localDataToken;
  }

  public static PodcasterDS getInstance()
  {
    if (instance == null)
      instance = new PodcasterDS();
    return instance;
  }

  private boolean updateUserInfo(DataCommand paramDataCommand)
  {
    UserInfo localUserInfo = (UserInfo)paramDataCommand.getParam().get("userInfo");
    if ((localUserInfo == null) || (localUserInfo.snsInfo == null))
      return false;
    try
    {
      Gson localGson = new Gson();
      SQLiteDatabase localSQLiteDatabase = DBManager.getInstance().getWritableDB("podcastersInfo");
      Object[] arrayOfObject = new Object[2];
      arrayOfObject[0] = Integer.valueOf(localUserInfo.podcasterId);
      arrayOfObject[1] = localGson.toJson(localUserInfo);
      localSQLiteDatabase.execSQL("insert or replace into podcastersInfo (podcasterId, data)  values(?,?)", arrayOfObject);
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
    return "PodcasterDS";
  }

  public IDataToken doCommand(DataCommand paramDataCommand, IDataRecvHandler paramIDataRecvHandler)
  {
    String str = paramDataCommand.getCurrentCommand();
    if (str.equalsIgnoreCase("UPDATEDB_PODCASTER_INFO"))
      return doUpdateCommand(paramDataCommand);
    if (str.equalsIgnoreCase("GETDB_PODCASTER_INFO"))
      return doAcquireCommand(paramDataCommand);
    return null;
  }

  public boolean isSynchronous(String paramString, Map<String, Object> paramMap)
  {
    return true;
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.qtradio.data.PodcasterDS
 * JD-Core Version:    0.6.2
 */