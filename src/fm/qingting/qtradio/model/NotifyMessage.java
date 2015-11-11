package fm.qingting.qtradio.model;

import java.io.Serializable;

public class NotifyMessage
  implements Comparable<NotifyMessage>, Serializable
{
  public ActionType actionType = ActionType.PLAY;
  public String channelname;
  public String content;
  public String dueTime;
  public String programId;
  public long recvTime;
  public STATE state = STATE.FRESH;
  public String title;

  public NotifyMessage(STATE paramSTATE, ActionType paramActionType, String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, long paramLong)
  {
    this.state = paramSTATE;
    this.actionType = paramActionType;
    this.title = paramString1;
    this.programId = paramString3;
    this.content = paramString4;
    this.recvTime = paramLong;
    this.dueTime = paramString5;
    this.channelname = paramString2;
  }

  public int compareTo(NotifyMessage paramNotifyMessage)
  {
    int i = -1;
    int j = this.state.compareTo(paramNotifyMessage.state);
    if (j == 0)
      if (this.recvTime == paramNotifyMessage.recvTime)
        i = 0;
    while (this.state == STATE.FRESH)
    {
      do
        return i;
      while (this.recvTime > paramNotifyMessage.recvTime);
      return 1;
    }
    if (this.state == STATE.READ)
      switch (1.$SwitchMap$fm$qingting$qtradio$model$NotifyMessage$STATE[paramNotifyMessage.state.ordinal()])
      {
      case 2:
      default:
      case 1:
      }
    while (this.state != STATE.EXPIRED)
    {
      return j;
      return 1;
    }
    return 1;
  }

  public static enum ActionType
  {
    static
    {
      OPEN = new ActionType("OPEN", 1);
      OTHER = new ActionType("OTHER", 2);
      ActionType[] arrayOfActionType = new ActionType[3];
      arrayOfActionType[0] = PLAY;
      arrayOfActionType[1] = OPEN;
      arrayOfActionType[2] = OTHER;
    }
  }

  public static enum STATE
  {
    static
    {
      EXPIRED = new STATE("EXPIRED", 1);
      READ = new STATE("READ", 2);
      STATE[] arrayOfSTATE = new STATE[3];
      arrayOfSTATE[0] = FRESH;
      arrayOfSTATE[1] = EXPIRED;
      arrayOfSTATE[2] = READ;
    }
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.qtradio.model.NotifyMessage
 * JD-Core Version:    0.6.2
 */