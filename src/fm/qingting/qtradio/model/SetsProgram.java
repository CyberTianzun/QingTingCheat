package fm.qingting.qtradio.model;

public class SetsProgram<T extends ProgramSet> extends Program
{
  public long endtime = 0L;
  public ProgramCategory programCategory = null;
  protected transient ProgramSetGroup<T> programSetGroup;
  public int programType = 0;
  public String programsetID = "";
  public String programsetName = "";
  public long recordTime = 0L;
  public boolean recordType = false;
  public int seektime = 0;
  public long startTime = 0L;

  private void initrecord()
  {
    this.programsetID = "";
    this.programsetName = "";
    this.startTime = 0L;
    this.seektime = 0;
    this.endtime = 0L;
  }

  public SetsProgram<T> clone()
  {
    SetsProgram localSetsProgram = new SetsProgram();
    localSetsProgram.setProgram(this);
    return localSetsProgram;
  }

  public ProgramSetGroup<T> getProgramSetGroup()
  {
    return this.programSetGroup;
  }

  public int getType()
  {
    return this.programType;
  }

  public void recordsChannel(ProgramCategory paramProgramCategory, long paramLong, boolean paramBoolean)
  {
    this.programCategory = paramProgramCategory;
    this.recordTime = paramLong;
    this.recordType = paramBoolean;
    initrecord();
  }

  public void recordsChannel(ProgramCategory paramProgramCategory, String paramString1, String paramString2, long paramLong1, int paramInt, long paramLong2, long paramLong3, boolean paramBoolean)
  {
    this.programCategory = paramProgramCategory;
    this.programsetID = paramString1;
    this.programsetName = paramString2;
    this.startTime = paramLong1;
    this.seektime = paramInt;
    this.endtime = paramLong2;
    this.recordTime = paramLong3;
    this.recordType = paramBoolean;
  }

  public void setProgram(Program paramProgram)
  {
    if ((paramProgram instanceof SetsProgram))
    {
      setProgram((SetsProgram)paramProgram);
      return;
    }
    super.setProgram(paramProgram);
  }

  public void setProgram(SetsProgram<T> paramSetsProgram)
  {
    super.setProgram(paramSetsProgram);
    this.programCategory = paramSetsProgram.programCategory;
    this.programSetGroup = paramSetsProgram.programSetGroup;
    this.programType = paramSetsProgram.programType;
    this.programsetID = paramSetsProgram.programsetID;
    this.programsetName = paramSetsProgram.programsetName;
    this.startTime = paramSetsProgram.startTime;
    this.seektime = paramSetsProgram.seektime;
    this.endtime = paramSetsProgram.endtime;
    this.recordTime = paramSetsProgram.recordTime;
    this.recordType = paramSetsProgram.recordType;
  }

  public void setProgramSetGroup(ProgramSetGroup<T> paramProgramSetGroup)
  {
    this.programSetGroup = paramProgramSetGroup;
    setProgramType(this.programSetGroup);
  }

  public void setProgramType(ProgramSetGroup<T> paramProgramSetGroup)
  {
    if (paramProgramSetGroup != null)
      paramProgramSetGroup.setType(this.programType);
  }

  public void setType(int paramInt)
  {
    this.programType = paramInt;
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.qtradio.model.SetsProgram
 * JD-Core Version:    0.6.2
 */