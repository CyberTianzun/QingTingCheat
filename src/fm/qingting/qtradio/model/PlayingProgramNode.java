package fm.qingting.qtradio.model;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class PlayingProgramNode extends Node
{
  public String broadcastTime;
  public int channelId;
  public String channelName;
  public int duration = 1;
  public int endTime = -1;
  public List<String> lstbroadcastersName;
  public String programName;
  public int startTime = -1;

  public PlayingProgramNode()
  {
    this.nodeName = "playingprogram";
  }

  public Node clone()
  {
    PlayingProgramNode localPlayingProgramNode = new PlayingProgramNode();
    localPlayingProgramNode.channelId = this.channelId;
    localPlayingProgramNode.programName = this.programName;
    localPlayingProgramNode.broadcastTime = this.broadcastTime;
    localPlayingProgramNode.lstbroadcastersName = new ArrayList();
    localPlayingProgramNode.duration = this.duration;
    for (int i = 0; i < this.lstbroadcastersName.size(); i++)
      localPlayingProgramNode.lstbroadcastersName.add(this.lstbroadcastersName.get(i));
    return localPlayingProgramNode;
  }

  public int endTime()
  {
    if (-1 == this.endTime);
    try
    {
      this.endTime = (startTime() + this.duration);
      label21: return this.endTime;
    }
    catch (Exception localException)
    {
      break label21;
    }
  }

  public String getFirstBroadcaster()
  {
    if (this.lstbroadcastersName.size() > 0)
      return (String)this.lstbroadcastersName.get(0);
    return "";
  }

  public int startTime()
  {
    if (-1 == this.startTime);
    try
    {
      String[] arrayOfString = Pattern.compile("\\D+").split(this.broadcastTime);
      if (arrayOfString.length == 2)
      {
        int i = Integer.parseInt(arrayOfString[0]);
        int j = Integer.parseInt(arrayOfString[1]);
        this.startTime = (i * 3600 + j * 60);
      }
      label57: return this.startTime;
    }
    catch (Exception localException)
    {
      break label57;
    }
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.qtradio.model.PlayingProgramNode
 * JD-Core Version:    0.6.2
 */