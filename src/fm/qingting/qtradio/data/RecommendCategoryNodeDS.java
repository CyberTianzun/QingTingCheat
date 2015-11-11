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
import fm.qingting.qtradio.model.ActivityNode;
import fm.qingting.qtradio.model.CategoryNode;
import fm.qingting.qtradio.model.ChannelNode;
import fm.qingting.qtradio.model.Node;
import fm.qingting.qtradio.model.ProgramNode;
import fm.qingting.qtradio.model.RecommendCategoryNode;
import fm.qingting.qtradio.model.RecommendItemNode;
import java.util.List;
import java.util.Map;

public class RecommendCategoryNodeDS
  implements IDataSource
{
  private static RecommendCategoryNodeDS instance;

  private RecommendCategoryNode acquireRecCategory(DataCommand paramDataCommand)
  {
    Map localMap = paramDataCommand.getParam();
    int i = ((Integer)localMap.get("id")).intValue();
    String str1 = (String)localMap.get("type");
    try
    {
      RecommendCategoryNode localRecommendCategoryNode = new RecommendCategoryNode();
      String str2 = "select * from recCategoryNodes where catid = '" + i + "'";
      if (str1 != null);
      for (String str3 = str2 + " and type = '" + str1 + "'"; ; str3 = str2)
      {
        Cursor localCursor = DBManager.getInstance().getReadableDB("recCategoryNodes").rawQuery(str3, null);
        Gson localGson = new Gson();
        Node localNode = null;
        if (localCursor.moveToNext())
        {
          int j = localCursor.getColumnIndex("RecommendItemNode");
          int k = localCursor.getColumnIndex("nodeName");
          int m = localCursor.getColumnIndex("type");
          String str4 = localCursor.getString(localCursor.getColumnIndex("node"));
          String str5 = localCursor.getString(j);
          String str6 = localCursor.getString(k);
          int n = localCursor.getInt(m);
          RecommendItemNode localRecommendItemNode = (RecommendItemNode)getNode(localGson, str5, RecommendItemNode.class);
          if (str6.equalsIgnoreCase("channel"))
            localNode = getNode(localGson, str4, ChannelNode.class);
          while (true)
          {
            if (localRecommendCategoryNode.sectionId == -1)
              localRecommendCategoryNode.sectionId = Integer.valueOf(i).intValue();
            if (localNode == null)
              break;
            localRecommendItemNode.setDetail(localNode);
            localRecommendCategoryNode.insertRecCategory(localRecommendItemNode, n);
            break;
            if (str6.equalsIgnoreCase("category"))
              localNode = getNode(localGson, str4, CategoryNode.class);
            else if (!str6.equalsIgnoreCase("subcategory"))
              if (str6.equalsIgnoreCase("program"))
                localNode = getNode(localGson, str4, ProgramNode.class);
              else if (str6.equalsIgnoreCase("activity"))
                localNode = getNode(localGson, str4, ActivityNode.class);
          }
        }
        localCursor.close();
        return localRecommendCategoryNode;
      }
    }
    catch (Exception localException)
    {
    }
    return null;
  }

  private boolean deleteRecCategory(DataCommand paramDataCommand)
  {
    try
    {
      DBManager.getInstance().getWritableDB("recCategoryNodes").execSQL("delete from recCategoryNodes");
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
    localDataToken.setData(new Result(true, acquireRecCategory(paramDataCommand)));
    return localDataToken;
  }

  private DataToken doDeleteCommand(DataCommand paramDataCommand)
  {
    DataToken localDataToken = new DataToken();
    localDataToken.setDataInfo(paramDataCommand);
    localDataToken.setData(new Result(true, Boolean.valueOf(deleteRecCategory(paramDataCommand))));
    return localDataToken;
  }

  private DataToken doInsertCommand(DataCommand paramDataCommand)
  {
    DataToken localDataToken = new DataToken();
    localDataToken.setDataInfo(paramDataCommand);
    localDataToken.setData(new Result(true, Boolean.valueOf(insertRecCategory(paramDataCommand))));
    return localDataToken;
  }

  private DataToken doUpdateCommand(DataCommand paramDataCommand)
  {
    DataToken localDataToken = new DataToken();
    localDataToken.setDataInfo(paramDataCommand);
    localDataToken.setData(new Result(true, Boolean.valueOf(updateRecCategory(paramDataCommand))));
    return localDataToken;
  }

  public static RecommendCategoryNodeDS getInstance()
  {
    if (instance == null)
      instance = new RecommendCategoryNodeDS();
    return instance;
  }

  private <T> Node getNode(Gson paramGson, String paramString, Class<T> paramClass)
  {
    if (paramString == null)
      return null;
    try
    {
      Node localNode = (Node)paramGson.fromJson(paramString, paramClass);
      return localNode;
    }
    catch (Exception localException)
    {
    }
    return null;
  }

  private boolean insertRecCategory(DataCommand paramDataCommand)
  {
    Map localMap = paramDataCommand.getParam();
    String str1 = (String)localMap.get("type");
    if (str1 == null)
      return false;
    int i = ((Integer)localMap.get("id")).intValue();
    List localList2;
    List localList1;
    if (str1.equalsIgnoreCase("l"))
    {
      List localList3 = (List)localMap.get("nodes");
      localList2 = null;
      localList1 = localList3;
    }
    while (true)
    {
      int k;
      int m;
      try
      {
        SQLiteDatabase localSQLiteDatabase = DBManager.getInstance().getWritableDB("recCategoryNodes");
        localSQLiteDatabase.beginTransaction();
        Gson localGson = new Gson();
        if ((localList1 != null) && (localList1.size() > 0))
        {
          int j = 0;
          if ((localList1 != null) && (j < localList1.size()))
          {
            RecommendItemNode localRecommendItemNode2 = (RecommendItemNode)localList1.get(j);
            String str5 = localGson.toJson(localRecommendItemNode2);
            String str6 = localGson.toJson(localRecommendItemNode2.mNode);
            Node localNode = localRecommendItemNode2.mNode;
            if (localNode == null)
            {
              j++;
              continue;
              if (!str1.equalsIgnoreCase("ll"))
                continue;
              localList2 = (List)localMap.get("nodes");
              localList1 = null;
              continue;
            }
            String str7 = localRecommendItemNode2.mNode.nodeName;
            Object[] arrayOfObject2 = new Object[5];
            arrayOfObject2[0] = str5;
            arrayOfObject2[1] = str6;
            arrayOfObject2[2] = str7;
            arrayOfObject2[3] = Integer.valueOf(i);
            arrayOfObject2[4] = Integer.valueOf(0);
            localSQLiteDatabase.execSQL("insert into recCategoryNodes(RecommendItemNode,node,nodeName,catid,type) values(?,?,?,?,?)", arrayOfObject2);
            continue;
          }
        }
        if ((localList2 != null) && (localList2.size() > 0))
        {
          k = 0;
          if (k < localList2.size())
          {
            if (localList2.get(k) == null)
              break label488;
            m = 0;
            if (m >= ((List)localList2.get(k)).size())
              break label488;
            RecommendItemNode localRecommendItemNode1 = (RecommendItemNode)((List)localList2.get(k)).get(m);
            String str2 = localGson.toJson(localRecommendItemNode1);
            String str3 = localGson.toJson(localRecommendItemNode1.mNode);
            if (localRecommendItemNode1.mNode == null)
              break label482;
            String str4 = localRecommendItemNode1.mNode.nodeName;
            Object[] arrayOfObject1 = new Object[5];
            arrayOfObject1[0] = str2;
            arrayOfObject1[1] = str3;
            arrayOfObject1[2] = str4;
            arrayOfObject1[3] = Integer.valueOf(i);
            arrayOfObject1[4] = Integer.valueOf(1);
            localSQLiteDatabase.execSQL("insert into recCategoryNodes(RecommendItemNode,node,nodeName,catid,type) values(?,?,?,?,?)", arrayOfObject1);
            break label482;
          }
        }
        localSQLiteDatabase.setTransactionSuccessful();
        localSQLiteDatabase.endTransaction();
        return true;
        localList1 = null;
        localList2 = null;
        continue;
      }
      catch (Exception localException)
      {
        return false;
      }
      label482: m++;
      continue;
      label488: k++;
    }
  }

  private boolean updateRecCategory(DataCommand paramDataCommand)
  {
    Map localMap = paramDataCommand.getParam();
    int i = ((Integer)localMap.get("id")).intValue();
    List localList1 = (List)localMap.get("banner");
    String str1;
    List localList2;
    if ((localList1 != null) && (localList1.size() > 0))
    {
      str1 = "0";
      localList2 = (List)localMap.get("main");
      if ((localList2 != null) && (localList2.size() > 0))
        str1 = "1";
    }
    while (true)
    {
      int k;
      int m;
      try
      {
        SQLiteDatabase localSQLiteDatabase = DBManager.getInstance().getWritableDB("recCategoryNodes");
        localSQLiteDatabase.beginTransaction();
        localSQLiteDatabase.execSQL("delete from recCategoryNodes where catid = '" + i + "'" + " and type = " + "'" + str1 + "'");
        Gson localGson = new Gson();
        int j;
        if ((localList1 != null) && (localList1.size() > 0))
        {
          j = 0;
          if ((localList1 != null) && (j < localList1.size()))
          {
            RecommendItemNode localRecommendItemNode2 = (RecommendItemNode)localList1.get(j);
            String str5 = localGson.toJson(localRecommendItemNode2);
            String str6 = localGson.toJson(localRecommendItemNode2.mNode);
            if (localRecommendItemNode2.mNode == null)
              continue;
            String str7 = localRecommendItemNode2.mNode.nodeName;
            Object[] arrayOfObject2 = new Object[5];
            arrayOfObject2[0] = str5;
            arrayOfObject2[1] = str6;
            arrayOfObject2[2] = str7;
            arrayOfObject2[3] = Integer.valueOf(i);
            arrayOfObject2[4] = Integer.valueOf(0);
            localSQLiteDatabase.execSQL("insert into recCategoryNodes(RecommendItemNode,node,nodeName,catid,type) values(?,?,?,?,?)", arrayOfObject2);
            continue;
          }
        }
        if ((localList2 != null) && (localList2.size() > 0))
        {
          k = 0;
          if (k < localList2.size())
          {
            if (localList2.get(k) == null)
              break label522;
            m = 0;
            if (m >= ((List)localList2.get(k)).size())
              break label522;
            RecommendItemNode localRecommendItemNode1 = (RecommendItemNode)((List)localList2.get(k)).get(m);
            String str2 = localGson.toJson(localRecommendItemNode1);
            String str3 = localGson.toJson(localRecommendItemNode1.mNode);
            if (localRecommendItemNode1.mNode == null)
              break label516;
            String str4 = localRecommendItemNode1.mNode.nodeName;
            Object[] arrayOfObject1 = new Object[5];
            arrayOfObject1[0] = str2;
            arrayOfObject1[1] = str3;
            arrayOfObject1[2] = str4;
            arrayOfObject1[3] = Integer.valueOf(i);
            arrayOfObject1[4] = Integer.valueOf(1);
            localSQLiteDatabase.execSQL("insert into recCategoryNodes(RecommendItemNode,node,nodeName,catid,type) values(?,?,?,?,?)", arrayOfObject1);
            break label516;
          }
        }
        localSQLiteDatabase.setTransactionSuccessful();
        localSQLiteDatabase.endTransaction();
        return true;
        str1 = null;
        break;
        j++;
        continue;
      }
      catch (Exception localException)
      {
        return false;
      }
      label516: m++;
      continue;
      label522: k++;
    }
  }

  public void addParser(IDataParser paramIDataParser)
  {
  }

  public String dataSourceName()
  {
    return "RecommendCategoryNodeDS";
  }

  public IDataToken doCommand(DataCommand paramDataCommand, IDataRecvHandler paramIDataRecvHandler)
  {
    String str = paramDataCommand.getCurrentCommand();
    if (str.equalsIgnoreCase("insertdb_reccategory_node"))
      return doInsertCommand(paramDataCommand);
    if (str.equalsIgnoreCase("getdb_reccategory_node"))
      return doAcquireCommand(paramDataCommand);
    if (str.equalsIgnoreCase("deletedb_reccategory_node"))
      return doDeleteCommand(paramDataCommand);
    if (str.equalsIgnoreCase("updatedb_reccategory_node"))
      return doUpdateCommand(paramDataCommand);
    return null;
  }

  public boolean isSynchronous(String paramString, Map<String, Object> paramMap)
  {
    return true;
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.qtradio.data.RecommendCategoryNodeDS
 * JD-Core Version:    0.6.2
 */