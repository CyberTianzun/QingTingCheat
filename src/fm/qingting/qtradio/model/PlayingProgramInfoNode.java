package fm.qingting.qtradio.model;

import fm.qingting.qtradio.helper.ProgramHelper;
import fm.qingting.qtradio.social.MiniFavNode;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PlayingProgramInfoNode extends Node
{
  private Map<Integer, Node> mapPlayingProgram = new HashMap();

  public PlayingProgramInfoNode()
  {
    this.nodeName = "playingprograminfo";
  }

  private Node getCurrentPlayingProgramById(int paramInt)
  {
    if (this.mapPlayingProgram.containsKey(Integer.valueOf(paramInt)))
    {
      Node localNode = (Node)this.mapPlayingProgram.get(Integer.valueOf(paramInt));
      if (hasOutOfDate(localNode))
      {
        this.mapPlayingProgram.remove(Integer.valueOf(paramInt));
        localNode = null;
      }
      return localNode;
    }
    return null;
  }

  private int getRelativeTime(long paramLong)
  {
    Calendar localCalendar = Calendar.getInstance();
    localCalendar.setTimeInMillis(paramLong);
    int i = localCalendar.get(11);
    int j = localCalendar.get(12);
    return 60 * (i * 60) + j * 60;
  }

  private boolean hasOutOfDate(Node paramNode)
  {
    if (paramNode == null)
      return true;
    if (paramNode.nodeName.equalsIgnoreCase("playingprogram"))
    {
      int i = getRelativeTime(System.currentTimeMillis());
      if ((((PlayingProgramNode)paramNode).endTime() > i) && (((PlayingProgramNode)paramNode).startTime() < i))
        return false;
    }
    return true;
  }

  private void updatePlayingProgram(int paramInt, Node paramNode)
  {
    if (paramNode == null);
    do
    {
      return;
      if (!this.mapPlayingProgram.containsKey(Integer.valueOf(paramInt)))
        break;
    }
    while (!hasOutOfDate((Node)this.mapPlayingProgram.get(Integer.valueOf(paramInt))));
    this.mapPlayingProgram.remove(Integer.valueOf(paramInt));
    this.mapPlayingProgram.put(Integer.valueOf(paramInt), paramNode);
    return;
    this.mapPlayingProgram.put(Integer.valueOf(paramInt), paramNode);
  }

  public Node getCurrentPlayingProgram(int paramInt, String paramString)
  {
    Node localNode = getCurrentPlayingProgramById(paramInt);
    if (localNode == null)
    {
      long l = System.currentTimeMillis() / 1000L;
      ProgramScheduleList localProgramScheduleList = ProgramHelper.getInstance().getProgramSchedule(paramInt, 0, true);
      if (localProgramScheduleList != null)
      {
        ProgramNode localProgramNode = localProgramScheduleList.getProgramNodeByTime(l);
        if (localProgramNode != null)
        {
          PlayingProgramNode localPlayingProgramNode = new PlayingProgramNode();
          localPlayingProgramNode.channelId = paramInt;
          localPlayingProgramNode.channelName = paramString;
          localPlayingProgramNode.programName = localProgramNode.title;
          localPlayingProgramNode.broadcastTime = localProgramNode.startTime;
          localPlayingProgramNode.duration = localProgramNode.getDuration();
          if (localProgramNode.lstBroadcaster != null)
          {
            localPlayingProgramNode.lstbroadcastersName = new ArrayList();
            for (int i = 0; i < localProgramNode.lstBroadcaster.size(); i++)
              localPlayingProgramNode.lstbroadcastersName.add(((BroadcasterNode)localProgramNode.lstBroadcaster.get(i)).nick);
          }
          this.mapPlayingProgram.put(Integer.valueOf(paramInt), localPlayingProgramNode);
          return localPlayingProgramNode;
        }
      }
    }
    return localNode;
  }

  public Node getCurrentPlayingProgram(Node paramNode)
  {
    if (paramNode == null)
      return null;
    if (paramNode.nodeName.equalsIgnoreCase("channel"))
    {
      ChannelNode localChannelNode = (ChannelNode)paramNode;
      int k = localChannelNode.channelId;
      String str2 = localChannelNode.title;
      Node localNode2 = getCurrentPlayingProgramById(k);
      if (localNode2 == null)
      {
        ProgramNode localProgramNode2 = localChannelNode.getProgramNodeByTime(System.currentTimeMillis());
        if ((localProgramNode2 != null) && (localProgramNode2.available))
        {
          PlayingProgramNode localPlayingProgramNode2 = new PlayingProgramNode();
          localPlayingProgramNode2.channelId = k;
          localPlayingProgramNode2.channelName = str2;
          localPlayingProgramNode2.programName = localProgramNode2.title;
          localPlayingProgramNode2.broadcastTime = localProgramNode2.startTime;
          localPlayingProgramNode2.duration = localProgramNode2.getDuration();
          if (localProgramNode2.lstBroadcaster != null)
          {
            localPlayingProgramNode2.lstbroadcastersName = new ArrayList();
            for (int m = 0; m < localProgramNode2.lstBroadcaster.size(); m++)
              localPlayingProgramNode2.lstbroadcastersName.add(((BroadcasterNode)localProgramNode2.lstBroadcaster.get(m)).nick);
          }
          this.mapPlayingProgram.put(Integer.valueOf(k), localPlayingProgramNode2);
          return localPlayingProgramNode2;
        }
        return null;
      }
      return localNode2;
    }
    if (paramNode.nodeName.equalsIgnoreCase("minifav"))
    {
      MiniFavNode localMiniFavNode = (MiniFavNode)paramNode;
      if (localMiniFavNode.contentType == 0)
        return getCurrentPlayingProgramById(localMiniFavNode.id);
    }
    else if (paramNode.nodeName.equalsIgnoreCase("radiochannel"))
    {
      RadioChannelNode localRadioChannelNode = (RadioChannelNode)paramNode;
      int i = localRadioChannelNode.channelId;
      String str1 = localRadioChannelNode.channelName;
      Node localNode1 = getCurrentPlayingProgramById(i);
      if (localNode1 == null)
      {
        ProgramNode localProgramNode1 = localRadioChannelNode.getCurrentPlayingProgramNode(System.currentTimeMillis());
        if ((localProgramNode1 != null) && (localProgramNode1.available))
        {
          PlayingProgramNode localPlayingProgramNode1 = new PlayingProgramNode();
          localPlayingProgramNode1.channelId = i;
          localPlayingProgramNode1.channelName = str1;
          localPlayingProgramNode1.programName = localProgramNode1.title;
          localPlayingProgramNode1.broadcastTime = localProgramNode1.startTime;
          localPlayingProgramNode1.duration = localProgramNode1.getDuration();
          localPlayingProgramNode1.lstbroadcastersName = new ArrayList();
          for (int j = 0; j < localProgramNode1.lstBroadcaster.size(); j++)
            localPlayingProgramNode1.lstbroadcastersName.add(((BroadcasterNode)localProgramNode1.lstBroadcaster.get(j)).nick);
          this.mapPlayingProgram.put(Integer.valueOf(i), localPlayingProgramNode1);
          return localPlayingProgramNode1;
        }
        return null;
      }
      return localNode1;
    }
    return null;
  }

  public boolean isExist(int paramInt)
  {
    return this.mapPlayingProgram.containsKey(Integer.valueOf(paramInt));
  }

  public void onNodeUpdated(Object paramObject, String paramString)
  {
    List localList = (List)paramObject;
    if ((localList == null) || (paramString == null));
    while (true)
    {
      return;
      if (paramString.equalsIgnoreCase("ACPP"))
        for (int i = 0; i < localList.size(); i++)
          if (((Node)localList.get(i)).nodeName.equalsIgnoreCase("playingprogram"))
            updatePlayingProgram(((PlayingProgramNode)localList.get(i)).channelId, ((PlayingProgramNode)localList.get(i)).clone());
    }
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.qtradio.model.PlayingProgramInfoNode
 * JD-Core Version:    0.6.2
 */