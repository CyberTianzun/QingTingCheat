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
import fm.qingting.qtradio.model.CategoryNode;
import fm.qingting.qtradio.model.ChannelNode;
import fm.qingting.qtradio.model.Node;
import java.util.List;
import java.util.Map;

public class AllFavCategoryDS
  implements IDataSource
{
  private static AllFavCategoryDS instance;

  private List<Node> acquireAllNodes(DataCommand paramDataCommand)
  {
    List localList = acquireCategoryNodes(paramDataCommand);
    acquireChannelNodes(paramDataCommand, localList);
    return localList;
  }

  // ERROR //
  private List<Node> acquireCategoryNodes(DataCommand paramDataCommand)
  {
    // Byte code:
    //   0: new 25	java/util/ArrayList
    //   3: dup
    //   4: invokespecial 26	java/util/ArrayList:<init>	()V
    //   7: astore_2
    //   8: invokestatic 32	fm/qingting/qtradio/data/DBManager:getInstance	()Lfm/qingting/qtradio/data/DBManager;
    //   11: ldc 34
    //   13: invokevirtual 38	fm/qingting/qtradio/data/DBManager:getReadableDB	(Ljava/lang/String;)Landroid/database/sqlite/SQLiteDatabase;
    //   16: ldc 40
    //   18: aconst_null
    //   19: invokevirtual 46	android/database/sqlite/SQLiteDatabase:rawQuery	(Ljava/lang/String;[Ljava/lang/String;)Landroid/database/Cursor;
    //   22: astore 4
    //   24: new 48	com/google/gson/Gson
    //   27: dup
    //   28: invokespecial 49	com/google/gson/Gson:<init>	()V
    //   31: astore 5
    //   33: aload 4
    //   35: invokeinterface 55 1 0
    //   40: ifeq +100 -> 140
    //   43: aload 4
    //   45: aload 4
    //   47: ldc 57
    //   49: invokeinterface 61 2 0
    //   54: invokeinterface 65 2 0
    //   59: astore 6
    //   61: aload 4
    //   63: aload 4
    //   65: ldc 67
    //   67: invokeinterface 61 2 0
    //   72: invokeinterface 65 2 0
    //   77: astore 7
    //   79: aload 7
    //   81: ifnull -48 -> 33
    //   84: aload 6
    //   86: ifnull -53 -> 33
    //   89: aload 7
    //   91: ldc 69
    //   93: invokevirtual 75	java/lang/String:equalsIgnoreCase	(Ljava/lang/String;)Z
    //   96: ifeq +57 -> 153
    //   99: aload 5
    //   101: aload 6
    //   103: ldc 77
    //   105: invokevirtual 81	com/google/gson/Gson:fromJson	(Ljava/lang/String;Ljava/lang/Class;)Ljava/lang/Object;
    //   108: checkcast 77	fm/qingting/qtradio/model/CategoryNode
    //   111: astore 9
    //   113: aload 9
    //   115: ifnull -82 -> 33
    //   118: aload_2
    //   119: aload 9
    //   121: invokeinterface 87 2 0
    //   126: pop
    //   127: goto -94 -> 33
    //   130: astore 8
    //   132: aload 8
    //   134: invokevirtual 90	java/lang/Exception:printStackTrace	()V
    //   137: goto -104 -> 33
    //   140: aload 4
    //   142: invokeinterface 93 1 0
    //   147: aload_2
    //   148: areturn
    //   149: astore 11
    //   151: aconst_null
    //   152: areturn
    //   153: aconst_null
    //   154: astore 9
    //   156: goto -43 -> 113
    //   159: astore_3
    //   160: aload_2
    //   161: areturn
    //
    // Exception table:
    //   from	to	target	type
    //   89	113	130	java/lang/Exception
    //   0	8	149	java/lang/Exception
    //   8	33	159	java/lang/Exception
    //   33	79	159	java/lang/Exception
    //   118	127	159	java/lang/Exception
    //   132	137	159	java/lang/Exception
    //   140	147	159	java/lang/Exception
  }

  private void acquireChannelNodes(DataCommand paramDataCommand, List<Node> paramList)
  {
    if (paramList == null)
      return;
    try
    {
      Cursor localCursor = DBManager.getInstance().getReadableDB("favCategoryNodes").rawQuery("select * from allFavCategoryNodes where name = 'channel'", null);
      Gson localGson = new Gson();
      String str1;
      String str2;
      do
      {
        if (!localCursor.moveToNext())
          break;
        str1 = localCursor.getString(localCursor.getColumnIndex("node"));
        str2 = localCursor.getString(localCursor.getColumnIndex("name"));
      }
      while ((str2 == null) || (str1 == null));
      while (true)
      {
        try
        {
          if (!str2.equalsIgnoreCase("channel"))
            break label146;
          localChannelNode = (ChannelNode)localGson.fromJson(str1, ChannelNode.class);
          if (localChannelNode == null)
            break;
          addToCategory(paramList, (ChannelNode)localChannelNode);
        }
        catch (Exception localException2)
        {
          localException2.printStackTrace();
        }
        break;
        localCursor.close();
        return;
        label146: ChannelNode localChannelNode = null;
      }
    }
    catch (Exception localException1)
    {
    }
  }

  private void addToCategory(List<Node> paramList, ChannelNode paramChannelNode)
  {
    if ((paramList == null) || (paramChannelNode == null));
    while (true)
    {
      return;
      for (int i = 0; i < paramList.size(); i++)
        if ((((Node)paramList.get(i)).nodeName.equalsIgnoreCase("category")) && (((CategoryNode)paramList.get(i)).categoryId == paramChannelNode.categoryId))
        {
          ((CategoryNode)paramList.get(i)).addChannelNode(paramChannelNode);
          return;
        }
    }
  }

  private boolean deleteCategoryNodes(DataCommand paramDataCommand)
  {
    try
    {
      DBManager.getInstance().getWritableDB("favCategoryNodes").execSQL("delete from allFavCategoryNodes");
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
    localDataToken.setData(new Result(true, acquireAllNodes(paramDataCommand)));
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

  public static AllFavCategoryDS getInstance()
  {
    if (instance == null)
      instance = new AllFavCategoryDS();
    return instance;
  }

  private boolean insertCategoryNodes(DataCommand paramDataCommand)
  {
    List localList = (List)paramDataCommand.getParam().get("nodes");
    if ((localList == null) || (localList.size() == 0))
      return false;
    while (true)
    {
      int i;
      try
      {
        SQLiteDatabase localSQLiteDatabase = DBManager.getInstance().getWritableDB("favCategoryNodes");
        localSQLiteDatabase.beginTransaction();
        Gson localGson = new Gson();
        i = 0;
        if (i < localList.size())
        {
          Node localNode1 = (Node)localList.get(i);
          String str1 = localNode1.nodeName;
          if (str1.equalsIgnoreCase("category"))
          {
            j = ((CategoryNode)localNode1).categoryId;
            break label327;
          }
          if (str1.equalsIgnoreCase("channel"))
          {
            j = ((ChannelNode)localNode1).channelId;
            break label327;
            String str2 = localGson.toJson(localNode1);
            Object[] arrayOfObject1 = new Object[3];
            arrayOfObject1[0] = Integer.valueOf(j);
            arrayOfObject1[1] = str1;
            arrayOfObject1[2] = str2;
            localSQLiteDatabase.execSQL("insert into allFavCategoryNodes(id,name,node) values(?,?,?)", arrayOfObject1);
            if ((!str1.equalsIgnoreCase("category")) || (((CategoryNode)localNode1).mLstChannelNodes == null))
              break label332;
            int k = 0;
            if (k >= ((CategoryNode)localNode1).mLstChannelNodes.size())
              break label332;
            Node localNode2 = (Node)((CategoryNode)localNode1).mLstChannelNodes.get(k);
            String str3 = localGson.toJson(localNode2);
            String str4 = localNode2.nodeName;
            int m = ((ChannelNode)localNode2).channelId;
            Object[] arrayOfObject2 = new Object[3];
            arrayOfObject2[0] = Integer.valueOf(m);
            arrayOfObject2[1] = str4;
            arrayOfObject2[2] = str3;
            localSQLiteDatabase.execSQL("insert into allFavCategoryNodes(id,name,node) values(?,?,?)", arrayOfObject2);
            k++;
            continue;
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
        return false;
      }
      int j = 0;
      label327: if (j == 0)
        label332: i++;
    }
  }

  private boolean updateCategoryNodes(DataCommand paramDataCommand)
  {
    List localList = (List)paramDataCommand.getParam().get("nodes");
    if ((localList == null) || (localList.size() == 0))
      return false;
    while (true)
    {
      int i;
      try
      {
        SQLiteDatabase localSQLiteDatabase = DBManager.getInstance().getWritableDB("favCategoryNodes");
        localSQLiteDatabase.beginTransaction();
        localSQLiteDatabase.execSQL("delete from allFavCategoryNodes");
        Gson localGson = new Gson();
        i = 0;
        if (i < localList.size())
        {
          Node localNode1 = (Node)localList.get(i);
          String str1 = localNode1.nodeName;
          if (str1.equalsIgnoreCase("category"))
          {
            j = ((CategoryNode)localNode1).categoryId;
            break label334;
          }
          if (str1.equalsIgnoreCase("channel"))
          {
            j = ((ChannelNode)localNode1).channelId;
            break label334;
            String str2 = localGson.toJson(localNode1);
            Object[] arrayOfObject1 = new Object[3];
            arrayOfObject1[0] = Integer.valueOf(j);
            arrayOfObject1[1] = str1;
            arrayOfObject1[2] = str2;
            localSQLiteDatabase.execSQL("insert into allFavCategoryNodes(id,name,node) values(?,?,?)", arrayOfObject1);
            if ((!str1.equalsIgnoreCase("category")) || (((CategoryNode)localNode1).mLstChannelNodes == null))
              break label339;
            int k = 0;
            if (k >= ((CategoryNode)localNode1).mLstChannelNodes.size())
              break label339;
            Node localNode2 = (Node)((CategoryNode)localNode1).mLstChannelNodes.get(k);
            String str3 = localGson.toJson(localNode2);
            String str4 = localNode2.nodeName;
            int m = ((ChannelNode)localNode2).channelId;
            Object[] arrayOfObject2 = new Object[3];
            arrayOfObject2[0] = Integer.valueOf(m);
            arrayOfObject2[1] = str4;
            arrayOfObject2[2] = str3;
            localSQLiteDatabase.execSQL("insert into allFavCategoryNodes(id,name,node) values(?,?,?)", arrayOfObject2);
            k++;
            continue;
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
        return false;
      }
      int j = 0;
      label334: if (j == 0)
        label339: i++;
    }
  }

  public void addParser(IDataParser paramIDataParser)
  {
  }

  public String dataSourceName()
  {
    return "AllFavCategoryDS";
  }

  public IDataToken doCommand(DataCommand paramDataCommand, IDataRecvHandler paramIDataRecvHandler)
  {
    paramDataCommand.getCurrentCommand();
    return null;
  }

  public boolean isSynchronous(String paramString, Map<String, Object> paramMap)
  {
    return true;
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.qtradio.data.AllFavCategoryDS
 * JD-Core Version:    0.6.2
 */