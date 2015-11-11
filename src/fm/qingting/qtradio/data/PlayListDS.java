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
import fm.qingting.qtradio.model.Node;
import java.util.List;
import java.util.Map;

public class PlayListDS
  implements IDataSource
{
  private static PlayListDS instance;

  // ERROR //
  private List<Node> acquirePlaylist(DataCommand paramDataCommand)
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
    //   33: aconst_null
    //   34: astore 6
    //   36: aconst_null
    //   37: astore 7
    //   39: aload 4
    //   41: invokeinterface 48 1 0
    //   46: ifeq +162 -> 208
    //   49: aload 4
    //   51: aload 4
    //   53: ldc 50
    //   55: invokeinterface 54 2 0
    //   60: invokeinterface 58 2 0
    //   65: astore 8
    //   67: aload 4
    //   69: aload 4
    //   71: ldc 60
    //   73: invokeinterface 54 2 0
    //   78: invokeinterface 64 2 0
    //   83: istore 9
    //   85: iload 9
    //   87: ifne +65 -> 152
    //   90: aload 5
    //   92: aload 8
    //   94: ldc 66
    //   96: invokevirtual 70	com/google/gson/Gson:fromJson	(Ljava/lang/String;Ljava/lang/Class;)Ljava/lang/Object;
    //   99: checkcast 66	fm/qingting/qtradio/model/ProgramNode
    //   102: astore 13
    //   104: aload 13
    //   106: astore 7
    //   108: aload 7
    //   110: ifnull +111 -> 221
    //   113: aload 6
    //   115: ifnull +17 -> 132
    //   118: aload 6
    //   120: aload 7
    //   122: putfield 76	fm/qingting/qtradio/model/Node:nextSibling	Lfm/qingting/qtradio/model/Node;
    //   125: aload 7
    //   127: aload 6
    //   129: putfield 79	fm/qingting/qtradio/model/Node:prevSibling	Lfm/qingting/qtradio/model/Node;
    //   132: aload_2
    //   133: aload 7
    //   135: invokeinterface 85 2 0
    //   140: pop
    //   141: aload 7
    //   143: astore 11
    //   145: aload 11
    //   147: astore 6
    //   149: goto -110 -> 39
    //   152: iload 9
    //   154: iconst_2
    //   155: if_icmpne +20 -> 175
    //   158: aload 5
    //   160: aload 8
    //   162: ldc 87
    //   164: invokevirtual 70	com/google/gson/Gson:fromJson	(Ljava/lang/String;Ljava/lang/Class;)Ljava/lang/Object;
    //   167: checkcast 87	fm/qingting/qtradio/model/ChannelNode
    //   170: astore 13
    //   172: goto -68 -> 104
    //   175: iload 9
    //   177: iconst_3
    //   178: if_icmpne +50 -> 228
    //   181: aload 5
    //   183: aload 8
    //   185: ldc 89
    //   187: invokevirtual 70	com/google/gson/Gson:fromJson	(Ljava/lang/String;Ljava/lang/Class;)Ljava/lang/Object;
    //   190: checkcast 89	fm/qingting/qtradio/model/RadioChannelNode
    //   193: astore 13
    //   195: goto -91 -> 104
    //   198: astore 10
    //   200: aload 10
    //   202: invokevirtual 92	java/lang/Exception:printStackTrace	()V
    //   205: goto -97 -> 108
    //   208: aload 4
    //   210: invokeinterface 95 1 0
    //   215: aload_2
    //   216: areturn
    //   217: astore 14
    //   219: aconst_null
    //   220: areturn
    //   221: aload 6
    //   223: astore 11
    //   225: goto -80 -> 145
    //   228: aload 7
    //   230: astore 13
    //   232: goto -128 -> 104
    //   235: astore_3
    //   236: aload_2
    //   237: areturn
    //
    // Exception table:
    //   from	to	target	type
    //   90	104	198	java/lang/Exception
    //   158	172	198	java/lang/Exception
    //   181	195	198	java/lang/Exception
    //   0	8	217	java/lang/Exception
    //   8	33	235	java/lang/Exception
    //   39	85	235	java/lang/Exception
    //   118	132	235	java/lang/Exception
    //   132	141	235	java/lang/Exception
    //   200	205	235	java/lang/Exception
    //   208	215	235	java/lang/Exception
  }

  private DataToken doAcquireCommand(DataCommand paramDataCommand)
  {
    DataToken localDataToken = new DataToken();
    localDataToken.setDataInfo(paramDataCommand);
    localDataToken.setData(new Result(true, acquirePlaylist(paramDataCommand)));
    return localDataToken;
  }

  private DataToken doInsertCommand(DataCommand paramDataCommand)
  {
    DataToken localDataToken = new DataToken();
    localDataToken.setDataInfo(paramDataCommand);
    localDataToken.setData(new Result(true, Boolean.valueOf(insertPlaylist(paramDataCommand))));
    return localDataToken;
  }

  private DataToken doUpdateCommand(DataCommand paramDataCommand)
  {
    DataToken localDataToken = new DataToken();
    localDataToken.setDataInfo(paramDataCommand);
    localDataToken.setData(new Result(true, Boolean.valueOf(updatePlaylist(paramDataCommand))));
    return localDataToken;
  }

  public static PlayListDS getInstance()
  {
    if (instance == null)
      instance = new PlayListDS();
    return instance;
  }

  private boolean insertPlaylist(DataCommand paramDataCommand)
  {
    List localList = (List)paramDataCommand.getParam().get("nodes");
    if ((localList == null) || (localList.size() == 0))
      return false;
    while (true)
    {
      int i;
      try
      {
        SQLiteDatabase localSQLiteDatabase = DBManager.getInstance().getWritableDB("playList");
        localSQLiteDatabase.beginTransaction();
        Gson localGson = new Gson();
        i = 0;
        if (i < localList.size())
        {
          Node localNode = (Node)localList.get(i);
          int j;
          if (localNode.nodeName.equalsIgnoreCase("program"))
          {
            j = 0;
            String str = localGson.toJson(localNode);
            Object[] arrayOfObject = new Object[3];
            arrayOfObject[0] = Integer.valueOf(i);
            arrayOfObject[1] = Integer.valueOf(j);
            arrayOfObject[2] = str;
            localSQLiteDatabase.execSQL("insert into playList(id,type,node) values(?,?,?)", arrayOfObject);
          }
          else
          {
            if (localNode.nodeName.equalsIgnoreCase("ondemandprogram"))
            {
              j = 1;
              continue;
            }
            if (localNode.nodeName.equalsIgnoreCase("channel"))
            {
              j = 2;
              continue;
            }
            if (localNode.nodeName.equalsIgnoreCase("radiochannel"))
            {
              j = 3;
              continue;
            }
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
      i++;
    }
  }

  private boolean updatePlaylist(DataCommand paramDataCommand)
  {
    List localList = (List)paramDataCommand.getParam().get("nodes");
    if ((localList == null) || (localList.size() == 0))
      return false;
    while (true)
    {
      int i;
      try
      {
        SQLiteDatabase localSQLiteDatabase = DBManager.getInstance().getWritableDB("playList");
        localSQLiteDatabase.beginTransaction();
        localSQLiteDatabase.execSQL("delete from playList");
        Gson localGson = new Gson();
        i = 0;
        if (i < localList.size())
        {
          Node localNode = (Node)localList.get(i);
          int j;
          if (localNode.nodeName.equalsIgnoreCase("program"))
          {
            j = 0;
            String str = localGson.toJson(localNode);
            Object[] arrayOfObject = new Object[3];
            arrayOfObject[0] = Integer.valueOf(i);
            arrayOfObject[1] = Integer.valueOf(j);
            arrayOfObject[2] = str;
            localSQLiteDatabase.execSQL("insert into playList(id,type,node) values(?,?,?)", arrayOfObject);
          }
          else
          {
            if (localNode.nodeName.equalsIgnoreCase("ondemandprogram"))
            {
              j = 1;
              continue;
            }
            if (localNode.nodeName.equalsIgnoreCase("channel"))
            {
              j = 2;
              continue;
            }
            if (localNode.nodeName.equalsIgnoreCase("radiochannel"))
            {
              j = 3;
              continue;
            }
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
      i++;
    }
  }

  public void addParser(IDataParser paramIDataParser)
  {
  }

  public String dataSourceName()
  {
    return "PlayListDS";
  }

  public IDataToken doCommand(DataCommand paramDataCommand, IDataRecvHandler paramIDataRecvHandler)
  {
    String str = paramDataCommand.getCurrentCommand();
    if (str.equalsIgnoreCase("insertdb_playlist"))
      return doInsertCommand(paramDataCommand);
    if (str.equalsIgnoreCase("getdb_playlist"))
      return doAcquireCommand(paramDataCommand);
    if (str.equalsIgnoreCase("updatedb_playlist"))
      return doUpdateCommand(paramDataCommand);
    return null;
  }

  public boolean isSynchronous(String paramString, Map<String, Object> paramMap)
  {
    return true;
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.qtradio.data.PlayListDS
 * JD-Core Version:    0.6.2
 */