package fm.qingting.qtradio.data;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import com.google.gson.Gson;
import fm.qingting.framework.data.DataCommand;
import fm.qingting.framework.data.DataToken;
import fm.qingting.framework.data.IDataParser;
import fm.qingting.framework.data.IDataRecvHandler;
import fm.qingting.framework.data.IDataSource;
import fm.qingting.framework.data.IDataToken;
import fm.qingting.framework.data.Result;
import fm.qingting.qtradio.model.ChannelNode;
import fm.qingting.qtradio.model.Node;
import java.util.List;
import java.util.Map;

public class FavouriteChannelDS
  implements IDataSource
{
  private static FavouriteChannelDS instance;

  // ERROR //
  private List<Node> acquireFavourites(DataCommand paramDataCommand)
  {
    // Byte code:
    //   0: new 18	java/util/ArrayList
    //   3: dup
    //   4: invokespecial 19	java/util/ArrayList:<init>	()V
    //   7: astore_2
    //   8: invokestatic 25	fm/qingting/qtradio/data/DBManager:getInstance	()Lfm/qingting/qtradio/data/DBManager;
    //   11: ldc 27
    //   13: invokevirtual 31	fm/qingting/qtradio/data/DBManager:getReadableDB	(Ljava/lang/String;)Landroid/database/sqlite/SQLiteDatabase;
    //   16: ldc 33
    //   18: aconst_null
    //   19: invokevirtual 39	android/database/sqlite/SQLiteDatabase:rawQuery	(Ljava/lang/String;[Ljava/lang/String;)Landroid/database/Cursor;
    //   22: astore 4
    //   24: new 41	com/google/gson/Gson
    //   27: dup
    //   28: invokespecial 42	com/google/gson/Gson:<init>	()V
    //   31: astore 5
    //   33: aload 4
    //   35: invokeinterface 48 1 0
    //   40: ifeq +129 -> 169
    //   43: aload 4
    //   45: aload 4
    //   47: ldc 50
    //   49: invokeinterface 54 2 0
    //   54: invokeinterface 58 2 0
    //   59: astore 6
    //   61: aload 5
    //   63: aload 6
    //   65: ldc 60
    //   67: invokevirtual 64	com/google/gson/Gson:fromJson	(Ljava/lang/String;Ljava/lang/Class;)Ljava/lang/Object;
    //   70: checkcast 60	fm/qingting/qtradio/model/ChannelNode
    //   73: astore 8
    //   75: aload 8
    //   77: ifnull +86 -> 163
    //   80: aload 8
    //   82: getfield 68	fm/qingting/qtradio/model/ChannelNode:title	Ljava/lang/String;
    //   85: ifnull +72 -> 157
    //   88: aload 8
    //   90: getfield 68	fm/qingting/qtradio/model/ChannelNode:title	Ljava/lang/String;
    //   93: ldc 70
    //   95: invokevirtual 76	java/lang/String:equalsIgnoreCase	(Ljava/lang/String;)Z
    //   98: ifne +59 -> 157
    //   101: aload_2
    //   102: aload 8
    //   104: invokeinterface 82 2 0
    //   109: pop
    //   110: iconst_0
    //   111: istore 9
    //   113: iload 9
    //   115: ifeq -82 -> 33
    //   118: aload 5
    //   120: aload 6
    //   122: ldc 84
    //   124: invokevirtual 64	com/google/gson/Gson:fromJson	(Ljava/lang/String;Ljava/lang/Class;)Ljava/lang/Object;
    //   127: checkcast 84	fm/qingting/qtradio/model/ChannelNodeV5
    //   130: astore 10
    //   132: aload 10
    //   134: ifnull -101 -> 33
    //   137: aload_2
    //   138: aload 10
    //   140: invokevirtual 88	fm/qingting/qtradio/model/ChannelNodeV5:convertToChannel	()Lfm/qingting/qtradio/model/ChannelNode;
    //   143: invokeinterface 82 2 0
    //   148: pop
    //   149: goto -116 -> 33
    //   152: astore 7
    //   154: goto -121 -> 33
    //   157: iconst_1
    //   158: istore 9
    //   160: goto -47 -> 113
    //   163: iconst_1
    //   164: istore 9
    //   166: goto -53 -> 113
    //   169: aload 4
    //   171: invokeinterface 91 1 0
    //   176: aload_2
    //   177: areturn
    //   178: astore 13
    //   180: aconst_null
    //   181: areturn
    //   182: astore_3
    //   183: aload_2
    //   184: areturn
    //
    // Exception table:
    //   from	to	target	type
    //   61	75	152	java/lang/Exception
    //   80	110	152	java/lang/Exception
    //   118	132	152	java/lang/Exception
    //   137	149	152	java/lang/Exception
    //   0	8	178	java/lang/Exception
    //   8	33	182	java/lang/Exception
    //   33	61	182	java/lang/Exception
    //   169	176	182	java/lang/Exception
  }

  private boolean deleteFavouriteChannel(DataCommand paramDataCommand)
  {
    try
    {
      DBManager.getInstance().getWritableDB("favouritechannels").execSQL("delete from favouritechannels");
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
    localDataToken.setData(new Result(true, acquireFavourites(paramDataCommand)));
    return localDataToken;
  }

  private DataToken doDeleteCommand(DataCommand paramDataCommand)
  {
    DataToken localDataToken = new DataToken();
    localDataToken.setDataInfo(paramDataCommand);
    localDataToken.setData(new Result(true, Boolean.valueOf(deleteFavouriteChannel(paramDataCommand))));
    return localDataToken;
  }

  private DataToken doInsertCommand(DataCommand paramDataCommand)
  {
    DataToken localDataToken = new DataToken();
    localDataToken.setDataInfo(paramDataCommand);
    localDataToken.setData(new Result(true, Boolean.valueOf(insertFavourites(paramDataCommand))));
    return localDataToken;
  }

  private DataToken doUpdateCommand(DataCommand paramDataCommand)
  {
    DataToken localDataToken = new DataToken();
    localDataToken.setDataInfo(paramDataCommand);
    localDataToken.setData(new Result(true, Boolean.valueOf(updateFavouriteChannel(paramDataCommand))));
    return localDataToken;
  }

  public static FavouriteChannelDS getInstance()
  {
    if (instance == null)
      instance = new FavouriteChannelDS();
    return instance;
  }

  private boolean insertFavourites(DataCommand paramDataCommand)
  {
    Map localMap = paramDataCommand.getParam();
    List localList = (List)localMap.get("channels");
    Integer localInteger = (Integer)localMap.get("total");
    if ((localList == null) || (localList.size() == 0))
      return false;
    localInteger.intValue();
    try
    {
      SQLiteDatabase localSQLiteDatabase = DBManager.getInstance().getWritableDB("favouritechannels");
      localSQLiteDatabase.beginTransaction();
      Gson localGson = new Gson();
      for (int i = 0; i < localList.size(); i++)
      {
        Node localNode = (Node)localList.get(i);
        String str = localGson.toJson(localNode);
        int j = Integer.valueOf(((ChannelNode)localNode).channelId).intValue();
        Object[] arrayOfObject = new Object[2];
        arrayOfObject[0] = Integer.valueOf(j);
        arrayOfObject[1] = str;
        localSQLiteDatabase.execSQL("insert into favouritechannels(id, channelNode) values(?, ?)", arrayOfObject);
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

  private boolean updateFavouriteChannel(DataCommand paramDataCommand)
  {
    Node localNode = (Node)paramDataCommand.getParam().get("channel");
    if (localNode == null)
      return false;
    try
    {
      SQLiteDatabase localSQLiteDatabase = DBManager.getInstance().getWritableDB("favouritechannels");
      localSQLiteDatabase.beginTransaction();
      String str = new Gson().toJson(localNode);
      ContentValues localContentValues = new ContentValues();
      localContentValues.put("id", String.valueOf(((ChannelNode)localNode).channelId));
      localContentValues.put("channelNode", str);
      String[] arrayOfString = new String[1];
      arrayOfString[0] = String.valueOf(((ChannelNode)localNode).channelId);
      localSQLiteDatabase.update("favouritechannels", localContentValues, "id=?", arrayOfString);
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
    return "FavouriteChannelDS";
  }

  public IDataToken doCommand(DataCommand paramDataCommand, IDataRecvHandler paramIDataRecvHandler)
  {
    String str = paramDataCommand.getCurrentCommand();
    if (str.equalsIgnoreCase("insert_favourite_channels"))
      return doInsertCommand(paramDataCommand);
    if (str.equalsIgnoreCase("get_favourite_channels"))
      return doAcquireCommand(paramDataCommand);
    if (str.equalsIgnoreCase("delete_favourite_channels"))
      return doDeleteCommand(paramDataCommand);
    if (str.equalsIgnoreCase("update_favourite_channels"))
      return doUpdateCommand(paramDataCommand);
    return null;
  }

  public boolean isSynchronous(String paramString, Map<String, Object> paramMap)
  {
    return true;
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.qtradio.data.FavouriteChannelDS
 * JD-Core Version:    0.6.2
 */