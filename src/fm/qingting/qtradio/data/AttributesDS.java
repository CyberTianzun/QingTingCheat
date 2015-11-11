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
import java.util.List;
import java.util.Map;

public class AttributesDS
  implements IDataSource
{
  private static AttributesDS instance;

  // ERROR //
  private List<fm.qingting.qtradio.model.Attributes> acquireCategoryAttributes(DataCommand paramDataCommand)
  {
    // Byte code:
    //   0: aload_1
    //   1: invokevirtual 22	fm/qingting/framework/data/DataCommand:getParam	()Ljava/util/Map;
    //   4: ldc 24
    //   6: invokeinterface 30 2 0
    //   11: checkcast 32	java/lang/Integer
    //   14: invokevirtual 36	java/lang/Integer:intValue	()I
    //   17: istore_2
    //   18: new 38	java/util/ArrayList
    //   21: dup
    //   22: invokespecial 39	java/util/ArrayList:<init>	()V
    //   25: astore_3
    //   26: new 41	java/lang/StringBuilder
    //   29: dup
    //   30: invokespecial 42	java/lang/StringBuilder:<init>	()V
    //   33: ldc 44
    //   35: invokevirtual 48	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   38: ldc 50
    //   40: invokevirtual 48	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   43: iload_2
    //   44: invokevirtual 53	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
    //   47: ldc 55
    //   49: invokevirtual 48	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   52: invokevirtual 59	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   55: astore 5
    //   57: invokestatic 65	fm/qingting/qtradio/data/DBManager:getInstance	()Lfm/qingting/qtradio/data/DBManager;
    //   60: ldc 67
    //   62: invokevirtual 71	fm/qingting/qtradio/data/DBManager:getReadableDB	(Ljava/lang/String;)Landroid/database/sqlite/SQLiteDatabase;
    //   65: aload 5
    //   67: aconst_null
    //   68: invokevirtual 77	android/database/sqlite/SQLiteDatabase:rawQuery	(Ljava/lang/String;[Ljava/lang/String;)Landroid/database/Cursor;
    //   71: astore 6
    //   73: new 79	com/google/gson/Gson
    //   76: dup
    //   77: invokespecial 80	com/google/gson/Gson:<init>	()V
    //   80: astore 7
    //   82: aload 6
    //   84: invokeinterface 86 1 0
    //   89: ifeq +62 -> 151
    //   92: aload 6
    //   94: aload 6
    //   96: ldc 88
    //   98: invokeinterface 92 2 0
    //   103: invokeinterface 96 2 0
    //   108: astore 8
    //   110: aload 7
    //   112: aload 8
    //   114: ldc 98
    //   116: invokevirtual 102	com/google/gson/Gson:fromJson	(Ljava/lang/String;Ljava/lang/Class;)Ljava/lang/Object;
    //   119: checkcast 98	fm/qingting/qtradio/model/Attributes
    //   122: astore 10
    //   124: aload 10
    //   126: ifnull -44 -> 82
    //   129: aload_3
    //   130: aload 10
    //   132: invokeinterface 108 2 0
    //   137: pop
    //   138: goto -56 -> 82
    //   141: astore 9
    //   143: aload 9
    //   145: invokevirtual 111	java/lang/Exception:printStackTrace	()V
    //   148: goto -66 -> 82
    //   151: aload 6
    //   153: invokeinterface 114 1 0
    //   158: aload_3
    //   159: areturn
    //   160: astore 12
    //   162: aconst_null
    //   163: areturn
    //   164: astore 4
    //   166: aload_3
    //   167: areturn
    //
    // Exception table:
    //   from	to	target	type
    //   110	124	141	java/lang/Exception
    //   18	26	160	java/lang/Exception
    //   26	82	164	java/lang/Exception
    //   82	110	164	java/lang/Exception
    //   129	138	164	java/lang/Exception
    //   143	148	164	java/lang/Exception
    //   151	158	164	java/lang/Exception
  }

  private boolean deleteCategoryAttributes(DataCommand paramDataCommand)
  {
    try
    {
      DBManager.getInstance().getWritableDB("categoryAttributes").execSQL("delete from categoryAttributes");
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
    localDataToken.setData(new Result(true, acquireCategoryAttributes(paramDataCommand)));
    return localDataToken;
  }

  private DataToken doDeleteCommand(DataCommand paramDataCommand)
  {
    DataToken localDataToken = new DataToken();
    localDataToken.setDataInfo(paramDataCommand);
    localDataToken.setData(new Result(true, Boolean.valueOf(deleteCategoryAttributes(paramDataCommand))));
    return localDataToken;
  }

  private DataToken doUpdateCommand(DataCommand paramDataCommand)
  {
    DataToken localDataToken = new DataToken();
    localDataToken.setDataInfo(paramDataCommand);
    localDataToken.setData(new Result(true, Boolean.valueOf(updateCategoryAttributes(paramDataCommand))));
    return localDataToken;
  }

  public static AttributesDS getInstance()
  {
    if (instance == null)
      instance = new AttributesDS();
    return instance;
  }

  private boolean updateCategoryAttributes(DataCommand paramDataCommand)
  {
    Map localMap = paramDataCommand.getParam();
    List localList = (List)localMap.get("attrs");
    int i = ((Integer)localMap.get("catid")).intValue();
    if ((localList == null) || (localList.size() == 0))
      return false;
    try
    {
      String str1 = "delete from categoryAttributes" + " where catid = '" + i + "'";
      SQLiteDatabase localSQLiteDatabase = DBManager.getInstance().getWritableDB("categoryAttributes");
      localSQLiteDatabase.beginTransaction();
      localSQLiteDatabase.execSQL(str1);
      Gson localGson = new Gson();
      for (int j = 0; j < localList.size(); j++)
      {
        String str2 = localGson.toJson(localList.get(j));
        Object[] arrayOfObject = new Object[2];
        arrayOfObject[0] = Integer.valueOf(i);
        arrayOfObject[1] = str2;
        localSQLiteDatabase.execSQL("insert into categoryAttributes(catid,attrs) values(?,?)", arrayOfObject);
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
    return "AttributesDS";
  }

  public IDataToken doCommand(DataCommand paramDataCommand, IDataRecvHandler paramIDataRecvHandler)
  {
    String str = paramDataCommand.getCurrentCommand();
    if (str.equalsIgnoreCase("GETDB_CATEGORY_ATTRIBUTES"))
      return doAcquireCommand(paramDataCommand);
    if (str.equalsIgnoreCase("DELETEDB_CATEGORY_ATTRIBUTES"))
      return doDeleteCommand(paramDataCommand);
    if (str.equalsIgnoreCase("UPDATEDB_CATEGORY_ATTRIBUTES"))
      return doUpdateCommand(paramDataCommand);
    return null;
  }

  public boolean isSynchronous(String paramString, Map<String, Object> paramMap)
  {
    return true;
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.qtradio.data.AttributesDS
 * JD-Core Version:    0.6.2
 */