package fm.qingting.qtradio.model;

public class ReserveChannel
{
  public String anchorName = "";
  public boolean aviable = true;
  public int playtype = 1;
  public ProgramSet program = null;
  public SetsProgram reserveProgram = null;
  public long reservetime = 0L;
  public String showName = "";

  public ReserveChannel()
  {
  }

  public ReserveChannel(long paramLong, String paramString1, String paramString2, SetsProgram paramSetsProgram, int paramInt)
  {
    this.reservetime = paramLong;
    this.showName = paramString1;
    this.anchorName = paramString2;
    this.reserveProgram = paramSetsProgram;
    this.playtype = paramInt;
  }

  public ReserveChannel(long paramLong, String paramString1, String paramString2, SetsProgram paramSetsProgram, int paramInt, boolean paramBoolean)
  {
    this.reservetime = paramLong;
    this.showName = paramString1;
    this.anchorName = paramString2;
    this.reserveProgram = paramSetsProgram;
    this.playtype = paramInt;
    this.aviable = paramBoolean;
  }

  public boolean equals(ReserveChannel paramReserveChannel)
  {
    if (paramReserveChannel == null);
    while ((this.reservetime != paramReserveChannel.reservetime) || (!this.showName.equalsIgnoreCase(paramReserveChannel.showName)) || (!this.anchorName.equalsIgnoreCase(paramReserveChannel.anchorName)))
      return false;
    return true;
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.qtradio.model.ReserveChannel
 * JD-Core Version:    0.6.2
 */