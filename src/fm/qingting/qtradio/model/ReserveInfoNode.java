package fm.qingting.qtradio.model;

import android.os.Handler;
import android.os.Message;
import fm.qingting.framework.data.DataManager;
import fm.qingting.framework.data.IResultToken;
import fm.qingting.framework.data.Result;
import fm.qingting.qtradio.alarm.ClockManager;
import fm.qingting.qtradio.alarm.ClockManager.IClockListener;
import fm.qingting.qtradio.controller.ControllerManager;
import fm.qingting.qtradio.notification.Notifier;
import fm.qingting.utils.LifeTime;
import fm.qingting.utils.TimeUtil;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class ReserveInfoNode extends Node
  implements ClockManager.IClockListener
{
  private static final int MAX_PLAY_COUNT = 100;
  private static final int TIME_OVER_DUE = 120;
  private static final int TIME_THRESHOLD = 2;
  private final Handler mHandler = new Handler()
  {
    public void handleMessage(Message paramAnonymousMessage)
    {
      if (paramAnonymousMessage == null)
        return;
      switch (paramAnonymousMessage.what)
      {
      default:
        return;
      case 1:
      }
      int i = ((Integer)paramAnonymousMessage.obj).intValue();
      ReserveInfoNode.this.chooseReserveProgram(i);
    }
  };
  private List<ReserveNode> mLstReserveNodes = null;
  public String mTitle = "预约节目";
  private boolean needToWriteToDB = false;

  public ReserveInfoNode()
  {
    this.nodeName = "reserveinfo";
  }

  private void chooseReserveProgram(int paramInt)
  {
    if ((this.mLstReserveNodes == null) || (this.mLstReserveNodes.size() == 0));
    while (true)
    {
      return;
      for (int i = 0; i < this.mLstReserveNodes.size(); i++)
        if ((((ReserveNode)this.mLstReserveNodes.get(i)).reserveNode.nodeName.equalsIgnoreCase("program")) && (((ReserveNode)this.mLstReserveNodes.get(i)).reserveTime <= paramInt) && (2L + ((ReserveNode)this.mLstReserveNodes.get(i)).reserveTime > paramInt))
        {
          sendReserveNotification((Node)this.mLstReserveNodes.get(i));
          this.mLstReserveNodes.remove(i);
          return;
        }
    }
  }

  private void deleteReserveProgram()
  {
    DataManager.getInstance().getData("delete_reserve_program", null, null);
  }

  private String getTimeInfo(long paramLong)
  {
    Calendar localCalendar = Calendar.getInstance();
    int i = localCalendar.get(6);
    localCalendar.setTimeInMillis(paramLong);
    int j = localCalendar.get(6);
    if (j < i)
      return "";
    if (j == i);
    for (String str = "" + "今天"; ; str = "" + "明天")
    {
      int k = localCalendar.get(12);
      int m = localCalendar.get(11);
      Locale localLocale = Locale.CHINESE;
      Object[] arrayOfObject = new Object[3];
      arrayOfObject[0] = str;
      arrayOfObject[1] = Integer.valueOf(m);
      arrayOfObject[2] = Integer.valueOf(k);
      return String.format(localLocale, "%s%02d:%02d", arrayOfObject);
      if (j != i + 1)
        break;
    }
    return TimeUtil.msToDate3(paramLong);
  }

  private int getTomorrowDayOfWeek()
  {
    int i = Calendar.getInstance().get(7);
    if (i == 7)
      return 1;
    return i + 1;
  }

  public static void msToDate(long paramLong)
  {
    new Date(paramLong);
    new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
  }

  private boolean needToWriteToDB()
  {
    return this.needToWriteToDB;
  }

  private void sendReserveNotification(Node paramNode)
  {
    if (paramNode == null);
    while (true)
    {
      return;
      if (paramNode.nodeName.equalsIgnoreCase("reserve"));
      for (ReserveNode localReserveNode = (ReserveNode)paramNode; localReserveNode != null; localReserveNode = null)
      {
        ProgramNode localProgramNode = (ProgramNode)localReserveNode.reserveNode;
        Notifier localNotifier = new Notifier(ControllerManager.getInstance().getContext());
        String str = "<<" + localProgramNode.title + ">>马上就要开始啦，点击即可收听哦^_^";
        if (localProgramNode.mLiveInVirtual)
        {
          localNotifier.notify("11", "", localProgramNode.title, str, "", String.valueOf(localProgramNode.getAbsoluteStartTime()), localReserveNode.programName, localReserveNode.channelId, "reserve", localReserveNode.channelId, localReserveNode.uniqueId, localReserveNode.channelId, 0, 1, null, null);
          return;
        }
        localNotifier.notify("11", "", localProgramNode.title, str, "", String.valueOf(localProgramNode.getAbsoluteStartTime()), localReserveNode.programName, localReserveNode.channelId, "reserve", localReserveNode.channelId, localReserveNode.uniqueId, localReserveNode.channelId, 0, 0, null, null);
        return;
      }
    }
  }

  public void WriteToDB()
  {
    if (!needToWriteToDB());
    do
    {
      return;
      deleteReserveProgram();
      this.needToWriteToDB = false;
    }
    while (this.mLstReserveNodes.size() == 0);
    HashMap localHashMap = new HashMap();
    localHashMap.put("reserveprogram", this.mLstReserveNodes);
    DataManager.getInstance().getData("insert_reserve_program", null, localHashMap);
  }

  public void addReserveNode(Node paramNode)
  {
    if (paramNode == null);
    ReserveNode localReserveNode;
    long l;
    do
    {
      do
        return;
      while ((!paramNode.nodeName.equalsIgnoreCase("program")) || (isExisted(paramNode)));
      if (this.mLstReserveNodes.size() == 100)
        this.mLstReserveNodes.remove(99);
      localReserveNode = new ReserveNode();
      localReserveNode.reserveNode = paramNode;
      localReserveNode.channelId = ((ProgramNode)paramNode).channelId;
      localReserveNode.programName = ((ProgramNode)paramNode).title;
      localReserveNode.uniqueId = ((ProgramNode)paramNode).id;
      l = ((ProgramNode)paramNode).getAbsoluteStartTime();
    }
    while (l <= System.currentTimeMillis() / 1000L);
    localReserveNode.reserveTime = l;
    int i = 0;
    if (i < this.mLstReserveNodes.size())
      if (l > ((ReserveNode)this.mLstReserveNodes.get(i)).reserveTime);
    for (int j = 1; ; j = 0)
    {
      this.needToWriteToDB = true;
      if (j != 0)
      {
        this.mLstReserveNodes.add(i, localReserveNode);
        return;
        i++;
        break;
      }
      this.mLstReserveNodes.add(localReserveNode);
      return;
      i = 0;
    }
  }

  public boolean allowReserve(Node paramNode)
  {
    if (paramNode == null);
    return false;
  }

  public boolean autoReserveNextProgram(Node paramNode, String paramString1, String paramString2)
  {
    if ((paramNode != null) && (paramString1 != null) && (paramString2 == null));
    return false;
  }

  public boolean autoReserveNextProgram(ProgramNode paramProgramNode)
  {
    return false;
  }

  public void cancelReserve(int paramInt)
  {
    if (this.mLstReserveNodes != null);
    for (int i = 0; ; i++)
      if (i < this.mLstReserveNodes.size())
      {
        if (((ProgramNode)((ReserveNode)this.mLstReserveNodes.get(i)).reserveNode).id == paramInt)
        {
          this.mLstReserveNodes.remove(i);
          this.needToWriteToDB = true;
        }
      }
      else
        return;
  }

  public void deleteAll()
  {
    if (this.mLstReserveNodes != null)
      this.mLstReserveNodes.clear();
    deleteReserveProgram();
    this.needToWriteToDB = true;
  }

  public String getLastestReserveInfo()
  {
    if ((this.mLstReserveNodes == null) || (this.mLstReserveNodes.size() == 0))
      return "预约直播内容，到点准时通知您";
    ReserveNode localReserveNode = (ReserveNode)this.mLstReserveNodes.get(0);
    if ((localReserveNode.reserveNode != null) && (localReserveNode.reserveNode.nodeName.equalsIgnoreCase("program")))
    {
      String str = getTimeInfo(1000L * localReserveNode.reserveTime);
      if (str.equalsIgnoreCase(""))
        return "" + ((ProgramNode)localReserveNode.reserveNode).title;
      return "" + str + ", " + ((ProgramNode)localReserveNode.reserveNode).title;
    }
    return "";
  }

  public long getReadyToInvokeReserveTask()
  {
    if (this.mLstReserveNodes == null)
      return 9223372036854775807L;
    long l = 9223372036854775807L;
    for (int i = 0; i < this.mLstReserveNodes.size(); i++)
      if ((((ReserveNode)this.mLstReserveNodes.get(i)).reserveTime < l) && (1000L * ((ReserveNode)this.mLstReserveNodes.get(i)).reserveTime > System.currentTimeMillis()))
        l = ((ReserveNode)this.mLstReserveNodes.get(i)).reserveTime;
    return l;
  }

  public List<ReserveNode> getReserveInfoNodes()
  {
    if (this.mLstReserveNodes == null)
    {
      Result localResult = DataManager.getInstance().getData("get_reserve_program", null, null).getResult();
      if (localResult.getSuccess())
        this.mLstReserveNodes = ((List)localResult.getData());
      if (this.mLstReserveNodes == null)
        this.mLstReserveNodes = new ArrayList();
      long l = System.currentTimeMillis() / 1000L;
      for (int i = 0; i < this.mLstReserveNodes.size(); i++)
        if (120L + ((ReserveNode)this.mLstReserveNodes.get(i)).reserveTime < l)
        {
          this.mLstReserveNodes.remove(i);
          i--;
        }
    }
    return this.mLstReserveNodes;
  }

  public void init()
  {
    if (!LifeTime.isFirstLaunchAfterInstall)
      getReserveInfoNodes();
    while (true)
    {
      ClockManager.getInstance().addListener(this);
      return;
      this.mLstReserveNodes = new ArrayList();
    }
  }

  public int isExisted(int paramInt)
  {
    if (this.mLstReserveNodes != null)
      for (int i = -1 + this.mLstReserveNodes.size(); i >= 0; i--)
        if (((ProgramNode)((ReserveNode)this.mLstReserveNodes.get(i)).reserveNode).id == paramInt)
          return i;
    return -1;
  }

  public boolean isExisted(Node paramNode)
  {
    if (paramNode == null);
    while (this.mLstReserveNodes == null)
      return false;
    if (paramNode.nodeName.equalsIgnoreCase("program"));
    for (int i = ((ProgramNode)paramNode).id; ; i = 0)
    {
      for (int j = -1 + this.mLstReserveNodes.size(); j >= 0; j--)
        if ((((ReserveNode)this.mLstReserveNodes.get(j)).reserveNode.nodeName.equalsIgnoreCase("program")) && (((ProgramNode)((ReserveNode)this.mLstReserveNodes.get(j)).reserveNode).id == i))
          return true;
      break;
    }
  }

  public void onClockTime(int paramInt)
  {
    if (this.mHandler != null)
    {
      Message localMessage = Message.obtain(this.mHandler, 1, Integer.valueOf(paramInt));
      this.mHandler.sendMessage(localMessage);
    }
  }

  public void onTime(Clock paramClock)
  {
  }

  public void onTimeStart(Clock paramClock)
  {
  }

  public void onTimeStop(Clock paramClock)
  {
  }

  public void onTimerRemoved()
  {
  }

  public boolean reserveNextProgram(Node paramNode)
  {
    if (paramNode == null);
    return false;
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.qtradio.model.ReserveInfoNode
 * JD-Core Version:    0.6.2
 */