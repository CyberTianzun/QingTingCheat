package fm.qingting.qtradio.data;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Pair;
import fm.qingting.framework.data.DataCommand;
import fm.qingting.framework.data.DataToken;
import fm.qingting.framework.data.IDataParser;
import fm.qingting.framework.data.IDataRecvHandler;
import fm.qingting.framework.data.IDataSource;
import fm.qingting.framework.data.IDataToken;
import fm.qingting.framework.data.Result;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MyPodcasterDS
  implements IDataSource
{
  private static MyPodcasterDS instance;

  private boolean acquireFollowInfo(DataCommand paramDataCommand)
  {
    try
    {
      Map localMap = paramDataCommand.getParam();
      int i = ((Integer)localMap.get("pid")).intValue();
      String str = (String)localMap.get("ukey");
      SQLiteDatabase localSQLiteDatabase = DBManager.getInstance().getReadableDB("podcasterFollow");
      String[] arrayOfString = new String[2];
      arrayOfString[0] = String.valueOf(i);
      arrayOfString[1] = str;
      Cursor localCursor = localSQLiteDatabase.rawQuery("select * from myPodcaster where podcasterId = ? AND userKey = ?", arrayOfString);
      if (localCursor.moveToNext());
      for (boolean bool = true; ; bool = false)
      {
        localCursor.close();
        return bool;
      }
    }
    catch (Exception localException)
    {
    }
    return false;
  }

  private List<Pair> acquireFollowInfoAll(DataCommand paramDataCommand)
  {
    String str = (String)paramDataCommand.getParam().get("ukey");
    ArrayList localArrayList = new ArrayList();
    try
    {
      Cursor localCursor = DBManager.getInstance().getReadableDB("podcasterFollow").rawQuery("select podcasterId,updateTime from myPodcaster where userKey=? order by followTime DESC", new String[] { str });
      int i = localCursor.getColumnIndex("podcasterId");
      int j = localCursor.getColumnIndex("updateTime");
      while (localCursor.moveToNext())
      {
        int k = localCursor.getInt(i);
        long l = localCursor.getLong(j);
        localArrayList.add(new Pair(Integer.valueOf(k), Long.valueOf(l)));
      }
      localCursor.close();
      return localArrayList;
    }
    catch (Exception localException)
    {
    }
    return localArrayList;
  }

  private boolean deleteFollowInfo(DataCommand paramDataCommand)
  {
    Map localMap = paramDataCommand.getParam();
    int i = ((Integer)localMap.get("pid")).intValue();
    String str = (String)localMap.get("ukey");
    try
    {
      SQLiteDatabase localSQLiteDatabase = DBManager.getInstance().getWritableDB("podcasterFollow");
      Object[] arrayOfObject = new Object[2];
      arrayOfObject[0] = Integer.valueOf(i);
      arrayOfObject[1] = str;
      localSQLiteDatabase.execSQL("delete from myPodcaster where podcasterId=? AND userKey =?", arrayOfObject);
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
    localDataToken.setData(new Result(true, Boolean.valueOf(acquireFollowInfo(paramDataCommand))));
    return localDataToken;
  }

  private DataToken doAcquireCommandAll(DataCommand paramDataCommand)
  {
    DataToken localDataToken = new DataToken();
    localDataToken.setDataInfo(paramDataCommand);
    localDataToken.setData(new Result(true, acquireFollowInfoAll(paramDataCommand)));
    return localDataToken;
  }

  private DataToken doDeleltCommand(DataCommand paramDataCommand)
  {
    DataToken localDataToken = new DataToken();
    localDataToken.setDataInfo(paramDataCommand);
    localDataToken.setData(new Result(true, Boolean.valueOf(deleteFollowInfo(paramDataCommand))));
    return localDataToken;
  }

  private DataToken doGetLatestProgramId(DataCommand paramDataCommand)
  {
    DataToken localDataToken = new DataToken();
    localDataToken.setDataInfo(paramDataCommand);
    localDataToken.setData(new Result(true, getLatestProgramId(paramDataCommand)));
    return localDataToken;
  }

  private DataToken doInsertCommand(DataCommand paramDataCommand)
  {
    DataToken localDataToken = new DataToken();
    localDataToken.setDataInfo(paramDataCommand);
    localDataToken.setData(new Result(true, Boolean.valueOf(updateFollowInfo(paramDataCommand))));
    return localDataToken;
  }

  private DataToken doUpdateLatestProgramId(DataCommand paramDataCommand)
  {
    DataToken localDataToken = new DataToken();
    localDataToken.setDataInfo(paramDataCommand);
    localDataToken.setData(new Result(true, Boolean.valueOf(updateLatestProgramId(paramDataCommand))));
    return localDataToken;
  }

  public static MyPodcasterDS getInstance()
  {
    if (instance == null)
      instance = new MyPodcasterDS();
    return instance;
  }

  private Integer getLatestProgramId(DataCommand paramDataCommand)
  {
    Map localMap = paramDataCommand.getParam();
    int i = ((Integer)localMap.get("pid")).intValue();
    String str = (String)localMap.get("ukey");
    try
    {
      SQLiteDatabase localSQLiteDatabase = DBManager.getInstance().getReadableDB("podcasterFollow");
      String[] arrayOfString = new String[2];
      arrayOfString[0] = String.valueOf(i);
      arrayOfString[1] = str;
      localCursor = localSQLiteDatabase.rawQuery("select lpId from myPodcaster where podcasterId=? AND userKey =?", arrayOfString);
      if (localCursor.moveToFirst())
      {
        int k = localCursor.getInt(0);
        j = k;
      }
    }
    catch (Exception localException1)
    {
      while (true)
      {
        try
        {
          Cursor localCursor;
          localCursor.close();
          return Integer.valueOf(j);
          localException1 = localException1;
          j = 0;
          continue;
        }
        catch (Exception localException2)
        {
          continue;
        }
        int j = 0;
      }
    }
  }

  private boolean updateFollowInfo(DataCommand paramDataCommand)
  {
    Map localMap = paramDataCommand.getParam();
    int i = ((Integer)localMap.get("pid")).intValue();
    String str = (String)localMap.get("ukey");
    long l1 = ((Long)localMap.get("uptime")).longValue();
    long l2 = System.currentTimeMillis();
    try
    {
      SQLiteDatabase localSQLiteDatabase = DBManager.getInstance().getWritableDB("podcasterFollow");
      Object[] arrayOfObject = new Object[4];
      arrayOfObject[0] = Integer.valueOf(i);
      arrayOfObject[1] = Long.valueOf(l2);
      arrayOfObject[2] = str;
      arrayOfObject[3] = Long.valueOf(l1);
      localSQLiteDatabase.execSQL("insert into myPodcaster (podcasterId,followTime,userKey,updateTime) values(?,?,?,?)", arrayOfObject);
      return true;
    }
    catch (Exception localException)
    {
    }
    return false;
  }

  private boolean updateLatestProgramId(DataCommand paramDataCommand)
  {
    Map localMap = paramDataCommand.getParam();
    int i = ((Integer)localMap.get("pid")).intValue();
    String str = (String)localMap.get("ukey");
    long l = ((Long)localMap.get("uptime")).longValue();
    try
    {
      SQLiteDatabase localSQLiteDatabase = DBManager.getInstance().getWritableDB("podcasterFollow");
      Object[] arrayOfObject = new Object[3];
      arrayOfObject[0] = Long.valueOf(l);
      arrayOfObject[1] = Integer.valueOf(i);
      arrayOfObject[2] = str;
      localSQLiteDatabase.execSQL("update myPodcaster set updateTime=? where podcasterId=? AND userKey =?", arrayOfObject);
      return true;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return false;
  }

  public void addParser(IDataParser paramIDataParser)
  {
  }

  public String dataSourceName()
  {
    return "MyPodcasterDS";
  }

  public IDataToken doCommand(DataCommand paramDataCommand, IDataRecvHandler paramIDataRecvHandler)
  {
    String str = paramDataCommand.getCurrentCommand();
    if (str.equalsIgnoreCase("GETDB_PODCASTER_FOLLOW_INFO"))
      return doAcquireCommand(paramDataCommand);
    if (str.equalsIgnoreCase("GETDB_ALL_PODCASTER_FOLLOW_INFO"))
      return doAcquireCommandAll(paramDataCommand);
    if (str.equalsIgnoreCase("INSERTDB_PODCASTER_FOLLOW_INFO"))
      return doInsertCommand(paramDataCommand);
    if (str.equalsIgnoreCase("DELETEDB_PODCASTER_FOLLOW_INFO"))
      return doDeleltCommand(paramDataCommand);
    if (str.equalsIgnoreCase("GETDB_PODCASTER_LATEST_PROGRAME"))
      return doGetLatestProgramId(paramDataCommand);
    if (str.equalsIgnoreCase("UPDATEDB_PODCASTER_LATEST_PROGRAME"))
      return doUpdateLatestProgramId(paramDataCommand);
    return null;
  }

  public boolean isSynchronous(String paramString, Map<String, Object> paramMap)
  {
    return true;
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.qtradio.data.MyPodcasterDS
 * JD-Core Version:    0.6.2
 */