package fm.qingting.qtradio.model;

import java.util.ArrayList;
import java.util.List;

public class ProgramsScheduleNode extends Node
{
  private boolean available;
  public int dayOfWeek;
  public List<ProgramNode> mLstPrograms = new ArrayList();

  public ProgramsScheduleNode()
  {
    this.nodeName = "programsschedule";
  }

  public boolean isAvailable()
  {
    return this.available;
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.qtradio.model.ProgramsScheduleNode
 * JD-Core Version:    0.6.2
 */