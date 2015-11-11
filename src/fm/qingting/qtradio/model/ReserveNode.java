package fm.qingting.qtradio.model;

public class ReserveNode extends Node
{
  public int channelId;
  public String programName;
  public Node reserveNode;
  public long reserveTime;
  public int uniqueId;

  public ReserveNode()
  {
    this.nodeName = "reserve";
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.qtradio.model.ReserveNode
 * JD-Core Version:    0.6.2
 */