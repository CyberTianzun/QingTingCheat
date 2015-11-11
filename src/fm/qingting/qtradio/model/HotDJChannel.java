package fm.qingting.qtradio.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class HotDJChannel
{
  public List<HotDJProgram> djProgram = new ArrayList();
  public String id;
  public String name;
  public String rid;

  public HotDJChannel(String paramString1, String paramString2, String paramString3)
  {
    this.id = paramString1;
    this.name = paramString2;
    this.rid = paramString3;
  }

  public void addElement(HotDJProgram paramHotDJProgram)
  {
    this.djProgram.add(paramHotDJProgram);
  }

  public String getCurrentChannelName()
  {
    Iterator localIterator = this.djProgram.iterator();
    Object localObject = null;
    HotDJProgram localHotDJProgram;
    if (localIterator.hasNext())
    {
      localHotDJProgram = (HotDJProgram)localIterator.next();
      if (localHotDJProgram.isCurrent())
        localObject = "正在主持:" + localHotDJProgram.getName();
    }
    else
    {
      return localObject;
    }
    if (localObject == null);
    for (String str = localHotDJProgram.getName(); ; str = (String)localObject + " " + localHotDJProgram.getName())
    {
      localObject = str;
      break;
    }
  }

  public String getName()
  {
    return this.name;
  }

  public boolean isCurrent()
  {
    Iterator localIterator = this.djProgram.iterator();
    while (localIterator.hasNext())
      if (((HotDJProgram)localIterator.next()).isCurrent())
        return true;
    return false;
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.qtradio.model.HotDJChannel
 * JD-Core Version:    0.6.2
 */