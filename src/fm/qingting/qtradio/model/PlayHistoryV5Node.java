package fm.qingting.qtradio.model;

public class PlayHistoryV5Node extends Node
{
  public String categoryId;
  public String channelId;
  public String channelName;
  public long playContent = 0L;
  public Node playNode;
  public long playTime;
  public String subCatId;

  public PlayHistoryV5Node()
  {
    this.nodeName = "playhistoryv5";
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.qtradio.model.PlayHistoryV5Node
 * JD-Core Version:    0.6.2
 */