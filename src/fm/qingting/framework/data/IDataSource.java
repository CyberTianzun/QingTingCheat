package fm.qingting.framework.data;

import java.util.Map;

public abstract interface IDataSource
{
  public abstract void addParser(IDataParser paramIDataParser);

  public abstract String dataSourceName();

  public abstract IDataToken doCommand(DataCommand paramDataCommand, IDataRecvHandler paramIDataRecvHandler);

  public abstract boolean isSynchronous(String paramString, Map<String, Object> paramMap);
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.framework.data.IDataSource
 * JD-Core Version:    0.6.2
 */