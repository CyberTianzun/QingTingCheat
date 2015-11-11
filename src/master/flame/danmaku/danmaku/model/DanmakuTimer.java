package master.flame.danmaku.danmaku.model;

public class DanmakuTimer
{
  public long currMillisecond;
  private long lastInterval;

  public long add(long paramLong)
  {
    return update(paramLong + this.currMillisecond);
  }

  public long lastInterval()
  {
    return this.lastInterval;
  }

  public long update(long paramLong)
  {
    this.lastInterval = (paramLong - this.currMillisecond);
    this.currMillisecond = paramLong;
    return this.lastInterval;
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     master.flame.danmaku.danmaku.model.DanmakuTimer
 * JD-Core Version:    0.6.2
 */