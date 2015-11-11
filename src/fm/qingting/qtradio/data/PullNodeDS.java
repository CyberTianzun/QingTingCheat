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

public class PullNodeDS
  implements IDataSource
{
  private static PullNodeDS instance;

  // ERROR //
  private List<Node> acquirePulllist(DataCommand paramDataCommand)
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
    //   36: aload 4
    //   38: invokeinterface 48 1 0
    //   43: ifeq +161 -> 204
    //   46: aload 4
    //   48: aload 4
    //   50: ldc 50
    //   52: invokeinterface 54 2 0
    //   57: invokeinterface 58 2 0
    //   62: astore 7
    //   64: aload 4
    //   66: aload 4
    //   68: ldc 60
    //   70: invokeinterface 54 2 0
    //   75: invokeinterface 64 2 0
    //   80: istore 8
    //   82: iload 8
    //   84: ifne +41 -> 125
    //   87: aload 5
    //   89: aload 7
    //   91: ldc 66
    //   93: invokevirtual 70	com/google/gson/Gson:fromJson	(Ljava/lang/String;Ljava/lang/Class;)Ljava/lang/Object;
    //   96: checkcast 66	fm/qingting/qtradio/model/ProgramNode
    //   99: astore 11
    //   101: aload 11
    //   103: astore 6
    //   105: aload 6
    //   107: ifnull -71 -> 36
    //   110: aload_2
    //   111: aload 6
    //   113: invokeinterface 76 2 0
    //   118: pop
    //   119: goto -83 -> 36
    //   122: astore_3
    //   123: aload_2
    //   124: areturn
    //   125: iload 8
    //   127: iconst_1
    //   128: if_icmpne +20 -> 148
    //   131: aload 5
    //   133: aload 7
    //   135: ldc 78
    //   137: invokevirtual 70	com/google/gson/Gson:fromJson	(Ljava/lang/String;Ljava/lang/Class;)Ljava/lang/Object;
    //   140: checkcast 78	fm/qingting/qtradio/model/OnDemandProgramNode
    //   143: astore 11
    //   145: goto -44 -> 101
    //   148: iload 8
    //   150: iconst_2
    //   151: if_icmpne +20 -> 171
    //   154: aload 5
    //   156: aload 7
    //   158: ldc 80
    //   160: invokevirtual 70	com/google/gson/Gson:fromJson	(Ljava/lang/String;Ljava/lang/Class;)Ljava/lang/Object;
    //   163: checkcast 80	fm/qingting/qtradio/model/ChannelNode
    //   166: astore 11
    //   168: goto -67 -> 101
    //   171: iload 8
    //   173: iconst_3
    //   174: if_icmpne +43 -> 217
    //   177: aload 5
    //   179: aload 7
    //   181: ldc 82
    //   183: invokevirtual 70	com/google/gson/Gson:fromJson	(Ljava/lang/String;Ljava/lang/Class;)Ljava/lang/Object;
    //   186: checkcast 82	fm/qingting/qtradio/model/RadioChannelNode
    //   189: astore 11
    //   191: goto -90 -> 101
    //   194: astore 9
    //   196: aload 9
    //   198: invokevirtual 85	java/lang/Exception:printStackTrace	()V
    //   201: goto -96 -> 105
    //   204: aload 4
    //   206: invokeinterface 88 1 0
    //   211: aload_2
    //   212: areturn
    //   213: astore 12
    //   215: aconst_null
    //   216: areturn
    //   217: aload 6
    //   219: astore 11
    //   221: goto -120 -> 101
    //
    // Exception table:
    //   from	to	target	type
    //   8	33	122	java/lang/Exception
    //   36	82	122	java/lang/Exception
    //   110	119	122	java/lang/Exception
    //   196	201	122	java/lang/Exception
    //   204	211	122	java/lang/Exception
    //   87	101	194	java/lang/Exception
    //   131	145	194	java/lang/Exception
    //   154	168	194	java/lang/Exception
    //   177	191	194	java/lang/Exception
    //   0	8	213	java/lang/Exception
  }

  private boolean delPulllist(DataCommand paramDataCommand)
  {
    List localList = (List)paramDataCommand.getParam().get("nodes");
    if ((localList == null) || (localList.size() == 0))
      return false;
    try
    {
      SQLiteDatabase localSQLiteDatabase = DBManager.getInstance().getWritableDB("pullList");
      localSQLiteDatabase.beginTransaction();
      localSQLiteDatabase.execSQL("delete from pullList");
      localSQLiteDatabase.setTransactionSuccessful();
      localSQLiteDatabase.endTransaction();
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
    localDataToken.setData(new Result(true, acquirePulllist(paramDataCommand)));
    return localDataToken;
  }

  private DataToken doDeleteCommand(DataCommand paramDataCommand)
  {
    DataToken localDataToken = new DataToken();
    localDataToken.setDataInfo(paramDataCommand);
    localDataToken.setData(new Result(true, Boolean.valueOf(delPulllist(paramDataCommand))));
    return localDataToken;
  }

  private DataToken doUpdateCommand(DataCommand paramDataCommand)
  {
    DataToken localDataToken = new DataToken();
    localDataToken.setDataInfo(paramDataCommand);
    localDataToken.setData(new Result(true, Boolean.valueOf(updatePulllist(paramDataCommand))));
    return localDataToken;
  }

  public static PullNodeDS getInstance()
  {
    if (instance == null)
      instance = new PullNodeDS();
    return instance;
  }

  private boolean updatePulllist(DataCommand paramDataCommand)
  {
    List localList = (List)paramDataCommand.getParam().get("nodes");
    if ((localList == null) || (localList.size() == 0))
      return false;
    while (true)
    {
      int i;
      try
      {
        SQLiteDatabase localSQLiteDatabase = DBManager.getInstance().getWritableDB("pullList");
        localSQLiteDatabase.beginTransaction();
        localSQLiteDatabase.execSQL("delete from pullList");
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
            localSQLiteDatabase.execSQL("insert into pullList(id,type,node) values(?,?,?)", arrayOfObject);
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
    return "PullNodeDS";
  }

  public IDataToken doCommand(DataCommand paramDataCommand, IDataRecvHandler paramIDataRecvHandler)
  {
    String str = paramDataCommand.getCurrentCommand();
    if (str.equalsIgnoreCase("updatedb_pull_node"))
      return doUpdateCommand(paramDataCommand);
    if (str.equalsIgnoreCase("getdb_pull_node"))
      return doAcquireCommand(paramDataCommand);
    if (str.equalsIgnoreCase("deletedb_pull_node"))
      return doDeleteCommand(paramDataCommand);
    return null;
  }

  public boolean isSynchronous(String paramString, Map<String, Object> paramMap)
  {
    return true;
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.qtradio.data.PullNodeDS
 * JD-Core Version:    0.6.2
 */