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
import fm.qingting.qtradio.social.UserProfile;
import java.util.List;
import java.util.Map;

public class SocialUserProfileDS
  implements IDataSource
{
  private static SocialUserProfileDS instance;

  private UserProfile acquireUserInfo(DataCommand paramDataCommand)
  {
    try
    {
      String str1 = (String)paramDataCommand.getParam().get("userKey");
      if (str1 != null)
      {
        if (str1.equalsIgnoreCase(""))
          return null;
        String str2 = "select * from socialUserProfile where userKey = '" + str1 + "'";
        Cursor localCursor = DBManager.getInstance().getReadableDB("socialuser").rawQuery(str2, null);
        Gson localGson = new Gson();
        String str3;
        if (localCursor.moveToNext())
          str3 = localCursor.getString(localCursor.getColumnIndex("userKey"));
        while (true)
        {
          try
          {
            localUserProfile = (UserProfile)localGson.fromJson(str3, UserProfile.class);
            localCursor.close();
            return localUserProfile;
          }
          catch (Exception localException2)
          {
            localUserProfile = null;
            continue;
          }
          UserProfile localUserProfile = null;
        }
      }
    }
    catch (Exception localException1)
    {
    }
    return null;
  }

  private boolean deleteUserInfo(DataCommand paramDataCommand)
  {
    String str1 = (String)paramDataCommand.getParam().get("userKey");
    Object localObject;
    if (str1 == null)
      localObject = "delete from socialUserProfile";
    try
    {
      while (true)
      {
        DBManager.getInstance().getWritableDB("socialuser").execSQL((String)localObject);
        return true;
        String str2 = "delete from socialUserProfile where userKey = '" + str1 + "'";
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

  public static SocialUserProfileDS getInstance()
  {
    if (instance == null)
      instance = new SocialUserProfileDS();
    return instance;
  }

  private boolean insertUserInfo(DataCommand paramDataCommand)
  {
    List localList = (List)paramDataCommand.getParam().get("users");
    if ((localList == null) || (localList.size() == 0))
      return false;
    try
    {
      SQLiteDatabase localSQLiteDatabase = DBManager.getInstance().getWritableDB("userinfos");
      localSQLiteDatabase.beginTransaction();
      Gson localGson = new Gson();
      for (int i = 0; i < localList.size(); i++)
      {
        String str = localGson.toJson(((UserProfile)localList.get(i)).getUserInfo());
        Object[] arrayOfObject = new Object[2];
        arrayOfObject[0] = ((UserProfile)localList.get(i)).getUserKey();
        arrayOfObject[1] = str;
        localSQLiteDatabase.execSQL("insert into socialUserProfile(userKey,userProfile) values(?,?)", arrayOfObject);
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
    return "SocialUserProfileDS";
  }

  public IDataToken doCommand(DataCommand paramDataCommand, IDataRecvHandler paramIDataRecvHandler)
  {
    String str = paramDataCommand.getCurrentCommand();
    if (str.equalsIgnoreCase("insertdb_social_user_info"))
      return doInsertCommand(paramDataCommand);
    if (str.equalsIgnoreCase("getdb_social_user_info"))
      return doAcquireCommand(paramDataCommand);
    if (str.equalsIgnoreCase("deletedb_social_user_info"))
      return doDeleteCommand(paramDataCommand);
    return null;
  }

  public boolean isSynchronous(String paramString, Map<String, Object> paramMap)
  {
    return true;
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.qtradio.data.SocialUserProfileDS
 * JD-Core Version:    0.6.2
 */