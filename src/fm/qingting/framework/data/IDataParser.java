package fm.qingting.framework.data;

public abstract interface IDataParser
{
  public abstract IDataParser getNextParser();

  public abstract Result parse(String paramString, Object paramObject1, Object paramObject2);

  public abstract void setNextParser(IDataParser paramIDataParser);
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.framework.data.IDataParser
 * JD-Core Version:    0.6.2
 */