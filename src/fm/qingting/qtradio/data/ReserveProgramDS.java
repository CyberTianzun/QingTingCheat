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
import fm.qingting.qtradio.model.ReserveNode;
import java.util.List;
import java.util.Map;

public class ReserveProgramDS
  implements IDataSource
{
  private static ReserveProgramDS instance;
  private int MAX_COUNT = 10;

  // ERROR //
  private List<ReserveNode> acquireReserveProgram(DataCommand paramDataCommand)
  {
    // Byte code:
    //   0: new 22	java/util/ArrayList
    //   3: dup
    //   4: invokespecial 23	java/util/ArrayList:<init>	()V
    //   7: astore_2
    //   8: invokestatic 29	fm/qingting/qtradio/data/DBManager:getInstance	()Lfm/qingting/qtradio/data/DBManager;
    //   11: ldc 31
    //   13: invokevirtual 35	fm/qingting/qtradio/data/DBManager:getReadableDB	(Ljava/lang/String;)Landroid/database/sqlite/SQLiteDatabase;
    //   16: ldc 37
    //   18: aconst_null
    //   19: invokevirtual 43	android/database/sqlite/SQLiteDatabase:rawQuery	(Ljava/lang/String;[Ljava/lang/String;)Landroid/database/Cursor;
    //   22: astore 4
    //   24: iconst_0
    //   25: istore 5
    //   27: new 45	com/google/gson/Gson
    //   30: dup
    //   31: invokespecial 46	com/google/gson/Gson:<init>	()V
    //   34: astore 6
    //   36: aconst_null
    //   37: astore 7
    //   39: aload 4
    //   41: invokeinterface 52 1 0
    //   46: ifeq +235 -> 281
    //   49: iload 5
    //   51: aload_0
    //   52: getfield 16	fm/qingting/qtradio/data/ReserveProgramDS:MAX_COUNT	I
    //   55: if_icmpge +226 -> 281
    //   58: aload 4
    //   60: ldc 54
    //   62: invokeinterface 58 2 0
    //   67: istore 8
    //   69: aload 4
    //   71: ldc 60
    //   73: invokeinterface 58 2 0
    //   78: istore 9
    //   80: aload 4
    //   82: ldc 62
    //   84: invokeinterface 58 2 0
    //   89: istore 10
    //   91: aload 4
    //   93: ldc 64
    //   95: invokeinterface 58 2 0
    //   100: istore 11
    //   102: aload 4
    //   104: ldc 66
    //   106: invokeinterface 58 2 0
    //   111: istore 12
    //   113: aload 4
    //   115: iload 8
    //   117: invokeinterface 70 2 0
    //   122: astore 13
    //   124: aload 4
    //   126: iload 9
    //   128: invokeinterface 70 2 0
    //   133: astore 14
    //   135: aload 4
    //   137: iload 10
    //   139: invokeinterface 70 2 0
    //   144: astore 15
    //   146: aload 4
    //   148: iload 11
    //   150: invokeinterface 70 2 0
    //   155: astore 16
    //   157: aload 4
    //   159: iload 12
    //   161: invokeinterface 70 2 0
    //   166: astore 17
    //   168: aload 6
    //   170: aload 13
    //   172: ldc 72
    //   174: invokevirtual 76	com/google/gson/Gson:fromJson	(Ljava/lang/String;Ljava/lang/Class;)Ljava/lang/Object;
    //   177: checkcast 78	fm/qingting/qtradio/model/Node
    //   180: astore 22
    //   182: aload 22
    //   184: astore 7
    //   186: aload 7
    //   188: ifnull +114 -> 302
    //   191: new 80	fm/qingting/qtradio/model/ReserveNode
    //   194: dup
    //   195: invokespecial 81	fm/qingting/qtradio/model/ReserveNode:<init>	()V
    //   198: astore 20
    //   200: aload 20
    //   202: aload 7
    //   204: putfield 85	fm/qingting/qtradio/model/ReserveNode:reserveNode	Lfm/qingting/qtradio/model/Node;
    //   207: aload 20
    //   209: aload 14
    //   211: invokestatic 91	java/lang/Long:valueOf	(Ljava/lang/String;)Ljava/lang/Long;
    //   214: invokevirtual 95	java/lang/Long:longValue	()J
    //   217: putfield 99	fm/qingting/qtradio/model/ReserveNode:reserveTime	J
    //   220: aload 20
    //   222: aload 15
    //   224: invokestatic 104	java/lang/Integer:valueOf	(Ljava/lang/String;)Ljava/lang/Integer;
    //   227: invokevirtual 108	java/lang/Integer:intValue	()I
    //   230: putfield 110	fm/qingting/qtradio/model/ReserveNode:channelId	I
    //   233: aload 20
    //   235: aload 16
    //   237: putfield 113	fm/qingting/qtradio/model/ReserveNode:programName	Ljava/lang/String;
    //   240: aload 20
    //   242: aload 17
    //   244: invokestatic 104	java/lang/Integer:valueOf	(Ljava/lang/String;)Ljava/lang/Integer;
    //   247: invokevirtual 108	java/lang/Integer:intValue	()I
    //   250: putfield 116	fm/qingting/qtradio/model/ReserveNode:uniqueId	I
    //   253: aload 20
    //   255: getfield 99	fm/qingting/qtradio/model/ReserveNode:reserveTime	J
    //   258: invokestatic 121	java/lang/System:currentTimeMillis	()J
    //   261: ldc2_w 122
    //   264: ldiv
    //   265: lcmp
    //   266: ifle +43 -> 309
    //   269: aload_2
    //   270: aload 20
    //   272: invokeinterface 129 2 0
    //   277: pop
    //   278: goto +31 -> 309
    //   281: aload 4
    //   283: invokeinterface 132 1 0
    //   288: aload_2
    //   289: areturn
    //   290: astore 23
    //   292: aconst_null
    //   293: areturn
    //   294: astore_3
    //   295: aload_2
    //   296: areturn
    //   297: astore 18
    //   299: goto -113 -> 186
    //   302: iload 5
    //   304: istore 19
    //   306: goto +9 -> 315
    //   309: iload 5
    //   311: iconst_1
    //   312: iadd
    //   313: istore 19
    //   315: iload 19
    //   317: istore 5
    //   319: goto -280 -> 39
    //
    // Exception table:
    //   from	to	target	type
    //   0	8	290	java/lang/Exception
    //   8	24	294	java/lang/Exception
    //   27	36	294	java/lang/Exception
    //   39	168	294	java/lang/Exception
    //   191	278	294	java/lang/Exception
    //   281	288	294	java/lang/Exception
    //   168	182	297	java/lang/Exception
  }

  private boolean deleteReserveProgram(DataCommand paramDataCommand)
  {
    try
    {
      DBManager.getInstance().getWritableDB("reserveprogram").execSQL("delete from reserveprogram");
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
    localDataToken.setData(new Result(true, acquireReserveProgram(paramDataCommand)));
    return localDataToken;
  }

  private DataToken doDeleteCommand(DataCommand paramDataCommand)
  {
    DataToken localDataToken = new DataToken();
    localDataToken.setDataInfo(paramDataCommand);
    localDataToken.setData(new Result(true, Boolean.valueOf(deleteReserveProgram(paramDataCommand))));
    return localDataToken;
  }

  private DataToken doInsertCommand(DataCommand paramDataCommand)
  {
    DataToken localDataToken = new DataToken();
    localDataToken.setDataInfo(paramDataCommand);
    localDataToken.setData(new Result(true, Boolean.valueOf(insertReserveProgram(paramDataCommand))));
    return localDataToken;
  }

  public static ReserveProgramDS getInstance()
  {
    if (instance == null)
      instance = new ReserveProgramDS();
    return instance;
  }

  private boolean insertReserveProgram(DataCommand paramDataCommand)
  {
    List localList = (List)paramDataCommand.getParam().get("reserveprogram");
    if ((localList == null) || (localList.size() == 0))
      return false;
    try
    {
      SQLiteDatabase localSQLiteDatabase = DBManager.getInstance().getWritableDB("reserveprogram");
      localSQLiteDatabase.beginTransaction();
      Gson localGson = new Gson();
      for (int i = 0; i < localList.size(); i++)
      {
        ReserveNode localReserveNode = (ReserveNode)localList.get(i);
        int j = localReserveNode.channelId;
        String str1 = localReserveNode.programName;
        int k = localReserveNode.uniqueId;
        long l = localReserveNode.reserveTime;
        String str2 = localGson.toJson(localReserveNode.reserveNode);
        Object[] arrayOfObject = new Object[5];
        arrayOfObject[0] = Long.valueOf(l);
        arrayOfObject[1] = str2;
        arrayOfObject[2] = Integer.valueOf(j);
        arrayOfObject[3] = str1;
        arrayOfObject[4] = Integer.valueOf(k);
        localSQLiteDatabase.execSQL("insert into reserveprogram(time, reserveProgram,channelId,programName,programId) values(?,?,?,?,?)", arrayOfObject);
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
    return "ReserveProgramDS";
  }

  public IDataToken doCommand(DataCommand paramDataCommand, IDataRecvHandler paramIDataRecvHandler)
  {
    String str = paramDataCommand.getCurrentCommand();
    if (str.equalsIgnoreCase("insert_reserve_program"))
      return doInsertCommand(paramDataCommand);
    if (str.equalsIgnoreCase("get_reserve_program"))
      return doAcquireCommand(paramDataCommand);
    if (str.equalsIgnoreCase("delete_reserve_program"))
      return doDeleteCommand(paramDataCommand);
    return null;
  }

  public boolean isSynchronous(String paramString, Map<String, Object> paramMap)
  {
    return true;
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.qtradio.data.ReserveProgramDS
 * JD-Core Version:    0.6.2
 */