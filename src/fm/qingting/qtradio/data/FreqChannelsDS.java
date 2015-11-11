package fm.qingting.qtradio.data;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import fm.qingting.framework.data.DataCommand;
import fm.qingting.framework.data.DataToken;
import fm.qingting.framework.data.IDataParser;
import fm.qingting.framework.data.IDataRecvHandler;
import fm.qingting.framework.data.IDataSource;
import fm.qingting.framework.data.IDataToken;
import fm.qingting.framework.data.Result;
import fm.qingting.qtradio.model.FreqChannel;
import fm.qingting.qtradio.model.FreqChannelInfoNode;
import fm.qingting.qtradio.model.Node;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class FreqChannelsDS
  implements IDataSource
{
  private static FreqChannelsDS instance;

  private List<FreqChannel> acquireFreqChannels(DataCommand paramDataCommand)
  {
    String str1 = (String)paramDataCommand.getParam().get("city");
    ArrayList localArrayList = new ArrayList();
    try
    {
      String str2 = "select * from freqChannels where city = '" + str1 + "'";
      Cursor localCursor = DBManager.getInstance().getReadableDB("freqChannels").rawQuery(str2, null);
      while (localCursor.moveToNext())
      {
        int i = localCursor.getColumnIndex("channelid");
        int j = localCursor.getColumnIndex("channelname");
        int k = localCursor.getColumnIndex("freq");
        int m = localCursor.getColumnIndex("city");
        FreqChannel localFreqChannel = new FreqChannel();
        localFreqChannel.channelId = localCursor.getInt(i);
        localFreqChannel.channelName = localCursor.getString(j);
        localFreqChannel.channelFreq = localCursor.getString(k);
        localFreqChannel.city = localCursor.getString(m);
        localArrayList.add(localFreqChannel);
      }
      localCursor.close();
      return localArrayList;
    }
    catch (Exception localException)
    {
    }
    return localArrayList;
  }

  private boolean deleteFreqChannels(DataCommand paramDataCommand)
  {
    try
    {
      DBManager.getInstance().getWritableDB("freqChannels").execSQL("delete from freqChannels");
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
    localDataToken.setData(new Result(true, acquireFreqChannels(paramDataCommand)));
    return localDataToken;
  }

  private DataToken doDeleteCommand(DataCommand paramDataCommand)
  {
    DataToken localDataToken = new DataToken();
    localDataToken.setDataInfo(paramDataCommand);
    localDataToken.setData(new Result(true, Boolean.valueOf(deleteFreqChannels(paramDataCommand))));
    return localDataToken;
  }

  private DataToken doInsertCommand(DataCommand paramDataCommand)
  {
    DataToken localDataToken = new DataToken();
    localDataToken.setDataInfo(paramDataCommand);
    localDataToken.setData(new Result(true, Boolean.valueOf(insertFreqChannels(paramDataCommand))));
    return localDataToken;
  }

  public static FreqChannelsDS getInstance()
  {
    if (instance == null)
      instance = new FreqChannelsDS();
    return instance;
  }

  private boolean insertFreqChannels(DataCommand paramDataCommand)
  {
    Node localNode = (Node)paramDataCommand.getParam().get("freqs");
    if (localNode == null)
      return false;
    FreqChannelInfoNode localFreqChannelInfoNode = (FreqChannelInfoNode)localNode;
    try
    {
      SQLiteDatabase localSQLiteDatabase = DBManager.getInstance().getWritableDB("freqChannels");
      Iterator localIterator = localFreqChannelInfoNode.mapFreqChannel.entrySet().iterator();
      while (localIterator.hasNext())
      {
        List localList = (List)((Map.Entry)localIterator.next()).getValue();
        for (int i = 0; i < localList.size(); i++)
        {
          Object[] arrayOfObject = new Object[4];
          arrayOfObject[0] = Integer.valueOf(((FreqChannel)localList.get(i)).channelId);
          arrayOfObject[1] = ((FreqChannel)localList.get(i)).channelName;
          arrayOfObject[2] = ((FreqChannel)localList.get(i)).city;
          arrayOfObject[3] = ((FreqChannel)localList.get(i)).channelFreq;
          localSQLiteDatabase.execSQL("insert into freqChannels(channelid,channelname,city,freq) values(?,?,?,?)", arrayOfObject);
        }
      }
    }
    catch (Exception localException)
    {
      return false;
    }
    return true;
  }

  public void addParser(IDataParser paramIDataParser)
  {
  }

  public String dataSourceName()
  {
    return "FreqChannelsDS";
  }

  public IDataToken doCommand(DataCommand paramDataCommand, IDataRecvHandler paramIDataRecvHandler)
  {
    String str = paramDataCommand.getCurrentCommand();
    if (str.equalsIgnoreCase("insertdb_freq_channels"))
      return doInsertCommand(paramDataCommand);
    if (str.equalsIgnoreCase("getdb_freq_channels"))
      return doAcquireCommand(paramDataCommand);
    if (str.equalsIgnoreCase("deletedb_freq_channels"))
      return doDeleteCommand(paramDataCommand);
    return null;
  }

  public boolean isSynchronous(String paramString, Map<String, Object> paramMap)
  {
    return true;
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.qtradio.data.FreqChannelsDS
 * JD-Core Version:    0.6.2
 */