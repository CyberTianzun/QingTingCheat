package fm.qingting.framework.model;

public abstract interface INavigationBarListener
{
  public static final int ITEM_TYPE_FILTER = 4;
  public static final int ITEM_TYPE_LEFT = 2;
  public static final int ITEM_TYPE_RIGHT = 3;
  public static final int ITEM_TYPE_TITLE = 1;

  public abstract void onItemClick(int paramInt);
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.framework.model.INavigationBarListener
 * JD-Core Version:    0.6.2
 */