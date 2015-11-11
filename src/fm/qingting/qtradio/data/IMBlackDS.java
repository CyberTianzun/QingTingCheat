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
import fm.qingting.qtradio.im.info.GroupInfo;
import fm.qingting.qtradio.room.UserInfo;
import java.util.List;
import java.util.Map;

public class IMBlackDS
  implements IDataSource
{
  private static IMBlackDS instance;

  // ERROR //
  private List<GroupInfo> acquireGroupIMConstacts(DataCommand paramDataCommand)
  {
    // Byte code:
    //   0: aload_1
    //   1: invokevirtual 22	fm/qingting/framework/data/DataCommand:getParam	()Ljava/util/Map;
    //   4: ldc 24
    //   6: invokeinterface 30 2 0
    //   11: checkcast 32	java/lang/String
    //   14: astore_3
    //   15: aload_3
    //   16: ifnonnull +5 -> 21
    //   19: aconst_null
    //   20: areturn
    //   21: new 34	java/util/ArrayList
    //   24: dup
    //   25: invokespecial 35	java/util/ArrayList:<init>	()V
    //   28: astore 4
    //   30: new 37	java/lang/StringBuilder
    //   33: dup
    //   34: invokespecial 38	java/lang/StringBuilder:<init>	()V
    //   37: ldc 40
    //   39: invokevirtual 44	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   42: aload_3
    //   43: invokevirtual 44	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   46: ldc 46
    //   48: invokevirtual 44	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   51: invokevirtual 50	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   54: astore 5
    //   56: invokestatic 56	fm/qingting/qtradio/data/DBManager:getInstance	()Lfm/qingting/qtradio/data/DBManager;
    //   59: ldc 58
    //   61: invokevirtual 62	fm/qingting/qtradio/data/DBManager:getReadableDB	(Ljava/lang/String;)Landroid/database/sqlite/SQLiteDatabase;
    //   64: aload 5
    //   66: aconst_null
    //   67: invokevirtual 68	android/database/sqlite/SQLiteDatabase:rawQuery	(Ljava/lang/String;[Ljava/lang/String;)Landroid/database/Cursor;
    //   70: astore 6
    //   72: new 70	com/google/gson/Gson
    //   75: dup
    //   76: invokespecial 71	com/google/gson/Gson:<init>	()V
    //   79: astore 7
    //   81: aconst_null
    //   82: astore 8
    //   84: aload 6
    //   86: invokeinterface 77 1 0
    //   91: ifeq +53 -> 144
    //   94: aload 6
    //   96: aload 6
    //   98: ldc 79
    //   100: invokeinterface 83 2 0
    //   105: invokeinterface 87 2 0
    //   110: astore 9
    //   112: aload 7
    //   114: aload 9
    //   116: ldc 89
    //   118: invokevirtual 93	com/google/gson/Gson:fromJson	(Ljava/lang/String;Ljava/lang/Class;)Ljava/lang/Object;
    //   121: checkcast 89	fm/qingting/qtradio/im/info/GroupInfo
    //   124: astore 11
    //   126: aload 11
    //   128: ifnull +29 -> 157
    //   131: aload 4
    //   133: aload 11
    //   135: invokeinterface 99 2 0
    //   140: pop
    //   141: goto +16 -> 157
    //   144: aload 6
    //   146: invokeinterface 102 1 0
    //   151: aload 4
    //   153: areturn
    //   154: astore_2
    //   155: aconst_null
    //   156: areturn
    //   157: aload 11
    //   159: astore 8
    //   161: goto -77 -> 84
    //   164: astore 10
    //   166: aload 8
    //   168: astore 11
    //   170: goto -44 -> 126
    //
    // Exception table:
    //   from	to	target	type
    //   0	15	154	java/lang/Exception
    //   21	81	154	java/lang/Exception
    //   84	112	154	java/lang/Exception
    //   131	141	154	java/lang/Exception
    //   144	151	154	java/lang/Exception
    //   112	126	164	java/lang/Exception
  }

  // ERROR //
  private List<UserInfo> acquireUserIMConstacts(DataCommand paramDataCommand)
  {
    // Byte code:
    //   0: aload_1
    //   1: invokevirtual 22	fm/qingting/framework/data/DataCommand:getParam	()Ljava/util/Map;
    //   4: ldc 24
    //   6: invokeinterface 30 2 0
    //   11: checkcast 32	java/lang/String
    //   14: astore_3
    //   15: aload_3
    //   16: ifnonnull +5 -> 21
    //   19: aconst_null
    //   20: areturn
    //   21: new 34	java/util/ArrayList
    //   24: dup
    //   25: invokespecial 35	java/util/ArrayList:<init>	()V
    //   28: astore 4
    //   30: new 37	java/lang/StringBuilder
    //   33: dup
    //   34: invokespecial 38	java/lang/StringBuilder:<init>	()V
    //   37: ldc 40
    //   39: invokevirtual 44	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   42: aload_3
    //   43: invokevirtual 44	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   46: ldc 46
    //   48: invokevirtual 44	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   51: invokevirtual 50	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   54: astore 5
    //   56: invokestatic 56	fm/qingting/qtradio/data/DBManager:getInstance	()Lfm/qingting/qtradio/data/DBManager;
    //   59: ldc 58
    //   61: invokevirtual 62	fm/qingting/qtradio/data/DBManager:getReadableDB	(Ljava/lang/String;)Landroid/database/sqlite/SQLiteDatabase;
    //   64: aload 5
    //   66: aconst_null
    //   67: invokevirtual 68	android/database/sqlite/SQLiteDatabase:rawQuery	(Ljava/lang/String;[Ljava/lang/String;)Landroid/database/Cursor;
    //   70: astore 6
    //   72: new 70	com/google/gson/Gson
    //   75: dup
    //   76: invokespecial 71	com/google/gson/Gson:<init>	()V
    //   79: astore 7
    //   81: aconst_null
    //   82: astore 8
    //   84: aload 6
    //   86: invokeinterface 77 1 0
    //   91: ifeq +102 -> 193
    //   94: aload 6
    //   96: ldc 79
    //   98: invokeinterface 83 2 0
    //   103: istore 9
    //   105: aload 6
    //   107: ldc 105
    //   109: invokeinterface 83 2 0
    //   114: istore 10
    //   116: aload 6
    //   118: iload 9
    //   120: invokeinterface 87 2 0
    //   125: astore 11
    //   127: aload 6
    //   129: iload 10
    //   131: invokeinterface 87 2 0
    //   136: astore 12
    //   138: aload 7
    //   140: aload 11
    //   142: ldc 107
    //   144: invokevirtual 93	com/google/gson/Gson:fromJson	(Ljava/lang/String;Ljava/lang/Class;)Ljava/lang/Object;
    //   147: checkcast 107	fm/qingting/qtradio/room/SnsInfo
    //   150: astore 14
    //   152: aload 14
    //   154: ifnull +52 -> 206
    //   157: new 109	fm/qingting/qtradio/room/UserInfo
    //   160: dup
    //   161: invokespecial 110	fm/qingting/qtradio/room/UserInfo:<init>	()V
    //   164: astore 15
    //   166: aload 15
    //   168: aload 12
    //   170: putfield 114	fm/qingting/qtradio/room/UserInfo:userKey	Ljava/lang/String;
    //   173: aload 15
    //   175: aload 14
    //   177: putfield 118	fm/qingting/qtradio/room/UserInfo:snsInfo	Lfm/qingting/qtradio/room/SnsInfo;
    //   180: aload 4
    //   182: aload 15
    //   184: invokeinterface 99 2 0
    //   189: pop
    //   190: goto +16 -> 206
    //   193: aload 6
    //   195: invokeinterface 102 1 0
    //   200: aload 4
    //   202: areturn
    //   203: astore_2
    //   204: aconst_null
    //   205: areturn
    //   206: aload 14
    //   208: astore 8
    //   210: goto -126 -> 84
    //   213: astore 13
    //   215: aload 8
    //   217: astore 14
    //   219: goto -67 -> 152
    //
    // Exception table:
    //   from	to	target	type
    //   0	15	203	java/lang/Exception
    //   21	81	203	java/lang/Exception
    //   84	138	203	java/lang/Exception
    //   157	190	203	java/lang/Exception
    //   193	200	203	java/lang/Exception
    //   138	152	213	java/lang/Exception
  }

  private boolean deleteIMConstacts(DataCommand paramDataCommand)
  {
    try
    {
      DBManager.getInstance().getWritableDB("imContacts").execSQL("delete from imContacts");
      return true;
    }
    catch (Exception localException)
    {
    }
    return false;
  }

  private DataToken doAcquireUserCommand(DataCommand paramDataCommand)
  {
    DataToken localDataToken = new DataToken();
    localDataToken.setDataInfo(paramDataCommand);
    localDataToken.setData(new Result(true, acquireUserIMConstacts(paramDataCommand)));
    return localDataToken;
  }

  private DataToken doUpdateUserCommand(DataCommand paramDataCommand)
  {
    DataToken localDataToken = new DataToken();
    localDataToken.setDataInfo(paramDataCommand);
    localDataToken.setData(new Result(true, Boolean.valueOf(updateIMConstacts(paramDataCommand))));
    return localDataToken;
  }

  public static IMBlackDS getInstance()
  {
    if (instance == null)
      instance = new IMBlackDS();
    return instance;
  }

  private boolean insertIMConstacts(DataCommand paramDataCommand)
  {
    Map localMap = paramDataCommand.getParam();
    String str1 = (String)localMap.get("type");
    if (str1 == null)
      return false;
    UserInfo localUserInfo;
    Object localObject;
    if (str1.equalsIgnoreCase("ru"))
    {
      localUserInfo = (UserInfo)localMap.get("ru");
      localObject = null;
    }
    while (true)
    {
      try
      {
        SQLiteDatabase localSQLiteDatabase = DBManager.getInstance().getWritableDB("imContacts");
        localSQLiteDatabase.beginTransaction();
        Gson localGson = new Gson();
        if (localUserInfo != null)
        {
          String str2 = localGson.toJson(localUserInfo.snsInfo);
          Object[] arrayOfObject1 = new Object[3];
          arrayOfObject1[0] = str1;
          arrayOfObject1[1] = str2;
          arrayOfObject1[2] = localUserInfo.userKey;
          localSQLiteDatabase.execSQL("insert into imContacts(type,info,key) values(?,?,?)", arrayOfObject1);
        }
        if (localObject != null)
        {
          String str3 = localGson.toJson(localObject);
          Object[] arrayOfObject2 = new Object[3];
          arrayOfObject2[0] = str1;
          arrayOfObject2[1] = str3;
          arrayOfObject2[2] = ((GroupInfo)localObject).groupId;
          localSQLiteDatabase.execSQL("insert into imContacts(type,info,key) values(?,?,?)", arrayOfObject2);
        }
        localSQLiteDatabase.setTransactionSuccessful();
        localSQLiteDatabase.endTransaction();
        return true;
        if (str1.equalsIgnoreCase("rg"))
        {
          localObject = (GroupInfo)localMap.get("rg");
          localUserInfo = null;
          continue;
        }
        if (str1.equalsIgnoreCase("wu"))
        {
          localUserInfo = (UserInfo)localMap.get("wu");
          localObject = null;
          continue;
        }
        if (str1.equalsIgnoreCase("wg"))
        {
          localObject = (GroupInfo)localMap.get("wg");
          localUserInfo = null;
          continue;
        }
      }
      catch (Exception localException)
      {
        return false;
      }
      localObject = null;
      localUserInfo = null;
    }
  }

  private boolean updateIMConstacts(DataCommand paramDataCommand)
  {
    Map localMap = paramDataCommand.getParam();
    String str1 = (String)localMap.get("type");
    if (str1 == null)
      return false;
    List localList3;
    List localList1;
    if (str1.equalsIgnoreCase("ru"))
    {
      localList3 = (List)localMap.get("ru");
      localList1 = null;
    }
    for (List localList2 = localList3; ; localList2 = null)
    {
      try
      {
        SQLiteDatabase localSQLiteDatabase;
        while (true)
        {
          localSQLiteDatabase = DBManager.getInstance().getWritableDB("imContacts");
          String str2 = "delete from imContacts where type = '" + str1 + "'";
          localSQLiteDatabase.beginTransaction();
          localSQLiteDatabase.execSQL(str2);
          if (localList2 == null)
            break;
          for (int j = 0; j < localList2.size(); j++)
          {
            String str4 = new Gson().toJson(((UserInfo)localList2.get(j)).snsInfo);
            Object[] arrayOfObject2 = new Object[3];
            arrayOfObject2[0] = str1;
            arrayOfObject2[1] = str4;
            arrayOfObject2[2] = ((UserInfo)localList2.get(j)).userKey;
            localSQLiteDatabase.execSQL("insert into imContacts(type,info,key) values(?,?,?)", arrayOfObject2);
          }
          if (str1.equalsIgnoreCase("rg"))
          {
            localList1 = (List)localMap.get("rg");
            localList2 = null;
          }
          else if (str1.equalsIgnoreCase("wu"))
          {
            localList2 = (List)localMap.get("wu");
            localList1 = null;
          }
          else
          {
            if (!str1.equalsIgnoreCase("wg"))
              break label390;
            localList1 = (List)localMap.get("wg");
            localList2 = null;
          }
        }
        if (localList1 != null)
          for (int i = 0; i < localList1.size(); i++)
          {
            String str3 = new Gson().toJson(localList1.get(i));
            Object[] arrayOfObject1 = new Object[3];
            arrayOfObject1[0] = str1;
            arrayOfObject1[1] = str3;
            arrayOfObject1[2] = ((GroupInfo)localList1.get(i)).groupId;
            localSQLiteDatabase.execSQL("insert into imContacts(type,info,key) values(?,?,?)", arrayOfObject1);
          }
        localSQLiteDatabase.setTransactionSuccessful();
        localSQLiteDatabase.endTransaction();
        return true;
      }
      catch (Exception localException)
      {
        return false;
      }
      label390: localList1 = null;
    }
  }

  public void addParser(IDataParser paramIDataParser)
  {
  }

  public String dataSourceName()
  {
    return "IMBlackDS";
  }

  public IDataToken doCommand(DataCommand paramDataCommand, IDataRecvHandler paramIDataRecvHandler)
  {
    String str = paramDataCommand.getCurrentCommand();
    if (str.equalsIgnoreCase("getdb_im_black_user"))
      return doAcquireUserCommand(paramDataCommand);
    if (str.equalsIgnoreCase("updatedb_im_black_user"))
      return doUpdateUserCommand(paramDataCommand);
    return null;
  }

  public boolean isSynchronous(String paramString, Map<String, Object> paramMap)
  {
    return true;
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.qtradio.data.IMBlackDS
 * JD-Core Version:    0.6.2
 */