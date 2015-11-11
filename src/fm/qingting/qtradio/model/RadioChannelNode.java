package fm.qingting.qtradio.model;

import fm.qingting.qtradio.helper.ChannelHelper;
import fm.qingting.qtradio.helper.ProgramHelper;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class RadioChannelNode extends Node
{
  public transient int ContentType = 0;
  public int channelId;
  public String channelName;
  public String freq;
  public transient boolean hasLoadPrograms = false;
  public transient List<ProgramNode> lstProgramNodes = new ArrayList();
  public transient ProgramNode programNodeError = null;

  public RadioChannelNode()
  {
    this.nodeName = "radiochannel";
    init();
  }

  private void init()
  {
    this.freq = "";
    this.channelId = 0;
    this.channelName = "FM";
  }

  public ChannelNode convertToChannelNode()
  {
    return ChannelHelper.getInstance().getChannel(this.channelId, 0);
  }

  public ProgramNode getCurrentPlayingProgramNode(long paramLong)
  {
    if (this.lstProgramNodes != null)
    {
      long l1 = paramLong / 1000L;
      for (int i = 0; i < this.lstProgramNodes.size(); i++)
      {
        long l2 = ((ProgramNode)this.lstProgramNodes.get(i)).getAbsoluteStartTime();
        long l3 = ((ProgramNode)this.lstProgramNodes.get(i)).getAbsoluteEndTime();
        if ((l2 <= l1) && (l3 > l1))
          return (ProgramNode)this.lstProgramNodes.get(i);
      }
    }
    return ProgramHelper.getInstance().getProgramTempNode();
  }

  public String getProgramsDuraion()
  {
    return String.valueOf(Calendar.getInstance().get(7));
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.qtradio.model.RadioChannelNode
 * JD-Core Version:    0.6.2
 */