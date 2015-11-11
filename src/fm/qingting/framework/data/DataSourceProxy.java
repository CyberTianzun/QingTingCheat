package fm.qingting.framework.data;

import java.util.HashMap;
import java.util.Map;

public class DataSourceProxy
  implements IDataSourceProxy, IDataRecvHandler
{
  protected HashMap<IDataToken, DataToken> requests = new HashMap();

  public void addParser(IDataParser paramIDataParser)
  {
  }

  public String dataSourceName()
  {
    return "DataSourceProxy";
  }

  public IDataToken doCommand(DataCommand paramDataCommand, IDataRecvHandler paramIDataRecvHandler)
  {
    return null;
  }

  public IDataToken doCommand(DataCommand paramDataCommand, IDataRecvHandler paramIDataRecvHandler, IDataSource paramIDataSource)
  {
    DataToken localDataToken = new DataToken();
    localDataToken.setDataRecvHandler(paramIDataRecvHandler);
    localDataToken.setDataInfo(paramDataCommand);
    IDataToken localIDataToken = paramIDataSource.doCommand(paramDataCommand, this);
    this.requests.put(localIDataToken, localDataToken);
    return localDataToken;
  }

  public boolean isSynchronous(String paramString, Map<String, Object> paramMap)
  {
    return false;
  }

  public boolean isSynchronous(String paramString, Map<String, Object> paramMap, IDataSource paramIDataSource)
  {
    return paramIDataSource.isSynchronous(paramString, paramMap);
  }

  public void onRecvData(Object paramObject1, Object paramObject2, IDataToken paramIDataToken, Object paramObject3)
  {
    DataToken localDataToken = (DataToken)this.requests.remove(paramIDataToken);
    if (localDataToken == null)
      return;
    localDataToken.dispatchDataEvent(paramObject1, this, paramObject3);
  }

  public void onRecvError(String paramString1, String paramString2, Object paramObject1, IDataToken paramIDataToken, Object paramObject2)
  {
    DataToken localDataToken = (DataToken)this.requests.remove(paramIDataToken);
    if (localDataToken == null)
      return;
    localDataToken.dispatchErrorEvent(paramString1, paramString2, paramObject1, paramObject2);
  }

  public boolean proxyAvailable(DataCommand paramDataCommand, IDataSource paramIDataSource)
  {
    return true;
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.framework.data.DataSourceProxy
 * JD-Core Version:    0.6.2
 */