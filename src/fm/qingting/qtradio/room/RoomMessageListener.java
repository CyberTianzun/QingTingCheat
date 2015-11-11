package fm.qingting.qtradio.room;

public abstract interface RoomMessageListener
{
  public abstract void onConnect();

  public abstract void onConnectFailure();

  public abstract void onDisconnect();

  public abstract void onMessage();
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.qtradio.room.RoomMessageListener
 * JD-Core Version:    0.6.2
 */