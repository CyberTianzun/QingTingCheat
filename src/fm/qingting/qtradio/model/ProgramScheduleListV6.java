package fm.qingting.qtradio.model;

import java.util.ArrayList;
import java.util.List;

public class ProgramScheduleListV6
{
  public int channelId;
  public List<ProgramScheduleV6> mLstProgramsScheduleNodes = new ArrayList();
  public int type;

  public ProgramScheduleListV6(int paramInt)
  {
    this.type = paramInt;
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.qtradio.model.ProgramScheduleListV6
 * JD-Core Version:    0.6.2
 */