package fm.qingting.async;

public abstract interface DataTrackingEmitter extends DataEmitter
{
  public abstract int getBytesRead();

  public abstract DataTracker getDataTracker();

  public abstract void setDataEmitter(DataEmitter paramDataEmitter);

  public abstract void setDataTracker(DataTracker paramDataTracker);

  public static abstract interface DataTracker
  {
    public abstract void onData(int paramInt);
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.async.DataTrackingEmitter
 * JD-Core Version:    0.6.2
 */