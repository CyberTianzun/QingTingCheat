package fm.qingting.qtradio.view.moreContentView;

public class SectionItem
{
  public static final int BANNER = 2;
  public static final int ITEM = 1;
  public static final int SECTION;
  public Object data;
  public final int type;

  public SectionItem(int paramInt, Object paramObject)
  {
    this.type = paramInt;
    this.data = paramObject;
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.qtradio.view.moreContentView.SectionItem
 * JD-Core Version:    0.6.2
 */