package cn.com.iresearch.mapptracker.fm;

public abstract interface IRCallBack
{
  public abstract void preSend();

  public abstract void sendFail(String paramString);

  public abstract void sendSuccess();
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     cn.com.iresearch.mapptracker.fm.IRCallBack
 * JD-Core Version:    0.6.2
 */