package com.tencent.stat.event;

public enum EventType
{
  private int v;

  static
  {
    ERROR = new EventType("ERROR", 2, 3);
    CUSTOM = new EventType("CUSTOM", 3, 1000);
    ADDITION = new EventType("ADDITION", 4, 1001);
    MONITOR_STAT = new EventType("MONITOR_STAT", 5, 1002);
    EventType[] arrayOfEventType = new EventType[6];
    arrayOfEventType[0] = PAGE_VIEW;
    arrayOfEventType[1] = SESSION_ENV;
    arrayOfEventType[2] = ERROR;
    arrayOfEventType[3] = CUSTOM;
    arrayOfEventType[4] = ADDITION;
    arrayOfEventType[5] = MONITOR_STAT;
  }

  private EventType(int paramInt)
  {
    this.v = paramInt;
  }

  public int GetIntValue()
  {
    return this.v;
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.tencent.stat.event.EventType
 * JD-Core Version:    0.6.2
 */