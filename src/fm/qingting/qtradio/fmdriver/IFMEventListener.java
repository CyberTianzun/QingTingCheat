package fm.qingting.qtradio.fmdriver;

public abstract interface IFMEventListener
{
  public abstract void onAudioQualityStatus(int paramInt);

  public abstract void onChannelFound(int paramInt);

  public abstract void onFMOff();

  public abstract void onFMOn();

  public abstract void onHeadsetPlugged();

  public abstract void onHeadsetUnplugged();

  public abstract void onScanComplete(boolean paramBoolean);

  public abstract void onScanStarted();

  public abstract void onTune(int paramInt);
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.qtradio.fmdriver.IFMEventListener
 * JD-Core Version:    0.6.2
 */