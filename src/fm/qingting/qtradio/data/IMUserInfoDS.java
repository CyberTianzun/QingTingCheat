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
import fm.qingting.qtradio.room.SnsInfo;
import fm.qingting.qtradio.room.UserInfo;
import java.util.List;
import java.util.Map;

public class IMUserInfoDS
  implements IDataSource
{
  private static IMUserInfoDS instance;

  private UserInfo acquireIMUserInfo(DataCommand paramDataCommand)
  {
    while (true)
    {
      try
      {
        String str1 = (String)paramDataCommand.getParam().get("id");
        if (str1 == null)
          return null;
        String str2 = "select * from imUserInfo where id = '" + str1 + "'";
        Cursor localCursor = DBManager.getInstance().getReadableDB("imUserInfo").rawQuery(str2, null);
        if (localCursor.moveToNext())
        {
          localUserInfo = new UserInfo();
          int i = localCursor.getColumnIndex("avatar");
          int j = localCursor.getColumnIndex("id");
          int k = localCursor.getColumnIndex("name");
          int m = localCursor.getColumnIndex("gender");
          localUserInfo.userId = localCursor.getString(j);
          localUserInfo.userKey = localUserInfo.userId;
          localUserInfo.snsInfo.sns_name = localCursor.getString(k);
          localUserInfo.snsInfo.sns_avatar = localCursor.getString(i);
          localUserInfo.snsInfo.sns_gender = localCursor.getString(m);
          localCursor.close();
          return localUserInfo;
        }
      }
      catch (Exception localException)
      {
        return null;
      }
      UserInfo localUserInfo = null;
    }
  }

  private DataToken doAcquireUserCommand(DataCommand paramDataCommand)
  {
    DataToken localDataToken = new DataToken();
    localDataToken.setDataInfo(paramDataCommand);
    localDataToken.setData(new Result(true, acquireIMUserInfo(paramDataCommand)));
    return localDataToken;
  }

  private DataToken doUpdateUserCommand(DataCommand paramDataCommand)
  {
    DataToken localDataToken = new DataToken();
    localDataToken.setDataInfo(paramDataCommand);
    localDataToken.setData(new Result(true, Boolean.valueOf(updateIMUser(paramDataCommand))));
    return localDataToken;
  }

  public static IMUserInfoDS getInstance()
  {
    if (instance == null)
      instance = new IMUserInfoDS();
    return instance;
  }

  private boolean updateIMUser(DataCommand paramDataCommand)
  {
    Map localMap = paramDataCommand.getParam();
    String str1 = (String)localMap.get("type");
    if (str1 == null)
      return false;
    List localList;
    UserInfo localUserInfo;
    if (str1.equalsIgnoreCase("lu"))
    {
      localList = (List)localMap.get("lu");
      localUserInfo = null;
    }
    while (true)
    {
      try
      {
        SQLiteDatabase localSQLiteDatabase = DBManager.getInstance().getWritableDB("imUserInfo");
        if (localUserInfo != null)
        {
          String str2 = "delete from imUserInfo where id = '" + localUserInfo.userKey + "'";
          localSQLiteDatabase.beginTransaction();
          localSQLiteDatabase.execSQL(str2);
          Object[] arrayOfObject1 = new Object[4];
          arrayOfObject1[0] = localUserInfo.userKey;
          arrayOfObject1[1] = localUserInfo.snsInfo.sns_avatar;
          arrayOfObject1[2] = localUserInfo.snsInfo.sns_name;
          arrayOfObject1[3] = localUserInfo.snsInfo.sns_gender;
          localSQLiteDatabase.execSQL("insert into imUserInfo(id,avatar,name,gender) values(?,?,?,?)", arrayOfObject1);
          localSQLiteDatabase.setTransactionSuccessful();
          localSQLiteDatabase.endTransaction();
          return true;
          if (!str1.equalsIgnoreCase("u"))
            break label387;
          localUserInfo = (UserInfo)localMap.get("u");
          localList = null;
          continue;
        }
        if (localList == null)
          continue;
        int i = 0;
        if (i < localList.size())
        {
          String str3 = "delete from imUserInfo where id = '" + ((UserInfo)localList.get(i)).userKey + "'";
          localSQLiteDatabase.beginTransaction();
          localSQLiteDatabase.execSQL(str3);
          Object[] arrayOfObject2 = new Object[4];
          arrayOfObject2[0] = ((UserInfo)localList.get(i)).userKey;
          arrayOfObject2[1] = ((UserInfo)localList.get(i)).snsInfo.sns_avatar;
          arrayOfObject2[2] = ((UserInfo)localList.get(i)).snsInfo.sns_name;
          arrayOfObject2[3] = ((UserInfo)localList.get(i)).snsInfo.sns_gender;
          localSQLiteDatabase.execSQL("insert into imUserInfo(id,avatar,name,gender) values(?,?,?,?)", arrayOfObject2);
          i++;
          continue;
        }
        continue;
      }
      catch (Exception localException)
      {
        return false;
      }
      label387: localUserInfo = null;
      localList = null;
    }
  }

  public void addParser(IDataParser paramIDataParser)
  {
  }

  public String dataSourceName()
  {
    return "IMUserInfoDS";
  }

  public IDataToken doCommand(DataCommand paramDataCommand, IDataRecvHandler paramIDataRecvHandler)
  {
    String str = paramDataCommand.getCurrentCommand();
    if (str.equalsIgnoreCase("getdb_im_user_info"))
      return doAcquireUserCommand(paramDataCommand);
    if (str.equalsIgnoreCase("updatedb_im_user_info"))
      return doUpdateUserCommand(paramDataCommand);
    return null;
  }

  public boolean isSynchronous(String paramString, Map<String, Object> paramMap)
  {
    return true;
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.qtradio.data.IMUserInfoDS
 * JD-Core Version:    0.6.2
 */