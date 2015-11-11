package fm.qingting.utils;

public abstract interface OnPlayProcessListener
{
  public abstract void onManualSeek();

  public abstract void onProcessChanged();

  public abstract void onProcessMaxChanged();

  public abstract void onProgressPause();

  public abstract void onProgressResume();
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.utils.OnPlayProcessListener
 * JD-Core Version:    0.6.2
 */