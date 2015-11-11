package fm.qingting.framework.data;

public enum DataError
{
  private String _code;
  private String _message;

  static
  {
    CONFIG_ERROR = new DataError("CONFIG_ERROR", 3, "1002", "配置文件错误");
    DATASOURCE_ERROR = new DataError("DATASOURCE_ERROR", 4, "1003", "无效数据源");
    DATA_ERROR = new DataError("DATA_ERROR", 5, "1004", "数据错误");
    DataError[] arrayOfDataError = new DataError[6];
    arrayOfDataError[0] = DATA_304;
    arrayOfDataError[1] = NETWORK_ERROR;
    arrayOfDataError[2] = REQUEST_ERROR;
    arrayOfDataError[3] = CONFIG_ERROR;
    arrayOfDataError[4] = DATASOURCE_ERROR;
    arrayOfDataError[5] = DATA_ERROR;
  }

  private DataError(String arg3, String arg4)
  {
    Object localObject1;
    this._code = localObject1;
    Object localObject2;
    this._message = localObject2;
  }

  public String getCode()
  {
    return this._code;
  }

  public String getMessage()
  {
    return this._message;
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.framework.data.DataError
 * JD-Core Version:    0.6.2
 */