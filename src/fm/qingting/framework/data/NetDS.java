package fm.qingting.framework.data;

import fm.qingting.framework.net.NetRequest;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import org.apache.http.StatusLine;

public class NetDS
  implements IDataSource, IDataRecvHandler
{
  private static NetDS instance = null;
  private Map<NetRequest, DataToken> _cbMap = new HashMap();
  private IMonitorMobAgent _mobAgent;
  private IDataParser _parser;

  private Map<String, Object> convertParam(Map<String, Object> paramMap)
  {
    Object localObject;
    if (paramMap == null)
      localObject = null;
    while (true)
    {
      return localObject;
      localObject = new HashMap();
      Iterator localIterator = paramMap.keySet().iterator();
      while (localIterator.hasNext())
      {
        String str = (String)localIterator.next();
        ((Map)localObject).put(str, paramMap.get(str));
      }
    }
  }

  public static NetDS getInstance()
  {
    try
    {
      if (instance == null)
        instance = new NetDS();
      NetDS localNetDS = instance;
      return localNetDS;
    }
    finally
    {
    }
  }

  public void addParser(IDataParser paramIDataParser)
  {
    paramIDataParser.setNextParser(this._parser);
    this._parser = paramIDataParser;
  }

  public String dataSourceName()
  {
    return "net";
  }

  public IDataToken doCommand(DataCommand paramDataCommand, IDataRecvHandler paramIDataRecvHandler)
  {
    try
    {
      DataToken localDataToken = new DataToken();
      localDataToken.setDataRecvHandler(paramIDataRecvHandler);
      localDataToken.setDataInfo(paramDataCommand);
      NetRequest localNetRequest = new NetRequest(paramDataCommand.getCurrentCommand(), this, convertParam(paramDataCommand.getExtendedParam()), paramDataCommand.getMethod(), paramDataCommand.getEncoding());
      Boolean localBoolean = (Boolean)paramDataCommand.getParam().get("use_time_stamp_cache");
      if ((localBoolean != null) && (localBoolean.booleanValue()))
        localNetRequest.enableTimeStampCache();
      this._cbMap.put(localNetRequest, localDataToken);
      new Thread(localNetRequest).start();
      return localDataToken;
    }
    finally
    {
    }
  }

  public boolean isSynchronous(String paramString, Map<String, Object> paramMap)
  {
    return false;
  }

  public void onRecvData(Object paramObject1, Object paramObject2, IDataToken paramIDataToken, Object paramObject3)
  {
    DataToken localDataToken = (DataToken)this._cbMap.get(paramObject2);
    this._cbMap.remove(paramObject2);
    if (localDataToken == null)
      return;
    DataCommand localDataCommand = (DataCommand)localDataToken.getDataInfo();
    Map localMap = (Map)paramObject3;
    localMap.put("command", localDataCommand);
    if (localMap != null)
    {
      String str = (String)localMap.get("request");
      ((Long)localMap.get("duration")).longValue();
      if (str != null)
        str.indexOf("googleapis");
    }
    Object localObject;
    if (this._parser == null)
      localObject = new Result(true, paramObject1);
    while (true)
    {
      StatusLine localStatusLine = (StatusLine)localMap.get("statusline");
      if (localObject != null)
        break label250;
      if ((localStatusLine == null) || (localStatusLine.getStatusCode() != 304))
        break;
      localDataToken.dispatchErrorEvent(DataError.DATA_304.getCode(), DataError.DATA_304.getMessage(), this, localDataCommand);
      return;
      try
      {
        Result localResult = this._parser.parse(localDataCommand.getType(), localDataCommand.getParam(), paramObject1);
        localObject = localResult;
      }
      catch (Exception localException)
      {
        localObject = null;
      }
    }
    if (this._mobAgent != null);
    localDataToken.dispatchErrorEvent(DataError.DATA_ERROR.getCode(), DataError.DATA_ERROR.getMessage(), this, localDataCommand);
    return;
    label250: if (this._mobAgent != null);
    localDataToken.dispatchDataEvent(localObject, this, localMap);
  }

  public void onRecvError(String paramString1, String paramString2, Object paramObject1, IDataToken paramIDataToken, Object paramObject2)
  {
    DataToken localDataToken = (DataToken)this._cbMap.get(paramObject1);
    this._cbMap.remove(paramObject1);
    if (localDataToken == null)
      return;
    DataCommand localDataCommand = (DataCommand)localDataToken.getDataInfo();
    Map localMap = (Map)paramObject2;
    if (localMap != null)
    {
      String str = (String)localMap.get("request");
      ((Long)localMap.get("duration")).longValue();
      if (str != null)
        str.indexOf("googleapis");
    }
    if (paramString1.equalsIgnoreCase("2000"))
      if ((this._mobAgent != null) && (localMap != null))
        this._mobAgent.sendMobAgentAPITIMEOUT(localDataCommand.getType(), localMap);
    while (true)
    {
      localDataToken.dispatchErrorEvent(paramString1, paramString2, this, localDataCommand);
      return;
      if (paramString1.equalsIgnoreCase("3000"))
      {
        if ((this._mobAgent != null) && (localMap != null))
          this._mobAgent.sendMobAgentAPIUNKNOWHOST(localDataCommand.getType(), localMap);
      }
      else if ((this._mobAgent != null) && (localMap != null))
        this._mobAgent.sendMobAgentAPIERROR(localDataCommand.getType(), "网络请求失败", localMap);
    }
  }

  public void setMobLister(IMonitorMobAgent paramIMonitorMobAgent)
  {
    this._mobAgent = paramIMonitorMobAgent;
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.framework.data.NetDS
 * JD-Core Version:    0.6.2
 */