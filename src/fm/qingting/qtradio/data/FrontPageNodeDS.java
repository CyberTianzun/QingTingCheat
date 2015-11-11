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
import fm.qingting.qtradio.model.FrontPageNode;
import fm.qingting.qtradio.model.Node;
import fm.qingting.qtradio.model.OnDemandProgramNode;
import fm.qingting.qtradio.model.ProgramNode;
import fm.qingting.qtradio.model.RecommendItemNode;
import java.util.List;
import java.util.Map;

public class FrontPageNodeDS
  implements IDataSource
{
  private static FrontPageNodeDS instance;

  private FrontPageNode acquireFrontPage(DataCommand paramDataCommand)
  {
    try
    {
      FrontPageNode localFrontPageNode = new FrontPageNode();
      Cursor localCursor = DBManager.getInstance().getReadableDB("frontpageNodes").rawQuery("select * from frontpageNodes", null);
      Gson localGson = new Gson();
      Node localNode = null;
      while (localCursor.moveToNext())
      {
        int i = localCursor.getColumnIndex("RecommendItemNode");
        int j = localCursor.getColumnIndex("nodeName");
        int k = localCursor.getColumnIndex("type");
        String str1 = localCursor.getString(localCursor.getColumnIndex("node"));
        String str2 = localCursor.getString(i);
        String str3 = localCursor.getString(j);
        int m = localCursor.getInt(k);
        RecommendItemNode localRecommendItemNode = (RecommendItemNode)getNode(localGson, str2, RecommendItemNode.class);
        if (str3.equalsIgnoreCase("channel"))
          localNode = getNode(localGson, str1, ChannelNode.class);
        while (true)
        {
          if (localNode == null)
            break label301;
          localRecommendItemNode.setDetail(localNode);
          if (m != 0)
            break label303;
          localFrontPageNode.addToRecommendList(localRecommendItemNode);
          break;
          if (str3.equalsIgnoreCase("category"))
            localNode = getNode(localGson, str1, CategoryNode.class);
          else if (!str3.equalsIgnoreCase("subcategory"))
            if (str3.equalsIgnoreCase("program"))
              localNode = getNode(localGson, str1, ProgramNode.class);
            else if (str3.equalsIgnoreCase("ondemandprogram"))
              localNode = getNode(localGson, str1, OnDemandProgramNode.class);
            else if (str3.equalsIgnoreCase("activity"))
              localNode = getNode(localGson, str1, ActivityNode.class);
        }
        label301: continue;
        label303: if (m == 1)
          localFrontPageNode.addToShowList(localRecommendItemNode);
      }
      localCursor.close();
      return localFrontPageNode;
    }
    catch (Exception localException)
    {
    }
    return null;
  }

  private boolean delInsertFrontPage(DataCommand paramDataCommand)
  {
    Map localMap = paramDataCommand.getParam();
    List localList1 = (List)localMap.get("banner");
    List localList2 = (List)localMap.get("showlist");
    while (true)
    {
      try
      {
        SQLiteDatabase localSQLiteDatabase = DBManager.getInstance().getWritableDB("frontpageNodes");
        localSQLiteDatabase.beginTransaction();
        localSQLiteDatabase.execSQL("delete from frontpageNodes");
        Gson localGson = new Gson();
        int i = 0;
        if ((localList1 != null) && (i < localList1.size()))
        {
          RecommendItemNode localRecommendItemNode2 = (RecommendItemNode)localList1.get(i);
          String str4 = localGson.toJson(localRecommendItemNode2);
          String str5 = localGson.toJson(localRecommendItemNode2.mNode);
          if (localRecommendItemNode2.mNode != null)
          {
            String str6 = localRecommendItemNode2.mNode.nodeName;
            Object[] arrayOfObject2 = new Object[4];
            arrayOfObject2[0] = str4;
            arrayOfObject2[1] = str5;
            arrayOfObject2[2] = str6;
            arrayOfObject2[3] = Integer.valueOf(0);
            localSQLiteDatabase.execSQL("insert into frontpageNodes(RecommendItemNode,node,nodeName,type) values(?,?,?,?)", arrayOfObject2);
            continue;
            if ((localList2 != null) && (j < localList2.size()))
            {
              RecommendItemNode localRecommendItemNode1 = (RecommendItemNode)localList2.get(j);
              String str1 = localGson.toJson(localRecommendItemNode1);
              if (localRecommendItemNode1.mNode == null)
                break label323;
              String str2 = localGson.toJson(localRecommendItemNode1.mNode);
              String str3 = localRecommendItemNode1.mNode.nodeName;
              Object[] arrayOfObject1 = new Object[4];
              arrayOfObject1[0] = str1;
              arrayOfObject1[1] = str2;
              arrayOfObject1[2] = str3;
              arrayOfObject1[3] = Integer.valueOf(1);
              localSQLiteDatabase.execSQL("insert into frontpageNodes(RecommendItemNode,node,nodeName,type) values(?,?,?,?)", arrayOfObject1);
              break label323;
            }
            localSQLiteDatabase.setTransactionSuccessful();
            localSQLiteDatabase.endTransaction();
            return true;
          }
          i++;
          continue;
        }
      }
      catch (Exception localException)
      {
        return false;
      }
      int j = 0;
      continue;
      label323: j++;
    }
  }

  private boolean deleteFrontPage(DataCommand paramDataCommand)
  {
    try
    {
      DBManager.getInstance().getWritableDB("frontpageNodes").execSQL("delete from frontpageNodes");
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
    localDataToken.setData(new Result(true, acquireFrontPage(paramDataCommand)));
    return localDataToken;
  }

  private DataToken doDelInsertCommand(DataCommand paramDataCommand)
  {
    DataToken localDataToken = new DataToken();
    localDataToken.setDataInfo(paramDataCommand);
    localDataToken.setData(new Result(true, Boolean.valueOf(delInsertFrontPage(paramDataCommand))));
    return localDataToken;
  }

  private DataToken doDeleteCommand(DataCommand paramDataCommand)
  {
    DataToken localDataToken = new DataToken();
    localDataToken.setDataInfo(paramDataCommand);
    localDataToken.setData(new Result(true, Boolean.valueOf(deleteFrontPage(paramDataCommand))));
    return localDataToken;
  }

  private DataToken doInsertCommand(DataCommand paramDataCommand)
  {
    DataToken localDataToken = new DataToken();
    localDataToken.setDataInfo(paramDataCommand);
    localDataToken.setData(new Result(true, Boolean.valueOf(insertFrontPage(paramDataCommand))));
    return localDataToken;
  }

  public static FrontPageNodeDS getInstance()
  {
    if (instance == null)
      instance = new FrontPageNodeDS();
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

  private boolean insertFrontPage(DataCommand paramDataCommand)
  {
    Map localMap = paramDataCommand.getParam();
    List localList1 = (List)localMap.get("banner");
    List localList2 = (List)localMap.get("showlist");
    while (true)
    {
      try
      {
        SQLiteDatabase localSQLiteDatabase = DBManager.getInstance().getWritableDB("frontpageNodes");
        localSQLiteDatabase.beginTransaction();
        Gson localGson = new Gson();
        int i = 0;
        if ((localList1 != null) && (i < localList1.size()))
        {
          RecommendItemNode localRecommendItemNode2 = (RecommendItemNode)localList1.get(i);
          String str4 = localGson.toJson(localRecommendItemNode2);
          if (localRecommendItemNode2.mNode != null)
          {
            String str5 = localGson.toJson(localRecommendItemNode2.mNode);
            String str6 = localRecommendItemNode2.mNode.nodeName;
            Object[] arrayOfObject2 = new Object[4];
            arrayOfObject2[0] = str4;
            arrayOfObject2[1] = str5;
            arrayOfObject2[2] = str6;
            arrayOfObject2[3] = Integer.valueOf(0);
            localSQLiteDatabase.execSQL("insert into frontpageNodes(RecommendItemNode,node,nodeName,type) values(?,?,?,?)", arrayOfObject2);
            continue;
            if ((localList2 != null) && (j < localList2.size()))
            {
              RecommendItemNode localRecommendItemNode1 = (RecommendItemNode)localList2.get(j);
              String str1 = localGson.toJson(localRecommendItemNode1);
              if (localRecommendItemNode1.mNode == null)
                break label316;
              String str2 = localGson.toJson(localRecommendItemNode1.mNode);
              String str3 = localRecommendItemNode1.mNode.nodeName;
              Object[] arrayOfObject1 = new Object[4];
              arrayOfObject1[0] = str1;
              arrayOfObject1[1] = str2;
              arrayOfObject1[2] = str3;
              arrayOfObject1[3] = Integer.valueOf(1);
              localSQLiteDatabase.execSQL("insert into frontpageNodes(RecommendItemNode,node,nodeName,type) values(?,?,?,?)", arrayOfObject1);
              break label316;
            }
            localSQLiteDatabase.setTransactionSuccessful();
            localSQLiteDatabase.endTransaction();
            return true;
          }
          i++;
          continue;
        }
      }
      catch (Exception localException)
      {
        return false;
      }
      int j = 0;
      continue;
      label316: j++;
    }
  }

  public void addParser(IDataParser paramIDataParser)
  {
  }

  public String dataSourceName()
  {
    return "FrontPageNodeDS";
  }

  public IDataToken doCommand(DataCommand paramDataCommand, IDataRecvHandler paramIDataRecvHandler)
  {
    String str = paramDataCommand.getCurrentCommand();
    if (str.equalsIgnoreCase("insertdb_frontpage_node"))
      return doInsertCommand(paramDataCommand);
    if (str.equalsIgnoreCase("getdb_frontpage_node"))
      return doAcquireCommand(paramDataCommand);
    if (str.equalsIgnoreCase("deletedb_frontpage_node"))
      return doDeleteCommand(paramDataCommand);
    if (str.equalsIgnoreCase("delinsertdb_frontpage_node"))
      return doDelInsertCommand(paramDataCommand);
    return null;
  }

  public boolean isSynchronous(String paramString, Map<String, Object> paramMap)
  {
    return true;
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.qtradio.data.FrontPageNodeDS
 * JD-Core Version:    0.6.2
 */