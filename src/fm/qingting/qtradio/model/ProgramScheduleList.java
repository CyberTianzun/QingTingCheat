package fm.qingting.qtradio.model;

import fm.qingting.framework.data.DataManager;
import fm.qingting.utils.TimeUtil;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProgramScheduleList extends Node
{
  public int channelId;
  private transient boolean hasUpdated = false;
  public List<ProgramSchedule> mLstProgramsScheduleNodes = new ArrayList();
  public int type;

  public ProgramScheduleList(int paramInt)
  {
    this.nodeName = "programschedulelist";
    this.type = paramInt;
  }

  public boolean addProgramNode(ProgramNode paramProgramNode)
  {
    if (paramProgramNode == null)
      return false;
    if (this.mLstProgramsScheduleNodes.size() == 0)
    {
      ProgramSchedule localProgramSchedule = new ProgramSchedule();
      this.mLstProgramsScheduleNodes.add(localProgramSchedule);
    }
    return ((ProgramSchedule)this.mLstProgramsScheduleNodes.get(0)).addProgramNode(paramProgramNode);
  }

  public void delProgramNode(int paramInt)
  {
    if (this.mLstProgramsScheduleNodes.size() == 0)
      return;
    ((ProgramSchedule)this.mLstProgramsScheduleNodes.get(0)).delProgramNode(paramInt);
  }

  public List<ProgramNode> getLstProgramNode(int paramInt)
  {
    for (int i = 0; i < this.mLstProgramsScheduleNodes.size(); i++)
      if (((ProgramSchedule)this.mLstProgramsScheduleNodes.get(i)).dayOfWeek == paramInt)
        return ((ProgramSchedule)this.mLstProgramsScheduleNodes.get(i)).mLstProgramNodes;
    return null;
  }

  public ProgramNode getProgramNode(int paramInt)
  {
    for (int i = 0; i < this.mLstProgramsScheduleNodes.size(); i++)
      for (int j = 0; j < ((ProgramSchedule)this.mLstProgramsScheduleNodes.get(i)).mLstProgramNodes.size(); j++)
        if (((ProgramNode)((ProgramSchedule)this.mLstProgramsScheduleNodes.get(i)).mLstProgramNodes.get(j)).id == paramInt)
          return (ProgramNode)((ProgramSchedule)this.mLstProgramsScheduleNodes.get(i)).mLstProgramNodes.get(j);
    return null;
  }

  public ProgramNode getProgramNodeByTime(long paramLong)
  {
    int i = TimeUtil.getDayofWeek(paramLong);
    for (int j = 0; j < this.mLstProgramsScheduleNodes.size(); j++)
      if (((ProgramSchedule)this.mLstProgramsScheduleNodes.get(j)).dayOfWeek == i)
        return ((ProgramSchedule)this.mLstProgramsScheduleNodes.get(j)).getProgramNodeByTime(paramLong);
    return null;
  }

  public void setChannelId(int paramInt)
  {
    this.channelId = paramInt;
    for (int i = 0; i < this.mLstProgramsScheduleNodes.size(); i++)
      for (int j = 0; j < ((ProgramSchedule)this.mLstProgramsScheduleNodes.get(i)).mLstProgramNodes.size(); j++)
        ((ProgramNode)((ProgramSchedule)this.mLstProgramsScheduleNodes.get(i)).mLstProgramNodes.get(j)).channelId = paramInt;
  }

  public void updateToDB()
  {
    if ((this.type == 0) || (this.hasUpdated));
    List localList;
    do
    {
      return;
      localList = getLstProgramNode(0);
    }
    while ((localList == null) || (localList.size() == 0));
    this.hasUpdated = true;
    HashMap localHashMap = new HashMap();
    localHashMap.put("id", Integer.valueOf(this.channelId));
    localHashMap.put("nodes", localList);
    localHashMap.put("size", Integer.valueOf(localList.size()));
    DataManager.getInstance().getData("updatedb_program_node", null, localHashMap);
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.qtradio.model.ProgramScheduleList
 * JD-Core Version:    0.6.2
 */