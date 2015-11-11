package fm.qingting.async.http.libcore;

public enum ResponseSource
{
  static
  {
    ResponseSource[] arrayOfResponseSource = new ResponseSource[3];
    arrayOfResponseSource[0] = CACHE;
    arrayOfResponseSource[1] = CONDITIONAL_CACHE;
    arrayOfResponseSource[2] = NETWORK;
  }

  public boolean requiresConnection()
  {
    return (this == CONDITIONAL_CACHE) || (this == NETWORK);
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.async.http.libcore.ResponseSource
 * JD-Core Version:    0.6.2
 */