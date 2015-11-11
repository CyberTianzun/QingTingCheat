package fm.qingting.qtradio.model;

public class PlayHistoryNode extends Node
{
  public int categoryId;
  public int channelId;
  public String channelName;
  public String channelThumb;
  public long playContent = 0L;
  public Node playNode;
  public long playTime;
  public int subCatId;

  public PlayHistoryNode()
  {
    this.nodeName = "playhistory";
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.qtradio.model.PlayHistoryNode
 * JD-Core Version:    0.6.2
 */