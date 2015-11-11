package fm.qingting.qtradio.model;

import java.util.ArrayList;
import java.util.List;

public class CheckInInfoNode extends Node
{
  private List<CheckInNode> lstCheckIn = new ArrayList();

  public CheckInInfoNode()
  {
    this.nodeName = "checkininfo";
  }

  private int isExisted(String paramString)
  {
    if (paramString == null)
    {
      i = -1;
      return i;
    }
    for (int i = 0; ; i++)
    {
      if (i >= this.lstCheckIn.size())
        break label52;
      if (((CheckInNode)this.lstCheckIn.get(i)).name.equalsIgnoreCase(paramString))
        break;
    }
    label52: return -1;
  }

  public void addCheckInNode(CheckInNode paramCheckInNode)
  {
    if (paramCheckInNode == null);
    while (isExisted(paramCheckInNode.name) != -1)
      return;
    this.lstCheckIn.add(paramCheckInNode);
  }

  public CheckInNode getCheckInNodeByName(String paramString)
  {
    if (paramString == null);
    int i;
    do
    {
      return null;
      i = isExisted(paramString);
    }
    while (i == -1);
    return (CheckInNode)this.lstCheckIn.get(i);
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.qtradio.model.CheckInInfoNode
 * JD-Core Version:    0.6.2
 */