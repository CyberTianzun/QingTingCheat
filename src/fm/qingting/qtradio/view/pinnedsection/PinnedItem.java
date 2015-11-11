package fm.qingting.qtradio.view.pinnedsection;

public class PinnedItem
{
  public static final int ITEM = 0;
  public static final int SECTION = 1;
  public Object data;
  public int listPosition;
  public int sectionPosition;
  public final int type;

  public PinnedItem(int paramInt, Object paramObject)
  {
    this.type = paramInt;
    this.data = paramObject;
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.qtradio.view.pinnedsection.PinnedItem
 * JD-Core Version:    0.6.2
 */