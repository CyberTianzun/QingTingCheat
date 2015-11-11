package fm.qingting.qtradio.pushcontent;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import fm.qingting.framework.data.DBHelper;
import fm.qingting.utils.LifeTime;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class ChannelTimingDB
{
  private static final String ChannelID = "channelId";
  private static final String TableName = "ChannelTimingDB";
  private static final String UpdateTime = "update_time";
  private static final String ViewTime = "view_time";
  private Context _context;
  private DBHelper helper;

  public ChannelTimingDB(Context paramContext)
  {
    this._context = paramContext;
    create();
  }

  private void create()
  {
    if (this._context == null)
      return;
    HashMap localHashMap1 = new HashMap();
    HashMap localHashMap2 = new HashMap();
    localHashMap2.put("channelId", "varchar(30)");
    localHashMap2.put("update_time", "integer");
    localHashMap2.put("view_time", "integer");
    localHashMap1.put("ChannelTimingDB", localHashMap2);
    this.helper = new DBHelper(localHashMap1, null, this._context, "ChannelTimingDB", null, 1, null);
  }

  private static void log(String paramString)
  {
  }

  public Map<String, TimeBean> load()
  {
    HashMap localHashMap = new HashMap();
    if (LifeTime.isFirstLaunchAfterInstall)
      return localHashMap;
    SQLiteDatabase localSQLiteDatabase = this.helper.getReadableDatabase();
    Cursor localCursor;
    try
    {
      localCursor = localSQLiteDatabase.rawQuery("select * from ChannelTimingDB", null);
      while (localCursor.moveToNext())
        localHashMap.put(localCursor.getString(localCursor.getColumnIndex("channelId")), new TimeBean(localCursor.getLong(localCursor.getColumnIndex("view_time")), localCursor.getLong(localCursor.getColumnIndex("update_time"))));
    }
    catch (Exception localException)
    {
      log("[load failed]:" + localException.getLocalizedMessage());
      return localHashMap;
    }
    log("[load] " + localHashMap.size() + " items");
    localCursor.close();
    localSQLiteDatabase.close();
    return localHashMap;
  }

  public void save(Map<String, TimeBean> paramMap)
  {
    if (paramMap == null)
    {
      log("[Error]empty data");
      return;
    }
    SQLiteDatabase localSQLiteDatabase = this.helper.getWritableDatabase();
    try
    {
      localSQLiteDatabase.beginTransaction();
      localSQLiteDatabase.execSQL("delete from ChannelTimingDB");
      Iterator localIterator = paramMap.keySet().iterator();
      while (localIterator.hasNext())
      {
        String str = (String)localIterator.next();
        TimeBean localTimeBean = (TimeBean)paramMap.get(str);
        Object[] arrayOfObject = new Object[3];
        arrayOfObject[0] = str;
        arrayOfObject[1] = Long.valueOf(localTimeBean.getViewTime());
        arrayOfObject[2] = Long.valueOf(localTimeBean.getUpdateTime());
        localSQLiteDatabase.execSQL("insert into ChannelTimingDB(channelId, view_time, update_time) values(?, ?, ?)", arrayOfObject);
      }
    }
    catch (Exception localException)
    {
      log("[save failed]:" + localException.getLocalizedMessage());
      return;
    }
    localSQLiteDatabase.setTransactionSuccessful();
    localSQLiteDatabase.endTransaction();
    localSQLiteDatabase.close();
    log("[save] " + paramMap.size() + " items");
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.qtradio.pushcontent.ChannelTimingDB
 * JD-Core Version:    0.6.2
 */