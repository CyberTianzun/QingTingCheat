package fm.qingting.qtradio.data;

import android.database.sqlite.SQLiteDatabase;
import com.google.gson.Gson;
import fm.qingting.framework.data.DataCommand;
import fm.qingting.framework.data.DataToken;
import fm.qingting.framework.data.IDataParser;
import fm.qingting.framework.data.IDataRecvHandler;
import fm.qingting.framework.data.IDataSource;
import fm.qingting.framework.data.IDataToken;
import fm.qingting.framework.data.ListData;
import fm.qingting.framework.data.Result;
import fm.qingting.qtradio.model.ProgramSet;
import fm.qingting.qtradio.model.ReserveChannel;
import fm.qingting.qtradio.model.ReserveObject;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class ReservationDS
  implements IDataSource
{
  public static final long day = 86400000L;
  private static ReservationDS instance;
  public static final long week = 604800000L;
  private ArrayList<ReserveChannel> temporaryListData;

  private DataToken doAddReserveCommand(DataCommand paramDataCommand)
  {
    DataToken localDataToken = new DataToken();
    localDataToken.setData(new Result(true, null));
    ListData localListData = getReserves();
    ReserveChannel localReserveChannel1 = (ReserveChannel)paramDataCommand.getParam().get("reserve");
    if (localReserveChannel1 == null)
      return localDataToken;
    ArrayList localArrayList = new ArrayList();
    localArrayList.add(localReserveChannel1);
    Iterator localIterator = localListData.data.iterator();
    while (localIterator.hasNext())
    {
      ReserveChannel localReserveChannel2 = (ReserveChannel)localIterator.next();
      if (!localReserveChannel2.equals(localReserveChannel1))
        localArrayList.add(localReserveChannel2);
    }
    updateReserves(localArrayList);
    return localDataToken;
  }

  private DataToken doGetReserveCommand(DataCommand paramDataCommand)
  {
    DataToken localDataToken = new DataToken();
    localDataToken.setDataInfo(paramDataCommand);
    localDataToken.setData(new Result(true, getReserves()));
    return localDataToken;
  }

  private DataToken doRemoveReserveCommand(DataCommand paramDataCommand)
  {
    DataToken localDataToken = new DataToken();
    localDataToken.setData(new Result(true, null));
    ListData localListData = getReserves();
    ReserveChannel localReserveChannel = (ReserveChannel)paramDataCommand.getParam().get("reserve");
    if (localReserveChannel == null)
      return localDataToken;
    List localList = localListData.data;
    Iterator localIterator = localList.iterator();
    while (localIterator.hasNext())
      if (((ReserveChannel)localIterator.next()).equals(localReserveChannel))
        localIterator.remove();
    updateReserves(localList);
    return localDataToken;
  }

  private DataToken doUpdateReserveCommand(DataCommand paramDataCommand)
  {
    DataToken localDataToken = new DataToken();
    updateReserves((List)paramDataCommand.getParam().get("reserves"));
    localDataToken.setData(new Result(true, null));
    return localDataToken;
  }

  private IDataToken doexpiredReserveCommand(DataCommand paramDataCommand)
  {
    DataToken localDataToken = new DataToken();
    Result localResult = new Result(true, Boolean.valueOf(false));
    localDataToken.setData(localResult);
    ListData localListData = getReserves();
    ReserveChannel localReserveChannel1 = (ReserveChannel)paramDataCommand.getParam().get("reserve");
    if (localReserveChannel1 == null)
      return localDataToken;
    ArrayList localArrayList = new ArrayList();
    Iterator localIterator = localListData.data.iterator();
    while (localIterator.hasNext())
    {
      ReserveChannel localReserveChannel2 = (ReserveChannel)localIterator.next();
      if (localReserveChannel2.equals(localReserveChannel1))
        localReserveChannel2.aviable = false;
      localArrayList.add(localReserveChannel2);
    }
    updateReserves(localArrayList);
    localDataToken.setData(localResult);
    return localDataToken;
  }

  private IDataToken doreAddReserveCommand(DataCommand paramDataCommand)
  {
    DataToken localDataToken = new DataToken();
    localDataToken.setData(new Result(true, null));
    ListData localListData = getReserves();
    ReserveObject localReserveObject = (ReserveObject)paramDataCommand.getParam().get("reserve");
    if (localReserveObject == null)
      return localDataToken;
    ArrayList localArrayList = new ArrayList();
    Iterator localIterator = localListData.data.iterator();
    while (localIterator.hasNext())
    {
      ReserveChannel localReserveChannel = (ReserveChannel)localIterator.next();
      if (localReserveChannel.program.programId.equals(localReserveObject.pid))
      {
        localReserveChannel.aviable = true;
        localReserveChannel.reservetime = localReserveObject.nexttime;
      }
      localArrayList.add(localReserveChannel);
    }
    updateReserves(localArrayList);
    return localDataToken;
  }

  public static ReservationDS getInstance()
  {
    if (instance == null)
      instance = new ReservationDS();
    return instance;
  }

  // ERROR //
  private ListData getReserves()
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 151	fm/qingting/qtradio/data/ReservationDS:temporaryListData	Ljava/util/ArrayList;
    //   4: ifnonnull +189 -> 193
    //   7: new 58	java/util/ArrayList
    //   10: dup
    //   11: invokespecial 59	java/util/ArrayList:<init>	()V
    //   14: astore_1
    //   15: invokestatic 156	fm/qingting/qtradio/data/DBManager:getInstance	()Lfm/qingting/qtradio/data/DBManager;
    //   18: ldc 48
    //   20: invokevirtual 160	fm/qingting/qtradio/data/DBManager:getReadableDB	(Ljava/lang/String;)Landroid/database/sqlite/SQLiteDatabase;
    //   23: astore_2
    //   24: aload_2
    //   25: ldc 162
    //   27: aconst_null
    //   28: invokevirtual 168	android/database/sqlite/SQLiteDatabase:rawQuery	(Ljava/lang/String;[Ljava/lang/String;)Landroid/database/Cursor;
    //   31: astore 6
    //   33: new 170	com/google/gson/Gson
    //   36: dup
    //   37: invokespecial 171	com/google/gson/Gson:<init>	()V
    //   40: astore 7
    //   42: aload 6
    //   44: invokeinterface 176 1 0
    //   49: ifeq +122 -> 171
    //   52: aload 6
    //   54: iconst_0
    //   55: invokeinterface 180 2 0
    //   60: astore 8
    //   62: aload 7
    //   64: aload 8
    //   66: ldc 56
    //   68: invokevirtual 184	com/google/gson/Gson:fromJson	(Ljava/lang/String;Ljava/lang/Class;)Ljava/lang/Object;
    //   71: checkcast 56	fm/qingting/qtradio/model/ReserveChannel
    //   74: astore 10
    //   76: aload 10
    //   78: ifnull -36 -> 42
    //   81: aload_1
    //   82: aload 10
    //   84: invokevirtual 185	java/util/ArrayList:add	(Ljava/lang/Object;)Z
    //   87: pop
    //   88: goto -46 -> 42
    //   91: astore_3
    //   92: aload_1
    //   93: astore 4
    //   95: aload 4
    //   97: new 187	fm/qingting/qtradio/data/ComparatorItem
    //   100: dup
    //   101: invokestatic 193	java/lang/System:currentTimeMillis	()J
    //   104: ldc2_w 194
    //   107: ldiv
    //   108: invokespecial 198	fm/qingting/qtradio/data/ComparatorItem:<init>	(J)V
    //   111: invokestatic 204	java/util/Collections:sort	(Ljava/util/List;Ljava/util/Comparator;)V
    //   114: new 206	fm/qingting/framework/data/Navigation
    //   117: dup
    //   118: invokespecial 207	fm/qingting/framework/data/Navigation:<init>	()V
    //   121: astore 5
    //   123: aload 5
    //   125: aload 4
    //   127: invokevirtual 211	java/util/ArrayList:size	()I
    //   130: putfield 215	fm/qingting/framework/data/Navigation:count	I
    //   133: aload 5
    //   135: iconst_1
    //   136: putfield 218	fm/qingting/framework/data/Navigation:page	I
    //   139: aload 5
    //   141: ldc 219
    //   143: putfield 221	fm/qingting/framework/data/Navigation:size	I
    //   146: new 67	fm/qingting/framework/data/ListData
    //   149: dup
    //   150: aload 4
    //   152: aload 5
    //   154: invokespecial 224	fm/qingting/framework/data/ListData:<init>	(Ljava/util/List;Lfm/qingting/framework/data/Navigation;)V
    //   157: areturn
    //   158: astore 9
    //   160: aload 9
    //   162: invokevirtual 227	java/lang/Exception:printStackTrace	()V
    //   165: aconst_null
    //   166: astore 10
    //   168: goto -92 -> 76
    //   171: aload 6
    //   173: invokeinterface 230 1 0
    //   178: aload_0
    //   179: new 58	java/util/ArrayList
    //   182: dup
    //   183: aload_1
    //   184: invokespecial 233	java/util/ArrayList:<init>	(Ljava/util/Collection;)V
    //   187: putfield 151	fm/qingting/qtradio/data/ReservationDS:temporaryListData	Ljava/util/ArrayList;
    //   190: goto -98 -> 92
    //   193: new 58	java/util/ArrayList
    //   196: dup
    //   197: aload_0
    //   198: getfield 151	fm/qingting/qtradio/data/ReservationDS:temporaryListData	Ljava/util/ArrayList;
    //   201: invokespecial 233	java/util/ArrayList:<init>	(Ljava/util/Collection;)V
    //   204: astore 4
    //   206: goto -111 -> 95
    //
    // Exception table:
    //   from	to	target	type
    //   24	42	91	java/lang/Exception
    //   42	62	91	java/lang/Exception
    //   81	88	91	java/lang/Exception
    //   160	165	91	java/lang/Exception
    //   171	190	91	java/lang/Exception
    //   62	76	158	java/lang/Exception
  }

  private void updateReserves(List<ReserveChannel> paramList)
  {
    int i = 0;
    SQLiteDatabase localSQLiteDatabase = DBManager.getInstance().getWritableDB("reserve");
    localSQLiteDatabase.execSQL("delete from qtreserves");
    Iterator localIterator = paramList.iterator();
    Gson localGson = new Gson();
    try
    {
      while ((localIterator.hasNext()) && (i < 20))
      {
        String str = localGson.toJson(localIterator.next());
        Object[] arrayOfObject = new Object[2];
        arrayOfObject[0] = Integer.valueOf(i);
        arrayOfObject[1] = str;
        localSQLiteDatabase.execSQL("insert into qtreserves(id, program) values(?, ?)", arrayOfObject);
        i++;
      }
    }
    catch (Exception localException)
    {
      this.temporaryListData = new ArrayList(paramList);
    }
  }

  public void addParser(IDataParser paramIDataParser)
  {
  }

  public String dataSourceName()
  {
    return "Reserve";
  }

  public IDataToken doCommand(DataCommand paramDataCommand, IDataRecvHandler paramIDataRecvHandler)
  {
    String str = paramDataCommand.getCurrentCommand();
    if (str.equalsIgnoreCase("getReserves"))
      return doGetReserveCommand(paramDataCommand);
    if (str.equalsIgnoreCase("addReserve"))
      return doAddReserveCommand(paramDataCommand);
    if (str.equalsIgnoreCase("deleteReserve"))
      return doRemoveReserveCommand(paramDataCommand);
    if (str.equalsIgnoreCase("reReserve"))
      return doreAddReserveCommand(paramDataCommand);
    if (str.equalsIgnoreCase("expiredreserve"))
      return doexpiredReserveCommand(paramDataCommand);
    return null;
  }

  public boolean isSynchronous(String paramString, Map<String, Object> paramMap)
  {
    return true;
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.qtradio.data.ReservationDS
 * JD-Core Version:    0.6.2
 */