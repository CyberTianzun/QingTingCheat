package fm.qingting.qtradio.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class HotDJProgram
{
  public List<BroadcastTime> broadcastTime = new ArrayList();
  public String name;

  public HotDJProgram(String paramString)
  {
    this.name = paramString;
  }

  public void addElement(BroadcastTime paramBroadcastTime)
  {
    this.broadcastTime.add(paramBroadcastTime);
  }

  public String getName()
  {
    return this.name;
  }

  public boolean isCurrent()
  {
    Iterator localIterator = this.broadcastTime.iterator();
    while (localIterator.hasNext())
      if (((BroadcastTime)localIterator.next()).isCurrentTime())
        return true;
    return false;
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.qtradio.model.HotDJProgram
 * JD-Core Version:    0.6.2
 */