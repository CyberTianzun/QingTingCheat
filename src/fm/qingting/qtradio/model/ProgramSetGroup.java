package fm.qingting.qtradio.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.TimeZone;

public class ProgramSetGroup<T extends ProgramSet> extends Program
{
  protected int programSetIndex = 0;
  protected List<T> programSets = new ArrayList();
  public int programType = 0;
  public int timeRawOffset = TimeZone.getTimeZone("Shanghai").getRawOffset();

  public void addProgramSet(T paramT)
  {
    this.programSets.add(paramT);
  }

  public boolean equals(Object paramObject)
  {
    boolean bool1;
    if (this == paramObject)
      bool1 = true;
    ProgramSetGroup localProgramSetGroup;
    int k;
    int m;
    do
    {
      int i;
      int j;
      do
      {
        boolean bool2;
        do
        {
          do
          {
            return bool1;
            bool1 = false;
          }
          while (paramObject == null);
          bool2 = paramObject instanceof ProgramSetGroup;
          bool1 = false;
        }
        while (!bool2);
        localProgramSetGroup = (ProgramSetGroup)paramObject;
        i = this.timeRawOffset;
        j = localProgramSetGroup.timeRawOffset;
        bool1 = false;
      }
      while (i != j);
      k = getProgramSetCount();
      m = localProgramSetGroup.getProgramSetCount();
      bool1 = false;
    }
    while (k != m);
    Iterator localIterator = this.programSets.iterator();
    for (int n = 0; ; n++)
    {
      if (!localIterator.hasNext())
        break label165;
      ProgramSet localProgramSet1 = (ProgramSet)localIterator.next();
      ProgramSet localProgramSet2 = localProgramSetGroup.getProgramSet(n);
      boolean bool3 = localProgramSet1.getClass().equals(localProgramSet2.getClass());
      bool1 = false;
      if (!bool3)
        break;
      boolean bool4 = localProgramSet1.equals(localProgramSet2);
      bool1 = false;
      if (!bool4)
        break;
    }
    label165: return true;
  }

  public T getNextProgramSet()
  {
    int i = 1 + getProgramSetIndex();
    if ((i >= this.programSets.size()) || (i < 0))
      return null;
    return (ProgramSet)this.programSets.get(i);
  }

  public T getPreviousProgramSet()
  {
    int i = -1 + getProgramSetIndex();
    if ((i >= this.programSets.size()) || (i < 0))
      return null;
    return (ProgramSet)this.programSets.get(i);
  }

  public T getProgramSet()
  {
    if ((this.programSets != null) && (this.programSetIndex >= 0) && (this.programSetIndex < this.programSets.size()))
      return (ProgramSet)this.programSets.get(this.programSetIndex);
    return null;
  }

  public T getProgramSet(int paramInt)
  {
    if ((this.programSets == null) || (paramInt < 0) || (paramInt >= this.programSets.size()))
      return null;
    return (ProgramSet)this.programSets.get(paramInt);
  }

  public int getProgramSetCount()
  {
    if (this.programSets == null)
      return 0;
    return this.programSets.size();
  }

  public int getProgramSetIndex()
  {
    return this.programSetIndex;
  }

  public int getProgramSetIndexOnDemand()
  {
    return this.programSetIndex;
  }

  public int getProgramSetIndexV2()
  {
    return this.programSetIndex;
  }

  public int getProgramSetIndexWithoutVerify()
  {
    return this.programSetIndex;
  }

  public List<T> getProgramSets()
  {
    return this.programSets;
  }

  public boolean isSetsOfProgram(Program paramProgram)
  {
    if ((this.programId == null) || (paramProgram == null));
    while (!this.programId.equalsIgnoreCase(paramProgram.programId))
      return false;
    return true;
  }

  public void setProgramSetIndex(int paramInt)
  {
    this.programSetIndex = verifyProgramSetIndex(paramInt);
  }

  public void setProgramSetIndexWithoutVerify(int paramInt)
  {
    if ((paramInt > this.programSets.size()) || (paramInt < 0))
      return;
    this.programSetIndex = paramInt;
  }

  public void setProgramSets(List<T> paramList)
  {
    this.programSets = new ArrayList(paramList);
    this.programSetIndex = verifyProgramSetIndex(this.programSetIndex);
  }

  public void setType(int paramInt)
  {
    this.programType = paramInt;
  }

  protected int verifyProgramSetIndex(int paramInt)
  {
    if (this.programSets == null)
      paramInt = 0;
    do
    {
      return paramInt;
      int i = this.programSets.size();
      if (paramInt >= i)
        return i;
    }
    while (paramInt >= 0);
    return 0;
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.qtradio.model.ProgramSetGroup
 * JD-Core Version:    0.6.2
 */