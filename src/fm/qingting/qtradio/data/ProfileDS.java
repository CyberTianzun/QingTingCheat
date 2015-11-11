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
import java.util.Map;

public class ProfileDS
  implements IDataSource
{
  private static ProfileDS instance;

  private DataToken doGetClientIDCommand(DataCommand paramDataCommand)
  {
    DataToken localDataToken = new DataToken();
    String str = getClientID();
    if (str != null);
    for (Result localResult = new Result(true, str); ; localResult = new Result(false, null))
    {
      localDataToken.setData(localResult);
      return localDataToken;
    }
  }

  private DataToken doGetValueCommand(DataCommand paramDataCommand)
  {
    DataToken localDataToken = new DataToken();
    String str = getValue((String)paramDataCommand.getParam().get("key"));
    if (str != null);
    for (Result localResult = new Result(true, str); ; localResult = new Result(false, null, "", ""))
    {
      localDataToken.setData(localResult);
      return localDataToken;
    }
  }

  private DataToken doUpdateClientIDCommand(DataCommand paramDataCommand)
  {
    DataToken localDataToken = new DataToken();
    updateClientID((String)paramDataCommand.getParam().get("clientid"));
    localDataToken.setData(new Result(true, null));
    return localDataToken;
  }

  private DataToken doUpdateValueCommand(DataCommand paramDataCommand)
  {
    DataToken localDataToken = new DataToken();
    Map localMap = paramDataCommand.getParam();
    updateValue((String)localMap.get("key"), (String)localMap.get("value"));
    localDataToken.setData(new Result(true, (String)localMap.get("value")));
    return localDataToken;
  }

  private String getClientID()
  {
    String str = null;
    try
    {
      Cursor localCursor = DBManager.getInstance().getReadableDB("profile").rawQuery("select key, value from profile", null);
      do
      {
        boolean bool = localCursor.moveToNext();
        str = null;
        if (!bool)
          break;
      }
      while (!localCursor.getString(0).equalsIgnoreCase("clientid"));
      str = localCursor.getString(1);
      localCursor.close();
      return str;
    }
    catch (Exception localException)
    {
    }
    return str;
  }

  public static ProfileDS getInstance()
  {
    if (instance == null)
      instance = new ProfileDS();
    return instance;
  }

  private String getValue(String paramString)
  {
    try
    {
      Object[] arrayOfObject = new Object[1];
      arrayOfObject[0] = paramString.replace("'", "''");
      String str1 = String.format("select key, value from profile where key='%s'", arrayOfObject);
      SQLiteDatabase localSQLiteDatabase = DBManager.getInstance().getReadableDB("profile");
      Cursor localCursor = localSQLiteDatabase.rawQuery(str1, null);
      if (localCursor.isAfterLast())
      {
        localCursor.close();
        localSQLiteDatabase.close();
        return null;
      }
      localCursor.moveToNext();
      String str2 = localCursor.getString(1);
      localCursor.close();
      return str2;
    }
    catch (Exception localException)
    {
    }
    return null;
  }

  private void updateClientID(String paramString)
  {
    for (int i = 1; ; i = 0)
      try
      {
        SQLiteDatabase localSQLiteDatabase = DBManager.getInstance().getWritableDB("profile");
        Cursor localCursor = localSQLiteDatabase.rawQuery("select key, value from profile where key='clientid'", null);
        if (!localCursor.isAfterLast())
        {
          localCursor.close();
          if (i != 0);
          String str;
          for (Object localObject = String.format("update profile set value = '%s' where key = 'clientid'", new Object[] { paramString }); ; localObject = str)
          {
            localSQLiteDatabase.execSQL((String)localObject);
            return;
            str = String.format("insert into profile(key, value) values('clientid', '%s')", new Object[] { paramString });
          }
        }
      }
      catch (Exception localException)
      {
        return;
      }
  }

  private void updateValue(String paramString1, String paramString2)
  {
    int i = 1;
    if (paramString1 == null)
      return;
    if (paramString2 == null)
      paramString2 = "";
    while (true)
    {
      try
      {
        Object[] arrayOfObject1 = new Object[1];
        arrayOfObject1[0] = paramString1.replace("'", "''");
        String str1 = String.format("select key, value from profile where key='%s'", arrayOfObject1);
        SQLiteDatabase localSQLiteDatabase = DBManager.getInstance().getWritableDB("profile");
        Cursor localCursor = localSQLiteDatabase.rawQuery(str1, null);
        if (!localCursor.isAfterLast())
        {
          localCursor.close();
          if (i != 0)
          {
            Object[] arrayOfObject2 = new Object[2];
            arrayOfObject2[0] = paramString2.replace("'", "''");
            arrayOfObject2[1] = paramString1.replace("'", "''");
            localObject = String.format("update profile set value = '%s' where key = '%s'", arrayOfObject2);
            localSQLiteDatabase.execSQL((String)localObject);
            return;
          }
          Object[] arrayOfObject3 = new Object[2];
          arrayOfObject3[0] = paramString1.replace("'", "''");
          arrayOfObject3[1] = paramString2.replace("'", "''");
          String str2 = String.format("insert into profile(key, value) values('%s', '%s')", arrayOfObject3);
          Object localObject = str2;
          continue;
        }
      }
      catch (Exception localException)
      {
        return;
      }
      i = 0;
    }
  }

  public void addParser(IDataParser paramIDataParser)
  {
  }

  public String dataSourceName()
  {
    return "Profile";
  }

  public IDataToken doCommand(DataCommand paramDataCommand, IDataRecvHandler paramIDataRecvHandler)
  {
    String str = paramDataCommand.getCurrentCommand();
    if (str.equalsIgnoreCase("getClientID"))
      return doGetClientIDCommand(paramDataCommand);
    if (str.equalsIgnoreCase("updateClientID"))
      return doUpdateClientIDCommand(paramDataCommand);
    if (str.equalsIgnoreCase("getValue"))
      return doGetValueCommand(paramDataCommand);
    if (str.equalsIgnoreCase("updateValue"))
      return doUpdateValueCommand(paramDataCommand);
    return null;
  }

  public boolean isSynchronous(String paramString, Map<String, Object> paramMap)
  {
    return true;
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.qtradio.data.ProfileDS
 * JD-Core Version:    0.6.2
 */