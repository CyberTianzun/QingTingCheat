package fm.qingting.qtradio.model;

import java.util.ArrayList;
import java.util.List;

public class ProgramSchedule
{
  public int dayOfWeek;
  public List<ProgramNode> mLstProgramNodes;

  public boolean addProgramNode(ProgramNode paramProgramNode)
  {
    if (paramProgramNode == null)
      return false;
    if (this.mLstProgramNodes == null)
      this.mLstProgramNodes = new ArrayList();
    paramProgramNode.prevSibling = null;
    paramProgramNode.nextSibling = null;
    if (this.mLstProgramNodes.size() > 0)
    {
      int i = 0;
      int j = -1;
      while (true)
      {
        if (i >= this.mLstProgramNodes.size())
          break label119;
        if (((ProgramNode)this.mLstProgramNodes.get(i)).uniqueId == paramProgramNode.uniqueId)
          break;
        if (((ProgramNode)this.mLstProgramNodes.get(i)).sequence < paramProgramNode.sequence)
          j = i;
        i++;
      }
      label119: if (j != -1)
      {
        Node localNode = (Node)this.mLstProgramNodes.get(j);
        paramProgramNode.prevSibling = localNode;
        paramProgramNode.nextSibling = localNode.nextSibling;
        if (localNode.nextSibling != null)
          localNode.nextSibling.prevSibling = paramProgramNode;
        localNode.nextSibling = paramProgramNode;
        if (j < -1 + this.mLstProgramNodes.size())
        {
          int k = j + 1;
          this.mLstProgramNodes.add(k, paramProgramNode);
        }
      }
    }
    while (true)
    {
      return true;
      this.mLstProgramNodes.add(paramProgramNode);
      continue;
      paramProgramNode.nextSibling = ((Node)this.mLstProgramNodes.get(0));
      ((ProgramNode)this.mLstProgramNodes.get(0)).prevSibling = paramProgramNode;
      this.mLstProgramNodes.add(0, paramProgramNode);
      continue;
      this.mLstProgramNodes.add(paramProgramNode);
    }
  }

  public void delProgramNode(int paramInt)
  {
    if (this.mLstProgramNodes == null);
    while (true)
    {
      return;
      for (int i = 0; i < this.mLstProgramNodes.size(); i++)
        if (((ProgramNode)this.mLstProgramNodes.get(i)).uniqueId == paramInt)
        {
          this.mLstProgramNodes.remove(i);
          return;
        }
    }
  }

  public ProgramNode getProgramNodeByTime(long paramLong)
  {
    if (this.mLstProgramNodes == null)
      return null;
    for (int i = 0; i < this.mLstProgramNodes.size(); i++)
      if ((((ProgramNode)this.mLstProgramNodes.get(i)).getAbsoluteStartTime() < paramLong) && (paramLong < ((ProgramNode)this.mLstProgramNodes.get(i)).getAbsoluteEndTime()))
        return (ProgramNode)this.mLstProgramNodes.get(i);
    return null;
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.qtradio.model.ProgramSchedule
 * JD-Core Version:    0.6.2
 */