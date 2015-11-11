package fm.qingting.qtradio.helper;

import android.os.Handler;
import android.os.Message;
import fm.qingting.framework.data.DataManager;
import fm.qingting.framework.data.IResultToken;
import fm.qingting.framework.data.Result;
import fm.qingting.qtradio.model.ChannelNode;
import fm.qingting.qtradio.model.InfoManager;
import fm.qingting.qtradio.model.Node;
import fm.qingting.qtradio.model.ProgramNode;
import fm.qingting.qtradio.model.ProgramSchedule;
import fm.qingting.qtradio.model.ProgramScheduleList;
import fm.qingting.qtradio.model.RootNode;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProgramHelper extends Node
{
  private static ProgramHelper _instance = null;
  private Map<Integer, ProgramScheduleList> mapProgramNodes = new HashMap();
  public Map<Integer, Boolean> mapUpdatePrograms = new HashMap();
  public transient ProgramNode programNodeTemp;

  private ProgramHelper()
  {
    this.nodeName = "programhelper";
  }

  private boolean addProgramSchedule(ProgramScheduleList paramProgramScheduleList, Map<String, String> paramMap)
  {
    if (paramProgramScheduleList == null);
    label330: label719: label848: label855: label859: 
    while (true)
    {
      return false;
      if (paramMap == null)
        return addProgramSchedule(paramProgramScheduleList);
      String str = (String)paramMap.get("id");
      if (str != null)
      {
        paramProgramScheduleList.setChannelId(Integer.valueOf(str).intValue());
        int i = paramProgramScheduleList.channelId;
        ProgramScheduleList localProgramScheduleList1 = (ProgramScheduleList)this.mapProgramNodes.get(Integer.valueOf(i));
        List localList1;
        int j;
        List localList2;
        int i5;
        if ((localProgramScheduleList1 != null) && (localProgramScheduleList1.type == 1))
          if (localProgramScheduleList1.type == 1)
          {
            localList1 = paramProgramScheduleList.getLstProgramNode(0);
            if (localList1 != null)
              for (int i6 = 0; i6 < localList1.size(); i6++)
                ((ProgramNode)localList1.get(i6)).channelId = i;
            j = Integer.valueOf((String)paramMap.get("page")).intValue();
            int k = Integer.valueOf((String)paramMap.get("pagesize")).intValue();
            ProgramScheduleList localProgramScheduleList2 = (ProgramScheduleList)this.mapProgramNodes.get(Integer.valueOf(i));
            if (localProgramScheduleList2 == null)
              this.mapProgramNodes.put(Integer.valueOf(i), paramProgramScheduleList);
            int m;
            while (true)
            {
              return true;
              localList2 = localProgramScheduleList2.getLstProgramNode(0);
              m = localList2.size() / k;
              if (localList2.size() == 0)
              {
                this.mapProgramNodes.put(Integer.valueOf(i), paramProgramScheduleList);
              }
              else
              {
                if (m != 0)
                  break;
                addProgramSchedule(paramProgramScheduleList);
              }
            }
            if ((j > m) && (localList1.size() > 0))
            {
              ProgramNode localProgramNode4 = (ProgramNode)localList1.get(-1 + localList1.size());
              if (localProgramNode4 == null)
                break label855;
              i5 = 1;
              if (i5 >= localList2.size())
                break label855;
              if (((ProgramNode)localList2.get(i5)).uniqueId != localProgramNode4.uniqueId);
            }
          }
        for (int i4 = 1; ; i4 = 0)
        {
          if (i4 != 0)
            break label859;
          Node localNode = (Node)localList1.get(0);
          localNode.prevSibling = ((Node)localList2.get(-1 + localList2.size()));
          ((ProgramNode)localList2.get(-1 + localList2.size())).nextSibling = localNode;
          localList2.addAll(localList1);
          break;
          i5++;
          break label330;
          int n;
          int i1;
          int i2;
          if ((j == 1) && (localList1.size() > 0))
          {
            n = 0;
            i1 = 0;
            if (n < localList1.size())
            {
              i2 = 0;
              label490: if ((i2 >= localList2.size()) || (i2 >= localList1.size()))
                break label848;
              if (((ProgramNode)localList2.get(i2)).uniqueId != ((ProgramNode)localList1.get(n)).uniqueId);
            }
          }
          for (int i3 = 1; ; i3 = i1)
          {
            if (i3 == 0)
            {
              if (n >= localList2.size())
                break label719;
              ProgramNode localProgramNode2 = (ProgramNode)((ProgramNode)localList2.get(n)).prevSibling;
              ProgramNode localProgramNode3 = (ProgramNode)((ProgramNode)localList2.get(n)).nextSibling;
              localList2.add(n, localList1.get(n));
              ((ProgramNode)localList2.get(n)).prevSibling = localProgramNode2;
              ((ProgramNode)localList2.get(n)).nextSibling = localProgramNode3;
              if (localProgramNode2 != null)
                localProgramNode2.nextSibling = ((Node)localList1.get(n));
              if (localProgramNode3 != null)
                localProgramNode3.prevSibling = ((Node)localList1.get(n));
            }
            while (true)
            {
              n++;
              i1 = i3;
              break;
              i2++;
              break label490;
              ProgramNode localProgramNode1 = (ProgramNode)localList2.get(-1 + localList2.size());
              if (localProgramNode1 != null)
              {
                localList2.add(localList1.get(n));
                ((ProgramNode)localList1.get(n)).prevSibling = localProgramNode1;
                localProgramNode1.nextSibling = ((Node)localList1.get(n));
              }
            }
            return true;
            addProgramSchedule(paramProgramScheduleList);
            break;
            this.mapProgramNodes.put(Integer.valueOf(i), paramProgramScheduleList);
            break;
            this.mapProgramNodes.put(Integer.valueOf(i), paramProgramScheduleList);
            break;
          }
        }
      }
    }
  }

  private boolean allowReadCache(int paramInt)
  {
    return !InfoManager.getInstance().hasConnectedNetwork();
  }

  public static ProgramHelper getInstance()
  {
    if (_instance == null)
      _instance = new ProgramHelper();
    return _instance;
  }

  private boolean restoreProgramSchedule(int paramInt)
  {
    Object localObject = null;
    int i = 0;
    HashMap localHashMap = new HashMap();
    localHashMap.put("id", Integer.valueOf(paramInt));
    Result localResult = DataManager.getInstance().getData("getdb_program_node", null, localHashMap).getResult();
    if ((localResult != null) && (localResult.getSuccess()));
    for (List localList = (List)localResult.getData(); ; localList = null)
    {
      if ((localList == null) || (localList.size() == 0))
        return false;
      ProgramScheduleList localProgramScheduleList = new ProgramScheduleList(1);
      localProgramScheduleList.channelId = ((ProgramNode)localList.get(0)).channelId;
      ProgramSchedule localProgramSchedule = new ProgramSchedule();
      localProgramSchedule.mLstProgramNodes = localList;
      localProgramSchedule.dayOfWeek = 0;
      localProgramScheduleList.mLstProgramsScheduleNodes.add(localProgramSchedule);
      this.mapProgramNodes.put(Integer.valueOf(paramInt), localProgramScheduleList);
      while (i < localList.size())
      {
        if (localObject != null)
        {
          localObject.nextSibling = ((Node)localList.get(i));
          ((ProgramNode)localList.get(i)).prevSibling = localObject;
        }
        Node localNode = (Node)localList.get(i);
        i++;
        localObject = localNode;
      }
      return true;
    }
  }

  public boolean addProgramSchedule(Node paramNode)
  {
    if (paramNode == null)
      return false;
    if (paramNode.nodeName.equalsIgnoreCase("program"))
    {
      int j = ((ProgramNode)paramNode).channelId;
      ProgramScheduleList localProgramScheduleList = (ProgramScheduleList)this.mapProgramNodes.get(Integer.valueOf(j));
      if ((localProgramScheduleList == null) && (((ProgramNode)paramNode).channelType == 1))
        localProgramScheduleList = new ProgramScheduleList(1);
      if ((localProgramScheduleList != null) && (localProgramScheduleList.type == 1))
        localProgramScheduleList.addProgramNode((ProgramNode)paramNode);
    }
    while (true)
    {
      return true;
      if (paramNode.nodeName.equalsIgnoreCase("programschedulelist"))
      {
        int i = ((ProgramScheduleList)paramNode).channelId;
        ChannelNode localChannelNode = InfoManager.getInstance().root().getCurrentPlayingChannelNode();
        if ((localChannelNode != null) && (localChannelNode.channelId == i))
          localChannelNode.setProgramScheduleList((ProgramScheduleList)paramNode);
        this.mapProgramNodes.put(Integer.valueOf(i), (ProgramScheduleList)paramNode);
      }
    }
  }

  public ProgramScheduleList getProgramSchedule(int paramInt1, int paramInt2, boolean paramBoolean)
  {
    ProgramScheduleList localProgramScheduleList = (ProgramScheduleList)this.mapProgramNodes.get(Integer.valueOf(paramInt1));
    if ((localProgramScheduleList == null) && (paramInt2 == 1) && ((allowReadCache(paramInt1)) || (paramBoolean)) && (restoreProgramSchedule(paramInt1)))
      localProgramScheduleList = (ProgramScheduleList)this.mapProgramNodes.get(Integer.valueOf(paramInt1));
    return localProgramScheduleList;
  }

  public ProgramNode getProgramTempNode()
  {
    if (this.programNodeTemp != null)
      return this.programNodeTemp;
    this.programNodeTemp = new ProgramNode();
    this.programNodeTemp.title = "节目单加载中";
    this.programNodeTemp.startTime = "00:00";
    this.programNodeTemp.endTime = "23:59";
    this.programNodeTemp.available = false;
    this.programNodeTemp.uniqueId = 0;
    this.programNodeTemp.duration = this.programNodeTemp.getDuration();
    this.programNodeTemp.parent = this;
    return this.programNodeTemp;
  }

  public void init()
  {
    InfoManager.getInstance().registerNodeEventListener(this, "ADD_VIRTUAL_PROGRAMS_SCHEDULE");
    InfoManager.getInstance().registerNodeEventListener(this, "ADD_LIVE_PROGRAMS_SCHEDULE");
  }

  public void onNodeUpdated(Object paramObject, Map<String, String> paramMap, String paramString)
  {
    Node localNode = (Node)paramObject;
    if (localNode == null);
    do
    {
      do
      {
        boolean bool;
        do
        {
          return;
          if (!paramString.equalsIgnoreCase("ADD_VIRTUAL_PROGRAMS_SCHEDULE"))
            break;
          bool = addProgramSchedule((ProgramScheduleList)localNode, paramMap);
        }
        while ((localNode == null) || (!bool));
        udpateToDB((ProgramScheduleList)localNode);
        return;
        if (paramString.equalsIgnoreCase("ADD_LIVE_PROGRAMS_SCHEDULE"))
        {
          addProgramSchedule((ProgramScheduleList)localNode, paramMap);
          return;
        }
      }
      while (!paramString.equalsIgnoreCase("ADD_RELOAD_VIRTUAL_PROGRAMS_SCHEDULE"));
      addProgramSchedule((ProgramScheduleList)localNode, paramMap);
    }
    while ((localNode == null) || (!localNode.nodeName.equalsIgnoreCase("programschedulelist")));
    udpateToDB((ProgramScheduleList)localNode);
  }

  public void udpateToDB(ProgramScheduleList paramProgramScheduleList)
  {
    if (paramProgramScheduleList == null);
    ProgramScheduleList localProgramScheduleList;
    do
    {
      return;
      int i = paramProgramScheduleList.channelId;
      localProgramScheduleList = (ProgramScheduleList)this.mapProgramNodes.get(Integer.valueOf(i));
    }
    while ((localProgramScheduleList == null) || (localProgramScheduleList.channelId == 0) || (localProgramScheduleList.type == 0));
    Message localMessage = new Message();
    localMessage.what = 5;
    localMessage.obj = localProgramScheduleList;
    InfoManager.getInstance().getDataStoreHandler().sendMessage(localMessage);
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.qtradio.helper.ProgramHelper
 * JD-Core Version:    0.6.2
 */