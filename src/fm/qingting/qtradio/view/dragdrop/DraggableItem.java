package fm.qingting.qtradio.view.dragdrop;

public abstract interface DraggableItem
{
  public static final int DRAG = 1;
  public static final int NORMAL;

  public abstract int getState();

  public abstract void setState(int paramInt);
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.qtradio.view.dragdrop.DraggableItem
 * JD-Core Version:    0.6.2
 */