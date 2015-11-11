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
import fm.qingting.qtradio.model.BillboardItemNode;
import fm.qingting.qtradio.model.ChannelNode;
import fm.qingting.qtradio.model.Node;
import fm.qingting.qtradio.model.ProgramNode;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class BillboardNodeDS
  implements IDataSource
{
  private static BillboardNodeDS instance;

  private List<BillboardItemNode> acquireBillboard(DataCommand paramDataCommand)
  {
    String str1 = (String)paramDataCommand.getParam().get("type");
    if ((str1 == null) || (str1.equalsIgnoreCase("")))
      return null;
    ArrayList localArrayList = new ArrayList();
    try
    {
      String str2 = "select * from billboardNodes where type = '" + str1 + "'";
      Cursor localCursor = DBManager.getInstance().getReadableDB("billboardNodes").rawQuery(str2, null);
      Gson localGson = new Gson();
      Node localNode = null;
      label255: 
      while (localCursor.moveToNext())
      {
        int i = localCursor.getColumnIndex("BillboardItemNode");
        int j = localCursor.getColumnIndex("nodeName");
        String str3 = localCursor.getString(localCursor.getColumnIndex("node"));
        String str4 = localCursor.getString(i);
        String str5 = localCursor.getString(j);
        BillboardItemNode localBillboardItemNode = (BillboardItemNode)getNode(localGson, str4, BillboardItemNode.class);
        if (str5.equalsIgnoreCase("channel"))
          localNode = getNode(localGson, str3, ChannelNode.class);
        while (true)
        {
          if (localNode == null)
            break label255;
          localNode.parent = localBillboardItemNode;
          localBillboardItemNode.setDetail(localNode);
          localArrayList.add(localBillboardItemNode);
          break;
          if (str5.equalsIgnoreCase("program"))
            localNode = getNode(localGson, str3, ProgramNode.class);
        }
      }
      localCursor.close();
      return localArrayList;
    }
    catch (Exception localException)
    {
    }
    return null;
  }

  private boolean deleteBillboard(DataCommand paramDataCommand)
  {
    String str1 = (String)paramDataCommand.getParam().get("type");
    if ((str1 == null) || (str1.equalsIgnoreCase("")))
      return false;
    try
    {
      String str2 = "delete from billboardNodes where type = '" + str1 + "'";
      DBManager.getInstance().getWritableDB("billboardNodes").execSQL(str2);
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
    localDataToken.setData(new Result(true, acquireBillboard(paramDataCommand)));
    return localDataToken;
  }

  private DataToken doDeleteCommand(DataCommand paramDataCommand)
  {
    DataToken localDataToken = new DataToken();
    localDataToken.setDataInfo(paramDataCommand);
    localDataToken.setData(new Result(true, Boolean.valueOf(deleteBillboard(paramDataCommand))));
    return localDataToken;
  }

  private DataToken doInsertCommand(DataCommand paramDataCommand)
  {
    DataToken localDataToken = new DataToken();
    localDataToken.setDataInfo(paramDataCommand);
    localDataToken.setData(new Result(true, Boolean.valueOf(insertBillboard(paramDataCommand))));
    return localDataToken;
  }

  private DataToken doUpdateCommand(DataCommand paramDataCommand)
  {
    DataToken localDataToken = new DataToken();
    localDataToken.setDataInfo(paramDataCommand);
    localDataToken.setData(new Result(true, Boolean.valueOf(updateBillboard(paramDataCommand))));
    return localDataToken;
  }

  public static BillboardNodeDS getInstance()
  {
    if (instance == null)
      instance = new BillboardNodeDS();
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

  private boolean insertBillboard(DataCommand paramDataCommand)
  {
    Map localMap = paramDataCommand.getParam();
    int i = ((Integer)localMap.get("type")).intValue();
    List localList = (List)localMap.get("nodes");
    if ((localList == null) || (localList.size() == 0))
      return false;
    try
    {
      SQLiteDatabase localSQLiteDatabase = DBManager.getInstance().getWritableDB("billboardNodes");
      localSQLiteDatabase.beginTransaction();
      Gson localGson = new Gson();
      for (int j = 0; ; j++)
        if (j < localList.size())
        {
          BillboardItemNode localBillboardItemNode = (BillboardItemNode)localList.get(j);
          String str1 = localGson.toJson(localBillboardItemNode);
          String str2 = localGson.toJson(localBillboardItemNode.mNode);
          if (localBillboardItemNode.mNode != null)
          {
            String str3 = localBillboardItemNode.mNode.nodeName;
            Object[] arrayOfObject = new Object[4];
            arrayOfObject[0] = str1;
            arrayOfObject[1] = str2;
            arrayOfObject[2] = str3;
            arrayOfObject[3] = Integer.valueOf(i);
            localSQLiteDatabase.execSQL("insert into billboardNodes(BillboardItemNode,node,nodeName,type) values(?,?,?,?)", arrayOfObject);
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

  private boolean updateBillboard(DataCommand paramDataCommand)
  {
    Map localMap = paramDataCommand.getParam();
    int i = ((Integer)localMap.get("type")).intValue();
    List localList = (List)localMap.get("nodes");
    if ((localList == null) || (localList.size() == 0))
      return false;
    try
    {
      SQLiteDatabase localSQLiteDatabase = DBManager.getInstance().getWritableDB("billboardNodes");
      localSQLiteDatabase.beginTransaction();
      localSQLiteDatabase.execSQL("delete from billboardNodes where type = '" + i + "'");
      Gson localGson = new Gson();
      for (int j = 0; ; j++)
        if (j < localList.size())
        {
          BillboardItemNode localBillboardItemNode = (BillboardItemNode)localList.get(j);
          String str1 = localGson.toJson(localBillboardItemNode);
          String str2 = localGson.toJson(localBillboardItemNode.mNode);
          if (localBillboardItemNode.mNode != null)
          {
            String str3 = localBillboardItemNode.mNode.nodeName;
            Object[] arrayOfObject = new Object[4];
            arrayOfObject[0] = str1;
            arrayOfObject[1] = str2;
            arrayOfObject[2] = str3;
            arrayOfObject[3] = Integer.valueOf(i);
            localSQLiteDatabase.execSQL("insert into billboardNodes(BillboardItemNode,node,nodeName,type) values(?,?,?,?)", arrayOfObject);
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

  public void addParser(IDataParser paramIDataParser)
  {
  }

  public String dataSourceName()
  {
    return "BillboardNodeDS";
  }

  public IDataToken doCommand(DataCommand paramDataCommand, IDataRecvHandler paramIDataRecvHandler)
  {
    String str = paramDataCommand.getCurrentCommand();
    if (str.equalsIgnoreCase("insertdb_billboarditem"))
      return doInsertCommand(paramDataCommand);
    if (str.equalsIgnoreCase("getdb_billboarditem"))
      return doAcquireCommand(paramDataCommand);
    if (str.equalsIgnoreCase("deletedb_billboarditem"))
      return doDeleteCommand(paramDataCommand);
    if (str.equalsIgnoreCase("updatedb_billboarditem"))
      return doUpdateCommand(paramDataCommand);
    return null;
  }

  public boolean isSynchronous(String paramString, Map<String, Object> paramMap)
  {
    return true;
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.qtradio.data.BillboardNodeDS
 * JD-Core Version:    0.6.2
 */