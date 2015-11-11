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
import fm.qingting.qtradio.model.PlayHistoryNode;
import java.util.List;
import java.util.Map;

public class PlayHistoryDS
  implements IDataSource
{
  private static PlayHistoryDS instance;

  // ERROR //
  private List<PlayHistoryNode> acquirePlayHistory(DataCommand paramDataCommand)
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
    //   43: ifeq +373 -> 416
    //   46: aload 4
    //   48: ldc 50
    //   50: invokeinterface 54 2 0
    //   55: istore 7
    //   57: aload 4
    //   59: ldc 56
    //   61: invokeinterface 54 2 0
    //   66: istore 8
    //   68: aload 4
    //   70: ldc 58
    //   72: invokeinterface 54 2 0
    //   77: istore 9
    //   79: aload 4
    //   81: ldc 60
    //   83: invokeinterface 54 2 0
    //   88: istore 10
    //   90: aload 4
    //   92: ldc 62
    //   94: invokeinterface 54 2 0
    //   99: istore 11
    //   101: aload 4
    //   103: ldc 64
    //   105: invokeinterface 54 2 0
    //   110: istore 12
    //   112: aload 4
    //   114: ldc 66
    //   116: invokeinterface 54 2 0
    //   121: istore 13
    //   123: aload 4
    //   125: iload 7
    //   127: invokeinterface 70 2 0
    //   132: astore 14
    //   134: aload 4
    //   136: iload 8
    //   138: invokeinterface 74 2 0
    //   143: lstore 15
    //   145: aload 4
    //   147: iload 9
    //   149: invokeinterface 70 2 0
    //   154: astore 17
    //   156: aload 4
    //   158: iload 10
    //   160: invokeinterface 78 2 0
    //   165: istore 18
    //   167: aload 4
    //   169: iload 11
    //   171: invokeinterface 78 2 0
    //   176: istore 19
    //   178: aload 4
    //   180: iload 12
    //   182: invokeinterface 74 2 0
    //   187: lstore 20
    //   189: aload 4
    //   191: iload 13
    //   193: invokeinterface 70 2 0
    //   198: astore 22
    //   200: aload 4
    //   202: aload 4
    //   204: ldc 80
    //   206: invokeinterface 54 2 0
    //   211: invokeinterface 70 2 0
    //   216: astore 23
    //   218: iconst_0
    //   219: istore 24
    //   221: iload 24
    //   223: aload_2
    //   224: invokeinterface 86 1 0
    //   229: if_icmpge +22 -> 251
    //   232: aload_2
    //   233: iload 24
    //   235: invokeinterface 90 2 0
    //   240: checkcast 92	fm/qingting/qtradio/model/PlayHistoryNode
    //   243: getfield 95	fm/qingting/qtradio/model/PlayHistoryNode:channelId	I
    //   246: iload 18
    //   248: if_icmpne +187 -> 435
    //   251: iload 24
    //   253: aload_2
    //   254: invokeinterface 86 1 0
    //   259: if_icmpne -223 -> 36
    //   262: aload 17
    //   264: ldc 97
    //   266: invokevirtual 103	java/lang/String:equalsIgnoreCase	(Ljava/lang/String;)Z
    //   269: istore 25
    //   271: iload 25
    //   273: ifeq +103 -> 376
    //   276: aload 5
    //   278: aload 14
    //   280: ldc 105
    //   282: invokevirtual 109	com/google/gson/Gson:fromJson	(Ljava/lang/String;Ljava/lang/Class;)Ljava/lang/Object;
    //   285: checkcast 105	fm/qingting/qtradio/model/ProgramNode
    //   288: astore 27
    //   290: new 92	fm/qingting/qtradio/model/PlayHistoryNode
    //   293: dup
    //   294: invokespecial 110	fm/qingting/qtradio/model/PlayHistoryNode:<init>	()V
    //   297: astore 28
    //   299: aload 27
    //   301: ifnull +10 -> 311
    //   304: aload 27
    //   306: aload 28
    //   308: putfield 116	fm/qingting/qtradio/model/Node:parent	Lfm/qingting/qtradio/model/Node;
    //   311: aload 28
    //   313: aload 27
    //   315: putfield 118	fm/qingting/qtradio/model/PlayHistoryNode:playNode	Lfm/qingting/qtradio/model/Node;
    //   318: aload 28
    //   320: lload 15
    //   322: putfield 122	fm/qingting/qtradio/model/PlayHistoryNode:playTime	J
    //   325: aload 28
    //   327: iload 19
    //   329: putfield 125	fm/qingting/qtradio/model/PlayHistoryNode:categoryId	I
    //   332: aload 28
    //   334: iload 18
    //   336: putfield 95	fm/qingting/qtradio/model/PlayHistoryNode:channelId	I
    //   339: aload 28
    //   341: lload 20
    //   343: putfield 128	fm/qingting/qtradio/model/PlayHistoryNode:playContent	J
    //   346: aload 28
    //   348: aload 22
    //   350: putfield 131	fm/qingting/qtradio/model/PlayHistoryNode:channelName	Ljava/lang/String;
    //   353: aload 28
    //   355: aload 23
    //   357: putfield 133	fm/qingting/qtradio/model/PlayHistoryNode:channelThumb	Ljava/lang/String;
    //   360: aload_2
    //   361: aload 28
    //   363: invokeinterface 137 2 0
    //   368: pop
    //   369: aload 27
    //   371: astore 6
    //   373: goto -337 -> 36
    //   376: aload 17
    //   378: ldc 139
    //   380: invokevirtual 103	java/lang/String:equalsIgnoreCase	(Ljava/lang/String;)Z
    //   383: istore 26
    //   385: iload 26
    //   387: ifeq +41 -> 428
    //   390: aload 5
    //   392: aload 14
    //   394: ldc 141
    //   396: invokevirtual 109	com/google/gson/Gson:fromJson	(Ljava/lang/String;Ljava/lang/Class;)Ljava/lang/Object;
    //   399: checkcast 141	fm/qingting/qtradio/model/ChannelNode
    //   402: astore 27
    //   404: goto -114 -> 290
    //   407: astore 30
    //   409: aload 6
    //   411: astore 27
    //   413: goto -123 -> 290
    //   416: aload 4
    //   418: invokeinterface 144 1 0
    //   423: aload_2
    //   424: areturn
    //   425: astore_3
    //   426: aconst_null
    //   427: areturn
    //   428: aload 6
    //   430: astore 27
    //   432: goto -142 -> 290
    //   435: iinc 24 1
    //   438: goto -217 -> 221
    //   441: astore 31
    //   443: aload 6
    //   445: astore 27
    //   447: goto -157 -> 290
    //
    // Exception table:
    //   from	to	target	type
    //   390	404	407	java/lang/Exception
    //   0	33	425	java/lang/Exception
    //   36	218	425	java/lang/Exception
    //   221	251	425	java/lang/Exception
    //   251	271	425	java/lang/Exception
    //   290	299	425	java/lang/Exception
    //   304	311	425	java/lang/Exception
    //   311	369	425	java/lang/Exception
    //   376	385	425	java/lang/Exception
    //   416	423	425	java/lang/Exception
    //   276	290	441	java/lang/Exception
  }

  private boolean delInsertPlayHistory(DataCommand paramDataCommand)
  {
    List localList = (List)paramDataCommand.getParam().get("playhistory");
    if ((localList == null) || (localList.size() == 0))
      return false;
    int i = 0;
    try
    {
      SQLiteDatabase localSQLiteDatabase = DBManager.getInstance().getWritableDB("playhistory");
      localSQLiteDatabase.beginTransaction();
      localSQLiteDatabase.execSQL("delete from playhistory");
      Gson localGson = new Gson();
      int j = 0;
      while (j < localList.size())
      {
        PlayHistoryNode localPlayHistoryNode = (PlayHistoryNode)localList.get(j);
        long l1 = localPlayHistoryNode.playTime;
        String str1 = localGson.toJson(localPlayHistoryNode.playNode);
        int k = localPlayHistoryNode.channelId;
        int m = localPlayHistoryNode.categoryId;
        long l2 = localPlayHistoryNode.playContent;
        String str2 = localPlayHistoryNode.channelName;
        String str3 = localPlayHistoryNode.channelThumb;
        int n = i + 1;
        Object[] arrayOfObject = new Object[10];
        arrayOfObject[0] = Integer.valueOf(n);
        arrayOfObject[1] = ((PlayHistoryNode)localList.get(j)).playNode.nodeName;
        arrayOfObject[2] = str1;
        arrayOfObject[3] = Long.valueOf(l1);
        arrayOfObject[4] = Integer.valueOf(k);
        arrayOfObject[5] = Integer.valueOf(m);
        arrayOfObject[6] = Integer.valueOf(localPlayHistoryNode.subCatId);
        arrayOfObject[7] = Long.valueOf(l2);
        arrayOfObject[8] = str2;
        arrayOfObject[9] = str3;
        localSQLiteDatabase.execSQL("insert into playhistory(id,nodename,playNode,time,channelId,catId,subCatId,playPosition,channelName,channelThumb) values(?,?,?, ?, ?,?,?,?,?,?)", arrayOfObject);
        j++;
        i = n;
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

  private boolean deletePlayHistory(DataCommand paramDataCommand)
  {
    try
    {
      DBManager.getInstance().getWritableDB("playhistory").execSQL("delete from playhistory");
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
    localDataToken.setData(new Result(true, acquirePlayHistory(paramDataCommand)));
    return localDataToken;
  }

  private DataToken doDelInsertCommand(DataCommand paramDataCommand)
  {
    DataToken localDataToken = new DataToken();
    localDataToken.setDataInfo(paramDataCommand);
    localDataToken.setData(new Result(true, Boolean.valueOf(delInsertPlayHistory(paramDataCommand))));
    return localDataToken;
  }

  private DataToken doDeleteCommand(DataCommand paramDataCommand)
  {
    DataToken localDataToken = new DataToken();
    localDataToken.setDataInfo(paramDataCommand);
    localDataToken.setData(new Result(true, Boolean.valueOf(deletePlayHistory(paramDataCommand))));
    return localDataToken;
  }

  private DataToken doInsertCommand(DataCommand paramDataCommand)
  {
    DataToken localDataToken = new DataToken();
    localDataToken.setDataInfo(paramDataCommand);
    localDataToken.setData(new Result(true, Boolean.valueOf(insertPlayHistory(paramDataCommand))));
    return localDataToken;
  }

  public static PlayHistoryDS getInstance()
  {
    if (instance == null)
      instance = new PlayHistoryDS();
    return instance;
  }

  private boolean insertPlayHistory(DataCommand paramDataCommand)
  {
    List localList = (List)paramDataCommand.getParam().get("playhistory");
    if ((localList == null) || (localList.size() == 0))
      return false;
    try
    {
      SQLiteDatabase localSQLiteDatabase = DBManager.getInstance().getWritableDB("playhistory");
      localSQLiteDatabase.beginTransaction();
      Gson localGson = new Gson();
      int i = 0;
      for (int j = 0; j < localList.size(); j++)
      {
        PlayHistoryNode localPlayHistoryNode = (PlayHistoryNode)localList.get(j);
        long l1 = localPlayHistoryNode.playTime;
        String str1 = localGson.toJson(localPlayHistoryNode.playNode);
        int k = localPlayHistoryNode.channelId;
        int m = localPlayHistoryNode.categoryId;
        long l2 = localPlayHistoryNode.playContent;
        String str2 = localPlayHistoryNode.channelName;
        String str3 = localPlayHistoryNode.channelThumb;
        i++;
        Object[] arrayOfObject = new Object[9];
        arrayOfObject[0] = Integer.valueOf(i);
        arrayOfObject[1] = ((PlayHistoryNode)localList.get(j)).playNode.nodeName;
        arrayOfObject[2] = str1;
        arrayOfObject[3] = Long.valueOf(l1);
        arrayOfObject[4] = Integer.valueOf(k);
        arrayOfObject[5] = Integer.valueOf(m);
        arrayOfObject[6] = Long.valueOf(l2);
        arrayOfObject[7] = str2;
        arrayOfObject[8] = str3;
        localSQLiteDatabase.execSQL("insert into playhistory(id,nodename,playNode,time,channelId,catId,playPosition,channelName,channelThumb) values(?,?, ?, ?,?,?,?,?,?)", arrayOfObject);
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
    return "PlayHistoryDS";
  }

  public IDataToken doCommand(DataCommand paramDataCommand, IDataRecvHandler paramIDataRecvHandler)
  {
    String str = paramDataCommand.getCurrentCommand();
    if (str.equalsIgnoreCase("insertdb_play_history"))
      return doInsertCommand(paramDataCommand);
    if (str.equalsIgnoreCase("getdb_play_history"))
      return doAcquireCommand(paramDataCommand);
    if (str.equalsIgnoreCase("deletedb_play_history"))
      return doDeleteCommand(paramDataCommand);
    if (str.equalsIgnoreCase("delinsertdb_play_history"))
      return doDelInsertCommand(paramDataCommand);
    return null;
  }

  public boolean isSynchronous(String paramString, Map<String, Object> paramMap)
  {
    return true;
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.qtradio.data.PlayHistoryDS
 * JD-Core Version:    0.6.2
 */