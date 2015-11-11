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
import java.util.Map;

public class UserInfoDS
  implements IDataSource
{
  private static UserInfoDS instance;

  private UserInfo acquireUserInfo(DataCommand paramDataCommand)
  {
    while (true)
    {
      try
      {
        String str1 = (String)paramDataCommand.getParam().get("site");
        if ((str1 == null) || (str1.equalsIgnoreCase("")))
          break;
        String str2 = "select * from userInfos where sns_site = '" + str1 + "'";
        Cursor localCursor = DBManager.getInstance().getReadableDB("userinfos").rawQuery(str2, null);
        localUserInfo = new UserInfo();
        if (localCursor.moveToNext())
        {
          int i = localCursor.getColumnIndex("sns_id");
          int j = localCursor.getColumnIndex("sns_site");
          int k = localCursor.getColumnIndex("sns_name");
          int m = localCursor.getColumnIndex("sns_account");
          int n = localCursor.getColumnIndex("sns_avatar");
          localUserInfo.snsInfo.sns_id = localCursor.getString(i);
          localUserInfo.snsInfo.sns_site = localCursor.getString(j);
          localUserInfo.snsInfo.sns_name = localCursor.getString(k);
          localUserInfo.snsInfo.sns_account = localCursor.getString(m);
          localUserInfo.snsInfo.sns_avatar = localCursor.getString(n);
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
    return null;
  }

  private boolean deleteUserInfo(DataCommand paramDataCommand)
  {
    String str1 = (String)paramDataCommand.getParam().get("site");
    Object localObject;
    if (str1 == null)
      localObject = "delete from userInfos";
    try
    {
      while (true)
      {
        DBManager.getInstance().getWritableDB("userinfos").execSQL((String)localObject);
        return true;
        String str2 = "delete from userInfos where sns_site = '" + str1 + "'";
        localObject = str2;
      }
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
    localDataToken.setData(new Result(true, acquireUserInfo(paramDataCommand)));
    return localDataToken;
  }

  private DataToken doDeleteCommand(DataCommand paramDataCommand)
  {
    DataToken localDataToken = new DataToken();
    localDataToken.setDataInfo(paramDataCommand);
    localDataToken.setData(new Result(true, Boolean.valueOf(deleteUserInfo(paramDataCommand))));
    return localDataToken;
  }

  private DataToken doInsertCommand(DataCommand paramDataCommand)
  {
    DataToken localDataToken = new DataToken();
    localDataToken.setDataInfo(paramDataCommand);
    localDataToken.setData(new Result(true, Boolean.valueOf(insertUserInfo(paramDataCommand))));
    return localDataToken;
  }

  public static UserInfoDS getInstance()
  {
    if (instance == null)
      instance = new UserInfoDS();
    return instance;
  }

  private boolean insertUserInfo(DataCommand paramDataCommand)
  {
    UserInfo localUserInfo = (UserInfo)paramDataCommand.getParam().get("userInfo");
    if (localUserInfo == null)
      return false;
    try
    {
      SQLiteDatabase localSQLiteDatabase = DBManager.getInstance().getWritableDB("userinfos");
      Object[] arrayOfObject = new Object[5];
      arrayOfObject[0] = localUserInfo.snsInfo.sns_id;
      arrayOfObject[1] = localUserInfo.snsInfo.sns_site;
      arrayOfObject[2] = localUserInfo.snsInfo.sns_name;
      arrayOfObject[3] = localUserInfo.snsInfo.sns_account;
      arrayOfObject[4] = localUserInfo.snsInfo.sns_avatar;
      localSQLiteDatabase.execSQL("insert into userInfos(sns_id,sns_site,sns_name,sns_account,sns_avatar) values(?,?,?,?,?)", arrayOfObject);
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
    return "UserInfoDS";
  }

  public IDataToken doCommand(DataCommand paramDataCommand, IDataRecvHandler paramIDataRecvHandler)
  {
    String str = paramDataCommand.getCurrentCommand();
    if (str.equalsIgnoreCase("insertdb_user_info"))
      return doInsertCommand(paramDataCommand);
    if (str.equalsIgnoreCase("getdb_user_info"))
      return doAcquireCommand(paramDataCommand);
    if (str.equalsIgnoreCase("deletedb_user_info"))
      return doDeleteCommand(paramDataCommand);
    return null;
  }

  public boolean isSynchronous(String paramString, Map<String, Object> paramMap)
  {
    return true;
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.qtradio.data.UserInfoDS
 * JD-Core Version:    0.6.2
 */