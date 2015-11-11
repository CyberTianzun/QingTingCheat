package fm.qingting.qtradio.social;

import fm.qingting.qtradio.model.Node;

public class MiniFavNode extends Node
{
  public int categoryId;
  public int channelType;
  public int contentType;
  public int id;
  public String name;
  public int parentId;
  public long time;

  public MiniFavNode()
  {
    this.nodeName = "minifav";
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.qtradio.social.MiniFavNode
 * JD-Core Version:    0.6.2
 */