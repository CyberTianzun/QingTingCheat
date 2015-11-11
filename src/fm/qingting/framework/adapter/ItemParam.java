package fm.qingting.framework.adapter;

public class ItemParam
{
  public Object param;
  public int position;
  public Object target;
  public String type = "";

  public ItemParam(String paramString, int paramInt, Object paramObject1, Object paramObject2)
  {
    this.type = paramString;
    this.position = paramInt;
    this.target = paramObject1;
    this.param = paramObject2;
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.framework.adapter.ItemParam
 * JD-Core Version:    0.6.2
 */