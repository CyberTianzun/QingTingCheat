package com.tencent.stat;

public enum StatReportStrategy
{
  int v;

  static
  {
    BATCH = new StatReportStrategy("BATCH", 2, 3);
    APP_LAUNCH = new StatReportStrategy("APP_LAUNCH", 3, 4);
    DEVELOPER = new StatReportStrategy("DEVELOPER", 4, 5);
    PERIOD = new StatReportStrategy("PERIOD", 5, 6);
    ONLY_WIFI_NO_CACHE = new StatReportStrategy("ONLY_WIFI_NO_CACHE", 6, 7);
    StatReportStrategy[] arrayOfStatReportStrategy = new StatReportStrategy[7];
    arrayOfStatReportStrategy[0] = INSTANT;
    arrayOfStatReportStrategy[1] = ONLY_WIFI;
    arrayOfStatReportStrategy[2] = BATCH;
    arrayOfStatReportStrategy[3] = APP_LAUNCH;
    arrayOfStatReportStrategy[4] = DEVELOPER;
    arrayOfStatReportStrategy[5] = PERIOD;
    arrayOfStatReportStrategy[6] = ONLY_WIFI_NO_CACHE;
  }

  private StatReportStrategy(int paramInt)
  {
    this.v = paramInt;
  }

  public static StatReportStrategy getStatReportStrategy(int paramInt)
  {
    for (StatReportStrategy localStatReportStrategy : values())
      if (paramInt == localStatReportStrategy.getIntValue())
        return localStatReportStrategy;
    return null;
  }

  public int getIntValue()
  {
    return this.v;
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.tencent.stat.StatReportStrategy
 * JD-Core Version:    0.6.2
 */