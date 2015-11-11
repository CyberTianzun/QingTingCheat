package fm.qingting.qtradio.model;

import java.util.ArrayList;
import java.util.List;

public class ProgramsListNode extends Node
{
  private boolean available;
  public int programType;
  public String programsId;
  private List<ProgramNode> programsList = new ArrayList();

  public ProgramsListNode()
  {
    this.nodeName = "programslist";
  }

  public boolean isAvailable()
  {
    return this.available;
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.qtradio.model.ProgramsListNode
 * JD-Core Version:    0.6.2
 */