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
import fm.qingting.qtradio.model.CategoryNode;
import fm.qingting.qtradio.model.Node;
import java.util.List;
import java.util.Map;

public class CategoryNodeDS
  implements IDataSource
{
  private static CategoryNodeDS instance;

  // ERROR //
  private List<Node> acquireCategoryNodes(DataCommand paramDataCommand)
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
    //   89: ifeq +68 -> 157
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
    //   119: checkcast 98	fm/qingting/qtradio/model/CategoryNode
    //   122: astore 10
    //   124: aload 10
    //   126: ifnull -44 -> 82
    //   129: aload 10
    //   131: iload_2
    //   132: putfield 106	fm/qingting/qtradio/model/CategoryNode:parentId	I
    //   135: aload_3
    //   136: aload 10
    //   138: invokeinterface 112 2 0
    //   143: pop
    //   144: goto -62 -> 82
    //   147: astore 9
    //   149: aload 9
    //   151: invokevirtual 115	java/lang/Exception:printStackTrace	()V
    //   154: goto -72 -> 82
    //   157: aload 6
    //   159: invokeinterface 118 1 0
    //   164: aload_3
    //   165: areturn
    //   166: astore 12
    //   168: aconst_null
    //   169: areturn
    //   170: astore 4
    //   172: aload_3
    //   173: areturn
    //
    // Exception table:
    //   from	to	target	type
    //   110	124	147	java/lang/Exception
    //   129	144	147	java/lang/Exception
    //   18	26	166	java/lang/Exception
    //   26	82	170	java/lang/Exception
    //   82	110	170	java/lang/Exception
    //   149	154	170	java/lang/Exception
    //   157	164	170	java/lang/Exception
  }

  private boolean deleteCategoryNodes(DataCommand paramDataCommand)
  {
    try
    {
      DBManager.getInstance().getWritableDB("categoryNodes").execSQL("delete from categoryNodes");
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
    localDataToken.setData(new Result(true, acquireCategoryNodes(paramDataCommand)));
    return localDataToken;
  }

  private DataToken doDeleteCommand(DataCommand paramDataCommand)
  {
    DataToken localDataToken = new DataToken();
    localDataToken.setDataInfo(paramDataCommand);
    localDataToken.setData(new Result(true, Boolean.valueOf(deleteCategoryNodes(paramDataCommand))));
    return localDataToken;
  }

  private DataToken doInsertCommand(DataCommand paramDataCommand)
  {
    DataToken localDataToken = new DataToken();
    localDataToken.setDataInfo(paramDataCommand);
    localDataToken.setData(new Result(true, Boolean.valueOf(insertCategoryNodes(paramDataCommand))));
    return localDataToken;
  }

  private DataToken doUpdateCommand(DataCommand paramDataCommand)
  {
    DataToken localDataToken = new DataToken();
    localDataToken.setDataInfo(paramDataCommand);
    localDataToken.setData(new Result(true, Boolean.valueOf(updateCategoryNodes(paramDataCommand))));
    return localDataToken;
  }

  public static CategoryNodeDS getInstance()
  {
    if (instance == null)
      instance = new CategoryNodeDS();
    return instance;
  }

  private boolean insertCategoryNodes(DataCommand paramDataCommand)
  {
    List localList = (List)paramDataCommand.getParam().get("nodes");
    if ((localList == null) || (localList.size() == 0))
      return false;
    try
    {
      SQLiteDatabase localSQLiteDatabase = DBManager.getInstance().getWritableDB("categoryNodes");
      localSQLiteDatabase.beginTransaction();
      Gson localGson = new Gson();
      for (int i = 0; ; i++)
        if (i < localList.size())
        {
          Node localNode = (Node)localList.get(i);
          if (localNode.nodeName.equalsIgnoreCase("category"))
          {
            String str = localGson.toJson(localNode);
            Object[] arrayOfObject = new Object[3];
            arrayOfObject[0] = Integer.valueOf(((CategoryNode)localNode).categoryId);
            arrayOfObject[1] = Integer.valueOf(((CategoryNode)localNode).parentId);
            arrayOfObject[2] = str;
            localSQLiteDatabase.execSQL("insert into categoryNodes(id,parentId,categoryNode) values(?,?,?)", arrayOfObject);
          }
        }
        else
        {
          localSQLiteDatabase.setTransactionSuccessful();
          localSQLiteDatabase.endTransaction();
          return true;
        }
    }
    catch (Exception localException)
    {
    }
    return false;
  }

  private boolean updateCategoryNodes(DataCommand paramDataCommand)
  {
    Map localMap = paramDataCommand.getParam();
    List localList = (List)localMap.get("nodes");
    int i = ((Integer)localMap.get("parentid")).intValue();
    if ((localList == null) || (localList.size() == 0))
      return false;
    try
    {
      String str1 = "delete from categoryNodes" + " where parentId = '" + i + "'";
      SQLiteDatabase localSQLiteDatabase = DBManager.getInstance().getWritableDB("categoryNodes");
      localSQLiteDatabase.beginTransaction();
      localSQLiteDatabase.execSQL(str1);
      Gson localGson = new Gson();
      for (int j = 0; j < localList.size(); j++)
      {
        Node localNode = (Node)localList.get(j);
        String str2 = localGson.toJson(localNode);
        Object[] arrayOfObject = new Object[3];
        arrayOfObject[0] = Integer.valueOf(((CategoryNode)localNode).categoryId);
        arrayOfObject[1] = Integer.valueOf(i);
        arrayOfObject[2] = str2;
        localSQLiteDatabase.execSQL("insert into categoryNodes(id,parentId,categoryNode) values(?,?,?)", arrayOfObject);
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
    return "CategoryNodeDS";
  }

  public IDataToken doCommand(DataCommand paramDataCommand, IDataRecvHandler paramIDataRecvHandler)
  {
    String str = paramDataCommand.getCurrentCommand();
    if (str.equalsIgnoreCase("insertdb_category_node"))
      return doInsertCommand(paramDataCommand);
    if (str.equalsIgnoreCase("getdb_category_node"))
      return doAcquireCommand(paramDataCommand);
    if (str.equalsIgnoreCase("deletedb_category_node"))
      return doDeleteCommand(paramDataCommand);
    if (str.equalsIgnoreCase("updatedb_category_node"))
      return doUpdateCommand(paramDataCommand);
    return null;
  }

  public boolean isSynchronous(String paramString, Map<String, Object> paramMap)
  {
    return true;
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.qtradio.data.CategoryNodeDS
 * JD-Core Version:    0.6.2
 */